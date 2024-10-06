package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * �͈͌��� + �ʑI���t�B�[���h(�u�����N)
 * @author AIS
 *
 */
public class TNoneReferenceRangeUnit extends TReferenceRangeUnit {

	/** serialVersionUID */
	private static final long serialVersionUID = -7792095504739309324L;

	/** �͈̓t�B�[���h */
	public TNoneReferenceRange ctrlReferenceRange;

	/** �ʑI���t�B�[���h */
	public TNoneOptionalSelector ctrlNoneSelector;

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TNoneReferenceRange();
		ctrlNoneSelector = new TNoneOptionalSelector();
	}

	@Override
	public TReferenceRange getReferenceRange() {
		return ctrlReferenceRange;
	}

	@Override
	public TOptionalSelector getOptionalSelector() {
		return ctrlNoneSelector;
	}

	@Override
	public String getBorderTitle() {
		return "";
	}

}
