package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 通貨マスタエンティティ
 */
public class CUR_MST extends MasterBase {

	/**  */
	public static final String TABLE = "CUR_MST";

	private String kAI_CODE = "";

	private String cUR_CODE = "";

	private String cUR_NAME = "";

	private String cUR_NAME_S;

	private String cUR_NAME_K;

	private int dEC_KETA;

	private int rATE_POW;

	private int cONV_KBN;

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
	public String getCUR_CODE() {
		return cUR_CODE;
	}

	/**
	 * @param cUR_CODE
	 */
	public void setCUR_CODE(String cUR_CODE) {
		this.cUR_CODE = cUR_CODE;
	}

	/**
	 * @return String
	 */
	public String getCUR_NAME() {
		return cUR_NAME;
	}

	/**
	 * @param cUR_NAME
	 */
	public void setCUR_NAME(String cUR_NAME) {
		this.cUR_NAME = cUR_NAME;
	}

	/**
	 * @return String
	 */
	public String getCUR_NAME_S() {
		return cUR_NAME_S;
	}

	/**
	 * @param cUR_NAME_S
	 */
	public void setCUR_NAME_S(String cUR_NAME_S) {
		this.cUR_NAME_S = cUR_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getCUR_NAME_K() {
		return cUR_NAME_K;
	}

	/**
	 * @param cUR_NAME_K
	 */
	public void setCUR_NAME_K(String cUR_NAME_K) {
		this.cUR_NAME_K = cUR_NAME_K;
	}

	/**
	 * @return int
	 */
	public int getDEC_KETA() {
		return dEC_KETA;
	}

	/**
	 * @param dEC_KETA
	 */
	public void setDEC_KETA(int dEC_KETA) {
		this.dEC_KETA = dEC_KETA;
	}

	/**
	 * @return int
	 */
	public int getRATE_POW() {
		return rATE_POW;
	}

	/**
	 * @param rATE_POW
	 */
	public void setRATE_POW(int rATE_POW) {
		this.rATE_POW = rATE_POW;
	}

	/**
	 * @return int
	 */
	public int getCONV_KBN() {
		return cONV_KBN;
	}

	/**
	 * @param cONV_KBN
	 */
	public void setCONV_KBN(int cONV_KBN) {
		this.cONV_KBN = cONV_KBN;
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
		StringBuffer buff = new StringBuffer("");
		buff.append("").append(kAI_CODE);
		buff.append("/").append(cUR_CODE);
		buff.append("/").append(cUR_NAME);
		buff.append("/").append(cUR_NAME_S);
		buff.append("/").append(cUR_NAME_K);
		buff.append("/").append(rATE_POW);
		buff.append("/").append(cONV_KBN);
		buff.append("/").append(dEC_KETA);
		buff.append("/").append(sTR_DATE);
		buff.append("/").append(eND_DATE);
		buff.append("/").append(iNP_DATE);
		buff.append("/").append(uPD_DATE);
		buff.append("/").append(pRG_ID);
		buff.append("/").append(uSR_ID);
		buff.append("");
		return buff.toString();
	}

	/**
	 * servlet response用
	 */
	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(cUR_CODE);
		list.add(cUR_NAME);
		list.add(cUR_NAME_S);
		list.add(cUR_NAME_K);
		list.add(rATE_POW);
		list.add(cONV_KBN);
		list.add(dEC_KETA);
		list.add(sTR_DATE);
		list.add(eND_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
