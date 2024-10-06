package jp.co.ais.trans.master.entity;

import java.math.*;
import java.util.*;

/**
 * 
 */
public class BMN_MST extends MasterBase {

	/**  */
	public static final String TABLE = "BMN_MST";

	private String kAI_CODE = "";

	private String dEP_CODE = "";

	private String dEP_NAME;

	private String dEP_NAME_S;

	private String dEP_NAME_K;

	private Integer mEN_1;

	private Integer mEN_2;

	private BigDecimal aREA;

	private int dEP_KBN;

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
	public String getDEP_CODE() {
		return dEP_CODE;
	}

	/**
	 * @param dEP_CODE
	 */
	public void setDEP_CODE(String dEP_CODE) {
		this.dEP_CODE = dEP_CODE;
	}

	/**
	 * @return String
	 */
	public String getDEP_NAME() {
		return dEP_NAME;
	}

	/**
	 * @param dEP_NAME
	 */
	public void setDEP_NAME(String dEP_NAME) {
		this.dEP_NAME = dEP_NAME;
	}

	/**
	 * @return String
	 */
	public String getDEP_NAME_S() {
		return dEP_NAME_S;
	}

	/**
	 * @param dEP_NAME_S
	 */
	public void setDEP_NAME_S(String dEP_NAME_S) {
		this.dEP_NAME_S = dEP_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getDEP_NAME_K() {
		return dEP_NAME_K;
	}

	/**
	 * @param dEP_NAME_K
	 */
	public void setDEP_NAME_K(String dEP_NAME_K) {
		this.dEP_NAME_K = dEP_NAME_K;
	}

	/**
	 * @return Integer
	 */
	public Integer getMEN_1() {
		return mEN_1;
	}

	/**
	 * @param mEN_1
	 */
	public void setMEN_1(Integer mEN_1) {
		this.mEN_1 = mEN_1;
	}

	/**
	 * @return Integer
	 */
	public Integer getMEN_2() {
		return mEN_2;
	}

	/**
	 * @param mEN_2
	 */
	public void setMEN_2(Integer mEN_2) {
		this.mEN_2 = mEN_2;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getAREA() {
		return aREA;
	}

	/**
	 * @param sPACE
	 */
	public void setAREA(BigDecimal sPACE) {
		this.aREA = sPACE;
	}

	/**
	 * @return int
	 */
	public int getDEP_KBN() {
		return dEP_KBN;
	}

	/**
	 * @param dEP_KBN
	 */
	public void setDEP_KBN(int dEP_KBN) {
		this.dEP_KBN = dEP_KBN;
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
		buff.append("/dEP_CODE=").append(dEP_CODE);
		buff.append("/dEP_NAME=").append(dEP_NAME);
		buff.append("/dEP_NAME_S=").append(dEP_NAME_S);
		buff.append("/dEP_NAME_K=").append(dEP_NAME_K);
		buff.append("/mEN_1=").append(mEN_1);
		buff.append("/mEN_2=").append(mEN_2);
		buff.append("/sPACE=").append(aREA);
		buff.append("/dEP_KBN=").append(dEP_KBN);
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
		list.add(dEP_CODE);
		list.add(dEP_NAME);
		list.add(dEP_NAME_S);
		list.add(dEP_NAME_K);
		list.add(mEN_1);
		list.add(mEN_2);
		list.add(aREA);
		list.add(dEP_KBN);
		list.add(sTR_DATE);
		list.add(eND_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
