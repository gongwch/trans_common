package jp.co.ais.trans2.common.excel;

/**
 * エクセルのカラム種別
 */
public enum ExcelColumnType {
	/** 文字 */
	STRING,
	/** 文字(ｶﾅ文字のみ) */
	STRING_KANA,
	/** 文字(英数のみ) */
	ALPHANUMERIC,
	/** 文字(英数記号のみ) */
	ALPHANUMERIC_SYMBOLS,
	/** 整数 */
	INTEGER,
	/** 数値 */
	DECIMAL,
	/** 日付 */
	DATE,

}
