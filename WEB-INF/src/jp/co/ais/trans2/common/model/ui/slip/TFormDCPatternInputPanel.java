package jp.co.ais.trans2.common.model.ui.slip;

/**
 * 仕訳明細入力パネル
 * 
 * @author AIS
 */
public class TFormDCPatternInputPanel extends TFormDCInputPanel {

	/**
	 * コントローラ作成
	 */
	@Override
	protected void createController() {
		controller = new TFormDCPatternInputPanelCtrl(this);
	}

}
