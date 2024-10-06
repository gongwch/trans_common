package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.Timer;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.event.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * TPanelを継承した、業務パネルの基盤となるPanel.
 */
public class TPanelBusiness extends TPanel implements TInterfaceTabPolicy {

	/** function key max no. */
	public static final int FUNCTION_KEY_NUM = 15;

	/** tab番号. */
	protected List<TNameComponent> listTabControlNo = new ArrayList<TNameComponent>();

	/** tab順component */
	protected List<Component> listTabComponent = new ArrayList<Component>();

	/** focus policy */
	protected TFocusPolicy focusPolicy = null;

	/** タブ順フォーカス移動ポリシー(Enter keyのとき) */
	protected TFocusPolicyEnter focusPolicyEnter = null;

	/** true:enter-keyでの移動 */
	protected boolean modeEnter = false;

	/** function key -> action 対応付けtable. */
	protected Map<Integer, JButton[]> functionKeyToActionMap = new HashMap<Integer, JButton[]>();

	/** Verifier有効/無効 */
	protected boolean isVerifierEnabled = true;

	/** 直前にアクセスされたコンポーネント */
	protected KeyEvent keyEventBefore = null;

	/** プログラムコード */
	protected String programCode = null;

	/**
	 * コンストラクタ
	 */
	public TPanelBusiness() {
		super();
		this.initListener();

		functionKeyToActionMap.put(TGuiUtil.WITH_NO_KEY, new JButton[FUNCTION_KEY_NUM]);
		functionKeyToActionMap.put(TGuiUtil.WITH_CTRL_KEY, new JButton[FUNCTION_KEY_NUM]);
		functionKeyToActionMap.put(TGuiUtil.WITH_SHIFT_KEY, new JButton[FUNCTION_KEY_NUM]);
		functionKeyToActionMap.put(TGuiUtil.WITH_ALT_KEY, new JButton[FUNCTION_KEY_NUM]);
	}

	/**
	 * Listenerの初期設定. <br>
	 * ファンクションキーの取得のための初期化。<br>
	 * KeyListenerを設定する。<br>
	 */
	private void initListener() {

		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				paneKeyPressed(evt);
			}
		});
	}

	/**
	 * PanelのUI初期化を完結させる. <br>
	 * メッセージの変換表示。 タブ順の設定。<br>
	 * メッセージIDの設定、タブ順番号の設定が完了したら即このmethodを呼び出す。
	 */
	public void initPanel() {

		// コンポーネントの復元
		restoreComponent();

		// メッセージの変換.
		this.translateLangMessageID();

		// ショートカットキー登録(ボタン登録分の追加)
		this.registShortcutKey();

		// tab-key順の設定.

		// タブ移動で上位フォーカスサイクルにフォーカスを戻さない
		// super.setFocusCycleRoot(true);

		// Tab順の収集
		this.collectTabControlNo();

		// Tab順の登録
		TGuiUtil.changeTabControlNo(listTabControlNo, listTabComponent);

		// Tab順の反映
		this.focusPolicy = new TFocusPolicy(this.listTabComponent);
		this.focusPolicyEnter = new TFocusPolicyEnter(this.listTabComponent);

		TGuiUtil.getParentRoot(this).setFocusTraversalPolicy(this.focusPolicy);

	}

	/**
	 * タブ移動の設定。<br>
	 * appletからのcontainer階層構築後に呼び出す。
	 */
	public void setTabPolicy() {
		Container p = TGuiUtil.getParentRoot(this);
		this.modeEnter = false;
		p.setFocusTraversalPolicy(this.focusPolicy);
	}

	/**
	 * enter-keyでのタブ移動の設定。<br>
	 * 
	 * @param b true:enter-keyでの移動 false:通常移動
	 */
	public void setModeEnter(boolean b) {

		this.modeEnter = b;
		Container p = TGuiUtil.getParentRoot(this);
		p.setFocusTraversalPolicy(b ? this.focusPolicyEnter : this.focusPolicy);
	}

	/**
	 * enter-keyでのタブ移動の設定。<br>
	 * 
	 * @return b true:enter-keyでの移動 false:通常移動
	 */
	public boolean getModeEnter() {

		return this.modeEnter;
	}

	/**
	 * パネルのアイテムのメッセージIDを変換する.
	 */
	protected void translateLangMessageID() {

		// Dialogのタイトルも処理するため、rootのcontainer以降を指定する.
		TGuiUtil.translateLangMessageID(this, TClientLoginInfo.getInstance().getUserLanguage());
	}

	/**
	 * ショートカットキー登録.
	 */
	protected void registShortcutKey() {
		TGuiUtil.recursiveCollectFunctionKey(this, functionKeyToActionMap, 0);
	}

	/**
	 * tabNo収集.
	 */
	protected void collectTabControlNo() {
		listTabControlNo.clear();
		TGuiUtil.collectTabControlNo(this, this.listTabControlNo);
	}

	/**
	 * key入力の処理
	 * 
	 * @param e
	 */
	private void paneKeyPressed(KeyEvent e) {

		int fkey = TGuiUtil.checkFunctionKeyEvent(e);
		int mkey = TGuiUtil.checkWithKeyEvent(e);

		if (0 < fkey) {
			this.handleFunctionKey(fkey, mkey);
		}
		return;
	}

	/**
	 * 同時押しキーを元に、ファンクションキーとボタンの対応付けテーブルを返す。
	 * 
	 * @param withKey 同時押しキー WITH_NO_KEY, WITH_SHIFT_KEY, WITH_CTRL_KEY or WITH_ALT_KEY
	 * @return ファンクションキーとボタン対応付けテーブル
	 */
	private JButton[] getFunctionKeyActionTable(int withKey) {

		return this.functionKeyToActionMap.get(withKey);
	}

	/**
	 * ファンクションキーの処理を行う。<br>
	 * 登録されたファンクションキーとボタンの対応付けテーブルに従い、ボタン押下等価の処理を行う。<br>
	 * ファンクションキーとボタンの対応付けテーブルを登録せず、<br>
	 * 独自処理を行うときは、overrideする。
	 * 
	 * @param fkey ファンクションキー番号 F1 -> F12
	 * @param mkey 複合キー
	 */
	public void handleFunctionKey(int fkey, int mkey) {

		if (fkey <= 0 || FUNCTION_KEY_NUM < fkey) {
			return;
		}

		JButton[] functionKeyToActionTable = this.getFunctionKeyActionTable(mkey);

		if (functionKeyToActionTable == null) {
			return;
		}
		if (functionKeyToActionTable.length < fkey) {
			return;
		}

		JButton trgt = functionKeyToActionTable[fkey - 1];
		if (trgt == null) {
			return;
		}

		// 無効状態であれば実行しない。
		if (!trgt.isEnabled() || !TGuiUtil.isActive(trgt) || !TGuiUtil.getParentFrameOrDialog(trgt).isEnabled()) {
			return;
		}

		// ボタン押下イベント実行

		if (trgt.getVerifyInputWhenFocusTarget()) {
			// Verifiy有効の場合のみフォーカス移動
			trgt.requestFocus();
		}

		// タイマー設定
		Timer tim = new Timer(300, null);
		tim.setRepeats(false);
		tim.addActionListener(new DoClickListener(tim, trgt));
		tim.start();

		// タイマーが実行されている間は操作が無効
		TGuiUtil.getParentFrameOrDialog(trgt).setEnabled(false);
	}

	/**
	 * ショートカットキー実行のためのタイマーイベント 遅延実行クラス
	 */
	private class DoClickListener implements ActionListener {

		/** 押下ボタン */
		private JButton btn;

		/** 遅延実行のためのTimer */
		private Timer tim;

		/**
		 * constructor.
		 * 
		 * @param tim
		 * @param btn
		 */
		public DoClickListener(Timer tim, JButton btn) {
			this.tim = tim;
			this.btn = btn;
		}

		/**
		 * タイマー時限実行method
		 */
		public void actionPerformed(ActionEvent evt) {
			this.tim.stop();
			// 操作復活
			TGuiUtil.getParentFrameOrDialog(btn).setEnabled(true);

			// requestFocus()後、一定時間をおいて、まだfocusを持っていたとき
			if (!this.btn.getVerifyInputWhenFocusTarget() || this.btn.hasFocus()) {
				this.btn.doClick();
			}
		}
	}

	/**
	 * TButton.setShortcutKey()を利用してください. <br>
	 * <s> ファンクションキーとボタンの対応付けテーブルを登録する。<br>
	 * ファンクションキーが押下されたとき、tbl[FUNCTION_KEY_NUM]で登録されたボタンのdoClick()を呼び出す.<br>
	 * F[n]が押下されたとき、tbl[n-1].doClick()が実行される。<br>
	 * 対応するボタンがないときはnullを設定する. </s>
	 * 
	 * @deprecated ショートカットはボタンに登録
	 * @param tbl TPanelBusiness.FUNCTION_KEY_NUM
	 */
	public void setFunctionKeyToActionTable(JButton[] tbl) {

		this.setFunctionKeyToActionTable(tbl, TGuiUtil.WITH_NO_KEY);
	}

	/**
	 * TButton.setShortcutKey()を利用してください. <br>
	 * <s> ファンクションキーとボタンと同時押しキーの対応付けテーブルを登録する。<br>
	 * ファンクションキーが押下されたとき、tbl[FUNCTION_KEY_NUM]で登録されたボタンのdoClick()を呼び出す.<br>
	 * F[n]が押下されたとき、tbl[n-1].doClick()が実行される。<br>
	 * 対応するボタンがないときはnullを設定する. </s>
	 * 
	 * @deprecated ショートカットはボタンに登録
	 * @param tbl TPanelBusiness.FUNCTION_KEY_NUM
	 * @param mkey WITH_SHIFT_KEY | WITH_CTRL_KEY | WIDTH_ALT_KEYの組み合わせ
	 */
	public void setFunctionKeyToActionTable(JButton[] tbl, int mkey) {

		this.functionKeyToActionMap.put(mkey, tbl);
	}

	/**
	 * for MouseListener
	 */
	public void panelRequestFocus() {
		this.requestFocus(); // キーボードフォーカスの設定
	}

	// フォーカス移動の自由度を上げるためmethod 追加

	/**
	 * 直前にアクセスされたKeyEvent setter.
	 * 
	 * @param evt KeyEvent
	 */
	public void setEventBefore(KeyEvent evt) {

		this.keyEventBefore = evt;
	}

	/**
	 * 直前にアクセスされたKeyEvent getter.
	 * 
	 * @return KeyEvent
	 */
	public KeyEvent getEventBefore() {

		return this.keyEventBefore;
	}

	/**
	 * 先頭の入力アイテムにフォーカスを移動する。
	 */
	public void transferFocusTopItem() {

		Component comp = focusPolicyEnter.getDefaultComponent(null);

		// 有効な先頭「入力」アイテムがなければ、TAB移動の先頭アイテムに
		// フォーカスする。

		if (comp == null) {
			comp = focusPolicy.getDefaultComponent(null);
		}

		if (comp != null) {
			comp.requestFocus();
		}
	}

	/**
	 * 配下コンポーネントのVerifier有効／無効
	 * 
	 * @param flag
	 */
	public void setVerifierEnabled(boolean flag) {
		TGuiUtil.setComponentsVerifierEnabled(this.getParentFrame(), flag);
		this.isVerifierEnabled = flag;
	}

	/**
	 * 配下コンポーネントのVerifier有効／無効
	 * 
	 * @return 有効／無効
	 */
	public boolean isVerifierEnabled() {
		return this.isVerifierEnabled;
	}

	/**
	 * 現在のカーソル状態がWAITかどうかを判定
	 * 
	 * @return WAIT状態である場合true
	 */
	public boolean isWaitCursorNow() {
		return Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR).equals(this.getCursor());
	}

	/**
	 * ログインユーザの言語コードに対する、メッセージを返す. 指定単語ID、または単語をバインド.
	 * 
	 * @param messageID メッセージID
	 * @param bindIds 単語ID、または、単語のリスト
	 * @return 変換されたメッセージ
	 */
	protected String getMessage(String messageID, Object... bindIds) {

		return MessageUtil.convertMessage(getLoginLanguage(), messageID, bindIds);
	}

	/**
	 * ログインユーザの言語コードに対する、単語文字を返す.
	 * 
	 * @param wordID 単語ID
	 * @return 単語文字
	 */
	public String getWord(String wordID) {

		return MessageUtil.getWord(getLoginLanguage(), wordID);
	}

	/**
	 * ログインユーザの言語コードに対する、単語文字(略称)を返す.
	 * 
	 * @param wordID 単語ID
	 * @return 単語文字
	 */
	public String getShortWord(String wordID) {

		return MessageUtil.getShortWord(getLoginLanguage(), wordID);
	}

	/**
	 * ログインユーザの言語コードに対する、単語文字リストを返す.
	 * 
	 * @param wordIDs 単語IDリスト
	 * @return 単語文字リスト
	 */
	public String[] getWordList(String[] wordIDs) {

		return MessageUtil.getWordList(getLoginLanguage(), wordIDs);
	}

	/**
	 * ログインユーザの言語コード
	 * 
	 * @return 言語コード
	 */
	protected String getLoginLanguage() {
		return TClientLoginInfo.getInstance().getUserLanguage();
	}

	/**
	 * ファンクションキーイベントを登録する.<br>
	 * デフォルトF1キー
	 * 
	 * @param listener ファンクションキーイベント
	 */
	protected void addFunctionKeyListener(final FunctionKeyListener listener) {
		addFunctionKeyListener(listener, KeyEvent.VK_F1);
	}

	/**
	 * ファンクションキーイベントを登録する.
	 * 
	 * @param listener ファンクションキーイベント
	 * @param keyCode キーコード(KeyEvent.VK_F1～F12)
	 */
	protected void addFunctionKeyListener(final FunctionKeyListener listener, final int keyCode) {

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == keyCode) {
					listener.setRepeats(false);
					listener.start();
				}
			}
		});
	}

	/**
	 * プログラムID
	 * 
	 * @return プログラムID
	 */
	public String getProgramCode() {
		return programCode;
	}

	/**
	 * プログラムID
	 * 
	 * @param programCode プログラムID
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	/**
	 * コンポーネントを復元する
	 */
	protected void restoreComponent() {
		try {
			restoreComponent(this.getClass());
		} catch (Exception e) {
			ClientErrorHandler.handledException(e);
		}
	}

	/**
	 * 指定クラスのフィールドのコンポーネントを復元する
	 * 
	 * @param c 指定クラス
	 * @throws Exception
	 */
	protected void restoreComponent(Class c) throws Exception {
		if (c == null || TPanelBusiness.class.equals(c)) {
			return;
		}

		// 親クラスの復元
		restoreComponent(c.getSuperclass());

		Field[] fields = c.getDeclaredFields(); // publicのみ⇒privateも含み

		for (Field field : fields) {
			field.setAccessible(true); // アクセス可能
			Object obj = field.get(this);

			if (obj instanceof TStorable) {
				TStorable storable = (TStorable) obj;
				TStorableKey key = storable.getKey();
				if (key == null) {
					key = new TDefaultStorableKey(c, field);
				}

				storable.restoreComponent(key);
			}
		}
	}

	/**
	 * @param list
	 */
	public void setListTabControlNo(List<TNameComponent> list) {
		this.listTabControlNo = list;
	}

	/**
	 * @param list
	 */
	public void setListTabComponent(List<Component> list) {
		this.listTabComponent = list;

		this.focusPolicy = new TFocusPolicy(list);
		this.focusPolicyEnter = new TFocusPolicyEnter(list);
	}
}
