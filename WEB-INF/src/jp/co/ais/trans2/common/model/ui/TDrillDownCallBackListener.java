package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * ドリルダウン用コンポーネントリスナー<br>
 * ドリルダウンが表示される際に呼ばれる.
 */
public class TDrillDownCallBackListener extends TCallBackListener {

	/**
	 * 伝票ヘッダ変更後処理
	 * 
	 * @param hdr 伝票ヘッダ情報
	 */
	public void changedSlipHeader(@SuppressWarnings("unused") SWK_HDR hdr) {
		// Orverride用
	}
}
