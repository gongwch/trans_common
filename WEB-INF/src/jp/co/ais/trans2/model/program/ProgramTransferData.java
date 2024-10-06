package jp.co.ais.trans2.model.program;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �n���v���O�����m�[�h�f�[�^
 */
public class ProgramTransferData extends TransferBase {

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

	/** �v���O�����m�[�h���X�g */
	protected List<ProgramTreeNode> nodeList = null;

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
	 * �v���O�����m�[�h���X�g�̎擾
	 * 
	 * @return nodeList �v���O�����m�[�h���X�g
	 */
	public List<ProgramTreeNode> getNodeList() {
		return nodeList;
	}

	/**
	 * �v���O�����m�[�h���X�g�̐ݒ�
	 * 
	 * @param nodeList �v���O�����m�[�h���X�g
	 */
	public void setNodeList(List<ProgramTreeNode> nodeList) {
		this.nodeList = nodeList;
	}

}
