package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 補助科目マスタのExcel定義クラス
 */
public class SubItemMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0090";
	}

	public String getSheetName() {
		return "C02353";
	}

	public String[] getHeaderTexts() {
		return new String[] { "C00596", "C00572", "C00602", "C00701", "C00739", "C00740", "C01264", "C00573", "C01272",
				"C01155", "C01188", "C01049", "C01083", "C01079", "C01081", "C01102", "C01094", "C01134", "C01284",
				"C01120", "C01026", "C01028", "C01030", "C01032", "C01034", "C01036", "C01288", "C01289", "C01290",
				"C01282", "C01088", "C01223", "C01301", "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		return new short[] { 0, 10, 10, 40, 20, 40, 5, 10, 15, 15, 18, 21, 21, 21, 21, 21, 21, 13, 13, 12, 12, 12, 12,
				12, 12, 12, 14, 14, 14, 15, 15, 12, 12, 6, 6, };
	}

	private Map triCodeFlgMap;

	private Map mapUkmKbn;

	private Map mapGlFlg1;

	private Map mapGlFlg2;

	private Map mapGlFlg3;

	private Map mapApFlg1;

	private Map mapApFlg2;

	private Map mapArFlg1;

	private Map mapArFlg2;

	private Map mapFaFlg1;

	private Map mapFaFlg2;

	private Map mapHasFlg;

	private Map mapEmpCodeFlg;

	private Map mapKnrFlg1;

	private Map mapKnrFlg2;

	private Map mapKnrFlg3;

	private Map mapKnrFlg4;

	private Map mapKnrFlg5;

	private Map mapKnrFlg6;

	private Map mapHmFlg1;

	private Map mapHmFlg2;

	private Map mapHmFlg3;

	private Map mapUriZeiFlg;

	private Map mapSirZeiFlg;

	private Map mapMcrFlg;

	/**
	 * 
	 */
	public SubItemMasterReportExcelDefine() {
		// 内訳区分 - 0:無 1:有
		mapUkmKbn = new HashMap();
		mapUkmKbn.put(0, "C02155");
		mapUkmKbn.put(1, "C02156");
		// 取引先入力フラグ - 0:入力不可 2:得意先 3:仕入先 4:得意先
		triCodeFlgMap = new HashMap();
		triCodeFlgMap.put(0, "C01279");
		triCodeFlgMap.put(2, "C00401");
		triCodeFlgMap.put(3, "C00203");
		triCodeFlgMap.put(4, "C02122");
		// 入金伝票入力ﾌﾗｸﾞ - 0:入力不可 1:入力可
		mapGlFlg1 = new HashMap();
		mapGlFlg1.put(0, "C01279");
		mapGlFlg1.put(1, "C01276");
		// 出金伝票入力ﾌﾗｸﾞ - 0:入力不可 1:入力可
		mapGlFlg2 = new HashMap();
		mapGlFlg2.put("0", "C01279");
		mapGlFlg2.put("1", "C01276");
		// 振替伝票入力ﾌﾗｸﾞ - 0:入力不可 1:入力可
		mapGlFlg3 = new HashMap();
		mapGlFlg3.put("0", "C01279");
		mapGlFlg3.put("1", "C01276");
		// 経費精算伝票入力ﾌﾗｸﾞ- 0:入力不可 1:入力可
		mapApFlg1 = new HashMap();
		mapApFlg1.put("0", "C01279");
		mapApFlg1.put("1", "C01276");
		// 請求書伝票入力ﾌﾗｸﾞ- 0:入力不可 1:入力可
		mapApFlg2 = new HashMap();
		mapApFlg2.put("0", "C01279");
		mapApFlg2.put("1", "C01276");
		// 債権計上伝票入力ﾌﾗｸﾞ- 0:入力不可 1:入力可
		mapArFlg1 = new HashMap();
		mapArFlg1.put("0", "C01279");
		mapArFlg1.put("1", "C01276");
		// 債権消込伝票入力ﾌﾗｸﾞ- 0:入力不可 1:入力可
		mapArFlg2 = new HashMap();
		mapArFlg2.put("0", "C01279");
		mapArFlg2.put("1", "C01276");
		// 資産計上伝票入力ﾌﾗｸﾞ- 0:入力不可 1:入力可
		mapFaFlg1 = new HashMap();
		mapFaFlg1.put("0", "C01279");
		mapFaFlg1.put("1", "C01276");
		// 支払依頼伝票入力ﾌﾗｸﾞ - 0:入力不可 1:入力可
		mapFaFlg2 = new HashMap();
		mapFaFlg2.put("0", "C01279");
		mapFaFlg2.put("1", "C01276");
		// 発生日入力ﾌﾗｸﾞ - 0:入力不可 1:入力可
		mapHasFlg = new HashMap();
		mapHasFlg.put(0, "C01279");
		mapHasFlg.put(1, "C01276");
		// 社員入力フラグ- 0:入力不可 1:入力可
		mapEmpCodeFlg = new HashMap();
		mapEmpCodeFlg.put(0, "C01279");
		mapEmpCodeFlg.put(1, "C01276");
		// 管理１入力フラグ- 0:入力不可 1:入力可
		mapKnrFlg1 = new HashMap();
		mapKnrFlg1.put("0", "C01279");
		mapKnrFlg1.put("1", "C01276");
		// 管理2入力フラグ- 0:入力不可 1:入力可
		mapKnrFlg2 = new HashMap();
		mapKnrFlg2.put("0", "C01279");
		mapKnrFlg2.put("1", "C01276");
		// 管理3入力フラグ- 0:入力不可 1:入力可
		mapKnrFlg3 = new HashMap();
		mapKnrFlg3.put("0", "C01279");
		mapKnrFlg3.put("1", "C01276");
		// 管理4入力フラグ- 0:入力不可 1:入力可
		mapKnrFlg4 = new HashMap();
		mapKnrFlg4.put("0", "C01279");
		mapKnrFlg4.put("1", "C01276");
		// 管理5入力フラグ- 0:入力不可 1:入力可
		mapKnrFlg5 = new HashMap();
		mapKnrFlg5.put("0", "C01279");
		mapKnrFlg5.put("1", "C01276");
		// 管理6入力フラグ- 0:入力不可 1:入力可
		mapKnrFlg6 = new HashMap();
		mapKnrFlg6.put("0", "C01279");
		mapKnrFlg6.put("1", "C01276");
		// 非会計1入力フラグ- 0:入力不可 1:入力可
		mapHmFlg1 = new HashMap();
		mapHmFlg1.put("0", "C01279");
		mapHmFlg1.put("1", "C01276");
		// 非会計2入力フラグ- 0:入力不可 1:入力可
		mapHmFlg2 = new HashMap();
		mapHmFlg2.put("0", "C01279");
		mapHmFlg2.put("1", "C01276");
		// 非会計3入力フラグ- 0:入力不可 1:入力可
		mapHmFlg3 = new HashMap();
		mapHmFlg3.put("0", "C01279");
		mapHmFlg3.put("1", "C01276");
		// 売上課税入力フラグ- 0:入力不可 1:入力可
		mapUriZeiFlg = new HashMap();
		mapUriZeiFlg.put("0", "C01279");
		mapUriZeiFlg.put("1", "C01276");
		// 仕入課税入力フラグ- 0:入力不可 1:入力可
		mapSirZeiFlg = new HashMap();
		mapSirZeiFlg.put("0", "C01279");
		mapSirZeiFlg.put("1", "C01276");
		// 多通貨入力フラグ- 0:入力不可 1:入力可
		mapMcrFlg = new HashMap();
		mapMcrFlg.put("0", "C01279");
		mapMcrFlg.put("1", "C01276");
		// //評価替対象フラグ - 0:しない 1:する
		// excFlgMap = new HashMap();
		// excFlgMap.put("0", "C02099");
		// excFlgMap.put("1", "C02100");
	}

	public List convertDataToList(Object dto, String langCode) {
		HKM_MST entity = (HKM_MST) dto;
		List list = new ArrayList();

		// 会社コード
		list.add(entity.getKAI_CODE());
		// 科目コード
		list.add(entity.getKMK_CODE());
		// 補助科目コード
		list.add(entity.getHKM_CODE());
		// 補助科目名称
		list.add(entity.getHKM_NAME());
		// 補助科目略称
		list.add(entity.getHKM_NAME_S());
		// 補助科目検索名称
		list.add(entity.getHKM_NAME_K());
		// 内訳区分
		list.add(new AlignString(getWord(entity.getUKM_KBN(), mapUkmKbn, langCode), AlignString.CENTER));
		// 消費税コード
		list.add(entity.getZEI_CODE());
		// 入金伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getGL_FLG_1(), mapGlFlg1, langCode));
		// 出金伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getGL_FLG_2(), mapGlFlg1, langCode));
		// 振替伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getGL_FLG_3(), mapGlFlg1, langCode));
		// 経費精算伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getAP_FLG_1(), mapGlFlg1, langCode));
		// 請求書伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getAP_FLG_2(), mapGlFlg1, langCode));
		// 債権計上伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getAR_FLG_1(), mapGlFlg1, langCode));
		// 債権消込伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getAR_FLG_2(), mapGlFlg1, langCode));
		// 資産計上伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getFA_FLG_1(), mapGlFlg1, langCode));
		// 支払依頼伝票入力ﾌﾗｸﾞ
		list.add(getWord(entity.getFA_FLG_2(), mapGlFlg1, langCode));
		// 取引先入力フラグ
		list.add(getWord(entity.getTRI_CODE_FLG(), triCodeFlgMap, langCode));
		// 発生日入力フラグ
		list.add(getWord(entity.getHAS_FLG(), mapHasFlg, langCode));
		// 社員入力フラグ
		list.add(getWord(entity.getEMP_CODE_FLG(), mapEmpCodeFlg, langCode));
		// 管理１入力フラグ
		list.add(getWord(entity.getKNR_FLG_1(), mapEmpCodeFlg, langCode));
		// 管理２入力フラグ
		list.add(getWord(entity.getKNR_FLG_2(), mapEmpCodeFlg, langCode));
		// 管理３入力フラグ
		list.add(getWord(entity.getKNR_FLG_3(), mapEmpCodeFlg, langCode));
		// 管理４入力フラグ
		list.add(getWord(entity.getKNR_FLG_4(), mapEmpCodeFlg, langCode));
		// 管理５入力フラグ
		list.add(getWord(entity.getKNR_FLG_5(), mapEmpCodeFlg, langCode));
		// 管理６入力フラグ
		list.add(getWord(entity.getKNR_FLG_6(), mapEmpCodeFlg, langCode));
		// 非会計1入力フラグ
		list.add(getWord(entity.getHM_FLG_1(), mapEmpCodeFlg, langCode));
		// 非会計2入力フラグ
		list.add(getWord(entity.getHM_FLG_2(), mapEmpCodeFlg, langCode));
		// 非会計3入力フラグ
		list.add(getWord(entity.getHM_FLG_3(), mapEmpCodeFlg, langCode));
		// 売上課税入力フラグ
		list.add(getWord(entity.getURI_ZEI_FLG(), mapEmpCodeFlg, langCode));
		// 仕入課税入力フラグ
		list.add(getWord(entity.getSIR_ZEI_FLG(), mapEmpCodeFlg, langCode));
		// 多通貨入力フラグ
		list.add(getWord(entity.getMCR_FLG(), mapEmpCodeFlg, langCode));
		// 評価替対象フラグ
		list.add(entity.getEXC_FLG() == 0 ? getWord("C02099", langCode) : getWord("C02100", langCode));
		// 開始年月日
		list.add(entity.getSTR_DATE());
		// 終了年月日
		list.add(entity.getEND_DATE());

		return list;
	}
}
