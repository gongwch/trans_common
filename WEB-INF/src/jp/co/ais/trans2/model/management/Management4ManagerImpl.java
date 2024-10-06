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
 * 管理4マネージャの実装クラスです。
 * 
 * @author AIS
 *
 */
public class Management4ManagerImpl extends TModel implements Management4Manager {

	public List<Management4> get(Management4SearchCondition condition) throws TException {

		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		// 検索結果格納用配列生成
		List<Management4> list = new ArrayList<Management4>();

		try {

			VCreator sql = getSQL(condition);

			sql.add("ORDER BY");
			sql.add("    knr4.KNR_CODE_4 ");

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
	 * 指定条件に該当する管理4情報を返す<br>
	 * 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する管理4情報
	 * @throws TException
	 */
	public List<Management4> getRef(Management4SearchCondition condition) throws TException {

		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		// 検索結果格納用配列生成
		List<Management4> list = new ArrayList<Management4>();

		try {

			VCreator sql = getSQL(condition);

			if (condition.getCompanyCodeList() != null && !condition.getCompanyCodeList().isEmpty()) {
				sql.add(" AND knr4.KAI_CODE IN ? ", condition.getCompanyCodeList());
			}

			sql.add(" ORDER BY  ");
			sql.add("   knr4.KNR_CODE_4 ");
			sql.add("  ,CASE WHEN knr4.KAI_CODE = ? THEN 0 ELSE 1 END ", getCompanyCode()); // 同じコードの場合にログイン会社優先
			sql.add("  ,knr4.KAI_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String currentCode = null;

			while (rs.next()) {

				Management4 bean = mapping(rs);

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
	protected VCreator getSQL(Management4SearchCondition condition) {
		VCreator sql = new VCreator();

		sql.add("");
		sql.add("SELECT");
		sql.add("	 knr4.KAI_CODE");
		sql.add("	,knr4.KNR_CODE_4");
		sql.add("	,knr4.KNR_NAME_4");
		sql.add("	,knr4.KNR_NAME_S_4");
		sql.add("	,knr4.KNR_NAME_K_4");
		sql.add("	,knr4.STR_DATE");
		sql.add("	,knr4.END_DATE");
		sql.add("FROM KNR4_MST knr4");
		sql.add("WHERE 1 = 1");

		// 会社コード
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add("AND");
			sql.add("    knr4.KAI_CODE = ?", condition.getCompanyCode());

		}

		// コード
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql.add("AND");
			sql.add("    knr4.KNR_CODE_4 = ?", condition.getCode());

		}

		// コード前方一致
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql.add("AND");
			sql.addLikeFront("    knr4.KNR_CODE_4 ?", condition.getCodeLike());

		}

		// 略称曖昧
		if (!Util.isNullOrEmpty(condition.getNamesLike())) {
			sql.add("AND");
			sql.addNLikeAmbi("    knr4.KNR_NAME_S_4 ?", condition.getNamesLike());

		}

		// 検索名称曖昧
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql.add("AND");
			sql.addNLikeAmbi("   knr4.KNR_NAME_K_4 ?", condition.getNamekLike());

		}

		// 開始コード
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql.add("AND");
			sql.add("    knr4.KNR_CODE_4 >= ?", condition.getCodeFrom());

		}

		// 終了コード
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql.add("AND");
			sql.add("    knr4.KNR_CODE_4 <= ?", condition.getCodeTo());

		}

		// コード(複数)
		if (condition.getManagement4CodeList() != null && condition.getManagement4CodeList().length != 0) {
			sql.add(" AND knr4.KNR_CODE_4 IN ");
			sql.addInStatement(condition.getManagement4CodeList());

		}

		// 有効期間
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql.add("AND");
			sql.addYMDHMS("    knr4.STR_DATE <= ?", condition.getValidTerm());
			sql.add("AND");
			sql.addYMDHMS("    knr4.END_DATE >= ?", condition.getValidTerm());
		}

		// 最終更新日時
		if (condition.getLastUpdateDate() != null) {
			sql.adt(" AND  (knr4.INP_DATE > ? ", condition.getLastUpdateDate());
			sql.adt("    OR knr4.UPD_DATE > ?)", condition.getLastUpdateDate());
		}
		return sql;
	}

	public void entry(Management4 management) throws TException {
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("INSERT INTO KNR4_MST(");
			sql.add("	 KAI_CODE");
			sql.add("	,KNR_CODE_4");
			sql.add("	,KNR_NAME_4");
			sql.add("	,KNR_NAME_S_4");
			sql.add("	,KNR_NAME_K_4");
			sql.add("	,STR_DATE");
			sql.add("	,END_DATE");
			sql.add("   ,INP_DATE");
			sql.add("   ,UPD_DATE");
			sql.add("   ,PRG_ID");
			sql.add("   ,USR_ID");
			sql.add(") VALUES(");
			sql.add("    ?", management.getCompanyCode());
			sql.add("   ,?", management.getCode());
			sql.add("    ,?", management.getName());
			sql.add("    ,?", management.getNames());
			sql.add("    ,?", management.getNamek());
			sql.add("    ,?", management.getDateFrom());
			sql.add("    ,?", management.getDateTo());
			sql.addYMDHMS("    ,?", getNow());
			sql.add(",NULL ");
			sql.add("    ,?", getProgramCode());
			sql.add("    ,?", getUserCode());
			sql.add(")");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void modify(Management4 management) throws TException {

		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("UPDATE");
			sql.add("    KNR4_MST");
			sql.add("SET");
			sql.add("    KNR_NAME_4 = ? ", management.getName());
			sql.add("   ,KNR_NAME_S_4 = ? ", management.getNames());
			sql.add("   ,KNR_NAME_K_4 = ? ", management.getNamek());
			sql.add("   ,STR_DATE = ?", management.getDateFrom());
			sql.add("   ,END_DATE = ?", management.getDateTo());
			sql.addYMDHMS("    ,UPD_DATE = ?", getNow());
			sql.add("   ,PRG_ID = ?", getProgramCode());
			sql.add("   ,USR_ID = ?", getUserCode());

			sql.add("WHERE");
			sql.add("    KAI_CODE = ?", management.getCompanyCode());
			sql.add("AND");
			sql.add("    KNR_CODE_4 = ?", management.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void delete(Management4 management) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.MANAGEMENT_4);
		condition.setCompanyCode(management.getCompanyCode());
		condition.setManagement4Code(management.getCode());

		// 使用済みチェック
		CheckMasterUseDao cd = (CheckMasterUseDao) getComponent(CheckMasterUseDao.class);
		cd.check(condition);

		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("DELETE FROM");
			sql.add("    KNR4_MST ");
			sql.add("WHERE");
			sql.add("    KAI_CODE = ? ", getCompanyCode());
			sql.add("AND");
			sql.add("    KNR_CODE_4 = ? ", management.getCode());

			DBUtil.execute(conn, sql);
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 管理4一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の管理1一覧
	 * @throws TException
	 */
	public byte[] getExcel(Management4SearchCondition condition) throws TException {

		List<Management4> list = get(condition);
		if (list == null || list.isEmpty()) {
			return null;
		}

		Management4Excel layout = new Management4Excel(getUser().getLanguage());
		byte[] data = layout.getExcel(getCompany(), list);

		return data;

	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected Management4 mapping(ResultSet rs) throws Exception {

		Management4 bean = new Management4();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("KNR_CODE_4"));
		bean.setName(rs.getString("KNR_NAME_4"));
		bean.setNames(rs.getString("KNR_NAME_S_4"));
		bean.setNamek(rs.getString("KNR_NAME_K_4"));
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));

		return bean;

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

}
