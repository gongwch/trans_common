package org.seasar.extension.dbcp.impl;

import java.sql.*;

import javax.sql.*;

import jp.co.ais.trans.common.config.*;

/**
 * TRANS2 Client Info �����p
 */
public class TRANS2DataSourceImpl extends XADataSourceImpl {

	@Override
	public XAConnection getXAConnection(String user, String password) throws SQLException {

		String sessionName = "TRANS2 Application";

		try {
			sessionName = ServerConfig.getProperty("trans.db.session.name");

		} catch (Throwable ex) {
			// �G���[�Ȃ�
		}

		addProperty("v$session.program", sessionName);

		XAConnection con = super.getXAConnection(user, password);

		// ���ʎq

		return con;
	}
}
