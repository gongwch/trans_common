package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * パスワード履歴管理マスタ
 * 
 * @author roh
 */
public class PWD_HST_TBL extends TransferBase {

	/**  */
	public static final String TABLE = "PWD_HST_TBL";

	/** 会社コード */
	private String kAI_CODE;

	/** ユーザコード */
	private String uSR_CODE;

	/** 更新日付け */
	private Date iNP_DATE;

	/** パスワード */
	private String pASSWORD;

	/**
	 * 入力日付け習得
	 * 
	 * @return 入力日付け
	 */
	public Date getINP_DATE() {
		return iNP_DATE;
	}

	/**
	 * 入力日付け設定
	 * 
	 * @param inp_date
	 */
	public void setINP_DATE(Date inp_date) {
		iNP_DATE = inp_date;
	}

	/**
	 * 会社コード習得
	 * 
	 * @return 会社コード
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * 会社コード設定
	 * 
	 * @param kai_code
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * パスワード習得
	 * 
	 * @return パスワード
	 */
	public String getPASSWORD() {
		return pASSWORD;
	}

	/**
	 * パスワード設定
	 * 
	 * @param password
	 */
	public void setPASSWORD(String password) {
		pASSWORD = password;
	}

	/**
	 * ユーザコード習得
	 * 
	 * @return ユーザコード
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
		uSR_CODE = usr_code;
	}

}
