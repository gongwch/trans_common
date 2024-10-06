package jp.co.ais.trans2.model.security;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 排他制御テーブルのマネージャ
 * 
 * @author AIS
 */
public interface CodeExclusiveManager {

	/**
	 * 排他する
	 * 
	 * @param processType
	 * @param code
	 * @param rowNo
	 * @throws TException
	 */
	public void exclude(String processType, String code, String rowNo) throws TException;

	/**
	 * 排他解除する
	 * 
	 * @throws TException
	 */
	public void cancelExclude() throws TException;

	/**
	 * 排他解除する
	 * 
	 * @param processType
	 * @param code
	 * @param rowNo
	 * @throws TException
	 */
	public void cancelExclude(String processType, String code, String rowNo) throws TException;

	/**
	 * 排他掛ける<br>
	 * 複数一括排他「codeListとrowNoListの個数は一致する必要」
	 * 
	 * @param processType
	 * @param codeList
	 * @param rowNoList
	 * @throws TException
	 */
	public void exclude(String processType, List<String> codeList, List<Integer> rowNoList) throws TException;

	/**
	 * 排他解除<br>
	 * 複数一括排他「codeListとrowNoListの個数は一致する必要」
	 * 
	 * @param processType
	 * @param codeList
	 * @param rowNoList
	 * @throws TException
	 */
	public void cancelExclude(String processType, List<String> codeList, List<Integer> rowNoList) throws TException;
}
