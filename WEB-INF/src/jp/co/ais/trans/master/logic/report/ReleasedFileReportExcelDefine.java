package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���s���O��Excel��`�N���X
 */
public class ReleasedFileReportExcelDefine extends ReportExcelDefineBase {

	/** ����R�[�h */
	private String langCode;

	/**
	 * �t�@�C�����擾
	 * 
	 * @return MG0028 ���s���O�v���O������
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#getFileName()
	 */
	public String getFileName() {

		return "MG0029";
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param langCode ����R�[�h
	 */
	public ReleasedFileReportExcelDefine(String langCode) {
		this.langCode = langCode;
	}

	/**
	 * �V�[�g���擾
	 * 
	 * @return �V�[�g��
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#getSheetName()
	 */
	public String getSheetName() {

		return "C02914";
	}

	/**
	 * �^�C�g�����̔z��擾
	 * 
	 * @return String[] �^�C�g�����X�g
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#getHeaderTexts()
	 */
	public String[] getHeaderTexts() {
		String updateDate = MessageUtil.getWord(langCode, "C00169") + MessageUtil.getWord(langCode, "C02906");

		return new String[] { "C02912", "C01988", updateDate, "C02915" };

	}

	/**
	 * �J�������擾
	 * 
	 * @return short[] �J�������z��
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#getColumnWidths()
	 */
	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 50, 35, 15, 10 };
	}

	/**
	 * �I�u�W�F�N�g��ArrayList�ɕϊ�
	 * 
	 * @return �t�@�C�����X�g
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#convertDataToList(java.lang.Object, java.lang.String)
	 */
	public List convertDataToList(Object dto, String lang) {
		ReleasedFileObject entity = (ReleasedFileObject) dto;
		List list = new ArrayList();
		list.add(entity.getPATH_NAME());
		list.add(entity.getFILE_NAME());

		list.add(entity.getUPDATE_DATE());
		list.add(entity.getLENGTH());

		return list;

	}
}
