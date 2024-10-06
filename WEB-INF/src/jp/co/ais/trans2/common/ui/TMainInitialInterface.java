package jp.co.ais.trans2.common.ui;

import java.util.*;

import jp.co.ais.trans.common.gui.*;

/**
 * MAIN画面初期化時の初期処理I/F
 */
public interface TMainInitialInterface extends TLoginInitialInterface {

	/**
	 * 処理名
	 * 
	 * @return 処理名
	 */
	public String getName();

	/**
	 * 初期化処理(TMain画面作成前)
	 */
	public void init();

	/**
	 * 開始処理(TMain画面作成後)
	 */
	public void afterCreate();

	/**
	 * @return 表示上ボタン
	 */
	public List<TButton> getAddonButtonList();
}
