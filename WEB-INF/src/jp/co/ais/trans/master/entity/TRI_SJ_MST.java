package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
/**
 * 
 */
public class TRI_SJ_MST extends MasterBase {

	/**  */
	public static final String TABLE = "TRI_SJ_MST";

	private String kAI_CODE = "";

	private String tRI_CODE = "";

	private String tRI_NAME_S = "";

	private String tJK_CODE = "";

	private int fURI_TESU_KBN;

	private String sJC_DATE;

	private String sJR_MON;

	private String sJP_DATE;

	private String sIHA_KBN;

	private String sIHA_HOU_CODE;

	private String fURI_CBK_CODE;

	private String bNK_CODE;

	private String bNK_STN_CODE;

	private String yKN_KBN;

	private String yKN_NO;

	private String yKN_NAME;

	private String yKN_KANA;

	private String gS_MKT_CODE;

	private String gS_STN_NAME;

	private String gS_BNK_NAME;

	private int gS_TESU_KBN;

	private String sIHA_BNK_NAME;

	private String sIHA_STN_NAME;

	private String sIHA_BNK_ADR;

	private String kEIYU_BNK_NAME;

	private String kEIYU_STN_NAME;

	private String kEIYU_BNK_ADR;

	private String uKE_ADR;

	private Date sTR_DATE;

	private Date eND_DATE;

	private String pRG_ID;

	// ‘—‹à–Ú“I–¼’Ç‰Á
	private String mKT_NAME;

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
	public String getTRI_CODE() {
		return tRI_CODE;
	}

	/**
	 * @param tRI_CODE
	 */
	public void setTRI_CODE(String tRI_CODE) {
		this.tRI_CODE = tRI_CODE;
	}

	/**
	 * @return String
	 */
	public String getTRI_NAME_S() {
		return tRI_NAME_S;
	}

	/**
	 * @param tRI_NAME_S
	 */
	public void setTRI_NAME_S(String tRI_NAME_S) {
		this.tRI_NAME_S = tRI_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getTJK_CODE() {
		return tJK_CODE;
	}

	/**
	 * @param tJK_CODE
	 */
	public void setTJK_CODE(String tJK_CODE) {
		this.tJK_CODE = tJK_CODE;
	}

	/**
	 * @return int
	 */
	public int getFURI_TESU_KBN() {
		return fURI_TESU_KBN;
	}

	/**
	 * @param fURI_TESU_KBN
	 */
	public void setFURI_TESU_KBN(int fURI_TESU_KBN) {
		this.fURI_TESU_KBN = fURI_TESU_KBN;
	}

	/**
	 * @return String
	 */
	public String getSJC_DATE() {
		return sJC_DATE;
	}

	/**
	 * @param sJC_DATE
	 */
	public void setSJC_DATE(String sJC_DATE) {
		this.sJC_DATE = sJC_DATE;
	}

	/**
	 * @return String
	 */
	public String getSJR_MON() {
		return sJR_MON;
	}

	/**
	 * @param sJR_MON
	 */
	public void setSJR_MON(String sJR_MON) {
		this.sJR_MON = sJR_MON;
	}

	/**
	 * @return String
	 */
	public String getSJP_DATE() {
		return sJP_DATE;
	}

	/**
	 * @param sJP_DATE
	 */
	public void setSJP_DATE(String sJP_DATE) {
		this.sJP_DATE = sJP_DATE;
	}

	/**
	 * @return String
	 */
	public String getSIHA_KBN() {
		return sIHA_KBN;
	}

	/**
	 * @param sIHA_KBN
	 */
	public void setSIHA_KBN(String sIHA_KBN) {
		this.sIHA_KBN = sIHA_KBN;
	}

	/**
	 * @return String
	 */
	public String getSIHA_HOU_CODE() {
		return sIHA_HOU_CODE;
	}

	/**
	 * @param sIHA_HOU_CODE
	 */
	public void setSIHA_HOU_CODE(String sIHA_HOU_CODE) {
		this.sIHA_HOU_CODE = sIHA_HOU_CODE;
	}

	/**
	 * @return String
	 */
	public String getFURI_CBK_CODE() {
		return fURI_CBK_CODE;
	}

	/**
	 * @param fURI_CBK_CODE
	 */
	public void setFURI_CBK_CODE(String fURI_CBK_CODE) {
		this.fURI_CBK_CODE = fURI_CBK_CODE;
	}

	/**
	 * @return String
	 */
	public String getBNK_CODE() {
		return bNK_CODE;
	}

	/**
	 * @param bNK_CODE
	 */
	public void setBNK_CODE(String bNK_CODE) {
		this.bNK_CODE = bNK_CODE;
	}

	/**
	 * @return String
	 */
	public String getBNK_STN_CODE() {
		return bNK_STN_CODE;
	}

	/**
	 * @param bNK_STN_CODE
	 */
	public void setBNK_STN_CODE(String bNK_STN_CODE) {
		this.bNK_STN_CODE = bNK_STN_CODE;
	}

	/**
	 * @return String
	 */
	public String getYKN_KBN() {
		return yKN_KBN;
	}

	/**
	 * @param yKN_KBN
	 */
	public void setYKN_KBN(String yKN_KBN) {
		this.yKN_KBN = yKN_KBN;
	}

	/**
	 * @return String
	 */
	public String getYKN_NO() {
		return yKN_NO;
	}

	/**
	 * @param yKN_NO
	 */
	public void setYKN_NO(String yKN_NO) {
		this.yKN_NO = yKN_NO;
	}

	/**
	 * @return String
	 */
	public String getYKN_NAME() {
		return yKN_NAME;
	}

	/**
	 * @param yKN_NAME
	 */
	public void setYKN_NAME(String yKN_NAME) {
		this.yKN_NAME = yKN_NAME;
	}

	/**
	 * @return String
	 */
	public String getYKN_KANA() {
		return yKN_KANA;
	}

	/**
	 * @param yKN_KANA
	 */
	public void setYKN_KANA(String yKN_KANA) {
		this.yKN_KANA = yKN_KANA;
	}

	/**
	 * @return String
	 */
	public String getGS_MKT_CODE() {
		return gS_MKT_CODE;
	}

	/**
	 * @param gS_MKT_CODE
	 */
	public void setGS_MKT_CODE(String gS_MKT_CODE) {
		this.gS_MKT_CODE = gS_MKT_CODE;
	}

	/**
	 * @return String
	 */
	public String getGS_STN_NAME() {
		return gS_STN_NAME;
	}

	/**
	 * @param gS_STN_NAME
	 */
	public void setGS_STN_NAME(String gS_STN_NAME) {
		this.gS_STN_NAME = gS_STN_NAME;
	}

	/**
	 * @return String
	 */
	public String getGS_BNK_NAME() {
		return gS_BNK_NAME;
	}

	/**
	 * @param gS_BNK_NAME
	 */
	public void setGS_BNK_NAME(String gS_BNK_NAME) {
		this.gS_BNK_NAME = gS_BNK_NAME;
	}

	/**
	 * @return String
	 */
	public int getGS_TESU_KBN() {
		return gS_TESU_KBN;
	}

	/**
	 * @param gS_TESU_KBN
	 */
	public void setGS_TESU_KBN(int gS_TESU_KBN) {
		this.gS_TESU_KBN = gS_TESU_KBN;
	}

	/**
	 * @return String
	 */
	public String getSIHA_BNK_NAME() {
		return sIHA_BNK_NAME;
	}

	/**
	 * @param sIHA_BNK_NAME
	 */
	public void setSIHA_BNK_NAME(String sIHA_BNK_NAME) {
		this.sIHA_BNK_NAME = sIHA_BNK_NAME;
	}

	/**
	 * @return String String
	 */
	public String getSIHA_STN_NAME() {
		return sIHA_STN_NAME;
	}

	/**
	 * @param sIHA_STN_NAME
	 */
	public void setSIHA_STN_NAME(String sIHA_STN_NAME) {
		this.sIHA_STN_NAME = sIHA_STN_NAME;
	}

	/**
	 * @return String
	 */
	public String getSIHA_BNK_ADR() {
		return sIHA_BNK_ADR;
	}

	/**
	 * @param sIHA_BNK_ADR
	 */
	public void setSIHA_BNK_ADR(String sIHA_BNK_ADR) {
		this.sIHA_BNK_ADR = sIHA_BNK_ADR;
	}

	/**
	 * @return String
	 */
	public String getKEIYU_BNK_NAME() {
		return kEIYU_BNK_NAME;
	}

	/**
	 * @param kEIYU_BNK_NAME
	 */
	public void setKEIYU_BNK_NAME(String kEIYU_BNK_NAME) {
		this.kEIYU_BNK_NAME = kEIYU_BNK_NAME;
	}

	/**
	 * @return String
	 */
	public String getKEIYU_STN_NAME() {
		return kEIYU_STN_NAME;
	}

	/**
	 * @param kEIYU_STN_NAME
	 */
	public void setKEIYU_STN_NAME(String kEIYU_STN_NAME) {
		this.kEIYU_STN_NAME = kEIYU_STN_NAME;
	}

	/**
	 * @return String
	 */
	public String getKEIYU_BNK_ADR() {
		return kEIYU_BNK_ADR;
	}

	/**
	 * @param kEIYU_BNK_ADR
	 */
	public void setKEIYU_BNK_ADR(String kEIYU_BNK_ADR) {
		this.kEIYU_BNK_ADR = kEIYU_BNK_ADR;
	}

	/**
	 * @return String
	 */
	public String getUKE_ADR() {
		return uKE_ADR;
	}

	/**
	 * @param uKE_ADR
	 */
	public void setUKE_ADR(String uKE_ADR) {
		this.uKE_ADR = uKE_ADR;
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
	public String getMKT_NAME() {
		return mKT_NAME;
	}

	/**
	 * @param mKT_NAME
	 */
	public void setMKT_NAME(String mKT_NAME) {
		this.mKT_NAME = mKT_NAME;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/tRI_CODE=").append(tRI_CODE);
		buff.append("/tRI_NAME_S=").append(tRI_NAME_S);
		buff.append("/tJK_CODE=").append(tJK_CODE);
		buff.append("/fURI_TESU_KBN=").append(fURI_TESU_KBN);
		buff.append("/sJC_DATE=").append(sJC_DATE);
		buff.append("/sJR_MON=").append(sJR_MON);
		buff.append("/sJP_DATE=").append(sJP_DATE);
		buff.append("/sIHA_KBN=").append(sIHA_KBN);
		buff.append("/sIHA_HOU_CODE=").append(sIHA_HOU_CODE);
		buff.append("/fURI_CBK_CODE=").append(fURI_CBK_CODE);
		buff.append("/bNK_CODE=").append(bNK_CODE);
		buff.append("/bNK_STN_CODE=").append(bNK_STN_CODE);
		buff.append("/yKN_KBN=").append(yKN_KBN);
		buff.append("/yKN_NO=").append(yKN_NO);
		buff.append("/yKN_KANA=").append(yKN_KANA);
		buff.append("/gS_MKT_CODE=").append(gS_MKT_CODE);
		buff.append("/gS_BNK_NAME=").append(gS_BNK_NAME);
		buff.append("/gS_STN_NAME=").append(gS_STN_NAME);
		buff.append("/yKN_NAME=").append(yKN_NAME);
		buff.append("/gS_TESU_KBN=").append(gS_TESU_KBN);
		buff.append("/sIHA_BNK_NAME=").append(sIHA_BNK_NAME);
		buff.append("/sIHA_STN_NAME=").append(sIHA_STN_NAME);
		buff.append("/sIHA_BNK_ADR=").append(sIHA_BNK_ADR);
		buff.append("/kEIYU_BNK_NAME=").append(kEIYU_BNK_NAME);
		buff.append("/kEIYU_STN_NAME=").append(kEIYU_STN_NAME);
		buff.append("/kEIYU_BNK_ADR=").append(kEIYU_BNK_ADR);
		buff.append("/uKE_ADR=").append(uKE_ADR);
		buff.append("/sTR_DATE=").append(sTR_DATE);
		buff.append("/eND_DATE=").append(eND_DATE);
		buff.append("/iNP_DATE=").append(iNP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("/mKT_NAME=").append(mKT_NAME);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(tRI_CODE);
		list.add(tRI_NAME_S);
		list.add(tJK_CODE);
		list.add(fURI_TESU_KBN);
		list.add(sJC_DATE);
		list.add(sJR_MON);
		list.add(sJP_DATE);
		list.add(sIHA_KBN);
		list.add(sIHA_HOU_CODE);
		list.add(fURI_CBK_CODE);
		list.add(bNK_CODE);
		list.add(bNK_STN_CODE);
		list.add(yKN_KBN);
		list.add(yKN_NO);
		list.add(yKN_KANA);
		list.add(gS_MKT_CODE);
		list.add(gS_BNK_NAME);
		list.add(gS_STN_NAME);
		list.add(yKN_NAME);
		list.add(gS_TESU_KBN);
		list.add(sIHA_BNK_NAME);
		list.add(sIHA_STN_NAME);
		list.add(sIHA_BNK_ADR);
		list.add(kEIYU_BNK_NAME);
		list.add(kEIYU_STN_NAME);
		list.add(kEIYU_BNK_ADR);
		list.add(uKE_ADR);
		list.add(sTR_DATE);
		list.add(eND_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);
		list.add(mKT_NAME);
		return list;
	}
}
