package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class BAT_CTL extends MasterBase {

	/**  */
	public static final String TABLE = "BAT_CTL";

	private String kAI_CODE = "";

	private String bAT_ID = "";

	private Date bAT_STR_DATE;

	private Date bAT_END_DATE;

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
	public String getBAT_ID() {
		return bAT_ID;
	}

	/**
	 * @param bAT_ID
	 */
	public void setBAT_ID(String bAT_ID) {
		this.bAT_ID = bAT_ID;
	}

	/**
	 * @return Date
	 */
	public Date getBAT_STR_DATE() {
		return bAT_STR_DATE;
	}

	/**
	 * @param bAT_STR_DATE
	 */
	public void setBAT_STR_DATE(Date bAT_STR_DATE) {
		this.bAT_STR_DATE = bAT_STR_DATE;
	}

	/**
	 * @return Date
	 */
	public Date getBAT_END_DATE() {
		return bAT_END_DATE;
	}

	/**
	 * @param bAT_END_DATE
	 */
	public void setBAT_END_DATE(Date bAT_END_DATE) {
		this.bAT_END_DATE = bAT_END_DATE;
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
		buff.append("/bAT_ID=").append(bAT_ID);
		buff.append("/bAT_STR_DATE=").append(bAT_STR_DATE);
		buff.append("/bAT_END_DATE=").append(bAT_END_DATE);
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
		list.add(bAT_ID);
		list.add(bAT_STR_DATE);
		list.add(bAT_END_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
