package jp.co.ais.trans2.common.gui;

import jp.co.ais.trans.common.gui.*;

/**
 * ���x���t�����p�Ő���e�L�X�g�t�B�[���h
 */
public class TLabelHalfAngleTextField extends TLabelField {
	
	/**
	 * ���p�Ő���e�L�X�g�t�B�[���h����
	 * 
	 * @return ���p�Ő���e�L�X�g�t�B�[���h
	 */
	@Override
	protected TTextField createTextField() {
		return new THalfAngleTextField();
	}
}
