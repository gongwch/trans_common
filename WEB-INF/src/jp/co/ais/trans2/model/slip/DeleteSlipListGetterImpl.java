package jp.co.ais.trans2.model.slip;

import java.sql.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * 削除伝票リスト実装
 * 
 * @author AIS
 */
public class DeleteSlipListGetterImpl extends TModel implements DeleteSlipListGetter {

	/**
	 * 削除伝票リストを返す
	 * 
	 * @param condition 検索条件
	 * @return 削除伝票リスト
	 * @throws TException
	 */
	public DeleteSlipListBook get(SlipCondition condition) throws TException {

		// 帳票データにマッピング
		DeleteSlipListBook book = getBook(condition);
		return book;

	}

	/**
	 * データ抽出
	 * 
	 * @param condition
	 * @return DeleteSlipListBook
	 * @throws TException
	 */
	protected DeleteSlipListBook getBook(SlipCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();
		DeleteSlipListBook book = null;
		try {
			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT ");
			sql.add("  del_dtl.DEL_DEN_NO ");
			sql.add(" , del_dtl.INP_DATE ");
			sql.add(" , del_dtl.USR_ID ");
			sql.add(" , usr_mst.USR_NAME ");
			sql.add(" FROM DEL_DTL del_dtl");
			sql.add("    LEFT JOIN USR_MST usr_mst");
			sql.add("      ON   usr_mst.KAI_CODE = del_dtl.KAI_CODE");
			sql.add("      AND  usr_mst.USR_CODE = del_dtl.USR_ID");
			sql.add(" WHERE 1 = 1 ");

			// 会社コード
			if (condition.getCompany() != null) {
				sql.add(" AND   del_dtl.KAI_CODE = ? ", condition.getCompany().getCode());
			}

			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add(" AND   del_dtl.KAI_CODE = ? ", condition.getCompanyCode());
			}

			// 伝票日付From
			if (condition.getSlipDateFrom() != null) {
				sql.addYMDHMS(" AND   del_dtl.DEL_DEN_DATE >= ? ", condition.getSlipDateFrom());
			}

			// 伝票日付To
			if (condition.getSlipDateTo() != null) {
				sql.addYMDHMS(" AND   del_dtl.DEL_DEN_DATE <= ? ", condition.getSlipDateTo());
			}

			// 更新日付
			if (condition.getUpdateDateFrom() != null) {
				sql.addYMDHMS(" AND  ( del_dtl.INP_DATE >= ? ", condition.getUpdateDateFrom());
				sql.add("              OR 	 ");
				sql.addYMDHMS("        del_dtl.UPD_DATE >= ? ", condition.getUpdateDateFrom());
				sql.add("             ) ");
			} else {
				// 登録日付
				if (condition.getEntryDateFrom() != null) {
					sql.addYMDHMS(" AND   del_dtl.INP_DATE >= ? ", condition.getEntryDateFrom());
				}
			}

			sql.add(" ORDER BY ");
			sql.add(" del_dtl.DEL_DEN_NO ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			// 製本
			book = mapping(condition, rs);

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return book;

	}

	/**
	 * 製本
	 * 
	 * @param condition
	 * @param rs
	 * @return DeleteSlipListBook
	 * @throws Exception
	 */
	protected DeleteSlipListBook mapping(SlipCondition condition, ResultSet rs) throws Exception {

		DeleteSlipListBook book = new DeleteSlipListBook();
		book.setCondition(condition);

		book.setMaxRowCount(100);

		book.addHeader(new DeleteSlipListHeader());

		boolean isExists = false;
		while (rs.next()) {
			book.addDetail(getDetail(rs));
			isExists = true;
		}

		if (!isExists) {
			return null;
		}

		return book;

	}

	/**
	 * 明細を返す
	 * 
	 * @param rs
	 * @return 削除伝票リスト
	 * @throws Exception
	 */
	protected DeleteSlipListDetail getDetail(ResultSet rs) throws Exception {

		DeleteSlipListDetail detail = new DeleteSlipListDetail();

		detail.setDelSlipNo(rs.getString("DEL_DEN_NO"));
		detail.setDelDate(rs.getDate("INP_DATE"));
		detail.setUsrID(rs.getString("USR_ID"));
		detail.setUsrName(rs.getString("USR_NAME"));

		return detail;

	}

}
