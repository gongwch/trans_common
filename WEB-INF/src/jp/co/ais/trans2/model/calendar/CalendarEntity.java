package jp.co.ais.trans2.model.calendar;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * カレンダー情報
 * 
 * @author AIS
 */
public class CalendarEntity extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** カレンダー日付 */
	protected Date calDate = null;

	/** 社員支払対象日区分 */
	protected String calSha = null;

	/** 銀行営業日区分 */
	protected String calBank = null;

	/** 臨時支払対象日区分 */
	protected String calRinji = null;

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
	 * カレンダー日付の取得
	 * 
	 * @return calDate カレンダー日付
	 */
	public Date getCalDate() {
		return calDate;
	}

	/**
	 * カレンダー日付の設定
	 * 
	 * @param calDate カレンダー日付
	 */
	public void setCalDate(Date calDate) {
		this.calDate = calDate;
	}

	/**
	 * 社員支払対象日区分の取得
	 * 
	 * @return calSha 社員支払対象日区分
	 */
	public String getCalSha() {
		return calSha;
	}

	/**
	 * 社員支払対象日区分の設定
	 * 
	 * @param calSha 社員支払対象日区分
	 */
	public void setCalSha(String calSha) {
		this.calSha = calSha;
	}

	/**
	 * 銀行営業日区分の取得
	 * 
	 * @return calBank 銀行営業日区分
	 */
	public String getCalBank() {
		return calBank;
	}

	/**
	 * 銀行営業日区分の設定
	 * 
	 * @param calBank 銀行営業日区分
	 */
	public void setCalBank(String calBank) {
		this.calBank = calBank;
	}

	/**
	 * 臨時支払対象日区分の取得
	 * 
	 * @return calRinji 臨時支払対象日区分
	 */
	public String getCalRinji() {
		return calRinji;
	}

	/**
	 * 臨時支払対象日区分の設定
	 * 
	 * @param calRinji 臨時支払対象日区分
	 */
	public void setCalRinji(String calRinji) {
		this.calRinji = calRinji;
	}

	/**
	 * 銀行休業日の取得
	 * 
	 * @return bankHoliday 銀行休業日
	 */
	public boolean isBankHoliday() {
		return "1".equals(calBank);
	}
}
