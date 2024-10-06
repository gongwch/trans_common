package jp.co.ais.trans2.model.tag;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * MG0460TagMaster - 付箋マスタ - Interface Class
 * 
 * @author AIS
 */
public interface TagManager {

	/**
	 * 情報検索 (SELECT)
	 * 
	 * @param condition 検索条件
	 * @return Tag 付箋マスタ
	 * @throws TException
	 */
	public List<Tag> get(TagSearchCondition condition) throws TException;

	/**
	 * 情報登録 (INSERT)
	 * 
	 * @param bean 入力情報
	 * @throws TException
	 */
	public void entry(Tag bean) throws TException;

	/**
	 * 情報修正 (UPDATE)
	 * 
	 * @param bean 入力情報
	 * @throws TException
	 */
	public void modify(Tag bean) throws TException;

	/**
	 * 情報削除 (DELETE)
	 * 
	 * @param bean 選択情報
	 * @throws TException
	 */
	public void delete(Tag bean) throws TException;
}