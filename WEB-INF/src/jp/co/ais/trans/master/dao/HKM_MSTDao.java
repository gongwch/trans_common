package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface HKM_MSTDao {

	/**  */
	public Class BEAN = HKM_MST.class;

	/**
	 * @return List
	 */
	public List getAllHKM_MST();

	/**  */
	public String getHkmInfo_QUERY = "KAI_CODE = ? AND KMK_CODE = ? AND HKM_CODE BETWEEN ? AND ? ORDER BY HKM_CODE ";

	/**
	 * @param KAI_CODE
	 * @param KMK_CODE
	 * @param HKM_CODE_FROM
	 * @param HKM_CODE_TO
	 * @return List
	 */
	public List getHkmInfo(String KAI_CODE, String KMK_CODE, String HKM_CODE_FROM, String HKM_CODE_TO);

	// �w�肳�ꂽ�f�[�^�̎擾
	/**  */
	public String getHKM_MSTByKaicodeKmkcodeHkmcode_ARGS = "KAI_CODE, KMK_CODE, HKM_CODE";

	/**
	 * @param kaiCode
	 * @param kmkCode
	 * @param hkmCode
	 * @return HKM_MST
	 */
	public HKM_MST getHKM_MSTByKaicodeKmkcodeHkmcode(String kaiCode, String kmkCode, String hkmCode);

	// �w�肳�ꂽ�f�[�^�̎擾
	/**  */
	public String getHKM_MSTByKaicodeKmkcode_ARGS = "KAI_CODE, HKM_CODE";

	/**
	 * @param kaiCode
	 * @param hkmCode
	 * @return HKM_MST
	 */
	public HKM_MST getHKM_MSTByKaicodeKmkcode(String kaiCode, String hkmCode);

	/**
	 * @param dto
	 */
	public void insert(HKM_MST dto);

	/**
	 * @param dto
	 */
	public void update(HKM_MST dto);

	/**
	 * @param dto
	 */
	public void delete(HKM_MST dto);

	// ���L�� ISFnet China �ǉ���

	/**  */
	public String getAllHKM_MST1_QUERY = "KAI_CODE = ? ORDER BY KMK_CODE,HKM_CODE";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllHKM_MST1(String kaiCode);

	/**  */
	public String getAllHKM_MST2_QUERY = "KAI_CODE = ? AND KMK_CODE = ? ORDER BY HKM_CODE";

	/**
	 * @param kaiCode
	 * @param kmkCode
	 * @return List
	 */
	public List getAllHKM_MST2(String kaiCode, String kmkCode);

	/**  */
	public String getHkmInfoFrom_QUERY = "KAI_CODE = ? AND KMK_CODE = ? AND HKM_CODE >= ? ORDER BY HKM_CODE ";

	/**
	 * @param kaiCode
	 * @param kmkCode
	 * @param hkmCodeFrom
	 * @return List
	 */
	public List getHkmInfoFrom(String kaiCode, String kmkCode, String hkmCodeFrom);

	/**  */
	public String getHkmInfoTo_QUERY = "KAI_CODE = ? AND KMK_CODE = ? AND HKM_CODE <= ? ORDER BY HKM_CODE ";

	/**
	 * @param kaiCode
	 * @param kmkCode
	 * @param hkmCodeTo
	 * @return List
	 */
	public List getHkmInfoTo(String kaiCode, String kmkCode, String hkmCodeTo);

	// ��������
	/**  */
	public String conditionSearch_ARGS = "kaiCode,kmkCode,hkmCode,hkmName_S,hkmName_K,beginCode,endCode,kind";

	/**
	 * @param kaiCode
	 * @param kmkCode
	 * @param hkmCode
	 * @param hkmName_S
	 * @param hkmName_K
	 * @param beginCode
	 * @param endCode
	 * @param kind
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String kmkCode, String hkmCode, String hkmName_S, String hkmName_K,
		String beginCode, String endCode, String kind);

	/** findListByParameter()���� */
	public static final String findListByParameter_ARGS = "param";

	/** findByParameter()���� */
	public static final String findByParameter_ARGS = "param";

	/**
	 * �⏕�ȖڃR�[�h����
	 * 
	 * @param param Input�p�����[�^
	 * @return �⏕�Ȗڃ��X�g
	 */
	public List findListByParameter(AccountItemInputParameter param);

	/**
	 * �⏕�Ȗڂ��擾
	 * 
	 * @param param Input�p�����[�^
	 * @return �⏕�Ȗڃ}�X�^�w�b�_�[�f�[�^
	 */
	public HKM_MST findByParameter(AccountItemInputParameter param);

}
