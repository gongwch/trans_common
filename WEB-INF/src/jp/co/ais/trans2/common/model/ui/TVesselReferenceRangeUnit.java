package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * �D�͈͌��� + �ʑI���t�B�[���h
 * 
 * @author AIS
 */
public class TVesselReferenceRangeUnit extends TReferenceRangeUnit {

	/** �͈̓t�B�[���h */
	public TVesselReferenceRange ctrlReferenceRange;

	/** �ʑI���t�B�[���h */
	public TVesselOptionalSelector ctrlOptionalSelector;

	/**
	 * �R���X�g���N�^.
	 */
	public TVesselReferenceRangeUnit() {
		super();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param border
	 */
	public TVesselReferenceRangeUnit(boolean border) {
		super(border);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param border
	 * @param title
	 */
	public TVesselReferenceRangeUnit(boolean border, boolean title) {
		super(border, title);
	}

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TVesselReferenceRange();
		ctrlOptionalSelector = new TVesselOptionalSelector();

	}

	@Override
	public TVesselReferenceRange getReferenceRange() {
		return ctrlReferenceRange;
	}

	@Override
	public TVesselOptionalSelector getOptionalSelector() {
		return ctrlOptionalSelector;
	}

	@Override
	public String getBorderTitle() {
		// �D�͈�
		return TModelUIUtil.getWord("C11761"); // �D�͈�
	}
}
