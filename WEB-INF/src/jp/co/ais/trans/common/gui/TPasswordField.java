package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;

/**
 * パスワードフィールド
 */
public class TPasswordField extends JPasswordField implements TInterfaceTabControl {

	/** true:フォーカス当てる時明るい緑 */
	public static final boolean isHighLight = ClientConfig.isFlagOn("trans.textfield.focus.highlight");

	/** 背景カラー */
	protected Color backColor = getBackground();

	/** タブ順 */
	protected int tabControlNo = -1;

	/** タブ移動可・付加 */
	protected boolean isTabEnabled = true;

	/** IME制御 */
	protected boolean imeFlag = true;

	/** 最大長 */
	protected int maxLength = 128;

	/**
	 * コンストラクタ.
	 */
	public TPasswordField() {
		super();
		this.setDocument(createPlainDocument());
		this.setEditable(true);
		this.setImeMode(false);
		this.setText("");

		// フォーカス用リスナ
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

		// ファンクションキー用リスナ
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				handleKeyPressed(evt);
			}
		});
	}

	/**
	 * パスワード文字を取得
	 * 
	 * @return 文字
	 */
	public String getValue() {
		return new String(getPassword());
	}

	/**
	 * 入力値が有るかどうか
	 * 
	 * @return true:入力値有り
	 */
	public boolean isEmpty() {
		return Util.isNullOrEmpty(getValue());
	}

	/**
	 * 入力制御Document
	 * 
	 * @return PlainDocument
	 */
	protected PlainDocument createPlainDocument() {
		return new TextPasswordDocument();
	}

	/**
	 * FocusGainedイベント
	 */
	protected void focusGainedActionPerformed() {
		// フォーカス当てる時のバックカラー設定
		focusGainedBackColor();
	}

	/**
	 * lostFocusイベント
	 */
	protected void focusLostActionPerformed() {
		// ロストフォーカス時のバックカラー設定
		focusLostBackColor();
	}

	/**
	 * フォーカス当てる時のバックカラー設定
	 */
	public void focusGainedBackColor() {
		if (isHighLight) {
			// 明るい緑モード
			backColor = getBackground();
			if (this.isEditable()) {
				setBackground(TTextBGColor.getHighLightFocusInColor());
			}
		}
	}

	/**
	 * ロストフォーカス時のバックカラー設定
	 */
	public void focusLostBackColor() {
		if (isHighLight) {
			// 明るい緑モード解除
			if (this.isEditable()) {
				setBackground(backColor);
			}
		}
	}

	/**
	 * キー処理.
	 * 
	 * @param evt キーイベント
	 */
	protected void handleKeyPressed(KeyEvent evt) {

		// Enterキー
		TGuiUtil.transferFocusByEnterKey(this, evt);

		// // F2 クリア対応
		// int fkey = TGuiUtil.checkFunctionKeyEvent(evt);
		// if(fkey == 2){
		// if(this.isEnabled() && this.isEditable()){
		// this.setText("");
		// }
		// }

		// ファンクションキー
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
	 * Tab移動可能かどうか
	 * 
	 * @return true:Tab移動可能
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

	// 固有設定. ********************************************

	/**
	 * 最大桁数
	 * 
	 * @return 最大桁数
	 */
	public int getMaxLength() {
		return this.maxLength;
	}

	/**
	 * 最大桁数
	 * 
	 * @param maxLength 最大桁数
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * IMEモードフラグ
	 * 
	 * @param flag true: IMEモード
	 */
	protected void setImeMode(boolean flag) {
		this.enableInputMethods(!flag); // 必要.
		this.enableInputMethods(flag);
	}

	/**
	 * 内部クラスのtext document
	 */
	protected class TextPasswordDocument extends PlainDocument {

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

			// バイト数による入力制御
			if (maxLength >= 0) {

				if (TPasswordField.this.getPassword().length + str.getBytes().length > maxLength) {

					return;
				}
			}

			for (int i = 0; i < str.length(); i++) {
				String s = str.substring(i, i + 1);

				// 全角禁止で全角が含まれている場合はNG
				if (s.getBytes().length > 1) {
					return;
				}

				// 正規表現チェック : 半角カナはNG
				if (s.matches("[｡-ﾟ]")) {
					return;
				}
			}

			super.insertString(offs, str, a);
		}
	}

	/**
	 * フォントサイズ変更用のサイズ補正を行う
	 * 
	 * @see javax.swing.JComponent#setPreferredSize(java.awt.Dimension)
	 */
	@Override
	public void setPreferredSize(Dimension d) {
		super.setPreferredSize(TGuiUtil.correctSize(TGuiUtil.TYPE_TEXTFIELD, d));
	}

	/**
	 * フォントサイズ変更用のサイズ補正を行う
	 * 
	 * @see java.awt.Component#setSize(java.awt.Dimension)
	 */
	@Override
	public void setSize(Dimension d) {
		super.setSize(TGuiUtil.correctSize(TGuiUtil.TYPE_TEXTFIELD, d));
	}

	/**
	 * フォントサイズ変更用のサイズ補正を行う
	 * 
	 * @see javax.swing.JComponent#setMaximumSize(java.awt.Dimension)
	 */
	@Override
	public void setMaximumSize(Dimension d) {
		super.setMaximumSize(TGuiUtil.correctSize(TGuiUtil.TYPE_TEXTFIELD, d));
	}
}
