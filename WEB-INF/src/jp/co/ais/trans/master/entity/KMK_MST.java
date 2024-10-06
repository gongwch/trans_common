package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 科目データ
 */
public class KMK_MST extends MasterBase {

	/**  */
	public static final String TABLE = "KMK_MST";

	private String kAI_CODE = "";

	private String kMK_CODE = "";

	private String kMK_NAME;

	private String kMK_NAME_S;

	private String kMK_NAME_K;

	private int sUM_KBN;

	private String kMK_SHU;

	private int dC_KBN;

	private int hKM_KBN;

	private String kMK_CNT_GL;

	private String kMK_CNT_AP;

	private String kMK_CNT_AR;

	private String kOTEI_DEP_CODE;

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

	private String kMK_CNT_BG;

	private String sKN_CODE_DR;

	private String sKN_CODE_CR;

	private String kMK_CNT_SOUSAI;

	private int kESI_KBN;

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
	public String getKMK_NAME() {
		return kMK_NAME;
	}

	/**
	 * @param kMK_NAME
	 */
	public void setKMK_NAME(String kMK_NAME) {
		this.kMK_NAME = kMK_NAME;
	}

	/**
	 * @return String
	 */
	public String getKMK_NAME_S() {
		return kMK_NAME_S;
	}

	/**
	 * @param kMK_NAME_S
	 */
	public void setKMK_NAME_S(String kMK_NAME_S) {
		this.kMK_NAME_S = kMK_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getKMK_NAME_K() {
		return kMK_NAME_K;
	}

	/**
	 * @param kMK_NAME_K
	 */
	public void setKMK_NAME_K(String kMK_NAME_K) {
		this.kMK_NAME_K = kMK_NAME_K;
	}

	/**
	 * @return int
	 */
	public int getSUM_KBN() {
		return sUM_KBN;
	}

	/**
	 * @param sUM_KBN
	 */
	public void setSUM_KBN(int sUM_KBN) {
		this.sUM_KBN = sUM_KBN;
	}

	/**
	 * @return String
	 */
	public String getKMK_SHU() {
		return kMK_SHU;
	}

	/**
	 * @param kMK_SHU
	 */
	public void setKMK_SHU(String kMK_SHU) {
		this.kMK_SHU = kMK_SHU;
	}

	/**
	 * @return int
	 */
	public int getDC_KBN() {
		return dC_KBN;
	}

	/**
	 * @param dC_KBN
	 */
	public void setDC_KBN(int dC_KBN) {
		this.dC_KBN = dC_KBN;
	}

	/**
	 * @return int
	 */
	public int getHKM_KBN() {
		return hKM_KBN;
	}

	/**
	 * @param hKM_KBN
	 */
	public void setHKM_KBN(int hKM_KBN) {
		this.hKM_KBN = hKM_KBN;
	}

	/**
	 * @return String
	 */
	public String getKMK_CNT_GL() {
		return kMK_CNT_GL;
	}

	/**
	 * @param kMK_CNT_GL
	 */
	public void setKMK_CNT_GL(String kMK_CNT_GL) {
		this.kMK_CNT_GL = kMK_CNT_GL;
	}

	/**
	 * @return String
	 */
	public String getKMK_CNT_AP() {
		return kMK_CNT_AP;
	}

	/**
	 * @param kMK_CNT_AP
	 */
	public void setKMK_CNT_AP(String kMK_CNT_AP) {
		this.kMK_CNT_AP = kMK_CNT_AP;
	}

	/**
	 * @return String
	 */
	public String getKMK_CNT_AR() {
		return kMK_CNT_AR;
	}

	/**
	 * @param kMK_CNT_AR
	 */
	public void setKMK_CNT_AR(String kMK_CNT_AR) {
		this.kMK_CNT_AR = kMK_CNT_AR;
	}

	/**
	 * @return String
	 */
	public String getKOTEI_DEP_CODE() {
		return kOTEI_DEP_CODE;
	}

	/**
	 * @param kOTEI_DEP_CODE
	 */
	public void setKOTEI_DEP_CODE(String kOTEI_DEP_CODE) {
		this.kOTEI_DEP_CODE = kOTEI_DEP_CODE;
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
	 * @return int int
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
	 * @return String
	 */
	public String getKMK_CNT_BG() {
		return kMK_CNT_BG;
	}

	/**
	 * @param kMK_CNT_BG
	 */
	public void setKMK_CNT_BG(String kMK_CNT_BG) {
		this.kMK_CNT_BG = kMK_CNT_BG;
	}

	/**
	 * @return String
	 */
	public String getSKN_CODE_DR() {
		return sKN_CODE_DR;
	}

	/**
	 * @param sKN_CODE_DR
	 */
	public void setSKN_CODE_DR(String sKN_CODE_DR) {
		this.sKN_CODE_DR = sKN_CODE_DR;
	}

	/**
	 * @return String
	 */
	public String getSKN_CODE_CR() {
		return sKN_CODE_CR;
	}

	/**
	 * @param sKN_CODE_CR
	 */
	public void setSKN_CODE_CR(String sKN_CODE_CR) {
		this.sKN_CODE_CR = sKN_CODE_CR;
	}

	/**
	 * @return String
	 */
	public String getKMK_CNT_SOUSAI() {
		return kMK_CNT_SOUSAI;
	}

	/**
	 * @param kMK_CNT_SOUSAI
	 */
	public void setKMK_CNT_SOUSAI(String kMK_CNT_SOUSAI) {
		this.kMK_CNT_SOUSAI = kMK_CNT_SOUSAI;
	}

	/**
	 * @return int
	 */
	public int getKESI_KBN() {
		return kESI_KBN;
	}

	/**
	 * @param kESI_KBN
	 */
	public void setKESI_KBN(int kESI_KBN) {
		this.kESI_KBN = kESI_KBN;
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
		buff.append("/kMK_NAME=").append(kMK_NAME);
		buff.append("/kMK_NAME_S=").append(kMK_NAME_S);
		buff.append("/kMK_NAME_K=").append(kMK_NAME_K);
		buff.append("/sUM_KBN=").append(sUM_KBN);
		buff.append("/kMK_SHU=").append(kMK_SHU);
		buff.append("/dC_KBN=").append(dC_KBN);
		buff.append("/hKM_KBN=").append(hKM_KBN);
		buff.append("/kMK_CNT_GL=").append(kMK_CNT_GL);
		buff.append("/kMK_CNT_AP=").append(kMK_CNT_AP);
		buff.append("/kMK_CNT_AR=").append(kMK_CNT_AR);
		buff.append("/kOTEI_DEP_CODE=").append(kOTEI_DEP_CODE);
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
		buff.append("/kMK_CNT_BG=").append(kMK_CNT_BG);
		buff.append("/sKN_CODE_DR=").append(sKN_CODE_DR);
		buff.append("/sKN_CODE_CR=").append(sKN_CODE_CR);
		buff.append("/kMK_CNT_SOUSAI=").append(kMK_CNT_SOUSAI);
		buff.append("/kESI_KBN=").append(kESI_KBN);
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
		list.add(kMK_NAME);
		list.add(kMK_NAME_S);
		list.add(kMK_NAME_K);
		list.add(sUM_KBN);
		list.add(kMK_SHU);
		list.add(dC_KBN);
		list.add(hKM_KBN);
		list.add(kMK_CNT_GL);
		list.add(kMK_CNT_AP);
		list.add(kMK_CNT_AR);
		list.add(kOTEI_DEP_CODE);
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
		list.add(kMK_CNT_BG);
		list.add(sKN_CODE_DR);
		list.add(sKN_CODE_CR);
		list.add(kMK_CNT_SOUSAI);
		list.add(kESI_KBN);
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
		map.put("KMK_NAME", kMK_NAME);
		map.put("KMK_NAME_S", kMK_NAME_S);
		map.put("KMK_NAME_K", kMK_NAME_K);
		map.put("SUM_KBN", sUM_KBN);
		map.put("KMK_SHU", kMK_SHU);
		map.put("DC_KBN", dC_KBN);
		map.put("HKM_KBN", hKM_KBN);
		map.put("KMK_CNT_GL", kMK_CNT_GL);
		map.put("KMK_CNT_AP", kMK_CNT_AP);
		map.put("KMK_CNT_AR", kMK_CNT_AR);
		map.put("KOTEI_DEP_CODE", kOTEI_DEP_CODE);
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
		map.put("KMK_CNT_BG", kMK_CNT_BG);
		map.put("SKN_CODE_DR", sKN_CODE_DR);
		map.put("SKN_CODE_CR", sKN_CODE_CR);
		map.put("KMK_CNT_SOUSAI", kMK_CNT_SOUSAI);
		map.put("KESI_KBN", kESI_KBN);
		map.put("STR_DATE", sTR_DATE);
		map.put("END_DATE", eND_DATE);
		map.put("INP_DATE", iNP_DATE);
		map.put("UPD_DATE", uPD_DATE);
		map.put("PRG_ID", pRG_ID);
		map.put("USR_ID", uSR_ID);

		return map;
	}
}
