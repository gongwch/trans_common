package jp.co.ais.trans2.model.port;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * サマータイムマスタ
 */
public class OP_SMR_TIME_MST extends TransferBase implements Cloneable {

	/** クローン */
	@Override
	public OP_SMR_TIME_MST clone() {
		try {
			return (OP_SMR_TIME_MST) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return 表示名
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("\nregion=").append(REGION_CODE);
		sb.append("\ttz=").append(TIME_DIFFERENCE);
		sb.append("\tfrom=").append(DateUtil.toYMDHMString(FROM_DATE));
		sb.append("\tto=").append(DateUtil.toYMDHMString(TO_DATE));

		return sb.toString();
	}

	/** 会社コード */
	protected String KAI_CODE = null;

	/** 年 */
	protected int YEAR = 0;

	/** REGIONコード */
	protected String REGION_CODE = null;

	/** REGION名称 */
	protected String REGION_NAME = null;

	/** 時差 */
	protected BigDecimal TIME_DIFFERENCE = null;

	/** 有効期間(LCT)開始 */
	protected Date FROM_DATE = null;

	/** 有効期間(LCT)終了 */
	protected Date TO_DATE = null;

	/** REMARKS */
	protected String REMARKS = null;

	/** 登録日時 */
	protected Date INP_DATE = null;

	/** 更新日時 */
	protected Date UPD_DATE = null;

	/** プログラムID */
	protected String PRG_ID = null;

	/** ユーザーID */
	protected String USR_ID = null;

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
	 * 年の取得
	 * 
	 * @return YEAR 年
	 */
	public int getYEAR() {
		return YEAR;
	}

	/**
	 * 年の設定
	 * 
	 * @param YEAR 年
	 */
	public void setYEAR(int YEAR) {
		this.YEAR = YEAR;
	}

	/**
	 * REGIONコードの取得
	 * 
	 * @return REGION_CODE REGIONコード
	 */
	public String getREGION_CODE() {
		return REGION_CODE;
	}

	/**
	 * REGIONコードの設定
	 * 
	 * @param REGION_CODE REGIONコード
	 */
	public void setREGION_CODE(String REGION_CODE) {
		this.REGION_CODE = REGION_CODE;
	}

	/**
	 * REGION名称の取得
	 * 
	 * @return REGION_NAME REGION名称
	 */
	public String getREGION_NAME() {
		return REGION_NAME;
	}

	/**
	 * REGION名称の設定
	 * 
	 * @param REGION_NAME REGION名称
	 */
	public void setREGION_NAME(String REGION_NAME) {
		this.REGION_NAME = REGION_NAME;
	}

	/**
	 * 時差の取得
	 * 
	 * @return TIME_DIFFERENCE 時差
	 */
	public BigDecimal getTIME_DIFFERENCE() {
		return TIME_DIFFERENCE;
	}

	/**
	 * 時差の設定
	 * 
	 * @param TIME_DIFFERENCE 時差
	 */
	public void setTIME_DIFFERENCE(BigDecimal TIME_DIFFERENCE) {
		this.TIME_DIFFERENCE = TIME_DIFFERENCE;
	}

	/**
	 * 有効期間(LCT)開始の取得
	 * 
	 * @return FROM_DATE 有効期間(LCT)開始
	 */
	public Date getFROM_DATE() {
		return FROM_DATE;
	}

	/**
	 * 有効期間(LCT)開始の設定
	 * 
	 * @param FROM_DATE 有効期間(LCT)開始
	 */
	public void setFROM_DATE(Date FROM_DATE) {
		this.FROM_DATE = FROM_DATE;
	}

	/**
	 * 有効期間(LCT)終了の取得
	 * 
	 * @return TO_DATE 有効期間(LCT)終了
	 */
	public Date getTO_DATE() {
		return TO_DATE;
	}

	/**
	 * 有効期間(LCT)終了の設定
	 * 
	 * @param TO_DATE 有効期間(LCT)終了
	 */
	public void setTO_DATE(Date TO_DATE) {
		this.TO_DATE = TO_DATE;
	}

	/**
	 * REMARKSの取得
	 * 
	 * @return REMARKS REMARKS
	 */
	public String getREMARKS() {
		return REMARKS;
	}

	/**
	 * REMARKSの設定
	 * 
	 * @param REMARKS REMARKS
	 */
	public void setREMARKS(String REMARKS) {
		this.REMARKS = REMARKS;
	}

	/**
	 * 登録日時の取得
	 * 
	 * @return INP_DATE 登録日時
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * 登録日時の設定
	 * 
	 * @param INP_DATE 登録日時
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * 更新日時の取得
	 * 
	 * @return UPD_DATE 更新日時
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * 更新日時の設定
	 * 
	 * @param UPD_DATE 更新日時
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

}
