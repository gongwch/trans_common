package jp.co.ais.trans2.model.slip;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 伝票付箋機能
 */
public class SWK_TAG extends TransferBase {

	/** テーブル名 */
	public static final String TABLE = "SWK_TAG";

	/** 会社コード */
	protected String KAI_CODE = null;

	/** 伝票番号 */
	protected String SWK_DEN_NO = null;

	/** 連番 */
	protected int SEQ = 0;

	/** 付箋コード */
	protected String TAG_CODE = null;

	/** 付箋色 */
	protected Color TAG_COLOR = null;

	/** 付箋タイトル */
	protected String TAG_TITLE = null;

	/** 登録日付 */
	protected Date INP_DATE = null;

	/** 更新日付 */
	protected Date UPD_DATE = null;

	/** プログラムID */
	protected String PRG_ID = null;

	/** ユーザID */
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
	 * 伝票番号の取得
	 * 
	 * @return SWK_DEN_NO 伝票番号
	 */
	public String getSWK_DEN_NO() {
		return SWK_DEN_NO;
	}

	/**
	 * 伝票番号の設定
	 * 
	 * @param SWK_DEN_NO 伝票番号
	 */
	public void setSWK_DEN_NO(String SWK_DEN_NO) {
		this.SWK_DEN_NO = SWK_DEN_NO;
	}

	/**
	 * 連番の取得
	 * 
	 * @return SEQ 連番
	 */
	public int getSEQ() {
		return SEQ;
	}

	/**
	 * 連番の設定
	 * 
	 * @param SEQ 連番
	 */
	public void setSEQ(int SEQ) {
		this.SEQ = SEQ;
	}

	/**
	 * 付箋コードの取得
	 * 
	 * @return TAG_CODE 付箋コード
	 */
	public String getTAG_CODE() {
		return TAG_CODE;
	}

	/**
	 * 付箋コードの設定
	 * 
	 * @param TAG_CODE 付箋コード
	 */
	public void setTAG_CODE(String TAG_CODE) {
		this.TAG_CODE = TAG_CODE;
	}

	/**
	 * 付箋色の取得
	 * 
	 * @return TAG_COLOR 付箋色
	 */
	public Color getTAG_COLOR() {
		return TAG_COLOR;
	}

	/**
	 * 付箋色の設定
	 * 
	 * @param TAG_COLOR 付箋色
	 */
	public void setTAG_COLOR(Color TAG_COLOR) {
		this.TAG_COLOR = TAG_COLOR;
	}

	/**
	 * 付箋タイトルの取得
	 * 
	 * @return TAG_TITLE 付箋タイトル
	 */
	public String getTAG_TITLE() {
		return TAG_TITLE;
	}

	/**
	 * 付箋タイトルの設定
	 * 
	 * @param TAG_TITLE 付箋タイトル
	 */
	public void setTAG_TITLE(String TAG_TITLE) {
		this.TAG_TITLE = TAG_TITLE;
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
	 * ユーザIDの取得
	 * 
	 * @return USR_ID ユーザID
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ユーザIDの設定
	 * 
	 * @param USR_ID ユーザID
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

}
