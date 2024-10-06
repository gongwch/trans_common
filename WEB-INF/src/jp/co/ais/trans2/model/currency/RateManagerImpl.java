package jp.co.ais.trans2.model.currency;

import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * 通貨レート情報の実装クラス
 * 
 * @author AIS
 */
public class RateManagerImpl extends TModel implements RateManager {

	public List<Rate> get(RateSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Rate> list = new ArrayList<Rate>();

		try {

			VCreator sql = new VCreator();
			sql.add("  SELECT  ");
			sql.add("      rate.KAI_CODE ");
			sql.add("     ,rate.CUR_CODE ");
			sql.add("     ,rate.CUR_RATE ");
			sql.add("     ,rate.STR_DATE ");
			sql.add("     ,rate.RATE_TYPE ");
			sql.add("     ,cur.CUR_NAME_S  ");
			sql.add("  FROM RATE_MST_VIEW rate  ");
			sql.add("  LEFT JOIN CUR_MST cur ON rate.KAI_CODE = cur.KAI_CODE  ");
			sql.add("                    AND    rate.CUR_CODE = cur.CUR_CODE  ");
			sql.add("  WHERE 1 = 1  ");

			// 会社コード
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add(" AND	rate.KAI_CODE = ? ", condition.getCompanyCode());
			}

			// 通貨コード
			if (!Util.isNullOrEmpty(condition.getCurrencyCode())) {
				sql.add(" AND	rate.CUR_CODE = ? ", condition.getCurrencyCode());
			}

			// 適用開始日付
			if (!Util.isNullOrEmpty(condition.getTermFrom())) {
				sql.add(" AND	rate.STR_DATE = ? ", condition.getTermFrom());
			}

			// 適用開始日付（開始）
			if (!Util.isNullOrEmpty(condition.getDateFrom())) {
				sql.add(" AND	rate.STR_DATE >= ? ", condition.getDateFrom());
			}

			// 適用開始日付（終了）
			if (!Util.isNullOrEmpty(condition.getDateTo())) {
				sql.add(" AND	rate.STR_DATE <= ? ", condition.getDateTo());
			}

			// 通貨開始コード
			if (!Util.isNullOrEmpty(condition.getCurrencyCodeFrom())) {
				sql.add(" AND	rate.CUR_CODE >= ? ", condition.getCurrencyCodeFrom());
			}

			// 通貨終了コード
			if (!Util.isNullOrEmpty(condition.getCurrencyCodeTo())) {
				sql.add(" AND	rate.CUR_CODE <= ? ", condition.getCurrencyCodeTo());
			}

			// レートタイプ
			if (condition.getRateType() != null) {
				sql.add(" AND	rate.RATE_TYPE = ? ", condition.getRateType().value);
			}

			// レートタイプ
			List<Integer> rateTypeList = new ArrayList<Integer>();

			if (condition.isCompanyRate() || condition.getRateType() == RateType.COMPANY) {
				rateTypeList.add(RateType.COMPANY.value);
			}
			if (condition.isSettlementRate() || condition.getRateType() == RateType.SETTLEMENT) {
				rateTypeList.add(RateType.SETTLEMENT.value);
			}
			if (condition.isFuncCompanyRate() || condition.getRateType() == RateType.FNC_COMPANY) {
				rateTypeList.add(RateType.FNC_COMPANY.value);
			}
			if (condition.isFuncSettlementRate() || condition.getRateType() == RateType.FNC_SETTLEMENT) {
				rateTypeList.add(RateType.FNC_SETTLEMENT.value);
			}

			if (!rateTypeList.isEmpty()) {
				sql.adi(" AND	rate.RATE_TYPE IN ? ", rateTypeList);
			}

			sql.add(" ORDER BY ");
			sql.add("  rate.RATE_TYPE ");
			sql.add(" ,rate.CUR_CODE ");
			sql.add(" ,rate.STR_DATE DESC ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return list;

	}

	public void entry(Rate rate) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			String tableName = getTableName(rate.getRateType());

			VCreator sql = new VCreator();
			sql.add(" INSERT INTO " + tableName + " (  ");
			sql.add("   KAI_CODE ");
			sql.add("  ,CUR_CODE ");
			sql.add("  ,CUR_RATE ");
			sql.add("  ,STR_DATE ");
			sql.add("  ,INP_DATE ");
			sql.add("  ,UPD_DATE ");
			sql.add("  ,PRG_ID ");
			sql.add("  ,USR_ID ");
			sql.add(" ) VALUES ( ");
			sql.add("  ? ", rate.getCompanyCode());
			sql.add(" ,? ", rate.getCurrency().getCode());
			sql.add(" ,? ", rate.getCurrencyRate());
			sql.add(" ,? ", rate.getTermFrom());
			sql.adt(" ,? ", getNow());
			sql.add(" ,NULL ");
			sql.add(" ,? ", getProgramCode());
			sql.add(" ,? ", getUserCode());
			sql.add(" ) ");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void entry(List<Rate> list) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			if (list == null || list.size() == 0) {
				return;
			}

			for (Rate rate : list) {

				String tableName = getTableName(rate.getRateType());

				VCreator sql = new VCreator();
				sql.add(" INSERT INTO " + tableName + " (  ");
				sql.add("   KAI_CODE ");
				sql.add("  ,CUR_CODE ");
				sql.add("  ,CUR_RATE ");
				sql.add("  ,STR_DATE ");
				sql.add("  ,INP_DATE ");
				sql.add("  ,UPD_DATE ");
				sql.add("  ,PRG_ID ");
				sql.add("  ,USR_ID ");
				sql.add(" ) VALUES ( ");
				sql.add("  ? ", rate.getCompanyCode());
				sql.add(" ,? ", rate.getCurrency().getCode());
				sql.add(" ,? ", rate.getCurrencyRate());
				sql.add(" ,? ", rate.getTermFrom());
				sql.adt(" ,? ", getNow());
				sql.add(" ,NULL ");
				sql.add(" ,? ", getProgramCode());
				sql.add(" ,? ", getUserCode());
				sql.add(" ) ");

				DBUtil.execute(conn, sql);
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void modify(Rate rate) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			String tableName = getTableName(rate.getRateType());

			VCreator sql = new VCreator();
			sql.add(" UPDATE " + tableName);
			sql.add(" SET ");
			sql.add("    CUR_RATE = ? ", rate.getCurrencyRate());
			sql.adt("   ,UPD_DATE = ? ", getNow());
			sql.add("   ,PRG_ID = ? ", getProgramCode());
			sql.add("   ,USR_ID = ? ", getUserCode());
			sql.add(" WHERE KAI_CODE = ? ", rate.getCompanyCode());
			sql.add(" AND	CUR_CODE = ? ", rate.getCurrency().getCode());
			sql.add(" AND	STR_DATE = ? ", rate.getTermFrom());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void delete(Rate rate) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			String tableName = getTableName(rate.getRateType());

			VCreator sql = new VCreator();
			sql.add(" DELETE FROM " + tableName);
			sql.add(" WHERE KAI_CODE = ? ", rate.getCompanyCode());
			sql.add(" AND	CUR_CODE = ? ", rate.getCurrency().getCode());
			sql.add(" AND	STR_DATE = ? ", rate.getTermFrom());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * レートタイプに紐付くテーブル名を返す
	 * 
	 * @param rateType
	 * @return テーブル名
	 */
	protected String getTableName(RateType rateType) {

		String tableName = null;
		if (RateType.COMPANY == rateType) {
			tableName = "RATE_MST";
		} else if (RateType.SETTLEMENT == rateType) {
			tableName = "RATE_KSN_MST";
		} else if (RateType.FNC_COMPANY == rateType) {
			tableName = "FNC_RATE_MST";
		} else if (RateType.FNC_SETTLEMENT == rateType) {
			tableName = "FNC_RATE_KSN_MST";
		}
		return tableName;
	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return rate
	 * @throws Exception
	 */
	protected Rate mapping(ResultSet rs) throws Exception {

		Rate rate = new Rate();

		rate.setCompanyCode(rs.getString("KAI_CODE"));
		rate.setRateType(RateType.getRateType(rs.getInt("RATE_TYPE")));
		rate.setCurrencyRate(rs.getBigDecimal("CUR_RATE"));
		rate.setTermFrom(rs.getDate("STR_DATE"));

		Currency currency = new Currency();
		currency.setCode(rs.getString("CUR_CODE"));
		currency.setNames(rs.getString("CUR_NAME_S"));
		rate.setCurrency(currency);

		return rate;
	}

	/**
	 * 通貨レート取得
	 * 
	 * @param rate 通貨レート
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getRate(Rate rate, Date date) throws TException {
		return getRate(rate.getCompanyCode(), rate.getCurrency().getCode(), date, "RATE_MST");
	}

	/**
	 * 機能通貨レート取得
	 * 
	 * @param rate 通貨レート
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getFunctionalRate(Rate rate, Date date) throws TException {
		return getRate(rate.getCompanyCode(), rate.getCurrency().getCode(), date, "FNC_RATE_MST");
	}

	/**
	 * 通貨レート（期末決算）取得
	 * 
	 * @param rate 通貨レート
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getSettlementRate(Rate rate, Date date) throws TException {
		return getRate(rate.getCompanyCode(), rate.getCurrency().getCode(), date, "RATE_KSN_MST");
	}

	/**
	 * 機能通貨レート（期末決算）取得
	 * 
	 * @param rate 通貨レート
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getFunctionalSettlementRate(Rate rate, Date date) throws TException {
		return getRate(rate.getCompanyCode(), rate.getCurrency().getCode(), date, "FNC_RATE_KSN_MST");
	}

	/**
	 * 通貨レート取得
	 * 
	 * @param currency 通貨
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getRate(Currency currency, Date date) throws TException {
		return getRate(currency.getCompanyCode(), currency.getCode(), date, "RATE_MST");
	}

	/**
	 * 通貨レート取得
	 * 
	 * @param currencyCode 通貨コード
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getRate(String currencyCode, Date date) throws TException {
		return getRate(getCompanyCode(), currencyCode, date, "RATE_MST");
	}

	/**
	 * 機能通貨レート取得
	 * 
	 * @param currency 通貨
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getFunctionalRate(Currency currency, Date date) throws TException {
		return getRate(currency.getCompanyCode(), currency.getCode(), date, "FNC_RATE_MST");
	}

	/**
	 * 機能通貨レート取得
	 * 
	 * @param currencyCode 通貨コード
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getFunctionalRate(String currencyCode, Date date) throws TException {
		return getRate(getCompanyCode(), currencyCode, date, "FNC_RATE_MST");
	}

	/**
	 * 機能通貨レート（期末決算）取得
	 * 
	 * @param currencyCode 通貨コード
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getSettlementRate(String currencyCode, Date date) throws TException {
		return getRate(getCompanyCode(), currencyCode, date, "RATE_KSN_MST");
	}

	/**
	 * 通貨レート（期末決算）取得
	 * 
	 * @param currency 通貨
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getSettlementRate(Currency currency, Date date) throws TException {
		return getSettlementRate(currency.getCode(), date);
	}

	/**
	 * 通貨レート（期末決算）取得
	 * 
	 * @param currencyCode
	 * @param date
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getFunctionalSettlementRate(String currencyCode, Date date) throws TException {
		return getRate(getCompanyCode(), currencyCode, date, "FNC_RATE_KSN_MST");
	}

	/**
	 * 機能通貨通貨レート（期末決算）取得
	 * 
	 * @param currency 通貨
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getFunctionalSettlementRate(Currency currency, Date date) throws TException {
		return getRate(currency.getCompanyCode(), currency.getCode(), date, "FNC_RATE_KSN_MST");
	}

	/**
	 * 通貨レート取得
	 * 
	 * @param companyCode 会社コード
	 * @param currencyCode 通貨コード
	 * @param date 基準日
	 * @param tableName テーブル物理名
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getRate(String companyCode, String currencyCode, Date date, String tableName) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();

			sql.add(" SELECT ");
			sql.add("     CUR_RATE ");
			sql.add(" FROM " + tableName);
			sql.add(" WHERE KAI_CODE = ? ", companyCode);
			sql.add("   AND CUR_CODE = ? ", currencyCode);
			sql.add("   AND STR_DATE = ( ");
			sql.add("     SELECT MAX(STR_DATE) FROM " + tableName);
			sql.add("         WHERE KAI_CODE = ? ", companyCode);
			sql.add("           AND CUR_CODE = ? ", currencyCode);
			sql.add("           AND STR_DATE <= ? ", date);
			sql.add("   )");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			BigDecimal ret = null;
			if (rs.next()) {
				ret = rs.getBigDecimal("CUR_RATE");
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

			return ret;

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 発生日に対する通貨レート取得
	 * 
	 * @param currencyCode 通貨コード
	 * @param occurDate    発生日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getRateByOccurDate(String currencyCode, Date occurDate) throws TException {
		return getRateByOccurDate(getCompanyCode(), currencyCode, occurDate, "RATE_MST");
	}

	/**
	 * 発生日に対する通貨レート（期末決算）取得
	 * 
	 * @param currencyCode 通貨レート
	 * @param occurDate    発生日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getSettlementRateByOccurDate(String currencyCode, Date occurDate) throws TException {
		return getRateByOccurDate(getCompanyCode(), currencyCode, occurDate, "RATE_KSN_MST");
	}

	/**
	 * 発生日に対する通貨レート取得
	 * 
	 * @param companyCode  会社コード
	 * @param currencyCode 通貨コード
	 * @param occurDate    発生日
	 * @param tableName    テーブル物理名
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getRateByOccurDate(String companyCode, String currencyCode, Date occurDate, String tableName)
			throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();

			sql.add(" SELECT ");
			sql.add("     CUR_RATE ");
			sql.add(" FROM " + tableName);
			sql.add(" WHERE KAI_CODE = ? ", companyCode);
			sql.add("   AND CUR_CODE = ? ", currencyCode);
			sql.add("   AND STR_DATE >= ? ", DateUtil.getFirstDate(occurDate));
			sql.add("   AND STR_DATE <= ? ", occurDate);

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			BigDecimal rate = null;
			if (rs.next()) {
				rate = rs.getBigDecimal("CUR_RATE");
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

			return rate;

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 通貨CROSSレート取得
	 * 
	 * @param fromCurCode 元通貨コード
	 * @param toCurCode 先通貨コード
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getCrossRate(String fromCurCode, String toCurCode, Date date) throws TException {
		return getCrossRate(getCompanyCode(), fromCurCode, toCurCode, getCompany().getAccountConfig().getKeyCurrency()
			.getCode(), date, "RATE_MST");
	}

	/**
	 * 通貨CROSSレート取得
	 * 
	 * @param fromCurrency 元通貨
	 * @param toCurrency 先通貨
	 * @param date 基準日
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getCrossRate(Currency fromCurrency, Currency toCurrency, Date date) throws TException {
		return getCrossRate(getCompanyCode(), fromCurrency.getCode(), toCurrency.getCode(), getCompany()
			.getAccountConfig().getKeyCurrency().getCode(), date, "RATE_MST");
	}

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
		Date date, String tableName) throws TException {
		return getCrossRate(companyCode, fromCurCode, toCurCode, keyCurCode, date, tableName, 4);
	}

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
	public BigDecimal getCrossRate(String fromCurCode, String toCurCode, Date date, int digits) throws TException {
		return getCrossRate(getCompanyCode(), fromCurCode, toCurCode, getCompany().getAccountConfig().getKeyCurrency()
			.getCode(), date, "RATE_MST", digits);
	}

	/**
	 * 通貨CROSSレート取得
	 * 
	 * @param companyCode
	 * @param fromCurCode 元通貨コード
	 * @param toCurCode 先通貨コード
	 * @param keyCurCode 基軸通貨コード
	 * @param date 基準日
	 * @param tableName テーブル名
	 * @param digits 小数点以下桁数
	 * @return レート
	 * @throws TException
	 */
	public BigDecimal getCrossRate(String companyCode, String fromCurCode, String toCurCode, String keyCurCode,
		Date date, String tableName, int digits) throws TException {

		if (Util.equals(fromCurCode, toCurCode)) {
			return BigDecimal.ONE;
		}

		BigDecimal fromRate = null;
		BigDecimal toRate = null;

		Connection conn = DBUtil.getConnection();

		try {

			// FROM レート(POWER考慮済み)
			if (Util.equals(fromCurCode, keyCurCode)) {
				fromRate = BigDecimal.ONE;
			} else {
				VCreator sql = new VCreator();

				sql.add(" SELECT rate.CUR_RATE * POWER(10, NVL(cur.RATE_POW, 0)) AS CUR_RATE ");
				sql.add(" FROM " + tableName + " rate ");
				sql.add(" INNER  JOIN CUR_MST cur ON cur.KAI_CODE = ? ", companyCode);
				sql.add("                     AND    cur.CUR_CODE = rate.CUR_CODE ");
				sql.add(" WHERE rate.KAI_CODE = ? ", companyCode);
				sql.add("   AND rate.CUR_CODE = ? ", fromCurCode);
				sql.add("   AND rate.STR_DATE = ( ");
				sql.add("     SELECT MAX(STR_DATE) FROM " + tableName);
				sql.add("         WHERE KAI_CODE = ? ", companyCode);
				sql.add("           AND CUR_CODE = ? ", fromCurCode);
				sql.add("           AND STR_DATE <= ? ", date);
				sql.add("   )");

				Statement statement = DBUtil.getStatement(conn);
				ResultSet rs = DBUtil.select(statement, sql);

				if (rs.next()) {
					fromRate = rs.getBigDecimal("CUR_RATE");
				}

				DBUtil.close(rs);
				DBUtil.close(statement);

			}

			if (DecimalUtil.isNullOrZero(fromRate)) {
				return null;
			}

			// TO レート(POWER考慮済み)
			if (Util.equals(toCurCode, keyCurCode)) {
				toRate = BigDecimal.ONE;
			} else {
				VCreator sql = new VCreator();

				sql.add(" SELECT rate.CUR_RATE * POWER(10, NVL(cur.RATE_POW, 0)) AS CUR_RATE ");
				sql.add(" FROM " + tableName + " rate ");
				sql.add(" INNER  JOIN CUR_MST cur ON cur.KAI_CODE = ? ", companyCode);
				sql.add("                     AND    cur.CUR_CODE = rate.CUR_CODE ");
				sql.add(" WHERE rate.KAI_CODE = ? ", companyCode);
				sql.add("   AND rate.CUR_CODE = ? ", toCurCode);
				sql.add("   AND rate.STR_DATE = ( ");
				sql.add("     SELECT MAX(STR_DATE) FROM " + tableName);
				sql.add("         WHERE KAI_CODE = ? ", companyCode);
				sql.add("           AND CUR_CODE = ? ", toCurCode);
				sql.add("           AND STR_DATE <= ? ", date);
				sql.add("   )");

				Statement statement = DBUtil.getStatement(conn);
				ResultSet rs = DBUtil.select(statement, sql);

				if (rs.next()) {
					toRate = rs.getBigDecimal("CUR_RATE");
				}

				DBUtil.close(rs);
				DBUtil.close(statement);
			}

			if (DecimalUtil.isNullOrZero(toRate)) {
				return null;
			}

			// TO/FROM
			return DecimalUtil.avoidNull(toRate).divide(fromRate, digits, RoundingMode.HALF_UP);

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * SQL用
	 */
	protected class VCreator extends SQLCreator {

		/**
		 * コンストラクター
		 */
		public VCreator() {
			crlf = " ";
		}
	}

	/**
	 * エクセルを返す
	 * 
	 * @param condition
	 * @return エクセル
	 * @throws TException
	 */
	public byte[] getExcel(RateSearchCondition condition) throws TException {

		try {

			// 会社データを抽出
			List<Rate> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			RateExcel exl = new RateExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}

	}

}
