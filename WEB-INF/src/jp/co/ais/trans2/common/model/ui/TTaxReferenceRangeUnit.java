package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * ����Ŕ͈͌����t�B�[���h + �ʑI���t�B�[���h
 * 
 * @author AIS
 */
public class TTaxReferenceRangeUnit extends TReferenceRangeUnit {

	/** �͈̓t�B�[���h */
	public TTaxReferenceRange ctrlTaxReferenceRange;

	/** ����ŌʑI�� */
	public TTaxOptionalSelector ctrlTaxOptionalSelector;

	/** */
	public TTaxReferenceRangeUnit() {
		super(false);
	}

	/**
	 * @param border �{�[�_�[��\������ꍇtrue
	 */
	public TTaxReferenceRangeUnit(boolean border) {
		super(border);
	}

	/**
	 * @param border �{�[�_�[��\������ꍇtrue
	 * @param title �^�C�g���\�����ǂ���
	 */
	public TTaxReferenceRangeUnit(boolean border, boolean title) {
		super(border, title);
	}

	/**
	 * �R���|�[�l���g�̏�����
	 */
	@Override
	public void initComponents() {
		ctrlTaxReferenceRange = new TTaxReferenceRange();
		ctrlTaxOptionalSelector = new TTaxOptionalSelector();
	}

	/**
	 * �C�ӑI��(�ʎw��)�R���|�[�l���g��getter
	 * 
	 * @return �C�ӑI��(�ʎw��)�R���|�[�l���g
	 */
	@Override
	public TOptionalSelector getOptionalSelector() {
		return ctrlTaxOptionalSelector;
	}

	/**
	 * �C�ӑI��(�ʎw��)�R���|�[�l���g��getter
	 * 
	 * @return �C�ӑI��(�ʎw��)�R���|�[�l���g
	 */
	@Override
	public TReferenceRange getReferenceRange() {
		return ctrlTaxReferenceRange;
	}

	/**
	 * �J�n�t�B�[���h�őI�����ꂽ�Ȗ�Entity��Ԃ�
	 * 
	 * @return �J�n�t�B�[���h�őI�����ꂽ�Ȗ�Entity
	 */
	public ConsumptionTax getEntityFrom() {
		return ctrlTaxReferenceRange.getEntityFrom();
	}

	/**
	 * �I���t�B�[���h�őI�����ꂽ�Ȗ�Entity��Ԃ�
	 * 
	 * @return �I���t�B�[���h�őI�����ꂽ�Ȗ�Entity
	 */
	public ConsumptionTax getEntityTo() {
		return ctrlTaxReferenceRange.getEntityTo();
	}

	/**
	 * �{�[�_�[�̃^�C�g����Ԃ�
	 * 
	 * @return �{�[�^�[�̃^�C�g��
	 */
	@Override
	public String getBorderTitle() {
		// �I��
		return TModelUIUtil.getWord("C00286");
	}

}
