package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * �D�̔C�ӑI���R���|�[�l���g�_�C�A���O
 * 
 * @author AIS
 */
public class TVesselOptionalSelectorDialog extends TOptionalSelectorDialog {

	/**
	 * �e�[�u���̃J����
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** �R�[�h */
		code,
		/** ���� */
		names,
		/** �G���e�B�e�B */
		entity
	}

	/**
	 * @param parent
	 * @param mordal
	 */
	public TVesselOptionalSelectorDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

}
