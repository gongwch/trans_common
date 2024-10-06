package jp.co.ais.trans.common.gui;

import java.io.*;
import javax.swing.*;

/**
 * JAppletを継承したApplet.
 */
public class TApplet extends JApplet {

	/** シリアルUID */
	private static final long serialVersionUID = 4778113009491812269L;

	/** Constructor. */
	public TApplet() {
		super();
	}

	/**
	 * 全メッセージ読込.
	 * 
	 * @deprecated 利用しない
	 * @param lang 言語
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public void setupMessage(String lang) throws IOException {
		//
	}

}
