package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 通貨マスタのExcel定義クラス
 */
public class CurrencyMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0300";
	}

	public String getSheetName() {
		// 通貨マスタを返す
		return "C01985";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] { "C00596", "C00665", "C00880", "C00881", "C00882", "C00896", "C00897", "C00884", "C00055",
				"C00261" };
	}

	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 0, 6, 40, 20, 20, 6, 5, 20, 6, 6 };
	}

	private Map mapConvKbn;

	/**
	 * 
	 */
	public CurrencyMasterReportExcelDefine() {
		// 換算区分 - 0:掛算 1:割算
		mapConvKbn = new HashMap();
		mapConvKbn.put(0, "C00065");
		mapConvKbn.put(1, "C00563");
	}

	public List convertDataToList(Object dto, String langCode) {
		CUR_MST entity = (CUR_MST) dto;
		List list = new ArrayList();

		// 会社コード
		list.add(entity.getKAI_CODE());
		// 通貨コード
		// list.add(entity.getCUR_CODE());

		list.add(new AlignString(entity.getCUR_CODE(), AlignString.CENTER));

		// 通貨名称
		list.add(entity.getCUR_NAME());
		// 通貨略称
		list.add(entity.getCUR_NAME_S());
		// 通貨検索名称
		list.add(entity.getCUR_NAME_K());
		// レート係数
		list.add(entity.getRATE_POW());
		// 換算区分
		list.add(new AlignString(getWord(entity.getCONV_KBN(), mapConvKbn, langCode), AlignString.CENTER));
		// 小数点以下桁数
		list.add(entity.getDEC_KETA());
		// 開始年月日
		list.add(entity.getSTR_DATE());
		// 終了年月日
		list.add(entity.getEND_DATE());

		return list;
	}
}
