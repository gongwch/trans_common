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
 * �����}�X�^�_�C�A���O �R���g���[��
 * 
 * @author mayongliang
 */
public class MG0150EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** �����}�X�^�_�C�A���O */
	protected MG0150EditDisplayDialog dialog;

	/**
	 * �ڑ���Servlet����
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "MG0150CustomerMasterServlet";
	}

	/** �����敪 */
	protected boolean isUpdate = false;

	protected boolean isSpot = false;

	/** ����R�[�h */
	public String triCode = null;

	protected REFDialogCtrl refSummaryCode;

	private REFDialogCtrl refInputBankAccountCode;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	MG0150EditDisplayDialogCtrl(Frame parent, String titleid) {

		// �����}�X�^�_�C�A���O�̏�����
		dialog = new MG0150EditDisplayDialog(parent, true, this, titleid);

		dialog.rdoMyCompanyPay.setSelected(true);
		dialog.rdoNotVendor.setSelected(true);
		dialog.rdoNotCustomer.setSelected(true);
		dialog.rdoNormally.setSelected(true);

		init();

		// �_�C�A���O������Adialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.isSettle = false;
				dialog.setVisible(false);
			}
		});

		dialog.ctrlCustomerCode.getField().requestFocus();
		dialog.dtBeginDate.setValue(minInputDate);
		dialog.dtEndDate.setValue(maxInputDate);

		changedCustomerSelect(false);
	}

	protected void init() {

		refSummaryCode = new REFDialogCtrl(dialog.ctrlSummaryCode, dialog.getParentFrame());
		refSummaryCode.setTargetServlet(getServletName());
		refSummaryCode.setTitleID("C02326");
		refSummaryCode.setColumnLabels("C00786", "C00787", "C00836");
		refSummaryCode.setShowsMsgOnError(false);
		refSummaryCode.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// ��ЃR�[�h�̐ݒ�
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "Customer");
				return keys;
			}

		});

		dialog.ctrlSummaryCode.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refSummaryCode.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlSummaryCode.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlSummaryCode.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlSummaryCode.getValue());
					dialog.ctrlSummaryCode.getField().clearOldText();
					dialog.ctrlSummaryCode.getField().requestFocus();
					return false;

				}
				return true;
			}
		});

		// ������s�������ނ̐ݒ�
		refInputBankAccountCode = new REFDialogCtrl(dialog.ctrlInputBankAccountCode, dialog.getParentFrame());
		refInputBankAccountCode.setColumnLabels("C00857", "C00124", "C00794");
		refInputBankAccountCode.setTargetServlet("MP0030BankAccountMasterServlet");
		refInputBankAccountCode.setTitleID("C02322");
		refInputBankAccountCode.setShowsMsgOnError(false);

		dialog.ctrlInputBankAccountCode.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refInputBankAccountCode.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlInputBankAccountCode.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlInputBankAccountCode.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlInputBankAccountCode.getValue());
					dialog.ctrlInputBankAccountCode.getField().clearOldText();
					dialog.ctrlInputBankAccountCode.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		refInputBankAccountCode.setREFListener(new REFAdapter() {

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
					addSendValues("cbkCbkCode", dialog.ctrlInputBankAccountCode.getField().getText());

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

						dialog.ctrlInputBankAccountCode.getNotice().setEditable(true);
						dialog.ctrlInputBankAccountCode.getNotice().setText(text1);
						dialog.ctrlInputBankAccountCode.getNotice().setEditable(false);

						dialog.txtDepositTypeAccountNumber.setText(text2);
					}
				} catch (IOException ex) {
					errorHandler(dialog, ex, "E00009");
				}
			}

			// ��s�R�[�h�ɂċ�s�}�X�^�ɑ��݂��Ȃ�
			public void badCodeInputted() {
				dialog.ctrlInputBankAccountCode.getNotice().setEditable(true);
				dialog.ctrlInputBankAccountCode.getNotice().setText("");
				dialog.ctrlInputBankAccountCode.getNotice().setEditable(false);

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
		dialog.setVisible(true);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map �Z�b�g����l
	 */

	void setValues(Map map) {
		boolean boo;
		// �����R�[�h�̐ݒ�
		dialog.ctrlCustomerCode.setValue((String) map.get("triCode"));
		// ����於�̂̐ݒ�
		dialog.ctrlCustomerName.setValue((String) map.get("triName"));
		// ����旪�̂̐ݒ�
		dialog.ctrlCustomerAbbreviationName.setValue((String) map.get("triName_S"));
		// ����挟�����̂̐ݒ�
		dialog.ctrlCustomerNameForSearch.setValue((String) map.get("triName_K"));
		// �X�֔ԍ��̐ݒ�
		dialog.ctrlPostcode.setValue((String) map.get("zip"));
		// �Z���J�i�̐ݒ�
		dialog.ctrlAddressKana.setValue((String) map.get("jyuKana"));
		// �Z���P�̐ݒ�
		dialog.ctrlAddress1.setValue((String) map.get("jyu1"));
		// �Z���Q�̐ݒ�
		dialog.ctrlAddress2.setValue((String) map.get("jyu2"));
		// �d�b�ԍ��̐ݒ�
		dialog.ctrlTelephoneNumber.setValue((String) map.get("tel"));
		// FAX�ԍ��̐ݒ�
		dialog.ctrlFaxNumber.setValue((String) map.get("fax"));
		// �W�v�����R�[�h�̐ݒ�
		dialog.ctrlSummaryCode.setValue((String) map.get("sumCode"));
		// �d����敪�̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("siireKbn")));
		dialog.rdoNotVendor.setSelected(!boo);
		dialog.rdoVendor.setSelected(boo);
		// ���Ӑ�敪�̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("tokuiKbn")));
		dialog.rdoNotCustomer.setSelected(!boo);
		dialog.rdoCustomer.setSelected(boo);
		changedCustomerSelect(boo);

		// �����������ߓ��̐ݒ�
		dialog.numCashInConditionCutoffDay.setValue(map.get("njCDate"));
		// �����������ߌ㌎�̐ݒ�
		dialog.numCashInConditionAfterCutoffMonth.setValue(map.get("njRMon"));
		// ���������������̐ݒ�
		dialog.numCashInConditionCashInDay.setValue(map.get("njPDate"));
		// ������s�������ނ̐ݒ�
		dialog.ctrlInputBankAccountCode.setValue((String) map.get("nknCbkCode"));
		// ����`�ԋ敪�̐ݒ�
		dialog.ctrlOfficeName.setValue((String) map.get("jigName"));
		// �U���˗��l���̐ݒ�
		dialog.ctrlTransferRequesterKanaName.setValue((String) map.get("iraiName"));
		// �����萔���敪�̐ݒ�
		String tesKbn = (String) map.get("nyuTesuKbn");

		if ("1".equals(tesKbn)) {
			dialog.rdoMyCompanyPay.setSelected(true);
		} else {
			dialog.rdoCustomerPay.setSelected(true);
		}

		// �J�n�N�����̐ݒ�
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// �I���N�����̐ݒ�
		dialog.dtEndDate.setValue((Date) map.get("endDate"));
		// �ҏW���[�h�̂Ƃ��͓E�v�R�[�h�A�E�v�敪�Ƃ��ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));

		// SPOT�{�o�^
		isSpot = "spot".equals(map.get("flag"));
		dialog.ctrlCustomerCode.setEnabled(true);
		dialog.ctrlCustomerCode.setEditable(!isUpdate);

		// �����R�[�h
		if (isUpdate) {
			// requestFocus
			dialog.ctrlCustomerName.getField().requestFocus();
			if ("00".equals(map.get("triKbn"))) {
				dialog.rdoNormally.setSelected(true);
				dialog.rdoSpot.setSelected(false);
				dialog.rdoSpot.setEnabled(false);
			} else {
				dialog.rdoNormally.setSelected(false);
				dialog.rdoNormally.setEnabled(false);
				dialog.rdoSpot.setSelected(true);
				dialog.rdoSpot.setEnabled(true);
			}
		}

		refSummaryCode.refreshName();
		refInputBankAccountCode.refreshName();
	}

	/**
	 * ����
	 */
	void disposeDialog() {
		try {

			// �߂�{�^������
			if (!dialog.isSettle) {
				// ��ʂ�߂�
				dialog.setVisible(false);
				return;
			}

			// �m��{�^������ �`�F�b�NOK�Ȃ����
			if (checkDialog()) {
				dialog.setVisible(false);
			}
		} catch (IOException ex) {
			errorHandler(dialog, ex, "E00009");
		}
	}

	protected boolean checkDialog() throws IOException {

		// �����R�[�h
		if (dialog.ctrlCustomerCode.isEmpty()) {
			showMessage(dialog, "I00002", "C00786");
			dialog.ctrlCustomerCode.requestFocus();
			return false;
		}

		if (!isSpot && !isUpdate && checkCode(dialog.ctrlCustomerCode.getValue())) {
			showMessage(dialog, "W00005", "C00174");
			dialog.ctrlCustomerCode.requestFocus();
			return false;
		}

		// ����於��
		if (dialog.ctrlCustomerName.isEmpty()) {
			showMessage(dialog, "I00002", "C00830");
			dialog.ctrlCustomerName.requestFocus();
			return false;
		}

		// ����旪�̃`�F�b�N
		if (dialog.ctrlCustomerAbbreviationName.isEmpty()) {
			showMessage(dialog, "I00002", "C00787");
			dialog.ctrlCustomerAbbreviationName.requestFocus();
			return false;
		}

		// ����挟�����̃`�F�b�N
		if (dialog.ctrlCustomerNameForSearch.isEmpty()) {
			showMessage(dialog, "I00002", "C00836");
			dialog.ctrlCustomerNameForSearch.requestFocus();
			return false;
		}

		// ���Ӑ悪�I������Ă���ꍇ�̂݃`�F�b�N
		if (dialog.rdoCustomer.isSelected()) {

			// ������s�������ރ`�F�b�N
			if (dialog.ctrlInputBankAccountCode.isEmpty()) {
				showMessage(dialog, "I00002", "C00647");
				dialog.ctrlInputBankAccountCode.requestTextFocus();
				return false;
			}

			// �����������ߓ��`�F�b�N
			if (dialog.numCashInConditionCutoffDay.isEmpty()) {
				showMessage(dialog, "I00002", "C00868");
				dialog.numCashInConditionCutoffDay.requestFocus();
				return false;
			}

			int cutoffDay = dialog.numCashInConditionCutoffDay.getInt();

			// �������� ���ߓ� 0�`31,99
			if (cutoffDay != 99 && (cutoffDay < 1 || 31 < cutoffDay)) {
				showMessage(dialog, "W00065", "1", "31, 99");
				dialog.numCashInConditionCutoffDay.requestFocus();
				return false;
			}

			// �������� ���ߌ㌎�`�F�b�N
			if (dialog.numCashInConditionAfterCutoffMonth.isEmpty()) {
				showMessage(dialog, "I00002", "C00869");
				dialog.numCashInConditionAfterCutoffMonth.requestFocus();
				return false;
			}

			int cutoffMonth = dialog.numCashInConditionAfterCutoffMonth.getInt();

			// �������� ���ߌ㌎ 0�`99
			if (cutoffMonth < 0 || 99 < cutoffMonth) {
				showMessage(dialog, "W00065", "0", "99");
				dialog.numCashInConditionAfterCutoffMonth.requestFocus();
				return false;
			}

			// �������� �������`�F�b�N
			if (dialog.numCashInConditionCashInDay.isEmpty()) {
				showMessage(dialog, "I00002", "C00870");
				dialog.numCashInConditionCashInDay.requestFocus();
				return false;
			}

			int cashInDay = dialog.numCashInConditionCashInDay.getInt();

			// �������� ������ 0�`31,99
			if (cashInDay != 99 && (cashInDay < 1 || 31 < cashInDay)) {
				showMessage(dialog, "W00065", "1", "31, 99");
				dialog.numCashInConditionCashInDay.requestFocus();
				return false;
			}
		}

		// �J�n�N����
		if (dialog.dtBeginDate.isEmpty()) {
			showMessage(dialog, "W00034", "C00055");
			dialog.dtBeginDate.getCalendar().requestFocus();
			return false;
		}
		// �I���N����
		if (dialog.dtEndDate.isEmpty()) {
			showMessage(dialog, "W00034", "C00261");
			dialog.dtEndDate.getCalendar().requestFocus();
			return false;
		}

		// �J�n�N���������I���N����
		if (Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue()) == false) {
			showMessage(dialog, "W00035", "");
			dialog.dtBeginDate.getCalendar().requestFocus();
			return false;
		}

		return true;

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
		// �����R�[�h�̐ݒ�
		map.put("triCode", dialog.ctrlCustomerCode.getValue());
		// �ߋ��̎����R�[�h�̐ݒ�
		map.put("oldTriCode", this.triCode);
		// ����於�̂̐ݒ�
		map.put("triName", dialog.ctrlCustomerName.getValue());
		// ����旪�̂̐ݒ�
		map.put("triName_S", dialog.ctrlCustomerAbbreviationName.getValue());
		// ����挟�����̂̐ݒ�
		map.put("triName_K", dialog.ctrlCustomerNameForSearch.getValue());
		// �X�֔ԍ��̐ݒ�
		map.put("zip", dialog.ctrlPostcode.getValue());
		// �Z���J�i�̐ݒ�
		map.put("jyuKana", dialog.ctrlAddressKana.getValue());
		// �Z���J�i�̐ݒ�
		map.put("jyu1", dialog.ctrlAddress1.getValue());
		// �Z���Q�̐ݒ�
		map.put("jyu2", dialog.ctrlAddress2.getValue());
		// �d�b�ԍ��̐ݒ�
		map.put("tel", dialog.ctrlTelephoneNumber.getValue());
		// FAX�ԍ��̐ݒ�
		map.put("fax", dialog.ctrlFaxNumber.getValue());
		// �W�v�����R�[�h�̐ݒ�
		map.put("sumCode", dialog.ctrlSummaryCode.getValue());
		// �d����敪�̐ݒ�
		map.put("siireKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoVendor.isSelected())));
		// ���Ӑ�敪�̐ݒ�
		map.put("tokuiKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoCustomer.isSelected())));
		// �����������ߓ��̐ݒ�
		map.put("njCDate", dialog.numCashInConditionCutoffDay.getValue());
		// �����������ߌ㌎�̐ݒ�
		map.put("njRMon", dialog.numCashInConditionAfterCutoffMonth.getValue());
		// ���������������̐ݒ�
		map.put("njPDate", dialog.numCashInConditionCashInDay.getValue());
		// ������s�������ނ̐ݒ�
		map.put("nknCbkCode", dialog.ctrlInputBankAccountCode.getValue());
		// ���Ə����̂̐ݒ�
		map.put("jigName", dialog.ctrlOfficeName.getValue());
		// �U���˗��l���̐ݒ�
		map.put("iraiName", dialog.ctrlTransferRequesterKanaName.getValue());
		// �����萔���敪�̐ݒ�
		String tesKbn;
		if (dialog.rdoMyCompanyPay.isSelected()) {
			tesKbn = "1";
		} else {
			tesKbn = "2";
		}
		map.put("nyuTesuKbn", tesKbn);
		// ����`�ԋ敪�̐ݒ�
		boolean boo = dialog.rdoNormally.isSelected();
		if (boo) {
			map.put("triKbn", "00");
		} else {
			map.put("triKbn", "01");
		}
		// �J�n�N�����̐ݒ�
		map.put("strDate", dialog.dtBeginDate.getValue());
		// �I���N�����̐ݒ�
		map.put("endDate", dialog.dtEndDate.getValue());
		// ���ʂ�Ԃ�
		return map;
	}

	/**
	 * �����R�[�h�ݒ�
	 * 
	 * @param triCode �����R�[�h
	 */
	public void setTriCode(String triCode) {
		this.triCode = triCode;
	}

	/**
	 * �����R�[�h�`�F�b�N
	 * 
	 * @param triCode_ �����R�[�h
	 * @return true:OK
	 * @throws IOException
	 */
	protected boolean checkCode(String triCode_) throws IOException {

		// �����R�[�h������
		if (Util.isNullOrEmpty(triCode_)) {
			return false;
		}

		// ������ʂ̐ݒ�
		addSendValues("flag", "checkcode");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// �����R�[�h�̐ݒ�
		addSendValues("triCode", triCode_);

		// ���M
		if (!request(getServletName())) {
			errorHandler(dialog);
			return true;
		}

		// ���ʂ��擾
		List result = super.getResultList();

		// ���ʂ�Ԃ�
		return !result.isEmpty();
	}

	/**
	 * ���Ӑ�`�F�b�N���ύX���ꂽ�ۂ̏���
	 * 
	 * @param isSelect true:�I�� false:��I��
	 */
	protected void changedCustomerSelect(boolean isSelect) {
		// �R���|�[�l���g����
		dialog.ctrlInputBankAccountCode.setEnabled(isSelect);
		dialog.ctrlTransferRequesterKanaName.setEnabled(isSelect);
		dialog.numCashInConditionCutoffDay.setEnabled(isSelect);
		dialog.numCashInConditionAfterCutoffMonth.setEnabled(isSelect);
		dialog.numCashInConditionCashInDay.setEnabled(isSelect);
		dialog.rdoCustomerPay.setEnabled(isSelect);
		dialog.rdoMyCompanyPay.setEnabled(isSelect);

		if (!isSelect) {
			dialog.ctrlInputBankAccountCode.clear();
			dialog.txtDepositTypeAccountNumber.setValue("");
			dialog.ctrlTransferRequesterKanaName.setValue("");

			dialog.numCashInConditionCutoffDay.setNumber(0);
			dialog.numCashInConditionAfterCutoffMonth.setNumber(0);
			dialog.numCashInConditionCashInDay.setNumber(0);

			dialog.rdoMyCompanyPay.setSelected(true);
		}
	}
}