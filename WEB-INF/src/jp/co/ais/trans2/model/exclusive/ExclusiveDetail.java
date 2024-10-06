package jp.co.ais.trans2.model.exclusive;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 排他明細一覧マスタ
 */
public class ExclusiveDetail extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String KAI_CODE = null;

	/** 処理区分 */
	protected String SHORI_KBN = null;

	/** 排他キー */
	protected String HAITA_KEY = null;

	/** 行番号 */
	protected String GYO_NO = null;

	/** 処理日時 */
	protected Date INP_DATE = null;

	/** プログラムID */
	protected String PRG_ID = null;

	/** プログラム名称 */
	protected String PRG_NAME = null;

	/** ユーザID */
	protected String USR_ID = null;

	/** ユーザ名称 */
	protected String USR_NAME = null;

	/**
	 * @return 会社コードを戻します。
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * @param KAI_CODE 会社コードを設定します。
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * @return 処理区分を戻します。
	 */
	public String getSHORI_KBN() {
		return SHORI_KBN;
	}

	/**
	 * @param SHORI_KBN 処理区分を設定します。
	 */
	public void setSHORI_KBN(String SHORI_KBN) {
		this.SHORI_KBN = SHORI_KBN;
	}

	/**
	 * @return 排他キーを戻します。
	 */
	public String getHAITA_KEY() {
		return HAITA_KEY;
	}

	/**
	 * @param HAITA_KEY 排他キーを設定します。
	 */
	public void setHAITA_KEY(String HAITA_KEY) {
		this.HAITA_KEY = HAITA_KEY;
	}

	/**
	 * @return 行番号を戻します。
	 */
	public String getGYO_NO() {
		return GYO_NO;
	}

	/**
	 * @param GYO_NO 行番号を設定します。
	 */
	public void setGYO_NO(String GYO_NO) {
		this.GYO_NO = GYO_NO;
	}

	/**
	 * @return 処理日時を戻します。
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * @param INP_DATE 処理日時を設定します。
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * @return プログラムIDを戻します。
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * @param PRG_ID プログラムIDを設定します。
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * @return プログラム名称を戻します。
	 */
	public String getPRG_NAME() {
		return PRG_NAME;
	}

	/**
	 * @param PRG_NAME プログラム名称を設定します。
	 */
	public void setPRG_NAME(String PRG_NAME) {
		this.PRG_NAME = PRG_NAME;
	}

	/**
	 * @return ユーザIDを戻します。
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * @param USR_ID ユーザIDを設定します。
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * @return ユーザ名称を戻します。
	 */
	public String getUSR_NAME() {
		return USR_NAME;
	}

	/**
	 * @param USR_NAME ユーザ名称を設定します。
	 */
	public void setUSR_NAME(String USR_NAME) {
		this.USR_NAME = USR_NAME;
	}

}