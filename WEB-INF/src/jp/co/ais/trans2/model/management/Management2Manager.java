package jp.co.ais.trans2.model.management;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 管理2インターフェース。
 * 
 * @author AIS
 */
public interface Management2Manager {

	/**
	 * 指定条件に該当する管理情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する管理情報
	 * @throws TException
	 */
	public List<Management2> get(Management2SearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する管理情報を返す<br>
	 * 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する管理情報
	 * @throws TException
	 */
	public List<Management2> getRef(Management2SearchCondition condition) throws TException;

	/**
	 * 管理2を登録する。
	 * 
	 * @param management2
	 * @throws TException
	 */
	public void entry(Management2 management2) throws TException;

	/**
	 * 管理2を修正する。
	 * 
	 * @param management2
	 * @throws TException
	 */
	public void modify(Management2 management2) throws TException;

	/**
	 * 管理2を削除する。
	 * 
	 * @param management2
	 * @throws TException
	 */
	public void delete(Management2 management2) throws TException;

	/**
	 * プログラム(エクセル)を返す
	 * 
	 * @param condition 検索条件
	 * @return プログラム
	 * @throws TException
	 */
	public byte[] getExcel(Management2SearchCondition condition) throws TException;

}
