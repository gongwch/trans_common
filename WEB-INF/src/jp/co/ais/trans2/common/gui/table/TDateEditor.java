package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * ���t�ҏW�p�Z���G�f�B�^
 */
public class TDateEditor extends TBaseCellEditor {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param table
	 */
	public TDateEditor(TTable table) {
		this(TCalendar.TYPE_YMD, table);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param calType �t�H�[�}�b�g
	 * @param table
	 */
	public TDateEditor(int calType, TTable table) {

		this(new TCalendar(calType), table);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param editor
	 * @param table
	 */
	public TDateEditor(TCalendar editor, TTable table) {
		super(editor, table);

		// �G�f�B�^�p�R���|�[�l���g�̐ݒ�
		editor.setBorder(BorderFactory.createEmptyBorder());
	}

	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean,
	 *      int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		// �R���|�[�l���g�̎擾
		TCalendar editor = (TCalendar) getComponent();

		// �Ώۍs�ԍ��̐ݒ�
		editor.setRowIndex(row);

		// �l�̐ݒ�
		if (value instanceof Date) {
			editor.setValue((Date) value);

		} else {
			editor.setValue(value);
		}

		if (editor.isUseTablePaste()) {
			TTablePasteUtil.handleKeyEvent(editor, tbl, row, column, editor.getCellTypes());
		}

		return editor;
	}

}