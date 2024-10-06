package jp.co.ais.trans2.common.gui.table;

/**
 * スプレッドシート上のコンポーネント表示操作アダプター
 */
public abstract class TComponentViewAdapter {

	/**
	 * ブランク判定
	 * 
	 * @param row 行
	 * @param column 列
	 * @return true:ブランク
	 */
	@SuppressWarnings("unused")
	public boolean isBlank(int row, int column) {
		return false;
	}

	/**
	 * 使用可能判定
	 * 
	 * @param row 行
	 * @param column 列
	 * @return true:使用可
	 */
	@SuppressWarnings("unused")
	public boolean isEnable(int row, int column) {
		return true;
	}
}
