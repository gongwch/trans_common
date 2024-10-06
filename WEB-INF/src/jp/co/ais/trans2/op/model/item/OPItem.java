package jp.co.ais.trans2.op.model.item;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.model.*;

/**
 * OP ITEM
 */
public class OPItem extends TransferBase implements TReferable, AutoCompletable, FilterableEntity, Cloneable {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ITEM_CODE=");
		sb.append(ITEM_CODE);
		sb.append("/ITEM_NAME=");
		sb.append(ITEM_NAME);
		sb.append("/ITEM_CTL_KBN=");
		sb.append(ITEM_CTL_KBN);
		sb.append("/ITEM_SUB_KBN=");
		sb.append(ITEM_SUB_KBN);
		return sb.toString();
	}

	/**
	 * クローン
	 * 
	 * @see java.lang.Object clone()
	 */
	@Override
	public OPItem clone() {
		try {
			OPItem bean = (OPItem) super.clone();
			return bean;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return 表示名
	 */
	public String getDisplayText() {
		return getCode() + " " + getName();
	}

	/** 会社コード */
	protected String KAI_CODE = null;

	/** ITEMコード */
	protected String ITEM_CODE = null;

	/** ITEM名称 */
	protected String ITEM_NAME = null;

	/** ITEM略称 */
	protected String ITEM_NAME_S = null;

	/** ITEM検索名称 */
	protected String ITEM_NAME_K = null;

	/** 請求書名 */
	protected String ITEM_INV_NAME = null;

	/** CONTRACTタイプ */
	protected int CTRT_TYPE = -1;

	/** SA 区分 */
	protected int SA_KBN = -1;

	/** 貸借区分 */
	protected int DC_KBN = -1;

	/** アイテム制御区分 */
	protected String ITEM_CTL_KBN = null;

	/** アイテムSUB区分 */
	protected String ITEM_SUB_KBN = null;

	/** コミッション区分 */
	protected int ADCOM_KBN = -1;

	/** Brokarage区分 */
	protected int BRKR_KBN = -1;

	/** 発生日基準区分 */
	protected int OCCUR_BASE_KBN = -1;

	/** OWNR_SHIP_CODE */
	protected String OWNR_SHIP_CODE = null;

	/** OWNR_SHIP_NAME */
	protected String OWNR_SHIP_NAME = null;

	/** 科目コード */
	protected String KMK_CODE = null;

	/** 補助科目コード */
	protected String HKM_CODE = null;

	/** 内訳科目コード */
	protected String UKM_CODE = null;

	/** 科目名称 */
	protected String KMK_NAME = null;

	/** 補助科目名称 */
	protected String HKM_NAME = null;

	/** 内訳科目名称 */
	protected String UKM_NAME = null;

	/** 科目略称 */
	protected String KMK_NAME_S = null;

	/** 補助科目略称 */
	protected String HKM_NAME_S = null;

	/** 内訳科目略称 */
	protected String UKM_NAME_S = null;

	/** 消費税コード */
	protected String ZEI_CODE = null;

	/** 消費税名称 */
	protected String ZEI_NAME = null;

	/** 消費税名称 */
	protected String ZEI_NAME_S = null;

	/** 繰延科目コード */
	protected String KURI_KMK_CODE = null;

	/** 繰延補助科目コード */
	protected String KURI_HKM_CODE = null;

	/** 繰延内訳科目コード */
	protected String KURI_UKM_CODE = null;

	/** 繰延科目名称 */
	protected String KURI_KMK_NAME = null;

	/** 繰延科目名称 */
	protected String KURI_KMK_NAME_S = null;

	/** 繰延補助科目名称 */
	protected String KURI_HKM_NAME = null;

	/** 繰延補助科目名称 */
	protected String KURI_HKM_NAME_S = null;

	/** 繰延内訳科目名称 */
	protected String KURI_UKM_NAME = null;

	/** 繰延内訳科目名称 */
	protected String KURI_UKM_NAME_S = null;

	/** 繰延科目 消費税コード */
	protected String KURI_ZEI_CODE = null;

	/** 繰延科目の消費税名称 */
	protected String KURI_ZEI_NAME = null;

	/** 繰延科目の消費税名称 */
	protected String KURI_ZEI_NAME_S = null;

	/** 相手科目コード */
	protected String AT_KMK_CODE = null;

	/** 相手補助科目コード */
	protected String AT_HKM_CODE = null;

	/** 相手内訳科目コード */
	protected String AT_UKM_CODE = null;

	/** 相手科目名称 */
	protected String AT_KMK_NAME = null;

	/** 相手補助科目名称 */
	protected String AT_HKM_NAME = null;

	/** 相手内訳科目名称 */
	protected String AT_UKM_NAME = null;

	/** 相手科目略称 */
	protected String AT_KMK_NAME_S = null;

	/** 相手補助科目略称 */
	protected String AT_HKM_NAME_S = null;

	/** 相手内訳科目略称 */
	protected String AT_UKM_NAME_S = null;

	/** 相手消費税コード */
	protected String AT_ZEI_CODE = null;

	/** 相手消費税名称 */
	protected String AT_ZEI_NAME = null;

	/** 相手消費税名称 */
	protected String AT_ZEI_NAME_S = null;

	/** 相手繰延科目コード */
	protected String AT_KURI_KMK_CODE = null;

	/** 相手繰延補助科目コード */
	protected String AT_KURI_HKM_CODE = null;

	/** 相手繰延内訳科目コード */
	protected String AT_KURI_UKM_CODE = null;

	/** 相手繰延科目名称 */
	protected String AT_KURI_KMK_NAME = null;

	/** 相手繰延補助科目名称 */
	protected String AT_KURI_HKM_NAME = null;

	/** 相手繰延内訳科目名称 */
	protected String AT_KURI_UKM_NAME = null;

	/** 相手繰延科目名称 */
	protected String AT_KURI_KMK_NAME_S = null;

	/** 相手繰延補助科目名称 */
	protected String AT_KURI_HKM_NAME_S = null;

	/** 相手繰延内訳科目名称 */
	protected String AT_KURI_UKM_NAME_S = null;

	/** 相手繰延科目 消費税コード */
	protected String AT_KURI_ZEI_CODE = null;

	/** 相手繰延科目の消費税名称 */
	protected String AT_KURI_ZEI_NAME = null;

	/** 相手繰延科目の消費税名称 */
	protected String AT_KURI_ZEI_NAME_S = null;

	/** 概算科目コード */
	protected String EST_KMK_CODE = null;

	/** 概算補助科目コード */
	protected String EST_HKM_CODE = null;

	/** 概算内訳科目コード */
	protected String EST_UKM_CODE = null;

	/** 概算科目名称 */
	protected String EST_KMK_NAME = null;

	/** 概算科目名称 */
	protected String EST_KMK_NAME_S = null;

	/** 概算補助科目名称 */
	protected String EST_HKM_NAME = null;

	/** 概算補助科目名称 */
	protected String EST_HKM_NAME_S = null;

	/** 概算内訳科目名称 */
	protected String EST_UKM_NAME = null;

	/** 概算内訳科目名称 */
	protected String EST_UKM_NAME_S = null;

	/** 概算科目 消費税コード */
	protected String EST_ZEI_CODE = null;

	/** 概算科目の消費税名称 */
	protected String EST_ZEI_NAME = null;

	/** 概算科目の消費税名称 */
	protected String EST_ZEI_NAME_S = null;

	/** 相手概算科目コード */
	protected String AT_EST_KMK_CODE = null;

	/** 相手概算補助科目コード */
	protected String AT_EST_HKM_CODE = null;

	/** 相手概算内訳科目コード */
	protected String AT_EST_UKM_CODE = null;

	/** 相手概算科目名称 */
	protected String AT_EST_KMK_NAME = null;

	/** 相手概算補助科目名称 */
	protected String AT_EST_HKM_NAME = null;

	/** 相手概算内訳科目名称 */
	protected String AT_EST_UKM_NAME = null;

	/** 相手概算科目名称 */
	protected String AT_EST_KMK_NAME_S = null;

	/** 相手概算補助科目名称 */
	protected String AT_EST_HKM_NAME_S = null;

	/** 相手概算内訳科目名称 */
	protected String AT_EST_UKM_NAME_S = null;

	/** 相手概算科目 消費税コード */
	protected String AT_EST_ZEI_CODE = null;

	/** 相手概算科目の消費税名称 */
	protected String AT_EST_ZEI_NAME = null;

	/** 相手概算科目の消費税名称 */
	protected String AT_EST_ZEI_NAME_S = null;

	/** 行摘要 */
	protected String ROW_OUTLINE = null;

	/** 開始年月日 */
	protected Date STR_DATE = null;

	/** 終了年月日 */
	protected Date END_DATE = null;

	/** 登録日付 */
	protected Date INP_DATE = null;

	/** 更新日付 */
	protected Date UPD_DATE = null;

	/** プログラムＩＤ */
	protected String PRG_ID = null;

	/** ユーザーＩＤ */
	protected String USR_ID = null;

	/** BNKR_TYPE_CODE */
	protected String BNKR_TYPE_CODE = null;

	/** VC/VR変換アイテムコード */
	protected String VCVR_ITEM_CODE = null;

	/** 収益借方①科目コード */
	protected String NEW_ACC_DR_KMK_CODE = null;

	/** 収益借方①補助科目コード */
	protected String NEW_ACC_DR_HKM_CODE = null;

	/** 収益借方①内訳科目コード */
	protected String NEW_ACC_DR_UKM_CODE = null;

	/** 収益借方①消費税コード */
	protected String NEW_ACC_DR_ZEI_CODE = null;

	/** 収益借方①科目名称 */
	protected String NEW_ACC_DR_KMK_NAME = null;

	/** 収益借方①補助科目名称 */
	protected String NEW_ACC_DR_HKM_NAME = null;

	/** 収益借方①内訳科目名称 */
	protected String NEW_ACC_DR_UKM_NAME = null;

	/** 収益借方①消費税名称 */
	protected String NEW_ACC_DR_ZEI_NAME = null;

	/** 収益借方①科目 */
	protected String NEW_ACC_DR_KMK_NAME_S = null;

	/** 収益借方①補助科目略称 */
	protected String NEW_ACC_DR_HKM_NAME_S = null;

	/** 収益借方①内訳科目略称 */
	protected String NEW_ACC_DR_UKM_NAME_S = null;

	/** 収益借方①消費税略称 */
	protected String NEW_ACC_DR_ZEI_NAME_S = null;

	/** 収益貸方①科目コード */
	protected String NEW_ACC_CR_KMK_CODE = null;

	/** 収益貸方①補助科目コード */
	protected String NEW_ACC_CR_HKM_CODE = null;

	/** 収益貸方①内訳科目コード */
	protected String NEW_ACC_CR_UKM_CODE = null;

	/** 収益貸方①消費税コード */
	protected String NEW_ACC_CR_ZEI_CODE = null;

	/** 収益貸方①科目名称 */
	protected String NEW_ACC_CR_KMK_NAME = null;

	/** 収益貸方①補助科目名称 */
	protected String NEW_ACC_CR_HKM_NAME = null;

	/** 収益貸方①内訳科目名称 */
	protected String NEW_ACC_CR_UKM_NAME = null;

	/** 収益貸方①消費税名称 */
	protected String NEW_ACC_CR_ZEI_NAME = null;

	/** 収益貸方①科目 */
	protected String NEW_ACC_CR_KMK_NAME_S = null;

	/** 収益貸方①補助科目略称 */
	protected String NEW_ACC_CR_HKM_NAME_S = null;

	/** 収益貸方①内訳科目略称 */
	protected String NEW_ACC_CR_UKM_NAME_S = null;

	/** 収益貸方①消費税略称 */
	protected String NEW_ACC_CR_ZEI_NAME_S = null;

	/** 収益借方②科目コード */
	protected String NEW_ACC_DR_2_KMK_CODE = null;

	/** 収益借方②補助科目コード */
	protected String NEW_ACC_DR_2_HKM_CODE = null;

	/** 収益借方②内訳科目コード */
	protected String NEW_ACC_DR_2_UKM_CODE = null;

	/** 収益借方②消費税コード */
	protected String NEW_ACC_DR_2_ZEI_CODE = null;

	/** 収益借方②科目名称 */
	protected String NEW_ACC_DR_2_KMK_NAME = null;

	/** 収益借方②補助科目名称 */
	protected String NEW_ACC_DR_2_HKM_NAME = null;

	/** 収益借方②内訳科目名称 */
	protected String NEW_ACC_DR_2_UKM_NAME = null;

	/** 収益借方②消費税名称 */
	protected String NEW_ACC_DR_2_ZEI_NAME = null;

	/** 収益借方②科目 */
	protected String NEW_ACC_DR_2_KMK_NAME_S = null;

	/** 収益借方②補助科目略称 */
	protected String NEW_ACC_DR_2_HKM_NAME_S = null;

	/** 収益借方②内訳科目略称 */
	protected String NEW_ACC_DR_2_UKM_NAME_S = null;

	/** 収益借方②消費税略称 */
	protected String NEW_ACC_DR_2_ZEI_NAME_S = null;

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
	 * ITEMコードの取得
	 * 
	 * @return ITEM_CODE ITEMコード
	 */
	public String getITEM_CODE() {
		return ITEM_CODE;
	}

	/**
	 * ITEMコードの設定
	 * 
	 * @param ITEM_CODE ITEMコード
	 */
	public void setITEM_CODE(String ITEM_CODE) {
		this.ITEM_CODE = ITEM_CODE;
	}

	/**
	 * ITEM名称の取得
	 * 
	 * @return ITEM_NAME ITEM名称
	 */
	public String getITEM_NAME() {
		return ITEM_NAME;
	}

	/**
	 * ITEM名称の設定
	 * 
	 * @param ITEM_NAME ITEM名称
	 */
	public void setITEM_NAME(String ITEM_NAME) {
		this.ITEM_NAME = ITEM_NAME;
	}

	/**
	 * ITEM略称の取得
	 * 
	 * @return ITEM_NAME_S ITEM略称
	 */
	public String getITEM_NAME_S() {
		return ITEM_NAME_S;
	}

	/**
	 * ITEM略称の設定
	 * 
	 * @param ITEM_NAME_S ITEM略称
	 */
	public void setITEM_NAME_S(String ITEM_NAME_S) {
		this.ITEM_NAME_S = ITEM_NAME_S;
	}

	/**
	 * ITEM検索名称の取得
	 * 
	 * @return ITEM_NAME_K ITEM検索名称
	 */
	public String getITEM_NAME_K() {
		return ITEM_NAME_K;
	}

	/**
	 * ITEM検索名称の設定
	 * 
	 * @param ITEM_NAME_K ITEM検索名称
	 */
	public void setITEM_NAME_K(String ITEM_NAME_K) {
		this.ITEM_NAME_K = ITEM_NAME_K;
	}

	/**
	 * 請求書名の取得
	 * 
	 * @return ITEM_INV_NAME 請求書名
	 */
	public String getITEM_INV_NAME() {
		return ITEM_INV_NAME;
	}

	/**
	 * 請求書名の設定
	 * 
	 * @param ITEM_INV_NAME 請求書名
	 */
	public void setITEM_INV_NAME(String ITEM_INV_NAME) {
		this.ITEM_INV_NAME = ITEM_INV_NAME;
	}

	/**
	 * CONTRACTタイプの取得
	 * 
	 * @return CTRT_TYPE CONTRACTタイプ
	 */
	public int getCTRT_TYPE() {
		return CTRT_TYPE;
	}

	/**
	 * CONTRACTタイプの設定
	 * 
	 * @param CTRT_TYPE CONTRACTタイプ
	 */
	public void setCTRT_TYPE(int CTRT_TYPE) {
		this.CTRT_TYPE = CTRT_TYPE;
	}

	/**
	 * SA区分の取得
	 * 
	 * @return SA_KBN SA区分
	 */
	public int getSA_KBN() {
		return SA_KBN;
	}

	/**
	 * SA区分の設定
	 * 
	 * @param SA_KBN SA区分
	 */
	public void setSA_KBN(int SA_KBN) {
		this.SA_KBN = SA_KBN;
	}

	/**
	 * 貸借区分の取得
	 * 
	 * @return DC_KBN 貸借区分
	 */
	public int getDC_KBN() {
		return DC_KBN;
	}

	/**
	 * 貸借区分の設定
	 * 
	 * @param DC_KBN 貸借区分
	 */
	public void setDC_KBN(int DC_KBN) {
		this.DC_KBN = DC_KBN;
	}

	/**
	 * アイテム制御区分の取得
	 * 
	 * @return ITEM_CTL_KBN アイテム制御区分
	 */
	public String getITEM_CTL_KBN() {
		return ITEM_CTL_KBN;
	}

	/**
	 * アイテム制御区分の設定
	 * 
	 * @param ITEM_CTL_KBN アイテム制御区分
	 */
	public void setITEM_CTL_KBN(String ITEM_CTL_KBN) {
		this.ITEM_CTL_KBN = ITEM_CTL_KBN;
	}

	/**
	 * アイテムSUB区分の取得
	 * 
	 * @return ITEM_SUB_KBN アイテムSUB区分
	 */
	public String getITEM_SUB_KBN() {
		return ITEM_SUB_KBN;
	}

	/**
	 * アイテムSUB区分の設定
	 * 
	 * @param ITEM_SUB_KBN アイテムSUB区分
	 */
	public void setITEM_SUB_KBN(String ITEM_SUB_KBN) {
		this.ITEM_SUB_KBN = ITEM_SUB_KBN;
	}

	/**
	 * コミッション区分の取得
	 * 
	 * @return ADCOM_KBN コミッション区分
	 */
	public int getADCOM_KBN() {
		return ADCOM_KBN;
	}

	/**
	 * コミッション区分の設定
	 * 
	 * @param ADCOM_KBN コミッション区分
	 */
	public void setADCOM_KBN(int ADCOM_KBN) {
		this.ADCOM_KBN = ADCOM_KBN;
	}

	/**
	 * Brokerage区分の取得
	 * 
	 * @return BRKR_KBN Brokerage区分
	 */
	public int getBRKR_KBN() {
		return BRKR_KBN;
	}

	/**
	 * Brokerage区分の設定
	 * 
	 * @param BRKR_KBN Brokerage区分
	 */
	public void setBRKR_KBN(int BRKR_KBN) {
		this.BRKR_KBN = BRKR_KBN;
	}

	/**
	 * 発生日基準区分の取得
	 * 
	 * @return OCCUR_BASE_KBN 発生日基準区分
	 */
	public int getOCCUR_BASE_KBN() {
		return OCCUR_BASE_KBN;
	}

	/**
	 * 発生日基準区分の設定
	 * 
	 * @param OCCUR_BASE_KBN 発生日基準区分
	 */
	public void setOCCUR_BASE_KBN(int OCCUR_BASE_KBN) {
		this.OCCUR_BASE_KBN = OCCUR_BASE_KBN;
	}

	/**
	 * OWNR_SHIP_CODE の取得
	 * 
	 * @return OWNR_SHIP_CODE
	 */
	public String getOWNR_SHIP_CODE() {
		return OWNR_SHIP_CODE;
	}

	/**
	 * OWNR_SHIP_CODE の設定
	 * 
	 * @param OWNR_SHIP_CODE
	 */
	public void setOWNR_SHIP_CODE(String OWNR_SHIP_CODE) {
		this.OWNR_SHIP_CODE = OWNR_SHIP_CODE;
	}

	/**
	 * OWNR_SHIP_NAME の取得
	 * 
	 * @return OWNR_SHIP_NAME
	 */
	public String getOWNR_SHIP_NAME() {
		return OWNR_SHIP_NAME;
	}

	/**
	 * OWNR_SHIP_NAME の設定
	 * 
	 * @param OWNR_SHIP_NAME
	 */
	public void setOWNR_SHIP_NAME(String OWNR_SHIP_NAME) {
		this.OWNR_SHIP_NAME = OWNR_SHIP_NAME;
	}

	/**
	 * 科目コードの取得
	 * 
	 * @return KMK_CODE 科目コード
	 */
	public String getKMK_CODE() {
		return KMK_CODE;
	}

	/**
	 * 科目コードの設定
	 * 
	 * @param KMK_CODE 科目コード
	 */
	public void setKMK_CODE(String KMK_CODE) {
		this.KMK_CODE = KMK_CODE;
	}

	/**
	 * 補助科目コードの取得
	 * 
	 * @return HKM_CODE 補助科目コード
	 */
	public String getHKM_CODE() {
		return HKM_CODE;
	}

	/**
	 * 補助科目コードの設定
	 * 
	 * @param HKM_CODE 補助科目コード
	 */
	public void setHKM_CODE(String HKM_CODE) {
		this.HKM_CODE = HKM_CODE;
	}

	/**
	 * 内訳科目コードの取得
	 * 
	 * @return UKM_CODE 内訳科目コード
	 */
	public String getUKM_CODE() {
		return UKM_CODE;
	}

	/**
	 * 内訳科目コードの設定
	 * 
	 * @param UKM_CODE 内訳科目コード
	 */
	public void setUKM_CODE(String UKM_CODE) {
		this.UKM_CODE = UKM_CODE;
	}

	/**
	 * 科目略称の取得
	 * 
	 * @return KMK_NAME_S 科目略称
	 */
	public String getKMK_NAME_S() {
		return KMK_NAME_S;
	}

	/**
	 * 科目略称の設定
	 * 
	 * @param KMK_NAME_S 科目略称
	 */
	public void setKMK_NAME_S(String KMK_NAME_S) {
		this.KMK_NAME_S = KMK_NAME_S;
	}

	/**
	 * 補助科目略称の取得
	 * 
	 * @return HKM_NAME_S 補助科目略称
	 */
	public String getHKM_NAME_S() {
		return HKM_NAME_S;
	}

	/**
	 * 補助科目略称の設定
	 * 
	 * @param HKM_NAME_S 補助科目略称
	 */
	public void setHKM_NAME_S(String HKM_NAME_S) {
		this.HKM_NAME_S = HKM_NAME_S;
	}

	/**
	 * 内訳科目略称の取得
	 * 
	 * @return UKM_NAME_S 内訳科目略称
	 */
	public String getUKM_NAME_S() {
		return UKM_NAME_S;
	}

	/**
	 * 内訳科目略称の設定
	 * 
	 * @param UKM_NAME_S 内訳科目略称
	 */
	public void setUKM_NAME_S(String UKM_NAME_S) {
		this.UKM_NAME_S = UKM_NAME_S;
	}

	/**
	 * 科目-補助-内訳の略称の取得
	 * 
	 * @return 科目-補助-内訳の略称
	 */
	public String getAccountName() {
		return Util.avoidNull(KMK_NAME_S) + " " + Util.avoidNull(HKM_NAME_S) + " " + Util.avoidNull(UKM_NAME_S);
	}

	/**
	 * 科目-補助-内訳のコードの取得
	 * 
	 * @return 科目-補助-内訳のコード
	 */
	public String getAccountCode() {

		return Util.avoidNull(KMK_CODE) + " " + Util.avoidNull(HKM_CODE) + " " + Util.avoidNull(UKM_CODE);
	}

	/**
	 * 消費税コードの取得
	 * 
	 * @return ZEI_CODE 消費税コード
	 */
	public String getZEI_CODE() {
		return ZEI_CODE;
	}

	/**
	 * 消費税コードの設定
	 * 
	 * @param ZEI_CODE 消費税コード
	 */
	public void setZEI_CODE(String ZEI_CODE) {
		this.ZEI_CODE = ZEI_CODE;
	}

	/**
	 * ROW_OUTLINEの取得
	 * 
	 * @return ROW_OUTLINE ROW_OUTLINE
	 */
	public String getROW_OUTLINE() {
		return ROW_OUTLINE;
	}

	/**
	 * ROW_OUTLINEの設定
	 * 
	 * @param ROW_OUTLINE ROW_OUTLINE
	 */
	public void setROW_OUTLINE(String ROW_OUTLINE) {
		this.ROW_OUTLINE = ROW_OUTLINE;
	}

	/**
	 * KURI_KMK_CODE 科目コードの取得
	 * 
	 * @return KURI_KMK_CODE 目コード
	 */
	public String getKURI_KMK_CODE() {
		return KURI_KMK_CODE;
	}

	/**
	 * KURI_KMK_CODE 科目コードの設定
	 * 
	 * @param KURI_KMK_CODE 科目コード
	 */
	public void setKURI_KMK_CODE(String KURI_KMK_CODE) {
		this.KURI_KMK_CODE = KURI_KMK_CODE;
	}

	/**
	 * KURI補助科目コードの取得
	 * 
	 * @return KURI_HKM_CODE 補助科目コード
	 */
	public String getKURI_HKM_CODE() {
		return KURI_HKM_CODE;
	}

	/**
	 * KURI補助科目コードの設定
	 * 
	 * @param KURI_HKM_CODE 補助科目コード
	 */
	public void setKURI_HKM_CODE(String KURI_HKM_CODE) {
		this.KURI_HKM_CODE = KURI_HKM_CODE;
	}

	/**
	 * AddCom内訳科目コードの取得
	 * 
	 * @return ADDCOM_UKM_CODE AddCom内訳科目コード
	 */
	public String getKURI_UKM_CODE() {
		return KURI_UKM_CODE;
	}

	/**
	 * KURI内訳科目コードの設定
	 * 
	 * @param KURI_UKM_CODE AddCom内訳科目コード
	 */
	public void setKURI_UKM_CODE(String KURI_UKM_CODE) {
		this.KURI_UKM_CODE = KURI_UKM_CODE;
	}

	/**
	 * KURI科目の消費税コードの取得
	 * 
	 * @return KURI_ZEI_CODE 目の消費税コード
	 */
	public String getKURI_ZEI_CODE() {
		return KURI_ZEI_CODE;
	}

	/**
	 * KURI科目の消費税コードの設定
	 * 
	 * @param KURI_ZEI_CODE 科目の消費税コード
	 */
	public void setKURI_ZEI_CODE(String KURI_ZEI_CODE) {
		this.KURI_ZEI_CODE = KURI_ZEI_CODE;
	}

	/**
	 * 科目名称の取得
	 * 
	 * @return KMK_NAME 科目名称
	 */
	public String getKMK_NAME() {
		return KMK_NAME;
	}

	/**
	 * 科目名称の設定
	 * 
	 * @param KMK_NAME 科目名称
	 */
	public void setKMK_NAME(String KMK_NAME) {
		this.KMK_NAME = KMK_NAME;
	}

	/**
	 * 補助科目名称の取得
	 * 
	 * @return HKM_NAME 補助科目名称
	 */
	public String getHKM_NAME() {
		return HKM_NAME;
	}

	/**
	 * 補助科目名称の設定
	 * 
	 * @param HKM_NAME 補助科目名称
	 */
	public void setHKM_NAME(String HKM_NAME) {
		this.HKM_NAME = HKM_NAME;
	}

	/**
	 * 内訳科目名称の取得
	 * 
	 * @return UKM_NAME 内訳科目名称
	 */
	public String getUKM_NAME() {
		return UKM_NAME;
	}

	/**
	 * 内訳科目名称の設定
	 * 
	 * @param UKM_NAME 内訳科目
	 */
	public void setUKM_NAME(String UKM_NAME) {
		this.UKM_NAME = UKM_NAME;
	}

	/**
	 * 消費税名称の取得
	 * 
	 * @return ZEI_CODE 消費税名称
	 */
	public String getZEI_NAME() {
		return ZEI_NAME;
	}

	/**
	 * 消費税名称の設定
	 * 
	 * @param ZEI_NAME 消費税名称
	 */
	public void setZEI_NAME(String ZEI_NAME) {
		this.ZEI_NAME = ZEI_NAME;
	}

	/**
	 * 消費税名称の取得
	 * 
	 * @return ZEI_CODE 消費税名称
	 */
	public String getZEI_NAME_S() {
		return ZEI_NAME_S;
	}

	/**
	 * 消費税名称の設定
	 * 
	 * @param ZEI_NAME_S 消費税名称
	 */
	public void setZEI_NAME_S(String ZEI_NAME_S) {
		this.ZEI_NAME_S = ZEI_NAME_S;
	}

	/**
	 * KURI科目名称の取得
	 * 
	 * @return KURI_KMK_NAME 科目名称
	 */
	public String getKURI_KMK_NAME() {
		return KURI_KMK_NAME;
	}

	/**
	 * KURI科目名称の設定
	 * 
	 * @param KURI_KMK_NAME 科目名称
	 */
	public void setKURI_KMK_NAME(String KURI_KMK_NAME) {
		this.KURI_KMK_NAME = KURI_KMK_NAME;
	}

	/**
	 * KURI補助科目名称の取得
	 * 
	 * @return KURI_HKM_NAME 補助科目名称
	 */
	public String getKURI_HKM_NAME() {
		return KURI_HKM_NAME;
	}

	/**
	 * KURI補助科目名称の設定
	 * 
	 * @param KURI_HKM_NAME 補助科目名称
	 */
	public void setKURI_HKM_NAME(String KURI_HKM_NAME) {
		this.KURI_HKM_NAME = KURI_HKM_NAME;
	}

	/**
	 * KURI内訳科目名称の取得
	 * 
	 * @return KURI_UKM_NAME 内訳科目名称
	 */
	public String getKURI_UKM_NAME() {
		return KURI_UKM_NAME;
	}

	/**
	 * KURI科目名称の設定
	 * 
	 * @param KURI_UKM_NAME 内訳科目名称
	 */
	public void setKURI_UKM_NAME(String KURI_UKM_NAME) {
		this.KURI_UKM_NAME = KURI_UKM_NAME;
	}

	/**
	 * KURI科目の消費税名称の取得
	 * 
	 * @return KURI_ZEI_NAME 科目の消費税名称
	 */
	public String getKURI_ZEI_NAME() {
		return KURI_ZEI_NAME;
	}

	/**
	 * KURI科目の消費税名称の設定
	 * 
	 * @param KURI_ZEI_NAME 科目の消費税名称
	 */
	public void setKURI_ZEI_NAME(String KURI_ZEI_NAME) {
		this.KURI_ZEI_NAME = KURI_ZEI_NAME;
	}

	/**
	 * KURI科目の消費税名称の取得
	 * 
	 * @return KURI_ZEI_NAME 科目の消費税名称
	 */
	public String getKURI_ZEI_NAME_S() {
		return KURI_ZEI_NAME_S;
	}

	/**
	 * KURI科目の消費税名称の設定
	 * 
	 * @param KURI_ZEI_NAME_S 科目の消費税名称
	 */
	public void setKURI_ZEI_NAME_S(String KURI_ZEI_NAME_S) {
		this.KURI_ZEI_NAME_S = KURI_ZEI_NAME_S;
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
	 * 登録日付の取得
	 * 
	 * @return INP_DATE 登録日付
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * 登録日付の設定
	 * 
	 * @param INP_DATE 登録日付
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * 更新日付の取得
	 * 
	 * @return UPD_DATE 更新日付
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * 更新日付の設定
	 * 
	 * @param UPD_DATE 更新日付
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * プログラムＩＤの取得
	 * 
	 * @return PRG_ID プログラムＩＤ
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * プログラムＩＤの設定
	 * 
	 * @param PRG_ID プログラムＩＤ
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * ユーザーＩＤの取得
	 * 
	 * @return USR_ID ユーザーＩＤ
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ユーザーＩＤの設定
	 * 
	 * @param USR_ID ユーザーＩＤ
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * 科目コードの取得（相手）
	 * 
	 * @return AT_KMK_CODE 科目コード
	 */
	public String getAT_KMK_CODE() {
		return AT_KMK_CODE;
	}

	/**
	 * 科目コードの設定（相手）
	 * 
	 * @param AT_KMK_CODE 科目コード
	 */
	public void setAT_KMK_CODE(String AT_KMK_CODE) {
		this.AT_KMK_CODE = AT_KMK_CODE;
	}

	/**
	 * 補助科目コードの取得（相手）
	 * 
	 * @return AT_HKM_CODE 補助科目コード
	 */
	public String getAT_HKM_CODE() {
		return AT_HKM_CODE;
	}

	/**
	 * 補助科目コードの設定（相手）
	 * 
	 * @param AT_HKM_CODE 補助科目コード
	 */
	public void setAT_HKM_CODE(String AT_HKM_CODE) {
		this.AT_HKM_CODE = AT_HKM_CODE;
	}

	/**
	 * 内訳科目コードの取得（相手）
	 * 
	 * @return AT_UKM_CODE 内訳科目コード
	 */
	public String getAT_UKM_CODE() {
		return AT_UKM_CODE;
	}

	/**
	 * 内訳科目コードの設定（相手）
	 * 
	 * @param AT_UKM_CODE 内訳科目コード
	 */
	public void setAT_UKM_CODE(String AT_UKM_CODE) {
		this.AT_UKM_CODE = AT_UKM_CODE;
	}

	/**
	 * 科目略称の取得（相手）
	 * 
	 * @return AT_KMK_NAME_S 科目略称
	 */
	public String getAT_KMK_NAME_S() {
		return AT_KMK_NAME_S;
	}

	/**
	 * 科目略称の設定（相手）
	 * 
	 * @param AT_KMK_NAME_S 科目略称
	 */
	public void setAT_KMK_NAME_S(String AT_KMK_NAME_S) {
		this.AT_KMK_NAME_S = AT_KMK_NAME_S;
	}

	/**
	 * 補助科目略称の取得（相手）
	 * 
	 * @return AT_HKM_NAME_S 補助科目略称
	 */
	public String getAT_HKM_NAME_S() {
		return AT_HKM_NAME_S;
	}

	/**
	 * 補助科目略称の設定（相手）
	 * 
	 * @param AT_HKM_NAME_S 補助科目略称
	 */
	public void setAT_HKM_NAME_S(String AT_HKM_NAME_S) {
		this.AT_HKM_NAME_S = AT_HKM_NAME_S;
	}

	/**
	 * 内訳科目略称の取得（相手）
	 * 
	 * @return AT_UKM_NAME_S 内訳科目略称
	 */
	public String getAT_UKM_NAME_S() {
		return AT_UKM_NAME_S;
	}

	/**
	 * 内訳科目略称の設定（相手）
	 * 
	 * @param AT_UKM_NAME_S 内訳科目略称
	 */
	public void setAT_UKM_NAME_S(String AT_UKM_NAME_S) {
		this.AT_UKM_NAME_S = AT_UKM_NAME_S;
	}

	/**
	 * 科目-補助-内訳の略称の取得（相手）
	 * 
	 * @return 科目-補助-内訳の略称
	 */
	public String getAT_AccountName() {
		return Util.avoidNull(AT_KMK_NAME_S) + " " + Util.avoidNull(AT_HKM_NAME_S) + " "
			+ Util.avoidNull(AT_UKM_NAME_S);
	}

	/**
	 * 消費税コードの取得（相手）
	 * 
	 * @return AT_ZEI_CODE 消費税コード
	 */
	public String getAT_ZEI_CODE() {
		return AT_ZEI_CODE;
	}

	/**
	 * 消費税コードの設定（相手）
	 * 
	 * @param AT_ZEI_CODE 消費税コード
	 */
	public void setAT_ZEI_CODE(String AT_ZEI_CODE) {
		this.AT_ZEI_CODE = AT_ZEI_CODE;
	}

	/**
	 * AT_KURI_KMK_CODE 科目コードの取得（相手）
	 * 
	 * @return AT_KURI_KMK_CODE 目コード
	 */
	public String getAT_KURI_KMK_CODE() {
		return AT_KURI_KMK_CODE;
	}

	/**
	 * AT_KURI_KMK_CODE 科目コードの設定（相手）
	 * 
	 * @param AT_KURI_KMK_CODE 科目コード
	 */
	public void setAT_KURI_KMK_CODE(String AT_KURI_KMK_CODE) {
		this.AT_KURI_KMK_CODE = AT_KURI_KMK_CODE;
	}

	/**
	 * KURI補助科目コードの取得（相手）
	 * 
	 * @return AT_KURI_HKM_CODE 補助科目コード
	 */
	public String getAT_KURI_HKM_CODE() {
		return AT_KURI_HKM_CODE;
	}

	/**
	 * KURI補助科目コードの設定（相手）
	 * 
	 * @param AT_KURI_HKM_CODE 補助科目コード
	 */
	public void setAT_KURI_HKM_CODE(String AT_KURI_HKM_CODE) {
		this.AT_KURI_HKM_CODE = AT_KURI_HKM_CODE;
	}

	/**
	 * KURI内訳科目コードの取得（相手）
	 * 
	 * @return AT_KURI_UKM_CODE 内訳科目コード
	 */
	public String getAT_KURI_UKM_CODE() {
		return AT_KURI_UKM_CODE;
	}

	/**
	 * KURI内訳科目コードの設定（相手）
	 * 
	 * @param AT_KURI_UKM_CODE 内訳科目コード
	 */
	public void setAT_KURI_UKM_CODE(String AT_KURI_UKM_CODE) {
		this.AT_KURI_UKM_CODE = AT_KURI_UKM_CODE;
	}

	/**
	 * KURI科目の消費税コードの取得（相手）
	 * 
	 * @return AT_KURI_ZEI_CODE 目の消費税コード
	 */
	public String getAT_KURI_ZEI_CODE() {
		return AT_KURI_ZEI_CODE;
	}

	/**
	 * KURI科目の消費税コードの設定（相手）
	 * 
	 * @param AT_KURI_ZEI_CODE 科目の消費税コード
	 */
	public void setAT_KURI_ZEI_CODE(String AT_KURI_ZEI_CODE) {
		this.AT_KURI_ZEI_CODE = AT_KURI_ZEI_CODE;
	}

	/**
	 * 科目名称の取得（相手）
	 * 
	 * @return AT_KMK_NAME 科目名称
	 */
	public String getAT_KMK_NAME() {
		return AT_KMK_NAME;
	}

	/**
	 * 科目名称の設定（相手）
	 * 
	 * @param AT_KMK_NAME 科目名称
	 */
	public void setAT_KMK_NAME(String AT_KMK_NAME) {
		this.AT_KMK_NAME = AT_KMK_NAME;
	}

	/**
	 * 補助科目名称の取得（相手）
	 * 
	 * @return AT_HKM_NAME 補助科目名称
	 */
	public String getAT_HKM_NAME() {
		return AT_HKM_NAME;
	}

	/**
	 * 補助科目名称の設定（相手）
	 * 
	 * @param AT_HKM_NAME 補助科目名称
	 */
	public void setAT_HKM_NAME(String AT_HKM_NAME) {
		this.AT_HKM_NAME = AT_HKM_NAME;
	}

	/**
	 * 内訳科目名称の取得（相手）
	 * 
	 * @return AT_UKM_NAME 内訳科目名称
	 */
	public String getAT_UKM_NAME() {
		return AT_UKM_NAME;
	}

	/**
	 * 内訳科目名称の設定（相手）
	 * 
	 * @param AT_UKM_NAME 内訳科目
	 */
	public void setAT_UKM_NAME(String AT_UKM_NAME) {
		this.AT_UKM_NAME = AT_UKM_NAME;
	}

	/**
	 * 消費税名称の取得（相手）
	 * 
	 * @return ZEI_NAME 消費税名称
	 */
	public String getAT_ZEI_NAME() {
		return AT_ZEI_NAME;
	}

	/**
	 * 消費税名称の設定（相手）
	 * 
	 * @param AT_ZEI_NAME_S 消費税名称
	 */
	public void setAT_ZEI_NAME_S(String AT_ZEI_NAME_S) {
		this.AT_ZEI_NAME_S = AT_ZEI_NAME_S;
	}

	/**
	 * 消費税名称の取得（相手）
	 * 
	 * @return ZEI_NAME_S 消費税名称
	 */
	public String getAT_ZEI_NAME_S() {
		return AT_ZEI_NAME_S;
	}

	/**
	 * 消費税名称の設定（相手）
	 * 
	 * @param AT_ZEI_NAME_S 消費税名称
	 */
	public void setAT_ZEI_NAME(String AT_ZEI_NAME_S) {
		this.AT_ZEI_NAME_S = AT_ZEI_NAME_S;
	}

	/**
	 * KURI科目名称の取得（相手）
	 * 
	 * @return AT_KURI_KMK_NAME 科目名称
	 */
	public String getAT_KURI_KMK_NAME() {
		return AT_KURI_KMK_NAME;
	}

	/**
	 * KURI科目名称の設定（相手）
	 * 
	 * @param AT_KURI_KMK_NAME 科目名称
	 */
	public void setAT_KURI_KMK_NAME(String AT_KURI_KMK_NAME) {
		this.AT_KURI_KMK_NAME = AT_KURI_KMK_NAME;
	}

	/**
	 * KURI補助科目名称の取得（相手）
	 * 
	 * @return KURI_HKM_NAME 補助科目名称
	 */
	public String getAT_KURI_HKM_NAME() {
		return AT_KURI_HKM_NAME;
	}

	/**
	 * KURI補助科目名称の設定（相手）
	 * 
	 * @param AT_KURI_HKM_NAME 補助科目名称
	 */
	public void setAT_KURI_HKM_NAME(String AT_KURI_HKM_NAME) {
		this.AT_KURI_HKM_NAME = AT_KURI_HKM_NAME;
	}

	/**
	 * KURI内訳科目名称の取得（相手）
	 * 
	 * @return AT_KURI_UKM_NAME 内訳科目名称
	 */
	public String getAT_KURI_UKM_NAME() {
		return AT_KURI_UKM_NAME;
	}

	/**
	 * KURI科目名称の設定（相手）
	 * 
	 * @param AT_KURI_UKM_NAME 内訳科目名称
	 */
	public void setAT_KURI_UKM_NAME(String AT_KURI_UKM_NAME) {
		this.AT_KURI_UKM_NAME = AT_KURI_UKM_NAME;
	}

	/**
	 * KURI科目の消費税名称の取得（相手）
	 * 
	 * @return AT_KURI_ZEI_NAME 科目の消費税名称
	 */
	public String getAT_KURI_ZEI_NAME() {
		return AT_KURI_ZEI_NAME;
	}

	/**
	 * KURI科目の消費税名称の設定（相手）
	 * 
	 * @param AT_KURI_ZEI_NAME 科目の消費税名称
	 */
	public void setAT_KURI_ZEI_NAME(String AT_KURI_ZEI_NAME) {
		this.AT_KURI_ZEI_NAME = AT_KURI_ZEI_NAME;
	}

	/**
	 * KURI科目の消費税名称の取得（相手）
	 * 
	 * @return AT_KURI_ZEI_NAME_S 科目の消費税名称
	 */
	public String getAT_KURI_ZEI_NAME_S() {
		return AT_KURI_ZEI_NAME_S;
	}

	/**
	 * KURI科目の消費税名称の設定（相手）
	 * 
	 * @param AT_KURI_ZEI_NAME_S 科目の消費税名称
	 */
	public void setAT_KURI_ZEI_NAME_S(String AT_KURI_ZEI_NAME_S) {
		this.AT_KURI_ZEI_NAME_S = AT_KURI_ZEI_NAME_S;
	}

	/**
	 * 科目略称の取得
	 * 
	 * @return KURI_KMK_NAME_S 科目略称
	 */
	public String getKURI_KMK_NAME_S() {
		return KURI_KMK_NAME_S;
	}

	/**
	 * 科目略称の設定
	 * 
	 * @param KURI_KMK_NAME_S 科目略称
	 */
	public void setKURI_KMK_NAME_S(String KURI_KMK_NAME_S) {
		this.KURI_KMK_NAME_S = KURI_KMK_NAME_S;
	}

	/**
	 * 科目名称の取得（相手）
	 * 
	 * @return KURI_HKM_NAME_S 科目名称
	 */
	public String getKURI_HKM_NAME_S() {
		return KURI_HKM_NAME_S;
	}

	/**
	 * 補助科目略称の設定
	 * 
	 * @param KURI_HKM_NAME_S 補助科目略称
	 */
	public void setKURI_HKM_NAME_S(String KURI_HKM_NAME_S) {
		this.KURI_HKM_NAME_S = KURI_HKM_NAME_S;
	}

	/**
	 * 補助科目略称の取得
	 * 
	 * @return KURI_HKM_NAME_S 補助科目略称
	 */
	public String getKURI_UKM_NAME_S() {
		return KURI_UKM_NAME_S;
	}

	/**
	 * 内訳科目略称の設定（相手）
	 * 
	 * @param KURI_UKM_NAME_S 内訳科目略称
	 */
	public void setKURI_UKM_NAME_S(String KURI_UKM_NAME_S) {
		this.KURI_UKM_NAME_S = KURI_UKM_NAME_S;
	}

	/**
	 * 科目名称の取得（相手）
	 * 
	 * @return AT_KURI_KMK_NAME_S 科目名称
	 */
	public String getAT_KURI_KMK_NAME_S() {
		return AT_KURI_KMK_NAME_S;
	}

	/**
	 * 科目略称の設定
	 * 
	 * @param AT_KURI_KMK_NAME_S 科目略称
	 */
	public void setAT_KURI_KMK_NAME_S(String AT_KURI_KMK_NAME_S) {
		this.AT_KURI_KMK_NAME_S = AT_KURI_KMK_NAME_S;
	}

	/**
	 * 補助科目名称の取得（相手）
	 * 
	 * @return AT_KURI_HKM_NAME_S 補助科目名称
	 */
	public String getAT_KURI_HKM_NAME_S() {
		return AT_KURI_HKM_NAME_S;
	}

	/**
	 * 補助科目名称の設定（相手）
	 * 
	 * @param AT_KURI_HKM_NAME_S 補助科目名称
	 */
	public void setAT_KURI_HKM_NAME_S(String AT_KURI_HKM_NAME_S) {
		this.AT_KURI_HKM_NAME_S = AT_KURI_HKM_NAME_S;
	}

	/**
	 * 内訳科目名称の取得（相手）
	 * 
	 * @return AT_KURI_UKM_NAME_S 内訳科目名称
	 */
	public String getAT_KURI_UKM_NAME_S() {
		return AT_KURI_UKM_NAME_S;
	}

	/**
	 * 内訳科目名称の設定（相手）
	 * 
	 * @param AT_KURI_UKM_NAME_S 内訳科目
	 */
	public void setAT_KURI_UKM_NAME_S(String AT_KURI_UKM_NAME_S) {
		this.AT_KURI_UKM_NAME_S = AT_KURI_UKM_NAME_S;
	}

	/**
	 * EST_KMK_CODE 科目コードの取得
	 * 
	 * @return EST_KMK_CODE 目コード
	 */
	public String getEST_KMK_CODE() {
		return EST_KMK_CODE;
	}

	/**
	 * EST_KMK_CODE 科目コードの設定
	 * 
	 * @param EST_KMK_CODE 科目コード
	 */
	public void setEST_KMK_CODE(String EST_KMK_CODE) {
		this.EST_KMK_CODE = EST_KMK_CODE;
	}

	/**
	 * EST補助科目コードの取得
	 * 
	 * @return EST_HKM_CODE 補助科目コード
	 */
	public String getEST_HKM_CODE() {
		return EST_HKM_CODE;
	}

	/**
	 * EST補助科目コードの設定
	 * 
	 * @param EST_HKM_CODE 補助科目コード
	 */
	public void setEST_HKM_CODE(String EST_HKM_CODE) {
		this.EST_HKM_CODE = EST_HKM_CODE;
	}

	/**
	 * AddCom内訳科目コードの取得
	 * 
	 * @return ADDCOM_UKM_CODE AddCom内訳科目コード
	 */
	public String getEST_UKM_CODE() {
		return EST_UKM_CODE;
	}

	/**
	 * EST内訳科目コードの設定
	 * 
	 * @param EST_UKM_CODE AddCom内訳科目コード
	 */
	public void setEST_UKM_CODE(String EST_UKM_CODE) {
		this.EST_UKM_CODE = EST_UKM_CODE;
	}

	/**
	 * EST科目の消費税コードの取得
	 * 
	 * @return EST_ZEI_CODE 目の消費税コード
	 */
	public String getEST_ZEI_CODE() {
		return EST_ZEI_CODE;
	}

	/**
	 * EST科目の消費税コードの設定
	 * 
	 * @param EST_ZEI_CODE 科目の消費税コード
	 */
	public void setEST_ZEI_CODE(String EST_ZEI_CODE) {
		this.EST_ZEI_CODE = EST_ZEI_CODE;
	}

	/**
	 * EST科目名称の取得
	 * 
	 * @return EST_KMK_NAME 科目名称
	 */
	public String getEST_KMK_NAME() {
		return EST_KMK_NAME;
	}

	/**
	 * EST科目名称の設定
	 * 
	 * @param EST_KMK_NAME 科目名称
	 */
	public void setEST_KMK_NAME(String EST_KMK_NAME) {
		this.EST_KMK_NAME = EST_KMK_NAME;
	}

	/**
	 * EST補助科目名称の取得
	 * 
	 * @return EST_HKM_NAME 補助科目名称
	 */
	public String getEST_HKM_NAME() {
		return EST_HKM_NAME;
	}

	/**
	 * EST補助科目名称の設定
	 * 
	 * @param EST_HKM_NAME 補助科目名称
	 */
	public void setEST_HKM_NAME(String EST_HKM_NAME) {
		this.EST_HKM_NAME = EST_HKM_NAME;
	}

	/**
	 * EST内訳科目名称の取得
	 * 
	 * @return EST_UKM_NAME 内訳科目名称
	 */
	public String getEST_UKM_NAME() {
		return EST_UKM_NAME;
	}

	/**
	 * EST科目名称の設定
	 * 
	 * @param EST_UKM_NAME 内訳科目名称
	 */
	public void setEST_UKM_NAME(String EST_UKM_NAME) {
		this.EST_UKM_NAME = EST_UKM_NAME;
	}

	/**
	 * EST科目の消費税名称の取得
	 * 
	 * @return EST_ZEI_NAME 科目の消費税名称
	 */
	public String getEST_ZEI_NAME() {
		return EST_ZEI_NAME;
	}

	/**
	 * EST科目の消費税名称の設定
	 * 
	 * @param EST_ZEI_NAME 科目の消費税名称
	 */
	public void setEST_ZEI_NAME(String EST_ZEI_NAME) {
		this.EST_ZEI_NAME = EST_ZEI_NAME;
	}

	/**
	 * EST科目の消費税名称の取得
	 * 
	 * @return EST_ZEI_NAME 科目の消費税名称
	 */
	public String getEST_ZEI_NAME_S() {
		return EST_ZEI_NAME_S;
	}

	/**
	 * EST科目の消費税名称の設定
	 * 
	 * @param EST_ZEI_NAME_S 科目の消費税名称
	 */
	public void setEST_ZEI_NAME_S(String EST_ZEI_NAME_S) {
		this.EST_ZEI_NAME_S = EST_ZEI_NAME_S;
	}

	/**
	 * AT_EST_KMK_CODE 科目コードの取得（相手）
	 * 
	 * @return AT_EST_KMK_CODE 目コード
	 */
	public String getAT_EST_KMK_CODE() {
		return AT_EST_KMK_CODE;
	}

	/**
	 * AT_EST_KMK_CODE 科目コードの設定（相手）
	 * 
	 * @param AT_EST_KMK_CODE 科目コード
	 */
	public void setAT_EST_KMK_CODE(String AT_EST_KMK_CODE) {
		this.AT_EST_KMK_CODE = AT_EST_KMK_CODE;
	}

	/**
	 * EST補助科目コードの取得（相手）
	 * 
	 * @return AT_EST_HKM_CODE 補助科目コード
	 */
	public String getAT_EST_HKM_CODE() {
		return AT_EST_HKM_CODE;
	}

	/**
	 * EST補助科目コードの設定（相手）
	 * 
	 * @param AT_EST_HKM_CODE 補助科目コード
	 */
	public void setAT_EST_HKM_CODE(String AT_EST_HKM_CODE) {
		this.AT_EST_HKM_CODE = AT_EST_HKM_CODE;
	}

	/**
	 * EST内訳科目コードの取得（相手）
	 * 
	 * @return AT_EST_UKM_CODE 内訳科目コード
	 */
	public String getAT_EST_UKM_CODE() {
		return AT_EST_UKM_CODE;
	}

	/**
	 * EST内訳科目コードの設定（相手）
	 * 
	 * @param AT_EST_UKM_CODE 内訳科目コード
	 */
	public void setAT_EST_UKM_CODE(String AT_EST_UKM_CODE) {
		this.AT_EST_UKM_CODE = AT_EST_UKM_CODE;
	}

	/**
	 * EST科目の消費税コードの取得（相手）
	 * 
	 * @return AT_EST_ZEI_CODE 目の消費税コード
	 */
	public String getAT_EST_ZEI_CODE() {
		return AT_EST_ZEI_CODE;
	}

	/**
	 * EST科目の消費税コードの設定（相手）
	 * 
	 * @param AT_EST_ZEI_CODE 科目の消費税コード
	 */
	public void setAT_EST_ZEI_CODE(String AT_EST_ZEI_CODE) {
		this.AT_EST_ZEI_CODE = AT_EST_ZEI_CODE;
	}

	/**
	 * EST科目名称の取得（相手）
	 * 
	 * @return AT_EST_KMK_NAME 科目名称
	 */
	public String getAT_EST_KMK_NAME() {
		return AT_EST_KMK_NAME;
	}

	/**
	 * EST科目名称の設定（相手）
	 * 
	 * @param AT_EST_KMK_NAME 科目名称
	 */
	public void setAT_EST_KMK_NAME(String AT_EST_KMK_NAME) {
		this.AT_EST_KMK_NAME = AT_EST_KMK_NAME;
	}

	/**
	 * EST補助科目名称の取得（相手）
	 * 
	 * @return EST_HKM_NAME 補助科目名称
	 */
	public String getAT_EST_HKM_NAME() {
		return AT_EST_HKM_NAME;
	}

	/**
	 * EST補助科目名称の設定（相手）
	 * 
	 * @param AT_EST_HKM_NAME 補助科目名称
	 */
	public void setAT_EST_HKM_NAME(String AT_EST_HKM_NAME) {
		this.AT_EST_HKM_NAME = AT_EST_HKM_NAME;
	}

	/**
	 * EST内訳科目名称の取得（相手）
	 * 
	 * @return AT_EST_UKM_NAME 内訳科目名称
	 */
	public String getAT_EST_UKM_NAME() {
		return AT_EST_UKM_NAME;
	}

	/**
	 * EST科目名称の設定（相手）
	 * 
	 * @param AT_EST_UKM_NAME 内訳科目名称
	 */
	public void setAT_EST_UKM_NAME(String AT_EST_UKM_NAME) {
		this.AT_EST_UKM_NAME = AT_EST_UKM_NAME;
	}

	/**
	 * EST科目の消費税名称の取得（相手）
	 * 
	 * @return AT_EST_ZEI_NAME 科目の消費税名称
	 */
	public String getAT_EST_ZEI_NAME() {
		return AT_EST_ZEI_NAME;
	}

	/**
	 * EST科目の消費税名称の設定（相手）
	 * 
	 * @param AT_EST_ZEI_NAME 科目の消費税名称
	 */
	public void setAT_EST_ZEI_NAME(String AT_EST_ZEI_NAME) {
		this.AT_EST_ZEI_NAME = AT_EST_ZEI_NAME;
	}

	/**
	 * EST科目の消費税名称の取得（相手）
	 * 
	 * @return AT_EST_ZEI_NAME_S 科目の消費税名称
	 */
	public String getAT_EST_ZEI_NAME_S() {
		return AT_EST_ZEI_NAME_S;
	}

	/**
	 * EST科目の消費税名称の設定（相手）
	 * 
	 * @param AT_EST_ZEI_NAME_S 科目の消費税名称
	 */
	public void setAT_EST_ZEI_NAME_S(String AT_EST_ZEI_NAME_S) {
		this.AT_EST_ZEI_NAME_S = AT_EST_ZEI_NAME_S;
	}

	/**
	 * 科目略称の取得
	 * 
	 * @return EST_KMK_NAME_S 科目略称
	 */
	public String getEST_KMK_NAME_S() {
		return EST_KMK_NAME_S;
	}

	/**
	 * 科目略称の設定
	 * 
	 * @param EST_KMK_NAME_S 科目略称
	 */
	public void setEST_KMK_NAME_S(String EST_KMK_NAME_S) {
		this.EST_KMK_NAME_S = EST_KMK_NAME_S;
	}

	/**
	 * 科目名称の取得（相手）
	 * 
	 * @return EST_HKM_NAME_S 科目名称
	 */
	public String getEST_HKM_NAME_S() {
		return EST_HKM_NAME_S;
	}

	/**
	 * 補助科目略称の設定
	 * 
	 * @param EST_HKM_NAME_S 補助科目略称
	 */
	public void setEST_HKM_NAME_S(String EST_HKM_NAME_S) {
		this.EST_HKM_NAME_S = EST_HKM_NAME_S;
	}

	/**
	 * 補助科目略称の取得
	 * 
	 * @return EST_HKM_NAME_S 補助科目略称
	 */
	public String getEST_UKM_NAME_S() {
		return EST_UKM_NAME_S;
	}

	/**
	 * 内訳科目略称の設定（相手）
	 * 
	 * @param EST_UKM_NAME_S 内訳科目略称
	 */
	public void setEST_UKM_NAME_S(String EST_UKM_NAME_S) {
		this.EST_UKM_NAME_S = EST_UKM_NAME_S;
	}

	/**
	 * 科目名称の取得（相手）
	 * 
	 * @return AT_EST_KMK_NAME_S 科目名称
	 */
	public String getAT_EST_KMK_NAME_S() {
		return AT_EST_KMK_NAME_S;
	}

	/**
	 * 科目略称の設定
	 * 
	 * @param AT_EST_KMK_NAME_S 科目略称
	 */
	public void setAT_EST_KMK_NAME_S(String AT_EST_KMK_NAME_S) {
		this.AT_EST_KMK_NAME_S = AT_EST_KMK_NAME_S;
	}

	/**
	 * 補助科目名称の取得（相手）
	 * 
	 * @return AT_EST_HKM_NAME_S 補助科目名称
	 */
	public String getAT_EST_HKM_NAME_S() {
		return AT_EST_HKM_NAME_S;
	}

	/**
	 * 補助科目名称の設定（相手）
	 * 
	 * @param AT_EST_HKM_NAME_S 補助科目名称
	 */
	public void setAT_EST_HKM_NAME_S(String AT_EST_HKM_NAME_S) {
		this.AT_EST_HKM_NAME_S = AT_EST_HKM_NAME_S;
	}

	/**
	 * 内訳科目名称の取得（相手）
	 * 
	 * @return AT_EST_UKM_NAME_S 内訳科目名称
	 */
	public String getAT_EST_UKM_NAME_S() {
		return AT_EST_UKM_NAME_S;
	}

	/**
	 * 内訳科目名称の設定（相手）
	 * 
	 * @param AT_EST_UKM_NAME_S 内訳科目
	 */
	public void setAT_EST_UKM_NAME_S(String AT_EST_UKM_NAME_S) {
		this.AT_EST_UKM_NAME_S = AT_EST_UKM_NAME_S;
	}

	/**
	 * SAアイテムコードを取得します。
	 * 
	 * @return SAアイテムコード
	 */
	public String getCode() {
		return getITEM_CODE();
	}

	/**
	 * SAアイテムコードを設定します。
	 * 
	 * @param code SAアイテムコード
	 */
	public void setCode(String code) {
		setITEM_CODE(code);
	}

	/**
	 * SAアイテム名称を取得します。
	 * 
	 * @return SAアイテム名称
	 */
	public String getName() {
		return getITEM_NAME();
	}

	/**
	 * SAアイテム名称を設定します。
	 * 
	 * @param name SAアイテム名称
	 */
	public void setName(String name) {
		setITEM_NAME(name);
	}

	/**
	 * アイテム略称を取得します。
	 * 
	 * @return SAアイテム略称
	 */
	public String getNames() {
		return getITEM_NAME_S();
	}

	/**
	 * アイテム略称を設定する。
	 * 
	 * @param names SAアイテム略称
	 */
	public void setNames(String names) {
		setITEM_NAME_S(names);
	}

	/**
	 * アイテム検索名称を取得します。
	 * 
	 * @return SAアイテム検索名称
	 */
	public String getNamek() {
		return getITEM_NAME_K();
	}

	/**
	 * アイテム検索名称を設定する。
	 * 
	 * @param namek SAアイテム検索名称
	 */
	public void setNamek(String namek) {
		setITEM_NAME_K(namek);
	}

	/**
	 * 開始日を戻します。
	 * 
	 * @return 開始日
	 */
	public Date getDateFrom() {
		return STR_DATE;
	}

	/**
	 * 終了日を戻します。
	 * 
	 * @return 終了日
	 */
	public Date getDateTo() {
		return END_DATE;
	}

	/**
	 * Return value for BNKR_TYPE_CODE
	 * 
	 * @return BNKR_TYPE_CODE
	 */
	public String getBNKR_TYPE_CODE() {
		return BNKR_TYPE_CODE;
	}

	/**
	 * Set value for bNKR_TYPE_CODE
	 * 
	 * @param bNKR_TYPE_CODE
	 */
	public void setBNKR_TYPE_CODE(String bNKR_TYPE_CODE) {
		BNKR_TYPE_CODE = bNKR_TYPE_CODE;
	}

	/**
	 * VC/VR変換アイテムコードの取得
	 * 
	 * @return VCVR_ITEM_CODE VC/VR変換アイテムコード
	 */
	public String getVCVR_ITEM_CODE() {
		return VCVR_ITEM_CODE;
	}

	/**
	 * VC/VR変換アイテムコードの設定
	 * 
	 * @param VCVR_ITEM_CODE VC/VR変換アイテムコード
	 */
	public void setVCVR_ITEM_CODE(String VCVR_ITEM_CODE) {
		this.VCVR_ITEM_CODE = VCVR_ITEM_CODE;
	}

	/**
	 * 収益借方①科目コードの取得
	 * 
	 * @return NEW_ACC_DR_KMK_CODE 収益借方①科目コード
	 */
	public String getNEW_ACC_DR_KMK_CODE() {
		return NEW_ACC_DR_KMK_CODE;
	}

	/**
	 * 収益借方①科目コードの設定
	 * 
	 * @param NEW_ACC_DR_KMK_CODE 収益借方①科目コード
	 */
	public void setNEW_ACC_DR_KMK_CODE(String NEW_ACC_DR_KMK_CODE) {
		this.NEW_ACC_DR_KMK_CODE = NEW_ACC_DR_KMK_CODE;
	}

	/**
	 * 収益借方①補助科目コードの取得
	 * 
	 * @return NEW_ACC_DR_HKM_CODE 収益借方①補助科目コード
	 */
	public String getNEW_ACC_DR_HKM_CODE() {
		return NEW_ACC_DR_HKM_CODE;
	}

	/**
	 * 収益借方①補助科目コードの設定
	 * 
	 * @param NEW_ACC_DR_HKM_CODE 収益借方①補助科目コード
	 */
	public void setNEW_ACC_DR_HKM_CODE(String NEW_ACC_DR_HKM_CODE) {
		this.NEW_ACC_DR_HKM_CODE = NEW_ACC_DR_HKM_CODE;
	}

	/**
	 * 収益借方①内訳科目コードの取得
	 * 
	 * @return NEW_ACC_DR_UKM_CODE 収益借方①内訳科目コード
	 */
	public String getNEW_ACC_DR_UKM_CODE() {
		return NEW_ACC_DR_UKM_CODE;
	}

	/**
	 * 収益借方①内訳科目コードの設定
	 * 
	 * @param NEW_ACC_DR_UKM_CODE 収益借方①内訳科目コード
	 */
	public void setNEW_ACC_DR_UKM_CODE(String NEW_ACC_DR_UKM_CODE) {
		this.NEW_ACC_DR_UKM_CODE = NEW_ACC_DR_UKM_CODE;
	}

	/**
	 * 収益借方①消費税コードの取得
	 * 
	 * @return NEW_ACC_DR_ZEI_CODE 収益借方①消費税コード
	 */
	public String getNEW_ACC_DR_ZEI_CODE() {
		return NEW_ACC_DR_ZEI_CODE;
	}

	/**
	 * 収益借方①消費税コードの設定
	 * 
	 * @param NEW_ACC_DR_ZEI_CODE 収益借方①消費税コード
	 */
	public void setNEW_ACC_DR_ZEI_CODE(String NEW_ACC_DR_ZEI_CODE) {
		this.NEW_ACC_DR_ZEI_CODE = NEW_ACC_DR_ZEI_CODE;
	}

	/**
	 * 収益借方①科目名称の取得
	 * 
	 * @return NEW_ACC_DR_KMK_NAME 収益借方①科目名称
	 */
	public String getNEW_ACC_DR_KMK_NAME() {
		return NEW_ACC_DR_KMK_NAME;
	}

	/**
	 * 収益借方①科目名称の設定
	 * 
	 * @param NEW_ACC_DR_KMK_NAME 収益借方①科目名称
	 */
	public void setNEW_ACC_DR_KMK_NAME(String NEW_ACC_DR_KMK_NAME) {
		this.NEW_ACC_DR_KMK_NAME = NEW_ACC_DR_KMK_NAME;
	}

	/**
	 * 収益借方①補助科目名称の取得
	 * 
	 * @return NEW_ACC_DR_HKM_NAME 収益借方①補助科目名称
	 */
	public String getNEW_ACC_DR_HKM_NAME() {
		return NEW_ACC_DR_HKM_NAME;
	}

	/**
	 * 収益借方①補助科目名称の設定
	 * 
	 * @param NEW_ACC_DR_HKM_NAME 収益借方①補助科目名称
	 */
	public void setNEW_ACC_DR_HKM_NAME(String NEW_ACC_DR_HKM_NAME) {
		this.NEW_ACC_DR_HKM_NAME = NEW_ACC_DR_HKM_NAME;
	}

	/**
	 * 収益借方①内訳科目名称の取得
	 * 
	 * @return NEW_ACC_DR_UKM_NAME 収益借方①内訳科目名称
	 */
	public String getNEW_ACC_DR_UKM_NAME() {
		return NEW_ACC_DR_UKM_NAME;
	}

	/**
	 * 収益借方①内訳科目名称の設定
	 * 
	 * @param NEW_ACC_DR_UKM_NAME 収益借方①内訳科目名称
	 */
	public void setNEW_ACC_DR_UKM_NAME(String NEW_ACC_DR_UKM_NAME) {
		this.NEW_ACC_DR_UKM_NAME = NEW_ACC_DR_UKM_NAME;
	}

	/**
	 * 収益借方①消費税名称の取得
	 * 
	 * @return NEW_ACC_DR_ZEI_NAME 収益借方①消費税名称
	 */
	public String getNEW_ACC_DR_ZEI_NAME() {
		return NEW_ACC_DR_ZEI_NAME;
	}

	/**
	 * 収益借方①消費税名称の設定
	 * 
	 * @param NEW_ACC_DR_ZEI_NAME 収益借方①消費税名称
	 */
	public void setNEW_ACC_DR_ZEI_NAME(String NEW_ACC_DR_ZEI_NAME) {
		this.NEW_ACC_DR_ZEI_NAME = NEW_ACC_DR_ZEI_NAME;
	}

	/**
	 * 収益借方①科目の取得
	 * 
	 * @return NEW_ACC_DR_KMK_NAME_S 収益借方①科目
	 */
	public String getNEW_ACC_DR_KMK_NAME_S() {
		return NEW_ACC_DR_KMK_NAME_S;
	}

	/**
	 * 収益借方①科目の設定
	 * 
	 * @param NEW_ACC_DR_KMK_NAME_S 収益借方①科目
	 */
	public void setNEW_ACC_DR_KMK_NAME_S(String NEW_ACC_DR_KMK_NAME_S) {
		this.NEW_ACC_DR_KMK_NAME_S = NEW_ACC_DR_KMK_NAME_S;
	}

	/**
	 * 収益借方①補助科目略称の取得
	 * 
	 * @return NEW_ACC_DR_HKM_NAME_S 収益借方①補助科目略称
	 */
	public String getNEW_ACC_DR_HKM_NAME_S() {
		return NEW_ACC_DR_HKM_NAME_S;
	}

	/**
	 * 収益借方①補助科目略称の設定
	 * 
	 * @param NEW_ACC_DR_HKM_NAME_S 収益借方①補助科目略称
	 */
	public void setNEW_ACC_DR_HKM_NAME_S(String NEW_ACC_DR_HKM_NAME_S) {
		this.NEW_ACC_DR_HKM_NAME_S = NEW_ACC_DR_HKM_NAME_S;
	}

	/**
	 * 収益借方①内訳科目略称の取得
	 * 
	 * @return NEW_ACC_DR_UKM_NAME_S 収益借方①内訳科目略称
	 */
	public String getNEW_ACC_DR_UKM_NAME_S() {
		return NEW_ACC_DR_UKM_NAME_S;
	}

	/**
	 * 収益借方①内訳科目略称の設定
	 * 
	 * @param NEW_ACC_DR_UKM_NAME_S 収益借方①内訳科目略称
	 */
	public void setNEW_ACC_DR_UKM_NAME_S(String NEW_ACC_DR_UKM_NAME_S) {
		this.NEW_ACC_DR_UKM_NAME_S = NEW_ACC_DR_UKM_NAME_S;
	}

	/**
	 * 収益借方①消費税略称の取得
	 * 
	 * @return NEW_ACC_DR_ZEI_NAME_S 収益借方①消費税略称
	 */
	public String getNEW_ACC_DR_ZEI_NAME_S() {
		return NEW_ACC_DR_ZEI_NAME_S;
	}

	/**
	 * 収益借方①消費税略称の設定
	 * 
	 * @param NEW_ACC_DR_ZEI_NAME_S 収益借方①消費税略称
	 */
	public void setNEW_ACC_DR_ZEI_NAME_S(String NEW_ACC_DR_ZEI_NAME_S) {
		this.NEW_ACC_DR_ZEI_NAME_S = NEW_ACC_DR_ZEI_NAME_S;
	}

	/**
	 * 収益貸方①科目コードの取得
	 * 
	 * @return NEW_ACC_CR_KMK_CODE 収益貸方①科目コード
	 */
	public String getNEW_ACC_CR_KMK_CODE() {
		return NEW_ACC_CR_KMK_CODE;
	}

	/**
	 * 収益貸方①科目コードの設定
	 * 
	 * @param NEW_ACC_CR_KMK_CODE 収益貸方①科目コード
	 */
	public void setNEW_ACC_CR_KMK_CODE(String NEW_ACC_CR_KMK_CODE) {
		this.NEW_ACC_CR_KMK_CODE = NEW_ACC_CR_KMK_CODE;
	}

	/**
	 * 収益貸方①補助科目コードの取得
	 * 
	 * @return NEW_ACC_CR_HKM_CODE 収益貸方①補助科目コード
	 */
	public String getNEW_ACC_CR_HKM_CODE() {
		return NEW_ACC_CR_HKM_CODE;
	}

	/**
	 * 収益貸方①補助科目コードの設定
	 * 
	 * @param NEW_ACC_CR_HKM_CODE 収益貸方①補助科目コード
	 */
	public void setNEW_ACC_CR_HKM_CODE(String NEW_ACC_CR_HKM_CODE) {
		this.NEW_ACC_CR_HKM_CODE = NEW_ACC_CR_HKM_CODE;
	}

	/**
	 * 収益貸方①内訳科目コードの取得
	 * 
	 * @return NEW_ACC_CR_UKM_CODE 収益貸方①内訳科目コード
	 */
	public String getNEW_ACC_CR_UKM_CODE() {
		return NEW_ACC_CR_UKM_CODE;
	}

	/**
	 * 収益貸方①内訳科目コードの設定
	 * 
	 * @param NEW_ACC_CR_UKM_CODE 収益貸方①内訳科目コード
	 */
	public void setNEW_ACC_CR_UKM_CODE(String NEW_ACC_CR_UKM_CODE) {
		this.NEW_ACC_CR_UKM_CODE = NEW_ACC_CR_UKM_CODE;
	}

	/**
	 * 収益貸方①消費税コードの取得
	 * 
	 * @return NEW_ACC_CR_ZEI_CODE 収益貸方①消費税コード
	 */
	public String getNEW_ACC_CR_ZEI_CODE() {
		return NEW_ACC_CR_ZEI_CODE;
	}

	/**
	 * 収益貸方①消費税コードの設定
	 * 
	 * @param NEW_ACC_CR_ZEI_CODE 収益貸方①消費税コード
	 */
	public void setNEW_ACC_CR_ZEI_CODE(String NEW_ACC_CR_ZEI_CODE) {
		this.NEW_ACC_CR_ZEI_CODE = NEW_ACC_CR_ZEI_CODE;
	}

	/**
	 * 収益貸方①科目名称の取得
	 * 
	 * @return NEW_ACC_CR_KMK_NAME 収益貸方①科目名称
	 */
	public String getNEW_ACC_CR_KMK_NAME() {
		return NEW_ACC_CR_KMK_NAME;
	}

	/**
	 * 収益貸方①科目名称の設定
	 * 
	 * @param NEW_ACC_CR_KMK_NAME 収益貸方①科目名称
	 */
	public void setNEW_ACC_CR_KMK_NAME(String NEW_ACC_CR_KMK_NAME) {
		this.NEW_ACC_CR_KMK_NAME = NEW_ACC_CR_KMK_NAME;
	}

	/**
	 * 収益貸方①補助科目名称の取得
	 * 
	 * @return NEW_ACC_CR_HKM_NAME 収益貸方①補助科目名称
	 */
	public String getNEW_ACC_CR_HKM_NAME() {
		return NEW_ACC_CR_HKM_NAME;
	}

	/**
	 * 収益貸方①補助科目名称の設定
	 * 
	 * @param NEW_ACC_CR_HKM_NAME 収益貸方①補助科目名称
	 */
	public void setNEW_ACC_CR_HKM_NAME(String NEW_ACC_CR_HKM_NAME) {
		this.NEW_ACC_CR_HKM_NAME = NEW_ACC_CR_HKM_NAME;
	}

	/**
	 * 収益貸方①内訳科目名称の取得
	 * 
	 * @return NEW_ACC_CR_UKM_NAME 収益貸方①内訳科目名称
	 */
	public String getNEW_ACC_CR_UKM_NAME() {
		return NEW_ACC_CR_UKM_NAME;
	}

	/**
	 * 収益貸方①内訳科目名称の設定
	 * 
	 * @param NEW_ACC_CR_UKM_NAME 収益貸方①内訳科目名称
	 */
	public void setNEW_ACC_CR_UKM_NAME(String NEW_ACC_CR_UKM_NAME) {
		this.NEW_ACC_CR_UKM_NAME = NEW_ACC_CR_UKM_NAME;
	}

	/**
	 * 収益貸方①消費税名称の取得
	 * 
	 * @return NEW_ACC_CR_ZEI_NAME 収益貸方①消費税名称
	 */
	public String getNEW_ACC_CR_ZEI_NAME() {
		return NEW_ACC_CR_ZEI_NAME;
	}

	/**
	 * 収益貸方①消費税名称の設定
	 * 
	 * @param NEW_ACC_CR_ZEI_NAME 収益貸方①消費税名称
	 */
	public void setNEW_ACC_CR_ZEI_NAME(String NEW_ACC_CR_ZEI_NAME) {
		this.NEW_ACC_CR_ZEI_NAME = NEW_ACC_CR_ZEI_NAME;
	}

	/**
	 * 収益貸方①科目の取得
	 * 
	 * @return NEW_ACC_CR_KMK_NAME_S 収益貸方①科目
	 */
	public String getNEW_ACC_CR_KMK_NAME_S() {
		return NEW_ACC_CR_KMK_NAME_S;
	}

	/**
	 * 収益貸方①科目の設定
	 * 
	 * @param NEW_ACC_CR_KMK_NAME_S 収益貸方①科目
	 */
	public void setNEW_ACC_CR_KMK_NAME_S(String NEW_ACC_CR_KMK_NAME_S) {
		this.NEW_ACC_CR_KMK_NAME_S = NEW_ACC_CR_KMK_NAME_S;
	}

	/**
	 * 収益貸方①補助科目略称の取得
	 * 
	 * @return NEW_ACC_CR_HKM_NAME_S 収益貸方①補助科目略称
	 */
	public String getNEW_ACC_CR_HKM_NAME_S() {
		return NEW_ACC_CR_HKM_NAME_S;
	}

	/**
	 * 収益貸方①補助科目略称の設定
	 * 
	 * @param NEW_ACC_CR_HKM_NAME_S 収益貸方①補助科目略称
	 */
	public void setNEW_ACC_CR_HKM_NAME_S(String NEW_ACC_CR_HKM_NAME_S) {
		this.NEW_ACC_CR_HKM_NAME_S = NEW_ACC_CR_HKM_NAME_S;
	}

	/**
	 * 収益貸方①内訳科目略称の取得
	 * 
	 * @return NEW_ACC_CR_UKM_NAME_S 収益貸方①内訳科目略称
	 */
	public String getNEW_ACC_CR_UKM_NAME_S() {
		return NEW_ACC_CR_UKM_NAME_S;
	}

	/**
	 * 収益貸方①内訳科目略称の設定
	 * 
	 * @param NEW_ACC_CR_UKM_NAME_S 収益貸方①内訳科目略称
	 */
	public void setNEW_ACC_CR_UKM_NAME_S(String NEW_ACC_CR_UKM_NAME_S) {
		this.NEW_ACC_CR_UKM_NAME_S = NEW_ACC_CR_UKM_NAME_S;
	}

	/**
	 * 収益貸方①消費税略称の取得
	 * 
	 * @return NEW_ACC_CR_ZEI_NAME_S 収益貸方①消費税略称
	 */
	public String getNEW_ACC_CR_ZEI_NAME_S() {
		return NEW_ACC_CR_ZEI_NAME_S;
	}

	/**
	 * 収益貸方①消費税略称の設定
	 * 
	 * @param NEW_ACC_CR_ZEI_NAME_S 収益貸方①消費税略称
	 */
	public void setNEW_ACC_CR_ZEI_NAME_S(String NEW_ACC_CR_ZEI_NAME_S) {
		this.NEW_ACC_CR_ZEI_NAME_S = NEW_ACC_CR_ZEI_NAME_S;
	}

	/**
	 * 収益借方②科目コードの取得
	 * 
	 * @return NEW_ACC_DR_2_KMK_CODE 収益借方②科目コード
	 */
	public String getNEW_ACC_DR_2_KMK_CODE() {
		return NEW_ACC_DR_2_KMK_CODE;
	}

	/**
	 * 収益借方②科目コードの設定
	 * 
	 * @param NEW_ACC_DR_2_KMK_CODE 収益借方②科目コード
	 */
	public void setNEW_ACC_DR_2_KMK_CODE(String NEW_ACC_DR_2_KMK_CODE) {
		this.NEW_ACC_DR_2_KMK_CODE = NEW_ACC_DR_2_KMK_CODE;
	}

	/**
	 * 収益借方②補助科目コードの取得
	 * 
	 * @return NEW_ACC_DR_2_HKM_CODE 収益借方②補助科目コード
	 */
	public String getNEW_ACC_DR_2_HKM_CODE() {
		return NEW_ACC_DR_2_HKM_CODE;
	}

	/**
	 * 収益借方②補助科目コードの設定
	 * 
	 * @param NEW_ACC_DR_2_HKM_CODE 収益借方②補助科目コード
	 */
	public void setNEW_ACC_DR_2_HKM_CODE(String NEW_ACC_DR_2_HKM_CODE) {
		this.NEW_ACC_DR_2_HKM_CODE = NEW_ACC_DR_2_HKM_CODE;
	}

	/**
	 * 収益借方②内訳科目コードの取得
	 * 
	 * @return NEW_ACC_DR_2_UKM_CODE 収益借方②内訳科目コード
	 */
	public String getNEW_ACC_DR_2_UKM_CODE() {
		return NEW_ACC_DR_2_UKM_CODE;
	}

	/**
	 * 収益借方②内訳科目コードの設定
	 * 
	 * @param NEW_ACC_DR_2_UKM_CODE 収益借方②内訳科目コード
	 */
	public void setNEW_ACC_DR_2_UKM_CODE(String NEW_ACC_DR_2_UKM_CODE) {
		this.NEW_ACC_DR_2_UKM_CODE = NEW_ACC_DR_2_UKM_CODE;
	}

	/**
	 * 収益借方②消費税コードの取得
	 * 
	 * @return NEW_ACC_DR_2_ZEI_CODE 収益借方②消費税コード
	 */
	public String getNEW_ACC_DR_2_ZEI_CODE() {
		return NEW_ACC_DR_2_ZEI_CODE;
	}

	/**
	 * 収益借方②消費税コードの設定
	 * 
	 * @param NEW_ACC_DR_2_ZEI_CODE 収益借方②消費税コード
	 */
	public void setNEW_ACC_DR_2_ZEI_CODE(String NEW_ACC_DR_2_ZEI_CODE) {
		this.NEW_ACC_DR_2_ZEI_CODE = NEW_ACC_DR_2_ZEI_CODE;
	}

	/**
	 * 収益借方②科目名称の取得
	 * 
	 * @return NEW_ACC_DR_2_KMK_NAME 収益借方②科目名称
	 */
	public String getNEW_ACC_DR_2_KMK_NAME() {
		return NEW_ACC_DR_2_KMK_NAME;
	}

	/**
	 * 収益借方②科目名称の設定
	 * 
	 * @param NEW_ACC_DR_2_KMK_NAME 収益借方②科目名称
	 */
	public void setNEW_ACC_DR_2_KMK_NAME(String NEW_ACC_DR_2_KMK_NAME) {
		this.NEW_ACC_DR_2_KMK_NAME = NEW_ACC_DR_2_KMK_NAME;
	}

	/**
	 * 収益借方②補助科目名称の取得
	 * 
	 * @return NEW_ACC_DR_2_HKM_NAME 収益借方②補助科目名称
	 */
	public String getNEW_ACC_DR_2_HKM_NAME() {
		return NEW_ACC_DR_2_HKM_NAME;
	}

	/**
	 * 収益借方②補助科目名称の設定
	 * 
	 * @param NEW_ACC_DR_2_HKM_NAME 収益借方②補助科目名称
	 */
	public void setNEW_ACC_DR_2_HKM_NAME(String NEW_ACC_DR_2_HKM_NAME) {
		this.NEW_ACC_DR_2_HKM_NAME = NEW_ACC_DR_2_HKM_NAME;
	}

	/**
	 * 収益借方②内訳科目名称の取得
	 * 
	 * @return NEW_ACC_DR_2_UKM_NAME 収益借方②内訳科目名称
	 */
	public String getNEW_ACC_DR_2_UKM_NAME() {
		return NEW_ACC_DR_2_UKM_NAME;
	}

	/**
	 * 収益借方②内訳科目名称の設定
	 * 
	 * @param NEW_ACC_DR_2_UKM_NAME 収益借方②内訳科目名称
	 */
	public void setNEW_ACC_DR_2_UKM_NAME(String NEW_ACC_DR_2_UKM_NAME) {
		this.NEW_ACC_DR_2_UKM_NAME = NEW_ACC_DR_2_UKM_NAME;
	}

	/**
	 * 収益借方②消費税名称の取得
	 * 
	 * @return NEW_ACC_DR_2_ZEI_NAME 収益借方②消費税名称
	 */
	public String getNEW_ACC_DR_2_ZEI_NAME() {
		return NEW_ACC_DR_2_ZEI_NAME;
	}

	/**
	 * 収益借方②消費税名称の設定
	 * 
	 * @param NEW_ACC_DR_2_ZEI_NAME 収益借方②消費税名称
	 */
	public void setNEW_ACC_DR_2_ZEI_NAME(String NEW_ACC_DR_2_ZEI_NAME) {
		this.NEW_ACC_DR_2_ZEI_NAME = NEW_ACC_DR_2_ZEI_NAME;
	}

	/**
	 * 収益借方②科目の取得
	 * 
	 * @return NEW_ACC_DR_2_KMK_NAME_S 収益借方②科目
	 */
	public String getNEW_ACC_DR_2_KMK_NAME_S() {
		return NEW_ACC_DR_2_KMK_NAME_S;
	}

	/**
	 * 収益借方②科目の設定
	 * 
	 * @param NEW_ACC_DR_2_KMK_NAME_S 収益借方②科目
	 */
	public void setNEW_ACC_DR_2_KMK_NAME_S(String NEW_ACC_DR_2_KMK_NAME_S) {
		this.NEW_ACC_DR_2_KMK_NAME_S = NEW_ACC_DR_2_KMK_NAME_S;
	}

	/**
	 * 収益借方②補助科目略称の取得
	 * 
	 * @return NEW_ACC_DR_2_HKM_NAME_S 収益借方②補助科目略称
	 */
	public String getNEW_ACC_DR_2_HKM_NAME_S() {
		return NEW_ACC_DR_2_HKM_NAME_S;
	}

	/**
	 * 収益借方②補助科目略称の設定
	 * 
	 * @param NEW_ACC_DR_2_HKM_NAME_S 収益借方②補助科目略称
	 */
	public void setNEW_ACC_DR_2_HKM_NAME_S(String NEW_ACC_DR_2_HKM_NAME_S) {
		this.NEW_ACC_DR_2_HKM_NAME_S = NEW_ACC_DR_2_HKM_NAME_S;
	}

	/**
	 * 収益借方②内訳科目略称の取得
	 * 
	 * @return NEW_ACC_DR_2_UKM_NAME_S 収益借方②内訳科目略称
	 */
	public String getNEW_ACC_DR_2_UKM_NAME_S() {
		return NEW_ACC_DR_2_UKM_NAME_S;
	}

	/**
	 * 収益借方②内訳科目略称の設定
	 * 
	 * @param NEW_ACC_DR_2_UKM_NAME_S 収益借方②内訳科目略称
	 */
	public void setNEW_ACC_DR_2_UKM_NAME_S(String NEW_ACC_DR_2_UKM_NAME_S) {
		this.NEW_ACC_DR_2_UKM_NAME_S = NEW_ACC_DR_2_UKM_NAME_S;
	}

	/**
	 * 収益借方②消費税略称の取得
	 * 
	 * @return NEW_ACC_DR_2_ZEI_NAME_S 収益借方②消費税略称
	 */
	public String getNEW_ACC_DR_2_ZEI_NAME_S() {
		return NEW_ACC_DR_2_ZEI_NAME_S;
	}

	/**
	 * 収益借方②消費税略称の設定
	 * 
	 * @param NEW_ACC_DR_2_ZEI_NAME_S 収益借方②消費税略称
	 */
	public void setNEW_ACC_DR_2_ZEI_NAME_S(String NEW_ACC_DR_2_ZEI_NAME_S) {
		this.NEW_ACC_DR_2_ZEI_NAME_S = NEW_ACC_DR_2_ZEI_NAME_S;
	}

}
