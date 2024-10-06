package jp.co.ais.trans2.model.management;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.check.*;

/**
 * 管理1マネージャの実装クラスです。
 * @author AIS
 *
 */
public class Management1ManagerImpl extends TModel implements Management1Manager {

	public List<Management1> get(Management1SearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Management1> list = new ArrayList<Management1>();

		try {

			String sql = getSQL(condition);
			
			sql = sql +
				" ORDER BY " +
					" knr1.KNR_CODE_1 ";

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

	/**
	 * 指定条件に該当する管理情報を返す<br>
	 * 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する管理情報
	 * @throws TException
	 */
	public List<Management1> getRef(Management1SearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Management1> list = new ArrayList<Management1>();

		try {

			String sql = getSQL(condition);

			if (condition.getCompanyCodeList() != null && !condition.getCompanyCodeList().isEmpty()) {
				sql += " AND knr1.KAI_CODE IN " + SQLUtil.getInStatement(condition.getCompanyCodeList());
			}

			sql = sql + " ORDER BY knr1.KNR_CODE_1 ";
			sql = sql + "         ,CASE WHEN knr1.KAI_CODE = " + SQLUtil.getParam(getCompanyCode());
			sql = sql + "               THEN 0 ELSE 1 END ";
			sql = sql + "         ,knr1.KAI_CODE ";

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String currentCode = null;

			while (rs.next()) {

				Management1 bean = mapping(rs);

				if (Util.isNullOrEmpty(currentCode)) {
					// 初期化
					currentCode = bean.getCode();
				} else if (Util.equals(currentCode, bean.getCode())) {
					// コードが同じ場合に処理不要
					continue;
				}

				// コード退避
				currentCode = bean.getCode();

				// 戻り値リスト持つ
				list.add(bean);
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
	 * @param condition
	 * @return SQL
	 */
	protected String getSQL(Management1SearchCondition condition) {
		String sql =
			" SELECT " +
				" knr1.KAI_CODE, " +
				" knr1.KNR_CODE_1, " +
				" knr1.KNR_NAME_1, " +
				" knr1.KNR_NAME_S_1, " +
				" knr1.KNR_NAME_K_1, " +
				" knr1.STR_DATE, " +
				" knr1.END_DATE " +
			" FROM " +
				" KNR1_MST knr1 " +
			" WHERE 1 = 1 ";

		// 会社コード
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql = sql + " AND	knr1.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode());
		}

		// コード
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql = sql + " AND	knr1.KNR_CODE_1 = " + SQLUtil.getParam(condition.getCode());
		}

		// コード前方一致
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql = sql + " AND	knr1.KNR_CODE_1 "
				+ SQLUtil.getLikeStatement(condition.getCodeLike(), SQLUtil.CHAR_FRONT);
		}

		// 略称
		if (!Util.isNullOrEmpty(condition.getNamesLike())) {
			sql = sql + " AND	knr1.KNR_NAME_S_1 " + SQLUtil.getLikeStatement(condition.getNamesLike(), SQLUtil.NCHAR_AMBI);
		}

		// 検索名称
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql = sql + " AND	knr1.KNR_NAME_K_1 " + SQLUtil.getLikeStatement(condition.getNamekLike(), SQLUtil.NCHAR_AMBI);
		}

		// 開始コード
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql = sql + " AND	knr1.KNR_CODE_1 >= " + SQLUtil.getParam(condition.getCodeFrom());
		}

		// 終了コード
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql = sql + " AND	knr1.KNR_CODE_1 <= " + SQLUtil.getParam(condition.getCodeTo());
		}

		// コード(複数)
		if (condition.getManagement1CodeList() != null && condition.getManagement1CodeList().length != 0) {
			sql = sql + " AND	knr1.KNR_CODE_1 IN " + SQLUtil.getInStatement(condition.getManagement1CodeList());
		}

		// 有効期間
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql = sql +
				" AND	knr1.STR_DATE <= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm()) +
				" AND	knr1.END_DATE >= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm());
		}
		return sql;
	}

	public void entry(Management1 management1) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
	
			String sql =
				" INSERT INTO KNR1_MST ( " +
					" KAI_CODE, " +
					" KNR_CODE_1, " +
					" KNR_NAME_1, " +
					" KNR_NAME_S_1, " +
					" KNR_NAME_K_1, " +
					" STR_DATE, " +
					" END_DATE, " +
					" INP_DATE, " +
					" UPD_DATE, " +
					" PRG_ID, " +
					" USR_ID " +
				" ) VALUES (" +
					SQLUtil.getParam(management1.getCompanyCode()) + ", " +
					SQLUtil.getParam(management1.getCode()) + ", " +
					SQLUtil.getParam(management1.getName()) + ", " +
					SQLUtil.getParam(management1.getNames()) + ", " +
					SQLUtil.getParam(management1.getNamek()) + ", " +
					SQLUtil.getYYYYMMDDParam(management1.getDateFrom()) + ", " +
					SQLUtil.getYYYYMMDDParam(management1.getDateTo()) + ", " +
					SQLUtil.getYMDHMSParam(getNow()) + ", " +
					" NULL, " +
					SQLUtil.getParam(getProgramCode()) + ", " +
					SQLUtil.getParam(getUserCode()) + 
				" ) ";
	
			DBUtil.execute(conn, sql);
			
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void modify(Management1 management1) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			String sql =
				" UPDATE KNR1_MST " +
				" SET " +
					" KNR_NAME_1 = " + SQLUtil.getParam(management1.getName()) + ", " +
					" KNR_NAME_S_1 = " + SQLUtil.getParam(management1.getNames()) + ", " +
					" KNR_NAME_K_1 = " + SQLUtil.getParam(management1.getNamek()) + ", " +
					" STR_DATE = " + SQLUtil.getYYYYMMDDParam(management1.getDateFrom()) + ", " +
					" END_DATE = " + SQLUtil.getYYYYMMDDParam(management1.getDateTo()) + ", " +
					" UPD_DATE = " + SQLUtil.getYMDHMSParam(getNow()) + ", " +
					" PRG_ID = " + SQLUtil.getParam(getProgramCode()) + ", " +
					" USR_ID = " + SQLUtil.getParam(getUserCode()) + 
			    " WHERE KAI_CODE = " + SQLUtil.getParam(management1.getCompanyCode()) +
				" AND	KNR_CODE_1 = " + SQLUtil.getParam(management1.getCode());
	
			DBUtil.execute(conn, sql);
			
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void delete(Management1 management1) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.MANAGEMENT_1);
		condition.setCompanyCode(management1.getCompanyCode());
		condition.setManagement1Code(management1.getCode());

		// 使用済みチェック
		CheckMasterUseDao cd = (CheckMasterUseDao) getComponent(CheckMasterUseDao.class);
		cd.check(condition);

		Connection conn = DBUtil.getConnection();

		try {
			
			String sql =
			" DELETE FROM KNR1_MST " +
		    " WHERE KAI_CODE = " + SQLUtil.getParam(management1.getCompanyCode()) +
			" AND	KNR_CODE_1 = " + SQLUtil.getParam(management1.getCode());

			DBUtil.execute(conn, sql);
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * O/Rマッピング
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected Management1 mapping(ResultSet rs) throws Exception {

		Management1 bean = new Management1();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("KNR_CODE_1"));
		bean.setName(rs.getString("KNR_NAME_1"));
		bean.setNames(rs.getString("KNR_NAME_S_1"));
		bean.setNamek(rs.getString("KNR_NAME_K_1"));
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));

		return bean;

	}

	/**
	 * 管理1一覧をエクセル形式で返す
	 * @param condition 検索条件
	 * @return エクセル形式の管理1一覧
	 * @throws TException
	 */
	public byte[] getExcel(Management1SearchCondition condition) throws TException {

		List<Management1> list = get(condition);
		if (list == null || list.isEmpty()) {
			return null;
		}

		Management1Excel layout = new Management1Excel(getUser().getLanguage());
		byte[] data = layout.getExcel(condition.getCompany(), list);

		return data;

	}

}
