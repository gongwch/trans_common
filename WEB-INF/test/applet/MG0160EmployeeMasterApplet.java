package applet;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.master.ui.*;

/**
 * MG0160EmployeeMaster - ŽÐˆõƒ}ƒXƒ^ - Test Class
 * 
 * @author AIS
 */
public class MG0160EmployeeMasterApplet extends TTestApplet {

	public TController getController() {
		return new MG0160EmployeeMasterPanelCtrl();
	}

	/**
	 * @return Program ID
	 */
	@Override
	protected String getProgramID() {
		return "MG0160";
	}
}