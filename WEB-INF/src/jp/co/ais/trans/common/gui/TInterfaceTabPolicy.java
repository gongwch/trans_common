package jp.co.ais.trans.common.gui;

import java.awt.event.KeyEvent;

/**
 * tab focus policy interface.
 */
public interface TInterfaceTabPolicy {

	/**
	 * enter key のpolicy使用
	 * 
	 * @return true:enter keyのpolicy使用 false:通常policy使用
	 */
	public boolean getModeEnter();

	/**
	 * enter key のpolicy使用
	 * 
	 * @param b true:enter keyのpolicy使用 false:通常policy使用
	 */
	public void setModeEnter(boolean b);

	/**
	 * 直前にアクセスされたコンポーネント setter.
	 * 
	 * @param evt キーイベント
	 */
	public void setEventBefore(KeyEvent evt);

	/**
	 * 直前にアクセスされたコンポーネント getter.
	 * 
	 * @return キーイベント
	 */
	public KeyEvent getEventBefore();
}
