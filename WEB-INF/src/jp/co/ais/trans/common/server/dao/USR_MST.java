package jp.co.ais.trans.common.server.dao;

import java.util.*;

import jp.co.ais.trans.common.server.*;

/**
 * ���[�U�}�X�^(���o�[�W����).<br>
 * KT�̕��𗘗p���邱�ƁB
 */
public class USR_MST implements TInterfaceHasToObjectArray {

	/** �e�[�u���� */
	public static final String TABLE = "USR_MST";

	private String kAI_CODE = "";

	private String uSR_CODE = "";

	private String pASSWORD = "";

	private String lNG_CODE = "";

	private String uSR_NAME;

	private String uSR_NAME_S;

	private String uSR_NAME_K;

	private String sYS_KBN;

	private int pRC_KEN;

	private int uPD_KEN;

	private String eMP_CODE;

	private String dEP_CODE;

	private int kEIRI_KBN;

	private Date sTR_DATE;

	private Date eND_DATE;

	private Date iNP_DATE;

	private Date uPD_DATE;

	private String pRG_ID;

	private String uSR_ID;

	/**
	 * @return obj
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
	 * @return obj
	 */
	public String getUSR_CODE() {
		return uSR_CODE;
	}

	/**
	 * @param uSR_CODE
	 */
	public void setUSR_CODE(String uSR_CODE) {
		this.uSR_CODE = uSR_CODE;
	}

	/**
	 * @return obj
	 */
	public String getPASSWORD() {
		return pASSWORD;
	}

	/**
	 * @param pASSWORD
	 */
	public void setPASSWORD(String pASSWORD) {
		this.pASSWORD = pASSWORD;
	}

	/**
	 * @return obj
	 */
	public String getLNG_CODE() {
		return lNG_CODE;
	}

	/**
	 * @param lNG_CODE
	 */
	public void setLNG_CODE(String lNG_CODE) {
		this.lNG_CODE = lNG_CODE;
	}

	/**
	 * @return obj
	 */
	public String getUSR_NAME() {
		return uSR_NAME;
	}

	/**
	 * @param uSR_NAME
	 */
	public void setUSR_NAME(String uSR_NAME) {
		this.uSR_NAME = uSR_NAME;
	}

	/**
	 * @return obj
	 */
	public String getUSR_NAME_S() {
		return uSR_NAME_S;
	}

	/**
	 * @param uSR_NAME_S
	 */
	public void setUSR_NAME_S(String uSR_NAME_S) {
		this.uSR_NAME_S = uSR_NAME_S;
	}

	/**
	 * @return obj
	 */
	public String getUSR_NAME_K() {
		return uSR_NAME_K;
	}

	/**
	 * @param uSR_NAME_K
	 */
	public void setUSR_NAME_K(String uSR_NAME_K) {
		this.uSR_NAME_K = uSR_NAME_K;
	}

	/**
	 * @return obj
	 */
	public String getSYS_KBN() {
		return sYS_KBN;
	}

	/**
	 * @param sYS_KBN
	 */
	public void setSYS_KBN(String sYS_KBN) {
		this.sYS_KBN = sYS_KBN;
	}

	/**
	 * @return obj
	 */
	public int getPRC_KEN() {
		return pRC_KEN;
	}

	/**
	 * @param pRC_KEN
	 */
	public void setPRC_KEN(int pRC_KEN) {
		this.pRC_KEN = pRC_KEN;
	}

	/**
	 * @return obj
	 */
	public int getUPD_KEN() {
		return uPD_KEN;
	}

	/**
	 * @param uPD_KEN
	 */
	public void setUPD_KEN(int uPD_KEN) {
		this.uPD_KEN = uPD_KEN;
	}

	/**
	 * @return obj
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
	 * @return obj
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
	 * @return obj
	 */
	public int getKEIRI_KBN() {
		return kEIRI_KBN;
	}

	/**
	 * @param kEIRI_KBN
	 */
	public void setKEIRI_KBN(int kEIRI_KBN) {
		this.kEIRI_KBN = kEIRI_KBN;
	}

	/**
	 * @return obj
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
	 * @return obj
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
	 * @return obj
	 */
	public Date getINP_DATE() {
		return iNP_DATE;
	}

	/**
	 * @param iNP_DATE
	 */
	public void setINP_DATE(Date iNP_DATE) {
		this.iNP_DATE = iNP_DATE;
	}

	/**
	 * @return obj
	 */
	public Date getUPD_DATE() {
		return uPD_DATE;
	}

	/**
	 * @param uPD_DATE
	 */
	public void setUPD_DATE(Date uPD_DATE) {
		this.uPD_DATE = uPD_DATE;
	}

	/**
	 * @return obj
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
	 * @return obj
	 */
	public String getUSR_ID() {
		return uSR_ID;
	}

	/**
	 * @param uSR_ID
	 */
	public void setUSR_ID(String uSR_ID) {
		this.uSR_ID = uSR_ID;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/uSR_CODE=").append(uSR_CODE);
		buff.append("/pASSWORD=").append(pASSWORD);
		buff.append("/lNG_CODE=").append(lNG_CODE);
		buff.append("/uSR_NAME=").append(uSR_NAME);
		buff.append("/uSR_NAME_S=").append(uSR_NAME_S);
		buff.append("/uSR_NAME_K=").append(uSR_NAME_K);
		buff.append("/sYS_KBN=").append(sYS_KBN);
		buff.append("/pRC_KEN=").append(pRC_KEN);
		buff.append("/uPD_KEN=").append(uPD_KEN);
		buff.append("/eMP_CODE=").append(eMP_CODE);
		buff.append("/dEP_CODE=").append(dEP_CODE);
		buff.append("/kEIRI_KBN=").append(kEIRI_KBN);
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
		list.add(uSR_CODE);
		list.add(pASSWORD);
		list.add(lNG_CODE);
		list.add(uSR_NAME);
		list.add(uSR_NAME_S);
		list.add(uSR_NAME_K);
		list.add(sYS_KBN);
		list.add(pRC_KEN);
		list.add(uPD_KEN);
		list.add(eMP_CODE);
		list.add(dEP_CODE);
		list.add(kEIRI_KBN);
		list.add(sTR_DATE);
		list.add(eND_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
