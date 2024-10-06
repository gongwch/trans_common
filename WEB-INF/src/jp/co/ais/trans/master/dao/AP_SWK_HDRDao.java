package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * AP�d��w�b�_Dao
 */
public interface AP_SWK_HDRDao {

	/** AP�d��w�b�_ */
	public Class BEAN = AP_SWK_HDR.class;

	/**
	 * �S�̌���
	 * 
	 * @return AP�d��w�b�_���X�g
	 */
	public List getAllAP_SWK_HDR();

	/** getApSwkInfo�N�G�� */
	public String getApSwkInfo_QUERY = "KAI_CODE = ? AND SWK_DEN_DATE BETWEEN ? AND ? AND SWK_DEN_NO BETWEEN ? AND ? ORDER BY SWK_DEN_DATE, SWK_DEN_NO ";

	/**
	 * @param KAI_CODE
	 * @param SWK_DEN_DATE_FROM
	 * @param SWK_DEN_DATE_TO
	 * @param SWK_DEN_NO_FROM
	 * @param SWK_DEN_NO_TO
	 * @return �f�[�^���X�g
	 */
	public List getApSwkInfo(String KAI_CODE, Date SWK_DEN_DATE_FROM, Date SWK_DEN_DATE_TO, String SWK_DEN_NO_FROM,
		String SWK_DEN_NO_TO);

	/** �w�肳�ꂽ�f�[�^�̎擾 */
	public String getAP_SWK_HDRByKaicodeSwkdendateSwkdenno_ARGS = "KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * PK����
	 * 
	 * @param kaiCode
	 * @param swkdenDATE
	 * @param swkdenNO
	 * @return AP�d��w�b�_
	 */
	public AP_SWK_HDR getAP_SWK_HDRByKaicodeSwkdendateSwkdenno(String kaiCode, Date swkdenDATE, String swkdenNO);

	/** getApSwkInfo���� */
	public String getAP_SWK_HDRBySwkdenno_ARGS = "SWK_DEN_NO";

	/**
	 * @param swkdenNO
	 * @return �f�[�^���X�g
	 */
	public List getAP_SWK_HDRBySwkdenno(String swkdenNO);

	/** getAP_SWK_HDRByKaicodeSwkdenno_ARGS���� */
	public String getAP_SWK_HDRByKaicodeSwkdenno_ARGS = "KAI_CODE, SWK_DEN_NO";

	/**
	 * �`�[�p
	 * 
	 * @param kaiCode
	 * @param swkdenNO
	 * @return �f�[�^
	 */
	public AP_SWK_HDR getAP_SWK_HDRByKaicodeSwkdenno(String kaiCode, String swkdenNO);

	/** getAP_SWK_HDRByKaicodeSwkdennoUsrid_ARGS���� */
	public String getAP_SWK_HDRByKaicodeSwkdennoUsrid_ARGS = "KAI_CODE, SWK_DEN_NO, USR_ID";

	/**
	 * @param kaiCode
	 * @param swkdenNO
	 * @param usrId
	 * @return �f�[�^
	 */
	public AP_SWK_HDR getAP_SWK_HDRByKaicodeSwkdennoUsrid(String kaiCode, String swkdenNO, String usrId);

	/**
	 * �o�^
	 * 
	 * @param dto
	 */
	public void insert(AP_SWK_HDR dto);

	/**
	 * �X�V
	 * 
	 * @param dto
	 */
	public void update(AP_SWK_HDR dto);

	/**
	 * �폜
	 * 
	 * @param dto
	 */
	public void delete(AP_SWK_HDR dto);

	/**
	 * �`�[�ԍ���Key�ɂ��čX�V�敪�֘A�����X�V
	 * 
	 * @param dto �f�[�^
	 */
	public void updateUpdKbn(AP_SWK_HDR dto);

	/** updateUnlockShareByUser sql */
	public String updateUnlockShareByUser_ARGS = "kaiCode, usrId, prgId";

	/**
	 * AP�d��`�[�r�� �������� <BR>
	 * ���[�U���u�ҏW���v��AP�d��`�[���u�ʏ�v�ɋ�����������B
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

	/** getLockSlip SQL */
	public static final String getLockSlip_QUERY = "KAI_CODE = ? AND SWK_SHR_KBN =1 ";

	/**
	 * �r���`�[���X�g�擾
	 * 
	 * @param kAI_CODE
	 * @return �r���`�[���X�g
	 */
	public List<AP_SWK_HDR> getLockSlip(String kAI_CODE);

	/**
	 * AP�d��`�[�r�� �v���O�����w��̋������� <BR>
	 * ���[�U���u�ҏW���v��AP�d��`�[���u�ʏ�v�ɋ�����������B
	 */
	public String updateUnlockShareByUsrPrg_ARGS = "kaiCode, usrId, prgId";

	/**
	 * @param kaiCode ��ЃR�[�h
	 * @param usrId ���[�UID
	 * @param prgId �v���O����ID
	 */
	public void updateUnlockShareByUsrPrg(String kaiCode, String usrId, String prgId);

	/** �f�[�^���X�V����(AP�Ј��x��) */
	public String updateApEmpPayData_ARGS = "bean";

	/**
	 * �f�[�^���X�V����(AP�Ј��x��)
	 * 
	 * @param bean AP�d��ެ���BEAN
	 */
	public void updateApEmpPayData(AP_SWK_HDR bean);

	/** �f�[�^���X�V����(AP�Ј��x��) */
	public String updateApEmpPayCancelData_ARGS = "bean";

	/**
	 * �f�[�^���X�V����(AP�Ј��x��)
	 * 
	 * @param bean AP�d��ެ���BEAN
	 */
	public void updateApEmpPayCancelData(AP_SWK_HDR bean);

	/** �����`�[�ԍ����X�V���� SQL */
	public static final String updateKaridrdenno_SQL = "UPDATE AP_SWK_HDR SET SWK_KARI_DR_DEN_NO = ? WHERE KAI_CODE = ? AND SWK_DEN_NO = ?";

	/**
	 * �����`�[�ԍ����X�V����
	 * 
	 * @param drDenNo �����`�[�ԍ�
	 * @param kaiCode ��ЃR�[�h
	 * @param denNo �`�[�ԍ�
	 */
	public void updateKaridrdenno(String drDenNo, String kaiCode, String denNo);

	/** �`�o�d��W���[�i���w�b�_���X�V����(�Ј��x����t����) */
	public String updateSwkHdrAcceptClos_ARGS = "bean";

	/**
	 * �`�o�d��W���[�i���w�b�_���X�V����(�Ј��x����t����)
	 * 
	 * @param bean AP�d��ެ���BEAN
	 */
	public void updateSwkHdrAcceptClos(AP_SWK_HDR bean);
	
	/** deleteByKaicodeDenno SQL */
	public static final String deleteByKaicodeDenno_QUERY = "KAI_CODE = ? AND SWK_DEN_NO = ?";

	/**
	 * �w�肳�ꂽKey�ɕR�Â��f�[�^��S�č폜����
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param slipNo �`�[�ԍ�
	 */
	public void deleteByKaicodeDenno(String companyCode, String slipNo);

}
