package jp.co.ais.trans2.common.excel;

import java.awt.Color;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.formula.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.*;

/**
 * エクセル出力共通
 */
public class TExcelOutput {

	/**
	 * ワークブックの取得<br>
	 * Excel2003, Excel2007共通<br>
	 * poiバージョンアップするため、丸コピー用クラス
	 * 
	 * @param workBook
	 * @return Workbook
	 */
	public static Workbook getWorkbook(Workbook workBook) {
		// コンバート機能は未実現、trans-2-excelのjarをインポート必要（同名ファイル丸コピー）
		return workBook;
	}

	/**
	 * Workbook生成
	 * 
	 * @param type
	 * @return Workbook
	 */
	public static Workbook createWorkbook(ExcelType type) {
		switch (type) {
			case XLSX:
				return new XSSFWorkbook();

			default:
				return new HSSFWorkbook();
		}
	}

	/**
	 * フォーマットの追加
	 * 
	 * @param sb
	 * @param sheetName
	 */
	public static void appendFormat(StringBuffer sb, String sheetName) {
		SheetNameFormatter.appendFormat(sb, sheetName);
	}

	/**
	 * 背景色設定
	 * 
	 * @param workBook
	 * @param style
	 * @param color
	 */
	public static void setFillColor(Workbook workBook, CellStyle style, Color color) {
		// HSSF
		setFillColor(workBook, style, color, CellStyle.SOLID_FOREGROUND);
	}

	/**
	 * 背景色設定
	 * 
	 * @param workBook
	 * @param style
	 * @param color
	 * @param fillPattern
	 */
	public static void setFillColor(Workbook workBook, CellStyle style, Color color, short fillPattern) {
		setFillColor(workBook, style, color, null, fillPattern);
	}

	/**
	 * 背景色設定
	 * 
	 * @param workBook
	 * @param style
	 * @param color
	 * @param bgColor
	 * @param fillPattern
	 */
	public static void setFillColor(Workbook workBook, CellStyle style, Color color, Color bgColor, short fillPattern) {
		// HSSF
		HSSFPalette palette = ((HSSFWorkbook) workBook).getCustomPalette();
		HSSFColor hssfColor = palette.findColor((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
		if (hssfColor == null) {
			hssfColor = palette.findSimilarColor(color.getRed(), color.getGreen(), color.getBlue());
		}
		style.setFillPattern(fillPattern);
		style.setFillForegroundColor(hssfColor.getIndex());
		HSSFPalette bgpalette;
		HSSFColor hssfBgColor;
		if (bgColor != null) {
			bgpalette = ((HSSFWorkbook) workBook).getCustomPalette();
			hssfBgColor = bgpalette.findColor((byte) bgColor.getRed(), (byte) bgColor.getGreen(), (byte) bgColor.getBlue());
			if (hssfBgColor == null) {
				hssfBgColor = bgpalette.findSimilarColor(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue());
			}
			style.setFillBackgroundColor(hssfBgColor.getIndex());
		} else {
			style.setFillBackgroundColor(HSSFColor.AUTOMATIC.index);
		}
		style.setWrapText(true);
		style.setAlignment(CellStyle.ALIGN_CENTER);

	}

	/**
	 * 文字色設定
	 * 
	 * @param workBook
	 * @param font
	 * @param color
	 */
	public static void setForeColor(Workbook workBook, Font font, Color color) {
		// HSSF
		HSSFPalette palette = ((HSSFWorkbook) workBook).getCustomPalette();
		HSSFColor hssfColor = palette.findColor((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
		if (hssfColor == null) {
			hssfColor = palette.findSimilarColor(color.getRed(), color.getGreen(), color.getBlue());
		}
		font.setColor(hssfColor.getIndex());
	}

}
