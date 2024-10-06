package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.tree.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.tree.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.master.ui.MG0250ProgramDisplayMasterPanel.TProgramMenuPanel;
import jp.co.ais.trans2.model.program.*;

/**
 * �v���O�����\���ݒ�}�X�^�̃R���g���[��
 * 
 * @author AIS
 */
public class MG0250ProgramDisplayMasterPanelCtrl extends TController {

	/** �w����� */
	protected MG0250ProgramDisplayMasterPanel mainView = null;

	/** �V�K�^�u�o�^��� */
	protected MG0250ProgramDisplayMasterTabDialog tabDialog = null;

	/**
	 * �^�u�ҏW���[�h
	 * 
	 * @author AIS
	 */
	protected enum TabMode {
		/** NEW */
		NEW,
		/** MODIFY */
		MODIFY
	}

	/** �^�u�ҏW�̃��[�h */
	protected TabMode mode = null;

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
		mainView = new MG0250ProgramDisplayMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createProgramDisplayMasterTabDialog() {
		tabDialog = new MG0250ProgramDisplayMasterTabDialog(getCompany(), mainView.getParentFrame(), true);
		addNewTabDialogEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		// [�^�u�ǉ�]�{�^������
		mainView.btnAddTab.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnAddTab_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�^�u�C��]�{�^������
		mainView.btnModifyTab.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModifyTab_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�^�u�폜]�{�^������
		mainView.btnDeleteTab.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDeleteTab_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�m��]�{�^������
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [��(�v���O�����ǉ�)]�{�^������
		mainView.btnAddProgram.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnAddProgram_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * �w����ʂ�����������
	 * 
	 * @throws TException
	 */
	protected void initMainView() throws TException {

		mainView.menuTab.setTabPlacement(SwingConstants.LEFT);
		mainView.menuTab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		mainView.tbl.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// �v���O���������擾
		ProgramSearchCondition condition = getSearchCondition();
		List<Program> list = getProgram(condition);

		// ���������ɊY������v���O���������݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
		if (list == null || list.isEmpty()) {
			showMessage(mainView.getParentFrame(), "I00022");
			return;
		}

		// �v���O���������ꗗ�ɕ\������
		for (Program program : list) {
			mainView.tbl.addRow(getRowData(program));
		}

	}

	/**
	 * �V�K�^�u�o�^��ʂ̃C�x���g��`
	 */
	protected void addNewTabDialogEvent() {

		// [�m��]�{�^������
		tabDialog.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				newTabDialog_btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�߂�]�{�^������
		tabDialog.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				newTabDialog_btnClose_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * �w�����[�^�u�ǉ�]�{�^������
	 */
	protected void btnAddTab_Click() {

		try {

			mode = TabMode.NEW;
			createProgramDisplayMasterTabDialog();
			tabDialog.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�����[�^�u�C��]�{�^������
	 */
	protected void btnModifyTab_Click() {

		try {

			mode = TabMode.MODIFY;
			createProgramDisplayMasterTabDialog();
			tabDialog.ctrlProgramGroupName.setValue(getSelectedTabPanel().getTitle());

			tabDialog.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�����[�^�u�폜]�{�^������
	 */
	protected void btnDeleteTab_Click() {

		try {

			TProgramMenuPanel pnl = getSelectedTabPanel();
			pnl.setTitle(tabDialog.ctrlProgramGroupName.getValue());
			int index = mainView.menuTab.indexOfComponent(pnl);
			mainView.menuTab.removeTabAt(index);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�����[�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00004")) {
				return;
			}

			// �o�^
			List<SystemizedProgram> list = getInputedSystemizedProgram();
			entrySystemizedProgram(list);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [��(�v���O�����ǉ�)]�{�^������
	 */
	protected void btnAddProgram_Click() {

		try {

			// �I�����ꂽ�v���O�����ꗗ���擾
			List<Program> list = getSelectedPrograms();

			// �����̃��j���[�ɒǉ�
			TProgramMenuPanel pnl = (TProgramMenuPanel) mainView.menuTab.getSelectedComponent();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) pnl.tree.getLastSelectedPathComponent();
			if (node == null) {
				node = pnl.getRoot();
			}

			for (Program program : list) {
				TTreeItem item = new TTreeItem(program.getNames(), program);
				DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(item);
				node.add(treeNode);

			}
			pnl.tree.expandAll();
			pnl.tree.reload();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �^�u�ݒ�_�C�A���O[�m��]�{�^������
	 */
	protected void newTabDialog_btnSettle_Click() {

		// �V�K�̏ꍇ
		if (TabMode.NEW == mode) {
			mainView.menuTab.add(getTabPanel());

			// �C���̏ꍇ
		} else {
			TProgramMenuPanel pnl = getSelectedTabPanel();
			pnl.setTitle(tabDialog.ctrlProgramGroupName.getValue());
			int index = mainView.menuTab.indexOfComponent(pnl);
			mainView.menuTab.removeTabAt(index);
			mainView.menuTab.add(pnl, index);
			mainView.menuTab.setSelectedIndex(index);
		}
		tabDialog.setVisible(false);
		tabDialog.dispose();
	}

	/**
	 * �^�u�ݒ�_�C�A���O[�߂�]�{�^������
	 */
	protected void newTabDialog_btnClose_Click() {
		tabDialog.setVisible(false);
		tabDialog.dispose();
	}

	/**
	 * �V�K�^�u��Ԃ�
	 * 
	 * @return �V�K�^�u
	 */
	protected TProgramMenuPanel getTabPanel() {

		TProgramMenuPanel panel = mainView.new TProgramMenuPanel();
		panel.setTitle(tabDialog.ctrlProgramGroupName.getValue());

		return panel;
	}

	/**
	 * �v���O�����ꗗ��Ԃ�
	 * 
	 * @param condition
	 * @return List<Program>
	 * @throws TException
	 */
	@SuppressWarnings("unchecked")
	protected List<Program> getProgram(ProgramSearchCondition condition) throws TException {

		List<Program> list = (List<Program>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * �v���O�����̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected ProgramSearchCondition getSearchCondition() {

		ProgramSearchCondition condition = new ProgramSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setExecutable(true);
		condition.setNotExecutable(false);

		return condition;

	}

	/**
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return Class
	 */
	protected Class getModelClass() {
		return ProgramManager.class;
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

	/**
	 * �I�����ꂽ�^�u�p�l����Ԃ�
	 * 
	 * @return �I�����ꂽ�^�u�p�l��
	 */
	protected TProgramMenuPanel getSelectedTabPanel() {
		TProgramMenuPanel pnl = (TProgramMenuPanel) mainView.menuTab.getSelectedComponent();
		return pnl;
	}

	/**
	 * �I�����ꂽ�v���O�����̈ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ�v���O�����̈ꗗ
	 */
	protected List<Program> getSelectedPrograms() {

		List<Program> list = new ArrayList<Program>();

		int[] rows = mainView.tbl.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			Program program = new Program();
			program.setCode((String) mainView.tbl
				.getRowValueAt(rows[i], MG0250ProgramDisplayMasterPanel.SC.programCode));
			program.setNames((String) mainView.tbl.getRowValueAt(rows[i],
				MG0250ProgramDisplayMasterPanel.SC.programNames));
			list.add(program);
		}

		return list;
	}

	/**
	 * ���͂��ꂽ�v���O�����̌n��Ԃ�
	 * 
	 * @return ���͂��ꂽ�v���O�����̌n
	 */
	protected List<SystemizedProgram> getInputedSystemizedProgram() {

		List<SystemizedProgram> list = new ArrayList<SystemizedProgram>();

		// �^�u�̐������v���O�����̌n������
		for (int tabCount = 0; tabCount < mainView.menuTab.getTabCount(); tabCount++) {
			SystemizedProgram systemizedProgram = new SystemizedProgram();
			systemizedProgram.setCompanyCode(TLoginInfo.getCompany().getCode());
			systemizedProgram.setProgramGroupCode(Integer.toString(tabCount));
			TProgramMenuPanel panel = (TProgramMenuPanel) mainView.menuTab.getComponentAt(tabCount);
			systemizedProgram.setProgramGroupName(panel.getTitle());
			list.add(systemizedProgram);
		}
		return list;
	}

	/**
	 * �v���O�����̌n��o�^����
	 * 
	 * @param list �v���O�����̌n�̃��X�g
	 * @throws TException
	 */
	protected void entrySystemizedProgram(List<SystemizedProgram> list) throws TException {
		request(getModelClass(), "entrySystemizedProgram", list);
	}

}
