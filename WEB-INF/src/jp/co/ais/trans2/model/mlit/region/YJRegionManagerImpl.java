package jp.co.ais.trans2.model.mlit.region;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * 輸送実績国・地域マネージャ実装クラス
 */
public class YJRegionManagerImpl extends TModel implements YJRegionManager {

	/**
	 * 輸送実績国を取得する
	 * 
	 * @return 輸送実績国リスト
	 * @throws TException
	 */
	public List<YJRegion> getRegion(YJRegionSearchCondition condition) throws TException {

		List<YJRegion> list = new ArrayList<YJRegion>();

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT ");
			sql.add("   region.KAI_CODE ");
			sql.add("  ,region.REGION_CODE ");
			sql.add("  ,region.REGION_NAME ");
			sql.add("  ,NULL COUNTRY_CODE ");
			sql.add("  ,NULL COUNTRY_NAME ");
			sql.add("  ,region.REMARK ");
			sql.add("  ,NULL COUNTRY_REMARK ");
			sql.add("  ,region.INP_DATE ");
			sql.add("  ,region.UPD_DATE ");
			sql.add("  ,region.PRG_ID ");
			sql.add("  ,region.USR_ID ");
			sql.add("  ,NULL COUNTRY_INP_DATE ");
			sql.add("  ,NULL COUNTRY_UPD_DATE ");
			sql.add("  ,NULL COUNTRY_PRG_ID ");
			sql.add("  ,NULL COUNTRY_PRG_ID ");
			sql.add(" FROM YJ_REGION_MST region ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(createSelectWhereSql(condition));
			sql.add(" ORDER BY ");
			sql.add("   region.KAI_CODE ");
			sql.add("  ,region.REGION_CODE ");

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
	 * 輸送実績国マスタの登録
	 * 
	 * @param bean 輸送実績国マスタ
	 * @throws TException
	 */
	public void entryRegion(YJRegion bean) throws TException {

		SQLCreator sql = new SQLCreator();

		sql.add(" INSERT INTO YJ_REGION_MST ( ");
		sql.add("   KAI_CODE ");
		sql.add("  ,REGION_CODE ");
		sql.add("  ,REGION_NAME ");
		sql.add("  ,REMARK ");
		sql.add("  ,INP_DATE ");
		sql.add("  ,UPD_DATE ");
		sql.add("  ,PRG_ID ");
		sql.add("  ,USR_ID ");
		sql.add(" ) VALUES ( ");
		sql.add("        ? ", bean.getKAI_CODE()); // 会社コード
		sql.add("       ,? ", bean.getREGION_CODE()); // コード
		sql.add("       ,? ", bean.getREGION_NAME()); // 名称
		sql.add("       ,? ", bean.getREGION_REMARK()); // Remark
		sql.addYMDHMS(" ,? ", bean.getINP_DATE()); // 登録日付
		sql.addYMDHMS(" ,? ", bean.getUPD_DATE()); // 更新日付
		sql.add("       ,? ", bean.getPRG_ID()); // プログラムID
		sql.add("       ,? ", bean.getUSR_ID()); // ユーザーID
		sql.add(" ) ");

		DBUtil.execute(sql);

	}

	/**
	 * 輸送実績国マスタの削除
	 * 
	 * @param bean 輸送実績国マスタ
	 * @throws TException
	 */
	public void deleteRegion(YJRegion bean) throws TException {

		SQLCreator sql = new SQLCreator();

		sql.add("DELETE FROM YJ_REGION_MST ");
		sql.add(" WHERE KAI_CODE  = ? ", bean.getKAI_CODE());
		sql.add("   AND REGION_CODE = ? ", bean.getREGION_CODE());

		DBUtil.execute(sql);

	}

	/**
	 * 輸送実績国マスタの登録
	 * 
	 * @param bean 輸送実績国マスタ
	 * @return 登録時間など設定後のエンティティ
	 * @throws TException
	 */
	public YJRegion entryRegionMaster(YJRegion bean) throws TException {

		// 削除
		deleteRegion(bean);

		// 情報設定
		if (Util.isNullOrEmpty(bean.getINP_DATE())) {
			bean.setINP_DATE(getNow());
		} else {
			bean.setUPD_DATE(getNow());
		}
		bean.setPRG_ID(getProgramCode());
		bean.setUSR_ID(getUserCode());

		// 登録
		entryRegion(bean);

		return bean;
	}

	/**
	 * エクセルを返す
	 * 
	 * @param condition
	 * @return エクセル
	 * @throws TException
	 */
	public byte[] getExcelRegion(YJRegionSearchCondition condition) throws TException {

		try {
			// データを抽出
			List<YJRegion> list = getRegion(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			MLITRegionExcel excel = new MLITRegionExcel(getUser().getLanguage(), getCompany());
			return excel.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * 輸送実績地域を取得する
	 * 
	 * @return 輸送実績地域リスト
	 * @throws TException
	 */
	public List<YJRegion> getCountry(YJRegionSearchCondition condition) throws TException {

		List<YJRegion> list = new ArrayList<YJRegion>();

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT ");
			sql.add("   region.KAI_CODE ");
			sql.add("  ,region.REGION_CODE ");
			sql.add("  ,region.REGION_NAME ");
			sql.add("  ,cou.COUNTRY_CODE ");
			sql.add("  ,cou.COUNTRY_NAME ");
			sql.add("  ,region.REMARK ");
			sql.add("  ,cou.REMARK  COUNTRY_REMARK ");
			sql.add("  ,region.INP_DATE ");
			sql.add("  ,region.UPD_DATE ");
			sql.add("  ,region.PRG_ID ");
			sql.add("  ,region.USR_ID ");
			sql.add("  ,cou.INP_DATE COUNTRY_INP_DATE ");
			sql.add("  ,cou.UPD_DATE COUNTRY_UPD_DATE ");
			sql.add("  ,cou.PRG_ID   COUNTRY_PRG_ID ");
			sql.add("  ,cou.USR_ID   COUNTRY_PRG_ID ");
			sql.add("FROM YJ_REGION_MST region ");
			sql.add("INNER JOIN YJ_COUNTRY_MST cou ON region.KAI_CODE    = cou.KAI_CODE ");
			sql.add("                             AND region.REGION_CODE = cou.REGION_CODE ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(createSelectWhereSql(condition));
			sql.add(" ORDER BY ");
			sql.add("   region.KAI_CODE ");
			sql.add("  ,region.REGION_CODE ");
			sql.add("  ,cou.COUNTRY_CODE ");

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
	 * 輸送実績地域マスタの登録
	 * 
	 * @param bean 輸送実績地域マスタ
	 * @throws TException
	 */
	public void entryCountry(YJRegion bean) throws TException {

		SQLCreator sql = new SQLCreator();

		sql.add(" INSERT INTO YJ_COUNTRY_MST ( ");
		sql.add("   KAI_CODE ");
		sql.add("  ,REGION_CODE ");
		sql.add("  ,COUNTRY_CODE ");
		sql.add("  ,COUNTRY_NAME ");
		sql.add("  ,REMARK ");
		sql.add("  ,INP_DATE ");
		sql.add("  ,UPD_DATE ");
		sql.add("  ,PRG_ID ");
		sql.add("  ,USR_ID ");
		sql.add(" ) VALUES ( ");
		sql.add("        ? ", bean.getKAI_CODE()); // 会社コード
		sql.add("       ,? ", bean.getREGION_CODE()); // 国コード
		sql.add("       ,? ", bean.getCOUNTRY_CODE()); // 地域コード
		sql.add("       ,? ", bean.getCOUNTRY_NAME()); // 地域名称
		sql.add("       ,? ", bean.getCOUNTRY_REMARK()); // Remark
		sql.addYMDHMS(" ,? ", bean.getINP_DATE()); // 登録日付
		sql.addYMDHMS(" ,? ", bean.getUPD_DATE()); // 更新日付
		sql.add("       ,? ", bean.getPRG_ID()); // プログラムID
		sql.add("       ,? ", bean.getUSR_ID()); // ユーザーID
		sql.add(" ) ");

		DBUtil.execute(sql);

	}

	/**
	 * 輸送実績地域マスタの削除
	 * 
	 * @param bean 輸送実績地域マスタ
	 * @throws TException
	 */
	public void deleteCountry(YJRegion bean) throws TException {

		SQLCreator sql = new SQLCreator();

		sql.add("DELETE FROM YJ_COUNTRY_MST ");
		sql.add(" WHERE KAI_CODE     = ? ", bean.getKAI_CODE());
		sql.add("   AND REGION_CODE  = ? ", bean.getREGION_CODE());
		sql.add("   AND COUNTRY_CODE = ? ", bean.getCOUNTRY_CODE());

		DBUtil.execute(sql);

	}

	/**
	 * 輸送実績地域マスタの登録
	 * 
	 * @param bean 輸送実績地域マスタ
	 * @return 登録時間など設定後のエンティティ
	 * @throws TException
	 */
	public YJRegion entryCountryMaster(YJRegion bean) throws TException {

		// 削除
		deleteCountry(bean);

		// 情報設定
		if (Util.isNullOrEmpty(bean.getINP_DATE())) {
			bean.setINP_DATE(getNow());
		} else {
			bean.setUPD_DATE(getNow());
		}
		bean.setPRG_ID(getProgramCode());
		bean.setUSR_ID(getUserCode());

		// 登録
		entryCountry(bean);

		return bean;
	}

	/**
	 * エクセルを返す
	 * 
	 * @param condition
	 * @return エクセル
	 * @throws TException
	 */
	public byte[] getExcelCountry(YJRegionSearchCondition condition) throws TException {

		try {
			// データを抽出
			List<YJRegion> list = getCountry(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			MLITCountryExcel excel = new MLITCountryExcel(getUser().getLanguage(), getCompany());
			return excel.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * 検索文の作成
	 * 
	 * @param condition
	 * @return 検索文
	 */
	protected String createSelectWhereSql(YJRegionSearchCondition condition) {
		SQLCreator sql = new SQLCreator();

		// 会社コード
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add(" AND region.KAI_CODE  = ?", condition.getCompanyCode());
		}

		// 国コード
		if (!Util.isNullOrEmpty(condition.getRegionCode())) {
			sql.add(" AND region.REGION_CODE  = ? ", condition.getRegionCode());
		}

		// 開始国コード
		if (!Util.isNullOrEmpty(condition.getRegionCodeFrom())) {
			sql.add(" AND region.REGION_CODE >= ? ", condition.getRegionCodeFrom());
		}

		// 終了国コード
		if (!Util.isNullOrEmpty(condition.getRegionCodeTo())) {
			sql.add(" AND region.REGION_CODE <= ? ", condition.getRegionCodeTo());
		}

		// 国コード前方一致
		if (!Util.isNullOrEmpty(condition.getRegionCodeLike())) {
			sql.addLikeFront(" AND region.REGION_CODE ? ", condition.getRegionCodeLike());
		}

		// 国名称あいまい
		if (!Util.isNullOrEmpty(condition.getRegionNameLike())) {
			sql.addNLikeAmbi(" AND region.REGION_NAME ? ", condition.getRegionNameLike());
		}

		// 地域コード
		if (!Util.isNullOrEmpty(condition.getCountryCode())) {
			sql.add(" AND cou.COUNTRY_CODE  = ? ", condition.getCountryCode());
		}

		// 開始地域コード
		if (!Util.isNullOrEmpty(condition.getCountryCodeFrom())) {
			sql.add(" AND cou.COUNTRY_CODE >= ? ", condition.getCountryCodeFrom());
		}

		// 終了地域コード
		if (!Util.isNullOrEmpty(condition.getCountryCodeTo())) {
			sql.add(" AND cou.COUNTRY_CODE <= ? ", condition.getCountryCodeTo());
		}

		// 地域コード前方一致
		if (!Util.isNullOrEmpty(condition.getCountryCodeLike())) {
			sql.addLikeFront(" AND cou.COUNTRY_CODE ? ", condition.getCountryCodeLike());
		}

		// 地域名称あいまい
		if (!Util.isNullOrEmpty(condition.getCountryNameLike())) {
			sql.addNLikeAmbi(" AND cou.COUNTRY_NAME ? ", condition.getCountryNameLike());
		}
		return sql.toSQL();
	}

	/**
	 * マッピング処理
	 * 
	 * @param rs
	 * @return 輸送実績国
	 * @throws SQLException
	 */
	protected YJRegion mapping(ResultSet rs) throws SQLException {
		YJRegion item = new YJRegion();

		item.setKAI_CODE(rs.getString("KAI_CODE"));
		item.setREGION_CODE(rs.getString("REGION_CODE"));
		item.setREGION_NAME(rs.getString("REGION_NAME"));
		item.setREGION_REMARK(rs.getString("REMARK"));
		item.setCOUNTRY_CODE(rs.getString("COUNTRY_CODE"));
		item.setCOUNTRY_NAME(rs.getString("COUNTRY_NAME"));
		item.setCOUNTRY_REMARK(rs.getString("COUNTRY_REMARK"));
		item.setINP_DATE(rs.getTimestamp("INP_DATE"));
		item.setUPD_DATE(rs.getTimestamp("UPD_DATE"));
		item.setPRG_ID(rs.getString("PRG_ID"));
		item.setUSR_ID(rs.getString("USR_ID"));

		return item;
	}
}
