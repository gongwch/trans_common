package applet;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.master.ui.*;


/**
 * “`•[Ží•Êƒ}ƒXƒ^
 */
public class MG0330SlipTypeMasterApplet extends TTestApplet{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Override
	public TController getController() {
		return new MG0330SlipTypeMasterPanelCtrl();
	}
}
