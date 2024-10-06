package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 
 */
public class ReportExcelDefineBase implements ReportExcelDefine {

	private String kaiCode = null;

	private String langCode = null;

	public String getFileName() {
		return null;
	}

	public String getSheetName() {
		return null;
	}

	public String[] getHeaderTexts() {
		return null;
	}

	public short[] getColumnWidths() {
		return null;
	}

	public List convertDataToList(Object dto, String lang) {
		return null;
	}

	protected String getWord(String messageID, String lang) {
		if (Util.isNullOrEmpty(messageID)) {
			return "";
		} else {
			return MessageUtil.getWord(lang, messageID);
		}
	}

	protected String getWord(Object value, Map map, String lang) {
		if (Util.isNullOrEmpty(value)) {
			return "";
		} else {
			String messageID = (String) map.get(value);
			return getWord(messageID, lang);
		}
	}

	/**
	 * 会社コードの取得
	 * 
	 * @param kaiCode
	 */
	public void setKaiCode(String kaiCode) {
		this.kaiCode = kaiCode;
	}

	public String getKaiCode() {
		return this.kaiCode;
	}

	/**
	 * 言語コードの取得
	 */
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getLangCode() {
		return this.langCode;
	}
}
