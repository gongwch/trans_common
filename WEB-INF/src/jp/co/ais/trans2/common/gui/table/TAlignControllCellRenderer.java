package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * 特殊配置セルレンダラ
 */
public class TAlignControllCellRenderer extends TBaseCellRenderer {

	/** 配置 */
	protected Map<Integer, List<Integer>> alignMap = new TreeMap<Integer, List<Integer>>();

	/** デフォルト */
	protected int defaultAlignment = SwingConstants.LEFT;

	/**
	 * コンストラクタ.
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

		// 色の設定
		comp.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));
		comp.setForeground(tbl.getCellFontColor(isSelected, hasFocus));

		// 配置換え
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
	 * 上位のsetHorizontalAlignmentを呼ぶ為の内部メソッド
	 * 
	 * @param alignment 位置
	 */
	protected void setAlignment(int alignment) {
		super.setHorizontalAlignment(alignment);
	}

	/**
	 * 特殊配置させる行を追加する
	 * 
	 * @param align 位置
	 * @param rows 行番号
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
	 * 配置設定をクリア
	 */
	public void clear() {
		alignMap.clear();
	}
}