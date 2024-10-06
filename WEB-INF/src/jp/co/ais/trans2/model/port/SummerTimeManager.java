package jp.co.ais.trans2.model.port;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * サマータイムマスタManager
 */
public interface SummerTimeManager {

	/**
	 * 取得処理
	 * 
	 * @param condition
	 * @return list
	 * @throws TException
	 */
	public List<OP_SMR_TIME_MST> get(PortSearchCondition condition) throws TException;

	/**
	 * 登録処理
	 * 
	 * @param list
	 * @return 更新後リストデータ
	 * @throws TException
	 */
	public List<OP_SMR_TIME_MST> entry(List<OP_SMR_TIME_MST> list) throws TException;

	/**
	 * エクセル出力
	 * 
	 * @param condition
	 * @return byte[]
	 * @throws TException
	 */
	public byte[] getExcel(PortSearchCondition condition) throws TException;

	/**
	 * エクセルインポート処理
	 * 
	 * @param file
	 * @param condition
	 * @return list 取込後リスト
	 * @throws TErroneousSummerTimeException
	 * @throws TException
	 */
	public List<OP_SMR_TIME_MST> importExcel(File file, PortSearchCondition condition)
		throws TErroneousSummerTimeException, TException;
}
