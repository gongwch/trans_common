package jp.co.ais.trans.common.gui;

import javax.swing.plaf.basic.*;

/**
 * コンボボックス用編集者
 */
public class TComboBoxFieldEditor extends BasicComboBoxEditor {

	/** テキストフィールド */
	protected TTextField field = (TTextField) super.editor;

	/**
	 * コンストラクター
	 */
	public TComboBoxFieldEditor() {
		//
	}

	/**
	 * コンストラクター
	 * 
	 * @param field
	 */
	public TComboBoxFieldEditor(TTextField field) {
		this.field = field;
	}

	/**
	 * フィールドの取得
	 * 
	 * @return フィールド
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
