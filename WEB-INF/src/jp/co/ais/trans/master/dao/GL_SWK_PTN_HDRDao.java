package jp.co.ais.trans.master.dao;

import jp.co.ais.trans.master.entity.*;
import java.util.*;

/**
 * �p�^�[��GL�`�[Dao
 */
public interface GL_SWK_PTN_HDRDao {

	/** �p�^�[��GL�`�[Bean */
	public Class BEAN = GL_SWK_PTN_HDR.class;

	/**
	 * �p�^�[��GL�`�[�S�̎擾
	 * 
	 * @return �p�^�[��GL�`�[���X�g
	 */
	public List getAllGL_SWK_PTN_HDR();

	/** Query������ */
	public String getGlSwkPtnInfo_QUERY = "KAI_CODE = ? AND SWK_DEN_DATE BETWEEN ? AND ? AND SWK_DEN_NO BETWEEN ? AND ? ORDER BY SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * ��������
	 * 
	 * @param KAI_CODE
	 * @param SWK_DEN_DATE_FROM
	 * @param SWK_DEN_DATE_TO
	 * @param SWK_DEN_NO_FROM
	 * @param SWK_DEN_NO_TO
	 * @return �p�^�[��GL�`�[���X�g
	 */
	public List getGlSwkPtnInfo(String KAI_CODE, Date SWK_DEN_DATE_FROM, Date SWK_DEN_DATE_TO, String SWK_DEN_NO_FROM,
		String SWK_DEN_NO_TO);

	/** �w�肳�ꂽ�f�[�^�̎擾 */
	public String getGL_SWK_PTN_HDRByKaicodeSwkdendateSwkdenno_ARGS = "KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * �w�肳�ꂽ�f�[�^�̎擾
	 * 
	 * @param kaiCode
	 * @param swkdenDATE
	 * @param swkdenNO
	 * @return �p�^�[��GL�`�[
	 */
	public GL_SWK_PTN_HDR getGL_SWK_PTN_HDRByKaicodeSwkdendateSwkdenno(String kaiCode, Date swkdenDATE, String swkdenNO);

	/**
	 * �o�^
	 * 
	 * @param dto
	 */
	public void insert(GL_SWK_PTN_HDR dto);

	/**
	 * �X�V
	 * 
	 * @param dto
	 */
	public void update(GL_SWK_PTN_HDR dto);

	/**
	 * �폜
	 * 
	 * @param dto
	 */
	public void delete(GL_SWK_PTN_HDR dto);

	/** Query������ */
	public String updateUnlockShareByUser_ARGS = "kaiCode, usrId, prgId";

	/**
	 * �p�^�[��GL�`�[�r�� �������� <BR>
	 * ���[�U���u�ҏW���v�̃p�^�[��GL�d��`�[���u�ʏ�v�ɋ�����������B
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param usrId ���[�UID
	 * @param prgId �v���O����ID
	 */
	public void updateUnlockShareByUser(String kaiCode, String usrId, String prgId);

	/** Query������ */
	public String getLockSlip_QUERY = "KAI_CODE = ? AND SWK_SHR_KBN =1";

	/**
	 * �r����Ԃ̓`�[���X�g�擾
	 * 
	 * @param kAI_CODE
	 * @return �r����Ԃ̓`�[���X�g
	 */
	public List<GL_SWK_PTN_HDR> getLockSlip(String kAI_CODE);

	/**
	 * �p�^�[��GL�d��`�[�r�� �v���O�����w��̋������� <BR>
	 * ���[�U���u�ҏW���v�̃p�^�[��GL�d��`�[���u�ʏ�v�ɋ�����������B
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
	public String getGL_SWK_PTN_HDRByKaicodeSwkdenno_ARGS = "KAI_CODE,SWK_DEN_NO";
	
	/**
	 * PK����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param denNo �`�[�ԍ�
	 * @return AR�p�^�[���w�b�_�[
	 */
	public GL_SWK_PTN_HDR getGL_SWK_PTN_HDRByKaicodeSwkdenno(String kaiCode,String denNo);
}
