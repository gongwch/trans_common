package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * 銀行口座マスタ検索パラメータ
 */
public class ApCbkMstParameter extends TransferBase {

	/** 会社コード */
	private String kaiCode;

	/** 銀行口座コード */
	private String cbkCode;

	/** 銀行口座コード（あいまい検索） */
	private String likeCbkCode;

	/** 銀行名（あいまい検索） */
	private String likeNameS;

	/** 口座番号（あいまい検索） */
	private String likeCbkYknNo;

	/** 有効期間日付 */
	private Date termBasisDate;

	/** 社員FB区分 */
	private boolean empFbKbn;

	/** 社外FB区分 */
	private boolean outFbKbn;

	/** 銀行口座コード */
	private List<String> cbkCodes;

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
	 * 銀行口座コード（あいまい検索）を設定する
	 * 
	 * @return 銀行口座コード（あいまい検索）
	 */
	public String getLikeCbkCode() {

		if (Util.isNullOrEmpty(likeCbkCode)) {
			return "";
		}

		return likeCbkCode;
	}

	/**
	 * 銀行口座コード（あいまい検索）を取得する
	 * 
	 * @param likeCbkCode
	 */
	public void setLikeCbkCode(String likeCbkCode) {
		this.likeCbkCode = likeCbkCode;
	}

	/**
	 * 銀行名（あいまい検索）を取得する
	 * 
	 * @return 銀行名（あいまい検索）
	 */
	public String getLikeNameS() {

		if (Util.isNullOrEmpty(likeNameS)) {
			return "";
		}

		return likeNameS;

	}

	/**
	 * 銀行名（あいまい検索）を設定する
	 * 
	 * @param likeNameS
	 */
	public void setLikeNameS(String likeNameS) {
		this.likeNameS = likeNameS;
	}

	/**
	 * 口座番号（あいまい検索）を取得する
	 * 
	 * @return 口座番号（あいまい検索）
	 */
	public String getLikeCbkYknNo() {

		if (Util.isNullOrEmpty(likeCbkYknNo)) {
			return "";
		}

		return likeCbkYknNo;
	}

	/**
	 * 口座番号（あいまい検索）を設定する
	 * 
	 * @param likeCbkYknNo
	 */
	public void setLikeCbkYknNo(String likeCbkYknNo) {
		this.likeCbkYknNo = likeCbkYknNo;
	}

	/**
	 * 社員FB区分を取得する
	 * 
	 * @return 社員FB区分
	 */
	public boolean getEmpFbKbn() {
		return empFbKbn;
	}

	/**
	 * 社員FB区分を設定する
	 * 
	 * @param empFbKbn
	 */
	public void setEmpFbKbn(boolean empFbKbn) {
		this.empFbKbn = empFbKbn;
	}

	/**
	 * 社外FB区分を取得する
	 * 
	 * @return 社外FB区分
	 */
	public boolean getOutFbKbn() {
		return outFbKbn;
	}

	/**
	 * 社外FB区分を設定する
	 * 
	 * @param outFbKbn
	 */
	public void setOutFbKbn(boolean outFbKbn) {
		this.outFbKbn = outFbKbn;
	}

	/**
	 * 銀行口座コードを取得する
	 * 
	 * @return 銀行口座コード
	 */
	public String getCbkCode() {
		return cbkCode;
	}

	/**
	 * 銀行口座コードを指定する
	 * 
	 * @param cbkCode
	 */
	public void setCbkCode(String cbkCode) {
		this.cbkCode = cbkCode;
	}

	/**
	 * 銀行口座コード取得
	 * 
	 * @return cbkCodes
	 */
	public List<String> getCbkCodes() {
		return cbkCodes;
	}

	/**
	 * 銀行口座コード設定
	 * 
	 * @param cbkCodes
	 */
	public void setCbkCodes(List<String> cbkCodes) {
		this.cbkCodes = cbkCodes;
	}
}
