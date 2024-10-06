package jp.co.ais.trans2.model.close;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 日次更新情報
 * 
 * @author AIS
 */
public class DailyData extends TransferBase {

	/** 会社コード */
	protected String companyCode;

	/** 会社名 */
	protected String companyName = null;

	/** 最終実行日時 */
	protected Date lastUpdateDate = null;

	/**
	 * 会社コードの取得
	 * 
	 * @return companyCode 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 会社名の取得
	 * 
	 * @return companyName 会社名
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * 会社名の設定
	 * 
	 * @param companyName 会社名
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 最終実行日時の取得
	 * 
	 * @return lastUpdateDate 最終実行日時
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * 最終実行日時の設定
	 * 
	 * @param lastUpdateDate 最終実行日時
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

}
