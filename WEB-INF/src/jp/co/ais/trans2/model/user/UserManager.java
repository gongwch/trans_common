package jp.co.ais.trans2.model.user;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ユーザーインターフェース。
 * 
 * @author AIS
 */
public interface UserManager {

	/**
	 * 指定条件に該当するユーザー情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するユーザー情報
	 * @throws TException
	 */
	public List<User> get(UserSearchCondition condition) throws TException;

	/**
	 * 指定の会社コード、ユーザーコードに紐付くユーザー情報を返す
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @return 指定の会社コード、ユーザーコードに紐付くユーザー
	 * @throws TException
	 */
	public User get(String companyCode, String userCode) throws TException;

	/**
	 * 指定の会社コード、ユーザーコード、パスワードに紐付くユーザー情報を返す
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param password パスワード
	 * @return 指定の会社コード、ユーザーコード、パスワードに紐付くユーザー
	 * @throws TException
	 */
	public User get(String companyCode, String userCode, String password) throws TException;

	/**
	 * プリンタ名称を返す(プリンタ設定がある場合)
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @return プリンタ名称
	 * @throws TException
	 */
	public String getPrinterName(String companyCode, String userCode) throws TException;

	/**
	 * ユーザーを登録する。
	 * 
	 * @param user ユーザー
	 * @throws TException
	 */
	public void entry(User user) throws TException;

	/**
	 * ユーザーを修正する。
	 * 
	 * @param user ユーザー
	 * @throws TException
	 */
	public void modify(User user) throws TException;

	/**
	 * ユーザーを削除する。
	 * 
	 * @param user ユーザー
	 * @throws TException
	 */
	public void delete(User user) throws TException;

	/**
	 * ユーザーのL&Fを登録する。
	 * 
	 * @param user ユーザー
	 * @throws TException
	 */
	public void entryLookandFeel(User user) throws TException;

	/**
	 * ユーザーマスタ一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式のユーザーマスタ一覧
	 * @throws TException
	 */
	public byte[] getExcel(UserSearchCondition condition) throws TException;

	/**
	 * クライアントプリンタ名称を更新する。
	 * 
	 * @param user プリンタ名称
	 * @throws TException
	 */
	public void updatePrinter(User user) throws TException;

	/**
	 * ダッシュボード権限コントロール一覧取得
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するユーザー情報
	 * @throws TException
	 */
	public List<USR_DASH_CTL> getDashBoardSysKbn(UserSearchCondition condition) throws TException;

}
