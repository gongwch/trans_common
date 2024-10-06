package jp.co.ais.trans.master.logic;

import java.util.*;

/**
 * 
 */
public interface ManagementLogic {

	/**
	 * 管理情報取得<BR>
	 * 管理情報取得。
	 * 
	 * @param conditonMap パラメーターMap
	 * @return triName 管理情報
	 */
	public Map getKnrName(Map conditonMap);

	/**
	 * 管理1マスタ検索
	 * 
	 * @param strKaiCode 会社コー
	 * @param strKnrCode 社員コード
	 * @param strKnrName 社員略称
	 * @param strKnrName_K 社員検索名
	 * @param strSlipDate 伝票日付
	 * @param args その他パラメータ
	 * @return 管理1マスタリスト
	 */
	public List searchKnr1MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args);

	/**
	 * 管理2マスタ検索
	 * 
	 * @param strKaiCode 会社コー
	 * @param strKnrCode 社員コード
	 * @param strKnrName 社員略称
	 * @param strKnrName_K 社員検索名
	 * @param strSlipDate 伝票日付
	 * @param args その他パラメータ
	 * @return 管理2マスタリスト
	 */
	public List searchKnr2MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args);

	/**
	 * 管理3マスタ検索
	 * 
	 * @param strKaiCode 会社コー
	 * @param strKnrCode 社員コード
	 * @param strKnrName 社員略称
	 * @param strKnrName_K 社員検索名
	 * @param strSlipDate 伝票日付
	 * @param args その他パラメータ
	 * @return 管理3マスタリスト
	 */
	public List searchKnr3MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args);

	/**
	 * 管理4マスタ検索
	 * 
	 * @param strKaiCode 会社コー
	 * @param strKnrCode 社員コード
	 * @param strKnrName 社員略称
	 * @param strKnrName_K 社員検索名
	 * @param strSlipDate 伝票日付
	 * @param args その他パラメータ
	 * @return 管理4マスタリスト
	 */
	public List searchKnr4MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args);

	/**
	 * 管理5マスタ検索
	 * 
	 * @param strKaiCode 会社コー
	 * @param strKnrCode 社員コード
	 * @param strKnrName 社員略称
	 * @param strKnrName_K 社員検索名
	 * @param strSlipDate 伝票日付
	 * @param args その他パラメータ
	 * @return 管理5マスタリスト
	 */
	public List searchKnr5MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args);

	/**
	 * 管理6マスタ検索
	 * 
	 * @param strKaiCode 会社コー
	 * @param strKnrCode 社員コード
	 * @param strKnrName 社員略称
	 * @param strKnrName_K 社員検索名
	 * @param strSlipDate 伝票日付
	 * @param args その他パラメータ
	 * @return 管理6マスタリスト
	 */
	public List searchKnr6MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args);

}
