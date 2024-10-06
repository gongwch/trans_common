package jp.co.ais.trans2.common.gui;

import java.awt.event.*;
import java.util.*;

/**
 * JTableとのAdapter
 * 
 * @author AIS
 */
@SuppressWarnings("unused")
public class TTableAdapter {

	/** 元テーブル */
	protected BaseTable table;

	/**
	 * テーブル取得
	 * 
	 * @return テーブル
	 */
	public BaseTable getTable() {
		return table;
	}

	/**
	 * テーブル設定
	 * 
	 * @param table テーブル
	 */
	public void setTable(BaseTable table) {
		this.table = table;
		this.table.adapter = this;
	}

	/**
	 * 指定セルのエディット可否<br>
	 * <br>
	 * <b>■注意■■■■■■■■■■■■■■■■■■■■■■<br>
	 * ■override時にconvertXXXXIndexToModelを忘れず<br>
	 * ■■■■■■■■■■■■■■■■■■■■■■■■■</b>
	 * 
	 * @param row 行
	 * @param column 列
	 * @return true:可
	 */
	public boolean isCellEditable(int row, int column) {
		return table.getModel().isCellEditable(table.convertRowIndexToModel(row),
			table.convertColumnIndexToModel(column));
	}

	/**
	 * 指定値の変更確認
	 * 
	 * @param before 変更前の値
	 * @param after 変更後の値
	 * @param row 行
	 * @param column 列
	 * @return true:変更あり
	 */
	public boolean isValueChanged(Object before, Object after, int row, int column) {

		if ((before != null && after == null) || (before == null && after != null)) {
			return true;
		}

		return !before.toString().equals(after.toString());
	}

	/**
	 * 値が変更された場合に呼び出される
	 * 
	 * @param before 変更前の値
	 * @param after 変更後の値
	 * @param row 行
	 * @param column 列
	 */
	public void changedValueAt(Object before, Object after, int row, int column) {
		// Override用
	}

	/**
	 * ヘッダーをクリック前
	 * 
	 * @param column 列
	 */
	public void beforeHeaderClicked(int column) {
		// Override用
	}

	/**
	 * ヘッダーをクリック後
	 * 
	 * @param column 列
	 */
	public void afterHeaderClicked(int column) {
		// Override用
	}

	/**
	 * 行番号をクリック後
	 */
	public void afterRowHeaderClicked() {
		// Override用
	}

	/**
	 * 行選択状態変更
	 */
	public void rowSelectionChanged() {
		// Override用
	}

	/**
	 * 列情報保存時処理
	 */
	public void fireTableColumnChanged() {
		// Override用
	}

	/**
	 * MTableのフッター座標変更処理
	 */
	public void fireMTableFooterChanged() {
		// Override用
	}

	/**
	 * セル編集前の処理
	 * 
	 * @param row
	 * @param column
	 * @return true:編集処理を行う、false:処理中断
	 */
	public boolean beforeEditCellAt(int row, int column) {
		return true;
	}

	/**
	 * セル編集前の処理
	 * 
	 * @param row
	 * @param column
	 * @param e
	 * @return true:編集処理を行う、false:処理中断
	 */
	public boolean beforeEditCellAt(int row, int column, EventObject e) {
		return true;
	}

	/**
	 * @param event
	 * @return Tool tips
	 */
	public String getToolTipText(MouseEvent event) {
		return null;
	}

	/**
	 * ヘッダーTooltip取得
	 * 
	 * @param event
	 * @return Tool tips
	 */
	public String getHeaderToolTipText(MouseEvent event) {
		return null;
	}
}
