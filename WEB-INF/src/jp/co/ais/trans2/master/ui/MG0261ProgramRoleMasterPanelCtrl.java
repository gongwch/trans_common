package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.table.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.master.ui.MG0261ProgramRoleMasterDialog.SC;
import jp.co.ais.trans2.model.program.*;
import jp.co.ais.trans2.model.security.*;

/**
 * �v���O�������[���}�X�^ CTRL
 * 
 * @author AIS
 */
public class MG0261ProgramRoleMasterPanelCtrl extends TController {

	/**
	 * �w�����
	 */
	protected MG0261ProgramRoleMasterPanel mainView = null;

	/**
	 * �ҏW���
	 */
	protected MG0261ProgramRoleMasterDialog editView = null;

	/** true:�l�ݒ菈���� */
	public boolean processing = false;

	/** ���W�I�O���[�v */
	public List<Integer> grpAuthority;

	/**
	 * ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B
	 */
	protected Mode mode = null;

	/**
	 * �C���O�̃f�[�^
	 */
	protected ProgramRole editBean = null;

	/**
	 * �v���O�������X�g
	 */
	protected List<Program> editPrgList = null;

	/** ���샂�[�h */
	protected enum Mode {
		/** �V�K */
		NEW,
		/** �C�� */
		MODIFY,
		/** ���� */
		COPY
	}

	/** ���������敪 */
	protected static final int NOT_SET = -1; // ���ݒ�

	protected static final int PROC_OFF = 0; // �g�p�s��

	protected static final int READ_ONLY = 1; // �ǎ�̂�

	protected static final int ADMIN = 2; // �X�V�\

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
		mainView = new MG0261ProgramRoleMasterPanel();
		addMainViewEvent();

		grpAuthority = new ArrayList<Integer>();
		grpAuthority.add(SC.procOff.ordinal());
		grpAuthority.add(SC.readOnly.ordinal());
		grpAuthority.add(SC.admin.ordinal());

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
	 * �l�ύX�\���ǂ�������
	 * 
	 * @return true:�l�Čv�Z���A�ύX���������Ȃ��Bfalse:�l�ύX��������
	 */
	protected boolean isProcessing() {
		return processing;
	}

	/**
	 * �X�v���b�h�l�ύX
	 * 
	 * @param row
	 * @param col
	 */
	protected void changedTable(int row, int col) {

		try {
			// �u���b�N
			processing = true;

			TableCellEditor editor = editView.tbl.getCellEditor(row, col);
			if (editor != null) {
				editor.stopCellEditing();
			}

			if (grpAuthority.contains(col)) {
				for (Integer colIndex : grpAuthority) {
					if (colIndex == col) {
						continue;
					}
					editView.tbl.setRowValueAt(false, row, colIndex);
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			processing = false;
		}
	}

	/**
	 * @param before
	 * @param after
	 * @param col
	 * @return true:�l�ύX
	 */
	protected boolean isTableValueChanged(Object before, Object after, int col) {

		if (false //
			|| SC.procOff.ordinal() == col //
			|| SC.readOnly.ordinal() == col //
			|| SC.admin.ordinal() == col) {
			// �t���O�̔��菈��

			// return !d1.equals(d2);
		}

		if ((before != null && after == null) || (before == null && after != null)) {
			return true;
		}

		return !Util.avoidNullNT(before).equals(Util.avoidNullNT(after));
	}

	/**
	 * @param col
	 */
	protected void afterTableHeaderClicked(int col) {
		if (false //
			|| SC.procOff.ordinal() == col //
			|| SC.readOnly.ordinal() == col //
			|| SC.admin.ordinal() == col) {

			// ���W�I�{�^���w�b�_�[�N���b�N�C�x���g

			for (int row = 0; row < editView.tbl.getRowCount(); row++) {
				editView.tbl.setRowValueAt(true, row, col);
			}
		}
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
			ProgramRoleSearchCondition condition = getSearchCondition();
			List<ProgramRole> list = getList(condition);

			// ���������ɊY������v���O���������݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// �v���O���������ꗗ�ɕ\������
			for (ProgramRole bean : list) {
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
			ProgramRole bean = getSelectedRowData();

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
			ProgramRole bean = getSelectedRowData();

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
			ProgramRole bean = getSelectedRowData();

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
			ProgramRoleSearchCondition condition = getSearchCondition();
			condition.setLang(super.getUser().getLanguage());

			// �f�[�^���o
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11153") + ".xls");// �v���O�������[���}�X�^

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0261ProgramRoleMasterDialog(getCompany(), mainView.getParentFrame(), true);

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

		// �X�v���b�h
		editView.tbl.setAdapter(new TTableAdapter() {

			/**
			 * �s�I����ԕύX
			 */
			@Override
			public void rowSelectionChanged() {
				// TODO
			}

			@Override
			public void changedValueAt(Object before, Object after, int row, int column) {
				changedTable(row, column);
			}

			@Override
			public boolean isValueChanged(Object before, Object after, int row, int column) {
				if (isProcessing()) {
					return false;
				}

				return isTableValueChanged(before, after, getTable().convertColumnIndexToModel(column));
			}

			@Override
			public void afterHeaderClicked(int column) {
				afterTableHeaderClicked(column);
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
	protected void initEditView(Mode editMode, ProgramRole bean) throws TException {

		this.mode = editMode;
		editPrgList = getProgramList();

		// �V�K�̏ꍇ
		if (Mode.NEW == editMode) {

			editView.setTitle(getWord("C01698"));// �V�K���
			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());
			editBean = null;// �ҏW�O�f�[�^���폜

			// �V�K�v���O�����ꗗ���쐬
			for (Program program : editPrgList) {
				editView.tbl.addRow(getRowData(program));
				int row = editView.tbl.getRowCount() - 1;
				editView.tbl.setRowValueAt(false, row, SC.procOff.ordinal());
				editView.tbl.setRowValueAt(false, row, SC.readOnly.ordinal());
				editView.tbl.setRowValueAt(true, row, SC.admin.ordinal());
			}

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

			// �ҏW�ς݃v���O�����ꗗ���쐬
			for (Program program : getEditPrgList(bean.getProgramList())) {
				editView.tbl.addRow(getRowData(program));
				int row = editView.tbl.getRowCount() - 1;
				editView.tbl.setRowValueAt(false, row, SC.procOff.ordinal());
				editView.tbl.setRowValueAt(false, row, SC.readOnly.ordinal());
				editView.tbl.setRowValueAt(false, row, SC.admin.ordinal());
				if (program.getProcAuthKbn() == PROC_OFF) {
					editView.tbl.setRowValueAt(true, row, SC.procOff.ordinal());
				}
				if (program.getProcAuthKbn() == READ_ONLY) {
					editView.tbl.setRowValueAt(true, row, SC.readOnly.ordinal());
				}
				if (program.getProcAuthKbn() == ADMIN) {
					editView.tbl.setRowValueAt(true, row, SC.admin.ordinal());
				}
				if (program.getProcAuthKbn() == NOT_SET) {
					editView.tbl.setRowValueAt(true, row, SC.admin.ordinal());
				}
			}

		}

	}

	/**
	 * �v���O�����ꗗ�̃f�[�^���擾����
	 * 
	 * @return PRG���X�g
	 */
	protected List<Program> getPrgList() {

		List<Program> list = new ArrayList<Program>();

		// �e�v���O�����̏��������敪���擾
		for (int row = 0; row < editView.tbl.getRowCount(); row++) {

			int procAuthKbn = -1;
			boolean isProcOff = (Boolean) editView.tbl.getRowValueAt(row, SC.procOff);
			boolean isReadOnly = (Boolean) editView.tbl.getRowValueAt(row, SC.readOnly);
			boolean isAdmin = (Boolean) editView.tbl.getRowValueAt(row, SC.admin);

			if (isProcOff) {
				procAuthKbn = PROC_OFF;
			} else if (isReadOnly) {
				procAuthKbn = READ_ONLY;
			} else if (isAdmin) {
				procAuthKbn = ADMIN;
			} else {
				procAuthKbn = PROC_OFF;
			}

			Program addPrg = new Program();
			addPrg.setCode(Util.avoidNull(editView.tbl.getRowValueAt(row, SC.programCode)));
			addPrg.setProcAuthKbn(procAuthKbn);

			list.add(addPrg);
		}

		return list;
	}

	/**
	 * �v���O�������ꗗ���擾����
	 * 
	 * @param selectedlist
	 * @return �I���s���X�g
	 */
	protected List<Program> getEditPrgList(List<Program> selectedlist) {

		List<Program> list = new ArrayList<Program>();

		// PRG_MST�ɓo�^����Ă���PRG_CODE��LOOP
		for (Program bean : editPrgList) {
			// PRG_ROLE_MST�ɓo�^����Ă���PRG_CODE��LOOP���ă}�b�`���O
			for (Program tmpBean : selectedlist) {
				if (bean.getCode().equals(tmpBean.getCode())) {
					// PRG_ROLE_MST�ɐݒ�ς݂�PRG_CODE���������珈�������敪���㏑��
					bean.setProcAuthKbn(tmpBean.getProcAuthKbn());
					break;
				}
			}

			list.add(bean);
		}

		return list;
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
			ProgramRole bean = getInputedData();

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
	protected ProgramRoleSearchCondition getSearchCondition() {

		ProgramRoleSearchCondition condition = new ProgramRoleSearchCondition();
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
		return ProgramRoleManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�v���O������Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�v���O����
	 */
	protected ProgramRole getInputedData() {

		ProgramRole bean = new ProgramRole();
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
		bean.setPrgCode(getProgramCode());
		bean.setUsrId(getUser().getCode());
		bean.setProgramList(getPrgList());
		return bean;

	}

	/**
	 * �G���e�B�e�B���ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param bean �f�[�^
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�v���O����
	 */
	protected String[] getRowData(ProgramRole bean) {

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
	protected List<ProgramRole> getList(ProgramRoleSearchCondition condition) throws Exception {

		List<ProgramRole> list = (List<ProgramRole>) request(getModelClass(), "get", condition);
		return list;

	}

	/**
	 * �ꗗ�őI������Ă���v���O������Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���v���O����
	 * @throws Exception
	 */
	protected ProgramRole getSelectedRowData() throws Exception {

		ProgramRoleSearchCondition condition = new ProgramRoleSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0260ProgramRoleMasterPanel.SC.code));

		List<ProgramRole> list = getList(condition);

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
	protected void doDelete(ProgramRole bean) throws Exception {
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
			editView.ctrlProgramCode.requestFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓���v���O���������ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {

			ProgramRoleSearchCondition condition = new ProgramRoleSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlProgramCode.getValue());

			List<ProgramRole> list = getList(condition);

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
			editView.ctrlProgramNamek.requestTextFocus();
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
	 * �v���O�����ꗗ���擾����
	 * 
	 * @return �v���O�����ꗗ
	 * @throws TException
	 */
	protected List<Program> getProgramList() throws TException {

		ProgramSearchCondition condition = new ProgramSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setExecutable(true);
		condition.setNotExecutable(false);

		List<Program> list = (List<Program>) request(ProgramManager.class, "get", condition);

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
