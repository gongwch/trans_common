package jp.co.ais.trans2.model.slip;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;

/**
 * C/S連携用仕訳ジャーナルDao
 * 
 * @author TRANS(D) YF.CONG
 */
public class SeaCASTSWK_DTLDaoImpl implements SWK_DTLDao {

	/**
	 * 削除処理
	 * 
	 * @param param 条件
	 */
	public void deleteByCondition(SlipCondition param) {

		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			SQLCreator sql = new SQLCreator();
			sql.add("DELETE FROM " + SeaCASTConfig.getTableName("SWK_DAT") + " ");
			sql.add(" WHERE ");
			sql.add("       SWK_DENNO = ? ", param.getSlipNo());

			if (!Util.isNullOrEmpty(param.getCompanyCode())) {
				sql.add("     AND KAICODE = ? ", param.getCompanyCode());
			}

			DBUtil.execute(conn, sql);

		} catch (TException ex) {
			throw new TRuntimeException(ex);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * @see jp.co.ais.trans2.model.slip.SWK_DTLDao#findByCondition(jp.co.ais.trans2.model.slip.SlipCondition)
	 */
	public List<SWK_DTL> findByCondition(SlipCondition param) {
		Connection conn = null;
		List<SWK_DTL> list = new ArrayList<SWK_DTL>();
		try {
			conn = DBUtil.getConnection();

			SQLCreator sql = new SQLCreator();

			sql.add("SELECT");
			sql.add("	swk.KAI_CODE,");
			sql.add("	swk.SWK_DEN_DATE,");
			sql.add("	swk.SWK_DEN_NO,");
			sql.add("	swk.SWK_GYO_NO,");
			sql.add("	swk.SWK_NENDO,");
			sql.add("	swk.SWK_TUKIDO,");
			sql.add("	swk.SWK_SEI_NO,");
			sql.add("	swk.SWK_DC_KBN,");
			sql.add("	swk.SWK_KMK_CODE,");
			sql.add("	swk.SWK_HKM_CODE,");
			sql.add("	swk.SWK_UKM_CODE,");
			sql.add("	swk.SWK_DEP_CODE,");
			sql.add("	swk.SWK_ZEI_KBN,");
			sql.add("	swk.SWK_KIN,");
			sql.add("	swk.SWK_ZEI_KIN,");
			sql.add("	swk.SWK_ZEI_CODE,");
			sql.add("	swk.SWK_ZEI_RATE,");
			sql.add("	swk.SWK_GYO_TEK_CODE,");
			sql.add("	swk.SWK_GYO_TEK,");
			sql.add("	swk.SWK_TRI_CODE,");
			sql.add("	swk.SWK_EMP_CODE,");
			sql.add("	swk.SWK_KNR_CODE_1,");
			sql.add("	swk.SWK_KNR_CODE_2,");
			sql.add("	swk.SWK_KNR_CODE_3,");
			sql.add("	swk.SWK_KNR_CODE_4,");
			sql.add("	swk.SWK_KNR_CODE_5,");
			sql.add("	swk.SWK_KNR_CODE_6,");
			sql.add("	swk.SWK_HM_1,");
			sql.add("	swk.SWK_HM_2,");
			sql.add("	swk.SWK_HM_3,");
			sql.add("	swk.SWK_AUTO_KBN,");
			sql.add("	swk.SWK_DATA_KBN,");
			sql.add("	swk.SWK_UPD_KBN,");
			sql.add("	swk.SWK_KSN_KBN,");
			sql.add("	swk.SWK_AT_KMK_CODE,");
			sql.add("	swk.SWK_AT_HKM_CODE,");
			sql.add("	swk.SWK_AT_UKM_CODE,");
			sql.add("	swk.SWK_AT_DEP_CODE,");
			sql.add("	swk.INP_DATE,");
			sql.add("	swk.UPD_DATE,");
			sql.add("	swk.PRG_ID,");
			sql.add("	swk.USR_ID,");
			sql.add("	swk.SWK_K_KAI_CODE,");
			sql.add("	swk.SWK_CUR_CODE,");
			sql.add("	swk.SWK_CUR_RATE,");
			sql.add("	swk.SWK_IN_KIN,");
			sql.add("	swk.SWK_TUKE_KBN,");
			sql.add("	swk.SWK_IN_ZEI_KIN,");
			sql.add("	swk.SWK_KESI_KBN,");
			sql.add("	swk.SWK_SOUSAI_DEN_NO,");
			sql.add("	swk.HAS_DATE,");
			sql.add("	swk.SWK_BOOK_NO,");
			sql.add("	swk.SWK_ADJ_KBN,");
			sql.add("	env.KAI_NAME      SWK_K_KAI_NAME,");
			sql.add("	env.KAI_NAME_S    SWK_K_KAI_NAME_S,");
			sql.add("	emp.EMP_NAME      SWK_EMP_NAME,");
			sql.add("	emp.EMP_NAME_S    SWK_EMP_NAME_S,");
			sql.add("	dep.DEP_NAME      SWK_DEP_NAME,");
			sql.add("	dep.DEP_NAME_S    SWK_DEP_NAME_S,");
			sql.add("	kmk.KMK_NAME      SWK_KMK_NAME,");
			sql.add("	kmk.KMK_NAME_S    SWK_KMK_NAME_S,");
			sql.add("	hkm.HKM_NAME      SWK_HKM_NAME,");
			sql.add("	hkm.HKM_NAME_S    SWK_HKM_NAME_S,");
			sql.add("	ukm.UKM_NAME      SWK_UKM_NAME,");
			sql.add("	ukm.UKM_NAME_S    SWK_UKM_NAME_S,");
			sql.add("	cur.DEC_KETA      CUR_DEC_KETA,");
			sql.add("	zei.ZEI_NAME      SWK_ZEI_NAME,");
			sql.add("	zei.ZEI_NAME_S    SWK_ZEI_NAME_S,");
			sql.add("	tri.TRI_NAME      SWK_TRI_NAME,");
			sql.add("	tri.TRI_NAME_S    SWK_TRI_NAME_S,");
			sql.add("	knr1.KNR_NAME_1   SWK_KNR_NAME_1,");
			sql.add("	knr1.KNR_NAME_S_1 SWK_KNR_NAME_S_1,");
			sql.add("	knr2.KNR_NAME_2   SWK_KNR_NAME_2,");
			sql.add("	knr2.KNR_NAME_S_2 SWK_KNR_NAME_S_2,");
			sql.add("	knr3.KNR_NAME_3   SWK_KNR_NAME_3,");
			sql.add("	knr3.KNR_NAME_S_3 SWK_KNR_NAME_S_3,");
			sql.add("	knr4.KNR_NAME_4   SWK_KNR_NAME_4,");
			sql.add("	knr4.KNR_NAME_S_4 SWK_KNR_NAME_S_4,");
			sql.add("	knr5.KNR_NAME_5   SWK_KNR_NAME_5,");
			sql.add("	knr5.KNR_NAME_S_5 SWK_KNR_NAME_S_5,");
			sql.add("	knr6.KNR_NAME_6   SWK_KNR_NAME_6,");
			sql.add("	knr6.KNR_NAME_S_6 SWK_KNR_NAME_S_6,");
			sql.add("	keyCur.CUR_CODE   KEY_CUR_CODE,");
			sql.add("	keyCur.DEC_KETA   KEY_CUR_DEC_KETA,");
			sql.add("	funcCur.CUR_CODE  FUNC_CUR_CODE,");
			sql.add("	funcCur.DEC_KETA  FUNC_CUR_DEC_KETA,");
			sql.add("	NULL SWK_IRAI_DEP_CODE, ");
			sql.add("	NULL SWK_IRAI_EMP_CODE ");
			sql.add("FROM (" + getSWK_DTL() + ") swk");
			sql.add("  LEFT OUTER JOIN EMP_MST  emp   ON  swk.KAI_CODE       = emp.KAI_CODE");
			sql.add("                                AND  swk.SWK_EMP_CODE   = emp.EMP_CODE");
			sql.add("  LEFT OUTER JOIN ENV_MST  env   ON  swk.SWK_K_KAI_CODE = env.KAI_CODE");
			sql.add("  LEFT OUTER JOIN BMN_MST  dep   ON  swk.KAI_CODE       = dep.KAI_CODE");
			sql.add("                                AND  swk.SWK_DEP_CODE   = dep.DEP_CODE");
			sql.add("  LEFT OUTER JOIN KMK_MST  kmk   ON  swk.KAI_CODE       = kmk.KAI_CODE");
			sql.add("                                AND  swk.SWK_KMK_CODE   = kmk.KMK_CODE");
			sql.add("  LEFT OUTER JOIN HKM_MST  hkm   ON  swk.KAI_CODE       = hkm.KAI_CODE");
			sql.add("                                AND  swk.SWK_KMK_CODE   = hkm.KMK_CODE");
			sql.add("                                AND  swk.SWK_HKM_CODE   = hkm.HKM_CODE");
			sql.add("  LEFT OUTER JOIN UKM_MST  ukm   ON  swk.KAI_CODE       = ukm.KAI_CODE");
			sql.add("                                AND  swk.SWK_KMK_CODE   = ukm.KMK_CODE");
			sql.add("                                AND  swk.SWK_HKM_CODE   = ukm.HKM_CODE");
			sql.add("                                AND  swk.SWK_UKM_CODE   = ukm.UKM_CODE");
			sql.add("  LEFT OUTER JOIN SZEI_MST zei   ON  swk.KAI_CODE       = zei.KAI_CODE");
			sql.add("                                AND  swk.SWK_ZEI_CODE   = zei.ZEI_CODE");
			sql.add("  LEFT OUTER JOIN CUR_MST  cur   ON  swk.KAI_CODE       = cur.KAI_CODE");
			sql.add("                                AND  swk.SWK_CUR_CODE   = cur.CUR_CODE");
			sql.add("  LEFT OUTER JOIN TRI_MST  tri   ON  swk.KAI_CODE       = tri.KAI_CODE");
			sql.add("                                AND  swk.SWK_TRI_CODE   = tri.TRI_CODE");
			sql.add("  LEFT OUTER JOIN KNR1_MST knr1  ON  swk.KAI_CODE       = knr1.KAI_CODE");
			sql.add("                                AND  swk.SWK_KNR_CODE_1 = knr1.KNR_CODE_1");
			sql.add("  LEFT OUTER JOIN KNR2_MST knr2  ON  swk.KAI_CODE       = knr2.KAI_CODE");
			sql.add("                                AND  swk.SWK_KNR_CODE_2 = knr2.KNR_CODE_2");
			sql.add("  LEFT OUTER JOIN KNR3_MST knr3  ON  swk.KAI_CODE       = knr3.KAI_CODE");
			sql.add("                                AND  swk.SWK_KNR_CODE_3 = knr3.KNR_CODE_3");
			sql.add("  LEFT OUTER JOIN KNR4_MST knr4  ON  swk.KAI_CODE       = knr4.KAI_CODE");
			sql.add("                                AND  swk.SWK_KNR_CODE_4 = knr4.KNR_CODE_4");
			sql.add("  LEFT OUTER JOIN KNR5_MST knr5  ON  swk.KAI_CODE       = knr5.KAI_CODE");
			sql.add("                                AND  swk.SWK_KNR_CODE_5 = knr5.KNR_CODE_5");
			sql.add("  LEFT OUTER JOIN KNR6_MST knr6  ON  swk.KAI_CODE       = knr6.KAI_CODE");
			sql.add("                                AND  swk.SWK_KNR_CODE_6 = knr6.KNR_CODE_6");
			sql.add("  LEFT OUTER JOIN CMP_MST cmp    ON  swk.KAI_CODE       = cmp.KAI_CODE");
			sql.add("  LEFT OUTER JOIN CUR_MST keyCur ON  cmp.KAI_CODE      = keyCur.KAI_CODE");
			sql.add("                                AND  cmp.CUR_CODE      = keyCur.CUR_CODE");
			sql.add("  LEFT OUTER JOIN CUR_MST funcCur ON cmp.KAI_CODE      = funcCur.KAI_CODE");
			sql.add("                                 AND cmp.FNC_CUR_CODE  = funcCur.CUR_CODE ");
			sql.add("WHERE 1 = 1 ");

			if(!Util.isNullOrEmpty(param.getCompanyCode())){
				sql.add("  AND swk.KAI_CODE = ? ", param.getCompanyCode());
			}

			if(!Util.isNullOrEmpty(param.getSlipNo())){
				sql.add("  AND swk.SWK_DEN_NO = ? ", param.getSlipNo());
			}

			if(!Util.isNullOrEmpty(param.getSlipNoList())){
				sql.add("  AND swk.SWK_DEN_NO IN ? ", toList(param.getSlipNoList()));
			}

			if(!Util.isNullOrEmpty(param.getSlipNoLike())){
				sql.addLikeFront("  AND swk.SWK_DEN_NO ? ", param.getSlipNoLike());
			}

			if(!Util.isNullOrEmpty(param.getSlipDateFrom())){
				sql.add("  AND swk.SWK_DEN_NO >= ? ", param.getSlipDateFrom());
			}

			if(!Util.isNullOrEmpty(param.getSlipDateTo())){
				sql.add("  AND swk.SWK_DEN_NO <= ? ", param.getSlipDateTo());
			}

			if(!Util.isNullOrEmpty(param.getDataKindList())){
				sql.add("  AND swk.SWK_DATA_KBN IN ? ", toList(param.getDataKindList()));
			}

			if(!Util.isNullOrEmpty(param.getSlipTypeList())){
				sql.add("  AND swk.DEN_SYU_CODE IN ? ", toList(param.getSlipTypeList()));
			}

			if(!Util.isNullOrEmpty(param.getSlipStateList())){
				sql.add("  AND swk.SWK_UPD_KBN IN ? ", toIntList(param.getSlipStateList()));
		    }

			if(!Util.isNullOrEmpty(param.getSlipDateFrom())){
				sql.add("  AND swk.SWK_DEN_DATE >= ? ", param.getSlipDateFrom());
			}

			if(!Util.isNullOrEmpty(param.getSlipDateTo())){
				sql.add("  AND swk.SWK_DEN_DATE <= ? ", param.getSlipDateTo());
			}

			if(param.getAutoDivision() != -1){
				sql.add("  AND swk.SWK_AUTO_KBN = ? ", param.getAutoDivision());
			}

			if(!Util.isNullOrEmpty(param.getOrder())){
				sql.add(" ORDER BY ?", param.getOrder());
			}else{
				sql.add(" ORDER BY swk.KAI_CODE, swk.SWK_DEN_NO, swk.SWK_GYO_NO");
			}

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				SWK_DTL bean = mapping(rs);
				// 名称etc･･･を追加取得
				bean.setSWK_K_KAI_NAME(rs.getString("SWK_K_KAI_NAME"));
				bean.setSWK_K_KAI_NAME_S(rs.getString("SWK_K_KAI_NAME_S"));
				bean.setSWK_EMP_NAME(rs.getString("SWK_EMP_NAME"));
				bean.setSWK_EMP_NAME_S(rs.getString("SWK_EMP_NAME_S"));
				bean.setSWK_DEP_NAME(rs.getString("SWK_DEP_NAME"));
				bean.setSWK_DEP_NAME_S(rs.getString("SWK_DEP_NAME_S"));
				bean.setSWK_KMK_NAME(rs.getString("SWK_KMK_NAME"));
				bean.setSWK_KMK_NAME_S(rs.getString("SWK_KMK_NAME_S"));
				bean.setSWK_HKM_NAME(rs.getString("SWK_HKM_NAME"));
				bean.setSWK_HKM_NAME_S(rs.getString("SWK_HKM_NAME_S"));
				bean.setSWK_UKM_NAME(rs.getString("SWK_UKM_NAME"));
				bean.setSWK_UKM_NAME_S(rs.getString("SWK_UKM_NAME_S"));
				bean.setCUR_DEC_KETA(rs.getInt("CUR_DEC_KETA"));
				bean.setSWK_ZEI_NAME(rs.getString("SWK_ZEI_NAME"));
				bean.setSWK_ZEI_NAME_S(rs.getString("SWK_ZEI_NAME_S"));
				bean.setSWK_TRI_NAME(rs.getString("SWK_TRI_NAME"));
				bean.setSWK_TRI_NAME_S(rs.getString("SWK_TRI_NAME_S"));
				bean.setSWK_KNR_NAME_1(rs.getString("SWK_KNR_NAME_1"));
				bean.setSWK_KNR_NAME_S_1(rs.getString("SWK_KNR_NAME_S_1"));
				bean.setSWK_KNR_NAME_2(rs.getString("SWK_KNR_NAME_2"));
				bean.setSWK_KNR_NAME_S_2(rs.getString("SWK_KNR_NAME_S_2"));
				bean.setSWK_KNR_NAME_3(rs.getString("SWK_KNR_NAME_3"));
				bean.setSWK_KNR_NAME_S_3(rs.getString("SWK_KNR_NAME_S_3"));
				bean.setSWK_KNR_NAME_4(rs.getString("SWK_KNR_NAME_4"));
				bean.setSWK_KNR_NAME_S_4(rs.getString("SWK_KNR_NAME_S_4"));
				bean.setSWK_KNR_NAME_5(rs.getString("SWK_KNR_NAME_5"));
				bean.setSWK_KNR_NAME_S_5(rs.getString("SWK_KNR_NAME_S_5"));
				bean.setSWK_KNR_NAME_6(rs.getString("SWK_KNR_NAME_6"));
				bean.setSWK_KNR_NAME_S_6(rs.getString("SWK_KNR_NAME_S_6"));
				bean.setKEY_CUR_CODE(rs.getString("KEY_CUR_CODE"));
				bean.setKEY_CUR_DEC_KETA(rs.getInt("KEY_CUR_DEC_KETA"));
				bean.setFUNC_CUR_CODE(rs.getString("FUNC_CUR_CODE"));
				bean.setFUNC_CUR_DEC_KETA(rs.getInt("FUNC_CUR_DEC_KETA"));
				list.add(bean); 
			}

			DBUtil.close(rs);
			DBUtil.close(statement);
		} catch (Exception e) {
			throw new TRuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}
		return list;
	}

	/**
	 * @see jp.co.ais.trans2.model.slip.SWK_DTLDao#findEraseDtlByCondition(jp.co.ais.trans2.model.slip.SlipCondition)
	 */
	public List<SWK_DTL> findEraseDtlByCondition(SlipCondition param) {
		// 必要性が出たら実装
		return null;
	}

	/**
	 * @see jp.co.ais.trans2.model.slip.SWK_DTLDao#findEraseDtlByCondition2(jp.co.ais.trans2.model.slip.SlipCondition)
	 */
	public List<SWK_DTL> findEraseDtlByCondition2(SlipCondition param) {
		// 必要性が出たら実装
		return null;
	}

	/**
	 * @see jp.co.ais.trans2.model.slip.SWK_DTLDao#getDtl(java.lang.String, java.lang.String, int)
	 */
	public SWK_DTL getDtl(String companyCode, String slipNo, int rowNo) {
		Connection conn = null;
		SWK_DTL bean = null;
		try {
			conn = DBUtil.getConnection();

			SQLCreator sql = new SQLCreator();
			sql.add("SELECT * FROM ( " + getSWK_DTL() + " ) swk");
			sql.add(" WHERE swk.KAI_CODE = ?", companyCode);
			sql.add("   AND swk.SWK_DEN_NO = ?", slipNo);
			sql.add("   AND swk.SWK_GYO_NO = ?", rowNo);

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			if (rs.next()) {
				bean = mapping(rs);
			}

			DBUtil.close(rs);
			DBUtil.close(statement);
		} catch (Exception e) {
			throw new TRuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}
		return bean;
	}

	/**
	 * @see jp.co.ais.trans2.model.slip.SWK_DTLDao#getEraseItemCode(java.lang.String, java.lang.String)
	 */
	public String getEraseItemCode(String companyCode, String eraseNo) {
		// 必要性が出たら実装
		return null;
	}

	/**
	 * @see jp.co.ais.trans2.model.slip.SWK_DTLDao#insert(java.util.List)
	 */
	public void insert(List<SWK_DTL> list) {
		// 必要性が出たら実装

	}

	/**
	 * @see jp.co.ais.trans2.model.slip.SWK_DTLDao#updateEraseState(jp.co.ais.trans2.model.slip.SWK_DTL)
	 */
	public int updateEraseState(SWK_DTL entity) {
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			SQLCreator sql = new SQLCreator();
			sql.add("UPDATE " + SeaCASTConfig.getTableName("SWK_DAT") + " SET ");
			sql.add("	SWK_KESI_KBN = 1, ");
			sql.add("	SWK_SEI_DENNO = ?, ", entity.getSWK_SOUSAI_DEN_NO());
			sql.add("	PRGID = ?, ", entity.getPRG_ID());
			sql.add("	USRID = ?, ", entity.getUSR_ID());
			sql.add("	UPDDATE = SYSDATE");
			sql.add(" WHERE KAICODE = ?", entity.getKAI_CODE());
			sql.add("   AND SWK_DENNO = ?", entity.getSWK_DEN_NO());
			sql.add("   AND SWK_GYONO = ?", entity.getSWK_GYO_NO());
			sql.add("	AND SWK_KESI_KBN = 0 ");

			return DBUtil.execute(conn, sql);
		} catch (TException ex) {
			throw new TRuntimeException(ex);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * @see jp.co.ais.trans2.model.slip.SWK_DTLDao#updateEraseState2(jp.co.ais.trans2.model.slip.SlipCondition)
	 */
	public int updateEraseState2(SlipCondition param) {
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			SQLCreator sql = new SQLCreator();
			sql.add("UPDATE " + SeaCASTConfig.getTableName("SWK_DAT") + " SET ");
			sql.add("	SWK_KESI_KBN = 0, ");
			sql.add("	SWK_SEI_DENNO = NULL, ");
			sql.add("	PRGID = ?, ", param.getProgramCode());
			sql.add("	USRID = ?, ", param.getUserCode());
			sql.add("	UPDDATE = SYSDATE");
			sql.add(" WHERE KAICODE = ?", param.getCompanyCode());
			sql.add("   AND SWK_KESI_KBN = 1");

			if (!Util.isNullOrEmpty(param.getSlipNo())){
				sql.add("   AND SWK_SEI_DENNO = ?", param.getSlipNo());
			}

			return DBUtil.execute(conn, sql);
		} catch (TException ex) {
			throw new TRuntimeException(ex);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * @see jp.co.ais.trans2.model.slip.SWK_DTLDao#updateSlipState(jp.co.ais.trans2.model.slip.SWK_HDR)
	 */
	public int updateSlipState(SWK_HDR entity) {
		// 必要性が出たら実装
		return 0;
	}

	/**
	 * エンティティにセットして返す
	 * 
	 * @param rs
	 * @return bean
	 * @throws SQLException
	 */
	protected SWK_DTL mapping(ResultSet rs) throws SQLException {
		SWK_DTL bean = new SWK_DTL();
		bean.setKAI_CODE(rs.getString("KAI_CODE"));
		bean.setSWK_DEN_DATE(rs.getDate("SWK_DEN_DATE"));
		bean.setSWK_DEN_NO(rs.getString("SWK_DEN_NO"));
		bean.setSWK_GYO_NO(rs.getInt("SWK_GYO_NO"));
		bean.setSWK_NENDO(rs.getInt("SWK_NENDO"));
		bean.setSWK_TUKIDO(rs.getInt("SWK_TUKIDO"));
		bean.setSWK_SEI_NO(rs.getString("SWK_SEI_NO"));
		bean.setSWK_DC_KBN(rs.getInt("SWK_DC_KBN"));
		bean.setSWK_KMK_CODE(rs.getString("SWK_KMK_CODE"));
		bean.setSWK_HKM_CODE(rs.getString("SWK_HKM_CODE"));
		bean.setSWK_UKM_CODE(rs.getString("SWK_UKM_CODE"));
		bean.setSWK_DEP_CODE(rs.getString("SWK_DEP_CODE"));
		bean.setSWK_ZEI_KBN(rs.getInt("SWK_ZEI_KBN"));
		bean.setSWK_KIN(rs.getBigDecimal("SWK_KIN"));
		bean.setSWK_ZEI_KIN(rs.getBigDecimal("SWK_ZEI_KIN"));
		bean.setSWK_ZEI_CODE(rs.getString("SWK_ZEI_CODE"));
		bean.setSWK_ZEI_RATE(rs.getBigDecimal("SWK_ZEI_RATE"));
		bean.setSWK_GYO_TEK_CODE(rs.getString("SWK_GYO_TEK_CODE"));
		bean.setSWK_GYO_TEK(rs.getString("SWK_GYO_TEK"));
		bean.setSWK_TRI_CODE(rs.getString("SWK_TRI_CODE"));
		bean.setSWK_EMP_CODE(rs.getString("SWK_EMP_CODE"));
		bean.setSWK_KNR_CODE_1(rs.getString("SWK_KNR_CODE_1"));
		bean.setSWK_KNR_CODE_2(rs.getString("SWK_KNR_CODE_2"));
		bean.setSWK_KNR_CODE_3(rs.getString("SWK_KNR_CODE_3"));
		bean.setSWK_KNR_CODE_4(rs.getString("SWK_KNR_CODE_4"));
		bean.setSWK_KNR_CODE_5(rs.getString("SWK_KNR_CODE_5"));
		bean.setSWK_KNR_CODE_6(rs.getString("SWK_KNR_CODE_6"));
		bean.setSWK_HM_1(rs.getString("SWK_HM_1"));
		bean.setSWK_HM_2(rs.getString("SWK_HM_2"));
		bean.setSWK_HM_3(rs.getString("SWK_HM_3"));
		bean.setSWK_AUTO_KBN(rs.getInt("SWK_AUTO_KBN"));
		bean.setSWK_DATA_KBN(rs.getString("SWK_DATA_KBN"));
		bean.setSWK_UPD_KBN(rs.getInt("SWK_UPD_KBN"));
		bean.setSWK_KSN_KBN(rs.getInt("SWK_KSN_KBN"));
		bean.setSWK_AT_KMK_CODE(rs.getString("SWK_AT_KMK_CODE"));
		bean.setSWK_AT_HKM_CODE(rs.getString("SWK_AT_HKM_CODE"));
		bean.setSWK_AT_UKM_CODE(rs.getString("SWK_AT_UKM_CODE"));
		bean.setSWK_AT_DEP_CODE(rs.getString("SWK_AT_DEP_CODE"));
		bean.setINP_DATE(rs.getDate("INP_DATE"));
		bean.setUPD_DATE(rs.getDate("UPD_DATE"));
		bean.setPRG_ID(rs.getString("PRG_ID"));
		bean.setUSR_ID(rs.getString("USR_ID"));
		bean.setSWK_K_KAI_CODE(rs.getString("SWK_K_KAI_CODE"));
		bean.setSWK_CUR_CODE(rs.getString("SWK_CUR_CODE"));
		bean.setSWK_CUR_RATE(rs.getBigDecimal("SWK_CUR_RATE"));
		bean.setSWK_IN_KIN(rs.getBigDecimal("SWK_IN_KIN"));
		bean.setSWK_TUKE_KBN(rs.getInt("SWK_TUKE_KBN"));
		bean.setSWK_IN_ZEI_KIN(rs.getBigDecimal("SWK_IN_ZEI_KIN"));
		bean.setSWK_KESI_KBN(rs.getInt("SWK_KESI_KBN"));
		bean.setSWK_SOUSAI_DEN_NO(rs.getString("SWK_SOUSAI_DEN_NO"));
		bean.setHAS_DATE(rs.getDate("HAS_DATE"));
		bean.setSWK_BOOK_NO(rs.getInt("SWK_BOOK_NO"));
		bean.setSWK_ADJ_KBN(rs.getInt("SWK_ADJ_KBN"));
		return bean;
	}

	/**
	 * SWK_DATをSWK_DTLとして取得するSQL
	 * 
	 * @return sql
	 */
	protected String getSWK_DTL(){
		String sql =
			"SELECT " +
			"	KAICODE        KAI_CODE, " + "\n" +
			"	SWK_DENDATE    SWK_DEN_DATE, " + "\n" +
			"	SWK_DENNO      SWK_DEN_NO, " + "\n" +
			"	SWK_GYONO      SWK_GYO_NO, " + "\n" +
			"	SWK_NENDO      SWK_NENDO, " + "\n" +
			"	SWK_TUKIDO     SWK_TUKIDO, " + "\n" +
			"	SWK_SNO        SWK_SEI_NO, " + "\n" +
			"	SWK_DC_KBN     SWK_DC_KBN, " + "\n" +
			"	SWK_KMKCODE    SWK_KMK_CODE, " + "\n" +
			"	SWK_HKMCODE    SWK_HKM_CODE, " + "\n" +
			"	SWK_UKMCODE    SWK_UKM_CODE, " + "\n" +
			"	SWK_DEPCODE    SWK_DEP_CODE, " + "\n" +
			"	SWK_ZEI_KBN    SWK_ZEI_KBN, " + "\n" +
			"	SWK_KIN        SWK_KIN, " + "\n" +
			"	SWK_ZEIKIN     SWK_ZEI_KIN, " + "\n" +
			"	SWK_ZEICODE    SWK_ZEI_CODE, " + "\n" +
			"	SWK_ZEIRATE    SWK_ZEI_RATE, " + "\n" +
			"	SWK_GTKCODE    SWK_GYO_TEK_CODE, " + "\n" +
			"	SWK_GYOTEK     SWK_GYO_TEK, " + "\n" +
			"	SWK_TRI_CODE   SWK_TRI_CODE, " + "\n" +
			"	SWK_EMP_CODE   SWK_EMP_CODE, " + "\n" +
			"	SWK_KNR_CODE1  SWK_KNR_CODE_1, " + "\n" +
			"	SWK_KNR_CODE2  SWK_KNR_CODE_2, " + "\n" +
			"	SWK_KNR_CODE3  SWK_KNR_CODE_3, " + "\n" +
			"	SWK_KNR_CODE4  SWK_KNR_CODE_4, " + "\n" +
			"	SWK_KNR_CODE5  SWK_KNR_CODE_5, " + "\n" +
			"	SWK_KNR_CODE6  SWK_KNR_CODE_6, " + "\n" +
			"	SWK_HM1        SWK_HM_1, " + "\n" +
			"	SWK_HM2        SWK_HM_2, " + "\n" +
			"	SWK_HM3        SWK_HM_3, " + "\n" +
			"	SWK_AUTO_KBN   SWK_AUTO_KBN, " + "\n" +
			"	SWK_DATA_KBN   SWK_DATA_KBN, " + "\n" +
			"	SWK_UPD_KBN    SWK_UPD_KBN, " + "\n" +
			"	SWK_KSN_KBN    SWK_KSN_KBN, " + "\n" +
			"	SWK_AT_KMKCODE SWK_AT_KMK_CODE, " + "\n" +
			"	SWK_AT_HKMCODE SWK_AT_HKM_CODE, " + "\n" +
			"	SWK_AT_UKMCODE SWK_AT_UKM_CODE, " + "\n" +
			"	SWK_AT_DEPCODE SWK_AT_DEP_CODE, " + "\n" +
			"	INPDATE        INP_DATE, " + "\n" +
			"	UPDDATE        UPD_DATE, " + "\n" +
			"	PRGID          PRG_ID, " + "\n" +
			"	USRID          USR_ID, " + "\n" +
			"	SWK_KKAICODE   SWK_K_KAI_CODE, " + "\n" +
			"	SWK_CUR_CODE   SWK_CUR_CODE, " + "\n" +
			"	SWK_CUR_RATE   SWK_CUR_RATE, " + "\n" +
			"	SWK_IN_KIN     SWK_IN_KIN, " + "\n" +
			"	SWK_TUKE_KBN   SWK_TUKE_KBN, " + "\n" +
			"	SWK_IN_ZEIKIN  SWK_IN_ZEI_KIN, " + "\n" +
			"	SWK_KESI_KBN   SWK_KESI_KBN, " + "\n" +
			"	SWK_SEI_DENNO  SWK_SOUSAI_DEN_NO, " + "\n" +
			"	SWK_DENDATE    HAS_DATE, " + "\n" +
			"	1              SWK_BOOK_NO, " + "\n" +
			"	0              SWK_ADJ_KBN " + "\n" +
			"FROM " + SeaCASTConfig.getTableName("SWK_DAT");
		return sql;
	}

	/**
	 * String[]→Listへ変換
	 * 
	 * @param param
	 * @return list
	 */
	protected List toList(String[] param){
		List<String> list = new ArrayList<String>();
		for(int i=0; i<param.length; i++){
			list.add(param[i]);
		}
		return list;
	}

	/**
	 * int[]→Listへ変換
	 * 
	 * @param param
	 * @return list
	 */
	protected List toIntList(int[] param){
		List<String> list = new ArrayList<String>();
		for(int i=0; i<param.length; i++){
			list.add(Integer.toString(param[i]));
		}
		return list;
	}
}
