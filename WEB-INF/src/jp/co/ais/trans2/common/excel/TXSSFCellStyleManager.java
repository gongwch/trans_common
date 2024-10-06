package jp.co.ais.trans2.common.excel;

import java.lang.reflect.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.extensions.*;

/**
 * xlsエクセルのセルスタイルを管理する
 */
public class TXSSFCellStyleManager extends TExcelCellStyleManager {

	/**
	 * コンストラクタ.
	 * 
	 * @param workBook
	 * @param languageCode
	 */
	public TXSSFCellStyleManager(Workbook workBook, String languageCode) {
		super(workBook, languageCode);
	}

	/**
	 * スタイルを初期化する
	 */
	@Override
	protected void initStyleByType() {

		this.headStyle.setFillForegroundColor((short) 22);

		DataFormat df = workBook.createDataFormat();
		this.dateCellStyle.setDataFormat(df.getFormat("m/d/yy"));
	}

	/**
	 * 「縮小して全体を表示する」の設定
	 * 
	 * @param style セルスタイル
	 */
	@Override
	public void setShrinkToFit(CellStyle style) {
		try {
			Field field = XSSFCellStyle.class.getDeclaredField("_cellAlignment");
			field.setAccessible(true);
			XSSFCellAlignment e = (XSSFCellAlignment) field.get(style);
			e.getCTCellAlignment().setShrinkToFit(true);
		} catch (Exception e1) {
			// エラーなし
		}
	}
}
