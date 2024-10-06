package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���s���O��Excel��`�N���X
 */
public class ExecutedLogReportExcelDefine extends ReportExcelDefineBase {

	/** ����R�[�h */
	private String langCode;

	/**
	 * �t�@�C�����擾
	 * 
	 * @return MG0028 ���s���O�v���O������
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#getFileName()
	 */
	public String getFileName() {
		return "MG0028";
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param langCode ����R�[�h
	 */
	public ExecutedLogReportExcelDefine(String langCode) {
		this.langCode = langCode;
	}

	/**
	 * �V�[�g���擾
	 * 
	 * @return �V�[�g��
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#getSheetName()
	 */
	public String getSheetName() {
		return MessageUtil.getWord(langCode, "C02911");
	}

	/**
	 * �^�C�g�����̔z��擾
	 * 
	 * @return String[] �^�C�g�����X�g
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#getHeaderTexts()
	 */
	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		String logdate = MessageUtil.getWord(langCode, "C00218") + MessageUtil.getWord(langCode, "C02909");
		String userCode = MessageUtil.getWord(langCode, "C00218") + MessageUtil.getWord(langCode, "C00589");
		String userName = MessageUtil.getWord(langCode, "C00218") + MessageUtil.getWord(langCode, "C00691");
		return new String[] { logdate, userCode, userName, "C02907", "C00818", "C00819", "C02908" };

	}

	/**
	 * �J�������擾
	 * 
	 * @return short[] �J�������z��
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#getColumnWidths()
	 */
	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 15, 11, 11, 10, 10, 12, 8 };
	}

	/**
	 * �I�u�W�F�N�g��ArrayList�ɕϊ�
	 * 
	 * @return ���O���X�g
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#convertDataToList(java.lang.Object, java.lang.String)
	 */
	public List convertDataToList(Object dto, String lang) {
		ExecutedLog entity = (ExecutedLog) dto;
		List list = new ArrayList();
		list.add(DateUtil.toYMDHMSString(entity.getEXCUTED_DATE()));
		list.add(entity.getUSR_CODE());
		list.add(entity.getUSR_NAME());
		list.add(entity.getIP_ADDRESS());
		list.add(entity.getPROGRAM_CODE());
		list.add(entity.getPROGRAM_NAME());
		list.add(entity.getSTATE());

		return list;

	}
}
