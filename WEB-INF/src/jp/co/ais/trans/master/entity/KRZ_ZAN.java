package jp.co.ais.trans.master.entity;

import java.math.*;
import java.util.*;

/**
 * 
 */
public class KRZ_ZAN extends MasterBase {

	/**  */
	public static final String TABLE = "KRZ_ZAN";

	private String kAI_CODE = "";

	private int kRZ_NENDO;

	private String kRZ_DEP_CODE = "";

	private String kRZ_KMK_CODE = "";

	private String kRZ_HKM_CODE;

	private String kRZ_UKM_CODE;

	private String kRZ_TRI_CODE;

	private String kRZ_EMP_CODE;

	private String kRZ_KNR_CODE_1;

	private String kRZ_KNR_CODE_2;

	private String kRZ_KNR_CODE_3;

	private String kRZ_KNR_CODE_4;

	private String kRZ_KNR_CODE_5;

	private String kRZ_KNR_CODE_6;

	private int kRZ_KSN_KBN;

	private String kRZ_CUR_CODE;

	private BigDecimal kRZ_STR_SUM;

	private BigDecimal kRZ_DR_1;

	private BigDecimal kRZ_CR_1;

	private BigDecimal kRZ_ZAN_1;

	private BigDecimal kRZ_DR_2;

	private BigDecimal kRZ_CR_2;

	private BigDecimal kRZ_ZAN_2;

	private BigDecimal kRZ_DR_3;

	private BigDecimal kRZ_CR_3;

	private BigDecimal kRZ_ZAN_3;

	private BigDecimal kRZ_DR_4;

	private BigDecimal kRZ_CR_4;

	private BigDecimal kRZ_ZAN_4;

	private BigDecimal kRZ_DR_5;

	private BigDecimal kRZ_CR_5;

	private BigDecimal kRZ_ZAN_5;

	private BigDecimal kRZ_DR_6;

	private BigDecimal kRZ_CR_6;

	private BigDecimal kRZ_ZAN_6;

	private BigDecimal kRZ_DR_7;

	private BigDecimal kRZ_CR_7;

	private BigDecimal kRZ_ZAN_7;

	private BigDecimal kRZ_DR_8;

	private BigDecimal kRZ_CR_8;

	private BigDecimal kRZ_ZAN_8;

	private BigDecimal kRZ_DR_9;

	private BigDecimal kRZ_CR_9;

	private BigDecimal kRZ_ZAN_9;

	private BigDecimal kRZ_DR_10;

	private BigDecimal kRZ_CR_10;

	private BigDecimal kRZ_ZAN_10;

	private BigDecimal kRZ_DR_11;

	private BigDecimal kRZ_CR_11;

	private BigDecimal kRZ_ZAN_11;

	private BigDecimal kRZ_DR_12;

	private BigDecimal kRZ_CR_12;

	private BigDecimal kRZ_ZAN_12;

	private String pRG_ID;

	private BigDecimal kRZ_STR_SUM_G;

	private BigDecimal kRZ_DR_G_1;

	private BigDecimal kRZ_CR_G_1;

	private BigDecimal kRZ_ZAN_G_1;

	private BigDecimal kRZ_DR_G_2;

	private BigDecimal kRZ_CR_G_2;

	private BigDecimal kRZ_ZAN_G_2;

	private BigDecimal kRZ_DR_G_3;

	private BigDecimal kRZ_CR_G_3;

	private BigDecimal kRZ_ZAN_G_3;

	private BigDecimal kRZ_DR_G_4;

	private BigDecimal kRZ_CR_G_4;

	private BigDecimal kRZ_ZAN_G_4;

	private BigDecimal kRZ_DR_G_5;

	private BigDecimal kRZ_CR_G_5;

	private BigDecimal kRZ_ZAN_G_5;

	private BigDecimal kRZ_DR_G_6;

	private BigDecimal kRZ_CR_G_6;

	private BigDecimal kRZ_ZAN_G_6;

	private BigDecimal kRZ_DR_G_7;

	private BigDecimal kRZ_CR_G_7;

	private BigDecimal kRZ_ZAN_G_7;

	private BigDecimal kRZ_DR_G_8;

	private BigDecimal kRZ_CR_G_8;

	private BigDecimal kRZ_ZAN_G_8;

	private BigDecimal kRZ_DR_G_9;

	private BigDecimal kRZ_CR_G_9;

	private BigDecimal kRZ_ZAN_G_9;

	private BigDecimal kRZ_DR_G_10;

	private BigDecimal kRZ_CR_G_10;

	private BigDecimal kRZ_ZAN_G_10;

	private BigDecimal kRZ_DR_G_11;

	private BigDecimal kRZ_CR_G_11;

	private BigDecimal kRZ_ZAN_G_11;

	private BigDecimal kRZ_DR_G_12;

	private BigDecimal kRZ_CR_G_12;

	private BigDecimal kRZ_ZAN_G_12;

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
	 * @return int
	 */
	public int getKRZ_NENDO() {
		return kRZ_NENDO;
	}

	/**
	 * @param kRZ_NENDO
	 */
	public void setKRZ_NENDO(int kRZ_NENDO) {
		this.kRZ_NENDO = kRZ_NENDO;
	}

	/**
	 * @return String
	 */
	public String getKRZ_DEP_CODE() {
		return kRZ_DEP_CODE;
	}

	/**
	 * @param kRZ_DEP_CODE
	 */
	public void setKRZ_DEP_CODE(String kRZ_DEP_CODE) {
		this.kRZ_DEP_CODE = kRZ_DEP_CODE;
	}

	/**
	 * @return String
	 */
	public String getKRZ_KMK_CODE() {
		return kRZ_KMK_CODE;
	}

	/**
	 * @param kRZ_KMK_CODE
	 */
	public void setKRZ_KMK_CODE(String kRZ_KMK_CODE) {
		this.kRZ_KMK_CODE = kRZ_KMK_CODE;
	}

	/**
	 * @return String
	 */
	public String getKRZ_HKM_CODE() {
		return kRZ_HKM_CODE;
	}

	/**
	 * @param kRZ_HKM_CODE
	 */
	public void setKRZ_HKM_CODE(String kRZ_HKM_CODE) {
		this.kRZ_HKM_CODE = kRZ_HKM_CODE;
	}

	/**
	 * @return String
	 */
	public String getKRZ_UKM_CODE() {
		return kRZ_UKM_CODE;
	}

	/**
	 * @param kRZ_UKM_CODE
	 */
	public void setKRZ_UKM_CODE(String kRZ_UKM_CODE) {
		this.kRZ_UKM_CODE = kRZ_UKM_CODE;
	}

	/**
	 * @return String
	 */
	public String getKRZ_TRI_CODE() {
		return kRZ_TRI_CODE;
	}

	/**
	 * @param kRZ_TRI_CODE
	 */
	public void setKRZ_TRI_CODE(String kRZ_TRI_CODE) {
		this.kRZ_TRI_CODE = kRZ_TRI_CODE;
	}

	/**
	 * @return String
	 */
	public String getKRZ_EMP_CODE() {
		return kRZ_EMP_CODE;
	}

	/**
	 * @param kRZ_EMP_CODE
	 */
	public void setKRZ_EMP_CODE(String kRZ_EMP_CODE) {
		this.kRZ_EMP_CODE = kRZ_EMP_CODE;
	}

	/**
	 * @return String
	 */
	public String getKRZ_KNR_CODE_1() {
		return kRZ_KNR_CODE_1;
	}

	/**
	 * @param kRZ_KNR_CODE_1
	 */
	public void setKRZ_KNR_CODE_1(String kRZ_KNR_CODE_1) {
		this.kRZ_KNR_CODE_1 = kRZ_KNR_CODE_1;
	}

	/**
	 * @return String
	 */
	public String getKRZ_KNR_CODE_2() {
		return kRZ_KNR_CODE_2;
	}

	/**
	 * @param kRZ_KNR_CODE_2
	 */
	public void setKRZ_KNR_CODE_2(String kRZ_KNR_CODE_2) {
		this.kRZ_KNR_CODE_2 = kRZ_KNR_CODE_2;
	}

	/**
	 * @return String
	 */
	public String getKRZ_KNR_CODE_3() {
		return kRZ_KNR_CODE_3;
	}

	/**
	 * @param kRZ_KNR_CODE_3
	 */
	public void setKRZ_KNR_CODE_3(String kRZ_KNR_CODE_3) {
		this.kRZ_KNR_CODE_3 = kRZ_KNR_CODE_3;
	}

	/**
	 * @return String
	 */
	public String getKRZ_KNR_CODE_4() {
		return kRZ_KNR_CODE_4;
	}

	/**
	 * @param kRZ_KNR_CODE_4
	 */
	public void setKRZ_KNR_CODE_4(String kRZ_KNR_CODE_4) {
		this.kRZ_KNR_CODE_4 = kRZ_KNR_CODE_4;
	}

	/**
	 * @return String
	 */
	public String getKRZ_KNR_CODE_5() {
		return kRZ_KNR_CODE_5;
	}

	/**
	 * @param kRZ_KNR_CODE_5
	 */
	public void setKRZ_KNR_CODE_5(String kRZ_KNR_CODE_5) {
		this.kRZ_KNR_CODE_5 = kRZ_KNR_CODE_5;
	}

	/**
	 * @return String
	 */
	public String getKRZ_KNR_CODE_6() {
		return kRZ_KNR_CODE_6;
	}

	/**
	 * @param kRZ_KNR_CODE_6
	 */
	public void setKRZ_KNR_CODE_6(String kRZ_KNR_CODE_6) {
		this.kRZ_KNR_CODE_6 = kRZ_KNR_CODE_6;
	}

	/**
	 * @return int
	 */
	public int getKRZ_KSN_KBN() {
		return kRZ_KSN_KBN;
	}

	/**
	 * @param kRZ_KSN_KBN
	 */
	public void setKRZ_KSN_KBN(int kRZ_KSN_KBN) {
		this.kRZ_KSN_KBN = kRZ_KSN_KBN;
	}

	/**
	 * @return String
	 */
	public String getKRZ_CUR_CODE() {
		return kRZ_CUR_CODE;
	}

	/**
	 * @param kRZ_CUR_CODE
	 */
	public void setKRZ_CUR_CODE(String kRZ_CUR_CODE) {
		this.kRZ_CUR_CODE = kRZ_CUR_CODE;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_STR_SUM() {
		return kRZ_STR_SUM;
	}

	/**
	 * @param kRZ_STR_SUM
	 */
	public void setKRZ_STR_SUM(BigDecimal kRZ_STR_SUM) {
		this.kRZ_STR_SUM = kRZ_STR_SUM;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_1() {
		return kRZ_DR_1;
	}

	/**
	 * @param kRZ_DR_1
	 */
	public void setKRZ_DR_1(BigDecimal kRZ_DR_1) {
		this.kRZ_DR_1 = kRZ_DR_1;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_1() {
		return kRZ_CR_1;
	}

	/**
	 * @param kRZ_CR_1
	 */
	public void setKRZ_CR_1(BigDecimal kRZ_CR_1) {
		this.kRZ_CR_1 = kRZ_CR_1;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_1() {
		return kRZ_ZAN_1;
	}

	/**
	 * @param kRZ_ZAN_1
	 */
	public void setKRZ_ZAN_1(BigDecimal kRZ_ZAN_1) {
		this.kRZ_ZAN_1 = kRZ_ZAN_1;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_2() {
		return kRZ_DR_2;
	}

	/**
	 * @param kRZ_DR_2
	 */
	public void setKRZ_DR_2(BigDecimal kRZ_DR_2) {
		this.kRZ_DR_2 = kRZ_DR_2;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_2() {
		return kRZ_CR_2;
	}

	/**
	 * @param kRZ_CR_2
	 */
	public void setKRZ_CR_2(BigDecimal kRZ_CR_2) {
		this.kRZ_CR_2 = kRZ_CR_2;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_2() {
		return kRZ_ZAN_2;
	}

	/**
	 * @param kRZ_ZAN_2
	 */
	public void setKRZ_ZAN_2(BigDecimal kRZ_ZAN_2) {
		this.kRZ_ZAN_2 = kRZ_ZAN_2;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_3() {
		return kRZ_DR_3;
	}

	/**
	 * @param kRZ_DR_3
	 */
	public void setKRZ_DR_3(BigDecimal kRZ_DR_3) {
		this.kRZ_DR_3 = kRZ_DR_3;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_3() {
		return kRZ_CR_3;
	}

	/**
	 * @param kRZ_CR_3
	 */
	public void setKRZ_CR_3(BigDecimal kRZ_CR_3) {
		this.kRZ_CR_3 = kRZ_CR_3;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_3() {
		return kRZ_ZAN_3;
	}

	/**
	 * @param kRZ_ZAN_3
	 */
	public void setKRZ_ZAN_3(BigDecimal kRZ_ZAN_3) {
		this.kRZ_ZAN_3 = kRZ_ZAN_3;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_4() {
		return kRZ_DR_4;
	}

	/**
	 * @param kRZ_DR_4
	 */
	public void setKRZ_DR_4(BigDecimal kRZ_DR_4) {
		this.kRZ_DR_4 = kRZ_DR_4;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_4() {
		return kRZ_CR_4;
	}

	/**
	 * @param kRZ_CR_4
	 */
	public void setKRZ_CR_4(BigDecimal kRZ_CR_4) {
		this.kRZ_CR_4 = kRZ_CR_4;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_4() {
		return kRZ_ZAN_4;
	}

	/**
	 * @param kRZ_ZAN_4
	 */
	public void setKRZ_ZAN_4(BigDecimal kRZ_ZAN_4) {
		this.kRZ_ZAN_4 = kRZ_ZAN_4;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_5() {
		return kRZ_DR_5;
	}

	/**
	 * @param kRZ_DR_5
	 */
	public void setKRZ_DR_5(BigDecimal kRZ_DR_5) {
		this.kRZ_DR_5 = kRZ_DR_5;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_5() {
		return kRZ_CR_5;
	}

	/**
	 * @param kRZ_CR_5
	 */
	public void setKRZ_CR_5(BigDecimal kRZ_CR_5) {
		this.kRZ_CR_5 = kRZ_CR_5;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_5() {
		return kRZ_ZAN_5;
	}

	/**
	 * @param kRZ_ZAN_5
	 */
	public void setKRZ_ZAN_5(BigDecimal kRZ_ZAN_5) {
		this.kRZ_ZAN_5 = kRZ_ZAN_5;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_6() {
		return kRZ_DR_6;
	}

	/**
	 * @param kRZ_DR_6
	 */
	public void setKRZ_DR_6(BigDecimal kRZ_DR_6) {
		this.kRZ_DR_6 = kRZ_DR_6;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_6() {
		return kRZ_CR_6;
	}

	/**
	 * @param kRZ_CR_6
	 */
	public void setKRZ_CR_6(BigDecimal kRZ_CR_6) {
		this.kRZ_CR_6 = kRZ_CR_6;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_6() {
		return kRZ_ZAN_6;
	}

	/**
	 * @param kRZ_ZAN_6
	 */
	public void setKRZ_ZAN_6(BigDecimal kRZ_ZAN_6) {
		this.kRZ_ZAN_6 = kRZ_ZAN_6;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_7() {
		return kRZ_DR_7;
	}

	/**
	 * @param kRZ_DR_7
	 */
	public void setKRZ_DR_7(BigDecimal kRZ_DR_7) {
		this.kRZ_DR_7 = kRZ_DR_7;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_7() {
		return kRZ_CR_7;
	}

	/**
	 * @param kRZ_CR_7
	 */
	public void setKRZ_CR_7(BigDecimal kRZ_CR_7) {
		this.kRZ_CR_7 = kRZ_CR_7;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_7() {
		return kRZ_ZAN_7;
	}

	/**
	 * @param kRZ_ZAN_7
	 */
	public void setKRZ_ZAN_7(BigDecimal kRZ_ZAN_7) {
		this.kRZ_ZAN_7 = kRZ_ZAN_7;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_8() {
		return kRZ_DR_8;
	}

	/**
	 * @param kRZ_DR_8
	 */
	public void setKRZ_DR_8(BigDecimal kRZ_DR_8) {
		this.kRZ_DR_8 = kRZ_DR_8;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_8() {
		return kRZ_CR_8;
	}

	/**
	 * @param kRZ_CR_8
	 */
	public void setKRZ_CR_8(BigDecimal kRZ_CR_8) {
		this.kRZ_CR_8 = kRZ_CR_8;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_8() {
		return kRZ_ZAN_8;
	}

	/**
	 * @param kRZ_ZAN_8
	 */
	public void setKRZ_ZAN_8(BigDecimal kRZ_ZAN_8) {
		this.kRZ_ZAN_8 = kRZ_ZAN_8;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_9() {
		return kRZ_DR_9;
	}

	/**
	 * @param kRZ_DR_9
	 */
	public void setKRZ_DR_9(BigDecimal kRZ_DR_9) {
		this.kRZ_DR_9 = kRZ_DR_9;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_9() {
		return kRZ_CR_9;
	}

	/**
	 * @param kRZ_CR_9
	 */
	public void setKRZ_CR_9(BigDecimal kRZ_CR_9) {
		this.kRZ_CR_9 = kRZ_CR_9;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_9() {
		return kRZ_ZAN_9;
	}

	/**
	 * @param kRZ_ZAN_9
	 */
	public void setKRZ_ZAN_9(BigDecimal kRZ_ZAN_9) {
		this.kRZ_ZAN_9 = kRZ_ZAN_9;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_10() {
		return kRZ_DR_10;
	}

	/**
	 * @param kRZ_DR_10
	 */
	public void setKRZ_DR_10(BigDecimal kRZ_DR_10) {
		this.kRZ_DR_10 = kRZ_DR_10;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_10() {
		return kRZ_CR_10;
	}

	/**
	 * @param kRZ_CR_10
	 */
	public void setKRZ_CR_10(BigDecimal kRZ_CR_10) {
		this.kRZ_CR_10 = kRZ_CR_10;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_10() {
		return kRZ_ZAN_10;
	}

	/**
	 * @param kRZ_ZAN_10
	 */
	public void setKRZ_ZAN_10(BigDecimal kRZ_ZAN_10) {
		this.kRZ_ZAN_10 = kRZ_ZAN_10;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_11() {
		return kRZ_DR_11;
	}

	/**
	 * @param kRZ_DR_11
	 */
	public void setKRZ_DR_11(BigDecimal kRZ_DR_11) {
		this.kRZ_DR_11 = kRZ_DR_11;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_11() {
		return kRZ_CR_11;
	}

	/**
	 * @param kRZ_CR_11
	 */
	public void setKRZ_CR_11(BigDecimal kRZ_CR_11) {
		this.kRZ_CR_11 = kRZ_CR_11;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_11() {
		return kRZ_ZAN_11;
	}

	/**
	 * @param kRZ_ZAN_11
	 */
	public void setKRZ_ZAN_11(BigDecimal kRZ_ZAN_11) {
		this.kRZ_ZAN_11 = kRZ_ZAN_11;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_12() {
		return kRZ_DR_12;
	}

	/**
	 * @param kRZ_DR_12
	 */
	public void setKRZ_DR_12(BigDecimal kRZ_DR_12) {
		this.kRZ_DR_12 = kRZ_DR_12;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_12() {
		return kRZ_CR_12;
	}

	/**
	 * @param kRZ_CR_12
	 */
	public void setKRZ_CR_12(BigDecimal kRZ_CR_12) {
		this.kRZ_CR_12 = kRZ_CR_12;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_12() {
		return kRZ_ZAN_12;
	}

	/**
	 * @param kRZ_ZAN_12
	 */
	public void setKRZ_ZAN_12(BigDecimal kRZ_ZAN_12) {
		this.kRZ_ZAN_12 = kRZ_ZAN_12;
	}

	/**
	 * @return BigDecimal
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
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_STR_SUM_G() {
		return kRZ_STR_SUM_G;
	}

	/**
	 * @param kRZ_STR_SUM_G
	 */
	public void setKRZ_STR_SUM_G(BigDecimal kRZ_STR_SUM_G) {
		this.kRZ_STR_SUM_G = kRZ_STR_SUM_G;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_G_1() {
		return kRZ_DR_G_1;
	}

	/**
	 * @param kRZ_DR_G_1
	 */
	public void setKRZ_DR_G_1(BigDecimal kRZ_DR_G_1) {
		this.kRZ_DR_G_1 = kRZ_DR_G_1;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_G_1() {
		return kRZ_CR_G_1;
	}

	/**
	 * @param kRZ_CR_G_1
	 */
	public void setKRZ_CR_G_1(BigDecimal kRZ_CR_G_1) {
		this.kRZ_CR_G_1 = kRZ_CR_G_1;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_G_1() {
		return kRZ_ZAN_G_1;
	}

	/**
	 * @param kRZ_ZAN_G_1
	 */
	public void setKRZ_ZAN_G_1(BigDecimal kRZ_ZAN_G_1) {
		this.kRZ_ZAN_G_1 = kRZ_ZAN_G_1;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_G_2() {
		return kRZ_DR_G_2;
	}

	/**
	 * @param kRZ_DR_G_2
	 */
	public void setKRZ_DR_G_2(BigDecimal kRZ_DR_G_2) {
		this.kRZ_DR_G_2 = kRZ_DR_G_2;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_G_2() {
		return kRZ_CR_G_2;
	}

	/**
	 * @param kRZ_CR_G_2
	 */
	public void setKRZ_CR_G_2(BigDecimal kRZ_CR_G_2) {
		this.kRZ_CR_G_2 = kRZ_CR_G_2;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_G_2() {
		return kRZ_ZAN_G_2;
	}

	/**
	 * @param kRZ_ZAN_G_2
	 */
	public void setKRZ_ZAN_G_2(BigDecimal kRZ_ZAN_G_2) {
		this.kRZ_ZAN_G_2 = kRZ_ZAN_G_2;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_G_3() {
		return kRZ_DR_G_3;
	}

	/**
	 * @param kRZ_DR_G_3
	 */
	public void setKRZ_DR_G_3(BigDecimal kRZ_DR_G_3) {
		this.kRZ_DR_G_3 = kRZ_DR_G_3;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_G_3() {
		return kRZ_CR_G_3;
	}

	/**
	 * @param kRZ_CR_G_3
	 */
	public void setKRZ_CR_G_3(BigDecimal kRZ_CR_G_3) {
		this.kRZ_CR_G_3 = kRZ_CR_G_3;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_G_3() {
		return kRZ_ZAN_G_3;
	}

	/**
	 * @param kRZ_ZAN_G_3
	 */
	public void setKRZ_ZAN_G_3(BigDecimal kRZ_ZAN_G_3) {
		this.kRZ_ZAN_G_3 = kRZ_ZAN_G_3;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_G_4() {
		return kRZ_DR_G_4;
	}

	/**
	 * @param kRZ_DR_G_4
	 */
	public void setKRZ_DR_G_4(BigDecimal kRZ_DR_G_4) {
		this.kRZ_DR_G_4 = kRZ_DR_G_4;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_G_4() {
		return kRZ_CR_G_4;
	}

	/**
	 * @param kRZ_CR_G_4
	 */
	public void setKRZ_CR_G_4(BigDecimal kRZ_CR_G_4) {
		this.kRZ_CR_G_4 = kRZ_CR_G_4;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_G_4() {
		return kRZ_ZAN_G_4;
	}

	/**
	 * @param kRZ_ZAN_G_4
	 */
	public void setKRZ_ZAN_G_4(BigDecimal kRZ_ZAN_G_4) {
		this.kRZ_ZAN_G_4 = kRZ_ZAN_G_4;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_G_5() {
		return kRZ_DR_G_5;
	}

	/**
	 * @param kRZ_DR_G_5
	 */
	public void setKRZ_DR_G_5(BigDecimal kRZ_DR_G_5) {
		this.kRZ_DR_G_5 = kRZ_DR_G_5;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_G_5() {
		return kRZ_CR_G_5;
	}

	/**
	 * @param kRZ_CR_G_5
	 */
	public void setKRZ_CR_G_5(BigDecimal kRZ_CR_G_5) {
		this.kRZ_CR_G_5 = kRZ_CR_G_5;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_G_5() {
		return kRZ_ZAN_G_5;
	}

	/**
	 * @param kRZ_ZAN_G_5
	 */
	public void setKRZ_ZAN_G_5(BigDecimal kRZ_ZAN_G_5) {
		this.kRZ_ZAN_G_5 = kRZ_ZAN_G_5;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_G_6() {
		return kRZ_DR_G_6;
	}

	/**
	 * @param kRZ_DR_G_6
	 */
	public void setKRZ_DR_G_6(BigDecimal kRZ_DR_G_6) {
		this.kRZ_DR_G_6 = kRZ_DR_G_6;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_G_6() {
		return kRZ_CR_G_6;
	}

	/**
	 * @param kRZ_CR_G_6
	 */
	public void setKRZ_CR_G_6(BigDecimal kRZ_CR_G_6) {
		this.kRZ_CR_G_6 = kRZ_CR_G_6;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_G_6() {
		return kRZ_ZAN_G_6;
	}

	/**
	 * @param kRZ_ZAN_G_6
	 */
	public void setKRZ_ZAN_G_6(BigDecimal kRZ_ZAN_G_6) {
		this.kRZ_ZAN_G_6 = kRZ_ZAN_G_6;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_G_7() {
		return kRZ_DR_G_7;
	}

	/**
	 * @param kRZ_DR_G_7
	 */
	public void setKRZ_DR_G_7(BigDecimal kRZ_DR_G_7) {
		this.kRZ_DR_G_7 = kRZ_DR_G_7;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_G_7() {
		return kRZ_CR_G_7;
	}

	/**
	 * @param kRZ_CR_G_7
	 */
	public void setKRZ_CR_G_7(BigDecimal kRZ_CR_G_7) {
		this.kRZ_CR_G_7 = kRZ_CR_G_7;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_G_7() {
		return kRZ_ZAN_G_7;
	}

	/**
	 * @param kRZ_ZAN_G_7
	 */
	public void setKRZ_ZAN_G_7(BigDecimal kRZ_ZAN_G_7) {
		this.kRZ_ZAN_G_7 = kRZ_ZAN_G_7;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_G_8() {
		return kRZ_DR_G_8;
	}

	/**
	 * @param kRZ_DR_G_8
	 */
	public void setKRZ_DR_G_8(BigDecimal kRZ_DR_G_8) {
		this.kRZ_DR_G_8 = kRZ_DR_G_8;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_G_8() {
		return kRZ_CR_G_8;
	}

	/**
	 * @param kRZ_CR_G_8
	 */
	public void setKRZ_CR_G_8(BigDecimal kRZ_CR_G_8) {
		this.kRZ_CR_G_8 = kRZ_CR_G_8;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_G_8() {
		return kRZ_ZAN_G_8;
	}

	/**
	 * @param kRZ_ZAN_G_8
	 */
	public void setKRZ_ZAN_G_8(BigDecimal kRZ_ZAN_G_8) {
		this.kRZ_ZAN_G_8 = kRZ_ZAN_G_8;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_G_9() {
		return kRZ_DR_G_9;
	}

	/**
	 * @param kRZ_DR_G_9
	 */
	public void setKRZ_DR_G_9(BigDecimal kRZ_DR_G_9) {
		this.kRZ_DR_G_9 = kRZ_DR_G_9;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_G_9() {
		return kRZ_CR_G_9;
	}

	/**
	 * @param kRZ_CR_G_9
	 */
	public void setKRZ_CR_G_9(BigDecimal kRZ_CR_G_9) {
		this.kRZ_CR_G_9 = kRZ_CR_G_9;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_G_9() {
		return kRZ_ZAN_G_9;
	}

	/**
	 * @param kRZ_ZAN_G_9
	 */
	public void setKRZ_ZAN_G_9(BigDecimal kRZ_ZAN_G_9) {
		this.kRZ_ZAN_G_9 = kRZ_ZAN_G_9;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_G_10() {
		return kRZ_DR_G_10;
	}

	/**
	 * @param kRZ_DR_G_10
	 */
	public void setKRZ_DR_G_10(BigDecimal kRZ_DR_G_10) {
		this.kRZ_DR_G_10 = kRZ_DR_G_10;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_G_10() {
		return kRZ_CR_G_10;
	}

	/**
	 * @param kRZ_CR_G_10
	 */
	public void setKRZ_CR_G_10(BigDecimal kRZ_CR_G_10) {
		this.kRZ_CR_G_10 = kRZ_CR_G_10;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_G_10() {
		return kRZ_ZAN_G_10;
	}

	/**
	 * @param kRZ_ZAN_G_10
	 */
	public void setKRZ_ZAN_G_10(BigDecimal kRZ_ZAN_G_10) {
		this.kRZ_ZAN_G_10 = kRZ_ZAN_G_10;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_G_11() {
		return kRZ_DR_G_11;
	}

	/**
	 * @param kRZ_DR_G_11
	 */
	public void setKRZ_DR_G_11(BigDecimal kRZ_DR_G_11) {
		this.kRZ_DR_G_11 = kRZ_DR_G_11;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_G_11() {
		return kRZ_CR_G_11;
	}

	/**
	 * @param kRZ_CR_G_11
	 */
	public void setKRZ_CR_G_11(BigDecimal kRZ_CR_G_11) {
		this.kRZ_CR_G_11 = kRZ_CR_G_11;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_G_11() {
		return kRZ_ZAN_G_11;
	}

	/**
	 * @param kRZ_ZAN_G_11
	 */
	public void setKRZ_ZAN_G_11(BigDecimal kRZ_ZAN_G_11) {
		this.kRZ_ZAN_G_11 = kRZ_ZAN_G_11;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_DR_G_12() {
		return kRZ_DR_G_12;
	}

	/**
	 * @param kRZ_DR_G_12
	 */
	public void setKRZ_DR_G_12(BigDecimal kRZ_DR_G_12) {
		this.kRZ_DR_G_12 = kRZ_DR_G_12;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_CR_G_12() {
		return kRZ_CR_G_12;
	}

	/**
	 * @param kRZ_CR_G_12
	 */
	public void setKRZ_CR_G_12(BigDecimal kRZ_CR_G_12) {
		this.kRZ_CR_G_12 = kRZ_CR_G_12;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getKRZ_ZAN_G_12() {
		return kRZ_ZAN_G_12;
	}

	/**
	 * @param kRZ_ZAN_G_12
	 */
	public void setKRZ_ZAN_G_12(BigDecimal kRZ_ZAN_G_12) {
		this.kRZ_ZAN_G_12 = kRZ_ZAN_G_12;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/kRZ_NENDO=").append(kRZ_NENDO);
		buff.append("/kRZ_DEP_CODE=").append(kRZ_DEP_CODE);
		buff.append("/kRZ_KMK_CODE=").append(kRZ_KMK_CODE);
		buff.append("/kRZ_HKM_CODE=").append(kRZ_HKM_CODE);
		buff.append("/kRZ_UKM_CODE=").append(kRZ_UKM_CODE);
		buff.append("/kRZ_TRI_CODE=").append(kRZ_TRI_CODE);
		buff.append("/kRZ_EMP_CODE=").append(kRZ_EMP_CODE);
		buff.append("/kRZ_KNR_CODE_1=").append(kRZ_KNR_CODE_1);
		buff.append("/kRZ_KNR_CODE_2=").append(kRZ_KNR_CODE_2);
		buff.append("/kRZ_KNR_CODE_3=").append(kRZ_KNR_CODE_3);
		buff.append("/kRZ_KNR_CODE_4=").append(kRZ_KNR_CODE_4);
		buff.append("/kRZ_KNR_CODE_5=").append(kRZ_KNR_CODE_5);
		buff.append("/kRZ_KNR_CODE_6=").append(kRZ_KNR_CODE_6);
		buff.append("/kRZ_KSN_KBN=").append(kRZ_KSN_KBN);
		buff.append("/kRZ_CUR_CODE=").append(kRZ_CUR_CODE);
		buff.append("/kRZ_STR_SUM=").append(kRZ_STR_SUM);
		buff.append("/kRZ_DR_1=").append(kRZ_DR_1);
		buff.append("/kRZ_CR_1=").append(kRZ_CR_1);
		buff.append("/kRZ_ZAN_1=").append(kRZ_ZAN_1);
		buff.append("/kRZ_DR_2=").append(kRZ_DR_2);
		buff.append("/kRZ_CR_2=").append(kRZ_CR_2);
		buff.append("/kRZ_ZAN_2=").append(kRZ_ZAN_2);
		buff.append("/kRZ_DR_3=").append(kRZ_DR_3);
		buff.append("/kRZ_CR_3=").append(kRZ_CR_3);
		buff.append("/kRZ_ZAN_3=").append(kRZ_ZAN_3);
		buff.append("/kRZ_DR_4=").append(kRZ_DR_4);
		buff.append("/kRZ_CR_4=").append(kRZ_CR_4);
		buff.append("/kRZ_ZAN_4=").append(kRZ_ZAN_4);
		buff.append("/kRZ_DR_5=").append(kRZ_DR_5);
		buff.append("/kRZ_CR_5=").append(kRZ_CR_5);
		buff.append("/kRZ_ZAN_5=").append(kRZ_ZAN_5);
		buff.append("/kRZ_DR_6=").append(kRZ_DR_6);
		buff.append("/kRZ_CR_6=").append(kRZ_CR_6);
		buff.append("/kRZ_ZAN_6=").append(kRZ_ZAN_6);
		buff.append("/kRZ_DR_7=").append(kRZ_DR_7);
		buff.append("/kRZ_CR_7=").append(kRZ_CR_7);
		buff.append("/kRZ_ZAN_7=").append(kRZ_ZAN_7);
		buff.append("/kRZ_DR_8=").append(kRZ_DR_8);
		buff.append("/kRZ_CR_8=").append(kRZ_CR_8);
		buff.append("/kRZ_ZAN_8=").append(kRZ_ZAN_8);
		buff.append("/kRZ_DR_9=").append(kRZ_DR_9);
		buff.append("/kRZ_CR_9=").append(kRZ_CR_9);
		buff.append("/kRZ_ZAN_9=").append(kRZ_ZAN_9);
		buff.append("/kRZ_DR_10=").append(kRZ_DR_10);
		buff.append("/kRZ_CR_10=").append(kRZ_CR_10);
		buff.append("/kRZ_ZAN_10=").append(kRZ_ZAN_10);
		buff.append("/kRZ_DR_11=").append(kRZ_DR_11);
		buff.append("/kRZ_CR_11=").append(kRZ_CR_11);
		buff.append("/kRZ_ZAN_11=").append(kRZ_ZAN_11);
		buff.append("/kRZ_DR_12=").append(kRZ_DR_12);
		buff.append("/kRZ_CR_12=").append(kRZ_CR_12);
		buff.append("/kRZ_ZAN_12=").append(kRZ_ZAN_12);
		buff.append("/iNP_DATE=").append(iNP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("/kRZ_STR_SUM_G=").append(kRZ_STR_SUM_G);
		buff.append("/kRZ_DR_G_1=").append(kRZ_DR_G_1);
		buff.append("/kRZ_CR_G_1=").append(kRZ_CR_G_1);
		buff.append("/kRZ_ZAN_G_1=").append(kRZ_ZAN_G_1);
		buff.append("/kRZ_DR_G_2=").append(kRZ_DR_G_2);
		buff.append("/kRZ_CR_G_2=").append(kRZ_CR_G_2);
		buff.append("/kRZ_ZAN_G_2=").append(kRZ_ZAN_G_2);
		buff.append("/kRZ_DR_G_3=").append(kRZ_DR_G_3);
		buff.append("/kRZ_CR_G_3=").append(kRZ_CR_G_3);
		buff.append("/kRZ_ZAN_G_3=").append(kRZ_ZAN_G_3);
		buff.append("/kRZ_DR_G_4=").append(kRZ_DR_G_4);
		buff.append("/kRZ_CR_G_4=").append(kRZ_CR_G_4);
		buff.append("/kRZ_ZAN_G_4=").append(kRZ_ZAN_G_4);
		buff.append("/kRZ_DR_G_5=").append(kRZ_DR_G_5);
		buff.append("/kRZ_CR_G_5=").append(kRZ_CR_G_5);
		buff.append("/kRZ_ZAN_G_5=").append(kRZ_ZAN_G_5);
		buff.append("/kRZ_DR_G_6=").append(kRZ_DR_G_6);
		buff.append("/kRZ_CR_G_6=").append(kRZ_CR_G_6);
		buff.append("/kRZ_ZAN_G_6=").append(kRZ_ZAN_G_6);
		buff.append("/kRZ_DR_G_7=").append(kRZ_DR_G_7);
		buff.append("/kRZ_CR_G_7=").append(kRZ_CR_G_7);
		buff.append("/kRZ_ZAN_G_7=").append(kRZ_ZAN_G_7);
		buff.append("/kRZ_DR_G_8=").append(kRZ_DR_G_8);
		buff.append("/kRZ_CR_G_8=").append(kRZ_CR_G_8);
		buff.append("/kRZ_ZAN_G_8=").append(kRZ_ZAN_G_8);
		buff.append("/kRZ_DR_G_9=").append(kRZ_DR_G_9);
		buff.append("/kRZ_CR_G_9=").append(kRZ_CR_G_9);
		buff.append("/kRZ_ZAN_G_9=").append(kRZ_ZAN_G_9);
		buff.append("/kRZ_DR_G_10=").append(kRZ_DR_G_10);
		buff.append("/kRZ_CR_G_10=").append(kRZ_CR_G_10);
		buff.append("/kRZ_ZAN_G_10=").append(kRZ_ZAN_G_10);
		buff.append("/kRZ_DR_G_11=").append(kRZ_DR_G_11);
		buff.append("/kRZ_CR_G_11=").append(kRZ_CR_G_11);
		buff.append("/kRZ_ZAN_G_11=").append(kRZ_ZAN_G_11);
		buff.append("/kRZ_DR_G_12=").append(kRZ_DR_G_12);
		buff.append("/kRZ_CR_G_12=").append(kRZ_CR_G_12);
		buff.append("/kRZ_ZAN_G_12=").append(kRZ_ZAN_G_12);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(kRZ_NENDO);
		list.add(kRZ_DEP_CODE);
		list.add(kRZ_KMK_CODE);
		list.add(kRZ_HKM_CODE);
		list.add(kRZ_UKM_CODE);
		list.add(kRZ_TRI_CODE);
		list.add(kRZ_EMP_CODE);
		list.add(kRZ_KNR_CODE_1);
		list.add(kRZ_KNR_CODE_2);
		list.add(kRZ_KNR_CODE_3);
		list.add(kRZ_KNR_CODE_4);
		list.add(kRZ_KNR_CODE_5);
		list.add(kRZ_KNR_CODE_6);
		list.add(kRZ_KSN_KBN);
		list.add(kRZ_CUR_CODE);
		list.add(kRZ_STR_SUM);
		list.add(kRZ_DR_1);
		list.add(kRZ_CR_1);
		list.add(kRZ_ZAN_1);
		list.add(kRZ_DR_2);
		list.add(kRZ_CR_2);
		list.add(kRZ_ZAN_2);
		list.add(kRZ_DR_3);
		list.add(kRZ_CR_3);
		list.add(kRZ_ZAN_3);
		list.add(kRZ_DR_4);
		list.add(kRZ_CR_4);
		list.add(kRZ_ZAN_4);
		list.add(kRZ_DR_5);
		list.add(kRZ_CR_5);
		list.add(kRZ_ZAN_5);
		list.add(kRZ_DR_6);
		list.add(kRZ_CR_6);
		list.add(kRZ_ZAN_6);
		list.add(kRZ_DR_7);
		list.add(kRZ_CR_7);
		list.add(kRZ_ZAN_7);
		list.add(kRZ_DR_8);
		list.add(kRZ_CR_8);
		list.add(kRZ_ZAN_8);
		list.add(kRZ_DR_9);
		list.add(kRZ_CR_9);
		list.add(kRZ_ZAN_9);
		list.add(kRZ_DR_10);
		list.add(kRZ_CR_10);
		list.add(kRZ_ZAN_10);
		list.add(kRZ_DR_11);
		list.add(kRZ_CR_11);
		list.add(kRZ_ZAN_11);
		list.add(kRZ_DR_12);
		list.add(kRZ_CR_12);
		list.add(kRZ_ZAN_12);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);
		list.add(kRZ_STR_SUM_G);
		list.add(kRZ_DR_G_1);
		list.add(kRZ_CR_G_1);
		list.add(kRZ_ZAN_G_1);
		list.add(kRZ_DR_G_2);
		list.add(kRZ_CR_G_2);
		list.add(kRZ_ZAN_G_2);
		list.add(kRZ_DR_G_3);
		list.add(kRZ_CR_G_3);
		list.add(kRZ_ZAN_G_3);
		list.add(kRZ_DR_G_4);
		list.add(kRZ_CR_G_4);
		list.add(kRZ_ZAN_G_4);
		list.add(kRZ_DR_G_5);
		list.add(kRZ_CR_G_5);
		list.add(kRZ_ZAN_G_5);
		list.add(kRZ_DR_G_6);
		list.add(kRZ_CR_G_6);
		list.add(kRZ_ZAN_G_6);
		list.add(kRZ_DR_G_7);
		list.add(kRZ_CR_G_7);
		list.add(kRZ_ZAN_G_7);
		list.add(kRZ_DR_G_8);
		list.add(kRZ_CR_G_8);
		list.add(kRZ_ZAN_G_8);
		list.add(kRZ_DR_G_9);
		list.add(kRZ_CR_G_9);
		list.add(kRZ_ZAN_G_9);
		list.add(kRZ_DR_G_10);
		list.add(kRZ_CR_G_10);
		list.add(kRZ_ZAN_G_10);
		list.add(kRZ_DR_G_11);
		list.add(kRZ_CR_G_11);
		list.add(kRZ_ZAN_G_11);
		list.add(kRZ_DR_G_12);
		list.add(kRZ_CR_G_12);
		list.add(kRZ_ZAN_G_12);

		return list;
	}
}
