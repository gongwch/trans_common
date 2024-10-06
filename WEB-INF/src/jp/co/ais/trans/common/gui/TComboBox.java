package jp.co.ais.trans.common.gui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * JComboBoxに、タブ順インターフェイスを追加したComboBox.
 */
public class TComboBox extends JComboBox implements TInterfaceTabControl, TTableComponent {

	/**  */
	protected int tabControlNo = -1;

	/**  */
	protected boolean isTabEnabled = true;

	/**  */
	protected int rowIndex = -1;

	/** TableCellEditor利用か */
	protected boolean tableCellEditor = false;

	/**
	 * Constructor.
	 */
	public TComboBox() {
		super();

		// ファンクションキー用リスナ
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				handleKeyPressed(evt);
			}
		});

		// フォーカスがあるとき全選択状態
		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent evt) {
				handleFocusGained();
			}
		});

		// マウスホイールでの値変更
		this.addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {
				JComboBox source = (JComboBox) e.getSource();
				if (!source.hasFocus()) {
					return;
				}

				int ni = source.getSelectedIndex() + e.getWheelRotation();
				if (ni >= 0 && ni < source.getItemCount()) {
					source.setSelectedIndex(ni);
				}
			}
		});

	}

	/**
	 * ファンクションキー処理.
	 * 
	 * @param evt キーイベント
	 */
	protected void handleKeyPressed(KeyEvent evt) {

		TGuiUtil.transferFocusByEnterKey(this, evt);
		TGuiUtil.dispatchPanelBusinessFunctionKey(this, evt);
	}

	/**
	 * focus gained処理.
	 */
	protected void handleFocusGained() {

		this.getEditor().selectAll();
	}

	/**
	 * add key listener
	 * 
	 * @param listener
	 */
	@Override
	public void addKeyListener(KeyListener listener) {

		this.getEditor().getEditorComponent().addKeyListener(listener);
		super.addKeyListener(listener);
	}

	/**
	 * remove focus listener
	 * 
	 * @param listener
	 */
	@Override
	public void removeKeyListener(KeyListener listener) {

		this.getEditor().getEditorComponent().removeKeyListener(listener);
		super.removeKeyListener(listener);
	}

	/**
	 * add focus listener
	 * 
	 * @param listener
	 */
	@Override
	public void addFocusListener(FocusListener listener) {

		this.getEditor().getEditorComponent().addFocusListener(listener);
		super.addFocusListener(listener);
	}

	/**
	 * remove focus listener
	 * 
	 * @param listener
	 */
	@Override
	public void removeFocusListener(FocusListener listener) {

		this.getEditor().getEditorComponent().removeFocusListener(listener);
		super.removeFocusListener(listener);
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#getTabControlNo()
	 */
	public int getTabControlNo() {
		return this.tabControlNo;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		this.tabControlNo = no;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#isTabEnabled()
	 */
	public boolean isTabEnabled() {
		return this.isTabEnabled;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#setTabEnabled(boolean)
	 */
	public void setTabEnabled(boolean bool) {
		this.isTabEnabled = bool;
	}

	/**
	 * 値(キー)・表示文字を追加
	 * 
	 * @param text テキスト
	 * @param value 値
	 */
	public void addTextValueItem(Object value, String text) {

		addItem(new TTextValue(text, value));
	}

	/**
	 * リスト内要素全ての値を追加
	 * 
	 * @param itemList 値リスト
	 */
	public void addItemList(List<Object> itemList) {
		for (Object obj : itemList) {
			addItem(obj);
		}
	}

	/**
	 * 値リストのデータモデル設定
	 * 
	 * @param itemList データモデル
	 */
	public void setModel(List<Object> itemList) {
		DefaultComboBoxModel model = new DefaultComboBoxModel();

		for (Object value : itemList) {
			model.addElement(value);
		}

		setModel(model);
	}

	/**
	 * 値リストのデータモデル設定
	 * 
	 * @param itemList データモデル
	 */
	public void setModel(Object[] itemList) {
		setModel(new DefaultComboBoxModel(itemList));
	}

	/**
	 * 値(キー)・表示文字のデータモデル設定
	 * 
	 * @param itemMap データモデル
	 */
	public void setModel(Map<Object, String> itemMap) {
		DefaultComboBoxModel model = new DefaultComboBoxModel();

		for (Object key : itemMap.keySet()) {
			model.addElement(new TTextValue(itemMap.get(key), key));
		}

		setModel(model);
	}

	/**
	 * 値(キー)・表示文字のデータモデル設定
	 * 
	 * @param texts 表示文字リスト
	 * @param values 値リスト
	 */
	public void setModel(String[] texts, Object[] values) {
		DefaultComboBoxModel model = new DefaultComboBoxModel();

		for (int i = 0; i < texts.length; i++) {
			model.addElement(new TTextValue(texts[i], values[i]));
		}

		setModel(model);
	}

	/**
	 * 選択されているアイテムを取得
	 * 
	 * @return アイテム
	 */
	@Override
	public Object getSelectedItem() {

		Object item = super.getSelectedItem();

		if (item instanceof TTextValue) {
			return ((TTextValue) item).getText();
		}

		return item;
	}

	/**
	 * 選択されている値を取得
	 * 
	 * @return 値
	 */
	public Object getSelectedItemValue() {
		Object item = super.getSelectedItem();

		if (item instanceof TTextValue) {
			return ((TTextValue) item).getValue();
		}

		return item;
	}

	/**
	 * 選択されているTextValueを取得<br>
	 * 値がTextValueではない場合、nullを返す.
	 * 
	 * @return TextValue
	 */
	public TTextValue getSelectedTextValue() {
		Object item = super.getSelectedItem();

		if (item instanceof TTextValue) {
			return ((TTextValue) item);
		}

		return null;
	}

	/**
	 * 表示文字で選択を変更する
	 * 
	 * @param text 表示文字
	 */
	public void setSelectedText(String text) {
		if (text == null) {
			return;
		}

		ComboBoxModel model = super.getModel();

		// 登録Itemが存在しない場合、処理なし。
		if (model == null || model.getSize() == 0) {
			return;
		}

		// 指定の文字列と同じItem持つ行を選択状態にする。
		for (int i = 0; i < model.getSize(); i++) {
			Object item = model.getElementAt(i);

			if (item instanceof TTextValue) {
				String txt = ((TTextValue) item).getText();

				if (txt != null && text.equals(txt)) {
					super.setSelectedItem(item);
					return;
				}

			} else {
				super.setSelectedItem(text);
				return;
			}
		}
	}

	/**
	 * 値を指定して選択を変更する
	 * 
	 * @param value 値
	 */
	public void setSelectedItemValue(Object value) {
		if (value == null) {
			return;
		}

		ComboBoxModel model = super.getModel();

		// 登録Itemが存在しない場合、処理なし。
		if (model == null || model.getSize() == 0) {
			return;
		}

		// 指定の文字列と同じItemValue持つ行を選択状態にする。
		for (int i = 0; i < model.getSize(); i++) {
			Object item = model.getElementAt(i);

			if (item instanceof TTextValue) {
				Object v = ((TTextValue) item).getValue();

				if (v != null && value.equals(v)) {
					super.setSelectedItem(item);
					return;
				}

			} else {
				super.setSelectedItem(value);
				return;
			}
		}
	}

	/**
	 * 表示文字を全て返す.
	 * 
	 * @return リスト
	 */
	public List<String> getTextList() {

		List<String> list = new LinkedList<String>();

		ComboBoxModel model = super.getModel();

		if (model == null || model.getSize() == 0) {
			return list;
		}

		// 指定の文字列と同じItemValue持つ行を選択状態にする。
		for (int i = 0; i < model.getSize(); i++) {
			Object item = model.getElementAt(i);

			if (item instanceof TTextValue) {
				String text = ((TTextValue) item).getText();
				list.add(text);

			} else {
				list.add(item.toString());
			}
		}

		return list;
	}

	/**
	 * 指定文字が表示リストに含まれているかどうか
	 * 
	 * @param text 文字
	 * @return true:含む
	 */
	public boolean containsText(String text) {
		return getTextList().contains(text);
	}

	/**
	 * 値を全て返す.
	 * 
	 * @return リスト
	 */
	public List<Object> getItemValueList() {

		List<Object> list = new LinkedList<Object>();

		ComboBoxModel model = super.getModel();

		if (model == null || model.getSize() == 0) {
			return list;
		}

		// 指定の文字列と同じItemValue持つ行を選択状態にする。
		for (int i = 0; i < model.getSize(); i++) {
			Object item = model.getElementAt(i);

			if (item instanceof TTextValue) {
				Object value = ((TTextValue) item).getValue();
				list.add(value);

			} else {
				list.add(item);
			}
		}

		return list;
	}

	/**
	 * 指定値がリストに含まれているかどうか
	 * 
	 * @param value 値
	 * @return true:含む
	 */
	public boolean containsValue(Object value) {
		return getItemValueList().contains(value);
	}

	/**
	 * 表示文字と値を保持するクラス
	 */
	public static class TTextValue {

		/** 表示文字 */
		protected String view;

		/** 値 */
		protected Object value;

		/**
		 * コンストラクタ
		 * 
		 * @param text 表示文字
		 */
		public TTextValue(String text) {
			this(text, text);
		}

		/**
		 * コンストラクタ
		 * 
		 * @param text 表示文字
		 * @param value 値
		 */
		public TTextValue(String text, Object value) {
			this.view = text;
			this.value = value;
		}

		/**
		 * 表示文字取得
		 * 
		 * @return 表示文字
		 */
		public String getText() {
			return view;
		}

		/**
		 * 表示文字設定
		 * 
		 * @param text 表示文字
		 */
		public void setText(String text) {
			this.view = text;
		}

		/**
		 * 値取得
		 * 
		 * @return 値
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * 値設定
		 * 
		 * @param value 値
		 */
		public void setValue(Object value) {
			this.value = value;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return this.view;
		}

		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {

			if (obj == null || !(obj instanceof TTextValue)) {
				return false;
			}

			if (super.equals(obj)) {
				return true;
			}

			TTextValue target = (TTextValue) obj;
			return equalsValue(target.value);
		}

		/**
		 * 値の比較
		 * 
		 * @param targetValue 対象値
		 * @return true:同一
		 */
		public boolean equalsValue(Object targetValue) {
			if (targetValue == null) {
				return this.value == null;

			} else if (this.value == null) {
				return false;
			}

			return this.value.equals(targetValue);
		}
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellRenderer(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellRenderer getCellRenderer(TTable tbl) {
		return new TComboBoxRenderer(this, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellEditor getCellEditor(TTable tbl) {

		tableCellEditor = true;
		return new TComboBoxEditor(this, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getRowIndex()
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#setRowIndex(int)
	 */
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * 水平方向のalign getter
	 * 
	 * @return 水平方向のalign
	 */
	public int getDefaultCellRendererHorizontalAlignment() {
		return SwingConstants.CENTER;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#isTableCellEditor()
	 */
	public boolean isTableCellEditor() {
		return tableCellEditor;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#setTableCellEditor(boolean)
	 */
	public void setTableCellEditor(boolean tableCellEditor) {
		this.tableCellEditor = tableCellEditor;
	}
}
