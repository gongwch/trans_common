package jp.co.ais.trans2.model.company;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * 会社階層コントロールマスタの実装
 * 
 * @author AIS
 */
public class CompanyOrganizationManagerImpl extends TModel implements CompanyOrganizationManager {

	public List<Company> get(CompanySearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Company> list = new ArrayList<Company>();

		try {

			VCreator sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("	 cmp.KAI_CODE ");
			sql.add("	 ,env.KAI_NAME ");
			sql.add("	 ,env.KAI_NAME_S ");
			sql.add(" FROM ");
			sql.add("	 CMP_MST cmp ");
			sql.add("	 LEFT OUTER JOIN ENV_MST env ");
			sql.add("	 ON	env.KAI_CODE = cmp.KAI_CODE ");
			sql.add(" ORDER BY cmp.KAI_CODE ");

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
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected Company mapping(ResultSet rs) throws Exception {

		Company bean = createCompany();
		bean.setCode(rs.getString("KAI_CODE"));
		bean.setName(rs.getString("KAI_NAME"));
		bean.setNames(rs.getString("KAI_NAME_S"));
		return bean;
	}

	/**
	 * @return 会社
	 */
	protected Company createCompany() {
		return new Company();
	}

	/**
	 * 指定条件に該当する会社組織情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する会社組織情報
	 * @throws TException
	 */
	public List<CompanyOrganization> getCompanyOrganization(CompanyOrganizationSearchCondition condition)
		throws TException {

		Connection conn = DBUtil.getConnection();
		List<CompanyOrganization> list = new ArrayList<CompanyOrganization>();

		try {

			VCreator sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("  DISTINCT(DPK_SSK) DPK_SSK ");
			sql.add(" ,DPK_SSK_NAME ");
			sql.add(" FROM EVK_MST ");
			sql.add(" WHERE KAI_CODE = ? ", getCompanyCode());
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
	 * 会社組織階層 O/Rマッピング
	 * 
	 * @param rs
	 * @return CompanyOrganization
	 * @throws Exception
	 */
	protected CompanyOrganization mappingDepOrg(ResultSet rs) throws Exception {

		CompanyOrganization bean = createOrganization();

		bean.setCode(rs.getString("DPK_SSK"));
		bean.setName(rs.getString("DPK_SSK_NAME"));

		return bean;
	}

	/**
	 * 指定条件に該当する会社組織情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する会社組織情報(会社階層マスタ用)
	 * @throws TException
	 */
	public List<CompanyOrganization> getCompanyOrganizationData(CompanyOrganizationSearchCondition condition)
		throws TException {

		Connection conn = DBUtil.getConnection();
		List<CompanyOrganization> list = new ArrayList<CompanyOrganization>();

		try {

			VCreator sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("  evk.KAI_CODE ");
			sql.add(" ,evk.DPK_SSK ");
			sql.add(" ,evk.DPK_SSK_NAME ");
			sql.add(" ,evk.DPK_DEP_CODE ");
			sql.add(" ,env.KAI_NAME ");
			sql.add(" ,env.KAI_NAME_S ");
			sql.add(" ,evk.DPK_LVL ");
			sql.add(" ,evk.DPK_LVL_0 ");
			sql.add(" ,env0.KAI_NAME KAI_NAME_0 ");
			sql.add(" ,evk.DPK_LVL_1 ");
			sql.add(" ,env1.KAI_NAME KAI_NAME_1 ");
			sql.add(" ,evk.DPK_LVL_2 ");
			sql.add(" ,env2.KAI_NAME KAI_NAME_2 ");
			sql.add(" ,evk.DPK_LVL_3 ");
			sql.add(" ,env3.KAI_NAME KAI_NAME_3 ");
			sql.add(" ,evk.DPK_LVL_4 ");
			sql.add(" ,env4.KAI_NAME KAI_NAME_4 ");
			sql.add(" ,evk.DPK_LVL_5 ");
			sql.add(" ,env5.KAI_NAME KAI_NAME_5 ");
			sql.add(" ,evk.DPK_LVL_6 ");
			sql.add(" ,env6.KAI_NAME KAI_NAME_6 ");
			sql.add(" ,evk.DPK_LVL_7 ");
			sql.add(" ,env7.KAI_NAME KAI_NAME_7 ");
			sql.add(" ,evk.DPK_LVL_8 ");
			sql.add(" ,env8.KAI_NAME KAI_NAME_8 ");
			sql.add(" ,evk.DPK_LVL_9 ");
			sql.add(" ,env9.KAI_NAME KAI_NAME_9 ");
			sql.add(" ,evk.DPK_LVL_0_NAME ");
			sql.add(" ,evk.DPK_LVL_1_NAME ");
			sql.add(" ,evk.DPK_LVL_2_NAME ");
			sql.add(" ,evk.DPK_LVL_3_NAME ");
			sql.add(" ,evk.DPK_LVL_4_NAME ");
			sql.add(" ,evk.DPK_LVL_5_NAME ");
			sql.add(" ,evk.DPK_LVL_6_NAME ");
			sql.add(" ,evk.DPK_LVL_7_NAME ");
			sql.add(" ,evk.DPK_LVL_8_NAME ");
			sql.add(" ,evk.DPK_LVL_9_NAME ");

			sql.add(" FROM EVK_MST evk");
			sql.add(" LEFT JOIN ENV_MST env ON env.KAI_CODE = evk.DPK_DEP_CODE ");
			sql.add(" LEFT JOIN ENV_MST env0 ON env0.KAI_CODE = evk.DPK_LVL_0 ");
			sql.add(" LEFT JOIN ENV_MST env1 ON env1.KAI_CODE = evk.DPK_LVL_1 ");
			sql.add(" LEFT JOIN ENV_MST env2 ON env2.KAI_CODE = evk.DPK_LVL_2 ");
			sql.add(" LEFT JOIN ENV_MST env3 ON env3.KAI_CODE = evk.DPK_LVL_3 ");
			sql.add(" LEFT JOIN ENV_MST env4 ON env4.KAI_CODE = evk.DPK_LVL_4 ");
			sql.add(" LEFT JOIN ENV_MST env5 ON env5.KAI_CODE = evk.DPK_LVL_5 ");
			sql.add(" LEFT JOIN ENV_MST env6 ON env6.KAI_CODE = evk.DPK_LVL_6 ");
			sql.add(" LEFT JOIN ENV_MST env7 ON env7.KAI_CODE = evk.DPK_LVL_7 ");
			sql.add(" LEFT JOIN ENV_MST env8 ON env8.KAI_CODE = evk.DPK_LVL_8 ");
			sql.add(" LEFT JOIN ENV_MST env9 ON env9.KAI_CODE = evk.DPK_LVL_9 ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND evk.KAI_CODE = ? ", getCompanyCode());

			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add("  AND evk.DPK_SSK = ?", condition.getCode());
			}

			sql.add(" ORDER BY CASE WHEN evk.DPK_LVL = 0 THEN 0 ELSE 1 END ");
			sql.add("        ,evk.DPK_LVL_0");
			sql.add("        ,CASE WHEN evk.DPK_LVL_1 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,evk.DPK_LVL_1 ");
			sql.add("        ,CASE WHEN evk.DPK_LVL_2 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,evk.DPK_LVL_2 ");
			sql.add("        ,CASE WHEN evk.DPK_LVL_3 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,evk.DPK_LVL_3 ");
			sql.add("        ,CASE WHEN evk.DPK_LVL_4 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,evk.DPK_LVL_4 ");
			sql.add("        ,CASE WHEN evk.DPK_LVL_5 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,evk.DPK_LVL_5 ");
			sql.add("        ,CASE WHEN evk.DPK_LVL_6 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,evk.DPK_LVL_6 ");
			sql.add("        ,CASE WHEN evk.DPK_LVL_7 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,evk.DPK_LVL_7 ");
			sql.add("        ,CASE WHEN evk.DPK_LVL_8 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,evk.DPK_LVL_8 ");
			sql.add("        ,CASE WHEN evk.DPK_LVL_9 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,evk.DPK_LVL_9 ");
			sql.add("        ,evk.DPK_DEP_CODE ");

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
	 * 指定条件に該当する会社組織情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する会社組織情報(会社階層マスタ用)
	 * @throws TException
	 */
	public CompanyOrganization getCompanyOrganizationName(CompanyOrganizationSearchCondition condition)
		throws TException {

		Connection conn = DBUtil.getConnection();
		CompanyOrganization bean = null;

		try {

			VCreator sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("  evk.KAI_CODE ");
			sql.add(" ,evk.DPK_SSK ");
			sql.add(" ,evk.DPK_SSK_NAME ");
			sql.add(" ,evk.DPK_DEP_CODE ");
			sql.add(" ,NULL AS KAI_NAME ");
			sql.add(" ,NULL AS KAI_NAME_S ");
			sql.add(" ,evk.DPK_LVL ");
			sql.add(" ,evk.DPK_LVL_0 ");
			sql.add(" ,NULL AS KAI_NAME_0 ");
			sql.add(" ,NULL AS DPK_LVL_1 ");
			sql.add(" ,NULL AS KAI_NAME_1 ");
			sql.add(" ,NULL AS DPK_LVL_2 ");
			sql.add(" ,NULL AS KAI_NAME_2 ");
			sql.add(" ,NULL AS DPK_LVL_3 ");
			sql.add(" ,NULL AS KAI_NAME_3 ");
			sql.add(" ,NULL AS DPK_LVL_4 ");
			sql.add(" ,NULL AS KAI_NAME_4 ");
			sql.add(" ,NULL AS DPK_LVL_5 ");
			sql.add(" ,NULL AS KAI_NAME_5 ");
			sql.add(" ,NULL AS DPK_LVL_6 ");
			sql.add(" ,NULL AS KAI_NAME_6 ");
			sql.add(" ,NULL AS DPK_LVL_7 ");
			sql.add(" ,NULL AS KAI_NAME_7 ");
			sql.add(" ,NULL AS DPK_LVL_8 ");
			sql.add(" ,NULL AS KAI_NAME_8 ");
			sql.add(" ,NULL AS DPK_LVL_9 ");
			sql.add(" ,NULL AS KAI_NAME_9 ");
			sql.add(" ,evk.DPK_LVL_0_NAME ");
			sql.add(" ,evk.DPK_LVL_1_NAME ");
			sql.add(" ,evk.DPK_LVL_2_NAME ");
			sql.add(" ,evk.DPK_LVL_3_NAME ");
			sql.add(" ,evk.DPK_LVL_4_NAME ");
			sql.add(" ,evk.DPK_LVL_5_NAME ");
			sql.add(" ,evk.DPK_LVL_6_NAME ");
			sql.add(" ,evk.DPK_LVL_7_NAME ");
			sql.add(" ,evk.DPK_LVL_8_NAME ");
			sql.add(" ,evk.DPK_LVL_9_NAME ");

			sql.add(" FROM EVK_MST evk");
			sql.add(" WHERE evk.KAI_CODE = ? ", getCompanyCode());

			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add("  AND evk.DPK_SSK = ?", condition.getCode());
			}

			if (DBUtil.isUseMySQL) {
				sql.add(" AND evk.DPK_LVL_0_NAME IS NOT NULL ");
				sql.add(" LIMIT 1 ");
			} else {
				sql.add(" AND evk.DPK_LVL_0_NAME IS NOT NULL ");
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
	 * 会社組織階層 O/Rマッピング
	 * 
	 * @param rs
	 * @return CompanyOrganization
	 * @throws Exception
	 */
	protected CompanyOrganization mappingHierarchy(ResultSet rs) throws Exception {

		CompanyOrganization bean = createOrganization();

		bean.setCode(rs.getString("DPK_SSK"));
		bean.setName(rs.getString("DPK_SSK_NAME"));
		bean.setCmpCode(rs.getString("DPK_DEP_CODE"));
		bean.setCmpName(rs.getString("KAI_NAME"));
		bean.setCmpNames(rs.getString("KAI_NAME_S"));
		bean.setLevel(rs.getInt("DPK_LVL"));
		bean.setLevel0(rs.getString("DPK_LVL_0"));
		bean.setLevel0Name(rs.getString("KAI_NAME_0"));
		bean.setLevel1(rs.getString("DPK_LVL_1"));
		bean.setLevel1Name(rs.getString("KAI_NAME_1"));
		bean.setLevel2(rs.getString("DPK_LVL_2"));
		bean.setLevel2Name(rs.getString("KAI_NAME_2"));
		bean.setLevel3(rs.getString("DPK_LVL_3"));
		bean.setLevel3Name(rs.getString("KAI_NAME_3"));
		bean.setLevel4(rs.getString("DPK_LVL_4"));
		bean.setLevel4Name(rs.getString("KAI_NAME_4"));
		bean.setLevel5(rs.getString("DPK_LVL_5"));
		bean.setLevel5Name(rs.getString("KAI_NAME_5"));
		bean.setLevel6(rs.getString("DPK_LVL_6"));
		bean.setLevel6Name(rs.getString("KAI_NAME_6"));
		bean.setLevel7(rs.getString("DPK_LVL_7"));
		bean.setLevel7Name(rs.getString("KAI_NAME_7"));
		bean.setLevel8(rs.getString("DPK_LVL_8"));
		bean.setLevel8Name(rs.getString("KAI_NAME_8"));
		bean.setLevel9(rs.getString("DPK_LVL_9"));
		bean.setLevel9Name(rs.getString("KAI_NAME_9"));
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
	 * @return 会社階層
	 */
	protected CompanyOrganization createOrganization() {
		return new CompanyOrganization();
	}

	/**
	 * 会社階層名称を登録する。
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryCompanyNameOrganization(CompanyOrganization bean) throws TException {

		Connection conn = DBUtil.getConnection();
		try {
			VCreator s = new VCreator();

			s.add(" UPDATE EVK_MST SET ");
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

	/**
	 * 会社階層削除
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void deleteCompanyOrganization(CompanyOrganization bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator s = new VCreator();
			s.add(" DELETE FROM EVK_MST ");
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
	 * 会社階層を登録する。
	 * 
	 * @param sskCode
	 * @param sskName
	 * @param list 会社階層
	 * @throws TException
	 */
	public void entryCompanyOrganization(String sskCode, String sskName, List<CompanyOrganization> list)
		throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator s = new VCreator();
			s.add(" DELETE FROM EVK_MST ");
			s.add(" WHERE KAI_CODE = ? ", getCompanyCode());
			s.add("  AND DPK_SSK = ? ", sskCode);
			s.add("  AND DPK_LVL <> 0 ");

			DBUtil.execute(s);

			if (list != null && !list.isEmpty()) {

				for (CompanyOrganization bean : list) {

					s = new VCreator();

					s.add(" INSERT INTO EVK_MST ( ");
					s.add("  KAI_CODE ");
					s.add("  ,DPK_SSK ");
					s.add("  ,DPK_SSK_NAME ");
					s.add("  ,DPK_DEP_CODE ");
					s.add("  ,DPK_LVL ");
					s.add("  ,DPK_LVL_0 ");
					s.add("  ,DPK_LVL_1 ");
					s.add("  ,DPK_LVL_2 ");
					s.add("  ,DPK_LVL_3");
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
					s.add(" ) VALUES ( ");
					s.add("  ? ", getCompanyCode());
					s.add("  ,? ", sskCode);
					s.add("  ,? ", sskName);
					s.add("  ,? ", bean.getCmpCode());
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
	 * 会社階層LEVEL0登録(新規)
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryCompanyOrganization(CompanyOrganization bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator s = new VCreator();

			s.add(" INSERT INTO EVK_MST ( ");
			s.add("  KAI_CODE ");
			s.add("  ,DPK_SSK ");
			s.add("  ,DPK_SSK_NAME ");
			s.add("  ,DPK_DEP_CODE ");
			s.add("  ,DPK_LVL ");
			s.add("  ,DPK_LVL_0 ");
			s.add("  ,INP_DATE ");
			s.add("  ,PRG_ID");
			s.add("  ,USR_ID ");
			s.add(" ) VALUES ( ");
			s.add("  ? ", getCompanyCode());
			s.add("  ,? ", bean.getCode());
			s.add("  ,? ", bean.getName());
			s.add("  ,? ", bean.getCmpCode());
			s.add("  ,? ", bean.getLevel());
			s.add("  ,? ", bean.getCmpCode());
			s.adt("  ,?", getNow());
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
	 * 会社階層名称一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の会社階層一覧
	 * @throws TException
	 */
	public byte[] getCompanyOrganizationNameExcel(CompanyOrganizationSearchCondition condition) throws TException {

		try {

			// データを抽出
			List<CompanyOrganization> list = getCompanyOrganizationData(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			CompanyHierarchyNameExcel excel = new CompanyHierarchyNameExcel(getUser().getLanguage());
			return excel.getExcelHierarchy(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * 指定条件に該当する会社情報を返す。
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する会社情報
	 * @throws TException
	 */
	public List<String> getCompanyCodeList(CompanyOutputCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();
		List<String> list = new ArrayList<String>();

		try {

			String upper = "evk.DPK_LVL_" + (condition.getLevel() - 1);
			String level = "evk.DPK_LVL_" + condition.getLevel();
			Company superiorCompany = condition.getSuperiorCompany();
			Company from = condition.getCompanyFrom();
			Company to = condition.getCompanyTo();
			List<String> optList = condition.getOptionalCompanysCode();

			VCreator sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("  evk.KAI_CODE     AS ORI_KAI_CODE ");
			sql.add(" ,evk.DPK_DEP_CODE AS KAI_CODE ");
			sql.add(" ,evk.DPK_SSK ");
			sql.add(" ,evk.DPK_LVL ");
			if (condition.getLevel() > 0) {
				sql.add(" ," + upper);
			}
			sql.add(" ," + level);

			sql.add(" FROM EVK_MST evk");
			sql.add(" WHERE evk.KAI_CODE = ? ", getCompanyCode());

			// 検索条件から「会社コード」を外す。
			// ⇒組織の配下の会社
			// （1. 上位会社を指定しない場合）
			// ⇒上位会社の配下の会社
			// （2. 開始会社、終了会社、任意指定がない場合）
			// ⇒開始会社、終了会社が指定されている場合
			// __3. 該当する会社
			// ⇒任意指定が指定される場合
			// __4. 該当する会社

			sql.add("  AND evk.DPK_SSK = ? ", condition.getOrganizationCode());

			if (superiorCompany == null) {
				// 1. 上位会社を指定しない場合 -> 組織の配下の会社全部

			} else {
				// 上位会社指定あり

				if (condition.getLevel() > 0) {
					// 2. 開始会社、終了会社、任意指定がない場合

					sql.add("  AND " + upper + " = ? ", superiorCompany.getCode());

				}
			}

			// 3. 該当する会社
			if (from != null || to != null) {
				// 開始会社、終了会社が指定されている場合

				// 開始会社
				if (from != null) {
					sql.add("  AND " + level + " >= ? ", from.getCode());
				}

				// 終了会社
				if (to != null) {
					sql.add("  AND " + level + " <= ? ", to.getCode());
				}

			} else if (optList != null && !optList.isEmpty()) {

				// 任意指定
				sql.add("  AND " + level + " IN ? ", optList);
			}

			sql.add(" ORDER BY ");
			sql.add("    evk.DPK_LVL ");
			sql.add("   ,evk.DPK_DEP_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				String companyCode = Util.avoidNull(rs.getString("KAI_CODE"));
				list.add(companyCode);
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
