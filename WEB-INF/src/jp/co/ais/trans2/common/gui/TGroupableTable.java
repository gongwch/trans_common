package jp.co.ais.trans2.common.gui;

import javax.swing.table.*;

/**
 * VC結合ヘッダーテーブル
 */
public class TGroupableTable extends TTable {

	/**
	 * @see jp.co.ais.trans2.common.gui.TTable#createBaseTable()
	 */
	@Override
	protected BaseTable createBaseTable() {
		BaseTable tbl = new VCBaseTable();
		tbl.getTableHeader().setBackground(columnColor);
		tbl.getTableHeader().setForeground(columnFontColor);
		tbl.getTableHeader().setBorder(null);
		return tbl;
	}

	/**
	 * VC結合ヘッダーテーブル
	 */
	public class VCBaseTable extends BaseTable {

		/**
		 * ヘッダー描画用コンポ(結合列対応)
		 */
		@Override
		protected JTableHeader createDefaultTableHeader() {
			return new TGroupableTableHeader(columnModel);
		}
	}
}
