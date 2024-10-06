package jp.co.ais.trans2.model.check;

import jp.co.ais.trans.common.except.*;

/**
 * マスタ使用済みチェック
 */
public interface CheckMasterUseDao {

	/**
	 * マスタ使用済みチェック
	 * 
	 * @param condition チェック条件
	 * @throws TException
	 */
	public void check(CheckCondition condition) throws TException;
}
