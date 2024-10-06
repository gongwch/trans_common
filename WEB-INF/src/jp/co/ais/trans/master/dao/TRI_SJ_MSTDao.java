package jp.co.ais.trans.master.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface TRI_SJ_MSTDao {

	/**  */
	public Class BEAN = TRI_SJ_MST.class;

	/**
	 * @return List
	 */
	public List getAllTRI_SJ_MST();

	/**  */
	public String getTriSjInfo_QUERY = "KAI_CODE = ? AND TRI_CODE BETWEEN ? AND ? AND TJK_CODE BETWEEN ? AND ? ORDER BY TRI_CODE, TJK_CODE ";

	/**
	 * @param KAI_CODE
	 * @param TRI_CODE_FROM
	 * @param TRI_CODE_TO
	 * @param TJK_CODE_FROM
	 * @param TJK_CODE_TO
	 * @return List
	 */
	public List getTriSjInfo(String KAI_CODE, String TRI_CODE_FROM, String TRI_CODE_TO, String TJK_CODE_FROM,
		String TJK_CODE_TO);

	/**  */
	public String getTriSjDateRangeInfo_QUERY = "KAI_CODE = ? AND TRI_CODE = ? AND TJK_CODE = ? AND ? BETWEEN STR_DATE AND END_DATE ORDER BY TRI_CODE, TJK_CODE ";

	/**
	 * @param KAI_CODE
	 * @param TRI_CODE
	 * @param TJK_CODE
	 * @param DATE
	 * @return List
	 */
	public List getTriSjDateRangeInfo(String KAI_CODE, String TRI_CODE, String TJK_CODE, Date DATE);

	// �w�肳�ꂽ�f�[�^�̎擾
	/**  */
	public String getTRI_SJ_MSTByKaicodeTricodeTjkcode_ARGS = "KAI_CODE, TRI_CODE, TJK_CODE";

	/**
	 * @param kaiCode
	 * @param triCode
	 * @param tjkCode
	 * @return TRI_SJ_MST
	 */
	public TRI_SJ_MST getTRI_SJ_MSTByKaicodeTricodeTjkcode(String kaiCode, String triCode, String tjkCode);

	/**
	 * @param dto
	 */
	public void insert(TRI_SJ_MST dto);

	/**
	 * @param dto
	 */
	public void update(TRI_SJ_MST dto);

	/**
	 * @param dto
	 */
	public void delete(TRI_SJ_MST dto);

	// ���L�� ISFnet China �ǉ���

	// public String getAllTRI_SJ_MST2_QUERY= "KAI_CODE = ? ORDER BY TRI_CODE, TJK_CODE";
	/**  */
	public String getOneTRI_SJ_MST2_ARGS = "KAI_CODE, TRI_CODE, TRI_SJ_CODE";

	/**
	 * @param kaiCode
	 * @param triCode
	 * @param triSjCode
	 * @return List
	 */
	public TRI_SJ_MST getOneTRI_SJ_MST2(String kaiCode, String triCode, String triSjCode);

	/**  */
	public String getAllTRI_SJ_MST2_ARGS = "KAI_CODE, TRI_CODE_FROM, TRI_CODE_TO";

	/**
	 * @param kaiCode
	 * @param beginTriSjCode
	 * @param endTriSjCode
	 * @return List
	 */
	public List getAllTRI_SJ_MST2(String kaiCode, String beginTriSjCode, String endTriSjCode);

	/**  */
	public String getTriSjInfo2_QUERY = "KAI_CODE = ? AND TRI_CODE BETWEEN ? AND ? ORDER BY TRI_CODE, TJK_CODE ";

	/**
	 * @param KAI_CODE
	 * @param TRI_CODE_FROM
	 * @param TRI_CODE_TO
	 * @return List
	 */
	public List getTriSjInfo2(String KAI_CODE, String TRI_CODE_FROM, String TRI_CODE_TO);

	/**  */
	public String getTriSjInfoFrom_QUERY = "KAI_CODE = ? AND TRI_CODE >= ? ORDER BY TRI_CODE ";

	/**
	 * @param KAI_CODE
	 * @param TRI_CODE_FROM
	 * @return List
	 */
	public List getTriSjInfoFrom(String KAI_CODE, String TRI_CODE_FROM);

	/**  */
	public String getTriSjInfoTo_QUERY = "KAI_CODE = ? AND TRI_CODE <= ? ORDER BY TRI_CODE ";

	/**
	 * @param KAI_CODE
	 * @param TRI_CODE_TO
	 * @return List
	 */
	public List getTriSjInfoTo(String KAI_CODE, String TRI_CODE_TO);

	// �t�H�[�J�X�p���\�b�h
	/**  */
	public String searchBnkName_ARGS = "kaiCode,triCode,tjkCode";

	/**
	 * @param kaiCode
	 * @param triCode
	 * @param tjkCode
	 * @return List
	 */
	public List<Object> searchBnkName(String kaiCode, String triCode, String tjkCode);

	// �_�C�A���O�p���\�b�h
	/**  */
	public String refDialogSearch_ARGS = "kaiCode,triCode,tjkCode,termBasisDate";

	/**
	 * @param kaiCode
	 * @param triCode
	 * @param tjkCode
	 * @param termBasisDate
	 * @return List
	 */
	public List<Object> refDialogSearch(String kaiCode, String triCode, String tjkCode, Timestamp termBasisDate);

	// �f�t�H���g�̎x�������擾
	/**  */
	public String getDefaultPaymentCondition_ARGS = "kaiCode, triCode, curCode";

	/**
	 * @param kaiCode
	 * @param triCode
	 * @param curCode
	 * @return TRI_SJ_MST
	 */
	public TRI_SJ_MST getDefaultPaymentCondition(String kaiCode, String triCode, String curCode);

	// �x�����@����
	/**  */
	public String getSihhoh_ARGS = "strKaiCode,strTjkCode,minTriSjcode";

	/**
	 * @param strKaiCode
	 * @param strTjkCode
	 * @param minTriSjcode
	 * @return TRI_SJ_MST
	 */
	public TRI_SJ_MST getSihhoh(String strKaiCode, String strTjkCode, String minTriSjcode);
	
	/** �p�����[�^�[ */
	public String getTriSjMstData_ARGS = "kaiCode,triCode,tjkCode,slipDate";

	/**
	 * �����x����������
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param tjkCode
	 * @param slipDate 
	 * @return List
	 */
	public List getTriSjMstData(String kaiCode, String triCode, String tjkCode, Date slipDate);
}
