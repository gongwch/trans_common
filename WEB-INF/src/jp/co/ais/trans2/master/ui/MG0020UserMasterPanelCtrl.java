package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.slip.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.ui.MG0020UserMasterPanel.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ���[�U�[�}�X�^�̃R���g���[��
 * 
 * @author AIS
 */
public class MG0020UserMasterPanelCtrl extends TController {

	/** �w����� */
	protected MG0020UserMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0020UserMasterDialog editView = null;

	/** �Y�t�t�@�C�� */
	protected USR_SIGN attachments = null;

	/** �ő�t�@�C���T�C�Y(MB) */
	public static int maxFileSize = 4;

	/** �g���q */
	public static String[] supportFileExts = null;

	/** �V�X�e���敪�ꗗ */
	public static String[] sysKbnList = null;

	static {
		try {
			maxFileSize = Util.avoidNullAsInt(ClientConfig.getProperty("trans.usr.sign.use.attachment.max.size"));
		} catch (Throwable e) {
			// �����Ȃ�
		}

		try {
			supportFileExts = ClientConfig.getProperties("trans.usr.sign.attachment.support.file.exts");
		} catch (Throwable e) {
			supportFileExts = new String[] { "gif", "jpg", "jpeg", "png" };
		}
		try {
			sysKbnList = ClientConfig.getProperties("trans.user.master.syskbn.code.list");
		} catch (Throwable e) {
			// �����Ȃ�
		}
	}

	/**
	 * ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B
	 */
	protected Mode mode = null;

	/**
	 * ���샂�[�h�B
	 * 
	 * @author AIS
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
		mainView = new MG0020UserMasterPanel();
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
		
		initMainVisibleSetting();
	}

	/**
	 * �w����� �\���ݒ�̐ؑ�
	 */
	protected void initMainVisibleSetting() {
		if (!getCompany().getAccountConfig().isUseWorkflowApprove()) {
			mainView.tbl.setColumnWidth(SC.aprv_roleCode, -1);
			mainView.tbl.setColumnWidth(SC.aprv_roleNames, -1);
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

			// ��������
			UserSearchCondition condition = getSearchCondition();

			// �J�n <= �I���`�F�b�N
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				// �x�����b�Z�[�W�\��
				showMessage(mainView, "I00068");
				mainView.ctrlUserRange.ctrlUserReferenceFrom.code.requestFocus();
				return;
			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// ���[�U�[�����擾
			List<User> list = getUser(condition);

			// ���������ɊY�����郆�[�U�[�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ���[�U�[�����ꗗ�ɕ\������
			for (User user : list) {
				mainView.tbl.addRow(getRowData(user));
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

			// �ҏW�Ώۂ̃��[�U�[���擾����
			User user = getSelectedUser();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̃��[�U�[�����Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, user);

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

			// ���ʑΏۂ̃��[�U�[���擾����
			User user = getSelectedUser();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̃��[�U�[�����Z�b�g����
			createEditView();
			initEditView(Mode.COPY, user);

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

			// �폜�Ώۂ̃��[�U�[���擾����
			User user = getSelectedUser();

			// �폜����
			deleteUser(user);

			// �폜�������[�U�[���ꗗ����폜
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
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// �f�[�^���o
			UserSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			// ���[�U�[�}�X�^
			printer.preview(data, getWord("C02355") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0020UserMasterDialog(mainView.getParentFrame(), true);

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

		// SIGN�{�^��
		editView.btnSignAttach.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// SIGN�Y�t�ǉ�
				try {
					attachFile();
				} catch (IOException e1) {
					//
				}
			}
		});

		// SIGN�t�B�[���h��verify�C�x���g
		editView.ctrlSignAttach.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				try {

					// �l�̕ύX���Ȃ�
					if (!editView.ctrlSignAttach.isValueChanged()) {
						return true;
					}

					return checkFileName();

				} finally {
					editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}

		});

	}

	/**
	 * @return �ύX���ꂽ��
	 */
	protected boolean checkFileName() {
		String fileName = editView.ctrlSignAttach.getValue();
		if (Util.isNullOrEmpty(fileName)) {
			attachments = null;
			return true;
		}
		if (!Util.equals(attachments.getFILE_NAME(), editView.ctrlSignAttach.getValue())) {
			editView.ctrlSignAttach.setValue(null);
			attachments = null;
			return false;
		}
		return true;

	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param mode_ ���샂�[�h�B
	 * @param user ���[�U�[�B�C���A���ʂ̏ꍇ�͓��Y���[�U�[����ҏW��ʂɃZ�b�g����B
	 * @throws TException
	 */
	protected void initEditView(Mode mode_, User user) throws TException {

		this.mode = mode_;

		// �V�X�e���敪�\��
		if (MG0020UserMasterDialog.DASH_BOARD_CONDITION) {
			systemKbnTbl(mode_);
		}
		switchEditViewVisible();

		// �V�K�̏ꍇ
		if (Mode.NEW == mode_) {

			// �V�K���
			editView.setTitle(getWord("C01698"));

			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());

			return;

			// �ҏW�A���ʂ̏ꍇ
		} else if (Mode.MODIFY == mode_ || Mode.COPY == mode_) {

			// �ҏW
			if (Mode.MODIFY == mode_) {
				// �ҏW���
				editView.setTitle(getWord("C00977"));
				editView.ctrlCode.setEditable(false);
				editView.setEditMode();
				// ����
			} else {
				// ���ʉ��
				editView.setTitle(getWord("C01738"));
			}

			editView.ctrlCode.setValue(user.getCode());
			editView.ctrlName.setValue(user.getName());
			editView.ctrlNames.setValue(user.getNames());
			editView.ctrlNamek.setValue(user.getNamek());
			editView.ctrlPassword.setValue(user.getPassword());
			editView.ctrlProgramRole.setEntity(user.getProgramRole());
			editView.ctrlUserRole.setEntity(user.getUserRole());
			if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
				editView.ctrlAprvGroup.setEntity(user.getAprvRoleGroup());
			}
			if (MG0020UserMasterDialog.USE_BL_SIGNER) {
				editView.ctrlSignerDept.setValue(user.getSignerDept());
				editView.ctrlSignerTitle.setValue(user.getSignerTitle());
				editView.ctrlSignerName.setValue(user.getSignerName());
				editView.ctrlSignAttach.setValue(user.getSignFileName());
				attachments = user.getSignFile();
			}
			editView.ctrlEMailAddress.setValue(user.getEMailAddress());
			editView.cboUpdateAuthorityLevel.setSelectedItemValue(user.getSlipRole());
			editView.cboAccountingPerson.setSelectedItemValue(user.getUserAccountRole());
			editView.ctrlEmployeeReference.setEntity(user.getEmployee());
			editView.ctrlDepartmentReference.setEntity(user.getDepartment());
			editView.ctrlLangcode.setSelectedItemValue(user.getLanguage());
			editView.dtBeginDate.setValue(user.getDateFrom());
			editView.dtEndDate.setValue(user.getDateTo());

			// �A�N�Z�X����
			if (MG0020UserMasterDialog.USE_ACCESS_FLAG) {
				for (int i = 0; i < User.ACCESS_FLAG_COUNT; i++) {
					UserAccessFlag e = UserAccessFlag.get(user, i);
					editView.ctrlAccessPermission[i].setSelectedItemValue(e);
				}
			}
		}

	}

	/**
	 * �ҏW��ʃR���|�[�l���g�\���ؑ�
	 */
	protected void switchEditViewVisible() {
		editView.ctrlAprvGroup.setVisible(getCompany().getAccountConfig().isUseWorkflowApprove());
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

			// ���͂��ꂽ���[�U�[�����擾
			User user = getInputedUser();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entry", user);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(user));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", user);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(user));

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
	 * �w����ʂœ��͂��ꂽ���[�U�[�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected UserSearchCondition getSearchCondition() {

		UserSearchCondition condition = createUserSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCodeFrom(mainView.ctrlUserRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlUserRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return UserManager
	 */
	protected Class getModelClass() {
		return UserManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ���[�U�[��Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ���[�U�[
	 */
	protected User getInputedUser() {

		User user = createUser();
		user.setCompanyCode(getCompany().getCode());
		user.setCode(editView.ctrlCode.getValue());
		user.setName(editView.ctrlName.getValue());
		user.setNames(editView.ctrlNames.getValue());
		user.setNamek(editView.ctrlNamek.getValue());
		user.setPassword(editView.ctrlPassword.getValue());
		user.setProgramRole(editView.ctrlProgramRole.getEntity());
		user.setUserRole(editView.ctrlUserRole.getEntity() == null ? null : editView.ctrlUserRole.getEntity());
		user.setAprvRoleGroup(editView.ctrlAprvGroup.getEntity() == null ? null : editView.ctrlAprvGroup.getEntity());
		if (MG0020UserMasterDialog.USE_BL_SIGNER) {
			user.setSignerDept(editView.ctrlSignerDept.getValue());
			user.setSignerTitle(editView.ctrlSignerTitle.getValue());
			user.setSignerName(editView.ctrlSignerName.getValue());
			user.setSignFileName(editView.ctrlSignAttach.getValue());
			user.setSignFile(attachments);
		}
		user.setEMailAddress(editView.ctrlEMailAddress.getValue());
		user.setSlipRole((SlipRole) editView.cboUpdateAuthorityLevel.getSelectedItemValue());
		user.setUserAccountRole((UserAccountRole) editView.cboAccountingPerson.getSelectedItemValue());
		user.setEmployee(editView.ctrlEmployeeReference.getEntity());
		user.setDepartment(editView.ctrlDepartmentReference.getEntity());
		user.setLanguage(editView.ctrlLangcode.getSelectedItemValue());
		user.setLanguageName((String) editView.ctrlLangcode.getComboBox().getSelectedItem());
		user.setDateFrom(editView.dtBeginDate.getValue());
		user.setDateTo(editView.dtEndDate.getValue());

		if (MG0020UserMasterDialog.USE_ACCESS_FLAG) {
			int i = 0;
			user.setAccessPermissionFlag1(getAccessPermission(i++));
			user.setAccessPermissionFlag2(getAccessPermission(i++));
			user.setAccessPermissionFlag3(getAccessPermission(i++));
			user.setAccessPermissionFlag4(getAccessPermission(i++));
			user.setAccessPermissionFlag5(getAccessPermission(i++));
			user.setAccessPermissionFlag6(getAccessPermission(i++));
			user.setAccessPermissionFlag7(getAccessPermission(i++));
			user.setAccessPermissionFlag8(getAccessPermission(i++));
			user.setAccessPermissionFlag9(getAccessPermission(i++));
			user.setAccessPermissionFlag10(getAccessPermission(i++));
		}

		if (MG0020UserMasterDialog.DASH_BOARD_CONDITION) {

			List<USR_DASH_CTL> list = new ArrayList<USR_DASH_CTL>();
			for (int i = 0; editView.tblSysState.getRowCount() > i; i++) {

				USR_DASH_CTL bean = new USR_DASH_CTL();
				boolean usrKbn = (Boolean) editView.tblSysState.getRowValueAt(i, MG0020UserMasterDialog.SC.chk);
				String sysKbn = (String) editView.tblSysState.getRowValueAt(i, MG0020UserMasterDialog.SC.sysKbn);

				bean.setKAI_CODE(getCompany().getCode());
				bean.setUSR_CODE(editView.ctrlCode.getValue());
				bean.setSYS_KBN_CODE(sysKbn);
				bean.setUSE_KBN(usrKbn);
				list.add(bean);
			}
			user.setUSR_DASH_CTLList(list);
		}

		return user;

	}

	/**
	 * �A�N�Z�X�����̓o�^�l��Ԃ�
	 * 
	 * @param index
	 * @return �A�N�Z�X�����̓o�^�l
	 */
	protected int getAccessPermission(int index) {
		UserAccessFlag e = (UserAccessFlag) editView.ctrlAccessPermission[index].getSelectedItemValue();
		if (e == null) {
			return UserAccessFlag.NONE.value;
		}

		return e.value;
	}

	/**
	 * @return ���[�U��������
	 */
	protected UserSearchCondition createUserSearchCondition() {
		return new UserSearchCondition();
	}

	/**
	 * @return ���[�U
	 */
	protected User createUser() {
		return new User();
	}

	/**
	 * ���[�U�[�����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param user ���[�U�[
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ���[�U�[
	 */
	protected Object[] getRowData(User user) {

		Object[] row = new Object[24];
		row[SC.code.ordinal()] = user.getCode();
		row[SC.name.ordinal()] = user.getName();
		row[SC.names.ordinal()] = user.getNames();
		row[SC.namek.ordinal()] = user.getNamek();
		row[SC.program_roleCode.ordinal()] = user.getProgramRole().getCode();
		row[SC.program_roleNames.ordinal()] = user.getProgramRole().getNames();
		row[SC.user_roleCode.ordinal()] = (Util.isNullOrEmpty(user.getUserRole()) ? "" : user.getUserRole().getCode());
		row[SC.user_roleNames.ordinal()] = (Util.isNullOrEmpty(user.getUserRole()) ? ""
			: user.getUserRole().getNames());
		row[SC.aprv_roleCode.ordinal()] = (Util.isNullOrEmpty(user.getAprvRoleGroup()) ? ""
			: user.getAprvRoleGroup().getAPRV_ROLE_GRP_CODE());
		row[SC.aprv_roleNames.ordinal()] = (Util.isNullOrEmpty(user.getAprvRoleGroup()) ? ""
			: user.getAprvRoleGroup().getAPRV_ROLE_GRP_NAME_S());
		row[SC.signerDept.ordinal()] = user.getSignerDept();
		row[SC.signerTitle.ordinal()] = user.getSignerTitle();
		row[SC.signerName.ordinal()] = user.getSignerName();
		row[SC.signFileName.ordinal()] = user.getSignFileName();
		row[SC.eMailAddress.ordinal()] = user.getEMailAddress();
		row[SC.updateAuthority.ordinal()] = getWord(SlipRole.getSlipRoleName(user.getSlipRole()));
		row[SC.isAccountant.ordinal()] = getWord(UserAccountRole.getUserAccountRoleName(user.getUserAccountRole()));
		row[SC.employeeCode.ordinal()] = user.getEmployee().getCode();
		row[SC.employeeNames.ordinal()] = user.getEmployee().getNames();
		row[SC.departmentCode.ordinal()] = user.getDepartment().getCode();
		row[SC.departmentNames.ordinal()] = user.getDepartment().getNames();
		row[SC.language.ordinal()] = user.getLanguageName();
		row[SC.dateFrom.ordinal()] = DateUtil.toYMDString(user.getDateFrom());
		row[SC.dateTo.ordinal()] = DateUtil.toYMDString(user.getDateTo());

		return row;
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
	 * ���������ɊY�����郆�[�U�[�̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�����郆�[�U�[�̃��X�g
	 * @throws Exception
	 */
	protected List<User> getUser(UserSearchCondition condition) throws Exception {

		List<User> list = (List<User>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * �ꗗ�őI������Ă��郆�[�U�[��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă��郆�[�U�[
	 * @throws Exception
	 */
	protected User getSelectedUser() throws Exception {

		UserSearchCondition condition = createUserSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0020UserMasterPanel.SC.code));

		List<User> list = getUser(condition);

		if (list == null || list.isEmpty()) {
			throw new TException(getMessage("I00193"));
		}
		return list.get(0);

	}

	/**
	 * �w��̃��[�U�[���폜����
	 * 
	 * @param user ���[�U�[
	 * @throws Exception
	 */
	protected void deleteUser(User user) throws Exception {
		request(getModelClass(), "delete", user);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// ���[�U�[�R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			// ���[�U�[�R�[�h����͂��Ă��������B
			showMessage(editView, "I00037", "C00589");
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		String replacedCode = StringUtil.replaceToUnderscore(editView.ctrlCode.getValue());
		if ((Mode.NEW == mode || Mode.COPY == mode) && !Util.equals(editView.ctrlCode.getValue(), replacedCode)) {
			// {���[�U�[�R�[�h}�͉p����(0-9)�A������(a-z)�A�啶��(A-Z)�A�L��($, _)�ȊO�͎g�p�ł��܂���B
			showMessage(editView, "I00900", editView.ctrlCode.getLabelText());
			return false;
		}

		// ���[�U�[���̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlName.getValue())) {
			// ���[�U�[���̂���͂��Ă��������B
			showMessage(editView, "I00037", "C00691");
			editView.ctrlName.requestTextFocus();
			return false;
		}

		// ���[�U�[���̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlNames.getValue())) {
			// ���[�U�[���̂���͂��Ă��������B
			showMessage(editView, "I00037", "C00692");
			editView.ctrlNames.requestTextFocus();
			return false;
		}

		// ���[�U�[�������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlNamek.getValue())) {
			// ���[�U�[�������̂���͂��Ă��������B
			showMessage(editView, "I00037", "C00693");
			editView.ctrlNamek.requestTextFocus();
			return false;
		}

		// �p�X���[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlPassword.getValue())) {
			showMessage(editView, "I00139");
			editView.ctrlPassword.requestTextFocus();
			return false;
		}

		// �v���O�������[���R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlProgramRole.getCode())) {
			// �v���O�������[���R�[�h����͂��Ă��������B
			showMessage(editView, "I00037", "C11159");
			editView.ctrlProgramRole.requestFocus();
			return false;
		}
		// �\�����A���F�O���[�v�������͂̏ꍇ�G���[
		if (editView.ctrlAprvGroup.isVisible() && editView.ctrlAprvGroup.isEmpty()) {
			showMessage(editView, "I00037", editView.ctrlAprvGroup.btn.getText());
			editView.ctrlAprvGroup.requestFocus();
			return false;
		}
		// EMail Address�����[���A�h���X�`��(@.*)�ȊO�̏ꍇ�G���[
		if (!Util.isNullOrEmpty(editView.ctrlEMailAddress.getValue())) {
			String email = editView.ctrlEMailAddress.getValue();
			if (!checkMailAddressByRegularExpression(email)) {
				// ���������[���A�h���X����͂��Ă��������B
				showMessage(editView, "I00789");
				editView.ctrlEMailAddress.requestTextFocus();
				return false;
			}
		}

		// �Ј��R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlEmployeeReference.getCode())) {
			// �Ј��R�[�h����͂��Ă��������B
			showMessage(editView, "I00037", "C00697");
			editView.ctrlEmployeeReference.requestFocus();
			return false;
		}

		// ����R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDepartmentReference.getCode())) {
			// ����R�[�h����͂��Ă��������B
			showMessage(editView, "I00037", "C00698");
			editView.ctrlDepartmentReference.requestFocus();
			return false;
		}

		// �J�n�N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			// �J�n�N��������͂��Ă��������B
			showMessage(editView, "I00037", "C00055");
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			// �I���N��������͂��Ă��������B
			showMessage(editView, "I00037", "C00261");
			editView.dtEndDate.requestTextFocus();
			return false;
		}
		// �J�n�N����,�I���N�����`�F�b�N
		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}
		// ���F�����ݒ莞�K�{
		if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
			if (editView.ctrlAprvGroup.isEmpty()) {
				showMessage(editView, "I00037", editView.ctrlAprvGroup.btn.getLangMessageID());
				editView.ctrlAprvGroup.requestTextFocus();
				return false;
			}
		}

		// �V�K�A���ʂ̏ꍇ�͓��ꃆ�[�U�[�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			UserSearchCondition condition = createUserSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlCode.getValue());

			List<User> list = getUser(condition);

			if (list != null && !list.isEmpty()) {
				// �w��̃��[�U�[�͊��ɑ��݂��܂��B
				showMessage(editView, "I00229");
				editView.ctrlCode.requestTextFocus();
				return false;
			}
		}

		return true;

	}

	/**
	 * ���[���A�h���X�`�F�b�N
	 * 
	 * @param address �`�F�b�N�Ώۂ̃A�h���X
	 * @return ���������[���A�h���X��true *@*.*�`���ȊO��false
	 */
	public static boolean checkMailAddressByRegularExpression(String address) {

		String aText = "[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^\\$\\-\\|]";
		String dotAtom = aText + "+" + "(\\." + aText + "+)*";
		String regularExpression = "^" + dotAtom + "@" + dotAtom + "$";
		Pattern pattern = Pattern.compile(regularExpression);
		Matcher matcher = pattern.matcher(address);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	/**
	 * �Y�t�G���e�B�e�B�̍쐬
	 * 
	 * @param filename
	 * @param file
	 * @return �Y�t
	 */
	protected USR_SIGN createEntity(String filename, File file) {
		USR_SIGN bean = createBean();
		if (!Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			bean.setUSR_CODE(editView.ctrlCode.getValue());
		}
		bean.setINP_DATE(Util.getCurrentDate()); // �V�K
		bean.setFILE_NAME(filename);
		bean.setFile(file);

		// �K�{���
		bean.setKAI_CODE(getCompanyCode());
		bean.setPRG_ID(getProgramCode());
		bean.setUSR_ID(getUserCode());

		return bean;
	}

	/**
	 * @return �G���e�B�e�B�쐬
	 */
	protected USR_SIGN createBean() {
		return new USR_SIGN();
	}

	/**
	 * �t�@�C���ǉ�
	 * 
	 * @throws IOException
	 */
	protected void attachFile() throws IOException {
		// �t�@�C���I���_�C�A���O���J��
		TFileChooser fc = new TFileChooser();
		fc.setAcceptAllFileFilterUsed(false); // �S�Ẵt�@�C���͑I��s��
		fc.setMultiSelectionEnabled(true); // �����I���\

		File dir = getPreviousFolder(".chooser");

		// �O��̃t�H���_�𕜌�
		if (dir != null) {
			fc.setCurrentDirectory(dir);
		}

		// �Y�t�t�@�C���t�B���^�[
		TFileFilter filter = new TFileFilter(getWord("C11613"), supportFileExts);

		fc.setFileFilter(filter);
		if (TFileChooser.FileStatus.Selected != fc.show(editView)) {
			return;
		}

		TFile[] tFiles = fc.getSelectedTFiles();

		// �w��t�@�C����Y�t����
		addAllFiles(tFiles);
	}

	/**
	 * @return FolderKeyName��߂��܂��B
	 */
	protected String getFolderKeyName() {
		return "usr.sign.attachment";
	}

	/**
	 * �O��ۑ������t�H���_����Ԃ��B
	 * 
	 * @param type ���
	 * @return �O��ۑ������t�H���_���
	 */
	protected File getPreviousFolder(String type) {

		String path = SystemUtil.getAisSettingDir();
		File dir = (File) FileUtil.getObject(path + File.separator + getFolderKeyName() + type);

		return dir;
	}

	/**
	 * �w��̃t�H���_����ۑ�����
	 * 
	 * @param dir �t�H���_���
	 */
	protected void saveFolder(File dir) {
		String path = SystemUtil.getAisSettingDir();
		FileUtil.saveObject(dir, path + File.separator + getFolderKeyName() + ".chooser");
	}

	/**
	 * �w��t�@�C����Y�t����
	 * 
	 * @param tFiles
	 * @return true �ǉ�����
	 * @throws IOException
	 */
	protected boolean addAllFiles(TFile[] tFiles) throws IOException {
		if (tFiles == null || tFiles.length == 0) {
			return false;
		}

		File[] files = new File[tFiles.length];
		for (int i = 0; i < tFiles.length; i++) {
			files[i] = tFiles[i].getFile();
		}

		return addAllFiles(files);
	}

	/**
	 * �w��t�@�C����Y�t����
	 * 
	 * @param tFiles
	 * @return true �ǉ�����
	 * @throws IOException
	 */
	protected boolean addAllFiles(File[] tFiles) throws IOException {
		if (tFiles == null || tFiles.length == 0) {
			return false;
		}

		for (int i = 0; i < tFiles.length; i++) {
			File file = tFiles[i];

			String path = file.getPath();
			String filename = file.getName();

			byte[] data = null;

			try {
				data = ResourceUtil.zipBinary(filename, ResourceUtil.readBinarry(path));
			} catch (FileNotFoundException e) {
				showMessage(e.getMessage());
				return false;
			}

			if (data != null && data.length > 1024 * 1024 * TAttachButtonCtrl.maxFileSize) {
				// �t�@�C���T�C�Y��4MB�𒴂��Ă��܂��B
				showMessage("I00446", TAttachButtonCtrl.maxFileSize);
				return false;
			}

			if (data == null) {
				// �t�@�C�������݂��ĂȂ��ꍇ�A�����s�v
				return false;
			}

            // ���ꕄ���`�F�b�N
            String chk = new String(filename.getBytes(), "SHIFT-JIS");
            if (chk.contains("?")) { 
                String c = chk.replaceAll("\\?", "<b><font color=red>?</font></b>");
                // "<html>�t�@�C�����ɓ��ꕶ�����܂܂�Ă��܂��B<br>" + c + "<html>"
                showMessage("I01135", c);
                return false;
            }

			addFile(filename, file);

			if (i == 0) {
				// �t�H���_�L��
				saveFolder(file);
			}
		}

		return true;
	}

	/**
	 * @param filename
	 * @param file
	 */
	protected void addFile(String filename, File file) {

		attachments = createEntity(filename, file);
		editView.ctrlSignAttach.setValue(filename);
		setData(attachments);

	}

	/**
	 * �f�[�^�ݒ�
	 * 
	 * @param bean
	 */
	public void setData(USR_SIGN bean) {

		// ���[�J���ɕۑ�����
		try {
			TPrinter printer = new TPrinter(true);

			if (bean.getFileData() != null) {
				byte[] data = (byte[]) bean.getFileData();

				String tempFileName = printer.createResultFile(bean.getFILE_NAME(), data);
				File file = new File(tempFileName);
				bean.setFile(file);
				bean.setFileData(null); // �o�C�i�����N���A
			}
		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * �V�X�e���敪�̃e�[�u���\��
	 * 
	 * @param mode_
	 * @throws TException
	 */
	public void systemKbnTbl(Mode mode_) throws TException {
		List<String> sysList = Arrays.asList(sysKbnList);
		UserSearchCondition condition = createUserSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode("");
		if (Mode.MODIFY == mode_ || Mode.COPY == mode_) {
			condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0020UserMasterPanel.SC.code));
		}
		condition.setSysKbnList(sysList);

		List<USR_DASH_CTL> list = (List<USR_DASH_CTL>) request(getModelClass(), "getDashBoardSysKbn", condition);

		for (USR_DASH_CTL bean : list) {
			editView.tblSysState.addRow(systemListAdd(bean));
		}

	}

	/**
	 * �V�X�e���敪�ꗗ�e�[�u����add
	 * 
	 * @param bean
	 * @return list
	 */
	public List<Object> systemListAdd(USR_DASH_CTL bean) {

		List<Object> list = new ArrayList<Object>();

		list.add(bean.getSYS_KBN_CODE());
		list.add(bean.isUSE_KBN());
		list.add(bean.getSYS_KBN_NAME());

		return list;

	}
}
