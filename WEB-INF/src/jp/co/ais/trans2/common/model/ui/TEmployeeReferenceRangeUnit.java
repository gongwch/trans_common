package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * �Ј��͈͌��� + �ʑI���t�B�[���h
 * 
 * @author AIS
 */
public class TEmployeeReferenceRangeUnit extends TReferenceRangeUnit {

	/** serialVersionUID */
	private static final long serialVersionUID = 1829071159623026452L;

	/** �͈̓t�B�[���h */
	public TEmployeeReferenceRange ctrlReferenceRange;

	/** �ʑI���t�B�[���h */
	public TEmployeeOptionalSelector ctrlOptionalSelector;

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TEmployeeReferenceRange();
		ctrlOptionalSelector = new TEmployeeOptionalSelector();
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
		// �Ј��͈�
		return TModelUIUtil.getWord("C11131");
	}

}
