package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �V�X�e���敪�}�X�^Dao
 */
public interface SYS_MSTDao {

	/** Bean */
	public Class BEAN = SYS_MST.class;

	/**
	 * �S�̌���
	 * 
	 * @return �V�X�e���敪
	 */
	public List getAllSYS_MST();

	/** �p�����[�^ */
	public String getSysInfo_QUERY = "KAI_CODE = ? AND SYS_KBN BETWEEN ? AND ? ORDER BY SYS_KBN ";

	/**
	 * �͈͌���
	 * 
	 * @param KAI_CODE
	 * @param SYS_KBNE_FROM
	 * @param SYS_KBN_TO
	 * @return �V�X�e���敪
	 */
	public List getSysInfo(String KAI_CODE, String SYS_KBNE_FROM, String SYS_KBN_TO);

	/** �p�����[�^ */
	public String getSYS_MSTByKaicodeSyskbn_ARGS = "KAI_CODE, SYS_KBN";

	/**
	 * �w�肳�ꂽ�V�X�e���敪�̎擾
	 * 
	 * @param kaiCode
	 * @param sysKBN
	 * @return �V�X�e���敪
	 */
	public SYS_MST getSYS_MSTByKaicodeSyskbn(String kaiCode, String sysKBN);

	/**
	 * @param dto
	 */
	public void insert(SYS_MST dto);

	/**
	 * @param dto
	 */
	public void update(SYS_MST dto);

	/**
	 * @param dto
	 */
	public void delete(SYS_MST dto);

	// ���L�� ISFnet China �ǉ���
	/** �p�����[�^ */
	public String getAllSYS_MST2_QUERY = "KAI_CODE = ? ORDER BY SYS_KBN";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllSYS_MST2(String kaiCode);

	/** �p�����[�^ */
	public String getSysInfoFrom_QUERY = "KAI_CODE = ? AND SYS_KBN >= ? ORDER BY SYS_KBN ";

	/**
	 * @param kaiCode
	 * @param sysKbnFrom
	 * @return List
	 */
	public List getSysInfoFrom(String kaiCode, String sysKbnFrom);

	/** �p�����[�^ */
	public String getSysInfoTo_QUERY = "KAI_CODE = ? AND SYS_KBN <= ? ORDER BY SYS_KBN ";

	/**
	 * @param kaiCode
	 * @param sysKbnTo
	 * @return List
	 */
	public List getSysInfoTo(String kaiCode, String sysKbnTo);

	/** �p�����[�^ */
	public String conditionSearch_ARGS = "KAI_CODE,SYS_KBN,SYS_NAME_S,SYS_NAME_K,beginCode,endCode";

	/**
	 * REF����
	 * 
	 * @param kaiCode
	 * @param sysKbn
	 * @param sysName_S
	 * @param sysName_K
	 * @param beginCode
	 * @param endCode
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String sysKbn, String sysName_S, String sysName_K, String beginCode,
		String endCode);
}
