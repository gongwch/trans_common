package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.*;

/**
 * プログラム単位の排他管理
 * 
 * @author AIS
 */
public interface ProgramExclusiveManager {

	/**
	 * 排他する
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザコード
	 * @param programCode プログラムコード
	 * @throws TException
	 */
	public void exclude(String companyCode, String userCode, String programCode) throws TException;

	/**
	 * 排他する
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザコード
	 * @param programCode プログラムコード
	 * @param processID 処理ID
	 * @throws TException
	 */
	public void exclude(String companyCode, String userCode, String programCode, String processID) throws TException;

	/**
	 * 排他解除する
	 * 
	 * @throws TException
	 */
	public void cancelExclude() throws TException;

	/**
	 * 排他解除する
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザコード
	 * @param processID 処理ID
	 * @throws TException
	 */
	public void cancelExclude(String companyCode, String userCode, String processID) throws TException;

}
