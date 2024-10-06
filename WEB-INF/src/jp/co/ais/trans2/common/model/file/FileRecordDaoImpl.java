package jp.co.ais.trans2.common.model.file;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.db.SQLUtil;
import jp.co.ais.trans2.common.file.TFile;
import jp.co.ais.trans2.model.TModel;

/**
 * ファイル履歴管理Dao実装
 * @author AIS
 *
 */
public class FileRecordDaoImpl extends TModel implements FileRecordDao {

	public TFile getLastRecordFile(TFile file) throws TException {

		List<TFile> fileList = getRecordFile(file);

		if (fileList == null || fileList.isEmpty()) {
			return null;
		}
		return fileList.get(0);

	}

	public List<TFile> getRecordFile(TFile file) throws TException {

		Connection conn = DBUtil.getConnection();

		List<TFile> list = new ArrayList<TFile>();

		try {

			String sql =
				" SELECT " +
					" fr.KAI_CODE, " +
					" fr.KEY_CODE, " +
					" fr.FILE_NAME, " +
					" fr.INP_DATE, " +
					" fr.PRG_ID, " +
					" fr.USR_ID " +
				" FROM  " +
					" FILE_RECORD fr " +
				" WHERE fr.KAI_CODE = " + SQLUtil.getParam(file.getCompanyCode()) +
				" AND	fr.KEY_CODE = " + SQLUtil.getParam(file.getKey());  
				
				if (!Util.isNullOrEmpty(file.getFileName())) {
					sql +=
						" AND fr.FILE_NAME = " +SQLUtil.getParam(file.getFileName());
				}

				sql +=
					" ORDER BY " +
						" fr.INP_DATE DESC ";

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
			
		} finally {
			DBUtil.close(conn);
		}

		return list;

	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected TFile mapping(ResultSet rs) throws Exception {

		TFile bean = new TFile();
		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setKey(rs.getString("KEY_CODE"));
		bean.setFileName(rs.getString("FILE_NAME"));
		bean.setInputDate(rs.getTimestamp("INP_DATE"));
		bean.setProgramCode(rs.getString("PRG_ID"));
		bean.setUserCode(rs.getString("USR_ID"));
	
		return bean;
	}

	public void recordFile(TFile file) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			String sql =
				" INSERT INTO FILE_RECORD ( " +
					" KAI_CODE, " +
					" KEY_CODE, " +
					" FILE_NAME, " +
					" INP_DATE, " +
					" PRG_ID, " +
					" USR_ID " +
				" ) VALUES ( " +
					SQLUtil.getParam(file.getCompanyCode()) + ", " +
					SQLUtil.getParam(file.getKey()) + ", " +
					SQLUtil.getParam(file.getFileName()) + ", " +
					SQLUtil.getYMDHMSParam(file.getInputDate()) + ", " +
					SQLUtil.getParam(getProgramCode()) + ", " +
					SQLUtil.getParam(file.getUserCode()) +
				" ) ";

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

}
