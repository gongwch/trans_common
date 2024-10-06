package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class EMP_MST extends MasterBase {

	/**  */
	public static final String TABLE = "EMP_MST";

	private String kAI_CODE = "";

	private String eMP_CODE = "";

	private String eMP_NAME = "";

	private String eMP_NAME_S;

	private String eMP_NAME_K;

	private String eMP_FURI_BNK_CODE;

	private String eMP_FURI_STN_CODE;

	private String eMP_YKN_NO;

	private int eMP_KOZA_KBN;

	private String eMP_YKN_KANA;

	private String eMP_CBK_CODE;

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
	public String getEMP_CODE() {
		return eMP_CODE;
	}

	/**
	 * @param eMP_CODE
	 */
	public void setEMP_CODE(String eMP_CODE) {
		this.eMP_CODE = eMP_CODE;
	}

	/**
	 * @return String
	 */
	public String getEMP_NAME() {
		return eMP_NAME;
	}

	/**
	 * @param eMP_NAME
	 */
	public void setEMP_NAME(String eMP_NAME) {
		this.eMP_NAME = eMP_NAME;
	}

	/**
	 * @return String
	 */
	public String getEMP_NAME_S() {
		return eMP_NAME_S;
	}

	/**
	 * @param eMP_NAME_S
	 */
	public void setEMP_NAME_S(String eMP_NAME_S) {
		this.eMP_NAME_S = eMP_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getEMP_NAME_K() {
		return eMP_NAME_K;
	}

	/**
	 * @param eMP_NAME_K
	 */
	public void setEMP_NAME_K(String eMP_NAME_K) {
		this.eMP_NAME_K = eMP_NAME_K;
	}

	/**
	 * @return String
	 */
	public String getEMP_FURI_BNK_CODE() {
		return eMP_FURI_BNK_CODE;
	}

	/**
	 * @param eMP_FURI_BNK_CODE
	 */
	public void setEMP_FURI_BNK_CODE(String eMP_FURI_BNK_CODE) {
		this.eMP_FURI_BNK_CODE = eMP_FURI_BNK_CODE;
	}

	/**
	 * @return String
	 */
	public String getEMP_FURI_STN_CODE() {
		return eMP_FURI_STN_CODE;
	}

	/**
	 * @param eMP_FURI_STN_CODE
	 */
	public void setEMP_FURI_STN_CODE(String eMP_FURI_STN_CODE) {
		this.eMP_FURI_STN_CODE = eMP_FURI_STN_CODE;
	}

	/**
	 * @return String
	 */
	public String getEMP_YKN_NO() {
		return eMP_YKN_NO;
	}

	/**
	 * @param eMP_YKN_NO
	 */
	public void setEMP_YKN_NO(String eMP_YKN_NO) {
		this.eMP_YKN_NO = eMP_YKN_NO;
	}

	/**
	 * @return String
	 */
	public int getEMP_KOZA_KBN() {
		return eMP_KOZA_KBN;
	}

	/**
	 * @param eMP_KOZA_KBN
	 */
	public void setEMP_KOZA_KBN(int eMP_KOZA_KBN) {
		this.eMP_KOZA_KBN = eMP_KOZA_KBN;
	}

	/**
	 * @return String
	 */
	public String getEMP_YKN_KANA() {
		return eMP_YKN_KANA;
	}

	/**
	 * @param eMP_YKN_KANA
	 */
	public void setEMP_YKN_KANA(String eMP_YKN_KANA) {
		this.eMP_YKN_KANA = eMP_YKN_KANA;
	}

	/**
	 * @return String
	 */
	public String getEMP_CBK_CODE() {
		return eMP_CBK_CODE;
	}

	/**
	 * @param eMP_CBK_CODE
	 */
	public void setEMP_CBK_CODE(String eMP_CBK_CODE) {
		this.eMP_CBK_CODE = eMP_CBK_CODE;
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
		buff.append("/eMP_CODE=").append(eMP_CODE);
		buff.append("/eMP_NAME=").append(eMP_NAME);
		buff.append("/eMP_NAME_S=").append(eMP_NAME_S);
		buff.append("/eMP_NAME_K=").append(eMP_NAME_K);
		buff.append("/eMP_FURI_BNK_CODE=").append(eMP_FURI_BNK_CODE);
		buff.append("/eMP_FURI_STN_CODE=").append(eMP_FURI_STN_CODE);
		buff.append("/eMP_YKN_NO=").append(eMP_YKN_NO);
		buff.append("/eMP_KOZA_KBN=").append(eMP_KOZA_KBN);
		buff.append("/eMP_YKN_KANA=").append(eMP_YKN_KANA);
		buff.append("/eMP_CBK_CODE=").append(eMP_CBK_CODE);
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
		list.add(eMP_CODE);
		list.add(eMP_NAME);
		list.add(eMP_NAME_S);
		list.add(eMP_NAME_K);
		list.add(eMP_FURI_BNK_CODE);
		list.add(eMP_FURI_STN_CODE);
		list.add(eMP_YKN_NO);
		list.add(eMP_KOZA_KBN);
		list.add(eMP_YKN_KANA);
		list.add(eMP_CBK_CODE);
		list.add(sTR_DATE);
		list.add(eND_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
