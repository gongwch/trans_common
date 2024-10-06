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
 * ���������}�X�^
 * 
 * @author mayongliang
 */
public class MG0155EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** ���������}�X�^�_�C�A���O */
	protected MG0155EditDisplayDialog dialog;

	/**
	 * �ڑ���Servlet����
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "MG0155CustomerConditionMasterServlet";
	}

	/** �����敪 */
	protected boolean isUpdate = false;

	protected REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	private REFDialogCtrl ref3;

	private REFDialogCtrl ref4;

	private REFDialogCtrl ref5;

	protected REFDialogCtrl ref6;

	/**
	 * �R���X�g���N�^
	 */
	protected MG0155EditDisplayDialogCtrl() {
		// �����Ȃ�
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	MG0155EditDisplayDialogCtrl(Frame parent, String titleid) {
		// ���������}�X�^�_�C�A���O�̏�����
		dialog = new MG0155EditDisplayDialog(parent, true, this, titleid);
		dialog.ctrlCustomerAbbreviationName.setEditable(false);
		dialog.ctrlCustomerAbbreviationName.setEnabled(false);
		dialog.rdoNormally.setSelected(true);
		dialog.rdoMyCompanyPay.setSelected(true);
		dialog.rdoRemittanceRecipient.setSelected(true);

		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.isSettle = false;
				dialog.setVisible(false);
			}
		});
		dialog.ctrlBranch.getField().setEditable(false);
		dialog.ctrlBranch.getButton().setEnabled(false);

		init();

		dialog.ctrlCustomer.getField().requestFocus();
		dialog.dtBeginDate.setValue(minInputDate);
		dialog.dtEndDate.setValue(maxInputDate);

	}

	void init() {
		// �����
		ref1 = new REFDialogCtrl(dialog.ctrlCustomer, dialog.getParentFrame());
		ref1.setTargetServlet("MG0150CustomerMasterServlet");
		ref1.setTitleID("C02326");
		ref1.setColumnLabels("C00786", "C00787", "C00836");
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				keys.put("kind", "Customer");
				keys.put("kaiCode", getLoginUserCompanyCode());
				return keys;
			}

			public void goodCodeInputted() {
				dialog.ctrlCustomerAbbreviationName.getField().setText(dialog.ctrlCustomer.getNotice().getText());
				dialog.ctrlCustomerAbbreviationName.getField().setEnabled(true);
			}

			public void badCodeInputted() {
				dialog.ctrlCustomerAbbreviationName.getField().setText("");
			}

		});

		dialog.ctrlCustomer.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();

				if (!Util.isNullOrEmpty(dialog.ctrlCustomer.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlCustomer.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlCustomer.getValue());
					dialog.ctrlCustomer.getField().clearOldText();
					dialog.ctrlCustomer.getField().requestFocus();
					return false;

				}
				return true;
			}
		});

		// �x�����@

		ref2 = new REFDialogCtrl(dialog.ctrlPaymentMethod, dialog.getParentFrame());
		ref2.setTargetServlet("MP0040PaymentMethodMasterServlet");
		ref2.setTitleID("C02350");
		ref2.setColumnLabels("C00864", "C00865", "C00866");
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// ��s�R�[�h�̐ݒ�
				keys.put("kind", "SelectPaymentMethod");
				keys.put("kaiCode", getLoginUserCompanyCode());
				// �f�[�^��Ԃ�
				return keys;
			}
		});

		dialog.ctrlPaymentMethod.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlPaymentMethod.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlPaymentMethod.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlPaymentMethod.getValue());
					dialog.ctrlPaymentMethod.getField().clearOldText();
					dialog.ctrlPaymentMethod.getField().requestFocus();
					return false;

				}
				return true;
			}
		});

		// ��s
		ref3 = new REFDialogCtrl(dialog.ctrlBank, dialog.getParentFrame());
		ref3.setTargetServlet("MG0140BankMasterServlet");
		ref3.setTitleID("C02323");
		ref3.setColumnLabels("C00779", "C00781", "C00829");
		ref3.setShowsMsgOnError(false);
		ref3.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				keys.put("kind", "Bank");
				return keys;
			}

			// �x�X�R�[�h���݂���
			public void goodCodeInputted() {
				dialog.ctrlBranch.getField().setEditableEnabled(true);
				dialog.ctrlBranch.getButton().setEnabled(true);
			}

			// �x�X�R�[�h���݂��Ȃ�
			public void badCodeInputted() {
				dialog.ctrlBranch.getField().setText(null);
				dialog.ctrlBranch.getField().setEditableEnabled(false);
				dialog.ctrlBranch.getNotice().setEditable(true);
				dialog.ctrlBranch.getNotice().setText(null);
				dialog.ctrlBranch.getNotice().setEditable(false);
				dialog.ctrlBranch.getButton().setEnabled(false);
			}

			public void codeChanged() {
				dialog.ctrlBranch.getField().setText(null);
				dialog.ctrlBranch.getNotice().setText(null);
			}

		});

		dialog.ctrlBank.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref3.refreshName();
				if (dialog.ctrlBank.isValueChanged()) {
					if (!Util.isNullOrEmpty(dialog.ctrlBank.getValue().trim())
						&& !Util.isNullOrEmpty(dialog.ctrlBank.getNotice().getText().trim())) {
						dialog.ctrlBranch.getField().setText(null);
						dialog.ctrlBranch.getNotice().setText(null);

					}
				}

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

		ref4 = new REFDialogCtrl(dialog.ctrlBranch, dialog.getParentFrame());
		ref4.setTargetServlet("MG0140BankMasterServlet");
		ref4.setTitleID("C00778");
		ref4.setColumnLabels("C00780", "C00783", "C00785");
		ref4.setShowsMsgOnError(false);
		ref4.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// ��s�R�[�h�̐ݒ�
				keys.put("kind", "BankBranch");
				keys.put("bnkCode", dialog.ctrlBank.getField().getText());
				// �f�[�^��Ԃ�
				return keys;
			}
		});

		dialog.ctrlBranch.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref4.refreshName();
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

		// �����ړI
		ref5 = new REFDialogCtrl(dialog.ctrlRemittancePurpose, dialog.getParentFrame());
		ref5.setTargetServlet("ApMktMstServlet");
		ref5.setTitleID("C00947");
		ref5.setColumnLabels("C00793", "C02037", null);
		ref5.setShowsMsgOnError(false);
		ref5.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// ��s�R�[�h�̐ݒ�
				keys.put("kind", "ApMktMst");
				// �f�[�^��Ԃ�
				return keys;
			}
		});

		dialog.ctrlRemittancePurpose.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref5.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlRemittancePurpose.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlRemittancePurpose.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlRemittancePurpose.getValue());
					dialog.ctrlRemittancePurpose.getField().clearOldText();
					dialog.ctrlRemittancePurpose.getField().requestFocus();
					return false;

				}
				return true;
			}
		});

		// �U���U�o��s
		ref6 = new REFDialogCtrl(dialog.ctrlTransferDrawingBank, dialog.getParentFrame());
		ref6.setColumnLabels("C00857", "C00124", "C00794");
		ref6.setTargetServlet("MP0030BankAccountMasterServlet");
		ref6.setTitleID("C02322");
		ref6.setShowsMsgOnError(false);

		dialog.ctrlTransferDrawingBank.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref6.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlTransferDrawingBank.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlTransferDrawingBank.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlTransferDrawingBank.getValue());
					dialog.ctrlTransferDrawingBank.getField().clearOldText();
					dialog.ctrlTransferDrawingBank.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		ref6.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "BankAccount");

				return keys;
			}

			// ��s�R�[�h�ɂċ�s�}�X�^�ɑ���
			public void goodCodeInputted() {
				try {
					addSendValues("flag", "getrefitems");
					addSendValues("kaiCode", getLoginUserCompanyCode());
					addSendValues("cbkCbkCode", dialog.ctrlTransferDrawingBank.getField().getText());

					// �T�[�o���̃G���[�ꍇ

					if (!request("MP0030BankAccountMasterServlet")) {
						errorHandler(dialog);

					}

					List result = getResultList().get(0);
					if (result != null) {
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

						dialog.ctrlTransferDrawingBank.getNotice().setEditable(true);
						dialog.ctrlTransferDrawingBank.getNotice().setText(text1);
						dialog.ctrlTransferDrawingBank.getNotice().setEditable(false);

						dialog.txtDepositTypeAccountNumber.setEditable(true);
						dialog.txtDepositTypeAccountNumber.setText(text2);
						dialog.txtDepositTypeAccountNumber.setEditable(false);
					}
				} catch (IOException e) {
					errorHandler(dialog, e, "E00009");
				}
			}

			// ��s�R�[�h�ɂċ�s�}�X�^�ɑ��݂��Ȃ�
			public void badCodeInputted() {
				dialog.ctrlTransferDrawingBank.getNotice().setEditable(true);
				dialog.ctrlTransferDrawingBank.getNotice().setText("");
				dialog.ctrlTransferDrawingBank.getNotice().setEditable(false);

				dialog.txtDepositTypeAccountNumber.setEditable(true);
				dialog.txtDepositTypeAccountNumber.setText("");
				dialog.txtDepositTypeAccountNumber.setEditable(false);
			}

			// ��s�R�[�h���N���A
			public void textCleared() {
				badCodeInputted();
			}
		});

	}

	/**
	 * �\��
	 */
	void show() {
		// ��ʂ�\������
		dialog.setVisible(true);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map �Z�b�g����l
	 */

	void setValues(Map map) {
		String selectValue;

		// �����R�[�h�̐ݒ�
		dialog.ctrlCustomer.setValue((String) map.get("triCode"));
		// �����R�[�h�̐ݒ�
		dialog.ctrlCustomerConditionCode.setValue((String) map.get("tjkCode"));
		// �U���萔���敪�̐ݒ�
		selectValue = (String) map.get("furiTesuKen");
		if ("1".equals(selectValue)) {
			dialog.rdoMyCompanyPay.setSelected(true);
			dialog.rdoCustomerPay.setSelected(false);
		} else {
			dialog.rdoMyCompanyPay.setSelected(false);
			dialog.rdoCustomerPay.setSelected(true);
		}
		// �x���������ߌ㌎�̐ݒ�
		dialog.numPaymentTermsCutoffDate.setValue(map.get("sjcDate"));
		// �x���������ߓ��̐ݒ�
		dialog.numPaymentTermsAfterCutoffMonth.setValue(map.get("sjrMon"));
		// �x�������x�����̐ݒ�
		dialog.numPaymentTermsCashInDate.setValue(map.get("sjpDate"));
		// �x�����@�̐ݒ�
		selectValue = (String) map.get("sihaKbn");
		if ("00".equals(selectValue)) {
			dialog.rdoTemporary.setSelected(true);
			dialog.rdoOntime.setSelected(false);
		} else {
			dialog.rdoTemporary.setSelected(false);
			dialog.rdoOntime.setSelected(true);
		}
		// �x�����@�̐ݒ�
		dialog.ctrlPaymentMethod.setValue((String) map.get("sihaHouCode"));
		// �U���U�o��s�������ނ̐ݒ�
		dialog.ctrlTransferDrawingBank.setValue((String) map.get("furiCbkCode"));
		// ��s�R�[�h�̐ݒ�
		dialog.ctrlBank.setValue((String) map.get("bnkCode"));
		// �x�X�R�[�h�̐ݒ�
		dialog.ctrlBranch.getField().setEditable(true);
		dialog.ctrlBranch.getButton().setEnabled(true);
		dialog.ctrlBranch.setValue((String) map.get("bnkStnCode"));
		// �a����ڂ̐ݒ�
		selectValue = (String) map.get("yknKbn");
		if ("1".equals(selectValue)) {
			dialog.rdoNormally.setSelected(true);
			dialog.rdoInterim.setSelected(false);
			dialog.rdoForeignCurrency.setSelected(false);
			dialog.rdoSavings.setSelected(false);
		} else if ("2".equals(selectValue)) {
			dialog.rdoNormally.setSelected(false);
			dialog.rdoInterim.setSelected(true);
			dialog.rdoForeignCurrency.setSelected(false);
			dialog.rdoSavings.setSelected(false);
		} else if ("3".equals(selectValue)) {
			dialog.rdoNormally.setSelected(false);
			dialog.rdoInterim.setSelected(false);
			dialog.rdoForeignCurrency.setSelected(true);
			dialog.rdoSavings.setSelected(false);
		} else {
			dialog.rdoNormally.setSelected(false);
			dialog.rdoInterim.setSelected(false);
			dialog.rdoForeignCurrency.setSelected(false);
			dialog.rdoSavings.setSelected(true);
		}
		// �����ԍ��̐ݒ�
		dialog.ctrlAccountNumber.setValue((String) map.get("yknNo"));
		// �������`�̐ݒ�
		dialog.ctrlEnglishBankAddress.setValue((String) map.get("yknName"));
		// �������`�J�i�̐ݒ�
		dialog.ctrlAccountName.setValue((String) map.get("yknKana"));
		// �����ړI���̐ݒ�
		dialog.ctrlRemittancePurpose.setValue((String) map.get("gsMktCode"));
		// ��d���x�X���̂̐ݒ�
		dialog.ctrlEnglishBankName.setValue((String) map.get("gsBnkName"));
		// ��d����s���̂̐ݒ�
		dialog.ctrlEnglishBranchName.setValue((String) map.get("gsStnName"));
		// �萔���敪�̐ݒ�
		selectValue = (String) map.get("gsTesuKbn");
		if ("1".equals(selectValue)) {
			dialog.rdoRemittanceRecipient.setSelected(true);
			dialog.rdoRemittanceMaker.setSelected(false);
		} else {
			dialog.rdoRemittanceRecipient.setSelected(false);
			dialog.rdoRemittanceMaker.setSelected(true);
		}
		// �x����s���̂̐ݒ�
		dialog.ctrlPaymentBankName.setValue((String) map.get("sihaBnkName"));
		// �x���x�X���̂̐ݒ�
		dialog.ctrlPaymentBranchName.setValue((String) map.get("sihaStnName"));
		// �x���x�X���̂̐ݒ�
		dialog.ctrlPaymentBankAddress.setValue((String) map.get("sihaBnkAdr"));
		// �o�R��s���̂̐ݒ�
		dialog.ctrlViaBankName.setValue((String) map.get("keiyuBnkName"));
		// �o�R�x�X���̂̐ݒ�
		dialog.ctrlViaBranchName.setValue((String) map.get("keiyuStnName"));
		// �o�R��s�Z���̐ݒ�
		dialog.ctrlViaBankAddress.setValue((String) map.get("keiyuBnkAdr"));
		// ���l�Z���̐ݒ�
		dialog.ctrlRecipientAddress.setValue((String) map.get("ukeAdr"));
		// �J�n�N�����̐ݒ�
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// �J�n�N�����̐ݒ�
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		ref1.refreshName();
		ref2.refreshName();
		ref3.refreshName();
		ref4.refreshName();
		ref5.refreshName();
		ref6.refreshName();
		// �ҏW���[�h�̂Ƃ��͓E�v�R�[�h�A�E�v�敪�Ƃ��ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));
		dialog.ctrlCustomer.setEnabled(true);
		dialog.ctrlCustomer.setEditable(!isUpdate);
		dialog.ctrlCustomer.setButtonEnabled(!isUpdate);
		dialog.ctrlCustomerConditionCode.setEnabled(true);
		dialog.ctrlCustomerConditionCode.setEditable(!isUpdate);
		if (isUpdate) {
			dialog.ctrlPaymentMethod.getField().requestFocus();
		}

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
		try {// �����R�[�h
			if (Util.isNullOrEmpty(dialog.ctrlCustomer.getValue())) {
				showMessage(dialog, "I00002", "C00786");
				dialog.ctrlCustomer.requestTextFocus();
				return false;
			}

			// ���������R�[�h

			if (Util.isNullOrEmpty(dialog.ctrlCustomerConditionCode.getValue())) {
				showMessage(dialog, "I00002", "C00788");
				dialog.ctrlCustomerConditionCode.requestFocus();
				return false;
			}

			if (!isUpdate && checkCode(dialog.ctrlCustomer.getValue(), dialog.ctrlCustomerConditionCode.getValue())) {
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlCustomerConditionCode.requestFocus();
				return false;
			}

			// �x�����@�`�F�b�N
			if (Util.isNullOrEmpty(dialog.ctrlPaymentMethod.getValue())) {
				showMessage(dialog, "I00002", "C00233");
				dialog.ctrlPaymentMethod.requestTextFocus();
				return false;
			}
			// �����߃`�F�b�N
			if (Util.isNullOrEmpty(dialog.numPaymentTermsCutoffDate.getValue())) {
				showMessage(dialog, "I00002", "C01265");
				dialog.numPaymentTermsCutoffDate.requestFocus();
				return false;
			}

			String strNum2 = (String) dialog.numPaymentTermsCutoffDate.getValue();
			// �l�����������ߓ�����0�`3199�ȊO
			if (!Util.isNullOrEmpty(strNum2)
				&& ((Integer.parseInt(strNum2) < 1 || 31 < Integer.parseInt(strNum2)) && Integer.parseInt(strNum2) != 99)) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "W00065", "1", "31, 99");
				dialog.numPaymentTermsCutoffDate.requestFocus();
				// �G���[��Ԃ�
				return false;
			}

			// ���㌎�`�F�b�N
			if (Util.isNullOrEmpty(dialog.numPaymentTermsAfterCutoffMonth.getValue())) {
				showMessage(dialog, "I00002", "C00979");
				dialog.numPaymentTermsAfterCutoffMonth.requestFocus();
				return false;
			}

			String strNum3 = (String) dialog.numPaymentTermsAfterCutoffMonth.getValue();
			if (!Util.isNullOrEmpty(strNum3) && (Integer.parseInt(strNum3) < 0 || 99 < Integer.parseInt(strNum3))) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "W00065", "0", "99");
				dialog.numPaymentTermsAfterCutoffMonth.requestFocus();
				// �G���[��Ԃ�
				return false;
			}

			// �������`�F�b�N
			if (Util.isNullOrEmpty(dialog.numPaymentTermsCashInDate.getValue())) {
				showMessage(dialog, "I00002", "C00448");
				dialog.numPaymentTermsCashInDate.requestFocus();
				return false;
			}

			String strNum4 = (String) dialog.numPaymentTermsCashInDate.getValue();
			// �l�����������ߓ�����0�`3199�ȊO
			if (!Util.isNullOrEmpty(strNum4)
				&& ((Integer.parseInt(strNum4) < 1 || 31 < Integer.parseInt(strNum4)) && Integer.parseInt(strNum4) != 99)) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "W00065", "1", "31, 99");
				dialog.numPaymentTermsCashInDate.requestFocus();
				// �G���[��Ԃ�
				return false;
			}

			// �U���U�o��s�`�F�b�N
			// �����R�[�h�`�F�b�N
			boolean bol;

			bol = checkNaiCode(dialog.ctrlPaymentMethod.getValue());

			// �����R�[�h��12,13,18,19�̏ꍇ
			if (bol) {
				// �U���U�o��s�`�F�b�N
				if (Util.isNullOrEmpty(dialog.ctrlTransferDrawingBank.getValue())) {
					showMessage(dialog, "I00002", "C00946");
					dialog.ctrlTransferDrawingBank.getField().requestFocus();
					return false;
				}
			}

			// �� �s�`�F�b�N
			// �����R�[�h�`�F�b�N
			// boolean bol = checkNaiCode(dialog.ctrlPaymentMethod.getValue());
			// �����R�[�h��12,13,18,19�̏ꍇ
			if (bol) {
				// �� �s�`�F�b�N
				if (Util.isNullOrEmpty(dialog.ctrlBank.getValue())) {
					showMessage(dialog, "I00002", "C00124");
					dialog.ctrlBank.requestTextFocus();
					return false;
				}
			}

			// �x �X�P�`�F�b�N
			// �����R�[�h�`�F�b�N
			// boolean bol = checkNaiCode(dialog.ctrlPaymentMethod.getValue());
			// �����R�[�h��12,13,18,19�̏ꍇ
			if (bol) {
				// �x �X�P�`�F�b�N
				if (Util.isNullOrEmpty(dialog.ctrlBranch.getValue())) {
					showMessage(dialog, "I00002", "C00222");
					dialog.ctrlBranch.getField().requestFocus();
					return false;
				}
			}

			// �����ԍ��`�F�b�N
			// �����R�[�h�`�F�b�N
			// boolean bol = checkNaiCode(dialog.ctrlPaymentMethod.getValue());
			// �����R�[�h��12,13,18,19�̏ꍇ
			if (bol) {
				// �����ԍ��`�F�b�N
				if (Util.isNullOrEmpty(dialog.ctrlAccountNumber.getValue())) {
					showMessage(dialog, "I00002", "C00794");
					dialog.ctrlAccountNumber.requestFocus();
					return false;
				}
			}

			// �������`�J�i�`�F�b�N
			// �����R�[�h�`�F�b�N
			// boolean bol = checkNaiCode(dialog.ctrlPaymentMethod.getValue());
			// �����R�[�h��12,13,18,19�̏ꍇ
			if (bol) {
				// �������`�J�i�`�F�b�N
				if (Util.isNullOrEmpty(dialog.ctrlAccountName.getValue())) {
					showMessage(dialog, "I00002", "C00168");
					dialog.ctrlAccountName.requestFocus();
					return false;
				}
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
		// �����R�[�h�̐ݒ�
		String selectedValue;
		map.put("triCode", dialog.ctrlCustomer.getField().getText());
		// ���������R�[�h�̐ݒ�
		map.put("tjkCode", dialog.ctrlCustomerConditionCode.getField().getText());

		// �U���萔���敪�̐ݒ�
		if (dialog.rdoMyCompanyPay.isSelected()) {
			selectedValue = "1";
		} else {
			selectedValue = "2";
		}
		map.put("furiTesuKen", selectedValue);
		// �x���������ߓ��̐ݒ�
		map.put("sjcDate", dialog.numPaymentTermsCutoffDate.getText());
		// �x���������ߌ㌎�̐ݒ�
		map.put("sjrMon", dialog.numPaymentTermsAfterCutoffMonth.getText());
		// �x�������x�����̐ݒ�
		map.put("sjpDate", dialog.numPaymentTermsCashInDate.getText());
		// �x���敪�̐ݒ�
		if (dialog.rdoTemporary.isSelected()) {
			selectedValue = "00";
		} else {
			selectedValue = "10";
		}
		map.put("sihaKbn", selectedValue);
		// �x�����@�̐ݒ�
		map.put("sihaHouCode", dialog.ctrlPaymentMethod.getField().getText());
		// �U���U�o��s�������ނ̐ݒ�
		map.put("furiCbkCode", dialog.ctrlTransferDrawingBank.getField().getText());
		// ��s�R�[�h�̐ݒ�
		map.put("bnkCode", dialog.ctrlBank.getField().getText());
		// ��s�R�[�h�̐ݒ�
		map.put("bnkStnCode", dialog.ctrlBranch.getField().getText());
		// �a����ڂ̐ݒ�
		if (dialog.rdoNormally.isSelected()) {
			selectedValue = "1";
		} else if (dialog.rdoInterim.isSelected()) {
			selectedValue = "2";
		} else if (dialog.rdoForeignCurrency.isSelected()) {
			selectedValue = "3";
		} else {
			selectedValue = "4";
		}
		map.put("yknKbn", selectedValue);
		// �����ԍ��̐ݒ�
		map.put("yknNo", dialog.ctrlAccountNumber.getField().getText());
		// �������`�̐ݒ�
		map.put("yknName", dialog.ctrlEnglishBankAddress.getField().getText());
		// �������`�J�i�̐ݒ�
		map.put("yknKana", dialog.ctrlAccountName.getField().getText());
		// �����ړI���̐ݒ�
		map.put("gsMktCode", dialog.ctrlRemittancePurpose.getField().getText());
		// ��d���x�X���̂̐ݒ�
		map.put("gsStnName", dialog.ctrlEnglishBranchName.getField().getText());
		// ��d����s���̂̐ݒ�
		map.put("gsBnkName", dialog.ctrlEnglishBankName.getField().getText());
		// �萔���敪�̐ݒ�
		if (dialog.rdoRemittanceRecipient.isSelected()) {
			selectedValue = "1";
		} else {
			selectedValue = "2";
		}
		map.put("gsTesuKbn", selectedValue);
		// �x����s���̂̐ݒ�
		map.put("sihaBnkName", dialog.ctrlPaymentBankName.getField().getText());
		// �x���x�X���̂̐ݒ�
		map.put("sihaStnName", dialog.ctrlPaymentBranchName.getField().getText());
		// �x����s�Z���̐ݒ�
		map.put("sihaBnkAdr", dialog.ctrlPaymentBankAddress.getField().getText());
		// �o�R��s���̂̐ݒ�
		map.put("keiyuBnkName", dialog.ctrlViaBankName.getField().getText());
		// �o�R��s���̂̐ݒ�
		map.put("keiyuStnName", dialog.ctrlViaBranchName.getField().getText());
		// �o�R��s���̂̐ݒ�
		map.put("keiyuBnkAdr", dialog.ctrlViaBankAddress.getField().getText());
		// ���l�Z���̐ݒ�
		map.put("ukeAdr", dialog.ctrlRecipientAddress.getField().getText());
		// �J�n�N�����̐ݒ�
		map.put("strDate", dialog.dtBeginDate.getValue());
		// �I���N�����̐ݒ�
		map.put("endDate", dialog.dtEndDate.getValue());
		// ���ʂ�Ԃ�
		return map;
	}

	// �x�������R�[�h�`�F�b�N
	boolean checkNaiCode(String hohCode) throws IOException {
		// ������ʂ̐ݒ�
		addSendValues("flag", "checkNaiCode");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// �x���R�[�h�̐ݒ�
		addSendValues("hohHohCode", hohCode);

		if (!request(getServletName())) {
			errorHandler(dialog);
			return false;
		}
		Map data = super.getResult();

		if ("12".equals(String.valueOf(data.get("hOH_NAI_CODE")))) {
			return true;
		} else if ("13".equals(String.valueOf(data.get("hOH_NAI_CODE")))) {
			return true;
		} else if ("18".equals(String.valueOf(data.get("hOH_NAI_CODE")))) {
			return true;
		} else if ("19".equals(String.valueOf(data.get("hOH_NAI_CODE")))) {
			return true;
		} else {
			return false;
		}
	}

	boolean checkCode(String triCode, String tjkCode) throws IOException {
		// �ȖڃR�[�h������
		if (Util.isNullOrEmpty(triCode) && Util.isNullOrEmpty(tjkCode)) {
			return false;
		}
		// ������ʂ̐ݒ�
		addSendValues("flag", "checkcode");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// �����R�[�h�̐ݒ�
		addSendValues("triCode", triCode);
		// ���������R�[�h�̐ݒ�
		addSendValues("tjkCode", tjkCode);

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