package jp.co.ais.trans2.common.model.ui.slip;

/**
 * 仕訳明細パネル
 * 
 * @author AIS
 */
public class TFormSlipPatternDetailPanel extends TFormSlipDetailPanel {

	/**
	 * コンストラクタ.
	 * 
	 * @param parent 親
	 */
	public TFormSlipPatternDetailPanel(TSlipPatternPanel parent) {
		super(parent);
	}

	/**
	 * コントローラーの取得
	 * 
	 * @return コントローラー
	 */
	@Override
	public TFormSlipPatternDetailPanelCtrl createController() {
		return new TFormSlipPatternDetailPanelCtrl(this);
	}

	/**
	 * コントローラ取得
	 * 
	 * @return コントローラ
	 */
	@Override
	public TFormSlipPatternDetailPanelCtrl getController() {
		return (TFormSlipPatternDetailPanelCtrl) controller;
	}

}
