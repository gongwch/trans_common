package jp.co.ais.trans2.common.excel;

import java.math.*;
import java.util.*;

import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.util.*;

/**
 * エクセルのセル
 */
public class TExcelCell {

	/** Cell */
	public Cell cell;

	/** ブック */
	public TExcel book;

	/** 行番号 */
	protected int rowIndex = -1;

	/** 列番号 */
	protected int columnIndex = -1;

	/**
	 * コンストラクター
	 * 
	 * @param book
	 * @param cell
	 */
	public TExcelCell(TExcel book, Cell cell) {
		this.book = book;
		this.cell = cell;

		if (cell != null) {
			this.rowIndex = cell.getRowIndex();
			this.columnIndex = cell.getColumnIndex();
		}
	}

	/**
	 * 行番号の取得
	 * 
	 * @return rowIndex 行番号
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * 行番号の設定
	 * 
	 * @param rowIndex 行番号
	 */
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * 列番号の取得
	 * 
	 * @return columnIndex 列番号
	 */
	public int getColumnIndex() {
		return columnIndex;
	}

	/**
	 * 列番号の設定
	 * 
	 * @param columnIndex 列番号
	 */
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	/**
	 * 列番号取得
	 * 
	 * @return 列番号
	 */
	public int getCellColumnIndex() {
		return cell.getColumnIndex();
	}

	/**
	 * 行番号取得
	 * 
	 * @return 行番号
	 */
	public int getCellRowIndex() {
		return cell.getRowIndex();
	}

	/**
	 * 行取得
	 * 
	 * @return 行
	 */
	public TExcelRow getRow() {
		TExcelRow er = new TExcelRow(book);
		er.row = cell.getRow();
		return er;
	}

	/**
	 * 指定列のセルの値を設定する
	 * 
	 * @param value 値
	 */
	public void setValue(String value) {
		cell.setCellValue(Util.avoidNullNT(value));
	}

	/**
	 * 指定列のセルの値を設定する
	 * 
	 * @param value 値
	 */
	public void setValue(int value) {
		cell.setCellValue(value);
	}

	/**
	 * 指定列のセルの値を設定する
	 * 
	 * @param value 値
	 */
	public void setValue(BigDecimal value) {

		if (value == null) {
			return;
		}

		cell.setCellValue(value.doubleValue());
	}

	/**
	 * 指定列のセルの値を設定する
	 * 
	 * @param value 値
	 */
	public void setValue(Date value) {

		if (value == null) {
			return;
		}

		cell.setCellValue(value);
	}

	/**
	 * 文字列表現
	 */
	@Override
	public String toString() {
		return TExcelSheet.toExcelNo(rowIndex, columnIndex);
	}

}
