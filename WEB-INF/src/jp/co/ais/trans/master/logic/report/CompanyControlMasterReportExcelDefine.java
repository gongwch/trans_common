package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ïÐRg[}X^ÌExcelè`NX
 */
public class CompanyControlMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0030";
	}

	public String getSheetName() {
		// ïÐRg[}X^ðÔ·
		return "C00910";
	}

	public String[] getHeaderTexts() {

		if (!"MG0031".equals(Util.avoidNull(fileName))) {
			// ^CgðÔ·
			return new String[] { "C00596", "C00700", "C00701", "C00942", "C00702", "C00936", "C00937", "C00938",
					"C00939", "C00940", "C00941", "C00703", "C00704", "C00705", "C00706", "C00707", "C00708", "C00943",
					"C00944", "C00945", "C00709", "C00710", "C00711", "C00105", "C00713", "C00714", "C00715", "C00224",
					"C01248", "C01000", "C02152", "C02153", "C00717", "C00557", "C00082", "C00083" };
		}

		String strDirectKbn = getWord("C10547", getLangCode()) + getWord("C00017", getLangCode())
			+ getWord("C00127", getLangCode());
		// ^CgðÔ·
		return new String[] { "C00596", "C00700", "C00701", "C00942", "C00702", "C00936", "C00937", "C00938", "C00939",
				"C00940", "C00941", "C00703", "C00704", "C00705", "C00706", "C00707", "C00708", "C00943", "C00944",
				"C00945", "C00709", "C00710", "C00711", "C00105", "C00713", "C00714", "C00715", "C00224", "C01248",
				"C01000", "C02152", "C02153", "C00717", "C00557", "C00082", "C00083", strDirectKbn };
	}

	public short[] getColumnWidths() {

		if (!"MG0031".equals(Util.avoidNull(fileName))) {
			// ñðÔ·
			return new short[] { 10, 10, 10, 8, 10, 6, 6, 6, 6, 6, 6, 10, 10, 10, 10, 10, 10, 13, 13, 13, 13, 13, 13,
					2, 10, 10, 10, 15, 10, 10, 10, 10, 10, 14, 18, 18 };
		}

		// ñðÔ·
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
	 * RXgN^
	 * 
	 * @param fileName vOhc
	 */
	public CompanyControlMasterReportExcelDefine(String fileName) {
		this.fileName = fileName;

		// 0:gpµÈ¢ 1:gp·é
		mapCheck = new HashMap();
		mapCheck.put(0, "C00282");
		mapCheck.put(1, "C00281");

		// óüÌúl
		mapCheck1 = new HashMap();
		mapCheck1.put(0, "C02367");
		mapCheck1.put(1, "C02368");

		// »ê³FÌ×¸Þ,o³FÌ×¸Þ
		mapCheck2 = new HashMap();
		mapCheck2.put(0, "C02099");
		mapCheck2.put(1, "C02100");

		// ñïv¾×æª1-3
		mapCmpHmKbn = new HashMap();
		mapCmpHmKbn.put(0, "C00282");
		mapCmpHmKbn.put(1, "C02160");
		mapCmpHmKbn.put(2, "C02161");
		mapCmpHmKbn.put(3, "C00446");

		// ©®ÝèÚP-3
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

		// 0:ØÌ 1:Øã 2:lÌÜü
		mapRadio = new HashMap();
		mapRadio.put(0, "C00121");
		mapRadio.put(1, "C00120");
		mapRadio.put(2, "C00215");
	}

	public List convertDataToList(Object dto, String langCode) {
		CMP_MST entity = (CMP_MST) dto;
		List list = new ArrayList();

		// ïÐR[h
		list.add(entity.getKAI_CODE());
		// ÈÚ¼Ì
		list.add(entity.getCMP_KMK_NAME());
		// âÈÚ¼Ì
		list.add(entity.getCMP_HKM_NAME());
		// àóÈÚÇ
		list.add(new AlignString(getWord(entity.getCMP_UKM_KBN(), mapCheck, langCode), AlignString.CENTER));
		// àóÈÚ¼Ì
		list.add(entity.getCMP_UKM_NAME());
		// Çæª1
		list.add(new AlignString(getWord(entity.getKNR_KBN_1(), mapCheck, langCode), AlignString.CENTER));
		// Çæª2
		list.add(new AlignString(getWord(entity.getKNR_KBN_2(), mapCheck, langCode), AlignString.CENTER));
		// Çæª3
		list.add(new AlignString(getWord(entity.getKNR_KBN_3(), mapCheck, langCode), AlignString.CENTER));
		// Çæª4
		list.add(new AlignString(getWord(entity.getKNR_KBN_4(), mapCheck, langCode), AlignString.CENTER));
		// Çæª5
		list.add(new AlignString(getWord(entity.getKNR_KBN_5(), mapCheck, langCode), AlignString.CENTER));
		// Çæª6
		list.add(new AlignString(getWord(entity.getKNR_KBN_6(), mapCheck, langCode), AlignString.CENTER));
		// Ç¼Ì1
		list.add(entity.getKNR_NAME_1());
		// Ç¼Ì2
		list.add(entity.getKNR_NAME_2());
		// Ç¼Ì3
		list.add(entity.getKNR_NAME_3());
		// Ç¼Ì4
		list.add(entity.getKNR_NAME_4());
		// Ç¼Ì5
		list.add(entity.getKNR_NAME_5());
		// Ç¼Ì6
		list.add(entity.getKNR_NAME_6());
		// ñïv¾×æª1
		list.add(new AlignString(getWord(entity.getCMP_HM_KBN_1(), mapCmpHmKbn, langCode), AlignString.CENTER));
		// ñïv¾×æª2
		list.add(new AlignString(getWord(entity.getCMP_HM_KBN_2(), mapCmpHmKbn, langCode), AlignString.CENTER));
		// ñïv¾×æª3
		list.add(new AlignString(getWord(entity.getCMP_HM_KBN_3(), mapCmpHmKbn, langCode), AlignString.CENTER));
		// ñïv¾×¼Ì1
		list.add(entity.getCMP_HM_NAME_1());
		// ñïv¾×¼Ì2
		list.add(entity.getCMP_HM_NAME_2());
		// ñïv¾×¼Ì3
		list.add(entity.getCMP_HM_NAME_3());
		// úñ
		list.add(entity.getCMP_KISYU());
		// ©®ÝèÚP
		list.add(getWord(entity.getJID_1(), mapJid, langCode));
		// ©®ÝèÚ2
		list.add(getWord(entity.getJID_2(), mapJid, langCode));
		// ©®ÝèÚ3
		list.add(getWord(entity.getJID_3(), mapJid, langCode));
		// ©®ÌÔ
		String autoNoKeta = String.valueOf(entity.getAUTO_NO_KETA());
		if ("0".equals(autoNoKeta)) {
			list.add("");
		} else {
			list.add(entity.getAUTO_NO_KETA());
		}
		// `[óüæª
		list.add(new AlignString(getWord(entity.getPRINT_KBN(), mapCheck, langCode), AlignString.CENTER));
		// óüÌúl
		list.add(getWord(entity.getPRINT_DEF(), mapCheck1, langCode));
		// »ê³FÌ×¸Þ
		list.add(getWord(entity.getCMP_G_SHONIN_FLG(), mapCheck2, langCode));
		// o³FÌ×¸Þ
		list.add(getWord(entity.getCMP_K_SHONIN_FLG(), mapCheck2, langCode));
		// {MÊÝR[h
		list.add(new AlignString(entity.getCUR_CODE(), AlignString.CENTER));
		// [g·Z[
		list.add(getWord(entity.getRATE_KBN(), mapRadio, langCode));
		// ¼óÁïÅ[
		list.add(getWord(entity.getKU_KBN(), mapRadio, langCode));
		// ¼¥ÁïÅ[
		list.add(getWord(entity.getKB_KBN(), mapRadio, langCode));
		if ("MG0031".equals(Util.avoidNull(fileName))) {
			// ¼Úóüæª
			list.add(getWord(0, mapCheck1, langCode));
		}

		return list;
	}
}
