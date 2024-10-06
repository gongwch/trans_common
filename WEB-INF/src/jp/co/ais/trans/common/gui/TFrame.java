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
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;

/**
 * JFrameを継承し、messageIDインターフェースを実装したFrame.
 */
// TODO Delegaterに纏める
public class TFrame extends JFrame implements TInterfaceLangMessageID, TInterfaceTabPolicy {

	/** function key max no. */
	public static final int FUNCTION_KEY_NUM = 15;

	private String messageID = null;

	/** tab番号. */
	private List<TNameComponent> listTabControlNo = new ArrayList<TNameComponent>();

	/** tab順component */
	private List<Component> listTabComponent = new ArrayList<Component>();

	/** focus policy */
	private TFocusPolicy focusPolicy = null;

	private TFocusPolicyEnter focusPolicyEnter = null;

	private boolean modeEnter = false;

	/** function key -> action 対応付けtable. */
	private Map<Integer, JButton[]> functionKeyToActionMap = new HashMap<Integer, JButton[]>();

	/** 直前にアクセスされたコンポーネント */
	private KeyEvent keyEventBefore = null;

	/** VerifierEnabled */
	private boolean isVerifierEnabled = true;

	/** 親コンポーネント */
	private Component parent;

	/** モーダルブロックフラグ */
	private boolean blocking;

	/**
	 * コンストラクタ.
	 */
	public TFrame() {
		this(null, "");
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param titleID タイトル
	 */
	public TFrame(String titleID) {
		this(null, titleID);
	}

	/**
	 * コンストラクタ(モーダルモード)
	 * 
	 * @param cont 呼び出し元コンテナ
	 */
	public TFrame(Container cont) {
		this(cont, "");
	}

	/**
	 * コンストラクタ(モーダルモード)
	 * 
	 * @param cont 呼び出し元コンテナ
	 * @param titleID タイトル
	 */
	public TFrame(Container cont, String titleID) {
		super();

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		this.setTitle(getWord(titleID));

		this.init();

		if (cont != null) {
			this.parent = TGuiUtil.getParentFrameOrDialog2(cont);

			if (parent == null) {
				// 親ウィンドウが存在しません。
				throw new IllegalArgumentException(getMessage("I00252"));
			}
		}

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				modalEnd();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				modalEnd();
			}
		});
	}

	/**
	 * component初期化
	 */
	private void init() {
		functionKeyToActionMap.put(TGuiUtil.WITH_NO_KEY, new JButton[FUNCTION_KEY_NUM]);
		functionKeyToActionMap.put(TGuiUtil.WITH_CTRL_KEY, new JButton[FUNCTION_KEY_NUM]);
		functionKeyToActionMap.put(TGuiUtil.WITH_SHIFT_KEY, new JButton[FUNCTION_KEY_NUM]);
		functionKeyToActionMap.put(TGuiUtil.WITH_ALT_KEY, new JButton[FUNCTION_KEY_NUM]);

		this.initListener();
	}

	/**
	 * Listenerの初期設定. <br>
	 * ファンクションキーの取得のための初期化。<br>
	 * KeyListenerを設定する。<br>
	 * Dialogを閉じるときに、入力エラーを表示させないために WindowListenerを設定する。
	 */
	private void initListener() {

		this.addWindowListener(new TWindowListener(null));

		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				paneKeyPressed(evt);
			}
		});
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

		if (this.functionKeyToActionMap == null) {
			return null;
		}

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

		if (this.functionKeyToActionMap == null) {
			return;
		}

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
	 * DialogのUI初期化を完結させる. <br>
	 * メッセージの変換表示。 タブ順の設定。 メッセージIDの設定、タブ順番号の設定が完了したら即このmethodを呼び出す。
	 */
	protected void initFrame() {

		// コンポーネントの復元
		restoreComponent();

		// メッセージの変換.

		// messageの設定
		this.translateLangMessageID();

		// ショートカットキー登録(ボタン登録分の追加)
		this.registShortcutKey();

		// tab-key順の設定.

		// Tab順の収集
		this.collectTabControlNo();

		// Tab順の登録
		TGuiUtil.changeTabControlNo(listTabControlNo, listTabComponent);

		// Tab順の反映
		// this.setFocusTraversalPolicy(this.focusPolicy);
		this.focusPolicy = new TFocusPolicy(this.listTabComponent);
		this.focusPolicyEnter = new TFocusPolicyEnter(this.listTabComponent);

		this.setTabPolicy();
	}

	/**
	 * タブ移動の設定。<br>
	 * appletからのcontainer階層構築後に呼び出す。
	 */
	public void setTabPolicy() {
		this.focusPolicy = new TFocusPolicy(this.listTabComponent);
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

	// interface 実装

	/**
	 * message IDの設定
	 * 
	 * @param messageID メッセージID
	 */
	public void setLangMessageID(String messageID) {
		this.messageID = messageID;

		this.setTitle(getWord(messageID));
	}

	/**
	 * message IDの取得
	 * 
	 * @return messageID
	 */
	public String getLangMessageID() {
		return this.messageID;
	}

	// フォーカス移動の自由度を上げるためmethod 追加

	/**
	 * 直前にアクセスされたコンポーネント setter.
	 * 
	 * @param evt
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
	 * 配下コンポーネントのVerifier有効／無効
	 * 
	 * @param flag true:有効
	 */
	public void setVerifierEnabled(boolean flag) {
		TGuiUtil.setComponentsVerifierEnabled(this, flag);
		this.isVerifierEnabled = flag;
	}

	/**
	 * 配下コンポーネントのVerifier有効／無効
	 * 
	 * @return true:有効
	 */
	public boolean isVerifierEnabled() {
		return this.isVerifierEnabled;
	}

	/**
	 * ログインユーザの言語コードに対する、メッセージを返す. 指定単語ID、または単語をバインド.
	 * 
	 * @param messageId メッセージID
	 * @param bindIds 単語ID、または、単語のリスト
	 * @return 変換されたメッセージ
	 */
	protected String getMessage(String messageId, Object... bindIds) {

		return MessageUtil.convertMessage(getLoginLanguage(), messageId, bindIds);
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
	 * Visible:falseの場合、dispose()を行ってメモリ開放する.
	 * 
	 * @see java.awt.Component#setVisible(boolean)
	 */
	@Override
	public void setVisible(final boolean b) {
		super.setVisible(b);

		if (parent != null) {
			if (isVisible()) {
				modalStart();
			} else {
				modalEnd();
			}
		}
	}

	/**
	 * モーダル開始
	 */
	private void modalStart() {
		if (parent == null) return;

		parent.setEnabled(false);

		if (parent instanceof Window) {
			((Window) parent).setFocusableWindowState(false);
		}

		blocking = true;
	}

	/**
	 * モーダル終了
	 */
	private void modalEnd() {
		if (parent == null || !blocking) return;

		blocking = false;

		if (parent instanceof Window) {
			((Window) parent).setFocusableWindowState(true);
		}

		parent.setEnabled(true);
		parent.setVisible(true);
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
		 * 
		 * @param evt
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
	 * コンポーネントを復元する
	 */
	protected void restoreComponent() {

		try {
			Class c = this.getClass();
			Field[] fields = c.getFields();

			for (Field field : fields) {
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

		} catch (Exception e) {
			ClientErrorHandler.handledException(e);
		}
	}

	/**
	 * デフォルトカーソルを設定
	 */
	public void setDefaultCursor() {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * WAITカーソルを設定
	 */
	public void setWaitCursor() {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
}
