package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class KTK_MST extends MasterBase {

	/**  */
	public static final String TABLE = "KTK_MST";

	private String kAI_CODE = "";

	private String kTK_KAI_CODE = "";

	private String kTK_DEP_CODE = "";

	private String kTK_KMK_CODE = "";

	private String kTK_HKM_CODE;

	private String kTK_UKM_CODE;

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
	public String getKTK_KAI_CODE() {
		return kTK_KAI_CODE;
	}

	/**
	 * @param kTK_KAI_CODE
	 */
	public void setKTK_KAI_CODE(String kTK_KAI_CODE) {
		this.kTK_KAI_CODE = kTK_KAI_CODE;
	}

	/**
	 * @return String
	 */
	public String getKTK_DEP_CODE() {
		return kTK_DEP_CODE;
	}

	/**
	 * @param kTK_DEP_CODE
	 */
	public void setKTK_DEP_CODE(String kTK_DEP_CODE) {
		this.kTK_DEP_CODE = kTK_DEP_CODE;
	}

	/**
	 * @return String
	 */
	public String getKTK_KMK_CODE() {
		return kTK_KMK_CODE;
	}

	/**
	 * @param kTK_KMK_CODE
	 */
	public void setKTK_KMK_CODE(String kTK_KMK_CODE) {
		this.kTK_KMK_CODE = kTK_KMK_CODE;
	}

	/**
	 * @return String
	 */
	public String getKTK_HKM_CODE() {
		return kTK_HKM_CODE;
	}

	/**
	 * @param kTK_HKM_CODE
	 */
	public void setKTK_HKM_CODE(String kTK_HKM_CODE) {
		this.kTK_HKM_CODE = kTK_HKM_CODE;
	}

	/**
	 * @return String
	 */
	public String getKTK_UKM_CODE() {
		return kTK_UKM_CODE;
	}

	/**
	 * @param kTK_UKM_CODE
	 */
	public void setKTK_UKM_CODE(String kTK_UKM_CODE) {
		this.kTK_UKM_CODE = kTK_UKM_CODE;
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
		buff.append("/kTK_KAI_CODE=").append(kTK_KAI_CODE);
		buff.append("/kTK_DEP_CODE=").append(kTK_DEP_CODE);
		buff.append("/kTK_KMK_CODE=").append(kTK_KMK_CODE);
		buff.append("/kTK_HKM_CODE=").append(kTK_HKM_CODE);
		buff.append("/kTK_UKM_CODE=").append(kTK_UKM_CODE);
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
		list.add(kTK_KAI_CODE);
		list.add(kTK_DEP_CODE);
		list.add(kTK_KMK_CODE);
		list.add(kTK_HKM_CODE);
		list.add(kTK_UKM_CODE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
