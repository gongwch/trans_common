package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class HKM_MST extends MasterBase {

	/**  */
	public static final String TABLE = "HKM_MST";

	private String kAI_CODE = "";

	private String kMK_CODE = "";

	private String hKM_CODE = "";

	private String hKM_NAME;

	private String hKM_NAME_S;

	private String hKM_NAME_K;

	private int uKM_KBN;

	private String zEI_CODE;

	private int gL_FLG_1;

	private int gL_FLG_2;

	private int gL_FLG_3;

	private int aP_FLG_1;

	private int aP_FLG_2;

	private int aR_FLG_1;

	private int aR_FLG_2;

	private int fA_FLG_1;

	private int fA_FLG_2;

	private int tRI_CODE_FLG;

	private int hAS_FLG;

	private int eMP_CODE_FLG;

	private int kNR_FLG_1;

	private int kNR_FLG_2;

	private int kNR_FLG_3;

	private int kNR_FLG_4;

	private int kNR_FLG_5;

	private int kNR_FLG_6;

	private int hM_FLG_1;

	private int hM_FLG_2;

	private int hM_FLG_3;

	private int uRI_ZEI_FLG;

	private int sIR_ZEI_FLG;

	private int mCR_FLG;

	private int eXC_FLG;

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
	public String getKMK_CODE() {
		return kMK_CODE;
	}

	/**
	 * @param kMK_CODE
	 */
	public void setKMK_CODE(String kMK_CODE) {
		this.kMK_CODE = kMK_CODE;
	}

	/**
	 * @return String
	 */
	public String getHKM_CODE() {
		return hKM_CODE;
	}

	/**
	 * @param hKM_CODE
	 */
	public void setHKM_CODE(String hKM_CODE) {
		this.hKM_CODE = hKM_CODE;
	}

	/**
	 * @return String
	 */
	public String getHKM_NAME() {
		return hKM_NAME;
	}

	/**
	 * @param hKM_NAME
	 */
	public void setHKM_NAME(String hKM_NAME) {
		this.hKM_NAME = hKM_NAME;
	}

	/**
	 * @return String
	 */
	public String getHKM_NAME_S() {
		return hKM_NAME_S;
	}

	/**
	 * @param hKM_NAME_S
	 */
	public void setHKM_NAME_S(String hKM_NAME_S) {
		this.hKM_NAME_S = hKM_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getHKM_NAME_K() {
		return hKM_NAME_K;
	}

	/**
	 * @param hKM_NAME_K
	 */
	public void setHKM_NAME_K(String hKM_NAME_K) {
		this.hKM_NAME_K = hKM_NAME_K;
	}

	/**
	 * @return int
	 */
	public int getUKM_KBN() {
		return uKM_KBN;
	}

	/**
	 * @param uKM_KBN
	 */
	public void setUKM_KBN(int uKM_KBN) {
		this.uKM_KBN = uKM_KBN;
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
	 * @return int
	 */
	public int getGL_FLG_1() {
		return gL_FLG_1;
	}

	/**
	 * @param gL_FLG_1
	 */
	public void setGL_FLG_1(int gL_FLG_1) {
		this.gL_FLG_1 = gL_FLG_1;
	}

	/**
	 * @return int
	 */
	public int getGL_FLG_2() {
		return gL_FLG_2;
	}

	/**
	 * @param gL_FLG_2
	 */
	public void setGL_FLG_2(int gL_FLG_2) {
		this.gL_FLG_2 = gL_FLG_2;
	}

	/**
	 * @return int
	 */
	public int getGL_FLG_3() {
		return gL_FLG_3;
	}

	/**
	 * @param gL_FLG_3
	 */
	public void setGL_FLG_3(int gL_FLG_3) {
		this.gL_FLG_3 = gL_FLG_3;
	}

	/**
	 * @return int
	 */
	public int getAP_FLG_1() {
		return aP_FLG_1;
	}

	/**
	 * @param aP_FLG_1
	 */
	public void setAP_FLG_1(int aP_FLG_1) {
		this.aP_FLG_1 = aP_FLG_1;
	}

	/**
	 * @return int
	 */
	public int getAP_FLG_2() {
		return aP_FLG_2;
	}

	/**
	 * @param aP_FLG_2
	 */
	public void setAP_FLG_2(int aP_FLG_2) {
		this.aP_FLG_2 = aP_FLG_2;
	}

	/**
	 * @return int
	 */
	public int getAR_FLG_1() {
		return aR_FLG_1;
	}

	/**
	 * @param aR_FLG_1
	 */
	public void setAR_FLG_1(int aR_FLG_1) {
		this.aR_FLG_1 = aR_FLG_1;
	}

	/**
	 * @return int
	 */
	public int getAR_FLG_2() {
		return aR_FLG_2;
	}

	/**
	 * @param aR_FLG_2
	 */
	public void setAR_FLG_2(int aR_FLG_2) {
		this.aR_FLG_2 = aR_FLG_2;
	}

	/**
	 * @return int
	 */
	public int getFA_FLG_1() {
		return fA_FLG_1;
	}

	/**
	 * @param fA_FLG_1
	 */
	public void setFA_FLG_1(int fA_FLG_1) {
		this.fA_FLG_1 = fA_FLG_1;
	}

	/**
	 * @return int
	 */
	public int getFA_FLG_2() {
		return fA_FLG_2;
	}

	/**
	 * @param fA_FLG_2
	 */
	public void setFA_FLG_2(int fA_FLG_2) {
		this.fA_FLG_2 = fA_FLG_2;
	}

	/**
	 * @return int
	 */
	public int getTRI_CODE_FLG() {
		return tRI_CODE_FLG;
	}

	/**
	 * @param tRI_CODE_FLG
	 */
	public void setTRI_CODE_FLG(int tRI_CODE_FLG) {
		this.tRI_CODE_FLG = tRI_CODE_FLG;
	}

	/**
	 * @return int
	 */
	public int getHAS_FLG() {
		return hAS_FLG;
	}

	/**
	 * @param hAS_FLG
	 */
	public void setHAS_FLG(int hAS_FLG) {
		this.hAS_FLG = hAS_FLG;
	}

	/**
	 * @return int
	 */
	public int getEMP_CODE_FLG() {
		return eMP_CODE_FLG;
	}

	/**
	 * @param eMP_CODE_FLG
	 */
	public void setEMP_CODE_FLG(int eMP_CODE_FLG) {
		this.eMP_CODE_FLG = eMP_CODE_FLG;
	}

	/**
	 * @return int
	 */
	public int getKNR_FLG_1() {
		return kNR_FLG_1;
	}

	/**
	 * @param kNR_FLG_1
	 */
	public void setKNR_FLG_1(int kNR_FLG_1) {
		this.kNR_FLG_1 = kNR_FLG_1;
	}

	/**
	 * @return int
	 */
	public int getKNR_FLG_2() {
		return kNR_FLG_2;
	}

	/**
	 * @param kNR_FLG_2
	 */
	public void setKNR_FLG_2(int kNR_FLG_2) {
		this.kNR_FLG_2 = kNR_FLG_2;
	}

	/**
	 * @return int
	 */
	public int getKNR_FLG_3() {
		return kNR_FLG_3;
	}

	/**
	 * @param kNR_FLG_3
	 */
	public void setKNR_FLG_3(int kNR_FLG_3) {
		this.kNR_FLG_3 = kNR_FLG_3;
	}

	/**
	 * @return int
	 */
	public int getKNR_FLG_4() {
		return kNR_FLG_4;
	}

	/**
	 * @param kNR_FLG_4
	 */
	public void setKNR_FLG_4(int kNR_FLG_4) {
		this.kNR_FLG_4 = kNR_FLG_4;
	}

	/**
	 * @return int
	 */
	public int getKNR_FLG_5() {
		return kNR_FLG_5;
	}

	/**
	 * @param kNR_FLG_5
	 */
	public void setKNR_FLG_5(int kNR_FLG_5) {
		this.kNR_FLG_5 = kNR_FLG_5;
	}

	/**
	 * @return int
	 */
	public int getKNR_FLG_6() {
		return kNR_FLG_6;
	}

	/**
	 * @param kNR_FLG_6
	 */
	public void setKNR_FLG_6(int kNR_FLG_6) {
		this.kNR_FLG_6 = kNR_FLG_6;
	}

	/**
	 * @return int
	 */
	public int getHM_FLG_1() {
		return hM_FLG_1;
	}

	/**
	 * @param hM_FLG_1
	 */
	public void setHM_FLG_1(int hM_FLG_1) {
		this.hM_FLG_1 = hM_FLG_1;
	}

	/**
	 * @return int
	 */
	public int getHM_FLG_2() {
		return hM_FLG_2;
	}

	/**
	 * @param hM_FLG_2
	 */
	public void setHM_FLG_2(int hM_FLG_2) {
		this.hM_FLG_2 = hM_FLG_2;
	}

	/**
	 * @return int
	 */
	public int getHM_FLG_3() {
		return hM_FLG_3;
	}

	/**
	 * @param hM_FLG_3
	 */
	public void setHM_FLG_3(int hM_FLG_3) {
		this.hM_FLG_3 = hM_FLG_3;
	}

	/**
	 * @return int
	 */
	public int getURI_ZEI_FLG() {
		return uRI_ZEI_FLG;
	}

	/**
	 * @param uRI_ZEI_FLG
	 */
	public void setURI_ZEI_FLG(int uRI_ZEI_FLG) {
		this.uRI_ZEI_FLG = uRI_ZEI_FLG;
	}

	/**
	 * @return int
	 */
	public int getSIR_ZEI_FLG() {
		return sIR_ZEI_FLG;
	}

	/**
	 * @param sIR_ZEI_FLG
	 */
	public void setSIR_ZEI_FLG(int sIR_ZEI_FLG) {
		this.sIR_ZEI_FLG = sIR_ZEI_FLG;
	}

	/**
	 * @return int
	 */
	public int getMCR_FLG() {
		return mCR_FLG;
	}

	/**
	 * @param mCR_FLG
	 */
	public void setMCR_FLG(int mCR_FLG) {
		this.mCR_FLG = mCR_FLG;
	}

	/**
	 * @return int
	 */
	public int getEXC_FLG() {
		return eXC_FLG;
	}

	/**
	 * @param eXC_FLG
	 */
	public void setEXC_FLG(int eXC_FLG) {
		this.eXC_FLG = eXC_FLG;
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
		buff.append("/kMK_CODE=").append(kMK_CODE);
		buff.append("/hKM_CODE=").append(hKM_CODE);
		buff.append("/hKM_NAME=").append(hKM_NAME);
		buff.append("/hKM_NAME_S=").append(hKM_NAME_S);
		buff.append("/hKM_NAME_K=").append(hKM_NAME_K);
		buff.append("/uKM_KBN=").append(uKM_KBN);
		buff.append("/zEI_CODE=").append(zEI_CODE);
		buff.append("/gL_FLG_1=").append(gL_FLG_1);
		buff.append("/gL_FLG_2=").append(gL_FLG_2);
		buff.append("/gL_FLG_3=").append(gL_FLG_3);
		buff.append("/aP_FLG_1=").append(aP_FLG_1);
		buff.append("/aP_FLG_2=").append(aP_FLG_2);
		buff.append("/aR_FLG_1=").append(aR_FLG_1);
		buff.append("/aR_FLG_2=").append(aR_FLG_2);
		buff.append("/fA_FLG_1=").append(fA_FLG_1);
		buff.append("/fA_FLG_2=").append(fA_FLG_2);
		buff.append("/tRI_CODE_FLG=").append(tRI_CODE_FLG);
		buff.append("/hAS_FLG=").append(hAS_FLG);
		buff.append("/eMP_CODE_FLG=").append(eMP_CODE_FLG);
		buff.append("/kNR_FLG_1=").append(kNR_FLG_1);
		buff.append("/kNR_FLG_2=").append(kNR_FLG_2);
		buff.append("/kNR_FLG_3=").append(kNR_FLG_3);
		buff.append("/kNR_FLG_4=").append(kNR_FLG_4);
		buff.append("/kNR_FLG_5=").append(kNR_FLG_5);
		buff.append("/kNR_FLG_6=").append(kNR_FLG_6);
		buff.append("/hM_FLG_1=").append(hM_FLG_1);
		buff.append("/hM_FLG_2=").append(hM_FLG_2);
		buff.append("/hM_FLG_3=").append(hM_FLG_3);
		buff.append("/uRI_ZEI_FLG=").append(uRI_ZEI_FLG);
		buff.append("/sIR_ZEI_FLG=").append(sIR_ZEI_FLG);
		buff.append("/mCR_FLG=").append(mCR_FLG);
		buff.append("/eXC_FLG=").append(eXC_FLG);
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
		list.add(kMK_CODE);
		list.add(hKM_CODE);
		list.add(hKM_NAME);
		list.add(hKM_NAME_S);
		list.add(hKM_NAME_K);
		list.add(uKM_KBN);
		list.add(zEI_CODE);
		list.add(gL_FLG_1);
		list.add(gL_FLG_2);
		list.add(gL_FLG_3);
		list.add(aP_FLG_1);
		list.add(aP_FLG_2);
		list.add(aR_FLG_1);
		list.add(aR_FLG_2);
		list.add(fA_FLG_1);
		list.add(fA_FLG_2);
		list.add(tRI_CODE_FLG);
		list.add(hAS_FLG);
		list.add(eMP_CODE_FLG);
		list.add(kNR_FLG_1);
		list.add(kNR_FLG_2);
		list.add(kNR_FLG_3);
		list.add(kNR_FLG_4);
		list.add(kNR_FLG_5);
		list.add(kNR_FLG_6);
		list.add(hM_FLG_1);
		list.add(hM_FLG_2);
		list.add(hM_FLG_3);
		list.add(uRI_ZEI_FLG);
		list.add(sIR_ZEI_FLG);
		list.add(mCR_FLG);
		list.add(eXC_FLG);
		list.add(sTR_DATE);
		list.add(eND_DATE);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}

	/**
	 * マップ変換
	 * 
	 * @return マップ
	 */
	public Map toObjectMap() {

		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("KAI_CODE", kAI_CODE);
		map.put("KMK_CODE", kMK_CODE);
		map.put("HKM_CODE", hKM_CODE);
		map.put("HKM_NAME", hKM_NAME);
		map.put("HKM_NAME_S", hKM_NAME_S);
		map.put("HKM_NAME_K", hKM_NAME_K);
		map.put("UKM_KBN", uKM_KBN);
		map.put("ZEI_CODE", zEI_CODE);
		map.put("GL_FLG_1", gL_FLG_1);
		map.put("GL_FLG_2", gL_FLG_2);
		map.put("GL_FLG_3", gL_FLG_3);
		map.put("AP_FLG_1", aP_FLG_1);
		map.put("AP_FLG_2", aP_FLG_2);
		map.put("AR_FLG_1", aR_FLG_1);
		map.put("AR_FLG_2", aR_FLG_2);
		map.put("FA_FLG_1", fA_FLG_1);
		map.put("FA_FLG_2", fA_FLG_2);
		map.put("TRI_CODE_FLG", tRI_CODE_FLG);
		map.put("HAS_FLG", hAS_FLG);
		map.put("EMP_CODE_FLG", eMP_CODE_FLG);
		map.put("KNR_FLG_1", kNR_FLG_1);
		map.put("KNR_FLG_2", kNR_FLG_2);
		map.put("KNR_FLG_3", kNR_FLG_3);
		map.put("KNR_FLG_4", kNR_FLG_4);
		map.put("KNR_FLG_5", kNR_FLG_5);
		map.put("KNR_FLG_6", kNR_FLG_6);
		map.put("HM_FLG_1", hM_FLG_1);
		map.put("HM_FLG_2", hM_FLG_2);
		map.put("HM_FLG_3", hM_FLG_3);
		map.put("URI_ZEI_FLG", uRI_ZEI_FLG);
		map.put("SIR_ZEI_FLG", sIR_ZEI_FLG);
		map.put("MCR_FLG", mCR_FLG);
		map.put("EXC_FLG", eXC_FLG);
		map.put("STR_DATE", sTR_DATE);
		map.put("END_DATE", eND_DATE);
		map.put("INP_DATE", iNP_DATE);
		map.put("UPD_DATE", uPD_DATE);
		map.put("PRG_ID", pRG_ID);
		map.put("USR_ID", uSR_ID);

		return map;
	}
}
