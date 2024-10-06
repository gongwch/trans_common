package jp.co.ais.trans.master.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.master.entity.*;

/**
 * �����}�X�^Dao
 */
public interface TRI_MSTDao {

	/** beans */
	public Class BEAN = TRI_MST.class;

	/**
	 * �S�̌���
	 * 
	 * @return List
	 */
	public List getAllTRI_MST();

	/** �p�����[�^ */
	public String getTriInfo_QUERY = "KAI_CODE = ? AND TRI_CODE BETWEEN ? AND ? ORDER BY TRI_CODE ";

	/**
	 * @param KAI_CODE
	 * @param TRI_CODE_FROM
	 * @param TRI_CODE_TO
	 * @return List
	 */
	public List getTriInfo(String KAI_CODE, String TRI_CODE_FROM, String TRI_CODE_TO);

	/** �p�����[�^ */
	public String getTriDateRangeInfo_QUERY = "KAI_CODE = ? AND TRI_CODE = ? AND ? BETWEEN STR_DATE AND END_DATE ORDER BY TRI_CODE ";

	/**
	 * @param KAI_CODE
	 * @param TRI_CODE
	 * @param DATE
	 * @return List
	 */
	public List getTriDateRangeInfo(String KAI_CODE, String TRI_CODE, Date DATE);

	/** �p�����[�^ */
	public String getTRI_MSTByKaicodeTricode_ARGS = "KAI_CODE, TRI_CODE";

	/**
	 * �w�肳�ꂽ�f�[�^�̎擾
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @return TRI_MST
	 */
	public TRI_MST getTRI_MSTByKaicodeTricode(String kaiCode, String triCode);

	/**
	 * �o�^
	 * 
	 * @param dto
	 */
	public void insert(TRI_MST dto);

	/**
	 * �X�V
	 * 
	 * @param dto
	 */
	public void update(TRI_MST dto);

	/**
	 * �폜
	 * 
	 * @param dto
	 */
	public void delete(TRI_MST dto);

	/** �p�����[�^ */
	public String getAllTRI_MST2_QUERY = "KAI_CODE = ? ORDER BY TRI_CODE";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllTRI_MST2(String kaiCode);

	/**
	 * Query����
	 */
	public String getTRI_MSTByKaiCode_ARGS = "KAI_CODE";

	/**
	 * ��ЃR�[�h�Ŏ���惊�X�g���擾�i����Ȃ��j
	 * 
	 * @param KAI_CODE ��ЃR�[�h
	 * @return ����惊�X�g
	 */
	public List<TRI_MST> getTRI_MSTByKaiCode(String KAI_CODE);

	/** �p�����[�^ */
	public String getTriInfoFrom_QUERY = "KAI_CODE = ? AND TRI_CODE >= ? ORDER BY TRI_CODE ";

	/**
	 * @param kaiCode
	 * @param triCodeFrom
	 * @return List
	 */
	public List getTriInfoFrom(String kaiCode, String triCodeFrom);

	/** �p�����[�^ */
	public String getTriInfoTo_QUERY = "KAI_CODE = ? AND TRI_CODE <= ? ORDER BY TRI_CODE ";

	/**
	 * @param kaiCode
	 * @param triCodeTo
	 * @return List
	 */
	public List getTriInfoTo(String kaiCode, String triCodeTo);

	/** REF���� */
	public String conditionSearch_ARGS = "kaiCode,triCode,triName_S,triName_K,beginCode,endCode, kind";

	/**
	 * REF�_�C�A���O����
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param triName_S
	 * @param triName_K
	 * @param beginCode
	 * @param endCode
	 * @param kind
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String triCode, String triName_S, String triName_K, String beginCode,
		String endCode, String kind);

	/** �W�v�����������Ƃ������� */
	public String getTriInfoBySumCodeKaiCode_QUERY = "KAI_CODE = ? AND SUM_CODE = ?";

	/**
	 * @param kaiCode
	 * @param sumCode
	 * @return List<Object>
	 */
	public List<Object> getTriInfoBySumCodeKaiCode(String kaiCode, String sumCode);

	/** REF���� */
	public String conditionTokuiSearch_ARGS = "kaiCode,tekCode";

	/**
	 * ���Ӑ�̏ꍇ
	 * 
	 * @param kaiCode
	 * @param tekCode
	 * @return TRI_MST
	 */
	public TRI_MST conditionTokuiSearch(String kaiCode, String tekCode);

	/** �p�����[�^ */
	public String conditionSiireSearch_ARGS = "kaiCode,tekCode";

	/**
	 * �d����̏ꍇ
	 * 
	 * @param kaiCode
	 * @param tekCode
	 * @return TRI_MST
	 */
	public TRI_MST conditionSiireSearch(String kaiCode, String tekCode);

	/** �p�����[�^ */
	public String conditionToOrSiiSearch_ARGS = "kaiCode,tekCode";

	/**
	 * ���Ӑ恕�d����̏ꍇ
	 * 
	 * @param kaiCode
	 * @param tekCode
	 * @return TRI_MST
	 */
	public TRI_MST conditionToOrSiiSearch(String kaiCode, String tekCode);

	/** ����挟���̈��� */
	public String searchRefCustomer_ARGS = "kaiCode, triCode, sName, kName, termBasisDate, siire, tokui, beginCode, endCode";

	/**
	 * ����挟���i�_�C�A���O�p�j
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param sName
	 * @param kName
	 * @param termBasisDate
	 * @param siire
	 * @param tokui
	 * @param beginCode
	 * @param endCode
	 * @return ����惊�X�g
	 */
	public List searchRefCustomer(String kaiCode, String triCode, String sName, String kName, Timestamp termBasisDate,
		boolean siire, boolean tokui, String beginCode, String endCode);

	/** getTRI_MSTByBeginEnd���� */
	public String getTRI_MSTByBeginEnd_ARGS = "kaiCode, triCode, beginCode, endCode";

	/**
	 * �J�n�E�I���X�e�[�^�X����ɂ����f�[�^����<br>
	 * �J�n�E�I���R�[�h���w�肳��Ă���ꍇ�A���͈͓̔��ɊY�����Ȃ��ꍇ��null���Ԃ�.
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param triCode �Ώێ����R�[�h
	 * @param beginCode �J�n�����R�[�h
	 * @param endCode �I�������R�[�h
	 * @return �����f�[�^
	 */
	public TRI_MST getTRI_MSTByBeginEnd(String kaiCode, String triCode, String beginCode, String endCode);

	/** �˗����̍X�V�p�����[�^ */
	public final String updateIraiName_ARGS = "dto";

	/**
	 * �˗����̂��X�V
	 * 
	 * @param dto
	 */
	public void updateIraiName(TRI_MST dto);
	
	/** ����挟���̈��� */
	public String getTriMstData_ARGS = "kaiCode, triCode, slipDate";

	/**
	 * ����挟��
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param slipDate 
	 * @return ����惊�X�g
	 */
	public List getTriMstData(String kaiCode, String triCode, Date slipDate);
}
