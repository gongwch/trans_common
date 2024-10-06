package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 処理対象の伝票
 */
public class SlipDen extends TransferBase {

	/** 会社コード */
	protected String KAI_CODE = null;

	/** 伝票番号 */
	protected String SWK_DEN_NO = null;

	/** 伝票日付 */
	protected Date SWK_DEN_DATE = null;

	/** BEFORE伝票番号 */
	protected String SWK_BEFORE_DEN_NO = null;

	/** 前回更新区分 */
	protected int SWK_BEFORE_UPD_KBN = -1;

	/** データ区分 */
	protected String SWK_DATA_KBN = null;

	/** 更新区分 */
	protected int SWK_UPD_KBN = -1;

	/** 承認者コード */
	protected String SWK_SYO_EMP_CODE = null;

	/** 承認者名称 */
	protected String SWK_SYO_EMP_NAME = null;

	/** 承認者略称 */
	protected String SWK_SYO_EMP_NAME_S = null;

	/** 承認日 */
	protected Date SWK_SYO_DATE = null;

	/** 依頼者 */
	protected String SWK_IRAI_EMP_CODE = null;

	/** 伝票摘要 */
	protected String SWK_TEK = null;

	/** 伝票種別 */
	protected String SWK_DEN_SYU = null;

	/** 通貨コード */
	protected String SWK_CUR_CODE = null;

	/** 修正回数 */
	protected int SWK_UPD_CNT = -1;

	/** 決算区分 */
	protected int SWK_KSN_KBN = -1;

	/** 経理承認フラグ */
	protected boolean ACCT_APRV_FLG = false;

	/** 今の承認者コード */
	protected String APRV_ROLE_CODE = null;

	/** 次の承認者コード */
	protected String NEXT_APRV_ROLE_CODE = null;

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
	 * 伝票番号の取得
	 * 
	 * @return SWK_DEN_NO 伝票番号
	 */
	public String getSWK_DEN_NO() {
		return SWK_DEN_NO;
	}

	/**
	 * 伝票番号の設定
	 * 
	 * @param SWK_DEN_NO 伝票番号
	 */
	public void setSWK_DEN_NO(String SWK_DEN_NO) {
		this.SWK_DEN_NO = SWK_DEN_NO;
	}

	/**
	 * 伝票日付の取得
	 * 
	 * @return SWK_DEN_DATE 伝票日付
	 */
	public Date getSWK_DEN_DATE() {
		return SWK_DEN_DATE;
	}

	/**
	 * 伝票日付の設定
	 * 
	 * @param SWK_DEN_DATE 伝票日付
	 */
	public void setSWK_DEN_DATE(Date SWK_DEN_DATE) {
		this.SWK_DEN_DATE = SWK_DEN_DATE;
	}

	/**
	 * BEFORE伝票番号の取得
	 * 
	 * @return SWK_BEFORE_DEN_NO BEFORE伝票番号
	 */
	public String getSWK_BEFORE_DEN_NO() {
		return SWK_BEFORE_DEN_NO;
	}

	/**
	 * BEFORE伝票番号の設定
	 * 
	 * @param SWK_BEFORE_DEN_NO BEFORE伝票番号
	 */
	public void setSWK_BEFORE_DEN_NO(String SWK_BEFORE_DEN_NO) {
		this.SWK_BEFORE_DEN_NO = SWK_BEFORE_DEN_NO;
	}

	/**
	 * 前回更新区分の取得
	 * 
	 * @return SWK_BEFORE_UPD_KBN 前回更新区分
	 */
	public int getSWK_BEFORE_UPD_KBN() {
		return SWK_BEFORE_UPD_KBN;
	}

	/**
	 * 前回更新区分の設定
	 * 
	 * @param SWK_BEFORE_UPD_KBN 前回更新区分
	 */
	public void setSWK_BEFORE_UPD_KBN(int SWK_BEFORE_UPD_KBN) {
		this.SWK_BEFORE_UPD_KBN = SWK_BEFORE_UPD_KBN;
	}

	/**
	 * データ区分の取得
	 * 
	 * @return SWK_DATA_KBN データ区分
	 */
	public String getSWK_DATA_KBN() {
		return SWK_DATA_KBN;
	}

	/**
	 * データ区分の設定
	 * 
	 * @param SWK_DATA_KBN データ区分
	 */
	public void setSWK_DATA_KBN(String SWK_DATA_KBN) {
		this.SWK_DATA_KBN = SWK_DATA_KBN;
	}

	/**
	 * 更新区分の取得
	 * 
	 * @return SWK_UPD_KBN 更新区分
	 */
	public int getSWK_UPD_KBN() {
		return SWK_UPD_KBN;
	}

	/**
	 * 更新区分の設定
	 * 
	 * @param SWK_UPD_KBN 更新区分
	 */
	public void setSWK_UPD_KBN(int SWK_UPD_KBN) {
		this.SWK_UPD_KBN = SWK_UPD_KBN;
	}

	/**
	 * 承認者コードの取得
	 * 
	 * @return SWK_SYO_EMP_CODE 承認者コード
	 */
	public String getSWK_SYO_EMP_CODE() {
		return SWK_SYO_EMP_CODE;
	}

	/**
	 * 承認者コードの設定
	 * 
	 * @param SWK_SYO_EMP_CODE 承認者コード
	 */
	public void setSWK_SYO_EMP_CODE(String SWK_SYO_EMP_CODE) {
		this.SWK_SYO_EMP_CODE = SWK_SYO_EMP_CODE;
	}

	/**
	 * 承認者名称の取得
	 * 
	 * @return SWK_SYO_EMP_NAME 承認者名称
	 */
	public String getSWK_SYO_EMP_NAME() {
		return SWK_SYO_EMP_NAME;
	}

	/**
	 * 承認者名称の設定
	 * 
	 * @param SWK_SYO_EMP_NAME 承認者名称
	 */
	public void setSWK_SYO_EMP_NAME(String SWK_SYO_EMP_NAME) {
		this.SWK_SYO_EMP_NAME = SWK_SYO_EMP_NAME;
	}

	/**
	 * 承認者略称の取得
	 * 
	 * @return SWK_SYO_EMP_NAME_S 承認者略称
	 */
	public String getSWK_SYO_EMP_NAME_S() {
		return SWK_SYO_EMP_NAME_S;
	}

	/**
	 * 承認者略称の設定
	 * 
	 * @param SWK_SYO_EMP_NAME_S 承認者略称
	 */
	public void setSWK_SYO_EMP_NAME_S(String SWK_SYO_EMP_NAME_S) {
		this.SWK_SYO_EMP_NAME_S = SWK_SYO_EMP_NAME_S;
	}

	/**
	 * 承認日の取得
	 * 
	 * @return SWK_SYO_DATE 承認日
	 */
	public Date getSWK_SYO_DATE() {
		return SWK_SYO_DATE;
	}

	/**
	 * 承認日の設定
	 * 
	 * @param SWK_SYO_DATE 承認日
	 */
	public void setSWK_SYO_DATE(Date SWK_SYO_DATE) {
		this.SWK_SYO_DATE = SWK_SYO_DATE;
	}

	/**
	 * 依頼者の取得
	 * 
	 * @return SWK_IRAI_EMP_CODE 依頼者
	 */
	public String getSWK_IRAI_EMP_CODE() {
		return SWK_IRAI_EMP_CODE;
	}

	/**
	 * 依頼者の設定
	 * 
	 * @param SWK_IRAI_EMP_CODE 依頼者
	 */
	public void setSWK_IRAI_EMP_CODE(String SWK_IRAI_EMP_CODE) {
		this.SWK_IRAI_EMP_CODE = SWK_IRAI_EMP_CODE;
	}

	/**
	 * 伝票摘要の取得
	 * 
	 * @return SWK_TEK 伝票摘要
	 */
	public String getSWK_TEK() {
		return SWK_TEK;
	}

	/**
	 * 伝票摘要の設定
	 * 
	 * @param SWK_TEK 伝票摘要
	 */
	public void setSWK_TEK(String SWK_TEK) {
		this.SWK_TEK = SWK_TEK;
	}

	/**
	 * 伝票種別の取得
	 * 
	 * @return SWK_DEN_SYU 伝票種別
	 */
	public String getSWK_DEN_SYU() {
		return SWK_DEN_SYU;
	}

	/**
	 * 伝票種別の設定
	 * 
	 * @param SWK_DEN_SYU 伝票種別
	 */
	public void setSWK_DEN_SYU(String SWK_DEN_SYU) {
		this.SWK_DEN_SYU = SWK_DEN_SYU;
	}

	/**
	 * 通貨コードの取得
	 * 
	 * @return SWK_CUR_CODE 通貨コード
	 */
	public String getSWK_CUR_CODE() {
		return SWK_CUR_CODE;
	}

	/**
	 * 通貨コードの設定
	 * 
	 * @param SWK_CUR_CODE 通貨コード
	 */
	public void setSWK_CUR_CODE(String SWK_CUR_CODE) {
		this.SWK_CUR_CODE = SWK_CUR_CODE;
	}

	/**
	 * 修正回数の取得
	 * 
	 * @return SWK_UPD_CNT 修正回数
	 */
	public int getSWK_UPD_CNT() {
		return SWK_UPD_CNT;
	}

	/**
	 * 修正回数の設定
	 * 
	 * @param SWK_UPD_CNT 修正回数
	 */
	public void setSWK_UPD_CNT(int SWK_UPD_CNT) {
		this.SWK_UPD_CNT = SWK_UPD_CNT;
	}

	/**
	 * 決算区分の取得
	 * 
	 * @return SWK_KSN_KBN 決算区分
	 */
	public int getSWK_KSN_KBN() {
		return SWK_KSN_KBN;
	}

	/**
	 * 決算区分の設定
	 * 
	 * @param SWK_KSN_KBN 決算区分
	 */
	public void setSWK_KSN_KBN(int SWK_KSN_KBN) {
		this.SWK_KSN_KBN = SWK_KSN_KBN;
	}

	/**
	 * 経理承認フラグの取得
	 * 
	 * @return ACCT_APRV_FLG 経理承認フラグ
	 */
	public boolean isACCT_APRV_FLG() {
		return ACCT_APRV_FLG;
	}

	/**
	 * 経理承認フラグの設定
	 * 
	 * @param ACCT_APRV_FLG 経理承認フラグ
	 */
	public void setACCT_APRV_FLG(boolean ACCT_APRV_FLG) {
		this.ACCT_APRV_FLG = ACCT_APRV_FLG;
	}

	/**
	 * 今の承認者コードの取得
	 * 
	 * @return APRV_ROLE_CODE 今の承認者コード
	 */
	public String getAPRV_ROLE_CODE() {
		return APRV_ROLE_CODE;
	}

	/**
	 * 今の承認者コードの設定
	 * 
	 * @param APRV_ROLE_CODE 今の承認者コード
	 */
	public void setAPRV_ROLE_CODE(String APRV_ROLE_CODE) {
		this.APRV_ROLE_CODE = APRV_ROLE_CODE;
	}

	/**
	 * 次の承認者コードの取得
	 * 
	 * @return NEXT_APRV_ROLE_CODE 次の承認者コード
	 */
	public String getNEXT_APRV_ROLE_CODE() {
		return NEXT_APRV_ROLE_CODE;
	}

	/**
	 * 次の承認者コードの設定
	 * 
	 * @param NEXT_APRV_ROLE_CODE 次の承認者コード
	 */
	public void setNEXT_APRV_ROLE_CODE(String NEXT_APRV_ROLE_CODE) {
		this.NEXT_APRV_ROLE_CODE = NEXT_APRV_ROLE_CODE;
	}

}
