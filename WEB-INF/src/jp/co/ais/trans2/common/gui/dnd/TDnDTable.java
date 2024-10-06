package jp.co.ais.trans2.common.gui.dnd;

import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * Object���h���b�O���h���b�v�Ŏ󂯓n���ׂ�Panel
 */
public class TDnDTable extends TTable implements DragSourceListener, DragGestureListener, DropTargetListener {

	/** ���X�i�[ */
	private TDnDListener dndListener;

	@Override
	public BaseTable createBaseTable() {
		return new TDnDBaseTable();
	}

	/**
	 * �h���b�O���h���b�v�����̃Z�b�g
	 * 
	 * @param listener ���X�i�[
	 */
	public void setDnDListener(TDnDListener listener) {
		((TDnDBaseTable) table).setDnDListener(listener);
		this.dndListener = listener;

		// �󔒉ӏ��ւ̃h���b�v���ł���悤��
		new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
	}

	/**
	 * Object���h���b�O���h���b�v�Ŏ󂯓n���ׂ�Panel
	 */
	public class TDnDBaseTable extends BaseTable implements DragSourceListener, DragGestureListener, DropTargetListener {

		/** ���X�i�[ */
		private TDnDListener listener;

		/**
		 * �R���X�g���N�^.
		 */
		public TDnDBaseTable() {
			super();
		}

		/**
		 * �h���b�O���h���b�v�����̃Z�b�g
		 * 
		 * @param listener ���X�i�[
		 */
		public void setDnDListener(TDnDListener listener) {
			this.listener = listener;

			// �h���b�O���h���b�v���������邽�߂̃��\�b�h���ĂԁB
			DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(this,
				DnDConstants.ACTION_COPY_OR_MOVE, this);
			new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
		}

		/**
		 * �h���b�O����.<br>
		 * Object���󂯓n��.
		 * 
		 * @see java.awt.dnd.DragGestureListener#dragGestureRecognized(java.awt.dnd.DragGestureEvent)
		 */
		public void dragGestureRecognized(DragGestureEvent e) {

			try {
				if (!SwingUtilities.isLeftMouseButton((MouseEvent) e.getTriggerEvent())) {
					return;
				}

				if ((e.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {

					Transferable transfer = new ObjectTransferable(listener.getDragData(e));

					Object data = transfer.getTransferData(ObjectTransferable.FLAVOR);
					// �h���b�O����^�[�Q�b�g�����݂��Ȃ��ꍇ
					if (data == null) {
						return;
					}

					e.startDrag(DragSource.DefaultCopyDrop, transfer);
				}

			} catch (InvalidDnDOperationException ioe) {
				// �����Ȃ�
			} catch (UnsupportedFlavorException ex) {
				// �����Ȃ�
			} catch (IOException ex) {
				// �����Ȃ�
			}
		}

		/**
		 * �h���b�v����.<br>
		 * Object���󂯎��.
		 * 
		 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
		 */
		public void drop(DropTargetDropEvent e) {

			boolean gotData = false;

			try {
				// �]�����`�F�b�N
				if (!e.isDataFlavorSupported(ObjectTransferable.FLAVOR)) {
					return;
				}

				Object data = e.getTransferable().getTransferData(ObjectTransferable.FLAVOR);
				if (data == null) {
					return;
				}

				listener.dropData(e, data);

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
			// Override�p
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragEnter(java.awt.dnd.DragSourceDragEvent)
		 */
		public void dragEnter(DragSourceDragEvent dsde) {
			// Override�p
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragExit(java.awt.dnd.DragSourceEvent)
		 */
		public void dragExit(DragSourceEvent dse) {
			// Override�p
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragOver(java.awt.dnd.DragSourceDragEvent)
		 */
		public void dragOver(DragSourceDragEvent dsde) {
			// Override�p
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dropActionChanged(java.awt.dnd.DragSourceDragEvent)
		 */
		public void dropActionChanged(DragSourceDragEvent dsde) {
			// Override�p
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
		 */
		public void dragEnter(DropTargetDragEvent dtde) {
			// Override�p
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
		 */
		public void dragExit(DropTargetEvent dte) {
			// Override�p
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)
		 */
		public void dragOver(DropTargetDragEvent dtde) {
			// Override�p
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.DropTargetDragEvent)
		 */
		public void dropActionChanged(DropTargetDragEvent dtde) {
			// Override�p
		}
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
	 */
	@Override
	public void dragEnter(DropTargetDragEvent arg0) {
		// Override�p

	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
	 */
	@Override
	public void dragExit(DropTargetEvent arg0) {
		// Override�p

	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)
	 */
	@Override
	public void dragOver(DropTargetDragEvent arg0) {
		// Override�p

	}

	/**
	 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
	 */
	@Override
	public void drop(DropTargetDropEvent e) {

		boolean gotData = false;

		try {
			// �]�����`�F�b�N
			if (!e.isDataFlavorSupported(ObjectTransferable.FLAVOR)) {
				return;
			}

			Object data = e.getTransferable().getTransferData(ObjectTransferable.FLAVOR);
			if (data == null) {
				return;
			}

			dndListener.dropData(e, data);

			gotData = true;

		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			e.dropComplete(gotData);
		}
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.DropTargetDragEvent)
	 */
	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
		// Override�p

	}

	/**
	 * @see java.awt.dnd.DragGestureListener#dragGestureRecognized(java.awt.dnd.DragGestureEvent)
	 */
	@Override
	public void dragGestureRecognized(DragGestureEvent arg0) {
		// Override�p

	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragDropEnd(java.awt.dnd.DragSourceDropEvent)
	 */
	@Override
	public void dragDropEnd(DragSourceDropEvent arg0) {
		// Override�p

	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragEnter(java.awt.dnd.DragSourceDragEvent)
	 */
	@Override
	public void dragEnter(DragSourceDragEvent arg0) {
		// Override�p

	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragExit(java.awt.dnd.DragSourceEvent)
	 */
	@Override
	public void dragExit(DragSourceEvent arg0) {
		// Override�p

	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragOver(java.awt.dnd.DragSourceDragEvent)
	 */
	@Override
	public void dragOver(DragSourceDragEvent arg0) {
		// Override�p

	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dropActionChanged(java.awt.dnd.DragSourceDragEvent)
	 */
	@Override
	public void dropActionChanged(DragSourceDragEvent arg0) {
		// Override�p

	}

}
