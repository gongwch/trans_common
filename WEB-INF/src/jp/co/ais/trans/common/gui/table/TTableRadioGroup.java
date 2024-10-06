package jp.co.ais.trans.common.gui.table;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * TTable用Radioボタングループ
 */
// TODO 選択行になった際、文字色が反転しない
public class TTableRadioGroup extends TPanel {

	/** ボタンリスト */
	private List<TRadioButton> list;

	/** 選択Index番号 */
	private int index;

	/**
	 * コンストラクタ.
	 * 
	 * @param table 連動スプレッド
	 * @param rowIndex スプレッド内の行番号
	 */
	public TTableRadioGroup(TTable table, int rowIndex) {
		this(table, rowIndex, 2);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param table 連動スプレッド
	 * @param rowIndex スプレッド内の行番号
	 * @param number RadioButtonの数
	 */
	public TTableRadioGroup(TTable table, int rowIndex, int number) {
		this(table, rowIndex, new String[number]);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param table 連動スプレッド
	 * @param rowIndex スプレッド内の行番号
	 * @param words 表示文字(RadioButtonの数)
	 */
	public TTableRadioGroup(TTable table, int rowIndex, String... words) {
		list = new ArrayList<TRadioButton>(words.length);

		this.setLayout(new GridBagLayout());
		this.setFocusable(false);
		this.setOpaque(false);

		ButtonGroup group = new ButtonGroup();

		for (int i = 0; i < words.length; i++) {

			TRadioButton radio = new TRadioButton(rowIndex);
			radio.setText(Util.avoidNull(words[i]));
			radio.setOpaque(false);
			radio.setFocusable(false);

			if (i == 0) {
				radio.setSelected(true);
			}

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = i;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 0, 0);
			this.add(radio, gbc);

			group.add(radio);

			list.add(radio);
		}

		SelectedListener listener = new SelectedListener(table);

		for (TRadioButton radio : list) {
			radio.addItemListener(listener);
		}
	}

	/**
	 * 指定ラジオボタンのIndex番号変更.
	 * 
	 * @param radio 対象Radioボタン
	 */
	private void setSelectedIndex(TRadioButton radio) {

		this.index = list.indexOf(radio);
	}

	/**
	 * 指定Indexのボタンを選択状態に変更.<br>
	 * 不正番号指定時は0に設定.
	 * 
	 * @param index 番号
	 */
	public void setSelectedIndex(int index) {
		this.index = (index >= list.size() || index < 0) ? 0 : index;

		list.get(this.index).setSelected(true);
	}

	/**
	 * 現在の選択ボタンINDEXを取得.
	 * 
	 * @return 選択ボタンINDEX
	 */
	public int getSelectedIndex() {
		return index;
	}

	/**
	 * 次のラジオボタンに選択移動する.
	 */
	public void nextSelected() {

		if (index + 1 >= list.size()) {
			index = 0;

		} else {
			index++;
		}

		list.get(index).setSelected(true);
	}

	/**
	 * スプレッド用リスナー
	 */
	private class SelectedListener implements ItemListener {

		/** 対象スプレッド */
		private TTable table;

		/**
		 * コンストラクタ.
		 * 
		 * @param table 対象スプレッド
		 */
		public SelectedListener(TTable table) {
			this.table = table;
		}

		/**
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */
		public void itemStateChanged(ItemEvent e) {
			TRadioButton radio = (TRadioButton) e.getSource();
			int rowNo = table.isSort() ? table.getCurrentRow() : radio.getIndex();

			table.requestFocusInWindow();
			table.setCurrentCell(rowNo, 0);
			table.setRowSelection(rowNo, rowNo);

			setSelectedIndex(radio);
		}
	}
}
