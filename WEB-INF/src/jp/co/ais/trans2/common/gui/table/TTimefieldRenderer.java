package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * TZ編集用セルレンダラ
 */
public class TTimefieldRenderer extends TBaseCellRenderer {

	/** レンダラ用コンポーネント */
	protected final TTimeField renderer;

	/** スプレッドのフォントを使う */
	protected boolean useTableFont = false;

	/** 年月日 */
	protected static final String BASE_YMD = "2000/01/01 ";

	/**
	 * コンストラクタ
	 * 
	 * @param renderer
	 * @param table
	 */
	public TTimefieldRenderer(TTimeField renderer, TTable table) {

		super(table);

		// レンダラ用コンポーネントの設定
		this.renderer = renderer;
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
	 *      boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		TTimeField txt = new TTimeField();
		txt.setTableCellEditor(true);

		txt.setBorder(BorderFactory.createEmptyBorder());

		if (this.isUseTableFont()) {
			txt.setFont(table.getFont());
		}

		// 値の設定
		setValue(txt, value);

		// 色の設定
		txt.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));

		if (isSelected) {
			txt.setForeground(tbl.getCellFontColor(isSelected, hasFocus));
		}

		// ボーダー設定
		setBorder(txt, isSelected, hasFocus);

		return txt;
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