package jp.co.ais.trans.master.ui;

import java.io.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���[�U�F�؊Ǘ��}�X�^�R���g���[��
 * 
 * @author roh
 */
public class MG0026UserAuthUpdatePanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	private static final String PROGRAM_ID = "MG0026";

	/**
	 * �ڑ���Servlet����
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "MG0026UserAuthUpdateServlet";
	}

	/** �p�l�� */
	protected MG0026UserAuthUpdatePanel panel;

	/** ���b�N�A�E�g��J������(����) */
	protected String lockOutReleaseStr = getWord("C02772") + getWord("C02774");

	/** �p�X���[�h����(����) */
	protected String pwdTermStr = getWord("C00597") + getWord("C00101");

	/** �F�؊Ǘ��G���e�B�e�B */
	protected USR_AUTH_MST usrAuthDto;

	/**
	 * �p�l���擾
	 * 
	 * @return �p�l��
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/** �R���X�g���N�^ */
	public MG0026UserAuthUpdatePanelCtrl() {
		panel = new MG0026UserAuthUpdatePanel(this);

		try {
			// ��ʍ\�z
			initPanel();

			// �f�[�^���f
			reflectNowData();

		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * �p�l���̃��b�Z�[�W�\��
	 */
	protected void initPanel() {
		// ���b�N�A�E�g��J������
		panel.lockOutReleaseField.setLabelText(lockOutReleaseStr);

		MessageUtil.getShortWord(getLoginLanguage(), "C02773");

		boolean isShortWord = Util.isShortLanguage(getLoginLanguage());

		// ���b�N�A�E�g�ő��
		panel.lockOutArrField.setLabelText(getWord("C02772")
			+ (isShortWord ? MessageUtil.getShortWord(getLoginLanguage(), "C02773") : getWord("C02773")));
		// ���G���x��
		panel.complicateLvlField.setLabelText(getWord("C02776")
			+ (isShortWord ? MessageUtil.getShortWord(getLoginLanguage(), "C01739") : getWord("C01739")));
		// �p�X���[�h����
		panel.pwdTermField.setLabelText(pwdTermStr);

		// ���G���x��
		String kindOrverWord = getWord("C02780");
		for (int i = 1; i <= 4; i++) {
			panel.complicateLvlField.getComboBox().addTextValueItem(i, i + kindOrverWord);
		}

		// 1�`9�܂ł̒l��ݒ�
		for (int i = 0; i <= 9; i++) {
			panel.lockOutArrField.getComboBox().addTextValueItem(i, String.valueOf(i));
			panel.historyMaxField.getComboBox().addTextValueItem(i, String.valueOf(i));
			panel.pwdMinField.getComboBox().addTextValueItem(i, String.valueOf(i));
		}

		panel.pwdMinField.getComboBox().addTextValueItem(10, "10");
	}

	/**
	 * ��ʏ�񏉊���
	 * 
	 * @throws IOException
	 */
	protected void reflectNowData() throws IOException {

		addSendValues("flag", "findByKaiCode");

		if (!request(getServletName())) {
			errorHandler(panel);
			return;
		}

		usrAuthDto = (USR_AUTH_MST) super.getResultDto(USR_AUTH_MST.class);

		if (usrAuthDto != null) {
			panel.lockOutArrField.getComboBox().setSelectedIndex(usrAuthDto.getLOCK_OUT_ARR_CNT());
			panel.lockOutReleaseField.setNumberValue(usrAuthDto.getLOCK_OUT_RELEASE_TIME());
			panel.pwdMinField.getComboBox().setSelectedIndex(usrAuthDto.getPWD_MIN_LENGTH());
			panel.pwdTermField.setNumberValue(usrAuthDto.getPWD_TERM());
			panel.historyMaxField.getComboBox().setSelectedIndex(usrAuthDto.getHISTORY_MAX_CNT());
			panel.complicateLvlField.getComboBox().setSelectedIndex(usrAuthDto.getCOMPLICATE_LVL() - 1);
			panel.pwdTimeLimitMsgField.setNumberValue(usrAuthDto.getPWD_EXP_BEFORE_DAYS());
		}
	}

	/**
	 * �p�l���t�B�[���h�̒l�`�F�b�N
	 * 
	 * @return false:�l�s��
	 */
	protected boolean checkPanel() {

		// ���b�N�A�E�g�񐔐ݒ�`�F�b�N
		if (Util.isNullOrEmpty(panel.lockOutReleaseField.getValue())) {
			showMessage(panel, "I00002", lockOutReleaseStr);
			panel.lockOutReleaseField.requestFocus();
			return false;
		}

		// �p�X���[�h�L�����Ԑݒ�`�F�b�N
		if (Util.isNullOrEmpty(panel.pwdTermField.getValue())) {
			showMessage(panel, "I00002", pwdTermStr);
			panel.pwdTermField.requestFocus();
			return false;
		}

		// �����؂ꃁ�b�Z�[�W�ʒm���ԃ`�F�b�N
		if (Util.isNullOrEmpty(panel.pwdTimeLimitMsgField.getValue())) {
			showMessage(panel, "I00002", "C04285");
			panel.pwdTimeLimitMsgField.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * ���X�V(�m��)
	 */
	protected void update() {

		try {
			if (!checkPanel()) {
				return;
			}

			addSendValues("flag", "updateAuthDto");

			USR_AUTH_MST usrAuthUpdateDto = new USR_AUTH_MST();

			// Dto���p�����[�^�Ƃ��Đݒ�
			usrAuthUpdateDto
				.setLOCK_OUT_ARR_CNT((Integer) (panel.lockOutArrField.getComboBox().getSelectedItemValue()));
			usrAuthUpdateDto.setLOCK_OUT_RELEASE_TIME(panel.lockOutReleaseField.getIntValue());
			usrAuthUpdateDto.setPWD_MIN_LENGTH((Integer) panel.pwdMinField.getComboBox().getSelectedItemValue());
			usrAuthUpdateDto.setPWD_TERM(panel.pwdTermField.getIntValue());
			usrAuthUpdateDto.setCOMPLICATE_LVL((Integer) panel.complicateLvlField.getComboBox().getSelectedItemValue());
			usrAuthUpdateDto.setHISTORY_MAX_CNT((Integer) (panel.historyMaxField.getComboBox().getSelectedItemValue()));
			usrAuthUpdateDto.setPRG_ID(PROGRAM_ID);
			usrAuthUpdateDto.setPWD_EXP_BEFORE_DAYS(panel.pwdTimeLimitMsgField.getIntValue());

			addSendDto(usrAuthUpdateDto);

			if (!request(getServletName())) {
				errorHandler(panel);
				return;
			}

			showMessage(panel, "I00008");

		} catch (IOException ex) {
			super.errorHandler(panel, ex, "E00009");
		}
	}
}
