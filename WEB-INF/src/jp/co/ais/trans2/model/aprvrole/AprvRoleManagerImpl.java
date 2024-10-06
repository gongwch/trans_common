package jp.co.ais.trans2.model.aprvrole;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.user.*;

/**
 * 承認権限ロールマスタSQL
 */
public class AprvRoleManagerImpl extends TModel implements AprvRoleManager {

	public List<AprvRole> get(AprvRoleSearchCondition condition) throws TException {
		Connection conn = DBUtil.getConnection();

		List<AprvRole> list = new ArrayList<AprvRole>();
		try {

			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT ");
			sql.add("      apr.KAI_CODE ");
			sql.add("      ,apr.APRV_ROLE_CODE ");
			sql.add("      ,apr.APRV_ROLE_NAME ");
			sql.add("      ,apr.APRV_ROLE_NAME_S ");
			sql.add("      ,apr.APRV_ROLE_NAME_K ");
			sql.add("      ,apr.ACCT_APRV_FLG ");
			sql.add("      ,apr.STR_DATE ");
			sql.add("      ,apr.END_DATE ");
			sql.add("      ,apr.INP_DATE ");
			sql.add("      ,apr.UPD_DATE ");
			sql.add("      ,apr.PRG_ID ");
			sql.add("      ,apr.USR_ID ");

			sql.add("      ,apr.USR_CODE ");
			sql.add("      ,usr.USR_NAME ");
			sql.add("      ,usr.USR_NAME_S ");
			sql.add("      ,usr.USR_NAME_K ");
			sql.add("      ,usr.DEP_CODE ");
			sql.add("      ,dep.DEP_NAME ");
			sql.add("      ,dep.DEP_NAME_S ");
			sql.add("      ,dep.DEP_NAME_K ");

			sql.add(" FROM APRV_ROLE_MST apr ");
			sql.add(" LEFT JOIN USR_MST usr ON usr.KAI_CODE = apr.KAI_CODE ");
			sql.add("                      AND usr.USR_CODE = apr.USR_CODE ");
			sql.add(" LEFT JOIN BMN_MST dep ON dep.KAI_CODE  = usr.KAI_CODE ");
			sql.add("                       AND dep.DEP_CODE = usr.DEP_CODE ");

			if (!Util.isNullOrEmpty(condition.getUserCode())) {
				sql.add(" INNER JOIN APRV_ROLE_GRP_MST grp ON grp.KAI_CODE = apr.KAI_CODE ");
				sql.add("                                 AND grp.APRV_ROLE_CODE = apr.APRV_ROLE_CODE ");
			}
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND apr.KAI_CODE = ? ", condition.getCompanyCode());

			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add(" AND apr.APRV_ROLE_CODE = ? ", condition.getCode());
			}

			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add(" AND apr.APRV_ROLE_CODE >= ? ", condition.getCodeFrom());
			}
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add(" AND apr.APRV_ROLE_CODE <= ? ", condition.getCodeTo());
			}
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.addLikeFront(" AND apr.APRV_ROLE_CODE ? ", condition.getCodeLike());
			}
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				sql.addNLikeAmbi(" AND apr.APRV_ROLE_NAME_S ? ", condition.getNamesLike());
			}
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql.addNLikeAmbi(" AND apr.APRV_ROLE_NAME_K ? ", condition.getNamekLike());
			}

			// 有効期間
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				sql.add(" AND apr.STR_DATE <= ?", condition.getValidTerm());
				sql.add(" AND apr.END_DATE >= ?", condition.getValidTerm());
			}
			// ユーザー
			if (!Util.isNullOrEmpty(condition.getUserCode())) {
				sql.add(" AND EXISTS (SELECT 1 ");
				sql.add("             FROM APRV_ROLE_MST r ");
				sql.add("             INNER JOIN APRV_ROLE_GRP_MST g ON g.KAI_CODE = r.KAI_CODE ");
				sql.add("                                           AND g.APRV_ROLE_CODE = r.APRV_ROLE_CODE ");
				sql.add("             WHERE r.KAI_CODE           = apr.KAI_CODE ");
				sql.add("             AND   g.APRV_ROLE_GRP_CODE = grp.APRV_ROLE_GRP_CODE ");
				sql.add("             AND   g.APRV_LEVEL        <= grp.APRV_LEVEL ");
				sql.add("             AND   r.USR_CODE = ? ", condition.getUserCode());
				if (condition.getAprvGrpList() != null && !condition.getAprvGrpList().isEmpty()) {
					sql.add("             AND   (1=2 ");
					for (AprvRoleGroup g : condition.getAprvGrpList()) {
						for (AprvRoleGroupDetail d : g.getDetailList()) {
							sql.add("         OR (g.APRV_ROLE_GRP_CODE = ? ", g.getAPRV_ROLE_GRP_CODE());
							sql.add("         AND g.APRV_ROLE_CODE = ? )", d.getAPRV_ROLE_CODE());
						}
					}
					sql.add("                   ) ");
				}
				sql.add("            ) ");
			}

			sql.add(" ORDER BY ");
			sql.add("  apr.KAI_CODE ");
			sql.add(" ,apr.APRV_ROLE_CODE ");

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
	protected List<AprvRole> mapping(ResultSet rs) throws SQLException {

		List<AprvRole> list = new ArrayList();
		AprvRole entity = null;
		while (rs.next()) {
			String code = rs.getString("APRV_ROLE_CODE");
			if (entity == null || !Util.equals(entity.getAPRV_ROLE_CODE(), code)) {
				entity = new AprvRole();
				entity.setCompanyCode(rs.getString("KAI_CODE")); // 会社コード
				entity.setAPRV_ROLE_CODE(code); // ロールコード
				entity.setAPRV_ROLE_NAME(rs.getString("APRV_ROLE_NAME")); // ロール名称
				entity.setAPRV_ROLE_NAME_S(rs.getString("APRV_ROLE_NAME_S")); // ロール略称
				entity.setAPRV_ROLE_NAME_K(rs.getString("APRV_ROLE_NAME_K")); // ロール検索名称
				entity.setSTR_DATE(rs.getTimestamp("STR_DATE")); // 開始年月日
				entity.setEND_DATE(rs.getTimestamp("END_DATE")); // 終了年月日
				entity.setINP_DATE(rs.getTimestamp("INP_DATE")); // 登録日付
				entity.setUPD_DATE(rs.getTimestamp("UPD_DATE")); // 更新日付
				entity.setPRG_ID(rs.getString("PRG_ID")); // プログラムID
				entity.setUSR_ID(rs.getString("USR_ID")); // ユーザーID
				list.add(entity);
			}
			User usr = new User();
			usr.setCompanyCode(entity.getCompanyCode());
			usr.setCode(rs.getString("USR_CODE"));
			usr.setName(rs.getString("USR_NAME"));
			usr.setNames(rs.getString("USR_NAME_S"));
			usr.setNamek(rs.getString("USR_NAME_K"));
			Department dep = new Department();
			dep.setCompanyCode(entity.getCompanyCode());
			dep.setCode(rs.getString("DEP_CODE"));
			dep.setName(rs.getString("DEP_NAME"));
			dep.setNames(rs.getString("DEP_NAME_S"));
			dep.setNamek(rs.getString("DEP_NAME_K"));
			usr.setDepartment(dep);
			entity.addUser(usr);

		}
		return list;
	}

	/**
	 * 承認権限ロールマスタ登録
	 * 
	 * @param usrList
	 * @throws TException
	 */
	public void entry(List<AprvRole> usrList) throws TException {
		Connection conn = DBUtil.getConnection();
		try {
			for (int i = 0; i < usrList.size(); i++) {
				SQLCreator sql = new SQLCreator();
				AprvRole bean = usrList.get(i);
				sql.add(" INSERT INTO APRV_ROLE_MST ( ");
				sql.add("      KAI_CODE ");
				sql.add("      ,APRV_ROLE_CODE ");
				sql.add("      ,APRV_ROLE_NAME ");
				sql.add("      ,APRV_ROLE_NAME_S ");
				sql.add("      ,APRV_ROLE_NAME_K ");
				sql.add("      ,USR_CODE ");
				sql.add("      ,STR_DATE ");
				sql.add("      ,END_DATE ");
				sql.add("      ,INP_DATE ");
				sql.add("      ,UPD_DATE ");
				sql.add("      ,PRG_ID ");
				sql.add("      ,USR_ID ");
				sql.add(" )VALUES( ");
				sql.add("     ? ", bean.getCompanyCode());
				sql.add("     ,? ", bean.getAPRV_ROLE_CODE());
				sql.add("     ,? ", bean.getAPRV_ROLE_NAME());
				sql.add("     ,? ", bean.getAPRV_ROLE_NAME_S());
				sql.add("     ,? ", bean.getAPRV_ROLE_NAME_K());
				sql.add("     ,? ", bean.getUSR_CODE());
				sql.adt("     ,? ", bean.getSTR_DATE());
				sql.adt("     ,? ", bean.getEND_DATE());
				sql.adt("     ,? ", getNow());
				sql.adt("     ,? ", null);
				sql.add("     ,? ", getProgramCode());
				sql.add("     ,? ", getUserCode());
				sql.add(" ) ");
				DBUtil.execute(conn, sql);
			}

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 承認権限ロールマスタ修正
	 * 
	 * @param usrList
	 * @throws TException
	 */
	public void modify(List<AprvRole> usrList) throws TException {
		Connection conn = DBUtil.getConnection();
		try {

			SQLCreator sql = new SQLCreator();
			AprvRole bean = usrList.get(0);
			sql.add(" DELETE FROM APRV_ROLE_MST ");
			sql.add(" WHERE KAI_CODE       = ? ", bean.getCompanyCode());
			sql.add(" AND   APRV_ROLE_CODE = ? ", bean.getAPRV_ROLE_CODE());
			DBUtil.execute(conn, sql);
			for (int i = 0; i < usrList.size(); i++) {
				bean = usrList.get(i);
				sql = new SQLCreator();
				sql.add(" INSERT INTO APRV_ROLE_MST ( ");
				sql.add("      KAI_CODE ");
				sql.add("      ,APRV_ROLE_CODE ");
				sql.add("      ,APRV_ROLE_NAME ");
				sql.add("      ,APRV_ROLE_NAME_S ");
				sql.add("      ,APRV_ROLE_NAME_K ");
				sql.add("      ,USR_CODE ");
				sql.add("      ,STR_DATE ");
				sql.add("      ,END_DATE ");
				sql.add("      ,INP_DATE ");
				sql.add("      ,UPD_DATE ");
				sql.add("      ,PRG_ID ");
				sql.add("      ,USR_ID ");
				sql.add(" )VALUES( ");
				sql.add("     ? ", bean.getCompanyCode());
				sql.add("     ,? ", bean.getAPRV_ROLE_CODE());
				sql.add("     ,? ", bean.getAPRV_ROLE_NAME());
				sql.add("     ,? ", bean.getAPRV_ROLE_NAME_S());
				sql.add("     ,? ", bean.getAPRV_ROLE_NAME_K());
				sql.add("     ,? ", bean.getUSR_CODE());
				sql.adt("     ,? ", bean.getSTR_DATE());
				sql.adt("     ,? ", bean.getEND_DATE());
				sql.adt("     ,? ", getNow());
				sql.adt("     ,? ", getNow());
				sql.add("     ,? ", getProgramCode());
				sql.add("     ,? ", getUserCode());
				sql.add(" ) ");

				DBUtil.execute(conn, sql);
			}
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
	public void delete(AprvRole bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator sql = new SQLCreator();
			sql.add(" DELETE FROM APRV_ROLE_MST ");
			sql.add(" WHERE KAI_CODE = ? ", bean.getCompanyCode());
			sql.add("     AND APRV_ROLE_CODE = ? ", bean.getAPRV_ROLE_CODE());
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
	public byte[] getExcel(AprvRoleSearchCondition condition) throws TException {

		try {

			// データを抽出
			List<AprvRole> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			AprvRoleExcel exl = new AprvRoleExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

}