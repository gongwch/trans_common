package org.seasar.extension.dbcp.impl;

import java.sql.*;

import javax.sql.*;

import jp.co.ais.trans.common.config.*;

/**
 * SeaCAST2 Client Info �����p
 */
public class SeaCAST2DataSourceImpl extends XADataSourceImpl {

	@Override
	public XAConnection getXAConnection(String user, String password) throws SQLException {

		String sessionName = "SeaCAST2 Application";

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
