package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class KNR5_MST extends MasterBase {

	/**  */
	public static final String TABLE = "KNR5_MST";

	private String kAI_CODE = "";

	private String kNR_CODE_5 = "";

	private String kNR_NAME_5 = "";

	private String kNR_NAME_S_5;

	private String kNR_NAME_K_5;

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
	public String getKNR_CODE_5() {
		return kNR_CODE_5;
	}

	/**
	 * @param kNR_CODE_5
	 */
	public void setKNR_CODE_5(String kNR_CODE_5) {
		this.kNR_CODE_5 = kNR_CODE_5;
	}

	/**
	 * @return String
	 */
	public String getKNR_NAME_5() {
		return kNR_NAME_5;
	}

	/**
	 * @param kNR_NAME_5
	 */
	public void setKNR_NAME_5(String kNR_NAME_5) {
		this.kNR_NAME_5 = kNR_NAME_5;
	}

	/**
	 * @return String
	 */
	public String getKNR_NAME_S_5() {
		return kNR_NAME_S_5;
	}

	/**
	 * @param kNR_NAME_S_5
	 */
	public void setKNR_NAME_S_5(String kNR_NAME_S_5) {
		this.kNR_NAME_S_5 = kNR_NAME_S_5;
	}

	/**
	 * @return String
	 */
	public String getKNR_NAME_K_5() {
		return kNR_NAME_K_5;
	}

	/**
	 * @param kNR_NAME_K_5
	 */
	public void setKNR_NAME_K_5(String kNR_NAME_K_5) {
		this.kNR_NAME_K_5 = kNR_NAME_K_5;
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
		buff.append("/kNR_CODE_5=").append(kNR_CODE_5);
		buff.append("/kNR_NAME_5=").append(kNR_NAME_5);
		buff.append("/kNR_NAME_S_5=").append(kNR_NAME_S_5);
		buff.append("/kNR_NAME_K_5=").append(kNR_NAME_K_5);
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
		list.add(kNR_CODE_5);
		list.add(kNR_NAME_5);
		list.add(kNR_NAME_S_5);
		list.add(kNR_NAME_K_5);
		list.add(sTR_DATE);
		list.add(eND_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
