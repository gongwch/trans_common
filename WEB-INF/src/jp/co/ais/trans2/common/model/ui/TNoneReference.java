package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * �����R���|�[�l���g(�u�����N)
 * @author AIS
 *
 */
public class TNoneReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = -1294503288035244144L;

	/** �R���g���[�� */
	protected TNoneReferenceController controller;

	/**
	 * 
	 *
	 */
	public TNoneReference() {
		controller = new TNoneReferenceController(this);
	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

}
