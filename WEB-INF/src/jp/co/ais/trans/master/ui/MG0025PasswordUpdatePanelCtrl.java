package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
import java.util.List;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * �p�X���[�h�ύX
 */
public class MG0025PasswordUpdatePanelCtrl extends TPanelCtrlBase {

	private MG0025PasswordUpdatePanel panel;

	private static final String PROGRAM_ID = "MG0025";

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0020UserMasterServlet";

	/**
	 * �R���X�g���N�^.
	 */
	public MG0025PasswordUpdatePanelCtrl() {
		try {
			panel = new MG0025PasswordUpdatePanel(this);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		getUsername();
		panel.btnReturn.setVisible(false);
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				panel.ctrlNewPassword.getField().requestFocus();
			}
		});
	}

	/**
	 * �p�l���擾
	 * 
	 * @return �ʉ݃}�X�^�p�l��
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * ����
	 * 
	 * @return boolean
	 */
	private boolean checkPanel() {
		if (Util.isNullOrEmpty(panel.ctrlNewPassword.getValue())) {
			showMessage(panel, "I00002", "C00742");
			panel.ctrlNewPassword.requestFocus();
			return false;
		}
		if (Util.isNullOrEmpty(panel.ctrlNewPasswordConfirmation.getValue())) {
			showMessage(panel, "I00002", "C00305");
			panel.ctrlNewPasswordConfirmation.requestFocus();
			return false;
		}
		return true;
	}

	void disposeDialog() {
		String newPassWord = panel.ctrlNewPassword.getValue();
		String oldPassWord = panel.ctrlNewPasswordConfirmation.getValue();

		if (panel.isSettle) {
			if (!checkPanel()) return;
			// �m��{�^������ �`�F�b�NOK�Ȃ����
			if (newPassWord.equals(oldPassWord)) {
				if (this.showConfirmMessage(panel, "Q00005", "")) {
					savePassword();
				}
			} else {
				showMessage(panel, "W00074", "");
				panel.ctrlNewPasswordConfirmation.getField().requestFocus();
				return;
			}
		} else {
			// �߂�{�^������
			panel.ctrlNewPassword.getField().setText("");
			panel.ctrlNewPasswordConfirmation.getField().setText("");
		}
	}

	/**
	 * �p�X���[�h�X�V
	 */
	private void savePassword() {
		try {
			addSendValues("flag", "passwordupdate");
			addSendValues("kaiCode", this.getLoginUserCompanyCode());
			addSendValues("usrCode", this.getLoginUserID());
			addSendValues("newPassword", panel.ctrlNewPassword.getValue());
			addSendValues("prgID", PROGRAM_ID);

			if (!request(TARGET_SERVLET)) {

				// �G���[���b�Z�[�W�����݂����
				if (!Util.isNullOrEmpty(getErrorMessage())) {
					errorHandler(panel, getErrorMessage());
				} else {
					errorHandler(panel, "S00002");
				}
				return;
			}
			showMessage(panel, "I00008");

		} catch (IOException ex) {
			super.errorHandler(panel, ex, "E00009");
		}
	}

	private void getUsername() {
		try {
			panel.ctrlUserCode.getField().setText(this.getLoginUserID());

			panel.ctrlNewPassword.getField().requestFocus();
			addSendValues("flag", "find");
			addSendValues("kaiCode", this.getLoginUserCompanyCode());
			addSendValues("beginUsrCode", this.getLoginUserID());
			addSendValues("endUsrCode", this.getLoginUserID());

			if (!request(TARGET_SERVLET)) {
				errorHandler(panel, "S00002");
			}

			List result = super.getResultList();
			if (result != null && result.size() > 0) {
				List inner = (List) result.get(0);
				String name_S = (String) inner.get(5);

				panel.txtUserName.setEditable(true);
				panel.txtUserName.setText(name_S);
				panel.txtUserName.setEditable(false);
			}
		} catch (IOException ex) {
			super.errorHandler(panel, ex, "E00009");
		}
	}
}
