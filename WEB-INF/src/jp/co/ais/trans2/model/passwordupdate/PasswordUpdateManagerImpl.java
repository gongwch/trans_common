package jp.co.ais.trans2.model.passwordupdate;

import java.sql.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.model.*;

/**
 * MG0025 - パスワード変更
 * 
 * @author AIS
 */
public class PasswordUpdateManagerImpl extends TModel implements PasswordUpdateManager {

	/**
	 * 情報修正 (UPDATE)
	 * 
	 * @param bean 入力情報
	 * @throws TException
	 */
	public void modify(PasswordUpdate bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			// ユーザーマスタ登録
			sql.add("");
			sql.add("UPDATE");
			sql.add("    USR_MST");
			sql.add("SET");
			sql.add("    PASSWORD = ?", bean.getNewPassword());
			sql.adt("    ,UPD_DATE = ?", getNow());
			sql.add("    ,PRG_ID = ?", getProgramCode());
			sql.add("    ,USR_ID = ?", getUserCode());
			sql.add("WHERE");
			sql.add("    KAI_CODE = ?", getCompanyCode());
			sql.add("AND");
			sql.add("    USR_CODE = ?", getUser().getCode());
			DBUtil.execute(conn, sql);
			// ユーザー認証管理マスタ確認
			sql.clear();
			sql.add("SELECT COUNT(*) cnt");
			sql.add("    FROM USR_AUTH_MST");
			sql.add("    WHERE KAI_CODE =?", getCompanyCode());
			// ユーザー認証管理マスタに該当データありの場合:実行
			int count = DBUtil.selectOneInt(conn, sql.toSQL());
			if (count != 0) {
				sql.clear();
				sql.add("");
				sql.add("INSERT INTO PWD_HST_TBL (");
				sql.add("    KAI_CODE ");
				sql.add("    ,USR_CODE ");
				sql.add("    ,INP_DATE ");
				sql.add("    ,PASSWORD ");
				sql.add(") VALUES ( ");
				sql.add("     ?", getCompanyCode());
				sql.add("    ,?", getUser().getCode());
				sql.adt("    ,?", getNow());
				sql.add("    ,? )", bean.getNewPassword());
				DBUtil.execute(conn, sql);
			}
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

}