package jp.co.ais.trans2.model.slip;

import java.sql.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.db.*;


/**
 * C/S�A�g�p�폜�`�[�f�[�^Dao
 */
public class SeaCASTDEL_DTLDaoImpl implements DEL_DTLDao {

	public void delete(DEL_DTL dto) {
		// �K�v�����o�������

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
		// �K�v�����o�������

	}

}
