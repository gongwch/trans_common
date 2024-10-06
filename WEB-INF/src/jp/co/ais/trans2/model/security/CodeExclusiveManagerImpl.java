package jp.co.ais.trans2.model.security;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.model.*;

/**
 * �r������e�[�u���̃}�l�[�W���̎����N���X�ł�
 * 
 * @author AIS
 */
public class CodeExclusiveManagerImpl extends TModel implements CodeExclusiveManager {

	/**
	 * �r������
	 * 
	 * @param processType
	 * @param code
	 * @param rowNo
	 * @throws TException
	 */
	public void exclude(String processType, String code, String rowNo) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			// �r���R���g���[���}�X�^�����b�N
			try {
				DBUtil.execute(conn, "LOCK TABLE HAITA_CTL IN SHARE ROW EXCLUSIVE MODE NOWAIT");
			} catch (TException e) {
				throw new TException("W01133");// �������ݍ����Ă���܂��B���΂炭���҂����������B
			}

			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT USR_ID, PRG_ID ");
			sql.add("   FROM HAITA_CTL ");
			sql.add("  WHERE KAI_CODE  = ? ", getCompanyCode());
			sql.add("    AND SHORI_KBN = ? ", processType);
			sql.add("    AND TRI_CODE  = ? ", code);
			sql.add("    AND GYO_NO    = ? ", rowNo);

			boolean hasError = false;
			Statement st = conn.createStatement();
			ResultSet rs = DBUtil.select(st, sql);

			if (rs.next()) {
				if (!getUserCode().equals(rs.getString("USR_ID"))) {
					throw new TException("W01134");// �����[�U�[���g�p���ł��B
				}

				if (!getProgramCode().equals(rs.getString("PRG_ID"))) {
					throw new TException("W01135");// ���̃v���O�����Ŏg�p���ł��B
				}

				// ���g�̔r���̏ꍇ�AINSERT�����ŏI��
				hasError = true;
			}

			DBUtil.close(rs);
			DBUtil.close(st);

			if (hasError) {
				return;
			}

			sql = new SQLCreator();
			sql.add(" INSERT INTO HAITA_CTL ( ");
			sql.add("  KAI_CODE, ");
			sql.add("  SHORI_KBN, ");
			sql.add("  TRI_CODE,  ");
			sql.add("  GYO_NO,    ");
			sql.add("  INP_DATE,  ");
			sql.add("  UPD_DATE,  ");
			sql.add("  PRG_ID,    ");
			sql.add("  USR_ID     ");
			sql.add(" ) VALUES ( ");
			sql.add(" ?, ", getCompanyCode());
			sql.add(" ?, ", processType);
			sql.add(" ?, ", code);
			sql.add(" ?, ", rowNo);
			sql.adt(" ?, ", getNow());
			sql.add(" NULL, ");
			sql.add(" ?, ", getProgramCode());
			sql.add(" ?  ", getUserCode());
			sql.add(" ) ");

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
	 * @throws TException
	 */
	public void cancelExclude() throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();
			sql.add(" DELETE FROM HAITA_CTL ");
			sql.add(" WHERE KAI_CODE = ? ", getCompanyCode());
			sql.add("   AND PRG_ID   = ? ", getProgramCode());
			sql.add("   AND USR_ID   = ? ", getUserCode());

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
	 * @param processType
	 * @param code
	 * @param rowNo
	 * @throws TException
	 */
	public void cancelExclude(String processType, String code, String rowNo) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();
			sql.add(" DELETE FROM HAITA_CTL ");
			sql.add(" WHERE KAI_CODE  = ? ", getCompanyCode());
			sql.add("   AND SHORI_KBN = ? ", processType);
			sql.add("   AND TRI_CODE  = ? ", code);
			sql.add("   AND GYO_NO    = ? ", rowNo);
			sql.add("   AND PRG_ID    = ? ", getProgramCode());
			sql.add("   AND USR_ID    = ? ", getUserCode());

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
	 * �r���|����<br>
	 * �����ꊇ�r���ucodeList��rowNoList�̌��͈�v����K�v�v
	 * 
	 * @param processType
	 * @param codeList
	 * @param rowNoList
	 * @throws TException
	 */
	public void exclude(String processType, List<String> codeList, List<Integer> rowNoList) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			// �r���R���g���[���}�X�^�����b�N
			try {
				DBUtil.execute(conn, "LOCK TABLE HAITA_CTL IN SHARE ROW EXCLUSIVE MODE NOWAIT");
			} catch (TException e) {
				throw new TException("W01133");// �������ݍ����Ă���܂��B���΂炭���҂����������B
			}

			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT USR_ID, PRG_ID ");
			sql.add("   FROM HAITA_CTL ");
			sql.add("  WHERE KAI_CODE  = ? ", getCompanyCode());
			sql.add("    AND SHORI_KBN = ? ", processType);
			sql.add("    AND ( 1 = 2 ");

			for (int i = 0; i < codeList.size(); i++) {
				sql.add("    OR (TRI_CODE  = ?   ", codeList.get(i));
				sql.add("    AND GYO_NO    = ? ) ", rowNoList.get(i));
			}
			sql.add("        )");

			boolean hasError = false;

			Statement st = conn.createStatement();
			ResultSet rs = DBUtil.select(st, sql);

			if (rs.next()) {
				if (!getUserCode().equals(rs.getString("USR_ID"))) {
					throw new TException("W01134");// �����[�U�[���g�p���ł��B
				}

				if (!getProgramCode().equals(rs.getString("PRG_ID"))) {
					throw new TException("W01135");// ���̃v���O�����Ŏg�p���ł��B
				}

				// ���g�̔r���̏ꍇ�AINSERT�����ŏI��
				hasError = true;
			}

			DBUtil.close(rs);
			DBUtil.close(st);

			if (hasError) {
				return;
			}

			// �f�[�^�o�^����
			for (int j = 0; j < codeList.size(); j++) {
				sql = new SQLCreator();
				sql.add(" INSERT INTO HAITA_CTL (");
				sql.add("  KAI_CODE,  ");
				sql.add("  SHORI_KBN, ");
				sql.add("  TRI_CODE,  ");
				sql.add("  GYO_NO,    ");
				sql.add("  INP_DATE,  ");
				sql.add("  UPD_DATE,  ");
				sql.add("  PRG_ID,    ");
				sql.add("  USR_ID     ");
				sql.add(" ) VALUES ( ");
				sql.add(" ?, ", getCompanyCode());
				sql.add(" ?, ", processType);
				sql.add(" ?, ", codeList.get(j));
				sql.add(" ?, ", rowNoList.get(j));
				sql.adt(" ?, ", getNow());
				sql.add(" NULL, ");
				sql.add(" ?, ", getProgramCode());
				sql.add(" ?  ", getUserCode());
				sql.add(" ) ");
				DBUtil.execute(conn, sql);
			}

		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * �r������<br>
	 * �����ꊇ�r���ucodeList��rowNoList�̌��͈�v����K�v�v
	 * 
	 * @param processType
	 * @param codeList
	 * @param rowNoList
	 * @throws TException
	 */
	public void cancelExclude(String processType, List<String> codeList, List<Integer> rowNoList) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator sql = new SQLCreator();
			sql.add(" DELETE FROM HAITA_CTL ");
			sql.add("  WHERE KAI_CODE  = ? ", getCompanyCode());
			sql.add("    AND SHORI_KBN = ? ", processType);
			sql.add("    AND PRG_ID = ? ", getProgramCode());
			sql.add("    AND USR_ID = ? ", getUserCode());
			sql.add("    AND ( 1 = 2 ");

			for (int i = 0; i < codeList.size(); i++) {
				sql.add("    OR (TRI_CODE  = ?   ", codeList.get(i));
				sql.add("    AND GYO_NO    = ? ) ", rowNoList.get(i));
			}
			sql.add("        )");

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
