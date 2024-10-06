package jp.co.ais.trans2.model.close;

import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.history.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 日次締め処理の実装
 * @author AIS
 *
 */
public class DailyCloseImpl extends TModel implements DailyClose {
	
	/** CFデータ作成不要 */
	public static boolean isNotCreateCF = ServerConfig.isFlagOn("trans.GL0060.not.create.cf.data");

	/** true:承認履歴機能有効 */
	public static boolean isUseApproveHistory = ServerConfig.isFlagOn("trans.slip.use.approve.history");

	/** 部門単位で前期繰越利益を生成 */
	public static boolean isDepCreate = ServerConfig.isFlagOn("trans.GL0060.dep.create.retained.earnings");

	/** true:CM_FUND_DTL登録を行うか */
	public static boolean isUseCmFund = ServerConfig.isFlagOn("trans.use.cm.fund.entry");

	/** 外貨評価替概算の伝票種別 */
	public static String code01Z = "01Z";

	/** 計算ロジック */
	public TCalculator calculator = TCalculatorFactory.getCalculator();

	/**
	 * 最後に日次更新した日付を返す
	 * @param company
	 * @return Date 最後に日次更新した日付
	 * @throws TException
	 */
	public Date getLastDailyClosedDate(Company company) throws TException {

		Connection conn = DBUtil.getConnection();
		Date date = null;

		try {

			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT ");
			sql.add("     NITIJI_DATE ");
			sql.add(" FROM SIM_CTL ");
			sql.add(" WHERE	KAI_CODE = ? ",company.getCode());
			
			date = DBUtil.selectOneDate(conn, sql.toSQL());

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return date;

	}

	/**
	 * 最後に日次更新した日付を更新する
	 * @param company 会社
	 * @param date 日付
	 * @throws TException
	 */
	public void updateLastDailyClosedDate(Company company, Date date) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();
			sql.add(" UPDATE SIM_CTL ");
			sql.add(" SET NITIJI_DATE = ? ",date);
			sql.adt("    ,UPD_DATE = ? ", getNow());
			sql.add("    ,PRG_ID = ? ", getProgramCode());
			sql.add("    ,USR_ID = ? ", getUserCode());
			sql.add(" WHERE	KAI_CODE = ? ", company.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 日次の締めを行う
	 * @param date 伝票日付
	 * @param company 会社
	 * @throws TException
	 */
	public void closeDaily(Date date, Company company) throws TException {

		Connection conn = DBUtil.getConnection();

		// MySQLの場合は最後にT_DAILY_CLOSEを削除するため、判定フラグを追加
		boolean isFlush = false;
		String slipUpdateTable = "T_DAILY_CLOSE";
		
		try {

			// 1.初期処理

			// 前期繰越利益科目とその計上部門を取得する
			SlipManager slipManager = (SlipManager)getComponent(SlipManager.class);
			AutoJornalAccount retainedEarningsItemJornal =
				slipManager.getAutoJornalAccount(
						company.getCode(),
						AutoJornalAccountType.PREVIOUS_EARNING_CARRIED_FORWARD);

			if (retainedEarningsItemJornal == null) {
				// 前期繰越利益の取得に失敗しました。
				throw new TException("I00201", "C02113");
			}

			// 共通条件の生成
			// 会社
			String sqlSwkWhere = " WHERE swk.KAI_CODE = " + SQLUtil.getParam(company.getCode());

			// 伝票日付
			sqlSwkWhere = sqlSwkWhere + " AND	swk.SWK_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date);

			// 更新区分
			if (company.getAccountConfig().isUseApprove()) {
				sqlSwkWhere = sqlSwkWhere + " AND	swk.SWK_UPD_KBN = 3 ";
			} else if (company.getAccountConfig().isUseFieldApprove()) {
				sqlSwkWhere = sqlSwkWhere + " AND	swk.SWK_UPD_KBN = 2 ";
			} else {
				sqlSwkWhere = sqlSwkWhere + " AND	swk.SWK_UPD_KBN = 1 ";
			}

			// 更新対象データ一時格納テーブル

			if (DBUtil.isUseMySQL || !isNotCreateCF) {

				SQLCreator sqlCnt = new SQLCreator();
				sqlCnt.add("  SELECT COUNT(*) FROM " + slipUpdateTable);
				int tblCnt = DBUtil.selectOneInt(conn, sqlCnt.toSQL());
				if (tblCnt > 0) {
					sqlCnt = new SQLCreator();
					sqlCnt.add("  SELECT DISTINCT KAI_CODE FROM " + slipUpdateTable);

					Statement statement = conn.createStatement();
					ResultSet rs = DBUtil.select(statement, sqlCnt);

					String kaiCode = null;
					while (rs.next()) {
						kaiCode = rs.getString("KAI_CODE");
					}
					rs.close();
					statement.close();

					
					throw new TException("I01140", kaiCode); // 会社：{0} で日次更新実行中です。しばらくたってから再度実行してください。
				}
				
				SQLCreator sqlUd = new SQLCreator();
				sqlUd.add("  INSERT INTO " + slipUpdateTable);
				sqlUd.add("  ( KAI_CODE ");
				sqlUd.add("   ,SWK_DEN_NO ");
				sqlUd.add("  ) ");
				sqlUd.add("  SELECT DISTINCT swk.KAI_CODE ");
				sqlUd.add("                 ,swk.SWK_DEN_NO ");
				sqlUd.add("  FROM SWK_DTL swk ");
				sqlUd.add(sqlSwkWhere);
				sqlUd.add("  AND swk.DEN_SYU_CODE <> ? ", code01Z);

				int count = DBUtil.execute(conn, sqlUd);

				if (count == 0) {
					throw new TException("I00022"); // 対象となるデータが見つかりません。
				}
				
				// 件数があればtrue→最後に削除
				isFlush = true;

				if (DBUtil.isUseMySQL) {
					// ロックはMySQLのみ
					sqlUd = new SQLCreator();
					sqlUd.add(" SELECT SWK_DEN_NO ");
					sqlUd.add(" FROM " + slipUpdateTable);
					sqlUd.add(" WHERE KAI_CODE = ? ", company.getCode());

					sqlSwkWhere = sqlSwkWhere + "AND swk.SWK_DEN_NO IN (" + sqlUd.toSQL() + ")";
					
					{
						// 伝票をロックにする(日次更新準備)
						String sqlUpdate = " SET PRG_ID = 'BATCH' " + sqlSwkWhere;
						
						DBUtil.execute(conn, " UPDATE GL_SWK_HDR swk " + sqlUpdate);
						DBUtil.execute(conn, " UPDATE AP_SWK_HDR swk " + sqlUpdate);
						DBUtil.execute(conn, " UPDATE AR_SWK_HDR swk " + sqlUpdate);
						DBUtil.execute(conn, " UPDATE SWK_DTL swk " + sqlUpdate);
					}
				}
				
			}

			// 更新用SQL持つ(更新対象と残高転記対象が異なる可能(中間決算繰り越さない場合))
			String updateSqlSwkWhere = sqlSwkWhere;
			String updateSqlSwkWhereLast = sqlSwkWhere; // 最終更新用に退避
			
			// 債務残高、債権残高、債権回収データ転記用条件
			String balanceSqlSwkWhere = sqlSwkWhere;

			// 中間決算繰り越すWHERE条件設定
			sqlSwkWhere += getCarryJournalOfMidtermClosingForward(company);

			if (!isCarryJournalOfMidtermClosingForward(company)) {
				// 中間繰り越さない場合のみ、期末月を計算して設定
				int beginMonth = company.getFiscalPeriod().getMonthBeginningOfPeriod();
				String endMonth = StringUtil.rightBX("0" + ((beginMonth + 10) % 12 + 1), 2);

				if (!Util.isNullOrEmpty(endMonth)) {
					// 期末月以外の場合は決算伝票を転記しない
					balanceSqlSwkWhere += " AND (swk.SWK_KSN_KBN = 0 OR TO_CHAR(swk.SWK_DEN_DATE, 'MM') = " + SQLUtil.getParam(endMonth) + ") ";
				}
			}
			
			if (!DBUtil.isUseMySQL) {
				// 外貨評価替の伝票種別を除く
				sqlSwkWhere += " AND    swk.DEN_SYU_CODE <> '" + code01Z + "' ";
			}

			// 更新情報
			String sqlUpdateInfoColumn =
				SQLUtil.getYMDHMSParam(getNow()) + ", " +
				" NULL, " +
				SQLUtil.getParam(getProgramCode()) + ", " +
				SQLUtil.getParam(getUserCode()) + ", ";

			// 管理残高のカラム
			String krzZanColumn =
				" KAI_CODE, " +
				" KRZ_NENDO, " +
				" KRZ_DEP_CODE, " +
				" KRZ_KMK_CODE, " +
				" KRZ_HKM_CODE, " +
				" KRZ_UKM_CODE, " +
				" KRZ_TRI_CODE, " +
				" KRZ_EMP_CODE, " +
				" KRZ_KNR_CODE_1, " +
				" KRZ_KNR_CODE_2, " +
				" KRZ_KNR_CODE_3, " +
				" KRZ_KNR_CODE_4, " +
				" KRZ_KNR_CODE_5, " +
				" KRZ_KNR_CODE_6, " +
				" KRZ_KSN_KBN, " +
				" KRZ_CUR_CODE, " +
				" KRZ_STR_SUM, " +
				" KRZ_STR_SUM_G, ";
			for (int month = 1; month <= 12; month++) {
				krzZanColumn = krzZanColumn +
					" KRZ_DR_" + month + ", " +
					" KRZ_CR_" + month + ", " +
					" KRZ_ZAN_" + month + ", " +
					" KRZ_DR_G_" + month + ", " +
					" KRZ_CR_G_" + month + ", " +
					" KRZ_ZAN_G_" + month + ", ";
			}
			krzZanColumn = krzZanColumn +
				" INP_DATE, " +
				" UPD_DATE, " +
				" PRG_ID, " +
				" USR_ID, " +
				" KRZ_BOOK_NO, " +
				" KRZ_ADJ_KBN ";

			// 2.テーブルロックにより他トランザクションをブロック
			try {
				DBUtil.execute(conn, "LOCK TABLE GL_SWK_HDR IN SHARE ROW EXCLUSIVE MODE NOWAIT");
				DBUtil.execute(conn, "LOCK TABLE AP_SWK_HDR IN SHARE ROW EXCLUSIVE MODE NOWAIT");
				DBUtil.execute(conn, "LOCK TABLE AR_SWK_HDR IN SHARE ROW EXCLUSIVE MODE NOWAIT");
				DBUtil.execute(conn, "LOCK TABLE SWK_DTL IN SHARE ROW EXCLUSIVE MODE NOWAIT");
			} catch (TException e) {
				throw new TException("W01133");
			}

			// 3.更新対象仕訳のアンバランスチェック
			String sql =
				" SELECT " +
					" SWK_BOOK_NO, " +
					" SWK_DEN_NO " +
				" FROM " +
					" SWK_DTL swk " + updateSqlSwkWhere +
				" GROUP BY " +
				    " SWK_BOOK_NO, " +
				    " SWK_DEN_NO " +
				" HAVING SUM(CASE WHEN SWK_DC_kBN = 0 THEN SWK_KIN ELSE -SWK_KIN END) <> 0 ";

			String unbalanceSlipNo = (String)DBUtil.select(conn, sql, new ORMapper() {

				public Object mapping(ResultSet rs) throws Exception {

					String result = "";
					while (rs.next()) {
						result = result + rs.getString("SWK_DEN_NO") + ";";
					}
					return result;
				}
			});

			if (!Util.isNullOrEmpty(unbalanceSlipNo)) {
				// 更新対象の仕訳が貸借不一致です。伝票番号：
				throw new TException("W01141" , unbalanceSlipNo);
			}

			// 4.残高繰越処理
			// 4.1 前期繰越利益がどの年度まで作成されているかを取得
			sql =
				" SELECT	MAX(KRZ_NENDO) NENDO " +
			    " FROM		KRZ_ZAN "+
			    " WHERE		KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
			    " AND		KRZ_KMK_CODE = " + SQLUtil.getParam(retainedEarningsItemJornal.getItemCode());

			int krzMaxNendo = DBUtil.selectOneInt(conn, sql);

			// 4.2 今回対象仕訳の最大の年度を取得
			sql =
				" SELECT	MAX(SWK_NENDO) MAX_NENDO " +
			    " FROM		SWK_DTL swk " + updateSqlSwkWhere;

			int swkMaxNendo = DBUtil.selectOneInt(conn, sql);

			sql =
				" SELECT	MIN(SWK_NENDO) MIN_NENDO " +
			    " FROM		SWK_DTL swk " + updateSqlSwkWhere;

			int swkMinNendo = DBUtil.selectOneInt(conn, sql);

			// 対象データ無し
			if (swkMaxNendo == 0) {
				// 対象となるデータが見つかりません。
				throw new TException("I00022");
			}

			// 管理残高が全く無い場合は、今回更新対象の伝票の年度とする
			if (krzMaxNendo == 0) {
				krzMaxNendo = swkMinNendo;
			}

			// 4.3 繰越利益の最終年度を求める。
			int lastNendo = krzMaxNendo;
			if (krzMaxNendo <= swkMaxNendo) {
				lastNendo = swkMaxNendo + 1;
			}

			// 4.4 管理残高の現在B/Sが繰り越されている年度を今回の繰越年度が超えた場合、繰越処理をする
			if (lastNendo > krzMaxNendo) {

				// 繰越は、今現在の年度から今回対象の最大年度まで
				for (int i = krzMaxNendo; i < lastNendo; i++) {

					// 前期繰越利益行作成
					// ※前期繰越行は必ず1年先まで作る
					sql =
						" INSERT INTO KRZ_ZAN ( " + krzZanColumn + " ) " +
						" SELECT " +
							" krz.KAI_CODE, " +
							Integer.toString(i + 1) + ", ";
					if (isDepCreate) {
						// 部門単位で生成
						sql += " krz.KRZ_DEP_CODE, ";
					} else {
						// 自動仕訳科目マスタ
						sql += SQLUtil.getParam(retainedEarningsItemJornal.getDepertmentCode()) + ", ";
					}
					sql +=
							SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) + ", " +
							SQLUtil.getParam(retainedEarningsItemJornal.getSubItemCode()) + ", " +
							SQLUtil.getParam(retainedEarningsItemJornal.getDetailItemCode()) + ", " +
							" NULL, " +
							" NULL, " +
							" NULL, " +
							" NULL, " +
							" NULL, " +
							" NULL, " +
							" NULL, " +
							" NULL, " +
							" 0, " +
							" krz.KRZ_CUR_CODE, " +
							" SUM(krz.KRZ_ZAN_12), " +
							" SUM(krz.KRZ_ZAN_G_12), ";
					for (int month = 1; month <= 12; month++) {
						sql = sql +
							" 0, 0, SUM(krz.KRZ_ZAN_12), " +
							" 0, 0, SUM(krz.KRZ_ZAN_G_12), ";
					}
					sql = sql + sqlUpdateInfoColumn +
							" krz.KRZ_BOOK_NO, " +
							" krz.KRZ_ADJ_KBN " +
						" FROM " +
							" KRZ_ZAN krz " +
						" WHERE krz.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
						" AND	krz.KRZ_NENDO = " + Integer.toString(i) +
						" AND	krz.KRZ_KMK_CODE = " + SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) +
						" GROUP BY " +
							" krz.KAI_CODE, " +
							" krz.KRZ_CUR_CODE, ";
					if (isDepCreate) {
						// 部門単位で生成
						sql += " krz.KRZ_DEP_CODE, ";
					}
					sql += " krz.KRZ_BOOK_NO, " +
							" krz.KRZ_ADJ_KBN ";

					int count = DBUtil.execute(conn, sql);

					// 前期繰越利益のレコードが無ければ登録する。
					// ※繰越利益行の存在チェックは常に日本基準帳簿を指すことにする。
					if (count == 0) {

						if (isDepCreate) {
							// 部門単位で生成
							sql =
								"INSERT INTO KRZ_ZAN " +
								"SELECT " +
								    " dtl.KAI_CODE, " +
									Integer.toString(i + 1) + ", " +
									" dtl.SWK_DEP_CODE, " +
									SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) + ", " +
									SQLUtil.getParam(retainedEarningsItemJornal.getSubItemCode()) + ", " +
									SQLUtil.getParam(retainedEarningsItemJornal.getDetailItemCode()) + ", " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" 0, " +
									SQLUtil.getParam(company.getAccountConfig().getKeyCurrency().getCode()) + ", " +
									" 0, ";//KRZ_STR_SUM
							for (int month = 1; month <= 12; month++) {
								sql = sql + " 0, 0, 0, ";
							}
							sql = sql + sqlUpdateInfoColumn;
							sql = sql + " 0, ";//KRZ_STR_SUM_G
							for (int month = 1; month <= 12; month++) {
								sql = sql + " 0, 0, 0, ";
							}
							sql = sql + " 1, " +
									" 0 " +
								" FROM SWK_DTL dtl "+
								" LEFT OUTER JOIN KMK_MST kmk " +
								" ON  dtl.KAI_CODE = kmk.KAI_CODE " +
								" AND dtl.SWK_KMK_CODE = kmk.KMK_CODE " +
								" WHERE dtl.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
								" AND kmk.KMK_SHU = 1 ";
								// 更新区分
								if (company.getAccountConfig().isUseApprove()) {
									sql += " AND dtl.SWK_UPD_KBN = 3 ";
								} else if (company.getAccountConfig().isUseFieldApprove()) {
									sql += " AND dtl.SWK_UPD_KBN = 2 ";
								} else {
									sql += " AND dtl.SWK_UPD_KBN = 1 ";
								}
							sql += 
								" AND dtl.SWK_NENDO = " + Integer.toString(i) +
								" GROUP BY dtl.KAI_CODE, dtl.SWK_NENDO, dtl.SWK_DEP_CODE " +
								" ORDER BY dtl.KAI_CODE, dtl.SWK_NENDO, dtl.SWK_DEP_CODE ";
						} else {
							// 自動仕訳科目マスタで生成
							sql =
								" INSERT INTO KRZ_ZAN ( " + krzZanColumn + " ) " +
								" VALUES ( " +
									SQLUtil.getParam(company.getCode()) + ", " +
									Integer.toString(i + 1) + ", " +
									SQLUtil.getParam(retainedEarningsItemJornal.getDepertmentCode()) + ", " +
									SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) + ", " +
									SQLUtil.getParam(retainedEarningsItemJornal.getSubItemCode()) + ", " +
									SQLUtil.getParam(retainedEarningsItemJornal.getDetailItemCode()) + ", " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" 0, " +
									SQLUtil.getParam(company.getAccountConfig().getKeyCurrency().getCode()) + ", " +
									" 0, " +
									" 0, ";
							for (int month = 1; month <= 12; month++) {
								sql = sql + " 0, 0, 0, 0, 0, 0, ";
							}
							sql = sql + sqlUpdateInfoColumn +
									" 1, " +
									" 0 " +
								" ) ";
						}

						DBUtil.execute(conn, sql);

					}

					// B/S繰越
					sql =
						" INSERT INTO KRZ_ZAN ( " + krzZanColumn + " ) " +
						" SELECT /*+RULE*/ " +
							" krz.KAI_CODE, " +
							Integer.toString(i) + ", " +
							" krz.KRZ_DEP_CODE, " +
							" krz.KRZ_KMK_CODE, " +
							" krz.KRZ_HKM_CODE, " +
							" krz.KRZ_UKM_CODE, " +
							" krz.KRZ_TRI_CODE, " +
							" krz.KRZ_EMP_CODE, " +
							" krz.KRZ_KNR_CODE_1, " +
							" krz.KRZ_KNR_CODE_2, " +
							" krz.KRZ_KNR_CODE_3, " +
							" krz.KRZ_KNR_CODE_4, " +
							" krz.KRZ_KNR_CODE_5, " +
							" krz.KRZ_KNR_CODE_6, " +
							" 0, " +
							" krz.KRZ_CUR_CODE, " +
							" SUM(krz.KRZ_ZAN_12), " +
							" SUM(krz.KRZ_ZAN_G_12), ";
					for (int month = 1; month <= 12; month++) {
						sql = sql +
							" 0, 0, SUM(krz.KRZ_ZAN_12), " +
							" 0, 0, SUM(krz.KRZ_ZAN_G_12), ";
					}
					sql = sql + sqlUpdateInfoColumn +
							" krz.KRZ_BOOK_NO, " +
							" krz.KRZ_ADJ_KBN " +
						" FROM " +
							" KRZ_ZAN krz " +
							" INNER JOIN KMK_MST kmk " +
							" ON	krz.KAI_CODE = kmk.KAI_CODE " +
							" AND	krz.KRZ_KMK_CODE = kmk.KMK_CODE " +
							" AND	kmk.KMK_SHU <> '1' " +
						" WHERE krz.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
						" AND	krz.KRZ_NENDO = " + Integer.toString(i - 1) +
						" AND	krz.KRZ_KMK_CODE <> " + SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) +
						" GROUP BY " +
							" krz.KAI_CODE, " +
							" krz.KRZ_DEP_CODE, " +
							" krz.KRZ_KMK_CODE, " +
							" krz.KRZ_HKM_CODE, " +
							" krz.KRZ_UKM_CODE, " +
							" krz.KRZ_TRI_CODE, " +
							" krz.KRZ_EMP_CODE, " +
							" krz.KRZ_KNR_CODE_1, " +
							" krz.KRZ_KNR_CODE_2, " +
							" krz.KRZ_KNR_CODE_3, " +
							" krz.KRZ_KNR_CODE_4, " +
							" krz.KRZ_KNR_CODE_5, " +
							" krz.KRZ_KNR_CODE_6, " +
							" krz.KRZ_CUR_CODE, " +
							" krz.KRZ_BOOK_NO, " +
							" krz.KRZ_ADJ_KBN " +
						" HAVING 	SUM(krz.KRZ_ZAN_12) <> 0 " +
						" OR		SUM(krz.KRZ_ZAN_G_12) <> 0 ";

					DBUtil.execute(conn, sql);

				}

			}

			// 5 残高転記(当年度)
			updateKrzZanCurrentYear(sqlSwkWhere, krzZanColumn);

			// 5 残高転記(翌年度以降)

			// 5.1 B/S転記
			updateKrzZanBS(sqlSwkWhere, krzZanColumn, retainedEarningsItemJornal, swkMinNendo, swkMaxNendo, lastNendo);

			// 5.2 P/L転記(翌年の前期繰越利益に加算)
			updateKrzZanPL(sqlSwkWhere, krzZanColumn, retainedEarningsItemJornal, swkMinNendo, swkMaxNendo, lastNendo, company);

			// 6.残高バランスチェック
			for (int nendo = swkMinNendo; nendo < lastNendo; nendo++) {

				sql =
					" SELECT " +
						" SUM(CASE WHEN kmk.DC_KBN = 0 THEN krz.KRZ_ZAN_12 ELSE -krz.KRZ_ZAN_12 END) BALANCE ";
						if (isDepCreate) {
							// 部門単位でチェック
							sql += " ,krz.KRZ_DEP_CODE ,dep.DEP_NAME ";
						}
						sql = sql +
			        " FROM " +
			        	" KRZ_ZAN krz " +
						" LEFT OUTER JOIN KMK_MST kmk " +
						" ON	krz.KAI_CODE = kmk.KAI_CODE " +
						" AND	krz.KRZ_KMK_CODE = kmk.KMK_CODE " +
						" LEFT OUTER JOIN BMN_MST dep " +
						" ON	krz.KAI_CODE     = dep.KAI_CODE " +
						" AND	krz.KRZ_DEP_CODE = dep.DEP_CODE " +
					" WHERE 	krz.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					" AND		krz.KRZ_NENDO = " + Integer.toString(nendo);
						if (isDepCreate) {
							// 部門単位でチェック
							sql += " GROUP BY krz.KRZ_DEP_CODE ,dep.DEP_NAME ";
						}

				if (isDepCreate) {
					// 計上部門毎にチェック → 部門ごとに金額を表示
					Statement statement = conn.createStatement();
					ResultSet rs = DBUtil.select(statement, sql);

					String result = "";
					BigDecimal balance = null;
					String msg = null;
					while (rs.next()) {
						if (!Util.isNullOrEmpty(result)) {
							result = result + "\n";
						}
						balance = rs.getBigDecimal("BALANCE");
						if (balance != null && balance.compareTo(BigDecimal.ZERO) != 0) {
							// 管理残高が貸借不一致です。 アンバランス額： 、アンバランス年度：、アンバランス計上部門：
							msg = getMessage("W00091", getWord("C11228") + balance.toString() + "、" + getWord("C11229") + Integer.toString(nendo) + "、" + getWord("CAC002") + rs.getString("KRZ_DEP_CODE") + ":" + rs.getString("DEP_NAME"));
							
							result = result + msg;
						}

					}
					rs.close();
					statement.close();
					if (!Util.isNullOrEmpty(result)) {
						// 構築したエラーがあれば終了
						throw new TException(result);
					}

				} else {
					BigDecimal balance = DecimalUtil.toBigDecimalNVL(DBUtil.selectOne(conn, sql));
					if (balance != null && balance.compareTo(BigDecimal.ZERO) != 0) {
						// 管理残高が貸借不一致です。 アンバランス額： 、アンバランス年度：
						throw new TException(getMessage("W00091", getWord("C11228") + balance.toString() + "、"
							+ getWord("C11229") + Integer.toString(nendo)));
					}
				}

			}

			// 7.債務残高更新
			sql =
				" INSERT INTO AP_ZAN ( " +
					" KAI_CODE, " +
					" ZAN_DEN_DATE, " +
					" ZAN_DEN_NO, " +
					" ZAN_GYO_NO, " +
					" ZAN_DC_KBN, " +
					" ZAN_KMK_CODE, " +
					" ZAN_HKM_CODE, " +
					" ZAN_UKM_CODE, " +
					" ZAN_DEP_CODE, " +
					" ZAN_UKE_DEP_CODE, " +
					" ZAN_TEK_CODE, " +
					" ZAN_TEK, " +
					" ZAN_SIHA_CODE, " +
					" ZAN_KIN, " +
					" ZAN_SIHA_DATE, " +
					" ZAN_SIHA_KBN, " +
					" ZAN_HOH_CODE, " +
					" ZAN_HORYU_KBN, " +
					" ZAN_KESAI_KBN, " +
					" ZAN_SEI_NO, " +
					" ZAN_DATA_KBN, " +
					" ZAN_NAI_CODE, " +
					" ZAN_LIST_KBN, " +
					" ZAN_SIHA_KAGEN_KBN, " +
					" ZAN_TEG_KAGEN_KBN, " +
					" ZAN_FURI_KAGEN_KBN, " +
					" ZAN_INP_DATE, " +
					" PRG_ID, " +
					" USR_ID, " +
					" ZAN_TJK_CODE, " +
					" ZAN_CUR_CODE, " +
					" ZAN_CUR_RATE, " +
					" ZAN_IN_SIHA_KIN, " +
					" ZAN_SYS_KBN, " +
					" ZAN_DEN_SYU, " +
					" ZAN_FURI_CBK_CODE, " +
					" ZAN_FURI_BNK_CODE, " +
					" ZAN_FURI_STN_CODE, " +
					" ZAN_TEG_CBK_CODE, " +
					" ZAN_TEG_BNK_CODE, " +
					" ZAN_TEG_STN_CODE, " +
					" ZAN_SWK_GYO_NO " +
				" ) " +
				" SELECT /*+RULE*/ " +
					" swk.KAI_CODE, " +
					" swk.SWK_DEN_DATE, " +
					" swk.SWK_DEN_NO, " +
					" 1, " +
					" 1, " +
					" swk.SWK_KMK_CODE, " +
					" swk.SWK_HKM_CODE, " +
					" swk.SWK_UKM_CODE, " +
					" swk.SWK_DEP_CODE, " +
					" swk.SWK_UKE_DEP_CODE, " +
					" swk.SWK_TEK_CODE, " +
					" swk.SWK_TEK, " +
					" swk.SWK_TRI_CODE, " +
					" swk.SWK_SIHA_KIN, " +
					" swk.SWK_SIHA_DATE, " +
					" swk.SWK_SIHA_KBN, " +
					" swk.SWK_HOH_CODE, " +
					" swk.SWK_HORYU_KBN, " +
					" 0, " +
					" swk.SWK_SEI_NO, " +
					" swk.SWK_DATA_KBN, " +
					" hoh.HOH_NAI_CODE, " +
					" 0, " +
					" 0, " +
					" 0, " +
					" 0, " +
					SQLUtil.getYMDHMSParam(getNow()) + ", " +
					SQLUtil.getParam(getProgramCode()) + ", " +
					SQLUtil.getParam(getUserCode()) + ", " +
					" swk.SWK_TJK_CODE, " +
					" swk.SWK_CUR_CODE, " +
					" swk.SWK_CUR_RATE, " +
					" swk.SWK_IN_SIHA_KIN, " +
					" swk.SWK_SYS_KBN, " +
					" swk.SWK_DEN_SYU, " +
					" swk.SWK_CBK_CODE, " +
					" cbk.CBK_BNK_CODE, " +
					" cbk.CBK_STN_CODE, " +
					" swk.SWK_CBK_CODE, " +
					" cbk.CBK_BNK_CODE, " +
					" cbk.CBK_STN_CODE, " +
					" dtl.SWK_GYO_NO " +
			    " FROM /*+RULE*/ " +
			    	" AP_SWK_HDR swk " +
					" INNER JOIN SWK_DTL dtl " +
					" ON	swk.KAI_CODE = dtl.KAI_CODE " +
					" AND 	swk.SWK_DEN_NO = dtl.SWK_DEN_NO " +
					" AND 	swk.SWK_KMK_CODE = dtl.SWK_KMK_CODE " +
					" AND	NVL(swk.SWK_HKM_CODE, ' ') = NVL(dtl.SWK_HKM_CODE, ' ') " +
					" AND	NVL(swk.SWK_UKM_CODE, ' ') = NVL(dtl.SWK_UKM_CODE, ' ') " +
					" AND 	swk.SWK_SIHA_KIN = CASE WHEN dtl.SWK_DC_KBN = 1 THEN dtl.SWK_KIN ELSE dtl.SWK_KIN * -1 END " +
					" AND 	dtl.SWK_AUTO_KBN = 1 " +
					" AND 	dtl.SWK_BOOK_NO = 1 " +
					" AND 	dtl.SWK_ADJ_KBN IN (0, 1) " +
					" INNER JOIN KMK_MST kmk " +
					" ON	swk.KAI_CODE = kmk.KAI_CODE " +
					" AND 	swk.SWK_KMK_CODE = kmk.KMK_CODE " +
					" AND 	kmk.KMK_CNT_AP = '01' " +
					" INNER JOIN AP_HOH_MST hoh " +
					" ON	swk.KAI_CODE = hoh.KAI_CODE " +
					" AND 	swk.SWK_HOH_CODE = hoh.HOH_HOH_CODE " +
					" LEFT OUTER JOIN AP_CBK_MST cbk " +
					" ON	swk.KAI_CODE = cbk.KAI_CODE " +
					" AND 	swk.SWK_CBK_CODE = cbk.CBK_CBK_CODE " +
					balanceSqlSwkWhere +
					" AND swk.SWK_DATA_KBN IN ('23', '2T') ";

			DBUtil.execute(conn, sql);
			
			// 8.債権残高更新
			sql =
				" INSERT INTO AR_ZAN ( " +
					" KAI_CODE, " +
					" ZAN_DEP_CODE, " +
					" ZAN_TRI_CODE, " +
					" ZAN_SEI_NO, " +
					" ZAN_SEI_DEN_DATE, " +
					" ZAN_SEI_DEN_NO, " +
					" ZAN_SEI_GYO_NO, " +
					" ZAN_DATA_KBN, " +
					" ZAN_KESI_DEN_DATE, " +
					" ZAN_KESI_DEN_NO, " +
					" ZAN_KESI_GYO_NO, " +
					" ZAN_KMK_CODE, " +
					" ZAN_HKM_CODE, " +
					" ZAN_UKM_CODE, " +
					" ZAN_AR_DATE, " +
					" ZAN_SEI_KIN, " +
					" ZAN_SEI_IN_KIN, " +
					" ZAN_KESI_KIN, " +
					" ZAN_KESI_IN_KIN, " +
					" ZAN_TRI_NAME, " +
					" INP_DATE, " +
					" UPD_DATE, " +
					" PRG_ID, " +
					" USR_ID, " +
					" ZAN_CBK_CODE, " +
					" ZAN_CUR_CODE, " +
					" ZAN_CUR_RATE " +
		        " ) " +
		        " SELECT /*+RULE*/ " +
					" swk.KAI_CODE, " +
					" swk.SWK_DEP_CODE, " +
					" swk.SWK_TRI_CODE, " +
					" swk.SWK_SEI_NO, " +
					" swk.SWK_DEN_DATE, " +
					" swk.SWK_DEN_NO, " +
					" dtl.SWK_GYO_NO, " +
					" swk.SWK_DATA_KBN, " +
					" NULL, " +
					" NULL, " +
					" NULL, " +
					" swk.SWK_KMK_CODE, " +
					" swk.SWK_HKM_CODE, " +
					" swk.SWK_UKM_CODE, " +
					" swk.SWK_AR_DATE, " +
					" swk.SWK_KIN, " +
					" swk.SWK_IN_KIN, " +
					" NULL, " +
					" NULL, " +
					" tri.TRI_NAME_S, " +
					SQLUtil.getYMDHMSParam(getNow()) + ", " +
					" NULL, " +
					SQLUtil.getParam(getProgramCode()) + ", " +
					SQLUtil.getParam(getUserCode()) + ", " +
					" swk.SWK_CBK_CODE, " +
					" swk.SWK_CUR_CODE, " +
					" swk.SWK_CUR_RATE " +
		        " FROM " +
		        	" AR_SWK_HDR swk " +
					" INNER JOIN SWK_DTL dtl " +
					" ON	swk.KAI_CODE = dtl.KAI_CODE " +
					" AND 	swk.SWK_DEN_NO = dtl.SWK_DEN_NO " +
					" AND 	swk.SWK_KMK_CODE = dtl.SWK_KMK_CODE " +
					" AND	NVL(swk.SWK_HKM_CODE, ' ') = NVL(dtl.SWK_HKM_CODE, ' ') " +
					" AND	NVL(swk.SWK_UKM_CODE, ' ') = NVL(dtl.SWK_UKM_CODE, ' ') " +
					" AND 	swk.SWK_KIN = CASE WHEN dtl.SWK_DC_KBN = 0 THEN dtl.SWK_KIN ELSE dtl.SWK_KIN * -1 END " +
					" AND 	dtl.SWK_AUTO_KBN = 1 " +
					" AND 	dtl.SWK_BOOK_NO = 1 " +
					" AND 	dtl.SWK_ADJ_KBN IN (0, 1) " +
					" INNER JOIN KMK_MST kmk " +
					" ON		swk.KAI_CODE = kmk.KAI_CODE " +
					" AND		swk.SWK_KMK_CODE = kmk.KMK_CODE " +
					" AND		kmk.KMK_CNT_AR = '01' " +
					" INNER JOIN TRI_MST tri " +
					" ON		swk.KAI_CODE = tri.KAI_CODE " +
					" AND		swk.SWK_TRI_CODE = tri.TRI_CODE " +
					balanceSqlSwkWhere +
					" AND swk.SWK_DATA_KBN IN ('31', '33') ";

			DBUtil.execute(conn, sql);

			// 9.債権回収データ登録
			sql =
				" INSERT INTO AR_KAI_DAT ( " +
					" KAI_CODE, " +
					" KAI_DEN_DATE, " +
					" KAI_DEN_NO, " +
					" KAI_GYO_NO, " +
					" KAI_DC_KBN, " +
					" KAI_KMK_CODE, " +
					" KAI_HKM_CODE, " +
					" KAI_UKM_CODE, " +
					" KAI_DEP_CODE, " +
					" KAI_KIN, " +
					" KAI_TRI_CODE, " +
					" KAI_TRI_NAME, " +
					" KAI_DATA_KBN, " +
					" KAI_KESI_DEN_DATE, " +
					" KAI_KESI_DEN_NO, " +
					" KAI_INP_DATE, " +
					" UPD_DATE, " +
					" PRG_ID, " +
					" USR_ID, " +
					" KAI_IN_KIN, " +
					" KAI_CUR_CODE, " +
					" KAI_CUR_RATE, " +
					" KAI_GYO_TEK_CODE, " +
					" KAI_GYO_TEK " +
				" ) " +
				" SELECT /*+RULE*/ " +
					" swk.KAI_CODE, " +
					" swk.SWK_DEN_DATE, " +
					" swk.SWK_DEN_NO, " +
					" swk.SWK_GYO_NO, " +
					" swk.SWK_DC_KBN, " +
					" swk.SWK_KMK_CODE, " +
					" swk.SWK_HKM_CODE, " +
					" swk.SWK_UKM_CODE, " +
					" swk.SWK_DEP_CODE, " +
					" CASE WHEN swk.SWK_DC_KBN = 0 THEN -swk.SWK_KIN ELSE swk.SWK_KIN END, " +
					" swk.SWK_TRI_CODE, " +
					" tri.TRI_NAME_S, " +
					" swk.SWK_DATA_KBN, " +
					" NULL, " +
					" NULL, " +
					SQLUtil.getYMDHMSParam(getNow()) + ", " +
					" NULL, " +
					SQLUtil.getParam(getProgramCode()) + ", " +
					SQLUtil.getParam(getUserCode()) + ", " +
					" CASE WHEN swk.SWK_DC_KBN = 0 THEN -swk.SWK_IN_KIN ELSE swk.SWK_IN_KIN END, " +
					" swk.SWK_CUR_CODE, " +
					" swk.SWK_CUR_RATE, " +
					" swk.SWK_GYO_TEK_CODE, " +
					" swk.SWK_GYO_TEK " +
			    " FROM " +
					" SWK_DTL swk " +
					" INNER JOIN SWK_KMK_MST kmk " +
					" ON	swk.KAI_CODE = kmk.KAI_CODE " +
					" AND	swk.SWK_KMK_CODE = kmk.KMK_CODE " +
					" AND	NVL(swk.SWK_HKM_CODE,' ') = NVL(kmk.HKM_CODE,' ') " +
					" AND	NVL(swk.SWK_UKM_CODE,' ') = NVL(kmk.UKM_CODE,' ') " +
					" AND 	kmk.KMK_CNT = 7 " +
					" LEFT OUTER JOIN TRI_MST tri " +
					" ON   	swk.KAI_CODE = tri.KAI_CODE " +
					" AND 	swk.SWK_TRI_CODE = tri.TRI_CODE " +
					balanceSqlSwkWhere +
					" AND	swk.SWK_KESI_KBN = 0 " + // 消込区分=0
					" AND	swk.SWK_DATA_KBN NOT IN ('14', '15') " + // 見積伝票除外
					" AND	(swk.SWK_DC_KBN = 1 " +
					" OR 	(swk.SWK_DC_KBN = 0 " +
					" AND 	swk.SWK_DATA_KBN NOT IN ('13', '32'))) " +
					" AND	swk.SWK_ADJ_KBN IN (0, 1) " +
					" AND	swk.SWK_BOOK_NO = 1 ";

			DBUtil.execute(conn, sql);

			if (!isNotCreateCF) {
				// CFデータ作成はプロパティにより、作成不要の場合、処理不要
				
				// 10.預金の相手明細登録
				// 10.1 現預金が発生している伝票で、現預金の相手科目を登録する。
				sql =
					" INSERT INTO CFL_DTL ( " +
						" KAI_CODE, " +
						" CFL_SWK_DEN_DATE, " +
						" CFL_SWK_DEN_NO, " +
						" SWK_DEN_DATE, " +
						" SWK_DEN_NO, " +
						" SWK_GYO_NO, " +
						" SWK_GYO_TEK, " +
						" SWK_DEP_CODE, " +
						" SWK_KMK_CODE, " +
						" SWK_HKM_CODE, " +
						" SWK_UKM_CODE, " +
						" SWK_TRI_CODE, " +
						" SWK_EMP_CODE, " +
						" SWK_KNR_CODE_1, " +
						" SWK_KNR_CODE_2, " +
						" SWK_KNR_CODE_3, " +
						" SWK_KNR_CODE_4, " +
						" SWK_KNR_CODE_5, " +
						" SWK_KNR_CODE_6, " +
						" SWK_CUR_CODE, " +
						" SWK_DC_KBN, " +
						" SWK_KIN, " +
						" SWK_IN_KIN, " +
						" DEN_SYU_CODE, " +
						" SWK_BOOK_NO, " +
						" SWK_ADJ_KBN " +
					" ) " +
					" SELECT /*+RULE*/ " +
						" swk.KAI_CODE, " +
						" swk.SWK_DEN_DATE, " +
						" swk.SWK_DEN_NO, " +
						" swk.SWK_DEN_DATE, " +
						" swk.SWK_DEN_NO, " +
						" swk.SWK_GYO_NO, " +
						" swk.SWK_GYO_TEK, " +
						" swk.SWK_DEP_CODE, " +
						" swk.SWK_KMK_CODE, " +
						" swk.SWK_HKM_CODE, " +
						" swk.SWK_UKM_CODE, " +
						" swk.SWK_TRI_CODE, " +
						" swk.SWK_EMP_CODE, " +
						" swk.SWK_KNR_CODE_1, " +
						" swk.SWK_KNR_CODE_2, " +
						" swk.SWK_KNR_CODE_3, " +
						" swk.SWK_KNR_CODE_4, " +
						" swk.SWK_KNR_CODE_5, " +
						" swk.SWK_KNR_CODE_6, " +
						" swk.SWK_CUR_CODE, " +
						" swk.SWK_DC_KBN, " +
						" swk.SWK_KIN, " +
						" swk.SWK_IN_KIN, " +
						" swk.DEN_SYU_CODE, " +
						" swk.SWK_BOOK_NO, " +
						" swk.SWK_ADJ_KBN " +
					" FROM " +
					    " SWK_DTL swk " +
					    " INNER JOIN KMK_MST kmk " +
						" ON	swk.KAI_CODE = kmk.KAI_CODE " +
						" AND 	swk.SWK_KMK_CODE = kmk.KMK_CODE " +
						" AND 	kmk.KMK_CNT_GL <> '04' " +

						// GL科目区分：資金科目の仕訳が存在している伝票 が対象
						" INNER JOIN ( " +
							" SELECT   swk2.KAI_CODE, swk2.SWK_DEN_DATE, swk2.SWK_DEN_NO, swk2.SWK_BOOK_NO, swk2.SWK_ADJ_KBN " +
							" FROM SWK_DTL swk2 " +
							" INNER JOIN KMK_MST kmk2 " +
							" ON  swk2.KAI_CODE     = kmk2.KAI_CODE " +
							" AND swk2.SWK_KMK_CODE = kmk2.KMK_CODE " +
							" AND kmk2.KMK_CNT_GL   = '04' " +
							" GROUP BY swk2.KAI_CODE, swk2.SWK_DEN_DATE, swk2.SWK_DEN_NO, swk2.SWK_BOOK_NO, swk2.SWK_ADJ_KBN " +
						" ) swk2" + 
						" ON  swk.KAI_CODE     = swk2.KAI_CODE " +
						" AND swk.SWK_DEN_DATE = swk2.SWK_DEN_DATE " +
						" AND swk.SWK_DEN_NO   = swk2.SWK_DEN_NO " +
						" AND swk.SWK_BOOK_NO  = swk2.SWK_BOOK_NO " +
						" AND swk.SWK_ADJ_KBN  = swk2.SWK_ADJ_KBN " +
						sqlSwkWhere + 
						" AND NVL(swk.SWK_KIN ,0) <> 0 ";	
				DBUtil.execute(conn, sql);
	
				// 10.2 債務の消込伝票だった場合、相手科目を未払から債務計上時の相手に変更
				// ※ 【債務計上伝票】費用 / 未払の費用側まで遡る
				// 出金伝票の未払科目を削除
				sql =
					" DELETE " +
					" FROM	CFL_DTL " +
					" WHERE EXISTS ( " +
						" SELECT	1 " +
						" FROM " +
							" AP_ZAN apzan " +
	        			" WHERE	CFL_DTL.KAI_CODE = apzan.KAI_CODE " +
	        			" AND	CFL_DTL.SWK_DEN_NO = apzan.ZAN_SIHA_DEN_NO " +
	            		// TODO 以下の条件不要。
	            		// 直前のINSERT対象の抽出条件と重複&&出金伝票・債務計上伝票の紐付けはAP_ZANのみで可能
	        			" AND	CFL_DTL.SWK_KMK_CODE = apzan.ZAN_KMK_CODE " +
	        			" AND	NVL(CFL_DTL.SWK_HKM_CODE, ' ') = NVL(apzan.ZAN_HKM_CODE, ' ') " +
	        			" AND	NVL(CFL_DTL.SWK_UKM_CODE, ' ') = NVL(apzan.ZAN_UKM_CODE, ' ') " +

	        			" AND apzan.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +  // レスポンス向上の為、条件追加

        			" ) " +
	        		" AND	EXISTS ( " +
	        			" SELECT	1 " +
	        			" FROM " +
	        				" SWK_DTL swk " +
							" INNER JOIN KMK_MST kmk " +
							" ON	swk.KAI_CODE = kmk.KAI_CODE " +
							" AND 	swk.SWK_KMK_CODE = kmk.KMK_CODE " +
							" AND 	kmk.KMK_CNT_GL = '04' " +
	        			sqlSwkWhere +
	        				" AND	CFL_DTL.KAI_CODE = swk.KAI_CODE " +
	        				" AND	CFL_DTL.SWK_DEN_DATE = swk.SWK_DEN_DATE " +
	        				" AND	CFL_DTL.SWK_DEN_NO = swk.SWK_DEN_NO " +
	        		" ) " +
				" AND KAI_CODE = " + SQLUtil.getParam(company.getCode()); // レスポンス向上の為、条件追加
	
				// 条件追加：-- 一部支払（分割）は対象外
				sql +=
					" AND NOT EXISTS (  " +
					"     SELECT 1  FROM ( " +
					"         SELECT " +
					"             apzan1.KAI_CODE" +
					"           , apzan1.ZAN_JSK_DATE" +
					"           , apzan1.ZAN_SIHA_DEN_NO" +
					"         FROM AP_ZAN apzan1" +
					"             INNER JOIN ( " +
					"                 SELECT" +
					"                     KAI_CODE" +
					"                   , ZAN_DEN_DATE  " +
					"                   , ZAN_DEN_NO" +
					"                   , COUNT(*)" +
					"                 FROM AP_ZAN" +
					"                 GROUP BY " +
					"                     KAI_CODE" +
					"                   , ZAN_DEN_DATE  " +
					"                   , ZAN_DEN_NO" +
					"                 HAVING COUNT(*) <> 1" +
					"             ) apzan3" +
					"             ON  apzan1.KAI_CODE = apzan3.KAI_CODE " +
					"             AND apzan1.ZAN_DEN_DATE = apzan3.ZAN_DEN_DATE " +
					"             AND apzan1.ZAN_DEN_NO = apzan3.ZAN_DEN_NO " +
					"         WHERE apzan1.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"         AND   apzan1.ZAN_SIHA_DEN_NO IN ( " +
					"             SELECT SWK_DEN_NO " +
					"             FROM T_DAILY_CLOSE " +
					"             WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"         ) " +
					"     ) apzan2" +
					"     WHERE apzan2.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"     AND   apzan2.ZAN_JSK_DATE <= " + SQLUtil.getYYYYMMDDParam(date) + 
					"     AND   CFL_DTL.KAI_CODE   = apzan2.KAI_CODE  " +
					"     AND   CFL_DTL.SWK_DEN_NO = apzan2.ZAN_SIHA_DEN_NO " +
					" )  " +
					" AND KAI_CODE = " + SQLUtil.getParam(company.getCode());

				DBUtil.execute(conn, sql);
	
				// 債務計上の未払科目以外を登録
				sql =
					" INSERT INTO CFL_DTL ( " +
						" KAI_CODE, " +
						" CFL_SWK_DEN_DATE, " +
						" CFL_SWK_DEN_NO, " +
						" SWK_DEN_DATE, " +
						" SWK_DEN_NO, " +
						" SWK_GYO_NO, " +
						" SWK_GYO_TEK, " +
						" SWK_DEP_CODE, " +
						" SWK_KMK_CODE, " +
						" SWK_HKM_CODE, " +
						" SWK_UKM_CODE, " +
						" SWK_TRI_CODE, " +
						" SWK_EMP_CODE, " +
						" SWK_KNR_CODE_1, " +
						" SWK_KNR_CODE_2, " +
						" SWK_KNR_CODE_3, " +
						" SWK_KNR_CODE_4, " +
						" SWK_KNR_CODE_5, " +
						" SWK_KNR_CODE_6, " +
						" SWK_CUR_CODE, " +
						" SWK_DC_KBN, " +
						" SWK_KIN, " +
						" SWK_IN_KIN, " +
						" DEN_SYU_CODE, " +
						" SWK_BOOK_NO, " +
						" SWK_ADJ_KBN " +
					" ) " +
					" SELECT /*+RULE*/ " +
						" swk2.KAI_CODE, " +
						" apzan2.ZAN_JSK_DATE, " +
						" apzan2.ZAN_SIHA_DEN_NO, " + // 支払伝票番号
						" swk2.SWK_DEN_DATE, " +
						" swk2.SWK_DEN_NO, " + // 債務計上伝票番号
						" swk2.SWK_GYO_NO, " +
						" swk2.SWK_GYO_TEK, " +
						" swk2.SWK_DEP_CODE, " +
						" swk2.SWK_KMK_CODE, " +
						" swk2.SWK_HKM_CODE, " +
						" swk2.SWK_UKM_CODE, " +
						" swk2.SWK_TRI_CODE, " +
						" swk2.SWK_EMP_CODE, " +
						" swk2.SWK_KNR_CODE_1, " +
						" swk2.SWK_KNR_CODE_2, " +
						" swk2.SWK_KNR_CODE_3, " +
						" swk2.SWK_KNR_CODE_4, " +
						" swk2.SWK_KNR_CODE_5, " +
						" swk2.SWK_KNR_CODE_6, " +
						" swk2.SWK_CUR_CODE, " +
						" swk2.SWK_DC_KBN, " +
						" swk2.SWK_KIN, " +
						" swk2.SWK_IN_KIN, " +
						" swk2.DEN_SYU_CODE, " +
						" swk2.SWK_BOOK_NO, " +
						" swk2.SWK_ADJ_KBN " +
					" FROM " +
					    " SWK_DTL swk2 " +
					    " INNER JOIN KMK_MST kmk " +
						" ON	swk2.KAI_CODE = kmk.KAI_CODE " +
						" AND 	swk2.SWK_KMK_CODE = kmk.KMK_CODE " +
						" INNER JOIN AP_ZAN apzan2 " +
						" ON	swk2.KAI_CODE = apzan2.KAI_CODE " +
						" AND	swk2.SWK_DEN_NO = apzan2.ZAN_DEN_NO " +
						" AND	NOT (swk2.SWK_KMK_CODE = apzan2.ZAN_KMK_CODE " +
						" AND	NVL(swk2.SWK_HKM_CODE, ' ') = NVL(apzan2.ZAN_HKM_CODE, ' ') " +
						" AND	NVL(swk2.SWK_UKM_CODE, ' ') = NVL(apzan2.ZAN_UKM_CODE, ' ') " +
						" AND 	swk2.SWK_AUTO_KBN = 1) " +

						// 支払伝票作成済 の伝票が対象
						" INNER JOIN ( " +
							"SELECT DISTINCT apzan.KAI_CODE, apzan.ZAN_DEN_NO " +
							" FROM SWK_DTL swk " +
							" INNER JOIN AP_ZAN apzan " +
							" ON  swk.KAI_CODE   = apzan.KAI_CODE " +
							" AND swk.SWK_DEN_NO = apzan.ZAN_SIHA_DEN_NO " +
							// GL科目区分：資金科目の仕訳が存在している伝票 が対象
							" INNER JOIN ( " +
								" SELECT   swk3.KAI_CODE, swk3.SWK_DEN_DATE, swk3.SWK_DEN_NO, swk3.SWK_BOOK_NO, swk3.SWK_ADJ_KBN " +
								" FROM SWK_DTL swk3 " +
								" INNER JOIN KMK_MST kmk3 " +
								" ON  swk3.KAI_CODE     = kmk3.KAI_CODE " +
								" AND swk3.SWK_KMK_CODE = kmk3.KMK_CODE " +
								" AND kmk3.KMK_CNT_GL   = '04' " +
								" GROUP BY swk3.KAI_CODE, swk3.SWK_DEN_DATE, swk3.SWK_DEN_NO, swk3.SWK_BOOK_NO, swk3.SWK_ADJ_KBN " +
							" ) swk3" + 
							" ON  swk.KAI_CODE     = swk3.KAI_CODE " +
							" AND swk.SWK_DEN_DATE = swk3.SWK_DEN_DATE " +
							" AND swk.SWK_DEN_NO   = swk3.SWK_DEN_NO " +
							" AND swk.SWK_BOOK_NO  = swk3.SWK_BOOK_NO " +
							" AND swk.SWK_ADJ_KBN  = swk3.SWK_ADJ_KBN " +

							sqlSwkWhere +
						" ) swk" + 
						" ON  swk2.KAI_CODE   = swk.KAI_CODE " +
						" AND swk2.SWK_DEN_NO = swk.ZAN_DEN_NO ";
				// 条件追加：-- 一部支払（分割）は対象外
				sql +=
					" INNER JOIN ( " +
					"     SELECT  " +
					"         apzan2.KAI_CODE " +
					"       , apzan2.ZAN_DEN_DATE " +
					"       , apzan2.ZAN_DEN_NO " +
					"     FROM AP_ZAN apzan2 " +
					"     WHERE apzan2.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"     AND apzan2.ZAN_SIHA_DEN_NO IN ( " +
					"         SELECT SWK_DEN_NO " +
					"         FROM T_DAILY_CLOSE " +
					"         WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"     ) " +
					"     AND NOT EXISTS ( " +
					"         SELECT 1 FROM ( " +
					"             SELECT " +
					"                 apzan1.KAI_CODE " +
					"               , apzan1.ZAN_JSK_DATE " +
					"               , apzan1.ZAN_SIHA_DEN_NO " +
					"             FROM AP_ZAN apzan1 " +
					"             INNER JOIN ( " +
					"                 SELECT " +
					"                     KAI_CODE " +
					"                   , ZAN_DEN_DATE " +
					"                   , ZAN_DEN_NO " +
					"                   , COUNT(*) " +
					"                 FROM AP_ZAN " +
					"                 GROUP BY  " +
					"                     KAI_CODE " +
					"                   , ZAN_DEN_DATE " +
					"                   , ZAN_DEN_NO " +
					"                 HAVING COUNT(*) <> 1 " +
					"             ) apzan3 " +
					"             ON  apzan1.KAI_CODE = apzan3.KAI_CODE " +
					"             AND apzan1.ZAN_DEN_DATE = apzan3.ZAN_DEN_DATE " +
					"             AND apzan1.ZAN_DEN_NO = apzan3.ZAN_DEN_NO " +
					"             WHERE apzan1.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"             AND apzan1.ZAN_SIHA_DEN_NO IN ( " +
					"                 SELECT SWK_DEN_NO " +
					"                 FROM T_DAILY_CLOSE " +
					"                 WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"             ) " +
					"         ) apzan4 " +
					"         WHERE apzan2.KAI_CODE = apzan4.KAI_CODE " +
					"         AND   apzan2.ZAN_JSK_DATE = apzan4.ZAN_JSK_DATE " +
					"         AND   apzan2.ZAN_SIHA_DEN_NO = apzan4.ZAN_SIHA_DEN_NO " +
					"     ) " +
					" ) apzan3 " +
					" ON  apzan3.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					" AND apzan3.ZAN_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) + 
					" AND swk2.KAI_CODE   = apzan3.KAI_CODE " +
					" AND swk2.SWK_DEN_NO = apzan3.ZAN_DEN_NO ";
				
				DBUtil.execute(conn, sql);
	
				// 10.3 社員支払の消込伝票だった場合、相手科目を未払から社員支払時の相手に変更
				// ※ 【社員支払伝票】費用 / 未払の費用側まで遡る
				sql =
					" DELETE " +
					" FROM	CFL_DTL " +
					" WHERE EXISTS ( " +
						" SELECT	1 " +
						" FROM " +
							" AP_SWK_HDR hdr " +
	        			" WHERE	CFL_DTL.KAI_CODE = hdr.KAI_CODE " +
	        			" AND	CFL_DTL.SWK_DEN_NO = hdr.SWK_SIHA_DEN_NO " +
	        			" AND	CFL_DTL.SWK_KMK_CODE = hdr.SWK_KMK_CODE " +
	        			" AND	NVL(CFL_DTL.SWK_HKM_CODE, ' ') = NVL(hdr.SWK_HKM_CODE, ' ') " +
	        			" AND	NVL(CFL_DTL.SWK_UKM_CODE, ' ') = NVL(hdr.SWK_UKM_CODE, ' ') " +
						" AND   hdr.KAI_CODE = " + SQLUtil.getParam(company.getCode()) + // レスポンス向上の為、条件追加
	        		" ) " +
        		" AND KAI_CODE = " + SQLUtil.getParam(company.getCode()); // レスポンス向上の為、条件追加
				// -- 現預金科目を含む伝票を対象とする条件を追加				
				sql +=
					" AND EXISTS ( " +
					"     SELECT 1 FROM  SWK_DTL swk  " +
					"         INNER JOIN KMK_MST kmk  " +
					"         ON  swk.KAI_CODE = kmk.KAI_CODE  " +
					"         AND swk.SWK_KMK_CODE = kmk.KMK_CODE  " +
					"         AND kmk.KMK_CNT_GL = '04'  " +
					"     WHERE swk.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"     AND   swk.SWK_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) +
					"     AND   swk.SWK_UPD_KBN = 3 " +
					"     AND   swk.SWK_DEN_NO IN ( " +
					"         SELECT SWK_DEN_NO " +
					"         FROM T_DAILY_CLOSE" +
					"         WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"     ) " +
					"     AND CFL_DTL.KAI_CODE = swk.KAI_CODE  " +
					"     AND CFL_DTL.SWK_DEN_DATE = swk.SWK_DEN_DATE  " +
					"     AND CFL_DTL.SWK_DEN_NO = swk.SWK_DEN_NO  " +
					" ) " +
					" AND KAI_CODE = " + SQLUtil.getParam(company.getCode());
				
				DBUtil.execute(conn, sql);
				
				// 社員支払の未払科目以外を登録
				sql =
					" INSERT INTO CFL_DTL ( " +
						" KAI_CODE, " +
						" CFL_SWK_DEN_DATE, " +
						" CFL_SWK_DEN_NO, " +
						" SWK_DEN_DATE, " +
						" SWK_DEN_NO, " +
						" SWK_GYO_NO, " +
						" SWK_GYO_TEK, " +
						" SWK_DEP_CODE, " +
						" SWK_KMK_CODE, " +
						" SWK_HKM_CODE, " +
						" SWK_UKM_CODE, " +
						" SWK_TRI_CODE, " +
						" SWK_EMP_CODE, " +
						" SWK_KNR_CODE_1, " +
						" SWK_KNR_CODE_2, " +
						" SWK_KNR_CODE_3, " +
						" SWK_KNR_CODE_4, " +
						" SWK_KNR_CODE_5, " +
						" SWK_KNR_CODE_6, " +
						" SWK_CUR_CODE, " +
						" SWK_DC_KBN, " +
						" SWK_KIN, " +
						" SWK_IN_KIN, " +
						" DEN_SYU_CODE, " +
						" SWK_BOOK_NO, " +
						" SWK_ADJ_KBN " +
					" ) " +
					" SELECT /*+RULE*/ " +
						" swk2.KAI_CODE, " +
						" hdr2.SWK_JSK_DATE, " +
						" hdr2.SWK_SIHA_DEN_NO, " + // 支払伝票番号
						" swk2.SWK_DEN_DATE, " +
						" swk2.SWK_DEN_NO, " + // 債務計上伝票番号
						" swk2.SWK_GYO_NO, " +
						" swk2.SWK_GYO_TEK, " +
						" swk2.SWK_DEP_CODE, " +
						" swk2.SWK_KMK_CODE, " +
						" swk2.SWK_HKM_CODE, " +
						" swk2.SWK_UKM_CODE, " +
						" swk2.SWK_TRI_CODE, " +
						" swk2.SWK_EMP_CODE, " +
						" swk2.SWK_KNR_CODE_1, " +
						" swk2.SWK_KNR_CODE_2, " +
						" swk2.SWK_KNR_CODE_3, " +
						" swk2.SWK_KNR_CODE_4, " +
						" swk2.SWK_KNR_CODE_5, " +
						" swk2.SWK_KNR_CODE_6, " +
						" swk2.SWK_CUR_CODE, " +
						" swk2.SWK_DC_KBN, " +
						" swk2.SWK_KIN, " +
						" swk2.SWK_IN_KIN, " +
						" swk2.DEN_SYU_CODE, " +
						" swk2.SWK_BOOK_NO, " +
						" swk2.SWK_ADJ_KBN " +
					" FROM " +
					    " SWK_DTL swk2 " +
					    " INNER JOIN KMK_MST kmk " +
						" ON	swk2.KAI_CODE = kmk.KAI_CODE " +
						" AND 	swk2.SWK_KMK_CODE = kmk.KMK_CODE " +
						" INNER JOIN AP_SWK_HDR hdr2 " +
						" ON	swk2.KAI_CODE = hdr2.KAI_CODE " +
						" AND	swk2.SWK_DEN_NO = hdr2.SWK_DEN_NO " +
						" AND	NOT (swk2.SWK_KMK_CODE = hdr2.SWK_KMK_CODE " +
						" AND	NVL(swk2.SWK_HKM_CODE, ' ') = NVL(hdr2.SWK_HKM_CODE, ' ') " +
						" AND	NVL(swk2.SWK_UKM_CODE, ' ') = NVL(hdr2.SWK_UKM_CODE, ' ') " +
						" ) " +
						// 今回の日次更新対象のうち、社員支払伝票作成済 が対象
						" INNER JOIN ( " +
							" SELECT   hdr.KAI_CODE, hdr.SWK_DEN_NO " +
							" FROM SWK_DTL swk " +
							" INNER JOIN AP_SWK_HDR hdr " +
							" ON  swk.KAI_CODE   = hdr.KAI_CODE " +
							" AND swk.SWK_DEN_NO = hdr.SWK_SIHA_DEN_NO " +
							
							// 現預金科目を含む伝票を対象とする条件を追加
							"            INNER JOIN ( " + 
							"                SELECT " + 
							"                  swk3.KAI_CODE " + 
							"                , swk3.SWK_DEN_DATE " + 
							"                , swk3.SWK_DEN_NO " + 
							"                , swk3.SWK_BOOK_NO " + 
							"                , swk3.SWK_ADJ_KBN " + 
							"                FROM SWK_DTL swk3 " + 
							"                    INNER JOIN KMK_MST kmk3 " + 
							"                    ON  swk3.KAI_CODE     = kmk3.KAI_CODE " + 
							"                    AND swk3.SWK_KMK_CODE = kmk3.KMK_CODE " + 
							"                    AND kmk3.KMK_CNT_GL   = '04' " + 
							"                    GROUP BY swk3.KAI_CODE " + 
							"                    , swk3.SWK_DEN_DATE " + 
							"                    , swk3.SWK_DEN_NO " + 
							"                    , swk3.SWK_BOOK_NO " + 
							"                    , swk3.SWK_ADJ_KBN " + 
							"            ) swk3 " + 
							"            ON  swk.KAI_CODE     = swk3.KAI_CODE " + 
							"            AND swk.SWK_DEN_DATE = swk3.SWK_DEN_DATE " + 
							"            AND swk.SWK_DEN_NO   = swk3.SWK_DEN_NO " + 
							"            AND swk.SWK_BOOK_NO  = swk3.SWK_BOOK_NO " + 
							"            AND swk.SWK_ADJ_KBN  = swk3.SWK_ADJ_KBN " + 
							sqlSwkWhere +
							" GROUP BY hdr.KAI_CODE, hdr.SWK_DEN_NO " +
						" ) hdr" + 
						" ON  swk2.KAI_CODE   = hdr.KAI_CODE " +
						" AND swk2.SWK_DEN_NO = hdr.SWK_DEN_NO ";
	
				DBUtil.execute(conn, sql);
	
				// -- 10.4 債権の消込伝票だった場合、相手科目を未収から債権計上時の相手に変更
				// -- ※ 【債権計上伝票】未収 / 収益の収益側まで遡る
				// -- 入金伝票の未収科目を削除
				sql =
				" DELETE FROM CFL_DTL " + 
				" WHERE  EXISTS" + 
				" (SELECT 1" + 
				"       FROM   AR_ZAN arzan" + 
				"       WHERE  CFL_DTL.KAI_CODE = arzan.KAI_CODE" + 
				"       AND    CFL_DTL.SWK_DEN_NO = arzan.ZAN_KESI_DEN_NO" + 
				"       AND    CFL_DTL.SWK_GYO_NO = arzan.ZAN_KESI_GYO_NO" + 
				"       AND    CFL_DTL.SWK_KMK_CODE = arzan.ZAN_KMK_CODE" + 
				"       AND    NVL(CFL_DTL.SWK_HKM_CODE, ' ') = NVL(arzan.ZAN_HKM_CODE, ' ')" + 
				"       AND    NVL(CFL_DTL.SWK_UKM_CODE, ' ') = NVL(arzan.ZAN_UKM_CODE, ' ')" + 
				"       AND    arzan.KAI_CODE = " + SQLUtil.getParam(company.getCode()) + " )" + 
				" AND    EXISTS" + 
				" (SELECT 1" + 
				"       FROM   SWK_DTL swk" + 
				"       INNER  JOIN KMK_MST kmk ON swk.KAI_CODE = kmk.KAI_CODE" + 
				"                              AND swk.SWK_KMK_CODE = kmk.KMK_CODE" + 
				"                              AND kmk.KMK_CNT_GL = '04'" + 
				"       WHERE  swk.KAI_CODE = " + SQLUtil.getParam(company.getCode()) + 
				"       AND    swk.SWK_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) + 
				"       AND    swk.SWK_UPD_KBN = 3" + 
				"       AND swk.SWK_DEN_NO IN ( " + 
				"             SELECT SWK_DEN_NO " + 
				"             FROM T_DAILY_CLOSE" + 
				"             WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +  
				"       ) " + 
				"       AND    CFL_DTL.KAI_CODE = swk.KAI_CODE" + 
				"       AND    CFL_DTL.SWK_DEN_DATE = swk.SWK_DEN_DATE" + 
				"       AND    CFL_DTL.SWK_DEN_NO = swk.SWK_DEN_NO)" + 
				// -- 一部入金（分割）は対象外" + 
				" AND    EXISTS" + 
				" (SELECT 1" + 
				"         FROM (" + 
				"                 SELECT" + 
				"                     kesi.KAI_CODE" + 
				"                     , sei.ZAN_SEI_DEN_DATE" + 
				"                     , sei.ZAN_SEI_DEN_NO" + 
				"                     , kesi.ZAN_KESI_DEN_DATE" + 
				"                     , kesi.ZAN_KESI_DEN_NO" + 
				"                     , kesi.ZAN_KESI_GYO_NO" + 
				"                     , sei.ZAN_SEI_IN_KIN" + 
				"                     , kesi.ZAN_KESI_IN_KIN" + 
				"                 FROM AR_ZAN kesi" + 
				"                     INNER JOIN AR_ZAN sei" + 
				"                         ON kesi.KAI_CODE = sei.KAI_CODE" + 
				"                         AND kesi.ZAN_SEI_DEN_DATE = sei.ZAN_SEI_DEN_DATE" + 
				"                         AND kesi.ZAN_SEI_DEN_NO = sei.ZAN_SEI_DEN_NO" + 
				"                         AND kesi.ZAN_KESI_IN_KIN = sei.ZAN_SEI_IN_KIN" + 
				"                         AND sei.ZAN_DATA_KBN = '31'" + 
				"                 WHERE " + 
				"                     kesi.KAI_CODE = " + SQLUtil.getParam(company.getCode()) + 
				"                     AND kesi.ZAN_DATA_KBN <> '31'    " + 
				"                 ) arzan2" + 
				"       WHERE  CFL_DTL.KAI_CODE = arzan2.KAI_CODE" + 
				"       AND    CFL_DTL.SWK_DEN_DATE = arzan2.ZAN_KESI_DEN_DATE" + 
				"       AND    CFL_DTL.SWK_DEN_NO = arzan2.ZAN_KESI_DEN_NO " +
				"       AND    CFL_DTL.SWK_GYO_NO = arzan2.ZAN_KESI_GYO_NO)";
				DBUtil.execute(conn, sql);

				// -- 債権計上の未収科目以外を登録
				sql =
				" INSERT INTO CFL_DTL ( " +
				"    KAI_CODE " +
				" ,  CFL_SWK_DEN_DATE " +
				" ,  CFL_SWK_DEN_NO " +
				" ,  SWK_DEN_DATE " +
				" ,  SWK_DEN_NO " +
				" ,  SWK_GYO_NO " +
				" ,  SWK_GYO_TEK " +
				" ,  SWK_DEP_CODE " +
				" ,  SWK_KMK_CODE " +
				" ,  SWK_HKM_CODE " +
				" ,  SWK_UKM_CODE " +
				" ,  SWK_TRI_CODE " +
				" ,  SWK_EMP_CODE " +
				" ,  SWK_KNR_CODE_1 " +
				" ,  SWK_KNR_CODE_2 " +
				" ,  SWK_KNR_CODE_3 " +
				" ,  SWK_KNR_CODE_4 " +
				" ,  SWK_KNR_CODE_5 " +
				" ,  SWK_KNR_CODE_6 " +
				" ,  SWK_CUR_CODE " +
				" ,  SWK_DC_KBN " +
				" ,  SWK_KIN " +
				" ,  SWK_IN_KIN " +
				" ,  DEN_SYU_CODE " +
				" ,  SWK_BOOK_NO " +
				" ,  SWK_ADJ_KBN   " +
				" )   " +
				" SELECT " +
				"    swk2.KAI_CODE " +
				" ,  arzan2.ZAN_KESI_DEN_DATE " +
				" ,  arzan2.ZAN_KESI_DEN_NO " +
				" ,  swk2.SWK_DEN_DATE " +
				" ,  swk2.SWK_DEN_NO " +
				" ,  swk2.SWK_GYO_NO " +
				" ,  swk2.SWK_GYO_TEK " +
				" ,  swk2.SWK_DEP_CODE " +
				" ,  swk2.SWK_KMK_CODE " +
				" ,  swk2.SWK_HKM_CODE " +
				" ,  swk2.SWK_UKM_CODE " +
				" ,  swk2.SWK_TRI_CODE " +
				" ,  swk2.SWK_EMP_CODE " +
				" ,  swk2.SWK_KNR_CODE_1 " +
				" ,  swk2.SWK_KNR_CODE_2 " +
				" ,  swk2.SWK_KNR_CODE_3 " +
				" ,  swk2.SWK_KNR_CODE_4 " +
				" ,  swk2.SWK_KNR_CODE_5 " +
				" ,  swk2.SWK_KNR_CODE_6 " +
				" ,  swk2.SWK_CUR_CODE " +
				" ,  swk2.SWK_DC_KBN " +
				" ,  swk2.SWK_KIN " +
				" ,  swk2.SWK_IN_KIN " +
				" ,  swk2.DEN_SYU_CODE " +
				" ,  swk2.SWK_BOOK_NO " +
				" ,  swk2.SWK_ADJ_KBN " +
				" FROM   SWK_DTL swk2 " +
				" INNER  JOIN KMK_MST kmk ON swk2.KAI_CODE = kmk.KAI_CODE " +
				"                        AND swk2.SWK_KMK_CODE = kmk.KMK_CODE " +
				" INNER  JOIN AR_ZAN arzan2 ON swk2.KAI_CODE = arzan2.KAI_CODE " +
				"                          AND swk2.SWK_DEN_NO = arzan2.ZAN_SEI_DEN_NO " +
				"                          AND arzan2.ZAN_DATA_KBN <> '31' " +
				"                          AND NOT (swk2.SWK_KMK_CODE = arzan2.ZAN_KMK_CODE " +
				"                              AND NVL(swk2.SWK_HKM_CODE, ' ') = NVL(arzan2.ZAN_HKM_CODE, ' ') " +
				"                              AND NVL(swk2.SWK_UKM_CODE, ' ') = NVL(arzan2.ZAN_UKM_CODE, ' ') " +
				"                              AND swk2.SWK_AUTO_KBN = 1) " +
				" INNER JOIN (  " +
				" SELECT DISTINCT " +
				"   arzan.KAI_CODE " +
				" , arzan.ZAN_SEI_DEN_NO   " +
				" FROM SWK_DTL swk   " +
				"     INNER JOIN AR_ZAN arzan   " +
				"     ON  swk.KAI_CODE   = arzan.KAI_CODE   " +
				"     AND swk.SWK_DEN_NO = arzan.ZAN_KESI_DEN_NO   " +
				"     INNER JOIN (   " +
				"         SELECT " +
				"           swk3.KAI_CODE " +
				"         , swk3.SWK_DEN_DATE " +
				"         , swk3.SWK_DEN_NO " +
				"         , swk3.SWK_BOOK_NO " +
				"         , swk3.SWK_ADJ_KBN   " +
				"         FROM SWK_DTL swk3   " +
				"             INNER JOIN KMK_MST kmk3   " +
				"             ON  swk3.KAI_CODE     = kmk3.KAI_CODE   " +
				"             AND swk3.SWK_KMK_CODE = kmk3.KMK_CODE   " +
				"             AND kmk3.KMK_CNT_GL   = '04'   " +
				"             GROUP BY swk3.KAI_CODE " +
				"             , swk3.SWK_DEN_DATE " +
				"             , swk3.SWK_DEN_NO " +
				"             , swk3.SWK_BOOK_NO " +
				"             , swk3.SWK_ADJ_KBN   " +
				"     ) swk3  " +
				"     ON  swk.KAI_CODE     = swk3.KAI_CODE   " +
				"     AND swk.SWK_DEN_DATE = swk3.SWK_DEN_DATE   " +
				"     AND swk.SWK_DEN_NO   = swk3.SWK_DEN_NO   " +
				"     AND swk.SWK_BOOK_NO  = swk3.SWK_BOOK_NO   " +
				"     AND swk.SWK_ADJ_KBN  = swk3.SWK_ADJ_KBN   " +
				" WHERE swk.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				" AND    swk.SWK_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) +
				"  AND    swk.SWK_UPD_KBN = 3  " +
				" AND swk.SWK_DEN_NO IN (  " +
				"     SELECT SWK_DEN_NO  " +
				"     FROM T_DAILY_CLOSE " +
				"     WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				" )  " +
				" ) swk  " +
				" ON  swk2.KAI_CODE   = swk.KAI_CODE   " +
				" AND swk2.SWK_DEN_NO = swk.ZAN_SEI_DEN_NO  " +
				" INNER JOIN (  " +
				"             SELECT " +
				"                 kesi.KAI_CODE " +
				"                 , sei.ZAN_SEI_DEN_DATE " +
				"                 , sei.ZAN_SEI_DEN_NO " +
				"                 , kesi.ZAN_KESI_DEN_DATE " +
				"                 , kesi.ZAN_KESI_DEN_NO " +
				"                 , sei.ZAN_SEI_IN_KIN " +
				"                 , kesi.ZAN_KESI_IN_KIN " +
				"             FROM AR_ZAN kesi " +
				"                 INNER JOIN AR_ZAN sei " +
				"                     ON kesi.KAI_CODE = sei.KAI_CODE " +
				"                     AND kesi.ZAN_SEI_DEN_DATE = sei.ZAN_SEI_DEN_DATE " +
				"                     AND kesi.ZAN_SEI_DEN_NO = sei.ZAN_SEI_DEN_NO " +
				"                     AND kesi.ZAN_KESI_IN_KIN = sei.ZAN_SEI_IN_KIN " +
				"                     AND sei.ZAN_DATA_KBN = '31' " +
				"             WHERE  " +
				"                 kesi.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"                 AND kesi.ZAN_DATA_KBN <> '31'     " +
				"           ) arzan3 " +
				"         ON  arzan3.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"         AND    arzan3.ZAN_SEI_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) +
				"         AND    swk2.KAI_CODE = arzan3.KAI_CODE " +
				"         AND    swk2.SWK_DEN_NO = arzan3.ZAN_SEI_DEN_NO ";
				DBUtil.execute(conn, sql);
				
				// -- 10.5 債権回収仮勘定による債権の消込伝票だった場合、相手科目を未収から債権計上時の相手に変更
				// -- ※ 【債権計上伝票】未収 / 収益の収益側まで遡る
				// -- 入金伝票の未収科目を削除
				sql = 
				" DELETE FROM CFL_DTL  " +
				" WHERE " +
				"     EXISTS (  " +
				"         SELECT " +
				"             1  " +
				"         FROM " +
				"             SWK_DTL swk  " +
				"             INNER JOIN AR_ZAN arzan  " +
				"                 ON swk.KAI_CODE = arzan.KAI_CODE  " +
				"                 AND swk.SWK_DEN_DATE = arzan.ZAN_KESI_DEN_DATE  " +
				"                 AND swk.SWK_DEN_NO = arzan.ZAN_KESI_DEN_NO  " +
				"             INNER JOIN AR_KAI_DAT arkai  " +
				"                 ON arzan.KAI_CODE = arkai.KAI_CODE  " +
				"                 AND arzan.ZAN_KESI_DEN_DATE = arkai.KAI_KESI_DEN_DATE  " +
				"                 AND arzan.ZAN_KESI_DEN_NO = arkai.KAI_KESI_DEN_NO  " +
				"                 AND arzan.ZAN_KESI_IN_KIN = arkai.KAI_IN_KIN  " +
				// -- 一部入金（分割）は対象外
				"             INNER JOIN (  " +
				"                         SELECT " +
				"                             kesi.KAI_CODE " +
				"                             , sei.ZAN_SEI_DEN_DATE " +
				"                             , sei.ZAN_SEI_DEN_NO " +
				"                             , kesi.ZAN_KESI_DEN_DATE " +
				"                             , kesi.ZAN_KESI_DEN_NO " +
				"                             , sei.ZAN_SEI_IN_KIN " +
				"                             , kesi.ZAN_KESI_IN_KIN " +
				"                         FROM " +
				"                             AR_ZAN kesi " +
				"                             INNER JOIN AR_ZAN sei " +
				"                                 ON kesi.KAI_CODE = sei.KAI_CODE " +
				"                                 AND kesi.ZAN_SEI_DEN_DATE = sei.ZAN_SEI_DEN_DATE " +
				"                                 AND kesi.ZAN_SEI_DEN_NO = sei.ZAN_SEI_DEN_NO " +
				"                                 AND kesi.ZAN_KESI_IN_KIN = sei.ZAN_SEI_IN_KIN " +
				"                                 AND sei.ZAN_DATA_KBN = '31' " +
				"                         WHERE  " +
				"                             kesi.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"                             AND kesi.ZAN_DATA_KBN <> '31'     " +
				"             ) kesi  " +
				"                 ON arkai.KAI_CODE = kesi.KAI_CODE  " +
				"                 AND arkai.KAI_KESI_DEN_DATE = kesi.ZAN_KESI_DEN_DATE  " +
				"                 AND arkai.KAI_KESI_DEN_NO = kesi.ZAN_KESI_DEN_NO  " +
				"         WHERE " +
				"             CFL_DTL.KAI_CODE = arkai.KAI_CODE  " +
				"             AND CFL_DTL.SWK_DEN_DATE = arkai.KAI_DEN_DATE  " +
				"             AND CFL_DTL.SWK_DEN_NO = arkai.KAI_DEN_NO  " +
				"             AND arkai.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"             AND swk.SWK_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) +
				"             AND swk.SWK_UPD_KBN = 3  " +
				"             AND swk.SWK_DEN_NO IN (  " +
				"                 SELECT " +
				"                     SWK_DEN_NO  " +
				"                 FROM " +
				"                     T_DAILY_CLOSE  " +
				"                 WHERE " +
				"                     KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"             ) " +
				"     ) ";
				DBUtil.execute(conn, sql);

				// -- 債権計上の未収科目以外を登録
				sql = 
				" INSERT INTO CFL_DTL ( " +
				"    KAI_CODE " +
				" ,  CFL_SWK_DEN_DATE " +
				" ,  CFL_SWK_DEN_NO " +
				" ,  SWK_DEN_DATE " +
				" ,  SWK_DEN_NO " +
				" ,  SWK_GYO_NO " +
				" ,  SWK_GYO_TEK " +
				" ,  SWK_DEP_CODE " +
				" ,  SWK_KMK_CODE " +
				" ,  SWK_HKM_CODE " +
				" ,  SWK_UKM_CODE " +
				" ,  SWK_TRI_CODE " +
				" ,  SWK_EMP_CODE " +
				" ,  SWK_KNR_CODE_1 " +
				" ,  SWK_KNR_CODE_2 " +
				" ,  SWK_KNR_CODE_3 " +
				" ,  SWK_KNR_CODE_4 " +
				" ,  SWK_KNR_CODE_5 " +
				" ,  SWK_KNR_CODE_6 " +
				" ,  SWK_CUR_CODE " +
				" ,  SWK_DC_KBN " +
				" ,  SWK_KIN " +
				" ,  SWK_IN_KIN " +
				" ,  DEN_SYU_CODE " +
				" ,  SWK_BOOK_NO " +
				" ,  SWK_ADJ_KBN   " +
				" )   " +
				" SELECT " +
				"    swk2.KAI_CODE " +
				" ,  kai.KAI_DEN_DATE " +
				" ,  kai.KAI_DEN_NO " +
				" ,  swk2.SWK_DEN_DATE " +
				" ,  swk2.SWK_DEN_NO " +
				" ,  swk2.SWK_GYO_NO " +
				" ,  swk2.SWK_GYO_TEK " +
				" ,  swk2.SWK_DEP_CODE " +
				" ,  swk2.SWK_KMK_CODE " +
				" ,  swk2.SWK_HKM_CODE " +
				" ,  swk2.SWK_UKM_CODE " +
				" ,  swk2.SWK_TRI_CODE " +
				" ,  swk2.SWK_EMP_CODE " +
				" ,  swk2.SWK_KNR_CODE_1 " +
				" ,  swk2.SWK_KNR_CODE_2 " +
				" ,  swk2.SWK_KNR_CODE_3 " +
				" ,  swk2.SWK_KNR_CODE_4 " +
				" ,  swk2.SWK_KNR_CODE_5 " +
				" ,  swk2.SWK_KNR_CODE_6 " +
				" ,  swk2.SWK_CUR_CODE " +
				" ,  swk2.SWK_DC_KBN " +
				" ,  swk2.SWK_KIN " +
				" ,  swk2.SWK_IN_KIN " +
				" ,  swk2.DEN_SYU_CODE " +
				" ,  swk2.SWK_BOOK_NO " +
				" ,  swk2.SWK_ADJ_KBN " +
				" FROM   SWK_DTL swk2 " +
				" INNER  JOIN KMK_MST kmk ON swk2.KAI_CODE = kmk.KAI_CODE " +
				"                        AND swk2.SWK_KMK_CODE = kmk.KMK_CODE " +
				" INNER  JOIN AR_ZAN arzan2 ON swk2.KAI_CODE = arzan2.KAI_CODE " +
				"                          AND swk2.SWK_DEN_NO = arzan2.ZAN_SEI_DEN_NO " +
				"                          AND arzan2.ZAN_DATA_KBN <> '31' " +
				"                          AND NOT (swk2.SWK_KMK_CODE = arzan2.ZAN_KMK_CODE " +
				"                              AND NVL(swk2.SWK_HKM_CODE, ' ') = NVL(arzan2.ZAN_HKM_CODE, ' ') " +
				"                              AND NVL(swk2.SWK_UKM_CODE, ' ') = NVL(arzan2.ZAN_UKM_CODE, ' ') " +
				"                              AND swk2.SWK_AUTO_KBN = 1) " +
				" INNER  JOIN AR_KAI_DAT kai ON arzan2.KAI_CODE = kai.KAI_CODE " +
				"                          AND arzan2.ZAN_KESI_DEN_NO = kai.KAI_KESI_DEN_NO " +
				"                          AND arzan2.ZAN_KESI_IN_KIN = kai.KAI_IN_KIN  " +
				" INNER JOIN (  " +
				"             SELECT DISTINCT " +
				"               swk3.KAI_CODE " +
				"             , swk3.SWK_DEN_DATE  " +
				"             , swk3.SWK_DEN_NO  " +
				"             , arkai.KAI_DEN_DATE  " +
				"             , arkai.KAI_DEN_NO  " +
				"             FROM SWK_DTL swk3   " +
				"                 INNER JOIN AR_KAI_DAT arkai   " +
				"                 ON  swk3.KAI_CODE   = arkai.KAI_CODE   " +
				"                 AND swk3.SWK_DEN_NO = arkai.KAI_KESI_DEN_NO   " +
				"             WHERE arkai.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"             AND    arkai.KAI_KESI_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) +
				"              AND    swk3.SWK_UPD_KBN = 3  " +
				"             AND swk3.SWK_DEN_NO IN (  " +
				"                 SELECT SWK_DEN_NO  " +
				"                 FROM T_DAILY_CLOSE " +
				"                 WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"             )  " +
				"         ) arkai  " +
				" ON  arzan2.KAI_CODE   = arkai.KAI_CODE   " +
				" AND arzan2.ZAN_KESI_DEN_NO = arkai.SWK_DEN_NO  " +
				" INNER JOIN (  " +
				"             SELECT " +
				"                 kesi.KAI_CODE " +
				"                 , sei.ZAN_SEI_DEN_DATE " +
				"                 , sei.ZAN_SEI_DEN_NO " +
				"                 , kesi.ZAN_KESI_DEN_DATE " +
				"                 , kesi.ZAN_KESI_DEN_NO " +
				"                 , sei.ZAN_SEI_IN_KIN " +
				"                 , kesi.ZAN_KESI_IN_KIN " +
				"             FROM " +
				"                 AR_ZAN kesi " +
				"                 INNER JOIN AR_ZAN sei " +
				"                     ON kesi.KAI_CODE = sei.KAI_CODE " +
				"                     AND kesi.ZAN_SEI_DEN_DATE = sei.ZAN_SEI_DEN_DATE " +
				"                     AND kesi.ZAN_SEI_DEN_NO = sei.ZAN_SEI_DEN_NO " +
				"                     AND kesi.ZAN_KESI_IN_KIN = sei.ZAN_SEI_IN_KIN " +
				"                     AND sei.ZAN_DATA_KBN = '31' " +
				"             WHERE  " +
				"                 kesi.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"                 AND kesi.ZAN_DATA_KBN <> '31'     " +
				"           ) arzan3 " +
				"         ON  arzan3.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"         AND    arzan3.ZAN_SEI_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) +
				"         AND    swk2.KAI_CODE = arzan3.KAI_CODE " +
				"         AND    swk2.SWK_DEN_NO = arzan3.ZAN_SEI_DEN_NO ";
				DBUtil.execute(conn, sql);

				// -- 債権消込の未収科目および回収仮勘定以外を登録
				sql =
				" INSERT INTO CFL_DTL ( " +
				"    KAI_CODE " +
				" ,  CFL_SWK_DEN_DATE " +
				" ,  CFL_SWK_DEN_NO " +
				" ,  SWK_DEN_DATE " +
				" ,  SWK_DEN_NO " +
				" ,  SWK_GYO_NO " +
				" ,  SWK_GYO_TEK " +
				" ,  SWK_DEP_CODE " +
				" ,  SWK_KMK_CODE " +
				" ,  SWK_HKM_CODE " +
				" ,  SWK_UKM_CODE " +
				" ,  SWK_TRI_CODE " +
				" ,  SWK_EMP_CODE " +
				" ,  SWK_KNR_CODE_1 " +
				" ,  SWK_KNR_CODE_2 " +
				" ,  SWK_KNR_CODE_3 " +
				" ,  SWK_KNR_CODE_4 " +
				" ,  SWK_KNR_CODE_5 " +
				" ,  SWK_KNR_CODE_6 " +
				" ,  SWK_CUR_CODE " +
				" ,  SWK_DC_KBN " +
				" ,  SWK_KIN " +
				" ,  SWK_IN_KIN " +
				" ,  DEN_SYU_CODE " +
				" ,  SWK_BOOK_NO " +
				" ,  SWK_ADJ_KBN   " +
				" )   " +
				" SELECT " +
				"    swk2.KAI_CODE " +
				" ,  arkai.KAI_DEN_DATE " +
				" ,  arkai.KAI_DEN_NO " +
				" ,  swk2.SWK_DEN_DATE " +
				" ,  swk2.SWK_DEN_NO " +
				" ,  swk2.SWK_GYO_NO " +
				" ,  swk2.SWK_GYO_TEK " +
				" ,  swk2.SWK_DEP_CODE " +
				" ,  swk2.SWK_KMK_CODE " +
				" ,  swk2.SWK_HKM_CODE " +
				" ,  swk2.SWK_UKM_CODE " +
				" ,  swk2.SWK_TRI_CODE " +
				" ,  swk2.SWK_EMP_CODE " +
				" ,  swk2.SWK_KNR_CODE_1 " +
				" ,  swk2.SWK_KNR_CODE_2 " +
				" ,  swk2.SWK_KNR_CODE_3 " +
				" ,  swk2.SWK_KNR_CODE_4 " +
				" ,  swk2.SWK_KNR_CODE_5 " +
				" ,  swk2.SWK_KNR_CODE_6 " +
				" ,  swk2.SWK_CUR_CODE " +
				" ,  swk2.SWK_DC_KBN " +
				" ,  swk2.SWK_KIN " +
				" ,  swk2.SWK_IN_KIN " +
				" ,  swk2.DEN_SYU_CODE " +
				" ,  swk2.SWK_BOOK_NO " +
				" ,  swk2.SWK_ADJ_KBN " +
				" FROM   SWK_DTL swk2 " +
				" INNER  JOIN KMK_MST kmk ON swk2.KAI_CODE = kmk.KAI_CODE " +
				"                        AND swk2.SWK_KMK_CODE = kmk.KMK_CODE " +
				"                        AND kmk.KMK_CODE NOT IN (" +
				"                            SELECT KMK_CODE FROM SWK_KMK_MST " +
				"                            WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"                            AND KMK_CNT = 7) " +
				" INNER JOIN (  " +
				"         SELECT DISTINCT " +
				"           swk3.KAI_CODE " +
				"         , swk3.SWK_DEN_DATE  " +
				"         , swk3.SWK_DEN_NO  " +
				"         , arkai.KAI_DEN_DATE  " +
				"         , arkai.KAI_DEN_NO  " +
				"         FROM SWK_DTL swk3   " +
				"             INNER JOIN AR_KAI_DAT arkai   " +
				"             ON  swk3.KAI_CODE   = arkai.KAI_CODE   " +
				"             AND swk3.SWK_DEN_NO = arkai.KAI_KESI_DEN_NO   " +
				"         WHERE arkai.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"         AND    arkai.KAI_KESI_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) +
				"          AND    swk3.SWK_UPD_KBN = 3  " +
				"         AND swk3.SWK_DEN_NO IN (  " +
				"             SELECT SWK_DEN_NO  " +
				"             FROM T_DAILY_CLOSE " +
				"             WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"         )  " +
				" ) arkai  " +
				" ON  swk2.KAI_CODE   = arkai.KAI_CODE   " +
				" AND swk2.SWK_DEN_NO = arkai.SWK_DEN_NO  " +
				"  " +
				" INNER JOIN (  " +
				"             SELECT " +
				"                 kesi.KAI_CODE " +
				"                 , sei.ZAN_SEI_DEN_DATE " +
				"                 , sei.ZAN_SEI_DEN_NO " +
				"                 , kesi.ZAN_KESI_DEN_DATE " +
				"                 , kesi.ZAN_KESI_DEN_NO " +
				"                 , kesi.ZAN_KESI_GYO_NO " +
				"                 , sei.ZAN_SEI_IN_KIN " +
				"                 , kesi.ZAN_KESI_IN_KIN " +
				"             FROM " +
				"                 AR_ZAN kesi " +
				"                 INNER JOIN AR_ZAN sei " +
				"                     ON kesi.KAI_CODE = sei.KAI_CODE " +
				"                     AND kesi.ZAN_SEI_DEN_DATE = sei.ZAN_SEI_DEN_DATE " +
				"                     AND kesi.ZAN_SEI_DEN_NO = sei.ZAN_SEI_DEN_NO " +
				"                     AND kesi.ZAN_KESI_IN_KIN = sei.ZAN_SEI_IN_KIN " +
				"                     AND sei.ZAN_DATA_KBN = '31' " +
				"             WHERE  " +
				"                 kesi.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"                 AND kesi.ZAN_DATA_KBN <> '31'     " +
				"           ) arzan3 " +
				"         ON  arzan3.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"         AND    arzan3.ZAN_SEI_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) +
				"         AND    swk2.KAI_CODE = arzan3.KAI_CODE " +
				"         AND    swk2.SWK_DEN_NO = arzan3.ZAN_KESI_DEN_NO " +
				"         AND    swk2.SWK_GYO_NO <> arzan3.ZAN_KESI_GYO_NO ";
				DBUtil.execute(conn, sql);

				// キャッシュフローコードの初期値をセットする
				// 純額
				sql =
					" UPDATE CFL_DTL cfd " +
					" SET " +
						" CFL_CODE = ( " +
							" SELECT " +
								" CFL_CODE " +
							" FROM " +
								" CFL_MST cfm " +
							" WHERE cfd.KAI_CODE = cfm.KAI_CODE " +
							" AND	cfd.SWK_KMK_CODE = cfm.KMK_CODE " +
							" AND	cfm.GAKU_HYG_KBN = 0 " +
							" AND	cfm.CFL_KBN = 0 " +
						" ) " +
					" WHERE	cfd.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					" AND	cfd.CFL_CODE IS NULL " +
					" AND	EXISTS ( " +
						" SELECT 1 " +
						" FROM " +
							" CFL_MST cfm " +
						" WHERE cfd.KAI_CODE = cfm.KAI_CODE " +
						" AND	cfd.SWK_KMK_CODE = cfm.KMK_CODE " +
						" AND	cfm.GAKU_HYG_KBN = 0 " +
						" AND	cfm.CFL_KBN = 0 " +
						" HAVING COUNT(*) = 1" +
					" ) ";
	
				DBUtil.execute(conn, sql);
	
				// 総額(加算)
				sql =
					" UPDATE CFL_DTL cfd " +
					" SET " +
						" CFL_CODE = ( " +
							" SELECT " +
								" CFL_CODE " +
							" FROM " +
								" CFL_MST cfm " +
							" WHERE cfd.KAI_CODE = cfm.KAI_CODE " +
							" AND	cfd.SWK_KMK_CODE = cfm.KMK_CODE " +
							" AND	cfm.GAKU_HYG_KBN = 1 " +
							" AND	cfm.CFL_KBN = 0 " +
						" ) " +
					" WHERE	cfd.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					" AND	cfd.CFL_CODE IS NULL " +
					" AND 	cfd.SWK_DC_KBN = 1 " +
					" AND	EXISTS ( " +
						" SELECT 1 " +
						" FROM " +
							" CFL_MST cfm " +
						" WHERE cfd.KAI_CODE = cfm.KAI_CODE " +
						" AND	cfd.SWK_KMK_CODE = cfm.KMK_CODE " +
						" AND	cfm.GAKU_HYG_KBN = 1 " +
						" AND	cfm.CFL_KBN = 0 " +
						" HAVING COUNT(*) = 1" +
					" ) ";
	
				DBUtil.execute(conn, sql);
	
				// 総額(減算)
				sql =
					" UPDATE CFL_DTL cfd " +
					" SET " +
						" CFL_CODE = ( " +
							" SELECT " +
								" CFL_CODE " +
							" FROM " +
								" CFL_MST cfm " +
							" WHERE cfd.KAI_CODE = cfm.KAI_CODE " +
							" AND	cfd.SWK_KMK_CODE = cfm.KMK_CODE " +
							" AND	cfm.GAKU_HYG_KBN = 1 " +
							" AND	cfm.CFL_KBN = 1 " +
						" ) " +
					" WHERE	cfd.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					" AND	cfd.CFL_CODE IS NULL " +
					" AND 	cfd.SWK_DC_KBN = 0 " +
					" AND	EXISTS ( " +
						" SELECT 1 " +
						" FROM " +
							" CFL_MST cfm " +
						" WHERE cfd.KAI_CODE = cfm.KAI_CODE " +
						" AND	cfd.SWK_KMK_CODE = cfm.KMK_CODE " +
						" AND	cfm.GAKU_HYG_KBN = 1 " +
						" AND	cfm.CFL_KBN = 1 " +
						" HAVING COUNT(*) = 1" +
					" ) ";
	
				DBUtil.execute(conn, sql);
				
			}
			
			// 承認履歴が必要の場合、承認履歴も登録する
			if (isUseApproveHistory) {
				insertHistory(conn, updateSqlSwkWhere);
			}

			// 11.伝票を更新状態にする
			String sqlUpdate =
				" SET " +
					" SWK_UPD_KBN = 4, " +
					" UPD_DATE = " + SQLUtil.getYMDHMSParam(getNow()) + ", " +
					" PRG_ID = " + SQLUtil.getParam(getProgramCode()) + ", " +
					" USR_ID = " + SQLUtil.getParam(getUserCode()) + updateSqlSwkWhereLast;

			String sqlUpdateHdr = sqlUpdate + (!DBUtil.isUseMySQL ? " AND    swk.SWK_DEN_SYU <> '" + code01Z + "' " : "");
			String sqlUpdateDtl = sqlUpdate + (!DBUtil.isUseMySQL ? " AND    swk.DEN_SYU_CODE <> '" + code01Z + "' " : "");

			DBUtil.execute(conn, " UPDATE GL_SWK_HDR swk " + sqlUpdateHdr);
			DBUtil.execute(conn, " UPDATE AP_SWK_HDR swk " + sqlUpdateHdr);
			DBUtil.execute(conn, " UPDATE AR_SWK_HDR swk " + sqlUpdateHdr);
			DBUtil.execute(conn, " UPDATE SWK_DTL swk " + sqlUpdateDtl);

			// CM_FUND_DTL登録を行うか
			if (isUseCmFund) {
				// CM_FUND_DTL登録処理
				entryCmFundInfo(company, conn);
			}
			
			// 12.日次更新日付を更新
			updateLastDailyClosedDate(company, getNow());

		} catch (TException e) {
			ServerErrorHandler.handledException(e);
			throw e;
		} catch (Exception e) {
			ServerErrorHandler.handledException(e);
			throw new TException(e);
		} finally {
			
			if(isFlush) {
				// 最終T_DAILY_CLOSEを削除
				if (DBUtil.isUseMySQL || !isNotCreateCF) {
					DBUtil.execute(conn, " TRUNCATE TABLE " + slipUpdateTable);
					if (DBUtil.isUseMySQL) {
						DBUtil.execute(conn, " DELETE FROM " + slipUpdateTable);
					}
				}
			}
			
			DBUtil.close(conn);
		}

	}

	/**
	 * 中間決算繰り越さない場合のWHERE条件の取得
	 * 
	 * @param company
	 * @return 中間決算繰り越さない場合のWHERE条件
	 */
	protected String getCarryJournalOfMidtermClosingForward(Company company) {
		if (isCarryJournalOfMidtermClosingForward(company)) {
			// 中間決算繰り越す
			return "";
		} else {
			// 中間決算繰り越さない
			return " AND (swk.SWK_KSN_KBN = 0 OR swk.SWK_TUKIDO = 12) "; // 通常伝票、または期末の決算伝票のみ残高転記
		}
	}

	/**
	 * 会計繰り越す機能フラグの取得
	 * 
	 * @param company 指定会社情報
	 * @return true:中間決算繰り越す、false:中間決算繰り越さない
	 */
	protected boolean isCarryJournalOfMidtermClosingForward(Company company) {
		return company.getAccountConfig().isCarryJournalOfMidtermClosingForward();
	}
	
	/**
	 * 5 残高転記(当年度)処理
	 * @param sqlSwkWhere
	 * @param krzZanColumn
	 * @throws TException 
	 */
	protected void updateKrzZanCurrentYear(String sqlSwkWhere, String krzZanColumn) throws TException {
		Connection conn = DBUtil.getConnection();
		try {
			
			ServerLogger.debug(" 5 残高転記(当年度)処理");

			// 更新対象データの取得
			String sql = " SELECT /*+RULE*/ " +
							" swk.KAI_CODE, " +
							" swk.SWK_NENDO, " +
							" swk.SWK_DEP_CODE, " +
							" swk.SWK_KMK_CODE, " +
							" swk.SWK_HKM_CODE, " +
							" swk.SWK_UKM_CODE, " +
							" swk.SWK_TRI_CODE, " +
							" swk.SWK_EMP_CODE, " +
							" swk.SWK_KNR_CODE_1, " +
							" swk.SWK_KNR_CODE_2, " +
							" swk.SWK_KNR_CODE_3, " +
							" swk.SWK_KNR_CODE_4, " +
							" swk.SWK_KNR_CODE_5, " +
							" swk.SWK_KNR_CODE_6, " +
							" swk.SWK_KSN_KBN, " +
							" swk.SWK_CUR_CODE, ";
			for (int month = 1; month <= 12; month++) {
				sql = sql +
							" SUM(CASE WHEN swk.SWK_TUKIDO = " + month + " AND swk.SWK_DC_KBN = 0 " +
								" THEN swk.SWK_KIN ELSE 0 END) SWK_DR_" + month + ", " +
							" SUM(CASE WHEN swk.SWK_TUKIDO = " + month + " AND swk.SWK_DC_KBN = 1 " +
								" THEN swk.SWK_KIN ELSE 0 END) SWK_CR_" + month + ", " +
							" SUM(CASE WHEN swk.SWK_TUKIDO <= " + month + " THEN " +
								" CASE WHEN swk.SWK_DC_KBN = kmk.DC_KBN THEN swk.SWK_KIN ELSE -swk.SWK_KIN END " +
								" ELSE 0 END) SWK_ZAN_" + month + ", " +
							" SUM(CASE WHEN swk.SWK_TUKIDO = " + month + " AND swk.SWK_DC_KBN = 0 " +
								" THEN swk.SWK_IN_KIN ELSE 0 END) SWK_DR_G_" + month + ", " +
							" SUM(CASE WHEN swk.SWK_TUKIDO = " + month + " AND swk.SWK_DC_KBN = 1 " +
								" THEN swk.SWK_IN_KIN ELSE 0 END) SWK_CR_G_" + month + ", " +
							" SUM(CASE WHEN swk.SWK_TUKIDO <= " + month + " THEN " +
								" CASE WHEN swk.SWK_DC_KBN = kmk.DC_KBN THEN swk.SWK_IN_KIN ELSE -swk.SWK_IN_KIN END " +
								" ELSE 0 END) SWK_ZAN_G_" + month + ", ";
			}
			sql = sql +
						" swk.SWK_BOOK_NO, " +
						" swk.SWK_ADJ_KBN " +
					" FROM " +
						" SWK_DTL swk " +
						" INNER JOIN KMK_MST kmk " +
						" ON	swk.KAI_CODE = kmk.KAI_CODE " +
						" AND	swk.SWK_KMK_CODE = kmk.KMK_CODE " +
						sqlSwkWhere +
					" GROUP BY " +
						" swk.KAI_CODE, " +
						" swk.SWK_NENDO, " +
						" swk.SWK_DEP_CODE, " +
						" swk.SWK_KMK_CODE, " +
						" swk.SWK_HKM_CODE, " +
						" swk.SWK_UKM_CODE, " +
						" swk.SWK_TRI_CODE, " +
						" swk.SWK_EMP_CODE, " +
						" swk.SWK_KNR_CODE_1, " +
						" swk.SWK_KNR_CODE_2, " +
						" swk.SWK_KNR_CODE_3, " +
						" swk.SWK_KNR_CODE_4, " +
						" swk.SWK_KNR_CODE_5, " +
						" swk.SWK_KNR_CODE_6, " +
						" swk.SWK_KSN_KBN, " +
						" swk.SWK_CUR_CODE, " +
						" swk.SWK_BOOK_NO, " +
						" swk.SWK_ADJ_KBN ";

			Statement statement = conn.createStatement();
			ResultSet rs = DBUtil.select(statement, sql);

			Date now = getNow();
			while (rs.next()) {
				// 管理残高更新処理
				sql = "UPDATE KRZ_ZAN krz SET ";
				for (int month = 1; month <= 12; month++) {
					sql = sql +
						" KRZ_DR_" + month + " = krz.KRZ_DR_" + month + " + " + DecimalUtil.avoidNull(rs.getBigDecimal("SWK_DR_" + month)) + ", " +
						" KRZ_CR_" + month + " = krz.KRZ_CR_" + month + " + " + DecimalUtil.avoidNull(rs.getBigDecimal("SWK_CR_" + month)) + ", " +
						" KRZ_ZAN_" + month + " = krz.KRZ_ZAN_" + month + " + " + DecimalUtil.avoidNull(rs.getBigDecimal("SWK_ZAN_" + month)) + ", " +
						" KRZ_DR_G_" + month + " = krz.KRZ_DR_G_" + month + " + " + DecimalUtil.avoidNull(rs.getBigDecimal("SWK_DR_G_" + month)) + ", " +
						" KRZ_CR_G_" + month + " = krz.KRZ_CR_G_" + month + " + " + DecimalUtil.avoidNull(rs.getBigDecimal("SWK_CR_G_" + month)) + ", " +
						" KRZ_ZAN_G_" + month + " = krz.KRZ_ZAN_G_" + month + " + " + DecimalUtil.avoidNull(rs.getBigDecimal("SWK_ZAN_G_" + month)) + ", ";
				}
				sql = sql +
							" UPD_DATE = " + SQLUtil.getYMDHMSParam(now) + ", " +
							" PRG_ID = " + SQLUtil.getParam(getProgramCode()) + ", " +
							" USR_ID = " + SQLUtil.getParam(getUserCode()) + " " +
						"WHERE krz.KAI_CODE = " + SQLUtil.getParam(rs.getString("KAI_CODE")) +
						"  AND krz.KRZ_NENDO = " + rs.getInt("SWK_NENDO") +
						"  AND krz.KRZ_DEP_CODE = " + SQLUtil.getParam(rs.getString("SWK_DEP_CODE")) +
						"  AND krz.KRZ_KMK_CODE = " + SQLUtil.getParam(rs.getString("SWK_KMK_CODE")) +
						"  AND krz.KRZ_HKM_CODE " + (Util.isNullOrEmpty(rs.getString("SWK_HKM_CODE")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_HKM_CODE"))) +
						"  AND krz.KRZ_UKM_CODE " + (Util.isNullOrEmpty(rs.getString("SWK_UKM_CODE")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_UKM_CODE"))) +
						"  AND krz.KRZ_TRI_CODE " + (Util.isNullOrEmpty(rs.getString("SWK_TRI_CODE")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_TRI_CODE"))) +
						"  AND krz.KRZ_EMP_CODE " + (Util.isNullOrEmpty(rs.getString("SWK_EMP_CODE")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_EMP_CODE"))) +
						"  AND krz.KRZ_KNR_CODE_1 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_1")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_1"))) +
						"  AND krz.KRZ_KNR_CODE_2 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_2")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_2"))) +
						"  AND krz.KRZ_KNR_CODE_3 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_3")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_3"))) +
						"  AND krz.KRZ_KNR_CODE_4 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_4")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_4"))) +
						"  AND krz.KRZ_KNR_CODE_5 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_5")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_5"))) +
						"  AND krz.KRZ_KNR_CODE_6 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_6")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_6"))) +
						"  AND krz.KRZ_KSN_KBN = " + rs.getInt("SWK_KSN_KBN") +
						"  AND krz.KRZ_CUR_CODE = " + SQLUtil.getParam(rs.getString("SWK_CUR_CODE")) +
						"  AND krz.KRZ_BOOK_NO = " + rs.getInt("SWK_BOOK_NO") +
						"  AND krz.KRZ_ADJ_KBN = " + rs.getInt("SWK_ADJ_KBN");

				// 管理残高更新処理で更新件数が0件の場合
				if (DBUtil.execute(conn, sql) == 0) {
					// 管理残高登録処理
					sql = "INSERT INTO KRZ_ZAN ( " + krzZanColumn + " ) VALUES (" +
						SQLUtil.getParam(rs.getString("KAI_CODE")) + ", " +
						rs.getInt("SWK_NENDO") + ", " +
						SQLUtil.getParam(rs.getString("SWK_DEP_CODE")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_KMK_CODE")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_HKM_CODE")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_UKM_CODE")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_TRI_CODE")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_EMP_CODE")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_KNR_CODE_1")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_KNR_CODE_2")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_KNR_CODE_3")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_KNR_CODE_4")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_KNR_CODE_5")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_KNR_CODE_6")) + ", " +
						rs.getInt("SWK_KSN_KBN") + ", " +
						SQLUtil.getParam(rs.getString("SWK_CUR_CODE")) + ", " +
						"0, 0, ";
					String sql_kin = "";
					for (int month = 1; month <= 12; month++) {
						sql_kin += SQLUtil.getParam(rs.getBigDecimal("SWK_DR_" + month)) + ", " +
							       SQLUtil.getParam(rs.getBigDecimal("SWK_CR_" + month)) + ", " +
							       SQLUtil.getParam(rs.getBigDecimal("SWK_ZAN_" + month)) + ", " +
							       SQLUtil.getParam(rs.getBigDecimal("SWK_DR_G_" + month)) + ", " +
							       SQLUtil.getParam(rs.getBigDecimal("SWK_CR_G_" + month)) + ", " +
							       SQLUtil.getParam(rs.getBigDecimal("SWK_ZAN_G_" + month)) + ", ";
					}
					sql = sql + sql_kin +
						SQLUtil.getYMDHMSParam(now) + ", " +
						" NULL, " +
						SQLUtil.getParam(getProgramCode()) + ", " +
						SQLUtil.getParam(getUserCode()) + ", " +
						rs.getInt("SWK_BOOK_NO") + ", " +
						rs.getInt("SWK_ADJ_KBN") + ")";

					DBUtil.execute(conn, sql);
				}
			}

			DBUtil.close(statement);
			DBUtil.close(rs);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 5.1 B/S転記
	 * 
	 * @param sqlSwkWhere
	 * @param krzZanColumn
	 * @param retainedEarningsItemJornal 
	 * @param swkMinNendo 
	 * @param swkMaxNendo 
	 * @param lastNendo 
	 * @throws TException
	 */
	protected void updateKrzZanBS(String sqlSwkWhere, String krzZanColumn, AutoJornalAccount retainedEarningsItemJornal, int swkMinNendo, int swkMaxNendo, int lastNendo) throws TException {
		Connection conn = DBUtil.getConnection();
		try {
			
			ServerLogger.debug("5.1 B/S転記");

			for (int nendo = swkMinNendo; nendo <= swkMaxNendo; nendo++) {

				for (int toNendo = nendo; toNendo < lastNendo - 1; toNendo++) {

					// 更新対象データの取得
					String sql = 
						" SELECT /*+RULE*/ " +
							" swk.KAI_CODE, " +
							" swk.SWK_DEP_CODE, " +
							" swk.SWK_KMK_CODE, " +
							" swk.SWK_HKM_CODE, " +
							" swk.SWK_UKM_CODE, " +
							" swk.SWK_TRI_CODE, " +
							" swk.SWK_EMP_CODE, " +
							" swk.SWK_KNR_CODE_1, " +
							" swk.SWK_KNR_CODE_2, " +
							" swk.SWK_KNR_CODE_3, " +
							" swk.SWK_KNR_CODE_4, " +
							" swk.SWK_KNR_CODE_5, " +
							" swk.SWK_KNR_CODE_6, " +
							" swk.SWK_CUR_CODE, " +
							" SUM(CASE WHEN swk.SWK_DC_KBN = kmk.DC_KBN THEN swk.SWK_KIN ELSE -swk.SWK_KIN END) SWK_ZAN, " +
							" SUM(CASE WHEN swk.SWK_DC_KBN = kmk.DC_KBN THEN swk.SWK_IN_KIN ELSE -swk.SWK_IN_KIN END) SWK_ZAN_G, " +
							" swk.SWK_BOOK_NO, " +
							" swk.SWK_ADJ_KBN " +
						" FROM " +
							" SWK_DTL swk " +
							" INNER JOIN KMK_MST kmk " +
							" ON	swk.KAI_CODE = kmk.KAI_CODE " +
							" AND	swk.SWK_KMK_CODE = kmk.KMK_CODE " +
							" AND	kmk.KMK_SHU <> '1' " +
							" AND	kmk.KMK_CODE <> " + SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) +
						sqlSwkWhere +
						" AND	swk.SWK_NENDO = " + Integer.toString(nendo) +
						" GROUP BY " +
							" swk.KAI_CODE, " +
							" swk.SWK_DEP_CODE, " +
							" swk.SWK_KMK_CODE, " +
							" swk.SWK_HKM_CODE, " +
							" swk.SWK_UKM_CODE, " +
							" swk.SWK_TRI_CODE, " +
							" swk.SWK_EMP_CODE, " +
							" swk.SWK_KNR_CODE_1, " +
							" swk.SWK_KNR_CODE_2, " +
							" swk.SWK_KNR_CODE_3, " +
							" swk.SWK_KNR_CODE_4, " +
							" swk.SWK_KNR_CODE_5, " +
							" swk.SWK_KNR_CODE_6, " +
							" swk.SWK_CUR_CODE, " +
							" swk.SWK_BOOK_NO, " +
							" swk.SWK_ADJ_KBN ";

					Statement statement = conn.createStatement();
					ResultSet rs = DBUtil.select(statement, sql);

					Date now = getNow();
					while (rs.next()) {

						BigDecimal zan = DecimalUtil.avoidNull(rs.getBigDecimal("SWK_ZAN"));
						BigDecimal zanG = DecimalUtil.avoidNull(rs.getBigDecimal("SWK_ZAN_G"));
						
						// 管理残高更新処理
						sql = "UPDATE KRZ_ZAN krz SET " +
						        " krz.KRZ_STR_SUM = krz.KRZ_STR_SUM + " + SQLUtil.getParam(zan) + ", " +
						        " krz.KRZ_STR_SUM_G = krz.KRZ_STR_SUM_G + " + SQLUtil.getParam(zanG) + ", ";
						for (int month = 1; month <= 12; month++) {
							sql = sql +
								" KRZ_ZAN_" + month + " = krz.KRZ_ZAN_" + month + " + " + SQLUtil.getParam(zan) + ", " +
								" KRZ_ZAN_G_" + month + " = krz.KRZ_ZAN_G_" + month + " + " + SQLUtil.getParam(zanG) + ", ";
						}
						sql = sql +
									" UPD_DATE = " + SQLUtil.getYMDHMSParam(now) + ", " +
									" PRG_ID = " + SQLUtil.getParam(getProgramCode()) + ", " +
									" USR_ID = " + SQLUtil.getParam(getUserCode()) + " " +
								"WHERE krz.KAI_CODE = " + SQLUtil.getParam(rs.getString("KAI_CODE")) +
								"  AND krz.KRZ_NENDO = " + Integer.toString(toNendo + 1) +
								"  AND krz.KRZ_DEP_CODE = " + SQLUtil.getParam(rs.getString("SWK_DEP_CODE")) +
								"  AND krz.KRZ_KMK_CODE = " + SQLUtil.getParam(rs.getString("SWK_KMK_CODE")) +
								"  AND krz.KRZ_HKM_CODE " + (Util.isNullOrEmpty(rs.getString("SWK_HKM_CODE")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_HKM_CODE"))) +
								"  AND krz.KRZ_UKM_CODE " + (Util.isNullOrEmpty(rs.getString("SWK_UKM_CODE")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_UKM_CODE"))) +
								"  AND krz.KRZ_TRI_CODE " + (Util.isNullOrEmpty(rs.getString("SWK_TRI_CODE")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_TRI_CODE"))) +
								"  AND krz.KRZ_EMP_CODE " + (Util.isNullOrEmpty(rs.getString("SWK_EMP_CODE")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_EMP_CODE"))) +
								"  AND krz.KRZ_KNR_CODE_1 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_1")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_1"))) +
								"  AND krz.KRZ_KNR_CODE_2 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_2")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_2"))) +
								"  AND krz.KRZ_KNR_CODE_3 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_3")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_3"))) +
								"  AND krz.KRZ_KNR_CODE_4 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_4")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_4"))) +
								"  AND krz.KRZ_KNR_CODE_5 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_5")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_5"))) +
								"  AND krz.KRZ_KNR_CODE_6 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_6")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_6"))) +
								"  AND krz.KRZ_KSN_KBN = 0 " +
								"  AND krz.KRZ_CUR_CODE = " + SQLUtil.getParam(rs.getString("SWK_CUR_CODE")) +
								"  AND krz.KRZ_BOOK_NO = " + rs.getInt("SWK_BOOK_NO") +
								"  AND krz.KRZ_ADJ_KBN = " + rs.getInt("SWK_ADJ_KBN");

						// 管理残高更新処理で更新件数が0件の場合
						if (DBUtil.execute(conn, sql) == 0) {
							// 管理残高登録処理
							sql = "INSERT INTO KRZ_ZAN ( " + krzZanColumn + " ) VALUES (" +
								SQLUtil.getParam(rs.getString("KAI_CODE")) + ", " +
								Integer.toString(toNendo + 1) + ", " +
								SQLUtil.getParam(rs.getString("SWK_DEP_CODE")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_KMK_CODE")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_HKM_CODE")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_UKM_CODE")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_TRI_CODE")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_EMP_CODE")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_KNR_CODE_1")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_KNR_CODE_2")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_KNR_CODE_3")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_KNR_CODE_4")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_KNR_CODE_5")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_KNR_CODE_6")) + ", " +
								"0, " + // 決算区分
								SQLUtil.getParam(rs.getString("SWK_CUR_CODE")) + ", " +
								SQLUtil.getParam(zan) + ", " + // 期首残
								SQLUtil.getParam(zanG) + ", "; // 期首外貨残
							String sql_kin = "";
							for (int month = 1; month <= 12; month++) {
								sql_kin += " 0, " +
										   " 0, " +
										   SQLUtil.getParam(zan) + ", " +
										   " 0, " +
										   " 0, " +
										   SQLUtil.getParam(zanG) + ", ";
							}
							sql = sql + sql_kin +
								SQLUtil.getYMDHMSParam(now) + ", " +
								" NULL, " +
								SQLUtil.getParam(getProgramCode()) + ", " +
								SQLUtil.getParam(getUserCode()) + ", " +
								rs.getInt("SWK_BOOK_NO") + ", " +
								rs.getInt("SWK_ADJ_KBN") + ")";

							DBUtil.execute(conn, sql);
						}
					}

					DBUtil.close(statement);
					DBUtil.close(rs);

				}
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 5.2 P/L転記(翌年の前期繰越利益に加算)
	 * 
	 * @param sqlSwkWhere
	 * @param krzZanColumn
	 * @param retainedEarningsItemJornal
	 * @param swkMinNendo
	 * @param swkMaxNendo
	 * @param lastNendo
	 * @throws TException
	 */
	protected void updateKrzZanPL(String sqlSwkWhere, String krzZanColumn,
		AutoJornalAccount retainedEarningsItemJornal, int swkMinNendo, int swkMaxNendo, int lastNendo)
		throws TException {
		updateKrzZanPL(sqlSwkWhere, krzZanColumn, retainedEarningsItemJornal, swkMinNendo, swkMaxNendo, lastNendo, getCompany());
	}

	/**
	 * 5.2 P/L転記(翌年の前期繰越利益に加算)
	 * 
	 * @param sqlSwkWhere
	 * @param krzZanColumn
	 * @param retainedEarningsItemJornal
	 * @param swkMinNendo
	 * @param swkMaxNendo
	 * @param lastNendo
	 * @param company 会社情報
	 * @throws TException
	 */
	protected void updateKrzZanPL(String sqlSwkWhere, String krzZanColumn,
		AutoJornalAccount retainedEarningsItemJornal, int swkMinNendo, int swkMaxNendo, int lastNendo, Company company)
		throws TException {
		Connection conn = DBUtil.getConnection();
		try {

			ServerLogger.debug("5.2 P/L転記(翌年の前期繰越利益に加算)");
			
			String keyCur = SQLUtil.getParam(company.getAccountConfig().getKeyCurrency().getCode());
			String fncCur = SQLUtil.getParam(company.getAccountConfig().getFunctionalCurrency().getCode());

			for (int nendo = swkMinNendo; nendo <= swkMaxNendo; nendo++) {

				for (int toNendo = nendo; toNendo < lastNendo; toNendo++) {
					
					// 更新対象データの取得
					String sql = 
						" SELECT /*+RULE*/ " +
							" swk.KAI_CODE, " +
							" SUM(CASE WHEN swk.SWK_DC_KBN = 1 THEN swk.SWK_KIN ELSE -swk.SWK_KIN END) SWK_ZAN, " +
							" swk.SWK_BOOK_NO, " +
							" swk.SWK_ADJ_KBN ";

					if (isDepCreate) {
						// 部門単位で生成
						sql += " , swk.SWK_DEP_CODE ";
					}
					sql += " FROM " +
							" SWK_DTL swk " +
							" INNER JOIN KMK_MST kmk " +
							" ON	swk.KAI_CODE = kmk.KAI_CODE " +
							" AND	swk.SWK_KMK_CODE = kmk.KMK_CODE " +
							" AND	(kmk.KMK_SHU = '1' " +
							" OR	kmk.KMK_CODE = " + SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) + ") " +
						sqlSwkWhere +
						" AND	swk.SWK_NENDO = " + Integer.toString(nendo) +
						" GROUP BY " +
							" swk.KAI_CODE, " +
							" swk.SWK_BOOK_NO, " +
							" swk.SWK_ADJ_KBN ";
					if (isDepCreate) {
						// 部門単位で生成
						sql += " , swk.SWK_DEP_CODE ";
					}
					
					Statement statement = conn.createStatement();
					ResultSet rs = DBUtil.select(statement, sql);
					
					Date now = getNow();
					while (rs.next()) {
						
						BigDecimal zan = DecimalUtil.avoidNull(rs.getBigDecimal("SWK_ZAN"));
						
						// 管理残高更新処理
						sql = "UPDATE KRZ_ZAN krz SET " +
							" krz.KRZ_STR_SUM = krz.KRZ_STR_SUM + " + SQLUtil.getParam(zan) + ", " +
							" krz.KRZ_STR_SUM_G = krz.KRZ_STR_SUM_G + " + SQLUtil.getParam(zan) + ", ";
						for (int month = 1; month <= 12; month++) {
							sql = sql +
								" KRZ_ZAN_" + month + " = krz.KRZ_ZAN_" + month + " + " + SQLUtil.getParam(zan) + ", " +
								" KRZ_ZAN_G_" + month + " = krz.KRZ_ZAN_G_" + month + " + " + SQLUtil.getParam(zan) + ", ";
						}
						sql = sql +
							" UPD_DATE = " + SQLUtil.getYMDHMSParam(now) + ", " +
							" PRG_ID = " + SQLUtil.getParam(getProgramCode()) + ", " +
							" USR_ID = " + SQLUtil.getParam(getUserCode()) + " " +
							"WHERE krz.KAI_CODE = " + SQLUtil.getParam(rs.getString("KAI_CODE")) +
							"  AND krz.KRZ_NENDO = " + Integer.toString(toNendo + 1);
						if (isDepCreate) {
							// 部門単位で生成
							sql += " AND krz.KRZ_DEP_CODE = " + SQLUtil.getParam(rs.getString("SWK_DEP_CODE"));
						} else {
							// 自動仕訳科目マスタで生成
							sql += "  AND krz.KRZ_DEP_CODE = " + SQLUtil.getParam(retainedEarningsItemJornal.getDepertmentCode());
						}
						sql +=
							"  AND krz.KRZ_KMK_CODE = " + SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) +
							"  AND krz.KRZ_KSN_KBN = 0 " +
							"  AND krz.KRZ_CUR_CODE = CASE WHEN krz.KRZ_BOOK_NO = 1 THEN " + keyCur + " ELSE " + fncCur + " END " +
							"  AND krz.KRZ_BOOK_NO = " + rs.getInt("SWK_BOOK_NO") +
							"  AND krz.KRZ_ADJ_KBN = " + rs.getInt("SWK_ADJ_KBN");
						
						// 管理残高更新処理で更新件数が0件の場合
						if (DBUtil.execute(conn, sql) == 0) {
							// 管理残高登録処理
							sql = "INSERT INTO KRZ_ZAN ( " + krzZanColumn + " ) VALUES (" +
								SQLUtil.getParam(rs.getString("KAI_CODE")) + ", " +
								Integer.toString(toNendo + 1) + ", ";
							if (isDepCreate) {
								// 部門単位で生成
								sql += SQLUtil.getParam(rs.getString("SWK_DEP_CODE")) + ", ";
							} else {
								// 自動仕訳科目マスタで生成
								sql += SQLUtil.getParam(retainedEarningsItemJornal.getDepertmentCode()) + ", ";
							}
							sql +=
								SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) + ", " +
								SQLUtil.getParam(retainedEarningsItemJornal.getSubItemCode()) + ", " +
								SQLUtil.getParam(retainedEarningsItemJornal.getDetailItemCode()) + ", " +
								" NULL, " +
								" NULL, " +
								" NULL, " +
								" NULL, " +
								" NULL, " +
								" NULL, " +
								" NULL, " +
								" NULL, " +
								"0, " + // 決算区分
							    ((rs.getInt("SWK_BOOK_NO") == 1) ? keyCur : fncCur) + ", " +
								SQLUtil.getParam(zan) + ", " + // 期首残
								SQLUtil.getParam(zan) + ", ";  // 期首外貨残
							String sql_kin = "";
							for (int month = 1; month <= 12; month++) {
								sql_kin += " 0, " +
									" 0, " +
									SQLUtil.getParam(zan) + ", " +
									" 0, " +
									" 0, " +
									SQLUtil.getParam(zan) + ", ";
							}
							sql = sql + sql_kin +
								SQLUtil.getYMDHMSParam(now) + ", " +
								" NULL, " +
								SQLUtil.getParam(getProgramCode()) + ", " +
								SQLUtil.getParam(getUserCode()) + ", " +
								rs.getInt("SWK_BOOK_NO") + ", " +
								rs.getInt("SWK_ADJ_KBN") + ")";
							
							DBUtil.execute(conn, sql);
						}
					}
					
					DBUtil.close(statement);
					DBUtil.close(rs);
					
				}
			}
			
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 履歴登録
	 * 
	 * @param conn
	 * @param sqlSwkWhere
	 * @throws TException 
	 */
	protected void insertHistory(Connection conn, String sqlSwkWhere) throws TException {
		String empCode = "";
		if (getUser().getEmployee() != null) {
			empCode = getUser().getEmployee().getCode();
		}

		SQLCreator sql = new SQLCreator();
		sql.add("");
		sql.add(" INSERT INTO SWK_SYO_RRK ");
		sql.add(" ( ");
		sql.add("    KAI_CODE ");
		sql.add("   ,SWK_DEN_DATE ");
		sql.add("   ,SWK_DEN_NO ");
		sql.add("   ,SWK_UPD_KBN ");
		sql.add("   ,EMP_CODE ");
		sql.add("   ,SYO_FLG ");
		sql.add("   ,INP_DATE ");
		sql.add("   ,PRG_ID ");
		sql.add("   ,USR_ID ");
		sql.add(" ) ");
		sql.add(" SELECT hdr.KAI_CODE ");
		sql.add("       ,hdr.SWK_DEN_DATE ");
		sql.add("       ,hdr.SWK_DEN_NO ");
		sql.add("       ,? SWK_UPD_KBN ", SlipState.UPDATE.value);
		sql.add("       ,? EMP_CODE ", empCode);
		sql.add("       ,? SYO_FLG ", ApproveHistory.SYO_FLG.UPDATE);
		sql.addYMDHMS("       ,? INP_DATE ", getNow());
		sql.add("       ,? PRG_ID ", getProgramCode());
		sql.add("       ,? USR_ID ", getUserCode());
		sql.add(" FROM   (SELECT KAI_CODE ");
		sql.add("               ,SWK_DEN_DATE ");
		sql.add("               ,SWK_DEN_NO ");
		sql.add("         FROM   GL_SWK_HDR swk ");
		sql.add(sqlSwkWhere);
		sql.add("         UNION ALL ");
		sql.add("         SELECT KAI_CODE ");
		sql.add("               ,SWK_DEN_DATE ");
		sql.add("               ,SWK_DEN_NO ");
		sql.add("         FROM   AP_SWK_HDR swk ");
		sql.add(sqlSwkWhere);
		sql.add("         UNION ALL ");
		sql.add("         SELECT KAI_CODE ");
		sql.add("               ,SWK_DEN_DATE ");
		sql.add("               ,SWK_DEN_NO ");
		sql.add("         FROM   AR_SWK_HDR swk ");
		sql.add(sqlSwkWhere);
		sql.add("         ) hdr ");

		DBUtil.execute(conn, sql);
	}

	/****************************************************************************************************************
	 * 以下複数会社版メソッド
	 ****************************************************************************************************************/

	/**
	 * 日次更新データを取得する
	 * 
	 * @return 日次更新データ
	 * @throws TException
	 */
	public List<DailyData> getDailyData() throws TException {

		Connection conn = DBUtil.getConnection();
		List<DailyData> list = new ArrayList<DailyData>();

		try {

			// 会社全データ抽出
			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT env.KAI_CODE ");
			sql.add("       ,env.KAI_NAME ");
			sql.add("       ,sim.NITIJI_DATE ");
			sql.add(" FROM   ENV_MST env ");
			sql.add(" INNER  JOIN SIM_CTL sim ON sim.KAI_CODE = env.KAI_CODE ");
			sql.add(" ORDER BY CASE WHEN env.KAI_CODE = ? THEN 1 ELSE 2 END ", getCompanyCode());
			sql.add("         ,env.KAI_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				DailyData bean = new DailyData();
				bean.setCompanyCode(rs.getString("KAI_CODE"));
				bean.setCompanyName(rs.getString("KAI_NAME"));
				bean.setLastUpdateDate(rs.getTimestamp("NITIJI_DATE"));
				list.add(bean);
			}

			DBUtil.close(rs);
			DBUtil.close(statement);
			
			return list;

		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}
	
	/**
	 * CM_FUND_DTL情報の更新
	 * 
	 * @param company
	 * @param conn 
	 * @throws TException
	 */
	public void entryCmFundInfo(Company company, Connection conn) throws TException {

		try {
			// 更新対象データ一時格納テーブル
			String slipUpdateTable = "T_DAILY_CLOSE";

			// 日次更新対象のヘッダーレコードは全て削除
			VCreator sql = new VCreator();
			sql.add(" DELETE FROM CM_FUND_DTL ");
			sql.add(" WHERE (KAI_CODE , KEY_DEN_NO) IN ( ");
			sql.add("     SELECT KAI_CODE , SWK_DEN_NO FROM " + slipUpdateTable);
			sql.add(" ) ");
			sql.add(" AND DATA_TYPE = 0 "); // 0 ヘッダー
			DBUtil.execute(conn, sql);

			// 共通部構築
			VCreator sqlIns = new VCreator();
			sqlIns.add(" INSERT INTO CM_FUND_DTL ( ");
			sqlIns.add("      KAI_CODE ");
			sqlIns.add("     ,DEN_DATE ");
			sqlIns.add("     ,DEN_NO ");
			sqlIns.add("     ,TRI_CODE ");
			sqlIns.add("     ,TRI_NAME ");
			sqlIns.add("     ,KNR_CODE ");
			sqlIns.add("     ,KNR_NAME ");
			sqlIns.add("     ,TEK ");
			sqlIns.add("     ,DEN_SYU_CODE ");
			sqlIns.add("     ,DEN_SYU_NAME ");
			sqlIns.add("     ,CUR_CODE ");
			sqlIns.add("     ,ZAN_KIN ");
			sqlIns.add("     ,ZAN_IN_KIN ");
			sqlIns.add("     ,CBK_CODE ");
			sqlIns.add("     ,DATA_KBN ");
			sqlIns.add("     ,SYS_KBN ");
			sqlIns.add("     ,DATA_TYPE ");
			sqlIns.add("     ,KEY_DEN_DATE ");
			sqlIns.add("     ,KEY_DEN_NO ");
			sqlIns.add("     ,KEY_GYO_NO ");
			sqlIns.add("     ,KEY_SEI_NO ");
			sqlIns.add("     ,KEY_DEP_CODE ");
			sqlIns.add("     ,KEY_TRI_CODE ");
			sqlIns.add("     ,INP_DATE ");
			sqlIns.add("     ,UPD_DATE ");
			sqlIns.add("     ,PRG_ID ");
			sqlIns.add("     ,USR_ID ");
			sqlIns.add(" ) VALUES ( ");

			// 債務残高生成
			sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("  zan.KAI_CODE, ");
			sql.add("  zan.ZAN_SIHA_DATE, ");
			sql.add("  zan.ZAN_DEN_DATE, ");
			sql.add("  zan.ZAN_DEN_NO, ");
			sql.add("  zan.ZAN_GYO_NO, ");
			sql.add("  zan.ZAN_DEP_CODE, ");
			sql.add("  dep.DEP_NAME, ");
			sql.add("  zan.ZAN_TEK, ");
			sql.add("  zan.ZAN_SIHA_CODE, ");
			sql.add("  tri.TRI_NAME, ");
			sql.add("  zan.ZAN_KIN, ");
			sql.add("  zan.ZAN_IN_SIHA_KIN, ");
			sql.add("  zan.ZAN_CUR_CODE, ");
			sql.add("  cbk.CUR_CODE, ");
			sql.add("  cur.DEC_KETA, ");
			sql.add("  cur.RATE_POW, ");
			sql.add("  zan.ZAN_SYS_KBN, ");
			sql.add("  zan.ZAN_DEN_SYU, ");
			sql.add("  syu.DEN_SYU_NAME, ");
			sql.add("  zan.ZAN_FURI_CBK_CODE, ");

			sql.add("  zan.ZAN_INP_DATE, ");
			sql.add("  zan.UPD_DATE, ");
			sql.add("  zan.PRG_ID, ");
			sql.add("  zan.USR_ID ");

			sql.add(" FROM AP_ZAN zan ");

			sql.add(" LEFT OUTER JOIN TRI_MST tri ");
			sql.add(" ON  zan.KAI_CODE      = tri.KAI_CODE ");
			sql.add(" AND zan.ZAN_SIHA_CODE = tri.TRI_CODE ");

			sql.add(" LEFT OUTER JOIN BMN_MST dep ");
			sql.add(" ON  zan.KAI_CODE     = dep.KAI_CODE ");
			sql.add(" AND zan.ZAN_DEP_CODE = dep.DEP_CODE ");

			sql.add(" LEFT OUTER JOIN DEN_SYU_MST syu ");
			sql.add(" ON  zan.KAI_CODE    = syu.KAI_CODE ");
			sql.add(" AND zan.ZAN_DEN_SYU = syu.DEN_SYU_CODE ");

			sql.add(" INNER JOIN AP_CBK_MST cbk ");
			sql.add(" ON  zan.KAI_CODE          = cbk.KAI_CODE ");
			sql.add(" AND zan.ZAN_FURI_CBK_CODE = cbk.CBK_CBK_CODE ");

			sql.add(" INNER JOIN CUR_MST cur ");
			sql.add(" ON  cbk.KAI_CODE = cur.KAI_CODE ");
			sql.add(" AND cbk.CUR_CODE = cur.CUR_CODE ");

			sql.add(" WHERE (zan.KAI_CODE , zan.ZAN_DEN_NO) IN ( ");
			sql.add("     SELECT KAI_CODE , SWK_DEN_NO FROM " + slipUpdateTable);
			sql.add(" ) ");

			sql.add(" ORDER BY zan.KAI_CODE , zan.ZAN_SIHA_DATE , zan.ZAN_DEN_NO ");
			
			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String curCode = null;
			BigDecimal inKin = BigDecimal.ZERO;
			BigDecimal kin = BigDecimal.ZERO;
			String keyCurCode = company.getAccountConfig().getKeyCurrency().getCode();
			RateManager rateMn = get(RateManager.class);
	
			while (rs.next()) {
				//銀行口座通貨
				curCode = rs.getString("CUR_CODE");
				// 金額セット
				inKin = rs.getBigDecimal("ZAN_IN_SIHA_KIN");
				kin = rs.getBigDecimal("ZAN_KIN");
				
				if (Util.equals(curCode, rs.getString("ZAN_CUR_CODE"))) {
					// 口座通貨と取引通貨が同一 はそのまま

				} else if (Util.equals(curCode, keyCurCode)) {
					// 取引通貨と基軸通貨が同一
					inKin = kin;
				} else {
					// 以外は計算より求める
					BigDecimal rate = rateMn.getRate(curCode, rs.getDate("ZAN_SIHA_DATE"));
					inKin = convertToForeign(kin, rate, rs.getInt("RATE_POW"), rs.getInt("DEC_KETA"), company);
				}

				// 登録共通
				sql = new VCreator();
				sql.add(sqlIns);
				sql.add("     ? ", rs.getString("KAI_CODE"));
				sql.add("    ,? ", rs.getDate("ZAN_SIHA_DATE"));
				sql.add("    ,? ", rs.getString("ZAN_DEN_NO"));
				sql.add("    ,? ", rs.getString("ZAN_SIHA_CODE"));
				sql.add("    ,? ", rs.getString("TRI_NAME"));
				sql.add("    ,? ", rs.getString("ZAN_DEP_CODE"));
				sql.add("    ,? ", rs.getString("DEP_NAME"));
				sql.add("    ,? ", rs.getString("ZAN_TEK"));
				sql.add("    ,? ", rs.getString("ZAN_DEN_SYU"));
				sql.add("    ,? ", rs.getString("DEN_SYU_NAME"));
				sql.add("    ,? ", curCode);
				sql.add("    ,? ", kin.negate()); // 符号反転
				sql.add("    ,? ", inKin.negate()); // 符号反転
				sql.add("    ,? ", rs.getString("ZAN_FURI_CBK_CODE"));
				sql.add("    ,0 "); // DATA_KBN
				sql.add("    ,? ", rs.getString("ZAN_SYS_KBN"));
				sql.add("    ,2 "); // DATA_TYPE
				sql.add("    ,? ", rs.getDate("ZAN_DEN_DATE"));
				sql.add("    ,? ", rs.getString("ZAN_DEN_NO"));
				sql.add("    ,? ", rs.getInt("ZAN_GYO_NO"));
				sql.add("    ,NULL ");
				sql.add("    ,NULL ");
				sql.add("    ,NULL ");
				sql.adt("    ,? ", rs.getTimestamp("ZAN_INP_DATE"));
				sql.adt("    ,? ", rs.getTimestamp("UPD_DATE"));
				sql.add("    ,? ", rs.getString("PRG_ID"));
				sql.add("    ,? ", rs.getString("USR_ID"));
				sql.add(" ) ");

				DBUtil.execute(sql);
			}
			
			DBUtil.close(rs);
			DBUtil.close(statement);
			
			// 債権残高生成
			sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("  zan.KAI_CODE, ");
			sql.add("  zan.ZAN_AR_DATE, ");
			sql.add("  zan.ZAN_SEI_DEN_DATE, ");
			sql.add("  zan.ZAN_SEI_DEN_NO, ");
			sql.add("  zan.ZAN_SEI_GYO_NO, ");
			sql.add("  zan.ZAN_SEI_NO, ");
			sql.add("  zan.ZAN_DEP_CODE, ");
			sql.add("  dep.DEP_NAME, ");
			sql.add("  swk.SWK_TEK, "); // 
			sql.add("  zan.ZAN_TRI_CODE, ");
			sql.add("  tri.TRI_NAME, ");
			sql.add("  zan.ZAN_SEI_KIN, ");
			sql.add("  zan.ZAN_SEI_IN_KIN, ");
			sql.add("  zan.ZAN_CUR_CODE, ");
			sql.add("  cbk.CUR_CODE, ");
			sql.add("  cur.DEC_KETA, ");
			sql.add("  cur.RATE_POW, ");
			sql.add("  swk.SWK_SYS_KBN, ");
			sql.add("  swk.SWK_DEN_SYU, ");
			sql.add("  syu.DEN_SYU_NAME, ");
			sql.add("  zan.ZAN_CBK_CODE, ");
			sql.add("  zan.INP_DATE, ");
			sql.add("  zan.UPD_DATE, ");
			sql.add("  zan.PRG_ID, ");
			sql.add("  zan.USR_ID ");

			sql.add(" FROM AR_ZAN zan ");

			sql.add(" INNER JOIN AR_SWK_HDR swk ");
			sql.add(" ON  zan.KAI_CODE         = swk.KAI_CODE ");
			sql.add(" AND zan.ZAN_SEI_DEN_DATE = swk.SWK_DEN_DATE ");
			sql.add(" AND zan.ZAN_SEI_DEN_NO   = swk.SWK_DEN_NO ");

			sql.add(" LEFT OUTER JOIN TRI_MST tri ");
			sql.add(" ON  zan.KAI_CODE     = tri.KAI_CODE ");
			sql.add(" AND zan.ZAN_TRI_CODE = tri.TRI_CODE ");

			sql.add(" LEFT OUTER JOIN BMN_MST dep ");
			sql.add(" ON  zan.KAI_CODE     = dep.KAI_CODE ");
			sql.add(" AND zan.ZAN_DEP_CODE = dep.DEP_CODE ");

			sql.add(" LEFT OUTER JOIN DEN_SYU_MST syu ");
			sql.add(" ON  swk.KAI_CODE    = syu.KAI_CODE ");
			sql.add(" AND swk.SWK_DEN_SYU = syu.DEN_SYU_CODE ");

			sql.add(" INNER JOIN AP_CBK_MST cbk ");
			sql.add(" ON  zan.KAI_CODE     = cbk.KAI_CODE ");
			sql.add(" AND zan.ZAN_CBK_CODE = cbk.CBK_CBK_CODE ");

			sql.add(" INNER JOIN CUR_MST cur ");
			sql.add(" ON  cbk.KAI_CODE = cur.KAI_CODE ");
			sql.add(" AND cbk.CUR_CODE = cur.CUR_CODE ");

			sql.add(" WHERE (zan.KAI_CODE , zan.ZAN_SEI_DEN_NO) IN ( ");
			sql.add("     SELECT KAI_CODE , SWK_DEN_NO FROM " + slipUpdateTable);
			sql.add(" ) ");

			sql.add(" ORDER BY zan.KAI_CODE , zan.ZAN_AR_DATE , zan.ZAN_SEI_DEN_NO ");
			
			statement = DBUtil.getStatement(conn);
			rs = DBUtil.select(statement, sql);

			// 初期化
			curCode = null;
			inKin = BigDecimal.ZERO;
			kin = BigDecimal.ZERO;
			
			while (rs.next()) {
				//銀行口座通貨
				curCode = rs.getString("CUR_CODE");
				// 金額セット
				inKin = rs.getBigDecimal("ZAN_SEI_IN_KIN");
				kin = rs.getBigDecimal("ZAN_SEI_KIN");
				
				if (Util.equals(curCode, rs.getString("ZAN_CUR_CODE"))) {
					// 口座通貨と取引通貨が同一 はそのまま

				} else if (Util.equals(curCode, keyCurCode)) {
					// 取引通貨と基軸通貨が同一
					inKin = kin;
				} else {
					// 以外は計算より求める
					BigDecimal rate = rateMn.getRate(curCode, rs.getDate("ZAN_AR_DATE"));
					inKin = convertToForeign(kin, rate, rs.getInt("RATE_POW"), rs.getInt("DEC_KETA"), company);
				}

				// 登録共通
				sql = new VCreator();
				sql.add(sqlIns);
				sql.add("     ? ", rs.getString("KAI_CODE"));
				sql.add("    ,? ", rs.getDate("ZAN_AR_DATE"));
				sql.add("    ,? ", rs.getString("ZAN_SEI_DEN_NO"));
				sql.add("    ,? ", rs.getString("ZAN_TRI_CODE"));
				sql.add("    ,? ", rs.getString("TRI_NAME"));
				sql.add("    ,? ", rs.getString("ZAN_DEP_CODE"));
				sql.add("    ,? ", rs.getString("DEP_NAME"));
				sql.add("    ,? ", rs.getString("SWK_TEK"));
				sql.add("    ,? ", rs.getString("SWK_DEN_SYU"));
				sql.add("    ,? ", rs.getString("DEN_SYU_NAME"));
				sql.add("    ,? ", curCode);
				sql.add("    ,? ", kin); // 符号そのまま
				sql.add("    ,? ", inKin); // 符号そのまま
				sql.add("    ,? ", rs.getString("ZAN_CBK_CODE"));
				sql.add("    ,0 "); // DATA_KBN
				sql.add("    ,? ", rs.getString("SWK_SYS_KBN"));
				sql.add("    ,2 "); // DATA_TYPE
				sql.add("    ,? ", rs.getDate("ZAN_SEI_DEN_DATE"));
				sql.add("    ,? ", rs.getString("ZAN_SEI_DEN_NO"));
				sql.add("    ,? ", rs.getInt("ZAN_SEI_GYO_NO"));
				sql.add("    ,? ", rs.getString("ZAN_SEI_NO"));
				sql.add("    ,? ", rs.getString("ZAN_DEP_CODE"));
				sql.add("    ,? ", rs.getString("ZAN_TRI_CODE"));
				sql.adt("    ,? ", rs.getTimestamp("INP_DATE"));
				sql.adt("    ,? ", rs.getTimestamp("UPD_DATE"));
				sql.add("    ,? ", rs.getString("PRG_ID"));
				sql.add("    ,? ", rs.getString("USR_ID"));
				sql.add(" ) ");

				DBUtil.execute(sql);
			}
			
			DBUtil.close(rs);
			DBUtil.close(statement);
			
		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * 基軸通貨→入力金額
	 * 
	 * @param keyAmount 基軸通貨金額
	 * @param rate レート
	 * @param ratePow 外貨通貨レート係数
	 * @param decimalPoints 外貨通貨小数点以下桁数
	 * @param company
	 * @return 入力金額
	 */
	public BigDecimal convertToForeign(BigDecimal keyAmount, BigDecimal rate, int ratePow, int decimalPoints,
		Company company) {

		if (rate == null) {
			return null;
		}

		if (keyAmount == null) {
			return null;
		}

		if (DecimalUtil.isNullOrZero(rate)) {
			return BigDecimal.ZERO;
		}

		if (DecimalUtil.isNullOrZero(keyAmount)) {
			return BigDecimal.ZERO;
		}

		AccountConfig conf = company.getAccountConfig();
		ExchangeFraction frac = conf.getExchangeFraction();

		// 換算
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(frac);
		param.setConvertType(conf.getConvertType());
		param.setDigit(decimalPoints);
		param.setKeyAmount(keyAmount);
		param.setRate(rate);
		param.setRatePow(ratePow);

		return calculator.exchangeForeignAmount(param);
	}

	/**
	 * REVALUATION_CTLのSTATUS_KBNを更新する
	 * 
	 * @param date
	 * @param companyCode
	 * @throws TException
	 */
	public void checkStatus(Date date, String companyCode) throws TException {

		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();
			Date proc_ym = null;
			boolean isChk = false;
			List<String> list = new ArrayList<String>();
			list.add("01A");
			list.add("01Z");

			String baseCurrencyCode = getCompany().getAccountConfig().getKeyCurrency().getCode();

			sql.add(" SELECT ");
			sql.add("    hdr.KAI_CODE ");
			sql.add("	,PKG_DATE_UTIL_getTukidoEnd(hdr.SWK_DEN_DATE) AS END_MONTH ");
			sql.add("	,CASE WHEN COUNT(*) <> 0 THEN 1 ELSE 0 END ");
			sql.add(" FROM GL_SWK_HDR hdr ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND hdr.KAI_CODE = ? ", companyCode);
			sql.add(" AND hdr.SWK_DEN_SYU IN ? ", list);

			if (DBUtil.isUseMySQL) {
				sql.add("AND DATE_FORMAT(hdr.SWK_DEN_DATE,'%Y/%m') ");
			} else {
				sql.add("AND TO_CHAR(hdr.SWK_DEN_DATE,'YYYY/MM') ");
			}

			sql.add(" IN ( ");
			sql.add(" SELECT DISTINCT dtl.SWK_DEN_YM FROM ( ");

			if (DBUtil.isUseMySQL) {
				sql.add(" SELECT DISTINCT DATE_FORMAT(swk.SWK_DEN_DATE,'%Y/%m') SWK_DEN_YM");
			} else {
				sql.add(" SELECT DISTINCT TO_CHAR(swk.SWK_DEN_DATE,'YYYY/MM') SWK_DEN_YM");
			}

			sql.add(" FROM SWK_DTL swk ");
			sql.add("     INNER JOIN SIM_CTL sim ");
			sql.add("     ON swk.KAI_CODE = sim.KAI_CODE ");
			sql.add("     INNER JOIN KMK_MST kmk ");
			sql.add("     ON  swk.KAI_CODE     = kmk.KAI_CODE ");
			sql.add("     AND swk.SWK_KMK_CODE = kmk.KMK_CODE ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND swk.KAI_CODE = ? ", companyCode);
			sql.add(" AND swk.DEN_SYU_CODE NOT IN ? ", list);
			sql.add(" AND swk.SWK_UPD_KBN = ? ", SlipState.APPROVE.value);
			sql.add(" AND swk.SWK_CUR_CODE <> ? ", baseCurrencyCode);
			sql.add(" AND NVL(kmk.EXC_FLG,0) <> 0 ");
			sql.add(" AND NVL(kmk.HKM_KBN,0) = 0 ");

			if (DBUtil.isUseMySQL) {
				sql.add(" AND swk.SWK_DEN_DATE >= TIMESTAMPADD(MONTH,sim.SIM_MON,sim.SIM_STR_DATE) ");
			} else {
				sql.add(" AND swk.SWK_DEN_DATE >= ADD_MONTHS(sim.SIM_STR_DATE, sim.SIM_MON) ");
			}

			sql.add(" AND swk.SWK_DEN_DATE <= PKG_DATE_UTIL_getTukidoEnd(?) ", date);
			sql.add(" UNION ALL ");

			if (DBUtil.isUseMySQL) {
				sql.add(" SELECT DISTINCT DATE_FORMAT(swk.SWK_DEN_DATE,'%Y/%m') SWK_DEN_YM ");
			} else {
				sql.add(" SELECT DISTINCT TO_CHAR(swk.SWK_DEN_DATE,'YYYY/MM') SWK_DEN_YM ");
			}

			sql.add(" FROM SWK_DTL swk ");
			sql.add("     INNER JOIN SIM_CTL sim ");
			sql.add("     ON swk.KAI_CODE = sim.KAI_CODE ");
			sql.add("     INNER JOIN KMK_MST kmk ");
			sql.add("     ON  swk.KAI_CODE     = kmk.KAI_CODE ");
			sql.add("     AND swk.SWK_KMK_CODE = kmk.KMK_CODE ");
			sql.add("     INNER JOIN HKM_MST hkm ");
			sql.add("     ON  swk.KAI_CODE     = hkm.KAI_CODE ");
			sql.add("     AND swk.SWK_KMK_CODE = hkm.KMK_CODE ");
			sql.add("     AND swk.SWK_HKM_CODE = hkm.HKM_CODE ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND swk.KAI_CODE = ? ", companyCode);
			sql.add(" AND swk.DEN_SYU_CODE NOT IN ? ", list);
			sql.add(" AND swk.SWK_UPD_KBN = ? ", SlipState.APPROVE.value);
			sql.add(" AND swk.SWK_CUR_CODE <> ? ", baseCurrencyCode);
			sql.add(" AND NVL(kmk.HKM_KBN,0) = 1 ");
			sql.add(" AND NVL(hkm.UKM_KBN,0) = 0 ");
			sql.add(" AND NVL(hkm.EXC_FLG,0) <> 0 ");

			if (DBUtil.isUseMySQL) {
				sql.add(" AND swk.SWK_DEN_DATE >= TIMESTAMPADD(MONTH,sim.SIM_MON,sim.SIM_STR_DATE) ");
			} else {
				sql.add(" AND swk.SWK_DEN_DATE >= ADD_MONTHS(sim.SIM_STR_DATE, sim.SIM_MON) ");
			}

			sql.add(" AND swk.SWK_DEN_DATE <= PKG_DATE_UTIL_getTukidoEnd(?) ", date);
			sql.add(" UNION ALL ");

			if (DBUtil.isUseMySQL) {
				sql.add(" SELECT DISTINCT DATE_FORMAT(swk.SWK_DEN_DATE,'%Y/%m') SWK_DEN_YM ");
			} else {
				sql.add(" SELECT DISTINCT TO_CHAR(swk.SWK_DEN_DATE,'YYYY/MM') SWK_DEN_YM ");
			}

			sql.add(" FROM SWK_DTL swk ");
			sql.add("     INNER JOIN SIM_CTL sim ");
			sql.add("     ON swk.KAI_CODE = sim.KAI_CODE ");
			sql.add("     INNER JOIN KMK_MST kmk ");
			sql.add("     ON  swk.KAI_CODE     = kmk.KAI_CODE ");
			sql.add("     AND swk.SWK_KMK_CODE = kmk.KMK_CODE ");
			sql.add("     INNER JOIN HKM_MST hkm ");
			sql.add("     ON  swk.KAI_CODE     = hkm.KAI_CODE ");
			sql.add("     AND swk.SWK_KMK_CODE = hkm.KMK_CODE ");
			sql.add("     AND swk.SWK_HKM_CODE = hkm.HKM_CODE ");
			sql.add("     INNER JOIN UKM_MST ukm ");
			sql.add("     ON  swk.KAI_CODE     = ukm.KAI_CODE ");
			sql.add("     AND swk.SWK_KMK_CODE = ukm.KMK_CODE ");
			sql.add("     AND swk.SWK_HKM_CODE = ukm.HKM_CODE ");
			sql.add("     AND swk.SWK_UKM_CODE = ukm.UKM_CODE ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND swk.KAI_CODE = ? ", companyCode);
			sql.add(" AND swk.DEN_SYU_CODE NOT IN ? ", list);
			sql.add(" AND swk.SWK_UPD_KBN = ? ", SlipState.APPROVE.value);
			sql.add(" AND swk.SWK_CUR_CODE <> ? ", baseCurrencyCode);
			sql.add(" AND NVL(kmk.HKM_KBN,0) = 1 ");
			sql.add(" AND NVL(hkm.UKM_KBN,0) = 1 ");
			sql.add(" AND NVL(ukm.EXC_FLG,0) <> 0 ");

			if (DBUtil.isUseMySQL) {
				sql.add(" AND swk.SWK_DEN_DATE >= TIMESTAMPADD(MONTH,sim.SIM_MON,sim.SIM_STR_DATE) ");
			} else {
				sql.add(" AND swk.SWK_DEN_DATE >= ADD_MONTHS(sim.SIM_STR_DATE, sim.SIM_MON) ");
			}

			sql.add(" AND swk.SWK_DEN_DATE <= PKG_DATE_UTIL_getTukidoEnd(?) ", date);
			sql.add(" ) ");
			sql.add(" dtl ");
			sql.add(" ) ");
			sql.add(" GROUP BY hdr.KAI_CODE, PKG_DATE_UTIL_getTukidoEnd(hdr.SWK_DEN_DATE) ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				isChk = true;
				proc_ym = rs.getDate("END_MONTH");
			}

			// 外貨評価対象の伝票の更新時に実行
			if (isChk) {

				isChk = false;

				sql = new VCreator();

				sql.add("");
				sql.add(" SELECT PROC_YM ");
				sql.add(" FROM REVALUATION_CTL ");
				sql.add(" WHERE KAI_CODE = ? ", companyCode);
				sql.add(" AND   PROC_YM = PKG_DATE_UTIL_getTukidoEnd(?) ", proc_ym);

				statement = DBUtil.getStatement(conn);
				rs = DBUtil.select(statement, sql);

				isChk = rs.next();

				if (isChk) {
					// レコードが存在する場合は｢UPDATE｣
					sql = new VCreator();

					sql.add("");
					sql.add(" UPDATE REVALUATION_CTL SET ");
					sql.add("     STATUS_KBN = 1 ");
					sql.addYMDHMS("    ,UPD_DATE = ? ", getNow());
					sql.add("    ,PRG_ID = ? ", getProgramCode());
					sql.add("    ,USR_ID = ? ", getUserCode());
					sql.add(" WHERE  KAI_CODE = ? ", companyCode);
					sql.add(" AND PROC_YM = PKG_DATE_UTIL_getTukidoEnd(?) ", proc_ym);

					DBUtil.execute(conn, sql);

				} else {
					// レコードが存在しない場合は｢INSERT｣
					sql = new VCreator();

					sql.add("");
					sql.add(" INSERT INTO REVALUATION_CTL ( ");
					sql.add("        KAI_CODE ");
					sql.add("       ,PROC_YM ");
					sql.add("       ,STATUS_KBN ");
					sql.add("       ,INP_DATE ");
					sql.add("       ,PRG_ID ");
					sql.add("       ,USR_ID ");
					sql.add(" ) VALUES ( ");
					sql.add("        ? ", companyCode);
					sql.add("		,PKG_DATE_UTIL_getTukidoEnd(?) ", proc_ym);
					sql.add("       ,1 ");
					sql.addYMDHMS("	,? ", getNow());
					sql.add("       ,? ", getProgramCode());
					sql.add("       ,? ", getUserCode());
					sql.add(" ) ");

					DBUtil.execute(conn, sql);
				}

			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 外貨評価替伝票の存在チェック
	 * 
	 * @param companyCode
	 * @throws TException
	 */
	public List<Date> existSlip(String companyCode) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Date> list = new ArrayList<Date>();

		try {

			VCreator sql = new VCreator();

			sql.add(" SELECT PROC_YM ");
			sql.add(" FROM REVALUATION_CTL ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND KAI_CODE = ? ", companyCode);
			sql.add(" AND STATUS_KBN = 1 ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(rs.getDate("PROC_YM"));
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return list;
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