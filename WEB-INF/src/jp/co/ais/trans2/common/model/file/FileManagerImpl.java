package jp.co.ais.trans2.common.model.file;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.model.*;

/**
 * ファイル管理実装
 * 
 * @author AIS
 */
public class FileManagerImpl extends TModel implements FileManager {

	public void recordFile(TFile file) throws TException {

		FileRecordDao dao = (FileRecordDao) getComponent(FileRecordDao.class);
		dao.recordFile(file);

	}

	public TFile getLastRecordFile(TFile file) throws TException {

		FileRecordDao dao = (FileRecordDao) getComponent(FileRecordDao.class);
		return dao.getLastRecordFile(file);

	}

	/**
	 * 取込履歴エクセル出力
	 */
	public byte[] getExcel(String CompanyCode, String key) throws TException {

		TFile file = new TFile();
		file.setCompanyCode(CompanyCode);
		file.setKey(key);

		FileRecordDao dao = (FileRecordDao) getComponent(FileRecordDao.class);
		List<TFile> list = dao.getRecordFile(file);

		if (list == null || list.isEmpty()) {
			return null;
		}

		FileRecordExcel layout = new FileRecordExcel(getUser().getLanguage());
		byte[] data = layout.getExcel(list);

		return data;

	}

}
