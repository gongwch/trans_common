package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �d��W���[�i��Dao
 */
public interface SWK_DTLDao {

	/** Entity */
	public Class BEAN = SWK_DTL.class;

	/**
	 * �S���f�[�^�擾
	 * 
	 * @return �f�[�^���X�g
	 */
	public List getAllSWK_DTL();

	/** getSwkDtlInfo QUERY */
	public String getSwkDtlInfo_QUERY = "KAI_CODE = ? AND SWK_DEN_DATE BETWEEN ? AND ? AND SWK_DEN_NO BETWEEN ? AND ? ORDER BY SWK_DEN_DATE, SWK_DEN_NO, SWK_GYO_NO ";

	/**
	 * @param KAI_CODE
	 * @param SWK_DEN_DATE_FROM
	 * @param SWK_DEN_DATE_TO
	 * @param SWK_DEN_NO_FROM
	 * @param SWK_DEN_NO_TO
	 * @return �f�[�^���X�g
	 */
	public List getSwkDtlInfo(String KAI_CODE, Date SWK_DEN_DATE_FROM, Date SWK_DEN_DATE_TO, String SWK_DEN_NO_FROM,
		String SWK_DEN_NO_TO);

	/** getSwkDtlInfo QUERY */
	public String getSwkListByKaiCodeAndDenNo_QUERY = "KAI_CODE = ?  AND SWK_DEN_NO = ? ORDER BY SWK_GYO_NO ";

	/**
	 * �w��̓`�[�ԍ��ɓ�����S�Ă̎d����擾
	 * 
	 * @param KAI_CODE
	 * @param SWK_DEN_NO
	 * @return List<SWK_DTL>
	 */
	public List<SWK_DTL> getSwkListByKaiCodeAndDenNo(String KAI_CODE, String SWK_DEN_NO);

	/** getSWK_DTLByKaicodeSwkdendateSwkdennoSwkGyono���� */
	public String getSWK_DTLByKaicodeSwkdendateSwkdennoSwkGyono_ARGS = "KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO, SWK_GYO_NO";

	/**
	 * @param kaiCode
	 * @param swkdenDATE
	 * @param swkdenNO
	 * @param swkgyoNO
	 * @return �f�[�^���X�g
	 */
	public List<Object> getSWK_DTLByKaicodeSwkdendateSwkdennoSwkGyono(String kaiCode, Date swkdenDATE, String swkdenNO,
		String swkgyoNO);

	/** getSWK_DTLByKaicodeSwkdennoSwkGyono���� */
	public String getSWK_DTLByKaicodeSwkdennoSwkGyono_ARGS = "KAI_CODE, SWK_DEN_NO, SWK_GYO_NO";

	/**
	 * @param kaiCode
	 * @param swkdenNO
	 * @param swkgyoNO
	 * @return �f�[�^
	 */
	public SWK_DTL getSWK_DTLByKaicodeSwkdennoSwkGyono(String kaiCode, String swkdenNO, String swkgyoNO);

	/** getSwkDtlByKeta���� */
	public String getSwkDtlByKeta_ARGS = "KAI_CODE, SWK_DEN_NO, SWK_DC_KBN";

	/**
	 * ����
	 * 
	 * @param kaiCode
	 * @param swkdenNO
	 * @param dcKbn
	 * @return ����
	 */
	public int getSwkDtlByKeta(String kaiCode, String swkdenNO, int dcKbn);

	/** getSwkDtlByKaicodeSwkden���� */
	public String getSwkDtlByKaicodeSwkden_ARGS = "KAI_CODE, SWK_DEN_NO, SWK_DC_KBN";

	/**
	 * �f�[�^�擾
	 * 
	 * @param kaiCode
	 * @param swkdenNO
	 * @param dcKbn
	 * @return �f�[�^
	 */
	public SWK_DTL getSwkDtlByKaicodeSwkden(String kaiCode, String swkdenNO, int dcKbn);

	/** getAllTukeCompany(2:�t�֐掩���d��s) */
	public String getAllTukeCompany_SQL = "SELECT KAI_CODE FROM SWK_DTL WHERE SWK_DEN_NO = ? AND SWK_TUKE_KBN IN (1, 2) GROUP BY KAI_CODE";

	/**
	 * �t�֐��ЃR�[�h���擾����
	 * 
	 * @param slipNo �`�[�ԍ�
	 * @return �t�֐��ЃR�[�h���X�g
	 */
	public List<SWK_DTL> getAllTukeCompany(String slipNo);

	/** getTukeOriginalCompany */
	public String getTukeOriginalCompany_SQL = "SELECT KAI_CODE FROM SWK_DTL WHERE SWK_DEN_NO = ? AND SWK_TUKE_KBN = 0";

	/**
	 * �w��`�[�ԍ��̕t�֌���ЃR�[�h���擾����
	 * 
	 * @param slipNo �`�[�ԍ�
	 * @return �t�֐��ЃR�[�h
	 */
	public String getTukeOriginalCompany(String slipNo);

	/**
	 * �o�^
	 * 
	 * @param dto
	 */
	public void insert(SWK_DTL dto);

	/**
	 * �X�V
	 * 
	 * @param dto
	 */
	public void update(SWK_DTL dto);

	/**
	 * �폜
	 * 
	 * @param dto
	 */
	public void delete(SWK_DTL dto);

	/** updateForSeiDenNoDenDate���� */
	public String updateForSeiDenNoDenDate_ARGS = "kaiCode, denNo, gyoNo, seiDenNo, denDate,prgId,usrId,updDate";

	/**
	 * @param kaiCode
	 * @param denNo
	 * @param gyoNo
	 * @param seiDenNo
	 * @param denDate
	 * @param prgId
	 * @param usrId
	 * @param updDate
	 */
	public void updateForSeiDenNoDenDate(String kaiCode, String denNo, String gyoNo, String seiDenNo, Date denDate,
		String prgId, String usrId, Date updDate);

	/** updateForSeiDenNo���� */
	public String updateForSeiDenNo_ARGS = "kaiCode, seiDenNo, gyoNo, denDate, prgId, usrId, updDate";

	/**
	 * @param kaiCode
	 * @param seiDenNo
	 * @param gyoNo
	 * @param denDate
	 * @param prgId
	 * @param usrId
	 */
	public void updateForSeiDenNo(String kaiCode, String seiDenNo, String gyoNo, Date denDate, String prgId,
		String usrId);

	/** updateClearanceData���� */
	public String updateClearanceData_ARGS = "kaiCode, denNo, date, usrId,prgId";

	/**
	 * @param kaiCode
	 * @param denNo
	 * @param date
	 * @param usrId
	 * @param prgId
	 */
	public void updateClearanceData(String kaiCode, String denNo, Date date, String usrId, String prgId);

	/** updateSwkDtlByCoItem���� */
	public String updateSwkDtlByCoItem_ARGS = "KAI_CODE, SWK_DEN_NO, SWK_DC_KBN, SWK_KMK_CODE, SWK_HKM_CODE, SWK_UKM_CODE, SWK_DEP_CODE";

	/**
	 * ����Ȗڂ��X�V����
	 * 
	 * @param kaiCode
	 * @param denNo
	 * @param dcKbn
	 * @param strKmkCode
	 * @param strHkmCode
	 * @param strUkmCode
	 * @param strDepCode
	 */
	public void updateSwkDtlByCoItem(String kaiCode, String denNo, int dcKbn, String strKmkCode, String strHkmCode,
		String strUkmCode, String strDepCode);

	/** updateUPD_KBN SQL */
	public static final String updateUpdKbn_SQL = "UPDATE SWK_DTL SET SWK_UPD_KBN = ?, USR_ID = ?, PRG_ID = ? WHERE SWK_DEN_NO = ?   AND SWK_UPD_KBN <> '4'";

	/**
	 * �X�V�敪�ύX.<br>
	 * �����͓`�[�ԍ��̂�
	 * 
	 * @param swkUpdKbn �X�V�敪
	 * @param userID ���[�UID
	 * @param prgID �v���O����ID
	 * @param swkDenNo �`�[�ԍ�
	 */
	@Deprecated
	public void updateUpdKbn(String swkUpdKbn, String userID, String prgID, String swkDenNo);

	/** updateUPD_KBN SQL */
	public static final String updateKbnDateId_SQL = "UPDATE SWK_DTL SET SWK_UPD_KBN = ?, USR_ID = ?, PRG_ID = ?, UPD_DATE = ? WHERE SWK_DEN_NO = ?   AND SWK_UPD_KBN <> '4'";

	/**
	 * �X�V�敪�ύX.<br>
	 * �����͓`�[�ԍ��̂�
	 * 
	 * @param swkUpdKbn �X�V�敪
	 * @param userID ���[�UID
	 * @param prgID �v���O����ID
	 * @param updDate �X�V���t
	 * @param swkDenNo �`�[�ԍ�
	 */
	public void updateKbnDateId(String swkUpdKbn, String userID, String prgID, Date updDate, String swkDenNo);

	/** deleteByKaicodeDenno SQL */
	public static final String deleteByKaicodeDenno_QUERY = "KAI_CODE = ? AND SWK_DEN_NO = ?";

	/**
	 * �w�肳�ꂽKey�ɕR�Â��f�[�^��S�č폜����
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param slipNo �`�[�ԍ�
	 */
	public void deleteByKaicodeDenno(String companyCode, String slipNo);

	/**
	 * �w��̓`�[�ԍ����擾
	 */
	public String getSWK_DTLByKaicodeSwkdennoSwkdendate_QUERY = "KAI_CODE = ? AND SWK_DEN_NO = ? AND SWK_DEN_DATE = ? ";

	/**
	 * �w��̓`�[�ԍ����擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param denNo �`�[�ԍ� *
	 * @param denDate �`�[���t
	 * @return SWK_DTL���X�^
	 */
	public List<SWK_DTL> getSWK_DTLByKaicodeSwkdennoSwkdendate(String kaiCode, String denNo, Date denDate);

	/**
	 * �d��W���[�i�����X�V(AP�Ј��x��)
	 */
	public String updateDetail_ARGS = "bean";

	/**
	 * �d��W���[�i�����X�V(AP�Ј��x��)
	 * 
	 * @param bean �d��W���[�i��
	 */
	public void updateDetail(SWK_DTL bean);

	/** SQL */
	public String getCountByUpdateDivision_ARGS = "slipNo, kbn";

	/** SQL */
	public String getCountByUpdateDivision_SQL = "SELECT COUNT(*) FROM SWK_DTL WHERE SWK_DEN_NO = /*slipNo*/ AND SWK_UPD_KBN IN /*kbn*/(999)";

	/**
	 * �w��X�V�敪��Ԃɂ���d��̌�����Ԃ�.(��Ќׂ�)
	 * 
	 * @param slipNo �`�[�ԍ�
	 * @param kbn �ΏۍX�V�敪
	 * @return ����
	 */
	public int getCountByUpdateDivision(String slipNo, int... kbn);

	/** SQL */
	public String deleteSwkDtlByDenNo_ARGS = "kaiCode, swkDenNo";

	/**
	 * �s�v�Ȏd��f�[�^���폜����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param swkDenNo �`�[�ԍ�
	 * @return �폜�s
	 */
	public int deleteSwkDtlByDenNo(String kaiCode, String swkDenNo);
}
