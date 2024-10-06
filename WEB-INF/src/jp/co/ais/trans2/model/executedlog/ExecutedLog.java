package jp.co.ais.trans2.model.executedlog;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 実行ログ参照
 */
public class ExecutedLog extends TransferBase {

	/** 会社コード */
	protected String kaiCode = null;

	/** 実行日時 */
	protected Date excDate = null;

	/** 実行ユーザーコード */
	protected String excCode = null;

	/** 実行ユーザー名称 */
	protected String excName = null;

	/** 実行ユーザー略称 */
	protected String excNames = null;

	/** IPアドレス */
	protected String ipAddress = null;

	/** プログラムコード */
	protected String proCode = null;

	/** プログラム名称 */
	protected String proName = null;

	/** 実行状態 */
	protected String stste = null;

	/**
	 * 会社コードの取得
	 * 
	 * @return kaiCode 会社コード
	 */
	public String getKaiCode() {
		return kaiCode;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param kaiCode 会社コード
	 */
	public void setKaiCode(String kaiCode) {
		this.kaiCode = kaiCode;
	}

	/**
	 * 実行日時の取得
	 * 
	 * @return excDate 実行日時
	 */
	public Date getExcDate() {
		return excDate;
	}

	/**
	 * 実行日時の設定
	 * 
	 * @param excDate 実行日時
	 */
	public void setExcDate(Date excDate) {
		this.excDate = excDate;
	}

	/**
	 * 実行ユーザーコードの取得
	 * 
	 * @return excCode 実行ユーザーコード
	 */
	public String getExcCode() {
		return excCode;
	}

	/**
	 * 実行ユーザーコードの設定
	 * 
	 * @param excCode 実行ユーザーコード
	 */
	public void setExcCode(String excCode) {
		this.excCode = excCode;
	}

	/**
	 * 実行ユーザー名称の取得
	 * 
	 * @return excName 実行ユーザー名称
	 */
	public String getExcName() {
		return excName;
	}

	/**
	 * 実行ユーザー名称の設定
	 * 
	 * @param excName 実行ユーザー名称
	 */
	public void setExcName(String excName) {
		this.excName = excName;
	}

	/**
	 * 実行ユーザー略称の取得
	 * 
	 * @return excNames 実行ユーザー略称
	 */
	public String getExcNames() {
		return excNames;
	}

	/**
	 * 実行ユーザー略称の設定
	 * 
	 * @param excNames 実行ユーザー略称
	 */
	public void setExcNames(String excNames) {
		this.excNames = excNames;
	}

	/**
	 * IPアドレスの取得
	 * 
	 * @return ipAddress IPアドレス
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * IPアドレスの設定
	 * 
	 * @param ipAddress IPアドレス
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * プログラムコードの取得
	 * 
	 * @return proCode プログラムコード
	 */
	public String getProCode() {
		return proCode;
	}

	/**
	 * プログラムコードの設定
	 * 
	 * @param proCode プログラムコード
	 */
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	/**
	 * プログラム名称の取得
	 * 
	 * @return proName プログラム名称
	 */
	public String getProName() {
		return proName;
	}

	/**
	 * プログラム名称の設定
	 * 
	 * @param proName プログラム名称
	 */
	public void setProName(String proName) {
		this.proName = proName;
	}

	/**
	 * 実行状態の取得
	 * 
	 * @return stste 実行状態
	 */
	public String getStste() {
		return stste;
	}

	/**
	 * 実行状態の設定
	 * 
	 * @param stste 実行状態
	 */
	public void setStste(String stste) {
		this.stste = stste;
	}

}
