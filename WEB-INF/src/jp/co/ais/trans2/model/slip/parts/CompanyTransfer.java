package jp.co.ais.trans2.model.slip.parts;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.slip.SWK_DTL.KESI_KBN;
import jp.co.ais.trans2.model.user.*;

/**
 * 会社間付替処理
 */
public class CompanyTransfer extends TModel {

	/** true:BS勘定は計上会社コードの入力値を利用<Server> */
	public static boolean isBsUseKCompany = ServerConfig.isFlagOn("trans.slip.bs.use.kcompany");

	/** ログイン会社コード */
	protected String baseCompanyCode;

	/** 会社間付替情報 */
	protected Map<String, TransferConfig> confMap = new TreeMap<String, TransferConfig>();

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

		boolean isJournalizedTax = slip.isJournalizedTax();

		// DBから部門情報を取得する
		DepartmentManager manager = (DepartmentManager) getComponent(DepartmentManager.class);

		for (SWK_DTL dtl : dtlList) {

			String kKaiCode = dtl.getSWK_K_KAI_CODE();

			if (baseCompanyCode.equals(kKaiCode)) {
				// 自社明細 0(自社)
				insertDtlList.add(makeSelfDtl(baseCompanyCode, dtl, ++gyoNo, 0));

			} else {
				// 付替元明細 1(自社)
				SWK_DTL newdtl = makeOtherDtl(baseCompanyCode, dtl, ++gyoNo, 1, isJournalizedTax);

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

					String tekiyo = Util.avoidNull(depName) + " " + newdtl.getSWK_GYO_TEK();
					tekiyo = StringUtil.leftBX(tekiyo, 80);
					newdtl.setSWK_GYO_TEK(tekiyo);
				}

				if (isBsUseKCompany) {
					newdtl.setBsDetail(null);
				}
				insertDtlList.add(newdtl);

				// 付替先明細 2(他社)
				newdtl = makeSelfDtl(kKaiCode, dtl, ++gyoNo, 2);
				insertDtlList.add(newdtl);

				// 付替先明細(付替) 2(他社)
				SWK_DTL tukeDtl = dtl.clone();
				tukeDtl.setSWK_K_KAI_CODE(baseCompanyCode);
				if (isBsUseKCompany) {
					tukeDtl.setBsDetail(null);
				}
				insertDtlList.add(makeOtherDtl(kKaiCode, tukeDtl, ++gyoNo, 2, isJournalizedTax));
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
	 * 仕訳ジャーナルの構築(他計上会社)
	 * 
	 * @param kaiCode 会社コード
	 * @param dtl 対象明細
	 * @param gyoNo 行番号
	 * @param tukeKbn 付替区分
	 * @param isJournalizedTax 消費税
	 * @return 作成仕訳
	 * @throws TException
	 */
	protected SWK_DTL makeOtherDtl(String kaiCode, SWK_DTL dtl, int gyoNo, int tukeKbn, boolean isJournalizedTax)
		throws TException {

		// 会社間付替マスタから取得
		String fromCompany = kaiCode;
		String toCompany = dtl.getSWK_K_KAI_CODE();
		TransferConfig conf = getConfig(fromCompany, toCompany);

		SWK_DTL newDtl = dtl.clone();

		newDtl.setKAI_CODE(kaiCode);
		newDtl.setSWK_GYO_NO(gyoNo);

		if (!baseCompanyCode.equals(kaiCode)) {
			if (dtl.getSWK_DC_KBN() == 0) {
				newDtl.setSWK_DC_KBN(1);

			} else {
				newDtl.setSWK_DC_KBN(0);
			}
		}

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
		newDtl.setSWK_TRI_CODE(conf.getTransferCustomerCode());
		newDtl.setSWK_TRI_NAME(conf.getTransferCustomerName());
		newDtl.setSWK_TRI_NAME_S(conf.getTransferCustomerNames());

		if (isJournalizedTax) {
			newDtl.setSWK_KIN(newDtl.getSWK_KIN());

		} else {
			newDtl.setSWK_KIN(newDtl.getSWK_KIN().add(newDtl.getSWK_ZEI_KIN()));
		}
		newDtl.setSWK_ZEI_KBN(2);
		newDtl.setSWK_ZEI_KIN(BigDecimal.ZERO);
		newDtl.setSWK_ZEI_RATE(BigDecimal.ZERO);
		newDtl.setSWK_ZEI_CODE(null);
		newDtl.setSWK_ZEI_NAME(null);
		newDtl.setSWK_ZEI_NAME_S(null);

		if (isJournalizedTax) {
			newDtl.setSWK_IN_KIN(newDtl.getSWK_IN_KIN());

		} else {
			newDtl.setSWK_IN_KIN(newDtl.getSWK_IN_KIN().add(newDtl.getSWK_IN_ZEI_KIN()));
		}
		newDtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);

		newDtl.setSWK_AUTO_KBN(1);
		newDtl.setSWK_TUKE_KBN(tukeKbn);

		newDtl.setSWK_KESI_KBN(KESI_KBN.NOMAL);
		newDtl.setSWK_KESI_DATE(null);
		newDtl.setSWK_KESI_DEN_NO(null);
		newDtl.setSWK_SOUSAI_DATE(null);
		newDtl.setSWK_SOUSAI_DEN_NO(null);
		newDtl.setSWK_SOUSAI_GYO_NO(null);

		return newDtl;
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
