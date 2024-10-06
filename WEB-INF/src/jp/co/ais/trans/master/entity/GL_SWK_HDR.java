package jp.co.ais.trans.master.entity;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * GL仕訳ヘッダー
 */
public class GL_SWK_HDR extends MasterBase implements SWK_HDR, Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = 8626812311231212313L;

	/**  */
	public static final String TABLE = "GL_SWK_HDR";

	private String kAI_CODE = "";

	private Date sWK_DEN_DATE;

	private String sWK_DEN_NO = "";

	private String sWK_SEI_NO;

	private String sWK_KMK_CODE;

	private String sWK_HKM_CODE;

	private String sWK_UKM_CODE;

	private String sWK_DEP_CODE;

	private String sWK_TRI_CODE;

	private String sWK_EMP_CODE;

	private BigDecimal sWK_KIN;

	private String sWK_DATA_KBN = "";

	private String sWK_UKE_DEP_CODE;

	private String sWK_TEK_CODE;

	private String sWK_TEK = "";

	private String sWK_BEFORE_DEN_NO;

	private int sWK_UPD_KBN;

	private String sWK_SYO_EMP_CODE;

	private Date sWK_SYO_DATE;

	private String sWK_IRAI_EMP_CODE;

	private String sWK_IRAI_DEP_CODE;

	private Date sWK_IRAI_DATE;

	private int sWK_SHR_KBN;

	private int sWK_KSN_KBN;

	private String pRG_ID;

	private String sWK_CUR_CODE;

	private BigDecimal sWK_CUR_RATE;

	private BigDecimal sWK_IN_KIN;

	private String sWK_SYS_KBN;

	private String sWK_DEN_SYU;

	private Integer sWK_UPD_CNT;

	private int sWK_TUKE_KBN;

	private Date sWK_DEN_DATE_REVERSE;

	private transient List<SWK_DTL> dtails = new LinkedList<SWK_DTL>();

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
	 * @return Date
	 */
	public Date getSWK_DEN_DATE() {
		return sWK_DEN_DATE;
	}

	/**
	 * @param sWK_DEN_DATE
	 */
	public void setSWK_DEN_DATE(Date sWK_DEN_DATE) {
		this.sWK_DEN_DATE = sWK_DEN_DATE;
	}

	/**
	 * @return String
	 */
	public String getSWK_DEN_NO() {
		return sWK_DEN_NO;
	}

	/**
	 * @param sWK_DEN_NO
	 */
	public void setSWK_DEN_NO(String sWK_DEN_NO) {
		this.sWK_DEN_NO = sWK_DEN_NO;
	}

	/**
	 * @return String
	 */
	public String getSWK_SEI_NO() {
		return sWK_SEI_NO;
	}

	/**
	 * @param sWK_SEI_NO
	 */
	public void setSWK_SEI_NO(String sWK_SEI_NO) {
		this.sWK_SEI_NO = sWK_SEI_NO;
	}

	/**
	 * @return String
	 */
	public String getSWK_KMK_CODE() {
		return sWK_KMK_CODE;
	}

	/**
	 * @param sWK_KMK_CODE
	 */
	public void setSWK_KMK_CODE(String sWK_KMK_CODE) {
		this.sWK_KMK_CODE = sWK_KMK_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_HKM_CODE() {
		return sWK_HKM_CODE;
	}

	/**
	 * @param sWK_HKM_CODE
	 */
	public void setSWK_HKM_CODE(String sWK_HKM_CODE) {
		this.sWK_HKM_CODE = sWK_HKM_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_UKM_CODE() {
		return sWK_UKM_CODE;
	}

	/**
	 * @param sWK_UKM_CODE
	 */
	public void setSWK_UKM_CODE(String sWK_UKM_CODE) {
		this.sWK_UKM_CODE = sWK_UKM_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_DEP_CODE() {
		return sWK_DEP_CODE;
	}

	/**
	 * @param sWK_DEP_CODE
	 */
	public void setSWK_DEP_CODE(String sWK_DEP_CODE) {
		this.sWK_DEP_CODE = sWK_DEP_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_TRI_CODE() {
		return sWK_TRI_CODE;
	}

	/**
	 * @param sWK_TRI_CODE
	 */
	public void setSWK_TRI_CODE(String sWK_TRI_CODE) {
		this.sWK_TRI_CODE = sWK_TRI_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_EMP_CODE() {
		return sWK_EMP_CODE;
	}

	/**
	 * @param sWK_EMP_CODE
	 */
	public void setSWK_EMP_CODE(String sWK_EMP_CODE) {
		this.sWK_EMP_CODE = sWK_EMP_CODE;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSWK_KIN() {
		return sWK_KIN;
	}

	/**
	 * @param sWK_KIN
	 */
	public void setSWK_KIN(BigDecimal sWK_KIN) {
		this.sWK_KIN = sWK_KIN;
	}

	/**
	 * @return String
	 */
	public String getSWK_DATA_KBN() {
		return sWK_DATA_KBN;
	}

	/**
	 * @param sWK_DATA_KBN
	 */
	public void setSWK_DATA_KBN(String sWK_DATA_KBN) {
		this.sWK_DATA_KBN = sWK_DATA_KBN;
	}

	/**
	 * @return String
	 */
	public String getSWK_UKE_DEP_CODE() {
		return sWK_UKE_DEP_CODE;
	}

	/**
	 * @param sWK_UKE_DEP_CODE
	 */
	public void setSWK_UKE_DEP_CODE(String sWK_UKE_DEP_CODE) {
		this.sWK_UKE_DEP_CODE = sWK_UKE_DEP_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_TEK_CODE() {
		return sWK_TEK_CODE;
	}

	/**
	 * @param sWK_TEK_CODE
	 */
	public void setSWK_TEK_CODE(String sWK_TEK_CODE) {
		this.sWK_TEK_CODE = sWK_TEK_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_TEK() {
		return sWK_TEK;
	}

	/**
	 * @param sWK_TEK
	 */
	public void setSWK_TEK(String sWK_TEK) {
		this.sWK_TEK = sWK_TEK;
	}

	/**
	 * @return String
	 */
	public String getSWK_BEFORE_DEN_NO() {
		return sWK_BEFORE_DEN_NO;
	}

	/**
	 * @param sWK_BEFORE_DEN_NO
	 */
	public void setSWK_BEFORE_DEN_NO(String sWK_BEFORE_DEN_NO) {
		this.sWK_BEFORE_DEN_NO = sWK_BEFORE_DEN_NO;
	}

	/**
	 * @return int
	 */
	public int getSWK_UPD_KBN() {
		return sWK_UPD_KBN;
	}

	/**
	 * @param sWK_UPD_KBN
	 */
	public void setSWK_UPD_KBN(int sWK_UPD_KBN) {
		this.sWK_UPD_KBN = sWK_UPD_KBN;
	}

	/**
	 * @return String
	 */
	public String getSWK_SYO_EMP_CODE() {
		return sWK_SYO_EMP_CODE;
	}

	/**
	 * @param sWK_SYO_EMP_CODE
	 */
	public void setSWK_SYO_EMP_CODE(String sWK_SYO_EMP_CODE) {
		this.sWK_SYO_EMP_CODE = sWK_SYO_EMP_CODE;
	}

	/**
	 * @return Date
	 */
	public Date getSWK_SYO_DATE() {
		return sWK_SYO_DATE;
	}

	/**
	 * @param sWK_SYO_DATE
	 */
	public void setSWK_SYO_DATE(Date sWK_SYO_DATE) {
		this.sWK_SYO_DATE = sWK_SYO_DATE;
	}

	/**
	 * @return String
	 */
	public String getSWK_IRAI_EMP_CODE() {
		return sWK_IRAI_EMP_CODE;
	}

	/**
	 * @param sWK_IRAI_EMP_CODE
	 */
	public void setSWK_IRAI_EMP_CODE(String sWK_IRAI_EMP_CODE) {
		this.sWK_IRAI_EMP_CODE = sWK_IRAI_EMP_CODE;
	}

	/**
	 * @return String
	 */
	public String getSWK_IRAI_DEP_CODE() {
		return sWK_IRAI_DEP_CODE;
	}

	/**
	 * @param sWK_IRAI_DEP_CODE
	 */
	public void setSWK_IRAI_DEP_CODE(String sWK_IRAI_DEP_CODE) {
		this.sWK_IRAI_DEP_CODE = sWK_IRAI_DEP_CODE;
	}

	/**
	 * @return Date
	 */
	public Date getSWK_IRAI_DATE() {
		return sWK_IRAI_DATE;
	}

	/**
	 * @param sWK_IRAI_DATE
	 */
	public void setSWK_IRAI_DATE(Date sWK_IRAI_DATE) {
		this.sWK_IRAI_DATE = sWK_IRAI_DATE;
	}

	/**
	 * @return int
	 */
	public int getSWK_SHR_KBN() {
		return sWK_SHR_KBN;
	}

	/**
	 * @param sWK_SHR_KBN
	 */
	public void setSWK_SHR_KBN(int sWK_SHR_KBN) {
		this.sWK_SHR_KBN = sWK_SHR_KBN;
	}

	/**
	 * @return int
	 */
	public int getSWK_KSN_KBN() {
		return sWK_KSN_KBN;
	}

	/**
	 * @param sWK_KSN_KBN
	 */
	public void setSWK_KSN_KBN(int sWK_KSN_KBN) {
		this.sWK_KSN_KBN = sWK_KSN_KBN;
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
	public String getSWK_CUR_CODE() {
		return sWK_CUR_CODE;
	}

	/**
	 * @param sWK_CUR_CODE
	 */
	public void setSWK_CUR_CODE(String sWK_CUR_CODE) {
		this.sWK_CUR_CODE = sWK_CUR_CODE;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSWK_CUR_RATE() {
		return sWK_CUR_RATE;
	}

	/**
	 * @param sWK_CUR_RATE
	 */
	public void setSWK_CUR_RATE(BigDecimal sWK_CUR_RATE) {
		this.sWK_CUR_RATE = sWK_CUR_RATE;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSWK_IN_KIN() {
		return sWK_IN_KIN;
	}

	/**
	 * @param sWK_IN_KIN
	 */
	public void setSWK_IN_KIN(BigDecimal sWK_IN_KIN) {
		this.sWK_IN_KIN = sWK_IN_KIN;
	}

	/**
	 * @return String
	 */
	public String getSWK_SYS_KBN() {
		return sWK_SYS_KBN;
	}

	/**
	 * @param sWK_SYS_KBN
	 */
	public void setSWK_SYS_KBN(String sWK_SYS_KBN) {
		this.sWK_SYS_KBN = sWK_SYS_KBN;
	}

	/**
	 * @return String
	 */
	public String getSWK_DEN_SYU() {
		return sWK_DEN_SYU;
	}

	/**
	 * @param sWK_DEN_SYU
	 */
	public void setSWK_DEN_SYU(String sWK_DEN_SYU) {
		this.sWK_DEN_SYU = sWK_DEN_SYU;
	}

	/**
	 * @return Integer
	 */
	public Integer getSWK_UPD_CNT() {
		return sWK_UPD_CNT;
	}

	/**
	 * @param sWK_UPD_CNT
	 */
	public void setSWK_UPD_CNT(Integer sWK_UPD_CNT) {
		this.sWK_UPD_CNT = sWK_UPD_CNT;
	}

	/**
	 * @return int
	 */
	public int getSWK_TUKE_KBN() {
		return sWK_TUKE_KBN;
	}

	/**
	 * @param sWK_TUKE_KBN
	 */
	public void setSWK_TUKE_KBN(int sWK_TUKE_KBN) {
		this.sWK_TUKE_KBN = sWK_TUKE_KBN;
	}

	/**
	 * @return Date
	 */
	public Date getSWK_DEN_DATE_REVERSE() {
		return sWK_DEN_DATE_REVERSE;
	}

	/**
	 * @param sWK_DEN_DATE_REVERSE
	 */
	public void setSWK_DEN_DATE_REVERSE(Date sWK_DEN_DATE_REVERSE) {
		this.sWK_DEN_DATE_REVERSE = sWK_DEN_DATE_REVERSE;
	}

	/**
	 * 明細(仕訳)の追加
	 * 
	 * @param swkDtl 対象仕訳
	 */
	public void addDetail(SWK_DTL... swkDtl) {
		for (SWK_DTL dtl : swkDtl) {
			dtails.add(dtl);
		}
	}

	/**
	 * 明細(仕訳)の削除
	 * 
	 * @param swkDtl 対象仕訳
	 */
	public void removeDetail(SWK_DTL swkDtl) {
		dtails.remove(swkDtl);
	}

	/**
	 * 明細(仕訳)の取得
	 * 
	 * @return 明細(仕訳)
	 */
	public List<SWK_DTL> getDetails() {
		return dtails;
	}

	/**
	 * @see jp.co.ais.trans.common.dt.TransferBase#toObjectArray()
	 */
	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(sWK_DEN_DATE);
		list.add(sWK_DEN_NO);
		list.add(sWK_SEI_NO);
		list.add(sWK_KMK_CODE);
		list.add(sWK_HKM_CODE);
		list.add(sWK_UKM_CODE);
		list.add(sWK_DEP_CODE);
		list.add(sWK_TRI_CODE);
		list.add(sWK_EMP_CODE);
		list.add(sWK_KIN);
		list.add(sWK_DATA_KBN);
		list.add(sWK_UKE_DEP_CODE);
		list.add(sWK_TEK_CODE);
		list.add(sWK_TEK);
		list.add(sWK_BEFORE_DEN_NO);
		list.add(sWK_UPD_KBN);
		list.add(sWK_SYO_EMP_CODE);
		list.add(sWK_SYO_DATE);
		list.add(sWK_IRAI_EMP_CODE);
		list.add(sWK_IRAI_DEP_CODE);
		list.add(sWK_IRAI_DATE);
		list.add(sWK_SHR_KBN);
		list.add(sWK_KSN_KBN);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);
		list.add(sWK_CUR_CODE);
		list.add(sWK_CUR_RATE);
		list.add(sWK_IN_KIN);
		list.add(sWK_SYS_KBN);
		list.add(sWK_DEN_SYU);
		list.add(sWK_UPD_CNT);
		list.add(sWK_TUKE_KBN);

		return list;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public GL_SWK_HDR clone() {
		try {
			return (GL_SWK_HDR) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}
}
