package jp.co.ais.trans2.model.item;

/**
 * ����Ȗڃ}�X�^��������
 * 
 * @author AIS
 */
public class DetailItemSearchCondition extends SubItemSearchCondition {

	/** �⏕�ȖڃR�[�h */
	protected String subItemCode = null;

	/** ����Ȗڂ����݂��Ȃ��Ȗڂ����o���邩 */
	protected boolean getNotExistDetailItem = false;

	/**
	 * ����Ȗڂ����݂��Ȃ��Ȗڂ����o���邩��Ԃ��B<br>
	 * true�̏ꍇ�A����Ȗڂ������Ƃ��Ȗڂ�����Entity��Ԃ��B
	 * 
	 * @return ����Ȗڂ����݂��Ȃ��Ȗڂ����o���邩
	 */
	public boolean isGetNotExistDetailItem() {
		return getNotExistDetailItem;
	}

	/**
	 * ����Ȗڂ����݂��Ȃ��Ȗڂ����o���邩��ݒ肷��B<br>
	 * true�̏ꍇ�A����Ȗڂ������Ƃ��Ȗڂ�����Entity��Ԃ��B
	 * 
	 * @param getNotExistDetailItem ����Ȗڂ����݂��Ȃ��Ȗڂ����o���邩
	 */
	public void setGetNotExistDetailItem(boolean getNotExistDetailItem) {
		this.getNotExistDetailItem = getNotExistDetailItem;
	}

	/**
	 * �⏕�ȖڃR�[�hgetter
	 * 
	 * @return �⏕�ȖڃR�[�h
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * �⏕�ȖڃR�[�hsetter
	 * 
	 * @param subItemCode �⏕�ȖڃR�[�h
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public DetailItemSearchCondition clone() {
		return (DetailItemSearchCondition) super.clone();
	}

}
