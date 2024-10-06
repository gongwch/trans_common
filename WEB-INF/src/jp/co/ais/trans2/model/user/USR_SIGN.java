package jp.co.ais.trans2.model.user;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.file.*;

/**
 * ユーザー SIGN
 */
public class USR_SIGN extends TransferBase implements TFileTransfer {

	/** テーブル名 */
	public static final String TABLE = "USR_SIGN_ATTACH";

	/** 会社コード */
	protected String KAI_CODE = null;

	/** 担当者コード */
	protected String USR_CODE = null;

	/** ファイル名 */
	protected String FILE_NAME = null;

	/** ファイルデータ */
	protected Object FILE_DATA = null;

	/** ファイル */
	protected File file = null;

	/** サーバサイトファイル名 */
	protected String serverFileName = null;

	/** 登録日付 */
	protected Date INP_DATE = null;

	/** プログラムID */
	protected String PRG_ID = null;

	/** ユーザID */
	protected String USR_ID = null;

	/** ファイルインデックス */
	protected int fileIndex = -1;

	/**
	 * 会社コードの取得
	 * 
	 * @return KAI_CODE 会社コード
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param KAI_CODE 会社コード
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * /** 担当者コードの取得
	 * 
	 * @return USER_CODE 担当者コード
	 */
	public String getUSR_CODE() {
		return USR_CODE;
	}

	/**
	 * 担当者コードの設定
	 * 
	 * @param USR_CODE 担当者コード
	 */
	public void setUSR_CODE(String USR_CODE) {
		this.USR_CODE = USR_CODE;
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
	 * ファイルデータの取得
	 * 
	 * @return FILE_DATA ファイルデータ
	 */
	public Object getFILE_DATA() {
		return FILE_DATA;
	}

	/**
	 * ファイルデータの設定
	 * 
	 * @param FILE_DATA ファイルデータ
	 */
	public void setFILE_DATA(Object FILE_DATA) {
		this.FILE_DATA = FILE_DATA;
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
	 * プログラムIDの取得
	 * 
	 * @return PRG_ID プログラムID
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * プログラムIDの設定
	 * 
	 * @param PRG_ID プログラムID
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
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
	public Object getFileData() {
		return getFILE_DATA();
	}

	/**
	 * ファイルバイナリを設定
	 * 
	 * @param binary
	 */
	public void setFileData(Object binary) {
		setFILE_DATA(binary);
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
