package jp.co.ais.trans.master.entity;

import java.math.BigDecimal;
import java.util.*;
import java.util.Date;

/**
 * ������f�[�^
 */
public class AR_KAI_DAT extends MasterBase {

	/** �e�[�u�� */
	public static final String TABLE = "AR_KAI_DAT";

	private String kAI_CODE = "";

	private Date kAI_DEN_DATE;

	private String kAI_DEN_NO = "";

	private int kAI_GYO_NO;

	private int kAI_DC_KBN;

	private String kAI_KMK_CODE = "";

	private String kAI_HKM_CODE;

	private String kAI_UKM_CODE;

	private String kAI_DEP_CODE = "";

	private BigDecimal kAI_KIN;

	private String kAI_TRI_CODE;

	private String kAI_TRI_NAME;

	private String kAI_DATA_KBN = "";

	private Date kAI_KESI_DEN_DATE;

	private String kAI_KESI_DEN_NO;

	private Date kAI_INP_DATE;

	private String pRG_ID;

	private String kAI_GYO_TEK_CODE;

	private String kAI_GYO_TEK;

	/**
	 * @return ��ЃR�[�h
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
	 * @return ����`�[���t
	 */
	public Date getKAI_DEN_DATE() {
		return kAI_DEN_DATE;
	}

	/**
	 * @param kAI_DEN_DATE
	 */
	public void setKAI_DEN_DATE(Date kAI_DEN_DATE) {
		this.kAI_DEN_DATE = kAI_DEN_DATE;
	}

	/**
	 * @return ����`�[�ԍ�
	 */
	public String getKAI_DEN_NO() {
		return kAI_DEN_NO;
	}

	/**
	 * @param kAI_DEN_NO
	 */
	public void setKAI_DEN_NO(String kAI_DEN_NO) {
		this.kAI_DEN_NO = kAI_DEN_NO;
	}

	/**
	 * @return �`�[�s�ԍ�
	 */
	public int getKAI_GYO_NO() {
		return kAI_GYO_NO;
	}

	/**
	 * @param kAI_GYO_NO
	 */
	public void setKAI_GYO_NO(int kAI_GYO_NO) {
		this.kAI_GYO_NO = kAI_GYO_NO;
	}

	/**
	 * @return �ݎ؋敪
	 */
	public int getKAI_DC_KBN() {
		return kAI_DC_KBN;
	}

	/**
	 * @param kAI_DC_KBN
	 */
	public void setKAI_DC_KBN(int kAI_DC_KBN) {
		this.kAI_DC_KBN = kAI_DC_KBN;
	}

	/**
	 * @return �ȖڃR�[�h
	 */
	public String getKAI_KMK_CODE() {
		return kAI_KMK_CODE;
	}

	/**
	 * @param kAI_KMK_CODE
	 */
	public void setKAI_KMK_CODE(String kAI_KMK_CODE) {
		this.kAI_KMK_CODE = kAI_KMK_CODE;
	}

	/**
	 * @return �⏕�R�[�h
	 */
	public String getKAI_HKM_CODE() {
		return kAI_HKM_CODE;
	}

	/**
	 * @param kAI_HKM_CODE
	 */
	public void setKAI_HKM_CODE(String kAI_HKM_CODE) {
		this.kAI_HKM_CODE = kAI_HKM_CODE;
	}

	/**
	 * @return ����R�[�h
	 */
	public String getKAI_UKM_CODE() {
		return kAI_UKM_CODE;
	}

	/**
	 * @param kAI_UKM_CODE
	 */

	public void setKAI_UKM_CODE(String kAI_UKM_CODE) {
		this.kAI_UKM_CODE = kAI_UKM_CODE;
	}

	/**
	 * @return ����R�[�h
	 */
	public String getKAI_DEP_CODE() {
		return kAI_DEP_CODE;
	}

	/**
	 * @param kAI_DEP_CODE
	 */
	public void setKAI_DEP_CODE(String kAI_DEP_CODE) {
		this.kAI_DEP_CODE = kAI_DEP_CODE;
	}

	/**
	 * @return ������z
	 */
	public BigDecimal getKAI_KIN() {
		return kAI_KIN;
	}

	/**
	 * @param kAI_KIN
	 */
	public void setKAI_KIN(BigDecimal kAI_KIN) {
		this.kAI_KIN = kAI_KIN;
	}

	/**
	 * @return �����R�[�h
	 */
	public String getKAI_TRI_CODE() {
		return kAI_TRI_CODE;
	}

	/**
	 * @param kAI_TRI_CODE
	 */
	public void setKAI_TRI_CODE(String kAI_TRI_CODE) {
		this.kAI_TRI_CODE = kAI_TRI_CODE;
	}

	/**
	 * @return ����於��
	 */
	public String getKAI_TRI_NAME() {
		return kAI_TRI_NAME;
	}

	/**
	 * @param kAI_TRI_NAME
	 */
	public void setKAI_TRI_NAME(String kAI_TRI_NAME) {
		this.kAI_TRI_NAME = kAI_TRI_NAME;
	}

	/**
	 * @return �f�[�^�敪
	 */
	public String getKAI_DATA_KBN() {
		return kAI_DATA_KBN;
	}

	/**
	 * @param kAI_DATA_KBN
	 */
	public void setKAI_DATA_KBN(String kAI_DATA_KBN) {
		this.kAI_DATA_KBN = kAI_DATA_KBN;
	}

	/**
	 * @return �����`�[���t
	 */
	public Date getKAI_KESI_DEN_DATE() {
		return kAI_KESI_DEN_DATE;
	}

	/**
	 * @param kAI_KESI_DEN_DATE
	 */
	public void setKAI_KESI_DEN_DATE(Date kAI_KESI_DEN_DATE) {
		this.kAI_KESI_DEN_DATE = kAI_KESI_DEN_DATE;
	}

	/**
	 * @return �����`�[�ԍ�
	 */
	public String getKAI_KESI_DEN_NO() {
		return kAI_KESI_DEN_NO;
	}

	/**
	 * @param kAI_KESI_DEN_NO
	 */
	public void setKAI_KESI_DEN_NO(String kAI_KESI_DEN_NO) {
		this.kAI_KESI_DEN_NO = kAI_KESI_DEN_NO;
	}

	/**
	 * @return ���͓��t
	 */
	public Date getKAI_INP_DATE() {
		return kAI_INP_DATE;
	}

	/**
	 * @param kAI_INP_DATE
	 */
	public void setKAI_INP_DATE(Date kAI_INP_DATE) {
		this.kAI_INP_DATE = kAI_INP_DATE;
	}

	/**
	 * @return �v���O����ID
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
	 * @return �s�E�v�R�[�h
	 */
	public String getKAI_GYO_TEK_CODE() {
		return kAI_GYO_TEK_CODE;
	}

	/**
	 * @param kAI_GYO_TEK_CODE
	 */
	public void setKAI_GYO_TEK_CODE(String kAI_GYO_TEK_CODE) {
		this.kAI_GYO_TEK_CODE = kAI_GYO_TEK_CODE;
	}

	/**
	 * @return �s�E�v
	 */
	public String getKAI_GYO_TEK() {
		return kAI_GYO_TEK;
	}

	/**
	 * @param kAI_GYO_TEK
	 */
	public void setKAI_GYO_TEK(String kAI_GYO_TEK) {
		this.kAI_GYO_TEK = kAI_GYO_TEK;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/kAI_DEN_DATE=").append(kAI_DEN_DATE);
		buff.append("/kAI_DEN_NO=").append(kAI_DEN_NO);
		buff.append("/kAI_GYO_NO=").append(kAI_GYO_NO);
		buff.append("/kAI_DC_KBN=").append(kAI_DC_KBN);
		buff.append("/kAI_KMK_CODE=").append(kAI_KMK_CODE);
		buff.append("/kAI_HKM_CODE=").append(kAI_HKM_CODE);
		buff.append("/kAI_UKM_CODE=").append(kAI_UKM_CODE);
		buff.append("/kAI_DEP_CODE=").append(kAI_DEP_CODE);
		buff.append("/kAI_KIN=").append(kAI_KIN);
		buff.append("/kAI_TRI_CODE=").append(kAI_TRI_CODE);
		buff.append("/kAI_TRI_NAME=").append(kAI_TRI_NAME);
		buff.append("/kAI_DATA_KBN=").append(kAI_DATA_KBN);
		buff.append("/kAI_KESI_DEN_DATE=").append(kAI_KESI_DEN_DATE);
		buff.append("/kAI_KESI_DEN_NO=").append(kAI_KESI_DEN_NO);
		buff.append("/kAI_INP_DATE=").append(kAI_INP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/kAI_GYO_TEK_CODE=").append(kAI_GYO_TEK_CODE);
		buff.append("/kAI_GYO_TEK=").append(kAI_GYO_TEK);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(kAI_DEN_DATE);
		list.add(kAI_DEN_NO);
		list.add(kAI_GYO_NO);
		list.add(kAI_DC_KBN);
		list.add(kAI_KMK_CODE);
		list.add(kAI_HKM_CODE);
		list.add(kAI_UKM_CODE);
		list.add(kAI_DEP_CODE);
		list.add(kAI_KIN);
		list.add(kAI_TRI_CODE);
		list.add(kAI_TRI_NAME);
		list.add(kAI_DATA_KBN);
		list.add(kAI_KESI_DEN_DATE);
		list.add(kAI_KESI_DEN_NO);
		list.add(kAI_INP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(kAI_GYO_TEK_CODE);
		list.add(kAI_GYO_TEK);

		return list;
	}
}
