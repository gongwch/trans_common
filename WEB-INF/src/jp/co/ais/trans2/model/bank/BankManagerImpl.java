package jp.co.ais.trans2.model.bank;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * ��s�E�x�X�}�l�[�W���̎����N���X�ł��B
 * 
 * @author AIS
 */
public class BankManagerImpl extends TModel implements BankManager {

	/**
	 * �w������ɊY�������s����Ԃ� �x�X���͊܂܂�Ȃ��B
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������s���
	 * @throws TException
	 */
	public List<Bank> get(BankSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Bank> list = new ArrayList<Bank>();

		try {

			SQLCreator s = new SQLCreator();
			s.add("SELECT ");
			s.add("	DISTINCT BNK_CODE,");
			s.add("	BNK_NAME_S,");
			s.add("	BNK_NAME_K,");
			s.add("	BNK_KANA");
			s.add("	FROM BNK_MST ");
			s.add("WHERE 1 = 1");

			// �R�[�h
			if (!Util.isNullOrEmpty(condition.getCode())) {
				s.add(" AND	BNK_CODE = ? ", condition.getCode());
			}

			// �R�[�h�O����v
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				s.addLikeFront(" AND	BNK_CODE ?", condition.getCodeLike());
			}

			// ����
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				s.addNLikeAmbi(" AND	BNK_NAME_S ?", condition.getNamesLike());
			}

			// ��������
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				s.addNLikeAmbi(" AND	BNK_NAME_K ?", condition.getNamekLike());
			}

			// �J�n�R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				s.add(" AND	BNK_CODE >= ?", condition.getCodeFrom());
			}

			// �I���R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				s.add(" AND	BNK_CODE <= ?", condition.getCodeTo());
			}

			// �L������
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				s.add(" AND	STR_DATE <= ? ", condition.getValidTerm());
				s.add(" AND	END_DATE >= ? ", condition.getValidTerm());
			}

			s.add("ORDER BY BNK_CODE");
			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, s);

			while (rs.next()) {
				list.add(mappingBank(rs));
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
	 * ��s�E�x�X�����擾
	 * 
	 * @param condition
	 * @return ��s�E�x�X���
	 * @throws TException
	 */
	public List<Bank> get(BranchSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Bank> list = new ArrayList<Bank>();

		try {
			SQLCreator s = new SQLCreator();

			s.add("SELECT");
			s.add("	BNK_CODE,");
			s.add("	BNK_NAME_S,");
			s.add("	BNK_NAME_K,");
			s.add("	BNK_KANA,");
			s.add("	BNK_STN_CODE,");
			s.add("	BNK_STN_NAME_S,");
			s.add("	BNK_STN_NAME_K,");
			s.add("	BNK_STN_KANA,");
			s.add("	STR_DATE,");
			s.add("	END_DATE");
			s.add("FROM BNK_MST ");

			s.add(" WHERE 1 = 1");

			// ��s�R�[�h
			if (!Util.isNullOrEmpty(condition.getBankCode())) {
				s.add(" AND	BNK_CODE = ? ", condition.getBankCode());
			}

			// �x�X�R�[�h
			if (!Util.isNullOrEmpty(condition.getCode())) {
				s.add(" AND	BNK_STN_CODE = ? ", condition.getCode());
			}

			// �R�[�h�O����v
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				s.addLikeFront(" AND	BNK_STN_CODE ?", condition.getCodeLike());
			}

			// ����
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				s.addNLikeAmbi(" AND	BNK_STN_NAME_S ?", condition.getNamesLike());
			}

			// ��������
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				s.addNLikeAmbi(" AND	BNK_STN_NAME_K ?", condition.getNamekLike());
			}

			// �J�n�R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				s.add(" AND	BNK_STN_CODE >= ?", condition.getCodeFrom());
			}

			// �I���R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				s.add(" AND	BNK_STN_CODE <= ?", condition.getCodeTo());
			}

			// �L������
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				s.add(" AND	STR_DATE <= ? ", condition.getValidTerm());
				s.add(" AND	END_DATE >= ? ", condition.getValidTerm());
			}

			s.add("ORDER BY BNK_CODE");
			s.add("    , BNK_STN_CODE");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, s);

			while (rs.next()) {
				list.add(mappingBankBranch(rs));
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
	 * ��s����o�^����B
	 * 
	 * @param bean ��s���
	 * @throws TException
	 */
	public void entry(Bank bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator s = new SQLCreator();

			s.add("INSERT INTO BNK_MST (");
			s.add("	BNK_CODE,");
			s.add("	BNK_NAME_S,");
			s.add("	BNK_NAME_K,");
			s.add("	BNK_KANA,");
			s.add("	BNK_STN_CODE,");
			s.add("	BNK_STN_NAME_S,");
			s.add("	BNK_STN_NAME_K,");
			s.add("	BNK_STN_KANA,");
			s.add("	STR_DATE,");
			s.add("	END_DATE,");
			s.add(" INP_DATE,");
			s.add(" PRG_ID,");
			s.add(" USR_ID");
			s.add(") VALUES (");
			s.add("  ?,", bean.getCode());
			s.add("  ?,", bean.getNames());
			s.add("  ?,", bean.getNamek());
			s.add("  ?,", bean.getKana());
			s.add("  ?,", bean.getBranch().getCode());
			s.add("  ?,", bean.getBranch().getNames());
			s.add("  ?,", bean.getBranch().getNamek());
			s.add("  ?,", bean.getBranch().getKana());
			s.add("  ?,", bean.getDateFrom());
			s.add("  ?,", bean.getDateTo());
			s.addYMDHMS("  ?,", getNow());
			s.add("  ?,", getProgramCode());
			s.add("  ? ", getUserCode());
			s.add(")");

			DBUtil.execute(conn, s);

			s = new SQLCreator();

			s.add("UPDATE BNK_MST SET");
			s.add("	BNK_CODE = ?,", bean.getCode());
			s.add("	BNK_NAME_S = ?,", bean.getNames());
			s.add("	BNK_KANA = ?,", bean.getKana());
			s.add("	BNK_NAME_K = ?,", bean.getNamek());
			s.addYMDHMS(" UPD_DATE = ?,", getNow());
			s.add(" PRG_ID = ?,", getProgramCode());
			s.add(" USR_ID = ?", getUserCode());
			s.add("WHERE BNK_CODE = ?", bean.getCode());

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ��s�����C������B
	 * 
	 * @param bean ��s���
	 * @throws TException
	 */
	public void modify(Bank bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator s = new SQLCreator();

			s.add("UPDATE BNK_MST SET");
			s.add("	BNK_CODE = ?,", bean.getCode());
			s.add("	BNK_NAME_S = ?,", bean.getNames());
			s.add("	BNK_NAME_K = ?,", bean.getNamek());
			s.add("	BNK_KANA = ?,", bean.getKana());
			s.add("	BNK_STN_CODE = ?,", bean.getBranch().getCode());
			s.add("	BNK_STN_NAME_S = ?,", bean.getBranch().getNames());
			s.add("	BNK_STN_NAME_K = ?,", bean.getBranch().getNamek());
			s.add("	BNK_STN_KANA = ?,", bean.getBranch().getKana());
			s.add("	STR_DATE = ?,", bean.getDateFrom());
			s.add("	END_DATE = ?,", bean.getDateTo());
			s.addYMDHMS(" UPD_DATE = ? ,", getNow());
			s.add(" PRG_ID = ? ,", getProgramCode());
			s.add(" USR_ID = ?", getUserCode());
			s.add("WHERE BNK_CODE = ?", bean.getCode());
			s.add("AND BNK_STN_CODE = ?", bean.getBranch().getCode());

			DBUtil.execute(conn, s);

			s = new SQLCreator();

			s.add("UPDATE BNK_MST SET");
			s.add("	BNK_CODE = ?,", bean.getCode());
			s.add("	BNK_NAME_S = ?,", bean.getNames());
			s.add("	BNK_KANA = ?,", bean.getKana());
			s.add("	BNK_NAME_K = ?,", bean.getNamek());
			s.addYMDHMS(" UPD_DATE = ?,", getNow());
			s.add(" PRG_ID = ?,", getProgramCode());
			s.add(" USR_ID = ?", getUserCode());
			s.add("WHERE BNK_CODE = ?", bean.getCode());

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ��s�����폜����B
	 * 
	 * @param bean ��s���
	 * @throws TException
	 */
	public void delete(Bank bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator s = new SQLCreator();
			s.add("DELETE FROM BNK_MST ");
			s.add("WHERE BNK_CODE = ?", bean.getCode());
			s.add("AND BNK_STN_CODE =?", bean.getBranch().getCode());

			DBUtil.execute(conn, s);
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �G�N�Z���o��
	 */
	public byte[] getExcel(BranchSearchCondition condition) throws TException {

		try {
			List<Bank> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			BankExcel exl = new BankExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return �G���e�B�e�B
	 * @throws Exception
	 */
	protected Bank mappingBank(ResultSet rs) throws Exception {

		Bank bean = new Bank();

		// ��s�R�[�h
		bean.setCode(rs.getString("BNK_CODE"));

		// ��s����
		bean.setNames(rs.getString("BNK_NAME_S"));

		// ��s����
		bean.setNamek(rs.getString("BNK_NAME_K"));

		// ��s��
		bean.setKana(rs.getString("BNK_KANA"));

		return bean;

	}

	/**
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return �G���e�B�e�B
	 * @throws Exception
	 */
	protected Bank mappingBankBranch(ResultSet rs) throws Exception {

		Bank bank = new Bank();

		// ��s�R�[�h
		bank.setCode(rs.getString("BNK_CODE"));
		// ��s����
		bank.setNames(rs.getString("BNK_NAME_S"));
		// ��s����
		bank.setNamek(rs.getString("BNK_NAME_K"));
		// ��s��
		bank.setKana(rs.getString("BNK_KANA"));
		// �J�n�N����
		bank.setDateFrom(rs.getDate("STR_DATE"));
		// �I���N����
		bank.setDateTo(rs.getDate("END_DATE"));

		Branch branch = new Branch();
		// �x�X�R�[�h
		branch.setCode(rs.getString("BNK_STN_CODE"));
		// �x�X����
		branch.setNames(rs.getString("BNK_STN_NAME_S"));
		// �x�X����
		branch.setNamek(rs.getString("BNK_STN_NAME_K"));
		// �x�X��
		branch.setKana(rs.getString("BNK_STN_KANA"));

		bank.setBranch(branch);

		return bank;

	}
}
