package jp.co.ais.trans2.model.mlit.region;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 輸送実績国・地域用マネージャ
 */
public interface YJRegionManager {

	/**
	 * 輸送実績国を取得する
	 * 
	 * @param condition
	 * @return 輸送実績国リスト
	 * @throws TException
	 */
	public List<YJRegion> getRegion(YJRegionSearchCondition condition) throws TException;

	/**
	 * 輸送実績国マスタを削除する
	 * 
	 * @param bean 輸送実績国マスタ
	 * @throws TException
	 */
	public void deleteRegion(YJRegion bean) throws TException;

	/**
	 * 輸送実績国マスタを登録する
	 * 
	 * @param bean 輸送実績国マスタ
	 * @return 登録時間など設定後のエンティティ
	 * @throws TException
	 */
	public YJRegion entryRegionMaster(YJRegion bean) throws TException;

	/**
	 * エクセルファイルを作成する
	 * 
	 * @param condition
	 * @return エクセルファイル
	 * @throws TException
	 */
	public byte[] getExcelRegion(YJRegionSearchCondition condition) throws TException;

	/**
	 * 輸送実績地域を取得する
	 * 
	 * @param condition
	 * @return 輸送実績地域リスト
	 * @throws TException
	 */
	public List<YJRegion> getCountry(YJRegionSearchCondition condition) throws TException;

	/**
	 * 輸送実績地域マスタを削除する
	 * 
	 * @param bean 輸送実績地域マスタ
	 * @throws TException
	 */
	public void deleteCountry(YJRegion bean) throws TException;

	/**
	 * 輸送実績地域マスタを登録する
	 * 
	 * @param bean 輸送実績地域マスタ
	 * @return 登録時間など設定後のエンティティ
	 * @throws TException
	 */
	public YJRegion entryCountryMaster(YJRegion bean) throws TException;

	/**
	 * エクセルファイルを作成する
	 * 
	 * @param condition
	 * @return エクセルファイル
	 * @throws TException
	 */
	public byte[] getExcelCountry(YJRegionSearchCondition condition) throws TException;
}
