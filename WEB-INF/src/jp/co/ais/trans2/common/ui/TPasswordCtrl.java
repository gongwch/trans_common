package jp.co.ais.trans2.common.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans.common.gui.TFrame;
import jp.co.ais.trans.common.gui.TPanelBusiness;
import jp.co.ais.trans.common.util.Util;
import jp.co.ais.trans2.common.client.TController;
import jp.co.ais.trans2.common.exception.TInformationException;
import jp.co.ais.trans2.model.company.Company;
import jp.co.ais.trans2.model.security.PasswordManager;
import jp.co.ais.trans2.model.user.User;

/**
 * �p�X���[�h�ύX�̃R���g���[���B<BR>
 * 
 * @author AIS
 */
public class TPasswordCtrl extends TController {

	/** �Ăяo�����e�t���[�� */
	protected TFrame parentFrame = null;

	/**
	 * �p�X���[�h�ύX���
	 */
	protected TPassword view = null;

	/**
	 * �p�X���[�h�ύX���Ȃ��ꂽ��
	 */
	protected boolean isPasswordChanged = false;

	/**
	 * �e�t���[���Z�b�g
	 * 
	 * @param parentFrame �e�t���[��
	 */
	public TPasswordCtrl(TFrame parentFrame) {
		this.parentFrame = parentFrame;
	}

	public void start() {

		try {

			// �p�X���[�h�ύX��ʐ���
			view = createView();

			// ��ʏ�����
			initView();

			// �\��
			view.setLocationRelativeTo(null);
			view.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * �p�X���[�h�ύX��ʂ�����������
	 */
	protected void initView() {

		// ���[�U�[
		view.txUserCode.setValue(getUser().getCode());
		view.txUserNames.setValue(getUser().getNames());

		// �C�x���g��`
		initViewEvent();

	}

	/**
	 * ���O�C����ʂ̃C�x���g��`
	 */
	protected void initViewEvent() {

		// �m��{�^������
		view.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused")
			ActionEvent e) {
				btnSettle_Click();
			}
		});

		// �߂�{�^������
		view.btnBack.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused")
			ActionEvent e) {
				btnBack_Click();
			}
		});

	}

	/**
	 * �p�X���[�h�ύX��ʂ̃t�@�N�g��
	 * 
	 * @return �p�X���[�h�ύX���
	 */
	protected TPassword createView() {
		return new TPassword(parentFrame, true);
	}

	/**
	 * [�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			// ���̓`�F�b�N
			if (!isInputCorrect()) {
				return;
			}

			// �p�X���[�h�ύX����
			try {
				updatePassword(getCompany(), getUser(), view.txNewPassword.getValue());
			} catch (TInformationException e) {
				showMessage(view, e.getMessageID(), e.getMessageArgs());
				view.txNewPassword.requestTextFocus();
				return;
			}

			setPasswordChanged(true);
			view.setVisible(false);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [�߂�]�{�^������
	 */
	protected void btnBack_Click() {

		try {
			view.setVisible(false);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * ���͂�����������Ԃ�
	 * 
	 * @return true(����) / false(�G���[)
	 */
	protected boolean isInputCorrect() {

		// �V�p�X���[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(view.txNewPassword.getValue())) {
			showMessage(view, "I00139");// �p�X���[�h����͂��Ă��������B
			view.txNewPassword.requestFocus();
			return false;
		}

		// �V�p�X���[�h(�m�F)�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(view.txNewPasswordRe.getValue())) {
			showMessage(view, "I00140");// �p�X���[�h(�m�F)����͂��Ă��������B
			view.txNewPasswordRe.requestFocus();
			return false;
		}

		// �V�p�X���[�h�Ƃ��̊m�F����v���Ȃ��ꍇ�G���[
		if (!view.txNewPassword.getValue().equals(view.txNewPasswordRe.getValue())) {
			showMessage(view, "I00141");// �p�X���[�h����v���܂���B
			view.txNewPassword.requestFocus();
			return false;
		}

		return true;

	}

	public boolean isPasswordChanged() {
		return isPasswordChanged;
	}

	public void setPasswordChanged(boolean isPasswordChanged) {
		this.isPasswordChanged = isPasswordChanged;
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * �p�X���[�h���X�V����
	 * 
	 * @param company ���
	 * @param user ���[�U�[
	 * @param password �p�X���[�h
	 */
	protected void updatePassword(Company company, User user, String password) throws TInformationException, TException {
		request(PasswordManager.class, "updatePassword", company, user, password);
	}

}
