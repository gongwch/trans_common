package jp.co.ais.trans.common.gui;

import java.awt.datatransfer.*;

import javax.swing.*;

/**
 * 文字列 転送処理クラス <br>
 * Drag and Drop, Copy(Cut) and Paste対応
 */
abstract class TStringTransferHandler extends TransferHandler {

	/**
	 * 文字列取出し
	 * 
	 * @param c
	 * @return 文字列
	 */
	protected abstract String exportString(JComponent c);

	/**
	 * 文字列読込み
	 * 
	 * @param c
	 * @param str
	 */
	protected abstract void importString(JComponent c, String str);

	/**
	 * クリア
	 * 
	 * @param c
	 * @param remove
	 */
	protected abstract void cleanup(JComponent c, boolean remove);

	/**
	 * @see TransferHandler#createTransferable
	 */
	@Override
	protected Transferable createTransferable(JComponent c) {
		return new StringSelection(exportString(c));
	}

	/**
	 * @see TransferHandler#getSourceActions
	 */
	@Override
	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}

	/**
	 * @see TransferHandler#importData(JComponent,Transferable )
	 */
	@Override
	public boolean importData(JComponent c, Transferable t) {
		if (canImport(c, t.getTransferDataFlavors())) {
			try {
				String str = (String) t.getTransferData(DataFlavor.stringFlavor);
				importString(c, str);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * @see TransferHandler#exportDone
	 */
	@Override
	protected void exportDone(JComponent c, Transferable data, int action) {
		cleanup(c, action == MOVE);
	}

	/**
	 * @see TransferHandler#exportDone
	 */
	@Override
	public boolean canImport(JComponent c, DataFlavor[] flavors) {
		for (int i = 0; i < flavors.length; i++) {
			if (DataFlavor.stringFlavor.equals(flavors[i])) {
				return true;
			}
		}
		return false;
	}
}
