package jp.co.ais.trans.master.common;

import java.util.*;

/**
 * 
 */
public interface ReportExcelDefine {

	/**
	 * Excel�o�͂̃t�@�C����
	 * 
	 * @return String
	 */
	public String getFileName();

	/**
	 * Excel�o�͂̃V�[�g��
	 * 
	 * @return String
	 */
	public String getSheetName();

	/**
	 * ��̃w�b�_�[�̕���
	 * 
	 * @return String[]
	 */
	public String[] getHeaderTexts();

	/**
	 * ��̕�
	 * 
	 * @return short[]
	 */
	public short[] getColumnWidths();

	/**
	 * �f�[�^�̒���
	 * 
	 * @param dto
	 * @param langCode
	 * @return List
	 */
	public List convertDataToList(Object dto, String langCode);

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @param kaiCode
	 */
	public void setKaiCode(String kaiCode);

	/**
	 * @return String
	 */
	public String getKaiCode();

	/**
	 * ����R�[�h�̎擾
	 * 
	 * @param kaiCode
	 */
	public void setLangCode(String kaiCode);

	/**
	 * @return String
	 */
	public String getLangCode();
}
