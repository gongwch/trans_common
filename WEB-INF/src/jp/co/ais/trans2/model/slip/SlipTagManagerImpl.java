package jp.co.ais.trans2.model.slip;

import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * 伝票付箋の実装クラスです。
 * 
 * @author AIS
 */
public class SlipTagManagerImpl extends TModel implements SlipTagManager {

	/**
	 * 特定の伝票の付箋情報を設定する
	 * 
	 * @param slip 伝票
	 * @return 付箋情報<ファイル名, バイナリ>
	 */
	public List<SWK_TAG> get(Slip slip) throws TException {
		return get(slip.getCompanyCode(), slip.getSlipNo());
	}

	/**
	 * 特定の伝票の付箋情報を設定する
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @return 付箋情報<ファイル名, バイナリ>
	 */
	public List<SWK_TAG> get(String companyCode, String slipNo) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			return get(conn, companyCode, slipNo);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 特定の伝票の付箋情報を設定する
	 * 
	 * @param conn
	 * @param companyCode
	 * @param slipNo
	 * @return 付箋情報<ファイル名, バイナリ>
	 * @throws Exception
	 */
	protected List<SWK_TAG> get(Connection conn, String companyCode, String slipNo) throws Exception {

		if (Util.isNullOrEmpty(slipNo)) {
			return null;
		}

		List<SWK_TAG> list = new ArrayList<SWK_TAG>();

		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" SELECT KAI_CODE ");
		sql.add("       ,SWK_DEN_NO ");
		sql.add("       ,SEQ ");
		sql.add("       ,TAG_CODE ");
		sql.add("       ,TAG_COLOR ");
		sql.add("       ,TAG_TITLE ");
		sql.add("       ,INP_DATE ");
		sql.add("       ,UPD_DATE ");
		sql.add("       ,PRG_ID ");
		sql.add("       ,USR_ID ");
		sql.add(" FROM   SWK_TAG ");
		sql.add(" WHERE 1 = 1 ");

		// 会社コード
		if (!Util.isNullOrEmpty(companyCode)) {
			sql.add(" AND   KAI_CODE = ? ", companyCode);
		}

		// 伝票番号
		if (!Util.isNullOrEmpty(slipNo)) {
			sql.add(" AND   SWK_DEN_NO = ? ", slipNo);
		}

		sql.add(" ORDER BY SEQ ");

		Statement statement = DBUtil.getStatement(conn);
		ResultSet rs = DBUtil.select(statement, sql);

		while (rs.next()) {
			list.add(mapping(rs));
		}

		DBUtil.close(rs);
		DBUtil.close(statement);

		return list;

	}

	/**
	 * マッピング処理
	 * 
	 * @param rs
	 * @return 付箋
	 * @throws Exception
	 */
	protected SWK_TAG mapping(ResultSet rs) throws Exception {

		SWK_TAG bean = createBean();
		bean.setKAI_CODE(rs.getString("KAI_CODE"));
		bean.setSWK_DEN_NO(rs.getString("SWK_DEN_NO"));
		bean.setSEQ(rs.getInt("SEQ"));
		bean.setTAG_CODE(rs.getString("TAG_CODE"));
		int[] rgbCode = Util.toRGBColorCode(rs.getString("TAG_COLOR"));
		bean.setTAG_COLOR(new Color(rgbCode[0], rgbCode[1], rgbCode[2]));
		bean.setTAG_TITLE(rs.getString("TAG_TITLE"));
		bean.setINP_DATE(rs.getTimestamp("INP_DATE"));
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE"));
		bean.setPRG_ID(rs.getString("PRG_ID"));
		bean.setUSR_ID(rs.getString("USR_ID"));

		return bean;
	}

	/**
	 * @return エンティティ作成
	 */
	protected SWK_TAG createBean() {
		return new SWK_TAG();
	}

	/**
	 * 伝票付箋の登録
	 * 
	 * @param slip 伝票
	 */
	public void entry(Slip slip) {

		Connection conn = null;

		try {

			conn = DBUtil.getConnection();

			// 排他チェック
			checkLock(conn, slip.getCompanyCode(), slip.getSlipNo(), slip.getHeader().getSWK_UPD_CNT());

			// 削除
			if (getCompany().getAccountConfig().isUseGroupAccount() && getCompanyCode().equals(slip.getCompanyCode())) {
				// 親会社の伝票且つログイン会社がグループ会計使う場合、伝票番号で削除
				delete(null, slip.getSlipNo(), null);
			} else if (getCompany().getAccountConfig().isUseGroupAccount()
				&& !getCompanyCode().equals(slip.getCompanyCode())) {
				// 子会社且つログイン会社がグループ会計使う場合、処理不要
				return;
			} else {
				delete(slip.getCompanyCode(), slip.getSlipNo(), null);
			}

			// Map<String, byte[]>
			List<SWK_TAG> swkTags = slip.getHeader().getSwkTags();

			if (swkTags == null || swkTags.size() == 0) {
				// 付箋なしの場合、INSERT不要
				return;
			}

			// 付箋の登録
			for (SWK_TAG entity : swkTags) {

				// 伝票番号設定
				entity.setKAI_CODE(slip.getCompanyCode());
				entity.setSWK_DEN_NO(slip.getSlipNo());

				if (entity.getINP_DATE() == null) {
					entity.setINP_DATE(getNow());
				}
				if (Util.isNullOrEmpty(entity.getPRG_ID())) {
					entity.setPRG_ID(getProgramCode());
				}
				if (Util.isNullOrEmpty(entity.getUSR_ID())) {
					entity.setUSR_ID(getUserCode());
				}

				insert(conn, entity);
			}

		} catch (Exception e) {
			throw new TRuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 付箋の登録
	 * 
	 * @param bean 付箋
	 */
	public void entry(SWK_TAG bean) {

		Connection conn = null;

		try {

			conn = DBUtil.getConnection();

			// 排他チェック
			checkLock(conn, bean.getKAI_CODE(), bean.getSWK_DEN_NO());

			// 登録処理
			insert(conn, bean);

		} catch (Exception e) {
			throw new TRuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * INSERT
	 * 
	 * @param conn
	 * @param bean
	 * @throws TException
	 */
	protected void insert(Connection conn, SWK_TAG bean) throws TException {

		VCreator sql = new VCreator();

		sql.add(" INSERT INTO SWK_TAG ");
		sql.add("   (KAI_CODE ");
		sql.add("   ,SWK_DEN_NO ");
		sql.add("   ,SEQ ");
		sql.add("   ,TAG_CODE ");
		sql.add("   ,TAG_COLOR ");
		sql.add("   ,TAG_TITLE ");
		sql.add("   ,INP_DATE ");
		sql.add("   ,PRG_ID ");
		sql.add("   ,USR_ID) ");
		sql.add(" VALUES ");
		sql.add("   (? ", bean.getKAI_CODE());
		sql.add("   ,? ", bean.getSWK_DEN_NO());
		sql.add("   ,? ", bean.getSEQ());
		sql.add("   ,? ", bean.getTAG_CODE());
		sql.add("   ,? ", Util.to16RGBColorCode(bean.getTAG_COLOR()));
		sql.add("   ,? ", bean.getTAG_TITLE());
		sql.adt("   ,? ", bean.getINP_DATE());
		sql.add("   ,? ", bean.getPRG_ID());
		sql.add("   ,? ", bean.getUSR_ID());
		sql.add("   ) ");

		DBUtil.execute(conn, sql);
	}

	/**
	 * 付箋に対する伝票の排他チェック
	 * 
	 * @param conn
	 * @param kaiCode
	 * @param slipNo
	 * @throws Exception
	 */
	protected void checkLock(Connection conn, String kaiCode, String slipNo) throws Exception {
		checkLock(conn, kaiCode, slipNo, -1);
	}

	/**
	 * 付箋に対する伝票の排他チェック
	 * 
	 * @param conn
	 * @param kaiCode
	 * @param slipNo
	 * @param slipUpdCnt
	 * @throws Exception
	 */
	protected void checkLock(Connection conn, String kaiCode, String slipNo, int slipUpdCnt) throws Exception {

		// 排他チェック
		SlipManager sm = (SlipManager) getComponent(SlipManager.class);
		sm.lockSlipTable();

		VCreator where = new VCreator();
		where.add("         AND    KAI_CODE = ? ", kaiCode);
		where.add("         AND    SWK_DEN_NO = ? ", slipNo);
		if (slipUpdCnt >= 0) {
			where.add("         AND    SWK_UPD_CNT = ? ", slipUpdCnt);
		}

		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" SELECT SUM(CNT) ");
		sql.add(" FROM   (SELECT COUNT(*) cnt ");
		sql.add("         FROM   GL_SWK_HDR ");
		sql.add("         WHERE  SWK_SHR_KBN = 1 ");
		sql.add(where.toSQL());
		sql.add("         UNION ALL ");
		sql.add("         SELECT COUNT(*) cnt ");
		sql.add("         FROM   AP_SWK_HDR ");
		sql.add("         WHERE  SWK_SHR_KBN = 1 ");
		sql.add(where.toSQL());
		sql.add("         UNION ALL ");
		sql.add("         SELECT COUNT(*) cnt ");
		sql.add("         FROM   AR_SWK_HDR ");
		sql.add("         WHERE  SWK_SHR_KBN = 1 ");
		sql.add(where.toSQL());
		sql.add("        ) s ");

		int count = DBUtil.selectOneInt(conn, sql.toSQL());
		if (count != 0) {
			// 指定の伝票は他ユーザーが編集中です。
			throw new TRuntimeException("W00123");
		}

		sql = new VCreator();
		sql.add(" SELECT SUM(CNT) ");
		sql.add(" FROM   (SELECT COUNT(*) cnt ");
		sql.add("         FROM   GL_SWK_HDR ");
		sql.add("         WHERE  1 = 1 ");
		sql.add(where.toSQL());
		sql.add("         UNION ALL ");
		sql.add("         SELECT COUNT(*) cnt ");
		sql.add("         FROM   AP_SWK_HDR ");
		sql.add("         WHERE  1 = 1 ");
		sql.add(where.toSQL());
		sql.add("         UNION ALL ");
		sql.add("         SELECT COUNT(*) cnt ");
		sql.add("         FROM   AR_SWK_HDR ");
		sql.add("         WHERE  1 = 1 ");
		sql.add(where.toSQL());
		sql.add("        ) s ");
		count = DBUtil.selectOneInt(conn, sql.toSQL());
		if (count == 0) {
			// 指定の伝票は他端末で更新されています。{0}
			throw new TRuntimeException("I00070", slipNo);
		}

	}

	/**
	 * 付箋に対する伝票の承認日時のチェック
	 * 
	 * @param conn
	 * @param bean 付箋
	 * @param minDate 最小日時
	 * @throws Exception
	 */
	protected void checkApproveTimestamp(Connection conn, SWK_TAG bean, Date minDate) throws Exception {

		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" SELECT KAI_CODE ");
		sql.add("       ,SWK_DEN_NO ");
		sql.add("       ,SWK_UPD_KBN ");
		sql.add("       ,INP_DATE ");
		sql.add(" FROM   SWK_SYO_RRK ");
		sql.add(" WHERE  KAI_CODE = ? ", bean.getKAI_CODE());
		sql.add(" AND    SWK_DEN_NO = ? ", bean.getSWK_DEN_NO());
		sql.add(" ORDER BY INP_DATE DESC ");

		Statement statement = DBUtil.getStatement(conn);
		ResultSet rs = DBUtil.select(statement, sql);

		while (rs.next()) {
			// 1行目のみ判定:現場承認、経理承認、更新の場合
			int updKbn = rs.getInt("SWK_UPD_KBN");

			switch (updKbn) {
				case 2:
				case 3:
				case 4:
					Date inpDate = rs.getTimestamp("INP_DATE");
					if (inpDate != null && Util.isSmallerThenByYMDHMS(minDate, inpDate)) {
						// 承認時の付箋ファイルは削除できません。
						throw new TRuntimeException("I00534");
					}
					break;
			}

			// 1行目のみ判定
			break;
		}

		DBUtil.close(rs);
		DBUtil.close(statement);
	}

	/**
	 * 付箋情報の削除
	 * 
	 * @param list List<Tag>
	 */
	public void delete(List<SWK_TAG> list) {

		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			if (list == null || list.isEmpty()) {
				return;
			}

			SWK_TAG bean = list.get(0);

			// 排他チェック
			checkLock(conn, bean.getKAI_CODE(), bean.getSWK_DEN_NO());

			Date minDate = bean.getINP_DATE();
			for (SWK_TAG tag : list) {
				minDate = DateUtil.minYMDHMS(minDate, tag.getINP_DATE());
			}

			// 最小日時で承認済みのチェックを行う
			if (minDate != null) {
				checkApproveTimestamp(conn, bean, minDate);
			}

			for (SWK_TAG tag : list) {
				delete(conn, bean.getKAI_CODE(), bean.getSWK_DEN_NO(), tag.getSEQ());
			}

		} catch (Exception e) {
			throw new TRuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 特定の伝票の付箋情報の削除
	 * 
	 * @param companyCode
	 * @param slipNo
	 */
	public void delete(String companyCode, String slipNo) {
		delete(companyCode, slipNo, null);
	}

	/**
	 * 特定の伝票の付箋情報の削除
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @param sEQ SEQ
	 */
	public void delete(String companyCode, String slipNo, Integer sEQ) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();

			// 削除処理
			delete(conn, companyCode, slipNo, sEQ);

		} catch (Exception e) {
			throw new TRuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 特定の伝票の付箋情報の削除
	 * 
	 * @param conn
	 * @param companyCode
	 * @param slipNo
	 * @param sEQ SEQ
	 * @throws Exception
	 */
	protected void delete(Connection conn, String companyCode, String slipNo, Integer sEQ) throws Exception {

		// 削除
		VCreator sql = new VCreator();

		sql.add(" DELETE FROM SWK_TAG ");
		sql.add(" WHERE  SWK_DEN_NO = ? ", slipNo);

		if (!Util.isNullOrEmpty(companyCode)) {
			sql.add(" AND    KAI_CODE = ? ", companyCode);
		}
		if (sEQ != null) {
			sql.add(" AND    SEQ = ? ", sEQ);
		}

		DBUtil.execute(conn, sql);
	}

	/**
	 * SQL用
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
