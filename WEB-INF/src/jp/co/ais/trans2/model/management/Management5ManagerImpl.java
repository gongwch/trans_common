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
 * �Ǘ�5�}�l�[�W���̎����N���X�ł��B
 * 
 * @author AIS
 */
public class Management5ManagerImpl extends TModel implements Management5Manager {

	public List<Management5> get(Management5SearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Management5> list = new ArrayList<Management5>();

		try {

			VCreator sql = getSQL(condition);

			sql.add(" ORDER BY ");
			sql.add("  knr5.KAI_CODE ");
			sql.add(" ,knr5.KNR_CODE_5 ");

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
	 * �w������ɊY������Ǘ�5����Ԃ�<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ�5���
	 * @throws TException
	 */
	public List<Management5> getRef(Management5SearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Management5> list = new ArrayList<Management5>();

		try {

			VCreator sql = getSQL(condition);

			if (condition.getCompanyCodeList() != null && !condition.getCompanyCodeList().isEmpty()) {
				sql.add(" AND knr5.KAI_CODE IN ? ", condition.getCompanyCodeList());
			}

			sql.add(" ORDER BY  ");
			sql.add("   knr5.KNR_CODE_5 ");
			sql.add("  ,CASE WHEN knr5.KAI_CODE = ? THEN 0 ELSE 1 END ", getCompanyCode()); // �����R�[�h�̏ꍇ�Ƀ��O�C����ЗD��
			sql.add("  ,knr5.KAI_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String currentCode = null;

			while (rs.next()) {

				Management5 bean = mapping(rs);

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
	protected VCreator getSQL(Management5SearchCondition condition) {
		VCreator sql = new VCreator();

		sql.add("  SELECT             ");
		sql.add("  knr5.KAI_CODE      ");
		sql.add(" ,knr5.KNR_CODE_5    ");
		sql.add(" ,knr5.KNR_NAME_5    ");
		sql.add(" ,knr5.KNR_NAME_S_5  ");
		sql.add(" ,knr5.KNR_NAME_K_5  ");
		sql.add(" ,knr5.STR_DATE      ");
		sql.add(" ,knr5.END_DATE      ");

		sql.add("  FROM               ");
		sql.add("  KNR5_MST knr5      ");

		sql.add("  WHERE 1 = 1        ");

		// ��ЃR�[�h
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add(" AND knr5.KAI_CODE = ? ", condition.getCompanyCode());
		}

		// �R�[�h
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql.add(" AND knr5.KNR_CODE_5 = ? ", condition.getCode());
		}

		// �R�[�h�O����v
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql.addNLikeAmbi(" AND knr5.KNR_CODE_5  ? ", condition.getCodeLike());
		}

		// ����
		if (!Util.isNullOrEmpty(condition.getNamesLike())) {
			sql.addNLikeAmbi(" AND knr5.KNR_NAME_S_5  ? ", condition.getNamesLike());
		}

		// ��������
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql.addNLikeAmbi(" AND knr5.KNR_NAME_K_5  ? ", condition.getNamekLike());
		}

		// �J�n�R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql.add(" AND	knr5.KNR_CODE_5 >= ?", condition.getCodeFrom());
		}

		// �I���R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql.add(" AND	knr5.KNR_CODE_5 <= ?", condition.getCodeTo());
		}

		// �R�[�h(����)
		if (condition.getManagement5CodeList() != null && condition.getManagement5CodeList().length != 0) {
			sql.add(" AND	knr5.KNR_CODE_5 IN ?", condition.getManagement5CodeList());
		}

		// �L������
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql.add(" AND	knr5.STR_DATE <= ?", condition.getValidTerm());
			sql.add(" AND	knr5.END_DATE >= ?", condition.getValidTerm());
		}
		return sql;
	}

	public void entry(Management5 management) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("  INSERT INTO KNR5_MST (        ");
			sql.add("  KAI_CODE                      ");
			sql.add(" ,KNR_CODE_5                    ");
			sql.add(" ,KNR_NAME_5                    ");
			sql.add(" ,KNR_NAME_S_5                  ");
			sql.add(" ,KNR_NAME_K_5                  ");
			sql.add(" ,STR_DATE                      ");
			sql.add(" ,END_DATE                      ");
			sql.add(" ,INP_DATE                      ");
			sql.add(" ,UPD_DATE                      ");
			sql.add(" ,PRG_ID                      ");
			sql.add(" ,USR_ID                      ");
			sql.add("  ) VALUES (                    ");
			sql.add("  ? ", management.getCompanyCode());
			sql.add(" ,? ", management.getCode());
			sql.add(" ,? ", management.getName());
			sql.add(" ,? ", management.getNames());
			sql.add(" ,? ", management.getNamek());
			sql.add(" ,? ", management.getDateFrom());
			sql.add(" ,? ", management.getDateTo());
			sql.adt(" ,? ", getNow());
			sql.add(" ,NULL                          ");
			sql.add(" ,? ", getProgramCode());
			sql.add(" ,? ", getUserCode());
			sql.add("  )                             ");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void modify(Management5 management) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("  UPDATE KNR5_MST                         ");
			sql.add("  SET                                     ");
			sql.add("  KNR_NAME_5 = ?", management.getName());
			sql.add(" ,KNR_NAME_S_5 = ?", management.getNames());
			sql.add(" ,KNR_NAME_K_5 = ?", management.getNamek());
			sql.add(" ,STR_DATE = ?", management.getDateFrom());
			sql.add(" ,END_DATE = ?", management.getDateTo());
			sql.adt(" ,UPD_DATE = ?", getNow());
			sql.add(" ,PRG_ID = ?", getProgramCode());
			sql.add(" ,USR_ID = ?", getUserCode());
			sql.add("  WHERE KAI_CODE = ?", management.getCompanyCode());
			sql.add("  AND KNR_CODE_5 = ?", management.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void delete(Management5 management) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.MANAGEMENT_5);
		condition.setCompanyCode(management.getCompanyCode());
		condition.setManagement5Code(management.getCode());

		// �g�p�ς݃`�F�b�N
		CheckMasterUseDao cd = (CheckMasterUseDao) getComponent(CheckMasterUseDao.class);
		cd.check(condition);

		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("  DELETE FROM KNR5_MST                    ");
			sql.add("  WHERE KAI_CODE = ?", management.getCompanyCode());
			sql.add("  AND KNR_CODE_5 = ?", management.getCode());

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
	protected Management5 mapping(ResultSet rs) throws Exception {

		Management5 bean = new Management5();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("KNR_CODE_5"));
		bean.setName(rs.getString("KNR_NAME_5"));
		bean.setNames(rs.getString("KNR_NAME_S_5"));
		bean.setNamek(rs.getString("KNR_NAME_K_5"));
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));

		return bean;

	}

	/**
	 * �Ǘ�5�ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̊Ǘ�5�ꗗ
	 * @throws TException
	 */
	public byte[] getExcel(Management5SearchCondition condition) throws TException {

		List<Management5> list = get(condition);
		if (list == null || list.isEmpty()) {
			return null;
		}

		Management5Excel layout = new Management5Excel(getUser().getLanguage());
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
