package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * ドリルダウン用コンポーネントリスナー<br>
 * ドリルダウンで伝票状態変更される際に呼ばれる.
 */
public class TDrillDownSlipStateListener extends TCallBackListener {

	/**
	 * 伝票状態変更後処理
	 * 
	 * @param slipDen
	 * @param row
	 */
	@SuppressWarnings("unused")
	public void updateSlipState(SlipDen slipDen, int row) {
		// Override用
	}
}
