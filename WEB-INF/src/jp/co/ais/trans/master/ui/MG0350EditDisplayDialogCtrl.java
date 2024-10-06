package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * �Ǘ��}�X�^�_�C�A���O �R���g���[��
 */
public class MG0350EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** �Ǘ��}�X�^�_�C�A���O */
	private MG0350EditDisplayDialog dialog;

	/** �����敪 */
	private boolean isUpdate;

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0350InterCompanyTransferMasterServlet";

	private REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	MG0350EditDisplayDialogCtrl(Frame parent, String titleid) {
		// �Ǘ��}�X�^�_�C�A���O�̏�����
		dialog = new MG0350EditDisplayDialog(parent, true, this, titleid);

		dialog.ctrlItemUnit.getTSubItemField().setEditable(false);
		dialog.ctrlItemUnit.getTSubItemField().setButtonEnabled(false);

		dialog.ctrlItemUnit.getTSubItemField().setEditable(false);
		dialog.ctrlItemUnit.getTSubItemField().setButtonEnabled(false);
		// �t�։�Ђ̐ݒ�
		ref1 = new REFDialogCtrl(dialog.ctrlTransferCompany, dialog.getParentFrame());
		ref1.setColumnLabels("C00596", "C00686", null);
		ref1.setTargetServlet("MG0010EnvironmentalSettingMasterServlet");
		ref1.setTitleID(getWord("C00053"));
		ref1.setShowsMsgOnError(false);
		ref1.addIgnoredButton(dialog.btnReturn);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "EnvironmentalSetting");
				return keys;
			}
		});

		dialog.ctrlTransferCompany.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlTransferCompany.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlTransferCompany.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlTransferCompany.getValue());
					dialog.ctrlTransferCompany.getField().clearOldText();
					dialog.ctrlTransferCompany.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		// �t�֌v�㕔��̐ݒ�
		ref2 = new REFDialogCtrl(dialog.ctrlTransferAppropriateDepartment, dialog.getParentFrame());
		ref2.setColumnLabels("C00698", "C00724", "C00725");
		ref2.setTargetServlet("MG0060DepartmentMasterServlet");
		ref2.setTitleID(getWord("C02338"));
		ref2.setShowsMsgOnError(false);
		ref2.addIgnoredButton(dialog.btnReturn);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "Department");
				return keys;
			}
		});

		dialog.ctrlTransferAppropriateDepartment.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlTransferAppropriateDepartment.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlTransferAppropriateDepartment.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlTransferAppropriateDepartment.getValue());
					dialog.ctrlTransferAppropriateDepartment.getField().clearOldText();
					dialog.ctrlTransferAppropriateDepartment.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		// fix bug: �m��{�^���������� UI �`�F�b�N�����s�̏ꍇ�́A
		// �_�C�A���O������Adialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});
		dialog.ctrlTransferCompany.getField().requestFocus();

	}

	/**
	 * (�Ȗ�)
	 */
	public void setItemData() {
		// ��ЃR�[�hsetSummaryDivision
		dialog.ctrlItemUnit.getTItemField().getInputParameter().setSummaryDivision("0");
	}

	/**
	 * (�⏕�Ȗ�)
	 */
	public void setSubItemData() {
		// ��ЃR�[�h
		dialog.ctrlItemUnit.getTSubItemField().getInputParameter().setItemCode(
			dialog.ctrlItemUnit.getTItemField().getValue());
	}

	/**
	 * (����Ȗ�)
	 */
	public void setBreakDownItemData() {
		// ��ЃR�[�h
		dialog.ctrlItemUnit.getTBreakDownItemField().getInputParameter().setItemCode(
			dialog.ctrlItemUnit.getTItemField().getValue());
		dialog.ctrlItemUnit.getTBreakDownItemField().getInputParameter().setSubItemCode(
			dialog.ctrlItemUnit.getTSubItemField().getValue());
	}

	/**
	 * �\��
	 * 
	 * @param isEnabledknrCode �t�։�ЃG���A�ҏW��(true)�A�t��(false)
	 */
	void show(boolean isEnabledknrCode) {
		// ��ʂ�\������
		dialog.setVisible(true);
		// ����R�[�h�̐ݒ�
		dialog.ctrlTransferCompany.setEnabled(isEnabledknrCode);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map
	 */
	void setValues(Map map) {

		String itemCode = Util.avoidNull(map.get("ktkKmkCode"));
		String subItemCode = Util.avoidNull(map.get("ktkHkmCode"));
		String breakDownItemCode = Util.avoidNull(map.get("ktkUkmCode"));

		// �Ȗڃp�����[�^
		dialog.ctrlItemUnit.getInputParameter().setItemCode(itemCode);
		dialog.ctrlItemUnit.getInputParameter().setSubItemCode(subItemCode);
		dialog.ctrlItemUnit.getInputParameter().setBreakDownItemCode(breakDownItemCode);

		// �Ȗ�
		dialog.ctrlItemUnit.setItemCode(itemCode);

		// �⏕�Ȗ�
		dialog.ctrlItemUnit.setSubItemCode(subItemCode);

		// ����Ȗ�
		dialog.ctrlItemUnit.setBreakDownItemCode(breakDownItemCode);

		dialog.ctrlTransferCompany.setValue((String) map.get("ktkKaiCode"));
		if (!("".equals(map.get("ktkKaiCode")))) {
			dialog.ctrlTransferCompany.setEditable(false);
			dialog.ctrlTransferCompany.setButtonEnabled(false);
		}
		dialog.ctrlTransferAppropriateDepartment.setValue((String) map.get("ktkDepCode"));

		// �ҏW���[�h�̂Ƃ��͕t�։�ЃR�[�h���ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));

		// ��ЃR�[�h�̐ݒ�
		dialog.ctrlTransferCompany.setEnabled(true);
		dialog.ctrlTransferCompany.setEditable(!isUpdate);

		ref1.refreshName();
		ref2.refreshName();

		if (isUpdate) {
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					dialog.ctrlTransferAppropriateDepartment.getField().requestFocus();
					dialog.ctrlTransferCompany.setEnabled(false);
					dialog.ctrlTransferCompany.setEditable(false);
				}
			});
		} else {
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					dialog.ctrlTransferCompany.getField().requestFocus();
				}
			});
		}
	}

	boolean checkDialog() {
		try {// �t�։�Ѓ`�F�b�N
			if (Util.isNullOrEmpty(dialog.ctrlTransferCompany.getValue())) {
				showMessage(dialog, "I00002", "C00846");
				dialog.ctrlTransferCompany.requestTextFocus();
				return false;
			}
			// �t�։�Ѓ`�F�b�N

			if (!isUpdate && checkCode(dialog.ctrlTransferCompany.getField().getText())) {
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlTransferCompany.requestTextFocus();
				return false;
			}

			// �t�֌v�㕔��`�F�b�N
			if (Util.isNullOrEmpty(dialog.ctrlTransferAppropriateDepartment.getValue())) {
				showMessage(dialog, "I00002", "C00847");
				dialog.ctrlTransferAppropriateDepartment.requestTextFocus();
				return false;
			}
			// �t�։Ȗڃ`�F�b�N
			if (Util.isNullOrEmpty(dialog.ctrlItemUnit.getTItemField().getValue())) {
				showMessage(dialog, "I00002", "C02052");
				dialog.ctrlItemUnit.getTItemField().requestTextFocus();
				return false;
			}

			// �t�֕⏕�Ȗڃ`�F�b�N
			if (dialog.ctrlItemUnit.getTSubItemField().getField().isEditable()
				&& Util.isNullOrEmpty(dialog.ctrlItemUnit.getTSubItemField().getValue())) {
				showMessage(dialog, "I00002", "C02053");
				dialog.ctrlItemUnit.getTSubItemField().requestTextFocus();
				return false;
			}

			// ����Ȗڃ`�F�b�N
			if (dialog.ctrlItemUnit.getTSubItemField().getField().isEditable()
				&& Util.isNullOrEmpty(dialog.ctrlItemUnit.getTSubItemField().getValue())) {
				showMessage(dialog, "I00002", "C02054");
				dialog.ctrlItemUnit.getTSubItemField().requestTextFocus();
				return false;
			}
			return true;
		} catch (IOException e) {
			errorHandler(dialog, e, "E00009");
			return false;
		}
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
			dialog.setVisible(false);// ����
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
	 * ��ʏ�\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 */
	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();

		map.put("ktkKaiCode", dialog.ctrlTransferCompany.getValue());
		map.put("ktkDepCode", dialog.ctrlTransferAppropriateDepartment.getValue());
		map.put("ktkKmkCode", dialog.ctrlItemUnit.getTItemField().getValue());
		map.put("ktkHkmCode", dialog.ctrlItemUnit.getTSubItemField().getValue());
		map.put("ktkUkmCode", dialog.ctrlItemUnit.getTBreakDownItemField().getValue());
		map.put("kaiCode", getLoginUserCompanyCode());
		// ���ʂ�Ԃ�
		return map;
	}

	boolean checkCode(String code) throws IOException {
		if (Util.isNullOrEmpty(code)) {
			return false;
		}
		// ������ʂ̐ݒ�
		addSendValues("flag", "checkcode");
		addSendValues("kaiCode", getLoginUserCompanyCode());
		addSendValues("ktkKaiCode", code);

		if (!request(TARGET_SERVLET)) {
			errorHandler(dialog);
			return false;
		}

		// ���ʂ��擾
		List result = super.getResultList();
		// ���ʂ�Ԃ�
		return (result.size() > 0);
	}
}
