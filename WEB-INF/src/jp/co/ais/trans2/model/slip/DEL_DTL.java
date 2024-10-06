package jp.co.ais.trans2.model.slip;

import java.util.*;
import jp.co.ais.trans.common.dt.*;

/**
 * 削除伝票データ
 */
public class DEL_DTL extends TransferBase {

	/**  */
	public static final String TABLE = "DEL_DTL";

	/** 会社コード */
	protected String kAI_CODE = "";

	/** 伝票日付 */
	protected Date dEL_DEN_DATE;

	/** 伝票番号 */
	protected String dEL_DEN_NO = "";

	/** 登録日付 */
	protected Date iNP_DATE;

	/** 更新日付 */
	protected Date uPD_DATE;

	/** ユーザーＩＤ */
	protected String uSR_ID = "";

	/** プログラムＩＤ */
	protected String pRG_ID;

	/**
	 * dEL_DEN_DATEを取得する。
	 * 
	 * @return Date dEL_DEN_DATE
	 */
	public Date getDEL_DEN_DATE() {
		return dEL_DEN_DATE;
	}

	/**
	 * dEL_DEN_DATEを設定する。
	 * 
	 * @param del_den_date
	 */
	public void setDEL_DEN_DATE(Date del_den_date) {
		dEL_DEN_DATE = del_den_date;
	}

	/**
	 * dEL_DEN_NOを取得する。
	 * 
	 * @return String dEL_DEN_NO
	 */
	public String getDEL_DEN_NO() {
		return dEL_DEN_NO;
	}

	/**
	 * dEL_DEN_NOを設定する。
	 * 
	 * @param del_den_no
	 */
	public void setDEL_DEN_NO(String del_den_no) {
		dEL_DEN_NO = del_den_no;
	}

	/**
	 * iNP_DATEを取得する。
	 * 
	 * @return String iNP_DATE
	 */
	public Date getINP_DATE() {
		return iNP_DATE;
	}

	/**
	 * iNP_DATEを設定する。
	 * 
	 * @param inp_date
	 */
	public void setINP_DATE(Date inp_date) {
		iNP_DATE = inp_date;
	}

	/**
	 * kAI_CODEを取得する。
	 * 
	 * @return String kAI_CODE
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * kAI_CODEを設定する。
	 * 
	 * @param kai_code
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * pRG_IDを取得する。
	 * 
	 * @return String pRG_ID
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * pRG_IDを設定する。
	 * 
	 * @param prg_id
	 */
	public void setPRG_ID(String prg_id) {
		pRG_ID = prg_id;
	}

	/**
	 * uPD_DATEを取得する。
	 * 
	 * @return String uPD_DATE
	 */
	public Date getUPD_DATE() {
		return uPD_DATE;
	}

	/**
	 * uPD_DATEを設定する。
	 * 
	 * @param upd_date
	 */
	public void setUPD_DATE(Date upd_date) {
		uPD_DATE = upd_date;
	}

	/**
	 * uSR_IDを取得する。
	 * 
	 * @return String uSR_ID
	 */
	public String getUSR_ID() {
		return uSR_ID;
	}

	/**
	 * uSR_IDを設定する。
	 * 
	 * @param usr_id
	 */
	public void setUSR_ID(String usr_id) {
		uSR_ID = usr_id;
	}

}
