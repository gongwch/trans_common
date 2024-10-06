package jp.co.ais.trans2.model.mlit.item;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 輸送実績<br>
 * アイテムBean
 */
public class YJItem extends TransferBase {

	/** 会社コード */
	protected String KAI_CODE;

	/** アイテムコード */
	protected String ITEM_CODE;

	/** アイテム名称 */
	protected String ITEM_NAME;

	/** 備考 */
	protected String REMARK;

	/** アイテム補助コード */
	protected String ITEM_SUB_CODE;

	/** アイテム補助名称 */
	protected String ITEM_SUB_NAME;

	/** 備考（補助） */
	protected String SUB_REMARK;

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
	 * アイテムコードを取得する
	 * 
	 * @return アイテムコード
	 */
	public String getITEM_CODE() {
		return this.ITEM_CODE;
	}

	/**
	 * アイテムコードを設定
	 * 
	 * @param ITEM_CODE アイテムコード
	 */
	public void setITEM_CODE(String ITEM_CODE) {
		this.ITEM_CODE = ITEM_CODE;
	}

	/**
	 * アイテム名称を取得する
	 * 
	 * @return アイテム名称
	 */
	public String getITEM_NAME() {
		return this.ITEM_NAME;
	}

	/**
	 * アイテム名称を設定
	 * 
	 * @param ITEM_NAME アイテム名称
	 */
	public void setITEM_NAME(String ITEM_NAME) {
		this.ITEM_NAME = ITEM_NAME;
	}

	/**
	 * 備考を取得する
	 * 
	 * @return 備考
	 */
	public String getREMARK() {
		return this.REMARK;
	}

	/**
	 * 備考を設定
	 * 
	 * @param REMARK 備考
	 */
	public void setREMARK(String REMARK) {
		this.REMARK = REMARK;
	}

	/**
	 * アイテム補助コードを取得する
	 * 
	 * @return アイテム補助コード
	 */
	public String getITEM_SUB_CODE() {
		return this.ITEM_SUB_CODE;
	}

	/**
	 * アイテム補助コードを設定
	 * 
	 * @param ITEM_SUB_CODE アイテム補助コード
	 */
	public void setITEM_SUB_CODE(String ITEM_SUB_CODE) {
		this.ITEM_SUB_CODE = ITEM_SUB_CODE;
	}

	/**
	 * アイテム補助名称を取得する
	 * 
	 * @return アイテム補助名称
	 */
	public String getITEM_SUB_NAME() {
		return this.ITEM_SUB_NAME;
	}

	/**
	 * アイテム補助名称を設定
	 * 
	 * @param ITEM_SUB_NAME アイテム補助名称
	 */
	public void setITEM_SUB_NAME(String ITEM_SUB_NAME) {
		this.ITEM_SUB_NAME = ITEM_SUB_NAME;
	}

	/**
	 * 備考（補助）を取得する
	 * 
	 * @return 備考（補助）
	 */
	public String getSUB_REMARK() {
		return this.SUB_REMARK;
	}

	/**
	 * 備考（補助）を設定
	 * 
	 * @param SUB_REMARK 備考（補助）
	 */
	public void setSUB_REMARK(String SUB_REMARK) {
		this.SUB_REMARK = SUB_REMARK;
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
