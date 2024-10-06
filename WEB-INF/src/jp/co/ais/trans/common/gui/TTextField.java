package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.lang.Character.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

import com.klg.jclass.field.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * 標準文字列TextField
 */
public class TTextField extends JCTextField implements TInterfaceTabControl, TTableComponent {

	/** キーイベントコード */
	public int beforeKeyCode;

	/** タブ順 */
	protected int tabControlNo = -1;

	/** タブ移動可・付加 */
	protected boolean isTabEnabled = true;

	/** IME制御 */
	protected boolean imeFlag = true;

	/** 全角・半角の切替を行うかどうか */
	protected boolean isChangeCharacterSubsets = true;

	/** 文字変更がなくても書き換えがあればOldValueをクリアするかどうか */
	protected boolean clearOldValueByReplesedText = false;

	/** 変更前テキスト(focusGainedで変更) */
	protected String oldText = "";

	/** 複数のInputVerifierを扱う為のクラス */
	protected TInputVerifierMulti verifiers = new TInputVerifierMulti();

	/** インクリメントサーチ機能 */
	protected AutoCompleteUtil autoComplete = null;

	/** 入力制御ドキュメント */
	protected PlainDocument document = createPlainDocument();

	/** テキスト内容をコピーするTTextField */
	protected List<TTextField> valueCopyFields = new LinkedList<TTextField>();

	/** 汎用INDEXキー */
	protected int index;

	/** TableCellEditor利用か */
	protected boolean tableCellEditor = false;

	/** true:IME制御を行わない */
	public static final boolean isNoImeControl = ClientConfig.isFlagOn("trans.textfield.no.ime.control");

	/** true:フォーカス当てる時明るい緑 */
	public static final boolean isHighLight = ClientConfig.isFlagOn("trans.textfield.focus.highlight");

	/** 背景カラー */
	protected Color backColor = getBackground();

	/** 貼り付け機能使うか。true:使う */
	protected boolean useTablePaste = false;

	/** タイプ配列(貼り付け列→右全列) */
	protected int[] cellTypes = null;

	/**
	 * コンストラクタ.
	 */
	public TTextField() {
		super();

		this.setDocument(document);
		this.setEditable(true);
		this.setValue("");

		// JRE1.6対応(変換表示が切れる問題)
		Insets def = getMargin();
		setMargin(new Insets(1, def.left, 1, def.right));

		super.setInputVerifier(verifiers);

		// 名称を入力し、ロストフォーカス。listCopyNameFieldに指定のTTextFieldへ名称の内容をコピーする
		this.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				copyText();
				return true;
			}
		});

		// フォーカス用リスナ
		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				beforeKeyCode = -1;

				focusGainedActionPerformed(e);
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
				if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if (beforeKeyCode == KeyEvent.VK_ENTER) {
						evt.consume();
					}
				}

				handleKeyPressed(evt);

				beforeKeyCode = evt.getKeyCode();
			}

			@Override
			public void keyReleased(KeyEvent e) {

				beforeKeyCode = -1;
			}
		});

		super.setDropTarget(null);

		// TAB キーの自動移動を削除

		// 順方向
		Set<AWTKeyStroke> keySet;
		keySet = super.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);

		Set<AWTKeyStroke> newSet = new HashSet<AWTKeyStroke>();
		for (Iterator<AWTKeyStroke> i = keySet.iterator(); i.hasNext();) {
			AWTKeyStroke strk = i.next();
			if (strk.getKeyCode() != KeyEvent.VK_TAB) {
				newSet.add(strk);
			}
		}
		super.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newSet);

		// 逆方向
		keySet = super.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS);

		newSet = new HashSet<AWTKeyStroke>();
		for (Iterator<AWTKeyStroke> i = keySet.iterator(); i.hasNext();) {
			AWTKeyStroke strk = i.next();
			if (strk.getKeyCode() != KeyEvent.VK_TAB) {
				newSet.add(strk);
			}
		}

		super.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, newSet);

		initTextHelper();
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
	 * 文字列の中のマルチバイト文字
	 * 
	 * @param str
	 * @return マルチバイト文字
	 */
	protected String exceptMultiByte(String str) {

		if (Util.isNullOrEmpty(str)) {
			return "";
		}

		// 正規表現でASCII以外を除外
		Pattern p = Pattern.compile("[^\\p{ASCII}]");
		Matcher m = p.matcher(str);
		return m.replaceAll("");
	}

	/**
	 * キー処理.
	 * 
	 * @param evt キーイベント
	 */
	protected void handleKeyPressed(KeyEvent evt) {

		// ファンクションキー
		TGuiUtil.dispatchPanelBusinessFunctionKey(this, evt);

		if (!this.isFocusOwner()) {
			return;
		}

		// Enterキー
		TGuiUtil.transferFocusByEnterKey(this, evt);

		repaint();

		// // F2 クリア対応
		// int fkey = TGuiUtil.checkFunctionKeyEvent(evt);
		// if(fkey == 2){
		// if(this.isEnabled() && this.isEditable()){
		// this.setText("");
		// }
		// }
	}

	/**
	 * 入力制御
	 * 
	 * @return PlainDocument
	 */
	protected PlainDocument createPlainDocument() {
		return new TStringPlainDocument(this) {

			/**
			 * @see AbstractDocument#remove(int, int)
			 */
			@Override
			public void remove(int offs, int len) throws BadLocationException {
				if (clearOldValueByReplesedText) {
					TTextField.this.clearOldText(); // 古い入力データを削除
				}

				super.remove(offs, len);
			}
		};
	}

	/**
	 * FocusGainedイベントを感知し、OldTextを挿入.
	 * 
	 * @param e イベント
	 */
	protected void focusGainedActionPerformed(FocusEvent e) {

		// 他のWindowからフォーカスが来たときにnullになる。
		Component comp = e.getOppositeComponent();

		if (comp != null) {
			// コンポーネントの親のFrame,Dialogが同じであれあばoldTextを上書きする。
			if (comp instanceof Container) {

				Container a = TGuiUtil.getParentFrameOrDialog((Container) comp);
				Container b = TGuiUtil.getParentFrameOrDialog(this);

				if (a.equals(b)) {

					this.pushOldText();
				}
			}
		}

		this.selectAll();

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
		this.commitEdit();
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
	 * コピー指定TTextFieldへ入力値をコピーする
	 */
	protected void copyText() {

		// ブランクはコピーしない
		if (Util.isNullOrEmpty(this.getText())) {
			return;
		}

		// 指定のTTextFieldへ内容をコピー
		for (TTextField txtField : valueCopyFields) {

			// 対象のテキストがブランク場合は、コピーしない
			if (Util.isNullOrEmpty(txtField.getText())) {
				txtField.setText(this.getText());
			}
		}
	}

	/**
	 * @return 変更前テキスト
	 * @deprecated 使用しない(isValueChanged()を利用)
	 */
	public String getOldText() {
		return this.oldText;
	}

	/**
	 * 変更前テキストをクリア
	 */
	public void clearOldText() {
		this.oldText = null;
	}

	/**
	 * 変更前テキストに現テキストを設定
	 */
	public void pushOldText() {
		this.oldText = this.getText();
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
	 * space入力許可
	 * 
	 * @return true:許可
	 */
	public boolean isAllowedSpace() {
		return ((TStringPlainDocument) document).isAllowedSpace();
	}

	/**
	 * space入力許可
	 * 
	 * @param b true:許可
	 */
	public void setAllowedSpace(boolean b) {
		((TStringPlainDocument) document).setAllowedSpace(b);
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
	 * 文字変更がなくても書き換えがあればOldValueをクリアするかどうか
	 * 
	 * @return true:クリアする
	 */
	public boolean isClearOldValueByReplesedText() {
		return clearOldValueByReplesedText;
	}

	/**
	 * 文字変更がなくても書き換えがあればOldValueをクリアするかどうか.<br>
	 * (デフォルトfalse)
	 * 
	 * @param clearOldValueByReplesedText true:クリアする
	 */
	public void setClearOldValueByReplesedText(boolean clearOldValueByReplesedText) {
		this.clearOldValueByReplesedText = clearOldValueByReplesedText;
	}

	/**
	 * editable, enabledプロパティ同時設定
	 * 
	 * @param flag setEditable(), setEnabled()設定値
	 */
	public void setEditableEnabled(boolean flag) {

		if (flag) {
			super.setEnabled(true);
			this.setEditable(true);
		} else {
			this.setEditable(false);
			super.setEnabled(false);
		}
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
	 * 値の設定. <br>
	 * enabled=false,editable=falseでも、文字を設定できる。
	 * 
	 * @param t 設定文字列
	 */
	@Override
	public void setText(String t) {
		super.setText(t);

		super.moveCaretPosition(0);
	}

	/**
	 * 値の設定. <br>
	 * フィールドに値を設定し、テキストに文字を設定する.
	 * 
	 * @param obj 設定値
	 */
	@Override
	public void setValue(Object obj) {
		this.setText(Util.avoidNull(obj));
	}

	/**
	 * 入力値をクリアする.
	 */
	public void clear() {
		this.setValue("");
	}

	/**
	 * イベントのマスク
	 * 
	 * @param mask
	 */
	public void setDisableEvents(long mask) {
		this.disableEvents(mask);
	}

	/**
	 * イベント許可
	 * 
	 * @param mask
	 */
	public void setEnableEvents(long mask) {
		this.enableEvents(mask);
	}

	/**
	 * キーイベント処理 <br>
	 * tabキーの自動移動を削除しているため、tabキー入力時も このmethodが呼ばれる。
	 * 
	 * @param evt キーイベント
	 */
	@Override
	public void processKeyEvent(KeyEvent evt) {

		if (evt.getKeyCode() == KeyEvent.VK_TAB) {
			if (evt.getID() == KeyEvent.KEY_PRESSED) {
				this.handleKeyPressed(evt);
				evt.consume();
				return;
			}
		}
		super.processKeyEvent(evt);
	}

	/**
	 * 変更されたかどうかを判定する
	 * 
	 * @return true:変更されている
	 */
	public boolean isValueChanged() {

		// 現在の入力値
		String crnt = getText();

		if (this.oldText == null || "".equals(this.oldText)) {
			return true;
		}

		return !(Util.avoidNullNT(crnt).equals(Util.avoidNullNT(this.oldText)));
	}

	/**
	 * 変更されたかどうかを判定する. null(ブランク)も対象
	 * 
	 * @return true:変更されている
	 */
	public boolean isValueChanged2() {

		// 現在の入力値
		String crnt = getText();

		boolean flag1 = this.oldText == null;
		boolean flag2 = crnt == null;

		if ((flag1 && !flag2) || (!flag1 && flag2)) {
			return true;
		}

		return !(Util.avoidNullNT(crnt).equals(Util.avoidNullNT(this.oldText)));
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
	 * Value取得(getText()と等価)
	 */
	@Override
	public Object getValue() {
		return this.getText();
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
	 * @see javax.swing.JComponent#setInputVerifier(javax.swing.InputVerifier)
	 */
	@Override
	public void setInputVerifier(InputVerifier inputVerifier) {
		verifiers.add(inputVerifier);
	}

	/**
	 * InputVerifyを取り消す
	 * 
	 * @param inputVerifier InputVerify
	 */
	public void removeInputVerifier(InputVerifier inputVerifier) {
		verifiers.remove(inputVerifier);
	}

	/**
	 * 全てのInputVerifyを取り消す
	 */
	public void removeAllInputVerifier() {
		if (verifiers != null && verifiers.verifiers != null) {
			verifiers.verifiers.clear();
		}
	}

	/**
	 * インクリメントサーチ機能の取得
	 * 
	 * @return autoComplete インクリメントサーチ機能
	 */
	public AutoCompleteUtil getAutoComplete() {
		return autoComplete;
	}

	/**
	 * インクリメントサーチ機能の設定
	 * 
	 * @param autoComplete インクリメントサーチ機能
	 */
	public void setAutoComplete(AutoCompleteUtil autoComplete) {
		this.autoComplete = autoComplete;
	}

	/**
	 * @see javax.swing.JComponent#setPreferredSize(java.awt.Dimension)
	 */
	@Override
	public void setPreferredSize(Dimension d) {
		super.setPreferredSize(TGuiUtil.correctSize(TGuiUtil.TYPE_TEXTFIELD, d));
	}

	/**
	 * @see java.awt.Component#setSize(Dimension)
	 */
	@Override
	public void setSize(Dimension d) {
		super.setSize(TGuiUtil.correctSize(TGuiUtil.TYPE_TEXTFIELD, d));
	}

	/**
	 * @see java.awt.Component#setSize(int, int)
	 */
	@Override
	public void setSize(int width, int height) {
		Dimension d = TGuiUtil.correctSize(TGuiUtil.TYPE_TEXTFIELD, new Dimension(width, height));
		super.setSize(d.width, d.height);
	}

	/**
	 * @see javax.swing.JComponent#setMaximumSize(java.awt.Dimension)
	 */
	@Override
	public void setMaximumSize(Dimension d) {
		super.setMaximumSize(TGuiUtil.correctSize(TGuiUtil.TYPE_TEXTFIELD, d));
	}

	/**
	 * ボタン連動.Enterキー押下時に登録ボタンを押下する.<br>
	 * （Enterでのフォーカス移動が無効になるので注意）
	 * 
	 * @param btn 対象のボタン
	 */
	public void addEnterToButton(final TButton btn) {

		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (!isFocusOwner() || !isEditable()) {
						return;
					}

					btn.doClick();
					btn.requestFocusInWindow();
				}
			}
		});
	}

	/**
	 * ロストフォーカス時、テキストの入力値をコピーするテキストフィールドを登録.
	 * 
	 * @param txtField 対象TTextField
	 */
	public void addValueCopyTextField(TTextField txtField) {
		valueCopyFields.add(txtField);
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

		TTextField renderer = new TTextField();
		return new TTextRenderer(renderer, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellEditor getCellEditor(TTable tbl) {
		tableCellEditor = true;
		return new TTextEditor(this, tbl);
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
	 * 貼り付け機能使うか。true:使うの取得
	 * 
	 * @return useTablePaste 貼り付け機能使うか。true:使う
	 */
	public boolean isUseTablePaste() {
		return useTablePaste;
	}

	/**
	 * 貼り付け機能使うか。true:使うの設定
	 * 
	 * @param useTablePaste 貼り付け機能使うか。true:使う
	 */
	public void setUseTablePaste(boolean useTablePaste) {
		this.useTablePaste = useTablePaste;
	}

	/**
	 * タイプ配列(貼り付け列→右全列)の取得
	 * 
	 * @return cellTypes タイプ配列(貼り付け列→右全列)
	 */
	public int[] getCellTypes() {
		return cellTypes;
	}

	/**
	 * タイプ配列(貼り付け列→右全列)の設定
	 * 
	 * @param cellTypes タイプ配列(貼り付け列→右全列)
	 */
	public void setCellTypes(int[] cellTypes) {
		this.cellTypes = cellTypes;
	}

}
