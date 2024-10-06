package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * 支払方法マスタ検索パラメータ
 */
public class ApHohMstParameter extends TransferBase {

	/** 支払対象区分 社員支払 */
	public static final String SIH_KBN_EMP = "0";

	/** 支払対象区分 社外支払 */
	public static final String SIH_KBN_CMP = "1";

	/** 支払内部コード 社員未収 */
	public static final String NAI_CODE_EMP_DEFERRED = "10";

	/** 支払内部コード 未払振込（社員） */
	public static final String NAI_CODE_EMP_UNPAID = "03";

	/** 会社コード */
	private String kaiCode;

	/** 社員コード */
	private String empCode;

	/** 支払対象区分 */
	private String hohSihKbn;

	/** 支払内部コード (NOT条件) */
	private String notHohNaiCode;

	/** 支払内部コード */
	private String[] hohNaiCode;

	/** 支払方法コード */
	private String hohCode;

	/** 支払方法コード（あいまい検索） */
	private String likeHohCode;

	/** 支払方法名称（あいまい検索） */
	private String likeHohName;

	/** 支払方法検索名称（あいまい検索） */
	private String likeHohNameK;

	/** 有効期間日付 */
	private Date termBasisDate;

	/** 支払方法コード */
	private List<String> hohCodes;

	/**
	 * 有効期間日付を取得する
	 * 
	 * @return 有効期間日付
	 */
	public Date getTermBasisDate() {
		return termBasisDate;
	}

	/**
	 * 有効期間日付を設定する
	 * 
	 * @param termBasisDate
	 */
	public void setTermBasisDate(Date termBasisDate) {
		this.termBasisDate = termBasisDate;
	}

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public String getKaiCode() {
		return kaiCode;
	}

	/**
	 * 会社コード
	 * 
	 * @param kaiCode
	 */
	public void setKaiCode(String kaiCode) {
		this.kaiCode = kaiCode;
	}

	/**
	 * 支払内部コード (NOT条件)
	 * 
	 * @return 支払内部コード (NOT条件)
	 */
	public String getNotHohNaiCode() {
		return notHohNaiCode;
	}

	/**
	 * 支払内部コード (NOT条件)
	 * 
	 * @param notHohNaiCode
	 */
	public void setNotHohNaiCode(String notHohNaiCode) {
		this.notHohNaiCode = notHohNaiCode;
	}

	/**
	 * 支払対象区分を取得する
	 * 
	 * @return 支払対象区分
	 */
	public String getHohCode() {
		return hohCode;
	}

	/**
	 * 支払対象区分を設定する
	 * 
	 * @param hohCode
	 */
	public void setHohCode(String hohCode) {
		this.hohCode = hohCode;
	}

	/**
	 * 支払内部コードを取得する
	 * 
	 * @return 支払内部コード
	 */
	public String[] getHohNaiCode() {
		return hohNaiCode;
	}

	/**
	 * 支払内部コードを設定する
	 * 
	 * @param hohNaiCode
	 */
	public void setHohNaiCode(String[] hohNaiCode) {
		this.hohNaiCode = hohNaiCode;
	}

	/**
	 * 支払対象区分を取得する
	 * 
	 * @return 支払対象区分
	 */
	public String getHohSihKbn() {
		return hohSihKbn;
	}

	/**
	 * 支払対象区分を設定する
	 * 
	 * @param hohSihKbn
	 */
	public void setHohSihKbn(String hohSihKbn) {
		this.hohSihKbn = hohSihKbn;
	}

	/**
	 * 支払方法コード（あいまい検索）を設定する
	 * 
	 * @return 支払方法コード（あいまい検索）
	 */
	public String getLikeHohCode() {

		if (Util.isNullOrEmpty(likeHohCode)) {
			return "";
		}

		return likeHohCode;
	}

	/**
	 * 支払方法コード（あいまい検索）を取得する
	 * 
	 * @param likeHohCode
	 */
	public void setLikeHohCode(String likeHohCode) {
		this.likeHohCode = likeHohCode;
	}

	/**
	 * 支払方法名称（あいまい検索）を取得する
	 * 
	 * @return 支払方法名称（あいまい検索）
	 */
	public String getLikeHohName() {

		if (Util.isNullOrEmpty(likeHohName)) {
			return "";
		}

		return likeHohName;

	}

	/**
	 * 支払方法名称（あいまい検索）を設定する
	 * 
	 * @param likeHohName
	 */
	public void setLikeHohName(String likeHohName) {
		this.likeHohName = likeHohName;
	}

	/**
	 * 支払方法検索名称（あいまい検索）を取得する
	 * 
	 * @return 支払方法検索名称（あいまい検索）
	 */
	public String getLikeHohNameK() {

		if (Util.isNullOrEmpty(likeHohNameK)) {
			return "";
		}

		return likeHohNameK;
	}

	/**
	 * 支払方法検索名称（あいまい検索）を設定する
	 * 
	 * @param likeHohNameK
	 */
	public void setLikeHohNameK(String likeHohNameK) {
		this.likeHohNameK = likeHohNameK;
	}

	/**
	 * 社員コードを取得する
	 * 
	 * @return empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * 社員コードを設定する
	 * 
	 * @param empCode
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * 支払方法コードリストを取得する
	 * 
	 * @return hohCodes
	 */
	public List<String> getHohCodes() {
		return hohCodes;
	}

	/**
	 * 支払方法コードリストを設定する
	 * 
	 * @param hohCodes
	 */
	public void setHohCodes(List<String> hohCodes) {
		this.hohCodes = hohCodes;
	}

}
