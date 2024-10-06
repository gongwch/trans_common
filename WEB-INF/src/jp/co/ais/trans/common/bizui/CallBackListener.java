package jp.co.ais.trans.common.bizui;

import java.awt.*;

/**
 * コンポーネントリスナー<br>
 * lostfocusやREFダイアログから値が選ばれた際に呼ばれる.
 * 古いバージョン<br>
 * 新しいバージョン:{@link jp.co.ais.trans2.common.gui.event.TCallBackListener}
 */
public class CallBackListener {

	/**
	 * 後処理
	 */
	public void before() {
		// Orverride用
	}

	/**
	 * 後処理
	 */
	public void before(@SuppressWarnings("unused") Component component) {
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
	 */
	public void after(@SuppressWarnings("unused") Component component) {
		// Orverride用
	}

	/**
	 * 後処理
	 * 
	 * @param flag
	 */
	public void after(@SuppressWarnings("unused") boolean flag) {
		// Orverride用
	}
}
