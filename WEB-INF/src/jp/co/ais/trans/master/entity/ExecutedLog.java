package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 実行ログ出力用オブジェクト
 * 
 * @author roh
 */
public class ExecutedLog extends TransferBase {

	/** テーブル */
	public static final String TABLE = "EXE_LOG_TBL";

	/** NoInsert属性 */
	public static final String insert_NO_PERSISTENT_PROPS = "pROGRAM_NAME";

	/** 実行日付 */
	private Date eXCUTED_DATE;

	/** ユーザコード */
	private String uSR_CODE;

	/** ユーザ名称 */
	private String uSR_NAME;

	/** IPアドレス */
	private String iP_ADDRESS;

	/** プログラムコード */
	private String pROGRAM_CODE;

	/** プログラム名称 */
	private String pROGRAM_NAME;

	/** 実行状態 */
	private String sTATE;

	/** 会社コード */
	private String kAI_CODE;

	/**
	 * 実行日付取得
	 * 
	 * @return eXCUTED_DATE
	 */
	public Date getEXCUTED_DATE() {
		return eXCUTED_DATE;
	}

	/**
	 * 実行日付設定
	 * 
	 * @param excuted_date
	 */
	public void setEXCUTED_DATE(Date excuted_date) {
		this.eXCUTED_DATE = excuted_date;
	}

	/**
	 * IPアドレス取得
	 * 
	 * @return iP_ADDRESS
	 */
	public String getIP_ADDRESS() {
		return iP_ADDRESS;
	}

	/**
	 * IPアドレス設定
	 * 
	 * @param ip_address
	 */
	public void setIP_ADDRESS(String ip_address) {
		this.iP_ADDRESS = ip_address;
	}

	/**
	 * プログラムコード取得
	 * 
	 * @return pROGRAM_CODE
	 */
	public String getPROGRAM_CODE() {
		return pROGRAM_CODE;
	}

	/**
	 * プログラムコード設定
	 * 
	 * @param program_code
	 */
	public void setPROGRAM_CODE(String program_code) {
		this.pROGRAM_CODE = program_code;
	}

	/**
	 * 実行状態取得
	 * 
	 * @return sTATE
	 */
	public String getSTATE() {
		return sTATE;
	}

	/**
	 * 実行状態設定
	 * 
	 * @param state
	 */
	public void setSTATE(String state) {
		this.sTATE = state;
	}

	/**
	 * ユーザコード取得
	 * 
	 * @return uSR_CODE
	 */
	public String getUSR_CODE() {
		return uSR_CODE;
	}

	/**
	 * ユーザコード設定
	 * 
	 * @param usr_code
	 */
	public void setUSR_CODE(String usr_code) {
		this.uSR_CODE = usr_code;
	}

	/**
	 * ユーザ名称取得
	 * 
	 * @return uSR_NAME
	 */
	public String getUSR_NAME() {
		return uSR_NAME;
	}

	/**
	 * ユーザ名称設定
	 * 
	 * @param usr_name
	 */
	public void setUSR_NAME(String usr_name) {
		this.uSR_NAME = usr_name;
	}

	/**
	 * プログラム名称取得
	 * 
	 * @return pROGRAM_NAME
	 */
	public String getPROGRAM_NAME() {
		return pROGRAM_NAME;
	}

	/**
	 * プログラム名称設定
	 * 
	 * @param program_name
	 */
	public void setPROGRAM_NAME(String program_name) {
		this.pROGRAM_NAME = program_name;
	}

	/**
	 * kAI_CODE取得
	 * 
	 * @return kAI_CODE
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * kAI_CODE設定
	 * 
	 * @param kai_code
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}
}
