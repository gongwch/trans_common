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
 * 管理3マネージャの実装クラスです。
 * 
 * @author AIS
 */
public class Management3ManagerImpl extends TModel implements Management3Manager {

	/**
	 * 情報検索 (SELECT)
	 * 
	 * @param condition 検索条件
	 * @return List 検索結果
	 * @throws TException
	 */
	public List<Management3> get(Management3SearchCondition condition) throws TException {

		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		// 検索結果格納用配列生成
		List<Management3> list = new ArrayList<Management3>();

		try {

			VCreator sql = getSQL(condition);

			sql.add("ORDER BY");
			sql.add("knr3.KNR_CODE_3");

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
	 * 指定条件に該当する管理3情報を返す<br>
	 * 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する管理3情報
	 * @throws TException
	 */
	public List<Management3> getRef(Management3SearchCondition condition) throws TException {

		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		// 検索結果格納用配列生成
		List<Management3> list = new ArrayList<Management3>();

		try {

			VCreator sql = getSQL(condition);

			if (condition.getCompanyCodeList() != null && !condition.getCompanyCodeList().isEmpty()) {
				sql.add(" AND knr3.KAI_CODE IN ? ", condition.getCompanyCodeList());
			}

			sql.add(" ORDER BY  ");
			sql.add("   knr3.KNR_CODE_3 ");
			sql.add("  ,CASE WHEN knr3.KAI_CODE = ? THEN 0 ELSE 1 END ", getCompanyCode()); // 同じコードの場合にログイン会社優先
			sql.add("  ,knr3.KAI_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String currentCode = null;

			while (rs.next()) {

				Management3 bean = mapping(rs);

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
	protected VCreator getSQL(Management3SearchCondition condition) {
		VCreator sql = new VCreator();

		sql.add("");
		sql.add(" SELECT ");
		sql.add("	 knr3.KAI_CODE ");
		sql.add("	,knr3.KNR_CODE_3 ");
		sql.add(" 	,knr3.KNR_NAME_3 ");
		sql.add("	,knr3.KNR_NAME_S_3 ");
		sql.add("	,knr3.KNR_NAME_K_3 ");
		sql.add("	,knr3.STR_DATE ");
		sql.add("	,knr3.END_DATE ");
		sql.add(" FROM KNR3_MST knr3");
		sql.add(" WHERE 1 = 1 ");

		// 会社コード
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add("AND");
			sql.add("		knr3.KAI_CODE =?", condition.getCompanyCode());
		}

		// コード
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql.add("AND");
			sql.add("		knr3.KNR_CODE_3 =?", condition.getCode());
		}

		// コード前方一致
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql.add("AND");
			sql.addLikeFront("    knr3.KNR_CODE_3 ?", condition.getCodeLike());
		}

		// 略称曖昧
		if (!Util.isNullOrEmpty(condition.getNamesLike())) {
			sql.add("AND");
			sql.addNLikeAmbi("		knr3.KNR_NAME_S_3 ?", condition.getNamesLike());
		}

		// 検索名称曖昧
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql.add("AND");
			sql.addNLikeAmbi("		knr3.KNR_NAME_K_3 ?", condition.getNamekLike());
		}

		// 開始コード
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql.add("AND");
			sql.add("		knr3.KNR_CODE_3 >=?", condition.getCodeFrom());
		}

		// 終了コード
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql.add("AND");
			sql.add("		knr3.KNR_CODE_3 <=?", condition.getCodeTo());
		}

		// コード(複数)
		if (condition.getManagement3CodeList() != null && condition.getManagement3CodeList().length != 0) {
			sql.add("AND knr3.KNR_CODE_3 IN");
			sql.addInStatement(condition.getManagement3CodeList());
		}

		// 有効期間
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql.add("AND");
			sql.addYMDHMS("		knr3.STR_DATE <= ?", condition.getValidTerm());
			sql.add("AND");
			sql.addYMDHMS("		knr3.END_DATE >= ?", condition.getValidTerm());
		}

		// 最終更新日時
		if (condition.getLastUpdateDate() != null) {
			sql.adt(" AND  (knr3.INP_DATE > ? ", condition.getLastUpdateDate());
			sql.adt("    OR knr3.UPD_DATE > ?)", condition.getLastUpdateDate());
		}
		return sql;
	}

	/**
	 * 情報登録 (INSERT)
	 * 
	 * @param bean 入力情報
	 * @throws TException
	 */
	public void entry(Management3 bean) throws TException {

		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();

			sql.add("");
			sql.add("INSERT INTO KNR3_MST (");
			sql.add("	 KAI_CODE ");
			sql.add("	,KNR_CODE_3 ");
			sql.add(" 	,KNR_NAME_3 ");
			sql.add("	,KNR_NAME_S_3 ");
			sql.add("	,KNR_NAME_K_3 ");
			sql.add("	,STR_DATE ");
			sql.add("	,END_DATE ");
			sql.add("	,INP_DATE ");
			sql.add("	,UPD_DATE ");
			sql.add("	,PRG_ID ");
			sql.add("	,USR_ID ");
			sql.add(" ) VALUES (");
			sql.add("     ?", getCompanyCode());
			sql.add("    ,?", bean.getCode());
			sql.add("    ,?", bean.getName());
			sql.add("    ,?", bean.getNames());
			sql.add("    ,?", bean.getNamek());
			sql.add("    ,?", bean.getDateFrom());
			sql.add("    ,?", bean.getDateTo());
			sql.addYMDHMS("    ,?", getNow());
			sql.add(" ,NULL");
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

	/**
	 * 情報修正 (UPDATE)
	 * 
	 * @param bean 入力情報
	 * @throws TException
	 */
	public void modify(Management3 bean) throws TException {

		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();

			sql.add("");
			sql.add("UPDATE");
			sql.add("    KNR3_MST");
			sql.add("SET");
			sql.add("    KNR_NAME_3 = ?", bean.getName());
			sql.add("    ,KNR_NAME_S_3 = ?", bean.getNames());
			sql.add("    ,KNR_NAME_K_3 = ?", bean.getNamek());
			sql.add("    ,STR_DATE = ?", bean.getDateFrom());
			sql.add("    ,END_DATE = ?", bean.getDateTo());
			sql.addYMDHMS("    ,UPD_DATE = ?", getNow());
			sql.add("    ,PRG_ID = ?", getProgramCode());
			sql.add("    ,USR_ID = ?", getUserCode());
			sql.add("WHERE");
			sql.add("    KAI_CODE = ?", getCompanyCode());
			sql.add("AND");
			sql.add("    KNR_CODE_3 = ?", bean.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 情報削除 (DELETE)
	 * 
	 * @param bean 選択情報
	 * @throws TException
	 */
	public void delete(Management3 bean) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.MANAGEMENT_3);
		condition.setCompanyCode(bean.getCompanyCode());
		condition.setManagement3Code(bean.getCode());

		// 使用済みチェック
		CheckMasterUseDao cd = (CheckMasterUseDao) getComponent(CheckMasterUseDao.class);
		cd.check(condition);

		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();

			sql.add("");
			sql.add("DELETE FROM");
			sql.add("    KNR3_MST ");
			sql.add("WHERE");
			sql.add("    KAI_CODE = ? ", getCompanyCode());
			sql.add("AND");
			sql.add("    KNR_CODE_3 = ? ", bean.getCode());

			DBUtil.execute(conn, sql);

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
	 * @return エンティティ
	 * @throws Exception
	 */
	protected Management3 mapping(ResultSet rs) throws Exception {

		Management3 bean = new Management3();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("KNR_CODE_3"));
		bean.setName(rs.getString("KNR_NAME_3"));
		bean.setNames(rs.getString("KNR_NAME_S_3"));
		bean.setNamek(rs.getString("KNR_NAME_K_3"));
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));

		return bean;

	}

	/**
	 * 管理3一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の管理3一覧
	 * @throws TException
	 */
	public byte[] getExcel(Management3SearchCondition condition) throws TException {

		List<Management3> list = get(condition);
		if (list == null || list.isEmpty()) {
			return null;
		}

		Management3Excel layout = new Management3Excel(getUser().getLanguage());
		byte[] data = layout.getExcel(getCompany(), list);

		return data;

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
