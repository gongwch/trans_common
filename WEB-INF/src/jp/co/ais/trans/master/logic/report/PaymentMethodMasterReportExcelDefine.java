package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 支払方法マスタのExcel定義クラス
 */
public class PaymentMethodMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MP0040";
	}

	public String getSheetName() {
		// 支払方法マスタを返す
		return "C02350";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] { "C00864", "C00865", "C00866", "C00572", "C00602", "C00876", "C00571", "C01096", "C01097",
				"C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 9, 20, 40, 10, 10, 10, 10, 8, 13, 6, 6 };
	}

	private Map hohNaiCodeMap;

	/**
	 * 
	 */
	public PaymentMethodMasterReportExcelDefine() {
		// 01:現金（社員） 03:未払振込（社員） 04:クレジット（社員）
		// 10：社員未収（社員） 11:現金 12:振込（銀行窓口） 13:振込（FB作成）
		// 14:小切手 15:支払手形 16:消込 17:相殺 18: 外国送金
		// 9：振込（国外用窓口） 99: その他
		hohNaiCodeMap = new LinkedHashMap();
		hohNaiCodeMap.put("01", "C02135");
		hohNaiCodeMap.put("03", "C02136");
		hohNaiCodeMap.put("04", "C02137");
		hohNaiCodeMap.put("10", "C02138");
		hohNaiCodeMap.put("11", "C00154");
		hohNaiCodeMap.put("12", "C02139");
		hohNaiCodeMap.put("13", "C02140");
		hohNaiCodeMap.put("14", "C02141");
		hohNaiCodeMap.put("15", "C00230");
		hohNaiCodeMap.put("16", "C02142");
		hohNaiCodeMap.put("17", "C00331");
		hohNaiCodeMap.put("18", "C02143");
		hohNaiCodeMap.put("19", "C02144");
		hohNaiCodeMap.put("99", "C00338");
	}

	public List convertDataToList(Object dto, String langCode) {
		AP_HOH_MST entity = (AP_HOH_MST) dto;
		List list = new ArrayList();
		// 支払方法コード
		list.add(entity.getHOH_HOH_CODE());
		// 支払方法名
		list.add(entity.getHOH_HOH_NAME());
		// 支払方法検索名称
		list.add(entity.getHOH_HOH_NAME_K());
		// 科目コード
		list.add(entity.getHOH_KMK_CODE());
		// 補助科目コード
		list.add(entity.getHOH_HKM_CODE());
		// 内訳科目コード
		list.add(entity.getHOH_UKM_CODE());
		// 計上部門コード
		list.add(entity.getHOH_DEP_CODE());
		// 支払対象区分
		list.add(new AlignString(0 == entity.getHOH_SIH_KBN() ? getWord("C01119", langCode) : getWord("C01124",
				langCode), AlignString.CENTER));
		// 支払内部コード
		list.add(getWord(entity.getHOH_NAI_CODE(), hohNaiCodeMap, langCode));
		// 開始年月日
		list.add(entity.getSTR_DATE());
		// 終了年月日
		list.add(entity.getEND_DATE());

		return list;
	}
}
