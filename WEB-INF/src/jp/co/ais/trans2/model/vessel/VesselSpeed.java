package jp.co.ais.trans2.model.vessel;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * VESSELスピードマスタ(CM_VESSEL_SPD_MST)
 */
public class VesselSpeed extends TransferBase implements Cloneable {

	/** クローン */
	@Override
	public VesselSpeed clone() {
		try {
			return (VesselSpeed) super.clone();
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
		sb.append("/SPD_BAL=");
		sb.append(SPD_BAL);
		sb.append("/SPD_LAD=");
		sb.append(SPD_LAD);
		return sb.toString();
	}

	/** 会社コード */
	protected String KAI_CODE = null;

	/** VESSELコード */
	protected String VESSEL_CODE = null;

	/** SPEEDタイプコード */
	protected String SPD_TYPE_CODE = null;

	/** 表示順 */
	protected int DISP_ODR = -1;

	/** BALLASTスピード */
	protected BigDecimal SPD_BAL = null;

	/** LADENスピード */
	protected BigDecimal SPD_LAD = null;

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

	/** 海上燃料消費 */
	protected List<VesselSeaCons> seaConsList = new ArrayList<VesselSeaCons>();

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
	 * 表示順の取得
	 * 
	 * @return DISP_ODR 表示順の取得
	 */
	public int getDISP_ODR() {
		return DISP_ODR;
	}

	/**
	 * 表示順の設定
	 * 
	 * @param DISP_ODR 表示順の設定
	 */
	public void setDISP_ODR(int DISP_ODR) {
		this.DISP_ODR = DISP_ODR;
	}

	/**
	 * BALLASTスピードの取得
	 * 
	 * @return SPD_BAL BALLASTスピードの取得
	 */
	public BigDecimal getSPD_BAL() {
		return SPD_BAL;
	}

	/**
	 * BALLASTスピードの設定
	 * 
	 * @param SPD_BAL BALLASTスピードの設定
	 */
	public void setSPD_BAL(BigDecimal SPD_BAL) {
		this.SPD_BAL = SPD_BAL;
	}

	/**
	 * LADENスピードの取得
	 * 
	 * @return SPD_LAD LADENスピードの取得
	 */
	public BigDecimal getSPD_LAD() {
		return SPD_LAD;
	}

	/**
	 * LADENスピードの設定
	 * 
	 * @param SPD_LAD LADENスピードの設定
	 */
	public void setSPD_LAD(BigDecimal SPD_LAD) {
		this.SPD_LAD = SPD_LAD;
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

	/**
	 * 海上燃料消費の取得
	 * 
	 * @return seaConsList 海上燃料消費
	 */
	public List<VesselSeaCons> getSeaConsList() {
		return seaConsList;
	}

	/**
	 * 海上燃料消費の設定
	 * 
	 * @param seaConsList 海上燃料消費
	 */
	public void setSeaConsList(List<VesselSeaCons> seaConsList) {
		this.seaConsList = seaConsList;
	}

	/**
	 * 海上燃料消費の挿入
	 * 
	 * @param seaConses
	 */
	public void addSeaCons(VesselSeaCons... seaConses) {
		if (this.seaConsList == null) {
			this.seaConsList = new ArrayList<VesselSeaCons>();
		}

		for (VesselSeaCons entity : seaConses) {
			this.seaConsList.add(entity);
		}
	}
}
