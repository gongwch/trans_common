package jp.co.ais.trans2.model.close;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.db.SQLUtil;
import jp.co.ais.trans2.define.SettlementTerm;
import jp.co.ais.trans2.model.TModel;

/**
 * 締め関連のDao実装
 * @author AIS
 *
 */
public class CloseDaoImpl extends TModel implements CloseDao {

	public FiscalPeriod getFiscalPeriod(String companyCode) throws TException {

		FiscalPeriod period = null;

		Connection conn = DBUtil.getConnection();

		try {

			String sql =
				" SELECT " +
					" cmp.KAI_CODE, " +					
					" cmp.CMP_KISYU, " +
					" sim.NENDO, " +
					" sim.SIM_MON, " +
					" sim.KSN_KBN, " +
					" sim.SIM_STR_DATE, " +
					" sim.SIM_END_DATE, " +
					" glc.KSD_KBN, " +
					" glc.KSN_NYU_KBN " +
				" FROM " +
					" CMP_MST cmp " +
					" INNER JOIN SIM_CTL sim " +
					" ON	cmp.KAI_CODE = sim.KAI_CODE " +
					" INNER JOIN GL_CTL_MST glc " +
					" ON	cmp.KAI_CODE = glc.KAI_CODE " +
				" WHERE	cmp.KAI_CODE = " + SQLUtil.getParam(companyCode);

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				period = mapping(rs);
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return period;

	}

	/**
	 * O/Rマッピング
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	protected FiscalPeriod mapping(ResultSet rs) throws Exception {

		FiscalPeriod fiscalPeriod = new FiscalPeriod();
		fiscalPeriod.setFiscalYear(rs.getInt("NENDO"));
		fiscalPeriod.setClosedMonth(rs.getInt("SIM_MON"));
		fiscalPeriod.setSettlementStage(rs.getInt("KSN_KBN"));
		fiscalPeriod.setMonthBeginningOfPeriod(rs.getInt("CMP_KISYU"));
		fiscalPeriod.setDateBeginningOfPeriod(rs.getDate("SIM_STR_DATE"));
		fiscalPeriod.setDateEndOfPeriod(rs.getDate("SIM_END_DATE"));
		int settlementTerm = rs.getInt("KSN_NYU_KBN");
		fiscalPeriod.setSettlementTerm(SettlementTerm.getSettlementTerm(settlementTerm));
		fiscalPeriod.setMaxSettlementStage(rs.getInt("KSD_KBN"));

		return fiscalPeriod;

	}

	public void update(String companyCode, FiscalPeriod fp) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			String sql =
				" UPDATE	SIM_CTL" +
				" SET " +
					" NENDO = " + Integer.toString(fp.getFiscalYear()) + ", " +
					" SIM_STR_DATE = " + SQLUtil.getYYYYMMDDParam(fp.getDateBeginningOfPeriod()) + ", " +
					" SIM_END_DATE = " + SQLUtil.getYYYYMMDDParam(fp.getDateEndOfPeriod()) + ", " +
					" SIM_MON = " + Integer.toString(fp.getClosedMonth()) + ", " +
					" KSN_KBN = " + Integer.toString(fp.getSettlementStage()) + ", " +
					" UPD_DATE = " + SQLUtil.getYMDHMSParam(getNow()) + ", " +
					" PRG_ID = " + SQLUtil.getParam(getProgramCode()) + ", " +
					" USR_ID = " + SQLUtil.getParam(getUserCode()) +
				" WHERE	KAI_CODE = " + SQLUtil.getParam(companyCode);

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

}
