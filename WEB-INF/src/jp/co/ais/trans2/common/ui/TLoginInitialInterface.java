package jp.co.ais.trans2.common.ui;

/**
 * ログイン時の初期処理I/F
 */
public interface TLoginInitialInterface {

	/**
	 * 処理名
	 * 
	 * @return 処理名
	 */
	public String getName();

	/**
	 * 初期処理
	 */
	public void init();
}
