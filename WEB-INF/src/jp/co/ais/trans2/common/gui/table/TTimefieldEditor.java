package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * TZ編集用セルエディタ
 */
public class TTimefieldEditor extends TBaseCellEditor {

	/** 年月日 */
	protected static final String BASE_YMD = "2000/01/01 ";

	/** スプレッドのフォントを使う */
	protected boolean useTableFont = false;

	/**
	 * コンストラクタ
	 * 
	 * @param editor
	 * @param table
	 */
	public TTimefieldEditor(TTimeField editor, TTable table) {
		super(editor, table);
	}

	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean,
	 *      int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		// コンポーネントの取得
		TTimeField editor = (TTimeField) getComponent();
		editor.setBorder(BorderFactory.createEmptyBorder());

		if (this.isUseTableFont()) {
			editor.setFont(table.getFont());
		}

		// 対象行番号の設定
		editor.setRowIndex(row);

		// 値の設定
		setValue(editor, value);

		if (editor.isUseTablePaste()) {
			TTablePasteUtil.handleKeyEvent(editor, tbl, row, column, editor.getCellTypes());
		}

		return editor;
	}

	/**
	 * 値の設定
	 * 
	 * @param txt
	 * @param value
	 */
	protected void setValue(TTimeField txt, Object value) {

		if (Util.isNullOrEmpty(value)) {
			txt.clear();
		}
		try {
			String hm = Util.avoidNull(value);
			// HH:mm 形式以外はエラーとする。
			if (hm.length() != 5) {
				return;
			}

			Date date = null;
			date = DateUtil.toYMDHMDate(BASE_YMD + hm);
			txt.setText(DateUtil.toHMString(date));
		} catch (ParseException e) {
			return;
		}
	}

	/**
	 * スプレッドのフォントを使うの取得
	 * 
	 * @return useTableFont スプレッドのフォントを使う
	 */
	public boolean isUseTableFont() {
		return useTableFont;
	}

	/**
	 * スプレッドのフォントを使うの設定
	 * 
	 * @param useTableFont スプレッドのフォントを使う
	 */
	public void setUseTableFont(boolean useTableFont) {
		this.useTableFont = useTableFont;
	}

}