package jp.co.ais.trans2.common.config;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.util.*;

/**
 * C/S�A�g�p�ݒ�
 */
public class SeaCASTConfig {

	/** SeaCAST�̃X�L�[�}�� */
	public static String shemaName = null;

	/** SeaCAST�ւ�DB�����N�� */
	public static String dbLink = null;

	static {
		dbLink = ServerConfig.getProperty("trans.slip.dblink");
		shemaName = ServerConfig.getProperty("trans.slip.schema");
	}

	/**
	 * DB���A�X�L�[�}�t���e�[�u����
	 * 
	 * @param name C/S�Ńe�[�u��������
	 * @return �e�[�u����
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
