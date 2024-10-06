package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �Ǘ��R�}�X�^Dao
 */
public interface KNR3_MSTDao {

	/**  */
	public Class BEAN = KNR3_MST.class;

	/**
	 * @return List
	 */
	public List getAllKNR3_MST();

	/**  */
	public String getKnr3Info_QUERY = "KAI_CODE = ? AND KNR_CODE_3 BETWEEN ? AND ? ORDER BY KNR_CODE_3 ";

	/**
	 * @param KAI_CODE
	 * @param KNR_CODE_3_FROM
	 * @param KNR_CODE_3_TO
	 * @return List
	 */
	public List getKnr3Info(String KAI_CODE, String KNR_CODE_3_FROM, String KNR_CODE_3_TO);

	// �w�肳�ꂽ�f�[�^�̎擾
	/**  */
	public String getKNR3_MSTByKaicodeKnrcode3_ARGS = "KAI_CODE,KNR_CODE_3";

	/**
	 * @param kaiCode
	 * @param knrCode3
	 * @return KNR3_MST
	 */
	public KNR3_MST getKNR3_MSTByKaicodeKnrcode3(String kaiCode, String knrCode3);

	/**
	 * @param dto
	 */
	public void insert(KNR3_MST dto);

	/**
	 * @param dto
	 */
	public void update(KNR3_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KNR3_MST dto);

	// ���L�� ISFnet China �ǉ���

	// ����Ђ̑S�����R�[�h
	/**  */
	public String getAllKNR3_MST2_QUERY = "KAI_CODE = ? ORDER BY KNR_CODE_3";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllKNR3_MST2(String kaiCode);

	// ��Ԃ̃f�[�^���擾
	/**  */
	public String getKnr3InfoFrom_QUERY = "KAI_CODE = ? AND KNR_CODE_3 >= ? ORDER BY KNR_CODE_3 ";

	/**
	 * @param kaiCode
	 * @param knrCode3From
	 * @return List
	 */
	public List getKnr3InfoFrom(String kaiCode, String knrCode3From);

	// ��Ԃ̃f�[�^���擾
	/**  */
	public String getKnr3InfoTo_QUERY = "KAI_CODE = ? AND KNR_CODE_3 <= ? ORDER BY KNR_CODE_3 ";

	/**
	 * @param kaiCode
	 * @param knrCode3To
	 * @return List
	 */
	public List getKnr3InfoTo(String kaiCode, String knrCode3To);

	// �q�d�e����
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,KNR_CODE_3,KNR_NAME_S_3,KNR_NAME_K_3,beginCode,endCode";

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
	 * �Ǘ��}�X�^3����
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
	public String searchKnrNameS3_ARGS = "slipDate, kaiCode, knrCode, beginCode, endCode";

	/**
	 * �Ǘ�3�}�X�^�f�[�^�̊Ǘ�3���̂�����<BR>
	 * �Ǘ�3�}�X�^�f�[�^�̊Ǘ�3���̂�����
	 * 
	 * @param slipDate �`�[���t
	 * @param kaiCode ��ЃR�[�h
	 * @param knrCode �Ǘ�3�R�[�h
	 * @param beginCode
	 * @param endCode
	 * @return �Ǘ�3�}�X�^�w�b�_�[�f�[�^
	 */
	public KNR3_MST searchKnrNameS3(Date slipDate, String kaiCode, String knrCode, String beginCode, String endCode);


	/** �p�����[�^�[ */
	public String getData_ARGS = "param";
	
	/**
	 * �Ǘ��}�X�^�f�[�^�擾
	 * 
	 * @param param
	 * @return �Ǘ��}�X�^���X�g
	 */
	public List<KNR3_MST> getData(KnrMstParam param);

	/** �p�����[�^�[ */
	public String getKnr3MstData_ARGS = "param";
	
	/**
	 * �Ǘ��}�X�^�f�[�^�擾
	 * 
	 * @param param
	 * @return �Ǘ��}�X�^���X�g
	 */
	public List<KNR3_MST> getKnr3MstData(KnrMstParam param);

}
