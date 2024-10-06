package jp.co.ais.trans2.model.country;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * �����Dao�̎����N���X����
 */
public class CountryDaoImpl extends TModel implements CountryDao {

	/**
	 * ����
	 * 
	 * @param condition ����
	 * @return ��������
	 * @throws TException
	 */
	public List<Country> get(CountrySearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			List<Country> list = new ArrayList<Country>();

			VCreator sql = new VCreator();
			sql.add("SELECT");
			sql.add("  cou.COU_CODE,");
			sql.add("  cou.COU_CODE2,");
			sql.add("  cou.COU_NO,");
			sql.add("  cou.COU_NAME,");
			sql.add("  cou.COU_NAME2,");
			sql.add("  cou.STR_DATE,");
			sql.add("  cou.END_DATE");
			sql.add("FROM COUNTRY_MST cou");
			sql.add("WHERE 1 = 1");

			sql.add(getSelectWhereSQL(condition));

			sql.add("ORDER BY");
			sql.add("  cou.COU_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}

			DBUtil.close(statement);
			DBUtil.close(rs);

			return list;

		} catch (SQLException e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * SELECT����WHERE����
	 * 
	 * @param condition ����
	 * @return WHERE��
	 */
	protected String getSelectWhereSQL(CountrySearchCondition condition) {
		VCreator sql = new VCreator();

		// �R�[�h
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql.add(" AND cou.COU_CODE = ?", condition.getCode());
		}

		// �R�[�h�O����v
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql.addLikeFront(" AND cou.COU_CODE ?", condition.getCodeLike());
		}

		// ���̂����܂�
		if (!Util.isNullOrEmpty(condition.getNameLike())) {
			sql.addNLikeAmbi("  AND cou.COU_NAME ?", condition.getNameLike());
		}

		// �R�[�h2
		if (!Util.isNullOrEmpty(condition.getCode2())) {
			sql.add(" AND cou.COU_CODE2 = ?", condition.getCode2());
		}

		// �J�n�R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql.add("  AND cou.COU_CODE >= ?", condition.getCodeFrom());
		}

		// �I���R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql.add("  AND cou.COU_CODE <= ?", condition.getCodeTo());
		}

		// �L������
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql.adt("  AND cou.STR_DATE <= ?", condition.getValidTerm());
			sql.adt("  AND cou.END_DATE >= ?", condition.getValidTerm());
		}

		// �ŏI�X�V����
		if (condition.getLastUpdateDate() != null) {
			sql.adt(" AND  (cou.INP_DATE > ? ", condition.getLastUpdateDate());
			sql.adt("    OR cou.UPD_DATE > ?)", condition.getLastUpdateDate());
		}

		return sql.toSQL();
	}

	/**
	 * �������ʒl��Bean�ɃZ�b�g
	 * 
	 * @param rs ResultSet ��������
	 * @return �������ʒl���Z�b�g���ꂽBean
	 * @throws SQLException
	 */
	protected Country mapping(ResultSet rs) throws SQLException {
		Country bean = createCountry();
		bean.setCode(rs.getString("COU_CODE"));
		bean.setCode2(rs.getString("COU_CODE2"));
		bean.setNumber(rs.getString("COU_NO"));
		bean.setName(rs.getString("COU_NAME"));
		bean.setName2(rs.getString("COU_NAME2"));
		bean.setDateFrom(rs.getTimestamp("STR_DATE"));
		bean.setDateTo(rs.getTimestamp("END_DATE"));

		return bean;
	}

	/**
	 * ���G���e�B�e�B����
	 * 
	 * @return ���G���e�B�e�B
	 */
	protected Country createCountry() {
		return new Country();
	}

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(CountrySearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   COUNTRY_MST cou");
			sql.add(" WHERE  1 = 1 "); // ��ЃR�[�h

			// �ŏI�X�V����
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (cou.INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR cou.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR cou.INP_DATE IS NULL AND cou.UPD_DATE IS NULL) ");
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
	public void entry(Country bean) throws TException {
		// TODO �v����
	}

	/**
	 * ���C�� (UPDATE)
	 * 
	 * @param bean ���͏��
	 * @throws TException
	 */
	public void modify(Country bean) throws TException {
		// TODO �v����
	}

	/**
	 * ���폜 (DELETE)
	 * 
	 * @param bean �I�����
	 * @throws TException
	 */
	public void delete(Country bean) throws TException {
		// TODO �v����
	}

	/**
	 * SQL creator
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
