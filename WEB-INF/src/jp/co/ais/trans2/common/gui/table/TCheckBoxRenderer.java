package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * チェックボックス用セルレンダラ
 */
public class TCheckBoxRenderer extends TBaseCellRenderer {

	/** レンダラ用コンポーネント */
	protected final TCheckBox renderer;

	/** ブランク表示Adapter */
	protected TComponentViewAdapter viewAdapter;

	/**
	 * コンストラクタ
	 * 
	 * @param table
	 */
	public TCheckBoxRenderer(TTable table) {

		this(new TCheckBox(), table);

	}

	/**
	 * コンストラクタ
	 * 
	 * @param chkBox
	 * @param table
	 */
	public TCheckBoxRenderer(TCheckBox chkBox, TTable table) {

		super(table);

		// レンダラ用コンポーネントを設定
		this.renderer = chkBox;
		this.renderer.setHorizontalAlignment(SwingConstants.CENTER);
		this.renderer.setOpaque(true);
	}

	/**
	 * ブランク表示アダプター
	 * 
	 * @param adapter アダプター
	 */
	public void setViewAdapter(TComponentViewAdapter adapter) {
		this.viewAdapter = adapter;
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
	 *      boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		if (viewAdapter != null) {

			if (viewAdapter.isBlank(row, column)) {
				TTextField label = new TTextField();
				label.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));
				setBorder(label, isSelected, hasFocus);

				return label;
			}

			// 使用不可の場合、使用不可コンポを戻る
			if (!viewAdapter.isEnable(row, column)) {

				// 値の設定
				renderer.setEnabled(false);
				renderer.setSelected(false);

			} else {
				renderer.setEnabled(true);
			}
		}

		// 値の設定
		if (value == null) {
			renderer.setSelected(false);
		} else {
			renderer.setSelected(BooleanUtil.toBoolean(value.toString()));
		}

		// 色の設定
		this.renderer.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));
		this.renderer.setForeground(tbl.getCellFontColor(isSelected, hasFocus));

		// ボーダー設定
		setBorder(renderer, isSelected, hasFocus);

		return renderer;
	}

	/**
	 * レンダラ元
	 * 
	 * @return レンダラ元
	 */
	public TCheckBox getRenderer() {
		return renderer;
	}
}