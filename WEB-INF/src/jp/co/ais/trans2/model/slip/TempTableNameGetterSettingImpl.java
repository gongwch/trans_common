package jp.co.ais.trans2.model.slip;

import java.sql.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * テンポラリーテーブル名設定クラス
 */
public class TempTableNameGetterSettingImpl extends TModel implements TempTableNameGetterSetting {

	/**
	 * ワークテーブル名の取得
	 * 
	 * @param bean
	 * @return T_BALANCE_WORKテーブル名
	 */
	public String getBalanceWorkTableName(TransferBase bean) {
		String defaultTableName = "T_BALANCE_WORK";

		if (DBUtil.isUseMySQL) {

			// MYSQLの場合はDROPは必要なし
			String tempTableName = createTempTableName(defaultTableName);

			// 一時テーブル作成
			createTable(defaultTableName, tempTableName);

			return tempTableName;
		}

		return defaultTableName;
	}

	/**
	 * 一時テーブル名の取得
	 * 
	 * @param bean
	 * @param defaultTableName デフォルト一時テーブルID
	 * @return 各プログラムで使う一時テーブル名
	 */
	public String getTemporaryWorkTableName(TransferBase bean, String defaultTableName) {

		if (DBUtil.isUseMySQL) {

			// MYSQLの場合はDROPは必要なし
			String tempTableName = createTempTableName(defaultTableName);

			// 一時テーブル作成
			createTable(defaultTableName, tempTableName);

			return tempTableName;
		}

		return defaultTableName;
	}

	/**
	 * 一時テーブル作成
	 * 
	 * @param defaultTableName
	 * @param tempTableName
	 */
	protected void createTable(String defaultTableName, String tempTableName) {

		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			SQLCreator sql = new SQLCreator();
			sql.add("CREATE TABLE IF NOT EXISTS ").add(tempTableName).add(" LIKE ").add(defaultTableName);
			DBUtil.execute(conn, sql);

		} catch (Exception ex) {
			throw new TRuntimeException(ex);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * @param defaultTableName
	 * @return 一時テーブル名
	 */
	protected String createTempTableName(String defaultTableName) {

		String kaiCode = StringUtil.replaceToUnderscore(getCompanyCode());
		String userCode = StringUtil.replaceToUnderscore(getUserCode());

		StringBuilder sb = new StringBuilder();
		sb.append(defaultTableName);
		sb.append("_");
		sb.append(kaiCode);
		sb.append("_");
		sb.append(getProgramCode());
		sb.append("_");
		sb.append(userCode);
		return sb.toString();
	}
}
