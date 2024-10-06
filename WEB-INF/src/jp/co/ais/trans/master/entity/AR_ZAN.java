package jp.co.ais.trans.master.entity;

import java.math.*;
import java.util.*;

/**
 * 
 */
public class AR_ZAN extends MasterBase {

	/**  */
	public static final String TABLE = "AR_ZAN";

	private String kAI_CODE = "";

	private String zAN_DEP_CODE = "";

	private String zAN_TRI_CODE = "";

	private String zAN_SEI_NO = "";

	private Date zAN_SEI_DEN_DATE;

	private String zAN_SEI_DEN_NO;

	private Integer zAN_SEI_GYO_NO;

	private String zAN_DATA_KBN = "";

	private Date zAN_KESI_DEN_DATE;

	private String zAN_KESI_DEN_NO;

	private Integer zAN_KESI_GYO_NO;

	private String zAN_KMK_CODE = "";

	private String zAN_HKM_CODE;

	private String zAN_UKM_CODE;

	private Date zAN_AR_DATE;

	private BigDecimal zAN_SEI_KIN;

	private BigDecimal zAN_KESI_KIN;

	private String zAN_TRI_NAME;

	private String pRG_ID;

	private int zAN_SOUSAI_FLG;

	private Date zAN_SOUSAI_DATE;

	private String zAN_SIHA_KBN;

	private String zAN_UTK_NO;

	private String zAN_CBK_CODE;

	private String zAN_CUR_CODE;

	private BigDecimal zAN_CUR_RATE;

	private BigDecimal zAN_SEI_IN_KIN;

	private BigDecimal zAN_KESI_IN_KIN;

	private String zAN_NYU_UTK_NO;

	private Integer zAN_NYU_GYO_NO;

	private Long zAN_NYU_KESI_KIN;

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
	public String getZAN_DEP_CODE() {
		return zAN_DEP_CODE;
	}

	/**
	 * @param zAN_DEP_CODE
	 */
	public void setZAN_DEP_CODE(String zAN_DEP_CODE) {
		this.zAN_DEP_CODE = zAN_DEP_CODE;
	}

	/**
	 * @return String
	 */
	public String getZAN_TRI_CODE() {
		return zAN_TRI_CODE;
	}

	/**
	 * @param zAN_TRI_CODE
	 */
	public void setZAN_TRI_CODE(String zAN_TRI_CODE) {
		this.zAN_TRI_CODE = zAN_TRI_CODE;
	}

	/**
	 * @return String
	 */
	public String getZAN_SEI_NO() {
		return zAN_SEI_NO;
	}

	/**
	 * @param zAN_SEI_NO
	 */
	public void setZAN_SEI_NO(String zAN_SEI_NO) {
		this.zAN_SEI_NO = zAN_SEI_NO;
	}

	/**
	 * @return Date
	 */
	public Date getZAN_SEI_DEN_DATE() {
		return zAN_SEI_DEN_DATE;
	}

	/**
	 * @param zAN_SEI_DEN_DATE
	 */
	public void setZAN_SEI_DEN_DATE(Date zAN_SEI_DEN_DATE) {
		this.zAN_SEI_DEN_DATE = zAN_SEI_DEN_DATE;
	}

	/**
	 * @return String
	 */
	public String getZAN_SEI_DEN_NO() {
		return zAN_SEI_DEN_NO;
	}

	/**
	 * @param zAN_SEI_DEN_NO
	 */
	public void setZAN_SEI_DEN_NO(String zAN_SEI_DEN_NO) {
		this.zAN_SEI_DEN_NO = zAN_SEI_DEN_NO;
	}

	/**
	 * @return Integer
	 */
	public Integer getZAN_SEI_GYO_NO() {
		return zAN_SEI_GYO_NO;
	}

	/**
	 * @param zAN_SEI_GYO_NO
	 */
	public void setZAN_SEI_GYO_NO(Integer zAN_SEI_GYO_NO) {
		this.zAN_SEI_GYO_NO = zAN_SEI_GYO_NO;
	}

	/**
	 * @return String
	 */
	public String getZAN_DATA_KBN() {
		return zAN_DATA_KBN;
	}

	/**
	 * @param zAN_DATA_KBN
	 */
	public void setZAN_DATA_KBN(String zAN_DATA_KBN) {
		this.zAN_DATA_KBN = zAN_DATA_KBN;
	}

	/**
	 * @return Date
	 */
	public Date getZAN_KESI_DEN_DATE() {
		return zAN_KESI_DEN_DATE;
	}

	/**
	 * @param zAN_KESI_DEN_DATE
	 */
	public void setZAN_KESI_DEN_DATE(Date zAN_KESI_DEN_DATE) {
		this.zAN_KESI_DEN_DATE = zAN_KESI_DEN_DATE;
	}

	/**
	 * @return String
	 */
	public String getZAN_KESI_DEN_NO() {
		return zAN_KESI_DEN_NO;
	}

	/**
	 * @param zAN_KESI_DEN_NO
	 */
	public void setZAN_KESI_DEN_NO(String zAN_KESI_DEN_NO) {
		this.zAN_KESI_DEN_NO = zAN_KESI_DEN_NO;
	}

	/**
	 * @return Integer
	 */
	public Integer getZAN_KESI_GYO_NO() {
		return zAN_KESI_GYO_NO;
	}

	/**
	 * @param zAN_KESI_GYO_NO
	 */
	public void setZAN_KESI_GYO_NO(Integer zAN_KESI_GYO_NO) {
		this.zAN_KESI_GYO_NO = zAN_KESI_GYO_NO;
	}

	/**
	 * @return String
	 */
	public String getZAN_KMK_CODE() {
		return zAN_KMK_CODE;
	}

	/**
	 * @param zAN_KMK_CODE
	 */
	public void setZAN_KMK_CODE(String zAN_KMK_CODE) {
		this.zAN_KMK_CODE = zAN_KMK_CODE;
	}

	/**
	 * @return String
	 */
	public String getZAN_HKM_CODE() {
		return zAN_HKM_CODE;
	}

	/**
	 * @param zAN_HKM_CODE
	 */
	public void setZAN_HKM_CODE(String zAN_HKM_CODE) {
		this.zAN_HKM_CODE = zAN_HKM_CODE;
	}

	/**
	 * @return String
	 */
	public String getZAN_UKM_CODE() {
		return zAN_UKM_CODE;
	}

	/**
	 * @param zAN_UKM_CODE
	 */
	public void setZAN_UKM_CODE(String zAN_UKM_CODE) {
		this.zAN_UKM_CODE = zAN_UKM_CODE;
	}

	/**
	 * @return Date
	 */
	public Date getZAN_AR_DATE() {
		return zAN_AR_DATE;
	}

	/**
	 * @param zAN_AR_DATE
	 */
	public void setZAN_AR_DATE(Date zAN_AR_DATE) {
		this.zAN_AR_DATE = zAN_AR_DATE;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getZAN_SEI_KIN() {
		return zAN_SEI_KIN;
	}

	/**
	 * @param zAN_SEI_KIN
	 */
	public void setZAN_SEI_KIN(BigDecimal zAN_SEI_KIN) {
		this.zAN_SEI_KIN = zAN_SEI_KIN;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getZAN_KESI_KIN() {
		return zAN_KESI_KIN;
	}

	/**
	 * @param zAN_KESI_KIN
	 */
	public void setZAN_KESI_KIN(BigDecimal zAN_KESI_KIN) {
		this.zAN_KESI_KIN = zAN_KESI_KIN;
	}

	/**
	 * @return String
	 */
	public String getZAN_TRI_NAME() {
		return zAN_TRI_NAME;
	}

	/**
	 * @param zAN_TRI_NAME
	 */
	public void setZAN_TRI_NAME(String zAN_TRI_NAME) {
		this.zAN_TRI_NAME = zAN_TRI_NAME;
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
	 * @return int
	 */
	public int getZAN_SOUSAI_FLG() {
		return zAN_SOUSAI_FLG;
	}

	/**
	 * @param zAN_SOUSAI_FLG
	 */
	public void setZAN_SOUSAI_FLG(int zAN_SOUSAI_FLG) {
		this.zAN_SOUSAI_FLG = zAN_SOUSAI_FLG;
	}

	/**
	 * @return Date
	 */
	public Date getZAN_SOUSAI_DATE() {
		return zAN_SOUSAI_DATE;
	}

	/**
	 * @param zAN_SOUSAI_DATE
	 */
	public void setZAN_SOUSAI_DATE(Date zAN_SOUSAI_DATE) {
		this.zAN_SOUSAI_DATE = zAN_SOUSAI_DATE;
	}

	/**
	 * @return String
	 */
	public String getZAN_SIHA_KBN() {
		return zAN_SIHA_KBN;
	}

	/**
	 * @param zAN_SIHA_KBN
	 */
	public void setZAN_SIHA_KBN(String zAN_SIHA_KBN) {
		this.zAN_SIHA_KBN = zAN_SIHA_KBN;
	}

	/**
	 * @return String
	 */
	public String getZAN_UTK_NO() {
		return zAN_UTK_NO;
	}

	/**
	 * @param zAN_UTK_NO
	 */
	public void setZAN_UTK_NO(String zAN_UTK_NO) {
		this.zAN_UTK_NO = zAN_UTK_NO;
	}

	/**
	 * @return String
	 */
	public String getZAN_CBK_CODE() {
		return zAN_CBK_CODE;
	}

	/**
	 * @param zAN_CBK_CODE
	 */
	public void setZAN_CBK_CODE(String zAN_CBK_CODE) {
		this.zAN_CBK_CODE = zAN_CBK_CODE;
	}

	/**
	 * @return String
	 */
	public String getZAN_CUR_CODE() {
		return zAN_CUR_CODE;
	}

	/**
	 * @param zAN_CUR_CODE
	 */
	public void setZAN_CUR_CODE(String zAN_CUR_CODE) {
		this.zAN_CUR_CODE = zAN_CUR_CODE;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getZAN_CUR_RATE() {
		return zAN_CUR_RATE;
	}

	/**
	 * @param zAN_CUR_RATE
	 */
	public void setZAN_CUR_RATE(BigDecimal zAN_CUR_RATE) {
		this.zAN_CUR_RATE = zAN_CUR_RATE;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getZAN_SEI_IN_KIN() {
		return zAN_SEI_IN_KIN;
	}

	/**
	 * @param zAN_SEI_IN_KIN
	 */
	public void setZAN_SEI_IN_KIN(BigDecimal zAN_SEI_IN_KIN) {
		this.zAN_SEI_IN_KIN = zAN_SEI_IN_KIN;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getZAN_KESI_IN_KIN() {
		return zAN_KESI_IN_KIN;
	}

	/**
	 * @param zAN_KESI_IN_KIN
	 */
	public void setZAN_KESI_IN_KIN(BigDecimal zAN_KESI_IN_KIN) {
		this.zAN_KESI_IN_KIN = zAN_KESI_IN_KIN;
	}

	/**
	 * @return String
	 */
	public String getZAN_NYU_UTK_NO() {
		return zAN_NYU_UTK_NO;
	}

	/**
	 * @param zAN_NYU_UTK_NO
	 */
	public void setZAN_NYU_UTK_NO(String zAN_NYU_UTK_NO) {
		this.zAN_NYU_UTK_NO = zAN_NYU_UTK_NO;
	}

	/**
	 * @return Integer
	 */
	public Integer getZAN_NYU_GYO_NO() {
		return zAN_NYU_GYO_NO;
	}

	/**
	 * @param zAN_NYU_GYO_NO
	 */
	public void setZAN_NYU_GYO_NO(Integer zAN_NYU_GYO_NO) {
		this.zAN_NYU_GYO_NO = zAN_NYU_GYO_NO;
	}

	/**
	 * @return Long
	 */
	public Long getZAN_NYU_KESI_KIN() {
		return zAN_NYU_KESI_KIN;
	}

	/**
	 * @param zAN_NYU_KESI_KIN
	 */
	public void setZAN_NYU_KESI_KIN(Long zAN_NYU_KESI_KIN) {
		this.zAN_NYU_KESI_KIN = zAN_NYU_KESI_KIN;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/zAN_DEP_CODE=").append(zAN_DEP_CODE);
		buff.append("/zAN_TRI_CODE=").append(zAN_TRI_CODE);
		buff.append("/zAN_SEI_NO=").append(zAN_SEI_NO);
		buff.append("/zAN_SEI_DEN_DATE=").append(zAN_SEI_DEN_DATE);
		buff.append("/zAN_SEI_DEN_NO=").append(zAN_SEI_DEN_NO);
		buff.append("/zAN_SEI_GYO_NO=").append(zAN_SEI_GYO_NO);
		buff.append("/zAN_DATA_KBN=").append(zAN_DATA_KBN);
		buff.append("/zAN_KESI_DEN_DATE=").append(zAN_KESI_DEN_DATE);
		buff.append("/zAN_KESI_DEN_NO=").append(zAN_KESI_DEN_NO);
		buff.append("/zAN_KESI_GYO_NO=").append(zAN_KESI_GYO_NO);
		buff.append("/zAN_KMK_CODE=").append(zAN_KMK_CODE);
		buff.append("/zAN_HKM_CODE=").append(zAN_HKM_CODE);
		buff.append("/zAN_UKM_CODE=").append(zAN_UKM_CODE);
		buff.append("/zAN_AR_DATE=").append(zAN_AR_DATE);
		buff.append("/zAN_SEI_KIN=").append(zAN_SEI_KIN);
		buff.append("/zAN_KESI_KIN=").append(zAN_KESI_KIN);
		buff.append("/zAN_TRI_NAME=").append(zAN_TRI_NAME);
		buff.append("/iNP_DATE=").append(iNP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("/zAN_SOUSAI_FLG=").append(zAN_SOUSAI_FLG);
		buff.append("/zAN_SOUSAI_DATE=").append(zAN_SOUSAI_DATE);
		buff.append("/zAN_SIHA_KBN=").append(zAN_SIHA_KBN);
		buff.append("/zAN_UTK_NO=").append(zAN_UTK_NO);
		buff.append("/zAN_CBK_CODE=").append(zAN_CBK_CODE);
		buff.append("/zAN_CUR_CODE=").append(zAN_CUR_CODE);
		buff.append("/zAN_CUR_RATE=").append(zAN_CUR_RATE);
		buff.append("/zAN_SEI_IN_KIN=").append(zAN_SEI_IN_KIN);
		buff.append("/zAN_KESI_IN_KIN=").append(zAN_KESI_IN_KIN);
		buff.append("/zAN_NYU_UTK_NO=").append(zAN_NYU_UTK_NO);
		buff.append("/zAN_NYU_GYO_NO=").append(zAN_NYU_GYO_NO);
		buff.append("/zAN_NYU_KESI_KIN=").append(zAN_NYU_KESI_KIN);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(zAN_DEP_CODE);
		list.add(zAN_TRI_CODE);
		list.add(zAN_SEI_NO);
		list.add(zAN_SEI_DEN_DATE);
		list.add(zAN_SEI_DEN_NO);
		list.add(zAN_SEI_GYO_NO);
		list.add(zAN_DATA_KBN);
		list.add(zAN_KESI_DEN_DATE);
		list.add(zAN_KESI_DEN_NO);
		list.add(zAN_KESI_GYO_NO);
		list.add(zAN_KMK_CODE);
		list.add(zAN_HKM_CODE);
		list.add(zAN_UKM_CODE);
		list.add(zAN_AR_DATE);
		list.add(zAN_SEI_KIN);
		list.add(zAN_KESI_KIN);
		list.add(zAN_TRI_NAME);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);
		list.add(zAN_SOUSAI_FLG);
		list.add(zAN_SOUSAI_DATE);
		list.add(zAN_SIHA_KBN);
		list.add(zAN_UTK_NO);
		list.add(zAN_CBK_CODE);
		list.add(zAN_CUR_CODE);
		list.add(zAN_CUR_RATE);
		list.add(zAN_SEI_IN_KIN);
		list.add(zAN_KESI_IN_KIN);
		list.add(zAN_NYU_UTK_NO);
		list.add(zAN_NYU_GYO_NO);
		list.add(zAN_NYU_KESI_KIN);

		return list;
	}
}
