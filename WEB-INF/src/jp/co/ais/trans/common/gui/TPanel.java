package jp.co.ais.trans.common.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

/**
 * JPanelを継承し、メッセージIDインターフェースを実装したPanel.
 */
public class TPanel extends JPanel implements TInterfaceLangMessageID {

	private String langMessageID = null;

	/**
	 * Constructor.
	 */
	public TPanel() {
		super();
		setOpaque(false);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param isDoubleBuffered
	 */
	public TPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		setOpaque(false);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public TPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		setOpaque(false);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param layout
	 */
	public TPanel(LayoutManager layout) {
		super(layout);
		setOpaque(false);
	}

	/**
	 * 親コンテナをたどり、最初のFrameを返す.
	 * 
	 * @return 元フレーム
	 */
	public Frame getParentFrame() {
		for (Container p = this.getParent(); p != null; p = p.getParent()) {
			if (p instanceof Frame) {
				return (Frame) p;
			}
		}
		return null;
	}

	/**
	 * TInterfaceLangMessageID.setLangMessageIDの実装
	 * 
	 * @param langMessageID メッセージID
	 */
	public void setLangMessageID(String langMessageID) {

		this.langMessageID = langMessageID;

		this.setBorder(new TitledBorder(new EtchedBorder(), langMessageID));
	}

	/**
	 * TInterfaceLangMessageID.setLangMessageIDの実装
	 * 
	 * @return String メッセージID
	 */
	public String getLangMessageID() {
		return this.langMessageID;
	}

	/**
	 * デフォルトカーソルを設定
	 */
	public void setDefaultCursor() {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * WAITカーソルを設定
	 */
	public void setWaitCursor() {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
}
