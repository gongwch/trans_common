package jp.co.ais.trans.master.entity;

import java.util.*;
import java.util.Date;

public class AP_HOH_MST extends MasterBase {
	public static final String TABLE = "AP_HOH_MST";

	private String kAI_CODE = "";

	private String hOH_HOH_CODE = "";

	private String hOH_HOH_NAME;

	private String hOH_HOH_NAME_K;

	private String hOH_KMK_CODE;

	private String hOH_HKM_CODE;

	private String hOH_UKM_CODE;

	private String hOH_DEP_CODE;

	private int hOH_SIH_KBN;

	private String hOH_NAI_CODE;

	private Date sTR_DATE;

	private Date eND_DATE;

	private Date hOH_INP_DATE;

	private String pRG_ID;

	public String getKAI_CODE() {
		return kAI_CODE;
	}

	public void setKAI_CODE(String kAI_CODE) {
		this.kAI_CODE = kAI_CODE;
	}

	public String getHOH_HOH_CODE() {
		return hOH_HOH_CODE;
	}

	public void setHOH_HOH_CODE(String hOH_HOH_CODE) {
		this.hOH_HOH_CODE = hOH_HOH_CODE;
	}

	public String getHOH_HOH_NAME() {
		return hOH_HOH_NAME;
	}

	public void setHOH_HOH_NAME(String hOH_HOH_NAME) {
		this.hOH_HOH_NAME = hOH_HOH_NAME;
	}

	public String getHOH_HOH_NAME_K() {
		return hOH_HOH_NAME_K;
	}

	public void setHOH_HOH_NAME_K(String hOH_HOH_NAME_K) {
		this.hOH_HOH_NAME_K = hOH_HOH_NAME_K;
	}

	public String getHOH_KMK_CODE() {
		return hOH_KMK_CODE;
	}

	public void setHOH_KMK_CODE(String hOH_KMK_CODE) {
		this.hOH_KMK_CODE = hOH_KMK_CODE;
	}

	public String getHOH_HKM_CODE() {
		return hOH_HKM_CODE;
	}

	public void setHOH_HKM_CODE(String hOH_HKM_CODE) {
		this.hOH_HKM_CODE = hOH_HKM_CODE;
	}

	public String getHOH_UKM_CODE() {
		return hOH_UKM_CODE;
	}

	public void setHOH_UKM_CODE(String hOH_UKM_CODE) {
		this.hOH_UKM_CODE = hOH_UKM_CODE;
	}

	public String getHOH_DEP_CODE() {
		return hOH_DEP_CODE;
	}

	public void setHOH_DEP_CODE(String hOH_DEP_CODE) {
		this.hOH_DEP_CODE = hOH_DEP_CODE;
	}

	public int getHOH_SIH_KBN() {
		return hOH_SIH_KBN;
	}

	public void setHOH_SIH_KBN(int hOH_SIH_KBN) {
		this.hOH_SIH_KBN = hOH_SIH_KBN;
	}

	public String getHOH_NAI_CODE() {
		return hOH_NAI_CODE;
	}

	public void setHOH_NAI_CODE(String hOH_NAI_CODE) {
		this.hOH_NAI_CODE = hOH_NAI_CODE;
	}

	public Date getSTR_DATE() {
		return sTR_DATE;
	}

	public void setSTR_DATE(Date sTR_DATE) {
		this.sTR_DATE = sTR_DATE;
	}

	public Date getEND_DATE() {
		return eND_DATE;
	}

	public void setEND_DATE(Date eND_DATE) {
		this.eND_DATE = eND_DATE;
	}

	public Date getHOH_INP_DATE() {
		return hOH_INP_DATE;
	}

	public void setHOH_INP_DATE(Date hOH_INP_DATE) {
		this.hOH_INP_DATE = hOH_INP_DATE;
	}

	public String getPRG_ID() {
		return pRG_ID;
	}

	public void setPRG_ID(String pRG_ID) {
		this.pRG_ID = pRG_ID;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/hOH_HOH_CODE=").append(hOH_HOH_CODE);
		buff.append("/hOH_HOH_NAME=").append(hOH_HOH_NAME);
		buff.append("/hOH_HOH_NAME_K=").append(hOH_HOH_NAME_K);
		buff.append("/hOH_KMK_CODE=").append(hOH_KMK_CODE);
		buff.append("/hOH_HKM_CODE=").append(hOH_HKM_CODE);
		buff.append("/hOH_UKM_CODE=").append(hOH_UKM_CODE);
		buff.append("/hOH_DEP_CODE=").append(hOH_DEP_CODE);
		buff.append("/hOH_SIH_KBN=").append(hOH_SIH_KBN);
		buff.append("/hOH_NAI_CODE=").append(hOH_NAI_CODE);
		buff.append("/sTR_DATE=").append(sTR_DATE);
		buff.append("/eND_DATE=").append(eND_DATE);
		buff.append("/hOH_INP_DATE=").append(hOH_INP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(hOH_HOH_CODE);
		list.add(hOH_HOH_NAME);
		list.add(hOH_HOH_NAME_K);
		list.add(hOH_KMK_CODE);
		list.add(hOH_HKM_CODE);
		list.add(hOH_UKM_CODE);
		list.add(hOH_DEP_CODE);
		list.add(hOH_SIH_KBN);
		list.add(hOH_NAI_CODE);
		list.add(sTR_DATE);
		list.add(eND_DATE);
		list.add(hOH_INP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
