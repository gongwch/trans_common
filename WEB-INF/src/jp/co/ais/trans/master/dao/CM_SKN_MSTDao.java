package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �����R�[�h�}�X�^Dao
 */
public interface CM_SKN_MSTDao {

	/** */
	public Class BEAN = CM_SKN_MST.class;

	/**
	 * @return List
	 */
	public List getAllCM_SKN_MST();

	/**
	 * @param dto
	 */
	public void insert(CM_SKN_MST dto);

	// �e�[�u��CM_SKN_MST��Primary Key ���Ȃ��ł��̂ŁA
	// update��delete��SQL�t�@�C���̍쐬�K�v

	/**
	 * @param dto
	 */
	public void update(CM_SKN_MST dto);

	/**
	 * @param dto
	 */
	public void delete(CM_SKN_MST dto);

	// ���L�� ISFnet China �ǉ���

	/**  */
	public String getCM_SKN_MST_ARGS = "SKN_KAI_CODE, SKN_CODE";

	/**
	 * @param kaiCode
	 * @param skncode
	 * @return CM_SKN_MST
	 */
	public CM_SKN_MST getCM_SKN_MST(String kaiCode, String skncode);

	// ��������

	/**  */
	public String conditionSearch_ARGS = "sknKaiCode,sknCode,sknName_S,sknName_K,kind";

	/**
	 * @param sknKaiCode
	 * @param sknCode
	 * @param sknName_S
	 * @param sknName_K
	 * @param kind
	 * @return List
	 */
	public List conditionSearch(String sknKaiCode, String sknCode, String sknName_S, String sknName_K, String kind);

}
