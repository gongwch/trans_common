package jp.co.ais.trans2.model.port;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * PortLinerCharge
 */
public class PortLinerCharge extends TransferBase {

	/**
	 * クローン.
	 * 
	 * @return Port Liner Charge
	 */
	@Override
	public PortLinerCharge clone() {
		try {
			return (PortLinerCharge) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/** 会社コード */
	protected String companyCode = null;

	/** Portコード */
	protected String code = null;

	/** CHRG_NO */
	protected int CHRG_NO;

	/** CHARGE NAME */
	protected String CHARGE_NAME;

	/** LD TYPE */
	protected String LD_TYPE;

	/** CATEGORY */
	protected String CATEGORY;

	/** CATEGORY_NAME */
	protected String CATEGORY_NAME;

	/** CARGO TYPE */
	protected String CARGO_TYPE;

	/** CARGO_CODE */
	protected String CARGO_CODE;

	/** CURRENCY */
	protected String CURRENCY;

	/** AMOUNT */
	protected BigDecimal AMOUNT;

	/** CALCULATE TYPE */
	protected String PER;

	/** MINIMUM */
	protected BigDecimal MIN_AMT;

	/** HEAVY LIFT */
	protected BigDecimal HEAVY_LIFT;

	/** 登録年月日. */
	protected Date INP_DATE = null;

	/** 更新年月日. */
	protected Date UPD_DATE = null;

	/** プログラムID. */
	protected String PRG_ID = null;

	/** ユーザーID. */
	protected String USR_ID = null;

	/** 小数点以下桁数 */
	protected int DEC_KETA = -1;

	/**
	 * 会社コードを取得します。
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードを設定します。
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * Portコードを取得します。
	 * 
	 * @return Portコード
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Portコードを設定します。
	 * 
	 * @param code Portコード
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Charge No称を取得します。
	 * 
	 * @return Charge No称
	 */
	public int getCHRG_NO() {
		return CHRG_NO;
	}

	/**
	 * CHRG_NO称を設定します。
	 * 
	 * @param CHRG_NO Charge No称
	 */
	public void setCHRG_NO(int CHRG_NO) {
		this.CHRG_NO = CHRG_NO;
	}

	/**
	 * Charge名称を取得します。
	 * 
	 * @return Charge名称
	 */
	public String getCHARGE_NAME() {
		return CHARGE_NAME;
	}

	/**
	 * Charge名称を設定します。
	 * 
	 * @param CHARGE_NAME Charge名称
	 */
	public void setCHARGE_NAME(String CHARGE_NAME) {
		this.CHARGE_NAME = CHARGE_NAME;
	}

	/**
	 * LD種類を取得します。
	 * 
	 * @return LD_TYPE
	 */
	public String getLD_TYPE() {
		return LD_TYPE;
	}

	/**
	 * LD種類を設定します。
	 * 
	 * @param LD_TYPE LD種類
	 */
	public void setLD_TYPE(String LD_TYPE) {
		this.LD_TYPE = LD_TYPE;
	}

	/**
	 * Categoryを取得します。
	 * 
	 * @return CAREGORY
	 */
	public String getCATEGORY() {
		return CATEGORY;
	}

	/**
	 * Categoryを設定します。
	 * 
	 * @param CATEGORY
	 */
	public void setCATEGORY(String CATEGORY) {
		this.CATEGORY = CATEGORY;
	}

	/**
	 * Categoryを取得します。
	 * 
	 * @return CATEGORY_NAME
	 */
	public String getCATEGORY_NAME() {
		return CATEGORY_NAME;
	}

	/**
	 * Categoryを設定します。
	 * 
	 * @param CATEGORY_NAME
	 */
	public void setCATEGORY_NAME(String CATEGORY_NAME) {
		this.CATEGORY_NAME = CATEGORY_NAME;
	}

	/**
	 * Cargo種類を取得します。
	 * 
	 * @return CARGO_TYPE
	 */
	public String getCARGO_TYPE() {
		return CARGO_TYPE;
	}

	/**
	 * Cargo種類を設定します。
	 * 
	 * @param CARGO_TYPE
	 */
	public void setCARGO_TYPE(String CARGO_TYPE) {
		this.CARGO_TYPE = CARGO_TYPE;
	}

	/**
	 * Cargo種類を取得します。
	 * 
	 * @return CARGO_CODE
	 */
	public String getCARGO_CODE() {
		return CARGO_CODE;
	}

	/**
	 * Cargo種類を設定します。
	 * 
	 * @param CARGO_CODE
	 */
	public void setCARGO_CODE(String CARGO_CODE) {
		this.CARGO_CODE = CARGO_CODE;
	}

	/**
	 * 通貨を取得します。
	 * 
	 * @return CURRENCY
	 */
	public String getCURRENCY() {
		return CURRENCY;
	}

	/**
	 * 通貨を設定します。
	 * 
	 * @param CURRENCY
	 */
	public void setCURRENCY(String CURRENCY) {
		this.CURRENCY = CURRENCY;
	}

	/**
	 * @return AMOUNT
	 */
	public BigDecimal getAMOUNT() {
		return AMOUNT;
	}

	/**
	 * @param AMOUNT
	 */

	public void setAMOUNT(BigDecimal AMOUNT) {
		this.AMOUNT = AMOUNT;
	}

	/**
	 * CALCULATEを取得します。
	 * 
	 * @return PER
	 */
	public String getPER() {
		return PER;
	}

	/**
	 * CALCULATEを設定します。
	 * 
	 * @param PER
	 */
	public void setPER(String PER) {
		this.PER = PER;
	}

	/**
	 * @return MINIMUM
	 */
	public BigDecimal getMIN_AMT() {
		return MIN_AMT;
	}

	/**
	 * @param MIN_AMT
	 */
	public void setMIN_AMT(BigDecimal MIN_AMT) {
		this.MIN_AMT = MIN_AMT;
	}

	/**
	 * @return HEAVY LIFT
	 */
	public BigDecimal getHEAVY_LIFT() {
		return HEAVY_LIFT;
	}

	/**
	 * @param HEAVY_LIFT
	 */

	public void setHEAVY_LIFT(BigDecimal HEAVY_LIFT) {
		this.HEAVY_LIFT = HEAVY_LIFT;
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
	 * 小数点以下桁数の取得
	 * 
	 * @return dEC_KETA 小数点以下桁数
	 */
	public int getDEC_KETA() {
		return DEC_KETA;
	}

	/**
	 * 小数点以下桁数の設定
	 * 
	 * @param DEC_KETA 小数点以下桁数
	 */
	public void setDEC_KETA(int DEC_KETA) {
		this.DEC_KETA = DEC_KETA;
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
	 * ユーザー編集を取得します。
	 * 
	 * @return USR_ID
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ユーザー編集を設定します。
	 * 
	 * @param USR_ID
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

}
