package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * �e�L�X�g�ҏW�p�Z�������_��
 */
public class TTextAreaRenderer extends TBaseCellRenderer {

	/** �����_���p�R���|�[�l���g */
	private final TTextArea renderer;

	/** �X�v���b�h */
	private final TTable spread;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param renderer
	 * @param table
	 */
	public TTextAreaRenderer(TTextArea renderer, TTable table) {
		super(table);

		// �����_���p�R���|�[�l���g�̐ݒ�
		this.renderer = renderer;
		this.spread = table;
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
	 *      boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		// �l�̐ݒ�
		renderer.setText(Util.avoidNullNT(value));

		// �F�̐ݒ�
		this.renderer.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));
		this.renderer.setForeground(tbl.getCellFontColor(isSelected, hasFocus));

		// �{�[�_�[�ݒ�
		setBorder(renderer, isSelected, hasFocus);

		if (renderer.isAllowAutoAdjustTableHeight()) {

			// ���s��T�C�Y���擾���邽�߂�Bounds�ēx�ݒ肷��
			Rectangle rect = table.getCellRect(row, column, false);
			renderer.setBounds(rect);

			int height = Math.max(spread.getRowHeight(), renderer.getPreferredSize().height);
			adjustRowHeight(row, height);
		}

		return renderer;
	}

	/**
	 * ��������
	 * 
	 * @param row
	 * @param height
	 */
	public void adjustRowHeight(int row, int height) {

		if (spread.table.getRowHeight(row) != height) {
			spread.table.setRowHeight(row, height);
		}

		if (spread.getRowHeaderView().getRowHeight(row) != height) {
			spread.getRowHeaderView().setRowHeight(row, height);
		}
	}

}