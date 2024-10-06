package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 伝票排他解除マスタ
 */
public class SlipUnLock extends TransferBase {

	/** 会社コード */
	protected String KAI_CODE = null;

	/** システム区分 */
	protected String SWK_SYS_KBN = null;

	/** システム区分名称 */
	protected String SYS_NAME = null;

	/** 伝票番号 */
	protected String SWK_DEN_NO = null;

	/** 伝票種別 */
	protected String SWK_DEN_SYU = null;

	/** 伝票種別名称 */
	protected String DEN_SYU_NAME = null;

	/** 伝票日付 */
	protected Date SWK_DEN_DATE = null;

	/** 伝票摘要 */
	protected String SWK_TEK = null;

	/** 排他ユーザ */
	protected String USR_ID = null;

	/** 排他ユーザ名称 */
	protected String USR_NAME = null;

	/** 排他実行日時 */
	protected Date UPD_DATE = null;

	/** 排他実行日時 */
	protected int flag = -1;

	/**
	 * @return 会社コードを戻します。
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * @param KAI_CODE 会社コードを設定します。
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * @return システム区分を戻します。
	 */
	public String getSWK_SYS_KBN() {
		return SWK_SYS_KBN;
	}

	/**
	 * @param SWK_SYS_KBN システム区分を設定します。
	 */
	public void setSWK_SYS_KBN(String SWK_SYS_KBN) {
		this.SWK_SYS_KBN = SWK_SYS_KBN;
	}

	/**
	 * @return システム区分名称を戻します。
	 */
	public String getSYS_NAME() {
		return SYS_NAME;
	}

	/**
	 * @param SYS_NAME システム区分名称を設定します。
	 */
	public void setSYS_NAME(String SYS_NAME) {
		this.SYS_NAME = SYS_NAME;
	}

	/**
	 * @return 伝票番号を戻します。
	 */
	public String getSWK_DEN_NO() {
		return SWK_DEN_NO;
	}

	/**
	 * @param SWK_DEN_NO 伝票番号を設定します。
	 */
	public void setSWK_DEN_NO(String SWK_DEN_NO) {
		this.SWK_DEN_NO = SWK_DEN_NO;
	}

	/**
	 * @return 伝票種別を戻します。
	 */
	public String getSWK_DEN_SYU() {
		return SWK_DEN_SYU;
	}

	/**
	 * @param SWK_DEN_SYU 伝票種別を設定します。
	 */
	public void setSWK_DEN_SYU(String SWK_DEN_SYU) {
		this.SWK_DEN_SYU = SWK_DEN_SYU;
	}

	/**
	 * @return 伝票種別名称を戻します。
	 */
	public String getDEN_SYU_NAME() {
		return DEN_SYU_NAME;
	}

	/**
	 * @param DEN_SYU_NAME 伝票種別名称を設定します。
	 */
	public void setDEN_SYU_NAME(String DEN_SYU_NAME) {
		this.DEN_SYU_NAME = DEN_SYU_NAME;
	}

	/**
	 * @return 伝票日付を戻します。
	 */
	public Date getSWK_DEN_DATE() {
		return SWK_DEN_DATE;
	}

	/**
	 * @param SWK_DEN_DATE 伝票日付を設定します。
	 */
	public void setSWK_DEN_DATE(Date SWK_DEN_DATE) {
		this.SWK_DEN_DATE = SWK_DEN_DATE;
	}

	/**
	 * @return 仕訳摘要を戻します。
	 */
	public String getSWK_TEK() {
		return SWK_TEK;
	}

	/**
	 * @param SWK_TEK 仕訳摘要を設定します。
	 */
	public void setSWK_TEK(String SWK_TEK) {
		this.SWK_TEK = SWK_TEK;
	}

	/**
	 * @return 排他ユーザを戻します。
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * @param USR_ID 排他ユーザを設定します。
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * @return 排他ユーザ名称を戻します。
	 */
	public String getUSR_NAME() {
		return USR_NAME;
	}

	/**
	 * @param USR_NAME 排他ユーザ名称を設定します。
	 */
	public void setUSR_NAME(String USR_NAME) {
		this.USR_NAME = USR_NAME;
	}

	/**
	 * 排他実行日付
	 * 
	 * @return 排他実行日付
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * 排他実行日付
	 * 
	 * @param UPD_DATE 排他実行日付を設定します。
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;

	}

	/**
	 * フラグ
	 * 
	 * @return flag
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 * フラグ
	 * 
	 * @param flag を設定します。
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}
}
