package jp.co.ais.trans2.common.gui.tree;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;

import jp.co.ais.trans.common.gui.*;

/**
 * ツールチップ表示版ツリー
 */
public class TToolTipTree extends TTree {

	/**
	 * コンストラクター
	 */
	public TToolTipTree() {
		super();

		// 初期化
		init();
	}

	/**
	 * コンストラクター
	 * 
	 * @param node
	 */
	public TToolTipTree(TreeNode node) {
		super(node);

		// 初期化
		init();
	}

	/**
	 * 初期化
	 */
	protected void init() {
		// レジスト
		ToolTipManager.sharedInstance().registerComponent(this);

		// 複数選択不可
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}

	/**
	 * @see javax.swing.JTree#getToolTipText(java.awt.event.MouseEvent)
	 */
	@Override
	public String getToolTipText(MouseEvent e) {

		TreePath path = getPathForLocation(e.getX(), e.getY());
		if (path == null || path.getLastPathComponent() == null
			|| !(path.getLastPathComponent() instanceof TMenuTreeNode)) {
			return null;
		}

		TMenuTreeNode node = (TMenuTreeNode) path.getLastPathComponent();
		return node.getToolTipText();
	}

}
