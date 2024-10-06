package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.passwordupdate.*;

/**
 * �p�X���[�h�ύX�̃R���g���[��
 * 
 * @author AIS
 */
public class MG0025PasswordUpdatePanelCtrl extends TController {

	/** �ύX��� */
	protected MG0025PasswordUpdatePanel mainView = null;

	@Override
	public void start() {

		try {

			// �w����ʐ���
			createMainView();

			// �w����ʂ�����������
			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * �ύX��ʂ�����������
	 */
	protected void initMainView() {
		mainView.ctrlUserCode.getField().setText(getUserCode());
		mainView.ctrlUserCode.setEditable(false);
		mainView.ctrlUserName.setText(getUser().getName());
		mainView.ctrlUserName.setEditable(false);
		mainView.ctrlNewPassword.getField().requestFocus();
	}

	/**
	 * �ύX��ʂ̃t�@�N�g���B�V�K�ɉ�ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0025PasswordUpdatePanel();
		addMainViewEvent();
	}

	/**
	 * �ύX��ʂ̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		// [�m��]�{�^������
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * [�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			// ���̓`�F�b�N���s���B
			if (!isInputCorrect()) {
				return;
			}
			// �o�^���܂����H
			if (!showConfirmMessage(mainView, FocusButton.YES, "Q00005")) {
				return;
			}

			// ���͂��ꂽ�����擾
			PasswordUpdate passwordUpdate = getInputedPassword();

			// �X�V
			request(getModelClass(), "modify", passwordUpdate);

			// �����������b�Z�[�W
			showMessage(mainView, "I00008");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return UserManager
	 */
	protected Class getModelClass() {
		return PasswordUpdateManager.class;
	}

	/**
	 * �ύX��ʂœ��͂��ꂽ���[�U�[��Ԃ�
	 * 
	 * @return �ύX��ʂœ��͂��ꂽ���[�U�[
	 */
	protected PasswordUpdate getInputedPassword() {

		PasswordUpdate PasswordUpdate = new PasswordUpdate();
		PasswordUpdate.setNewPassword(mainView.ctrlNewPassword.getValue());
		PasswordUpdate.setConfNewPassword(mainView.ctrlConfNewPassword.getValue());

		return PasswordUpdate;

	}

	/**
	 * ���͂����l���Ó������`�F�b�N����
	 * 
	 * @return ���͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 */
	protected boolean isInputCorrect() {
		// �V�����p�X���[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(mainView.ctrlNewPassword.getValue())) {
			showMessage(mainView, "I00037", "C00742");
			mainView.ctrlNewPassword.requestFocus();
			return false;
		}
		// �V�����p�X���[�h(�m�F)�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(mainView.ctrlConfNewPassword.getValue())) {
			showMessage(mainView, "I00037", "C00305");
			mainView.ctrlConfNewPassword.requestFocus();
			return false;
		}

		// �V�����p�X���[�h�Ɗm�F�p���قȂ�ꍇ�G���[
		if (!mainView.ctrlNewPassword.getValue().equals(mainView.ctrlConfNewPassword.getValue())) {
			showMessage(mainView, "W00074", "");
			mainView.ctrlConfNewPassword.getField().requestFocus();
			return false;
		}

		return true;

	}

}
