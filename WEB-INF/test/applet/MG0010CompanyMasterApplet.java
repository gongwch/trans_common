package applet;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.master.ui.*;

/**
 * ��ЃR���g���[���}�X�^�e�X�g�N���X
 * 
 * @author AIS
 */
public class MG0010CompanyMasterApplet extends TTestApplet {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public TController getController() {
		return new MG0010CompanyMasterPanelCtrl();
	}

	protected String getProgramID() {
		return "MG0010";
	}

}
