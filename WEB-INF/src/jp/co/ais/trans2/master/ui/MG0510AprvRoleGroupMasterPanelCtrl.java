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
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.master.ui.MG0510AprvRoleGroupMasterDialog.*;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * ���F�������[���O���[�v�}�X�^�R���g���[��
 */
public class MG0510AprvRoleGroupMasterPanelCtrl extends TController {

	/** �w����� */
	protected MG0510AprvRoleGroupMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0510AprvRoleGroupMasterDialog editView = null;

	/** �S���[�����X�g */
	protected List<AprvRole> allRoleList = null;

	/** �ҏW��Bean */
	protected AprvRoleGroup editBean = null;

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
		mainView = new MG0510AprvRoleGroupMasterPanel(getCompany());
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
			AprvRoleGroupSearchCondition condition = getSearchCondition();
			List<AprvRoleGroup> list = get(condition);

			// ���������ɊY�����鏳�F�������[�������݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage("I00022");
				return;
			}

			// ���F�������[�������ꗗ�ɕ\������
			for (AprvRoleGroup bean : list) {
				mainView.tbl.addRow(convertToMainRow(bean));
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
			AprvRoleGroup bean = getSelected();

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
			AprvRoleGroup bean = getSelected();
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
			AprvRoleGroup bean = getSelected();

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
			AprvRoleGroupSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage("I00022");
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C12229") + ".xls"); // ���F�O���[�v

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0510AprvRoleGroupMasterDialog(getCompany(), mainView.getParentFrame(), true);

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

		// [��(�ǉ�)]�{�^������
		editView.btnAdd.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnAdd_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [��(�폜�ǉ�)]�{�^������
		editView.btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCancel_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// �����e�[�u���A�_�v�^
		editView.tblLeft.setAdapter(new TTableAdapter() {

			@Override
			public void rowSelectionChanged() {
				afterLeftTableRowSelectionChange();
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditLeftCellEditable(row, column);
			}

		});
		// �s���{�^��
		editView.btnUp.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnUp_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

		});
		// �s���{�^��
		editView.btnDown.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDown_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

		});
	}

	/**
	 * �ҏW��ʍ��� �Z���̕ҏW��
	 * 
	 * @param row
	 * @param column
	 * @return true:�ҏW�\
	 */
	protected boolean isEditLeftCellEditable(int row, int column) {
		int maxRow = editView.tblLeft.getRowCount() - 1;
		if (row < maxRow) {
			return true;
		}
		return false;
	}

	/**
	 * ���{�^���������̏���
	 */
	protected void btnUp_Click() {
		try {
			TTable tbl = editView.tblLeft;
			int selectedRow = tbl.getSelectedRow();
			if (selectedRow <= 0) {
				return;
			}
			int targetRow = selectedRow - 1;

			// �I���s�Ə�̍s�̍����ւ�
			tbl.moveRow(selectedRow, selectedRow, targetRow);
			// �Ώۍs��I��
			tbl.setRowSelection(targetRow);
		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * ���{�^���������̏���
	 */
	protected void btnDown_Click() {
		try {
			TTable tbl = editView.tblLeft;
			int selectedRow = tbl.getSelectedRow();
			if (selectedRow >= tbl.getRowCount() - 1) {
				return;
			}
			int targetRow = selectedRow + 1;

			// �I���s�Ə�̍s�̍����ւ�
			tbl.moveRow(selectedRow, selectedRow, targetRow);
			// �Ώۍs��I��
			tbl.setRowSelection(targetRow);
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW��� �����e�[�u���s�I��ύX���̏���
	 */
	protected void afterLeftTableRowSelectionChange() {
		switchEditLeftButtonEditable();
	}

	/**
	 * �ҏW��� �����e�[�u���{�^���̕ҏW�ېؑ�
	 */
	protected void switchEditLeftButtonEditable() {
		int[] selectedRows = editView.tblLeft.getSelectedRows();
		boolean isEnabled = false;
		boolean isTop = false;
		boolean isBottom = false;
		if (selectedRows == null || selectedRows.length == 0 || selectedRows.length > 1) {
			// �I�𖳂��������͑I���s������
			isEnabled = false;
		} else if (editView.tblLeft.isSelectedRow()) {
			isTop = editView.tblLeft.getSelectedRow() == 0;
			isBottom = editView.tblLeft.getSelectedRow() == editView.tblLeft.getRowCount() - 1;
			isEnabled = true;
		}
		editView.btnUp.setEnabled(isEnabled && !isTop);
		editView.btnDown.setEnabled(isEnabled && !isBottom);
	}

	/**
	 * [�ꗗ����I��]�{�^������
	 */
	protected void btnAdd_Click() {

		int selectedRows[] = editView.tblRight.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {
			Object bean = editView.tblRight.getRowValueAt(selectedRows[i], SC.bean);
			String userCode = (String) editView.tblRight.getRowValueAt(selectedRows[i], SC.code);
			String userNames = (String) editView.tblRight.getRowValueAt(selectedRows[i], SC.name);

			editView.tblLeft.addRow(new Object[] { bean, userCode, userNames, false });
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

			Object bean = editView.tblLeft.getRowValueAt(selectedRows[i], SC.bean);
			String userCode = (String) editView.tblLeft.getRowValueAt(selectedRows[i], SC.code);
			String userNames = (String) editView.tblLeft.getRowValueAt(selectedRows[i], SC.name);

			editView.tblRight.addRow(new Object[] { bean, userCode, userNames });
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
	protected void initEditView(Mode mode_, AprvRoleGroup bean) throws TException {
		this.mode = mode_;
		allRoleList = getRoleList();
		// �V�K
		if (Mode.NEW == mode) {

			editView.setTitle(getWord("C01698"));
			editView.ctrlBeginDate.setValue(editView.ctrlBeginDate.getCalendar().getMinimumDate());
			editView.ctrlEndDate.setValue(editView.ctrlEndDate.getCalendar().getMaximumDate());
			// ���[�U�[�����ꗗ�ɒǉ�����
			for (AprvRole user : allRoleList) {
				editView.tblRight.addRow(convertToEditRoleRow(user));
			}

		}

		else if (Mode.MODIFY == mode || Mode.COPY == mode) {

			// �ҏW
			if (Mode.MODIFY == mode) {
				editView.setTitle(getWord("C00977")); // �ҏW���
				// �ҏW��ʂŁA�ҏW�s�̐ݒ���s���B
				editView.ctrlGroupCode.setEditable(false);
				editView.setEditMode();

			} else {
				// ����
				editView.setTitle(getWord("C01738")); // ���ʉ��

			}
			editView.ctrlGroupCode.setValue(bean.getAPRV_ROLE_GRP_CODE());
			editView.ctrlGroupName.setValue(bean.getAPRV_ROLE_GRP_NAME());
			editView.ctrlGroupNames.setValue(bean.getAPRV_ROLE_GRP_NAME_S());
			editView.ctrlGroupNamek.setValue(bean.getAPRV_ROLE_GRP_NAME_K());
			editView.ctrlBeginDate.setValue(bean.getSTR_DATE());
			editView.ctrlEndDate.setValue(bean.getEND_DATE());

			// ���F�������[���O���[�v�}�X�^�ɓo�^���ꂦ�Ă��郆�[�U�[���ꗗ�ɕ\��
			List<AprvRoleGroupDetail> roleList = bean.getDetailList();
			for (AprvRoleGroupDetail user : roleList) {
				editView.tblLeft.addRow(convertToEditRoleRow(user));
			}

			// ���[�U�[�����ꗗ�ɒǉ�����
			for (AprvRole editUser : allRoleList) {
				boolean isExist = false;
				for (AprvRole user : roleList) {
					if (Util.equals(editUser.getAPRV_ROLE_CODE(), user.getAPRV_ROLE_CODE())) {
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					editView.tblRight.addRow(convertToEditRoleRow(editUser));
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
			AprvRoleGroup bean = getInputed();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {
				// �V�K�o�^
				request(getModelClass(), "entry", bean);
				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(convertToMainRow(bean));
				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);
				mainView.tbl.setRowSelection(0);
				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {
				// �C��
				request(getModelClass(), "modify", bean);
				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(convertToMainRow(bean));
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
		return AprvRoleGroupManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ���F�������[������Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ���F�������[�����
	 */
	protected AprvRoleGroup getInputed() {
		AprvRoleGroup bean = new AprvRoleGroup();
		bean.setKAI_CODE(getCompanyCode());// ��ЃR�[�h
		bean.setAPRV_ROLE_GRP_CODE(editView.ctrlGroupCode.getValue());// ���[���R�[�h
		bean.setAPRV_ROLE_GRP_NAME(editView.ctrlGroupName.getValue());// ���[������
		bean.setAPRV_ROLE_GRP_NAME_S(editView.ctrlGroupNames.getValue());// ���[������
		bean.setAPRV_ROLE_GRP_NAME_K(editView.ctrlGroupNamek.getValue());// ���[����������
		bean.setSTR_DATE(editView.ctrlBeginDate.getValue()); // �J�n�N����
		bean.setEND_DATE(editView.ctrlEndDate.getValue()); // �I���N����
		List<AprvRoleGroupDetail> list = new ArrayList();
		bean.setDetailList(list);
		for (int row = 0; row < editView.tblLeft.getRowCount(); row++) {
			AprvRoleGroupDetail dtl = new AprvRoleGroupDetail();
			dtl.setAPRV_ROLE_CODE((String) editView.tblLeft.getRowValueAt(row, SC.code));// �o�����F
			dtl.setAPRV_LEVEL(row + 1); // ���F�������x��
			list.add(dtl);
		}
		return bean;
	}

	/**
	 * @param bean
	 * @return �f�[�^�擾
	 */
	protected List<Object> convertToMainRow(AprvRoleGroup bean) {
		List<Object> list = new ArrayList<Object>();

		list.add(bean);
		list.add(bean.getAPRV_ROLE_GRP_CODE()); // ���[���R�[�h
		list.add(bean.getAPRV_ROLE_GRP_NAME()); // ���[������
		list.add(bean.getAPRV_ROLE_GRP_NAME_S()); // ���[������
		list.add(bean.getAPRV_ROLE_GRP_NAME_K()); // ���[����������
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
	protected List<AprvRoleGroup> get(AprvRoleGroupSearchCondition condition) throws Exception {
		condition.setCompanyCode(getCompanyCode());
		List<AprvRoleGroup> list = (List<AprvRoleGroup>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * ���[���[�����擾����
	 * 
	 * @return ���[�U�[
	 * @throws TException
	 */
	protected List<AprvRole> getRoleList() throws TException {

		AprvRoleSearchCondition condition = new AprvRoleSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		List<AprvRole> list = (List<AprvRole>) request(AprvRoleManager.class, "get", condition);
		return list;

	}

	/**
	 * ���[�������ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param role ���[�U�[
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�v���O����
	 */
	protected Object[] convertToEditRoleRow(AprvRole role) {
		return new Object[] { role, role.getAPRV_ROLE_CODE(), role.getAPRV_ROLE_NAME() };
	}

	/**
	 * �ҏW��ʎw��s�̏��F�������[���擾
	 * 
	 * @param isLeft
	 * @param row
	 * @return ���F�������[��
	 */
	protected AprvRole getEditRoleAt(boolean isLeft, int row) {
		if (isLeft) {
			return (AprvRole) editView.tblLeft.getValueAt(row, SC.bean);
		} else {
			return (AprvRole) editView.tblRight.getValueAt(row, SC.bean);
		}
	}

	/**
	 * �w����ʂœ��͂��ꂽ���F�������[���O���[�v�}�X�^�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected AprvRoleGroupSearchCondition getSearchCondition() {

		AprvRoleGroupSearchCondition condition = new AprvRoleGroupSearchCondition();
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
	protected AprvRoleGroup getSelected() throws Exception {

		AprvRoleGroupSearchCondition condition = new AprvRoleGroupSearchCondition();
		AprvRoleGroup role = (AprvRoleGroup) mainView.tbl.getSelectedRowValueAt(MG0510AprvRoleGroupMasterPanel.SC.bean);
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(role.getAPRV_ROLE_GRP_CODE());

		List<AprvRoleGroup> list = get(condition);

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
		if (Util.isNullOrEmpty(editView.ctrlGroupCode.getValue())) {
			showMessage(editView, "I00037", "C11154"); // ���[���R�[�h����͂��Ă��������B
			editView.ctrlGroupCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓��ꏳ�F�������[���R�[�h�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			AprvRoleGroupSearchCondition condition = new AprvRoleGroupSearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlGroupCode.getValue());

			List<AprvRoleGroup> list = get(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C11154"); // �w��̃��[���R�[�h�͊��ɑ��݂��܂��B
				editView.ctrlGroupCode.requestTextFocus();
				return false;
			}
		}

		// ���F�������[�����̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlGroupName.getValue())) {
			showMessage(editView, "I00037", "C11155"); // ���[�����̂���͂��Ă��������B
			editView.ctrlGroupName.requestTextFocus();
			return false;
		}

		// ���F�������[�����̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlGroupNames.getValue())) {
			showMessage(editView, "I00037", "C11156"); // ���F�������[�����̂���͂��Ă��������B
			editView.ctrlGroupNames.requestTextFocus();
			return false;
		}

		// ���F�������[���������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlGroupNamek.getValue())) {
			showMessage(editView, "I00037", "C11157"); // ���F�������[���������̂���͂��Ă��������B
			editView.ctrlGroupNamek.requestTextFocus();
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
			editView.ctrlGroupNamek.requestTextFocus();
			return false;
		}
		// // �o�����F���[�������݂��邩
		// boolean isExistsAccAprv = false;
		// // �o�����F����ԉ��ɑ��݂��邩
		// boolean isAccBottom = false;
		// for (int r = 0; r < editView.tblLeft.getRowCount(); r++) {
		// AprvRole role = getEditRoleAt(true, r);
		// if (role.isACCT_APRV_FLG()) {
		// isExistsAccAprv = true;
		// if (r == editView.tblLeft.getRowCount() - 1) {
		// isAccBottom = true;
		// }
		// }
		// }
		// if (!isExistsAccAprv) {
		// // �o�����F�s�����݂��Ȃ��ꍇ�G���[
		// showMessage(editView, "�o�����F�Ώۂ̃��[����K��1�w�肵�Ă��������B");
		// return false;
		// } else if (!isAccBottom) {
		// // �o�����F�s����ԉ��ł͂Ȃ��ꍇ�G���[
		// showMessage(editView, "�o�����F�Ώۂ̃��[���͕K����ԉ��ɔz�u���Ă��������B");
		// return false;
		// }
		return true;
	}

}
