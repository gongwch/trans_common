package jp.co.ais.trans.master.entity;

import java.math.BigDecimal;
import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * APédñÛÉwÉbÉ_
 */
public class AP_SWK_HDR extends MasterBase implements SWK_HDR, Cloneable {

	/**  */
	public static final String TABLE = "AP_SWK_HDR";

	private String kAI_CODE = "";

	private Date sWK_DEN_DATE;

	private String sWK_DEN_NO = "";

	private String sWK_KMK_CODE;

	private String sWK_HKM_CODE;

	private String sWK_UKM_CODE;

	private String sWK_DEP_CODE;

	private String sWK_TRI_CODE;

	private String sWK_EMP_CODE;

	private String sWK_SIHA_KBN;

	private Date sWK_SIHA_DATE;

	private String sWK_HOH_CODE;

	private int sWK_HORYU_KBN;

	private BigDecimal sWK_KARI_KIN;

	private BigDecimal sWK_KEIHI_KIN;

	private BigDecimal sWK_SIHA_KIN;

	private String sWK_KARI_DR_DEN_NO;

	private String sWK_KARI_CR_DEN_NO;

	private int sWK_KESAI_KBN;

	private String sWK_SEI_NO;

	private String sWK_DATA_KBN = "";

	private String sWK_UKE_DEP_CODE;

	private String sWK_TEK_CODE;

	private String sWK_TEK = "";

	private String sWK_BEFORE_DEN_NO;

	private Integer sWK_UPD_KBN;

	private String sWK_SYO_EMP_CODE;

	private Date sWK_SYO_DATE;

	private String sWK_IRAI_EMP_CODE;

	private String sWK_IRAI_DEP_CODE;

	private Date sWK_IRAI_DATE;

	private String sWK_KARI_DEP_CODE;

	private int sWK_SHR_KBN;

	private int sWK_KSN_KBN;

	private Date sWK_INP_DATE;

	private String pRG_ID;

	private String sWK_TJK_CODE;

	private String sWK_CUR_CODE;

	private BigDecimal sWK_CUR_RATE;

	private BigDecimal sWK_IN_KARI_KIN;

	private BigDecimal sWK_IN_KEIHI_KIN;

	private BigDecimal sWK_IN_SIHA_KIN;

	private String sWK_SYS_KBN;

	private String sWK_DEN_SYU;

	private int sWK_TUKE_KBN;

	private Integer sWK_UPD_CNT;

	private String sWK_INV_CUR_CODE;

	private BigDecimal sWK_INV_CUR_RATE;

	private BigDecimal sWK_INV_KIN;

	private String sWK_CBK_CODE;

	private Date sWK_SSY_DATE;

	private String sWK_UTK_NO;

	private String sWK_KARI_CUR_CODE;

	private BigDecimal sWK_KARI_CUR_RATE;

	/** ãÊï™ ÇPÅFé–àıéxï•ì`ï[ÇçÏê¨Ç∑ÇÈÇégóp */
	private int kbn = 0;

	/**
	 * @return kAI_CODE
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
	 * @return sWK_DEN_DATE
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
	 * @return sWK_DEN_NO
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
	 * @return sWK_KMK_CODE
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
	 * @return sWK_HKM_CODE
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
	 * @return sWK_UKM_CODE
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
	 * @return sWK_DEP_CODE
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
	 * @return sWK_DEP_CODE
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
	 * @return sWK_EMP_CODE
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
	 * @return sWK_SIHA_KBN
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
	 * @return sWK_SIHA_DATE
	 */
	public Date getSWK_SIHA_DATE() {
		return sWK_SIHA_DATE;
	}

	/**
	 * @param sWK_SIHA_DATE
	 */
	public void setSWK_SIHA_DATE(Date sWK_SIHA_DATE) {
		this.sWK_SIHA_DATE = sWK_SIHA_DATE;
	}

	/**
	 * @return sWK_HOH_CODE
	 */
	public String getSWK_HOH_CODE() {
		return sWK_HOH_CODE;
	}

	/**
	 * @param sWK_HOH_CODE
	 */
	public void setSWK_HOH_CODE(String sWK_HOH_CODE) {
		this.sWK_HOH_CODE = sWK_HOH_CODE;
	}

	/**
	 * @return sWK_HORYU_KBN
	 */
	public int getSWK_HORYU_KBN() {
		return sWK_HORYU_KBN;
	}

	/**
	 * @param sWK_HORYU_KBN
	 */
	public void setSWK_HORYU_KBN(int sWK_HORYU_KBN) {
		this.sWK_HORYU_KBN = sWK_HORYU_KBN;
	}

	/**
	 * @return sWK_KARI_KIN
	 */
	public BigDecimal getSWK_KARI_KIN() {
		return sWK_KARI_KIN;
	}

	/**
	 * @param sWK_KARI_KIN
	 */
	public void setSWK_KARI_KIN(BigDecimal sWK_KARI_KIN) {
		this.sWK_KARI_KIN = sWK_KARI_KIN;
	}

	/**
	 * @return sWK_KEIHI_KIN
	 */
	public BigDecimal getSWK_KEIHI_KIN() {
		return sWK_KEIHI_KIN;
	}

	/**
	 * @param sWK_KEIHI_KIN
	 */
	public void setSWK_KEIHI_KIN(BigDecimal sWK_KEIHI_KIN) {
		this.sWK_KEIHI_KIN = sWK_KEIHI_KIN;
	}

	/**
	 * @return sWK_SIHA_KIN
	 */
	public BigDecimal getSWK_SIHA_KIN() {
		return sWK_SIHA_KIN;
	}

	/**
	 * @param sWK_SIHA_KIN
	 */
	public void setSWK_SIHA_KIN(BigDecimal sWK_SIHA_KIN) {
		this.sWK_SIHA_KIN = sWK_SIHA_KIN;
	}

	/**
	 * @return sWK_KARI_DR_DEN_NO
	 */
	public String getSWK_KARI_DR_DEN_NO() {
		return sWK_KARI_DR_DEN_NO;
	}

	/**
	 * @param sWK_KARI_DR_DEN_NO
	 */
	public void setSWK_KARI_DR_DEN_NO(String sWK_KARI_DR_DEN_NO) {
		this.sWK_KARI_DR_DEN_NO = sWK_KARI_DR_DEN_NO;
	}

	/**
	 * @return sWK_KARI_CR_DEN_NO
	 */
	public String getSWK_KARI_CR_DEN_NO() {
		return sWK_KARI_CR_DEN_NO;
	}

	/**
	 * @param sWK_KARI_CR_DEN_NO
	 */
	public void setSWK_KARI_CR_DEN_NO(String sWK_KARI_CR_DEN_NO) {
		this.sWK_KARI_CR_DEN_NO = sWK_KARI_CR_DEN_NO;
	}

	/**
	 * @return sWK_KESAI_KBN
	 */
	public int getSWK_KESAI_KBN() {
		return sWK_KESAI_KBN;
	}

	/**
	 * @param sWK_KESAI_KBN
	 */
	public void setSWK_KESAI_KBN(int sWK_KESAI_KBN) {
		this.sWK_KESAI_KBN = sWK_KESAI_KBN;
	}

	/**
	 * @return sWK_SEI_NO
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
	 * @return sWK_DATA_KBN
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
	 * @return sWK_UKE_DEP_CODE
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
	 * @return sWK_TEK_CODE
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
	 * @return sWK_TEK_CODE
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
	 * @return sWK_TEK_CODE
	 */
	public String getSWK_BEFORE_DEN_NO() {
		return sWK_BEFORE_DEN_NO;
	}

	/**
	 * @param sWK_BEFORE_DEN_NO
	 */
	public void setSWK_BEFORE_DEN_NO(String sWK_BEFORE_DEN_NO) {
		this.sWK_BEFORE_DEN_NO = sWK_BEFORE_DEN_NO;
	}

	/**
	 * @return sWK_UPD_KBN
	 */
	public Integer getSWK_UPD_KBN() {
		return sWK_UPD_KBN;
	}

	/**
	 * @param sWK_UPD_KBN
	 */
	public void setSWK_UPD_KBN(Integer sWK_UPD_KBN) {
		this.sWK_UPD_KBN = sWK_UPD_KBN;
	}

	/**
	 * @return sWK_SYO_EMP_CODE
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
	 * @return sWK_SYO_DATE
	 */
	public Date getSWK_SYO_DATE() {
		return sWK_SYO_DATE;
	}

	/**
	 * @param sWK_SYO_DATE
	 */
	public void setSWK_SYO_DATE(Date sWK_SYO_DATE) {
		this.sWK_SYO_DATE = sWK_SYO_DATE;
	}

	/**
	 * @return sWK_IRAI_EMP_CODE
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
	 * @return sWK_IRAI_DEP_CODE
	 */
	public String getSWK_IRAI_DEP_CODE() {
		return sWK_IRAI_DEP_CODE;
	}

	/**
	 * @param sWK_IRAI_DEP_CODE
	 */
	public void setSWK_IRAI_DEP_CODE(String sWK_IRAI_DEP_CODE) {
		this.sWK_IRAI_DEP_CODE = sWK_IRAI_DEP_CODE;
	}

	/**
	 * @return sWK_IRAI_DATE
	 */
	public Date getSWK_IRAI_DATE() {
		return sWK_IRAI_DATE;
	}

	/**
	 * @param sWK_IRAI_DATE
	 */
	public void setSWK_IRAI_DATE(Date sWK_IRAI_DATE) {
		this.sWK_IRAI_DATE = sWK_IRAI_DATE;
	}

	/**
	 * @return sWK_KARI_DEP_CODE
	 */
	public String getSWK_KARI_DEP_CODE() {
		return sWK_KARI_DEP_CODE;
	}

	/**
	 * @param sWK_KARI_DEP_CODE
	 */
	public void setSWK_KARI_DEP_CODE(String sWK_KARI_DEP_CODE) {
		this.sWK_KARI_DEP_CODE = sWK_KARI_DEP_CODE;
	}

	/**
	 * @return sWK_SHR_KBN
	 */
	public int getSWK_SHR_KBN() {
		return sWK_SHR_KBN;
	}

	/**
	 * @param sWK_SHR_KBN
	 */
	public void setSWK_SHR_KBN(int sWK_SHR_KBN) {
		this.sWK_SHR_KBN = sWK_SHR_KBN;
	}

	/**
	 * @return sWK_KSN_KBN
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
	 * @return sWK_INP_DATE
	 */
	public Date getSWK_INP_DATE() {
		return sWK_INP_DATE;
	}

	/**
	 * @param sWK_INP_DATE
	 */
	public void setSWK_INP_DATE(Date sWK_INP_DATE) {
		this.sWK_INP_DATE = sWK_INP_DATE;
	}

	/**
	 * @return pRG_ID
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
	 * @return sWK_TJK_CODE
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
	 * @return sWK_CUR_CODE
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
	 * @return sWK_CUR_RATE
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
	 * @return sWK_IN_KARI_KIN
	 */
	public BigDecimal getSWK_IN_KARI_KIN() {
		return sWK_IN_KARI_KIN;
	}

	/**
	 * @param sWK_IN_KARI_KIN
	 */
	public void setSWK_IN_KARI_KIN(BigDecimal sWK_IN_KARI_KIN) {
		this.sWK_IN_KARI_KIN = sWK_IN_KARI_KIN;
	}

	/**
	 * @return sWK_IN_KEIHI_KIN
	 */
	public BigDecimal getSWK_IN_KEIHI_KIN() {
		return sWK_IN_KEIHI_KIN;
	}

	/**
	 * @param sWK_IN_KEIHI_KIN
	 */
	public void setSWK_IN_KEIHI_KIN(BigDecimal sWK_IN_KEIHI_KIN) {
		this.sWK_IN_KEIHI_KIN = sWK_IN_KEIHI_KIN;
	}

	/**
	 * @return sWK_IN_SIHA_KIN
	 */
	public BigDecimal getSWK_IN_SIHA_KIN() {
		return sWK_IN_SIHA_KIN;
	}

	/**
	 * @param sWK_IN_SIHA_KIN
	 */
	public void setSWK_IN_SIHA_KIN(BigDecimal sWK_IN_SIHA_KIN) {
		this.sWK_IN_SIHA_KIN = sWK_IN_SIHA_KIN;
	}

	/**
	 * @return sWK_SYS_KBN
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
	 * @return sWK_DEN_SYU
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
	 * @return sWK_TUKE_KBN
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
	 * @return sWK_UPD_CNT
	 */
	public Integer getSWK_UPD_CNT() {
		return sWK_UPD_CNT;
	}

	/**
	 * @param sWK_UPD_CNT
	 */
	public void setSWK_UPD_CNT(Integer sWK_UPD_CNT) {
		this.sWK_UPD_CNT = sWK_UPD_CNT;
	}

	/**
	 * @return sWK_INV_CUR_CODE
	 */
	public String getSWK_INV_CUR_CODE() {
		return sWK_INV_CUR_CODE;
	}

	/**
	 * @param sWK_INV_CUR_CODE
	 */
	public void setSWK_INV_CUR_CODE(String sWK_INV_CUR_CODE) {
		this.sWK_INV_CUR_CODE = sWK_INV_CUR_CODE;
	}

	/**
	 * @return sWK_INV_CUR_RATE
	 */
	public BigDecimal getSWK_INV_CUR_RATE() {
		return sWK_INV_CUR_RATE;
	}

	/**
	 * @param sWK_INV_CUR_RATE
	 */
	public void setSWK_INV_CUR_RATE(BigDecimal sWK_INV_CUR_RATE) {
		this.sWK_INV_CUR_RATE = sWK_INV_CUR_RATE;
	}

	/**
	 * @return sWK_INV_KIN
	 */
	public BigDecimal getSWK_INV_KIN() {
		return sWK_INV_KIN;
	}

	/**
	 * @param sWK_INV_KIN
	 */
	public void setSWK_INV_KIN(BigDecimal sWK_INV_KIN) {
		this.sWK_INV_KIN = sWK_INV_KIN;
	}

	/**
	 * @return sWK_CBK_CODE
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
	 * @return sWK_SSY_DATE
	 */
	public Date getSWK_SSY_DATE() {
		return sWK_SSY_DATE;
	}

	/**
	 * @param sWK_SSY_DATE
	 */
	public void setSWK_SSY_DATE(Date sWK_SSY_DATE) {
		this.sWK_SSY_DATE = sWK_SSY_DATE;
	}

	/**
	 * @return sWK_UTK_NO
	 */
	public String getSWK_UTK_NO() {
		return sWK_UTK_NO;
	}

	/**
	 * @param sWK_UTK_NO
	 */
	public void setSWK_UTK_NO(String sWK_UTK_NO) {
		this.sWK_UTK_NO = sWK_UTK_NO;
	}

	/**
	 * @return sWK_KARI_CUR_CODE
	 */
	public String getSWK_KARI_CUR_CODE() {
		return sWK_KARI_CUR_CODE;
	}

	/**
	 * @param sWK_KARI_CUR_CODE
	 */
	public void setSWK_KARI_CUR_CODE(String sWK_KARI_CUR_CODE) {
		this.sWK_KARI_CUR_CODE = sWK_KARI_CUR_CODE;
	}

	/**
	 * @return sWK_KARI_CUR_RATE
	 */
	public BigDecimal getSWK_KARI_CUR_RATE() {
		return sWK_KARI_CUR_RATE;
	}

	/**
	 * @param sWK_KARI_CUR_RATE
	 */
	public void setSWK_KARI_CUR_RATE(BigDecimal sWK_KARI_CUR_RATE) {
		this.sWK_KARI_CUR_RATE = sWK_KARI_CUR_RATE;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/sWK_DEN_DATE=").append(sWK_DEN_DATE);
		buff.append("/sWK_DEN_NO=").append(sWK_DEN_NO);
		buff.append("/sWK_KMK_CODE=").append(sWK_KMK_CODE);
		buff.append("/sWK_HKM_CODE=").append(sWK_HKM_CODE);
		buff.append("/sWK_UKM_CODE=").append(sWK_UKM_CODE);
		buff.append("/sWK_DEP_CODE=").append(sWK_DEP_CODE);
		buff.append("/sWK_TRI_CODE=").append(sWK_TRI_CODE);
		buff.append("/sWK_EMP_CODE=").append(sWK_EMP_CODE);
		buff.append("/sWK_SIHA_KBN=").append(sWK_SIHA_KBN);
		buff.append("/sWK_SIHA_DATE=").append(sWK_SIHA_DATE);
		buff.append("/sWK_HOH_CODE=").append(sWK_HOH_CODE);
		buff.append("/sWK_HORYU_KBN=").append(sWK_HORYU_KBN);
		buff.append("/sWK_KARI_KIN=").append(sWK_KARI_KIN);
		buff.append("/sWK_KEIHI_KIN=").append(sWK_KEIHI_KIN);
		buff.append("/sWK_SIHA_KIN=").append(sWK_SIHA_KIN);
		buff.append("/sWK_KARI_DR_DEN_NO=").append(sWK_KARI_DR_DEN_NO);
		buff.append("/sWK_KARI_CR_DEN_NO=").append(sWK_KARI_CR_DEN_NO);
		buff.append("/sWK_KESAI_KBN=").append(sWK_KESAI_KBN);
		buff.append("/sWK_SEI_NO=").append(sWK_SEI_NO);
		buff.append("/sWK_DATA_KBN=").append(sWK_DATA_KBN);
		buff.append("/sWK_UKE_DEP_CODE=").append(sWK_UKE_DEP_CODE);
		buff.append("/sWK_TEK_CODE=").append(sWK_TEK_CODE);
		buff.append("/sWK_TEK=").append(sWK_TEK);
		buff.append("/sWK_BEFORE_DEN_NO=").append(sWK_BEFORE_DEN_NO);
		buff.append("/sWK_UPD_KBN=").append(sWK_UPD_KBN);
		buff.append("/sWK_SYO_EMP_CODE=").append(sWK_SYO_EMP_CODE);
		buff.append("/sWK_SYO_DATE=").append(sWK_SYO_DATE);
		buff.append("/sWK_IRAI_EMP_CODE=").append(sWK_IRAI_EMP_CODE);
		buff.append("/sWK_IRAI_DEP_CODE=").append(sWK_IRAI_DEP_CODE);
		buff.append("/sWK_IRAI_DATE=").append(sWK_IRAI_DATE);
		buff.append("/sWK_KARI_DEP_CODE=").append(sWK_KARI_DEP_CODE);
		buff.append("/sWK_SHR_KBN=").append(sWK_SHR_KBN);
		buff.append("/sWK_KSN_KBN=").append(sWK_KSN_KBN);
		buff.append("/sWK_INP_DATE=").append(sWK_INP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("/sWK_TJK_CODE=").append(sWK_TJK_CODE);
		buff.append("/sWK_CUR_CODE=").append(sWK_CUR_CODE);
		buff.append("/sWK_CUR_RATE=").append(sWK_CUR_RATE);
		buff.append("/sWK_IN_KARI_KIN=").append(sWK_IN_KARI_KIN);
		buff.append("/sWK_IN_KEIHI_KIN=").append(sWK_IN_KEIHI_KIN);
		buff.append("/sWK_IN_SIHA_KIN=").append(sWK_IN_SIHA_KIN);
		buff.append("/sWK_SYS_KBN=").append(sWK_SYS_KBN);
		buff.append("/sWK_DEN_SYU=").append(sWK_DEN_SYU);
		buff.append("/sWK_TUKE_KBN=").append(sWK_TUKE_KBN);
		buff.append("/sWK_UPD_CNT=").append(sWK_UPD_CNT);
		buff.append("/sWK_INV_CUR_CODE=").append(sWK_INV_CUR_CODE);
		buff.append("/sWK_INV_CUR_RATE=").append(sWK_INV_CUR_RATE);
		buff.append("/sWK_INV_KIN=").append(sWK_INV_KIN);
		buff.append("/sWK_CBK_CODE=").append(sWK_CBK_CODE);
		buff.append("/sWK_SSY_DATE=").append(sWK_SSY_DATE);
		buff.append("/sWK_UTK_NO=").append(sWK_UTK_NO);
		buff.append("/sWK_KARI_CUR_CODE=").append(sWK_KARI_CUR_CODE);
		buff.append("/sWK_KARI_CUR_RATE=").append(sWK_KARI_CUR_RATE);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(kAI_CODE);
		list.add(sWK_DEN_DATE);
		list.add(sWK_DEN_NO);
		list.add(sWK_KMK_CODE);
		list.add(sWK_HKM_CODE);
		list.add(sWK_UKM_CODE);
		list.add(sWK_DEP_CODE);
		list.add(sWK_TRI_CODE);
		list.add(sWK_EMP_CODE);
		list.add(sWK_SIHA_KBN);
		list.add(sWK_SIHA_DATE);
		list.add(sWK_HOH_CODE);
		list.add(sWK_HORYU_KBN);
		list.add(sWK_KARI_KIN);
		list.add(sWK_KEIHI_KIN);
		list.add(sWK_SIHA_KIN);
		list.add(sWK_KARI_DR_DEN_NO);
		list.add(sWK_KARI_CR_DEN_NO);
		list.add(sWK_KESAI_KBN);
		list.add(sWK_SEI_NO);
		list.add(sWK_DATA_KBN);
		list.add(sWK_UKE_DEP_CODE);
		list.add(sWK_TEK_CODE);
		list.add(sWK_TEK);
		list.add(sWK_BEFORE_DEN_NO);
		list.add(sWK_UPD_KBN);
		list.add(sWK_SYO_EMP_CODE);
		list.add(sWK_SYO_DATE);
		list.add(sWK_IRAI_EMP_CODE);
		list.add(sWK_IRAI_DEP_CODE);
		list.add(sWK_IRAI_DATE);
		list.add(sWK_KARI_DEP_CODE);
		list.add(sWK_SHR_KBN);
		list.add(sWK_KSN_KBN);
		list.add(sWK_INP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);
		list.add(sWK_TJK_CODE);
		list.add(sWK_CUR_CODE);
		list.add(sWK_CUR_RATE);
		list.add(sWK_IN_KARI_KIN);
		list.add(sWK_IN_KEIHI_KIN);
		list.add(sWK_IN_SIHA_KIN);
		list.add(sWK_SYS_KBN);
		list.add(sWK_DEN_SYU);
		list.add(sWK_TUKE_KBN);
		list.add(sWK_UPD_CNT);
		list.add(sWK_INV_CUR_CODE);
		list.add(sWK_INV_CUR_RATE);
		list.add(sWK_INV_KIN);
		list.add(sWK_CBK_CODE);
		list.add(sWK_SSY_DATE);
		list.add(sWK_UTK_NO);
		list.add(sWK_KARI_CUR_CODE);
		list.add(sWK_KARI_CUR_RATE);

		return list;
	}

	/**
	 * ãÊï™ÇéÊìæ
	 * 
	 * @return int
	 */
	public int getKbn() {
		return kbn;
	}

	/**
	 * ãÊï™Çê›íË
	 * 
	 * @param kbn
	 */
	public void setKbn(int kbn) {
		this.kbn = kbn;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public AP_SWK_HDR clone() {
		try {
			return (AP_SWK_HDR) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}
}
