package jp.co.ais.trans.common.server.servlet;

/**
 * ログイン証明
 */
public final class LoginCertification {

	/** UID */
	private static final long serialVersionUID = 1L;

	/** 認証状態 true:認証 false:認証されていない */
	private boolean certify = false;

	/** 会社コード */
	private String companyCode;

	/** ユーザコード */
	private String userCode;

	/**
	 * コンストラクタ
	 * 
	 * @param compCode 会社マスタ
	 * @param userCode ユーザマスタ
	 */
	public LoginCertification(String compCode, String userCode) {
		this.certify = true;

		this.companyCode = compCode;
		this.userCode = userCode;
	}

	/**
	 * 認証状態参照.
	 * 
	 * @return true:承認状態
	 */
	public boolean isCertified() {
		return this.certify;
	}

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ユーザコード
	 * 
	 * @return ユーザコード
	 */
	public String getUserCode() {
		return userCode;
	}
}
