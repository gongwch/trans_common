package applet;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.master.ui.*;


/**
 * �����x�������}�X�^�e�X�g�N���X
 * 
 * @author AIS
 */
public class MG0155CustomerConditionMasterApplet extends TTestApplet {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Override
	public TController getController() {
		return new MG0155CustomerPaymentSettingMasterPanelCtrl();
	}

	/**
	 * �v���O����ID��Ԃ�
	 * 
	 * @return �v���O����ID
	 */
	@Override
	protected String getProgramID() {
		return "MG0155";
	}

}
