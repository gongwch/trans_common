package jp.co.ais.trans2.model.slip;

import java.math.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;

/**
 * �d�󖾍�
 */
public class SlipInstructionDtl extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String kAI_CODE;

	/** �`�[���t */
	protected String sWK_DEN_DATE;

	/** �`�[�ԍ� */
	protected String sWK_DEN_NO;

	/** �s�ԍ� */
	protected int sWK_GYO_NO;

	/** �؜ߔԍ� */
	protected String sWK_SEI_NO;

	/** �ݎ؋敪 */
	protected int sWK_DC_KBN;

	/** �ȖڃR�[�h */
	protected String sWK_KMK_CODE;

	/** �⏕�ȖڃR�[�h */
	protected String sWK_HKM_CODE;

	/** ����ȖڃR�[�h */
	protected String sWK_UKM_CODE;

	/** �v�㕔��R�[�h */
	protected String sWK_DEP_CODE;

	/** �ŋ敪 */
	protected int sWK_ZEI_KBN;

	/** �M�݋��z */
	protected BigDecimal sWK_KIN;

	/** ����Ŋz */
	protected BigDecimal sWK_ZEI_KIN;

	/** ����ŃR�[�h */
	protected String sWK_ZEI_CODE;

	/** �s�E�v�R�[�h */
	protected String sWK_GYO_TEK_CODE;

	/** �s�E�v */
	protected String sWK_GYO_TEK;

	/** �����R�[�h */
	protected String sWK_TRI_CODE;

	/** �Ј��R�[�h */
	protected String sWK_EMP_CODE;

	/** �Ǘ��P�R�[�h */
	protected String sWK_KNR_CODE_1;

	/** �Ǘ��Q�R�[�h */
	protected String sWK_KNR_CODE_2;

	/** �Ǘ��R�R�[�h */
	protected String sWK_KNR_CODE_3;

	/** �Ǘ��S�R�[�h */
	protected String sWK_KNR_CODE_4;

	/** �Ǘ��T�R�[�h */
	protected String sWK_KNR_CODE_5;

	/** �Ǘ��U�R�[�h */
	protected String sWK_KNR_CODE_6;

	/** ���v���ׂP */
	protected String sWK_HM_1;

	/** ���v���ׂQ */
	protected String sWK_HM_2;

	/** ���v���ׂR */
	protected String sWK_HM_3;

	/** �����d��敪 */
	protected int sWK_AUTO_KBN;

	/** �X�V�敪 */
	protected int sWK_UPD_KBN;

	/** ���Z�敪 */
	protected int sWK_KSN_KBN;

	/** ����ȖڃR�[�h */
	protected String sWK_AT_KMK_CODE;

	/** ����⏕�ȖڃR�[�h */
	protected String sWK_AT_HKM_CODE;

	/** �������ȖڃR�[�h */
	protected String sWK_AT_UKM_CODE;

	/** ���蕔��R�[�h */
	protected String sWK_AT_DEP_CODE;

	/** �v���к��� */
	protected String sWK_K_KAI_CODE;

	/** �ʉݺ��� */
	protected String sWK_CUR_CODE;

	/** ڰ� */
	protected BigDecimal sWK_CUR_RATE;

	/** ���͋��z */
	protected BigDecimal sWK_IN_KIN;

	/** ��Њԕt�֓`�[�敪 */
	protected int sWK_TUKE_KBN;

	/** ���͏���Ŋz */
	protected BigDecimal sWK_IN_ZEI_KIN;

	/** ������ */
	protected String hAS_DATE;

	/** �`�[��ʺ��� */
	protected String dEN_SYU_CODE;

	/** �ʉ� �����_���� */
	protected int cUR_DEC_KETA;

	/**
	 * �`�[��ʺ���
	 * 
	 * @return �`�[��ʺ���
	 */
	public String getDEN_SYU_CODE() {
		return dEN_SYU_CODE;
	}

	/**
	 * �`�[��ʺ���
	 * 
	 * @param den_syu_code �`�[��ʺ���
	 */
	public void setDEN_SYU_CODE(String den_syu_code) {
		dEN_SYU_CODE = den_syu_code;
	}

	/**
	 * ������
	 * 
	 * @return ������
	 */
	public String getHAS_DATE() {
		return hAS_DATE;
	}

	/**
	 * ������
	 * 
	 * @param has_date ������
	 */
	public void setHAS_DATE(String has_date) {
		hAS_DATE = has_date;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @param kai_code ��ЃR�[�h
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * ���蕔��R�[�h
	 * 
	 * @return ���蕔��R�[�h
	 */
	public String getSWK_AT_DEP_CODE() {
		return sWK_AT_DEP_CODE;
	}

	/**
	 * ���蕔��R�[�h
	 * 
	 * @param swk_at_dep_code ���蕔��R�[�h
	 */
	public void setSWK_AT_DEP_CODE(String swk_at_dep_code) {
		sWK_AT_DEP_CODE = swk_at_dep_code;
	}

	/**
	 * ����⏕�ȖڃR�[�h
	 * 
	 * @return ����⏕�ȖڃR�[�h
	 */
	public String getSWK_AT_HKM_CODE() {
		return sWK_AT_HKM_CODE;
	}

	/**
	 * ����⏕�ȖڃR�[�h
	 * 
	 * @param swk_at_hkm_code ����⏕�ȖڃR�[�h
	 */
	public void setSWK_AT_HKM_CODE(String swk_at_hkm_code) {
		sWK_AT_HKM_CODE = swk_at_hkm_code;
	}

	/**
	 * ����ȖڃR�[�h
	 * 
	 * @return ����ȖڃR�[�h
	 */
	public String getSWK_AT_KMK_CODE() {
		return sWK_AT_KMK_CODE;
	}

	/**
	 * ����ȖڃR�[�h
	 * 
	 * @param swk_at_kmk_code ����ȖڃR�[�h
	 */
	public void setSWK_AT_KMK_CODE(String swk_at_kmk_code) {
		sWK_AT_KMK_CODE = swk_at_kmk_code;
	}

	/**
	 * �������ȖڃR�[�h
	 * 
	 * @return �������ȖڃR�[�h
	 */
	public String getSWK_AT_UKM_CODE() {
		return sWK_AT_UKM_CODE;
	}

	/**
	 * �������ȖڃR�[�h
	 * 
	 * @param swk_at_ukm_code �������ȖڃR�[�h
	 */
	public void setSWK_AT_UKM_CODE(String swk_at_ukm_code) {
		sWK_AT_UKM_CODE = swk_at_ukm_code;
	}

	/**
	 * �����d��敪
	 * 
	 * @return �����d��敪
	 */
	public int getSWK_AUTO_KBN() {
		return sWK_AUTO_KBN;
	}

	/**
	 * �����d�󂩂ǂ���
	 * 
	 * @return sWK_AUTO_KBN true:�����d��
	 */
	public boolean isAutoDetail() {
		return BooleanUtil.toBoolean(sWK_AUTO_KBN);
	}

	/**
	 * �����d��敪
	 * 
	 * @param swk_auto_kbn �����d��敪
	 */
	public void setSWK_AUTO_KBN(int swk_auto_kbn) {
		sWK_AUTO_KBN = swk_auto_kbn;
	}

	/**
	 * �ʉݺ���
	 * 
	 * @return �ʉݺ���
	 */
	public String getSWK_CUR_CODE() {
		return sWK_CUR_CODE;
	}

	/**
	 * �ʉݺ���
	 * 
	 * @param swk_cur_code �ʉݺ���
	 */
	public void setSWK_CUR_CODE(String swk_cur_code) {
		sWK_CUR_CODE = swk_cur_code;
	}

	/**
	 * ڰ�
	 * 
	 * @return ڰ�
	 */
	public BigDecimal getSWK_CUR_RATE() {
		return sWK_CUR_RATE;
	}

	/**
	 * ڰ�
	 * 
	 * @param swk_cur_rate ڰ�
	 */
	public void setSWK_CUR_RATE(BigDecimal swk_cur_rate) {
		sWK_CUR_RATE = swk_cur_rate;
	}

	/**
	 * �ݎ؋敪
	 * 
	 * @return �ݎ؋敪
	 */
	public int getSWK_DC_KBN() {
		return sWK_DC_KBN;
	}

	/**
	 * �ݎ؋敪
	 * 
	 * @param swk_dc_kbn �ݎ؋敪
	 */
	public void setSWK_DC_KBN(int swk_dc_kbn) {
		sWK_DC_KBN = swk_dc_kbn;
	}

	/**
	 * �ݎ�
	 * 
	 * @param dc �ݎ�
	 */
	public void setDC(Dc dc) {
		sWK_DC_KBN = dc.value;
	}

	/**
	 * �ݎ�
	 * 
	 * @return �ݎ�
	 */
	public Dc getDC() {
		return Dc.getDc(sWK_DC_KBN);
	}

	/**
	 * �`�[���t
	 * 
	 * @return �`�[���t
	 */
	public String getSWK_DEN_DATE() {
		return sWK_DEN_DATE;
	}

	/**
	 * �`�[���t
	 * 
	 * @param swk_den_date �`�[���t
	 */
	public void setSWK_DEN_DATE(String swk_den_date) {
		sWK_DEN_DATE = swk_den_date;
	}

	/**
	 * �`�[�ԍ�
	 * 
	 * @return �`�[�ԍ�
	 */
	public String getSWK_DEN_NO() {
		return sWK_DEN_NO;
	}

	/**
	 * �`�[�ԍ�
	 * 
	 * @param swk_den_no �`�[�ԍ�
	 */
	public void setSWK_DEN_NO(String swk_den_no) {
		sWK_DEN_NO = swk_den_no;
	}

	/**
	 * �v�㕔��R�[�h
	 * 
	 * @return �v�㕔��R�[�h
	 */
	public String getSWK_DEP_CODE() {
		return sWK_DEP_CODE;
	}

	/**
	 * �v�㕔��R�[�h
	 * 
	 * @param swk_dep_code �v�㕔��R�[�h
	 */
	public void setSWK_DEP_CODE(String swk_dep_code) {
		sWK_DEP_CODE = swk_dep_code;
	}

	/**
	 * �Ј��R�[�h
	 * 
	 * @return �Ј��R�[�h
	 */
	public String getSWK_EMP_CODE() {
		return sWK_EMP_CODE;
	}

	/**
	 * �Ј��R�[�h
	 * 
	 * @param swk_emp_code �Ј��R�[�h
	 */
	public void setSWK_EMP_CODE(String swk_emp_code) {
		sWK_EMP_CODE = swk_emp_code;
	}

	/**
	 * �s�ԍ�
	 * 
	 * @return �s�ԍ�
	 */
	public int getSWK_GYO_NO() {
		return sWK_GYO_NO;
	}

	/**
	 * �s�ԍ�
	 * 
	 * @param swk_gyo_no �s�ԍ�
	 */
	public void setSWK_GYO_NO(int swk_gyo_no) {
		sWK_GYO_NO = swk_gyo_no;
	}

	/**
	 * �s�E�v
	 * 
	 * @return �s�E�v
	 */
	public String getSWK_GYO_TEK() {
		return sWK_GYO_TEK;
	}

	/**
	 * �s�E�v
	 * 
	 * @param swk_gyo_tek �s�E�v
	 */
	public void setSWK_GYO_TEK(String swk_gyo_tek) {
		sWK_GYO_TEK = swk_gyo_tek;
	}

	/**
	 * �s�E�v�R�[�h
	 * 
	 * @return �s�E�v�R�[�h
	 */
	public String getSWK_GYO_TEK_CODE() {
		return sWK_GYO_TEK_CODE;
	}

	/**
	 * �s�E�v�R�[�h
	 * 
	 * @param swk_gyo_tek_code �s�E�v�R�[�h
	 */
	public void setSWK_GYO_TEK_CODE(String swk_gyo_tek_code) {
		sWK_GYO_TEK_CODE = swk_gyo_tek_code;
	}

	/**
	 * �⏕�ȖڃR�[�h
	 * 
	 * @return �⏕�ȖڃR�[�h
	 */
	public String getSWK_HKM_CODE() {
		return sWK_HKM_CODE;
	}

	/**
	 * �⏕�ȖڃR�[�h
	 * 
	 * @param swk_hkm_code �⏕�ȖڃR�[�h
	 */
	public void setSWK_HKM_CODE(String swk_hkm_code) {
		sWK_HKM_CODE = swk_hkm_code;
	}

	/**
	 * ���v���ׂP
	 * 
	 * @return ���v���ׂP
	 */
	public String getSWK_HM_1() {
		return sWK_HM_1;
	}

	/**
	 * ���v���ׂP
	 * 
	 * @param swk_hm_1 ���v���ׂP
	 */
	public void setSWK_HM_1(String swk_hm_1) {
		sWK_HM_1 = swk_hm_1;
	}

	/**
	 * ���v���ׂQ
	 * 
	 * @return ���v���ׂQ
	 */
	public String getSWK_HM_2() {
		return sWK_HM_2;
	}

	/**
	 * ���v���ׂQ
	 * 
	 * @param swk_hm_2 ���v���ׂQ
	 */
	public void setSWK_HM_2(String swk_hm_2) {
		sWK_HM_2 = swk_hm_2;
	}

	/**
	 * ���v���ׂR
	 * 
	 * @return ���v���ׂR
	 */
	public String getSWK_HM_3() {
		return sWK_HM_3;
	}

	/**
	 * ���v���ׂR
	 * 
	 * @param swk_hm_3 ���v���ׂR
	 */
	public void setSWK_HM_3(String swk_hm_3) {
		sWK_HM_3 = swk_hm_3;
	}

	/**
	 * ���͋��z
	 * 
	 * @return sWK_IN_KIN ���͋��z
	 */
	public BigDecimal getSWK_IN_KIN() {
		return sWK_IN_KIN;
	}

	/**
	 * ���͋��z
	 * 
	 * @param swk_in_kin ���͋��z
	 */
	public void setSWK_IN_KIN(BigDecimal swk_in_kin) {
		sWK_IN_KIN = swk_in_kin;
	}

	/**
	 * ���͏���Ŋz
	 * 
	 * @return ���͏���Ŋz
	 */
	public BigDecimal getSWK_IN_ZEI_KIN() {
		return sWK_IN_ZEI_KIN;
	}

	/**
	 * ���͏���Ŋz
	 * 
	 * @param swk_in_zei_kin ���͏���Ŋz
	 */
	public void setSWK_IN_ZEI_KIN(BigDecimal swk_in_zei_kin) {
		sWK_IN_ZEI_KIN = swk_in_zei_kin;
	}

	/**
	 * �v���к���
	 * 
	 * @return �v���к���
	 */
	public String getSWK_K_KAI_CODE() {
		return sWK_K_KAI_CODE;
	}

	/**
	 * �v���к���
	 * 
	 * @param swk_k_kai_code �v���к���
	 */
	public void setSWK_K_KAI_CODE(String swk_k_kai_code) {
		sWK_K_KAI_CODE = swk_k_kai_code;
	}

	/**
	 * �M�݋��z
	 * 
	 * @return �M�݋��z
	 */
	public BigDecimal getSWK_KIN() {
		return sWK_KIN;
	}

	/**
	 * �M�݋��z
	 * 
	 * @param swk_kin �M�݋��z
	 */
	public void setSWK_KIN(BigDecimal swk_kin) {
		sWK_KIN = swk_kin;
	}

	/**
	 * �ȖڃR�[�h
	 * 
	 * @return �ȖڃR�[�h
	 */
	public String getSWK_KMK_CODE() {
		return sWK_KMK_CODE;
	}

	/**
	 * �ȖڃR�[�h
	 * 
	 * @param swk_kmk_code �ȖڃR�[�h
	 */
	public void setSWK_KMK_CODE(String swk_kmk_code) {
		sWK_KMK_CODE = swk_kmk_code;
	}

	/**
	 * �Ǘ��P�R�[�h
	 * 
	 * @return �Ǘ��P�R�[�h
	 */
	public String getSWK_KNR_CODE_1() {
		return sWK_KNR_CODE_1;
	}

	/**
	 * �Ǘ��P�R�[�h
	 * 
	 * @param swk_knr_code_1 �Ǘ��P�R�[�h
	 */
	public void setSWK_KNR_CODE_1(String swk_knr_code_1) {
		sWK_KNR_CODE_1 = swk_knr_code_1;
	}

	/**
	 * �Ǘ��Q�R�[�h
	 * 
	 * @return �Ǘ��Q�R�[�h
	 */
	public String getSWK_KNR_CODE_2() {
		return sWK_KNR_CODE_2;
	}

	/**
	 * �Ǘ��Q�R�[�h
	 * 
	 * @param swk_knr_code_2 �Ǘ��Q�R�[�h
	 */
	public void setSWK_KNR_CODE_2(String swk_knr_code_2) {
		sWK_KNR_CODE_2 = swk_knr_code_2;
	}

	/**
	 * �Ǘ��R�R�[�h
	 * 
	 * @return �Ǘ��R�R�[�h
	 */
	public String getSWK_KNR_CODE_3() {
		return sWK_KNR_CODE_3;
	}

	/**
	 * �Ǘ��R�R�[�h
	 * 
	 * @param swk_knr_code_3 �Ǘ��R�R�[�h
	 */
	public void setSWK_KNR_CODE_3(String swk_knr_code_3) {
		sWK_KNR_CODE_3 = swk_knr_code_3;
	}

	/**
	 * �Ǘ��S�R�[�h
	 * 
	 * @return �Ǘ��S�R�[�h
	 */
	public String getSWK_KNR_CODE_4() {
		return sWK_KNR_CODE_4;
	}

	/**
	 * �Ǘ��S�R�[�h
	 * 
	 * @param swk_knr_code_4 �Ǘ��S�R�[�h
	 */
	public void setSWK_KNR_CODE_4(String swk_knr_code_4) {
		sWK_KNR_CODE_4 = swk_knr_code_4;
	}

	/**
	 * �Ǘ��T�R�[�h
	 * 
	 * @return �Ǘ��T�R�[�h
	 */
	public String getSWK_KNR_CODE_5() {
		return sWK_KNR_CODE_5;
	}

	/**
	 * �Ǘ��T�R�[�h
	 * 
	 * @param swk_knr_code_5 �Ǘ��T�R�[�h
	 */
	public void setSWK_KNR_CODE_5(String swk_knr_code_5) {
		sWK_KNR_CODE_5 = swk_knr_code_5;
	}

	/**
	 * �Ǘ��U�R�[�h
	 * 
	 * @return �Ǘ��U�R�[�h
	 */
	public String getSWK_KNR_CODE_6() {
		return sWK_KNR_CODE_6;
	}

	/**
	 * �Ǘ��U�R�[�h
	 * 
	 * @param swk_knr_code_6 �Ǘ��U�R�[�h
	 */
	public void setSWK_KNR_CODE_6(String swk_knr_code_6) {
		sWK_KNR_CODE_6 = swk_knr_code_6;
	}

	/**
	 * ���Z�敪
	 * 
	 * @return ���Z�敪
	 */
	public int getSWK_KSN_KBN() {
		return sWK_KSN_KBN;
	}

	/**
	 * ���Z�敪
	 * 
	 * @param swk_ksn_kbn ���Z�敪
	 */
	public void setSWK_KSN_KBN(int swk_ksn_kbn) {
		sWK_KSN_KBN = swk_ksn_kbn;
	}

	/**
	 * �؜ߔԍ�
	 * 
	 * @return �؜ߔԍ�
	 */
	public String getSWK_SEI_NO() {
		return sWK_SEI_NO;
	}

	/**
	 * �؜ߔԍ�
	 * 
	 * @param swk_sei_no �؜ߔԍ�
	 */
	public void setSWK_SEI_NO(String swk_sei_no) {
		sWK_SEI_NO = swk_sei_no;
	}

	/**
	 * �����R�[�h
	 * 
	 * @return �����R�[�h
	 */
	public String getSWK_TRI_CODE() {
		return sWK_TRI_CODE;
	}

	/**
	 * �����R�[�h
	 * 
	 * @param swk_tri_code �����R�[�h
	 */
	public void setSWK_TRI_CODE(String swk_tri_code) {
		sWK_TRI_CODE = swk_tri_code;
	}

	/**
	 * ��Њԕt�֓`�[�敪
	 * 
	 * @return ��Њԕt�֓`�[�敪
	 */
	public int getSWK_TUKE_KBN() {
		return sWK_TUKE_KBN;
	}

	/**
	 * ��Њԕt�֓`�[�敪
	 * 
	 * @param swk_tuke_kbn ��Њԕt�֓`�[�敪
	 */
	public void setSWK_TUKE_KBN(int swk_tuke_kbn) {
		sWK_TUKE_KBN = swk_tuke_kbn;
	}

	/**
	 * ����ȖڃR�[�h
	 * 
	 * @return ����ȖڃR�[�h
	 */
	public String getSWK_UKM_CODE() {
		return sWK_UKM_CODE;
	}

	/**
	 * ����ȖڃR�[�h
	 * 
	 * @param swk_ukm_code ����ȖڃR�[�h
	 */
	public void setSWK_UKM_CODE(String swk_ukm_code) {
		sWK_UKM_CODE = swk_ukm_code;
	}

	/**
	 * �X�V�敪
	 * 
	 * @return �X�V�敪
	 */
	public int getSWK_UPD_KBN() {
		return sWK_UPD_KBN;
	}

	/**
	 * �X�V�敪
	 * 
	 * @param swk_upd_kbn �X�V�敪
	 */
	public void setSWK_UPD_KBN(int swk_upd_kbn) {
		sWK_UPD_KBN = swk_upd_kbn;
	}

	/**
	 * ����ŃR�[�h
	 * 
	 * @return ����ŃR�[�h
	 */
	public String getSWK_ZEI_CODE() {
		return sWK_ZEI_CODE;
	}

	/**
	 * ����ŃR�[�h
	 * 
	 * @param swk_zei_code ����ŃR�[�h
	 */
	public void setSWK_ZEI_CODE(String swk_zei_code) {
		sWK_ZEI_CODE = swk_zei_code;
	}

	/**
	 * �ŋ敪
	 * 
	 * @return �ŋ敪
	 */
	public int getSWK_ZEI_KBN() {
		return sWK_ZEI_KBN;
	}

	/**
	 * �ŋ敪
	 * 
	 * @param swk_zei_kbn �ŋ敪
	 */
	public void setSWK_ZEI_KBN(int swk_zei_kbn) {
		sWK_ZEI_KBN = swk_zei_kbn;
	}

	/**
	 * �ŋ敪
	 * 
	 * @param type �ŋ敪
	 */
	public void setTaxCalcType(TaxCalcType type) {
		this.sWK_ZEI_KBN = type.value;
	}

	/**
	 * ����Ŋz
	 * 
	 * @return ����Ŋz
	 */
	public BigDecimal getSWK_ZEI_KIN() {
		return sWK_ZEI_KIN;
	}

	/**
	 * ����Ŋz
	 * 
	 * @param swk_zei_kin ����Ŋz
	 */
	public void setSWK_ZEI_KIN(BigDecimal swk_zei_kin) {
		sWK_ZEI_KIN = swk_zei_kin;
	}

	/** ��t����R�[�h */
	protected String sWK_UKE_DEP_CODE;

	/** �`�[�E�v�R�[�h */
	protected String sWK_TEK_CODE;

	/** �`�[�E�v */
	protected String sWK_TEK;

	/** ���F�� */
	protected String sWK_SYO_EMP_CODE;

	/** ���F�� */
	protected String sWK_SYO_DATE;

	/** �˗��� */
	protected String sWK_IRAI_EMP_CODE;

	/** �˗�����R�[�h */
	protected String sWK_IRAI_DEP_CODE;

	/** �˗��� */
	protected String sWK_IRAI_DATE;

	/** ���ы敪 */
	protected String sWK_SYS_KBN;

	/** �`�[��� */
	protected String sWK_DEN_SYU;

	// AP�s����--
	/** �x���敪 */
	protected String sWK_SIHA_KBN;

	/** �x���� */
	protected String sWK_SIHA_DATE;

	/** �x�����@ */
	protected String sWK_HOH_CODE;

	/** �ۗ��敪 */
	protected int sWK_HORYU_KBN;

	/** ������������ */
	protected String sWK_TJK_CODE;

	/** ��s�������� */
	protected String sWK_CBK_CODE;

	// AR�s����--
	/** �����\��� */
	protected String sWK_AR_DATE;

	/** ����� */
	protected String sWK_UKE_DATE;

	/**
	 * �`�[���
	 * 
	 * @return �`�[���
	 */
	public String getSWK_DEN_SYU() {
		return sWK_DEN_SYU;
	}

	/**
	 * �`�[���
	 * 
	 * @param swk_den_syu �`�[���
	 */
	public void setSWK_DEN_SYU(String swk_den_syu) {
		sWK_DEN_SYU = swk_den_syu;
	}

	/**
	 * �˗���
	 * 
	 * @return �˗���
	 */
	public String getSWK_IRAI_DATE() {
		return sWK_IRAI_DATE;
	}

	/**
	 * �˗���
	 * 
	 * @param swk_irai_date �˗���
	 */
	public void setSWK_IRAI_DATE(String swk_irai_date) {
		sWK_IRAI_DATE = swk_irai_date;
	}

	/**
	 * �˗�����R�[�h
	 * 
	 * @return �˗�����R�[�h
	 */
	public String getSWK_IRAI_DEP_CODE() {
		return sWK_IRAI_DEP_CODE;
	}

	/**
	 * �˗�����R�[�h
	 * 
	 * @param swk_irai_dep_code �˗�����R�[�h
	 */
	public void setSWK_IRAI_DEP_CODE(String swk_irai_dep_code) {
		sWK_IRAI_DEP_CODE = swk_irai_dep_code;
	}

	/**
	 * �˗���
	 * 
	 * @return �˗���
	 */
	public String getSWK_IRAI_EMP_CODE() {
		return sWK_IRAI_EMP_CODE;
	}

	/**
	 * �˗���
	 * 
	 * @param swk_irai_emp_code �˗���
	 */
	public void setSWK_IRAI_EMP_CODE(String swk_irai_emp_code) {
		sWK_IRAI_EMP_CODE = swk_irai_emp_code;
	}

	/**
	 * ���F��
	 * 
	 * @return ���F��
	 */
	public String getSWK_SYO_DATE() {
		return sWK_SYO_DATE;
	}

	/**
	 * ���F��
	 * 
	 * @param swk_syo_date ���F��
	 */
	public void setSWK_SYO_DATE(String swk_syo_date) {
		sWK_SYO_DATE = swk_syo_date;
	}

	/**
	 * ���F��
	 * 
	 * @return ���F��
	 */
	public String getSWK_SYO_EMP_CODE() {
		return sWK_SYO_EMP_CODE;
	}

	/**
	 * ���F��
	 * 
	 * @param swk_syo_emp_code ���F��
	 */
	public void setSWK_SYO_EMP_CODE(String swk_syo_emp_code) {
		sWK_SYO_EMP_CODE = swk_syo_emp_code;
	}

	/**
	 * ���ы敪
	 * 
	 * @return ���ы敪
	 */
	public String getSWK_SYS_KBN() {
		return sWK_SYS_KBN;
	}

	/**
	 * ���ы敪
	 * 
	 * @param swk_sys_kbn ���ы敪
	 */
	public void setSWK_SYS_KBN(String swk_sys_kbn) {
		sWK_SYS_KBN = swk_sys_kbn;
	}

	/**
	 * �`�[�E�v
	 * 
	 * @return �`�[�E�v
	 */
	public String getSWK_TEK() {
		return sWK_TEK;
	}

	/**
	 * �`�[�E�v
	 * 
	 * @param swk_tek �`�[�E�v
	 */
	public void setSWK_TEK(String swk_tek) {
		sWK_TEK = swk_tek;
	}

	/**
	 * �`�[�E�v�R�[�h
	 * 
	 * @return �`�[�E�v�R�[�h
	 */
	public String getSWK_TEK_CODE() {
		return sWK_TEK_CODE;
	}

	/**
	 * �`�[�E�v�R�[�h
	 * 
	 * @param swk_tek_code �`�[�E�v�R�[�h
	 */
	public void setSWK_TEK_CODE(String swk_tek_code) {
		sWK_TEK_CODE = swk_tek_code;
	}

	/**
	 * ��t����R�[�h
	 * 
	 * @return ��t����R�[�h
	 */
	public String getSWK_UKE_DEP_CODE() {
		return sWK_UKE_DEP_CODE;
	}

	/**
	 * ��t����R�[�h
	 * 
	 * @param swk_uke_dep_code ��t����R�[�h
	 */
	public void setSWK_UKE_DEP_CODE(String swk_uke_dep_code) {
		sWK_UKE_DEP_CODE = swk_uke_dep_code;
	}

	/**
	 * �����\���
	 * 
	 * @return �����\���
	 */
	public String getSWK_AR_DATE() {
		return sWK_AR_DATE;
	}

	/**
	 * �����\���
	 * 
	 * @param swk_ar_date �����\���
	 */
	public void setSWK_AR_DATE(String swk_ar_date) {
		sWK_AR_DATE = swk_ar_date;
	}

	/**
	 * ��s��������
	 * 
	 * @return ��s��������
	 */
	public String getSWK_CBK_CODE() {
		return sWK_CBK_CODE;
	}

	/**
	 * ��s��������
	 * 
	 * @param swk_cbk_code ��s��������
	 */
	public void setSWK_CBK_CODE(String swk_cbk_code) {
		sWK_CBK_CODE = swk_cbk_code;
	}

	/**
	 * �x�����@
	 * 
	 * @return �x�����@
	 */
	public String getSWK_HOH_CODE() {
		return sWK_HOH_CODE;
	}

	/**
	 * �x�����@
	 * 
	 * @param swk_hoh_code �x�����@
	 */
	public void setSWK_HOH_CODE(String swk_hoh_code) {
		sWK_HOH_CODE = swk_hoh_code;
	}

	/**
	 * �ۗ��敪
	 * 
	 * @return �ۗ��敪
	 */
	public int getSWK_HORYU_KBN() {
		return sWK_HORYU_KBN;
	}

	/**
	 * �ۗ��敪
	 * 
	 * @param swk_horyu_kbn �ۗ��敪
	 */
	public void setSWK_HORYU_KBN(int swk_horyu_kbn) {
		sWK_HORYU_KBN = swk_horyu_kbn;
	}

	/**
	 * �x����
	 * 
	 * @return �x����
	 */
	public String getSWK_SIHA_DATE() {
		return sWK_SIHA_DATE;
	}

	/**
	 * �x����
	 * 
	 * @param swk_siha_date �x����
	 */
	public void setSWK_SIHA_DATE(String swk_siha_date) {
		sWK_SIHA_DATE = swk_siha_date;
	}

	/**
	 * �x���敪
	 * 
	 * @return �x���敪
	 */
	public String getSWK_SIHA_KBN() {
		return sWK_SIHA_KBN;
	}

	/**
	 * �x���敪
	 * 
	 * @param swk_siha_kbn �x���敪
	 */
	public void setSWK_SIHA_KBN(String swk_siha_kbn) {
		sWK_SIHA_KBN = swk_siha_kbn;
	}

	/**
	 * ������������
	 * 
	 * @return ������������
	 */
	public String getSWK_TJK_CODE() {
		return sWK_TJK_CODE;
	}

	/**
	 * ������������
	 * 
	 * @param swk_tjk_code ������������
	 */
	public void setSWK_TJK_CODE(String swk_tjk_code) {
		sWK_TJK_CODE = swk_tjk_code;
	}

	/**
	 * �ۗ��敪
	 * 
	 * @return true:�ۗ�
	 */
	public boolean isSuspended() {
		return BooleanUtil.toBoolean(sWK_HORYU_KBN);
	}

	/**
	 * �ۗ��敪
	 * 
	 * @param b true:�ۗ�
	 */
	public void setSuspended(boolean b) {
		sWK_HORYU_KBN = BooleanUtil.toInt(b);
	}

	/**
	 * �x���敪
	 * 
	 * @return �x���敪
	 */
	public PaymentDateType getPaymentType() {
		return PaymentDateType.getPaymentDateType(sWK_SIHA_KBN);
	}

	/**
	 * �x���敪
	 * 
	 * @param paymentDateType �x���敪
	 */
	public void setPaymentType(PaymentDateType paymentDateType) {
		sWK_SIHA_KBN = paymentDateType.value;
	}

	/**
	 * �ʉ� �����_����
	 * 
	 * @return �ʉ� �����_����
	 */
	public int getCUR_DEC_KETA() {
		return cUR_DEC_KETA;
	}

	/**
	 * �ʉ� �����_����
	 * 
	 * @param cur_dec_keta �ʉ� �����_����
	 */
	public void setCUR_DEC_KETA(int cur_dec_keta) {
		cUR_DEC_KETA = cur_dec_keta;
	}

	/**
	 * �����
	 * 
	 * @return �����
	 */
	public String getSWK_UKE_DATE() {
		return sWK_UKE_DATE;
	}

	/**
	 * �����
	 * 
	 * @param swk_uke_date �����
	 */
	public void setSWK_UKE_DATE(String swk_uke_date) {
		sWK_UKE_DATE = swk_uke_date;
	}

}
