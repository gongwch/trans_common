package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ����}�X�^Dao
 */
public interface LNG_MSTDao {

	/** */
	public Class BEAN = LNG_MST.class;

	/**
	 * @return List
	 */
	public List getAllLNG_MST();

	/**  */
	public String getLngInfo_QUERY = "KAI_CODE = ? AND LNG_CODE BETWEEN ? AND ? ORDER BY LNG_CODE ";

	/**
	 * @param KAI_CODE
	 * @param LNG_CODEE_FROM
	 * @param LNG_CODE_TO
	 * @return List
	 */
	public List getLngInfo(String KAI_CODE, String LNG_CODEE_FROM, String LNG_CODE_TO);

	// �w�肳�ꂽ�f�[�^�̎擾
	/**  */
	public String getLNG_MSTByKaicodeLngcode_ARGS = "KAI_CODE, LNG_CODE";

	/**
	 * @param kaiCode
	 * @param lngCode
	 * @return LNG_MST
	 */
	public LNG_MST getLNG_MSTByKaicodeLngcode(String kaiCode, String lngCode);

	/**
	 * @param dto
	 */
	public void insert(LNG_MST dto);

	/**
	 * @param dto
	 */
	public void update(LNG_MST dto);

	/**
	 * @param dto
	 */
	public void delete(LNG_MST dto);

	// ���L�� ISFnet China �ǉ���

	// REF����
	/**  */
	public String conditionSearch_ARGS = "kaiCode,lngCode";

	/**
	 * @param kaiCode
	 * @param lngCode
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String lngCode);
}
