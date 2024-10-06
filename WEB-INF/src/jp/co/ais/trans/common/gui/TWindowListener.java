package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;

/**
 * TInputVerifierと連動し、Windowを閉じるときにverify()を呼ばないよう制御
 */
public class TWindowListener extends WindowAdapter {

	/**  */
	@SuppressWarnings("unused")
	protected Window parent = null;

	/**
	 * constructor
	 * 
	 * @param cont 親ウインドウ(Frame, Dialog)
	 */
	public TWindowListener(Window cont) {
		parent = cont;
	}

	/**
	 * ウインドウが動作状態になったときのコールバック
	 * 
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent evt) {

		// setComponentsVerifierEnabled を他の処理より先に呼ぶ
		if (evt.getSource() instanceof Window) {
			TGuiUtil.setComponentsVerifierEnabled((Window) evt.getSource(), true);
			// ClientLogger.debug("windowActivated");
		}
	}

	/**
	 * ウインドウが閉じられようとしているときのコールバック
	 * 
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent evt) {

		// setComponentsVerifierEnabled を他の処理より先に呼ぶ
		if (evt.getSource() instanceof Window) {
			TGuiUtil.setComponentsVerifierEnabled((Window) evt.getSource(), false);
			// ClientLogger.debug("windowClosing");
		}
	}

	/**
	 * ウインドウが非動作状態になったときのコールバック
	 * 
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeactivated(WindowEvent evt) {

		// setComponentsVerifierEnabled を他の処理より先に呼ぶ
		if (evt.getSource() instanceof Window) {
			if (((Window) evt.getSource()).isActive()) {
				TGuiUtil.setComponentsVerifierEnabled((Window) evt.getSource(), false);
				// ClientLogger.debug("windowDeactivated");
			}
		}
	}
}
