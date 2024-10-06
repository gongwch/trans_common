package jp.co.ais.trans2.common.excel;

import java.math.*;
import java.util.*;

import javax.swing.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * Excelシート
 * 
 * @author AIS
 */
public class TExcelSheet {

	/** ブック */
	public TExcel book;

	/** シート */
	public Sheet sheet;

	/** ヘッダー行 */
	protected Row row = null;

	/** 最終行番号 */
	protected int rowNo = 1;

	/** 最終列番号 */
	protected int columnNo = 0;

	/** セルスタイル保持用 */
	protected Map<Integer, CellStyle> styleMap = new TreeMap<Integer, CellStyle>();

	/**
	 * コンストラクタ.
	 * 
	 * @param book TExcel
	 */
	public TExcelSheet(TExcel book) {
		this.book = book;
	}

	/**
	 * 最終行番号セット(初期位置)
	 * 
	 * @param initRow 最終行番号
	 */
	public void setRowNo(int initRow) {
		this.rowNo = (short) initRow;
	}

	/**
	 * 最終列の後ろにカラムを追加する
	 * 
	 * @param title
	 * @param width
	 */
	public void addColumn(String title, int width) {
		if (row == null) {
			row = sheet.createRow(0);
		}

		Cell newcell = createCell(row, columnNo, book.getCellStyleManager().getHeadStyle());
		newcell.setCellValue(title);
		setColumnWidth(columnNo, width);
		columnNo++;
	}

	/**
	 * 幅を設定する
	 * 
	 * @param columIndex カラム番号
	 * @param width 幅
	 */
	public void setColumnWidth(int columIndex, int width) {
		sheet.setColumnWidth(columIndex, width);
	}

	/**
	 * 指定タイプの設定を行い、セルを生成する.
	 * 
	 * @param hssfRow_ 該当行
	 * @param cellIndex セル番号
	 * @param cellStyle タイプ
	 * @return 生成されたセル
	 */
	protected Cell createCell(Row hssfRow_, int cellIndex, CellStyle cellStyle) {

		Cell newcell = hssfRow_.createCell(cellIndex);

		CellStyle style = styleMap.get(cellStyle.hashCode());

		if (style == null) {
			style = book.createCellStyleManager().createCellStyle();
			style.cloneStyleFrom(cellStyle);

			styleMap.put(cellStyle.hashCode(), style);
		}

		newcell.setCellStyle(style);

		return newcell;
	}

	/**
	 * 指定のセルにスタイルをセットする.
	 * 
	 * @param row 行番号
	 * @param column 列番号
	 * @param style 値
	 */
	public void setCellStyle(int row, int column, CellStyle style) {

		Row hrow = sheet.getRow(row);
		if (hrow == null) {
			hrow = sheet.createRow(row);
		}

		Cell cell = hrow.getCell((short) column);
		if (cell == null) {
			cell = hrow.createCell((short) column);
		}

		cell.setCellStyle(style);
	}

	/**
	 * 行を追加する
	 * 
	 * @return 追加した行
	 */
	public TExcelRow addRow() {

		// 行数チェック(Excel2003の場合のみチェック)
		if (65536 <= rowNo && !TExcel.isExcelXml()) {
			throw new TRuntimeException("W00213");
		}

		Row row_ = sheet.createRow(rowNo);
		TExcelRow er = new TExcelRow(book);
		er.row = row_;
		rowNo++;
		return er;
	}

	/**
	 * 行を取得する
	 * 
	 * @param rowNo_ 行番号
	 * @return 取得した行
	 */
	public TExcelRow getRow(int rowNo_) {
		Row row_ = sheet.getRow(rowNo_);
		if (row_ == null) {
			row_ = sheet.createRow(rowNo_);
		}
		TExcelRow er = new TExcelRow(book);
		er.row = row_;
		return er;
	}

	/**
	 * 印刷方向の設定
	 * 
	 * @param landscape true:横 / false:縦
	 */
	public void setLandscape(boolean landscape) {
		PrintSetup setup = sheet.getPrintSetup();
		setup.setLandscape(landscape);
	}

	/**
	 * マージンの設定<br>
	 * <li>SwingConstants.TOP</li><br>
	 * <li>SwingConstants.BOTTOM</li><br>
	 * <li>SwingConstants.LEFT</li><br>
	 * <li>SwingConstants.RIGHT</li><br>
	 * 
	 * @param align SwingConstantsを使う
	 * @param margin
	 */
	public void setMargin(int align, double margin) {

		short sAlign = 0;

		switch (align) {
			case SwingConstants.TOP:
				sAlign = Sheet.TopMargin;
				break;

			case SwingConstants.BOTTOM:
				sAlign = Sheet.BottomMargin;
				break;

			case SwingConstants.LEFT:
				sAlign = Sheet.LeftMargin;
				break;

			case SwingConstants.RIGHT:
				sAlign = Sheet.RightMargin;
				break;
		}

		sheet.setMargin(sAlign, margin);

	}

	/**
	 * 指定のセルに値をセットする
	 * 
	 * @param row
	 * @param column
	 * @param value 値
	 * @param decimalPoint
	 */
	public void setValue(int row, int column, BigDecimal value, int decimalPoint) {

		Row hrow = sheet.getRow(row);
		if (hrow == null) {
			hrow = sheet.createRow(row);
		}

		TExcelCellStyleManager cm = book.getCellStyleManager();
		String format = NumberFormatUtil.makeNumberFormat(decimalPoint);

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

		Cell newcell = createCell(hrow, (short) column, cm.getDecimalStyles().get(format));

		if (value == null) {
			newcell.setCellValue("");

		} else {
			newcell.setCellValue(value.doubleValue());
		}
	}

	/**
	 * 指定のセルに値をセットする
	 * 
	 * @param row
	 * @param column
	 * @param value 値
	 */
	public void setValue(int row, int column, String value) {

		Row hrow = sheet.getRow(row);
		if (hrow == null) {
			hrow = sheet.createRow(row);
		}

		Cell newcell = createCell(hrow, (short) column, book.getCellStyleManager().getLeftStyle());

		newcell.setCellValue(value);
	}

	/**
	 * 指定のセルに値をセットする
	 * 
	 * @param row
	 * @param column
	 * @param value
	 * @param decimalPoint
	 * @param styleType
	 */
	@SuppressWarnings("unused")
	public void setValue(int row, int column, BigDecimal value, int decimalPoint, TCellStyle styleType) {

		Row hrow = sheet.getRow(row);
		if (hrow == null) {
			hrow = sheet.createRow(row);
		}

		Cell newcell = createCell(hrow, column, styleType.getCellStyle());

		if (value == null) {
			newcell.setCellValue("");

		} else {
			newcell.setCellValue(value.doubleValue());
		}

	}

	/**
	 * 指定のセルに値をセットする
	 * 
	 * @param row
	 * @param column
	 * @param value 値
	 * @param styleType
	 */
	public void setValue(int row, int column, String value, TCellStyle styleType) {

		Row hrow = sheet.getRow(row);
		if (hrow == null) {
			hrow = sheet.createRow(row);
		}

		Cell newcell = createCell(hrow, column, styleType.getCellStyle());

		newcell.setCellValue(value);
	}

	/**
	 * 指定のセルに値をセットする
	 * 
	 * @param row
	 * @param column
	 * @param value 値
	 * @param styleType
	 */
	public void setValue(int row, int column, Date value, TCellStyle styleType) {

		Row hrow = sheet.getRow(row);
		if (hrow == null) {
			hrow = sheet.createRow(row);
		}

		Cell newcell = createCell(hrow, column, styleType.getCellStyle());

		newcell.setCellValue(value);
	}

	/**
	 * 指定のセルに値をセットする（行スタイルコピー、数値設定）
	 * 
	 * @param copyrow コピー元行
	 * @param row コピー先行
	 * @param column 指定セル
	 * @param value 値
	 * @param decimalPoint 小数点以下桁数
	 */
	public void setValueAndStyleGet(int copyrow, int row, int column, BigDecimal value, int decimalPoint) {

		Row hrow = sheet.getRow(row);

		if (hrow == null) {
			hrow = copyRow(copyrow, row);
		}

		TExcelCellStyleManager cm = book.getCellStyleManager();
		String format = NumberFormatUtil.makeNumberFormat(decimalPoint);

		cm.getDecimalStyles().put(format, hrow.getCell(column).getCellStyle());

		if (value == null) {
			hrow.getCell(column).setCellValue("");

		} else {
			hrow.getCell(column).setCellValue(value.doubleValue());
		}

	}

	/**
	 * 指定のセルに値をセットする（行スタイルコピー、文字列設定）
	 * 
	 * @param copyrow コピー元行
	 * @param row コピー先行
	 * @param column 指定セル
	 * @param value 値
	 */
	public void setValueAndStyleGet(int copyrow, int row, int column, String value) {

		Row hrow = sheet.getRow(row);

		if (hrow == null) {
			hrow = copyRow(copyrow, row);
		}

		if (value == null) {
			hrow.getCell(column).setCellValue("");
		} else {
			hrow.getCell(column).setCellValue(value);
		}
	}

	/**
	 * 指定のセルに値をセットする（行スタイルコピー、日付設定）
	 * 
	 * @param copyrow コピー元行
	 * @param row コピー先行
	 * @param column 指定セル
	 * @param value 値
	 */
	public void setValueAndStyleGet(int copyrow, int row, int column, Date value) {

		Row hrow = sheet.getRow(row);

		if (hrow == null) {
			hrow = copyRow(copyrow, row);
		}

		if (value == null) {
			hrow.getCell(column).setCellValue("");
		} else {
			hrow.getCell(column).setCellValue(value);
		}
	}

	/**
	 * 指定のセルに値をセットする
	 * 
	 * @param row 対象行
	 * @param column 対象セル
	 * @param value 値
	 */
	public void setValueAndStyleGet(int row, int column, String value) {

		Row hrow = sheet.getRow(row);
		if (hrow == null) {
			hrow = sheet.createRow(row);
		}

		if (value == null) {
			hrow.getCell(column).setCellValue("");
		} else {
			hrow.getCell(column).setCellValue(value);
		}
	}

	/**
	 * 指定範囲の行を削除して、指定の行を移動する。
	 * 
	 * @param fromRow 削除開始行
	 * @param toRow 削除終了行
	 * @param shitCount 移動行数
	 */
	public void removeRow(int fromRow, int toRow, int shitCount) {

		// 指定範囲の行削除
		for (int i = fromRow; i <= toRow; i++) {
			Row hrow = sheet.getRow(i);
			sheet.removeRow(hrow);
		}

		// 行削除後の指定範囲の行移動（削除行一行下の行から最後行までｓ移動する）
		sheet.shiftRows(toRow + 1, sheet.getLastRowNum(), shitCount, true, true);
	}

	/**
	 * 行をコピーする
	 * 
	 * @param copy コピー元
	 * @param insert コピー先
	 * @return Row
	 */
	public Row copyRow(int copy, int insert) {

		// コピー元の行の取得
		Row copyRow = sheet.getRow(copy);

		// コピー元の行を別の行へコピーする
		Row row_ = sheet.createRow(insert);

		// コピー元の行の高さをコピー先の行に設定する
		row_.setHeight(copyRow.getHeight());

		// コピー元のセル数を取得する
		int length = copyRow.getLastCellNum();

		// セルをコピーする
		for (int i = 0; i < length; i++) {

			Cell coypCell = copyRow.getCell(i);
			Cell cell = row_.createCell(i);

			if (coypCell != null) {
				// 値を取得
				switch (coypCell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						cell.setCellValue(coypCell.getBooleanCellValue());
						break;

					case Cell.CELL_TYPE_ERROR:
						cell.setCellValue(coypCell.getErrorCellValue());
						break;

					case Cell.CELL_TYPE_FORMULA:
						cell.setCellFormula(coypCell.getCellFormula());
						break;

					case Cell.CELL_TYPE_NUMERIC:
						cell.setCellValue(coypCell.getNumericCellValue());
						break;

					case Cell.CELL_TYPE_STRING:
						cell.setCellValue(coypCell.getRichStringCellValue());
						break;

					case Cell.CELL_TYPE_BLANK:
					default:
						break;
				}

				// スタイルを取得
				cell.setCellStyle(coypCell.getCellStyle());
			}
		}

		return row_;
	}

	/**
	 * 最終行番号.<br>
	 * 先頭の値が無い行はカウントしない
	 * 
	 * @return 最終行番号
	 */
	public int getLastRowNum() {

		return sheet.getLastRowNum();
	}

	/**
	 * 行数カウント
	 * 
	 * @return 行数カウント
	 */
	@SuppressWarnings({ "unused", "hiding" })
	public int getRowCount() {
		int i = 0;
		for (Row row : sheet) {
			i++;
		}

		return i;
	}

	/**
	 * 汎用値取得
	 * 
	 * @param rowIndex 行番号
	 * @param columnIndex カラムIndex
	 * @return 文字列
	 */
	public Object getValue(int rowIndex, int columnIndex) {
		Row row_ = sheet.getRow(rowIndex);

		if (row_ == null) {
			return null;
		}

		Cell cell_ = row_.getCell(columnIndex);

		if (cell_ == null) {
			return null;
		}

		switch (cell_.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return cell_.getRichStringCellValue().getString();

			case Cell.CELL_TYPE_BOOLEAN:
				return cell_.getBooleanCellValue();

			case Cell.CELL_TYPE_NUMERIC:
				return cell_.getNumericCellValue();

			case Cell.CELL_TYPE_FORMULA: {
				FormulaEvaluator evaluator = book.workBook.getCreationHelper().createFormulaEvaluator();

				CellValue cellValue = evaluator.evaluate(cell_);
				int cellType = cellValue.getCellType();
				switch (cellType) {
					case Cell.CELL_TYPE_STRING:
						return cell_.getRichStringCellValue().getString();

					case Cell.CELL_TYPE_BOOLEAN:
						return cell_.getBooleanCellValue();

					case Cell.CELL_TYPE_NUMERIC:
						return cell_.getNumericCellValue();
					case Cell.CELL_TYPE_ERROR:
						return cell_.getErrorCellValue();
					case Cell.CELL_TYPE_BLANK:
					default:
						return null;
				}
			}
			case Cell.CELL_TYPE_ERROR:
				return cell_.getErrorCellValue();

			case Cell.CELL_TYPE_BLANK:
			default:
				return null;
		}

	}

	/**
	 * セル取得
	 * 
	 * @param rowIndex 行番号
	 * @param columnIndex カラムIndex
	 * @return セル
	 */
	public Cell getCell(int rowIndex, int columnIndex) {
		Row row_ = sheet.getRow(rowIndex);

		if (row_ == null) {
			return null;
		}

		return row_.getCell(columnIndex);

	}

	/**
	 * セル取得(A1方式指定)
	 * 
	 * @param cellNo セル番号
	 * @return エクセルのセル
	 * @throws TException
	 */
	public TExcelCell getExcelCell(String cellNo) throws TException {
		int[] indexs = indexOfCell(cellNo);
		return getExcelCell(indexs[0], indexs[1]);
	}

	/**
	 * セル取得
	 * 
	 * @param rowIndex 行番号
	 * @param columnIndex カラムIndex
	 * @return エクセルのセル
	 * @throws TException
	 */
	public TExcelCell getExcelCell(int rowIndex, int columnIndex) throws TException {
		Cell cell = getCell(rowIndex, columnIndex);
		if (cell == null) {
			// I00435:セル{0}が存在していません。
			throw new TException("I00435", toExcelNo(rowIndex, columnIndex));
		}
		return new TExcelCell(book, cell);
	}

	/**
	 * 文字列取得
	 * 
	 * @param rowIndex 行番号
	 * @param columnIndex カラムIndex
	 * @return 文字列
	 */
	public String getString(int rowIndex, int columnIndex) {
		return Util.avoidNullNT(getValue(rowIndex, columnIndex));
	}

	/**
	 * 文字列取得
	 * 
	 * @param rowIndex 行番号
	 * @param columnIndex カラムIndex
	 * @return 文字列
	 */
	public Date getDate(int rowIndex, int columnIndex) {
		String str = Util.avoidNullNT(getValue(rowIndex, columnIndex));

		if (Util.isNumber(str)) {

			return TExcel.getExcelDate(new BigDecimal(str));
		}

		return null;
	}

	/**
	 * INT取得
	 * 
	 * @param rowIndex 行番号
	 * @param columnIndex カラムIndex
	 * @return INT
	 */
	public int getInt(int rowIndex, int columnIndex) {
		Row row_ = sheet.getRow(rowIndex);
		Cell cell = row_.getCell(columnIndex);

		if (cell == null) {
			return 0;
		}

		return (int) cell.getNumericCellValue();
	}

	/**
	 * 数値取得
	 * 
	 * @param rowIndex 行番号
	 * @param columnIndex カラムIndex
	 * @return 数値
	 */
	public BigDecimal getDecimal(int rowIndex, int columnIndex) {
		Row row_ = sheet.getRow(rowIndex);
		Cell cell = row_.getCell(columnIndex);

		if (cell == null) {
			return null;
		}

		return BigDecimal.valueOf(cell.getNumericCellValue());
	}

	/**
	 * boolean取得
	 * 
	 * @param rowIndex 行番号
	 * @param columnIndex カラムIndex
	 * @return 数値
	 */
	public boolean getBoolean(int rowIndex, int columnIndex) {
		Row row_ = sheet.getRow(rowIndex);
		Cell cell = row_.getCell(columnIndex);

		if (cell == null) {
			return false;
		}

		return cell.getBooleanCellValue();
	}

	/**
	 * セル取得(A1方式指定)
	 * 
	 * @param cellNo セル番号
	 * @return セル
	 */
	public Cell getCell(String cellNo) {
		int[] indexs = indexOfCell(cellNo);
		return getCell(indexs[0], indexs[1]);
	}

	/**
	 * 文字列取得(A1方式指定)
	 * 
	 * @param cellNo セル番号
	 * @return 文字列
	 */
	public String getString(String cellNo) {
		int[] indexs = indexOfCell(cellNo);
		return getString(indexs[0], indexs[1]);
	}

	/**
	 * 日付取得(A1方式指定)
	 * 
	 * @param cellNo セル番号
	 * @return 文字列
	 */
	public Date getDate(String cellNo) {
		int[] indexs = indexOfCell(cellNo);
		return getDate(indexs[0], indexs[1]);
	}

	/**
	 * INT取得(A1方式指定)
	 * 
	 * @param cellNo セル番号
	 * @return INT
	 */
	public int getInt(String cellNo) {
		int[] indexs = indexOfCell(cellNo);
		return getInt(indexs[0], indexs[1]);
	}

	/**
	 * 数値取得(A1方式指定)
	 * 
	 * @param cellNo セル番号
	 * @return 数値
	 */
	public BigDecimal getDecimal(String cellNo) {
		int[] indexs = indexOfCell(cellNo);
		return getDecimal(indexs[0], indexs[1]);
	}

	/**
	 * boolean取得(A1方式指定)
	 * 
	 * @param cellNo セル番号
	 * @return 数値
	 */
	public boolean getBoolean(String cellNo) {
		int[] indexs = indexOfCell(cellNo);
		return getBoolean(indexs[0], indexs[1]);
	}

	/**
	 * A1方式記述からIndex番号を特定する
	 * 
	 * @param cellNo セル番号(A1方式)
	 * @return Index 0:行、0:列
	 */
	public static int[] indexOfCell(String cellNo) {
		int rowIndex = 0;
		int columnIndex = 0;

		if (Util.isNumber(String.valueOf(cellNo.charAt(1)))) {
			// A1～Z1
			String rowChar = cellNo.substring(1, cellNo.length());
			char columnChar = cellNo.charAt(0);

			rowIndex = Integer.parseInt(rowChar) - 1;
			columnIndex = Character.getNumericValue(columnChar) - 10;

		} else {
			// AA1～
			String rowChar = cellNo.substring(2, cellNo.length());
			char columnChar1 = cellNo.charAt(0);
			char columnChar2 = cellNo.charAt(1);

			rowIndex = Integer.parseInt(rowChar) - 1;
			columnIndex = (Character.getNumericValue(columnChar1) - 9) * 26;
			columnIndex += Character.getNumericValue(columnChar2) - 10;
		}

		return new int[] { rowIndex, columnIndex };
	}

	/**
	 * エクセルのセル番号のコンバート<br>
	 * 例)．row=2,col=91⇒cell=CN3<br>
	 * row<0、col<0、col>255の場合、null戻る
	 * 
	 * @param row 行番号
	 * @param col 列番号
	 * @return エクセルのセル番号
	 */
	public static String toExcelNo(int row, int col) {

		if (row < 0 || col < 0) {
			return null;
		}

		if (col > 255) {
			return null;
		}

		String cellNo = "";
		char a = 'A';

		if (col < 26) {
			cellNo = String.valueOf((char) (a + col));
		} else {
			int b = col % 26;
			int c = (col - b) / 26;
			cellNo = String.valueOf((char) (a + c - 1)) + String.valueOf((char) (a + b));
		}
		cellNo = cellNo + (row + 1);

		return cellNo;
	}

	/**
	 * 指定のセルにスタイルをセットする.
	 * 
	 * @param cellRow 行番号
	 * @param cellColumn 列番号
	 * @return style スタイル
	 */
	public TCellStyle getTCellStyle(int cellRow, int cellColumn) {
		Cell cell = getCell(cellRow, cellColumn);

		if (cell == null) {
			Row row_ = sheet.getRow(cellRow);
			cell = row_.createCell(cellColumn);
		}

		return new TCellStyle(cell.getCellStyle());
	}

	/**
	 * シートズームの設定
	 * 
	 * @param numerator 分子
	 * @param denominator 分母
	 */
	public void setZoom(int numerator, int denominator) {
		sheet.setZoom(numerator, denominator);
	}

	/**
	 * シートの選択設定
	 * 
	 * @param sel true:選択
	 */
	public void setSelected(boolean sel) {
		sheet.setSelected(sel);
	}

	/**
	 * セルを結合する
	 * 
	 * @param row1
	 * @param column1
	 * @param row2
	 * @param column2
	 */
	public void addMergedRegion(int row1, int column1, int row2, int column2) {
		sheet.addMergedRegion(new CellRangeAddress(row1, row2, column1, column2));
	}

	/**
	 * ページ設定：指定された横と縦ページ数に合わせて自動改ページにする
	 */
	public void setAutobreaksPrint() {
		// 自動改ページモード
		sheet.setAutobreaks(true);
	}

	/**
	 * 「次のページに合わせて印刷」オプションをON（XLSXの場合必須）
	 */
	public void setFitToPage() {
		sheet.setFitToPage(true);
	}

	/**
	 * ページ設定：合わせて印刷場合の横
	 * 
	 * @param height 横
	 */
	public void setFitHeight(int height) {
		sheet.getPrintSetup().setFitHeight((short) height);
	}

	/**
	 * ページ設定：合わせて印刷場合の縦
	 * 
	 * @param width 縦
	 */
	public void setFitWidth(int width) {
		sheet.getPrintSetup().setFitWidth((short) width);
	}

	/**
	 * ページ設定：ページ中央：水平にチェック
	 */
	public void setHorizontallyCenterPrint() {
		// 水平にチェックあり
		sheet.setHorizontallyCenter(true);
	}

	/**
	 * ページ設定：ページ中央：垂直にチェック
	 */
	public void setVerticallyCenterPrint() {
		// 垂直にチェックあり
		sheet.setVerticallyCenter(true);
	}

}
