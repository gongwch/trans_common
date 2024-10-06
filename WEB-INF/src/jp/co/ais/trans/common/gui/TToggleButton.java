package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * JToggleButton�ɁA�^�u���A���b�Z�[�WID�C���^�[�t�F�C�X��ǉ�����Button.
 */
public class TToggleButton extends JToggleButton implements TInterfaceLangMessageID, TInterfaceTabControl {

	/** �^�u�� */
	protected int tabControlNo = -1;

	/** Tab�ł̃t�H�[�J�X�ړ����\�� */
	protected boolean isTabEnabled = true;

	/** Enter�ł̃t�H�[�J�X�ړ����\�� */
	protected boolean isEnterFocusable = false;

	/** �{�^�����P��ID */
	protected String langMessageID = null;

	/** �I��(����)��ԕ��� */
	protected String selectedText;

	/** ���I����ԕ��� */
	protected String notSelectedText;

	/** �������J���[ */
	protected Color selectedColor;

	/** �񉟉���(�f�t�H���g)�J���[ */
	protected Color notSelectedColor;

	/**
	 * Constructor.
	 */
	public TToggleButton() {
		this("");
	}

	/**
	 * Constructor.
	 * 
	 * @param text �{�^������
	 */
	public TToggleButton(String text) {
		super(text);

		setLangMessageID(text);

		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					TToggleButton.this.doClick();
				}

				handleKeyPressed(e);
			}
		});

		this.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedColor != null) {
					setForeground(isSelected() ? selectedColor : notSelectedColor);
				}

				if (notSelectedText != null) {
					setText(isSelected() ? selectedText : notSelectedText);
				}
			}
		});
	}

	/**
	 * �L�[����.
	 * 
	 * @param evt �L�[�C�x���g
	 */
	protected void handleKeyPressed(KeyEvent evt) {

		// �t�@���N�V�����L�[
		TGuiUtil.dispatchPanelBusinessFunctionKey(this, evt);
	}

	/**
	 * @see javax.swing.AbstractButton#setSelected(boolean)
	 */
	@Override
	public void setSelected(boolean isSelected) {
		super.setSelected(isSelected);

		if (selectedColor != null) {
			setForeground(isSelected ? selectedColor : notSelectedColor);
		}

		if (notSelectedText != null) {
			setText(isSelected ? selectedText : notSelectedText);
		}
	}

	/**
	 * �I���A���I����Ԃ̃e�L�X�g��ݒ肷��.(����P��ID�Ή�)
	 * 
	 * @param selectedText �I��(����)��ԕ���
	 * @param notSelectedText ���I����ԕ���
	 */
	public void setSelectStateText(String notSelectedText, String selectedText) {
		this.selectedText = selectedText;
		this.notSelectedText = notSelectedText;
	}

	/**
	 * �I�����ꂽ�ꍇ�̃J���[��ݒ肷��.
	 * 
	 * @param color �I��(�������)�J���[
	 */
	public void setSelectStateColor(Color color) {
		this.setSelectStateColor(getForeground(), color);
	}

	/**
	 * �I������Ă��Ȃ��ꍇ�̃J���[��ݒ肷��.
	 * 
	 * @param color ��I��(�񉟉����)�J���[
	 */
	public void setNonSelectStateColor(Color color) {
		this.setSelectStateColor(color, getForeground());
	}

	/**
	 * �I���A���I����Ԃ̃J���[��ݒ肷��.
	 * 
	 * @param notSelectedColor ���I����ԃJ���[
	 * @param selectedColor �I��(����)��ԃJ���[
	 */
	public void setSelectStateColor(Color notSelectedColor, Color selectedColor) {
		this.selectedColor = selectedColor;
		this.notSelectedColor = notSelectedColor;

		setForeground(isSelected() ? selectedColor : notSelectedColor);
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
	public void setSize(int width, int height) {
		Dimension d = TGuiUtil.correctSize(TGuiUtil.TYPE_BUTTON, new Dimension(width, height));
		super.setSize(d.height, d.width);
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
	protected class ActionListenerWrapper implements ActionListener {

		/** �I���W�i���̃��X�i�[ */
		protected ActionListener original;

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
