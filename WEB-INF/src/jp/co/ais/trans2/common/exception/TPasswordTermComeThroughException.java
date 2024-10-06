package jp.co.ais.trans2.common.exception;

/**
 * パスワードの有効期間切れException
 * 
 * @author AIS
 */
public class TPasswordTermComeThroughException extends TWarningException {

	/** システム管理者かどうか */
	protected boolean admin = false;

	/** 有効期間切れまでの日数 */
	protected int daysBeforePasswordTermComeThrough = 0;

	/**
	 * システム管理者かどうかの取得
	 * 
	 * @return admin システム管理者かどうか
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * システム管理者かどうかの設定
	 * 
	 * @param admin システム管理者かどうか
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * @return int
	 */
	public int getDaysBeforePasswordTermComeThrough() {
		return daysBeforePasswordTermComeThrough;
	}

	/**
	 * @param daysBeforePasswordTermComeThrough
	 */
	public void setDaysBeforePasswordTermComeThrough(int daysBeforePasswordTermComeThrough) {
		this.daysBeforePasswordTermComeThrough = daysBeforePasswordTermComeThrough;
	}

}
