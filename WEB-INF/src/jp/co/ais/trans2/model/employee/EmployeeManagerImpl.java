package jp.co.ais.trans2.model.employee;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.check.*;

/**
 * MG0160EmployeeMaster - �Ј��}�X�^ - Model Class
 * 
 * @author AIS
 */
public class EmployeeManagerImpl extends TModel implements EmployeeManager {

	/**
	 * ��񌟍� (SELECT)
	 * 
	 * @param condition ��������
	 * @return List ��������
	 * @throws TException
	 */
	public List<Employee> get(EmployeeSearchCondition condition) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		// �������ʊi�[�p�z�񐶐�
		List<Employee> list = new ArrayList<Employee>();

		try {

			VCreator sql = getSQL(condition);

			sql.add("ORDER BY");
			sql.add("    emp.EMP_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}

			DBUtil.close(statement);
			DBUtil.close(rs);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return list;
	}

	/**
	 * ��񌟍� (SELECT)
	 * 
	 * @param condition ��������
	 * @return List ��������
	 * @throws TException
	 */
	public List<Employee> getRef(EmployeeSearchCondition condition) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		// �������ʊi�[�p�z�񐶐�
		List<Employee> list = new ArrayList<Employee>();

		try {

			VCreator sql = getSQL(condition);

			if (condition.getCompanyCodeList() != null && !condition.getCompanyCodeList().isEmpty()) {
				sql.add(" AND emp.KAI_CODE IN ? ", condition.getCompanyCodeList());
			}

			sql.add(" ORDER BY  ");
			sql.add("   emp.EMP_CODE ");
			sql.add("  ,CASE WHEN emp.KAI_CODE = ? THEN 0 ELSE 1 END ", getCompanyCode()); // �����R�[�h�̏ꍇ�Ƀ��O�C����ЗD��
			sql.add("  ,emp.KAI_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String currentCode = null;

			while (rs.next()) {

				Employee bean = mapping(rs);

				if (Util.isNullOrEmpty(currentCode)) {
					// ������
					currentCode = bean.getCode();
				} else if (Util.equals(currentCode, bean.getCode())) {
					// �R�[�h�������ꍇ�ɏ����s�v
					continue;
				}

				// �R�[�h�ޔ�
				currentCode = bean.getCode();

				// �߂�l���X�g����
				list.add(bean);
			}

			DBUtil.close(statement);
			DBUtil.close(rs);

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
	protected VCreator getSQL(EmployeeSearchCondition condition) {
		VCreator sql = new VCreator();

		sql.add("");
		sql.add("SELECT");
		sql.add("    emp.KAI_CODE,");
		sql.add("    emp.EMP_CODE,");
		sql.add("    emp.EMP_NAME,");
		sql.add("    emp.EMP_NAME_S,");
		sql.add("    emp.EMP_NAME_K,");
		sql.add("    emp.EMP_CBK_CODE,");
		sql.add("    cbk_bnk.BNK_NAME_S,");
		sql.add("    cbk_bnk.BNK_STN_NAME_S,");
		sql.add("    emp.EMP_FURI_BNK_CODE,");
		sql.add("    bnk.BNK_NAME_S FURI_BNK_NAME_S,");
		sql.add("    emp.EMP_FURI_STN_CODE,");
		sql.add("    bnk.BNK_STN_NAME_S FURI_STN_NAME_S,");
		sql.add("    emp.EMP_YKN_NO,");
		sql.add("    emp.EMP_KOZA_KBN,");
		sql.add("    emp.EMP_YKN_KANA,");
		sql.add("    emp.STR_DATE,");
		sql.add("    emp.END_DATE");
		sql.add("FROM EMP_MST emp");
		sql.add("    LEFT OUTER JOIN BNK_MST bnk");
		sql.add("      ON emp.EMP_FURI_BNK_CODE = bnk.BNK_CODE");
		sql.add("      AND emp.EMP_FURI_STN_CODE = bnk.BNK_STN_CODE");
		sql.add("    LEFT OUTER JOIN AP_CBK_MST cbk");
		sql.add("      ON emp.KAI_CODE = cbk.KAI_CODE");
		sql.add("      AND emp.EMP_CBK_CODE = cbk.CBK_CBK_CODE");
		sql.add("    LEFT OUTER JOIN BNK_MST cbk_bnk");
		sql.add("      ON cbk.CBK_BNK_CODE = cbk_bnk.BNK_CODE");
		sql.add("      AND cbk.CBK_STN_CODE = cbk_bnk.BNK_STN_CODE");
		sql.add("WHERE 1 = 1");

		// ���������F��ЃR�[�h
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add("AND");
			sql.add("    emp.KAI_CODE = ?", condition.getCompanyCode());
		}
		// ���������F�R�[�h
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql.add("AND");
			sql.add("    emp.EMP_CODE = ?", condition.getCode());
		}
		// ���������F�R�[�h�O����v
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql.add("AND");
			sql.addLikeFront("    emp.EMP_CODE ?", condition.getCodeLike());
		}
		// ���������F���̂����܂�
		if (!Util.isNullOrEmpty(condition.getNamesLike())) {
			sql.add("AND");
			sql.addNLikeAmbi("    emp.EMP_NAME_S ?", condition.getNamesLike());
		}
		// ���������F�������̂����܂�
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql.add("AND");
			sql.addNLikeAmbi("    emp.EMP_NAME_K ?", condition.getNamekLike());
		}
		// ���������F�J�n�R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql.add("AND");
			sql.add("    emp.EMP_CODE >= ?", condition.getCodeFrom());
		}
		// ���������F�I���R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql.add("AND");
			sql.add("    emp.EMP_CODE <= ?", condition.getCodeTo());
		}
		// �R�[�h(����)
		if (condition.getEmployeeCodeList() != null && condition.getEmployeeCodeList().length != 0) {
			sql.add(" AND emp.EMP_CODE IN ");
			sql.addInStatement(condition.getEmployeeCodeList());
		}
		// ���������F�L������
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql.add("AND");
			sql.addYMDHMS("    emp.STR_DATE <= ?", condition.getValidTerm());
			sql.add("AND");
			sql.addYMDHMS("    emp.END_DATE >= ?", condition.getValidTerm());
		}

		// �ŏI�X�V����
		if (condition.getLastUpdateDate() != null) {
			sql.adt(" AND  (emp.INP_DATE > ? ", condition.getLastUpdateDate());
			sql.adt("    OR emp.UPD_DATE > ?)", condition.getLastUpdateDate());
		}
		return sql;
	}

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(EmployeeSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   EMP_MST emp ");
			sql.add(" WHERE  emp.KAI_CODE = ? ", condition.getCompanyCode()); // ��ЃR�[�h

			// �ŏI�X�V����
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (emp.INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR emp.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR emp.INP_DATE IS NULL AND emp.UPD_DATE IS NULL) ");
			}

			// �폜���聁���ݎ����Ă��錏����DB�̉ߋ��̌����ƕs��v
			hasDelete = DBUtil.selectOneInt(conn, sql.toSQL()) != condition.getNowCount();

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return hasDelete;
	}

	/**
	 * ���o�^ (INSERT)
	 * 
	 * @param bean ���͏��
	 * @throws TException
	 */
	public void entry(Employee bean) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("INSERT INTO EMP_MST (");
			sql.add("    KAI_CODE,");
			sql.add("    EMP_CODE,");
			sql.add("    EMP_NAME,");
			sql.add("    EMP_NAME_S,");
			sql.add("    EMP_NAME_K,");
			sql.add("    EMP_CBK_CODE,");
			sql.add("    EMP_FURI_BNK_CODE,");
			sql.add("    EMP_FURI_STN_CODE,");
			sql.add("    EMP_YKN_NO,");
			sql.add("    EMP_KOZA_KBN,");
			sql.add("    EMP_YKN_KANA,");
			sql.add("    STR_DATE,");
			sql.add("    END_DATE,");
			sql.add("    INP_DATE,");
			sql.add("    PRG_ID,");
			sql.add("    USR_ID");
			sql.add(") VALUES (");
			sql.add("    ?,", getCompanyCode());
			sql.add("    ?,", bean.getCode());
			sql.add("    ?,", bean.getName());
			sql.add("    ?,", bean.getNames());
			sql.add("    ?,", bean.getNamek());
			sql.add("    ?,", bean.getCbkCode());
			sql.add("    ?,", bean.getBnkCode());
			sql.add("    ?,", bean.getStnCode());
			sql.add("    ?,", bean.getYknNo());
			sql.add("    ?,", bean.getKozaKbnValue());
			sql.add("    ?,", bean.getYknKana());
			sql.add("    ?,", bean.getDateFrom());
			sql.add("    ?,", bean.getDateTo());
			sql.addYMDHMS("    ?,", getNow());
			sql.add("    ?,", getProgramCode());
			sql.add("    ?", getUserCode());
			sql.add(")");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ���C�� (UPDATE)
	 * 
	 * @param bean ���͏��
	 * @throws TException
	 */
	public void modify(Employee bean) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("UPDATE");
			sql.add("    EMP_MST");
			sql.add("SET");
			sql.add("    EMP_NAME = ?,", bean.getName());
			sql.add("    EMP_NAME_S = ?,", bean.getNames());
			sql.add("    EMP_NAME_K = ?,", bean.getNamek());
			sql.add("    EMP_CBK_CODE = ?,", bean.getCbkCode());
			sql.add("    EMP_FURI_BNK_CODE = ?,", bean.getBnkCode());
			sql.add("    EMP_FURI_stn_CODE = ?,", bean.getStnCode());
			sql.add("    EMP_YKN_NO = ?,", bean.getYknNo());
			sql.add("    EMP_KOZA_KBN = ?,", bean.getKozaKbnValue());
			sql.add("    EMP_YKN_KANA = ?,", bean.getYknKana());
			sql.add("    STR_DATE = ?,", bean.getDateFrom());
			sql.add("    END_DATE = ?,", bean.getDateTo());
			sql.addYMDHMS("    UPD_DATE = ?,", getNow());
			sql.add("    PRG_ID = ?,", getProgramCode());
			sql.add("    USR_ID = ?", getUserCode());
			sql.add("WHERE");
			sql.add("    KAI_CODE = ?", getCompanyCode());
			sql.add("AND");
			sql.add("    EMP_CODE = ?", bean.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ���폜 (DELETE)
	 * 
	 * @param bean �I�����
	 * @throws TException
	 */
	public void delete(Employee bean) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.EMPLOYEE);
		condition.setCompanyCode(getCompanyCode());
		condition.setEmployeeCode(bean.getCode());

		// �g�p�ς݃`�F�b�N
		CheckMasterUseDao cd = (CheckMasterUseDao) getComponent(CheckMasterUseDao.class);
		cd.check(condition);

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("DELETE FROM");
			sql.add("    EMP_MST ");
			sql.add("WHERE");
			sql.add("    KAI_CODE = ? ", getCompanyCode());
			sql.add("AND");
			sql.add("    EMP_CODE = ? ", bean.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ��񌟍� (SELECT)
	 * 
	 * @param condition ��������
	 * @return byte ��������
	 * @throws TException
	 */
	public byte[] getExcel(EmployeeSearchCondition condition) throws TException {

		try {

			List<Employee> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			EmployeeExcel exl = new EmployeeExcel(getUser().getLanguage());

			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �������ʒl��Bean�ɃZ�b�g
	 * 
	 * @param rs ResultSet ��������
	 * @return Employee �������ʒl���Z�b�g���ꂽBean
	 * @throws Exception
	 */
	protected Employee mapping(ResultSet rs) throws Exception {

		Employee bean = new Employee();

		bean.setCode(rs.getString("EMP_CODE"));
		bean.setName(rs.getString("EMP_NAME"));
		bean.setNames(rs.getString("EMP_NAME_S"));
		bean.setNamek(rs.getString("EMP_NAME_K"));
		bean.setCbkCode(rs.getString("EMP_CBK_CODE"));
		if (!Util.isNullOrEmpty(rs.getString("BNK_NAME_S"))) {
			bean.setCbkName(rs.getString("BNK_NAME_S") + " " + rs.getString("BNK_STN_NAME_S"));
		} else {
			bean.setCbkName(" ");
		}
		bean.setBnkCode(rs.getString("EMP_FURI_BNK_CODE"));
		bean.setBnkName(rs.getString("FURI_BNK_NAME_S"));
		bean.setStnCode(rs.getString("EMP_FURI_STN_CODE"));
		bean.setStnName(rs.getString("FURI_STN_NAME_S"));
		bean.setKozaKbn(DepositKind.getDepositKind(rs.getInt("EMP_KOZA_KBN")));
		bean.setYknNo(rs.getString("EMP_YKN_NO"));
		bean.setYknKana(rs.getString("EMP_YKN_KANA"));
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));

		return bean;
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