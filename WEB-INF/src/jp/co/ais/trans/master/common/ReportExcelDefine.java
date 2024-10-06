package jp.co.ais.trans.master.common;

import java.util.*;

/**
 * 
 */
public interface ReportExcelDefine {

	/**
	 * Excel出力のファイル名
	 * 
	 * @return String
	 */
	public String getFileName();

	/**
	 * Excel出力のシート名
	 * 
	 * @return String
	 */
	public String getSheetName();

	/**
	 * 列のヘッダーの文字
	 * 
	 * @return String[]
	 */
	public String[] getHeaderTexts();

	/**
	 * 列の幅
	 * 
	 * @return short[]
	 */
	public short[] getColumnWidths();

	/**
	 * データの調整
	 * 
	 * @param dto
	 * @param langCode
	 * @return List
	 */
	public List convertDataToList(Object dto, String langCode);

	/**
	 * 会社コードの取得
	 * 
	 * @param kaiCode
	 */
	public void setKaiCode(String kaiCode);

	/**
	 * @return String
	 */
	public String getKaiCode();

	/**
	 * 言語コードの取得
	 * 
	 * @param kaiCode
	 */
	public void setLangCode(String kaiCode);

	/**
	 * @return String
	 */
	public String getLangCode();
}
