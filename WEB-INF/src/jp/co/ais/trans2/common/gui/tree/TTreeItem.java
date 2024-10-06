package jp.co.ais.trans2.common.gui.tree;

import jp.co.ais.trans.common.dt.TransferBase;

/**
 * Tree�̃m�[�h�ɃZ�b�g����A�C�e��
 * 
 * @author AIS
 */
public class TTreeItem extends TransferBase {

	/** serialVersionUID */
	private static final long serialVersionUID = -1628401786423721107L;

	/** �m�[�h�Ɋi�[����A�C�e�� */
	protected Object obj = null;

	/** ��ʏ�ɕ\�����镶�� */
	protected String caption = null;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param caption
	 * @param obj
	 */
	public TTreeItem(String caption, Object obj) {
		this.caption = caption;
		this.obj = obj;
	}

	/**
	 * �\����
	 */
	@Override
	public String toString() {
		return caption;
	}

	/**
	 * @return caption��߂��܂��B
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption caption��ݒ肵�܂��B
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return obj��߂��܂��B
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * @param obj obj��ݒ肵�܂��B
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

}
