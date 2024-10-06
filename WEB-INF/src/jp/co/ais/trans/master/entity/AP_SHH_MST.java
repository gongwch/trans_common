package jp.co.ais.trans.master.entity;

import java.math.BigDecimal;
import java.util.*;
import java.util.Date;

public class AP_SHH_MST extends MasterBase {
	public static final String TABLE = "AP_SHH_MST";

	public static final String kAI_CODE_ID = "sequence, sequenceName=kAI_CODE";

	private String kAI_CODE = "";

	private int sHH_SIHA_1;

	private int sHH_SIHA_5;

	private int sHH_SIHA_10;

	private int sHH_SIHA_15;

	private int sHH_SIHA_20;

	private int sHH_SIHA_25;

	private int sHH_SIHA_30;

	private int sHH_BNK_KBN_1;

	private int sHH_BNK_KBN_5;

	private int sHH_BNK_KBN_10;

	private int sHH_BNK_KBN_15;

	private int sHH_BNK_KBN_20;

	private int sHH_BNK_KBN_25;

	private int sHH_BNK_KBN_30;

	private BigDecimal sHH_SIHA_MIN;

	private BigDecimal sHH_FURI_MIN;

	private String sHH_TESU_KMK_CODE;

	private String sHH_TESU_HKM_CODE;

	private String sHH_TESU_UKM_CODE;

	private String sHH_TESU_DEP_CODE;

	private String sHH_TESU_ZEI_CODE;

	private String sHH_GS_PRINT_KBN;

	private String sHH_SEI_NO_KBN;

	private Date sHH_INP_DATE;

	private String pRG_ID;


	public String getKAI_CODE() {
		return kAI_CODE;
	}

	public void setKAI_CODE(String kAI_CODE) {
		this.kAI_CODE = kAI_CODE;
	}

	public int getSHH_SIHA_1() {
		return sHH_SIHA_1;
	}

	public void setSHH_SIHA_1(int sHH_SIHA_1) {
		this.sHH_SIHA_1 = sHH_SIHA_1;
	}

	public int getSHH_SIHA_5() {
		return sHH_SIHA_5;
	}

	public void setSHH_SIHA_5(int sHH_SIHA_5) {
		this.sHH_SIHA_5 = sHH_SIHA_5;
	}

	public int getSHH_SIHA_10() {
		return sHH_SIHA_10;
	}

	public void setSHH_SIHA_10(int sHH_SIHA_10) {
		this.sHH_SIHA_10 = sHH_SIHA_10;
	}

	public int getSHH_SIHA_15() {
		return sHH_SIHA_15;
	}

	public void setSHH_SIHA_15(int sHH_SIHA_15) {
		this.sHH_SIHA_15 = sHH_SIHA_15;
	}

	public int getSHH_SIHA_20() {
		return sHH_SIHA_20;
	}

	public void setSHH_SIHA_20(int sHH_SIHA_20) {
		this.sHH_SIHA_20 = sHH_SIHA_20;
	}

	public int getSHH_SIHA_25() {
		return sHH_SIHA_25;
	}

	public void setSHH_SIHA_25(int sHH_SIHA_25) {
		this.sHH_SIHA_25 = sHH_SIHA_25;
	}

	public int getSHH_SIHA_30() {
		return sHH_SIHA_30;
	}

	public void setSHH_SIHA_30(int sHH_SIHA_30) {
		this.sHH_SIHA_30 = sHH_SIHA_30;
	}

	public int getSHH_BNK_KBN_1() {
		return sHH_BNK_KBN_1;
	}

	public void setSHH_BNK_KBN_1(int sHH_BNK_KBN_1) {
		this.sHH_BNK_KBN_1 = sHH_BNK_KBN_1;
	}

	public int getSHH_BNK_KBN_5() {
		return sHH_BNK_KBN_5;
	}

	public void setSHH_BNK_KBN_5(int sHH_BNK_KBN_5) {
		this.sHH_BNK_KBN_5 = sHH_BNK_KBN_5;
	}

	public int getSHH_BNK_KBN_10() {
		return sHH_BNK_KBN_10;
	}

	public void setSHH_BNK_KBN_10(int sHH_BNK_KBN_10) {
		this.sHH_BNK_KBN_10 = sHH_BNK_KBN_10;
	}

	public int getSHH_BNK_KBN_15() {
		return sHH_BNK_KBN_15;
	}

	public void setSHH_BNK_KBN_15(int sHH_BNK_KBN_15) {
		this.sHH_BNK_KBN_15 = sHH_BNK_KBN_15;
	}

	public int getSHH_BNK_KBN_20() {
		return sHH_BNK_KBN_20;
	}

	public void setSHH_BNK_KBN_20(int sHH_BNK_KBN_20) {
		this.sHH_BNK_KBN_20 = sHH_BNK_KBN_20;
	}

	public int getSHH_BNK_KBN_25() {
		return sHH_BNK_KBN_25;
	}

	public void setSHH_BNK_KBN_25(int sHH_BNK_KBN_25) {
		this.sHH_BNK_KBN_25 = sHH_BNK_KBN_25;
	}

	public int getSHH_BNK_KBN_30() {
		return sHH_BNK_KBN_30;
	}

	public void setSHH_BNK_KBN_30(int sHH_BNK_KBN_30) {
		this.sHH_BNK_KBN_30 = sHH_BNK_KBN_30;
	}

	public BigDecimal getSHH_SIHA_MIN() {
		return sHH_SIHA_MIN;
	}

	public void setSHH_SIHA_MIN(BigDecimal sHH_SIHA_MIN) {
		this.sHH_SIHA_MIN = sHH_SIHA_MIN;
	}

	public BigDecimal getSHH_FURI_MIN() {
		return sHH_FURI_MIN;
	}

	public void setSHH_FURI_MIN(BigDecimal sHH_FURI_MIN) {
		this.sHH_FURI_MIN = sHH_FURI_MIN;
	}

	public String getSHH_TESU_KMK_CODE() {
		return sHH_TESU_KMK_CODE;
	}

	public void setSHH_TESU_KMK_CODE(String sHH_TESU_KMK_CODE) {
		this.sHH_TESU_KMK_CODE = sHH_TESU_KMK_CODE;
	}

	public String getSHH_TESU_HKM_CODE() {
		return sHH_TESU_HKM_CODE;
	}

	public void setSHH_TESU_HKM_CODE(String sHH_TESU_HKM_CODE) {
		this.sHH_TESU_HKM_CODE = sHH_TESU_HKM_CODE;
	}

	public String getSHH_TESU_UKM_CODE() {
		return sHH_TESU_UKM_CODE;
	}

	public void setSHH_TESU_UKM_CODE(String sHH_TESU_UKM_CODE) {
		this.sHH_TESU_UKM_CODE = sHH_TESU_UKM_CODE;
	}

	public String getSHH_TESU_DEP_CODE() {
		return sHH_TESU_DEP_CODE;
	}

	public void setSHH_TESU_DEP_CODE(String sHH_TESU_DEP_CODE) {
		this.sHH_TESU_DEP_CODE = sHH_TESU_DEP_CODE;
	}

	public String getSHH_TESU_ZEI_CODE() {
		return sHH_TESU_ZEI_CODE;
	}

	public void setSHH_TESU_ZEI_CODE(String sHH_TESU_ZEI_CODE) {
		this.sHH_TESU_ZEI_CODE = sHH_TESU_ZEI_CODE;
	}

	public String getSHH_GS_PRINT_KBN() {
		return sHH_GS_PRINT_KBN;
	}

	public void setSHH_GS_PRINT_KBN(String sHH_GS_PRINT_KBN) {
		this.sHH_GS_PRINT_KBN = sHH_GS_PRINT_KBN;
	}

	public String getSHH_SEI_NO_KBN() {
		return sHH_SEI_NO_KBN;
	}

	public void setSHH_SEI_NO_KBN(String sHH_SEI_NO_KBN) {
		this.sHH_SEI_NO_KBN = sHH_SEI_NO_KBN;
	}

	public Date getSHH_INP_DATE() {
		return sHH_INP_DATE;
	}

	public void setSHH_INP_DATE(Date sHH_INP_DATE) {
		this.sHH_INP_DATE = sHH_INP_DATE;
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
		buff.append("/sHH_SIHA_1=").append(sHH_SIHA_1);
		buff.append("/sHH_SIHA_5=").append(sHH_SIHA_5);
		buff.append("/sHH_SIHA_10=").append(sHH_SIHA_10);
		buff.append("/sHH_SIHA_15=").append(sHH_SIHA_15);
		buff.append("/sHH_SIHA_20=").append(sHH_SIHA_20);
		buff.append("/sHH_SIHA_25=").append(sHH_SIHA_25);
		buff.append("/sHH_SIHA_30=").append(sHH_SIHA_30);
		buff.append("/sHH_BNK_KBN_1=").append(sHH_BNK_KBN_1);
		buff.append("/sHH_BNK_KBN_5=").append(sHH_BNK_KBN_5);
		buff.append("/sHH_BNK_KBN_10=").append(sHH_BNK_KBN_10);
		buff.append("/sHH_BNK_KBN_15=").append(sHH_BNK_KBN_15);
		buff.append("/sHH_BNK_KBN_20=").append(sHH_BNK_KBN_20);
		buff.append("/sHH_BNK_KBN_25=").append(sHH_BNK_KBN_25);
		buff.append("/sHH_BNK_KBN_30=").append(sHH_BNK_KBN_30);
		buff.append("/sHH_SIHA_MIN=").append(sHH_SIHA_MIN);
		buff.append("/sHH_FURI_MIN=").append(sHH_FURI_MIN);
		buff.append("/sHH_TESU_KMK_CODE=").append(sHH_TESU_KMK_CODE);
		buff.append("/sHH_TESU_HKM_CODE=").append(sHH_TESU_HKM_CODE);
		buff.append("/sHH_TESU_UKM_CODE=").append(sHH_TESU_UKM_CODE);
		buff.append("/sHH_TESU_DEP_CODE=").append(sHH_TESU_DEP_CODE);
		buff.append("/sHH_TESU_ZEI_CODE=").append(sHH_TESU_ZEI_CODE);
		buff.append("/sHH_GS_PRINT_KBN=").append(sHH_GS_PRINT_KBN);
		buff.append("/sHH_SEI_NO_KBN=").append(sHH_SEI_NO_KBN);
		buff.append("/sHH_INP_DATE=").append(sHH_INP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(sHH_SIHA_1);
		list.add(sHH_SIHA_5);
		list.add(sHH_SIHA_10);
		list.add(sHH_SIHA_15);
		list.add(sHH_SIHA_20);
		list.add(sHH_SIHA_25);
		list.add(sHH_SIHA_30);
		list.add(sHH_BNK_KBN_1);
		list.add(sHH_BNK_KBN_5);
		list.add(sHH_BNK_KBN_10);
		list.add(sHH_BNK_KBN_15);
		list.add(sHH_BNK_KBN_20);
		list.add(sHH_BNK_KBN_25);
		list.add(sHH_BNK_KBN_30);
		list.add(sHH_SIHA_MIN);
		list.add(sHH_FURI_MIN);
		list.add(sHH_TESU_KMK_CODE);
		list.add(sHH_TESU_HKM_CODE);
		list.add(sHH_TESU_UKM_CODE);
		list.add(sHH_TESU_DEP_CODE);
		list.add(sHH_TESU_ZEI_CODE);
		list.add(sHH_GS_PRINT_KBN);
		list.add(sHH_SEI_NO_KBN);
		list.add(sHH_INP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
