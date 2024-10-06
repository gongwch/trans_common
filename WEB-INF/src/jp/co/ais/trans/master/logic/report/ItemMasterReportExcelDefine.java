package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 科目マスタのExcel定義クラス
 */
public class ItemMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0080";
	}

	public String getSheetName() {
		// 科目マスタを返す
		return "C02342";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] { "C00596", "C00572", "C00700", "C00730", "C00731", "C01148", "C01007", "C01226", "C01314",
				"C02066", "C00573", "C00968", "C00959", "C00960", "C00962", "C01134", "C01284", "C01217", "C02078",
				"C01301", "C01272", "C01155", "C01188", "C01049", "C01083", "C01079", "C01081", "C01102", "C01094",
				"C01223", "C01120", "C01026", "C01028", "C01030", "C01032", "C01034", "C01036", "C01288", "C01289",
				"C01290", "C01282", "C01088", "C02076", "C02077", "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 0, 10, 40, 20, 40, 4, 5, 4, 4, 7, 10, 11, 11, 11, 11, 12, 12, 13, 18, 12, 15, 15, 15, 21,
				21, 21, 21, 21, 21, 12, 10, 12, 12, 12, 12, 12, 12, 14, 14, 14, 15, 15, 9, 9, 6, 6 };
	}

	private Map kmkShuMap;

	private Map hkmKbnMap;

	private Map kmkCntGlMap;

	private Map kmkCntApMap;

	private Map kmkCntArMap;

	private Map triCodeFlgMap;

	private Map kmkCntBgMap;

	private Map kmkCntSousaiMap;

	/**
	 * 
	 */
	public ItemMasterReportExcelDefine() {

		// 科目種別 - 0:貸借科目 1:損益科目 2:利益処分科目 3:製造原価科目 9:非会計科目
		kmkShuMap = new HashMap();
		kmkShuMap.put("0", "C02108");
		kmkShuMap.put("1", "C02109");
		kmkShuMap.put("2", "C02110");
		kmkShuMap.put("3", "C02111");
		kmkShuMap.put("9", "C02112");
		// 補助区分 - 0:なし 1:あり
		hkmKbnMap = new HashMap();
		hkmKbnMap.put(0, "C00412");
		hkmKbnMap.put(1, "C00006");
		// ＧＬ科目制御区分 - 00:通常 01:前期繰越利益 04:資金科目 05:売上科目 06:為替換算差損益 07:仮勘定 08:為替差損 09:為替差益
		kmkCntGlMap = new HashMap();
		kmkCntGlMap.put("00", "C00372");
		kmkCntGlMap.put("01", "C02113");
		kmkCntGlMap.put("04", "C02114");
		kmkCntGlMap.put("05", "C02115");
		kmkCntGlMap.put("06", "C02116");
		kmkCntGlMap.put("07", "C02117");
		kmkCntGlMap.put("08", "C00995");
		kmkCntGlMap.put("09", "C01783");
		// AP科目制御区分 - 00:通常 01:債務管理科目 02：従業員仮払科目
		kmkCntApMap = new HashMap();
		kmkCntApMap.put("00", "C00372");
		kmkCntApMap.put("01", "C02118");
		kmkCntApMap.put("02", "C02119");
		// AR科目制御区分 - 00:通常 01:債権管理科目 02:債権回収仮勘定
		kmkCntArMap = new HashMap();
		kmkCntArMap.put("00", "C00372");
		kmkCntArMap.put("01", "C02120");
		kmkCntArMap.put("02", "C02121");
		// 取引先入力フラグ - 0:入力不可 2:得意先 3:仕入先 4:得意先
		triCodeFlgMap = new HashMap();
		triCodeFlgMap.put(0, "C01279");
		triCodeFlgMap.put(2, "C00401");
		triCodeFlgMap.put(3, "C00203");
		triCodeFlgMap.put(4, "C02122");
		// BG科目制御区分 - 00:通常 11:予算管理対象
		kmkCntBgMap = new HashMap();
		kmkCntBgMap.put("00", "C00372");
		kmkCntBgMap.put("11", "C02125");
		// 相殺科目制御区分 - 0:しない 1:する
		kmkCntSousaiMap = new HashMap();
		kmkCntSousaiMap.put("0", "C02099");
		kmkCntSousaiMap.put("1", "C02100");
	}

	public List convertDataToList(Object dto, String langCode) {
		KMK_MST entity = (KMK_MST) dto;
		List list = new ArrayList();

		boolean input = !(entity.getSUM_KBN() == 0);

		// 会社コード
		list.add(entity.getKAI_CODE());
		// 科目コード
		list.add(entity.getKMK_CODE());
		// 科目名称
		list.add(entity.getKMK_NAME());
		// 科目略称
		list.add(entity.getKMK_NAME_S());
		// 科目検索名称
		list.add(entity.getKMK_NAME_K());
		// 集計区分
		String sumKbn = String.valueOf(entity.getSUM_KBN());
		if ("0".equals(sumKbn)) {
			list.add(new AlignString(getWord("C02157", langCode), AlignString.CENTER));
		} else if ("1".equals(sumKbn)) {
			list.add(new AlignString(getWord("C02158", langCode), AlignString.CENTER));
		} else {
			list.add(new AlignString(getWord("C02159", langCode), AlignString.CENTER));
		}
		// 科目種別
		list.add(getWord(entity.getKMK_SHU(), kmkShuMap, langCode));
		// 貸借区分
		String dcKbn = entity.getDC_KBN() == 0 ? getWord("C01125", langCode) : getWord("C01228", langCode);
		list.add(new AlignString(dcKbn, AlignString.CENTER));
		// 補助区分
		String hkmKbn = getWord(entity.getHKM_KBN(), hkmKbnMap, langCode);
		list.add(new AlignString(input ? "" : hkmKbn, AlignString.CENTER));
		// 固定部門ｺｰﾄﾞ
		list.add(entity.getKOTEI_DEP_CODE());
		// 消費税コード
		list.add(entity.getZEI_CODE());
		// GL科目制御区分
		String kmkCntGl = getWord(entity.getKMK_CNT_GL(), kmkCntGlMap, langCode);
		list.add(new AlignString(input ? "" : kmkCntGl, AlignString.CENTER));
		// AP科目制御区分
		String kmkCntAp = getWord(entity.getKMK_CNT_AP(), kmkCntApMap, langCode);
		list.add(new AlignString(input ? "" : kmkCntAp, AlignString.CENTER));
		// AR科目制御区分
		String kmkCntAR = getWord(entity.getKMK_CNT_AR(), kmkCntArMap, langCode);
		list.add(new AlignString(input ? "" : kmkCntAR, AlignString.CENTER));
		// BG科目制御区分
		String kmkCntBg = getWord(entity.getKMK_CNT_BG(), kmkCntBgMap, langCode);
		list.add(new AlignString(input ? "" : kmkCntBg, AlignString.CENTER));
		// 取引先入力フラグ
		String triCodeFlg = getWord(entity.getTRI_CODE_FLG(), triCodeFlgMap, langCode);
		list.add(input ? "" : triCodeFlg);
		// 発生日入力フラグ
		String hasFlg = entity.getHAS_FLG() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : hasFlg);
		// 相殺科目制御区分
		String kmkCntSousai = getWord(entity.getKMK_CNT_SOUSAI(), kmkCntSousaiMap, langCode);
		list.add(new AlignString(input ? "" : kmkCntSousai, AlignString.CENTER));
		// BS勘定消込区分
		String kesiKbn = entity.getKESI_KBN() == 0 ? getWord("C02099", langCode) : getWord("C02100", langCode);
		list.add(new AlignString(input ? "" : kesiKbn, AlignString.CENTER));
		// 評価替対象フラグ
		String excFlg = String.valueOf(entity.getEXC_FLG());
		if ("0".equals(excFlg)) {
			list.add(input ? "" : getWord("C02099", langCode));
		} else if ("1".equals(excFlg)) {
			list.add(input ? "" : getWord("C02123", langCode));
		} else if ("2".equals(excFlg)) {
			list.add(input ? "" : getWord("C02124", langCode));
		}
		// 入金伝票入力ﾌﾗｸﾞ
		String glFlg1 = entity.getGL_FLG_1() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : glFlg1);
		// 出金伝票入力ﾌﾗｸﾞ
		String glFlg2 = entity.getGL_FLG_2() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : glFlg2);
		// 振替伝票入力ﾌﾗｸﾞ
		String glFlg3 = entity.getGL_FLG_3() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : glFlg3);
		// 経費精算伝票入力ﾌﾗｸﾞ
		String apFlg1 = entity.getAP_FLG_1() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : apFlg1);
		// 請求書伝票入力ﾌﾗｸﾞ
		String apFlg2 = entity.getAP_FLG_2() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : apFlg2);
		// 債権計上伝票入力ﾌﾗｸﾞ
		String arFlg1 = entity.getAR_FLG_1() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : arFlg1);
		// 債権消込伝票入力ﾌﾗｸﾞ
		String arFlg2 = entity.getAR_FLG_2() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : arFlg2);
		// 資産計上伝票入力ﾌﾗｸﾞ
		String faFlg1 = entity.getFA_FLG_1() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : faFlg1);
		// 支払依頼伝票入力ﾌﾗｸﾞ
		String faFlg2 = entity.getFA_FLG_2() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : faFlg2);
		// 多通貨入力フラグ
		String mcrFlg = entity.getMCR_FLG() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : mcrFlg);
		// 社員入力フラグ
		String empCodeFlg = entity.getEMP_CODE_FLG() == 0 ? getWord("C01279", langCode) : getWord("C02371", langCode);
		list.add(input ? "" : empCodeFlg);
		// 管理１入力フラグ
		String knrFlg1 = entity.getKNR_FLG_1() == 0 ? getWord("C01279", langCode) : getWord("C02371", langCode);
		list.add(input ? "" : knrFlg1);
		// 管理２入力フラグ
		String knrFlg2 = entity.getKNR_FLG_2() == 0 ? getWord("C01279", langCode) : getWord("C02371", langCode);
		list.add(input ? "" : knrFlg2);
		// 管理３入力フラグ
		String knrFlg3 = entity.getKNR_FLG_3() == 0 ? getWord("C01279", langCode) : getWord("C02371", langCode);
		list.add(input ? "" : knrFlg3);
		// 管理４入力フラグ
		String knrFlg4 = entity.getKNR_FLG_4() == 0 ? getWord("C01279", langCode) : getWord("C02371", langCode);
		list.add(input ? "" : knrFlg4);
		// 管理５入力フラグ
		String knrFlg5 = entity.getKNR_FLG_5() == 0 ? getWord("C01279", langCode) : getWord("C02371", langCode);
		list.add(input ? "" : knrFlg5);
		// 管理６入力フラグ
		String knrFlg6 = entity.getKNR_FLG_6() == 0 ? getWord("C01279", langCode) : getWord("C02371", langCode);
		list.add(input ? "" : knrFlg6);
		// 非会計1入力フラグ
		String hmFlg1 = entity.getHM_FLG_1() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : hmFlg1);
		// 非会計2入力フラグ
		String hmFlg2 = entity.getHM_FLG_2() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : hmFlg2);
		// 非会計3入力フラグ
		String hmFlg3 = entity.getHM_FLG_3() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : hmFlg3);
		// 売上課税入力フラグ
		String uriZeiFlg = entity.getURI_ZEI_FLG() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : uriZeiFlg);
		// 仕入課税入力フラグ
		String sirZeiFlg = entity.getSIR_ZEI_FLG() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : sirZeiFlg);
		// 借方資金コード
		list.add(entity.getSKN_CODE_DR());
		// 貸方資金コード
		list.add(entity.getSKN_CODE_CR());
		// 開始年月日
		list.add(entity.getSTR_DATE());
		// 終了年月日
		list.add(entity.getEND_DATE());

		return list;
	}
}
