package jp.co.ais.trans.master.entity;

import java.math.BigDecimal;
import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 債務伝票結合エンティティ
 */
public class ApSlipUnit extends TransferBase {

	/** 会社コード */
	private String kAI_CODE = "";

	/** 伝票日付 */
	private Date dEN_DATE;

	/** 伝票番号 */
	private String dEN_NO = "";

	/** 科目コード */
	private String hDR_KMK_CODE;

	/** 補助科目コード */
	private String hDR_HKM_CODE;

	/** 内訳科目コード */
	private String hDR_UKM_CODE;

	/** 計上部門コード */
	private String hDR_DEP_CODE;

	/** 取引先コード */
	private String hDR_TRI_CODE;

	/** 社員コード */
	private String hDR_EMP_CODE;

	/** 支払区分 */
	private String hDR_SIHA_KBN;

	/** 支払日 */
	private Date hDR_SIHA_DATE;

	/** 支払方法コード */
	private String hDR_HOH_CODE;

	/** 保留区分 */
	private int hDR_HORYU_KBN;

	/** 仮払金額 */
	private BigDecimal hDR_KARI_KIN;

	/** 経費金額 */
	private BigDecimal hDR_KEIHI_KIN;

	/** 支払金額 */
	private BigDecimal hDR_SIHA_KIN;

	/** 仮払申請伝票番号 */
	private String hDR_KARI_DR_DEN_NO;

	/** 仮払精算伝票番号 */
	private String hDR_KARI_CR_DEN_NO;

	/** 支払決済区分 */
	private int hDR_KESAI_KBN;

	/** 証憑番号 */
	private String sEI_NO;

	/** データ区分 */
	private String dATA_KBN = "";

	/** 受付部門コード */
	private String hDR_UKE_DEP_CODE;

	/** 伝票摘要コード */
	private String hDR_TEK_CODE;

	/** 伝票摘要 */
	private String hDR_TEK = "";

	/** 訂正前伝票番号 */
	private String hDR_BEFORE_DEN_NO;

	/** 更新区分 */
	private int uPD_KBN;

	/** 承認者コード */
	private String hDR_SYO_EMP_CODE;

	/** 承認日 */
	private Date hDR_SYO_DATE;

	/** 依頼者 社員コード */
	private String hDR_IRAI_EMP_CODE;

	/** 依頼部門コード */
	private String hDR_IRAI_DEP_CODE;

	/** 依頼日 */
	private Date hDR_IRAI_DATE;

	/** 仮払計上部門コード */
	private String hDR_KARI_DEP_CODE;

	/** 排他フラグ */
	private int hDR_SHR_KBN;

	/** 決算区分 */
	private int KSN_KBN;

	/** ユーザーID */
	private String uSR_ID;

	/** プログラムID */
	private String pRG_ID;

	/** 取引先条件コード */
	private String hDR_TJK_CODE;

	/** 通貨コード */
	private String hDR_CUR_CODE;

	/** レート */
	private BigDecimal hDR_CUR_RATE;

	/** 入力仮払金額 */
	private BigDecimal hDR_IN_KARI_KIN;

	/** 入力経費金額 */
	private BigDecimal hDR_IN_KEIHI_KIN;

	/** 入力支払金額 */
	private BigDecimal hDR_IN_SIHA_KIN;

	/** システム区分 */
	private String hDR_SYS_KBN;

	/** 伝票種別 */
	private String dEN_SYU;

	/** 会社間付替伝票区分 */
	private int hDR_TUKE_KBN;

	/** 修正回数 */
	private Integer hDR_UPD_CNT;

	/** 請求書通貨コード */
	private String hDR_INV_CUR_CODE;

	/** 請求書通貨レート */
	private BigDecimal hDR_INV_CUR_RATE;

	/** 請求書通貨金額 */
	private BigDecimal hDR_INV_KIN;

	/** 銀行口座コード */
	private String hDR_CBK_CODE;

	/** 精算予定日 */
	private Date hDR_SSY_DATE;

	/** 受付NO */
	private String hDR_UTK_NO;

	/** 仮払通貨コード */
	private String hDR_KARI_CUR_CODE;

	/** 仮払通貨レート */
	private BigDecimal hDR_KARI_CUR_RATE;

	// ここから明細

	/** 行番号 */
	private int sWK_GYO_NO;

	/** 年度 */
	private int sWK_NENDO;

	/** 月度 */
	private int sWK_TUKIDO;

	/** 貸借区分 */
	private int sWK_DC_KBN;

	/** 科目コード */
	private String sWK_KMK_CODE = "";

	/** 補助科目コード */
	private String sWK_HKM_CODE;

	/** 内訳科目コード */
	private String sWK_UKM_CODE;

	/** 計上部門コード */
	private String sWK_DEP_CODE = "";

	/** 税区分 */
	private int sWK_ZEI_KBN;

	/** 邦貨金額 */
	private BigDecimal sWK_KIN;

	/** 消費税金額 */
	private BigDecimal sWK_ZEI_KIN;

	/** 消費税コード */
	private String sWK_ZEI_CODE;

	/** 消費税名称 */
	private String sWK_ZEI_NAME;

	/** 税率 */
	private BigDecimal sWK_ZEI_RATE;

	/** 行摘要コード */
	private String sWK_GYO_TEK_CODE;

	/** 行摘要 */
	private String sWK_GYO_TEK;

	/** 取引先コード */
	private String sWK_TRI_CODE;

	/** 社員コード */
	private String sWK_EMP_CODE;

	/** 管理1コード */
	private String sWK_KNR_CODE_1;

	/** 管理2コード */
	private String sWK_KNR_CODE_2;

	/** 管理3コード */
	private String sWK_KNR_CODE_3;

	/** 管理4コード */
	private String sWK_KNR_CODE_4;

	/** 管理5コード */
	private String sWK_KNR_CODE_5;

	/** 管理6コード */
	private String sWK_KNR_CODE_6;

	/** 非会計明細1 */
	private String sWK_HM_1;

	/** 非会計明細2 */
	private String sWK_HM_2;

	/** 非会計明細3 */
	private String sWK_HM_3;

	/** 自動仕訳区分 */
	private int sWK_AUTO_KBN;

	/** 相手科目コード */
	private String sWK_AT_KMK_CODE;

	/** 相手補助科目コード */
	private String sWK_AT_HKM_CODE;

	/** 相手内訳科目コード */
	private String sWK_AT_UKM_CODE;

	/** 相手部門コード */
	private String sWK_AT_DEP_CODE;

	/** 計上会社コード */
	private String sWK_K_KAI_CODE;

	/** 計上会社名称 */
	private String sWK_K_KAI_NAME;

	/** 通貨コード */
	private String sWK_CUR_CODE;

	/** レート */
	private BigDecimal sWK_CUR_RATE;

	/** 入力金額 */
	private BigDecimal sWK_IN_KIN;

	/** 会社間付替伝票区分 */
	private int sWK_TUKE_KBN;

	/** 入力消費税額 */
	private BigDecimal sWK_IN_ZEI_KIN;

	/** 消込区分 */
	private int sWK_KESI_KBN;

	/** 消込伝票日付 */
	private Date sWK_KESI_DATE;

	/** 消込伝票番号 */
	private String sWK_KESI_DEN_NO;

	/** 相殺日 */
	private Date sWK_SOUSAI_DATE;

	/** 相殺伝票番号 */
	private String sWK_SOUSAI_DEN_NO;

	/** 発生日 */
	private Date sWK_HAS_DATE;

	// ここから結合 ヘッダー関連
	/** 科目名称 */
	private String hDR_KMK_NAME;

	/** 補助科目名称 */
	private String hDR_HKM_NAME;

	/** 内訳科目名称 */
	private String hDR_UKM_NAME;

	/** 科目略称 */
	private String hDR_KMK_NAME_S;

	/** 補助科目略称 */
	private String hDR_HKM_NAME_S;

	/** 内訳科目略称 */
	private String hDR_UKM_NAME_S;

	/** 支払方法名称 */
	private String hDR_HOH_NAME;

	/** 支払方法内部コード */
	private String hDR_HOH_NAI_CODE;

	/** 会社名称 */
	private String kAI_NAME;

	/** 会社略称 */
	private String kAI_NAME_S;

	/** 会社コード 基軸通貨 */
	private String bASE_CUR_CODE;

	/** 会社コード 基軸通貨 小数点桁数 */
	private String bASE_CUR_DEC_KETA;

	/** 依頼部門名称 */
	private String hDR_IRAI_DEP_NAME;

	/** 依頼部門略称 */
	private String hDR_IRAI_DEP_NAME_S;

	/** 依頼者 社員名称 */
	private String hDR_IRAI_EMP_NAME;

	/** 依頼者 社員略称 */
	private String hDR_IRAI_EMP_NAME_S;

	/** 社員名称 */
	private String hDR_EMP_NAME;

	/** 社員略称 */
	private String hDR_EMP_NAME_S;

	/** 銀行口座 預金種目 */
	private String hDR_CBK_YKN_KBN;

	/** 銀行口座コード 銀行名 */
	private String hDR_CBK_BANK_NAME;

	/** 銀行口座コード 銀行コード */
	private String HDR_CBK_BNK_CODE;

	/** 銀行口座コード 支店名 */
	private String hDR_CBK_BANK_STN_NAME;

	/** 銀行口座コード 支店コード */
	private String HDR_CBK_STN_CODE;

	/** 伝票種別 検索名称 */
	private String dEN_SYU_NAME_K;

	/** 科目名称（帳票出力用） */
	private String cMP_KMK_NAME;

	/** 補助科目名称（帳票出力用） */
	private String cMP_HKM_NAME;

	/** 内訳科目名称（帳票出力用） */
	private String cMP_UKM_NAME;

	/** 計上部門名称 */
	private String hDR_DEP_NAME;

	/** 計上部門略称 */
	private String hDR_DEP_NAME_S;

	/** 通貨コード 小数点桁数(ヘッダー) */
	private String hDR_CUR_DEC_KETA;

	// ここから結合 ジャーナル関連

	/** 科目名称 */
	private String sWK_KMK_NAME;

	/** 補助科目名称 */
	private String sWK_HKM_NAME;

	/** 内訳科目名称 */
	private String sWK_UKM_NAME;

	/** 科目略称 */
	private String sWK_KMK_NAME_S;

	/** 補助科目略称 */
	private String sWK_HKM_NAME_S;

	/** 内訳科目略称 */
	private String sWK_UKM_NAME_S;

	/** 取引先名称 */
	private String sWK_TRI_NAME;

	/** 社員名称 */
	private String sWK_EMP_NAME;

	/** 取引先略称 */
	private String sWK_TRI_NAME_S;

	/** 社員略称 */
	private String sWK_EMP_NAME_S;

	/** 管理1名称 */
	private String sWK_KNR_NAME_1;

	/** 管理2名称 */
	private String sWK_KNR_NAME_2;

	/** 管理3名称 */
	private String sWK_KNR_NAME_3;

	/** 管理4名称 */
	private String sWK_KNR_NAME_4;

	/** 管理5名称 */
	private String sWK_KNR_NAME_5;

	/** 管理6名称 */
	private String sWK_KNR_NAME_6;

	/** 管理1略称 */
	private String sWK_KNR_NAME_S_1;

	/** 管理2略称 */
	private String sWK_KNR_NAME_S_2;

	/** 管理3略称 */
	private String sWK_KNR_NAME_S_3;

	/** 管理4略称 */
	private String sWK_KNR_NAME_S_4;

	/** 管理5略称 */
	private String sWK_KNR_NAME_S_5;

	/** 管理6略称 */
	private String sWK_KNR_NAME_S_6;

	/** 通貨コード 小数点桁数 */
	private String sWK_CUR_DEC_KETA;

	/** 明細 計上部門略称 */
	private String sWK_DEP_NAME_S;

	// ここから追加項目

	/** 精算済仮払入力金額 */
	private BigDecimal hDR_SSY_KARI_IN_KIN;

	/** 精算済仮払邦貨金額 */
	private BigDecimal hDR_SSY_KARI_KIN;

	/** 仮払日 */
	private Date tMP_PAY_DATE;

	/**
	 * 基軸通貨を取得する
	 * 
	 * @return 基軸通貨
	 */
	public String getBASE_CUR_CODE() {
		return bASE_CUR_CODE;
	}

	/**
	 * 基軸通貨を設定する
	 * 
	 * @param base_cur_code
	 */
	public void setBASE_CUR_CODE(String base_cur_code) {
		bASE_CUR_CODE = base_cur_code;
	}

	/**
	 * 基軸通貨の小数点桁数を取得する
	 * 
	 * @return 基軸通貨の小数点桁数
	 */
	public String getBASE_CUR_DEC_KETA() {
		return bASE_CUR_DEC_KETA;
	}

	/**
	 * 基軸通貨の小数点桁数を設定する
	 * 
	 * @param base_cur_dec_keta
	 */
	public void setBASE_CUR_DEC_KETA(String base_cur_dec_keta) {
		bASE_CUR_DEC_KETA = base_cur_dec_keta;
	}

	/**
	 * データ区分を取得する
	 * 
	 * @return データ区分
	 */
	public String getDATA_KBN() {
		return dATA_KBN;
	}

	/**
	 * データ区分を設定する
	 * 
	 * @param data_kbn
	 */
	public void setDATA_KBN(String data_kbn) {
		dATA_KBN = data_kbn;
	}

	/**
	 * 伝票日付を取得する
	 * 
	 * @return 伝票日付
	 */
	public Date getDEN_DATE() {
		return dEN_DATE;
	}

	/**
	 * 伝票日付を設定する
	 * 
	 * @param den_date
	 */
	public void setDEN_DATE(Date den_date) {
		dEN_DATE = den_date;
	}

	/**
	 * 伝票番号を取得する
	 * 
	 * @return 伝票番号
	 */
	public String getDEN_NO() {
		return dEN_NO;
	}

	/**
	 * 伝票番号を設定する
	 * 
	 * @param den_no
	 */
	public void setDEN_NO(String den_no) {
		dEN_NO = den_no;
	}

	/**
	 * 伝票種別を取得する
	 * 
	 * @return 伝票種別
	 */
	public String getDEN_SYU() {
		return dEN_SYU;
	}

	/**
	 * 伝票種別を設定する
	 * 
	 * @param den_syu
	 */
	public void setDEN_SYU(String den_syu) {
		dEN_SYU = den_syu;
	}

	/**
	 * 訂正前伝票番号 を取得する
	 * 
	 * @return 訂正前伝票番号
	 */
	public String getHDR_BEFORE_DEN_NO() {
		return hDR_BEFORE_DEN_NO;
	}

	/**
	 * 訂正前伝票番号を設定する
	 * 
	 * @param hdr_before_den_no
	 */
	public void setHDR_BEFORE_DEN_NO(String hdr_before_den_no) {
		hDR_BEFORE_DEN_NO = hdr_before_den_no;
	}

	/**
	 * 銀行口座コード 銀行名を取得する
	 * 
	 * @return 銀行口座コード 銀行名
	 */
	public String getHDR_CBK_BANK_NAME() {
		return hDR_CBK_BANK_NAME;
	}

	/**
	 * 銀行口座コード 銀行名を設定する
	 * 
	 * @param hdr_cbk_bank_name
	 */
	public void setHDR_CBK_BANK_NAME(String hdr_cbk_bank_name) {
		hDR_CBK_BANK_NAME = hdr_cbk_bank_name;
	}

	/**
	 * 銀行口座コード 支店名を取得する
	 * 
	 * @return 銀行口座コード 支店名
	 */
	public String getHDR_CBK_BANK_STN_NAME() {
		return hDR_CBK_BANK_STN_NAME;
	}

	/**
	 * 銀行口座コード 支店名を設定する
	 * 
	 * @param hdr_cbk_bank_stn_name
	 */
	public void setHDR_CBK_BANK_STN_NAME(String hdr_cbk_bank_stn_name) {
		hDR_CBK_BANK_STN_NAME = hdr_cbk_bank_stn_name;
	}

	/**
	 * 銀行口座コードを取得する
	 * 
	 * @return 銀行口座コード
	 */
	public String getHDR_CBK_CODE() {
		return hDR_CBK_CODE;
	}

	/**
	 * 銀行口座コードを設定する
	 * 
	 * @param hdr_cbk_code
	 */
	public void setHDR_CBK_CODE(String hdr_cbk_code) {
		hDR_CBK_CODE = hdr_cbk_code;
	}

	/**
	 * 銀行口座 預金種目を取得する
	 * 
	 * @return 銀行口座 預金種目
	 */
	public String getHDR_CBK_YKN_KBN() {
		return hDR_CBK_YKN_KBN;
	}

	/**
	 * 銀行口座 預金種目を設定する
	 * 
	 * @param hdr_cbk_ykn_kbn
	 */
	public void setHDR_CBK_YKN_KBN(String hdr_cbk_ykn_kbn) {
		hDR_CBK_YKN_KBN = hdr_cbk_ykn_kbn;
	}

	/**
	 * 通貨コードを取得する
	 * 
	 * @return 通貨コード
	 */
	public String getHDR_CUR_CODE() {
		return hDR_CUR_CODE;
	}

	/**
	 * 通貨コードを設定する
	 * 
	 * @param hdr_cur_code
	 */
	public void setHDR_CUR_CODE(String hdr_cur_code) {
		hDR_CUR_CODE = hdr_cur_code;
	}

	/**
	 * レートを取得する
	 * 
	 * @return レート
	 */
	public BigDecimal getHDR_CUR_RATE() {
		return hDR_CUR_RATE;
	}

	/**
	 * レートを設定する
	 * 
	 * @param hdr_cur_rate
	 */
	public void setHDR_CUR_RATE(BigDecimal hdr_cur_rate) {
		hDR_CUR_RATE = hdr_cur_rate;
	}

	/**
	 * 計上部門コードを取得する
	 * 
	 * @return 計上部門コード
	 */
	public String getHDR_DEP_CODE() {
		return hDR_DEP_CODE;
	}

	/**
	 * 計上部門コードを設定する
	 * 
	 * @param hdr_dep_code
	 */
	public void setHDR_DEP_CODE(String hdr_dep_code) {
		hDR_DEP_CODE = hdr_dep_code;
	}

	/**
	 * 社員コードを取得する
	 * 
	 * @return 社員コード
	 */
	public String getHDR_EMP_CODE() {
		return hDR_EMP_CODE;
	}

	/**
	 * 社員コードを設定する
	 * 
	 * @param hdr_emp_code
	 */
	public void setHDR_EMP_CODE(String hdr_emp_code) {
		hDR_EMP_CODE = hdr_emp_code;
	}

	/**
	 * 補助科目コードを取得する
	 * 
	 * @return 補助科目コード
	 */
	public String getHDR_HKM_CODE() {
		return hDR_HKM_CODE;
	}

	/**
	 * 補助科目コードを設定する
	 * 
	 * @param hdr_hkm_code
	 */
	public void setHDR_HKM_CODE(String hdr_hkm_code) {
		hDR_HKM_CODE = hdr_hkm_code;
	}

	/**
	 * 補助科目名称を取得する
	 * 
	 * @return 補助科目名称
	 */
	public String getHDR_HKM_NAME() {
		return hDR_HKM_NAME;
	}

	/**
	 * 補助科目名称を設定する
	 * 
	 * @param hdr_hkm_name
	 */
	public void setHDR_HKM_NAME(String hdr_hkm_name) {
		hDR_HKM_NAME = hdr_hkm_name;
	}

	/**
	 * 補助科目略称を取得する
	 * 
	 * @return 補助科目略称
	 */
	public String getHDR_HKM_NAME_S() {
		return hDR_HKM_NAME_S;
	}

	/**
	 * 補助科目略称を設定する
	 * 
	 * @param hdr_hkm_name_s
	 */
	public void setHDR_HKM_NAME_S(String hdr_hkm_name_s) {
		hDR_HKM_NAME_S = hdr_hkm_name_s;
	}

	/**
	 * 支払方法コードを取得する
	 * 
	 * @return 支払方法コード
	 */
	public String getHDR_HOH_CODE() {
		return hDR_HOH_CODE;
	}

	/**
	 * 支払方法コードを設定する
	 * 
	 * @param hdr_hoh_code
	 */
	public void setHDR_HOH_CODE(String hdr_hoh_code) {
		hDR_HOH_CODE = hdr_hoh_code;
	}

	/**
	 * 支払方法内部コードを取得する
	 * 
	 * @return 支払方法内部コード
	 */
	public String getHDR_HOH_NAI_CODE() {
		return hDR_HOH_NAI_CODE;
	}

	/**
	 * 支払方法内部コードを設定する
	 * 
	 * @param hdr_hoh_nai_code
	 */
	public void setHDR_HOH_NAI_CODE(String hdr_hoh_nai_code) {
		hDR_HOH_NAI_CODE = hdr_hoh_nai_code;
	}

	/**
	 * 支払方法名称を取得する
	 * 
	 * @return 支払方法名称
	 */
	public String getHDR_HOH_NAME() {
		return hDR_HOH_NAME;
	}

	/**
	 * 支払方法名称を設定する
	 * 
	 * @param hdr_hoh_name
	 */
	public void setHDR_HOH_NAME(String hdr_hoh_name) {
		hDR_HOH_NAME = hdr_hoh_name;
	}

	/**
	 * 保留区分を取得する
	 * 
	 * @return 保留区分
	 */
	public int getHDR_HORYU_KBN() {
		return hDR_HORYU_KBN;
	}

	/**
	 * 保留区分を設定する
	 * 
	 * @param hdr_horyu_kbn
	 */
	public void setHDR_HORYU_KBN(int hdr_horyu_kbn) {
		hDR_HORYU_KBN = hdr_horyu_kbn;
	}

	/**
	 * 入力仮払金額を取得する
	 * 
	 * @return 入力仮払金額
	 */
	public BigDecimal getHDR_IN_KARI_KIN() {
		return hDR_IN_KARI_KIN;
	}

	/**
	 * 入力仮払金額を設定する
	 * 
	 * @param hdr_in_kari_kin
	 */
	public void setHDR_IN_KARI_KIN(BigDecimal hdr_in_kari_kin) {
		hDR_IN_KARI_KIN = hdr_in_kari_kin;
	}

	/**
	 * 入力経費金額を取得する
	 * 
	 * @return 入力経費金額
	 */
	public BigDecimal getHDR_IN_KEIHI_KIN() {
		return hDR_IN_KEIHI_KIN;
	}

	/**
	 * 入力経費金額を設定する
	 * 
	 * @param hdr_in_keihi_kin
	 */
	public void setHDR_IN_KEIHI_KIN(BigDecimal hdr_in_keihi_kin) {
		hDR_IN_KEIHI_KIN = hdr_in_keihi_kin;
	}

	/**
	 * 入力支払金額を取得する
	 * 
	 * @return 入力支払金額
	 */
	public BigDecimal getHDR_IN_SIHA_KIN() {
		return hDR_IN_SIHA_KIN;
	}

	/**
	 * 入力支払金額を設定する
	 * 
	 * @param hdr_in_siha_kin
	 */
	public void setHDR_IN_SIHA_KIN(BigDecimal hdr_in_siha_kin) {
		hDR_IN_SIHA_KIN = hdr_in_siha_kin;
	}

	/**
	 * 請求書通貨コードを取得する
	 * 
	 * @return 請求書通貨コード
	 */
	public String getHDR_INV_CUR_CODE() {
		return hDR_INV_CUR_CODE;
	}

	/**
	 * 請求書通貨コードを設定する
	 * 
	 * @param hdr_inv_cur_code
	 */
	public void setHDR_INV_CUR_CODE(String hdr_inv_cur_code) {
		hDR_INV_CUR_CODE = hdr_inv_cur_code;
	}

	/**
	 * 請求書通貨レートを取得する
	 * 
	 * @return 請求書通貨レート
	 */
	public BigDecimal getHDR_INV_CUR_RATE() {
		return hDR_INV_CUR_RATE;
	}

	/**
	 * 請求書通貨レートを設定する
	 * 
	 * @param hdr_inv_cur_rate
	 */
	public void setHDR_INV_CUR_RATE(BigDecimal hdr_inv_cur_rate) {
		hDR_INV_CUR_RATE = hdr_inv_cur_rate;
	}

	/**
	 * 請求書通貨金額を取得する
	 * 
	 * @return 請求書通貨金額
	 */
	public BigDecimal getHDR_INV_KIN() {
		return hDR_INV_KIN;
	}

	/**
	 * 請求書通貨金額を設定する
	 * 
	 * @param hdr_inv_kin
	 */
	public void setHDR_INV_KIN(BigDecimal hdr_inv_kin) {
		hDR_INV_KIN = hdr_inv_kin;
	}

	/**
	 * 依頼日を取得する
	 * 
	 * @return 依頼日
	 */
	public Date getHDR_IRAI_DATE() {
		return hDR_IRAI_DATE;
	}

	/**
	 * 依頼日を設定する
	 * 
	 * @param hdr_irai_date
	 */
	public void setHDR_IRAI_DATE(Date hdr_irai_date) {
		hDR_IRAI_DATE = hdr_irai_date;
	}

	/**
	 * 依頼部門コードを取得する
	 * 
	 * @return 依頼部門コード
	 */
	public String getHDR_IRAI_DEP_CODE() {
		return hDR_IRAI_DEP_CODE;
	}

	/**
	 * 依頼部門コードを設定する
	 * 
	 * @param hdr_irai_dep_code
	 */
	public void setHDR_IRAI_DEP_CODE(String hdr_irai_dep_code) {
		hDR_IRAI_DEP_CODE = hdr_irai_dep_code;
	}

	/**
	 * 依頼部門名を取得する
	 * 
	 * @return 依頼部門名
	 */
	public String getHDR_IRAI_DEP_NAME() {
		return hDR_IRAI_DEP_NAME;
	}

	/**
	 * 依頼部門名を設定する
	 * 
	 * @param hdr_irai_dep_name
	 */
	public void setHDR_IRAI_DEP_NAME(String hdr_irai_dep_name) {
		hDR_IRAI_DEP_NAME = hdr_irai_dep_name;
	}

	/**
	 * 依頼部門略称を取得する
	 * 
	 * @return 依頼部門略称
	 */
	public String getHDR_IRAI_DEP_NAME_S() {
		return hDR_IRAI_DEP_NAME_S;
	}

	/**
	 * 依頼部門略称を設定する
	 * 
	 * @param hdr_irai_dep_name_s
	 */
	public void setHDR_IRAI_DEP_NAME_S(String hdr_irai_dep_name_s) {
		hDR_IRAI_DEP_NAME_S = hdr_irai_dep_name_s;
	}

	/**
	 * 依頼社員コードを取得する
	 * 
	 * @return 依頼社員コード
	 */
	public String getHDR_IRAI_EMP_CODE() {
		return hDR_IRAI_EMP_CODE;
	}

	/**
	 * 依頼社員コードを設定する
	 * 
	 * @param hdr_irai_emp_code
	 */
	public void setHDR_IRAI_EMP_CODE(String hdr_irai_emp_code) {
		hDR_IRAI_EMP_CODE = hdr_irai_emp_code;
	}

	/**
	 * 依頼社員名を取得する
	 * 
	 * @return 依頼社員名
	 */
	public String getHDR_IRAI_EMP_NAME() {
		return hDR_IRAI_EMP_NAME;
	}

	/**
	 * 依頼社員名を設定する
	 * 
	 * @param hdr_irai_emp_name
	 */
	public void setHDR_IRAI_EMP_NAME(String hdr_irai_emp_name) {
		hDR_IRAI_EMP_NAME = hdr_irai_emp_name;
	}

	/**
	 * 依頼社員略称を取得する
	 * 
	 * @return 依頼社員略称
	 */
	public String getHDR_IRAI_EMP_NAME_S() {
		return hDR_IRAI_EMP_NAME_S;
	}

	/**
	 * 依頼社員略称を設定する
	 * 
	 * @param hdr_irai_emp_name_s
	 */
	public void setHDR_IRAI_EMP_NAME_S(String hdr_irai_emp_name_s) {
		hDR_IRAI_EMP_NAME_S = hdr_irai_emp_name_s;
	}

	/**
	 * 仮払精算伝票番号を取得する
	 * 
	 * @return 仮払精算伝票番号
	 */
	public String getHDR_KARI_CR_DEN_NO() {
		return hDR_KARI_CR_DEN_NO;
	}

	/**
	 * 仮払精算伝票番号を設定する
	 * 
	 * @param hdr_kari_cr_den_no
	 */
	public void setHDR_KARI_CR_DEN_NO(String hdr_kari_cr_den_no) {
		hDR_KARI_CR_DEN_NO = hdr_kari_cr_den_no;
	}

	/**
	 * 仮払通貨コードを取得する
	 * 
	 * @return 仮払通貨コード
	 */
	public String getHDR_KARI_CUR_CODE() {
		return hDR_KARI_CUR_CODE;
	}

	/**
	 * 仮払通貨コードを設定する
	 * 
	 * @param hdr_kari_cur_code
	 */
	public void setHDR_KARI_CUR_CODE(String hdr_kari_cur_code) {
		hDR_KARI_CUR_CODE = hdr_kari_cur_code;
	}

	/**
	 * 仮払通貨レートを取得する
	 * 
	 * @return 仮払通貨レート
	 */
	public BigDecimal getHDR_KARI_CUR_RATE() {
		return hDR_KARI_CUR_RATE;
	}

	/**
	 * 仮払通貨レートを設定する
	 * 
	 * @param hdr_kari_cur_rate
	 */
	public void setHDR_KARI_CUR_RATE(BigDecimal hdr_kari_cur_rate) {
		hDR_KARI_CUR_RATE = hdr_kari_cur_rate;
	}

	/**
	 * 仮払計上部門コードを取得する
	 * 
	 * @return 仮払計上部門コード
	 */
	public String getHDR_KARI_DEP_CODE() {
		return hDR_KARI_DEP_CODE;
	}

	/**
	 * 仮払計上部門コードを設定する
	 * 
	 * @param hdr_kari_dep_code
	 */
	public void setHDR_KARI_DEP_CODE(String hdr_kari_dep_code) {
		hDR_KARI_DEP_CODE = hdr_kari_dep_code;
	}

	/**
	 * 仮払申請伝票番号を取得する
	 * 
	 * @return 仮払申請伝票番号
	 */
	public String getHDR_KARI_DR_DEN_NO() {
		return hDR_KARI_DR_DEN_NO;
	}

	/**
	 * 仮払申請伝票番号を設定する
	 * 
	 * @param hdr_kari_dr_den_no
	 */
	public void setHDR_KARI_DR_DEN_NO(String hdr_kari_dr_den_no) {
		hDR_KARI_DR_DEN_NO = hdr_kari_dr_den_no;
	}

	/**
	 * 仮払金額を取得する
	 * 
	 * @return 仮払金額
	 */
	public BigDecimal getHDR_KARI_KIN() {
		return hDR_KARI_KIN;
	}

	/**
	 * 仮払金額を設定する
	 * 
	 * @param hdr_kari_kin
	 */
	public void setHDR_KARI_KIN(BigDecimal hdr_kari_kin) {
		hDR_KARI_KIN = hdr_kari_kin;
	}

	/**
	 * 経費金額を取得する
	 * 
	 * @return 経費金額
	 */
	public BigDecimal getHDR_KEIHI_KIN() {
		return hDR_KEIHI_KIN;
	}

	/**
	 * 経費金額を設定する
	 * 
	 * @param hdr_keihi_kin
	 */
	public void setHDR_KEIHI_KIN(BigDecimal hdr_keihi_kin) {
		hDR_KEIHI_KIN = hdr_keihi_kin;
	}

	/**
	 * 支払決済区分を取得する
	 * 
	 * @return 支払決済区分
	 */
	public int getHDR_KESAI_KBN() {
		return hDR_KESAI_KBN;
	}

	/**
	 * 支払決済区分を設定する
	 * 
	 * @param hdr_kesai_kbn
	 */
	public void setHDR_KESAI_KBN(int hdr_kesai_kbn) {
		hDR_KESAI_KBN = hdr_kesai_kbn;
	}

	/**
	 * 科目コードを取得する
	 * 
	 * @return 科目コード
	 */
	public String getHDR_KMK_CODE() {
		return hDR_KMK_CODE;
	}

	/**
	 * 科目コードを設定する
	 * 
	 * @param hdr_kmk_code
	 */
	public void setHDR_KMK_CODE(String hdr_kmk_code) {
		hDR_KMK_CODE = hdr_kmk_code;
	}

	/**
	 * 科目名称を取得する
	 * 
	 * @return 科目名称
	 */
	public String getHDR_KMK_NAME() {
		return hDR_KMK_NAME;
	}

	/**
	 * 科目名称を設定する
	 * 
	 * @param hdr_kmk_name
	 */
	public void setHDR_KMK_NAME(String hdr_kmk_name) {
		hDR_KMK_NAME = hdr_kmk_name;
	}

	/**
	 * 科目略称を取得する
	 * 
	 * @return 科目略称
	 */
	public String getHDR_KMK_NAME_S() {
		return hDR_KMK_NAME_S;
	}

	/**
	 * 科目略称を設定する
	 * 
	 * @param hdr_kmk_name_s
	 */
	public void setHDR_KMK_NAME_S(String hdr_kmk_name_s) {
		hDR_KMK_NAME_S = hdr_kmk_name_s;
	}

	/**
	 * 排他区分を取得する
	 * 
	 * @return 排他区分
	 */
	public int getHDR_SHR_KBN() {
		return hDR_SHR_KBN;
	}

	/**
	 * 排他区分を設定する
	 * 
	 * @param hdr_shr_kbn
	 */
	public void setHDR_SHR_KBN(int hdr_shr_kbn) {
		hDR_SHR_KBN = hdr_shr_kbn;
	}

	/**
	 * 支払日を取得する
	 * 
	 * @return 支払日
	 */
	public Date getHDR_SIHA_DATE() {
		return hDR_SIHA_DATE;
	}

	/**
	 * 支払日を設定する
	 * 
	 * @param hdr_siha_date
	 */
	public void setHDR_SIHA_DATE(Date hdr_siha_date) {
		hDR_SIHA_DATE = hdr_siha_date;
	}

	/**
	 * 支払区分を取得する
	 * 
	 * @return 支払区分
	 */
	public String getHDR_SIHA_KBN() {
		return hDR_SIHA_KBN;
	}

	/**
	 * 支払区分を設定する
	 * 
	 * @param hdr_siha_kbn
	 */
	public void setHDR_SIHA_KBN(String hdr_siha_kbn) {
		hDR_SIHA_KBN = hdr_siha_kbn;
	}

	/**
	 * 支払金額を取得する
	 * 
	 * @return 支払金額
	 */
	public BigDecimal getHDR_SIHA_KIN() {
		return hDR_SIHA_KIN;
	}

	/**
	 * 支払金額を設定する
	 * 
	 * @param hdr_siha_kin
	 */
	public void setHDR_SIHA_KIN(BigDecimal hdr_siha_kin) {
		hDR_SIHA_KIN = hdr_siha_kin;
	}

	/**
	 * 精算予定日を取得する
	 * 
	 * @return 精算予定日
	 */
	public Date getHDR_SSY_DATE() {
		return hDR_SSY_DATE;
	}

	/**
	 * 精算予定日を設定する
	 * 
	 * @param hdr_ssy_date
	 */
	public void setHDR_SSY_DATE(Date hdr_ssy_date) {
		hDR_SSY_DATE = hdr_ssy_date;
	}

	/**
	 * 承認日を取得する
	 * 
	 * @return 承認日
	 */
	public Date getHDR_SYO_DATE() {
		return hDR_SYO_DATE;
	}

	/**
	 * を設定する
	 * 
	 * @param hdr_syo_date
	 */
	public void setHDR_SYO_DATE(Date hdr_syo_date) {
		hDR_SYO_DATE = hdr_syo_date;
	}

	/**
	 * 承認者を取得する
	 * 
	 * @return 承認者
	 */
	public String getHDR_SYO_EMP_CODE() {
		return hDR_SYO_EMP_CODE;
	}

	/**
	 * 承認者を設定する
	 * 
	 * @param hdr_syo_emp_code
	 */
	public void setHDR_SYO_EMP_CODE(String hdr_syo_emp_code) {
		hDR_SYO_EMP_CODE = hdr_syo_emp_code;
	}

	/**
	 * システム区分を取得する
	 * 
	 * @return システム区分
	 */
	public String getHDR_SYS_KBN() {
		return hDR_SYS_KBN;
	}

	/**
	 * システム区分を設定する
	 * 
	 * @param hdr_sys_kbn
	 */
	public void setHDR_SYS_KBN(String hdr_sys_kbn) {
		hDR_SYS_KBN = hdr_sys_kbn;
	}

	/**
	 * 伝票摘要を取得する
	 * 
	 * @return 伝票摘要
	 */
	public String getHDR_TEK() {
		return hDR_TEK;
	}

	/**
	 * 伝票摘要を設定する
	 * 
	 * @param hdr_tek
	 */
	public void setHDR_TEK(String hdr_tek) {
		hDR_TEK = hdr_tek;
	}

	/**
	 * 伝票摘要コードを取得する
	 * 
	 * @return 伝票摘要コード
	 */
	public String getHDR_TEK_CODE() {
		return hDR_TEK_CODE;
	}

	/**
	 * 伝票摘要コードを設定する
	 * 
	 * @param hdr_tek_code
	 */
	public void setHDR_TEK_CODE(String hdr_tek_code) {
		hDR_TEK_CODE = hdr_tek_code;
	}

	/**
	 * 取引先条件コードを取得する
	 * 
	 * @return 取引先条件コード
	 */
	public String getHDR_TJK_CODE() {
		return hDR_TJK_CODE;
	}

	/**
	 * 取引先条件コードを設定する
	 * 
	 * @param hdr_tjk_code
	 */
	public void setHDR_TJK_CODE(String hdr_tjk_code) {
		hDR_TJK_CODE = hdr_tjk_code;
	}

	/**
	 * 取引先コードを取得する
	 * 
	 * @return 取引先コード
	 */
	public String getHDR_TRI_CODE() {
		return hDR_TRI_CODE;
	}

	/**
	 * 取引先コードを設定する
	 * 
	 * @param hdr_tri_code
	 */
	public void setHDR_TRI_CODE(String hdr_tri_code) {
		hDR_TRI_CODE = hdr_tri_code;
	}

	/**
	 * 会社間付替区分を取得する
	 * 
	 * @return 会社間付替区分
	 */
	public int getHDR_TUKE_KBN() {
		return hDR_TUKE_KBN;
	}

	/**
	 * 会社間付替区分を設定する
	 * 
	 * @param hdr_tuke_kbn
	 */
	public void setHDR_TUKE_KBN(int hdr_tuke_kbn) {
		hDR_TUKE_KBN = hdr_tuke_kbn;
	}

	/**
	 * 受付部門コードを取得する
	 * 
	 * @return 受付部門コード
	 */
	public String getHDR_UKE_DEP_CODE() {
		return hDR_UKE_DEP_CODE;
	}

	/**
	 * 受付部門コードを設定する
	 * 
	 * @param hdr_uke_dep_code
	 */
	public void setHDR_UKE_DEP_CODE(String hdr_uke_dep_code) {
		hDR_UKE_DEP_CODE = hdr_uke_dep_code;
	}

	/**
	 * 内訳科目コードを取得する
	 * 
	 * @return 内訳科目コード
	 */
	public String getHDR_UKM_CODE() {
		return hDR_UKM_CODE;
	}

	/**
	 * 内訳科目コードを設定する
	 * 
	 * @param hdr_ukm_code
	 */
	public void setHDR_UKM_CODE(String hdr_ukm_code) {
		hDR_UKM_CODE = hdr_ukm_code;
	}

	/**
	 * 内訳科目名称を取得する
	 * 
	 * @return 内訳科目名称
	 */
	public String getHDR_UKM_NAME() {
		return hDR_UKM_NAME;
	}

	/**
	 * 内訳科目名称を設定する
	 * 
	 * @param hdr_ukm_name
	 */
	public void setHDR_UKM_NAME(String hdr_ukm_name) {
		hDR_UKM_NAME = hdr_ukm_name;
	}

	/**
	 * 内訳科目略称を取得する
	 * 
	 * @return 内訳科目略称
	 */
	public String getHDR_UKM_NAME_S() {
		return hDR_UKM_NAME_S;
	}

	/**
	 * 内訳科目略称を設定する
	 * 
	 * @param hdr_ukm_name_s
	 */
	public void setHDR_UKM_NAME_S(String hdr_ukm_name_s) {
		hDR_UKM_NAME_S = hdr_ukm_name_s;
	}

	/**
	 * 修正回数を取得する
	 * 
	 * @return 修正回数
	 */
	public Integer getHDR_UPD_CNT() {
		return hDR_UPD_CNT;
	}

	/**
	 * 修正回数を設定する
	 * 
	 * @param hdr_upd_cnt
	 */
	public void setHDR_UPD_CNT(Integer hdr_upd_cnt) {
		hDR_UPD_CNT = hdr_upd_cnt;
	}

	/**
	 * 受付NOを取得する
	 * 
	 * @return 受付NO
	 */
	public String getHDR_UTK_NO() {
		return hDR_UTK_NO;
	}

	/**
	 * 受付NOを設定する
	 * 
	 * @param hdr_utk_no
	 */
	public void setHDR_UTK_NO(String hdr_utk_no) {
		hDR_UTK_NO = hdr_utk_no;
	}

	/**
	 * 会社コードを取得する
	 * 
	 * @return 会社コード
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * 会社コードを設定する
	 * 
	 * @param kai_code
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * 会社名を取得する
	 * 
	 * @return 会社名
	 */
	public String getKAI_NAME() {
		return kAI_NAME;
	}

	/**
	 * 会社名を設定する
	 * 
	 * @param kai_name
	 */
	public void setKAI_NAME(String kai_name) {
		kAI_NAME = kai_name;
	}

	/**
	 * 会社略称を取得する
	 * 
	 * @return 会社略称
	 */
	public String getKAI_NAME_S() {
		return kAI_NAME_S;
	}

	/**
	 * 会社略称を設定する
	 * 
	 * @param kai_name_s
	 */
	public void setKAI_NAME_S(String kai_name_s) {
		kAI_NAME_S = kai_name_s;
	}

	/**
	 * 決算区分を取得する
	 * 
	 * @return 決算区分
	 */
	public int getKSN_KBN() {
		return KSN_KBN;
	}

	/**
	 * 決算区分を設定する
	 * 
	 * @param ksn_kbn
	 */
	public void setKSN_KBN(int ksn_kbn) {
		KSN_KBN = ksn_kbn;
	}

	/**
	 * PGIDを取得する
	 * 
	 * @return PGID
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * PGIDを設定する
	 * 
	 * @param prg_id
	 */
	public void setPRG_ID(String prg_id) {
		pRG_ID = prg_id;
	}

	/**
	 * 証憑番号を取得する
	 * 
	 * @return 証憑番号
	 */
	public String getSEI_NO() {
		return sEI_NO;
	}

	/**
	 * 証憑番号を設定する
	 * 
	 * @param sei_no
	 */
	public void setSEI_NO(String sei_no) {
		sEI_NO = sei_no;
	}

	/**
	 * 相手部門コードを取得する
	 * 
	 * @return 相手部門コード
	 */
	public String getSWK_AT_DEP_CODE() {
		return sWK_AT_DEP_CODE;
	}

	/**
	 * 相手部門コードを設定する
	 * 
	 * @param swk_at_dep_code
	 */
	public void setSWK_AT_DEP_CODE(String swk_at_dep_code) {
		sWK_AT_DEP_CODE = swk_at_dep_code;
	}

	/**
	 * 相手補助コードを取得する
	 * 
	 * @return 相手補助コード
	 */
	public String getSWK_AT_HKM_CODE() {
		return sWK_AT_HKM_CODE;
	}

	/**
	 * 相手補助コードを設定する
	 * 
	 * @param swk_at_hkm_code
	 */
	public void setSWK_AT_HKM_CODE(String swk_at_hkm_code) {
		sWK_AT_HKM_CODE = swk_at_hkm_code;
	}

	/**
	 * 相手科目コードを取得する
	 * 
	 * @return 相手科目コード
	 */
	public String getSWK_AT_KMK_CODE() {
		return sWK_AT_KMK_CODE;
	}

	/**
	 * 相手科目コードを設定する
	 * 
	 * @param swk_at_kmk_code
	 */
	public void setSWK_AT_KMK_CODE(String swk_at_kmk_code) {
		sWK_AT_KMK_CODE = swk_at_kmk_code;
	}

	/**
	 * 相手内訳科目を取得する
	 * 
	 * @return 相手内訳科目
	 */
	public String getSWK_AT_UKM_CODE() {
		return sWK_AT_UKM_CODE;
	}

	/**
	 * 相手内訳科目を設定する
	 * 
	 * @param swk_at_ukm_code
	 */
	public void setSWK_AT_UKM_CODE(String swk_at_ukm_code) {
		sWK_AT_UKM_CODE = swk_at_ukm_code;
	}

	/**
	 * 自動仕訳区分を取得する
	 * 
	 * @return 自動仕訳区分
	 */
	public int getSWK_AUTO_KBN() {
		return sWK_AUTO_KBN;
	}

	/**
	 * 自動仕訳区分を設定する
	 * 
	 * @param swk_auto_kbn
	 */
	public void setSWK_AUTO_KBN(int swk_auto_kbn) {
		sWK_AUTO_KBN = swk_auto_kbn;
	}

	/**
	 * 通貨コードを取得する
	 * 
	 * @return 通貨コード
	 */
	public String getSWK_CUR_CODE() {
		return sWK_CUR_CODE;
	}

	/**
	 * 通貨コードを設定する
	 * 
	 * @param swk_cur_code
	 */
	public void setSWK_CUR_CODE(String swk_cur_code) {
		sWK_CUR_CODE = swk_cur_code;
	}

	/**
	 * 通貨コード 小数点を取得する
	 * 
	 * @return 通貨コード 小数点
	 */
	public String getSWK_CUR_DEC_KETA() {
		return sWK_CUR_DEC_KETA;
	}

	/**
	 * 通貨コード 小数点を設定する
	 * 
	 * @param swk_cur_dec_keta
	 */
	public void setSWK_CUR_DEC_KETA(String swk_cur_dec_keta) {
		sWK_CUR_DEC_KETA = swk_cur_dec_keta;
	}

	/**
	 * レートを取得する
	 * 
	 * @return レート
	 */
	public BigDecimal getSWK_CUR_RATE() {
		return sWK_CUR_RATE;
	}

	/**
	 * レートを設定する
	 * 
	 * @param swk_cur_rate
	 */
	public void setSWK_CUR_RATE(BigDecimal swk_cur_rate) {
		sWK_CUR_RATE = swk_cur_rate;
	}

	/**
	 * 貸借区分を取得する
	 * 
	 * @return 貸借区分
	 */
	public int getSWK_DC_KBN() {
		return sWK_DC_KBN;
	}

	/**
	 * 貸借区分を設定する
	 * 
	 * @param swk_dc_kbn
	 */
	public void setSWK_DC_KBN(int swk_dc_kbn) {
		sWK_DC_KBN = swk_dc_kbn;
	}

	/**
	 * 部門コードを取得する
	 * 
	 * @return 部門コード
	 */
	public String getSWK_DEP_CODE() {
		return sWK_DEP_CODE;
	}

	/**
	 * 部門コードを設定する
	 * 
	 * @param swk_dep_code
	 */
	public void setSWK_DEP_CODE(String swk_dep_code) {
		sWK_DEP_CODE = swk_dep_code;
	}

	/**
	 * 社員コードを取得する
	 * 
	 * @return 社員コード
	 */
	public String getSWK_EMP_CODE() {
		return sWK_EMP_CODE;
	}

	/**
	 * 社員コードを設定する
	 * 
	 * @param swk_emp_code
	 */
	public void setSWK_EMP_CODE(String swk_emp_code) {
		sWK_EMP_CODE = swk_emp_code;
	}

	/**
	 * 社員名称を取得する
	 * 
	 * @return 社員名称
	 */
	public String getSWK_EMP_NAME() {
		return sWK_EMP_NAME;
	}

	/**
	 * 社員名称を設定する
	 * 
	 * @param swk_emp_name
	 */
	public void setSWK_EMP_NAME(String swk_emp_name) {
		sWK_EMP_NAME = swk_emp_name;
	}

	/**
	 * 社員略称を取得する
	 * 
	 * @return 社員略称
	 */
	public String getSWK_EMP_NAME_S() {
		return sWK_EMP_NAME_S;
	}

	/**
	 * 社員略称を設定する
	 * 
	 * @param swk_emp_name_s
	 */
	public void setSWK_EMP_NAME_S(String swk_emp_name_s) {
		sWK_EMP_NAME_S = swk_emp_name_s;
	}

	/**
	 * 行番号を取得する
	 * 
	 * @return 行番号
	 */
	public int getSWK_GYO_NO() {
		return sWK_GYO_NO;
	}

	/**
	 * 行番号を設定する
	 * 
	 * @param swk_gyo_no
	 */
	public void setSWK_GYO_NO(int swk_gyo_no) {
		sWK_GYO_NO = swk_gyo_no;
	}

	/**
	 * 行摘要を取得する
	 * 
	 * @return 行摘要
	 */
	public String getSWK_GYO_TEK() {
		return sWK_GYO_TEK;
	}

	/**
	 * 行摘要を設定する
	 * 
	 * @param swk_gyo_tek
	 */
	public void setSWK_GYO_TEK(String swk_gyo_tek) {
		sWK_GYO_TEK = swk_gyo_tek;
	}

	/**
	 * 行摘要コードを取得する
	 * 
	 * @return 行摘要コード
	 */
	public String getSWK_GYO_TEK_CODE() {
		return sWK_GYO_TEK_CODE;
	}

	/**
	 * 行摘要コードを設定する
	 * 
	 * @param swk_gyo_tek_code
	 */
	public void setSWK_GYO_TEK_CODE(String swk_gyo_tek_code) {
		sWK_GYO_TEK_CODE = swk_gyo_tek_code;
	}

	/**
	 * 発生日を取得する
	 * 
	 * @return 発生日
	 */
	public Date getSWK_HAS_DATE() {
		return sWK_HAS_DATE;
	}

	/**
	 * 発生日を設定する
	 * 
	 * @param swk_has_date
	 */
	public void setSWK_HAS_DATE(Date swk_has_date) {
		sWK_HAS_DATE = swk_has_date;
	}

	/**
	 * 補助科目コードを取得する
	 * 
	 * @return 補助科目コード
	 */
	public String getSWK_HKM_CODE() {
		return sWK_HKM_CODE;
	}

	/**
	 * 補助科目コードを設定する
	 * 
	 * @param swk_hkm_code
	 */
	public void setSWK_HKM_CODE(String swk_hkm_code) {
		sWK_HKM_CODE = swk_hkm_code;
	}

	/**
	 * 補助科目名称を取得する
	 * 
	 * @return 補助科目名称
	 */
	public String getSWK_HKM_NAME() {
		return sWK_HKM_NAME;
	}

	/**
	 * 補助科目名称を設定する
	 * 
	 * @param swk_hkm_name
	 */
	public void setSWK_HKM_NAME(String swk_hkm_name) {
		sWK_HKM_NAME = swk_hkm_name;
	}

	/**
	 * 補助科目略称を取得する
	 * 
	 * @return 補助科目略称
	 */
	public String getSWK_HKM_NAME_S() {
		return sWK_HKM_NAME_S;
	}

	/**
	 * 補助科目略称を設定する
	 * 
	 * @param swk_hkm_name_s
	 */
	public void setSWK_HKM_NAME_S(String swk_hkm_name_s) {
		sWK_HKM_NAME_S = swk_hkm_name_s;
	}

	/**
	 * 非会計明細1を取得する
	 * 
	 * @return 非会計明細1
	 */
	public String getSWK_HM_1() {
		return sWK_HM_1;
	}

	/**
	 * 非会計明細1を設定する
	 * 
	 * @param swk_hm_1
	 */
	public void setSWK_HM_1(String swk_hm_1) {
		sWK_HM_1 = swk_hm_1;
	}

	/**
	 * 非会計明細2を取得する
	 * 
	 * @return 非会計明細2
	 */
	public String getSWK_HM_2() {
		return sWK_HM_2;
	}

	/**
	 * 非会計明細2を設定する
	 * 
	 * @param swk_hm_2
	 */
	public void setSWK_HM_2(String swk_hm_2) {
		sWK_HM_2 = swk_hm_2;
	}

	/**
	 * 非会計明細3を取得する
	 * 
	 * @return 非会計明細3
	 */
	public String getSWK_HM_3() {
		return sWK_HM_3;
	}

	/**
	 * 非会計明細3を設定する
	 * 
	 * @param swk_hm_3
	 */
	public void setSWK_HM_3(String swk_hm_3) {
		sWK_HM_3 = swk_hm_3;
	}

	/**
	 * 入力金額を取得する
	 * 
	 * @return 入力金額
	 */
	public BigDecimal getSWK_IN_KIN() {
		return sWK_IN_KIN;
	}

	/**
	 * 入力金額を設定する
	 * 
	 * @param swk_in_kin
	 */
	public void setSWK_IN_KIN(BigDecimal swk_in_kin) {
		sWK_IN_KIN = swk_in_kin;
	}

	/**
	 * 入力消費税額を取得する
	 * 
	 * @return 入力消費税額
	 */
	public BigDecimal getSWK_IN_ZEI_KIN() {
		return sWK_IN_ZEI_KIN;
	}

	/**
	 * 入力消費税額を設定する
	 * 
	 * @param swk_in_zei_kin
	 */
	public void setSWK_IN_ZEI_KIN(BigDecimal swk_in_zei_kin) {
		sWK_IN_ZEI_KIN = swk_in_zei_kin;
	}

	/**
	 * 計上会社コードを取得する
	 * 
	 * @return 計上会社コード
	 */
	public String getSWK_K_KAI_CODE() {
		return sWK_K_KAI_CODE;
	}

	/**
	 * 計上会社コードを設定する
	 * 
	 * @param swk_k_kai_code
	 */
	public void setSWK_K_KAI_CODE(String swk_k_kai_code) {
		sWK_K_KAI_CODE = swk_k_kai_code;
	}

	/**
	 * 消込伝票日付を取得する
	 * 
	 * @return 消込伝票日付
	 */
	public Date getSWK_KESI_DATE() {
		return sWK_KESI_DATE;
	}

	/**
	 * 消込伝票日付を設定する
	 * 
	 * @param swk_kesi_date
	 */
	public void setSWK_KESI_DATE(Date swk_kesi_date) {
		sWK_KESI_DATE = swk_kesi_date;
	}

	/**
	 * 消込伝票番号を取得する
	 * 
	 * @return 消込伝票番号
	 */
	public String getSWK_KESI_DEN_NO() {
		return sWK_KESI_DEN_NO;
	}

	/**
	 * 消込伝票番号を設定する
	 * 
	 * @param swk_kesi_den_no
	 */
	public void setSWK_KESI_DEN_NO(String swk_kesi_den_no) {
		sWK_KESI_DEN_NO = swk_kesi_den_no;
	}

	/**
	 * 消込区分を取得する
	 * 
	 * @return 消込区分
	 */
	public int getSWK_KESI_KBN() {
		return sWK_KESI_KBN;
	}

	/**
	 * 消込区分を設定する
	 * 
	 * @param swk_kesi_kbn
	 */
	public void setSWK_KESI_KBN(int swk_kesi_kbn) {
		sWK_KESI_KBN = swk_kesi_kbn;
	}

	/**
	 * 邦貨金額を取得する
	 * 
	 * @return 邦貨金額
	 */
	public BigDecimal getSWK_KIN() {
		return sWK_KIN;
	}

	/**
	 * 邦貨金額を設定する
	 * 
	 * @param swk_kin
	 */
	public void setSWK_KIN(BigDecimal swk_kin) {
		sWK_KIN = swk_kin;
	}

	/**
	 * 科目コードを取得する
	 * 
	 * @return 科目コード
	 */
	public String getSWK_KMK_CODE() {
		return sWK_KMK_CODE;
	}

	/**
	 * 科目コードを設定する
	 * 
	 * @param swk_kmk_code
	 */
	public void setSWK_KMK_CODE(String swk_kmk_code) {
		sWK_KMK_CODE = swk_kmk_code;
	}

	/**
	 * 科目名称を取得する
	 * 
	 * @return 科目名称
	 */
	public String getSWK_KMK_NAME() {
		return sWK_KMK_NAME;
	}

	/**
	 * 科目名称を設定する
	 * 
	 * @param swk_kmk_name
	 */
	public void setSWK_KMK_NAME(String swk_kmk_name) {
		sWK_KMK_NAME = swk_kmk_name;
	}

	/**
	 * 科目略称を取得する
	 * 
	 * @return 科目略称
	 */
	public String getSWK_KMK_NAME_S() {
		return sWK_KMK_NAME_S;
	}

	/**
	 * 科目略称を設定する
	 * 
	 * @param swk_kmk_name_s
	 */
	public void setSWK_KMK_NAME_S(String swk_kmk_name_s) {
		sWK_KMK_NAME_S = swk_kmk_name_s;
	}

	/**
	 * 管理1コードを取得する
	 * 
	 * @return 管理1コード
	 */
	public String getSWK_KNR_CODE_1() {
		return sWK_KNR_CODE_1;
	}

	/**
	 * 管理1コードを設定する
	 * 
	 * @param swk_knr_code_1
	 */
	public void setSWK_KNR_CODE_1(String swk_knr_code_1) {
		sWK_KNR_CODE_1 = swk_knr_code_1;
	}

	/**
	 * 管理2コードを取得する
	 * 
	 * @return 管理2コード
	 */
	public String getSWK_KNR_CODE_2() {
		return sWK_KNR_CODE_2;
	}

	/**
	 * 管理2コードを設定する
	 * 
	 * @param swk_knr_code_2
	 */
	public void setSWK_KNR_CODE_2(String swk_knr_code_2) {
		sWK_KNR_CODE_2 = swk_knr_code_2;
	}

	/**
	 * 管理3コードを取得する
	 * 
	 * @return 管理1コード3
	 */
	public String getSWK_KNR_CODE_3() {
		return sWK_KNR_CODE_3;
	}

	/**
	 * 管理3コードを設定する
	 * 
	 * @param swk_knr_code_3
	 */
	public void setSWK_KNR_CODE_3(String swk_knr_code_3) {
		sWK_KNR_CODE_3 = swk_knr_code_3;
	}

	/**
	 * 管理4コードを取得する
	 * 
	 * @return 管理4コード
	 */
	public String getSWK_KNR_CODE_4() {
		return sWK_KNR_CODE_4;
	}

	/**
	 * 管理4コードを設定する
	 * 
	 * @param swk_knr_code_4
	 */
	public void setSWK_KNR_CODE_4(String swk_knr_code_4) {
		sWK_KNR_CODE_4 = swk_knr_code_4;
	}

	/**
	 * 管理5コードを取得する
	 * 
	 * @return 管理5コード
	 */
	public String getSWK_KNR_CODE_5() {
		return sWK_KNR_CODE_5;
	}

	/**
	 * 管理5コードを設定する
	 * 
	 * @param swk_knr_code_5
	 */
	public void setSWK_KNR_CODE_5(String swk_knr_code_5) {
		sWK_KNR_CODE_5 = swk_knr_code_5;
	}

	/**
	 * 管理6コードを取得する
	 * 
	 * @return 管理6コード
	 */
	public String getSWK_KNR_CODE_6() {
		return sWK_KNR_CODE_6;
	}

	/**
	 * 管理6コードを設定する
	 * 
	 * @param swk_knr_code_6
	 */
	public void setSWK_KNR_CODE_6(String swk_knr_code_6) {
		sWK_KNR_CODE_6 = swk_knr_code_6;
	}

	/**
	 * 管理1コード名称を取得する
	 * 
	 * @return 管理1コード名称
	 */
	public String getSWK_KNR_NAME_1() {
		return sWK_KNR_NAME_1;
	}

	/**
	 * 管理1コード名称を設定する
	 * 
	 * @param swk_knr_name_1
	 */
	public void setSWK_KNR_NAME_1(String swk_knr_name_1) {
		sWK_KNR_NAME_1 = swk_knr_name_1;
	}

	/**
	 * 管理2コード名称を取得する
	 * 
	 * @return 管理2コード名称
	 */
	public String getSWK_KNR_NAME_2() {
		return sWK_KNR_NAME_2;
	}

	/**
	 * 管理2コード名称を設定する
	 * 
	 * @param swk_knr_name_2
	 */
	public void setSWK_KNR_NAME_2(String swk_knr_name_2) {
		sWK_KNR_NAME_2 = swk_knr_name_2;
	}

	/**
	 * 管理3コード名称を取得する
	 * 
	 * @return 管理3コード名称
	 */
	public String getSWK_KNR_NAME_3() {
		return sWK_KNR_NAME_3;
	}

	/**
	 * 管理3コード名称を設定する
	 * 
	 * @param swk_knr_name_3
	 */
	public void setSWK_KNR_NAME_3(String swk_knr_name_3) {
		sWK_KNR_NAME_3 = swk_knr_name_3;
	}

	/**
	 * 管理4コード名称を取得する
	 * 
	 * @return 管理4コード名称
	 */
	public String getSWK_KNR_NAME_4() {
		return sWK_KNR_NAME_4;
	}

	/**
	 * 管理4コード名称を設定する
	 * 
	 * @param swk_knr_name_4
	 */
	public void setSWK_KNR_NAME_4(String swk_knr_name_4) {
		sWK_KNR_NAME_4 = swk_knr_name_4;
	}

	/**
	 * 管理5コード名称を取得する
	 * 
	 * @return 管理5コード名称
	 */
	public String getSWK_KNR_NAME_5() {
		return sWK_KNR_NAME_5;
	}

	/**
	 * 管理5コード名称を設定する
	 * 
	 * @param swk_knr_name_5
	 */
	public void setSWK_KNR_NAME_5(String swk_knr_name_5) {
		sWK_KNR_NAME_5 = swk_knr_name_5;
	}

	/**
	 * 管理6コード名称を取得する
	 * 
	 * @return 管理6コード名称
	 */
	public String getSWK_KNR_NAME_6() {
		return sWK_KNR_NAME_6;
	}

	/**
	 * 管理6コード名称を設定する
	 * 
	 * @param swk_knr_name_6
	 */
	public void setSWK_KNR_NAME_6(String swk_knr_name_6) {
		sWK_KNR_NAME_6 = swk_knr_name_6;
	}

	/**
	 * 管理1コード略称を取得する
	 * 
	 * @return 管理1コード略称
	 */
	public String getSWK_KNR_NAME_S_1() {
		return sWK_KNR_NAME_S_1;
	}

	/**
	 * 管理1コード略称を設定する
	 * 
	 * @param swk_knr_name_s_1
	 */
	public void setSWK_KNR_NAME_S_1(String swk_knr_name_s_1) {
		sWK_KNR_NAME_S_1 = swk_knr_name_s_1;
	}

	/**
	 * 管理2コード略称を取得する
	 * 
	 * @return 管理2コード略称
	 */
	public String getSWK_KNR_NAME_S_2() {
		return sWK_KNR_NAME_S_2;
	}

	/**
	 * 管理2コード略称を設定する
	 * 
	 * @param swk_knr_name_s_2
	 */
	public void setSWK_KNR_NAME_S_2(String swk_knr_name_s_2) {
		sWK_KNR_NAME_S_2 = swk_knr_name_s_2;
	}

	/**
	 * 管理3コード略称を取得する
	 * 
	 * @return 管理3コード略称
	 */
	public String getSWK_KNR_NAME_S_3() {
		return sWK_KNR_NAME_S_3;
	}

	/**
	 * 管理3コード略称を設定する
	 * 
	 * @param swk_knr_name_s_3
	 */
	public void setSWK_KNR_NAME_S_3(String swk_knr_name_s_3) {
		sWK_KNR_NAME_S_3 = swk_knr_name_s_3;
	}

	/**
	 * 管理4コード略称を取得する
	 * 
	 * @return 管理4コード略称
	 */
	public String getSWK_KNR_NAME_S_4() {
		return sWK_KNR_NAME_S_4;
	}

	/**
	 * 管理4コード略称を設定する
	 * 
	 * @param swk_knr_name_s_4
	 */
	public void setSWK_KNR_NAME_S_4(String swk_knr_name_s_4) {
		sWK_KNR_NAME_S_4 = swk_knr_name_s_4;
	}

	/**
	 * 管理5コード略称を取得する
	 * 
	 * @return 管理5コード略称
	 */
	public String getSWK_KNR_NAME_S_5() {
		return sWK_KNR_NAME_S_5;
	}

	/**
	 * 管理5コード略称を設定する
	 * 
	 * @param swk_knr_name_s_5
	 */
	public void setSWK_KNR_NAME_S_5(String swk_knr_name_s_5) {
		sWK_KNR_NAME_S_5 = swk_knr_name_s_5;
	}

	/**
	 * 管理6コード略称を取得する
	 * 
	 * @return 管理6コード略称
	 */
	public String getSWK_KNR_NAME_S_6() {
		return sWK_KNR_NAME_S_6;
	}

	/**
	 * 管理6コード略称を設定する
	 * 
	 * @param swk_knr_name_s_6
	 */
	public void setSWK_KNR_NAME_S_6(String swk_knr_name_s_6) {
		sWK_KNR_NAME_S_6 = swk_knr_name_s_6;
	}

	/**
	 * 年度を取得する
	 * 
	 * @return 年度
	 */
	public int getSWK_NENDO() {
		return sWK_NENDO;
	}

	/**
	 * 年度を設定する
	 * 
	 * @param swk_nendo
	 */
	public void setSWK_NENDO(int swk_nendo) {
		sWK_NENDO = swk_nendo;
	}

	/**
	 * 相殺日を取得する
	 * 
	 * @return 相殺日
	 */
	public Date getSWK_SOUSAI_DATE() {
		return sWK_SOUSAI_DATE;
	}

	/**
	 * 相殺日を設定する
	 * 
	 * @param swk_sousai_date
	 */
	public void setSWK_SOUSAI_DATE(Date swk_sousai_date) {
		sWK_SOUSAI_DATE = swk_sousai_date;
	}

	/**
	 * 相殺伝票番号を取得する
	 * 
	 * @return 相殺伝票番号
	 */
	public String getSWK_SOUSAI_DEN_NO() {
		return sWK_SOUSAI_DEN_NO;
	}

	/**
	 * 相殺伝票番号を設定する
	 * 
	 * @param swk_sousai_den_no
	 */
	public void setSWK_SOUSAI_DEN_NO(String swk_sousai_den_no) {
		sWK_SOUSAI_DEN_NO = swk_sousai_den_no;
	}

	/**
	 * 取引先コードを取得する
	 * 
	 * @return 取引先コード
	 */
	public String getSWK_TRI_CODE() {
		return sWK_TRI_CODE;
	}

	/**
	 * 取引先コードを設定する
	 * 
	 * @param swk_tri_code
	 */
	public void setSWK_TRI_CODE(String swk_tri_code) {
		sWK_TRI_CODE = swk_tri_code;
	}

	/**
	 * 取引先名称を取得する
	 * 
	 * @return 取引先名称
	 */
	public String getSWK_TRI_NAME() {
		return sWK_TRI_NAME;
	}

	/**
	 * 取引先名称を設定する
	 * 
	 * @param swk_tri_name
	 */
	public void setSWK_TRI_NAME(String swk_tri_name) {
		sWK_TRI_NAME = swk_tri_name;
	}

	/**
	 * 取引先略称を取得する
	 * 
	 * @return 取引先略称
	 */
	public String getSWK_TRI_NAME_S() {
		return sWK_TRI_NAME_S;
	}

	/**
	 * 取引先略称を設定する
	 * 
	 * @param swk_tri_name_s
	 */
	public void setSWK_TRI_NAME_S(String swk_tri_name_s) {
		sWK_TRI_NAME_S = swk_tri_name_s;
	}

	/**
	 * 会社間付替区分を取得する
	 * 
	 * @return 会社間付替区分
	 */
	public int getSWK_TUKE_KBN() {
		return sWK_TUKE_KBN;
	}

	/**
	 * 会社間付替区分を設定する
	 * 
	 * @param swk_tuke_kbn
	 */
	public void setSWK_TUKE_KBN(int swk_tuke_kbn) {
		sWK_TUKE_KBN = swk_tuke_kbn;
	}

	/**
	 * 月度を取得する
	 * 
	 * @return 月度
	 */
	public int getSWK_TUKIDO() {
		return sWK_TUKIDO;
	}

	/**
	 * 月度を設定する
	 * 
	 * @param swk_tukido
	 */
	public void setSWK_TUKIDO(int swk_tukido) {
		sWK_TUKIDO = swk_tukido;
	}

	/**
	 * 内訳科目コードを取得する
	 * 
	 * @return 内訳科目コード
	 */
	public String getSWK_UKM_CODE() {
		return sWK_UKM_CODE;
	}

	/**
	 * 内訳科目コードを設定する
	 * 
	 * @param swk_ukm_code
	 */
	public void setSWK_UKM_CODE(String swk_ukm_code) {
		sWK_UKM_CODE = swk_ukm_code;
	}

	/**
	 * 内訳科目名称を取得する
	 * 
	 * @return 内訳科目名称
	 */
	public String getSWK_UKM_NAME() {
		return sWK_UKM_NAME;
	}

	/**
	 * 内訳科目名称を設定する
	 * 
	 * @param swk_ukm_name
	 */
	public void setSWK_UKM_NAME(String swk_ukm_name) {
		sWK_UKM_NAME = swk_ukm_name;
	}

	/**
	 * 内訳科目略称を取得する
	 * 
	 * @return 内訳科目略称
	 */
	public String getSWK_UKM_NAME_S() {
		return sWK_UKM_NAME_S;
	}

	/**
	 * 内訳科目略称を設定する
	 * 
	 * @param swk_ukm_name_s
	 */
	public void setSWK_UKM_NAME_S(String swk_ukm_name_s) {
		sWK_UKM_NAME_S = swk_ukm_name_s;
	}

	/**
	 * 税コードを取得する
	 * 
	 * @return 税コード
	 */
	public String getSWK_ZEI_CODE() {
		return sWK_ZEI_CODE;
	}

	/**
	 * 税コードを設定する
	 * 
	 * @param swk_zei_code
	 */
	public void setSWK_ZEI_CODE(String swk_zei_code) {
		sWK_ZEI_CODE = swk_zei_code;
	}

	/**
	 * 税区分を取得する
	 * 
	 * @return 税区分
	 */
	public int getSWK_ZEI_KBN() {
		return sWK_ZEI_KBN;
	}

	/**
	 * 税区分を設定する
	 * 
	 * @param swk_zei_kbn
	 */
	public void setSWK_ZEI_KBN(int swk_zei_kbn) {
		sWK_ZEI_KBN = swk_zei_kbn;
	}

	/**
	 * 消費税金額を取得する
	 * 
	 * @return 消費税金額
	 */
	public BigDecimal getSWK_ZEI_KIN() {
		return sWK_ZEI_KIN;
	}

	/**
	 * 消費税金額を設定する
	 * 
	 * @param swk_zei_kin
	 */
	public void setSWK_ZEI_KIN(BigDecimal swk_zei_kin) {
		sWK_ZEI_KIN = swk_zei_kin;
	}

	/**
	 * 税率を取得する
	 * 
	 * @return 税率
	 */
	public BigDecimal getSWK_ZEI_RATE() {
		return sWK_ZEI_RATE;
	}

	/**
	 * 税率を設定する
	 * 
	 * @param swk_zei_rate
	 */
	public void setSWK_ZEI_RATE(BigDecimal swk_zei_rate) {
		sWK_ZEI_RATE = swk_zei_rate;
	}

	/**
	 * 更新区分を取得する
	 * 
	 * @return 更新区分
	 */
	public int getUPD_KBN() {
		return uPD_KBN;
	}

	/**
	 * 更新区分を設定する
	 * 
	 * @param upd_kbn
	 */
	public void setUPD_KBN(int upd_kbn) {
		uPD_KBN = upd_kbn;
	}

	/**
	 * ユーザーIDを取得する
	 * 
	 * @return ユーザーID
	 */
	public String getUSR_ID() {
		return uSR_ID;
	}

	/**
	 * ユーザーIDを設定する
	 * 
	 * @param usr_id
	 */
	public void setUSR_ID(String usr_id) {
		uSR_ID = usr_id;
	}

	/**
	 * 伝票種別 検索名称
	 * 
	 * @return 伝票種別 検索名称
	 */
	public String getDEN_SYU_NAME_K() {
		return dEN_SYU_NAME_K;
	}

	/**
	 * 伝票種別 検索名称
	 * 
	 * @param den_syu_name_k
	 */
	public void setDEN_SYU_NAME_K(String den_syu_name_k) {
		dEN_SYU_NAME_K = den_syu_name_k;
	}

	/**
	 * 補助科目名称（帳票出力用）を取得する
	 * 
	 * @return 補助科目名称（帳票出力用）
	 */
	public String getCMP_HKM_NAME() {
		return cMP_HKM_NAME;
	}

	/**
	 * 補助科目名称（帳票出力用）を設定する
	 * 
	 * @param cmp_hkm_name
	 */
	public void setCMP_HKM_NAME(String cmp_hkm_name) {
		cMP_HKM_NAME = cmp_hkm_name;
	}

	/**
	 * 科目名称（帳票出力用）を取得する
	 * 
	 * @return 科目名称（帳票出力用）
	 */
	public String getCMP_KMK_NAME() {
		return cMP_KMK_NAME;
	}

	/**
	 * 科目名称（帳票出力用）を設定する
	 * 
	 * @param cmp_kmk_name
	 */
	public void setCMP_KMK_NAME(String cmp_kmk_name) {
		cMP_KMK_NAME = cmp_kmk_name;
	}

	/**
	 * 内訳科目名称（帳票出力用）を取得する
	 * 
	 * @return 内訳科目名称（帳票出力用）
	 */
	public String getCMP_UKM_NAME() {
		return cMP_UKM_NAME;
	}

	/**
	 * 内訳科目名称（帳票出力用）を設定する
	 * 
	 * @param cmp_ukm_name
	 */
	public void setCMP_UKM_NAME(String cmp_ukm_name) {
		cMP_UKM_NAME = cmp_ukm_name;
	}

	/**
	 * 社員名称を取得する
	 * 
	 * @return 社員名称
	 */
	public String getHDR_EMP_NAME() {
		return hDR_EMP_NAME;
	}

	/**
	 * 社員名称を設定する
	 * 
	 * @param hdr_emp_name
	 */
	public void setHDR_EMP_NAME(String hdr_emp_name) {
		hDR_EMP_NAME = hdr_emp_name;
	}

	/**
	 * 社員略称を取得する
	 * 
	 * @return 社員略称
	 */
	public String getHDR_EMP_NAME_S() {
		return hDR_EMP_NAME_S;
	}

	/**
	 * 社員略称を設定する
	 * 
	 * @param hdr_emp_name_s
	 */
	public void setHDR_EMP_NAME_S(String hdr_emp_name_s) {
		hDR_EMP_NAME_S = hdr_emp_name_s;
	}

	/**
	 * 銀行口座コード 銀行コードを取得する
	 * 
	 * @return 銀行口座コード 銀行コード
	 */
	public String getHDR_CBK_BNK_CODE() {
		return HDR_CBK_BNK_CODE;
	}

	/**
	 * 銀行口座コード 銀行コードを設定する
	 * 
	 * @param hdr_cbk_bnk_code
	 */
	public void setHDR_CBK_BNK_CODE(String hdr_cbk_bnk_code) {
		HDR_CBK_BNK_CODE = hdr_cbk_bnk_code;
	}

	/**
	 * 銀行口座コード 支店コードを取得する
	 * 
	 * @return 銀行口座コード 支店コード
	 */
	public String getHDR_CBK_STN_CODE() {
		return HDR_CBK_STN_CODE;
	}

	/**
	 * 銀行口座コード 支店コードを設定する
	 * 
	 * @param hdr_cbk_stn_code
	 */
	public void setHDR_CBK_STN_CODE(String hdr_cbk_stn_code) {
		HDR_CBK_STN_CODE = hdr_cbk_stn_code;
	}

	/**
	 * 計上部門名を取得する
	 * 
	 * @return 計上部門名
	 */
	public String getHDR_DEP_NAME() {
		return hDR_DEP_NAME;
	}

	/**
	 * 計上部門名を設定する
	 * 
	 * @param hdr_dep_name
	 */
	public void setHDR_DEP_NAME(String hdr_dep_name) {
		hDR_DEP_NAME = hdr_dep_name;
	}

	/**
	 * 計上部門略称を取得する
	 * 
	 * @return 計上部門略称
	 */
	public String getHDR_DEP_NAME_S() {
		return hDR_DEP_NAME_S;
	}

	/**
	 * 計上部門略称を設定する
	 * 
	 * @param hdr_dep_name_s
	 */
	public void setHDR_DEP_NAME_S(String hdr_dep_name_s) {
		hDR_DEP_NAME_S = hdr_dep_name_s;
	}

	/**
	 * 通貨コード 小数点を取得する(ヘッダー)
	 * 
	 * @return 通貨コード 小数点(ヘッダー)
	 */
	public String getHDR_CUR_DEC_KETA() {
		return hDR_CUR_DEC_KETA;
	}

	/**
	 * 通貨コード 小数点を設定する(ヘッダー)
	 * 
	 * @param hdr_cur_dec_keta
	 */
	public void setHDR_CUR_DEC_KETA(String hdr_cur_dec_keta) {
		hDR_CUR_DEC_KETA = hdr_cur_dec_keta;
	}

	/**
	 * 精算済仮払入力金額を取得する
	 * 
	 * @return 精算済仮払入力金額
	 */
	public BigDecimal getHDR_SSY_KARI_IN_KIN() {
		return hDR_SSY_KARI_IN_KIN;
	}

	/**
	 * 精算済仮払入力金額を設定する
	 * 
	 * @param hdr_ssy_kari_in_kin
	 */
	public void setHDR_SSY_KARI_IN_KIN(BigDecimal hdr_ssy_kari_in_kin) {
		hDR_SSY_KARI_IN_KIN = hdr_ssy_kari_in_kin;
	}

	/**
	 * 精算済仮払邦貨金額を取得する。
	 * 
	 * @return 精算済仮払邦貨金額
	 */
	public BigDecimal getHDR_SSY_KARI_KIN() {
		return hDR_SSY_KARI_KIN;
	}

	/**
	 * 精算済仮払邦貨金額を設定する
	 * 
	 * @param hdr_ssy_kari_kin
	 */
	public void setHDR_SSY_KARI_KIN(BigDecimal hdr_ssy_kari_kin) {
		hDR_SSY_KARI_KIN = hdr_ssy_kari_kin;
	}

	/**
	 * 明細 計上部門略称
	 * 
	 * @return 明細 計上部門略称
	 */
	public String getSWK_DEP_NAME_S() {
		return sWK_DEP_NAME_S;
	}

	/**
	 * 明細 計上部門略称
	 * 
	 * @param swk_dep_name_s 明細 計上部門略称
	 */
	public void setSWK_DEP_NAME_S(String swk_dep_name_s) {
		sWK_DEP_NAME_S = swk_dep_name_s;
	}

	/**
	 * 仮払日を取得
	 * 
	 * @return 仮払日
	 */
	public Date getTMP_PAY_DATE() {
		return tMP_PAY_DATE;
	}

	/**
	 * 仮払日を設定
	 * 
	 * @param tmp_pay_date
	 */
	public void setTMP_PAY_DATE(Date tmp_pay_date) {
		tMP_PAY_DATE = tmp_pay_date;
	}

	/**
	 * 計上会社名称
	 * 
	 * @return sWK_K_KAI_NAME 計上会社名称
	 */
	public String getSWK_K_KAI_NAME() {
		return sWK_K_KAI_NAME;
	}

	/**
	 * 計上会社名称
	 * 
	 * @param swk_k_kai_name 計上会社名称設定する
	 */
	public void setSWK_K_KAI_NAME(String swk_k_kai_name) {
		sWK_K_KAI_NAME = swk_k_kai_name;
	}

	/**
	 * 消費税名称
	 * 
	 * @return sWK_ZEI_NAME 消費税名称
	 */
	public String getSWK_ZEI_NAME() {
		return sWK_ZEI_NAME;
	}

	/**
	 * 消費税名称
	 * 
	 * @param swk_zei_name 消費税名称設定する
	 */
	public void setSWK_ZEI_NAME(String swk_zei_name) {
		sWK_ZEI_NAME = swk_zei_name;
	}

}
