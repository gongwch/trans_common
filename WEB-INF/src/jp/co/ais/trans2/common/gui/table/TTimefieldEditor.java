package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * TZ�ҏW�p�Z���G�f�B�^
 */
public class TTimefieldEditor extends TBaseCellEditor {

	/** �N���� */
	protected static final String BASE_YMD = "2000/01/01 ";

	/** �X�v���b�h�̃t�H���g���g�� */
	protected boolean useTableFont = false;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param editor
	 * @param table
	 */
	public TTimefieldEditor(TTimeField editor, TTable table) {
		super(editor, table);
	}

	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean,
	 *      int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		// �R���|�[�l���g�̎擾
		TTimeField editor = (TTimeField) getComponent();
		editor.setBorder(BorderFactory.createEmptyBorder());

		if (this.isUseTableFont()) {
			editor.setFont(table.getFont());
		}

		// �Ώۍs�ԍ��̐ݒ�
		editor.setRowIndex(row);

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
	protected void setValue(TTimeField txt, Object value) {

		if (Util.isNullOrEmpty(value)) {
			txt.clear();
		}
		try {
			String hm = Util.avoidNull(value);
			// HH:mm �`���ȊO�̓G���[�Ƃ���B
			if (hm.length() != 5) {
				return;
			}

			Date date = null;
			date = DateUtil.toYMDHMDate(BASE_YMD + hm);
			txt.setText(DateUtil.toHMString(date));
		} catch (ParseException e) {
			return;
		}
	}

	/**
	 * �X�v���b�h�̃t�H���g���g���̎擾
	 * 
	 * @return useTableFont �X�v���b�h�̃t�H���g���g��
	 */
	public boolean isUseTableFont() {
		return useTableFont;
	}

	/**
	 * �X�v���b�h�̃t�H���g���g���̐ݒ�
	 * 
	 * @param useTableFont �X�v���b�h�̃t�H���g���g��
	 */
	public void setUseTableFont(boolean useTableFont) {
		this.useTableFont = useTableFont;
	}

}