package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ��ЊK�w�}�X�^DAO
 */
public interface EVK_MSTDao {

	/**  */
	public Class BEAN = EVK_MST.class;

	/**
	 * @return List
	 */
	public List getAllEVK_MST();

	/**  */
	public String getEvkInfo_QUERY = "KAI_CODE = ? AND DPK_SSK BETWEEN ? AND ? AND DPK_DEP_CODE BETWEEN ? AND ? ORDER BY DPK_SSK, DPK_DEP_CODE ";

	/**
	 * @param KAI_CODE
	 * @param DPK_SSK_FROM
	 * @param DPK_SSK_TO
	 * @param DPK_DEP_CODE_FROM
	 * @param DPK_DEP_CODE_TO
	 * @return List
	 */
	public List getEvkInfo(String KAI_CODE, String DPK_SSK_FROM, String DPK_SSK_TO, String DPK_DEP_CODE_FROM,
		String DPK_DEP_CODE_TO);

	// �w�肳�ꂽ�f�[�^�̎擾
	/**  */
	public String getEVK_MSTByKaicodeDpksskDpkdepcode_ARGS = "KAI_CODE, DPK_SSK, DPK_DEP_CODE";

	/**
	 * @param kaiCode
	 * @param dpkSSK
	 * @param dpkdepCode
	 * @return EVK_MST
	 */
	public EVK_MST getEVK_MSTByKaicodeDpksskDpkdepcode(String kaiCode, String dpkSSK, String dpkdepCode);

	/** �p�����[�^�[ */
	public String getDpkSsk_SQL = "SELECT DISTINCT(T.DPK_SSK) FROM EVK_MST T WHERE T.KAI_CODE = ? ORDER BY T.DPK_SSK";

	/**
	 * ��ВP�ʂ̑g�D�R�[�h���X�g���擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @return List list
	 */
	public List getDpkSsk(String kaiCode);

	// �w�肳�ꂽ�f�[�^�̎擾
	/**  */
	public String getEVK_MSTByKaicode_ARGS = "KAI_CODE";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getEVK_MSTByKaicode(String kaiCode);

	/**  */
	public String getEVK_MST_ARGS = "KAI_CODE,DPK_SSK,DPK_DEP_CODE";

	/**
	 * @param KAI_CODE
	 * @param DPK_SSK
	 * @param DPK_DEP_CODE
	 * @return EVK_MST
	 */
	public EVK_MST getEVK_MST(String KAI_CODE, String DPK_SSK, String DPK_DEP_CODE);

	/**  */
	public String getEVK_MSTSsk_ARGS = "KAI_CODE,DPK_SSK";

	/**
	 * @param KAI_CODE
	 * @param DPK_SSK
	 * @return EVK_MST
	 */
	public EVK_MST getEVK_MSTSsk(String KAI_CODE, String DPK_SSK);

	/**
	 * @param dto
	 */
	public void insert(EVK_MST dto);

	/**
	 * @param dto
	 */
	public void update(EVK_MST dto);

	/**
	 * @param dto
	 */
	public void delete(EVK_MST dto);
}
