package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class T_GL0321 extends MasterBase {

	/**  */
	public static final String TABLE = "T_GL0321";

	private String kAI_CODE = "";

	private String sWK_DEN_DATE = "";

	private String sWK_DEN_NO = "";

	private String sWK_GYO_NO = "";

	private String sWK_UKE_DEP_CODE;

	private String sWK_TEK_CODE;

	private String sWK_TEK;

	private String sWK_SYO_EMP_CODE;

	private String sWK_SYO_DATE;

	private String sWK_IRAI_EMP_CODE;

	private String sWK_IRAI_DEP_CODE;

	private String sWK_IRAI_DATE;

	private String sWK_SYS_KBN;

	private String sWK_DEN_SYU;

	private String sWK_UPD_KBN = "";

	private String sWK_KSN_KBN = "";

	private String sWK_KMK_CODE;

	private String sWK_HKM_CODE;

	private String sWK_UKM_CODE;

	private String sWK_DEP_CODE;

	private String sWK_TRI_CODE;

	private String sWK_EMP_CODE;

	private String sWK_CUR_CODE;

	private String sWK_CUR_RATE;

	private String sWK_DC_KBN = "";

	private String sWK_ZEI_KBN;

	private String sWK_ZEI_KIN;

	private String sWK_ZEI_CODE;

	private String sWK_GYO_TEK_CODE;

	private String sWK_GYO_TEK;

	private String sWK_KNR_CODE_1;

	private String sWK_KNR_CODE_2;

	private String sWK_KNR_CODE_3;

	private String sWK_KNR_CODE_4;

	private String sWK_KNR_CODE_5;

	private String sWK_KNR_CODE_6;

	private String sWK_HM_1;

	private String sWK_HM_2;

	private String sWK_HM_3;

	private String sWK_AUTO_KBN = "";

	private String hAS_DATE;

	private String sWK_AT_KMK_CODE;

	private String sWK_AT_HKM_CODE;

	private String sWK_AT_UKM_CODE;

	private String sWK_AT_DEP_CODE;

	private String sWK_K_KAI_CODE;

	private String sWK_SEI_NO;

	private String sWK_KIN;

	private String sWK_IN_KIN;

	private String sWK_SIHA_KBN;

	private String sWK_SIHA_DATE_AP;

	private String sWK_HOH_CODE_AP;

	private String sWK_HORYU_KBN;

	private String sWK_TJK_CODE;

	private String sWK_AR_DATE;

	private String sWK_UKE_DATE;

	private String sWK_CBK_CODE;

	private String sWK_TUKE_KBN;

	private String uKE_DEP_NAME;

	private String kMK_CODE;

	private Long kMK_HKM_KBN;

	private Long kMK_URI_ZEI_FLG;

	private Long kMK_SIR_ZEI_FLG;

	private Long kMK_TRI_CODE_FLG;

	private Long kMK_EMP_CODE_FLG;

	private Long kMK_KNR_FLG_1;

	private Long kMK_KNR_FLG_2;

	private Long kMK_KNR_FLG_3;

	private Long kMK_KNR_FLG_4;

	private Long kMK_KNR_FLG_5;

	private Long kMK_KNR_FLG_6;

	private Long kMK_HM_FLG_1;

	private Long kMK_HM_FLG_2;

	private Long kMK_HM_FLG_3;

	private String hKM_NAME;

	private Long hKM_UKM_KBN;

	private Long hKM_URI_ZEI_FLG;

	private Long hKM_SIR_ZEI_FLG;

	private Long hKM_TRI_CODE_FLG;

	private Long hKM_EMP_CODE_FLG;

	private Long hKM_KNR_FLG_1;

	private Long hKM_KNR_FLG_2;

	private Long hKM_KNR_FLG_3;

	private Long hKM_KNR_FLG_4;

	private Long hKM_KNR_FLG_5;

	private Long hKM_KNR_FLG_6;

	private Long hKM_HM_FLG_1;

	private Long hKM_HM_FLG_2;

	private Long hKM_HM_FLG_3;

	private String uKM_NAME;

	private Long uKM_URI_ZEI_FLG;

	private Long uKM_SIR_ZEI_FLG;

	private Long uKM_TRI_CODE_FLG;

	private Long uKM_EMP_CODE_FLG;

	private Long uKM_KNR_FLG_1;

	private Long uKM_KNR_FLG_2;

	private Long uKM_KNR_FLG_3;

	private Long uKM_KNR_FLG_4;

	private Long uKM_KNR_FLG_5;

	private Long uKM_KNR_FLG_6;

	private Long uKM_HM_FLG_1;

	private Long uKM_HM_FLG_2;

	private Long uKM_HM_FLG_3;

	private String zEI_ZEI_CODE;

	private String iRA_EMP_CODE;

	private String iDE_DEP_CODE;

	private String kEI_DEP_CODE;

	private Long eRR_FLG;

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
	 * @return String
	 */
	public String getSWK_DEN_DATE() {
		return sWK_DEN_DATE;
	}

	/**
	 * @param sWK_DEN_DATE
	 */
	public void setSWK_DEN_DATE(String sWK_DEN_DATE) {
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
	 * @return String
	 */
	public String getSWK_GYO_NO() {
		return sWK_GYO_NO;
	}

	/**
	 * @param sWK_GYO_NO
	 */
	public void setSWK_GYO_NO(String sWK_GYO_NO) {
		this.sWK_GYO_NO = sWK_GYO_NO;
	}

	/**
	 * @return String
	 */
	public String getSWK_UKE_DEP_CODE() {
		return sWK_UKE_DEP_CODE;
	}

	/**
	 * @param sWK_UKE_DEP_CODE
	 */
	public void setSWK_UKE_DEP_CODE(String sWK_UKE_DEP_CODE) {
		this.sWK_UKE_DEP_CODE = sWK_UKE_DEP_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_TEK_CODE() {
		return sWK_TEK_CODE;
	}

	/**
	 * @param sWK_TEK_CODE
	 */
	public void setSWK_TEK_CODE(String sWK_TEK_CODE) {
		this.sWK_TEK_CODE = sWK_TEK_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_TEK() {
		return sWK_TEK;
	}

	/**
	 * @param sWK_TEK
	 */
	public void setSWK_TEK(String sWK_TEK) {
		this.sWK_TEK = sWK_TEK;
	}

	/**
	 * @return String
	 */
	public String getSWK_SYO_EMP_CODE() {
		return sWK_SYO_EMP_CODE;
	}

	/**
	 * @param sWK_SYO_EMP_CODE
	 */
	public void setSWK_SYO_EMP_CODE(String sWK_SYO_EMP_CODE) {
		this.sWK_SYO_EMP_CODE = sWK_SYO_EMP_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_SYO_DATE() {
		return sWK_SYO_DATE;
	}

	/**
	 * @param sWK_SYO_DATE
	 */
	public void setSWK_SYO_DATE(String sWK_SYO_DATE) {
		this.sWK_SYO_DATE = sWK_SYO_DATE;
	}

	/**
	 * @return String
	 */
	public String getSWK_IRAI_EMP_CODE() {
		return sWK_IRAI_EMP_CODE;
	}

	/**
	 * @param sWK_IRAI_EMP_CODE
	 */
	public void setSWK_IRAI_EMP_CODE(String sWK_IRAI_EMP_CODE) {
		this.sWK_IRAI_EMP_CODE = sWK_IRAI_EMP_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_IRAI_DEP_CODE() {
		return sWK_IRAI_DEP_CODE;
	}

	/**
	 * @param sFR_IRAI_DEP_CODE
	 */
	public void setSWK_IRAI_DEP_CODE(String sFR_IRAI_DEP_CODE) {
		this.sWK_IRAI_DEP_CODE = sFR_IRAI_DEP_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_IRAI_DATE() {
		return sWK_IRAI_DATE;
	}

	/**
	 * @param sWK_IRAI_DATE
	 */
	public void setSWK_IRAI_DATE(String sWK_IRAI_DATE) {
		this.sWK_IRAI_DATE = sWK_IRAI_DATE;
	}

	/**
	 * @return String
	 */
	public String getSWK_SYS_KBN() {
		return sWK_SYS_KBN;
	}

	/**
	 * @param sWK_SYS_KBN
	 */
	public void setSWK_SYS_KBN(String sWK_SYS_KBN) {
		this.sWK_SYS_KBN = sWK_SYS_KBN;
	}

	/**
	 * @return String
	 */
	public String getSWK_DEN_SYU() {
		return sWK_DEN_SYU;
	}

	/**
	 * @param sWK_DEN_SYU
	 */
	public void setSWK_DEN_SYU(String sWK_DEN_SYU) {
		this.sWK_DEN_SYU = sWK_DEN_SYU;
	}

	/**
	 * @return String
	 */
	public String getSWK_UPD_KBN() {
		return sWK_UPD_KBN;
	}

	/**
	 * @param sWK_UPD_KBN
	 */
	public void setSWK_UPD_KBN(String sWK_UPD_KBN) {
		this.sWK_UPD_KBN = sWK_UPD_KBN;
	}

	/**
	 * @return String
	 */
	public String getSWK_KSN_KBN() {
		return sWK_KSN_KBN;
	}

	/**
	 * @param sWK_KSN_KBN
	 */
	public void setSWK_KSN_KBN(String sWK_KSN_KBN) {
		this.sWK_KSN_KBN = sWK_KSN_KBN;
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
	 * @return String
	 */
	public String getSWK_CUR_RATE() {
		return sWK_CUR_RATE;
	}

	/**
	 * @param sWK_CUR_RATE
	 */
	public void setSWK_CUR_RATE(String sWK_CUR_RATE) {
		this.sWK_CUR_RATE = sWK_CUR_RATE;
	}

	/**
	 * @return String
	 */
	public String getSWK_DC_KBN() {
		return sWK_DC_KBN;
	}

	/**
	 * @param sWK_DC_KBN
	 */
	public void setSWK_DC_KBN(String sWK_DC_KBN) {
		this.sWK_DC_KBN = sWK_DC_KBN;
	}

	/**
	 * @return String
	 */
	public String getSWK_ZEI_KBN() {
		return sWK_ZEI_KBN;
	}

	/**
	 * @param sWK_ZEI_KBN
	 */
	public void setSWK_ZEI_KBN(String sWK_ZEI_KBN) {
		this.sWK_ZEI_KBN = sWK_ZEI_KBN;
	}

	/**
	 * @return String
	 */
	public String getSWK_ZEI_KIN() {
		return sWK_ZEI_KIN;
	}

	/**
	 * @param sWK_ZEI_KIN
	 */
	public void setSWK_ZEI_KIN(String sWK_ZEI_KIN) {
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
	 * @return String
	 */
	public String getSWK_AUTO_KBN() {
		return sWK_AUTO_KBN;
	}

	/**
	 * @param sWK_AUTO_KBN
	 */
	public void setSWK_AUTO_KBN(String sWK_AUTO_KBN) {
		this.sWK_AUTO_KBN = sWK_AUTO_KBN;
	}

	/**
	 * @return String
	 */
	public String getHAS_DATE() {
		return hAS_DATE;
	}

	/**
	 * @param hAS_DATE
	 */
	public void setHAS_DATE(String hAS_DATE) {
		this.hAS_DATE = hAS_DATE;
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
	 * @return String
	 */
	public String getSWK_KIN() {
		return sWK_KIN;
	}

	/**
	 * @param sWK_KIN
	 */
	public void setSWK_KIN(String sWK_KIN) {
		this.sWK_KIN = sWK_KIN;
	}

	/**
	 * @return String
	 */
	public String getSWK_IN_KIN() {
		return sWK_IN_KIN;
	}

	/**
	 * @param sWK_IN_KIN
	 */
	public void setSWK_IN_KIN(String sWK_IN_KIN) {
		this.sWK_IN_KIN = sWK_IN_KIN;
	}

	/**
	 * @return String
	 */
	public String getSWK_SIHA_KBN() {
		return sWK_SIHA_KBN;
	}

	/**
	 * @param sWK_SIHA_KBN
	 */
	public void setSWK_SIHA_KBN(String sWK_SIHA_KBN) {
		this.sWK_SIHA_KBN = sWK_SIHA_KBN;
	}

	/**
	 * @return String
	 */
	public String getSWK_SIHA_DATE_AP() {
		return sWK_SIHA_DATE_AP;
	}

	/**
	 * @param sWK_SIHA_DATE_AP
	 */
	public void setSWK_SIHA_DATE_AP(String sWK_SIHA_DATE_AP) {
		this.sWK_SIHA_DATE_AP = sWK_SIHA_DATE_AP;
	}

	/**
	 * @return String
	 */
	public String getSWK_HOH_CODE_AP() {
		return sWK_HOH_CODE_AP;
	}

	/**
	 * @param sWK_HOH_CODE_AP
	 */
	public void setSWK_HOH_CODE_AP(String sWK_HOH_CODE_AP) {
		this.sWK_HOH_CODE_AP = sWK_HOH_CODE_AP;
	}

	/**
	 * @return String
	 */
	public String getSWK_HORYU_KBN() {
		return sWK_HORYU_KBN;
	}

	/**
	 * @param sWK_HORYU_KBN
	 */
	public void setSWK_HORYU_KBN(String sWK_HORYU_KBN) {
		this.sWK_HORYU_KBN = sWK_HORYU_KBN;
	}

	/**
	 * @return String
	 */
	public String getSWK_TJK_CODE() {
		return sWK_TJK_CODE;
	}

	/**
	 * @param sWK_TJK_CODE
	 */
	public void setSWK_TJK_CODE(String sWK_TJK_CODE) {
		this.sWK_TJK_CODE = sWK_TJK_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_AR_DATE() {
		return sWK_AR_DATE;
	}

	/**
	 * @param sWK_AR_DATE
	 */
	public void setSWK_AR_DATE(String sWK_AR_DATE) {
		this.sWK_AR_DATE = sWK_AR_DATE;
	}

	/**
	 * @return String
	 */
	public String getSWK_UKE_DATE() {
		return sWK_UKE_DATE;
	}

	/**
	 * @param sWK_UKE_DATE
	 */
	public void setSWK_UKE_DATE(String sWK_UKE_DATE) {
		this.sWK_UKE_DATE = sWK_UKE_DATE;
	}

	/**
	 * @return String
	 */
	public String getSWK_CBK_CODE() {
		return sWK_CBK_CODE;
	}

	/**
	 * @param sWK_CBK_CODE
	 */
	public void setSWK_CBK_CODE(String sWK_CBK_CODE) {
		this.sWK_CBK_CODE = sWK_CBK_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_TUKE_KBN() {
		return sWK_TUKE_KBN;
	}

	/**
	 * @param sWK_TUKE_KBN
	 */
	public void setSWK_TUKE_KBN(String sWK_TUKE_KBN) {
		this.sWK_TUKE_KBN = sWK_TUKE_KBN;
	}

	/**
	 * @return String
	 */
	public String getUKE_DEP_NAME() {
		return uKE_DEP_NAME;
	}

	/**
	 * @param uKE_DEP_NAME
	 */
	public void setUKE_DEP_NAME(String uKE_DEP_NAME) {
		this.uKE_DEP_NAME = uKE_DEP_NAME;
	}

	/**
	 * @return String
	 */
	public String getKMK_CODE() {
		return kMK_CODE;
	}

	/**
	 * @param kMK_CODE
	 */
	public void setKMK_CODE(String kMK_CODE) {
		this.kMK_CODE = kMK_CODE;
	}

	/**
	 * @return Long
	 */
	public Long getKMK_HKM_KBN() {
		return kMK_HKM_KBN;
	}

	/**
	 * @param kMK_HKM_KBN
	 */
	public void setKMK_HKM_KBN(Long kMK_HKM_KBN) {
		this.kMK_HKM_KBN = kMK_HKM_KBN;
	}

	/**
	 * @return Long
	 */
	public Long getKMK_URI_ZEI_FLG() {
		return kMK_URI_ZEI_FLG;
	}

	/**
	 * @param kMK_URI_ZEI_FLG
	 */
	public void setKMK_URI_ZEI_FLG(Long kMK_URI_ZEI_FLG) {
		this.kMK_URI_ZEI_FLG = kMK_URI_ZEI_FLG;
	}

	/**
	 * @return Long
	 */
	public Long getKMK_SIR_ZEI_FLG() {
		return kMK_SIR_ZEI_FLG;
	}

	/**
	 * @param kMK_SIR_ZEI_FLG
	 */
	public void setKMK_SIR_ZEI_FLG(Long kMK_SIR_ZEI_FLG) {
		this.kMK_SIR_ZEI_FLG = kMK_SIR_ZEI_FLG;
	}

	/**
	 * @return Long
	 */
	public Long getKMK_TRI_CODE_FLG() {
		return kMK_TRI_CODE_FLG;
	}

	/**
	 * @param kMK_TRI_CODE_FLG
	 */
	public void setKMK_TRI_CODE_FLG(Long kMK_TRI_CODE_FLG) {
		this.kMK_TRI_CODE_FLG = kMK_TRI_CODE_FLG;
	}

	/**
	 * @return Long
	 */
	public Long getKMK_EMP_CODE_FLG() {
		return kMK_EMP_CODE_FLG;
	}

	/**
	 * @param kMK_EMP_CODE_FLG
	 */
	public void setKMK_EMP_CODE_FLG(Long kMK_EMP_CODE_FLG) {
		this.kMK_EMP_CODE_FLG = kMK_EMP_CODE_FLG;
	}

	/**
	 * @return Long
	 */
	public Long getKMK_KNR_FLG_1() {
		return kMK_KNR_FLG_1;
	}

	/**
	 * @param kMK_KNR_FLG_1
	 */
	public void setKMK_KNR_FLG_1(Long kMK_KNR_FLG_1) {
		this.kMK_KNR_FLG_1 = kMK_KNR_FLG_1;
	}

	/**
	 * @return Long
	 */
	public Long getKMK_KNR_FLG_2() {
		return kMK_KNR_FLG_2;
	}

	/**
	 * @param kMK_KNR_FLG_2
	 */
	public void setKMK_KNR_FLG_2(Long kMK_KNR_FLG_2) {
		this.kMK_KNR_FLG_2 = kMK_KNR_FLG_2;
	}

	/**
	 * @return Long
	 */
	public Long getKMK_KNR_FLG_3() {
		return kMK_KNR_FLG_3;
	}

	/**
	 * @param kMK_KNR_FLG_3
	 */
	public void setKMK_KNR_FLG_3(Long kMK_KNR_FLG_3) {
		this.kMK_KNR_FLG_3 = kMK_KNR_FLG_3;
	}

	/**
	 * @return Long
	 */
	public Long getKMK_KNR_FLG_4() {
		return kMK_KNR_FLG_4;
	}

	/**
	 * @param kMK_KNR_FLG_4
	 */
	public void setKMK_KNR_FLG_4(Long kMK_KNR_FLG_4) {
		this.kMK_KNR_FLG_4 = kMK_KNR_FLG_4;
	}

	/**
	 * @return Long
	 */
	public Long getKMK_KNR_FLG_5() {
		return kMK_KNR_FLG_5;
	}

	/**
	 * @param kMK_KNR_FLG_5
	 */
	public void setKMK_KNR_FLG_5(Long kMK_KNR_FLG_5) {
		this.kMK_KNR_FLG_5 = kMK_KNR_FLG_5;
	}

	/**
	 * @return Long
	 */
	public Long getKMK_KNR_FLG_6() {
		return kMK_KNR_FLG_6;
	}

	/**
	 * @param kMK_KNR_FLG_6
	 */
	public void setKMK_KNR_FLG_6(Long kMK_KNR_FLG_6) {
		this.kMK_KNR_FLG_6 = kMK_KNR_FLG_6;
	}

	/**
	 * @return Long
	 */
	public Long getKMK_HM_FLG_1() {
		return kMK_HM_FLG_1;
	}

	/**
	 * @param kMK_HM_FLG_1
	 */
	public void setKMK_HM_FLG_1(Long kMK_HM_FLG_1) {
		this.kMK_HM_FLG_1 = kMK_HM_FLG_1;
	}

	/**
	 * @return Long
	 */
	public Long getKMK_HM_FLG_2() {
		return kMK_HM_FLG_2;
	}

	/**
	 * @param kMK_HM_FLG_2
	 */
	public void setKMK_HM_FLG_2(Long kMK_HM_FLG_2) {
		this.kMK_HM_FLG_2 = kMK_HM_FLG_2;
	}

	/**
	 * @return Long
	 */
	public Long getKMK_HM_FLG_3() {
		return kMK_HM_FLG_3;
	}

	/**
	 * @param kMK_HM_FLG_3
	 */
	public void setKMK_HM_FLG_3(Long kMK_HM_FLG_3) {
		this.kMK_HM_FLG_3 = kMK_HM_FLG_3;
	}

	/**
	 * @return Long
	 */
	public String getHKM_NAME() {
		return hKM_NAME;
	}

	/**
	 * @param hKM_NAME
	 */
	public void setHKM_NAME(String hKM_NAME) {
		this.hKM_NAME = hKM_NAME;
	}

	/**
	 * @return Long
	 */
	public Long getHKM_UKM_KBN() {
		return hKM_UKM_KBN;
	}

	/**
	 * @param hKM_UKM_KBN
	 */
	public void setHKM_UKM_KBN(Long hKM_UKM_KBN) {
		this.hKM_UKM_KBN = hKM_UKM_KBN;
	}

	/**
	 * @return Long
	 */
	public Long getHKM_URI_ZEI_FLG() {
		return hKM_URI_ZEI_FLG;
	}

	/**
	 * @param hKM_URI_ZEI_FLG
	 */
	public void setHKM_URI_ZEI_FLG(Long hKM_URI_ZEI_FLG) {
		this.hKM_URI_ZEI_FLG = hKM_URI_ZEI_FLG;
	}

	/**
	 * @return Long
	 */
	public Long getHKM_SIR_ZEI_FLG() {
		return hKM_SIR_ZEI_FLG;
	}

	/**
	 * @param hKM_SIR_ZEI_FLG
	 */
	public void setHKM_SIR_ZEI_FLG(Long hKM_SIR_ZEI_FLG) {
		this.hKM_SIR_ZEI_FLG = hKM_SIR_ZEI_FLG;
	}

	/**
	 * @return Long
	 */
	public Long getHKM_TRI_CODE_FLG() {
		return hKM_TRI_CODE_FLG;
	}

	/**
	 * @param hKM_TRI_CODE_FLG
	 */
	public void setHKM_TRI_CODE_FLG(Long hKM_TRI_CODE_FLG) {
		this.hKM_TRI_CODE_FLG = hKM_TRI_CODE_FLG;
	}

	/**
	 * @return Long
	 */
	public Long getHKM_EMP_CODE_FLG() {
		return hKM_EMP_CODE_FLG;
	}

	/**
	 * @param hKM_EMP_CODE_FLG
	 */
	public void setHKM_EMP_CODE_FLG(Long hKM_EMP_CODE_FLG) {
		this.hKM_EMP_CODE_FLG = hKM_EMP_CODE_FLG;
	}

	/**
	 * @return Long
	 */
	public Long getHKM_KNR_FLG_1() {
		return hKM_KNR_FLG_1;
	}

	/**
	 * @param hKM_KNR_FLG_1
	 */
	public void setHKM_KNR_FLG_1(Long hKM_KNR_FLG_1) {
		this.hKM_KNR_FLG_1 = hKM_KNR_FLG_1;
	}

	/**
	 * @return Long
	 */
	public Long getHKM_KNR_FLG_2() {
		return hKM_KNR_FLG_2;
	}

	/**
	 * @param hKM_KNR_FLG_2
	 */
	public void setHKM_KNR_FLG_2(Long hKM_KNR_FLG_2) {
		this.hKM_KNR_FLG_2 = hKM_KNR_FLG_2;
	}

	/**
	 * @return Long
	 */
	public Long getHKM_KNR_FLG_3() {
		return hKM_KNR_FLG_3;
	}

	/**
	 * @param hKM_KNR_FLG_3
	 */
	public void setHKM_KNR_FLG_3(Long hKM_KNR_FLG_3) {
		this.hKM_KNR_FLG_3 = hKM_KNR_FLG_3;
	}

	/**
	 * @return Long
	 */
	public Long getHKM_KNR_FLG_4() {
		return hKM_KNR_FLG_4;
	}

	/**
	 * @param hKM_KNR_FLG_4
	 */
	public void setHKM_KNR_FLG_4(Long hKM_KNR_FLG_4) {
		this.hKM_KNR_FLG_4 = hKM_KNR_FLG_4;
	}

	/**
	 * @return Long
	 */
	public Long getHKM_KNR_FLG_5() {
		return hKM_KNR_FLG_5;
	}

	/**
	 * @param hKM_KNR_FLG_5
	 */
	public void setHKM_KNR_FLG_5(Long hKM_KNR_FLG_5) {
		this.hKM_KNR_FLG_5 = hKM_KNR_FLG_5;
	}

	/**
	 * @return Long
	 */
	public Long getHKM_KNR_FLG_6() {
		return hKM_KNR_FLG_6;
	}

	/**
	 * @param hKM_KNR_FLG_6
	 */
	public void setHKM_KNR_FLG_6(Long hKM_KNR_FLG_6) {
		this.hKM_KNR_FLG_6 = hKM_KNR_FLG_6;
	}

	/**
	 * @return Long
	 */
	public Long getHKM_HM_FLG_1() {
		return hKM_HM_FLG_1;
	}

	/**
	 * @param hKM_HM_FLG_1
	 */
	public void setHKM_HM_FLG_1(Long hKM_HM_FLG_1) {
		this.hKM_HM_FLG_1 = hKM_HM_FLG_1;
	}

	/**
	 * @return Long
	 */
	public Long getHKM_HM_FLG_2() {
		return hKM_HM_FLG_2;
	}

	/**
	 * @param hKM_HM_FLG_2
	 */
	public void setHKM_HM_FLG_2(Long hKM_HM_FLG_2) {
		this.hKM_HM_FLG_2 = hKM_HM_FLG_2;
	}

	/**
	 * @return Long
	 */
	public Long getHKM_HM_FLG_3() {
		return hKM_HM_FLG_3;
	}

	/**
	 * @param hKM_HM_FLG_3
	 */
	public void setHKM_HM_FLG_3(Long hKM_HM_FLG_3) {
		this.hKM_HM_FLG_3 = hKM_HM_FLG_3;
	}

	/**
	 * @return Long
	 */
	public String getUKM_NAME() {
		return uKM_NAME;
	}

	/**
	 * @param uKM_NAME
	 */
	public void setUKM_NAME(String uKM_NAME) {
		this.uKM_NAME = uKM_NAME;
	}

	/**
	 * @return Long
	 */
	public Long getUKM_URI_ZEI_FLG() {
		return uKM_URI_ZEI_FLG;
	}

	/**
	 * @param uKM_URI_ZEI_FLG
	 */
	public void setUKM_URI_ZEI_FLG(Long uKM_URI_ZEI_FLG) {
		this.uKM_URI_ZEI_FLG = uKM_URI_ZEI_FLG;
	}

	/**
	 * @return Long
	 */
	public Long getUKM_SIR_ZEI_FLG() {
		return uKM_SIR_ZEI_FLG;
	}

	/**
	 * @param uKM_SIR_ZEI_FLG
	 */
	public void setUKM_SIR_ZEI_FLG(Long uKM_SIR_ZEI_FLG) {
		this.uKM_SIR_ZEI_FLG = uKM_SIR_ZEI_FLG;
	}

	/**
	 * @return Long
	 */
	public Long getUKM_TRI_CODE_FLG() {
		return uKM_TRI_CODE_FLG;
	}

	/**
	 * @param uKM_TRI_CODE_FLG
	 */
	public void setUKM_TRI_CODE_FLG(Long uKM_TRI_CODE_FLG) {
		this.uKM_TRI_CODE_FLG = uKM_TRI_CODE_FLG;
	}

	/**
	 * @return Long
	 */
	public Long getUKM_EMP_CODE_FLG() {
		return uKM_EMP_CODE_FLG;
	}

	/**
	 * @param uKM_EMP_CODE_FLG
	 */
	public void setUKM_EMP_CODE_FLG(Long uKM_EMP_CODE_FLG) {
		this.uKM_EMP_CODE_FLG = uKM_EMP_CODE_FLG;
	}

	/**
	 * @return Long
	 */
	public Long getUKM_KNR_FLG_1() {
		return uKM_KNR_FLG_1;
	}

	/**
	 * @param uKM_KNR_FLG_1
	 */
	public void setUKM_KNR_FLG_1(Long uKM_KNR_FLG_1) {
		this.uKM_KNR_FLG_1 = uKM_KNR_FLG_1;
	}

	/**
	 * @return Long
	 */
	public Long getUKM_KNR_FLG_2() {
		return uKM_KNR_FLG_2;
	}

	/**
	 * @param uKM_KNR_FLG_2
	 */
	public void setUKM_KNR_FLG_2(Long uKM_KNR_FLG_2) {
		this.uKM_KNR_FLG_2 = uKM_KNR_FLG_2;
	}

	/**
	 * @return Long
	 */
	public Long getUKM_KNR_FLG_3() {
		return uKM_KNR_FLG_3;
	}

	/**
	 * @param uKM_KNR_FLG_3
	 */
	public void setUKM_KNR_FLG_3(Long uKM_KNR_FLG_3) {
		this.uKM_KNR_FLG_3 = uKM_KNR_FLG_3;
	}

	/**
	 * @return Long
	 */
	public Long getUKM_KNR_FLG_4() {
		return uKM_KNR_FLG_4;
	}

	/**
	 * @param uKM_KNR_FLG_4
	 */
	public void setUKM_KNR_FLG_4(Long uKM_KNR_FLG_4) {
		this.uKM_KNR_FLG_4 = uKM_KNR_FLG_4;
	}

	/**
	 * @return Long
	 */
	public Long getUKM_KNR_FLG_5() {
		return uKM_KNR_FLG_5;
	}

	/**
	 * @param uKM_KNR_FLG_5
	 */
	public void setUKM_KNR_FLG_5(Long uKM_KNR_FLG_5) {
		this.uKM_KNR_FLG_5 = uKM_KNR_FLG_5;
	}

	/**
	 * @return Long
	 */
	public Long getUKM_KNR_FLG_6() {
		return uKM_KNR_FLG_6;
	}

	/**
	 * @param uKM_KNR_FLG_6
	 */
	public void setUKM_KNR_FLG_6(Long uKM_KNR_FLG_6) {
		this.uKM_KNR_FLG_6 = uKM_KNR_FLG_6;
	}

	/**
	 * @return Long
	 */
	public Long getUKM_HM_FLG_1() {
		return uKM_HM_FLG_1;
	}

	/**
	 * @param uKM_HM_FLG_1
	 */
	public void setUKM_HM_FLG_1(Long uKM_HM_FLG_1) {
		this.uKM_HM_FLG_1 = uKM_HM_FLG_1;
	}

	/**
	 * @return Long
	 */
	public Long getUKM_HM_FLG_2() {
		return uKM_HM_FLG_2;
	}

	/**
	 * @param uKM_HM_FLG_2
	 */
	public void setUKM_HM_FLG_2(Long uKM_HM_FLG_2) {
		this.uKM_HM_FLG_2 = uKM_HM_FLG_2;
	}

	/**
	 * @return Long
	 */
	public Long getUKM_HM_FLG_3() {
		return uKM_HM_FLG_3;
	}

	/**
	 * @param uKM_HM_FLG_3
	 */
	public void setUKM_HM_FLG_3(Long uKM_HM_FLG_3) {
		this.uKM_HM_FLG_3 = uKM_HM_FLG_3;
	}

	/**
	 * @return String
	 */
	public String getZEI_ZEI_CODE() {
		return zEI_ZEI_CODE;
	}

	/**
	 * @param zEI_ZEI_CODE
	 */
	public void setZEI_ZEI_CODE(String zEI_ZEI_CODE) {
		this.zEI_ZEI_CODE = zEI_ZEI_CODE;
	}

	/**
	 * @return String
	 */
	public String getIRA_EMP_CODE() {
		return iRA_EMP_CODE;
	}

	/**
	 * @param iRA_EMP_CODE
	 */
	public void setIRA_EMP_CODE(String iRA_EMP_CODE) {
		this.iRA_EMP_CODE = iRA_EMP_CODE;
	}

	/**
	 * @return String
	 */
	public String getIDE_DEP_CODE() {
		return iDE_DEP_CODE;
	}

	/**
	 * @param iDE_DEP_CODE
	 */
	public void setIDE_DEP_CODE(String iDE_DEP_CODE) {
		this.iDE_DEP_CODE = iDE_DEP_CODE;
	}

	/**
	 * @return String
	 */
	public String getKEI_DEP_CODE() {
		return kEI_DEP_CODE;
	}

	/**
	 * @param kEI_DEP_CODE
	 */
	public void setKEI_DEP_CODE(String kEI_DEP_CODE) {
		this.kEI_DEP_CODE = kEI_DEP_CODE;
	}

	/**
	 * @return Long
	 */
	public Long getERR_FLG() {
		return eRR_FLG;
	}

	/**
	 * @param eRR_FLG
	 */
	public void setERR_FLG(Long eRR_FLG) {
		this.eRR_FLG = eRR_FLG;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/sWK_DEN_DATE=").append(sWK_DEN_DATE);
		buff.append("/sWK_DEN_NO=").append(sWK_DEN_NO);
		buff.append("/sWK_GYO_NO=").append(sWK_GYO_NO);
		buff.append("/sWK_UKE_DEP_CODE=").append(sWK_UKE_DEP_CODE);
		buff.append("/sWK_TEK_CODE=").append(sWK_TEK_CODE);
		buff.append("/sWK_TEK=").append(sWK_TEK);
		buff.append("/sWK_SYO_EMP_CODE=").append(sWK_SYO_EMP_CODE);
		buff.append("/sWK_SYO_DATE=").append(sWK_SYO_DATE);
		buff.append("/sWK_IRAI_EMP_CODE=").append(sWK_IRAI_EMP_CODE);
		buff.append("/sFR_IRAI_DEP_CODE=").append(sWK_IRAI_DEP_CODE);
		buff.append("/sWK_IRAI_DATE=").append(sWK_IRAI_DATE);
		buff.append("/sWK_SYS_KBN=").append(sWK_SYS_KBN);
		buff.append("/sWK_DEN_SYU=").append(sWK_DEN_SYU);
		buff.append("/sWK_UPD_KBN=").append(sWK_UPD_KBN);
		buff.append("/sWK_KSN_KBN=").append(sWK_KSN_KBN);
		buff.append("/sWK_KMK_CODE=").append(sWK_KMK_CODE);
		buff.append("/sWK_HKM_CODE=").append(sWK_HKM_CODE);
		buff.append("/sWK_UKM_CODE=").append(sWK_UKM_CODE);
		buff.append("/sWK_DEP_CODE=").append(sWK_DEP_CODE);
		buff.append("/sWK_TRI_CODE=").append(sWK_TRI_CODE);
		buff.append("/sWK_EMP_CODE=").append(sWK_EMP_CODE);
		buff.append("/sWK_CUR_CODE=").append(sWK_CUR_CODE);
		buff.append("/sWK_CUR_RATE=").append(sWK_CUR_RATE);
		buff.append("/sWK_DC_KBN=").append(sWK_DC_KBN);
		buff.append("/sWK_ZEI_KBN=").append(sWK_ZEI_KBN);
		buff.append("/sWK_ZEI_KIN=").append(sWK_ZEI_KIN);
		buff.append("/sWK_ZEI_CODE=").append(sWK_ZEI_CODE);
		buff.append("/sWK_GYO_TEK_CODE=").append(sWK_GYO_TEK_CODE);
		buff.append("/sWK_GYO_TEK=").append(sWK_GYO_TEK);
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
		buff.append("/hAS_DATE=").append(hAS_DATE);
		buff.append("/sWK_AT_KMK_CODE=").append(sWK_AT_KMK_CODE);
		buff.append("/sWK_AT_HKM_CODE=").append(sWK_AT_HKM_CODE);
		buff.append("/sWK_AT_UKM_CODE=").append(sWK_AT_UKM_CODE);
		buff.append("/sWK_AT_DEP_CODE=").append(sWK_AT_DEP_CODE);
		buff.append("/sWK_K_KAI_CODE=").append(sWK_K_KAI_CODE);
		buff.append("/sWK_SEI_NO=").append(sWK_SEI_NO);
		buff.append("/sWK_KIN=").append(sWK_KIN);
		buff.append("/sWK_IN_KIN=").append(sWK_IN_KIN);
		buff.append("/sWK_SIHA_KBN=").append(sWK_SIHA_KBN);
		buff.append("/sWK_SIHA_DATE_AP=").append(sWK_SIHA_DATE_AP);
		buff.append("/sWK_HOH_CODE_AP=").append(sWK_HOH_CODE_AP);
		buff.append("/sWK_HORYU_KBN=").append(sWK_HORYU_KBN);
		buff.append("/sWK_TJK_CODE=").append(sWK_TJK_CODE);
		buff.append("/sWK_AR_DATE=").append(sWK_AR_DATE);
		buff.append("/sWK_UKE_DATE=").append(sWK_UKE_DATE);
		buff.append("/sWK_CBK_CODE=").append(sWK_CBK_CODE);
		buff.append("/sWK_TUKE_KBN=").append(sWK_TUKE_KBN);
		buff.append("/uKE_DEP_NAME=").append(uKE_DEP_NAME);
		buff.append("/kMK_CODE=").append(kMK_CODE);
		buff.append("/kMK_HKM_KBN=").append(kMK_HKM_KBN);
		buff.append("/kMK_URI_ZEI_FLG=").append(kMK_URI_ZEI_FLG);
		buff.append("/kMK_SIR_ZEI_FLG=").append(kMK_SIR_ZEI_FLG);
		buff.append("/kMK_TRI_CODE_FLG=").append(kMK_TRI_CODE_FLG);
		buff.append("/kMK_EMP_CODE_FLG=").append(kMK_EMP_CODE_FLG);
		buff.append("/kMK_KNR_FLG_1=").append(kMK_KNR_FLG_1);
		buff.append("/kMK_KNR_FLG_2=").append(kMK_KNR_FLG_2);
		buff.append("/kMK_KNR_FLG_3=").append(kMK_KNR_FLG_3);
		buff.append("/kMK_KNR_FLG_4=").append(kMK_KNR_FLG_4);
		buff.append("/kMK_KNR_FLG_5=").append(kMK_KNR_FLG_5);
		buff.append("/kMK_KNR_FLG_6=").append(kMK_KNR_FLG_6);
		buff.append("/kMK_HM_FLG_1=").append(kMK_HM_FLG_1);
		buff.append("/kMK_HM_FLG_2=").append(kMK_HM_FLG_2);
		buff.append("/kMK_HM_FLG_3=").append(kMK_HM_FLG_3);
		buff.append("/hKM_NAME=").append(hKM_NAME);
		buff.append("/hKM_UKM_KBN=").append(hKM_UKM_KBN);
		buff.append("/hKM_URI_ZEI_FLG=").append(hKM_URI_ZEI_FLG);
		buff.append("/hKM_SIR_ZEI_FLG=").append(hKM_SIR_ZEI_FLG);
		buff.append("/hKM_TRI_CODE_FLG=").append(hKM_TRI_CODE_FLG);
		buff.append("/hKM_EMP_CODE_FLG=").append(hKM_EMP_CODE_FLG);
		buff.append("/hKM_KNR_FLG_1=").append(hKM_KNR_FLG_1);
		buff.append("/hKM_KNR_FLG_2=").append(hKM_KNR_FLG_2);
		buff.append("/hKM_KNR_FLG_3=").append(hKM_KNR_FLG_3);
		buff.append("/hKM_KNR_FLG_4=").append(hKM_KNR_FLG_4);
		buff.append("/hKM_KNR_FLG_5=").append(hKM_KNR_FLG_5);
		buff.append("/hKM_KNR_FLG_6=").append(hKM_KNR_FLG_6);
		buff.append("/hKM_HM_FLG_1=").append(hKM_HM_FLG_1);
		buff.append("/hKM_HM_FLG_2=").append(hKM_HM_FLG_2);
		buff.append("/hKM_HM_FLG_3=").append(hKM_HM_FLG_3);
		buff.append("/uKM_NAME=").append(uKM_NAME);
		buff.append("/uKM_URI_ZEI_FLG=").append(uKM_URI_ZEI_FLG);
		buff.append("/uKM_SIR_ZEI_FLG=").append(uKM_SIR_ZEI_FLG);
		buff.append("/uKM_TRI_CODE_FLG=").append(uKM_TRI_CODE_FLG);
		buff.append("/uKM_EMP_CODE_FLG=").append(uKM_EMP_CODE_FLG);
		buff.append("/uKM_KNR_FLG_1=").append(uKM_KNR_FLG_1);
		buff.append("/uKM_KNR_FLG_2=").append(uKM_KNR_FLG_2);
		buff.append("/uKM_KNR_FLG_3=").append(uKM_KNR_FLG_3);
		buff.append("/uKM_KNR_FLG_4=").append(uKM_KNR_FLG_4);
		buff.append("/uKM_KNR_FLG_5=").append(uKM_KNR_FLG_5);
		buff.append("/uKM_KNR_FLG_6=").append(uKM_KNR_FLG_6);
		buff.append("/uKM_HM_FLG_1=").append(uKM_HM_FLG_1);
		buff.append("/uKM_HM_FLG_2=").append(uKM_HM_FLG_2);
		buff.append("/uKM_HM_FLG_3=").append(uKM_HM_FLG_3);
		buff.append("/zEI_ZEI_CODE=").append(zEI_ZEI_CODE);
		buff.append("/iRA_EMP_CODE=").append(iRA_EMP_CODE);
		buff.append("/iDE_DEP_CODE=").append(iDE_DEP_CODE);
		buff.append("/kEI_DEP_CODE=").append(kEI_DEP_CODE);
		buff.append("/eRR_FLG=").append(eRR_FLG);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(sWK_DEN_DATE);
		list.add(sWK_DEN_NO);
		list.add(sWK_GYO_NO);
		list.add(sWK_UKE_DEP_CODE);
		list.add(sWK_TEK_CODE);
		list.add(sWK_TEK);
		list.add(sWK_SYO_EMP_CODE);
		list.add(sWK_SYO_DATE);
		list.add(sWK_IRAI_EMP_CODE);
		list.add(sWK_IRAI_DEP_CODE);
		list.add(sWK_IRAI_DATE);
		list.add(sWK_SYS_KBN);
		list.add(sWK_DEN_SYU);
		list.add(sWK_UPD_KBN);
		list.add(sWK_KSN_KBN);
		list.add(sWK_KMK_CODE);
		list.add(sWK_HKM_CODE);
		list.add(sWK_UKM_CODE);
		list.add(sWK_DEP_CODE);
		list.add(sWK_TRI_CODE);
		list.add(sWK_EMP_CODE);
		list.add(sWK_CUR_CODE);
		list.add(sWK_CUR_RATE);
		list.add(sWK_DC_KBN);
		list.add(sWK_ZEI_KBN);
		list.add(sWK_ZEI_KIN);
		list.add(sWK_ZEI_CODE);
		list.add(sWK_GYO_TEK_CODE);
		list.add(sWK_GYO_TEK);
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
		list.add(hAS_DATE);
		list.add(sWK_AT_KMK_CODE);
		list.add(sWK_AT_HKM_CODE);
		list.add(sWK_AT_UKM_CODE);
		list.add(sWK_AT_DEP_CODE);
		list.add(sWK_K_KAI_CODE);
		list.add(sWK_SEI_NO);
		list.add(sWK_KIN);
		list.add(sWK_IN_KIN);
		list.add(sWK_SIHA_KBN);
		list.add(sWK_SIHA_DATE_AP);
		list.add(sWK_HOH_CODE_AP);
		list.add(sWK_HORYU_KBN);
		list.add(sWK_TJK_CODE);
		list.add(sWK_AR_DATE);
		list.add(sWK_UKE_DATE);
		list.add(sWK_CBK_CODE);
		list.add(sWK_TUKE_KBN);
		list.add(uKE_DEP_NAME);
		list.add(kMK_CODE);
		list.add(kMK_HKM_KBN);
		list.add(kMK_URI_ZEI_FLG);
		list.add(kMK_SIR_ZEI_FLG);
		list.add(kMK_TRI_CODE_FLG);
		list.add(kMK_EMP_CODE_FLG);
		list.add(kMK_KNR_FLG_1);
		list.add(kMK_KNR_FLG_2);
		list.add(kMK_KNR_FLG_3);
		list.add(kMK_KNR_FLG_4);
		list.add(kMK_KNR_FLG_5);
		list.add(kMK_KNR_FLG_6);
		list.add(kMK_HM_FLG_1);
		list.add(kMK_HM_FLG_2);
		list.add(kMK_HM_FLG_3);
		list.add(hKM_NAME);
		list.add(hKM_UKM_KBN);
		list.add(hKM_URI_ZEI_FLG);
		list.add(hKM_SIR_ZEI_FLG);
		list.add(hKM_TRI_CODE_FLG);
		list.add(hKM_EMP_CODE_FLG);
		list.add(hKM_KNR_FLG_1);
		list.add(hKM_KNR_FLG_2);
		list.add(hKM_KNR_FLG_3);
		list.add(hKM_KNR_FLG_4);
		list.add(hKM_KNR_FLG_5);
		list.add(hKM_KNR_FLG_6);
		list.add(hKM_HM_FLG_1);
		list.add(hKM_HM_FLG_2);
		list.add(hKM_HM_FLG_3);
		list.add(uKM_NAME);
		list.add(uKM_URI_ZEI_FLG);
		list.add(uKM_SIR_ZEI_FLG);
		list.add(uKM_TRI_CODE_FLG);
		list.add(uKM_EMP_CODE_FLG);
		list.add(uKM_KNR_FLG_1);
		list.add(uKM_KNR_FLG_2);
		list.add(uKM_KNR_FLG_3);
		list.add(uKM_KNR_FLG_4);
		list.add(uKM_KNR_FLG_5);
		list.add(uKM_KNR_FLG_6);
		list.add(uKM_HM_FLG_1);
		list.add(uKM_HM_FLG_2);
		list.add(uKM_HM_FLG_3);
		list.add(zEI_ZEI_CODE);
		list.add(iRA_EMP_CODE);
		list.add(iDE_DEP_CODE);
		list.add(kEI_DEP_CODE);
		list.add(eRR_FLG);

		return list;
	}
}
