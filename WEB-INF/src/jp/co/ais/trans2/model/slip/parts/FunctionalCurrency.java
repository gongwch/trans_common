package jp.co.ais.trans2.model.slip.parts;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.slip.*;

/**
 * 機能通貨関連
 */
public class FunctionalCurrency extends TModel {

	/** 通貨レートマネージャ */
	protected RateManager rateManager;

	/** 差損益用 */
	protected LossOrProfit lossOrProfit;

	/** レート */
	protected Map<String, Map<String, BigDecimal>> rateMap = new TreeMap<String, Map<String, BigDecimal>>();

	/** 計上会社 */
	protected Company kcompany;

	/**
	 * 機能通貨仕訳の追加
	 * 
	 * @param motoSlip 元伝票
	 * @return 作成後伝票
	 * @throws TException
	 */
	public Slip create(Slip motoSlip) throws TException {
		rateMap.clear();

		// 伝票の会社情報
		CompanyManager companyManager = (CompanyManager) getComponent(CompanyManager.class);
		kcompany = companyManager.get(motoSlip.getCompanyCode());

		lossOrProfit = (LossOrProfit) getComponent(LossOrProfit.class);
		lossOrProfit.setCompany(kcompany);

		Slip slip = motoSlip.clone();
		slip.setCompany(kcompany);
		slip.getHeader().setAccountBook(AccountBook.IFRS); // 調整区分をIFRSに

		// 機能通貨仕訳に置き換え
		makeFunctional(slip);

		// 消費税仕訳の追加
		if (!slip.isJournalizedTax()) {
			addTax(slip);
		}

		// 為替差損益の仕訳作成
		lossOrProfit.addLossOrProfit(slip, CurrencyType.FUNCTIONAL);

		// 形成
		slip.buildSlip();

		return slip;
	}

	/**
	 * 機能通貨仕訳の追加
	 * 
	 * @param slip 対象伝票
	 * @throws TException
	 */
	protected void makeFunctional(Slip slip) throws TException {

		AccountConfig config = kcompany.getAccountConfig(); // 会計情報
		Currency keyCurrency = config.getKeyCurrency(); // 基軸通貨
		Currency functionCurrency = config.getFunctionalCurrency(); // 機能通貨

		// IFRS仕訳作成
		List<SWK_DTL> list = new ArrayList<SWK_DTL>();

		// 基軸と機能が同じ場合は、内容はそのままでIFRS仕訳を追加
		if (keyCurrency.getCode().equals(functionCurrency.getCode())) {
			for (SWK_DTL dtl : slip.getDetails()) {
				SWK_DTL fdtl = dtl.clone();
				fdtl.setCurrencyType(CurrencyType.FUNCTIONAL);
				fdtl.setAccountBook(AccountBook.IFRS);

				list.add(fdtl);
			}

			slip.setDetails(list);

			return;
		}

		// 機能通貨コード
		String funcCurrncyCode = functionCurrency.getCode();

		// 計算機
		TCalculator calculator = TCalculatorFactory.getCalculator();
		TExchangeAmount exchangeParam = TCalculatorFactory.createExchangeAmount(); // 引数
		exchangeParam.setConvertType(config.getConvertType() == ConvertType.MULTIPLICATION ? ConvertType.DIVIDE
			: ConvertType.MULTIPLICATION); // 換算方法(掛/割) 該当会社の逆
		exchangeParam.setExchangeFraction(config.getExchangeFraction()); // 換算方法(丸め)
		exchangeParam.setDigit(functionCurrency.getDecimalPoint()); // 機能通貨の小数点桁数

		for (SWK_DTL dtl : slip.getDetails()) {
			// 為替差損益仕訳は除外
			if (lossOrProfit.isProfitOrLoss(dtl)) {
				continue;
			}

			// 機能通貨仕訳行
			SWK_DTL fdtl = dtl.clone();
			fdtl.setCurrencyType(CurrencyType.FUNCTIONAL);
			fdtl.setAccountBook(AccountBook.IFRS);

			// 仕訳行の通貨コード
			String curCode = fdtl.getSWK_CUR_CODE();

			// 仕訳通貨＝機能通貨
			if (funcCurrncyCode.equals(curCode)) {
				fdtl.setSWK_CUR_RATE(BigDecimal.ONE); // レート
				fdtl.setSWK_KIN(fdtl.getSWK_IN_KIN()); // 邦貨金額
				fdtl.setSWK_ZEI_KIN(fdtl.getSWK_IN_ZEI_KIN()); // 消費税額(邦貨)

				list.add(fdtl);
				continue;
			}

			// 仕訳通貨≠機能通貨 →換算

			// 機能通貨レート
			BigDecimal funcRate = getRate(fdtl);
			fdtl.setSWK_CUR_RATE(funcRate);

			exchangeParam.setRate(funcRate);
			exchangeParam.setRatePow(dtl.getCUR_RATE_POW()); // レート係数

			// 邦貨
			exchangeParam.setForeignAmount(dtl.getSWK_IN_KIN());
			fdtl.setSWK_KIN(calculator.exchangeKeyAmount(exchangeParam));

			// 消費税額(邦貨)
			if (dtl.getSWK_IN_ZEI_KIN() != null) {
				exchangeParam.setForeignAmount(dtl.getSWK_IN_ZEI_KIN());
				fdtl.setSWK_ZEI_KIN(calculator.exchangeKeyAmount(exchangeParam));

			} else {
				fdtl.setSWK_ZEI_KIN(null);
			}

			// 消込情報クリア
			fdtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);
			fdtl.setSWK_KESI_DATE(null); // 消込伝票日付
			fdtl.setSWK_KESI_DEN_NO(null); // 消込伝票番号
			fdtl.setSWK_SOUSAI_DATE(null); // 相殺伝票日付
			fdtl.setSWK_SOUSAI_DEN_NO(null); // 相殺伝票番号
			fdtl.setSWK_SOUSAI_GYO_NO(null); // 相殺行番号

			fdtl.setSWK_APAR_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);// AP/AR消込区分
			fdtl.setSWK_APAR_DEN_NO(null);// AP/AR消込伝票番号
			fdtl.setSWK_APAR_GYO_NO(null);// AP/AR消込行番号

			fdtl.setAP_ZAN(null);
			fdtl.setAR_ZAN(null);
			fdtl.setBsDetail(null);

			list.add(fdtl);
		}

		slip.setDetails(list);
	}

	/**
	 * 機能通貨仕訳の消費税仕訳を追加
	 * 
	 * @param slip 伝票
	 * @throws TException
	 */
	protected void addTax(Slip slip) throws TException {
		TaxJournalIssuer logic = (TaxJournalIssuer) getComponent(TaxJournalIssuer.class);
		logic.setCurrencyType(CurrencyType.FUNCTIONAL);
		logic.addJournal(slip, slip.getDetailCount());
	}

	/**
	 * 機能通貨レート取得
	 * 
	 * @param dtl 対象仕訳
	 * @return レート
	 * @throws TException
	 */
	protected BigDecimal getRate(SWK_DTL dtl) throws TException {
		if (rateManager == null) {
			rateManager = (RateManager) getComponent(RateManager.class);
		}

		// レート基準日(発生日)
		Date rateDate = dtl.getHAS_DATE();
		if (rateDate == null) {
			rateDate = dtl.getSWK_DEN_DATE(); // 発生日が無い場合は伝票日付のレート
		}

		// 仕訳行の通貨コード
		String curCode = dtl.getSWK_CUR_CODE();

		return getRate(rateDate, curCode);
	}

	/**
	 * 機能通貨レート取得
	 * 
	 * @param date 対象日
	 * @param curCode 通貨コード
	 * @return レート
	 * @throws TException
	 */
	protected BigDecimal getRate(Date date, String curCode) throws TException {
		if (kcompany.getAccountConfig().getFunctionalCurrency().getCode().equals(curCode)) {
			return BigDecimal.ONE;
		}

		Map<String, BigDecimal> map = rateMap.get(curCode);

		if (map == null) {
			map = new TreeMap<String, BigDecimal>();
			rateMap.put(curCode, map);
		}

		BigDecimal rate = map.get(DateUtil.toYMDString(date));

		if (rate == null) {
			// 対象日付から最新のレート取得
			rateManager.setCompany(kcompany);
			rate = rateManager.getFunctionalRate(curCode, date);

			if (rate == null) {
				throw new TException(getMessage("I00158", kcompany.getCode(), curCode, DateUtil.toYMDString(date)));
				// 会社[{0}] 通貨[{1}] 発生日[{2}] の機能通貨レートが設定されていません。
			}

			map.put(DateUtil.toYMDString(date), rate);
		}

		return rate;
	}
}
