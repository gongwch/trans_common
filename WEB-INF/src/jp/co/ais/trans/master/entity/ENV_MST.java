package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class ENV_MST extends MasterBase {

	/**  */
	public static final String TABLE = "ENV_MST";

	/**  */
	public static final String kAI_CODE_ID = "sequence, sequenceName=kAI_CODE";

	private String kAI_CODE = "";

	private String kAI_NAME;

	private String kAI_NAME_S;

	private Date sTR_DATE;

	private Date eND_DATE;

	private String pRG_ID;

	private String zIP;

	private String jYU_KANA;

	private String jYU_1;

	private String jYU_2;

	private String tEL;

	private String fAX;

	private String fORECOLOR;

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
	public String getKAI_NAME() {
		return kAI_NAME;
	}

	/**
	 * @param kAI_NAME
	 */
	public void setKAI_NAME(String kAI_NAME) {
		this.kAI_NAME = kAI_NAME;
	}

	/**
	 * @return String
	 */
	public String getKAI_NAME_S() {
		return kAI_NAME_S;
	}

	/**
	 * @param kAI_NAME_S
	 */
	public void setKAI_NAME_S(String kAI_NAME_S) {
		this.kAI_NAME_S = kAI_NAME_S;
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
	public String getFORECOLOR() {
		return fORECOLOR;
	}

	/**
	 * @param fORECOLOR
	 */
	public void setFORECOLOR(String fORECOLOR) {
		this.fORECOLOR = fORECOLOR;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/kAI_NAME=").append(kAI_NAME);
		buff.append("/kAI_NAME_S=").append(kAI_NAME_S);
		buff.append("/sTR_DATE=").append(sTR_DATE);
		buff.append("/eND_DATE=").append(eND_DATE);
		buff.append("/iNP_DATE=").append(iNP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("/zIP=").append(zIP);
		buff.append("/jYU_KANA=").append(jYU_KANA);
		buff.append("/jYU_1=").append(jYU_1);
		buff.append("/jYU_2=").append(jYU_2);
		buff.append("/tEL=").append(tEL);
		buff.append("/fAX=").append(fAX);
		buff.append("/fORECOLOR=").append(fORECOLOR);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(kAI_NAME);
		list.add(kAI_NAME_S);
		list.add(sTR_DATE);
		list.add(eND_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);
		list.add(zIP);
		list.add(jYU_KANA);
		list.add(jYU_1);
		list.add(jYU_2);
		list.add(tEL);
		list.add(fAX);
		list.add(fORECOLOR);

		return list;
	}
}
