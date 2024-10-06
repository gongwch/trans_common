package jp.co.ais.trans2.common.gui;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * TTableのセルにセットできるコンポーネントインターフェース<br>
 * 
 * @author AIS
 */
public interface TTableComponent {

	/**
	 * CellRendererを返す
	 * 
	 * @param tbl
	 * @return Renderer
	 */
	public TableCellRenderer getCellRenderer(TTable tbl);

	/**
	 * CellEditorを返す
	 * 
	 * @param tbl
	 * @return CellEditor
	 */
	public TableCellEditor getCellEditor(TTable tbl);

	/**
	 * 行番号を取得します
	 * 
	 * @return 行番号
	 */
	public int getRowIndex();

	/**
	 * 行番号を設定します
	 * 
	 * @param rowIndex
	 */
	public void setRowIndex(int rowIndex);

	/**
	 * 水平方向のalign getter
	 * 
	 * @return 水平方向のalign
	 */
	public int getDefaultCellRendererHorizontalAlignment();

	/**
	 * テーブルで利用されているかどうか
	 * 
	 * @return true:利用
	 */
	public boolean isTableCellEditor();

	/**
	 * テーブルで利用するかどうか
	 * 
	 * @param tableCellEditor true:利用
	 */
	public void setTableCellEditor(boolean tableCellEditor);

}
