package jp.co.ais.trans2.model.history;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 承認履歴マネージャ
 */
public interface ApproveHistoryManager {

	/**
	 * 承認履歴データの取得
	 * 
	 * @param condition 条件
	 * @return List データ
	 * @throws TException 取得失敗
	 */
	public List<ApproveHistory> get(ApproveHistoryCondition condition) throws TException;

	/**
	 * 承認コントロールの取得
	 * 
	 * @param condition 条件
	 * @return List データ
	 * @throws TException 取得失敗
	 */
	public List<ApproveHistory> getCtl(ApproveHistoryCondition condition) throws TException;

	/**
	 * 承認履歴情報の登録
	 * 
	 * @param bean 承認履歴情報
	 * @throws TException
	 */
	public void entry(ApproveHistory bean) throws TException;

	/**
	 * 承認履歴の作成
	 * 
	 * @param employee
	 * @param slip
	 * @param flag 承認フラグ(0:承認、1:キャンセル、2:否認、3:登録、4:修正)
	 * @return 承認履歴
	 */
	public ApproveHistory getApproveHistory(Slip slip, Employee employee, int flag);

	/**
	 * 承認履歴の作成
	 * 
	 * @param employee
	 * @param dtl
	 * @param flag 承認フラグ(0:承認、1:キャンセル、2:否認、3:登録、4:修正)
	 * @return 承認履歴
	 */
	public ApproveHistory getApproveHistory(SlipDen dtl, Employee employee, int flag);

}