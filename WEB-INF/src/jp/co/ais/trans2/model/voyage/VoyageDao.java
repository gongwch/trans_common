package jp.co.ais.trans2.model.voyage;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * VoyageマスタDao
 * 
 * @author AIS
 */
public interface VoyageDao {

	/**
	 * 指定条件に該当するVoyage情報を返す。
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するVoyage情報
	 * @throws TException
	 */
	public List<Voyage> getByCondition(VoyageSearchCondition condition) throws TException;

	/**
	 * 指定コードに該当するVoyage情報を返す。
	 * 
	 * @param companyCode 会社コード
	 * @param voyageCode Voyageコード
	 * @return 指定コードに該当するVoyage情報
	 * @throws TException
	 */
	public Voyage get(String companyCode, String voyageCode) throws TException;

	/**
	 * Voyageを登録する。
	 * 
	 * @param dto Voyage
	 * @throws TException
	 */
	public void insert(Voyage dto) throws TException;

	/**
	 * Voyageを修正する。
	 * 
	 * @param dto Voyage
	 * @throws TException
	 */
	public void update(Voyage dto) throws TException;

	/**
	 * Voyageを削除する。
	 * 
	 * @param dto Voyage
	 * @throws TException
	 */
	public void delete(Voyage dto) throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(VoyageSearchCondition condition) throws TException;

}
