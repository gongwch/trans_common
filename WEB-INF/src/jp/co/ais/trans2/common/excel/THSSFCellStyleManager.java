package jp.co.ais.trans2.common.excel;

import java.lang.reflect.*;

import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.*;

/**
 * xlsxエクセルのセルスタイルを管理する
 */
public class THSSFCellStyleManager extends TExcelCellStyleManager {

	/**
	 * コンストラクタ
	 * 
	 * @param workBook
	 * @param languageCode
	 */
	public THSSFCellStyleManager(Workbook workBook, String languageCode) {
		super(workBook, languageCode);
	}

	/**
	 * スタイルを初期化する
	 */
	@Override
	protected void initStyleByType() {
		this.headStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		this.dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
	}

	/**
	 * 「縮小して全体を表示する」の設定
	 * 
	 * @param style セルスタイル
	 */
	@Override
	public void setShrinkToFit(CellStyle style) {
		try {
			Field field = HSSFCellStyle.class.getDeclaredField("_format");
			field.setAccessible(true);
			ExtendedFormatRecord e = (ExtendedFormatRecord) field.get(style);
			e.setShrinkToFit(true);
		} catch (Exception e1) {
			// エラーなし
		}
	}
}
