package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �Ǘ��c
 */
public interface KRZ_ZANDao {

	/** Entity��` */
	public static final Class BEAN = KRZ_ZAN.class;

	/**
	 * �S�f�[�^�擾
	 * 
	 * @return �f�[�^���X�g
	 */
	public List getAllKRZ_ZAN();

	/** getKnrZanInfo Query */
	public static final String getKnrZanInfo_QUERY = "KAI_CODE = ? AND KRZ_NENDO = ? AND KRZ_DEP_CODE = ? AND KMK_CODE BETWEEN ? AND ? ORDER BY KMK_CODE ";

	/**
	 * ���������Ƀf�[�^���擾����
	 * 
	 * @param KAI_CODE ��ЃR�[�h
	 * @param KRZ_NENDO �N�x
	 * @param KRZ_DEP_CODE ����R�[�h
	 * @param KMK_CODE_FROM �R�[�h�J�n
	 * @param KMK_CODE_TO �R�[�h�I��
	 * @return �f�[�^
	 */
	public KRZ_ZAN getKnrZanInfo(String KAI_CODE, int KRZ_NENDO, String KRZ_DEP_CODE, String KMK_CODE_FROM,
		String KMK_CODE_TO);

	/**
	 * �w�肳�ꂽ�f�[�^�̎擾
	 */
	public String getKrzZanInfoByBG_ARGS = "kaiCode, nenDo, strKmkCode, endKmkCode, strDepCode, endDepCode";

	/**
	 * �͈͎Q��
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param nenDo �N�x
	 * @param strKmkCode �ȖڊJ�n�R�[�h
	 * @param endKmkCode �ȖڏI���R�[�h
	 * @param strDepCode ����J�n�R�[�h
	 * @param endDepCode ����I���R�[�h
	 * @return �Ǘ��c��Bean���X�g
	 */
	public List<KRZ_ZAN> getKrzZanInfoByBG(String kaiCode, int nenDo, String strKmkCode, String endKmkCode,
		String strDepCode, String endDepCode);
}
