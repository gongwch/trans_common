package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;

/**
 * JButton�ɁA�^�u���A���b�Z�[�WID�C���^�[�t�F�C�X��ǉ�����Button.
 */
public class TButton extends JButton implements TInterfaceLangMessageID, TInterfaceTabControl {

	private int tabControlNo = -1;

	/** Tab�ł̃t�H�[�J�X�ړ����\�� */
	private boolean isTabEnabled = true;

	/** Enter�ł̃t�H�[�J�X�ړ����\�� */
	private boolean isEnterFocusable = false;

	private String langMessageID = null;

	/** �V���[�g�J�b�g�L�[(�g�ݍ��킹)��� */
	private int shortcutType = TGuiUtil.SKEY_NONE;

	/** �t�@���N�V�����L�[�ԍ� VK_XX */
	private int shortcutKey = -1;

	private boolean isComponentsVerifierEnabled = true;

	/** �e�L�X�g�Ō�ɎO�_���[�_�[(...)��t�^���邩�ǂ��� */
	private boolean isAddThreeDots = false;

	/** ���s���܂ރe�L�X�g���[�h */
	private boolean isCrlfTextMode = false;

	/** ���s��╶�� */
	private String[] crlfTexts;

	/** ���s�e�L�X�g�̔z�u��CENTER�ɂ��邩�ǂ��� */
	private boolean isCrlfCenter = false;

	/** Verifier�����̏ꍇ�ɃN���b�N�Ńt�H�[�J�X�ړ��������Ȃ��ׂ̃��X�i */
	private MouseListener nonVerifyMouseListener = new MouseAdapter() {

		public void mouseEntered(MouseEvent e) {
			setFocusable(false);
		}

		public void mouseExited(MouseEvent e) {
			setFocusable(true);
		}
	};

	/**
	 * Constructor.
	 */
	public TButton() {
		super();

		// �t�@���N�V�����L�[�p���X�i
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_SPACE) {

					try {
						Thread.sleep(200);

					} catch (InterruptedException ex) {
						// �A�����s�h�~�p�̃^�C�����O
					}
				}

				handleKeyPressed(evt);
			}
		});

		setOpaque(false);
		setMultiClickThreshhold(100);
		setMargin(new Insets(0, 0, 0, 0));
	}

	/**
	 * Constructor.
	 * 
	 * @param text �{�^������
	 */
	public TButton(String text) {
		this();

		setText(text);
	}

	/**
	 * �t�@���N�V�����L�[����.
	 * 
	 * @param evt �L�[�C�x���g
	 */
	private void handleKeyPressed(KeyEvent evt) {

		// �{�^����EnterKey�Ŏ��s���邽�߁A�ړ����Ȃ��B
		// TGuiUtil.transferFocusByEnterKey(this, evt);
		TGuiUtil.dispatchPanelBusinessFunctionKey(this, evt);
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#setLangMessageID(String)
	 */
	public void setLangMessageID(String langMessageID) {
		this.langMessageID = langMessageID;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#getLangMessageID()
	 */
	public String getLangMessageID() {
		return this.langMessageID;
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
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#isTabEnabled()
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

	/**
	 * Enter�Ńt�H�[�J�X�ړ��\���ǂ���
	 * 
	 * @return true:�t�H�[�J�X�ړ��\
	 */
	public boolean isEnterFocusable() {
		return isEnterFocusable;
	}

	/**
	 * Enter�Ńt�H�[�J�X�ړ��\���ǂ���
	 * 
	 * @param isEnterFocusable true:�t�H�[�J�X�ړ��\
	 */
	public void setEnterFocusable(boolean isEnterFocusable) {
		this.isEnterFocusable = isEnterFocusable;
	}

	/**
	 * �t�H�[�J�X����verify���Ă΂Ȃ��ݒ�ɂ���
	 * 
	 * @param isNotCallVerify true:verify���Ă΂Ȃ�
	 */
	public void setForClose(boolean isNotCallVerify) {
		this.setVerifyInputWhenFocusTarget(!isNotCallVerify);
	}

	/**
	 * @see javax.swing.JComponent#setVerifyInputWhenFocusTarget(boolean)
	 */
	@Override
	public void setVerifyInputWhenFocusTarget(boolean verifyInputWhenFocusTarget) {
		super.setVerifyInputWhenFocusTarget(verifyInputWhenFocusTarget);

		if (!verifyInputWhenFocusTarget) {
			this.addMouseListener(nonVerifyMouseListener);

		} else {
			this.removeMouseListener(nonVerifyMouseListener);
		}
	}

	/**
	 * �eWindow�z����Verifier�̐ݒ�
	 * 
	 * @param b
	 */
	public void setComponentsVerifierEnabled(boolean b) {
		Window parent = TGuiUtil.getParentWindow(this);
		if (parent != null) {
			TGuiUtil.setComponentsVerifierEnabled(parent, b);
		}
		this.isComponentsVerifierEnabled = b;
	}

	/**
	 * �eWindow�z����Verifier�̐ݒ�
	 * 
	 * @return �eWindow�z����Verifier�̐ݒ�
	 */
	public boolean isComponentsVerifierEnabled() {
		return this.isComponentsVerifierEnabled;
	}

	/**
	 * �t�@���N�V�����L�[��ݒ肷��
	 * 
	 * @param skey �t�@���N�V�����L�[�萔 KeyEvent.VK_F1�`F12, VK_PAGE_UP, VK_PAGE_DOWN
	 */
	public void setShortcutKey(int skey) {
		this.shortcutType = TGuiUtil.WITH_NO_KEY;
		this.shortcutKey = skey;
	}

	/**
	 * ���������L�[�ƃt�@���N�V�����L�[��ݒ肷��<br>
	 * �����L�[�́AShift�ACtrl�AAlt�̂ݑΉ�
	 * 
	 * @param withKey ���������L�[ KeyEvent.VK_SHIFT, VK_CONTROL, VK_ALT
	 * @param skey �t�@���N�V�����L�[�萔 KeyEvent.VK_F1�`F12, VK_PAGE_UP, VK_PAGE_DOWN
	 */
	public void setShortcutKey(int withKey, int skey) {
		this.shortcutType = withKey;
		this.shortcutKey = skey;
	}

	/**
	 * �g�ݍ��킹�L�[���<br>
	 * FKEY_NONE, WITH_NO_KEY, KeyEvent.VK_SHIFT, VK_CONTROL, VK_ALT
	 * 
	 * @return �t�@���N�V�������
	 */
	int getShortcutType() {
		return shortcutType;
	}

	/**
	 * �V���[�g�J�b�g�L�[�R�[�h
	 * 
	 * @return �V���[�g�J�b�g�L�[�R�[�h
	 */
	int getShortcutKey() {
		return shortcutKey;
	}

	/**
	 * �e�L�X�g�Ō�ɎO�_���[�_�[(...)��t�^���邩�ǂ���
	 * 
	 * @return true:�t�^����
	 */
	public boolean isAddThreeDots() {
		return isAddThreeDots;
	}

	/**
	 * �e�L�X�g�Ō�ɎO�_���[�_�[(...)��t�^���邩�ǂ���
	 * 
	 * @param isAddThreeDots true:�t�^����
	 */
	public void setAddThreeDots(boolean isAddThreeDots) {
		this.isAddThreeDots = isAddThreeDots;

		if (isCrlfTextMode) {
			this.setCrlfText(crlfTexts);
		} else {
			this.setText(getText());
		}
	}

	/**
	 * @see javax.swing.AbstractButton#setText(java.lang.String)
	 */
	@Override
	public void setText(String text) {
		String buttonText = text;
		if (isAddThreeDots) {
			buttonText += "...";
		}

		super.setText(buttonText);

		isCrlfTextMode = false;
	}

	/**
	 * �����s�̃��x��������ݒ肷��.<br>
	 * ����AMessageID�̕����w��͖���.(�P��𒼐ڐݒ肷�邱��)
	 * 
	 * @param texts ���x���������X�g
	 */
	public void setCrlfText(String[] texts) {
		setCrlfText(texts, this.isCrlfCenter);
	}

	/**
	 * �����s�̃��x��������ݒ肷��.<br>
	 * ����AMessageID�̕����w��͖���.(�P��𒼐ڐݒ肷�邱��)
	 * 
	 * @param texts ���x���������X�g
	 * @param isCenter ���x���𒆉��񂹂ɂ��邩�ǂ���. true:����
	 */
	public void setCrlfText(String[] texts, boolean isCenter) {
		this.crlfTexts = texts;
		this.isCrlfCenter = isCenter;

		StringBuilder buff = new StringBuilder();
		for (String text : texts) {
			if (buff.length() != 0) buff.append("<br>");
			buff.append(text);
		}

		if (isAddThreeDots) {
			buff.append("...");
		}

		Color forground = isEnabled() ? getForeground() : Color.GRAY.brighter();
		if (isCrlfCenter) {
			buff.insert(0, "<center>");

		}

		buff.insert(0, "<font color=" + Util.to16RGBColorCode(forground) + ">");

		if (isCrlfCenter) {
			buff.append("</center>");
		}

		buff.append("</font>");

		buff.insert(0, "<html>");
		buff.append("</html>");

		super.setText(buff.toString());

		isCrlfTextMode = true;
	}

	/**
	 * �����s���x�����[�h��Ԃ��ǂ���
	 * 
	 * @return true:�����s���x�����[�h
	 */
	public boolean isCrlfTextMode() {
		return this.isCrlfTextMode;
	}

	/**
	 * �����Ńt�@���N�V�����L�[�̕��������邩�ǂ���
	 * 
	 * @return true:�����
	 */
	public boolean isAutoFkeyWord() {
		// �����s���x���łȂ��ꍇ�A(FX)�����������I�ɑ}��
		return !this.isCrlfTextMode;
	}

	/**
	 * @see javax.swing.AbstractButton#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);

		if (isCrlfTextMode) {
			this.setCrlfText(this.crlfTexts);
		}
	}

	/**
	 * @see javax.swing.JComponent#setPreferredSize(java.awt.Dimension)
	 */
	@Override
	public void setPreferredSize(Dimension d) {
		super.setPreferredSize(TGuiUtil.correctSize(TGuiUtil.TYPE_BUTTON, d));
	}

	/**
	 * @see java.awt.Component#setSize(Dimension)
	 */
	@Override
	public void setSize(Dimension d) {
		super.setSize(TGuiUtil.correctSize(TGuiUtil.TYPE_BUTTON, d));
	}

	/**
	 * @see java.awt.Component#setSize(int, int)
	 */
	@Override
	public void setSize(int height, int width) {
		Dimension d = TGuiUtil.correctSize(TGuiUtil.TYPE_BUTTON, new Dimension(width, height));
		super.setSize(d.width, d.height);
	}

	/**
	 * @see javax.swing.JComponent#setMaximumSize(java.awt.Dimension)
	 */
	@Override
	public void setMaximumSize(Dimension d) {
		super.setMaximumSize(TGuiUtil.correctSize(TGuiUtil.TYPE_BUTTON, d));
	}

	/**
	 * @see javax.swing.AbstractButton#addActionListener(java.awt.event.ActionListener)
	 */
	@Override
	public void addActionListener(ActionListener l) {
		super.addActionListener(new ActionListenerWrapper(l));
	}

	/**
	 * �A�N�V�������쎞�ɐe�R���|�[�l���g�����b�N����Listener�̃��b�p�[
	 */
	private class ActionListenerWrapper implements ActionListener {

		/** �I���W�i���̃��X�i�[ */
		private ActionListener original;

		/**
		 * �R���X�g���N�^
		 * 
		 * @param org �I���W�i���̃��X�i�[
		 */
		public ActionListenerWrapper(ActionListener org) {
			this.original = org;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() != null && e.getSource() instanceof Container) {

				MouseListener[] ls = getMouseListeners();
				for (MouseListener l : ls) {
					removeMouseListener(l);
				}

				try {

					this.original.actionPerformed(e);

				} finally {

					for (MouseListener l : ls) {
						addMouseListener(l);
					}
				}

			} else {
				this.original.actionPerformed(e);
			}
		}
	}
}
