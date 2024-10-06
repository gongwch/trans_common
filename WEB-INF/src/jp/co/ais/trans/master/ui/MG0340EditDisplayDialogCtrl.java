package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * �J�����x���}�X�^
 */
public class MG0340EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** ����}�X�^�_�C�A���O */
	private MG0340EditDisplayDialog dialog;

	/** �����敪 */
	private boolean isUpdate;

	/** ����p�t���O */
	private boolean flag = true;

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0340IndicationLevelMasterServlet";

	private REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	private REFDialogCtrl ref3;

	private REFDialogCtrl ref4;

	private Map dpkSskMap;

	private Map levelMap;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	MG0340EditDisplayDialogCtrl(Frame parent, String titleid) {
		// ����}�X�^�_�C�A���O�̏�����
		dialog = new MG0340EditDisplayDialog(parent, true, this, titleid);

		ref1 = new REFDialogCtrl(dialog.ctrlUser, dialog.getParentFrame());
		ref1.setColumnLabels("C00589", "C00692", "C00693");
		ref1.setTargetServlet("MG0020UserMasterServlet");
		ref1.setTitleID(getWord("C02355"));
		ref1.setShowsMsgOnError(false);
		ref1.addIgnoredButton(dialog.btnReturn);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "User");
				return keys;
			}
		});

		dialog.ctrlUser.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlUser.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlUser.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlUser.getValue());
					dialog.ctrlUser.getField().clearOldText();
					dialog.ctrlUser.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		ref2 = new REFDialogCtrl(dialog.ItemSystem, dialog.getParentFrame());
		ref2.setColumnLabels("C00617", "C00619", "C00620");
		ref2.setTargetServlet("KmkTkMstServlet");
		ref2.setTitleID(getWord("C00609"));
		ref2.setShowsMsgOnError(false);
		ref2.addIgnoredButton(dialog.btnReturn);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "KmkTkMst");
				return keys;
			}
		});

		dialog.ItemSystem.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				if (!Util.isNullOrEmpty(dialog.ItemSystem.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ItemSystem.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ItemSystem.getValue());
					dialog.ItemSystem.getField().clearOldText();
					dialog.ItemSystem.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		ref3 = new REFDialogCtrl(dialog.ctrlDepartment, dialog.getParentFrame());
		ref3.setColumnLabels("C00698", "C00724", "C00725");
		ref3.setTargetServlet("MG0060DepartmentMasterServlet");
		ref3.setTitleID(getWord("C02338"));
		ref3.setShowsMsgOnError(false);
		ref3.addIgnoredButton(dialog.btnReturn);
		ref3.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("dpkSsk", getComboBoxSelectedValue(dialog.ctrlOrganizationCode.getComboBox()));
				keys.put("level", getComboBoxSelectedValue(dialog.ctrlHierarchicalLevel.getComboBox()));
				keys.put("parentDepCode", dialog.ctrlUpperDepartment.getField().getText());
				keys.put("kind", "DepartmentForMG0340");
				return keys;
			}
		});

		dialog.ctrlDepartment.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref3.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlDepartment.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlDepartment.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlDepartment.getValue());
					dialog.ctrlDepartment.getField().clearOldText();
					dialog.ctrlDepartment.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		ref4 = new REFDialogCtrl(dialog.ctrlUpperDepartment, dialog.getParentFrame());
		ref4.setColumnLabels("C00698", "C00724", "C00725");
		ref4.setTargetServlet("MG0060DepartmentMasterServlet");
		ref4.setTitleID(getWord("C02338"));
		ref4.setShowsMsgOnError(false);
		ref4.addIgnoredButton(dialog.btnReturn);
		ref4.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("dpkSsk", getComboBoxSelectedValue(dialog.ctrlOrganizationCode.getComboBox()));
				keys.put("level", getComboBoxSelectedValue(dialog.ctrlHierarchicalLevel.getComboBox()));
				keys.put("kind", "UpperDepartmentForMG0340");
				return keys;
			}

			public void goodCodeInputted() {
				if (flag) {
					dialog.ctrlDepartment.setEditable(true);
					dialog.ctrlDepartment.setButtonEnabled(true);
					flag = false;
				} else {
					if (!getWord("C00991").equals(
						dialog.ctrlHierarchicalLevel.getComboBox().getSelectedItem().toString())) {
						dialog.ctrlDepartment.setEditable(true);
						dialog.ctrlDepartment.setButtonEnabled(true);
						dialog.ctrlDepartment.setValue("");
						dialog.ctrlDepartment.setNoticeValue("");
					}
				}
			}

			public void badCodeInputted() {
				// �K�w���x�����O�̏ꍇ�́A����I�����o����悤�ɂ���B
				if (getWord("C00991").equals(dialog.ctrlHierarchicalLevel.getComboBox().getSelectedItem().toString())) {
					this.goodCodeInputted();
				} else {
					dialog.ctrlDepartment.setButtonEnabled(false);
					dialog.ctrlDepartment.setNoticeEditable(true);
					dialog.ctrlDepartment.setNoticeValue("");
					dialog.ctrlDepartment.setNoticeEditable(false);
					dialog.ctrlDepartment.setValue("");
					dialog.ctrlDepartment.setEditable(false);
				}
			}

			public void codeChanged() {
				dialog.ctrlDepartment.setNoticeValue("");
				dialog.ctrlDepartment.setValue("");
			}
		});

		dialog.ctrlUpperDepartment.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref4.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlUpperDepartment.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlUpperDepartment.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlUpperDepartment.getValue());
					dialog.ctrlUpperDepartment.getField().clearOldText();
					dialog.ctrlUpperDepartment.getField().requestFocus();
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

		try {
			loadDpkSskMapData();
		} catch (IOException e) {
			errorHandler(dialog, e, "E00009");
		}
		levelMap = new LinkedHashMap();
		levelMap.put("0", "C00991");
		levelMap.put("1", "C02126");
		levelMap.put("2", "C02127");
		levelMap.put("3", "C02128");
		levelMap.put("4", "C02129");
		levelMap.put("5", "C02130");
		levelMap.put("6", "C02131");
		levelMap.put("7", "C02132");
		levelMap.put("8", "C02133");
		levelMap.put("9", "C02134");
		this.translateMessageIDForMapData(levelMap);

		this.fillItemsToComboBox(dialog.ctrlOrganizationCode.getComboBox(), dpkSskMap, false);
		this.fillItemsToComboBox(dialog.ctrlHierarchicalLevel.getComboBox(), levelMap, false);

		dialog.ctrlUser.getField().requestFocus(isUpdate);

	}

	/**
	 * �\��
	 * 
	 * @param isEnabledDeptCode ���[�U�[�R�[�h�G���A�ҏW��(true)�A�t��(false)
	 */
	void show(boolean isEnabledDeptCode) {
		// ��ʂ�\������
		dialog.setVisible(true);
		// ����R�[�h�̐ݒ�
		dialog.ctrlUser.setEnabled(isEnabledDeptCode);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map
	 */

	void setValues(Map map) {
		// ���[�U�[�R�[�h�̐ݒ�
		dialog.ctrlUser.setValue((String) map.get("kjlUsrId"));
		// �Ȗڑ̌n���ނ̐ݒ�
		dialog.ItemSystem.setValue((String) map.get("kjlKmtCode"));

		this.setComboBoxSelectedItem(dialog.ctrlHierarchicalLevel.getComboBox(), (String) map.get("kjlLvl"));

		this.setComboBoxSelectedItem(dialog.ctrlOrganizationCode.getComboBox(), (String) map.get("kjlDpkSsk"));

		// ����R�[�h�̐ݒ�
		dialog.ctrlDepartment.setValue((String) map.get("kjlDepCode"));
		// ��ʕ���R�[�h�̐ݒ�
		dialog.ctrlUpperDepartment.setValue((String) map.get("kjlUpDepCode"));

		// �ҏW���[�h�̂Ƃ��͕���R�[�h���ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));
		// ����R�[�h�̐ݒ�

		dialog.ctrlUser.setButtonEnabled(!isUpdate);
		dialog.ctrlUser.setEditable(!isUpdate);
		dialog.ctrlUser.setNoticeEditable(!isUpdate);

		dialog.ItemSystem.setButtonEnabled(!isUpdate);
		dialog.ItemSystem.setEditable(!isUpdate);
		dialog.ItemSystem.setNoticeEditable(!isUpdate);

		dialog.ctrlOrganizationCode.setEnabled(!isUpdate);
		if (isUpdate) {
			dialog.ctrlHierarchicalLevel.getComboBox().requestFocus();
		}
		ref1.refreshName();
		ref2.refreshName();
		ref3.refreshName();
		ref4.refreshName();
	}

	boolean checkDialog() {
		try {// ���[�U�[�R�[�h�����̓`�F�b�N
			if (Util.isNullOrEmpty(dialog.ctrlUser.getField().getText())) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "I00002", "C00528");
				dialog.ctrlUser.requestTextFocus();
				// �G���[��Ԃ�
				return false;
			}

			// ���匟�����̖����̓`�F�b�N
			if (Util.isNullOrEmpty(dialog.ItemSystem.getField().getText())) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "I00002", "C02048");
				dialog.ItemSystem.requestTextFocus();
				// �G���[��Ԃ�
				return false;
			}

			// �g�D�R�[�h�����̓`�F�b�N
			if (Util.isNullOrEmpty(dialog.ctrlOrganizationCode.getComboBox().getSelectedItem().toString())) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "I00002", "C00335");
				dialog.ctrlOrganizationCode.getComboBox().requestFocus();
				// �G���[��Ԃ�
				return false;
			}

			// ���[�U�[�R�[�h�����݂��Ă��܂��`�F�b�N

			if (!isUpdate
				&& checkCode(dialog.ctrlUser.getField().getText(), dialog.ItemSystem.getField().getText(), this
					.getComboBoxSelectedValue(dialog.ctrlOrganizationCode.getComboBox()))) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlUser.requestTextFocus();
				// �G���[��Ԃ�
				return false;
			}

			// �J�����x�������̓`�F�b�N
			if (Util.isNullOrEmpty(dialog.ctrlHierarchicalLevel.getComboBox().getSelectedItem().toString())) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "I00002", "C00060");
				dialog.ctrlHierarchicalLevel.requestFocus();
				// �G���[��Ԃ�
				return false;
			}

			// ��ʕ���R�[�h�����̓`�F�b�N
			if (dialog.ctrlUpperDepartment.getField().isEditable()
				&& Util.isNullOrEmpty(dialog.ctrlUpperDepartment.getField().getText())) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "I00002", "C01909");
				dialog.ctrlUpperDepartment.requestTextFocus();
				// �G���[��Ԃ�
				return false;
			}

			// ����R�[�h�����̓`�F�b�N
			if (dialog.ctrlDepartment.getField().isEditable()
				&& Util.isNullOrEmpty(dialog.ctrlDepartment.getField().getText())) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "I00002", "C00698");
				dialog.ctrlDepartment.requestTextFocus();
				// �G���[��Ԃ�
				return false;
			}

			// �������
			return true;
		} catch (IOException e) {
			ClientErrorHandler.handledException(e);
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
	 * ��ʏ�\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 */
	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();
		// ���[�U�[�R�[�h�̐ݒ�
		map.put("kjlUsrId", dialog.ctrlUser.getValue());
		// ����R�[�h�̐ݒ�
		map.put("kjlDepCode", dialog.ctrlDepartment.getValue());
		// ��ʕ���R�[�h�̐ݒ�
		map.put("kjlUpDepCode", dialog.ctrlUpperDepartment.getValue());
		// �Ȗڑ̌n���ނ̐ݒ�
		map.put("kjlKmtCode", dialog.ItemSystem.getValue());
		// �g�D�R�[�hނ̐ݒ�
		String dpkSsk = this.getComboBoxSelectedValue(dialog.ctrlOrganizationCode.getComboBox());
		map.put("kjlDpkSsk", dpkSsk);
		// �K�w���x���̐ݒ�
		String level = this.getComboBoxSelectedValue(dialog.ctrlHierarchicalLevel.getComboBox());
		// level.substring(3);
		map.put("kjlLvl", level);
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", getLoginUserCompanyCode());
		// ���ʂ�Ԃ�
		return map;
	}

	boolean checkCode(String userCode, String itemCode, String organizationCode) throws IOException {
		// ����R�[�h������
		if (Util.isNullOrEmpty(userCode)) return false;
		if (Util.isNullOrEmpty(itemCode)) return false;
		if (Util.isNullOrEmpty(organizationCode)) return false;
		// ������ʂ̐ݒ�
		addSendValues("flag", "checkcode");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", getLoginUserCompanyCode());

		addSendValues("kjlUsrId", userCode);
		addSendValues("kjlKmtCode", itemCode);
		addSendValues("kjlDpkSsk", organizationCode);
		if (!request(TARGET_SERVLET)) {
			errorHandler(dialog);
			return true;
		}

		// ���ʂ��擾
		List result = super.getResultList();
		// ���ʂ�Ԃ�
		return (result.size() > 0);
	}

	private void loadDpkSskMapData() throws IOException {
		addSendValues("flag", "getorganizations");
		addSendValues("kaiCode", getLoginUserCompanyCode());

		if (!request("MG0050DepartmentHierarchyMasterServlet")) {
			errorHandler(dialog);
			return;
		}
		List result = super.getResultList();
		dpkSskMap = new TreeMap();
		Iterator ite = result.iterator();
		while (ite.hasNext()) {
			List inner = (List) ite.next();

			// �g�D�R�[�h
			String text = (String) inner.get(1);
			dpkSskMap.put(text, text);
		}

	}

	/**
	 * �K�w���x���ύX 0���x���Ȃ��ʑg�D�R�[�h����s�\
	 */
	void checkIsValidOrgLevel() {
		boolean isSelectZero = dialog.ctrlHierarchicalLevel.getComboBox().getSelectedIndex() == 0;

		controlDeptField(!isSelectZero, isSelectZero);
	}

	/**
	 * �g�D�R�[�h�ύX �� �K�w���x��0��
	 */
	void changeOrgCode() {
		if (dialog.ctrlHierarchicalLevel.getComboBox().getItemCount() > 0) {
			dialog.ctrlHierarchicalLevel.getComboBox().setSelectedIndex(0);
		}
	}

	/**
	 * @param isUpperEdit �Ԋ���
	 * @param isDeptEdit �C����
	 */
	private void controlDeptField(boolean isUpperEdit, boolean isDeptEdit) {
		dialog.ctrlUpperDepartment.getField().setText("");
		dialog.ctrlUpperDepartment.getNotice().setText("");
		dialog.ctrlUpperDepartment.getButton().setEnabled(isUpperEdit);
		dialog.ctrlUpperDepartment.setEditable(isUpperEdit);

		dialog.ctrlDepartment.getField().setText("");
		dialog.ctrlDepartment.getNotice().setText("");
		dialog.ctrlDepartment.setEditable(isDeptEdit);
		dialog.ctrlDepartment.getButton().setEnabled(isDeptEdit);
	}
}
