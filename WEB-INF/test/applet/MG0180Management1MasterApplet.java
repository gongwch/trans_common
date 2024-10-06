package applet;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.master.ui.*;


/**
 * 管理1マスタテストクラス
 * @author AIS
 *
 */
public class MG0180Management1MasterApplet extends TTestApplet{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public TController getController() {
		return new MG0180Management1MasterPanelCtrl();
	}

}
