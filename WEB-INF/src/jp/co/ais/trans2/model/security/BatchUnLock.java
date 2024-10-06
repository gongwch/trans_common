package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.dt.*;

/**
 * バッチ排他解除マスタ
 */
public class BatchUnLock extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String KAI_CODE = null;

	/** 処理ID */
	protected String BAT_ID = null;

	/** プログラム名称 */
	protected String PRG_NAME = null;

	/** 排他ユーザ */
	protected String USR_ID = null;

	/** 排他ユーザ名称 */
	protected String USR_NAME = null;

	/** 排他実行日時 */
	protected String BAT_STR_DATE = null;

	/**
	 * @return 会社コードを戻します。
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * @param 会社コードを設定します。
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * @return 処理IDを戻します。
	 */
	public String getBAT_ID() {
		return BAT_ID;
	}

	/**
	 * @param 処理IDを設定します。
	 */
	public void setBAT_ID(String BAT_ID) {
		this.BAT_ID = BAT_ID;
	}

	/**
	 * @return プログラム名称を戻します。
	 */
	public String getPRG_NAME() {
		return PRG_NAME;
	}

	/**
	 * @param プログラム名称を設定します。
	 */
	public void setPRG_NAME(String PRG_NAME) {
		this.PRG_NAME = PRG_NAME;
	}

	/**
	 * @return 排他ユーザを戻します。
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * @param 排他ユーザを設定します。
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * @return 排他ユーザ名称を戻します。
	 */
	public String getUSR_NAME() {
		return USR_NAME;
	}

	/**
	 * @param 排他ユーザ名称を設定します。
	 */
	public void setUSR_NAME(String USR_NAME) {
		this.USR_NAME = USR_NAME;
	}

	/**
	 * 排他実行日付
	 * 
	 * @return 排他実行日付
	 */
	public String getBAT_STR_DATE() {
		return BAT_STR_DATE;
	}

	/**
	 * 排他実行日付
	 * 
	 * @param 排他実行日付
	 */
	public void setBAT_STR_DATE(String BAT_STR_DATE) {
		this.BAT_STR_DATE = BAT_STR_DATE;

	}

}
