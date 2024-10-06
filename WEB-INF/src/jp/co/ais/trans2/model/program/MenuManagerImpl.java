package jp.co.ais.trans2.model.program;

import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * メニュー表示マネージャの実装クラス
 * @author AIS
 *
 */
public class MenuManagerImpl extends TModel implements MenuManager {

	/**
	 * 指定条件に該当するプログラム体系を返す
	 * @param condition 検索条件
	 * @return 指定条件に該当するプログラム体系 
	 * @throws TException
	 */
	public List<SystemizedProgram> getSystemizedProgram(MenuSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<SystemizedProgram> list = new ArrayList<SystemizedProgram>();

		try {

			// 多言語対応
			String menuTableName = getMenuTableName(); // メニュー表示マスタ
			String groupTableName = getProgramGroupTableName(); // グループマスタ
			String prgTableName = getProgramTableName(); // プログラムマスタ

			String sql =
				" SELECT " +
					" menu.KAI_CODE, " +
					" menu.MENU_GRP_CODE, " +
					" prgg.SYS_NAME, " +
					" prgg.MENU_COLOR, " +
					" menu.MENU_PARENT_CODE, " +
					" menu.MENU_CODE, " +
					" menu.MENU_NAME, " +
					" menu.MENU_KBN, " +
					" prgg.DISP_INDEX DISP_INDEX, " +
					" menu.MENU_DISP_INDEX MENU_DISP_INDEX, " +
					" menu.INP_DATE, " +
					" menu.UPD_DATE, " +
					" menu.PRG_ID, " +
					" menu.USR_ID, " +
					" prg.PRG_CODE, " +
					" prg.PRG_NAME, " +
					" prg.PRG_NAME_S, " +
					" prg.PRG_NAME_K, " +
					" prg.COM, " +
					" prg.LD_NAME, " +
					" prg.STR_DATE, " +
					" prg.END_DATE " +
				" FROM " +
					menuTableName + "  menu " +
					" INNER JOIN "+ groupTableName  + " prgg " +
					" ON	menu.KAI_CODE = prgg.KAI_CODE " +
					" AND	menu.MENU_GRP_CODE = prgg.SYS_CODE " +
					" INNER JOIN "+ prgTableName  + " prg " +
					" ON	menu.KAI_CODE = prg.KAI_CODE " +
					" AND	menu.MENU_CODE = prg.PRG_CODE " +
				" WHERE 1 = 1 ";

			// 会社コード
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql = sql +
					" AND menu.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode());
			}

			// プログラムロール
			if (!Util.isNullOrEmpty(condition.getProgramRoleCode())) {
				
				if (!condition.isWithoutProgramRole()) {
					sql = sql +
						" AND	EXISTS ( " +
							" SELECT " +
								" 1 " +
							" FROM " +
								" PRG_ROLE_MST pr " +
							" WHERE	menu.KAI_CODE = pr.KAI_CODE " +
							" AND	menu.MENU_CODE = pr.PRG_CODE " +
							" AND	pr.ROLE_CODE = " + SQLUtil.getParam(condition.getProgramRoleCode()) +
							" AND	menu.MENU_KBN = '2' " +
						" ) ";
				} else {
					sql = sql +
						" AND menu.MENU_KBN = '2'";
				}
				
				sql = sql +
					" UNION ALL " +
					" SELECT " +
						" menu.KAI_CODE, " +
						" menu.MENU_GRP_CODE, " +
						" prgg.SYS_NAME, " +
						" prgg.MENU_COLOR, " +
						" menu.MENU_PARENT_CODE, " +
						" menu.MENU_CODE, " +
						" menu.MENU_NAME, " +
						" menu.MENU_KBN, " +
						" prgg.DISP_INDEX DISP_INDEX, " +
						" menu.MENU_DISP_INDEX MENU_DISP_INDEX, " +
						" menu.INP_DATE, " +
						" menu.UPD_DATE, " +
						" menu.PRG_ID, " +
						" menu.USR_ID, " +
						" NULL, " +
						" NULL, " +
						" NULL, " +
						" NULL, " +
						" NULL, " +
						" NULL, " +
						" NULL, " +
						" NULL " +
					" FROM " +
						menuTableName + "  menu " +
						" INNER JOIN "+ groupTableName  + " prgg " +
						" ON	menu.KAI_CODE = prgg.KAI_CODE " +
						" AND	menu.MENU_GRP_CODE = prgg.SYS_CODE " +
					" WHERE menu.MENU_KBN = '1' ";

				// 会社コード
				if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
					sql = sql +
						" AND menu.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode());
				}
			}
			
			if (condition.isWithBlank()) {
				sql = sql +
					" UNION ALL" +
					" SELECT prgg.KAI_CODE," +
					"        prgg.SYS_CODE," +
					"        prgg.SYS_NAME," +
					"        prgg.MENU_COLOR," +
					"        NULL MENU_PARENT_CODE," +
					"        prgg.SYS_CODE MENU_CODE," +
					"        prgg.SYS_NAME MENU_NAME," +
					"        NULL MENU_KBN," +
					"        prgg.DISP_INDEX DISP_INDEX," +
					"        NULL MENU_DISP_INDEX," +
					"        NULL INP_DATE," +
					"        NULL UPD_DATE," +
					"        NULL PRG_ID," +
					"        NULL USR_ID," +
					"        NULL," +
					"        NULL," +
					"        NULL," +
					"        NULL," +
					"        NULL," +
					"        NULL," +
					"        NULL," +
					"        NULL" +
					"   FROM "+ groupTableName  + " prgg" +
					"  WHERE NOT EXISTS" +
					"        (SELECT 1" +
					"           FROM " + menuTableName + " menu" +
					"          WHERE menu.KAI_CODE = prgg.KAI_CODE" +
					"            AND menu.MENU_GRP_CODE = prgg.SYS_CODE)";
	
				// 会社コード
				if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
					sql = sql +
						" AND	prgg.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode());
				}
			}
			
			sql = sql +
				" ORDER BY " +
					" DISP_INDEX, " +
					" MENU_DISP_INDEX ";

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			List<MenuDisp> menulist = null;
			String sysCode = null;
			SystemizedProgram sysPrg = null;

			while (rs.next()) {
				// プログラムグループが変わったら次のグループへ
				if (!Util.avoidNull(rs.getString("MENU_GRP_CODE")).equals(sysCode)) {
					if (sysCode != null) {
						sysPrg.setMenuDisp(menulist);
						list.add(sysPrg);
					}
					menulist = new ArrayList<MenuDisp>();
					sysPrg = new SystemizedProgram();
					sysPrg.setCompanyCode(rs.getString("KAI_CODE"));
					sysPrg.setProgramGroupCode(rs.getString("MENU_GRP_CODE"));
					sysPrg.setProgramGroupName(rs.getString("SYS_NAME"));
					sysPrg.setMenuColor(changeToRGB(rs.getString("MENU_COLOR")));

				}

				// メニュー区分がNULLの場合、リスト登録不要
				MenuDisp menu = mapping(rs);
				if (menu != null) {
					menulist.add(menu);
				}
				sysCode = Util.avoidNull(rs.getString("MENU_GRP_CODE"));
			}

			if (sysCode != null) { 
				sysPrg.setMenuDisp(menulist);
				list.add(sysPrg);
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
	 * 指定条件に該当するプログラム名称を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するプログラム名称
	 * @throws TException
	 */
	public String getProgramName(MenuSearchCondition condition) throws TException {

		// 多言語対応
		String menuTableName = getMenuTableName(); // メニュー表示マスタ

		SQLCreator sql = new SQLCreator();
		sql.add(" SELECT m.MENU_NAME ");
		sql.add(" FROM " + menuTableName + " m ");
		sql.add(" INNER JOIN PRG_ROLE_MST pr ON pr.KAI_CODE = m.KAI_CODE ");
		sql.add("                           AND pr.PRG_CODE = m.MENU_CODE ");

		sql.add(" WHERE m.KAI_CODE = ? ", getCompanyCode());
		sql.add(" AND   m.MENU_CODE = ? ", condition.getCode());
		if (getUser().getProgramRole() != null) {
			sql.add(" AND pr.ROLE_CODE = ? ", getUser().getProgramRole().getCode());
		}

		String prgName = Util.avoidNull(DBUtil.selectOne(sql));
		return prgName;
	}

	/**
	 * メニュー表示マスタ テーブル名称
	 * 
	 * @return テーブル名
	 * @throws TException 
	 */
	protected String getMenuTableName() throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			String tableName = "MENU_MST_" +  Util.avoidNull(getUser().getLanguage()).toUpperCase();
			DBUtil.selectOneInt(conn, "SELECT COUNT(*) FROM " + tableName);

			// ユーザ言語に紐付くテーブルが存在していれば言語付きテーブル
			return tableName;

		} catch (Exception e) {
			// 無ければデフォルト
			return "MENU_MST";

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * プログラムグループマスタ テーブル名称
	 * 
	 * @return テーブル名
	 * @throws TException 
	 */
	protected String getProgramGroupTableName() throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			String tableName = "PRG_GRP_MST_" +  Util.avoidNull(getUser().getLanguage()).toUpperCase();
			DBUtil.selectOneInt(conn, "SELECT COUNT(*) FROM " + tableName);

			// ユーザ言語に紐付くテーブルが存在していれば言語付きテーブル
			return tableName;

		} catch (Exception e) {
			// 無ければデフォルト
			return "PRG_GRP_MST";

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * プログラムマスタ テーブル名称
	 * 
	 * @return テーブル名
	 * @throws TException 
	 */
	protected String getProgramTableName() throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			String tableName = "PRG_MST_" +  Util.avoidNull(getUser().getLanguage()).toUpperCase();
			DBUtil.selectOneInt(conn, "SELECT COUNT(*) FROM " + tableName);

			// ユーザ言語に紐付くテーブルが存在していれば言語付きテーブル
			return tableName;

		} catch (Exception e) {
			// 無ければデフォルト
			return "PRG_MST";

		} finally {
			DBUtil.close(conn);
		}
	}


	/**
	 * O/Rマッピング
	 * @param rs
	 * @return メニュー表示
	 * @throws Exception
	 */
	protected MenuDisp mapping(ResultSet rs) throws Exception {
		
		// メニュー区分がNULLの場合、リスト登録不要
		if (rs.getString("MENU_KBN") == null) {
			return null;
		}

		SystemClassification systemClassification = new SystemClassification();
		systemClassification.setCode(rs.getString("MENU_GRP_CODE"));
		systemClassification.setName(rs.getString("SYS_NAME"));
		systemClassification.setMenuColor(changeToRGB(rs.getString("MENU_COLOR")));
		
		MenuDisp bean = new MenuDisp();
		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setSystemClassification(systemClassification);
		bean.setParentCode(rs.getString("MENU_PARENT_CODE"));
		bean.setCode(rs.getString("MENU_CODE"));
		bean.setMenuDivision(MenuDivision.get(rs.getString("MENU_KBN")));
		bean.setDispIndex(rs.getInt("MENU_DISP_INDEX"));

		bean.setName(rs.getString("MENU_NAME"));
		if (!bean.isMenu()) {
			if (Util.isNullOrEmpty(bean.getName())) {
				bean.setName(Util.avoidNull(rs.getString("PRG_NAME_S")));
			}
		}
		
		Program program = new Program();
		program.setCode(rs.getString("PRG_CODE"));
		program.setName(rs.getString("PRG_NAME"));
		program.setNames(rs.getString("PRG_NAME_S"));
		program.setNamek(rs.getString("PRG_NAME_K"));
		program.setComment(rs.getString("COM"));
		program.setLoadClassName(rs.getString("LD_NAME"));
		program.setTermFrom(rs.getDate("STR_DATE"));
		program.setTermTo(rs.getDate("END_DATE"));
		bean.setProgram(program);

		return bean;

	}


	/**
	 * メニュー表示を登録する。
	 * 
	 * @param systemList メニュー表示
	 * @throws TException
	 */
	public void entry(List<SystemizedProgram> systemList) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			String menuTableName = getMenuTableName(); // メニュー表示マスタ
			String groupTableName = getProgramGroupTableName(); // グループマスタ
			
			String sql = "";

			sql =
				" DELETE FROM " + groupTableName +
			    " WHERE KAI_CODE = " + SQLUtil.getParam(systemList.get(0).getCompanyCode());

			DBUtil.execute(conn, sql);

			sql =
				" DELETE FROM " + menuTableName +
			    " WHERE KAI_CODE = " + SQLUtil.getParam(systemList.get(0).getCompanyCode());

			DBUtil.execute(conn, sql);

			for (SystemizedProgram sys : systemList) {

				sql =
					" INSERT INTO " + groupTableName + " ( " +
						" KAI_CODE, " +
						" SYS_CODE, " +
						" SYS_NAME, " +
						" INP_DATE, " +
						" UPD_DATE, " +
						" PRG_ID, " +
						" USR_ID, " +
						" DISP_INDEX, " +
						" MENU_COLOR " +
					" ) VALUES (" +
						SQLUtil.getParam(sys.getCompanyCode()) + ", " +
						SQLUtil.getParam(sys.getProgramGroupCode()) + ", " +
						SQLUtil.getParam(sys.getProgramGroupName()) + ", " +
						SQLUtil.getYMDHMSParam(getNow()) + ", " +
						" NULL, " +
						SQLUtil.getParam(getProgramCode()) + ", " +
						SQLUtil.getParam(getUserCode()) + ", " +
						sys.getDispIndex() + ", " +
						SQLUtil.getParam(Util.to16RGBColorCode(sys.getMenuColor())) +
					" ) ";

				DBUtil.execute(conn, sql);

				// メニューリスト
				List<MenuDisp> menuList = sys.getMenuDisp();

				for (MenuDisp menu : menuList) {

					sql =
						" INSERT INTO " + menuTableName + " ( " +
							" KAI_CODE, " +
							" MENU_GRP_CODE, " +
							" MENU_PARENT_CODE, " +
							" MENU_CODE, " +
							" MENU_NAME, " +
							" MENU_KBN, " +
							" MENU_DISP_INDEX, " +
							" INP_DATE, " +
							" UPD_DATE, " +
							" PRG_ID, " +
							" USR_ID " +
						" ) VALUES (" +
							SQLUtil.getParam(menu.getCompanyCode()) + ", " +
							SQLUtil.getParam(menu.getSystemClassification().getCode()) + ", " +
							SQLUtil.getParam(menu.getParentCode()) + ", " +
							SQLUtil.getParam(menu.getCode()) + ", " +
							SQLUtil.getParam(menu.getName()) + ", " +
							SQLUtil.getParam(menu.getMenuDivision().value) + ", " +
							menu.getDispIndex() + ", " +
							SQLUtil.getYMDHMSParam(getNow()) + ", " +
							" NULL, " +
							SQLUtil.getParam(getProgramCode()) + ", " +
							SQLUtil.getParam(getUserCode()) +
						" ) ";

					DBUtil.execute(conn, sql);
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 16進法カラー値をRGBへ転換する
	 * 
	 * @param menuColor
	 * @return color RGBカラー
	 */
	public Color changeToRGB(String menuColor) {

		if (menuColor == null) {
			return null;
		}

		String r = menuColor.substring(1, 3);
		int red = Integer.parseInt(r, 16);
		String g = menuColor.substring(3, 5);
		int green = Integer.parseInt(g, 16);
		String b = menuColor.substring(5, 7);
		int blue = Integer.parseInt(b, 16);
		Color color = new Color(red, green, blue);
		return color;
	}
}
