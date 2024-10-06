package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 管理残
 */
public interface KRZ_ZANDao {

	/** Entity定義 */
	public static final Class BEAN = KRZ_ZAN.class;

	/**
	 * 全データ取得
	 * 
	 * @return データリスト
	 */
	public List getAllKRZ_ZAN();

	/** getKnrZanInfo Query */
	public static final String getKnrZanInfo_QUERY = "KAI_CODE = ? AND KRZ_NENDO = ? AND KRZ_DEP_CODE = ? AND KMK_CODE BETWEEN ? AND ? ORDER BY KMK_CODE ";

	/**
	 * 条件を元にデータを取得する
	 * 
	 * @param KAI_CODE 会社コード
	 * @param KRZ_NENDO 年度
	 * @param KRZ_DEP_CODE 部門コード
	 * @param KMK_CODE_FROM コード開始
	 * @param KMK_CODE_TO コード終了
	 * @return データ
	 */
	public KRZ_ZAN getKnrZanInfo(String KAI_CODE, int KRZ_NENDO, String KRZ_DEP_CODE, String KMK_CODE_FROM,
		String KMK_CODE_TO);

	/**
	 * 指定されたデータの取得
	 */
	public String getKrzZanInfoByBG_ARGS = "kaiCode, nenDo, strKmkCode, endKmkCode, strDepCode, endDepCode";

	/**
	 * 範囲参照
	 * 
	 * @param kaiCode 会社コード
	 * @param nenDo 年度
	 * @param strKmkCode 科目開始コード
	 * @param endKmkCode 科目終了コード
	 * @param strDepCode 部門開始コード
	 * @param endDepCode 部門終了コード
	 * @return 管理残高Beanリスト
	 */
	public List<KRZ_ZAN> getKrzZanInfoByBG(String kaiCode, int nenDo, String strKmkCode, String endKmkCode,
		String strDepCode, String endDepCode);
}
