package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ���c��Dao
 */
public interface AP_ZANDao {

	public Class BEAN = AP_ZAN.class;

	public List getAllAP_ZAN();

	// �w�肳�ꂽ�f�[�^�̌���
	public static final String getByKaicodeZansihadenno_QUERY = "KAI_CODE = ? AND ZAN_DEN_NO = ?";

	public List getByKaicodeZansihadenno(String kaiCode, String zandenNO);

	// �w�肳�ꂽ�f�[�^�̎擾
	public static final String getAP_ZANByKaicodeZandennoZangyono_QUERY = "KAI_CODE = ? AND ZAN_DEN_NO = ? AND ZAN_GYO_NO = ?";

	public AP_ZAN getAP_ZANByKaicodeZandennoZangyono(String kaiCode, String zandenNO, int zangyoNO);

	public void insert(AP_ZAN dto);

	public void update(AP_ZAN dto);

	public void delete(AP_ZAN dto);

	// �w�肳�ꂽ�f�[�^�̍폜
	public static final String deleteByKaicodeZandenno_SQL = "DELETE FROM AP_ZAN WHERE KAI_CODE = ? AND ZAN_DEN_NO = ?";

	public void deleteByKaicodeZandenno(String kaiCode, String zanDenNo);

	// �w�肳�ꂽ�f�[�^�̍폜
	public static final String deleteForPaymentMethodModification_SQL = "DELETE FROM AP_ZAN WHERE KAI_CODE = ? AND ZAN_DEN_NO = ? AND ZAN_KESAI_KBN = 0 AND ZAN_DATA_KBN IN ('23', '2T')";

	public void deleteForPaymentMethodModification(String kaiCode, String zanDenNo);

	// �w�肳�ꂽ�f�[�^�̍폜
	public static final String deleteByPayeeDateForPaymentMethodModification_SQL = "DELETE FROM AP_ZAN WHERE KAI_CODE = ? AND ZAN_DEN_NO = ? AND ZAN_SIHA_DATE = ? AND ZAN_KESAI_KBN = 0 AND ZAN_DATA_KBN IN ('23', '2T')";

	public void deleteByPayeeDateForPaymentMethodModification(String kaiCode, String zanDenNo, Date shihaDate);
}
