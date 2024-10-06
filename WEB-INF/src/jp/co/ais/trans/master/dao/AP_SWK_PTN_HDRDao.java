package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * AP�d��p�^��Dao
 */
public interface AP_SWK_PTN_HDRDao {

	/** AP�d��p�^��bean */
	public Class BEAN = AP_SWK_PTN_HDR.class;

	/**
	 * �S�̎擾
	 * 
	 * @return List
	 */
	public List getAllAP_SWK_PTN_HDR();

	/** �p�����[�^ */
	public String getApSwkPtnInfo_QUERY = "KAI_CODE = ? AND SWK_DEN_DATE BETWEEN ? AND ? AND SWK_DEN_NO BETWEEN ? AND ? ORDER BY SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * ��������
	 * 
	 * @param KAI_CODE
	 * @param SWK_DEN_DATE_FROM
	 * @param SWK_DEN_DATE_TO
	 * @param SWK_DEN_NO_FROM
	 * @param SWK_DEN_NO_TO
	 * @return List
	 */
	public List getApSwkPtnlInfo(String KAI_CODE, Date SWK_DEN_DATE_FROM, Date SWK_DEN_DATE_TO, String SWK_DEN_NO_FROM,
		String SWK_DEN_NO_TO);

	/** �w�肳�ꂽ�f�[�^�̎擾 */
	public String getAP_SWK_PTN_HDRByKaicodeSwkdendateSwkdenno_ARGS = "KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * PK����
	 * 
	 * @param kaiCode
	 * @param swkdenDATE
	 * @param swkdenNO
	 * @return AP�d��p�^��
	 */
	public AP_SWK_PTN_HDR getAP_SWK_PTN_HDRByKaicodeSwkdendateSwkdenno(String kaiCode, Date swkdenDATE, String swkdenNO);

	/**
	 * �o�^
	 * 
	 * @param dto
	 */
	public void insert(AP_SWK_PTN_HDR dto);

	/**
	 * �X�V
	 * 
	 * @param dto
	 */
	public void update(AP_SWK_PTN_HDR dto);

	/**
	 * �폜
	 * 
	 * @param dto
	 */
	public void delete(AP_SWK_PTN_HDR dto);

	/** updateUnlockShareByUser ������ */
	public String updateUnlockShareByUser_ARGS = "kaiCode, usrId, prgId";

	/**
	 * �p�^�[��AP�d��`�[�r�� �������� <BR>
	 * ���[�U���u�ҏW���v�̃p�^�[��AP�d��`�[���u�ʏ�v�ɋ�����������B
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param usrId ���[�UID
	 * @param prgId �v���O����ID
	 */
	public void updateUnlockShareByUser(String kaiCode, String usrId, String prgId);

	/** getLockSlip sql */
	public static final String getLockSlip_QUERY = "KAI_CODE = ? AND SWK_SHR_KBN =1";

	/**
	 * �r���`�[���X�g�擾
	 * 
	 * @param kAI_CODE
	 * @return �r���`�[���X�g
	 */
	public List<AP_SWK_PTN_HDR> getLockSlip(String kAI_CODE);

	/**
	 * �p�^�[��AP�d��`�[�r�� �v���O�����w��̋������� <BR>
	 * ���[�U���u�ҏW���v�̃p�^�[��AP�d��`�[���u�ʏ�v�ɋ�����������B
	 */
	public String updateUnlockShareByUsrPrg_ARGS = "kaiCode, usrId, prgId";

	/**
	 * @param kaiCode ��ЃR�[�h
	 * @param usrId ���[�UID
	 * @param prgId �v���O����ID
	 */
	public void updateUnlockShareByUsrPrg(String kaiCode, String usrId, String prgId);

	/** deleteByDenNo SQL */
	public static final String deleteByKaiCodeDenNo_QUERY = "KAI_CODE = ? AND SWK_DEN_NO = ?";

	/**
	 * �w�肳�ꂽKey�ɕR�Â��f�[�^��S�č폜����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param ptnNo �`�[�ԍ�
	 */
	public void deleteByKaiCodeDenNo(String kaiCode, String ptnNo);

	/** �p�����[�^ */
	public String getAP_SWK_PTN_HDRByKaicodeSwkdenno_ARGS = "KAI_CODE,SWK_DEN_NO";

	/**
	 * PK����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param denNo �`�[�ԍ�
	 * @return AP�p�^�[���w�b�_�[
	 */
	public AP_SWK_PTN_HDR getAP_SWK_PTN_HDRByKaicodeSwkdenno(String kaiCode, String denNo);
}
