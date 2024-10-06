package jp.co.ais.trans2.model.security;

import java.util.Date;

import jp.co.ais.trans.common.dt.TransferBase;

/**
 * パスワード
 * @author AIS
 *
 */
public class Password extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** ユーザーコード */
	protected String userCode = null;

	/** 更新日 */
	protected Date updateDate = null;

	/** パスワード */
	protected String password = null;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}
