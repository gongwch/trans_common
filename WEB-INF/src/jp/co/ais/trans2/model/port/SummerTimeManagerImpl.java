package jp.co.ais.trans2.model.port;

import java.io.*;
import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * サマータイムマスタManager実装
 */
public class SummerTimeManagerImpl extends TModel implements SummerTimeManager {

	/**
	 * 取得処理
	 * 
	 * @param condition
	 * @return list
	 */
	public List<OP_SMR_TIME_MST> get(PortSearchCondition condition) throws TException {

		Connection conn = null;
		List<OP_SMR_TIME_MST> list = new ArrayList<OP_SMR_TIME_MST>();
		try {
			conn = DBUtil.getConnection();

			VCreator sql = new VCreator();
			sql.add("SELECT ");
			sql.add("  cm.KAI_CODE ");
			sql.add(" ,smr.YEAR ");
			sql.add(" ,cm.CODE      AS REGION_CODE ");
			sql.add(" ,cm.CODE_NAME AS REGION_NAME ");
			sql.add(" ,smr.TIME_DIFFERENCE ");
			sql.add(" ,smr.FROM_DATE ");
			sql.add(" ,smr.TO_DATE ");
			sql.add(" ,smr.REMARKS ");
			sql.add(" ,smr.INP_DATE ");
			sql.add(" ,smr.UPD_DATE ");
			sql.add(" ,smr.PRG_ID ");
			sql.add(" ,smr.USR_ID ");

			sql.add("FROM OP_CODE_MST cm ");
			sql.add("LEFT JOIN OP_SMR_TIME_MST smr ON smr.KAI_CODE = cm.KAI_CODE ");
			sql.add("                          AND    smr.REGION_CODE = cm.CODE ");
			if (condition.getYEAR() > 0) {
				sql.add("                      AND    smr.YEAR = ? ", condition.getYEAR());
			}

			String companyCode = condition.getCompanyCode();
			if (Util.isNullOrEmpty(companyCode)) {
				companyCode = getCompanyCode();
			}

			sql.add(" WHERE cm.KAI_CODE = ? ", companyCode);
			sql.add(" AND   cm.CODE_DIV = ? ", OPCodeDivision.SMR_TIME_REGION.getValue());

			if (!Util.isNullOrEmpty(condition.getREGION_CODE())) {
				sql.add(" AND   cm.CODE = ? ", condition.getREGION_CODE());
			}

			// 存在しているSUMMER TIMEデータのみ取得
			if (condition.isOnlySummerData()) {
				// INNER JOIN
				sql.add(" AND   smr.KAI_CODE IS NOT NULL ");
			}

			// SUMMER TIME基準日時指定
			if (condition.getSummerTimeBaseDate() != null) {
				sql.add(" AND   smr.FROM_DATE <= ? ", condition.getSummerTimeBaseDate());
				sql.add(" AND   smr.TO_DATE   >= ? ", condition.getSummerTimeBaseDate());
			}

			sql.add(" ORDER BY cm.KAI_CODE");
			sql.add("         ,cm.CODE ");
			sql.add("         ,smr.YEAR DESC");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				OP_SMR_TIME_MST bean = mapping(rs);

				if (bean.getYEAR() == 0) {
					bean.setYEAR(condition.getYEAR());
				}

				list.add(bean);
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
		return list;
	}

	/**
	 * @param rs
	 * @return SummerTime
	 * @throws Exception
	 */
	protected OP_SMR_TIME_MST mapping(ResultSet rs) throws Exception {
		OP_SMR_TIME_MST bean = createOP_SMR_TIME_MST();
		bean.setKAI_CODE(rs.getString("KAI_CODE"));
		bean.setYEAR(rs.getInt("YEAR"));
		bean.setREGION_CODE(rs.getString("REGION_CODE"));
		bean.setREGION_NAME(rs.getString("REGION_NAME"));
		bean.setTIME_DIFFERENCE(rs.getBigDecimal("TIME_DIFFERENCE"));
		bean.setFROM_DATE(rs.getTimestamp("FROM_DATE"));
		bean.setTO_DATE(rs.getTimestamp("TO_DATE"));
		bean.setREMARKS(rs.getString("REMARKS"));
		bean.setINP_DATE(rs.getTimestamp("INP_DATE"));
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE"));
		bean.setPRG_ID(rs.getString("PRG_ID"));
		bean.setUSR_ID(rs.getString("USR_ID"));
		return bean;
	}

	/**
	 * @return BEAN
	 */
	protected OP_SMR_TIME_MST createOP_SMR_TIME_MST() {
		return new OP_SMR_TIME_MST();
	}

	/**
	 * 登録処理
	 * 
	 * @param conn
	 * @param bean
	 * @throws TException
	 */
	protected void entry(Connection conn, OP_SMR_TIME_MST bean) throws TException {
		VCreator sql = new VCreator();
		sql.add("INSERT INTO OP_SMR_TIME_MST");
		sql.add(" (KAI_CODE ");
		sql.add(" ,REGION_CODE ");
		sql.add(" ,YEAR ");
		sql.add(" ,TIME_DIFFERENCE ");
		sql.add(" ,FROM_DATE ");
		sql.add(" ,TO_DATE ");
		sql.add(" ,REMARKS ");
		sql.add(" ,INP_DATE ");
		sql.add(" ,UPD_DATE ");
		sql.add(" ,PRG_ID ");
		sql.add(" ,USR_ID ");
		sql.add(") VALUES (");
		sql.add("  ? ", bean.getKAI_CODE());
		sql.add(" ,? ", bean.getREGION_CODE());
		sql.add(" ,? ", bean.getYEAR());
		sql.add(" ,? ", bean.getTIME_DIFFERENCE());
		sql.adt(" ,? ", bean.getFROM_DATE());
		sql.adt(" ,? ", bean.getTO_DATE());
		sql.add(" ,? ", bean.getREMARKS());
		sql.adt(" ,? ", bean.getINP_DATE());
		sql.add(" ,? ", bean.getUPD_DATE());
		sql.add(" ,? ", bean.getPRG_ID());
		sql.add(" ,?)", bean.getUSR_ID());

		DBUtil.execute(conn, sql.toSQL());
	}

	/**
	 * 削除処理
	 * 
	 * @param conn
	 * @param companyCode
	 * @param year
	 * @param regionCode
	 * @throws TException
	 */
	protected void delete(Connection conn, String companyCode, int year, String regionCode) throws TException {
		VCreator sql = new VCreator();
		sql.add("DELETE FROM OP_SMR_TIME_MST ");
		sql.add(" WHERE KAI_CODE = ? ", companyCode);
		sql.add(" AND   YEAR = ? ", year);

		if (!Util.isNullOrEmpty(regionCode)) {
			sql.add(" AND   REGION_CODE = ? ", regionCode);
		}

		DBUtil.execute(conn, sql.toSQL());
	}

	/**
	 * SQL creator
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
	 * 登録処理
	 * 
	 * @param list
	 * @return 更新後リストデータ
	 * @throws TException
	 */
	public List<OP_SMR_TIME_MST> entry(List<OP_SMR_TIME_MST> list) throws TException {

		Connection conn = null;
		try {
			conn = DBUtil.getConnection();

			for (OP_SMR_TIME_MST bean : list) {
				// 削除処理
				delete(conn, bean.getKAI_CODE(), bean.getYEAR(), bean.getREGION_CODE());

				if (bean.getFROM_DATE() == null && bean.getTO_DATE() == null
					&& DecimalUtil.isNullOrZero(bean.getTIME_DIFFERENCE()) && Util.isNullOrEmpty(bean.getREMARKS())) {
					// ４つはブランクの場合は削除のみ処理しない
					continue;

				} else {
					// 登録

					if (bean.getINP_DATE() == null) {
						bean.setINP_DATE(getNow());
					} else {
						bean.setUPD_DATE(getNow());
					}
					bean.setPRG_ID(getProgramCode());
					bean.setUSR_ID(getUserCode());

					// 登録処理
					entry(conn, bean);
				}
			}

			return list;

		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * エクセル出力
	 * 
	 * @param condition
	 * @return byte[]
	 * @throws TException
	 */
	public byte[] getExcel(PortSearchCondition condition) throws TException {

		List<OP_SMR_TIME_MST> list = get(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}
		SummerTimeExcel excel = new SummerTimeExcel(getUser().getLanguage());
		return excel.getExcel(list);
	}

	/**
	 * エクセルインポート処理
	 * 
	 * @param file
	 * @param condition
	 * @return list 取込後リスト
	 * @throws TErroneousSummerTimeException
	 * @throws TException
	 */
	public List<OP_SMR_TIME_MST> importExcel(File file, PortSearchCondition condition)
		throws TErroneousSummerTimeException, TException {

		SummerTimeConvert impl = get(SummerTimeConvert.class);

		List<OP_SMR_TIME_MST> list = impl.convert(file, condition);
		return list;
	}
}
