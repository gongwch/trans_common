package jp.co.ais.trans2.model.department;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 渡す部門ノードデータ
 */
public class DepartmentTransferData extends TransferBase {

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

	/** 部門ノードリスト */
	protected List<DepartmentTreeNode> nodeList = null;

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
	 * 部門ノードリストの取得
	 * 
	 * @return nodeList 部門ノードリスト
	 */
	public List<DepartmentTreeNode> getNodeList() {
		return nodeList;
	}

	/**
	 * 部門ノードリストの設定
	 * 
	 * @param nodeList 部門ノードリスト
	 */
	public void setNodeList(List<DepartmentTreeNode> nodeList) {
		this.nodeList = nodeList;
	}

}
