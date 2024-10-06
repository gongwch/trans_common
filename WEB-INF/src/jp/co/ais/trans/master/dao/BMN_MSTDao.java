package jp.co.ais.trans.master.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.master.entity.*;

/**
 * ����}�X�^Dao
 */
public interface BMN_MSTDao {

	/**  */
	public Class BEAN = BMN_MST.class;

	/**
	 * @return List
	 */
	public List getAllBMN_MST();

	/**  */
	public String getBmnInfo_QUERY = "KAI_CODE = ? AND DEP_CODE BETWEEN ? AND ? ORDER BY DEP_CODE ";

	/**
	 * @param KAI_CODE
	 * @param DEP_CODE_FROM
	 * @param DEP_CODE_TO
	 * @return List
	 */
	public List getBmnInfo(String KAI_CODE, String DEP_CODE_FROM, String DEP_CODE_TO);

	// �w�肳�ꂽ�f�[�^�̎擾
	/**  */
	public String getBMN_MSTByKaicodeDepcode_ARGS = "KAI_CODE, DEP_CODE";

	/**
	 * @param kaiCode
	 * @param depCode
	 * @return BMN_MST
	 */
	public BMN_MST getBMN_MSTByKaicodeDepcode(String kaiCode, String depCode);

	/**
	 * @param dto
	 */
	public void insert(BMN_MST dto);

	/**
	 * @param dto
	 */
	public void update(BMN_MST dto);

	/**
	 * @param dto
	 */
	public void delete(BMN_MST dto);

	// ���L�� ISFnet China �ǉ���

	// ���ׂẴ��R�[�h���擾
	/**  */
	public String getAllBMN_MST2_QUERY = "KAI_CODE = ? ORDER BY DEP_CODE";

	/**
	 * @param KAI_CODE
	 * @return List
	 */
	public List getAllBMN_MST2(String KAI_CODE);

	// ��Ԃ̃f�[�^���擾
	/**  */
	public String getBmnInfoFrom_QUERY = "KAI_CODE = ? AND DEP_CODE >= ? ORDER BY DEP_CODE ";

	/**
	 * @param KAI_CODE
	 * @param DEP_CODE_FROM
	 * @return List
	 */
	public List getBmnInfoFrom(String KAI_CODE, String DEP_CODE_FROM);

	// ��Ԃ̃f�[�^���擾
	/**  */
	public String getBmnInfoTo_QUERY = "KAI_CODE = ? AND DEP_CODE <= ? ORDER BY DEP_CODE ";

	/**
	 * @param KAI_CODE
	 * @param DEP_CODE_TO
	 * @return List
	 */
	public List getBmnInfoTo(String KAI_CODE, String DEP_CODE_TO);

	// �q�d�e����
	/**  */
	public String conditionSearch_ARGS = "kaiCode, depCode, depName_S, depName_K, beginCode, endCode, kind";

	/**
	 * @param kaiCode
	 * @param depCode
	 * @param depName_S
	 * @param depName_K
	 * @param beginCode
	 * @param endCode
	 * @param kind
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String depCode, String depName_S, String depName_K, String beginCode,
		String endCode, String kind);

	// MG0340�u�J�����x���}�X�^�E�ҏW��ʁv�F��ʕ���q�d�e����
	/**  */
	public String getUpperDepartmentsForMG0340_ARGS = "kaiCode,dpkSsk,level,depCode,depName_S,depName_K";

	/**
	 * @param kaiCode
	 * @param dpkSsk
	 * @param level
	 * @param depCode
	 * @param depName_S
	 * @param depName_K
	 * @return List
	 */
	public List getUpperDepartmentsForMG0340(String kaiCode, String dpkSsk, int level, String depCode,
		String depName_S, String depName_K);

	/**  */
	public String getUpperDepartmentForMG0340_ARGS = "kaiCode,dpkSsk,level,depCode";

	/**
	 * @param kaiCode
	 * @param dpkSsk
	 * @param level
	 * @param depCode
	 * @return BMN_MST
	 */
	public BMN_MST getUpperDepartmentForMG0340(String kaiCode, String dpkSsk, int level, String depCode);

	// MG0340�u�J�����x���}�X�^�E�ҏW��ʁv�F����q�d�e����
	/**  */
	public String getDepartmentsForMG0340_ARGS = "kaiCode,dpkSsk,level,parentDepCode,depCode,depName_S,depName_K";

	/**
	 * @param kaiCode
	 * @param dpkSsk
	 * @param level
	 * @param parentDepCode
	 * @param depCode
	 * @param depName_S
	 * @param depName_K
	 * @return List
	 */
	public List getDepartmentsForMG0340(String kaiCode, String dpkSsk, int level, String parentDepCode, String depCode,
		String depName_S, String depName_K);

	/**  */
	public String getDepartmentForMG0340_ARGS = "kaiCode,dpkSsk,level,parentDepCode,depCode";

	/**
	 * @param kaiCode
	 * @param dpkSsk
	 * @param level
	 * @param parentDepCode
	 * @param depCode
	 * @return BMN_MST
	 */
	public BMN_MST getDepartmentForMG0340(String kaiCode, String dpkSsk, int level, String parentDepCode, String depCode);

	/** ����}�X�^���擾 */
	public String searchBmnMstLvlData_ARGS = "kaiCode, depCode, depName, depName_K, dpkSsk, bmnKbn, upBmnCode, dpkLvl,baseDpkLvl,baseBmnCode";

	/**
	 * @param kaiCode
	 * @param depCode
	 * @param depName
	 * @param depName_K
	 * @param dpkSsk
	 * @param bmnKbn
	 * @param upBmnCode
	 * @param dpkLvl
	 * @param baseDpkLvl
	 * @param baseBmnCode
	 * @return List
	 */
	public List searchBmnMstLvlData(String kaiCode, String depCode, String depName, String depName_K, String dpkSsk,
		String bmnKbn, String upBmnCode, String dpkLvl, String baseDpkLvl, String baseBmnCode);

	/** ����}�X�^���� */
	public String searchBmnMstData_ARGS = "kaiCode,depCode,depName,depName_K,slipDate";

	/**
	 * @param kaiCode
	 * @param depCode
	 * @param depName
	 * @param depName_K
	 * @param slipDate
	 * @return List
	 */
	public List searchBmnMstData(String kaiCode, String depCode, String depName, String depName_K, Date slipDate);

	/** �p�����[�^�[ */
	public String getDepNameS_ARGS = "KaiCode, DpkSsk, DepCode, panelLevel, kjlLvl, kjlDepCode";

	/**
	 * ���嗪�̂��擾
	 * 
	 * @param KaiCode
	 * @param DpkSsk
	 * @param DepCode
	 * @param panelLevel
	 * @param kjlLvl
	 * @param kjlDepCode
	 * @return String
	 */
	public String getDepNameS(String KaiCode, String DpkSsk, String DepCode, int panelLevel, int kjlLvl,
		String kjlDepCode);

	/**  */
	public String searchDepDpkData_ARGS = "kaiCode,depCode,sName,kName,termBasisDate,organization,level,upperDepart,sumDepart,inputDepart,beginCode,endCode";

	/**
	 * ���匟��
	 * 
	 * @param kaiCode
	 * @param depCode
	 * @param sName
	 * @param kName
	 * @param termBasisDate
	 * @param organization
	 * @param level
	 * @param upperDepart
	 * @param sumDepart
	 * @param inputDepart
	 * @param beginCode
	 * @param endCode
	 * @return ���僊�X�g
	 */
	public List<Object> searchDepDpkData(String kaiCode, String depCode, String sName, String kName,
		Timestamp termBasisDate, String organization, int level, String upperDepart, boolean sumDepart,
		boolean inputDepart, String beginCode, String endCode);

	/** getBmnMstDataByKaiCodeDepCode���� */
	public String getBmnMstDataByKaiCodeDepCode_QUERY = "KAI_CODE = ? AND DEP_CODE = ? AND DEP_KBN = 0";

	/**
	 * ����}�X�^����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param depCode ����R�[�h
	 * @return ����}�X�^Bean
	 */
	public BMN_MST getBmnMstDataByKaiCodeDepCode(String kaiCode, String depCode);

	/** getBMN_MSTByBeginEnd���� */
	public String getBMN_MSTByBeginEnd_ARGS = "kaiCode, deptCode, beginCode, endCode";

	/**
	 * �J�n�E�I���X�e�[�^�X����ɂ����f�[�^����<br>
	 * �J�n�E�I���R�[�h���w�肳��Ă���ꍇ�A���͈͓̔��ɊY�����Ȃ��ꍇ��null���Ԃ�.
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param deptCode ����R�[�h
	 * @param beginCode �J�n����R�[�h
	 * @param endCode �I������R�[�h
	 * @return ����f�[�^
	 */
	public BMN_MST getBMN_MSTByBeginEnd(String kaiCode, String deptCode, String beginCode, String endCode);
	
	/** ����}�X�^���� */
	public String getDepDpkData_ARGS = "kaiCode,depCode,slipDate";

	/**
	 * @param kaiCode
	 * @param depCode
	 * @param slipDate
	 * @return List
	 */
	public List getDepDpkData(String kaiCode, String depCode, Date slipDate);

}
