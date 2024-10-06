package jp.co.ais.trans2.model.slip;

import java.sql.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * �e���|�����[�e�[�u�����ݒ�N���X
 */
public class TempTableNameGetterSettingImpl extends TModel implements TempTableNameGetterSetting {

	/**
	 * ���[�N�e�[�u�����̎擾
	 * 
	 * @param bean
	 * @return T_BALANCE_WORK�e�[�u����
	 */
	public String getBalanceWorkTableName(TransferBase bean) {
		String defaultTableName = "T_BALANCE_WORK";

		if (DBUtil.isUseMySQL) {

			// MYSQL�̏ꍇ��DROP�͕K�v�Ȃ�
			String tempTableName = createTempTableName(defaultTableName);

			// �ꎞ�e�[�u���쐬
			createTable(defaultTableName, tempTableName);

			return tempTableName;
		}

		return defaultTableName;
	}

	/**
	 * �ꎞ�e�[�u�����̎擾
	 * 
	 * @param bean
	 * @param defaultTableName �f�t�H���g�ꎞ�e�[�u��ID
	 * @return �e�v���O�����Ŏg���ꎞ�e�[�u����
	 */
	public String getTemporaryWorkTableName(TransferBase bean, String defaultTableName) {

		if (DBUtil.isUseMySQL) {

			// MYSQL�̏ꍇ��DROP�͕K�v�Ȃ�
			String tempTableName = createTempTableName(defaultTableName);

			// �ꎞ�e�[�u���쐬
			createTable(defaultTableName, tempTableName);

			return tempTableName;
		}

		return defaultTableName;
	}

	/**
	 * �ꎞ�e�[�u���쐬
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
	 * @return �ꎞ�e�[�u����
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
