package jp.co.ais.trans2.common.file;

import java.io.*;

/**
 * ファイル送受信IF
 */
public interface TFileTransfer {

	/**
	 * ファイルを取得
	 * 
	 * @return file
	 */
	public File getFile();

	/**
	 * ファイルを設定
	 * 
	 * @param file
	 */
	public void setFile(File file);

	/**
	 * ファイルバイナリを取得
	 * 
	 * @return binary
	 */
	public Object getFileData();

	/**
	 * ファイルバイナリを設定
	 * 
	 * @param binary
	 */
	public void setFileData(Object binary);

	/**
	 * ファイルインデックスの取得
	 * 
	 * @return fileIndex ファイルインデックス
	 */
	public int getFileIndex();

	/**
	 * ファイルインデックスの設定
	 * 
	 * @param fileIndex ファイルインデックス
	 */
	public void setFileIndex(int fileIndex);

	/**
	 * サーバサイトファイル名の取得
	 * 
	 * @return serverFileName サーバサイトファイル名
	 */
	public String getServerFileName();

	/**
	 * サーバサイトファイル名の設定
	 * 
	 * @param serverFileName サーバサイトファイル名
	 */
	public void setServerFileName(String serverFileName);

}
