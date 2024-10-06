package jp.co.ais.trans2.model.security;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.*;

/**
 * ユーザ認証管理マスタ
 */
public class USR_AUTH_MST extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String KAI_CODE = null;

	/** ロックアウト到達回数 */
	protected int LOCK_OUT_ARR_CNT;

	/** ロックアウト開放時間 */
	protected String LOCK_OUT_RELEASE_TIME = null;

	/** 最低パスワード長 */
	protected int PWD_MIN_LENGTH;

	/** パスワード有効期間 */
	protected String PWD_TERM = null;

	/** 複雑レベル */
	protected int COMPLICATE_LVL;

	/** 履歴保持数 */
	protected int HISTORY_MAX_CNT;

	/** 登録日付 */
	protected Date INP_DATE = null;

	/** 更新日付 */
	protected Date UPD_DATE = null;

	/** プログラムID */
	protected String PRG_ID = null;

	/** ユーザID */
	protected String USR_ID = null;

	/** パスワード期限切れ通知日数 */
	protected String PWD_EXP_BEFORE_DAYS = null;

	/**
	 * @return ロックアウト到達回数を戻します。
	 */
	public int getLOCK_OUT_ARR_CNT() {
		return LOCK_OUT_ARR_CNT;
	}

	/**
	 * @param ロックアウト到達回数を設定します。
	 */
	public void setLOCK_OUT_ARR_CNT(int rockcnt) {
		this.LOCK_OUT_ARR_CNT = rockcnt;
	}

	/**
	 * @return 会社コードを戻します。
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * @param 会社コードを設定します。
	 */
	public void setKAI_CODE(String companyCode) {
		this.KAI_CODE = companyCode;
	}

	/**
	 * @return ロックアウト開放時間を戻します。
	 */
	public String getLOCK_OUT_RELEASE_TIME() {
		return LOCK_OUT_RELEASE_TIME;
	}

	/**
	 * @param ロックアウト開放時間を設定します。
	 */
	public void setLOCK_OUT_RELEASE_TIME(String rockcnttime) {
		this.LOCK_OUT_RELEASE_TIME = rockcnttime;
	}

	/**
	 * @return 最低パスワード長を戻します。
	 */
	public int getPWD_MIN_LENGTH() {
		return PWD_MIN_LENGTH;
	}

	/**
	 * @param 最低パスワード長を設定します。
	 */
	public void setPWD_MIN_LENGTH(int minpwd) {
		this.PWD_MIN_LENGTH = minpwd;
	}

	/**
	 * @return パスワード有効期間を戻します。
	 */
	public String getPWD_TERM() {
		return PWD_TERM;
	}

	/**
	 * @param パスワード有効期間を設定します。
	 */
	public void setPWD_TERM(String pwdterm) {
		this.PWD_TERM = pwdterm;
	}

	/**
	 * @return 複雑レベルを戻します。
	 */
	public int getCOMPLICATE_LVL() {
		return COMPLICATE_LVL;
	}

	/**
	 * @param 複雑レベルを設定します。
	 */
	public void setCOMPLICATE_LVL(int diffilev) {
		this.COMPLICATE_LVL = diffilev;
	}

	/**
	 * @return 履歴保持数を戻します。
	 */
	public int getHISTORY_MAX_CNT() {
		return HISTORY_MAX_CNT;
	}

	/**
	 * @param 履歴保持数を設定します。
	 */
	public void setHISTORY_MAX_CNT(int histcnt) {
		this.HISTORY_MAX_CNT = histcnt;
	}

	/**
	 * 登録日付を戻します
	 * 
	 * @return true:存在
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * 登録日付を設定します
	 * 
	 * @param hasSubItem true:存在
	 */
	public void setINP_DATE(Date insdate) {
		this.INP_DATE = insdate;
	}

	/**
	 * 更新日付を戻します
	 * 
	 * @return true:存在
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * 更新日付を設定します
	 * 
	 * @param hasSubItem true:存在
	 */
	public void setUPD_DATE(Date upddate) {
		this.UPD_DATE = upddate;
	}

	/**
	 * プログラムIDを戻します
	 * 
	 * @return true:存在
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * プログラムIDを設定します
	 * 
	 * @param hasSubItem true:存在
	 */
	public void setPRG_ID(String prgId) {
		this.PRG_ID = prgId;
	}

	/**
	 * ユーザIDを戻します
	 * 
	 * @return true:存在
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ユーザIDを設定します
	 * 
	 * @param USR_ID true:存在
	 */
	public void setUSR_ID(String userId) {
		this.USR_ID = userId;
	}

	/**
	 * パスワード期限切れ通知日数を戻します
	 * 
	 * @return true:存在
	 */
	public String getPWD_EXP_BEFORE_DAYS() {
		return PWD_EXP_BEFORE_DAYS;
	}

	/**
	 * パスワード期限切れ通知日数を設定します
	 * 
	 * @param hasSubItem true:存在
	 */
	public void setPWD_EXP_BEFORE_DAYS(String pwdexp) {
		this.PWD_EXP_BEFORE_DAYS = pwdexp;
	}

}
