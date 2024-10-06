package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * ���l�ҏW�p�Z���G�f�B�^
 */
public class TNumberCellValueEditor extends TBaseCellEditor {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param editor
	 * @param table
	 */
	public TNumberCellValueEditor(final TNumericField editor, TTable table) {
		super(editor, table);
	}

	/**
	 * @see javax.swing.DefaultCellEditor#getCellEditorValue()
	 */
	@Override
	public Object getCellEditorValue() {
		TNumericField txt = (TNumericField) editorComponent;

		NumberCellValue cellValue = new NumberCellValue();
		cellValue.setNumber(txt.getBigDecimal());
		cellValue.setMaxLength(txt.getMaxLength());
		cellValue.setDecimalPoint(txt.getDecimalPoint());

		return cellValue;
	}

	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean,
	 *      int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		// �R���|�[�l���g�̎擾
		TNumericField editor = (TNumericField) getComponent();
		editor.setBorder(BorderFactory.createEmptyBorder());

		// �Ώۍs�ԍ��̐ݒ�
		editor.setRowIndex(row);

		if (value != null) {
			// �l�̐ݒ�
			editor.setMaxLength(((NumberCellValue) value).getMaxLength());
			editor.setDecimalPoint(((NumberCellValue) value).getDecimalPoint());
			editor.setNumber(((NumberCellValue) value).getNumber());
		}

		return editor;
	}
}