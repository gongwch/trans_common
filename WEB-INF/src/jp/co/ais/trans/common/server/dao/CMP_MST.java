package jp.co.ais.trans.common.server.dao;

import java.util.*;

import jp.co.ais.trans.common.server.*;

/**
 * 会社コントロールマスタ(旧バージョン).<br>
 * KTの方を利用すること。
 */
public class CMP_MST implements TInterfaceHasToObjectArray {

	/** テーブル名 */
	public static final String TABLE = "CMP_MST";

	/** ID */
	public static final String kAI_CODE_ID = "sequence, sequenceName=kAI_CODE";

	private String kAI_CODE = "";

	private String cMP_KMK_NAME;

	private String cMP_HKM_NAME;

	private int cMP_UKM_KBN;

	private String cMP_UKM_NAME;

	private int kNR_KBN_1;

	private int kNR_KBN_2;

	private int kNR_KBN_3;

	private int kNR_KBN_4;

	private int kNR_KBN_5;

	private int kNR_KBN_6;

	private String kNR_NAME_1;

	private String kNR_NAME_2;

	private String kNR_NAME_3;

	private String kNR_NAME_4;

	private String kNR_NAME_5;

	private String kNR_NAME_6;

	private int cMP_HM_KBN_1;

	private int cMP_HM_KBN_2;

	private int cMP_HM_KBN_3;

	private String cMP_HM_NAME_1;

	private String cMP_HM_NAME_2;

	private String cMP_HM_NAME_3;

	private Integer cMP_KISYU;

	private String jID_1;

	private String jID_2;

	private String jID_3;

	private int aUTO_NO_KETA;

	private int pRINT_KBN;

	private int pRINT_DEF;

	private int cMP_G_SHONIN_FLG;

	private int cMP_K_SHONIN_FLG;

	private String cUR_CODE = "";

	private int rATE_KBN;

	private int kU_KBN;

	private int kB_KBN;

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
	public String getCMP_KMK_NAME() {
		return cMP_KMK_NAME;
	}

	/**
	 * @param cMP_KMK_NAME
	 */
	public void setCMP_KMK_NAME(String cMP_KMK_NAME) {
		this.cMP_KMK_NAME = cMP_KMK_NAME;
	}

	/**
	 * @return obj
	 */
	public String getCMP_HKM_NAME() {
		return cMP_HKM_NAME;
	}

	/**
	 * @param cMP_HKM_NAME
	 */
	public void setCMP_HKM_NAME(String cMP_HKM_NAME) {
		this.cMP_HKM_NAME = cMP_HKM_NAME;
	}

	/**
	 * @return obj
	 */
	public int getCMP_UKM_KBN() {
		return cMP_UKM_KBN;
	}

	/**
	 * @param cMP_UKM_KBN
	 */
	public void setCMP_UKM_KBN(int cMP_UKM_KBN) {
		this.cMP_UKM_KBN = cMP_UKM_KBN;
	}

	/**
	 * @return obj
	 */
	public String getCMP_UKM_NAME() {
		return cMP_UKM_NAME;
	}

	/**
	 * @param cMP_UKM_NAME
	 */
	public void setCMP_UKM_NAME(String cMP_UKM_NAME) {
		this.cMP_UKM_NAME = cMP_UKM_NAME;
	}

	/**
	 * @return obj
	 */
	public int getKNR_KBN_1() {
		return kNR_KBN_1;
	}

	/**
	 * @param kNR_KBN_1
	 */
	public void setKNR_KBN_1(int kNR_KBN_1) {
		this.kNR_KBN_1 = kNR_KBN_1;
	}

	/**
	 * @return obj
	 */
	public int getKNR_KBN_2() {
		return kNR_KBN_2;
	}

	/**
	 * @param kNR_KBN_2
	 */
	public void setKNR_KBN_2(int kNR_KBN_2) {
		this.kNR_KBN_2 = kNR_KBN_2;
	}

	/**
	 * @return obj
	 */
	public int getKNR_KBN_3() {
		return kNR_KBN_3;
	}

	/**
	 * @param kNR_KBN_3
	 */
	public void setKNR_KBN_3(int kNR_KBN_3) {
		this.kNR_KBN_3 = kNR_KBN_3;
	}

	/**
	 * @return obj
	 */
	public int getKNR_KBN_4() {
		return kNR_KBN_4;
	}

	/**
	 * @param kNR_KBN_4
	 */
	public void setKNR_KBN_4(int kNR_KBN_4) {
		this.kNR_KBN_4 = kNR_KBN_4;
	}

	/**
	 * @return obj
	 */
	public int getKNR_KBN_5() {
		return kNR_KBN_5;
	}

	/**
	 * @param kNR_KBN_5
	 */
	public void setKNR_KBN_5(int kNR_KBN_5) {
		this.kNR_KBN_5 = kNR_KBN_5;
	}

	/**
	 * @return obj
	 */
	public int getKNR_KBN_6() {
		return kNR_KBN_6;
	}

	/**
	 * @param kNR_KBN_6
	 */
	public void setKNR_KBN_6(int kNR_KBN_6) {
		this.kNR_KBN_6 = kNR_KBN_6;
	}

	/**
	 * @return obj
	 */
	public String getKNR_NAME_1() {
		return kNR_NAME_1;
	}

	/**
	 * @param kNR_NAME_1
	 */
	public void setKNR_NAME_1(String kNR_NAME_1) {
		this.kNR_NAME_1 = kNR_NAME_1;
	}

	/**
	 * @return obj
	 */
	public String getKNR_NAME_2() {
		return kNR_NAME_2;
	}

	/**
	 * @param kNR_NAME_2
	 */
	public void setKNR_NAME_2(String kNR_NAME_2) {
		this.kNR_NAME_2 = kNR_NAME_2;
	}

	/**
	 * @return obj
	 */
	public String getKNR_NAME_3() {
		return kNR_NAME_3;
	}

	/**
	 * @param kNR_NAME_3
	 */
	public void setKNR_NAME_3(String kNR_NAME_3) {
		this.kNR_NAME_3 = kNR_NAME_3;
	}

	/**
	 * @return obj
	 */
	public String getKNR_NAME_4() {
		return kNR_NAME_4;
	}

	/**
	 * @param kNR_NAME_4
	 */
	public void setKNR_NAME_4(String kNR_NAME_4) {
		this.kNR_NAME_4 = kNR_NAME_4;
	}

	/**
	 * @return obj
	 */
	public String getKNR_NAME_5() {
		return kNR_NAME_5;
	}

	/**
	 * @param kNR_NAME_5
	 */
	public void setKNR_NAME_5(String kNR_NAME_5) {
		this.kNR_NAME_5 = kNR_NAME_5;
	}

	/**
	 * @return obj
	 */
	public String getKNR_NAME_6() {
		return kNR_NAME_6;
	}

	/**
	 * @param kNR_NAME_6
	 */
	public void setKNR_NAME_6(String kNR_NAME_6) {
		this.kNR_NAME_6 = kNR_NAME_6;
	}

	/**
	 * @return obj
	 */
	public int getCMP_HM_KBN_1() {
		return cMP_HM_KBN_1;
	}

	/**
	 * @param cMP_HM_KBN_1
	 */
	public void setCMP_HM_KBN_1(int cMP_HM_KBN_1) {
		this.cMP_HM_KBN_1 = cMP_HM_KBN_1;
	}

	/**
	 * @return obj
	 */
	public int getCMP_HM_KBN_2() {
		return cMP_HM_KBN_2;
	}

	/**
	 * @param cMP_HM_KBN_2
	 */
	public void setCMP_HM_KBN_2(int cMP_HM_KBN_2) {
		this.cMP_HM_KBN_2 = cMP_HM_KBN_2;
	}

	/**
	 * @return obj
	 */
	public int getCMP_HM_KBN_3() {
		return cMP_HM_KBN_3;
	}

	/**
	 * @param cMP_HM_KBN_3
	 */
	public void setCMP_HM_KBN_3(int cMP_HM_KBN_3) {
		this.cMP_HM_KBN_3 = cMP_HM_KBN_3;
	}

	/**
	 * @return obj
	 */
	public String getCMP_HM_NAME_1() {
		return cMP_HM_NAME_1;
	}

	/**
	 * @param cMP_HM_NAME_1
	 */
	public void setCMP_HM_NAME_1(String cMP_HM_NAME_1) {
		this.cMP_HM_NAME_1 = cMP_HM_NAME_1;
	}

	/**
	 * @return obj
	 */
	public String getCMP_HM_NAME_2() {
		return cMP_HM_NAME_2;
	}

	/**
	 * @param cMP_HM_NAME_2
	 */
	public void setCMP_HM_NAME_2(String cMP_HM_NAME_2) {
		this.cMP_HM_NAME_2 = cMP_HM_NAME_2;
	}

	/**
	 * @return obj
	 */
	public String getCMP_HM_NAME_3() {
		return cMP_HM_NAME_3;
	}

	/**
	 * @param cMP_HM_NAME_3
	 */
	public void setCMP_HM_NAME_3(String cMP_HM_NAME_3) {
		this.cMP_HM_NAME_3 = cMP_HM_NAME_3;
	}

	/**
	 * @return obj
	 */
	public Integer getCMP_KISYU() {
		return cMP_KISYU;
	}

	/**
	 * @param cMP_KISYU
	 */
	public void setCMP_KISYU(Integer cMP_KISYU) {
		this.cMP_KISYU = cMP_KISYU;
	}

	/**
	 * @return obj
	 */
	public String getJID_1() {
		return jID_1;
	}

	/**
	 * @param jID_1
	 */
	public void setJID_1(String jID_1) {
		this.jID_1 = jID_1;
	}

	/**
	 * @return obj
	 */
	public String getJID_2() {
		return jID_2;
	}

	/**
	 * @param jID_2
	 */
	public void setJID_2(String jID_2) {
		this.jID_2 = jID_2;
	}

	/**
	 * @return obj
	 */
	public String getJID_3() {
		return jID_3;
	}

	/**
	 * @param jID_3
	 */
	public void setJID_3(String jID_3) {
		this.jID_3 = jID_3;
	}

	/**
	 * @return obj
	 */
	public int getAUTO_NO_KETA() {
		return aUTO_NO_KETA;
	}

	/**
	 * @param aUTO_NO_KETA
	 */
	public void setAUTO_NO_KETA(int aUTO_NO_KETA) {
		this.aUTO_NO_KETA = aUTO_NO_KETA;
	}

	/**
	 * @return obj
	 */
	public int getPRINT_KBN() {
		return pRINT_KBN;
	}

	/**
	 * @param pRINT_KBN
	 */
	public void setPRINT_KBN(int pRINT_KBN) {
		this.pRINT_KBN = pRINT_KBN;
	}

	/**
	 * @return obj
	 */
	public int getPRINT_DEF() {
		return pRINT_DEF;
	}

	/**
	 * @param pRINT_DEF
	 */
	public void setPRINT_DEF(int pRINT_DEF) {
		this.pRINT_DEF = pRINT_DEF;
	}

	/**
	 * @return obj
	 */
	public int getCMP_G_SHONIN_FLG() {
		return cMP_G_SHONIN_FLG;
	}

	/**
	 * @param cMP_G_SHONIN_FLG
	 */
	public void setCMP_G_SHONIN_FLG(int cMP_G_SHONIN_FLG) {
		this.cMP_G_SHONIN_FLG = cMP_G_SHONIN_FLG;
	}

	/**
	 * @return obj
	 */
	public int getCMP_K_SHONIN_FLG() {
		return cMP_K_SHONIN_FLG;
	}

	/**
	 * @param cMP_K_SHONIN_FLG
	 */
	public void setCMP_K_SHONIN_FLG(int cMP_K_SHONIN_FLG) {
		this.cMP_K_SHONIN_FLG = cMP_K_SHONIN_FLG;
	}

	/**
	 * @return obj
	 */
	public String getCUR_CODE() {
		return cUR_CODE;
	}

	/**
	 * @param cUR_CODE
	 */
	public void setCUR_CODE(String cUR_CODE) {
		this.cUR_CODE = cUR_CODE;
	}

	/**
	 * @return obj
	 */
	public int getRATE_KBN() {
		return rATE_KBN;
	}

	/**
	 * @param rATE_KBN
	 */
	public void setRATE_KBN(int rATE_KBN) {
		this.rATE_KBN = rATE_KBN;
	}

	/**
	 * @return obj
	 */
	public int getKU_KBN() {
		return kU_KBN;
	}

	/**
	 * @param kU_KBN
	 */
	public void setKU_KBN(int kU_KBN) {
		this.kU_KBN = kU_KBN;
	}

	/**
	 * @return obj
	 */
	public int getKB_KBN() {
		return kB_KBN;
	}

	/**
	 * @param kB_KBN
	 */
	public void setKB_KBN(int kB_KBN) {
		this.kB_KBN = kB_KBN;
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

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/cMP_KMK_NAME=").append(cMP_KMK_NAME);
		buff.append("/cMP_HKM_NAME=").append(cMP_HKM_NAME);
		buff.append("/cMP_UKM_KBN=").append(cMP_UKM_KBN);
		buff.append("/cMP_UKM_NAME=").append(cMP_UKM_NAME);
		buff.append("/kNR_KBN_1=").append(kNR_KBN_1);
		buff.append("/kNR_KBN_2=").append(kNR_KBN_2);
		buff.append("/kNR_KBN_3=").append(kNR_KBN_3);
		buff.append("/kNR_KBN_4=").append(kNR_KBN_4);
		buff.append("/kNR_KBN_5=").append(kNR_KBN_5);
		buff.append("/kNR_KBN_6=").append(kNR_KBN_6);
		buff.append("/kNR_NAME_1=").append(kNR_NAME_1);
		buff.append("/kNR_NAME_2=").append(kNR_NAME_2);
		buff.append("/kNR_NAME_3=").append(kNR_NAME_3);
		buff.append("/kNR_NAME_4=").append(kNR_NAME_4);
		buff.append("/kNR_NAME_5=").append(kNR_NAME_5);
		buff.append("/kNR_NAME_6=").append(kNR_NAME_6);
		buff.append("/cMP_HM_KBN_1=").append(cMP_HM_KBN_1);
		buff.append("/cMP_HM_KBN_2=").append(cMP_HM_KBN_2);
		buff.append("/cMP_HM_KBN_3=").append(cMP_HM_KBN_3);
		buff.append("/cMP_HM_NAME_1=").append(cMP_HM_NAME_1);
		buff.append("/cMP_HM_NAME_2=").append(cMP_HM_NAME_2);
		buff.append("/cMP_HM_NAME_3=").append(cMP_HM_NAME_3);
		buff.append("/cMP_KISYU=").append(cMP_KISYU);
		buff.append("/jID_1=").append(jID_1);
		buff.append("/jID_2=").append(jID_2);
		buff.append("/jID_3=").append(jID_3);
		buff.append("/aUTO_NO_KETA=").append(aUTO_NO_KETA);
		buff.append("/pRINT_KBN=").append(pRINT_KBN);
		buff.append("/pRINT_DEF=").append(pRINT_DEF);
		buff.append("/cMP_G_SHONIN_FLG=").append(cMP_G_SHONIN_FLG);
		buff.append("/cMP_K_SHONIN_FLG=").append(cMP_K_SHONIN_FLG);
		buff.append("/cUR_CODE=").append(cUR_CODE);
		buff.append("/rATE_KBN=").append(rATE_KBN);
		buff.append("/kU_KBN=").append(kU_KBN);
		buff.append("/kB_KBN=").append(kB_KBN);
		buff.append("/iNP_DATE=").append(iNP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("]");
		return buff.toString();
	}

	/**
	 * @see jp.co.ais.trans.common.server.TInterfaceHasToObjectArray#toObjectArray()
	 */
	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(cMP_KMK_NAME);
		list.add(cMP_HKM_NAME);
		list.add(cMP_UKM_KBN);
		list.add(cMP_UKM_NAME);
		list.add(kNR_KBN_1);
		list.add(kNR_KBN_2);
		list.add(kNR_KBN_3);
		list.add(kNR_KBN_4);
		list.add(kNR_KBN_5);
		list.add(kNR_KBN_6);
		list.add(kNR_NAME_1);
		list.add(kNR_NAME_2);
		list.add(kNR_NAME_3);
		list.add(kNR_NAME_4);
		list.add(kNR_NAME_5);
		list.add(kNR_NAME_6);
		list.add(cMP_HM_KBN_1);
		list.add(cMP_HM_KBN_2);
		list.add(cMP_HM_KBN_3);
		list.add(cMP_HM_NAME_1);
		list.add(cMP_HM_NAME_2);
		list.add(cMP_HM_NAME_3);
		list.add(cMP_KISYU);
		list.add(jID_1);
		list.add(jID_2);
		list.add(jID_3);
		list.add(aUTO_NO_KETA);
		list.add(pRINT_KBN);
		list.add(pRINT_DEF);
		list.add(cMP_G_SHONIN_FLG);
		list.add(cMP_K_SHONIN_FLG);
		list.add(cUR_CODE);
		list.add(rATE_KBN);
		list.add(kU_KBN);
		list.add(kB_KBN);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
