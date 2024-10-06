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
 * ��ЊK�w�R���g���[���}�X�^�̎���
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
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return �G���e�B�e�B
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
	 * @return ���
	 */
	protected Company createCompany() {
		return new Company();
	}

	/**
	 * �w������ɊY�������Бg�D����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������Бg�D���
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
	 * ��Бg�D�K�w O/R�}�b�s���O
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
	 * �w������ɊY�������Бg�D����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������Бg�D���(��ЊK�w�}�X�^�p)
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
	 * �w������ɊY�������Бg�D����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������Бg�D���(��ЊK�w�}�X�^�p)
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
	 * ��Бg�D�K�w O/R�}�b�s���O
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
	 * @return ��ЊK�w
	 */
	protected CompanyOrganization createOrganization() {
		return new CompanyOrganization();
	}

	/**
	 * ��ЊK�w���̂�o�^����B
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
	 * ��ЊK�w�폜
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
	 * ��ЊK�w��o�^����B
	 * 
	 * @param sskCode
	 * @param sskName
	 * @param list ��ЊK�w
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
	 * ��ЊK�wLEVEL0�o�^(�V�K)
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
	 * ��ЊK�w���̈ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̉�ЊK�w�ꗗ
	 * @throws TException
	 */
	public byte[] getCompanyOrganizationNameExcel(CompanyOrganizationSearchCondition condition) throws TException {

		try {

			// �f�[�^�𒊏o
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
	 * �w������ɊY�������Џ���Ԃ��B
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������Џ��
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

			// ������������u��ЃR�[�h�v���O���B
			// �ˑg�D�̔z���̉��
			// �i1. ��ʉ�Ђ��w�肵�Ȃ��ꍇ�j
			// �ˏ�ʉ�Ђ̔z���̉��
			// �i2. �J�n��ЁA�I����ЁA�C�ӎw�肪�Ȃ��ꍇ�j
			// �ˊJ�n��ЁA�I����Ђ��w�肳��Ă���ꍇ
			// __3. �Y��������
			// �˔C�ӎw�肪�w�肳���ꍇ
			// __4. �Y��������

			sql.add("  AND evk.DPK_SSK = ? ", condition.getOrganizationCode());

			if (superiorCompany == null) {
				// 1. ��ʉ�Ђ��w�肵�Ȃ��ꍇ -> �g�D�̔z���̉�БS��

			} else {
				// ��ʉ�Ўw�肠��

				if (condition.getLevel() > 0) {
					// 2. �J�n��ЁA�I����ЁA�C�ӎw�肪�Ȃ��ꍇ

					sql.add("  AND " + upper + " = ? ", superiorCompany.getCode());

				}
			}

			// 3. �Y��������
			if (from != null || to != null) {
				// �J�n��ЁA�I����Ђ��w�肳��Ă���ꍇ

				// �J�n���
				if (from != null) {
					sql.add("  AND " + level + " >= ? ", from.getCode());
				}

				// �I�����
				if (to != null) {
					sql.add("  AND " + level + " <= ? ", to.getCode());
				}

			} else if (optList != null && !optList.isEmpty()) {

				// �C�ӎw��
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
	 * SQL�p
	 */
	protected class VCreator extends SQLCreator {

		/**
		 * �R���X�g���N�^�[
		 */
		public VCreator() {
			crlf = " ";
		}
	}

}
