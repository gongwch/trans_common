package jp.co.ais.trans.master.entity;

import jp.co.ais.trans.common.dt.*;

/**
 * リリースファイル用オブジェクト
 * 
 * @author roh
 */
public class ReleasedFileObject extends TransferBase {

	/** フォルダ名 */
	private String pATH_NAME;

	/** ファイル名 */
	private String fILE_NAME;

	/** 更新日付 */
	private String uPDATE_DATE;

	/** ファイルの長さ */
	private long lENGTH;

	/**
	 * ファイルの長さ取得
	 * 
	 * @return ファイルの長さ
	 */

	public long getLENGTH() {
		return lENGTH;
	}

	/**
	 * ファイルの長さ設定
	 * 
	 * @param length
	 */
	public void setLENGTH(long length) {
		lENGTH = length;
	}

	/**
	 * ファイル名取得
	 * 
	 * @return ファイル名
	 */
	public String getFILE_NAME() {
		return fILE_NAME;
	}

	/**
	 * ファイル名設定
	 * 
	 * @param file_name
	 */
	public void setFILE_NAME(String file_name) {
		fILE_NAME = file_name;
	}

	/**
	 * フォルダ名 取得
	 * 
	 * @return フォルダ名
	 */
	public String getPATH_NAME() {
		return pATH_NAME;
	}

	/**
	 * フォルダ名 設定
	 * 
	 * @param path_name
	 */
	public void setPATH_NAME(String path_name) {
		pATH_NAME = path_name;
	}

	/**
	 * 更新日付取得
	 * 
	 * @return 更新日付
	 */
	public String getUPDATE_DATE() {
		return uPDATE_DATE;
	}

	/**
	 * 更新日付設定
	 * 
	 * @param update_date
	 */
	public void setUPDATE_DATE(String update_date) {
		uPDATE_DATE = update_date;
	}

}
