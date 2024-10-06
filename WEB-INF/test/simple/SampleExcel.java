package simple;

import java.awt.Color;
import java.io.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.common.util.DateUtil;
import jp.co.ais.trans2.common.excel.*;

/**
 * サンプル
 * 
 * @author AIS
 */
public class SampleExcel extends TExcel {

	/** USD書式 */
	protected CellStyle usdStyle;

	/** JPY書式 */
	protected CellStyle jpyStyle;

	/**
	 * コンストラクタ
	 * 
	 * @throws TException
	 */
	public SampleExcel() throws TException {
		super(new File("temp.xls"));
		// super("ja");
	}

	/**
	 * テンプレートファイルを読み込む
	 * 
	 * @param cls Class
	 * @param path ファイルパス
	 * @throws TException
	 */
	@Override
	protected void readTempFile(Class cls, String path) throws TException {
		super.readTempFile(this.getClass(), path);
	}

	/**
	 * エクセルで渡す
	 * 
	 * @param list 一覧データの配列
	 * @exception TException
	 * @return byte Excel
	 */
	public byte[] getExcel(List<List<Object>> list) throws TException {
		try {
			for (int i = 0; i < 3; i++) {
				createReport(list, i);
			}

			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルレイアウト定義 各カルムのデータをセット
	 * 
	 * @param list 一覧データの配列
	 * @param index シート番号
	 */
	public void createReport(List<List<Object>> list, int index) {

		// シート追加(テンプレートシートからコピー)
		TExcelSheet sheet = null;
		if (index == 0) {
			sheet = getSheet(0);
			this.setSheetName(0, "サンプル" + index);
		} else {
			sheet = cloneSheet(0, "サンプル" + index);// サンプル
		}
		// TExcelSheet sheet = addSheet("サンプル" + index, "");// サンプル

		sheet.setZoom(85, 100);

		// ページ設定：余白
		sheet.setMargin(SwingConstants.TOP, convertCmToInch(1.9d));
		sheet.setMargin(SwingConstants.RIGHT, convertCmToInch(1.9d));
		sheet.setMargin(SwingConstants.LEFT, convertCmToInch(1.9d));
		sheet.setMargin(SwingConstants.BOTTOM, convertCmToInch(0.5d));

		// ページ設定：ページ中央：水平にチェック
		sheet.setHorizontallyCenterPrint();

		// スタイルマネージャ
		TExcelCellStyleManager csm = sheet.book.getCellStyleManager();

		int MAX_COL = 15;

		// 列幅設定(1文字で約256単位)
		for (int i = 0; i < MAX_COL; i++) {
			sheet.setColumnWidth(i, 3350);
		}

		// タイトル
		sheet.setRowNo(0);
		TExcelRow newRow = sheet.addRow();
		newRow.addCell("各船明細表", csm.getCenterStyleBoldNoneBorder());
		for (int i = 0; i < MAX_COL; i++) {
			newRow.addEmptyCellWithStyle(csm.getCenterStyleBoldNoneBorder());
		}

		// 対象年月
		Date from = DateUtil.getDate(2013, 4, 1);
		Date to = DateUtil.getDate(2014, 3, 31);
		newRow = sheet.addRow();
		String titleYm = DateUtil.toJpYMString(from) + " ～ " + DateUtil.toJpYMString(to);
		newRow.addCell(titleYm, csm.getCenterStyleNoneBorder());
		for (int i = 0; i < MAX_COL; i++) {
			newRow.addEmptyCellWithStyle(csm.getCenterStyleNoneBorder());
		}

		newRow = sheet.addRow();
		newRow.addCell("会社情報", csm.getLeftStyleNoneBorder());

		// エクセルの列固定機能について、
		// 下記の二つ関数で実現できます。（標準機能）
		// ①印刷時の固定行列を設定する(※インデックスは0～)
		{
			// パラメーター:
			// sheetIndex シートIndex
			// startColumn 開始列
			// endColumn 終了列
			// startRow 開始行
			// endRow 終了行
			setRepeatingRowsAndColumns(0, 0, 2, 0, 3);
		}

		// ②見た目の固定行列を設定する
		{
			// パラメーター:
			// cols 列個数
			// row 行個数
			sheet.sheet.createFreezePane(3, 4);
		}

		// 印刷範囲設定サンプル
		{
			// シート番号=index、列開始=0、列終了=2、行開始=0、行終了=10*indexの順
			setPrintArea(index, 0, 2, 0, 11 + 10 * index);
		}

		// 行改ページ指定
		{
			// row index 0～
			sheet.sheet.setRowBreak(6);
		}

		// 印刷設定(ヘッダ・フッタ XLSバージョン)
		{
			sheet.sheet.getHeader().setCenter(HeaderFooter.date());
			sheet.sheet.getHeader().setRight(HeaderFooter.page() + "/" + HeaderFooter.numPages());
			sheet.sheet.getFooter().setLeft(HeaderFooter.font("ＭＳ ゴシック", "Italic") + "AIS Excel Sample");
		}

		// ヘッダ、フッタの余白設定
		{
			sheet.sheet.getPrintSetup().setHeaderMargin(convertCmToInch(0.9d));
			sheet.sheet.getPrintSetup().setFooterMargin(convertCmToInch(0.8d));
		}

		// CTSheetView view = ((XSSFSheet) sheet.sheet).getCTWorksheet().getSheetViews().getSheetViewArray(0);
		// view.setView(Enum.forString("pageBreakPreview"));

		// ヘッダータイトル
		newRow = sheet.addRow();

		// カラム設定
		newRow.addCell(getWord("固定列①"), csm.getHeadStyle());
		newRow.addCell(getWord("固定列②"), csm.getHeadStyle());
		newRow.addCell(getWord("固定列③"), csm.getHeadStyle());
		for (int i = 3; i < MAX_COL; i++) {
			newRow.addCell(getWord("カラム_" + (i + 1)), csm.getHeadStyle());
		}

		String usdFormat = "$#,##0.00;-$#,##0.00";
		String jpyFormat = "\"\\\"#,##0;\"\\-\"#,##0";

		CellStyle titleTextStyle = null;
		CellStyle titleUSDStyle = null;
		CellStyle titleJPYStyle = null;

		if (titleTextStyle == null) {
			titleTextStyle = createCellStyle(Color.red, Color.yellow, null);
		}
		if (titleUSDStyle == null) {
			titleUSDStyle = createCellStyle(Color.blue, Color.yellow, usdFormat);
		}
		if (titleJPYStyle == null) {
			titleJPYStyle = createCellStyle(Color.green, Color.black, jpyFormat);
		}

		// 明細描画
		int i = 0;
		for (List<Object> data : list) {
			newRow = sheet.addRow();

			CellStyle f = i % 2 == 0 ? titleUSDStyle : titleJPYStyle;

			for (Object obj : data) {

				if (obj instanceof Date) {
					newRow.addCell((Date) obj);
				} else if (obj instanceof BigDecimal) {
					// 指定書式で値を設定する
					newRow.addNumberCell(((BigDecimal) obj).multiply(new BigDecimal(index + 1)), f);
				} else {
					newRow.addCell(Util.avoidNull(obj));
				}
			}

			i++;
		}
	}

	/**
	 * セルスタイルの作成
	 * 
	 * @param foreColor
	 * @param fillColor
	 * @param format 書式(数値の場合)
	 * @return セルスタイル
	 */
	protected CellStyle createCellStyle(Color foreColor, Color fillColor, String format) {
		CellStyle style = workBook.createCellStyle();
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		Font font = cellStyleManager.getFontStyle((short) 9);
		if (foreColor != null) {
			setForeColor(font, foreColor);
		}
		style.setFont(font);

		if (fillColor != null) {
			setFillColor(style, fillColor);
		}

		if (!Util.isNullOrEmpty(format)) {
			style.setAlignment(CellStyle.ALIGN_RIGHT);
			DataFormat df = cellStyleManager.createDataFormat();
			style.setDataFormat(df.getFormat(format));
		}

		return style;
	}

	/**
	 * 文字色設定
	 * 
	 * @param font
	 * @param color
	 */
	protected void setForeColor(Font font, Color color) {
		TExcelOutput.setForeColor(workBook, font, color);
	}

	/**
	 * 背景色設定
	 * 
	 * @param style
	 * @param color
	 */
	protected void setFillColor(CellStyle style, Color color) {
		TExcelOutput.setFillColor(workBook, style, color);
	}

}