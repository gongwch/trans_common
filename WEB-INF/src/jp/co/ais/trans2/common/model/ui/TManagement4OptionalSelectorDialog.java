package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * �Ǘ�4�̔C�ӑI���R���|�[�l���g�_�C�A���O
 * 
 * @author AIS
 */
public class TManagement4OptionalSelectorDialog extends TOptionalSelectorDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 6720856141179813605L;

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
	public TManagement4OptionalSelectorDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

}
