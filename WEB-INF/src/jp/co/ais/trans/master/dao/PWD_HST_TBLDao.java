package jp.co.ais.trans.master.dao;

import java.io.*;
import java.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �����Ǘ��}�X�^DAO
 * 
 * @author roh
 */
public interface PWD_HST_TBLDao extends Serializable {

	/** �����Ǘ��}�X�^Bean */
	public Class BEAN = PWD_HST_TBL.class;

	/**
	 * �o�^
	 * 
	 * @param dto
	 */
	public void insert(PWD_HST_TBL dto);

	/**
	 * �X�V
	 * 
	 * @param dto
	 */
	public void update(PWD_HST_TBL dto);

	/**
	 * �폜
	 * 
	 * @param dto
	 */
	public void delete(PWD_HST_TBL dto);

	/** findPassword SQL */
	public String findPassword_QUERY = "KAI_CODE = ? AND USR_CODE = ? ORDER BY INP_DATE";

	/**
	 * ���������K��
	 * 
	 * @param kaiCode
	 * @param usrCode
	 * @return �����肷��
	 */
	public List<PWD_HST_TBL> findPassword(String kaiCode, String usrCode);

	/** ���O�C���`�F�b�N�폜SQL */
	public String deleteByPK_SQL = "DELETE FROM PWD_HST_TBL WHERE KAI_CODE = ? AND USR_CODE = ?";

	/**
	 * ���O�C���`�F�b�N�폜
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param usrCode ���[�U�R�[�h
	 */
	public void deleteByPK(String kaiCode, String usrCode);

}
