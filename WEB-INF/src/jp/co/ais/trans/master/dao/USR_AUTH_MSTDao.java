package jp.co.ais.trans.master.dao;

import java.io.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ���[�U�F�؃}�X�^DAO
 * 
 * @author roh
 */
public interface USR_AUTH_MSTDao extends Serializable {

	/** ���[�U�F�؃}�X�^Bean */
	public Class BEAN = USR_AUTH_MST.class;

	/**
	 * �o�^
	 * 
	 * @param dto
	 */
	public void insert(USR_AUTH_MST dto);

	/**
	 * �X�V
	 * 
	 * @param dto
	 */
	public void update(USR_AUTH_MST dto);

	/**
	 * �폜
	 * 
	 * @param dto
	 */
	public void delete(USR_AUTH_MST dto);

	/** findByKaiCode SQL */
	public static final String findByKaiCode_ARGS = "KAI_CODE";

	/**
	 * ��Ђ̔F�؏����K��
	 * 
	 * @param KAI_CODE
	 * @return �F�؏��
	 */
	public USR_AUTH_MST findByKaiCode(String KAI_CODE);
}
