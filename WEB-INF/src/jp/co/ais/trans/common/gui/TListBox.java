package jp.co.ais.trans.common.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * JListに、タブ順インターフェイスを追加したListBox.
 */
public class TListBox extends JList implements TInterfaceTabControl {

	private int tabControlNo = -1;

	private boolean isTabEnabled = true;

	/**
	 * Constructor.
	 */
	public TListBox() {
		super();

		init();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param model モデル
	 */
	public TListBox(ListModel model) {
		super(model);

		init();
	}

	/**
	 * 初期処理
	 */
	protected void init() {

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

		TGuiUtil.transferFocusByEnterKey(this, evt);
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
}
