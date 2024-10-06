package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.tree.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.dnd.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.department.DepartmentTransferData.SourceType;

/**
 * ����K�w�}�X�^�̃R���g���[��
 * 
 * @author AIS
 */
public class MG0050DepartmentHierarchyMasterPanelCtrl extends TController {

	/** ���샂�[�h */
	public enum Mode {
		/** �V�K */
		NEW
		/** ���� */
		, COPY
		/** �폜 */
		, DELETE
		/** �G�N�Z�� */
		, EXCEL
		/** �m�� */
		, SETTLE
	}

	/** �w����� */
	protected MG0050DepartmentHierarchyMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0050DepartmentHierarchyDialog editView = null;

	/** tree -> spread : false�Atree -> tree : true */
	protected boolean treeToTree = true;

	/** ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B */
	protected Mode mode = null;

	/** ���[�g�� */
	public String rootName = null;

	/** �c���[����̃f�[�^�ړ����� */
	protected boolean isDragFromTree = false;

	/** �O�̑g�D�R�[�h */
	public String oldSskCode = null;

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
		mainView = new MG0050DepartmentHierarchyMasterPanel(this);
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		// [�V�K]�{�^������
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [����]�{�^������
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�폜]�{�^������
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�G�N�Z��]�{�^������
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�m��]�{�^������
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// �g�D�R�[�h�ύX����
		mainView.ctrlOrganizationCode.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent evt) {
				changedOrganizationCode(evt.getStateChange());
			}
		});

		// �ꗗ�h���b�O���h���b�v�C�x���g
		mainView.dndTable.setDnDListener(new TDnDAdapter() {

			@Override
			public Object getDragData(DragGestureEvent evt) {

				// �ꗗ����h���b�O�f�[�^�̎擾�������s��
				List<DepartmentTreeNode> nodeList = dragFromTable(evt);

				DepartmentTransferData transferData = new DepartmentTransferData();
				transferData.setNodeList(nodeList);
				transferData.setSource(SourceType.TABLE);

				isDragFromTree = false;

				return transferData;
			}

			@Override
			public void dropData(DropTargetDropEvent evt, Object value) {

				// �ꗗ�փh���b�v�������s��
				dropToTable(value);
			}

		});

		// ����K�w�}�E�X�C�x���g
		mainView.tree.addMouseListener(new MouseAdapter() {

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

					popup.show(e.getComponent(), e.getX(), e.getY());

					// �E�N���b�N���A�}�E�X�I�[�o�[�̃m�[�h�̃t�H�[�J�X�𓖂Ă�
					mainView.tree.setSelectionRow(mainView.tree.getRowForLocation(e.getX(), e.getY()));
				}
			}
		});

		// ����K�w�h���b�O���h���b�v�C�x���g
		mainView.tree.setDnDListener(new TDnDAdapter() {

			@Override
			public Object getDragData(DragGestureEvent evt) {

				// �������c���[���ꎞ�ۑ�����

				List<DepartmentTreeNode> nodeList = dragFromTree(evt, mainView.tree);
				DepartmentTransferData transferData = new DepartmentTransferData();
				transferData.setNodeList(nodeList);
				transferData.setSource(SourceType.TREE);

				isDragFromTree = true;

				return transferData;
			}

			@Override
			public void dropData(DropTargetDropEvent evt, Object value) {

				// �c���[�ւ̃h���b�v����
				dropToTree(evt, mainView.tree, value);
			}

			@Override
			public boolean isLeaf(TreeNode node) {
				return true;
			}
		});

	}

	/**
	 * �g�D�R�[�h�ύX����
	 * 
	 * @param state
	 */
	protected void changedOrganizationCode(int state) {
		try {
			if (Util.isNullOrEmpty(mainView.ctrlOrganizationCode.getSelectedOrganizationCode())) {

				// ���x���O������
				mainView.ctrlDepartment.clear();

				initMainView();

				return;
			}

			if (ItemEvent.SELECTED != state) {
				return;
			}

			// �g�D���������̒�`
			DepartmentOrganizationSearchCondition orgCondition = getTreeSearchCondition();

			// DB����g�D�����擾����
			List<DepartmentOrganization> orgList = getDepartmentOrganization(orgCondition);

			// ���匟�������̒�`
			DepartmentSearchCondition depCondition = getSearchCondition();

			// DB���畔������擾����
			List<Department> depList = getDepartment(depCondition);

			// ���x���O������
			mainView.ctrlDepartment.clear();

			// ���x���O
			for (DepartmentOrganization org : orgList) {
				if (org.getLevel() == 0) {
					String departmentCode = org.getDepCode();
					mainView.ctrlDepartment.setCode(departmentCode);
					mainView.ctrlDepartment.setNames(org.getDepNames());
					break;
				}
			}

			// �c���[�\�z
			initTree(orgList);

			// �t�B���^�[
			if (depList != null) {
				for (DepartmentOrganization org : orgList) {
					String departmentCode = org.getDepCode();

					for (int i = depList.size() - 1; i >= 0; i--) {
						if (departmentCode.equals(depList.get(i).getCode())) {
							depList.remove(i);
							break;
						}
					}
				}
			}

			// ����ꗗ�֔��f
			initList(depList);

			setMainButtonEnabled(!Util.isNullOrEmpty(mainView.ctrlOrganizationCode.getSelectedItemValue()));

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �w����ʂ�����������
	 */
	protected void initMainView() {

		// ���C���{�^���������s�ɂ���
		setMainButtonEnabled(false);

		// �ꗗ�ݒ菈��
		initList(null);

		// ����K�w�ݒ菈��
		initTree(null);

	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param bln enabled
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
		mainView.btnExcel.setEnabled(bln);
		mainView.btnSettle.setEnabled(bln);
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
	 * [����]�{�^������
	 */
	protected void btnCopy_Click() {

		try {

			// ���ʑΏۂ̑g�D�R�[�h���擾����
			DepartmentOrganization org = getSelectedDepOrg();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̕�������Z�b�g����
			createEditView();
			initEditView(Mode.COPY, org);

			// ���ʉ�ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * ����}�X�^���f�[�^���擾���A�ꗗ�ɐݒ肷��B
	 * 
	 * @param list ����
	 */
	protected void initList(List<Department> list) {

		// �ꗗ���N���A����
		mainView.dndTable.removeRow();

		// ����K�w�}�X�^�����݂��Ȃ��ꍇ
		if (list == null || list.isEmpty()) {
			return;
		}

		// ��������ꗗ�ɕ\������
		for (Department department : list) {
			mainView.dndTable.addRow(getDepartmentDataRow(department));
		}

		// 1�s�ڂ�I������
		mainView.dndTable.setRowSelection(0);

	}

	/**
	 * ����̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected DepartmentSearchCondition getSearchCondition() {
		DepartmentSearchCondition condition = new DepartmentSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setSumDepartment(true);

		return condition;
	}

	/**
	 * ����K�w�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected DepartmentOrganizationSearchCondition getOrganizationSearchCondition() {
		DepartmentOrganizationSearchCondition condition = new DepartmentOrganizationSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());

		return condition;
	}

	/**
	 * ���������ɊY�����镔��̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�����镔��̃��X�g
	 * @throws TException
	 */
	protected List<Department> getDepartment(DepartmentSearchCondition condition) throws TException {
		return (List<Department>) request(getModelClass(), "get", condition);
	}

	/**
	 * ���f���N���X��Ԃ�
	 * 
	 * @return ���f���N���X
	 */
	protected Class getModelClass() {
		return DepartmentManager.class;
	}

	/**
	 * ��������ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param department ����
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ����
	 */
	protected Object[] getDepartmentDataRow(Department department) {
		return new Object[] { department.getCode(), department.getNames(), department };
	}

	/**
	 * ����K�w�}�X�^���f�[�^���擾���A����K�w�ɐݒ肷��B
	 * 
	 * @param list ����K�w
	 */
	protected void initTree(List<DepartmentOrganization> list) {

		// �c���[�̃N���A����
		DepartmentTreeNode root = (DepartmentTreeNode) mainView.tree.getModel().getRoot();
		if (root != null) {
			root.removeAllChildren();
			mainView.tree.reload();
		}

		// ����K�w�}�X�^�����݂��Ȃ��ꍇ
		if (list == null || list.isEmpty()) {
			return;
		}

		// ����K�w�\��
		setTreeDepartmentOrganizationData(list);
	}

	/**
	 * ����K�w�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected DepartmentOrganizationSearchCondition getTreeSearchCondition() {

		DepartmentOrganizationSearchCondition condition = new DepartmentOrganizationSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());

		return condition;

	}

	/**
	 * ���������ɊY�����镔��K�w�̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�����镔��K�w�̃��X�g
	 * @throws TException
	 */
	protected List<DepartmentOrganization> getDepartmentOrganization(DepartmentOrganizationSearchCondition condition)
		throws TException {
		return (List<DepartmentOrganization>) request(getModelClass(), "getDepartmentOrganizationData", condition);
	}

	/**
	 * �ꗗ�փh���b�v����
	 * 
	 * @param value DepartmentTransferData �n������m�[�h�f�[�^
	 */
	protected void dropToTable(Object value) {

		if (!isDragFromTree) {
			return;
		}

		// ��]�̕���m�[�h�f�[�^����Ȃ��ꍇ�A�����s�v
		if (!(value instanceof DepartmentTransferData)) {
			return;
		}

		DepartmentTransferData transferData = (DepartmentTransferData) value;

		// ���X�g��0���̏ꍇ�A�����s�v
		if (transferData.getNodeList() == null || transferData.getNodeList().isEmpty()) {
			return;
		}

		// �c���[����폜(�I���̂�)���X�v���b�h�֕���
		deleteTreeDataAndRestoreTable();

	}

	/**
	 * ����K�w�h���b�O����
	 * 
	 * @param evt �h���b�O�^�[�Q�b�g�C�x���g
	 * @param tree ����K�w���X�g
	 * @return TTee�̊K�w�f�[�^
	 */
	protected List<DepartmentTreeNode> dragFromTree(DragGestureEvent evt, TDnDTree tree) {

		ArrayList<DepartmentTreeNode> listTreeNode = new ArrayList<DepartmentTreeNode>();

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

						DepartmentTreeNode node = (DepartmentTreeNode) path.getLastPathComponent();

						Enumeration enumNode = node.breadthFirstEnumeration();
						while (enumNode.hasMoreElements()) {
							DepartmentTreeNode eachNode = (DepartmentTreeNode) enumNode.nextElement();
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
	 * ����K�w�h���b�v����
	 * 
	 * @param evt �h���b�v�^�[�Q�b�g�C�x���g
	 * @param tree ����K�w���X�g
	 * @param value �w�肳�ꂽ�f�[�^
	 */
	protected void dropToTree(DropTargetDropEvent evt, TDnDTree tree, Object value) {

		// �]�����`�F�b�N
		if (evt.isDataFlavorSupported(ObjectTransferable.FLAVOR)) {

			// drop�ʒu�ɂ���TreeNode���擾����B�h���b�v�ʒu�Ƀm�[�h�����݂��Ȃ��ꍇ�A�������I������B
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			Point p = evt.getLocation();
			TreePath targetPath = tree.getPathForLocation(p.x, p.y);

			DepartmentTreeNode targetNode = null;
			if (targetPath == null) {
				targetNode = (DepartmentTreeNode) model.getRoot();
			} else {
				targetNode = (DepartmentTreeNode) targetPath.getLastPathComponent();
			}

			// �ړ���m�[�h
			TreePath dropPath = new TreePath(model.getPathToRoot(targetNode.getParent() == null ? targetNode
				: targetNode.getParent()));// �ړ���p�X�擾

			// �ύX�ς݂̃m�[�h���X�g�i�e��ʗp�j
			List<DepartmentTreeNode> insertedNodeList = new ArrayList<DepartmentTreeNode>();

			if (!(value instanceof DepartmentTransferData)) {
				return;
			}

			// �n������K�w�m�[�h�f�[�^
			DepartmentTransferData transferData = (DepartmentTransferData) value;

			// ����K�w�m�[�h���X�g
			List<DepartmentTreeNode> list = transferData.getNodeList();

			if (list == null || list.isEmpty()) {
				return;
			}

			// �`�F�b�N�A�ڕW�̓h���b�O���ł���΁A�h���b�v�s�v
			if (list.contains(targetNode)) {
				evt.dropComplete(false);
				return;
			}

			for (DepartmentTreeNode node : list) {

				// �����̐e�m�[�h
				DepartmentTreeNode parentNode = (DepartmentTreeNode) node.getParent();

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

					DepartmentTreeNode targetParentNode = (DepartmentTreeNode) targetNode.getParent();

					if (targetParentNode != null && evt.getDropAction() != DnDConstants.ACTION_MOVE) {
						model.insertNodeInto(node, targetParentNode, targetParentNode.getIndex(targetNode));
					} else {
						model.insertNodeInto(node, targetNode, targetNode.getChildCount());
					}

				} else {
					// spread -> tree�̏ꍇ

					DepartmentTreeNode targetParentNode = (DepartmentTreeNode) targetNode.getParent();

					if (targetParentNode != null && evt.getDropAction() != DnDConstants.ACTION_MOVE) {
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
	 * @param list �K�w�f�[�^
	 */
	public void setTreeDepartmentOrganizationData(List<DepartmentOrganization> list) {
		List<DepartmentDisp> listDnDData = new ArrayList<DepartmentDisp>();

		for (DepartmentOrganization org : list) {
			DepartmentDisp disp = new DepartmentDisp();
			disp.setCompanyCode(org.getCompanyCode());
			disp.setCode(org.getDepCode());
			disp.setName(org.getDepNames());
			disp.setParentCode(getParentCode(org));

			if (Util.isNullOrEmpty(disp.getParentCode())) {

				disp.setInpDate(org.getInpDate());
			}

			Department department = new Department();
			department.setCode(org.getDepCode());
			department.setNames(org.getDepNames());
			disp.setDepartment(department);

			listDnDData.add(disp);
		}

		setTreeData(listDnDData);
	}

	/**
	 * @param org
	 * @return �e�R�[�h
	 */
	protected String getParentCode(DepartmentOrganization org) {
		switch (org.getLevel()) {
			case 1:
				return org.getLevel0();
			case 2:
				return org.getLevel1();
			case 3:
				return org.getLevel2();
			case 4:
				return org.getLevel3();
			case 5:
				return org.getLevel4();
			case 6:
				return org.getLevel5();
			case 7:
				return org.getLevel6();
			case 8:
				return org.getLevel7();
			case 9:
				return org.getLevel8();
		}
		return null;
	}

	/**
	 * �K�w�f�[�^��ݒ肷��
	 * 
	 * @param listDnDData �K�w�f�[�^
	 */
	public void setTreeData(List<DepartmentDisp> listDnDData) {

		int rootNum = 0;
		/** �K�w�f�[�^�̃��[�g�f�[�^ */
		DepartmentDisp baseDndData = null;
		DepartmentTreeNode baseNode = null;

		DefaultTreeModel model = null;

		// ��ʊK�w�R�[�h = �u�����N �̃f�[�^�̓��[�g�m�[�h�ɐݒ肷��B
		for (DepartmentDisp disp : listDnDData) {
			if (Util.isNullOrEmpty(disp.getParentCode())) {
				baseDndData = disp;

				baseNode = new DepartmentTreeNode(disp);
				model = new DefaultTreeModel(baseNode);
				mainView.tree.setModel(model);
				rootNum = rootNum + 1;
			}
		}

		if (baseNode == null || rootNum > 1) {
			return;
		}

		// �q�K�w�f�[�^��ݒ肷��
		createTree(baseNode, baseDndData, listDnDData);

		model.reload();
		TreePath visiblePath = new TreePath(((DefaultTreeModel) mainView.tree.getModel()).getPathToRoot(baseNode));
		mainView.tree.setSelectionPath(visiblePath);

		mainView.tree.setRootVisible(false);
		mainView.tree.setEditable(false);

		// ���ׂēW�J���܂�
		TreeNode root = (TreeNode) mainView.tree.getModel().getRoot();
		mainView.tree.expandAll(new TreePath(root), true);
	}

	/**
	 * �K�w�f�[�^��ݒ肷��
	 * 
	 * @param parentNode
	 * @param parent
	 * @param listData �K�w�f�[�^
	 */
	protected void createTree(DepartmentTreeNode parentNode, DepartmentDisp parent, List<DepartmentDisp> listData) {
		for (DepartmentDisp dndData : listData) {

			if (dndData.getName() == null) {
				continue;
			}

			if (parent.getCode().equals(dndData.getParentCode())) {
				DepartmentTreeNode childNode = new DepartmentTreeNode(dndData);
				parentNode.add(childNode);

				createTree(childNode, dndData, listData);
			}
		}
	}

	/**
	 * [�폜]�{�^������
	 */
	protected void btnDelete_Click() {
		try {
			// �m�F���b�Z�[�W
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			DepartmentOrganization bean = getSelectedDepOrg();

			// ����K�w�����擾
			request(getModelClass(), "deleteDepartmentOrganization", bean);

			if (bean == null) {
				// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂���
				throw new TException("I00193");
			}

			// �R���{�{�b�N�X���t���b�V��
			mainView.ctrlOrganizationCode.getController().initList();

			// ���x���O������
			mainView.ctrlDepartment.clear();

			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ����K�w��Ԃ�
	 * 
	 * @return ����K�w
	 * @throws Exception
	 */
	protected DepartmentOrganization getSelectedDepOrg() throws Exception {

		DepartmentOrganizationSearchCondition condition = createDepartmentOrganizationSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());
		List<DepartmentOrganization> list = getDepartmentOrganization(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * @return ����K�w���̌�������
	 */
	protected DepartmentOrganizationSearchCondition createDepartmentOrganizationSearchCondition() {
		return new DepartmentOrganizationSearchCondition();
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
			DepartmentOrganizationSearchCondition condition = getOrganizationSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getDepartmentOrganizationExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02327") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			// �m��Check
			if (!checkSettle()) {
				return;
			}

			// �m�F���b�Z�[�W��\������
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			List<DepartmentOrganization> list = getTreeData();

			// �o�^����
			request(getModelClass(), "entryDepartmentOrganization",
				mainView.ctrlOrganizationCode.getSelectedOrganizationCode(), list);

			// �����������b�Z�[�W
			showMessage(mainView, "I00008");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �m��Check
	 * 
	 * @return true:�G���[�Ȃ�
	 */
	protected boolean checkSettle() {
		if (getMaxLevel() > 9) {
			showMessage(mainView, "W00153");
			return false;
		}

		return true;
	}

	/**
	 * ����K�w�h���b�O����
	 * 
	 * @param evt �h���b�O�^�[�Q�b�g�C�x���g
	 * @return TTable�̊K�w�f�[�^
	 */
	public List<DepartmentTreeNode> dragFromTable(DragGestureEvent evt) {

		List<DepartmentDisp> listDnDData = new ArrayList<DepartmentDisp>();

		InputEvent inputEvent = evt.getTriggerEvent();
		try {

			// ���N���b�N��
			if (SwingUtilities.isLeftMouseButton((MouseEvent) inputEvent)) {
				if ((evt.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {

					// �ꗗ.�I���s�̃f�[�^���擾�B�����s�I������Ă���ꍇ�͑S�Ď擾����B

					// �I���s�̃f�[�^���擾�B
					for (Department department : getSelectedDepartment()) {

						DepartmentDisp data = new DepartmentDisp();
						data.setParentCode("");
						data.setCode(department.getCode());
						data.setName(department.getNames());
						data.setDepartment(department);
						listDnDData.add(data);
					}

				}
			}
		} catch (InvalidDnDOperationException ioe) {
			// �����Ȃ�
		} catch (Exception ex) {
			// �����Ȃ�
		}

		return createDepOrgTree(listDnDData);
	}

	/**
	 * �ꗗ�őI������Ă��镔���Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă��镔��
	 */
	protected List<Department> getSelectedDepartment() {

		List<Department> list = new ArrayList<Department>();
		for (int i : mainView.dndTable.getSelectedRows()) {
			list.add((Department) mainView.dndTable.getRowValueAt(i, MG0050DepartmentHierarchyMasterPanel.SC.bean));
		}

		return list;
	}

	/**
	 * �R���X�g���N�^ Tree��Table�ATree��Tree�p
	 * 
	 * @param list
	 * @return TTee�̊K�w�f�[�^
	 */
	public List<DepartmentTreeNode> createDepOrgTree(List list) {

		if (list == null || list.isEmpty()) {
			return null;
		}

		Object obj = list.get(0);

		if (obj instanceof DepartmentDisp) {
			List<DepartmentTreeNode> tmpList = new ArrayList<DepartmentTreeNode>();
			for (int i = 0; i < list.size(); i++) {

				DepartmentDisp dnDData = (DepartmentDisp) list.get(i);
				DepartmentTreeNode departmentTreeNode = new DepartmentTreeNode(dnDData);
				tmpList.add(departmentTreeNode);
			}
			return tmpList;
		}

		return list;
	}

	/**
	 * �g�D�R�[�h�̍폜���X�v���b�h�֕���
	 */
	public void deleteTreeDataAndRestoreTable() {

		try {

			// �����폜���\
			if (mainView.tree == null || mainView.tree.getModel() == null) {
				return;
			}

			TreePath[] deletePaths = null;

			if (mainView.tree.getSelectionPaths() == null) {
				deletePaths = mainView.tree.getAllPaths();
			} else {
				deletePaths = mainView.tree.getSelectionPaths();
			}

			if (deletePaths == null) {
				return;
			}

			DefaultTreeModel model = (DefaultTreeModel) mainView.tree.getModel();
			DepartmentTreeNode root = (DepartmentTreeNode) mainView.tree.getModel().getRoot(); // ���[�g�m�[�h

			if (root == null || root.getDepDisp() == null) {
				return;
			}

			String rootCode = root.getDepDisp().getCode(); // ���[�g�R�[�h

			// ���ɕ��������m�[�h���L�^����
			List<DepartmentTreeNode> listFinish = new ArrayList<DepartmentTreeNode>();

			// ���ׂĂ̑I�������폜����
			for (TreePath path : deletePaths) {

				if (path == null) {
					return;
				}

				Object obj = path.getLastPathComponent();

				if (!(obj instanceof DepartmentTreeNode)) {
					continue;
				}

				DepartmentTreeNode selectedNode = (DepartmentTreeNode) obj;
				if (selectedNode.getDepDisp() == null) {

					continue;

				} else {

					Enumeration enumNode = selectedNode.breadthFirstEnumeration();
					while (enumNode.hasMoreElements()) {
						DepartmentTreeNode node = (DepartmentTreeNode) enumNode.nextElement();

						if (listFinish.contains(node)) {
							continue;
						} else {
							listFinish.add(node);
						}

						if (node.getDepDisp().getDepartment() != null
							&& !Util.isNullOrEmpty(node.getDepDisp().getDepartment().getCode())) {

							// �ꗗ�ɒǉ�����
							mainView.dndTable.addRow(getDepartmentDataRow(node.getDepDisp().getDepartment()));
						}
					}

				}

				// ���[�g�m�[�h���I������Ă��鏈���𒆒f����B
				String selectCode = selectedNode.getDepDisp().getCode();

				if (rootCode.equals(selectCode)) {
					continue;
				}

				// �I���m�[�h���폜��
				model.removeNodeFromParent(selectedNode);
			}

			// �폜��A�K�w�f�[�^�ł̓��[�g�m�[�h��I����Ԃɂ���B
			TreePath visiblePath = new TreePath(model.getPathToRoot(root));
			mainView.tree.setSelectionPath(visiblePath);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �ő僌�x�����擾����
	 * 
	 * @return �ő僌�x��
	 */
	public int getMaxLevel() {
		int maxLevel = 0;

		DepartmentTreeNode root = (DepartmentTreeNode) mainView.tree.getModel().getRoot();

		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			DepartmentTreeNode node = (DepartmentTreeNode) e.nextElement();

			TreeNode[] path = node.getPath();
			maxLevel = Math.max(maxLevel, path.length);
		}

		return maxLevel - 1;
	}

	/**
	 * ���[�g�m�[�h���܂߂Ȃ��K�w�f�[�^���擾����
	 * 
	 * @return �K�w�f�[�^
	 */
	public List<DepartmentOrganization> getTreeData() {

		List<DepartmentOrganization> list = new ArrayList<DepartmentOrganization>();

		DepartmentTreeNode root = (DepartmentTreeNode) mainView.tree.getModel().getRoot();
		DepartmentDisp level0 = null;

		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			DepartmentTreeNode node = (DepartmentTreeNode) e.nextElement();

			TreeNode[] path = node.getPath();
			int level = path.length - 1;

			if (level == 0) {
				level0 = ((DepartmentTreeNode) path[0]).getDepDisp();
				continue;
			}

			DepartmentOrganization bean = new DepartmentOrganization();
			bean.setCompanyCode(getCompanyCode());
			bean.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());
			bean.setDepCode(getNodeDepartmentCode(path[level]));
			bean.setLevel(level);
			bean.setInpDate(level0.getInpDate());

			bean.setLevel0(getNodeDepartmentCode(path[0]));
			if (level >= 1) {
				bean.setLevel1(getNodeDepartmentCode(path[1]));
			}
			if (level >= 2) {
				bean.setLevel2(getNodeDepartmentCode(path[2]));
			}
			if (level >= 3) {
				bean.setLevel3(getNodeDepartmentCode(path[3]));
			}
			if (level >= 4) {
				bean.setLevel4(getNodeDepartmentCode(path[4]));
			}
			if (level >= 5) {
				bean.setLevel5(getNodeDepartmentCode(path[5]));
			}
			if (level >= 6) {
				bean.setLevel6(getNodeDepartmentCode(path[6]));
			}
			if (level >= 7) {
				bean.setLevel7(getNodeDepartmentCode(path[7]));
			}
			if (level >= 8) {
				bean.setLevel8(getNodeDepartmentCode(path[8]));
			}
			if (level >= 9) {
				bean.setLevel9(getNodeDepartmentCode(path[9]));
			}

			list.add(bean);

		}

		return list;
	}

	/**
	 * @param node
	 * @return ����R�[�h
	 */
	protected String getNodeDepartmentCode(TreeNode node) {
		return ((DepartmentTreeNode) node).getDepDisp().getCode();
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0050DepartmentHierarchyDialog(getCompany(), mainView.getParentFrame(), true);

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
				btnEditViewSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�߂�]�{�^������
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnEditViewClose_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param mode_ �V�K
	 * @param bean
	 */
	protected void initEditView(Mode mode_, DepartmentOrganization bean) {

		this.mode = mode_;
		switch (mode) {
			case NEW:
				editView.setTitle(getWord("C01698"));

				break;

			case COPY:
				editView.setTitle(getWord("C01738"));
				editView.ctrlDepartmentReference.setEditable(false);

				oldSskCode = bean.getCode();

				editView.ctrlOrganizationCode.setValue(oldSskCode);
				editView.ctrlDepartmentReference.code.setValue(bean.getDepCode());
				editView.ctrlDepartmentReference.name.setValue(bean.getDepNames());

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

			// ���͂��ꂽ�g�D�R�[�h�����擾
			DepartmentOrganization departmentOrganization = getInputedOrg();

			if (Mode.NEW == mode) {
				// �V�K�o�^
				request(getModelClass(), "entryDepartmentOrganization", departmentOrganization);
			} else if (Mode.COPY == mode) {
				// ����
				request(getModelClass(), "entryCopyDepartmentOrganization", departmentOrganization, oldSskCode);
			}

			// �ҏW��ʂ����
			btnEditViewClose_Click();

			// �ǉ������R���{�{�b�N�X�ɔ��f����
			mainView.ctrlOrganizationCode.getController().initList();

			mainView.ctrlOrganizationCode.setSelectedItemValue(departmentOrganization.getCode());
			mainView.ctrlDepartment.setCode(departmentOrganization.getDepCode());
			mainView.ctrlDepartment.setNames(departmentOrganization.getDepNames());

			// �g�D���������̒�`
			DepartmentOrganizationSearchCondition orgCondition = getTreeSearchCondition();

			// DB����g�D�����擾����
			List<DepartmentOrganization> orgList = getDepartmentOrganization(orgCondition);

			// ���匟�������̒�`
			DepartmentSearchCondition depCondition = getSearchCondition();

			// DB���畔������擾����
			List<Department> depList = getDepartment(depCondition);

			// �t�B���^�[
			if (depList != null) {
				for (DepartmentOrganization org : orgList) {
					String departmentCode = org.getDepCode();

					for (int i = depList.size() - 1; i >= 0; i--) {
						if (departmentCode.equals(depList.get(i).getCode())) {
							depList.remove(i);
							break;
						}
					}
				}
			}

			if (Mode.NEW == mode) {
				initTree(null);
			} else if (Mode.COPY == mode) {
				initTree(orgList);
			}

			initList(depList);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�g�D�R�[�h��Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�g�D�R�[�h���
	 */
	protected DepartmentOrganization getInputedOrg() {

		// �g�D�R�[�h���
		DepartmentOrganization org = new DepartmentOrganization();
		org.setCode(editView.ctrlOrganizationCode.getValue());
		org.setDepCode(editView.ctrlDepartmentReference.getCode());
		org.setDepNames(editView.ctrlDepartmentReference.getNames());
		return org;

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
	 * @throws Exception
	 */
	protected boolean isEditViewInputRight() throws Exception {

		// �g�D�R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlOrganizationCode.getValue())) {
			showMessage(editView, "I00037", "C00335"); // �g�D�R�[�h
			editView.ctrlOrganizationCode.requestFocus();
			return false;
		}

		// ���x���O�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDepartmentReference.getCode())) {
			showMessage(editView, "I00037", "C00722"); // ���x���O
			editView.ctrlDepartmentReference.btn.requestFocus();
			return false;
		}

		// �g�D�R�[�h���d�����Ă�����G���[
		DepartmentOrganizationSearchCondition condition = new DepartmentOrganizationSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(editView.ctrlOrganizationCode.getValue());

		List<DepartmentOrganization> list = getDepartmentOrganization(condition);

		if (list != null && !list.isEmpty()) {
			showMessage(editView, "I00084", "C00335");
			editView.ctrlOrganizationCode.requestTextFocus();
			return false;
		}

		return true;
	}

}
