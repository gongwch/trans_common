package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * �C�ӑI���R���|�[�l���g(�u�����N)
 * @author AIS
 *
 */
public class TNoneOptionalSelector extends TOptionalSelector {

	/** serialVersionUID */
	private static final long serialVersionUID = -3641547109215684699L;

	public TNoneOptionalSelector() {
		setEnabled(false);
	}

	@Override
	public TOptionalSelectorController getController() {
		return null;
	}
	
}
