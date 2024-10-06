package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �����ړI�}�X�^Dao
 */
public interface AP_MKT_MSTDao {

	/** */
	public Class BEAN = AP_MKT_MST.class;

	/** �\�[�g�� */
	public String getAllAP_MKT_MST_QUERY = "ORDER BY MKT_CODE";

	/**
	 * @return �����ړI�S�f�[�^
	 */
	public List getAllAP_MKT_MST();

	/** �������� */
	public String getAP_MKT_MST_ARGS = "MKT_CODE";

	/**
	 * @param mktCode
	 * @return AP_MKT_MST
	 */
	public AP_MKT_MST getAP_MKT_MST(String mktCode);

	/** �������� */
	public String conditionSearch_ARGS = "mktCode,mktName";

	/**
	 * @param mktCode
	 * @param mktName
	 * @return List
	 */
	public List conditionSearch(String mktCode, String mktName);

	/**
	 * @param dto
	 */
	public void insert(AP_MKT_MST dto);

	/**
	 * @param dto
	 */
	public void update(AP_MKT_MST dto);

	/**
	 * @param dto
	 */
	public void delete(AP_MKT_MST dto);
}
