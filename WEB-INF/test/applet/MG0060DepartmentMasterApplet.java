package applet;

import jp.co.ais.trans2.common.client.TController;
import jp.co.ais.trans2.master.ui.*;

/**
 * MG0060DepartmentMasterApplet
 */
public class MG0060DepartmentMasterApplet extends TTestApplet{

	@Override
	public TController getController() {
		return new MG0060DepartmentMasterPanelCtrl();
	}
	
	/**
	 * �v���O����ID��Ԃ�
	 * 
	 * @return �v���O����ID
	 */
	@Override
	protected String getProgramID() {
		return "MG0060";
	}
}