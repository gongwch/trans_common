package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * JToggleButtonに、タブ順、メッセージIDインターフェイスを追加したButton.
 */
public class TToggleButton extends JToggleButton implements TInterfaceLangMessageID, TInterfaceTabControl {

	/** タブ順 */
	protected int tabControlNo = -1;

	/** Tabでのフォーカス移動が可能か */
	protected boolean isTabEnabled = true;

	/** Enterでのフォーカス移動が可能か */
	protected boolean isEnterFocusable = false;

	/** ボタン名単語ID */
	protected String langMessageID = null;

	/** 選択(押下)状態文字 */
	protected String selectedText;

	/** 未選択状態文字 */
	protected String notSelectedText;

	/** 押下時カラー */
	protected Color selectedColor;

	/** 非押下時(デフォルト)カラー */
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
	 * @param text ボタン文字
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
	 * キー処理.
	 * 
	 * @param evt キーイベント
	 */
	protected void handleKeyPressed(KeyEvent evt) {

		// ファンクションキー
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
	 * 選択、未選択状態のテキストを設定する.(※非単語ID対応)
	 * 
	 * @param selectedText 選択(押下)状態文字
	 * @param notSelectedText 未選択状態文字
	 */
	public void setSelectStateText(String notSelectedText, String selectedText) {
		this.selectedText = selectedText;
		this.notSelectedText = notSelectedText;
	}

	/**
	 * 選択された場合のカラーを設定する.
	 * 
	 * @param color 選択(押下状態)カラー
	 */
	public void setSelectStateColor(Color color) {
		this.setSelectStateColor(getForeground(), color);
	}

	/**
	 * 選択されていない場合のカラーを設定する.
	 * 
	 * @param color 非選択(非押下状態)カラー
	 */
	public void setNonSelectStateColor(Color color) {
		this.setSelectStateColor(color, getForeground());
	}

	/**
	 * 選択、未選択状態のカラーを設定する.
	 * 
	 * @param notSelectedColor 未選択状態カラー
	 * @param selectedColor 選択(押下)状態カラー
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
	 * Enterでフォーカス移動可能かどうか
	 * 
	 * @return true:フォーカス移動可能
	 */
	public boolean isEnterFocusable() {
		return isEnterFocusable;
	}

	/**
	 * Enterでフォーカス移動可能かどうか
	 * 
	 * @param isEnterFocusable true:フォーカス移動可能
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
	 * アクション動作時に親コンポーネントをロックするListenerのラッパー
	 */
	protected class ActionListenerWrapper implements ActionListener {

		/** オリジナルのリスナー */
		protected ActionListener original;

		/**
		 * コンストラクタ
		 * 
		 * @param org オリジナルのリスナー
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
