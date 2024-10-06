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
 * @author yangjing
 */
public class MP0030EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** ��s�����}�X�^�_�C�A���O */
	protected MP0030EditDisplayDialog dialog;

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "MP0030BankAccountMasterServlet";

	/** map�̏����� */
	protected Map yknKbnMap;

	/** �����敪 */
	protected boolean isUpdate = false;

	/** �ʉ݃R�[�h */
	protected REFDialogCtrl refCurCode;

	/** ��s�R�[�h */
	protected REFDialogCtrl refCbkBnkCode;

	/** �x�X�R�[�h */
	protected REFDialogCtrl refCbkStnCode;

	/** �v�㕔��R�[�h */
	protected REFDialogCtrl refCbkDepCode;

	/**
	 * �R���X�g���N�^
	 */
	protected MP0030EditDisplayDialogCtrl() {
		// �����Ȃ�
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	MP0030EditDisplayDialogCtrl(Frame parent, String titleid) {
		// ��s�����}�X�^�_�C�A���O�̏�����
		dialog = new MP0030EditDisplayDialog(parent, true, this, titleid);
		// �^�C�g����ݒ肷��
		// �_�C�A���O������Adialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});

		init();

		setItemCondition();

	}

	/**
	 * �ȖڃR���|�[�l���g �����ݒ�(�w�b�_)
	 */
	public void setItemCondition() {
		// ���O�C�����[�U�[�̉�ЃR�[�h
		dialog.ctrlItemUnit.getInputParameter().setCompanyCode(super.getLoginUserCompanyCode());
	}

	/**
	 * �_�C�A���O������
	 */
	protected void init() {
		// �W�v�敪�̐ݒ�
		dialog.rdoEmployeeFBDivisionException.setSelected(true);
		dialog.rdoExternalFBException.setSelected(true);

		// �x�X�E�⏕�E�����������
		dialog.ctrlBranch.setEnabled(true);
		dialog.ctrlBranch.setEditable(false);
		dialog.ctrlBranch.setButtonEnabled(false);
		dialog.ctrlItemUnit.getTSubItemField().setEnabled(true);
		dialog.ctrlItemUnit.getTSubItemField().setEditable(false);
		dialog.ctrlItemUnit.getTSubItemField().setButtonEnabled(false);
		dialog.ctrlItemUnit.getTBreakDownItemField().setEnabled(true);
		dialog.ctrlItemUnit.getTBreakDownItemField().setEditable(false);
		dialog.ctrlItemUnit.getTBreakDownItemField().setButtonEnabled(false);

		// �ʉ݃R�[�h
		refCurCode = new REFDialogCtrl(dialog.ctrlCurrency, dialog.getParentFrame());
		refCurCode.setColumnLabels("C00665", "C00881", "C00882");
		refCurCode.setTargetServlet("MG0300CurrencyMasterServlet");
		refCurCode.setTitleID("C01985");
		refCurCode.setShowsMsgOnError(false);
		refCurCode.addIgnoredButton(dialog.btnReturn);

		refCurCode.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				/* KIND */
				keys.put("kind", "Currency");
				return keys;
			}
		});

		dialog.ctrlCurrency.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refCurCode.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlCurrency.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlCurrency.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlCurrency.getValue());
					dialog.ctrlCurrency.getField().clearOldText();
					dialog.ctrlCurrency.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		// ��s�R�[�h
		refCbkBnkCode = new REFDialogCtrl(dialog.ctrlBank, dialog.getParentFrame());
		refCbkBnkCode.setColumnLabels("C00779", "C00781", "C00829");
		refCbkBnkCode.setTargetServlet("MG0140BankMasterServlet");
		refCbkBnkCode.setTitleID("C02323");
		refCbkBnkCode.setShowsMsgOnError(false);
		refCbkBnkCode.addIgnoredButton(dialog.btnReturn);

		refCbkBnkCode.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				/* KIND */
				keys.put("kind", "Bank");
				return keys;
			}

			// ��s�R�[�h�ɂċ�s�}�X�^�ɑ���
			public void goodCodeInputted() {
				dialog.ctrlBranch.setEditable(true);
				dialog.ctrlBranch.setButtonEnabled(true);

			}

			// ��s�R�[�h�ɂċ�s�}�X�^�ɑ��݂��Ȃ�
			public void badCodeInputted() {
				dialog.ctrlBranch.setValue("");
				dialog.ctrlBranch.setEditable(false);
				dialog.ctrlBranch.setNoticeEditable(true);
				dialog.ctrlBranch.setNoticeValue("");
				dialog.ctrlBranch.setNoticeEditable(false);
				dialog.ctrlBranch.setButtonEnabled(false);
			}

			// ��s�R�[�h���N���A
			public void codeChanged() {
				dialog.ctrlBranch.setValue("");
				dialog.ctrlBranch.setNoticeValue("");
			}
		});

		dialog.ctrlBank.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refCbkBnkCode.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlBank.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlBank.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlBank.getValue());
					dialog.ctrlBank.getField().clearOldText();
					dialog.ctrlBank.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		// �x�X
		refCbkStnCode = new REFDialogCtrl(dialog.ctrlBranch, dialog.getParentFrame());
		refCbkStnCode.setColumnLabels("C00780", "C00783", "C00785");
		refCbkStnCode.setTargetServlet("MG0140BankMasterServlet");
		refCbkStnCode.setTitleID("C00778");
		refCbkStnCode.setShowsMsgOnError(false);
		refCbkStnCode.addIgnoredButton(dialog.btnReturn);

		refCbkStnCode.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("bnkCode", dialog.ctrlBank.getField().getText());
				/* KIND */
				keys.put("kind", "BankBranch");
				return keys;
			}
		});

		dialog.ctrlBranch.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refCbkStnCode.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlBranch.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlBranch.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlBranch.getValue());
					dialog.ctrlBranch.getField().clearOldText();
					dialog.ctrlBranch.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		// �v�㕔��
		refCbkDepCode = new REFDialogCtrl(dialog.ctrlAppropriateDepartment, dialog.getParentFrame());
		refCbkDepCode.setColumnLabels("C00698", "C00724", "C00725");
		refCbkDepCode.setTargetServlet("MG0060DepartmentMasterServlet");
		refCbkDepCode.setTitleID("C02338");
		refCbkDepCode.setShowsMsgOnError(false);
		refCbkDepCode.addIgnoredButton(dialog.btnReturn);

		refCbkDepCode.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				/* KIND */
				keys.put("kind", "Department");
				return keys;
			}
		});

		dialog.ctrlAppropriateDepartment.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refCbkDepCode.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlAppropriateDepartment.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlAppropriateDepartment.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlAppropriateDepartment.getValue());
					dialog.ctrlAppropriateDepartment.getField().clearOldText();
					dialog.ctrlAppropriateDepartment.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		dialog.dtBeginDate.setValue(minInputDate);
		dialog.dtEndDate.setValue(maxInputDate);
		dialog.ctrlBankAccount.requestTextFocus();

	}

	/**
	 * @param map
	 */
	public void setYknKbnMap(Map map) {
		this.yknKbnMap = map;

		this.fillItemsToComboBox(dialog.ctrlDepositType.getComboBox(), yknKbnMap);

	}

	/**
	 * �\��
	 */
	void show() {
		dialog.setVisible(true);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map �Z�b�g����l
	 */

	void setValues(Map map) {
		boolean boo;
		// �ȖڃR�[�h�̐ݒ�
		dialog.ctrlBankAccount.setValue((String) map.get("cbkCbkCode"));
		// ��s�������̂̐ݒ�
		dialog.txtBankAccountName.setValue(map.get("cbkName"));
		// �ʉ݃R�[�h�̐ݒ�
		dialog.ctrlCurrency.setValue((String) map.get("curCode"));
		// ��s�R�[�h�̐ݒ�
		dialog.ctrlBank.setValue((String) map.get("cbkBnkCode"));
		dialog.ctrlBranch.setEditable(true);
		dialog.ctrlBranch.setButtonEnabled(true);
		// �x�X�R�[�h�̐ݒ�
		dialog.ctrlBranch.setValue((String) map.get("cbkStnCode"));
		// �U���˗��l�R�[�h�̐ݒ�
		dialog.ctrlTransferRequesterCode.setValue((String) map.get("cbkIraiEmpCode"));
		// �U���˗��l���̐ݒ�
		dialog.ctrlTransferRequesterKanaName.setValue((String) map.get("cbkIraiName"));
		// �U���˗��l���i�����j�̐ݒ�
		dialog.ctrlRemittanceRequesterKanjiName.setValue((String) map.get("cbkIraiName_J"));
		// �U���˗��l���i�p���j�̐ݒ�
		dialog.ctrlRemittanceRequesterEnglishName.setValue((String) map.get("cbkIraiName_E"));
		// �a����ڂ̐ݒ�
		this.setComboBoxSelectedItem(dialog.ctrlDepositType.getComboBox(), (String) map.get("cbkYknKbn"));
		// �����ԍ��̐ݒ�
		dialog.ctrlAccountNumber.setValue((String) map.get("cbkYknNo"));
		// �Ј��e�a�敪�̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("cbkEmpFbKbn")));
		dialog.rdoEmployeeFBDivisionException.setSelected(!boo);
		dialog.rdoEmployeeFBUse.setSelected(boo);
		// �ЊO�e�a�敪�̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("cbkOutFbKbn")));
		dialog.rdoExternalFBException.setSelected(!boo);
		dialog.rdoExternalFBUse.setSelected(boo);
		// �v�㕔��R�[�h�̐ݒ�
		dialog.ctrlAppropriateDepartment.setValue((String) map.get("cbkDepCode"));

		String itemCode = Util.avoidNull(map.get("cbkKmkCode"));
		String subItemCode = Util.avoidNull(map.get("cbkHkmCode"));

		// �Ȗڃp�����[�^
		dialog.ctrlItemUnit.getInputParameter().setItemCode(itemCode);
		dialog.ctrlItemUnit.getInputParameter().setSubItemCode(subItemCode);

		// �Ȗ�
		dialog.ctrlItemUnit.setItemCode(itemCode);

		// �⏕�Ȗ�
		dialog.ctrlItemUnit.setSubItemCode(subItemCode);

		// ����Ȗ�
		dialog.ctrlItemUnit.setBreakDownItemCode(Util.avoidNull(map.get("cbkUkmCode")));

		// �J�n�N�����̐ݒ�
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// �I���N�����̐ݒ�
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// �ҏW���[�h�̂Ƃ��͓E�v�R�[�h�A�E�v�敪�Ƃ��ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));
		dialog.ctrlBankAccount.setEditable(!isUpdate);

		// �ʉݖ��̂̒l�����
		refCurCode.refreshName();
		// ��s���̂̒l�����
		refCbkBnkCode.refreshName();
		// �x�X���̂̒l�����
		refCbkStnCode.refreshName();
		// �v�㕔��R�[�h�̒l�����
		refCbkDepCode.refreshName();

		if (isUpdate) {
			dialog.txtBankAccountName.requestFocus();
		}
	}

	/**
	 * ����
	 */
	void disposeDialog() {

		// �G���[������ꍇ�ɂ̓_�C�A���O����Ȃ�
		if (dialog.isSettle) {
			// ��ʕK�{���͍��ڂ̃`���b�N
			if (checkDialog()) {
				dialog.setVisible(false);
			} else {
				dialog.isSettle = false;
			}
		} else {
			dialog.setVisible(false);
		}
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
	 * ��ʏ�\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 */

	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();
		// ��s�����R�[�h�̐ݒ�
		map.put("cbkCbkCode", dialog.ctrlBankAccount.getValue());
		// ��s�������̂̐ݒ�
		map.put("cbkName", dialog.txtBankAccountName.getValue());
		// �ʉ݃R�[�h�̐ݒ�
		map.put("curCode", dialog.ctrlCurrency.getValue());
		// ��s�R�[�h�̐ݒ�
		map.put("cbkBnkCode", dialog.ctrlBank.getValue());
		// �x�X�R�[�h�̐ݒ�
		map.put("cbkStnCode", dialog.ctrlBranch.getValue());
		// �U���˗��l�R�[�h�̐ݒ�
		map.put("cbkIraiEmpCode", dialog.ctrlTransferRequesterCode.getValue());
		// �U���˗��l���̐ݒ�
		map.put("cbkIraiName", dialog.ctrlTransferRequesterKanaName.getValue());
		// �U���˗��l���i�����j�̐ݒ�
		map.put("cbkIraiName_J", dialog.ctrlRemittanceRequesterKanjiName.getValue());
		// �U���˗��l���i�p���j�̐ݒ�
		map.put("cbkIraiName_E", dialog.ctrlRemittanceRequesterEnglishName.getValue());
		// �a����ڂ̐ݒ�
		map.put("cbkYknKbn", this.getComboBoxSelectedValue(dialog.ctrlDepositType.getComboBox()));
		// �����ԍ��̐ݒ�
		map.put("cbkYknNo", dialog.ctrlAccountNumber.getValue());
		// �Ј��e�a�敪�̐ݒ�
		map.put("cbkEmpFbKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoEmployeeFBUse.isSelected())));
		// �ЊO�e�a�敪�̐ݒ�
		map.put("cbkOutFbKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoExternalFBUse.isSelected())));
		// �v�㕔��R�[�h�̐ݒ�
		map.put("cbkDepCode", dialog.ctrlAppropriateDepartment.getValue());
		// �ȖڃR�[�h�̐ݒ�
		map.put("cbkKmkCode", dialog.ctrlItemUnit.getTItemField().getValue());
		// �⏕�ȖڃR�[�h�̐ݒ�
		map.put("cbkHkmCode", dialog.ctrlItemUnit.getTSubItemField().getValue());
		// ����ȖڃR�[�h�̐ݒ�
		map.put("cbkUkmCode", dialog.ctrlItemUnit.getTBreakDownItemField().getValue());
		// �J�n�N�����̐ݒ�
		map.put("strDate", dialog.dtBeginDate.getValue());
		// �I���N�����̐ݒ�
		map.put("endDate", dialog.dtEndDate.getValue());
		// ���ʂ�Ԃ�
		return map;
	}

	boolean checkCode(String cbkCbkCode) {
		// ��s�����R�[�h������
		if (Util.isNullOrEmpty(cbkCbkCode)) {
			return false;
		}
		// ������ʂ̐ݒ�
		addSendValues("flag", "checkcode");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// ��s�����R�[�h�̐ݒ�
		addSendValues("cbkCbkCode", cbkCbkCode);

		try {
			if (!request(TARGET_SERVLET)) {
				errorHandler(dialog);
				return true;
			}

			// ���ʂ��擾
			List result = super.getResultList();
			// ���ʂ�Ԃ�
			return (result.size() > 0);
		} catch (IOException e) {
			errorHandler(dialog, e, "E00009");
			return false;
		}
	}

	/**
	 * �_�C�A���O�̓��͍��ڃ`�F�b�N
	 * 
	 * @return true �`�F�b�N���� false �G���[������
	 */
	boolean checkDialog() {

		// ��s�����R�[�h�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlBankAccount.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(dialog, "I00002", "C00857");
			dialog.ctrlBankAccount.requestFocus();
			return false;
		}

		if (!isUpdate && checkCode(dialog.ctrlBankAccount.getValue())) {
			showMessage(dialog, "W00005", "C00174");
			dialog.ctrlBankAccount.requestFocus();
			return false;
		}

		// ��s��������
		if (Util.isNullOrEmpty(dialog.txtBankAccountName.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(dialog, "I00002", "C02145");
			dialog.txtBankAccountName.requestFocus();
			return false;
		}

		// �ʉ݃`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlCurrency.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(dialog, "I00002", "C00371");
			dialog.ctrlCurrency.requestTextFocus();
			return false;
		}

		// ��s�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlBank.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(dialog, "I00002", "C00124");
			dialog.ctrlBank.requestTextFocus();
			return false;
		}

		// �x�X�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlBranch.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(dialog, "I00002", "C00222");
			dialog.ctrlBranch.requestTextFocus();
			return false;
		}

		// �U���˗��l�R�[�h�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlTransferRequesterCode.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(dialog, "I00002", "C00858");
			dialog.ctrlTransferRequesterCode.requestFocus();
			return false;
		}

		// �U���˗��l���̐ݒ�
		if (Util.isNullOrEmpty(dialog.ctrlTransferRequesterKanaName.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(dialog, "I00002", "C00859");
			dialog.ctrlTransferRequesterKanaName.requestFocus();
			return false;
		}

		// �����ԍ��`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlAccountNumber.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(dialog, "I00002", "C00794");
			dialog.ctrlAccountNumber.requestFocus();
			return false;
		}

		// �a����ڃ`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlDepositType.getComboBox().getSelectedItem().toString())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(dialog, "I00002", "C01326");
			dialog.ctrlDepositType.getComboBox().requestFocus();
			return false;
		}

		// �v�㕔��`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlAppropriateDepartment.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(dialog, "I00002", "C00863");
			dialog.ctrlAppropriateDepartment.requestTextFocus();
			return false;
		}

		// �Ȗڃ`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlItemUnit.getTItemField().getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(dialog, "I00002", "C00572");
			dialog.ctrlItemUnit.getTItemField().requestTextFocus();
			return false;
		}

		// �⏕�`�F�b�N
		if (dialog.ctrlItemUnit.getTSubItemField().getField().isEditable()
			&& Util.isNullOrEmpty(dialog.ctrlItemUnit.getTSubItemField().getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(dialog, "I00002", "C00602");
			dialog.ctrlItemUnit.getTSubItemField().requestTextFocus();
			return false;
		}

		// ����`�F�b�N
		if (dialog.ctrlItemUnit.getTBreakDownItemField().getField().isEditable()
			&& Util.isNullOrEmpty(dialog.ctrlItemUnit.getTBreakDownItemField().getValue())) {
			showMessage(dialog, "I00002", "C00603");
			dialog.ctrlItemUnit.getTBreakDownItemField().requestTextFocus();
			return false;
		}

		// �J�n�N����
		if (Util.isNullOrEmpty(dialog.dtBeginDate.getValue())) {
			showMessage(dialog, "W00034", "C00055");
			dialog.dtBeginDate.getCalendar().requestFocus();
			return false;
		}

		// �I���N����
		if (Util.isNullOrEmpty(dialog.dtEndDate.getValue())) {
			showMessage(dialog, "W00034", "C00261");
			dialog.dtEndDate.getCalendar().requestFocus();
			return false;
		}

		// �J�n�N���������I���N�����ɂ��Ă�������
		if (Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue()) == false) {
			dialog.dtBeginDate.getCalendar().requestFocus();
			showMessage(dialog, "W00035", "");
			return false;
		}

		return true;

	}
}
