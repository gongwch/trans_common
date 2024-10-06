package jp.co.ais.trans.common.gui;

import java.awt.*;

import javax.swing.border.*;

/***************************************************************************************************
 * TitledBorderに、MessageIDインターフェイスを追加したBorder.
 **************************************************************************************************/
public class TTitledBorder extends TitledBorder implements TInterfaceLangMessageID {

	/**  */
	protected String langMessageID = null;

	/**
	 * Constructor.(TitledBorderと同様)
	 * 
	 * @param border
	 */
	public TTitledBorder(Border border) {
		super(border);
	}

	/**
	 * Constructor.
	 * 
	 * @param border
	 * @param title
	 */
	public TTitledBorder(Border border, String title) {
		super(border, title);
	}

	/**
	 * Constructor.
	 * 
	 * @param border
	 * @param title
	 * @param titleJustification
	 * @param titlePosition
	 */
	public TTitledBorder(Border border, String title, int titleJustification, int titlePosition) {
		super(border, title, titleJustification, titlePosition);
	}

	/**
	 * Constructor.
	 * 
	 * @param border
	 * @param title
	 * @param titleJustification
	 * @param titlePosition
	 * @param titleFont
	 */
	public TTitledBorder(Border border, String title, int titleJustification, int titlePosition, Font titleFont) {
		super(border, title, titleJustification, titlePosition, titleFont);
	}

	/**
	 * Constructor.
	 * 
	 * @param border
	 * @param title
	 * @param titleJustification
	 * @param titlePosition
	 * @param titleFont
	 * @param titleColor
	 */
	public TTitledBorder(Border border, String title, int titleJustification, int titlePosition, Font titleFont,
		Color titleColor) {
		super(border, title, titleJustification, titlePosition, titleFont, titleColor);
	}

	/**
	 * Constructor.
	 * 
	 * @param title
	 */
	public TTitledBorder(String title) {
		super(title);
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

}
