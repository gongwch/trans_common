package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * 会計帳簿
 * 
 * @author AIS
 */
public enum AccountBook implements TEnumRadio {

	/** 両方 */
	BOTH(0),

	/** 自国帳簿 */
	OWN(1),

	/** IFRS帳簿 */
	IFRS(2);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private AccountBook(int value) {
		this.value = value;
	}

	/**
	 * 自国帳簿を表すかどうか
	 * 
	 * @return true:自国帳簿
	 */
	public boolean isOWN() {
		return isOWN(value);
	}

	/**
	 * IFRS帳簿を表すかどうか
	 * 
	 * @return true:IFRS帳簿
	 */
	public boolean isIFRS() {
		return isIFRS(value);
	}

	/**
	 * タイプ取得
	 * 
	 * @param ak 実値
	 * @return タイプ
	 */
	public static AccountBook get(int ak) {
		for (AccountBook em : values()) {
			if (em.value == ak) {
				return em;
			}
		}
		return null;
	}

	/**
	 * 指定値が自国帳簿を表すかどうか
	 * 
	 * @param value 値
	 * @return true:自国帳簿
	 */
	public static boolean isOWN(int value) {
		AccountBook book = get(value);

		return book.equals(BOTH) || book.equals(OWN);
	}

	/**
	 * 指定値がIFRS帳簿を表すかどうか
	 * 
	 * @param value 値
	 * @return true:IFRS帳簿
	 */
	public static boolean isIFRS(int value) {
		AccountBook book = get(value);

		return book.equals(BOTH) || book.equals(IFRS);
	}

	/**
	 * 帳簿名称を返す
	 * 
	 * @param accountBook 会計帳簿
	 * @return 更新区分名称
	 */
	public static String getAccountBookName(AccountBook accountBook) {

		switch (accountBook) {
			case OWN:
				return "C10982"; // 自国会計帳簿
			case IFRS:
				return "C10983"; // IFRS会計帳簿
			default:
				return null;
		}
	}

	/**
	 * 表示名の取得
	 */
	@Override
	public String getName() {
		return getAccountBookName(this);
	}

}
