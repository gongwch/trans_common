package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class LNG_MST extends MasterBase {

	/**  */
	public static final String TABLE = "LNG_MST";

	private String kAI_CODE = "";

	private String lNG_CODE = "";

	private String fILE_PATH = "";

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
	public String getLNG_CODE() {
		return lNG_CODE;
	}

	/**
	 * @param lNG_CODE
	 */
	public void setLNG_CODE(String lNG_CODE) {
		this.lNG_CODE = lNG_CODE;
	}

	/**
	 * @return String
	 */
	public String getFILE_PATH() {
		return fILE_PATH;
	}

	/**
	 * @param fILE_PATH
	 */
	public void setFILE_PATH(String fILE_PATH) {
		this.fILE_PATH = fILE_PATH;
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
		buff.append("/lNG_CODE=").append(lNG_CODE);
		buff.append("/fILE_PATH=").append(fILE_PATH);
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
		list.add(lNG_CODE);
		list.add(fILE_PATH);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
