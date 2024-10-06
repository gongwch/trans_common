package jp.co.ais.trans.logic.system.impl;

import java.util.*;

/**
 * 実行ログ検索パラメータ
 */
public class ExecutedLogSearchParam {

	/** 検索開始日付 */
	private Date startDate;

	/** 検索終了日付 */
	private Date endDate;

	/** 検索開始ユーザ */
	private String startUser;

	/** 検索終了ユーザ */
	private String endUser;

	/** 検索開始プログラム */
	private String startPrg;

	/** 検索終了プログラム */
	private String endPrg;

	/** 会社コード */
	private String companyCode;

	/** ログインアウト区分 */
	private int isIncludeLogin = 0;

	/** ソート順 */
	private String orderBy;

	/**
	 * companyCode取得
	 * 
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * companyCode設定
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * endDate取得
	 * 
	 * @return endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * endDate設定
	 * 
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * endPrg取得
	 * 
	 * @return endPrg
	 */
	public String getEndPrg() {
		return endPrg;
	}

	/**
	 * endPrg設定
	 * 
	 * @param endPrg
	 */
	public void setEndPrg(String endPrg) {
		this.endPrg = endPrg;
	}

	/**
	 * endUser取得
	 * 
	 * @return endUser
	 */
	public String getEndUser() {
		return endUser;
	}

	/**
	 * endUser設定
	 * 
	 * @param endUser
	 */
	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}

	/**
	 * orderBy取得
	 * 
	 * @return orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * orderBy設定
	 * 
	 * @param orderBy
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * startDate取得
	 * 
	 * @return startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * startDate設定
	 * 
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * startPrg取得
	 * 
	 * @return startPrg
	 */
	public String getStartPrg() {
		return startPrg;
	}

	/**
	 * startPrg設定
	 * 
	 * @param startPrg
	 */
	public void setStartPrg(String startPrg) {
		this.startPrg = startPrg;
	}

	/**
	 * startUser取得
	 * 
	 * @return startUser
	 */
	public String getStartUser() {
		return startUser;
	}

	/**
	 * startUser設定
	 * 
	 * @param startUser
	 */
	public void setStartUser(String startUser) {
		this.startUser = startUser;
	}

	/**
	 * isIncludeLogin取得
	 * 
	 * @return isIncludeLogin
	 */
	public int getIsIncludeLogin() {
		return isIncludeLogin;
	}

	/**
	 * isIncludeLogin設定
	 * 
	 * @param isIncludeLogin
	 */
	public void setIsIncludeLogin(int isIncludeLogin) {
		this.isIncludeLogin = isIncludeLogin;
	}

}
