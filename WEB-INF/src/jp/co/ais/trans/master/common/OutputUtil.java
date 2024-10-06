package jp.co.ais.trans.master.common;

import java.util.*;

import org.apache.poi.hssf.usermodel.*;

import jp.co.ais.trans.common.report.*;

/**
 * 外部出力
 */
public class OutputUtil extends ExcelList {

	/** エクセルワークブック */
	private ReportExcelDefine reportDefine;

	/** シート */
	private HSSFSheet sheet;

	/**
	 * コンストラクタ
	 * 
	 * @param reportDefine
	 * @param langCode 言語コード
	 */
	public OutputUtil(ReportExcelDefine reportDefine, String langCode) {
		super(langCode);

		this.reportDefine = reportDefine;

		// シート名 (名称+メンテナンス)
		String sheetNameID = this.reportDefine.getSheetName();
		String sheetName = getWord(sheetNameID);

		// ヘッダー文字
		String headerTitle = getWord(sheetNameID);

		// 新規ワークシートを作成する
		sheet = addSheet(sheetName, headerTitle);

		// ヘッダ(カラム名)の設定
		HSSFRow newrow = sheet.createRow(0);
		String[] headerTexts = this.reportDefine.getHeaderTexts(); // カラム名テキスト
		short[] columnWidths = this.reportDefine.getColumnWidths(); // 各カラム幅
		short index = 0;

		// Column width の設定
		for (short i = 0; i < headerTexts.length; i++) {
			short columnWidth = 0;

			if (columnWidths != null && columnWidths.length > i) {
				columnWidth = columnWidths[i];
			}
			if (columnWidth > 0) {
				String headerText = getWord(headerTexts[i]);

				// カラム名設定
				addHeaderCell(newrow, index, headerText);

				// 各カラム幅を設定
				sheet.setColumnWidth(index, (short) ((columnWidths[i] + 7) * 256));
				index++;
			}
		}
	}

	/**
	 * エクセル生成
	 * 
	 * @param entities
	 */
	public void createExcel(List entities) {
		int nextRowIndex = sheet.getLastRowNum() + 1;
		short[] columnWidths = reportDefine.getColumnWidths();

		for (int i = 0; i < entities.size(); i++) {
			// 行オブジェクトの作成
			HSSFRow newrow = sheet.createRow(nextRowIndex + i);

			// エンティティ ⇒ List
			Object entity = entities.get(i);
			List list = reportDefine.convertDataToList(entity, getLanguageCode());
			short index = 0;

			for (short j = 0; j < list.size(); j++) {
				Object obj = list.get(j);
				short columnWidth = 0;

				if (columnWidths != null && columnWidths.length > j) {
					columnWidth = columnWidths[j];
				}

				if (columnWidth > 0) {

					if (obj == null) {
						addEmptyCell(newrow, index);
					} else if (obj instanceof FormatDecimal) {
						FormatDecimal fd = (FormatDecimal) obj;
						addCell(newrow, index, fd.getValue(), fd.getFormat());
					} else if (obj instanceof Number) {
						Number num = (Number) obj;
						addCell(newrow, index, num.doubleValue());
					} else if (obj instanceof Date) {
						Date date = (Date) obj;
						addDateCell(newrow, index, date);
					} else if (obj instanceof AlignString) {
						AlignString str = (AlignString) obj;

						switch (str.getAlign()) {
							case AlignString.RIGHT:
								addRightCell(newrow, index, str.getString());
								break;
							case AlignString.CENTER:
								addCenterCell(newrow, index, str.getString());
								break;
							default:
								addCell(newrow, index, str.getString());
								break;
						}
					} else {
						addCell(newrow, index, String.valueOf(obj));
					}
					index++;
				}
			}
		}
	}

	/**
	 * シートを返す
	 * 
	 * @return シート
	 */
	public HSSFSheet getSheet() {
		return this.sheet;
	}
}
