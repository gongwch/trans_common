package jp.co.ais.trans.common.gui.message;

/**
 * フォーカスボタンEnum
 */
public enum FocusButton {

	/** はい */
	YES(0),

	/** いいえ */
	NO(1);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private FocusButton(int value) {
		this.value = value;
	}
}
