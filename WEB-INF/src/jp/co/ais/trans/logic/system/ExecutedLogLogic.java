package jp.co.ais.trans.logic.system;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 実行ログ一覧ビジネスロジック
 * 
 * @author roh
 */
public interface ExecutedLogLogic {

	/**
	 * 会社コード設定
	 * 
	 * @param companyCode 会社コード
	 */
	void setCompanyCode(String companyCode);

	/**
	 * 開始日付設定
	 * 
	 * @param startDate 開始日付
	 */
	void setStartDate(Date startDate);

	/**
	 * 終了日付設定
	 * 
	 * @param endDate 終了日付
	 */
	void setEndDate(Date endDate);

	/**
	 * 開始プログラムコード設定
	 * 
	 * @param startPrg 開始プログラムコード
	 */
	void setStartProgramCode(String startPrg);

	/**
	 * 終了プログラムコード設定
	 * 
	 * @param endPrg 終了プログラムコード
	 */
	void setEndProgramCode(String endPrg);

	/**
	 * 開始ユーザコード設定
	 * 
	 * @param userCode 開始ユーザコード
	 */
	void setStartUserCode(String userCode);

	/**
	 * 終了ユーザコード設定
	 * 
	 * @param endUser 終了ユーザコード
	 */
	void setEndUserCode(String endUser);

	/**
	 * ログインアウト対象設定
	 */
	void onLogin();

	/**
	 * ログインアウト非対象設定
	 */
	void offLogin();

	/**
	 * 実行ログ一覧を取得
	 * 
	 * @return List 実行ログ一覧
	 */
	List<ExecutedLog> getExecutedLogList();

	/**
	 * 日付でソート
	 * 
	 * @param list 対象リスト
	 */
	void sortByDate(List<ExecutedLog> list);

	/**
	 * ユーザコードでソート
	 * 
	 * @param list 対象リスト
	 */
	void sortByUserCode(List<ExecutedLog> list);

	/**
	 * プログラムコードでソート
	 * 
	 * @param list 対象リスト
	 */
	void sortByProgramCode(List<ExecutedLog> list);

	/**
	 * 言語コード設定
	 * 
	 * @param langCode
	 */
	void setLangCode(String langCode);

	/**
	 * sort設定
	 * 
	 * @param sort
	 */
	void setSort(int sort);

	/**
	 * ログ実行
	 * 
	 * @param userCode ユーザコード
	 * @param userName ユーザ名称
	 * @param ipAddress IPアドレス
	 * @param prgCode プログラムID
	 * @param state 状態
	 */
	public void log(String userCode, String userName, String ipAddress, String prgCode, String state);

	/**
	 * ログインログ実行
	 * 
	 * @param userCode ユーザコード
	 * @param userName ユーザ名称
	 * @param ipAddress IPアドレス
	 */
	public void logLogin(String userCode, String userName, String ipAddress);

	/**
	 * ログアウトログ実行
	 * 
	 * @param userCode ユーザコード
	 * @param userName ユーザ名称
	 * @param ipAddress IPアドレス
	 */
	public void logLogout(String userCode, String userName, String ipAddress);
}
