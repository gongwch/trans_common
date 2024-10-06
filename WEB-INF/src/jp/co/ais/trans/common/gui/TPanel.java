package jp.co.ais.trans.common.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

/**
 * JPanel���p�����A���b�Z�[�WID�C���^�[�t�F�[�X����������Panel.
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
	 * �R���X�g���N�^.
	 * 
	 * @param isDoubleBuffered
	 */
	public TPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		setOpaque(false);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public TPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		setOpaque(false);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param layout
	 */
	public TPanel(LayoutManager layout) {
		super(layout);
		setOpaque(false);
	}

	/**
	 * �e�R���e�i�����ǂ�A�ŏ���Frame��Ԃ�.
	 * 
	 * @return ���t���[��
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
	 * TInterfaceLangMessageID.setLangMessageID�̎���
	 * 
	 * @param langMessageID ���b�Z�[�WID
	 */
	public void setLangMessageID(String langMessageID) {

		this.langMessageID = langMessageID;

		this.setBorder(new TitledBorder(new EtchedBorder(), langMessageID));
	}

	/**
	 * TInterfaceLangMessageID.setLangMessageID�̎���
	 * 
	 * @return String ���b�Z�[�WID
	 */
	public String getLangMessageID() {
		return this.langMessageID;
	}

	/**
	 * �f�t�H���g�J�[�\����ݒ�
	 */
	public void setDefaultCursor() {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * WAIT�J�[�\����ݒ�
	 */
	public void setWaitCursor() {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
}
