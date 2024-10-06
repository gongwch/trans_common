package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 銀行マスタ検索パラメータ
 */
public class BnkMstParameter extends TransferBase {

	/** 銀行コード */
	private String bnkCode = "";

	/** 支店コード */
	private String bnkStnCode = "";

	/** 銀行コード（あいまい） */
	private String likeBnkCode = "";

	/** 支店コード（あいまい） */
	private String likeBnkStnCode = "";

	/** 銀行名 */
	private String likeBnkName = "";

	/** 銀行検索名称 */
	private String likeBnkNameK = "";

	/** 支店名 */
	private String likeBnkStnName = "";

	/** 支店検索名称 */
	private String likeBnkStnNameK = "";

	/** 銀行開始コード */
	private String bnkCodeBegin = "";

	/** 銀行終了コード */
	private String bnkCodeEnd = "";

	/** 支店開始コード */
	private String bnkStnCodeBegin = "";

	/** 支店終了コード */
	private String bnkStnCodeEnd = "";

	/** 有効期間日付 */
	private Date termBasisDate = null;

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
	 * 銀行コード取得
	 * 
	 * @return 銀行コード
	 */
	public String getBnkCode() {
		return bnkCode;
	}

	/**
	 * 銀行コード設定
	 * 
	 * @param bnkCode 銀行コード
	 */
	public void setBnkCode(String bnkCode) {
		this.bnkCode = bnkCode;
	}

	/**
	 * bnkCodeBegin取得
	 * 
	 * @return bnkCodeBegin
	 */
	public String getBnkCodeBegin() {
		return bnkCodeBegin;
	}

	/**
	 * bnkCodeBegin設定
	 * 
	 * @param bnkCodeBegin
	 */
	public void setBnkCodeBegin(String bnkCodeBegin) {
		this.bnkCodeBegin = bnkCodeBegin;
	}

	/**
	 * bnkCodeEnd取得
	 * 
	 * @return bnkCodeEnd
	 */
	public String getBnkCodeEnd() {
		return bnkCodeEnd;
	}

	/**
	 * bnkCodeEnd設定
	 * 
	 * @param bnkCodeEnd
	 */
	public void setBnkCodeEnd(String bnkCodeEnd) {
		this.bnkCodeEnd = bnkCodeEnd;
	}

	/**
	 * 支店コード取得
	 * 
	 * @return 支店コード
	 */
	public String getBnkStnCode() {
		return bnkStnCode;
	}

	/**
	 * 支店コード設定
	 * 
	 * @param bnkStnCode 支店コード
	 */
	public void setBnkStnCode(String bnkStnCode) {
		this.bnkStnCode = bnkStnCode;
	}

	/**
	 * bnkStnCodeBegin取得
	 * 
	 * @return bnkStnCodeBegin
	 */
	public String getBnkStnCodeBegin() {
		return bnkStnCodeBegin;
	}

	/**
	 * bnkStnCodeBegin設定
	 * 
	 * @param bnkStnCodeBegin
	 */
	public void setBnkStnCodeBegin(String bnkStnCodeBegin) {
		this.bnkStnCodeBegin = bnkStnCodeBegin;
	}

	/**
	 * 支店終了コード取得
	 * 
	 * @return 支店終了コード
	 */
	public String getBnkStnCodeEnd() {
		return bnkStnCodeEnd;
	}

	/**
	 * 支店終了コード設定
	 * 
	 * @param bnkStnCodeEnd 支店終了コード
	 */
	public void setBnkStnCodeEnd(String bnkStnCodeEnd) {
		this.bnkStnCodeEnd = bnkStnCodeEnd;
	}

	/**
	 * likeBnkCode取得
	 * 
	 * @return likeBnkCode
	 */
	public String getLikeBnkCode() {
		return likeBnkCode;
	}

	/**
	 * likeBnkCode設定
	 * 
	 * @param likeBnkCode
	 */
	public void setLikeBnkCode(String likeBnkCode) {
		this.likeBnkCode = likeBnkCode;
	}

	/**
	 * likeBnkName取得
	 * 
	 * @return likeBnkName
	 */
	public String getLikeBnkName() {
		return likeBnkName;
	}

	/**
	 * likeBnkName設定
	 * 
	 * @param likeBnkName
	 */
	public void setLikeBnkName(String likeBnkName) {
		this.likeBnkName = likeBnkName;
	}

	/**
	 * likeBnkNameK取得
	 * 
	 * @return likeBnkNameK
	 */
	public String getLikeBnkNameK() {
		return likeBnkNameK;
	}

	/**
	 * likeBnkNameK設定
	 * 
	 * @param likeBnkNameK
	 */
	public void setLikeBnkNameK(String likeBnkNameK) {
		this.likeBnkNameK = likeBnkNameK;
	}

	/**
	 * likeBnkStnCode取得
	 * 
	 * @return likeBnkStnCode
	 */
	public String getLikeBnkStnCode() {
		return likeBnkStnCode;
	}

	/**
	 * likeBnkStnCode設定
	 * 
	 * @param likeBnkStnCode
	 */
	public void setLikeBnkStnCode(String likeBnkStnCode) {
		this.likeBnkStnCode = likeBnkStnCode;
	}

	/**
	 * likeBnkStnName取得
	 * 
	 * @return likeBnkStnName
	 */
	public String getLikeBnkStnName() {
		return likeBnkStnName;
	}

	/**
	 * likeBnkStnName設定
	 * 
	 * @param likeBnkStnName
	 */
	public void setLikeBnkStnName(String likeBnkStnName) {
		this.likeBnkStnName = likeBnkStnName;
	}

	/**
	 * likeBnkStnNameK取得
	 * 
	 * @return likeBnkStnNameK
	 */
	public String getLikeBnkStnNameK() {
		return likeBnkStnNameK;
	}

	/**
	 * likeBnkStnNameK設定
	 * 
	 * @param likeBnkStnNameK
	 */
	public void setLikeBnkStnNameK(String likeBnkStnNameK) {
		this.likeBnkStnNameK = likeBnkStnNameK;
	}

}
