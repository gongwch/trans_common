package jp.co.ais.trans2.model.slip;

import java.sql.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.db.*;


/**
 * C/S連携用削除伝票データDao
 */
public class SeaCASTDEL_DTLDaoImpl implements DEL_DTLDao {

	public void delete(DEL_DTL dto) {
		// 必要性が出たら実装

	}

	public void insert(DEL_DTL dto) {
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			SQLCreator sql = new SQLCreator();
			sql.add("INSERT INTO " + SeaCASTConfig.getTableName("DEL_DAT"));
			sql.add(" ( ");
			sql.add("KAICODE, ");
			sql.add("DEL_DENDATE, ");
			sql.add("DEL_DENNO, ");
			sql.add("INPDATE, ");
			sql.add("UPDDATE, ");
			sql.add("PRGID, ");
			sql.add("USRID ");
			sql.add(" ) VALUES ( ");
			sql.add(" ?, ", dto.getKAI_CODE());
			sql.add(" ?, ", dto.getDEL_DEN_DATE());
			sql.add(" ?, ", dto.getDEL_DEN_NO());
			sql.add(" ?, ", dto.getINP_DATE());
			sql.add(" ?, ", dto.getUPD_DATE());
			sql.add(" ?, ", dto.getPRG_ID());
			sql.add(" ? ", dto.getUSR_ID());
			sql.add(" )");

			DBUtil.execute(conn, sql);
		} catch (TException ex) {
			throw new TRuntimeException(ex);
		} finally {
			DBUtil.close(conn);
		}
	}

	public void update(DEL_DTL dto) {
		// 必要性が出たら実装

	}

}
