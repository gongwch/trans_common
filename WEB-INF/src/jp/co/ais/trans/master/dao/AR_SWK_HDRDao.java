package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * AR�d��w�b�_Dao
 */
public interface AR_SWK_HDRDao {

	/** Entity */
	public Class BEAN = AR_SWK_HDR.class;

	/**
	 * �S�f�[�^�擾
	 * 
	 * @return �f�[�^���X�g
	 */
	public List getAllAR_SWK_HDR();

	/** getArSwkInfo�N�G�� */
	public String getArSwkInfo_QUERY = "KAI_CODE = ? AND SWK_DEN_DATE BETWEEN ? AND ? AND SWK_DEN_NO BETWEEN ? AND ? ORDER BY SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * @param KAI_CODE
	 * @param SWK_DEN_DATE_FROM
	 * @param SWK_DEN_DATE_TO
	 * @param SWK_DEN_NO_FROM
	 * @param SWK_DEN_NO_TO
	 * @return �f�[�^���X�g
	 */
	public List getArSwkInfo(String KAI_CODE, Date SWK_DEN_DATE_FROM, Date SWK_DEN_DATE_TO, String SWK_DEN_NO_FROM,
		String SWK_DEN_NO_TO);

	/** �w�肳�ꂽ�f�[�^�̎擾 */
	public String getAR_SWK_HDRByKaicodeSwkdendateSwkdenno_ARGS = "KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * PK����
	 * 
	 * @param kaiCode
	 * @param swkdenDATE
	 * @param swkdenNO
	 * @return AR�d��w�b�_
	 */
	public AR_SWK_HDR getAR_SWK_HDRByKaicodeSwkdendateSwkdenno(String kaiCode, Date swkdenDATE, String swkdenNO);

	/** getAR_SWK_HDRBySwkdenno���� */
	public String getAR_SWK_HDRBySwkdenno_ARGS = "SWK_DEN_NO";

	/**
	 * @param swkdenNO
	 * @return �f�[�^���X�g
	 */
	public List getAR_SWK_HDRBySwkdenno(String swkdenNO);

	/** getAR_SWK_HDRByKaicodeSwkdenno���� */
	public String getAR_SWK_HDRByKaicodeSwkdenno_ARGS = "KAI_CODE, SWK_DEN_NO";

	/**
	 * �`�[�p
	 * 
	 * @param kaiCode
	 * @param swkdenNO
	 * @return �f�[�^���X�g
	 */
	public AR_SWK_HDR getAR_SWK_HDRByKaicodeSwkdenno(String kaiCode, String swkdenNO);

	/** getAR_SWK_HDRByKaicodeSwkdennoUsrid���� */
	public String getAR_SWK_HDRByKaicodeSwkdennoUsrid_ARGS = "KAI_CODE, SWK_DEN_NO, USR_ID";

	/**
	 * @param kaiCode
	 * @param swkdenNO
	 * @param usrId
	 * @return �f�[�^
	 */
	public AR_SWK_HDR getAR_SWK_HDRByKaicodeSwkdennoUsrid(String kaiCode, String swkdenNO, String usrId);

	/**
	 * �o�^
	 * 
	 * @param dto
	 */
	public void insert(AR_SWK_HDR dto);

	/**
	 * �X�V
	 * 
	 * @param dto
	 */
	public void update(AR_SWK_HDR dto);

	/**
	 * �폜
	 * 
	 * @param dto
	 */
	public void delete(AR_SWK_HDR dto);

	/**
	 * �`�[�ԍ���Key�ɂ��čX�V�敪�֘A�����X�V
	 * 
	 * @param dto �f�[�^
	 */
	public void updateUpdKbn(AR_SWK_HDR dto);

	/** updateUnlockShareByUser ������ */
	public String updateUnlockShareByUser_ARGS = "kaiCode, usrId, prgId";

	/**
	 * AR�d��`�[�r�� �������� <BR>
	 * ���[�U���u�ҏW���v��AR�d��`�[���u�ʏ�v�ɋ�����������B
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param usrId ���[�UID
	 * @param prgId �v���O����ID
	 */
	public void updateUnlockShareByUser(String kaiCode, String usrId, String prgId);

	/** deleteByDenNo SQL */
	public static final String deleteByDenNo_QUERY = "KAI_CODE = ? AND SWK_DEN_NO = ?";

	/**
	 * �w�肳�ꂽKey�ɕR�Â��f�[�^��S�č폜����
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param slipNo �`�[�ԍ�
	 */
	public void deleteByDenNo(String companyCode, String slipNo);

	/** getLockSlip sql */
	public static final String getLockSlip_QUERY = "KAI_CODE = ? AND SWK_SHR_KBN =1";

	/**
	 * �r���`�[���X�g�擾
	 * 
	 * @param kAI_CODE
	 * @return �r���`�[���X�g
	 */
	public List<AR_SWK_HDR> getLockSlip(String kAI_CODE);
	
	/**
	 * AR�d��`�[�r�� �v���O�����w��̋������� <BR>
	 * ���[�U���u�ҏW���v��AR�d��`�[���u�ʏ�v�ɋ�����������B
	 */
	public String updateUnlockShareByUsrPrg_ARGS = "kaiCode, usrId, prgId";

	/**
	 * @param kaiCode ��ЃR�[�h
	 * @param usrId ���[�UID
	 * @param prgId �v���O����ID
	 */
	public void updateUnlockShareByUsrPrg(String kaiCode, String usrId, String prgId);

}
