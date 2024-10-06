package jp.co.ais.trans2.model.slip;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.program.*;

/**
 * 伝票種別管理の実装クラスです。
 * 
 * @author AIS
 */
public class SlipTypeManagerImpl extends TModel implements SlipTypeManager {
	

	/**
	 * 伝票種別取得
	 */
	public List<SlipType> get(SlipTypeSearchCondition condition) throws TException {
		boolean isInvoice = getCompany().isCMP_INV_CHK_FLG();

		Connection conn = DBUtil.getConnection();

		List<SlipType> list = new ArrayList<SlipType>();

		try {

			String sql =
				" SELECT " +
					" densyu.KAI_CODE, " +
					" densyu.DEN_SYU_CODE, " +
					" densyu.SYS_KBN, " +
					" densyu.DEN_SYU_NAME, " +
					" densyu.DEN_SYU_NAME_S, " +
					" densyu.DEN_SYU_NAME_K, " +
					" densyu.DATA_KBN, " +
					" densyu.TA_SYS_KBN, " +
					" densyu.DAT_SAIBAN_FLG, " +
					" densyu.TANI, " +
					" densyu.ZEI_KBN, " +
					" densyu.SWK_IN_KBN, " +
					" densyu.INP_DATE, " +
					" densyu.UPD_DATE, " +
					" densyu.PRG_ID, " +
					" densyu.USR_ID, " +
					" densyu.REV_DEN_SYU_CODE, " +
					" densyu2.DEN_SYU_NAME AS REV_DEN_SYU_NAME, " +
					" densyu2.DEN_SYU_NAME_S AS REV_DEN_SYU_NAME_S, " +
					" systype.SYS_KBN_NAME ";
			       if (isInvoice) {
				       sql = sql + " ,densyu.INV_SYS_FLG ";
			       }
				sql = sql + " FROM DEN_SYU_MST densyu " +
			    " LEFT OUTER JOIN DEN_SYU_MSt densyu2 " +
			    " ON   densyu.KAI_CODE = densyu2.KAI_CODE" +
			    " AND  densyu.REV_DEN_SYU_CODE = densyu2.DEN_SYU_CODE" +
			    " LEFT OUTER JOIN SYS_MST systype " +
			    " ON   densyu.KAI_CODE = systype.KAI_CODE " +
			    " AND  densyu.SYS_KBN = systype.SYS_KBN ";

			sql = sql + " WHERE 1 = 1 ";

			// 会社コード
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql = sql + " AND	densyu.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode());
			}

			// コード
			if (!condition.getCodeList().isEmpty()) {
				sql = sql + " AND	densyu.DEN_SYU_CODE IN " + SQLUtil.getInStatement(condition.getCodeList());
			}

			// 開始コード
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql = sql + " AND	densyu.DEN_SYU_CODE >= " + SQLUtil.getParam(condition.getCodeFrom());
			}

			// 終了コード
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql = sql + " AND	densyu.DEN_SYU_CODE <= " + SQLUtil.getParam(condition.getCodeTo());
			}

			// コード前方一致
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql = sql + " AND	densyu.DEN_SYU_CODE "
					+ SQLUtil.getLikeStatement(condition.getCodeLike(), SQLUtil.CHAR_FRONT);
			}

			// 略称あいまい
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				sql = sql + " AND	densyu.DEN_SYU_NAME_S "
					+ SQLUtil.getLikeStatement(condition.getNamesLike(), SQLUtil.NCHAR_AMBI);
			}

			// 検索名称あいまい
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql = sql + " AND	densyu.DEN_SYU_NAME_K "
					+ SQLUtil.getLikeStatement(condition.getNamekLike(), SQLUtil.NCHAR_AMBI);
			}

			if(condition.isReferOtherSystemDivision()){
				sql = sql + " AND ((densyu.TA_SYS_KBN = 1 AND densyu.SWK_IN_KBN = 0) or densyu.TA_SYS_KBN = 0)";

				sql = sql 
				+ " AND densyu.DEN_SYU_CODE NOT IN ( "
				+ "     SELECT REV_DEN_SYU_CODE FROM DEN_SYU_MST "
				+ "     WHERE KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode())
				+ "     AND   REV_DEN_SYU_CODE IS NOT NULL ) ";
				
			}

			sql = sql +
				" ORDER BY " +
					" densyu.DEN_SYU_CODE ";

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
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return 伝票種別
	 * @throws Exception
	 */
	protected SlipType mapping(ResultSet rs) throws Exception {
		SlipType bean = new SlipType();
		boolean isInvoice = getCompany().isCMP_INV_CHK_FLG();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("DEN_SYU_CODE"));
		bean.setName(rs.getString("DEN_SYU_NAME"));
		bean.setNames(rs.getString("DEN_SYU_NAME_S"));
		bean.setNamek(rs.getString("DEN_SYU_NAME_K"));
		bean.setDataType(rs.getString("DATA_KBN"));
		bean.setInnerConsumptionTaxCalculation(BooleanUtil.toBoolean(rs.getInt("ZEI_KBN")));
		bean.setSystemDiv(rs.getString("SYS_KBN"));
		bean.setTakeNewSlipNo(rs.getInt("DAT_SAIBAN_FLG") == 1);
		
		if (0 == rs.getInt("SWK_IN_KBN")) {
			bean.setSlipState(SlipState.ENTRY);
		} else {
			bean.setSlipState(SlipState.APPROVE);
		}
		
		bean.setJounalIfDivision(SlipState.getJnlIfDivName(rs.getInt("SWK_IN_KBN")));
		bean.setAnotherSystemDivision(BooleanUtil.toBoolean(rs.getInt("TA_SYS_KBN")));
		bean.setAcceptUnit(AcceptUnit.getAcceptUnit(rs.getInt("TANI")));

		if (isInvoice) {
			bean.setINV_SYS_FLG(BooleanUtil.toBoolean(rs.getInt("INV_SYS_FLG")));
		}

		// 振戻伝票種別
		if (rs.getString("REV_DEN_SYU_CODE") != null) {
			bean.setReversingCode(Util.avoidNull(rs.getString("REV_DEN_SYU_CODE"))); // 振戻伝票コード
			
			SlipType revSlipType = new SlipType();
			revSlipType.setCode(rs.getString("REV_DEN_SYU_CODE")); // 振戻伝票種別コード
			revSlipType.setName(rs.getString("REV_DEN_SYU_NAME")); // 振戻伝票種別名称
			revSlipType.setNames(rs.getString("REV_DEN_SYU_NAME_S")); // 振戻伝票種別略称
			bean.setReversingSlipType(revSlipType);
		}

		// システム区分
		SystemDivision systemDivision = new SystemDivision();
		systemDivision.setCode(rs.getString("SYS_KBN")); // システム区分コード
		systemDivision.setName(rs.getString("SYS_KBN_NAME")); // システム区分名称
		bean.setSystemDivision(systemDivision);

		return bean;
	}

	/**
	 * 特定の伝票種別情報を返す
	 * @param companyCode 会社コード
	 * @param slipTypeCode 伝票種別コード
	 * @return 特定の伝票種別情報
	 * @throws TException
	 */
	public SlipType get(String companyCode, String slipTypeCode) throws TException {

		SlipTypeSearchCondition condition = new SlipTypeSearchCondition();
		condition.setCompanyCode(companyCode);
		condition.setCode(slipTypeCode);

		List<SlipType> list = get(condition);
		if (list == null || list.isEmpty()) {
			return null;
		}

		return list.get(0);

	}

	/**
	 * 伝票種別登録
	 */
	public void entry(SlipType sliptype) throws TException {
		boolean isInvoice = getCompany().isCMP_INV_CHK_FLG();

		Connection conn = DBUtil.getConnection();

		try {

			String sql =
				" INSERT INTO DEN_SYU_MST(" +
					" KAI_CODE," +
					" DEN_SYU_CODE," +
					" SYS_KBN," +
					" DEN_SYU_NAME," +
					" DEN_SYU_NAME_S," +
					" DEN_SYU_NAME_K," +
					" DATA_KBN," +
					" TA_SYS_KBN," +
					" DAT_SAIBAN_FLG," +
					" TANI," +
					" ZEI_KBN," +
					" SWK_IN_KBN," +
					" INP_DATE," +
					" PRG_ID," +
					" USR_ID," +
					" REV_DEN_SYU_CODE ";
					if(isInvoice){
					       sql = sql + " ,INV_SYS_FLG ";
					}
			    sql = sql +
				" ) VALUES (" +
					SQLUtil.getParam(sliptype.getCompanyCode()) + ", " +
					SQLUtil.getParam(sliptype.getCode()) + ", " +
					SQLUtil.getParam(sliptype.getSystemDiv()) + ", " +
					SQLUtil.getParam(sliptype.getName()) + ", " +
					SQLUtil.getParam(sliptype.getNames()) + ", " +
					SQLUtil.getParam(sliptype.getNamek()) + ", " +
					SQLUtil.getParam(sliptype.getDataType()) + ", " +
					Integer.toString(BooleanUtil.toInt(sliptype.isAnotherSystemDivision())) + ", " +
					Integer.toString(BooleanUtil.toInt(sliptype.isTakeNewSlipNo())) + ", " +
					Integer.toString(AcceptUnit.getAcceptUnitCode(sliptype.isAcceptUnit())) + ", " +
					Integer.toString(TaxCalcType.getTaxCulKbn(sliptype.isInnerConsumptionTaxCalculation()).value) + ", " +
					Integer.toString(SlipState.getJnlIfDivCode(sliptype.getJounalIfDivision())) + ", " +
					SQLUtil.getYMDHMSParam(getNow()) + ", " +
					SQLUtil.getParam(getProgramCode()) + ", " +
					SQLUtil.getParam(getUserCode()) + ", " +
					SQLUtil.getParam(sliptype.getReversingCode());
			if (isInvoice) {
				sql = sql + ", " + Integer.toString(BooleanUtil.toInt(sliptype.isINV_SYS_FLG()));
			}
				sql = sql +
				   " ) ";

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 伝票種別更新
	 */
	public void modify(SlipType sliptype) throws TException {
		boolean isInvoice = getCompany().isCMP_INV_CHK_FLG();

		Connection conn = DBUtil.getConnection();

		try {

			String sql =
				" UPDATE DEN_SYU_MST SET" +
					" SYS_KBN = " + SQLUtil.getParam(sliptype.getSystemDiv()) + ", " +
					" DEN_SYU_NAME = " + SQLUtil.getParam(sliptype.getName()) + ", " +
					" DEN_SYU_NAME_S = " + SQLUtil.getParam(sliptype.getNames()) + ", " +
					" DEN_SYU_NAME_K = " + SQLUtil.getParam(sliptype.getNamek()) + ", " +
					" DATA_KBN = " + SQLUtil.getParam(sliptype.getDataType()) + ", " +
					" TA_SYS_KBN = " + Integer.toString(BooleanUtil.toInt(sliptype.isAnotherSystemDivision())) + ", " +
					" DAT_SAIBAN_FLG = " + Integer.toString(BooleanUtil.toInt(sliptype.isTakeNewSlipNo())) + ", " +
					" TANI = " + Integer.toString(AcceptUnit.getAcceptUnitCode(sliptype.isAcceptUnit())) + ", " +
					" ZEI_KBN = " + Integer.toString(TaxCalcType.getTaxCulKbn(sliptype.isInnerConsumptionTaxCalculation()).value) + ", " +
					" SWK_IN_KBN =" + Integer.toString(SlipState.getJnlIfDivCode(sliptype.getJounalIfDivision())) + ", " +
					" UPD_DATE = " + SQLUtil.getYMDHMSParam(getNow()) + ", " +
					" PRG_ID = " + SQLUtil.getParam(getProgramCode()) + ", " +
					" USR_ID = " + SQLUtil.getParam(getUserCode()) + ", " +
					" REV_DEN_SYU_CODE = " + SQLUtil.getParam(sliptype.getReversingCode());
			if (isInvoice) {
				sql = sql + ",INV_SYS_FLG = " + Integer.toString(BooleanUtil.toInt(sliptype.isINV_SYS_FLG()));
			}
			sql = sql +
				" WHERE KAI_CODE = " + SQLUtil.getParam(sliptype.getCompanyCode()) +
				" AND DEN_SYU_CODE = " + SQLUtil.getParam(sliptype.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 伝票種別削除
	 */
	public void delete(SlipType sliptype) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			String sql =
				" DELETE FROM DEN_SYU_MST" +
				" WHERE KAI_CODE = " + SQLUtil.getParam(sliptype.getCompanyCode()) +
				" AND DEN_SYU_CODE = " + SQLUtil.getParam(sliptype.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 伝票種別マスタ一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の伝票種別マスタ一覧
	 * @throws TException
	 */
	public byte[] getExcel(SlipTypeSearchCondition condition) throws TException {

		List<SlipType> sliptype = get(condition);
		if (sliptype == null || sliptype.isEmpty()) {
			return null;
		}

		SlipTypeExcel layout = getLayout(getUser().getLanguage(), condition);
		byte[] data = layout.getExcel(sliptype);

		return data;

	}

	/**
	 * EXCELレイアウトファクトリ
	 * 
	 * @param language
	 * @param condition
	 * @return SlipTypeExcel(language)
	 */
	protected SlipTypeExcel getLayout(String language, SlipTypeSearchCondition condition) {
		return new SlipTypeExcel(language, condition);
	}
}
