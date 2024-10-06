package jp.co.ais.trans.master.entity;

import java.io.*;
import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 
 */
public class SWK_DTL extends MasterBase implements Cloneable, Serializable {

	/**  */
	public static final String TABLE = "SWK_DTL";

	private String kAI_CODE = "";

	private Date sWK_DEN_DATE;

	private String sWK_DEN_NO = "";

	private int sWK_GYO_NO;

	private int sWK_NENDO;

	private int sWK_TUKIDO;

	private String sWK_SEI_NO;

	private int sWK_DC_KBN;

	private String sWK_KMK_CODE = "";

	// 科目
	private String sWK_KMK_NAME = "";

	private String sWK_HKM_CODE;

	// 補助科目
	private String sWK_HKM_NAME = "";

	private String sWK_UKM_CODE;

	// 内訳科目
	private String sWK_UKM_NAME;

	private String sWK_DEP_CODE = "";

	// 計上部門
	private String sWK_DEP_NAME = "";

	private int sWK_ZEI_KBN;

	private BigDecimal sWK_KIN;

	private BigDecimal sWK_ZEI_KIN;

	private String sWK_ZEI_CODE;

	// 消費税名称
	private String sWK_ZEI_NAME;

	private BigDecimal sWK_ZEI_RATE;

	// 借方金額
	private BigDecimal sWK_DR_KIN;

	// 貸方金額
	private BigDecimal sWK_CR_KIN;

	// 借方金額外貨
	private BigDecimal sWK_DR_KIN_G;

	// 貸方金額外貨
	private BigDecimal sWK_CR_KIN_G;

	private String sWK_GYO_TEK_CODE;

	private String sWK_GYO_TEK;

	private String sWK_TRI_CODE;

	// 取引先
	private String SWK_TRI_NAME;

	private String sWK_EMP_CODE;

	// 社員
	private String SWK_EMP_NAME;

	private String sWK_KNR_CODE_1;

	// 管理1
	private String sWK_KNR_NAME_1;

	private String sWK_KNR_CODE_2;

	// 管理2
	private String sWK_KNR_NAME_2;

	private String sWK_KNR_CODE_3;

	// 管理3
	private String sWK_KNR_NAME_3;

	private String sWK_KNR_CODE_4;

	// 管理4
	private String sWK_KNR_NAME_4;

	private String sWK_KNR_CODE_5;

	// 管理5
	private String sWK_KNR_NAME_5;

	private String sWK_KNR_CODE_6;

	// 管理6
	private String sWK_KNR_NAME_6;

	private String sWK_HM_1;

	private String sWK_HM_2;

	private String sWK_HM_3;

	private int sWK_AUTO_KBN;

	private String sWK_DATA_KBN = "";

	private int sWK_UPD_KBN;

	private int sWK_KSN_KBN;

	private String sWK_AT_KMK_CODE;

	private String sWK_AT_HKM_CODE;

	private String sWK_AT_UKM_CODE;

	private String sWK_AT_DEP_CODE;

	private String pRG_ID;

	private String sWK_K_KAI_CODE;

	// 計上会社
	private String sWK_K_KAI_NAME;

	// 小数点桁数
	private int digit = 0;

	private String sWK_CUR_CODE;

	private BigDecimal sWK_CUR_RATE;

	private BigDecimal sWK_IN_KIN;

	private int sWK_TUKE_KBN;

	private BigDecimal sWK_IN_ZEI_KIN;

	private int sWK_KESI_KBN;

	private Date sWK_KESI_DATE;

	private String sWK_KESI_DEN_NO;

	private Date sWK_SOUSAI_DATE;

	private String sWK_SOUSAI_DEN_NO;

	private Date hAS_DATE;

	private String dEN_SYU_CODE = "";

	// APAR区分
	private String sWK_APAR_KBN;

	/** 債権残高 伝票日付 */
	private Date aR_DEN_DATE;

	/** 債権残高 伝票番号 */
	private String aR_DEN_NO;

	/** 債権残高 行番号 */
	private int aR_GYO_NO;

	/** 債権残高.入金予定日 */
	private Date aR_DATE;

	/** 債権残高.請求書番号 */
	private String aR_SEI_NO;

	/** 債務残高 伝票日付 */
	private Date aP_DEN_DATE;

	/** 債務残高 伝票番号 */
	private String aP_DEN_NO;

	/** 債務残高 行番号 */
	private int aP_GYO_NO;

	/**
	 * @return String
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * @param kAI_CODE
	 */
	public void setKAI_CODE(String kAI_CODE) {
		this.kAI_CODE = kAI_CODE;
	}

	/**
	 * @return Date
	 */
	public Date getSWK_DEN_DATE() {
		return sWK_DEN_DATE;
	}

	/**
	 * @param sWK_DEN_DATE
	 */
	public void setSWK_DEN_DATE(Date sWK_DEN_DATE) {
		this.sWK_DEN_DATE = sWK_DEN_DATE;
	}

	/**
	 * @return String
	 */
	public String getSWK_DEN_NO() {
		return sWK_DEN_NO;
	}

	/**
	 * @param sWK_DEN_NO
	 */
	public void setSWK_DEN_NO(String sWK_DEN_NO) {
		this.sWK_DEN_NO = sWK_DEN_NO;
	}

	/**
	 * @return int
	 */
	public int getSWK_GYO_NO() {
		return sWK_GYO_NO;
	}

	/**
	 * @param sWK_GYO_NO
	 */
	public void setSWK_GYO_NO(int sWK_GYO_NO) {
		this.sWK_GYO_NO = sWK_GYO_NO;
	}

	/**
	 * @return int
	 */
	public int getSWK_NENDO() {
		return sWK_NENDO;
	}

	/**
	 * @param sWK_NENDO
	 */
	public void setSWK_NENDO(int sWK_NENDO) {
		this.sWK_NENDO = sWK_NENDO;
	}

	/**
	 * @return int
	 */
	public int getSWK_TUKIDO() {
		return sWK_TUKIDO;
	}

	/**
	 * @param sWK_TUKIDO
	 */
	public void setSWK_TUKIDO(int sWK_TUKIDO) {
		this.sWK_TUKIDO = sWK_TUKIDO;
	}

	/**
	 * @return String
	 */
	public String getSWK_SEI_NO() {
		return sWK_SEI_NO;
	}

	/**
	 * @param sWK_SEI_NO
	 */
	public void setSWK_SEI_NO(String sWK_SEI_NO) {
		this.sWK_SEI_NO = sWK_SEI_NO;
	}

	/**
	 * @return int
	 */
	public int getSWK_DC_KBN() {
		return sWK_DC_KBN;
	}

	/**
	 * @param sWK_DC_KBN
	 */
	public void setSWK_DC_KBN(int sWK_DC_KBN) {
		this.sWK_DC_KBN = sWK_DC_KBN;
	}

	/**
	 * @return String
	 */
	public String getSWK_KMK_CODE() {
		return sWK_KMK_CODE;
	}

	/**
	 * @param sWK_KMK_CODE
	 */
	public void setSWK_KMK_CODE(String sWK_KMK_CODE) {
		this.sWK_KMK_CODE = sWK_KMK_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_HKM_CODE() {
		return sWK_HKM_CODE;
	}

	/**
	 * @param sWK_HKM_CODE
	 */
	public void setSWK_HKM_CODE(String sWK_HKM_CODE) {
		this.sWK_HKM_CODE = sWK_HKM_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_UKM_CODE() {
		return sWK_UKM_CODE;
	}

	/**
	 * @param sWK_UKM_CODE
	 */
	public void setSWK_UKM_CODE(String sWK_UKM_CODE) {
		this.sWK_UKM_CODE = sWK_UKM_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_DEP_CODE() {
		return sWK_DEP_CODE;
	}

	/**
	 * @param sWK_DEP_CODE
	 */
	public void setSWK_DEP_CODE(String sWK_DEP_CODE) {
		this.sWK_DEP_CODE = sWK_DEP_CODE;
	}

	/**
	 * @return int
	 */
	public int getSWK_ZEI_KBN() {
		return sWK_ZEI_KBN;
	}

	/**
	 * @param sWK_ZEI_KBN
	 */
	public void setSWK_ZEI_KBN(int sWK_ZEI_KBN) {
		this.sWK_ZEI_KBN = sWK_ZEI_KBN;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSWK_KIN() {
		return sWK_KIN;
	}

	/**
	 * @param sWK_KIN
	 */
	public void setSWK_KIN(BigDecimal sWK_KIN) {
		this.sWK_KIN = sWK_KIN;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSWK_ZEI_KIN() {
		return sWK_ZEI_KIN;
	}

	/**
	 * @param sWK_ZEI_KIN
	 */
	public void setSWK_ZEI_KIN(BigDecimal sWK_ZEI_KIN) {
		this.sWK_ZEI_KIN = sWK_ZEI_KIN;
	}

	/**
	 * @return String
	 */
	public String getSWK_ZEI_CODE() {
		return sWK_ZEI_CODE;
	}

	/**
	 * @param sWK_ZEI_CODE
	 */
	public void setSWK_ZEI_CODE(String sWK_ZEI_CODE) {
		this.sWK_ZEI_CODE = sWK_ZEI_CODE;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSWK_ZEI_RATE() {
		return sWK_ZEI_RATE;
	}

	/**
	 * @param sWK_ZEI_RATE
	 */
	public void setSWK_ZEI_RATE(BigDecimal sWK_ZEI_RATE) {
		this.sWK_ZEI_RATE = sWK_ZEI_RATE;
	}

	/**
	 * @return String
	 */
	public String getSWK_GYO_TEK_CODE() {
		return sWK_GYO_TEK_CODE;
	}

	/**
	 * @param sWK_GYO_TEK_CODE
	 */
	public void setSWK_GYO_TEK_CODE(String sWK_GYO_TEK_CODE) {
		this.sWK_GYO_TEK_CODE = sWK_GYO_TEK_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_GYO_TEK() {
		return sWK_GYO_TEK;
	}

	/**
	 * @param sWK_GYO_TEK
	 */
	public void setSWK_GYO_TEK(String sWK_GYO_TEK) {
		this.sWK_GYO_TEK = sWK_GYO_TEK;
	}

	/**
	 * @return String
	 */
	public String getSWK_TRI_CODE() {
		return sWK_TRI_CODE;
	}

	/**
	 * @param sWK_TRI_CODE
	 */
	public void setSWK_TRI_CODE(String sWK_TRI_CODE) {
		this.sWK_TRI_CODE = sWK_TRI_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_EMP_CODE() {
		return sWK_EMP_CODE;
	}

	/**
	 * @param sWK_EMP_CODE
	 */
	public void setSWK_EMP_CODE(String sWK_EMP_CODE) {
		this.sWK_EMP_CODE = sWK_EMP_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_KNR_CODE_1() {
		return sWK_KNR_CODE_1;
	}

	/**
	 * @param sWK_KNR_CODE_1
	 */
	public void setSWK_KNR_CODE_1(String sWK_KNR_CODE_1) {
		this.sWK_KNR_CODE_1 = sWK_KNR_CODE_1;
	}

	/**
	 * @return String
	 */
	public String getSWK_KNR_CODE_2() {
		return sWK_KNR_CODE_2;
	}

	/**
	 * @param sWK_KNR_CODE_2
	 */
	public void setSWK_KNR_CODE_2(String sWK_KNR_CODE_2) {
		this.sWK_KNR_CODE_2 = sWK_KNR_CODE_2;
	}

	/**
	 * @return String
	 */
	public String getSWK_KNR_CODE_3() {
		return sWK_KNR_CODE_3;
	}

	/**
	 * @param sWK_KNR_CODE_3
	 */
	public void setSWK_KNR_CODE_3(String sWK_KNR_CODE_3) {
		this.sWK_KNR_CODE_3 = sWK_KNR_CODE_3;
	}

	/**
	 * @return String
	 */
	public String getSWK_KNR_CODE_4() {
		return sWK_KNR_CODE_4;
	}

	/**
	 * @param sWK_KNR_CODE_4
	 */
	public void setSWK_KNR_CODE_4(String sWK_KNR_CODE_4) {
		this.sWK_KNR_CODE_4 = sWK_KNR_CODE_4;
	}

	/**
	 * @return String
	 */
	public String getSWK_KNR_CODE_5() {
		return sWK_KNR_CODE_5;
	}

	/**
	 * @param sWK_KNR_CODE_5
	 */
	public void setSWK_KNR_CODE_5(String sWK_KNR_CODE_5) {
		this.sWK_KNR_CODE_5 = sWK_KNR_CODE_5;
	}

	/**
	 * @return String
	 */
	public String getSWK_KNR_CODE_6() {
		return sWK_KNR_CODE_6;
	}

	/**
	 * @param sWK_KNR_CODE_6
	 */
	public void setSWK_KNR_CODE_6(String sWK_KNR_CODE_6) {
		this.sWK_KNR_CODE_6 = sWK_KNR_CODE_6;
	}

	/**
	 * @return String
	 */
	public String getSWK_HM_1() {
		return sWK_HM_1;
	}

	/**
	 * @param sWK_HM_1
	 */
	public void setSWK_HM_1(String sWK_HM_1) {
		this.sWK_HM_1 = sWK_HM_1;
	}

	/**
	 * @return String
	 */
	public String getSWK_HM_2() {
		return sWK_HM_2;
	}

	/**
	 * @param sWK_HM_2
	 */
	public void setSWK_HM_2(String sWK_HM_2) {
		this.sWK_HM_2 = sWK_HM_2;
	}

	/**
	 * @return String
	 */
	public String getSWK_HM_3() {
		return sWK_HM_3;
	}

	/**
	 * @param sWK_HM_3
	 */
	public void setSWK_HM_3(String sWK_HM_3) {
		this.sWK_HM_3 = sWK_HM_3;
	}

	/**
	 * @return int
	 */
	public int getSWK_AUTO_KBN() {
		return sWK_AUTO_KBN;
	}

	/**
	 * @param sWK_AUTO_KBN
	 */
	public void setSWK_AUTO_KBN(int sWK_AUTO_KBN) {
		this.sWK_AUTO_KBN = sWK_AUTO_KBN;
	}

	/**
	 * @return String
	 */
	public String getSWK_DATA_KBN() {
		return sWK_DATA_KBN;
	}

	/**
	 * @param sWK_DATA_KBN
	 */
	public void setSWK_DATA_KBN(String sWK_DATA_KBN) {
		this.sWK_DATA_KBN = sWK_DATA_KBN;
	}

	/**
	 * @return int
	 */
	public int getSWK_UPD_KBN() {
		return sWK_UPD_KBN;
	}

	/**
	 * @param sWK_UPD_KBN
	 */
	public void setSWK_UPD_KBN(int sWK_UPD_KBN) {
		this.sWK_UPD_KBN = sWK_UPD_KBN;
	}

	/**
	 * @return int
	 */
	public int getSWK_KSN_KBN() {
		return sWK_KSN_KBN;
	}

	/**
	 * @param sWK_KSN_KBN
	 */
	public void setSWK_KSN_KBN(int sWK_KSN_KBN) {
		this.sWK_KSN_KBN = sWK_KSN_KBN;
	}

	/**
	 * @return String
	 */
	public String getSWK_AT_KMK_CODE() {
		return sWK_AT_KMK_CODE;
	}

	/**
	 * @param sWK_AT_KMK_CODE
	 */
	public void setSWK_AT_KMK_CODE(String sWK_AT_KMK_CODE) {
		this.sWK_AT_KMK_CODE = sWK_AT_KMK_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_AT_HKM_CODE() {
		return sWK_AT_HKM_CODE;
	}

	/**
	 * @param sWK_AT_HKM_CODE
	 */
	public void setSWK_AT_HKM_CODE(String sWK_AT_HKM_CODE) {
		this.sWK_AT_HKM_CODE = sWK_AT_HKM_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_AT_UKM_CODE() {
		return sWK_AT_UKM_CODE;
	}

	/**
	 * @param sWK_AT_UKM_CODE
	 */
	public void setSWK_AT_UKM_CODE(String sWK_AT_UKM_CODE) {
		this.sWK_AT_UKM_CODE = sWK_AT_UKM_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_AT_DEP_CODE() {
		return sWK_AT_DEP_CODE;
	}

	/**
	 * @param sWK_AT_DEP_CODE
	 */
	public void setSWK_AT_DEP_CODE(String sWK_AT_DEP_CODE) {
		this.sWK_AT_DEP_CODE = sWK_AT_DEP_CODE;
	}

	/**
	 * @return String
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * @param pRG_ID
	 */
	public void setPRG_ID(String pRG_ID) {
		this.pRG_ID = pRG_ID;
	}

	/**
	 * @return String
	 */
	public String getSWK_K_KAI_CODE() {
		return sWK_K_KAI_CODE;
	}

	/**
	 * @param sWK_K_KAI_CODE
	 */
	public void setSWK_K_KAI_CODE(String sWK_K_KAI_CODE) {
		this.sWK_K_KAI_CODE = sWK_K_KAI_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_CUR_CODE() {
		return sWK_CUR_CODE;
	}

	/**
	 * @param sWK_CUR_CODE
	 */
	public void setSWK_CUR_CODE(String sWK_CUR_CODE) {
		this.sWK_CUR_CODE = sWK_CUR_CODE;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSWK_CUR_RATE() {
		return sWK_CUR_RATE;
	}

	/**
	 * @param sWK_CUR_RATE
	 */
	public void setSWK_CUR_RATE(BigDecimal sWK_CUR_RATE) {
		this.sWK_CUR_RATE = sWK_CUR_RATE;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSWK_IN_KIN() {
		return sWK_IN_KIN;
	}

	/**
	 * @param sWK_IN_KIN
	 */
	public void setSWK_IN_KIN(BigDecimal sWK_IN_KIN) {
		this.sWK_IN_KIN = sWK_IN_KIN;
	}

	/**
	 * @return int
	 */
	public int getSWK_TUKE_KBN() {
		return sWK_TUKE_KBN;
	}

	/**
	 * @param sWK_TUKE_KBN
	 */
	public void setSWK_TUKE_KBN(int sWK_TUKE_KBN) {
		this.sWK_TUKE_KBN = sWK_TUKE_KBN;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSWK_IN_ZEI_KIN() {
		return sWK_IN_ZEI_KIN;
	}

	/**
	 * @param sWK_IN_ZEI_KIN
	 */
	public void setSWK_IN_ZEI_KIN(BigDecimal sWK_IN_ZEI_KIN) {
		this.sWK_IN_ZEI_KIN = sWK_IN_ZEI_KIN;
	}

	/**
	 * @return int
	 */
	public int getSWK_KESI_KBN() {
		return sWK_KESI_KBN;
	}

	/**
	 * @param sWK_KESI_KBN
	 */
	public void setSWK_KESI_KBN(int sWK_KESI_KBN) {
		this.sWK_KESI_KBN = sWK_KESI_KBN;
	}

	/**
	 * @return Date
	 */
	public Date getSWK_KESI_DATE() {
		return sWK_KESI_DATE;
	}

	/**
	 * @param sWK_KESI_DATE
	 */
	public void setSWK_KESI_DATE(Date sWK_KESI_DATE) {
		this.sWK_KESI_DATE = sWK_KESI_DATE;
	}

	/**
	 * @return String
	 */
	public String getSWK_KESI_DEN_NO() {
		return sWK_KESI_DEN_NO;
	}

	/**
	 * @param sWK_KESI_DEN_NO
	 */
	public void setSWK_KESI_DEN_NO(String sWK_KESI_DEN_NO) {
		this.sWK_KESI_DEN_NO = sWK_KESI_DEN_NO;
	}

	/**
	 * @return Date
	 */
	public Date getSWK_SOUSAI_DATE() {
		return sWK_SOUSAI_DATE;
	}

	/**
	 * @param sWK_SOUSAI_DATE
	 */
	public void setSWK_SOUSAI_DATE(Date sWK_SOUSAI_DATE) {
		this.sWK_SOUSAI_DATE = sWK_SOUSAI_DATE;
	}

	/**
	 * @return String
	 */
	public String getSWK_SOUSAI_DEN_NO() {
		return sWK_SOUSAI_DEN_NO;
	}

	/**
	 * @param sWK_SOUSAI_DEN_NO
	 */
	public void setSWK_SOUSAI_DEN_NO(String sWK_SOUSAI_DEN_NO) {
		this.sWK_SOUSAI_DEN_NO = sWK_SOUSAI_DEN_NO;
	}

	/**
	 * @return Date
	 */
	public Date getHAS_DATE() {
		return hAS_DATE;
	}

	/**
	 * @param hAS_DATE
	 */
	public void setHAS_DATE(Date hAS_DATE) {
		this.hAS_DATE = hAS_DATE;
	}

	/**
	 * @return String
	 */
	public String getDEN_SYU_CODE() {
		return dEN_SYU_CODE;
	}

	/**
	 * @param dEN_SYU_CODE
	 */
	public void setDEN_SYU_CODE(String dEN_SYU_CODE) {
		this.dEN_SYU_CODE = dEN_SYU_CODE;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/sWK_DEN_DATE=").append(sWK_DEN_DATE);
		buff.append("/sWK_DEN_NO=").append(sWK_DEN_NO);
		buff.append("/sWK_GYO_NO=").append(sWK_GYO_NO);
		buff.append("/sWK_NENDO=").append(sWK_NENDO);
		buff.append("/sWK_TUKIDO=").append(sWK_TUKIDO);
		buff.append("/sWK_SEI_NO=").append(sWK_SEI_NO);
		buff.append("/sWK_DC_KBN=").append(sWK_DC_KBN);
		buff.append("/sWK_KMK_CODE=").append(sWK_KMK_CODE);
		buff.append("/sWK_HKM_CODE=").append(sWK_HKM_CODE);
		buff.append("/sWK_UKM_CODE=").append(sWK_UKM_CODE);
		buff.append("/sWK_DEP_CODE=").append(sWK_DEP_CODE);
		buff.append("/sWK_ZEI_KBN=").append(sWK_ZEI_KBN);
		buff.append("/sWK_KIN=").append(sWK_KIN);
		buff.append("/sWK_ZEI_KIN=").append(sWK_ZEI_KIN);
		buff.append("/sWK_ZEI_CODE=").append(sWK_ZEI_CODE);
		buff.append("/sWK_ZEI_RATE=").append(sWK_ZEI_RATE);
		buff.append("/sWK_GYO_TEK_CODE=").append(sWK_GYO_TEK_CODE);
		buff.append("/sWK_GYO_TEK=").append(sWK_GYO_TEK);
		buff.append("/sWK_TRI_CODE=").append(sWK_TRI_CODE);
		buff.append("/sWK_EMP_CODE=").append(sWK_EMP_CODE);
		buff.append("/sWK_KNR_CODE_1=").append(sWK_KNR_CODE_1);
		buff.append("/sWK_KNR_CODE_2=").append(sWK_KNR_CODE_2);
		buff.append("/sWK_KNR_CODE_3=").append(sWK_KNR_CODE_3);
		buff.append("/sWK_KNR_CODE_4=").append(sWK_KNR_CODE_4);
		buff.append("/sWK_KNR_CODE_5=").append(sWK_KNR_CODE_5);
		buff.append("/sWK_KNR_CODE_6=").append(sWK_KNR_CODE_6);
		buff.append("/sWK_HM_1=").append(sWK_HM_1);
		buff.append("/sWK_HM_2=").append(sWK_HM_2);
		buff.append("/sWK_HM_3=").append(sWK_HM_3);
		buff.append("/sWK_AUTO_KBN=").append(sWK_AUTO_KBN);
		buff.append("/sWK_DATA_KBN=").append(sWK_DATA_KBN);
		buff.append("/sWK_UPD_KBN=").append(sWK_UPD_KBN);
		buff.append("/sWK_KSN_KBN=").append(sWK_KSN_KBN);
		buff.append("/sWK_AT_KMK_CODE=").append(sWK_AT_KMK_CODE);
		buff.append("/sWK_AT_HKM_CODE=").append(sWK_AT_HKM_CODE);
		buff.append("/sWK_AT_UKM_CODE=").append(sWK_AT_UKM_CODE);
		buff.append("/sWK_AT_DEP_CODE=").append(sWK_AT_DEP_CODE);
		buff.append("/iNP_DATE=").append(iNP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("/sWK_K_KAI_CODE=").append(sWK_K_KAI_CODE);
		buff.append("/sWK_CUR_CODE=").append(sWK_CUR_CODE);
		buff.append("/sWK_CUR_RATE=").append(sWK_CUR_RATE);
		buff.append("/sWK_IN_KIN=").append(sWK_IN_KIN);
		buff.append("/sWK_TUKE_KBN=").append(sWK_TUKE_KBN);
		buff.append("/sWK_IN_ZEI_KIN=").append(sWK_IN_ZEI_KIN);
		buff.append("/sWK_KESI_KBN=").append(sWK_KESI_KBN);
		buff.append("/sWK_KESI_DATE=").append(sWK_KESI_DATE);
		buff.append("/sWK_KESI_DEN_NO=").append(sWK_KESI_DEN_NO);
		buff.append("/sWK_SOUSAI_DATE=").append(sWK_SOUSAI_DATE);
		buff.append("/sWK_SOUSAI_DEN_NO=").append(sWK_SOUSAI_DEN_NO);
		buff.append("/hAS_DATE=").append(hAS_DATE);
		buff.append("/dEN_SYU_CODE=").append(dEN_SYU_CODE);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(sWK_DEN_DATE);
		list.add(sWK_DEN_NO);
		list.add(sWK_GYO_NO);
		list.add(sWK_NENDO);
		list.add(sWK_TUKIDO);
		list.add(sWK_SEI_NO);
		list.add(sWK_DC_KBN);
		list.add(sWK_KMK_CODE);
		list.add(sWK_HKM_CODE);
		list.add(sWK_UKM_CODE);
		list.add(sWK_DEP_CODE);
		list.add(sWK_ZEI_KBN);
		list.add(sWK_KIN);
		list.add(sWK_ZEI_KIN);
		list.add(sWK_ZEI_CODE);
		list.add(sWK_ZEI_RATE);
		list.add(sWK_GYO_TEK_CODE);
		list.add(sWK_GYO_TEK);
		list.add(sWK_TRI_CODE);
		list.add(sWK_EMP_CODE);
		list.add(sWK_KNR_CODE_1);
		list.add(sWK_KNR_CODE_2);
		list.add(sWK_KNR_CODE_3);
		list.add(sWK_KNR_CODE_4);
		list.add(sWK_KNR_CODE_5);
		list.add(sWK_KNR_CODE_6);
		list.add(sWK_HM_1);
		list.add(sWK_HM_2);
		list.add(sWK_HM_3);
		list.add(sWK_AUTO_KBN);
		list.add(sWK_DATA_KBN);
		list.add(sWK_UPD_KBN);
		list.add(sWK_KSN_KBN);
		list.add(sWK_AT_KMK_CODE);
		list.add(sWK_AT_HKM_CODE);
		list.add(sWK_AT_UKM_CODE);
		list.add(sWK_AT_DEP_CODE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);
		list.add(sWK_K_KAI_CODE);
		list.add(sWK_CUR_CODE);
		list.add(sWK_CUR_RATE);
		list.add(sWK_IN_KIN);
		list.add(sWK_TUKE_KBN);
		list.add(sWK_IN_ZEI_KIN);
		list.add(sWK_KESI_KBN);
		list.add(sWK_KESI_DATE);
		list.add(sWK_KESI_DEN_NO);
		list.add(sWK_SOUSAI_DATE);
		list.add(sWK_SOUSAI_DEN_NO);
		list.add(hAS_DATE);
		list.add(dEN_SYU_CODE);

		return list;
	}

	/**
	 * 小数点桁数
	 * 
	 * @return digit 小数点桁数
	 */
	public int getDigit() {
		return digit;
	}

	/**
	 * 小数点桁数
	 * 
	 * @param digit 小数点桁数
	 */
	public void setDigit(int digit) {
		this.digit = digit;
	}

	/**
	 * 計上会社
	 * 
	 * @return sWK_K_KAI_NNAME 計上会社
	 */
	public String getSWK_K_KAI_NAME() {
		return sWK_K_KAI_NAME;
	}

	/**
	 * 計上会社
	 * 
	 * @param name 計上会社
	 */
	public void setSWK_K_KAI_NAME(String name) {
		sWK_K_KAI_NAME = name;
	}

	/**
	 * @return sWK_DEP_NAME
	 */
	public String getSWK_DEP_NAME() {
		return sWK_DEP_NAME;
	}

	/**
	 * @param swk_dep_name 設定する sWK_DEP_NAME
	 */
	public void setSWK_DEP_NAME(String swk_dep_name) {
		sWK_DEP_NAME = swk_dep_name;
	}

	/**
	 * @return String
	 */
	public String getSWK_KMK_NAME() {
		return sWK_KMK_NAME;
	}

	/**
	 * @param swk_kmk_name
	 */
	public void setSWK_KMK_NAME(String swk_kmk_name) {
		sWK_KMK_NAME = swk_kmk_name;
	}

	/**
	 * @return String
	 */
	public String getSWK_HKM_NAME() {
		return sWK_HKM_NAME;
	}

	/**
	 * @param swk_hkm_name
	 */
	public void setSWK_HKM_NAME(String swk_hkm_name) {
		sWK_HKM_NAME = swk_hkm_name;
	}

	/**
	 * @return String
	 */
	public String getSWK_UKM_NAME() {
		return sWK_UKM_NAME;
	}

	/**
	 * @param swk_ukm_name
	 */
	public void setSWK_UKM_NAME(String swk_ukm_name) {
		sWK_UKM_NAME = swk_ukm_name;
	}

	/**
	 * @return String
	 */
	public String getSWK_ZEI_NAME() {
		return sWK_ZEI_NAME;
	}

	/**
	 * @param swk_zei_name
	 */
	public void setSWK_ZEI_NAME(String swk_zei_name) {
		sWK_ZEI_NAME = swk_zei_name;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSWK_DR_KIN() {
		return sWK_DR_KIN;
	}

	/**
	 * @param swk_dr_kin
	 */
	public void setSWK_DR_KIN(BigDecimal swk_dr_kin) {
		sWK_DR_KIN = swk_dr_kin;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSWK_CR_KIN() {
		return sWK_CR_KIN;
	}

	/**
	 * @param swk_cr_kin
	 */
	public void setSWK_CR_KIN(BigDecimal swk_cr_kin) {
		sWK_CR_KIN = swk_cr_kin;
	}

	/**
	 * @return String
	 */
	public String getSWK_TRI_NAME() {
		return SWK_TRI_NAME;
	}

	/**
	 * @param swk_tri_name
	 */
	public void setSWK_TRI_NAME(String swk_tri_name) {
		SWK_TRI_NAME = swk_tri_name;
	}

	/**
	 * @return String
	 */
	public String getSWK_EMP_NAME() {
		return SWK_EMP_NAME;
	}

	/**
	 * @param swk_emp_name
	 */
	public void setSWK_EMP_NAME(String swk_emp_name) {
		SWK_EMP_NAME = swk_emp_name;
	}

	/**
	 * @return String
	 */
	public String getSWK_KNR_NAME_1() {
		return sWK_KNR_NAME_1;
	}

	/**
	 * @param swk_knr_name_1
	 */
	public void setSWK_KNR_NAME_1(String swk_knr_name_1) {
		sWK_KNR_NAME_1 = swk_knr_name_1;
	}

	/**
	 * @return String
	 */
	public String getSWK_KNR_NAME_2() {
		return sWK_KNR_NAME_2;
	}

	/**
	 * @param swk_knr_name_2
	 */
	public void setSWK_KNR_NAME_2(String swk_knr_name_2) {
		sWK_KNR_NAME_2 = swk_knr_name_2;
	}

	/**
	 * @return String
	 */
	public String getSWK_KNR_NAME_3() {
		return sWK_KNR_NAME_3;
	}

	/**
	 * @param swk_knr_name_3
	 */
	public void setSWK_KNR_NAME_3(String swk_knr_name_3) {
		sWK_KNR_NAME_3 = swk_knr_name_3;
	}

	/**
	 * @return String
	 */
	public String getSWK_KNR_NAME_4() {
		return sWK_KNR_NAME_4;
	}

	/**
	 * @param swk_knr_name_4
	 */
	public void setSWK_KNR_NAME_4(String swk_knr_name_4) {
		sWK_KNR_NAME_4 = swk_knr_name_4;
	}

	/**
	 * @return String
	 */
	public String getSWK_KNR_NAME_5() {
		return sWK_KNR_NAME_5;
	}

	/**
	 * @param swk_knr_name_5
	 */
	public void setSWK_KNR_NAME_5(String swk_knr_name_5) {
		sWK_KNR_NAME_5 = swk_knr_name_5;
	}

	/**
	 * @return String
	 */
	public String getSWK_KNR_NAME_6() {
		return sWK_KNR_NAME_6;
	}

	/**
	 * @param swk_knr_name_6
	 */
	public void setSWK_KNR_NAME_6(String swk_knr_name_6) {
		sWK_KNR_NAME_6 = swk_knr_name_6;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSWK_CR_KIN_G() {
		return sWK_CR_KIN_G;
	}

	/**
	 * @param swk_cr_kin_g
	 */
	public void setSWK_CR_KIN_G(BigDecimal swk_cr_kin_g) {
		sWK_CR_KIN_G = swk_cr_kin_g;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSWK_DR_KIN_G() {
		return sWK_DR_KIN_G;
	}

	/**
	 * @param swk_dr_kin_g
	 */
	public void setSWK_DR_KIN_G(BigDecimal swk_dr_kin_g) {
		sWK_DR_KIN_G = swk_dr_kin_g;
	}

	/**
	 * APAR区分の取得
	 * 
	 * @return APAR区分
	 */
	public String getSWK_APAR_KBN() {
		return sWK_APAR_KBN;
	}

	/**
	 * APAR区分の設定
	 * 
	 * @param swk_apar_kbn APAR区分
	 */
	public void setSWK_APAR_KBN(String swk_apar_kbn) {
		sWK_APAR_KBN = swk_apar_kbn;
	}

	/**
	 * オブジェクトのClone
	 */
	@Override
	public SWK_DTL clone() {
		try {
			return (SWK_DTL) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * AP_DEN_DATE取得
	 * 
	 * @return AP_DEN_DATE
	 */
	public Date getAP_DEN_DATE() {
		return aP_DEN_DATE;
	}

	/**
	 * AP_DEN_DATE設定
	 * 
	 * @param ap_den_date
	 */
	public void setAP_DEN_DATE(Date ap_den_date) {
		aP_DEN_DATE = ap_den_date;
	}

	/**
	 * AP_DEN_NO取得
	 * 
	 * @return AP_DEN_NO
	 */
	public String getAP_DEN_NO() {
		return aP_DEN_NO;
	}

	/**
	 * AP_DEN_NO設定
	 * 
	 * @param ap_den_no
	 */
	public void setAP_DEN_NO(String ap_den_no) {
		aP_DEN_NO = ap_den_no;
	}

	/**
	 * AP_GYO_NO取得
	 * 
	 * @return AP_GYO_NO
	 */
	public int getAP_GYO_NO() {
		return aP_GYO_NO;
	}

	/**
	 * AP_GYO_NO設定
	 * 
	 * @param ap_gyo_no
	 */
	public void setAP_GYO_NO(int ap_gyo_no) {
		aP_GYO_NO = ap_gyo_no;
	}

	/**
	 * aR_DEN_DATE取得
	 * 
	 * @return aR_DEN_DATE
	 */
	public Date getAR_DEN_DATE() {
		return aR_DEN_DATE;
	}

	/**
	 * aR_DEN_DATE設定
	 * 
	 * @param ar_den_date
	 */
	public void setAR_DEN_DATE(Date ar_den_date) {
		aR_DEN_DATE = ar_den_date;
	}

	/**
	 * aR_DEN_NO取得
	 * 
	 * @return aR_DEN_NO
	 */
	public String getAR_DEN_NO() {
		return aR_DEN_NO;
	}

	/**
	 * aR_DEN_NO設定
	 * 
	 * @param ar_den_no
	 */
	public void setAR_DEN_NO(String ar_den_no) {
		aR_DEN_NO = ar_den_no;
	}

	/**
	 * aR_GYO_NO取得
	 * 
	 * @return aR_GYO_NO
	 */
	public int getAR_GYO_NO() {
		return aR_GYO_NO;
	}

	/**
	 * aR_GYO_NO設定
	 * 
	 * @param ar_gyo_no
	 */
	public void setAR_GYO_NO(int ar_gyo_no) {
		aR_GYO_NO = ar_gyo_no;
	}

	/**
	 * aR_DATE取得
	 * 
	 * @return aR_DATE
	 */
	public Date getAR_DATE() {
		return aR_DATE;
	}

	/**
	 * aR_DATE設定
	 * 
	 * @param ar_date
	 */
	public void setAR_DATE(Date ar_date) {
		aR_DATE = ar_date;
	}

	/**
	 * aR_SEI_NO取得
	 * 
	 * @return aR_SEI_NO
	 */
	public String getAR_SEI_NO() {
		return aR_SEI_NO;
	}

	/**
	 * aR_SEI_NO設定
	 * 
	 * @param ar_sei_no
	 */
	public void setAR_SEI_NO(String ar_sei_no) {
		aR_SEI_NO = ar_sei_no;
	}
}
