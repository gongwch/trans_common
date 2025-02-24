package jp.co.ais.trans2.model.slip;

import java.math.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;

/**
 * dó¾×
 */
public class SlipInstructionDtl extends TransferBase implements Cloneable {

	/** ïÐR[h */
	protected String kAI_CODE;

	/** `[út */
	protected String sWK_DEN_DATE;

	/** `[Ô */
	protected String sWK_DEN_NO;

	/** sÔ */
	protected int sWK_GYO_NO;

	/** ØßÔ */
	protected String sWK_SEI_NO;

	/** ÝØæª */
	protected int sWK_DC_KBN;

	/** ÈÚR[h */
	protected String sWK_KMK_CODE;

	/** âÈÚR[h */
	protected String sWK_HKM_CODE;

	/** àóÈÚR[h */
	protected String sWK_UKM_CODE;

	/** vãåR[h */
	protected String sWK_DEP_CODE;

	/** Åæª */
	protected int sWK_ZEI_KBN;

	/** MÝàz */
	protected BigDecimal sWK_KIN;

	/** ÁïÅz */
	protected BigDecimal sWK_ZEI_KIN;

	/** ÁïÅR[h */
	protected String sWK_ZEI_CODE;

	/** sEvR[h */
	protected String sWK_GYO_TEK_CODE;

	/** sEv */
	protected String sWK_GYO_TEK;

	/** æøæR[h */
	protected String sWK_TRI_CODE;

	/** ÐõR[h */
	protected String sWK_EMP_CODE;

	/** ÇPR[h */
	protected String sWK_KNR_CODE_1;

	/** ÇQR[h */
	protected String sWK_KNR_CODE_2;

	/** ÇRR[h */
	protected String sWK_KNR_CODE_3;

	/** ÇSR[h */
	protected String sWK_KNR_CODE_4;

	/** ÇTR[h */
	protected String sWK_KNR_CODE_5;

	/** ÇUR[h */
	protected String sWK_KNR_CODE_6;

	/** ñïv¾×P */
	protected String sWK_HM_1;

	/** ñïv¾×Q */
	protected String sWK_HM_2;

	/** ñïv¾×R */
	protected String sWK_HM_3;

	/** ©®dóæª */
	protected int sWK_AUTO_KBN;

	/** XVæª */
	protected int sWK_UPD_KBN;

	/** Zæª */
	protected int sWK_KSN_KBN;

	/** èÈÚR[h */
	protected String sWK_AT_KMK_CODE;

	/** èâÈÚR[h */
	protected String sWK_AT_HKM_CODE;

	/** èàóÈÚR[h */
	protected String sWK_AT_UKM_CODE;

	/** èåR[h */
	protected String sWK_AT_DEP_CODE;

	/** vãïÐº°ÄÞ */
	protected String sWK_K_KAI_CODE;

	/** ÊÝº°ÄÞ */
	protected String sWK_CUR_CODE;

	/** Ú°Ä */
	protected BigDecimal sWK_CUR_RATE;

	/** üÍàz */
	protected BigDecimal sWK_IN_KIN;

	/** ïÐÔtÖ`[æª */
	protected int sWK_TUKE_KBN;

	/** üÍÁïÅz */
	protected BigDecimal sWK_IN_ZEI_KIN;

	/** ­¶ú */
	protected String hAS_DATE;

	/** `[íÊº°ÄÞ */
	protected String dEN_SYU_CODE;

	/** ÊÝ ­_ */
	protected int cUR_DEC_KETA;

	/**
	 * `[íÊº°ÄÞ
	 * 
	 * @return `[íÊº°ÄÞ
	 */
	public String getDEN_SYU_CODE() {
		return dEN_SYU_CODE;
	}

	/**
	 * `[íÊº°ÄÞ
	 * 
	 * @param den_syu_code `[íÊº°ÄÞ
	 */
	public void setDEN_SYU_CODE(String den_syu_code) {
		dEN_SYU_CODE = den_syu_code;
	}

	/**
	 * ­¶ú
	 * 
	 * @return ­¶ú
	 */
	public String getHAS_DATE() {
		return hAS_DATE;
	}

	/**
	 * ­¶ú
	 * 
	 * @param has_date ­¶ú
	 */
	public void setHAS_DATE(String has_date) {
		hAS_DATE = has_date;
	}

	/**
	 * ïÐR[h
	 * 
	 * @return ïÐR[h
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * ïÐR[h
	 * 
	 * @param kai_code ïÐR[h
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * èåR[h
	 * 
	 * @return èåR[h
	 */
	public String getSWK_AT_DEP_CODE() {
		return sWK_AT_DEP_CODE;
	}

	/**
	 * èåR[h
	 * 
	 * @param swk_at_dep_code èåR[h
	 */
	public void setSWK_AT_DEP_CODE(String swk_at_dep_code) {
		sWK_AT_DEP_CODE = swk_at_dep_code;
	}

	/**
	 * èâÈÚR[h
	 * 
	 * @return èâÈÚR[h
	 */
	public String getSWK_AT_HKM_CODE() {
		return sWK_AT_HKM_CODE;
	}

	/**
	 * èâÈÚR[h
	 * 
	 * @param swk_at_hkm_code èâÈÚR[h
	 */
	public void setSWK_AT_HKM_CODE(String swk_at_hkm_code) {
		sWK_AT_HKM_CODE = swk_at_hkm_code;
	}

	/**
	 * èÈÚR[h
	 * 
	 * @return èÈÚR[h
	 */
	public String getSWK_AT_KMK_CODE() {
		return sWK_AT_KMK_CODE;
	}

	/**
	 * èÈÚR[h
	 * 
	 * @param swk_at_kmk_code èÈÚR[h
	 */
	public void setSWK_AT_KMK_CODE(String swk_at_kmk_code) {
		sWK_AT_KMK_CODE = swk_at_kmk_code;
	}

	/**
	 * èàóÈÚR[h
	 * 
	 * @return èàóÈÚR[h
	 */
	public String getSWK_AT_UKM_CODE() {
		return sWK_AT_UKM_CODE;
	}

	/**
	 * èàóÈÚR[h
	 * 
	 * @param swk_at_ukm_code èàóÈÚR[h
	 */
	public void setSWK_AT_UKM_CODE(String swk_at_ukm_code) {
		sWK_AT_UKM_CODE = swk_at_ukm_code;
	}

	/**
	 * ©®dóæª
	 * 
	 * @return ©®dóæª
	 */
	public int getSWK_AUTO_KBN() {
		return sWK_AUTO_KBN;
	}

	/**
	 * ©®dó©Ç¤©
	 * 
	 * @return sWK_AUTO_KBN true:©®dó
	 */
	public boolean isAutoDetail() {
		return BooleanUtil.toBoolean(sWK_AUTO_KBN);
	}

	/**
	 * ©®dóæª
	 * 
	 * @param swk_auto_kbn ©®dóæª
	 */
	public void setSWK_AUTO_KBN(int swk_auto_kbn) {
		sWK_AUTO_KBN = swk_auto_kbn;
	}

	/**
	 * ÊÝº°ÄÞ
	 * 
	 * @return ÊÝº°ÄÞ
	 */
	public String getSWK_CUR_CODE() {
		return sWK_CUR_CODE;
	}

	/**
	 * ÊÝº°ÄÞ
	 * 
	 * @param swk_cur_code ÊÝº°ÄÞ
	 */
	public void setSWK_CUR_CODE(String swk_cur_code) {
		sWK_CUR_CODE = swk_cur_code;
	}

	/**
	 * Ú°Ä
	 * 
	 * @return Ú°Ä
	 */
	public BigDecimal getSWK_CUR_RATE() {
		return sWK_CUR_RATE;
	}

	/**
	 * Ú°Ä
	 * 
	 * @param swk_cur_rate Ú°Ä
	 */
	public void setSWK_CUR_RATE(BigDecimal swk_cur_rate) {
		sWK_CUR_RATE = swk_cur_rate;
	}

	/**
	 * ÝØæª
	 * 
	 * @return ÝØæª
	 */
	public int getSWK_DC_KBN() {
		return sWK_DC_KBN;
	}

	/**
	 * ÝØæª
	 * 
	 * @param swk_dc_kbn ÝØæª
	 */
	public void setSWK_DC_KBN(int swk_dc_kbn) {
		sWK_DC_KBN = swk_dc_kbn;
	}

	/**
	 * ÝØ
	 * 
	 * @param dc ÝØ
	 */
	public void setDC(Dc dc) {
		sWK_DC_KBN = dc.value;
	}

	/**
	 * ÝØ
	 * 
	 * @return ÝØ
	 */
	public Dc getDC() {
		return Dc.getDc(sWK_DC_KBN);
	}

	/**
	 * `[út
	 * 
	 * @return `[út
	 */
	public String getSWK_DEN_DATE() {
		return sWK_DEN_DATE;
	}

	/**
	 * `[út
	 * 
	 * @param swk_den_date `[út
	 */
	public void setSWK_DEN_DATE(String swk_den_date) {
		sWK_DEN_DATE = swk_den_date;
	}

	/**
	 * `[Ô
	 * 
	 * @return `[Ô
	 */
	public String getSWK_DEN_NO() {
		return sWK_DEN_NO;
	}

	/**
	 * `[Ô
	 * 
	 * @param swk_den_no `[Ô
	 */
	public void setSWK_DEN_NO(String swk_den_no) {
		sWK_DEN_NO = swk_den_no;
	}

	/**
	 * vãåR[h
	 * 
	 * @return vãåR[h
	 */
	public String getSWK_DEP_CODE() {
		return sWK_DEP_CODE;
	}

	/**
	 * vãåR[h
	 * 
	 * @param swk_dep_code vãåR[h
	 */
	public void setSWK_DEP_CODE(String swk_dep_code) {
		sWK_DEP_CODE = swk_dep_code;
	}

	/**
	 * ÐõR[h
	 * 
	 * @return ÐõR[h
	 */
	public String getSWK_EMP_CODE() {
		return sWK_EMP_CODE;
	}

	/**
	 * ÐõR[h
	 * 
	 * @param swk_emp_code ÐõR[h
	 */
	public void setSWK_EMP_CODE(String swk_emp_code) {
		sWK_EMP_CODE = swk_emp_code;
	}

	/**
	 * sÔ
	 * 
	 * @return sÔ
	 */
	public int getSWK_GYO_NO() {
		return sWK_GYO_NO;
	}

	/**
	 * sÔ
	 * 
	 * @param swk_gyo_no sÔ
	 */
	public void setSWK_GYO_NO(int swk_gyo_no) {
		sWK_GYO_NO = swk_gyo_no;
	}

	/**
	 * sEv
	 * 
	 * @return sEv
	 */
	public String getSWK_GYO_TEK() {
		return sWK_GYO_TEK;
	}

	/**
	 * sEv
	 * 
	 * @param swk_gyo_tek sEv
	 */
	public void setSWK_GYO_TEK(String swk_gyo_tek) {
		sWK_GYO_TEK = swk_gyo_tek;
	}

	/**
	 * sEvR[h
	 * 
	 * @return sEvR[h
	 */
	public String getSWK_GYO_TEK_CODE() {
		return sWK_GYO_TEK_CODE;
	}

	/**
	 * sEvR[h
	 * 
	 * @param swk_gyo_tek_code sEvR[h
	 */
	public void setSWK_GYO_TEK_CODE(String swk_gyo_tek_code) {
		sWK_GYO_TEK_CODE = swk_gyo_tek_code;
	}

	/**
	 * âÈÚR[h
	 * 
	 * @return âÈÚR[h
	 */
	public String getSWK_HKM_CODE() {
		return sWK_HKM_CODE;
	}

	/**
	 * âÈÚR[h
	 * 
	 * @param swk_hkm_code âÈÚR[h
	 */
	public void setSWK_HKM_CODE(String swk_hkm_code) {
		sWK_HKM_CODE = swk_hkm_code;
	}

	/**
	 * ñïv¾×P
	 * 
	 * @return ñïv¾×P
	 */
	public String getSWK_HM_1() {
		return sWK_HM_1;
	}

	/**
	 * ñïv¾×P
	 * 
	 * @param swk_hm_1 ñïv¾×P
	 */
	public void setSWK_HM_1(String swk_hm_1) {
		sWK_HM_1 = swk_hm_1;
	}

	/**
	 * ñïv¾×Q
	 * 
	 * @return ñïv¾×Q
	 */
	public String getSWK_HM_2() {
		return sWK_HM_2;
	}

	/**
	 * ñïv¾×Q
	 * 
	 * @param swk_hm_2 ñïv¾×Q
	 */
	public void setSWK_HM_2(String swk_hm_2) {
		sWK_HM_2 = swk_hm_2;
	}

	/**
	 * ñïv¾×R
	 * 
	 * @return ñïv¾×R
	 */
	public String getSWK_HM_3() {
		return sWK_HM_3;
	}

	/**
	 * ñïv¾×R
	 * 
	 * @param swk_hm_3 ñïv¾×R
	 */
	public void setSWK_HM_3(String swk_hm_3) {
		sWK_HM_3 = swk_hm_3;
	}

	/**
	 * üÍàz
	 * 
	 * @return sWK_IN_KIN üÍàz
	 */
	public BigDecimal getSWK_IN_KIN() {
		return sWK_IN_KIN;
	}

	/**
	 * üÍàz
	 * 
	 * @param swk_in_kin üÍàz
	 */
	public void setSWK_IN_KIN(BigDecimal swk_in_kin) {
		sWK_IN_KIN = swk_in_kin;
	}

	/**
	 * üÍÁïÅz
	 * 
	 * @return üÍÁïÅz
	 */
	public BigDecimal getSWK_IN_ZEI_KIN() {
		return sWK_IN_ZEI_KIN;
	}

	/**
	 * üÍÁïÅz
	 * 
	 * @param swk_in_zei_kin üÍÁïÅz
	 */
	public void setSWK_IN_ZEI_KIN(BigDecimal swk_in_zei_kin) {
		sWK_IN_ZEI_KIN = swk_in_zei_kin;
	}

	/**
	 * vãïÐº°ÄÞ
	 * 
	 * @return vãïÐº°ÄÞ
	 */
	public String getSWK_K_KAI_CODE() {
		return sWK_K_KAI_CODE;
	}

	/**
	 * vãïÐº°ÄÞ
	 * 
	 * @param swk_k_kai_code vãïÐº°ÄÞ
	 */
	public void setSWK_K_KAI_CODE(String swk_k_kai_code) {
		sWK_K_KAI_CODE = swk_k_kai_code;
	}

	/**
	 * MÝàz
	 * 
	 * @return MÝàz
	 */
	public BigDecimal getSWK_KIN() {
		return sWK_KIN;
	}

	/**
	 * MÝàz
	 * 
	 * @param swk_kin MÝàz
	 */
	public void setSWK_KIN(BigDecimal swk_kin) {
		sWK_KIN = swk_kin;
	}

	/**
	 * ÈÚR[h
	 * 
	 * @return ÈÚR[h
	 */
	public String getSWK_KMK_CODE() {
		return sWK_KMK_CODE;
	}

	/**
	 * ÈÚR[h
	 * 
	 * @param swk_kmk_code ÈÚR[h
	 */
	public void setSWK_KMK_CODE(String swk_kmk_code) {
		sWK_KMK_CODE = swk_kmk_code;
	}

	/**
	 * ÇPR[h
	 * 
	 * @return ÇPR[h
	 */
	public String getSWK_KNR_CODE_1() {
		return sWK_KNR_CODE_1;
	}

	/**
	 * ÇPR[h
	 * 
	 * @param swk_knr_code_1 ÇPR[h
	 */
	public void setSWK_KNR_CODE_1(String swk_knr_code_1) {
		sWK_KNR_CODE_1 = swk_knr_code_1;
	}

	/**
	 * ÇQR[h
	 * 
	 * @return ÇQR[h
	 */
	public String getSWK_KNR_CODE_2() {
		return sWK_KNR_CODE_2;
	}

	/**
	 * ÇQR[h
	 * 
	 * @param swk_knr_code_2 ÇQR[h
	 */
	public void setSWK_KNR_CODE_2(String swk_knr_code_2) {
		sWK_KNR_CODE_2 = swk_knr_code_2;
	}

	/**
	 * ÇRR[h
	 * 
	 * @return ÇRR[h
	 */
	public String getSWK_KNR_CODE_3() {
		return sWK_KNR_CODE_3;
	}

	/**
	 * ÇRR[h
	 * 
	 * @param swk_knr_code_3 ÇRR[h
	 */
	public void setSWK_KNR_CODE_3(String swk_knr_code_3) {
		sWK_KNR_CODE_3 = swk_knr_code_3;
	}

	/**
	 * ÇSR[h
	 * 
	 * @return ÇSR[h
	 */
	public String getSWK_KNR_CODE_4() {
		return sWK_KNR_CODE_4;
	}

	/**
	 * ÇSR[h
	 * 
	 * @param swk_knr_code_4 ÇSR[h
	 */
	public void setSWK_KNR_CODE_4(String swk_knr_code_4) {
		sWK_KNR_CODE_4 = swk_knr_code_4;
	}

	/**
	 * ÇTR[h
	 * 
	 * @return ÇTR[h
	 */
	public String getSWK_KNR_CODE_5() {
		return sWK_KNR_CODE_5;
	}

	/**
	 * ÇTR[h
	 * 
	 * @param swk_knr_code_5 ÇTR[h
	 */
	public void setSWK_KNR_CODE_5(String swk_knr_code_5) {
		sWK_KNR_CODE_5 = swk_knr_code_5;
	}

	/**
	 * ÇUR[h
	 * 
	 * @return ÇUR[h
	 */
	public String getSWK_KNR_CODE_6() {
		return sWK_KNR_CODE_6;
	}

	/**
	 * ÇUR[h
	 * 
	 * @param swk_knr_code_6 ÇUR[h
	 */
	public void setSWK_KNR_CODE_6(String swk_knr_code_6) {
		sWK_KNR_CODE_6 = swk_knr_code_6;
	}

	/**
	 * Zæª
	 * 
	 * @return Zæª
	 */
	public int getSWK_KSN_KBN() {
		return sWK_KSN_KBN;
	}

	/**
	 * Zæª
	 * 
	 * @param swk_ksn_kbn Zæª
	 */
	public void setSWK_KSN_KBN(int swk_ksn_kbn) {
		sWK_KSN_KBN = swk_ksn_kbn;
	}

	/**
	 * ØßÔ
	 * 
	 * @return ØßÔ
	 */
	public String getSWK_SEI_NO() {
		return sWK_SEI_NO;
	}

	/**
	 * ØßÔ
	 * 
	 * @param swk_sei_no ØßÔ
	 */
	public void setSWK_SEI_NO(String swk_sei_no) {
		sWK_SEI_NO = swk_sei_no;
	}

	/**
	 * æøæR[h
	 * 
	 * @return æøæR[h
	 */
	public String getSWK_TRI_CODE() {
		return sWK_TRI_CODE;
	}

	/**
	 * æøæR[h
	 * 
	 * @param swk_tri_code æøæR[h
	 */
	public void setSWK_TRI_CODE(String swk_tri_code) {
		sWK_TRI_CODE = swk_tri_code;
	}

	/**
	 * ïÐÔtÖ`[æª
	 * 
	 * @return ïÐÔtÖ`[æª
	 */
	public int getSWK_TUKE_KBN() {
		return sWK_TUKE_KBN;
	}

	/**
	 * ïÐÔtÖ`[æª
	 * 
	 * @param swk_tuke_kbn ïÐÔtÖ`[æª
	 */
	public void setSWK_TUKE_KBN(int swk_tuke_kbn) {
		sWK_TUKE_KBN = swk_tuke_kbn;
	}

	/**
	 * àóÈÚR[h
	 * 
	 * @return àóÈÚR[h
	 */
	public String getSWK_UKM_CODE() {
		return sWK_UKM_CODE;
	}

	/**
	 * àóÈÚR[h
	 * 
	 * @param swk_ukm_code àóÈÚR[h
	 */
	public void setSWK_UKM_CODE(String swk_ukm_code) {
		sWK_UKM_CODE = swk_ukm_code;
	}

	/**
	 * XVæª
	 * 
	 * @return XVæª
	 */
	public int getSWK_UPD_KBN() {
		return sWK_UPD_KBN;
	}

	/**
	 * XVæª
	 * 
	 * @param swk_upd_kbn XVæª
	 */
	public void setSWK_UPD_KBN(int swk_upd_kbn) {
		sWK_UPD_KBN = swk_upd_kbn;
	}

	/**
	 * ÁïÅR[h
	 * 
	 * @return ÁïÅR[h
	 */
	public String getSWK_ZEI_CODE() {
		return sWK_ZEI_CODE;
	}

	/**
	 * ÁïÅR[h
	 * 
	 * @param swk_zei_code ÁïÅR[h
	 */
	public void setSWK_ZEI_CODE(String swk_zei_code) {
		sWK_ZEI_CODE = swk_zei_code;
	}

	/**
	 * Åæª
	 * 
	 * @return Åæª
	 */
	public int getSWK_ZEI_KBN() {
		return sWK_ZEI_KBN;
	}

	/**
	 * Åæª
	 * 
	 * @param swk_zei_kbn Åæª
	 */
	public void setSWK_ZEI_KBN(int swk_zei_kbn) {
		sWK_ZEI_KBN = swk_zei_kbn;
	}

	/**
	 * Åæª
	 * 
	 * @param type Åæª
	 */
	public void setTaxCalcType(TaxCalcType type) {
		this.sWK_ZEI_KBN = type.value;
	}

	/**
	 * ÁïÅz
	 * 
	 * @return ÁïÅz
	 */
	public BigDecimal getSWK_ZEI_KIN() {
		return sWK_ZEI_KIN;
	}

	/**
	 * ÁïÅz
	 * 
	 * @param swk_zei_kin ÁïÅz
	 */
	public void setSWK_ZEI_KIN(BigDecimal swk_zei_kin) {
		sWK_ZEI_KIN = swk_zei_kin;
	}

	/** ótåR[h */
	protected String sWK_UKE_DEP_CODE;

	/** `[EvR[h */
	protected String sWK_TEK_CODE;

	/** `[Ev */
	protected String sWK_TEK;

	/** ³FÒ */
	protected String sWK_SYO_EMP_CODE;

	/** ³Fú */
	protected String sWK_SYO_DATE;

	/** ËÒ */
	protected String sWK_IRAI_EMP_CODE;

	/** ËåR[h */
	protected String sWK_IRAI_DEP_CODE;

	/** Ëú */
	protected String sWK_IRAI_DATE;

	/** ¼½ÃÑæª */
	protected String sWK_SYS_KBN;

	/** `[íÊ */
	protected String sWK_DEN_SYU;

	// APs«ª--
	/** x¥æª */
	protected String sWK_SIHA_KBN;

	/** x¥ú */
	protected String sWK_SIHA_DATE;

	/** x¥û@ */
	protected String sWK_HOH_CODE;

	/** Û¯æª */
	protected int sWK_HORYU_KBN;

	/** æøæðº°ÄÞ */
	protected String sWK_TJK_CODE;

	/** âsûÀº°ÄÞ */
	protected String sWK_CBK_CODE;

	// ARs«ª--
	/** üà\èú */
	protected String sWK_AR_DATE;

	/** óüú */
	protected String sWK_UKE_DATE;

	/**
	 * `[íÊ
	 * 
	 * @return `[íÊ
	 */
	public String getSWK_DEN_SYU() {
		return sWK_DEN_SYU;
	}

	/**
	 * `[íÊ
	 * 
	 * @param swk_den_syu `[íÊ
	 */
	public void setSWK_DEN_SYU(String swk_den_syu) {
		sWK_DEN_SYU = swk_den_syu;
	}

	/**
	 * Ëú
	 * 
	 * @return Ëú
	 */
	public String getSWK_IRAI_DATE() {
		return sWK_IRAI_DATE;
	}

	/**
	 * Ëú
	 * 
	 * @param swk_irai_date Ëú
	 */
	public void setSWK_IRAI_DATE(String swk_irai_date) {
		sWK_IRAI_DATE = swk_irai_date;
	}

	/**
	 * ËåR[h
	 * 
	 * @return ËåR[h
	 */
	public String getSWK_IRAI_DEP_CODE() {
		return sWK_IRAI_DEP_CODE;
	}

	/**
	 * ËåR[h
	 * 
	 * @param swk_irai_dep_code ËåR[h
	 */
	public void setSWK_IRAI_DEP_CODE(String swk_irai_dep_code) {
		sWK_IRAI_DEP_CODE = swk_irai_dep_code;
	}

	/**
	 * ËÒ
	 * 
	 * @return ËÒ
	 */
	public String getSWK_IRAI_EMP_CODE() {
		return sWK_IRAI_EMP_CODE;
	}

	/**
	 * ËÒ
	 * 
	 * @param swk_irai_emp_code ËÒ
	 */
	public void setSWK_IRAI_EMP_CODE(String swk_irai_emp_code) {
		sWK_IRAI_EMP_CODE = swk_irai_emp_code;
	}

	/**
	 * ³Fú
	 * 
	 * @return ³Fú
	 */
	public String getSWK_SYO_DATE() {
		return sWK_SYO_DATE;
	}

	/**
	 * ³Fú
	 * 
	 * @param swk_syo_date ³Fú
	 */
	public void setSWK_SYO_DATE(String swk_syo_date) {
		sWK_SYO_DATE = swk_syo_date;
	}

	/**
	 * ³FÒ
	 * 
	 * @return ³FÒ
	 */
	public String getSWK_SYO_EMP_CODE() {
		return sWK_SYO_EMP_CODE;
	}

	/**
	 * ³FÒ
	 * 
	 * @param swk_syo_emp_code ³FÒ
	 */
	public void setSWK_SYO_EMP_CODE(String swk_syo_emp_code) {
		sWK_SYO_EMP_CODE = swk_syo_emp_code;
	}

	/**
	 * ¼½ÃÑæª
	 * 
	 * @return ¼½ÃÑæª
	 */
	public String getSWK_SYS_KBN() {
		return sWK_SYS_KBN;
	}

	/**
	 * ¼½ÃÑæª
	 * 
	 * @param swk_sys_kbn ¼½ÃÑæª
	 */
	public void setSWK_SYS_KBN(String swk_sys_kbn) {
		sWK_SYS_KBN = swk_sys_kbn;
	}

	/**
	 * `[Ev
	 * 
	 * @return `[Ev
	 */
	public String getSWK_TEK() {
		return sWK_TEK;
	}

	/**
	 * `[Ev
	 * 
	 * @param swk_tek `[Ev
	 */
	public void setSWK_TEK(String swk_tek) {
		sWK_TEK = swk_tek;
	}

	/**
	 * `[EvR[h
	 * 
	 * @return `[EvR[h
	 */
	public String getSWK_TEK_CODE() {
		return sWK_TEK_CODE;
	}

	/**
	 * `[EvR[h
	 * 
	 * @param swk_tek_code `[EvR[h
	 */
	public void setSWK_TEK_CODE(String swk_tek_code) {
		sWK_TEK_CODE = swk_tek_code;
	}

	/**
	 * ótåR[h
	 * 
	 * @return ótåR[h
	 */
	public String getSWK_UKE_DEP_CODE() {
		return sWK_UKE_DEP_CODE;
	}

	/**
	 * ótåR[h
	 * 
	 * @param swk_uke_dep_code ótåR[h
	 */
	public void setSWK_UKE_DEP_CODE(String swk_uke_dep_code) {
		sWK_UKE_DEP_CODE = swk_uke_dep_code;
	}

	/**
	 * üà\èú
	 * 
	 * @return üà\èú
	 */
	public String getSWK_AR_DATE() {
		return sWK_AR_DATE;
	}

	/**
	 * üà\èú
	 * 
	 * @param swk_ar_date üà\èú
	 */
	public void setSWK_AR_DATE(String swk_ar_date) {
		sWK_AR_DATE = swk_ar_date;
	}

	/**
	 * âsûÀº°ÄÞ
	 * 
	 * @return âsûÀº°ÄÞ
	 */
	public String getSWK_CBK_CODE() {
		return sWK_CBK_CODE;
	}

	/**
	 * âsûÀº°ÄÞ
	 * 
	 * @param swk_cbk_code âsûÀº°ÄÞ
	 */
	public void setSWK_CBK_CODE(String swk_cbk_code) {
		sWK_CBK_CODE = swk_cbk_code;
	}

	/**
	 * x¥û@
	 * 
	 * @return x¥û@
	 */
	public String getSWK_HOH_CODE() {
		return sWK_HOH_CODE;
	}

	/**
	 * x¥û@
	 * 
	 * @param swk_hoh_code x¥û@
	 */
	public void setSWK_HOH_CODE(String swk_hoh_code) {
		sWK_HOH_CODE = swk_hoh_code;
	}

	/**
	 * Û¯æª
	 * 
	 * @return Û¯æª
	 */
	public int getSWK_HORYU_KBN() {
		return sWK_HORYU_KBN;
	}

	/**
	 * Û¯æª
	 * 
	 * @param swk_horyu_kbn Û¯æª
	 */
	public void setSWK_HORYU_KBN(int swk_horyu_kbn) {
		sWK_HORYU_KBN = swk_horyu_kbn;
	}

	/**
	 * x¥ú
	 * 
	 * @return x¥ú
	 */
	public String getSWK_SIHA_DATE() {
		return sWK_SIHA_DATE;
	}

	/**
	 * x¥ú
	 * 
	 * @param swk_siha_date x¥ú
	 */
	public void setSWK_SIHA_DATE(String swk_siha_date) {
		sWK_SIHA_DATE = swk_siha_date;
	}

	/**
	 * x¥æª
	 * 
	 * @return x¥æª
	 */
	public String getSWK_SIHA_KBN() {
		return sWK_SIHA_KBN;
	}

	/**
	 * x¥æª
	 * 
	 * @param swk_siha_kbn x¥æª
	 */
	public void setSWK_SIHA_KBN(String swk_siha_kbn) {
		sWK_SIHA_KBN = swk_siha_kbn;
	}

	/**
	 * æøæðº°ÄÞ
	 * 
	 * @return æøæðº°ÄÞ
	 */
	public String getSWK_TJK_CODE() {
		return sWK_TJK_CODE;
	}

	/**
	 * æøæðº°ÄÞ
	 * 
	 * @param swk_tjk_code æøæðº°ÄÞ
	 */
	public void setSWK_TJK_CODE(String swk_tjk_code) {
		sWK_TJK_CODE = swk_tjk_code;
	}

	/**
	 * Û¯æª
	 * 
	 * @return true:Û¯
	 */
	public boolean isSuspended() {
		return BooleanUtil.toBoolean(sWK_HORYU_KBN);
	}

	/**
	 * Û¯æª
	 * 
	 * @param b true:Û¯
	 */
	public void setSuspended(boolean b) {
		sWK_HORYU_KBN = BooleanUtil.toInt(b);
	}

	/**
	 * x¥æª
	 * 
	 * @return x¥æª
	 */
	public PaymentDateType getPaymentType() {
		return PaymentDateType.getPaymentDateType(sWK_SIHA_KBN);
	}

	/**
	 * x¥æª
	 * 
	 * @param paymentDateType x¥æª
	 */
	public void setPaymentType(PaymentDateType paymentDateType) {
		sWK_SIHA_KBN = paymentDateType.value;
	}

	/**
	 * ÊÝ ­_
	 * 
	 * @return ÊÝ ­_
	 */
	public int getCUR_DEC_KETA() {
		return cUR_DEC_KETA;
	}

	/**
	 * ÊÝ ­_
	 * 
	 * @param cur_dec_keta ÊÝ ­_
	 */
	public void setCUR_DEC_KETA(int cur_dec_keta) {
		cUR_DEC_KETA = cur_dec_keta;
	}

	/**
	 * óüú
	 * 
	 * @return óüú
	 */
	public String getSWK_UKE_DATE() {
		return sWK_UKE_DATE;
	}

	/**
	 * óüú
	 * 
	 * @param swk_uke_date óüú
	 */
	public void setSWK_UKE_DATE(String swk_uke_date) {
		sWK_UKE_DATE = swk_uke_date;
	}

}
