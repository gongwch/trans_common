package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class SIM_CTL extends MasterBase {

	/**  */
	public static final String TABLE = "SIM_CTL";

	/**  */
	public static final String kAI_CODE_ID = "sequence, sequenceName=kAI_CODE";

	private String kAI_CODE = "";

	private int nENDO;

	private Date sIM_STR_DATE;

	private Date sIM_END_DATE;

	private int sIM_MON;

	private int kSN_KBN;

	private Date nITIJI_DATE;

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
	 * @return int
	 */
	public int getNENDO() {
		return nENDO;
	}

	/**
	 * @param nENDO
	 */
	public void setNENDO(int nENDO) {
		this.nENDO = nENDO;
	}

	/**
	 * @return Date
	 */
	public Date getSIM_STR_DATE() {
		return sIM_STR_DATE;
	}

	/**
	 * @param sIM_STR_DATE
	 */
	public void setSIM_STR_DATE(Date sIM_STR_DATE) {
		this.sIM_STR_DATE = sIM_STR_DATE;
	}

	/**
	 * @return Date
	 */
	public Date getSIM_END_DATE() {
		return sIM_END_DATE;
	}

	/**
	 * @param sIM_END_DATE
	 */
	public void setSIM_END_DATE(Date sIM_END_DATE) {
		this.sIM_END_DATE = sIM_END_DATE;
	}

	/**
	 * @return int
	 */
	public int getSIM_MON() {
		return sIM_MON;
	}

	/**
	 * @param sIM_MON
	 */
	public void setSIM_MON(int sIM_MON) {
		this.sIM_MON = sIM_MON;
	}

	/**
	 * @return int
	 */
	public int getKSN_KBN() {
		return kSN_KBN;
	}

	/**
	 * @param kSN_KBN
	 */
	public void setKSN_KBN(int kSN_KBN) {
		this.kSN_KBN = kSN_KBN;
	}

	/**
	 * @return Date
	 */
	public Date getNITIJI_DATE() {
		return nITIJI_DATE;
	}

	/**
	 * @param nITIJI_DATE
	 */
	public void setNITIJI_DATE(Date nITIJI_DATE) {
		this.nITIJI_DATE = nITIJI_DATE;
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
		buff.append("/nENDO=").append(nENDO);
		buff.append("/sIM_STR_DATE=").append(sIM_STR_DATE);
		buff.append("/sIM_END_DATE=").append(sIM_END_DATE);
		buff.append("/sIM_MON=").append(sIM_MON);
		buff.append("/kSN_KBN=").append(kSN_KBN);
		buff.append("/nITIJI_DATE=").append(nITIJI_DATE);
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
		list.add(nENDO);
		list.add(sIM_STR_DATE);
		list.add(sIM_END_DATE);
		list.add(sIM_MON);
		list.add(kSN_KBN);
		list.add(nITIJI_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
