package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * �e�L�X�g�ҏW�p�Z�������_��
 */
public class TTextRenderer extends TBaseCellRenderer {

	/** �����_���p�R���|�[�l���g */
	protected final TTextField renderer;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param renderer
	 * @param table
	 */
	public TTextRenderer(TTextField renderer, TTable table) {
		super(table);

		// �����_���p�R���|�[�l���g�̐ݒ�
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
		renderer.setValue(value);

		// �F�̐ݒ�
		this.renderer.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));
		this.renderer.setForeground(tbl.getCellFontColor(isSelected, hasFocus));

		// �{�[�_�[�ݒ�
		setBorder(renderer, isSelected, hasFocus);

		return renderer;
	}

}