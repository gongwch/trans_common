package applet;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.master.ui.*;


/**
 * 取引先支払条件マスタテストクラス
 * 
 * @author AIS
 */
public class MG0155CustomerConditionMasterApplet extends TTestApplet {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Override
	public TController getController() {
		return new MG0155CustomerPaymentSettingMasterPanelCtrl();
	}

	/**
	 * プログラムIDを返す
	 * 
	 * @return プログラムID
	 */
	@Override
	protected String getProgramID() {
		return "MG0155";
	}

}
