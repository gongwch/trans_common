package jp.co.ais.trans2.model.vessel;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * 海上燃料消費量マスタ(CM_VESSEL_SEA_CONS_MST)
 */
public class VesselSeaCons extends TransferBase implements Cloneable {

	/** クローン */
	@Override
	public VesselSeaCons clone() {
		try {
			return (VesselSeaCons) super.clone();
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
		sb.append("/SPD_TYPE_CODE=");
		sb.append(SPD_TYPE_CODE);
		sb.append("/USING_PU_KBN=");
		sb.append(USING_PU_KBN);
		sb.append("/SEA_CONS_BAL=");
		sb.append(SEA_CONS_BAL);
		sb.append("/SEA_CONS_LAD=");
		sb.append(SEA_CONS_LAD);
		return sb.toString();
	}

	/** 会社コード */
	protected String KAI_CODE = null;

	/** VESSEL_CODE */
	protected String VESSEL_CODE = null;

	/** SPEEDタイプコード */
	protected String SPD_TYPE_CODE = null;

	/** 使用目的区分 */
	protected int USING_PU_KBN = 0;

	/** 海上燃料消費量(BALLAST) */
	protected BigDecimal SEA_CONS_BAL = null;

	/** 海上燃料消費量(LADEN) */
	protected BigDecimal SEA_CONS_LAD = null;

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
	 * VESSEL_CODEの取得
	 * 
	 * @return VESSEL_CODE VESSEL_CODEの取得
	 */
	public String getVESSEL_CODE() {
		return VESSEL_CODE;
	}

	/**
	 * VESSEL_CODEの設定
	 * 
	 * @param VESSEL_CODE VESSEL_CODEの設定
	 */
	public void setVESSEL_CODE(String VESSEL_CODE) {
		this.VESSEL_CODE = VESSEL_CODE;
	}

	/**
	 * SPEEDタイプコードの取得
	 * 
	 * @return SPD_TYPE_CODE SPEEDタイプコードの取得
	 */
	public String getSPD_TYPE_CODE() {
		return SPD_TYPE_CODE;
	}

	/**
	 * SPEEDタイプコードの設定
	 * 
	 * @param SPD_TYPE_CODE SPEEDタイプコードの設定
	 */
	public void setSPD_TYPE_CODE(String SPD_TYPE_CODE) {
		this.SPD_TYPE_CODE = SPD_TYPE_CODE;
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
	 * 海上燃料消費量(BALLAST)の取得
	 * 
	 * @return SEA_CONS_BAL 海上燃料消費量(BALLAST)の取得
	 */
	public BigDecimal getSEA_CONS_BAL() {
		return SEA_CONS_BAL;
	}

	/**
	 * 海上燃料消費量(BALLAST)の設定
	 * 
	 * @param SEA_CONS_BAL 海上燃料消費量(BALLAST)の設定
	 */
	public void setSEA_CONS_BAL(BigDecimal SEA_CONS_BAL) {
		this.SEA_CONS_BAL = SEA_CONS_BAL;
	}

	/**
	 * 海上燃料消費量(LADEN)の取得
	 * 
	 * @return SEA_CONS_LAD 海上燃料消費量(LADEN)の取得
	 */
	public BigDecimal getSEA_CONS_LAD() {
		return SEA_CONS_LAD;
	}

	/**
	 * 海上燃料消費量(LADEN)の設定
	 * 
	 * @param SEA_CONS_LAD 海上燃料消費量(LADEN)の設定
	 */
	public void setSEA_CONS_LAD(BigDecimal SEA_CONS_LAD) {
		this.SEA_CONS_LAD = SEA_CONS_LAD;
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
