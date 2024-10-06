package jp.co.ais.trans2.common.model.ui.slip;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.balance.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 明細のコンバーター
 */
public class TDetailConverter extends TController {

	/** 会社 */
	public Company company = null;

	/** 発生日対応フラグ */
	public boolean isUseHasDate = ClientConfig.isFlagOn("trans.slip.bs.use.occurdate");

	/** 計算ロジック */
	public TCalculator calculator = TCalculatorFactory.getCalculator();

	/**
	 * @param dialog
	 * @return 選択債務残高リスト
	 */
	public List<AP_ZAN> getSelectedAPBalanceList(TApBalanceListDialogCtrl dialog) {
		return dialog.getSelectedList();
	}

	/**
	 * 残高リスト変換
	 * 
	 * @param zan 債務残高
	 * @return リスト
	 */
	public SWK_DTL toDetail(AP_ZAN zan) {
		SWK_DTL dtl = createSlipDetail();

		dtl.setAP_ZAN(zan);

		// 会社コード
		dtl.setKAI_CODE(getCompanyCode());

		// 計上会社
		dtl.setSWK_K_KAI_CODE(zan.getKAI_CODE());
		dtl.setSWK_K_KAI_NAME_S(zan.getKAI_NAME_S());

		// 計上部門
		dtl.setSWK_DEP_CODE(zan.getZAN_DEP_CODE());
		dtl.setSWK_DEP_NAME_S(zan.getZAN_DEP_NAME_S());

		// 科目
		dtl.setSWK_KMK_CODE(zan.getZAN_KMK_CODE());
		dtl.setSWK_KMK_NAME_S(zan.getZAN_KMK_NAME_S());

		// 補助科目
		dtl.setSWK_HKM_CODE(zan.getZAN_HKM_CODE());
		dtl.setSWK_HKM_NAME_S(zan.getZAN_HKM_NAME_S());

		// 内訳科目
		dtl.setSWK_UKM_CODE(zan.getZAN_UKM_CODE());
		dtl.setSWK_UKM_NAME_S(zan.getZAN_UKM_NAME_S());

		// 発生日
		dtl.setHAS_DATE(zan.getZAN_DEN_DATE());

		// 消費税(非課税)
		dtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE);

		// 行摘要
		dtl.setSWK_GYO_TEK(zan.getZAN_TEK());

		// 借方
		dtl.setDC(Dc.DEBIT);

		// 入力金額(外貨)
		dtl.setSWK_IN_KIN(zan.getZAN_IN_SIHA_KIN());

		// 邦貨金額
		dtl.setSWK_KIN(zan.getZAN_KIN());

		// 取引先
		dtl.setSWK_TRI_CODE(zan.getZAN_SIHA_CODE());
		dtl.setSWK_TRI_NAME_S(zan.getZAN_SIHA_NAME_S());

		// 社員
		// 管理1～6
		// 発生日
		// 非会計明細1～3

		// 通貨
		dtl.setSWK_CUR_CODE(zan.getZAN_CUR_CODE()); // 通貨コード
		dtl.setCUR_DEC_KETA(zan.getZAN_CUR_DEC_KETA()); // 小数点以下桁数
		dtl.setCUR_RATE_POW(zan.getZAN_CUR_RATE_POW()); // レート係数

		// 換算レート
		dtl.setSWK_CUR_RATE(zan.getZAN_CUR_RATE());

		// 消込区分
		dtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);

		return buildDetail(dtl);
	}

	/**
	 * @param dialog
	 * @return 選択債権残高リスト
	 */
	public List<AR_ZAN> getSelectedARBalanceList(TArBalanceListDialogCtrl dialog) {
		return dialog.getSelectedList();
	}

	/**
	 * 残高リスト変換
	 * 
	 * @param zan 債権残高
	 * @return リスト
	 */
	public SWK_DTL toDetail(AR_ZAN zan) {
		SWK_DTL dtl = createSlipDetail();

		dtl.setAR_ZAN(zan);

		// 会社コード
		dtl.setKAI_CODE(getCompanyCode());

		// 計上会社
		dtl.setSWK_K_KAI_CODE(zan.getKAI_CODE());
		dtl.setSWK_K_KAI_NAME_S(zan.getKAI_NAME_S());

		// 計上部門
		dtl.setSWK_DEP_CODE(zan.getZAN_DEP_CODE());
		dtl.setSWK_DEP_NAME_S(zan.getZAN_DEP_NAME_S());

		// 科目
		dtl.setSWK_KMK_CODE(zan.getZAN_KMK_CODE());
		dtl.setSWK_KMK_NAME_S(zan.getZAN_KMK_NAME_S());

		// 補助科目
		dtl.setSWK_HKM_CODE(zan.getZAN_HKM_CODE());
		dtl.setSWK_HKM_NAME_S(zan.getZAN_HKM_NAME_S());

		// 内訳科目
		dtl.setSWK_UKM_CODE(zan.getZAN_UKM_CODE());
		dtl.setSWK_UKM_NAME_S(zan.getZAN_UKM_NAME_S());

		// 発生日
		dtl.setHAS_DATE(zan.getZAN_SEI_DEN_DATE());

		// 消費税(非課税)
		dtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE);

		// 行摘要
		dtl.setSWK_GYO_TEK(zan.getZAN_TEK());

		// 貸方
		dtl.setDC(Dc.CREDIT);

		// 入力金額(外貨)
		dtl.setSWK_IN_KIN(zan.getNON_KESI_IN_KIN());

		// 邦貨金額
		dtl.setSWK_KIN(zan.getNON_KESI_KIN());

		// 取引先
		dtl.setSWK_TRI_CODE(zan.getZAN_TRI_CODE());
		dtl.setSWK_TRI_NAME_S(zan.getZAN_TRI_NAME_S());

		// 通貨
		dtl.setSWK_CUR_CODE(zan.getZAN_CUR_CODE()); // 通貨コード
		dtl.setCUR_DEC_KETA(zan.getZAN_CUR_DEC_KETA()); // 小数点以下桁数
		dtl.setCUR_RATE_POW(zan.getZAN_CUR_RATE_POW()); // レート係数

		// 換算レート
		dtl.setSWK_CUR_RATE(zan.getZAN_CUR_RATE());

		// 消込区分
		dtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);

		return buildDetail(dtl);
	}

	/**
	 * @param dialog
	 * @return 選択BS勘定リスト
	 */
	public List<SWK_DTL> getSelectedBSList(BSEraseDialogCtrl dialog) {
		return dialog.getSelectedList();
	}

	/**
	 * BS消込変換
	 * 
	 * @param bs BS消込
	 * @param kcompany 計上会社
	 * @return 仕訳ジャーナル
	 */
	public SWK_DTL toDetail(SWK_DTL bs, Company kcompany) {
		SWK_DTL dtl = createSlipDetail();

		if (kcompany == null) {
			kcompany = getCompany();
		}

		// BS勘定消込情報の設定
		dtl.setBsDetail(bs);

		// 会社コード
		dtl.setKAI_CODE(getCompanyCode());

		// 計上会社
		dtl.setSWK_K_KAI_CODE(bs.getKAI_CODE()); // 後に再設定処理で上書きあり

		// 計上部門
		dtl.setSWK_DEP_CODE(bs.getSWK_DEP_CODE());
		dtl.setSWK_DEP_NAME_S(bs.getSWK_DEP_NAME_S());

		// 科目
		dtl.setSWK_KMK_CODE(bs.getSWK_KMK_CODE());
		dtl.setSWK_KMK_NAME_S(bs.getSWK_KMK_NAME_S());

		// 補助科目
		dtl.setSWK_HKM_CODE(bs.getSWK_HKM_CODE());
		dtl.setSWK_HKM_NAME_S(bs.getSWK_HKM_NAME_S());

		// 内訳科目
		dtl.setSWK_UKM_CODE(bs.getSWK_UKM_CODE());
		dtl.setSWK_UKM_NAME_S(bs.getSWK_UKM_NAME_S());

		// 発生日
		if (isUseHasDate) {
			dtl.setHAS_DATE(bs.getHAS_DATE());
		} else {
			dtl.setHAS_DATE(bs.getSWK_DEN_DATE());
		}

		// 消費税(非課税)
		dtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE);
		dtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
		dtl.setSWK_ZEI_KIN(BigDecimal.ZERO);

		// 行摘要
		dtl.setSWK_GYO_TEK(getBsDetailRowRemark(bs));

		// 貸方
		dtl.setDC(bs.isDR() ? Dc.CREDIT : Dc.DEBIT);

		// 入力金額(外貨)
		dtl.setSWK_IN_KIN(bs.getSWK_IN_KIN());

		// 邦貨金額
		dtl.setSWK_KIN(bs.getSWK_KIN());

		// 取引先
		dtl.setSWK_TRI_CODE(bs.getSWK_TRI_CODE());
		dtl.setSWK_TRI_NAME_S(bs.getSWK_TRI_NAME_S());

		// 社員
		dtl.setSWK_EMP_CODE(bs.getSWK_EMP_CODE());
		dtl.setSWK_EMP_NAME_S(bs.getSWK_EMP_NAME_S());

		// 管理1
		dtl.setSWK_KNR_CODE_1(bs.getSWK_KNR_CODE_1());
		dtl.setSWK_KNR_NAME_S_1(bs.getSWK_KNR_NAME_S_1());

		// 管理2
		dtl.setSWK_KNR_CODE_2(bs.getSWK_KNR_CODE_2());
		dtl.setSWK_KNR_NAME_S_2(bs.getSWK_KNR_NAME_S_2());

		// 管理3
		dtl.setSWK_KNR_CODE_3(bs.getSWK_KNR_CODE_3());
		dtl.setSWK_KNR_NAME_S_3(bs.getSWK_KNR_NAME_S_3());

		// 管理4
		dtl.setSWK_KNR_CODE_4(bs.getSWK_KNR_CODE_4());
		dtl.setSWK_KNR_NAME_S_4(bs.getSWK_KNR_NAME_S_4());

		// 管理5
		dtl.setSWK_KNR_CODE_5(bs.getSWK_KNR_CODE_5());
		dtl.setSWK_KNR_NAME_S_5(bs.getSWK_KNR_NAME_S_5());

		// 管理6
		dtl.setSWK_KNR_CODE_6(bs.getSWK_KNR_CODE_6());
		dtl.setSWK_KNR_NAME_S_6(bs.getSWK_KNR_NAME_S_6());

		// 非会計明細1
		dtl.setSWK_HM_1(bs.getSWK_HM_1());

		// 非会計明細2
		dtl.setSWK_HM_2(bs.getSWK_HM_2());

		// 非会計明細3
		dtl.setSWK_HM_3(bs.getSWK_HM_3());

		// 通貨
		dtl.setSWK_CUR_CODE(bs.getSWK_CUR_CODE()); // 通貨コード
		dtl.setCUR_DEC_KETA(bs.getCUR_DEC_KETA()); // 小数点以下桁数
		dtl.setCUR_RATE_POW(bs.getCUR_RATE_POW()); // レート係数

		// 換算レート
		dtl.setSWK_CUR_RATE(bs.getSWK_CUR_RATE());

		// 消込区分
		dtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.FORWARD);

		// 付替え会社の基軸通貨が異なる場合に変換を行う
		String parentKeyCur = getCompany().getAccountConfig().getKeyCurrency().getCode();
		String groupKeyCur = kcompany.getAccountConfig().getKeyCurrency().getCode();
		if (!Util.equals(parentKeyCur, groupKeyCur)) {
			String curCode = dtl.getSWK_CUR_CODE();

			if (Util.equals(parentKeyCur, curCode)) {
				// 1. 仕訳の通貨コード＝付替え会社の基軸通貨コード
				// 基軸通貨金額＝入力金額
				dtl.setSWK_CUR_RATE(BigDecimal.ONE);
				dtl.setSWK_KIN(dtl.getSWK_IN_KIN());
				dtl.setSWK_ZEI_KIN(dtl.getSWK_IN_ZEI_KIN());
			} else {
				// 2. 付け替え会社の基軸通貨と異なるため、基軸通貨金額再計算
				Currency currency = dtl.getCurrency();
				if (currency == null) {
					if (Util.equals(dtl.getSWK_CUR_CODE(), getCompany().getAccountConfig().getKeyCurrency().getCode())) {
						currency = getCompany().getAccountConfig().getKeyCurrency();
					} else {
						currency = getCurrency(getCompany().getCode(), dtl.getSWK_CUR_CODE());
					}
				}

				if (currency != null) {
					dtl.setCurrency(currency);
				}

				// 基軸通貨金額＝入力金額×(÷)発生日レート
				BigDecimal rate = getCurrentRate(getCompany(), currency, dtl.getHAS_DATE());
				BigDecimal kin = convertToBase(getCompany(), dtl.getSWK_IN_KIN(), rate, currency);
				BigDecimal zeiKin = convertToBase(getCompany(), dtl.getSWK_IN_ZEI_KIN(), rate, currency);

				dtl.setSWK_CUR_RATE(rate);
				dtl.setSWK_KIN(kin);
				dtl.setSWK_ZEI_KIN(zeiKin);
			}
		}

		return buildDetail(dtl, kcompany);
	}

	/**
	 * 通貨情報の取得
	 * 
	 * @param companyCode
	 * @param currencyCode
	 * @return 通貨情報
	 */
	protected Currency getCurrency(String companyCode, String currencyCode) {
		try {

			CurrencySearchCondition condition = new CurrencySearchCondition();
			condition.setCompanyCode(companyCode);
			condition.setCode(currencyCode);

			List<Currency> list = (List<Currency>) request(CurrencyManager.class, "get", condition);
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}

			// 会社[{0}] 通貨[{1}]が登録されていません。
			ClientLogger.error(getMessage("W01124", getWord("C00053") + "[" + company + "]" + getWord("C00371") + "["
				+ currencyCode + "]"));

			return null;
		} catch (TException e) {
			ClientLogger.error(e);
			return null;
		}
	}

	/**
	 * レートの取得
	 * 
	 * @param kcompany 計上会社
	 * @param currency 明細通貨
	 * @param baseDate 基準日
	 * @return レート
	 */
	protected BigDecimal getCurrentRate(Company kcompany, Currency currency, Date baseDate) {

		if (kcompany == null || currency == null || baseDate == null) {
			return null;
		}

		if (kcompany.getAccountConfig().getKeyCurrency().getCode().equals(currency.getCode())) {
			return BigDecimal.ONE;
		}

		try {
			BigDecimal rate = (BigDecimal) request(RateManager.class, "getRate", currency, baseDate);

			if (rate == null) {
				// 会社[{0}]通貨[{1}]発生日[{2}]の自社レートが設定されていません。
				ClientLogger.error(getMessage("I00339", kcompany.getCode(), currency.getCode(),
					DateUtil.toYMDString(baseDate)));
			}

			return rate;
		} catch (TException e) {
			ClientLogger.error(e);
			return null;
		}
	}

	/**
	 * 入力金額→基軸金額
	 * 
	 * @param kcompany
	 * @param foreignAmount 外貨金額
	 * @param rate レート
	 * @param currency 外貨通貨
	 * @return 基軸通貨金額
	 */
	public BigDecimal convertToBase(Company kcompany, BigDecimal foreignAmount, BigDecimal rate, Currency currency) {

		if (rate == null) {
			return null;
		}

		if (foreignAmount == null) {
			return null;
		}

		if (currency == null) {
			return null;
		}

		if (DecimalUtil.isNullOrZero(rate)) {
			return BigDecimal.ZERO;
		}

		if (DecimalUtil.isNullOrZero(foreignAmount)) {
			return BigDecimal.ZERO;
		}

		AccountConfig conf = kcompany.getAccountConfig();

		// 換算
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(conf.getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(conf.getKeyCurrency().getDecimalPoint());
		param.setForeignAmount(foreignAmount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
	}

	/**
	 * @param bs BS消込
	 * @return 行摘要
	 */
	public String getBsDetailRowRemark(SWK_DTL bs) {
		String prefix = "";
		try {
			prefix = ClientConfig.getProperty("trans.slip.bs.detail.row.remark.prefix");
		} catch (Throwable e) {
			// エラーなし
		}

		return StringUtil.leftBX(prefix + bs.getSWK_DEN_NO() + " " + bs.getSWK_GYO_TEK(), 80);
	}

	/**
	 * 仕訳のエンティティを擬似的にセット.
	 * 
	 * @param dtl 仕訳
	 * @return 仕訳
	 */
	public SWK_DTL buildDetail(SWK_DTL dtl) {
		return buildDetail(dtl, getCompany());
	}

	/**
	 * 仕訳のエンティティを擬似的にセット.
	 * 
	 * @param dtl 仕訳
	 * @param kcompany 計上会社コード
	 * @return 仕訳
	 */
	public SWK_DTL buildDetail(SWK_DTL dtl, Company kcompany) {

		String companyCode = dtl.getSWK_K_KAI_CODE();

		// 計上会社(自社以外なし前提)
		// 自社以外の仕訳を消し込みたい場合は、サーバから取得するようにカスタマイズを.
		dtl.setAppropriateCompany(kcompany);

		// 計上部門
		Department dept = createDepartment();
		dept.setCompanyCode(companyCode);
		dept.setCode(dtl.getSWK_DEP_CODE());
		dept.setNames(dtl.getSWK_DEP_NAME_S());
		dtl.setDepartment(dept);

		// 科目
		Item kmk = createitem();
		kmk.setCompanyCode(companyCode);
		kmk.setCode(dtl.getSWK_KMK_CODE());
		kmk.setNames(dtl.getSWK_KMK_NAME_S());

		// 補助科目
		if (!Util.isNullOrEmpty(dtl.getSWK_HKM_CODE())) {
			SubItem hkm = createSubItem();
			hkm.setCompanyCode(companyCode);
			hkm.setCode(dtl.getSWK_HKM_CODE());
			hkm.setNames(dtl.getSWK_HKM_NAME_S());
			kmk.setSubItem(hkm);

			// 内訳科目
			if (!Util.isNullOrEmpty(dtl.getSWK_UKM_CODE())) {
				DetailItem ukm = createDetailItem();
				ukm.setCompanyCode(companyCode);
				ukm.setCode(dtl.getSWK_UKM_CODE());
				ukm.setNames(dtl.getSWK_UKM_NAME_S());
				hkm.setDetailItem(ukm);
			}
		}

		dtl.setItem(kmk);

		// 通貨
		Currency currency = createCurrency();
		currency.setCompanyCode(companyCode);
		currency.setCode(dtl.getSWK_CUR_CODE());
		currency.setDecimalPoint(dtl.getCUR_DEC_KETA());
		currency.setRatePow(dtl.getCUR_RATE_POW());
		dtl.setCurrency(currency);

		return dtl;
	}

	/**
	 * 会社情報を戻します。
	 * 
	 * @return 会社情報
	 */
	@Override
	public Company getCompany() {
		if (company != null) {
			return company;
		}
		return TLoginInfo.getCompany();
	}

	/**
	 * 会社の設定
	 * 
	 * @param company 会社
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return 明細仕訳
	 */
	public SWK_DTL createSlipDetail() {
		return new SWK_DTL();
	}

	/**
	 * @return 部門
	 */
	public Department createDepartment() {
		return new Department();
	}

	/**
	 * @return 科目
	 */
	public Item createitem() {
		return new Item();
	}

	/**
	 * @return 補助科目
	 */
	public SubItem createSubItem() {
		return new SubItem();
	}

	/**
	 * @return 内訳科目
	 */
	public DetailItem createDetailItem() {
		return new DetailItem();
	}

	/**
	 * @return 通貨
	 */
	public Currency createCurrency() {
		return new Currency();
	}
}
