package jp.co.ais.trans2.model.vessel;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * PORT燃料消費量マスタ(CM_VESSEL_PORT_CONS_MST)
 */
public class VesselPortCons extends TransferBase implements Cloneable {

	/** クローン */
	@Override
	public VesselPortCons clone() {
		try {
			return (VesselPortCons) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("KAI_CODE=");
		sb.append(KAI_CODE);
		sb.append("/VESSEL_CODE=");
		sb.append(VESSEL_CODE);
		sb.append("/USING_PU_KBN=");
		sb.append(USING_PU_KBN);
		sb.append("/PORT_LD_CONS=");
		sb.append(PORT_LD_CONS);
		sb.append("/PORT_DISC_CONS=");
		sb.append(PORT_DISC_CONS);
		sb.append("/PORT_IDLE_CONS=");
		sb.append(PORT_IDLE_CONS);
		return sb.toString();
	}

	/** 会社コード */
	protected String KAI_CODE = null;

	/** VESSELコード */
	protected String VESSEL_CODE = null;

	/** 使用目的区分 */
	protected int USING_PU_KBN = 0;

	/** LOADING消費量 */
	protected BigDecimal PORT_LD_CONS = null;

	/** DISCHARGING消費量 */
	protected BigDecimal PORT_DISC_CONS = null;

	/** IDLE消費量 */
	protected BigDecimal PORT_IDLE_CONS = null;

	/** 開始年月日 */
	protected Date STR_DATE = null;

	/** 終了年月日 */
	protected Date END_DATE = null;

	/** 登録年月日 */
	protected Date INP_DATE = null;

	/** 更新年月日 */
	protected Date UPD_DATE = null;

	/** プログラムID */
	protected String PRG_ID = null;

	/** ユーザーID */
	protected String USR_ID = null;

	/**
	 * 会社コードの取得
	 * 
	 * @return KAI_CODE 会社コードの取得
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param KAI_CODE 会社コードの設定
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * VESSELコードの取得
	 * 
	 * @return VESSEL_CODE VESSELコードの取得
	 */
	public String getVESSEL_CODE() {
		return VESSEL_CODE;
	}

	/**
	 * VESSELコードの設定
	 * 
	 * @param VESSEL_CODE VESSELコードの設定
	 */
	public void setVESSEL_CODE(String VESSEL_CODE) {
		this.VESSEL_CODE = VESSEL_CODE;
	}

	/**
	 * 使用目的区分の取得
	 * 
	 * @return USING_PU_KBN 使用目的区分
	 */
	public int getUSING_PU_KBN() {
		return USING_PU_KBN;
	}

	/**
	 * 使用目的区分の設定
	 * 
	 * @param USING_PU_KBN 使用目的区分
	 */
	public void setUSING_PU_KBN(int USING_PU_KBN) {
		this.USING_PU_KBN = USING_PU_KBN;
	}

	/**
	 * LOADING消費量の取得
	 * 
	 * @return PORT_LD_CONS LOADING消費量の取得
	 */
	public BigDecimal getPORT_LD_CONS() {
		return PORT_LD_CONS;
	}

	/**
	 * LOADING消費量の設定
	 * 
	 * @param PORT_LD_CONS LOADING消費量の設定
	 */
	public void setPORT_LD_CONS(BigDecimal PORT_LD_CONS) {
		this.PORT_LD_CONS = PORT_LD_CONS;
	}

	/**
	 * DISCHARGING消費量の取得
	 * 
	 * @return PORT_DISC_CONS DISCHARGING消費量の取得
	 */
	public BigDecimal getPORT_DISC_CONS() {
		return PORT_DISC_CONS;
	}

	/**
	 * DISCHARGING消費量の設定
	 * 
	 * @param PORT_DISC_CONS DISCHARGING消費量の設定
	 */
	public void setPORT_DISC_CONS(BigDecimal PORT_DISC_CONS) {
		this.PORT_DISC_CONS = PORT_DISC_CONS;
	}

	/**
	 * IDLE消費量の取得
	 * 
	 * @return PORT_IDLE_CONS IDLE消費量の取得
	 */
	public BigDecimal getPORT_IDLE_CONS() {
		return PORT_IDLE_CONS;
	}

	/**
	 * IDLE消費量の設定
	 * 
	 * @param PORT_IDLE_CONS IDLE消費量の設定
	 */
	public void setPORT_IDLE_CONS(BigDecimal PORT_IDLE_CONS) {
		this.PORT_IDLE_CONS = PORT_IDLE_CONS;
	}

	/**
	 * 開始年月日の取得
	 * 
	 * @return STR_DATE 開始年月日の取得
	 */
	public Date getSTR_DATE() {
		return STR_DATE;
	}

	/**
	 * 開始年月日の設定
	 * 
	 * @param STR_DATE 開始年月日の設定
	 */
	public void setSTR_DATE(Date STR_DATE) {
		this.STR_DATE = STR_DATE;
	}

	/**
	 * 終了年月日の取得
	 * 
	 * @return END_DATE 終了年月日の取得
	 */
	public Date getEND_DATE() {
		return END_DATE;
	}

	/**
	 * 終了年月日の設定
	 * 
	 * @param END_DATE 終了年月日の設定
	 */
	public void setEND_DATE(Date END_DATE) {
		this.END_DATE = END_DATE;
	}

	/**
	 * 登録年月日の取得
	 * 
	 * @return INP_DATE 登録年月日の取得
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * 登録年月日の設定
	 * 
	 * @param INP_DATE 登録年月日の設定
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * 更新年月日の取得
	 * 
	 * @return UPD_DATE 更新年月日の取得
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * 更新年月日の設定
	 * 
	 * @param UPD_DATE 更新年月日の設定
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * プログラムIDの取得
	 * 
	 * @return PRG_ID プログラムIDの取得
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * プログラムIDの設定
	 * 
	 * @param PRG_ID プログラムIDの設定
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * ユーザーIDの取得
	 * 
	 * @return USR_ID ユーザーIDの取得
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ユーザーIDの設定
	 * 
	 * @param USR_ID ユーザーIDの設定
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

}
