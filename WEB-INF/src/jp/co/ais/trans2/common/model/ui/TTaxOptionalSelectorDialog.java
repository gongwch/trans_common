package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * ����ł̔C�ӑI���R���|�[�l���g�_�C�A���O
 * 
 * @author AIS
 */
public class TTaxOptionalSelectorDialog extends TOptionalSelectorDialog {

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
	public TTaxOptionalSelectorDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

}
