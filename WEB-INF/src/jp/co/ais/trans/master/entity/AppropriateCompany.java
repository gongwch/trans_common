package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 計上会社フィールドエンティティ
 * 
 * @author zhangbo
 */
public class AppropriateCompany extends TransferBase {

	/** 会社コード */
	private String KAI_CODE = "";

	/** 会社略称 */
	private String KAI_NAME_S = "";

	/** 開始会社コード */
	private String strCompanyCode;

	/** 終了会社コード */
	private String endCompanyCode;

	/** 開始年月日 */
	private Date STR_DATE;

	/** 終了年月日 */
	private Date END_DATE;

	/** 本邦通貨コード */
	private String CUR_CODE;

	/** 操作区分：TRUE：REF；FALSE：ロスとフォッカス */
	private boolean blnOptKbn;

	/** 通貨区分 */
	private boolean curKbn;

	/**
	 * 本邦通貨コードを取得
	 * 
	 * @return 本邦通貨コード
	 */
	public String getCUR_CODE() {
		return CUR_CODE;
	}

	/**
	 * 本邦通貨コードを設定
	 * 
	 * @param cur_code
	 */
	public void setCUR_CODE(String cur_code) {
		CUR_CODE = cur_code;
	}

	/**
	 * 終了年月日を取得
	 * 
	 * @return 終了年月日
	 */
	public Date getEND_DATE() {
		return END_DATE;
	}

	/**
	 * 終了年月日を設定
	 * 
	 * @param end_date
	 */
	public void setEND_DATE(Date end_date) {
		END_DATE = end_date;
	}

	/**
	 * 会社コードを取得
	 * 
	 * @return 会社コード
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * 会社コードを設定
	 * 
	 * @param kai_code
	 */
	public void setKAI_CODE(String kai_code) {
		KAI_CODE = kai_code;
	}

	/**
	 * 会社略称を取得
	 * 
	 * @return 会社略称
	 */
	public String getKAI_NAME_S() {
		return KAI_NAME_S;
	}

	/**
	 * 会社略称を設定
	 * 
	 * @param kai_name_s
	 */
	public void setKAI_NAME_S(String kai_name_s) {
		KAI_NAME_S = kai_name_s;
	}

	/**
	 * 開始年月日を取得
	 * 
	 * @return 開始年月日
	 */
	public Date getSTR_DATE() {
		return STR_DATE;
	}

	/**
	 * 開始年月日を設定
	 * 
	 * @param str_date
	 */
	public void setSTR_DATE(Date str_date) {
		STR_DATE = str_date;
	}

	/**
	 * 操作区分を取得
	 * 
	 * @return blnOptKbn
	 */
	public boolean isBlnOptKbn() {
		return blnOptKbn;
	}

	/**
	 * 操作区分を設定
	 * 
	 * @param blnOptKbn
	 */
	public void setBlnOptKbn(boolean blnOptKbn) {
		this.blnOptKbn = blnOptKbn;
	}

	/**
	 * 通貨区分の取得
	 * 
	 * @return curKbn
	 */
	public boolean isCurKbn() {
		return curKbn;
	}

	/**
	 * 通貨区分の設定
	 * 
	 * @param curKbn
	 */
	public void setCurKbn(boolean curKbn) {
		this.curKbn = curKbn;
	}

	/**
	 * 終了会社コードを取得する。
	 * 
	 * @return String 終了会社コード
	 */
	public String getEndCompanyCode() {
		return endCompanyCode;
	}

	/**
	 * 終了会社コードを設定する。
	 * 
	 * @param endCompanyCode
	 */
	public void setEndCompanyCode(String endCompanyCode) {
		this.endCompanyCode = endCompanyCode;
	}

	/**
	 * 開始会社コードを取得する。
	 * 
	 * @return String 開始会社コード
	 */
	public String getStrCompanyCode() {
		return strCompanyCode;
	}

	/**
	 * 開始会社コードを設定する。
	 * 
	 * @param strCompanyCode
	 */
	public void setStrCompanyCode(String strCompanyCode) {
		this.strCompanyCode = strCompanyCode;
	}

}
