package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * “E—vƒ}ƒXƒ^‚ÌExcel’è‹`ƒNƒ‰ƒX
 */
public class MemoMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0120";
	}

	public String getSheetName() {
		// “E—vƒ}ƒXƒ^‚ğ•Ô‚·
		return "C02349";
	}

	public String[] getHeaderTexts() {
		// ƒ^ƒCƒgƒ‹‚ğ•Ô‚·
		return new String[] { "C00596", "C00568", "C02047", "C00564", "C00565", "C00566", "C00055", "C00261" };
	}

	private Map dataKbnMap;

	private Map tekKbnMap;

	/**
	 * 
	 */
	public MemoMasterReportExcelDefine() {
		// Š·Z‹æ•ª - 11:“ü‹à“`•[,12:o‹à“`•[,13:U‘Ö“`•[,14:Œ©Ï“`•[,15:Œ©ÏÁ‹“`•[,
		// 21:Ğˆõ‰¼•¥,22:Œo”ï¸Z,23:¿‹‘,24:x•¥“`•[(Ğˆõ),
		// 25:x•¥“`•[(—Õ),26:x•¥“`•[(’è),27:ŠO‘‘—‹à’²®,
		// 28:—§‘ÖŒğ’Ê”ï,2E:ŒğÛ”ï‰ï‹c”ï(Ğˆõ),2T:ŒğÛ”ï‰ï‹c”ï(ĞŠO),
		// 31:ÂŒ Œvã,32:ÂŒ Á,33:—§‘Ö‹à¿‹‘,41:óè“o˜^,42:óèŒˆÏ,
		// 51:xè“o˜^,52:xèŒˆÏ,61:ŒÅ’è‘YU‘Ö,62:ŒÅ’è‘Yx•¥
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

		tekKbnMap = new HashMap();
		tekKbnMap.put(0, "C00569");
		tekKbnMap.put(1, "C00119");
	}

	public short[] getColumnWidths() {
		// —ñ•‚ğ•Ô‚·
		return new short[] { 0, 5, 10, 10, 80, 40, 6, 6, };
	}

	public List convertDataToList(Object dto, String langCode) {
		TEK_MST entity = (TEK_MST) dto;
		List list = new ArrayList();

		// ‰ïĞƒR[ƒh
		list.add(entity.getKAI_CODE());
		// “E—v‹æ•ª
		list.add(new AlignString(getWord(entity.getTEK_KBN(), tekKbnMap, langCode), AlignString.CENTER));

		list.add(new AlignString(getWord(entity.getDATA_KBN(), dataKbnMap, langCode), AlignString.CENTER));

		// list.add(getWord(entity.getDATA_KBN(),dataKbnMap,langCode));
		// “E—vƒR[ƒh
		list.add(entity.getTEK_CODE());
		// “E—v–¼Ì
		list.add(entity.getTEK_NAME());
		// “E—vŒŸõ–¼Ì
		list.add(entity.getTEK_NAME_K());
		// ŠJn”NŒ“ú
		list.add(entity.getSTR_DATE());
		// I—¹”NŒ“ú
		list.add(entity.getEND_DATE());

		return list;
	}
}
