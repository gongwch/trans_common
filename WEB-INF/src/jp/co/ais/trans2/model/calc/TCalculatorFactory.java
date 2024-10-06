package jp.co.ais.trans2.model.calc;

/**
 * 計算関連Factory
 */
public class TCalculatorFactory {

	//TODO S2で差し替え
	
	/**
	 * TCalculator取得
	 * 
	 * @return TCalculator
	 */
	public static TCalculator getCalculator() {
		return new TCalculator();
	}

	/**
	 * 外貨換算パラメータを生成
	 * 
	 * @return 換算パラメータ
	 */
	public static TExchangeAmount createExchangeAmount() {
		return new TExchangeAmount();
	}

	/**
	 * 税額計算パラメータを生成
	 * 
	 * @return 税額計算パラメータ
	 */
	public static TTaxCalculation createTaxCalculation() {
		return new TTaxCalculation();
	}
}
