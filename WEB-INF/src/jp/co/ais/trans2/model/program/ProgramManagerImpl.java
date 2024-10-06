package jp.co.ais.trans2.model.program;

import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * �v���O�����}�l�[�W���̎����N���X
 * @author AIS
 *
 */
public class ProgramManagerImpl extends TModel implements ProgramManager {

	/**
	 * �w������ɊY������v���O�����̌n��Ԃ�
	 * @param condition ��������
	 * @return �w������ɊY������v���O�����̌n 
	 * @throws TException
	 */
	public List<SystemizedProgram> getSystemizedProgram(ProgramSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<SystemizedProgram> list = new ArrayList<SystemizedProgram>();

		try {
			// ������Ή�
			String prgTableName = getProgramTableName(); // �v���O�����}�X�^
			String groupTableName = getProgramGroupTableName(); // �O���[�v�}�X�^
			
			String sql =
				" SELECT " +
					" prg.KAI_CODE, " +
					" prg.SYS_CODE, " +
					" prgg.SYS_NAME, " +
					" prgg.MENU_COLOR, " +
					" '1' SYS_KBN_NAME_S, " +
					" prg.PRG_CODE, " +
					" prg.PRG_NAME, " +
					" prg.PRG_NAME_S, " +
					" prg.PRG_NAME_K, " +
					" prg.KEN, " +
					" prg.COM, " +
					" prg.LD_NAME, " +
					" prg.STR_DATE, " +
					" prg.END_DATE, " +
					" prg.INP_DATE, " +
					" prg.UPD_DATE, " +
					" prg.PRG_ID, " +
					" prg.USR_ID, " +
					" prg.PARENT_PRG_CODE, " +
					" prg.MENU_KBN, " +
					" prg.DISP_INDEX " +
				" FROM " +
					prgTableName + "  prg " +
					" INNER JOIN "+ groupTableName  + " prgg " +
					" ON	prg.KAI_CODE = prgg.KAI_CODE " +
					" AND	prg.SYS_CODE = prgg.SYS_CODE " +
				" WHERE 1 = 1 ";

			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql = sql +
					" AND	prg.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode());
			}

			// �v���O�������[��
			if (!Util.isNullOrEmpty(condition.getProgramRoleCode())) {
				sql = sql +
					" AND (EXISTS ( " +
						" SELECT " +
							" 1 " +
						" FROM " +
							" PRG_ROLE_MST pr " +
						" WHERE	prg.KAI_CODE = pr.KAI_CODE " +
						" AND	prg.PRG_CODE = pr.PRG_CODE " +
						" AND	pr.ROLE_CODE = " + SQLUtil.getParam(condition.getProgramRoleCode()) +
						" OR prg.MENU_KBN = 0) " +
					" ) ";
			}
	
			sql = sql +
				" ORDER BY " +
					" prgg.DISP_INDEX, " +
					" prg.SYS_CODE," +
					" prg.DISP_INDEX ";

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			List<Program> prglist = null;
			String sysCode = null;
			SystemizedProgram sysPrg = null;

			while (rs.next()) {
				// �v���O�����O���[�v���ς�����玟�̃O���[�v��
				if (!Util.avoidNull(rs.getString("SYS_CODE")).equals(sysCode)) {
					if (sysCode != null) {
						sysPrg.setPrograms(prglist);
						list.add(sysPrg);
					}
					prglist = new ArrayList<Program>();
					sysPrg = new SystemizedProgram();
					sysPrg.setCompanyCode(rs.getString("KAI_CODE"));
					sysPrg.setProgramGroupCode(rs.getString("SYS_CODE"));
					sysPrg.setProgramGroupName(rs.getString("SYS_NAME"));
					sysPrg.setMenuColor(changeToRGB(rs.getString("MENU_COLOR")));

				}
				prglist.add(mapping(rs));
				sysCode = Util.avoidNull(rs.getString("SYS_CODE"));
			}

			if (sysCode != null) { 
				sysPrg.setPrograms(prglist);
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
	 * �w������ɊY������v���O������Ԃ�
	 * @param condition ��������
	 * @return �w������ɊY������v���O���� 
	 * @throws TException
	 */
	public List<Program> get(ProgramSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Program> list = new ArrayList<Program>();

		try {

			String sql =
				" SELECT " +
					" prg.KAI_CODE, " +
					" prg.SYS_CODE, " +
					" ss.SYS_NAME, " +
					" prg.PRG_CODE, " +
					" prg.PRG_NAME, " +
					" prg.PRG_NAME_S, " +
					" prg.PRG_NAME_K, " +
					" prg.KEN, " +
					" prg.COM, " +
					" prg.LD_NAME, " +
					" prg.STR_DATE, " +
					" prg.END_DATE, " +
					" prg.INP_DATE, " +
					" prg.UPD_DATE, " +
					" prg.PRG_ID, " +
					" prg.USR_ID, " +
					" prg.PARENT_PRG_CODE, " +
					" prg.MENU_KBN, " +
					" prg.DISP_INDEX " +
				" FROM " +
					" PRG_MST prg " +
					" LEFT OUTER JOIN PRG_GRP_MST ss " +
					" ON	prg.KAI_CODE = ss.KAI_CODE " +
					" AND	prg.SYS_CODE = ss.SYS_CODE " +
				" WHERE 1 = 1 ";

			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql = sql + " AND	prg.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode());
			}

			// �V�X�e���敪
			if (!Util.isNullOrEmpty(condition.getSystemCode())) {
				sql = sql + " AND	prg.SYS_CODE = " + SQLUtil.getParam(condition.getSystemCode());
			}

			// �R�[�h��v
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql = sql + " AND	prg.PRG_CODE = " + SQLUtil.getParam(condition.getCode());
			}

			// ���s�\�ȃv���O�����̂�
			if (!condition.isExecutable() || !condition.isNotExecutable())  {
				if (condition.isExecutable()) {
					sql = sql + " AND	prg.MENU_KBN = 1 ";
				}
				if (condition.isNotExecutable()) {
					sql = sql + " AND	prg.MENU_KBN = 0 ";
				}
			}

			// �R�[�h�J�n
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql = sql + " AND	prg.PRG_CODE >= " + SQLUtil.getParam(condition.getCodeFrom());
			}

			// �R�[�h�I��
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql = sql + " AND	prg.PRG_CODE <= " + SQLUtil.getParam(condition.getCodeTo());
			}

			// �L������
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				sql = sql +
					" AND	prg.STR_DATE <= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm())+ " AND	prg.END_DATE >= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm());
			}

			// �R�[�h�O����v
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql = sql + " AND	prg.PRG_CODE "
					+ SQLUtil.getLikeStatement(condition.getCodeLike(), SQLUtil.CHAR_FRONT);
			}

			// ���̂����܂�
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				sql = sql + " AND	prg.PRG_NAME_S "
					+ SQLUtil.getLikeStatement(condition.getNamesLike(), SQLUtil.NCHAR_AMBI);
			}

			// �������̂����܂�
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql = sql + " AND	prg.PRG_NAME_K "
					+ SQLUtil.getLikeStatement(condition.getNamekLike(), SQLUtil.NCHAR_AMBI);
			}
			
			// ���j���[�\���ȊO�̂�
			if (condition.isWithoutMenu()){
		        sql = sql
		        + " AND NOT EXISTS (SELECT 1 "
		        + "                   FROM MENU_MST menu "
		        + "                  WHERE menu.KAI_CODE = prg.KAI_CODE "
		        + "                    AND menu.MENU_CODE = prg.PRG_CODE) ";
			}

			sql = sql +
				" ORDER BY " +
					" prg.SYS_CODE, " +
					" prg.PRG_CODE ";

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
	 * O/R�}�b�s���O
	 * @param rs
	 * @return �v���O����
	 * @throws Exception
	 */
	protected Program mapping(ResultSet rs) throws Exception {

		Program bean = new Program();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		SystemClassification systemClassification = new SystemClassification();
		systemClassification.setCode(rs.getString("SYS_CODE"));
		systemClassification.setName(rs.getString("SYS_NAME"));
		bean.setSystemClassification(systemClassification);
		bean.setSysCode(rs.getString("SYS_CODE")); // �V�X�e���敪�R�[�h
		bean.setCode(rs.getString("PRG_CODE"));
		bean.setName(rs.getString("PRG_NAME"));
		bean.setNames(rs.getString("PRG_NAME_S"));
		bean.setNamek(rs.getString("PRG_NAME_K"));
		bean.setComment(rs.getString("COM"));
		bean.setLoadClassName(rs.getString("LD_NAME"));
		bean.setTermFrom(rs.getDate("STR_DATE"));
		bean.setTermTo(rs.getDate("END_DATE"));
		bean.setParentPrgCode(rs.getString("PARENT_PRG_CODE"));
		bean.setMenuKbn(rs.getInt("MENU_KBN"));

		return bean;

	}

	/**
	 * �w������ɊY������V�X�e����Ԃ�
	 * @param condition ��������
	 * @return �w������ɊY������V�X�e�� 
	 * @throws TException
	 */
	public List<SystemClassification> getSystem(SystemClassificationSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<SystemClassification> list = new ArrayList<SystemClassification>();

		try {

			String sql =
				" SELECT " +
					" ss.KAI_CODE, " +
					" ss.SYS_CODE, " +
					" ss.SYS_NAME, " +
					" ss.INP_DATE, " +
					" ss.UPD_DATE, " +
					" ss.PRG_ID, " +
					" ss.USR_ID, " +
					" ss.DISP_INDEX "+
				" FROM " +
					" PRG_GRP_MST ss " +
				" WHERE 1 = 1 ";

			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql = sql + " AND	ss.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode());
			}

			// �R�[�h��v
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql = sql + " AND	ss.SYS_CODE = " + SQLUtil.getParam(condition.getCode());
			}

			// �R�[�h�O����v
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql = sql + " AND	ss.SYS_CODE "
					+ SQLUtil.getLikeStatement(condition.getCodeLike(), SQLUtil.CHAR_FRONT);
			}

			// �������̂����܂�
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				sql = sql + " AND	ss.SYS_NAME "
					+ SQLUtil.getLikeStatement(condition.getNamesLike(), SQLUtil.NCHAR_AMBI);
			}

			sql = sql +
				" ORDER BY " +
					" ss.SYS_CODE ";

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingSystem(rs));
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
	 * System��O/R�}�b�s���O
	 * @param rs
	 * @return �V�X�e��
	 * @throws Exception
	 */
	protected SystemClassification mappingSystem(ResultSet rs) throws Exception {

		SystemClassification bean = new SystemClassification();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("SYS_CODE"));
		bean.setName(rs.getString("SYS_NAME"));
 
		return bean;

	}

	public void entry(Program program) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			// �V�X�e���敪�R�[�h
			String sysCode = program.getSystemClassification().code;
			if (!Util.isNullOrEmpty(program.getSysCode())) {
				sysCode = program.getSysCode();
			}

			String sql =
				" INSERT INTO PRG_MST ( " +
					" KAI_CODE, " +
					" SYS_CODE, " +
					" PRG_CODE, " +
					" PRG_NAME, " +
					" PRG_NAME_S, " +
					" PRG_NAME_K, " +
					" KEN, " +
					" COM, " +
					" LD_NAME, " +
					" STR_DATE, " +
					" END_DATE, " +
					" INP_DATE, " +
					" UPD_DATE, " +
					" PRG_ID, " +
					" USR_ID, " +
					" PARENT_PRG_CODE, " +
					" MENU_KBN, " +
					" DISP_INDEX " +
				" ) VALUES (" +
					SQLUtil.getParam(program.getCompanyCode()) + ", " +
					SQLUtil.getParam(sysCode) + ", " +
					SQLUtil.getParam(program.getCode()) + ", " +
					SQLUtil.getParam(program.getName()) + ", " +
					SQLUtil.getParam(program.getNames()) + ", " +
					SQLUtil.getParam(program.getNamek()) + ", " +
					" 0, " +
					SQLUtil.getParam(program.getComment()) + ", " +
					SQLUtil.getParam(program.getLoadClassName()) + ", " +
					SQLUtil.getYYYYMMDDParam(program.getTermFrom()) + ", " +
					SQLUtil.getYYYYMMDDParam(program.getTermTo()) + ", " +
					SQLUtil.getYMDHMSParam(getNow()) + ", " +
					" NULL, " +
					SQLUtil.getParam(getProgramCode()) + ", " +
					SQLUtil.getParam(getUserCode()) + ", " +
					" NULL, " +
					" 1, " +
					" 0 " +
				" ) ";
	
			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void modify(Program program) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			// �V�X�e���敪�R�[�h
			String sysCode = program.getSystemClassification().code;
			if (!Util.isNullOrEmpty(program.getSysCode())) {
				sysCode = program.getSysCode();
			}
			
			String sql =
				" UPDATE PRG_MST " +
				" SET " +
			        " SYS_CODE = " + SQLUtil.getParam(sysCode) + ", " +
					" PRG_NAME = " + SQLUtil.getParam(program.getName()) + ", " +
					" PRG_NAME_S = " + SQLUtil.getParam(program.getNames()) + ", " +
					" PRG_NAME_K = " + SQLUtil.getParam(program.getNamek()) + ", " +
					" STR_DATE = " + SQLUtil.getParam(DateUtil.toYMDString(program.getTermFrom())) + ", " +
					" END_DATE = " + SQLUtil.getParam(DateUtil.toYMDString(program.getTermTo())) + ", " +
					" COM =      " + SQLUtil.getParam(program.getComment()) + ", " +
					" LD_NAME =  " + SQLUtil.getParam(program.getLoadClassName()) + ", " +
					" UPD_DATE = " + SQLUtil.getYMDHMSParam(getNow()) + ", " +
					" PRG_ID = " + SQLUtil.getParam(getProgramCode()) + ", " +
					" USR_ID = " + SQLUtil.getParam(getUserCode()) + 
			    " WHERE KAI_CODE = " + SQLUtil.getParam(program.getCompanyCode()) +
				" AND	PRG_CODE = " + SQLUtil.getParam(program.getCode());
			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void delete(Program program) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			// �V�X�e���敪�R�[�h
			String sysCode = program.getSystemClassification().getCode();
			if (!Util.isNullOrEmpty(program.getSysCode())) {
				sysCode = program.getSysCode();
			}
			
			String sql =
				" DELETE FROM PRG_MST " +
			    " WHERE KAI_CODE = " + SQLUtil.getParam(program.getCompanyCode()) +
				" AND	PRG_CODE = " + SQLUtil.getParam(program.getCode()) +
				" AND   SYS_CODE = " + SQLUtil.getParam(sysCode);

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}
	/**
	 * �v���O����(�G�N�Z��)��Ԃ�
	 * 
	 * @param condition ��������
	 * @return �v���O����
	 * @throws TException
	 */
	public byte[] getExcel(ProgramSearchCondition condition) throws TException {
		List<Program> list = get(condition);
		if (list == null || list.isEmpty()) {
			return null;
		}

		ProgramExcel layout = new ProgramExcel(condition.getLang());
		byte[] data = layout.getExcel(list);

		return data;

	}
	/**
	 * 16�i�@�J���[�l��RGB�֓]������
	 * 
	 * @param menuColor 
	 * @return color RGB�J���[
	 */
	public Color changeToRGB(String menuColor){
		if(menuColor == null){
			return null;
		}
		
		// 
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
