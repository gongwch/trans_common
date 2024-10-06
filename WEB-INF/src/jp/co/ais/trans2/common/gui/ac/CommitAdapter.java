package jp.co.ais.trans2.common.gui.ac;

import jp.co.ais.trans2.model.*;

/**
 * 
 */
public class CommitAdapter implements CommitListener {

	/**
	 * 値選択後の処理
	 * 
	 * @param value
	 */
	public void commit(Object value) {
		//
	}

	/**
	 * プッシュする値を取得
	 * 
	 * @param value
	 * @return 設定値
	 */
	public String getText(Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof AutoCompletable) {
			return ((AutoCompletable) value).getName();
		} else if (value instanceof TReferable) {
			return ((TReferable) value).getNames();
		}

		return value.toString();
	}

	/**
	 * @return true: push処理を実行する
	 */
	public boolean isDoPush() {
		return false;
	}

}
