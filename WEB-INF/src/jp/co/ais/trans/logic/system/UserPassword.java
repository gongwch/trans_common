package jp.co.ais.trans.logic.system;

import java.io.*;

/**
 * パスワード変更ロジック
 */
public interface UserPassword extends Serializable {

	/**
	 * コード設定
	 * 
	 * @param compCode 会社コード
	 * @param userCode ユーザコード
	 */
	public void setCode(String compCode, String userCode);

	/**
	 * 現在のパスワードと同一かどうかをチェックする
	 * 
	 * @param password パスワード
	 * @return true:現在のパスワードと同一
	 */
	public boolean equalsNowPassword(String password);

	/**
	 * パスワード管理を行うかどうか
	 * 
	 * @return true:パスワード管理する
	 */
	public boolean isPasswordManaged();

	/**
	 * パスワードが有効期間をオーバーしているかチェック
	 * 
	 * @return boolean true:有効期間オーバー
	 */
	public boolean isPasswordTermOrver();

	/**
	 * パスワードの妥当性チェック
	 * 
	 * @param password 対象パスワード
	 * @return true:パスワードが妥当
	 */
	public boolean assertPassword(String password);

	/**
	 * 文字数チェック
	 * 
	 * @param password パスワード
	 * @return boolean true:文字数規定を満たす
	 */
	public boolean assertLength(String password);

	/**
	 * 複雑レベルチェック
	 * 
	 * @param password パスワード
	 * @return boolean true:複雑レベルを満たしている
	 */
	public boolean assertComplicateLevel(String password);

	/**
	 * パスワード履歴更新
	 * 
	 * @param password パスワード
	 * @return boolean true:パスワード履歴内に存在
	 */
	public boolean containtsHistory(String password);

	/**
	 * パスワード情報の更新
	 * 
	 * @param password パスワード
	 * @param prgID プログラムID
	 */
	public void update(String password, String prgID);

	/**
	 * エラー内容をメッセージIDで返す
	 * 
	 * @return メッセージID
	 */
	public String getErrorMessageId();

	/**
	 * エラーメッセージIDのバインド文字リストを返す
	 * 
	 * @return バインド文字リスト
	 */
	public Object[] getErrorArgs();

	/**
	 * パスワード有効期限切れ通知日数チェック
	 * 
	 * @return boolean true: パスワード有効期限切れの通知をする
	 */
	public boolean isPwdLimitMsgNotice();

	/**
	 * パスワード有効期限までの残日数を返す
	 * 
	 * @return パスワード有効期限までの残日数
	 */
	public int getPwdLimitMsg();

	/**
	 * パスワード履歴更新
	 * 
	 * @param password
	 */
	public void updateHistory(String password);

}
