package jp.co.ais.trans2.common.gui.ac;

/**
 * 自動完成可能I/F
 */
public interface AutoCompletable {

	/**
	 * @return 表示名取得
	 */
	public String getName();

	/**
	 * @return コード取得
	 */
	public String getCode();

	/**
	 * @return インクリメントサーチ表示値
	 */
	public String getDisplayText();

}
