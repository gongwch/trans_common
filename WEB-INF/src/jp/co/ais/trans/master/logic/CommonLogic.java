package jp.co.ais.trans.master.logic;

import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 
 */
public interface CommonLogic {

	/**
	 * 一覧画面での検索
	 * 
	 * @param conditions
	 * @return List
	 * @throws ParseException
	 * @conditions 検索条件（Map形式）
	 */
	List find(Map conditions) throws ParseException;

	/**
	 * 特定のレコードの検索
	 * 
	 * @param keys
	 * @return Object
	 * @throws ParseException
	 * @keys レコードの主キー（Map形式）
	 */
	Object findOne(Map keys) throws ParseException;

	/**
	 * REFダイアログ用の検索
	 * 
	 * @conditions 検索条件（Map形式、少なくともcode/name_S/name_K三つ条件を含む）
	 * @param conditions
	 * @return List
	 * @throws ParseException
	 */
	List findREF(Map conditions) throws ParseException;

	/**
	 * ButtonFieldでコードを入力時、略称の検索
	 * 
	 * @keys 検索条件（Map形式、findREFと同じ条件）
	 * @param keys
	 * @return String
	 * @throws ParseException
	 */
	String findName(Map keys) throws ParseException;

	/**
	 * 新規場合の主キー重複の回避チェック
	 * 
	 * @param keys
	 * @return boolean
	 * @throws ParseException
	 * @keys レコードの主キー（Map形式）
	 */
	boolean checkCode(Map keys) throws ParseException;

	/**
	 * レコードの削除
	 * 
	 * @param keys
	 * @throws ParseException
	 * @keys レコードの主キー（Map形式）
	 */
	void delete(Map keys) throws ParseException;

	/**
	 * レコードの新規（該当レコード既に存在する場合は、エラーをスロー）
	 * 
	 * @dto レコード
	 * @param dto
	 * @throws TException
	 */
	void insert(Object dto) throws TException;

	/**
	 * レコードの変更（該当レコードは存在しない場合は、エラーをスロー）
	 * 
	 * @param dto
	 * @throws TException
	 * @dto レコード
	 */
	void update(Object dto) throws TException;

	/**
	 * レコードの登録（該当レコードは存在する場合は、変更を行う；存在しない場合は、新規を行う）
	 * 
	 * @dto レコード
	 * @param dto
	 */
	void save(Object dto);

	/**
	 * ユーザーIDをセット
	 * 
	 * @param userID
	 * @throws TException
	 */
	void setUserCode(String userID) throws TException;
}
