package jp.co.ais.trans.common.gui.dnd;

import java.awt.datatransfer.*;
import java.util.*;

/**
 * Transferable実現クラス
 */
public class TOrderedTransferable implements Transferable {

	/** 転送データ */
	List<OrderedData> dataList;

	/** DataFlavor 名称 */
	private static final String NAME = "TOrderedTransferable";

	/** TOrderedTransferableのDataFlavor */
	public static final DataFlavor orderedFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, NAME);

	/** TOrderedTransferableでサポートしているDataFlavor */
	private static final DataFlavor[] supportedFlavors = { orderedFlavor };

	/**
	 * コンストラクタ.
	 * 
	 * @param list
	 */
	public TOrderedTransferable(List<OrderedData> list) {
		super();
		dataList = list;
	}

	/**
	 * 転送データを取得する
	 */
	public Object getTransferData(DataFlavor df) {
		if (isDataFlavorSupported(df)) {
			return dataList;
		} else {
			return null;
		}
	}

	/**
	 * 対象の型をサポートしているかどうか確認する
	 */
	public boolean isDataFlavorSupported(DataFlavor df) {
		return (df.equals(orderedFlavor));
	}

	/**
	 * @サポートしている型を渡す
	 */
	public DataFlavor[] getTransferDataFlavors() {
		return supportedFlavors;
	}

}
