package jp.co.ais.trans.master.dao;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �r���R���g���[��Dao
 */
public interface HAITA_CTLDao extends Serializable {

	/** Entity��` */
	public Class BEAN = HAITA_CTL.class;

	/**
	 * �S�f�[�^�擾
	 * 
	 * @return �S�f�[�^
	 */
	public List getAllHAITA_CTL();

	/** getHaitaCtlInfo QUERY */
	public String getHaitaCtlInfo_QUERY = "KAI_CODE = ? AND SHORI_KBN BETWEEN ? AND ? AND TRI_CODE BETWEEN ? AND ? ORDER BY SHORI_KBN, TRI_CODE ";

	/**
	 * �f�[�^�擾
	 * 
	 * @param KAI_CODE
	 * @param SHORI_KBN_FROM
	 * @param SHORI_KBN_TO
	 * @param TRI_CODE_FROM
	 * @param TRI_CODE_TO
	 * @return ���X�g�f�[�^
	 */
	public List getHaitaCtlInfo(String KAI_CODE, String SHORI_KBN_FROM, String SHORI_KBN_TO, String TRI_CODE_FROM,
		String TRI_CODE_TO);

	/** getHAITA_CTLByKaicodeShorikbnTricode ARGS */
	public String getHAITA_CTLByKaicodeShorikbnTricode_ARGS = "KAI_CODE, SHORI_KBN, TRI_CODE";

	/**
	 * �f�[�^�擾
	 * 
	 * @param kaiCode
	 * @param shoriKBN
	 * @param triCode
	 * @return Entity
	 */
	public HAITA_CTL getHAITA_CTLByKaicodeShorikbnTricode(String kaiCode, String shoriKBN, String triCode);

	/** getHAITA_CTLByKaicodeShorikbnTricodeGyono ARGS */
	public String getHAITA_CTLByKaicodeShorikbnTricodeGyono_ARGS = "KAI_CODE, SHORI_KBN, TRI_CODE, GYO_NO";

	/**
	 * �f�[�^�擾
	 * 
	 * @param kaiCode
	 * @param shoriKBN
	 * @param triCode
	 * @param gyoNo
	 * @return Entity
	 */
	public HAITA_CTL getHAITA_CTLByKaicodeShorikbnTricodeGyono(String kaiCode, String shoriKBN, String triCode,
		String gyoNo);

	/**
	 * �o�^
	 * 
	 * @param dto
	 */
	public void insert(HAITA_CTL dto);

	/**
	 * �X�V
	 * 
	 * @param dto
	 */
	public void update(HAITA_CTL dto);

	/**
	 * �폜
	 * 
	 * @param dto
	 */
	public void delete(HAITA_CTL dto);

	/** deleteLockUserQuery */
	public static final String deleteLockUser_SQL = "DELETE FROM HAITA_CTL WHERE KAI_CODE = ? AND USR_ID = ?";

	/**
	 * ���[�U�R�[�h�ō폜(�r������)<br>
	 * 0���G���[����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @return �X�V����
	 */
	public int deleteLockUser(String kaiCode, String userCode);

	/** deleteByUsrPrg Query */
	public static final String deleteByUsrPrg_SQL = "DELETE FROM HAITA_CTL WHERE KAI_CODE = ? AND USR_ID = ? AND PRG_ID = ?";

	/**
	 * ���[�U�R�[�h�ƃv���O����ID�ō폜(�r������).<br>
	 * 0���G���[����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @param prgId �v���O����ID
	 * @return �X�V����
	 */
	public int deleteByUsrPrg(String kaiCode, String userCode, String prgId);

	/** �w�肵�����[�U�[�̎����r�������� (0���G���[����) */
	public static final String deleteByKaicodeShorikbnUsrID_SQL = "DELETE FROM HAITA_CTL WHERE KAI_CODE = ? AND SHORI_KBN = ? AND USR_ID = ?";

	/**
	 * �w�肵�����[�U�[�̎����r��������(0���G���[����)
	 * 
	 * @param KaiCode
	 * @param shoriKbn
	 * @param usrId
	 * @return �X�V����
	 */
	public int deleteByKaicodeShorikbnUsrID(String KaiCode, String shoriKbn, String usrId);

}
