package jp.co.ais.trans2.model.remittance;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * 送金目的マネージャの実装クラス
 * 
 * @author AIS
 */
public class RemittanceManagerImpl extends TModel implements RemittanceManager {

	/**
	 * 指定条件に該当する送金目的情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する摘要情報
	 * @throws TException
	 */
	public List<Remittance> get(RemittanceSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Remittance> list = new ArrayList<Remittance>();

		try {

			SQLCreator s = new SQLCreator();
			s.add("SELECT ");
			s.add("  MKT_CODE, ");
			s.add("  MKT_NAME ");
			s.add("FROM AP_MKT_MST ");
			s.add("WHERE   1 = 1 ");

			// コード
			if (!Util.isNullOrEmpty(condition.getCode())) {
				s.add(" AND   MKT_CODE = ?", condition.getCode());
			}
			// コード前方一致
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				s.addLikeFront(" AND   MKT_CODE  ?", condition.getCodeLike());
			}
			// コードfrom
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				s.add(" AND MKT_CODE >= ?", condition.getCodeFrom());
			}
			// コードto
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				s.add(" AND MKT_CODE <= ?", condition.getCodeTo());
			}
			// 名称
			if (!Util.isNullOrEmpty(condition.getNameLike())) {
				s.addNLikeAmbi(" AND   MKT_NAME  ?", condition.getNameLike());
			}

			s.add("ORDER BY MKT_CODE");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, s);

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

	/**
	 * 送金目的登録
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entry(Remittance bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator s = new SQLCreator();
			s.add("INSERT INTO AP_MKT_MST( ");
			s.add("   MKT_CODE, ");
			s.add("   MKT_NAME ");
			s.add(") VALUES (");
			s.add("   ? ,", bean.getCode());
			s.add("   ? ", bean.getName());
			s.add(") ");

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 修正
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void modify(Remittance bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator s = new SQLCreator();
			s.add("UPDATE   AP_MKT_MST");
			s.add(" SET ");
			s.add("   MKT_CODE = ? ", bean.getCode());
			s.add("  ,MKT_NAME = ? ", bean.getName());
			s.add("WHERE MKT_CODE = ? ", bean.getCode());

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 削除
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(Remittance bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator s = new SQLCreator();
			s.add("DELETE ");
			s.add("FROM AP_MKT_MST ");
			s.add("WHERE MKT_CODE = ? ", bean.getCode());

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return Remittance
	 * @throws Exception
	 */
	protected Remittance mapping(ResultSet rs) throws Exception {

		Remittance bean = new Remittance();
		bean.setCode(rs.getString("MKT_CODE"));
		bean.setName(rs.getString("MKT_NAME"));

		return bean;
	}

	/**
	 * 送金目的一覧をエクセル形式で返す
	 * 
	 * @param condition
	 * @return エクセルバイナリ
	 * @throws TException
	 */
	public byte[] getExcel(RemittanceSearchCondition condition) throws TException {

		try {

			// データを抽出
			List<Remittance> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			BalanceAccountsExcel exl = new BalanceAccountsExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	public List<Remittance> getRemittance(RemittanceSearchCondition condition) throws TException {
		Connection conn = DBUtil.getConnection();

		List<Remittance> list = new ArrayList<Remittance>();
		try {

			SQLCreator sql = new SQLCreator();

			sql.add("SELECT rmt.KAI_CODE ");
			sql.add("      ,rmt.RMT_CODE ");
			sql.add("      ,rmt.MKT_CODE ");
			sql.add("      ,mkt.MKT_NAME ");
			sql.add("      ,rmt.RMT_NAME ");
			sql.add("      ,rmt.INP_DATE ");
			sql.add("FROM AP_RMT_MST rmt");
			sql.add("LEFT JOIN AP_MKT_MST mkt ON rmt.MKT_CODE = mkt.MKT_CODE");

			sql.add("WHERE 1 = 1 ");

			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add(" AND rmt.KAI_CODE = ? ", condition.getCompanyCode());
			}
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add(" AND rmt.RMT_CODE = ? ", condition.getCode());
			}
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add(" AND rmt.RMT_CODE >= ?", condition.getCodeFrom());
			}
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add(" AND rmt.RMT_CODE <= ?", condition.getCodeTo());
			}
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.addLikeFront(" AND rmt.RMT_CODE ? ", condition.getCodeLike());
			}
			if (!Util.isNullOrEmpty(condition.getBalanceCode())) {
				sql.add(" AND rmt.MKT_CODE = ? ", condition.getBalanceCode());
			}
			if (!Util.isNullOrEmpty(condition.getNameLike())) {
				sql.addNLikeAmbi(" AND rmt.MKT_CODE ? ", condition.getNameLike());
			}
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql.addNLikeAmbi(" AND rmt.RMT_NAME ? ", condition.getNamekLike());
			}

			sql.add("ORDER BY ");
			sql.add("  rmt.KAI_CODE ");
			sql.add(" ,rmt.RMT_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingRemittance(rs));
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

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return entity
	 * @throws SQLException
	 */
	protected Remittance mappingRemittance(ResultSet rs) throws SQLException {
		Remittance entity = new Remittance();

		entity.setCompanyCode(rs.getString("KAI_CODE"));
		entity.setCode(rs.getString("RMT_CODE"));
		entity.setBalanceCode(rs.getString("MKT_CODE"));
		entity.setBalanceName(rs.getString("MKT_NAME"));
		entity.setName(rs.getString("RMT_NAME"));
		entity.setInpDate(rs.getTimestamp("INP_DATE"));

		return entity;
	}

	/**
	 * 送金目的登録
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryRemittance(Remittance bean) throws TException {
		bean.setInpDate(getNow());
		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator s = new SQLCreator();
			s.add("INSERT INTO AP_RMT_MST (");
			s.add("   KAI_CODE ");
			s.add("  ,RMT_CODE ");
			s.add("  ,MKT_CODE ");
			s.add("  ,RMT_NAME ");
			s.add("  ,INP_DATE ");
			s.add("  ,UPD_DATE ");
			s.add("  ,PRG_ID ");
			s.add("  ,USR_ID ");

			s.add(") VALUES (");
			s.add("   ? ", bean.getCompanyCode());
			s.add("  ,? ", bean.getCode());
			s.add("  ,? ", bean.getBalanceCode());
			s.add("  ,? ", bean.getName());
			s.addYMDHMS(" ,? ", bean.getInpDate());
			s.addYMDHMS(" ,? ", null);
			s.add("  ,? ", getProgramCode());
			s.add("  ,? ", getUserCode());
			s.add(")");

			DBUtil.execute(conn, s);

		} catch (TException e) {
			throw e;

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 送金目的修正
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void modifyRemittance(Remittance bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator s = new SQLCreator();

			s.add("DELETE FROM AP_RMT_MST");
			s.add(" WHERE KAI_CODE = ? ", bean.getCompanyCode());
			s.add(" AND   RMT_CODE = ? ", bean.getCode());

			DBUtil.execute(s);

			s = new SQLCreator();

			s.add("INSERT INTO AP_RMT_MST (");
			s.add("   KAI_CODE ");
			s.add("  ,RMT_CODE ");
			s.add("  ,MKT_CODE ");
			s.add("  ,RMT_NAME ");
			s.add("  ,INP_DATE ");
			s.add("  ,UPD_DATE ");
			s.add("  ,PRG_ID ");
			s.add("  ,USR_ID ");

			s.add(") VALUES (");
			s.add("   ? ", bean.getCompanyCode());
			s.add("  ,? ", bean.getCode());
			s.add("  ,? ", bean.getBalanceCode());
			s.add("  ,? ", bean.getName());
			if (Util.isNullOrEmpty(bean.getInpDate())) {
				s.addYMDHMS("  ,?", getNow());
				s.addYMDHMS("  ,?", null);
			} else {
				s.addYMDHMS("  ,?", bean.getInpDate());
				s.addYMDHMS("  ,?", getNow());
			}
			s.add("  ,?", getProgramCode());
			s.add("  ,?", getUserCode());
			s.add(")");

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 送金目的削除
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void deleteRemittance(Remittance bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator s = new SQLCreator();

			s.add("DELETE FROM AP_RMT_MST");
			s.add(" WHERE KAI_CODE = ? ", bean.getCompanyCode());
			s.add(" AND   RMT_CODE = ? ", bean.getCode());

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 送金目的一覧をエクセル形式で返す
	 * 
	 * @param condition
	 * @return エクセルバイナリ
	 * @throws TException
	 */
	public byte[] getExcelRemittance(RemittanceSearchCondition condition) throws TException {

		try {

			// データを抽出
			List<Remittance> list = getRemittance(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			RemittancePurposeExcel exl = new RemittancePurposeExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * 送金目的一覧をエクセル形式で返す
	 * 
	 * @param condition
	 * @return エクセルバイナリ
	 * @throws TException
	 */
	public byte[] getExcelRemittancePurpose(RemittanceSearchCondition condition) throws TException {

		try {

			// データを抽出
			List<Remittance> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			RemittancePurposeOldExcel exl = new RemittancePurposeOldExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}
}
