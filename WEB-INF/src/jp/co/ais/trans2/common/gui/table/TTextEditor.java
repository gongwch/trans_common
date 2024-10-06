package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * �e�L�X�g�ҏW�p�Z���G�f�B�^
 */
public class TTextEditor extends TBaseCellEditor {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param editor
	 * @param table
	 */
	public TTextEditor(TTextField editor, TTable table) {

		super(editor, table);

		// �G�f�B�^�p�R���|�[�l���g�̐ݒ�
		editor.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));

	}

	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean,
	 *      int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		// �R���|�[�l���g�̎擾
		TTextField editor = (TTextField) getComponent();

		// �Ώۍs�ԍ��̐ݒ�
		editor.setRowIndex(row);

		// �l�̐ݒ�
		editor.setValue(value);
		editor.pushOldText(); // �C���N�������g�T�[�`�o�O�Ή�

		return editor;
	}

}