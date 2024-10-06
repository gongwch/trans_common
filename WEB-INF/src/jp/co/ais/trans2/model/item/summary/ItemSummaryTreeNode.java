package jp.co.ais.trans2.model.item.summary;

import javax.swing.tree.*;

/**
 * DefaultMutableTreeNodeクラスを継承して作成。<br>
 * Drag＆Drop時、ItemSumTreeNodeを転送するために使用する。
 * 
 * @author Yanwei
 */
public class ItemSummaryTreeNode extends DefaultMutableTreeNode {

	/** ノード情報 */
	protected ItemSummaryDisp itemSummaryDisp;

	/**
	 * コンストラクタ
	 * 
	 * @param itemSummaryDisp ノード情報
	 */
	public ItemSummaryTreeNode(ItemSummaryDisp itemSummaryDisp) {

		// ①継承元のコンストラクタを呼ぶ。
		super(getName(itemSummaryDisp));

		// ②引数のDnDDataをプロパティにセットする。
		this.itemSummaryDisp = itemSummaryDisp;
	}

	/**
	 * 科目集計名の取得
	 * 
	 * @param itemSummaryDisp
	 * @return 科目集計名
	 */
	public static String getName(ItemSummaryDisp itemSummaryDisp) {

		return itemSummaryDisp.getViewName();

	}

	/**
	 * ノード情報
	 * 
	 * @return ノード情報
	 */
	public ItemSummaryDisp getItemSummaryDisp() {
		return itemSummaryDisp;
	}

	/**
	 * ノード情報
	 * 
	 * @param itemSummaryDisp ノード情報設定する
	 */
	public void setItemSummaryDisp(ItemSummaryDisp itemSummaryDisp) {
		this.itemSummaryDisp = itemSummaryDisp;
	}

}