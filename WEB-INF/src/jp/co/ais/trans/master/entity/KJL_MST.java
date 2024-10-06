package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class KJL_MST extends MasterBase {

	/**  */
	public static final String TABLE = "KJL_MST";

	private String kAI_CODE = "";

	private String kJL_USR_ID = "";

	private String kJL_KMT_CODE = "";

	private String kJL_DPK_SSK = "";

	private int kJL_LVL;

	private String kJL_UP_DEP_CODE;

	private String kJL_DEP_CODE = "";

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
	public String getKJL_USR_ID() {
		return kJL_USR_ID;
	}

	/**
	 * @param kJL_USR_ID
	 */
	public void setKJL_USR_ID(String kJL_USR_ID) {
		this.kJL_USR_ID = kJL_USR_ID;
	}

	/**
	 * @return String
	 */
	public String getKJL_KMT_CODE() {
		return kJL_KMT_CODE;
	}

	/**
	 * @param kJL_KMT_CODE
	 */
	public void setKJL_KMT_CODE(String kJL_KMT_CODE) {
		this.kJL_KMT_CODE = kJL_KMT_CODE;
	}

	/**
	 * @return String
	 */
	public String getKJL_DPK_SSK() {
		return kJL_DPK_SSK;
	}

	/**
	 * @param kJL_DPK_SSK
	 */
	public void setKJL_DPK_SSK(String kJL_DPK_SSK) {
		this.kJL_DPK_SSK = kJL_DPK_SSK;
	}

	/**
	 * @return String
	 */
	public int getKJL_LVL() {
		return kJL_LVL;
	}

	/**
	 * @param kJL_LVL
	 */
	public void setKJL_LVL(int kJL_LVL) {
		this.kJL_LVL = kJL_LVL;
	}

	/**
	 * @return String
	 */
	public String getKJL_UP_DEP_CODE() {
		return kJL_UP_DEP_CODE;
	}

	/**
	 * @param kJL_UP_DEP_CODE
	 */
	public void setKJL_UP_DEP_CODE(String kJL_UP_DEP_CODE) {
		this.kJL_UP_DEP_CODE = kJL_UP_DEP_CODE;
	}

	/**
	 * @return String
	 */
	public String getKJL_DEP_CODE() {
		return kJL_DEP_CODE;
	}

	/**
	 * @param kJL_DEP_CODE
	 */
	public void setKJL_DEP_CODE(String kJL_DEP_CODE) {
		this.kJL_DEP_CODE = kJL_DEP_CODE;
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
		buff.append("/kJL_USR_ID=").append(kJL_USR_ID);
		buff.append("/kJL_KMT_CODE=").append(kJL_KMT_CODE);
		buff.append("/kJL_DPK_SSK=").append(kJL_DPK_SSK);
		buff.append("/kJL_LVL=").append(kJL_LVL);
		buff.append("/kJL_UP_DEP_CODE=").append(kJL_UP_DEP_CODE);
		buff.append("/kJL_DEP_CODE=").append(kJL_DEP_CODE);
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
		list.add(kJL_USR_ID);
		list.add(kJL_KMT_CODE);
		list.add(kJL_DPK_SSK);
		list.add(kJL_LVL);
		list.add(kJL_UP_DEP_CODE);
		list.add(kJL_DEP_CODE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
