package jp.co.ais.trans2.model.department;

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
 * 部門マネージャの実装クラスです。
 * 
 * @author AIS
 */
public class DepartmentManagerImpl extends TModel implements DepartmentManager {

	public List<Department> get(DepartmentSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Department> list = new ArrayList<Department>();

		try {

			VCreator s = getSQL(condition);

			s.add("ORDER BY bmn.DEP_CODE");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, s);

			while (rs.next()) {
				list.add(mapping(rs, condition));
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
	 * 指定条件に該当する部門情報を返す<br>
	 * 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する部門情報
	 * @throws TException
	 */
	public List<Department> getRef(DepartmentSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Department> list = new ArrayList<Department>();

		try {

			VCreator sql = getSQL(condition);

			if (condition.getCompanyCodeList() != null && !condition.getCompanyCodeList().isEmpty()) {
				sql.add(" AND bmn.KAI_CODE IN ? ", condition.getCompanyCodeList());
			}

			sql.add(" ORDER BY  ");
			sql.add("   bmn.DEP_CODE ");
			sql.add("  ,CASE WHEN bmn.KAI_CODE = ? THEN 0 ELSE 1 END ", getCompanyCode()); // 同じコードの場合にログイン会社優先
			sql.add("  ,bmn.KAI_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String currentCode = null;

			while (rs.next()) {

				Department bean = mapping(rs, condition);

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
	protected VCreator getSQL(DepartmentSearchCondition condition) {
		boolean isJoinDpkMst = !Util.isNullOrEmpty(condition.getOrganizationCode()) || condition.getLevel() >= 0;

		VCreator s = new VCreator();

		s.add("SELECT");
		s.add("  bmn.KAI_CODE,");
		s.add("  bmn.DEP_CODE,");
		s.add("  bmn.DEP_NAME,");
		s.add("  bmn.DEP_NAME_S,");
		s.add("  bmn.DEP_NAME_K,");
		s.add("  bmn.MEN_1,");
		s.add("  bmn.MEN_2,");
		s.add("  bmn.AREA,");
		s.add("  bmn.DEP_KBN,");
		s.add("  bmn.STR_DATE,");
		s.add("  bmn.END_DATE,");

		if (condition.isUseIf1()) {
			s.add("  bmn.IF_CODE_1,");
			s.add("  bmn.IF_NAME_1,");
		} else {
			s.add("  NULL IF_CODE_1,");
			s.add("  NULL IF_NAME_1,");
		}
		if (condition.isUseIf2()) {
			s.add("  bmn.IF_CODE_2,");
			s.add("  bmn.IF_NAME_2,");
		} else {
			s.add("  NULL IF_CODE_2,");
			s.add("  NULL IF_NAME_2,");
		}
		if (condition.isUseIf3()) {
			s.add("  bmn.IF_CODE_3,");
			s.add("  bmn.IF_NAME_3");
		} else {
			s.add("  NULL IF_CODE_3,");
			s.add("  NULL IF_NAME_3 ");
		}

		if (isJoinDpkMst) {
			s.add(", ");
			s.add(" dpk.DPK_SSK, ");
			s.add(" dpk.DPK_LVL ");

			if (condition.getLevel() > 0) {
				s.add(", ");
				s.add(" dpk.DPK_LVL_" + Integer.toString(condition.getLevel() - 1) + " SUPERIOR_DEP_CODE ");
			}
		}

		s.add("FROM BMN_MST bmn");

		// 組織
		if (isJoinDpkMst) {

			s.add(" INNER JOIN DPK_MST dpk ");
			s.add(" ON 	bmn.KAI_CODE = dpk.KAI_CODE ");
			s.add(" AND	bmn.DEP_CODE = dpk.DPK_DEP_CODE ");

			// 組織
			if (!Util.isNullOrEmpty(condition.getOrganizationCode())) {
				s.add(" AND	dpk.DPK_SSK = ?", condition.getOrganizationCode());
			}

			// レベル
			if (condition.getLevel() >= 0) {
				s.add(" AND	dpk.DPK_LVL = ?", condition.getLevel());
			}

			// 上位部門
			if (!Util.isNullOrEmpty(condition.getSuperiorDepartmentCode())) {
				s.add(" AND	dpk.DPK_LVL_?", condition.getLevel() - 1);
				s.add(" = ?", condition.getSuperiorDepartmentCode());
			}
		}

		s.add("WHERE 1 = 1");

		// 会社コード
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			s.add("  AND  bmn.KAI_CODE = ? ", condition.getCompanyCode());
		}

		// 部門コード
		if (!Util.isNullOrEmpty(condition.getCode())) {
			s.add("  AND  bmn.DEP_CODE = ? ", condition.getCode());
		}

		// 開始コード
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			s.add("  AND  bmn.DEP_CODE >= ?", condition.getCodeFrom());
		}

		// 終了コード
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			s.add("  AND  bmn.DEP_CODE <= ?", condition.getCodeTo());
		}

		// コード(複数)
		if (condition.getDepartmentCodeList() != null && condition.getDepartmentCodeList().length != 0) {
			s.add(" AND  bmn.DEP_CODE IN ");
			s.addInStatement(condition.getDepartmentCodeList());
		}

		// コード前方一致
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			s.addLikeFront("  AND  bmn.DEP_CODE ?", condition.getCodeLike());
		}

		// 部門略称
		if (!Util.isNullOrEmpty(condition.getNamesLike())) {
			s.addLikeAmbi("  AND  bmn.DEP_NAME_S ?", condition.getNamesLike());
		}

		// 部門検索名称
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			s.addLikeAmbi("  AND  bmn.DEP_NAME_K ?", condition.getNamekLike());
		}

		// 集計部門を含まない場合
		if (!condition.isSumDepartment()) {
			s.add("  AND	bmn.DEP_KBN = 0 ");
		}

		// 有効期間
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			s.add("  AND  bmn.STR_DATE <= ? ", condition.getValidTerm());
			s.add("  AND  bmn.END_DATE >= ? ", condition.getValidTerm());
		}

		// 最終更新日時
		if (condition.getLastUpdateDate() != null) {
			s.adt(" AND  (bmn.INP_DATE > ? ", condition.getLastUpdateDate());
			s.adt("    OR bmn.UPD_DATE > ?)", condition.getLastUpdateDate());
		}
		return s;
	}

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(DepartmentSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   BMN_MST bmn ");
			sql.add(" WHERE  bmn.KAI_CODE = ? ", condition.getCompanyCode()); // 会社コード

			// 集計部門を含まない場合
			if (!condition.isSumDepartment()) {
				sql.add("  AND	bmn.DEP_KBN = 0 ");
			}

			// 最終更新日時
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (bmn.INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR bmn.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR bmn.INP_DATE IS NULL AND bmn.UPD_DATE IS NULL) ");
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

	// 部門登録
	public void entry(Department bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator s = new VCreator();
			s.add("INSERT INTO BMN_MST (");
			s.add("  KAI_CODE,");
			s.add("  DEP_CODE,");
			s.add("  DEP_NAME,");
			s.add("  DEP_NAME_S,");
			s.add("  DEP_NAME_K,");
			s.add("  MEN_1,");
			s.add("  MEN_2,");
			s.add("  AREA,");
			s.add("  DEP_KBN,");
			s.add("  STR_DATE,");
			s.add("  END_DATE,");
			s.add("  INP_DATE,");
			s.add("  PRG_ID,");
			s.add("  USR_ID");

			if (bean.isUseIf1()) {
				s.add("  ,IF_CODE_1 ");
				s.add("  ,IF_NAME_1 ");
			}
			if (bean.isUseIf2()) {
				s.add("  ,IF_CODE_2 ");
				s.add("  ,IF_NAME_2 ");
			}
			if (bean.isUseIf3()) {
				s.add("  ,IF_CODE_3 ");
				s.add("  ,IF_NAME_3 ");
			}

			s.add(") VALUES (");
			s.add("  ?,", bean.getCompanyCode());
			s.add("  ?,", bean.getCode());
			s.add("  ?,", bean.getName());
			s.add("  ?,", bean.getNames());
			s.add("  ?,", bean.getNamek());
			s.add("  ?,", bean.getPersonnel1());
			s.add("  ?,", bean.getPersonnel2());
			s.add("  ?,", bean.getFloorSpace());
			s.add("  ?,", BooleanUtil.toInt(bean.isSumDepartment()));
			s.add("  ?,", bean.getDateFrom());
			s.add("  ?,", bean.getDateTo());
			s.addYMDHMS("  ?,", getNow());
			s.add("  ?,", getProgramCode());
			s.add("  ? ", getUserCode());

			if (bean.isUseIf1()) {
				s.add("  ,? ", bean.getIfCode1());
				s.add("  ,? ", bean.getIfName1());
			}
			if (bean.isUseIf2()) {
				s.add("  ,? ", bean.getIfCode2());
				s.add("  ,? ", bean.getIfName2());
			}
			if (bean.isUseIf3()) {
				s.add("  ,? ", bean.getIfCode3());
				s.add("  ,? ", bean.getIfName3());
			}
			s.add(")");

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	// 修正
	public void modify(Department bean) throws TException {
		Connection conn = DBUtil.getConnection();
		try {
			VCreator s = new VCreator();
			s.add("UPDATE BMN_MST SET");
			s.add("  DEP_NAME = ?,", bean.getName());
			s.add("  DEP_NAME_S = ?,", bean.getNames());
			s.add("  DEP_NAME_K = ?,", bean.getNamek());
			s.add("  MEN_1 = ?,", bean.getPersonnel1());
			s.add("  MEN_2 = ?,", bean.getPersonnel2());
			s.add("  AREA = ?,", bean.getFloorSpace());
			s.add("  DEP_KBN = ?,", BooleanUtil.toInt(bean.isSumDepartment()));
			s.add("  STR_DATE = ?,", bean.getDateFrom());
			s.add("  END_DATE = ?,", bean.getDateTo());
			s.addYMDHMS("  UPD_DATE = ?,", getNow());
			s.add("  PRG_ID = ?,", getProgramCode());
			s.add("  USR_ID = ? ", getUserCode());

			if (bean.isUseIf1()) {
				s.add("  ,IF_CODE_1 = ? ", bean.getIfCode1());
				s.add("  ,IF_NAME_1 = ? ", bean.getIfName1());
			}
			if (bean.isUseIf2()) {
				s.add("  ,IF_CODE_2 = ? ", bean.getIfCode2());
				s.add("  ,IF_NAME_2 = ? ", bean.getIfName2());
			}
			if (bean.isUseIf3()) {
				s.add("  ,IF_CODE_3 = ? ", bean.getIfCode3());
				s.add("  ,IF_NAME_3 = ? ", bean.getIfName3());
			}
			s.add("WHERE KAI_CODE = ?", bean.getCompanyCode());
			s.add("  AND DEP_CODE = ?", bean.getCode());

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	// 削除
	public void delete(Department bean) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.DEPARTMENT);
		condition.setCompanyCode(bean.getCompanyCode());
		condition.setDepartmentCode(bean.getCode());

		// 使用済みチェック
		CheckMasterUseDao cd = (CheckMasterUseDao) getComponent(CheckMasterUseDao.class);
		cd.check(condition);

		Connection conn = DBUtil.getConnection();

		try {
			VCreator s = new VCreator();
			s.add("DELETE FROM BMN_MST");
			s.add("WHERE KAI_CODE = ?", bean.getCompanyCode());
			s.add("  AND DEP_CODE = ?", bean.getCode());

			DBUtil.execute(conn, s);

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
	 * @param condition
	 * @return Department
	 * @throws Exception
	 */
	protected Department mapping(ResultSet rs, DepartmentSearchCondition condition) throws Exception {

		Department bean = createDepartment();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("DEP_CODE"));
		bean.setName(rs.getString("DEP_NAME"));
		bean.setNames(rs.getString("DEP_NAME_S"));
		bean.setNamek(rs.getString("DEP_NAME_K"));
		bean.setPersonnel1(rs.getInt("MEN_1"));
		bean.setPersonnel2(rs.getInt("MEN_2"));
		bean.setFloorSpace(rs.getBigDecimal("AREA"));
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));
		bean.setSumDepartment(rs.getInt("DEP_KBN") != 0);

		bean.setUseIf1(condition.isUseIf1());
		bean.setIfCode1(rs.getString("IF_CODE_1"));
		bean.setIfName1(rs.getString("IF_NAME_1"));
		bean.setUseIf2(condition.isUseIf2());
		bean.setIfCode2(rs.getString("IF_CODE_2"));
		bean.setIfName2(rs.getString("IF_NAME_2"));
		bean.setUseIf3(condition.isUseIf3());
		bean.setIfCode3(rs.getString("IF_CODE_3"));
		bean.setIfName3(rs.getString("IF_NAME_3"));

		boolean isJoinDpkMst = !Util.isNullOrEmpty(condition.getOrganizationCode()) || condition.getLevel() >= 0;

		if (isJoinDpkMst) {
			bean.setOrganizationCode(rs.getString("DPK_SSK"));
			bean.setLevel(rs.getInt("DPK_LVL"));
		}

		if (condition.getLevel() > 0) {
			bean.setSuperiorDepartmentCode(rs.getString("SUPERIOR_DEP_CODE"));
		}

		return bean;

	}

	/**
	 * @return 部門
	 */
	protected Department createDepartment() {
		return new Department();
	}

	/**
	 * 部門一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の部門一覧
	 * @throws TException
	 */
	public byte[] getExcel(DepartmentSearchCondition condition) throws TException {

		try {

			// データを抽出
			List<Department> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			DepartmentExcel exl = new DepartmentExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * 部門組織階層コードを取得
	 * 
	 * @see jp.co.ais.trans2.model.department.DepartmentManager#getDepartmentOrganization(jp.co.ais.trans2.model.department.DepartmentOrganizationSearchCondition)
	 */
	public List<DepartmentOrganization> getDepartmentOrganization(DepartmentOrganizationSearchCondition condition)
		throws TException {

		Connection conn = DBUtil.getConnection();
		List<DepartmentOrganization> list = new ArrayList<DepartmentOrganization>();

		try {

			VCreator sql = new VCreator();
			sql.add("SELECT");
			sql.add("  DISTINCT(DPK_SSK) DPK_SSK");
			sql.add("FROM DPK_MST ");
			sql.add("WHERE KAI_CODE = ?", condition.getCompanyCode());
			sql.add("ORDER BY DPK_SSK ");

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
	 * 部門組織階層 O/Rマッピング
	 * 
	 * @param rs
	 * @return DepartmentOrganization
	 * @throws Exception
	 */
	protected DepartmentOrganization mappingDepOrg(ResultSet rs) throws Exception {

		DepartmentOrganization bean = createOrganization();

		bean.setCode(rs.getString("DPK_SSK"));

		return bean;
	}

	/**
	 * 部門組織階層コードを取得
	 * 
	 * @see jp.co.ais.trans2.model.department.DepartmentManager#getDepartmentOrganization(jp.co.ais.trans2.model.department.DepartmentOrganizationSearchCondition)
	 */
	public List<DepartmentOrganization> getDepartmentOrganizationData(DepartmentOrganizationSearchCondition condition)
		throws TException {

		Connection conn = DBUtil.getConnection();
		List<DepartmentOrganization> list = new ArrayList<DepartmentOrganization>();

		try {

			VCreator sql = new VCreator();
			sql.add("SELECT");
			sql.add("  dpk.KAI_CODE");
			sql.add(" ,dpk.DPK_SSK");
			sql.add(" ,dpk.DPK_DEP_CODE");
			sql.add(" ,dep.DEP_NAME");
			sql.add(" ,dep.DEP_NAME_S");
			sql.add(" ,dpk.DPK_LVL");
			sql.add(" ,dpk.DPK_LVL_0");
			sql.add(" ,dep0.DEP_NAME DEP_NAME_0");
			sql.add(" ,dpk.DPK_LVL_1");
			sql.add(" ,dep1.DEP_NAME DEP_NAME_1");
			sql.add(" ,dpk.DPK_LVL_2");
			sql.add(" ,dep2.DEP_NAME DEP_NAME_2");
			sql.add(" ,dpk.DPK_LVL_3");
			sql.add(" ,dep3.DEP_NAME DEP_NAME_3");
			sql.add(" ,dpk.DPK_LVL_4");
			sql.add(" ,dep4.DEP_NAME DEP_NAME_4");
			sql.add(" ,dpk.DPK_LVL_5");
			sql.add(" ,dep5.DEP_NAME DEP_NAME_5");
			sql.add(" ,dpk.DPK_LVL_6");
			sql.add(" ,dep6.DEP_NAME DEP_NAME_6");
			sql.add(" ,dpk.DPK_LVL_7");
			sql.add(" ,dep7.DEP_NAME DEP_NAME_7");
			sql.add(" ,dpk.DPK_LVL_8");
			sql.add(" ,dep8.DEP_NAME DEP_NAME_8");
			sql.add(" ,dpk.DPK_LVL_9");
			sql.add(" ,dep9.DEP_NAME DEP_NAME_9");
			sql.add("FROM DPK_MST dpk");
			sql.add("LEFT JOIN BMN_MST dep ON dpk.KAI_CODE = dep.KAI_CODE ");
			sql.add("                     AND dpk.DPK_DEP_CODE = dep.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep0 ON dpk.KAI_CODE = dep0.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_0 = dep0.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep1 ON dpk.KAI_CODE = dep1.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_1 = dep1.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep2 ON dpk.KAI_CODE = dep2.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_2 = dep2.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep3 ON dpk.KAI_CODE = dep3.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_3 = dep3.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep4 ON dpk.KAI_CODE = dep4.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_4 = dep4.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep5 ON dpk.KAI_CODE = dep5.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_5 = dep5.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep6 ON dpk.KAI_CODE = dep6.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_6 = dep6.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep7 ON dpk.KAI_CODE = dep7.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_7 = dep7.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep8 ON dpk.KAI_CODE = dep8.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_8 = dep8.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep9 ON dpk.KAI_CODE = dep9.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_9 = dep9.DEP_CODE ");
			sql.add("WHERE dpk.KAI_CODE = ?", condition.getCompanyCode());

			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add("  AND dpk.DPK_SSK = ?", condition.getCode());
			}
			sql.add("ORDER BY CASE WHEN dpk.DPK_LVL = 0 THEN 0 ELSE 1 END ");
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
	 * 部門組織階層 O/Rマッピング
	 * 
	 * @param rs
	 * @return DepartmentOrganization
	 * @throws Exception
	 */
	protected DepartmentOrganization mappingHierarchy(ResultSet rs) throws Exception {

		DepartmentOrganization bean = createOrganization();

		bean.setCode(rs.getString("DPK_SSK"));
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

		return bean;
	}

	/**
	 * @return 部門階層
	 */
	protected DepartmentOrganization createOrganization() {
		return new DepartmentOrganization();
	}

	/** 部門階層LEVEL0登録(新規) */
	public void entryDepartmentOrganization(DepartmentOrganization bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator s = new VCreator();

			s.add("INSERT INTO DPK_MST (");
			s.add("  KAI_CODE,");
			s.add("  DPK_SSK,");
			s.add("  DPK_DEP_CODE,");
			s.add("  DPK_LVL,");
			s.add("  DPK_LVL_0,");
			s.add("  INP_DATE,");
			s.add("  PRG_ID,");
			s.add("  USR_ID");
			s.add(") VALUES (");
			s.add("  ?,", getCompanyCode());
			s.add("  ?,", bean.getCode());
			s.add("  ?,", bean.getDepCode());
			s.add("  ?,", bean.getLevel());
			s.add("  ?,", bean.getDepCode());
			s.addYMDHMS("  ?,", getNow());
			s.add("  ?,", getProgramCode());
			s.add("  ?", getUserCode());
			s.add(")");

			DBUtil.execute(s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/** 部門階層LEVEL0、前の組織コードの部門階層→新組織コードの部門階層へコピー(複写) */
	public void entryCopyDepartmentOrganization(DepartmentOrganization bean, String oldSskCode) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator s = new VCreator();

			s.add("INSERT INTO DPK_MST (");
			s.add("   KAI_CODE");
			s.add("  ,DPK_SSK");
			s.add("  ,DPK_DEP_CODE");
			s.add("  ,DPK_LVL");
			s.add("  ,DPK_LVL_0");
			s.add("  ,DPK_LVL_1");
			s.add("  ,DPK_LVL_2");
			s.add("  ,DPK_LVL_3");
			s.add("  ,DPK_LVL_4");
			s.add("  ,DPK_LVL_5");
			s.add("  ,DPK_LVL_6");
			s.add("  ,DPK_LVL_7");
			s.add("  ,DPK_LVL_8");
			s.add("  ,DPK_LVL_9");
			s.add("  ,INP_DATE");
			s.add("  ,PRG_ID");
			s.add("  ,USR_ID");
			s.add(")");
			s.add("SELECT ");
			s.add("   KAI_CODE");
			s.add("      ,?", bean.getCode());
			s.add("      ,DPK_DEP_CODE");
			s.add("      ,DPK_LVL");
			s.add("      ,DPK_LVL_0");
			s.add("      ,DPK_LVL_1");
			s.add("      ,DPK_LVL_2");
			s.add("      ,DPK_LVL_3");
			s.add("      ,DPK_LVL_4");
			s.add("      ,DPK_LVL_5");
			s.add("      ,DPK_LVL_6");
			s.add("      ,DPK_LVL_7");
			s.add("      ,DPK_LVL_8");
			s.add("      ,DPK_LVL_9");
			s.addYMDHMS(",?", getNow());
			s.add("      ,?", getProgramCode());
			s.add("      ,?", getUserCode());
			s.add("FROM DPK_MST dpk");
			s.add("WHERE KAI_CODE = ?", getCompanyCode());

			if (!Util.isNullOrEmpty(oldSskCode)) {
				s.add("  AND DPK_SSK = ?", oldSskCode);
			}

			DBUtil.execute(s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 部門階層を登録する。
	 * 
	 * @param sskCode
	 * @param list 部門階層
	 * @throws TException
	 */
	public void entryDepartmentOrganization(String sskCode, List<DepartmentOrganization> list) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator s = new VCreator();
			s.add("DELETE FROM DPK_MST");
			s.add("WHERE KAI_CODE = ?", getCompanyCode());
			s.add("  AND DPK_SSK = ?", sskCode);
			s.add("  AND DPK_LVL <> 0 ");

			DBUtil.execute(s);

			if (list != null && !list.isEmpty()) {

				for (DepartmentOrganization bean : list) {

					s = new VCreator();

					s.add("INSERT INTO DPK_MST (");
					s.add("  KAI_CODE,");
					s.add("  DPK_SSK,");
					s.add("  DPK_DEP_CODE,");
					s.add("  DPK_LVL,");
					s.add("  DPK_LVL_0,");
					s.add("  DPK_LVL_1,");
					s.add("  DPK_LVL_2,");
					s.add("  DPK_LVL_3,");
					s.add("  DPK_LVL_4,");
					s.add("  DPK_LVL_5,");
					s.add("  DPK_LVL_6,");
					s.add("  DPK_LVL_7,");
					s.add("  DPK_LVL_8,");
					s.add("  DPK_LVL_9,");
					s.add("  INP_DATE,");
					s.add("  UPD_DATE,");
					s.add("  PRG_ID,");
					s.add("  USR_ID");
					s.add(") VALUES (");
					s.add("  ?,", getCompanyCode());
					s.add("  ?,", bean.getCode());
					s.add("  ?,", bean.getDepCode());
					s.add("  ?,", bean.getLevel());
					s.add("  ?,", bean.getLevel0());
					s.add("  ?,", bean.getLevel1());
					s.add("  ?,", bean.getLevel2());
					s.add("  ?,", bean.getLevel3());
					s.add("  ?,", bean.getLevel4());
					s.add("  ?,", bean.getLevel5());
					s.add("  ?,", bean.getLevel6());
					s.add("  ?,", bean.getLevel7());
					s.add("  ?,", bean.getLevel8());
					s.add("  ?,", bean.getLevel9());
					s.addYMDHMS("  ?,", bean.getInpDate());
					s.addYMDHMS("  ?,", getNow());
					s.add("  ?,", getProgramCode());
					s.add("  ?", getUserCode());
					s.add(")");

					DBUtil.execute(s);
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	// 部門階層削除
	public void deleteDepartmentOrganization(DepartmentOrganization bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator s = new VCreator();
			s.add("DELETE FROM DPK_MST");
			s.add("WHERE KAI_CODE = ?", getCompanyCode());
			s.add("  AND DPK_SSK = ?", bean.getCode());

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 部門階層一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の部門階層一覧
	 * @throws TException
	 */
	public byte[] getDepartmentOrganizationExcel(DepartmentOrganizationSearchCondition condition) throws TException {

		try {

			// データを抽出
			List<DepartmentOrganization> list = getDepartmentOrganizationData(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			DepartmentHierarchyExcel excel = new DepartmentHierarchyExcel(getUser().getLanguage());
			return excel.getExcelHierarchy(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * 部門階層マスタに登録されていない部門リスト取得
	 * 
	 * @param companyCode
	 * @param depCondition
	 * @return 部門階層マスタに登録されていない部門リスト
	 * @throws TException
	 */
	public List<String> getNotExistDepartmentList(String companyCode, DepartmentOutputCondition depCondition)
		throws TException {

		List<String> list = new ArrayList<String>();
		String dpk = TransSQLUtil.getDepartmentOutputConditionStatement(depCondition);
		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();

			sql.add(" SELECT b.DEP_CODE ");
			sql.add("       ,b.DEP_NAME ");
			sql.add(" FROM BMN_MST b ");
			sql.add(" WHERE b.KAI_CODE = ? ", companyCode);
			sql.add("   AND NOT EXISTS (SELECT 1 ");
			sql.add("                   FROM DPK_MST dpk ");
			sql.add("                   WHERE b.KAI_CODE = dpk.KAI_CODE ");
			sql.add("                     AND b.DEP_CODE = dpk.DPK_DEP_CODE ");
			sql.add(dpk);
			sql.add("                  )");
			sql.add(" ORDER BY b.DEP_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			StringBuilder sb = null;

			while (rs.next()) {
				sb = new StringBuilder();
				sb.append(Util.avoidNull(rs.getString("DEP_CODE")));
				sb.append(" ");
				sb.append(Util.avoidNull(rs.getString("DEP_NAME")));
				list.add(sb.toString());
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (TException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TException(ex);
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
