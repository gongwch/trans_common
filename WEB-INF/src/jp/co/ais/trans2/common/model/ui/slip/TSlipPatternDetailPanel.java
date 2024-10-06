package jp.co.ais.trans2.common.model.ui.slip;

/**
 * 伝票パターン明細コンポーネント
 */
public class TSlipPatternDetailPanel extends TSlipDetailPanel {

	/**
	 * コントローラーの取得
	 * 
	 * @return コントローラー
	 */
	@Override
	protected TSlipDetailPanelCtrl createController() {
		return new TSlipPatternDetailPanelCtrl(this);
	}

}
