package jp.co.ais.trans2.model.close;

import java.util.*;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.model.company.Company;

/**
 * 日次締め処理
 * 
 * @author AIS
 */
public interface DailyClose {

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
	 * @return Date
	 * @throws TException
	 */
	public Date getLastDailyClosedDate(Company company) throws TException;

	/**
	 * 日次更新データを取得する（複数会社版）
	 * 
	 * @return 日次更新データ
	 * @throws TException
	 */
	public List<DailyData> getDailyData() throws TException;

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
}
