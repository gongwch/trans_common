package jp.co.ais.trans2.define;

import java.util.*;

/**
 * “`•[í—Ş
 */
public enum SlipKind {

	/** “ü‹à“`•[ */
	INPUT_CASH_FLOW,

	/** o‹à“`•[ */
	OUTPUT_CASH_FLOW,

	/** U‘Ö“`•[ */
	TRANSFER,

	/** ”„ã“`•[ */
	SALES,

	/** d“ü“`•[ */
	PURCHASE,

	/** IFRS’²®“`•[ */
	IFRS_TRANSFER,

	/** Ğˆõx•¥“`•[ */
	EMPLOYEE;

	/** í—ŞƒŠƒXƒg */
	public static Map<String, SlipKind> kindList = Collections.synchronizedMap(new TreeMap<String, SlipKind>());

	static {
		kindList.put("11", INPUT_CASH_FLOW); // 11:“ü‹à“`•[
		kindList.put("12", OUTPUT_CASH_FLOW); // 12:o‹à“`•[
		kindList.put("13", TRANSFER); // 13:U‘Ö“`•[
		kindList.put("14", TRANSFER); // 14:Œ©Ï“`•[(U–ß)
		kindList.put("15", TRANSFER); // 15:Œ©ÏÁ‹“`•[(U–ßæÁ)
		kindList.put("16", IFRS_TRANSFER); // 16:IFRS’²®“`•[
		kindList.put("17", IFRS_TRANSFER); // 17:©‘’²®“`•[

		kindList.put("21", EMPLOYEE); // 21:Ğˆõ‰¼•¥
		kindList.put("22", EMPLOYEE); // 22:Œo”ï¸Z
		kindList.put("23", PURCHASE); // 23:¿‹‘(Â–±)
		kindList.put("24", TRANSFER); // 24:x•¥“`•[(Ğˆõ)
		kindList.put("25", TRANSFER); // 25:x•¥“`•[(—Õ)
		kindList.put("26", TRANSFER); // 26:x•¥“`•[(’è)
		kindList.put("27", TRANSFER); // 27:ŠO‘‘—‹à’²®
		kindList.put("28", TRANSFER); // 28:x•¥“`•[(‹¤’Ê)
		kindList.put("2E", TRANSFER); // 2E:ŒğÛ”ï‰ï‹c”ï(Ğˆõ)
		kindList.put("2T", TRANSFER); // 2T:ŒğÛ”ï‰ï‹c”ï(ĞŠO)

		kindList.put("31", SALES); // 31:ÂŒ Œvã
		kindList.put("32", TRANSFER); // 32:ÂŒ Á
		kindList.put("33", TRANSFER); // 33:—§‘Ö‹à¿‹‘

		kindList.put("41", TRANSFER); // 41:óè“o˜^
		kindList.put("42", TRANSFER); // 42:óèŒˆÏ

		kindList.put("51", TRANSFER); // 51:xè“o˜^
		kindList.put("52", TRANSFER); // 52:xèŒˆÏ

		kindList.put("61", TRANSFER); // 61:ŒÅ’è‘YU‘Ö
		kindList.put("62", TRANSFER); // 62:ŒÅ’è‘Yx•¥
	}

	/**
	 * w’è“`•[‚Ì“`•[í—Ş‚ğ“Á’è‚·‚é
	 * 
	 * @param dataKind ƒf[ƒ^‹æ•ª
	 * @return “`•[í—Ş
	 */
	public static SlipKind get(String dataKind) {
		return kindList.get(dataKind);
	}
}
