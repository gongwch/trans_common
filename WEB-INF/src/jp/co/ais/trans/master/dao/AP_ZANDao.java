package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 債権残高Dao
 */
public interface AP_ZANDao {

	public Class BEAN = AP_ZAN.class;

	public List getAllAP_ZAN();

	// 指定されたデータの検索
	public static final String getByKaicodeZansihadenno_QUERY = "KAI_CODE = ? AND ZAN_DEN_NO = ?";

	public List getByKaicodeZansihadenno(String kaiCode, String zandenNO);

	// 指定されたデータの取得
	public static final String getAP_ZANByKaicodeZandennoZangyono_QUERY = "KAI_CODE = ? AND ZAN_DEN_NO = ? AND ZAN_GYO_NO = ?";

	public AP_ZAN getAP_ZANByKaicodeZandennoZangyono(String kaiCode, String zandenNO, int zangyoNO);

	public void insert(AP_ZAN dto);

	public void update(AP_ZAN dto);

	public void delete(AP_ZAN dto);

	// 指定されたデータの削除
	public static final String deleteByKaicodeZandenno_SQL = "DELETE FROM AP_ZAN WHERE KAI_CODE = ? AND ZAN_DEN_NO = ?";

	public void deleteByKaicodeZandenno(String kaiCode, String zanDenNo);

	// 指定されたデータの削除
	public static final String deleteForPaymentMethodModification_SQL = "DELETE FROM AP_ZAN WHERE KAI_CODE = ? AND ZAN_DEN_NO = ? AND ZAN_KESAI_KBN = 0 AND ZAN_DATA_KBN IN ('23', '2T')";

	public void deleteForPaymentMethodModification(String kaiCode, String zanDenNo);

	// 指定されたデータの削除
	public static final String deleteByPayeeDateForPaymentMethodModification_SQL = "DELETE FROM AP_ZAN WHERE KAI_CODE = ? AND ZAN_DEN_NO = ? AND ZAN_SIHA_DATE = ? AND ZAN_KESAI_KBN = 0 AND ZAN_DATA_KBN IN ('23', '2T')";

	public void deleteByPayeeDateForPaymentMethodModification(String kaiCode, String zanDenNo, Date shihaDate);
}
