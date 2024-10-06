package jp.co.ais.trans2.model.remittance;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 送金目的情報
 */
public class Remittance extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** 送金目的コード */
	protected String code = null;

	/** 送金目的名カナ */
	protected String name = null;

	/** 国際収支コード */
	protected String balanceCode = null;

	/** 国際収支名称 */
	protected String balanceName = null;

	/** 登録日付 */
	protected Date inpDate;

	/** 更新日付 */
	protected Date updDate;

	/**
	 * 会社コードを戻す
	 * 
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * 会社コードを設定する
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 送金目的コードを戻す
	 * 
	 * @return code 送金目的コード
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 送金目的コードを設定する
	 * 
	 * @param code 送金目的コード
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 送金目的名カナを戻す
	 * 
	 * @return kana 送金目的名カナ
	 */
	public String getName() {
		return name;
	}

	/**
	 * 送金目的名カナを設定する
	 * 
	 * @param kana 送金目的名カナ
	 */
	public void setName(String kana) {
		this.name = kana;
	}

	/**
	 * 国際収支コードを戻す
	 * 
	 * @return balanceCode
	 */
	public String getBalanceCode() {
		return this.balanceCode;
	}

	/**
	 * 国際収支コードを設定する
	 * 
	 * @param balanceCode
	 */
	public void setBalanceCode(String balanceCode) {
		this.balanceCode = balanceCode;
	}

	/**
	 * 国際収支名称を戻す
	 * 
	 * @return balanceName
	 */
	public String getBalanceName() {
		return this.balanceName;
	}

	/**
	 * 国際収支名称を設定する
	 * 
	 * @param balanceName
	 */
	public void setBalanceName(String balanceName) {
		this.balanceName = balanceName;
	}

	/**
	 * 登録日付の取得
	 * 
	 * @return inpDate
	 */
	public Date getInpDate() {
		return inpDate;
	}

	/**
	 * 登録日付を設定する
	 * 
	 * @param inpDate
	 */
	public void setInpDate(Date inpDate) {
		this.inpDate = inpDate;
	}

	/**
	 * 更新日付の取得
	 * 
	 * @return updDate
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * 更新日付を設定する
	 * 
	 * @param updDate
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

}
