package jp.co.ais.trans2.common.model.format;

/**
 * 通常外貨のフォーマッタ(括弧なし)
 * 
 * @author AIS
 */
public class NormalForeignAmountFormat extends ForeignAmountFormat {

	/**
	 * @return 外貨先頭
	 */
	@Override
	protected String getPrefix() {
		return "";
	}

	/**
	 * @return 外貨末
	 */
	@Override
	protected String getSuffix() {
		return "";
	}
}
