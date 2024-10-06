package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class KMK_TK_MST extends MasterBase {

	/**  */
	public static final String TABLE = "KMK_TK_MST";

	private String kAI_CODE = "";

	private String kMT_CODE = "";

	private String kMT_NAME;

	private String kMT_NAME_S;

	private String kMT_NAME_K;

	private String pRG_ID;

	/**
	 * @return String
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * @param kai_code
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * @return String
	 */
	public String getKMT_CODE() {
		return kMT_CODE;
	}

	/**
	 * @param kmt_code
	 */
	public void setKMT_CODE(String kmt_code) {
		kMT_CODE = kmt_code;
	}

	/**
	 * @return String
	 */
	public String getKMT_NAME() {
		return kMT_NAME;
	}

	/**
	 * @param kmt_name
	 */
	public void setKMT_NAME(String kmt_name) {
		kMT_NAME = kmt_name;
	}

	/**
	 * @return String
	 */
	public String getKMT_NAME_K() {
		return kMT_NAME_K;
	}

	/**
	 * @param kmt_name_k
	 */
	public void setKMT_NAME_K(String kmt_name_k) {
		kMT_NAME_K = kmt_name_k;
	}

	/**
	 * @return String
	 */
	public String getKMT_NAME_S() {
		return kMT_NAME_S;
	}

	/**
	 * @param kmt_name_s
	 */
	public void setKMT_NAME_S(String kmt_name_s) {
		kMT_NAME_S = kmt_name_s;
	}

	/**
	 * @return String
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * @param prg_id
	 */
	public void setPRG_ID(String prg_id) {
		pRG_ID = prg_id;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/kMT_CODE=").append(kMT_CODE);
		buff.append("/kMT_NAME=").append(kMT_NAME);
		buff.append("/kMT_NAME_S=").append(kMT_NAME_S);
		buff.append("/kMT_NAME_K=").append(kMT_NAME_K);
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
		list.add(kMT_CODE);
		list.add(kMT_NAME);
		list.add(kMT_NAME_S);
		list.add(kMT_NAME_K);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
