package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class BNK_MST extends MasterBase {

	/**  */
	public static final String TABLE = "BNK_MST";

	private String bNK_CODE = "";

	private String bNK_STN_CODE = "";

	private String bNK_NAME_S;

	private String bNK_KANA = "";

	private String bNK_NAME_K;

	private String bNK_STN_NAME_S;

	private String bNK_STN_KANA = "";

	private String bNK_STN_NAME_K;

	private Date sTR_DATE;

	private Date eND_DATE;

	private String pRG_ID;

	/**
	 * @return String
	 */
	public String getBNK_CODE() {
		return bNK_CODE;
	}

	/**
	 * @param bNK_CODE
	 */
	public void setBNK_CODE(String bNK_CODE) {
		this.bNK_CODE = bNK_CODE;
	}

	/**
	 * @return String
	 */
	public String getBNK_STN_CODE() {
		return bNK_STN_CODE;
	}

	/**
	 * @param bNK_STN_CODE
	 */
	public void setBNK_STN_CODE(String bNK_STN_CODE) {
		this.bNK_STN_CODE = bNK_STN_CODE;
	}

	/**
	 * @return String
	 */
	public String getBNK_NAME_S() {
		return bNK_NAME_S;
	}

	/**
	 * @param bNK_NAME_S
	 */
	public void setBNK_NAME_S(String bNK_NAME_S) {
		this.bNK_NAME_S = bNK_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getBNK_KANA() {
		return bNK_KANA;
	}

	/**
	 * @param bNK_KANA
	 */
	public void setBNK_KANA(String bNK_KANA) {
		this.bNK_KANA = bNK_KANA;
	}

	/**
	 * @return String
	 */
	public String getBNK_NAME_K() {
		return bNK_NAME_K;
	}

	/**
	 * @param bNK_NAME_K
	 */
	public void setBNK_NAME_K(String bNK_NAME_K) {
		this.bNK_NAME_K = bNK_NAME_K;
	}

	/**
	 * @return String
	 */
	public String getBNK_STN_NAME_S() {
		return bNK_STN_NAME_S;
	}

	/**
	 * @param bNK_STN_NAME_S
	 */
	public void setBNK_STN_NAME_S(String bNK_STN_NAME_S) {
		this.bNK_STN_NAME_S = bNK_STN_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getBNK_STN_KANA() {
		return bNK_STN_KANA;
	}

	/**
	 * @param bNK_STN_KANA
	 */
	public void setBNK_STN_KANA(String bNK_STN_KANA) {
		this.bNK_STN_KANA = bNK_STN_KANA;
	}

	/**
	 * @return String
	 */
	public String getBNK_STN_NAME_K() {
		return bNK_STN_NAME_K;
	}

	/**
	 * @param bNK_STN_NAME_K
	 */
	public void setBNK_STN_NAME_K(String bNK_STN_NAME_K) {
		this.bNK_STN_NAME_K = bNK_STN_NAME_K;
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
		buff.append("/bNK_CODE=").append(bNK_CODE);
		buff.append("/bNK_STN_CODE=").append(bNK_STN_CODE);
		buff.append("/bNK_NAME_S=").append(bNK_NAME_S);
		buff.append("/bNK_KANA=").append(bNK_KANA);
		buff.append("/bNK_NAME_K=").append(bNK_NAME_K);
		buff.append("/bNK_STN_NAME_S=").append(bNK_STN_NAME_S);
		buff.append("/bNK_STN_KANA=").append(bNK_STN_KANA);
		buff.append("/bNK_STN_NAME_K=").append(bNK_STN_NAME_K);
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

		list.add(bNK_CODE);
		list.add(bNK_STN_CODE);
		list.add(bNK_NAME_S);
		list.add(bNK_KANA);
		list.add(bNK_NAME_K);
		list.add(bNK_STN_NAME_S);
		list.add(bNK_STN_KANA);
		list.add(bNK_STN_NAME_K);
		list.add(sTR_DATE);
		list.add(eND_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
