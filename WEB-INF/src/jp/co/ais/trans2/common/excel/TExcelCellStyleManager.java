package jp.co.ais.trans2.common.excel;

import java.util.*;

import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.config.*;

/**
 * エクセルのセルスタイルを管理する
 * 
 * @author AIS
 */
public abstract class TExcelCellStyleManager {

	/** Workbook */
	protected Workbook workBook;

	/** エクセルのフォントサイズ(デフォルト) */
	protected static final short DEFAULT_FONT_SIZE = 11;

	/** 帳票タイトルのフォーマット */
	protected CellStyle headStyle;

	/** 帳票タイトルの左寄せフォーマット */
	protected CellStyle headLeftStyle;

	/** セルスタイル(左寄せ) */
	protected CellStyle leftStyle;

	/** セルスタイル(右寄せ) */
	protected CellStyle rightStyle;

	/** セルスタイル(中央) */
	protected CellStyle centerStyle;

	/** セルスタイル(中央、ボーダーなし、選択範囲中央寄せ、太字&サイズ+4) */
	protected CellStyle centerStyleBoldNoneBorder;

	/** セルスタイル(中央、ボーダーなし、選択範囲中央寄せ) */
	protected CellStyle centerStyleNoneBorder;

	/** セルスタイル(中央、ボーダーあり、選択範囲中央寄せ) */
	protected CellStyle centerStyleBorder;

	/** セルスタイル(左寄せ、ボーダーなし) */
	protected CellStyle leftStyleNoneBorder;

	/** セルスタイル(左寄せ、ボーダーなし) */
	protected CellStyle rightStyleNoneBorder;

	/** セルスタイル(日付) */
	protected CellStyle dateCellStyle;

	/** セルスタイル(任意の型) */
	protected Map<String, CellStyle> stylesMap = new TreeMap<String, CellStyle>();

	/** セルスタイル(数値) */
	protected Map<String, CellStyle> decimalStyles = new TreeMap<String, CellStyle>();

	/** テンプレートエクセルから作成したセルスタイル */
	protected Map<String, CellStyle> templateStyles = new TreeMap<String, CellStyle>();

	/** フォント */
	protected String font;

	/** フォント */
	protected String languageCode;

	/**
	 * コンストラクタ
	 * 
	 * @param workBook
	 * @param languageCode 言語コード
	 */
	public TExcelCellStyleManager(Workbook workBook, String languageCode) {
		this.workBook = workBook;
		this.languageCode = languageCode;
		this.font = ReportConfig.getExcelFontName(getLanguageCode());

		initStyle();
	}

	/**
	 * セルスタイルを生成する
	 * 
	 * @return セルスタイル
	 */
	public CellStyle createCellStyle() {
		return workBook.createCellStyle();
	}

	/**
	 * 「縮小して全体を表示する」のセルスタイルを生成する
	 * 
	 * @return 「縮小して全体を表示する」のセルスタイル
	 */
	public CellStyle createShrinkToFitCellStyle() {
		CellStyle style = createCellStyle();
		setShrinkToFit(style);
		return style;
	}

	/**
	 * 「縮小して全体を表示する」の設定
	 * 
	 * @param style セルスタイル
	 */
	public abstract void setShrinkToFit(CellStyle style);

	/**
	 * DataFormatを生成する
	 * 
	 * @return DataFormat
	 */
	public DataFormat createDataFormat() {
		return workBook.createDataFormat();
	}

	/**
	 * スタイルを初期化する
	 */
	protected void initStyle() {

		// ヘッダーのスタイル
		this.headStyle = workBook.createCellStyle();
		this.headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		this.headStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.headStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.headStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.headStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		this.headStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.headStyle.setFont(this.getFontStyle());

		// ヘッダーの左寄せスタイル
		this.headLeftStyle = workBook.createCellStyle();
		this.headLeftStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		this.headLeftStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		this.headLeftStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.headLeftStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.headLeftStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.headLeftStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.headLeftStyle.setAlignment(CellStyle.ALIGN_LEFT);
		this.headLeftStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.headLeftStyle.setFont(this.getFontStyle());

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

		// 中間のスタイル(ボーダーなし、選択範囲中央寄せ、太字)
		this.centerStyleBoldNoneBorder = workBook.createCellStyle();
		this.centerStyleBoldNoneBorder.setBorderLeft(CellStyle.BORDER_NONE);
		this.centerStyleBoldNoneBorder.setBorderRight(CellStyle.BORDER_NONE);
		this.centerStyleBoldNoneBorder.setBorderTop(CellStyle.BORDER_NONE);
		this.centerStyleBoldNoneBorder.setBorderBottom(CellStyle.BORDER_NONE);
		this.centerStyleBoldNoneBorder.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
		this.centerStyleBoldNoneBorder.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		Font boldFont = this.getFontStyle((short) (DEFAULT_FONT_SIZE + 4));
		boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		this.centerStyleBoldNoneBorder.setFont(boldFont);

		// 中間のスタイル(ボーダーなし、選択範囲中央寄せ)
		this.centerStyleNoneBorder = workBook.createCellStyle();
		this.centerStyleNoneBorder.setBorderLeft(CellStyle.BORDER_NONE);
		this.centerStyleNoneBorder.setBorderRight(CellStyle.BORDER_NONE);
		this.centerStyleNoneBorder.setBorderTop(CellStyle.BORDER_NONE);
		this.centerStyleNoneBorder.setBorderBottom(CellStyle.BORDER_NONE);
		this.centerStyleNoneBorder.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
		this.centerStyleNoneBorder.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.centerStyleNoneBorder.setFont(this.getFontStyle());

		// 中間のスタイル(ボーダーあり、選択範囲中央寄せ)
		this.centerStyleBorder = workBook.createCellStyle();
		this.centerStyleBorder.setFillPattern(CellStyle.SOLID_FOREGROUND);
		this.centerStyleBorder.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		this.centerStyleBorder.setBorderLeft(CellStyle.BORDER_THIN);
		this.centerStyleBorder.setBorderRight(CellStyle.BORDER_THIN);
		this.centerStyleBorder.setBorderTop(CellStyle.BORDER_THIN);
		this.centerStyleBorder.setBorderBottom(CellStyle.BORDER_THIN);
		this.centerStyleBorder.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
		this.centerStyleBorder.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.centerStyleBorder.setFont(this.getFontStyle());

		// 左側のスタイル(ボーダーなし)
		this.leftStyleNoneBorder = workBook.createCellStyle();
		this.leftStyleNoneBorder.setBorderLeft(CellStyle.BORDER_NONE);
		this.leftStyleNoneBorder.setBorderRight(CellStyle.BORDER_NONE);
		this.leftStyleNoneBorder.setBorderTop(CellStyle.BORDER_NONE);
		this.leftStyleNoneBorder.setBorderBottom(CellStyle.BORDER_NONE);
		this.leftStyleNoneBorder.setAlignment(CellStyle.ALIGN_LEFT);
		this.leftStyleNoneBorder.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.leftStyleNoneBorder.setFont(this.getFontStyle());

		// 右側のスタイル(ボーダーなし)
		this.rightStyleNoneBorder = workBook.createCellStyle();
		this.rightStyleNoneBorder.setBorderLeft(CellStyle.BORDER_NONE);
		this.rightStyleNoneBorder.setBorderRight(CellStyle.BORDER_NONE);
		this.rightStyleNoneBorder.setBorderTop(CellStyle.BORDER_NONE);
		this.rightStyleNoneBorder.setBorderBottom(CellStyle.BORDER_NONE);
		this.rightStyleNoneBorder.setAlignment(CellStyle.ALIGN_RIGHT);
		this.rightStyleNoneBorder.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.rightStyleNoneBorder.setFont(this.getFontStyle());

		// 日付形式セル作成
		this.dateCellStyle = workBook.createCellStyle();
		this.dateCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.dateCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.dateCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.dateCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.dateCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		this.dateCellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.dateCellStyle.setFont(this.getFontStyle());

		// テンプレートエクセルのセルスタイルの初期化
		clearTemplateStyles();

		// タイプ別設定
		initStyleByType();
	}

	/**
	 * スタイル個別設定
	 */
	protected abstract void initStyleByType();

	/**
	 * Excel用デフォルトフォントスタイル
	 * 
	 * @return フォントスタイル
	 */
	public Font getFontStyle() {
		return getFontStyle(DEFAULT_FONT_SIZE);
	}

	/**
	 * Excel用フォントスタイル
	 * 
	 * @param fontSize フォントサイズ
	 * @return フォントスタイル
	 */
	public Font getFontStyle(short fontSize) {
		Font font_ = this.workBook.createFont();
		font_.setFontName(this.font);
		font_.setFontHeightInPoints(fontSize);

		return font_;
	}

	/**
	 * 言語コード
	 * 
	 * @return 言語コード
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * ヘッダーセルスタイル
	 * 
	 * @return ヘッダーセルスタイル
	 */
	public CellStyle getHeadStyle() {
		return headStyle;
	}

	/**
	 * ヘッダーセルスタイル
	 * 
	 * @param headStyle ヘッダーセルスタイル
	 */
	public void setHeadStyle(CellStyle headStyle) {
		this.headStyle = headStyle;
	}

	/**
	 * 帳票タイトルの左寄せフォーマットの取得
	 * 
	 * @return headLeftStyle 帳票タイトルの左寄せフォーマット
	 */
	public CellStyle getHeadLeftStyle() {
		return headLeftStyle;
	}

	/**
	 * 帳票タイトルの左寄せフォーマットの設定
	 * 
	 * @param headLeftStyle 帳票タイトルの左寄せフォーマット
	 */
	public void setHeadLeftStyle(CellStyle headLeftStyle) {
		this.headLeftStyle = headLeftStyle;
	}

	/**
	 * 中央セルスタイル
	 * 
	 * @return 中央セルスタイル
	 */
	public CellStyle getCenterStyle() {
		return centerStyle;
	}

	/**
	 * 中央セルスタイル
	 * 
	 * @param centerStyle 中央セルスタイル
	 */
	public void setCenterStyle(CellStyle centerStyle) {
		this.centerStyle = centerStyle;
	}

	/**
	 * 日付セルスタイル
	 * 
	 * @return 日付セルスタイル
	 */
	public CellStyle getDateCellStyle() {
		return dateCellStyle;
	}

	/**
	 * 日付セルスタイル
	 * 
	 * @param dateCellStyle 日付セルスタイル
	 */
	public void setDateCellStyle(CellStyle dateCellStyle) {
		this.dateCellStyle = dateCellStyle;
	}

	/**
	 * 左セルスタイル
	 * 
	 * @return 左セルスタイル
	 */
	public CellStyle getLeftStyle() {
		return leftStyle;
	}

	/**
	 * 左セルスタイル
	 * 
	 * @param leftStyle 左セルスタイル
	 */
	public void setLeftStyle(CellStyle leftStyle) {
		this.leftStyle = leftStyle;
	}

	/**
	 * 右セルスタイル
	 * 
	 * @return 右セルスタイル
	 */
	public CellStyle getRightStyle() {
		return rightStyle;
	}

	/**
	 * 右セルスタイル
	 * 
	 * @param rightStyle 右セルスタイル
	 */
	public void setRightStyle(CellStyle rightStyle) {
		this.rightStyle = rightStyle;
	}

	/**
	 * 数値セルスタイル
	 * 
	 * @return 数値セルスタイル
	 */
	public Map<String, CellStyle> getDecimalStyles() {
		return decimalStyles;
	}

	/**
	 * 任意の方のセルスタイルMap<br>
	 * 数値系のスタイルにおいてはgetDecimalStylesを利用
	 * 
	 * @return セルスタイルのマップ
	 */
	public Map<String, CellStyle> getStyleMap() {
		return stylesMap;
	}

	/**
	 * テンプレートエクセルのセルスタイルの初期化
	 */
	public void clearTemplateStyles() {
		this.templateStyles.clear();
	}

	/**
	 * テンプレートエクセルのセルスタイルの取得
	 * 
	 * @param template セルEnum
	 * @return dtlLeft セルスタイル
	 */
	public CellStyle getTemplateStyle(Enum template) {
		return templateStyles.get(template.name());
	}

	/**
	 * テンプレートエクセルのセルスタイルの設定
	 * 
	 * @param template セルEnum
	 * @param style セルスタイル
	 */
	public void setTemplateStyle(Enum template, CellStyle style) {
		this.templateStyles.put(template.name(), style);
	}

	/**
	 * セルスタイル(中央、ボーダーなし、選択範囲中央寄せ、太字&サイズ+4)の取得
	 * 
	 * @return centerStyleBoldNoneBorder セルスタイル(中央、ボーダーなし、選択範囲中央寄せ、太字&サイズ+4)
	 */
	public CellStyle getCenterStyleBoldNoneBorder() {
		return centerStyleBoldNoneBorder;
	}

	/**
	 * セルスタイル(中央、ボーダーなし、選択範囲中央寄せ、太字&サイズ+4)の設定
	 * 
	 * @param centerStyleBoldNoneBorder セルスタイル(中央、ボーダーなし、選択範囲中央寄せ、太字&サイズ+4)
	 */
	public void setCenterStyleBoldNoneBorder(CellStyle centerStyleBoldNoneBorder) {
		this.centerStyleBoldNoneBorder = centerStyleBoldNoneBorder;
	}

	/**
	 * セルスタイル(中央、ボーダーなし、選択範囲中央寄せ)の取得
	 * 
	 * @return centerStyleNoneBorder セルスタイル(中央、ボーダーなし、選択範囲中央寄せ)
	 */
	public CellStyle getCenterStyleNoneBorder() {
		return centerStyleNoneBorder;
	}

	/**
	 * セルスタイル(中央、ボーダーなし、選択範囲中央寄せ)の設定
	 * 
	 * @param centerStyleNoneBorder セルスタイル(中央、ボーダーなし、選択範囲中央寄せ)
	 */
	public void setCenterStyleNoneBorder(CellStyle centerStyleNoneBorder) {
		this.centerStyleNoneBorder = centerStyleNoneBorder;
	}

	/**
	 * セルスタイル(中央、ボーダーあり、選択範囲中央寄せ)の取得
	 * 
	 * @return centerStyleBorder セルスタイル(中央、ボーダーあり、選択範囲中央寄せ)
	 */
	public CellStyle getCenterStyleBorder() {
		return centerStyleBorder;
	}

	/**
	 * セルスタイル(中央、ボーダーあり、選択範囲中央寄せ)の設定
	 * 
	 * @param centerStyleBorder セルスタイル(中央、ボーダーあり、選択範囲中央寄せ)
	 */
	public void setCenterStyleBorder(CellStyle centerStyleBorder) {
		this.centerStyleBorder = centerStyleBorder;
	}

	/**
	 * セルスタイル(左寄せ、ボーダーなし)の取得
	 * 
	 * @return leftStyleNoneBorder セルスタイル(左寄せ、ボーダーなし)
	 */
	public CellStyle getLeftStyleNoneBorder() {
		return leftStyleNoneBorder;
	}

	/**
	 * セルスタイル(左寄せ、ボーダーなし)の設定
	 * 
	 * @param leftStyleNoneBorder セルスタイル(左寄せ、ボーダーなし)
	 */
	public void setLeftStyleNoneBorder(CellStyle leftStyleNoneBorder) {
		this.leftStyleNoneBorder = leftStyleNoneBorder;
	}

	/**
	 * セルスタイル(左寄せ、ボーダーなし)の取得
	 * 
	 * @return rightStyleNoneBorder セルスタイル(左寄せ、ボーダーなし)
	 */
	public CellStyle getRightStyleNoneBorder() {
		return rightStyleNoneBorder;
	}

	/**
	 * セルスタイル(左寄せ、ボーダーなし)の設定
	 * 
	 * @param rightStyleNoneBorder セルスタイル(左寄せ、ボーダーなし)
	 */
	public void setRightStyleNoneBorder(CellStyle rightStyleNoneBorder) {
		this.rightStyleNoneBorder = rightStyleNoneBorder;
	}

}
