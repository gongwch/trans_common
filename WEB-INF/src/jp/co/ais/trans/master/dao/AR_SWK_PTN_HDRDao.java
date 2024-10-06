package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * AR�d��p�^��DAO
 */
public interface AR_SWK_PTN_HDRDao {

	/** AR�d��p�^��Beans */
	public Class BEAN = AR_SWK_PTN_HDR.class;

	/**
	 * �S�̌���
	 * 
	 * @return AR�d��p�^�����X�g
	 */
	public List getAllAR_SWK_PTN_HDR();

	/** getArSwkPtnlInfo SQL */
	public String getArSwkPtnInfo_QUERY = "KAI_CODE = ? AND SWK_DEN_DATE BETWEEN ? AND ? AND SWK_DEN_NO BETWEEN ? AND ? ORDER BY SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * ���t�͈́A�`�[�ԍ��͈͌���
	 * 
	 * @param KAI_CODE
	 * @param SWK_DEN_DATE_FROM
	 * @param SWK_DEN_DATE_TO
	 * @param SWK_DEN_NO_FROM
	 * @param SWK_DEN_NO_TO
	 * @return AR�d��p�^�����X�g
	 */
	public List getArSwkPtnlInfo(String KAI_CODE, Date SWK_DEN_DATE_FROM, Date SWK_DEN_DATE_TO, String SWK_DEN_NO_FROM,
		String SWK_DEN_NO_TO);

	/** getAR_SWK_PTN_HDRByKaicodeSwkdendateSwkdennno SQL */
	public String getAR_SWK_PTN_HDRByKaicodeSwkdendateSwkdennno_ARGS = "KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * PK�ɂ�錟��
	 * 
	 * @param kaiCode
	 * @param swkdenDATE
	 * @param swkdenNO
	 * @return AR_SWK_PTN_HDR
	 */
	public AR_SWK_PTN_HDR getAR_SWK_PTN_HDRByKaicodeSwkdendateSwkdennno(String kaiCode, Date swkdenDATE, String swkdenNO);

	/**
	 * �o�^
	 * 
	 * @param dto
	 */
	public void insert(AR_SWK_PTN_HDR dto);

	/**
	 * �X�V
	 * 
	 * @param dto
	 */
	public void update(AR_SWK_PTN_HDR dto);

	/**
	 * �폜
	 * 
	 * @param dto
	 */
	public void delete(AR_SWK_PTN_HDR dto);

	/** updateUnlockShareByUser SQL */
	public String updateUnlockShareByUser_ARGS = "kaiCode, usrId, prgId";

	/**
	 * �p�^�[��AR�d��`�[�r�� �������� <BR>
	 * ���[�U���u�ҏW���v�̃p�^�[��AR�d��`�[���u�ʏ�v�ɋ�����������B
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
	public List<AR_SWK_PTN_HDR> getLockSlip(String kAI_CODE);

	/**
	 * �p�^�[��AR�d��`�[�r�� �v���O�����w��̋������� <BR>
	 * ���[�U���u�ҏW���v�̃p�^�[��AR�d��`�[���u�ʏ�v�ɋ�����������B
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
	public String getAR_SWK_PTN_HDRByKaicodeSwkdenno_ARGS = "KAI_CODE,SWK_DEN_NO";

	/**
	 * PK����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param denNo �`�[�ԍ�
	 * @return AR�p�^�[���w�b�_�[
	 */
	public AR_SWK_PTN_HDR getAR_SWK_PTN_HDRByKaicodeSwkdenno(String kaiCode, String denNo);
}
