package jp.co.ais.trans2.model.aprvrole;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * 承認権限ロールグループマスタSQL
 */
public class AprvRoleGroupManagerImpl extends TModel implements AprvRoleGroupManager {

	public List<AprvRoleGroup> get(AprvRoleGroupSearchCondition condition) throws TException {
		Connection conn = DBUtil.getConnection();

		List<AprvRoleGroup> list = new ArrayList<AprvRoleGroup>();
		try {

			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT DISTINCT ");
			sql.add("      grp.KAI_CODE ");
			sql.add("      ,grp.APRV_ROLE_GRP_CODE ");
			sql.add("      ,grp.APRV_ROLE_GRP_NAME ");
			sql.add("      ,grp.APRV_ROLE_GRP_NAME_S ");
			sql.add("      ,grp.APRV_ROLE_GRP_NAME_K ");
			sql.add("      ,grp.STR_DATE ");
			sql.add("      ,grp.END_DATE ");
			sql.add("      ,grp.INP_DATE ");
			sql.add("      ,grp.UPD_DATE ");
			sql.add("      ,grp.PRG_ID ");
			sql.add("      ,grp.USR_ID ");

			sql.add("      ,grp.APRV_LEVEL ");
			sql.add("      ,grp.APRV_ROLE_CODE ");
			sql.add("      ,role.APRV_ROLE_NAME ");
			sql.add("      ,role.APRV_ROLE_NAME_S ");
			sql.add("      ,role.APRV_ROLE_NAME_K ");
			sql.add("      ,role.ACCT_APRV_FLG ");

			sql.add(" FROM APRV_ROLE_GRP_MST grp ");
			sql.add(" LEFT JOIN APRV_ROLE_MST role ON role.KAI_CODE       = grp.KAI_CODE ");
			sql.add("                             AND role.APRV_ROLE_CODE = grp.APRV_ROLE_CODE ");

			sql.add(" WHERE 1 = 1 ");
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add(" AND grp.KAI_CODE = ? ", condition.getCompanyCode());
			}
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add(" AND grp.APRV_ROLE_GRP_CODE = ? ", condition.getCode());
			}
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add(" AND grp.APRV_ROLE_GRP_CODE >= ? ", condition.getCodeFrom());
			}
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add(" AND grp.APRV_ROLE_GRP_CODE <= ? ", condition.getCodeTo());
			}
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.addLikeFront(" AND grp.APRV_ROLE_GRP_CODE ? ", condition.getCodeLike());
			}
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				sql.addNLikeAmbi(" AND grp.APRV_ROLE_GRP_NAME_S ? ", condition.getNamesLike());
			}
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql.addNLikeAmbi(" AND grp.APRV_ROLE_GRP_NAME_K ? ", condition.getNamekLike());
			}

			// 有効期間
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				sql.add(" AND grp.STR_DATE <= ?", condition.getValidTerm());
				sql.add(" AND grp.END_DATE >= ?", condition.getValidTerm());
			}
			// ユーザー
			if (!Util.isNullOrEmpty(condition.getUserCode())) {
				sql.add(" AND EXISTS (SELECT 1 ");
				sql.add("             FROM APRV_ROLE_MST r ");
				sql.add("             WHERE r.KAI_CODE = grp.KAI_CODE ");
				sql.add("             AND   r.APRV_ROLE_CODE = grp.APRV_ROLE_CODE ");
				sql.add("             AND   r.USR_CODE = ? ", condition.getUserCode());
				sql.add("            ) ");
			}

			sql.add(" ORDER BY ");
			sql.add("  grp.KAI_CODE ");
			sql.add(" ,grp.APRV_ROLE_GRP_CODE ");
			sql.add(" ,grp.APRV_LEVEL ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			list = mapping(rs);

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
	protected List<AprvRoleGroup> mapping(ResultSet rs) throws SQLException {
		List<AprvRoleGroup> list = new ArrayList();
		AprvRoleGroup entity = null;
		while (rs.next()) {
			String grpCode = rs.getString("APRV_ROLE_GRP_CODE");
			if (entity == null || !Util.equals(entity.getAPRV_ROLE_GRP_CODE(), grpCode)) {
				// コードが変わった→ヘッダmapping
				entity = new AprvRoleGroup();
				entity.setKAI_CODE(rs.getString("KAI_CODE")); // 会社コード
				entity.setAPRV_ROLE_GRP_CODE(rs.getString("APRV_ROLE_GRP_CODE")); // ロールコード
				entity.setAPRV_ROLE_GRP_NAME(rs.getString("APRV_ROLE_GRP_NAME")); // ロール名称
				entity.setAPRV_ROLE_GRP_NAME_S(rs.getString("APRV_ROLE_GRP_NAME_S")); // ロール略称
				entity.setAPRV_ROLE_GRP_NAME_K(rs.getString("APRV_ROLE_GRP_NAME_K")); // ロール検索名称
				entity.setSTR_DATE(rs.getTimestamp("STR_DATE")); // 開始年月日
				entity.setEND_DATE(rs.getTimestamp("END_DATE")); // 終了年月日
				entity.setINP_DATE(rs.getTimestamp("INP_DATE")); // 登録日付
				entity.setUPD_DATE(rs.getTimestamp("UPD_DATE")); // 更新日付
				entity.setPRG_ID(rs.getString("PRG_ID")); // プログラムID
				entity.setUSR_ID(rs.getString("USR_ID")); // ユーザーID
				list.add(entity);
			}
			// 明細部マッピング
			AprvRoleGroupDetail dtl = new AprvRoleGroupDetail();
			dtl.setAPRV_ROLE_CODE(rs.getString("APRV_ROLE_CODE"));
			dtl.setAPRV_ROLE_NAME(rs.getString("APRV_ROLE_NAME"));
			dtl.setAPRV_ROLE_NAME_S(rs.getString("APRV_ROLE_NAME_S"));
			dtl.setAPRV_ROLE_NAME_K(rs.getString("APRV_ROLE_NAME_K"));
			dtl.setAPRV_LEVEL(rs.getInt("APRV_LEVEL"));
			entity.addDetailList(dtl);
		}
		return list;
	}

	/**
	 * 承認権限ロールマスタ登録
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entry(AprvRoleGroup bean) throws TException {
		Connection conn = DBUtil.getConnection();
		try {
			entry(bean, conn);

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 登録処理
	 * 
	 * @param bean
	 * @param conn
	 * @throws TException
	 */
	protected void entry(AprvRoleGroup bean, Connection conn) throws TException {
		for (AprvRoleGroupDetail d : bean.getDetailList()) {
			SQLCreator sql = new SQLCreator();
			sql.add(" INSERT INTO APRV_ROLE_GRP_MST ( ");
			sql.add("      KAI_CODE ");
			sql.add("      ,APRV_ROLE_GRP_CODE ");
			sql.add("      ,APRV_ROLE_GRP_NAME ");
			sql.add("      ,APRV_ROLE_GRP_NAME_S ");
			sql.add("      ,APRV_ROLE_GRP_NAME_K ");
			sql.add("      ,APRV_ROLE_CODE ");
			sql.add("      ,APRV_LEVEL ");
			sql.add("      ,STR_DATE ");
			sql.add("      ,END_DATE ");
			sql.add("      ,INP_DATE ");
			sql.add("      ,UPD_DATE ");
			sql.add("      ,PRG_ID ");
			sql.add("      ,USR_ID ");
			sql.add(" )VALUES( ");
			sql.add("     ? ", bean.getKAI_CODE());
			sql.add("     ,? ", bean.getAPRV_ROLE_GRP_CODE());
			sql.add("     ,? ", bean.getAPRV_ROLE_GRP_NAME());
			sql.add("     ,? ", bean.getAPRV_ROLE_GRP_NAME_S());
			sql.add("     ,? ", bean.getAPRV_ROLE_GRP_NAME_K());
			sql.add("     ,? ", d.getAPRV_ROLE_CODE());
			sql.add("     ,? ", d.getAPRV_LEVEL());
			sql.adt("     ,? ", bean.getSTR_DATE());
			sql.adt("     ,? ", bean.getEND_DATE());
			sql.adt("     ,? ", getNow());
			sql.adt("     ,? ", null);
			sql.add("     ,? ", getProgramCode());
			sql.add("     ,? ", getUserCode());
			sql.add(" ) ");
			DBUtil.execute(conn, sql);
		}
	}

	/**
	 * 承認権限ロールマスタ修正
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void modify(AprvRoleGroup bean) throws TException {
		Connection conn = DBUtil.getConnection();
		try {

			SQLCreator sql = new SQLCreator();
			sql.add(" DELETE FROM APRV_ROLE_GRP_MST ");
			sql.add(" WHERE KAI_CODE       = ? ", bean.getKAI_CODE());
			sql.add(" AND   APRV_ROLE_GRP_CODE = ? ", bean.getAPRV_ROLE_GRP_CODE());
			DBUtil.execute(conn, sql);

			entry(bean, conn);
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 承認権限ロールマスタ削除
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(AprvRoleGroup bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator sql = new SQLCreator();
			sql.add(" DELETE FROM APRV_ROLE_GRP_MST ");
			sql.add(" WHERE KAI_CODE = ? ", bean.getKAI_CODE());
			sql.add("     AND APRV_ROLE_GRP_CODE = ? ", bean.getAPRV_ROLE_GRP_CODE());
			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 承認権限ロールマスタ一覧をエクセル形式で返す
	 * 
	 * @param condition
	 * @return エクセルバイナリ
	 * @throws TException
	 */
	public byte[] getExcel(AprvRoleGroupSearchCondition condition) throws TException {

		try {

			// データを抽出
			List<AprvRoleGroup> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			AprvRoleGroupExcel exl = new AprvRoleGroupExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

}