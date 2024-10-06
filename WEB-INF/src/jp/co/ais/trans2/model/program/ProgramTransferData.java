package jp.co.ais.trans2.model.program;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 渡すプログラムノードデータ
 */
public class ProgramTransferData extends TransferBase {

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

	/** プログラムノードリスト */
	protected List<ProgramTreeNode> nodeList = null;

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
	 * プログラムノードリストの取得
	 * 
	 * @return nodeList プログラムノードリスト
	 */
	public List<ProgramTreeNode> getNodeList() {
		return nodeList;
	}

	/**
	 * プログラムノードリストの設定
	 * 
	 * @param nodeList プログラムノードリスト
	 */
	public void setNodeList(List<ProgramTreeNode> nodeList) {
		this.nodeList = nodeList;
	}

}
