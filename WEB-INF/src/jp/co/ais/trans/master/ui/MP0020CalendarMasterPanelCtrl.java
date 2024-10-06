package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * �J�����_�[�}�X�^
 */
public class MP0020CalendarMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	protected static final String PROGRAM_ID = "MP0020";

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MP0020CalendarMasterServlet";

	/** �p�l�� */
	protected MP0020CalendarMasterPanel panel;

	/**
	 * �R���X�g���N�^.
	 */
	public MP0020CalendarMasterPanelCtrl() {

		try {
			// �ꗗ��ʂ̎��ቻ
			panel = new MP0020CalendarMasterPanel(this);
			// �ꗗ��ʂ̏�����
			panel.setInit();
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				//�t�H�[�J�X���Q�b�g����
				getFocus();
			}
		});
	}

	/**
	 * �t�H�[�J�X���Q�b�g����
	 */
	public void getFocus() {
		panel.txtObjectYears.requestFocus();		
	}
	
	/**
	 * @return String
	 */
	public String getLangCode() {
		return super.getLoginLanguage();
	}

	/**
	 * �p�l���擾
	 * 
	 * @return ����ް�}�X�^�p�l��
	 */
	public TPanelBusiness getPanel() {
		// �p�l����Ԃ�
		return this.panel;
	}

	/**
	 * �\���f�[�^�̎擾
	 * 
	 * @param dteFrom �J�n���t, dteTo �I�����t
	 * @param dteTo
	 */
	public void reflesh(Date dteFrom, Date dteTo) {
		try {
			// ��ЃR�[�h�̎擾
			String kaiCode = getLoginUserCompanyCode();
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "find");
			// ��ЃR�[�h�̐ݒ�
			addSendValues("kaiCode", kaiCode);
			// �J�n���t�̐ݒ�
			addSendValues("beginCalDate", DateUtil.toYMDHMSString(dteFrom));
			// �I�����t�̐ݒ�
			addSendValues("endCalDate", DateUtil.toYMDHMSString(dteTo));
			// ���M
			// �T�[�o���̃G���[�ꍇ
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
				return;
			}
			// ���ʂ̎擾
			List lisRt = getResultList();
			// ���ʂ̐ݒ�
			panel.setDte(lisRt);
			panel.btnSettle.setEnabled(true);
		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �폜����
	 * 
	 * @param calMonth ���t, intLastDay ����
	 * @param intLastDay
	 */
	void delete(String calMonth, int intLastDay) {
		try {
			// ��ЃR�[�h�̎擾
			String kaiCode = getLoginUserCompanyCode();
			// ������ʂ̐ݒ�
			super.addSendValues("flag", "delete");
			// ��ЃR�[�h�̐ݒ�
			super.addSendValues("kaiCode", kaiCode);
			// ���t�̐ݒ�
			super.addSendValues("calMonth", calMonth);
			// �����̐ݒ�
			super.addSendValues("intLastDay", String.valueOf(intLastDay));
			// �T�[�u���b�g�̐ڑ���
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
				return;
			}
		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * @return boolean
	 */
	public boolean disposeDialog() {
		boolean isSettle = false;
		// �m��{�^������ �`�F�b�NOK�Ȃ����
		if (this.showConfirmMessage(panel.getParentFrame(), "Q00005", "")) {
			isSettle = true;
			// �������b�Z�[�W��\������B
			showMessage(panel.getParentFrame(), "I00008", (Object) "");
		}
		return isSettle;
	}

	/**
	 * �����̓��t�̏������`�F�b�N���A�����̎��A���̃X�g�����O��߂�
	 * 
	 * @param strDate ���t
	 * @return String
	 */

	public String chkDate(String strDate) {
		// ����
		return strDate;
	}

	/**
	 * �V�K����
	 * 
	 * @param calDate ���t
	 * @param calHarai �Ј��x���Ώۓ�
	 * @param calBnkKbn ��s�x�Ɠ�
	 * @param calSha �Վ��x���Ώۓ�
	 */
	public void insert(String calDate, String calHarai, String calBnkKbn, String calSha) {
		try {
			// ��ЃR�[�h�̎擾
			String kaiCode = getLoginUserCompanyCode();
			// ������ʂ̐ݒ�
			super.addSendValues("flag", "insert");
			// ��ЃR�[�h�̐ݒ�
			super.addSendValues("kaiCode", kaiCode);
			// ���t�̐ݒ�
			super.addSendValues("calDate", calDate);
			// �Ј��x���Ώۓ��̐ݒ�
			super.addSendValues("calHarai", calHarai);
			// ��s�x�Ɠ��̐ݒ�
			super.addSendValues("calBnkKbn", calBnkKbn);
			// �Վ��x���Ώۓ��̐ݒ�
			super.addSendValues("calSha", calSha);
			// �v���O�����h�c�̐ݒ�
			super.addSendValues("prgID", PROGRAM_ID);
			// �T�[�u���b�g�̐ڑ���
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
			}
		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

}
