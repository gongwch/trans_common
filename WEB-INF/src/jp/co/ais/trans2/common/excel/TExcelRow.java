package jp.co.ais.trans2.common.excel;

import java.math.*;
import java.util.*;

import javax.swing.*;

import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;

/**
 * エクセルの行
 * 
 * @author AIS
 */
public class TExcelRow {

	/** ROW */
	public Row row;

	/** ブック */
	public TExcel book;

	/** 現在参照しているカラム番号 */
	public short columnNo = 0;

	/**
	 * @param book
	 */
	public TExcelRow(TExcel book) {
		this.book = book;
	}

	/**
	 * セルを追加する
	 * 
	 * @param value 値
	 */
	public void addCell(String value) {
		addCell(value, SwingConstants.LEFT);
	}

	/**
	 * セルを追加する
	 * 
	 * @param value 値
	 * @param align
	 */
	public void addCell(String value, int align) {

		CellStyle style;
		switch (align) {
			case SwingConstants.LEFT:
				style = book.getCellStyleManager().getLeftStyle();
				break;

			case SwingConstants.CENTER:
				style = book.getCellStyleManager().getCenterStyle();
				break;

			default:
				style = book.getCellStyleManager().getRightStyle();
				break;
		}

		Cell newcell = createCell(row, columnNo, style);

		newcell.setCellValue(Util.avoidNullNT(value));
		columnNo++;
	}

	/**
	 * スタイルを指定してセルを追加する。
	 * 
	 * @param value
	 * @param style
	 */
	public void addCell(String value, CellStyle style) {
		Cell newcell = createCell(row, columnNo, style);
		newcell.setCellValue(Util.avoidNullNT(value));
		columnNo++;
	}

	/**
	 * スタイルを指定して数値セルを追加する。
	 * 
	 * @param num
	 * @param style
	 */
	public void addNumberCell(BigDecimal num, CellStyle style) {
		Cell newcell = createCell(row, columnNo, style);
		if (num == null) {
			newcell.setCellValue("");
		} else {
			newcell.setCellValue(num.doubleValue());
		}
		columnNo++;
	}

	/**
	 * 日付のセルを追加する
	 * 
	 * @param value
	 */
	public void addCell(Date value) {
		Cell newcell = createCell(row, columnNo, book.getCellStyleManager().getDateCellStyle());
		if (value != null) {
			newcell.setCellValue(value);
		}
		columnNo++;
	}

	/**
	 * BigDecimalのセルを追加する
	 * 
	 * @param value
	 */
	public void addCell(BigDecimal value) {
		addCell(value, 0);
	}

	/**
	 * BigDecimalのセルを追加する
	 * 
	 * @param value
	 * @param decimalPoint
	 */
	public void addCell(BigDecimal value, int decimalPoint) {
		addCell(value, NumberFormatUtil.makeExcelNumberFormat(decimalPoint));
	}

	/**
	 * BigDecimalのセルを追加する
	 * 
	 * @param value
	 * @param format
	 */
	public void addCell(BigDecimal value, String format) {

		TExcelCellStyleManager cm = book.getCellStyleManager();

		if (!cm.getDecimalStyles().containsKey(format)) {

			CellStyle decimalCellStyle = cm.createCellStyle();
			decimalCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			decimalCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			decimalCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			decimalCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			decimalCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			decimalCellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
			decimalCellStyle.setFont(cm.getFontStyle());

			// フォーマットを作成する
			DataFormat df = cm.createDataFormat();
			decimalCellStyle.setDataFormat(df.getFormat(format));
			cm.getDecimalStyles().put(format, decimalCellStyle);
		}

		Cell newcell = createCell(row, columnNo, cm.decimalStyles.get(format));

		if (value != null) {
			newcell.setCellValue(value.doubleValue());
		}
		columnNo++;
	}

	/**
	 * @param date
	 * @param format
	 */
	public void addCell(Date date, String format) {
		TExcelCellStyleManager cm = book.getCellStyleManager();
		
		if (!cm.getStyleMap().containsKey(format)) {
			CellStyle dateCellStyle = cm.createCellStyle();
			dateCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			dateCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			dateCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			dateCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			dateCellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
			dateCellStyle.setFont(cm.getFontStyle());
			// フォーマットを作成する
			DataFormat df = cm.createDataFormat();
			dateCellStyle.setDataFormat(df.getFormat(format));
			cm.getStyleMap().put(format, dateCellStyle);
		}

		Cell newCell = createCell(row, columnNo, cm.getStyleMap().get(format));
		if (date != null) {
			newCell.setCellValue(date);
		}
		columnNo++;
	}

	/**
	 * intのセルを追加する
	 * 
	 * @param value
	 */
	public void addCell(int value) {
		addCell(new BigDecimal(value), 0);
	}

	/**
	 * 空セルを追加する
	 */
	public void addEmptyCell() {
		CellStyle style = book.getCellStyleManager().getDateCellStyle();
		addEmptyCellWithStyle(style);
	}

	/**
	 * 指定スタイルの空セルを追加する
	 * 
	 * @param style 指定スタイル
	 */
	public void addEmptyCellWithStyle(CellStyle style) {
		createCell(row, columnNo, style);
		columnNo++;
	}

	/**
	 * セルを生成する
	 * 
	 * @param row_
	 * @param cellIndex
	 * @param style
	 * @return Cell
	 */
	public Cell createCell(Row row_, int cellIndex, CellStyle style) {
		Cell newcell = row_.createCell(cellIndex);
		newcell.setCellStyle(style);
		return newcell;
	}

	/**
	 * 指定列のセルの値を設定する
	 * 
	 * @param columnNo_ 列
	 * @param value 値
	 */
	public void setCellValueWithDefaultStyle(int columnNo_, String value) {
		Cell newcell = row.getCell((short) columnNo_);
		if (newcell == null) {
			ServerLogger.error("row=" + row.getRowNum() + ", col=" + columnNo_ + " is null");
		}

		newcell.setCellValue(Util.avoidNullNT(value));
	}

	/**
	 * 指定列のセルの値を設定する
	 * 
	 * @param columnNo_ 列
	 * @param value 値
	 */
	public void setCellValueWithDefaultStyle(int columnNo_, BigDecimal value) {
		Cell newcell = row.getCell((short) columnNo_);

		if (newcell == null) {
			ServerLogger.error("row=" + row.getRowNum() + ", col=" + columnNo_ + " is null.");
		}

		if (value != null) {
			newcell.setCellValue(value.doubleValue());
		}
	}

	/**
	 * 指定列のセルの値を設定する
	 * 
	 * @param columnNo_ 列
	 * @param value 値
	 * @param template テンプレートセル
	 */
	public void setCellValue(int columnNo_, String value, Enum template) {

		CellStyle style = getCellStyle(columnNo_, template);
		Cell newcell = createCell(row, (short) columnNo_, style);

		// nullの場合は変更なし
		if (!Util.isNullOrEmpty(value)) {
			newcell.setCellValue(Util.avoidNullNT(value));
		}
	}

	/**
	 * 指定列のBigDecimalのセルの値を設定する
	 * 
	 * @param columnNo_ 列
	 * @param value
	 * @param template テンプレートセル
	 */
	public void setCellValue(int columnNo_, BigDecimal value, Enum template) {

		CellStyle style = getCellStyle(columnNo_, template);
		Cell newcell = createCell(row, (short) columnNo_, style);

		// nullの場合は変更なし
		if (!DecimalUtil.isNullOrZero(value)) {
			newcell.setCellValue(value.doubleValue());
		}

	}

	/**
	 * 指定列のBigDecimalのセルの値を設定する
	 * 
	 * @param columnNo_ 列
	 * @param value
	 * @param template テンプレートセル
	 */
	public void setCellValueIncludeZero(int columnNo_, BigDecimal value, Enum template) {

		CellStyle style = getCellStyle(columnNo_, template);
		Cell newcell = createCell(row, (short) columnNo_, style);

		// nullの場合は変更なし
		if (value != null) {
			newcell.setCellValue(value.doubleValue());
		}

	}

	/**
	 * 指定列のdoubleのセルの値を設定する
	 * 
	 * @param columnNo_ 列
	 * @param value
	 * @param template テンプレートセル
	 */
	public void setCellValue(int columnNo_, double value, Enum template) {

		CellStyle style = getCellStyle(columnNo_, template);
		Cell newcell = createCell(row, (short) columnNo_, style);

		// nullの場合は変更なし
		if (value != 0) {
			newcell.setCellValue(value);
		}

	}

	/**
	 * 指定列のdoubleのセルの値を設定する
	 * 
	 * @param columnNo_ 列
	 * @param value
	 * @param template テンプレートセル
	 */
	public void setCellValueIncludeZero(int columnNo_, double value, Enum template) {

		CellStyle style = getCellStyle(columnNo_, template);
		Cell newcell = createCell(row, (short) columnNo_, style);

		// nullの場合は変更なし
		newcell.setCellValue(value);

	}

	/**
	 * セルスタイル設定
	 * 
	 * @param columnNo_ 列
	 * @param template テンプレートセル
	 */
	public void setCellStyle(int columnNo_, Enum template) {
		getCellStyle(columnNo_, template);
	}

	/**
	 * セルスタイル取得
	 * 
	 * @param columnNo_ 列
	 * @param template テンプレートセル
	 * @return セルスタイル
	 */
	public CellStyle getCellStyle(int columnNo_, Enum template) {
		CellStyle style = null;

		if (book.getCellStyleManager().getTemplateStyle(template) == null) {
			// テンプレートセルスタイルなし

			Cell cell = row.getCell((short) columnNo_);
			if (cell == null) {
				ServerLogger.error("row=" + row.getRowNum() + ", col=" + columnNo_ + " is null.");
			}

			style = cell.getCellStyle();

			// Enumに中央寄せあり
			if (template.name().indexOf("Center") > 0) {
				style.setAlignment(CellStyle.ALIGN_CENTER);
			}

			book.getCellStyleManager().setTemplateStyle(template, style);

		} else {
			// テンプレートセルスタイルあり

			return book.getCellStyleManager().getTemplateStyle(template);
		}

		return style;
	}

	/**
	 * 行高さ設定
	 * 
	 * @param height 高さ
	 */
	public void setHeight(short height) {
		row.setHeight(height);
	}
}
