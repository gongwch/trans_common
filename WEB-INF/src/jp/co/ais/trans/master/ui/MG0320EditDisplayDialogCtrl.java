package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.util.*;

/**
 * �V�X�e���敪�}�X�^�_�C�A���O �R���g���[��
 */
public class MG0320EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** �V�X�e���敪�}�X�^�_�C�A���O */
	protected MG0320EditDisplayDialog dialog;

	/** �����敪 */
	private boolean isUpdate;

	/**
	 * �ڑ���Servlet����
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "MG0320SystemDivisionMasterServlet";
	}

	protected Map dataKbnMap;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	MG0320EditDisplayDialogCtrl(Frame parent, String titleid) {
		// �V�X�e���敪�}�X�^�_�C�A���O�̏�����
		dialog = createDialog(parent, titleid);
		// �^�C�g����ݒ肷��
		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent ev) {
				// ������ʂ̐ݒ�
				dialog.btnReturn.doClick();
			}
		});

		// fix bug: �m��{�^���������� UI �`�F�b�N�����s�̏ꍇ�́A
		// �_�C�A���O������Adialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});
		dialog.ctrlSystemDivision.getField().requestFocus();

	}

	/**
	 * @param parent �e�R���e�i�[
	 * @param titleid �^�C�g��
	 * @return �_�C�A���O
	 */
	protected MG0320EditDisplayDialog createDialog(Frame parent, String titleid) {
		return new MG0320EditDisplayDialog(parent, true, this, titleid);
	}

	/**
	 * �\��
	 * 
	 * @param isEnabledsysKbn �V�X�e���敪�R�[�h�G���A�ҏW��(true)�A�t��(false)
	 */
	void show(boolean isEnabledsysKbn) {
		// ��ʂ�\������
		dialog.setVisible(true);
		// �V�X�e���敪�R�[�h�̐ݒ�
		dialog.ctrlSystemDivision.setEditable(isEnabledsysKbn);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map
	 */
	void setValues(Map map) {
		// �V�X�e���敪�R�[�h�̐ݒ�
		dialog.ctrlSystemDivision.setValue((String) map.get("sysKbn"));
		// �V�X�e���敪���̂̐ݒ�
		dialog.ctrlSystemDivisionName.setValue((String) map.get("sysName"));
		// �V�X�e���敪���̂̐ݒ�
		dialog.ctrlSystemDivisionAbbreviatedName.setValue((String) map.get("sysName_S"));
		// �V�X�e���敪�������̂̐ݒ�
		dialog.ctrlSystemDivisionNameForSearch.setValue((String) map.get("sysName_K"));
		// �O���V�X�e���敪�̐ݒ�
		this.setComboBoxSelectedItem(dialog.ctrlOutsideSystemDivision.getComboBox(), (String) map.get("osyKbn"));
		// �ҏW���[�h�̂Ƃ��̓V�X�e���敪�R�[�h���ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));
		// �V�X�e���敪�R�[�h�̐ݒ�
		dialog.ctrlSystemDivision.setEditable(!isUpdate);
		if (isUpdate) {
			dialog.ctrlSystemDivisionName.getField().requestFocus();
		}
	}

	boolean checkDialog() {
		// �V�X�e���敪�R�[�h�����̓`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlSystemDivision.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", "C00980");
			dialog.ctrlSystemDivision.requestFocus();
			// �G���[��Ԃ�
			return false;
		}

		// �V�X�e���敪�R�[�h�����݂��Ă��܂��`�F�b�N
		if (!isUpdate && checkCode(dialog.ctrlSystemDivision.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "W00005", "C00174");
			dialog.ctrlSystemDivision.requestFocus();
			// �G���[��Ԃ�
			return false;
		}

		// �V�X�e���敪���̖����̓`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlSystemDivisionName.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", "C00832");
			dialog.ctrlSystemDivisionName.requestFocus();
			// �G���[��Ԃ�
			return false;
		}

		// �V�X�e���敪���̖����̓`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlSystemDivisionAbbreviatedName.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", "C00833");
			dialog.ctrlSystemDivisionAbbreviatedName.requestFocus();
			// �G���[��Ԃ�
			return false;
		}

		// �V�X�e���敪�������̖����̓`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlSystemDivisionNameForSearch.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", "C00834");
			dialog.ctrlSystemDivisionNameForSearch.requestFocus();
			// �G���[��Ԃ�
			return false;
		}

		// �O���V�X�e���敪�����̓`�F�b�N
		if (Util.isNullOrEmpty(this.getComboBoxSelectedValue(dialog.ctrlOutsideSystemDivision.getComboBox()))) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", "C01018");
			dialog.ctrlOutsideSystemDivision.getComboBox().requestFocus();
			// �G���[��Ԃ�
			return false;
		}

		// �������
		return true;
	}

	/**
	 * ����
	 */
	void disposeDialog() {
		if (dialog.isSettle) {
			// �m��{�^������ �`�F�b�NOK�Ȃ����
			if (!this.checkDialog()) return;
			dialog.setVisible(!this.checkDialog());
		} else {
			// ��ʂ�߂�
			dialog.setVisible(false);
		}
	}

	/**
	 * �m��{�^���������ꂽ���ǂ���
	 * 
	 * @return �m��̏ꍇtrue
	 */
	boolean isSettle() {
		// ������ʂ̐ݒ�
		return dialog.isSettle;
	}

	/**
	 * @param map
	 */
	public void setDataKbnMap(Map map) {
		this.dataKbnMap = map;

		this.fillItemsToComboBox(dialog.ctrlOutsideSystemDivision.getComboBox(), dataKbnMap);
	}

	/**
	 * ��ʏ�\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 */
	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();

		map.put("sysKbn", dialog.ctrlSystemDivision.getValue());
		map.put("sysName", dialog.ctrlSystemDivisionName.getValue());
		map.put("sysName_K", dialog.ctrlSystemDivisionNameForSearch.getValue());
		map.put("sysName_S", dialog.ctrlSystemDivisionAbbreviatedName.getValue());
		map.put("osyKbn", this.getComboBoxSelectedValue(dialog.ctrlOutsideSystemDivision.getComboBox()));
		map.put("kaiCode", getLoginUserCompanyCode());
		// ���ʂ�Ԃ�
		return map;
	}

	boolean checkCode(String code) {
		try {
			if (Util.isNullOrEmpty(code)) {
				return false;
			}
			// ������ʂ̐ݒ�
			addSendValues("flag", "checkcode");
			addSendValues("kaiCode", getLoginUserCompanyCode());
			addSendValues("sysKbn", code);

			if (!request(getServletName())) {
				// �T�[�o���̃G���[�ꍇ
				errorHandler(dialog);
				return false;
			}

			// ���ʂ��擾
			List result = super.getResultList();
			// ���ʂ�Ԃ�
			return (result.size() > 0);
		} catch (IOException e) {
			errorHandler(dialog);
			return false;
		}
	}

}
