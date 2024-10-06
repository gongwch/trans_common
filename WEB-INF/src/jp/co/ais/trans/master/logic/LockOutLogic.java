package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ロックアウトビジネスロジック
 * 
 * @author roh
 */
public interface LockOutLogic {

	/**
	 * ロックアウトデータ取得
	 * 
	 * @param kaiCode 会社コード
	 * @param arrCount 失敗回数制限
	 * @return ロックアウトリスト
	 */
	public abstract List<LOCK_OUT_TBL> findWithUserName(String kaiCode, int arrCount);

	/**
	 * ロックアウト情報消去
	 * 
	 * @param entityList エンティティリスト
	 */
	public abstract void delete(List<LOCK_OUT_TBL> entityList);

}
