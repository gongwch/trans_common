package jp.co.ais.trans2.common.file;

import java.io.File;
import java.util.Date;

import jp.co.ais.trans.common.dt.TransferBase;

/**
 * ファイル
 * 
 * @author AIS
 */
public class TFile extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** キーコード */
	protected String key = null;

	/** ファイル名称 */
	protected String fileName = null;

	/** 入力時刻 */
	protected Date inputDate = null;

	/** プログラムコード */
	protected String programCode = null;

	/** ユーザーコード */
	protected String userCode = null;

	/** ファイル */
	protected File file = null;

	/**
	 * 会社コードの取得
	 * 
	 * @return companyCode 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * キーコードの取得
	 * 
	 * @return key キーコード
	 */
	public String getKey() {
		return key;
	}

	/**
	 * キーコードの設定
	 * 
	 * @param key キーコード
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * ファイル名称の取得
	 * 
	 * @return fileName ファイル名称
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * ファイル名称の設定
	 * 
	 * @param fileName ファイル名称
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 入力時刻の取得
	 * 
	 * @return inputDate 入力時刻
	 */
	public Date getInputDate() {
		return inputDate;
	}

	/**
	 * 入力時刻の設定
	 * 
	 * @param inputDate 入力時刻
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * プログラムコードの取得
	 * 
	 * @return programCode プログラムコード
	 */
	public String getProgramCode() {
		return programCode;
	}

	/**
	 * プログラムコードの設定
	 * 
	 * @param programCode プログラムコード
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	/**
	 * ユーザーコードの取得
	 * 
	 * @return userCode ユーザーコード
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * ユーザーコードの設定
	 * 
	 * @param userCode ユーザーコード
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

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

}
