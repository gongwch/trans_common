package jp.co.ais.trans2.model.calendar;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * カレンダー情報の検索条件
 * 
 * @author AIS
 */
public class CalendarSearchCondition extends TransferBase implements Cloneable {

	/** 銀行休業日区分 */
	public static String BANK = "CAL_BNK_KBN";

	/** 臨時支払日区分 */
	public static String TEMPORARY = "CAL_HARAI";

	/** 社員支払日区分 */
	public static String EMPLOYEE = "CAL_SHA";

	/** 会社コード */
	protected String companyCode = null;

	/** 開始日付 */
	protected Date dateFrom = null;

	/** 終了日付 */
	protected Date dateTo = null;

	/** 区分カラム */
	protected String divisionColumn = null;

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
	 * 開始日付の取得
	 * 
	 * @return dateFrom 開始日付
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * 開始日付の設定
	 * 
	 * @param dateFrom 開始日付
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * 終了日付の取得
	 * 
	 * @return dateTo 終了日付
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * 終了日付の設定
	 * 
	 * @param dateTo 終了日付
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * 区分カラムの取得
	 * 
	 * @return divisionColumn 区分カラム
	 */
	public String getDivisionColumn() {
		return divisionColumn;
	}

	/**
	 * 区分カラムの設定
	 * 
	 * @param divisionColumn 区分カラム
	 */
	public void setDivisionColumn(String divisionColumn) {
		this.divisionColumn = divisionColumn;
	}

}
