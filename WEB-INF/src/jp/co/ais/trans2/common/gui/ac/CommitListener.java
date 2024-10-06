package jp.co.ais.trans2.common.gui.ac;

/**
 * 値処理
 */
public interface CommitListener {

	/**
	 * 値選択後の処理
	 * 
	 * @param value
	 */
	public void commit(Object value);

	/**
	 * プッシュする値を取得
	 * 
	 * @param value
	 * @return 設定値
	 */
	public String getText(Object value);

	/**
	 * @return true: push処理を実行する
	 */
	public boolean isDoPush();
}
