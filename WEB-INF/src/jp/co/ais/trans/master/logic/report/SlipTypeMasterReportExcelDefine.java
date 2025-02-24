package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * `[íÊ}X^ }X^ÌExcelè`NX
 */
public class SlipTypeMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0330";
	}

	public String getSheetName() {
		// }X^ðÔ·
		return "C00912";
	}

	public String[] getHeaderTexts() {
		// `[íÊ}X^CgðÔ·
		return new String[] { "C00596", "C00837", "C00980", "C00838", "C00839", "C02757", "C02047", "C00355", "C00392",
				"C00018", "C00287", "C00299" };
	}

	public short[] getColumnWidths() {
		// ñðÔ·
		return new short[] { 0, 9, 7, 40, 20, 20, 9, 9, 20, 17, 20, 20 };
	}

	private Map dataKbnMap;

	private Map taSysKbnMap;

	private Map datSaiBanFlgMap;

	private Map taniMap;

	private Map zeiKbnMap;

	private Map swkInKbnMap;

	/**
	 * 
	 */
	public SlipTypeMasterReportExcelDefine() {
		// ÃÞ°Àæª - 11:üà`[,12:oà`[,13:UÖ`[,14:©Ï`[,15:©ÏÁ`[,
		// 21:Ðõ¼¥,22:oï¸Z,23:¿,24:x¥`[(Ðõ),
		// 25:x¥`[(Õ),26:x¥`[(è),27:Oà²®,
		// 28:§ÖðÊï,2E:ðÛïïcï(Ðõ),2T:ðÛïïcï(ÐO),
		// 31:Â vã,32:Â Á,33:§Öà¿,41:óèo^,42:óèÏ,
		// 51:xèo^,52:xèÏ,61:ÅèYUÖ,62:ÅèYx¥
		dataKbnMap = new HashMap();
		dataKbnMap.put("11", "C00419");
		dataKbnMap.put("12", "C00264");
		dataKbnMap.put("13", "C00469");
		dataKbnMap.put("14", "C02079");
		dataKbnMap.put("15", "C02080");
		dataKbnMap.put("21", "C02081");
		dataKbnMap.put("22", "C02082");
		dataKbnMap.put("23", "C00314");
		dataKbnMap.put("24", "C02083");
		dataKbnMap.put("25", "C02084");
		dataKbnMap.put("26", "C02085");
		dataKbnMap.put("27", "C02086");
		dataKbnMap.put("28", "C02087");
		dataKbnMap.put("2E", "C02088");
		dataKbnMap.put("2T", "C02089");
		dataKbnMap.put("31", "C01978");
		dataKbnMap.put("32", "C01979");
		dataKbnMap.put("33", "C02090");
		dataKbnMap.put("41", "C02091");
		dataKbnMap.put("42", "C02092");
		dataKbnMap.put("51", "C02093");
		dataKbnMap.put("52", "C02094");
		dataKbnMap.put("61", "C02095");
		dataKbnMap.put("62", "C02096");
		// ¼¼½ÃÑæª - 0:¼¼½óüÎÛO 1:¼¼½óüÎÛ
		taSysKbnMap = new HashMap();
		taSysKbnMap.put(0, "C02097");
		taSysKbnMap.put(1, "C02098");
		// `[ÔÌÔÌ×¸Þ - 0:µÈ¢ PF·é
		datSaiBanFlgMap = new HashMap();
		datSaiBanFlgMap.put(0, "C02099");
		datSaiBanFlgMap.put(1, "C02100");
		// óüPÊ - 0:`[íÊPÊÉ´×°Áª¯¸ 1:`[ÔPÊÉ´×°Áª¯¸

		taniMap = new HashMap();
		taniMap.put(0, "C02101");
		taniMap.put(1, "C02102");
		// ÁïÅvZæª - 0:OÅ 1:àÅ
		zeiKbnMap = new HashMap();
		zeiKbnMap.put(0, "C00337");
		zeiKbnMap.put(1, "C00023");
		// dóC^[tF[Xæª - 0:o^ 1:³F
		swkInKbnMap = new HashMap();
		swkInKbnMap.put(0, "C01258");
		swkInKbnMap.put(1, "C01168");
	}

	public List convertDataToList(Object dto, String langCode) {
		DEN_SYU_MST entity = (DEN_SYU_MST) dto;
		List list = new ArrayList();

		// ïÐR[h
		list.add(entity.getKAI_CODE());
		// `[íÊº°ÄÞ
		list.add(entity.getDEN_SYU_CODE());
		// VXeæª
		list.add(new AlignString(entity.getSYS_KBN(), AlignString.CENTER));
		// `[íÊ¼Ì
		list.add(entity.getDEN_SYU_NAME());
		// `[íÊªÌ
		list.add(entity.getDEN_SYU_NAME_S());
		//  [^Cg
		list.add(entity.getDEN_SYU_NAME_K());
		// ÃÞ°Àæª
		list.add(new AlignString(getWord(entity.getDATA_KBN(), dataKbnMap, langCode), AlignString.CENTER));
		// ¼¼½ÃÑæª
		list.add(new AlignString(getWord(entity.getTA_SYS_KBN(), taSysKbnMap, langCode), AlignString.CENTER));
		// `[ÔÌÔÌ×¸Þ
		list.add(getWord(entity.getDAT_SAIBAN_FLG(), datSaiBanFlgMap, langCode));
		// óüPÊ
		list.add(getWord(entity.getTANI(), taniMap, langCode));
		// ÁïÅvZæª
		list.add(new AlignString(getWord(entity.getZEI_KBN(), zeiKbnMap, langCode), AlignString.CENTER));
		// dóC^[tF[Xæª
		list.add(new AlignString(getWord(entity.getSWK_IN_KBN(), swkInKbnMap, langCode), AlignString.CENTER));

		return list;
	}
}
