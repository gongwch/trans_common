package jp.co.ais.trans2.model.code;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * コードマスタ共通マネージャ
 */
public interface CodeManager {

	/**
	 * コードマスタリストの取得
	 * 
	 * @param condition
	 * @return コードマスタリスト
	 * @throws TException
	 */
	public List<OP_CODE_MST> get(CodeSearchCondition condition) throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(CodeSearchCondition condition) throws TException;

}
