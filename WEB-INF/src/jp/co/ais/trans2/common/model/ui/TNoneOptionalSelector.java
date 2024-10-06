package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * 任意選択コンポーネント(ブランク)
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
