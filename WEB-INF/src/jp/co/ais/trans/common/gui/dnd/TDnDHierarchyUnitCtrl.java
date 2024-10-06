package jp.co.ais.trans.common.gui.dnd;

import java.util.*;

import javax.swing.tree.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;

/**
 * �K�w�f�[�^�쐬��ՃR���|�[���l���g�R���g���[��
 */
public class TDnDHierarchyUnitCtrl extends TAppletClientBase {

	/** �K�w�f�[�^�쐬��ՃR���|�[���l���gPanle */
	protected TDnDHierarchyUnit panel;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param panel
	 */
	public TDnDHierarchyUnitCtrl(TDnDHierarchyUnit panel) {
		try {

			this.panel = panel;
		} catch (Exception e) {
			errorHandler(panel, e);
		}
	}

	/**
	 * �K�w�f�[�^���폜���� <br>
	 * DELETE�L�[ �������̏����B�K�w�f�[�^����I���m�[�h���폜����B
	 * 
	 * @return �K�w�f�[�^����I���m�[�h
	 */
	public List<DnDData> doDeleteTreeData() {
		THierarchyTreeNode selectedNode = (THierarchyTreeNode) panel.tree.getLastSelectedPathComponent();

		// �m�[�h���I������Ă��Ȃ�,�����𒆒f����B
		if (selectedNode == null) {
			return null;
		}

		// ���[�g�m�[�h���I������Ă���,�����𒆒f����B
		THierarchyTreeNode root = (THierarchyTreeNode) panel.tree.getModel().getRoot();
		String rootCode = root.getDndData().getCode();
		String selectCode = selectedNode.getDndData().getCode();
		if (rootCode.equals(selectCode)) {
			return null;
		}

		// Q�F�I�����ꂽ���ڈȉ����폜���܂����H
		if (!showConfirmMessage(panel, "Q00051")) {
			return null;
		}

		// �ꗗ�ɒǉ�����
		List<DnDData> listDndData = new ArrayList<DnDData>();

		Enumeration e = selectedNode.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			THierarchyTreeNode node = (THierarchyTreeNode) e.nextElement();
			listDndData.add(node.getDndData());
		}
		// �ꗗ�Ƀf�[�^��ǉ�����
		for (DnDData dndData : listDndData) {
			panel.ssJournal.addRow(dndData);
		}
		// �ꗗ���\�[�g����
		panel.ssJournal.doSort();

		// �I���m�[�h���폜��
		DefaultTreeModel model = (DefaultTreeModel) panel.tree.getModel();
		model.removeNodeFromParent(selectedNode);

		// �폜��A�K�w�f�[�^�ł̓��[�g�m�[�h��I����Ԃɂ���B
		TreePath visiblePath = new TreePath(model.getPathToRoot(root));
		panel.tree.setSelectionPath(visiblePath);

		return listDndData;
	}

	/**
	 * �K�w�f�[�^���擾����
	 * 
	 * @return �K�w�f�[�^
	 */
	public List<DnDData> getTreeData() {
		List<DnDData> listDndData = new ArrayList<DnDData>();

		THierarchyTreeNode root = (THierarchyTreeNode) panel.tree.getModel().getRoot();

		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			THierarchyTreeNode node = (THierarchyTreeNode) e.nextElement();
			DnDData dndData = node.getDndData();
			if (!node.equals(root)) {
				dndData.setTopCode(((THierarchyTreeNode) node.getParent()).getDndData().getCode());
			} else {
				dndData.setTopCode("");
			}
			listDndData.add(dndData);
		}

		return listDndData;
	}

	/**
	 * ���[�g�m�[�h���܂߂Ȃ��K�w�f�[�^���擾����
	 * 
	 * @return �K�w�f�[�^
	 */
	public List<DnDData> getTreeDataNoRoot() {
		List<DnDData> listDndData = new ArrayList<DnDData>();

		THierarchyTreeNode root = (THierarchyTreeNode) panel.tree.getModel().getRoot();

		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			THierarchyTreeNode node = (THierarchyTreeNode) e.nextElement();
			if (!node.equals(root)) {
				THierarchyTreeNode parentNode = (THierarchyTreeNode) (node.getParent());

				// ��ʊK�w�R�[�h = ���[�g�m�[�h
				if (parentNode.equals(root)) {
					DnDData dndData = new DnDData();
					dndData.setCode(node.getDndData().getCode());
					dndData.setName(node.getDndData().getName());
					dndData.setTopCode(""); // ��ʊK�w�R�[�h = �u�����N
					listDndData.add(dndData);
				} else {
					DnDData dndData1 = node.getDndData();
					dndData1.setTopCode(parentNode.getDndData().getCode());
					listDndData.add(dndData1);
				}
			}
		}

		return listDndData;
	}

	/**
	 * �K�w�f�[�^��ݒ肷��
	 * 
	 * @param listDnDData �K�w�f�[�^
	 */
	public void setTreeData(List<DnDData> listDnDData) {
		int rootNum = 0;
		THierarchyTreeNode baseNode = null;

		DefaultTreeModel model = null;

		// ��ʊK�w�R�[�h = �u�����N �̃f�[�^�̓��[�g�m�[�h�ɐݒ肷��B
		for (DnDData ndnData : listDnDData) {
			if (Util.isNullOrEmpty(ndnData.getTopCode())) {
				panel.baseDndData = ndnData;

				baseNode = new THierarchyTreeNode(ndnData);
				model = new DefaultTreeModel(baseNode);
				panel.tree.setModel(model);
				rootNum = rootNum + 1;
			}
		}

		if (baseNode == null || rootNum > 1) {
			this.errorHandler(panel, "E00009");
			return;
		}

		// �q�K�w�f�[�^��ݒ肷��
		createTree(baseNode, panel.baseDndData, listDnDData);

		model.reload();
		TreePath visiblePath = new TreePath(((DefaultTreeModel) panel.tree.getModel()).getPathToRoot(baseNode));
		panel.tree.setSelectionPath(visiblePath);

		// ���ׂēW�J���܂�
		TreeNode root = (TreeNode) panel.tree.getModel().getRoot();
		panel.tree.expandAll(new TreePath(root), true);
	}

	/**
	 * �K�w�f�[�^��ݒ肷��
	 * 
	 * @param parentNode
	 * @param parent
	 * @param listDnDData �K�w�f�[�^
	 */
	private void createTree(THierarchyTreeNode parentNode, DnDData parent, List<DnDData> listDnDData) {
		for (DnDData dndData : listDnDData) {

			if (parent.getCode().equals(dndData.getTopCode())) {
				THierarchyTreeNode childNode = new THierarchyTreeNode(dndData);
				parentNode.add(childNode);

				createTree(childNode, dndData, listDnDData);
			}
		}
	}
}
