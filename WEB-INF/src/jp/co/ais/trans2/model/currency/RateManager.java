package jp.co.ais.trans2.model.currency;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 通貨レート情報インターフェース。
 * 
 * @author AIS
 */
public interface RateManager {

	/**
	 * 指定条件に該当する通貨レート情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する通貨情報
	 * @throws TException
	 */
	public List<Rate> get(RateSearchCondition condition) throws TException;

	/**
	 * 通貨レート情報を登録する。
	 * 
	 * @param rate
	 * @throws TException
	 */
	public void entry(Rate rate) throws TException;

	/**
	 * 通貨レート情報を登録する。
	 * 
	 * @param rate
	 * @throws TException
	 */
	public void entry(List<Rate> rate) throws TException;

	/**
	 * 通貨レート情報を修正する。
	 * 
	 * @param rate
	 * @throws TException
	 */
	public void modify(Rate rate) throws TException;

	/**
	 * 通貨レート情報を削除する。
	 * 
	 * @param rate
	 * @throws TException
	 */
	public void delete(Rate rate) throws TException;

	/**
	 * 会社設定
	 * 
	 * @param company 会社
	 */
	public void setCompany(Company company);

	/**
	 * 通貨レート取得
	 * 
	 * @param currencyCode 通貨コード
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getRate(String currencyCode, Date date) throws TException;

	/**
	 * 通貨レート取得
	 * 
	 * @param currency 通貨
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getRate(Currency currency, Date date) throws TException;

	/**
	 * 通貨レート取得
	 * 
	 * @param rate 通貨レート
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getRate(Rate rate, Date date) throws TException;

	/**
	 * 通貨レート取得
	 * 
	 * @param companyCode
	 * @param currencyCode
	 * @param date
	 * @param tableName
	 * @return ret
	 * @throws TException
	 */
	public BigDecimal getRate(String companyCode, String currencyCode, Date date, String tableName) throws TException;

	/**
	 * 機能通貨レート取得
	 * 
	 * @param currency 通貨
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getFunctionalRate(Currency currency, Date date) throws TException;

	/**
	 * 機能通貨レート取得
	 * 
	 * @param currencyCode 通貨コード
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getFunctionalRate(String currencyCode, Date date) throws TException;

	/**
	 * 機能通貨レート取得
	 * 
	 * @param rate 通貨レート
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getFunctionalRate(Rate rate, Date date) throws TException;

	/**
	 * 通貨レート（期末決算）取得
	 * 
	 * @param currencyCode 通貨コード
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getSettlementRate(String currencyCode, Date date) throws TException;

	/**
	 * 通貨レート（期末決算）取得
	 * 
	 * @param currency 通貨
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getSettlementRate(Currency currency, Date date) throws TException;

	/**
	 * 通貨レート（期末決算）取得
	 * 
	 * @param rate 通貨レート
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getSettlementRate(Rate rate, Date date) throws TException;

	/**
	 * 機能通貨通貨レート（期末決算）取得
	 * 
	 * @param currencyCode 通貨コード
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getFunctionalSettlementRate(String currencyCode, Date date) throws TException;

	/**
	 * 機能通貨通貨レート（期末決算）取得
	 * 
	 * @param rate 通貨レート
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getFunctionalSettlementRate(Rate rate, Date date) throws TException;

	/**
	 * 機能通貨通貨レート（期末決算）取得
	 * 
	 * @param currency 通貨
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getFunctionalSettlementRate(Currency currency, Date date) throws TException;

	/**
	 * 通貨CROSSレート取得
	 * 
	 * @param fromCurCode 元通貨コード
	 * @param toCurCode 先通貨コード
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getCrossRate(String fromCurCode, String toCurCode, Date date) throws TException;

	/**
	 * 通貨CROSSレート取得
	 * 
	 * @param fromCurrency 元通貨
	 * @param toCurrency 先通貨
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getCrossRate(Currency fromCurrency, Currency toCurrency, Date date) throws TException;

	/**
	 * 通貨CROSSレート取得(レート精度を任意指定する)
	 * 
	 * @param fromCurCode 元通貨コード
	 * @param toCurCode 先通貨コード
	 * @param date 基準日
	 * @param digits 小数点以下桁数
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getCrossRate(String fromCurCode, String toCurCode, Date date, int digits) throws TException;

	/**
	 * 通貨CROSSレート取得
	 * 
	 * @param companyCode
	 * @param fromCurCode 元通貨コード
	 * @param toCurCode 先通貨コード
	 * @param keyCurCode 基軸通貨コード
	 * @param date 基準日
	 * @param tableName
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getCrossRate(String companyCode, String fromCurCode, String toCurCode, String keyCurCode,
		Date date, String tableName) throws TException;

	/**
	 * 発生日に対する通貨レート取得
	 * 
	 * @param currencyCode 通貨コード
	 * @param occurDate    発生日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getRateByOccurDate(String currencyCode, Date occurDate) throws TException;

	/**
	 * 発生日に対する通貨レート（期末決算）取得
	 * 
	 * @param currencyCode 通貨レート
	 * @param occurDate 発生日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getSettlementRateByOccurDate(String currencyCode, Date occurDate) throws TException;

	/**
	 * エクセルファイルを作成する
	 * 
	 * @param condition
	 * @return エクセルファイル
	 * @throws TException
	 */
	public byte[] getExcel(RateSearchCondition condition) throws TException;
}