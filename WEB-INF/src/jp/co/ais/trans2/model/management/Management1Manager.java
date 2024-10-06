package jp.co.ais.trans2.model.management;

import java.util.List;
import jp.co.ais.trans.common.except.*;

/**
 * 管理インターフェース。
 * 
 * @author AIS
 */
public interface Management1Manager {

	/**
	 * 指定条件に該当する管理情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する管理情報
	 * @throws TException
	 */
	public List<Management1> get(Management1SearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する管理情報を返す<br>
	 * 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する管理情報
	 * @throws TException
	 */
	public List<Management1> getRef(Management1SearchCondition condition) throws TException;

	/**
	 * 管理1を登録する。
	 * 
	 * @param management1 管理1
	 * @throws TException
	 */
	public void entry(Management1 management1) throws TException;

	/**
	 * 管理1を修正する。
	 * 
	 * @param management1 管理1
	 * @throws TException
	 */
	public void modify(Management1 management1) throws TException;

	/**
	 * 管理1を削除する。
	 * 
	 * @param management1 管理1
	 * @throws TException
	 */
	public void delete(Management1 management1) throws TException;

	/**
	 * 管理1一覧をエクセル形式で返す
	 * @param condition 検索条件
	 * @return エクセル形式の管理1一覧
	 * @throws TException
	 */
	public byte[] getExcel(Management1SearchCondition condition) throws TException;

}
