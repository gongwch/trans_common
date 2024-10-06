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
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.bank.*;
import jp.co.ais.trans2.model.employee.*;

/**
 * MG0160EmployeeMaster - �Ј��}�X�^ - Main Controller
 * 
 * @author AIS
 */
public class MG0160EmployeeMasterPanelCtrl extends TController {

	/** �V�K��� */
	protected MG0160EmployeeMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0160EmployeeMasterDialog editView = null;

	/** ���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p���� */
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

			// �w����ʏ�����
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
	 * �w����ʐ���, �C�x���g��`
	 */
	protected void createMainView() {
		mainView = new MG0160EmployeeMasterPanel();
		addMainViewEvent();

	}

	/**
	 * �w����ʏ�����
	 */
	protected void initMainView() {
		setMainButtonEnabled(false);
	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param bln Boolean
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnModify.setEnabled(bln);
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
	}

	/**
	 * �w����ʂ̃C�x���g��`
	 */
	protected void addMainViewEvent() {
		// [�V�K]�{�^��
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [����]�{�^��
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [�ҏW]�{�^��
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [����]�{�^��
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [�폜]�{�^��
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [�G�N�Z��]�{�^��
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * �w�����[�V�K]�{�^������
	 */
	protected void btnNew_Click() {
		try {

			// �ҏW��ʐ���
			createEditView();

			// �ҏW�ҏW��ʏ�����
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

			// ���������擾
			EmployeeSearchCondition condition = getSearchCondition();

			// �J�n <= �I���`�F�b�N
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlEmpRefRan.requestFocus();
				return;
			}

			// �ꗗ���N���A
			mainView.tbList.removeRow();

			// ���C���{�^������
			setMainButtonEnabled(false);

			// �f�[�^�擾
			List<Employee> list = getList(condition);

			// ���������ɊY������f�[�^�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ���������f�[�^���ꗗ�ɕ\������
			for (Employee emp : list) {
				mainView.tbList.addRow(getRowData(emp));
			}

			// ���C���{�^������
			setMainButtonEnabled(true);

			mainView.tbList.setRowSelectionInterval(0, 0);

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

			// �ҏW�Ώۂ̃f�[�^�擾
			Employee bean = getSelected();

			// �ҏW��ʐ���
			createEditView();

			// �ҏW��ʂɑI���f�[�^���Z�b�g
			initEditView(Mode.MODIFY, bean);

			// �ҏW��ʕ\��
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

			// ���ʑΏۂ̃f�[�^�擾
			Employee bean = getSelected();

			// ���ʉ�ʐ���
			createEditView();

			// �ҏW��ʂɑI���f�[�^���Z�b�g
			initEditView(Mode.COPY, bean);

			// ���ʉ�ʕ\��
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

			// �m�F���b�Z�[�W�\��
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜�Ώۂ̃f�[�^�擾
			Employee bean = getSelected();

			// �폜���s
			doDelete(bean);

			// �폜�����s���ꗗ����폜
			mainView.tbList.removeSelectedRow();

			// �폜������A�ꗗ�̃��R�[�h��0���̏ꍇ�A���C���{�^������
			if (mainView.tbList.getRowCount() == 0) {
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

		if (mainView.tbList.getSelectedRow() == -1) {
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

			// ���������̎擾
			EmployeeSearchCondition condition = getSearchCondition();

			// �J�n <= �I���`�F�b�N
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlEmpRefRan.requestFocus();
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �G�N�Z���f�[�^�̎擾
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			// �f�[�^�����������ꍇ�A�G���[���b�Z�[�W�o��
			if (Util.isNullOrEmpty(data)) {
				showMessage(mainView, "I00022");
				return;
			}

			// �G�N�Z���^�C�g���Z�b�g
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C00913") + ".xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ҏW��ʍ쐬
	 */
	protected void createEditView() {
		editView = new MG0160EmployeeMasterDialog(mainView.getParentFrame(), true);
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
		// ��s
		editView.ctrlBnkCode.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !editView.ctrlBnkCode.isValueChanged()) {
					return;
				}

				ctrlBankReference_after();
			}

			protected void ctrlBankReference_after() {
				Bank bank = editView.ctrlBnkCode.getEntity();

				if (bank != null) {
					editView.ctrlStnCode.clear();
					editView.ctrlStnCode.setEditable(true);
					editView.ctrlStnCode.getSearchCondition().setBankCode(bank.getCode());
				} else {
					editView.ctrlStnCode.clear();
					editView.ctrlStnCode.setEditable(false);
				}
			}
		});

	}

	/**
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnSettle_Click() {
		try {

			// ���̓f�[�^�`�F�b�N
			if (!isInputCorrect()) {
				return;
			}

			// ���̓f�[�^�擾
			Employee bean = getInputtedEmployee();

			// �V�K�o�^�̏ꍇ
			switch (mode) {

				case NEW:
				case COPY:

					// �V�K�o�^ - ���ʓo�^
					request(getModelClass(), "entry", bean);

					// �ǉ������ꗗ�ɔ��f
					mainView.tbList.addRow(getRowData(bean));

					// ���C���{�^������
					setMainButtonEnabled(true);

					break;

				case MODIFY:

					// �C��
					request(getModelClass(), "modify", bean);

					// �C�������ꗗ�ɔ��f
					mainView.tbList.modifySelectedRow(getRowData(bean));

					break;
			}

			btnClose_Click();
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW���[�߂�]�{�^������
	 */
	protected void btnClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * �ҏW��ʏ�����
	 * 
	 * @param editMode
	 * @param bean
	 */
	protected void initEditView(Mode editMode, Employee bean) {

		this.mode = editMode;

		switch (editMode) {

			case NEW:

				editView.setTitle(getWord("C01698"));
				editView.ctrlStnCode.setEditable(false);
				editView.ctrlDateFrom.setValue(editView.ctrlDateFrom.getCalendar().getMinimumDate());
				editView.ctrlDateTo.setValue(editView.ctrlDateTo.getCalendar().getMaximumDate());
				editView.bgKozaKbn.setSelected(editView.ctrlKozaKbnOrd.getModel(), true);

				break;

			case MODIFY:
			case COPY:

				if (Mode.MODIFY == editMode) {

					editView.setTitle(getWord("C00977"));
					editView.ctrlCode.setEnabled(false);

				} else {

					editView.setTitle(getWord("C01738"));

				}

				if (bean.getBnkCode() == null && bean.getStnCode() == null) {
					editView.ctrlStnCode.setEditable(false);
				}

				editView.ctrlCode.setValue(bean.getCode());
				editView.ctrlName.setValue(bean.getName());
				editView.ctrlNameS.setValue(bean.getNames());
				editView.ctrlNameK.setValue(bean.getNamek());
				editView.ctrlCbkCode.code.setValue(bean.getCbkCode());
				editView.ctrlCbkCode.name.setValue(bean.getCbkName());
				editView.ctrlBnkCode.code.setValue(bean.getBnkCode());
				editView.ctrlBnkCode.name.setValue(bean.getBnkName());
				editView.ctrlStnCode.code.setValue(bean.getStnCode());
				editView.ctrlStnCode.name.setValue(bean.getStnName());
				editView.ctrlStnCode.getSearchCondition().setBankCode(bean.getBnkCode());
				editView.bgKozaKbn.setSelected(editView.ctrlKozaKbnOrd.getModel(), true);
				editView.ctrlKozaKbnCur.setSelected(DepositKind.CURRENT == bean.getKozaKbn());
				editView.ctrlYknNo.setValue(bean.getYknNo());
				editView.ctrlYknKana.setValue(bean.getYknKana());
				editView.ctrlDateFrom.setValue(bean.getDateFrom());
				editView.ctrlDateTo.setValue(bean.getDateTo());

				break;
		}
	}

	/**
	 * �ҏW��ʂ̓��͒l�擾
	 * 
	 * @return Employee
	 */
	protected Employee getInputtedEmployee() {

		Employee bean = new Employee();

		bean.setCode(editView.ctrlCode.getValue());
		bean.setName(editView.ctrlName.getValue());
		bean.setNames(editView.ctrlNameS.getValue());
		bean.setNamek(editView.ctrlNameK.getValue());
		bean.setCbkCode(editView.ctrlCbkCode.getCode());
		bean.setCbkName(editView.ctrlCbkCode.getNames());
		bean.setBnkCode(editView.ctrlBnkCode.getCode());
		bean.setBnkName(editView.ctrlBnkCode.getNames());
		bean.setStnCode(editView.ctrlStnCode.getCode());
		bean.setStnName(editView.ctrlStnCode.getNames());

		if (editView.ctrlKozaKbnCur.isSelected()) {
			bean.setKozaKbn(DepositKind.CURRENT);
		} else {
			bean.setKozaKbn(DepositKind.ORDINARY);
		}

		bean.setYknKana(editView.ctrlYknKana.getValue());
		bean.setYknNo(editView.ctrlYknNo.getValue());
		bean.setDateFrom(editView.ctrlDateFrom.getValue());
		bean.setDateTo(editView.ctrlDateTo.getValue());

		return bean;
	}

	/**
	 * ���������擾
	 * 
	 * @return EmployeeSearchCondition ��������
	 */
	protected EmployeeSearchCondition getSearchCondition() {

		EmployeeSearchCondition condition = new EmployeeSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.ctrlEmpRefRan.getCodeFrom());
		condition.setCodeTo(mainView.ctrlEmpRefRan.getCodeTo());
		if (!mainView.chkTerm.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;
	}

	/**
	 * �ꗗ�ɕ\������f�[�^���Z�b�g
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(Employee bean) {

		List<Object> list = new ArrayList<Object>();

		list.add(bean.getCode());
		list.add(bean.getName());
		list.add(bean.getNames());
		list.add(bean.getNamek());
		list.add(bean.getCbkCode());
		list.add(bean.getCbkName());
		list.add(bean.getBnkCode());
		list.add(bean.getBnkName());
		list.add(bean.getStnCode());
		list.add(bean.getStnName());
		list.add(getWord(bean.getKozaKbnText()));
		list.add(bean.getYknNo());
		list.add(bean.getYknKana());
		list.add(DateUtil.toYMDString(bean.getDateFrom()));
		list.add(DateUtil.toYMDString(bean.getDateTo()));
		list.add(bean);

		return list;
	}

	/**
	 * �ꗗ�őI�������f�[�^���擾
	 * 
	 * @return bean
	 * @exception Exception
	 */
	protected Employee getSelected() throws Exception {

		Employee bean = (Employee) mainView.tbList.getSelectedRowValueAt(MG0160EmployeeMasterPanel.SC.ENTITY);

		EmployeeSearchCondition condition = new EmployeeSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(bean.getCode());

		List<Employee> list = getList(condition);

		// ���b�Z�[�W�ǉ��K�v ##########
		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
			throw new TException(getMessage("I00193"));
		}
		return list.get(0);
	}

	/**
	 * ���������ɊY������f�[�^��Ԃ�
	 * 
	 * @param condition
	 * @return List
	 * @throws Exception
	 */
	protected List<Employee> getList(EmployeeSearchCondition condition) throws Exception {
		List<Employee> list = (List<Employee>) request(getModelClass(), "get", condition);
		return list;
	}

	/**
	 * �w��s�f�[�^���폜����
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void doDelete(Employee bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * @return ���f��
	 */
	protected Class getModelClass() {
		return EmployeeManager.class;
	}

	/**
	 * �ҏW��ʓ��͒l�̑Ó������`�F�b�N *
	 * 
	 * @return bool - true:run, false:stop
	 * @exception Exception
	 */

	protected boolean isInputCorrect() throws Exception {
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			showMessage(editView, "I00037", "C00697");
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlName.getValue())) {
			showMessage(editView, "I00037", "C00807");
			editView.ctrlName.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlNameS.getValue())) {
			showMessage(editView, "I00037", "C00808");
			editView.ctrlNameS.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlNameK.getValue())) {
			showMessage(editView, "I00037", "C00809");
			editView.ctrlNameK.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlDateFrom.getValue())) {
			showMessage(editView, "I00037", "C00055");
			editView.ctrlDateFrom.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlDateTo.getValue())) {
			showMessage(editView, "I00037", "C00261");
			editView.ctrlDateTo.requestTextFocus();
			return false;
		}

		if (editView.ctrlStnCode.isEditable() && Util.isNullOrEmpty(editView.ctrlStnCode.getCode())) {
			showMessage(editView, "I00037", editView.ctrlStnCode.btn.getText());
			editView.ctrlStnCode.requestTextFocus();
			return false;
		}

		// �J�n�N����,�I���N�����`�F�b�N
		if ((!Util.isNullOrEmpty(editView.ctrlDateFrom.getValue()) && !Util.isNullOrEmpty(editView.ctrlDateTo
			.getValue())) && !Util.isSmallerThenByYMD(editView.ctrlDateFrom.getValue(), editView.ctrlDateTo.getValue())) {
			showMessage(editView, "I00067");
			editView.ctrlDateFrom.requestFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓����ЃR�[�h&&����Ј��R�[�h�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			EmployeeSearchCondition condition = new EmployeeSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlCode.getValue());

			List<Employee> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00084", "C00697");
				editView.ctrlCode.requestTextFocus();
				return false;
			}
		}
		return true;
	}
}