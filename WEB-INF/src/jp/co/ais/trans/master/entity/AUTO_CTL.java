package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class AUTO_CTL extends MasterBase {

	/**  */
	public static final String TABLE = "AUTO_CTL";

	private String kAI_CODE = "";

	private String pRIFIX = "";

	private int lAST_NO;

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
	public String getPRIFIX() {
		return pRIFIX;
	}

	/**
	 * @param pRIFIX
	 */
	public void setPRIFIX(String pRIFIX) {
		this.pRIFIX = pRIFIX;
	}

	/**
	 * @return int
	 */
	public int getLAST_NO() {
		return lAST_NO;
	}

	/**
	 * @param lAST_NO
	 */
	public void setLAST_NO(int lAST_NO) {
		this.lAST_NO = lAST_NO;
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
		buff.append("/pRIFIX=").append(pRIFIX);
		buff.append("/lAST_NO=").append(lAST_NO);
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
		list.add(pRIFIX);
		list.add(lAST_NO);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
