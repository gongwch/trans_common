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
 * �Ǘ�3�}�l�[�W���̎����N���X�ł��B
 * 
 * @author AIS
 */
public class Management3ManagerImpl extends TModel implements Management3Manager {

	/**
	 * ��񌟍� (SELECT)
	 * 
	 * @param condition ��������
	 * @return List ��������
	 * @throws TException
	 */
	public List<Management3> get(Management3SearchCondition condition) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		// �������ʊi�[�p�z�񐶐�
		List<Management3> list = new ArrayList<Management3>();

		try {

			VCreator sql = getSQL(condition);

			sql.add("ORDER BY");
			sql.add("knr3.KNR_CODE_3");

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
	 * �w������ɊY������Ǘ�3����Ԃ�<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ�3���
	 * @throws TException
	 */
	public List<Management3> getRef(Management3SearchCondition condition) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		// �������ʊi�[�p�z�񐶐�
		List<Management3> list = new ArrayList<Management3>();

		try {

			VCreator sql = getSQL(condition);

			if (condition.getCompanyCodeList() != null && !condition.getCompanyCodeList().isEmpty()) {
				sql.add(" AND knr3.KAI_CODE IN ? ", condition.getCompanyCodeList());
			}

			sql.add(" ORDER BY  ");
			sql.add("   knr3.KNR_CODE_3 ");
			sql.add("  ,CASE WHEN knr3.KAI_CODE = ? THEN 0 ELSE 1 END ", getCompanyCode()); // �����R�[�h�̏ꍇ�Ƀ��O�C����ЗD��
			sql.add("  ,knr3.KAI_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String currentCode = null;

			while (rs.next()) {

				Management3 bean = mapping(rs);

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
	protected VCreator getSQL(Management3SearchCondition condition) {
		VCreator sql = new VCreator();

		sql.add("");
		sql.add(" SELECT ");
		sql.add("	 knr3.KAI_CODE ");
		sql.add("	,knr3.KNR_CODE_3 ");
		sql.add(" 	,knr3.KNR_NAME_3 ");
		sql.add("	,knr3.KNR_NAME_S_3 ");
		sql.add("	,knr3.KNR_NAME_K_3 ");
		sql.add("	,knr3.STR_DATE ");
		sql.add("	,knr3.END_DATE ");
		sql.add(" FROM KNR3_MST knr3");
		sql.add(" WHERE 1 = 1 ");

		// ��ЃR�[�h
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add("AND");
			sql.add("		knr3.KAI_CODE =?", condition.getCompanyCode());
		}

		// �R�[�h
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql.add("AND");
			sql.add("		knr3.KNR_CODE_3 =?", condition.getCode());
		}

		// �R�[�h�O����v
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql.add("AND");
			sql.addLikeFront("    knr3.KNR_CODE_3 ?", condition.getCodeLike());
		}

		// ���̞B��
		if (!Util.isNullOrEmpty(condition.getNamesLike())) {
			sql.add("AND");
			sql.addNLikeAmbi("		knr3.KNR_NAME_S_3 ?", condition.getNamesLike());
		}

		// �������̞B��
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql.add("AND");
			sql.addNLikeAmbi("		knr3.KNR_NAME_K_3 ?", condition.getNamekLike());
		}

		// �J�n�R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql.add("AND");
			sql.add("		knr3.KNR_CODE_3 >=?", condition.getCodeFrom());
		}

		// �I���R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql.add("AND");
			sql.add("		knr3.KNR_CODE_3 <=?", condition.getCodeTo());
		}

		// �R�[�h(����)
		if (condition.getManagement3CodeList() != null && condition.getManagement3CodeList().length != 0) {
			sql.add("AND knr3.KNR_CODE_3 IN");
			sql.addInStatement(condition.getManagement3CodeList());
		}

		// �L������
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql.add("AND");
			sql.addYMDHMS("		knr3.STR_DATE <= ?", condition.getValidTerm());
			sql.add("AND");
			sql.addYMDHMS("		knr3.END_DATE >= ?", condition.getValidTerm());
		}

		// �ŏI�X�V����
		if (condition.getLastUpdateDate() != null) {
			sql.adt(" AND  (knr3.INP_DATE > ? ", condition.getLastUpdateDate());
			sql.adt("    OR knr3.UPD_DATE > ?)", condition.getLastUpdateDate());
		}
		return sql;
	}

	/**
	 * ���o�^ (INSERT)
	 * 
	 * @param bean ���͏��
	 * @throws TException
	 */
	public void entry(Management3 bean) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();

			sql.add("");
			sql.add("INSERT INTO KNR3_MST (");
			sql.add("	 KAI_CODE ");
			sql.add("	,KNR_CODE_3 ");
			sql.add(" 	,KNR_NAME_3 ");
			sql.add("	,KNR_NAME_S_3 ");
			sql.add("	,KNR_NAME_K_3 ");
			sql.add("	,STR_DATE ");
			sql.add("	,END_DATE ");
			sql.add("	,INP_DATE ");
			sql.add("	,UPD_DATE ");
			sql.add("	,PRG_ID ");
			sql.add("	,USR_ID ");
			sql.add(" ) VALUES (");
			sql.add("     ?", getCompanyCode());
			sql.add("    ,?", bean.getCode());
			sql.add("    ,?", bean.getName());
			sql.add("    ,?", bean.getNames());
			sql.add("    ,?", bean.getNamek());
			sql.add("    ,?", bean.getDateFrom());
			sql.add("    ,?", bean.getDateTo());
			sql.addYMDHMS("    ,?", getNow());
			sql.add(" ,NULL");
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

	/**
	 * ���C�� (UPDATE)
	 * 
	 * @param bean ���͏��
	 * @throws TException
	 */
	public void modify(Management3 bean) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();

			sql.add("");
			sql.add("UPDATE");
			sql.add("    KNR3_MST");
			sql.add("SET");
			sql.add("    KNR_NAME_3 = ?", bean.getName());
			sql.add("    ,KNR_NAME_S_3 = ?", bean.getNames());
			sql.add("    ,KNR_NAME_K_3 = ?", bean.getNamek());
			sql.add("    ,STR_DATE = ?", bean.getDateFrom());
			sql.add("    ,END_DATE = ?", bean.getDateTo());
			sql.addYMDHMS("    ,UPD_DATE = ?", getNow());
			sql.add("    ,PRG_ID = ?", getProgramCode());
			sql.add("    ,USR_ID = ?", getUserCode());
			sql.add("WHERE");
			sql.add("    KAI_CODE = ?", getCompanyCode());
			sql.add("AND");
			sql.add("    KNR_CODE_3 = ?", bean.getCode());

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
	public void delete(Management3 bean) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.MANAGEMENT_3);
		condition.setCompanyCode(bean.getCompanyCode());
		condition.setManagement3Code(bean.getCode());

		// �g�p�ς݃`�F�b�N
		CheckMasterUseDao cd = (CheckMasterUseDao) getComponent(CheckMasterUseDao.class);
		cd.check(condition);

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();

			sql.add("");
			sql.add("DELETE FROM");
			sql.add("    KNR3_MST ");
			sql.add("WHERE");
			sql.add("    KAI_CODE = ? ", getCompanyCode());
			sql.add("AND");
			sql.add("    KNR_CODE_3 = ? ", bean.getCode());

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
	protected Management3 mapping(ResultSet rs) throws Exception {

		Management3 bean = new Management3();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("KNR_CODE_3"));
		bean.setName(rs.getString("KNR_NAME_3"));
		bean.setNames(rs.getString("KNR_NAME_S_3"));
		bean.setNamek(rs.getString("KNR_NAME_K_3"));
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));

		return bean;

	}

	/**
	 * �Ǘ�3�ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̊Ǘ�3�ꗗ
	 * @throws TException
	 */
	public byte[] getExcel(Management3SearchCondition condition) throws TException {

		List<Management3> list = get(condition);
		if (list == null || list.isEmpty()) {
			return null;
		}

		Management3Excel layout = new Management3Excel(getUser().getLanguage());
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
