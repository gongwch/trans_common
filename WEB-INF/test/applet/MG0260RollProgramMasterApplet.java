package applet;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.master.ui.*;

/**
 * プログラムマスタテストクラス
 * 
 * @author AIS
 */
public class MG0260RollProgramMasterApplet extends TTestApplet {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Override
	public TController getController() {
		return new MG0260ProgramRoleMasterPanelCtrl();
	}

}
