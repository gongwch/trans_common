package jp.co.ais.trans2.model.item.summary;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �n���Ȗڃm�[�h�f�[�^
 */
public class ItemSummaryTransferData extends TransferBase {

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

	/** �Ȗڃm�[�h���X�g */
	protected List<ItemSummaryTreeNode> nodeList = null;

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
	 * �Ȗڃm�[�h���X�g�̎擾
	 * 
	 * @return nodeList �Ȗڃm�[�h���X�g
	 */
	public List<ItemSummaryTreeNode> getNodeList() {
		return nodeList;
	}

	/**
	 * �Ȗڃm�[�h���X�g�̐ݒ�
	 * 
	 * @param nodeList �Ȗڃm�[�h���X�g
	 */
	public void setNodeList(List<ItemSummaryTreeNode> nodeList) {
		this.nodeList = nodeList;
	}

}
