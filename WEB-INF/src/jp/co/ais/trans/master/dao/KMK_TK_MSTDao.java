package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �Ȗڑ̌n���̃}�X�^Dao
 */
public interface KMK_TK_MSTDao {

	/**  */
	public Class BEAN = KMK_TK_MST.class;

	/**  */
	public String getKMK_TK_MST_ARGS = "KAI_CODE, KMT_CODE";

	/**
	 * @param kaiCode
	 * @param kmtCode
	 * @return KMK_TK_MST
	 */
	public KMK_TK_MST getKMK_TK_MST(String kaiCode, String kmtCode);

	// ��������

	/**  */
	public String conditionSearch_ARGS = "kaiCode,kmtCode,kmtName_S,katName_K";

	/**
	 * @param kaiCode
	 * @param kmtCode
	 * @param kmtName_S
	 * @param katName_K
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String kmtCode, String kmtName_S, String katName_K);

	/**
	 * @param dto
	 */
	public void insert(KMK_TK_MST dto);

	/**
	 * @param dto
	 */
	public void update(KMK_TK_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KMK_TK_MST dto);

	/** �p�����[�^�[ */
	public String searchKmtMstData_ARGS = "kaiCode,kmtCode,kmtName,kmtName_K,startCode,endCode";

	/**
	 * �Ȗڑ̌n�}�X�^����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param kmtCode �Ȗڑ̌n�R�[�h
	 * @param kmtName �Ȗڑ̌n����
	 * @param kmtName_K �Ȗڑ̌n������
	 * @param startCode �J�n�R�[�h
	 * @param endCode �I���R�[�h
	 * @return �Ȗڑ̌n���X�g
	 */
	public List searchKmtMstData(String kaiCode, String kmtCode, String kmtName, String kmtName_K, String startCode,
		String endCode);

	/** �p�����[�^�[ */
	public String getKmkTkMst_ARGS = "KAI_CODE, KMT_CODE";

	/**
	 * �Ȗڑ̌n���̃f�[�^���� (1�s)
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param kmtCode �Ȗڑ̌n���̃R�[�h
	 * @return KMK_TK_MST �Ȗڑ̌n���̃f�[�^
	 */
	public KMK_TK_MST getKmkTkMst(String kaiCode, String kmtCode);
}
