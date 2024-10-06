package jp.co.ais.trans2.model.voyage;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * Voyage管理インターフェース
 * 
 * @author AIS
 */
public interface VoyageManager {

	/**
	 * 指定条件に該当するVoyage情報を返す。
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するVoyage情報
	 * @throws TException
	 */
	public List<Voyage> get(VoyageSearchCondition condition) throws TException;

	/**
	 * 指定コードに紐付くVoyage情報を返す。
	 * 
	 * @param companyCode 会社コード
	 * @param voyageCode Voyageコード
	 * @return 指定コードに紐付くVoyage情報
	 * @throws TException
	 */
	public Voyage get(String companyCode, String voyageCode) throws TException;

	/**
	 * Voyageを登録する。
	 * 
	 * @param voyage Voyage
	 * @throws TException
	 */
	public void entry(Voyage voyage) throws TException;

	/**
	 * Voyageを修正する。
	 * 
	 * @param voyage Voyage
	 * @throws TException
	 */
	public void modify(Voyage voyage) throws TException;

	/**
	 * Voyageを削除する。
	 * 
	 * @param voyage Voyage
	 * @throws TException
	 */
	public void delete(Voyage voyage) throws TException;

	/**
	 * エクセルファイルを作成する
	 * 
	 * @param condition
	 * @return エクセルファイル
	 * @throws TException
	 */
	public byte[] getExcel(VoyageSearchCondition condition) throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(VoyageSearchCondition condition) throws TException;

}
