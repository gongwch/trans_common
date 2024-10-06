package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * バッチ排他制御ロジック
 * 
 * @author 細谷
 */
public interface BatchUnLockLogic {

	/**
	 * 会社コードでバッチ排他リストを取得
	 * 
	 * @param companyCode 会社コード
	 * @return バッチ排他リスト
	 */
	public List<BAT_CTL> getBatchListByCompanyCode(String companyCode);

	/**
	 * リスト削除
	 * 
	 * @param dtoList バッチ排他リスト
	 */
	public void delete(List<BAT_CTL> dtoList);
}
