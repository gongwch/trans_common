package jp.co.ais.trans2.model.management;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 管理3インターフェース。
 * 
 * @author AIS
 */
public interface Management3Manager {

	/**
	 * 指定条件に該当する管理3情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する管理3情報
	 * @throws TException
	 */
	public List<Management3> get(Management3SearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する管理3情報を返す<br>
	 * 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する管理3情報
	 * @throws TException
	 */
	public List<Management3> getRef(Management3SearchCondition condition) throws TException;

	/**
	 * 管理3を登録する。
	 * 
	 * @param management
	 * @throws TException
	 */
	public void entry(Management3 management) throws TException;

	/**
	 * 管理3を修正する。
	 * 
	 * @param management
	 * @throws TException
	 */
	public void modify(Management3 management) throws TException;

	/**
	 * 管理3を削除する。
	 * 
	 * @param management
	 * @throws TException
	 */
	public void delete(Management3 management) throws TException;

	/**
	 * 管理3一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の管理3一覧
	 * @throws TException
	 */
	public byte[] getExcel(Management3SearchCondition condition) throws TException;

}
