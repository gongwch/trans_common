package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;

/**
 * �p�X���[�h�t�B�[���h
 */
public class TPasswordField extends JPasswordField implements TInterfaceTabControl {

	/** true:�t�H�[�J�X���Ă鎞���邢�� */
	public static final boolean isHighLight = ClientConfig.isFlagOn("trans.textfield.focus.highlight");

	/** �w�i�J���[ */
	protected Color backColor = getBackground();

	/** �^�u�� */
	protected int tabControlNo = -1;

	/** �^�u�ړ��E�t�� */
	protected boolean isTabEnabled = true;

	/** IME���� */
	protected boolean imeFlag = true;

	/** �ő咷 */
	protected int maxLength = 128;

	/**
	 * �R���X�g���N�^.
	 */
	public TPasswordField() {
		super();
		this.setDocument(createPlainDocument());
		this.setEditable(true);
		this.setImeMode(false);
		this.setText("");

		// �t�H�[�J�X�p���X�i
		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				focusGainedActionPerformed();
			}

			@Override
			public void focusLost(FocusEvent e) {
				focusLostActionPerformed();
			}
		});

		// �t�@���N�V�����L�[�p���X�i
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				handleKeyPressed(evt);
			}
		});
	}

	/**
	 * �p�X���[�h�������擾
	 * 
	 * @return ����
	 */
	public String getValue() {
		return new String(getPassword());
	}

	/**
	 * ���͒l���L�邩�ǂ���
	 * 
	 * @return true:���͒l�L��
	 */
	public boolean isEmpty() {
		return Util.isNullOrEmpty(getValue());
	}

	/**
	 * ���͐���Document
	 * 
	 * @return PlainDocument
	 */
	protected PlainDocument createPlainDocument() {
		return new TextPasswordDocument();
	}

	/**
	 * FocusGained�C�x���g
	 */
	protected void focusGainedActionPerformed() {
		// �t�H�[�J�X���Ă鎞�̃o�b�N�J���[�ݒ�
		focusGainedBackColor();
	}

	/**
	 * lostFocus�C�x���g
	 */
	protected void focusLostActionPerformed() {
		// ���X�g�t�H�[�J�X���̃o�b�N�J���[�ݒ�
		focusLostBackColor();
	}

	/**
	 * �t�H�[�J�X���Ă鎞�̃o�b�N�J���[�ݒ�
	 */
	public void focusGainedBackColor() {
		if (isHighLight) {
			// ���邢�΃��[�h
			backColor = getBackground();
			if (this.isEditable()) {
				setBackground(TTextBGColor.getHighLightFocusInColor());
			}
		}
	}

	/**
	 * ���X�g�t�H�[�J�X���̃o�b�N�J���[�ݒ�
	 */
	public void focusLostBackColor() {
		if (isHighLight) {
			// ���邢�΃��[�h����
			if (this.isEditable()) {
				setBackground(backColor);
			}
		}
	}

	/**
	 * �L�[����.
	 * 
	 * @param evt �L�[�C�x���g
	 */
	protected void handleKeyPressed(KeyEvent evt) {

		// Enter�L�[
		TGuiUtil.transferFocusByEnterKey(this, evt);

		// // F2 �N���A�Ή�
		// int fkey = TGuiUtil.checkFunctionKeyEvent(evt);
		// if(fkey == 2){
		// if(this.isEnabled() && this.isEditable()){
		// this.setText("");
		// }
		// }

		// �t�@���N�V�����L�[
		TGuiUtil.dispatchPanelBusinessFunctionKey(this, evt);
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#getTabControlNo()
	 */
	public int getTabControlNo() {
		return this.tabControlNo;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		this.tabControlNo = no;
	}

	/**
	 * Tab�ړ��\���ǂ���
	 * 
	 * @return true:Tab�ړ��\
	 */
	public boolean isTabEnabled() {
		return this.isTabEnabled;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#setTabEnabled(boolean)
	 */
	public void setTabEnabled(boolean bool) {
		this.isTabEnabled = bool;
	}

	// �ŗL�ݒ�. ********************************************

	/**
	 * �ő包��
	 * 
	 * @return �ő包��
	 */
	public int getMaxLength() {
		return this.maxLength;
	}

	/**
	 * �ő包��
	 * 
	 * @param maxLength �ő包��
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * IME���[�h�t���O
	 * 
	 * @param flag true: IME���[�h
	 */
	protected void setImeMode(boolean flag) {
		this.enableInputMethods(!flag); // �K�v.
		this.enableInputMethods(flag);
	}

	/**
	 * �����N���X��text document
	 */
	protected class TextPasswordDocument extends PlainDocument {

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

			// �o�C�g���ɂ����͐���
			if (maxLength >= 0) {

				if (TPasswordField.this.getPassword().length + str.getBytes().length > maxLength) {

					return;
				}
			}

			for (int i = 0; i < str.length(); i++) {
				String s = str.substring(i, i + 1);

				// �S�p�֎~�őS�p���܂܂�Ă���ꍇ��NG
				if (s.getBytes().length > 1) {
					return;
				}

				// ���K�\���`�F�b�N : ���p�J�i��NG
				if (s.matches("[�-�]")) {
					return;
				}
			}

			super.insertString(offs, str, a);
		}
	}

	/**
	 * �t�H���g�T�C�Y�ύX�p�̃T�C�Y�␳���s��
	 * 
	 * @see javax.swing.JComponent#setPreferredSize(java.awt.Dimension)
	 */
	@Override
	public void setPreferredSize(Dimension d) {
		super.setPreferredSize(TGuiUtil.correctSize(TGuiUtil.TYPE_TEXTFIELD, d));
	}

	/**
	 * �t�H���g�T�C�Y�ύX�p�̃T�C�Y�␳���s��
	 * 
	 * @see java.awt.Component#setSize(java.awt.Dimension)
	 */
	@Override
	public void setSize(Dimension d) {
		super.setSize(TGuiUtil.correctSize(TGuiUtil.TYPE_TEXTFIELD, d));
	}

	/**
	 * �t�H���g�T�C�Y�ύX�p�̃T�C�Y�␳���s��
	 * 
	 * @see javax.swing.JComponent#setMaximumSize(java.awt.Dimension)
	 */
	@Override
	public void setMaximumSize(Dimension d) {
		super.setMaximumSize(TGuiUtil.correctSize(TGuiUtil.TYPE_TEXTFIELD, d));
	}
}
