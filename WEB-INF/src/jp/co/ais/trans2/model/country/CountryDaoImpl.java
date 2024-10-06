package jp.co.ais.trans2.model.country;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * 国情報Daoの実装クラス実装
 */
public class CountryDaoImpl extends TModel implements CountryDao {

	/**
	 * 検索
	 * 
	 * @param condition 条件
	 * @return 検索結果
	 * @throws TException
	 */
	public List<Country> get(CountrySearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			List<Country> list = new ArrayList<Country>();

			VCreator sql = new VCreator();
			sql.add("SELECT");
			sql.add("  cou.COU_CODE,");
			sql.add("  cou.COU_CODE2,");
			sql.add("  cou.COU_NO,");
			sql.add("  cou.COU_NAME,");
			sql.add("  cou.COU_NAME2,");
			sql.add("  cou.STR_DATE,");
			sql.add("  cou.END_DATE");
			sql.add("FROM COUNTRY_MST cou");
			sql.add("WHERE 1 = 1");

			sql.add(getSelectWhereSQL(condition));

			sql.add("ORDER BY");
			sql.add("  cou.COU_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}

			DBUtil.close(statement);
			DBUtil.close(rs);

			return list;

		} catch (SQLException e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * SELECT文のWHERE条件
	 * 
	 * @param condition 条件
	 * @return WHERE文
	 */
	protected String getSelectWhereSQL(CountrySearchCondition condition) {
		VCreator sql = new VCreator();

		// コード
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql.add(" AND cou.COU_CODE = ?", condition.getCode());
		}

		// コード前方一致
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql.addLikeFront(" AND cou.COU_CODE ?", condition.getCodeLike());
		}

		// 名称あいまい
		if (!Util.isNullOrEmpty(condition.getNameLike())) {
			sql.addNLikeAmbi("  AND cou.COU_NAME ?", condition.getNameLike());
		}

		// コード2
		if (!Util.isNullOrEmpty(condition.getCode2())) {
			sql.add(" AND cou.COU_CODE2 = ?", condition.getCode2());
		}

		// 開始コード
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql.add("  AND cou.COU_CODE >= ?", condition.getCodeFrom());
		}

		// 終了コード
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql.add("  AND cou.COU_CODE <= ?", condition.getCodeTo());
		}

		// 有効期間
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql.adt("  AND cou.STR_DATE <= ?", condition.getValidTerm());
			sql.adt("  AND cou.END_DATE >= ?", condition.getValidTerm());
		}

		// 最終更新日時
		if (condition.getLastUpdateDate() != null) {
			sql.adt(" AND  (cou.INP_DATE > ? ", condition.getLastUpdateDate());
			sql.adt("    OR cou.UPD_DATE > ?)", condition.getLastUpdateDate());
		}

		return sql.toSQL();
	}

	/**
	 * 検索結果値をBeanにセット
	 * 
	 * @param rs ResultSet 検索結果
	 * @return 検索結果値がセットされたBean
	 * @throws SQLException
	 */
	protected Country mapping(ResultSet rs) throws SQLException {
		Country bean = createCountry();
		bean.setCode(rs.getString("COU_CODE"));
		bean.setCode2(rs.getString("COU_CODE2"));
		bean.setNumber(rs.getString("COU_NO"));
		bean.setName(rs.getString("COU_NAME"));
		bean.setName2(rs.getString("COU_NAME2"));
		bean.setDateFrom(rs.getTimestamp("STR_DATE"));
		bean.setDateTo(rs.getTimestamp("END_DATE"));

		return bean;
	}

	/**
	 * 国エンティティ生成
	 * 
	 * @return 国エンティティ
	 */
	protected Country createCountry() {
		return new Country();
	}

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(CountrySearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   COUNTRY_MST cou");
			sql.add(" WHERE  1 = 1 "); // 会社コード

			// 最終更新日時
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (cou.INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR cou.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR cou.INP_DATE IS NULL AND cou.UPD_DATE IS NULL) ");
			}

			// 削除あり＝現在持っている件数はDBの過去の件数と不一致
			hasDelete = DBUtil.selectOneInt(conn, sql.toSQL()) != condition.getNowCount();

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return hasDelete;
	}

	/**
	 * 情報登録 (INSERT)
	 * 
	 * @param bean 入力情報
	 * @throws TException
	 */
	public void entry(Country bean) throws TException {
		// TODO 要実装
	}

	/**
	 * 情報修正 (UPDATE)
	 * 
	 * @param bean 入力情報
	 * @throws TException
	 */
	public void modify(Country bean) throws TException {
		// TODO 要実装
	}

	/**
	 * 情報削除 (DELETE)
	 * 
	 * @param bean 選択情報
	 * @throws TException
	 */
	public void delete(Country bean) throws TException {
		// TODO 要実装
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
}
