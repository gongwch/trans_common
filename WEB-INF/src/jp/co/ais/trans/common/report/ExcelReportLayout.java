package jp.co.ais.trans.common.report;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;

/**
 * エクセル帳票ベースクラス. サーバサイドで使用.
 */
public class ExcelReportLayout extends LayoutBase {

	/** エクセルのフォントサイズ(デフォルト) */
	protected static final short DEFAULT_FONT_SIZE = 11;

	/** 左寄せスタイルタイプ */
	public static final int TYPE_LEFT_STYLE = 1;

	/** 右寄せスタイルタイプ */
	public static final int TYPE_RIGHT_STYLE = 2;

	/** 中央スタイルタイプ */
	public static final int TYPE_CENTER_STYLE = 3;

	/** 日付用スタイルタイプ */
	public static final int TYPE_DATE_STYLE = 4;

	/** セルスタイル(左寄せ) */
	protected CellStyle leftStyle = null;

	/** セルスタイル(右寄せ) */
	protected CellStyle rightStyle = null;

	/** セルスタイル(中央) */
	protected CellStyle centerStyle = null;

	/** セルスタイル(日付) */
	protected CellStyle dateCellStyle = null;

	/** セルスタイル(数値) */
	protected Map<String, CellStyle> decimalStyles = new TreeMap<String, CellStyle>();

	/** フォント */
	private String font;

	/** WorkBook */
	protected HSSFWorkbook workBook;

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public ExcelReportLayout(String lang) {
		super(lang);

		this.font = ReportConfig.getExcelFontName(getLanguageCode());

		// 新規ワークブックを作成する
		this.workBook = new HSSFWorkbook();

		this.initStyle();
	}

	/**
	 * 帳票タイトルのスタイルを作成する
	 */
	protected void initStyle() {

		// 中間のスタイル
		this.centerStyle = workBook.createCellStyle();
		this.centerStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.centerStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.centerStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.centerStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.centerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		this.centerStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.centerStyle.setFont(this.getFontStyle());

		// 左側のスタイル
		this.leftStyle = workBook.createCellStyle();
		this.leftStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.leftStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.leftStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.leftStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.leftStyle.setAlignment(CellStyle.ALIGN_LEFT);
		this.leftStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.leftStyle.setFont(this.getFontStyle());

		// 右側のスタイル
		this.rightStyle = workBook.createCellStyle();
		this.rightStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.rightStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.rightStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.rightStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.rightStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		this.rightStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.rightStyle.setFont(this.getFontStyle());

		// 日付形式セル作成
		this.dateCellStyle = workBook.createCellStyle();
		this.dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
		this.dateCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.dateCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.dateCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.dateCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.dateCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		this.dateCellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.dateCellStyle.setFont(this.getFontStyle());
	}

	/**
	 * Excel用フォント
	 * 
	 * @return フォント名
	 */
	@Override
	protected String getFont() {
		return font;
	}

	/**
	 * Excel用デフォルトフォントスタイル
	 * 
	 * @return フォントスタイル
	 */
	protected HSSFFont getFontStyle() {
		return getFontStyle(DEFAULT_FONT_SIZE);
	}

	/**
	 * Excel用フォントスタイル
	 * 
	 * @param fontSize フォントサイズ
	 * @return フォントスタイル
	 */
	protected HSSFFont getFontStyle(short fontSize) {
		HSSFFont hssfFont = this.workBook.createFont();
		hssfFont.setFontName(this.font);
		hssfFont.setFontHeightInPoints(fontSize);

		return hssfFont;
	}

	/**
	 * プリンタの設定
	 * 
	 * @param sheet 対象シート
	 * @param printerSize プリンタサイズ
	 * @param landscape 印刷時ページ設定 true:横
	 */
	protected void setPrinterSytle(HSSFSheet sheet, short printerSize, boolean landscape) {
		// プリンタ設定用紙
		HSSFPrintSetup setup = sheet.getPrintSetup();

		// 横方向を設定する
		setup.setLandscape(landscape);

		// サイズをA4に設定する
		setup.setPaperSize(printerSize);

		// ページ方向設定
		setup.setLeftToRight(true);
	}

	/**
	 * Bookにシートを追加する
	 * 
	 * @param sheetName シート名
	 * @return シート
	 */
	protected HSSFSheet addSheet(String sheetName) {
		HSSFSheet sheet = workBook.createSheet();
		int sheetIndex = workBook.getNumberOfSheets() - 1;

		// シート名設定
		setSheetName(sheetIndex, sheetName);

		// シートの幅を設定する
		int[] columnWidths = getColumnWidths();
		if (columnWidths.length != 0) {
			setSheetWidth(sheet, columnWidths);
		}

		return sheet;
	}

	/**
	 * Bookにシートを追加する.<br>
	 * 指定された単語を表中心上部に表示させる。
	 * 
	 * @param sheetName シート名
	 * @param headerWord ヘッダ表示文字
	 * @return シート
	 */
	protected HSSFSheet addSheet(String sheetName, String headerWord) {

		HSSFSheet sheet = addSheet(sheetName);

		// ヘッダー設定
		sheet.getHeader().setCenter(
			HeaderFooter.font("Stencil-Normal", this.getFont()) + HeaderFooter.fontSize((short) 11) + headerWord);

		return sheet;
	}

	/**
	 * カラム幅の指定(Orverride用)
	 * 
	 * @return カラム幅リスト
	 */
	protected int[] getColumnWidths() {
		return new int[0];
	}

	/**
	 * 行追加.<br>
	 * 最大行のチェックを行う.
	 * 
	 * @param sheet 対象シート
	 * @param rownum 対象行
	 * @return 追加行
	 */
	protected HSSFRow createRow(HSSFSheet sheet, int rownum) {

		// 行数チェック
		if (65536 < rownum) {
			throw new TRuntimeException("W00213");
		}

		return sheet.createRow(rownum);
	}

	/**
	 * シート名設定<br>
	 * 自動的に31文字で切る
	 * 
	 * @param sheetIndex 対象シートIndex
	 * @param sheetName 名称
	 */
	protected void setSheetName(int sheetIndex, String sheetName) {
		if (sheetName.length() > 31) {
			sheetName = sheetName.substring(0, 31);
		}

		// シート名を変更
		workBook.setSheetName(sheetIndex, sheetName);
	}

	/**
	 * シート幅を設定する
	 * 
	 * @param sheet 対象シート
	 * @param widths 幅値リスト
	 */
	protected void setSheetWidth(HSSFSheet sheet, int[] widths) {

		for (int i = 0; i < widths.length; i++) {
			// 会社コード
			sheet.setColumnWidth(i, widths[i]);
		}
	}

	/**
	 * 該当行の指定セル番号に空値を設定する
	 * 
	 * @param hssfRow 該当行
	 * @param cellIndex セル番号
	 */
	protected void addEmptyCell(HSSFRow hssfRow, short cellIndex) {

		this.addCell(hssfRow, cellIndex, "");
	}

	/**
	 * 該当行の指定セル番号に値を設定する
	 * 
	 * @param hssfRow 該当行
	 * @param cellIndex セル番号
	 * @param value 値
	 */
	protected void addCell(HSSFRow hssfRow, short cellIndex, String value) {

		this.addCell(hssfRow, cellIndex, value, TYPE_LEFT_STYLE);
	}

	/**
	 * 数値（Double）型：該当行の指定セル番号に値を設定する
	 * 
	 * @param hssfRow 該当行
	 * @param cellIndex セル番号
	 * @param value 値
	 */
	protected void addCell(HSSFRow hssfRow, short cellIndex, double value) {
		this.addCell(hssfRow, cellIndex, value, NumberFormatUtil.makeNumberFormat(0));
	}

	/**
	 * 数値（Double）型：該当行の指定セル番号に値を設定する
	 * 
	 * @param hssfRow 該当行
	 * @param cellIndex セル番号
	 * @param value 値
	 * @param digit 小数点桁数
	 */
	protected void addCell(HSSFRow hssfRow, short cellIndex, double value, int digit) {
		this.addCell(hssfRow, cellIndex, value, NumberFormatUtil.makeNumberFormat(digit));
	}

	/**
	 * 数値（double）型：該当行の指定セル番号に値を設定する<br>
	 * フォーマット：値のフォーマットを設定する フォーマット(format)：数字にフォーマットを設定する
	 * 
	 * @param hssfRow 該当行
	 * @param cellIndex セル番号
	 * @param value 値
	 * @param format フォーマット
	 */
	protected void addCell(HSSFRow hssfRow, int cellIndex, double value, String format) {

		HSSFCell newcell = hssfRow.createCell(cellIndex);

		// スタイルを作成する

		if (!decimalStyles.containsKey(format)) {
			CellStyle decimalCellStyle = workBook.createCellStyle();
			decimalCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			decimalCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			decimalCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			decimalCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			decimalCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			decimalCellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
			decimalCellStyle.setFont(this.getFontStyle());

			// フォーマットを作成する
			HSSFDataFormat df = this.workBook.createDataFormat();
			decimalCellStyle.setDataFormat(df.getFormat(format));

			decimalStyles.put(format, decimalCellStyle);
		}

		newcell.setCellStyle(decimalStyles.get(format));

		newcell.setCellValue(value);
	}

	/**
	 * 数値（int）型：該当行の指定セル番号に値を設定する
	 * 
	 * @param hssfRow 該当行
	 * @param cellIndex セル番号
	 * @param value 値
	 */
	protected void addCell(HSSFRow hssfRow, short cellIndex, int value) {
		HSSFCell newcell = createCell(hssfRow, cellIndex, TYPE_RIGHT_STYLE);

		newcell.setCellValue(value);
	}

	/**
	 * 該当行の指定セル番号に値を設定する
	 * 
	 * @param hssfRow 該当行
	 * @param cellIndex セル番号
	 * @param value 値
	 */
	protected void addRightCell(HSSFRow hssfRow, short cellIndex, String value) {
		this.addCell(hssfRow, cellIndex, value, TYPE_RIGHT_STYLE);
	}

	/**
	 * 該当行の指定セル番号に値を設定する
	 * 
	 * @param hssfRow 該当行
	 * @param cellIndex セル番号
	 * @param value 値
	 */
	protected void addCenterCell(HSSFRow hssfRow, short cellIndex, String value) {
		this.addCell(hssfRow, cellIndex, value, TYPE_CENTER_STYLE);
	}

	/**
	 * セル値設定
	 * 
	 * @param hssfRow 対象Row
	 * @param cellIndex 列Index
	 * @param value 値
	 * @param type タイプ
	 */
	private void addCell(HSSFRow hssfRow, short cellIndex, String value, int type) {
		HSSFCell newcell = createCell(hssfRow, cellIndex, type);

		// 値がNullの場合は、空文字を入れる
		if (value == null) {
			value = "";
		}

		// 1セルでの最大文字数(32767)を超える場合、後ろを切る（1バイトを基準）
		if (32767 < value.length()) {
			value = StringUtil.leftBX(value, 32767);
		}
		newcell.setCellValue(value);
	}

	/**
	 * 該当行の指定セル番号に値を設定する
	 * 
	 * @param hssfRow 該当行
	 * @param cellIndex セル番号
	 * @param value 値(日付)
	 */
	protected void addDateCell(HSSFRow hssfRow, short cellIndex, Date value) {
		// 文字列に変換
		HSSFCell newcell = createCell(hssfRow, cellIndex, TYPE_DATE_STYLE);
		newcell.setCellValue(value);
	}

	/**
	 * 指定タイプの設定を行い、セルを生成する.
	 * 
	 * @param hssfRow 該当行
	 * @param cellIndex セル番号
	 * @param styleType タイプ
	 * @return 生成されたセル
	 */
	protected HSSFCell createCell(HSSFRow hssfRow, int cellIndex, int styleType) {
		HSSFCell newcell = hssfRow.createCell(cellIndex);

		// スタイルを作成する
		CellStyle style;
		switch (styleType) {

			case TYPE_LEFT_STYLE:
				style = this.leftStyle;
				break;
			case TYPE_RIGHT_STYLE:
				style = this.rightStyle;
				break;
			case TYPE_CENTER_STYLE:
				style = this.centerStyle;
				break;
			case TYPE_DATE_STYLE:
				style = this.dateCellStyle;
				break;
			default:
				style = getStyle(styleType);
				break;
		}

		newcell.setCellStyle(style);

		return newcell;
	}

	/**
	 * 下位継承用
	 * 
	 * @param styleType タイプ
	 * @return スタイル
	 */
	protected CellStyle getStyle(@SuppressWarnings("unused") int styleType) {
		return leftStyle;
	}

	/**
	 * getBinary()を使って下さい
	 * 
	 * @param wb
	 * @return バイナリ
	 * @throws IOException
	 * @deprecated getBinary()を使って下さい
	 */
	protected byte[] getBinary(HSSFWorkbook wb) throws IOException {
		ByteArrayOutputStream fileOut = null;
		try {

			fileOut = new ByteArrayOutputStream();

			// ワークブックの取得
			Workbook newWb = TExcelOutput.getWorkbook(wb);

			// 作成したワークブックを保存する
			newWb.write(fileOut);

			fileOut.flush();

			return fileOut.toByteArray();

		} finally {

			ResourceUtil.closeOutputStream(fileOut);
		}
	}

	/**
	 * Excelのバイナリ変換
	 * 
	 * @return バイナリ
	 * @throws IOException
	 */
	public byte[] getBinary() throws IOException {
		ByteArrayOutputStream fileOut = null;
		try {

			fileOut = new ByteArrayOutputStream();

			// ワークブックの取得
			Workbook newWb = TExcelOutput.getWorkbook(workBook);

			// 作成したワークブックを保存する
			newWb.write(fileOut);

			fileOut.flush();

			return fileOut.toByteArray();
		} finally {
			ResourceUtil.closeOutputStream(fileOut);
		}
	}
}
