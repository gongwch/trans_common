package jp.co.ais.trans.common.gui.dnd;

import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.io.*;
import java.util.*;

/**
 * TTransferableクラスを継承して作成。<br>
 * Drag＆Drop時、THierarchyTreeNodeを転送するために使用する。
 * 
 * @author Yanwei
 */
public class TTransferable implements Transferable {

	/** ノード判定用DataFlavor 名称 */
	public static final String FLAVOR_NAME = "THierarchyTreeNode";

	/** ノード判定用DataFlavor */
	public static final DataFlavor nodeFavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, FLAVOR_NAME);

	/** TTransferableでサポートしているDataFlavor */
	private static final DataFlavor[] supportedFlavors = { nodeFavor };

	/** 転送データ */
	public List<THierarchyTreeNode> transferDataList;

	/**
	 * コンストラクタ Tree→Table、Tree→Tree用
	 * 
	 * @param transferData
	 */
	public TTransferable(THierarchyTreeNode transferData) {

		List<THierarchyTreeNode> list = new ArrayList<THierarchyTreeNode>();
		list.add(transferData);

		// TTransferable(list);
		this.transferDataList = list;

	}

	/**
	 * コンストラクタ Tree→Table、Tree→Tree用
	 * 
	 * @param list
	 */
	public TTransferable(List list) {
		if (list == null || list.isEmpty()) {
			return;
		}

		Object obj = list.get(0);
		// List<THierarchyTreeNode>の場合,引数.listをプロパティ.transferDataListにセットする
		if (obj instanceof THierarchyTreeNode) {
			this.transferDataList = list;

		}
		// List<DnDData>の場合,引数.list数分のTHierarchyTreeNodeを作成し、プロパティ.transferDataListにセットする
		else if (obj instanceof DnDData) {
			List<THierarchyTreeNode> tmpList = new ArrayList<THierarchyTreeNode>();
			for (int i = 0; i < list.size(); i++) {

				DnDData dnDData = (DnDData) list.get(i);
				THierarchyTreeNode tHierarchyTreeNode = new THierarchyTreeNode(dnDData);
				tmpList.add(tHierarchyTreeNode);
			}
			this.transferDataList = tmpList;
		}
	}

	/**
	 * オーバーライドメソッド 転送データを取得する
	 */
	public Object getTransferData(DataFlavor df) {
		if (isDataFlavorSupported(df)) {
			return transferDataList;
		} else {
			return null;
		}
	}

	/**
	 * オーバーライドメソッド 処理可能なDataFlavorか判断する
	 */
	public boolean isDataFlavorSupported(DataFlavor df) {
		return (df.equals(nodeFavor));
	}

	/**
	 * 転送データをノード形式で取得する
	 * 
	 * @param transferable
	 * @return 転送データ
	 */
	public static List<THierarchyTreeNode> getTHerarchyTreeNodeList(Transferable transferable) {
		try {
			return (List<THierarchyTreeNode>) transferable.getTransferData(nodeFavor);

		} catch (UnsupportedFlavorException e) {
			// 処理なし
		} catch (IOException e) {
			// 処理なし
		} catch (InvalidDnDOperationException e) {
			// 処理なし
		}

		return null;
	}

	/**
	 * 転送データをDnDData形式で取得する
	 * 
	 * @param transferable
	 * @return List<DnDData>TTｒeeの階層データ受け渡しに使用するクラス
	 */
	public static List<DnDData> getDnDDataList(Transferable transferable) {
		try {
			List<DnDData> listDnDData = new ArrayList<DnDData>();

			// 転送データ取得
			Object obj = transferable.getTransferData(nodeFavor);

			// ① 引数のTransferableで保持しているデータを取得し、Object ⇒ List<THerarchyTreeNode>に変換して返す。
			List<THierarchyTreeNode> list = (List<THierarchyTreeNode>) obj;
			if (list == null || list.isEmpty()) {
				return null;
			}

			// ② ①の各THierarchyTreeNodeで保持しているDnDDataを取得する。
			for (THierarchyTreeNode treeNode : list) {
				DnDData dndData = new DnDData();
				dndData.setCode(treeNode.getDndData().getCode());
				dndData.setName(treeNode.getDndData().getName());
				dndData.setTopCode("");// ③ DnDDataの上位階層コードをクリアする

				listDnDData.add(dndData);//
			}

			// ④ DnDDataをリスト形式にして返す
			return listDnDData;

		} catch (UnsupportedFlavorException e) {
			// 処理なし
		} catch (IOException e) {
			// 処理なし
		} catch (InvalidDnDOperationException e) {
			// 処理なし
		}

		return null;
	}

	/**
	 * @サポートしている型を渡す
	 */
	public DataFlavor[] getTransferDataFlavors() {
		return supportedFlavors;
	}

}