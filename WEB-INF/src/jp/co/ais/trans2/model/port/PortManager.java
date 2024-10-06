package jp.co.ais.trans2.model.port;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * Port管理インターフェース
 * 
 * @author AIS
 */
public interface PortManager {

	/**
	 * 指定条件に該当するPort情報を返す。
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するPort情報
	 * @throws TException
	 */
	public List<Port> get(PortSearchCondition condition) throws TException;

	/**
	 * 指定コードに紐付くPort情報を返す。
	 * 
	 * @param companyCode 会社コード
	 * @param portCode Portコード
	 * @return 指定コードに紐付くPort情報
	 * @throws TException
	 */
	public Port get(String companyCode, String portCode) throws TException;

	/**
	 * Portを登録する。
	 * 
	 * @param port Port
	 * @throws TException
	 */
	public void entry(Port port) throws TException;

	/**
	 * Portを修正する。
	 * 
	 * @param port Port
	 * @throws TException
	 */
	public void modify(Port port) throws TException;

	/**
	 * Portを削除する。
	 * 
	 * @param port Port
	 * @throws TException
	 */
	public void delete(Port port) throws TException;

	/**
	 * エクセルファイルを作成する
	 * 
	 * @param condition
	 * @return エクセルファイル
	 * @throws TException
	 */
	public byte[] getExcel(PortSearchCondition condition) throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(PortSearchCondition condition) throws TException;

}
