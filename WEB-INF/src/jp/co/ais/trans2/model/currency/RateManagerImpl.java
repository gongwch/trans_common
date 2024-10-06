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
 * �ʉ݃��[�g���̎����N���X
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

			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add(" AND	rate.KAI_CODE = ? ", condition.getCompanyCode());
			}

			// �ʉ݃R�[�h
			if (!Util.isNullOrEmpty(condition.getCurrencyCode())) {
				sql.add(" AND	rate.CUR_CODE = ? ", condition.getCurrencyCode());
			}

			// �K�p�J�n���t
			if (!Util.isNullOrEmpty(condition.getTermFrom())) {
				sql.add(" AND	rate.STR_DATE = ? ", condition.getTermFrom());
			}

			// �K�p�J�n���t�i�J�n�j
			if (!Util.isNullOrEmpty(condition.getDateFrom())) {
				sql.add(" AND	rate.STR_DATE >= ? ", condition.getDateFrom());
			}

			// �K�p�J�n���t�i�I���j
			if (!Util.isNullOrEmpty(condition.getDateTo())) {
				sql.add(" AND	rate.STR_DATE <= ? ", condition.getDateTo());
			}

			// �ʉ݊J�n�R�[�h
			if (!Util.isNullOrEmpty(condition.getCurrencyCodeFrom())) {
				sql.add(" AND	rate.CUR_CODE >= ? ", condition.getCurrencyCodeFrom());
			}

			// �ʉݏI���R�[�h
			if (!Util.isNullOrEmpty(condition.getCurrencyCodeTo())) {
				sql.add(" AND	rate.CUR_CODE <= ? ", condition.getCurrencyCodeTo());
			}

			// ���[�g�^�C�v
			if (condition.getRateType() != null) {
				sql.add(" AND	rate.RATE_TYPE = ? ", condition.getRateType().value);
			}

			// ���[�g�^�C�v
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
	 * ���[�g�^�C�v�ɕR�t���e�[�u������Ԃ�
	 * 
	 * @param rateType
	 * @return �e�[�u����
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
	 * O/R�}�b�s���O
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
	 * �ʉ݃��[�g�擾
	 * 
	 * @param rate �ʉ݃��[�g
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getRate(Rate rate, Date date) throws TException {
		return getRate(rate.getCompanyCode(), rate.getCurrency().getCode(), date, "RATE_MST");
	}

	/**
	 * �@�\�ʉ݃��[�g�擾
	 * 
	 * @param rate �ʉ݃��[�g
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getFunctionalRate(Rate rate, Date date) throws TException {
		return getRate(rate.getCompanyCode(), rate.getCurrency().getCode(), date, "FNC_RATE_MST");
	}

	/**
	 * �ʉ݃��[�g�i�������Z�j�擾
	 * 
	 * @param rate �ʉ݃��[�g
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getSettlementRate(Rate rate, Date date) throws TException {
		return getRate(rate.getCompanyCode(), rate.getCurrency().getCode(), date, "RATE_KSN_MST");
	}

	/**
	 * �@�\�ʉ݃��[�g�i�������Z�j�擾
	 * 
	 * @param rate �ʉ݃��[�g
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getFunctionalSettlementRate(Rate rate, Date date) throws TException {
		return getRate(rate.getCompanyCode(), rate.getCurrency().getCode(), date, "FNC_RATE_KSN_MST");
	}

	/**
	 * �ʉ݃��[�g�擾
	 * 
	 * @param currency �ʉ�
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getRate(Currency currency, Date date) throws TException {
		return getRate(currency.getCompanyCode(), currency.getCode(), date, "RATE_MST");
	}

	/**
	 * �ʉ݃��[�g�擾
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getRate(String currencyCode, Date date) throws TException {
		return getRate(getCompanyCode(), currencyCode, date, "RATE_MST");
	}

	/**
	 * �@�\�ʉ݃��[�g�擾
	 * 
	 * @param currency �ʉ�
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getFunctionalRate(Currency currency, Date date) throws TException {
		return getRate(currency.getCompanyCode(), currency.getCode(), date, "FNC_RATE_MST");
	}

	/**
	 * �@�\�ʉ݃��[�g�擾
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getFunctionalRate(String currencyCode, Date date) throws TException {
		return getRate(getCompanyCode(), currencyCode, date, "FNC_RATE_MST");
	}

	/**
	 * �@�\�ʉ݃��[�g�i�������Z�j�擾
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getSettlementRate(String currencyCode, Date date) throws TException {
		return getRate(getCompanyCode(), currencyCode, date, "RATE_KSN_MST");
	}

	/**
	 * �ʉ݃��[�g�i�������Z�j�擾
	 * 
	 * @param currency �ʉ�
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getSettlementRate(Currency currency, Date date) throws TException {
		return getSettlementRate(currency.getCode(), date);
	}

	/**
	 * �ʉ݃��[�g�i�������Z�j�擾
	 * 
	 * @param currencyCode
	 * @param date
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getFunctionalSettlementRate(String currencyCode, Date date) throws TException {
		return getRate(getCompanyCode(), currencyCode, date, "FNC_RATE_KSN_MST");
	}

	/**
	 * �@�\�ʉݒʉ݃��[�g�i�������Z�j�擾
	 * 
	 * @param currency �ʉ�
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getFunctionalSettlementRate(Currency currency, Date date) throws TException {
		return getRate(currency.getCompanyCode(), currency.getCode(), date, "FNC_RATE_KSN_MST");
	}

	/**
	 * �ʉ݃��[�g�擾
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param currencyCode �ʉ݃R�[�h
	 * @param date ���
	 * @param tableName �e�[�u��������
	 * @return ���[�g
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
	 * �������ɑ΂���ʉ݃��[�g�擾
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 * @param occurDate    ������
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getRateByOccurDate(String currencyCode, Date occurDate) throws TException {
		return getRateByOccurDate(getCompanyCode(), currencyCode, occurDate, "RATE_MST");
	}

	/**
	 * �������ɑ΂���ʉ݃��[�g�i�������Z�j�擾
	 * 
	 * @param currencyCode �ʉ݃��[�g
	 * @param occurDate    ������
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getSettlementRateByOccurDate(String currencyCode, Date occurDate) throws TException {
		return getRateByOccurDate(getCompanyCode(), currencyCode, occurDate, "RATE_KSN_MST");
	}

	/**
	 * �������ɑ΂���ʉ݃��[�g�擾
	 * 
	 * @param companyCode  ��ЃR�[�h
	 * @param currencyCode �ʉ݃R�[�h
	 * @param occurDate    ������
	 * @param tableName    �e�[�u��������
	 * @return ���[�g
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
	 * �ʉ�CROSS���[�g�擾
	 * 
	 * @param fromCurCode ���ʉ݃R�[�h
	 * @param toCurCode ��ʉ݃R�[�h
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getCrossRate(String fromCurCode, String toCurCode, Date date) throws TException {
		return getCrossRate(getCompanyCode(), fromCurCode, toCurCode, getCompany().getAccountConfig().getKeyCurrency()
			.getCode(), date, "RATE_MST");
	}

	/**
	 * �ʉ�CROSS���[�g�擾
	 * 
	 * @param fromCurrency ���ʉ�
	 * @param toCurrency ��ʉ�
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getCrossRate(Currency fromCurrency, Currency toCurrency, Date date) throws TException {
		return getCrossRate(getCompanyCode(), fromCurrency.getCode(), toCurrency.getCode(), getCompany()
			.getAccountConfig().getKeyCurrency().getCode(), date, "RATE_MST");
	}

	/**
	 * �ʉ�CROSS���[�g�擾
	 * 
	 * @param companyCode
	 * @param fromCurCode ���ʉ݃R�[�h
	 * @param toCurCode ��ʉ݃R�[�h
	 * @param keyCurCode ��ʉ݃R�[�h
	 * @param date ���
	 * @param tableName
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getCrossRate(String companyCode, String fromCurCode, String toCurCode, String keyCurCode,
		Date date, String tableName) throws TException {
		return getCrossRate(companyCode, fromCurCode, toCurCode, keyCurCode, date, tableName, 4);
	}

	/**
	 * �ʉ�CROSS���[�g�擾(���[�g���x��C�ӎw�肷��)
	 * 
	 * @param fromCurCode ���ʉ݃R�[�h
	 * @param toCurCode ��ʉ݃R�[�h
	 * @param date ���
	 * @param digits �����_�ȉ�����
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getCrossRate(String fromCurCode, String toCurCode, Date date, int digits) throws TException {
		return getCrossRate(getCompanyCode(), fromCurCode, toCurCode, getCompany().getAccountConfig().getKeyCurrency()
			.getCode(), date, "RATE_MST", digits);
	}

	/**
	 * �ʉ�CROSS���[�g�擾
	 * 
	 * @param companyCode
	 * @param fromCurCode ���ʉ݃R�[�h
	 * @param toCurCode ��ʉ݃R�[�h
	 * @param keyCurCode ��ʉ݃R�[�h
	 * @param date ���
	 * @param tableName �e�[�u����
	 * @param digits �����_�ȉ�����
	 * @return ���[�g
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

			// FROM ���[�g(POWER�l���ς�)
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

			// TO ���[�g(POWER�l���ς�)
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
	 * SQL�p
	 */
	protected class VCreator extends SQLCreator {

		/**
		 * �R���X�g���N�^�[
		 */
		public VCreator() {
			crlf = " ";
		}
	}

	/**
	 * �G�N�Z����Ԃ�
	 * 
	 * @param condition
	 * @return �G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(RateSearchCondition condition) throws TException {

		try {

			// ��Ѓf�[�^�𒊏o
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
