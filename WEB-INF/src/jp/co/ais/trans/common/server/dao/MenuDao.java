package jp.co.ais.trans.common.server.dao;

import java.util.*;

/**
 * メニューデータ取得用Dao
 */
public interface MenuDao {

	/** 対象Bean */
	public Class BEAN = MenuBean.class;

	/** ARGS */
	public static final String getPRG_MSTByKaicodeSyscodePrcken_ARGS = "kaiCode, sysCode, prcKen, targetDate, langPrefix, userCode";

	/**
	 * データ取得（指定）
	 * 
	 * @param kaiCode 会社コード
	 * @param sysCode システムコード
	 * @param prcKen プログラム区分
	 * @param targetDate 日付
	 * @param langPrefix 言語プレフィックス
	 * @param userCode ユーザーコード
	 * @return メニューデータ
	 */
	public List getPRG_MSTByKaicodeSyscodePrcken(String kaiCode, String sysCode, int prcKen, Date targetDate,
		String langPrefix, String userCode);

	/** ARGS */
	public static final String getPRG_MSTByKaicodeSyscodePrcken2_ARGS = "kaiCode, sysCode, prcKen, targetDate, langPrefix, userCode";

	/**
	 * データ取得（指定） 現場承認メニュー除外
	 * 
	 * @param kaiCode 会社コード
	 * @param sysCode システムコード
	 * @param prcKen プログラム区分
	 * @param targetDate 日付
	 * @param langPrefix 言語プレフィックス
	 * @param userCode ユーザーコード
	 * @return メニューデータ
	 */
	public List getPRG_MSTByKaicodeSyscodePrcken2(String kaiCode, String sysCode, int prcKen, Date targetDate,
		String langPrefix, String userCode);

	/** getCount() SQL */
	public static final String getCount_SQL = "SELECT count(*) FROM PRG_MST/*$langPrefix*/";

	/**
	 * データカウント
	 * 
	 * @param langPrefix 言語コード
	 * @return データ数
	 */
	public int getCount(String langPrefix);
}
