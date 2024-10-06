package jp.co.ais.trans2.model.item;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 科目制御区分インターフェース。
 * 
 * @author AIS
 */
public interface AutomaticJournalItemManager {

	/**
	 * 指定条件に該当する科目制御区分を返す
	 * @param condition 
	 * 
	 * @return 指定条件に該当する科目制御区分情報
	 * @throws TException
	 */
	public List<AutomaticJournalItem> get(AutomaticJournalItemSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する科目制御区分を登録する
	 * @param bean 
	 * @throws TException 
	 */
	public void entry(AutomaticJournalItem bean) throws TException;

	/**
	 * 指定条件に該当する科目制御区分を修正する
	 * @param bean 
	 * @throws TException 
	 */
	public void modify(AutomaticJournalItem bean) throws TException;

	/**
	 * 指定条件に該当する科目制御区分を削除する
	 * @param bean 
	 * @throws TException 
	 */
	public void delete(AutomaticJournalItem bean) throws TException;

	/**
	 * 指定条件に該当する科目制御区分の貸借をチェックする
	 * @param bean 
	 * @return int
	 * @throws TException 
	 */
	public int check(AutomaticJournalItem bean) throws TException;

	/**
	 * エクセル
	 * 
	 * @param condition
	 * @return 科目制御区分情報
	 * @throws TException
	 */
	public byte[] getExcel(AutomaticJournalItemSearchCondition condition) throws TException;

}
