package jp.co.ais.trans2.model.item;

/**
 * 補助科目
 * 
 * @author AIS
 */
public class SubItem extends Item {

	/** serialVersionUID */
	private static final long serialVersionUID = -1813034808256311886L;

	/** 内訳科目を持つか */
	protected boolean hasDetailItem = false;

	/** 内訳科目 */
	protected DetailItem detailItem = null;

	/**
	 * クローン
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
	 * 内訳科目
	 * 
	 * @param detailItem 内訳科目
	 */
	public void setDetailItem(DetailItem detailItem) {
		this.detailItem = detailItem;

		this.hasDetailItem = detailItem != null;
	}

	/**
	 * 内訳科目を持つか
	 * 
	 * @return true:持つ
	 */
	public boolean hasDetailItem() {
		return hasDetailItem;
	}

	/**
	 * 内訳科目を持つか
	 * 
	 * @param hasDetailItem true:持つ
	 */
	public void setDetailItem(boolean hasDetailItem) {
		this.hasDetailItem = hasDetailItem;
	}

}
