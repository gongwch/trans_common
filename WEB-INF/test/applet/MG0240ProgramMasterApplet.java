package applet;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.master.ui.*;

/**
 * �v���O�����}�X�^�e�X�g�N���X
 * @author AIS
 *
 */
public class MG0240ProgramMasterApplet extends TTestApplet{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public TController getController() {
		return new MG0240ProgramMasterPanelCtrl();
	}

}
