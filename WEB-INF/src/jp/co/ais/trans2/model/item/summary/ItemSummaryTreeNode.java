package jp.co.ais.trans2.model.item.summary;

import javax.swing.tree.*;

/**
 * DefaultMutableTreeNode�N���X���p�����č쐬�B<br>
 * Drag��Drop���AItemSumTreeNode��]�����邽�߂Ɏg�p����B
 * 
 * @author Yanwei
 */
public class ItemSummaryTreeNode extends DefaultMutableTreeNode {

	/** �m�[�h��� */
	protected ItemSummaryDisp itemSummaryDisp;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param itemSummaryDisp �m�[�h���
	 */
	public ItemSummaryTreeNode(ItemSummaryDisp itemSummaryDisp) {

		// �@�p�����̃R���X�g���N�^���ĂԁB
		super(getName(itemSummaryDisp));

		// �A������DnDData���v���p�e�B�ɃZ�b�g����B
		this.itemSummaryDisp = itemSummaryDisp;
	}

	/**
	 * �ȖڏW�v���̎擾
	 * 
	 * @param itemSummaryDisp
	 * @return �ȖڏW�v��
	 */
	public static String getName(ItemSummaryDisp itemSummaryDisp) {

		return itemSummaryDisp.getViewName();

	}

	/**
	 * �m�[�h���
	 * 
	 * @return �m�[�h���
	 */
	public ItemSummaryDisp getItemSummaryDisp() {
		return itemSummaryDisp;
	}

	/**
	 * �m�[�h���
	 * 
	 * @param itemSummaryDisp �m�[�h���ݒ肷��
	 */
	public void setItemSummaryDisp(ItemSummaryDisp itemSummaryDisp) {
		this.itemSummaryDisp = itemSummaryDisp;
	}

}