package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * ��Ђ̔C�ӑI���R���|�[�l���g�_�C�A���O
 * 
 * @author AIS
 */
public class TCompanyOptionalSelectorDialog extends TOptionalSelectorDialog {

	/**
	 * �e�[�u���̃J����
	 * 
	 * @author AIS
	 */
	public enum CMP_SEL_SC {
		/** BEAN */
		entity,
		/** �R�[�h */
		code,
		/** ���� */
		name
	}

	/**
	 * @param parent
	 * @param mordal
	 */
	public TCompanyOptionalSelectorDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

}
