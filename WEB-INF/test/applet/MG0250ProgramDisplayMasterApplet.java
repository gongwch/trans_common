package applet;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.master.ui.*;

/**
 * プログラム表示設定マスタテストクラス
 * @author AIS
 *
 */
public class MG0250ProgramDisplayMasterApplet extends TTestApplet{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public TController getController() {
		return new MG0250ProgramDisplayMasterPanelCtrl();
	}

}
