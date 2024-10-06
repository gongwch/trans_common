package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;

/**
 * �͈͌����t�B�[���h(�u�����N)
 * @author AIS
 *
 */
public class TNoneReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = 8640447660696025253L;

	/** �J�n�t�B�[���h */
	public TNoneReference ctrlNoneReferenceFrom;

	/** �I���t�B�[���h */
	public TNoneReference ctrlNoneReferenceTo;

	@Override
	public void initComponents() {
		ctrlNoneReferenceFrom = new TNoneReference();
		ctrlNoneReferenceTo = new TNoneReference();
	}

	@Override
	public TReference getFieldFrom() {
		return ctrlNoneReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlNoneReferenceTo;
	}

}
