package jp.co.ais.trans2.common.gui;

import javax.swing.event.*;
import javax.swing.table.*;

/**
 * �s�ԍ��\���p�f�[�^���f��
 */
public class RowHeaderDataModel extends DefaultTableModel implements TableModelListener {

	/** ���e�[�u�� */
	protected TTable tbl;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param tbl ���e�[�u��
	 */
	public RowHeaderDataModel(TTable tbl) {
		this.tbl = tbl; // �f�[�^�p�e�[�u��

		TableModel dataModel = getTable().getModel();
		dataModel.removeTableModelListener(this);
		dataModel.addTableModelListener(this);
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (getTable().getRowHeaderStarIndex() != -1 && getTable().getRowHeaderStarIndex() == row) {
			return "*";
		}
		if (getTable().getRowHeaderMessage() != null && getTable().getRowHeaderMessage().size() > row) {
			return getTable().getRowHeaderMessage().get(row);
		}
		return row + tbl.rowNumPlus;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Integer.class;
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		// �l�̓o�^�͍s��Ȃ�
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false; // �ҏW�s��
	}

	@Override
	public int getRowCount() {
		if (getTable() != null) {
			return getTable().getRowCount(); // �f�[�^�e�[�u���̕\���s����Ԃ�
		}
		return 0;
	}

	/**
	 * TableModelListener�̃��\�b�h�ł���A�f�[�^�pTableModel�ɕύX���������Ƃ��ɌĂ΂��B
	 */
	public void tableChanged(TableModelEvent e) {
		switch (e.getType()) {
			case TableModelEvent.INSERT: // �s�ǉ�
			case TableModelEvent.DELETE: // �s�폜
				super.fireTableChanged(e);
				break;

			default:
				break;
		}
	}

	/**
	 * @return BaseTable
	 */
	protected BaseTable getTable() {
		if (tbl == null) {
			return null;
		}
		return tbl.table;
	}
}