package jp.co.ais.trans2.model.executedlog;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * MG0028ExecutedLog - 実行ログ参照 - SearchCondition Class
 * 
 * @author AIS
 */
public class ExecutedLogSearchCondition extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String companyCode = null;

	/** 対象年月日（開始） */
	protected Date dateFrom = null;

	/** 対象年月日（終了） */
	protected Date dateTo = null;

	/** ユーザー範囲検索（開始） */
	protected String userFrom = null;

	/** ユーザー範囲検索（終了） */
	protected String userTo = null;

	/** プログラム範囲検索（開始） */
	protected String programFrom = null;

	/** プログラム範囲検索（終了） */
	protected String programTo = null;

	/** ログイン・ログアウトを対象 */
	protected boolean isLogin = false;

	/** 並び順 */
	protected int sort;

	/**
	 * 会社コード取得
	 * 
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コード設定
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 対象年月日（開始）の取得
	 * 
	 * @return dateFrom 対象年月日（開始）
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * 対象年月日（開始）の設定
	 * 
	 * @param dateFrom 対象年月日（開始）
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * 対象年月日（終了）の取得
	 * 
	 * @return dateTo 対象年月日（終了）
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * 対象年月日（終了）の設定
	 * 
	 * @param dateTo 対象年月日（終了）
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * ユーザー範囲検索（開始）の取得
	 * 
	 * @return userFrom ユーザー範囲検索（開始）
	 */
	public String getUserFrom() {
		return userFrom;
	}

	/**
	 * ユーザー範囲検索（開始）の設定
	 * 
	 * @param userFrom ユーザー範囲検索（開始）
	 */
	public void setUserFrom(String userFrom) {
		this.userFrom = userFrom;
	}

	/**
	 * ユーザー範囲検索（終了）の取得
	 * 
	 * @return userTo ユーザー範囲検索（終了）
	 */
	public String getUserTo() {
		return userTo;
	}

	/**
	 * ユーザー範囲検索（終了）の設定
	 * 
	 * @param userTo ユーザー範囲検索（終了）
	 */
	public void setUserTo(String userTo) {
		this.userTo = userTo;
	}

	/**
	 * プログラム範囲検索（開始）の取得
	 * 
	 * @return programFrom プログラム範囲検索（開始）
	 */
	public String getProgramFrom() {
		return programFrom;
	}

	/**
	 * プログラム範囲検索（開始）の設定
	 * 
	 * @param programFrom プログラム範囲検索（開始）
	 */
	public void setProgramFrom(String programFrom) {
		this.programFrom = programFrom;
	}

	/**
	 * プログラム範囲検索（終了）の取得
	 * 
	 * @return programTo プログラム範囲検索（終了）
	 */
	public String getProgramTo() {
		return programTo;
	}

	/**
	 * プログラム範囲検索（終了）の設定
	 * 
	 * @param programTo プログラム範囲検索（終了）
	 */
	public void setProgramTo(String programTo) {
		this.programTo = programTo;
	}

	/**
	 * ログイン・ログアウトを対象の取得
	 * 
	 * @return isLogin ログイン・ログアウトを対象
	 */
	public boolean isIsLogin() {
		return isLogin;
	}

	/**
	 * ログイン・ログアウトを対象の設定
	 * 
	 * @param isLogin ログイン・ログアウトを対象
	 */
	public void setIsLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	/**
	 * 並び順の取得
	 * 
	 * @return sort 並び順
	 */
	public int getSort() {
		return sort;
	}

	/**
	 * 並び順の設定
	 * 
	 * @param sort 並び順
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

}