package jp.co.ais.trans.logic.system;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;

/**
 * ユーザ認証ロジック
 * 
 * @author AIS
 */
public interface UserManager {

	/**
	 * 会社コード設定
	 * 
	 * @param compCode 会社コード
	 */
	public void setCompanyCode(String compCode);

	/**
	 * ユーザコード設定
	 * 
	 * @param userCode ユーザコード
	 */
	public void setUserCode(String userCode);

	/**
	 * ユーザを照合する（ログイン認証）.<br>
	 * 会社コード整合性チェック/ユーザコード、<br>
	 * パスワード整合性チェック/多重ログインチェックを行う
	 * 
	 * @param password パスワード
	 * @return true 認証OK false エラー
	 */
	public boolean collatedUser(String password);

	/**
	 * エラーメッセージ
	 * 
	 * @return エラーメッセージ
	 */
	public String getErrorMsg();

	/**
	 * ログイン処理
	 */
	public void login();

	/**
	 * ログアウト処理
	 */
	public void logout();

	/**
	 * ユーザ情報・会社情報を構築する
	 * 
	 * @return ユーザ情報
	 * @throws TException
	 */
	public TUserInfo makeUserInfo() throws TException;

	/**
	 * 会社コードに紐づく通貨情報(key:通貨コード value:小数点桁数)
	 * 
	 * @return 通貨情報リスト
	 */
	public Map getCompanyCurrency();

}
