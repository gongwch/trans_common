package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.TNumericField;

/**
 * �O�݋��z�t�B�[���h
 * 
 * @author AIS
 */
public class TForeignAmount extends TNumericField {

	/**
	 * �R���X�g���N�^.
	 */
	public TForeignAmount() {

		initComponents();

		allocateComponents();

	}

	/**
	 * 
	 */
	public void initComponents() {
		setMaxLength(13, 4);
		setChangeRedOfMinus(true);
	}

	/**
	 * 
	 */
	public void allocateComponents() {
		//
	}

}
