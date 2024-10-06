package jp.co.ais.trans.common.report;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.util.*;

/**
 * エクセルリスト出力用レイアウト上位クラス
 */
public class ExcelList extends ExcelReportLayout {

	/** 標準ヘッダスタイル */
	public static final int TYPE_HEADER_STYLE = 11;

	/** 左寄せヘッダスタイル */
	public static final int TYPE_LEFT_HEADER_STYLE = 12;

	/** 帳票タイトルのフォーマット */
	protected HSSFCellStyle headStyle;

	/** 帳票タイトル(左寄せ)のフォーマット */
	protected HSSFCellStyle headLeftStyle;

	/** カラム開始 */
	protected int columnBegin = -1;

	/** カラム終了 */
	protected int columnEnd = -1;

	/** 行開始 */
	protected int rowBegin = 0;

	/** 行終了 */
	protected int rowEnd = 0;

	/**
	 * コンストラクタ
	 *
	 * @param lang 言語コード
	 */
	public ExcelList(String lang) {
		super(lang);
	}

	/**
	 * Bookにシートを追加する
	 *
	 * @param sheetName シート名
	 * @param headerWord ヘッダ表示文字
	 * @return シート
	 */
	@Override
	protected HSSFSheet addSheet(String sheetName, String headerWord) {
		return addSheet(sheetName, headerWord, true);
	}

	/**
	 * Bookにシートを追加する.<br>
	 * 1行目にカラムタイトルを挿入しない
	 *
	 * @param sheetName シート名
	 * @param headerWord ヘッダ表示文字
	 * @return シート
	 */
	protected HSSFSheet addSheetNonTitle(String sheetName, String headerWord) {
		return addSheet(sheetName, headerWord, false);
	}

	/**
	 * Bookにシートを追加する
	 *
	 * @param sheetName シート名
	 * @param headerWord ヘッダ表示文字
	 * @param hasColoumnTitle 1行目カラムタイトル判定(true:付与)
	 * @return シート
	 */
	protected HSSFSheet addSheet(String sheetName, String headerWord, boolean hasColoumnTitle) {

		// エクセル禁則文字対応 コロン(:)、円(\)、スラ(/)、疑問符(?)、アスタ(*)、左右括弧([)(])
		String name = sheetName.replace(":", "").replace("\\", "").replace("/", "").replace("?", "").replace("*", "")
			.replace("[", "").replace("]", "");

		HSSFSheet sheet = super.addSheet(StringUtil.leftBX(name, 31), headerWord);

		// プリンタ設定
		setPrinterSytle(sheet);

		if (hasColoumnTitle) {
			// 行のタイトル(一行目のカラム部分)指定
			int sheetIndex = workBook.getNumberOfSheets() - 1;
			workBook.setRepeatingRowsAndColumns(sheetIndex, columnBegin, columnEnd, rowBegin, rowEnd);
		}

		return sheet;
	}

	/**
	 * タイトルの設定する。
	 *
	 * @param cBegin カラム開始
	 * @param cEnd カラム終了
	 * @param rBegin 行開始
	 * @param rEnd 行終了
	 */
	protected void setRowAndColumn(int cBegin, int cEnd, int rBegin, int rEnd) {
		columnBegin = cBegin;
		columnEnd = cEnd;
		rowBegin = rBegin;
		rowEnd = rEnd;
	}

	/**
	 * Excel用プリンタの設定(デフォルト:A4横)
	 *
	 * @param sheet 対象シート
	 */
	protected void setPrinterSytle(HSSFSheet sheet) {
		super.setPrinterSytle(sheet, PrintSetup.A4_PAPERSIZE, true);
	}

	/**
	 * 帳票タイトルのスタイルを作成する
	 */
	@Override
	protected void initStyle() {
		super.initStyle();

		// ヘッダーのスタイル
		this.headStyle = workBook.createCellStyle();
		this.headStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		this.headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		this.headStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.headStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.headStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.headStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		this.headStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.headStyle.setFont(this.getFontStyle());

		// 左寄せヘッダーのスタイル
		this.headLeftStyle = workBook.createCellStyle();
		this.headLeftStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		this.headLeftStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		this.headLeftStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.headLeftStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.headLeftStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.headLeftStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.headLeftStyle.setAlignment(CellStyle.ALIGN_LEFT);
		this.headLeftStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.headLeftStyle.setFont(this.getFontStyle());
	}

	/**
	 * スタイルを返す.
	 *
	 * @param styleType タイプ
	 * @return スタイル
	 */
	@Override
	protected CellStyle getStyle(int styleType) {
		switch (styleType) {
			case TYPE_HEADER_STYLE:
				return headStyle;

			case TYPE_LEFT_HEADER_STYLE:
				return headLeftStyle;

			default:
				return super.getStyle(styleType);
		}
	}

	/**
	 * 該当行の指定セル番号にヘッダを設定する
	 *
	 * @param hssfRow 該当行
	 * @param cellIndex セル番号
	 * @param value 値
	 */
	protected void addHeaderCell(HSSFRow hssfRow, short cellIndex, String value) {
		HSSFCell newcell = createCell(hssfRow, cellIndex, TYPE_HEADER_STYLE);

		newcell.setCellValue(value);
	}

	/**
	 * 指定された単語IDリストを変換してヘッダセルを設定する.
	 *
	 * @param hssfRow 該当行
	 * @param wordIDs 単語IDリスト
	 */
	protected void addHeaderCells(HSSFRow hssfRow, String[] wordIDs) {

		// ヘッダ文字の取得
		String[] headers = getWordList(wordIDs);

		for (short index = 0; index < headers.length; index++) {

			// カラム名設定
			addHeaderCell(hssfRow, index, headers[index]);
		}
	}

	/**
	 * 該当行の指定セル番号にヘッダを設定する
	 *
	 * @param hssfRow 該当行
	 * @param cellIndex セル番号
	 * @param value 値
	 */
	protected void addHeaderCell(HSSFRow hssfRow, int cellIndex, String value) {
		addHeaderCell(hssfRow, (short) cellIndex, value);
	}

	/**
	 * 該当行の指定セル番号に左寄せヘッダを設定する
	 *
	 * @param hssfRow 該当行
	 * @param cellIndex セル番号
	 * @param value 値
	 */
	protected void addLeftHeaderCell(HSSFRow hssfRow, int cellIndex, String value) {
		HSSFCell newcell = createCell(hssfRow, (short) cellIndex, TYPE_LEFT_HEADER_STYLE);

		newcell.setCellValue(value);
	}
}
