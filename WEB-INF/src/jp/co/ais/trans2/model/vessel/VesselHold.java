package jp.co.ais.trans2.model.vessel;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * Vessel hold master
 * 
 * @author Ngoc Nguyen
 */
public class VesselHold extends TransferBase implements Cloneable {

	/**
	 * クローン.
	 * 
	 * @return the OP vessel hold
	 */
	@Override
	public VesselHold clone() {
		try {
			return (VesselHold) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return return a string
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("KAI_CODE=");
		sb.append(KAI_CODE);
		sb.append("/VESSEL_CODE=");
		sb.append(VESSEL_CODE);
		sb.append("/HOLD_NO=");
		sb.append(HOLD_NO);
		return sb.toString();
	}

	/** 会社コード. */
	protected String KAI_CODE = null;

	/** VESSELコード. */
	protected String VESSEL_CODE = null;

	/** HOLD NO. */
	protected String HOLD_NO = null;

	/** 表示順. */
	protected int DISP_ODR = -1;

	/** HOLD NAME. */
	protected String HOLD_NAME = null;

	/** CENTER GRN. */
	protected BigDecimal CENTER_GRN = null;

	/** CENTER BALE. */
	protected BigDecimal CENTER_BALE = null;

	/** LEN BRE. */
	protected String LEN_BRE;

	/** TYPE SET. */
	protected String TYPE_SET;

	/** 開始年月日. */
	protected Date STR_DATE = null;

	/** 終了年月日. */
	protected Date END_DATE = null;

	/** 登録年月日. */
	protected Date INP_DATE = null;

	/** 更新年月日. */
	protected Date UPD_DATE = null;

	/** プログラムID. */
	protected String PRG_ID = null;

	/** ユーザーID. */
	protected String USR_ID = null;

	/**
	 * 会社コードの取得.
	 * 
	 * @return KAI_CODE 会社コードの取得
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * 会社コードの設定.
	 * 
	 * @param KAI_CODE 会社コードの設定
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * VESSELコードの取得.
	 * 
	 * @return VESSEL_CODE VESSELコードの取得
	 */
	public String getVESSEL_CODE() {
		return VESSEL_CODE;
	}

	/**
	 * VESSELコードの設定.
	 * 
	 * @param VESSEL_CODE VESSELコードの設定
	 */
	public void setVESSEL_CODE(String VESSEL_CODE) {
		this.VESSEL_CODE = VESSEL_CODE;
	}

	/**
	 * 表示順の取得.
	 * 
	 * @return DISP_ODR 表示順の取得
	 */
	public int getDISP_ODR() {
		return DISP_ODR;
	}

	/**
	 * 表示順の設定.
	 * 
	 * @param DISP_ODR 表示順の設定
	 */
	public void setDISP_ODR(int DISP_ODR) {
		this.DISP_ODR = DISP_ODR;
	}

	/**
	 * Gets the hold no.
	 * 
	 * @return the hold no
	 */
	public String getHOLD_NO() {
		return HOLD_NO;
	}

	/**
	 * Sets the hold no.
	 * 
	 * @param hOLD_NO the new hold no
	 */
	public void setHOLD_NO(String hOLD_NO) {
		HOLD_NO = hOLD_NO;
	}

	/**
	 * Gets the hold name.
	 * 
	 * @return the hold name
	 */
	public String getHOLD_NAME() {
		return HOLD_NAME;
	}

	/**
	 * Sets the hold name.
	 * 
	 * @param hOLD_NAME the new hold name
	 */
	public void setHOLD_NAME(String hOLD_NAME) {
		HOLD_NAME = hOLD_NAME;
	}

	/**
	 * Gets the center grn.
	 * 
	 * @return the center grn
	 */
	public BigDecimal getCENTER_GRN() {
		return CENTER_GRN;
	}

	/**
	 * Sets the center grn.
	 * 
	 * @param cENTER_GRN the new center grn
	 */
	public void setCENTER_GRN(BigDecimal cENTER_GRN) {
		CENTER_GRN = cENTER_GRN;
	}

	/**
	 * Gets the center bale.
	 * 
	 * @return the center bale
	 */
	public BigDecimal getCENTER_BALE() {
		return CENTER_BALE;
	}

	/**
	 * Sets the center bale.
	 * 
	 * @param cENTER_BALE the new center bale
	 */
	public void setCENTER_BALE(BigDecimal cENTER_BALE) {
		CENTER_BALE = cENTER_BALE;
	}

	/**
	 * Gets the len bre.
	 * 
	 * @return the len bre
	 */
	public String getLEN_BRE() {
		return LEN_BRE;
	}

	/**
	 * Sets the len bre.
	 * 
	 * @param lEN_BRE the new len bre
	 */
	public void setLEN_BRE(String lEN_BRE) {
		LEN_BRE = lEN_BRE;
	}

	/**
	 * Gets the type set.
	 * 
	 * @return the type set
	 */
	public String getTYPE_SET() {
		return TYPE_SET;
	}

	/**
	 * Sets the type set.
	 * 
	 * @param tYPE_SET the new type set
	 */
	public void setTYPE_SET(String tYPE_SET) {
		TYPE_SET = tYPE_SET;
	}

	/**
	 * 開始年月日の取得.
	 * 
	 * @return STR_DATE 開始年月日の取得
	 */
	public Date getSTR_DATE() {
		return STR_DATE;
	}

	/**
	 * 開始年月日の設定.
	 * 
	 * @param STR_DATE 開始年月日の設定
	 */
	public void setSTR_DATE(Date STR_DATE) {
		this.STR_DATE = STR_DATE;
	}

	/**
	 * 終了年月日の取得.
	 * 
	 * @return END_DATE 終了年月日の取得
	 */
	public Date getEND_DATE() {
		return END_DATE;
	}

	/**
	 * 終了年月日の設定.
	 * 
	 * @param END_DATE 終了年月日の設定
	 */
	public void setEND_DATE(Date END_DATE) {
		this.END_DATE = END_DATE;
	}

	/**
	 * 登録年月日の取得.
	 * 
	 * @return INP_DATE 登録年月日の取得
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * 登録年月日の設定.
	 * 
	 * @param INP_DATE 登録年月日の設定
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * 更新年月日の取得.
	 * 
	 * @return UPD_DATE 更新年月日の取得
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * 更新年月日の設定.
	 * 
	 * @param UPD_DATE 更新年月日の設定
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * プログラムIDの取得.
	 * 
	 * @return PRG_ID プログラムIDの取得
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * プログラムIDの設定.
	 * 
	 * @param PRG_ID プログラムIDの設定
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * ユーザーIDの取得.
	 * 
	 * @return USR_ID ユーザーIDの取得
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ユーザーIDの設定.
	 * 
	 * @param USR_ID ユーザーIDの設定
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}
}
