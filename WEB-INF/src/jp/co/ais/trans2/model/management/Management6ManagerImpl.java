package jp.co.ais.trans2.model.management;

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
 * �Ǘ�6�}�l�[�W���̎����N���X�ł��B
 * 
 * @author AIS
 */
public class Management6ManagerImpl extends TModel implements Management6Manager {

	public List<Management6> get(Management6SearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Management6> list = new ArrayList<Management6>();

		try {

			VCreator sql = getSQL(condition);

			sql.add(" ORDER BY ");
			sql.add("  knr6.KAI_CODE ");
			sql.add(" ,knr6.KNR_CODE_6 ");

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
	 * �w������ɊY������Ǘ�6����Ԃ�<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ�6���
	 * @throws TException
	 */
	public List<Management6> getRef(Management6SearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Management6> list = new ArrayList<Management6>();

		try {

			VCreator sql = getSQL(condition);

			if (condition.getCompanyCodeList() != null && !condition.getCompanyCodeList().isEmpty()) {
				sql.add(" AND knr6.KAI_CODE IN ? ", condition.getCompanyCodeList());
			}

			sql.add(" ORDER BY  ");
			sql.add("   knr6.KNR_CODE_6 ");
			sql.add("  ,CASE WHEN knr6.KAI_CODE = ? THEN 0 ELSE 1 END ", getCompanyCode()); // �����R�[�h�̏ꍇ�Ƀ��O�C����ЗD��
			sql.add("  ,knr6.KAI_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String currentCode = null;

			while (rs.next()) {

				Management6 bean = mapping(rs);

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
	protected VCreator getSQL(Management6SearchCondition condition) {
		VCreator sql = new VCreator();

		sql.add(" SELECT ");
		sql.add(" knr6.KAI_CODE ");
		sql.add(" ,knr6.KNR_CODE_6");
		sql.add(" ,knr6.KNR_NAME_6");
		sql.add(" ,knr6.KNR_NAME_S_6");
		sql.add(" ,knr6.KNR_NAME_K_6");
		sql.add(" ,knr6.STR_DATE");
		sql.add(" ,knr6.END_DATE");
		sql.add(" FROM ");
		sql.add(" KNR6_MST knr6 ");
		sql.add(" WHERE 1 = 1 ");

		// ��ЃR�[�h
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add(" AND	knr6.KAI_CODE = ?", condition.getCompanyCode());
		}

		// �R�[�h
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql.add(" AND	knr6.KNR_CODE_6 = ?", condition.getCode());
		}

		// �R�[�h�O����v
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql.addNLikeAmbi(" AND	knr6.KNR_CODE_6 ?", condition.getCodeLike());
		}

		// ����
		if (!Util.isNullOrEmpty(condition.getNamesLike())) {
			sql.addNLikeAmbi(" AND	knr6.KNR_NAME_S_6 ?", condition.getNamesLike());
		}

		// ��������
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql.addNLikeAmbi(" AND	knr6.KNR_NAME_K_6 ?", condition.getNamekLike());
		}

		// �J�n�R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql.add(" AND	knr6.KNR_CODE_6 >= ?", condition.getCodeFrom());
		}

		// �I���R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql.add(" AND	knr6.KNR_CODE_6 <= ?", condition.getCodeTo());
		}

		// �R�[�h(����)
		if (condition.getManagement6CodeList() != null && condition.getManagement6CodeList().length != 0) {
			sql.add(" AND	knr6.KNR_CODE_6 IN ?", condition.getManagement6CodeList());
		}

		// �L������
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql.add(" AND	knr6.STR_DATE <= ?", condition.getValidTerm());
			sql.add(" AND	knr6.END_DATE >= ?", condition.getValidTerm());
		}
		return sql;
	}

	public void entry(Management6 management) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add(" INSERT INTO KNR6_MST ( ");
			sql.add(" KAI_CODE ");
			sql.add(" ,KNR_CODE_6");
			sql.add(" ,KNR_NAME_6");
			sql.add(" ,KNR_NAME_S_6");
			sql.add(" ,KNR_NAME_K_6");
			sql.add(" ,STR_DATE");
			sql.add(" ,END_DATE");
			sql.add(" ,INP_DATE");
			sql.add(" ,UPD_DATE");
			sql.add(" ,PRG_ID");
			sql.add(" ,USR_ID");
			sql.add(" ) VALUES (");
			sql.add("  ?,", management.getCompanyCode());
			sql.add("  ?,", management.getCode());
			sql.add("  ?,", management.getName());
			sql.add("  ?,", management.getNames());
			sql.add("  ?,", management.getNamek());
			sql.add("  ?,", management.getDateFrom());
			sql.add("  ?,", management.getDateTo());
			sql.adt("  ?,", getNow());
			sql.add(" NULL, ");
			sql.add("  ?,", getProgramCode());
			sql.add("  ? ", getUserCode());
			sql.add(" ) ");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void modify(Management6 management) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();

			sql.add(" UPDATE KNR6_MST ");
			sql.add(" SET ");
			sql.add(" KNR_NAME_6 = ?", management.getName());
			sql.add(" ,KNR_NAME_S_6 = ?", management.getNames());
			sql.add(" ,KNR_NAME_K_6 = ?", management.getNamek());
			sql.add(" ,STR_DATE = ?", management.getDateFrom());
			sql.add(" ,END_DATE = ?", management.getDateTo());
			sql.adt(" ,UPD_DATE = ?", getNow());
			sql.add(" ,PRG_ID = ?", getProgramCode());
			sql.add(" ,USR_ID = ?", getUserCode());
			sql.add(" WHERE KAI_CODE = ?", management.getCompanyCode());
			sql.add(" AND	KNR_CODE_6 = ?", management.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void delete(Management6 management) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.MANAGEMENT_6);
		condition.setCompanyCode(management.getCompanyCode());
		condition.setManagement6Code(management.getCode());

		// �g�p�ς݃`�F�b�N
		CheckMasterUseDao cd = (CheckMasterUseDao) getComponent(CheckMasterUseDao.class);
		cd.check(condition);

		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();
			sql.add(" DELETE FROM KNR6_MST ");
			sql.add(" WHERE KAI_CODE = ?", management.getCompanyCode());
			sql.add(" AND	KNR_CODE_6 = ?", management.getCode());

			DBUtil.execute(conn, sql);
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return �G���e�B�e�B
	 * @throws Exception
	 */
	protected Management6 mapping(ResultSet rs) throws Exception {

		Management6 bean = new Management6();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("KNR_CODE_6"));
		bean.setName(rs.getString("KNR_NAME_6"));
		bean.setNames(rs.getString("KNR_NAME_S_6"));
		bean.setNamek(rs.getString("KNR_NAME_K_6"));
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));

		return bean;

	}

	/**
	 * �Ǘ�6�ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̊Ǘ�1�ꗗ
	 * @throws TException
	 */
	public byte[] getExcel(Management6SearchCondition condition) throws TException {

		List<Management6> list = get(condition);
		if (list == null || list.isEmpty()) {
			return null;
		}

		Management6Excel layout = new Management6Excel(getUser().getLanguage());
		byte[] data = layout.getExcel(getCompany(), list);

		return data;

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
