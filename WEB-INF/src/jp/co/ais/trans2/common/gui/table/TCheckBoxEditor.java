package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * �`�F�b�N�{�b�N�X�p�Z���G�f�B�^
 */
public class TCheckBoxEditor extends TBaseCellEditor {

	/** �u�����N�\��Adapter */
	protected TComponentViewAdapter viewAdapter;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param table
	 */
	public TCheckBoxEditor(TTable table) {
		this(new TCheckBox(), table);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param checkBox
	 * @param table
	 */
	public TCheckBoxEditor(TCheckBox checkBox, TTable table) {

		super(checkBox, table);

		// �`�F�b�N�{�b�N�X�̃��C�A�E�g���w��
		checkBox.setHorizontalAlignment(SwingConstants.CENTER);
		checkBox.setOpaque(true);

	}

	/**
	 * �u�����N�\���A�_�v�^�[
	 * 
	 * @param adapter �A�_�v�^�[
	 */
	public void setViewAdapter(TComponentViewAdapter adapter) {
		this.viewAdapter = adapter;
	}

	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean,
	 *      int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		if (viewAdapter != null && viewAdapter.isBlank(row, column)) {
			return null;
		}

		// �R���|�[�l���g�̎擾
		TCheckBox checkBox = (TCheckBox) getComponent();

		// �w�i�F�̐ݒ�
		checkBox.setBackground(tbl.getBackgroundColor(row, isSelected, isSelected));
		checkBox.setForeground(tbl.getSelectedFontColor());

		// �Ώۍs�ԍ��̐ݒ�
		checkBox.setRowIndex(row);
		checkBox.setModelIndex(table.convertRowIndexToModel(row));

		return super.getTableCellEditorComponent(table, value, isSelected, row, column);

	}

	/**
	 * �ΏۃZ�����ҏW�\���ǂ���
	 * 
	 * @param row
	 * @param column
	 * @return true �ҏW��
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		if (viewAdapter == null) {
			return super.isCellEditable(row, column);
		}

		return !viewAdapter.isBlank(row, column) && viewAdapter.isEnable(row, column);
	}
}