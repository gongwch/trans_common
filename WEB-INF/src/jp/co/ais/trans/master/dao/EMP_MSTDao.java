package jp.co.ais.trans.master.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface EMP_MSTDao {

	/**  */
	public Class BEAN = EMP_MST.class;

	/**
	 * @return List
	 */
	public List getAllEMP_MST();

	/**  */
	public String getEmpInfo_QUERY = "KAI_CODE = ? AND EMP_CODE BETWEEN ? AND ? ORDER BY EMP_CODE ";

	/**
	 * @param KAI_CODE
	 * @param EMP_CODE_FROM
	 * @param EMP_CODE_TO
	 * @return List
	 */
	public List getEmpInfo(String KAI_CODE, String EMP_CODE_FROM, String EMP_CODE_TO);

	// �w�肳�ꂽ�f�[�^�̎擾
	/**  */
	public String getEMP_MSTByKaicodeEmpcode_ARGS = "KAI_CODE,EMP_CODE";

	/**
	 * @param kaiCode
	 * @param empCode
	 * @return EMP_MST
	 */
	public EMP_MST getEMP_MSTByKaicodeEmpcode(String kaiCode, String empCode);

	/**
	 * @param dto
	 */
	public void insert(EMP_MST dto);

	/**
	 * @param dto
	 */
	public void update(EMP_MST dto);

	/**
	 * @param dto
	 */
	public void delete(EMP_MST dto);

	// ���L�� ISFnet China �ǉ���

	/**  */
	public String getAllEMP_MST2_QUERY = "KAI_CODE = ? ORDER BY EMP_CODE ";

	/**
	 * @param KAI_CODE
	 * @return List
	 */
	public List getAllEMP_MST2(String KAI_CODE);

	/**  */
	public String getEmpInfoFrom_QUERY = "KAI_CODE = ? AND EMP_CODE >= ? ORDER BY EMP_CODE ";

	/**
	 * @param KAI_CODE
	 * @param EMP_CODE_FROM
	 * @return List
	 */
	public List getEmpInfoFrom(String KAI_CODE, String EMP_CODE_FROM);

	/**  */
	public String getEmpInfoTo_QUERY = "KAI_CODE = ? AND EMP_CODE <= ? ORDER BY EMP_CODE ";

	/**
	 * @param KAI_CODE
	 * @param EMP_CODE_TO
	 * @return List
	 */
	public List getEmpInfoTo(String KAI_CODE, String EMP_CODE_TO);

	// ��������
	/**  */
	public String conditionSearch_ARGS = "kaiCode,empCode,empName_S,empName_K,beginCode,endCode";

	/**
	 * @param kaiCode
	 * @param empCode
	 * @param empName_S
	 * @param empName_K
	 * @param beginCode
	 * @param endCode
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String empCode, String empName_S, String empName_K, String beginCode,
		String endCode);

	/** �p�����[�^�[ */
	public String searchEmpNamesByUser_ARGS = "kaiCode, empCode, depCode";

	/**
	 * �Ј��}�X�^�f�[�^�̎Ј����̂�����<BR>
	 * �Ј��}�X�^�f�[�^�̎Ј����̂�����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param empCode �Ј��R�[�h
	 * @param depCode ��������R�[�h
	 * @return �Ј��}�X�^�w�b�_�[�f�[�^
	 */
	public EMP_MST searchEmpNamesByUser(String kaiCode, String empCode, String depCode);

	/** ���� */
	public String searchEmpMstData_ARGS = "kaiCode,empCode,empName,empName_K,slipDate,startCode,endCode";

	/**
	 * �Ј��}�X�^����
	 * 
	 * @param kaiCode ��ЃR�[
	 * @param empCode �Ј��R�[�h
	 * @param empName �Ј�����
	 * @param empName_K �Ј�������
	 * @param slipDate �`�[���t
	 * @param startCode �J�n�R�[�h
	 * @param endCode �I���R�[�h
	 * @return �Ј��}�X�^���X�g
	 */
	public List searchEmpMstData(String kaiCode, String empCode, String empName, String empName_K, Date slipDate,
		String startCode, String endCode);

	/** ���� */
	public String searchEmpMstDataByUser_ARGS = "kaiCode,empCode,empName,empName_K,depCode";

	/**
	 * �Ј��}�X�^����
	 * 
	 * @param kaiCode ��ЃR�[
	 * @param empCode �Ј��R�[�h
	 * @param empName �Ј�����
	 * @param empName_K �Ј�������
	 * @param depCode ��������R�[�h
	 * @return �Ј��}�X�^���X�g
	 */
	public List searchEmpMstDataByUser(String kaiCode, String empCode, String empName, String empName_K, String depCode);

	/** ���� */
	public String refDialogSearch_ARGS = "kaiCode,empCode,sName,kName,termBasisDate,user,depCode,beginCode,endCode";

	/**
	 * �_�C�A���O�Ј���񌟍�
	 * 
	 * @param kaiCode
	 * @param empCode
	 * @param sName
	 * @param kName
	 * @param termBasisDate
	 * @param user
	 * @param depCode
	 * @param beginCode
	 * @param endCode
	 * @return �Ј��}�X�^���X�g
	 */
	public List<Object> refDialogSearch(String kaiCode, String empCode, String sName, String kName,
		Timestamp termBasisDate, String user, String depCode, String beginCode, String endCode);
	
	/** ���� */
	public String getEmpMstData_ARGS = "kaiCode,empCode,slipDate";

	/**
	 * �Ј��}�X�^����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param empCode �Ј��R�[�h
	 * @param slipDate �`�[���t
	 * @return �Ј��}�X�^���X�g
	 */
	public List getEmpMstData(String kaiCode, String empCode, Date slipDate);

}
