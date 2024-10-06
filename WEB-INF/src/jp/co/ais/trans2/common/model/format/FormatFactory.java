package jp.co.ais.trans2.common.model.format;

import jp.co.ais.trans.common.config.*;

/**
 * 業務フォーマッタのファクトリ
 * 
 * @author AIS
 */
public class FormatFactory {

	/**
	 * 外貨のフォーマットを返す
	 * 
	 * @return 外貨のフォーマット
	 */
	public static ForeignAmountFormat getForeignAmountFormat() {

		try {
			if (ServerConfig.isFlagOn("trans.foreign.amount.no.brackets")) {
				// 括弧なし場合、通常外貨フォーマッタを返す
				return new NormalForeignAmountFormat();
			}
		} catch (Throwable e) {
			// 処理なし
		}

		return new ForeignAmountFormat();
	}

	/**
	 * レートのフォーマットを返す
	 * 
	 * @return レートのフォーマット
	 */
	public static RateFormat getRateFormat() {
		return new RateFormat();
	}

	/**
	 * 通貨のフォーマットを返す
	 * 
	 * @return 通貨のフォーマット
	 */
	public static CurrencyFormat getCurrencyFormat() {
		return new CurrencyFormat();
	}

	/**
	 * 決算段階のフォーマットを返す
	 * 
	 * @return 決算段階のフォーマット
	 */
	public static SettlementLevelFormat getSettlementLevelFormat() {
		return new SettlementLevelFormat();
	}
}
