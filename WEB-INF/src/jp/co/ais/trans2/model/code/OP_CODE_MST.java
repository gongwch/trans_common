package jp.co.ais.trans2.model.code;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.gui.ac.*;

/**
 * OP_CODE_MST
 */
public class OP_CODE_MST extends TransferBase implements Cloneable, AutoCompletable {

	/**
	 * クローン
	 */
	@Override
	public OP_CODE_MST clone() {
		try {
			OP_CODE_MST bean = (OP_CODE_MST) super.clone();

			return bean;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(CODE_DIV).append("]").append(CODE).append(" ").append(CODE_NAME);
		return sb.toString();
	}

	/**
	 * @return インクリメントサーチ表示値
	 */
	public String getDisplayText() {
		return getCODE_NAME();
	}

	/**
	 * @return コード
	 */
	public String getCode() {
		return getCODE();
	}

	/**
	 * @return 名称
	 */
	public String getName() {
		return getCODE_NAME();
	}

	/** 会社コード */
	protected String KAI_CODE = null;

	/** コード */
	protected String CODE = null;

	/** コード区分 */
	protected String CODE_DIV = null;

	/** 表示順 */
	protected int DISP_ODR = 0;

	/** コード名 */
	protected String CODE_NAME = null;

	/** 1:内航モード、以外:外航モード */
	protected int LCL_KBN = -1;

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
	 * コードの取得
	 * 
	 * @return CODE コード
	 */
	public String getCODE() {
		return CODE;
	}

	/**
	 * コードの設定
	 * 
	 * @param CODE コード
	 */
	public void setCODE(String CODE) {
		this.CODE = CODE;
	}

	/**
	 * コード区分の取得
	 * 
	 * @return CODE_DIV コード区分
	 */
	public String getCODE_DIV() {
		return CODE_DIV;
	}

	/**
	 * コード区分の設定
	 * 
	 * @param CODE_DIV コード区分
	 */
	public void setCODE_DIV(String CODE_DIV) {
		this.CODE_DIV = CODE_DIV;
	}

	/**
	 * 表示順の取得
	 * 
	 * @return DISP_ODR 表示順
	 */
	public int getDISP_ODR() {
		return DISP_ODR;
	}

	/**
	 * 表示順の設定
	 * 
	 * @param DISP_ODR 表示順
	 */
	public void setDISP_ODR(int DISP_ODR) {
		this.DISP_ODR = DISP_ODR;
	}

	/**
	 * コード名の取得
	 * 
	 * @return CODE_NAME コード名
	 */
	public String getCODE_NAME() {
		return CODE_NAME;
	}

	/**
	 * コード名の設定
	 * 
	 * @param CODE_NAME コード名
	 */
	public void setCODE_NAME(String CODE_NAME) {
		this.CODE_NAME = CODE_NAME;
	}

	/**
	 * 1:内航モード、以外:外航モードの取得
	 * 
	 * @return LCL_KBN 1:内航モード、以外:外航モード
	 */
	public int getLCL_KBN() {
		return LCL_KBN;
	}

	/**
	 * 1:内航モード、以外:外航モードの設定
	 * 
	 * @param LCL_KBN 1:内航モード、以外:外航モード
	 */
	public void setLCL_KBN(int LCL_KBN) {
		this.LCL_KBN = LCL_KBN;
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

}
