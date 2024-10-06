package jp.co.ais.trans2.model.department;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �n������m�[�h�f�[�^
 */
public class DepartmentTransferData extends TransferBase {

	/**
	 * �c���[or�X�v���b�h����̃t���O
	 */
	public enum SourceType {
		/** �c���[ */
		TREE,
		/** �X�v���b�h */
		TABLE,
	}

	/** �c���[or�X�v���b�h����̃t���O */
	protected SourceType source = null;

	/** ����m�[�h���X�g */
	protected List<DepartmentTreeNode> nodeList = null;

	/**
	 * �c���[or�X�v���b�h����̃t���O�̎擾
	 * 
	 * @return source �c���[or�X�v���b�h����̃t���O
	 */
	public SourceType getSource() {
		return source;
	}

	/**
	 * �c���[or�X�v���b�h����̃t���O�̐ݒ�
	 * 
	 * @param source �c���[or�X�v���b�h����̃t���O
	 */
	public void setSource(SourceType source) {
		this.source = source;
	}

	/**
	 * ����m�[�h���X�g�̎擾
	 * 
	 * @return nodeList ����m�[�h���X�g
	 */
	public List<DepartmentTreeNode> getNodeList() {
		return nodeList;
	}

	/**
	 * ����m�[�h���X�g�̐ݒ�
	 * 
	 * @param nodeList ����m�[�h���X�g
	 */
	public void setNodeList(List<DepartmentTreeNode> nodeList) {
		this.nodeList = nodeList;
	}

}
