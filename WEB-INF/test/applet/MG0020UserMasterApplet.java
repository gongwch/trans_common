package applet;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.master.ui.*;

/**
 * ユーザーマスタテストクラス
 * @author AIS
 *
 */
public class MG0020UserMasterApplet extends TTestApplet{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public TController getController() {
		return new MG0020UserMasterPanelCtrl();
	}

}
