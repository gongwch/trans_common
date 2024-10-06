package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.math.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * ���z�ҏW�p�Z���G�f�B�^
 */
public class TCurrencyNumricEditor extends TBaseCellEditor {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param editor
	 * @param table
	 */
	public TCurrencyNumricEditor(TCurrencyNumericField editor, TTable table) {
		super(editor, table);
	}

	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean,
	 *      int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		// �R���|�[�l���g�̎擾
		TCurrencyNumericField editor = (TCurrencyNumericField) getComponent();
		editor.setBorder(BorderFactory.createEmptyBorder());
		editor.setFont(table.getFont());

		if (editor.isUseTableFont()) {
			editor.setFont(table.getFont());
		}

		if (editorComponent instanceof TCurrencyNumericField) {
			TCurrencyNumericField field = (TCurrencyNumericField) editorComponent;
			editor.setUSD(field.isUSD());
			editor.setJPY(field.isJPY());
		}

		// �Ώۍs�ԍ��̐ݒ�
		editor.setRowIndex(row);

		if (value != null && value instanceof NumberCellValue) {
			// �l�̐ݒ�
			editor.setMaxLength(((NumberCellValue) value).getMaxLength());
			editor.setDecimalPoint(((NumberCellValue) value).getDecimalPoint());
			editor.setNumber(((NumberCellValue) value).getNumber());
		}

		// �l�̐ݒ�
		setValue(editor, value);

		if (editor.isUseTablePaste()) {
			TTablePasteUtil.handleKeyEvent(editor, tbl, row, column, editor.getCellTypes());
		}

		return editor;
	}

	/**
	 * �l�̐ݒ�
	 * 
	 * @param txt
	 * @param value
	 */
	protected void setValue(TCurrencyNumericField txt, Object value) {

		if (Util.isNullOrEmpty(value)) {
			txt.clear();

		} else if (value instanceof Number) {
			txt.setNumber((Number) value);

		} else {
			BigDecimal num = TTablePasteUtil.getNumber(Util.avoidNull(value));
			txt.setNumber(num);
		}
	}

	/**
	 * �����_�ݒ�
	 * 
	 * @param txt
	 * @param value
	 * @param point
	 */
	protected void setDecimalPoint(TCurrencyNumericField txt, Object value, int point) {
		txt.setDecimalPoint(point);

		setValue(txt, value);
	}
}