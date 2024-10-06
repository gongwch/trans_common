package jp.co.ais.trans2.model.management;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 管理5インターフェース。
 * 
 * @author AIS
 */
public interface Management5Manager {

	/**
	 * 指定条件に該当する管理5情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する管理5情報
	 * @throws TException
	 */
	public List<Management5> get(Management5SearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する管理5情報を返す<br>
	 * 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する管理5情報
	 * @throws TException
	 */
	public List<Management5> getRef(Management5SearchCondition condition) throws TException;

	/**
	 * 管理5を登録する。
	 * 
	 * @param management
	 * @throws TException
	 */
	public void entry(Management5 management) throws TException;

	/**
	 * 管理5を修正する。
	 * 
	 * @param management
	 * @throws TException
	 */
	public void modify(Management5 management) throws TException;

	/**
	 * 管理5を削除する。
	 * 
	 * @param management
	 * @throws TException
	 */
	public void delete(Management5 management) throws TException;

	/**
	 * 管理5一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の管理5一覧
	 * @throws TException
	 */
	public byte[] getExcel(Management5SearchCondition condition) throws TException;

}
