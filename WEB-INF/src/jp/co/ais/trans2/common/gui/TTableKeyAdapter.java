package jp.co.ais.trans2.common.gui;

import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;

/**
 * キーアダプター(貼り付け機能)
 */
public class TTableKeyAdapter extends KeyAdapter {

	/** フィールド */
	public TTextField editor;

	/** スプレッド */
	public TTable tbl;

	/** 行番号 */
	public int rowIndex;

	/** 列番号 */
	public int columnIndex;

	/** タイプ */
	public int[] types;

	/**
	 * コンストラクター
	 * 
	 * @param editor フィールド
	 * @param tbl スプレッド
	 * @param rowIndex 行番号
	 * @param columnIndex 列番号
	 * @param types タイプ配列(貼り付け列→右全列)
	 */
	public TTableKeyAdapter(TTextField editor, TTable tbl, int rowIndex, int columnIndex, int[] types) {
		this.editor = editor;
		this.tbl = tbl;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.types = types;
	}

	@Override
	public void keyPressed(KeyEvent ev) {

		// CTRL+V、SHIFT+INSERTで貼り付け機能対応
		if (rowIndex != -1
			&& ((KeyEvent.VK_V == ev.getKeyCode() && ev.isControlDown()) || (KeyEvent.VK_INSERT == ev.getKeyCode() && ev
				.isShiftDown()))) {

			String[] arr = TTablePasteUtil.getArray(TTablePasteUtil.getClipboardText(), "\n");
			if (arr == null || arr.length == 0 || types == null || types.length == 0) {
				return;
			}

			// １行目から貼り付ける
			int row = rowIndex;

			// １行目の編集モードを止める
			tbl.editCellAt(row, columnIndex);
			if (tbl.getCellEditor(row, columnIndex) != null) {
				tbl.getCellEditor(row, columnIndex).stopCellEditing();
			}

			// 貼り付ける
			for (int i = 0; i < arr.length; i++) {
				String line = arr[i];
				String[] cols = TTablePasteUtil.getArray(line, "\t");

				Object obj = null;

				// 行単位で貼り付ける
				for (int j = 0; j < Math.min(types.length, cols.length); j++) {
					int col = columnIndex + j;

					// 列アウトバウンド判定
					if (col >= tbl.getColumnModel().getColumnCount()) {
						// 列＞最大列、処理しない
						break;
					}

					switch (types[j]) {
						case TTablePasteUtil.NUMBER:
							obj = TTablePasteUtil.getNumber(cols[j]);
							break;
						case TTablePasteUtil.DATE:
							obj = TTablePasteUtil.getDate(cols[j]);
							break;
						case TTablePasteUtil.STRING:
							obj = cols[j];
							break;
						default:
							// 処理しない
							continue;
					}

					// 貼り付け
					tbl.setRowValueAt(obj, row, col);

					// 新しい設定値による計算処理を行う
					tbl.editCellAt(row, col);
					if (tbl.getCellEditor(row, col) != null) {
						tbl.getCellEditor(row, col).stopCellEditing();
					}
				}

				row++;
				if (row >= tbl.getRowCount()) {
					break;
				}
			}

		}
	}

}
