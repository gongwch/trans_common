package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans.common.gui.*;

/**
 * Table内部クラス
 */
public class BaseTable extends JTable implements TInterfaceTabControl {

	/** タブ順 */
	protected int tabControlNo = -1;

	/** タブ遷移可否 */
	protected boolean isTabEnabled = true;

	/** 行Index */
	protected int editRowIndex = 0;

	/** 列Index */
	protected int editColumnIndex = 0;

	/** 行ヘッダーの「*」表示行番号 */
	protected int rowHeaderStarIndex = -1;

	/** 行タイトルリスト */
	protected List<String> rowHeaderMessage = null;

	/** 行背景色リスト */
	protected List<Color> rowHeaderBackground = null;

	/** 行文字色リスト */
	protected List<Color> rowHeaderForeground = null;

	/**  */
	protected TTableAdapter adapter;

	/**
	 * コンストラクター
	 */
	public BaseTable() {
		//
	}

	/**
	 * コンストラクター
	 * 
	 * @param model
	 */
	public BaseTable(TableModel model) {
		super(model);
	}

	/**
	 * コンストラクター
	 * 
	 * @param adapter
	 */
	public BaseTable(TTableAdapter adapter) {
		this.adapter = adapter;
	}

	/**
	 * @see javax.swing.JTable#createDefaultTableHeader()
	 */
	@Override
	protected JTableHeader createDefaultTableHeader() {
		return createTableHeader();
	}

	/**
	 * @return テーブルヘッダー表現
	 */
	protected JTableHeader createTableHeader() {
		return new TTableHeader(columnModel, this);
	}

	/**
	 * 値の変更をアダプターに通知する仕組みを追加
	 * 
	 * @see javax.swing.JTable#createDefaultDataModel()
	 */
	@Override
	protected TableModel createDefaultDataModel() {

		return new DefaultTableModel() {

			@Override
			public void setValueAt(Object after, int row, int column) {
				Object before = getValueAt(row, column);

				super.setValueAt(after, row, column);

				// 変更通知
				if ((before != null || after != null) && adapter.isValueChanged(before, after, row, column)) {
					adapter.changedValueAt(before, after, row, column);
				}
			}
		};
	}

	/**
	 * 移動後、即編集
	 * 
	 * @see javax.swing.JTable#changeSelection(int, int, boolean, boolean)
	 */
	@Override
	public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
		if (!super.isEditing()) {
			editCellAt(rowIndex, columnIndex, new EventObject(this));
		}

		super.changeSelection(rowIndex, columnIndex, toggle, extend);
	}

	/**
	 * 非エディット状態の入力をENTERで確定させる
	 * 
	 * @see javax.swing.JTable#processKeyBinding(javax.swing.KeyStroke, java.awt.event.KeyEvent, int, boolean)
	 */
	@Override
	protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
		boolean retValue = super.processKeyBinding(ks, e, condition, pressed);

		if (KeyStroke.getKeyStroke('\t').equals(ks) || KeyStroke.getKeyStroke('\n').equals(ks)) {
			return retValue;
		}

		if (getInputContext().isCompositionEnabled() && !isEditing() && !pressed && !ks.isOnKeyRelease()) {
			int selectedRow = getSelectedRow();
			int selectedColumn = getSelectedColumn();
			if (selectedRow != -1 && selectedColumn != -1 && !editCellAt(selectedRow, selectedColumn)) {
				return retValue;
			}
		}

		return retValue;
	}

	/**
	 * @see javax.swing.JTable#updateUI()
	 */
	@Override
	public void updateUI() {
		int oldRowHeight = rowHeight;
		super.updateUI();
		this.setRowHeight(oldRowHeight);
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
	 * 
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

	@Override
	public boolean isCellEditable(int row, int column) {
		return adapter.isCellEditable(row, column);
	}

	/**
	 * editColumnIndexを取得する。
	 * 
	 * @return int editColumnIndex
	 */
	public int getEditColumnIndex() {
		return editColumnIndex;
	}

	/**
	 * editRowIndexを取得する。
	 * 
	 * @return int editRowIndex
	 */
	public int getEditRowIndex() {
		return editRowIndex;
	}

	/**
	 * 行ヘッダーの「*」表示行番号の取得
	 * 
	 * @return rowHeaderStarIndex 行ヘッダーの「*」表示行番号
	 */
	public int getRowHeaderStarIndex() {
		return rowHeaderStarIndex;
	}

	/**
	 * 行ヘッダーの「*」表示行番号の設定
	 * 
	 * @param rowHeaderStarIndex 行ヘッダーの「*」表示行番号
	 */
	public void setRowHeaderStarIndex(int rowHeaderStarIndex) {
		this.rowHeaderStarIndex = rowHeaderStarIndex;
	}

	/**
	 * 行タイトルリストの取得
	 * 
	 * @return rowHeaderMessage 行タイトルリスト
	 */
	public List<String> getRowHeaderMessage() {
		return rowHeaderMessage;
	}

	/**
	 * 行タイトルリストの設定
	 * 
	 * @param rowHeaderMessage 行タイトルリスト
	 */
	public void setRowHeaderMessage(List<String> rowHeaderMessage) {
		this.rowHeaderMessage = rowHeaderMessage;
	}

	/**
	 * 背景色リストの取得
	 * 
	 * @return rowHeaderBackground 背景色リスト
	 */
	public List<Color> getRowHeaderBackground() {
		return rowHeaderBackground;
	}

	/**
	 * 背景色リストの設定
	 * 
	 * @param rowHeaderBackground 背景色リスト
	 */
	public void setRowHeaderBackground(List<Color> rowHeaderBackground) {
		this.rowHeaderBackground = rowHeaderBackground;
	}

	/**
	 * 文字色リストの取得
	 * 
	 * @return rowHeaderMessage 文字色リスト
	 */
	public List<Color> getRowHeaderForeground() {
		return rowHeaderForeground;
	}

	/**
	 * 文字色リストの設定
	 * 
	 * @param rowHeaderForeground 文字色リスト
	 */
	public void setRowHeaderForeground(List<Color> rowHeaderForeground) {
		this.rowHeaderForeground = rowHeaderForeground;
	}

	/**
	 * @see javax.swing.JComponent#processMouseEvent(java.awt.event.MouseEvent)
	 */
	@Override
	protected void processMouseEvent(MouseEvent e) {
		super.processMouseEvent(e);
	}

	@Override
	public boolean editCellAt(int row, int column, EventObject e) {

		if (!adapter.beforeEditCellAt(row, column)) {
			// 処理中断判定
			return false;
		}

		if (!adapter.beforeEditCellAt(row, column, e)) {
			// 処理中断判定
			return false;
		}

		return super.editCellAt(row, column, e);
	}

	@Override
	public String getToolTipText(MouseEvent ev) {
		if (adapter == null) {
			return super.getToolTipText(ev);
		}

		return adapter.getToolTipText(ev);
	}
}
