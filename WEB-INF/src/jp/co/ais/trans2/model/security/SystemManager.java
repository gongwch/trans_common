package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * システムマネージャ
 */
public interface SystemManager {

	/**
	 * ユーザエントリ(ログイン)
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザコード
	 * @throws TException
	 */
	public void entryUser(String companyCode, String userCode) throws TException;

	/**
	 * 指定されるプログラムが開けるかどうか(メニュークリックタイミング)
	 * 
	 * @param log ログ情報
	 * @return true:開ける
	 * @throws TException
	 */
	public boolean canOpenProgram(Log log) throws TException;

	/**
	 * 指定されるプログラムを開く(メニュークリックタイミング)
	 * 
	 * @param log ログ情報
	 * @throws TException
	 */
	public void openProgram(Log log) throws TException;

	/**
	 * 指定されるユーザが開いてるプログラムを閉じる(タブ閉じる、×閉じる)
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザコード
	 * @param programCode プログラムコード
	 * @throws TException
	 */
	public void closeProgram(String companyCode, String userCode, String programCode) throws TException;

	/**
	 * 指定されるユーザが開いてる全てプログラムを閉じる
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザコード
	 * @throws TException
	 */
	public void closeAllProgram(String companyCode, String userCode) throws TException;

	/**
	 * OPキャッシュ情報の取得
	 * 
	 * @param param
	 * @return OPキャッシュ情報
	 * @throws TException
	 */
	public OPLoginInfo getOPCache(OPLoginInfo param) throws TException;

}
