package jp.co.ais.trans2.model.remark;

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
 * 摘要マネージャの実装クラスです。
 * 
 * @author AIS
 */
public class RemarkManagerImpl extends TModel implements RemarkManager {

	public List<Remark> get(RemarkSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Remark> list = new ArrayList<Remark>();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("SELECT");
			sql.add(" tek.KAI_CODE, ");
			sql.add(" tek.TEK_KBN, ");
			sql.add(" tek.DATA_KBN, ");
			sql.add(" tek.TEK_CODE, ");
			sql.add(" tek.TEK_NAME, ");
			sql.add(" tek.TEK_NAME_K, ");
			sql.add(" tek.STR_DATE, ");
			sql.add(" tek.END_DATE ");
			sql.add(" FROM ");
			sql.add(" TEK_MST tek ");
			sql.add(" WHERE 1 = 1 ");

			// 会社コード
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add(" AND	tek.KAI_CODE = ?", condition.getCompanyCode());
			}

			// コード
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add(" AND	tek.TEK_CODE = ?", condition.getCode());

			}

			// コード前方一致
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.add("AND");
				sql.addLikeFront(" 	tek.TEK_CODE ?", condition.getCodeLike());
			}

			// 名称
			if (!Util.isNullOrEmpty(condition.getNameLike())) {
				sql.add("AND");
				sql.addLikeAmbi(" 	tek.TEK_NAME ?", condition.getNameLike());
			}

			// 検索名称
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql.add("AND");
				sql.addLikeAmbi(" 	tek.TEK_NAME_K ?", condition.getNamekLike());
			}

			// 開始コード
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add(" AND	tek.TEK_CODE >= ?", condition.getCodeFrom());
			}

			// 終了コード
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add(" AND	tek.TEK_CODE <= ?", condition.getCodeTo());
			}

			// 有効期間
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				sql.add("  AND  tek.STR_DATE <= ? ", condition.getValidTerm());
				sql.add("  AND  tek.END_DATE >= ? ", condition.getValidTerm());
			}

			// 全件抽出するか
			if ((condition.isSlipRemark()) & (condition.isSlipRowRemark())) {
				sql.add(" AND (tek.TEK_KBN = 0 OR tek.TEK_KBN = 1)");
			}

			// データ区分
			if (!Util.isNullOrEmpty(condition.getDataType())) {
				sql.add(" AND	tek.DATA_KBN = ? ", condition.getDataType());
			}

			// データ区分リスト
			if (condition.getDataTypeList() != null && !condition.getDataTypeList().isEmpty()) {
				sql.add(" AND	tek.DATA_KBN IN = ? ", condition.getDataTypeList());
			}

			// 伝票摘要を抽出するか
			if (!condition.isSlipRemark()) {
				sql.add(" AND tek.TEK_KBN <> 0 ");
			}

			// 行摘要を抽出するか
			if (!condition.isSlipRowRemark()) {
				sql.add(" AND tek.TEK_KBN <> 1");
			}

			sql.add(" ORDER BY ");
			sql.add(" tek.TEK_CODE ");

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
	 * 情報登録 (INSERT)
	 * 
	 * @param bean 入力情報
	 * @throws TException
	 */
	public void entry(Remark bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("INSERT INTO  TEK_MST (");
			sql.add(" KAI_CODE, ");
			sql.add(" TEK_KBN, ");
			sql.add(" DATA_KBN, ");
			sql.add(" TEK_CODE, ");
			sql.add(" TEK_NAME, ");
			sql.add(" TEK_NAME_K, ");
			sql.add(" STR_DATE, ");
			sql.add(" END_DATE, ");
			sql.add(" INP_DATE,");
			sql.add(" PRG_ID, ");
			sql.add(" USR_ID ");

			sql.add(") VALUES (");
			sql.add("    ?,", bean.getCompanyCode());
			sql.add("    ?,", bean.isSlipRemark() ? 0 : 1);
			sql.add("    ?,", bean.getDataType());
			sql.add("    ?,", bean.getCode());
			sql.add("    ?,", bean.getName());
			sql.add("    ?,", bean.getNamek());
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
	 * 情報削除 (DELETE)
	 * 
	 * @param bean 選択情報
	 * @throws TException
	 */
	public void delete(Remark bean) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.EMPLOYEE);
		condition.setCompanyCode(bean.getCompanyCode());
		condition.setEmployeeCode(bean.getCode());

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("");
			sql.add("DELETE FROM");
			sql.add("    TEK_MST ");
			sql.add("WHERE");
			sql.add("    KAI_CODE = ? ", bean.getCompanyCode());
			sql.add("AND");
			sql.add("    TEK_CODE = ? ", bean.getCode());
			sql.add("AND");
			sql.add("    TEK_KBN = ? ", bean.isSlipRemark() ? 0 : 1);

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 情報修正 (UPDATE)
	 * 
	 * @param bean 入力情報
	 * @throws TException
	 */
	public void modify(Remark bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("");
			sql.add("UPDATE");
			sql.add("    TEK_MST");
			sql.add("SET");
			sql.add("    DATA_KBN = ?,", bean.getDataType());
			sql.add("    TEK_NAME = ?,", bean.getName());
			sql.add("    TEK_NAME_K = ?,", bean.getNamek());
			sql.add("    STR_DATE = ?,", bean.getDateFrom());
			sql.add("    END_DATE = ?,", bean.getDateTo());
			sql.addYMDHMS("    UPD_DATE = ?,", getNow());
			sql.add("    PRG_ID = ?,", getProgramCode());
			sql.add("    USR_ID = ?", getUserCode());
			sql.add("WHERE");
			sql.add("    KAI_CODE = ?", getCompanyCode());
			sql.add("AND");
			sql.add("    TEK_CODE = ?", bean.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * エクセル取得
	 * 
	 * @param condition 検索条件
	 * @return byte 検索結果
	 * @throws TException
	 */
	public byte[] getExcel(RemarkSearchCondition condition) throws TException {

		try {

			List<Remark> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			RemarkExcel exl = new RemarkExcel(getUser().getLanguage());

			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * O/Rマッピング /** 検索結果値をBeanにセット
	 * 
	 * @param rs
	 * @return Remark
	 * @throws Exception
	 */
	protected Remark mapping(ResultSet rs) throws Exception {

		Remark bean = new Remark();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setSlipRemark(rs.getInt("TEK_KBN") == 0);
		bean.setDataType(rs.getString("DATA_KBN"));
		bean.setCode(rs.getString("TEK_CODE"));
		bean.setName(rs.getString("TEK_NAME"));
		bean.setNamek(rs.getString("TEK_NAME_K"));
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));
		bean.setDataType(rs.getString("DATA_KBN"));

		return bean;

	}
}
