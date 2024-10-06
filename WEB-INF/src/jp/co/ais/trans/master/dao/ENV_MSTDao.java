package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface ENV_MSTDao {

	/**  */
	public Class BEAN = ENV_MST.class;

	/**
	 * @return List
	 */
	public List getAllENV_MST();

	/**  */
	public String getENV_MST_ARGS = "KAI_CODE";

	/**
	 * @param KAI_CODE
	 * @return EVK_MST
	 */
	public ENV_MST getENV_MST(String KAI_CODE);

	/**  */
	public String getENV_MSTByKaicode_ARGS = "KAI_CODE";

	/**
	 * @param KAI_CODE
	 * @return EVK_MST
	 */
	public ENV_MST getENV_MSTByKaicode(String KAI_CODE);

	/**
	 * @param dto
	 */
	public void insert(ENV_MST dto);

	/**
	 * @param dto
	 */
	public void update(ENV_MST dto);

	/**
	 * @param dto
	 */
	public void delete(ENV_MST dto);

	// ���L�� ISFnet China �ǉ���

	// ��������
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,KAI_NAME_S";

	/**
	 * @param kaiCode
	 * @param kaiName_S
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String kaiName_S);

	/** �p�����[�^�[ */
	public String getCmpNameS_ARGS = "KaiCode, DpkSsk, DepCode, panelLevel, kjlLvl, kjlDepCode";

	/** ��З��̂��擾 */
	/**
	 * @param KaiCode
	 * @param DpkSsk
	 * @param DepCode
	 * @param panelLevel
	 * @param kjlLvl
	 * @param kjlDepCode
	 * @return String
	 */
	public String getCmpNameS(String KaiCode, String DpkSsk, String DepCode, int panelLevel, int kjlLvl,
		String kjlDepCode);

	/** �g�D���� */
	public String conditionLvlSearch_ARGS = "loginKaiCode, kaiCode, kaiName_S, dpkSsk, cmpKbn, upCmpCode, dpkLvl, baseCmpCode, baseDpkLvl";

	/**
	 * �g�D����
	 * 
	 * @param loginKaiCode ���O�C����ЃR�[�h
	 * @param kaiCode ��ЃR�[�h
	 * @param kaiName_S ��Ж���
	 * @param dpkSsk �g�D�R�[�h
	 * @param cmpKbn �z����ЃR�[�h
	 * @param upCmpCode ��ʉ�ЃR�[�h
	 * @param dpkLvl �K�w���x��
	 * @param baseCmpCode ������ЃR�[�h
	 * @param baseDpkLvl �����K�w���x��
	 * @return list
	 */
	public List conditionLvlSearch(String loginKaiCode, String kaiCode, String kaiName_S, String dpkSsk, String cmpKbn,
		String upCmpCode, String dpkLvl, String baseCmpCode, String baseDpkLvl);

	// ISFnet �ǉ�
	/**  */
	public String getENV_MSTByKaicode1_QUERY = "ORDER BY KAI_CODE";

	/**
	 * @return List
	 */
	public List getENV_MSTByKaicode1();
	
	/** �p�����[�^�[ */
	public String getEnvMstData_ARGS = "kaiCode, slipDate";

	/** ��З��̂��擾 */
	/**
	 * @param kaiCode
	 * @param slipDate
	 * @return List
	 */
	public List getEnvMstData(String kaiCode, Date slipDate);
}
