package jp.co.ais.trans2.common.gui.dnd;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * Objectドラッグ＆ドロップ用リスト
 */
public class TDnDList extends TListBox implements DragSourceListener, DragGestureListener, DropTargetListener {

	/** リスナー */
	private TDnDListener listener;

	/**
	 * コンストラクタ.
	 */
	public TDnDList() {
		this(new DefaultListModel());
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param model モデル
	 */
	public TDnDList(ListModel model) {
		super(model);

		// // マウスクリックしっぱなし制御
		// addMouseListener(new MouseAdapter() {
		//
		// public void mousePressed(MouseEvent e) {
		// setEnabled(false);
		// }
		//
		// public void mouseReleased(MouseEvent e) {
		// setEnabled(true);
		// }
		//
		// public void mouseExited(MouseEvent e) {
		// setEnabled(true);
		// }
		// });
	}

	/**
	 * ドラッグ＆ドロップ実装のセット
	 * 
	 * @param listener リスナー
	 */
	public void setDnDListener(TDnDListener listener) {
		this.listener = listener;

		// ドラッグ＆ドロップを実現するためのメソッドを呼ぶ。
		DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE,
			this);
		new DropTarget(this, this);
	}

	/**
	 * @see java.awt.dnd.DragGestureListener#dragGestureRecognized(java.awt.dnd.DragGestureEvent)
	 */
	public void dragGestureRecognized(DragGestureEvent e) {

		try {
			if (!SwingUtilities.isLeftMouseButton((MouseEvent) e.getTriggerEvent())) {
				return;
			}

			if ((e.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {

				Transferable transfer = new ObjectTransferable(listener.getDragData());

				e.startDrag(DragSource.DefaultCopyDrop, transfer);
			}

		} catch (InvalidDnDOperationException ioe) {
			// 処理なし
		}
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
	 */
	public void drop(DropTargetDropEvent e) {

		boolean gotData = false;

		try {
			// 転送をチェック
			if (!e.isDataFlavorSupported(ObjectTransferable.FLAVOR)) {
				return;
			}

			Object data = e.getTransferable().getTransferData(ObjectTransferable.FLAVOR);

			if (data == null) {
				return;
			}

			listener.dropData(null, e.getLocation(), data);

			gotData = true;

		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			e.dropComplete(gotData);
		}
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragDropEnd(java.awt.dnd.DragSourceDropEvent)
	 */
	public void dragDropEnd(DragSourceDropEvent dsde) {
		// Override用
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragEnter(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dragEnter(DragSourceDragEvent dsde) {
		// Override用
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragExit(java.awt.dnd.DragSourceEvent)
	 */
	public void dragExit(DragSourceEvent dse) {
		// Override用
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragOver(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dragOver(DragSourceDragEvent dsde) {
		// Override用
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dropActionChanged(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dropActionChanged(DragSourceDragEvent dsde) {
		// Override用
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragEnter(DropTargetDragEvent dtde) {
		// Override用
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
	 */
	public void dragExit(DropTargetEvent dte) {
		// Override用
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragOver(DropTargetDragEvent dtde) {
		// Override用
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// Override用
	}
}
