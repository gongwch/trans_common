package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;

/**
 * USR_SIGN ボタン
 */
public class TSignAttachButton extends TImageButton {

	/** 親パネル */
	public TPanel parent = null;

	/** 親パネル */
	public TDialog dialog = null;

	/**
	 * コンポーネント初期化
	 */
	protected void allocateComponents() {

		setSize(16, 16);
		setFocusable(false);
	}

	/**
	 * @param icon
	 */
	@Override
	public void setIconType(IconType icon) {
		super.setIconType(icon);
		this.repaint();
	}

}
