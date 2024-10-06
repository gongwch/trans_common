package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * ��Д͈͌��� + �ʑI���t�B�[���h
 * 
 * @author AIS
 */
public class TCompanyReferenceRangeUnit extends TReferenceRangeUnit {

	/** �͈̓t�B�[���h */
	public TCompanyReferenceRange ctrlReferenceRange;

	/** �ʑI���t�B�[���h */
	public TCompanyOptionalSelector ctrlOptionalSelector;

	/**
	 * �R���X�g���N�^.
	 */
	public TCompanyReferenceRangeUnit() {
		super();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param border
	 */
	public TCompanyReferenceRangeUnit(boolean border) {
		super(border);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param border
	 * @param title
	 */
	public TCompanyReferenceRangeUnit(boolean border, boolean title) {
		super(border, title);
	}

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TCompanyReferenceRange();
		ctrlOptionalSelector = new TCompanyOptionalSelector();
	}

	@Override
	public TCompanyReferenceRange getReferenceRange() {
		return ctrlReferenceRange;
	}

	@Override
	public TCompanyOptionalSelector getOptionalSelector() {
		return ctrlOptionalSelector;
	}

	@Override
	public String getBorderTitle() {
		// ��Д͈�
		return TModelUIUtil.getWord("C11368");
	}
}
