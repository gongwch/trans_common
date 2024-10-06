package jp.co.ais.trans.logic.util;

import java.sql.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * トラオペ用自動採番Dao実装
 */
public class OPAutoControlDaoImpl extends TModel implements OPAutoControlDao {

	/**
	 * トラオペ用：自動採番された番号を取得<br>
	 * 自動採番コントロールの更新も行う
	 * 
	 * @param companyCode
	 * @param prifix
	 * @param increase
	 * @return トラオペ用自動採番ID
	 * @throws TException
	 */
	public int getAutoId(String companyCode, String usrCode, String prgCode, String prifix, int increase)
		throws TException {

		try {
			// 排他コントロールマスタをロック
			VCreator s = new VCreator();
			s.add(" LOCK TABLE OP_AUTO_CTL IN SHARE ROW EXCLUSIVE MODE NOWAIT ");
			DBUtil.execute(s);

		} catch (TException e) {
			throw new TRuntimeException("W01133");// 只今混み合っております。しばらくお待ちください。
		}

		Connection conn = DBUtil.getConnection();
		int id = 0;

		try {
			// 最終ID取得
			VCreator s = new VCreator();
			s.add(" SELECT LAST_NO ");
			s.add(" FROM   OP_AUTO_CTL ");
			s.add(" WHERE KAI_CODE = ? ", companyCode);
			s.add("   AND PRIFIX   = ? ", prifix);

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, s);

			while (rs.next()) {
				id = rs.getInt("LAST_NO");
			}
			DBUtil.close(rs);
			DBUtil.close(statement);

			if (id == 0) {
				// 新規登録
				id = increase;

				s = new VCreator();
				s.add(" INSERT INTO OP_AUTO_CTL ( ");
				s.add("        KAI_CODE ");
				s.add("       ,PRIFIX ");
				s.add("       ,LAST_NO ");
				s.add("       ,INP_DATE ");
				s.add("       ,PRG_ID ");
				s.add("       ,USR_ID ");
				s.add(" ) VALUES ( ");
				s.add("        ? ", companyCode);
				s.add("       ,? ", prifix);
				s.add("       ,? ", id);
				s.adt("       ,? ", Util.getCurrentDate());
				s.add("       ,? ", prgCode);
				s.add("       ,? ", usrCode);
				s.add(" ) ");

				DBUtil.execute(conn, s);
			} else {
				// 既存データあり
				id = id + increase;

				s = new VCreator();
				s.add(" UPDATE OP_AUTO_CTL SET ");
				s.add("        LAST_NO  = ? ", id);
				s.adt("       ,UPD_DATE = ? ", Util.getCurrentDate());
				s.add("       ,PRG_ID   = ? ", prgCode);
				s.add("       ,USR_ID   = ? ", usrCode);
				s.add(" WHERE KAI_CODE = ?", companyCode);
				s.add("   AND PRIFIX   = ?", prifix);

				DBUtil.execute(conn, s);
			}
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
		return id;
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