package jp.co.ais.trans2.model.bunkertype;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.gui.ac.*;

/**
 * OP_BNKR_TYPE_MST
 */
public class CM_BNKR_TYPE_MST extends TransferBase implements Cloneable, AutoCompletable {

	/**
	 * クローン
	 */
	@Override
	public CM_BNKR_TYPE_MST clone() {
		try {
			CM_BNKR_TYPE_MST bean = (CM_BNKR_TYPE_MST) super.clone();

			return bean;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return インクリメントサーチ表示値
	 */
	public String getDisplayText() {
		return getBNKR_TYPE_CODE();
	}

	/**
	 * @return コード
	 */
	public String getCode() {
		return getBNKR_TYPE_CODE();
	}

	/**
	 * @return 名称
	 */
	public String getName() {
		return getBNKR_TYPE_CODE();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("KAI_CODE=");
		sb.append(KAI_CODE);
		sb.append("/BNKR_TYPE_CODE=");
		sb.append(BNKR_TYPE_CODE);
		sb.append("/ENG_PRI_NOR=");
		sb.append(ENG_PRI_NOR);
		sb.append("/AUX_PRI_NOR=");
		sb.append(AUX_PRI_NOR);
		sb.append("/ENG_PRI_ECA=");
		sb.append(ENG_PRI_ECA);
		sb.append("/AUX_PRI_ECA=");
		sb.append(AUX_PRI_ECA);
		sb.append("/lclDEC=");
		sb.append(LCL_DEC_KETA);
		return sb.toString();
	}

	/** 会社コード */
	protected String KAI_CODE = null;

	/** BUNKERタイプコード */
	protected String BNKR_TYPE_CODE = null;

	/** Keep BUNKERタイプコード */
	protected String KEEP_BNKR_TYPE_CODE = null;

	/** GRADE_CODE */
	protected String GRADE_CODE = null;

	/** GRADE名称 */
	protected String GRADE_NAME = null;

	/** ORDER */
	protected int DISP_ODR = 0;

	/** MAIN_ENGINE使用順 */
	protected int ENG_PRI_NOR = -1;

	/** AUX使用順 */
	protected int AUX_PRI_NOR = -1;

	/** S_ECA_MAIN_ENGINE使用順 */
	protected int ENG_PRI_ECA = -1;

	/** S_ECA_AUX使用順 */
	protected int AUX_PRI_ECA = -1;

	/** SCRUBBER_MAIN_ENGINE使用順 */
	protected int SC_ENG_PRI_NOR = -1;

	/** SCRUBBER_AUX使用順 */
	protected int SC_AUX_PRI_NOR = -1;

	/** SCRUBBER_S_ECA_MAIN_ENGINE使用順 */
	protected int SC_ENG_PRI_ECA = -1;

	/** SCRUBBER_S_ECA_AUX使用順 */
	protected int SC_AUX_PRI_ECA = -1;

	/** (内航)小数点以下桁数 */
	protected int LCL_DEC_KETA = 0;

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

	/** 0:未使用、1:使用する */
	protected int USE_FLG = 1;

	/**
	 * 会社コードの取得
	 * 
	 * @return KAI_CODE 会社コード
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param KAI_CODE 会社コード
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * BUNKERタイプコードの取得
	 * 
	 * @return BNKR_TYPE_CODE BUNKERタイプコード
	 */
	public String getBNKR_TYPE_CODE() {
		return BNKR_TYPE_CODE;
	}

	/**
	 * BUNKERタイプコードの設定
	 * 
	 * @param BNKR_TYPE_CODE BUNKERタイプコード
	 */
	public void setBNKR_TYPE_CODE(String BNKR_TYPE_CODE) {
		this.BNKR_TYPE_CODE = BNKR_TYPE_CODE;
	}

	/**
	 * BUNKERタイプコードの取得
	 * 
	 * @return KEEP_BNKR_TYPE_CODE BUNKERタイプコード
	 */
	public String getKEEP_BNKR_TYPE_CODE() {
		return KEEP_BNKR_TYPE_CODE;
	}

	/**
	 * BUNKERタイプコードの設定
	 * 
	 * @param KEEP_BNKR_TYPE_CODE BUNKERタイプコード
	 */
	public void setKEEP_BNKR_TYPE_CODE(String KEEP_BNKR_TYPE_CODE) {
		this.KEEP_BNKR_TYPE_CODE = KEEP_BNKR_TYPE_CODE;
	}

	/**
	 * GRADE_CODEの取得
	 * 
	 * @return GRADE_CODE GRADE_CODEの取得
	 */
	public String getGRADE_CODE() {
		return this.GRADE_CODE;
	}

	/**
	 * GRADE_CODEの設定
	 * 
	 * @param GRADE_CODE GRADE_CODEの設定
	 */
	public void setGRADE_CODE(String GRADE_CODE) {
		this.GRADE_CODE = GRADE_CODE;
	}

	/**
	 * GRADE名称の取得
	 * 
	 * @return GRADE_NAME GRADE名称
	 */
	public String getGRADE_NAME() {
		return GRADE_NAME;
	}

	/**
	 * GRADE名称の設定
	 * 
	 * @param GRADE_NAME GRADE名称
	 */
	public void setGRADE_NAME(String GRADE_NAME) {
		this.GRADE_NAME = GRADE_NAME;
	}

	/**
	 * ORDERの取得
	 * 
	 * @return DISP_ODR ORDER
	 */
	public int getDISP_ODR() {
		return DISP_ODR;
	}

	/**
	 * ORDERの設定
	 * 
	 * @param DISP_ODR ORDER
	 */
	public void setDISP_ODR(int DISP_ODR) {
		this.DISP_ODR = DISP_ODR;
	}

	/**
	 * MAIN_ENGINE使用順の取得
	 * 
	 * @return ENG_PRI_NOR MAIN_ENGINE使用順
	 */
	public int getENG_PRI_NOR() {
		return ENG_PRI_NOR;
	}

	/**
	 * MAIN_ENGINE使用順の設定
	 * 
	 * @param ENG_PRI_NOR MAIN_ENGINE使用順
	 */
	public void setENG_PRI_NOR(int ENG_PRI_NOR) {
		this.ENG_PRI_NOR = ENG_PRI_NOR;
	}

	/**
	 * AUX使用順の取得
	 * 
	 * @return AUX_PRI_NOR AUX使用順
	 */
	public int getAUX_PRI_NOR() {
		return AUX_PRI_NOR;
	}

	/**
	 * AUX使用順の設定
	 * 
	 * @param AUX_PRI_NOR AUX使用順
	 */
	public void setAUX_PRI_NOR(int AUX_PRI_NOR) {
		this.AUX_PRI_NOR = AUX_PRI_NOR;
	}

	/**
	 * S_ECA_MAIN_ENGINE使用順の取得
	 * 
	 * @return ENG_PRI_ECA S_ECA_MAIN_ENGINE使用順
	 */
	public int getENG_PRI_ECA() {
		return ENG_PRI_ECA;
	}

	/**
	 * S_ECA_MAIN_ENGINE使用順の設定
	 * 
	 * @param ENG_PRI_ECA S_ECA_MAIN_ENGINE使用順
	 */
	public void setENG_PRI_ECA(int ENG_PRI_ECA) {
		this.ENG_PRI_ECA = ENG_PRI_ECA;
	}

	/**
	 * S_ECA_AUX使用順の取得
	 * 
	 * @return AUX_PRI_ECA S_ECA_AUX使用順
	 */
	public int getAUX_PRI_ECA() {
		return AUX_PRI_ECA;
	}

	/**
	 * S_ECA_AUX使用順の設定
	 * 
	 * @param AUX_PRI_ECA S_ECA_AUX使用順
	 */
	public void setAUX_PRI_ECA(int AUX_PRI_ECA) {
		this.AUX_PRI_ECA = AUX_PRI_ECA;
	}

	/**
	 * SCRUBBER_MAIN_ENGINE使用順の取得
	 * 
	 * @return SC_ENG_PRI_NOR SCRUBBER_MAIN_ENGINE使用順
	 */
	public int getSC_ENG_PRI_NOR() {
		return SC_ENG_PRI_NOR;
	}

	/**
	 * SCRUBBER_MAIN_ENGINE使用順の設定
	 * 
	 * @param SC_ENG_PRI_NOR SCRUBBER_MAIN_ENGINE使用順
	 */
	public void setSC_ENG_PRI_NOR(int SC_ENG_PRI_NOR) {
		this.SC_ENG_PRI_NOR = SC_ENG_PRI_NOR;
	}

	/**
	 * SCRUBBER_AUX使用順の取得
	 * 
	 * @return SC_AUX_PRI_NOR SCRUBBER_AUX使用順
	 */
	public int getSC_AUX_PRI_NOR() {
		return SC_AUX_PRI_NOR;
	}

	/**
	 * SCRUBBER_AUX使用順の設定
	 * 
	 * @param SC_AUX_PRI_NOR SCRUBBER_AUX使用順
	 */
	public void setSC_AUX_PRI_NOR(int SC_AUX_PRI_NOR) {
		this.SC_AUX_PRI_NOR = SC_AUX_PRI_NOR;
	}

	/**
	 * SCRUBBER_S_ECA_MAIN_ENGINE使用順の取得
	 * 
	 * @return SC_ENG_PRI_ECA SCRUBBER_S_ECA_MAIN_ENGINE使用順
	 */
	public int getSC_ENG_PRI_ECA() {
		return SC_ENG_PRI_ECA;
	}

	/**
	 * SCRUBBER_S_ECA_MAIN_ENGINE使用順の設定
	 * 
	 * @param SC_ENG_PRI_ECA SCRUBBER_S_ECA_MAIN_ENGINE使用順
	 */
	public void setSC_ENG_PRI_ECA(int SC_ENG_PRI_ECA) {
		this.SC_ENG_PRI_ECA = SC_ENG_PRI_ECA;
	}

	/**
	 * SCRUBBER_S_ECA_AUX使用順の取得
	 * 
	 * @return SC_AUX_PRI_ECA SCRUBBER_S_ECA_AUX使用順
	 */
	public int getSC_AUX_PRI_ECA() {
		return SC_AUX_PRI_ECA;
	}

	/**
	 * SCRUBBER_S_ECA_AUX使用順の設定
	 * 
	 * @param SC_AUX_PRI_ECA SCRUBBER_S_ECA_AUX使用順
	 */
	public void setSC_AUX_PRI_ECA(int SC_AUX_PRI_ECA) {
		this.SC_AUX_PRI_ECA = SC_AUX_PRI_ECA;
	}

	/**
	 * (内航)小数点以下桁数の取得
	 * 
	 * @return LCL_DEC_KETA (内航)小数点以下桁数
	 */
	public int getLCL_DEC_KETA() {
		return LCL_DEC_KETA;
	}

	/**
	 * (内航)小数点以下桁数の設定
	 * 
	 * @param LCL_DEC_KETA (内航)小数点以下桁数
	 */
	public void setLCL_DEC_KETA(int LCL_DEC_KETA) {
		this.LCL_DEC_KETA = LCL_DEC_KETA;
	}

	/**
	 * 開始年月日の取得
	 * 
	 * @return STR_DATE 開始年月日
	 */
	public Date getSTR_DATE() {
		return STR_DATE;
	}

	/**
	 * 開始年月日の設定
	 * 
	 * @param STR_DATE 開始年月日
	 */
	public void setSTR_DATE(Date STR_DATE) {
		this.STR_DATE = STR_DATE;
	}

	/**
	 * 終了年月日の取得
	 * 
	 * @return END_DATE 終了年月日
	 */
	public Date getEND_DATE() {
		return END_DATE;
	}

	/**
	 * 終了年月日の設定
	 * 
	 * @param END_DATE 終了年月日
	 */
	public void setEND_DATE(Date END_DATE) {
		this.END_DATE = END_DATE;
	}

	/**
	 * 登録年月日の取得
	 * 
	 * @return INP_DATE 登録年月日
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * 登録年月日の設定
	 * 
	 * @param INP_DATE 登録年月日
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * 更新年月日の取得
	 * 
	 * @return UPD_DATE 更新年月日
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * 更新年月日の設定
	 * 
	 * @param UPD_DATE 更新年月日
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * プログラムIDの取得
	 * 
	 * @return PRG_ID プログラムID
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * プログラムIDの設定
	 * 
	 * @param PRG_ID プログラムID
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * ユーザーIDの取得
	 * 
	 * @return USR_ID ユーザーID
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ユーザーIDの設定
	 * 
	 * @param USR_ID ユーザーID
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * 0:未使用、1:使用するの取得
	 * 
	 * @return USE_FLG 0:未使用、1:使用する
	 */
	public int getUSE_FLG() {
		return USE_FLG;
	}

	/**
	 * 0:未使用、1:使用するの設定
	 * 
	 * @param USE_FLG 0:未使用、1:使用する
	 */
	public void setUSE_FLG(int USE_FLG) {
		this.USE_FLG = USE_FLG;
	}

}
