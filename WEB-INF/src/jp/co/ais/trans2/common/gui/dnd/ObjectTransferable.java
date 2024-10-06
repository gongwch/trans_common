package jp.co.ais.trans2.common.gui.dnd;

import java.awt.datatransfer.*;

/**
 * Object送信用Transferable
 */
public class ObjectTransferable implements Transferable {

	/** ノード判定用DataFlavor */
	public static final DataFlavor FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, "Object");

	/** 転送データ */
	public Object transferData;

	/**
	 * コンストラクタ
	 * 
	 * @param transferData
	 */
	public ObjectTransferable(Object transferData) {
		this.transferData = transferData;
	}

	/**
	 * 転送データを取得する
	 */
	public Object getTransferData(DataFlavor df) {
		return (isDataFlavorSupported(df)) ? transferData : null;
	}

	/**
	 * 処理可能なDataFlavorか判断する
	 */
	public boolean isDataFlavorSupported(DataFlavor df) {
		return (df.equals(FLAVOR));
	}

	/**
	 * サポートしている型を渡す
	 */
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { FLAVOR };
	}
}