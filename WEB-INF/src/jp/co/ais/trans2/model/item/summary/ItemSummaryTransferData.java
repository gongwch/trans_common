package jp.co.ais.trans2.model.item.summary;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 渡す科目ノードデータ
 */
public class ItemSummaryTransferData extends TransferBase {

	/**
	 * ツリーorスプレッドからのフラグ
	 */
	public enum SourceType {
		/** ツリー */
		TREE,
		/** スプレッド */
		TABLE,
	}

	/** ツリーorスプレッドからのフラグ */
	protected SourceType source = null;

	/** 科目ノードリスト */
	protected List<ItemSummaryTreeNode> nodeList = null;

	/**
	 * ツリーorスプレッドからのフラグの取得
	 * 
	 * @return source ツリーorスプレッドからのフラグ
	 */
	public SourceType getSource() {
		return source;
	}

	/**
	 * ツリーorスプレッドからのフラグの設定
	 * 
	 * @param source ツリーorスプレッドからのフラグ
	 */
	public void setSource(SourceType source) {
		this.source = source;
	}

	/**
	 * 科目ノードリストの取得
	 * 
	 * @return nodeList 科目ノードリスト
	 */
	public List<ItemSummaryTreeNode> getNodeList() {
		return nodeList;
	}

	/**
	 * 科目ノードリストの設定
	 * 
	 * @param nodeList 科目ノードリスト
	 */
	public void setNodeList(List<ItemSummaryTreeNode> nodeList) {
		this.nodeList = nodeList;
	}

}
