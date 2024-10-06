package jp.co.ais.trans2.model.bill;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 請求区分マスタインターフェース。
 * 
 * @author AIS
 */
public interface BillTypeManager {

	/**
	 * 指定条件に該当する請求区分マスタ情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する請求区分マスタ情報
	 * @throws TException
	 */
	public List<BillType> get(BillTypeSearchCondition condition) throws TException;

	/**
	 * 請求区分マスタを登録する。
	 * 
	 * @param bean 請求区分マスタ
	 * @throws TException
	 */
	public void entry(BillType bean) throws TException;

	/**
	 * 請求区分マスタを削除する。
	 * 
	 * @param bean 請求区分マスタ
	 * @throws TException
	 */
	public void delete(BillType bean) throws TException;

	/**
	 * 請求区分マスタを登録する。
	 * 
	 * @param bean 請求区分マスタ
	 * @return 登録時間など設定後のエンティティ
	 * @throws TException
	 */
	public BillType entryMaster(BillType bean) throws TException;

	/**
	 * エクセルファイルを作成する
	 * 
	 * @param condition
	 * @return エクセルファイル
	 * @throws TException
	 */
	public byte[] getExcel(BillTypeSearchCondition condition) throws TException;

}
