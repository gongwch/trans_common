package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class HAITA_CTL extends MasterBase {

	/**  */
	public static final String TABLE = "HAITA_CTL";

	private String kAI_CODE = "";

	private String sHORI_KBN = "";

	private String tRI_CODE = "";

	private String gYO_NO = "";

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
	public String getSHORI_KBN() {
		return sHORI_KBN;
	}

	/**
	 * @param sHORI_KBN
	 */
	public void setSHORI_KBN(String sHORI_KBN) {
		this.sHORI_KBN = sHORI_KBN;
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
	public String getGYO_NO() {
		return gYO_NO;
	}

	/**
	 * @param gYO_NO
	 */
	public void setGYO_NO(String gYO_NO) {
		this.gYO_NO = gYO_NO;
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
		buff.append("/sHORI_KBN=").append(sHORI_KBN);
		buff.append("/tRI_CODE=").append(tRI_CODE);
		buff.append("/gYO_NO=").append(gYO_NO);
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
		list.add(sHORI_KBN);
		list.add(tRI_CODE);
		list.add(gYO_NO);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
