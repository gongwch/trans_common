package jp.co.ais.trans.common.gui.dnd;

import javax.swing.tree.*;

/**
 * DefaultMutableTreeNodeクラスを継承して作成。<br>
 * Drag＆Drop時、THierarchyTreeNodeを転送するために使用する。
 * 
 * @author Yanwei
 */
public class THierarchyTreeNode extends DefaultMutableTreeNode {

	/** ノード情報 */
	private DnDData dndData;

	/**
	 * コンストラクタ
	 * 
	 * @param dData ノード情報
	 */
	public THierarchyTreeNode(DnDData dData) {

		// ①継承元のコンストラクタを呼ぶ。
		super(dData.getViewName());

		// ②引数のDnDDataをプロパティにセットする。
		this.dndData = dData;
	}

	/**
	 * ノード情報
	 * 
	 * @return ノード情報
	 */
	public DnDData getDndData() {
		return dndData;
	}

	/**
	 * ノード情報
	 * 
	 * @param dndData ノード情報設定する
	 */
	public void setDndData(DnDData dndData) {
		this.dndData = dndData;
	}

}