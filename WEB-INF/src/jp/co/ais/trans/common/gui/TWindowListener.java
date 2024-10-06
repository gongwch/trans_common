package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;

/**
 * TInputVerifier�ƘA�����AWindow�����Ƃ���verify()���Ă΂Ȃ��悤����
 */
public class TWindowListener extends WindowAdapter {

	/**  */
	@SuppressWarnings("unused")
	protected Window parent = null;

	/**
	 * constructor
	 * 
	 * @param cont �e�E�C���h�E(Frame, Dialog)
	 */
	public TWindowListener(Window cont) {
		parent = cont;
	}

	/**
	 * �E�C���h�E�������ԂɂȂ����Ƃ��̃R�[���o�b�N
	 * 
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent evt) {

		// setComponentsVerifierEnabled �𑼂̏�������ɌĂ�
		if (evt.getSource() instanceof Window) {
			TGuiUtil.setComponentsVerifierEnabled((Window) evt.getSource(), true);
			// ClientLogger.debug("windowActivated");
		}
	}

	/**
	 * �E�C���h�E�������悤�Ƃ��Ă���Ƃ��̃R�[���o�b�N
	 * 
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent evt) {

		// setComponentsVerifierEnabled �𑼂̏�������ɌĂ�
		if (evt.getSource() instanceof Window) {
			TGuiUtil.setComponentsVerifierEnabled((Window) evt.getSource(), false);
			// ClientLogger.debug("windowClosing");
		}
	}

	/**
	 * �E�C���h�E���񓮍��ԂɂȂ����Ƃ��̃R�[���o�b�N
	 * 
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeactivated(WindowEvent evt) {

		// setComponentsVerifierEnabled �𑼂̏�������ɌĂ�
		if (evt.getSource() instanceof Window) {
			if (((Window) evt.getSource()).isActive()) {
				TGuiUtil.setComponentsVerifierEnabled((Window) evt.getSource(), false);
				// ClientLogger.debug("windowDeactivated");
			}
		}
	}
}
