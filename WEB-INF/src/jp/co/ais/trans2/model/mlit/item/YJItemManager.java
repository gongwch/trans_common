package jp.co.ais.trans2.model.mlit.item;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 輸送実績用アイテムマネージャ
 */
public interface YJItemManager {

	/**
	 * 輸送実績アイテムを取得する
	 * 
	 * @param condition
	 * @return 輸送実績アイテムリスト
	 * @throws TException
	 */
	public List<YJItem> getItems(YJItemSearchCondition condition) throws TException;

	/**
	 * 輸送実績アイテムマスタを削除する
	 * 
	 * @param bean 輸送実績アイテムマスタ
	 * @throws TException
	 */
	public void delete(YJItem bean) throws TException;

	/**
	 * 輸送実績アイテムマスタを登録する
	 * 
	 * @param bean 輸送実績アイテムマスタ
	 * @return 登録時間など設定後のエンティティ
	 * @throws TException
	 */
	public YJItem entryMaster(YJItem bean) throws TException;

	/**
	 * エクセルファイルを作成する
	 * 
	 * @param condition
	 * @return エクセルファイル
	 * @throws TException
	 */
	public byte[] getExcel(YJItemSearchCondition condition) throws TException;

	/**
	 * 輸送実績サブアイテムマスタを削除する
	 * 
	 * @param bean 輸送実績サブアイテムマスタ
	 * @throws TException
	 */
	public void deleteSub(YJItem bean) throws TException;

	/**
	 * 輸送実績サブアイテムマスタを登録する
	 * 
	 * @param bean 輸送実績サブアイテムマスタ
	 * @return 登録時間など設定後のエンティティ
	 * @throws TException
	 */
	public YJItem entrySubMaster(YJItem bean) throws TException;

	/**
	 * エクセルファイルを作成する
	 * 
	 * @param condition
	 * @return エクセルファイル
	 * @throws TException
	 */
	public byte[] getExcelSub(YJItemSearchCondition condition) throws TException;

	/**
	 * サブアイテムを取得する
	 * 
	 * @param condition
	 * @return 輸送実績アイテムリスト
	 * @throws TException
	 */
	public List<YJItem> getSubItems(YJItemSearchCondition condition) throws TException;

}
