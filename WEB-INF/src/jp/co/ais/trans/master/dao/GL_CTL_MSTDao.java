package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * GLコントロールマスタDao
 */
public interface GL_CTL_MSTDao {

	/**  */
	public Class BEAN = GL_CTL_MST.class;

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllGL_CTL_MST(String kaiCode);

	// public String getAllGL_CTL_MST2_QUERY = "ORDER BY KAI_CODE";
	// public List getAllGL_CTL_MST2();

	/**  */
	public String getGL_CTL_MST_QUERY = "KAI_CODE = ?";

	/**
	 * @param KAI_CODE
	 * @return GL_CTL_MST
	 */
	public GL_CTL_MST getGL_CTL_MST(String KAI_CODE);

	/**  */
	public String getGL_CTL_MSTByIKaicode_ARGS = "KAI_CODE";

	/**
	 * @param kAI_CODE
	 * @return GL_CTL_MST
	 */
	public GL_CTL_MST getGL_CTL_MSTByIKaicode(String kAI_CODE);

	/**
	 * @param dto
	 */
	public void insert(GL_CTL_MST dto);

	/**
	 * @param dto
	 */
	public void update(GL_CTL_MST dto);

	/**
	 * @param dto
	 */
	public void delete(GL_CTL_MST dto);
}
