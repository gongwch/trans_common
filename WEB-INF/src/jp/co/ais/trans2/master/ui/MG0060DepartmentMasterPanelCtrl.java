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
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.department.*;

/**
 * ����}�X�^ ��ʃR���g���[��
 */
public class MG0060DepartmentMasterPanelCtrl extends TController {

	/** �w����� */
	protected MG0060DepartmentMasterPanel mainView;

	/** �ҏW��� */
	protected MG0060DepartmentMasterDialog editView = null;

	/** ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B */
	protected Mode mode = null;

	/**
	 * ���샂�[�h�B
	 */
	protected enum Mode {
		/** �V�K */
		NEW,
		/** �C�� */
		MODIFY,
		/** ���� */
		COPY
	}

	/** �A�g�R�[�h1�g�p */
	public boolean isUseIf1 = false;

	/** �A�g�R�[�h2�g�p */
	public boolean isUseIf2 = false;

	/** �A�g�R�[�h3�g�p */
	public boolean isUseIf3 = false;

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

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0060DepartmentMasterPanel();
		addMainViewEvent();
	}

	/**
	 * ���C����ʃ{�^���������̃C�x���g
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
	 * ���C����ʂ̏�����
	 */
	protected void initMainView() {
		// ���C���{�^���������s�\�ɂ���
		setMainButtonEnabled(false);

		// ����͈͂̌����������Z�b�g����
		mainView.departmentRange.ctrlDepartmentReferenceFrom.getSearchCondition().setSumDepartment(true);
		mainView.departmentRange.ctrlDepartmentReferenceTo.getSearchCondition().setSumDepartment(true);

		// �O�H�D�D�J�X�^�}�C�Y�F�A�g�����Q�b�g
		try {
			String if1Code = ClientConfig.getProperty("trans.MG0060.if.code.1.length");
			String if1Name = ClientConfig.getProperty("trans.MG0060.if.name.1.length");

			int if1CodeLength = DecimalUtil.toInt(if1Code);
			int if1NameLength = DecimalUtil.toInt(if1Name);

			isUseIf1 = if1CodeLength > 0 && if1NameLength > 0;

		} catch (Exception e) {
			// �����Ȃ�
		}
		try {
			String if2Code = ClientConfig.getProperty("trans.MG0060.if.code.2.length");
			String if2Name = ClientConfig.getProperty("trans.MG0060.if.name.2.length");

			int if2CodeLength = DecimalUtil.toInt(if2Code);
			int if2NameLength = DecimalUtil.toInt(if2Name);

			isUseIf2 = if2CodeLength > 0 && if2NameLength > 0;

		} catch (Exception e) {
			// �����Ȃ�
		}
		try {
			String if3Code = ClientConfig.getProperty("trans.MG0060.if.code.3.length");
			String if3Name = ClientConfig.getProperty("trans.MG0060.if.name.3.length");

			int if3CodeLength = DecimalUtil.toInt(if3Code);
			int if3NameLength = DecimalUtil.toInt(if3Name);

			isUseIf3 = if3CodeLength > 0 && if3NameLength > 0;

		} catch (Exception e) {
			// �����Ȃ�
		}
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
			// �J�n�R�[�h<=�I���R�[�h�ɂ��Ă��������B
			if (Util.isSmallerThen(mainView.departmentRange.getCodeFrom(), mainView.departmentRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.departmentRange.getFieldFrom().requestFocus();
				return;
			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// ��������擾
			DepartmentSearchCondition condition = getSearchCondition();
			List<Department> list = getDepartment(condition);

			// ���������ɊY�����镔�傪���݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {

				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ��������ꗗ�ɕ\������
			for (Department department : list) {
				mainView.tbl.addRow(getRowData(department));
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
			// �ҏW�Ώۂ̕�����擾����
			Department department = getSelectedDepartment();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̕�������Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, department);

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

			// ���ʑΏۂ̕�����擾����
			Department department = getSelectedDepartment();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̕�������Z�b�g����
			createEditView();
			initEditView(Mode.COPY, department);

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

			// �폜�Ώۂ̕�����擾����
			Department department = getSelectedDepartment();

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜����
			deleteDepartment(department);

			// �폜����������ꗗ����폜
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
	 * �w�����[�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {

		try {
			// �J�n�R�[�h<=�I���R�[�h�ɂ��Ă��������B
			if (Util.isSmallerThen(mainView.departmentRange.getCodeFrom(), mainView.departmentRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.departmentRange.getFieldFrom().requestFocus();
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �f�[�^���o
			DepartmentSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02338") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0060DepartmentMasterDialog(getCompany(), mainView.getParentFrame(), true);

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
	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param mode_ ���샂�[�h�B
	 * @param bean ����B�C���A���ʂ̏ꍇ�͓��Y�������ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(Mode mode_, Department bean) {

		this.mode = mode_;

		// �V�K
		switch (mode) {
		// �V�K
			case NEW:
				editView.setTitle(getWord("C01698"));
				editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
				editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());

				break;

			case COPY:
			case MODIFY:
				// �ҏW
				if (Mode.MODIFY == mode_) {
					editView.setTitle(getWord("C00977"));
					editView.ctrlDepartmentCode.setEditable(false);
					editView.setEditMode();
					// ����
				} else {
					editView.setTitle(getWord("C01738"));
				}

				editView.ctrlDepartmentCode.setValue(bean.getCode());
				editView.ctrlDepartmentName.setValue(bean.getName());
				editView.ctrlDepartmentNames.setValue(bean.getNames());
				editView.ctrlDepartmentNamek.setValue(bean.getNamek());
				editView.ctrlDepartmentMen1.setNumber(bean.getPersonnel1());
				editView.ctrlDepartmentMen2.setNumber(bean.getPersonnel2());
				editView.ctrlDepartmentArea.setNumber(bean.getFloorSpace());
				editView.rdoTotal.setSelected(bean.isSumDepartment());
				editView.rdoImput.setSelected(!bean.isSumDepartment());
				editView.dtBeginDate.setValue(bean.getDateFrom());
				editView.dtEndDate.setValue(bean.getDateTo());

				editView.ctrlIf1Code.setValue(bean.getIfCode1());
				editView.ctrlIf1Name.setValue(bean.getIfName1());
				editView.ctrlIf2Code.setValue(bean.getIfCode2());
				editView.ctrlIf2Name.setValue(bean.getIfName2());
				editView.ctrlIf3Code.setValue(bean.getIfCode3());
				editView.ctrlIf3Name.setValue(bean.getIfName3());
		}
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

			// ���͂��ꂽ��������擾
			Department department = getInputedDepartment();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entry", department);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(department));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", department);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(department));

			}

			// �ҏW��ʂ����
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
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return DepartmentManager
	 */
	protected Class getModelClass() {
		return DepartmentManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�����Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ����
	 */
	protected Department getInputedDepartment() {

		Department department = createDepartment();
		department.setCompanyCode(getCompany().getCode());
		department.setCode(editView.ctrlDepartmentCode.getValue());
		department.setName(editView.ctrlDepartmentName.getValue());
		department.setNames(editView.ctrlDepartmentNames.getValue());
		department.setNamek(editView.ctrlDepartmentNamek.getValue());
		department.setPersonnel1(editView.ctrlDepartmentMen1.getIntValue());
		department.setPersonnel2(editView.ctrlDepartmentMen2.getIntValue());
		department.setFloorSpace(editView.ctrlDepartmentArea.getBigDecimalValue());
		department.setSumDepartment(editView.rdoImput.isSelected());
		department.setSumDepartment(editView.rdoTotal.isSelected());
		department.setDateFrom(editView.dtBeginDate.getValue());
		department.setDateTo(editView.dtEndDate.getValue());

		department.setIfCode1(editView.ctrlIf1Code.getValue());
		department.setIfName1(editView.ctrlIf1Name.getValue());
		department.setIfCode2(editView.ctrlIf2Code.getValue());
		department.setIfName2(editView.ctrlIf2Name.getValue());
		department.setIfCode3(editView.ctrlIf3Code.getValue());
		department.setIfName3(editView.ctrlIf3Name.getValue());

		department.setUseIf1(isUseIf1);
		department.setUseIf2(isUseIf2);
		department.setUseIf3(isUseIf3);

		return department;

	}

	/**
	 * @return ����
	 */
	protected Department createDepartment() {
		return new Department();
	}

	/**
	 * ��������ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param department ������
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ������
	 */
	protected List<Object> getRowData(Department department) {
		List<Object> list = new ArrayList<Object>();

		list.add(department.getCode()); // ����R�[�h
		list.add(department.getName()); // ���喼��
		list.add(department.getNames()); // ���嗪��
		list.add(department.getNamek()); // ���匟������
		list.add(NumberFormatUtil.formatNumber(department.getPersonnel1())); // �l��1
		list.add(NumberFormatUtil.formatNumber(department.getPersonnel2())); // �l��2
		list.add(NumberFormatUtil.formatNumber(department.getFloorSpace(), 2)); // ���ʐ�
		list.add(department.isSumDepartment() ? getWord("C00255") : getWord("C01275")); // ����敪
		list.add(DateUtil.toYMDString(department.getDateFrom())); // �J�n�N����
		list.add(DateUtil.toYMDString(department.getDateTo())); // �I���N����

		list.add(department.getIfCode1()); // �A�g�R�[�h1
		list.add(department.getIfName1()); // �A�g����1
		list.add(department.getIfCode2()); // �A�g�R�[�h2
		list.add(department.getIfName2()); // �A�g����2
		list.add(department.getIfCode3()); // �A�g�R�[�h3
		list.add(department.getIfName3()); // �A�g����3

		return list;
	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param mainEnabled enabled
	 */
	protected void setMainButtonEnabled(boolean mainEnabled) {
		mainView.btnModify.setEnabled(mainEnabled);
		mainView.btnCopy.setEnabled(mainEnabled);
		mainView.btnDelete.setEnabled(mainEnabled);
	}

	/**
	 * ���������ɊY�����镔��̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�����镔��̃��X�g
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Department> getDepartment(DepartmentSearchCondition condition) throws Exception {

		List<Department> list = (List<Department>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * �w����ʂœ��͂��ꂽ����̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected DepartmentSearchCondition getSearchCondition() {

		DepartmentSearchCondition condition = createCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.departmentRange.getCodeFrom());
		condition.setCodeTo(mainView.departmentRange.getCodeTo());
		condition.setSumDepartment(true);
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		condition.setUseIf1(isUseIf1);
		condition.setUseIf2(isUseIf2);
		condition.setUseIf3(isUseIf3);

		return condition;

	}

	/**
	 * @return ��������
	 */
	protected DepartmentSearchCondition createCondition() {
		return new DepartmentSearchCondition();
	}

	/**
	 * �ꗗ�őI������Ă��镔���Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă��镔��
	 * @throws Exception
	 */
	protected Department getSelectedDepartment() throws Exception {

		DepartmentSearchCondition condition = createCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0060DepartmentMasterPanel.SC.code));
		condition.setSumDepartment(true);

		condition.setUseIf1(isUseIf1);
		condition.setUseIf2(isUseIf2);
		condition.setUseIf3(isUseIf3);

		List<Department> list = getDepartment(condition);

		if (list == null || list.isEmpty()) {
			throw new TException("I00193"); // �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
		}
		return list.get(0);
	}

	/**
	 * �w��̕�����폜����
	 * 
	 * @param department ����
	 * @throws TException
	 */
	protected void deleteDepartment(Department department) throws TException {
		request(getModelClass(), "delete", department);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// ����R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDepartmentCode.getValue())) {
			showMessage(editView, "I00037", "C00698"); // ����R�[�h����͂��Ă��������B
			editView.ctrlDepartmentCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓��ꕔ�傪���ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			DepartmentSearchCondition condition = createCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlDepartmentCode.getValue());
			condition.setSumDepartment(true);

			List<Department> list = getDepartment(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00467"); // �w��̕���͊��ɑ��݂��܂��B
				editView.ctrlDepartmentCode.requestTextFocus();
				return false;
			}
		}

		// ���喼�̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDepartmentName.getValue())) {
			showMessage(editView, "I00037", "C00723"); // ���喼�̂���͂��Ă��������B
			editView.ctrlDepartmentName.requestTextFocus();
			return false;
		}

		// ���嗪�̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDepartmentNames.getValue())) {
			showMessage(editView, "I00037", "C00724"); // ���嗪�̂���͂��Ă��������B
			editView.ctrlDepartmentNames.requestTextFocus();
			return false;
		}

		// ���匟�����̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDepartmentNamek.getValue())) {
			showMessage(editView, "I00037", "C00725"); // ���匟�����̂���͂��Ă��������B
			editView.ctrlDepartmentNamek.requestTextFocus();
			return false;
		}

		// �l����1�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDepartmentMen1.getValue())) {
			showMessage(editView, "I00037", "C00726"); // �l����1����͂��Ă��������B
			editView.ctrlDepartmentMen1.requestTextFocus();
			return false;
		}

		// �l����2�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDepartmentMen2.getValue())) {
			showMessage(editView, "I00037", "C00727"); // �l����2����͂��Ă��������B
			editView.ctrlDepartmentMen2.requestTextFocus();
			return false;
		}

		// ���ʐς������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDepartmentArea.getValue())) {
			showMessage(editView, "I00037", "C00728"); // ���ʐς���͂��Ă��������B
			editView.ctrlDepartmentArea.requestTextFocus();
			return false;
		}

		// �J�n�N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055"); // �J�n�N��������͂��Ă��������B
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261"); // �I���N��������͂��Ă��������B
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		// �J�n�N����,�I���N�����`�F�b�N
		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}

		if (isUseIf1 && !Util.isNullOrEmpty(editView.ctrlIf1Code.getValue())) {
			String txt = StringUtil.leftBX(editView.ctrlIf1Code.getValue(), 1);
			if (Util.equals(txt, "0")) {
				showMessage(editView, "I00259", "C12060", editView.ctrlIf1Code.getValue());
				editView.ctrlIf1Code.requestFocus();
				return false;
			}
		}
		if (isUseIf2 && !Util.isNullOrEmpty(editView.ctrlIf2Code.getValue())) {
			String txt = StringUtil.leftBX(editView.ctrlIf2Code.getValue(), 1);
			if (Util.equals(txt, "0")) {
				showMessage(editView, "I00259", "C12062", editView.ctrlIf2Code.getValue());
				editView.ctrlIf2Code.requestFocus();
				return false;
			}
		}
		if (isUseIf3 && !Util.isNullOrEmpty(editView.ctrlIf3Code.getValue())) {
			String txt = StringUtil.leftBX(editView.ctrlIf3Code.getValue(), 1);
			if (Util.equals(txt, "0")) {
				showMessage(editView, "I00259", "C12064", editView.ctrlIf3Code.getValue());
				editView.ctrlIf3Code.requestFocus();
				return false;
			}
		}

		return true;

	}
}
