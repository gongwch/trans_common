package jp.co.ais.trans2.common.model.ui.slip;

/**
 * �d�󖾍ד��̓p�l��
 * 
 * @author AIS
 */
public class TFormDCPatternInputPanel extends TFormDCInputPanel {

	/**
	 * �R���g���[���쐬
	 */
	@Override
	protected void createController() {
		controller = new TFormDCPatternInputPanelCtrl(this);
	}

}
