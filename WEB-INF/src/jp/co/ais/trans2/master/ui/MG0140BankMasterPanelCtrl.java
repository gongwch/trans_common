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
import jp.co.ais.trans2.master.ui.MG0140BankMasterPanel.SC;
import jp.co.ais.trans2.model.bank.*;

/**
 * ��s�}�X�^�̃R���g���[��
 */

public class MG0140BankMasterPanelCtrl extends TController {

	/**
	 * �w�����
	 */
	protected MG0140BankMasterPanel mainView = null;

	/**
	 * �ҏW���
	 */
	protected MG0140BankMasterDialog editView = null;

	/**
	 * ���샂�[�h��c��
	 */
	protected Mode mode = null;

	/**
	 * ���샂�[�h
	 */
	protected enum Mode {
		/** �V�K */
		New,
		/** �C�� */
		Copy,
		/** ���� */
		Modify
	}

	@Override
	public void start() {

		try {
			// �w����ʐ���
			createMainView();

			// �w����ʂ�������
			initMainView();

			// �w����ʎw��
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
	 * �w����ʂ̃t�@�N�g��
	 */
	protected void createMainView() {
		mainView = new MG0140BankMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʃC�x���g��`
	 */
	protected void addMainViewEvent() {

		// �V�K�{�^������
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// �����{�^������
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// �ҏW�{�^������
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// ���ʃ{�^������
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// �폜�{�^������
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// �G�N�Z���{�^��������
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// ��s�R�[�h���̓C�x���g
		mainView.ctrlBank.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				verifyBankCode();
			}
		});
	}

	/**
	 * ���͂�����s�R�[�h���x�X���������ɓn��
	 */
	protected void verifyBankCode() {

		// ���͂�����s�R�[�h
		Bank bank = mainView.ctrlBank.getEntity();

		// ���͂���Ă��Ȃ��A�܂��̓}�X�^�ɑ��݂��Ȃ��l�ł���΁A�x�X���u�����N�ɂ���
		if (bank == null) {
			mainView.ctrlBranch.getFieldFrom().getSearchCondition().setBankCode(null);
			mainView.ctrlBranch.getFieldTo().getSearchCondition().setBankCode(null);
			mainView.ctrlBranch.clear();
		} else {
			mainView.ctrlBranch.getFieldFrom().getSearchCondition().setBankCode(bank.getCode());
			mainView.ctrlBranch.getFieldTo().getSearchCondition().setBankCode(bank.getCode());
		}
		setBranchEnabled(bank != null);

	}

	/**
	 * �w����ʂ�������
	 */
	protected void initMainView() {

		// ���C���{�^���������s�\�ɂ���
		setMainButtonEnabled(false);

		// �x�X�t�B�[���h�͎g�p�s��
		setBranchEnabled(false);

	}

	/**
	 * �w����ʐV�K�{�^��������
	 */
	protected void btnNew_Click() {

		try {

			// �ҏW��ʂ𐶐�
			createEditView();

			// �ҏW��ʂ�����������
			initEditView(Mode.New, null);

			// �ҏW��ʂ�\��
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w����ʌ����{�^��������
	 */
	protected void btnSearch_Click() {

		try {

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// �J�n�R�[�h<=�I���R�[�h�ɂ��Ă��������B
			if (Util.isSmallerThen(mainView.ctrlBranch.getCodeFrom(), mainView.ctrlBranch.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlBranch.getFieldFrom().requestFocus();
				return;
			}

			// ��s�����擾
			BranchSearchCondition branchcondition = getSearchCondition();

			List<Bank> list = getBranch(branchcondition);

			// ���������ɊY�������s�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "W01118", "C00124");
				return;
			}

			// ��s�����ꗗ�ɕ\������
			for (Bank bank : list) {
				mainView.tbl.addRow(getRowData(bank));
			}
			// ���C���{�^���������\�ɂ��A1�s�ڂ�I������
			setMainButtonEnabled(true);
			mainView.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �w����ʕҏW������
	 */
	protected void btnModify_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// �ҏW�Ώۂ̋�s�����擾����
			Bank bank = getSelectedRowData();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̋�s�����Z�b�g����
			createEditView();
			initEditView(Mode.Modify, bank);

			// �ҏW��ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w����ʕ��ʃ{�^��������
	 */
	protected void btnCopy_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// ���ʑΏۂ̋�s�����擾����
			Bank bank = getSelectedRowData();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̋�s�����Z�b�g����
			createEditView();
			initEditView(Mode.Copy, bank);

			// ���ʉ�ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w����ʍ폜������
	 */
	protected void btnDelete_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// �폜�Ώۂ̋�s�����擾����
			Bank bank = getSelectedRowData();

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜����
			deleteBank(bank);

			// �폜������s�����ꗗ����폜
			mainView.tbl.removeSelectedRow();

			// �폜��������0���ɂȂ����烁�C���{�^���������s�\�ɂ���
			if (mainView.tbl.getRowCount() == 0) {
				setMainButtonEnabled(false);
			}

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
	 * �w����ʃG�N�Z���{�^��������
	 */
	protected void btnExcel_Click() {

		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// �f�[�^���o
			BranchSearchCondition condition = getSearchCondition();

			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02323") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g��
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0140BankMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();

	}

	/**
	 * �ҏW��ʂ̃C�x���g��`
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

	}

	/**
	 * �ҏW��ʂ�������
	 * 
	 * @param mode_
	 * @param bank
	 */
	protected void initEditView(Mode mode_, Bank bank) {

		this.mode = mode_;

		// �V�K�̏ꍇ
		if (Mode.New == mode_) {
			editView.setTitle(getWord("C01698"));

			editView.dateFrom.setValue(editView.dateFrom.getCalendar().getMinimumDate());
			editView.dateTo.setValue(editView.dateTo.getCalendar().getMaximumDate());

			return;

			// �ҏW�A���ʂ̏ꍇ
		} else if (Mode.Modify == mode_ || Mode.Copy == mode_) {

			// �ҏW
			if (Mode.Modify == mode_) {
				editView.setTitle(getWord("C00977"));
				editView.ctrlCode.setEditable(false);
				editView.ctrlBranchCode.setEditable(false);
				editView.setEditMode();

				// ����
			} else {
				editView.setTitle(getWord("C01738"));

			}

			editView.ctrlCode.setValue(bank.getCode());
			editView.ctrlBranchCode.setValue(bank.getBranch().getCode());
			editView.ctrlName.setValue(bank.getNames());
			editView.ctrlKana.setValue(bank.getKana());
			editView.ctrlNamek.setValue(bank.getNamek());
			editView.ctrlBranchName.setValue(bank.getBranch().getNames());
			editView.ctrlBranchKana.setValue(bank.getBranch().getKana());
			editView.ctrlBranchNamek.setValue(bank.getBranch().getNamek());
			editView.dateFrom.setValue(bank.getDateFrom());
			editView.dateTo.setValue(bank.getDateTo());

		}
	}

	/**
	 * �ҏW��ʊm�������
	 */

	protected void btnSettle_Click() {

		try {

			// ���̓`�F�b�N���s��
			if (!isInputRight()) {
				return;
			}

			// ���͂��ꂽ��s�����擾
			Bank bank = getInputedBank();

			// �V�K�o�^�̏ꍇ
			if (Mode.New == mode || Mode.Copy == mode) {

				// �V�K�o�^
				request(getModelClass(), "entry", bank);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(bank));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.Modify == mode) {

				// �C��
				request(getModelClass(), "modify", bank);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(bank));

			}

			// �C���s�𔽉f����
			dispatchRow(bank);

			// �ҏW��ʂ����
			btnClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�肷����𔽉f����
	 * 
	 * @param bank
	 */
	protected void dispatchRow(Bank bank) {

		for (int i = 0; i < mainView.tbl.getRowCount(); i++) {
			String bankCode = (String) mainView.tbl.getRowValueAt(i, SC.BnkCode);
			if (bankCode.equals(bank.getCode())) {
				mainView.tbl.setRowValueAt(bank.getNames(), i, SC.BnkName);
				mainView.tbl.setRowValueAt(bank.getKana(), i, SC.BnkKana);
				mainView.tbl.setRowValueAt(bank.getNamek(), i, SC.BnkNamek);

			}
		}
	}

	/**
	 * �ҏW��ʖ߂������
	 */
	protected void btnClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * �w����ʂœ��͂��ꂽ��s�̌���������Ԃ��B
	 * 
	 * @return ��s�̌�������
	 */
	protected BranchSearchCondition getSearchCondition() {
		BranchSearchCondition condition = new BranchSearchCondition();
		condition.setBankCode(mainView.ctrlBank.getCode());
		condition.setCodeFrom(mainView.ctrlBranch.getCodeFrom());
		condition.setCodeTo(mainView.ctrlBranch.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;
	}

	/**
	 * �N���X��Ԃ�
	 * 
	 * @return �N���X
	 */
	protected Class getModelClass() {
		return BankManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂�����s����Ԃ��B
	 * 
	 * @return ��s���
	 */
	protected Bank getInputedBank() {

		Bank bank = new Bank();
		bank.setCode(editView.ctrlCode.getValue());
		Branch branch = new Branch();
		bank.setBranch(branch);
		bank.getBranch().setCode(editView.ctrlBranchCode.getValue());
		bank.setNames(editView.ctrlName.getValue());
		bank.setKana(editView.ctrlKana.getValue());
		bank.setNamek(editView.ctrlNamek.getValue());
		bank.getBranch().setNames(editView.ctrlBranchName.getValue());
		bank.getBranch().setKana(editView.ctrlBranchKana.getValue());
		bank.getBranch().setNamek(editView.ctrlBranchNamek.getValue());
		bank.setDateFrom(editView.dateFrom.getValue());
		bank.setDateTo(editView.dateTo.getValue());

		return bank;
	}

	/**
	 * ��s�����ꗗ�ɕ\������`���ŕԂ�
	 * 
	 * @param bank
	 * @return ��s���
	 */
	protected String[] getRowData(Bank bank) {
		return new String[] { bank.getCode(), bank.getBranch().getCode(), bank.getNames(), bank.getKana(),
				bank.getNamek(), bank.getBranch().getNames(), bank.getBranch().getKana(), bank.getBranch().getNamek(),
				DateUtil.toYMDString(bank.getDateFrom()), DateUtil.toYMDString(bank.getDateTo()) };
	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param bln
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnModify.setEnabled(bln);
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
	}

	/**
	 * ���������ɊY�������s���X�g��Ԃ�
	 * 
	 * @param condition
	 * @return ��s���X�g
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Bank> getBranch(BranchSearchCondition condition) throws Exception {

		List<Bank> list = (List<Bank>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * �ꗗ�őI������Ă����Ԃ��B
	 * 
	 * @return �I�����
	 * @throws Exception
	 */
	protected Bank getSelectedRowData() throws Exception {

		BranchSearchCondition condition = new BranchSearchCondition();
		condition.setBankCode((String) mainView.tbl.getSelectedRowValueAt(MG0140BankMasterPanel.SC.BnkCode));
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0140BankMasterPanel.SC.BranchCode));

		List<Bank> list = getBranch(condition);

		if (list == null || list.isEmpty()) {
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * �w��̋�s�����폜����
	 * 
	 * @param bank
	 * @throws Exception
	 */
	protected void deleteBank(Bank bank) throws Exception {
		request(getModelClass(), "delete", bank);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������ׂ�
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// ��s�R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			showMessage(editView, "I00037", "C00779");
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		// ��s�x�X�R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlBranchCode.getValue())) {
			showMessage(editView, "I00037", "C00780");
			editView.ctrlBranchCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓����s�R�[�h�Ǝx�X�R�[�h�����ɑ��݂�����G���[
		if (Mode.New == mode || Mode.Copy == mode) {
			BranchSearchCondition condition = new BranchSearchCondition();
			condition.setCode(editView.ctrlBranchCode.getValue());
			condition.setBankCode(editView.ctrlCode.getValue());
			List<Bank> list = getBranch(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00124");
				editView.ctrlBranchCode.requestTextFocus();
				return false;
			}
		}

		// ��s���������͂̏ꍇ�G���[

		if (Util.isNullOrEmpty(editView.ctrlName.getValue())) {
			showMessage(editView, "I00037", "C00781");
			editView.ctrlName.requestTextFocus();
			return false;
		}

		// ��s�J�i���̂������͂̏ꍇ�G���[

		if (Util.isNullOrEmpty(editView.ctrlKana.getValue())) {
			showMessage(editView, "I00037", "C00782");
			editView.ctrlKana.requestTextFocus();
			return false;

		}

		// ��s�������̂������͂̏ꍇ�G���[

		if (Util.isNullOrEmpty(editView.ctrlNamek.getValue())) {
			showMessage(editView, "I00037", "C00829");
			editView.ctrlNamek.requestTextFocus();
			return false;

		}

		// ��s�x�X���������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlBranchName.getValue())) {
			showMessage(editView, "I00037", "C00783");
			editView.ctrlBranchName.requestTextFocus();
			return false;

		}

		// ��s�x�X�J�i���̂������͂̏ꍇ�G���[

		if (Util.isNullOrEmpty(editView.ctrlBranchKana.getValue())) {
			showMessage(editView, "I00037", "C00784");
			editView.ctrlBranchKana.requestTextFocus();
			return false;

		}

		// ��s�x�X�������̂������͂̏ꍇ�G���[

		if (Util.isNullOrEmpty(editView.ctrlBranchNamek.getValue())) {
			showMessage(editView, "I00037", "C00785");
			editView.ctrlBranchNamek.requestTextFocus();
			return false;

		}

		// �J�n�N�����������͂̏ꍇ�G���[

		if (Util.isNullOrEmpty(editView.dateFrom.getValue())) {
			showMessage(editView, "I00037", "C00055");
			editView.dateFrom.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[

		if (Util.isNullOrEmpty(editView.dateTo.getValue())) {
			showMessage(editView, "I00037", "C00261");
			editView.dateTo.requestTextFocus();
			return false;
		}

		// �J�n�N����,�I���N�����`�F�b�N
		if ((!Util.isNullOrEmpty(editView.dateFrom.getValue()) && !Util.isNullOrEmpty(editView.dateTo.getValue()))
			&& !Util.isSmallerThenByYMD(editView.dateFrom.getValue(), editView.dateTo.getValue())) {
			showMessage(editView, "I00067");
			editView.dateFrom.requestFocus();
			return false;
		}

		return true;

	}

	/**
	 * �x�X�t�B�[���h�̓��͐���
	 * 
	 * @param bln true(���͉\�ɂ���)/false(���͕s�\�ɂ���)
	 */
	protected void setBranchEnabled(boolean bln) {
		mainView.ctrlBranch.setEditable(bln);
	}

}