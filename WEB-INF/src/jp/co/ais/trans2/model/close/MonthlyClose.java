package jp.co.ais.trans2.model.close;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;

/**
 * 月次締め処理
 * 
 * @author AIS
 */
public interface MonthlyClose {

	/**
	 * 月次の締めを行う
	 * 
	 * @param companyCode 会社コード
	 * @param fp 会計期間
	 * @return 月次締め後の会計期間
	 * @throws TException
	 */
	public FiscalPeriod closeMonthly(String companyCode, FiscalPeriod fp) throws TException;

	/**
	 * 月次更新の取消を行う
	 * 
	 * @param companyCode 会社コード
	 * @param fp 会計期間
	 * @return 月次締め前の会計期間
	 * @throws TException
	 */
	public FiscalPeriod cancelMonthly(String companyCode, FiscalPeriod fp) throws TException;

	/**
	 * 未更新の伝票が残っていないかをチェックする。
	 * 
	 * @param companyCode 会社コード
	 * @param fp 会計期間
	 * @return true(未更新伝票が残っている) / false(未更新伝票が残っていない)
	 * @throws TException
	 */
	public boolean isExistsNotClosedSlip(String companyCode, FiscalPeriod fp) throws TException;

	/**
	 * 伝票種別｢01Z：外貨評価替概算伝票｣が存在するかチェックする。
	 * 
	 * @param companyCode 会社コード
	 * @param fp 会計期間
	 * @return true(外貨評価替概算伝票が存在する) / false(外貨評価替概算伝票が存在しない)
	 * @throws TException
	 */
	public boolean isExistsForeignCurrencyRevaluationSlip(String companyCode, FiscalPeriod fp) throws TException;

	/**
	 * 外貨評価替対象の勘定科目が更新されているかチェックする。
	 * 
	 * @param companyCode 会社コード
	 * @param fp 会計期間
	 * @return true(更新されている) / false(更新されていない)
	 * @throws TException
	 */
	public boolean isUpdateRevaluationSlip(String companyCode, FiscalPeriod fp) throws TException;

	/**
	 * 支払処理が完了していないデータを取得する。
	 * 
	 * @param companyCode 会社コード
	 * @param fp 会計期間
	 * @return 支払処理が完了していないデータ
	 * @throws TException
	 */
	public List<Message> getNotPaidData(String companyCode, FiscalPeriod fp) throws TException;

	/**
	 * 燃料管理情報を取得する<br>
	 * 残油が残っている船・油種区分の伝票日付
	 * 
	 * @return list: 燃料締め情報beanリスト<br>
	 *         vessel_code: 船コード<br>
	 *         oil_type_kbn: 油種区分コード<br>
	 *         den_date: 伝票日付の年月の最終日付
	 * @throws TException
	 */
	public List<BMCloseInfo> getBM() throws TException;

	/**
	 * 燃料管理情報を取得する<br>
	 * 残油が残っている船・油種区分の伝票日付
	 * 
	 * @param fp
	 * @return list: 燃料締め情報beanリスト<br>
	 *         vessel_code: 船コード<br>
	 *         oil_type_kbn: 油種区分コード<br>
	 *         den_date: 伝票日付の年月の最終日付
	 * @throws TException
	 */
	public List<BunkerInfo> getBunker(FiscalPeriod fp) throws TException;

	/****************************************************************************************************************
	 * 以下複数会社版メソッド
	 ****************************************************************************************************************/

	/**
	 * 月次更新データを取得する
	 * 
	 * @param condition 検索条件
	 * @return 月次更新データ
	 * @throws TException
	 */
	public List<MonthData> getMonthData(MonthDataSearchCondition condition) throws TException;

	/**
	 * 伝票未作成のCM_FUND_DTLが存在したらエラー
	 * 
	 * @param companyCode 会社コード
	 * @param fp 会計期間
	 * @return 伝票未作成のCM_FUND_DTLが存在する
	 * @throws TException
	 */
	public boolean isExistsNotSlipByCmFund(String companyCode, FiscalPeriod fp) throws TException;
}
