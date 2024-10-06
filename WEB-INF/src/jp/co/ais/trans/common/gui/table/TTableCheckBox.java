package jp.co.ais.trans.common.gui.table;

import java.awt.event.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;

/**
 * TTable用CheckBox
 */
public class TTableCheckBox extends TCheckBox {

	/** カラム番号 */
	private int column = 0;

	/**
	 * コンストラクタ.
	 * 
	 * @param table 連動スプレッド
	 * @param row スプレッド内の行番号
	 * @param column スプレッド内のカラム番号
	 */
	public TTableCheckBox(TTable table, int row, int column) {

		setHorizontalAlignment(JCTableEnum.CENTER);
		setOpaque(false);
		setIndex(row);
		this.column = column;

		addItemListener(new SelectedListener(table));
	}

	/**
	 * スプレッド用リスナー
	 */
	private class SelectedListener implements ItemListener {

		/** 対象スプレッド */
		private TTable table;

		/**
		 * コンストラクタ.
		 * 
		 * @param table 対象スプレッド
		 */
		public SelectedListener(TTable table) {
			this.table = table;
		}

		/**
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */
		public void itemStateChanged(ItemEvent e) {
			TCheckBox checkBox = (TCheckBox) e.getSource();
			int row = table.isSort() ? table.getCurrentRow() : checkBox.getIndex();

			table.requestFocusInWindow();
			table.setRowSelection(row, row);

			// データの方
			Object obj = table.getDataSource().getTableDataItem(row, column);
			if (obj instanceof Boolean) {
				((JCVectorDataSource) table.getDataSource()).setCell(row, column,
					e.getStateChange() == ItemEvent.SELECTED);
			}
		}
	}
}
