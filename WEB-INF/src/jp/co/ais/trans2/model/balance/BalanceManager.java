package jp.co.ais.trans2.model.balance;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 残高マネージャ
 */
public interface BalanceManager {

	/**
	 * AP/AR残高更新
	 * 
	 * @param slip 伝票
	 */
	public void updateBalance(Slip slip);

	/**
	 * AP残高にロックをかける
	 * 
	 * @param list 対象残高(条件)リスト
	 * @throws TException 排他失敗
	 */
	public void lockApBalance(List<BalanceCondition> list) throws TException;

	/**
	 * AR残高にロックをかける
	 * 
	 * @param list 対象残高(条件)リスト
	 * @throws TException 排他失敗
	 */
	public void lockArBalance(List<BalanceCondition> list) throws TException;

	/**
	 * AP残高のロック解除
	 * 
	 * @param condition 対象残高(条件)
	 * @throws TException 排他失敗
	 */
	public void unlockApBalance(BalanceCondition condition) throws TException;

	/**
	 * AP残高のロック解除（複数）
	 * 
	 * @param condition 対象残高(条件)
	 * @throws TException 排他失敗
	 */
	public void unlockApBalanceList(BalanceCondition condition) throws TException;

	/**
	 * AR残高のロック解除
	 * 
	 * @param condition 対象残高(条件)
	 * @throws TException 排他失敗
	 */
	public void unlockArBalance(BalanceCondition condition) throws TException;

	/**
	 * AR残高のロック解除（複数）
	 * 
	 * @param condition 対象残高(条件)
	 * @throws TException 排他失敗
	 */
	public void unlockArBalanceList(BalanceCondition condition) throws TException;

	/**
	 * 債務残高データ取得
	 * 
	 * @param conditon 条件
	 * @return 残高データ
	 */
	public List<AP_ZAN> getApBalance(BalanceCondition conditon);

	/**
	 * 債務残高削除(消し込まれていない状態に戻す)
	 * 
	 * @param condition 条件
	 */
	public void clearApBalance(BalanceCondition condition);

	/**
	 * 債務残高更新(支払済みの状態にする)
	 * 
	 * @param list 残高リスト
	 */
	public void updateApBalance(List<AP_ZAN> list);

	/**
	 * 債権残高データ取得
	 * 
	 * @param conditon 条件
	 * @return List データ
	 */
	public List<AR_ZAN> getSummaryArBalance(BalanceCondition conditon);

	/**
	 * 債権残高データ取得
	 * 
	 * @param conditon 条件
	 * @return List データ
	 */
	public List<AR_ZAN> getArBalance(BalanceCondition conditon);

	/**
	 * 債務残高削除(消し込みデータの削除)
	 * 
	 * @param condition 条件
	 */
	public void clearArBalance(BalanceCondition condition);

	/**
	 * 債権残高更新(消し込みデータの追加)<br>
	 * swkDtlをBeansで渡し、残高情報更新
	 * 
	 * @param list 残高リスト
	 */
	public void insertArBalance(List<AR_ZAN> list);

	/**
	 * 債務残高更新(消し込みデータの追加)<br>
	 * swkDtlをBeansで渡し、残高情報更新
	 * 
	 * @param list 残高リスト
	 */
	public void insertApBalance(List<AP_ZAN> list);

	/**
	 * 登録
	 * 
	 * @param bean エンティティ
	 */
	public void insertByUpdateDate(AP_ZAN bean);

	/**
	 * 更新
	 * 
	 * @param bean エンティティ
	 * @return int
	 */
	public int updateByUpdateDate(AP_ZAN bean);

	/**
	 * 削除
	 * 
	 * @param bean 残高リスト
	 * @return int
	 */
	public int deleteByUpdateDate(AP_ZAN bean);

	/**
	 * 債務残高 1件検索(今はキーのみ)
	 * 
	 * @param condition 条件
	 * @return 債務残高
	 */
	public AP_ZAN getAP(BalanceCondition condition);

	/**
	 * 債権残高残高 1件検索(今はキーのみ)
	 * 
	 * @param condition 条件
	 * @return 債務残高
	 */
	public AR_ZAN getAR(BalanceCondition condition);

	/**
	 * CM_FUND_DTL情報の削除
	 * 
	 * @param condition
	 * @param isAP
	 * @return List<String>
	 * @throws TException
	 */
	public List<String> deleteCmFundInfoAPAR(BalanceCondition condition, boolean isAP) throws TException;

	/**
	 * CM_FUND_DTL情報の更新
	 * 
	 * @param kaiCode
	 * @param slipNoList
	 * @param isAP true:AP false:AR
	 * @throws TException
	 */
	public void updateCmFundDtl(String kaiCode, List<String> slipNoList, boolean isAP) throws TException;
}