package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * ��s�����͈͌��� + �ʑI���t�B�[���h
 * 
 * @author AIS
 */
public class TBankAccountReferenceRangeUnit extends TReferenceRangeUnit {

	/** �͈̓t�B�[���h */
	public TBankAccountReferenceRange ctrlReferenceRange;

	/** �ʑI���t�B�[���h */
	public TBankAccountOptionalSelector ctrlOptionalSelector;

	/**
	 * �R���X�g���N�^
	 */
	public TBankAccountReferenceRangeUnit() {
		super(false);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param border
	 */
	public TBankAccountReferenceRangeUnit(boolean border) {
		super(border, false);
	}

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TBankAccountReferenceRange();
		ctrlOptionalSelector = new TBankAccountOptionalSelector();
	}

	@Override
	public TReferenceRange getReferenceRange() {
		return ctrlReferenceRange;
	}

	@Override
	public TOptionalSelector getOptionalSelector() {
		return ctrlOptionalSelector;
	}

	@Override
	public String getBorderTitle() {
		// ��s�����͈�
		return TModelUIUtil.getWord("C11101");
	}

}
