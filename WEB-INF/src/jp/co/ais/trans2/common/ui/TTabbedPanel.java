package jp.co.ais.trans2.common.ui;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;

/**
 * TAB�\�ȃp�l��
 */
public class TTabbedPanel extends TPanelBusiness {

	/** TAB�@�\�_�C�A���O */
	protected TTabbedDialog dialog = null;

	/**
	 * TAB�@�\�_�C�A���O�̎擾
	 * 
	 * @return dialog TAB�@�\�_�C�A���O
	 */
	public TTabbedDialog getDialog() {
		return dialog;
	}

	/**
	 * TAB�@�\�_�C�A���O�̐ݒ�
	 * 
	 * @param dialog TAB�@�\�_�C�A���O
	 */
	public void setDialog(TTabbedDialog dialog) {
		this.dialog = dialog;
	}

	/**
	 * �p�l���̃A�C�e���̃��b�Z�[�WID��ϊ�����.
	 */
	@Override
	protected void translateLangMessageID() {
		TGuiUtil.recursiveTranslateLangMessageID(this, TClientLoginInfo.getInstance().getUserLanguage(), 0);
	}

	/**
	 * ACTIVE����
	 */
	public void active() {
		if (dialog != null) {
			dialog.active();
		}
	}
}
