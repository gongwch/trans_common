package jp.co.ais.trans2.common.model.file;

import java.util.List;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.common.file.TFile;

/**
 * ファイル履歴管理Dao
 * @author AIS
 *
 */
public interface FileRecordDao {

	/**
	 * 指定ファイル名の取込履歴を返す
	 * @param file ファイル
	 * @throws TException
	 */
	public List<TFile> getRecordFile(TFile file) throws TException;

	/**
	 * 指定ファイル名の最後に取り込まれた履歴を返す
	 * @param companyCode 会社コード
	 * @param key 取込システムプライマリキー
	 * @param fileName ファイル名
	 * @return 指定ファイル名の最後に取り込まれた履歴
	 * @throws TException
	 */
	public TFile getLastRecordFile(TFile file) throws TException;

	/**
	 * ファイルの履歴をとる
	 * @param file
	 * @throws TException
	 */
	public void recordFile(TFile file) throws TException;

}
