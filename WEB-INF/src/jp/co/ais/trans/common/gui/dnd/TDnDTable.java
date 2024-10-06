package jp.co.ais.trans.common.gui.dnd;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;

/**
 * TTable�̋@�\�g���N���X�B<BR>
 * �h���b�O���h���b�v������s�Ȃ����߂̃��\�b�h��ǉ�����B
 * 
 * @author Yanwei
 */
public class TDnDTable extends TTable implements DropTargetListener, DragGestureListener, DragSourceListener {

	/** �h���b�O�p */
	private boolean dragFlg = false;

	/**
	 * �R���X�g���N�^ �h���b�v�p�̐ݒ���s�Ȃ��B
	 */
	public TDnDTable() {
		// �Adrop�̑ΏۂɂȂ� �R���|�[�l���g���擾����B
		Component tableArea = super.getCellAreaHandler().getTable();
		// �Bdrop���������邽�߂̃��\�b�h���ĂԁB
		if (tableArea != null) {
			new DropTarget(tableArea, DnDConstants.ACTION_COPY_OR_MOVE, this);
		}
	}

	/**
	 * �h���b�O�p�̐ݒ���s�Ȃ��B
	 */
	@Override
	public void repaint() {
		super.repaint();

		JCVectorDataSource ds = (JCVectorDataSource) this.getDataSource();
		if (ds != null && ds.getNumRows() == 0) {
			dragFlg = false;
		}
		if (dragFlg) {
			return;
		}
		// �Adrag�̑ΏۂɂȂ� �R���|�[�l���g���擾����B
		Component cellArea = super.getCellAreaHandler().getCellArea();
		// �Bdrag���������邽�߂̃��\�b�h���ĂԁB
		if (cellArea != null) {
			new DragSource().createDefaultDragGestureRecognizer(cellArea, DnDConstants.ACTION_COPY_OR_MOVE, this);
			dragFlg = true;
		}
	}

	/**
	 * �h���b�O�J�n���ɌĂ΂�郁�\�b�h �h���b�O�����f�[�^��]������B
	 */
	public void dragGestureRecognized(DragGestureEvent e) {
		InputEvent inputEvent = e.getTriggerEvent();
		try {
			if (SwingUtilities.isLeftMouseButton((MouseEvent) inputEvent)) {
				if ((e.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {

					// �@�ꗗ.�I���s�̃f�[�^���擾�B�����s�I������Ă���ꍇ�͑S�Ď擾����B
					List<DnDData> listDnDData = new ArrayList<DnDData>();

					// ��ʂɑI�����ꂽ�f�[�^
					for (int nomRow = 0; nomRow < this.getNumRows(); nomRow++) {
						if (this.isSelected(nomRow, 0)) {
							listDnDData.add(getRowData(nomRow));// �A �擾�����f�[�^��List�ɂ���B
						}
					}
					// �I���f�[�^���Ȃ��ꍇ�̓h���b�O���Ȃ�
					if (listDnDData == null || listDnDData.size() == 0) {
						return;
					}

					// �B �A.List��������TTransferable�N���X���C���X�^���X������
					TTransferable transfer = new TTransferable(listDnDData);

					// �C�h���b�O���J�n����
					e.startDrag(DragSource.DefaultCopyDrop, transfer, this);
				}
			}
		} catch (InvalidDnDOperationException ioe) {
			// �����Ȃ�
		}
	}

	/**
	 * �h���b�v���ɌĂ΂�郁�\�b�h �]���f�[�^��ݒ肷��B
	 */
	public void drop(DropTargetDropEvent e) {
		// �]�����`�F�b�N
		if (e.isDataFlavorSupported(TTransferable.nodeFavor)) {

			// �@�]���f�[�^�FList<DnDData>���擾����B
			List<DnDData> list = TTransferable.getDnDDataList(e.getTransferable());

			// �A �]���f�[�^�����݂��Ȃ��ꍇ�͏����I��
			if (list == null || list.isEmpty()) {
				e.dropComplete(false);
				return;
			}
			// ���R���|�[�l���g���́i�ꗗ����́j�h���b�O���h���b�v�̏ꍇ�͏����I��
			for (int nomRow = 0; nomRow < this.getNumRows(); nomRow++) {
				DnDData dndData = getRowData(nomRow);// �ꗗ.�f�[�^
				if (dndData.getCode().equals(list.get(0).getCode())) {
					e.dropComplete(false);
					return;
				}
			}

			// �B �ꗗ�ɓ]���f�[�^��ǉ�����B�J��Ԃ�����
			e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			for (DnDData dndData : list) {
				addRow(dndData);
			}

			// �C �ꗗ�� �\�[�g����B
			this.doSort();

			// �D �h���b�v��������������B
			e.dropComplete(true);

		} else {
			e.rejectDrop();
			return;
		}
	}

	/**
	 * �h���b�v�I�����ɌĂ΂�郁�\�b�h �h���b�O���h���b�v����������B
	 */
	public void dragDropEnd(DragSourceDropEvent e) {

		// �@�h���b�v������Ɋ������������`�F�b�N����B
		if (!e.getDropSuccess()) {
			return;
		}

		// �A���R���|�[�l���g�Ƀf�[�^��]�������ꍇ�A�]�������s���ꗗ����폜����B
		// �]���f�[�^�FList<DnDData>���擾����B�]���f�[�^��DragSourceDropEvent�ŕێ����Ă���Transferable����擾����B
		List<DnDData> list = TTransferable.getDnDDataList(e.getDragSourceContext().getTransferable());
		if (list == null || list.isEmpty()) {
			return;
		}

		// �Ώۃf�[�^���ꗗ����폜����
		for (DnDData dndData : list) {
			deleteRow(dndData);
		}
	}

	/**
	 * �ꗗ�Ƀf�[�^��ǉ�����B
	 * 
	 * @param dndData TT��ee�̊K�w�f�[�^�󂯓n���Ɏg�p����N���X
	 */
	public void addRow(DnDData dndData) {
		JCVectorDataSource ds = (JCVectorDataSource) this.getDataSource();
		Vector<Vector> cells = ds.getCells();

		Vector<Object> colum = new Vector<Object>();
		colum.add(0, dndData); // �J����0��ځi��\���J����:����.DnDData�C���X�^���X
		colum.add(1, dndData.getCode());// �J����1���:����.DnDData.�R�[�h
		colum.add(2, dndData.getName());// �J����2���:����.DnDData.����

		// �ǉ�
		cells.add(cells.size(), colum);

		// �ꗗ�Ƀf�[�^�𔽉f����
		setDataList(cells);
	}

	/**
	 * �w��s����f�[�^���擾����
	 * 
	 * @param row �w��s
	 * @return DnDData
	 */
	public DnDData getRowData(int row) {

		if (row > this.getNumRows() - 1) {
			return null;
		}

		TableDataModel model = this.getDataView();

		DnDData dndData = new DnDData();
		dndData.setCode(model.getTableDataItem(row, 1).toString());
		dndData.setName(model.getTableDataItem(row, 2).toString());
		dndData.setTopCode("");

		return dndData;
	}

	/**
	 * �w�Ώۃf�[�^���ꗗ����폜����
	 * 
	 * @param dndData
	 */
	public void deleteRow(DnDData dndData) {
		JCVectorDataSource ds = (JCVectorDataSource) this.getDataSource();
		Vector<Vector> cells = ds.getCells();

		TableDataModel model = this.getDataView();
		for (int row = 0; row < model.getNumRows(); row++) {
			String code = model.getTableDataItem(row, 1).toString();
			if (code.equals(dndData.getCode())) {
				cells.remove(row);
				break;
			}
		}

		// �ꗗ�Ƀf�[�^�𔽉f����
		setDataList(cells);

		if (cells.size() == 0) {
			dragFlg = false;
		}
	}

	/**
	 * �ꗗ���\�[�g����
	 */
	public void doSort() {
		JCVectorDataSource ds = (JCVectorDataSource) this.getDataSource();
		Vector<Vector> cells = ds.getCells();

		// ���я�
		Collections.sort(cells, new OrderByCode());

		// �ꗗ�Ƀf�[�^�𔽉f����
		setDataList(cells);
	}

	public void dragEnter(DropTargetDragEvent dtde) {
		// ���g�p

	}

	public void dragExit(DropTargetEvent dte) {
		// ���g�p

	}

	public void dragOver(DropTargetDragEvent dtde) {
		// ���g�p
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
		// ���g�p

	}

	public void dragEnter(DragSourceDragEvent dsde) {
		// ���g�p

	}

	public void dragExit(DragSourceEvent dse) {
		// ���g�p

	}

	public void dragOver(DragSourceDragEvent dsde) {
		// ���g�p

	}

	public void dropActionChanged(DragSourceDragEvent dsde) {
		// ���g�p

	}

	/**
	 * �ꗗ��Vector���я��̔�r�N���X
	 * 
	 * @author Yanwei
	 */
	private class OrderByCode implements Comparator<Vector> {

		/**
		 * �ꗗ��Vector����
		 * 
		 * @param vecFir ��r�Ώۂ̍ŏ��̃I�u�W�F�N�g
		 * @param vecSec ��r�Ώۂ� 2 �Ԗڂ̃I�u�W�F�N�g
		 * @return int ��r����
		 */
		public int compare(Vector vecFir, Vector vecSec) {

			String o1Code = vecFir.get(1).toString();// �R�[�h
			String o2Code = vecSec.get(1).toString();

			return o1Code.compareTo(o2Code);
		}
	}

	/**
	 * �ꗗ�Ƀf�[�^�𔽉f����
	 * 
	 * @param cells �ꗗ�f�[�^
	 */
	public void setDataList(Vector<Vector> cells) {
		// SS�f�[�^�̍\�z
		JCVectorDataSource ds = new JCVectorDataSource();

		// ��\���ݒ肷��
		ds.setColumnLabels(this.columnLabels);

		ds.setCells(cells);
		ds.setNumRows(cells.size());
		ds.setNumColumns(3);

		this.setDataSource(ds);

		if (cells.size() > 0) {
			this.setRowSelection(0, 0);
			this.setCurrentCell(0, 0);
		}
	}

}