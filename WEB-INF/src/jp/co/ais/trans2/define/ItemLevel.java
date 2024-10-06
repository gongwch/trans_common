package jp.co.ais.trans2.define;

/**
 * ‰È–Ú‚ÌƒŒƒxƒ‹
 * @author AIS
 *
 */
public enum ItemLevel {
	ITEM(0),
	SUBITEM(1),
	DETAILITEM(2);

	@SuppressWarnings("unused")
	private int value;

	private ItemLevel(int value) {
		this.value = value;
	}

}

