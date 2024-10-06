package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 会社コントロールマスタのExcel定義クラス
 */
public class CompanyControlMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0030";
	}

	public String getSheetName() {
		// 会社コントロールマスタを返す
		return "C00910";
	}

	public String[] getHeaderTexts() {

		if (!"MG0031".equals(Util.avoidNull(fileName))) {
			// タイトルを返す
			return new String[] { "C00596", "C00700", "C00701", "C00942", "C00702", "C00936", "C00937", "C00938",
					"C00939", "C00940", "C00941", "C00703", "C00704", "C00705", "C00706", "C00707", "C00708", "C00943",
					"C00944", "C00945", "C00709", "C00710", "C00711", "C00105", "C00713", "C00714", "C00715", "C00224",
					"C01248", "C01000", "C02152", "C02153", "C00717", "C00557", "C00082", "C00083" };
		}

		String strDirectKbn = getWord("C10547", getLangCode()) + getWord("C00017", getLangCode())
			+ getWord("C00127", getLangCode());
		// タイトルを返す
		return new String[] { "C00596", "C00700", "C00701", "C00942", "C00702", "C00936", "C00937", "C00938", "C00939",
				"C00940", "C00941", "C00703", "C00704", "C00705", "C00706", "C00707", "C00708", "C00943", "C00944",
				"C00945", "C00709", "C00710", "C00711", "C00105", "C00713", "C00714", "C00715", "C00224", "C01248",
				"C01000", "C02152", "C02153", "C00717", "C00557", "C00082", "C00083", strDirectKbn };
	}

	public short[] getColumnWidths() {

		if (!"MG0031".equals(Util.avoidNull(fileName))) {
			// 列幅を返す
			return new short[] { 10, 10, 10, 8, 10, 6, 6, 6, 6, 6, 6, 10, 10, 10, 10, 10, 10, 13, 13, 13, 13, 13, 13,
					2, 10, 10, 10, 15, 10, 10, 10, 10, 10, 14, 18, 18 };
		}

		// 列幅を返す
		return new short[] { 10, 10, 10, 8, 10, 6, 6, 6, 6, 6, 6, 10, 10, 10, 10, 10, 10, 13, 13, 13, 13, 13, 13, 2,
				10, 10, 10, 15, 10, 10, 10, 10, 10, 14, 18, 18, 14 };
	}

	private Map mapCheck;

	private Map mapCheck1;

	private Map mapCheck2;

	private Map mapCmpHmKbn;

	private Map mapJid;

	private Map mapRadio;

	private String fileName;

	/**
	 * コンストラクタ
	 * 
	 * @param fileName プログラムＩＤ
	 */
	public CompanyControlMasterReportExcelDefine(String fileName) {
		this.fileName = fileName;

		// 0:使用しない 1:使用する
		mapCheck = new HashMap();
		mapCheck.put(0, "C00282");
		mapCheck.put(1, "C00281");

		// 印刷時の初期値
		mapCheck1 = new HashMap();
		mapCheck1.put(0, "C02367");
		mapCheck1.put(1, "C02368");

		// 現場承認ﾌﾗｸﾞ,経理承認ﾌﾗｸﾞ
		mapCheck2 = new HashMap();
		mapCheck2.put(0, "C02099");
		mapCheck2.put(1, "C02100");

		// 非会計明細区分1-3
		mapCmpHmKbn = new HashMap();
		mapCmpHmKbn.put(0, "C00282");
		mapCmpHmKbn.put(1, "C02160");
		mapCmpHmKbn.put(2, "C02161");
		mapCmpHmKbn.put(3, "C00446");

		// 自動設定項目１-3
		mapJid = new HashMap();
		mapJid.put("0", "C00412");
		mapJid.put("1", "C02162");
		mapJid.put("2", "C02163");
		mapJid.put("3", "C02164");
		mapJid.put("4", "C02165");
		mapJid.put("5", "C00528");
		mapJid.put("6", "C00467");
		mapJid.put("7", "C00980");
		mapJid.put("8", "C00596");

		// 0:切捨 1:切上 2:四捨五入
		mapRadio = new HashMap();
		mapRadio.put(0, "C00121");
		mapRadio.put(1, "C00120");
		mapRadio.put(2, "C00215");
	}

	public List convertDataToList(Object dto, String langCode) {
		CMP_MST entity = (CMP_MST) dto;
		List list = new ArrayList();

		// 会社コード
		list.add(entity.getKAI_CODE());
		// 科目名称
		list.add(entity.getCMP_KMK_NAME());
		// 補助科目名称
		list.add(entity.getCMP_HKM_NAME());
		// 内訳科目管理
		list.add(new AlignString(getWord(entity.getCMP_UKM_KBN(), mapCheck, langCode), AlignString.CENTER));
		// 内訳科目名称
		list.add(entity.getCMP_UKM_NAME());
		// 管理区分1
		list.add(new AlignString(getWord(entity.getKNR_KBN_1(), mapCheck, langCode), AlignString.CENTER));
		// 管理区分2
		list.add(new AlignString(getWord(entity.getKNR_KBN_2(), mapCheck, langCode), AlignString.CENTER));
		// 管理区分3
		list.add(new AlignString(getWord(entity.getKNR_KBN_3(), mapCheck, langCode), AlignString.CENTER));
		// 管理区分4
		list.add(new AlignString(getWord(entity.getKNR_KBN_4(), mapCheck, langCode), AlignString.CENTER));
		// 管理区分5
		list.add(new AlignString(getWord(entity.getKNR_KBN_5(), mapCheck, langCode), AlignString.CENTER));
		// 管理区分6
		list.add(new AlignString(getWord(entity.getKNR_KBN_6(), mapCheck, langCode), AlignString.CENTER));
		// 管理名称1
		list.add(entity.getKNR_NAME_1());
		// 管理名称2
		list.add(entity.getKNR_NAME_2());
		// 管理名称3
		list.add(entity.getKNR_NAME_3());
		// 管理名称4
		list.add(entity.getKNR_NAME_4());
		// 管理名称5
		list.add(entity.getKNR_NAME_5());
		// 管理名称6
		list.add(entity.getKNR_NAME_6());
		// 非会計明細区分1
		list.add(new AlignString(getWord(entity.getCMP_HM_KBN_1(), mapCmpHmKbn, langCode), AlignString.CENTER));
		// 非会計明細区分2
		list.add(new AlignString(getWord(entity.getCMP_HM_KBN_2(), mapCmpHmKbn, langCode), AlignString.CENTER));
		// 非会計明細区分3
		list.add(new AlignString(getWord(entity.getCMP_HM_KBN_3(), mapCmpHmKbn, langCode), AlignString.CENTER));
		// 非会計明細名称1
		list.add(entity.getCMP_HM_NAME_1());
		// 非会計明細名称2
		list.add(entity.getCMP_HM_NAME_2());
		// 非会計明細名称3
		list.add(entity.getCMP_HM_NAME_3());
		// 期首月
		list.add(entity.getCMP_KISYU());
		// 自動設定項目１
		list.add(getWord(entity.getJID_1(), mapJid, langCode));
		// 自動設定項目2
		list.add(getWord(entity.getJID_2(), mapJid, langCode));
		// 自動設定項目3
		list.add(getWord(entity.getJID_3(), mapJid, langCode));
		// 自動採番部桁数
		String autoNoKeta = String.valueOf(entity.getAUTO_NO_KETA());
		if ("0".equals(autoNoKeta)) {
			list.add("");
		} else {
			list.add(entity.getAUTO_NO_KETA());
		}
		// 伝票印刷区分
		list.add(new AlignString(getWord(entity.getPRINT_KBN(), mapCheck, langCode), AlignString.CENTER));
		// 印刷時の初期値
		list.add(getWord(entity.getPRINT_DEF(), mapCheck1, langCode));
		// 現場承認ﾌﾗｸﾞ
		list.add(getWord(entity.getCMP_G_SHONIN_FLG(), mapCheck2, langCode));
		// 経理承認ﾌﾗｸﾞ
		list.add(getWord(entity.getCMP_K_SHONIN_FLG(), mapCheck2, langCode));
		// 本邦通貨コード
		list.add(new AlignString(entity.getCUR_CODE(), AlignString.CENTER));
		// レート換算端数処理
		list.add(getWord(entity.getRATE_KBN(), mapRadio, langCode));
		// 仮受消費税端数処理
		list.add(getWord(entity.getKU_KBN(), mapRadio, langCode));
		// 仮払消費税端数処理
		list.add(getWord(entity.getKB_KBN(), mapRadio, langCode));
		if ("MG0031".equals(Util.avoidNull(fileName))) {
			// 直接印刷区分
			list.add(getWord(0, mapCheck1, langCode));
		}

		return list;
	}
}
