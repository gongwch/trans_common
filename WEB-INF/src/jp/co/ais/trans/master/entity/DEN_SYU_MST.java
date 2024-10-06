package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class DEN_SYU_MST extends MasterBase {

	/**  */
	public static final String TABLE = "DEN_SYU_MST";

	private String kAI_CODE = "";

	private String dEN_SYU_CODE = "";

	private String sYS_KBN = "";

	private String dEN_SYU_NAME;

	private String dEN_SYU_NAME_S;

	private String dEN_SYU_NAME_K;

	private String dATA_KBN;

	private int tA_SYS_KBN;

	private int dAT_SAIBAN_FLG;

	private int tANI;

	private int zEI_KBN;

	private int sWK_IN_KBN;

	private String pRG_ID;

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
	public String getDEN_SYU_CODE() {
		return dEN_SYU_CODE;
	}

	/**
	 * @param dEN_SYU_CODE
	 */
	public void setDEN_SYU_CODE(String dEN_SYU_CODE) {
		this.dEN_SYU_CODE = dEN_SYU_CODE;
	}

	/**
	 * @return String
	 */
	public String getSYS_KBN() {
		return sYS_KBN;
	}

	/**
	 * @param sYS_KBN
	 */
	public void setSYS_KBN(String sYS_KBN) {
		this.sYS_KBN = sYS_KBN;
	}

	/**
	 * @return String
	 */
	public String getDEN_SYU_NAME() {
		return dEN_SYU_NAME;
	}

	/**
	 * @param dEN_SYU_NAME
	 */
	public void setDEN_SYU_NAME(String dEN_SYU_NAME) {
		this.dEN_SYU_NAME = dEN_SYU_NAME;
	}

	/**
	 * @return String
	 */
	public String getDEN_SYU_NAME_S() {
		return dEN_SYU_NAME_S;
	}

	/**
	 * @param dEN_SYU_NAME_S
	 */
	public void setDEN_SYU_NAME_S(String dEN_SYU_NAME_S) {
		this.dEN_SYU_NAME_S = dEN_SYU_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getDEN_SYU_NAME_K() {
		return dEN_SYU_NAME_K;
	}

	/**
	 * @param dEN_SYU_NAME_K
	 */
	public void setDEN_SYU_NAME_K(String dEN_SYU_NAME_K) {
		this.dEN_SYU_NAME_K = dEN_SYU_NAME_K;
	}

	/**
	 * @return String
	 */
	public String getDATA_KBN() {
		return dATA_KBN;
	}

	/**
	 * @param dATA_KBN
	 */
	public void setDATA_KBN(String dATA_KBN) {
		this.dATA_KBN = dATA_KBN;
	}

	/**
	 * @return int
	 */
	public int getTA_SYS_KBN() {
		return tA_SYS_KBN;
	}

	/**
	 * @param tA_SYS_KBN
	 */
	public void setTA_SYS_KBN(int tA_SYS_KBN) {
		this.tA_SYS_KBN = tA_SYS_KBN;
	}

	/**
	 * @return int
	 */
	public int getDAT_SAIBAN_FLG() {
		return dAT_SAIBAN_FLG;
	}

	/**
	 * @param dAT_SAIBAN_FLG
	 */
	public void setDAT_SAIBAN_FLG(int dAT_SAIBAN_FLG) {
		this.dAT_SAIBAN_FLG = dAT_SAIBAN_FLG;
	}

	/**
	 * @return int
	 */
	public int getTANI() {
		return tANI;
	}

	/**
	 * @param tANI
	 */
	public void setTANI(int tANI) {
		this.tANI = tANI;
	}

	/**
	 * @return int
	 */
	public int getZEI_KBN() {
		return zEI_KBN;
	}

	/**
	 * @param zEI_KBN
	 */
	public void setZEI_KBN(int zEI_KBN) {
		this.zEI_KBN = zEI_KBN;
	}

	/**
	 * @return int
	 */
	public int getSWK_IN_KBN() {
		return sWK_IN_KBN;
	}

	/**
	 * @param sWK_IN_KBN
	 */
	public void setSWK_IN_KBN(int sWK_IN_KBN) {
		this.sWK_IN_KBN = sWK_IN_KBN;
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

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/dEN_SYU_CODE=").append(dEN_SYU_CODE);
		buff.append("/sYS_KBN=").append(sYS_KBN);
		buff.append("/dEN_SYU_NAME=").append(dEN_SYU_NAME);
		buff.append("/dEN_SYU_NAME_S=").append(dEN_SYU_NAME_S);
		buff.append("/dEN_SYU_NAME_K=").append(dEN_SYU_NAME_K);
		buff.append("/dATA_KBN=").append(dATA_KBN);
		buff.append("/tA_SYS_KBN=").append(tA_SYS_KBN);
		buff.append("/dAT_SAIBAN_FLG=").append(dAT_SAIBAN_FLG);
		buff.append("/tANI=").append(tANI);
		buff.append("/zEI_KBN=").append(zEI_KBN);
		buff.append("/sWK_IN_KBN=").append(sWK_IN_KBN);
		buff.append("/iNP_DATE=").append(iNP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(dEN_SYU_CODE);
		list.add(sYS_KBN);
		list.add(dEN_SYU_NAME);
		list.add(dEN_SYU_NAME_S);
		list.add(dEN_SYU_NAME_K);
		list.add(dATA_KBN);
		list.add(tA_SYS_KBN);
		list.add(dAT_SAIBAN_FLG);
		list.add(tANI);
		list.add(zEI_KBN);
		list.add(sWK_IN_KBN);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
