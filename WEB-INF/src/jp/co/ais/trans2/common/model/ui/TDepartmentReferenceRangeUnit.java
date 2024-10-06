package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * ����͈͌��� + �ʑI���t�B�[���h
 * 
 * @author AIS
 */
public class TDepartmentReferenceRangeUnit extends TReferenceRangeUnit {

	/** �͈̓t�B�[���h */
	public TDepartmentReferenceRange ctrlReferenceRange;

	/** �ʑI���t�B�[���h */
	public TDepartmentOptionalSelector ctrlOptionalSelector;

	/**
	 * �R���X�g���N�^.
	 */
	public TDepartmentReferenceRangeUnit() {
		super();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param border
	 */
	public TDepartmentReferenceRangeUnit(boolean border) {
		super(border);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param border
	 * @param title
	 */
	public TDepartmentReferenceRangeUnit(boolean border, boolean title) {
		super(border, title);
	}

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TDepartmentReferenceRange();
		ctrlOptionalSelector = new TDepartmentOptionalSelector();
	}

	@Override
	public TDepartmentReferenceRange getReferenceRange() {
		return ctrlReferenceRange;
	}

	@Override
	public TDepartmentOptionalSelector getOptionalSelector() {
		return ctrlOptionalSelector;
	}

	@Override
	public String getBorderTitle() {
		// ����͈�
		return TModelUIUtil.getWord("C11128");
	}
}
