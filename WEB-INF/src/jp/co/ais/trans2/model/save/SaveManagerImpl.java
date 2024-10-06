package jp.co.ais.trans2.model.save;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.file.*;

/**
 * 保存用ロジック
 * 
 * @author AIS
 */
@SuppressWarnings("unused")
public class SaveManagerImpl implements SaveManager {

	/** Save用Dao */
	protected OBJ_SAVEDao oBJ_SAVEDao;

	/**
	 * Save用Dao
	 * 
	 * @param dao Save用Dao
	 */
	public void setOBJ_SAVEDao(OBJ_SAVEDao dao) {
		this.oBJ_SAVEDao = dao;
	}

	/**
	 * 削除処理
	 * 
	 * @param base 削除対象
	 * @throws Exception
	 */
	public void deleteObject(OBJ_SAVE base) throws Exception {
		try {

			// DELETE
			oBJ_SAVEDao.deleteBySeq(base.getKAI_CODE(), base.getUSR_ID(), base.getPRG_ID(), base.getSEQ());

			// SEQ再採番
			oBJ_SAVEDao.updateSeq(base.getKAI_CODE(), base.getUSR_ID(), base.getPRG_ID());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * オブジェクト保存
	 * 
	 * @param list オブジェクトリスト
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
	 * 保存オブジェクト呼び出し
	 * 
	 * @param compCode 会社コード
	 * @param userID ユーザID
	 * @param prgID プログラムID
	 * @param seq シーケンスNo.
	 * @return オブジェクトリスト
	 * @throws Exception
	 */
	public List<OBJ_SAVE> readObject(String compCode, String userID, String prgID, Integer seq) throws Exception {
		return oBJ_SAVEDao.findByKey(compCode, userID, prgID, seq);
	}

	/**
	 * マニュアルの取得
	 * 
	 * @return マニュアルのリスト
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
	 * マニュアル登録
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
				// // サーバサイト一時ファイル→指定フォルダへ保存
				// String serverFileName = FileTransferUtil.createServerFile(bean.getFile());
				// bean.setServerFileName(serverFileName);
				// }

				FileTransferUtil.createServerFile(bean.getFile(), bean.getFILE_NAME());
				bean.setServerFileName(bean.getFILE_NAME()); // 同名で

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
	 * マニュアル削除
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
	 * マニュアル照会
	 * 
	 * @param bean
	 * @return マニュアル
	 * @throws TException
	 */
	public byte[] drilldownManual(MANUAL_ATTACH bean) throws TException {
		if (bean == null || Util.isNullOrEmpty(bean.getSRV_FILE_NAME())) {
			return null;
		}

		return FileTransferUtil.getServerFileNoZip(bean.getFILE_NAME(), bean.getSRV_FILE_NAME());
	}

	/**
	 * SQL用
	 */
	protected class VCreator extends SQLCreator {

		/**
		 * コンストラクター
		 */
		public VCreator() {
			crlf = " ";
		}
	}
}
