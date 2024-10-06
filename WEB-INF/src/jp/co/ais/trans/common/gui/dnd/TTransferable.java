package jp.co.ais.trans.common.gui.dnd;

import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.io.*;
import java.util.*;

/**
 * TTransferable�N���X���p�����č쐬�B<br>
 * Drag��Drop���ATHierarchyTreeNode��]�����邽�߂Ɏg�p����B
 * 
 * @author Yanwei
 */
public class TTransferable implements Transferable {

	/** �m�[�h����pDataFlavor ���� */
	public static final String FLAVOR_NAME = "THierarchyTreeNode";

	/** �m�[�h����pDataFlavor */
	public static final DataFlavor nodeFavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, FLAVOR_NAME);

	/** TTransferable�ŃT�|�[�g���Ă���DataFlavor */
	private static final DataFlavor[] supportedFlavors = { nodeFavor };

	/** �]���f�[�^ */
	public List<THierarchyTreeNode> transferDataList;

	/**
	 * �R���X�g���N�^ Tree��Table�ATree��Tree�p
	 * 
	 * @param transferData
	 */
	public TTransferable(THierarchyTreeNode transferData) {

		List<THierarchyTreeNode> list = new ArrayList<THierarchyTreeNode>();
		list.add(transferData);

		// TTransferable(list);
		this.transferDataList = list;

	}

	/**
	 * �R���X�g���N�^ Tree��Table�ATree��Tree�p
	 * 
	 * @param list
	 */
	public TTransferable(List list) {
		if (list == null || list.isEmpty()) {
			return;
		}

		Object obj = list.get(0);
		// List<THierarchyTreeNode>�̏ꍇ,����.list���v���p�e�B.transferDataList�ɃZ�b�g����
		if (obj instanceof THierarchyTreeNode) {
			this.transferDataList = list;

		}
		// List<DnDData>�̏ꍇ,����.list������THierarchyTreeNode���쐬���A�v���p�e�B.transferDataList�ɃZ�b�g����
		else if (obj instanceof DnDData) {
			List<THierarchyTreeNode> tmpList = new ArrayList<THierarchyTreeNode>();
			for (int i = 0; i < list.size(); i++) {

				DnDData dnDData = (DnDData) list.get(i);
				THierarchyTreeNode tHierarchyTreeNode = new THierarchyTreeNode(dnDData);
				tmpList.add(tHierarchyTreeNode);
			}
			this.transferDataList = tmpList;
		}
	}

	/**
	 * �I�[�o�[���C�h���\�b�h �]���f�[�^���擾����
	 */
	public Object getTransferData(DataFlavor df) {
		if (isDataFlavorSupported(df)) {
			return transferDataList;
		} else {
			return null;
		}
	}

	/**
	 * �I�[�o�[���C�h���\�b�h �����\��DataFlavor�����f����
	 */
	public boolean isDataFlavorSupported(DataFlavor df) {
		return (df.equals(nodeFavor));
	}

	/**
	 * �]���f�[�^���m�[�h�`���Ŏ擾����
	 * 
	 * @param transferable
	 * @return �]���f�[�^
	 */
	public static List<THierarchyTreeNode> getTHerarchyTreeNodeList(Transferable transferable) {
		try {
			return (List<THierarchyTreeNode>) transferable.getTransferData(nodeFavor);

		} catch (UnsupportedFlavorException e) {
			// �����Ȃ�
		} catch (IOException e) {
			// �����Ȃ�
		} catch (InvalidDnDOperationException e) {
			// �����Ȃ�
		}

		return null;
	}

	/**
	 * �]���f�[�^��DnDData�`���Ŏ擾����
	 * 
	 * @param transferable
	 * @return List<DnDData>TT��ee�̊K�w�f�[�^�󂯓n���Ɏg�p����N���X
	 */
	public static List<DnDData> getDnDDataList(Transferable transferable) {
		try {
			List<DnDData> listDnDData = new ArrayList<DnDData>();

			// �]���f�[�^�擾
			Object obj = transferable.getTransferData(nodeFavor);

			// �@ ������Transferable�ŕێ����Ă���f�[�^���擾���AObject �� List<THerarchyTreeNode>�ɕϊ����ĕԂ��B
			List<THierarchyTreeNode> list = (List<THierarchyTreeNode>) obj;
			if (list == null || list.isEmpty()) {
				return null;
			}

			// �A �@�̊eTHierarchyTreeNode�ŕێ����Ă���DnDData���擾����B
			for (THierarchyTreeNode treeNode : list) {
				DnDData dndData = new DnDData();
				dndData.setCode(treeNode.getDndData().getCode());
				dndData.setName(treeNode.getDndData().getName());
				dndData.setTopCode("");// �B DnDData�̏�ʊK�w�R�[�h���N���A����

				listDnDData.add(dndData);//
			}

			// �C DnDData�����X�g�`���ɂ��ĕԂ�
			return listDnDData;

		} catch (UnsupportedFlavorException e) {
			// �����Ȃ�
		} catch (IOException e) {
			// �����Ȃ�
		} catch (InvalidDnDOperationException e) {
			// �����Ȃ�
		}

		return null;
	}

	/**
	 * @�T�|�[�g���Ă���^��n��
	 */
	public DataFlavor[] getTransferDataFlavors() {
		return supportedFlavors;
	}

}