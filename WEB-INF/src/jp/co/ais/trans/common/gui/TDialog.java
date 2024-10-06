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

/**
 * JDialogを継承し、messageIDインターフェースを実装したDialog.
 */
public class TDialog extends JDialog implements TInterfaceLangMessageID, TInterfaceTabPolicy {

	/** function key max no. */
	public static final int FUNCTION_KEY_NUM = 15;

	/**  */
	private String messageID = null;

	/** tab番号. */
	private List<TNameComponent> listTabControlNo = new ArrayList<TNameComponent>();

	/** tab順component */
	private List<Component> listTabComponent = new ArrayList<Component>();

	/** focus policy */
	private TFocusPolicy focusPolicy = null;

	/**  */
	private TFocusPolicyEnter focusPolicyEnter = null;

	/**  */
	private boolean modeEnter = false;

	/**  */
	private Container parent = null;

	/** function key -> action 対応付けtable. */
	private Map<Integer, JButton[]> functionKeyToActionMap = new HashMap<Integer, JButton[]>();

	/** 直前にアクセスされたコンポーネント */
	private KeyEvent keyEventBefore = null;

	/**  */
	private boolean isVerifierEnabled = true;

	/**
	 * コンストラクタ
	 */
	public TDialog() {
		super();
		this.init();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param frame 親フレーム
	 * @param modal モーダルフラグ
	 */
	public TDialog(Frame frame, boolean modal) {
		super(frame, modal);
		this.parent = null;
		this.init();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param dialog 親ダイアログ
	 * @param modal モーダルフラグ
	 */
	public TDialog(Dialog dialog, boolean modal) {
		super(dialog, modal);
		this.parent = dialog;
		this.init();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param win 親ダイアログ
	 */
	public TDialog(Window win) {
		super(win);
		this.parent = win;
		this.init();
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
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setResizable(false);
		super.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
	}

	/**
	 * Listenerの初期設定. <br>
	 * ファンクションキーの取得のための初期化。<br>
	 * KeyListenerを設定する。<br>
	 * Dialogを閉じるときに、入力エラーを表示させないために WindowListenerを設定する。
	 */
	private void initListener() {

		this.addWindowListener(new TWindowListener((Window) this.parent));

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
			// this.handleFunctionKey(fkey);
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
	 * ボタン自体に登録すること<br>
	 * <s> ファンクションキーとボタンの対応付けテーブルを登録する。<br>
	 * ファンクションキーが押下されたとき、tbl[FUNCTION_KEY_NUM]で登録されたボタンのdoClick()を呼び出す.<br>
	 * F[n]が押下されたとき、tbl[n-1].doClick()が実行される。<br>
	 * 対応するボタンがないときはnullを設定する. </s>
	 * 
	 * @deprecated ボタン自体に登録すること
	 * @param tbl [TPanelBusiness.FUNCTION_KEY_NUM]
	 */
	public void setFunctionKeyToActionTable(JButton[] tbl) {

		this.setFunctionKeyToActionTable(tbl, TGuiUtil.WITH_NO_KEY);
	}

	/**
	 * ボタン自体に登録すること<br>
	 * <s> ファンクションキーとボタンと同時押しキーの対応付けテーブルを登録する。<br>
	 * ファンクションキーが押下されたとき、tbl[FUNCTION_KEY_NUM]で登録されたボタンのdoClick()を呼び出す.<br>
	 * F[n]が押下されたとき、tbl[n-1].doClick()が実行される。<br>
	 * 対応するボタンがないときはnullを設定する. </s>
	 * 
	 * @deprecated ボタン自体に登録すること
	 * @param tbl [TPanelBusiness.FUNCTION_KEY_NUM]
	 * @param mkey WITH_SHIFT_KEY | WITH_CTRL_KEY | WIDTH_ALT_KEYの組み合わせ
	 */
	public void setFunctionKeyToActionTable(JButton[] tbl, int mkey) {

		this.functionKeyToActionMap.put(mkey, tbl);
	}

	/**
	 * 親フレーム検索.
	 * 
	 * @return 親フレーム
	 */
	public Frame getParentFrame() {
		for (Container p = this.getParent(); p != null; p = p.getParent()) {
			if (p instanceof Frame) {
				return (Frame) p;
			}
		}
		return null;
	}

	/**
	 * 親ダイアログ検索.
	 * 
	 * @return 親ダイアログ
	 */
	public Dialog getParentDialog() {
		for (Container p = this.getParent(); p != null; p = p.getParent()) {
			if (p instanceof Dialog) {
				return (Dialog) p;
			}
		}
		return null;
	}

	/**
	 * DialogのUI初期化を完結させる. <br>
	 * メッセージの変換表示。 タブ順の設定。 メッセージIDの設定、タブ順番号の設定が完了したら即このmethodを呼び出す。
	 */
	protected void initDialog() {

		// コンポーネントの復元
		restoreComponent();

		// メッセージの変換.
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

	/**
	 * interface 実装
	 */

	/**
	 * message IDの設定
	 * 
	 * @param messageID メッセージID
	 */
	public void setLangMessageID(String messageID) {
		this.messageID = messageID;
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
	public void setVisible(boolean b) {
		super.setVisible(b);

		if (!isVisible()) {
			this.dispose();
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
	 * メッセージ(略称)取得
	 * 
	 * @param messageId メッセージID
	 * @return 変換後のメッセージ
	 */
	public String getShortWord(String messageId) {
		return MessageUtil.getShortWord(getLoginLanguage(), messageId);
	}

	/**
	 * @return listTabControlNo
	 */
	public List<TNameComponent> getListTabControlNo() {
		return this.listTabControlNo;
	}

	/**
	 * @return listTabComponent
	 */
	public List<Component> getListTabComponent() {
		return this.listTabComponent;
	}
}
