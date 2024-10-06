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
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.dnd.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.item.summary.*;
import jp.co.ais.trans2.model.item.summary.ItemSummaryTransferData.SourceType;

/**
 * �ȖڏW�v�}�X�^�̃R���g���[��
 * 
 * @author AIS
 */
public class MG0071ItemSummaryMasterPanelCtrl extends TController {

	/** ���샂�[�h */
	public enum Mode {
		/** �ҏW */
		MODIFY
		/** ���� */
		, COPY
		/** �G�N�Z�� */
		, EXCEL
		/** �m�� */
		, SETTLE
	}

	/** �w����� */
	protected MG0071ItemSummaryMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0071ItemSummaryMasterDialog editView = null;

	/** ���ʉ�� */
	protected MG0071ItemSummaryMasterCopyDialog copyView = null;

	/** �m�[�h */
	protected ItemSummaryTreeNode editNode = null;

	/** tree -> spread : false�Atree -> tree : true */
	protected boolean treeToTree = true;

	/** ���[�g�� */
	public String rootName = null;

	/** ���[�g�̃_�~�[�R�[�h */
	public static String dummyRootCode = "<  dummy  >";

	/** �c���[����̃f�[�^�ړ����� */
	protected boolean isDragFromTree = false;

	/** �Ȗڑ̌n�R�[�h�i��������.�Ȗڑ̌n�ύX���g�p�j */
	public String kmtCode = null;

	/** �Ȗڑ̌n���́i��������.�Ȗڑ̌n�ύX���g�p�j */
	public String kmtName = null;

	/** �O��Ȗڑ̌n�R�[�h�i���ʎ��g�p�j */
	public String preKmtCode = null;

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
		mainView = new MG0071ItemSummaryMasterPanel(this);
		addMainViewEvent();
	}

	/**
	 * �ҏW��ʍ쐬
	 */
	protected void createEditView() {
		editView = new MG0071ItemSummaryMasterDialog(getCompany(), mainView.getParentFrame(), true);
		addEditViewEvent();
	}

	/**
	 * ���ʉ�ʍ쐬
	 */
	protected void createCopyView() {
		copyView = new MG0071ItemSummaryMasterCopyDialog(getCompany(), mainView.getParentFrame(), true);
		addCopyViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		// [����]�{�^������
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
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

		// [�Ȗڑ̌n]verify
		mainView.ctrlItemOrganizationReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag) {
					return;
				}

				ctrlItemOrganizationReference_after();
			}
		});

		// �ꗗ�h���b�O���h���b�v�C�x���g
		mainView.dndTable.setDnDListener(new TDnDAdapter() {

			@Override
			public Object getDragData(DragGestureEvent evt) {

				// �ꗗ����h���b�O�f�[�^�̎擾�������s��
				List<ItemSummaryTreeNode> nodeList = dragFromTable(evt);

				ItemSummaryTransferData transferData = new ItemSummaryTransferData();
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

		// �ȖڏW�v�}�E�X�C�x���g
		mainView.tree.addMouseListener(new MouseAdapter() {

			/** �N���b�N�C�x���g */
			@Override
			public void mouseClicked(final MouseEvent e) {

				Component comp = e.getComponent();

				if (comp == null || !comp.isEnabled()) {
					return;
				}

				if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() > 1) {

					if (mainView.tree.getSelectionRows() != null) {

						// ���_�u���N���b�N
						doLeftDoubleClick();
					}
				}

			}
		});

		// �ȖڏW�v�h���b�O���h���b�v�C�x���g
		mainView.tree.setDnDListener(new TDnDAdapter() {

			@Override
			public Object getDragData(DragGestureEvent evt) {

				// �������c���[���ꎞ�ۑ�����
				List<ItemSummaryTreeNode> nodeList = dragFromTree(evt, mainView.tree);
				ItemSummaryTransferData transferData = new ItemSummaryTransferData();
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
	 * �_�u���N���b�N�ŕҏW��ʂ�\��
	 */
	protected void doLeftDoubleClick() {

		// �ҏW��ʂ𐶐�
		editView = new MG0071ItemSummaryMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// editNode��������
		editNode = (ItemSummaryTreeNode) mainView.tree.getSelectionPath().getLastPathComponent();

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();

		// �ҏW��ʂ�����������
		initEditView();

		// Expand
		mainView.tree.expandPath(mainView.tree.getSelectionPath());

		// �ҏW��ʂ�\��
		editView.setLocationRelativeTo(null);
		editView.setVisible(true);
	}

	/**
	 * �w�����[�Ȗڑ̌n]verify
	 */
	protected void ctrlItemOrganizationReference_after() {

		if (mainView.ctrlItemOrganizationReference.getEntity() != null
			&& mainView.ctrlItemOrganizationReference.isValueChanged()) {

			if (!showConfirmMessage(mainView, "Q00018")) {
				mainView.ctrlItemOrganizationReference.code.setValue(kmtCode);
				mainView.ctrlItemOrganizationReference.name.setValue(kmtName);

				if (Util.isNullOrEmpty(kmtCode)) {
					mainView.ctrlItemOrganizationReference.setEntity(null);
				} else {
					mainView.ctrlItemOrganizationReference.getEntity().setCode(kmtCode);
				}

				return;
			}

			// �w����ʂ��Ĕ��f
			refresh();
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

		// �ȖڏW�v�ݒ菈��
		initTree(null);

	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param bln enabled
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnCopy.setEnabled(bln);
		mainView.btnSettle.setEnabled(bln);
	}

	/**
	 * �w�����[����]�{�^������
	 */
	protected void btnSearch_Click() {

		try {

			// �Ȗڑ̌n����͂��Ă��������B
			if (Util.isNullOrEmpty(mainView.ctrlItemOrganizationReference.getCode())) {
				showMessage(mainView, "I00037", "C00609"); // �Ȗڑ̌n
				mainView.ctrlItemOrganizationReference.btn.requestFocus();
				return;
			}

			// �w����ʂ��Ĕ��f
			refresh();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �Ȗڃ}�X�^���f�[�^���擾���A�ꗗ�ɐݒ肷��B
	 * 
	 * @param list �Ȗ�
	 */
	protected void initList(List<Item> list) {

		// �ꗗ���N���A����
		mainView.dndTable.removeRow();

		// �Ȗڃ}�X�^�����݂��Ȃ��ꍇ
		if (list == null || list.isEmpty()) {
			return;
		}

		// �Ȗڏ����ꗗ�ɕ\������
		for (Item item : list) {
			mainView.dndTable.addRow(getItemDataRow(item));
		}

		// 1�s�ڂ�I������
		mainView.dndTable.setRowSelection(0);

	}

	/**
	 * �Ȗڂ̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected ItemSearchCondition getSearchCondition() {
		ItemSearchCondition condition = new ItemSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setSumItem(true);
		condition.setTitleItem(true);

		return condition;
	}

	/**
	 * �ȖڏW�v�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected ItemSummarySearchCondition getItemSummarySearchCondition() {
		ItemSummarySearchCondition condition = new ItemSummarySearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setKmtCode(mainView.ctrlItemOrganizationReference.getCode());

		return condition;
	}

	/**
	 * ���������ɊY������Ȗڂ̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY������Ȗڂ̃��X�g
	 * @throws TException
	 */
	protected List<Item> getItem(ItemSearchCondition condition) throws TException {
		return (List<Item>) request(getItemModelClass(), "get", condition);
	}

	/**
	 * �Ȗڃ��f���N���X��Ԃ�
	 * 
	 * @return ���f���N���X
	 */
	protected Class getItemModelClass() {
		return ItemManager.class;
	}

	/**
	 * ���f���N���X��Ԃ�
	 * 
	 * @return ���f���N���X
	 */
	protected Class getItemSummaryModelClass() {
		return ItemSummaryManager.class;
	}

	/**
	 * �Ȗڏ����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param item �Ȗ�
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�Ȗ�
	 */
	protected Object[] getItemDataRow(Item item) {
		return new Object[] { item.getCode(), item.getNames(), getWord(ItemSumType.getName(item.getItemSumType())),
				getWord(ItemType.getName(item.getItemType())), getWord(Dc.getName(item.getDc())), item };
	}

	/**
	 * �ȖڏW�v�}�X�^���f�[�^���擾���A�ȖڏW�v�ɐݒ肷��B
	 * 
	 * @param list �ȖڏW�v
	 */
	protected void initTree(List<ItemSummary> list) {

		// �c���[�̃N���A����
		ItemSummaryTreeNode root = (ItemSummaryTreeNode) mainView.tree.getModel().getRoot();
		if (root != null) {
			root.removeAllChildren();
			mainView.tree.reload();
		}

		// �ȖڏW�v�\��
		setTreeItemSummaryData(list);
	}

	/**
	 * �ȖڏW�v�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected ItemSummarySearchCondition getTreeSearchCondition() {

		ItemSummarySearchCondition condition = new ItemSummarySearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setKmtCode(mainView.ctrlItemOrganizationReference.getCode());

		return condition;

	}

	/**
	 * ���������ɊY������ȖڏW�v�̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY������ȖڏW�v�̃��X�g
	 * @throws TException
	 */
	protected List<ItemSummary> getItemSummary(ItemSummarySearchCondition condition) throws TException {
		return (List<ItemSummary>) request(getItemSummaryModelClass(), "get", condition);
	}

	/**
	 * �ꗗ�փh���b�v����
	 * 
	 * @param value DepartmentTransferData �n���Ȗڃm�[�h�f�[�^
	 */
	protected void dropToTable(Object value) {

		if (!isDragFromTree) {
			return;
		}

		// ��]�̉Ȗڃm�[�h�f�[�^����Ȃ��ꍇ�A�����s�v
		if (!(value instanceof ItemSummaryTransferData)) {
			return;
		}

		ItemSummaryTransferData transferData = (ItemSummaryTransferData) value;

		// ���X�g��0���̏ꍇ�A�����s�v
		if (transferData.getNodeList() == null || transferData.getNodeList().isEmpty()) {
			return;
		}

		// �c���[����폜(�I���̂�)���X�v���b�h�֕���
		deleteTreeDataAndRestoreTable();

	}

	/**
	 * �ȖڏW�v�h���b�O����
	 * 
	 * @param evt �h���b�O�^�[�Q�b�g�C�x���g
	 * @param tree �ȖڏW�v���X�g
	 * @return TTee�̊K�w�f�[�^
	 */
	protected List<ItemSummaryTreeNode> dragFromTree(DragGestureEvent evt, TDnDTree tree) {

		ArrayList<ItemSummaryTreeNode> listTreeNode = new ArrayList<ItemSummaryTreeNode>();

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

						ItemSummaryTreeNode node = (ItemSummaryTreeNode) path.getLastPathComponent();

						Enumeration enumNode = node.breadthFirstEnumeration();
						while (enumNode.hasMoreElements()) {
							ItemSummaryTreeNode eachNode = (ItemSummaryTreeNode) enumNode.nextElement();
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
	 * �ȖڏW�v�h���b�v����
	 * 
	 * @param evt �h���b�v�^�[�Q�b�g�C�x���g
	 * @param tree �ȖڏW�v���X�g
	 * @param value �w�肳�ꂽ�f�[�^
	 */
	protected void dropToTree(DropTargetDropEvent evt, TDnDTree tree, Object value) {

		// �]�����`�F�b�N
		if (evt.isDataFlavorSupported(ObjectTransferable.FLAVOR)) {

			// drop�ʒu�ɂ���TreeNode���擾����B�h���b�v�ʒu�Ƀm�[�h�����݂��Ȃ��ꍇ�A�������I������B
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			Point p = evt.getLocation();
			TreePath targetPath = tree.getPathForLocation(p.x, p.y);

			ItemSummaryTreeNode targetNode = null;

			if (targetPath == null) {
				targetNode = (ItemSummaryTreeNode) model.getRoot();
			} else {
				targetNode = (ItemSummaryTreeNode) targetPath.getLastPathComponent();
			}

			// �ړ���m�[�h
			TreePath dropPath = new TreePath(model.getPathToRoot(targetNode.getParent() == null ? targetNode
				: targetNode.getParent()));// �ړ���p�X�擾

			// �ύX�ς݂̃m�[�h���X�g�i�e��ʗp�j
			List<ItemSummaryTreeNode> insertedNodeList = new ArrayList<ItemSummaryTreeNode>();

			if (!(value instanceof ItemSummaryTransferData)) {
				return;
			}

			// �n���ȖڏW�v�m�[�h�f�[�^
			ItemSummaryTransferData transferData = (ItemSummaryTransferData) value;

			// �ȖڏW�v�m�[�h���X�g
			List<ItemSummaryTreeNode> list = transferData.getNodeList();

			if (list == null || list.isEmpty()) {
				return;
			}

			// �`�F�b�N�A�ڕW�̓h���b�O���ł���΁A�h���b�v�s�v
			if (list.contains(targetNode)) {
				evt.dropComplete(false);
				return;
			}

			for (ItemSummaryTreeNode node : list) {

				// �����̐e�m�[�h
				ItemSummaryTreeNode parentNode = (ItemSummaryTreeNode) node.getParent();

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

					// �ȖڏW�v�̕ҏW
					ItemSummary bean = node.getItemSummaryDisp().getItemSummary();// (�m�[�h�ɉȖڏW�v���͂��łɎ����Ă���)

					ItemSummaryTreeNode targetParentNode = (ItemSummaryTreeNode) targetNode.getParent();

					if (targetParentNode != null && evt.getDropAction() != DnDConstants.ACTION_MOVE) {
						// drop����target�̐e�f�[�^��KMK_CODE���Z�b�g
						bean.setSumCode(targetParentNode.getItemSummaryDisp().getItem().getCode());
						bean.setSumName(targetParentNode.getItemSummaryDisp().getItem().getNames());
						targetParentNode.getItemSummaryDisp().setParentCode(bean.getSumCode());
						model.insertNodeInto(node, targetParentNode, targetParentNode.getIndex(targetNode));

					} else {
						// drop����target�f�[�^��KMK_CODE���Z�b�g
						bean.setSumCode(targetNode.getItemSummaryDisp().getItem().getCode());
						bean.setSumName(targetNode.getItemSummaryDisp().getItem().getNames());
						targetNode.getItemSummaryDisp().setParentCode(bean.getSumCode());
						model.insertNodeInto(node, targetNode, targetNode.getChildCount());
					}

				} else {
					// spread -> tree�̏ꍇ

					// �ȖڏW�v�̍쐬
					Item item = node.getItemSummaryDisp().getItem();// (�m�[�h�ɉȖڏ��͂��łɎ����Ă���)
					ItemSummary bean = new ItemSummary();// (�ȖڏW�v���̐V�K�쐬)

					// �����l�̐ݒ�
					bean.setCompanyCode(getCompanyCode());
					bean.setKmtCode(mainView.ctrlItemOrganizationReference.getCode());
					bean.setKmtName(mainView.ctrlItemOrganizationReference.getNames());
					bean.setKmkCode(item.getCode());
					bean.setKmkName(item.getNames());
					bean.setKokName(item.getNames());
					bean.setOdr("1");
					bean.setHyjKbn(1);
					bean.setDateFrom(DateUtil.getDate(1900, 1, 1));
					bean.setDateTo(DateUtil.getDate(2099, 12, 31));

					ItemSummaryTreeNode targetParentNode = (ItemSummaryTreeNode) targetNode.getParent();

					if (targetParentNode != null && evt.getDropAction() != DnDConstants.ACTION_MOVE) {
						// drop����target�̐e�f�[�^��KMK_CODE���Z�b�g
						bean.setSumCode(targetParentNode.getItemSummaryDisp().getItem().getCode());
						bean.setSumName(targetParentNode.getItemSummaryDisp().getItem().getNames());
						targetParentNode.getItemSummaryDisp().setParentCode(bean.getSumCode());
						model.insertNodeInto(node, targetParentNode, targetParentNode.getIndex(targetNode));

					} else {
						// drop����target�f�[�^��KMK_CODE���Z�b�g
						bean.setSumCode(targetNode.getItemSummaryDisp().getItem().getCode());
						bean.setSumName(targetNode.getItemSummaryDisp().getItem().getNames());
						targetNode.getItemSummaryDisp().setParentCode(bean.getSumCode());
						model.insertNodeInto(node, targetNode, targetNode.getChildCount());
					}
					node.getItemSummaryDisp().setItemSummary(bean);// (�m�[�h�ɉȖڏW�v���̐V�K�쐬)
				}
				node.setUserObject(ItemSummaryTreeNode.getName(node.getItemSummaryDisp()));

				// �c���[���X�V���܂�
				model.reload(node);
			}

			evt.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

			if (SourceType.TABLE.equals(transferData.getSource())) {

				// �\�[�X���X�v���b�h�̏ꍇ�A�ꗗ����폜
				mainView.dndTable.removeSelectedRows();
			}

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
	public void setTreeItemSummaryData(List<ItemSummary> list) {
		List<ItemSummaryDisp> listDnDData = new ArrayList<ItemSummaryDisp>();

		// root
		ItemSummaryDisp root = new ItemSummaryDisp();
		root.setCode(dummyRootCode);
		root.setParentCode(null);

		Item rootItem = new Item();
		rootItem.setCode(null);
		root.setItem(rootItem);
		listDnDData.add(root);

		if (list != null) {
			for (ItemSummary itemSummary : list) {
				ItemSummaryDisp disp = new ItemSummaryDisp();
				disp.setCompanyCode(itemSummary.getCompanyCode());
				disp.setCode(itemSummary.getKmkCode());
				disp.setParentCode(getParentCode(itemSummary));

				Item item = new Item();
				item.setCode(itemSummary.getKmkCode());
				item.setNames(itemSummary.getKmkName());
				item.setItemSumType(itemSummary.getItemSumType());
				item.setItemType(itemSummary.getItemType());
				item.setDc(itemSummary.getDc());
				disp.setItem(item);

				disp.setItemSummary(itemSummary);

				listDnDData.add(disp);
			}
		}

		setTreeData(listDnDData);
	}

	/**
	 * @param itemSummary
	 * @return �e�R�[�h
	 */
	protected String getParentCode(ItemSummary itemSummary) {

		if (!Util.isNullOrEmpty(itemSummary.getSumCode())) {
			// �W�v�R�[�h��null����Ȃ��ꍇ�͂ЂƂO�̐e��KMK_CODE��ݒ肷��B

			return itemSummary.getSumCode();

		}
		return dummyRootCode;
	}

	/**
	 * �K�w�f�[�^��ݒ肷��
	 * 
	 * @param listDnDData �K�w�f�[�^
	 */
	public void setTreeData(List<ItemSummaryDisp> listDnDData) {

		int rootNum = 0;
		/** �K�w�f�[�^�̃��[�g�f�[�^ */
		ItemSummaryDisp baseDndData = null;
		ItemSummaryTreeNode baseNode = null;

		DefaultTreeModel model = null;

		// ��ʊK�w�R�[�h = �u�����N �̃f�[�^�̓��[�g�m�[�h�ɐݒ肷��B
		for (ItemSummaryDisp disp : listDnDData) {
			if (Util.isNullOrEmpty(disp.getParentCode())) {
				baseDndData = disp;

				baseNode = new ItemSummaryTreeNode(disp);
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
	protected void createTree(ItemSummaryTreeNode parentNode, ItemSummaryDisp parent, List<ItemSummaryDisp> listData) {
		for (ItemSummaryDisp dndData : listData) {

			if (parent.getCode().equals(dndData.getParentCode())) {
				ItemSummaryTreeNode childNode = new ItemSummaryTreeNode(dndData);
				parentNode.add(childNode);

				createTree(childNode, dndData, listData);
			}
		}
	}

	/**
	 * [����]�{�^������
	 */
	protected void btnCopy_Click() {

		// �ҏW��ʂ𐶐�
		copyView = new MG0071ItemSummaryMasterCopyDialog(getCompany(), mainView.getParentFrame(), true);

		// editNode��������
		editNode = (ItemSummaryTreeNode) mainView.tree.getSelectionPath().getLastPathComponent();

		// �ҏW��ʂ̃C�x���g��`
		addCopyViewEvent();

		// �ҏW��ʂ�����������
		initCopyView();

		// Expand
		mainView.tree.expandPath(mainView.tree.getSelectionPath());

		// �ҏW��ʂ�\��
		copyView.setLocationRelativeTo(null);
		copyView.setVisible(true);
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
			ItemSummarySearchCondition condition = getItemSummarySearchCondition();
			byte[] data = (byte[]) request(getItemSummaryModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02421") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			// �m�F���b�Z�[�W��\������
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			List<ItemSummary> list = getTreeData();

			// �o�^����
			request(getItemSummaryModelClass(), "entryItemSummary", mainView.ctrlItemOrganizationReference.getCode(),
				list);

			// �����������b�Z�[�W
			showMessage(mainView, "I00008");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ȖڏW�v�h���b�O����
	 * 
	 * @param evt �h���b�O�^�[�Q�b�g�C�x���g
	 * @return TTable�̊K�w�f�[�^
	 */
	public List<ItemSummaryTreeNode> dragFromTable(DragGestureEvent evt) {

		List<ItemSummaryDisp> listDnDData = new ArrayList<ItemSummaryDisp>();

		InputEvent inputEvent = evt.getTriggerEvent();
		try {

			// ���N���b�N��
			if (SwingUtilities.isLeftMouseButton((MouseEvent) inputEvent)) {
				if ((evt.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {

					// �ꗗ.�I���s�̃f�[�^���擾�B�����s�I������Ă���ꍇ�͑S�Ď擾����B

					// �I���s�̃f�[�^���擾�B
					for (Item item : getSelectedItem()) {
						ItemSummaryDisp data = new ItemSummaryDisp();
						data.setCode(item.getCode());
						data.setItem(item);
						listDnDData.add(data);
					}

				}
			}
		} catch (InvalidDnDOperationException ioe) {
			// �����Ȃ�
		} catch (Exception ex) {
			// �����Ȃ�
		}

		return createItemSummaryTree(listDnDData);
	}

	/**
	 * �ꗗ�őI������Ă���Ȗڂ�Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���Ȗ�
	 */
	protected List<Item> getSelectedItem() {

		List<Item> list = new ArrayList<Item>();
		for (int i : mainView.dndTable.getSelectedRows()) {
			list.add((Item) mainView.dndTable.getRowValueAt(i, MG0071ItemSummaryMasterPanel.SC.bean));
		}

		return list;
	}

	/**
	 * �R���X�g���N�^ Tree��Table�ATree��Tree�p
	 * 
	 * @param list
	 * @return TTee�̊K�w�f�[�^
	 */
	public List<ItemSummaryTreeNode> createItemSummaryTree(List list) {

		if (list == null || list.isEmpty()) {
			return null;
		}

		Object obj = list.get(0);

		if (obj instanceof ItemSummaryDisp) {
			List<ItemSummaryTreeNode> tmpList = new ArrayList<ItemSummaryTreeNode>();
			for (int i = 0; i < list.size(); i++) {

				ItemSummaryDisp dnDData = (ItemSummaryDisp) list.get(i);
				ItemSummaryTreeNode itemSummaryTreeNode = new ItemSummaryTreeNode(dnDData);
				tmpList.add(itemSummaryTreeNode);
			}
			return tmpList;
		}

		return list;
	}

	/**
	 * �Ȗڑ̌n�R�[�h�̍폜���X�v���b�h�֕���
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
			ItemSummaryTreeNode root = (ItemSummaryTreeNode) mainView.tree.getModel().getRoot(); // ���[�g�m�[�h

			if (root == null || root.getItemSummaryDisp() == null) {
				return;
			}

			String rootCode = root.getItemSummaryDisp().getCode(); // ���[�g�R�[�h

			// ���ɕ��������m�[�h���L�^����
			List<ItemSummaryTreeNode> listFinish = new ArrayList<ItemSummaryTreeNode>();

			// ���ׂĂ̑I�������폜����
			for (TreePath path : deletePaths) {

				if (path == null) {
					return;
				}

				Object obj = path.getLastPathComponent();

				if (!(obj instanceof ItemSummaryTreeNode)) {
					continue;
				}

				ItemSummaryTreeNode selectedNode = (ItemSummaryTreeNode) obj;
				if (selectedNode.getItemSummaryDisp() == null) {

					continue;

				} else {

					Enumeration enumNode = selectedNode.breadthFirstEnumeration();
					while (enumNode.hasMoreElements()) {
						ItemSummaryTreeNode node = (ItemSummaryTreeNode) enumNode.nextElement();

						if (listFinish.contains(node)) {
							continue;
						} else {
							listFinish.add(node);
						}

						if (node.getItemSummaryDisp().getItem() != null
							&& !Util.isNullOrEmpty(node.getItemSummaryDisp().getItem().getCode())) {

							// �ꗗ�ɒǉ�����
							mainView.dndTable.addRow(getItemDataRow(node.getItemSummaryDisp().getItem()));
						}
					}

				}

				// ���[�g�m�[�h���I������Ă��鏈���𒆒f����B
				String selectCode = selectedNode.getItemSummaryDisp().getCode();

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
	 * ���[�g�m�[�h���܂߂Ȃ��K�w�f�[�^���擾����
	 * 
	 * @return �K�w�f�[�^
	 */
	public List<ItemSummary> getTreeData() {

		List<ItemSummary> list = new ArrayList<ItemSummary>();

		ItemSummaryTreeNode root = (ItemSummaryTreeNode) mainView.tree.getModel().getRoot();

		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			ItemSummaryTreeNode node = (ItemSummaryTreeNode) e.nextElement();

			if (node.getItemSummaryDisp().getItemSummary() == null) {
				continue;
			}

			ItemSummaryDisp disp = node.getItemSummaryDisp().clone();// �ȖځA�ȖڏW�v�̂Q�̃f�[�^������
			ItemSummary bean = disp.getItemSummary();

			if (dummyRootCode.equals(bean.getSumCode())) {
				bean.setSumCode(null);
			}

			list.add(bean);

		}

		if (list.isEmpty()) {
			return null;
		}

		return list;
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
	 */
	protected void initEditView() {
		editView.setTitle(getWord("C00977"));// �ҏW���

		// ���ݒ�
		editView.ctrlKmtCode.setCode(editNode.getItemSummaryDisp().getItemSummary().getKmtCode());
		editView.ctrlKmtCode.setNames(editNode.getItemSummaryDisp().getItemSummary().getKmtName());
		editView.ctrlKmkCode.setCode(editNode.getItemSummaryDisp().getItemSummary().getKmkCode());
		editView.ctrlKmkCode.setNames(editNode.getItemSummaryDisp().getItemSummary().getKmkName());
		editView.ctrlKokName.setValue(editNode.getItemSummaryDisp().getItemSummary().getKokName());
		editView.ctrlSumCode.setCode(editNode.getItemSummaryDisp().getItemSummary().getSumCode());
		editView.ctrlSumCode.setNames(editNode.getItemSummaryDisp().getItemSummary().getSumName());
		editView.ctrlOdr.setValue(editNode.getItemSummaryDisp().getItemSummary().getOdr());
		if (editNode.getItemSummaryDisp().getItemSummary().getHyjKbn() == 1) {
			editView.ctrlHyjKbnVisible.setSelected(true);
		} else {
			editView.ctrlHyjKbnInvisible.setSelected(true);
		}
		editView.ctrlBeginDate.setValue(editNode.getItemSummaryDisp().getItemSummary().getDateFrom());
		editView.ctrlEndDate.setValue(editNode.getItemSummaryDisp().getItemSummary().getDateTo());

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

			// ���͂��ꂽ�ȖڏW�v�����擾
			ItemSummary bean = getInputedItemSummary();

			// ���͂��ꂽ�ȖڏW�v�����m�[�h�ɔ��f
			editNode.getItemSummaryDisp().setItemSummary(bean);

			editNode.setUserObject(ItemSummaryTreeNode.getName(editNode.getItemSummaryDisp()));

			// �m�[�h�f�[�^���c���[�ɔ��f�i�f�[�^���\�����j
			mainView.tree.reload();
			mainView.tree.repaint();
			mainView.tree.expandAll();

			// �ҏW��ʂ����
			btnEditViewClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�ȖڏW�v��Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�ȖڏW�v
	 */
	protected ItemSummary getInputedItemSummary() {

		ItemSummary bean = new ItemSummary();

		bean.setCompanyCode(super.getCompanyCode());
		bean.setKmtCode(editView.ctrlKmtCode.getCode());
		bean.setKmtName(editView.ctrlKmtCode.getNames());
		bean.setKmkCode(editView.ctrlKmkCode.getCode());
		bean.setKmkName(editView.ctrlKmkCode.getNames());
		bean.setKokName(editView.ctrlKokName.getValue());
		bean.setSumCode(editView.ctrlSumCode.getCode());
		bean.setSumName(editView.ctrlSumCode.getNames());
		bean.setOdr(editView.ctrlOdr.getValue());

		if (editView.ctrlHyjKbnVisible.isSelected()) {
			bean.setHyjKbn(1);
		} else {
			bean.setHyjKbn(0);
		}

		bean.setDateFrom(editView.ctrlBeginDate.getValue());
		bean.setDateTo(editView.ctrlEndDate.getValue());

		return bean;
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

		// ���\�Ȗږ��̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlKokName.getValue())) {
			showMessage(editView, "I00037", "C00624"); // ���\�Ȗږ���
			editView.ctrlKokName.requestTextFocus();
			return false;
		}

		// �o�͏����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlOdr.getValue())) {
			showMessage(editView, "I00037", "C00776"); // ���x���O
			editView.ctrlOdr.requestTextFocus();
			return false;
		}

		// �J�n�N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055"); // �J�n�N��������͂��Ă��������B
			editView.ctrlBeginDate.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261"); // �I���N��������͂��Ă��������B
			editView.ctrlEndDate.requestTextFocus();
			return false;
		}

		// �J�n�N����,�I���N�����`�F�b�N
		if (!Util.isSmallerThenByYMD(editView.ctrlBeginDate.getValue(), editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.ctrlBeginDate.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * ���ʉ�ʂ̃C�x���g��`�B
	 */
	protected void addCopyViewEvent() {

		// [�m��]�{�^������
		copyView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				copyView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopyViewSettle_Click();
				copyView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�߂�]�{�^������
		copyView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				copyView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopyViewClose_Click();
				copyView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * ���ʉ�ʂ�����������
	 */
	protected void initCopyView() {
		copyView.setTitle(getWord("C01738"));// ���ʉ��

		// ���ݒ�
		copyView.ctrlItemOrganizationReference.code.setValue(mainView.ctrlItemOrganizationReference.getCode());
		copyView.ctrlItemOrganizationReference.name.setValue(mainView.ctrlItemOrganizationReference.getNames());

		preKmtCode = mainView.ctrlItemOrganizationReference.getCode();
	}

	/**
	 * ���ʉ��[�m��]�{�^������
	 */
	protected void btnCopyViewSettle_Click() {

		try {

			// ���̓`�F�b�N���s��
			if (!isCopyViewInputRight()) {
				return;
			}

			// �m�F���b�Z�[�W
			// Q00078 �f�[�^���㏑������܂����A��낵���ł����H
			if (!showConfirmMessage(copyView, "Q00078")) {
				return;
			}

			// �Ȗڑ̌n�R�[�h�ύX�Ȃ��̏ꍇ�����s�v
			if (copyView.ctrlItemOrganizationReference.getCode().equals(preKmtCode)) {
				btnCopyViewClose_Click();
				return;
			}

			// �o�^����
			request(getItemSummaryModelClass(), "entryCopyItemSummary", preKmtCode,
				copyView.ctrlItemOrganizationReference.getCode());

			// ���͂��ꂽ�ȖڏW�v���𔽉f
			mainView.ctrlItemOrganizationReference.code.setValue(copyView.ctrlItemOrganizationReference.getCode());
			mainView.ctrlItemOrganizationReference.name.setValue(copyView.ctrlItemOrganizationReference.getNames());

			// �w����ʂ��Ĕ��f
			refresh();

			// ���ʉ�ʂ����
			btnCopyViewClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ���ʉ��[�߂�]�{�^������
	 */
	protected void btnCopyViewClose_Click() {
		copyView.setVisible(false);
	}

	/**
	 * ���ʉ�ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return ���ʉ�ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 */
	protected boolean isCopyViewInputRight() {

		// �Ȗڑ̌n�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(copyView.ctrlItemOrganizationReference.code.getValue())) {
			showMessage(copyView, "I00037", "C00609"); // �Ȗڑ̌n
			copyView.ctrlItemOrganizationReference.btn.requestFocus();
			return false;
		}

		// �Ȗڑ̌n�R�[�h���ύX�Ȃ��ꍇ�G���[
		if (copyView.ctrlItemOrganizationReference.getCode().equals(preKmtCode)) {
			// I00595 �����Ȗڑ̌n�ɕ��ʂł��܂���B
			showMessage(copyView, "I00595");
			copyView.ctrlItemOrganizationReference.btn.requestFocus();
			return false;
		}
		return true;
	}

	/**
	 * �Ȗڑ̌n�R�[�h�ɕR�Â��f�[�^���w����ʂɍĔ��f����
	 */
	protected void refresh() {

		try {
			// �ȖڏW�v���������̒�`
			ItemSummarySearchCondition itemSumCondition = getTreeSearchCondition();

			// DB����ȖڏW�v�����擾����
			List<ItemSummary> itemSumList;

			itemSumList = getItemSummary(itemSumCondition);

			// �Ȗڌ��������̒�`
			ItemSearchCondition itemCondition = getSearchCondition();

			// DB����Ȗڏ����擾����
			List<Item> itemList = getItem(itemCondition);

			// �c���[�\�z
			initTree(itemSumList);

			// �t�B���^�[
			if (itemList != null) {
				for (ItemSummary itemSum : itemSumList) {
					String itemSumCode = itemSum.getKmkCode();

					for (int i = itemList.size() - 1; i >= 0; i--) {
						if (itemSumCode.equals(itemList.get(i).getCode())) {
							itemList.remove(i);
							break;
						}
					}
				}
			}

			// �Ȗڈꗗ�֔��f
			initList(itemList);

			setMainButtonEnabled(!Util.isNullOrEmpty(mainView.ctrlItemOrganizationReference.getCode()));

			kmtCode = mainView.ctrlItemOrganizationReference.getCode();
			kmtName = mainView.ctrlItemOrganizationReference.getNames();

		} catch (Exception e) {
			errorHandler(e);
		}
	}
}
