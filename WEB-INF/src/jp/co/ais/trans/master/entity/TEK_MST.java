package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class TEK_MST extends MasterBase {

	/**  */
	public static final String TABLE = "TEK_MST";

	private String kAI_CODE = "";

	private int tEK_KBN;

	private String dATA_KBN;

	private String tEK_CODE = "";

	private String tEK_NAME;

	private String tEK_NAME_K;

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
	public int getTEK_KBN() {
		return tEK_KBN;
	}

	/**
	 * @param tEK_KBN
	 */
	public void setTEK_KBN(int tEK_KBN) {
		this.tEK_KBN = tEK_KBN;
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
	 * @return String
	 */
	public String getTEK_CODE() {
		return tEK_CODE;
	}

	/**
	 * @param tEK_CODE
	 */
	public void setTEK_CODE(String tEK_CODE) {
		this.tEK_CODE = tEK_CODE;
	}

	/**
	 * @return String
	 */
	public String getTEK_NAME() {
		return tEK_NAME;
	}

	/**
	 * @param tEK_NAME
	 */
	public void setTEK_NAME(String tEK_NAME) {
		this.tEK_NAME = tEK_NAME;
	}

	/**
	 * @return String
	 */
	public String getTEK_NAME_K() {
		return tEK_NAME_K;
	}

	/**
	 * @param tEK_NAME_K
	 */
	public void setTEK_NAME_K(String tEK_NAME_K) {
		this.tEK_NAME_K = tEK_NAME_K;
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
		buff.append("/tEK_KBN=").append(tEK_KBN);
		buff.append("/dATA_KBN=").append(dATA_KBN);
		buff.append("/tEK_CODE=").append(tEK_CODE);
		buff.append("/tEK_NAME=").append(tEK_NAME);
		buff.append("/tEK_NAME_K=").append(tEK_NAME_K);
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
		list.add(tEK_KBN);
		list.add(dATA_KBN);
		list.add(tEK_CODE);
		list.add(tEK_NAME);
		list.add(tEK_NAME_K);
		list.add(sTR_DATE);
		list.add(eND_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
