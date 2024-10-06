package jp.co.ais.trans2.model.department;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * ïîñÂÉ}ÉlÅ[ÉWÉÉÇÃé¿ëïÉNÉâÉXÇ≈Ç∑ÅB
 * 
 * @author AIS
 */
public class DepartmentOrganizationManagerImpl extends TModel implements DepartmentOrganizationManager {

	public List<Department> get(DepartmentSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Department> list = new ArrayList<Department>();

		try {

			VCreator s = new VCreator();
			s.add("SELECT");
			s.add("  KAI_CODE ");
			s.add("  ,DEP_CODE ");
			s.add("  ,DEP_NAME ");
			s.add("  ,DEP_NAME_S ");
			s.add(" FROM BMN_MST ");
			s.add(" WHERE 1 = 1");

			// âÔé–ÉRÅ[Éh
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				s.add("  AND  KAI_CODE = ? ", condition.getCompanyCode());
			}
			s.add(" ORDER BY DEP_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, s);

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
	 * O/RÉ}ÉbÉsÉìÉO
	 * 
	 * @param rs
	 * @return Department
	 * @throws Exception
	 */
	protected Department mapping(ResultSet rs) throws Exception {

		Department bean = createDepartment();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("DEP_CODE"));
		bean.setName(rs.getString("DEP_NAME"));
		bean.setNames(rs.getString("DEP_NAME_S"));
		return bean;

	}

	/**
	 * @return ïîñÂ
	 */
	protected Department createDepartment() {
		return new Department();
	}

	/**
	 * ïîñÂëgêDäKëwÉRÅ[ÉhÇéÊìæ
	 * 
	 * @param condition
	 * @return List<DepartmentOrganization>
	 * @throws TException
	 */
	public List<DepartmentOrganization> getDepartmentOrganization(DepartmentOrganizationSearchCondition condition)
		throws TException {

		Connection conn = DBUtil.getConnection();
		List<DepartmentOrganization> list = new ArrayList<DepartmentOrganization>();

		try {

			VCreator sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("  DISTINCT(DPK_SSK) DPK_SSK ");
			sql.add(" ,DPK_SSK_NAME ");
			sql.add(" FROM DPK_MST ");
			sql.add(" WHERE KAI_CODE = ? ", condition.getCompanyCode());
			sql.add(" ORDER BY DPK_SSK ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingDepOrg(rs));
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
	 * ïîñÂëgêDäKëw O/RÉ}ÉbÉsÉìÉO
	 * 
	 * @param rs
	 * @return DepartmentOrganization
	 * @throws Exception
	 */
	protected DepartmentOrganization mappingDepOrg(ResultSet rs) throws Exception {

		DepartmentOrganization bean = createOrganization();

		bean.setCode(rs.getString("DPK_SSK"));
		bean.setName(rs.getString("DPK_SSK_NAME"));
		return bean;
	}

	/**
	 * ïîñÂëgêDäKëwÉRÅ[ÉhÇéÊìæ
	 * 
	 * @param condition
	 * @return List<DepartmentOrganization>
	 * @throws TException
	 */
	public List<DepartmentOrganization> getDepartmentOrganizationData(DepartmentOrganizationSearchCondition condition)
		throws TException {

		Connection conn = DBUtil.getConnection();
		List<DepartmentOrganization> list = new ArrayList<DepartmentOrganization>();

		try {

			VCreator sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("  dpk.KAI_CODE ");
			sql.add(" ,dpk.DPK_SSK ");
			sql.add(" ,dpk.DPK_SSK_NAME ");
			sql.add(" ,dpk.DPK_DEP_CODE ");
			sql.add(" ,dep.DEP_NAME ");
			sql.add(" ,dep.DEP_NAME_S ");
			sql.add(" ,dpk.DPK_LVL ");
			sql.add(" ,dpk.DPK_LVL_0 ");
			sql.add(" ,dep0.DEP_NAME DEP_NAME_0 ");
			sql.add(" ,dpk.DPK_LVL_1 ");
			sql.add(" ,dep1.DEP_NAME DEP_NAME_1 ");
			sql.add(" ,dpk.DPK_LVL_2 ");
			sql.add(" ,dep2.DEP_NAME DEP_NAME_2 ");
			sql.add(" ,dpk.DPK_LVL_3 ");
			sql.add(" ,dep3.DEP_NAME DEP_NAME_3 ");
			sql.add(" ,dpk.DPK_LVL_4 ");
			sql.add(" ,dep4.DEP_NAME DEP_NAME_4 ");
			sql.add(" ,dpk.DPK_LVL_5 ");
			sql.add(" ,dep5.DEP_NAME DEP_NAME_5 ");
			sql.add(" ,dpk.DPK_LVL_6 ");
			sql.add(" ,dep6.DEP_NAME DEP_NAME_6 ");
			sql.add(" ,dpk.DPK_LVL_7 ");
			sql.add(" ,dep7.DEP_NAME DEP_NAME_7 ");
			sql.add(" ,dpk.DPK_LVL_8 ");
			sql.add(" ,dep8.DEP_NAME DEP_NAME_8 ");
			sql.add(" ,dpk.DPK_LVL_9 ");
			sql.add(" ,dep9.DEP_NAME DEP_NAME_9 ");
			sql.add(" ,dpk.DPK_LVL_0_NAME ");
			sql.add(" ,dpk.DPK_LVL_1_NAME ");
			sql.add(" ,dpk.DPK_LVL_2_NAME ");
			sql.add(" ,dpk.DPK_LVL_3_NAME ");
			sql.add(" ,dpk.DPK_LVL_4_NAME ");
			sql.add(" ,dpk.DPK_LVL_5_NAME ");
			sql.add(" ,dpk.DPK_LVL_6_NAME ");
			sql.add(" ,dpk.DPK_LVL_7_NAME ");
			sql.add(" ,dpk.DPK_LVL_8_NAME ");
			sql.add(" ,dpk.DPK_LVL_9_NAME ");

			sql.add(" FROM DPK_MST dpk ");
			sql.add(" LEFT JOIN BMN_MST dep ON dep.KAI_CODE = dpk.KAI_CODE ");
			sql.add("                     AND dep.DEP_CODE = dpk.DPK_DEP_CODE ");
			sql.add(" LEFT JOIN BMN_MST dep0 ON dep0.KAI_CODE =  dpk.KAI_CODE ");
			sql.add("                     AND dep0.DEP_CODE = dpk.DPK_LVL_0 ");
			sql.add(" LEFT JOIN BMN_MST dep1 ON dep1.KAI_CODE =  dpk.KAI_CODE ");
			sql.add("                     AND dep1.DEP_CODE = dpk.DPK_LVL_1 ");
			sql.add(" LEFT JOIN BMN_MST dep2 ON dep2.KAI_CODE = dpk.KAI_CODE ");
			sql.add("                     AND dep2.DEP_CODE = dpk.DPK_LVL_2 ");
			sql.add(" LEFT JOIN BMN_MST dep3 ON dep3.KAI_CODE = dpk.KAI_CODE ");
			sql.add("                     AND dep3.DEP_CODE = dpk.DPK_LVL_3 ");
			sql.add(" LEFT JOIN BMN_MST dep4 ON dep4.KAI_CODE = dpk.KAI_CODE ");
			sql.add("                     AND dep4.DEP_CODE = dpk.DPK_LVL_4 ");
			sql.add(" LEFT JOIN BMN_MST dep5 ON dep5.KAI_CODE =  dpk.KAI_CODE ");
			sql.add("                     AND dep5.DEP_CODE = dpk.DPK_LVL_5 ");
			sql.add(" LEFT JOIN BMN_MST dep6 ON dep6.KAI_CODE = dpk.KAI_CODE ");
			sql.add("                     AND dep6.DEP_CODE = dpk.DPK_LVL_6 ");
			sql.add(" LEFT JOIN BMN_MST dep7 ON dep7.KAI_CODE = dpk.KAI_CODE ");
			sql.add("                     AND dep7.DEP_CODE = dpk.DPK_LVL_7 ");
			sql.add(" LEFT JOIN BMN_MST dep8 ON dep8.KAI_CODE = dpk.KAI_CODE ");
			sql.add("                     AND dep8.DEP_CODE = dpk.DPK_LVL_8 ");
			sql.add(" LEFT JOIN BMN_MST dep9 ON dep9.KAI_CODE =  dpk.KAI_CODE ");
			sql.add("                     AND dep9.DEP_CODE = dpk.DPK_LVL_9 ");
			sql.add(" WHERE dpk.KAI_CODE = ?", getCompanyCode());

			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add("  AND dpk.DPK_SSK = ?", condition.getCode());
			}
			sql.add(" ORDER BY CASE WHEN dpk.DPK_LVL = 0 THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_0");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_1 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_1 ");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_2 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_2 ");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_3 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_3 ");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_4 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_4 ");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_5 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_5 ");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_6 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_6 ");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_7 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_7 ");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_8 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_8 ");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_9 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_9 ");
			sql.add("        ,dpk.DPK_DEP_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingHierarchy(rs));
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
	 * éwíËèåèÇ…äYìñÇ∑ÇÈïîñÂëgêDèÓïÒÇï‘Ç∑
	 * 
	 * @param condition åüçıèåè
	 * @return éwíËèåèÇ…äYìñÇ∑ÇÈïîñÂëgêDèÓïÒ(ïîñÂäKëwÉ}ÉXÉ^óp)
	 * @throws TException
	 */
	public DepartmentOrganization getDepartmentOrganizationName(DepartmentOrganizationSearchCondition condition)
		throws TException {

		Connection conn = DBUtil.getConnection();
		DepartmentOrganization bean = null;

		try {

			VCreator sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("  dpk.KAI_CODE ");
			sql.add(" ,dpk.DPK_SSK ");
			sql.add(" ,dpk.DPK_SSK_NAME ");
			sql.add(" ,dpk.DPK_DEP_CODE ");
			sql.add(" ,NULL AS DEP_NAME ");
			sql.add(" ,NULL AS DEP_NAME_S ");
			sql.add(" ,dpk.DPK_LVL ");
			sql.add(" ,dpk.DPK_LVL_0 ");
			sql.add(" ,NULL AS DEP_NAME_0 ");
			sql.add(" ,NULL AS DPK_LVL_1 ");
			sql.add(" ,NULL AS DEP_NAME_1 ");
			sql.add(" ,NULL AS DPK_LVL_2 ");
			sql.add(" ,NULL AS DEP_NAME_2 ");
			sql.add(" ,NULL AS DPK_LVL_3 ");
			sql.add(" ,NULL AS DEP_NAME_3 ");
			sql.add(" ,NULL AS DPK_LVL_4 ");
			sql.add(" ,NULL AS DEP_NAME_4 ");
			sql.add(" ,NULL AS DPK_LVL_5 ");
			sql.add(" ,NULL AS DEP_NAME_5 ");
			sql.add(" ,NULL AS DPK_LVL_6 ");
			sql.add(" ,NULL AS DEP_NAME_6 ");
			sql.add(" ,NULL AS DPK_LVL_7 ");
			sql.add(" ,NULL AS DEP_NAME_7 ");
			sql.add(" ,NULL AS DPK_LVL_8 ");
			sql.add(" ,NULL AS DEP_NAME_8 ");
			sql.add(" ,NULL AS DPK_LVL_9 ");
			sql.add(" ,NULL AS DEP_NAME_9 ");
			sql.add(" ,dpk.DPK_LVL_0_NAME ");
			sql.add(" ,dpk.DPK_LVL_1_NAME ");
			sql.add(" ,dpk.DPK_LVL_2_NAME ");
			sql.add(" ,dpk.DPK_LVL_3_NAME ");
			sql.add(" ,dpk.DPK_LVL_4_NAME ");
			sql.add(" ,dpk.DPK_LVL_5_NAME ");
			sql.add(" ,dpk.DPK_LVL_6_NAME ");
			sql.add(" ,dpk.DPK_LVL_7_NAME ");
			sql.add(" ,dpk.DPK_LVL_8_NAME ");
			sql.add(" ,dpk.DPK_LVL_9_NAME ");

			sql.add(" FROM DPK_MST dpk ");
			sql.add(" WHERE dpk.KAI_CODE = ?", getCompanyCode());

			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add("  AND dpk.DPK_SSK = ?", condition.getCode());
			}

			if (DBUtil.isUseMySQL) {
				sql.add(" AND dpk.DPK_LVL_0_NAME IS NOT NULL ");
				sql.add(" LIMIT 1 ");
			} else {
				sql.add(" AND dpk.DPK_LVL_0_NAME IS NOT NULL ");
				sql.add(" AND ROWNUM = 1 ");
			}

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				bean = mappingHierarchy(rs);
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return bean;

	}

	/**
	 * ïîñÂëgêDäKëw O/RÉ}ÉbÉsÉìÉO
	 * 
	 * @param rs
	 * @return DepartmentOrganization
	 * @throws Exception
	 */
	protected DepartmentOrganization mappingHierarchy(ResultSet rs) throws Exception {

		DepartmentOrganization bean = createOrganization();

		bean.setCode(rs.getString("DPK_SSK"));
		bean.setName(rs.getString("DPK_SSK_NAME"));
		bean.setDepCode(rs.getString("DPK_DEP_CODE"));
		bean.setDepName(rs.getString("DEP_NAME"));
		bean.setDepNames(rs.getString("DEP_NAME_S"));
		bean.setLevel(rs.getInt("DPK_LVL"));
		bean.setLevel0(rs.getString("DPK_LVL_0"));
		bean.setLevel0Name(rs.getString("DEP_NAME_0"));
		bean.setLevel1(rs.getString("DPK_LVL_1"));
		bean.setLevel1Name(rs.getString("DEP_NAME_1"));
		bean.setLevel2(rs.getString("DPK_LVL_2"));
		bean.setLevel2Name(rs.getString("DEP_NAME_2"));
		bean.setLevel3(rs.getString("DPK_LVL_3"));
		bean.setLevel3Name(rs.getString("DEP_NAME_3"));
		bean.setLevel4(rs.getString("DPK_LVL_4"));
		bean.setLevel4Name(rs.getString("DEP_NAME_4"));
		bean.setLevel5(rs.getString("DPK_LVL_5"));
		bean.setLevel5Name(rs.getString("DEP_NAME_5"));
		bean.setLevel6(rs.getString("DPK_LVL_6"));
		bean.setLevel6Name(rs.getString("DEP_NAME_6"));
		bean.setLevel7(rs.getString("DPK_LVL_7"));
		bean.setLevel7Name(rs.getString("DEP_NAME_7"));
		bean.setLevel8(rs.getString("DPK_LVL_8"));
		bean.setLevel8Name(rs.getString("DEP_NAME_8"));
		bean.setLevel9(rs.getString("DPK_LVL_9"));
		bean.setLevel9Name(rs.getString("DEP_NAME_9"));
		bean.setDPK_LVL_0_NAME(rs.getString("DPK_LVL_0_NAME"));
		bean.setDPK_LVL_1_NAME(rs.getString("DPK_LVL_1_NAME"));
		bean.setDPK_LVL_2_NAME(rs.getString("DPK_LVL_2_NAME"));
		bean.setDPK_LVL_3_NAME(rs.getString("DPK_LVL_3_NAME"));
		bean.setDPK_LVL_4_NAME(rs.getString("DPK_LVL_4_NAME"));
		bean.setDPK_LVL_5_NAME(rs.getString("DPK_LVL_5_NAME"));
		bean.setDPK_LVL_6_NAME(rs.getString("DPK_LVL_6_NAME"));
		bean.setDPK_LVL_7_NAME(rs.getString("DPK_LVL_7_NAME"));
		bean.setDPK_LVL_8_NAME(rs.getString("DPK_LVL_8_NAME"));
		bean.setDPK_LVL_9_NAME(rs.getString("DPK_LVL_9_NAME"));

		return bean;
	}

	/**
	 * @return ïîñÂäKëw
	 */
	protected DepartmentOrganization createOrganization() {
		return new DepartmentOrganization();
	}

	/** ïîñÂäKëwLEVEL0ìoò^(êVãK) */
	public void entryDepartmentOrganization(DepartmentOrganization bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator s = new VCreator();

			s.add(" INSERT INTO DPK_MST( ");
			s.add("  KAI_CODE");
			s.add("  ,DPK_SSK ");
			s.add("  ,DPK_SSK_NAME ");
			s.add("  ,DPK_DEP_CODE ");
			s.add("  ,DPK_LVL ");
			s.add("  ,DPK_LVL_0 ");
			s.add("  ,INP_DATE ");
			s.add("  ,PRG_ID ");
			s.add("  ,USR_ID ");
			s.add(" )VALUES( ");
			s.add("  ? ", getCompanyCode());
			s.add("  ,?", bean.getCode());
			s.add("  ,? ", bean.getName());
			s.add("  ,? ", bean.getDepCode());
			s.add("  ,? ", bean.getLevel());
			s.add("  ,? ", bean.getDepCode());
			s.adt("  ,? ", getNow());
			s.add("  ,? ", getProgramCode());
			s.add("  ,? ", getUserCode());
			s.add(" ) ");

			DBUtil.execute(s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ïîñÂäKëwLEVELñºèÃìoò^(êVãK)
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryDepartmentOrganizationName(DepartmentOrganization bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator s = new VCreator();

			s.add("INSERT INTO DPK_MST (");
			s.add("  KAI_CODE ");
			s.add("  ,DPK_SSK ");
			s.add("  ,DPK_DEP_CODE ");
			s.add("  ,DPK_LVL ");
			s.add("  ,DPK_LVL_0 ");
			s.add("  ,INP_DATE ");
			s.add("  ,PRG_ID ");
			s.add("  ,USR_ID");
			s.add(") VALUES (");
			s.add("  ? ", getCompanyCode());
			s.add("  ,? ", bean.getCode());
			s.add("  ,? ", bean.getDepCode());
			s.add("  ,? ", bean.getLevel());
			s.add("  ,? ", bean.getDepCode());
			s.adt("  ,? ", getNow());
			s.add("  ,? ", getProgramCode());
			s.add("  ,? ", getUserCode());
			s.add(" ) ");

			DBUtil.execute(s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ïîñÂäKëwÇ∆ëgêDñºèÃÇìoò^Ç∑ÇÈÅB
	 * 
	 * @param sskCode
	 * @param sskName
	 * @param list ïîñÂäKëw
	 * @throws TException
	 */
	public void entryDepartmentOrganization(String sskCode, String sskName, List<DepartmentOrganization> list)
		throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator s = new VCreator();
			s.add(" DELETE FROM DPK_MST ");
			s.add(" WHERE KAI_CODE = ? ", getCompanyCode());
			s.add("  AND DPK_SSK = ? ", sskCode);
			s.add("  AND DPK_LVL <> 0 ");

			DBUtil.execute(s);

			if (list != null && !list.isEmpty()) {

				for (DepartmentOrganization bean : list) {

					s = new VCreator();

					s.add(" INSERT INTO DPK_MST ( ");
					s.add("  KAI_CODE ");
					s.add("  ,DPK_SSK ");
					s.add("  ,DPK_SSK_NAME ");
					s.add("  ,DPK_DEP_CODE ");
					s.add("  ,DPK_LVL ");
					s.add("  ,DPK_LVL_0 ");
					s.add("  ,DPK_LVL_1 ");
					s.add("  ,DPK_LVL_2 ");
					s.add("  ,DPK_LVL_3 ");
					s.add("  ,DPK_LVL_4 ");
					s.add("  ,DPK_LVL_5 ");
					s.add("  ,DPK_LVL_6 ");
					s.add("  ,DPK_LVL_7 ");
					s.add("  ,DPK_LVL_8 ");
					s.add("  ,DPK_LVL_9 ");
					s.add("  ,INP_DATE ");
					s.add("  ,UPD_DATE ");
					s.add("  ,PRG_ID ");
					s.add("  ,USR_ID ");
					s.add(") VALUES (");
					s.add("  ? ", getCompanyCode());
					s.add("  ,? ", sskCode);
					s.add("  ,? ", sskName);
					s.add("  ,? ", bean.getDepCode());
					s.add("  ,? ", bean.getLevel());
					s.add("  ,? ", bean.getLevel0());
					s.add("  ,? ", bean.getLevel1());
					s.add("  ,? ", bean.getLevel2());
					s.add("  ,? ", bean.getLevel3());
					s.add("  ,? ", bean.getLevel4());
					s.add("  ,? ", bean.getLevel5());
					s.add("  ,? ", bean.getLevel6());
					s.add("  ,? ", bean.getLevel7());
					s.add("  ,? ", bean.getLevel8());
					s.add("  ,? ", bean.getLevel9());
					s.adt("  ,? ", bean.getInpDate());
					s.adt("  ,? ", getNow());
					s.add("  ,? ", getProgramCode());
					s.add("  ,? ", getUserCode());
					s.add(" ) ");

					DBUtil.execute(s);
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ïîñÂäKëwñºèÃÇìoò^Ç∑ÇÈÅB
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryDepartmentNameOrganization(DepartmentOrganization bean) throws TException {

		Connection conn = DBUtil.getConnection();
		try {
			VCreator s = new VCreator();

			s.add(" UPDATE DPK_MST SET ");
			s.add("     KAI_CODE = ? ", getCompanyCode());
			s.add("     ,DPK_SSK_NAME = ? ", bean.getName());
			s.add("     ,DPK_LVL_0_NAME = ? ", bean.getDPK_LVL_0_NAME());
			s.add("     ,DPK_LVL_1_NAME = ? ", bean.getDPK_LVL_1_NAME());
			s.add("     ,DPK_LVL_2_NAME = ? ", bean.getDPK_LVL_2_NAME());
			s.add("     ,DPK_LVL_3_NAME = ? ", bean.getDPK_LVL_3_NAME());
			s.add("     ,DPK_LVL_4_NAME = ? ", bean.getDPK_LVL_4_NAME());
			s.add("     ,DPK_LVL_5_NAME = ? ", bean.getDPK_LVL_5_NAME());
			s.add("     ,DPK_LVL_6_NAME = ? ", bean.getDPK_LVL_6_NAME());
			s.add("     ,DPK_LVL_7_NAME = ? ", bean.getDPK_LVL_7_NAME());
			s.add("     ,DPK_LVL_8_NAME = ? ", bean.getDPK_LVL_8_NAME());
			s.add("     ,DPK_LVL_9_NAME = ? ", bean.getDPK_LVL_9_NAME());
			s.add("     ,UPD_DATE = ? ", getNow());
			s.add("     ,PRG_ID = ? ", getProgramCode());
			s.add("     ,USR_ID = ? ", getUserCode());
			s.add("  WHERE KAI_CODE = ? ", getCompanyCode());
			s.add("  AND DPK_SSK = ? ", bean.getCode());

			DBUtil.execute(s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	// ïîñÂäKëwçÌèú
	public void deleteDepartmentOrganization(DepartmentOrganization bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator s = new VCreator();
			s.add(" DELETE FROM DPK_MST ");
			s.add(" WHERE KAI_CODE = ? ", getCompanyCode());
			s.add("  AND DPK_SSK = ? ", bean.getCode());

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ïîñÂäKëwñºèÃàÍóóÇÉGÉNÉZÉãå`éÆÇ≈ï‘Ç∑
	 * 
	 * @param condition åüçıèåè
	 * @return ÉGÉNÉZÉãå`éÆÇÃïîñÂäKëwàÍóó
	 * @throws TException
	 */
	public byte[] getDepartmentOrganizationNameExcel(DepartmentOrganizationSearchCondition condition) throws TException {

		try {

			// ÉfÅ[É^Çíäèo
			List<DepartmentOrganization> list = getDepartmentOrganizationData(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			DepartmentHierarchyNameExcel excel = new DepartmentHierarchyNameExcel(getUser().getLanguage());
			return excel.getExcelHierarchy(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * SQLóp
	 */
	protected class VCreator extends SQLCreator {

		/**
		 * ÉRÉìÉXÉgÉâÉNÉ^Å[
		 */
		public VCreator() {
			crlf = " ";
		}
	}
}
