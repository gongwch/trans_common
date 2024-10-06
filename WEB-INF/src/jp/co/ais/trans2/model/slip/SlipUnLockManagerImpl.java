package jp.co.ais.trans2.model.slip;

import java.sql.*;
import java.util.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * 伝票排他解除マスタインターフェース実装
 * 
 * @author AIS
 */
public class SlipUnLockManagerImpl extends TModel implements SlipUnLockManager {

	/**
	 * 伝票排他解除マスタ検索処理
	 */
	public List<SlipUnLock> get() throws TException {

		Connection conn = DBUtil.getConnection();
		List<SlipUnLock> list = new ArrayList<SlipUnLock>();

		try {

			// GL仕訳ジャーナルヘッダ
			SQLCreator sql = new SQLCreator();
			// GL仕訳ジャーナルヘッダ
			sql.add(" SELECT ");
			sql.add("     hdr.SWK_SYS_KBN ");
			sql.add("    ,sys.SYS_KBN_NAME ");
			sql.add("    ,hdr.SWK_DEN_NO ");
			sql.add("    ,hdr.SWK_DEN_SYU ");
			sql.add("    ,syu.DEN_SYU_NAME ");
			sql.add("    ,hdr.SWK_DEN_DATE ");
			sql.add("    ,hdr.SWK_TEK ");
			sql.add("    ,hdr.USR_ID ");
			sql.add("    ,usm.USR_NAME ");
			sql.add("    ,hdr.UPD_DATE ");
			sql.add("    ,0 AS KBN ");
			sql.add(" FROM GL_SWK_HDR hdr ");
			sql.add("     INNER JOIN USR_MST usm ");
			sql.add("     ON  hdr.KAI_CODE = usm.KAI_CODE ");
			sql.add("     AND hdr.USR_ID   = usm.USR_CODE ");
			sql.add("     INNER JOIN SYS_MST sys ");
			sql.add("     ON  hdr.KAI_CODE = sys.KAI_CODE ");
			sql.add("     AND hdr.SWK_SYS_KBN = sys.SYS_KBN ");
			sql.add("     INNER JOIN DEN_SYU_MST syu ");
			sql.add("     ON  hdr.KAI_CODE    = syu.KAI_CODE ");
			sql.add("     AND hdr.SWK_DEN_SYU = syu.DEN_SYU_CODE ");
			sql.add(" WHERE hdr.KAI_CODE = ?", getCompanyCode());
			sql.add(" AND   hdr.SWK_SHR_KBN = 1");
			// パターンGL仕訳ジャーナルヘッダ
			sql.add(" UNION ALL ");
			sql.add(" SELECT ");
			sql.add("     hdr.SWK_SYS_KBN ");
			sql.add("    ,sys.SYS_KBN_NAME ");
			sql.add("    ,hdr.SWK_DEN_NO ");
			sql.add("    ,hdr.SWK_DEN_SYU ");
			sql.add("    ,syu.DEN_SYU_NAME ");
			sql.add("    ,hdr.SWK_DEN_DATE ");
			sql.add("    ,hdr.SWK_TEK ");
			sql.add("    ,hdr.USR_ID ");
			sql.add("    ,usm.USR_NAME ");
			sql.add("    ,hdr.UPD_DATE ");
			sql.add("    ,1 AS KBN ");
			sql.add(" FROM GL_SWK_PTN_HDR hdr ");
			sql.add("     INNER JOIN USR_MST usm ");
			sql.add("     ON  hdr.KAI_CODE = usm.KAI_CODE ");
			sql.add("     AND hdr.USR_ID   = usm.USR_CODE ");
			sql.add("     INNER JOIN SYS_MST sys ");
			sql.add("     ON  hdr.KAI_CODE = sys.KAI_CODE ");
			sql.add("     AND hdr.SWK_SYS_KBN = sys.SYS_KBN ");
			sql.add("     INNER JOIN DEN_SYU_MST syu ");
			sql.add("     ON  hdr.KAI_CODE    = syu.KAI_CODE ");
			sql.add("     AND hdr.SWK_DEN_SYU = syu.DEN_SYU_CODE ");
			sql.add(" WHERE hdr.KAI_CODE = ? ", getCompanyCode());
			sql.add(" AND   hdr.SWK_SHR_KBN = 1 ");
			// AP仕訳ジャーナルヘッダ
			sql.add(" UNION ALL ");
			sql.add(" SELECT ");
			sql.add("     hdr.SWK_SYS_KBN ");
			sql.add("    ,sys.SYS_KBN_NAME ");
			sql.add("    ,hdr.SWK_DEN_NO ");
			sql.add("    ,hdr.SWK_DEN_SYU ");
			sql.add("    ,syu.DEN_SYU_NAME ");
			sql.add("    ,hdr.SWK_DEN_DATE ");
			sql.add("    ,hdr.SWK_TEK ");
			sql.add("    ,hdr.USR_ID ");
			sql.add("    ,usm.USR_NAME ");
			sql.add("    ,hdr.UPD_DATE ");
			sql.add("    ,2 AS KBN ");
			sql.add(" FROM AP_SWK_HDR hdr ");
			sql.add("     INNER JOIN USR_MST usm ");
			sql.add("     ON  hdr.KAI_CODE = usm.KAI_CODE ");
			sql.add("     AND hdr.USR_ID   = usm.USR_CODE ");
			sql.add("     INNER JOIN SYS_MST sys ");
			sql.add("     ON  hdr.KAI_CODE = sys.KAI_CODE ");
			sql.add("     AND hdr.SWK_SYS_KBN = sys.SYS_KBN ");
			sql.add("     INNER JOIN DEN_SYU_MST syu ");
			sql.add("     ON  hdr.KAI_CODE    = syu.KAI_CODE ");
			sql.add("     AND hdr.SWK_DEN_SYU = syu.DEN_SYU_CODE ");
			sql.add(" WHERE hdr.KAI_CODE = ? ", getCompanyCode());
			sql.add(" AND   hdr.SWK_SHR_KBN = 1 ");
			// パターンAP仕訳ジャーナルヘッダ
			sql.add(" UNION ALL ");
			sql.add(" SELECT ");
			sql.add("     hdr.SWK_SYS_KBN ");
			sql.add("    ,sys.SYS_KBN_NAME ");
			sql.add("    ,hdr.SWK_DEN_NO ");
			sql.add("    ,hdr.SWK_DEN_SYU ");
			sql.add("    ,syu.DEN_SYU_NAME ");
			sql.add("    ,hdr.SWK_DEN_DATE ");
			sql.add("    ,hdr.SWK_TEK ");
			sql.add("    ,hdr.USR_ID ");
			sql.add("    ,usm.USR_NAME ");
			sql.add("    ,hdr.UPD_DATE ");
			sql.add("    ,3 AS KBN ");
			sql.add(" FROM AP_SWK_PTN_HDR hdr ");
			sql.add("     INNER JOIN USR_MST usm ");
			sql.add("     ON  hdr.KAI_CODE = usm.KAI_CODE ");
			sql.add("     AND hdr.USR_ID   = usm.USR_CODE ");
			sql.add("     INNER JOIN SYS_MST sys ");
			sql.add("     ON  hdr.KAI_CODE = sys.KAI_CODE ");
			sql.add("     AND hdr.SWK_SYS_KBN = sys.SYS_KBN ");
			sql.add("     INNER JOIN DEN_SYU_MST syu ");
			sql.add("     ON  hdr.KAI_CODE    = syu.KAI_CODE ");
			sql.add("     AND hdr.SWK_DEN_SYU = syu.DEN_SYU_CODE ");
			sql.add(" WHERE hdr.KAI_CODE = ? ", getCompanyCode());
			sql.add(" AND   hdr.SWK_SHR_KBN = 1 ");
			// AR仕訳ジャーナルヘッダ
			sql.add(" UNION ALL ");
			sql.add(" SELECT ");
			sql.add("     hdr.SWK_SYS_KBN ");
			sql.add("    ,sys.SYS_KBN_NAME ");
			sql.add("    ,hdr.SWK_DEN_NO ");
			sql.add("    ,hdr.SWK_DEN_SYU ");
			sql.add("    ,syu.DEN_SYU_NAME ");
			sql.add("    ,hdr.SWK_DEN_DATE ");
			sql.add("    ,hdr.SWK_TEK ");
			sql.add("    ,hdr.USR_ID ");
			sql.add("    ,usm.USR_NAME ");
			sql.add("    ,hdr.UPD_DATE ");
			sql.add("    ,4 AS KBN ");
			sql.add(" FROM AR_SWK_HDR hdr ");
			sql.add("     INNER JOIN USR_MST usm ");
			sql.add("     ON  hdr.KAI_CODE = usm.KAI_CODE ");
			sql.add("     AND hdr.USR_ID   = usm.USR_CODE ");
			sql.add("     INNER JOIN SYS_MST sys ");
			sql.add("     ON  hdr.KAI_CODE = sys.KAI_CODE ");
			sql.add("     AND hdr.SWK_SYS_KBN = sys.SYS_KBN ");
			sql.add("     INNER JOIN DEN_SYU_MST syu ");
			sql.add("     ON  hdr.KAI_CODE    = syu.KAI_CODE ");
			sql.add("     AND hdr.SWK_DEN_SYU = syu.DEN_SYU_CODE ");
			sql.add(" WHERE hdr.KAI_CODE = ? ", getCompanyCode());
			sql.add(" AND   hdr.SWK_SHR_KBN = 1 ");
			// パターンAR仕訳ジャーナルヘッダ
			sql.add(" UNION ALL ");
			sql.add(" SELECT ");
			sql.add("     hdr.SWK_SYS_KBN ");
			sql.add("    ,sys.SYS_KBN_NAME ");
			sql.add("    ,hdr.SWK_DEN_NO ");
			sql.add("    ,hdr.SWK_DEN_SYU ");
			sql.add("    ,syu.DEN_SYU_NAME ");
			sql.add("    ,hdr.SWK_DEN_DATE ");
			sql.add("    ,hdr.SWK_TEK ");
			sql.add("    ,hdr.USR_ID ");
			sql.add("    ,usm.USR_NAME ");
			sql.add("    ,hdr.UPD_DATE ");
			sql.add("    ,5 AS KBN ");
			sql.add(" FROM AR_SWK_PTN_HDR hdr ");
			sql.add("     INNER JOIN USR_MST usm ");
			sql.add("     ON  hdr.KAI_CODE = usm.KAI_CODE ");
			sql.add("     AND hdr.USR_ID   = usm.USR_CODE ");
			sql.add("     INNER JOIN SYS_MST sys ");
			sql.add("     ON  hdr.KAI_CODE = sys.KAI_CODE ");
			sql.add("     AND hdr.SWK_SYS_KBN = sys.SYS_KBN ");
			sql.add("     INNER JOIN DEN_SYU_MST syu ");
			sql.add("     ON  hdr.KAI_CODE    = syu.KAI_CODE ");
			sql.add("     AND hdr.SWK_DEN_SYU = syu.DEN_SYU_CODE ");
			sql.add(" WHERE hdr.KAI_CODE = ? ", getCompanyCode());
			sql.add(" AND   hdr.SWK_SHR_KBN = 1 ");
			
			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}
			DBUtil.close(rs);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
		return list;
	}

	/**
	 * 伝票排他解除マスタ更新処理
	 */
	public void update(List<SlipUnLock> slipunlock) throws TException {

		
		for (SlipUnLock bean : slipunlock) {
			String table = ""; 

			switch (bean.getFlag()) {
				case 0:
					table = "GL_SWK_HDR";
					break;
				case 1:
					table = "GL_SWK_PTN_HDR";
					break;
				case 2:
					table = "AP_SWK_HDR";
					break;
				case 3:
					table = "AP_SWK_PTN_HDR";
					break;
				case 4:
					table = "AR_SWK_HDR";
					break;
				case 5:
					table = "AR_SWK_PTN_HDR";
					break;
				default:
					break;// Nullのとき
			}
			if (!Util.isNullOrEmpty(table)) {
				updateHDR(bean, table);
			}
		}
	}

	/**
	 * @param slipunlock
	 * @param table
	 * @throws TException 伝票排他解除マスタ更新処理
	 */
	public void updateHDR(SlipUnLock slipunlock, String table) throws TException {

		Connection conn = DBUtil.getConnection();
		try {

			SQLCreator sql = new SQLCreator();

			sql.add(" UPDATE " + table + " SET ");
			sql.add("     SWK_SHR_KBN = 0 ");
			sql.add("    ,PRG_ID = ? ", getProgramCode());
			sql.add("    ,USR_ID = ? ", getUserCode());
			sql.adt("    ,UPD_DATE = ? ", slipunlock.getUPD_DATE());
			sql.add(" WHERE KAI_CODE    = ? ", getCompanyCode());
			sql.add(" AND   SWK_DEN_NO  = ? ", slipunlock.getSWK_DEN_NO());
			sql.add(" AND   SWK_SYS_KBN = ? ", slipunlock.getSWK_SYS_KBN());
			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected SlipUnLock mapping(ResultSet rs) throws Exception {

		SlipUnLock bean = new SlipUnLock();

		bean.setSWK_SYS_KBN(rs.getString("SWK_SYS_KBN"));
		bean.setSYS_NAME(rs.getString("SYS_KBN_NAME"));
		bean.setSWK_DEN_NO(rs.getString("SWK_DEN_NO"));
		bean.setSWK_DEN_SYU(rs.getString("SWK_DEN_SYU"));
		bean.setDEN_SYU_NAME(rs.getString("DEN_SYU_NAME"));
		bean.setSWK_DEN_DATE(rs.getTimestamp("SWK_DEN_DATE"));
		bean.setSWK_TEK(rs.getString("SWK_TEK"));
		bean.setUSR_ID(rs.getString("USR_ID"));
		bean.setUSR_NAME(rs.getString("USR_NAME"));
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE"));
		bean.setFlag(rs.getInt("KBN"));

		return bean;

	}

}
