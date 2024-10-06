package jp.co.ais.trans2.model.cargo;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.model.*;

/**
 * CARGO情報
 */
public class Cargo extends TransferBase implements AutoCompletable, FilterableEntity {

	/**
	 * @return インクリメントサーチ表示値
	 */
	public String getDisplayText() {
		if (!Util.isNullOrEmpty(getName_N())) {
			return getCode() + " " + getName() +" "+ getName_N();
		} else {
			return getCode() + " " + getName();
		}		
	}

	/**
	 * @return 表示名取得
	 */
	public String getName() {
		return getCRG_NAME();
	}
	
	/**
	 * @return 表示名取得(外航用)
	 */
	public String getName_N() {
		return getCRG_NAME_N();
	}

	/**
	 * @return コード取得
	 */
	public String getCode() {
		return getCRG_CODE();
	}

	/**
	 * @return null
	 */
	public String getNamek() {
		return null;
	}

	/**
	 * @return null
	 */
	public String getNames() {
		return null;
	}

	/**
	 * @return STR_DATE
	 */
	public Date getDateFrom() {
		return STR_DATE;
	}

	/**
	 * @return END_DATE
	 */
	public Date getDateTo() {
		return END_DATE;
	}

	/** 会社コード */
	protected String KAI_CODE = null;

	/** CARGOコード */
	protected String CRG_CODE = null;

	/** CARGOグループコード */
	protected String CRG_GRP_CODE = null;

	/** CARGO名 */
	protected String CRG_NAME = null;

	/** サイズ係数 */
	protected BigDecimal STOWAGE_FACTOR = null;

	/** 貨物単位 */
	protected String CRG_UNIT_CODE = null;
	
	/** CARGO名 (内航用) */
	protected String CRG_NAME_N = null;

	/** 貨物単位 (内航用) */
	protected String CRG_UNIT_CODE_N = null;

	/** カテゴリー */
	protected String CATEGORY = null;

	/** カテゴリー名称 */
	protected String CATEGORY_NAME = null;

	/** MLIT ITEM */
	protected String MLIT_ITEM_CODE = null;

	/** MLIT ITEM */
	protected String MLIT_ITEM_NAME = null;

	/** MLIT SUB ITEM */
	protected String MLIT_SUB_ITEM_CODE = null;

	/** MLIT SUB ITEM */
	protected String MLIT_SUB_ITEM_NAME = null;

	/** 背景色 */
	protected String BG_COLOR = null;

	/** 文字色 */
	protected String TEXT_COLOR = null;

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

	/** CODE_NAME */
	protected String CODE_NAME = null;

	/** CODE_NAME_GRP */
	protected String CODE_NAME_GRP = null;

	/** ミニマム計算フラグ */
	protected int MIN_CALC_FLG = -1;

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
	 * CARGOコードの取得
	 * 
	 * @return CRG_CODE CARGOコード
	 */
	public String getCRG_CODE() {
		return CRG_CODE;
	}

	/**
	 * CARGOコードの設定
	 * 
	 * @param CRG_CODE CARGOコード
	 */
	public void setCRG_CODE(String CRG_CODE) {
		this.CRG_CODE = CRG_CODE;
	}

	/**
	 * CARGOグループコードの取得
	 * 
	 * @return CRG_GRP_CODE CARGOグループコード
	 */
	public String getCRG_GRP_CODE() {
		return CRG_GRP_CODE;
	}

	/**
	 * CARGOグループコードの設定
	 * 
	 * @param CRG_GRP_CODE CARGOグループコード
	 */
	public void setCRG_GRP_CODE(String CRG_GRP_CODE) {
		this.CRG_GRP_CODE = CRG_GRP_CODE;
	}

	/**
	 * CARGO名の取得
	 * 
	 * @return CRG_NAME CARGO名
	 */
	public String getCRG_NAME() {
		return CRG_NAME;
	}

	/**
	 * CARGO名の設定
	 * 
	 * @param CRG_NAME CARGO名
	 */
	public void setCRG_NAME(String CRG_NAME) {
		this.CRG_NAME = CRG_NAME;
	}

	/**
	 * サイズ係数の取得
	 * 
	 * @return STOWAGE_FACTOR サイズ係数
	 */
	public BigDecimal getSTOWAGE_FACTOR() {
		return STOWAGE_FACTOR;
	}

	/**
	 * サイズ係数の設定
	 * 
	 * @param STOWAGE_FACTOR サイズ係数
	 */
	public void setSTOWAGE_FACTOR(BigDecimal STOWAGE_FACTOR) {
		this.STOWAGE_FACTOR = STOWAGE_FACTOR;
	}

	/**
	 * 貨物単位の取得
	 * 
	 * @return CRG_UNIT_CODE 貨物単位
	 */
	public String getCRG_UNIT_CODE() {
		return CRG_UNIT_CODE;
	}

	/**
	 * 貨物単位の設定
	 * 
	 * @param CRG_UNIT_CODE 貨物単位
	 */
	public void setCRG_UNIT_CODE(String CRG_UNIT_CODE) {
		this.CRG_UNIT_CODE = CRG_UNIT_CODE;
	}
	
	/**
	 * CARGO名(内航用)の取得
	 * 
	 * @return CRG_NAME_N CARGO名
	 */
	public String getCRG_NAME_N() {
		return CRG_NAME_N;
	}

	/**
	 * CARGO名(内航用)の設定
	 * 
	 * @param CRG_NAME_N CARGO名
	 */
	public void setCRG_NAME_N(String CRG_NAME_N) {
		this.CRG_NAME_N = CRG_NAME_N;
	}

	/**
	 * 貨物単位(内航用)の取得
	 * 
	 * @return CRG_UNIT_CODE_N 貨物単位
	 */
	public String getCRG_UNIT_CODE_N() {
		return CRG_UNIT_CODE_N;
	}

	/**
	 * 貨物単位(内航用)の設定
	 * 
	 * @param CRG_UNIT_CODE_N 貨物単位
	 */
	public void setCRG_UNIT_CODE_N(String CRG_UNIT_CODE_N) {
		this.CRG_UNIT_CODE_N = CRG_UNIT_CODE_N;
	}

	/**
	 * カテゴリーの取得
	 * 
	 * @return CATEGORY カテゴリー
	 */
	public String getCATEGORY() {
		return CATEGORY;
	}

	/**
	 * カテゴリーの設定
	 * 
	 * @param CATEGORY カテゴリー
	 */
	public void setCATEGORY(String CATEGORY) {
		this.CATEGORY = CATEGORY;
	}

	/**
	 * カテゴリー名称の取得
	 * 
	 * @return CATEGORY カテゴリー名称
	 */
	public String getCATEGORY_NAME() {
		return CATEGORY_NAME;
	}

	/**
	 * カテゴリー名称の設定
	 * 
	 * @param CATEGORY_NAME カテゴリー名称
	 */
	public void setCATEGORY_NAME(String CATEGORY_NAME) {
		this.CATEGORY_NAME = CATEGORY_NAME;
	}

	/**
	 * MLIT ITEMの設定
	 * 
	 * @param MLIT_ITEM_CODE MLIT ITEM
	 */
	public void setMLIT_ITEM(String MLIT_ITEM_CODE) {
		this.MLIT_ITEM_CODE = MLIT_ITEM_CODE;
	}

	/**
	 * MLIT ITEMの取得
	 * 
	 * @return MLIT_ITEM MLIT ITEM
	 */
	public String getMLIT_ITEM() {
		return MLIT_ITEM_CODE;
	}

	/**
	 * MLIT ITEMの取得
	 * 
	 * @return MLIT_ITEM MLIT ITEM
	 */
	public String getMLIT_ITEM_CODE() {
		return MLIT_ITEM_CODE;
	}

	/**
	 * MLIT ITEMの設定
	 * 
	 * @param MLIT_ITEM_CODE MLIT ITEM
	 */
	public void setMLIT_ITEM_CODE(String MLIT_ITEM_CODE) {
		this.MLIT_ITEM_CODE = MLIT_ITEM_CODE;
	}

	/**
	 * MLIT ITEM名称の取得
	 * 
	 * @return MLIT_ITEM MLIT ITEM
	 */
	public String getMLIT_ITEM_NAME() {
		return MLIT_ITEM_NAME;
	}

	/**
	 * MLIT ITEM名称の設定
	 * 
	 * @param MLIT_ITEM_NAME MLIT ITEM名称
	 */
	public void setMLIT_ITEM_NAME(String MLIT_ITEM_NAME) {
		this.MLIT_ITEM_NAME = MLIT_ITEM_NAME;
	}

	/**
	 * MLIT SUB ITEMの取得
	 * 
	 * @return MLIT_SUB_ITEM MLIT SUB ITEM
	 */
	public String getMLIT_SUB_ITEM() {
		return MLIT_SUB_ITEM_CODE;
	}

	/**
	 * MLIT SUB ITEMの設定
	 * 
	 * @param MLIT_SUB_ITEM_CODE MLIT SUB ITEM
	 */
	public void setMLIT_SUB_ITEM(String MLIT_SUB_ITEM_CODE) {
		this.MLIT_SUB_ITEM_CODE = MLIT_SUB_ITEM_CODE;
	}

	/**
	 * MLIT SUB ITEMの取得
	 * 
	 * @return MLIT_SUB_ITEM MLIT SUB ITEM
	 */
	public String getMLIT_SUB_ITEM_CODE() {
		return MLIT_SUB_ITEM_CODE;
	}

	/**
	 * MLIT SUB ITEMの設定
	 * 
	 * @param MLIT_SUB_ITEM_CODE MLIT SUB ITEM
	 */
	public void setMLIT_SUB_ITEM_CODE(String MLIT_SUB_ITEM_CODE) {
		this.MLIT_SUB_ITEM_CODE = MLIT_SUB_ITEM_CODE;
	}

	/**
	 * MLIT SUB ITEM名称の取得
	 * 
	 * @return MLIT_SUB_ITEM MLIT SUB ITEM
	 */
	public String getMLIT_SUB_ITEM_NAME() {
		return MLIT_SUB_ITEM_NAME;
	}

	/**
	 * MLIT SUB ITEM名称の設定
	 * 
	 * @param MLIT_SUB_ITEM_NAME MLIT SUB ITEM
	 */
	public void setMLIT_SUB_ITEM_NAME(String MLIT_SUB_ITEM_NAME) {
		this.MLIT_SUB_ITEM_NAME = MLIT_SUB_ITEM_NAME;
	}

	/**
	 * 背景色の取得
	 * 
	 * @return BG_COLOR 背景色
	 */
	public String getBG_COLOR() {
		return BG_COLOR;
	}

	/**
	 * 背景色の設定
	 * 
	 * @param BG_COLOR 背景色
	 */
	public void setBG_COLOR(String BG_COLOR) {
		this.BG_COLOR = BG_COLOR;
	}

	/**
	 * 文字色の取得
	 * 
	 * @return TEXT_COLOR 文字色
	 */
	public String getTEXT_COLOR() {
		return TEXT_COLOR;
	}

	/**
	 * 文字色の設定
	 * 
	 * @param TEXT_COLOR 文字色
	 */
	public void setTEXT_COLOR(String TEXT_COLOR) {
		this.TEXT_COLOR = TEXT_COLOR;
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
	 * @return CODE_NAME
	 */
	public String getCODE_NAME() {
		return CODE_NAME;
	}

	/**
	 * @param cODE_NAME
	 */
	public void setCODE_NAME(String cODE_NAME) {
		CODE_NAME = cODE_NAME;
	}

	/**
	 * @return CODE_NAME_GRP
	 */
	public String getCODE_NAME_GRP() {
		return CODE_NAME_GRP;
	}

	/**
	 * @param cODE_NAME_GRP
	 */
	public void setCODE_NAME_GRP(String cODE_NAME_GRP) {
		CODE_NAME_GRP = cODE_NAME_GRP;
	}

	/**
	 * Minimum計算フラグの取得
	 * 
	 * @return MIN_CALC Minimum計算
	 */
	public int getMIN_CALC_FLG() {
		return MIN_CALC_FLG;
	}

	/**
	 * Minimum計算フラグの設定
	 * 
	 * @param mIN_CALC_FLG Minimum計算
	 */
	public void setMIN_CALC_FLG(int mIN_CALC_FLG) {
		MIN_CALC_FLG = mIN_CALC_FLG;
	}

	/**
	 * (Liner)Minimum計算フラグの取得<br>
	 * trueの場合、貨物数量が1未満の場合数量を1として計算する
	 * 
	 * @return MIN_CALC Minimum計算
	 */
	public boolean isMinimumCalc() {
		return BooleanUtil.toBoolean(MIN_CALC_FLG);
	}

	/**
	 * (Liner)Minimum計算フラグの取得<br>
	 * trueの場合、貨物数量が1未満の場合数量を1として計算する
	 * 
	 * @param mIN_CALC_FLG Minimum計算
	 */
	public void setMinimumCalc(boolean mIN_CALC_FLG) {
		this.MIN_CALC_FLG = BooleanUtil.toInt(mIN_CALC_FLG);
	}

}
