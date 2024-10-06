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
 * �Ј��}�X�^�_�C�A���O
 */
public class MG0160EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** �Ј��}�X�^�_�C�A���O */
	protected MG0160EditDisplayDialog dialog;

	/**
	 * �ڑ���Servlet����
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "MG0160EmployeeMasterServlet";
	}

	/** �����敪 */
	protected boolean isUpdate = false;

	protected REFDialogCtrl ref1;

	protected REFDialogCtrl ref2;

	protected REFDialogCtrl ref3;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parent
	 * @param titleid
	 */
	public MG0160EditDisplayDialogCtrl(Frame parent, String titleid) {
		// �Ǘ��}�X�^�_�C�A���O�̏�����
		dialog = createDialog(parent, titleid);

		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent ev) {
				dialog.isSettle = false;
				dialog.setVisible(false);
			}
		});

		init();

		dialog.ctrlTransferBranch.getField().setEditable(false);
		dialog.ctrlTransferBranch.getButton().setEnabled(false);
		dialog.rdoNormally.setSelected(true);

		dialog.ctrlEmployeeCode.getField().requestFocus();
		dialog.dtBeginDate.setValue(minInputDate);
		dialog.dtEndDate.setValue(maxInputDate);

	}

	/**
	 * @param parent �e�R���e�i�[
	 * @param titleid �^�C�g��
	 * @return �_�C�A���O
	 */
	protected MG0160EditDisplayDialog createDialog(Frame parent, String titleid) {
		return new MG0160EditDisplayDialog(parent, true, this, titleid);
	}

	void init() {

		// �U�o��s�����̐ݒ�
		ref1 = new REFDialogCtrl(dialog.ctrlDrawingBankAccount, dialog.getParentFrame());
		ref1.setColumnLabels("C00857", "C00124", "C00794");
		ref1.setTargetServlet("MP0030BankAccountMasterServlet");
		ref1.setTitleID("C02322");
		ref1.setShowsMsgOnError(false);

		dialog.ctrlDrawingBankAccount.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlDrawingBankAccount.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlDrawingBankAccount.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlDrawingBankAccount.getValue());
					dialog.ctrlDrawingBankAccount.getField().clearOldText();
					dialog.ctrlDrawingBankAccount.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		ref1.setREFListener(new REFAdapter() {

			@Override
			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "BankAccount");

				return keys;
			}

			// ��s�R�[�h�ɂċ�s�}�X�^�ɑ���
			@Override
			public void goodCodeInputted() {
				try {
					addSendValues("flag", "getrefitems");
					addSendValues("kaiCode", getLoginUserCompanyCode());
					addSendValues("cbkCbkCode", dialog.ctrlDrawingBankAccount.getField().getText());

					if (!request("MP0030BankAccountMasterServlet")) {
						errorHandler(dialog);
						return;
					}

					List result = getResultList().get(0);
					if (result != null && result.size() == 6) {
						String text1 = (String) result.get(1) + " " + (String) result.get(3);

						String rsText = (String) result.get(4);
						if ("1".equals(rsText)) {
							rsText = getWord("C00463");
						} else if ("2".equals(rsText)) {
							rsText = getWord("C00397");
						} else if ("3".equals(rsText)) {
							rsText = getWord("C00045");
						} else {
							rsText = getWord("C00368");
						}
						String text2 = rsText + " " + (String) result.get(5);

						dialog.ctrlDrawingBankAccount.getNotice().setEditable(true);
						dialog.ctrlDrawingBankAccount.getNotice().setText(text1);
						dialog.ctrlDrawingBankAccount.getNotice().setEditable(false);

						dialog.txtTransferAccountDepositType.setEditable(true);
						dialog.txtTransferAccountDepositType.setText(text2);
						dialog.txtTransferAccountDepositType.setEditable(false);
					}
				} catch (IOException e) {
					errorHandler(dialog, e, "E00009");
				}
			}

			// ��s�R�[�h�ɂċ�s�}�X�^�ɑ��݂��Ȃ�
			@Override
			public void badCodeInputted() {
				dialog.ctrlDrawingBankAccount.getNotice().setEditable(true);
				dialog.ctrlDrawingBankAccount.getNotice().setText("");
				dialog.ctrlDrawingBankAccount.getNotice().setEditable(false);

				dialog.txtTransferAccountDepositType.setEditable(true);
				dialog.txtTransferAccountDepositType.setText("");
				dialog.txtTransferAccountDepositType.setEditable(false);
			}

			// ��s�R�[�h���N���A
			@Override
			public void textCleared() {
				badCodeInputted();
			}
		});

		// �U����s�R�[�h�̐ݒ�
		ref2 = new REFDialogCtrl(dialog.ctrlTransferBank, dialog.getParentFrame());
		ref2.setTargetServlet("MG0140BankMasterServlet");
		ref2.setTitleID("C02323");
		ref2.setColumnLabels("C00779", "C00781", "C00829");
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			@Override
			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				keys.put("kind", "Bank");
				return keys;
			}

			@Override
			public void goodCodeInputted() {
				dialog.ctrlTransferBranch.getField().setEditableEnabled(true);
				dialog.ctrlTransferBranch.getButton().setEnabled(true);

			}

			@Override
			public void badCodeInputted() {
				dialog.ctrlTransferBranch.getField().setText(null);
				dialog.ctrlTransferBranch.getField().setEditableEnabled(false);
				dialog.ctrlTransferBranch.getNotice().setEditable(true);
				dialog.ctrlTransferBranch.getNotice().setText(null);
				dialog.ctrlTransferBranch.getNotice().setEditable(false);
				dialog.ctrlTransferBranch.getButton().setEnabled(false);
			}

			@Override
			public void codeChanged() {
				dialog.ctrlTransferBranch.getField().setText(null);
				dialog.ctrlTransferBranch.getNotice().setText(null);
			}
		});

		dialog.ctrlTransferBank.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				if (dialog.ctrlTransferBank.isValueChanged()) {
					if (!Util.isNullOrEmpty(dialog.ctrlTransferBank.getValue().trim())
						&& !Util.isNullOrEmpty(dialog.ctrlTransferBank.getNotice().getText().trim())) {
						dialog.ctrlTransferBranch.getField().setText(null);
						dialog.ctrlTransferBranch.getNotice().setText(null);

					}
				}

				if (!Util.isNullOrEmpty(dialog.ctrlTransferBank.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlTransferBank.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlTransferBank.getValue());
					dialog.ctrlTransferBank.getField().clearOldText();
					dialog.ctrlTransferBank.getField().requestFocus();
					return false;

				}
				return true;
			}
		});

		// �U���x�X�R�[�h�̐ݒ�
		ref3 = new REFDialogCtrl(dialog.ctrlTransferBranch, dialog.getParentFrame());
		ref3.setTargetServlet("MG0140BankMasterServlet");
		ref3.setTitleID("C00778");
		ref3.setColumnLabels("C00780", "C00783", "C00785");
		ref3.setShowsMsgOnError(false);
		ref3.setREFListener(new REFAdapter() {

			@Override
			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				keys.put("bnkCode", dialog.ctrlTransferBank.getField().getText());
				keys.put("kind", "BankBranch");
				return keys;
			}

		});

		dialog.ctrlTransferBranch.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				ref3.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlTransferBranch.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlTransferBranch.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlTransferBranch.getValue());
					dialog.ctrlTransferBranch.getField().clearOldText();
					dialog.ctrlTransferBranch.getField().requestFocus();
					return false;

				}
				return true;
			}
		});

	}

	/**
	 * �\��
	 */
	void show() {
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
	 * �l�̃Z�b�g
	 * 
	 * @param map �Z�b�g����l
	 */

	void setValues(Map map) {
		dialog.ctrlTransferBranch.getButton().setEnabled(true);
		dialog.ctrlTransferBranch.getField().setEditable(true);

		// �Ј��R�[�h�̐ݒ�
		dialog.ctrlEmployeeCode.setValue((String) map.get("empCode"));
		// �Ј����̂̐ݒ�
		dialog.ctrlEmployeeName.setValue((String) map.get("empName"));
		// �Ј����̂̐ݒ�
		dialog.ctrlEmployeeAbbreviationName.setValue((String) map.get("empName_S"));
		// �Ј��������̂̐ݒ�
		dialog.ctrlEmployeeNameForSearch.setValue((String) map.get("empName_K"));
		// �U����s�b�c�̐ݒ�
		dialog.ctrlTransferBank.setValue((String) map.get("empFuriBnkCode"));
		// �U���x�X�b�c�̐ݒ�
		dialog.ctrlTransferBranch.setValue((String) map.get("empFuriStnCode"));
		// �U�������ԍ��̐ݒ�
		dialog.ctrlTransferAccountNumber.setValue((String) map.get("empYknNo"));
		// �U�������a����ʂ̐ݒ�
		if (BooleanUtil.toBoolean(Integer.parseInt((String) map.get("empKozaKbn")))) {
			dialog.rdoNormally.setSelected(true);
			dialog.rdoInterim.setSelected(false);
		} else {
			dialog.rdoNormally.setSelected(false);
			dialog.rdoInterim.setSelected(true);
		}

		// �������`�J�i�̐ݒ�
		dialog.ctrlAccountName.setValue((String) map.get("empYknKana"));
		// �U�o��s�����R�[�h�̐ݒ�
		dialog.ctrlDrawingBankAccount.setValue((String) map.get("empCbkCode"));
		// �J�n�N�����̐ݒ�
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// �I���N�����̐ݒ�
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// �ҏW���[�h�̂Ƃ��͒ʉ݃R�[�h���ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));
		dialog.ctrlEmployeeCode.setEditable(!isUpdate); // 2006/12/29 Yanwei
		if (isUpdate) {
			dialog.ctrlEmployeeName.getField().requestFocus();
		}
		ref1.refreshName();
		ref2.refreshName();
		ref3.refreshName();
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
			// �Ј��R�[�h�`�F�b�N
			if (Util.isNullOrEmpty(dialog.ctrlEmployeeCode.getValue())) {
				showMessage(dialog, "I00002", "C00697");
				dialog.ctrlEmployeeCode.requestFocus();
				return false;
			}

			if (!Util.isNullOrEmpty(dialog.ctrlEmployeeCode.getValue()) && !isUpdate
				&& checkCode(dialog.ctrlEmployeeCode.getValue())) {
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlEmployeeCode.requestFocus();
				return false;
			}

			// �Ј����̃`�F�b�N
			if (Util.isNullOrEmpty(dialog.ctrlEmployeeName.getValue())) {
				showMessage(dialog, "I00002", "C00807");
				dialog.ctrlEmployeeName.requestFocus();
				return false;
			}
			// �Ј����̃`�F�b�N
			if (Util.isNullOrEmpty(dialog.ctrlEmployeeAbbreviationName.getValue())) {
				showMessage(dialog, "I00002", "C00808");
				dialog.ctrlEmployeeAbbreviationName.requestFocus();
				return false;
			}
			// �Ј��������̃`�F�b�N
			if (Util.isNullOrEmpty(dialog.ctrlEmployeeNameForSearch.getValue())) {
				showMessage(dialog, "I00002", "C00809");
				dialog.ctrlEmployeeNameForSearch.requestFocus();
				return false;
			}
			// �����ԍ��̌����`�F�b�N
			if (!Util.isNullOrEmpty(dialog.ctrlTransferAccountNumber.getValue())
				&& dialog.ctrlTransferAccountNumber.getValue().length() != 7) {
				showMessage(dialog, "W00143", "7" + getWord("C02161"));
				dialog.ctrlTransferAccountNumber.requestFocus();
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
	 * ��ʏ�\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 */

	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();
		// �Ј��R�[�h�̐ݒ�
		map.put("empCode", dialog.ctrlEmployeeCode.getValue());
		// �Ј����̂̐ݒ�
		map.put("empName", dialog.ctrlEmployeeName.getValue());
		// �Ј����̂̐ݒ�
		map.put("empName_S", dialog.ctrlEmployeeAbbreviationName.getValue());
		// �Ј��������̂̐ݒ�
		map.put("empName_K", dialog.ctrlEmployeeNameForSearch.getValue());
		// �U����s�b�c�̐ݒ�
		map.put("empFuriBnkCode", dialog.ctrlTransferBank.getValue());
		// �U���x�X�b�c�̐ݒ�
		map.put("empFuriStnCode", dialog.ctrlTransferBranch.getValue());
		// �U�������ԍ��̐ݒ�
		map.put("empYknNo", dialog.ctrlTransferAccountNumber.getValue());
		// �U�������a����ʂ̐ݒ�
		if (dialog.rdoNormally.isSelected()) {
			int rdo = 1;
			map.put("empKozaKbn", String.valueOf(rdo));
		} else {
			int rdo = 2;
			map.put("empKozaKbn", String.valueOf(rdo));
		}
		// �������`�J�i�̐ݒ�
		map.put("empYknKana", dialog.ctrlAccountName.getValue());
		// �U�o��s�����R�[�h�̐ݒ�
		map.put("empCbkCode", dialog.ctrlDrawingBankAccount.getValue());
		// �J�n�N�����̐ݒ�
		map.put("strDate", dialog.dtBeginDate.getValue());
		// �I���N�����̐ݒ�
		map.put("endDate", dialog.dtEndDate.getValue());
		// ���ʂ�Ԃ�
		return map;
	}

	boolean checkCode(String code) throws IOException {
		// �Ǘ��R�[�h������
		if (Util.isNullOrEmpty(code)) {
			return false;
		}
		// ������ʂ̐ݒ�
		addSendValues("flag", "checkcode");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// �Ј��R�[�h�̐ݒ�
		addSendValues("empCode", code);

		if (!request(getServletName())) {
			errorHandler(dialog);
			return true;
		}
		// ���ʂ��擾
		List result = super.getResultList();
		// ���ʂ�Ԃ�
		return (result.size() > 0);
	}
}
