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
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.program.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ���[�U�[���[���}�X�^ CTRL
 * 
 * @author AIS
 */
public class MG0270UserRoleMasterPanelCtrl extends TController {

	/**
	 * �w�����
	 */
	protected MG0270UserRoleMasterPanel mainView = null;

	/**
	 * �ҏW���
	 */
	protected MG0270UserRoleMasterDialog editView = null;

	/**
	 * ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B
	 */
	protected Mode mode = null;

	/**
	 * �C���O�̃f�[�^
	 */
	protected UserRole editBean = null;

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

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0270UserRoleMasterPanel();
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

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// �J�n�R�[�h<=�I���R�[�h�ɂ��Ă��������B
			if (Util.isSmallerThen(mainView.ctrlRange.getCodeFrom(), mainView.ctrlRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlRange.getFieldFrom().requestFocus();
				return;
			}

			// �v���O���������擾
			UserSearchCondition condition = getSearchCondition();
			List<UserRole> list = getList(condition);

			// ���������ɊY������v���O���������݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// �v���O���������ꗗ�ɕ\������
			for (UserRole bean : list) {
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

			// �ҏW�Ώۂ̃v���O�������擾����
			UserRole bean = getSelectedRowData();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̃v���O���������Z�b�g����
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

			// ���ʑΏۂ̃v���O�������擾����
			UserRole bean = getSelectedRowData();

			// �ҏW�O�f�[�^���폜
			editBean = null;

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̃v���O���������Z�b�g����
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

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜�Ώۂ̃v���O�������擾����
			UserRole bean = getSelectedRowData();

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
	 * [�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {

		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// �v���O���������擾
			UserSearchCondition condition = getSearchCondition();
			condition.setLang(super.getUser().getLanguage());

			// �f�[�^���o
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11164") + ".xls");// ���[�U�[���[���}�X�^

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0270UserRoleMasterDialog(getCompany(), mainView.getParentFrame(), true);

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
	 * @param editMode ���샂�[�h�B
	 * @param bean �v���O�����B�C���A���ʂ̏ꍇ�͓��Y�v���O��������ҏW��ʂɃZ�b�g����B
	 * @throws TException
	 */
	protected void initEditView(Mode editMode, UserRole bean) throws TException {

		this.mode = editMode;

		// �V�K�̏ꍇ
		if (Mode.NEW == editMode) {

			editView.setTitle(getWord("C01698"));// �V�K���
			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());
			editBean = null;// �ҏW�O�f�[�^���폜

			// �ҏW�A���ʂ̏ꍇ
		} else if (Mode.MODIFY == editMode || Mode.COPY == editMode) {

			// �ҏW
			if (Mode.MODIFY == editMode) {
				editView.setTitle(getWord("C00977"));// �ҏW���
				editView.ctrlProgramCode.setEditable(false);
				editView.setEditMode();
				editBean = bean;// �ҏW�O�f�[�^��ێ�
				// ����
			} else {
				editView.setTitle(getWord("C01738"));// ���ʉ��
				editBean = null;// �ҏW�O�f�[�^���폜
			}

			editView.ctrlProgramCode.setValue(bean.getCode());
			editView.ctrlProgramName.setValue(bean.getName());
			editView.ctrlProgramNames.setValue(bean.getNames());
			editView.ctrlProgramNamek.setValue(bean.getNamek());
			editView.dtBeginDate.setValue(bean.getTermFrom());
			editView.dtEndDate.setValue(bean.getTermTo());

		}

		// �ꗗ�ɕ\������f�[�^���擾
		List<Item> itemList = getItemList();
		List<Department> depList = getDepartmentList();

		// �ȖڊJ�����x���ꗗ�̐ݒ�
		for (Item data : itemList) {
			editView.itemTbl.addRow(getItemRow(data, bean));
		}

		// ����J�����x���ꗗ�̐ݒ�
		for (Department data : depList) {
			editView.depTbl.addRow(getDepRow(data, bean));
		}

	}

	/**
	 * �Ȗڈꗗ�ɐݒ肷��f�[�^���擾
	 * 
	 * @param bean
	 * @param usrBean
	 * @return �ݒ�f�[�^
	 */
	protected String[] getItemRow(Item bean, UserRole usrBean) {

		String div = "0";

		// �f�[�^�����݂���ꍇ�͈�v�f�[�^������
		if (usrBean != null) {

			// ��v����Ȗڂ̃f�[�^�����݂���ꍇ�A���̒l��D��
			for (RoleItemLevel lvl : usrBean.getItemLvlList()) {
				if (lvl.getItemCode().equals(bean.getCode())) {
					div = String.valueOf(lvl.getDepDivision());
					break;
				}
			}
		}

		return new String[] { div, bean.getCode(), bean.getNames() };
	}

	/**
	 * ����ꗗ�ɐݒ肷��f�[�^���擾
	 * 
	 * @param bean
	 * @param usrBean
	 * @return �ݒ�f�[�^
	 */
	protected List<Object> getDepRow(Department bean, UserRole usrBean) {

		boolean div = false;
		List<Object> addList = new ArrayList<Object>();

		// �f�[�^�����݂���ꍇ�͈�v�f�[�^������
		if (usrBean != null) {

			// ��v����Ȗڂ̃f�[�^�����݂���ꍇ�A���̒l��D��
			for (RoleDepartmentLevel lvl : usrBean.getDepLvlList()) {
				if (lvl.getDepCode().equals(bean.getCode())) {
					div = BooleanUtil.toBoolean(lvl.getDepDivision());
					break;
				}
			}
		}
		addList.add(div);
		addList.add(bean.getCode());
		addList.add(bean.getNames());

		return addList;
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

			// ���͂��ꂽ�v���O���������擾
			UserRole bean = getInputedData();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entry", bean);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(bean));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", bean);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(bean));

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
	 * �w����ʂœ��͂��ꂽ�v���O�����̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected UserSearchCondition getSearchCondition() {

		UserSearchCondition condition = new UserSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setCodeFrom(mainView.ctrlRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return �N���X
	 */
	protected Class getModelClass() {
		return UserRoleManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�v���O������Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�v���O����
	 */
	protected UserRole getInputedData() {

		UserRole bean = new UserRole();
		bean.setCompanyCode(TLoginInfo.getCompany().getCode());
		bean.setCode(editView.ctrlProgramCode.getValue());
		bean.setName(editView.ctrlProgramName.getValue());
		bean.setNames(editView.ctrlProgramNames.getValue());
		bean.setNamek(editView.ctrlProgramNamek.getValue());
		bean.setTermFrom(editView.dtBeginDate.getValue());
		bean.setTermTo(editView.dtEndDate.getValue());
		if (editBean != null) {
			bean.setInpDate(editBean.getInpDate());
		}
		bean.setUsrId(getUser().getCode());

		// �ȖڊJ�����x���̎擾
		for (int i = 0; i < editView.itemTbl.getRowCount(); i++) {

			// �ȖڃR�[�h�̐ݒ�
			RoleItemLevel lvl = new RoleItemLevel();
			lvl.setItemCode(Util.avoidNull(editView.itemTbl.getRowValueAt(i, MG0270UserRoleMasterDialog.SC.CODE)));

			// �J�����x���̐ݒ�
			String div = Util.avoidNull(editView.itemTbl.getRowValueAt(i, MG0270UserRoleMasterDialog.SC.DIVISION));
			lvl.setDepDivision(Integer.valueOf(div));

			bean.addItemLvlList(lvl);
		}

		// ����J�����x���̎擾
		for (int i = 0; i < editView.depTbl.getRowCount(); i++) {

			// ����R�[�h�̐ݒ�
			RoleDepartmentLevel lvl = new RoleDepartmentLevel();
			lvl.setDepCode(Util.avoidNull(editView.depTbl.getRowValueAt(i, MG0270UserRoleMasterDialog.SC.CODE)));

			// �J�����x���̐ݒ�
			boolean div = (Boolean) editView.depTbl.getRowValueAt(i, MG0270UserRoleMasterDialog.SC.DIVISION);
			lvl.setDepDivision(BooleanUtil.toInt(div));

			bean.addDepLvlList(lvl);
		}

		return bean;

	}

	/**
	 * �G���e�B�e�B���ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param bean �f�[�^
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�v���O����
	 */
	protected String[] getRowData(UserRole bean) {

		return new String[] { bean.getCode(), bean.getName(), bean.getNames(), bean.getNamek(),
				DateUtil.toYMDString(bean.getTermFrom()), DateUtil.toYMDString(bean.getTermTo()) };
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
	protected List<UserRole> getList(UserSearchCondition condition) throws Exception {

		List<UserRole> list = (List<UserRole>) request(getModelClass(), "get", condition);
		return list;

	}

	/**
	 * �ꗗ�őI������Ă���v���O������Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���v���O����
	 * @throws Exception
	 */
	protected UserRole getSelectedRowData() throws Exception {

		UserSearchCondition condition = new UserSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0270UserRoleMasterPanel.SC.code));

		List<UserRole> list = getList(condition);

		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂���
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * �w��̃v���O�������폜����
	 * 
	 * @param bean �}�X�^
	 * @throws Exception
	 */
	protected void doDelete(UserRole bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// �R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlProgramCode.getValue())) {
			showMessage(editView, "I00037", "C11154");// ���[���R�[�h
			editView.ctrlProgramCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓���v���O���������ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {

			UserSearchCondition condition = new UserSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlProgramCode.getValue());

			List<UserRole> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00055", "C11154");// ���[���R�[�h
				editView.ctrlProgramCode.requestTextFocus();
				return false;
			}
		}

		// ���̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlProgramName.getValue())) {
			showMessage(editView, "I00037", "C11155");// ���[������
			editView.ctrlProgramName.requestTextFocus();
			return false;
		}

		// ���̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlProgramNames.getValue())) {
			showMessage(editView, "I00037", "C11156");// ���[������
			editView.ctrlProgramNames.requestTextFocus();
			return false;
		}

		// �������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlProgramNamek.getValue())) {
			showMessage(editView, "I00037", "C11157");// ���[����������
			editView.ctrlProgramNamek.requestFocus();
			return false;
		}

		// �J�n�N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055");
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
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

		return true;

	}

	/**
	 * �ȖڃR�[�h�ꗗ���擾����
	 * 
	 * @return �v���O�����ꗗ
	 * @throws TException
	 */
	protected List<Item> getItemList() throws TException {

		ItemSearchCondition condition = new ItemSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setSumItem(false);
		condition.setTitleItem(false);
		List<Item> list = (List<Item>) request(ItemManager.class, "get", condition);

		return list;

	}

	/**
	 * ����ꗗ���擾����
	 * 
	 * @return �v���O�����ꗗ
	 * @throws TException
	 */
	protected List<Department> getDepartmentList() throws TException {

		DepartmentSearchCondition condition = new DepartmentSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());

		List<Department> list = (List<Department>) request(DepartmentManager.class, "get", condition);

		return list;

	}

	/**
	 * �v���O���������ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param program �v���O����
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�v���O����
	 */
	protected String[] getRowData(Program program) {
		return new String[] { program.getSystemClassification().getCode(), program.getCode(), program.getNames() };
	}

}
