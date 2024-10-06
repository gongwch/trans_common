package jp.co.ais.trans.master.common;

import java.math.*;

import jp.co.ais.trans.common.util.*;

/**
 * �t�H�[�}�b�g�ێ��̐��l�N���X
 */
public class FormatDecimal {

	/** �l */
	BigDecimal value = null;

	/** �t�H�[�}�b�g */
	String format = null;

	/** �����_�ȉ����� */
	int digit = 0;
	
	/**
	 * �R���X�g���N�^
	 * 
	 * @param value �l
	 * @param format �t�H�[�}�b�g
	 */
	public FormatDecimal(int value, String format) {
		this(new BigDecimal(value), format);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param value �l
	 * @param format �t�H�[�}�b�g
	 */
	public FormatDecimal(double value, String format) {
		this(new BigDecimal(value), format);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param value �l
	 * @param format �t�H�[�}�b�g
	 */
	public FormatDecimal(BigDecimal value, String format) {
		this.value = value;
		this.format = format;
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param value �l
	 * @param digit �t�H�[�}�b�g
	 */
	public FormatDecimal(BigDecimal value, int digit) {
		this.value = value;
		this.format = NumberFormatUtil.makeNumberFormat(digit);
	}

	
	/**
	 * �l�擾
	 * 
	 * @return �l
	 */
	public double getValue() {
		return this.value.doubleValue();
	}

	/**
	 * �t�H�[�}�b�g�擾
	 * 
	 * @return �t�H�[�}�b�g
	 */
	public String getFormat() {
		return this.format;
	}
}
