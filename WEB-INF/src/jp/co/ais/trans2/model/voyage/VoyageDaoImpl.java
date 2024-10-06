package jp.co.ais.trans2.model.voyage;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * VoyageマスタDao実装
 * 
 * @author AIS
 */
public class VoyageDaoImpl extends TModel implements VoyageDao {

	/**
	 * 指定条件に該当するVoyage情報を返す。<br/>
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するPEVoyage情報
	 * @throws TException
	 */
	public List<Voyage> getByCondition(VoyageSearchCondition condition) throws TException {
		Connection conn = DBUtil.getConnection();

		List<Voyage> list = new ArrayList<Voyage>();
		try {

			VCreator sql = new VCreator();

			sql.add("SELECT ");
			sql.add("  voyage.KAI_CODE ");
			sql.add(" ,voyage.VOYAGE_CODE ");
			sql.add(" ,voyage.VOYAGE_NAME ");
			sql.add(" ,voyage.VOYAGE_NAME_S ");
			sql.add(" ,voyage.VOYAGE_NAME_K ");
			sql.add(" ,voyage.INP_DATE ");
			sql.add(" ,voyage.UPD_DATE ");
			sql.add(" ,voyage.PRG_ID ");
			sql.add(" ,voyage.USR_ID ");
			sql.add(" ,voyage.STR_DATE ");
			sql.add(" ,voyage.END_DATE ");
			sql.add("FROM CM_VOYAGE_MST voyage");
			sql.add("WHERE 1 = 1 ");

			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add("  AND voyage.KAI_CODE = ? ", condition.getCompanyCode());
			}
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add("  AND voyage.VOYAGE_CODE = ? ", condition.getCode());
			}
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add("  AND voyage.VOYAGE_CODE >= ?", condition.getCodeFrom());
			}
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add("  AND voyage.VOYAGE_CODE <= ?", condition.getCodeTo());
			}
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.addLikeFront("  AND voyage.VOYAGE_CODE ? ", condition.getCodeLike());
			}
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				sql.addNLikeAmbi("  AND voyage.VOYAGE_NAME_S ? ", condition.getNamesLike());
			}
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql.addNLikeAmbi("  AND voyage.VOYAGE_NAME_K ? ", condition.getNamekLike());
			}
			// 有効期間
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				sql.add(" AND    voyage.STR_DATE <= ? ", condition.getValidTerm());
				sql.add(" AND    voyage.END_DATE >= ? ", condition.getValidTerm());
			}

			// 最終更新日時
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (voyage.INP_DATE > ? ", condition.getLastUpdateDate());
				sql.adt("    OR voyage.UPD_DATE > ?)", condition.getLastUpdateDate());
			}

			sql.add("ORDER BY ");
			sql.add("  voyage.KAI_CODE ");
			sql.add(" ,voyage.VOYAGE_CODE ");

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
	 * 指定コードに該当するVoyage情報を返す。
	 * 
	 * @param companyCode 会社コード
	 * @param voyageCode Voyageコード
	 * @return 指定コードに該当するVoyage情報
	 * @throws TException
	 */
	public Voyage get(String companyCode, String voyageCode) throws TException {

		VoyageSearchCondition condition = new VoyageSearchCondition();
		condition.setCompanyCode(companyCode);
		condition.setCode(voyageCode);

		List<Voyage> list = getByCondition(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(VoyageSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   CM_VOYAGE_MST voyage ");
			sql.add(" WHERE  voyage.KAI_CODE = ? ", condition.getCompanyCode()); // 会社コード

			// 最終更新日時
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (voyage.INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR voyage.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR voyage.INP_DATE IS NULL AND voyage.UPD_DATE IS NULL) ");
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
	 * Voyageを登録する。
	 * 
	 * @param dto Voyage
	 * @throws TException
	 */
	public void insert(Voyage dto) throws TException {
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			VCreator sql = new VCreator();

			sql.add("INSERT  ");
			sql.add("INTO CM_VOYAGE_MST(  ");
			sql.add("  KAI_CODE ");
			sql.add(" ,VOYAGE_CODE ");
			sql.add(" ,VOYAGE_NAME ");
			sql.add(" ,VOYAGE_NAME_S ");
			sql.add(" ,VOYAGE_NAME_K ");
			sql.add(" ,INP_DATE ");
			sql.add(" ,PRG_ID ");
			sql.add(" ,USR_ID ");
			sql.add(" ,STR_DATE ");
			sql.add(" ,END_DATE ");
			sql.add(")  ");
			sql.add("VALUES (  ");
			sql.add("  ? ", dto.getCompanyCode());
			sql.add(" ,? ", dto.getCode());
			sql.add(" ,? ", dto.getName());
			sql.add(" ,? ", dto.getNames());
			sql.add(" ,? ", dto.getNamek());
			sql.adt(" ,? ", getNow());
			sql.add(" ,? ", getProgramCode());
			sql.add(" ,? ", getUserCode());
			sql.adt(" ,? ", dto.getDateFrom());
			sql.adt(" ,? ", dto.getDateTo());
			sql.add(")  ");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * Voyageを修正する。
	 * 
	 * @param dto Voyage
	 * @throws TException
	 */
	public void update(Voyage dto) throws TException {
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			VCreator sql = new VCreator();

			sql.add("UPDATE CM_VOYAGE_MST  ");
			sql.add("SET ");
			sql.add("     KAI_CODE = ? ", dto.getCompanyCode());
			sql.add("    ,VOYAGE_CODE = ? ", dto.getCode());
			sql.add("    ,VOYAGE_NAME = ? ", dto.getName());
			sql.add("    ,VOYAGE_NAME_S = ? ", dto.getNames());
			sql.add("    ,VOYAGE_NAME_K = ? ", dto.getNamek());
			sql.adt("   ,UPD_DATE = ? ", getNow());
			sql.add("    ,PRG_ID = ? ", getProgramCode());
			sql.add("    ,USR_ID = ? ", getUserCode());
			sql.adt("   ,STR_DATE = ? ", dto.getDateFrom());
			sql.adt("   ,END_DATE = ? ", dto.getDateTo());
			sql.add("WHERE 1 = 1 ");
			sql.add("AND KAI_CODE = ? ", dto.getCompanyCode());
			sql.add("AND VOYAGE_CODE = ? ", dto.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * Voyageを削除する。
	 * 
	 * @param dto Voyage
	 * @throws TException
	 */
	public void delete(Voyage dto) throws TException {
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			VCreator sql = new VCreator();

			sql.add("DELETE FROM CM_VOYAGE_MST ");
			sql.add("  WHERE KAI_CODE  = ? ", dto.getCompanyCode());
			sql.add("  AND VOYAGE_CODE = ? ", dto.getCode());

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
	 * @return entity
	 * @throws SQLException
	 */
	protected Voyage mapping(ResultSet rs) throws SQLException {
		Voyage entity = new Voyage();

		entity.setCompanyCode(rs.getString("KAI_CODE"));
		entity.setCode(rs.getString("VOYAGE_CODE"));
		entity.setName(rs.getString("VOYAGE_NAME"));
		entity.setNames(rs.getString("VOYAGE_NAME_S"));
		entity.setNamek(rs.getString("VOYAGE_NAME_K"));
		entity.setDateFrom(rs.getDate("STR_DATE"));
		entity.setDateTo(rs.getDate("END_DATE"));
		return entity;
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
