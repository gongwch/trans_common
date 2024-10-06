package jp.co.ais.trans2.model.bank;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 銀行統廃合情報
 */
public class BankMerge extends TransferBase {

	/** 旧銀行コード */
	protected String oldBankCode;

	/** 旧銀行支店コード */
	protected String oldBankOffCode;

	/** 旧銀行名 */
	protected String oldBankName;

	/** 新銀行コード */
	protected String newBankCode;

	/** 新銀行名 */
	protected String newBankName;

	/** 新銀行カナ(FB) */
	protected String newBankKanaFb;

	/** 新銀行カナ */
	protected String newBankKana;

	/** 新銀行支店コード */
	protected String newBankOffCode;

	/** 新銀行支店名 */
	protected String newBankOffName;

	/** 新銀行支店カナ(FB) */
	protected String newBankOffKanaFb;

	/** 新銀行支店カナ */
	protected String newBankOffKana;

	/** 開始年月日 */
	protected Date dateFrom;

	/** 終了年月日 */
	protected Date dateTo;

	/** 更新数 */
	protected int renew;

	/** 追加数 */
	protected int add;


	/**
	 * @return 旧銀行コード
	 */
	public String getOldBankCode() {
		return oldBankCode;
	}

	/**
	 * @param oldBankCode 旧銀行コード
	 */
	public void setOldBankCode(String oldBankCode) {
		this.oldBankCode = oldBankCode;
	}

	/**
	 * @return 旧銀行支店コード
	 */
	public String getOldBankOffCode() {
		return oldBankOffCode;
	}

	/**
	 * @param oldBankOffCode 旧銀行支店コード
	 */
	public void setOldBankOffCode(String oldBankOffCode) {
		this.oldBankOffCode = oldBankOffCode;
	}

	/**
	 * @return 旧銀行名
	 */
	public String getOldBankName() {
		return oldBankName;
	}

	/**
	 * @param oldBankName 旧銀行名
	 */
	public void setOldBankName(String oldBankName) {
		this.oldBankName = oldBankName;
	}

	/**
	 * @return 新銀行コード
	 */
	public String getNewBankCode() {
		return newBankCode;
	}

	/**
	 * @param newBankCode 新銀行コード
	 */
	public void setNewBankCode(String newBankCode) {
		this.newBankCode = newBankCode;
	}

	/**
	 * @return 新銀行名
	 */
	public String getNewBankName() {
		return newBankName;
	}

	/**
	 * @param newBankName 新銀行名
	 */
	public void setNewBankName(String newBankName) {
		this.newBankName = newBankName;
	}

	/**
	 * @return 新銀行カナ(FB)
	 */
	public String getNewBankKanaFb() {
		return newBankKanaFb;
	}

	/**
	 * @param newBankKanaFb 新銀行カナ(FB)
	 */
	public void setNewBankKanaFb(String newBankKanaFb) {
		this.newBankKanaFb = newBankKanaFb;
	}

	/**
	 * @return 新銀行カナ
	 */
	public String getNewBankKana() {
		return newBankKana;
	}

	/**
	 * @param newBankKana 新銀行カナ
	 */
	public void setNewBankKana(String newBankKana) {
		this.newBankKana = newBankKana;
	}

	/**
	 * @return 新銀行支店コード
	 */
	public String getNewBankOffCode() {
		return newBankOffCode;
	}

	/**
	 * @param newBankOffCode 新銀行支店コード
	 */
	public void setNewBankOffCode(String newBankOffCode) {
		this.newBankOffCode = newBankOffCode;
	}

	/**
	 * @return 新銀行支店名
	 */
	public String getNewBankOffName() {
		return newBankOffName;
	}

	/**
	 * @param newBankOffName 新銀行支店名
	 */
	public void setNewBankOffName(String newBankOffName) {
		this.newBankOffName = newBankOffName;
	}

	/**
	 * @return 新銀行支店カナ(FB)
	 */
	public String getNewBankOffKanaFb() {
		return newBankOffKanaFb;
	}

	/**
	 * @param newBankOffKanaFb 新銀行支店カナ(FB)
	 */
	public void setNewBankOffKanaFb(String newBankOffKanaFb) {
		this.newBankOffKanaFb = newBankOffKanaFb;
	}

	/**
	 * @return 新銀行支店カナ
	 */
	public String getNewBankOffKana() {
		return newBankOffKana;
	}

	/**
	 * @param newBankOffKana 新銀行支店カナ
	 */
	public void setNewBankOffKana(String newBankOffKana) {
		this.newBankOffKana = newBankOffKana;
	}

	/**
	 * @return 開始年月日
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom 開始年月日
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return 終了年月日
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo 終了年月日
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	
	/**
	 * @return renew
	 */
	public int getRenew() {
		return renew;
	}

	/**
	 * @param renew
	 */
	public void setRenew(int renew) {
		this.renew = renew;
	}

	/**
	 * @return add
	 */
	public int getAdd() {
		return add;
	}

	/**
	 * @param add
	 */
	public void setAdd(int add) {
		this.add = add;
	}


}
