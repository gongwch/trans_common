package jp.co.ais.trans.common.gui;

import javax.swing.plaf.basic.*;

/**
 * �R���{�{�b�N�X�p�ҏW��
 */
public class TComboBoxFieldEditor extends BasicComboBoxEditor {

	/** �e�L�X�g�t�B�[���h */
	protected TTextField field = (TTextField) super.editor;

	/**
	 * �R���X�g���N�^�[
	 */
	public TComboBoxFieldEditor() {
		//
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param field
	 */
	public TComboBoxFieldEditor(TTextField field) {
		this.field = field;
	}

	/**
	 * �t�B�[���h�̎擾
	 * 
	 * @return �t�B�[���h
	 */
	public TTextField getField() {
		return field;
	}

	@Override
	protected TTextField createEditorComponent() {
		TTextField textfield = new TTextField();
		textfield.setBorder(null);
		return textfield;
	}
}
