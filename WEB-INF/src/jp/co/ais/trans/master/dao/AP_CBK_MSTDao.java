package jp.co.ais.trans.master.dao;

import java.sql.Timestamp;
import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ��s�����}�X�^Dao
 */
public interface AP_CBK_MSTDao {

	/** bean */
	public Class BEAN = AP_CBK_MST.class;

	/**
	 * �S�̌���
	 * 
	 * @return ��s�������X�g
	 */
	public List getAllAP_CBK_MST();

	/** �p�����[�^ */
	public String getApCbkInfo_QUERY = "KAI_CODE = ? AND CBK_CBK_CODE BETWEEN ? AND ? ORDER BY CBK_CBK_CODE ";

	/**
	 * @param KAI_CODE
	 * @param CBK_CBK_CODE_FROM
	 * @param CBK_CBK_CODE_TO
	 * @return ��s����
	 */
	public List getApCbkInfo(String KAI_CODE, String CBK_CBK_CODE_FROM, String CBK_CBK_CODE_TO);

	// �w�肳�ꂽ�f�[�^�̎擾
	/** �p�����[�^ */
	public String getAP_CBK_MSTByKaicodecbkcbkcode_ARGS = "KAI_CODE, CBK_CBK_CODE";

	/**
	 * ��L�[�w��
	 * 
	 * @param kaiCode
	 * @param cbkcbkCode
	 * @return ��s����
	 */
	public AP_CBK_MST getAP_CBK_MSTByKaicodecbkcbkcode(String kaiCode, String cbkcbkCode);

	/** �p�����[�^ */
	public String getAP_CBK_MSTByKaicodecbkcbkcodeoutfbkbn_QUERY = "KAI_CODE = ? AND CBK_CBK_CODE = ? AND CBK_OUT_FB_KBN = 1";

	/**
	 * �ЊO�敪������s�����擾
	 * 
	 * @param kaiCode
	 * @param cbkcbkCode
	 * @return ��s����
	 */
	public AP_CBK_MST getAP_CBK_MSTByKaicodecbkcbkcodeoutfbkbn(String kaiCode, String cbkcbkCode);

	/** �p�����[�^ */
	public String getAP_CBK_MSTfbInfo_ARGS = "kaiCode, cbkcbkCode, fbKbn";

	/**
	 * �ЊO�敪�w��
	 * 
	 * @param kaiCode
	 * @param cbkcbkCode
	 * @param fbKbn
	 * @return ��s����
	 */
	public AP_CBK_MST getAP_CBK_MSTfbInfo(String kaiCode, String cbkcbkCode, String fbKbn);

	/**
	 * �o�^
	 * 
	 * @param dto
	 */
	public void insert(AP_CBK_MST dto);

	/**
	 * �X�V
	 * 
	 * @param dto
	 */
	public void update(AP_CBK_MST dto);

	/**
	 * �폜
	 * 
	 * @param dto
	 */
	public void delete(AP_CBK_MST dto);

	// ���L�� ISFnet China �ǉ���

	/** �p�����[�^ */
	public String getAllAP_CBK_MST2_QUERY = "KAI_CODE = ? ORDER BY CBK_CBK_CODE";

	/**
	 * ���ׂẴ��R�[�h���擾
	 * 
	 * @param kaiCode
	 * @return ��s����
	 */
	public List getAllAP_CBK_MST2(String kaiCode);

	/** �p�����[�^ */
	public String getApCbkInfoFrom_QUERY = "KAI_CODE = ? AND CBK_CBK_CODE >= ? ORDER BY CBK_CBK_CODE ";

	/**
	 * ��Ԃ̃f�[�^���擾
	 * 
	 * @param kaiCode
	 * @param cbkCbkCodeFrom
	 * @return ��s����
	 */
	public List getApCbkInfoFrom(String kaiCode, String cbkCbkCodeFrom);

	/** �p�����[�^ */
	public String getApCbkInfoTo_QUERY = "KAI_CODE = ? AND CBK_CBK_CODE <= ? ORDER BY CBK_CBK_CODE ";

	/**
	 * ��Ԃ̃f�[�^���擾
	 * 
	 * @param kaiCode
	 * @param cbkCbkCodeTo
	 * @return ��s����
	 */
	public List getApCbkInfoTo(String kaiCode, String cbkCbkCodeTo);

	/** �p�����[�^ */
	public String conditionSearch_ARGS = "kaiCode,cbkCode,bnkName,cbkYknNo,beginCode,endCode,word1,word2,word3,word4,wordUnknown, tablespace";

	/**
	 * �q�d�e����
	 * 
	 * @param kaiCode
	 * @param cbkCode
	 * @param bnkName
	 * @param cbkYknNo
	 * @param beginCode
	 * @param endCode
	 * @param word1
	 * @param word2
	 * @param word3
	 * @param word4
	 * @param wordUnknown
	 * @param tablespace
	 * @return ��s����
	 */
	public List conditionSearch(String kaiCode, String cbkCode, String bnkName, String cbkYknNo, String beginCode,
		String endCode, String word1, String word2, String word3, String word4, String wordUnknown, String tablespace);

	/** �p�����[�^ */
	public String searchApCbkMstData_ARGS = "kaiCode, code, nameS, nameK,outKbn";

	/**
	 * �ЊO�U�o��s�����ꗗ���擾
	 * 
	 * @param strKaiCode
	 * @param strBnkCode
	 * @param strBnkName
	 * @param strYnkNo
	 * @param outKbn
	 * @return ��s����
	 */
	public List searchApCbkMstData(String strKaiCode, String strBnkCode, String strBnkName, String strYnkNo, int outKbn);

	/** �p�����[�^ */
	public String getCbkName_ARGS = "kaiCode,cbkCode,cbkKbn,tablespace";

	/**
	 * ��s���A�����ԍ�������擾
	 * 
	 * @param kaiCode
	 * @param cbkCode
	 * @param cbkKbn
	 * @param tablespace
	 * @return ��s����
	 */
	public AP_CBK_MST getCbkName(String kaiCode, String cbkCode, String cbkKbn, String tablespace);

	// ���ʋ�s�����R���|�[�i���g�pSQL
	/** �p�����[�^ */
	public String searchCommonCbkMstData_ARGS = "kaiCode, code, nameS, nameK,outKbn, empKbn,termBasisDate";

	/**
	 * ��s��������
	 * 
	 * @param strKaiCode
	 * @param strBnkCode
	 * @param strBnkName
	 * @param strYnkNo
	 * @param outKbn
	 * @param empKbn
	 * @param termBasisDate
	 * @return ��s�������c
	 */
	public List searchCommonCbkMstData(String strKaiCode, String strBnkCode, String strBnkName, String strYnkNo,
		boolean outKbn, boolean empKbn, Timestamp termBasisDate);

	/** �p�����[�^ */
	public String getDefaultReceivedAccount_ARGS = "kaiCode, cbkCode";

	/**
	 * �f�t�H���g�̎x�������擾
	 * 
	 * @param kaiCode
	 * @param cbkCode
	 * @return ��s����
	 */
	public AP_CBK_MST getDefaultReceivedAccount(String kaiCode, String cbkCode);

	/** �p�����[�^ */
	public String getApCbkMst_ARGS = "param";

	/**
	 * �p�����[�^�Ńf�[�^���擾����
	 * 
	 * @param param ��s�����p�����[�^
	 * @return ���X�g<��s�����}�X�^>
	 */
	public List getApCbkMst(ApCbkMstParameter param);
	
	/** �p�����[�^ */
	public String getCbkMstData_ARGS = "kaiCode, cbkCode, slipDate";

	/**
	 * ��s��������
	 * @param kaiCode 
	 * @param cbkCode 
	 * @param slipDate 
	 * 
	 * @return ���X�g ��s�����}�X�^
	 */
	public List getCbkMstData(String kaiCode, String cbkCode, Date slipDate);

}
