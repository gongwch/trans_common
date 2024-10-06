package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class GL_CTL_MST extends MasterBase {

	/**  */
	public static final String TABLE = "GL_CTL_MST";

	/**  */
	public static final String kAI_CODE_ID = "sequence, sequenceName=kAI_CODE";

	private String kAI_CODE = "";

	private int kSD_KBN;

	private int kSN_NYU_KBN;

	private int mT_ZAN_HYJ_KBN;

	private int eXC_RATE_KBN;

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
	public int getKSD_KBN() {
		return kSD_KBN;
	}

	/**
	 * @param kSD_KBN
	 */
	public void setKSD_KBN(int kSD_KBN) {
		this.kSD_KBN = kSD_KBN;
	}

	/**
	 * @return String
	 */
	public int getKSN_NYU_KBN() {
		return kSN_NYU_KBN;
	}

	/**
	 * @param kSN_NYU_KBN
	 */
	public void setKSN_NYU_KBN(int kSN_NYU_KBN) {
		this.kSN_NYU_KBN = kSN_NYU_KBN;
	}

	/**
	 * @return String
	 */
	public int getMT_ZAN_HYJ_KBN() {
		return mT_ZAN_HYJ_KBN;
	}

	/**
	 * @param mT_ZAN_HYJ_KBN
	 */
	public void setMT_ZAN_HYJ_KBN(int mT_ZAN_HYJ_KBN) {
		this.mT_ZAN_HYJ_KBN = mT_ZAN_HYJ_KBN;
	}

	/**
	 * @return String
	 */
	public int getEXC_RATE_KBN() {
		return eXC_RATE_KBN;
	}

	/**
	 * @param eXC_RATE_KBN
	 */
	public void setEXC_RATE_KBN(int eXC_RATE_KBN) {
		this.eXC_RATE_KBN = eXC_RATE_KBN;
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
		buff.append("/kSD_KBN=").append(kSD_KBN);
		buff.append("/kSN_NYU_KBN=").append(kSN_NYU_KBN);
		buff.append("/mT_ZAN_HYJ_KBN=").append(mT_ZAN_HYJ_KBN);
		buff.append("/eXC_RATE_KBN=").append(eXC_RATE_KBN);
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
		list.add(kSD_KBN);
		list.add(kSN_NYU_KBN);
		list.add(mT_ZAN_HYJ_KBN);
		list.add(eXC_RATE_KBN);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
