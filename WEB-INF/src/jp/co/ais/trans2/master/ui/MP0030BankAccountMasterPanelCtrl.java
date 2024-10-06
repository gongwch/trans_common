package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.bank.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * ��s�����}�X�^��ʃR���g���[��
 * 
 * @author AIS
 */
public class MP0030BankAccountMasterPanelCtrl extends TController {

	/** �p�l�� */
	protected MP0030BankAccountMasterPanel mainView;

	/** �ҏW��� */
	protected MP0030BankAccountMasterDialog editView;

	/** �C���O�̃f�[�^ */
	protected BankAccount editBean = null;

	/** ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B */
	protected Mode mode = null;

	/** ���샂�[�h */
	protected enum Mode {
		/** �V�K */
		NEW,
		/** �C�� */
		MODIFY,
		/** ���� */
		COPY
	}

	@Override
	public void start() {

		try {

			// �w����ʐ���
			createMainView();

			// �w����ʂ�����������
			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MP0030BankAccountMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		mainView.btnNew.addActionListener(new ActionListener() {

			// �V�K�{�^��������
			public void actionPerformed(ActionEvent evt) {
				btnNew_Click();
			}
		});

		mainView.btnSearch.addActionListener(new ActionListener() {

			// �����{�^��������
			public void actionPerformed(ActionEvent evt) {
				btnSearch_Click();
			}
		});

		mainView.btnModify.addActionListener(new ActionListener() {

			// �ҏW�{�^��������
			public void actionPerformed(ActionEvent evt) {
				btnModify_Click();
			}
		});

		mainView.btnCopy.addActionListener(new ActionListener() {

			// ���ʃ{�^��������
			public void actionPerformed(ActionEvent evt) {
				btnCopy_Click();
			}
		});

		mainView.btnDelete.addActionListener(new ActionListener() {

			// �폜�{�^��������
			public void actionPerformed(ActionEvent evt) {
				btnDelete_Click();
			}
		});

		mainView.btnListOutput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// ���X�g�o�̓{�^��������
				btnListOutput();
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
	 * �p�l���擾
	 * 
	 * @return �ʉ݃}�X�^�p�l��
	 */
	@Override
	public TPanelBusiness getPanel() {
		// �p�l����Ԃ�
		return mainView;
	}

	/**
	 * �V�K�o�^����
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
	 * ��������
	 */
	protected void btnSearch_Click() {

		try {

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// �J�n�R�[�h<=�I���R�[�h�ɂ��Ă��������B
			if (Util.isSmallerThen(mainView.ctrlRange.getCodeFrom(), mainView.ctrlRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlRange.getFieldFrom().requestFocus();
				return;
			}

			// ��s���������擾
			BankAccountSearchCondition condition = getSearchCondition();
			List<BankAccount> list = getList(condition);

			// ���������ɊY�������s���������݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ��s���������ꗗ�ɕ\������
			for (BankAccount bean : list) {
				mainView.tbl.addRow(getRowData(bean));
			}

			// ���C���{�^���������\�ɂ��A1�s�ڂ�I������
			setMainButtonEnabled(true);
			mainView.tbl.setRowSelectionInterval(0, 0);

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

			// �ҏW�Ώۂ̋�s�������擾����
			BankAccount bean = getSelectedRowData();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̋�s���������Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, bean);

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

			// ���ʑΏۂ̋�s�������擾����
			BankAccount bean = getSelectedRowData();

			// �ҏW�O�f�[�^���폜
			editBean = null;

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̋�s���������Z�b�g����
			createEditView();
			initEditView(Mode.COPY, bean);

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
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜�Ώۂ̋�s�������擾����
			BankAccount bean = getSelectedRowData();

			// �폜����
			doDelete(bean);

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
	 * �G�N�Z�����X�g�o��
	 */
	protected void btnListOutput() {

		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �G�N�Z���f�[�^�̎擾
			BankAccountSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			// ��s�����}�X�^
			printer.preview(data, getWord("C02322") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MP0030BankAccountMasterDialog(getCompany(), mainView.getParentFrame(), true);

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

			private void ctrlBankReference_after() {
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
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			// ���̓`�F�b�N���s���B
			if (!isInputRight()) {
				return;
			}

			// ���͂��ꂽ��s���������擾
			BankAccount bean = getInputedData();
			BankAccountSearchCondition condition = createBankAccountSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(bean.getCode());

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entry", bean);
				bean = getList(condition).get(0);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(bean));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", bean);
				bean = getList(condition).get(0);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(bean));

			}

			// �ҏW��ʂ����
			btnClose_Click();

			// �������b�Z�[�W
			showMessage(mainView.getParentFrame(), "I00013");

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
	protected boolean isInputRight() throws Exception {

		// ��s�����R�[�h�`�F�b�N
		if (Util.isNullOrEmpty(editView.ctrlBankAccount.getValue())) {
			// �G���[���b�Z�[�W�o��
			showMessage("I00002", "C00857");
			editView.ctrlBankAccount.requestFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓����s�����R�[�h�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {

			BankAccountSearchCondition condition = createBankAccountSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlBankAccount.getValue());

			List<BankAccount> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00055", "C01879");
				editView.ctrlBankAccount.requestTextFocus();
				return false;
			}
		}

		// ��s��������
		if (Util.isNullOrEmpty(editView.txtBankAccountName.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(editView, "I00002", "C02145");
			editView.txtBankAccountName.requestFocus();
			return false;
		}

		// �ʉ݃`�F�b�N
		if (Util.isNullOrEmpty(editView.ctrlCurrency.getCode())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(editView, "I00002", "C00371");
			editView.ctrlCurrency.requestTextFocus();
			return false;
		}

		// ��s�`�F�b�N
		if (Util.isNullOrEmpty(editView.ctrlBank.getCode())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(editView, "I00002", "C00124");
			editView.ctrlBank.requestTextFocus();
			return false;
		}

		// �x�X�`�F�b�N
		if (Util.isNullOrEmpty(editView.ctrlBranch.getCode())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(editView, "I00002", "C00222");
			editView.ctrlBranch.requestTextFocus();
			return false;
		}

		// �U���˗��l�R�[�h�`�F�b�N
		if (Util.isNullOrEmpty(editView.ctrlTransferRequesterCode.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(editView, "I00002", "C00858");
			editView.ctrlTransferRequesterCode.requestFocus();
			return false;
		}

		// �U���˗��l���̐ݒ�
		if (Util.isNullOrEmpty(editView.ctrlTransferRequesterKanaName.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(editView, "I00002", "C00859");
			editView.ctrlTransferRequesterKanaName.requestFocus();
			return false;
		}

		// �����ԍ��`�F�b�N
		if (Util.isNullOrEmpty(editView.ctrlAccountNumber.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(editView, "I00002", "C00794");
			editView.ctrlAccountNumber.requestFocus();
			return false;
		}

		// �a����ڃ`�F�b�N
		if (Util.isNullOrEmpty(editView.ctrlDepositType.getSelectedDepositKind())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(editView, "I00002", "C01326");
			editView.ctrlDepositType.requestFocus();
			return false;
		}

		// �v�㕔��`�F�b�N
		if (Util.isNullOrEmpty(editView.ctrlAppropriateDepartment.getCode())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(editView, "I00002", "C00863");
			editView.ctrlAppropriateDepartment.requestTextFocus();
			return false;
		}

		// �Ȗڃ`�F�b�N
		if (Util.isNullOrEmpty(editView.ctrlItemUnit.ctrlItemReference.getCode())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(editView, "I00002", "C00572");
			editView.ctrlItemUnit.ctrlItemReference.requestTextFocus();
			return false;
		}

		// �⏕�`�F�b�N
		if (editView.ctrlItemUnit.ctrlSubItemReference.isEditable()
			&& Util.isNullOrEmpty(editView.ctrlItemUnit.ctrlSubItemReference.getCode())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(editView, "I00002", "C00602");
			editView.ctrlItemUnit.ctrlSubItemReference.requestTextFocus();
			return false;
		}

		// ����`�F�b�N
		if (editView.ctrlItemUnit.ctrlDetailItemReference.isEditable()
			&& Util.isNullOrEmpty(editView.ctrlItemUnit.ctrlDetailItemReference.getCode())) {
			showMessage(editView, "I00002", "C00603");
			editView.ctrlItemUnit.ctrlDetailItemReference.requestTextFocus();
			return false;
		}

		// �J�n�N����
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "W00034", "C00055");
			editView.dtBeginDate.getCalendar().requestFocus();
			return false;
		}

		// �I���N����
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			showMessage(editView, "W00034", "C00261");
			editView.dtEndDate.getCalendar().requestFocus();
			return false;
		}

		// �J�n�N���������I���N�����ɂ��Ă�������
		if (Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue()) == false) {
			editView.dtBeginDate.getCalendar().requestFocus();
			showMessage(editView, "I00067", "");
			return false;
		}

		return true;

	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ��s������Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ��s����
	 */
	protected BankAccount getInputedData() {

		BankAccount bean = createBankAccount();
		bean.setCompanyCode(getCompanyCode());
		// ��s�����R�[�h�̐ݒ�
		bean.setCode(editView.ctrlBankAccount.getValue());
		// ��s�������̂̐ݒ�
		bean.setName(editView.txtBankAccountName.getText());
		// �ʉ݃R�[�h�̐ݒ�
		bean.setCurrencyCode(editView.ctrlCurrency.getCode());
		// ��s�R�[�h�̐ݒ�
		bean.setBankCode(editView.ctrlBank.getCode());
		// �x�X�R�[�h�̐ݒ�
		bean.setBranchCode(editView.ctrlBranch.getCode());
		// �U���˗��l�R�[�h�̐ݒ�
		bean.setClientCode(editView.ctrlTransferRequesterCode.getValue());
		// �U���˗��l���̐ݒ�
		bean.setClientName(editView.ctrlTransferRequesterKanaName.getValue());
		// �U���˗��l���i�����j�̐ݒ�
		bean.setClientNameJ(editView.ctrlTransferRequesterKanjiName.getValue());
		// �U���˗��l���i�p���j�̐ݒ�
		bean.setClientNameE(editView.ctrlTransferRequesterEnglishName.getValue());
		// �a����ڂ̐ݒ�
		bean.setDepositKind(editView.ctrlDepositType.getSelectedDepositKind());
		// �����ԍ��̐ݒ�
		bean.setAccountNo(editView.ctrlAccountNumber.getValue());
		// �Ј��e�a�敪�̐ݒ�
		bean.setUseEmployeePayment(editView.rdoEmployeeFBUse.isSelected());
		// �ЊO�e�a�敪�̐ݒ�
		bean.setUseExPayment(editView.rdoExternalFBUse.isSelected());
		// �v�㕔��R�[�h�̐ݒ�
		bean.setDepartmentCode(editView.ctrlAppropriateDepartment.getCode());
		// �ȖڃR�[�h�̐ݒ�
		bean.setItemCode(editView.ctrlItemUnit.ctrlItemReference.getCode());
		// �⏕�ȖڃR�[�h�̐ݒ�
		bean.setSubItemCode(editView.ctrlItemUnit.ctrlSubItemReference.getCode());
		// ����ȖڃR�[�h�̐ݒ�
		bean.setDetailItemCode(editView.ctrlItemUnit.ctrlDetailItemReference.getCode());
		// �J�n�N�����̐ݒ�
		bean.setDateFrom(editView.dtBeginDate.getValue());
		// �I���N�����̐ݒ�
		bean.setDateTo(editView.dtEndDate.getValue());

		return bean;
	}

	/**
	 * �ҏW���[�߂�]�{�^������
	 */
	protected void btnClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param editMode ���샂�[�h�B
	 * @param bean
	 */
	protected void initEditView(Mode editMode, BankAccount bean) {

		this.mode = editMode;

		// �V�K�̏ꍇ
		if (Mode.NEW == editMode) {

			// �V�K���
			editView.setTitle(getWord("C01698"));
			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());
			editView.ctrlBranch.setEditable(false);

			editBean = null;// �ҏW�O�f�[�^���폜

			// �ҏW�A���ʂ̏ꍇ
		} else if (Mode.MODIFY == editMode || Mode.COPY == editMode) {

			// �ҏW
			if (Mode.MODIFY == editMode) {
				// �ҏW���
				editView.setTitle(getWord("C00977"));
				editView.ctrlBankAccount.setEditable(false);
				editView.setEditMode();
				editBean = bean;// �ҏW�O�f�[�^��ێ�
				// ����
			} else {
				// ���ʉ��
				editView.setTitle(getWord("C01738"));
				editBean = null;// �ҏW�O�f�[�^���폜
			}

			setEditDialog(bean);

		}

	}

	/**
	 * ��ʂɋ�s���������Z�b�g����
	 * 
	 * @param bean
	 */
	protected void setEditDialog(BankAccount bean) {
		editView.ctrlBankAccount.setValue(bean.getCode());
		editView.txtBankAccountName.setValue(bean.getName());
		editView.ctrlCurrency.code.setValue(bean.getCurrencyCode());
		editView.ctrlBank.code.setValue(bean.getBankCode());
		editView.ctrlBank.name.setValue(bean.getBankName());
		editView.ctrlBranch.code.setValue(bean.getBranchCode());
		editView.ctrlBranch.name.setValue(bean.getBranchName());
		editView.ctrlBranch.getSearchCondition().setBankCode(bean.getBankCode());
		editView.ctrlTransferRequesterCode.setValue(bean.getClientCode());
		editView.ctrlTransferRequesterKanaName.setValue(bean.getClientName());
		editView.ctrlTransferRequesterKanjiName.setValue(bean.getClientNameJ());
		editView.ctrlTransferRequesterEnglishName.setValue(bean.getClientNameE());
		editView.ctrlAccountNumber.setValue(bean.getAccountNo());
		editView.ctrlDepositType.setSelectedDepositKind(bean.getDepositKind());
		editView.rdoEmployeeFBUse.setSelected(bean.isUseEmployeePayment());
		editView.rdoExternalFBUse.setSelected(bean.isUseExPayment());
		editView.ctrlAppropriateDepartment.code.setValue(bean.getDepartmentCode());
		editView.ctrlAppropriateDepartment.name.setValue(bean.getDepartmentNames());
		Item item = new Item();
		item.setCode(bean.getItemCode());
		item.setNames(bean.getItemNames());
		if (!Util.isNullOrEmpty(bean.getSubItemCode())) {
			SubItem subItem = new SubItem();
			subItem.setCode(bean.getSubItemCode());
			subItem.setNames(bean.getSubItemNames());

			if (!Util.isNullOrEmpty(bean.getDetailItemCode())) {
				DetailItem detailItem = new DetailItem();
				detailItem.setCode(bean.getDetailItemCode());
				detailItem.setNames(bean.getDetailItemNames());
				subItem.setDetailItem(detailItem);
			}
			item.setSubItem(subItem);
		}
		editView.ctrlItemUnit.setEntity(item);

		editView.dtBeginDate.setValue(bean.getDateFrom());
		editView.dtEndDate.setValue(bean.getDateTo());
	}

	/**
	 * �ꗗ�őI������Ă����s������Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă����s����
	 * @throws Exception
	 */
	protected BankAccount getSelectedRowData() throws Exception {

		BankAccountSearchCondition condition = createBankAccountSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MP0030BankAccountMasterPanel.SC.code));

		List<BankAccount> list = getList(condition);

		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * �w��̋�s�������폜����
	 * 
	 * @param bean �}�X�^
	 * @throws Exception
	 */
	protected void doDelete(BankAccount bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * �{�^��(�V�K�A�폜�A�ҏW�A���ʁA���X�g�o��)���b�N
	 * 
	 * @param boo
	 */
	protected void lockBtn(boolean boo) {
		mainView.btnDelete.setEnabled(boo);
		mainView.btnModify.setEnabled(boo);
		mainView.btnCopy.setEnabled(boo);
		mainView.btnListOutput.setEnabled(boo);

	}

	/**
	 * �w����ʂœ��͂��ꂽ��s�����̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected BankAccountSearchCondition getSearchCondition() {

		BankAccountSearchCondition condition = createBankAccountSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setCodeFrom(mainView.ctrlRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * ���������ɊY�����郊�X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�����郊�X�g
	 * @throws Exception
	 */
	protected List<BankAccount> getList(BankAccountSearchCondition condition) throws Exception {

		List<BankAccount> list = (List<BankAccount>) request(getModelClass(), "get", condition);
		return list;

	}

	/**
	 * Manager�N���X��Ԃ�
	 * 
	 * @return �N���X
	 */
	protected Class getModelClass() {
		return BankAccountManager.class;
	}

	/**
	 * �G���e�B�e�B���ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param bean �f�[�^
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ��s����
	 */
	protected String[] getRowData(BankAccount bean) {

		return new String[] { bean.getCode(), bean.getName(), bean.getCurrencyCode(), bean.getBankCode(),
				bean.getBankName(), bean.getBranchCode(), bean.getBranchName(), bean.getClientCode(),
				bean.getClientName(), bean.getClientNameJ(), bean.getClientNameE(),
				getWord(DepositKind.getDepositKindName(bean.getDepositKind())), bean.getAccountNo(),
				bean.isUseEmployeePayment() ? getWord("C02149") : getWord("C02148"),
				bean.isUseExPayment() ? getWord("C02151") : getWord("C02150"), bean.getDepartmentCode(),
				bean.getDepartmentNames(), bean.getItemCode(), bean.getItemNames(), bean.getSubItemCode(),
				bean.getSubItemNames(), bean.getDetailItemCode(), bean.getDetailItemNames(),
				DateUtil.toYMDString(bean.getDateFrom()), DateUtil.toYMDString(bean.getDateTo()) };
	}

	/**
	 * @return ��s����
	 */
	protected BankAccount createBankAccount() {
		return new BankAccount();
	}

	/**
	 * @return ��s�����̌�������
	 */
	protected BankAccountSearchCondition createBankAccountSearchCondition() {
		return new BankAccountSearchCondition();
	}

}
