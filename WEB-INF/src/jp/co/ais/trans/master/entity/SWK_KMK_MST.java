package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class SWK_KMK_MST extends MasterBase {

	/**  */
	public static final String TABLE = "SWK_KMK_MST";

	private String kAI_CODE = "";

	private int kMK_CNT;

	private String kMK_CNT_NAME;

	private String kMK_CODE;

	private String hKM_CODE;

	private String uKM_CODE;

	private String dEP_CODE;

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
	public int getKMK_CNT() {
		return kMK_CNT;
	}

	/**
	 * @param kMK_CNT
	 */
	public void setKMK_CNT(int kMK_CNT) {
		this.kMK_CNT = kMK_CNT;
	}

	/**
	 * @return String
	 */
	public String getKMK_CNT_NAME() {
		return kMK_CNT_NAME;
	}

	/**
	 * @param kMK_CNT_NAME
	 */
	public void setKMK_CNT_NAME(String kMK_CNT_NAME) {
		this.kMK_CNT_NAME = kMK_CNT_NAME;
	}

	/**
	 * @return String
	 */
	public String getKMK_CODE() {
		return kMK_CODE;
	}

	/**
	 * @param kMK_CODE
	 */
	public void setKMK_CODE(String kMK_CODE) {
		this.kMK_CODE = kMK_CODE;
	}

	/**
	 * @return String
	 */
	public String getHKM_CODE() {
		return hKM_CODE;
	}

	/**
	 * @param hKM_CODE
	 */
	public void setHKM_CODE(String hKM_CODE) {
		this.hKM_CODE = hKM_CODE;
	}

	/**
	 * @return String
	 */
	public String getUKM_CODE() {
		return uKM_CODE;
	}

	/**
	 * @param uKM_CODE
	 */
	public void setUKM_CODE(String uKM_CODE) {
		this.uKM_CODE = uKM_CODE;
	}

	/**
	 * @return String
	 */
	public String getDEP_CODE() {
		return dEP_CODE;
	}

	/**
	 * @param dEP_CODE
	 */
	public void setDEP_CODE(String dEP_CODE) {
		this.dEP_CODE = dEP_CODE;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/kMK_CNT=").append(kMK_CNT);
		buff.append("/kMK_CNT_NAME=").append(kMK_CNT_NAME);
		buff.append("/kMK_CODE=").append(kMK_CODE);
		buff.append("/hKM_CODE=").append(hKM_CODE);
		buff.append("/uKM_CODE=").append(uKM_CODE);
		buff.append("/dEP_CODE=").append(dEP_CODE);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(kMK_CNT);
		list.add(kMK_CNT_NAME);
		list.add(kMK_CODE);
		list.add(hKM_CODE);
		list.add(uKM_CODE);
		list.add(dEP_CODE);

		return list;
	}
}
