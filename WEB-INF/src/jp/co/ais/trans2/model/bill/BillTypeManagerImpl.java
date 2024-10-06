package jp.co.ais.trans2.model.bill;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * 請求区分マスタマネージャの実装クラスです。
 * 
 * @author AIS
 */
public class BillTypeManagerImpl extends TModel implements BillTypeManager {

	/**
	 * 指定条件に該当する請求区分マスタ情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する請求区分マスタ情報
	 * @throws TException
	 */
	public List<BillType> get(BillTypeSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<BillType> list = new ArrayList<BillType>();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT sei.KAI_CODE, ");
			sql.add("        sei.SEI_KBN, ");
			sql.add("        sei.SEI_NAME, ");
			sql.add("        sei.SEI_NAME_K, ");
			sql.add("        sei.SEI_FORM_DIR, ");
			sql.add("        sei.SEI_DTL_CNT, ");
			sql.add("        sei.STR_DATE, ");
			sql.add("        sei.END_DATE, ");
			sql.add("        sei.INP_DATE, ");
			sql.add("        sei.UPD_DATE, ");
			sql.add("        sei.PRG_ID, ");
			sql.add("        sei.USR_ID ");
			sql.add("   FROM AR_SEI_MST sei");
			sql.add("  WHERE 1 = 1 ");
			sql.add(createSelectWhereSql(condition));

			sql.add(" ORDER BY sei.SEI_KBN ");

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
	 * 検索文の作成
	 * 
	 * @param condition
	 * @return 検索文
	 */
	protected String createSelectWhereSql(BillTypeSearchCondition condition) {

		SQLCreator sql = new SQLCreator();

		// 会社コード
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add("   AND sei.KAI_CODE = ?", condition.getCompanyCode());
		}

		// コード
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql.add("   AND sei.SEI_KBN = ? ", condition.getCode());
		}

		// 開始コード
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql.add("   AND sei.SEI_KBN >= ? ", condition.getCodeFrom());
		}

		// 終了コード
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql.add("   AND sei.SEI_KBN <= ? ", condition.getCodeTo());
		}

		// コード前方一致
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql.addLikeFront("   AND sei.SEI_KBN ? ", condition.getCodeLike());
		}

		// 名称あいまい
		if (!Util.isNullOrEmpty(condition.getNameLike())) {
			sql.addNLikeAmbi("   AND sei.SEI_NAME ? ", condition.getNameLike());
		}

		// 検索名称あいまい
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql.addNLikeAmbi("   AND sei.SEI_NAME_K ? ", condition.getNamekLike());
		}

		// 有効期間
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql.add("   AND sei.STR_DATE <= ? ", condition.getValidTerm());
			sql.add("   AND sei.END_DATE >= ? ", condition.getValidTerm());
		}

		return sql.toSQL();
	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected BillType mapping(ResultSet rs) throws Exception {

		BillType bean = new BillType();

		bean.setCompanyCode(rs.getString("KAI_CODE")); // 会社コード
		bean.setCode(rs.getString("SEI_KBN")); // 請求区分
		bean.setName(rs.getString("SEI_NAME")); // 名称
		bean.setNamek(rs.getString("SEI_NAME_K")); // 検索名称
		bean.setFormat(rs.getString("SEI_FORM_DIR")); // 請求書フォーマット
		bean.setDetailCount(rs.getInt("SEI_DTL_CNT")); // 明細件数
		bean.setDateFrom(rs.getDate("STR_DATE")); // 有効期間開始
		bean.setDateTo(rs.getDate("END_DATE")); // 有効期間終了

		bean.setInpDate(rs.getTimestamp("INP_DATE"));
		bean.setUpdDate(rs.getTimestamp("UPD_DATE"));
		bean.setPrgId(rs.getString("PRG_ID"));
		bean.setUsrId(rs.getString("USR_ID"));

		return bean;

	}

	/**
	 * 請求区分マスタを登録する。
	 * 
	 * @param bean 請求区分マスタ
	 * @throws TException
	 */
	public void entry(BillType bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("INSERT INTO AR_SEI_MST (");
			sql.add("  KAI_CODE, ");
			sql.add("  SEI_KBN, ");
			sql.add("  SEI_NAME, ");
			sql.add("  SEI_NAME_K, ");
			sql.add("  SEI_FORM_DIR, ");
			sql.add("  SEI_DTL_CNT, ");
			sql.add("  STR_DATE, ");
			sql.add("  END_DATE, ");
			sql.add("  INP_DATE, ");
			sql.add("  UPD_DATE, ");
			sql.add("  PRG_ID, ");
			sql.add("  USR_ID ");
			sql.add(") VALUES (");
			sql.add("  ?, ", bean.getCompanyCode()); // 会社コード
			sql.add("  ?, ", bean.getCode()); // 請求区分
			sql.add("  ?, ", bean.getName()); // 請求名称
			sql.add("  ?, ", bean.getNamek()); // 検索名称
			sql.add("  ?, ", bean.getFormat()); // 請求書フォーマット
			sql.add("  ?, ", bean.getDetailCount()); // 明細件数
			sql.add("  ?, ", bean.getDateFrom()); // 開始年月日
			sql.add("  ?, ", bean.getDateTo()); // 終了年月日
			sql.addYMDHMS("  ?, ", bean.getInpDate()); // 登録日付
			sql.addYMDHMS("  ?, ", bean.getUpdDate()); // 更新日付
			sql.add("  ?, ", bean.getPrgId()); // プログラムＩＤ
			sql.add("  ?  ", bean.getUsrId()); // ユーザーＩＤ
			sql.add(")");

			DBUtil.execute(sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 請求区分マスタを削除する。
	 * 
	 * @param bean 請求区分マスタ
	 * @throws TException
	 */
	public void delete(BillType bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("DELETE FROM AR_SEI_MST");
			sql.add(" WHERE KAI_CODE = ? ", bean.getCompanyCode());
			sql.add("   AND SEI_KBN = ? ", bean.getCode());

			DBUtil.execute(sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 請求区分マスタを登録する。
	 * 
	 * @param bean 請求区分マスタ
	 * @return 登録時間など設定後のエンティティ
	 * @throws TException
	 */
	public BillType entryMaster(BillType bean) throws TException {

		// 削除
		delete(bean);

		// 情報設定
		if (Util.isNullOrEmpty(bean.getInpDate())) {
			bean.setInpDate(getNow());
		} else {
			bean.setUpdDate(getNow());
		}
		bean.setPrgId(getProgramCode());
		bean.setUsrId(getUserCode());

		// 登録
		entry(bean);

		return bean;
	}

	/**
	 * エクセルを返す
	 * 
	 * @param condition
	 * @return エクセル
	 * @throws TException
	 */
	public byte[] getExcel(BillTypeSearchCondition condition) throws TException {

		try {

			// 会社データを抽出
			List<BillType> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			BillTypeExcel exl = new BillTypeExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}

	}
}