package applet;

import jp.co.ais.trans2.common.client.TController;
import jp.co.ais.trans2.master.ui.*;

/**
 * MG0080ItemMasterApplet
 */
public class MG0080ItemMasterApplet extends TTestApplet{

	@Override
	public TController getController() {
		return new MG0080ItemMasterPanelCtrl();
	}
	
	/**
	 * �v���O����ID��Ԃ�
	 * 
	 * @return �v���O����ID
	 */
	@Override
	protected String getProgramID() {
		return "MG0080";
	}
}