package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;

/**
 * タブペイン
 */
@Deprecated
public class TTabbedPane extends JTabbedPane implements TInterfaceLangMessageID, TInterfaceTabControl {

	/** タイトルID(リスト形式で保存) */
	private String langMessageID = null;

	/** タブ順 */
	private int tabControlNo = -1;

	/** タブ移動可・付加 */
	private boolean isTabEnabled = true;

	/**
	 * コンストラクタ
	 * 
	 * @see javax.swing.JTabbedPane#JTabbedPane()
	 */
	public TTabbedPane() {
		super();

		this.init();
	}

	/**
	 * コンストラクタ
	 * 
	 * @see javax.swing.JTabbedPane#JTabbedPane(int,int)
	 * @param tabPlacement
	 * @param tabLayoutPolicy
	 */
	public TTabbedPane(int tabPlacement, int tabLayoutPolicy) {
		super(tabPlacement, tabLayoutPolicy);

		this.init();
	}

	/**
	 * コンストラクタ
	 * 
	 * @see javax.swing.JTabbedPane#JTabbedPane(int)
	 * @param tabPlacement
	 */
	public TTabbedPane(int tabPlacement) {
		super(tabPlacement);

		this.init();
	}

	/**
	 * コンストラクタ.<br>
	 * 指定されたメッセージID分のTPanelを追加する.<br>
	 * これを利用した場合、タブにコンポーネントを配置する際は、<br>
	 * getComponent(index)で自動的に追加されたTPanelを取得して配置することとなる。 <br>
	 * <br>
	 * 例) <br>
	 * TTabbedPane tabPane = new TTabbedPane(new String[] { &quot;C00001&quot;, &quot;C00002&quot;, &quot;C00003&quot;
	 * }); <br>
	 * TPanel pnlBase = (TPanel) tabPane.getComponent(0);
	 * 
	 * @param langMessageIDs メッセージIDリスト
	 */
	public TTabbedPane(String[] langMessageIDs) {
		super();

		for (int i = 0; i < langMessageIDs.length; i++) {
			add(langMessageIDs[i], new TPanel());
		}

		setLangMessageIDs(langMessageIDs);

		this.init();
	}

	/**
	 * 初期化処理
	 */
	private void init() {

		// ファンクションキー用リスナ
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				handleKeyPressed(evt);
			}
		});
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
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#getLangMessageID()
	 */
	public String getLangMessageID() {
		return this.langMessageID;
	}

	/**
	 * @deprecated setLangMessageIDs(String[])を利用
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#setLangMessageID(String)
	 */
	public void setLangMessageID(String langMessageID) {
		this.langMessageID = langMessageID;
	}

	/**
	 * タブタイトルの単語IDリストを設定
	 * 
	 * @param langMessageIDs メッセージIDリスト
	 */
	public void setLangMessageIDs(String[] langMessageIDs) {
		this.langMessageID = StringUtil.toDelimitString(langMessageIDs);
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
	 * InputVerifier対応
	 * 
	 * @see javax.swing.JTabbedPane#setSelectedIndex(int)
	 */
	@Override
	public void setSelectedIndex(int index) {
		Component comp = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

		// if no tabs are selected
		// -OR- the current focus owner is me
		// -OR- I request focus from another component and get it
		// then proceed with the tab switch
		if (getSelectedIndex() == -1 || comp == this || requestFocus(false)) {
			super.setSelectedIndex(index);
		}
	}

	/**
	 * 一つ目タブを選択する
	 */
	public void selectFirstTab() {
		super.setSelectedIndex(0);
	}

}
