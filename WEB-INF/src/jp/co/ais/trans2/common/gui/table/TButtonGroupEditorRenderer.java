package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * ラジオボタングループ用セルエディタ・レンダラ
 */
public class TButtonGroupEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

	/** エディタ用コンポーネント */
	protected final RadioButtonPanel editor;

	/** レンダラ用コンポーネント */
	protected final RadioButtonPanel renderer;

	/** ボタングループ 項目 */
	protected final String[] items;

	/**  */
	protected TTable tbl;

	/**
	 * コンストラクタ
	 * 
	 * @param items ボタングループ項目
	 * @param tbl
	 */
	public TButtonGroupEditorRenderer(String[] items, TTable tbl) {
		this(items, false);
		this.tbl = tbl;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param items ボタングループ項目
	 * @param isViewTitle タイトルを表示するかどうか<br>
	 *            ボタンだけ表示の場合はFalse
	 */
	public TButtonGroupEditorRenderer(String[] items, boolean isViewTitle) {

		super();
		this.items = items;

		// エディタ用コンポーネントのインスタンス化
		this.editor = new RadioButtonPanel(isViewTitle, items);

		// レンダラ用コンポーネントのインスタンス化
		this.renderer = new RadioButtonPanel(isViewTitle, items);
	}

	/**
	 * ボタン押下時の処理
	 * 
	 * @param comp エディタ OR レンダラ
	 * @param value 選択値
	 */
	protected void setSelectedButton(RadioButtonPanel comp, Object value) {

		// 選択値と一致するラジオボタンをセレクト状態にする
		for (int i = 0; i < items.length; i++) {
			if (items[i].equals(value)) {
				comp.buttons[i].setSelected(true);
			}
		}
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
	 *      boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		setSelectedButton(renderer, value);

		// 色の設定
		this.renderer.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));
		this.renderer.setForeground(tbl.getCellFontColor(isSelected, hasFocus));

		return renderer;
	}

	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean,
	 *      int, int)
	 */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		setSelectedButton(editor, value);

		// 背景色の設定
		editor.setBackground(tbl.getBackgroundColor(row, isSelected, isSelected));
		editor.setForeground(tbl.getSelectedFontColor());

		return editor;
	}

	/**
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	public Object getCellEditorValue() {
		return editor.group.getSelection().getActionCommand();
	}

	/**
	 * エディタ・レンダラ用コンポーネント
	 */
	class RadioButtonPanel extends JPanel {

		/** ボタングループ */
		public final ButtonGroup group = new ButtonGroup();

		/** ボタンリスト */
		public final JRadioButton[] buttons;

		/**
		 * コンストラクタ
		 * 
		 * @param isViewTitle タイトルを表示するかどうか<br>
		 *            ボタンだけ表示の場合はFalse
		 * @param items 表示項目(RadioButtonの数)
		 */
		public RadioButtonPanel(boolean isViewTitle, String... items) {

			this.setLayout(new GridBagLayout());
			this.setFocusable(false);

			// 表示項目の数だけ、ラジオボタンをインスタンス化する
			buttons = new JRadioButton[items.length];
			for (int i = 0; i < items.length; i++) {

				JRadioButton radio = new JRadioButton();

				// Trueの場合はボタンタイトルを設定
				if (isViewTitle) {
					radio.setText(Util.avoidNull(items[i]));
				}

				radio.setOpaque(false);
				radio.setFocusable(false);

				if (i == 0) {
					radio.setSelected(true);
				}

				radio.setActionCommand(items[i]);

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = i;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 5, 0, 5);
				this.add(radio, gbc);
				buttons[i] = radio;
				group.add(radio);

			}

		}
	}
}