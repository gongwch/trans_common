package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;

/**
 * USR_SIGN �{�^��
 */
public class TSignAttachButton extends TImageButton {

	/** �e�p�l�� */
	public TPanel parent = null;

	/** �e�p�l�� */
	public TDialog dialog = null;

	/**
	 * �R���|�[�l���g������
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
