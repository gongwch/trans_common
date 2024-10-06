package jp.co.ais.trans2.common.config;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.util.*;

/**
 * C/S連携用設定
 */
public class SeaCASTConfig {

	/** SeaCASTのスキーマ名 */
	public static String shemaName = null;

	/** SeaCASTへのDBリンク名 */
	public static String dbLink = null;

	static {
		dbLink = ServerConfig.getProperty("trans.slip.dblink");
		shemaName = ServerConfig.getProperty("trans.slip.schema");
	}

	/**
	 * DB名、スキーマ付きテーブル名
	 * 
	 * @param name C/S版テーブル物理名
	 * @return テーブル名
	 */
	public static String getTableName(String name) {
		String tableName = name;

		if (!Util.isNullOrEmpty(shemaName)) {
			tableName = shemaName + "." + tableName;
		}

		if (!Util.isNullOrEmpty(dbLink)) {
			tableName = tableName + "@" + dbLink;
		}

		return tableName;
	}
}
