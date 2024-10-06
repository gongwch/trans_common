package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * GL�d��w�b�_Dao
 */
public interface GL_SWK_HDRDao {

	/** Entity */
	public Class BEAN = GL_SWK_HDR.class;

	/**
	 * �S�f�[�^�擾
	 * 
	 * @return �f�[�^���X�g
	 */
	public List getAllGL_SWK_HDR();

	/** getGlSwkInfo���� */
	public String getGlSwkInfo_QUERY = "KAI_CODE = ? AND SWK_DEN_DATE BETWEEN ? AND ? AND SWK_DEN_NO BETWEEN ? AND ? ORDER BY SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * �����ɂ��d��w�b�_��񃊃X�g�擾
	 * 
	 * @param KAI_CODE
	 * @param SWK_DEN_DATE_FROM
	 * @param SWK_DEN_DATE_TO
	 * @param SWK_DEN_NO_FROM
	 * @param SWK_DEN_NO_TO
	 * @return �d��w�b�_��񃊃X�g
	 */
	public List getGlSwkInfo(String KAI_CODE, Date SWK_DEN_DATE_FROM, Date SWK_DEN_DATE_TO, String SWK_DEN_NO_FROM,
		String SWK_DEN_NO_TO);

	/** �w�肳�ꂽ�f�[�^�̎擾 */
	public String getGL_SWK_HDRByKaicodeSwkdendateSwkdenno_ARGS = "KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * PK����
	 * 
	 * @param kaiCode
	 * @param swkdenDATE
	 * @param swkdenNO
	 * @return GL�d��w�b�_
	 */
	public GL_SWK_HDR getGL_SWK_HDRByKaicodeSwkdendateSwkdenno(String kaiCode, Date swkdenDATE, String swkdenNO);

	/** getGL_SWK_HDRByKaicodeSwkdenno���� */
	public String getGL_SWK_HDRByKaicodeSwkdenno_ARGS = "KAI_CODE, SWK_DEN_NO";

	/**
	 * PK����
	 * 
	 * @param kaiCode
	 * @param swkdenNO
	 * @return �f�[�^
	 */
	public GL_SWK_HDR getGL_SWK_HDRByKaicodeSwkdenno(String kaiCode, String swkdenNO);

	/** getGL_SWK_HDRBySwkdenno���� */
	public String getGL_SWK_HDRBySwkdenno_ARGS = "SWK_DEN_NO";

	/**
	 * @param swkdenNO
	 * @return �f�[�^���X�g
	 */
	public List getGL_SWK_HDRBySwkdenno(String swkdenNO);

	/** getGL_SWK_HDRByKaicodeSwkdennoUsrid���� */
	public String getGL_SWK_HDRByKaicodeSwkdennoUsrid_ARGS = "KAI_CODE, SWK_DEN_NO, USR_ID";

	/**
	 * @param kaiCode
	 * @param swkdenNO
	 * @param usrId
	 * @return �f�[�^
	 */
	public GL_SWK_HDR getGL_SWK_HDRByKaicodeSwkdennoUsrid(String kaiCode, String swkdenNO, String usrId);

	/**
	 * �o�^
	 * 
	 * @param dto
	 */
	public void insert(GL_SWK_HDR dto);

	/**
	 * �X�V
	 * 
	 * @param dto
	 */
	public void update(GL_SWK_HDR dto);

	/**
	 * �폜
	 * 
	 * @param dto
	 */
	public void delete(GL_SWK_HDR dto);

	/**
	 * �`�[�ԍ���Key�ɂ��čX�V�敪�֘A�����X�V
	 * 
	 * @param dto �f�[�^
	 */
	public void updateUpdKbn(GL_SWK_HDR dto);

	/** updateUnlockShareByUser */
	public static final String updateUnlockShareByUser_ARGS = "kaiCode, usrId, prgId";

	/**
	 * GL�d��`�[�r�� �������� <BR>
	 * ���[�U���u�ҏW���v��GL�d��`�[���u�ʏ�v�ɋ�����������B
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param usrId ���[�UID
	 * @param prgId �v���O����ID
	 */
	public void updateUnlockShareByUser(String kaiCode, String usrId, String prgId);

	/** getGL_SWK_HDRByBeforeDenNo */
	public static final String getGL_SWK_HDRByBeforeDenNo_ARGS = "KAI_CODE, SWK_BEFORE_DEN_NO, SWK_DATA_KBN";

	/**
	 * GL�d��`�[ �f�[�^�敪�ƒ����O�`�[�ԍ��Ō��� <BR>
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param beforeDenNo �����O�`�[�ԍ�
	 * @param dataKbn �f�[�^�敪
	 * @return GL_SWK_HDR
	 */
	public GL_SWK_HDR getGL_SWK_HDRByBeforeDenNo(String kaiCode, String beforeDenNo, String dataKbn);

	/** deleteByKaicodeDenno SQL */
	public static final String deleteByKaicodeDenno_QUERY = "KAI_CODE = ? AND SWK_DEN_NO = ?";

	/**
	 * �w�肳�ꂽKey�ɕR�Â��f�[�^��S�č폜����
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param slipNo �`�[�ԍ�
	 */
	public void deleteByKaicodeDenno(String companyCode, String slipNo);

	/** QUERY �X�V��Ԃ̓`�[���X�g�擾 */
	public static final String getLockSlip_QUERY = "KAI_CODE = ? AND SWK_SHR_KBN =1";

	/**
	 * �X�V��Ԃ̓`�[���X�g�擾
	 * 
	 * @param kAI_CODE
	 * @return �X�V��Ԃ̓`�[���X�g
	 */
	public List<GL_SWK_HDR> getLockSlip(String kAI_CODE);

	/**
	 * GL�d��`�[�r�� �v���O�����w��̋������� <BR>
	 * ���[�U���u�ҏW���v��GL�d��`�[���u�ʏ�v�ɋ�����������B
	 */
	public String updateUnlockShareByUsrPrg_ARGS = "kaiCode, usrId, prgId";

	/**
	 * @param kaiCode ��ЃR�[�h
	 * @param usrId ���[�UID
	 * @param prgId �v���O����ID
	 */
	public void updateUnlockShareByUsrPrg(String kaiCode, String usrId, String prgId);

	/** �p�����[�^ */
	public String updateLockShareByUsrUser_ARGS = "kaiCode, denNo, prgId, usrId";

	/**
	 * �r���J�n
	 * 
	 * @param kaiCode
	 * @param denNo
	 * @param prgId
	 * @param usrId
	 */
	public void updateLockShareByUsrUser(String kaiCode, String denNo, String prgId, String usrId);

}
