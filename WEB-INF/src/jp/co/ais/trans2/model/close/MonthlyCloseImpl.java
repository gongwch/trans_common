package jp.co.ais.trans2.model.close;

import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 月次締め処理の実装
 * 
 * @author AIS
 */
public class MonthlyCloseImpl extends TModel implements MonthlyClose {

	public FiscalPeriod closeMonthly(String companyCode, FiscalPeriod fp) throws TException {

		// 締める月が決算月かを判断
		boolean isSettltmentMonth = fp.isSettlementMonth(fp.getFirstDateOfCurrentPeriodOfSettlement(),
			fp.getSettlementTerm(), fp.getMonthBeginningOfPeriod());

		// 通常月の場合は月を加算
		if (!isSettltmentMonth) {
			fp.setClosedMonth(fp.getClosedMonth() + 1);
			fp.setSettlementStage(0);

			// 決算月の場合
		} else {

			// 前月が最大まで締まっていれば月を加算
			if (fp.getFiscalMonth(fp.getFirstDateOfCurrentPeriodOfSettlement()) != fp.getClosedMonth()) {
				fp.setClosedMonth(fp.getClosedMonth() + 1);
				fp.setSettlementStage(0);
			} else if (fp.getClosedMonth() == 0 && fp.getSettlementStage() == 0) {
				fp.setClosedMonth(fp.getClosedMonth() + 1);
				fp.setSettlementStage(0);

			} else {
				fp.setSettlementStage(fp.getSettlementStage() + 1);
			}

		}

		// 年度繰越
		if (fp.getClosedMonth() == 12 && fp.getSettlementStage() == fp.getMaxSettlementStage()) {
			fp.setClosedMonth(0);
			fp.setSettlementStage(0);
			fp.setFiscalYear(fp.getFiscalYear() + 1);
			fp.setDateBeginningOfPeriod(DateUtil.getFirstDate(DateUtil.addYear(fp.getDateBeginningOfPeriod(), 1)));
			fp.setDateEndOfPeriod(DateUtil.addDay(
				DateUtil.getFirstDate(DateUtil.addYear(fp.getDateBeginningOfPeriod(), 1)), -1));

		}

		CloseDao dao = (CloseDao) getComponent(CloseDao.class);
		dao.update(companyCode, fp);

		// 締め情報取り直し
		CompanyManager cm = (CompanyManager) getComponent(CompanyManager.class);
		Company company = cm.get(companyCode);

		return company.getFiscalPeriod();

	}

	public boolean isExistsNotClosedSlip(String companyCode, FiscalPeriod fp) throws TException {

		Connection conn = DBUtil.getConnection();
		int count = 0;
		List<Integer> list = new ArrayList<Integer>();
		list.add(SlipState.FIELD_DENY.value);
		list.add(SlipState.FIELD_APPROVE.value);

		try {

			Date lastDate = DateUtil.getLastDate(fp.getFirstDateOfCurrentPeriodOfSettlement());

			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT ");
			sql.add("   COUNT(1) ");
			sql.add(" FROM  SWK_DTL ");
			sql.add(" WHERE KAI_CODE = ? ", companyCode);

			// 現場承認を使用しない場合｢現場否認｣、｢現場承認｣のチェック不要
			if (!getCompany().getAccountConfig().isUseFieldApprove()) {
				sql.adi(" AND   SWK_UPD_KBN NOT IN ?", list);
			}

			sql.add(" AND   SWK_UPD_KBN <> ? ", SlipState.UPDATE.value);
			sql.add(" AND   SWK_DEN_DATE <= ? ", lastDate);
			sql.add(" AND   CASE WHEN SWK_DEN_DATE = ? THEN SWK_KSN_KBN ", lastDate);
			sql.add("         ELSE 0 END <= ? ", fp.getCurrentSettlementStage());

			count = DBUtil.selectOneInt(conn, sql.toSQL());

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return (count != 0);
	}

	public boolean isExistsForeignCurrencyRevaluationSlip(String companyCode, FiscalPeriod fp) throws TException {

		Connection conn = DBUtil.getConnection();
		int count = 0;

		try {

			Date lastDate = DateUtil.getLastDate(fp.getFirstDateOfCurrentPeriodOfSettlement());

			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT ");
			sql.add("   COUNT(*) ");
			sql.add(" FROM  GL_SWK_HDR ");
			sql.add(" WHERE KAI_CODE = ? ", companyCode);
			sql.add(" AND   SWK_DEN_DATE <= ? ", lastDate);
			sql.add(" AND   CASE WHEN SWK_DEN_DATE = ? THEN SWK_KSN_KBN ", lastDate);
			sql.add("         ELSE 0 END <= ? ", fp.getCurrentSettlementStage());
			sql.add(" AND   SWK_DEN_SYU = '01Z' "); // 概算伝票種別

			count = DBUtil.selectOneInt(conn, sql.toSQL());

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return (count != 0);
	}

	public boolean isUpdateRevaluationSlip(String companyCode, FiscalPeriod fp) throws TException {

		Connection conn = DBUtil.getConnection();
		int statusKbn = 0;

		try {

			Date lastDate = DateUtil.getLastDate(fp.getFirstDateOfCurrentPeriodOfSettlement());

			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT ");
			sql.add("   STATUS_KBN ");
			sql.add(" FROM  REVALUATION_CTL ");
			sql.add(" WHERE KAI_CODE = ? ", companyCode);
			sql.add(" AND   PROC_YM  = ? ", lastDate);

			statusKbn = DBUtil.selectOneInt(conn, sql.toSQL());

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return (statusKbn == 1);
	}

	/**
	 * 支払処理が完了していないデータを取得する。
	 * 
	 * @param companyCode 会社コード
	 * @param fp 会計期間
	 * @return 支払処理が完了していないデータ
	 * @throws TException
	 */
	public List<Message> getNotPaidData(String companyCode, FiscalPeriod fp) throws TException {

		Connection conn = DBUtil.getConnection();
		List<Message> list = new ArrayList<Message>();

		try {

			Date lastDate = DateUtil.getLastDate(fp.getFirstDateOfCurrentPeriodOfSettlement());

			// 支払予定日が締める場合、エラー
			// 取得条件
			// 支払伝票起動日がNULL、且つ今回支払日<=締め日
			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT DISTINCT ");
			sql.add("     zan.ZAN_DEN_NO, ");
			sql.add("     zan.ZAN_SIHA_CODE, ");
			sql.add("     tri.TRI_NAME_S, ");
			sql.add("     zan.ZAN_SIHA_DATE ");
			sql.add(" FROM AP_CTL ctl  ");
			sql.add("INNER JOIN AP_ZAN zan ON ctl.KAI_CODE = zan.KAI_CODE  ");
			sql.add("                     AND ctl.CNT_UTK_NO = zan.ZAN_UTK_NO  ");
			sql.add(" LEFT JOIN TRI_MST tri ON tri.KAI_CODE = zan.KAI_CODE  ");
			sql.add("                      AND tri.TRI_CODE = zan.ZAN_SIHA_CODE  ");
			sql.add(" WHERE ctl.KAI_CODE = ? ", companyCode);
			sql.add(" AND   ctl.CNT_STR_AUT_DATE IS NULL ");
			sql.add(" AND   ctl.CNT_CUR_SIHA_DATE <= ? ", lastDate);

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingNotPaidData(rs));
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
	 * @param rs
	 * @return メッセージ
	 * @throws Exception
	 */
	protected Message mappingNotPaidData(ResultSet rs) throws Exception {
		// I00427：伝票番号：{0}、支払先：{1} {2}、支払予定日：{3}
		Message msg = new Message("I00427", rs.getString("ZAN_DEN_NO"), rs.getString("ZAN_SIHA_CODE"),
			rs.getString("TRI_NAME_S"), DateUtil.toYMDString(rs.getDate("ZAN_SIHA_DATE")));
		return msg;
	}

	/**
	 * 燃料管理情報を取得する<br>
	 * 残油が残っている船・油種区分の伝票日付
	 * 
	 * @return list: 燃料締め情報beanリスト<br>
	 *         vessel_code: 船コード<br>
	 *         oil_type_kbn: 油種区分コード<br>
	 *         den_date: 伝票日付の年月の最終日付
	 * @throws TException
	 */
	public List<BMCloseInfo> getBM() throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT   ");
			sql.add("      ctl.VESSEL_CODE  ");
			sql.add("     ,vsl.VESSEL_NAME  ");
			sql.add("     ,ctl.OIL_TYPE_KBN  ");
			sql.add("     ,oil.OIL_TYPE_NAME ");
			sql.add("     ,MIN(ctl.DEN_DATE) DEN_DATE ");
			sql.add("FROM BM_CTL ctl   ");
			sql.add("LEFT OUTER JOIN BM_BOM_DTL bom ON  ctl.KAI_CODE    = bom.KAI_CODE  ");
			sql.add("                              AND ctl.DEN_DATE     = bom.SWK_DEN_DATE  ");
			sql.add("                              AND ctl.VESSEL_CODE  = bom.VESSEL_CODE  ");
			sql.add("                              AND ctl.OIL_TYPE_KBN = bom.OIL_TYPE_KBN  ");
			sql.add("LEFT OUTER JOIN CM_VESSEL_MST vsl   ON vsl.KAI_CODE    = ctl.KAI_CODE ");
			sql.add("                                   AND vsl.VESSEL_CODE = ctl.VESSEL_CODE ");
			sql.add("LEFT OUTER JOIN BM_OIL_TYPE_MST oil ON oil.KAI_CODE    = ctl.KAI_CODE  ");
			sql.add("                                   AND oil.OIL_TYPE    = ctl.OIL_TYPE_KBN ");
			sql.add("WHERE ctl.KAI_CODE = ? ", getCompanyCode());
			sql.add(" AND   oil.TATE_KBN = 0 ");
			sql.add(" AND   bom.BOM <> 0 ");
			sql.add("GROUP BY ctl.VESSEL_CODE  ");
			sql.add("        ,vsl.VESSEL_NAME  ");
			sql.add("        ,ctl.OIL_TYPE_KBN  ");
			sql.add("        ,oil.OIL_TYPE_NAME ");
			sql.add("ORDER BY ctl.VESSEL_CODE  ");
			sql.add("        ,ctl.OIL_TYPE_KBN  ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			List<BMCloseInfo> list = mappingBM(rs);

			DBUtil.close(rs);
			DBUtil.close(statement);

			return list;

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 戻り値をマッピング<br>
	 * list: 燃料締め情報beanリスト<br>
	 * vessel_code: 船コード<br>
	 * oil_type_kbn: 油種区分コード<br>
	 * den_date: 伝票日付の年月の最終日付
	 * 
	 * @param rs
	 * @return BM情報
	 * @throws Exception
	 */
	protected List<BMCloseInfo> mappingBM(ResultSet rs) throws Exception {
		List<BMCloseInfo> list = new ArrayList();
		while (rs.next()) {
			BMCloseInfo info = new BMCloseInfo();
			info.setVESSEL_CODE(rs.getString("VESSEL_CODE"));
			info.setVESSEL_NAME(rs.getString("VESSEL_NAME"));
			info.setOIL_TYPE_KBN(rs.getString("OIL_TYPE_KBN"));
			info.setOIL_TYPE_NAME(rs.getString("OIL_TYPE_NAME"));
			info.setDEN_DATE(DateUtil.getLastDate(rs.getDate("DEN_DATE")));
			list.add(info);
		}
		return list;
	}

	/**
	 * 燃料管理情報を取得する<br>
	 * 残油が残っている船・油種区分の伝票日付
	 * 
	 * @param fp
	 * @return list: 燃料締め情報beanリスト<br>
	 *         vessel_code: 船コード<br>
	 *         oil_type_kbn: 油種区分コード<br>
	 *         den_date: 伝票日付の年月の最終日付
	 * @throws TException
	 */
	public List<BunkerInfo> getBunker(FiscalPeriod fp) throws TException {
		Connection conn = DBUtil.getConnection();
		try {
			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT   ");
			sql.add("      bnkr.GYO_NO ");
			sql.add("     ,bnkr.DEN_DATE  ");
			sql.add("     ,bnkr.DEN_YM  ");
			sql.add("     ,bnkr.DEN_NO  ");
			sql.add("     ,bnkr.SEISAN_DEN_NO  ");
			sql.add("     ,bnkr.DATA_KBN  ");
			sql.add("     ,bnkr.VESSEL_CODE  ");
			sql.add("     ,vsl.VESSEL_NAME  ");
			sql.add("     ,bnkr.OIL_TYPE_KBN  ");
			sql.add("     ,oil.OIL_TYPE_NAME ");
			sql.add("FROM BM_BUNKER_DTL bnkr   ");
			sql.add("LEFT OUTER JOIN CM_VESSEL_MST vsl   ON vsl.KAI_CODE    = bnkr.KAI_CODE ");
			sql.add("                                   AND vsl.VESSEL_CODE = bnkr.VESSEL_CODE ");
			sql.add("LEFT OUTER JOIN BM_OIL_TYPE_MST oil ON oil.KAI_CODE    = bnkr.KAI_CODE  ");
			sql.add("                                   AND oil.OIL_TYPE    = bnkr.OIL_TYPE_KBN ");
			sql.add("WHERE bnkr.KAI_CODE = ? ", getCompanyCode());
			sql.add(" AND    bnkr.DEN_DATE >= ?", fp.getFirstDateOfCurrentPeriodOfSettlement());
			sql.add(" AND    bnkr.DEN_DATE <= ?",
				DateUtil.getLastDate(DateUtil.addMonth(fp.getFirstDateOfClosedPeriodOfSettlement(), 1)));
			sql.add(" AND    bnkr.SEISAN_DEN_NO IS NULL ");
			sql.add(" AND    oil.TATE_KBN <> 1 "); // 立替の場合は対象外
			sql.add(" AND    bnkr.DATA_KBN = 1");
			sql.add(" AND    EXISTS ( SELECT 1 FROM ( ");
			sql.add("                         SELECT DISTINCT ");
			sql.add("                             bnk.KAI_CODE ");
			sql.add("                            ,bnk.VESSEL_CODE ");
			sql.add("                            ,bnk.OIL_TYPE_KBN ");
			sql.add("                         FROM BM_BUNKER_DTL bnk ");
			sql.add("                         WHERE KAI_CODE = ? ", getCompanyCode());
			sql.add("                         AND   NOT EXISTS (SELECT 1 FROM BM_BOM_DTL bom ");
			sql.add("                                           WHERE bnk.KAI_CODE     = bom.KAI_CODE ");
			sql.add("                                           AND   bnk.VESSEL_CODE  = bom.VESSEL_CODE ");
			sql.add("                                           AND   bnk.OIL_TYPE_KBN = bom.OIL_TYPE_KBN ");
			sql.add("                                           AND   bom.BOM = 0 ");
			sql.add("                                           AND   bom.KAI_CODE =  ? ", getCompanyCode());
			sql.add("                                           AND   bom.SWK_DEN_DATE >= ?",
				fp.getFirstDateOfCurrentPeriodOfSettlement());
			sql.add("                                           AND   bom.SWK_DEN_DATE <= ?",
				DateUtil.getLastDate(DateUtil.addMonth(fp.getFirstDateOfClosedPeriodOfSettlement(), 1)));
			sql.add("                    )) bnk ");
			sql.add("                    WHERE bnk.KAI_CODE     = bnkr.KAI_CODE ");
			sql.add("                    AND   bnk.VESSEL_CODE  = bnkr.VESSEL_CODE ");
			sql.add("                    AND   bnk.OIL_TYPE_KBN = bnkr.OIL_TYPE_KBN ");
			sql.add("                   ) ");
			sql.add(" AND    bnkr.DEN_NO NOT IN (");
			sql.add("                SELECT   ");
			sql.add("                    bnkr.DEN_NO  ");
			sql.add("                FROM BM_BUNKER_DTL bnkr   ");
			sql.add("                WHERE bnkr.DEN_NO IN (SELECT SEISAN_DEN_NO FROM BM_BUNKER_DTL)");

			sql.add("             )");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			List<BunkerInfo> list = mappingBunker(rs);

			DBUtil.close(rs);
			DBUtil.close(statement);

			return list;

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 戻り値をマッピング<br>
	 * list: 燃料締め情報beanリスト<br>
	 * vessel_code: 船コード<br>
	 * oil_type_kbn: 油種区分コード<br>
	 * den_date: 伝票日付の年月の最終日付
	 * 
	 * @param rs
	 * @return Bunker情報
	 * @throws Exception
	 */
	protected List<BunkerInfo> mappingBunker(ResultSet rs) throws Exception {
		List<BunkerInfo> list = new ArrayList();
		while (rs.next()) {
			BunkerInfo info = new BunkerInfo();
			info.setVESSEL_CODE(rs.getString("VESSEL_CODE"));
			info.setVESSEL_NAME(rs.getString("VESSEL_NAME"));
			info.setOIL_TYPE_KBN(rs.getString("OIL_TYPE_KBN"));
			info.setOIL_TYPE_NAME(rs.getString("OIL_TYPE_NAME"));
			info.setDEN_DATE(DateUtil.getLastDate(rs.getDate("DEN_DATE")));
			list.add(info);
		}
		return list;
	}

	/**
	 * 月次更新を取消する
	 * 
	 * @return 月次更新の取消
	 * @throws TException
	 */
	public FiscalPeriod cancelMonthly(String companyCode, FiscalPeriod fp) throws TException {

		// 締める月が決算月かを判断
		boolean isSettltmentMonth = fp.isSettlementMonth(fp.getFirstDateOfCurrentPeriodOfSettlement(),
			fp.getSettlementTerm(), fp.getMonthBeginningOfPeriod());

		// 締める月の前月が決算月かを判断
		boolean isBeforeSettltmentMonth = fp.isSettlementMonth(
			DateUtil.addMonth(fp.getFirstDateOfCurrentPeriodOfSettlement(), -1), fp.getSettlementTerm(),
			fp.getMonthBeginningOfPeriod());

		// 締める月の前々月が決算月かを判断
		boolean isBeforeSettltment2Month = fp.isSettlementMonth(
			DateUtil.addMonth(fp.getFirstDateOfCurrentPeriodOfSettlement(), -2), fp.getSettlementTerm(),
			fp.getMonthBeginningOfPeriod());

		// 決算段階使用
		boolean isUseSettlement = (fp.getMaxSettlementStage() != 0);

		if (fp.getClosedMonth() == 1 && fp.getSettlementStage() == 0) {
			// 取消を行うデータが締め月 = １、決算段階 = ０（締め月＝０、決算段階＝０）
			fp.setClosedMonth(0);
			fp.setSettlementStage(0);

		} else if (fp.getClosedMonth() == 0 && fp.getSettlementStage() == 0) {
			// 取消を行うデータが締め月 = ０、決算段階 = ０

			if (isUseSettlement) {
				// 決算段階を行う場合（締め月＝１２、決算段階＝最大決算段階－１）
				fp.setClosedMonth(12);
				fp.setSettlementStage(fp.getMaxSettlementStage() - 1);
			} else {
				// 決算段階を行わない場合（締め月＝１１、決算段階＝０）
				fp.setClosedMonth(11);
				fp.setSettlementStage(0);
			}
			fp.setFiscalYear(fp.getFiscalYear() - 1);
			fp.setDateBeginningOfPeriod(DateUtil.getFirstDate(DateUtil.addYear(fp.getDateBeginningOfPeriod(), -1)));
			fp.setDateEndOfPeriod(DateUtil.addDay(
				DateUtil.getFirstDate(DateUtil.addYear(fp.getDateBeginningOfPeriod(), 1)), -1));

		} else if (isSettltmentMonth) {
			// 取消を行うデータが決算月（決算段階を行う場合）

			if (fp.getSettlementTerm().equals(SettlementTerm.MONTH)) {
				// 決算伝票入力区分 = 月次

				if (fp.getSettlementStage() == 0) {
					// 決算段階 = ０（締め月＝締め月－1、決算段階＝最大決算段階）
					fp.setClosedMonth(fp.getClosedMonth() - 1);
					fp.setSettlementStage(fp.getMaxSettlementStage());
				} else {
					// 決算段階 = ０以外（締め月＝そのまま、決算段階＝決算段階－１）
					fp.setSettlementStage(fp.getSettlementStage() - 1);
				}
			} else {
				// 決算伝票入力区分 = 四半期、半期、一年

				if (fp.getSettlementStage() == 0) {
					// 決算段階 = ０（締め月＝締め月－１、決算段階＝０）
					fp.setClosedMonth(fp.getClosedMonth() - 1);
					fp.setSettlementStage(0);
				} else {
					// 決算段階 = ０以外（締め月＝そのまま、決算段階＝決算段階－１）
					fp.setSettlementStage(fp.getSettlementStage() - 1);
				}
			}

		} else if (!isSettltmentMonth) {
			// 取消を行うデータが通常月

			if (isUseSettlement) {
				// 決算段階を行う場合

				if (isBeforeSettltmentMonth) {
					// 前月が決算月（締め月＝そのまま、決算段階＝決算段階－１）
					fp.setSettlementStage(fp.getSettlementStage() - 1);
				} else {
					// 前月が通常月

					if (fp.getSettlementTerm().equals(SettlementTerm.QUARTER)) {
						// 四半期（締め月＝締め月－１、決算段階＝最大決算段階）
						fp.setClosedMonth(fp.getClosedMonth() - 1);
						fp.setSettlementStage(fp.getMaxSettlementStage());

					} else if (fp.getSettlementTerm().equals(SettlementTerm.HALF)) { // 半期

						if (isBeforeSettltment2Month) {
							// 前々月が決算月（締め月＝締め月－１、決算段階＝最大決算段階）
							fp.setClosedMonth(fp.getClosedMonth() - 1);
							fp.setSettlementStage(fp.getMaxSettlementStage());
						} else {
							// 前々月が通常月（締め月＝締め月－１、決算段階＝そのまま）
							fp.setClosedMonth(fp.getClosedMonth() - 1);
						}

					} else if (fp.getSettlementTerm().equals(SettlementTerm.YEAR)) {
						// 一年（締め月＝締め月－１、決算段階＝０）
						fp.setClosedMonth(fp.getClosedMonth() - 1);
						fp.setSettlementStage(0);
					}
				}
			} else {
				// 決算段階を行わない場合（締め月＝締め月－１、決算段階＝０）
				fp.setClosedMonth(fp.getClosedMonth() - 1);
				fp.setSettlementStage(0);
			}
		}

		CloseDao dao = (CloseDao) getComponent(CloseDao.class);
		dao.update(companyCode, fp);

		// 締め情報取り直し
		CompanyManager cm = (CompanyManager) getComponent(CompanyManager.class);
		Company company = cm.get(companyCode);

		return company.getFiscalPeriod();

	}

	/****************************************************************************************************************
	 * 以下複数会社版メソッド
	 ****************************************************************************************************************/

	/**
	 * 月次更新データを取得する
	 * 
	 * @param condition 検索条件
	 * @return 月次更新データ
	 * @throws TException
	 */
	public List<MonthData> getMonthData(MonthDataSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();
		List<MonthData> list = new ArrayList<MonthData>();

		try {

			// 会社全データ抽出
			CompanyManager cm = (CompanyManager) getComponent(CompanyManager.class);
			CompanySearchCondition cmCondition = new CompanySearchCondition();

			// 会社コード複数指定した場合
			if (condition.getCompanyCodeList() != null) {
				for (String code_ : condition.getCompanyCodeList()) {
					cmCondition.addCode(code_);
				}
			}
			List<Company> companyList = cm.get(cmCondition);

			// 会社が0件の場合、処理なし
			if (companyList == null || companyList.isEmpty()) {
				return null;
			}

			{
				// ログイン会社最優先

				Company self = null;
				List<Company> newList = new ArrayList<Company>();
				for (Company cmp : companyList) {
					if (Util.equals(cmp.getCode(), getCompanyCode())) {
						// ログイン会社の場合、一旦持つ
						self = cmp;
					} else {
						newList.add(cmp);
					}
				}

				if (self != null) {
					newList.add(0, self);
				}

				companyList = newList;
			}

			// 会社情報によって、繰り返し処理を行う
			for (Company company : companyList) {

				// 締め情報がNULLの場合、処理不要
				if (company.getFiscalPeriod() == null
					|| company.getFiscalPeriod().getFirstDateOfCurrentPeriodOfSettlement() == null) {
					continue;
				}

				// 月次処理のデータ作成
				MonthData bean = new MonthData();

				SQLCreator sql = new SQLCreator();

				sql.add("SELECT ");
				sql.add("   t.KAI_CODE ");
				sql.add("  ,SUM(CASE WHEN t.SWK_UPD_KBN = 1 THEN 1 ELSE 0 END) entry "); // 登録の個数
				sql.add("  ,SUM(CASE WHEN t.SWK_UPD_KBN = 11 THEN 1 ELSE 0 END) fieldDeny "); // 現場否認の個数
				sql.add("  ,SUM(CASE WHEN t.SWK_UPD_KBN = 2 THEN 1 ELSE 0 END) fieldApprove "); // 現場承認の個数
				sql.add("  ,SUM(CASE WHEN t.SWK_UPD_KBN = 12 THEN 1 ELSE 0 END) deny "); // 経理否認の個数
				sql.add("  ,SUM(CASE WHEN t.SWK_UPD_KBN = 3 THEN 1 ELSE 0 END) approve "); // 経理承認の個数
				sql.add(" FROM ( ");

				sql.add(" ( SELECT hdr.KAI_CODE, hdr.SWK_UPD_KBN");
				sql.add("     FROM GL_SWK_HDR hdr");
				sql.add(getWhereSql(company));
				sql.add(" ) ");

				sql.add(" UNION ALL ");

				sql.add(" ( SELECT hdr.KAI_CODE, hdr.SWK_UPD_KBN");
				sql.add("     FROM AP_SWK_HDR hdr");
				sql.add(getWhereSql(company));
				sql.add(" ) ");

				sql.add(" UNION ALL ");

				sql.add(" ( SELECT hdr.KAI_CODE, hdr.SWK_UPD_KBN");
				sql.add("     FROM AR_SWK_HDR hdr");
				sql.add(getWhereSql(company));
				sql.add(" ) ");
				sql.add("      ) t ");
				sql.add(" GROUP BY KAI_CODE ");

				Statement statement = DBUtil.getStatement(conn);
				ResultSet rs = DBUtil.select(statement, sql);

				bean.setCompany(company);
				bean.setCompanyCode(company.getCode());

				if (rs.next()) {

					bean.setEntry(rs.getInt("entry")); // 登録の個数
					bean.setFieldDeny(rs.getInt("fieldDeny")); // 現場否認の個数
					bean.setFieldApprove(rs.getInt("fieldApprove")); // 現場承認の個数
					bean.setDeny(rs.getInt("deny")); // 経理否認の個数
					bean.setApprove(rs.getInt("approve")); // 経理承認の個数

				} else {

					bean.setEntry(0); // 登録の個数
					bean.setFieldDeny(0); // 現場否認の個数
					bean.setFieldApprove(0); // 現場承認の個数
					bean.setDeny(0); // 経理否認の個数
					bean.setApprove(0); // 経理承認の個数
				}

				DBUtil.close(rs);
				DBUtil.close(statement);

				list.add(bean);
			}

		} catch (Exception e) {
			throw new TException("E00009", e);
		} finally {
			DBUtil.close(conn);
		}

		return list;

	}

	/**
	 * SQL条件WHEREの取得
	 * 
	 * @param company 会社情報
	 * @return SQL条件WHERE
	 */
	protected String getWhereSql(Company company) {
		SQLCreator sql = new SQLCreator();
		FiscalPeriod fp = company.getFiscalPeriod();

		Date lastDate = DateUtil.getLastDate(fp.getFirstDateOfCurrentPeriodOfSettlement());

		// 会社コード ＝ 会社情報の会社コード
		// 更新区分 <> 4（更新）
		// 伝票日付 <= 会社情報の締め日（締め月の末日）
		// 伝票日付 ＝ 会社情報の締め日（締め月の末日） 0 <= 会社情報の決算段階
		// 伝票日付 <> 会社情報の締め日（締め月の末日） 決算区分 <= 会社情報の決算段階

		sql.add("WHERE hdr.KAI_CODE = ? ", company.getCode());
		sql.add("  AND hdr.SWK_UPD_KBN <> 4 ");
		sql.add("  AND hdr.SWK_DEN_DATE <= ? ", lastDate);
		sql.add("  AND CASE WHEN hdr.SWK_DEN_DATE = ?  ", lastDate);
		sql.add("           THEN hdr.SWK_KSN_KBN ELSE 0 END ");
		sql.add("      <= ? ", fp.getCurrentSettlementStage());

		return sql.toSQL();
	}

	/**
	 * 伝票未作成のCM_FUND_DTLが存在したらエラー
	 * 
	 * @param companyCode 会社コード
	 * @param fp 会計期間
	 * @return 伝票未作成のCM_FUND_DTLが存在する
	 * @throws TException
	 */
	public boolean isExistsNotSlipByCmFund(String companyCode, FiscalPeriod fp) throws TException {

		Connection conn = DBUtil.getConnection();
		int count = 0;
		String[] checkSys = null;

		try {
			checkSys = ServerConfig.getProperties("trans.check.sys.cm.fund.monthly");
		} catch (Exception e) {
			// 処理なし

		}

		if (checkSys == null || checkSys.length == 0) {
			// 設定が無かったら戻る
			return false;
		}

		try {

			Date lastDate = DateUtil.getLastDate(fp.getFirstDateOfCurrentPeriodOfSettlement());

			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT COUNT(*) FROM CM_FUND_DTL ");
			sql.add(" WHERE  KAI_CODE  = ? ", companyCode);
			sql.add(" AND    DEN_DATE <= ? ", lastDate);
			sql.add(" AND    DEN_NO IS NULL ");
			sql.add(" AND    SYS_KBN IN ");
			sql.addInStatement(checkSys);

			count = DBUtil.selectOneInt(conn, sql.toSQL());

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return (count != 0);
	}
}
