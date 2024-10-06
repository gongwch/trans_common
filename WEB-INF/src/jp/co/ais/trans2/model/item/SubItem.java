package jp.co.ais.trans2.model.item;

/**
 * •â•‰È–Ú
 * 
 * @author AIS
 */
public class SubItem extends Item {

	/** serialVersionUID */
	private static final long serialVersionUID = -1813034808256311886L;

	/** “à–ó‰È–Ú‚ğ‚Â‚© */
	protected boolean hasDetailItem = false;

	/** “à–ó‰È–Ú */
	protected DetailItem detailItem = null;

	/**
	 * ƒNƒ[ƒ“
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
	 * “à–ó‰È–Ú
	 * 
	 * @param detailItem “à–ó‰È–Ú
	 */
	public void setDetailItem(DetailItem detailItem) {
		this.detailItem = detailItem;

		this.hasDetailItem = detailItem != null;
	}

	/**
	 * “à–ó‰È–Ú‚ğ‚Â‚©
	 * 
	 * @return true:‚Â
	 */
	public boolean hasDetailItem() {
		return hasDetailItem;
	}

	/**
	 * “à–ó‰È–Ú‚ğ‚Â‚©
	 * 
	 * @param hasDetailItem true:‚Â
	 */
	public void setDetailItem(boolean hasDetailItem) {
		this.hasDetailItem = hasDetailItem;
	}

}
