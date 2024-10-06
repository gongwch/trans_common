package jp.co.ais.trans2.common.model.ui.slip;

/**
 * 仕訳明細コントローラ
 * 
 * @author AIS
 */
public class TFormSlipPatternDetailPanelCtrl extends TFormSlipDetailPanelCtrl {

	/**
	 * コンストラクタ.
	 * 
	 * @param panel パネル
	 */
	protected TFormSlipPatternDetailPanelCtrl(TFormSlipPatternDetailPanel panel) {
		super(panel);
	}

	/**
	 * 画面表示初期処理
	 */
	@Override
	protected void initView() {
		super.initView();

		// 債務/債権残高/BS勘定非表示
		getView().pnlHeader.setVisible(false);
	}

	@Override
	public TFormSlipPatternDetailPanel getView() {
		return (TFormSlipPatternDetailPanel) panel;
	}

	/**
	 * @return T-Form 入力Panel
	 */
	@Override
	protected TFormDCInputPanel createTFormDCInputPanel() {
		return new TFormDCPatternInputPanel();
	}
}
