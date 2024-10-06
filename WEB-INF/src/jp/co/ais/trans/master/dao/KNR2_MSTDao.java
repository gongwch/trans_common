package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �Ǘ��Q�}�X�^Dao
 */
public interface KNR2_MSTDao {

	/**  */
	public Class BEAN = KNR2_MST.class;

	/**
	 * @return List
	 */
	public List getAllKNR2_MST();

	/**  */
	public String getKnr2Info_QUERY = "KAI_CODE = ? AND KNR_CODE_2 BETWEEN ? AND ? ORDER BY KNR_CODE_2 ";

	/**
	 * @param KAI_CODE
	 * @param KNR_CODE_2_FROM
	 * @param KNR_CODE_2_TO
	 * @return List
	 */
	public List getKnr2Info(String KAI_CODE, String KNR_CODE_2_FROM, String KNR_CODE_2_TO);

	// �w�肳�ꂽ�f�[�^�̎擾
	/**  */
	public String getKNR2_MSTByKaicodeKnrcode2_ARGS = "KAI_CODE,KNR_CODE_2";

	/**
	 * @param kaiCode
	 * @param knrCode2
	 * @return KNR2_MST
	 */
	public KNR2_MST getKNR2_MSTByKaicodeKnrcode2(String kaiCode, String knrCode2);

	/**
	 * @param dto
	 */
	public void insert(KNR2_MST dto);

	/**
	 * @param dto
	 */
	public void update(KNR2_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KNR2_MST dto);

	// ���L�� ISFnet China �ǉ���

	// ����Ђ̑S�����R�[�h
	/**  */
	public String getAllKNR2_MST2_QUERY = "KAI_CODE = ? ORDER BY KNR_CODE_2";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllKNR2_MST2(String kaiCode);

	// ��Ԃ̃f�[�^���擾
	/**  */
	public String getKnr2InfoFrom_QUERY = "KAI_CODE = ? AND KNR_CODE_2 >= ? ORDER BY KNR_CODE_2 ";

	/**
	 * @param kaiCode
	 * @param knrCode2From
	 * @return List
	 */
	public List getKnr2InfoFrom(String kaiCode, String knrCode2From);

	// ��Ԃ̃f�[�^���擾
	/**  */
	public String getKnr2InfoTo_QUERY = "KAI_CODE = ? AND KNR_CODE_2 <= ? ORDER BY KNR_CODE_2 ";

	/**
	 * @param kaiCode
	 * @param knrCode2To
	 * @return List
	 */
	public List getKnr2InfoTo(String kaiCode, String knrCode2To);

	// �q�d�e����
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,KNR_CODE_2,KNR_NAME_S_2,KNR_NAME_K_2,beginCode,endCode";

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
	public String searchKnrNameS2_ARGS = "slipDate, kaiCode, knrCode, beginCode, endCode";

	/**
	 * �Ǘ�2�}�X�^�f�[�^�̊Ǘ�2���̂�����<BR>
	 * �Ǘ�2�}�X�^�f�[�^�̊Ǘ�2���̂�����
	 * 
	 * @param slipDate �`�[���t
	 * @param kaiCode ��ЃR�[�h
	 * @param knrCode �Ǘ�2�R�[�h
	 * @param beginCode
	 * @param endCode
	 * @return �Ǘ�2�}�X�^�w�b�_�[�f�[�^
	 */
	public KNR2_MST searchKnrNameS2(Date slipDate, String kaiCode, String knrCode, String beginCode, String endCode);


	/** �p�����[�^�[ */
	public String getData_ARGS = "param";
	
	/**
	 * �Ǘ��}�X�^�f�[�^�擾
	 * 
	 * @param param
	 * @return �Ǘ��}�X�^���X�g
	 */
	public List<KNR2_MST> getData(KnrMstParam param);

	/** �p�����[�^�[ */
	public String getKnr2MstData_ARGS = "param";
	
	/**
	 * �Ǘ��}�X�^�f�[�^�擾
	 * 
	 * @param param
	 * @return �Ǘ��}�X�^���X�g
	 */
	public List<KNR2_MST> getKnr2MstData(KnrMstParam param);

	
}
