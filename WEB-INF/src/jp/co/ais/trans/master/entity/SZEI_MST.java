package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class SZEI_MST extends MasterBase {

	/**
	 * 消費税売上仕入区分 対象外
	 */
	public static final int ZEI_US_KBN_NONE = 0;

	/**
	 * 消費税売上仕入区分 売上
	 */
	public static final int ZEI_US_KBN_URI = 1;

	/**
	 * 消費税売上仕入区分 仕入
	 */
	public static final int ZEI_US_KBN_SIR = 2;

	/**  */
	public static final String TABLE = "SZEI_MST";

	private String kAI_CODE = "";

	private String zEI_CODE = "";

	private String zEI_NAME;

	private String zEI_NAME_S;

	private String zEI_NAME_K;

	private int uS_KBN;

	private Float zEI_RATE;

	private String sZEI_KEI_KBN;

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
	public String getZEI_CODE() {
		return zEI_CODE;
	}

	/**
	 * @param zEI_CODE
	 */
	public void setZEI_CODE(String zEI_CODE) {
		this.zEI_CODE = zEI_CODE;
	}

	/**
	 * @return String
	 */
	public String getZEI_NAME() {
		return zEI_NAME;
	}

	/**
	 * @param zEI_NAME
	 */
	public void setZEI_NAME(String zEI_NAME) {
		this.zEI_NAME = zEI_NAME;
	}

	/**
	 * @return String
	 */
	public String getZEI_NAME_S() {
		return zEI_NAME_S;
	}

	/**
	 * @param zEI_NAME_S
	 */
	public void setZEI_NAME_S(String zEI_NAME_S) {
		this.zEI_NAME_S = zEI_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getZEI_NAME_K() {
		return zEI_NAME_K;
	}

	/**
	 * @param zEI_NAME_K
	 */
	public void setZEI_NAME_K(String zEI_NAME_K) {
		this.zEI_NAME_K = zEI_NAME_K;
	}

	/**
	 * @return int
	 */
	public int getUS_KBN() {
		return uS_KBN;
	}

	/**
	 * @param uS_KBN
	 */
	public void setUS_KBN(int uS_KBN) {
		this.uS_KBN = uS_KBN;
	}

	/**
	 * @return Float
	 */
	public Float getZEI_RATE() {
		return zEI_RATE;
	}

	/**
	 * @param zEI_RATE
	 */
	public void setZEI_RATE(Float zEI_RATE) {
		this.zEI_RATE = zEI_RATE;
	}

	/**
	 * @return String
	 */
	public String getSZEI_KEI_KBN() {
		return sZEI_KEI_KBN;
	}

	/**
	 * @param sZEI_KEI_KBN
	 */
	public void setSZEI_KEI_KBN(String sZEI_KEI_KBN) {
		this.sZEI_KEI_KBN = sZEI_KEI_KBN;
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
		buff.append("/zEI_CODE=").append(zEI_CODE);
		buff.append("/zEI_NAME=").append(zEI_NAME);
		buff.append("/zEI_NAME_S=").append(zEI_NAME_S);
		buff.append("/zEI_NAME_K=").append(zEI_NAME_K);
		buff.append("/uS_KBN=").append(uS_KBN);
		buff.append("/zEI_RATE=").append(zEI_RATE);
		buff.append("/sZEI_KEI_KBN=").append(sZEI_KEI_KBN);
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
		list.add(zEI_CODE);
		list.add(zEI_NAME);
		list.add(zEI_NAME_S);
		list.add(zEI_NAME_K);
		list.add(uS_KBN);
		list.add(zEI_RATE);
		list.add(sZEI_KEI_KBN);
		list.add(sTR_DATE);
		list.add(eND_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}

	/**
	 * 売上課税かどうか
	 * 
	 * @return true:売上
	 */
	public boolean isSales() {
		return uS_KBN == ZEI_US_KBN_URI;
	}

	/**
	 * 仕入課税かどうか
	 * 
	 * @return true:仕入
	 */
	public boolean isStock() {
		return uS_KBN == ZEI_US_KBN_SIR;
	}
}
