package jp.co.ais.trans.master.entity;

import java.util.*;

public class AP_CAL_MST extends MasterBase {
	public static final String TABLE = "AP_CAL_MST";

	private String kAI_CODE = "";

	private Date cAL_DATE;

	private int cAL_HARAI;

	private int cAL_BNK_KBN;

	private int cAL_SHA;

	private Date cAL_INP_DATE;

	private String pRG_ID;


	public String getKAI_CODE() {
		return kAI_CODE;
	}

	public void setKAI_CODE(String kAI_CODE) {
		this.kAI_CODE = kAI_CODE;
	}

	public Date getCAL_DATE() {
		return cAL_DATE;
	}

	public void setCAL_DATE(Date cAL_DATE) {
		this.cAL_DATE = cAL_DATE;
	}

	public int getCAL_HARAI() {
		return cAL_HARAI;
	}

	public void setCAL_HARAI(int cAL_HARAI) {
		this.cAL_HARAI = cAL_HARAI;
	}

	public int getCAL_BNK_KBN() {
		return cAL_BNK_KBN;
	}

	public void setCAL_BNK_KBN(int cAL_BNK_KBN) {
		this.cAL_BNK_KBN = cAL_BNK_KBN;
	}

	public int getCAL_SHA() {
		return cAL_SHA;
	}

	public void setCAL_SHA(int cAL_SHA) {
		this.cAL_SHA = cAL_SHA;
	}

	public Date getCAL_INP_DATE() {
		return cAL_INP_DATE;
	}

	public void setCAL_INP_DATE(Date cAL_INP_DATE) {
		this.cAL_INP_DATE = cAL_INP_DATE;
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
		buff.append("/cAL_DATE=").append(cAL_DATE);
		buff.append("/cAL_HARAI=").append(cAL_HARAI);
		buff.append("/cAL_BNK_KBN=").append(cAL_BNK_KBN);
		buff.append("/cAL_SHA=").append(cAL_SHA);
		buff.append("/cAL_INP_DATE=").append(cAL_INP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("]");
		return buff.toString();
	}

	/**
	 * servlet response—p
	 */
	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(cAL_DATE);
		list.add(cAL_HARAI);
		list.add(cAL_BNK_KBN);
		list.add(cAL_SHA);
		list.add(cAL_INP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
