package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class CM_SKN_MST extends MasterBase {

	/**  */
	public static final String TABLE = "CM_SKN_MST";

	private String sKN_KAI_CODE = "";

	private String sKN_CODE = "";

	private String sKN_NAME = "";

	private String sKN_NAME_S = "";

	private String sKN_NAME_K = "";

	private int sKN_SUM_KBN;

	private int sKN_SYU_KBN;

	private String sKN_SUM_CODE = "";

	private int sKN_PLN_KBN;

	private Date sKN_INP_DATE;

	private Date sKN_UPD_DATE;

	private String sKN_PRG_ID;

	private String sKN_USR_ID;

	/**
	 * @return String
	 */
	public String getSKN_KAI_CODE() {
		return sKN_KAI_CODE;
	}

	/**
	 * @param sKN_KAI_CODE
	 */
	public void setSKN_KAI_CODE(String sKN_KAI_CODE) {
		this.sKN_KAI_CODE = sKN_KAI_CODE;
	}

	/**
	 * @return String
	 */
	public String getSKN_CODE() {
		return sKN_CODE;
	}

	/**
	 * @param sKN_CODE
	 */
	public void setSKN_CODE(String sKN_CODE) {
		this.sKN_CODE = sKN_CODE;
	}

	/**
	 * @return String
	 */
	public String getSKN_NAME() {
		return sKN_NAME;
	}

	/**
	 * @param sKN_NAME
	 */
	public void setSKN_NAME(String sKN_NAME) {
		this.sKN_NAME = sKN_NAME;
	}

	/**
	 * @return String
	 */
	public String getSKN_NAME_S() {
		return sKN_NAME_S;
	}

	/**
	 * @param sKN_NAME_S
	 */
	public void setSKN_NAME_S(String sKN_NAME_S) {
		this.sKN_NAME_S = sKN_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getSKN_NAME_K() {
		return sKN_NAME_K;
	}

	/**
	 * @param sKN_NAME_K
	 */
	public void setSKN_NAME_K(String sKN_NAME_K) {
		this.sKN_NAME_K = sKN_NAME_K;
	}

	/**
	 * @return int
	 */
	public int getSKN_SUM_KBN() {
		return sKN_SUM_KBN;
	}

	/**
	 * @param sKN_SUM_KBN
	 */
	public void setSKN_SUM_KBN(int sKN_SUM_KBN) {
		this.sKN_SUM_KBN = sKN_SUM_KBN;
	}

	/**
	 * @return int
	 */
	public int getSKN_SYU_KBN() {
		return sKN_SYU_KBN;
	}

	/**
	 * @param sKN_SYU_KBN
	 */
	public void setSKN_SYU_KBN(int sKN_SYU_KBN) {
		this.sKN_SYU_KBN = sKN_SYU_KBN;
	}

	/**
	 * @return String
	 */
	public String getSKN_SUM_CODE() {
		return sKN_SUM_CODE;
	}

	/**
	 * @param sKN_SUM_CODE
	 */
	public void setSKN_SUM_CODE(String sKN_SUM_CODE) {
		this.sKN_SUM_CODE = sKN_SUM_CODE;
	}

	/**
	 * @return int
	 */
	public int getSKN_PLN_KBN() {
		return sKN_PLN_KBN;
	}

	/**
	 * @param sKN_PLN_KBN
	 */
	public void setSKN_PLN_KBN(int sKN_PLN_KBN) {
		this.sKN_PLN_KBN = sKN_PLN_KBN;
	}

	/**
	 * @return Date
	 */
	public Date getSKN_INP_DATE() {
		return sKN_INP_DATE;
	}

	/**
	 * @param sKN_INP_DATE
	 */
	public void setSKN_INP_DATE(Date sKN_INP_DATE) {
		this.sKN_INP_DATE = sKN_INP_DATE;
	}

	/**
	 * @return Date
	 */
	public Date getSKN_UPD_DATE() {
		return sKN_UPD_DATE;
	}

	/**
	 * @param sKN_UPD_DATE
	 */
	public void setSKN_UPD_DATE(Date sKN_UPD_DATE) {
		this.sKN_UPD_DATE = sKN_UPD_DATE;
	}

	/**
	 * @return String
	 */
	public String getSKN_PRG_ID() {
		return sKN_PRG_ID;
	}

	/**
	 * @param sKN_PRG_ID
	 */
	public void setSKN_PRG_ID(String sKN_PRG_ID) {
		this.sKN_PRG_ID = sKN_PRG_ID;
	}

	/**
	 * @return String
	 */
	public String getSKN_USR_ID() {
		return sKN_USR_ID;
	}

	/**
	 * @param sKN_USR_ID
	 */
	public void setSKN_USR_ID(String sKN_USR_ID) {
		this.sKN_USR_ID = sKN_USR_ID;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/sKN_KAI_CODE=").append(sKN_KAI_CODE);
		buff.append("/sKN_CODE=").append(sKN_CODE);
		buff.append("/sKN_NAME=").append(sKN_NAME);
		buff.append("/sKN_NAME_S=").append(sKN_NAME_S);
		buff.append("/sKN_NAME_K=").append(sKN_NAME_K);
		buff.append("/sKN_SUM_KBN=").append(sKN_SUM_KBN);
		buff.append("/sKN_SYU_KBN=").append(sKN_SYU_KBN);
		buff.append("/sKN_SUM_CODE=").append(sKN_SUM_CODE);
		buff.append("/sKN_PLN_KBN=").append(sKN_PLN_KBN);
		buff.append("/sKN_INP_DATE=").append(sKN_INP_DATE);
		buff.append("/sKN_UPD_DATE=").append(sKN_UPD_DATE);
		buff.append("/sKN_PRG_ID=").append(sKN_PRG_ID);
		buff.append("/sKN_USR_ID=").append(sKN_USR_ID);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(sKN_KAI_CODE);
		list.add(sKN_CODE);
		list.add(sKN_NAME);
		list.add(sKN_NAME_S);
		list.add(sKN_NAME_K);
		list.add(sKN_SUM_KBN);
		list.add(sKN_SYU_KBN);
		list.add(sKN_SUM_CODE);
		list.add(sKN_PLN_KBN);
		list.add(sKN_INP_DATE);
		list.add(sKN_UPD_DATE);
		list.add(sKN_PRG_ID);
		list.add(sKN_USR_ID);

		return list;
	}
}
