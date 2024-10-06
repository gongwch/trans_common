package jp.co.ais.trans2.model.slip;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.file.*;

/**
 * 伝票添付
 */
public class SWK_ATTACH extends TransferBase implements TFileTransfer {

	/** テーブル名 */
	public static final String TABLE = "SWK_ATTACH";

	/** 会社コード */
	protected String KAI_CODE = null;

	/** 伝票番号 */
	protected String SWK_DEN_NO = null;

	/** 連番 */
	protected int SEQ = 0;

	/** 担当者コード */
	protected String EMP_CODE = null;

	/** 担当者名称 */
	protected String EMP_NAME = null;

	/** 担当者略称 */
	protected String EMP_NAME_S = null;

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
	 * 伝票番号の取得
	 * 
	 * @return SWK_DEN_NO 伝票番号
	 */
	public String getSWK_DEN_NO() {
		return SWK_DEN_NO;
	}

	/**
	 * 伝票番号の設定
	 * 
	 * @param SWK_DEN_NO 伝票番号
	 */
	public void setSWK_DEN_NO(String SWK_DEN_NO) {
		this.SWK_DEN_NO = SWK_DEN_NO;
	}

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
	 * 担当者コードの取得
	 * 
	 * @return EMP_CODE 担当者コード
	 */
	public String getEMP_CODE() {
		return EMP_CODE;
	}

	/**
	 * 担当者コードの設定
	 * 
	 * @param EMP_CODE 担当者コード
	 */
	public void setEMP_CODE(String EMP_CODE) {
		this.EMP_CODE = EMP_CODE;
	}

	/**
	 * 担当者名称の取得
	 * 
	 * @return EMP_NAME 担当者名称
	 */
	public String getEMP_NAME() {
		return EMP_NAME;
	}

	/**
	 * 担当者名称の設定
	 * 
	 * @param EMP_NAME 担当者名称
	 */
	public void setEMP_NAME(String EMP_NAME) {
		this.EMP_NAME = EMP_NAME;
	}

	/**
	 * 担当者略称の取得
	 * 
	 * @return EMP_NAME_S 担当者略称
	 */
	public String getEMP_NAME_S() {
		return EMP_NAME_S;
	}

	/**
	 * 担当者略称の設定
	 * 
	 * @param EMP_NAME_S 担当者略称
	 */
	public void setEMP_NAME_S(String EMP_NAME_S) {
		this.EMP_NAME_S = EMP_NAME_S;
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
