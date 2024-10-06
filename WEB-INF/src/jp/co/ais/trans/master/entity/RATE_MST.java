package jp.co.ais.trans.master.entity;

import java.math.*;
import java.util.*;

/**
 * 
 */
public class RATE_MST extends MasterBase {

	/**  */
	public static final String TABLE = "RATE_MST";

	private String kAI_CODE = "";

	private String cUR_CODE = "";

	private Date sTR_DATE;

	private BigDecimal cUR_RATE;

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
	 * @return BigDecimal
	 */
	public BigDecimal getCUR_RATE() {
		return cUR_RATE;
	}

	/**
	 * @param cUR_RATE
	 */
	public void setCUR_RATE(BigDecimal cUR_RATE) {
		this.cUR_RATE = cUR_RATE;
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

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/cUR_CODE=").append(cUR_CODE);
		buff.append("/sTR_DATE=").append(sTR_DATE);
		buff.append("/cUR_RATE=").append(cUR_RATE);
		buff.append("/iNP_DATE=").append(iNP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("]");
		return buff.toString();
	}

	@Override
	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(cUR_CODE);
		list.add(sTR_DATE);
		list.add(cUR_RATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
