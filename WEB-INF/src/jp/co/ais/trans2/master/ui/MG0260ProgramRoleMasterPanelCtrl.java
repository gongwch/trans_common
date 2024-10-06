package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.tree.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.tree.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.program.*;
import jp.co.ais.trans2.model.security.*;

/**
 * �v���O�������[���}�X�^ CTRL
 * 
 * @author AIS
 */
public class MG0260ProgramRoleMasterPanelCtrl extends TController {

	/**
	 * �w�����
	 */
	protected MG0260ProgramRoleMasterPanel mainView = null;

	/**
	 * �ҏW���
	 */
	protected MG0260ProgramRoleMasterDialog editView = null;

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
		mainView = new MG0260ProgramRoleMasterPanel();
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
		editView = new MG0260ProgramRoleMasterDialog(getCompany(), mainView.getParentFrame(), true);

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

		// [��(�v���O�����ǉ�)]�{�^������
		editView.btnAddProgram.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnAddProgram_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [��(�v���O�����ǉ�)]�{�^������
		editView.btnDeleteProgram.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDeleteProgram_Click();
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
	protected void initEditView(Mode editMode, ProgramRole bean) throws TException {

		this.mode = editMode;
		editPrgList = getProgramList();

		// �V�K�̏ꍇ
		if (Mode.NEW == editMode) {

			editView.setTitle(getWord("C01698"));// �V�K���
			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());
			editBean = null;// �ҏW�O�f�[�^���폜

			// �v���O���������ꗗ�ɒǉ�����
			for (Program program : editPrgList) {
				editView.tbl.addRow(getRowData(program));
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

			// �I���v���O�����ꗗ��ݒ肷��
			addSelectedPrg(bean.getProgramList());

			// �v���O���������ꗗ�ɒǉ�����
			for (Program program : getCandidatePrgList(bean.getProgramList())) {
				editView.tbl.addRow(getRowData(program));
			}

		}

	}

	/**
	 * [��(�v���O�����ǉ�)]�{�^������
	 */
	protected void btnAddProgram_Click() {

		try {

			// �I�����ꂽ�v���O�����ꗗ���擾
			addSelectedPrg(getSelectedCandidatePrgList());

			// �v���O�������ꗗ��������
			editView.tbl.removeRow();

			// �v���O�������ꗗ���Đݒ�
			for (Program bean : getCandidatePrgList(getSelectedPrgList(false))) {
				editView.tbl.addRow(getRowData(bean));
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [��(�v���O��������)]�{�^������
	 */
	protected void btnDeleteProgram_Click() {

		try {

			int[] selectedRow = editView.prgPnl.tree.getSelectionRows();

			if (selectedRow == null) {
				return;
			}

			List<Program> list = new ArrayList<Program>();
			DefaultMutableTreeNode node = editView.prgPnl.getRoot();

			// �e�m�[�h����I���s�ȊO�̃f�[�^���擾
			for (int i = 0; i < node.getChildCount(); i++) {

				boolean isSelected = false;
				for (int j = 0; j < selectedRow.length; j++) {

					if (selectedRow[j] == i) {
						isSelected = true;
					}

				}

				// �I�����ꂽ�f�[�^�͒ǉ����Ȃ�
				if (isSelected) {
					continue;
				}

				DefaultMutableTreeNode cNode = (DefaultMutableTreeNode) node.getChildAt(i);
				TTreeItem item = (TTreeItem) cNode.getUserObject();
				list.add((Program) item.getObj());
			}

			// �v���O�����I���ꗗ��������
			editView.prgPnl.getRoot().removeAllChildren();

			// �v���O�����I���ꗗ���Đݒ�
			addSelectedPrg(list);

			// �v���O�������ꗗ��������
			editView.tbl.removeRow();

			// �v���O�������ꗗ���Đݒ�
			for (Program bean : getCandidatePrgList(getSelectedPrgList(false))) {
				editView.tbl.addRow(getRowData(bean));
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �ҏW��� �I��PG�ꗗ�Ƀf�[�^��ǉ�����
	 * 
	 * @param list
	 */
	protected void addSelectedPrg(List<Program> list) {

		DefaultMutableTreeNode node = editView.prgPnl.getRoot();

		for (Program program : list) {
			TTreeItem item = new TTreeItem(program.getNames(), program);
			DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(item);
			node.add(treeNode);
		}

		editView.prgPnl.tree.expandAll();
		editView.prgPnl.tree.reload();

	}

	/**
	 * �I�����ꂽ�v���O�������ꗗ��Ԃ�
	 * 
	 * @return �I���s���X�g
	 */
	protected List<Program> getSelectedCandidatePrgList() {

		List<Program> list = new ArrayList<Program>();

		int[] rows = editView.tbl.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			Program program = new Program();
			program.setCode((String) editView.tbl
				.getRowValueAt(rows[i], MG0250ProgramDisplayMasterPanel.SC.programCode));
			program.setNames((String) editView.tbl.getRowValueAt(rows[i],
				MG0250ProgramDisplayMasterPanel.SC.programNames));
			list.add(program);
		}

		return list;
	}

	/**
	 * �I���v���O�����ꗗ�̃f�[�^���擾����
	 * 
	 * @param isDistinct �d���f�[�^���������邩�ǂ���
	 * @return �I��PRG���X�g
	 */
	protected List<Program> getSelectedPrgList(boolean isDistinct) {

		List<Program> list = new ArrayList<Program>();
		DefaultMutableTreeNode node = editView.prgPnl.getRoot();

		// �e�m�[�h����f�[�^���擾
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode cNode = (DefaultMutableTreeNode) node.getChildAt(i);
			TTreeItem item = (TTreeItem) cNode.getUserObject();
			Program addPrg = (Program) item.getObj();
			if (isDistinct) {

				boolean isExist = false;
				for (Program prg : list) {
					if (addPrg.getCode().equals(prg.getCode())) {
						isExist = true;
						break;
					}
				}

				if (isExist) {
					continue;
				}
			}
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
	protected List<Program> getCandidatePrgList(List<Program> selectedlist) {

		List<Program> list = new ArrayList<Program>();

		for (Program bean : editPrgList) {

			boolean isExist = false;
			for (Program tmpBean : selectedlist) {
				if (bean.getCode().equals(tmpBean.getCode())) {
					isExist = true;
				}
			}

			if (isExist) {
				continue;
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
		bean.setProgramList(getSelectedPrgList(true));
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

		DefaultMutableTreeNode node = editView.prgPnl.getRoot();
		if (node.getChildCount() == 0) {
			// �ꗗ����g�p�v���O������I�����Ă�������
			showMessage(editView, "I00254");
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
