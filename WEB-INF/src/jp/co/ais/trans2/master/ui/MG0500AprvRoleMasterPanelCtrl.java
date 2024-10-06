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
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.aprvrole.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ���F�������[���}�X�^�R���g���[��
 */
public class MG0500AprvRoleMasterPanelCtrl extends TController {

	/** �w����� */
	protected MG0500AprvRoleMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0500AprvRoleMasterDialog editView = null;

	/**
	 * ���[�U�[���X�g
	 */
	protected List<User> editUserList = null;

	/**
	 * ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B
	 */
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
		mainView = new MG0500AprvRoleMasterPanel(getCompany());
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃{�^���������̃C�x���g
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
			editView.setLocationRelativeTo(null);// �����ɕ\��������
			editView.setVisible(true);// �\����K�p

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
			if (Util.isSmallerThen(mainView.ctrlSearch.getCodeFrom(), mainView.ctrlSearch.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlSearch.getFieldFrom().requestFocus();
				return;
			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// �����������擾���A��������Ƃɏ��F�������[�������擾
			AprvRoleSearchCondition condition = getSearchCondition();
			List<AprvRole> list = get(condition);

			// ���������ɊY�����鏳�F�������[�������݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage("I00022");
				return;
			}

			// ���F�������[�������ꗗ�ɕ\������
			for (AprvRole bean : list) {
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

			// �ҏW�Ώۂ̃R�[�h���擾����
			AprvRole bean = getSelected();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̏��F�������[�������Z�b�g����
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

			// ���ʑΏۂ̏��F�������[�����擾����
			AprvRole bean = getSelected();
			// ���ʉ�ʂ𐶐����A���ʑΏۂ̏��F�������[�������Z�b�g����
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
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {
				return;
			}

			// ���F�������[�������擾
			AprvRole bean = getSelected();

			// �폜
			request(getModelClass(), "delete", bean);

			// �폜�����ωוi���ꗗ����폜
			mainView.tbl.removeSelectedRow();

			// �������b�Z�[�W
			showMessage(mainView, "I00013");

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

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// �f�[�^���o
			AprvRoleSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage("I00022");
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("���F�������[���}�X�^") + ".xls"); // ���F�������[���}�X�^

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0500AprvRoleMasterDialog(getCompany(), mainView.getParentFrame(), true);

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

		// [��(�v���O�����ǉ�)]�{�^������
		editView.btnAdd.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnAdd_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [��(�v���O�����ǉ�)]�{�^������
		editView.btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCancel_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * [�ꗗ����I��]�{�^������
	 */
	protected void btnAdd_Click() {

		int selectedRows[] = editView.tblRight.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String userCode = (String) editView.tblRight.getRowValueAt(selectedRows[i],
				MG0500AprvRoleMasterDialog.SC.userCode);
			String userNames = (String) editView.tblRight.getRowValueAt(selectedRows[i],
				MG0500AprvRoleMasterDialog.SC.userName);
			String depNames = (String) editView.tblRight.getRowValueAt(selectedRows[i],
				MG0500AprvRoleMasterDialog.SC.depName);

			editView.tblLeft.addRow(new Object[] { userCode, userNames, depNames });
		}

		// �I��������폜
		editView.tblRight.removeSelectedRows();

	}

	/**
	 * [�ꗗ����I�������]�{�^������
	 */
	protected void btnCancel_Click() {

		int selectedRows[] = editView.tblLeft.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String userCode = (String) editView.tblLeft.getRowValueAt(selectedRows[i],
				MG0500AprvRoleMasterDialog.SC.userCode);
			String userNames = (String) editView.tblLeft.getRowValueAt(selectedRows[i],
				MG0500AprvRoleMasterDialog.SC.userName);
			String depNames = (String) editView.tblLeft.getRowValueAt(selectedRows[i],
				MG0500AprvRoleMasterDialog.SC.depName);

			editView.tblRight.addRow(new Object[] { userCode, userNames, depNames });
		}

		// �I��悩��폜
		editView.tblLeft.removeSelectedRows();

	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param mode_ ���샂�[�h�B
	 * @param bean ���F�������[���B�C���A���ʂ̏ꍇ�͓��Y���F�������[������ҏW��ʂɃZ�b�g����B
	 * @throws TException
	 */
	protected void initEditView(Mode mode_, AprvRole bean) throws TException {
		this.mode = mode_;
		editUserList = getUserList();
		// �V�K
		if (Mode.NEW == mode) {

			editView.setTitle(getWord("C01698"));
			editView.ctrlBeginDate.setValue(editView.ctrlBeginDate.getCalendar().getMinimumDate());
			editView.ctrlEndDate.setValue(editView.ctrlEndDate.getCalendar().getMaximumDate());
			// ���[�U�[�����ꗗ�ɒǉ�����
			for (User user : editUserList) {
				editView.tblRight.addRow(getRowData(user));
			}

		}

		else if (Mode.MODIFY == mode || Mode.COPY == mode) {

			// �ҏW
			if (Mode.MODIFY == mode) {
				editView.setTitle(getWord("C00977")); // �ҏW���
				// �ҏW��ʂŁA�ҏW�s�̐ݒ���s���B
				editView.ctrlRoleCode.setEditable(false);
				editView.setEditMode();

			} else {
				// ����
				editView.setTitle(getWord("C01738")); // ���ʉ��

			}
			editView.ctrlRoleCode.setValue(bean.getAPRV_ROLE_CODE());
			editView.ctrlRoleName.setValue(bean.getAPRV_ROLE_NAME());
			editView.ctrlRoleNames.setValue(bean.getAPRV_ROLE_NAME_S());
			editView.ctrlRoleNamek.setValue(bean.getAPRV_ROLE_NAME_K());
			editView.ctrlBeginDate.setValue(bean.getSTR_DATE());
			editView.ctrlEndDate.setValue(bean.getEND_DATE());

			// ���F�������[���}�X�^�ɓo�^���ꂦ�Ă��郆�[�U�[���ꗗ�ɕ\��
			for (User user : bean.getUserList()) {
				editView.tblLeft.addRow(getRowDataRole(user));
			}

			// ���[�U�[�����ꗗ�ɒǉ�����
			for (User editUser : editUserList) {
				boolean isExist = false;
				for (User user : bean.getUserList()) {
					if (Util.equals(editUser.getCode(), user.getCode())) {
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					editView.tblRight.addRow(getRowData(editUser));
				}

			}
		}

	}

	/**
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			// ���̓`�F�b�N���s���B
			if (!isDialogInputCheck()) {
				return;
			}

			// ���͂��ꂽ���F�������[�������擾
			List<AprvRole> usrList = getInputed();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entry", usrList);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(usrList.get(0)));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);
				mainView.tbl.setRowSelection(0);
				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", usrList);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(usrList.get(0)));

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
	 * @return AprvRoleManager
	 */
	protected Class getModelClass() {
		return AprvRoleManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ���F�������[������Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ���F�������[�����
	 */
	protected List<AprvRole> getInputed() {
		List<AprvRole> usrList = new ArrayList<AprvRole>();
		for (int row = 0; row < editView.tblLeft.getRowCount(); row++) {
			AprvRole bean = new AprvRole();
			bean.setCompanyCode(super.getCompanyCode());// ��ЃR�[�h
			bean.setAPRV_ROLE_CODE(editView.ctrlRoleCode.getValue());// ���[���R�[�h
			bean.setAPRV_ROLE_NAME(editView.ctrlRoleName.getValue());// ���[������
			bean.setAPRV_ROLE_NAME_S(editView.ctrlRoleNames.getValue());// ���[������
			bean.setAPRV_ROLE_NAME_K(editView.ctrlRoleNamek.getValue());// ���[����������
			bean.setUSR_CODE((String) editView.tblLeft.getRowValueAt(row, MG0500AprvRoleMasterDialog.SC.userCode));// �o�����F
			bean.setSTR_DATE(editView.ctrlBeginDate.getValue()); // �J�n�N����
			bean.setEND_DATE(editView.ctrlEndDate.getValue()); // �I���N����

			usrList.add(bean);
		}
		return usrList;
	}

	/**
	 * @param bean
	 * @return �f�[�^�擾
	 */
	protected List<Object> getRowData(AprvRole bean) {
		List<Object> list = new ArrayList<Object>();

		list.add(bean);
		list.add(bean.getAPRV_ROLE_CODE()); // ���[���R�[�h
		list.add(bean.getAPRV_ROLE_NAME()); // ���[������
		list.add(bean.getAPRV_ROLE_NAME_S()); // ���[������
		list.add(bean.getAPRV_ROLE_NAME_K()); // ���[����������
		list.add(DateUtil.toYMDString(bean.getSTR_DATE())); // �J�n�N����
		list.add(DateUtil.toYMDString(bean.getEND_DATE())); // �I���N����

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
	 * ���������ɊY������R�[�h�̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY������R�[�h���X�g
	 * @throws Exception
	 */
	protected List<AprvRole> get(AprvRoleSearchCondition condition) throws Exception {
		condition.setCompanyCode(getCompanyCode());
		List<AprvRole> list = (List<AprvRole>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * ���[�U�[�����擾����
	 * 
	 * @return ���[�U�[
	 * @throws TException
	 */
	protected List<User> getUserList() throws TException {

		UserSearchCondition condition = new UserSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());

		List<User> list = (List<User>) request(UserManager.class, "get", condition);

		return list;

	}

	/**
	 * �V�K�p���[�U�[�����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param user ���[�U�[
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�v���O����
	 */
	protected String[] getRowData(User user) {
		return new String[] { user.getCode(), user.getNames(), user.getDepartment().getNames() };
	}

	/**
	 * �ҏW�p���[�U�[�����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param role
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�v���O����
	 */
	protected String[] getRowDataRole(User role) {
		return new String[] { role.getCode(), role.getNames(), role.getDepartment().getNames() };
	}

	/**
	 * �w����ʂœ��͂��ꂽ���F�������[���}�X�^�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected AprvRoleSearchCondition getSearchCondition() {

		AprvRoleSearchCondition condition = new AprvRoleSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.ctrlSearch.getCodeFrom());
		condition.setCodeTo(mainView.ctrlSearch.getCodeTo());

		if (!mainView.ctrlOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;
	}

	/**
	 * �ꗗ�őI������Ă��鏳�F�������[���R�[�h��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă��鏳�F�������[���R�[�h
	 * @throws Exception
	 */
	protected AprvRole getSelected() throws Exception {

		AprvRoleSearchCondition condition = new AprvRoleSearchCondition();
		AprvRole role = (AprvRole) mainView.tbl.getSelectedRowValueAt(MG0500AprvRoleMasterPanel.SC.bean);
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(role.getAPRV_ROLE_CODE());

		List<AprvRole> list = get(condition);

		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂���
			throw new TException("I00193");
		}
		return list.get(0);
	}

	/**
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isDialogInputCheck() throws Exception {

		// ���F�������[���R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlRoleCode.getValue())) {
			showMessage(editView, "I00037", "C11154"); // ���[���R�[�h����͂��Ă��������B
			editView.ctrlRoleCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓��ꏳ�F�������[���R�[�h�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			AprvRoleSearchCondition condition = new AprvRoleSearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlRoleCode.getValue());

			List<AprvRole> list = get(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C11154"); // �w��̃��[���R�[�h�͊��ɑ��݂��܂��B
				editView.ctrlRoleCode.requestTextFocus();
				return false;
			}
		}

		// ���F�������[�����̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlRoleName.getValue())) {
			showMessage(editView, "I00037", "C11155"); // ���[�����̂���͂��Ă��������B
			editView.ctrlRoleName.requestTextFocus();
			return false;
		}

		// ���F�������[�����̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlRoleNames.getValue())) {
			showMessage(editView, "I00037", "C11156"); // ���F�������[�����̂���͂��Ă��������B
			editView.ctrlRoleNames.requestTextFocus();
			return false;
		}

		// ���F�������[���������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlRoleNamek.getValue())) {
			showMessage(editView, "I00037", "C11157"); // ���F�������[���������̂���͂��Ă��������B
			editView.ctrlRoleNamek.requestTextFocus();
			return false;
		}

		// �J�n�N����
		if (Util.isNullOrEmpty(editView.ctrlBeginDate.getValue())) {
			showMessage(editView, "I00037", editView.ctrlBeginDate.getLabelText()); // �J�n�N��������͂��Ă�������
			editView.ctrlBeginDate.requestFocus();
			return false;
		}

		// �I���N����
		if (Util.isNullOrEmpty(editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00037", editView.ctrlEndDate.getLabelText()); // �I���N��������͂��Ă�������
			editView.ctrlEndDate.requestFocus();
			return false;
		}

		// �J�n�N�������I���N�����̏ꍇ�G���[
		if (!Util.isSmallerThenByYMD(editView.ctrlBeginDate.getValue(), editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00067"); // �J�n�N���� <= �I���N�����ɂ��Ă��������B
			editView.ctrlBeginDate.requestTextFocus();
			return false;
		}

		// ���[�U�[�����I���̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.tblLeft.getRowCount()) || editView.tblLeft.getRowCount() == 0) {
			showMessage(editView, "I00041", "C00528"); // ���[�U�[��I�����Ă��������B
			editView.ctrlRoleNamek.requestTextFocus();
			return false;
		}
		return true;
	}

}
