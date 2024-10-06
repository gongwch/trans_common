package jp.co.ais.trans.master.dao;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �o�b�`����Dao
 */
public interface BAT_CTLDao extends Serializable {

	/** Bean */
	public Class BEAN = BAT_CTL.class;

	/**
	 * �S�f�[�^�擾
	 * 
	 * @return �f�[�^���X�g
	 */
	public List getAllBAT_CTL();

	/** getBatchListByCompanyCode QUERY */
	public String getBatchListByCompanyCode_QUERY = "KAI_CODE = ? ORDER BY BAT_ID";

	/**
	 * ��ЃR�[�h�Ńo�b�`�r�����X�g���擾
	 * 
	 * @param companyCode
	 * @return �o�b�`�r�����X�g
	 */
	public List<BAT_CTL> getBatchListByCompanyCode(String companyCode);

	/** �ʃv���O�����ł̔r���m�F */
	public String getBatchListByID_ARGS = "kaiCode, batId, prgId";

	/** �ʃv���O�����ł̔r���m�F */
	public String getBatchListByID_QUERY = "KAI_CODE = /*kaiCode*/  AND BAT_ID = /*batId*/ AND PRG_ID NOT IN /*prgId*/('', '')";

	/**
	 * �ʃv���O�����ł̔r���m�F
	 * 
	 * @param kaiCode
	 * @param batId
	 * @param prgId
	 * @return �o�b�`�r�����X�g
	 */
	public List<BAT_CTL> getBatchListByID(String kaiCode, String batId, List<String> prgId);

	/** getBatCtlInfo QUERY */
	public String getBatCtlByPK_ARGS = "KAI_CODE, BAT_ID";

	/**
	 * PK�Ńf�[�^���擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param batId �o�b�`�R���g���[��ID
	 * @return BAT_CTL
	 */
	public BAT_CTL getBatCtlByPK(String kaiCode, String batId);

	/** getBAT_CTLById ARGS */
	public String getBAT_CTLById_ARGS = "KAI_CODE, BAT_ID, PRG_ID, USR_ID";

	/**
	 * �w������Ńf�[�^���擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param batId �o�b�`�R���g���[��ID
	 * @param prgId �v���O����ID
	 * @param usrId ���[�UID
	 * @return BAT_CTL
	 */
	public BAT_CTL getBAT_CTLById(String kaiCode, String batId, String prgId, String usrId);

	/**
	 * �V�K�o�^
	 * 
	 * @param dto
	 */
	public void insert(BAT_CTL dto);

	/**
	 * �X�V
	 * 
	 * @param dto
	 */
	public void update(BAT_CTL dto);

	/**
	 * �폜
	 * 
	 * @param dto
	 */
	public void delete(BAT_CTL dto);

	/**
	 * �폜
	 * 
	 * @param list
	 * @return �폜����
	 */
	public int deleteList(List<BAT_CTL> list);

	/** deleteForced */
	public String deleteForced_ARGS = "KAI_CODE, USR_ID";

	/**
	 * ���O�C���E���O�A�E�g���̋����f���[�g
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param usrId ���[�UID
	 * @return �폜����
	 */
	public int deleteForced(String kaiCode, String usrId);

	/** deleteByUsrPrg */
	public String deleteByUsrPrg_ARGS = "KAI_CODE, PRG_ID, USR_ID";

	/**
	 * �v���O�����w��̋����f���[�g
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param prgId �v���O����ID
	 * @param usrId ���[�UID
	 * @return �폜����
	 */
	public int deleteByUsrPrg(String kaiCode, String prgId, String usrId);

	/** deleteById */
	public String deleteById_QUERY = "KAI_CODE = ? AND BAT_ID = ? AND PRG_ID = ? AND USR_ID = ?";

	/**
	 * ID���w�肵���폜
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param batId �o�b�`�R���g���[��ID
	 * @param prgId �v���O����ID
	 * @param usrId ���[�UID
	 * @return �폜����
	 */
	public int deleteById(String kaiCode, String batId, String prgId, String usrId);

}
