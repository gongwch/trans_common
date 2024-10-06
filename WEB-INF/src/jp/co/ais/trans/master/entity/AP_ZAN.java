package jp.co.ais.trans.master.entity;

import java.math.BigDecimal;
import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;

public class AP_ZAN extends MasterBase {
	public static final String TABLE = "AP_ZAN";

	private String kAI_CODE = "";

	private Date zAN_DEN_DATE;

	private String zAN_DEN_NO = "";

	private int zAN_GYO_NO;

	private int zAN_DC_KBN;

	private String zAN_KMK_CODE = "";

	private String zAN_HKM_CODE;

	private String zAN_UKM_CODE;

	private String zAN_DEP_CODE = "";

	private String zAN_UKE_DEP_CODE;

	private String zAN_TEK_CODE;

	private String zAN_TEK;

	private String zAN_SIHA_CODE = "";

	private BigDecimal zAN_KIN;

	private Date zAN_SIHA_DATE;

	private Date zAN_JSK_DATE;

	private Date zAN_FURI_DATE;

	private String zAN_SIHA_KBN = "";

	private String zAN_HOH_CODE;

	private int zAN_HORYU_KBN;

	private int zAN_KESAI_KBN;

	private String zAN_SEI_NO;

	private String zAN_DATA_KBN = "";

	private String zAN_NAI_CODE = "";

	private int zAN_FURI_TESU_KBN;

	private int zAN_YUSO_KBN;

	private int zAN_YUSO_TESU_KBN;

	private Integer zAN_TEG_SAITO;

	private Date zAN_TEG_SIHA_KIJITU;

	private String zAN_FURI_CBK_CODE;

	private String zAN_FURI_BNK_CODE;

	private String zAN_FURI_STN_CODE;

	private String zAN_TEG_CBK_CODE;

	private String zAN_TEG_BNK_CODE;

	private String zAN_TEG_STN_CODE;

	private int zAN_LIST_KBN;

	private String zAN_SIHA_DEN_NO;

	private int zAN_SIHA_KAGEN_KBN;

	private int zAN_TEG_KAGEN_KBN;

	private int zAN_FURI_KAGEN_KBN;

	private Date zAN_INP_DATE;

	private String pRG_ID;

	private String zAN_TJK_CODE;

	private String zAN_CUR_CODE;

	private BigDecimal zAN_CUR_RATE;

	private BigDecimal zAN_IN_SIHA_KIN;

	private String zAN_SYS_KBN;

	private String zAN_DEN_SYU;

	private String zAN_UTK_NO;

	private Integer zAN_SIHA_GYO_NO;

	private String cBK_CUR_CODE;

	public String getKAI_CODE() {
		return kAI_CODE;
	}

	public void setKAI_CODE(String kAI_CODE) {
		this.kAI_CODE = kAI_CODE;
	}

	public Date getZAN_DEN_DATE() {
		return zAN_DEN_DATE;
	}

	public void setZAN_DEN_DATE(Date zAN_DEN_DATE) {
		this.zAN_DEN_DATE = zAN_DEN_DATE;
	}

	public String getZAN_DEN_NO() {
		return zAN_DEN_NO;
	}

	public void setZAN_DEN_NO(String zAN_DEN_NO) {
		this.zAN_DEN_NO = zAN_DEN_NO;
	}

	public int getZAN_GYO_NO() {
		return zAN_GYO_NO;
	}

	public void setZAN_GYO_NO(int zAN_GYO_NO) {
		this.zAN_GYO_NO = zAN_GYO_NO;
	}

	public int getZAN_DC_KBN() {
		return zAN_DC_KBN;
	}

	public void setZAN_DC_KBN(int zAN_DC_KBN) {
		this.zAN_DC_KBN = zAN_DC_KBN;
	}

	public String getZAN_KMK_CODE() {
		return zAN_KMK_CODE;
	}

	public void setZAN_KMK_CODE(String zAN_KMK_CODE) {
		this.zAN_KMK_CODE = zAN_KMK_CODE;
	}

	public String getZAN_HKM_CODE() {
		return zAN_HKM_CODE;
	}

	public void setZAN_HKM_CODE(String zAN_HKM_CODE) {
		this.zAN_HKM_CODE = zAN_HKM_CODE;
	}

	public String getZAN_UKM_CODE() {
		return zAN_UKM_CODE;
	}

	public void setZAN_UKM_CODE(String zAN_UKM_CODE) {
		this.zAN_UKM_CODE = zAN_UKM_CODE;
	}

	public String getZAN_DEP_CODE() {
		return zAN_DEP_CODE;
	}

	public void setZAN_DEP_CODE(String zAN_DEP_CODE) {
		this.zAN_DEP_CODE = zAN_DEP_CODE;
	}

	public String getZAN_UKE_DEP_CODE() {
		return zAN_UKE_DEP_CODE;
	}

	public void setZAN_UKE_DEP_CODE(String zAN_UKE_DEP_CODE) {
		this.zAN_UKE_DEP_CODE = zAN_UKE_DEP_CODE;
	}

	public String getZAN_TEK_CODE() {
		return zAN_TEK_CODE;
	}

	public void setZAN_TEK_CODE(String zAN_TEK_CODE) {
		this.zAN_TEK_CODE = zAN_TEK_CODE;
	}

	public String getZAN_TEK() {
		return zAN_TEK;
	}

	public void setZAN_TEK(String zAN_TEK) {
		this.zAN_TEK = zAN_TEK;
	}

	public String getZAN_SIHA_CODE() {
		return zAN_SIHA_CODE;
	}

	public void setZAN_SIHA_CODE(String zAN_SIHA_CODE) {
		this.zAN_SIHA_CODE = zAN_SIHA_CODE;
	}

	public BigDecimal getZAN_KIN() {
		return zAN_KIN;
	}

	public void setZAN_KIN(BigDecimal zAN_KIN) {
		this.zAN_KIN = zAN_KIN;
	}

	public Date getZAN_SIHA_DATE() {
		return zAN_SIHA_DATE;
	}

	public void setZAN_SIHA_DATE(Date zAN_SIHA_DATE) {
		this.zAN_SIHA_DATE = zAN_SIHA_DATE;
	}

	public Date getZAN_JSK_DATE() {
		return zAN_JSK_DATE;
	}

	public void setZAN_JSK_DATE(Date zAN_JSK_DATE) {
		this.zAN_JSK_DATE = zAN_JSK_DATE;
	}

	public Date getZAN_FURI_DATE() {
		return zAN_FURI_DATE;
	}

	public void setZAN_FURI_DATE(Date zAN_FURI_DATE) {
		this.zAN_FURI_DATE = zAN_FURI_DATE;
	}

	public String getZAN_SIHA_KBN() {
		return zAN_SIHA_KBN;
	}

	public void setZAN_SIHA_KBN(String zAN_SIHA_KBN) {
		this.zAN_SIHA_KBN = zAN_SIHA_KBN;
	}

	public String getZAN_HOH_CODE() {
		return zAN_HOH_CODE;
	}

	public void setZAN_HOH_CODE(String zAN_HOH_CODE) {
		this.zAN_HOH_CODE = zAN_HOH_CODE;
	}

	public int getZAN_HORYU_KBN() {
		return zAN_HORYU_KBN;
	}

	public void setZAN_HORYU_KBN(int zAN_HORYU_KBN) {
		this.zAN_HORYU_KBN = zAN_HORYU_KBN;
	}

	public int getZAN_KESAI_KBN() {
		return zAN_KESAI_KBN;
	}

	public void setZAN_KESAI_KBN(int zAN_KESAI_KBN) {
		this.zAN_KESAI_KBN = zAN_KESAI_KBN;
	}

	public String getZAN_SEI_NO() {
		return zAN_SEI_NO;
	}

	public void setZAN_SEI_NO(String zAN_SEI_NO) {
		this.zAN_SEI_NO = zAN_SEI_NO;
	}

	public String getZAN_DATA_KBN() {
		return zAN_DATA_KBN;
	}

	public void setZAN_DATA_KBN(String zAN_DATA_KBN) {
		this.zAN_DATA_KBN = zAN_DATA_KBN;
	}

	public String getZAN_NAI_CODE() {
		return zAN_NAI_CODE;
	}

	public void setZAN_NAI_CODE(String zAN_NAI_CODE) {
		this.zAN_NAI_CODE = zAN_NAI_CODE;
	}

	public int getZAN_FURI_TESU_KBN() {
		return zAN_FURI_TESU_KBN;
	}

	public void setZAN_FURI_TESU_KBN(int zAN_FURI_TESU_KBN) {
		this.zAN_FURI_TESU_KBN = zAN_FURI_TESU_KBN;
	}

	public int getZAN_YUSO_KBN() {
		return zAN_YUSO_KBN;
	}

	public void setZAN_YUSO_KBN(int zAN_YUSO_KBN) {
		this.zAN_YUSO_KBN = zAN_YUSO_KBN;
	}

	public int getZAN_YUSO_TESU_KBN() {
		return zAN_YUSO_TESU_KBN;
	}

	public void setZAN_YUSO_TESU_KBN(int zAN_YUSO_TESU_KBN) {
		this.zAN_YUSO_TESU_KBN = zAN_YUSO_TESU_KBN;
	}

	public Integer getZAN_TEG_SAITO() {
		return zAN_TEG_SAITO;
	}

	public void setZAN_TEG_SAITO(Integer zAN_TEG_SAITO) {
		this.zAN_TEG_SAITO = zAN_TEG_SAITO;
	}

	public Date getZAN_TEG_SIHA_KIJITU() {
		return zAN_TEG_SIHA_KIJITU;
	}

	public void setZAN_TEG_SIHA_KIJITU(Date zAN_TEG_SIHA_KIJITU) {
		this.zAN_TEG_SIHA_KIJITU = zAN_TEG_SIHA_KIJITU;
	}

	public String getZAN_FURI_CBK_CODE() {
		return zAN_FURI_CBK_CODE;
	}

	public void setZAN_FURI_CBK_CODE(String zAN_FURI_CBK_CODE) {
		this.zAN_FURI_CBK_CODE = zAN_FURI_CBK_CODE;
	}

	public String getZAN_FURI_BNK_CODE() {
		return zAN_FURI_BNK_CODE;
	}

	public void setZAN_FURI_BNK_CODE(String zAN_FURI_BNK_CODE) {
		this.zAN_FURI_BNK_CODE = zAN_FURI_BNK_CODE;
	}

	public String getZAN_FURI_STN_CODE() {
		return zAN_FURI_STN_CODE;
	}

	public void setZAN_FURI_STN_CODE(String zAN_FURI_STN_CODE) {
		this.zAN_FURI_STN_CODE = zAN_FURI_STN_CODE;
	}

	public String getZAN_TEG_CBK_CODE() {
		return zAN_TEG_CBK_CODE;
	}

	public void setZAN_TEG_CBK_CODE(String zAN_TEG_CBK_CODE) {
		this.zAN_TEG_CBK_CODE = zAN_TEG_CBK_CODE;
	}

	public String getZAN_TEG_BNK_CODE() {
		return zAN_TEG_BNK_CODE;
	}

	public void setZAN_TEG_BNK_CODE(String zAN_TEG_BNK_CODE) {
		this.zAN_TEG_BNK_CODE = zAN_TEG_BNK_CODE;
	}

	public String getZAN_TEG_STN_CODE() {
		return zAN_TEG_STN_CODE;
	}

	public void setZAN_TEG_STN_CODE(String zAN_TEG_STN_CODE) {
		this.zAN_TEG_STN_CODE = zAN_TEG_STN_CODE;
	}

	public int getZAN_LIST_KBN() {
		return zAN_LIST_KBN;
	}

	public void setZAN_LIST_KBN(int zAN_LIST_KBN) {
		this.zAN_LIST_KBN = zAN_LIST_KBN;
	}

	public String getZAN_SIHA_DEN_NO() {
		return zAN_SIHA_DEN_NO;
	}

	public void setZAN_SIHA_DEN_NO(String zAN_SIHA_DEN_NO) {
		this.zAN_SIHA_DEN_NO = zAN_SIHA_DEN_NO;
	}

	public int getZAN_SIHA_KAGEN_KBN() {
		return zAN_SIHA_KAGEN_KBN;
	}

	public void setZAN_SIHA_KAGEN_KBN(int zAN_SIHA_KAGEN_KBN) {
		this.zAN_SIHA_KAGEN_KBN = zAN_SIHA_KAGEN_KBN;
	}

	public int getZAN_TEG_KAGEN_KBN() {
		return zAN_TEG_KAGEN_KBN;
	}

	public void setZAN_TEG_KAGEN_KBN(int zAN_TEG_KAGEN_KBN) {
		this.zAN_TEG_KAGEN_KBN = zAN_TEG_KAGEN_KBN;
	}

	public int getZAN_FURI_KAGEN_KBN() {
		return zAN_FURI_KAGEN_KBN;
	}

	public void setZAN_FURI_KAGEN_KBN(int zAN_FURI_KAGEN_KBN) {
		this.zAN_FURI_KAGEN_KBN = zAN_FURI_KAGEN_KBN;
	}

	public Date getZAN_INP_DATE() {
		return zAN_INP_DATE;
	}

	public void setZAN_INP_DATE(Date zAN_INP_DATE) {
		this.zAN_INP_DATE = zAN_INP_DATE;
	}

	public String getPRG_ID() {
		return pRG_ID;
	}

	public void setPRG_ID(String pRG_ID) {
		this.pRG_ID = pRG_ID;
	}

	public String getZAN_TJK_CODE() {
		return zAN_TJK_CODE;
	}

	public void setZAN_TJK_CODE(String zAN_TJK_CODE) {
		this.zAN_TJK_CODE = zAN_TJK_CODE;
	}

	public String getZAN_CUR_CODE() {
		return zAN_CUR_CODE;
	}

	public void setZAN_CUR_CODE(String zAN_CUR_CODE) {
		this.zAN_CUR_CODE = zAN_CUR_CODE;
	}

	public BigDecimal getZAN_CUR_RATE() {
		return zAN_CUR_RATE;
	}

	public void setZAN_CUR_RATE(BigDecimal zAN_CUR_RATE) {
		this.zAN_CUR_RATE = zAN_CUR_RATE;
	}

	public BigDecimal getZAN_IN_SIHA_KIN() {
		return zAN_IN_SIHA_KIN;
	}

	public void setZAN_IN_SIHA_KIN(BigDecimal zAN_IN_SIHA_KIN) {
		this.zAN_IN_SIHA_KIN = zAN_IN_SIHA_KIN;
	}

	public String getZAN_SYS_KBN() {
		return zAN_SYS_KBN;
	}

	public void setZAN_SYS_KBN(String zAN_SYS_KBN) {
		this.zAN_SYS_KBN = zAN_SYS_KBN;
	}

	public String getZAN_DEN_SYU() {
		return zAN_DEN_SYU;
	}

	public void setZAN_DEN_SYU(String zAN_DEN_SYU) {
		this.zAN_DEN_SYU = zAN_DEN_SYU;
	}

	public String getZAN_UTK_NO() {
		return zAN_UTK_NO;
	}

	public void setZAN_UTK_NO(String zAN_UTK_NO) {
		this.zAN_UTK_NO = zAN_UTK_NO;
	}

	public Integer getZAN_SIHA_GYO_NO() {
		return zAN_SIHA_GYO_NO;
	}

	public void setZAN_SIHA_GYO_NO(Integer zAN_SIHA_GYO_NO) {
		this.zAN_SIHA_GYO_NO = zAN_SIHA_GYO_NO;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/zAN_DEN_DATE=").append(zAN_DEN_DATE);
		buff.append("/zAN_DEN_NO=").append(zAN_DEN_NO);
		buff.append("/zAN_GYO_NO=").append(zAN_GYO_NO);
		buff.append("/zAN_DC_KBN=").append(zAN_DC_KBN);
		buff.append("/zAN_KMK_CODE=").append(zAN_KMK_CODE);
		buff.append("/zAN_HKM_CODE=").append(zAN_HKM_CODE);
		buff.append("/zAN_UKM_CODE=").append(zAN_UKM_CODE);
		buff.append("/zAN_DEP_CODE=").append(zAN_DEP_CODE);
		buff.append("/zAN_UKE_DEP_CODE=").append(zAN_UKE_DEP_CODE);
		buff.append("/zAN_TEK_CODE=").append(zAN_TEK_CODE);
		buff.append("/zAN_TEK=").append(zAN_TEK);
		buff.append("/zAN_SIHA_CODE=").append(zAN_SIHA_CODE);
		buff.append("/zAN_KIN=").append(zAN_KIN);
		buff.append("/zAN_SIHA_DATE=").append(zAN_SIHA_DATE);
		buff.append("/zAN_JSK_DATE=").append(zAN_JSK_DATE);
		buff.append("/zAN_FURI_DATE=").append(zAN_FURI_DATE);
		buff.append("/zAN_SIHA_KBN=").append(zAN_SIHA_KBN);
		buff.append("/zAN_HOH_CODE=").append(zAN_HOH_CODE);
		buff.append("/zAN_HORYU_KBN=").append(zAN_HORYU_KBN);
		buff.append("/zAN_KESAI_KBN=").append(zAN_KESAI_KBN);
		buff.append("/zAN_SEI_NO=").append(zAN_SEI_NO);
		buff.append("/zAN_DATA_KBN=").append(zAN_DATA_KBN);
		buff.append("/zAN_NAI_CODE=").append(zAN_NAI_CODE);
		buff.append("/zAN_FURI_TESU_KBN=").append(zAN_FURI_TESU_KBN);
		buff.append("/zAN_YUSO_KBN=").append(zAN_YUSO_KBN);
		buff.append("/zAN_YUSO_TESU_KBN=").append(zAN_YUSO_TESU_KBN);
		buff.append("/zAN_TEG_SAITO=").append(zAN_TEG_SAITO);
		buff.append("/zAN_TEG_SIHA_KIJITU=").append(zAN_TEG_SIHA_KIJITU);
		buff.append("/zAN_FURI_CBK_CODE=").append(zAN_FURI_CBK_CODE);
		buff.append("/zAN_FURI_BNK_CODE=").append(zAN_FURI_BNK_CODE);
		buff.append("/zAN_FURI_STN_CODE=").append(zAN_FURI_STN_CODE);
		buff.append("/zAN_TEG_CBK_CODE=").append(zAN_TEG_CBK_CODE);
		buff.append("/zAN_TEG_BNK_CODE=").append(zAN_TEG_BNK_CODE);
		buff.append("/zAN_TEG_STN_CODE=").append(zAN_TEG_STN_CODE);
		buff.append("/zAN_LIST_KBN=").append(zAN_LIST_KBN);
		buff.append("/zAN_SIHA_DEN_NO=").append(zAN_SIHA_DEN_NO);
		buff.append("/zAN_SIHA_KAGEN_KBN=").append(zAN_SIHA_KAGEN_KBN);
		buff.append("/zAN_TEG_KAGEN_KBN=").append(zAN_TEG_KAGEN_KBN);
		buff.append("/zAN_FURI_KAGEN_KBN=").append(zAN_FURI_KAGEN_KBN);
		buff.append("/zAN_INP_DATE=").append(zAN_INP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("/zAN_TJK_CODE=").append(zAN_TJK_CODE);
		buff.append("/zAN_CUR_CODE=").append(zAN_CUR_CODE);
		buff.append("/zAN_CUR_RATE=").append(zAN_CUR_RATE);
		buff.append("/zAN_IN_SIHA_KIN=").append(zAN_IN_SIHA_KIN);
		buff.append("/zAN_SYS_KBN=").append(zAN_SYS_KBN);
		buff.append("/zAN_DEN_SYU=").append(zAN_DEN_SYU);
		buff.append("/zAN_UTK_NO=").append(zAN_UTK_NO);
		buff.append("/zAN_SIHA_GYO_NO=").append(zAN_SIHA_GYO_NO);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(zAN_DEN_DATE);
		list.add(zAN_DEN_NO);
		list.add(zAN_GYO_NO);
		list.add(zAN_DC_KBN);
		list.add(zAN_KMK_CODE);
		list.add(zAN_HKM_CODE);
		list.add(zAN_UKM_CODE);
		list.add(zAN_DEP_CODE);
		list.add(zAN_UKE_DEP_CODE);
		list.add(zAN_TEK_CODE);
		list.add(zAN_TEK);
		list.add(zAN_SIHA_CODE);
		list.add(zAN_KIN);
		list.add(zAN_SIHA_DATE);
		list.add(zAN_JSK_DATE);
		list.add(zAN_FURI_DATE);
		list.add(zAN_SIHA_KBN);
		list.add(zAN_HOH_CODE);
		list.add(zAN_HORYU_KBN);
		list.add(zAN_KESAI_KBN);
		list.add(zAN_SEI_NO);
		list.add(zAN_DATA_KBN);
		list.add(zAN_NAI_CODE);
		list.add(zAN_FURI_TESU_KBN);
		list.add(zAN_YUSO_KBN);
		list.add(zAN_YUSO_TESU_KBN);
		list.add(zAN_TEG_SAITO);
		list.add(zAN_TEG_SIHA_KIJITU);
		list.add(zAN_FURI_CBK_CODE);
		list.add(zAN_FURI_BNK_CODE);
		list.add(zAN_FURI_STN_CODE);
		list.add(zAN_TEG_CBK_CODE);
		list.add(zAN_TEG_BNK_CODE);
		list.add(zAN_TEG_STN_CODE);
		list.add(zAN_LIST_KBN);
		list.add(zAN_SIHA_DEN_NO);
		list.add(zAN_SIHA_KAGEN_KBN);
		list.add(zAN_TEG_KAGEN_KBN);
		list.add(zAN_FURI_KAGEN_KBN);
		list.add(zAN_INP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);
		list.add(zAN_TJK_CODE);
		list.add(zAN_CUR_CODE);
		list.add(zAN_CUR_RATE);
		list.add(zAN_IN_SIHA_KIN);
		list.add(zAN_SYS_KBN);
		list.add(zAN_DEN_SYU);
		list.add(zAN_UTK_NO);
		list.add(zAN_SIHA_GYO_NO);

		return list;
	}

	/**
	 * ObjectArrayÇ©ÇÁèÛë‘Çç\ízÇ∑ÇÈ<br>
	 * (toObjectArray()Ç≈ÉäÉXÉgèÛë‘Ç…ÇµÇΩÇ‡ÇÃÇå≥Ç…ñﬂÇ∑)
	 * 
	 * @param list ÉäÉXÉg
	 * @throws ParseException ì`ï[ì˙ïtïœä∑é∏îs
	 */
	public void reflectFromArray(List<String> list) throws ParseException {
		kAI_CODE = list.get(0);
		zAN_DEN_DATE = DateUtil.toYMDDate(list.get(1));
		zAN_DEN_NO = list.get(2);
		zAN_GYO_NO = Integer.parseInt(list.get(3));
		zAN_DC_KBN = Integer.parseInt(list.get(4));
		zAN_KMK_CODE = list.get(5);
		zAN_HKM_CODE = list.get(6);
		zAN_UKM_CODE = list.get(7);
		zAN_DEP_CODE = list.get(8);
		zAN_UKE_DEP_CODE = list.get(9);
		zAN_TEK_CODE = list.get(10);
		zAN_TEK = list.get(11);
		zAN_SIHA_CODE = list.get(12);
		zAN_KIN = new BigDecimal(list.get(13));
		zAN_SIHA_DATE = DateUtil.toYMDDate(list.get(14));
		zAN_JSK_DATE = DateUtil.toYMDDate(list.get(15));
		zAN_FURI_DATE = DateUtil.toYMDDate(list.get(16));
		zAN_SIHA_KBN = list.get(17);
		zAN_HOH_CODE = list.get(18);
		zAN_HORYU_KBN = Integer.parseInt(list.get(19));
		zAN_KESAI_KBN = Integer.parseInt(list.get(20));
		zAN_SEI_NO = list.get(21);
		zAN_DATA_KBN = list.get(22);
		zAN_NAI_CODE = list.get(23);
		zAN_FURI_TESU_KBN = Integer.parseInt(list.get(24));
		zAN_YUSO_KBN = Integer.parseInt(list.get(25));
		zAN_YUSO_TESU_KBN = Integer.parseInt(list.get(26));
		zAN_TEG_SAITO = Util.isNullOrEmpty(list.get(27)) ? null : new Integer(list.get(27));
		zAN_TEG_SIHA_KIJITU = DateUtil.toYMDDate(list.get(28));
		zAN_FURI_CBK_CODE = list.get(29);
		zAN_FURI_BNK_CODE = list.get(30);
		zAN_FURI_STN_CODE = list.get(31);
		zAN_TEG_CBK_CODE = list.get(32);
		zAN_TEG_BNK_CODE = list.get(33);
		zAN_TEG_STN_CODE = list.get(34);
		zAN_LIST_KBN = Integer.parseInt(list.get(35));
		zAN_SIHA_DEN_NO = list.get(36);
		zAN_SIHA_KAGEN_KBN = Integer.parseInt(list.get(37));
		zAN_TEG_KAGEN_KBN = Integer.parseInt(list.get(38));
		zAN_FURI_KAGEN_KBN = Integer.parseInt(list.get(39));
		zAN_INP_DATE = DateUtil.toYMDHMSDate(list.get(40));
		pRG_ID = list.get(41);
		zAN_TJK_CODE = list.get(42);
		zAN_CUR_CODE = list.get(43);
		zAN_CUR_RATE = new BigDecimal(list.get(44));
		zAN_IN_SIHA_KIN = new BigDecimal(list.get(45));
		zAN_SYS_KBN = list.get(46);
		zAN_DEN_SYU = list.get(47);
		zAN_UTK_NO = list.get(48);
		zAN_SIHA_GYO_NO = Util.isNullOrEmpty(list.get(49)) ? null : new Integer(list.get(49));
		uSR_ID = list.get(50);
		uPD_DATE = DateUtil.toYMDHMSDate(list.get(51));
	}

	public String getCBK_CUR_CODE() {
		return cBK_CUR_CODE;
	}

	public void setCBK_CUR_CODE(String cbk_cur_code) {
		cBK_CUR_CODE = cbk_cur_code;
	}
}
