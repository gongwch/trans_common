package jp.co.ais.trans.common.gui.dnd;

import java.awt.datatransfer.*;
import java.util.*;

/**
 * Transferable�����N���X
 */
public class TOrderedTransferable implements Transferable {

	/** �]���f�[�^ */
	List<OrderedData> dataList;

	/** DataFlavor ���� */
	private static final String NAME = "TOrderedTransferable";

	/** TOrderedTransferable��DataFlavor */
	public static final DataFlavor orderedFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, NAME);

	/** TOrderedTransferable�ŃT�|�[�g���Ă���DataFlavor */
	private static final DataFlavor[] supportedFlavors = { orderedFlavor };

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param list
	 */
	public TOrderedTransferable(List<OrderedData> list) {
		super();
		dataList = list;
	}

	/**
	 * �]���f�[�^���擾����
	 */
	public Object getTransferData(DataFlavor df) {
		if (isDataFlavorSupported(df)) {
			return dataList;
		} else {
			return null;
		}
	}

	/**
	 * �Ώۂ̌^���T�|�[�g���Ă��邩�ǂ����m�F����
	 */
	public boolean isDataFlavorSupported(DataFlavor df) {
		return (df.equals(orderedFlavor));
	}

	/**
	 * @�T�|�[�g���Ă���^��n��
	 */
	public DataFlavor[] getTransferDataFlavors() {
		return supportedFlavors;
	}

}
