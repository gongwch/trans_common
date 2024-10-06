package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 取引先マスタのExcel定義クラス
 */
public class CustomerMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0150";
	}

	public String getSheetName() {
		// 取引先マスタを返す
		return "C02326";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] { "C00596", "C00786", "C00830", "C00787", "C00836", "C00581", "C00527", "C01152", "C01150",
				"C01151", "C00393", "C00690", "C00871", "C01089", "C01261", "C02038", "C02039", "C00870", "C02040",
				"C01130", "C10133", "C02041", "C02042", "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 0, 10, 40, 40, 80, 40, 10, 80, 50, 50, 12, 12, 12, 6, 6, 12, 16, 12, 14, 10, 48, 20, 11,
				6, 6 };
	}

	private Map mapSiireKbn;

	private Map mapTokuiKbn;

	private Map mapTriKbn;

	/**
	 * 
	 */
	public CustomerMasterReportExcelDefine() {
		// 仕入先区分 - 0:非仕入先 1:仕入先
		mapSiireKbn = new HashMap();
		mapSiireKbn.put(0, "C01295");
		mapSiireKbn.put(1, "C00203");
		// 得意先区分 - 0:非得意先 1:得意先
		mapTokuiKbn = new HashMap();
		mapTokuiKbn.put(0, "C01296");
		mapTokuiKbn.put(1, "C00401");
		// 取引形態区分 - 00:通常 01:スポット
		mapTriKbn = new HashMap();
		mapTriKbn.put("00", "C00372");
		mapTriKbn.put("01", "C00308");

	}

	public List convertDataToList(Object dto, String langCode) {
		TRI_MST entity = (TRI_MST) dto;
		List list = new ArrayList();

		// 会社コード
		list.add(entity.getKAI_CODE());
		// 取引先コード
		list.add(entity.getTRI_CODE());
		// 取引先名称
		list.add(entity.getTRI_NAME());
		// 取引先略称
		list.add(entity.getTRI_NAME_S());
		// 取引先検索名称
		list.add(entity.getTRI_NAME_K());
		// 事業所名称
		list.add(entity.getJIG_NAME());
		// 郵便番号
		list.add(entity.getZIP());
		// 住所カナ
		list.add(entity.getJYU_KANA());
		// 住所１
		list.add(entity.getJYU_1());
		// 住所２
		list.add(entity.getJYU_2());
		// 電話番号
		list.add(entity.getTEL());
		// FAX番号
		list.add(entity.getFAX());
		// 集計相手先コード
		list.add(entity.getSUM_CODE());
		// 仕入先区分
		list.add(new AlignString(getWord(entity.getSIIRE_KBN(), mapSiireKbn, langCode), AlignString.CENTER));
		// 得意先区分
		list.add(new AlignString(getWord(entity.getTOKUI_KBN(), mapTokuiKbn, langCode), AlignString.CENTER));
		// 入金条件締め日
		list.add(entity.getNJ_C_DATE());
		// 入金条件締め後月
		list.add(entity.getNJ_R_MON());
		// 入金条件入金日
		list.add(entity.getNJ_P_DATE());
		// 入金銀行口座ｺｰﾄﾞ
		list.add(entity.getNKN_CBK_CODE());
		// 取引形態区分
		list.add(new AlignString(getWord(entity.getTRI_KBN(), mapTriKbn, langCode), AlignString.CENTER));
		// 振込依頼人名
		list.add(entity.getIRAI_NAME());
		// スポット伝票番号
		list.add(entity.getSPOT_DEN_NO());
		// 入金手数料区分
		list.add(new AlignString(entity.getNYU_TESU_KBN() == 1 ? getWord("C00399", langCode) : getWord("C00327",
			langCode), AlignString.CENTER));
		// 開始年月日
		list.add(entity.getSTR_DATE());
		// 終了年月日
		list.add(entity.getEND_DATE());

		return list;
	}
}
