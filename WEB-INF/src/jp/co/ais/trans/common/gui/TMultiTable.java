package jp.co.ais.trans.common.gui;

import java.awt.event.*;
import java.util.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

/**
 * TTableに、複数行選択を可能にしたTable.
 */
@Deprecated
public class TMultiTable extends TTable {

	/**
	 * Constructor.
	 */
	public TMultiTable() {
		super();
		// 複数選択可
		super.setSelectMultiRange(true);
	}

	/**
	 * キーリスナー
	 */
	@Override
	protected void setKeyListener() {
		// キー用リスナ
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent ev) {
				// ファンクションキー用ハンドラ
				handleKeyPressed(ev);

				// Enterキー押下（データ呼出がない場合は次行選択）
				if (ev.getKeyCode() == KeyEvent.VK_ENTER) {
					ev.consume();

					if (TMultiTable.this.btn != null && isEnterToButton) {
						TMultiTable.this.btn.doClick();
					} else {
						TMultiTable.this.traverse(JCTableEnum.TRAVERSE_DOWN);
						TMultiTable.this.requestFocus();// フォーカスをシートに戻す
					}
					TMultiTable.this
						.setRowSelection(TMultiTable.this.getCurrentRow(), TMultiTable.this.getCurrentRow());
				}
			}

			@Override
			public void keyReleased(KeyEvent ev) {

				// PageUp,PageDownキー押下
				if (ev.getKeyCode() == KeyEvent.VK_PAGE_UP || ev.getKeyCode() == KeyEvent.VK_PAGE_DOWN
					|| ev.getKeyCode() == KeyEvent.VK_TAB) {
					TMultiTable.this.traverse(JCTableEnum.TRAVERSE_TO_CELL);
					TMultiTable.this
						.setRowSelection(TMultiTable.this.getCurrentRow(), TMultiTable.this.getCurrentRow());
				}

				// スペースキー押下(チェックボックスがある場合反応)
				if (ev.getKeyCode() == KeyEvent.VK_SPACE) {

					JCVectorDataSource ds = (JCVectorDataSource) TMultiTable.this.getDataSource();

					TMultiTable table = TMultiTable.this;

					// 選ばれたデータ
					Collection collection = TMultiTable.this.getSelectedCells();

					JCCellRange[] list = (JCCellRange[]) collection.toArray(new JCCellRange[collection.size()]);

					// 一行でも選択されているか
					boolean isCheckLine = false;
					// 全行選択されているか
					boolean isAllCheckLine = true;

					// 複数選択可能時のみ
					if (isSelectMultiLine) {
						// 全件チェックされているかどうか確認
						for (JCCellRange range : list) {
							// 選択の初めの行
							int startRow = range.start_row;
							// 選択のは行を終える
							int endRow = range.end_row;

							// 選択終了行 < 選択開始行の場合は入換。
							if (endRow < startRow) {
								int tmp;
								tmp = endRow;
								endRow = startRow;
								startRow = tmp;
							}

							// 一行でもチェックがついてるか検索
							for (; startRow <= endRow; startRow++) {
								TCheckBox checkBox = (TCheckBox) table.getComponent(startRow, checkBoxColumnNumber);
								if (checkBox.isSelected()) {
									isCheckLine = true;
									if (isAllCheckLine) {
										isAllCheckLine = true;
									}
								} else {
									isAllCheckLine = false;
								}
							}
						}
					}

					// チェック処理
					for (JCCellRange range : list) {
						// 選択の初めの行
						int startRow = range.start_row;
						// 選択のは行を終える
						int endRow = range.end_row;

						// 選択終了行 < 選択開始行の場合は入換。
						if (endRow < startRow) {
							int tmp;
							tmp = endRow;
							endRow = startRow;
							startRow = tmp;
						}

						try {
							Boolean status = (Boolean) table.getDataSource().getTableDataItem(startRow,
								checkBoxColumnNumber);
							ds.setCell(startRow, checkBoxColumnNumber, !status);

						} catch (Exception e) {
							ev.consume();
						}

						// 複数行選択可能時
						if (isSelectMultiLine) {
							// チェック処理
							for (; startRow <= endRow; startRow++) {
								TCheckBox checkBox = (TCheckBox) table.getComponent(startRow, checkBoxColumnNumber);
								if (isAllCheckLine) {
									checkBox.setSelected(false);
								} else if (isCheckLine) {
									checkBox.setSelected(true);
								} else {
									checkBox.setSelected(!checkBox.isSelected());
								}
							}

						}
						// 単一行のみ
						else {
							try {
								TCheckBox checkBox = (TCheckBox) table.getComponent(startRow, checkBoxColumnNumber);
								checkBox.setSelected(!checkBox.isSelected());
							} catch (Exception e) {
								ev.consume();
							}
						}

					}

				}
			}

		});

	}

}
