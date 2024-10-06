package jp.co.ais.trans2.model.save;

import java.util.*;

/**
 * �I�u�W�F�N�g�ۑ�Dao
 */
public interface OBJ_SAVEDao {

	/** BEAN */
	public Class BEAN = OBJ_SAVE.class;

	/** ���� */
	public String findByKey_ARGS = "kAI_CODE, uSR_ID, pRG_ID, sEQ";

	/**
	 * ����
	 * 
	 * @param kAI_CODE ��ЃR�[�h
	 * @param uSR_ID ���[�UID
	 * @param pRG_ID �v���O����ID
	 * @param sEQ �V�[�P���XNo.
	 * @return ����
	 */
	public List<OBJ_SAVE> findByKey(String kAI_CODE, String uSR_ID, String pRG_ID, Integer sEQ);

	/**
	 * �V�K�쐬
	 * 
	 * @param entity
	 */
	public void insert(OBJ_SAVE entity);

	/**
	 * �폜
	 * 
	 * @param entity
	 */
	public void delete(OBJ_SAVE entity);

	/** ���� */
	public String deleteBySeq_ARGS = "kAI_CODE, uSR_ID, pRG_ID, sEQ";

	/**
	 * �폜
	 * 
	 * @param kAI_CODE ��ЃR�[�h
	 * @param uSR_ID ���[�UID
	 * @param pRG_ID �v���O����ID
	 * @param sEQ �V�[�P���XNo.
	 */
	public void deleteBySeq(String kAI_CODE, String uSR_ID, String pRG_ID, Integer sEQ);

	/** ���� */
	public String updateSeq_ARGS = "kAI_CODE, uSR_ID, pRG_ID";

	/**
	 * SEQ�X�V
	 * 
	 * @param kAI_CODE ��ЃR�[�h
	 * @param uSR_ID ���[�UID
	 * @param pRG_ID �v���O����ID
	 */
	public void updateSeq(String kAI_CODE, String uSR_ID, String pRG_ID);

}
