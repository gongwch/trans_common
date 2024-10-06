package jp.co.ais.trans2.common.gui.tree;

import javax.swing.tree.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.program.*;

/**
 * 
 */
public class TMenuTreeNode extends DefaultMutableTreeNode {

	/** ツリー項目 */
	protected TTreeItem item = null;

	/** プログラム情報 */
	protected SystemizedProgram prgGroup = null;

	/**
	 * コンストラクター
	 */
	public TMenuTreeNode() {
		super();
	}

	/**
	 * コンストラクター
	 * 
	 * @param item ツリー項目
	 * @param flag 子ツリー追加できるかどうか
	 */
	public TMenuTreeNode(TTreeItem item, boolean flag) {
		super(item, flag);

		this.item = item;
	}

	/**
	 * コンストラクター
	 * 
	 * @param item ツリー項目
	 */
	public TMenuTreeNode(TTreeItem item) {
		super(item);

		this.item = item;
	}

	/**
	 * ツールチップの取得
	 * 
	 * @return ツールチップ
	 */
	public String getToolTipText() {

		if (this.item == null || !(item.getObj() instanceof MenuDisp)) {
			return null;
		}

		MenuDisp menu = (MenuDisp) item.getObj();
		if (menu == null) {
			return null;
		}

		Program prg = menu.getProgram();
		if (prg == null || Util.isNullOrEmpty(prg.getComment())) {
			return menu.getViewName();
		}

		return prg.getComment();
	}

	/**
	 * ツリー項目の取得
	 * 
	 * @return item ツリー項目
	 */
	public TTreeItem getItem() {
		return item;
	}

	/**
	 * ツリー項目の設定
	 * 
	 * @param item ツリー項目
	 */
	public void setItem(TTreeItem item) {
		this.item = item;
	}

	/**
	 * プログラム情報の取得
	 * 
	 * @return prgGroup プログラム情報
	 */
	public SystemizedProgram getPrgGroup() {
		return prgGroup;
	}

	/**
	 * プログラム情報の設定
	 * 
	 * @param prgGroup プログラム情報
	 */
	public void setPrgGroup(SystemizedProgram prgGroup) {
		this.prgGroup = prgGroup;
	}

}
