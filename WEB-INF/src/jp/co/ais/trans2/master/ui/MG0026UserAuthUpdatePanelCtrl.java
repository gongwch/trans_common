package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.master.ui.MG0026UserAuthUpdatePanel;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.security.*;

/**
 * ���[�U�[�F�؊Ǘ��}�X�^�̃R���g���[��
 * 
 * @author AIS
 */
public class MG0026UserAuthUpdatePanelCtrl extends TController {

	/** �w����� */
	protected MG0026UserAuthUpdatePanel mainView = null;

	/** ���b�N�A�E�g�ő��(����) */
	protected String lockOutMaxReleaseStr = getWord("C02772") + getWord("C02773");

	/** ���b�N�A�E�g��J������(����) */
	protected String lockOutReleaseStr = getWord("C02772") + getWord("C02774");

	/** �p�X���[�h����(����) */
	protected String pwdTermStr = getWord("C00597") + getWord("C00101");

	/** �g���q */
	public static String[] supportFileExts = null;

	/**
	 * �p�l���擾
	 * 
	 * @return �p�l��
	 */
	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/** �R���X�g���N�^ */
	public MG0026UserAuthUpdatePanelCtrl() {
		mainView = new MG0026UserAuthUpdatePanel();
		addMainViewEvent();
		try {
			// ��ʍ\�z
			initPanel();

			// �����f�[�^���f
			reflectNowData();

		} catch (Exception e) {
			errorHandler(mainView.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * �p�l���̃��b�Z�[�W�\���ƃR���{�{�b�N�X�l���`
	 */
	protected void initPanel() {
		// ���b�N�A�E�g��J������
		mainView.rockOutLatTime.setLabelText(lockOutReleaseStr);

		MessageUtil.getShortWord(getLoginLanguage(), "C02773");

		boolean isShortWord = Util.isShortLanguage(getLoginLanguage());

		// ���b�N�A�E�g�ő��
		mainView.rockOutMaxCount.setLabelText(getWord("C02772")
			+ (isShortWord ? MessageUtil.getShortWord(getLoginLanguage(), "C02773") : getWord("C02773")));

		// ���G���x��
		mainView.difficultLevel.setLabelText(getWord("C02776")
			+ (isShortWord ? MessageUtil.getShortWord(getLoginLanguage(), "C01739") : getWord("C01739")));

		// �p�X���[�h����
		mainView.passTerm.setLabelText(pwdTermStr);

		// ���G���x���R���{�{�b�N�X�l
		String kindOrverWord = getWord("C02780");
		for (int i = 1; i <= 4; i++) {
			mainView.difficultLevel.getComboBox().addTextValueItem(i, i + kindOrverWord);
		}

		// 1�`9�܂ł̒l��ݒ�A�R���{�{�b�N�X�l
		for (int i = 0; i <= 9; i++) {
			mainView.rockOutMaxCount.getComboBox().addTextValueItem(i, String.valueOf(i));
			mainView.histryCount.getComboBox().addTextValueItem(i, String.valueOf(i));
			mainView.minPasslength.getComboBox().addTextValueItem(i, String.valueOf(i));
		}
		mainView.minPasslength.getComboBox().addTextValueItem(10, "10");

	}

	/**
	 * ��ʏ�񏉊���
	 * 
	 * @throws IOException
	 */
	protected void reflectNowData() throws IOException {

		try {
			USR_AUTH_MST bean = getUserauth();
			bean = (USR_AUTH_MST) request(getModelClass(), "get");

			if (!Util.isNullOrEmpty(bean.getKAI_CODE())) {
				mainView.rockOutMaxCount.getComboBox().setSelectedIndex(bean.getLOCK_OUT_ARR_CNT());
				mainView.rockOutLatTime.setValue(bean.getLOCK_OUT_RELEASE_TIME());
				mainView.minPasslength.getComboBox().setSelectedIndex(bean.getPWD_MIN_LENGTH());
				mainView.passTerm.setValue(bean.getPWD_TERM());
				mainView.histryCount.getComboBox().setSelectedIndex(bean.getHISTORY_MAX_CNT());
				mainView.difficultLevel.getComboBox().setSelectedIndex(bean.getCOMPLICATE_LVL() - 1);
				mainView.messagePshu.setValue(bean.getPWD_EXP_BEFORE_DAYS());
			}
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �w����ʃ{�^���������C�x���g
	 */
	protected void addMainViewEvent() {
		// [�m��]�{�^������
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * �w�����[�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			// ���̓`�F�b�N���s���B
			if (!isInputCorrect()) {
				return;
			}

			// �m�菈�� �o�^�܂��͍X�V
			USR_AUTH_MST bean = getUserauth();
			request(getModelClass(), "modify", bean);

			// ���������̃��b�Z�[�W
			showMessage(mainView, "I00008");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w����ʂœ��͂��ꂽ�l��Ԃ�
	 * 
	 * @return �w����ʂœ��͂��ꂽ�l
	 */
	protected USR_AUTH_MST getUserauth() {

		USR_AUTH_MST usrauthupd = new USR_AUTH_MST();

		usrauthupd.setKAI_CODE(getCompanyCode());
		usrauthupd.setLOCK_OUT_ARR_CNT((Integer) mainView.rockOutMaxCount.getComboBox().getSelectedItemValue());
		usrauthupd.setLOCK_OUT_RELEASE_TIME(mainView.rockOutLatTime.getValue());
		usrauthupd.setPWD_MIN_LENGTH((Integer) mainView.minPasslength.getComboBox().getSelectedItemValue());
		usrauthupd.setPWD_TERM(mainView.passTerm.getValue());
		usrauthupd.setCOMPLICATE_LVL((Integer) mainView.difficultLevel.getComboBox().getSelectedItemValue());
		usrauthupd.setHISTORY_MAX_CNT((Integer) mainView.histryCount.getComboBox().getSelectedItemValue());
		usrauthupd.setPWD_EXP_BEFORE_DAYS(mainView.messagePshu.getValue());

		return usrauthupd;
	}

	/**
	 * �C���^�t�F�[�X�N���X��Ԃ�
	 * 
	 * @return UserAuthMangager
	 */
	protected Class getModelClass() {
		return UserAuthUpdateManager.class;
	}

	/**
	 * �w����ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �w����ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// ���b�N�A�E�g�񐔌������Ԑݒ�`�F�b�N
		if (Util.isNullOrEmpty(mainView.rockOutLatTime.getValue())) {
			showMessage(mainView, "I00037", lockOutReleaseStr);
			mainView.rockOutLatTime.requestFocus();
			return false;
		}

		// �p�X���[�h�L�����Ԑݒ�`�F�b�N
		if (Util.isNullOrEmpty(mainView.passTerm.getValue())) {
			showMessage(mainView, "I00037", pwdTermStr);
			mainView.passTerm.requestFocus();
			return false;
		}

		// �����؂ꃁ�b�Z�[�W�ʒm���ԃ`�F�b�N
		if (Util.isNullOrEmpty(mainView.messagePshu.getValue())) {
			showMessage(mainView, "I00037", "C04285");
			mainView.messagePshu.requestFocus();
			return false;
		}

		return true;

	}

}
