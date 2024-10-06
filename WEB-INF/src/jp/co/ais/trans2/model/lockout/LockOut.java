package jp.co.ais.trans2.model.lockout;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * MG0027 ロックアウト管理
 * 
 * @author AIS
 */
public class LockOut extends TransferBase {

	/** 会社コード */
	protected String companyCode;

	/** ユーザーコード */
	protected String userCode = null;

	/** ユーザー略称 */
	protected String userNames = null;

	/** ログイン失敗日時 */
	protected Date logFailure = null;

	/**
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * ユーザーコードの取得
	 * 
	 * @return userCode ユーザーコード
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * ユーザーコードの設定
	 * 
	 * @param userCode ユーザーコード
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * ユーザー略称の取得
	 * 
	 * @return userNames ユーザー略称
	 */
	public String getUserNames() {
		return userNames;
	}

	/**
	 * ユーザー略称の設定
	 * 
	 * @param userNames ユーザー略称
	 */
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	/**
	 * ログイン失敗日時の取得
	 * 
	 * @return logFailure ログイン失敗日時
	 */
	public Date getLogFailure() {
		return logFailure;
	}

	/**
	 * ログイン失敗日時の設定
	 * 
	 * @param logFailure ログイン失敗日時
	 */
	public void setLogFailure(Date logFailure) {
		this.logFailure = logFailure;
	}

}
