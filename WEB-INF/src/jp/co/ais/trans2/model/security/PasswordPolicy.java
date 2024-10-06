package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.dt.TransferBase;

/**
 * パスワードポリシー
 * @author AIS
 *
 */
public class PasswordPolicy extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** パスワードロックアウト到達までの失敗許容回数。 0の場合、ロックアウトしない。 */
	protected int lockOutAllowedCount = 0; 

	/** ロックアウト後、ロック解除までの時間(分)。  0の場合、ロック解除しない。 */
	protected int lockOutReleaseTime = 0;

	/** 最低パスワード長 */
	protected int passwordMinLength = 0;

	/** パスワードの有効期限を日数で指定。 0の場合、有効期限なし。 */
	protected int passwordTerm = 0;

	/**
	 * 有効範囲：1～4。
	 * パスワードに英大文字、英小文字、数字、記号の文字種のうち、何種類を含まなければいけないかを指定する。
	 */
	protected int complicateLevel = 0;

	/**
	 * パスワード履歴マスタに保持するパスワード履歴数を指定する。
	 * 保持されているパスワードは新規設定に利用不可とする。
	 */
	protected int historyMaxCount = 0;

	/**パスワード有効期限の何日前に期限切れメッセージ通知を行うか。0の場合、通知しない */
	protected int daysNoticePasswordTermComeThrough = 0;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public int getComplicateLevel() {
		return complicateLevel;
	}

	public void setComplicateLevel(int complicateLevel) {
		this.complicateLevel = complicateLevel;
	}

	public int getDaysNoticePasswordTermComeThrough() {
		return daysNoticePasswordTermComeThrough;
	}

	public void setDaysNoticePasswordTermComeThrough(
			int daysNoticePasswordTermComeThrough) {
		this.daysNoticePasswordTermComeThrough = daysNoticePasswordTermComeThrough;
	}

	public int getHistoryMaxCount() {
		return historyMaxCount;
	}

	public void setHistoryMaxCount(int historyMaxCount) {
		this.historyMaxCount = historyMaxCount;
	}

	public int getLockOutAllowedCount() {
		return lockOutAllowedCount;
	}

	public void setLockOutAllowedCount(int lockOutAllowedCount) {
		this.lockOutAllowedCount = lockOutAllowedCount;
	}

	public int getLockOutReleaseTime() {
		return lockOutReleaseTime;
	}

	public void setLockOutReleaseTime(int lockOutReleaseTime) {
		this.lockOutReleaseTime = lockOutReleaseTime;
	}

	public int getPasswordMinLength() {
		return passwordMinLength;
	}

	public void setPasswordMinLength(int passwordMinLength) {
		this.passwordMinLength = passwordMinLength;
	}

	public int getPasswordTerm() {
		return passwordTerm;
	}

	public void setPasswordTerm(int passwordTerm) {
		this.passwordTerm = passwordTerm;
	}

}
