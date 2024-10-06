package jp.co.ais.trans.common.config;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���[�p�ݒ���
 */
public class ReportConfig {

	/** ���|�[�g�p�v���p�e�B */
	private static ResourceBundle bundle = ResourceBundle.getBundle("report");

	/**
	 * �_�C���N�g����p�t�H���g���̎擾
	 * 
	 * @param lang ����R�[�h
	 * @return fontName
	 */
	public static String getDirectPrintFontName(String lang) {
		return getProperty("report.direct.font." + lang);
	}

	/**
	 * PDF���[�t�H���g���̎擾
	 * 
	 * @param lang ����R�[�h
	 * @return fontName
	 */
	public static String getPDFFontName(String lang) {
		return getProperty("report.pdf.font." + lang);
	}

	/**
	 * PDF���[�t�H���g�̃t�@�C�������擾
	 * 
	 * @param lang ����R�[�h
	 * @return fontName
	 */
	public static String getPDFFontFileName(String lang) {
		return getProperty("report.pdf.font.file." + lang);
	}

	/**
	 * PDF���[Addon�t�H���gID�̎擾
	 * 
	 * @param index Addon�t�H���g�ԍ�
	 * @return fontName
	 */
	public static String getPDFAddonFontId(int index) {
		return getPropertyNE("report.pdf.addon." + index + ".font.id");
	}

	/**
	 * PDF���[Addon�t�H���g���̂̎擾
	 * 
	 * @param index Addon�t�H���g�ԍ�
	 * @return fontName
	 */
	public static String getPDFAddonFontName(int index) {
		return getPropertyNE("report.pdf.addon." + index + ".font.name");
	}

	/**
	 * PDF���[Addon�t�H���g�̃t�@�C���ݒ���擾
	 * 
	 * @param index Addon�t�H���g�ԍ�
	 * @return fontName
	 */
	public static String getPDFAddonFontFileName(int index) {
		return getPropertyNE("report.pdf.addon." + index + ".font.file");
	}

	/**
	 * Excel�t�H���g���̎擾
	 * 
	 * @param lang ����R�[�h
	 * @return fontName
	 */
	public static String getExcelFontName(String lang) {

		return getProperty("report.excel.font." + lang);
	}

	/**
	 * �v���p�e�B����l���擾
	 * 
	 * @param key �L�[
	 * @return �ݒ�l
	 */
	private static String getPropertyNE(String key) {
		try {
			return bundle.getString(key);
		} catch (Throwable ex) {
			return "";
		}
	}

	/**
	 * �v���p�e�B����l���擾
	 * 
	 * @param key �L�[
	 * @return �ݒ�l
	 */
	private static String getProperty(String key) {

		String value = bundle.getString(key);

		if (Util.isNullOrEmpty(value)) {
			// �ݒ薳���̓V�X�e���G���[
			throw new TEnvironmentException("S00001", key + " not found.");
		}

		return value;
	}

}
