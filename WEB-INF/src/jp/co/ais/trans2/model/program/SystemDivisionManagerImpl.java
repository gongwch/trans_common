package jp.co.ais.trans2.model.program;

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
 * 指定条件に該当するシステム区分情報を返す。
 * 
 * @author AIS
 */
public class SystemDivisionManagerImpl extends TModel implements SystemDivisionManager {

	public List<SystemDivision> get(SystemDivisionSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<SystemDivision> list = new ArrayList<SystemDivision>();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("SELECT ");
			sql.add("   KAI_CODE");
			sql.add("  ,SYS_KBN");
			sql.add("  ,SYS_KBN_NAME");
			sql.add("  ,SYS_KBN_NAME_S");
			sql.add("  ,SYS_KBN_NAME_K");
			sql.add("  ,OSY_KBN");
			sql.add("  ,INP_DATE");
			sql.add("  ,UPD_DATE");
			sql.add("  ,PRG_ID");
			sql.add("  ,USR_ID");
			sql.add(" FROM SYS_MST sys");
			sql.add(" WHERE 1 = 1");

			// 会社コード
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add(" AND	sys.KAI_CODE = ? ", condition.getCompanyCode());
			}

			// コード
			if (!condition.getCodeList().isEmpty()) {
				sql.add(" AND	sys.SYS_KBN IN ? ", condition.getCodeList());
			}

			// 開始コード
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add(" AND	sys.SYS_KBN >= ? ", condition.getCodeFrom());
			}

			// 終了コード
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add(" AND	sys.SYS_KBN <= ? ", condition.getCodeTo());
			}

			// コード前方一致
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.addLikeFront(" AND	sys.SYS_KBN ? ", condition.getCodeLike());
			}

			// 略称あいまい
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				sql.addNLikeAmbi(" AND	sys.SYS_KBN_NAME_S ? ", condition.getNamesLike());
			}

			// 検索名称あいまい
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql.addNLikeAmbi(" AND	sys.SYS_KBN_NAME_K ? ", condition.getNamekLike());
			}

			sql.add(" ORDER BY ");
			sql.add("  sys.SYS_KBN ");

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
	 * 特定のシステム区分情報を返す。
	 * 
	 * @param companyCode 会社コード
	 * @param SystemDivisionCode システム区分コード
	 * @return 特定のシステム区分情報
	 * @throws TException
	 */
	public SystemDivision get(String companyCode, String SystemDivisionCode) throws TException {

		SystemDivisionSearchCondition condition = new SystemDivisionSearchCondition();
		condition.setCompanyCode(companyCode);
		condition.setCode(SystemDivisionCode);

		List<SystemDivision> list = get(condition);
		if (list == null || list.isEmpty()) {
			return null;
		}

		return list.get(0);

	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return システム区分
	 * @throws Exception
	 */
	protected SystemDivision mapping(ResultSet rs) throws Exception {
		SystemDivision bean = new SystemDivision();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("SYS_KBN"));
		bean.setName(rs.getString("SYS_KBN_NAME"));
		bean.setNames(rs.getString("SYS_KBN_NAME_S"));
		bean.setNamek(rs.getString("SYS_KBN_NAME_K"));
		bean.setOuterSystemType(OuterSystemType.get(rs.getInt("OSY_KBN")));

		return bean;
	}

	/**
	 * データ登録処理
	 * 
	 * @param bean 登録データ
	 * @throws TException
	 */
	public void entry(SystemDivision bean) throws TException {
		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();
			sql.add("");
			sql.add("INSERT INTO SYS_MST (");
			sql.add("   KAI_CODE");
			sql.add("  ,SYS_KBN");
			sql.add("  ,SYS_KBN_NAME");
			sql.add("  ,SYS_KBN_NAME_S");
			sql.add("  ,SYS_KBN_NAME_K");
			sql.add("  ,OSY_KBN");
			sql.add("  ,INP_DATE");
			sql.add("  ,PRG_ID");
			sql.add("  ,USR_ID");
			sql.add(") VALUES (");
			sql.add("    ?,", getCompanyCode());
			sql.add("    ?,", bean.getCode());
			sql.add("    ?,", bean.getName());
			sql.add("    ?,", bean.getNames());
			sql.add("    ?,", bean.getNamek());
			sql.add("    ?,", bean.getOuterSystemType().value);
			sql.adt("    ?,", getNow());
			sql.add("    ?,", getProgramCode());
			sql.add("    ?", getUserCode());
			sql.add(")");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * データ更新処理
	 * 
	 * @param bean 更新データ
	 */
	public void modify(SystemDivision bean) throws TException {

		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("");
			sql.add("UPDATE");
			sql.add("    SYS_MST");
			sql.add("SET");
			sql.add("    SYS_KBN_NAME = ?", bean.getName());
			sql.add("    ,SYS_KBN_NAME_S = ?", bean.getNames());
			sql.add("    ,SYS_KBN_NAME_K = ?", bean.getNamek());
			sql.add("    ,OSY_KBN = ?", bean.getOuterSystemType().value);
			sql.adt("    ,UPD_DATE = ?", getNow());
			sql.add("    ,PRG_ID = ?", getProgramCode());
			sql.add("    ,USR_ID = ?", getUserCode());
			sql.add("WHERE");
			sql.add("    KAI_CODE = ?", getCompanyCode());
			sql.add("AND");
			sql.add("    SYS_KBN = ?", bean.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * システム区分情報を削除する。
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(SystemDivision bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();
			sql.add(" DELETE FROM SYS_MST ");
			sql.add(" WHERE	KAI_CODE = ? ", bean.getCompanyCode());
			sql.add(" AND	SYS_KBN = ? ", bean.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * システム区分一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式のシステム区分マスタ一覧
	 * @throws TException
	 */
	public byte[] getExcel(SystemDivisionSearchCondition condition) throws TException {

		List<SystemDivision> systemList = get(condition);
		if (systemList == null || systemList.isEmpty()) {
			return null;
		}

		SystemDivisionExcel layout = new SystemDivisionExcel(getUser().getLanguage());
		byte[] data = layout.getExcel(systemList);

		return data;

	}

}
