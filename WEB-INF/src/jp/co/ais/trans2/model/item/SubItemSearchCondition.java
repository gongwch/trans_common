package jp.co.ais.trans2.model.item;

/**
 * �⏕�Ȗڃ}�X�^��������
 * 
 * @author AIS
 */
public class SubItemSearchCondition extends ItemSearchCondition {

	/** �ȖڃR�[�h */
	protected String itemCode = null;

	/** �⏕�Ȗڂ����݂��Ȃ��Ȗڂ����o���邩 */
	protected boolean getNotExistSubItem = false;

	/** ����Ȗڂ��܂ނ� */
	protected boolean detailItem = false;

	/**
	 * �⏕�Ȗڂ����݂��Ȃ��Ȗڂ����o���邩��Ԃ��B<br>
	 * true�̏ꍇ�A�⏕�Ȗڂ������Ƃ��Ȗڂ�����Entity��Ԃ��B
	 * 
	 * @return �⏕�Ȗڂ����݂��Ȃ��Ȗڂ����o���邩
	 */
	public boolean isGetNotExistSubItem() {
		return getNotExistSubItem;
	}

	/**
	 * �⏕�Ȗڂ����݂��Ȃ��Ȗڂ����o���邩��ݒ肷��B<br>
	 * true�̏ꍇ�A�⏕�Ȗڂ������Ƃ��Ȗڂ�����Entity��Ԃ��B
	 * 
	 * @param getNotExistSubItem �⏕�Ȗڂ����݂��Ȃ��Ȗڂ����o���邩
	 */
	public void setGetNotExistSubItem(boolean getNotExistSubItem) {
		this.getNotExistSubItem = getNotExistSubItem;
	}

	/**
	 * �ȖڃR�[�hgetter
	 * 
	 * @return �ȖڃR�[�h
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * �ȖڃR�[�hsetter
	 * 
	 * @param itemCode �ȖڃR�[�h
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public SubItemSearchCondition clone() {
		return (SubItemSearchCondition) super.clone();
	}

	/**
	 * ����Ȗڂ��܂ނ�
	 * 
	 * @return detailItem
	 */
	public boolean isDetailItem() {
		return detailItem;
	}

	/**
	 * ����Ȗڂ��܂ނ�
	 * 
	 * @param detailItem
	 */
	public void setDetailItem(boolean detailItem) {
		this.detailItem = detailItem;
	}

}
