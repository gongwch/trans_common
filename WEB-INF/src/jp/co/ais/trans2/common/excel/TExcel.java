package jp.co.ais.trans2.common.excel;

import java.io.*;
import java.math.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.report.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.common.util.DateUtil;

/**
 * Excel生成クラス
 * 
 * @author AIS
 */
public class TExcel extends LayoutBase {

	/** PRINT_AREA */
	public static final String PRINT_AREA = "Print_Area";

	/** COMMA_PATTERN */
	public static final Pattern COMMA_PATTERN = Pattern.compile(",");

	/** millseconds */
	@Deprecated
	public static final BigDecimal millseconds = new BigDecimal(24 * 60 * 60 * 1000);

	/** 基準日付 */
	@Deprecated
	public static final Date baseDate = DateUtil.getDate(1900, 1, -1);

	/** １センチメートル＝０．３９３７インチ */
	public static final double INCH_UNIT = 0.3937d;

	/** Excel XML モード */
	public static boolean excelXml = false;

	static {
		try {
			excelXml = ServerConfig.isFlagOn("trans.excel.xml");
		} catch (Throwable e) {
			// 処理なし
		}
	}

	/** エクセルタイプ */
	protected ExcelType type = ExcelType.XLS;

	/** WorkBook */
	public Workbook workBook;

	/** スタイルマネージャ */
	protected TExcelCellStyleManager cellStyleManager;

	/** ファイルStream */
	protected InputStream stream;

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public TExcel(String lang) {
		this(lang, excelXml ? ExcelType.XLSX : ExcelType.XLS);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 * @param type
	 */
	public TExcel(String lang, ExcelType type) {
		super(lang);

		this.type = type;

		// 新規ワークブックを作成する
		this.workBook = createWorkbook();

		// スタイル初期化
		cellStyleManager = createCellStyleManager();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param file 読込ファイル
	 * @throws TException ファイル不備
	 */
	public TExcel(File file) throws TException {
		super("ja");

		// ファイル名
		String fileName = file.getName();

		if (fileName.indexOf(".") == -1) {
			// ファイル名に拡張子がありません。
			throw new TException("I00384");
		}

		// 拡張子からエクセルタイプ判別
		// 拡張子判別廃止。タイプ判別はpoi共通機能使う

		// ファイル読込
		readTempFile(null, file.getPath());

		// スタイル初期化
		cellStyleManager = createCellStyleManager();
	}

	/**
	 * @return true:Excel XML モード
	 */
	public static boolean isExcelXml() {
		return excelXml;
	}

	/**
	 * Workbook生成
	 * 
	 * @return Workbook
	 */
	protected Workbook createWorkbook() {
		return TExcelOutput.createWorkbook(type);
	}

	/**
	 * CellStyleManager生成
	 * 
	 * @return CellStyleManager
	 */
	protected TExcelCellStyleManager createCellStyleManager() {

		switch (type) {
		case XLSX:
			return new TXSSFCellStyleManager(workBook, lang);

		default:
			return new THSSFCellStyleManager(workBook, lang);
		}
	}

	/**
	 * プリンタの設定
	 * 
	 * @param sheet 対象シート
	 * @param printerSize プリンタサイズ
	 * @param landscape 印刷時ページ設定 true:横
	 */
	protected void setPrinterSytle(Sheet sheet, short printerSize, boolean landscape) {

		// プリンタ設定用紙
		PrintSetup setup = sheet.getPrintSetup();

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
	protected TExcelSheet addSheet(String sheetName) {

		TExcelSheet sheet = addSheet(sheetName, sheetName);
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
	protected TExcelSheet addSheet(String sheetName, String headerWord) {
		return addSheet(sheetName, headerWord, true, SwingConstants.CENTER);
	}

	/**
	 * Bookにシートを追加する.<br>
	 * 指定された単語を表中心上部に表示させる。
	 * 
	 * @param sheetName シート名
	 * @param headerWord ヘッダ表示文字
	 * @param align
	 * @return シート
	 */
	protected TExcelSheet addSheet(String sheetName, String headerWord, int align) {
		return addSheet(sheetName, headerWord, true, align);
	}

	/**
	 * Bookにシートを追加する.<br>
	 * 指定された単語を表中心上部に表示させる。
	 * 
	 * @param sheetName シート名
	 * @param headerWord ヘッダ表示文字
	 * @param hasColoumnTitle
	 * @param align
	 * @return シート
	 */
	protected TExcelSheet addSheet(String sheetName, String headerWord, boolean hasColoumnTitle, int align) {

		TExcelSheet es = new TExcelSheet(this);
		Sheet sheet = workBook.createSheet();
		int sheetIndex = workBook.getNumberOfSheets() - 1;

		// シート名設定
		setSheetName(sheetIndex, sheetName);
		setPrinterSytle(sheet, PrintSetup.A4_PAPERSIZE, true);

		es.sheet = sheet;

		// ヘッダー設定

		if (SwingConstants.LEFT == align) {
			es.sheet.getHeader().setLeft(
					HeaderFooter.font("Stencil-Normal", this.getFont()) + HeaderFooter.fontSize((short) 11)
							+ headerWord);
		} else if (SwingConstants.RIGHT == align) {
			es.sheet.getHeader().setRight(
					HeaderFooter.font("Stencil-Normal", this.getFont()) + HeaderFooter.fontSize((short) 11)
							+ headerWord);
		} else {
			es.sheet.getHeader().setCenter(
					HeaderFooter.font("Stencil-Normal", this.getFont()) + HeaderFooter.fontSize((short) 11)
							+ headerWord);
		}

		// 行のタイトル(一行目のカラム部分)指定
		if (hasColoumnTitle) {
			workBook.setRepeatingRowsAndColumns(sheetIndex, -1, -1, 0, 0);
		}

		return es;
	}

	/**
	 * シート名設定<br>
	 * 自動的に31文字で切る 禁止文字をブランクに変換
	 * 
	 * @param sheetIndex 対象シートIndex
	 * @param sheetName 名称
	 */
	protected void setSheetName(int sheetIndex, String sheetName) {

		if (sheetName.length() > 31) {
			sheetName = sheetName.substring(0, 31);
		}

		// ファイル名の禁止文字変換(/→ブランク)
		sheetName = getCorrectExcelSheetName(sheetName);

		// シート名を変更
		workBook.setSheetName(sheetIndex, sheetName);
	}

	/**
	 * 禁止文字変換後のシート名の取得 <br>
	 * シート名の禁止文字変換(<b>下記符号⇒ブランク</b>) <br>
	 * コロン(<b>:</b>)、 円記号(<b>\</b>)、 スラッシュ(<b>/</b>)、 疑問符(<b>?</b>)、 アスタリスク(<b>*</b>)、 左角カッコ(<b>[</b>)、 右角カッコ(<b>]</b>) <br>
	 * 
	 * @param sheetName シート名
	 * @return 正確シート名
	 */
	public static String getCorrectExcelSheetName(String sheetName) {

		// ファイル名の禁止文字変換(⇒ブランク)
		return sheetName.replaceAll(":", "").replaceAll("\\\\", "").replaceAll("/", "").replaceAll("\\?", "")
				.replaceAll("\\*", "").replaceAll("\\[", "").replaceAll("\\]", "");
	}

	/**
	 * テンプレートファイルを読み込む
	 * 
	 * @param cls Class
	 * @param path ファイルパス
	 * @throws TException
	 */
	protected void readTempFile(Class cls, String path) throws TException {

		try {
			if (cls != null) {
				stream = ResourceUtil.getResourceAsStream(cls, path);

			} else {
				stream = new FileInputStream(path);
			}

			workBook = WorkbookFactory.create(stream);
			if (workBook instanceof HSSFWorkbook) {
				type = ExcelType.XLS;
			} else {
				type = ExcelType.XLSX;
			}

		} catch (Throwable ex) {
			ServerErrorHandler.handledException(ex);

			// ファイルの読み込みに失敗しました。{0}
			throw new TException("E00021", path);
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
			workBook = TExcelOutput.getWorkbook(workBook);

			// 作成したワークブックを保存する
			workBook.write(fileOut);

			fileOut.flush();

			return fileOut.toByteArray();
		} finally {
			ResourceUtil.closeOutputStream(fileOut);
		}
	}

	@Override
	protected String getFont() {
		return null;
	}

	/**
	 * スタイルマネージャを返す
	 * 
	 * @return スタイルマネージャ
	 */
	public TExcelCellStyleManager getCellStyleManager() {
		return cellStyleManager;
	}

	/**
	 * エクセルタイプ
	 * 
	 * @return エクセルタイプ
	 */
	public ExcelType getType() {
		return this.type;
	}

	/**
	 * シートを取得する
	 * 
	 * @param sheetIndex シートIndex
	 * @return シート
	 */
	public TExcelSheet getSheet(int sheetIndex) {

		TExcelSheet es = new TExcelSheet(this);
		Sheet sheet = workBook.getSheetAt(sheetIndex);
		es.sheet = sheet;
		return es;
	}

	/**
	 * シートを取得する
	 * 
	 * @param sheetName シート名
	 * @return シート
	 */
	public TExcelSheet getSheet(String sheetName) {

		TExcelSheet es = new TExcelSheet(this);
		Sheet sheet = workBook.getSheet(sheetName);
		es.sheet = sheet;
		return es;
	}

	/**
	 * Streamを閉じる
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		super.finalize();

		ResourceUtil.closeInputStream(stream);
	}

	/**
	 * エクセルの日付形式Dateを取得する.
	 * 
	 * @param dec 数値
	 * @return 指定された数値に対応するDate(1900/01/01 00:00:00ベース)
	 */
	public static Date getExcelDate(BigDecimal dec) {
		Calendar parsed = org.apache.poi.ss.usermodel.DateUtil.getJavaCalendar(dec.doubleValue(), false);
		return parsed.getTime();

	}

	/**
	 * シートを削除する
	 * 
	 * @param sheetIndex シートIndex
	 */
	protected void removeSheet(int sheetIndex) {
		workBook.removeSheetAt(sheetIndex);
	}

	/**
	 * シート名称の取得
	 * 
	 * @param sheetIndex シートIndex
	 * @return シート名称
	 */
	protected String getSheetName(int sheetIndex) {
		return workBook.getSheetName(sheetIndex);
	}

	/**
	 * 印刷範囲を設定する<br>
	 * poi 3.2以降、XLSX対応のため、印刷範囲指定は効かなくなってしまう<br>
	 * そのための個別対応
	 * 
	 * @param sheetIndex シートIndex
	 * @param startColumn 開始列
	 * @param endColumn 終了列
	 * @param startRow 開始行
	 * @param endRow 終了行
	 */
	protected void setPrintArea(int sheetIndex, int startColumn, int endColumn, int startRow, int endRow) {

		// セル範囲作成
		CellReference cell = new CellReference(startRow, startColumn, true, true);
		String reference = cell.formatAsString();

		cell = new CellReference(endRow, endColumn, true, true);
		reference = reference + ":" + cell.formatAsString();

		// 名前取得
		Name name = null;
		for (int i = 0; i < workBook.getNumberOfNames(); i++) {
			Name n = workBook.getNameAt(i);
			if (n.getSheetIndex() == sheetIndex && PRINT_AREA.equals(n.getNameName())) {
				name = n;
				break;
			}
		}

		// 範囲
		String[] parts = COMMA_PATTERN.split(reference);
		StringBuffer sb = new StringBuffer(32);
		for (int i = 0; i < parts.length; i++) {
			if (i > 0) {
				sb.append(",");
			}
			TExcelOutput.appendFormat(sb, getSheetName(sheetIndex));
			sb.append("!");
			sb.append(parts[i]);
		}

		if (name == null) {
			// 作成
			name = workBook.createName();
			name.setNameName(PRINT_AREA);
			name.setSheetIndex(sheetIndex);
		}

		// 範囲設定
		name.setRefersToFormula(sb.toString());
	}

	/**
	 * 固定行列を設定する
	 * 
	 * @param sheetIndex シートIndex
	 * @param startColumn 開始列
	 * @param endColumn 終了列
	 * @param startRow 開始行
	 * @param endRow 終了行
	 */
	protected void setRepeatingRowsAndColumns(int sheetIndex, int startColumn, int endColumn, int startRow,
			int endRow) {
		workBook.setRepeatingRowsAndColumns(sheetIndex, startColumn, endColumn, startRow, endRow);
	}

	/**
	 * 指定シートをcloneする
	 * 
	 * @param sheetIndex シートIndex
	 * @return シート
	 */
	protected TExcelSheet cloneSheet(int sheetIndex) {

		TExcelSheet es = new TExcelSheet(this);
		Sheet sheet = workBook.cloneSheet(sheetIndex);

		{
			// 印刷設定もクローン
			Sheet s = workBook.getSheetAt(sheetIndex);
			clonePrintSetup(s, sheet);
		}

		es.sheet = sheet;
		return es;

	}

	/**
	 * 指定シートを指定のシート名でcloneする
	 * 
	 * @param sheetIndex シートIndex
	 * @param sheetName シート名
	 * @return シート
	 */
	protected TExcelSheet cloneSheet(int sheetIndex, String sheetName) {

		TExcelSheet es = new TExcelSheet(this);
		Sheet sheet = workBook.cloneSheet(sheetIndex);

		{
			// 印刷設定もクローン
			Sheet s = workBook.getSheetAt(sheetIndex);
			clonePrintSetup(s, sheet);
		}

		workBook.setSheetName(workBook.getNumberOfSheets() - 1, sheetName);

		es.sheet = sheet;
		return es;

	}

	/**
	 * 印刷設定もクローン<br>
	 * s -> sheet PrintSetupクローン
	 * 
	 * @param s
	 * @param sheet
	 */
	protected void clonePrintSetup(Sheet s, Sheet sheet) {
		// 印刷設定もクローン

		PrintSetup ps = sheet.getPrintSetup();
		PrintSetup ori = s.getPrintSetup();

		if (ps != null && ori != null) {
			ps.setPaperSize(ori.getPaperSize());
			ps.setScale(ori.getScale());
			ps.setPageStart(ori.getPageStart());
			ps.setFitWidth(ori.getFitWidth());
			ps.setFitHeight(ori.getFitHeight()); // 改ページがクローン後におかしい原因
			ps.setLeftToRight(ori.getLeftToRight());
			ps.setLandscape(ori.getLandscape());
			ps.setValidSettings(ori.getValidSettings());
			ps.setNoColor(ori.getNoColor());
			ps.setDraft(ori.getDraft());
			ps.setNotes(ori.getNotes());
			ps.setNoOrientation(ori.getNoOrientation());
			ps.setUsePage(ori.getUsePage());
			ps.setHResolution(ori.getHResolution());
			ps.setVResolution(ori.getVResolution());
			ps.setHeaderMargin(ori.getHeaderMargin());
			ps.setFooterMargin(ori.getFooterMargin());
			ps.setCopies(ori.getCopies());
		}
	}

	/**
	 * センチメートル⇒インチ
	 * 
	 * @param cm センチメートル
	 * @return インチ
	 */
	public static double convertCmToInch(double cm) {
		return cm * INCH_UNIT;
	}

	/**
	 * 計算式セルの再計算
	 * 
	 * @param wb
	 */
	public static void evaluateAll(Workbook wb) {
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		for (int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
			Sheet sheet = wb.getSheetAt(sheetNum);
			for (Row r : sheet) {
				for (Cell c : r) {
					if (c.getCellType() == Cell.CELL_TYPE_FORMULA) {
						try {
							evaluator.evaluateFormulaCell(c);
						} catch (Exception ex) {
							// 不思議なエラーを回避
						}
					}
				}
			}
		}
	}

	/**
	 * 計算式セルの再計算
	 */
	public void evaluate() {
		evaluateAll(workBook);
	}

	/**
	 * シート件数の取得
	 * 
	 * @return シート件数
	 */
	public int getSheetCount() {
		return workBook.getNumberOfSheets();
	}

	/**
	 * 行コピー
	 * 
	 * @param workbook
	 * @param worksheet
	 * @param sourceRowNum 元
	 * @param destinationRowNum 先(存在したら、行追加。存在してない場合、行作成)
	 */
	public static void copyRow(Workbook workbook, Sheet worksheet, int sourceRowNum, int destinationRowNum) {
		// Get the source / new row
		Row newRow = worksheet.getRow(destinationRowNum);
		Row sourceRow = worksheet.getRow(sourceRowNum);

		// If the row exist in destination, push down all rows by 1 else create a new row
		if (newRow != null) {
			worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
		} else {
			newRow = worksheet.createRow(destinationRowNum);
		}

		// Loop through source columns to add to new row
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			// Grab a copy of the old/new cell
			Cell oldCell = sourceRow.getCell(i);
			Cell newCell = newRow.createCell(i);

			// If the old cell is null jump to next cell
			if (oldCell == null) {
				newCell = null;
				continue;
			}

			// Copy style from old cell and apply to new cell
			CellStyle newCellStyle = workbook.createCellStyle();
			newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
			newCell.setCellStyle(newCellStyle);

			// If there is a cell comment, copy
			if (oldCell.getCellComment() != null) {
				newCell.setCellComment(oldCell.getCellComment());
			}

			// If there is a cell hyperlink, copy
			if (oldCell.getHyperlink() != null) {
				newCell.setHyperlink(oldCell.getHyperlink());
			}

			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());

			// Set the cell data value
			switch (oldCell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				newCell.setCellValue(oldCell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				newCell.setCellValue(oldCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				newCell.setCellErrorValue(oldCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				newCell.setCellFormula(oldCell.getCellFormula());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				newCell.setCellValue(oldCell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				newCell.setCellValue(oldCell.getRichStringCellValue());
				break;
			}
		}

		// If there are are any merged regions in the source row, copy to new row
		for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
			CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
			if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
				CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
						(newRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
						cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
				worksheet.addMergedRegion(newCellRangeAddress);
			}
		}
	}
}
