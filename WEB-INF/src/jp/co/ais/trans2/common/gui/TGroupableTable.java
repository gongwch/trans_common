package jp.co.ais.trans2.common.gui;

import javax.swing.table.*;

/**
 * VC�����w�b�_�[�e�[�u��
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
	 * VC�����w�b�_�[�e�[�u��
	 */
	public class VCBaseTable extends BaseTable {

		/**
		 * �w�b�_�[�`��p�R���|(������Ή�)
		 */
		@Override
		protected JTableHeader createDefaultTableHeader() {
			return new TGroupableTableHeader(columnModel);
		}
	}
}
