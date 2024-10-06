package jp.co.ais.trans.common.gui;

import javax.swing.tree.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;

/**
 * ツリー表現
 */
public class TTreeCellRenderer extends DefaultTreeCellRenderer {

	/**
	 * コンストラクター
	 */
	public TTreeCellRenderer() {
		init();
	}

	/**
	 * 初期化
	 */
	protected void init() {
		if (ClientConfig.isFlagOn("trans.tree.view.icons.use.circle")) {
			setOpenIcon(ResourceUtil.getImage(this.getClass(), "images/tree_circle_open.png"));
			setClosedIcon(ResourceUtil.getImage(this.getClass(), "images/tree_circle_close.png"));
			setLeafIcon(ResourceUtil.getImage(this.getClass(), "images/tree_circle_leaf.png"));
		} else if (ClientConfig.isFlagOn("trans.tree.view.icons.use.rect")) {
			setOpenIcon(ResourceUtil.getImage(this.getClass(), "images/tree_rect_open.png"));
			setClosedIcon(ResourceUtil.getImage(this.getClass(), "images/tree_rect_close.png"));
			setLeafIcon(ResourceUtil.getImage(this.getClass(), "images/tree_rect_leaf.png"));
		}
	}
}
