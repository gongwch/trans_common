package applet;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.master.ui.*;


/**
 * ��s�}�X�^�e�X�g�N���X
 * @author AIS
 */
public class MG0070ItemSummaryMasterApplet extends TTestApplet{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Override
	public TController getController() {
		return new MG0070ItemSummaryMasterPanelCtrl();
	}
}
