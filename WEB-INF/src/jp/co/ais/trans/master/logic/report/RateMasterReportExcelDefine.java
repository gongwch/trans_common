package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * レートマスタのExcel定義クラス
 */
public class RateMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0310";
	}

	public String getSheetName() {
		// レートマスタを返す
		return "C02352";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] { "C00596", "C00665", "C02046", "C00556" };
	}

	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 0, 6, 11, 11 };
	}

	public List convertDataToList(Object dto, String langCode) {
		RATE_MST entity = (RATE_MST) dto;
		List list = new ArrayList();

		// 会社コード
		list.add(entity.getKAI_CODE());
		// 通貨コード
		list.add(entity.getCUR_CODE());
		// 適用開始日付
		list.add(entity.getSTR_DATE());
		// レート
		FormatDecimal format = new FormatDecimal(entity.getCUR_RATE(), "###,##0.0000");
		list.add(format);

		return list;
	}
}
