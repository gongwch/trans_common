package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * ロックアウトマスタエンティティ
 * 
 * @author roh
 */
public class LOCK_OUT_TBL extends TransferBase {

	/** ロックアウトテーブル */
	public static final String TABLE = "LOCK_OUT_TBL";

	/** ユーザ名称設定（参照系クエリ用） */
	public static final String insert_NO_PERSISTENT_PROPS = "uSR_NAME";

	/** 会社コード */
	private String kAI_CODE;

	/** ユーザコード */
	private String uSR_CODE;

	/** 失敗回数 */
	private int fAIL_COUNT;

	/** 失敗日時 */
	private Date fAIL_DATE;

	/** 入力日時 */
	private Date iNP_DATE;

	/** ユーザ名 */
	private String uSR_NAME;

	/**
	 * 失敗回数取得
	 * 
	 * @return 失敗回数
	 */
	public int getFAIL_COUNT() {
		return fAIL_COUNT;
	}

	/**
	 * 失敗回数設定
	 * 
	 * @param fail_count 失敗回数
	 */
	public void setFAIL_COUNT(int fail_count) {
		fAIL_COUNT = fail_count;
	}

	/**
	 * 失敗日時取得
	 * 
	 * @return 失敗日時
	 */
	public Date getFAIL_DATE() {
		return fAIL_DATE;
	}

	/**
	 * 失敗日時設定
	 * 
	 * @param fail_date 失敗日時
	 */
	public void setFAIL_DATE(Date fail_date) {
		fAIL_DATE = fail_date;
	}

	/**
	 * 入力日時取得
	 * 
	 * @return 入力日時
	 */
	public Date getINP_DATE() {
		return iNP_DATE;
	}

	/**
	 * 入力日時設定
	 * 
	 * @param inp_date 入力日時
	 */
	public void setINP_DATE(Date inp_date) {
		iNP_DATE = inp_date;
	}

	/**
	 * 会社コード取得
	 * 
	 * @return 会社コード
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * 会社コード設定
	 * 
	 * @param kai_code 会社コード
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * ユーザコード取得
	 * 
	 * @return ユーザコード
	 */
	public String getUSR_CODE() {
		return uSR_CODE;
	}

	/**
	 * ユーザコード設定
	 * 
	 * @param code ユーザコード
	 */
	public void setUSR_CODE(String code) {
		uSR_CODE = code;
	}

	/**
	 * ユーザ名称取得
	 * 
	 * @return ユーザ名称
	 */
	public String getUSR_NAME() {
		return uSR_NAME;
	}

	/**
	 * ユーザ名称設定
	 * 
	 * @param usr_name ユーザ名称
	 */
	public void setUSR_NAME(String usr_name) {
		uSR_NAME = usr_name;
	}
}
