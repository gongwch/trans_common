package jp.co.ais.trans2.common.ui;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * TAB可能なダイアログ
 */
public interface TTabbedDialog {

	/**
	 * @return ダイアログ
	 */
	public TDialog getDialog();

	/**
	 * @return 表示用の上位パネル
	 */
	public TTabbedPanel getDialogBasePanel();

	/**
	 * Ctrlを返す
	 * 
	 * @return Ctrl
	 */
	public TController getController();

	/**
	 * @return 起動済みPGと認めるキー
	 */
	public String getUID();

	/**
	 * @return プログラムコード
	 */
	public String getProgramCode();

	/**
	 * @return プログラム名
	 */
	public String getProgramName();

	/**
	 * 画面表示前に基礎パネルの初期化
	 */
	public void initDialogBasePanel();

	/**
	 * 表示処理
	 * 
	 * @param b
	 */
	public void setVisible(boolean b);

	/**
	 * ACTIVE処理
	 */
	public void active();
}
