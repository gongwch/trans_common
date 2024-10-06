package jp.co.ais.trans2.model.user;

import jp.co.ais.trans.common.dt.*;

/**
 * ダッシュボード権限制御 USR_DASH_CTL
 * 
 * @author AIS
 */
public class USR_DASH_CTL extends TransferBase {

	/** 会社コード */
	protected String KAI_CODE = null;

	/** ユーザーコード */
	protected String USR_CODE = null;

	/** システム区分 */
	protected String SYS_KBN_CODE = null;

	/** システム区分名称 */
	protected String SYS_KBN_NAME = null;

	/** 0:使用不可false、1:使用可true */
	protected boolean USE_KBN = false;

	/**
	 * 会社コードの取得
	 * 
	 * @return KAI_CODE 会社コード
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param KAI_CODE 会社コード
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * /** ユーザーコードの取得
	 * 
	 * @return USER_CODE ユーザーコード
	 */
	public String getUSR_CODE() {
		return USR_CODE;
	}

	/**
	 * ユーザーコードの設定
	 * 
	 * @param USR_CODE ユーザーコード
	 */
	public void setUSR_CODE(String USR_CODE) {
		this.USR_CODE = USR_CODE;
	}

	/**
	 * システム区分の取得
	 * 
	 * @return SYS_KBN_CODE システム区分
	 */
	public String getSYS_KBN_CODE() {
		return SYS_KBN_CODE;
	}

	/**
	 * システム区分の設定
	 * 
	 * @param SYS_KBN_CODE システム区分
	 */
	public void setSYS_KBN_CODE(String SYS_KBN_CODE) {
		this.SYS_KBN_CODE = SYS_KBN_CODE;
	}

	/**
	 * システム区分名称の取得
	 * 
	 * @return SYS_KBN_NAME システム区分名称
	 */
	public String getSYS_KBN_NAME() {
		return SYS_KBN_NAME;
	}

	/**
	 * システム区分名称の設定
	 * 
	 * @param SYS_KBN_NAME システム区分名称
	 */
	public void setSYS_KBN_NAME(String SYS_KBN_NAME) {
		this.SYS_KBN_NAME = SYS_KBN_NAME;
	}

	/**
	 * 0:使用不可false、1:使用可trueの取得
	 * 
	 * @return USE_KBN 0:使用不可false、1:使用可true
	 */
	public boolean isUSE_KBN() {
		return USE_KBN;
	}

	/**
	 * 0:使用不可false、1:使用可trueの設定
	 * 
	 * @param USE_KBN 0:使用不可false、1:使用可true
	 */
	public void setUSE_KBN(boolean USE_KBN) {
		this.USE_KBN = USE_KBN;
	}

}
