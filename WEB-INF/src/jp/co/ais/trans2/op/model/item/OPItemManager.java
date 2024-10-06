package jp.co.ais.trans2.op.model.item;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * OPアイテムマネージャ
 */
public interface OPItemManager {

	/**
	 * REF用データの取得
	 * 
	 * @param condition
	 * @return REF用データ
	 * @throws TException
	 */
	public List<OPItem> get(OPItemSearchCondition condition) throws TException;

	/**
	 * OPITEM情報を返す
	 * 
	 * @param company
	 * @param item
	 * @return OPItem
	 * @throws TException
	 */
	public OPItem getItem(String company, String item) throws TException;

	/**
	 * 指定条件に該当するアイテムを返す
	 * 
	 * @param condition
	 * @return 指定条件に該当するアイテム
	 * @throws TException
	 */
	public OPItem getCode(OPItemSearchCondition condition) throws TException;

	/**
	 * ITEM情報を登録する。
	 * 
	 * @param bean item
	 * @param oldBean old item
	 * @return result
	 * @throws TException
	 */
	public OPItem entry(OPItem bean, OPItem oldBean) throws TException;

	/**
	 * Checks if is exist item
	 * 
	 * @param bean the bean
	 * @return true, if is exist
	 * @throws TException exception
	 */
	public boolean isExist(OPItem bean) throws TException;

	/**
	 * ITEM情報を修正する。
	 * 
	 * @param item
	 * @throws TException
	 */
	public void modify(OPItem item) throws TException;

	/**
	 * Insert new item
	 * 
	 * @param item
	 * @throws TException exception
	 */
	public void insert(OPItem item) throws TException;

	/**
	 * @param conn
	 * @param list
	 * @throws Exception
	 */
	public void insert(Connection conn, List<OPItem> list) throws Exception;

	/**
	 * ITEM情報を削除する。
	 * 
	 * @param item
	 * @throws TException
	 */
	public void delete(OPItem item) throws TException;

	/**
	 * OPアイテム削除(対象油種が存在してない場合)
	 * 
	 * @param conn
	 * @throws TException
	 */
	@Deprecated
	public void delete(Connection conn) throws TException;

	/**
	 * Checks if is exist item by control type
	 * 
	 * @param companyCode the company code
	 * @param itemControlType the item control type
	 * @param subItemControlType the sub item control type
	 * @return true, if is exist item
	 * @throws TException exception
	 */
	public Boolean isExistControlType(String companyCode, String itemControlType, String subItemControlType) throws TException;

	/**
	 * Gets the excel data.
	 * 
	 * @param condition the condition
	 * @return the excel
	 * @throws TException exception
	 */
	public byte[] getExcel(OPItemSearchCondition condition) throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(OPItemSearchCondition condition) throws TException;

}
