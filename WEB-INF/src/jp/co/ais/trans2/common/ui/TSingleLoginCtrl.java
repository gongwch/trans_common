package jp.co.ais.trans2.common.ui;

import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.define.*;

/**
 * ���O�C���̃R���g���[���B<BR>
 * �A�v���P�[�V�����̋N���A���O�C����ʂ̐���A���O�C���̐��۔�����s���B
 * 
 * @author AIS
 */
public class TSingleLoginCtrl extends TLoginCtrl {

	/**
	 * �v���O�����G���g���|�C���g<BR>
	 * �A�v���P�[�V�������N������B
	 */
	@Override
	public void start() {

		// UI�̐ݒ�
		try {
			// L&F ������
			TUIManager.setLookAndFeel(LookAndFeelType.WINDOWS.value);

			// ���O�C����ʐ���
			loginView = createLoginView();

			// ���O�C����ʏ�����
			initLoginView();

			TUIManager.setUI(loginView.btnLogin, LookAndFeelType.MINT, LookAndFeelColor.White);
			TUIManager.setUI(loginView.btnClear, LookAndFeelType.MINT, LookAndFeelColor.White);
			TUIManager.setUI(loginView.btnClose, LookAndFeelType.MINT, LookAndFeelColor.White);

			// ���O�C����ʔ�\��
			loginView.setVisible(false);

			// �f�t�H���g�l�ݒ�
			setDefaultLoginInfo();

			// ���O�C����񏉊���
			initLoginInformation();

			// ���O�C���{�^���������l
			btnLogin_Click();

		} catch (Exception e) {
			e.printStackTrace();
			errorHandler(loginView, e);
		} finally {

			// ���O�C�����s
			if (loginView != null && loginView.isVisible()) {

				// ���͍��ڐ���
				controllItems(true);
			}
		}

	}

	/**
	 * ���O�C����񏉊���
	 */
	protected void initLoginInformation() {

		String token = Util.avoidNull(ClientConfig.getProperty("trans.single.login.token"));
		String[] arr = UTF8EncryptUtil.getArray(token);

		if (arr != null && arr.length > 2) {
			loginView.txCompanyCode.setValue(arr[0]);
			loginView.txUserCode.setValue(arr[1]);
			loginView.txPassword.setValue(arr[2]);
		}

		// ���͍��ڐ���
		controllItems(false);
	}

	/**
	 * ���͍��ڐ���
	 * 
	 * @param enable true:���͉\
	 */
	protected void controllItems(boolean enable) {
		loginView.txCompanyCode.setEditable(enable);
		loginView.txUserCode.setEditable(enable);
		loginView.txPassword.setEditable(enable);
		loginView.btnLogin.setEnabled(enable);
		loginView.btnClear.setEnabled(enable);
		loginView.btnClose.setEnabled(enable);
	}

}
