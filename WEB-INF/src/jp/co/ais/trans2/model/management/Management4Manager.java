package jp.co.ais.trans2.model.management;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 管理4インターフェース。
 * 
 * @author AIS
 */
public interface Management4Manager {

	/**
	 * 指定条件に該当する管理4情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する管理4情報
	 * @throws TException
	 */
	public List<Management4> get(Management4SearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する管理4情報を返す<br>
	 * 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する管理4情報
	 * @throws TException
	 */
	public List<Management4> getRef(Management4SearchCondition condition) throws TException;

	/**
	 * 管理4を登録する。
	 * 
	 * @param management
	 * @throws TException
	 */
	public void entry(Management4 management) throws TException;

	/**
	 * 管理4を修正する。
	 * 
	 * @param management
	 * @throws TException
	 */
	public void modify(Management4 management) throws TException;

	/**
	 * 管理4を削除する。
	 * 
	 * @param management
	 * @throws TException
	 */
	public void delete(Management4 management) throws TException;

	/**
	 * 管理4一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の管理4一覧
	 * @throws TException
	 */
	public byte[] getExcel(Management4SearchCondition condition) throws TException;

}
