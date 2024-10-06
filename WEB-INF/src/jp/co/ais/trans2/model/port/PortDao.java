package jp.co.ais.trans2.model.port;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * PortマスタDao
 * 
 * @author AIS
 */
public interface PortDao {

	/**
	 * 指定条件に該当するPort情報を返す。
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するPort情報
	 * @throws TException
	 */
	public List<Port> getByCondition(PortSearchCondition condition) throws TException;

	/**
	 * 指定コードに該当するPort情報を返す。
	 * 
	 * @param companyCode 会社コード
	 * @param voyageCode Portコード
	 * @return 指定コードに該当するPort情報
	 * @throws TException
	 */
	public Port get(String companyCode, String voyageCode) throws TException;

	/**
	 * Portを登録する。
	 * 
	 * @param dto Port
	 * @throws TException
	 */
	public void insert(Port dto) throws TException;

	/**
	 * Portを修正する。
	 * 
	 * @param dto Port
	 * @throws TException
	 */
	public void update(Port dto) throws TException;

	/**
	 * Portを削除する。
	 * 
	 * @param dto Port
	 * @throws TException
	 */
	public void delete(Port dto) throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(PortSearchCondition condition) throws TException;

}
