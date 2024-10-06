package jp.co.ais.trans2.common.gui.dnd;

import java.awt.datatransfer.*;

/**
 * Object���M�pTransferable
 */
public class ObjectTransferable implements Transferable {

	/** �m�[�h����pDataFlavor */
	public static final DataFlavor FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, "Object");

	/** �]���f�[�^ */
	public Object transferData;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param transferData
	 */
	public ObjectTransferable(Object transferData) {
		this.transferData = transferData;
	}

	/**
	 * �]���f�[�^���擾����
	 */
	public Object getTransferData(DataFlavor df) {
		return (isDataFlavorSupported(df)) ? transferData : null;
	}

	/**
	 * �����\��DataFlavor�����f����
	 */
	public boolean isDataFlavorSupported(DataFlavor df) {
		return (df.equals(FLAVOR));
	}

	/**
	 * �T�|�[�g���Ă���^��n��
	 */
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { FLAVOR };
	}
}