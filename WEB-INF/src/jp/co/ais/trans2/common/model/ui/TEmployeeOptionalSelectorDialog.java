package jp.co.ais.trans2.common.model.ui;

import java.awt.Frame;
import jp.co.ais.trans2.common.gui.TOptionalSelectorDialog;

/**
 * �Ј��̔C�ӑI���R���|�[�l���g�_�C�A���O
 * @author AIS
 *
 */
public class TEmployeeOptionalSelectorDialog extends TOptionalSelectorDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 6529615256302611706L;

	/**
	 * �e�[�u���̃J����
	 * @author AIS
	 *
	 */
	public enum SC {
		code,
		names,
		entity
	}

	/**
	 * 
	 * @param parent
	 * @param mordal
	 */
	public TEmployeeOptionalSelectorDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

}
