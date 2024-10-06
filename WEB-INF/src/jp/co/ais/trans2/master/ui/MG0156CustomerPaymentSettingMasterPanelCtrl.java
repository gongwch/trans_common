package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.bank.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * �����x�������}�X�^(�C�O�p)�̃R���g���[���[
 * 
 * @author AIS
 */
public class MG0156CustomerPaymentSettingMasterPanelCtrl extends TController {

	/** ���샂�[�h�B */
	protected enum Mode {
		/** �V�K */
		NEW,
		/** �C�� */
		MODIFY,
		/** ���� */
		COPY
	}

	/** �w����� */
	protected MG0156CustomerPaymentSettingMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0156CustomerPaymentSettingMasterDialog editView = null;

	/** �U���萔���̃f�t�H���g�����Ƃ��邩 */
	protected boolean isDefaultOther = ClientConfig.isFlagOn("trans.MG0155.commission.default.other");

	/** ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B */
	protected Mode mode = null;

	@Override
	public void start() {

		try {
			// �w����ʐ���
			createMainView();

			// �w����ʂ�����������
			initMainView();

			// �w����ʕ\��
			mainView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return ���f��
	 */
	protected Class getModelClass() {
		return PaymentSettingManager.class;
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0156CustomerPaymentSettingMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		// [�V�K]�{�^������
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [����]�{�^������
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�ҏW]�{�^������
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [����]�{�^������
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�폜]�{�^������
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�G�N�Z��]�{�^������
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * �w����ʂ�����������
	 */
	protected void initMainView() {

		// ���C���{�^���������s�\�ɂ���
		setMainButtonEnabled(false);
	}

	/**
	 * �w�����[�V�K]�{�^������
	 */
	protected void btnNew_Click() {

		try {
			// �ҏW��ʂ𐶐�
			createEditView();

			// �ҏW��ʂ�����������
			initEditView(Mode.NEW, null);

			// �ҏW��ʂ�\��
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �w�����[����]�{�^������
	 */
	protected void btnSearch_Click() {

		try {
			// �J�n�R�[�h�A�I���R�[�h�̓��̓`�F�b�N
			if (!Util.isSmallerThen(mainView.ctrlCustomerReferenceRange.getCodeFrom(),
				mainView.ctrlCustomerReferenceRange.getCodeTo())) {
				showMessage(editView, "I00068");
				mainView.ctrlCustomerReferenceRange.getFieldFrom().requestFocus();
				return;
			}

			// �����������擾
			PaymentSettingSearchCondition condition = getSearchCondition();
			List<PaymentSetting> list = getList(condition);

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// ���������ɊY������f�[�^�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ���������f�[�^���ꗗ�ɕ\������
			for (PaymentSetting paymentSetting : list) {
				mainView.tbl.addRow(getRowData(paymentSetting));
			}

			// ���C���{�^���������\�ɂ��A1�s�ڂ�I������
			setMainButtonEnabled(true);
			mainView.tbl.setRowSelection(0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [�ҏW]�{�^������
	 */
	protected void btnModify_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// �ҏW�Ώۂ̃f�[�^���擾����
			PaymentSetting paymentSetting = getSelectedPaymentSetting();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̃f�[�^���Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, paymentSetting);

			// �ҏW��ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [����]�{�^������
	 */
	protected void btnCopy_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// ���ʑΏۂ̃��[�g���擾����
			PaymentSetting paymentSetting = getSelectedPaymentSetting();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̃f�[�^���Z�b�g����
			createEditView();
			initEditView(Mode.COPY, paymentSetting);

			// ���ʉ�ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [�폜]�{�^������
	 */
	protected void btnDelete_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜�Ώۂ̃f�[�^���擾����
			PaymentSetting paymentSetting = getSelectedPaymentSetting();

			// �폜����
			doDelete(paymentSetting);

			// �폜�����s���ꗗ����폜
			mainView.tbl.removeSelectedRow();

			// �폜��������0���ɂȂ����烁�C���{�^���������s�\�ɂ���
			if (mainView.tbl.getRowCount() == 0) {
				setMainButtonEnabled(false);
			}

			// �������b�Z�[�W
			showMessage(mainView.getParentFrame(), "I00013");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �I���s�̃`�F�b�N
	 * 
	 * @return true:�G���[�Ȃ�
	 */
	protected boolean checkMainView() {

		if (mainView.tbl.getSelectedRow() == -1) {
			// �ꗗ����f�[�^��I�����Ă��������B
			showMessage("I00043");
			return false;
		}

		return true;
	}

	/**
	 * [�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {

		try {
			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �G�N�Z���f�[�^�̎擾
			PaymentSettingSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcelForGlobal", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02325") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0156CustomerPaymentSettingMasterDialog(mainView.getParentFrame());
		editView.lblViaCaution.setText(getWord("C01584")); // ���o�R��s�͎d����s�̔�R�����X��

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();
	}

	/**
	 * �ҏW��ʂ̃C�x���g��`�B
	 */
	protected void addEditViewEvent() {

		// [�m��]�{�^������
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�߂�]�{�^������
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnClose_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// ��s
		editView.ctrlBank.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !editView.ctrlBank.isValueChanged()) {
					return;
				}

				ctrlBankReference_after();
			}

			protected void ctrlBankReference_after() {
				Bank bank = editView.ctrlBank.getEntity();

				editView.ctrlBranch.clear();
				editView.ctrlBranch.setEditable(bank != null);

				if (bank != null) {
					editView.ctrlBranch.getSearchCondition().setBankCode(bank.getCode());
				}
			}
		});
	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param editMode ���샂�[�h�B
	 * @param paymentSetting �f�[�^�B�C���A���ʂ̏ꍇ�͓��Y�f�[�^��ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(Mode editMode, PaymentSetting paymentSetting) {

		this.mode = editMode;

		// �R���{�{�b�N�X�̒��g�\�z

		// �x���敪
		editView.cbxPaymentDataType.addTextValueItem(PaymentDateType.TEMPORARY,
			getWord(PaymentDateType.TEMPORARY.getName()));
		editView.cbxPaymentDataType.addTextValueItem(PaymentDateType.REGULAR,
			getWord(PaymentDateType.REGULAR.getName()));

		// �a�����
		editView.cbxDepositKind.addTextValueItem(DepositKind.ORDINARY, getWord(DepositKind.ORDINARY.getName()));
		editView.cbxDepositKind.addTextValueItem(DepositKind.CURRENT, getWord(DepositKind.CURRENT.getName()));
		editView.cbxDepositKind.addTextValueItem(DepositKind.FOREIGN_CURRENCY,
			getWord(DepositKind.FOREIGN_CURRENCY.getName()));
		editView.cbxDepositKind.addTextValueItem(DepositKind.SAVINGS, getWord(DepositKind.SAVINGS.getName()));
		editView.cbxDepositKind.addTextValueItem(DepositKind.OTHERS, getWord(DepositKind.OTHERS.getName()));

		// �o���N�`���[�W�敪
		editView.cbxBankChargeType.addTextValueItem(BankChargeType.SHA, getShortWord(BankChargeType.SHA.toString()));
		editView.cbxBankChargeType.addTextValueItem(BankChargeType.BEN, getShortWord(BankChargeType.BEN.toString()));
		editView.cbxBankChargeType.addTextValueItem(BankChargeType.OUR, getShortWord(BankChargeType.OUR.toString()));

		// �U���萔��
		editView.cbxCashInFee.addTextValueItem(CashInFeeType.Our, getWord(CashInFeeType.Our.getName()));
		editView.cbxCashInFee.addTextValueItem(CashInFeeType.Other, getWord(CashInFeeType.Other.getName()));

		// �萔���x���敪
		String receiverName = getWord("C00330") + getWord(CommissionPaymentType.Receiver.getName());
		editView.cbxCommissionPaymentType.addTextValueItem(CommissionPaymentType.Receiver, receiverName);
		String payerName = getWord("C00330") + getWord(CommissionPaymentType.Payer.getName());
		editView.cbxCommissionPaymentType.addTextValueItem(CommissionPaymentType.Payer, payerName);

		// �ҏW��ʔ��f
		switch (editMode) {
			case NEW:
				// �V�K
				editView.setTitle(getWord("C01698"));
				editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
				editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());
				editView.ctrlBranch.setEditable(false);
				if (isDefaultOther) {
					editView.cbxCashInFee.setSelectedItemValue(CashInFeeType.Other);
				}
				return;

			case MODIFY:
				editView.setTitle(getWord("C00977"));
				editView.setEditMode();
				editView.ctrlCustomer.setEditable(false);
				editView.txtCustomerConditionCode.setEditable(false);
				break;

			case COPY:
				editView.setTitle(getWord("C01738"));
				break;
		}

		editView.ctrlCustomer.setEntity(paymentSetting.getCustomer());
		editView.txtCustomerConditionCode.setValue(paymentSetting.getCode());
		editView.ctrlPaymentMethod.setEntity(paymentSetting.getPaymentMethod());

		editView.numPaymentDay.setValue(paymentSetting.getCloseDay());
		editView.numPaymentMonth.setValue(paymentSetting.getNextMonth());
		editView.numPaymentPayDay.setValue(paymentSetting.getCashDay());

		editView.ctrlBankAccount.setEntity(paymentSetting.getBankAccount());
		editView.ctrlBank.code.setValue(paymentSetting.getBankCode());
		editView.ctrlBank.name.setValue(paymentSetting.getBankName());
		editView.ctrlBranch.code.setValue(paymentSetting.getBranchCode());
		editView.ctrlBranch.name.setValue(paymentSetting.getBranchName());
		editView.ctrlBranch.getSearchCondition().setBankCode(paymentSetting.getBankCode());

		editView.ctrlRemittanceReference.setEntity(paymentSetting.getRemittancePurpose());

		editView.cbxDepositKind.setSelectedItemValue(paymentSetting.getDepositKind());
		editView.cbxPaymentDataType.setSelectedItemValue(paymentSetting.getPaymentDateType());
		editView.cbxBankChargeType.setSelectedItemValue(paymentSetting.getBankChargeType());

		editView.cbxCashInFee.setSelectedItemValue(paymentSetting.getCashInFeeType());
		editView.cbxCommissionPaymentType.setSelectedItemValue(paymentSetting.getCommissionPaymentType());

		editView.txtAccountNumber.setValue(paymentSetting.getAccountNo());
		editView.txtBankSwiftCode.setValue(paymentSetting.getBankSwiftCode());
		editView.ctrlBankCountry.setEntity(paymentSetting.getBankCountry());
		editView.txtEnBankName.setValue(paymentSetting.getSendBankName());
		editView.txtEnBranchName.setValue(paymentSetting.getSendBranchName());
		editView.txtEnBankAddress.setValue(paymentSetting.getAccountName());
		editView.txtAccountKana.setValue(paymentSetting.getAccountNameKana());

		editView.txtViaBankSwiftCode.setValue(paymentSetting.getViaBankSwiftCode());
		editView.ctrlViaBankCountry.setEntity(paymentSetting.getViaBankCountry());
		editView.txtViaBankName.setValue(paymentSetting.getViaBankName());
		editView.txtViaBranchName.setValue(paymentSetting.getViaBranchName());
		editView.txtViaBankAddress.setValue(paymentSetting.getViaBankAddress());

		editView.ctrlRecipientCountry.setEntity(paymentSetting.getRecipientCountry());
		editView.txtRecipientAddress.setValue(paymentSetting.getReceiverAddress());

		editView.dtBeginDate.setValue(paymentSetting.getDateFrom());
		editView.dtEndDate.setValue(paymentSetting.getDateTo());
	}

	/**
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {
			// ���̓`�F�b�N���s���B
			if (!isInputCorrect()) {
				return;
			}

			// ���͂��ꂽ�f�[�^���擾
			PaymentSetting paymentSetting = getInputtedPaymentSetting();

			switch (mode) {
				case NEW: // �V�K
				case COPY:
					request(getModelClass(), "entry", paymentSetting);

					// �ǉ������ꗗ�ɔ��f����
					mainView.tbl.addRow(getRowData(paymentSetting));

					// ���C���{�^���������\�ɂ���
					setMainButtonEnabled(true);

					break;

				case MODIFY: // �C��
					request(getModelClass(), "modify", paymentSetting);

					// �C�����e���ꗗ�ɔ��f����
					mainView.tbl.modifySelectedRow(getRowData(paymentSetting));

					break;
			}

			// �ҏW��ʂ����
			btnClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// �����R�[�h�������͂̏ꍇ�G���[
		if (editView.ctrlCustomer.isEmpty()) {
			// �����R�[�h����͂��Ă��������B
			showMessage(editView, "I00037", "C00786");
			editView.ctrlCustomer.requestTextFocus();
			return false;
		}

		// ���������R�[�h�������͂̏ꍇ�G���[
		if (editView.txtCustomerConditionCode.isEmpty()) {
			// ���������R�[�h����͂��Ă��������B
			showMessage(editView, "I00037", "C00788");
			editView.txtCustomerConditionCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓�������R�[�h���A���������R�[�h�����݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			PaymentSettingSearchCondition condition = createPaymentSettingSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCustomerCode(editView.ctrlCustomer.getCode());
			condition.setCode(editView.txtCustomerConditionCode.getValue());
			List<PaymentSetting> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				// �w��̃f�[�^�͊��ɑ��݂��܂��B
				showMessage(editView, "I00262");
				editView.txtCustomerConditionCode.requestTextFocus();
				return false;
			}
		}

		// �x�����@�������͂̏ꍇ�G���[
		if (editView.ctrlPaymentMethod.isEmpty()) {
			// �x�����@����͂��Ă��������B
			showMessage(editView, "I00037", "C00233");
			editView.ctrlPaymentMethod.requestTextFocus();
			return false;
		}

		// �`�����߂������͂̏ꍇ�G���[
		if (editView.numPaymentDay.isEmpty()) {
			// ���ߓ�����͂��Ă��������B
			showMessage(editView, "I00037", "C00244");
			editView.numPaymentDay.requestFocus();
			return false;
		}

		// ���t(���l)�`�F�b�N
		int simDay = editView.numPaymentDay.getInt();
		if (simDay <= 0 || (31 < simDay && simDay != 99)) {
			// ���ߓ���1�`31, 99�͈̔͂œ��͂��Ă��������B
			showMessage(editView, "I00247", "C00244", "1", "31, 99");
			editView.numPaymentDay.requestFocus();
			return false;
		}

		// �`�����オ�����͂̏ꍇ�G���[
		if (editView.numPaymentMonth.isEmpty()) {
			// ���ߌ㌎����͂��Ă��������B
			showMessage(editView, "I00037", "C11257");
			editView.numPaymentMonth.requestFocus();
			return false;
		}

		// �`�������������͂̏ꍇ�G���[
		if (editView.numPaymentPayDay.isEmpty()) {
			// �x��������͂��Ă��������B
			showMessage(editView, "I00037", "C01098");
			editView.numPaymentPayDay.requestFocus();
			return false;
		}

		// ���t(���l)�`�F�b�N
		int payDay = editView.numPaymentPayDay.getInt();
		if (payDay <= 0 || (31 < payDay && payDay != 99)) {
			// �x������1�`31, 99�͈̔͂œ��͂��Ă��������B
			showMessage(editView, "I00247", "C01098", "1", "31, 99");
			editView.numPaymentPayDay.requestFocus();
			return false;
		}

		// �U���U�o��s�R�[�h�������͂̏ꍇ�G���[
		// if (editView.ctrlBankAccount.isEmpty()) {
		// // �U���U�o��s�R�[�h����͂��Ă��������B
		// showMessage(editView, "I00037", "C00792");
		// editView.ctrlBankAccount.requestTextFocus();
		// return false;
		// }

		// ��s�R�[�h�������͂̏ꍇ�G���[
		if (editView.ctrlBank.isEmpty()) {
			// ��s�R�[�h����͂��Ă��������B
			showMessage(editView, "I00037", "C00779");
			editView.ctrlBank.requestTextFocus();
			return false;
		}

		// �x�X�R�[�h�������͂̏ꍇ�G���[
		if (editView.ctrlBranch.isEmpty()) {
			// �x�X�R�[�h����͂��Ă��������B
			showMessage(editView, "I00037", "C02055");
			editView.ctrlBranch.requestTextFocus();
			return false;
		}

		// �����ԍ��������͂̏ꍇ�G���[
		PaymentMethod paymentMethod = editView.ctrlPaymentMethod.getEntity();

		if (paymentMethod != null && PaymentKind.isBankAccountPayment(paymentMethod.getPaymentKind())) {
			if (editView.txtAccountNumber.isEmpty()) {
				// �����ԍ�����͂��Ă��������B
				showMessage(editView, "I00037", "C00794");
				editView.txtAccountNumber.requestTextFocus();
				return false;
			}
		}

		// �������`�ł������͂̏ꍇ�G���[
		if (editView.txtAccountKana.isEmpty()) {
			// �������`�ł���͂��Ă��������B
			showMessage(editView, "I00037", "C00168");
			editView.txtAccountKana.requestFocus();
			return false;
		}

		// �J�n�N�����������͂̏ꍇ�G���[
		if (editView.dtBeginDate.isEmpty()) {
			// �J�n�N��������͂��Ă��������B
			showMessage(editView, "I00037", "C00055");
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[
		if (editView.dtEndDate.isEmpty()) {
			// �I���N��������͂��Ă��������B
			showMessage(editView, "I00037", "C00261");
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		// �J�n���t<=�I�����t�ɂ��Ă��������B
		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			// �J�n���t<=�I�����t�ɂ��Ă��������B
			showMessage(editView, "I00090", "C00055", "C00261");
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		return true;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�f�[�^��Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�f�[�^
	 */
	protected PaymentSetting getInputtedPaymentSetting() {
		PaymentSetting paymentSetting = createPaymentSetting();
		paymentSetting.setCompanyCode(getCompany().getCode());
		paymentSetting.setCustomer(editView.ctrlCustomer.getEntity());
		paymentSetting.setCode(editView.txtCustomerConditionCode.getValue());
		paymentSetting.setPaymentMethod(editView.ctrlPaymentMethod.getEntity());
		paymentSetting.setPaymentDateType(getSelectedPaymentDataType());
		paymentSetting.setCloseDay(editView.numPaymentDay.getText());
		paymentSetting.setNextMonth(editView.numPaymentMonth.getText());
		paymentSetting.setCashDay(editView.numPaymentPayDay.getText());
		paymentSetting.setBankAccount(editView.ctrlBankAccount.getEntity());
		paymentSetting.setBankCode(editView.ctrlBank.getCode());
		paymentSetting.setBankName(editView.ctrlBank.getNames());
		paymentSetting.setBranchCode(editView.ctrlBranch.getCode());
		paymentSetting.setBranchName(editView.ctrlBranch.getNames());
		paymentSetting.setRemittancePurpose(editView.ctrlRemittanceReference.getEntity());
		paymentSetting.setDepositKind(getSelectedDepositKind());
		paymentSetting.setAccountNo(editView.txtAccountNumber.getValue());
		paymentSetting.setSendBankName(editView.txtEnBankName.getValue());
		paymentSetting.setSendBranchName(editView.txtEnBranchName.getValue());
		paymentSetting.setAccountName(editView.txtEnBankAddress.getValue());
		paymentSetting.setAccountNameKana(editView.txtAccountKana.getText());
		paymentSetting.setViaBankName(editView.txtViaBankName.getValue());
		paymentSetting.setViaBranchName(editView.txtViaBranchName.getValue());
		paymentSetting.setViaBankAddress(editView.txtViaBankAddress.getValue());
		paymentSetting.setReceiverAddress(editView.txtRecipientAddress.getValue());
		paymentSetting.setDateFrom(editView.dtBeginDate.getValue());
		paymentSetting.setDateTo(editView.dtEndDate.getValue());
		paymentSetting.setBankCountry(editView.ctrlBankCountry.getEntity()); // ��s���R�[�h
		paymentSetting.setBankSwiftCode(editView.txtBankSwiftCode.getValue()); // ��sSWIFT�R�[�h
		paymentSetting.setViaBankCountry(editView.ctrlViaBankCountry.getEntity()); // �o�R��s���R�[�h
		paymentSetting.setViaBankSwiftCode(editView.txtViaBankSwiftCode.getValue()); // �o�R��sSWIFT�R�[�h
		paymentSetting.setRecipientCountry(editView.ctrlRecipientCountry.getEntity()); // ���l���R�[�h
		paymentSetting.setBankChargeType((BankChargeType) editView.cbxBankChargeType.getSelectedItemValue()); // �o���N�`���[�W�敪
		paymentSetting.setCashInFeeType(getSelectedCashInFeeType());
		paymentSetting.setCommissionPaymentType(getSelectedPayFeeType());

		return paymentSetting;
	}

	/**
	 * @return �x�������}�X�^�̌�������
	 */
	protected PaymentSettingSearchCondition createPaymentSettingSearchCondition() {
		return new PaymentSettingSearchCondition();
	}

	/**
	 * �G���e�B�e�B����
	 * 
	 * @return PaymentSetting
	 */
	protected PaymentSetting createPaymentSetting() {
		return new PaymentSetting();
	}

	/**
	 * �I�����ꂽ�U���萔����Ԃ�
	 * 
	 * @return �I�����ꂽ�U���萔��
	 */
	protected CashInFeeType getSelectedCashInFeeType() {
		CashInFeeType selectedIndex = (CashInFeeType) editView.cbxCashInFee.getSelectedItemValue();
		return selectedIndex;
	}

	/**
	 * �I�����ꂽ�x���敪��Ԃ�
	 * 
	 * @return �I�����ꂽ�x���敪
	 */
	protected PaymentDateType getSelectedPaymentDataType() {
		PaymentDateType selectedIndex = (PaymentDateType) editView.cbxPaymentDataType.getSelectedItemValue();
		return selectedIndex;
	}

	/**
	 * �I�����ꂽ�x���萔���敪��Ԃ�
	 * 
	 * @return �I�����ꂽ�x���萔���敪
	 */
	protected CommissionPaymentType getSelectedPayFeeType() {
		CommissionPaymentType selectedIndex = (CommissionPaymentType) editView.cbxCommissionPaymentType
			.getSelectedItemValue();
		return selectedIndex;
	}

	/**
	 * �I�����ꂽ�a����ڂ�Ԃ�
	 * 
	 * @return �I�����ꂽ�a�����
	 */
	protected DepositKind getSelectedDepositKind() {
		DepositKind selectedIndex = (DepositKind) editView.cbxDepositKind.getSelectedItemValue();
		return selectedIndex;
	}

	/**
	 * �ҏW���[�߂�]�{�^������
	 */
	protected void btnClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param bln enabled
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnModify.setEnabled(bln);
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
	}

	/**
	 * ���������ɊY�����郊�X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�����郊�X�g
	 * @throws Exception
	 */
	protected List<PaymentSetting> getList(PaymentSettingSearchCondition condition) throws Exception {
		List<PaymentSetting> list = (List<PaymentSetting>) request(getModelClass(), "get", condition);
		return list;
	}

	/**
	 * �ꗗ�őI������Ă���f�[�^��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���f�[�^
	 * @throws Exception
	 */
	protected PaymentSetting getSelectedPaymentSetting() throws Exception {
		PaymentSettingSearchCondition condition = createPaymentSettingSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCustomerCode((String) mainView.tbl
			.getSelectedRowValueAt(MG0156CustomerPaymentSettingMasterPanel.SC.customerCode));
		condition.setCode((String) mainView.tbl
			.getSelectedRowValueAt(MG0156CustomerPaymentSettingMasterPanel.SC.customerConditionCode));

		List<PaymentSetting> list = getList(condition);

		if (list == null || list.isEmpty()) {
			throw new TException("I00038");
		}

		return list.get(0);
	}

	/**
	 * �w����ʂœ��͂��ꂽ�f�[�^�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected PaymentSettingSearchCondition getSearchCondition() {
		PaymentSettingSearchCondition condition = createPaymentSettingSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCustomerCodeFrom(mainView.ctrlCustomerReferenceRange.getCodeFrom());
		condition.setCustomerCodeTo(mainView.ctrlCustomerReferenceRange.getCodeTo());

		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;
	}

	/**
	 * �x�����������ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param paymentSetting �x���������
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�f�[�^
	 */
	protected List<Object> getRowData(PaymentSetting paymentSetting) {
		List<Object> list = new ArrayList<Object>();
		list.add(paymentSetting);
		list.add(paymentSetting.getCustomer().getCode()); // �����R�[�h
		list.add(paymentSetting.getCustomer().getNames()); // ����旪��
		list.add(paymentSetting.getCode()); // ���������R�[�h
		list.add(paymentSetting.getCloseDay()); // ����
		list.add(paymentSetting.getNextMonth()); // �㌎
		list.add(paymentSetting.getCashDay()); // �x����
		list.add(getWord(paymentSetting.getPaymentDateType().getName())); // �x���敪

		// �x�����@
		if (paymentSetting.getPaymentMethod() != null) {
			list.add(paymentSetting.getPaymentMethod().getName());
		} else {
			list.add("");
		}

		// �U���U�o��s�����R�[�h
		if (paymentSetting.getBankAccount() != null) {
			list.add(paymentSetting.getBankAccount().getCode());
		} else {
			list.add("");
		}

		list.add(paymentSetting.getBankCode()); // ��s�R�[�h
		list.add(paymentSetting.getBankName()); // ��s����
		list.add(paymentSetting.getBranchCode()); // �x�X�R�[�h
		list.add(paymentSetting.getBranchName()); // �x�X����
		list.add(getWord(DepositKind.getDepositKindName(paymentSetting.getDepositKind()))); // �a�����
		list.add(paymentSetting.getBankChargeType() != null ? paymentSetting.getBankChargeType().toString() : ""); // �o���N�`���[�W�敪
		list.add(getWord(CashInFeeType.getCashInFeeTypeName(paymentSetting.getCashInFeeType()))); // �U���萔���敪
		list.add(getWord(CommissionPaymentType.getName(paymentSetting.getCommissionPaymentType()))); // �萔���敪
		list.add(paymentSetting.getAccountNo()); // �����ԍ�
		list.add(paymentSetting.getBankSwiftCode()); // ��sSWIFT�R�[�h
		list.add(paymentSetting.getSendBankName()); // �p����s��
		list.add(paymentSetting.getSendBranchName()); // �p���x�X��

		// ��s���R�[�h
		if (paymentSetting.getBankCountry() != null) {
			list.add(paymentSetting.getBankCountry().getCode());
		} else {
			list.add("");
		}

		list.add(paymentSetting.getAccountName()); // �p����s�Z��
		list.add(paymentSetting.getAccountNameKana()); // �������`�J�i

		if (paymentSetting.getRemittancePurpose() != null) {
			list.add(paymentSetting.getRemittancePurpose().getName()); // �����ړI��
		} else {
			list.add("");
		}

		list.add(paymentSetting.getViaBankSwiftCode()); // �o�R��sSWIFT�R�[�h
		list.add(paymentSetting.getViaBankName()); // �o�R��s����
		list.add(paymentSetting.getViaBranchName()); // �o�R�x�X����

		// �o�R��s���R�[�h
		if (paymentSetting.getViaBankCountry() != null) {
			list.add(paymentSetting.getViaBankCountry().getCode());
		} else {
			list.add("");
		}

		list.add(paymentSetting.getViaBankAddress()); // �o�R��s�Z��

		// ���l���R�[�h
		if (paymentSetting.getRecipientCountry() != null) {
			list.add(paymentSetting.getRecipientCountry().getCode());
		} else {
			list.add("");
		}

		list.add(paymentSetting.getReceiverAddress()); // ���l�Z��

		list.add(DateUtil.toYMDString(paymentSetting.getDateFrom())); // �J�n�N����
		list.add(DateUtil.toYMDString(paymentSetting.getDateTo())); // �I���N����

		return list;
	}

	/**
	 * �w��̍s�f�[�^���폜����
	 * 
	 * @param paymentSetting �f�[�^
	 * @throws Exception
	 */
	protected void doDelete(PaymentSetting paymentSetting) throws Exception {
		request(getModelClass(), "delete", paymentSetting);
	}

}
