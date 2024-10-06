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
 * ���j���[�\���}�l�[�W���̎����N���X
 * @author AIS
 *
 */
public class MenuManagerImpl extends TModel implements MenuManager {

	/**
	 * �w������ɊY������v���O�����̌n��Ԃ�
	 * @param condition ��������
	 * @return �w������ɊY������v���O�����̌n 
	 * @throws TException
	 */
	public List<SystemizedProgram> getSystemizedProgram(MenuSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<SystemizedProgram> list = new ArrayList<SystemizedProgram>();

		try {

			// ������Ή�
			String menuTableName = getMenuTableName(); // ���j���[�\���}�X�^
			String groupTableName = getProgramGroupTableName(); // �O���[�v�}�X�^
			String prgTableName = getProgramTableName(); // �v���O�����}�X�^

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

			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql = sql +
					" AND menu.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode());
			}

			// �v���O�������[��
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

				// ��ЃR�[�h
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
	
				// ��ЃR�[�h
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
				// �v���O�����O���[�v���ς�����玟�̃O���[�v��
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

				// ���j���[�敪��NULL�̏ꍇ�A���X�g�o�^�s�v
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
	 * �w������ɊY������v���O�������̂�Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������v���O��������
	 * @throws TException
	 */
	public String getProgramName(MenuSearchCondition condition) throws TException {

		// ������Ή�
		String menuTableName = getMenuTableName(); // ���j���[�\���}�X�^

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
	 * ���j���[�\���}�X�^ �e�[�u������
	 * 
	 * @return �e�[�u����
	 * @throws TException 
	 */
	protected String getMenuTableName() throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			String tableName = "MENU_MST_" +  Util.avoidNull(getUser().getLanguage()).toUpperCase();
			DBUtil.selectOneInt(conn, "SELECT COUNT(*) FROM " + tableName);

			// ���[�U����ɕR�t���e�[�u�������݂��Ă���Ό���t���e�[�u��
			return tableName;

		} catch (Exception e) {
			// ������΃f�t�H���g
			return "MENU_MST";

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �v���O�����O���[�v�}�X�^ �e�[�u������
	 * 
	 * @return �e�[�u����
	 * @throws TException 
	 */
	protected String getProgramGroupTableName() throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			String tableName = "PRG_GRP_MST_" +  Util.avoidNull(getUser().getLanguage()).toUpperCase();
			DBUtil.selectOneInt(conn, "SELECT COUNT(*) FROM " + tableName);

			// ���[�U����ɕR�t���e�[�u�������݂��Ă���Ό���t���e�[�u��
			return tableName;

		} catch (Exception e) {
			// ������΃f�t�H���g
			return "PRG_GRP_MST";

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �v���O�����}�X�^ �e�[�u������
	 * 
	 * @return �e�[�u����
	 * @throws TException 
	 */
	protected String getProgramTableName() throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			String tableName = "PRG_MST_" +  Util.avoidNull(getUser().getLanguage()).toUpperCase();
			DBUtil.selectOneInt(conn, "SELECT COUNT(*) FROM " + tableName);

			// ���[�U����ɕR�t���e�[�u�������݂��Ă���Ό���t���e�[�u��
			return tableName;

		} catch (Exception e) {
			// ������΃f�t�H���g
			return "PRG_MST";

		} finally {
			DBUtil.close(conn);
		}
	}


	/**
	 * O/R�}�b�s���O
	 * @param rs
	 * @return ���j���[�\��
	 * @throws Exception
	 */
	protected MenuDisp mapping(ResultSet rs) throws Exception {
		
		// ���j���[�敪��NULL�̏ꍇ�A���X�g�o�^�s�v
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
	 * ���j���[�\����o�^����B
	 * 
	 * @param systemList ���j���[�\��
	 * @throws TException
	 */
	public void entry(List<SystemizedProgram> systemList) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			String menuTableName = getMenuTableName(); // ���j���[�\���}�X�^
			String groupTableName = getProgramGroupTableName(); // �O���[�v�}�X�^
			
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

				// ���j���[���X�g
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
	 * 16�i�@�J���[�l��RGB�֓]������
	 * 
	 * @param menuColor
	 * @return color RGB�J���[
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
