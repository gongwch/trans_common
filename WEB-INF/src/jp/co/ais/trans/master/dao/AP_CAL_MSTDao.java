package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * カレンダーマスタDao
 */
public interface AP_CAL_MSTDao {

	/** BEAN */
	public Class BEAN = AP_CAL_MST.class;

	/**
	 * @return List
	 */
	public List getAllAP_CAL_MST();

	/**  */
	public String getApCalInfo_QUERY = "KAI_CODE = ? AND CAL_DATE BETWEEN ? AND ? ORDER BY CAL_DATE ";

	/**
	 * @param KAI_CODE
	 * @param CAL_DATE_FROM
	 * @param CAL_DATE_TO
	 * @return List
	 */
	public List getApCalInfo(String KAI_CODE, Date CAL_DATE_FROM, Date CAL_DATE_TO);

	/**  */
	public String getApCalBankInfo_QUERY = "KAI_CODE = ? AND CAL_DATE > ? AND CAL_SHA = ? AND CAL_BNK_KBN = ? ORDER BY CAL_DATE ";

	/**
	 * @param KAI_CODE
	 * @param CAL_DATE
	 * @param CAL_SHA
	 * @param CAL_BNK_KBN
	 * @return List
	 */
	public List getApCalBankInfo(String KAI_CODE, Date CAL_DATE, int CAL_SHA, int CAL_BNK_KBN);

	/** 指定されたデータの取得 */
	public String getAP_CAL_MSTByKaicodeCalcode_ARGS = "KAI_CODE, CAL_DATE";

	/**
	 * @param kaiCode
	 * @param calDATE
	 * @return AP_CAL_MST
	 */
	public AP_CAL_MST getAP_CAL_MSTByKaicodeCalcode(String kaiCode, Date calDATE);

	/**
	 * @param dto
	 */
	public void insert(AP_CAL_MST dto);

	/**
	 * @param dto
	 */
	public void update(AP_CAL_MST dto);

	/**
	 * @param dto
	 */
	public void delete(AP_CAL_MST dto);

	// 下記は ISFnet China 追加分

	/** 区間のデータを取得 */
	public String getCalendarInfo_QUERY = "KAI_CODE = ? AND CAL_DATE BETWEEN ? AND ? ORDER BY CAL_DATE ";

	/**
	 * @param kaiCode
	 * @param calDateFrom
	 * @param calDateTo
	 * @return List
	 */
	public List getCalendarInfo(String kaiCode, Date calDateFrom, Date calDateTo);

	/** 指定されたデータを取得 */
	public String getAP_CAL_MST_ARGS = "KAI_CODE, CAL_DATE";

	/**
	 * @param kaiCode
	 * @param calDate
	 * @return AP_CAL_MST
	 */
	public AP_CAL_MST getAP_CAL_MST(String kaiCode, Date calDate);

	/** getNearBusinessDayInfo引数 */
	public String getNearBusinessDayInfo_ARGS = "kaiCode, calDate";

	/**
	 * 対象日から未来の最も直近の銀行営業日を取得する。
	 * 
	 * @param kaiCode 会社コード
	 * @param calDate 対象日
	 * @return 銀行営業日
	 */
	public AP_CAL_MST getNearBusinessDayInfo(String kaiCode, Date calDate);

	/** getNearPreBusinessDayInfo引数 */
	public String getNearPreBusinessDayInfo_ARGS = "kaiCode, calDate";

	/**
	 * 対象日から過去の最も直近の銀行営業日を取得する。
	 * 
	 * @param kaiCode 会社コード
	 * @param calDate 対象日
	 * @return 銀行営業日
	 */
	public AP_CAL_MST getNearPreBusinessDayInfo(String kaiCode, Date calDate);

	// 
	/** getNearTempPayDayInfo引数 */
	public String getNearTempPayDayInfo_ARGS = "kaiCode, calDate";

	/**
	 * 対象日から未来の最も直近の臨時支払対象日を取得する。
	 * 
	 * @param kaiCode 会社コード
	 * @param calDate 対象日
	 * @return 臨時支払対象日
	 */
	public AP_CAL_MST getNearTempPayDayInfo(String kaiCode, Date calDate);

	/** getNearPreTempPayDayInfo引数 */
	public String getNearPreTempPayDayInfo_ARGS = "kaiCode, calDate";

	/**
	 * 対象日から過去の最も直近の臨時支払対象日を取得する。
	 * 
	 * @param kaiCode 会社コード
	 * @param calDate 対象日
	 * @return 臨時支払対象日
	 */
	public AP_CAL_MST getNearPreTempPayDayInfo(String kaiCode, Date calDate);

	// 
	/** getNearEmpPayDayInfo引数 */
	public String getNearEmpPayDayInfo_ARGS = "kaiCode, calDate";

	/**
	 * 対象日から未来の最も直近の社員支払対象日を取得する。
	 * 
	 * @param kaiCode 会社コード
	 * @param calDate 対象日
	 * @return 社員支払対象日
	 */
	public AP_CAL_MST getNearEmpPayDayInfo(String kaiCode, Date calDate);

	/** getNearPreEmpPayDayInfo引数 */
	public String getNearPreEmpPayDayInfo_ARGS = "kaiCode, calDate";

	/**
	 * 対象日から過去の最も直近の社員支払対象日を取得する。
	 * 
	 * @param kaiCode 会社コード
	 * @param calDate 対象日
	 * @return 社員支払対象日
	 */
	public AP_CAL_MST getNearPreEmpPayDayInfo(String kaiCode, Date calDate);

	/** 社員支払対象日 */
	public String getAPEmpPayDayCount_SQL = "SELECT COUNT(*) FROM AP_CAL_MST WHERE  KAI_CODE = ? AND CAL_DATE = ? AND CAL_HARAI = 1 ";

	/**
	 * 社員支払対象日(社員支払対象日区分=1)を取得する。
	 * 
	 * @param kaiCode 会社コード
	 * @param calDate 対象日
	 * @return 社員支払対象日が設定される場合：1、以外：0
	 */
	public int getAPEmpPayDayCount(String kaiCode, Date calDate);

}
