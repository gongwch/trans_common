package jp.co.ais.trans.common.gui.dnd;

import java.awt.*;
import java.awt.dnd.*;
import java.util.*;
import java.util.List;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * Ttable�g���N���X����X�v���b�h���̏����ύX���h���b�O&�h���b�v�Ŏ��������e�[�u��
 */
public abstract class TOrderedTable extends TTable implements DropTargetListener, DragGestureListener,
	DragSourceListener {

	/** �G���e�B�e�B��ێ����Ă���J�����C���f�b�N�X */
	static final int BEAN_INDEX = 0;

	/**
	 * �R���X�g���N�^
	 */
	public TOrderedTable() {

		// �X�v���b�h�̕\����ݒ肷��
		initTableStyle();

	}

	/**
	 * ���C�A�E�g��ݒ肷��
	 */
	@Override
	public void doLayout() {
		super.doLayout();

		// �h���b�v�^�[�Q�b�g�̒ǉ�
		Component cellArea = super.getCellAreaHandler().getCellArea();
		if (cellArea != null) {
			new DropTarget(cellArea, DnDConstants.ACTION_COPY_OR_MOVE, this);
			new DragSource().createDefaultDragGestureRecognizer(cellArea, DnDConstants.ACTION_COPY_OR_MOVE, this);
		}
	}

	/**
	 * �h���b�O�f�[�^��]������i�h���b�O�J�n���̏����j
	 */
	public void dragGestureRecognized(DragGestureEvent e) {

		try {

			if ((e.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {

				// �I���s�̎擾
				List<OrderedData> list = getSelectedList();

				// �I���f�[�^���Ȃ��ꍇ�̓h���b�O���Ȃ�
				if (list.size() == 0) {
					return;
				}

				// �h���b�O�����̊J�n
				e.startDrag(DragSource.DefaultCopyDrop, new TOrderedTransferable(list), this);
			}

		} catch (InvalidDnDOperationException ioe) {
			// �h���b�O���ɕK����������̂ŏ������Ȃ�
		}
	}

	/**
	 * �]���f�[�^���擾/�ݒ肷��i�h���b�v���̏����j
	 */
	public void drop(DropTargetDropEvent e) {

		try {
			Object droppedObject = e.getTransferable().getTransferData(TOrderedTransferable.orderedFlavor);

			// �]���f�[�^�̎擾
			List<OrderedData> list = (List<OrderedData>) droppedObject;

			// �]���f�[�^�����݂��Ȃ��ꍇ�͏����I��
			if (Util.isNullOrEmpty(list) || list.size() == 0) {
				e.dropComplete(false);
				return;
			}

			e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

			Point point = e.getLocation();
			Component comp = ((DropTarget) e.getSource()).getComponent();
			Point tablePoint = comp.getLocation();
			point.translate(tablePoint.x, tablePoint.y);

			JCCellPosition position = XYToCell(point.x, point.y);
			OrderedData dropTargetData = getOrderedData(position.row);

			// �h���b�v�ʒu�Ƀf�[�^�����݂��Ȃ��ꍇ�͏����I��
			if (Util.isNullOrEmpty(dropTargetData)) {
				e.dropComplete(false);
				return;
			}

			// �h���b�v�s�Ɠ]���s�������ꍇ�͏����I��
			for (OrderedData bean : list) {
				if (bean.equals(dropTargetData)) {
					e.dropComplete(false);
					return;
				}
			}

			// �]���s�f�[�^�̍폜
			removeRow(list);

			// �h���b�v�s�̉��Ƀf�[�^��ǉ�����
			List<OrderedData> updList = new ArrayList<OrderedData>();
			for (int rowIndex = 0; rowIndex < this.getNumRows(); rowIndex++) {
				OrderedData bean = getOrderedData(rowIndex);
				updList.add(bean);
				if (bean.equals(dropTargetData)) {
					updList.addAll(list);
				}
			}

			setOrderedData(updList);

			// �h���b�v�s���đI��
			for (int rowIndex = 0; rowIndex < this.getNumRows(); rowIndex++) {
				OrderedData bean = getOrderedData(rowIndex);
				if (bean.equals(dropTargetData)) {
					setRowSelection(rowIndex, rowIndex);
					setCurrentCell(rowIndex, 0);
				}
			}

			e.dropComplete(true);

		} catch (Exception ex) {
			e.dropComplete(false);
		}
	}

	/**
	 * �w�肵���s�ŕێ����Ă���G���e�B�e�B�f�[�^���擾����
	 * 
	 * @param index
	 * @return �G���e�B�e�B
	 */
	public OrderedData getOrderedData(int index) {

		return (OrderedData) this.getDataView().getTableDataItem(index, BEAN_INDEX);
	}

	/**
	 * ��v����f�[�^���폜����
	 * 
	 * @param list �Ώۃf�[�^���X�g
	 */
	public void removeRow(List<OrderedData> list) {

		// �ێ�����f�[�^���X�V����
		List<OrderedData> updList = getOrderedData();
		setOrderedData(updList);

		// �폜�Ώۍs�̎擾
		updList = new ArrayList<OrderedData>();
		for (int rowIndex = 0; rowIndex < this.getNumRows(); rowIndex++) {
			boolean isAdd = true;
			OrderedData bean = getOrderedData(rowIndex);

			// �f�[�^����v����ꍇ �ǉ����Ȃ��B
			for (OrderedData deleteBean : list) {
				if (deleteBean.equals(bean)) {
					isAdd = false;
				}
			}

			if (isAdd) {
				updList.add(bean);
			}
		}

		setOrderedData(updList);
	}

	/**
	 * �X�v���b�h�̕\����ݒ肷��
	 */
	public abstract void initTableStyle();

	/**
	 * �I���s���擾����
	 * 
	 * @return list
	 */
	public abstract List<OrderedData> getSelectedList();

	/**
	 * �ꗗ�Ƀf�[�^��ݒ肷��
	 * 
	 * @param list
	 */
	public abstract void setOrderedData(List<OrderedData> list);

	/**
	 * �ꗗ�̃f�[�^���擾����
	 * 
	 * @return �ꗗ�f�[�^
	 */
	public abstract List<OrderedData> getOrderedData();

	// �h���b�O���󂯓��ꂽ�ꍇ�A�J�[�\���̕\����ς���
	public void dragEnter(DragSourceDragEvent e) {
		e.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
	}

	// �h���b�O���󂯓��ꂽ�ꍇ�A�J�[�\���̕\����ς���
	public void dragExit(DragSourceEvent e) {
		e.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
	}

	public void dragOver(DragSourceDragEvent e) {
		// ���g�p
	}

	public void dropActionChanged(DragSourceDragEvent e) {
		// ���g�p
	}

	public void dragEnter(DropTargetDragEvent e) {
		// ���g�p
	}

	public void dragExit(DropTargetEvent e) {
		// ���g�p
	}

	public void dragOver(DropTargetDragEvent e) {
		// ���g�p
	}

	public void dropActionChanged(DropTargetDragEvent e) {
		// ���g�p
	}

	public void dragDropEnd(DragSourceDropEvent dsde) {
		// ���g�p

	}

}
