package jp.co.ais.trans2.model.slip.parts;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * 消費税関連
 */
public class TaxJournalIssuer extends TModel {

	/** % */
	public static final BigDecimal NUM_PERCENT = new BigDecimal("0.01");

	/** 100,000,000,000,000,000 */
	public static final BigDecimal NUM_MAX_PAD = new BigDecimal("100000000000000000"); // 18桁

	/** 通貨タイプ */
	protected CurrencyType currencyType = CurrencyType.KEY;

	/** 消費税マネージャ */
	protected ConsumptionTaxManager taxManager = (ConsumptionTaxManager) getComponent(ConsumptionTaxManager.class);

	/** 消費税マップ */
	protected Map<String, ConsumptionTax> taxMap = new HashMap<String, ConsumptionTax>();

	/** 消費税条件 */
	protected ConsumptionTaxSearchCondition param = new ConsumptionTaxSearchCondition();

	/** 消費税勘定行(仮受) */
	protected AutoJornalAccount kariukeKmk;

	/** 消費税勘定行(仮払)) */
	protected AutoJornalAccount karibaraiKmk;

	/** 伝票 */
	protected Slip slip;

	/** 伝票種別 */
	protected SlipType slipType;

	/**
	 * 通貨タイプ
	 * 
	 * @param currencyType 通貨タイプ
	 */
	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	/**
	 * 伝票に消費税仕訳を追加。<br>
	 * 本体勘定の消費税コード、消費税額をもとに<br>
	 * 消費税勘定を作成し、伝票にセット。
	 * 
	 * @param motoSlip 伝票
	 * @param maxRowNum 最大行番号
	 * @return 最大行番号
	 * @throws TException
	 */
	public int addJournal(Slip motoSlip, int maxRowNum) throws TException {

		// 通貨ごとに分ける
		Map<String, List<SWK_DTL>> map = splitCurrencyDetails(motoSlip.getDetails());

		// 対象が無ければ番号を返して終了
		if (map.isEmpty()) {
			return maxRowNum;
		}

		// 伝票
		this.slip = motoSlip;

		// 会社コード
		String companyCode = motoSlip.getCompanyCode();

		// 消費税計算用パラメータに会社コードセット
		this.param.setCompanyCode(companyCode);

		// 消費税勘定行(仮受)
		this.kariukeKmk = getAutoItem(companyCode, AutoJornalAccountType.RECEIVE_TAX); // 仮受消費税科目

		// 消費税勘定行(仮払)
		this.karibaraiKmk = getAutoItem(companyCode, AutoJornalAccountType.PAY_TAX); // 仮払消費税科目

		this.slipType = getSlipType(slip.getSlipType());

		for (Map.Entry<String, List<SWK_DTL>> entry : map.entrySet()) {
			maxRowNum = addTaxDetails(entry.getKey(), entry.getValue(), maxRowNum);
		}

		return maxRowNum;
	}

	/**
	 * 通貨ごとに分ける
	 * 
	 * @param list
	 * @return 通貨ごとに分けるマップ(key=通貨コード)
	 */
	public static Map<String, List<SWK_DTL>> splitCurrencyDetails(List<SWK_DTL> list) {

		// 通貨ごとに分ける
		Map<String, List<SWK_DTL>> map = new LinkedHashMap<String, List<SWK_DTL>>();

		for (SWK_DTL dtl : list) {
			// 消費税発生がない場合はスキップ
			if (DecimalUtil.isNullOrZero(dtl.getSWK_ZEI_KIN())) {
				continue;
			}

			String currency = dtl.getSWK_CUR_CODE();

			List<SWK_DTL> dtlList = map.get(currency);

			if (dtlList == null) {
				dtlList = new ArrayList<SWK_DTL>();
				map.put(currency, dtlList);
			}

			dtlList.add(dtl);
		}

		return map;
	}

	/**
	 * 処理単位ごとに分ける
	 * 
	 * @param taxDtlMap 処理単位ごとに分ける
	 * @param oriMap 処理単位ごとに分ける
	 * @param list 対象仕訳リスト
	 * @throws TException
	 */
	public void initTaxOccurMap(Map<String, SWK_DTL> taxDtlMap, Map<String, List<SWK_DTL>> oriMap, List<SWK_DTL> list)
		throws TException {
		for (SWK_DTL dtl : list) {

			ConsumptionTax tax = dtl.getTax();

			if (tax == null) {
				tax = getTax(dtl.getSWK_K_KAI_CODE(), dtl.getSWK_ZEI_CODE()); // 再取得
			}

			// 2023INVOICE制度対応
			if (Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
				continue;
			}
			// keySet:税区分(NULL考慮)+貸借区分+消費税CDの税区分+消費税コード+通貨
			String key = DecimalUtil.toBigDecimalNVL(dtl.getSWK_ZEI_KBN()) + "<>" + dtl.getSWK_DC_KBN() + "<>"
				+ tax.getTaxType().value + "<>" + dtl.getSWK_ZEI_CODE() + "<>" + dtl.getSWK_CUR_CODE();
			SWK_DTL taxDtl = null;
			List<SWK_DTL> oriSortList = null;

			if (taxDtlMap.containsKey(key)) {
				taxDtl = taxDtlMap.get(key);
				oriSortList = oriMap.get(key);

			} else {
				taxDtl = new SWK_DTL();
				taxDtl.setAddonInfo(new SlipDetailAddonInfo());
				taxDtl.setSWK_ZEI_KBN(dtl.getSWK_ZEI_KBN());
				taxDtl.setSWK_DC_KBN(dtl.getSWK_DC_KBN());
				taxDtl.setSWK_CUR_CODE(dtl.getSWK_CUR_CODE());
				taxDtl.setSWK_ZEI_CODE(dtl.getSWK_ZEI_CODE());
				taxDtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
				taxDtl.setSWK_ZEI_KIN(BigDecimal.ZERO);
				taxDtl.setTax(tax);

				// Mapセット
				taxDtlMap.put(key, taxDtl);

				oriSortList = new ArrayList<SWK_DTL>(); // 金額の降順＞行番号の昇順
				oriMap.put(key, oriSortList);
			}
			// 消費税額加算
			taxDtl.setSWK_IN_ZEI_KIN(add(taxDtl.getSWK_IN_ZEI_KIN(), dtl.getSWK_IN_ZEI_KIN()));
			taxDtl.setSWK_ZEI_KIN(add(taxDtl.getSWK_ZEI_KIN(), dtl.getSWK_ZEI_KIN()));

			// 元消費税額を持つ
			taxDtl.getAddonInfo().setValue("ORI_SWK_IN_ZEI_KIN", taxDtl.getSWK_IN_ZEI_KIN());
			taxDtl.getAddonInfo().setValue("ORI_SWK_ZEI_KIN", taxDtl.getSWK_ZEI_KIN());

			// 元仕訳のクローンも持つ
			oriSortList.add(dtl.clone());
		}
	}

	/**
	 * @param code
	 * @return 伝票種別
	 * @throws TException
	 */
	protected SlipType getSlipType(String code) throws TException {
		if (Util.isNullOrEmpty(code)) {
			return null;
		}

		SlipTypeManager stm = get(SlipTypeManager.class);
		SlipType bean = stm.get(getCompanyCode(), code);

		return bean;
	}

	/**
	 * 伝票に消費税仕訳を追加します。<br>
	 * 本体勘定の消費税コード、消費税額をもとに<br>
	 * 消費税勘定を作成し、伝票にセットします。
	 * 
	 * @param currency
	 * @param dtlList
	 * @param maxRowNum 最大行番号
	 * @return 最大行番号
	 * @throws TException
	 */
	public int addTaxDetails(String currency, List<SWK_DTL> dtlList, int maxRowNum) throws TException {

		// 伝票種別が判断対象
		boolean isInvoice = getCompany().isCMP_INV_CHK_FLG() && slipType != null && slipType.isINV_SYS_FLG();
		Currency cur = getCurrency(currency);

		// 売上課税(仮受)
		SWK_DTL uriDR = createUriDrDetail(cur);

		SWK_DTL uriCR = uriDR.clone(); // 貸方
		uriCR.setDC(Dc.CREDIT);

		// 仕入課税(仮払)
		SWK_DTL sireDR = createSireDrDetail(cur);

		SWK_DTL sireCR = sireDR.clone(); // 貸方
		sireCR.setDC(Dc.CREDIT);

		// 2023INVOICE制度対応用：仕訳Map
		Map<String, SWK_DTL> taxDtlMap = new LinkedHashMap<String, SWK_DTL>();
		Map<String, List<SWK_DTL>> oriMap = new LinkedHashMap<String, List<SWK_DTL>>(); // 元課税対象仕訳マップ

		if (isInvoice) {
			// 2023INVOICE制度対応

			// 消費税関連マップ初期化
			initTaxOccurMap(taxDtlMap, oriMap, dtlList);

		} else {
			// 既存処理

			for (SWK_DTL dtl : dtlList) {
				if (Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
					continue;
				}

				ConsumptionTax tax = dtl.getTax();

				if (tax == null) {
					tax = getTax(dtl.getSWK_K_KAI_CODE(), dtl.getSWK_ZEI_CODE()); // 再取得
				}

				if (tax == null) {
					continue;
				}

				switch (tax.getTaxType()) {
					case SALES: // 売上課税の場合、仮受消費税に加算
						if (dtl.isDR()) {
							uriDR.setSWK_IN_KIN(uriDR.getSWK_IN_KIN().add(dtl.getSWK_IN_ZEI_KIN()));
							uriDR.setSWK_KIN(uriDR.getSWK_KIN().add(dtl.getSWK_ZEI_KIN()));

						} else {
							uriCR.setSWK_IN_KIN(uriCR.getSWK_IN_KIN().add(dtl.getSWK_IN_ZEI_KIN()));
							uriCR.setSWK_KIN(uriCR.getSWK_KIN().add(dtl.getSWK_ZEI_KIN()));
						}

						break;

					case PURCHAESE: // 仕入課税の場合、仮払消費税に加算
						if (dtl.isDR()) {
							sireDR.setSWK_IN_KIN(sireDR.getSWK_IN_KIN().add(dtl.getSWK_IN_ZEI_KIN()));
							sireDR.setSWK_KIN(sireDR.getSWK_KIN().add(dtl.getSWK_ZEI_KIN()));

						} else {
							sireCR.setSWK_IN_KIN(sireCR.getSWK_IN_KIN().add(dtl.getSWK_IN_ZEI_KIN()));
							sireCR.setSWK_KIN(sireCR.getSWK_KIN().add(dtl.getSWK_ZEI_KIN()));
						}

						break;
				}
			}
		}

		if (isInvoice) {
			// 2023INVOICE制度対応

			// 対象が無ければ番号を返す
			if (taxDtlMap.entrySet() == null || taxDtlMap.entrySet().isEmpty()) {
				return maxRowNum;
			}

			for (String key : taxDtlMap.keySet()) {
				SWK_DTL dtl = taxDtlMap.get(key);

				if (dtl.getCurrency() == null) {
					dtl.setCurrency(cur);
				}

				int zeiKbn = dtl.getSWK_ZEI_KBN();
				int dcKbn = dtl.getSWK_DC_KBN();
				int taxType = dtl.getTax().getTaxType().value;
				// 摘要用
				String zeiName = dtl.getTax().getName();
				String zeiCalcType = zeiKbn == TaxCalcType.OUT.value ? getWord(TaxCalcType.OUT.getName())
					: getWord(TaxCalcType.IN.getName());

				// 経過措置仕訳追加
				maxRowNum = addTransferTaxDetail(oriMap, key, dtl, maxRowNum);

				if (!DecimalUtil.isNullOrZero(dtl.getSWK_ZEI_KIN())
					|| !DecimalUtil.isNullOrZero(dtl.getSWK_IN_ZEI_KIN())) {
					// 消費税が100%費用へ振替した場合は処理しない

					if (dcKbn == Dc.DEBIT.value && taxType == TaxType.SALES.value) {
						// 借方：売上
						SWK_DTL taxDtl = uriDR.clone();
						taxDtl.setSWK_ZEI_KBN(zeiKbn);
						taxDtl.setSWK_KIN(dtl.getSWK_ZEI_KIN());
						taxDtl.setSWK_IN_KIN(dtl.getSWK_IN_ZEI_KIN());
						taxDtl.setSWK_GYO_TEK(zeiCalcType + " " + zeiName); // 税計算 消費税名称
						taxDtl.setSWK_GYO_NO(++maxRowNum);
						slip.addDetail(taxDtl);

					} else if (dcKbn == Dc.CREDIT.value && taxType == TaxType.SALES.value) {
						// 貸方：売上
						SWK_DTL taxDtl = uriCR.clone();
						taxDtl.setDC(Dc.CREDIT);
						taxDtl.setSWK_ZEI_KBN(zeiKbn);
						taxDtl.setSWK_KIN(dtl.getSWK_ZEI_KIN());
						taxDtl.setSWK_IN_KIN(dtl.getSWK_IN_ZEI_KIN());
						taxDtl.setSWK_GYO_TEK(zeiCalcType + " " + zeiName);
						taxDtl.setSWK_GYO_NO(++maxRowNum);
						slip.addDetail(taxDtl);

					} else if (dcKbn == Dc.DEBIT.value && taxType == TaxType.PURCHAESE.value) {
						// 借方：仕入
						SWK_DTL taxDtl = sireDR.clone();
						taxDtl.setSWK_ZEI_KBN(zeiKbn);
						taxDtl.setSWK_KIN(dtl.getSWK_ZEI_KIN());
						taxDtl.setSWK_IN_KIN(dtl.getSWK_IN_ZEI_KIN());
						taxDtl.setSWK_GYO_TEK(zeiCalcType + " " + zeiName);
						taxDtl.setSWK_GYO_NO(++maxRowNum);
						slip.addDetail(taxDtl);

					} else if (dcKbn == Dc.CREDIT.value && taxType == TaxType.PURCHAESE.value) {
						// 貸方：仕入
						SWK_DTL taxDtl = sireCR.clone();
						taxDtl.setDC(Dc.CREDIT);
						taxDtl.setSWK_ZEI_KBN(zeiKbn);
						taxDtl.setSWK_KIN(dtl.getSWK_ZEI_KIN());
						taxDtl.setSWK_IN_KIN(dtl.getSWK_IN_ZEI_KIN());
						taxDtl.setSWK_GYO_TEK(zeiCalcType + " " + zeiName);
						taxDtl.setSWK_GYO_NO(++maxRowNum);
						slip.addDetail(taxDtl);
					}
				}
			}

		} else {
			// 既存処理
			// 金額があれば消費税行を追加。金額がマイナスなら貸借反転でプラスに

			// 売上課税(借受) 借方
			if (!DecimalUtil.isZero(uriDR.getSWK_KIN())) {
				uriDR.setSWK_GYO_NO(++maxRowNum);
				slip.addDetail(uriDR);
			}

			// 仕入課税(仮払) 借方
			if (!DecimalUtil.isZero(sireDR.getSWK_KIN())) {
				sireDR.setSWK_GYO_NO(++maxRowNum);
				slip.addDetail(sireDR);
			}

			// 売上課税(借受) 貸方
			if (!DecimalUtil.isZero(uriCR.getSWK_KIN())) {
				uriCR.setSWK_GYO_NO(++maxRowNum);
				slip.addDetail(uriCR);
			}

			// 仕入課税(仮払) 貸方
			if (!DecimalUtil.isZero(sireCR.getSWK_KIN())) {
				sireCR.setSWK_GYO_NO(++maxRowNum);
				slip.addDetail(sireCR);
			}
		}

		return maxRowNum;
	}

	/**
	 * 経過措置仕訳追加<br>
	 * dtl.getTax()は事前にマップ整理したもので、NULLあり得ない
	 * 
	 * @param oriMap
	 * @param key
	 * @param dtl
	 * @param maxRowNum
	 * @return 最後行番号
	 */
	public int addTransferTaxDetail(Map<String, List<SWK_DTL>> oriMap, String key, SWK_DTL dtl, int maxRowNum) {
		BigDecimal transferRate = null;
		String prefix = "";

		// 経過措置なし＝非適格消費税&&(経過措置控除率未設定 OR 設定値＝100%)
		boolean hasNoTransferRate = !dtl.getTax().isNO_INV_REG_FLG() || dtl.getTax().isNO_INV_REG_FLG() //
			&& (dtl.getTax().getKEKA_SOTI_RATE() == null
				|| DecimalUtil.equals(dtl.getTax().getKEKA_SOTI_RATE(), DecimalUtil.HUNDRED));

		if (!hasNoTransferRate) {
			// 経過措置控除可能率
			transferRate = subtract(DecimalUtil.HUNDRED, dtl.getTax().getKEKA_SOTI_RATE()); // 100% - 80%

			if (DecimalUtil.isNullOrZero(dtl.getTax().getKEKA_SOTI_RATE())) {
				// 100%控除のため、消費税認めない
				prefix = "";
			} else {
				prefix = getMessage("I01121", NumberFormatUtil.formatNumber(transferRate, 0) + "%");
			}
		}

		if (!hasNoTransferRate) {
			// 経過措置の振替比率＜＞０

			// 元仕訳をベースで計算する
			List<SWK_DTL> oriSortList = oriMap.get(key);

			// 按分調整処理を行う
			transferAdjst(getCompany(), slip, dtl, transferRate, oriSortList);

			boolean isDebugTax = ServerConfig.isFlagOn("trans.slip.debug.tax.transfer");

			// 仕訳を設定する
			for (SWK_DTL ori : oriSortList) {

				if (DecimalUtil.isNullOrZero(ori.getSWK_KIN()) && DecimalUtil.isNullOrZero(ori.getSWK_IN_KIN())) {
					// 金額がゼロは対象外
					continue;
				}

				// 不要情報クリア
				ori.setAP_ZAN(null);
				ori.setAR_ZAN(null);
				ori.setBsDetail(null);
				ori.setSWK_AUTO_KBN(1);
				ori.setSWK_ZEI_KIN(DecimalUtil.negate(ori.getSWK_KIN())); // 基軸通貨金額のマイナス
				ori.setSWK_IN_ZEI_KIN(DecimalUtil.negate(ori.getSWK_IN_KIN())); // 入力金額のマイナス
				ori.setSWK_ZEI_RATE(BigDecimal.ZERO);
				ori.setSWK_KESI_KBN(0);
				ori.setSWK_KESI_DEN_NO(null);
				ori.setSWK_KESI_DATE(null);
				ori.setSWK_APAR_KESI_KBN(0);
				ori.setSWK_APAR_DEN_NO(null);
				ori.setSWK_APAR_GYO_NO(null);

				String suffix = "";

				if (isDebugTax) {
					suffix = ":N"
						+ (ori.getAddonInfo() != null ? Util.avoidNull(ori.getAddonInfo().getValue("NEED_ADJ")) : "0")
						+ ":G" + ori.getSWK_GYO_NO();
				}

				// 経過措置:
				ori.setSWK_GYO_TEK(StringUtil.leftBX(prefix + ori.getSWK_GYO_TEK() + suffix, 80));
				if (ori.getAddonInfo() != null) {
					ori.setSWK_ORI_GYO_NO(Util.avoidNullAsInt(ori.getAddonInfo().getValue("ORI_GYO_NO"))); // 元行番号
				}
				ori.setSWK_GYO_NO(++maxRowNum);
				ori.setSWK_FREE_KBN(SWK_DTL.FREE_KBN.KEKA_SOTI); // 1:経過措置
				slip.addDetail(ori);
			}
		}

		return maxRowNum;
	}

	/**
	 * 計算対象分母の基軸通貨合計を返す<br>
	 * 注意：経過措置の振替額の合計ではない
	 * 
	 * @param list 対象リスト
	 * @param startIndex 開始INDEX
	 * @return 分母
	 */
	protected static BigDecimal getBunbo(List<SWK_DTL> list, int startIndex) {
		if (startIndex >= list.size()) {
			return BigDecimal.ZERO;
		}

		BigDecimal total = BigDecimal.ZERO;

		for (int i = startIndex; i < list.size(); i++) {
			BigDecimal zei = list.get(i).getSWK_KIN(); // 基軸通貨金額ベース
			total = add(total, zei);
		}

		return total;
	}

	/**
	 * @param company 会社
	 * @param slip
	 * @param dtl 消費税明細
	 * @param transferRate
	 * @param oriList ソート前リスト
	 */
	protected static void transferAdjst(Company company, Slip slip, SWK_DTL dtl, BigDecimal transferRate,
		List<SWK_DTL> oriList) {

		int keyDigits = company.getAccountConfig().getKeyCurrency().getDecimalPoint();
		if (slip != null && slip.getHeader().getSWK_ADJ_KBN() == AccountBook.IFRS.value) {
			keyDigits = company.getAccountConfig().getFunctionalCurrency().getDecimalPoint();
		}
		int digits = dtl.getCUR_DEC_KETA();
		BigDecimal zeiKin = dtl.getSWK_ZEI_KIN();
		BigDecimal zeiInKin = dtl.getSWK_IN_ZEI_KIN();
		BigDecimal totalFuriKin = getTransferTotalAmount(company, keyDigits, zeiKin, transferRate); // 按分対象消費税額
		BigDecimal totalFuriInKin = getTransferTotalAmount(company, digits, zeiInKin, transferRate); // 按分対象入力消費税額
		BigDecimal divFuriKin = multiply(keyDigits, totalFuriKin, BigDecimal.ONE); // 小数点以下処理
		BigDecimal divFuriInKin = multiply(digits, totalFuriInKin, BigDecimal.ONE); // 小数点以下処理

		// 振替済み税額を減らす
		dtl.setSWK_ZEI_KIN(subtract(dtl.getSWK_ZEI_KIN(), divFuriKin));
		dtl.setSWK_IN_ZEI_KIN(subtract(dtl.getSWK_IN_ZEI_KIN(), divFuriInKin));

		// 1. 1回目はシンプル調整を行う
		for (int i = 0; i < oriList.size(); i++) {
			SWK_DTL ori = oriList.get(i);

			if (ori.getAddonInfo() == null) {
				ori.setAddonInfo(new SlipDetailAddonInfo());
			}

			// 元行番号を持つ
			ori.getAddonInfo().setValue("ORI_GYO_NO", ori.getSWK_GYO_NO());
			ori.getAddonInfo().setValue("ORI_SWK_KIN", ori.getSWK_KIN()); // 元金額
			ori.getAddonInfo().setValue("DIV_TTL_KIN", divFuriKin); // 経過措置按分金額総額
			ori.getAddonInfo().setValue("DIV_TTL_IN_KIN", divFuriInKin); // 経過措置按分金額総額(入力)

			if (hasDecimalPoint(keyDigits, ori, transferRate)) {
				ori.getAddonInfo().setValue("NEED_ADJ", "1"); // 端数あり仕訳、2回目調整対象とする
			} else {
				ori.getAddonInfo().setValue("NEED_ADJ", "0"); // 端数なし仕訳、2回目調整対象外とする
			}

			// 1回目経過措置金額を設定する
			BigDecimal furiKin = getSimpleTransferAmount(keyDigits, ori.getSWK_ZEI_KIN(), transferRate); // 按分対象消費税額
			BigDecimal furiInKin = getSimpleTransferAmount(digits, ori.getSWK_IN_ZEI_KIN(), transferRate); // 按分対象入力消費税額

			ori.setSWK_KIN(furiKin);
			ori.setSWK_IN_KIN(furiInKin);

			ori.getAddonInfo().setValue("DIV_KIN", furiKin); // 経過措置按分金額
			ori.getAddonInfo().setValue("DIV_IN_KIN", furiInKin); // 経過措置按分金額(入力)

			if (DecimalUtil.isNullOrZero(ori.getSWK_KIN()) && DecimalUtil.isNullOrZero(ori.getSWK_IN_KIN())) {
				// 1回目計算結果がゼロなら、対象外
				ori.getAddonInfo().setValue("NEED_ADJ", "2"); // 端数なし仕訳、2回目調整対象外とする
			}

		}

		// 2. 経過措置額を合計する
		BigDecimal resumFuriKin = BigDecimal.ZERO;
		BigDecimal resumFuriInKin = BigDecimal.ZERO;
		for (int i = 0; i < oriList.size(); i++) {
			SWK_DTL ori = oriList.get(i);

			resumFuriKin = add(resumFuriKin, ori.getSWK_KIN());
			resumFuriInKin = add(resumFuriInKin, ori.getSWK_IN_KIN());
		}

		// 3. 差額
		BigDecimal saKin = subtract(totalFuriKin, resumFuriKin);
		BigDecimal saInKin = subtract(totalFuriInKin, resumFuriInKin);
		TaxJournalIssuerComparator comp = null;

		boolean isDebugTax = ServerConfig.isFlagOn("trans.slip.debug.tax.transfer");
		if (isDebugTax) {
			// 調整前情報
			debugSWK_DTL("-- ----調整前情報----", oriList, keyDigits, digits);
		}

		if (saKin.signum() == 1 //
			|| saKin.signum() == 0 && saInKin.signum() == 1 //
		) {
			// 基軸通貨金額差額＞０
			// 基軸通貨金額差額＝０且つ入力金額差額＞０
			comp = new TaxJournalIssuerComparator(true); // 金額降順
			Collections.sort(oriList, comp);

			int i = 1;
			for (SWK_DTL d : oriList) {
				d.setSWK_GYO_NO(i++);
			}

			if (isDebugTax) {
				// 調整後情報
				debugSWK_DTL("-- ----差額＞０調整後情報----", oriList, keyDigits, digits);
			}

		} else if (saKin.signum() == -1 //
			|| saKin.signum() == 0 && saInKin.signum() == -1 //
		) {
			// 基軸通貨金額差額＜０
			// 基軸通貨金額差額＝０且つ入力金額差額＜０
			comp = new TaxJournalIssuerComparator(false); // 金額昇順
			Collections.sort(oriList, comp);

			int i = 1;
			for (SWK_DTL d : oriList) {
				d.setSWK_GYO_NO(i++);
			}

			if (isDebugTax) {
				// 調整後情報
				debugSWK_DTL("-- ----差額＜０調整後情報----", oriList, keyDigits, digits);
			}

		} else {
			// 2回目調整不要、戻す
			return;
		}

		BigDecimal adjUnit = new BigDecimal("1").movePointLeft(keyDigits); // 基軸通貨金額調整単位
		BigDecimal adjInUnit = new BigDecimal("1").movePointLeft(digits); // 入力金額調整単位

		if (adjUnit.signum() != saKin.signum()) {
			adjUnit = adjUnit.abs();
			if (saKin.signum() == -1) {
				adjUnit = DecimalUtil.negate(adjUnit);
			}
		}
		if (adjInUnit.signum() != saInKin.signum()) {
			adjInUnit = adjInUnit.abs();
			if (saInKin.signum() == -1) {
				adjInUnit = DecimalUtil.negate(adjInUnit);
			}
		}

		// 4. 2回目は調整単位で調整を行う
		int i = 0;

		while (!DecimalUtil.isNullOrZero(saKin) || !DecimalUtil.isNullOrZero(saInKin)) {

			if (i >= oriList.size()) {
				// 最大サイズになっても調整できなかった場合、1行目の調整を行う
				SWK_DTL first = oriList.get(0);
				first.setSWK_KIN(add(first.getSWK_KIN(), saKin));
				first.setSWK_IN_KIN(add(first.getSWK_IN_KIN(), saInKin));

				first.getAddonInfo().setValue("DIV_KIN", first.getSWK_KIN()); // 経過措置按分金額
				first.getAddonInfo().setValue("DIV_IN_KIN", first.getSWK_IN_KIN()); // 経過措置按分金額(入力)

				first.getAddonInfo().setValue("DIV_SA_2_KIN", saKin); // 経過措置按分金額:差額
				first.getAddonInfo().setValue("DIV_SA_2_IN_KIN", saKin); // 経過措置按分金額(入力):差額
				break;
			}

			SWK_DTL ori = oriList.get(i++);

			if (ori.getAddonInfo() != null && Util.equals("1", Util.avoidNull(ori.getAddonInfo().getValue("NEED_ADJ"))) //
				&& (!DecimalUtil.isNullOrZero(ori.getSWK_KIN()) || !DecimalUtil.isNullOrZero(ori.getSWK_IN_KIN()))) {
				// 調整対象の一番先頭。並び順は既にソート済み

				if (!DecimalUtil.isNullOrZero(saKin)) {
					ori.setSWK_KIN(add(ori.getSWK_KIN(), adjUnit));
					saKin = subtract(saKin, adjUnit);
				}
				if (!DecimalUtil.isNullOrZero(saInKin)) {
					ori.setSWK_IN_KIN(add(ori.getSWK_IN_KIN(), adjInUnit));
					saInKin = subtract(saInKin, adjInUnit);
				}

				ori.getAddonInfo().setValue("DIV_KIN", ori.getSWK_KIN()); // 経過措置按分金額
				ori.getAddonInfo().setValue("DIV_IN_KIN", ori.getSWK_IN_KIN()); // 経過措置按分金額(入力)

				ori.getAddonInfo().setValue("DIV_SA_1_KIN", adjUnit); // 経過措置按分金額:差額
				ori.getAddonInfo().setValue("DIV_SA_1_IN_KIN", adjInUnit); // 経過措置按分金額(入力):差額
			}
		}

		// 元行番号順で再度ソートをかける
		comp.useOriginalRowNo();

		// 再度ソートを行う
		Collections.sort(oriList, comp);

		if (isDebugTax) {
			// 調整後情報
			debugSWK_DTL("-- ----再度ソート後情報----", oriList, keyDigits, digits);
		}

	}

	/**
	 * @param title
	 * @param oriList
	 * @param keyDigits
	 * @param digits
	 */
	@SuppressWarnings("unused")
	protected static void debugSWK_DTL(String title, List<SWK_DTL> oriList, int keyDigits, int digits) {
		System.out.println(title);

		for (SWK_DTL d : oriList) {
			StringBuilder sb = new StringBuilder();
			sb.append(d.getSWK_GYO_NO()).append("\t");
			sb.append(d.getSWK_KMK_CODE()).append("-").append(Util.avoidNull(d.getSWK_HKM_CODE())).append(":");
			sb.append(d.getSWK_KMK_NAME_S()).append("-").append(Util.avoidNull(d.getSWK_HKM_NAME_S()));

			int len = sb.length();
			int maxLen = Math.max(len, 50);

			if (len < maxLen) {
				sb.append(StringUtil.spaceFill("", maxLen - len));
			}

			sb.append("\t元金額:");
			sb.append(NumberFormatUtil
				.formatNumber(DecimalUtil.toBigDecimalNVL(d.getAddonInfo().getValue("ORI_SWK_KIN")), keyDigits));

			sb.append("\t経過措置金額:");
			sb.append(NumberFormatUtil.formatNumber(d.getSWK_KIN(), keyDigits));

			len = sb.length();
			maxLen = Math.max(len, 80);

			if (len < maxLen) {
				sb.append(StringUtil.spaceFill("", maxLen - len));
			}

			sb.append("\t行摘要:").append(d.getSWK_GYO_TEK());

			if (d.getAddonInfo() != null) {
				// 元行番号
				sb.append("\t元行番号:").append(d.getAddonInfo().getValue("ORI_GYO_NO"));
				// 端数あり仕訳、2回目調整対象とする
				sb.append("\t調整対象[=1]:").append(Util.avoidNullAsInt(d.getAddonInfo().getValue("NEED_ADJ")));
			}

			sb.append("\t経過措置金額の総額:");
			sb.append(NumberFormatUtil
				.formatNumber(DecimalUtil.toBigDecimalNVL(d.getAddonInfo().getValue("DIV_TTL_KIN")), keyDigits));
			sb.append("\t経過措置金額:");
			sb.append(NumberFormatUtil.formatNumber(DecimalUtil.toBigDecimalNVL(d.getAddonInfo().getValue("DIV_KIN")),
				keyDigits));
			sb.append("\t経過措置金額(差額調整1):");
			sb.append(NumberFormatUtil
				.formatNumber(DecimalUtil.toBigDecimalNVL(d.getAddonInfo().getValue("DIV_SA_1_KIN")), keyDigits));
			sb.append("\t経過措置金額(差額調整2):");
			sb.append(NumberFormatUtil
				.formatNumber(DecimalUtil.toBigDecimalNVL(d.getAddonInfo().getValue("DIV_SA_2_KIN")), keyDigits));

			System.out.println(sb.toString());
		}
	}

	/**
	 * 経過措置金額の取得
	 * 
	 * @param company
	 * @param digits
	 * @param zeiKin
	 * @param transferRate
	 * @return 経過措置金額(元消費税額-控除可能額)
	 */
	protected static BigDecimal getTransferTotalAmount(Company company, int digits, BigDecimal zeiKin,
		BigDecimal transferRate) {

		if (DecimalUtil.equals(DecimalUtil.HUNDRED, transferRate)) {
			// 100%控除のため、消費税認めない
			return zeiKin;
		}

		// 例
		// 消費税額＝107
		// 控除可能率＝80%
		// 経過措置金額＝107 - (107 * 80%の会社端数処理)
		// 切り捨て端数処理の場合
		// 経過措置金額＝107 - 85 ＝ 22

		// 控除可能額を計算する
		BigDecimal amount = multiply(15, zeiKin, subtract(DecimalUtil.HUNDRED, transferRate), NUM_PERCENT);
		BigDecimal kojyo = BigDecimal.ZERO;

		// 会社設定で端数処理(仮払消費税端数処理を利用)
		ExchangeFraction ef = company.getAccountConfig().getPaymentFunction();
		switch (ef) {
			case TRUNCATE: // 切捨
				kojyo = DecimalUtil.truncateNum(amount, digits);
				break;

			case ROUND_UP: // 切上
				kojyo = DecimalUtil.ceilingNum(amount, digits);
				break;

			case ROUND_OFF: // 四捨五入
				kojyo = DecimalUtil.roundNum(amount, digits);
				break;

		}

		// 元消費税額-控除可能額
		return subtract(zeiKin, kojyo);
	}

	/**
	 * 基軸通貨消費税額×経過措置控除可能率の端数判定
	 * 
	 * @param keyDigits
	 * @param ori
	 * @param transferRate
	 * @return true:小数点以下桁数、端数が発生する
	 */
	protected static boolean hasDecimalPoint(int keyDigits, SWK_DTL ori, BigDecimal transferRate) {

		if (DecimalUtil.equals(DecimalUtil.HUNDRED, transferRate)) {
			// 100%控除のため、消費税認めない
			return false;
		}

		// 例
		// 消費税額×経過措置控除可能率

		// 小数点以下桁数が発生しないケース
		// 消費税額＝100
		// 経過措置控除可能率＝20%
		// 消費税額×経過措置控除可能率＝100*20%=20

		// 小数点以下桁数が発生するケース
		// 消費税額＝101
		// 経過措置控除可能率＝20%
		// 消費税額×経過措置控除可能率＝101*20%=20.2 <> 20

		// 消費税額×経過措置控除可能率
		BigDecimal transferKin = multiply(15, ori.getSWK_ZEI_KIN(), transferRate, NUM_PERCENT);
		// 指定小数点以下桁数で四捨五入
		BigDecimal checkKin = DecimalUtil.roundNum(transferKin, keyDigits);

		// 比較
		if (!equals(transferKin, checkKin)) {
			return true;
		}

		return false;
	}

	/**
	 * 指定消費税額×経過措置控除可能率の金額
	 * 
	 * @param digits
	 * @param zeiKin
	 * @param transferRate
	 * @return 指定消費税額×経過措置控除可能率の金額
	 */
	protected static BigDecimal getSimpleTransferAmount(int digits, BigDecimal zeiKin, BigDecimal transferRate) {

		if (DecimalUtil.equals(DecimalUtil.HUNDRED, transferRate)) {
			// 100%控除のため、消費税認めない
			return zeiKin;
		}

		// 端数が発生しない場合に、経過措置控除可能率で計算した金額を返す

		// 消費税額×経過措置控除可能率
		BigDecimal transferKin = multiply(15, zeiKin, transferRate, NUM_PERCENT);
		// 指定小数点以下桁数で四捨五入
		BigDecimal checkKin = DecimalUtil.roundNum(transferKin, digits);

		return checkKin;
	}

	/**
	 * @param dtl
	 * @return ソートキー
	 */
	protected static String getOriSortKey(SWK_DTL dtl) {
		// 金額の降順＞行番号の昇順
		BigDecimal amt = subtract(NUM_MAX_PAD, dtl.getSWK_KIN());
		int rowNo = 1000000 + dtl.getSWK_GYO_NO();
		return NumberFormatUtil.formatNumber(amt, 4) + "<>" + Integer.toString(rowNo);
	}

	/**
	 * @param map
	 * @return 端数の反映仕訳
	 */
	protected static SWK_DTL getTransferAdjustDetail(Map<String, SWK_DTL> map) {

		String lastKey = null;
		for (String key : map.keySet()) {
			// 最終キーを使う
			lastKey = key;
		}

		return map.get(lastKey);
	}

	/**
	 * @param a
	 * @param b
	 * @return 和(a + b)
	 */
	protected static BigDecimal add(BigDecimal a, BigDecimal b) {
		return DecimalUtil.avoidNull(a).add(DecimalUtil.avoidNull(b));
	}

	/**
	 * @param a
	 * @param b
	 * @return 差(a - b)
	 */
	protected static BigDecimal subtract(BigDecimal a, BigDecimal b) {
		return DecimalUtil.avoidNull(a).subtract(DecimalUtil.avoidNull(b));
	}

	/**
	 * 数量の計算(a * b)
	 * 
	 * @param a
	 * @param b
	 * @param digits
	 * @return 数量
	 */
	public static BigDecimal multiply(BigDecimal a, BigDecimal b, int digits) {
		return DecimalUtil.roundNum(DecimalUtil.avoidNull(a).multiply(DecimalUtil.avoidNull(b)), digits);
	}

	/**
	 * 数量の計算(a * b)<br>
	 * 経過措置専用関数として、四捨五入固定
	 * 
	 * @param digits
	 * @param nums
	 * @return 数量
	 */
	public static BigDecimal multiply(int digits, BigDecimal... nums) {
		BigDecimal total = BigDecimal.ONE;
		if (nums.length > 0) {
			for (BigDecimal num : nums) {
				total = multiply(total, num, 15);
			}
		}
		return DecimalUtil.roundNum(total, digits);
	}

	/**
	 * 数値同一比較
	 * 
	 * @param a
	 * @param b
	 * @return true:同じ
	 */
	public static boolean equals(BigDecimal a, BigDecimal b) {
		if (a == null && b != null) {
			return false;
		}
		if (a != null && b == null) {
			return false;
		}
		if (a == null && b == null) {
			return true;
		}

		return a.compareTo(b) == 0;
	}

	/**
	 * 売上課税(仮受)仕訳ジャーナルの作成
	 * 
	 * @param cur 通貨情報
	 * @return 売上課税(仮受)仕訳ジャーナル
	 */
	protected SWK_DTL createUriDrDetail(Currency cur) {

		// 売上課税(仮受)
		SWK_DTL uriDR = slip.createDetail(); // 借方
		uriDR.setCurrencyType(currencyType); // BookNo.
		uriDR.setSWK_KMK_CODE(kariukeKmk.getItemCode());
		uriDR.setSWK_KMK_NAME(kariukeKmk.getItemName());
		uriDR.setSWK_KMK_NAME_S(kariukeKmk.getItemNames());
		uriDR.setSWK_HKM_CODE(kariukeKmk.getSubItemCode());
		uriDR.setSWK_HKM_NAME(kariukeKmk.getSubItemName());
		uriDR.setSWK_HKM_NAME_S(kariukeKmk.getSubItemNames());
		uriDR.setSWK_UKM_CODE(kariukeKmk.getDetailItemCode());
		uriDR.setSWK_UKM_NAME(kariukeKmk.getDetailItemName());
		uriDR.setSWK_UKM_NAME_S(kariukeKmk.getDetailItemNames());
		uriDR.setSWK_DEP_CODE(kariukeKmk.getDepertmentCode());
		uriDR.setSWK_DEP_NAME(kariukeKmk.getDepertmentName());
		uriDR.setSWK_DEP_NAME_S(kariukeKmk.getDepertmentNames());
		uriDR.setSWK_K_KAI_CODE(slip.getCompanyCode());
		uriDR.setDC(Dc.DEBIT);
		uriDR.setCurrency(cur);
		uriDR.setSWK_CUR_RATE(null);
		uriDR.setSWK_AUTO_KBN(SWK_DTL.AUTO_KBN.AUTO);
		uriDR.setSWK_IN_KIN(BigDecimal.ZERO);
		uriDR.setSWK_KIN(BigDecimal.ZERO);
		uriDR.setHAS_DATE(null);
		uriDR.setTaxJornal(true);
		uriDR.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE); // 非課税
		uriDR.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
		uriDR.setSWK_ZEI_KIN(BigDecimal.ZERO);
		uriDR.setSWK_GYO_TEK(slip.getRemarks()); // 摘要
		return uriDR;
	}

	/**
	 * 仕入課税(仮払)仕訳ジャーナルの作成
	 * 
	 * @param cur 通貨情報
	 * @return 仕入課税(仮払)仕訳ジャーナル
	 */
	protected SWK_DTL createSireDrDetail(Currency cur) {

		// 仕入課税(仮払)
		SWK_DTL sireDR = slip.createDetail(); // 借方
		sireDR.setCurrencyType(currencyType); // BookNo.
		sireDR.setSWK_KMK_CODE(karibaraiKmk.getItemCode());
		sireDR.setSWK_KMK_NAME(karibaraiKmk.getItemName());
		sireDR.setSWK_KMK_NAME_S(karibaraiKmk.getItemNames());
		sireDR.setSWK_HKM_CODE(karibaraiKmk.getSubItemCode());
		sireDR.setSWK_HKM_NAME(karibaraiKmk.getSubItemName());
		sireDR.setSWK_HKM_NAME_S(karibaraiKmk.getSubItemNames());
		sireDR.setSWK_UKM_CODE(karibaraiKmk.getDetailItemCode());
		sireDR.setSWK_UKM_NAME(karibaraiKmk.getDetailItemName());
		sireDR.setSWK_UKM_NAME_S(karibaraiKmk.getDetailItemNames());
		sireDR.setSWK_DEP_CODE(karibaraiKmk.getDepertmentCode());
		sireDR.setSWK_DEP_NAME(karibaraiKmk.getDepertmentName());
		sireDR.setSWK_DEP_NAME_S(karibaraiKmk.getDepertmentNames());
		sireDR.setSWK_K_KAI_CODE(slip.getCompanyCode());
		sireDR.setDC(Dc.DEBIT);
		sireDR.setCurrency(cur);
		sireDR.setSWK_CUR_RATE(null);
		sireDR.setSWK_AUTO_KBN(SWK_DTL.AUTO_KBN.AUTO);
		sireDR.setSWK_IN_KIN(BigDecimal.ZERO);
		sireDR.setSWK_KIN(BigDecimal.ZERO);
		sireDR.setHAS_DATE(null);
		sireDR.setTaxJornal(true);
		sireDR.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE); // 非課税
		sireDR.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
		sireDR.setSWK_ZEI_KIN(BigDecimal.ZERO);
		sireDR.setSWK_GYO_TEK(slip.getRemarks()); // 摘要
		return sireDR;
	}

	/**
	 * 消費税取得
	 * 
	 * @param kCompanyCode 計上会社コード
	 * @param taxCode 消費税コード
	 * @return 消費税情報
	 * @throws TException
	 */
	protected ConsumptionTax getTax(String kCompanyCode, String taxCode) throws TException {

		if (taxMap == null) {
			taxMap = new HashMap<String, ConsumptionTax>();
		}

		String companyCode = kCompanyCode;
		if (Util.isNullOrEmpty(kCompanyCode)) {
			companyCode = getCompanyCode();
		}

		String key = companyCode + "<>" + taxCode;

		if (taxMap.containsKey(key)) {
			return taxMap.get(key);
		}

		param.setCompanyCode(companyCode);
		param.setCode(taxCode);
		List<ConsumptionTax> list = taxManager.get(param);

		if (list == null || list.isEmpty()) {
			throw new TException(getMessage("I00129", "C00286", taxCode)); // 消費税[{0}]の取得に失敗しました。
		}

		ConsumptionTax bean = list.get(0);
		taxMap.put(key, bean);

		return bean;
	}

	/**
	 * 通貨情報取得
	 * 
	 * @param currencyCode 通貨コード
	 * @return 通貨
	 * @throws TException
	 */
	protected Currency getCurrency(String currencyCode) throws TException {
		CurrencySearchCondition condition = new CurrencySearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(currencyCode);

		CurrencyManager curManager = (CurrencyManager) getComponent(CurrencyManager.class);
		List<Currency> list = curManager.get(condition);

		if (list == null || list.isEmpty()) {
			throw new TException(getMessage("I00129", "C00371", currencyCode)); // 開発用 //"通貨[{0}]の取得に失敗しました。",
		}

		return list.get(0);
	}

	/**
	 * 自動仕訳科目を取得する.
	 * 
	 * @param companyCode 会社コード
	 * @param kmkCnt 科目制御区分
	 * @return 自動仕訳科目
	 * @throws TException
	 */
	protected AutoJornalAccount getAutoItem(String companyCode, AutoJornalAccountType kmkCnt) throws TException {
		SlipManager manager = (SlipManager) getComponent(SlipManager.class);

		AutoJornalAccount bean = manager.getAutoJornalAccount(companyCode, kmkCnt);

		if (bean == null) {
			throw new TException(getMessage("I00163", companyCode));// "会社[{0}]の自動仕訳科目:仮受/仮払消費税が設定されていません。
		}

		return bean;
	}

	/**
	 * 伝票に消費税仕訳を追加。<br>
	 * 本体勘定の消費税コード、消費税額をもとに<br>
	 * 消費税勘定を作成し、伝票にセット。
	 * 
	 * @param motoSlip 伝票
	 * @param maxRowNum 最大行番号
	 * @return 最大行番号
	 * @throws TException
	 */
	public int addTransferJournal(Slip motoSlip, int maxRowNum) throws TException {

		// 伝票
		this.slip = motoSlip;

		// 会社コード
		String companyCode = motoSlip.getCompanyCode();

		// 消費税計算用パラメータに会社コードセット
		this.param.setCompanyCode(companyCode);

		this.slipType = getSlipType(slip.getSlipType());

		// 伝票種別が判断対象
		boolean isInvoice = getCompany().isCMP_INV_CHK_FLG() && slipType != null && slipType.isINV_SYS_FLG();

		if (!isInvoice) {
			return maxRowNum;
		}

		// 事前チェック(経過措置仕訳として取り込んでいる場合は処理不要)
		for (SWK_DTL dtl : motoSlip.getDetails()) {
			if (dtl.getSWK_FREE_KBN() == SWK_DTL.FREE_KBN.KEKA_SOTI) {
				return maxRowNum;
			}
		}

		// 通貨ごとに分ける
		Map<String, List<SWK_DTL>> map = splitCurrencyDetails(motoSlip.getDetails());

		// 対象が無ければ番号を返して終了
		if (map.isEmpty()) {
			return maxRowNum;
		}

		// 消費税勘定行(仮受)
		this.kariukeKmk = getAutoItem(companyCode, AutoJornalAccountType.RECEIVE_TAX); // 仮受消費税科目

		// 消費税勘定行(仮払)
		this.karibaraiKmk = getAutoItem(companyCode, AutoJornalAccountType.PAY_TAX); // 仮払消費税科目

		boolean hasChanged = false;

		for (Map.Entry<String, List<SWK_DTL>> entry : map.entrySet()) {

			String currency = entry.getKey();
			Currency cur = getCurrency(currency);
			List<SWK_DTL> dtlList = entry.getValue();

			// 売上課税(仮受)
			SWK_DTL uriDR = createUriDrDetail(cur);

			SWK_DTL uriCR = uriDR.clone(); // 貸方
			uriCR.setDC(Dc.CREDIT);

			// 仕入課税(仮払)
			SWK_DTL sireDR = createSireDrDetail(cur);

			SWK_DTL sireCR = sireDR.clone(); // 貸方
			sireCR.setDC(Dc.CREDIT);

			// 2023INVOICE制度対応用：仕訳Map
			Map<String, SWK_DTL> taxDtlMap = new LinkedHashMap<String, SWK_DTL>();
			Map<String, List<SWK_DTL>> oriMap = new LinkedHashMap<String, List<SWK_DTL>>(); // 元課税対象仕訳マップ

			// 消費税関連マップ初期化
			initTaxOccurMap(taxDtlMap, oriMap, dtlList);

			// 対象が無ければ番号を返す
			if (taxDtlMap.entrySet() == null || taxDtlMap.entrySet().isEmpty()) {
				return maxRowNum;
			}

			for (String key : taxDtlMap.keySet()) {
				SWK_DTL dtl = taxDtlMap.get(key);

				if (DecimalUtil.isNullOrZero(dtl.getTax().getKEKA_SOTI_RATE())) {
					// 経過措置未設定
					continue;
				}

				if (dtl.getCurrency() == null) {
					dtl.setCurrency(cur);
				}

				int zeiKbn = dtl.getSWK_ZEI_KBN();
				int dcKbn = dtl.getSWK_DC_KBN();
				int taxType = dtl.getTax().getTaxType().value;
				// 摘要用
				String zeiName = dtl.getTax().getName();
				String zeiCalcType = zeiKbn == TaxCalcType.OUT.value ? getWord(TaxCalcType.OUT.getName())
					: getWord(TaxCalcType.IN.getName());

				// 経過措置仕訳追加
				maxRowNum = addTransferTaxDetail(oriMap, key, dtl, maxRowNum);

				// // 税額 = 元の税額 - 経過措置処理後税額 (目的は経過措置差分のみ、仕訳調整する)
				// // 元消費税額
				// BigDecimal zeiKin = DecimalUtil.toBigDecimalNVL(dtl.getAddonInfo().getValue("ORI_SWK_ZEI_KIN"));
				// BigDecimal zeiInKin = DecimalUtil.toBigDecimalNVL(dtl.getAddonInfo().getValue("ORI_SWK_IN_ZEI_KIN"));
				// zeiKin = subtract(zeiKin, dtl.getSWK_ZEI_KIN());
				// zeiInKin = subtract(zeiInKin, dtl.getSWK_IN_ZEI_KIN());
				BigDecimal zeiKin = dtl.getSWK_ZEI_KIN();
				BigDecimal zeiInKin = dtl.getSWK_IN_ZEI_KIN();

				if (dcKbn == Dc.DEBIT.value && taxType == TaxType.SALES.value) {
					// 借方：売上
					SWK_DTL taxDtl = createUriDrDetail(cur);
					taxDtl.setAddonInfo(new SlipDetailAddonInfo());
					taxDtl.getAddonInfo().setValue("NEW_DTL_FLG", "1"); // 新規フラグ
					taxDtl.setSWK_ZEI_KBN(zeiKbn);
					taxDtl.setSWK_KIN(zeiKin);
					taxDtl.setSWK_IN_KIN(zeiInKin);
					taxDtl.setSWK_GYO_TEK(zeiCalcType + " " + zeiName); // 税計算 消費税名称
					taxDtl.setSWK_GYO_NO(++maxRowNum);
					slip.addDetail(taxDtl);

				} else if (dcKbn == Dc.CREDIT.value && taxType == TaxType.SALES.value) {
					// 貸方：売上
					SWK_DTL taxDtl = createUriDrDetail(cur);
					taxDtl.setAddonInfo(new SlipDetailAddonInfo());
					taxDtl.getAddonInfo().setValue("NEW_DTL_FLG", "1"); // 新規フラグ
					taxDtl.setDC(Dc.CREDIT);
					taxDtl.setSWK_ZEI_KBN(zeiKbn);
					taxDtl.setSWK_KIN(zeiKin);
					taxDtl.setSWK_IN_KIN(zeiInKin);
					taxDtl.setSWK_GYO_TEK(zeiCalcType + " " + zeiName);
					taxDtl.setSWK_GYO_NO(++maxRowNum);
					slip.addDetail(taxDtl);

				} else if (dcKbn == Dc.DEBIT.value && taxType == TaxType.PURCHAESE.value) {
					// 借方：仕入
					SWK_DTL taxDtl = createSireDrDetail(cur);
					taxDtl.setAddonInfo(new SlipDetailAddonInfo());
					taxDtl.getAddonInfo().setValue("NEW_DTL_FLG", "1"); // 新規フラグ
					taxDtl.setSWK_ZEI_KBN(zeiKbn);
					taxDtl.setSWK_KIN(zeiKin);
					taxDtl.setSWK_IN_KIN(zeiInKin);
					taxDtl.setSWK_GYO_TEK(zeiCalcType + " " + zeiName);
					taxDtl.setSWK_GYO_NO(++maxRowNum);
					slip.addDetail(taxDtl);

				} else if (dcKbn == Dc.CREDIT.value && taxType == TaxType.PURCHAESE.value) {
					// 貸方：仕入
					SWK_DTL taxDtl = createSireDrDetail(cur);
					taxDtl.setAddonInfo(new SlipDetailAddonInfo());
					taxDtl.getAddonInfo().setValue("NEW_DTL_FLG", "1"); // 新規フラグ
					taxDtl.setDC(Dc.CREDIT);
					taxDtl.setSWK_ZEI_KBN(zeiKbn);
					taxDtl.setSWK_KIN(zeiKin);
					taxDtl.setSWK_IN_KIN(zeiInKin);
					taxDtl.setSWK_GYO_TEK(zeiCalcType + " " + zeiName);
					taxDtl.setSWK_GYO_NO(++maxRowNum);
					slip.addDetail(taxDtl);
				}

				hasChanged = true;
			}
		}

		if (hasChanged) {
			// 変更がある場合に既存取込対象消費税仕訳を削除する
			for (int i = slip.getDetailCount() - 1; i >= 0; i--) {
				SWK_DTL dtl = slip.getDetails().get(i);

				if (dtl.getAddonInfo() != null
					&& Util.equals("1", Util.avoidNull(dtl.getAddonInfo().getValue("NEW_DTL_FLG")))) {
					// 新規フラグが１は対象外
					continue;
				}

				if (dtl.getSWK_AUTO_KBN() == 1 //
					&& isTaxAutoItem(dtl.getSWK_KMK_CODE(), dtl.getSWK_HKM_CODE(), dtl.getSWK_UKM_CODE())) {
					// SWK_AUTO_KBN=1、仕訳の科目が仮払消費税は削除対象
					slip.getDetails().remove(i);
				}

			}

			// 削除＆変更があれば、バランスチェックを行う
			BigDecimal dr = BigDecimal.ZERO;
			BigDecimal cr = BigDecimal.ZERO;
			for (SWK_DTL dtl : slip.getDetails()) {
				dr = add(dr, dtl.getDebitKeyAmount());
				cr = add(cr, dtl.getCreditKeyAmount());
			}

			if (!equals(dr, cr)) {
				// W00121 貸借がバランスしていません。
				throw new TException("W00121");
			}

		}

		return maxRowNum;
	}

	/**
	 * @param itemCode
	 * @param subItemCode
	 * @param detailItemCode
	 * @return true:仮払消費税自動仕訳の科目
	 */
	public boolean isTaxAutoItem(String itemCode, String subItemCode, String detailItemCode) {
		if (karibaraiKmk != null && //
			Util.equals(itemCode, karibaraiKmk.getItemCode()) //
			&& Util.equals(subItemCode, karibaraiKmk.getSubItemCode()) //
			&& Util.equals(detailItemCode, karibaraiKmk.getDetailItemCode()) //
		) {
			return true;
		}
		return false;
	}
}
