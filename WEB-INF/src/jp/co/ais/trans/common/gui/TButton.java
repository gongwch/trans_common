package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;

/**
 * JButtonに、タブ順、メッセージIDインターフェイスを追加したButton.
 */
public class TButton extends JButton implements TInterfaceLangMessageID, TInterfaceTabControl {

	private int tabControlNo = -1;

	/** Tabでのフォーカス移動が可能か */
	private boolean isTabEnabled = true;

	/** Enterでのフォーカス移動が可能か */
	private boolean isEnterFocusable = false;

	private String langMessageID = null;

	/** ショートカットキー(組み合わせ)種類 */
	private int shortcutType = TGuiUtil.SKEY_NONE;

	/** ファンクションキー番号 VK_XX */
	private int shortcutKey = -1;

	private boolean isComponentsVerifierEnabled = true;

	/** テキスト最後に三点リーダー(...)を付与するかどうか */
	private boolean isAddThreeDots = false;

	/** 改行を含むテキストモード */
	private boolean isCrlfTextMode = false;

	/** 改行候補文字 */
	private String[] crlfTexts;

	/** 改行テキストの配置をCENTERにするかどうか */
	private boolean isCrlfCenter = false;

	/** Verifier無効の場合にクリックでフォーカス移動をさせない為のリスナ */
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

		// ファンクションキー用リスナ
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_SPACE) {

					try {
						Thread.sleep(200);

					} catch (InterruptedException ex) {
						// 連続実行防止用のタイムラグ
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
	 * @param text ボタン文字
	 */
	public TButton(String text) {
		this();

		setText(text);
	}

	/**
	 * ファンクションキー処理.
	 * 
	 * @param evt キーイベント
	 */
	private void handleKeyPressed(KeyEvent evt) {

		// ボタンはEnterKeyで実行するため、移動しない。
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
	 * フォーカス時にverifyを呼ばない設定にする
	 * 
	 * @param isNotCallVerify true:verifyを呼ばない
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
	 * 親Window配下のVerifierの設定
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
	 * 親Window配下のVerifierの設定
	 * 
	 * @return 親Window配下のVerifierの設定
	 */
	public boolean isComponentsVerifierEnabled() {
		return this.isComponentsVerifierEnabled;
	}

	/**
	 * ファンクションキーを設定する
	 * 
	 * @param skey ファンクションキー定数 KeyEvent.VK_F1～F12, VK_PAGE_UP, VK_PAGE_DOWN
	 */
	public void setShortcutKey(int skey) {
		this.shortcutType = TGuiUtil.WITH_NO_KEY;
		this.shortcutKey = skey;
	}

	/**
	 * 同時押しキーとファンクションキーを設定する<br>
	 * 同時キーは、Shift、Ctrl、Altのみ対応
	 * 
	 * @param withKey 同時押しキー KeyEvent.VK_SHIFT, VK_CONTROL, VK_ALT
	 * @param skey ファンクションキー定数 KeyEvent.VK_F1～F12, VK_PAGE_UP, VK_PAGE_DOWN
	 */
	public void setShortcutKey(int withKey, int skey) {
		this.shortcutType = withKey;
		this.shortcutKey = skey;
	}

	/**
	 * 組み合わせキー種類<br>
	 * FKEY_NONE, WITH_NO_KEY, KeyEvent.VK_SHIFT, VK_CONTROL, VK_ALT
	 * 
	 * @return ファンクション種類
	 */
	int getShortcutType() {
		return shortcutType;
	}

	/**
	 * ショートカットキーコード
	 * 
	 * @return ショートカットキーコード
	 */
	int getShortcutKey() {
		return shortcutKey;
	}

	/**
	 * テキスト最後に三点リーダー(...)を付与するかどうか
	 * 
	 * @return true:付与する
	 */
	public boolean isAddThreeDots() {
		return isAddThreeDots;
	}

	/**
	 * テキスト最後に三点リーダー(...)を付与するかどうか
	 * 
	 * @param isAddThreeDots true:付与する
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
	 * 複数行のラベル文字を設定する.<br>
	 * 現状、MessageIDの複数指定は無効.(単語を直接設定すること)
	 * 
	 * @param texts ラベル文字リスト
	 */
	public void setCrlfText(String[] texts) {
		setCrlfText(texts, this.isCrlfCenter);
	}

	/**
	 * 複数行のラベル文字を設定する.<br>
	 * 現状、MessageIDの複数指定は無効.(単語を直接設定すること)
	 * 
	 * @param texts ラベル文字リスト
	 * @param isCenter ラベルを中央寄せにするかどうか. true:中央
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
	 * 複数行ラベルモード状態かどうか
	 * 
	 * @return true:複数行ラベルモード
	 */
	public boolean isCrlfTextMode() {
		return this.isCrlfTextMode;
	}

	/**
	 * 自動でファンクションキーの文字を入れるかどうか
	 * 
	 * @return true:入れる
	 */
	public boolean isAutoFkeyWord() {
		// 複数行ラベルでない場合、(FX)文字を自動的に挿入
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
	 * アクション動作時に親コンポーネントをロックするListenerのラッパー
	 */
	private class ActionListenerWrapper implements ActionListener {

		/** オリジナルのリスナー */
		private ActionListener original;

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
