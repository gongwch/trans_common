package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * �Ǘ�5�̔C�ӑI���R���|�[�l���g�_�C�A���O
 * 
 * @author AIS
 */
public class TManagement5OptionalSelectorDialog extends TOptionalSelectorDialog {

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
	public TManagement5OptionalSelectorDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

}