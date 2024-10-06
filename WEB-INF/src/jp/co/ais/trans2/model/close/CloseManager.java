package jp.co.ais.trans2.model.close;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 締め管理インターフェース。
 * 
 * @author AIS
 */
public interface CloseManager {

	/**
	 * 指定会社の会計締め情報を返す
	 * 
	 * @param companyCode 会社コード
	 * @return 指定会社の会計締め情報
	 * @throws TException
	 */
	public FiscalPeriod getFiscalPeriod(String companyCode) throws TException;

	/**
	 * 指定日付の年度を返す
	 * 
	 * @param date 日付
	 * @param company 会社情報(期首月がセットされていることが前提)
	 * @return 指定日付の年度
	 */
	public int getFiscalYear(Date date, Company company);

	/**
	 * 指定日付の月度を返す
	 * 
	 * @param date 日付
	 * @param company 会社情報(期首月がセットされていることが前提)
	 * @return 指定日付の月度
	 */
	public int getFiscalMonth(Date date, Company company);

	/**
	 * 指定日付の年度の期首日を返す
	 * 
	 * @param date 日付
	 * @param company 会社情報(期首月がセットされていることが前提)
	 * @return 指定日付の年度の期首日
	 */
	public Date getDateBeginningOfPeriod(Date date, Company company);

	/**
	 * 日次の締めを行う
	 * 
	 * @param date 伝票日付
	 * @param company 会社
	 * @throws TException
	 */
	public void closeDaily(Date date, Company company) throws TException;

	/**
	 * 最後に日次更新した日付を返す
	 * 
	 * @param company
	 * @return 最後に日次更新した日付
	 * @throws TException
	 */
	public Date getLastDailyClosedDate(Company company) throws TException;

	/**
	 * 月次更新する。
	 * 
	 * @param companyCode 会社コード
	 * @param fp 会計期間情報
	 * @return 会計期間情報
	 * @throws TWarningException
	 * @throws TException
	 */
	public FiscalPeriod closeMonthly(String companyCode, FiscalPeriod fp) throws TWarningException, TException;

	/**
	 * 月次更新の取消をする。
	 * 
	 * @param companyCode 会社コード
	 * @param fp 会計期間情報
	 * @return 会計期間情報
	 * @throws TWarningException
	 * @throws TException
	 */
	public FiscalPeriod cancelMonthly(String companyCode, FiscalPeriod fp) throws TWarningException, TException;
	
	/**
	 * 「 REVALUATION_CTL」のSTATUS_KBNを更新する
	 * 
	 * @param date
	 * @param companyCode
	 * @throws TException
	 */
	public void checkStatus(Date date, String companyCode) throws TException;

	/**
	 * 外貨評価替伝票の存在チェック
	 * 
	 * @param companyCode
	 * @return PROC_YM
	 * @throws TException
	 */
	public List<Date> existSlip(String companyCode) throws TException;

	/****************************************************************************************************************
	 * 以下複数会社版メソッド
	 ****************************************************************************************************************/

	/**
	 * 日次更新する。
	 * 
	 * @param baseDate 処理年月日
	 * @param list 月次更新情報
	 * @return 処理後データリスト
	 * @throws TWarningException
	 * @throws TException
	 */
	public List<Message> closeAllDaily(Date baseDate, List<DailyData> list) throws TWarningException, TException;

	/**
	 * 日次更新データを取得する
	 * 
	 * @return 日次更新データ
	 * @throws TException
	 */
	public List<DailyData> getDailyData() throws TException;

	/**
	 * 月次更新する。
	 * 
	 * @param list 月次更新情報
	 * @return list 処理後データリスト
	 * @throws TWarningException
	 * @throws TException
	 */
	public List<Message> closeAllMonthly(List<MonthData> list) throws TWarningException, TException;

	/**
	 * 月次更新データを取得する
	 * 
	 * @param condition 検索条件
	 * @return 月次更新データ
	 * @throws TException
	 */
	public List<MonthData> getMonthData(MonthDataSearchCondition condition) throws TException;

	/**
	 * 月次更新の取消をする。
	 * 
	 * @param list 月次更新情報
	 * @throws TWarningException
	 * @throws TException
	 */
	public void cancelAllMonthly(List<MonthData> list) throws TWarningException, TException;

}
