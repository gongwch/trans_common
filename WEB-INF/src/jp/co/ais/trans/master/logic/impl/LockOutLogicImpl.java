package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * ロックアウトマスタロジック
 */
public class LockOutLogicImpl implements LockOutLogic {

	/** ロックアウトマスタDao */
	private LOCK_OUT_TBLDao dao;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao LOCK_OUT_TBL
	 */
	public LockOutLogicImpl(LOCK_OUT_TBLDao dao) {

		// 取引先マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * ロックアウト情報消去
	 * 
	 * @param entityList エンティティリスト
	 */
	public void delete(List<LOCK_OUT_TBL> entityList) {

		// ロックアウトリスト削除
		for (LOCK_OUT_TBL row : entityList) {
			dao.delete(row);
		}
	}

	/**
	 * ロックアウトデータ取得
	 * 
	 * @param kaiCode 会社コード
	 * @param arrCount 失敗回数制限
	 * @return ロックアウトリスト
	 */
	public List<LOCK_OUT_TBL> findWithUserName(String kaiCode, int arrCount) {
		return dao.findWithUserName(kaiCode, arrCount);
	}

}
