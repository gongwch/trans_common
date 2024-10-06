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
 * 銀行・支店マネージャの実装クラスです。
 * 
 * @author AIS
 */
public class BankManagerImpl extends TModel implements BankManager {

	/**
	 * 指定条件に該当する銀行情報を返す 支店情報は含まれない。
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する銀行情報
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

			// コード
			if (!Util.isNullOrEmpty(condition.getCode())) {
				s.add(" AND	BNK_CODE = ? ", condition.getCode());
			}

			// コード前方一致
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				s.addLikeFront(" AND	BNK_CODE ?", condition.getCodeLike());
			}

			// 略称
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				s.addNLikeAmbi(" AND	BNK_NAME_S ?", condition.getNamesLike());
			}

			// 検索名称
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				s.addNLikeAmbi(" AND	BNK_NAME_K ?", condition.getNamekLike());
			}

			// 開始コード
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				s.add(" AND	BNK_CODE >= ?", condition.getCodeFrom());
			}

			// 終了コード
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				s.add(" AND	BNK_CODE <= ?", condition.getCodeTo());
			}

			// 有効期間
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
	 * 銀行・支店情報を取得
	 * 
	 * @param condition
	 * @return 銀行・支店情報
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

			// 銀行コード
			if (!Util.isNullOrEmpty(condition.getBankCode())) {
				s.add(" AND	BNK_CODE = ? ", condition.getBankCode());
			}

			// 支店コード
			if (!Util.isNullOrEmpty(condition.getCode())) {
				s.add(" AND	BNK_STN_CODE = ? ", condition.getCode());
			}

			// コード前方一致
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				s.addLikeFront(" AND	BNK_STN_CODE ?", condition.getCodeLike());
			}

			// 略称
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				s.addNLikeAmbi(" AND	BNK_STN_NAME_S ?", condition.getNamesLike());
			}

			// 検索名称
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				s.addNLikeAmbi(" AND	BNK_STN_NAME_K ?", condition.getNamekLike());
			}

			// 開始コード
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				s.add(" AND	BNK_STN_CODE >= ?", condition.getCodeFrom());
			}

			// 終了コード
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				s.add(" AND	BNK_STN_CODE <= ?", condition.getCodeTo());
			}

			// 有効期間
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
	 * 銀行情報を登録する。
	 * 
	 * @param bean 銀行情報
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
	 * 銀行情報を修正する。
	 * 
	 * @param bean 銀行情報
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
	 * 銀行情報を削除する。
	 * 
	 * @param bean 銀行情報
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
	 * エクセル出力
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
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected Bank mappingBank(ResultSet rs) throws Exception {

		Bank bean = new Bank();

		// 銀行コード
		bean.setCode(rs.getString("BNK_CODE"));

		// 銀行名称
		bean.setNames(rs.getString("BNK_NAME_S"));

		// 銀行略称
		bean.setNamek(rs.getString("BNK_NAME_K"));

		// 銀行ｶﾅ
		bean.setKana(rs.getString("BNK_KANA"));

		return bean;

	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected Bank mappingBankBranch(ResultSet rs) throws Exception {

		Bank bank = new Bank();

		// 銀行コード
		bank.setCode(rs.getString("BNK_CODE"));
		// 銀行名称
		bank.setNames(rs.getString("BNK_NAME_S"));
		// 銀行略称
		bank.setNamek(rs.getString("BNK_NAME_K"));
		// 銀行ｶﾅ
		bank.setKana(rs.getString("BNK_KANA"));
		// 開始年月日
		bank.setDateFrom(rs.getDate("STR_DATE"));
		// 終了年月日
		bank.setDateTo(rs.getDate("END_DATE"));

		Branch branch = new Branch();
		// 支店コード
		branch.setCode(rs.getString("BNK_STN_CODE"));
		// 支店名称
		branch.setNames(rs.getString("BNK_STN_NAME_S"));
		// 支店略称
		branch.setNamek(rs.getString("BNK_STN_NAME_K"));
		// 支店ｶﾅ
		branch.setKana(rs.getString("BNK_STN_KANA"));

		bank.setBranch(branch);

		return bank;

	}
}
