package jp.co.ais.trans2.model.slip.parts;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.management.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.slip.SWK_DTL.*;
import jp.co.ais.trans2.model.user.*;

/**
 * 会社間付替処理<グループ会計版>
 * 
 * @author AIS YF.CONG
 */
public class GroupCompanyTransfer extends TModel {

	/** SEP */
	public static final String KEY_SEP = "<>";

	/** true:BS勘定は計上会社コードの入力値を利用<Server> */
	public static boolean isBsUseKCompany = ServerConfig.isFlagOn("trans.slip.bs.use.kcompany");

	/** true:計上会社も管理１を継続して利用する<Server> */
	public static boolean isUseManager1ForGroup = ServerConfig.isFlagOn("trans.slip.group.use.knr1");

	/** ログイン会社コード */
	protected String baseCompanyCode;

	/** 会社間付替情報 */
	protected Map<String, TransferConfig> confMap = new TreeMap<String, TransferConfig>();

	/** 会社情報 */
	protected Map<String, Company> companyMap = new TreeMap<String, Company>();

	/** 通貨情報 */
	protected Map<String, Currency> currencyMap = new TreeMap<String, Currency>();

	/** レート情報 */
	protected Map<String, BigDecimal> rateMap = new TreeMap<String, BigDecimal>();

	/** 科目情報 */
	protected Map<String, Item> itemMap = new TreeMap<String, Item>();

	/** 管理１情報 */
	protected Map<String, Management1> knr1Map = new HashMap<String, Management1>();

	/** 決算伝票かどうか true:決算、false:通常 */
	protected boolean isClosingSlip = false;

	/** 計算ロジック */
	protected TCalculator calculator = TCalculatorFactory.getCalculator();

	/** true:画面値の計上部門を利用<Server> */
	public static boolean isUseOriginalDepartment = ServerConfig
		.isFlagOn("trans.slip.company.transfer.use.original.dep");

	/** 部門のマップ **/
	protected HashMap<String, String> depMap = new HashMap<String, String>();

	/**
	 * 会社間付替の処理
	 * 
	 * @param slip 伝票
	 * @return 付替え後の伝票リスト
	 * @throws TException
	 */
	public List<Slip> transfer(final Slip slip) throws TException {

		// 決算伝票かどうか true:決算、false:通常
		isClosingSlip = slip.isClosingSlip();

		SWK_HDR hdr = slip.getHeader();
		List<SWK_DTL> dtlList = slip.getDetails();

		// 基準会社コードset
		baseCompanyCode = hdr.getKAI_CODE();

		// 計上会社コードを纏める
		Map<String, Company> kkaiCodeList = new LinkedHashMap<String, Company>();

		for (SWK_DTL dtlKcomp : slip.getDetails()) {
			kkaiCodeList.put(dtlKcomp.getSWK_K_KAI_CODE(), dtlKcomp.getAppropriateCompany());
		}

		// 会社毎の伝票
		Map<String, Slip> slipMap = new HashMap<String, Slip>(kkaiCodeList.size());

		// 自社仕訳 ヘッダ
		slipMap.put(baseCompanyCode, new Slip(hdr));

		// 他社仕訳 ヘッダ
		for (Map.Entry<String, Company> entry : kkaiCodeList.entrySet()) {

			if (baseCompanyCode.equals(entry.getKey())) {
				continue;
			}

			// 付替先の伝票作成(ヘッダのみ)
			SWK_HDR thdr;
			if (entry.getValue() != null) {
				thdr = makeHeader(entry.getValue(), hdr);

			} else {
				thdr = makeHeader(entry.getKey(), hdr);
			}

			slipMap.put(entry.getKey(), new Slip(thdr));
		}

		// 明細
		List<SWK_DTL> insertDtlList = new ArrayList<SWK_DTL>();

		// 行番号は会社を跨って採番
		int gyoNo = 0;

		// DBから部門情報を取得する
		DepartmentManager manager = (DepartmentManager) getComponent(DepartmentManager.class);

		SlipManager sm = (SlipManager) getComponent(SlipManager.class);
		List<String> cmpKey = new ArrayList<String>();
		List<String> autoJornalKey = new ArrayList<String>();

		for (SWK_DTL dtl : dtlList) {

			String kKaiCode = dtl.getSWK_K_KAI_CODE();

			if (baseCompanyCode.equals(kKaiCode)) {
				// 自社明細 0(自社)
				SWK_DTL detail = makeSelfDtl(baseCompanyCode, dtl, ++gyoNo, 0);
				insertDtlList.add(detail);

			} else {

				// 消費税科目の情報取得
				if (!cmpKey.contains(kKaiCode)) {
					// 一度取得した会社は再検索しない
					cmpKey.add(kKaiCode);
					int[] types = new int[] { AutoJornalAccountType.PAY_TAX.value,
							AutoJornalAccountType.RECEIVE_TAX.value };
					List<AutoJornalAccount> autoJornalList = sm.getAutoJornalAccounts(kKaiCode, types);
					if (autoJornalList != null && !autoJornalList.isEmpty()) {
						for (AutoJornalAccount bean : autoJornalList) {
							String key = bean.getCompanyCode() + "<>" + bean.getItemCode() + "<>"
								+ bean.getSubItemCode() + "<>" + bean.getDetailItemCode();
							autoJornalKey.add(key);
						}
					}
				}

				String key = kKaiCode + "<>" + dtl.getSWK_KMK_CODE() + "<>" + dtl.getSWK_HKM_CODE() + "<>"
					+ dtl.getSWK_UKM_CODE();

				if (dtl.getSWK_AUTO_KBN() != AUTO_KBN.AUTO //
					|| (dtl.getSWK_AUTO_KBN() == AUTO_KBN.AUTO && !autoJornalKey.contains(key))) {
					// 自動仕訳区分が自動で消費税科目の場合は作成しない。

					// 付替元明細 1(自社)
					SWK_DTL detail = makeParentDtl(baseCompanyCode, dtl, ++gyoNo, 1);

					// 行摘要への追加対応
					if (isUseOriginalDepartment) {
						String depName = dtl.getSWK_DEP_NAME();

						if (Util.isNullOrEmpty(depName)) {
							String depCode = dtl.getSWK_DEP_CODE();
							if (depMap.containsKey(depCode)) {
								depName = depMap.get(depCode);
							} else {
								// 部門検索条件
								DepartmentSearchCondition condition = new DepartmentSearchCondition();
								condition.setCompanyCode(kKaiCode);
								condition.setCode(depCode);
								List<Department> depList = manager.get(condition);

								if (depList.size() > 0) {
									Department dep = depList.get(0);
									depMap.put(depCode, dep.getName());
									depName = dep.getName();
								}
							}
							dtl.setSWK_DEP_NAME(depName);
						}

						String tekiyo = Util.avoidNull(depName) + " " + detail.getSWK_GYO_TEK();
						tekiyo = StringUtil.leftBX(tekiyo, 80);
						detail.setSWK_GYO_TEK(tekiyo);
					}

					if (isBsUseKCompany) {
						detail.setBsDetail(null);
					}
					insertDtlList.add(detail);
				}

				// 付替先明細 2(他社)
				SWK_DTL tukemotodtl = makeChildDtl(kKaiCode, dtl, ++gyoNo, 2);
				insertDtlList.add(tukemotodtl);

				// 付替先明細(付替) 2(他社)
				if (dtl.getSWK_AUTO_KBN() != AUTO_KBN.AUTO //
					|| (dtl.getSWK_AUTO_KBN() == AUTO_KBN.AUTO && !autoJornalKey.contains(key))) {

					// 自動仕訳区分が自動で消費税科目の場合は作成しない。
					SWK_DTL tukeautodtl = makeChildAutoDtl(kKaiCode, tukemotodtl, ++gyoNo, baseCompanyCode);
					if (isBsUseKCompany) {
						tukeautodtl.setBsDetail(null);
					}
					insertDtlList.add(tukeautodtl);
				}
			}
		}

		// 振り分け
		for (SWK_DTL dtl : insertDtlList) {
			String compCode = dtl.getKAI_CODE();
			slipMap.get(compCode).addDetail(dtl);
		}

		// 消費税仕訳を起票するかをセット(元伝票をコピー)
		List<Slip> slips = new ArrayList(slipMap.values());
		for (Slip oneSlip : slips) {
			oneSlip.setJournalizedTax(slip.isJournalizedTax());
		}

		return slips;

	}

	/**
	 * 付替先会社のヘッダ作成
	 * 
	 * @param company 会社
	 * @param hdr 元ヘッダ
	 * @return 付替先会社のヘッダ
	 * @throws TException
	 */
	protected SWK_HDR makeHeader(Company company, SWK_HDR hdr) throws TException {

		SWK_HDR newHdr = makeHeader(company.getCode(), hdr);

		AccountConfig conf = company.getAccountConfig();
		newHdr.setKEY_CUR_CODE(conf.getKeyCurrency().getCode());
		newHdr.setKEY_CUR_DEC_KETA(conf.getKeyCurrency().getDecimalPoint());
		newHdr.setFUNC_CUR_CODE(conf.getFunctionalCurrency().getCode());
		newHdr.setFUNC_CUR_DEC_KETA(conf.getFunctionalCurrency().getDecimalPoint());

		return newHdr;
	}

	/**
	 * 付替先会社のヘッダ作成
	 * 
	 * @param companyCode 会社コード
	 * @param hdr 元ヘッダ
	 * @return 付替先会社のヘッダ
	 * @throws TException
	 */
	protected SWK_HDR makeHeader(String companyCode, SWK_HDR hdr) throws TException {

		SWK_HDR newHdr = hdr.clone();

		// 会社コード
		newHdr.setKAI_CODE(companyCode);

		// 排他フラグ
		newHdr.setSWK_SHR_KBN(0);

		// 会社間付替伝票区分
		newHdr.setSWK_TUKE_KBN(1);

		// 科目コード SWK_KMK_CODE NULL
		newHdr.setItem(null);

		// 計上部門コード SWK_DEP_CODE NULL
		newHdr.setDepartment(null);

		// 取引先コード SWK_TRI_CODE NULL
		newHdr.setCustomer(null);

		// 社員コード SWK_EMP_CODE NULL
		newHdr.setEmployee(null);

		// 摘要コード NULL
		newHdr.setSWK_TEK_CODE(null);

		// 入出金邦貨金額 SWK_KIN 0
		newHdr.setSWK_KIN(BigDecimal.ZERO);

		// 承認者 SWK_SYO_EMP_CODE NULL
		newHdr.setSWK_SYO_EMP_CODE(null);
		newHdr.setSWK_SYO_EMP_NAME(null);
		newHdr.setSWK_SYO_EMP_NAME_S(null);

		// 承認日 SWK_SYO_DATE NULL
		newHdr.setSWK_SYO_DATE(null);

		// ﾚｰﾄ SWK_CUR_RATE NULL
		newHdr.setSWK_CUR_RATE(null);

		// 入出金入力金額 SWK_IN_KIN NULL
		newHdr.setSWK_IN_KIN(null);

		// 支払区分 SWK_SIHA_KBN NULL
		newHdr.setSWK_SIHA_KBN(null);

		// 支払日 SWK_SIHA_DATE NULL
		newHdr.setSWK_SIHA_DATE(null);

		// 支払方法 SWK_HOH_CODE NULL
		newHdr.setPaymentMethod(null);

		// 保留区分 SWK_HORYU_KBN NULL(0:保留しない)
		newHdr.setSWK_HORYU_KBN(0);

		// 仮払金額 SWK_KARI_KIN NULL
		newHdr.setSWK_KARI_KIN(null);

		// 経費金額 SWK_KEIHI_KIN NULL
		newHdr.setSWK_KEIHI_KIN(null);

		// 支払金額(邦貨) SWK_SIHA_KIN NULL
		newHdr.setSWK_SIHA_KIN(null);

		// 取引先条件マスタの被仕向口座預金種目
		newHdr.setSWK_TJK_YKN_KBN(0);

		// 取引先条件マスタの被仕向口座番号
		newHdr.setSWK_TJK_YKN_NO(null);

		// 取引先条件銀行名(支払銀行名)
		newHdr.setSWK_TJK_BNK_NAME_S(null);

		// 取引先条件銀行口座名(支払銀行支店名)
		newHdr.setSWK_TJK_BNK_STN_NAME_S(null);

		// 仮払申請伝票番号 SWK_KARI_DR_DEN_NO NULL
		newHdr.setSWK_KARI_DR_DEN_NO(null);

		// 仮払精算伝票番号 SWK_KARI_CR_DEN_NO NULL
		newHdr.setSWK_KARI_CR_DEN_NO(null);

		// 支払決済区分 SWK_KESAI_KBN NULL(0:通常)
		newHdr.setSWK_KESAI_KBN(0);

		// 仮払計上部門コード SWK_KARI_DEP_CODE NULL
		newHdr.setSWK_KARI_DEP_CODE(null);
		newHdr.setSWK_KARI_DEP_NAME(null);
		newHdr.setSWK_KARI_DEP_NAME_S(null);

		// 取引先条件ｺｰﾄﾞ SWK_TJK_CODE NULL
		newHdr.setSWK_TJK_CODE(null);

		// 入力仮払金額 SWK_IN_KARI_KIN NULL
		newHdr.setSWK_IN_KARI_KIN(null);

		// 入力経費金額 SWK_IN_KEIHI_KIN NULL
		newHdr.setSWK_IN_KEIHI_KIN(null);

		// 入力支払金額 SWK_IN_SIHA_KIN NULL
		newHdr.setSWK_IN_SIHA_KIN(null);

		// 請求書通貨ｺｰﾄﾞ SWK_INV_CUR_CODE NULL
		newHdr.setSWK_INV_CUR_CODE(null);

		// 請求書通貨ﾚｰﾄ SWK_INV_CUR_RATE NULL
		newHdr.setSWK_INV_CUR_RATE(null);

		// 請求書通貨金額 SWK_INV_KIN NULL
		newHdr.setSWK_INV_KIN(null);

		// 銀行口座ｺｰﾄﾞ SWK_CBK_CODE NULL
		newHdr.setBankAccount(null);

		// 精算予定日 SWK_SSY_DATE NULL
		newHdr.setSWK_SSY_DATE(null);

		// 受付NO SWK_UTK_NO NULL
		newHdr.setSWK_UTK_NO(null);

		// 仮払通貨ｺｰﾄﾞ SWK_KARI_CUR_CODE NULL
		newHdr.setSWK_KARI_CUR_CODE(null);

		// 仮払通貨ﾚｰﾄ SWK_KARI_CUR_RATE NULL
		newHdr.setSWK_KARI_CUR_RATE(null);

		// 請求区分 SWK_SEI_KBN NULL
		newHdr.setSWK_SEI_KBN(null);

		// 入金予定日 SWK_AR_DATE NULL
		newHdr.setSWK_AR_DATE(null);

		// 通貨ｺｰﾄﾞ SWK_CUR_CODE NULL
		newHdr.setSWK_CUR_CODE(null);

		User user = getEmployee(companyCode, hdr.getSWK_IRAI_EMP_CODE());

		if (user != null) {
			// 受付部門
			newHdr.setSWK_UKE_DEP_CODE(user.getDepartment().getCode());
			newHdr.setSWK_UKE_DEP_NAME(user.getDepartment().getName());
			newHdr.setSWK_UKE_DEP_NAME_S(user.getDepartment().getNames());
			// 依頼部門
			newHdr.setSWK_IRAI_DEP_CODE(user.getDepartment().getCode());
			newHdr.setSWK_IRAI_DEP_NAME(user.getDepartment().getName());
			newHdr.setSWK_IRAI_DEP_NAME_S(user.getDepartment().getNames());
			// 依頼社員
			newHdr.setSWK_IRAI_EMP_CODE(user.getEmployee().getCode());
			newHdr.setSWK_IRAI_EMP_NAME(user.getEmployee().getName());
			newHdr.setSWK_IRAI_EMP_NAME_S(user.getEmployee().getNames());
		}

		return newHdr;
	}

	/**
	 * 仕訳ジャーナルの構築 (自社)
	 * 
	 * @param kaiCode 会社コード
	 * @param dtl 対象明細
	 * @param gyoNo 行番号
	 * @param swkTukeKbn 付替区分
	 * @return 作成仕訳
	 */
	protected SWK_DTL makeSelfDtl(String kaiCode, SWK_DTL dtl, int gyoNo, int swkTukeKbn) {

		SWK_DTL newDtl = dtl.clone();

		newDtl.setKAI_CODE(kaiCode);
		newDtl.setSWK_GYO_NO(gyoNo);
		newDtl.setSWK_TUKE_KBN(swkTukeKbn);

		return newDtl;
	}

	/**
	 * 仕訳ジャーナルの構築 (自社：親会社)
	 * 
	 * @param kaiCode 会社コード
	 * @param dtl 対象明細
	 * @param gyoNo 行番号
	 * @param swkTukeKbn 付替区分
	 * @return 作成仕訳
	 * @throws TException
	 */
	protected SWK_DTL makeParentDtl(String kaiCode, SWK_DTL dtl, int gyoNo, int swkTukeKbn) throws TException {

		// 会社間付替マスタから取得
		String fromCompany = kaiCode;
		String toCompany = dtl.getSWK_K_KAI_CODE();
		TransferConfig conf = getConfig(fromCompany, toCompany);

		SWK_DTL newDtl = dtl.clone();

		// 会社コード ＝ ログイン会社コード
		newDtl.setKAI_CODE(kaiCode);

		// 行番号 ＝ 対象行番号
		newDtl.setSWK_GYO_NO(gyoNo);

		// 科目コード ＝ 付替元の科目コード
		// 補助科目コード ＝ 付替元の補助科目コード
		// 内訳科目コード ＝ 付替元の内訳科目コード
		// 計上部門コード ＝ 付替元の計上部門コード
		// 取引先コード ＝ 付替元の取引先コード
		newDtl.setSWK_KMK_CODE(conf.getTransferItemCode());
		newDtl.setSWK_KMK_NAME(conf.getTransferItemName());
		newDtl.setSWK_KMK_NAME_S(conf.getTransferItemNames());
		newDtl.setSWK_HKM_CODE(conf.getTransferSubItemCode());
		newDtl.setSWK_HKM_NAME(conf.getTransferSubItemName());
		newDtl.setSWK_HKM_NAME_S(conf.getTransferSubItemNames());
		newDtl.setSWK_UKM_CODE(conf.getTransferDetailItemCode());
		newDtl.setSWK_UKM_NAME(conf.getTransferDetailItemName());
		newDtl.setSWK_UKM_NAME_S(conf.getTransferDetailItemNames());
		newDtl.setSWK_DEP_CODE(conf.getTransferDepertmentCode());
		newDtl.setSWK_DEP_NAME(conf.getTransferDepertmentName());
		newDtl.setSWK_DEP_NAME_S(conf.getTransferDepertmentNames());

		// 科目情報取得
		Item item = getItem(kaiCode, conf.getTransferItemCode(), conf.getTransferSubItemCode(),
			conf.getTransferDetailItemCode());

		// 科目設定フラグがOFFの場合、取引先設定不要
		if (item != null && item.getPreferedItem() != null && item.getPreferedItem().isUseCustomer()) {
			newDtl.setSWK_TRI_CODE(conf.getTransferCustomerCode());
			newDtl.setSWK_TRI_NAME(conf.getTransferCustomerName());
			newDtl.setSWK_TRI_NAME_S(conf.getTransferCustomerNames());
		} else {
			newDtl.setCustomer(null);
		}

		newDtl.setEmployee(null);
		newDtl.setManagement1(null);
		newDtl.setManagement2(null);
		newDtl.setManagement3(null);
		newDtl.setManagement4(null);
		newDtl.setManagement5(null);
		newDtl.setManagement6(null);
		newDtl.setSWK_HM_1(null);
		newDtl.setSWK_HM_2(null);
		newDtl.setSWK_HM_3(null);

		if (isUseManager1ForGroup && item != null && item.getPreferedItem() != null
			&& item.getPreferedItem().isUseManagement1()) {
			Management1 mng1 = getManagement1(newDtl.getKAI_CODE(), dtl.getSWK_KNR_CODE_1());
			newDtl.setManagement1(mng1);
		}

		// 入力金額（税込み）:（入力金額（税抜き）+入力消費税額）
		newDtl.setSWK_IN_KIN(dtl.getSWK_IN_KIN().add(dtl.getSWK_IN_ZEI_KIN()));

		// 邦貨金額（税込み）:（邦貨金額（税抜き）+邦貨消費税額）
		newDtl.setSWK_KIN(dtl.getSWK_KIN().add(dtl.getSWK_ZEI_KIN()));

		// 入力消費税額 ＝ 0
		newDtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);

		// 邦貨消費税額 ＝ 0
		newDtl.setSWK_ZEI_KIN(BigDecimal.ZERO);

		// 税区分 ＝ 2:非課税
		newDtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE);

		// 消費税コード ＝ null
		newDtl.setSWK_ZEI_CODE(null);
		newDtl.setSWK_ZEI_NAME(null);
		newDtl.setSWK_ZEI_NAME_S(null);

		// 税率 ＝ 0
		newDtl.setSWK_ZEI_RATE(BigDecimal.ZERO);

		// 自動仕訳区分 ＝ 1:自動
		newDtl.setSWK_AUTO_KBN(SWK_DTL.AUTO_KBN.AUTO);

		// 会社間付替伝票区分 ＝ 1:付替元自動仕訳行
		newDtl.setSWK_TUKE_KBN(swkTukeKbn);

		newDtl.setSWK_KESI_KBN(KESI_KBN.NOMAL);
		newDtl.setSWK_KESI_DATE(null);
		newDtl.setSWK_KESI_DEN_NO(null);
		newDtl.setSWK_SOUSAI_DATE(null);
		newDtl.setSWK_SOUSAI_DEN_NO(null);
		newDtl.setSWK_SOUSAI_GYO_NO(null);

		return newDtl;
	}

	/**
	 * 仕訳ジャーナルの構築(他計上会社：子会社)
	 * 
	 * @param kaiCode 会社コード
	 * @param dtl 対象明細
	 * @param gyoNo 行番号
	 * @param tukeKbn 付替区分
	 * @return 作成仕訳
	 */
	protected SWK_DTL makeChildDtl(String kaiCode, SWK_DTL dtl, int gyoNo, int tukeKbn) {

		SWK_DTL newDtl = dtl.clone();

		// 会社コード ＝ 計上会社コード
		newDtl.setKAI_CODE(kaiCode);

		// 行番号 ＝ ①の行番号＋１
		newDtl.setSWK_GYO_NO(gyoNo);

		// 日産専用船（基軸通貨JPY）
		String baseCurrencyCode = getCompany().getAccountConfig().getKeyCurrency().getCode();

		// 子会社情報
		Company company = getCompany(kaiCode);

		// 子会社基軸通貨コード
		String keyCurrencyCode = company.getAccountConfig().getKeyCurrency().getCode();

		// 入力通貨コード
		String currencyCode = dtl.getSWK_CUR_CODE();

		// 入力通貨コード
		Currency currency = getCurrency(kaiCode, currencyCode);

		// レート ＝ 子会社のレートマスタに親会社の本邦通貨に対するレート、抽出基準日は伝票日付の入力通貨レート
		BigDecimal rate = null;

		// 入力金額
		BigDecimal foreignAmount = dtl.getSWK_IN_KIN();
		// 邦貨金額
		BigDecimal amount = null;
		// 入力消費税額 ＝ 入力仕訳の入力消費税額
		BigDecimal foreignTaxAmount = dtl.getSWK_IN_ZEI_KIN();
		// 邦貨消費税額
		BigDecimal taxAmount = null;

		// レート
		if (keyCurrencyCode.equals(baseCurrencyCode)) {
			// 親会社の基軸通貨＝子会社の基軸通貨の場合

			// レート＝入力レート
			rate = dtl.getSWK_CUR_RATE();
		} else {
			// 親会社の基軸通貨＜＞子会社の基軸通貨の場合

			// レート＝子会社のレートマスタに入力通貨に対するレート
			rate = getRate(kaiCode, currencyCode, newDtl);
		}

		// 邦貨金額
		if (keyCurrencyCode.equals(baseCurrencyCode)) {

			// ０.親会社の基軸通貨＝海外子会社の基軸通貨の場合

			// 邦貨金額＝付替仕訳作成元の邦貨金額
			amount = dtl.getSWK_KIN();

		} else if (currencyCode.equals(keyCurrencyCode)) {

			// １.入力伝票仕訳の通貨＝海外子会社の基軸通貨の場合

			// 邦貨金額＝付替仕訳作成元の入力金額
			amount = foreignAmount;

		} else {

			// ２.入力伝票仕訳の通貨＜＞海外子会社の基軸通貨の場合、★入力金額

			// 邦貨金額＝付替仕訳作成元の入力金額／レート
			amount = convertKeyAmount(foreignAmount, rate, company, currency);

		}

		// 邦貨消費税額＝付替仕訳作成元の入力消費税額／レート
		taxAmount = convertKeyAmount(foreignTaxAmount, rate, company, currency);

		// 仕訳へ反映

		// 通貨コード
		newDtl.setSWK_CUR_CODE(currencyCode);

		// レート
		newDtl.setSWK_CUR_RATE(rate);

		// 入力金額
		newDtl.setSWK_IN_KIN(foreignAmount);

		// 邦貨金額
		newDtl.setSWK_KIN(amount);

		// 入力消費税額
		newDtl.setSWK_IN_ZEI_KIN(foreignTaxAmount);

		// 邦貨消費税額
		newDtl.setSWK_ZEI_KIN(taxAmount);

		// 会社間付替伝票区分 ＝ 2:付替先自動仕訳行
		newDtl.setSWK_TUKE_KBN(tukeKbn);

		// 自動仕訳区分 ＝ 入力仕訳の自動仕訳区分
		newDtl.setSWK_AUTO_KBN(dtl.getSWK_AUTO_KBN());

		newDtl.setSWK_KESI_KBN(KESI_KBN.NOMAL);
		newDtl.setSWK_KESI_DATE(null);
		newDtl.setSWK_KESI_DEN_NO(null);
		newDtl.setSWK_SOUSAI_DATE(null);
		newDtl.setSWK_SOUSAI_DEN_NO(null);
		newDtl.setSWK_SOUSAI_GYO_NO(null);

		return newDtl;
	}

	/**
	 * 仕訳ジャーナルの構築(他計上会社：子会社自動仕訳)
	 * 
	 * @param kaiCode 会社コード
	 * @param dtl 対象明細
	 * @param gyoNo 行番号
	 * @param toCompanyCode 親会社コード
	 * @return 作成仕訳
	 * @throws TException
	 */
	protected SWK_DTL makeChildAutoDtl(String kaiCode, SWK_DTL dtl, int gyoNo, String toCompanyCode) throws TException {

		// 会社間付替マスタから取得
		String fromCompany = kaiCode;
		String toCompany = toCompanyCode;
		TransferConfig conf = getConfig(fromCompany, toCompany);

		SWK_DTL newDtl = dtl.clone();

		// 行番号 ＝ ②の行番号＋１
		newDtl.setSWK_GYO_NO(gyoNo);

		// 貸借区分 ＝ 逆にする
		if (!baseCompanyCode.equals(kaiCode)) {
			newDtl.reverseDC();
		}

		// 入力金額（税込み）:（入力金額（税抜き）+入力消費税額）
		newDtl.setSWK_IN_KIN(dtl.getSWK_IN_KIN().add(dtl.getSWK_IN_ZEI_KIN()));

		// 邦貨金額（税込み）:（邦貨金額（税抜き）+邦貨消費税額）
		newDtl.setSWK_KIN(dtl.getSWK_KIN().add(dtl.getSWK_ZEI_KIN()));

		// 入力消費税額 ＝ 0
		newDtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);

		// 邦貨消費税額 ＝ 0
		newDtl.setSWK_ZEI_KIN(BigDecimal.ZERO);

		// 税区分 ＝ 2:非課税
		newDtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE);

		// 消費税コード ＝ null
		newDtl.setSWK_ZEI_CODE(null);
		newDtl.setSWK_ZEI_NAME(null);
		newDtl.setSWK_ZEI_NAME_S(null);

		// 税率 ＝ 0
		newDtl.setSWK_ZEI_RATE(BigDecimal.ZERO);

		// 科目コード ＝ 付替先の科目コード
		// 補助科目コード ＝ 付替先の補助科目コード
		// 内訳科目コード ＝ 付替先の内訳科目コード
		// 計上部門コード ＝ 付替先の計上部門コード
		// 取引先コード ＝ 付替先の取引先コード
		newDtl.setSWK_KMK_CODE(conf.getTransferItemCode());
		newDtl.setSWK_KMK_NAME(conf.getTransferItemName());
		newDtl.setSWK_KMK_NAME_S(conf.getTransferItemNames());
		newDtl.setSWK_HKM_CODE(conf.getTransferSubItemCode());
		newDtl.setSWK_HKM_NAME(conf.getTransferSubItemName());
		newDtl.setSWK_HKM_NAME_S(conf.getTransferSubItemNames());
		newDtl.setSWK_UKM_CODE(conf.getTransferDetailItemCode());
		newDtl.setSWK_UKM_NAME(conf.getTransferDetailItemName());
		newDtl.setSWK_UKM_NAME_S(conf.getTransferDetailItemNames());
		newDtl.setSWK_DEP_CODE(conf.getTransferDepertmentCode());
		newDtl.setSWK_DEP_NAME(conf.getTransferDepertmentName());
		newDtl.setSWK_DEP_NAME_S(conf.getTransferDepertmentNames());

		// 科目情報取得
		Item item = getItem(kaiCode, conf.getTransferItemCode(), conf.getTransferSubItemCode(),
			conf.getTransferDetailItemCode());

		// 科目設定フラグがOFFの場合、取引先設定不要
		if (item != null && item.getPreferedItem() != null && item.getPreferedItem().isUseCustomer()) {
			newDtl.setSWK_TRI_CODE(conf.getTransferCustomerCode());
			newDtl.setSWK_TRI_NAME(conf.getTransferCustomerName());
			newDtl.setSWK_TRI_NAME_S(conf.getTransferCustomerNames());
		} else {
			newDtl.setCustomer(null);
		}

		newDtl.setEmployee(null);
		newDtl.setManagement1(null);
		newDtl.setManagement2(null);
		newDtl.setManagement3(null);
		newDtl.setManagement4(null);
		newDtl.setManagement5(null);
		newDtl.setManagement6(null);
		newDtl.setSWK_HM_1(null);
		newDtl.setSWK_HM_2(null);
		newDtl.setSWK_HM_3(null);

		if (isUseManager1ForGroup && item != null && item.getPreferedItem() != null
			&& item.getPreferedItem().isUseManagement1()) {
			Management1 mng1 = getManagement1(newDtl.getKAI_CODE(), dtl.getSWK_KNR_CODE_1());
			newDtl.setManagement1(mng1);
		}

		// 自動仕訳区分 ＝ 1:自動
		newDtl.setSWK_AUTO_KBN(SWK_DTL.AUTO_KBN.AUTO);

		// 計上会社コード ＝ ログイン会社コード
		newDtl.setSWK_K_KAI_CODE(baseCompanyCode);

		newDtl.setSWK_KESI_KBN(KESI_KBN.NOMAL);
		newDtl.setSWK_KESI_DATE(null);
		newDtl.setSWK_KESI_DEN_NO(null);
		newDtl.setSWK_SOUSAI_DATE(null);
		newDtl.setSWK_SOUSAI_DEN_NO(null);
		newDtl.setSWK_SOUSAI_GYO_NO(null);

		return newDtl;

	}

	/**
	 * @param companyCode
	 * @param knr1Code
	 * @return 管理１
	 * @throws TException
	 */
	protected Management1 getManagement1(String companyCode, String knr1Code) throws TException {

		if (Util.isNullOrEmpty(companyCode) || Util.isNullOrEmpty(knr1Code)) {
			return null;
		}

		String key = companyCode + "<>" + knr1Code;

		if (knr1Map.containsKey(key)) {
			return knr1Map.get(key);
		}

		Management1Manager mm = (Management1Manager) getComponent(Management1Manager.class);
		Management1SearchCondition condition = new Management1SearchCondition();
		condition.setCompanyCode(companyCode);
		condition.setCode(knr1Code);
		List<Management1> list = mm.get(condition);
		Management1 bean = null;

		if (list != null && !list.isEmpty()) {
			bean = list.get(0);
		}

		knr1Map.put(key, bean);

		return bean;
	}

	/**
	 * 会社情報の取得
	 * 
	 * @param companyCode 会社コード
	 * @return 会社情報
	 */
	protected Company getCompany(String companyCode) {

		String key = companyCode;

		Company company = companyMap.get(key);

		if (company == null) {
			CompanyManager manager = (CompanyManager) getComponent(CompanyManager.class);
			CompanySearchCondition condition = new CompanySearchCondition();
			condition.setCode(companyCode);

			try {
				List<Company> list = manager.get(condition);

				if (list != null && !list.isEmpty()) {
					companyMap.put(key, list.get(0));
				}

				company = companyMap.get(key);

				companyMap.put(key, company);

			} catch (TException e) {
				// なし
			}
		}

		return company;
	}

	/**
	 * 通貨の取得
	 * 
	 * @param companyCode 会社コード
	 * @param currencyCode 通貨コード
	 * @return 通貨
	 */
	protected Currency getCurrency(String companyCode, String currencyCode) {

		String key = companyCode + "<>" + currencyCode;

		Currency currency = currencyMap.get(key);

		if (currency == null) {

			try {
				CurrencyManager manager = (CurrencyManager) getComponent(CurrencyManager.class);

				CurrencySearchCondition condition = new CurrencySearchCondition();
				condition.setCompanyCode(companyCode);
				condition.setCode(currencyCode);

				List<Currency> list = manager.get(condition);

				if (list != null && list.size() > 0) {
					currency = list.get(0);
				}

				currencyMap.put(key, currency);

			} catch (TException e) {
				// なし
			}
		}

		return currency;
	}

	/**
	 * レートの取得
	 * 
	 * @param companyCode 会社コード
	 * @param currencyCode 通貨コード
	 * @param newDtl 仕訳ジャーナル
	 * @return レート
	 */
	protected BigDecimal getRate(String companyCode, String currencyCode, SWK_DTL newDtl) {

		Date slipDate = newDtl.getHAS_DATE() != null ? newDtl.getHAS_DATE() : newDtl.getSWK_DEN_DATE();

		String key = companyCode + "<>" + currencyCode + "<>" + DateUtil.toYMDPlainString(slipDate) + "<>"
			+ newDtl.getAccountBook().value;

		BigDecimal rate = rateMap.get(key);

		if (rate == null) {

			try {
				RateManager manager = (RateManager) getComponent(RateManager.class);
				String tableName = "";

				if (isClosingSlip) {
					tableName = "RATE_KSN_MST";
				} else {
					tableName = "RATE_MST";
				}
				rate = manager.getRate(companyCode, currencyCode, slipDate, tableName);

				if (rate == null) {
					rate = BigDecimal.ONE;
				}

				rateMap.put(key, rate);

			} catch (TException e) {
				// なし
			}
		}

		return rate;
	}

	/**
	 * 科目情報の取得
	 * 
	 * @param companyCode 会社ｺｰﾄﾞ
	 * @param itemCode 科目コード
	 * @param subCode 補助科目コード
	 * @param detailCode 内訳科目コード
	 * @return 科目情報
	 * @throws TException
	 */
	public Item getItem(String companyCode, String itemCode, String subCode, String detailCode) throws TException {

		if (Util.isNullOrEmpty(companyCode) || Util.isNullOrEmpty(itemCode)) {
			return null;
		}

		String key = companyCode + KEY_SEP + itemCode + KEY_SEP + subCode + KEY_SEP + detailCode;

		if (itemMap.containsKey(key)) {

			return itemMap.get(key);

		} else {

			// 科目情報取得
			ItemManager manager = (ItemManager) getComponent(ItemManager.class);
			Item bean = manager.getItem(companyCode, itemCode, subCode, detailCode);

			itemMap.put(key, bean);

			return bean;
		}

	}

	/**
	 * 入力情報を元に基軸金額に換算
	 * 
	 * @param amount 入力金額
	 * @param rate レート
	 * @param company 対象会社
	 * @param currency 入力通貨
	 * @return 基軸通貨金額
	 */
	protected BigDecimal convertKeyAmount(BigDecimal amount, BigDecimal rate, Company company, Currency currency) {

		if (DecimalUtil.isNullOrZero(amount)) {
			return BigDecimal.ZERO;
		}

		if (company == null || currency == null) {
			return BigDecimal.ZERO;
		}

		// 会計系設定
		AccountConfig conf = company.getAccountConfig();

		if (conf == null) {
			return BigDecimal.ZERO;
		}

		// 基軸通貨
		Currency keyCurrency = conf.getKeyCurrency();

		if (keyCurrency == null) {
			return BigDecimal.ZERO;
		}

		// 換算
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(company.getAccountConfig().getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(keyCurrency.getDecimalPoint());
		param.setForeignAmount(amount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
	}

	/**
	 * 付替情報の取得
	 * 
	 * @param fromCompany
	 * @param toCompany
	 * @return 付替情報
	 * @throws TException
	 */
	protected TransferConfig getConfig(String fromCompany, String toCompany) throws TException {

		TransferConfig conf = confMap.get(fromCompany + "<>" + toCompany);

		if (conf == null) {
			CompanyManager manager = (CompanyManager) getComponent(CompanyManager.class);
			List<TransferConfig> list = manager.getTransferConfig(fromCompany, toCompany);

			if (list == null || list.isEmpty()) {
				throw new TRuntimeException("W00107");
			}

			for (TransferConfig bean : list) {
				String from = bean.getCompanyCode();
				String to = bean.getTransferCompanyCode();

				confMap.put(from + "<>" + to, bean);
			}

			conf = confMap.get(fromCompany + "<>" + toCompany);
		}

		return conf;
	}

	/**
	 * ユーザ情報取得
	 * 
	 * @param companyCode 会社コード
	 * @param empCode 社員コード
	 * @return ユーザマスタ
	 * @throws TException
	 */
	protected User getEmployee(String companyCode, String empCode) throws TException {
		UserManager manager = (UserManager) getComponent(UserManager.class);
		UserSearchCondition condition = new UserSearchCondition();
		condition.setCompanyCode(companyCode);
		condition.setEmployeeCode(empCode);
		List<User> users = manager.get(condition);

		// 社員登録されていないユーザを除外
		for (int i = users.size() - 1; 0 <= i; i--) {
			User user = users.get(i);
			if (Util.isNullOrEmpty(user.getEmployee().getName())) {
				users.remove(i);
			}
		}

		if (users.isEmpty()) {
			throw new TException("I00157", companyCode, empCode);// 会社[{0}]のユーザマスタ、または社員マスタ[{1}]が設定されていません。
		}

		// 通常運用では無いが、複数ユーザに社員コードが割り当てられている場合は、ログインユーザコードと同一にする
		String userCode = getUserCode();
		for (User user : users) {
			if (userCode.equals(user.getCode())) {
				return user;
			}
		}

		return users.get(0);
	}
}
