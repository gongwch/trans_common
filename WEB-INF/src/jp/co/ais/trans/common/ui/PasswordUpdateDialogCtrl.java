package jp.co.ais.trans.common.ui;

import java.awt.*;
import java.io.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;

/**
 * �p�X���[�h�ύX�_�C�A���O�R���g���[��
 */
public class PasswordUpdateDialogCtrl extends TAppletClientBase {

	/** �v���O����ID */
	protected static final String PROGRAM_ID = "MG0025";

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "common/ChangePassword";

	/** �_�C�A���O */
	protected PasswordUpdateDialog dialog;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �p�l��
	 */
	public PasswordUpdateDialogCtrl(Frame parent) {
		try {
			dialog = new PasswordUpdateDialog(parent, this);

			// ���[�U�[�R�[�h�ƃ��[�U�[���̂̕\��
			dialog.ctrlUserCode.getField().setText(getLoginUserID());
			dialog.txtUserName.setText(getLoginUserName());

		} catch (Exception e) {
			errorHandler(dialog, e, "E00010");
		}
	}

	/**
	 * �\��
	 */
	public void show() {
		dialog.setVisible(true);
	}

	/**
	 * ����
	 */
	protected void disposeDialog() {
		try {
			// �m��{�^�����ǂ����`�F�b�N
			if (!dialog.isSettle) {
				dialog.setVisible(false);
				return;
			}

			// �m��{�^������
			if (!checkField()) {
				return;
			}

			addSendValues("newPassword", new String(dialog.ctrlNewPassword.getField().getPassword()));
			addSendValues("prgID", PROGRAM_ID);

			if (!request(TARGET_SERVLET)) {
				errorHandler(dialog);
				return;
			}

			showMessage(dialog, "I00008");

			dialog.setVisible(false);

		} catch (IOException ex) {
			errorHandler(dialog, ex, "E00009");
		}
	}

	/**
	 * ���̓`�F�b�N
	 * 
	 * @return true ���͂ɖ��Ȃ� false ��肠��
	 */
	protected boolean checkField() {

		if (Util.isNullOrEmpty(dialog.ctrlNewPassword.getValue())) {
			showMessage(dialog, "I00002", "C00742");
			dialog.ctrlNewPassword.getField().requestFocus();
			return false;
		}

		if (Util.isNullOrEmpty(dialog.ctrlNewPasswordConfirmation.getValue())) {
			showMessage(dialog, "I00002", "C00305");
			dialog.ctrlNewPasswordConfirmation.getField().requestFocus();
			return false;
		}

		if (!dialog.ctrlNewPassword.getValue().equals(dialog.ctrlNewPasswordConfirmation.getValue())) {
			showMessage(dialog, "W00074", "");
			dialog.ctrlNewPasswordConfirmation.getField().requestFocus();
			return false;
		}

		return true;
	}
}
