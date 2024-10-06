package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ���b�N�A�E�g�Ǘ�DAO�N���X
 * 
 * @author roh
 */
public interface LOCK_OUT_TBLDao {

	/**  */
	public Class BEAN = LOCK_OUT_TBL.class;

	/**  */
	public String findWithPK_ARGS = "KAI_CODE,USR_CODE";

	/**
	 * ���[�U�R�[�h���w�肵���b�N�A�E�g�����K��
	 * 
	 * @param kaiCode
	 * @param usrCode
	 * @return LOCK_OUT_TBL
	 */
	public LOCK_OUT_TBL findWithPK(String kaiCode, String usrCode);

	/**  */
	public String findWithUserName_ARGS = "kaiCode,arrCount";

	/**
	 * ���b�N�A�E�g���ꂽ�f�[�^���K��
	 * 
	 * @param kaiCode
	 * @param arrCount
	 * @return List<LOCK_OUT_TBL>
	 */
	public List<LOCK_OUT_TBL> findWithUserName(String kaiCode, int arrCount);

	/**
	 * @param dto
	 */
	public void insert(LOCK_OUT_TBL dto);

	/**
	 * @param dto
	 */
	public void update(LOCK_OUT_TBL dto);

	/**
	 * @param dto
	 */
	public void delete(LOCK_OUT_TBL dto);

	/** ���O�C���`�F�b�N�폜SQL */
	public String deleteByPK_SQL = "DELETE FROM LOCK_OUT_TBL WHERE KAI_CODE = ? AND USR_CODE = ?";

	/**
	 * ���O�C���`�F�b�N�폜
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param usrCode ���[�U�R�[�h
	 */
	public void deleteByPK(String kaiCode, String usrCode);
}
