package jp.co.ais.trans.common.gui;

/**
 * タブ順制御 interface. 
 * <br>
 * item にtab移動順番を設定する。 
 * 数値の小さいitemから大きいitemに順に移動する。
 * 同じ値のitemは、どちらが先になるか規定しない。
 */
public interface TInterfaceTabControl {

	/**
	 * GUIアイテムに設定されたタブ移動順番号取得
	 * 
	 * @return int タブ順番号
	 */
	int getTabControlNo();

	/**
	 * GUIアイテムにタブ移動順番号を設定
	 * 
	 * @param tabControlNo タブ順番号
	 */
	void setTabControlNo(int tabControlNo);

	/**
	 * タブ移動でフォーカス有効かを設定
	 * 
	 * @param bool true:有効 false:無効
	 */
	void setTabEnabled(boolean bool);
	
	/**
	 * タブ移動でフォーカス有効かを取得
	 * 
	 * @return true:有効 false:無効
	 */
	boolean isTabEnabled();
}
