package jp.co.ais.trans2.model.item.summary;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * MG0070ItemSummaryMaster - 科目集計マスタ - Interface Class
 * 
 * @author AIS
 */
public interface ItemSummaryManager {

	/**
	 * 情報検索 (SELECT)
	 * 
	 * @param condition 検索条件
	 * @return List 科目集計マスタ情報
	 * @throws TException
	 */
	public List<ItemSummary> get(ItemSummarySearchCondition condition) throws TException;

	/**
	 * リファレンス検索
	 * 
	 * @param condition
	 * @return リファレンス情報
	 * @throws TException
	 */
	public List<ItemSummary> getRef(ItemSummarySearchCondition condition) throws TException;

	/**
	 * 情報登録 (INSERT)
	 * 
	 * @param bean 入力情報
	 * @throws TException
	 */
	public void entry(ItemSummary bean) throws TException;

	/**
	 * 情報修正 (UPDATE)
	 * 
	 * @param bean 入力情報
	 * @throws TException
	 */
	public void modify(ItemSummary bean) throws TException;

	/**
	 * 情報削除 (DELETE)
	 * 
	 * @param bean 選択情報
	 * @throws TException
	 */
	public void delete(ItemSummary bean) throws TException;

	/**
	 * 情報検索しExcelで出力 (SELECT)
	 * 
	 * @param condition 検索条件
	 * @return Excel
	 * @throws TException
	 */
	public byte[] getExcel(ItemSummarySearchCondition condition) throws TException;

	/**
	 * 科目集計情報登録 (INSERT)
	 * 
	 * @param kmtCode 科目体系コード
	 * @param list 入力情報
	 * @throws TException
	 */
	public void entryItemSummary(String kmtCode, List<ItemSummary> list) throws TException;

	/**
	 * 科目集計情報登録 (INSERT)複写時
	 * 
	 * @param preKmtCode 前回科目体系コード
	 * @param kmtCode 今回科目体系コード
	 * @throws TException
	 */
	public void entryCopyItemSummary(String preKmtCode, String kmtCode) throws TException;

	/**
	 * 科目マスタに存在、科目集計マスタに存在しない科目チェック
	 * 
	 * @param orgCode
	 * @return 科目マスタに存在、科目集計マスタに存在しないかどうか true：未登録
	 * @throws TException
	 */
	public String getNotExistItemSum(String orgCode) throws TException;

	/**
	 * 科目マスタデータが存在しない集計科目チェック
	 * 
	 * @param orgCode
	 * @return 科目マスタデータが存在しないかどうか true：未登録
	 * @throws TException
	 */
	public String getNotExistItem(String orgCode) throws TException;
}