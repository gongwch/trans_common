package jp.co.ais.trans2.common.gui.event;

/**
 * コンポーネントリスナー<br>
 * lostfocusやREFダイアログから値が選ばれた際に呼ばれる.
 */
public class TCallBackListener {

	/**
	 * 後処理
	 */
	public void before() {
		// Orverride用
	}

	/**
	 * 後処理
	 */
	public void after() {
		// Orverride用
	}

	/**
	 * 後処理
	 * 
	 * @param flag
	 */
	public void after(@SuppressWarnings("unused")
	boolean flag) {
		// Orverride用
	}

	/**
	 * 後処理(Verify用)
	 * 
	 * @param flag
	 * @return false:NG
	 */
	public boolean afterVerify(@SuppressWarnings("unused")
	boolean flag) {
		// Orverride用
		return true;
	}
}
