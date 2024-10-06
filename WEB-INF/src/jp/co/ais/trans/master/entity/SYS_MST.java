package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * システム区分マスタ
 */
public class SYS_MST extends MasterBase {

	/** システム区分Bean */
	public static final String TABLE = "SYS_MST";

	/** 債務 */
	public static final String AP = "AP";

	/** 債務仕訳 */
	public static final int AP_HDR = 1;

	/** 債務仕訳パタン */
	public static final int AP_PTN = 2;

	/** 基本会計 */
	public static final String GL = "GL";

	/** 基本会計仕訳 */
	public static final int GL_HDR = 3;

	/** 基本会計仕訳パタン */
	public static final int GL_PTN = 4;

	/** 債権 */
	public static final String AR = "AR";

	/** 債権仕訳 */
	public static final int AR_HDR = 5;

	/** 債権仕訳パタン */
	public static final int AR_PTN = 6;

	/** 借入金 */
	public static final String LM = "LM";

	/** 借入金ヘッダ */
	public static final int LM_HDR = 7;

	/** 会社コード */
	protected String kAI_CODE = null;

	/** システム区分 */
	protected String sYS_KBN = null;

	/** システム区分名称 */
	protected String sYS_KBN_NAME = null;

	/** システム区分略称 */
	protected String sYS_KBN_NAME_S = null;

	/** システム区分検索名称 */
	protected String sYS_KBN_NAME_K = null;

	/** 外部システム区分 */
	protected String oSY_KBN = null;

	/** プログラムＩＤ */
	protected String pRG_ID = null;

	/** カラー */
	protected String sYS_COLOR = null;

	/**
	 * 会社コードの取得
	 * 
	 * @return kAI_CODE 会社コード
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param kAI_CODE 会社コード
	 */
	public void setKAI_CODE(String kAI_CODE) {
		this.kAI_CODE = kAI_CODE;
	}

	/**
	 * システム区分の取得
	 * 
	 * @return sYS_KBN システム区分
	 */
	public String getSYS_KBN() {
		return sYS_KBN;
	}

	/**
	 * システム区分の設定
	 * 
	 * @param sYS_KBN システム区分
	 */
	public void setSYS_KBN(String sYS_KBN) {
		this.sYS_KBN = sYS_KBN;
	}

	/**
	 * システム区分名称の取得
	 * 
	 * @return sYS_KBN_NAME システム区分名称
	 */
	public String getSYS_KBN_NAME() {
		return sYS_KBN_NAME;
	}

	/**
	 * システム区分名称の設定
	 * 
	 * @param sYS_KBN_NAME システム区分名称
	 */
	public void setSYS_KBN_NAME(String sYS_KBN_NAME) {
		this.sYS_KBN_NAME = sYS_KBN_NAME;
	}

	/**
	 * システム区分略称の取得
	 * 
	 * @return sYS_KBN_NAME_S システム区分略称
	 */
	public String getSYS_KBN_NAME_S() {
		return sYS_KBN_NAME_S;
	}

	/**
	 * システム区分略称の設定
	 * 
	 * @param sYS_KBN_NAME_S システム区分略称
	 */
	public void setSYS_KBN_NAME_S(String sYS_KBN_NAME_S) {
		this.sYS_KBN_NAME_S = sYS_KBN_NAME_S;
	}

	/**
	 * システム区分検索名称の取得
	 * 
	 * @return sYS_KBN_NAME_K システム区分検索名称
	 */
	public String getSYS_KBN_NAME_K() {
		return sYS_KBN_NAME_K;
	}

	/**
	 * システム区分検索名称の設定
	 * 
	 * @param sYS_KBN_NAME_K システム区分検索名称
	 */
	public void setSYS_KBN_NAME_K(String sYS_KBN_NAME_K) {
		this.sYS_KBN_NAME_K = sYS_KBN_NAME_K;
	}

	/**
	 * 外部システム区分の取得
	 * 
	 * @return oSY_KBN 外部システム区分
	 */
	public String getOSY_KBN() {
		return oSY_KBN;
	}

	/**
	 * 外部システム区分の設定
	 * 
	 * @param oSY_KBN 外部システム区分
	 */
	public void setOSY_KBN(String oSY_KBN) {
		this.oSY_KBN = oSY_KBN;
	}

	/**
	 * プログラムＩＤの取得
	 * 
	 * @return pRG_ID プログラムＩＤ
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * プログラムＩＤの設定
	 * 
	 * @param pRG_ID プログラムＩＤ
	 */
	public void setPRG_ID(String pRG_ID) {
		this.pRG_ID = pRG_ID;
	}

	/**
	 * カラーの取得
	 * 
	 * @return sYS_COLOR カラー
	 */
	public String getSYS_COLOR() {
		return sYS_COLOR;
	}

	/**
	 * カラーの設定
	 * 
	 * @param sYS_COLOR カラー
	 */
	public void setSYS_COLOR(String sYS_COLOR) {
		this.sYS_COLOR = sYS_COLOR;
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/sYS_KBN=").append(sYS_KBN);
		buff.append("/sYS_KBN_NAME=").append(sYS_KBN_NAME);
		buff.append("/sYS_KBN_NAME_S=").append(sYS_KBN_NAME_S);
		buff.append("/sYS_KBN_NAME_K=").append(sYS_KBN_NAME_K);
		buff.append("/oSY_KBN=").append(oSY_KBN);
		buff.append("/iNP_DATE=").append(iNP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("/sYS_COLOR=").append(sYS_COLOR);
		buff.append("]");
		return buff.toString();
	}

	@Override
	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(sYS_KBN);
		list.add(sYS_KBN_NAME);
		list.add(sYS_KBN_NAME_S);
		list.add(sYS_KBN_NAME_K);
		list.add(oSY_KBN);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);
		list.add(sYS_COLOR);

		return list;
	}
}
