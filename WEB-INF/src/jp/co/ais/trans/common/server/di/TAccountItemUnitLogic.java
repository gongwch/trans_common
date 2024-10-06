package jp.co.ais.trans.common.server.di;

import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.except.*;

/**
 * 検索ロジックインタフェース<BR>
 * <p>
 * 
 * @author Yitao
 */
public interface TAccountItemUnitLogic {

	/**
	 * 科目コード選択時表示データ取得<BR>
	 * 科目コード選択時表示データ取得。
	 * 
	 * @param inputParameter パラメーター
	 * @return map データマップ
	 * @throws TException
	 */
	public Map<String, Object> getItemInfo(AccountItemInputParameter inputParameter) throws TException;

	/**
	 * 科目マスタ一覧取得<BR>
	 * 科目マスタ一覧取得
	 * 
	 * @param inputParameter パラメーターMap
	 * @return map データマップ
	 * @throws TException
	 */
	public List getItemInfoAll(AccountItemInputParameter inputParameter) throws TException;

	/**
	 * 補助科目コード選択時表示データ取得<BR>
	 * 補助科目コード選択時表示データ取得。
	 * 
	 * @param inputParameter パラメーターMap
	 * @return map データマップ
	 * @throws TException
	 */
	public Map<String, Object> getSubItemInfo(AccountItemInputParameter inputParameter) throws TException;

	/**
	 * 補助科目マスタ一覧取得<BR>
	 * 補助科目マスタ一覧取得
	 * 
	 * @param inputParameter パラメーターMap
	 * @return map データマップ
	 * @throws TException
	 */
	public List getSubItemInfoAll(AccountItemInputParameter inputParameter) throws TException;

	/**
	 * 内訳科目コード選択時表示データ取得<BR>
	 * 内訳科目コード選択時表示データ取得。
	 * 
	 * @param inputParameter
	 * @return map データマップ
	 * @throws TException
	 */
	public Map<String, Object> getBreakDownItemInfo(AccountItemInputParameter inputParameter) throws TException;

	/**
	 * 内訳科目マスタ一覧取得<BR>
	 * 内訳科目マスタ一覧取得
	 * 
	 * @param inputParameter パラメーターMap
	 * @return map データマップ
	 * @throws TException
	 */
	public List getBreakDownItemInfoAll(AccountItemInputParameter inputParameter) throws TException;

}
