package jp.co.ais.trans2.model.passwordupdate;

import jp.co.ais.trans.common.dt.*;

/**
 * MG0025 パスワード変更
 * 
 * @author AIS
 */
public class PasswordUpdate extends TransferBase {

	/** 新しいパスワード */
	protected String newPassword = null;

	/** 新しいパスワード(確認) */
	protected String confNewPassword = null;

	/**
	 * 新しいパスワードの取得
	 * 
	 * @return newPasswoer 新しいパスワード
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * 新しいパスワードの設定
	 * 
	 * @param newPassword 新しいパスワード
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * 新しいパスワード(確認)の取得
	 * 
	 * @return ｃonfNewPassword 新しいパスワード
	 */
	public String getConfNewPassword() {
		return confNewPassword;
	}

	/**
	 * 新しいパスワード(確認)の設定
	 * 
	 * @param confNewPassword 新しいパスワード
	 */
	public void setConfNewPassword(String confNewPassword) {
		this.confNewPassword = confNewPassword;
	}

}
