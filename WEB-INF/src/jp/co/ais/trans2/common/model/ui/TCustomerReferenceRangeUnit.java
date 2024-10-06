package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * �����͈͌��� + �ʑI���t�B�[���h
 * 
 * @author AIS
 */
public class TCustomerReferenceRangeUnit extends TReferenceRangeUnit {

	/** serialVersionUID */
	private static final long serialVersionUID = -7792095504739309324L;

	/** �͈̓t�B�[���h */
	public TCustomerReferenceRange ctrlReferenceRange;

	/** �ʑI���t�B�[���h */
	public TCustomerOptionalSelector ctrlOptionalSelector;

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TCustomerReferenceRange();
		ctrlOptionalSelector = new TCustomerOptionalSelector();
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
		// �����͈�
		return TModelUIUtil.getWord("C10338");
	}

}
