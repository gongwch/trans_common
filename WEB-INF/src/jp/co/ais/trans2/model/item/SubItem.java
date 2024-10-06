package jp.co.ais.trans2.model.item;

/**
 * �⏕�Ȗ�
 * 
 * @author AIS
 */
public class SubItem extends Item {

	/** serialVersionUID */
	private static final long serialVersionUID = -1813034808256311886L;

	/** ����Ȗڂ����� */
	protected boolean hasDetailItem = false;

	/** ����Ȗ� */
	protected DetailItem detailItem = null;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public SubItem clone() {
		SubItem clone = (SubItem) super.clone();

		if (hasDetailItem && detailItem != null) {
			clone.setDetailItem(detailItem.clone());
		}

		return clone;
	}

	/**
	 * @see jp.co.ais.trans2.model.item.Item#getDetailItem()
	 */
	public DetailItem getDetailItem() {
		return detailItem;
	}

	/**
	 * ����Ȗ�
	 * 
	 * @param detailItem ����Ȗ�
	 */
	public void setDetailItem(DetailItem detailItem) {
		this.detailItem = detailItem;

		this.hasDetailItem = detailItem != null;
	}

	/**
	 * ����Ȗڂ�����
	 * 
	 * @return true:����
	 */
	public boolean hasDetailItem() {
		return hasDetailItem;
	}

	/**
	 * ����Ȗڂ�����
	 * 
	 * @param hasDetailItem true:����
	 */
	public void setDetailItem(boolean hasDetailItem) {
		this.hasDetailItem = hasDetailItem;
	}

}
