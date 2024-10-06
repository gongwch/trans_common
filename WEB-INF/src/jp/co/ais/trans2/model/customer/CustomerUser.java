package jp.co.ais.trans2.model.customer;

import jp.co.ais.trans.common.dt.*;

/**
 * 会社情報
 * 
 * @author AIS
 */
public class CustomerUser extends TransferBase {

	/** 会社コード */
	protected String KAI_CODE = null;

	/** 取引先コード */
	protected String TRI_CODE = null;

	/** 取引先名称 */
	protected String TRI_NAME = null;

	/** システム区分 */
	protected String SYS_KBN = null;

	/** 担当者名称 */
	protected String USR_NAME = null;

	/** 部署名称 */
	protected String DEP_NAME = null;

	/** 役職 */
	protected String POSITION = null;

	/**
	 * @return KAI_CODEを戻します。
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * @param KAI_CODE KAI_CODEを設定します。
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * @return TRI_CODEを戻します。
	 */
	public String getTRI_CODE() {
		return TRI_CODE;
	}

	/**
	 * @param TRI_CODE TRI_CODEを設定します。
	 */
	public void setTRI_CODE(String TRI_CODE) {
		this.TRI_CODE = TRI_CODE;
	}

	/**
	 * @return TRI_NAMEを戻します。
	 */
	public String getTRI_NAME() {
		return TRI_NAME;
	}

	/**
	 * @param TRI_NAME TRI_NAMEを設定します。
	 */
	public void setTRI_NAME(String TRI_NAME) {
		this.TRI_NAME = TRI_NAME;
	}

	/**
	 * @return SYS_KBNを戻します。
	 */
	public String getSYS_KBN() {
		return SYS_KBN;
	}

	/**
	 * @param SYS_KBN SYS_KBNを設定します。
	 */
	public void setSYS_KBN(String SYS_KBN) {
		this.SYS_KBN = SYS_KBN;
	}

	/**
	 * @return DEP_NAMEを戻します。
	 */
	public String getDEP_NAME() {
		return DEP_NAME;
	}

	/**
	 * @param DEP_NAME DEP_NAMEを設定します。
	 */
	public void setDEP_NAME(String DEP_NAME) {
		this.DEP_NAME = DEP_NAME;
	}

	/**
	 * @return USR_NAMEを戻します。
	 */
	public String getUSR_NAME() {
		return USR_NAME;
	}

	/**
	 * @param USR_NAME USR_NAMEを設定します。
	 */
	public void setUSR_NAME(String USR_NAME) {
		this.USR_NAME = USR_NAME;
	}

	/**
	 * @return POSITIONを戻します。
	 */
	public String getPOSITION() {
		return POSITION;
	}

	/**
	 * @param POSITION POSITIONを設定します。
	 */
	public void setPOSITION(String POSITION) {
		this.POSITION = POSITION;
	}

}
