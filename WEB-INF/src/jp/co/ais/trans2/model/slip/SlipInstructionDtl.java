package jp.co.ais.trans2.model.slip;

import java.math.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;

/**
 * 仕訳明細
 */
public class SlipInstructionDtl extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String kAI_CODE;

	/** 伝票日付 */
	protected String sWK_DEN_DATE;

	/** 伝票番号 */
	protected String sWK_DEN_NO;

	/** 行番号 */
	protected int sWK_GYO_NO;

	/** 証憑番号 */
	protected String sWK_SEI_NO;

	/** 貸借区分 */
	protected int sWK_DC_KBN;

	/** 科目コード */
	protected String sWK_KMK_CODE;

	/** 補助科目コード */
	protected String sWK_HKM_CODE;

	/** 内訳科目コード */
	protected String sWK_UKM_CODE;

	/** 計上部門コード */
	protected String sWK_DEP_CODE;

	/** 税区分 */
	protected int sWK_ZEI_KBN;

	/** 邦貨金額 */
	protected BigDecimal sWK_KIN;

	/** 消費税額 */
	protected BigDecimal sWK_ZEI_KIN;

	/** 消費税コード */
	protected String sWK_ZEI_CODE;

	/** 行摘要コード */
	protected String sWK_GYO_TEK_CODE;

	/** 行摘要 */
	protected String sWK_GYO_TEK;

	/** 取引先コード */
	protected String sWK_TRI_CODE;

	/** 社員コード */
	protected String sWK_EMP_CODE;

	/** 管理１コード */
	protected String sWK_KNR_CODE_1;

	/** 管理２コード */
	protected String sWK_KNR_CODE_2;

	/** 管理３コード */
	protected String sWK_KNR_CODE_3;

	/** 管理４コード */
	protected String sWK_KNR_CODE_4;

	/** 管理５コード */
	protected String sWK_KNR_CODE_5;

	/** 管理６コード */
	protected String sWK_KNR_CODE_6;

	/** 非会計明細１ */
	protected String sWK_HM_1;

	/** 非会計明細２ */
	protected String sWK_HM_2;

	/** 非会計明細３ */
	protected String sWK_HM_3;

	/** 自動仕訳区分 */
	protected int sWK_AUTO_KBN;

	/** 更新区分 */
	protected int sWK_UPD_KBN;

	/** 決算区分 */
	protected int sWK_KSN_KBN;

	/** 相手科目コード */
	protected String sWK_AT_KMK_CODE;

	/** 相手補助科目コード */
	protected String sWK_AT_HKM_CODE;

	/** 相手内訳科目コード */
	protected String sWK_AT_UKM_CODE;

	/** 相手部門コード */
	protected String sWK_AT_DEP_CODE;

	/** 計上会社ｺｰﾄﾞ */
	protected String sWK_K_KAI_CODE;

	/** 通貨ｺｰﾄﾞ */
	protected String sWK_CUR_CODE;

	/** ﾚｰﾄ */
	protected BigDecimal sWK_CUR_RATE;

	/** 入力金額 */
	protected BigDecimal sWK_IN_KIN;

	/** 会社間付替伝票区分 */
	protected int sWK_TUKE_KBN;

	/** 入力消費税額 */
	protected BigDecimal sWK_IN_ZEI_KIN;

	/** 発生日 */
	protected String hAS_DATE;

	/** 伝票種別ｺｰﾄﾞ */
	protected String dEN_SYU_CODE;

	/** 通貨 少数点桁数 */
	protected int cUR_DEC_KETA;

	/**
	 * 伝票種別ｺｰﾄﾞ
	 * 
	 * @return 伝票種別ｺｰﾄﾞ
	 */
	public String getDEN_SYU_CODE() {
		return dEN_SYU_CODE;
	}

	/**
	 * 伝票種別ｺｰﾄﾞ
	 * 
	 * @param den_syu_code 伝票種別ｺｰﾄﾞ
	 */
	public void setDEN_SYU_CODE(String den_syu_code) {
		dEN_SYU_CODE = den_syu_code;
	}

	/**
	 * 発生日
	 * 
	 * @return 発生日
	 */
	public String getHAS_DATE() {
		return hAS_DATE;
	}

	/**
	 * 発生日
	 * 
	 * @param has_date 発生日
	 */
	public void setHAS_DATE(String has_date) {
		hAS_DATE = has_date;
	}

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * 会社コード
	 * 
	 * @param kai_code 会社コード
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * 相手部門コード
	 * 
	 * @return 相手部門コード
	 */
	public String getSWK_AT_DEP_CODE() {
		return sWK_AT_DEP_CODE;
	}

	/**
	 * 相手部門コード
	 * 
	 * @param swk_at_dep_code 相手部門コード
	 */
	public void setSWK_AT_DEP_CODE(String swk_at_dep_code) {
		sWK_AT_DEP_CODE = swk_at_dep_code;
	}

	/**
	 * 相手補助科目コード
	 * 
	 * @return 相手補助科目コード
	 */
	public String getSWK_AT_HKM_CODE() {
		return sWK_AT_HKM_CODE;
	}

	/**
	 * 相手補助科目コード
	 * 
	 * @param swk_at_hkm_code 相手補助科目コード
	 */
	public void setSWK_AT_HKM_CODE(String swk_at_hkm_code) {
		sWK_AT_HKM_CODE = swk_at_hkm_code;
	}

	/**
	 * 相手科目コード
	 * 
	 * @return 相手科目コード
	 */
	public String getSWK_AT_KMK_CODE() {
		return sWK_AT_KMK_CODE;
	}

	/**
	 * 相手科目コード
	 * 
	 * @param swk_at_kmk_code 相手科目コード
	 */
	public void setSWK_AT_KMK_CODE(String swk_at_kmk_code) {
		sWK_AT_KMK_CODE = swk_at_kmk_code;
	}

	/**
	 * 相手内訳科目コード
	 * 
	 * @return 相手内訳科目コード
	 */
	public String getSWK_AT_UKM_CODE() {
		return sWK_AT_UKM_CODE;
	}

	/**
	 * 相手内訳科目コード
	 * 
	 * @param swk_at_ukm_code 相手内訳科目コード
	 */
	public void setSWK_AT_UKM_CODE(String swk_at_ukm_code) {
		sWK_AT_UKM_CODE = swk_at_ukm_code;
	}

	/**
	 * 自動仕訳区分
	 * 
	 * @return 自動仕訳区分
	 */
	public int getSWK_AUTO_KBN() {
		return sWK_AUTO_KBN;
	}

	/**
	 * 自動仕訳かどうか
	 * 
	 * @return sWK_AUTO_KBN true:自動仕訳
	 */
	public boolean isAutoDetail() {
		return BooleanUtil.toBoolean(sWK_AUTO_KBN);
	}

	/**
	 * 自動仕訳区分
	 * 
	 * @param swk_auto_kbn 自動仕訳区分
	 */
	public void setSWK_AUTO_KBN(int swk_auto_kbn) {
		sWK_AUTO_KBN = swk_auto_kbn;
	}

	/**
	 * 通貨ｺｰﾄﾞ
	 * 
	 * @return 通貨ｺｰﾄﾞ
	 */
	public String getSWK_CUR_CODE() {
		return sWK_CUR_CODE;
	}

	/**
	 * 通貨ｺｰﾄﾞ
	 * 
	 * @param swk_cur_code 通貨ｺｰﾄﾞ
	 */
	public void setSWK_CUR_CODE(String swk_cur_code) {
		sWK_CUR_CODE = swk_cur_code;
	}

	/**
	 * ﾚｰﾄ
	 * 
	 * @return ﾚｰﾄ
	 */
	public BigDecimal getSWK_CUR_RATE() {
		return sWK_CUR_RATE;
	}

	/**
	 * ﾚｰﾄ
	 * 
	 * @param swk_cur_rate ﾚｰﾄ
	 */
	public void setSWK_CUR_RATE(BigDecimal swk_cur_rate) {
		sWK_CUR_RATE = swk_cur_rate;
	}

	/**
	 * 貸借区分
	 * 
	 * @return 貸借区分
	 */
	public int getSWK_DC_KBN() {
		return sWK_DC_KBN;
	}

	/**
	 * 貸借区分
	 * 
	 * @param swk_dc_kbn 貸借区分
	 */
	public void setSWK_DC_KBN(int swk_dc_kbn) {
		sWK_DC_KBN = swk_dc_kbn;
	}

	/**
	 * 貸借
	 * 
	 * @param dc 貸借
	 */
	public void setDC(Dc dc) {
		sWK_DC_KBN = dc.value;
	}

	/**
	 * 貸借
	 * 
	 * @return 貸借
	 */
	public Dc getDC() {
		return Dc.getDc(sWK_DC_KBN);
	}

	/**
	 * 伝票日付
	 * 
	 * @return 伝票日付
	 */
	public String getSWK_DEN_DATE() {
		return sWK_DEN_DATE;
	}

	/**
	 * 伝票日付
	 * 
	 * @param swk_den_date 伝票日付
	 */
	public void setSWK_DEN_DATE(String swk_den_date) {
		sWK_DEN_DATE = swk_den_date;
	}

	/**
	 * 伝票番号
	 * 
	 * @return 伝票番号
	 */
	public String getSWK_DEN_NO() {
		return sWK_DEN_NO;
	}

	/**
	 * 伝票番号
	 * 
	 * @param swk_den_no 伝票番号
	 */
	public void setSWK_DEN_NO(String swk_den_no) {
		sWK_DEN_NO = swk_den_no;
	}

	/**
	 * 計上部門コード
	 * 
	 * @return 計上部門コード
	 */
	public String getSWK_DEP_CODE() {
		return sWK_DEP_CODE;
	}

	/**
	 * 計上部門コード
	 * 
	 * @param swk_dep_code 計上部門コード
	 */
	public void setSWK_DEP_CODE(String swk_dep_code) {
		sWK_DEP_CODE = swk_dep_code;
	}

	/**
	 * 社員コード
	 * 
	 * @return 社員コード
	 */
	public String getSWK_EMP_CODE() {
		return sWK_EMP_CODE;
	}

	/**
	 * 社員コード
	 * 
	 * @param swk_emp_code 社員コード
	 */
	public void setSWK_EMP_CODE(String swk_emp_code) {
		sWK_EMP_CODE = swk_emp_code;
	}

	/**
	 * 行番号
	 * 
	 * @return 行番号
	 */
	public int getSWK_GYO_NO() {
		return sWK_GYO_NO;
	}

	/**
	 * 行番号
	 * 
	 * @param swk_gyo_no 行番号
	 */
	public void setSWK_GYO_NO(int swk_gyo_no) {
		sWK_GYO_NO = swk_gyo_no;
	}

	/**
	 * 行摘要
	 * 
	 * @return 行摘要
	 */
	public String getSWK_GYO_TEK() {
		return sWK_GYO_TEK;
	}

	/**
	 * 行摘要
	 * 
	 * @param swk_gyo_tek 行摘要
	 */
	public void setSWK_GYO_TEK(String swk_gyo_tek) {
		sWK_GYO_TEK = swk_gyo_tek;
	}

	/**
	 * 行摘要コード
	 * 
	 * @return 行摘要コード
	 */
	public String getSWK_GYO_TEK_CODE() {
		return sWK_GYO_TEK_CODE;
	}

	/**
	 * 行摘要コード
	 * 
	 * @param swk_gyo_tek_code 行摘要コード
	 */
	public void setSWK_GYO_TEK_CODE(String swk_gyo_tek_code) {
		sWK_GYO_TEK_CODE = swk_gyo_tek_code;
	}

	/**
	 * 補助科目コード
	 * 
	 * @return 補助科目コード
	 */
	public String getSWK_HKM_CODE() {
		return sWK_HKM_CODE;
	}

	/**
	 * 補助科目コード
	 * 
	 * @param swk_hkm_code 補助科目コード
	 */
	public void setSWK_HKM_CODE(String swk_hkm_code) {
		sWK_HKM_CODE = swk_hkm_code;
	}

	/**
	 * 非会計明細１
	 * 
	 * @return 非会計明細１
	 */
	public String getSWK_HM_1() {
		return sWK_HM_1;
	}

	/**
	 * 非会計明細１
	 * 
	 * @param swk_hm_1 非会計明細１
	 */
	public void setSWK_HM_1(String swk_hm_1) {
		sWK_HM_1 = swk_hm_1;
	}

	/**
	 * 非会計明細２
	 * 
	 * @return 非会計明細２
	 */
	public String getSWK_HM_2() {
		return sWK_HM_2;
	}

	/**
	 * 非会計明細２
	 * 
	 * @param swk_hm_2 非会計明細２
	 */
	public void setSWK_HM_2(String swk_hm_2) {
		sWK_HM_2 = swk_hm_2;
	}

	/**
	 * 非会計明細３
	 * 
	 * @return 非会計明細３
	 */
	public String getSWK_HM_3() {
		return sWK_HM_3;
	}

	/**
	 * 非会計明細３
	 * 
	 * @param swk_hm_3 非会計明細３
	 */
	public void setSWK_HM_3(String swk_hm_3) {
		sWK_HM_3 = swk_hm_3;
	}

	/**
	 * 入力金額
	 * 
	 * @return sWK_IN_KIN 入力金額
	 */
	public BigDecimal getSWK_IN_KIN() {
		return sWK_IN_KIN;
	}

	/**
	 * 入力金額
	 * 
	 * @param swk_in_kin 入力金額
	 */
	public void setSWK_IN_KIN(BigDecimal swk_in_kin) {
		sWK_IN_KIN = swk_in_kin;
	}

	/**
	 * 入力消費税額
	 * 
	 * @return 入力消費税額
	 */
	public BigDecimal getSWK_IN_ZEI_KIN() {
		return sWK_IN_ZEI_KIN;
	}

	/**
	 * 入力消費税額
	 * 
	 * @param swk_in_zei_kin 入力消費税額
	 */
	public void setSWK_IN_ZEI_KIN(BigDecimal swk_in_zei_kin) {
		sWK_IN_ZEI_KIN = swk_in_zei_kin;
	}

	/**
	 * 計上会社ｺｰﾄﾞ
	 * 
	 * @return 計上会社ｺｰﾄﾞ
	 */
	public String getSWK_K_KAI_CODE() {
		return sWK_K_KAI_CODE;
	}

	/**
	 * 計上会社ｺｰﾄﾞ
	 * 
	 * @param swk_k_kai_code 計上会社ｺｰﾄﾞ
	 */
	public void setSWK_K_KAI_CODE(String swk_k_kai_code) {
		sWK_K_KAI_CODE = swk_k_kai_code;
	}

	/**
	 * 邦貨金額
	 * 
	 * @return 邦貨金額
	 */
	public BigDecimal getSWK_KIN() {
		return sWK_KIN;
	}

	/**
	 * 邦貨金額
	 * 
	 * @param swk_kin 邦貨金額
	 */
	public void setSWK_KIN(BigDecimal swk_kin) {
		sWK_KIN = swk_kin;
	}

	/**
	 * 科目コード
	 * 
	 * @return 科目コード
	 */
	public String getSWK_KMK_CODE() {
		return sWK_KMK_CODE;
	}

	/**
	 * 科目コード
	 * 
	 * @param swk_kmk_code 科目コード
	 */
	public void setSWK_KMK_CODE(String swk_kmk_code) {
		sWK_KMK_CODE = swk_kmk_code;
	}

	/**
	 * 管理１コード
	 * 
	 * @return 管理１コード
	 */
	public String getSWK_KNR_CODE_1() {
		return sWK_KNR_CODE_1;
	}

	/**
	 * 管理１コード
	 * 
	 * @param swk_knr_code_1 管理１コード
	 */
	public void setSWK_KNR_CODE_1(String swk_knr_code_1) {
		sWK_KNR_CODE_1 = swk_knr_code_1;
	}

	/**
	 * 管理２コード
	 * 
	 * @return 管理２コード
	 */
	public String getSWK_KNR_CODE_2() {
		return sWK_KNR_CODE_2;
	}

	/**
	 * 管理２コード
	 * 
	 * @param swk_knr_code_2 管理２コード
	 */
	public void setSWK_KNR_CODE_2(String swk_knr_code_2) {
		sWK_KNR_CODE_2 = swk_knr_code_2;
	}

	/**
	 * 管理３コード
	 * 
	 * @return 管理３コード
	 */
	public String getSWK_KNR_CODE_3() {
		return sWK_KNR_CODE_3;
	}

	/**
	 * 管理３コード
	 * 
	 * @param swk_knr_code_3 管理３コード
	 */
	public void setSWK_KNR_CODE_3(String swk_knr_code_3) {
		sWK_KNR_CODE_3 = swk_knr_code_3;
	}

	/**
	 * 管理４コード
	 * 
	 * @return 管理４コード
	 */
	public String getSWK_KNR_CODE_4() {
		return sWK_KNR_CODE_4;
	}

	/**
	 * 管理４コード
	 * 
	 * @param swk_knr_code_4 管理４コード
	 */
	public void setSWK_KNR_CODE_4(String swk_knr_code_4) {
		sWK_KNR_CODE_4 = swk_knr_code_4;
	}

	/**
	 * 管理５コード
	 * 
	 * @return 管理５コード
	 */
	public String getSWK_KNR_CODE_5() {
		return sWK_KNR_CODE_5;
	}

	/**
	 * 管理５コード
	 * 
	 * @param swk_knr_code_5 管理５コード
	 */
	public void setSWK_KNR_CODE_5(String swk_knr_code_5) {
		sWK_KNR_CODE_5 = swk_knr_code_5;
	}

	/**
	 * 管理６コード
	 * 
	 * @return 管理６コード
	 */
	public String getSWK_KNR_CODE_6() {
		return sWK_KNR_CODE_6;
	}

	/**
	 * 管理６コード
	 * 
	 * @param swk_knr_code_6 管理６コード
	 */
	public void setSWK_KNR_CODE_6(String swk_knr_code_6) {
		sWK_KNR_CODE_6 = swk_knr_code_6;
	}

	/**
	 * 決算区分
	 * 
	 * @return 決算区分
	 */
	public int getSWK_KSN_KBN() {
		return sWK_KSN_KBN;
	}

	/**
	 * 決算区分
	 * 
	 * @param swk_ksn_kbn 決算区分
	 */
	public void setSWK_KSN_KBN(int swk_ksn_kbn) {
		sWK_KSN_KBN = swk_ksn_kbn;
	}

	/**
	 * 証憑番号
	 * 
	 * @return 証憑番号
	 */
	public String getSWK_SEI_NO() {
		return sWK_SEI_NO;
	}

	/**
	 * 証憑番号
	 * 
	 * @param swk_sei_no 証憑番号
	 */
	public void setSWK_SEI_NO(String swk_sei_no) {
		sWK_SEI_NO = swk_sei_no;
	}

	/**
	 * 取引先コード
	 * 
	 * @return 取引先コード
	 */
	public String getSWK_TRI_CODE() {
		return sWK_TRI_CODE;
	}

	/**
	 * 取引先コード
	 * 
	 * @param swk_tri_code 取引先コード
	 */
	public void setSWK_TRI_CODE(String swk_tri_code) {
		sWK_TRI_CODE = swk_tri_code;
	}

	/**
	 * 会社間付替伝票区分
	 * 
	 * @return 会社間付替伝票区分
	 */
	public int getSWK_TUKE_KBN() {
		return sWK_TUKE_KBN;
	}

	/**
	 * 会社間付替伝票区分
	 * 
	 * @param swk_tuke_kbn 会社間付替伝票区分
	 */
	public void setSWK_TUKE_KBN(int swk_tuke_kbn) {
		sWK_TUKE_KBN = swk_tuke_kbn;
	}

	/**
	 * 内訳科目コード
	 * 
	 * @return 内訳科目コード
	 */
	public String getSWK_UKM_CODE() {
		return sWK_UKM_CODE;
	}

	/**
	 * 内訳科目コード
	 * 
	 * @param swk_ukm_code 内訳科目コード
	 */
	public void setSWK_UKM_CODE(String swk_ukm_code) {
		sWK_UKM_CODE = swk_ukm_code;
	}

	/**
	 * 更新区分
	 * 
	 * @return 更新区分
	 */
	public int getSWK_UPD_KBN() {
		return sWK_UPD_KBN;
	}

	/**
	 * 更新区分
	 * 
	 * @param swk_upd_kbn 更新区分
	 */
	public void setSWK_UPD_KBN(int swk_upd_kbn) {
		sWK_UPD_KBN = swk_upd_kbn;
	}

	/**
	 * 消費税コード
	 * 
	 * @return 消費税コード
	 */
	public String getSWK_ZEI_CODE() {
		return sWK_ZEI_CODE;
	}

	/**
	 * 消費税コード
	 * 
	 * @param swk_zei_code 消費税コード
	 */
	public void setSWK_ZEI_CODE(String swk_zei_code) {
		sWK_ZEI_CODE = swk_zei_code;
	}

	/**
	 * 税区分
	 * 
	 * @return 税区分
	 */
	public int getSWK_ZEI_KBN() {
		return sWK_ZEI_KBN;
	}

	/**
	 * 税区分
	 * 
	 * @param swk_zei_kbn 税区分
	 */
	public void setSWK_ZEI_KBN(int swk_zei_kbn) {
		sWK_ZEI_KBN = swk_zei_kbn;
	}

	/**
	 * 税区分
	 * 
	 * @param type 税区分
	 */
	public void setTaxCalcType(TaxCalcType type) {
		this.sWK_ZEI_KBN = type.value;
	}

	/**
	 * 消費税額
	 * 
	 * @return 消費税額
	 */
	public BigDecimal getSWK_ZEI_KIN() {
		return sWK_ZEI_KIN;
	}

	/**
	 * 消費税額
	 * 
	 * @param swk_zei_kin 消費税額
	 */
	public void setSWK_ZEI_KIN(BigDecimal swk_zei_kin) {
		sWK_ZEI_KIN = swk_zei_kin;
	}

	/** 受付部門コード */
	protected String sWK_UKE_DEP_CODE;

	/** 伝票摘要コード */
	protected String sWK_TEK_CODE;

	/** 伝票摘要 */
	protected String sWK_TEK;

	/** 承認者 */
	protected String sWK_SYO_EMP_CODE;

	/** 承認日 */
	protected String sWK_SYO_DATE;

	/** 依頼者 */
	protected String sWK_IRAI_EMP_CODE;

	/** 依頼部門コード */
	protected String sWK_IRAI_DEP_CODE;

	/** 依頼日 */
	protected String sWK_IRAI_DATE;

	/** ｼｽﾃﾑ区分 */
	protected String sWK_SYS_KBN;

	/** 伝票種別 */
	protected String sWK_DEN_SYU;

	// AP不足分--
	/** 支払区分 */
	protected String sWK_SIHA_KBN;

	/** 支払日 */
	protected String sWK_SIHA_DATE;

	/** 支払方法 */
	protected String sWK_HOH_CODE;

	/** 保留区分 */
	protected int sWK_HORYU_KBN;

	/** 取引先条件ｺｰﾄﾞ */
	protected String sWK_TJK_CODE;

	/** 銀行口座ｺｰﾄﾞ */
	protected String sWK_CBK_CODE;

	// AR不足分--
	/** 入金予定日 */
	protected String sWK_AR_DATE;

	/** 受入日 */
	protected String sWK_UKE_DATE;

	/**
	 * 伝票種別
	 * 
	 * @return 伝票種別
	 */
	public String getSWK_DEN_SYU() {
		return sWK_DEN_SYU;
	}

	/**
	 * 伝票種別
	 * 
	 * @param swk_den_syu 伝票種別
	 */
	public void setSWK_DEN_SYU(String swk_den_syu) {
		sWK_DEN_SYU = swk_den_syu;
	}

	/**
	 * 依頼日
	 * 
	 * @return 依頼日
	 */
	public String getSWK_IRAI_DATE() {
		return sWK_IRAI_DATE;
	}

	/**
	 * 依頼日
	 * 
	 * @param swk_irai_date 依頼日
	 */
	public void setSWK_IRAI_DATE(String swk_irai_date) {
		sWK_IRAI_DATE = swk_irai_date;
	}

	/**
	 * 依頼部門コード
	 * 
	 * @return 依頼部門コード
	 */
	public String getSWK_IRAI_DEP_CODE() {
		return sWK_IRAI_DEP_CODE;
	}

	/**
	 * 依頼部門コード
	 * 
	 * @param swk_irai_dep_code 依頼部門コード
	 */
	public void setSWK_IRAI_DEP_CODE(String swk_irai_dep_code) {
		sWK_IRAI_DEP_CODE = swk_irai_dep_code;
	}

	/**
	 * 依頼者
	 * 
	 * @return 依頼者
	 */
	public String getSWK_IRAI_EMP_CODE() {
		return sWK_IRAI_EMP_CODE;
	}

	/**
	 * 依頼者
	 * 
	 * @param swk_irai_emp_code 依頼者
	 */
	public void setSWK_IRAI_EMP_CODE(String swk_irai_emp_code) {
		sWK_IRAI_EMP_CODE = swk_irai_emp_code;
	}

	/**
	 * 承認日
	 * 
	 * @return 承認日
	 */
	public String getSWK_SYO_DATE() {
		return sWK_SYO_DATE;
	}

	/**
	 * 承認日
	 * 
	 * @param swk_syo_date 承認日
	 */
	public void setSWK_SYO_DATE(String swk_syo_date) {
		sWK_SYO_DATE = swk_syo_date;
	}

	/**
	 * 承認者
	 * 
	 * @return 承認者
	 */
	public String getSWK_SYO_EMP_CODE() {
		return sWK_SYO_EMP_CODE;
	}

	/**
	 * 承認者
	 * 
	 * @param swk_syo_emp_code 承認者
	 */
	public void setSWK_SYO_EMP_CODE(String swk_syo_emp_code) {
		sWK_SYO_EMP_CODE = swk_syo_emp_code;
	}

	/**
	 * ｼｽﾃﾑ区分
	 * 
	 * @return ｼｽﾃﾑ区分
	 */
	public String getSWK_SYS_KBN() {
		return sWK_SYS_KBN;
	}

	/**
	 * ｼｽﾃﾑ区分
	 * 
	 * @param swk_sys_kbn ｼｽﾃﾑ区分
	 */
	public void setSWK_SYS_KBN(String swk_sys_kbn) {
		sWK_SYS_KBN = swk_sys_kbn;
	}

	/**
	 * 伝票摘要
	 * 
	 * @return 伝票摘要
	 */
	public String getSWK_TEK() {
		return sWK_TEK;
	}

	/**
	 * 伝票摘要
	 * 
	 * @param swk_tek 伝票摘要
	 */
	public void setSWK_TEK(String swk_tek) {
		sWK_TEK = swk_tek;
	}

	/**
	 * 伝票摘要コード
	 * 
	 * @return 伝票摘要コード
	 */
	public String getSWK_TEK_CODE() {
		return sWK_TEK_CODE;
	}

	/**
	 * 伝票摘要コード
	 * 
	 * @param swk_tek_code 伝票摘要コード
	 */
	public void setSWK_TEK_CODE(String swk_tek_code) {
		sWK_TEK_CODE = swk_tek_code;
	}

	/**
	 * 受付部門コード
	 * 
	 * @return 受付部門コード
	 */
	public String getSWK_UKE_DEP_CODE() {
		return sWK_UKE_DEP_CODE;
	}

	/**
	 * 受付部門コード
	 * 
	 * @param swk_uke_dep_code 受付部門コード
	 */
	public void setSWK_UKE_DEP_CODE(String swk_uke_dep_code) {
		sWK_UKE_DEP_CODE = swk_uke_dep_code;
	}

	/**
	 * 入金予定日
	 * 
	 * @return 入金予定日
	 */
	public String getSWK_AR_DATE() {
		return sWK_AR_DATE;
	}

	/**
	 * 入金予定日
	 * 
	 * @param swk_ar_date 入金予定日
	 */
	public void setSWK_AR_DATE(String swk_ar_date) {
		sWK_AR_DATE = swk_ar_date;
	}

	/**
	 * 銀行口座ｺｰﾄﾞ
	 * 
	 * @return 銀行口座ｺｰﾄﾞ
	 */
	public String getSWK_CBK_CODE() {
		return sWK_CBK_CODE;
	}

	/**
	 * 銀行口座ｺｰﾄﾞ
	 * 
	 * @param swk_cbk_code 銀行口座ｺｰﾄﾞ
	 */
	public void setSWK_CBK_CODE(String swk_cbk_code) {
		sWK_CBK_CODE = swk_cbk_code;
	}

	/**
	 * 支払方法
	 * 
	 * @return 支払方法
	 */
	public String getSWK_HOH_CODE() {
		return sWK_HOH_CODE;
	}

	/**
	 * 支払方法
	 * 
	 * @param swk_hoh_code 支払方法
	 */
	public void setSWK_HOH_CODE(String swk_hoh_code) {
		sWK_HOH_CODE = swk_hoh_code;
	}

	/**
	 * 保留区分
	 * 
	 * @return 保留区分
	 */
	public int getSWK_HORYU_KBN() {
		return sWK_HORYU_KBN;
	}

	/**
	 * 保留区分
	 * 
	 * @param swk_horyu_kbn 保留区分
	 */
	public void setSWK_HORYU_KBN(int swk_horyu_kbn) {
		sWK_HORYU_KBN = swk_horyu_kbn;
	}

	/**
	 * 支払日
	 * 
	 * @return 支払日
	 */
	public String getSWK_SIHA_DATE() {
		return sWK_SIHA_DATE;
	}

	/**
	 * 支払日
	 * 
	 * @param swk_siha_date 支払日
	 */
	public void setSWK_SIHA_DATE(String swk_siha_date) {
		sWK_SIHA_DATE = swk_siha_date;
	}

	/**
	 * 支払区分
	 * 
	 * @return 支払区分
	 */
	public String getSWK_SIHA_KBN() {
		return sWK_SIHA_KBN;
	}

	/**
	 * 支払区分
	 * 
	 * @param swk_siha_kbn 支払区分
	 */
	public void setSWK_SIHA_KBN(String swk_siha_kbn) {
		sWK_SIHA_KBN = swk_siha_kbn;
	}

	/**
	 * 取引先条件ｺｰﾄﾞ
	 * 
	 * @return 取引先条件ｺｰﾄﾞ
	 */
	public String getSWK_TJK_CODE() {
		return sWK_TJK_CODE;
	}

	/**
	 * 取引先条件ｺｰﾄﾞ
	 * 
	 * @param swk_tjk_code 取引先条件ｺｰﾄﾞ
	 */
	public void setSWK_TJK_CODE(String swk_tjk_code) {
		sWK_TJK_CODE = swk_tjk_code;
	}

	/**
	 * 保留区分
	 * 
	 * @return true:保留
	 */
	public boolean isSuspended() {
		return BooleanUtil.toBoolean(sWK_HORYU_KBN);
	}

	/**
	 * 保留区分
	 * 
	 * @param b true:保留
	 */
	public void setSuspended(boolean b) {
		sWK_HORYU_KBN = BooleanUtil.toInt(b);
	}

	/**
	 * 支払区分
	 * 
	 * @return 支払区分
	 */
	public PaymentDateType getPaymentType() {
		return PaymentDateType.getPaymentDateType(sWK_SIHA_KBN);
	}

	/**
	 * 支払区分
	 * 
	 * @param paymentDateType 支払区分
	 */
	public void setPaymentType(PaymentDateType paymentDateType) {
		sWK_SIHA_KBN = paymentDateType.value;
	}

	/**
	 * 通貨 少数点桁数
	 * 
	 * @return 通貨 少数点桁数
	 */
	public int getCUR_DEC_KETA() {
		return cUR_DEC_KETA;
	}

	/**
	 * 通貨 少数点桁数
	 * 
	 * @param cur_dec_keta 通貨 少数点桁数
	 */
	public void setCUR_DEC_KETA(int cur_dec_keta) {
		cUR_DEC_KETA = cur_dec_keta;
	}

	/**
	 * 受入日
	 * 
	 * @return 受入日
	 */
	public String getSWK_UKE_DATE() {
		return sWK_UKE_DATE;
	}

	/**
	 * 受入日
	 * 
	 * @param swk_uke_date 受入日
	 */
	public void setSWK_UKE_DATE(String swk_uke_date) {
		sWK_UKE_DATE = swk_uke_date;
	}

}
