package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �Ǘ��P�}�X�^Dao
 */
public interface KNR1_MSTDao {

	/**  */
	public Class BEAN = KNR1_MST.class;

	/**
	 * @return List
	 */
	public List getAllKNR1_MST();

	/**  */
	public String getKnr1Info_QUERY = "KAI_CODE = ? AND KNR_CODE_1 BETWEEN ? AND ? ORDER BY KNR_CODE_1 ";

	/**
	 * @param KAI_CODE
	 * @param KNR_CODE_1_FROM
	 * @param KNR_CODE_1_TO
	 * @return List
	 */
	public List getKnr1Info(String KAI_CODE, String KNR_CODE_1_FROM, String KNR_CODE_1_TO);

	// �w�肳�ꂽ�f�[�^�̎擾
	/**  */
	public String getKNR1_MSTByKaicodeKnrcode1_ARGS = "KAI_CODE,KNR_CODE_1";

	/**
	 * @param kaiCode
	 * @param knrCode1
	 * @return KNR1_MST
	 */
	public KNR1_MST getKNR1_MSTByKaicodeKnrcode1(String kaiCode, String knrCode1);

	/**
	 * @param dto
	 */
	public void insert(KNR1_MST dto);

	/**
	 * @param dto
	 */
	public void update(KNR1_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KNR1_MST dto);

	// ���L�� ISFnet China �ǉ���

	// ����Ђ̑S�����R�[�h
	/**  */
	public String getAllKNR1_MST2_QUERY = "KAI_CODE = ? ORDER BY KNR_CODE_1 ";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllKNR1_MST2(String kaiCode);

	// ��Ԃ̃f�[�^���擾
	/**  */
	public String getKnr1InfoFrom_QUERY = "KAI_CODE = ? AND KNR_CODE_1 >= ? ORDER BY KNR_CODE_1 ";

	/**
	 * @param kaiCode
	 * @param knrCode1From
	 * @return List
	 */
	public List getKnr1InfoFrom(String kaiCode, String knrCode1From);

	// ��Ԃ̃f�[�^���擾
	/**  */
	public String getKnr1InfoTo_QUERY = "KAI_CODE = ? AND KNR_CODE_1 <= ? ORDER BY KNR_CODE_1 ";

	/**
	 * @param kaiCode
	 * @param knrCode1To
	 * @return List
	 */
	public List getKnr1InfoTo(String kaiCode, String knrCode1To);

	// �q�d�e����
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,KNR_CODE_1,KNR_NAME_S_1,KNR_NAME_K_1,beginCode,endCode";

	/**
	 * @param kaiCode
	 * @param knrCode
	 * @param knrName_S
	 * @param knrName_K
	 * @param beginCode
	 * @param endCode
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String knrCode, String knrName_S, String knrName_K, String beginCode,
		String endCode);

	/** ���� */
	public String searchKnrMstData_ARGS = "kaiCode,knrCode,knrName,knrName_K,slipDate,startCode,endCode";

	/**
	 * �Ǘ��}�X�^����
	 * 
	 * @param kaiCode ��ЃR�[
	 * @param knrCode �Ǘ��R�[�h
	 * @param knrName �Ǘ�����
	 * @param knrName_K �Ǘ�������
	 * @param slipDate �`�[���t
	 * @param startCode �J�n�R�[�h
	 * @param endCode �I���R�[�h
	 * @return �Ǘ��}�X�^���X�g
	 */
	public List searchKnrMstData(String kaiCode, String knrCode, String knrName, String knrName_K, Date slipDate,
		String startCode, String endCode);

	/** �p�����[�^�[ */
	public String searchKnrNameS1_ARGS = "slipDate, kaiCode, knrCode,beginCode,endCode";

	/**
	 * �Ǘ�1�}�X�^�f�[�^�̊Ǘ�1���̂�����<BR>
	 * �Ǘ�1�}�X�^�f�[�^�̊Ǘ�1���̂�����
	 * 
	 * @param slipDate �`�[���t
	 * @param kaiCode ��ЃR�[�h
	 * @param knrCode �Ǘ�1�R�[�h
	 * @param beginCode
	 * @param endCode
	 * @return �Ǘ�1�}�X�^�w�b�_�[�f�[�^
	 */
	public KNR1_MST searchKnrNameS1(Date slipDate, String kaiCode, String knrCode, String beginCode, String endCode);


	/** �p�����[�^�[ */
	public String getData_ARGS = "param";
	
	/**
	 * �Ǘ��}�X�^�f�[�^�擾
	 * 
	 * @param param
	 * @return �Ǘ��}�X�^���X�g
	 */
	public List<KNR1_MST> getData(KnrMstParam param);
	
	/** �p�����[�^�[ */
	public String getKnr1MstData_ARGS = "param";
	
	/**
	 * �Ǘ��}�X�^�f�[�^�擾
	 * 
	 * @param param
	 * @return �Ǘ��}�X�^���X�g
	 */
	public List<KNR1_MST> getKnr1MstData(KnrMstParam param);

	
}
