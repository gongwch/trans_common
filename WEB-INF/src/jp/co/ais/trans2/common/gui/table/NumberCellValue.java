package jp.co.ais.trans2.common.gui.table;

import java.math.*;

/**
 * 数値セル用データ
 */
public class NumberCellValue {

	/** 入力桁 */
	protected int maxLength;

	/** 小数点桁数 */
	protected int decimalPoint;

	/** 数値 */
	protected BigDecimal number;

	/** 単価表示モード */
	protected boolean bunkerPrice = false;

	/**
	 * 小数点桁数
	 * 
	 * @return 小数点桁数
	 */
	public int getDecimalPoint() {
		return decimalPoint;
	}

	/**
	 * 小数点桁数
	 * 
	 * @param decimalPoint 小数点桁数
	 */
	public void setDecimalPoint(int decimalPoint) {
		this.decimalPoint = decimalPoint;
	}

	/**
	 * 入力桁
	 * 
	 * @return 入力桁
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * 入力桁
	 * 
	 * @param maxLength 入力桁
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * 数値
	 * 
	 * @return 数値
	 */
	public BigDecimal getNumber() {
		return number;
	}

	/**
	 * 数値
	 * 
	 * @param number 数値
	 */
	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.number == null ? "" : this.number.toString();
	}

	/**
	 * 単価表示モードの取得
	 * 
	 * @return bunkerPrice 単価表示モード
	 */
	public boolean isBunkerPrice() {
		return bunkerPrice;
	}

	/**
	 * 単価表示モードの設定
	 * 
	 * @param bunkerPrice 単価表示モード
	 */
	public void setBunkerPrice(boolean bunkerPrice) {
		this.bunkerPrice = bunkerPrice;
	}

}