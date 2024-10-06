package jp.co.ais.trans2.model.payment;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * 支払方法マネージャの実装クラスです。
 * 
 * @author AIS
 */
public class PaymentMethodManagerImpl extends TModel implements PaymentMethodManager {

	/**
	 * 指定条件に該当する支払方法情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する支払方法情報
	 * @throws TException
	 */
	public List<PaymentMethod> get(PaymentMethodSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<PaymentMethod> list = new ArrayList<PaymentMethod>();

		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT hoh.*");

			sql.add(" FROM AP_HOH_MST hoh");

			sql.add(" WHERE 1 = 1 ");

			// 会社コード
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add("   AND hoh.KAI_CODE = ?", condition.getCompanyCode());
			}

			// コード
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add("   AND hoh.HOH_HOH_CODE = ?", condition.getCode());
			}

			// 開始コード
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add("   AND hoh.HOH_HOH_CODE >= ?", condition.getCodeFrom());
			}

			// 終了コード
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add("   AND hoh.HOH_HOH_CODE <= ?", condition.getCodeTo());
			}
			// コード前方一致
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.add("AND");
				sql.addLikeFront("  hoh.HOH_HOH_CODE ?", condition.getCodeLike());
			}

			// 名称あいまい
			if (!Util.isNullOrEmpty(condition.getNameLike())) {
				sql.add("AND");
				sql.addNLikeAmbi("  hoh.HOH_HOH_NAME ?", condition.getNameLike());
			}

			// 検索名称あいまい
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql.add("AND");
				sql.addNLikeAmbi("  hoh.HOH_HOH_NAME_K ?", condition.getNamekLike());
			}

			// 0:社員支払
			if (condition.isUseEmployeePayment()) {
				sql.add("   AND hoh.HOH_SIH_KBN = 0 ");
			}

			// 1:社外支払
			if (condition.isUseExPayment()) {
				sql.add("   AND hoh.HOH_SIH_KBN = 1 ");
			}

			// 支払方法コード IN指定
			if (!condition.getCodeList().isEmpty()) {
				sql.add("   AND HOH_HOH_CODE IN ?", condition.getCodeList());
			}

			// 支払方法内部コード IN指定
			if (!condition.getNotPaymentKindList().isEmpty()) {
				sql.add("   AND HOH_NAI_CODE NOT IN ?", condition.getNotPaymentKinds());
			}

			// 支払方法内部コード IN指定
			if (!condition.getPaymentKindList().isEmpty()) {
				sql.add("   AND HOH_NAI_CODE IN ?", condition.getPaymentKinds());
			}

			// 有効期間
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				sql.add("   AND hoh.STR_DATE <= ?", condition.getValidTerm());
				sql.add("   AND hoh.END_DATE >= ?", condition.getValidTerm());
			}

			// 最終更新日時
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (hoh.HOH_INP_DATE > ?", condition.getLastUpdateDate());
				sql.adt("    OR hoh.UPD_DATE > ?", condition.getLastUpdateDate());
				sql.add(")");
			}

			sql.add(" ORDER BY hoh.HOH_HOH_CODE ");

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
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(PaymentMethodSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   AP_HOH_MST hoh ");
			sql.add(" WHERE  hoh.KAI_CODE = ? ", condition.getCompanyCode()); // 会社コード

			// 最終更新日時
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (hoh.HOH_INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR hoh.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR hoh.HOH_INP_DATE IS NULL AND hoh.UPD_DATE IS NULL) ");
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

	/**
	 * 支払方法を登録する。
	 * 
	 * @param bean 支払方法
	 * @throws TException
	 */
	public void entry(PaymentMethod bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("  INSERT INTO  AP_HOH_MST (");
			sql.add("  KAI_CODE ");
			sql.add(" ,HOH_HOH_CODE ");
			sql.add(" ,HOH_HOH_NAME ");
			sql.add(" ,HOH_HOH_NAME_K ");
			sql.add(" ,HOH_KMK_CODE ");
			sql.add(" ,HOH_HKM_CODE ");
			sql.add(" ,HOH_UKM_CODE ");
			sql.add(" ,HOH_DEP_CODE ");
			sql.add(" ,HOH_SIH_KBN ");
			sql.add(" ,HOH_NAI_CODE ");
			sql.add(" ,STR_DATE ");
			sql.add(" ,END_DATE ");
			sql.add(" ,HOH_INP_DATE");
			sql.add(" ,PRG_ID ");
			sql.add(" ,USR_ID ");

			sql.add(") VALUES (");
			sql.add("     ?", bean.getCompanyCode());
			sql.add("    ,?", bean.getCode());
			sql.add("    ,?", bean.getName());
			sql.add("    ,?", bean.getNamek());
			sql.add("    ,?", bean.getItemCode());
			sql.add("    ,?", bean.getSubItemCode());
			sql.add("    ,?", bean.getDetailItemCode());
			sql.add("    ,?", bean.getDepartmentCode());
			sql.add("    ,?", bean.getPaymentDivision());
			sql.add("    ,?", bean.getPaymentKind().value);
			sql.adt("    ,?", bean.getDateFrom());
			sql.adt("    ,?", bean.getDateTo());
			sql.adt("    ,?", getNow());
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
	 * 支払方法を修正する。
	 * 
	 * @param bean 支払方法
	 * @throws TException
	 */
	public void modify(PaymentMethod bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("");
			sql.add("UPDATE");
			sql.add("    AP_HOH_MST");
			sql.add("SET");
			sql.add("     HOH_HOH_CODE = ?", bean.getCode());
			sql.add("    ,HOH_HOH_NAME = ?", bean.getName());
			sql.add("    ,HOH_HOH_NAME_K = ?", bean.getNamek());
			sql.add("    ,HOH_KMK_CODE = ?", bean.getItemCode());
			sql.add("    ,HOH_HKM_CODE = ?", bean.getSubItemCode());
			sql.add("    ,HOH_UKM_CODE = ?", bean.getDetailItemCode());
			sql.add("    ,HOH_DEP_CODE = ?", bean.getDepartmentCode());
			sql.add("    ,HOH_SIH_KBN = ?", bean.getPaymentDivision());
			sql.add("    ,HOH_NAI_CODE = ?", bean.getPaymentKind().value);
			sql.adt("    ,STR_DATE = ?", bean.getDateFrom());
			sql.adt("    ,END_DATE = ?", bean.getDateTo());
			sql.adt("    ,UPD_DATE = ?", getNow());
			sql.add("    ,PRG_ID = ?", getProgramCode());
			sql.add("    ,USR_ID = ?", getUserCode());
			sql.add("WHERE");
			sql.add("    KAI_CODE = ?", getCompanyCode());
			sql.add("AND");
			sql.add("    HOH_HOH_CODE = ?", bean.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 支払方法を削除する。
	 * 
	 * @param bean 支払方法
	 * @throws TException
	 */
	public void delete(PaymentMethod bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("");
			sql.add("DELETE FROM");
			sql.add("    AP_HOH_MST ");
			sql.add("WHERE");
			sql.add("    KAI_CODE = ? ", bean.getCompanyCode());
			sql.add("AND");
			sql.add("    HOH_HOH_CODE = ? ", bean.getCode());

			DBUtil.execute(conn, sql);

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
	 * @return エンティティ
	 * @throws Exception
	 */
	protected PaymentMethod mapping(ResultSet rs) throws Exception {

		PaymentMethod bean = new PaymentMethod();

		// 会社コード
		bean.setCompanyCode(rs.getString("KAI_CODE"));

		// コード
		bean.setCode(rs.getString("HOH_HOH_CODE"));

		// 名称
		bean.setName(rs.getString("HOH_HOH_NAME"));

		// 検索名称
		bean.setNamek(rs.getString("HOH_HOH_NAME_K"));

		// 科目コード
		bean.setItemCode(rs.getString("HOH_KMK_CODE"));

		// 補助科目コード
		bean.setSubItemCode(rs.getString("HOH_HKM_CODE"));

		// 内訳科目コード
		bean.setDetailItemCode(rs.getString("HOH_UKM_CODE"));

		// 部門コード
		bean.setDepartmentCode(rs.getString("HOH_DEP_CODE"));

		// 支払対象区分
		bean.setPaymentDivision(rs.getInt("HOH_SIH_KBN"));

		// 支払内部コード
		bean.setPaymentKind(PaymentKind.getPaymentKind(rs.getString("HOH_NAI_CODE")));

		// 有効期間開始
		bean.setDateFrom(rs.getDate("STR_DATE"));

		// 有効期間終了
		bean.setDateTo(rs.getDate("END_DATE"));

		return bean;

	}

	/**
	 * エクセル取得
	 * 
	 * @param condition 検索条件
	 * @return byte 検索結果
	 * @throws TException
	 */
	public byte[] getExcel(PaymentMethodSearchCondition condition) throws TException {

		try {

			List<PaymentMethod> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			PaymentMethodExcel exl = new PaymentMethodExcel(getUser().getLanguage());

			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * SQL creator
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
