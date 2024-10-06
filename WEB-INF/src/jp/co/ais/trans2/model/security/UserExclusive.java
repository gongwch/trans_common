package jp.co.ais.trans2.model.security;

/**
 * ユーザロック状態
 */
public enum UserExclusive {

	/** 登録可能 */
	CanEntry,

	/** パスワード不正または入力情報不正 */
	WrongPasswordOrElse,

	/** ロック中 */
	Locked;

}
