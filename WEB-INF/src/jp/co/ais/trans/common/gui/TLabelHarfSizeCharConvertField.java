package jp.co.ais.trans.common.gui;

/**
 * ���x�����p�ϊ��e�L�X�g�t�B�[���h�i���x���e�L�X�g�t�B�[���h�g���j
 */
public class TLabelHarfSizeCharConvertField extends TLabelField {

	/**
	 * Constructor. <br>
	 * �qitem������������.
	 */
	public TLabelHarfSizeCharConvertField() {
		super();
	}

	/**
	 * �e�L�X�g�t�B�[���h����
	 * 
	 * @return �e�L�X�g�t�B�[���h
	 */
	@Override
	protected THarfSizeCharConvertField createTextField() {
		return new THarfSizeCharConvertField();
	}
}
