package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * �⏕�Ȗڂ̔C�ӑI���R���|�[�l���g�_�C�A���O
 * 
 * @author AIS
 */
public class TSubItemOptionalSelectorDialog extends TOptionalSelectorDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 6720856141179813605L;

	/**
	 * �e�[�u���̃J����
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** �R�[�h */
		code,
		/** �� */
		names,
		/** ENTITY */
		entity
	}

	/**
	 * @param parent
	 * @param mordal
	 */
	public TSubItemOptionalSelectorDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

}
