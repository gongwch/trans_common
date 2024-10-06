package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �`�[��ʃ}�X�^DAO
 */
public interface DEN_SYU_MSTDao {

	/**  */
	public Class BEAN = DEN_SYU_MST.class;

	/**
	 * @return List
	 */
	public List getAllDEN_SYU_MST();

	/**  */
	public String getDenSyuInfo_QUERY = "KAI_CODE = ? AND DEN_SYU_CODE BETWEEN ? AND ? ORDER BY DEN_SYU_CODE ";

	/**
	 * @param KAI_CODE
	 * @param DEN_SYU_CODE_FROM
	 * @param DEN_SYU_CODE_TO
	 * @return List
	 */
	public List getDenSyuInfo(String KAI_CODE, String DEN_SYU_CODE_FROM, String DEN_SYU_CODE_TO);

	// �w�肳�ꂽ�f�[�^�̎擾
	/**  */
	public String getDEN_SYU_MSTByKaicodeDensyucode_ARGS = "KAI_CODE, DEN_SYU_CODE";

	/**
	 * @param kaiCode
	 * @param densyuCode
	 * @return DEN_SYU_MST
	 */
	public DEN_SYU_MST getDEN_SYU_MSTByKaicodeDensyucode(String kaiCode, String densyuCode);

	/**  */
	public String getDEN_SYU_MSTByKaicodeSyskbnDatakbn_QUERY = " KAI_CODE = ? AND SYS_KBN = ? AND DATA_KBN IN ('13','23','31')";

	/**
	 * @param dto
	 */
	public void insert(DEN_SYU_MST dto);

	/**
	 * @param dto
	 */
	public void update(DEN_SYU_MST dto);

	/**
	 * @param dto
	 */
	public void delete(DEN_SYU_MST dto);

	/**
	 * �V�X�e���敪�ƃf�[�^�敪���w�肵���f�[�^���擾
	 * 
	 * @param strKaiCode
	 * @param strSysKbn
	 * @return List
	 */
	public List getDEN_SYU_MSTByKaicodeSyskbnDatakbn(String strKaiCode, String strSysKbn);

	/** �p�����[�^�[ */
	public String getOtherSystem_ARGS = "KAI_CODE, DEN_SYU_CODE";

	/**
	 * �`�[��ʃ}�X�^����������
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param DenSyuCode �`�[��ʃR�[�h
	 * @return DEN_SYU_MST
	 */
	public DEN_SYU_MST getOtherSystem(String kaiCode, String DenSyuCode);

	// ���L�� ISFnet China �ǉ���

	/**  */
	public String getAllDEN_SYU_MST2_QUERY = "KAI_CODE = ? ORDER BY DEN_SYU_CODE ";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllDEN_SYU_MST2(String kaiCode);

	/**  */
	public String getDenSyuInfoFrom_QUERY = "KAI_CODE = ? AND DEN_SYU_CODE>= ? ORDER BY DEN_SYU_CODE";

	/**
	 * @param kaiCode
	 * @param denSyuCodeFrom
	 * @return List
	 */
	public List getDenSyuInfoFrom(String kaiCode, String denSyuCodeFrom);

	/**  */
	public String getDenSyuInfoTo_QUERY = "KAI_CODE = ? AND DEN_SYU_CODE<= ? ORDER BY DEN_SYU_CODE ";

	/**
	 * @param kaiCode
	 * @param denSyuCodeTo
	 * @return List
	 */
	public List getDenSyuInfoTo(String kaiCode, String denSyuCodeTo);

	// ��������
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,DEN_SYU_CODE,DEN_SYU_NAME_S,DEN_SYU_NAME_K,beginCode,endCode";

	/**
	 * @param kaiCode
	 * @param denSyuCode
	 * @param denSyuName_S
	 * @param denSyuName_K
	 * @param beginCode
	 * @param endCode
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String denSyuCode, String denSyuName_S, String denSyuName_K,
		String beginCode, String endCode);

	/** �`�탊�X�g�������� */
	public String getDenSyuList_ARGS = "kaiCode,isIncludeSystemEls";

	/**
	 * @param kaiCode ��ЃR�[�h
	 * @param isIncludeSystemEls true:���V�X�e�������荞�񂾓`�[��ʂ�ΏۂƂ���
	 * @return ���ʃ��X�g
	 */
	public List<DEN_SYU_MST> getDenSyuList(String kaiCode, boolean isIncludeSystemEls);

	/** �`�[��ʃR�[�h�擾 SQL */
	public String getDataKbnDenSyu_SQL = "SELECT MIN(DEN_SYU_CODE) FROM DEN_SYU_MST WHERE KAI_CODE = ? AND SYS_KBN = ? AND DATA_KBN = ?";

	/**
	 * �f�[�^�敪�ɓ���`�[��ʃR�[�h�擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param sysKbn �V�X�e���敪
	 * @param dataKbn �f�[�^�敪
	 * @return �`�[��ʃR�[�h
	 */
	public String getDataKbnDenSyu(String kaiCode, String sysKbn, String dataKbn);
}
