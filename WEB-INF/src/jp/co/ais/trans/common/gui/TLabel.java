package jp.co.ais.trans.common.gui;

import javax.swing.*;

/**
 * JLabelに、メッセージIDインターフェイスを追加したLabel.
 */
public class TLabel extends JLabel implements TInterfaceLangMessageID {

	/** シリアルUID */
	private static final long serialVersionUID = 0L;

	/** 単語ID */
	private String langMessageID = null;

	/** 回転の有無 true:90度回転 */
	protected boolean isRotate = false;

	/**
	 * コンストラクタ.
	 */
	public TLabel() {
		super();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param langMessageID
	 */
	public TLabel(String langMessageID) {
		this.langMessageID = langMessageID;
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param langMessageID
	 * @param isRotate
	 */
	public TLabel(String langMessageID, boolean isRotate) {
		this.langMessageID = langMessageID;
		setRotate(isRotate);
	}

	/*
	 * (non-Javadoc)
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#setLangMessageID(String)
	 */
	public void setLangMessageID(String langMessageID) {
		this.langMessageID = langMessageID;
	}

	/*
	 * (non-Javadoc)
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#getLangMessageID()
	 */
	public String getLangMessageID() {
		return this.langMessageID;
	}

	/**
	 * ラベルを回転するか否か
	 * 
	 * @return true:回転する
	 */
	public boolean isRotate() {
		return isRotate;
	}

	/**
	 * ラベルを回転するか否か
	 * 
	 * @param isRotate
	 */
	public void setRotate(boolean isRotate) {
		this.isRotate = isRotate;
		// LabelUI ui = getUI();
		// if (ui instanceof BaseLabelUI) {
		// }
	}

}
