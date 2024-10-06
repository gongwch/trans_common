package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 伝票排他強制解除用ValueObject
 */
public class SlipUnlockObject extends TransferBase {

	/** 会社コード */
	private String companyCode;

	/** サブシステムコード */
	private int subSystemCode;

	/** ユーザコード */
	private String userCode;

	/** ユーザ名称 */
	private String userName;

	/** 伝票番号 */
	private String silpNo;

	/** 伝票名称 */
	private String silpName;

	/** 伝票摘要 */
	private String silpTek;

	/** 伝票日付 */
	private Date silpDate;

	/** 排他日付 */
	private Date exclusiveDate;

	/**
	 * 会社コード取得
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コード設定
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 排他日付取得
	 * 
	 * @return 排他日付
	 */
	public Date getExclusiveDate() {
		return exclusiveDate;
	}

	/**
	 * 排他日付設定
	 * 
	 * @param exclusiveDate 排他日付
	 */
	public void setExclusiveDate(Date exclusiveDate) {
		this.exclusiveDate = exclusiveDate;
	}

	/**
	 * 伝票日付取得
	 * 
	 * @return 伝票日付
	 */
	public Date getSilpDate() {
		return silpDate;
	}

	/**
	 * 伝票日付設定
	 * 
	 * @param silpDate 伝票日付
	 */
	public void setSilpDate(Date silpDate) {
		this.silpDate = silpDate;
	}

	/**
	 * 伝票名称取得
	 * 
	 * @return 伝票名称
	 */
	public String getSilpName() {
		return silpName;
	}

	/**
	 * 伝票名称設定
	 * 
	 * @param silpName 伝票名称
	 */
	public void setSilpName(String silpName) {
		this.silpName = silpName;
	}

	/**
	 * 伝票コード取得
	 * 
	 * @return 伝票コード
	 */
	public String getSilpNo() {
		return silpNo;
	}

	/**
	 * 伝票コード設定
	 * 
	 * @param silpNo 伝票コード
	 */
	public void setSilpNo(String silpNo) {
		this.silpNo = silpNo;
	}

	/**
	 * 伝票摘要取得
	 * 
	 * @return 伝票摘要
	 */
	public String getSilpTek() {
		return silpTek;
	}

	/**
	 * 伝票摘要設定
	 * 
	 * @param silpTek 伝票摘要
	 */
	public void setSilpTek(String silpTek) {
		this.silpTek = silpTek;
	}

	/**
	 * サブシステムコード取得
	 * 
	 * @return サブシステムコード
	 */
	public int getSubSystemCode() {
		return subSystemCode;
	}

	/**
	 * サブシステムコード設定
	 * 
	 * @param subSystemCode サブシステムコード
	 */
	public void setSubSystemCode(int subSystemCode) {
		this.subSystemCode = subSystemCode;
	}

	/**
	 * ユーザコード取得
	 * 
	 * @return ユーザコード
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * ユーザコード設定
	 * 
	 * @param userCode ユーザコード
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * ユーザ名称取得
	 * 
	 * @return ユーザ名称
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * ユーザ名称設定
	 * 
	 * @param userName ユーザ名称
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
