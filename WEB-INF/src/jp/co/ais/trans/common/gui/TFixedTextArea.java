package jp.co.ais.trans.common.gui;

import javax.swing.*;

/***************************************************************************************************
 * 
 * JTextAreaに、メッセージIDインターフェイスを追加したTextArea.
 * <br>
 * 折り返し固定文字表示用。(Label用途)
 **************************************************************************************************/
public class TFixedTextArea extends JTextArea implements TInterfaceLangMessageID {

	/** シリアルUID */
	private static final long serialVersionUID = 1L;

	/** message ID */
	private String langMessageID = null;

	/**
	 * コンストラクタ.
	 */
	public TFixedTextArea() {
		super();
		this.setEditable(false);
	}

	/**
	 * 文字列の設定.
	 * <br>
	 * Label用途のため、JTextAreaのeditable propertyをfalseにしている。
	 * setText()をoverrideし、falseでも文字列を設定できるようにする。
	 * @param value 設定文字列
	 * 
	 */
	@Override
	public void setText(String value) {
		boolean b;

		b = super.isEditable();
		super.setEditable(true);

		super.setText(value);

		super.setEditable(b);
	}

	/* (non-Javadoc)
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#setLangMessageID(String)
	 */
	public void setLangMessageID(String langMessageID) {
		this.langMessageID = langMessageID;
	}

	/* (non-Javadoc)
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#getLangMessageID()
	 */
	public String getLangMessageID() {
		return this.langMessageID;
	}

}
