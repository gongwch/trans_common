package jp.co.ais.trans2.model.balance;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���c���N���X
 */
public class AP_ZAN extends TransferBase {

	/** �e�[�u���h�c */
	public static final String TABLE = "AP_ZAN";

	/** ��ЃR�[�h */
	protected String kAI_CODE;

	/** ��З��� */
	protected String kAI_NAME_S;

	/** �`�[���t */
	protected Date zAN_DEN_DATE;

	/** �`�[�ԍ� */
	protected String zAN_DEN_NO;

	/** �x���s�ԍ� */
	protected int zAN_GYO_NO;

	/** �ݎ؋敪 */
	protected int zAN_DC_KBN;

	/** �ȖڃR�[�h */
	protected String zAN_KMK_CODE;

	/** �Ȗڗ��� */
	protected String zAN_KMK_NAME_S;

	/** �⏕�ȖڃR�[�h */
	protected String zAN_HKM_CODE;

	/** �⏕�Ȗڗ��� */
	protected String zAN_HKM_NAME_S;

	/** ����ȖڃR�[�h */
	protected String zAN_UKM_CODE;

	/** ����Ȗڗ��� */
	protected String zAN_UKM_NAME_S;

	/** �v�㕔��R�[�h */
	protected String zAN_DEP_CODE;

	/** �v�㕔�嗪�� */
	protected String zAN_DEP_NAME_S;

	/** ��t����R�[�h */
	protected String zAN_UKE_DEP_CODE;

	/** �`�[�E�v�R�[�h */
	protected String zAN_TEK_CODE;

	/** �`�[�E�v */
	protected String zAN_TEK;

	/** �x����R�[�h */
	protected String zAN_SIHA_CODE;

	/** �x���旪�� */
	protected String zAN_SIHA_NAME_S;

	/** �M�ݎx�����z */
	protected BigDecimal zAN_KIN;

	/** �x���\��� */
	protected Date zAN_SIHA_DATE;

	/** �x�����ѓ� */
	protected Date zAN_JSK_DATE;

	/** �U���� */
	protected Date zAN_FURI_DATE;

	/** �x���敪 */
	protected String zAN_SIHA_KBN;

	/** �x�����@�R�[�h */
	protected String zAN_HOH_CODE;

	/** �ۗ��敪 */
	protected int zAN_HORYU_KBN;

	/** �x�����ϋ敪 */
	protected int zAN_KESAI_KBN;

	/** �������ԍ� */
	protected String zAN_SEI_NO;

	/** �f�[�^�敪 */
	protected String zAN_DATA_KBN;

	/** �x�������R�[�h */
	protected String zAN_NAI_CODE;

	/** �U���萔���敪 */
	protected int zAN_FURI_TESU_KBN;

	/** �X���敪 */
	protected int zAN_YUSO_KBN;

	/** �X���萔���敪 */
	protected int zAN_YUSO_TESU_KBN;

	/** ��`�T�C�g */
	protected int zAN_TEG_SAITO;

	/** ��`�x������ */
	protected Date zAN_TEG_SIHA_KIJITU;

	/** �U���U�o��s�����R�[�h */
	protected String zAN_FURI_CBK_CODE;

	/** �U���U�o��s�R�[�h */
	protected String zAN_FURI_BNK_CODE;

	/** �U���U�o�x�X�R�[�h */
	protected String zAN_FURI_STN_CODE;

	/** ��`�U�o��s�����R�[�h */
	protected String zAN_TEG_CBK_CODE;

	/** ��`�U�o��s�R�[�h */
	protected String zAN_TEG_BNK_CODE;

	/** ��`�U�o�x�X�R�[�h */
	protected String zAN_TEG_STN_CODE;

	/** ���׏o�͋敪 */
	protected int zAN_LIST_KBN;

	/** �x���`�[�ԍ� */
	protected String zAN_SIHA_DEN_NO;

	/** �x���`�[�s�ԍ� */
	protected int zAN_SIHA_GYO_NO;

	/** �x�������z�敪 */
	protected int zAN_SIHA_KAGEN_KBN;

	/** ��`�x�������z�敪 */
	protected int zAN_TEG_KAGEN_KBN;

	/** �U���萔�������z�敪 */
	protected int zAN_FURI_KAGEN_KBN;

	/** �o�^���t */
	protected Date zAN_INP_DATE;

	/** �X�V���t */
	protected Date uPD_DATE;

	/** �v���O�����h�c */
	protected String pRG_ID;

	/** ���[�U�[�h�c */
	protected String uSR_ID;

	/** ������������ */
	protected String zAN_TJK_CODE;

	/** �ʉݺ��� */
	protected String zAN_CUR_CODE;

	/** �ʉݏ����_���� */
	protected int zAN_CUR_DEC_KETA;

	/** �ʉ݃��[�g�W�� */
	protected int zAN_CUR_RATE_POW;

	/** ڰ� */
	protected BigDecimal zAN_CUR_RATE;

	/** ���͎x�����z */
	protected BigDecimal zAN_IN_SIHA_KIN;

	/** ���ы敪 */
	protected String zAN_SYS_KBN;

	/** �`�[��� */
	protected String zAN_DEN_SYU;

	/** ��tNO */
	protected String zAN_UTK_NO;

	/** �A�h�I���R�[�h1 */
	protected String aDD_CODE_1 = null;

	/** �A�h�I���R�[�h2 */
	protected String aDD_CODE_2 = null;

	/** �A�h�I���R�[�h3 */
	protected String aDD_CODE_3 = null;

	/** �A�h�I���R�[�h4 */
	protected String aDD_CODE_4 = null;

	/** �A�h�I���R�[�h5 */
	protected String aDD_CODE_5 = null;

	/** �A�h�I���R�[�h6 */
	protected String aDD_CODE_6 = null;

	/** �ǉ�����1 */
	protected String aDD_NAME_1 = null;

	/** �ǉ�����2 */
	protected String aDD_NAME_2 = null;

	/** �ǉ�����3 */
	protected String aDD_NAME_3 = null;

	/** �ǉ�����4 */
	protected String aDD_NAME_4 = null;

	/** �ǉ�����5 */
	protected String aDD_NAME_5 = null;

	/** �ǉ�����6 */
	protected String aDD_NAME_6 = null;

	/** �`�[�d��s�ԍ� */
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
	 * �A�h�I���R�[�h1�̎擾
	 * 
	 * @return aDD_CODE_1 �A�h�I���R�[�h1
	 */
	public String getADD_CODE_1() {
		return aDD_CODE_1;
	}

	/**
	 * �A�h�I���R�[�h1�̐ݒ�
	 * 
	 * @param aDD_CODE_1 �A�h�I���R�[�h1
	 */
	public void setADD_CODE_1(String aDD_CODE_1) {
		this.aDD_CODE_1 = aDD_CODE_1;
	}

	/**
	 * �A�h�I���R�[�h2�̎擾
	 * 
	 * @return aDD_CODE_2 �A�h�I���R�[�h2
	 */
	public String getADD_CODE_2() {
		return aDD_CODE_2;
	}

	/**
	 * �A�h�I���R�[�h2�̐ݒ�
	 * 
	 * @param aDD_CODE_2 �A�h�I���R�[�h2
	 */
	public void setADD_CODE_2(String aDD_CODE_2) {
		this.aDD_CODE_2 = aDD_CODE_2;
	}

	/**
	 * �A�h�I���R�[�h3�̎擾
	 * 
	 * @return aDD_CODE_3 �A�h�I���R�[�h3
	 */
	public String getADD_CODE_3() {
		return aDD_CODE_3;
	}

	/**
	 * �A�h�I���R�[�h3�̐ݒ�
	 * 
	 * @param aDD_CODE_3 �A�h�I���R�[�h3
	 */
	public void setADD_CODE_3(String aDD_CODE_3) {
		this.aDD_CODE_3 = aDD_CODE_3;
	}

	/**
	 * �A�h�I���R�[�h4�̎擾
	 * 
	 * @return aDD_CODE_4 �A�h�I���R�[�h4
	 */
	public String getADD_CODE_4() {
		return aDD_CODE_4;
	}

	/**
	 * �A�h�I���R�[�h4�̐ݒ�
	 * 
	 * @param aDD_CODE_4 �A�h�I���R�[�h4
	 */
	public void setADD_CODE_4(String aDD_CODE_4) {
		this.aDD_CODE_4 = aDD_CODE_4;
	}

	/**
	 * �A�h�I���R�[�h5�̎擾
	 * 
	 * @return aDD_CODE_5 �A�h�I���R�[�h5
	 */
	public String getADD_CODE_5() {
		return aDD_CODE_5;
	}

	/**
	 * �A�h�I���R�[�h5�̐ݒ�
	 * 
	 * @param aDD_CODE_5 �A�h�I���R�[�h5
	 */
	public void setADD_CODE_5(String aDD_CODE_5) {
		this.aDD_CODE_5 = aDD_CODE_5;
	}

	/**
	 * �A�h�I���R�[�h6�̎擾
	 * 
	 * @return aDD_CODE_6 �A�h�I���R�[�h6
	 */
	public String getADD_CODE_6() {
		return aDD_CODE_6;
	}

	/**
	 * �A�h�I���R�[�h6�̐ݒ�
	 * 
	 * @param aDD_CODE_6 �A�h�I���R�[�h6
	 */
	public void setADD_CODE_6(String aDD_CODE_6) {
		this.aDD_CODE_6 = aDD_CODE_6;
	}

	/**
	 * �ǉ�����1�̎擾
	 * 
	 * @return aDD_NAME_1 �ǉ�����1
	 */
	public String getADD_NAME_1() {
		return aDD_NAME_1;
	}

	/**
	 * �ǉ�����1�̐ݒ�
	 * 
	 * @param aDD_NAME_1 �ǉ�����1
	 */
	public void setADD_NAME_1(String aDD_NAME_1) {
		this.aDD_NAME_1 = aDD_NAME_1;
	}

	/**
	 * �ǉ�����2�̎擾
	 * 
	 * @return aDD_NAME_2 �ǉ�����2
	 */
	public String getADD_NAME_2() {
		return aDD_NAME_2;
	}

	/**
	 * �ǉ�����2�̐ݒ�
	 * 
	 * @param aDD_NAME_2 �ǉ�����2
	 */
	public void setADD_NAME_2(String aDD_NAME_2) {
		this.aDD_NAME_2 = aDD_NAME_2;
	}

	/**
	 * �ǉ�����3�̎擾
	 * 
	 * @return aDD_NAME_3 �ǉ�����3
	 */
	public String getADD_NAME_3() {
		return aDD_NAME_3;
	}

	/**
	 * �ǉ�����3�̐ݒ�
	 * 
	 * @param aDD_NAME_3 �ǉ�����3
	 */
	public void setADD_NAME_3(String aDD_NAME_3) {
		this.aDD_NAME_3 = aDD_NAME_3;
	}

	/**
	 * �ǉ�����4�̎擾
	 * 
	 * @return aDD_NAME_4 �ǉ�����4
	 */
	public String getADD_NAME_4() {
		return aDD_NAME_4;
	}

	/**
	 * �ǉ�����4�̐ݒ�
	 * 
	 * @param aDD_NAME_4 �ǉ�����4
	 */
	public void setADD_NAME_4(String aDD_NAME_4) {
		this.aDD_NAME_4 = aDD_NAME_4;
	}

	/**
	 * �ǉ�����5�̎擾
	 * 
	 * @return aDD_NAME_5 �ǉ�����5
	 */
	public String getADD_NAME_5() {
		return aDD_NAME_5;
	}

	/**
	 * �ǉ�����5�̐ݒ�
	 * 
	 * @param aDD_NAME_5 �ǉ�����5
	 */
	public void setADD_NAME_5(String aDD_NAME_5) {
		this.aDD_NAME_5 = aDD_NAME_5;
	}

	/**
	 * �ǉ�����6�̎擾
	 * 
	 * @return aDD_NAME_6 �ǉ�����6
	 */
	public String getADD_NAME_6() {
		return aDD_NAME_6;
	}

	/**
	 * �ǉ�����6�̐ݒ�
	 * 
	 * @param aDD_NAME_6 �ǉ�����6
	 */
	public void setADD_NAME_6(String aDD_NAME_6) {
		this.aDD_NAME_6 = aDD_NAME_6;
	}

	/**
	 * �`�[�d��s�ԍ��̎擾
	 * 
	 * @return zAN_SWK_GYO_NO �`�[�d��s�ԍ�
	 */
	public int getZAN_SWK_GYO_NO() {
		return zAN_SWK_GYO_NO;
	}

	/**
	 * �`�[�d��s�ԍ��̐ݒ�
	 * 
	 * @param zAN_SWK_GYO_NO �`�[�d��s�ԍ�
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
