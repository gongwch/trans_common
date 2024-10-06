package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.lang.Character.Subset;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * JTextAreaに、タブ順インターフェイスを追加したTextArea. <br>
 * 入力最大桁数 setMaxLength(), getMaxLength()を追加.
 */
public class TTextArea extends JTextArea implements TInterfaceTabControl, TTableComponent {

	/** 改行文字 */
	protected static final String ENTER_TEXT = System.getProperty("line.separator");

	/** フォーカス番号 */
	protected int tabControlNo = -1;

	/** タブ移動可能かどうか */
	protected boolean isTabEnabled = true;

	/** 全角・半角の切替を行うかどうか */
	protected boolean isChangeCharacterSubsets = true;

	/** IME制御 */
	protected boolean imeFlag;

	/** 入力制御ドキュメント */
	protected PlainDocument document = createPlainDocument();

	/** ENTERキーで改行 0:しない、1:ENTER、2:ALT+ENTER */
	protected int enterType = 1;

	/** 汎用INDEXキー */
	protected int index;

	/** TableCellEditor利用か */
	protected boolean tableCellEditor = false;

	/** スプレッド高さ自動調整 */
	protected boolean allowAutoAdjustTableHeight = false;

	/** 共有Renderer */
	protected TTextArea renderer = null;

	/** true:IME制御を行わない */
	public static final boolean isNoImeControl = ClientConfig.isFlagOn("trans.textfield.no.ime.control");

	/** true:フォーカス当てる時明るい緑 */
	public static final boolean isHighLight = ClientConfig.isFlagOn("trans.textfield.focus.highlight");

	/** 背景カラー */
	protected Color backColor = getBackground();

	/**
	 * コンストラクタ.
	 */
	public TTextArea() {
		super();

		this.setLineWrap(true);
		this.setImeMode(true);

		this.setDocument(document);

		// ファンクションキー用リスナ
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {

				handleKeyPressed(evt);
			}
		});

		// フォーカス用リスナ
		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				focusGainedActionPerformed(e);
			}

			@Override
			@SuppressWarnings("unused")
			public void focusLost(FocusEvent e) {
				focusLostActionPerformed();
			}
		});

		this.initTextHelper();
	}

	/**
	 * 入力制御
	 * 
	 * @return PlainDocument
	 */
	protected PlainDocument createPlainDocument() {
		return new TStringPlainDocument(this);
	}

	/**
	 * ファンクションキー処理.
	 * 
	 * @param evt キーイベント
	 */
	@SuppressWarnings("deprecation")
	protected void handleKeyPressed(KeyEvent evt) {

		TGuiUtil.dispatchPanelBusinessFunctionKey(this, evt);

		switch (evt.getKeyChar()) {
			case KeyEvent.VK_ENTER:

				switch (enterType) {
					case 0: // 改行なし
						evt.consume(); // Enterでフォーカス移動
						break;

					case 1: // Enter
						if (!evt.isControlDown()) {
							return; // Ctrl+Enterでフォーカス移動(改行はデフォルト)
						}

						evt.consume();
						break;

					case 2: // Alt+Enter
						if (!evt.isAltDown()) {
							evt.consume();
							break;
						}

						evt.setModifiers(0);

						return;
				}

				break;

			case KeyEvent.VK_TAB:
				evt.consume(); // Tabでフォーカス移動
				break;

			default:
				break;
		}

		// Enterキー
		TGuiUtil.transferFocusByEnterKey(TTextArea.this, evt);
	}

	/**
	 * テキスト補助機能
	 */
	protected void initTextHelper() {
		// UNDO/REDO
		TGuiUtil.initUndoRedo(this);

		// カットアンドペースト
		addMouseListener(TGuiUtil.createCnPListener(this));
	}

	/**
	 * FocusGainedイベントを感知し、OldTextを挿入.
	 * 
	 * @param e イベント
	 */
	@SuppressWarnings("unused")
	protected void focusGainedActionPerformed(FocusEvent e) {

		if (enterType != 1) {
			this.selectAll();
		}

		// IME入力モード制御
		if (isChangeCharacterSubsets && this.getInputContext() != null) {
			if (!isNoImeControl()) {
				// IME制御を行う場合のみ
				Subset[] subsets = isImeMode() ? TClientLoginInfo.getInstance().getCharacterSubsets() : null;
				this.getInputContext().setCharacterSubsets(subsets);
			}
		}

		// フォーカス当てる時のバックカラー設定
		focusGainedBackColor();
	}

	/**
	 * 再度、イベントを起し、lostFocus時に、移動先のenabledが変わる場合に対応する。
	 */
	protected void focusLostActionPerformed() {

		this.setCaretPosition(0);

		// Enterキー,Tabキーの再イベントが必要あればイベントを起す。
		TGuiUtil.transferBeforeFocusByEnterKey(this);

		// IME入力制御は解除
		if (isChangeCharacterSubsets && getInputContext() != null) {
			getInputContext().endComposition();
			if (!isNoImeControl()) {
				// IME制御を行う場合のみ
				getInputContext().setCharacterSubsets(null);
			}
		}

		// ロストフォーカス時のバックカラー設定
		focusLostBackColor();
	}

	/**
	 * @return true:IME制御なし
	 */
	public boolean isNoImeControl() {
		return isNoImeControl;
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
	 * タブフォーカス可否
	 * 
	 * @return タブフォーカス可否
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
	 * 最大桁数
	 * 
	 * @return 最大桁数
	 */
	public int getMaxLength() {
		return ((TStringPlainDocument) document).getMaxLength();
	}

	/**
	 * 最大桁数
	 * 
	 * @param maxLength 最大桁数
	 */
	public void setMaxLength(int maxLength) {
		((TStringPlainDocument) document).setMaxLength(maxLength);
	}

	/**
	 * 編集可・不可制御
	 */
	@Override
	public void setEditable(boolean edit) {
		super.setEditable(edit);

		this.setImeMode(this.imeFlag);
	}

	/**
	 * IMEモードフラグ
	 * 
	 * @param flag true: IMEモード(全角)
	 */
	public void setImeMode(boolean flag) {
		this.imeFlag = flag;

		if (document != null && document instanceof TStringPlainDocument) {
			((TStringPlainDocument) document).setImeMode(flag);
		}

		enableInputMethods(flag);
	}

	/**
	 * IMEモードフラグ
	 * 
	 * @return true: IMEモード(全角)
	 */
	public boolean isImeMode() {
		return this.imeFlag;
	}

	/**
	 * ENTERキーで改行するかどうか
	 * 
	 * @param isEnter true:改行する、false:改行しない(デフォルト)
	 */
	public void setEnterToChangingLine(boolean isEnter) {
		enterType = isEnter ? 1 : 0;
	}

	/**
	 * ALT+ENTERキーで改行するかどうか
	 * 
	 * @param isAltEnter true:改行する、false:改行しない(デフォルト)
	 */
	public void setAltEnterToChangingLine(boolean isAltEnter) {
		enterType = isAltEnter ? 2 : 0;
	}

	/**
	 * 入力値の取得.<br>
	 * 値がMaxLengthより大きい場合、右側から削除して提供する.
	 */
	@Override
	public String getText() {

		String str = super.getText();

		if (str.getBytes().length > getMaxLength()) {
			str = StringUtil.leftBX(str, getMaxLength());
		}

		return str;
	}

	/**
	 * 入力された値をそのまま返す.
	 * 
	 * @return 入力された値
	 */
	public String getInputText() {
		return super.getText();
	}

	/**
	 * 入力値が有るかどうか
	 * 
	 * @return true:入力値有り
	 */
	public boolean isEmpty() {
		return Util.isNullOrEmpty(getText());
	}

	/**
	 * 全角・半角のフラグを保持するかどうか.<br>
	 * falseに設定すると、初期フォーカスイン時、IME:OFFになる.
	 * 
	 * @param bol true:保持しない。 false:保持する。
	 */
	public void setIsChangeCharacterSubsets(boolean bol) {
		this.isChangeCharacterSubsets = bol;
	}

	/**
	 * 禁則文字(入力させない文字)の設定
	 * 
	 * @param words 禁則文字リスト
	 */
	public void setProhibitionWords(String... words) {
		((TStringPlainDocument) document).setProhibitionWords(words);
	}

	/**
	 * 入力可能文字を正規表現で指定する.
	 * 
	 * @param regex 正規表現文字
	 */
	public void setRegex(String regex) {
		((TStringPlainDocument) document).setRegex(regex);
	}

	/**
	 * 入力値をクリアする.
	 */
	public void clear() {
		this.setText("");
	}

	/**
	 * 改行付きのテキスト追加
	 * 
	 * @param txt テキスト
	 */
	public void addText(String txt) {
		if (isEmpty()) {
			setText(txt);
			return;
		}

		setText(getText() + SystemUtil.LINE_SEP + txt);
	}

	/**
	 * 区切り改行でテキスト追加
	 * 
	 * @param txt テキスト
	 */
	public void setTextList(String... txt) {
		setTextList(Arrays.asList(txt));
	}

	/**
	 * 区切り改行でテキスト追加
	 * 
	 * @param list テキスト
	 */
	public void setTextList(List<String> list) {
		clear();

		for (String txt : list) {
			addText(txt);
		}
	}

	/**
	 * インデックス番号
	 * 
	 * @return インデックス番号
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellRenderer(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellRenderer getCellRenderer(TTable tbl) {

		TTextArea area = renderer != null ? renderer : new TTextArea();
		area.setAllowAutoAdjustTableHeight(this.isAllowAutoAdjustTableHeight()); // 高さ自動調整
		return new TTextAreaRenderer(area, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellEditor getCellEditor(TTable tbl) {
		tableCellEditor = true;
		return null;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getRowIndex()
	 */
	public int getRowIndex() {
		return getIndex();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#setRowIndex(int)
	 */
	public void setRowIndex(int rowIndex) {
		this.index = rowIndex;
	}

	/**
	 * 水平方向のalign getter
	 * 
	 * @return 水平方向のalign
	 */
	public int getDefaultCellRendererHorizontalAlignment() {
		return SwingConstants.LEFT;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#isTableCellEditor()
	 */
	public boolean isTableCellEditor() {
		return tableCellEditor;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#setTableCellEditor(boolean)
	 */
	public void setTableCellEditor(boolean tableCellEditor) {
		this.tableCellEditor = tableCellEditor;
	}

	/**
	 * スプレッド高さ自動調整の取得
	 * 
	 * @return allowAutoAdjustTableHeight スプレッド高さ自動調整
	 */
	public boolean isAllowAutoAdjustTableHeight() {
		return allowAutoAdjustTableHeight;
	}

	/**
	 * スプレッド高さ自動調整の設定
	 * 
	 * @param allowAutoAdjustTableHeight スプレッド高さ自動調整
	 */
	public void setAllowAutoAdjustTableHeight(boolean allowAutoAdjustTableHeight) {
		this.allowAutoAdjustTableHeight = allowAutoAdjustTableHeight;
	}

	/**
	 * 共有Rendererの取得
	 * 
	 * @return renderer 共有Renderer
	 */
	public TTextArea getRenderer() {
		return renderer;
	}

	/**
	 * 共有Rendererの設定
	 * 
	 * @param renderer 共有Renderer
	 */
	public void setRenderer(TTextArea renderer) {
		this.renderer = renderer;
	}

}
