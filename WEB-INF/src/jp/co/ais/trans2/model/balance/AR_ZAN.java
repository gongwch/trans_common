package jp.co.ais.trans2.model.balance;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 債権残高クラス
 * 
 * @author AIS
 */
public class AR_ZAN extends TransferBase {

	/** テーブルＩＤ */
	public static final String TABLE = "AR_ZAN";

	/** 会社コード */
	protected String kAI_CODE;

	/** 会社略称 */
	protected String kAI_NAME_S;

	/** 計上部門コード */
	protected String zAN_DEP_CODE;

	/** 計上部門略称 */
	protected String zAN_DEP_NAME_S;

	/** 取引先コード */
	protected String zAN_TRI_CODE;

	/** 取引先略称 */
	protected String zAN_TRI_NAME_S;

	/** 請求書番号 */
	protected String zAN_SEI_NO;

	/** 伝票日付 */
	protected Date zAN_SEI_DEN_DATE;

	/** 伝票番号 */
	protected String zAN_SEI_DEN_NO;

	/** 行番号 */
	protected int zAN_SEI_GYO_NO;

	/** データ区分 */
	protected String zAN_DATA_KBN;

	/** 消込伝票日付 */
	protected Date zAN_KESI_DEN_DATE;

	/** 消込伝票番号 */
	protected String zAN_KESI_DEN_NO;

	/** 消込行番号 */
	protected int zAN_KESI_GYO_NO;

	/** 科目コード */
	protected String zAN_KMK_CODE;

	/** 科目略称 */
	protected String zAN_KMK_NAME_S;

	/** 補助科目コード */
	protected String zAN_HKM_CODE;

	/** 補助科目略称 */
	protected String zAN_HKM_NAME_S;

	/** 内訳科目コード */
	protected String zAN_UKM_CODE;

	/** 内訳科目略称 */
	protected String zAN_UKM_NAME_S;

	/** 入金予定日 */
	protected Date zAN_AR_DATE;

	/** 請求金額 */
	protected BigDecimal zAN_SEI_KIN;

	/** 消込金額 */
	protected BigDecimal zAN_KESI_KIN;

	/** 取引先名称 */
	protected String zAN_TRI_NAME;

	/** 登録日付 */
	protected Date iNP_DATE;

	/** 更新日付 */
	protected Date uPD_DATE;

	/** プログラムＩＤ */
	protected String pRG_ID;

	/** ユーザーＩＤ */
	protected String uSR_ID;

	/** 相殺フラグ */
	protected int zAN_SOUSAI_FLG;

	/** 相殺日 */
	protected Date zAN_SOUSAI_DATE;

	/** 支払区分 */
	protected String zAN_SIHA_KBN;

	/** 受付番号 */
	protected String zAN_UTK_NO;

	/** 銀行口座コード */
	protected String zAN_CBK_CODE;

	/** 通貨コード */
	protected String zAN_CUR_CODE;

	/** 通貨小数点桁数 */
	protected int zAN_CUR_DEC_KETA;

	/** 通貨レート係数 */
	protected int zAN_CUR_RATE_POW;

	/** 通貨レート */
	protected BigDecimal zAN_CUR_RATE;

	/** 請求金額（入力金額） */
	protected BigDecimal zAN_SEI_IN_KIN;

	/** 消込金額（入力金額） */
	protected BigDecimal zAN_KESI_IN_KIN;

	/** 入金受付番号 */
	protected String zAN_NYU_UTK_NO;

	/** 入金行番号 */
	protected Integer zAN_NYU_GYO_NO;

	/** 入金消込金額 */
	protected BigDecimal zAN_NYU_KESI_KIN;

	/** 摘要 */
	protected String zAN_TEK;

	/** 未消し込み金額(入力) */
	protected BigDecimal nON_KESI_IN_KIN;

	/** 未消し込み金額 */
	protected BigDecimal nON_KESI_KIN;

	/** 請求金額(入力) */
	protected BigDecimal sEI_IN_KIN;

	/** 請求金額 */
	protected BigDecimal sEI_KIN;

	/** 消し込み金額(入力) */
	protected BigDecimal kESI_IN_KIN;

	/** 消し込み金額 */
	protected BigDecimal kESI_KIN;

	/** アドオンコード1 */
	protected String aDD_CODE_1 = null;

	/** アドオンコード2 */
	protected String aDD_CODE_2 = null;

	/** アドオンコード3 */
	protected String aDD_CODE_3 = null;

	/** アドオンコード4 */
	protected String aDD_CODE_4 = null;

	/** アドオンコード5 */
	protected String aDD_CODE_5 = null;

	/** アドオンコード6 */
	protected String aDD_CODE_6 = null;

	/** 追加名称1 */
	protected String aDD_NAME_1 = null;

	/** 追加名称2 */
	protected String aDD_NAME_2 = null;

	/** 追加名称3 */
	protected String aDD_NAME_3 = null;

	/** 追加名称4 */
	protected String aDD_NAME_4 = null;

	/** 追加名称5 */
	protected String aDD_NAME_5 = null;

	/** 追加名称6 */
	protected String aDD_NAME_6 = null;

	/**
	 * @return iNP_DATE
	 */
	public Date getINP_DATE() {
		return iNP_DATE;
	}

	/**
	 * @param inp_date
	 */
	public void setINP_DATE(Date inp_date) {
		iNP_DATE = inp_date;
	}

	/**
	 * @return kAI_CODE
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * @param kai_code
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * @return kAI_NAME_S
	 */
	public String getKAI_NAME_S() {
		return kAI_NAME_S;
	}

	/**
	 * @param kai_name_s
	 */
	public void setKAI_NAME_S(String kai_name_s) {
		kAI_NAME_S = kai_name_s;
	}

	/**
	 * @return kESI_IN_KIN
	 */
	public BigDecimal getKESI_IN_KIN() {
		return kESI_IN_KIN;
	}

	/**
	 * @param kesi_in_kin
	 */
	public void setKESI_IN_KIN(BigDecimal kesi_in_kin) {
		kESI_IN_KIN = kesi_in_kin;
	}

	/**
	 * @return kESI_KIN
	 */
	public BigDecimal getKESI_KIN() {
		return kESI_KIN;
	}

	/**
	 * @param kesi_kin
	 */
	public void setKESI_KIN(BigDecimal kesi_kin) {
		kESI_KIN = kesi_kin;
	}

	/**
	 * @return nON_KESI_IN_KIN
	 */
	public BigDecimal getNON_KESI_IN_KIN() {
		return nON_KESI_IN_KIN;
	}

	/**
	 * @param non_kesi_in_kin
	 */
	public void setNON_KESI_IN_KIN(BigDecimal non_kesi_in_kin) {
		nON_KESI_IN_KIN = non_kesi_in_kin;
	}

	/**
	 * @return nON_KESI_KIN
	 */
	public BigDecimal getNON_KESI_KIN() {
		return nON_KESI_KIN;
	}

	/**
	 * @param non_kesi_kin
	 */
	public void setNON_KESI_KIN(BigDecimal non_kesi_kin) {
		nON_KESI_KIN = non_kesi_kin;
	}

	/**
	 * @return pRG_ID
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * @param prg_id
	 */
	public void setPRG_ID(String prg_id) {
		pRG_ID = prg_id;
	}

	/**
	 * @return sEI_IN_KIN
	 */
	public BigDecimal getSEI_IN_KIN() {
		return sEI_IN_KIN;
	}

	/**
	 * @param sei_in_kin
	 */
	public void setSEI_IN_KIN(BigDecimal sei_in_kin) {
		sEI_IN_KIN = sei_in_kin;
	}

	/**
	 * @return sEI_KIN
	 */
	public BigDecimal getSEI_KIN() {
		return sEI_KIN;
	}

	/**
	 * @param sei_kin
	 */
	public void setSEI_KIN(BigDecimal sei_kin) {
		sEI_KIN = sei_kin;
	}

	/**
	 * @return uPD_DATE
	 */
	public Date getUPD_DATE() {
		return uPD_DATE;
	}

	/**
	 * @param upd_date
	 */
	public void setUPD_DATE(Date upd_date) {
		uPD_DATE = upd_date;
	}

	/**
	 * @return uSR_ID
	 */
	public String getUSR_ID() {
		return uSR_ID;
	}

	/**
	 * @param usr_id
	 */
	public void setUSR_ID(String usr_id) {
		uSR_ID = usr_id;
	}

	/**
	 * @return zAN_AR_DATE
	 */
	public Date getZAN_AR_DATE() {
		return zAN_AR_DATE;
	}

	/**
	 * @param zan_ar_date
	 */
	public void setZAN_AR_DATE(Date zan_ar_date) {
		zAN_AR_DATE = zan_ar_date;
	}

	/**
	 * @return zAN_CBK_CODE
	 */
	public String getZAN_CBK_CODE() {
		return zAN_CBK_CODE;
	}

	/**
	 * @param zan_cbk_code
	 */
	public void setZAN_CBK_CODE(String zan_cbk_code) {
		zAN_CBK_CODE = zan_cbk_code;
	}

	/**
	 * @return zAN_CUR_CODE
	 */
	public String getZAN_CUR_CODE() {
		return zAN_CUR_CODE;
	}

	/**
	 * @param zan_cur_code
	 */
	public void setZAN_CUR_CODE(String zan_cur_code) {
		zAN_CUR_CODE = zan_cur_code;
	}

	/**
	 * @return zAN_CUR_DEC_KETA
	 */
	public int getZAN_CUR_DEC_KETA() {
		return zAN_CUR_DEC_KETA;
	}

	/**
	 * @param zan_cur_dec_keta
	 */
	public void setZAN_CUR_DEC_KETA(int zan_cur_dec_keta) {
		zAN_CUR_DEC_KETA = zan_cur_dec_keta;
	}

	/**
	 * @return zAN_CUR_RATE
	 */
	public BigDecimal getZAN_CUR_RATE() {
		return zAN_CUR_RATE;
	}

	/**
	 * @param zan_cur_rate
	 */
	public void setZAN_CUR_RATE(BigDecimal zan_cur_rate) {
		zAN_CUR_RATE = zan_cur_rate;
	}

	/**
	 * @return zAN_DATA_KBN
	 */
	public String getZAN_DATA_KBN() {
		return zAN_DATA_KBN;
	}

	/**
	 * @param zan_data_kbn
	 */
	public void setZAN_DATA_KBN(String zan_data_kbn) {
		zAN_DATA_KBN = zan_data_kbn;
	}

	/**
	 * @return zAN_DEP_CODE
	 */
	public String getZAN_DEP_CODE() {
		return zAN_DEP_CODE;
	}

	/**
	 * @param zan_dep_code
	 */
	public void setZAN_DEP_CODE(String zan_dep_code) {
		zAN_DEP_CODE = zan_dep_code;
	}

	/**
	 * @return zAN_DEP_NAME_S
	 */
	public String getZAN_DEP_NAME_S() {
		return zAN_DEP_NAME_S;
	}

	/**
	 * @param zan_dep_name_s
	 */
	public void setZAN_DEP_NAME_S(String zan_dep_name_s) {
		zAN_DEP_NAME_S = zan_dep_name_s;
	}

	/**
	 * @return zAN_HKM_CODE
	 */
	public String getZAN_HKM_CODE() {
		return zAN_HKM_CODE;
	}

	/**
	 * @param zan_hkm_code
	 */
	public void setZAN_HKM_CODE(String zan_hkm_code) {
		zAN_HKM_CODE = zan_hkm_code;
	}

	/**
	 * @return zAN_HKM_NAME_S
	 */
	public String getZAN_HKM_NAME_S() {
		return zAN_HKM_NAME_S;
	}

	/**
	 * @param zan_hkm_name_s
	 */
	public void setZAN_HKM_NAME_S(String zan_hkm_name_s) {
		zAN_HKM_NAME_S = zan_hkm_name_s;
	}

	/**
	 * @return zAN_KESI_DEN_DATE
	 */
	public Date getZAN_KESI_DEN_DATE() {
		return zAN_KESI_DEN_DATE;
	}

	/**
	 * @param zan_kesi_den_date
	 */
	public void setZAN_KESI_DEN_DATE(Date zan_kesi_den_date) {
		zAN_KESI_DEN_DATE = zan_kesi_den_date;
	}

	/**
	 * @return zAN_KESI_DEN_NO
	 */
	public String getZAN_KESI_DEN_NO() {
		return zAN_KESI_DEN_NO;
	}

	/**
	 * @param zan_kesi_den_no
	 */
	public void setZAN_KESI_DEN_NO(String zan_kesi_den_no) {
		zAN_KESI_DEN_NO = zan_kesi_den_no;
	}

	/**
	 * @return zAN_KESI_GYO_NO
	 */
	public int getZAN_KESI_GYO_NO() {
		return zAN_KESI_GYO_NO;
	}

	/**
	 * @param zan_kesi_gyo_no
	 */
	public void setZAN_KESI_GYO_NO(int zan_kesi_gyo_no) {
		zAN_KESI_GYO_NO = zan_kesi_gyo_no;
	}

	/**
	 * @return zAN_KESI_IN_KIN
	 */
	public BigDecimal getZAN_KESI_IN_KIN() {
		return zAN_KESI_IN_KIN;
	}

	/**
	 * @param zan_kesi_in_kin
	 */
	public void setZAN_KESI_IN_KIN(BigDecimal zan_kesi_in_kin) {
		zAN_KESI_IN_KIN = zan_kesi_in_kin;
	}

	/**
	 * @return zAN_KESI_KIN
	 */
	public BigDecimal getZAN_KESI_KIN() {
		return zAN_KESI_KIN;
	}

	/**
	 * @param zan_kesi_kin
	 */
	public void setZAN_KESI_KIN(BigDecimal zan_kesi_kin) {
		zAN_KESI_KIN = zan_kesi_kin;
	}

	/**
	 * @return zAN_KMK_CODE
	 */
	public String getZAN_KMK_CODE() {
		return zAN_KMK_CODE;
	}

	/**
	 * @param zan_kmk_code
	 */
	public void setZAN_KMK_CODE(String zan_kmk_code) {
		zAN_KMK_CODE = zan_kmk_code;
	}

	/**
	 * @return zAN_KMK_NAME_S
	 */
	public String getZAN_KMK_NAME_S() {
		return zAN_KMK_NAME_S;
	}

	/**
	 * @param zan_kmk_name_s
	 */
	public void setZAN_KMK_NAME_S(String zan_kmk_name_s) {
		zAN_KMK_NAME_S = zan_kmk_name_s;
	}

	/**
	 * @return zAN_NYU_GYO_NO
	 */
	public Integer getZAN_NYU_GYO_NO() {
		return zAN_NYU_GYO_NO;
	}

	/**
	 * @param zan_nyu_gyo_no
	 */
	public void setZAN_NYU_GYO_NO(Integer zan_nyu_gyo_no) {
		zAN_NYU_GYO_NO = zan_nyu_gyo_no;
	}

	/**
	 * @return zAN_NYU_KESI_KIN
	 */
	public BigDecimal getZAN_NYU_KESI_KIN() {
		return zAN_NYU_KESI_KIN;
	}

	/**
	 * @param zan_nyu_kesi_kin
	 */
	public void setZAN_NYU_KESI_KIN(BigDecimal zan_nyu_kesi_kin) {
		zAN_NYU_KESI_KIN = zan_nyu_kesi_kin;
	}

	/**
	 * @return zAN_NYU_UTK_NO
	 */
	public String getZAN_NYU_UTK_NO() {
		return zAN_NYU_UTK_NO;
	}

	/**
	 * @param zan_nyu_utk_no
	 */
	public void setZAN_NYU_UTK_NO(String zan_nyu_utk_no) {
		zAN_NYU_UTK_NO = zan_nyu_utk_no;
	}

	/**
	 * @return zAN_SEI_DEN_DATE
	 */
	public Date getZAN_SEI_DEN_DATE() {
		return zAN_SEI_DEN_DATE;
	}

	/**
	 * @param zan_sei_den_date
	 */
	public void setZAN_SEI_DEN_DATE(Date zan_sei_den_date) {
		zAN_SEI_DEN_DATE = zan_sei_den_date;
	}

	/**
	 * @return zAN_SEI_DEN_NO
	 */
	public String getZAN_SEI_DEN_NO() {
		return zAN_SEI_DEN_NO;
	}

	/**
	 * @param zan_sei_den_no
	 */
	public void setZAN_SEI_DEN_NO(String zan_sei_den_no) {
		zAN_SEI_DEN_NO = zan_sei_den_no;
	}

	/**
	 * @return zAN_SEI_GYO_NO
	 */
	public int getZAN_SEI_GYO_NO() {
		return zAN_SEI_GYO_NO;
	}

	/**
	 * @param zan_sei_gyo_no
	 */
	public void setZAN_SEI_GYO_NO(int zan_sei_gyo_no) {
		zAN_SEI_GYO_NO = zan_sei_gyo_no;
	}

	/**
	 * @return zAN_SEI_IN_KIN
	 */
	public BigDecimal getZAN_SEI_IN_KIN() {
		return zAN_SEI_IN_KIN;
	}

	/**
	 * @param zan_sei_in_kin
	 */
	public void setZAN_SEI_IN_KIN(BigDecimal zan_sei_in_kin) {
		zAN_SEI_IN_KIN = zan_sei_in_kin;
	}

	/**
	 * @return zAN_SEI_KIN
	 */
	public BigDecimal getZAN_SEI_KIN() {
		return zAN_SEI_KIN;
	}

	/**
	 * @param zan_sei_kin
	 */
	public void setZAN_SEI_KIN(BigDecimal zan_sei_kin) {
		zAN_SEI_KIN = zan_sei_kin;
	}

	/**
	 * @return zAN_SEI_NO
	 */
	public String getZAN_SEI_NO() {
		return zAN_SEI_NO;
	}

	/**
	 * @param zan_sei_no
	 */
	public void setZAN_SEI_NO(String zan_sei_no) {
		zAN_SEI_NO = zan_sei_no;
	}

	/**
	 * @return zAN_SIHA_KBN
	 */
	public String getZAN_SIHA_KBN() {
		return zAN_SIHA_KBN;
	}

	/**
	 * @param zan_siha_kbn
	 */
	public void setZAN_SIHA_KBN(String zan_siha_kbn) {
		zAN_SIHA_KBN = zan_siha_kbn;
	}

	/**
	 * @return zAN_SOUSAI_DATE
	 */
	public Date getZAN_SOUSAI_DATE() {
		return zAN_SOUSAI_DATE;
	}

	/**
	 * @param zan_sousai_date
	 */
	public void setZAN_SOUSAI_DATE(Date zan_sousai_date) {
		zAN_SOUSAI_DATE = zan_sousai_date;
	}

	/**
	 * @return zAN_SOUSAI_FLG
	 */
	public int getZAN_SOUSAI_FLG() {
		return zAN_SOUSAI_FLG;
	}

	/**
	 * @param zan_sousai_flg
	 */
	public void setZAN_SOUSAI_FLG(int zan_sousai_flg) {
		zAN_SOUSAI_FLG = zan_sousai_flg;
	}

	/**
	 * @return zAN_TEK
	 */
	public String getZAN_TEK() {
		return zAN_TEK;
	}

	/**
	 * @param zan_tek
	 */
	public void setZAN_TEK(String zan_tek) {
		zAN_TEK = zan_tek;
	}

	/**
	 * @return zAN_TRI_CODE
	 */
	public String getZAN_TRI_CODE() {
		return zAN_TRI_CODE;
	}

	/**
	 * @param zan_tri_code
	 */
	public void setZAN_TRI_CODE(String zan_tri_code) {
		zAN_TRI_CODE = zan_tri_code;
	}

	/**
	 * @return zAN_TRI_NAME
	 */
	public String getZAN_TRI_NAME() {
		return zAN_TRI_NAME;
	}

	/**
	 * @param zan_tri_name
	 */
	public void setZAN_TRI_NAME(String zan_tri_name) {
		zAN_TRI_NAME = zan_tri_name;
	}

	/**
	 * @return zAN_TRI_NAME_S
	 */
	public String getZAN_TRI_NAME_S() {
		return zAN_TRI_NAME_S;
	}

	/**
	 * @param zan_tri_name_s
	 */
	public void setZAN_TRI_NAME_S(String zan_tri_name_s) {
		zAN_TRI_NAME_S = zan_tri_name_s;
	}

	/**
	 * @return zAN_UKM_CODE
	 */
	public String getZAN_UKM_CODE() {
		return zAN_UKM_CODE;
	}

	/**
	 * @param zan_ukm_code
	 */
	public void setZAN_UKM_CODE(String zan_ukm_code) {
		zAN_UKM_CODE = zan_ukm_code;
	}

	/**
	 * @return zAN_UKM_NAME_S
	 */
	public String getZAN_UKM_NAME_S() {
		return zAN_UKM_NAME_S;
	}

	/**
	 * @param zan_ukm_name_s
	 */
	public void setZAN_UKM_NAME_S(String zan_ukm_name_s) {
		zAN_UKM_NAME_S = zan_ukm_name_s;
	}

	/**
	 * @return zAN_UTK_NO
	 */
	public String getZAN_UTK_NO() {
		return zAN_UTK_NO;
	}

	/**
	 * @param zan_utk_no
	 */
	public void setZAN_UTK_NO(String zan_utk_no) {
		zAN_UTK_NO = zan_utk_no;
	}

	/**
	 * @return zAN_CUR_RATE_POW
	 */
	public int getZAN_CUR_RATE_POW() {
		return zAN_CUR_RATE_POW;
	}

	/**
	 * @param zan_cur_rate_pow
	 */
	public void setZAN_CUR_RATE_POW(int zan_cur_rate_pow) {
		zAN_CUR_RATE_POW = zan_cur_rate_pow;
	}

	/**
	 * アドオンコード1の取得
	 * 
	 * @return aDD_CODE_1 アドオンコード1
	 */
	public String getADD_CODE_1() {
		return aDD_CODE_1;
	}

	/**
	 * アドオンコード1の設定
	 * 
	 * @param aDD_CODE_1 アドオンコード1
	 */
	public void setADD_CODE_1(String aDD_CODE_1) {
		this.aDD_CODE_1 = aDD_CODE_1;
	}

	/**
	 * アドオンコード2の取得
	 * 
	 * @return aDD_CODE_2 アドオンコード2
	 */
	public String getADD_CODE_2() {
		return aDD_CODE_2;
	}

	/**
	 * アドオンコード2の設定
	 * 
	 * @param aDD_CODE_2 アドオンコード2
	 */
	public void setADD_CODE_2(String aDD_CODE_2) {
		this.aDD_CODE_2 = aDD_CODE_2;
	}

	/**
	 * アドオンコード3の取得
	 * 
	 * @return aDD_CODE_3 アドオンコード3
	 */
	public String getADD_CODE_3() {
		return aDD_CODE_3;
	}

	/**
	 * アドオンコード3の設定
	 * 
	 * @param aDD_CODE_3 アドオンコード3
	 */
	public void setADD_CODE_3(String aDD_CODE_3) {
		this.aDD_CODE_3 = aDD_CODE_3;
	}

	/**
	 * アドオンコード4の取得
	 * 
	 * @return aDD_CODE_4 アドオンコード4
	 */
	public String getADD_CODE_4() {
		return aDD_CODE_4;
	}

	/**
	 * アドオンコード4の設定
	 * 
	 * @param aDD_CODE_4 アドオンコード4
	 */
	public void setADD_CODE_4(String aDD_CODE_4) {
		this.aDD_CODE_4 = aDD_CODE_4;
	}

	/**
	 * アドオンコード5の取得
	 * 
	 * @return aDD_CODE_5 アドオンコード5
	 */
	public String getADD_CODE_5() {
		return aDD_CODE_5;
	}

	/**
	 * アドオンコード5の設定
	 * 
	 * @param aDD_CODE_5 アドオンコード5
	 */
	public void setADD_CODE_5(String aDD_CODE_5) {
		this.aDD_CODE_5 = aDD_CODE_5;
	}

	/**
	 * アドオンコード6の取得
	 * 
	 * @return aDD_CODE_6 アドオンコード6
	 */
	public String getADD_CODE_6() {
		return aDD_CODE_6;
	}

	/**
	 * アドオンコード6の設定
	 * 
	 * @param aDD_CODE_6 アドオンコード6
	 */
	public void setADD_CODE_6(String aDD_CODE_6) {
		this.aDD_CODE_6 = aDD_CODE_6;
	}

	/**
	 * 追加名称1の取得
	 * 
	 * @return aDD_NAME_1 追加名称1
	 */
	public String getADD_NAME_1() {
		return aDD_NAME_1;
	}

	/**
	 * 追加名称1の設定
	 * 
	 * @param aDD_NAME_1 追加名称1
	 */
	public void setADD_NAME_1(String aDD_NAME_1) {
		this.aDD_NAME_1 = aDD_NAME_1;
	}

	/**
	 * 追加名称2の取得
	 * 
	 * @return aDD_NAME_2 追加名称2
	 */
	public String getADD_NAME_2() {
		return aDD_NAME_2;
	}

	/**
	 * 追加名称2の設定
	 * 
	 * @param aDD_NAME_2 追加名称2
	 */
	public void setADD_NAME_2(String aDD_NAME_2) {
		this.aDD_NAME_2 = aDD_NAME_2;
	}

	/**
	 * 追加名称3の取得
	 * 
	 * @return aDD_NAME_3 追加名称3
	 */
	public String getADD_NAME_3() {
		return aDD_NAME_3;
	}

	/**
	 * 追加名称3の設定
	 * 
	 * @param aDD_NAME_3 追加名称3
	 */
	public void setADD_NAME_3(String aDD_NAME_3) {
		this.aDD_NAME_3 = aDD_NAME_3;
	}

	/**
	 * 追加名称4の取得
	 * 
	 * @return aDD_NAME_4 追加名称4
	 */
	public String getADD_NAME_4() {
		return aDD_NAME_4;
	}

	/**
	 * 追加名称4の設定
	 * 
	 * @param aDD_NAME_4 追加名称4
	 */
	public void setADD_NAME_4(String aDD_NAME_4) {
		this.aDD_NAME_4 = aDD_NAME_4;
	}

	/**
	 * 追加名称5の取得
	 * 
	 * @return aDD_NAME_5 追加名称5
	 */
	public String getADD_NAME_5() {
		return aDD_NAME_5;
	}

	/**
	 * 追加名称5の設定
	 * 
	 * @param aDD_NAME_5 追加名称5
	 */
	public void setADD_NAME_5(String aDD_NAME_5) {
		this.aDD_NAME_5 = aDD_NAME_5;
	}

	/**
	 * 追加名称6の取得
	 * 
	 * @return aDD_NAME_6 追加名称6
	 */
	public String getADD_NAME_6() {
		return aDD_NAME_6;
	}

	/**
	 * 追加名称6の設定
	 * 
	 * @param aDD_NAME_6 追加名称6
	 */
	public void setADD_NAME_6(String aDD_NAME_6) {
		this.aDD_NAME_6 = aDD_NAME_6;
	}

}
