package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * ����z�u�Z�������_��
 */
public class TAlignControllCellRenderer extends TBaseCellRenderer {

	/** �z�u */
	protected Map<Integer, List<Integer>> alignMap = new TreeMap<Integer, List<Integer>>();

	/** �f�t�H���g */
	protected int defaultAlignment = SwingConstants.LEFT;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param tbl
	 */
	public TAlignControllCellRenderer(TTable tbl) {
		super(tbl);
	}

	/**
	 * @see javax.swing.JLabel#setHorizontalAlignment(int)
	 */
	@Override
	public void setHorizontalAlignment(int alignment) {
		super.setHorizontalAlignment(alignment);

		this.defaultAlignment = alignment;
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
	 *      boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		TAlignControllCellRenderer comp = (TAlignControllCellRenderer) super.getTableCellRendererComponent(table,
			value, isSelected, hasFocus, row, column);

		// �F�̐ݒ�
		comp.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));
		comp.setForeground(tbl.getCellFontColor(isSelected, hasFocus));

		// �z�u����
		for (Map.Entry<Integer, List<Integer>> entry : alignMap.entrySet()) {
			if (entry.getValue().contains(row)) {
				comp.setAlignment(entry.getKey());
				return comp;
			}
		}

		comp.setAlignment(defaultAlignment);
		return comp;
	}

	/**
	 * ��ʂ�setHorizontalAlignment���ĂԈׂ̓������\�b�h
	 * 
	 * @param alignment �ʒu
	 */
	protected void setAlignment(int alignment) {
		super.setHorizontalAlignment(alignment);
	}

	/**
	 * ����z�u������s��ǉ�����
	 * 
	 * @param align �ʒu
	 * @param rows �s�ԍ�
	 */
	public void addHorizontalAlignmentRow(int align, int... rows) {
		List<Integer> rowList = alignMap.get(align);

		if (rowList == null) {
			rowList = new LinkedList<Integer>();
			alignMap.put(align, rowList);
		}

		for (int row : rows) {
			rowList.add(row);
		}
	}

	/**
	 * �z�u�ݒ���N���A
	 */
	public void clear() {
		alignMap.clear();
	}
}