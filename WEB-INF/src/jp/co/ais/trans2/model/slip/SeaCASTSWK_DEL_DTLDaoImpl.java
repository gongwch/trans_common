package jp.co.ais.trans2.model.slip;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;

/**
 * C/S連携用仕訳DEL_DTLDao
 */
public class SeaCASTSWK_DEL_DTLDaoImpl implements SWK_DEL_DTLDao {

	/**
	 * 削除処理
	 * 
	 * @param param 条件
	 */
	public void deleteByCondition(SlipCondition param) {
		// 必要性が出たら実装
	}

	/**
	 * @see jp.co.ais.trans2.model.slip.SWK_DTLDao#findByCondition(jp.co.ais.trans2.model.slip.SlipCondition)
	 */
	public List<SWK_DTL> findByCondition(SlipCondition param) {
		// 必要性が出たら実装
		return null;
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
		return null;
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
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			String tableName = SeaCASTConfig.getTableName("SWK_DAT_DEL");
			String tableNameFrom = SeaCASTConfig.getTableName("SWK_DAT");

			for (SWK_DTL detail : list) {
				if (detail.isFunctionalCurrency()) {
					continue; // IFRS仕訳を除く
				}
				SQLCreator sql = new SQLCreator();
				sql.add("INSERT INTO ").add(tableName);
				sql.add("SELECT");
				sql.add("	dtl.KAICODE,");
				sql.add("	dtl.SWK_DENDATE,");
				sql.add("	dtl.SWK_DENNO,");
				sql.add("	dtl.SWK_GYONO,");
				sql.add("	dtl.SWK_NENDO,");
				sql.add("	dtl.SWK_TUKIDO,");
				sql.add("	dtl.SWK_SNO,");
				sql.add("	dtl.SWK_DC_KBN,");
				sql.add("	dtl.SWK_KMKCODE,");
				sql.add("	dtl.SWK_HKMCODE,");
				sql.add("	dtl.SWK_UKMCODE,");
				sql.add("	dtl.SWK_DEPCODE,");
				sql.add("	dtl.SWK_ZEI_KBN,");
				sql.add("	dtl.SWK_KIN,");
				sql.add("	dtl.SWK_ZEIKIN,");
				sql.add("	dtl.SWK_ZEICODE,");
				sql.add("	dtl.SWK_ZEIRATE,");
				sql.add("	dtl.SWK_GTKCODE,");
				sql.add("	dtl.SWK_GYOTEK,");
				sql.add("	dtl.SWK_TRI_CODE,");
				sql.add("	dtl.SWK_EMP_CODE,");
				sql.add("	dtl.SWK_KNR_CODE1,");
				sql.add("	dtl.SWK_KNR_CODE2,");
				sql.add("	dtl.SWK_KNR_CODE3,");
				sql.add("	dtl.SWK_KNR_CODE4,");
				sql.add("	dtl.SWK_KNR_CODE5,");
				sql.add("	dtl.SWK_KNR_CODE6,");
				sql.add("	dtl.SWK_HM1,");
				sql.add("	dtl.SWK_HM2,");
				sql.add("	dtl.SWK_HM3,");
				sql.add("	dtl.SWK_AUTO_KBN,");
				sql.add("	dtl.SWK_DATA_KBN,");
				sql.add("	dtl.SWK_BEFORE_DENNO,");
				sql.add("	dtl.SWK_UPD_KBN,");
				sql.add("	dtl.SWK_KSN_KBN,");
				sql.add("	dtl.SWK_AT_KMKCODE,");
				sql.add("	dtl.SWK_AT_HKMCODE,");
				sql.add("	dtl.SWK_AT_UKMCODE,");
				sql.add("	dtl.SWK_AT_DEPCODE,");
				sql.add("	dtl.SWK_SIHA_DATE,");
				sql.add("	dtl.SWK_SIHA_HOUHOU,");
				sql.add("	dtl.INPDATE,");
				sql.add("	dtl.UPDDATE,");
				sql.add("	dtl.PRGID,");
				sql.add("	dtl.USRID,");
				sql.add("	dtl.SWK_TZEI_KIN,");
				sql.add("	dtl.SWK_KKAICODE,");
				sql.add("	dtl.SWK_CUR_CODE,");
				sql.add("	dtl.SWK_CUR_RATE,");
				sql.add("	dtl.SWK_IN_KIN,");
				sql.add("	dtl.SWK_TUKE_KBN,");
				sql.add("	dtl.SWK_IN_ZEIKIN,");
				sql.add("	dtl.SWK_KESI_KBN,");
				sql.add("	NVL(gl.SWK_UPD_CNT, NVL(ap.SWK_UPD_CNT, ar.SWK_UPD_CNT)) SWK_UPD_CNT");
				sql.add(" FROM " + tableNameFrom + " dtl");
				sql.add("  LEFT OUTER JOIN " + SeaCASTConfig.getTableName("GL_SWK_JNL") + " gl");
				sql.add("    ON dtl.KAICODE     = gl.KAICODE");
				sql.add("   AND dtl.SWK_DENDATE = gl.SWK_DENDATE");
				sql.add("   AND dtl.SWK_DENNO   = gl.SWK_DENNO");
				sql.add("  LEFT OUTER JOIN " + SeaCASTConfig.getTableName("AP_SWK_JNL") + " ap");
				sql.add("    ON dtl.KAICODE     = ap.KAICODE");
				sql.add("   AND dtl.SWK_DENDATE = ap.SWK_DENDATE");
				sql.add("   AND dtl.SWK_DENNO   = ap.SWK_DENNO");
				sql.add("  LEFT OUTER JOIN " + SeaCASTConfig.getTableName("AR_SWK_JNL") + " ar");
				sql.add("    ON dtl.KAICODE     = ar.KAICODE");
				sql.add("   AND dtl.SWK_DENDATE = ar.SWK_DENDATE");
				sql.add("   AND dtl.SWK_DENNO   = ar.SWK_DENNO");
				sql.add(" WHERE dtl.KAICODE     = ?", detail.getKAI_CODE());
				sql.add("   AND dtl.SWK_DENDATE = ?", detail.getSWK_DEN_DATE());
				sql.add("   AND dtl.SWK_DENNO   = ?", detail.getSWK_DEN_NO());
				sql.add("   AND dtl.SWK_GYONO   = ?", detail.getSWK_GYO_NO());

				DBUtil.execute(conn, sql);
			}
		} catch (TException ex) {
			throw new TRuntimeException(ex);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * @see jp.co.ais.trans2.model.slip.SWK_DTLDao#updateEraseState(jp.co.ais.trans2.model.slip.SWK_DTL)
	 */
	public int updateEraseState(SWK_DTL entity) {
		// 必要性が出たら実装
		return 0;
	}

	/**
	 * @see jp.co.ais.trans2.model.slip.SWK_DTLDao#updateEraseState2(jp.co.ais.trans2.model.slip.SlipCondition)
	 */
	public int updateEraseState2(SlipCondition param) {
		// 必要性が出たら実装
		return 0;
	}

	/**
	 * @see jp.co.ais.trans2.model.slip.SWK_DTLDao#updateSlipState(jp.co.ais.trans2.model.slip.SWK_HDR)
	 */
	public int updateSlipState(SWK_HDR entity) {
		// 必要性が出たら実装
		return 0;
	}
}
