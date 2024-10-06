package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �Ǘ��S�}�X�^Dao
 */
public interface KNR4_MSTDao {

	/**  */
	public Class BEAN = KNR4_MST.class;

	/**
	 * @return List
	 */
	public List getAllKNR4_MST();

	/**  */
	public String getKnr4Info_QUERY = "KAI_CODE = ? AND KNR_CODE_4 BETWEEN ? AND ? ORDER BY KNR_CODE_4 ";

	/**
	 * @param KAI_CODE
	 * @param KNR_CODE_4_FROM
	 * @param KNR_CODE_4_TO
	 * @return List
	 */
	public List getKnr4Info(String KAI_CODE, String KNR_CODE_4_FROM, String KNR_CODE_4_TO);

	// �w�肳�ꂽ�f�[�^�̎擾
	/**  */
	public String getKNR4_MSTByKaicodeKnrcode4_ARGS = "KAI_CODE,KNR_CODE_4";

	/**
	 * @param kaiCode
	 * @param knrCode4
	 * @return KNR4_MST
	 */
	public KNR4_MST getKNR4_MSTByKaicodeKnrcode4(String kaiCode, String knrCode4);

	/**
	 * @param dto
	 */
	public void insert(KNR4_MST dto);

	/**
	 * @param dto
	 */
	public void update(KNR4_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KNR4_MST dto);

	// ���L�� ISFnet China �ǉ���

	// ����Ђ̑S�����R�[�h
	/**  */
	public String getAllKNR4_MST2_QUERY = "KAI_CODE = ? ORDER BY KNR_CODE_4";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllKNR4_MST2(String kaiCode);

	// ��Ԃ̃f�[�^���擾
	/**  */
	public String getKnr4InfoFrom_QUERY = "KAI_CODE = ? AND KNR_CODE_4 >= ? ORDER BY KNR_CODE_4 ";

	/**
	 * @param kaiCode
	 * @param knrCode4From
	 * @return List
	 */
	public List getKnr4InfoFrom(String kaiCode, String knrCode4From);

	// ��Ԃ̃f�[�^���擾
	/**  */
	public String getKnr4InfoTo_QUERY = "KAI_CODE = ? AND KNR_CODE_4 <= ? ORDER BY KNR_CODE_4 ";

	/**
	 * @param kaiCode
	 * @param knrCode4To
	 * @return List
	 */
	public List getKnr4InfoTo(String kaiCode, String knrCode4To);

	// �q�d�e����
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,KNR_CODE_4,KNR_NAME_S_4,KNR_NAME_K_4,beginCode,endCode";

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
	 * �Ǘ��}�X�^4����
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
	public String searchKnrNameS4_ARGS = "slipDate, kaiCode, knrCode,beginCode,endCode";

	/**
	 * �Ǘ�4�}�X�^�f�[�^�̊Ǘ�4���̂�����<BR>
	 * �Ǘ�4�}�X�^�f�[�^�̊Ǘ�4���̂�����
	 * 
	 * @param slipDate �`�[���t
	 * @param kaiCode ��ЃR�[�h
	 * @param knrCode �Ǘ�4�R�[�h
	 * @param beginCode
	 * @param endCode
	 * @return �Ǘ�4�}�X�^�w�b�_�[�f�[�^
	 */
	public KNR4_MST searchKnrNameS4(Date slipDate, String kaiCode, String knrCode, String beginCode, String endCode);



	/** �p�����[�^�[ */
	public String getData_ARGS = "param";
	
	/**
	 * �Ǘ��}�X�^�f�[�^�擾
	 * 
	 * @param param
	 * @return �Ǘ��}�X�^���X�g
	 */
	public List<KNR4_MST> getData(KnrMstParam param);
	
	/** �p�����[�^�[ */
	public String getKnr4MstData_ARGS = "param";
	
	/**
	 * �Ǘ��}�X�^�f�[�^�擾
	 * 
	 * @param param
	 * @return �Ǘ��}�X�^���X�g
	 */
	public List<KNR4_MST> getKnr4MstData(KnrMstParam param);

}
