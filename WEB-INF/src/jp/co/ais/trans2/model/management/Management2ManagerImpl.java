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
 * �Ǘ�2�}�l�[�W���̎����N���X�ł��B
 * 
 * @author AIS
 */
public class Management2ManagerImpl extends TModel implements Management2Manager {

	public List<Management2> get(Management2SearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Management2> list = new ArrayList<Management2>();

		try {

			VCreator sql = getSQL(condition);

			sql.add(" ORDER BY ");
			sql.add(" KNR_CODE_2");

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
	 * �w������ɊY������Ǘ�����Ԃ�<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ����
	 * @throws TException
	 */
	public List<Management2> getRef(Management2SearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Management2> list = new ArrayList<Management2>();

		try {

			VCreator sql = getSQL(condition);

			if (condition.getCompanyCodeList() != null && !condition.getCompanyCodeList().isEmpty()) {
				sql.add(" AND KAI_CODE IN ? ", condition.getCompanyCodeList());
			}

			sql.add(" ORDER BY  ");
			sql.add("   KNR_CODE_2 ");
			sql.add("  ,CASE WHEN KNR_CODE_2 = ? THEN 0 ELSE 1 END ", getCompanyCode()); // �����R�[�h�̏ꍇ�Ƀ��O�C����ЗD��
			sql.add("  ,KAI_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String currentCode = null;

			while (rs.next()) {

				Management2 bean = mapping(rs);

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
	protected VCreator getSQL(Management2SearchCondition condition) {
		VCreator sql = new VCreator();

		sql.add("SELECT");
		sql.add("  KAI_CODE ");
		sql.add(" ,KNR_CODE_2 ");
		sql.add(" ,KNR_NAME_2 ");
		sql.add(" ,KNR_NAME_S_2 ");
		sql.add(" ,KNR_NAME_K_2 ");
		sql.add(" ,STR_DATE ");
		sql.add(" ,END_DATE ");
		sql.add(" FROM ");
		sql.add(" KNR2_MST knr2 ");
		sql.add(" WHERE 1 = 1 ");

		// ��ЃR�[�h
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add(" AND	KAI_CODE = ?", condition.getCompanyCode());
		}

		// �R�[�h
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql.add(" AND	KNR_CODE_2 = ?", condition.getCode());
		}

		// �R�[�h�O����v
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql.addNLikeFront("AND KNR_CODE_2 ?", condition.getCodeLike());
		}

		// ����
		if (!Util.isNullOrEmpty(condition.getNamesLike())) {
			sql.addNLikeAmbi(" AND	KNR_NAME_S_2 ?", condition.getNamesLike());
		}

		// ��������
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql.addNLikeAmbi(" AND  KNR_NAME_K_2 ?", condition.getNamekLike());
		}

		// �J�n�R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql.add(" AND	KNR_CODE_2 >= ?", condition.getCodeFrom());
		}

		// �I���R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql.add(" AND	KNR_CODE_2 <= ?", condition.getCodeTo());
		}

		// �L������
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql.add(" AND	STR_DATE <= ?", condition.getValidTerm());
			sql.add(" AND	END_DATE >= ?", condition.getValidTerm());
		}
		return sql;
	}

	public void entry(Management2 management) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("INSERT INTO  KNR2_MST (");
			sql.add("  KAI_CODE ");
			sql.add(" ,KNR_CODE_2 ");
			sql.add(" ,KNR_NAME_2 ");
			sql.add(" ,KNR_NAME_S_2 ");
			sql.add(" ,KNR_NAME_K_2 ");
			sql.add(" ,STR_DATE ");
			sql.add(" ,END_DATE ");
			sql.add(" ,INP_DATE ");
			sql.add(" ,UPD_DATE ");
			sql.add(" ,PRG_ID ");
			sql.add(" ,USR_ID ");

			sql.add(") VALUES (");
			sql.add("     ?", management.getCompanyCode());
			sql.add("    ,?", management.getCode());
			sql.add("    ,?", management.getName());
			sql.add("    ,?", management.getNames());
			sql.add("    ,?", management.getNamek());
			sql.add("    ,?", management.getDateFrom());
			sql.add("    ,?", management.getDateTo());
			sql.adt("    ,?", getNow());
			sql.add(",NULL");
			sql.add("    ,?", getProgramCode());
			sql.add("    ,?", getUserCode());
			sql.add(")");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void modify(Management2 management2) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();

			sql.add("");
			sql.add("UPDATE");
			sql.add("    KNR2_MST");
			sql.add("SET");
			sql.add("  KNR_NAME_2 = ?", management2.getName());
			sql.add(" ,KNR_NAME_S_2= ?", management2.getNames());
			sql.add(" ,KNR_NAME_K_2= ?", management2.getNamek());
			sql.add(" ,STR_DATE= ?", management2.getDateFrom());
			sql.add(" ,END_DATE = ?", management2.getDateTo());
			sql.adt(" ,UPD_DATE = ?", getNow());
			sql.add(" ,PRG_ID = ?", getProgramCode());
			sql.add(" ,USR_ID = ?", getUserCode());
			sql.add("WHERE");
			sql.add("    KAI_CODE = ?", management2.getCompanyCode());
			sql.add("AND");
			sql.add("    KNR_CODE_2 = ?", management2.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * �Ǘ��Q�ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̊Ǘ�1�ꗗ
	 * @throws TException
	 */
	public byte[] getExcel(Management2SearchCondition condition) throws TException {

		List<Management2> list = get(condition);
		if (list == null || list.isEmpty()) {
			return null;
		}

		Management2Excel layout = new Management2Excel(getUser().getLanguage());
		byte[] data = layout.getExcel(getCompany(), list);

		return data;

	}

	public void delete(Management2 management) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.MANAGEMENT_2);
		condition.setCompanyCode(management.getCompanyCode());
		condition.setManagement2Code(management.getCode());

		// �g�p�ς݃`�F�b�N
		CheckMasterUseDao cd = (CheckMasterUseDao) getComponent(CheckMasterUseDao.class);
		cd.check(condition);

		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("DELETE FROM");
			sql.add("    KNR2_MST");
			sql.add("WHERE");
			sql.add("  KAI_CODE = ? ", management.getCompanyCode());
			sql.add("AND");
			sql.add(" KNR_CODE_2 = ? ", management.getCode());

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
	protected Management2 mapping(ResultSet rs) throws Exception {

		Management2 bean = new Management2();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("KNR_CODE_2"));
		bean.setName(rs.getString("KNR_NAME_2"));
		bean.setNames(rs.getString("KNR_NAME_S_2"));
		bean.setNamek(rs.getString("KNR_NAME_K_2"));
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
