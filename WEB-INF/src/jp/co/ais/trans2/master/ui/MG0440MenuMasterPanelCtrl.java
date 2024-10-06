package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;

import javax.swing.*;
import javax.swing.tree.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.dnd.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.program.*;
import jp.co.ais.trans2.model.program.ProgramTransferData.SourceType;

/**
 * ���j���[�\���}�X�^�̃R���g���[��
 * 
 * @author AIS
 */
public class MG0440MenuMasterPanelCtrl extends TController {

	/** ���샂�[�h */
	public enum Mode {
		/** �V�K */
		NEW
	}

	/** �V�K�^�u�_�~�[�R�[�h */
	public static String dummyTabCode = "999";

	/** �V�K�t�H���_�_�~�[�R�[�h */
	public static String dummyFolderCode = "99999999";

	/** �O���[�v�R�[�h�ő�l */
	public static String grpCodeMax = "99";

	/** �O���[�v�R�[�h�ő包�� */
	public static String grpCodeMaxFormat = "00";

	/** ���j���[�R�[�h�ő�l */
	public static String menuCodeMax = "99999";

	/** ���j���[�R�[�h�ő包�� */
	public static String menuCodeMaxFormat = "00000";

	/** ���o�pREGEX */
	protected final static String MENU_CODE_REGEX = "\\d+";

	/** ���o�pPARTTEN */
	protected final static Pattern MENU_CODE_PATTERN = Pattern.compile(MENU_CODE_REGEX);

	/** �w����� */
	protected MG0440MenuMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0440MenuMasterDialog editView = null;

	/** tree -> spread : false�Atree -> tree : true */
	protected boolean treeToTree = true;

	/** true:�V�K�^�u */
	protected boolean isNewTab = false;

	/** ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B */
	protected Mode mode = null;

	/** �������c���[(����) */
	protected TDnDTree selectedTree = null;

	/** �V�K�^�u�쐬���̃^�u�� */
	public String newTabName = null;

	/** �^�u�̍폜 */
	public String tabTitleDelete = null;

	/** �^�u�̕ҏW */
	public String tabTitleModify = null;

	/** �V�K�t�H���_�쐬 */
	public String folderTitleNew = null;

	/** ���j���[�̍폜 */
	public String folderTitleDelete = null;

	/** ���O�̕ύX */
	public String folderTitleNameChange = null;

	/** �V�K�t�H���_�쐬���̃t�H���_�� */
	public String newFolderName = null;

	/** �^�u�F�_�C�A���O�^�C�g�� */
	public String colorDialogName = null;

	/** ���[�g�� */
	public String rootName = null;

	/**
	 * �������J�n����
	 */
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

	/**
	 * �p�l���̎擾
	 */
	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0440MenuMasterPanel(this);
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		// [����]�{�^������
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
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

		// [�^�u�V�K�쐬]�{�^������
		mainView.btnNewTab.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNewTab_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// �ꗗ�h���b�O���h���b�v�C�x���g
		mainView.dndTable.setDnDListener(new TDnDAdapter() {

			@Override
			public Object getDragData(DragGestureEvent evt) {

				// �ꗗ����h���b�O�f�[�^�̎擾�������s��
				List<ProgramTreeNode> nodeList = dragFromTable(evt);

				ProgramTransferData transferData = new ProgramTransferData();
				transferData.setNodeList(nodeList);
				transferData.setSource(SourceType.TABLE);

				selectedTree = null;

				return transferData;
			}

			@Override
			public void dropData(DropTargetDropEvent evt, Object value) {

				// �ꗗ�փh���b�v�������s��
				dropToTable(value);
			}

		});

		// ���j���[�^�u�̃}�E�X�C�x���g
		mainView.menuTab.addMouseListener(new MouseAdapter() {

			/** ���j���[ */
			protected JPopupMenu popup = new JPopupMenu();

			/** �N���b�N�C�x���g */
			@Override
			public void mouseClicked(MouseEvent e) {

				Component comp = e.getComponent();

				if (comp == null || !comp.isEnabled()) {
					return;
				}

				// �_�u���N���b�N����
				if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {

					Point p = e.getPoint();
					int index = mainView.menuTab.indexAtLocation(p.x, p.y);

					if (index < 0) {
						return;
					}

					showTabEditDialog();

				} else if (SwingUtilities.isRightMouseButton(e)) {
					// �E�N���b�N
					popup.removeAll();

					// �^�u�̍폜
					JMenuItem tabDelete = new JMenuItem(tabTitleDelete);
					tabDelete.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent evt) {
							int index = mainView.menuTab.getSelectedIndex();

							// �폜�Ώۃ^�u�̍��ڂ𕜌�����
							JScrollPane panel = (JScrollPane) mainView.menuTab.getComponentAt(index);
							TDnDTree tree1 = (TDnDTree) panel.getViewport().getComponent(0);
							deleteTreeDataAndRestoreTable(tree1);

							mainView.menuTab.remove(index);
						}
					});

					// �^�u�̕ҏW
					JMenuItem tabModify = new JMenuItem(tabTitleModify);
					tabModify.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent evt) {
							showTabEditDialog();
						}
					});

					popup.add(tabDelete);
					popup.add(tabModify);

					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	/**
	 * �w����ʂ�����������
	 */
	protected void initMainView() {

		// ���C���{�^���������s�ɂ���
		setMainButtonEnabled(false);

		// �P�ꏉ����
		initWord();

		// �ꗗ�ݒ菈��
		initList();

		// �v���O�����K�w�ݒ菈��
		initTree();

		// ���C���{�^���������ɂ���
		setMainButtonEnabled(true);
	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param bln enabled
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnSettle.setEnabled(bln);
	}

	/**
	 * �P�ꏉ����
	 */
	protected void initWord() {

		// �V�K�^�u�쐬���̃^�u��
		newTabName = getWord("C11409"); // �V�����^�u

		// �^�u�̍폜
		tabTitleDelete = getWord("C11410"); // �^�u�̍폜

		// �^�u�̕ҏW
		tabTitleModify = getWord("C11411"); // �^�u�̕ҏW

		// �V�K�t�H���_�쐬
		folderTitleNew = getWord("C11412"); // �V�K�t�H���_�쐬

		// ���j���[�̍폜
		folderTitleDelete = getWord("C11405"); // ���j���[�̍폜

		// ���O�̕ύX
		folderTitleNameChange = getWord("C11408"); // ���O�̕ύX

		// �V�K�t�H���_�쐬���̃t�H���_��
		newFolderName = getWord("C11407"); // �V�����t�H���_

		// �^�u�F�_�C�A���O�^�C�g��
		colorDialogName = getWord("C11413"); // �^�u�F

		// ���[�g��
		rootName = getWord("C11406"); // ���[�g

	}

	/**
	 * �v���O�����}�X�^���f�[�^���擾���A�ꗗ�ɐݒ肷��B
	 */
	protected void initList() {

		try {

			// �ꗗ���N���A����
			mainView.dndTable.removeRow();

			// �v���O���������擾
			ProgramSearchCondition condition = getSearchCondition();
			List<Program> list = getProgram(condition);

			// ���������ɊY������v���O���������݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				return;
			}

			// �v���O���������ꗗ�ɕ\������
			for (Program program : list) {
				mainView.dndTable.addRow(getProgramDataRow(program));
			}

			// 1�s�ڂ�I������
			mainView.dndTable.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �v���O�����̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected ProgramSearchCondition getSearchCondition() {

		ProgramSearchCondition condition = new ProgramSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setExecutable(false);
		condition.setNotExecutable(false);
		condition.setWithoutMenu(true);

		condition.setValidTerm(Util.getCurrentDate());

		return condition;

	}

	/**
	 * ���������ɊY������v���O�����̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY������v���O�����̃��X�g
	 * @throws Exception
	 */
	protected List<Program> getProgram(ProgramSearchCondition condition) throws Exception {

		List<Program> list = (List<Program>) request(getProgramModelClass(), "get", condition);

		return list;
	}

	/**
	 * ���f���N���X��Ԃ�
	 * 
	 * @return ���f���N���X
	 */
	protected Class getProgramModelClass() {
		return ProgramManager.class;
	}

	/**
	 * �v���O���������ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param program �v���O����
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�v���O����
	 */
	protected Object[] getProgramDataRow(Program program) {
		return new Object[] { program.getCode(), program.getNames(), program };
	}

	/**
	 * ���j���[�\���}�X�^���f�[�^���擾���A�v���O�����K�w�ɐݒ肷��B
	 */
	protected void initTree() {

		try {

			// ���j���[�����擾
			MenuSearchCondition condition = getTreeSearchCondition();
			List<SystemizedProgram> list = getMenu(condition);

			// ���j���[�\���}�X�^�����݂��Ȃ��ꍇ
			if (list == null || list.isEmpty()) {
				return;
			}

			for (SystemizedProgram prgGroup : list) {

				// ���j���[�^�u��ǉ�����
				addNewTab(prgGroup);
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * ���j���[�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected MenuSearchCondition getTreeSearchCondition() {

		MenuSearchCondition condition = new MenuSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setProgramRoleCode(getUser().getProgramRole().getCode());
		condition.setWithoutProgramRole(true);
		condition.setWithBlank(true);

		return condition;

	}

	/**
	 * ���������ɊY�����郁�j���[�̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�����郁�j���[�̃��X�g
	 * @throws Exception
	 */
	protected List<SystemizedProgram> getMenu(MenuSearchCondition condition) throws Exception {

		List<SystemizedProgram> list = (List<SystemizedProgram>) request(getMenuModelClass(), "getSystemizedProgram",
			condition);

		return list;
	}

	/**
	 * ���f���N���X��Ԃ�
	 * 
	 * @return ���f���N���X
	 */
	protected Class getMenuModelClass() {
		return MenuManager.class;
	}

	/**
	 * �ꗗ�փh���b�v����
	 * 
	 * @param value ProgramTransferData �n���v���O�����m�[�h�f�[�^
	 */
	protected void dropToTable(Object value) {

		// �������c���[���Ȃ��ꍇ�A�����s�v
		if (selectedTree == null) {
			return;
		}

		// ��]�̃v���O�����m�[�h�f�[�^����Ȃ��ꍇ�A�����s�v
		if (!(value instanceof ProgramTransferData)) {
			return;
		}

		ProgramTransferData transferData = (ProgramTransferData) value;

		// ���X�g��0���̏ꍇ�A�����s�v
		if (transferData.getNodeList().isEmpty()) {
			return;
		}

		for (ProgramTreeNode node : transferData.getNodeList()) {

			// ���j���[���ƃv���O�������null�̏ꍇ�A�����s�v
			if (node.getMenuDisp() == null || node.getMenuDisp().getProgram() == null
				|| Util.isNullOrEmpty(node.getMenuDisp().getProgram().getCode()) || node.getMenuDisp().isMenu()) {
				continue;
			}

			// �c���[����폜(�I���̂�)���X�v���b�h�֕���
			deleteTreeDataAndRestoreTable(selectedTree);

		}

	}

	/**
	 * ���j���[�^�u�ǉ�
	 * 
	 * @param prgGroup �v���O�������
	 */
	public void addNewTab(SystemizedProgram prgGroup) {

		final TDnDTree tree = new TDnDTree();

		JScrollPane sp = new JScrollPane();

		TGuiUtil.setComponentSize(sp, 270, 400);
		sp.getViewport().add(tree, null);

		// �V�K�^�u�̃C�x���g�ǉ�
		addNewTabEvents(tree);

		List<MenuDisp> listDnDData = new ArrayList<MenuDisp>();

		String title = Util.avoidNullNT(prgGroup.getProgramGroupCode()) + " "
			+ Util.avoidNullNT(prgGroup.getProgramGroupName());

		if (Util.isNullOrEmpty(prgGroup) || Util.isNullOrEmpty(prgGroup.getMenuDisp())) {

			mainView.menuTab.addTab(title, sp);

			listDnDData = getRootMenus(null, prgGroup.getProgramGroupCode());

			// �^�u�I����ݒ肷��B
			int tabSelect = mainView.menuTab.getTabCount() - 1;

			mainView.menuTab.setSelectedIndex(tabSelect);
			mainView.menuTab.setBackgroundAt(tabSelect,
				prgGroup.getMenuColor() == null ? Color.GRAY : prgGroup.getMenuColor());

		} else {

			// �^�u�I����ݒ肷��B
			int tabSelect = mainView.menuTab.getTabCount();

			mainView.menuTab.addTab(title, sp);
			mainView.menuTab.setBackgroundAt(tabSelect, prgGroup.getMenuColor());

			listDnDData = getRootMenus(prgGroup.getMenuDisp(), prgGroup.getProgramGroupCode());

		}

		// �v���O�����K�w�\��
		setTreeData(listDnDData, tree);

	}

	/**
	 * �V�K�^�u�̃C�x���g�ǉ�
	 * 
	 * @param tree
	 */
	protected void addNewTabEvents(final TDnDTree tree) {

		// �v���O�����K�w�}�E�X�C�x���g
		tree.addMouseListener(new MouseAdapter() {

			/** ���j���[ */
			protected JPopupMenu popup = new JPopupMenu();

			/** �N���b�N�C�x���g */
			@Override
			public void mouseClicked(final MouseEvent e) {

				Component comp = e.getComponent();

				if (comp == null || !comp.isEnabled()) {
					return;
				}

				if (SwingUtilities.isRightMouseButton(e)) {
					// �E�N���b�N
					popup.removeAll();

					// �V�K�t�H���_�쐬
					JMenuItem folderNew = new JMenuItem(folderTitleNew);
					folderNew.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent evt) {
							addNewFolder(tree, e);
						}
					});

					// ���j���[�̍폜
					JMenuItem folderDelete = new JMenuItem(folderTitleDelete);
					folderDelete.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent evt) {
							deleteTreeDataAndRestoreTable(tree);
						}
					});

					// ���O�̕ύX
					JMenuItem folderNameChange = new JMenuItem(folderTitleNameChange);
					folderNameChange.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent evt) {
							changeFolderName(tree);
						}
					});

					popup.add(folderNew);
					popup.add(folderDelete);
					popup.add(folderNameChange);

					popup.show(e.getComponent(), e.getX(), e.getY());

					// �E�N���b�N���A�}�E�X�I�[�o�[�̃m�[�h�̃t�H�[�J�X�𓖂Ă�
					tree.setSelectionRow(tree.getRowForLocation(e.getX(), e.getY()));
				}
			}
		});

		// �v���O�����K�w�h���b�O���h���b�v�C�x���g
		tree.setDnDListener(new TDnDAdapter() {

			@Override
			public Object getDragData(DragGestureEvent evt) {

				// �������c���[���ꎞ�ۑ�����
				selectedTree = tree;

				List<ProgramTreeNode> nodeList = dragFromTree(evt, tree);
				ProgramTransferData transferData = new ProgramTransferData();
				transferData.setNodeList(nodeList);
				transferData.setSource(SourceType.TREE);

				return transferData;
			}

			@Override
			public void dropData(DropTargetDropEvent evt, Object value) {

				// �c���[�ւ̃h���b�v����
				dropToTree(evt, tree, value);
			}

			@Override
			public boolean isLeaf(TreeNode node) {
				if (node instanceof ProgramTreeNode) {

					ProgramTreeNode pnode = ((ProgramTreeNode) node);

					if (pnode.getMenuDisp() == null) {
						return true;
					}

					return !pnode.getMenuDisp().isMenu();

				} else {
					return node.isLeaf();
				}
			}
		});
	}

	/**
	 * ���[�g�m�[�h���쐬�B�e�R�[�h��ݒ肷��B
	 * 
	 * @param menuList �Ώۃ��X�g
	 * @param currentGroupCode �O���[�v�R�[�h
	 * @return ���[�g�̃v���O�����R�[�h�z��
	 */
	public List<MenuDisp> getRootMenus(List<MenuDisp> menuList, String currentGroupCode) {
		List<MenuDisp> parentList = new LinkedList<MenuDisp>();

		// �e�v���O�����R�[�h�擾
		String parentCode = currentGroupCode + "00000";

		// ���[�g���쐬�i�v���O�����K�w�ɕ\������Ȃ��j
		MenuDisp data = new MenuDisp();
		data.setParentCode("");
		data.setCode(parentCode);
		data.setName(rootName); // ���[�g
		data.setMenuDivision(MenuDivision.MENU);

		SystemClassification sys = new SystemClassification();
		sys.setCode(currentGroupCode);
		data.setSystemClassification(sys);

		parentList.add(data);

		if (menuList == null || menuList.isEmpty()) {
			return parentList;
		}

		for (MenuDisp menu : menuList) {

			String parntCode = menu.getParentCode();
			String code = menu.getCode();

			// �e�v���O�����R�[�h���ݒ肳��Ă��Ȃ��ꍇ�A���[�g�Ƃ���
			if (Util.isNullOrEmpty(parntCode) && !code.equals(parentCode)) {
				menu.setParentCode(parentCode);
				parentList.add(menu);
			} else {
				parentList.add(menu);
			}
		}

		return parentList;
	}

	/**
	 * ���O�̕ύX
	 * 
	 * @param tree �v���O�����K�w���X�g
	 */
	protected void changeFolderName(TDnDTree tree) {
		tree.startEditingAtPath(tree.getSelectionPath());
	}

	/**
	 * �v���O�����K�w�h���b�O����
	 * 
	 * @param evt �h���b�O�^�[�Q�b�g�C�x���g
	 * @param tree �v���O�����K�w���X�g
	 * @return TTee�̊K�w�f�[�^
	 */
	protected List<ProgramTreeNode> dragFromTree(DragGestureEvent evt, TDnDTree tree) {

		ArrayList<ProgramTreeNode> listTreeNode = new ArrayList<ProgramTreeNode>();

		InputEvent inputEvent = evt.getTriggerEvent();
		try {

			// drag�ʒu��TreeNode�����݂��Ȃ��ꍇ�A���̓��[�g�m�[�h�̏ꍇ�A�������I��
			Point pt = evt.getDragOrigin();
			TreePath checkPathCheck = tree.getPathForLocation(pt.x, pt.y);
			if (checkPathCheck == null || checkPathCheck.getParentPath() == null) {
				return null;
			}

			// ���N���b�N��
			if (SwingUtilities.isLeftMouseButton((MouseEvent) inputEvent)) {
				// drag�ʒu�ɂ���TreeNode���擾����B
				if ((evt.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {

					TreePath[] paths = tree.getSelectionPaths();
					if (paths == null) {
						return null;
					}

					// �I�����ꂽ�p�X���ׂĊ܂�
					for (TreePath path : paths) {

						ProgramTreeNode node = (ProgramTreeNode) path.getLastPathComponent();

						Enumeration enumNode = node.breadthFirstEnumeration();
						while (enumNode.hasMoreElements()) {
							ProgramTreeNode eachNode = (ProgramTreeNode) enumNode.nextElement();
							listTreeNode.add(eachNode);
						}
					}
				}
			}

			// Tree �� Spread�Ƀh���b�N���h���b�v���ĂȂ�������t���O���������B
			treeToTree = true;
		} catch (InvalidDnDOperationException ioe) {
			// �����Ȃ�
		}

		return listTreeNode;
	}

	/**
	 * �v���O�����K�w�h���b�v����
	 * 
	 * @param evt �h���b�v�^�[�Q�b�g�C�x���g
	 * @param tree �v���O�����K�w���X�g
	 * @param value �w�肳�ꂽ�f�[�^
	 */
	protected void dropToTree(DropTargetDropEvent evt, TDnDTree tree, Object value) {

		// �]�����`�F�b�N
		if (evt.isDataFlavorSupported(ObjectTransferable.FLAVOR)) {

			// drop�ʒu�ɂ���TreeNode���擾����B�h���b�v�ʒu�Ƀm�[�h�����݂��Ȃ��ꍇ�A�������I������B
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			Point p = evt.getLocation();
			TreePath targetPath = tree.getPathForLocation(p.x, p.y);

			ProgramTreeNode targetNode = null;
			if (targetPath == null) {
				targetNode = (ProgramTreeNode) model.getRoot();
			} else {
				targetNode = (ProgramTreeNode) targetPath.getLastPathComponent();
			}

			// �ړ���m�[�h
			TreePath dropPath = new TreePath(model.getPathToRoot(targetNode.getParent() == null ? targetNode
				: targetNode.getParent()));// �ړ���p�X�擾

			// �ύX�ς݂̃m�[�h���X�g�i�e��ʗp�j
			List<ProgramTreeNode> insertedNodeList = new ArrayList<ProgramTreeNode>();

			if (!(value instanceof ProgramTransferData)) {
				return;
			}

			// �n���v���O�����m�[�h�f�[�^
			ProgramTransferData transferData = (ProgramTransferData) value;

			// �v���O�����m�[�h���X�g
			List<ProgramTreeNode> list = transferData.getNodeList();

			if (list == null || list.isEmpty()) {
				return;
			}

			// �`�F�b�N�A�ڕW�̓h���b�O���ł���΁A�h���b�v�s�v
			if (list.contains(targetNode)) {
				evt.dropComplete(false);
				return;
			}

			for (ProgramTreeNode node : list) {

				// �����̐e�m�[�h
				ProgramTreeNode parentNode = (ProgramTreeNode) node.getParent();

				insertedNodeList.add(node);

				// �e�����Ɉړ��ΏۂɊ܂�ł�ꍇ�A�����s�v
				if (insertedNodeList.contains(parentNode)) {
					continue;
				}

				if (parentNode != null) {
					// tree -> tree�̏ꍇ

					// ���R���|�[�l���g���̃h���b�O���h���b�v���Ȃ����A �ȉ��̍��ڂ��`�F�b�N����B
					treeToTree = true;

					if (targetNode.equals(node)) {
						evt.dropComplete(false);
						return;
					}

					// �]�������m�[�h��Tree����폜
					model.removeNodeFromParent(node);

					ProgramTreeNode targetParentNode = (ProgramTreeNode) targetNode.getParent();

					if (targetParentNode != null
						&& MenuDivision.PROGRAM.equals(targetNode.getMenuDisp().getMenuDivision())) {
						model.insertNodeInto(node, targetParentNode, targetParentNode.getIndex(targetNode));
					} else {
						model.insertNodeInto(node, targetNode, targetNode.getChildCount());
					}

				} else {
					// spread -> tree�̏ꍇ

					ProgramTreeNode targetParentNode = (ProgramTreeNode) targetNode.getParent();

					if (targetParentNode != null
						&& MenuDivision.PROGRAM.equals(targetNode.getMenuDisp().getMenuDivision())) {
						model.insertNodeInto(node, targetParentNode, targetParentNode.getIndex(targetNode));
					} else {
						model.insertNodeInto(node, targetNode, targetNode.getChildCount());
					}
				}

			}

			evt.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

			if (SourceType.TABLE.equals(transferData.getSource())) {

				// �\�[�X���X�v���b�h�̏ꍇ�A�ꗗ����폜
				mainView.dndTable.removeSelectedRows();
			}

			// �X�V���܂�
			model.reload(list.get(0));

			// �W�J���܂�
			tree.expandAll(dropPath, true);

			// �I��
			tree.setSelectionPath(dropPath);

			// �h���b�v��������������B
			evt.dropComplete(true);

		} else {
			evt.rejectDrop();
		}
	}

	/**
	 * �K�w�f�[�^��ݒ肷��
	 * 
	 * @param listDnDData �K�w�f�[�^
	 * @param tree �v���O�����K�w���X�g
	 */
	public void setTreeData(List<MenuDisp> listDnDData, TDnDTree tree) {

		int rootNum = 0;
		/** �K�w�f�[�^�̃��[�g�f�[�^ */
		MenuDisp baseDndData = null;
		ProgramTreeNode baseNode = null;

		DefaultTreeModel model = null;

		// ��ʊK�w�R�[�h = �u�����N �̃f�[�^�̓��[�g�m�[�h�ɐݒ肷��B
		for (MenuDisp ndnData : listDnDData) {
			if (Util.isNullOrEmpty(ndnData.getParentCode())) {
				baseDndData = ndnData;

				baseNode = new ProgramTreeNode(ndnData);
				model = new DefaultTreeModel(baseNode);
				tree.setModel(model);
				rootNum = rootNum + 1;
			}
		}

		if (baseNode == null || rootNum > 1) {
			return;
		}

		// �q�K�w�f�[�^��ݒ肷��
		createTree(baseNode, baseDndData, listDnDData);

		model.reload();
		TreePath visiblePath = new TreePath(((DefaultTreeModel) tree.getModel()).getPathToRoot(baseNode));
		tree.setSelectionPath(visiblePath);

		tree.setRootVisible(false);
		tree.setEditable(true);

		// ���ׂēW�J���܂�
		TreeNode root = (TreeNode) tree.getModel().getRoot();
		tree.expandAll(new TreePath(root), true);
	}

	/**
	 * �K�w�f�[�^��ݒ肷��
	 * 
	 * @param parentNode
	 * @param parent
	 * @param listData �K�w�f�[�^
	 */
	protected void createTree(ProgramTreeNode parentNode, MenuDisp parent, List<MenuDisp> listData) {
		for (MenuDisp dndData : listData) {

			if (dndData.getName() == null) {
				continue;
			}

			if (parent.getCode().equals(dndData.getParentCode())) {
				ProgramTreeNode childNode = new ProgramTreeNode(dndData);
				parentNode.add(childNode);

				createTree(childNode, dndData, listData);
			}
		}
	}

	/**
	 * [�^�u�V�K�쐬]�{�^������
	 */
	protected void btnNewTab_Click() {

		try {

			isNewTab = true;
			SystemizedProgram sys = showTabEditDialog();

			if (sys != null) {
				// ���j���[�^�u��ǉ�����
				addNewTab(sys);
			}

		} catch (Exception e) {
			errorHandler(e);
		}

		isNewTab = false;

	}

	/**
	 * [����]�{�^������
	 */
	protected void btnSearch_Click() {

		// �����^�u�̃N���A
		mainView.menuTab.removeAll();

		// �ꗗ�ݒ菈��
		initList();

		// �v���O�����K�w�ݒ菈��
		initTree();
	}

	/**
	 * [�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			// ���̓`�F�b�N���s���B
			if (!isInputRight()) {
				return;
			}

			// �m�F���b�Z�[�W��\������
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00004")) {
				return;
			}

			List<SystemizedProgram> list = new LinkedList<SystemizedProgram>();
			int menuSeq = 1;

			// �O���[�v�R�[�h�͎����
			// �^�u���������s���B
			for (int i = 0; i < mainView.menuTab.getTabCount(); i++) {

				SystemizedProgram sys = new SystemizedProgram();

				TDnDTree tree = getDndTree(i);
				List<MenuDisp> menuList = getTreeDataWithoutRoot(tree, menuSeq);

				sys.setCompanyCode(getCompanyCode());

				// �O���[�v�R�[�h�A���̂̐ݒ�
				String[] key = mainView.menuTab.getTitleAt(i).split(" ", 2);
				String code = key[0];
				String name = key[1];
				sys.setProgramGroupCode(code);
				sys.setProgramGroupName(name);

				sys.setMenuColor(mainView.menuTab.getBackgroundAt(i));
				sys.setDispIndex(i + 1);
				sys.setMenuDisp(menuList);

				list.add(sys);
			}

			// �o�^����
			request(getMenuModelClass(), "entry", list);

			// �����������b�Z�[�W
			showMessage(mainView, "I00008");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �c���[�̎擾
	 * 
	 * @param i �^�u�C���f�b�N�X
	 * @return TDnDTree
	 */
	protected TDnDTree getDndTree(int i) {
		JScrollPane panel = (JScrollPane) mainView.menuTab.getComponentAt(i);
		TDnDTree tree = (TDnDTree) panel.getViewport().getComponent(0);
		return tree;
	}

	/**
	 * ���C����ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return ���C����ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 */
	protected boolean isInputRight() {

		for (int i = 0; i < mainView.menuTab.getTabCount(); i++) {

			TDnDTree tree = getDndTree(i);

			ProgramTreeNode root = (ProgramTreeNode) tree.getModel().getRoot();

			Enumeration e = root.breadthFirstEnumeration();
			while (e.hasMoreElements()) {

				ProgramTreeNode node = (ProgramTreeNode) e.nextElement();

				if (node.equals(root)) {
					continue;
				}

				// ���j���[���̌����`�F�b�N
				if (node.getMenuDisp() == null) {
					continue;
				}

				if (node.getMenuDisp().isMenu() && Util.avoidNull(node.toString()).getBytes().length > 40) {
					// ���j���[���͔̂��p40�����ȓ��œ��͂��Ă��������B
					showMessage(editView, "I00394", "C11424", 40);
					mainView.menuTab.setSelectedIndex(i);
					return false;
				}

				if (Util.avoidNull(node.toString()).getBytes().length <= 0) {
					// ���j���[���̂���͂��Ă��������B
					showMessage(editView, "I00037", "C11424");
					mainView.menuTab.setSelectedIndex(i);
					return false;
				}

			}
		}

		return true;
	}

	/**
	 * �v���O�����K�w�h���b�O����
	 * 
	 * @param evt �h���b�O�^�[�Q�b�g�C�x���g
	 * @return TTable�̊K�w�f�[�^
	 */
	public List<ProgramTreeNode> dragFromTable(DragGestureEvent evt) {

		List<MenuDisp> listDnDData = new ArrayList<MenuDisp>();

		InputEvent inputEvent = evt.getTriggerEvent();
		try {

			// ���N���b�N��
			if (SwingUtilities.isLeftMouseButton((MouseEvent) inputEvent)) {
				if ((evt.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {

					// �ꗗ.�I���s�̃f�[�^���擾�B�����s�I������Ă���ꍇ�͑S�Ď擾����B

					// �I���s�̃f�[�^���擾�B
					for (Program program : getSelectedProgram()) {

						MenuDisp data = new MenuDisp();
						data.setParentCode("");
						data.setCode(program.getCode());
						data.setName(program.getNames());
						data.setProgram(program);
						data.setMenuDivision(MenuDivision.PROGRAM);
						listDnDData.add(data);
					}

				}
			}
		} catch (InvalidDnDOperationException ioe) {
			// �����Ȃ�
		} catch (Exception ex) {
			// �����Ȃ�
		}

		return createProgramTree(listDnDData);
	}

	/**
	 * �ꗗ�őI������Ă���v���O������Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���v���O����
	 */
	protected List<Program> getSelectedProgram() {

		List<Program> list = new ArrayList<Program>();
		for (int i : mainView.dndTable.getSelectedRows()) {
			list.add((Program) mainView.dndTable.getRowValueAt(i, MG0440MenuMasterPanel.SC.bean));
		}

		return list;
	}

	/**
	 * �R���X�g���N�^ Tree��Table�ATree��Tree�p
	 * 
	 * @param list
	 * @return TTee�̊K�w�f�[�^
	 */
	public List<ProgramTreeNode> createProgramTree(List list) {

		if (list == null || list.isEmpty()) {
			return null;
		}

		Object obj = list.get(0);

		if (obj instanceof MenuDisp) {
			List<ProgramTreeNode> tmpList = new ArrayList<ProgramTreeNode>();
			for (int i = 0; i < list.size(); i++) {

				MenuDisp dnDData = (MenuDisp) list.get(i);
				ProgramTreeNode tProgramTreeNode = new ProgramTreeNode(dnDData);
				tmpList.add(tProgramTreeNode);
			}
			return tmpList;
		}

		return list;
	}

	/**
	 * �t�H���_�V�K�쐬
	 * 
	 * @param tree �v���O�����K�w���X�g
	 * @param evt �}�E�X�C�x���g
	 */
	public void addNewFolder(TDnDTree tree, MouseEvent evt) {

		try {

			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			TreePath targetPath = tree.getPathForLocation(evt.getX(), evt.getY());

			ProgramTreeNode targetNode = null;
			if (targetPath == null) {
				targetNode = (ProgramTreeNode) tree.getModel().getRoot();
			} else {
				targetNode = (ProgramTreeNode) targetPath.getLastPathComponent();
			}

			TreePath dropPath = new TreePath(((DefaultTreeModel) tree.getModel()).getPathToRoot(targetNode));// �ړ���p�X�擾

			List<MenuDisp> listDnDData = new ArrayList<MenuDisp>();

			MenuDisp data = new MenuDisp();

			if (MenuDivision.MENU.equals(targetNode.getMenuDisp().getMenuDivision())) {
				data.setCompanyCode(targetNode.getMenuDisp().getCompanyCode());
				data.setParentCode(targetNode.getMenuDisp().getCode());
				data.setCode("");
				data.setName(newFolderName);
				data.setMenuDivision(MenuDivision.MENU); // 1:���j���[

				SystemClassification sys = new SystemClassification();
				sys.setCode(targetNode.getMenuDisp().getSystemClassification().getCode());
				data.setSystemClassification(sys);

				listDnDData.add(data);
			} else {
				return;
			}

			// �m�[�h�̒ǉ�
			ProgramTreeNode node = new ProgramTreeNode(data);
			model.insertNodeInto(node, targetNode, targetNode.getChildCount());

			// �X�V���܂�
			((DefaultTreeModel) tree.getModel()).reload(node);

			// �W�J���܂�
			tree.expandAll(dropPath, true);

			// �I��
			tree.setSelectionPath(dropPath);

			// �I�����N���A
			tree.clearSelection();
		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * ���j���[�̍폜���X�v���b�h�֕���
	 * 
	 * @param tree �v���O�����K�w���X�g
	 */
	public void deleteTreeDataAndRestoreTable(TDnDTree tree) {

		try {

			// �����폜���\
			if (tree == null || tree.getModel() == null) {
				return;
			}

			TreePath[] deletePaths = null;

			if (tree.getSelectionPaths() == null) {
				deletePaths = tree.getAllPaths();
			} else {
				deletePaths = tree.getSelectionPaths();
			}

			if (deletePaths == null) {
				return;
			}

			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			ProgramTreeNode root = (ProgramTreeNode) tree.getModel().getRoot(); // ���[�g�m�[�h

			if (root == null || root.getMenuDisp() == null) {
				return;
			}

			String rootCode = root.getMenuDisp().getCode(); // ���[�g�R�[�h

			// ���ɕ��������m�[�h���L�^����
			List<ProgramTreeNode> listFinish = new ArrayList<ProgramTreeNode>();

			// ���ׂĂ̑I�������폜����
			for (TreePath path : deletePaths) {

				if (path == null) {
					return;
				}

				Object obj = path.getLastPathComponent();

				if (!(obj instanceof ProgramTreeNode)) {
					continue;
				}

				ProgramTreeNode selectedNode = (ProgramTreeNode) obj;
				if (selectedNode.getMenuDisp() == null) {

					continue;

				} else {

					// ���j���[�̏ꍇ�A���̑S�m�[�h���X�v���b�h�ɕ�������

					Enumeration enumNode = selectedNode.breadthFirstEnumeration();
					while (enumNode.hasMoreElements()) {
						ProgramTreeNode node = (ProgramTreeNode) enumNode.nextElement();

						if (listFinish.contains(node)) {
							continue;
						} else {
							listFinish.add(node);
						}

						if (node.getMenuDisp().getProgram() != null
							&& !Util.isNullOrEmpty(node.getMenuDisp().getProgram().getCode())) {

							// �ꗗ�ɒǉ�����
							mainView.dndTable.addRow(getProgramDataRow(node.getMenuDisp().getProgram()));
						}
					}

				}

				// ���[�g�m�[�h���I������Ă��鏈���𒆒f����B
				String selectCode = selectedNode.getMenuDisp().getCode();

				if (rootCode.equals(selectCode)) {
					continue;
				}

				// �I���m�[�h���폜��
				model.removeNodeFromParent(selectedNode);
			}

			// �폜��A�K�w�f�[�^�ł̓��[�g�m�[�h��I����Ԃɂ���B
			TreePath visiblePath = new TreePath(model.getPathToRoot(root));
			tree.setSelectionPath(visiblePath);

			// ������
			selectedTree = null;

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �Ώۃ^�u�̃O���[�v���ݒ�
	 * 
	 * @param tabIndex �^�u�C���f�b�N�X
	 * @param sys �O���[�v���
	 */
	public void syncMenuGroup(int tabIndex, SystemClassification sys) {

		// �Ώۃ^�u�̃O���[�v���ݒ���s���B
		JScrollPane panel = (JScrollPane) mainView.menuTab.getComponentAt(tabIndex);

		TDnDTree tree = (TDnDTree) panel.getViewport().getComponent(0);
		ProgramTreeNode root = (ProgramTreeNode) tree.getModel().getRoot();

		root.getMenuDisp().setSystemClassification(sys);
	}

	/**
	 * ���[�g�m�[�h���܂߂Ȃ��K�w�f�[�^���擾����
	 * 
	 * @param tree �v���O�����K�w���X�g
	 * @param menuSeq
	 * @return �K�w�f�[�^
	 */
	public List<MenuDisp> getTreeDataWithoutRoot(TDnDTree tree, int menuSeq) {

		int dispIndex = 1;

		List<MenuDisp> list = new ArrayList<MenuDisp>();

		ProgramTreeNode root = (ProgramTreeNode) tree.getModel().getRoot();

		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			ProgramTreeNode node = (ProgramTreeNode) e.nextElement();
			if (!node.equals(root)) {
				ProgramTreeNode parentNode = (ProgramTreeNode) (node.getParent());

				// ��ʊK�w�R�[�h = ���[�g�m�[�h

				MenuDisp menu = node.getMenuDisp();
				menu.setCompanyCode(getCompanyCode());
				menu.setCode(node.getMenuDisp().getCode());
				menu.setName(node.toString()); // Tree��ł̖��̕ύX�Ή�

				// �e�����[�g�m�[�h�̏ꍇ�A�e�R�[�h��ݒ肵�Ȃ��B
				menu.setParentCode("");

				// �\�����ݒ�
				menu.setDispIndex(dispIndex++);

				// �O���[�v���ݒ�
				menu.setSystemClassification(root.getMenuDisp().getSystemClassification());

				// �R�[�h��null�̏ꍇ�A�̔�
				if (menu.isMenu() || Util.isNullOrEmpty(menu.getCode())) {
					menu.setCode(getMenuCode(menuSeq++));
				}

				// �e�����[�g�m�[�h�̏ꍇ�A�e�R�[�h��ݒ肵�Ȃ��B
				menu.setParentCode(parentNode.equals(root) ? "" : parentNode.getMenuDisp().getCode()); // �e�R�[�h�ݒ�

				list.add(menu);

			}
		}

		return list;
	}

	/**
	 * ���j���[�R�[�h�̍̔�
	 * 
	 * @param seq
	 * @return ���j���[�R�[�h
	 */
	protected String getMenuCode(int seq) {
		String prefix = "MENU";
		return prefix + StringUtil.fillLeft(String.valueOf(seq), 10 - prefix.length(), '0');
	}

	/**
	 * �^�u����ύX����B
	 * 
	 * @return �^�u���
	 */
	public SystemizedProgram showTabEditDialog() {

		try {

			// �ҏW��ʂ𐶐�
			createEditView();

			// �ҏW��ʂ�����������
			initEditView();

			// �ҏW��ʂ�\��
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

			return editView.getSystemizedProgram();

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0440MenuMasterDialog(getCompany(), mainView.getParentFrame(), true);

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
				btnEditViewSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�߂�]�{�^������
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnEditViewClose_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * �ҏW��ʂ�����������
	 */
	protected void initEditView() {
		editView.setTitle(getWord("C11414")); // �^�u���ݒ���

		if (!isNewTab) {

			int index = mainView.menuTab.getSelectedIndex();

			// �O���[�v�R�[�h�A���̂̐ݒ�
			String[] key = mainView.menuTab.getTitleAt(index).split(" ", 2);
			String code = key[0];
			String name = key[1];
			editView.ctrlTabCode.setValue(code);
			editView.ctrlTabName.setValue(name);

			// �F���Z�b�g
			editView.colorChooser.setColor(mainView.menuTab.getBackgroundAt(index));

		} else {

			// �F���Z�b�g
			editView.colorChooser.setColor(Color.GRAY);

		}
	}

	/**
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnEditViewSettle_Click() {

		try {

			// ���̓`�F�b�N���s���B
			if (!isEditViewInputRight()) {
				return;
			}

			if (!isNewTab) {
				// �^�u�ɖ��̂�ݒ肷��B
				int tabIndex = mainView.menuTab.getSelectedIndex();

				String key = editView.ctrlTabCode.getValue() + " " + editView.ctrlTabName.getValue();
				mainView.menuTab.setTitleAt(tabIndex, key);

				SystemClassification classification = new SystemClassification();
				classification.setCode(editView.ctrlTabCode.getValue());
				syncMenuGroup(tabIndex, classification);

				// �F�ݒ�
				mainView.menuTab.setBackgroundAt(tabIndex, editView.colorChooser.getColor());

			} else {

				// �^�u�ǉ��p
				SystemizedProgram sys = new SystemizedProgram();
				sys.setProgramGroupCode(editView.ctrlTabCode.getValue());
				sys.setProgramGroupName(editView.ctrlTabName.getValue());
				sys.setMenuColor(editView.colorChooser.getColor());

				editView.setSystemizedProgram(sys);

			}

			// �ҏW��ʂ����
			btnEditViewClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW���[�߂�]�{�^������
	 */
	protected void btnEditViewClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 */
	protected boolean isEditViewInputRight() {

		// �O���[�v�R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlTabCode.getValue())) {
			showMessage(editView, "I00037", "C11402"); // �O���[�v�R�[�h
			editView.ctrlTabCode.requestFocus();
			return false;
		}

		// �O���[�v���̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlTabName.getValue())) {
			showMessage(editView, "I00037", "C11404"); // �O���[�v����
			editView.ctrlTabName.requestFocus();
			return false;
		}

		boolean isTabExists = false;
		String code = editView.ctrlTabCode.getValue();

		// �^�u���������s���B
		for (int i = 0; i < mainView.menuTab.getTabCount(); i++) {

			if (i == mainView.menuTab.getSelectedIndex() && !isNewTab) {
				// �I�𒆂͑ΏۊO
				continue;
			}

			String name = mainView.menuTab.getTitleAt(i).split(" ", 2)[0];
			if (name.equals(code)) {
				isTabExists = true;
				break;
			}
		}

		if (isTabExists) {
			// ���݂̏ꍇ�A�G���[:I00055">���ɑ��݂��܂��B �i�O���[�v�R�[�h�j
			showMessage(editView, "I00055", "C11402");
			editView.ctrlTabCode.requestFocus();
			return false;
		}

		return true;
	}

}
