package jp.co.ais.trans2.model.save;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.file.*;

/**
 * マニュアル添付
 */
public class MANUAL_ATTACH extends TransferBase implements TFileTransfer {

	/** 連番 */
	protected int SEQ = 0;

	/** ファイル名 */
	protected String FILE_NAME = null;

	/** ファイル */
	protected File file = null;

	/** サーバーファイル名 */
	protected String SRV_FILE_NAME = null;

	/** 登録日付 */
	protected Date INP_DATE = null;

	/** ユーザID */
	protected String USR_ID = null;

	/** ファイルインデックス */
	protected int fileIndex = -1;

	/**
	 * 連番の取得
	 * 
	 * @return SEQ 連番
	 */
	public int getSEQ() {
		return SEQ;
	}

	/**
	 * 連番の設定
	 * 
	 * @param SEQ 連番
	 */
	public void setSEQ(int SEQ) {
		this.SEQ = SEQ;
	}

	/**
	 * ファイル名の取得
	 * 
	 * @return FILE_NAME ファイル名
	 */
	public String getFILE_NAME() {
		return FILE_NAME;
	}

	/**
	 * ファイル名の設定
	 * 
	 * @param FILE_NAME ファイル名
	 */
	public void setFILE_NAME(String FILE_NAME) {
		this.FILE_NAME = FILE_NAME;
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

	/**
	 * サーバーファイル名の取得
	 * 
	 * @return SRV_FILE_NAME サーバーファイル名
	 */
	public String getSRV_FILE_NAME() {
		return SRV_FILE_NAME;
	}

	/**
	 * サーバーファイル名の設定
	 * 
	 * @param SRV_FILE_NAME サーバーファイル名
	 */
	public void setSRV_FILE_NAME(String SRV_FILE_NAME) {
		this.SRV_FILE_NAME = SRV_FILE_NAME;
	}

	/**
	 * サーバサイトファイル名の取得
	 * 
	 * @return serverFileName サーバサイトファイル名
	 */
	public String getServerFileName() {
		return SRV_FILE_NAME;
	}

	/**
	 * サーバサイトファイル名の設定
	 * 
	 * @param serverFileName サーバサイトファイル名
	 */
	public void setServerFileName(String serverFileName) {
		this.SRV_FILE_NAME = serverFileName;
	}

	/**
	 * 登録日付の取得
	 * 
	 * @return INP_DATE 登録日付
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * 登録日付の設定
	 * 
	 * @param INP_DATE 登録日付
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * ユーザIDの取得
	 * 
	 * @return USR_ID ユーザID
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ユーザIDの設定
	 * 
	 * @param USR_ID ユーザID
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * ファイルバイナリを取得
	 * 
	 * @return binary
	 */
	@Deprecated
	public Object getFileData() {
		return null;
	}

	/**
	 * ファイルバイナリを設定
	 * 
	 * @param binary
	 */
	@Deprecated
	public void setFileData(Object binary) {
		//
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

}
