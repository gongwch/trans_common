package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class KNR6_MST extends MasterBase {

	/**  */
	public static final String TABLE = "KNR6_MST";

	private String kAI_CODE = "";

	private String kNR_CODE_6 = "";

	private String kNR_NAME_6 = "";

	private String kNR_NAME_S_6;

	private String kNR_NAME_K_6;

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
	public String getKNR_CODE_6() {
		return kNR_CODE_6;
	}

	/**
	 * @param kNR_CODE_6
	 */
	public void setKNR_CODE_6(String kNR_CODE_6) {
		this.kNR_CODE_6 = kNR_CODE_6;
	}

	/**
	 * @return String
	 */
	public String getKNR_NAME_6() {
		return kNR_NAME_6;
	}

	/**
	 * @param kNR_NAME_6
	 */
	public void setKNR_NAME_6(String kNR_NAME_6) {
		this.kNR_NAME_6 = kNR_NAME_6;
	}

	/**
	 * @return String
	 */
	public String getKNR_NAME_S_6() {
		return kNR_NAME_S_6;
	}

	/**
	 * @param kNR_NAME_S_6
	 */
	public void setKNR_NAME_S_6(String kNR_NAME_S_6) {
		this.kNR_NAME_S_6 = kNR_NAME_S_6;
	}

	/**
	 * @return String
	 */
	public String getKNR_NAME_K_6() {
		return kNR_NAME_K_6;
	}

	/**
	 * @param kNR_NAME_K_6
	 */
	public void setKNR_NAME_K_6(String kNR_NAME_K_6) {
		this.kNR_NAME_K_6 = kNR_NAME_K_6;
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
		buff.append("/kNR_CODE_6=").append(kNR_CODE_6);
		buff.append("/kNR_NAME_6=").append(kNR_NAME_6);
		buff.append("/kNR_NAME_S_6=").append(kNR_NAME_S_6);
		buff.append("/kNR_NAME_K_6=").append(kNR_NAME_K_6);
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
		list.add(kNR_CODE_6);
		list.add(kNR_NAME_6);
		list.add(kNR_NAME_S_6);
		list.add(kNR_NAME_K_6);
		list.add(sTR_DATE);
		list.add(eND_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}