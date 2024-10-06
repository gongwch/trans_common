package jp.co.ais.trans2.model.balance;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * 債務残高クラス
 */
public class AP_ZAN extends TransferBase {

	/** テーブルＩＤ */
	public static final String TABLE = "AP_ZAN";

	/** 会社コード */
	protected String kAI_CODE;

	/** 会社略称 */
	protected String kAI_NAME_S;

	/** 伝票日付 */
	protected Date zAN_DEN_DATE;

	/** 伝票番号 */
	protected String zAN_DEN_NO;

	/** 支払行番号 */
	protected int zAN_GYO_NO;

	/** 貸借区分 */
	protected int zAN_DC_KBN;

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

	/** 計上部門コード */
	protected String zAN_DEP_CODE;

	/** 計上部門略称 */
	protected String zAN_DEP_NAME_S;

	/** 受付部門コード */
	protected String zAN_UKE_DEP_CODE;

	/** 伝票摘要コード */
	protected String zAN_TEK_CODE;

	/** 伝票摘要 */
	protected String zAN_TEK;

	/** 支払先コード */
	protected String zAN_SIHA_CODE;

	/** 支払先略称 */
	protected String zAN_SIHA_NAME_S;

	/** 邦貨支払金額 */
	protected BigDecimal zAN_KIN;

	/** 支払予定日 */
	protected Date zAN_SIHA_DATE;

	/** 支払実績日 */
	protected Date zAN_JSK_DATE;

	/** 振込日 */
	protected Date zAN_FURI_DATE;

	/** 支払区分 */
	protected String zAN_SIHA_KBN;

	/** 支払方法コード */
	protected String zAN_HOH_CODE;

	/** 保留区分 */
	protected int zAN_HORYU_KBN;

	/** 支払決済区分 */
	protected int zAN_KESAI_KBN;

	/** 請求書番号 */
	protected String zAN_SEI_NO;

	/** データ区分 */
	protected String zAN_DATA_KBN;

	/** 支払内部コード */
	protected String zAN_NAI_CODE;

	/** 振込手数料区分 */
	protected int zAN_FURI_TESU_KBN;

	/** 郵送区分 */
	protected int zAN_YUSO_KBN;

	/** 郵送手数料区分 */
	protected int zAN_YUSO_TESU_KBN;

	/** 手形サイト */
	protected int zAN_TEG_SAITO;

	/** 手形支払期日 */
	protected Date zAN_TEG_SIHA_KIJITU;

	/** 振込振出銀行口座コード */
	protected String zAN_FURI_CBK_CODE;

	/** 振込振出銀行コード */
	protected String zAN_FURI_BNK_CODE;

	/** 振込振出支店コード */
	protected String zAN_FURI_STN_CODE;

	/** 手形振出銀行口座コード */
	protected String zAN_TEG_CBK_CODE;

	/** 手形振出銀行コード */
	protected String zAN_TEG_BNK_CODE;

	/** 手形振出支店コード */
	protected String zAN_TEG_STN_CODE;

	/** 明細出力区分 */
	protected int zAN_LIST_KBN;

	/** 支払伝票番号 */
	protected String zAN_SIHA_DEN_NO;

	/** 支払伝票行番号 */
	protected int zAN_SIHA_GYO_NO;

	/** 支払下限額区分 */
	protected int zAN_SIHA_KAGEN_KBN;

	/** 手形支払下限額区分 */
	protected int zAN_TEG_KAGEN_KBN;

	/** 振込手数料下限額区分 */
	protected int zAN_FURI_KAGEN_KBN;

	/** 登録日付 */
	protected Date zAN_INP_DATE;

	/** 更新日付 */
	protected Date uPD_DATE;

	/** プログラムＩＤ */
	protected String pRG_ID;

	/** ユーザーＩＤ */
	protected String uSR_ID;

	/** 取引先条件ｺｰﾄﾞ */
	protected String zAN_TJK_CODE;

	/** 通貨ｺｰﾄﾞ */
	protected String zAN_CUR_CODE;

	/** 通貨小数点桁数 */
	protected int zAN_CUR_DEC_KETA;

	/** 通貨レート係数 */
	protected int zAN_CUR_RATE_POW;

	/** ﾚｰﾄ */
	protected BigDecimal zAN_CUR_RATE;

	/** 入力支払金額 */
	protected BigDecimal zAN_IN_SIHA_KIN;

	/** ｼｽﾃﾑ区分 */
	protected String zAN_SYS_KBN;

	/** 伝票種別 */
	protected String zAN_DEN_SYU;

	/** 受付NO */
	protected String zAN_UTK_NO;

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

	/** 伝票仕訳行番号 */
	protected int zAN_SWK_GYO_NO;

	/**
	 * @return TABLE
	 */
	public static String getTABLE() {
		return TABLE;
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
	 * @return zAN_DC_KBN
	 */
	public int getZAN_DC_KBN() {
		return zAN_DC_KBN;
	}

	/**
	 * @param zan_dc_kbn
	 */
	public void setZAN_DC_KBN(int zan_dc_kbn) {
		zAN_DC_KBN = zan_dc_kbn;
	}

	/**
	 * @return zAN_DEN_DATE
	 */
	public Date getZAN_DEN_DATE() {
		return zAN_DEN_DATE;
	}

	/**
	 * @param zan_den_date
	 */
	public void setZAN_DEN_DATE(Date zan_den_date) {
		zAN_DEN_DATE = zan_den_date;
	}

	/**
	 * @return zAN_DEN_NO
	 */
	public String getZAN_DEN_NO() {
		return zAN_DEN_NO;
	}

	/**
	 * @param zan_den_no
	 */
	public void setZAN_DEN_NO(String zan_den_no) {
		zAN_DEN_NO = zan_den_no;
	}

	/**
	 * @return zAN_DEN_SYU
	 */
	public String getZAN_DEN_SYU() {
		return zAN_DEN_SYU;
	}

	/**
	 * @param zan_den_syu
	 */
	public void setZAN_DEN_SYU(String zan_den_syu) {
		zAN_DEN_SYU = zan_den_syu;
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
	 * @return zAN_FURI_BNK_CODE
	 */
	public String getZAN_FURI_BNK_CODE() {
		return zAN_FURI_BNK_CODE;
	}

	/**
	 * @param zan_furi_bnk_code
	 */
	public void setZAN_FURI_BNK_CODE(String zan_furi_bnk_code) {
		zAN_FURI_BNK_CODE = zan_furi_bnk_code;
	}

	/**
	 * @return zAN_FURI_CBK_CODE
	 */
	public String getZAN_FURI_CBK_CODE() {
		return zAN_FURI_CBK_CODE;
	}

	/**
	 * @param zan_furi_cbk_code
	 */
	public void setZAN_FURI_CBK_CODE(String zan_furi_cbk_code) {
		zAN_FURI_CBK_CODE = zan_furi_cbk_code;
	}

	/**
	 * @return zAN_FURI_DATE
	 */
	public Date getZAN_FURI_DATE() {
		return zAN_FURI_DATE;
	}

	/**
	 * @param zan_furi_date
	 */
	public void setZAN_FURI_DATE(Date zan_furi_date) {
		zAN_FURI_DATE = zan_furi_date;
	}

	/**
	 * @return zAN_FURI_KAGEN_KBN
	 */
	public int getZAN_FURI_KAGEN_KBN() {
		return zAN_FURI_KAGEN_KBN;
	}

	/**
	 * @param zan_furi_kagen_kbn
	 */
	public void setZAN_FURI_KAGEN_KBN(int zan_furi_kagen_kbn) {
		zAN_FURI_KAGEN_KBN = zan_furi_kagen_kbn;
	}

	/**
	 * @return zAN_FURI_STN_CODE
	 */
	public String getZAN_FURI_STN_CODE() {
		return zAN_FURI_STN_CODE;
	}

	/**
	 * @param zan_furi_stn_code
	 */
	public void setZAN_FURI_STN_CODE(String zan_furi_stn_code) {
		zAN_FURI_STN_CODE = zan_furi_stn_code;
	}

	/**
	 * @return zAN_FURI_TESU_KBN
	 */
	public int getZAN_FURI_TESU_KBN() {
		return zAN_FURI_TESU_KBN;
	}

	/**
	 * @param zan_furi_tesu_kbn
	 */
	public void setZAN_FURI_TESU_KBN(int zan_furi_tesu_kbn) {
		zAN_FURI_TESU_KBN = zan_furi_tesu_kbn;
	}

	/**
	 * @return zAN_GYO_NO
	 */
	public int getZAN_GYO_NO() {
		return zAN_GYO_NO;
	}

	/**
	 * @param zan_gyo_no
	 */
	public void setZAN_GYO_NO(int zan_gyo_no) {
		zAN_GYO_NO = zan_gyo_no;
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
	 * @return zAN_HOH_CODE
	 */
	public String getZAN_HOH_CODE() {
		return zAN_HOH_CODE;
	}

	/**
	 * @param zan_hoh_code
	 */
	public void setZAN_HOH_CODE(String zan_hoh_code) {
		zAN_HOH_CODE = zan_hoh_code;
	}

	/**
	 * @return zAN_HORYU_KBN
	 */
	public int getZAN_HORYU_KBN() {
		return zAN_HORYU_KBN;
	}

	/**
	 * @param zan_horyu_kbn
	 */
	public void setZAN_HORYU_KBN(int zan_horyu_kbn) {
		zAN_HORYU_KBN = zan_horyu_kbn;
	}

	/**
	 * @return zAN_IN_SIHA_KIN
	 */
	public BigDecimal getZAN_IN_SIHA_KIN() {
		return zAN_IN_SIHA_KIN;
	}

	/**
	 * @param zan_in_siha_kin
	 */
	public void setZAN_IN_SIHA_KIN(BigDecimal zan_in_siha_kin) {
		zAN_IN_SIHA_KIN = zan_in_siha_kin;
	}

	/**
	 * @return zAN_INP_DATE
	 */
	public Date getZAN_INP_DATE() {
		return zAN_INP_DATE;
	}

	/**
	 * @param zan_inp_date
	 */
	public void setZAN_INP_DATE(Date zan_inp_date) {
		zAN_INP_DATE = zan_inp_date;
	}

	/**
	 * @return zAN_JSK_DATE
	 */
	public Date getZAN_JSK_DATE() {
		return zAN_JSK_DATE;
	}

	/**
	 * @param zan_jsk_date
	 */
	public void setZAN_JSK_DATE(Date zan_jsk_date) {
		zAN_JSK_DATE = zan_jsk_date;
	}

	/**
	 * @return zAN_KESAI_KBN
	 */
	public int getZAN_KESAI_KBN() {
		return zAN_KESAI_KBN;
	}

	/**
	 * @param zan_kesai_kbn
	 */
	public void setZAN_KESAI_KBN(int zan_kesai_kbn) {
		zAN_KESAI_KBN = zan_kesai_kbn;
	}

	/**
	 * @return zAN_KIN
	 */
	public BigDecimal getZAN_KIN() {
		return zAN_KIN;
	}

	/**
	 * @param zan_kin
	 */
	public void setZAN_KIN(BigDecimal zan_kin) {
		zAN_KIN = zan_kin;
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
	 * @return zAN_LIST_KBN
	 */
	public int getZAN_LIST_KBN() {
		return zAN_LIST_KBN;
	}

	/**
	 * @param zan_list_kbn
	 */
	public void setZAN_LIST_KBN(int zan_list_kbn) {
		zAN_LIST_KBN = zan_list_kbn;
	}

	/**
	 * @return zAN_NAI_CODE
	 */
	public String getZAN_NAI_CODE() {
		return zAN_NAI_CODE;
	}

	/**
	 * @param zan_nai_code
	 */
	public void setZAN_NAI_CODE(String zan_nai_code) {
		zAN_NAI_CODE = zan_nai_code;
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
	 * @return zAN_SIHA_CODE
	 */
	public String getZAN_SIHA_CODE() {
		return zAN_SIHA_CODE;
	}

	/**
	 * @param zan_siha_code
	 */
	public void setZAN_SIHA_CODE(String zan_siha_code) {
		zAN_SIHA_CODE = zan_siha_code;
	}

	/**
	 * @return zAN_SIHA_DATE
	 */
	public Date getZAN_SIHA_DATE() {
		return zAN_SIHA_DATE;
	}

	/**
	 * @param zan_siha_date
	 */
	public void setZAN_SIHA_DATE(Date zan_siha_date) {
		zAN_SIHA_DATE = zan_siha_date;
	}

	/**
	 * @return zAN_SIHA_DEN_NO
	 */
	public String getZAN_SIHA_DEN_NO() {
		return zAN_SIHA_DEN_NO;
	}

	/**
	 * @param zan_siha_den_no
	 */
	public void setZAN_SIHA_DEN_NO(String zan_siha_den_no) {
		zAN_SIHA_DEN_NO = zan_siha_den_no;
	}

	/**
	 * @return zAN_SIHA_GYO_NO
	 */
	public int getZAN_SIHA_GYO_NO() {
		return zAN_SIHA_GYO_NO;
	}

	/**
	 * @param zan_siha_gyo_no
	 */
	public void setZAN_SIHA_GYO_NO(int zan_siha_gyo_no) {
		zAN_SIHA_GYO_NO = zan_siha_gyo_no;
	}

	/**
	 * @return zAN_SIHA_KAGEN_KBN
	 */
	public int getZAN_SIHA_KAGEN_KBN() {
		return zAN_SIHA_KAGEN_KBN;
	}

	/**
	 * @param zan_siha_kagen_kbn
	 */
	public void setZAN_SIHA_KAGEN_KBN(int zan_siha_kagen_kbn) {
		zAN_SIHA_KAGEN_KBN = zan_siha_kagen_kbn;
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
	 * @return zAN_SIHA_NAME_S
	 */
	public String getZAN_SIHA_NAME_S() {
		return zAN_SIHA_NAME_S;
	}

	/**
	 * @param zan_siha_name_s
	 */
	public void setZAN_SIHA_NAME_S(String zan_siha_name_s) {
		zAN_SIHA_NAME_S = zan_siha_name_s;
	}

	/**
	 * @return zAN_SYS_KBN
	 */
	public String getZAN_SYS_KBN() {
		return zAN_SYS_KBN;
	}

	/**
	 * @param zan_sys_kbn
	 */
	public void setZAN_SYS_KBN(String zan_sys_kbn) {
		zAN_SYS_KBN = zan_sys_kbn;
	}

	/**
	 * @return zAN_TEG_BNK_CODE
	 */
	public String getZAN_TEG_BNK_CODE() {
		return zAN_TEG_BNK_CODE;
	}

	/**
	 * @param zan_teg_bnk_code
	 */
	public void setZAN_TEG_BNK_CODE(String zan_teg_bnk_code) {
		zAN_TEG_BNK_CODE = zan_teg_bnk_code;
	}

	/**
	 * @return zAN_TEG_CBK_CODE
	 */
	public String getZAN_TEG_CBK_CODE() {
		return zAN_TEG_CBK_CODE;
	}

	/**
	 * @param zan_teg_cbk_code
	 */
	public void setZAN_TEG_CBK_CODE(String zan_teg_cbk_code) {
		zAN_TEG_CBK_CODE = zan_teg_cbk_code;
	}

	/**
	 * @return zAN_TEG_KAGEN_KBN
	 */
	public int getZAN_TEG_KAGEN_KBN() {
		return zAN_TEG_KAGEN_KBN;
	}

	/**
	 * @param zan_teg_kagen_kbn
	 */
	public void setZAN_TEG_KAGEN_KBN(int zan_teg_kagen_kbn) {
		zAN_TEG_KAGEN_KBN = zan_teg_kagen_kbn;
	}

	/**
	 * @return zAN_TEG_SAITO
	 */
	public int getZAN_TEG_SAITO() {
		return zAN_TEG_SAITO;
	}

	/**
	 * @param zan_teg_saito
	 */
	public void setZAN_TEG_SAITO(int zan_teg_saito) {
		zAN_TEG_SAITO = zan_teg_saito;
	}

	/**
	 * @return zAN_TEG_SIHA_KIJITU
	 */
	public Date getZAN_TEG_SIHA_KIJITU() {
		return zAN_TEG_SIHA_KIJITU;
	}

	/**
	 * @param zan_teg_siha_kijitu
	 */
	public void setZAN_TEG_SIHA_KIJITU(Date zan_teg_siha_kijitu) {
		zAN_TEG_SIHA_KIJITU = zan_teg_siha_kijitu;
	}

	/**
	 * @return zAN_TEG_STN_CODE
	 */
	public String getZAN_TEG_STN_CODE() {
		return zAN_TEG_STN_CODE;
	}

	/**
	 * @param zan_teg_stn_code
	 */
	public void setZAN_TEG_STN_CODE(String zan_teg_stn_code) {
		zAN_TEG_STN_CODE = zan_teg_stn_code;
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
	 * @return zAN_TEK_CODE
	 */
	public String getZAN_TEK_CODE() {
		return zAN_TEK_CODE;
	}

	/**
	 * @param zan_tek_code
	 */
	public void setZAN_TEK_CODE(String zan_tek_code) {
		zAN_TEK_CODE = zan_tek_code;
	}

	/**
	 * @return zAN_TJK_CODE
	 */
	public String getZAN_TJK_CODE() {
		return zAN_TJK_CODE;
	}

	/**
	 * @param zan_tjk_code
	 */
	public void setZAN_TJK_CODE(String zan_tjk_code) {
		zAN_TJK_CODE = zan_tjk_code;
	}

	/**
	 * @return zAN_UKE_DEP_CODE
	 */
	public String getZAN_UKE_DEP_CODE() {
		return zAN_UKE_DEP_CODE;
	}

	/**
	 * @param zan_uke_dep_code
	 */
	public void setZAN_UKE_DEP_CODE(String zan_uke_dep_code) {
		zAN_UKE_DEP_CODE = zan_uke_dep_code;
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
	 * @return zAN_YUSO_KBN
	 */
	public int getZAN_YUSO_KBN() {
		return zAN_YUSO_KBN;
	}

	/**
	 * @param zan_yuso_kbn
	 */
	public void setZAN_YUSO_KBN(int zan_yuso_kbn) {
		zAN_YUSO_KBN = zan_yuso_kbn;
	}

	/**
	 * @return zAN_YUSO_TESU_KBN
	 */
	public int getZAN_YUSO_TESU_KBN() {
		return zAN_YUSO_TESU_KBN;
	}

	/**
	 * @param zan_yuso_tesu_kbn
	 */
	public void setZAN_YUSO_TESU_KBN(int zan_yuso_tesu_kbn) {
		zAN_YUSO_TESU_KBN = zan_yuso_tesu_kbn;
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

	/**
	 * 伝票仕訳行番号の取得
	 * 
	 * @return zAN_SWK_GYO_NO 伝票仕訳行番号
	 */
	public int getZAN_SWK_GYO_NO() {
		return zAN_SWK_GYO_NO;
	}

	/**
	 * 伝票仕訳行番号の設定
	 * 
	 * @param zAN_SWK_GYO_NO 伝票仕訳行番号
	 */
	public void setZAN_SWK_GYO_NO(int zAN_SWK_GYO_NO) {
		this.zAN_SWK_GYO_NO = zAN_SWK_GYO_NO;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof AP_ZAN)) {
			return false;
		}

		if (super.equals(obj)) {
			return true;
		}

		AP_ZAN trg = (AP_ZAN) obj;

		boolean isEqual = Util.avoidNull(kAI_CODE).equals(Util.avoidNull(trg.getKAI_CODE()));
		isEqual &= Util.avoidNull(zAN_DEN_NO).equals(Util.avoidNull(trg.getZAN_DEN_NO()));
		isEqual &= Util.avoidNull(zAN_GYO_NO).equals(Util.avoidNull(trg.getZAN_GYO_NO()));

		return isEqual;
	}
}
