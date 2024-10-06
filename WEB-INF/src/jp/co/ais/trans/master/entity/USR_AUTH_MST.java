package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * ユーザ認証管理マスタ
 * 
 * @author roh
 */
public class USR_AUTH_MST extends TransferBase {

	/** ユーザ認証管理テーブル */
	public static final String TABLE = "USR_AUTH_MST";

	/** 会社コード */
	private String kAI_CODE;

	/** ロックアウト回数 */
	private int lOCK_OUT_ARR_CNT;

	/** ロックアウト解除期間 */
	private int lOCK_OUT_RELEASE_TIME;

	/** パスワード最低個数 */
	private int pWD_MIN_LENGTH;

	/** パスワード有効期間 */
	private int pWD_TERM;

	/** 複雑れべル */
	private int cOMPLICATE_LVL;

	/** 履歴最大個数 */
	private int hISTORY_MAX_CNT;

	/** 入力日付 */
	private Date iNP_DATE;

	/** 更新日付 */
	private Date uPD_DATE;

	/** プログラムID */
	private String pRG_ID;

	/** ユーザID */
	private String uSR_ID;

	/** パスワード期限切れ通知日数 */
	private int pWD_EXP_BEFORE_DAYS;

	/**
	 * 複雑レベル取得
	 * 
	 * @return 複雑レベル
	 */
	public int getCOMPLICATE_LVL() {
		return cOMPLICATE_LVL;
	}

	/**
	 * 複雑レベル設定
	 * 
	 * @param complicate_lvl 複雑レベル
	 */
	public void setCOMPLICATE_LVL(int complicate_lvl) {
		cOMPLICATE_LVL = complicate_lvl;
	}

	/**
	 * 履歴最大個数取得
	 * 
	 * @return 履歴最大個数
	 */
	public int getHISTORY_MAX_CNT() {
		return hISTORY_MAX_CNT;
	}

	/**
	 * 履歴最大個数設定
	 * 
	 * @param history_max_cnt 履歴最大個数
	 */
	public void setHISTORY_MAX_CNT(int history_max_cnt) {
		hISTORY_MAX_CNT = history_max_cnt;
	}

	/**
	 * 入力日付取得
	 * 
	 * @return 入力日付
	 */
	public Date getINP_DATE() {
		return iNP_DATE;
	}

	/**
	 * 入力日付設定
	 * 
	 * @param inp_date 入力日付
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
	 * ロックアウト回数取得
	 * 
	 * @return ロックアウト回数
	 */
	public int getLOCK_OUT_ARR_CNT() {
		return lOCK_OUT_ARR_CNT;
	}

	/**
	 * ロックアウト回数設定
	 * 
	 * @param lock_out_arr_cnt ロックアウト回数
	 */
	public void setLOCK_OUT_ARR_CNT(int lock_out_arr_cnt) {
		lOCK_OUT_ARR_CNT = lock_out_arr_cnt;
	}

	/**
	 * ロックアウト開放時間取得
	 * 
	 * @return ロックアウト開放時間
	 */
	public int getLOCK_OUT_RELEASE_TIME() {
		return lOCK_OUT_RELEASE_TIME;
	}

	/**
	 * ロックアウト開放時間設定
	 * 
	 * @param lock_out_release_time ロックアウト開放時間
	 */
	public void setLOCK_OUT_RELEASE_TIME(int lock_out_release_time) {
		lOCK_OUT_RELEASE_TIME = lock_out_release_time;
	}

	/**
	 * プログラムID取得
	 * 
	 * @return プログラムID
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * プログラムID設定
	 * 
	 * @param prg_id プログラムID
	 */
	public void setPRG_ID(String prg_id) {
		pRG_ID = prg_id;
	}

	/**
	 * パスワード最低個数取得
	 * 
	 * @return パスワード最低個数
	 */
	public int getPWD_MIN_LENGTH() {
		return pWD_MIN_LENGTH;
	}

	/**
	 * パスワード最低個数設定
	 * 
	 * @param pwd_min_length パスワード最低個数
	 */
	public void setPWD_MIN_LENGTH(int pwd_min_length) {
		pWD_MIN_LENGTH = pwd_min_length;
	}

	/**
	 * パスワード有効期間取得
	 * 
	 * @return パスワード有効期間
	 */
	public int getPWD_TERM() {
		return pWD_TERM;
	}

	/**
	 * パスワード有効期間設定
	 * 
	 * @param pwd_term パスワード有効期間
	 */
	public void setPWD_TERM(int pwd_term) {
		pWD_TERM = pwd_term;
	}

	/**
	 * 更新日付取得
	 * 
	 * @return 更新日付
	 */
	public Date getUPD_DATE() {
		return uPD_DATE;
	}

	/**
	 * 更新日付設定
	 * 
	 * @param upd_date 更新日付
	 */
	public void setUPD_DATE(Date upd_date) {
		uPD_DATE = upd_date;
	}

	/**
	 * ユーザID取得
	 * 
	 * @return ユーザID
	 */
	public String getUSR_ID() {
		return uSR_ID;
	}

	/**
	 * ユーザID設定
	 * 
	 * @param usr_id ユーザID
	 */
	public void setUSR_ID(String usr_id) {
		uSR_ID = usr_id;
	}

	/**
	 * パスワード期限切れ通知日数
	 * 
	 * @return パスワード期限切れ通知日数
	 */
	public int getPWD_EXP_BEFORE_DAYS() {
		return pWD_EXP_BEFORE_DAYS;
	}

	/**
	 * パスワード期限切れ通知日数
	 * 
	 * @param pwd_exp_before_days パスワード期限切れ通知日数
	 */
	public void setPWD_EXP_BEFORE_DAYS(int pwd_exp_before_days) {
		pWD_EXP_BEFORE_DAYS = pwd_exp_before_days;
	}
}
