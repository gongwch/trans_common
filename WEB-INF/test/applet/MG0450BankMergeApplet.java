package applet;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.master.ui.*;



/**
 * ��s���p���e�X�g�N���X
 */
public class MG0450BankMergeApplet extends TTestApplet{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see applet.TTestApplet#getController()
	 */
	@Override
	public TController getController(){
		return new MG0450BankMergePanelCtrl();
	}
	
	/**
	 * @see applet.TTestApplet#getProgramID()
	 */
	@Override
	protected String getProgramID() {
		return "MG0450";
	}

}
