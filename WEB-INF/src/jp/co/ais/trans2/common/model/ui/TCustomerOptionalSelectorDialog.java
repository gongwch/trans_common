package jp.co.ais.trans2.common.model.ui;

import java.awt.Frame;
import jp.co.ais.trans2.common.gui.TOptionalSelectorDialog;

/**
 * �����̔C�ӑI���R���|�[�l���g�_�C�A���O
 * 
 * @author AIS
 */
public class TCustomerOptionalSelectorDialog extends TOptionalSelectorDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = -3866298548210927657L;

	/**
	 * �e�[�u���̃J����
	 * 
	 * @author AIS
	 */
	public enum SC {
		code, names, entity
	}

	/**
	 * @param parent
	 * @param mordal
	 */
	public TCustomerOptionalSelectorDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

}
