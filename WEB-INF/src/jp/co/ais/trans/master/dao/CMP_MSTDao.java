package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ��ЃR���g���[���}�X�^Dao
 */

public interface CMP_MSTDao {

	/**  */
	public Class BEAN = CMP_MST.class;

	/** �p�����[�^ */
	public String getAllCMP_MST_ARGS = "kaiCode,prgCode";

	/**
	 * �}�X�^�ꗗ�擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param prgCode �v���O�����R�[�h
	 * @return List
	 */
	public List getAllCMP_MST(String kaiCode, String prgCode);

	/**  */
	public String getCmpInfo_QUERY = "KAI_CODE BETWEEN ? AND ? ORDER BY KAI_CODE ";

	/**
	 * @param KAI_CODE_FROM
	 * @param KAI_CODE_TO
	 * @return List
	 */
	public List getCmpInfo(String KAI_CODE_FROM, String KAI_CODE_TO);

	/**  */
	public String getCMP_MST_ByKaicode_ARGS = "KAI_CODE";

	/**
	 * @param KAI_CODE
	 * @return CMP_MST
	 */
	public CMP_MST getCMP_MST_ByKaicode(String KAI_CODE);

	/**
	 * @param dto
	 */
	public void insert(CMP_MST dto);

	/**
	 * @param dto
	 */
	public void update(CMP_MST dto);

	/**
	 * @param dto
	 */
	public void delete(CMP_MST dto);
}
