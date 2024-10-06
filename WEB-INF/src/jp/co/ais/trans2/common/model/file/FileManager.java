package jp.co.ais.trans2.common.model.file;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.file.*;

/**
 * ファイル管理インターフェース
 * 
 * @author AIS
 */
public interface FileManager {

	/**
	 * ファイルの履歴をとる
	 * 
	 * @param file ファイル
	 * @throws TException
	 */
	public void recordFile(TFile file) throws TException;

	/**
	 * 指定ファイルの最終取込履歴を返す
	 * 
	 * @param file ファイル
	 * @return file
	 * @throws TException
	 */
	public TFile getLastRecordFile(TFile file) throws TException;

	/**
	 * 指定データをエクセルで出力
	 * 
	 * @param companyCode
	 * @param key
	 * @return 取込履歴データ
	 * @throws TException
	 */
	public byte[] getExcel(String companyCode, String key) throws TException;

}
