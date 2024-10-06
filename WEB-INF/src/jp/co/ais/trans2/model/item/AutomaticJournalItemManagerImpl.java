package jp.co.ais.trans2.model.item;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * 自動仕訳科目 マネージャの実装クラスです。
 * 
 * @author AIS
 */
public class AutomaticJournalItemManagerImpl extends TModel implements AutomaticJournalItemManager {

	public List<AutomaticJournalItem> get(AutomaticJournalItemSearchCondition condition) throws TException {

		List<AutomaticJournalItem> list = new ArrayList<AutomaticJournalItem>();

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT ");
			sql.add("     swk.KAI_CODE, ");
			sql.add("     swk.KMK_CNT, ");
			sql.add("     swk.KMK_CNT_NAME, ");
			sql.add("     swk.DEP_CODE, ");
			sql.add("     dep.DEP_NAME, ");
			sql.add("     dep.DEP_NAME_S, ");
			sql.add("     swk.KMK_CODE, ");
			sql.add("     kmk.KMK_NAME, ");
			sql.add("     kmk.KMK_NAME_S, ");
			sql.add("     kmk.DC_KBN, ");
			sql.add("     swk.HKM_CODE, ");
			sql.add("     hkm.HKM_NAME, ");
			sql.add("     hkm.HKM_NAME_S, ");
			sql.add("     swk.UKM_CODE, ");
			sql.add("     ukm.UKM_NAME, ");
			sql.add("     ukm.UKM_NAME_S ");

			sql.add(" FROM SWK_KMK_MST swk ");

			sql.add("      LEFT OUTER JOIN BMN_MST dep ");
			sql.add("      ON  swk.KAI_CODE = dep.KAI_CODE ");
			sql.add("      AND swk.DEP_CODE = dep.DEP_CODE ");

			sql.add("      LEFT OUTER JOIN KMK_MST kmk ");
			sql.add("      ON  swk.KAI_CODE = kmk.KAI_CODE ");
			sql.add("      AND swk.KMK_CODE = kmk.KMK_CODE ");

			sql.add("      LEFT OUTER JOIN HKM_MST hkm ");
			sql.add("      ON  swk.KAI_CODE = hkm.KAI_CODE ");
			sql.add("      AND swk.KMK_CODE = hkm.KMK_CODE ");
			sql.add("      AND swk.HKM_CODE = hkm.HKM_CODE ");

			sql.add("      LEFT OUTER JOIN UKM_MST ukm ");
			sql.add("      ON  swk.KAI_CODE = ukm.KAI_CODE ");
			sql.add("      AND swk.KMK_CODE = ukm.KMK_CODE ");
			sql.add("      AND swk.HKM_CODE = ukm.HKM_CODE ");
			sql.add("      AND swk.UKM_CODE = ukm.UKM_CODE ");

			sql.add(" WHERE swk.KAI_CODE = ? ", getCompanyCode());

			// 科目制御区分
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add(" AND  swk.KMK_CNT = ? ", DecimalUtil.toInt(condition.getCode()));
			}

			// 開始コード
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add("  AND  swk.KMK_CNT >= ?", condition.getCodeFrom());
			}

			// 終了コード
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add("  AND  swk.KMK_CNT <= ?", condition.getCodeTo());
			}

			// コード前方一致
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.addLikeFront("  AND  swk.KMK_CNT ?", condition.getCodeLike());
			}

			sql.add(" ORDER BY swk.KMK_CNT ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}
			DBUtil.close(rs);
			DBUtil.close(statement);
			for (AutomaticJournalItem bean : list) {
				ItemManager itemMng = (ItemManager) getComponent(ItemManager.class);
				Item item = itemMng.getItem(getCompanyCode(), bean.getKMK_CODE(), bean.getHKM_CODE(),
					bean.getUKM_CODE());
				bean.setItem(item);
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return list;

	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return bean
	 * @throws Exception
	 */
	protected AutomaticJournalItem mapping(ResultSet rs) throws Exception {

		AutomaticJournalItem bean = new AutomaticJournalItem();

		bean.setKAI_CODE(rs.getString("KAI_CODE"));
		bean.setKMK_CNT(rs.getString("KMK_CNT"));
		bean.setKMK_CNT_NAME(rs.getString("KMK_CNT_NAME"));
		bean.setDEP_CODE(rs.getString("DEP_CODE"));
		bean.setDEP_NAME(rs.getString("DEP_NAME"));
		bean.setDEP_NAMES(rs.getString("DEP_NAME_S"));
		bean.setKMK_CODE(rs.getString("KMK_CODE"));
		bean.setKMK_NAME(rs.getString("KMK_NAME"));
		bean.setKMK_NAMES(rs.getString("KMK_NAME_S"));
		bean.setHKM_CODE(rs.getString("HKM_CODE"));
		bean.setHKM_NAME(rs.getString("HKM_NAME"));
		bean.setHKM_NAMES(rs.getString("HKM_NAME_S"));
		bean.setUKM_CODE(rs.getString("UKM_CODE"));
		bean.setUKM_NAME(rs.getString("UKM_NAME"));
		bean.setUKM_NAMES(rs.getString("UKM_NAME_S"));
		bean.setDC_KBN(rs.getString("DC_KBN"));
		return bean;

	}

	// 科目制御区分登録
	public void entry(AutomaticJournalItem bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator s = new SQLCreator();
			s.add(" INSERT INTO SWK_KMK_MST ( ");
			s.add("     KAI_CODE, ");
			s.add("     KMK_CNT, ");
			s.add("     KMK_CNT_NAME, ");
			s.add("     KMK_CODE, ");
			s.add("     HKM_CODE, ");
			s.add("     UKM_CODE, ");
			s.add("     DEP_CODE ");
			s.add(" ) VALUES ( ");
			s.add("  ?,", bean.getKAI_CODE());
			s.add("  ?,", bean.getKMK_CNT());
			s.add("  ?,", bean.getKMK_CNT_NAME());
			s.add("  ?,", bean.getKMK_CODE());
			s.add("  ?,", bean.getHKM_CODE());
			s.add("  ?,", bean.getUKM_CODE());
			s.add("  ?", bean.getDEP_CODE());
			s.add(")");

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	public void modify(AutomaticJournalItem bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator sql = new SQLCreator();
			sql.add(" UPDATE SWK_KMK_MST SET ");
			sql.add("     KMK_CNT_NAME = ? , ", bean.getKMK_CNT_NAME());
			sql.add("     DEP_CODE     = ? , ", bean.getDEP_CODE());
			sql.add("     KMK_CODE     = ? , ", bean.getKMK_CODE());
			sql.add("     HKM_CODE     = ? , ", bean.getHKM_CODE());
			sql.add("     UKM_CODE     = ? ", bean.getUKM_CODE());
			sql.add(" WHERE KAI_CODE = ? ", getCompanyCode());
			sql.add(" AND   KMK_CNT  = ? ", bean.getKMK_CNT());

			DBUtil.execute(sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void delete(AutomaticJournalItem bean) throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator s = new SQLCreator();

			s.add(" DELETE FROM SWK_KMK_MST ");
			s.add(" WHERE KAI_CODE = ?", bean.getKAI_CODE());
			s.add(" AND   KMK_CNT  = ?", bean.getKMK_CNT());

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 
	 */
	public int check(AutomaticJournalItem bean) throws TException {

		Connection conn = DBUtil.getConnection();
		int dcKbn = 9;

		try {
			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT ");
			sql.add("     DC_KBN ");

			sql.add(" FROM KMK_MST ");

			sql.add(" WHERE KAI_CODE = ? ", getCompanyCode());

			// 科目制御区分
			if (!Util.isNullOrEmpty(bean.getKMK_CODE())) {
				sql.add(" AND  KMK_CODE = ? ", bean.getKMK_CODE());
			}

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				dcKbn = rs.getInt("DC_KBN");
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return dcKbn;

	}

	/**
	 * 科目制御区分一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の科目制御区分一覧
	 * @throws TException
	 */
	public byte[] getExcel(AutomaticJournalItemSearchCondition condition) throws TException {

		try {

			// データを抽出
			List<AutomaticJournalItem> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			AutomaticJournalItemExcel exl = new AutomaticJournalItemExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

}
