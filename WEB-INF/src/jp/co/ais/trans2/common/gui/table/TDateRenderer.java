package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * ���t�ҏW�p�Z�������_��
 */
public class TDateRenderer extends TBaseCellRenderer {

	/** �����_���p�R���|�[�l���g */
	private final TCalendar renderer;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param renderer
	 * @param table
	 */
	public TDateRenderer(TCalendar renderer, TTable table) {
		super(table);

		// �����_���p�R���|�[�l���g�̃C���X�^���X��
		this.renderer = renderer;
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
	 *      boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		// �l�̐ݒ�
		if (value instanceof Date) {
			renderer.setValue((Date) value);

		} else {
			renderer.setValue(value);
		}

		// �F�̐ݒ�
		this.renderer.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));
		this.renderer.setForeground(tbl.getCellFontColor(isSelected, hasFocus));

		// �{�[�_�[�ݒ�
		setBorder(renderer, isSelected, hasFocus);

		return renderer;
	}

}