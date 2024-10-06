package jp.co.ais.trans2.model.security;

import java.sql.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.model.*;

/**
 * �v���O�����r���̎���
 * @author AIS
 *
 */
public class ProgramExclusiveManagerImpl extends TModel implements ProgramExclusiveManager {

	/**
	 * �r������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @param programCode �v���O�����R�[�h
	 * @throws TException
	 */
	public void exclude(String companyCode, String userCode, String programCode) throws TException {
		this.exclude(companyCode,  userCode,  programCode, programCode);
	}
	
	/**
	 * �r������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @param programCode �v���O�����R�[�h
	 * @param processID ����ID
	 * @throws TException
	 */
	public void exclude(String companyCode, String userCode, String programCode, String processID) throws TException {

		Connection conn = DBUtil.getConnection();
		Statement st = null;

		try {

			// �o�b�`�R���g���[���}�X�^�����b�N
			try {
				DBUtil.execute(conn, "LOCK TABLE BAT_CTL IN SHARE ROW EXCLUSIVE MODE NOWAIT");
			} catch (TException e) {
//				 �������ݍ����Ă���܂��B���΂炭���҂����������B
				throw new TException("W01133");
			}

			String sql =
				" SELECT	USR_ID, PRG_ID " +
				" FROM		BAT_CTL " +
				" WHERE		KAI_CODE = " + SQLUtil.getParam(companyCode) +
				" AND		BAT_ID = " + SQLUtil.getParam(processID);

			st = conn.createStatement();
			ResultSet rs = DBUtil.select(st, sql);

			if (rs.next()) {
				if (!userCode.equals(rs.getString("USR_ID"))) {
					throw new TException("W01134");// �����[�U�[���g�p���ł��B
				}

				if (!programCode.equals(rs.getString("PRG_ID"))) {
					throw new TException("W01135");// ���̃v���O�����Ŏg�p���ł��B
				}

				
				// ���g�̔r���̏ꍇ�AINSERT�����ŏI��
				return;
			}

			sql =
				" INSERT INTO BAT_CTL ( " +
					" KAI_CODE, " +
					" BAT_ID, " +
					" BAT_STR_DATE, " +
					" INP_DATE, " +
					" PRG_ID, " +
					" USR_ID " +
				" ) VALUES ( " +
					SQLUtil.getParam(companyCode) + ", " +
					SQLUtil.getParam(processID) + ", " +
					SQLUtil.getYMDHMSParam(getNow()) + ", " +
					SQLUtil.getYMDHMSParam(getNow()) + ", " +
					SQLUtil.getParam(programCode) + ", " +
					SQLUtil.getParam(userCode) +
				" ) ";

			DBUtil.execute(conn, sql);

		} catch (TException e) {
			throw e;
			
		} catch (Exception e) {
			throw new TException(e);
			
		} finally {
			DBUtil.close(st);
			DBUtil.close(conn);
		}

	}

	/**
	 * �r����������
	 * 
	 * @throws TException
	 */
	public void cancelExclude() throws TException {
		Connection conn = DBUtil.getConnection();

		try {

			String sql =
				" DELETE " +
				" FROM		BAT_CTL " +
				" WHERE		KAI_CODE = " + SQLUtil.getParam(getCompanyCode()) + 
				" AND		USR_ID = " + SQLUtil.getParam(getUserCode()) +
				" AND		PRG_ID = " + SQLUtil.getParam(getProgramCode());

			DBUtil.execute(conn, sql);

		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}
	
	/**
	 * �r����������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @param processID ����ID
	 * @throws TException
	 */
	public void cancelExclude(String companyCode, @SuppressWarnings("unused") String userCode, String processID) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			String sql =
				" DELETE " +
				" FROM		BAT_CTL " +
				" WHERE		KAI_CODE = " + SQLUtil.getParam(companyCode) +
				" AND		BAT_ID = " + SQLUtil.getParam(processID);

			DBUtil.execute(conn, sql);

		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}
}
