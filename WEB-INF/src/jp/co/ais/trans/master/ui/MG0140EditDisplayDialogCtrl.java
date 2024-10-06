package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.util.*;

/**
 * ��s�}�X�^
 */
public class MG0140EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** ��s�}�X�^�_�C�A���O */
	protected MG0140EditDisplayDialog dialog;

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0140BankMasterServlet";

	/** �����敪 */
	private boolean isUpdate = false;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	MG0140EditDisplayDialogCtrl(Frame parent, String titleid) {
		// ��s�}�X�^�_�C�A���O�̏�����
		dialog = new MG0140EditDisplayDialog(parent, true, this, titleid);
		// ��ʂ̐ݒ�
		dialog.setSize(500, 400);
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.isSettle = false;
				dialog.setVisible(false);
			}
		});
		dialog.ctrlBankCode.requestTextFocus();

		dialog.dtBeginDate.setValue(minInputDate);
		dialog.dtEndDate.setValue(maxInputDate);

	}

	/**
	 * �\��
	 */
	void show() {
		// ��ʂ�\������
		dialog.setVisible(true);
	}

	/**
	 * �m��{�^���������ꂽ���ǂ���
	 * 
	 * @return �m��̏ꍇtrue
	 */
	boolean isSettle() {
		return dialog.isSettle;
	}

	/**
	 * ����
	 */
	void disposeDialog() {

		if (dialog.isSettle) {
			// �m��{�^������ �`�F�b�NOK�Ȃ����
			if (checkDialog()) {
				dialog.setVisible(false);
			}
		} else {
			// �߂�{�^������
			// ��ʂ�߂�
			dialog.setVisible(false);
		}

	}

	boolean checkDialog() {

		try {
			// ��s�R�[�h�`�F�b�N
			if (Util.isNullOrEmpty(dialog.ctrlBankCode.getValue())) {
				showMessage(dialog, "I00002", "C00779");
				dialog.ctrlBankCode.requestFocus();
				return false;
			}

			// ��s�x�X�R�[�h�`�F�b�N

			if (Util.isNullOrEmpty(dialog.ctrlBankBranchCode.getValue())) {
				showMessage(dialog, "I00002", "C00780");
				dialog.ctrlBankBranchCode.requestFocus();
				return false;
			}

			if (!Util.isNullOrEmpty(dialog.ctrlBankBranchCode.getValue()) && !isUpdate
				&& checkCode(dialog.ctrlBankCode.getValue(), dialog.ctrlBankBranchCode.getValue())) {
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlBankBranchCode.requestFocus();
				return false;
			}

			// ��s���`�F�b�N

			if (Util.isNullOrEmpty(dialog.ctrlBankName.getValue())) {
				showMessage(dialog, "I00002", "C00781");
				dialog.ctrlBankName.requestFocus();
				return false;
			}

			// ��s�J�i���̃`�F�b�N

			if (Util.isNullOrEmpty(dialog.ctrlBankKanaName.getValue())) {
				showMessage(dialog, "I00002", "C00782");
				dialog.ctrlBankKanaName.requestFocus();
				return false;
			}

			// ��s�������̃`�F�b�N

			if (Util.isNullOrEmpty(dialog.ctrlBankNameForSearch.getValue())) {
				showMessage(dialog, "I00002", "C00829");
				dialog.ctrlBankNameForSearch.requestFocus();
				return false;
			}

			// ��s�x�X���`�F�b�N

			if (Util.isNullOrEmpty(dialog.ctrlBankBranchName.getValue())) {
				showMessage(dialog, "I00002", "C00783");
				dialog.ctrlBankBranchName.requestFocus();
				return false;
			}

			// ��s�x�X�J�i���̃`�F�b�N

			if (Util.isNullOrEmpty(dialog.ctrlBankBranchKanaName.getValue())) {
				showMessage(dialog, "I00002", "C00784");
				dialog.ctrlBankBranchKanaName.requestFocus();
				return false;
			}

			// ��s�x�X�������̃`�F�b�N

			if (Util.isNullOrEmpty(dialog.ctrlBankBranchNameForSearch.getValue())) {
				showMessage(dialog, "I00002", "C00785");
				dialog.ctrlBankBranchNameForSearch.requestFocus();
				return false;
			}

			// �J�n�N����

			if (dialog.dtBeginDate.getValue() == null) {
				showMessage(dialog, "W00034", "C00055");
				dialog.dtBeginDate.getCalendar().requestFocus();
				return false;
			}

			// �I���N����

			if (dialog.dtEndDate.getValue() == null) {
				showMessage(dialog, "W00034", "C00261");
				dialog.dtEndDate.getCalendar().requestFocus();
				return false;
			}

			// �J�n�N���������I���N�����ɂ��Ă�������

			if (Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue()) == false) {
				showMessage(dialog, "W00035", "");
				dialog.dtBeginDate.getCalendar().requestFocus();
				return false;
			}

			return true;
		} catch (IOException e) {
			errorHandler(dialog, e, "E00009");
			return false;
		}
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map �Z�b�g����l
	 */
	void setValues(Map map) {
		// ��s�R�[�h�̐ݒ�
		dialog.ctrlBankCode.setValue((String) map.get("bnkCode"));
		// ��s�x�X�R�[�h�̐ݒ�
		dialog.ctrlBankBranchCode.setValue((String) map.get("bnkStnCode"));
		// ��s���̐ݒ�
		dialog.ctrlBankName.setValue((String) map.get("bnkName_S"));
		// ��s�J�i���̂̐ݒ�
		dialog.ctrlBankKanaName.setValue((String) map.get("bnkKana"));
		// ��s�������̂̐ݒ�
		dialog.ctrlBankNameForSearch.setValue((String) map.get("bnkName_K"));
		// ��s�x�X���̐ݒ�
		dialog.ctrlBankBranchName.setValue((String) map.get("bnkStnName_S"));
		// ��s�x�X�J�i���̂̐ݒ�
		dialog.ctrlBankBranchKanaName.setValue((String) map.get("bnkStnKana"));
		// ��s�x�X�������̂̐ݒ�
		dialog.ctrlBankBranchNameForSearch.setValue((String) map.get("bnkStnName_K"));
		// �J�n�N�����̐ݒ�
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// �I���N�����̐ݒ�
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// �ҏW���[�h�̂Ƃ��͋�s�R�[�h�A��s�x�X�R�[�h�Ƃ��ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));

		if (isUpdate) {
			dialog.ctrlBankName.requestFocus();
		}
		// ��s�R�[�h�̐ݒ�
		dialog.ctrlBankCode.setEditable(!isUpdate);
		// ��s�x�X�R�[�h�̐ݒ�
		dialog.ctrlBankBranchCode.setEditable(!isUpdate);

	}

	/**
	 * ��ʏ�\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 */
	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();
		// ��s�R�[�h�̐ݒ�
		map.put("bnkCode", dialog.ctrlBankCode.getValue());
		// ��s�x�X�R�[�h�̐ݒ�
		map.put("bnkStnCode", dialog.ctrlBankBranchCode.getValue());
		// ��s���̐ݒ�
		map.put("bnkName_S", dialog.ctrlBankName.getValue());
		// ��s�J�i���̂̐ݒ�
		map.put("bnkKana", dialog.ctrlBankKanaName.getValue());
		// ��s�������̂̐ݒ�
		map.put("bnkName_K", dialog.ctrlBankNameForSearch.getValue());
		// ��s�x�X���̐ݒ�
		map.put("bnkStnName_S", dialog.ctrlBankBranchName.getValue());
		// ��s�x�X�J�i���̂̐ݒ�
		map.put("bnkStnKana", dialog.ctrlBankBranchKanaName.getValue());
		// ��s�x�X�������̂̐ݒ�
		map.put("bnkStnName_K", dialog.ctrlBankBranchNameForSearch.getValue());
		// �J�n�N�����̐ݒ�
		map.put("strDate", dialog.dtBeginDate.getValue());
		// �I���N�����̐ݒ�
		map.put("endDate", dialog.dtEndDate.getValue());
		// ���ʂ�Ԃ�
		return map;
	}

	boolean checkCode(String bnkCode, String bnkStnCode) throws IOException {
		// ��s�R�[�h������
		if (Util.isNullOrEmpty(bnkCode)) {
			return false;
		}
		if (Util.isNullOrEmpty(bnkStnCode)) {
			return false;
		}
		// ������ʂ̐ݒ�
		addSendValues("flag", "checkcode");
		// ��s�R�[�h�̐ݒ�
		addSendValues("bnkCode", bnkCode);
		// ��s�x�X�R�[�h�̐ݒ�
		addSendValues("bnkStnCode", bnkStnCode);

		if (!request(TARGET_SERVLET)) {
			errorHandler(dialog);
			return true;
		}
		// ���ʂ��擾
		List result = super.getResultList();
		// ���ʂ�Ԃ�
		return (result.size() > 0);
	}
}
