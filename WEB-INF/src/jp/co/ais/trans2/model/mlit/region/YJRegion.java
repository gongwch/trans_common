package jp.co.ais.trans2.model.mlit.region;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 輸送実績<br>
 * 国Bean
 */
public class YJRegion extends TransferBase {

	/** 会社コード */
	protected String KAI_CODE;

	/** 国コード */
	protected String REGION_CODE;

	/** 国名称 */
	protected String REGION_NAME;

	/** 備考 */
	protected String REGION_REMARK;

	/** 地域コード */
	protected String COUNTRY_CODE;

	/** 地域名称 */
	protected String COUNTRY_NAME;

	/** 備考（地域） */
	protected String COUNTRY_REMARK;

	/** 登録日付 */
	protected Date INP_DATE;

	/** 更新日付 */
	protected Date UPD_DATE;

	/** Program ID */
	protected String PRG_ID;

	/** User ID */
	protected String USR_ID;

	/**
	 * 会社コード を取得する
	 * 
	 * @return 会社コード
	 */
	public String getKAI_CODE() {
		return this.KAI_CODE;
	}

	/**
	 * 会社コード を設定
	 * 
	 * @param KAI_CODE 会社コード
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * 国コードを取得する
	 * 
	 * @return 国コード
	 */
	public String getREGION_CODE() {
		return this.REGION_CODE;
	}

	/**
	 * 国コードを設定
	 * 
	 * @param REGION_CODE 国コード
	 */
	public void setREGION_CODE(String REGION_CODE) {
		this.REGION_CODE = REGION_CODE;
	}

	/**
	 * 国名称を取得する
	 * 
	 * @return 国名称
	 */
	public String getREGION_NAME() {
		return this.REGION_NAME;
	}

	/**
	 * 国名称を設定
	 * 
	 * @param REGION_NAME 国名称
	 */
	public void setREGION_NAME(String REGION_NAME) {
		this.REGION_NAME = REGION_NAME;
	}

	/**
	 * 備考を取得する
	 * 
	 * @return 備考
	 */
	public String getREGION_REMARK() {
		return this.REGION_REMARK;
	}

	/**
	 * 備考を設定
	 * 
	 * @param REGION_REMARK 備考
	 */
	public void setREGION_REMARK(String REGION_REMARK) {
		this.REGION_REMARK = REGION_REMARK;
	}

	/**
	 * 地域コードを取得する
	 * 
	 * @return 地域コード
	 */
	public String getCOUNTRY_CODE() {
		return this.COUNTRY_CODE;
	}

	/**
	 * 地域コードを設定
	 * 
	 * @param COUNTRY_CODE 地域コード
	 */
	public void setCOUNTRY_CODE(String COUNTRY_CODE) {
		this.COUNTRY_CODE = COUNTRY_CODE;
	}

	/**
	 * 地域名称を取得する
	 * 
	 * @return 地域名称
	 */
	public String getCOUNTRY_NAME() {
		return this.COUNTRY_NAME;
	}

	/**
	 * 地域名称を設定
	 * 
	 * @param COUNTRY_NAME 地域名称
	 */
	public void setCOUNTRY_NAME(String COUNTRY_NAME) {
		this.COUNTRY_NAME = COUNTRY_NAME;
	}

	/**
	 * 備考（地域）を取得する
	 * 
	 * @return 備考（地域）
	 */
	public String getCOUNTRY_REMARK() {
		return this.COUNTRY_REMARK;
	}

	/**
	 * 備考（地域）を設定
	 * 
	 * @param COUNTRY_REMARK 備考（地域）
	 */
	public void setCOUNTRY_REMARK(String COUNTRY_REMARK) {
		this.COUNTRY_REMARK = COUNTRY_REMARK;
	}

	/**
	 * 登録日付を取得する
	 * 
	 * @return 登録日付
	 */
	public Date getINP_DATE() {
		return this.INP_DATE;
	}

	/**
	 * 登録日付を設定
	 * 
	 * @param INP_DATE 登録日付
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * 更新日付を取得する
	 * 
	 * @return 更新日付
	 */
	public Date getUPD_DATE() {
		return this.UPD_DATE;
	}

	/**
	 * 更新日付を設定
	 * 
	 * @param UPD_DATE 更新日付
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * Program IDを取得する
	 * 
	 * @return Program ID
	 */
	public String getPRG_ID() {
		return this.PRG_ID;
	}

	/**
	 * Program IDを設定
	 * 
	 * @param PRG_ID Program ID
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * User IDを取得する
	 * 
	 * @return User ID
	 */
	public String getUSR_ID() {
		return this.USR_ID;
	}

	/**
	 * User IDを設定
	 * 
	 * @param USR_ID User ID
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

}
