package jp.co.ais.trans2.model.close;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 月次更新情報
 * 
 * @author TRANS(D) YF.CONG
 */
public class MonthData extends TransferBase {

	/** 会社コード */
	protected String companyCode;

	/** 会社情報 */
	protected Company company = null;

	/** 登録 */
	protected int entry = 0;

	/** 現場否認 */
	protected int fieldDeny = 0;

	/** 現場承認 */
	protected int fieldApprove = 0;

	/** 経理否認 */
	protected int deny = 0;

	/** 経理承認 */
	protected int approve = 0;

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
	 * 会社情報の取得
	 * 
	 * @return company 会社情報
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * 会社情報の設定
	 * 
	 * @param company 会社情報
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * 登録の取得
	 * 
	 * @return entry 登録
	 */
	public int getEntry() {
		return entry;
	}

	/**
	 * 登録の設定
	 * 
	 * @param entry 登録
	 */
	public void setEntry(int entry) {
		this.entry = entry;
	}

	/**
	 * 現場否認の取得
	 * 
	 * @return fieldDeny 現場否認
	 */
	public int getFieldDeny() {
		return fieldDeny;
	}

	/**
	 * 現場否認の設定
	 * 
	 * @param fieldDeny 現場否認
	 */
	public void setFieldDeny(int fieldDeny) {
		this.fieldDeny = fieldDeny;
	}

	/**
	 * 現場承認の取得
	 * 
	 * @return fieldApprove 現場承認
	 */
	public int getFieldApprove() {
		return fieldApprove;
	}

	/**
	 * 現場承認の設定
	 * 
	 * @param fieldApprove 現場承認
	 */
	public void setFieldApprove(int fieldApprove) {
		this.fieldApprove = fieldApprove;
	}

	/**
	 * 経理否認の取得
	 * 
	 * @return deny 経理否認
	 */
	public int getDeny() {
		return deny;
	}

	/**
	 * 経理否認の設定
	 * 
	 * @param deny 経理否認
	 */
	public void setDeny(int deny) {
		this.deny = deny;
	}

	/**
	 * 経理承認の取得
	 * 
	 * @return approve 経理承認
	 */
	public int getApprove() {
		return approve;
	}

	/**
	 * 経理承認の設定
	 * 
	 * @param approve 経理承認
	 */
	public void setApprove(int approve) {
		this.approve = approve;
	}

}
