package jp.co.ais.trans2.model.save;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.file.*;

/**
 * �ۑ��p���W�b�N
 * 
 * @author AIS
 */
@SuppressWarnings("unused")
public class SaveManagerImpl implements SaveManager {

	/** Save�pDao */
	protected OBJ_SAVEDao oBJ_SAVEDao;

	/**
	 * Save�pDao
	 * 
	 * @param dao Save�pDao
	 */
	public void setOBJ_SAVEDao(OBJ_SAVEDao dao) {
		this.oBJ_SAVEDao = dao;
	}

	/**
	 * �폜����
	 * 
	 * @param base �폜�Ώ�
	 * @throws Exception
	 */
	public void deleteObject(OBJ_SAVE base) throws Exception {
		try {

			// DELETE
			oBJ_SAVEDao.deleteBySeq(base.getKAI_CODE(), base.getUSR_ID(), base.getPRG_ID(), base.getSEQ());

			// SEQ�č̔�
			oBJ_SAVEDao.updateSeq(base.getKAI_CODE(), base.getUSR_ID(), base.getPRG_ID());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * �I�u�W�F�N�g�ۑ�
	 * 
	 * @param list �I�u�W�F�N�g���X�g
	 * @throws Exception
	 */
	public void saveObject(List<OBJ_SAVE> list) throws Exception {
		try {
			if (list.isEmpty()) {
				return;
			}

			OBJ_SAVE base = list.get(0);

			// DELETE
			oBJ_SAVEDao.deleteBySeq(base.getKAI_CODE(), base.getUSR_ID(), base.getPRG_ID(), null);

			for (OBJ_SAVE entity : list) {
				// INSERT
				oBJ_SAVEDao.insert(entity);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * �ۑ��I�u�W�F�N�g�Ăяo��
	 * 
	 * @param compCode ��ЃR�[�h
	 * @param userID ���[�UID
	 * @param prgID �v���O����ID
	 * @param seq �V�[�P���XNo.
	 * @return �I�u�W�F�N�g���X�g
	 * @throws Exception
	 */
	public List<OBJ_SAVE> readObject(String compCode, String userID, String prgID, Integer seq) throws Exception {
		return oBJ_SAVEDao.findByKey(compCode, userID, prgID, seq);
	}

	/**
	 * �}�j���A���̎擾
	 * 
	 * @return �}�j���A���̃��X�g
	 * @throws TException
	 */
	public List<MANUAL_ATTACH> getManual() throws TException {
		List<MANUAL_ATTACH> list = new ArrayList<MANUAL_ATTACH>();

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			conn = DBUtil.getConnection();

			VCreator sql = new VCreator();
			sql.add(" SELECT SEQ ");
			sql.add("       ,FILE_NAME ");
			sql.add("       ,SRV_FILE_NAME ");
			sql.add("       ,INP_DATE ");
			sql.add("       ,USR_ID ");
			sql.add(" FROM MANUAL_ATTACH ");
			sql.add(" ORDER BY SEQ ");

			st = DBUtil.getStatement(conn);
			rs = DBUtil.select(st, sql);

			while (rs.next()) {
				MANUAL_ATTACH bean = new MANUAL_ATTACH();
				bean.setSEQ(rs.getInt("SEQ"));
				bean.setFILE_NAME(rs.getString("FILE_NAME"));
				bean.setSRV_FILE_NAME(rs.getString("SRV_FILE_NAME"));
				bean.setINP_DATE(rs.getTimestamp("INP_DATE"));
				bean.setUSR_ID(rs.getString("USR_ID"));
				list.add(bean);
			}

		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(rs);
			DBUtil.close(st);
			DBUtil.close(conn);
		}

		return list;
	}

	/**
	 * �}�j���A���o�^
	 * 
	 * @param list
	 * @throws TException
	 */
	public List<MANUAL_ATTACH> entryManual(List<MANUAL_ATTACH> list) throws TException {

		Connection conn = null;

		try {

			conn = DBUtil.getConnection();

			List<SQLCreator> sqlList = new ArrayList<SQLCreator>();

			for (MANUAL_ATTACH bean : list) {
				// if (!Util.isNullOrEmpty(bean.getFile())) {
				// // �T�[�o�T�C�g�ꎞ�t�@�C�����w��t�H���_�֕ۑ�
				// String serverFileName = FileTransferUtil.createServerFile(bean.getFile());
				// bean.setServerFileName(serverFileName);
				// }

				FileTransferUtil.createServerFile(bean.getFile(), bean.getFILE_NAME());
				bean.setServerFileName(bean.getFILE_NAME()); // ������

				VCreator sql = new VCreator();
				sql.add(" INSERT INTO MANUAL_ATTACH ( ");
				sql.add("  SEQ ");
				sql.add(" ,FILE_NAME ");
				sql.add(" ,SRV_FILE_NAME ");
				sql.add(" ,INP_DATE ");
				sql.add(" ,USR_ID ");
				sql.add(" ) VALUES ( ");
				sql.add("  ? ", bean.getSEQ());
				sql.add(" ,? ", bean.getFILE_NAME());
				sql.add(" ,? ", bean.getSRV_FILE_NAME());
				sql.adt(" ,? ", bean.getINP_DATE());
				sql.add(" ,?)", bean.getUSR_ID());

				sqlList.add(sql);
			}

			if (!sqlList.isEmpty()) {
				DBUtil.executeBatch(conn, sqlList);
			}

		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(conn);
		}

		return getManual();
	}

	/**
	 * �}�j���A���폜
	 * 
	 * @param list
	 * @throws TException
	 */
	public void deleteManual(List<MANUAL_ATTACH> list) throws TException {

		Connection conn = null;

		try {

			conn = DBUtil.getConnection();

			List<SQLCreator> sqlList = new ArrayList<SQLCreator>();

			for (MANUAL_ATTACH bean : list) {
				VCreator sql = new VCreator();
				sql.add(" DELETE FROM MANUAL_ATTACH ");
				sql.add(" WHERE  SEQ = ? ", bean.getSEQ());

				sqlList.add(sql);
			}

			if (!sqlList.isEmpty()) {
				DBUtil.executeBatch(conn, sqlList);
			}

		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �}�j���A���Ɖ�
	 * 
	 * @param bean
	 * @return �}�j���A��
	 * @throws TException
	 */
	public byte[] drilldownManual(MANUAL_ATTACH bean) throws TException {
		if (bean == null || Util.isNullOrEmpty(bean.getSRV_FILE_NAME())) {
			return null;
		}

		return FileTransferUtil.getServerFileNoZip(bean.getFILE_NAME(), bean.getSRV_FILE_NAME());
	}

	/**
	 * SQL�p
	 */
	protected class VCreator extends SQLCreator {

		/**
		 * �R���X�g���N�^�[
		 */
		public VCreator() {
			crlf = " ";
		}
	}
}
