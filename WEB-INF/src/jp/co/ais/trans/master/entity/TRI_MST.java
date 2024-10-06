package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class TRI_MST extends MasterBase {

	/**  */
	public static final String TABLE = "TRI_MST";

	private String kAI_CODE = "";

	private String tRI_CODE = "";

	private String tRI_NAME = "";

	private String tRI_NAME_S = "";

	private String tRI_NAME_K = "";

	private String zIP = "";

	private String jYU_KANA;

	private String jYU_1;

	private String jYU_2;

	private String tEL;

	private String fAX;

	private String sUM_CODE;

	private int sIIRE_KBN;

	private int tOKUI_KBN;

	private String nJ_C_DATE;

	private String nJ_R_MON;

	private String nJ_P_DATE;

	private String nKN_CBK_CODE;

	private String tRI_KBN;

	private String sPOT_DEN_NO;

	private String jIG_NAME;

	private String iRAI_NAME;

	private int nYU_TESU_KBN;

	private Date sTR_DATE;

	private Date eND_DATE;

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
	public String getTRI_CODE() {
		return tRI_CODE;
	}

	/**
	 * @param tRI_CODE
	 */
	public void setTRI_CODE(String tRI_CODE) {
		this.tRI_CODE = tRI_CODE;
	}

	/**
	 * @return String
	 */
	public String getTRI_NAME() {
		return tRI_NAME;
	}

	/**
	 * @param tRI_NAME
	 */
	public void setTRI_NAME(String tRI_NAME) {
		this.tRI_NAME = tRI_NAME;
	}

	/**
	 * @return String
	 */
	public String getTRI_NAME_S() {
		return tRI_NAME_S;
	}

	/**
	 * @param tRI_NAME_S
	 */
	public void setTRI_NAME_S(String tRI_NAME_S) {
		this.tRI_NAME_S = tRI_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getTRI_NAME_K() {
		return tRI_NAME_K;
	}

	/**
	 * @param tRI_NAME_K
	 */
	public void setTRI_NAME_K(String tRI_NAME_K) {
		this.tRI_NAME_K = tRI_NAME_K;
	}

	/**
	 * @return String
	 */
	public String getZIP() {
		return zIP;
	}

	/**
	 * @param zIP
	 */
	public void setZIP(String zIP) {
		this.zIP = zIP;
	}

	/**
	 * @return String
	 */
	public String getJYU_KANA() {
		return jYU_KANA;
	}

	/**
	 * @param jYU_KANA
	 */
	public void setJYU_KANA(String jYU_KANA) {
		this.jYU_KANA = jYU_KANA;
	}

	/**
	 * @return String
	 */
	public String getJYU_1() {
		return jYU_1;
	}

	/**
	 * @param jYU_1
	 */
	public void setJYU_1(String jYU_1) {
		this.jYU_1 = jYU_1;
	}

	/**
	 * @return String
	 */
	public String getJYU_2() {
		return jYU_2;
	}

	/**
	 * @param jYU_2
	 */
	public void setJYU_2(String jYU_2) {
		this.jYU_2 = jYU_2;
	}

	/**
	 * @return String
	 */
	public String getTEL() {
		return tEL;
	}

	/**
	 * @param tEL
	 */
	public void setTEL(String tEL) {
		this.tEL = tEL;
	}

	/**
	 * @return String
	 */
	public String getFAX() {
		return fAX;
	}

	/**
	 * @param fAX
	 */
	public void setFAX(String fAX) {
		this.fAX = fAX;
	}

	/**
	 * @return String
	 */
	public String getSUM_CODE() {
		return sUM_CODE;
	}

	/**
	 * @param sUM_CODE
	 */
	public void setSUM_CODE(String sUM_CODE) {
		this.sUM_CODE = sUM_CODE;
	}

	/**
	 * @return String
	 */
	public int getSIIRE_KBN() {
		return sIIRE_KBN;
	}

	/**
	 * @param sIIRE_KBN
	 */
	public void setSIIRE_KBN(int sIIRE_KBN) {
		this.sIIRE_KBN = sIIRE_KBN;
	}

	/**
	 * @return String
	 */
	public int getTOKUI_KBN() {
		return tOKUI_KBN;
	}

	/**
	 * @param tOKUI_KBN
	 */
	public void setTOKUI_KBN(int tOKUI_KBN) {
		this.tOKUI_KBN = tOKUI_KBN;
	}

	/**
	 * @return String
	 */
	public String getNJ_C_DATE() {
		return nJ_C_DATE;
	}

	/**
	 * @param nJ_C_DATE
	 */
	public void setNJ_C_DATE(String nJ_C_DATE) {
		this.nJ_C_DATE = nJ_C_DATE;
	}

	/**
	 * @return String
	 */
	public String getNJ_R_MON() {
		return nJ_R_MON;
	}

	/**
	 * @param nJ_R_MON
	 */
	public void setNJ_R_MON(String nJ_R_MON) {
		this.nJ_R_MON = nJ_R_MON;
	}

	/**
	 * @return String
	 */
	public String getNJ_P_DATE() {
		return nJ_P_DATE;
	}

	/**
	 * @param nJ_P_DATE
	 */
	public void setNJ_P_DATE(String nJ_P_DATE) {
		this.nJ_P_DATE = nJ_P_DATE;
	}

	/**
	 * @return String
	 */
	public String getNKN_CBK_CODE() {
		return nKN_CBK_CODE;
	}

	/**
	 * @param nKN_CBK_CODE
	 */
	public void setNKN_CBK_CODE(String nKN_CBK_CODE) {
		this.nKN_CBK_CODE = nKN_CBK_CODE;
	}

	/**
	 * @return String
	 */
	public String getTRI_KBN() {
		return tRI_KBN;
	}

	/**
	 * @param tRI_KBN
	 */
	public void setTRI_KBN(String tRI_KBN) {
		this.tRI_KBN = tRI_KBN;
	}

	/**
	 * @return String
	 */
	public String getSPOT_DEN_NO() {
		return sPOT_DEN_NO;
	}

	/**
	 * @param sPOT_DEN_NO
	 */
	public void setSPOT_DEN_NO(String sPOT_DEN_NO) {
		this.sPOT_DEN_NO = sPOT_DEN_NO;
	}

	/**
	 * @return String
	 */
	public String getJIG_NAME() {
		return jIG_NAME;
	}

	/**
	 * @param jIG_NAME
	 */
	public void setJIG_NAME(String jIG_NAME) {
		this.jIG_NAME = jIG_NAME;
	}

	/**
	 * @return String
	 */
	public String getIRAI_NAME() {
		return iRAI_NAME;
	}

	/**
	 * @param iRAI_NAME
	 */
	public void setIRAI_NAME(String iRAI_NAME) {
		this.iRAI_NAME = iRAI_NAME;
	}

	/**
	 * @return int
	 */
	public int getNYU_TESU_KBN() {
		return nYU_TESU_KBN;
	}

	/**
	 * @param nYU_TESU_KBN
	 */
	public void setNYU_TESU_KBN(int nYU_TESU_KBN) {
		this.nYU_TESU_KBN = nYU_TESU_KBN;
	}

	/**
	 * @return Date
	 */
	public Date getSTR_DATE() {
		return sTR_DATE;
	}

	/**
	 * @param sTR_DATE
	 */
	public void setSTR_DATE(Date sTR_DATE) {
		this.sTR_DATE = sTR_DATE;
	}

	/**
	 * @return Date
	 */
	public Date getEND_DATE() {
		return eND_DATE;
	}

	/**
	 * @param eND_DATE
	 */
	public void setEND_DATE(Date eND_DATE) {
		this.eND_DATE = eND_DATE;
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
		buff.append("/tRI_CODE=").append(tRI_CODE);
		buff.append("/tRI_NAME=").append(tRI_NAME);
		buff.append("/tRI_NAME_S=").append(tRI_NAME_S);
		buff.append("/tRI_NAME_K=").append(tRI_NAME_K);
		buff.append("/zIP=").append(zIP);
		buff.append("/jYU_KANA=").append(jYU_KANA);
		buff.append("/jYU_1=").append(jYU_1);
		buff.append("/jYU_2=").append(jYU_2);
		buff.append("/tEL=").append(tEL);
		buff.append("/fAX=").append(fAX);
		buff.append("/sUM_CODE=").append(sUM_CODE);
		buff.append("/sIIRE_KBN=").append(sIIRE_KBN);
		buff.append("/tOKUI_KBN=").append(tOKUI_KBN);
		buff.append("/nJ_C_DATE=").append(nJ_C_DATE);
		buff.append("/nJ_R_MON=").append(nJ_R_MON);
		buff.append("/nJ_P_DATE=").append(nJ_P_DATE);
		buff.append("/nKN_CBK_CODE=").append(nKN_CBK_CODE);
		buff.append("/tRI_KBN=").append(tRI_KBN);
		buff.append("/sPOT_DEN_NO=").append(sPOT_DEN_NO);
		buff.append("/jIG_NAME=").append(jIG_NAME);
		buff.append("/iRAI_NAME=").append(iRAI_NAME);
		buff.append("/nYU_TESU_KBN=").append(nYU_TESU_KBN);
		buff.append("/sTR_DATE=").append(sTR_DATE);
		buff.append("/eND_DATE=").append(eND_DATE);
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
		list.add(tRI_CODE);
		list.add(tRI_NAME);
		list.add(tRI_NAME_S);
		list.add(tRI_NAME_K);
		list.add(zIP);
		list.add(jYU_KANA);
		list.add(jYU_1);
		list.add(jYU_2);
		list.add(tEL);
		list.add(fAX);
		list.add(sUM_CODE);
		list.add(sIIRE_KBN);
		list.add(tOKUI_KBN);
		list.add(nJ_C_DATE);
		list.add(nJ_R_MON);
		list.add(nJ_P_DATE);
		list.add(nKN_CBK_CODE);
		list.add(tRI_KBN);
		list.add(sPOT_DEN_NO);
		list.add(jIG_NAME);
		list.add(iRAI_NAME);
		list.add(nYU_TESU_KBN);
		list.add(sTR_DATE);
		list.add(eND_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
