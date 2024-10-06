package jp.co.ais.trans.master.common;

import java.math.*;

import jp.co.ais.trans.common.util.*;

/**
 * フォーマット保持の数値クラス
 */
public class FormatDecimal {

	/** 値 */
	BigDecimal value = null;

	/** フォーマット */
	String format = null;

	/** 小数点以下桁数 */
	int digit = 0;
	
	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 * @param format フォーマット
	 */
	public FormatDecimal(int value, String format) {
		this(new BigDecimal(value), format);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 * @param format フォーマット
	 */
	public FormatDecimal(double value, String format) {
		this(new BigDecimal(value), format);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 * @param format フォーマット
	 */
	public FormatDecimal(BigDecimal value, String format) {
		this.value = value;
		this.format = format;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 * @param digit フォーマット
	 */
	public FormatDecimal(BigDecimal value, int digit) {
		this.value = value;
		this.format = NumberFormatUtil.makeNumberFormat(digit);
	}

	
	/**
	 * 値取得
	 * 
	 * @return 値
	 */
	public double getValue() {
		return this.value.doubleValue();
	}

	/**
	 * フォーマット取得
	 * 
	 * @return フォーマット
	 */
	public String getFormat() {
		return this.format;
	}
}
