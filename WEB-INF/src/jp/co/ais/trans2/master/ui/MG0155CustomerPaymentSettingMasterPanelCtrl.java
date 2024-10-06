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
 * �����x�������}�X�^�̃R���g���[���[
 * 
 * @author AIS
 */
public class MG0155CustomerPaymentSettingMasterPanelCtrl extends TController {

	/** �w����� */
	protected MG0155CustomerPaymentSettingMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0155CustomerPaymentSettingMasterDialog editView = null;

	/** �U���萔���̃f�t�H���g�����Ƃ��邩 */
	protected boolean isDefaultOther = ClientConfig.isFlagOn("trans.MG0155.commission.default.other");

	/** ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B */
	protected Mode mode = null;

	/** ���샂�[�h�B */
	protected enum Mode {
		/** �V�K */
		NEW,
		/** �C�� */
		MODIFY,
		/** ���� */
		COPY
	}

	/**
	 * ��ʐ����y�я�����
	 */
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

	/**
	 * �p�l���擾
	 */
	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0155CustomerPaymentSettingMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		// [�V�K]�{�^������
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [����]�{�^������
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�ҏW]�{�^������
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [����]�{�^������
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�폜]�{�^������
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�G�N�Z��]�{�^������
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
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
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02325") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0155CustomerPaymentSettingMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();
	}

	/**
	 * �ҏW��ʂ̃C�x���g��`�B
	 */
	protected void addEditViewEvent() {

		// [�m��]�{�^������
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�߂�]�{�^������
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
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

				if (bank != null) {
					editView.ctrlBranch.clear();
					editView.ctrlBranch.setEditable(true);
					editView.ctrlBranch.getSearchCondition().setBankCode(bank.getCode());
				} else {
					editView.ctrlBranch.clear();
					editView.ctrlBranch.setEditable(false);
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

		switch (editMode) {

			case NEW:
				// �V�Ks
				editView.setTitle(getWord("C01698"));
				editView.ctrldtBeginDate.setValue(editView.ctrldtBeginDate.getCalendar().getMinimumDate());
				editView.ctrldtEndDate.setValue(editView.ctrldtEndDate.getCalendar().getMaximumDate());
				editView.ctrlBranch.setEditable(false);
				if (isDefaultOther) {
					editView.ctrlcboCashInFee.setSelectedItemValue(CashInFeeType.Other);
				}
				break;

			case MODIFY:
			case COPY:
				// �ҏW
				if (Mode.MODIFY == editMode) {
					editView.setTitle(getWord("C00977"));
					editView.setEditMode();
					editView.ctrlCustomerReference.setEditable(false);
					editView.ctrlCustomerConditionCode.setEditable(false);

					// ����
				} else {
					editView.setTitle(getWord("C01738"));
				}

				editView.ctrlCustomerReference.setEntity(paymentSetting.getCustomer());

				editView.ctrlCustomerConditionCode.setValue(paymentSetting.getCode());

				editView.ctrlPaymentMethodReference.setEntity(paymentSetting.getPaymentMethod());

				editView.ctrlpaymentConditionDay.setValue(paymentSetting.getCloseDay());
				editView.ctrlpaymentConditionMonth.setValue(paymentSetting.getNextMonth());
				editView.ctrlpaymentConditionPayDay.setValue(paymentSetting.getCashDay());

				editView.ctrlBankAccountReference.setEntity(paymentSetting.getBankAccount());

				editView.ctrlBank.code.setValue(paymentSetting.getBankCode());
				editView.ctrlBank.name.setValue(paymentSetting.getBankName());
				editView.ctrlBranch.code.setValue(paymentSetting.getBranchCode());
				editView.ctrlBranch.name.setValue(paymentSetting.getBranchName());
				editView.ctrlBranch.getSearchCondition().setBankCode(paymentSetting.getBankCode());

				editView.ctrlRemittanceReference.setEntity(paymentSetting.getRemittancePurpose());

				editView.ctrlcboPaymentDataType.setSelectedItemValue(paymentSetting.getPaymentDateType());
				editView.ctrlcboDepositKind.setSelectedItemValue(paymentSetting.getDepositKind());
				editView.ctrlcboCashInFee.setSelectedItemValue(paymentSetting.getCashInFeeType());
				editView.ctrlcboCommissionPaymentType.setSelectedItemValue(paymentSetting.getCommissionPaymentType());
				editView.ctrlAccountNumber.setValue(paymentSetting.getAccountNo());
				editView.ctrlEnglishBankName.setValue(paymentSetting.getSendBankName());
				editView.ctrlEnglishBranchName.setValue(paymentSetting.getSendBranchName());
				editView.ctrlEnglishBankAddress.setValue(paymentSetting.getAccountName());
				editView.ctrlAccountKana.setValue(paymentSetting.getAccountNameKana());
				editView.ctrlPaymentBankName.setValue(paymentSetting.getPayBankName());
				editView.ctrlPaymentBranchName.setValue(paymentSetting.getPayBranchName());
				editView.ctrlPaymentBankAddress.setValue(paymentSetting.getPayBankAddress());
				editView.ctrlViaBankName.setValue(paymentSetting.getViaBankName());
				editView.ctrlViaBranchName.setValue(paymentSetting.getViaBranchName());
				editView.ctrlViaBankAddress.setValue(paymentSetting.getViaBankAddress());
				editView.ctrlRecipientAddress.setValue(paymentSetting.getReceiverAddress());
				editView.ctrldtBeginDate.setValue(paymentSetting.getDateFrom());
				editView.ctrldtEndDate.setValue(paymentSetting.getDateTo());
		}
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

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entry", paymentSetting);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(paymentSetting));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", paymentSetting);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(paymentSetting));

			}

			// �ҏW��ʂ����
			btnClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�f�[�^��Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�f�[�^
	 */
	protected PaymentSetting getInputtedPaymentSetting() {

		PaymentSetting paymentSetting = createPaymentSetting();
		paymentSetting.setCompanyCode(getCompany().getCode());
		paymentSetting.setCustomer(editView.ctrlCustomerReference.getEntity());
		paymentSetting.setCode(editView.ctrlCustomerConditionCode.getValue());
		paymentSetting.setPaymentMethod(editView.ctrlPaymentMethodReference.getEntity());
		paymentSetting.setPaymentDateType(getSelectedPaymentDataType());
		paymentSetting.setCloseDay(editView.ctrlpaymentConditionDay.getText());
		paymentSetting.setNextMonth(editView.ctrlpaymentConditionMonth.getText());
		paymentSetting.setCashDay(editView.ctrlpaymentConditionPayDay.getText());
		paymentSetting.setBankAccount(editView.ctrlBankAccountReference.getEntity());
		paymentSetting.setBankCode(editView.ctrlBank.getCode());
		paymentSetting.setBankName(editView.ctrlBank.getNames());
		paymentSetting.setBranchCode(editView.ctrlBranch.getCode());
		paymentSetting.setBranchName(editView.ctrlBranch.getNames());
		paymentSetting.setRemittancePurpose(editView.ctrlRemittanceReference.getEntity());
		paymentSetting.setDepositKind(getSelectedDepositKind());
		paymentSetting.setCashInFeeType(getSelectedCashInFeeType());
		paymentSetting.setCommissionPaymentType(getSelectedPayFeeType());
		paymentSetting.setAccountNo(editView.ctrlAccountNumber.getValue());
		paymentSetting.setSendBankName(editView.ctrlEnglishBankName.getValue());
		paymentSetting.setSendBranchName(editView.ctrlEnglishBranchName.getValue());
		paymentSetting.setAccountName(editView.ctrlEnglishBankAddress.getValue());
		paymentSetting.setAccountNameKana(editView.ctrlAccountKana.getText());
		paymentSetting.setPayBankName(editView.ctrlPaymentBankName.getValue());
		paymentSetting.setPayBranchName(editView.ctrlPaymentBranchName.getValue());
		paymentSetting.setPayBankAddress(editView.ctrlPaymentBankAddress.getValue());
		paymentSetting.setViaBankName(editView.ctrlViaBankName.getValue());
		paymentSetting.setViaBranchName(editView.ctrlViaBranchName.getValue());
		paymentSetting.setViaBankAddress(editView.ctrlViaBankAddress.getValue());
		paymentSetting.setReceiverAddress(editView.ctrlRecipientAddress.getValue());
		paymentSetting.setDateFrom(editView.ctrldtBeginDate.getValue());
		paymentSetting.setDateTo(editView.ctrldtEndDate.getValue());

		return paymentSetting;
	}

	/**
	 * @return �x�������}�X�^�̌�������
	 */
	protected PaymentSettingSearchCondition createPaymentSettingSearchCondition() {
		return new PaymentSettingSearchCondition();
	}

	/**
	 * @return �x�������}�X�^���
	 */
	protected PaymentSetting createPaymentSetting() {
		return new PaymentSetting();
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
	@SuppressWarnings("unchecked")
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
			.getSelectedRowValueAt(MG0155CustomerPaymentSettingMasterPanel.SC.customerCode));
		condition.setCode((String) mainView.tbl
			.getSelectedRowValueAt(MG0155CustomerPaymentSettingMasterPanel.SC.customerConditionCord));

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
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return ���f��
	 */
	protected Class getModelClass() {
		return PaymentSettingManager.class;
	}

	/**
	 * �x�����������ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param paymentSetting �x���������
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�f�[�^
	 */
	protected List<Object> getRowData(PaymentSetting paymentSetting) {

		List<Object> list = new ArrayList<Object>();
		list.add(paymentSetting.getCustomer().getCode()); // �����R�[�h
		list.add(paymentSetting.getCustomer().getNames()); // ����旪��
		list.add(paymentSetting.getCode()); // ���������R�[�h
		list.add(getWord(CashInFeeType.getCashInFeeTypeName(paymentSetting.getCashInFeeType()))); // �U���萔���敪
		list.add(paymentSetting.getCloseDay()); // ����
		list.add(paymentSetting.getNextMonth()); // �㌎
		list.add(paymentSetting.getCashDay()); // �x����
		list.add(getWord(PaymentDateType.getPaymentDateTypeName(paymentSetting.getPaymentDateType()))); // �x���敪

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
		list.add(paymentSetting.getAccountNo()); // �����ԍ�
		list.add(paymentSetting.getAccountNameKana()); // �������`�J�i

		if (paymentSetting.getRemittancePurpose() != null) {
			list.add(paymentSetting.getRemittancePurpose().getName()); // �����ړI��
		} else {
			list.add("");
		}

		list.add(paymentSetting.getSendBankName()); // �p����s��
		list.add(paymentSetting.getSendBranchName()); // �p���x�X��
		list.add(paymentSetting.getAccountName()); // �p����s�Z��
		list.add(getWord(CommissionPaymentType.getName(paymentSetting.getCommissionPaymentType()))); // �萔���敪
		list.add(paymentSetting.getPayBankName()); // �x����s����
		list.add(paymentSetting.getPayBranchName()); // �x���x�X����
		list.add(paymentSetting.getPayBankAddress()); // �x����s�Z��
		list.add(paymentSetting.getViaBankName()); // �o�R��s����
		list.add(paymentSetting.getViaBranchName()); // �o�R�x�X����
		list.add(paymentSetting.getViaBankAddress()); // �o�R��s�Z��
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

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// �����R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlCustomerReference.getCode())) {
			// �����R�[�h����͂��Ă��������B
			showMessage(editView, "I00037", "C00786");
			editView.ctrlCustomerReference.requestTextFocus();
			return false;
		}

		// ���������R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlCustomerConditionCode.getValue())) {
			// ���������R�[�h����͂��Ă��������B
			showMessage(editView, "I00037", "C00788");
			editView.ctrlCustomerConditionCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓�������R�[�h���A���������R�[�h�����݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {

			PaymentSettingSearchCondition condition = createPaymentSettingSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCustomerCode(editView.ctrlCustomerReference.getCode());
			condition.setCode(editView.ctrlCustomerConditionCode.getValue());
			List<PaymentSetting> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				// �w��̃f�[�^�͊��ɑ��݂��܂��B
				showMessage(editView, "I00262");
				editView.ctrlCustomerConditionCode.requestTextFocus();
				return false;
			}
		}

		// �x�����@�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlPaymentMethodReference.getCode())) {
			// �x�����@����͂��Ă��������B
			showMessage(editView, "I00037", "C00233");
			editView.ctrlPaymentMethodReference.requestTextFocus();
			return false;
		}

		// �`�����߂������͂̏ꍇ�G���[
		if (editView.ctrlpaymentConditionDay.isEmpty()) {
			// ���ߓ�����͂��Ă��������B
			showMessage(editView, "I00037", "C00244");
			editView.ctrlpaymentConditionDay.requestFocus();
			return false;
		}

		// ���t(���l)�`�F�b�N
		int simDay = editView.ctrlpaymentConditionDay.getInt();
		if (simDay <= 0 || (31 < simDay && simDay != 99)) {
			// ���ߓ���1�`31, 99�͈̔͂œ��͂��Ă��������B
			showMessage(editView, "I00247", "C00244", "1", "31, 99");
			editView.ctrlpaymentConditionDay.requestFocus();
			return false;
		}

		// �`�����オ�����͂̏ꍇ�G���[
		if (editView.ctrlpaymentConditionMonth.isEmpty()) {
			// ���ߌ㌎����͂��Ă��������B
			showMessage(editView, "I00037", "C11257");
			editView.ctrlpaymentConditionMonth.requestFocus();
			return false;
		}

		// �`�������������͂̏ꍇ�G���[
		if (editView.ctrlpaymentConditionPayDay.isEmpty()) {
			// �x��������͂��Ă��������B
			showMessage(editView, "I00037", "C01098");
			editView.ctrlpaymentConditionPayDay.requestFocus();
			return false;
		}

		// ���t(���l)�`�F�b�N
		int payDay = editView.ctrlpaymentConditionPayDay.getInt();
		if (payDay <= 0 || (31 < payDay && payDay != 99)) {
			// �x������1�`31, 99�͈̔͂œ��͂��Ă��������B
			showMessage(editView, "I00247", "C01098", "1", "31, 99");
			editView.ctrlpaymentConditionPayDay.requestFocus();
			return false;
		}

		// �U���U�o��s�R�[�h�������͂̏ꍇ�G���[
		// if (Util.isNullOrEmpty(editView.ctrlBankAccountReference.getCode())) {
		// // �U���U�o��s�R�[�h����͂��Ă��������B
		// showMessage(editView, "I00037", "C00792");
		// editView.ctrlBankAccountReference.requestTextFocus();
		// return false;
		// }

		// ��s�R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlBank.getCode())) {
			// ��s�R�[�h����͂��Ă��������B
			showMessage(editView, "I00037", "C00779");
			editView.ctrlBank.requestTextFocus();
			return false;
		}

		// �x�X�R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlBranch.getCode())) {
			// �x�X�R�[�h����͂��Ă��������B
			showMessage(editView, "I00037", "C02055");
			editView.ctrlBranch.requestTextFocus();
			return false;
		}

		// �����ԍ��������͂̏ꍇ�G���[
		PaymentMethod paymentMethod = editView.ctrlPaymentMethodReference.getEntity();

		if (paymentMethod != null && PaymentKind.isBankAccountPayment(paymentMethod.getPaymentKind())) {
			if (editView.ctrlAccountNumber.isEmpty()) {
				// �����ԍ�����͂��Ă��������B
				showMessage(editView, "I00037", "C00794");
				editView.ctrlAccountNumber.requestTextFocus();
				return false;
			}
		}

		// �a����ڂ��O�ݎ��A�p����s������щp���x�X���������͂̏ꍇ�G���[
		if (getSelectedDepositKind() == DepositKind.FOREIGN_CURRENCY) {
			if (Util.isNullOrEmpty(editView.ctrlEnglishBankName.getValue())) {
				// �p����s������͂��Ă��������B
				showMessage(editView, "I00037", "C00795");
				editView.ctrlEnglishBankName.requestTextFocus();
				return false;
			}

			if (Util.isNullOrEmpty(editView.ctrlEnglishBranchName.getValue())) {
				// �p���x�X������͂��Ă��������B
				showMessage(editView, "I00037", "C00796");
				editView.ctrlEnglishBranchName.requestTextFocus();
				return false;
			}
		}

		// �������`�ł������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlAccountKana.getValue())) {
			// �������`�ł���͂��Ă��������B
			showMessage(editView, "I00037", "C00168");
			editView.ctrlAccountKana.requestFocus();
			return false;
		}

		// �J�n�N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrldtBeginDate.getValue())) {
			// �J�n�N��������͂��Ă��������B
			showMessage(editView, "I00037", "C00055");
			editView.ctrldtBeginDate.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrldtEndDate.getValue())) {
			// �I���N��������͂��Ă��������B
			showMessage(editView, "I00037", "C00261");
			editView.ctrldtEndDate.requestTextFocus();
			return false;
		}

		// �J�n���t<=�I�����t�ɂ��Ă��������B
		if (!Util.isSmallerThenByYMD(editView.ctrldtBeginDate.getValue(), editView.ctrldtEndDate.getValue())) {
			// �J�n���t<=�I�����t�ɂ��Ă��������B
			showMessage(editView, "I00090", getWord("C01013") + getWord("C00446"), getWord("C00260")
				+ getWord("C00446"));
			editView.ctrldtEndDate.requestTextFocus();
			return false;
		}

		return true;
	}

	/**
	 * �I�����ꂽ�U���萔����Ԃ�
	 * 
	 * @return �I�����ꂽ�U���萔��
	 */
	protected CashInFeeType getSelectedCashInFeeType() {

		CashInFeeType selectedIndex = (CashInFeeType) editView.ctrlcboCashInFee.getSelectedItemValue();
		return selectedIndex;
	}

	/**
	 * �I�����ꂽ�x���敪��Ԃ�
	 * 
	 * @return �I�����ꂽ�x���敪
	 */
	protected PaymentDateType getSelectedPaymentDataType() {

		PaymentDateType selectedIndex = (PaymentDateType) editView.ctrlcboPaymentDataType.getSelectedItemValue();
		return selectedIndex;
	}

	/**
	 * �I�����ꂽ�x���萔���敪��Ԃ�
	 * 
	 * @return �I�����ꂽ�x���萔���敪
	 */
	protected CommissionPaymentType getSelectedPayFeeType() {

		CommissionPaymentType selectedIndex = (CommissionPaymentType) editView.ctrlcboCommissionPaymentType
			.getSelectedItemValue();
		return selectedIndex;
	}

	/**
	 * �I�����ꂽ�a����ڂ�Ԃ�
	 * 
	 * @return �I�����ꂽ�a�����
	 */
	protected DepositKind getSelectedDepositKind() {

		DepositKind selectedIndex = (DepositKind) editView.ctrlcboDepositKind.getSelectedItemValue();
		return selectedIndex;
	}
}