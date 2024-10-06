package jp.co.ais.trans2.common.file;

import java.io.*;

import jp.co.ais.trans.common.dt.*;

/**
 * ファイルAdapter
 */
public class TFileAdpater extends TransferBase implements TFileTransfer {

	/** ファイル */
	protected File file = null;

	/** ファイルバイナリ */
	protected Object fileData = null;

	/** ファイルインデックス */
	protected int fileIndex = -1;

	/** サーバサイトファイル名 */
	protected String serverFileName = null;

	/**
	 * ファイルの取得
	 * 
	 * @return file ファイル
	 */
	public File getFile() {
		return file;
	}

	/**
	 * ファイルの設定
	 * 
	 * @param file ファイル
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * ファイルバイナリの取得
	 * 
	 * @return fileData ファイルバイナリ
	 */
	public Object getFileData() {
		return fileData;
	}

	/**
	 * ファイルバイナリの設定
	 * 
	 * @param fileData ファイルバイナリ
	 */
	public void setFileData(Object fileData) {
		this.fileData = fileData;
	}

	/**
	 * ファイルインデックスの取得
	 * 
	 * @return fileIndex ファイルインデックス
	 */
	public int getFileIndex() {
		return fileIndex;
	}

	/**
	 * ファイルインデックスの設定
	 * 
	 * @param fileIndex ファイルインデックス
	 */
	public void setFileIndex(int fileIndex) {
		this.fileIndex = fileIndex;
	}

	/**
	 * サーバサイトファイル名の取得
	 * 
	 * @return serverFileName サーバサイトファイル名
	 */
	public String getServerFileName() {
		return serverFileName;
	}

	/**
	 * サーバサイトファイル名の設定
	 * 
	 * @param serverFileName サーバサイトファイル名
	 */
	public void setServerFileName(String serverFileName) {
		this.serverFileName = serverFileName;
	}

}
