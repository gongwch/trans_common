package jp.co.ais.trans2.common.gui.table;

import java.util.regex.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.text.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * 基盤セルレンダラ
 */
public class TBaseCellRenderer extends DefaultTableCellRenderer {

	/** テーブル */
	protected TTable tbl;

	/**
	 * コンストラクタ
	 * 
	 * @param tbl テーブル
	 */
	public TBaseCellRenderer(TTable tbl) {
		super();
		this.tbl = tbl;
	}

	/**
	 * ボーダーセット
	 * 
	 * @param comp コンポーネント
	 * @param isSelected true:選択
	 * @param hasFocus true:フォーカス
	 */
	protected void setBorder(JComponent comp, boolean isSelected, boolean hasFocus) {

		if (hasFocus) {
			Border border = null;

			if (isSelected) {
				border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
			}

			if (border == null) {
				border = UIManager.getBorder("Table.focusCellHighlightBorder");
			}
			comp.setBorder(border);

		} else {
			comp.setBorder(BorderFactory.createEmptyBorder());
		}

		if (tbl == null) {
			return;
		}

		Pattern pattern = tbl.getHighlighterPattern();

		if (comp instanceof JTextArea) {
			Highlighter l = ((JTextArea) comp).getHighlighter();
			match(((JTextArea) comp).getText(), pattern, l);

		} else if (comp instanceof JTextField) {
			Highlighter l = ((JTextField) comp).getHighlighter();
			match(((JTextField) comp).getText(), pattern, l);

		} else if (comp instanceof TLabelField) {
			Highlighter l = ((TLabelField) comp).getField().getHighlighter();
			match(((TLabelField) comp).getValue(), pattern, l);

		} else if (comp instanceof TLabelNumericField) {
			Highlighter l = ((TLabelNumericField) comp).getField().getHighlighter();
			match(((TLabelNumericField) comp).getValue(), pattern, l);

		} else if (comp instanceof TChart) {
			// Highlighter l = ((TChart) comp).getHighlighter();
			// match(((TChart) comp).getValue(), pattern, l);

		}
	}

	/**
	 * @param txt
	 * @param pattern
	 * @param l
	 */
	protected void match(String txt, Pattern pattern, Highlighter l) {

		if (Util.isNullOrEmpty(txt) || pattern == null || l == null) {
			return;
		}

		Matcher matcher = pattern.matcher(txt.toLowerCase());

		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();

			try {
				l.addHighlight(start, end, TSearchField.DefaultPainter);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}