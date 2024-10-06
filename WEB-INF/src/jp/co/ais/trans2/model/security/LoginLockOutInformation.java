package jp.co.ais.trans2.model.security;

import java.util.Date;
import jp.co.ais.trans.common.dt.TransferBase;

/**
 * ロックアウト情報
 * @author AIS
 *
 */
public class LoginLockOutInformation extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** ユーザーコード */
	protected String userCode = null;

	/** ログイン失敗回数 */
	protected int failedCount = 0;

	/** 最終ログイン失敗タイムスタンプ */
	protected Date failedTimestamp = null;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}

	public Date getFailedTimestamp() {
		return failedTimestamp;
	}

	public void setFailedTimestamp(Date failedTimestamp) {
		this.failedTimestamp = failedTimestamp;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}
