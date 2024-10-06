package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

/**
 * 結合列
 */
public class TGroupColumn extends TableColumn {

	/** 描画者 */
	protected TableCellRenderer renderer;

	/** 結合列グループ */
	protected List list;

	/** ヘッダー文字 */
	protected String text;

	/** 余白 */
	protected int margin = 0;

	/**
	 * コンストラクター
	 * 
	 * @param text
	 */
	public TGroupColumn(String text) {
		this(null, text);
	}

	/**
	 * コンストラクター
	 * 
	 * @param renderer
	 * @param text
	 */
	public TGroupColumn(TableCellRenderer renderer, String text) {
		if (renderer == null) {
			this.renderer = new DefaultTableCellRenderer() {

				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
					JTableHeader header = table.getTableHeader();
					if (header != null) {
						setForeground(TTable.columnFontColor);
						setBackground(TTable.columnColor);
						setFont(header.getFont());
					}
					setHorizontalAlignment(SwingConstants.CENTER);
					setText((value == null) ? "" : value.toString());
					return this;
				}
			};
		} else {
			this.renderer = renderer;
		}
		this.text = text;
		list = new ArrayList();
	}

	/**
	 * @param g TableColumn or TGroupColumn
	 */
	public void add(TableColumn g) {
		if (g == null) {
			return;
		}
		list.add(g);
	}

	/**
	 * @param c TableColumn
	 * @param cols TGroupColumns
	 * @return 指定列を含むグループ
	 */
	public List getGroupColumns(TableColumn c, List cols) {
		cols.add(this);
		if (list.contains(c)) {
			return cols;
		}

		for (Object obj : list) {
			if (obj instanceof TGroupColumn) {
				List groups = ((TGroupColumn) obj).getGroupColumns(c, new ArrayList(cols));
				if (groups != null) {
					return groups;
				}
			}
		}
		return null;
	}

	/**
	 * @return ヘッダー描画者
	 */
	@Override
	public TableCellRenderer getHeaderRenderer() {
		return renderer;
	}

	/**
	 * ヘッダー描画者指定
	 * 
	 * @param renderer
	 */
	@Override
	public void setHeaderRenderer(TableCellRenderer renderer) {
		if (renderer != null) {
			this.renderer = renderer;
		}
	}

	/**
	 * @return ヘッダー文字
	 */
	@Override
	public Object getHeaderValue() {
		return text;
	}

	/**
	 * @param table
	 * @return ヘッダーサイズ
	 */
	public Dimension getSize(JTable table) {
		Component comp = renderer.getTableCellRendererComponent(table, getHeaderValue(), false, false, -1, -1);
		int height = comp.getPreferredSize().height;
		int totalWidth = 0;

		for (Object obj : list) {
			if (obj instanceof TableColumn) {
				TableColumn aColumn = (TableColumn) obj;
				totalWidth += aColumn.getWidth();
			} else {
				totalWidth += ((TGroupColumn) obj).getSize(table).width;
			}
		}
		return new Dimension(totalWidth, height);
	}

	/**
	 * @param margin
	 */
	public void setColumnMargin(int margin) {
		this.margin = margin;
		for (Object obj : list) {
			if (obj instanceof TGroupColumn) {
				((TGroupColumn) obj).setColumnMargin(margin);
			}
		}
	}

}
