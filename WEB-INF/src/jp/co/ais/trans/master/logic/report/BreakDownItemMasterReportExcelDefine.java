package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 内訳科目マスタのExcel定義クラス
 */
public class BreakDownItemMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0100";
	}

	public String getSheetName() {
		// 内訳科目マスタを返す
		return "C02320";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] { "C00596", "C00572", "C00602", "C00603", "C00702", "C01594", "C01593", "C00573", "C01272",
				"C01155", "C01188", "C01049", "C01083", "C01079", "C01081", "C01102", "C01094", "C01223", "C01301",
				"C01134", "C01284", "C01120", "C01026", "C01028", "C01030", "C01032", "C01034", "C01036", "C01288",
				"C01289", "C01290", "C01282", "C01088", "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 0, 10, 10, 10, 40, 20, 40, 10, 15, 15, 15, 21, 21, 20, 20, 20, 20, 12, 12, 12, 12, 10, 12,
				12, 12, 12, 12, 13, 14, 14, 14, 15, 15, 6, 6 };
	}

	private Map mapCheck;

	private Map mapCheck1;

	private Map triCodeFlgMap;

	/**
	 * 
	 */
	public BreakDownItemMasterReportExcelDefine() {
		// 0:入力不可 1:入力可
		mapCheck = new HashMap();
		mapCheck.put(0, "C01279");
		mapCheck.put(1, "C01276");

		// 0:入力必須 1:入力可
		mapCheck1 = new HashMap();
		mapCheck1.put(0, "C01279");
		mapCheck1.put(1, "C02371");

		triCodeFlgMap = new HashMap();
		triCodeFlgMap.put(0, "C01279");
		triCodeFlgMap.put(2, "C00401");
		triCodeFlgMap.put(3, "C00203");
		triCodeFlgMap.put(4, "C02122");
	}

	public List convertDataToList(Object dto, String langCode) {
		UKM_MST entity = (UKM_MST) dto;
		List list = new ArrayList();

		// 会社コード
		list.add(entity.getKAI_CODE());
		// 科目コード
		list.add(entity.getKMK_CODE());
		// 補助科目コード
		list.add(entity.getHKM_CODE());
		// 内訳科目コード
		list.add(entity.getUKM_CODE());
		// 内訳科目名称
		list.add(entity.getUKM_NAME());
		// 内訳科目略称
		list.add(entity.getUKM_NAME_S());
		// 内訳科目検索名称
		list.add(entity.getUKM_NAME_K());
		// 消費税コード
		list.add(entity.getZEI_CODE());
		// 入金伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getGL_FLG_1(), mapCheck, langCode));
		// 出金伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getGL_FLG_2(), mapCheck, langCode));
		// 振替伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getGL_FLG_3(), mapCheck, langCode));
		// 経費精算伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getAP_FLG_1(), mapCheck, langCode));
		// 請求書伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getAP_FLG_2(), mapCheck, langCode));
		// 債権計上伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getAR_FLG_1(), mapCheck, langCode));
		// 債権消込伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getAR_FLG_2(), mapCheck, langCode));
		// 資産計上伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getFA_FLG_1(), mapCheck, langCode));
		// 支払依頼伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getFA_FLG_2(), mapCheck, langCode));
		// 多通貨入力フラグ
		list.add(getWord(entity.getMCR_FLG(), mapCheck, langCode));
		// 評価替対象フラグ
		list.add(getWord(entity.getEXC_FLG(), mapCheck, langCode));
		// 取引先入力フラグ
		list.add(getWord(entity.getTRI_CODE_FLG(), triCodeFlgMap, langCode));
		// 発生日入力フラグ
		list.add(getWord(entity.getHAS_FLG(), mapCheck, langCode));
		// 社員入力フラグ
		list.add(getWord(entity.getEMP_CODE_FLG(), mapCheck1, langCode));
		// 管理１入力フラグ
		list.add(getWord(entity.getKNR_FLG_1(), mapCheck1, langCode));
		// 管理2入力フラグ
		list.add(getWord(entity.getKNR_FLG_2(), mapCheck1, langCode));
		// 管理3入力フラグ
		list.add(getWord(entity.getKNR_FLG_3(), mapCheck1, langCode));
		// 管理4入力フラグ
		list.add(getWord(entity.getKNR_FLG_4(), mapCheck1, langCode));
		// 管理5入力フラグ
		list.add(getWord(entity.getKNR_FLG_5(), mapCheck1, langCode));
		// 管理6入力フラグ
		list.add(getWord(entity.getKNR_FLG_6(), mapCheck1, langCode));
		// 非会計1入力フラグ
		list.add(getWord(entity.getHM_FLG_1(), mapCheck, langCode));
		// 非会計2入力フラグ
		list.add(getWord(entity.getHM_FLG_2(), mapCheck, langCode));
		// 非会計3入力フラグ
		list.add(getWord(entity.getHM_FLG_3(), mapCheck, langCode));
		// 売上課税入力フラグ
		list.add(getWord(entity.getURI_ZEI_FLG(), mapCheck, langCode));
		// 仕入課税入力フラグ
		list.add(getWord(entity.getSIR_ZEI_FLG(), mapCheck, langCode));
		// 開始年月日
		list.add(entity.getSTR_DATE());
		// 終了年月日
		list.add(entity.getEND_DATE());

		return list;
	}
}
