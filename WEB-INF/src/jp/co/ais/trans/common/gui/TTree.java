package jp.co.ais.trans.common.gui;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;

import jp.co.ais.trans2.common.config.*;

/**
 * JTreeに、タブ順インターフェイスを追加したTreeBox.
 */
public class TTree extends JTree implements TInterfaceTabControl {

	/** シリアルUID */
	protected static final long serialVersionUID = 5130879826202293920L;

	/** タブ */
	protected int tabControlNo = -1;

	/** タブ使用可 */
	protected boolean isTabEnabled = true;

	/**
	 * Constructor.
	 */
	public TTree() {
		super();

		init();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param node TreeNode
	 */
	public TTree(TreeNode node) {
		super(node);

		init();
	}

	/**
	 * 初期化処理
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

	/************************************************
	 * ファンクションキー処理.
	 * 
	 * @param evt キーイベント
	 ***********************************************/
	protected void handleKeyPressed(KeyEvent evt) {

		TGuiUtil.transferFocusByEnterKey(this, evt);
		TGuiUtil.dispatchPanelBusinessFunctionKey(this, evt);
	}

	/**
	 * @see javax.swing.JTree#getCellRenderer()
	 */
	@Override
	public TreeCellRenderer getCellRenderer() {
		if (ClientConfig.isFlagOn("trans.tree.view.icons.use.circle")
			|| ClientConfig.isFlagOn("trans.tree.view.icons.use.rect")) {
			return new TTreeCellRenderer();
		} else {
			return super.getCellRenderer();
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
	 * 
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
	 * ノードを全て展開する
	 */
	public void expandAll() {

		int row = 0;
		while (row < getRowCount()) {
			expandRow(row);
			row++;
		}
	}

	/**
	 * ノード追加等の変更をUIに反映する
	 */
	public void reload() {
		DefaultTreeModel model = (DefaultTreeModel) getModel();
		model.reload();
	}

	/**
	 * 指定範囲のパスを取得する
	 * 
	 * @param from 開始
	 * @param to 終了
	 * @return 指定範囲のパス
	 */
	@Override
	public TreePath[] getPathBetweenRows(int from, int to) {
		return super.getPathBetweenRows(from, to);
	}

	/**
	 * 全てのパスを取得する
	 * 
	 * @return 指定範囲のパス
	 */
	public TreePath[] getAllPaths() {

		int from = 0;
		int to = getRowCount();

		if (to == 0) {
			return null;
		}

		return getPathBetweenRows(from, to);
	}

}
