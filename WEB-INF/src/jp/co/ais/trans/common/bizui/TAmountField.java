package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���z�t�B�[���h.<br>
 * �w�肳�ꂽ�ʉ݃R�[�h�ɑ΂��鏬���_�����������Ŏ擾���A�t�H�[�}�b�g�ݒ���s��.
 */
public class TAmountField extends TNumericField {

	/** �ʉ݃R�[�h */
	private String currencyCode;

	/** �����_���� */
	private int digit = 0;

	/**
	 * �R���X�g���N�^.<br>
	 * ���O�C�����[�U�̉�ЂɕR�Â��ʉ݃R�[�h�������Őݒ�
	 */
	public TAmountField() {
		this(TClientLoginInfo.getInstance().getCompanyInfo().getBaseCurrencyCode());
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 */
	public TAmountField(String currencyCode) {
		super();

		this.currencyCode = currencyCode;
		setupDigit();

		super.setPositiveOnly(true); // �����̂�
		super.setMaxLength(17);
	}

	/**
	 * �ʉ݃R�[�h�ɑ΂��鏬���_������ݒ�
	 */
	private void setupDigit() {

		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();

		this.digit = compInfo.getCurrencyDigit(this.currencyCode);

		super.setNumericFormat(NumberFormatUtil.makeNumberFormat(digit));
	}

	/**
	 * �ʉ݃R�[�h
	 * 
	 * @return �ʉ݃R�[�h
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * �ʉ݃R�[�h
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
		setupDigit();
	}

	/**
	 * �����_����
	 * 
	 * @return �����_����
	 */
	public int getDigit() {
		return digit;
	}
}
