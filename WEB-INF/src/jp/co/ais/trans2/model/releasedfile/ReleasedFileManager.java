package jp.co.ais.trans2.model.releasedfile;

import jp.co.ais.trans.common.except.*;

/**
 * MG0029ReleasedFileMaster - リリースファイル一覧 - Interface Class
 * 
 * @author AIS
 */
public interface ReleasedFileManager {

	/**
	 * Excel出力
	 * 
	 * @return リリースファイル一覧
	 * @throws TException
	 */
	public byte[] getExcel() throws TException;

}