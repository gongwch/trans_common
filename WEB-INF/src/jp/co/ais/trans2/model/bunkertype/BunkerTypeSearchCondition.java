package jp.co.ais.trans2.model.bunkertype;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * 油種マスタ検索条件
 */
public class BunkerTypeSearchCondition extends TransferBase implements OPLoginCondition {

	/** 会社コード */
	protected String KAI_CODE = null;

	/** BNKR_TYPE_CODE */
	protected String BNKR_TYPE_CODE = null;

	/** BNKR_TYPE_ID */
	protected String BNKR_TYPE_ID = null;

	/** DISP_ODR */
	protected int DISP_ODR = 0;

	/** 最終更新日時 */
	protected Date lastUpdateDate = null;

	/** 現在件数 */
	protected int nowCount = 0;

	/**
	 * 会社コードの取得
	 * 
	 * @return KAI_CODE 会社コード
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param KAI_CODE 会社コード
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		setKAI_CODE(companyCode);
	}

	/**
	 * BNKR_TYPE_CODE の取得
	 * 
	 * @return BNKR_TYPE_CODE
	 */
	public String getBNKR_TYPE_CODE() {
		return BNKR_TYPE_CODE;
	}

	/**
	 * BNKR_TYPE_CODE の設定
	 * 
	 * @param BNKR_TYPE_CODE
	 */
	public void setBNKR_TYPE_CODE(String BNKR_TYPE_CODE) {
		this.BNKR_TYPE_CODE = BNKR_TYPE_CODE;
	}

	/**
	 * BNKR_TYPE_ID の取得
	 * 
	 * @return BNKR_TYPE_ID
	 */
	public String getBNKR_TYPE_ID() {
		return BNKR_TYPE_ID;
	}

	/**
	 * BNKR_TYPE_ID の設定
	 * 
	 * @param BNKR_TYPE_ID
	 */
	public void setBNKR_TYPE_ID(String BNKR_TYPE_ID) {
		this.BNKR_TYPE_ID = BNKR_TYPE_ID;
	}

	/**
	 * DISP_ODR の取得
	 * 
	 * @return DISP_ODR
	 */
	public int getDISP_ODR() {
		return DISP_ODR;
	}

	/**
	 * DISP_ODR の設定
	 * 
	 * @param DISP_ODR
	 */
	public void setDISP_ODR(int DISP_ODR) {
		this.DISP_ODR = DISP_ODR;
	}

	/**
	 * 最終更新日時の取得
	 * 
	 * @return lastUpdateDate 最終更新日時
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * 最終更新日時の設定
	 * 
	 * @param lastUpdateDate 最終更新日時
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * 現在件数の取得
	 * 
	 * @return nowCount 現在件数
	 */
	public int getNowCount() {
		return nowCount;
	}

	/**
	 * 現在件数の設定
	 * 
	 * @param nowCount 現在件数
	 */
	public void setNowCount(int nowCount) {
		this.nowCount = nowCount;
	}

}
