package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class DPK_MST extends MasterBase {

	/**  */
	public static final String TABLE = "DPK_MST";

	private String kAI_CODE = "";

	private String dPK_SSK = "";

	private String dPK_DEP_CODE = "";

	private int dPK_LVL;

	private String dPK_LVL_0;

	private String dPK_LVL_1;

	private String dPK_LVL_2;

	private String dPK_LVL_3;

	private String dPK_LVL_4;

	private String dPK_LVL_5;

	private String dPK_LVL_6;

	private String dPK_LVL_7;

	private String dPK_LVL_8;

	private String dPK_LVL_9;

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
	public String getDPK_SSK() {
		return dPK_SSK;
	}

	/**
	 * @param dPK_SSK
	 */
	public void setDPK_SSK(String dPK_SSK) {
		this.dPK_SSK = dPK_SSK;
	}

	/**
	 * @return String
	 */
	public String getDPK_DEP_CODE() {
		return dPK_DEP_CODE;
	}

	/**
	 * @param dPK_DEP_CODE
	 */
	public void setDPK_DEP_CODE(String dPK_DEP_CODE) {
		this.dPK_DEP_CODE = dPK_DEP_CODE;
	}

	/**
	 * @return intF
	 */
	public int getDPK_LVL() {
		return dPK_LVL;
	}

	/**
	 * @param dPK_LVL
	 */
	public void setDPK_LVL(int dPK_LVL) {
		this.dPK_LVL = dPK_LVL;
	}

	/**
	 * @return String
	 */
	public String getDPK_LVL_0() {
		return dPK_LVL_0;
	}

	/**
	 * @param dPK_LVL_0
	 */
	public void setDPK_LVL_0(String dPK_LVL_0) {
		this.dPK_LVL_0 = dPK_LVL_0;
	}

	/**
	 * @return String
	 */
	public String getDPK_LVL_1() {
		return dPK_LVL_1;
	}

	/**
	 * @param dPK_LVL_1
	 */
	public void setDPK_LVL_1(String dPK_LVL_1) {
		this.dPK_LVL_1 = dPK_LVL_1;
	}

	/**
	 * @return String
	 */
	public String getDPK_LVL_2() {
		return dPK_LVL_2;
	}

	/**
	 * @param dPK_LVL_2
	 */
	public void setDPK_LVL_2(String dPK_LVL_2) {
		this.dPK_LVL_2 = dPK_LVL_2;
	}

	/**
	 * @return String
	 */
	public String getDPK_LVL_3() {
		return dPK_LVL_3;
	}

	/**
	 * @param dPK_LVL_3
	 */
	public void setDPK_LVL_3(String dPK_LVL_3) {
		this.dPK_LVL_3 = dPK_LVL_3;
	}

	/**
	 * @return String
	 */
	public String getDPK_LVL_4() {
		return dPK_LVL_4;
	}

	/**
	 * @param dPK_LVL_4
	 */
	public void setDPK_LVL_4(String dPK_LVL_4) {
		this.dPK_LVL_4 = dPK_LVL_4;
	}

	/**
	 * @return String
	 */
	public String getDPK_LVL_5() {
		return dPK_LVL_5;
	}

	/**
	 * @param dPK_LVL_5
	 */
	public void setDPK_LVL_5(String dPK_LVL_5) {
		this.dPK_LVL_5 = dPK_LVL_5;
	}

	/**
	 * @return String
	 */
	public String getDPK_LVL_6() {
		return dPK_LVL_6;
	}

	/**
	 * @param dPK_LVL_6
	 */
	public void setDPK_LVL_6(String dPK_LVL_6) {
		this.dPK_LVL_6 = dPK_LVL_6;
	}

	/**
	 * @return String
	 */
	public String getDPK_LVL_7() {
		return dPK_LVL_7;
	}

	/**
	 * @param dPK_LVL_7
	 */
	public void setDPK_LVL_7(String dPK_LVL_7) {
		this.dPK_LVL_7 = dPK_LVL_7;
	}

	/**
	 * @return String
	 */
	public String getDPK_LVL_8() {
		return dPK_LVL_8;
	}

	/**
	 * @param dPK_LVL_8
	 */
	public void setDPK_LVL_8(String dPK_LVL_8) {
		this.dPK_LVL_8 = dPK_LVL_8;
	}

	/**
	 * @return String
	 */
	public String getDPK_LVL_9() {
		return dPK_LVL_9;
	}

	/**
	 * @param dPK_LVL_9
	 */
	public void setDPK_LVL_9(String dPK_LVL_9) {
		this.dPK_LVL_9 = dPK_LVL_9;
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
		buff.append("/dPK_SSK=").append(dPK_SSK);
		buff.append("/dPK_DEP_CODE=").append(dPK_DEP_CODE);
		buff.append("/dPK_LVL=").append(dPK_LVL);
		buff.append("/dPK_LVL_0=").append(dPK_LVL_0);
		buff.append("/dPK_LVL_1=").append(dPK_LVL_1);
		buff.append("/dPK_LVL_2=").append(dPK_LVL_2);
		buff.append("/dPK_LVL_3=").append(dPK_LVL_3);
		buff.append("/dPK_LVL_4=").append(dPK_LVL_4);
		buff.append("/dPK_LVL_5=").append(dPK_LVL_5);
		buff.append("/dPK_LVL_6=").append(dPK_LVL_6);
		buff.append("/dPK_LVL_7=").append(dPK_LVL_7);
		buff.append("/dPK_LVL_8=").append(dPK_LVL_8);
		buff.append("/dPK_LVL_9=").append(dPK_LVL_9);
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
		list.add(dPK_SSK);
		list.add(dPK_DEP_CODE);
		list.add(dPK_LVL);
		list.add(dPK_LVL_0);
		list.add(dPK_LVL_1);
		list.add(dPK_LVL_2);
		list.add(dPK_LVL_3);
		list.add(dPK_LVL_4);
		list.add(dPK_LVL_5);
		list.add(dPK_LVL_6);
		list.add(dPK_LVL_7);
		list.add(dPK_LVL_8);
		list.add(dPK_LVL_9);
		list.add(sTR_DATE);
		list.add(eND_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
