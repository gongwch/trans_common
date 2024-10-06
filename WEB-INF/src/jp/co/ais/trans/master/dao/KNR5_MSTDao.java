package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �Ǘ��T�}�X�^Dao
 */
public interface KNR5_MSTDao {

	/**  */
	public Class BEAN = KNR5_MST.class;

	/**
	 * @return List
	 */
	public List getAllKNR5_MST();

	/**  */
	public String getKnr5Info_QUERY = "KAI_CODE = ? AND KNR_CODE_5 BETWEEN ? AND ? ORDER BY KNR_CODE_5 ";

	/**
	 * @param KAI_CODE
	 * @param KNR_CODE_5_FROM
	 * @param KNR_CODE_5_TO
	 * @return List
	 */
	public List getKnr5Info(String KAI_CODE, String KNR_CODE_5_FROM, String KNR_CODE_5_TO);

	// �w�肳�ꂽ�f�[�^�̎擾
	/**  */
	public String getKNR5_MSTByKaicodeKnrcode5_ARGS = "KAI_CODE,KNR_CODE_5";

	/**
	 * @param kaiCode
	 * @param knrCode5
	 * @return KNR5_MST
	 */
	public KNR5_MST getKNR5_MSTByKaicodeKnrcode5(String kaiCode, String knrCode5);

	/**
	 * @param dto
	 */
	public void insert(KNR5_MST dto);

	/**
	 * @param dto
	 */
	public void update(KNR5_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KNR5_MST dto);

	// ���L�� ISFnet China �ǉ���

	// ����Ђ̑S�����R�[�h
	/**  */
	public String getAllKNR5_MST2_QUERY = "KAI_CODE = ? ORDER BY KNR_CODE_5";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllKNR5_MST2(String kaiCode);

	// ��Ԃ̃f�[�^���擾
	/**  */
	public String getKnr5InfoFrom_QUERY = "KAI_CODE = ? AND KNR_CODE_5 >= ? ORDER BY KNR_CODE_5 ";

	/**
	 * @param kaiCode
	 * @param knrCode5From
	 * @return List
	 */
	public List getKnr5InfoFrom(String kaiCode, String knrCode5From);

	// ��Ԃ̃f�[�^���擾
	/**  */
	public String getKnr5InfoTo_QUERY = "KAI_CODE = ? AND KNR_CODE_5 <= ? ORDER BY KNR_CODE_5 ";

	/**
	 * @param kaiCode
	 * @param knrCode5To
	 * @return List
	 */
	public List getKnr5InfoTo(String kaiCode, String knrCode5To);

	// �q�d�e����
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,KNR_CODE_5,KNR_NAME_S_5,KNR_NAME_K_5,beginCode,endCode";

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
	public String searchKnrNameS5_ARGS = "slipDate, kaiCode, knrCode,beginCode,endCode";

	/**
	 * �Ǘ�5�}�X�^�f�[�^�̊Ǘ�5���̂�����<BR>
	 * �Ǘ�5�}�X�^�f�[�^�̊Ǘ�5���̂�����
	 * 
	 * @param slipDate �`�[���t
	 * @param kaiCode ��ЃR�[�h
	 * @param knrCode �Ǘ�5�R�[�h
	 * @param beginCode
	 * @param endCode
	 * @return �Ǘ�5�}�X�^�w�b�_�[�f�[�^
	 */
	public KNR5_MST searchKnrNameS5(Date slipDate, String kaiCode, String knrCode, String beginCode, String endCode);



	/** �p�����[�^�[ */
	public String getData_ARGS = "param";
	
	/**
	 * �Ǘ��}�X�^�f�[�^�擾
	 * 
	 * @param param
	 * @return �Ǘ��}�X�^���X�g
	 */
	public List<KNR5_MST> getData(KnrMstParam param);
	
	/** �p�����[�^�[ */
	public String getKnr5MstData_ARGS = "param";
	
	/**
	 * �Ǘ��}�X�^�f�[�^�擾
	 * 
	 * @param param
	 * @return �Ǘ��}�X�^���X�g
	 */
	public List<KNR5_MST> getKnr5MstData(KnrMstParam param);

}
