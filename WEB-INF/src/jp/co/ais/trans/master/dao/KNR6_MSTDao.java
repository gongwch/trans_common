package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �Ǘ��U�}�X�^Dao
 */
public interface KNR6_MSTDao {

	/**  */
	public Class BEAN = KNR6_MST.class;

	/**
	 * @return List
	 */
	public List getAllKNR6_MST();

	/**  */
	public String getKnr6Info_QUERY = "KAI_CODE = ? AND KNR_CODE_6 BETWEEN ? AND ? ORDER BY KNR_CODE_6 ";

	/**
	 * @param KAI_CODE
	 * @param KNR_CODE_6_FROM
	 * @param KNR_CODE_6_TO
	 * @return List
	 */
	public List getKnr6Info(String KAI_CODE, String KNR_CODE_6_FROM, String KNR_CODE_6_TO);

	// �w�肳�ꂽ�f�[�^�̎擾
	/**  */
	public String getKNR6_MSTByKaicodeKnrcode6_ARGS = "KAI_CODE,KNR_CODE_6";

	/**
	 * @param kaiCode
	 * @param knrCode6
	 * @return KNR6_MST
	 */
	public KNR6_MST getKNR6_MSTByKaicodeKnrcode6(String kaiCode, String knrCode6);

	/**
	 * @param dto
	 */
	public void insert(KNR6_MST dto);

	/**
	 * @param dto
	 */
	public void update(KNR6_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KNR6_MST dto);

	// ���L�� ISFnet China �ǉ���

	// ����Ђ̑S�����R�[�h
	/**  */
	public String getAllKNR6_MST2_QUERY = "KAI_CODE = ? ORDER BY KNR_CODE_6";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllKNR6_MST2(String kaiCode);

	// ��Ԃ̃f�[�^���擾
	/**  */
	public String getKnr6InfoFrom_QUERY = "KAI_CODE = ? AND KNR_CODE_6 >= ? ORDER BY KNR_CODE_6 ";

	/**
	 * @param kaiCode
	 * @param knrCode6From
	 * @return List
	 */
	public List getKnr6InfoFrom(String kaiCode, String knrCode6From);

	// ��Ԃ̃f�[�^���擾
	/**  */
	public String getKnr6InfoTo_QUERY = "KAI_CODE = ? AND KNR_CODE_6 <= ? ORDER BY KNR_CODE_6 ";

	/**
	 * @param kaiCode
	 * @param knrCode6To
	 * @return List
	 */
	public List getKnr6InfoTo(String kaiCode, String knrCode6To);

	// �q�d�e����
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,KNR_CODE_6,KNR_NAME_S_6,KNR_NAME_K_6,beginCode,endCode";

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
	public String searchKnrNameS6_ARGS = "slipDate, kaiCode, knrCode,beginCode,endCode";

	/**
	 * �Ǘ�6�}�X�^�f�[�^�̊Ǘ�6���̂�����<BR>
	 * �Ǘ�6�}�X�^�f�[�^�̊Ǘ�6���̂�����
	 * 
	 * @param slipDate �`�[���t
	 * @param kaiCode ��ЃR�[�h
	 * @param knrCode �Ǘ�6�R�[�h
	 * @param beginCode
	 * @param endCode
	 * @return �Ǘ�61�}�X�^�w�b�_�[�f�[�^
	 */
	public KNR6_MST searchKnrNameS6(Date slipDate, String kaiCode, String knrCode, String beginCode, String endCode);


	/** �p�����[�^�[ */
	public String getData_ARGS = "param";
	
	/**
	 * �Ǘ��}�X�^�f�[�^�擾
	 * 
	 * @param param
	 * @return �Ǘ��}�X�^���X�g
	 */
	public List<KNR6_MST> getData(KnrMstParam param);

	/** �p�����[�^�[ */
	public String getKnr6MstData_ARGS = "param";
	
	/**
	 * �Ǘ��}�X�^�f�[�^�擾
	 * 
	 * @param param
	 * @return �Ǘ��}�X�^���X�g
	 */
	public List<KNR6_MST> getKnr6MstData(KnrMstParam param);

}
