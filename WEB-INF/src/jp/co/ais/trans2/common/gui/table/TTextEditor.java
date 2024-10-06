package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * テキスト編集用セルエディタ
 */
public class TTextEditor extends TBaseCellEditor {

	/**
	 * コンストラクタ
	 * 
	 * @param editor
	 * @param table
	 */
	public TTextEditor(TTextField editor, TTable table) {

		super(editor, table);

		// エディタ用コンポーネントの設定
		editor.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));

	}

	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean,
	 *      int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		// コンポーネントの取得
		TTextField editor = (TTextField) getComponent();

		// 対象行番号の設定
		editor.setRowIndex(row);

		// 値の設定
		editor.setValue(value);
		editor.pushOldText(); // インクリメントサーチバグ対応

		return editor;
	}

}