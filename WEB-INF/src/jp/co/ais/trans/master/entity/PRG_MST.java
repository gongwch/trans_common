package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * プログラムマスタ
 */
public class PRG_MST extends MasterBase {

	/** Entity */
	public static final String TABLE = "PRG_MST";

	private String kAI_CODE = "";

	private String sYS_CODE = "";

	private String pRG_CODE = "";

	private String pRG_NAME;

	private String pRG_NAME_S;

	private String pRG_NAME_K;

	/** 権限レベル */
	private Integer kEN = 9;

	private String cOM;

	private String lD_NAME;

	private Date sTR_DATE;

	private Date eND_DATE;

	private String pRG_ID;

	private String pARENT_PRG_CODE = "";

	private int mENU_KBN;

	private int dISP_INDEX;

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
	public String getSYS_CODE() {
		return sYS_CODE;
	}

	/**
	 * @param sYS_CODE
	 */
	public void setSYS_CODE(String sYS_CODE) {
		this.sYS_CODE = sYS_CODE;
	}

	/**
	 * @return String
	 */
	public String getPRG_CODE() {
		return pRG_CODE;
	}

	/**
	 * @param pRG_CODE
	 */
	public void setPRG_CODE(String pRG_CODE) {
		this.pRG_CODE = pRG_CODE;
	}

	/**
	 * @return String
	 */
	public String getPRG_NAME() {
		return pRG_NAME;
	}

	/**
	 * @param pRG_NAME
	 */
	public void setPRG_NAME(String pRG_NAME) {
		this.pRG_NAME = pRG_NAME;
	}

	/**
	 * @return String
	 */
	public String getPRG_NAME_S() {
		return pRG_NAME_S;
	}

	/**
	 * @param pRG_NAME_S
	 */
	public void setPRG_NAME_S(String pRG_NAME_S) {
		this.pRG_NAME_S = pRG_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getPRG_NAME_K() {
		return pRG_NAME_K;
	}

	/**
	 * @param pRG_NAME_K
	 */
	public void setPRG_NAME_K(String pRG_NAME_K) {
		this.pRG_NAME_K = pRG_NAME_K;
	}

	/**
	 * @return int
	 */
	public Integer getKEN() {
		return kEN;
	}

	/**
	 * @param kEN
	 */
	public void setKEN(Integer kEN) {
		this.kEN = (kEN == null) ? 9 : kEN.intValue();
	}

	/**
	 * @return String
	 */
	public String getCOM() {
		return cOM;
	}

	/**
	 * @param cOM
	 */
	public void setCOM(String cOM) {
		this.cOM = cOM;
	}

	/**
	 * @return String
	 */
	public String getLD_NAME() {
		return lD_NAME;
	}

	/**
	 * @param lD_NAME
	 */
	public void setLD_NAME(String lD_NAME) {
		this.lD_NAME = lD_NAME;
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

	/**
	 * @return String
	 */
	public String getPARENT_PRG_CODE() {
		return pARENT_PRG_CODE;
	}

	/**
	 * @param pARENT_PRG_CODE
	 */
	public void setPARENT_PRG_CODE(String pARENT_PRG_CODE) {
		this.pARENT_PRG_CODE = pARENT_PRG_CODE;
	}

	/**
	 * @return int
	 */
	public int getMENU_KBN() {
		return mENU_KBN;
	}

	/**
	 * @param mENU_KBN
	 */
	public void setMENU_KBN(int mENU_KBN) {
		this.mENU_KBN = mENU_KBN;
	}

	/**
	 * @return int
	 */
	public int getDISP_INDEX() {
		return dISP_INDEX;
	}

	/**
	 * @param dISP_INDEX
	 */
	public void setDISP_INDEX(int dISP_INDEX) {
		this.dISP_INDEX = dISP_INDEX;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/sYS_CODE=").append(sYS_CODE);
		buff.append("/pRG_CODE=").append(pRG_CODE);
		buff.append("/pRG_NAME=").append(pRG_NAME);
		buff.append("/pRG_NAME_S=").append(pRG_NAME_S);
		buff.append("/pRG_NAME_K=").append(pRG_NAME_K);
		buff.append("/kEN=").append(kEN);
		buff.append("/cOM=").append(cOM);
		buff.append("/lD_NAME=").append(lD_NAME);
		buff.append("/sTR_DATE=").append(sTR_DATE);
		buff.append("/eND_DATE=").append(eND_DATE);
		buff.append("/iNP_DATE=").append(iNP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("/pARENT_PRG_CODE=").append(pARENT_PRG_CODE);
		buff.append("/mENU_KBN=").append(mENU_KBN);
		buff.append("/dISP_INDEX=").append(dISP_INDEX);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(sYS_CODE);
		list.add(pRG_CODE);
		list.add(pRG_NAME);
		list.add(pRG_NAME_S);
		list.add(pRG_NAME_K);
		list.add(kEN);
		list.add(cOM);
		list.add(lD_NAME);
		list.add(sTR_DATE);
		list.add(eND_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);
		list.add(pARENT_PRG_CODE);
		list.add(mENU_KBN);
		list.add(dISP_INDEX);

		return list;
	}
}
