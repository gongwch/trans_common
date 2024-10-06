package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * コンボボックス用セルレンダラ
 */
public class TComboBoxRenderer extends TBaseCellRenderer {

	/** レンダラ用コンポーネント */
	protected final TComboBox renderer;

	/**
	 * コンストラクタ
	 * 
	 * @param cmbBox
	 * @param table
	 */
	public TComboBoxRenderer(TComboBox cmbBox, TTable table) {
		super(table);

		// レンダラ用コンポーネントを設定
		this.renderer = cmbBox;
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
	 *      boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		TComboBox box = new TComboBox();

		// 未選択状態で空白表示にしない場合はコメントを解除
		// if (Util.isNullOrEmpty(value) && renderer.getModel().getSize() != 0) {
		// Object element = renderer.getModel().getElementAt(0);
		// box.addItem(element.toString());
		//
		// } else {
		box.addItem((value == null) ? "" : value.toString());
		// }

		// 色の設定
		box.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));
		box.setForeground(tbl.getCellFontColor(isSelected, hasFocus));

		// ボーダー設定
		setBorder(box, isSelected, hasFocus);

		return box;
	}

}