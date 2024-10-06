package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �J�����_�[�}�X�^Dao
 */
public interface AP_CAL_MSTDao {

	/** BEAN */
	public Class BEAN = AP_CAL_MST.class;

	/**
	 * @return List
	 */
	public List getAllAP_CAL_MST();

	/**  */
	public String getApCalInfo_QUERY = "KAI_CODE = ? AND CAL_DATE BETWEEN ? AND ? ORDER BY CAL_DATE ";

	/**
	 * @param KAI_CODE
	 * @param CAL_DATE_FROM
	 * @param CAL_DATE_TO
	 * @return List
	 */
	public List getApCalInfo(String KAI_CODE, Date CAL_DATE_FROM, Date CAL_DATE_TO);

	/**  */
	public String getApCalBankInfo_QUERY = "KAI_CODE = ? AND CAL_DATE > ? AND CAL_SHA = ? AND CAL_BNK_KBN = ? ORDER BY CAL_DATE ";

	/**
	 * @param KAI_CODE
	 * @param CAL_DATE
	 * @param CAL_SHA
	 * @param CAL_BNK_KBN
	 * @return List
	 */
	public List getApCalBankInfo(String KAI_CODE, Date CAL_DATE, int CAL_SHA, int CAL_BNK_KBN);

	/** �w�肳�ꂽ�f�[�^�̎擾 */
	public String getAP_CAL_MSTByKaicodeCalcode_ARGS = "KAI_CODE, CAL_DATE";

	/**
	 * @param kaiCode
	 * @param calDATE
	 * @return AP_CAL_MST
	 */
	public AP_CAL_MST getAP_CAL_MSTByKaicodeCalcode(String kaiCode, Date calDATE);

	/**
	 * @param dto
	 */
	public void insert(AP_CAL_MST dto);

	/**
	 * @param dto
	 */
	public void update(AP_CAL_MST dto);

	/**
	 * @param dto
	 */
	public void delete(AP_CAL_MST dto);

	// ���L�� ISFnet China �ǉ���

	/** ��Ԃ̃f�[�^���擾 */
	public String getCalendarInfo_QUERY = "KAI_CODE = ? AND CAL_DATE BETWEEN ? AND ? ORDER BY CAL_DATE ";

	/**
	 * @param kaiCode
	 * @param calDateFrom
	 * @param calDateTo
	 * @return List
	 */
	public List getCalendarInfo(String kaiCode, Date calDateFrom, Date calDateTo);

	/** �w�肳�ꂽ�f�[�^���擾 */
	public String getAP_CAL_MST_ARGS = "KAI_CODE, CAL_DATE";

	/**
	 * @param kaiCode
	 * @param calDate
	 * @return AP_CAL_MST
	 */
	public AP_CAL_MST getAP_CAL_MST(String kaiCode, Date calDate);

	/** getNearBusinessDayInfo���� */
	public String getNearBusinessDayInfo_ARGS = "kaiCode, calDate";

	/**
	 * �Ώۓ����疢���̍ł����߂̋�s�c�Ɠ����擾����B
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param calDate �Ώۓ�
	 * @return ��s�c�Ɠ�
	 */
	public AP_CAL_MST getNearBusinessDayInfo(String kaiCode, Date calDate);

	/** getNearPreBusinessDayInfo���� */
	public String getNearPreBusinessDayInfo_ARGS = "kaiCode, calDate";

	/**
	 * �Ώۓ�����ߋ��̍ł����߂̋�s�c�Ɠ����擾����B
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param calDate �Ώۓ�
	 * @return ��s�c�Ɠ�
	 */
	public AP_CAL_MST getNearPreBusinessDayInfo(String kaiCode, Date calDate);

	// 
	/** getNearTempPayDayInfo���� */
	public String getNearTempPayDayInfo_ARGS = "kaiCode, calDate";

	/**
	 * �Ώۓ����疢���̍ł����߂̗Վ��x���Ώۓ����擾����B
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param calDate �Ώۓ�
	 * @return �Վ��x���Ώۓ�
	 */
	public AP_CAL_MST getNearTempPayDayInfo(String kaiCode, Date calDate);

	/** getNearPreTempPayDayInfo���� */
	public String getNearPreTempPayDayInfo_ARGS = "kaiCode, calDate";

	/**
	 * �Ώۓ�����ߋ��̍ł����߂̗Վ��x���Ώۓ����擾����B
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param calDate �Ώۓ�
	 * @return �Վ��x���Ώۓ�
	 */
	public AP_CAL_MST getNearPreTempPayDayInfo(String kaiCode, Date calDate);

	// 
	/** getNearEmpPayDayInfo���� */
	public String getNearEmpPayDayInfo_ARGS = "kaiCode, calDate";

	/**
	 * �Ώۓ����疢���̍ł����߂̎Ј��x���Ώۓ����擾����B
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param calDate �Ώۓ�
	 * @return �Ј��x���Ώۓ�
	 */
	public AP_CAL_MST getNearEmpPayDayInfo(String kaiCode, Date calDate);

	/** getNearPreEmpPayDayInfo���� */
	public String getNearPreEmpPayDayInfo_ARGS = "kaiCode, calDate";

	/**
	 * �Ώۓ�����ߋ��̍ł����߂̎Ј��x���Ώۓ����擾����B
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param calDate �Ώۓ�
	 * @return �Ј��x���Ώۓ�
	 */
	public AP_CAL_MST getNearPreEmpPayDayInfo(String kaiCode, Date calDate);

	/** �Ј��x���Ώۓ� */
	public String getAPEmpPayDayCount_SQL = "SELECT COUNT(*) FROM AP_CAL_MST WHERE  KAI_CODE = ? AND CAL_DATE = ? AND CAL_HARAI = 1 ";

	/**
	 * �Ј��x���Ώۓ�(�Ј��x���Ώۓ��敪=1)���擾����B
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param calDate �Ώۓ�
	 * @return �Ј��x���Ώۓ����ݒ肳���ꍇ�F1�A�ȊO�F0
	 */
	public int getAPEmpPayDayCount(String kaiCode, Date calDate);

}
