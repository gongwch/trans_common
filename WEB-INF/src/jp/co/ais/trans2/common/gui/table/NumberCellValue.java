package jp.co.ais.trans2.common.gui.table;

import java.math.*;

/**
 * ���l�Z���p�f�[�^
 */
public class NumberCellValue {

	/** ���͌� */
	protected int maxLength;

	/** �����_���� */
	protected int decimalPoint;

	/** ���l */
	protected BigDecimal number;

	/** �P���\�����[�h */
	protected boolean bunkerPrice = false;

	/**
	 * �����_����
	 * 
	 * @return �����_����
	 */
	public int getDecimalPoint() {
		return decimalPoint;
	}

	/**
	 * �����_����
	 * 
	 * @param decimalPoint �����_����
	 */
	public void setDecimalPoint(int decimalPoint) {
		this.decimalPoint = decimalPoint;
	}

	/**
	 * ���͌�
	 * 
	 * @return ���͌�
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * ���͌�
	 * 
	 * @param maxLength ���͌�
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * ���l
	 * 
	 * @return ���l
	 */
	public BigDecimal getNumber() {
		return number;
	}

	/**
	 * ���l
	 * 
	 * @param number ���l
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
	 * �P���\�����[�h�̎擾
	 * 
	 * @return bunkerPrice �P���\�����[�h
	 */
	public boolean isBunkerPrice() {
		return bunkerPrice;
	}

	/**
	 * �P���\�����[�h�̐ݒ�
	 * 
	 * @param bunkerPrice �P���\�����[�h
	 */
	public void setBunkerPrice(boolean bunkerPrice) {
		this.bunkerPrice = bunkerPrice;
	}

}