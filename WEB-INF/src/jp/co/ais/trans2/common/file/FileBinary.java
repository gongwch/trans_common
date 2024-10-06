package jp.co.ais.trans2.common.file;

import jp.co.ais.trans.common.dt.*;

/**
 * ファイルバイナリ
 */
public class FileBinary extends TransferBase {

	/** ファイル名 */
	protected String fileName = null;

	/** バイナリ */
	protected byte[] binary = null;

	/**
	 * ファイル名を取得する
	 * 
	 * @return ファイル名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * ファイル名を設定する
	 * 
	 * @param fbDataFileName
	 */
	public void setFileName(String fbDataFileName) {
		this.fileName = fbDataFileName;
	}

	/**
	 * バイナリデータを取得する
	 * 
	 * @return バイナリデータ
	 */
	public byte[] getBinary() {
		return binary;
	}

	/**
	 * バイナリデータを設定する
	 * 
	 * @param binary バイナリデータ
	 */
	public void setBinary(byte[] binary) {
		this.binary = binary;
	}

}