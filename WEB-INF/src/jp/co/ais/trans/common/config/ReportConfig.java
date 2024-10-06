package jp.co.ais.trans.common.config;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * 帳票用設定情報
 */
public class ReportConfig {

	/** レポート用プロパティ */
	private static ResourceBundle bundle = ResourceBundle.getBundle("report");

	/**
	 * ダイレクト印刷用フォント名の取得
	 * 
	 * @param lang 言語コード
	 * @return fontName
	 */
	public static String getDirectPrintFontName(String lang) {
		return getProperty("report.direct.font." + lang);
	}

	/**
	 * PDF帳票フォント名の取得
	 * 
	 * @param lang 言語コード
	 * @return fontName
	 */
	public static String getPDFFontName(String lang) {
		return getProperty("report.pdf.font." + lang);
	}

	/**
	 * PDF帳票フォントのファイル名を取得
	 * 
	 * @param lang 言語コード
	 * @return fontName
	 */
	public static String getPDFFontFileName(String lang) {
		return getProperty("report.pdf.font.file." + lang);
	}

	/**
	 * PDF帳票AddonフォントIDの取得
	 * 
	 * @param index Addonフォント番号
	 * @return fontName
	 */
	public static String getPDFAddonFontId(int index) {
		return getPropertyNE("report.pdf.addon." + index + ".font.id");
	}

	/**
	 * PDF帳票Addonフォント名称の取得
	 * 
	 * @param index Addonフォント番号
	 * @return fontName
	 */
	public static String getPDFAddonFontName(int index) {
		return getPropertyNE("report.pdf.addon." + index + ".font.name");
	}

	/**
	 * PDF帳票Addonフォントのファイル設定を取得
	 * 
	 * @param index Addonフォント番号
	 * @return fontName
	 */
	public static String getPDFAddonFontFileName(int index) {
		return getPropertyNE("report.pdf.addon." + index + ".font.file");
	}

	/**
	 * Excelフォント名の取得
	 * 
	 * @param lang 言語コード
	 * @return fontName
	 */
	public static String getExcelFontName(String lang) {

		return getProperty("report.excel.font." + lang);
	}

	/**
	 * プロパティから値を取得
	 * 
	 * @param key キー
	 * @return 設定値
	 */
	private static String getPropertyNE(String key) {
		try {
			return bundle.getString(key);
		} catch (Throwable ex) {
			return "";
		}
	}

	/**
	 * プロパティから値を取得
	 * 
	 * @param key キー
	 * @return 設定値
	 */
	private static String getProperty(String key) {

		String value = bundle.getString(key);

		if (Util.isNullOrEmpty(value)) {
			// 設定無しはシステムエラー
			throw new TEnvironmentException("S00001", key + " not found.");
		}

		return value;
	}

}
