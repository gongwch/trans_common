package jp.co.ais.trans.master.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.master.entity.*;

/**
 * �ʉ݃}�X�^Dao
 */
public interface CUR_MSTDao {

	/**  */
	public static Class BEAN = CUR_MST.class;

	/**  */
	public static String getAllCUR_MST_QUERY = "KAI_CODE = ? ORDER BY CUR_CODE";

	/**
	 * @param KAI_CODE
	 * @return List
	 */
	public List getAllCUR_MST(String KAI_CODE);

	/**  */
	public static String getCurInfo_QUERY = "KAI_CODE = ? AND CUR_CODE BETWEEN ? AND ? ORDER BY CUR_CODE ";

	/**
	 * @param KAI_CODE
	 * @param CUR_CODE_FROM
	 * @param CUR_CODE_TO
	 * @return List
	 */
	public List getCurInfo(String KAI_CODE, String CUR_CODE_FROM, String CUR_CODE_TO);

	/**  */
	public static String getCurInfoFrom_QUERY = "KAI_CODE = ? AND CUR_CODE >= ? ORDER BY CUR_CODE ";

	/**
	 * @param KAI_CODE
	 * @param CUR_CODE_FROM
	 * @return List
	 */
	public List getCurInfoFrom(String KAI_CODE, String CUR_CODE_FROM);

	/**  */
	public static String getCurInfoTo_QUERY = "KAI_CODE = ? AND CUR_CODE <= ? ORDER BY CUR_CODE ";

	/**
	 * @param KAI_CODE
	 * @param CUR_CODE_TO
	 * @return List
	 */
	public List getCurInfoTo(String KAI_CODE, String CUR_CODE_TO);

	/**  */
	public static String getCUR_MST_ARGS = "KAI_CODE, CUR_CODE";

	/**
	 * @param KAI_CODE
	 * @param CUR_CODE
	 * @return CUR_MST
	 */
	public CUR_MST getCUR_MST(String KAI_CODE, String CUR_CODE);

	/**
	 * @param dto
	 */
	public void insert(CUR_MST dto);

	/**
	 * @param dto
	 */
	public void update(CUR_MST dto);

	/**
	 * @param dto
	 */
	public void delete(CUR_MST dto);

	/**  */
	public static String getAllCUR_MST2_QUERY = "KAI_CODE = ? ORDER BY CUR_CODE ";

	/**
	 * @param KAI_CODE
	 * @return List
	 */
	public List getAllCUR_MST2(String KAI_CODE);

	/**  */
	public static String conditionSearch_ARGS = "kaiCode,curCode,curName,curName_K,beginCode,endCode";

	/**
	 * @param kaiCode
	 * @param curCode
	 * @param curName
	 * @param curName_K
	 * @param beginCode
	 * @param endCode
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String curCode, String curName, String curName_K, String beginCode,
		String endCode);

	/** �p�����[�^ */
	public static String searchRefCurrency_ARGS = "kaiCode,curCode,sName,kName,termBasisDate,beginCode,endCode";

	/**
	 * �ʉ݃t�B�[���h�_�C�A���O����
	 * 
	 * @param kaiCode
	 * @param curCode
	 * @param sName
	 * @param kName
	 * @param termBasisDate
	 * @param beginCode
	 * @param endCode
	 * @return �ʉ݃��X�g
	 */
	public List<Object> searchRefCurrency(String kaiCode, String curCode, String sName, String kName,
		Timestamp termBasisDate, String beginCode, String endCode);

	/** ���� */
	public static String searchCurMstInfo_ARGS = "kaiCode,curCode,slipDate";

	/**
	 * @param kaiCode
	 * @param curCode
	 * @param slipDate
	 * @return CUR_MST
	 */
	public CUR_MST searchCurMstInfo(String kaiCode, String curCode, Date slipDate);

	/** getCUR_MSTByKaicode���� */
	public static String getCUR_MSTByKaicode_ARGS = "KAI_CODE";

	/**
	 * ��ЃR�[�h�ɕR�Â��ʉ݃f�[�^�̎擾
	 * 
	 * @param companyCode
	 * @return �ʉ݃f�[�^���X�g
	 */
	public List<CUR_MST> getCUR_MSTByKaicode(String companyCode);

}
