package jp.co.ais.trans.master.entity;

import java.math.BigDecimal;
import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * ���`�[�����G���e�B�e�B
 */
public class ApSlipUnit extends TransferBase {

	/** ��ЃR�[�h */
	private String kAI_CODE = "";

	/** �`�[���t */
	private Date dEN_DATE;

	/** �`�[�ԍ� */
	private String dEN_NO = "";

	/** �ȖڃR�[�h */
	private String hDR_KMK_CODE;

	/** �⏕�ȖڃR�[�h */
	private String hDR_HKM_CODE;

	/** ����ȖڃR�[�h */
	private String hDR_UKM_CODE;

	/** �v�㕔��R�[�h */
	private String hDR_DEP_CODE;

	/** �����R�[�h */
	private String hDR_TRI_CODE;

	/** �Ј��R�[�h */
	private String hDR_EMP_CODE;

	/** �x���敪 */
	private String hDR_SIHA_KBN;

	/** �x���� */
	private Date hDR_SIHA_DATE;

	/** �x�����@�R�[�h */
	private String hDR_HOH_CODE;

	/** �ۗ��敪 */
	private int hDR_HORYU_KBN;

	/** �������z */
	private BigDecimal hDR_KARI_KIN;

	/** �o����z */
	private BigDecimal hDR_KEIHI_KIN;

	/** �x�����z */
	private BigDecimal hDR_SIHA_KIN;

	/** �����\���`�[�ԍ� */
	private String hDR_KARI_DR_DEN_NO;

	/** �������Z�`�[�ԍ� */
	private String hDR_KARI_CR_DEN_NO;

	/** �x�����ϋ敪 */
	private int hDR_KESAI_KBN;

	/** �؜ߔԍ� */
	private String sEI_NO;

	/** �f�[�^�敪 */
	private String dATA_KBN = "";

	/** ��t����R�[�h */
	private String hDR_UKE_DEP_CODE;

	/** �`�[�E�v�R�[�h */
	private String hDR_TEK_CODE;

	/** �`�[�E�v */
	private String hDR_TEK = "";

	/** �����O�`�[�ԍ� */
	private String hDR_BEFORE_DEN_NO;

	/** �X�V�敪 */
	private int uPD_KBN;

	/** ���F�҃R�[�h */
	private String hDR_SYO_EMP_CODE;

	/** ���F�� */
	private Date hDR_SYO_DATE;

	/** �˗��� �Ј��R�[�h */
	private String hDR_IRAI_EMP_CODE;

	/** �˗�����R�[�h */
	private String hDR_IRAI_DEP_CODE;

	/** �˗��� */
	private Date hDR_IRAI_DATE;

	/** �����v�㕔��R�[�h */
	private String hDR_KARI_DEP_CODE;

	/** �r���t���O */
	private int hDR_SHR_KBN;

	/** ���Z�敪 */
	private int KSN_KBN;

	/** ���[�U�[ID */
	private String uSR_ID;

	/** �v���O����ID */
	private String pRG_ID;

	/** ���������R�[�h */
	private String hDR_TJK_CODE;

	/** �ʉ݃R�[�h */
	private String hDR_CUR_CODE;

	/** ���[�g */
	private BigDecimal hDR_CUR_RATE;

	/** ���͉������z */
	private BigDecimal hDR_IN_KARI_KIN;

	/** ���͌o����z */
	private BigDecimal hDR_IN_KEIHI_KIN;

	/** ���͎x�����z */
	private BigDecimal hDR_IN_SIHA_KIN;

	/** �V�X�e���敪 */
	private String hDR_SYS_KBN;

	/** �`�[��� */
	private String dEN_SYU;

	/** ��Њԕt�֓`�[�敪 */
	private int hDR_TUKE_KBN;

	/** �C���� */
	private Integer hDR_UPD_CNT;

	/** �������ʉ݃R�[�h */
	private String hDR_INV_CUR_CODE;

	/** �������ʉ݃��[�g */
	private BigDecimal hDR_INV_CUR_RATE;

	/** �������ʉ݋��z */
	private BigDecimal hDR_INV_KIN;

	/** ��s�����R�[�h */
	private String hDR_CBK_CODE;

	/** ���Z�\��� */
	private Date hDR_SSY_DATE;

	/** ��tNO */
	private String hDR_UTK_NO;

	/** �����ʉ݃R�[�h */
	private String hDR_KARI_CUR_CODE;

	/** �����ʉ݃��[�g */
	private BigDecimal hDR_KARI_CUR_RATE;

	// �������疾��

	/** �s�ԍ� */
	private int sWK_GYO_NO;

	/** �N�x */
	private int sWK_NENDO;

	/** ���x */
	private int sWK_TUKIDO;

	/** �ݎ؋敪 */
	private int sWK_DC_KBN;

	/** �ȖڃR�[�h */
	private String sWK_KMK_CODE = "";

	/** �⏕�ȖڃR�[�h */
	private String sWK_HKM_CODE;

	/** ����ȖڃR�[�h */
	private String sWK_UKM_CODE;

	/** �v�㕔��R�[�h */
	private String sWK_DEP_CODE = "";

	/** �ŋ敪 */
	private int sWK_ZEI_KBN;

	/** �M�݋��z */
	private BigDecimal sWK_KIN;

	/** ����ŋ��z */
	private BigDecimal sWK_ZEI_KIN;

	/** ����ŃR�[�h */
	private String sWK_ZEI_CODE;

	/** ����Ŗ��� */
	private String sWK_ZEI_NAME;

	/** �ŗ� */
	private BigDecimal sWK_ZEI_RATE;

	/** �s�E�v�R�[�h */
	private String sWK_GYO_TEK_CODE;

	/** �s�E�v */
	private String sWK_GYO_TEK;

	/** �����R�[�h */
	private String sWK_TRI_CODE;

	/** �Ј��R�[�h */
	private String sWK_EMP_CODE;

	/** �Ǘ�1�R�[�h */
	private String sWK_KNR_CODE_1;

	/** �Ǘ�2�R�[�h */
	private String sWK_KNR_CODE_2;

	/** �Ǘ�3�R�[�h */
	private String sWK_KNR_CODE_3;

	/** �Ǘ�4�R�[�h */
	private String sWK_KNR_CODE_4;

	/** �Ǘ�5�R�[�h */
	private String sWK_KNR_CODE_5;

	/** �Ǘ�6�R�[�h */
	private String sWK_KNR_CODE_6;

	/** ���v����1 */
	private String sWK_HM_1;

	/** ���v����2 */
	private String sWK_HM_2;

	/** ���v����3 */
	private String sWK_HM_3;

	/** �����d��敪 */
	private int sWK_AUTO_KBN;

	/** ����ȖڃR�[�h */
	private String sWK_AT_KMK_CODE;

	/** ����⏕�ȖڃR�[�h */
	private String sWK_AT_HKM_CODE;

	/** �������ȖڃR�[�h */
	private String sWK_AT_UKM_CODE;

	/** ���蕔��R�[�h */
	private String sWK_AT_DEP_CODE;

	/** �v���ЃR�[�h */
	private String sWK_K_KAI_CODE;

	/** �v���Ж��� */
	private String sWK_K_KAI_NAME;

	/** �ʉ݃R�[�h */
	private String sWK_CUR_CODE;

	/** ���[�g */
	private BigDecimal sWK_CUR_RATE;

	/** ���͋��z */
	private BigDecimal sWK_IN_KIN;

	/** ��Њԕt�֓`�[�敪 */
	private int sWK_TUKE_KBN;

	/** ���͏���Ŋz */
	private BigDecimal sWK_IN_ZEI_KIN;

	/** �����敪 */
	private int sWK_KESI_KBN;

	/** �����`�[���t */
	private Date sWK_KESI_DATE;

	/** �����`�[�ԍ� */
	private String sWK_KESI_DEN_NO;

	/** ���E�� */
	private Date sWK_SOUSAI_DATE;

	/** ���E�`�[�ԍ� */
	private String sWK_SOUSAI_DEN_NO;

	/** ������ */
	private Date sWK_HAS_DATE;

	// �������猋�� �w�b�_�[�֘A
	/** �Ȗږ��� */
	private String hDR_KMK_NAME;

	/** �⏕�Ȗږ��� */
	private String hDR_HKM_NAME;

	/** ����Ȗږ��� */
	private String hDR_UKM_NAME;

	/** �Ȗڗ��� */
	private String hDR_KMK_NAME_S;

	/** �⏕�Ȗڗ��� */
	private String hDR_HKM_NAME_S;

	/** ����Ȗڗ��� */
	private String hDR_UKM_NAME_S;

	/** �x�����@���� */
	private String hDR_HOH_NAME;

	/** �x�����@�����R�[�h */
	private String hDR_HOH_NAI_CODE;

	/** ��Ж��� */
	private String kAI_NAME;

	/** ��З��� */
	private String kAI_NAME_S;

	/** ��ЃR�[�h ��ʉ� */
	private String bASE_CUR_CODE;

	/** ��ЃR�[�h ��ʉ� �����_���� */
	private String bASE_CUR_DEC_KETA;

	/** �˗����喼�� */
	private String hDR_IRAI_DEP_NAME;

	/** �˗����嗪�� */
	private String hDR_IRAI_DEP_NAME_S;

	/** �˗��� �Ј����� */
	private String hDR_IRAI_EMP_NAME;

	/** �˗��� �Ј����� */
	private String hDR_IRAI_EMP_NAME_S;

	/** �Ј����� */
	private String hDR_EMP_NAME;

	/** �Ј����� */
	private String hDR_EMP_NAME_S;

	/** ��s���� �a����� */
	private String hDR_CBK_YKN_KBN;

	/** ��s�����R�[�h ��s�� */
	private String hDR_CBK_BANK_NAME;

	/** ��s�����R�[�h ��s�R�[�h */
	private String HDR_CBK_BNK_CODE;

	/** ��s�����R�[�h �x�X�� */
	private String hDR_CBK_BANK_STN_NAME;

	/** ��s�����R�[�h �x�X�R�[�h */
	private String HDR_CBK_STN_CODE;

	/** �`�[��� �������� */
	private String dEN_SYU_NAME_K;

	/** �Ȗږ��́i���[�o�͗p�j */
	private String cMP_KMK_NAME;

	/** �⏕�Ȗږ��́i���[�o�͗p�j */
	private String cMP_HKM_NAME;

	/** ����Ȗږ��́i���[�o�͗p�j */
	private String cMP_UKM_NAME;

	/** �v�㕔�喼�� */
	private String hDR_DEP_NAME;

	/** �v�㕔�嗪�� */
	private String hDR_DEP_NAME_S;

	/** �ʉ݃R�[�h �����_����(�w�b�_�[) */
	private String hDR_CUR_DEC_KETA;

	// �������猋�� �W���[�i���֘A

	/** �Ȗږ��� */
	private String sWK_KMK_NAME;

	/** �⏕�Ȗږ��� */
	private String sWK_HKM_NAME;

	/** ����Ȗږ��� */
	private String sWK_UKM_NAME;

	/** �Ȗڗ��� */
	private String sWK_KMK_NAME_S;

	/** �⏕�Ȗڗ��� */
	private String sWK_HKM_NAME_S;

	/** ����Ȗڗ��� */
	private String sWK_UKM_NAME_S;

	/** ����於�� */
	private String sWK_TRI_NAME;

	/** �Ј����� */
	private String sWK_EMP_NAME;

	/** ����旪�� */
	private String sWK_TRI_NAME_S;

	/** �Ј����� */
	private String sWK_EMP_NAME_S;

	/** �Ǘ�1���� */
	private String sWK_KNR_NAME_1;

	/** �Ǘ�2���� */
	private String sWK_KNR_NAME_2;

	/** �Ǘ�3���� */
	private String sWK_KNR_NAME_3;

	/** �Ǘ�4���� */
	private String sWK_KNR_NAME_4;

	/** �Ǘ�5���� */
	private String sWK_KNR_NAME_5;

	/** �Ǘ�6���� */
	private String sWK_KNR_NAME_6;

	/** �Ǘ�1���� */
	private String sWK_KNR_NAME_S_1;

	/** �Ǘ�2���� */
	private String sWK_KNR_NAME_S_2;

	/** �Ǘ�3���� */
	private String sWK_KNR_NAME_S_3;

	/** �Ǘ�4���� */
	private String sWK_KNR_NAME_S_4;

	/** �Ǘ�5���� */
	private String sWK_KNR_NAME_S_5;

	/** �Ǘ�6���� */
	private String sWK_KNR_NAME_S_6;

	/** �ʉ݃R�[�h �����_���� */
	private String sWK_CUR_DEC_KETA;

	/** ���� �v�㕔�嗪�� */
	private String sWK_DEP_NAME_S;

	// ��������ǉ�����

	/** ���Z�ω������͋��z */
	private BigDecimal hDR_SSY_KARI_IN_KIN;

	/** ���Z�ω����M�݋��z */
	private BigDecimal hDR_SSY_KARI_KIN;

	/** ������ */
	private Date tMP_PAY_DATE;

	/**
	 * ��ʉ݂��擾����
	 * 
	 * @return ��ʉ�
	 */
	public String getBASE_CUR_CODE() {
		return bASE_CUR_CODE;
	}

	/**
	 * ��ʉ݂�ݒ肷��
	 * 
	 * @param base_cur_code
	 */
	public void setBASE_CUR_CODE(String base_cur_code) {
		bASE_CUR_CODE = base_cur_code;
	}

	/**
	 * ��ʉ݂̏����_�������擾����
	 * 
	 * @return ��ʉ݂̏����_����
	 */
	public String getBASE_CUR_DEC_KETA() {
		return bASE_CUR_DEC_KETA;
	}

	/**
	 * ��ʉ݂̏����_������ݒ肷��
	 * 
	 * @param base_cur_dec_keta
	 */
	public void setBASE_CUR_DEC_KETA(String base_cur_dec_keta) {
		bASE_CUR_DEC_KETA = base_cur_dec_keta;
	}

	/**
	 * �f�[�^�敪���擾����
	 * 
	 * @return �f�[�^�敪
	 */
	public String getDATA_KBN() {
		return dATA_KBN;
	}

	/**
	 * �f�[�^�敪��ݒ肷��
	 * 
	 * @param data_kbn
	 */
	public void setDATA_KBN(String data_kbn) {
		dATA_KBN = data_kbn;
	}

	/**
	 * �`�[���t���擾����
	 * 
	 * @return �`�[���t
	 */
	public Date getDEN_DATE() {
		return dEN_DATE;
	}

	/**
	 * �`�[���t��ݒ肷��
	 * 
	 * @param den_date
	 */
	public void setDEN_DATE(Date den_date) {
		dEN_DATE = den_date;
	}

	/**
	 * �`�[�ԍ����擾����
	 * 
	 * @return �`�[�ԍ�
	 */
	public String getDEN_NO() {
		return dEN_NO;
	}

	/**
	 * �`�[�ԍ���ݒ肷��
	 * 
	 * @param den_no
	 */
	public void setDEN_NO(String den_no) {
		dEN_NO = den_no;
	}

	/**
	 * �`�[��ʂ��擾����
	 * 
	 * @return �`�[���
	 */
	public String getDEN_SYU() {
		return dEN_SYU;
	}

	/**
	 * �`�[��ʂ�ݒ肷��
	 * 
	 * @param den_syu
	 */
	public void setDEN_SYU(String den_syu) {
		dEN_SYU = den_syu;
	}

	/**
	 * �����O�`�[�ԍ� ���擾����
	 * 
	 * @return �����O�`�[�ԍ�
	 */
	public String getHDR_BEFORE_DEN_NO() {
		return hDR_BEFORE_DEN_NO;
	}

	/**
	 * �����O�`�[�ԍ���ݒ肷��
	 * 
	 * @param hdr_before_den_no
	 */
	public void setHDR_BEFORE_DEN_NO(String hdr_before_den_no) {
		hDR_BEFORE_DEN_NO = hdr_before_den_no;
	}

	/**
	 * ��s�����R�[�h ��s�����擾����
	 * 
	 * @return ��s�����R�[�h ��s��
	 */
	public String getHDR_CBK_BANK_NAME() {
		return hDR_CBK_BANK_NAME;
	}

	/**
	 * ��s�����R�[�h ��s����ݒ肷��
	 * 
	 * @param hdr_cbk_bank_name
	 */
	public void setHDR_CBK_BANK_NAME(String hdr_cbk_bank_name) {
		hDR_CBK_BANK_NAME = hdr_cbk_bank_name;
	}

	/**
	 * ��s�����R�[�h �x�X�����擾����
	 * 
	 * @return ��s�����R�[�h �x�X��
	 */
	public String getHDR_CBK_BANK_STN_NAME() {
		return hDR_CBK_BANK_STN_NAME;
	}

	/**
	 * ��s�����R�[�h �x�X����ݒ肷��
	 * 
	 * @param hdr_cbk_bank_stn_name
	 */
	public void setHDR_CBK_BANK_STN_NAME(String hdr_cbk_bank_stn_name) {
		hDR_CBK_BANK_STN_NAME = hdr_cbk_bank_stn_name;
	}

	/**
	 * ��s�����R�[�h���擾����
	 * 
	 * @return ��s�����R�[�h
	 */
	public String getHDR_CBK_CODE() {
		return hDR_CBK_CODE;
	}

	/**
	 * ��s�����R�[�h��ݒ肷��
	 * 
	 * @param hdr_cbk_code
	 */
	public void setHDR_CBK_CODE(String hdr_cbk_code) {
		hDR_CBK_CODE = hdr_cbk_code;
	}

	/**
	 * ��s���� �a����ڂ��擾����
	 * 
	 * @return ��s���� �a�����
	 */
	public String getHDR_CBK_YKN_KBN() {
		return hDR_CBK_YKN_KBN;
	}

	/**
	 * ��s���� �a����ڂ�ݒ肷��
	 * 
	 * @param hdr_cbk_ykn_kbn
	 */
	public void setHDR_CBK_YKN_KBN(String hdr_cbk_ykn_kbn) {
		hDR_CBK_YKN_KBN = hdr_cbk_ykn_kbn;
	}

	/**
	 * �ʉ݃R�[�h���擾����
	 * 
	 * @return �ʉ݃R�[�h
	 */
	public String getHDR_CUR_CODE() {
		return hDR_CUR_CODE;
	}

	/**
	 * �ʉ݃R�[�h��ݒ肷��
	 * 
	 * @param hdr_cur_code
	 */
	public void setHDR_CUR_CODE(String hdr_cur_code) {
		hDR_CUR_CODE = hdr_cur_code;
	}

	/**
	 * ���[�g���擾����
	 * 
	 * @return ���[�g
	 */
	public BigDecimal getHDR_CUR_RATE() {
		return hDR_CUR_RATE;
	}

	/**
	 * ���[�g��ݒ肷��
	 * 
	 * @param hdr_cur_rate
	 */
	public void setHDR_CUR_RATE(BigDecimal hdr_cur_rate) {
		hDR_CUR_RATE = hdr_cur_rate;
	}

	/**
	 * �v�㕔��R�[�h���擾����
	 * 
	 * @return �v�㕔��R�[�h
	 */
	public String getHDR_DEP_CODE() {
		return hDR_DEP_CODE;
	}

	/**
	 * �v�㕔��R�[�h��ݒ肷��
	 * 
	 * @param hdr_dep_code
	 */
	public void setHDR_DEP_CODE(String hdr_dep_code) {
		hDR_DEP_CODE = hdr_dep_code;
	}

	/**
	 * �Ј��R�[�h���擾����
	 * 
	 * @return �Ј��R�[�h
	 */
	public String getHDR_EMP_CODE() {
		return hDR_EMP_CODE;
	}

	/**
	 * �Ј��R�[�h��ݒ肷��
	 * 
	 * @param hdr_emp_code
	 */
	public void setHDR_EMP_CODE(String hdr_emp_code) {
		hDR_EMP_CODE = hdr_emp_code;
	}

	/**
	 * �⏕�ȖڃR�[�h���擾����
	 * 
	 * @return �⏕�ȖڃR�[�h
	 */
	public String getHDR_HKM_CODE() {
		return hDR_HKM_CODE;
	}

	/**
	 * �⏕�ȖڃR�[�h��ݒ肷��
	 * 
	 * @param hdr_hkm_code
	 */
	public void setHDR_HKM_CODE(String hdr_hkm_code) {
		hDR_HKM_CODE = hdr_hkm_code;
	}

	/**
	 * �⏕�Ȗږ��̂��擾����
	 * 
	 * @return �⏕�Ȗږ���
	 */
	public String getHDR_HKM_NAME() {
		return hDR_HKM_NAME;
	}

	/**
	 * �⏕�Ȗږ��̂�ݒ肷��
	 * 
	 * @param hdr_hkm_name
	 */
	public void setHDR_HKM_NAME(String hdr_hkm_name) {
		hDR_HKM_NAME = hdr_hkm_name;
	}

	/**
	 * �⏕�Ȗڗ��̂��擾����
	 * 
	 * @return �⏕�Ȗڗ���
	 */
	public String getHDR_HKM_NAME_S() {
		return hDR_HKM_NAME_S;
	}

	/**
	 * �⏕�Ȗڗ��̂�ݒ肷��
	 * 
	 * @param hdr_hkm_name_s
	 */
	public void setHDR_HKM_NAME_S(String hdr_hkm_name_s) {
		hDR_HKM_NAME_S = hdr_hkm_name_s;
	}

	/**
	 * �x�����@�R�[�h���擾����
	 * 
	 * @return �x�����@�R�[�h
	 */
	public String getHDR_HOH_CODE() {
		return hDR_HOH_CODE;
	}

	/**
	 * �x�����@�R�[�h��ݒ肷��
	 * 
	 * @param hdr_hoh_code
	 */
	public void setHDR_HOH_CODE(String hdr_hoh_code) {
		hDR_HOH_CODE = hdr_hoh_code;
	}

	/**
	 * �x�����@�����R�[�h���擾����
	 * 
	 * @return �x�����@�����R�[�h
	 */
	public String getHDR_HOH_NAI_CODE() {
		return hDR_HOH_NAI_CODE;
	}

	/**
	 * �x�����@�����R�[�h��ݒ肷��
	 * 
	 * @param hdr_hoh_nai_code
	 */
	public void setHDR_HOH_NAI_CODE(String hdr_hoh_nai_code) {
		hDR_HOH_NAI_CODE = hdr_hoh_nai_code;
	}

	/**
	 * �x�����@���̂��擾����
	 * 
	 * @return �x�����@����
	 */
	public String getHDR_HOH_NAME() {
		return hDR_HOH_NAME;
	}

	/**
	 * �x�����@���̂�ݒ肷��
	 * 
	 * @param hdr_hoh_name
	 */
	public void setHDR_HOH_NAME(String hdr_hoh_name) {
		hDR_HOH_NAME = hdr_hoh_name;
	}

	/**
	 * �ۗ��敪���擾����
	 * 
	 * @return �ۗ��敪
	 */
	public int getHDR_HORYU_KBN() {
		return hDR_HORYU_KBN;
	}

	/**
	 * �ۗ��敪��ݒ肷��
	 * 
	 * @param hdr_horyu_kbn
	 */
	public void setHDR_HORYU_KBN(int hdr_horyu_kbn) {
		hDR_HORYU_KBN = hdr_horyu_kbn;
	}

	/**
	 * ���͉������z���擾����
	 * 
	 * @return ���͉������z
	 */
	public BigDecimal getHDR_IN_KARI_KIN() {
		return hDR_IN_KARI_KIN;
	}

	/**
	 * ���͉������z��ݒ肷��
	 * 
	 * @param hdr_in_kari_kin
	 */
	public void setHDR_IN_KARI_KIN(BigDecimal hdr_in_kari_kin) {
		hDR_IN_KARI_KIN = hdr_in_kari_kin;
	}

	/**
	 * ���͌o����z���擾����
	 * 
	 * @return ���͌o����z
	 */
	public BigDecimal getHDR_IN_KEIHI_KIN() {
		return hDR_IN_KEIHI_KIN;
	}

	/**
	 * ���͌o����z��ݒ肷��
	 * 
	 * @param hdr_in_keihi_kin
	 */
	public void setHDR_IN_KEIHI_KIN(BigDecimal hdr_in_keihi_kin) {
		hDR_IN_KEIHI_KIN = hdr_in_keihi_kin;
	}

	/**
	 * ���͎x�����z���擾����
	 * 
	 * @return ���͎x�����z
	 */
	public BigDecimal getHDR_IN_SIHA_KIN() {
		return hDR_IN_SIHA_KIN;
	}

	/**
	 * ���͎x�����z��ݒ肷��
	 * 
	 * @param hdr_in_siha_kin
	 */
	public void setHDR_IN_SIHA_KIN(BigDecimal hdr_in_siha_kin) {
		hDR_IN_SIHA_KIN = hdr_in_siha_kin;
	}

	/**
	 * �������ʉ݃R�[�h���擾����
	 * 
	 * @return �������ʉ݃R�[�h
	 */
	public String getHDR_INV_CUR_CODE() {
		return hDR_INV_CUR_CODE;
	}

	/**
	 * �������ʉ݃R�[�h��ݒ肷��
	 * 
	 * @param hdr_inv_cur_code
	 */
	public void setHDR_INV_CUR_CODE(String hdr_inv_cur_code) {
		hDR_INV_CUR_CODE = hdr_inv_cur_code;
	}

	/**
	 * �������ʉ݃��[�g���擾����
	 * 
	 * @return �������ʉ݃��[�g
	 */
	public BigDecimal getHDR_INV_CUR_RATE() {
		return hDR_INV_CUR_RATE;
	}

	/**
	 * �������ʉ݃��[�g��ݒ肷��
	 * 
	 * @param hdr_inv_cur_rate
	 */
	public void setHDR_INV_CUR_RATE(BigDecimal hdr_inv_cur_rate) {
		hDR_INV_CUR_RATE = hdr_inv_cur_rate;
	}

	/**
	 * �������ʉ݋��z���擾����
	 * 
	 * @return �������ʉ݋��z
	 */
	public BigDecimal getHDR_INV_KIN() {
		return hDR_INV_KIN;
	}

	/**
	 * �������ʉ݋��z��ݒ肷��
	 * 
	 * @param hdr_inv_kin
	 */
	public void setHDR_INV_KIN(BigDecimal hdr_inv_kin) {
		hDR_INV_KIN = hdr_inv_kin;
	}

	/**
	 * �˗������擾����
	 * 
	 * @return �˗���
	 */
	public Date getHDR_IRAI_DATE() {
		return hDR_IRAI_DATE;
	}

	/**
	 * �˗�����ݒ肷��
	 * 
	 * @param hdr_irai_date
	 */
	public void setHDR_IRAI_DATE(Date hdr_irai_date) {
		hDR_IRAI_DATE = hdr_irai_date;
	}

	/**
	 * �˗�����R�[�h���擾����
	 * 
	 * @return �˗�����R�[�h
	 */
	public String getHDR_IRAI_DEP_CODE() {
		return hDR_IRAI_DEP_CODE;
	}

	/**
	 * �˗�����R�[�h��ݒ肷��
	 * 
	 * @param hdr_irai_dep_code
	 */
	public void setHDR_IRAI_DEP_CODE(String hdr_irai_dep_code) {
		hDR_IRAI_DEP_CODE = hdr_irai_dep_code;
	}

	/**
	 * �˗����喼���擾����
	 * 
	 * @return �˗����喼
	 */
	public String getHDR_IRAI_DEP_NAME() {
		return hDR_IRAI_DEP_NAME;
	}

	/**
	 * �˗����喼��ݒ肷��
	 * 
	 * @param hdr_irai_dep_name
	 */
	public void setHDR_IRAI_DEP_NAME(String hdr_irai_dep_name) {
		hDR_IRAI_DEP_NAME = hdr_irai_dep_name;
	}

	/**
	 * �˗����嗪�̂��擾����
	 * 
	 * @return �˗����嗪��
	 */
	public String getHDR_IRAI_DEP_NAME_S() {
		return hDR_IRAI_DEP_NAME_S;
	}

	/**
	 * �˗����嗪�̂�ݒ肷��
	 * 
	 * @param hdr_irai_dep_name_s
	 */
	public void setHDR_IRAI_DEP_NAME_S(String hdr_irai_dep_name_s) {
		hDR_IRAI_DEP_NAME_S = hdr_irai_dep_name_s;
	}

	/**
	 * �˗��Ј��R�[�h���擾����
	 * 
	 * @return �˗��Ј��R�[�h
	 */
	public String getHDR_IRAI_EMP_CODE() {
		return hDR_IRAI_EMP_CODE;
	}

	/**
	 * �˗��Ј��R�[�h��ݒ肷��
	 * 
	 * @param hdr_irai_emp_code
	 */
	public void setHDR_IRAI_EMP_CODE(String hdr_irai_emp_code) {
		hDR_IRAI_EMP_CODE = hdr_irai_emp_code;
	}

	/**
	 * �˗��Ј������擾����
	 * 
	 * @return �˗��Ј���
	 */
	public String getHDR_IRAI_EMP_NAME() {
		return hDR_IRAI_EMP_NAME;
	}

	/**
	 * �˗��Ј�����ݒ肷��
	 * 
	 * @param hdr_irai_emp_name
	 */
	public void setHDR_IRAI_EMP_NAME(String hdr_irai_emp_name) {
		hDR_IRAI_EMP_NAME = hdr_irai_emp_name;
	}

	/**
	 * �˗��Ј����̂��擾����
	 * 
	 * @return �˗��Ј�����
	 */
	public String getHDR_IRAI_EMP_NAME_S() {
		return hDR_IRAI_EMP_NAME_S;
	}

	/**
	 * �˗��Ј����̂�ݒ肷��
	 * 
	 * @param hdr_irai_emp_name_s
	 */
	public void setHDR_IRAI_EMP_NAME_S(String hdr_irai_emp_name_s) {
		hDR_IRAI_EMP_NAME_S = hdr_irai_emp_name_s;
	}

	/**
	 * �������Z�`�[�ԍ����擾����
	 * 
	 * @return �������Z�`�[�ԍ�
	 */
	public String getHDR_KARI_CR_DEN_NO() {
		return hDR_KARI_CR_DEN_NO;
	}

	/**
	 * �������Z�`�[�ԍ���ݒ肷��
	 * 
	 * @param hdr_kari_cr_den_no
	 */
	public void setHDR_KARI_CR_DEN_NO(String hdr_kari_cr_den_no) {
		hDR_KARI_CR_DEN_NO = hdr_kari_cr_den_no;
	}

	/**
	 * �����ʉ݃R�[�h���擾����
	 * 
	 * @return �����ʉ݃R�[�h
	 */
	public String getHDR_KARI_CUR_CODE() {
		return hDR_KARI_CUR_CODE;
	}

	/**
	 * �����ʉ݃R�[�h��ݒ肷��
	 * 
	 * @param hdr_kari_cur_code
	 */
	public void setHDR_KARI_CUR_CODE(String hdr_kari_cur_code) {
		hDR_KARI_CUR_CODE = hdr_kari_cur_code;
	}

	/**
	 * �����ʉ݃��[�g���擾����
	 * 
	 * @return �����ʉ݃��[�g
	 */
	public BigDecimal getHDR_KARI_CUR_RATE() {
		return hDR_KARI_CUR_RATE;
	}

	/**
	 * �����ʉ݃��[�g��ݒ肷��
	 * 
	 * @param hdr_kari_cur_rate
	 */
	public void setHDR_KARI_CUR_RATE(BigDecimal hdr_kari_cur_rate) {
		hDR_KARI_CUR_RATE = hdr_kari_cur_rate;
	}

	/**
	 * �����v�㕔��R�[�h���擾����
	 * 
	 * @return �����v�㕔��R�[�h
	 */
	public String getHDR_KARI_DEP_CODE() {
		return hDR_KARI_DEP_CODE;
	}

	/**
	 * �����v�㕔��R�[�h��ݒ肷��
	 * 
	 * @param hdr_kari_dep_code
	 */
	public void setHDR_KARI_DEP_CODE(String hdr_kari_dep_code) {
		hDR_KARI_DEP_CODE = hdr_kari_dep_code;
	}

	/**
	 * �����\���`�[�ԍ����擾����
	 * 
	 * @return �����\���`�[�ԍ�
	 */
	public String getHDR_KARI_DR_DEN_NO() {
		return hDR_KARI_DR_DEN_NO;
	}

	/**
	 * �����\���`�[�ԍ���ݒ肷��
	 * 
	 * @param hdr_kari_dr_den_no
	 */
	public void setHDR_KARI_DR_DEN_NO(String hdr_kari_dr_den_no) {
		hDR_KARI_DR_DEN_NO = hdr_kari_dr_den_no;
	}

	/**
	 * �������z���擾����
	 * 
	 * @return �������z
	 */
	public BigDecimal getHDR_KARI_KIN() {
		return hDR_KARI_KIN;
	}

	/**
	 * �������z��ݒ肷��
	 * 
	 * @param hdr_kari_kin
	 */
	public void setHDR_KARI_KIN(BigDecimal hdr_kari_kin) {
		hDR_KARI_KIN = hdr_kari_kin;
	}

	/**
	 * �o����z���擾����
	 * 
	 * @return �o����z
	 */
	public BigDecimal getHDR_KEIHI_KIN() {
		return hDR_KEIHI_KIN;
	}

	/**
	 * �o����z��ݒ肷��
	 * 
	 * @param hdr_keihi_kin
	 */
	public void setHDR_KEIHI_KIN(BigDecimal hdr_keihi_kin) {
		hDR_KEIHI_KIN = hdr_keihi_kin;
	}

	/**
	 * �x�����ϋ敪���擾����
	 * 
	 * @return �x�����ϋ敪
	 */
	public int getHDR_KESAI_KBN() {
		return hDR_KESAI_KBN;
	}

	/**
	 * �x�����ϋ敪��ݒ肷��
	 * 
	 * @param hdr_kesai_kbn
	 */
	public void setHDR_KESAI_KBN(int hdr_kesai_kbn) {
		hDR_KESAI_KBN = hdr_kesai_kbn;
	}

	/**
	 * �ȖڃR�[�h���擾����
	 * 
	 * @return �ȖڃR�[�h
	 */
	public String getHDR_KMK_CODE() {
		return hDR_KMK_CODE;
	}

	/**
	 * �ȖڃR�[�h��ݒ肷��
	 * 
	 * @param hdr_kmk_code
	 */
	public void setHDR_KMK_CODE(String hdr_kmk_code) {
		hDR_KMK_CODE = hdr_kmk_code;
	}

	/**
	 * �Ȗږ��̂��擾����
	 * 
	 * @return �Ȗږ���
	 */
	public String getHDR_KMK_NAME() {
		return hDR_KMK_NAME;
	}

	/**
	 * �Ȗږ��̂�ݒ肷��
	 * 
	 * @param hdr_kmk_name
	 */
	public void setHDR_KMK_NAME(String hdr_kmk_name) {
		hDR_KMK_NAME = hdr_kmk_name;
	}

	/**
	 * �Ȗڗ��̂��擾����
	 * 
	 * @return �Ȗڗ���
	 */
	public String getHDR_KMK_NAME_S() {
		return hDR_KMK_NAME_S;
	}

	/**
	 * �Ȗڗ��̂�ݒ肷��
	 * 
	 * @param hdr_kmk_name_s
	 */
	public void setHDR_KMK_NAME_S(String hdr_kmk_name_s) {
		hDR_KMK_NAME_S = hdr_kmk_name_s;
	}

	/**
	 * �r���敪���擾����
	 * 
	 * @return �r���敪
	 */
	public int getHDR_SHR_KBN() {
		return hDR_SHR_KBN;
	}

	/**
	 * �r���敪��ݒ肷��
	 * 
	 * @param hdr_shr_kbn
	 */
	public void setHDR_SHR_KBN(int hdr_shr_kbn) {
		hDR_SHR_KBN = hdr_shr_kbn;
	}

	/**
	 * �x�������擾����
	 * 
	 * @return �x����
	 */
	public Date getHDR_SIHA_DATE() {
		return hDR_SIHA_DATE;
	}

	/**
	 * �x������ݒ肷��
	 * 
	 * @param hdr_siha_date
	 */
	public void setHDR_SIHA_DATE(Date hdr_siha_date) {
		hDR_SIHA_DATE = hdr_siha_date;
	}

	/**
	 * �x���敪���擾����
	 * 
	 * @return �x���敪
	 */
	public String getHDR_SIHA_KBN() {
		return hDR_SIHA_KBN;
	}

	/**
	 * �x���敪��ݒ肷��
	 * 
	 * @param hdr_siha_kbn
	 */
	public void setHDR_SIHA_KBN(String hdr_siha_kbn) {
		hDR_SIHA_KBN = hdr_siha_kbn;
	}

	/**
	 * �x�����z���擾����
	 * 
	 * @return �x�����z
	 */
	public BigDecimal getHDR_SIHA_KIN() {
		return hDR_SIHA_KIN;
	}

	/**
	 * �x�����z��ݒ肷��
	 * 
	 * @param hdr_siha_kin
	 */
	public void setHDR_SIHA_KIN(BigDecimal hdr_siha_kin) {
		hDR_SIHA_KIN = hdr_siha_kin;
	}

	/**
	 * ���Z�\������擾����
	 * 
	 * @return ���Z�\���
	 */
	public Date getHDR_SSY_DATE() {
		return hDR_SSY_DATE;
	}

	/**
	 * ���Z�\�����ݒ肷��
	 * 
	 * @param hdr_ssy_date
	 */
	public void setHDR_SSY_DATE(Date hdr_ssy_date) {
		hDR_SSY_DATE = hdr_ssy_date;
	}

	/**
	 * ���F�����擾����
	 * 
	 * @return ���F��
	 */
	public Date getHDR_SYO_DATE() {
		return hDR_SYO_DATE;
	}

	/**
	 * ��ݒ肷��
	 * 
	 * @param hdr_syo_date
	 */
	public void setHDR_SYO_DATE(Date hdr_syo_date) {
		hDR_SYO_DATE = hdr_syo_date;
	}

	/**
	 * ���F�҂��擾����
	 * 
	 * @return ���F��
	 */
	public String getHDR_SYO_EMP_CODE() {
		return hDR_SYO_EMP_CODE;
	}

	/**
	 * ���F�҂�ݒ肷��
	 * 
	 * @param hdr_syo_emp_code
	 */
	public void setHDR_SYO_EMP_CODE(String hdr_syo_emp_code) {
		hDR_SYO_EMP_CODE = hdr_syo_emp_code;
	}

	/**
	 * �V�X�e���敪���擾����
	 * 
	 * @return �V�X�e���敪
	 */
	public String getHDR_SYS_KBN() {
		return hDR_SYS_KBN;
	}

	/**
	 * �V�X�e���敪��ݒ肷��
	 * 
	 * @param hdr_sys_kbn
	 */
	public void setHDR_SYS_KBN(String hdr_sys_kbn) {
		hDR_SYS_KBN = hdr_sys_kbn;
	}

	/**
	 * �`�[�E�v���擾����
	 * 
	 * @return �`�[�E�v
	 */
	public String getHDR_TEK() {
		return hDR_TEK;
	}

	/**
	 * �`�[�E�v��ݒ肷��
	 * 
	 * @param hdr_tek
	 */
	public void setHDR_TEK(String hdr_tek) {
		hDR_TEK = hdr_tek;
	}

	/**
	 * �`�[�E�v�R�[�h���擾����
	 * 
	 * @return �`�[�E�v�R�[�h
	 */
	public String getHDR_TEK_CODE() {
		return hDR_TEK_CODE;
	}

	/**
	 * �`�[�E�v�R�[�h��ݒ肷��
	 * 
	 * @param hdr_tek_code
	 */
	public void setHDR_TEK_CODE(String hdr_tek_code) {
		hDR_TEK_CODE = hdr_tek_code;
	}

	/**
	 * ���������R�[�h���擾����
	 * 
	 * @return ���������R�[�h
	 */
	public String getHDR_TJK_CODE() {
		return hDR_TJK_CODE;
	}

	/**
	 * ���������R�[�h��ݒ肷��
	 * 
	 * @param hdr_tjk_code
	 */
	public void setHDR_TJK_CODE(String hdr_tjk_code) {
		hDR_TJK_CODE = hdr_tjk_code;
	}

	/**
	 * �����R�[�h���擾����
	 * 
	 * @return �����R�[�h
	 */
	public String getHDR_TRI_CODE() {
		return hDR_TRI_CODE;
	}

	/**
	 * �����R�[�h��ݒ肷��
	 * 
	 * @param hdr_tri_code
	 */
	public void setHDR_TRI_CODE(String hdr_tri_code) {
		hDR_TRI_CODE = hdr_tri_code;
	}

	/**
	 * ��Њԕt�֋敪���擾����
	 * 
	 * @return ��Њԕt�֋敪
	 */
	public int getHDR_TUKE_KBN() {
		return hDR_TUKE_KBN;
	}

	/**
	 * ��Њԕt�֋敪��ݒ肷��
	 * 
	 * @param hdr_tuke_kbn
	 */
	public void setHDR_TUKE_KBN(int hdr_tuke_kbn) {
		hDR_TUKE_KBN = hdr_tuke_kbn;
	}

	/**
	 * ��t����R�[�h���擾����
	 * 
	 * @return ��t����R�[�h
	 */
	public String getHDR_UKE_DEP_CODE() {
		return hDR_UKE_DEP_CODE;
	}

	/**
	 * ��t����R�[�h��ݒ肷��
	 * 
	 * @param hdr_uke_dep_code
	 */
	public void setHDR_UKE_DEP_CODE(String hdr_uke_dep_code) {
		hDR_UKE_DEP_CODE = hdr_uke_dep_code;
	}

	/**
	 * ����ȖڃR�[�h���擾����
	 * 
	 * @return ����ȖڃR�[�h
	 */
	public String getHDR_UKM_CODE() {
		return hDR_UKM_CODE;
	}

	/**
	 * ����ȖڃR�[�h��ݒ肷��
	 * 
	 * @param hdr_ukm_code
	 */
	public void setHDR_UKM_CODE(String hdr_ukm_code) {
		hDR_UKM_CODE = hdr_ukm_code;
	}

	/**
	 * ����Ȗږ��̂��擾����
	 * 
	 * @return ����Ȗږ���
	 */
	public String getHDR_UKM_NAME() {
		return hDR_UKM_NAME;
	}

	/**
	 * ����Ȗږ��̂�ݒ肷��
	 * 
	 * @param hdr_ukm_name
	 */
	public void setHDR_UKM_NAME(String hdr_ukm_name) {
		hDR_UKM_NAME = hdr_ukm_name;
	}

	/**
	 * ����Ȗڗ��̂��擾����
	 * 
	 * @return ����Ȗڗ���
	 */
	public String getHDR_UKM_NAME_S() {
		return hDR_UKM_NAME_S;
	}

	/**
	 * ����Ȗڗ��̂�ݒ肷��
	 * 
	 * @param hdr_ukm_name_s
	 */
	public void setHDR_UKM_NAME_S(String hdr_ukm_name_s) {
		hDR_UKM_NAME_S = hdr_ukm_name_s;
	}

	/**
	 * �C���񐔂��擾����
	 * 
	 * @return �C����
	 */
	public Integer getHDR_UPD_CNT() {
		return hDR_UPD_CNT;
	}

	/**
	 * �C���񐔂�ݒ肷��
	 * 
	 * @param hdr_upd_cnt
	 */
	public void setHDR_UPD_CNT(Integer hdr_upd_cnt) {
		hDR_UPD_CNT = hdr_upd_cnt;
	}

	/**
	 * ��tNO���擾����
	 * 
	 * @return ��tNO
	 */
	public String getHDR_UTK_NO() {
		return hDR_UTK_NO;
	}

	/**
	 * ��tNO��ݒ肷��
	 * 
	 * @param hdr_utk_no
	 */
	public void setHDR_UTK_NO(String hdr_utk_no) {
		hDR_UTK_NO = hdr_utk_no;
	}

	/**
	 * ��ЃR�[�h���擾����
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * ��ЃR�[�h��ݒ肷��
	 * 
	 * @param kai_code
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * ��Ж����擾����
	 * 
	 * @return ��Ж�
	 */
	public String getKAI_NAME() {
		return kAI_NAME;
	}

	/**
	 * ��Ж���ݒ肷��
	 * 
	 * @param kai_name
	 */
	public void setKAI_NAME(String kai_name) {
		kAI_NAME = kai_name;
	}

	/**
	 * ��З��̂��擾����
	 * 
	 * @return ��З���
	 */
	public String getKAI_NAME_S() {
		return kAI_NAME_S;
	}

	/**
	 * ��З��̂�ݒ肷��
	 * 
	 * @param kai_name_s
	 */
	public void setKAI_NAME_S(String kai_name_s) {
		kAI_NAME_S = kai_name_s;
	}

	/**
	 * ���Z�敪���擾����
	 * 
	 * @return ���Z�敪
	 */
	public int getKSN_KBN() {
		return KSN_KBN;
	}

	/**
	 * ���Z�敪��ݒ肷��
	 * 
	 * @param ksn_kbn
	 */
	public void setKSN_KBN(int ksn_kbn) {
		KSN_KBN = ksn_kbn;
	}

	/**
	 * PGID���擾����
	 * 
	 * @return PGID
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * PGID��ݒ肷��
	 * 
	 * @param prg_id
	 */
	public void setPRG_ID(String prg_id) {
		pRG_ID = prg_id;
	}

	/**
	 * �؜ߔԍ����擾����
	 * 
	 * @return �؜ߔԍ�
	 */
	public String getSEI_NO() {
		return sEI_NO;
	}

	/**
	 * �؜ߔԍ���ݒ肷��
	 * 
	 * @param sei_no
	 */
	public void setSEI_NO(String sei_no) {
		sEI_NO = sei_no;
	}

	/**
	 * ���蕔��R�[�h���擾����
	 * 
	 * @return ���蕔��R�[�h
	 */
	public String getSWK_AT_DEP_CODE() {
		return sWK_AT_DEP_CODE;
	}

	/**
	 * ���蕔��R�[�h��ݒ肷��
	 * 
	 * @param swk_at_dep_code
	 */
	public void setSWK_AT_DEP_CODE(String swk_at_dep_code) {
		sWK_AT_DEP_CODE = swk_at_dep_code;
	}

	/**
	 * ����⏕�R�[�h���擾����
	 * 
	 * @return ����⏕�R�[�h
	 */
	public String getSWK_AT_HKM_CODE() {
		return sWK_AT_HKM_CODE;
	}

	/**
	 * ����⏕�R�[�h��ݒ肷��
	 * 
	 * @param swk_at_hkm_code
	 */
	public void setSWK_AT_HKM_CODE(String swk_at_hkm_code) {
		sWK_AT_HKM_CODE = swk_at_hkm_code;
	}

	/**
	 * ����ȖڃR�[�h���擾����
	 * 
	 * @return ����ȖڃR�[�h
	 */
	public String getSWK_AT_KMK_CODE() {
		return sWK_AT_KMK_CODE;
	}

	/**
	 * ����ȖڃR�[�h��ݒ肷��
	 * 
	 * @param swk_at_kmk_code
	 */
	public void setSWK_AT_KMK_CODE(String swk_at_kmk_code) {
		sWK_AT_KMK_CODE = swk_at_kmk_code;
	}

	/**
	 * �������Ȗڂ��擾����
	 * 
	 * @return �������Ȗ�
	 */
	public String getSWK_AT_UKM_CODE() {
		return sWK_AT_UKM_CODE;
	}

	/**
	 * �������Ȗڂ�ݒ肷��
	 * 
	 * @param swk_at_ukm_code
	 */
	public void setSWK_AT_UKM_CODE(String swk_at_ukm_code) {
		sWK_AT_UKM_CODE = swk_at_ukm_code;
	}

	/**
	 * �����d��敪���擾����
	 * 
	 * @return �����d��敪
	 */
	public int getSWK_AUTO_KBN() {
		return sWK_AUTO_KBN;
	}

	/**
	 * �����d��敪��ݒ肷��
	 * 
	 * @param swk_auto_kbn
	 */
	public void setSWK_AUTO_KBN(int swk_auto_kbn) {
		sWK_AUTO_KBN = swk_auto_kbn;
	}

	/**
	 * �ʉ݃R�[�h���擾����
	 * 
	 * @return �ʉ݃R�[�h
	 */
	public String getSWK_CUR_CODE() {
		return sWK_CUR_CODE;
	}

	/**
	 * �ʉ݃R�[�h��ݒ肷��
	 * 
	 * @param swk_cur_code
	 */
	public void setSWK_CUR_CODE(String swk_cur_code) {
		sWK_CUR_CODE = swk_cur_code;
	}

	/**
	 * �ʉ݃R�[�h �����_���擾����
	 * 
	 * @return �ʉ݃R�[�h �����_
	 */
	public String getSWK_CUR_DEC_KETA() {
		return sWK_CUR_DEC_KETA;
	}

	/**
	 * �ʉ݃R�[�h �����_��ݒ肷��
	 * 
	 * @param swk_cur_dec_keta
	 */
	public void setSWK_CUR_DEC_KETA(String swk_cur_dec_keta) {
		sWK_CUR_DEC_KETA = swk_cur_dec_keta;
	}

	/**
	 * ���[�g���擾����
	 * 
	 * @return ���[�g
	 */
	public BigDecimal getSWK_CUR_RATE() {
		return sWK_CUR_RATE;
	}

	/**
	 * ���[�g��ݒ肷��
	 * 
	 * @param swk_cur_rate
	 */
	public void setSWK_CUR_RATE(BigDecimal swk_cur_rate) {
		sWK_CUR_RATE = swk_cur_rate;
	}

	/**
	 * �ݎ؋敪���擾����
	 * 
	 * @return �ݎ؋敪
	 */
	public int getSWK_DC_KBN() {
		return sWK_DC_KBN;
	}

	/**
	 * �ݎ؋敪��ݒ肷��
	 * 
	 * @param swk_dc_kbn
	 */
	public void setSWK_DC_KBN(int swk_dc_kbn) {
		sWK_DC_KBN = swk_dc_kbn;
	}

	/**
	 * ����R�[�h���擾����
	 * 
	 * @return ����R�[�h
	 */
	public String getSWK_DEP_CODE() {
		return sWK_DEP_CODE;
	}

	/**
	 * ����R�[�h��ݒ肷��
	 * 
	 * @param swk_dep_code
	 */
	public void setSWK_DEP_CODE(String swk_dep_code) {
		sWK_DEP_CODE = swk_dep_code;
	}

	/**
	 * �Ј��R�[�h���擾����
	 * 
	 * @return �Ј��R�[�h
	 */
	public String getSWK_EMP_CODE() {
		return sWK_EMP_CODE;
	}

	/**
	 * �Ј��R�[�h��ݒ肷��
	 * 
	 * @param swk_emp_code
	 */
	public void setSWK_EMP_CODE(String swk_emp_code) {
		sWK_EMP_CODE = swk_emp_code;
	}

	/**
	 * �Ј����̂��擾����
	 * 
	 * @return �Ј�����
	 */
	public String getSWK_EMP_NAME() {
		return sWK_EMP_NAME;
	}

	/**
	 * �Ј����̂�ݒ肷��
	 * 
	 * @param swk_emp_name
	 */
	public void setSWK_EMP_NAME(String swk_emp_name) {
		sWK_EMP_NAME = swk_emp_name;
	}

	/**
	 * �Ј����̂��擾����
	 * 
	 * @return �Ј�����
	 */
	public String getSWK_EMP_NAME_S() {
		return sWK_EMP_NAME_S;
	}

	/**
	 * �Ј����̂�ݒ肷��
	 * 
	 * @param swk_emp_name_s
	 */
	public void setSWK_EMP_NAME_S(String swk_emp_name_s) {
		sWK_EMP_NAME_S = swk_emp_name_s;
	}

	/**
	 * �s�ԍ����擾����
	 * 
	 * @return �s�ԍ�
	 */
	public int getSWK_GYO_NO() {
		return sWK_GYO_NO;
	}

	/**
	 * �s�ԍ���ݒ肷��
	 * 
	 * @param swk_gyo_no
	 */
	public void setSWK_GYO_NO(int swk_gyo_no) {
		sWK_GYO_NO = swk_gyo_no;
	}

	/**
	 * �s�E�v���擾����
	 * 
	 * @return �s�E�v
	 */
	public String getSWK_GYO_TEK() {
		return sWK_GYO_TEK;
	}

	/**
	 * �s�E�v��ݒ肷��
	 * 
	 * @param swk_gyo_tek
	 */
	public void setSWK_GYO_TEK(String swk_gyo_tek) {
		sWK_GYO_TEK = swk_gyo_tek;
	}

	/**
	 * �s�E�v�R�[�h���擾����
	 * 
	 * @return �s�E�v�R�[�h
	 */
	public String getSWK_GYO_TEK_CODE() {
		return sWK_GYO_TEK_CODE;
	}

	/**
	 * �s�E�v�R�[�h��ݒ肷��
	 * 
	 * @param swk_gyo_tek_code
	 */
	public void setSWK_GYO_TEK_CODE(String swk_gyo_tek_code) {
		sWK_GYO_TEK_CODE = swk_gyo_tek_code;
	}

	/**
	 * ���������擾����
	 * 
	 * @return ������
	 */
	public Date getSWK_HAS_DATE() {
		return sWK_HAS_DATE;
	}

	/**
	 * ��������ݒ肷��
	 * 
	 * @param swk_has_date
	 */
	public void setSWK_HAS_DATE(Date swk_has_date) {
		sWK_HAS_DATE = swk_has_date;
	}

	/**
	 * �⏕�ȖڃR�[�h���擾����
	 * 
	 * @return �⏕�ȖڃR�[�h
	 */
	public String getSWK_HKM_CODE() {
		return sWK_HKM_CODE;
	}

	/**
	 * �⏕�ȖڃR�[�h��ݒ肷��
	 * 
	 * @param swk_hkm_code
	 */
	public void setSWK_HKM_CODE(String swk_hkm_code) {
		sWK_HKM_CODE = swk_hkm_code;
	}

	/**
	 * �⏕�Ȗږ��̂��擾����
	 * 
	 * @return �⏕�Ȗږ���
	 */
	public String getSWK_HKM_NAME() {
		return sWK_HKM_NAME;
	}

	/**
	 * �⏕�Ȗږ��̂�ݒ肷��
	 * 
	 * @param swk_hkm_name
	 */
	public void setSWK_HKM_NAME(String swk_hkm_name) {
		sWK_HKM_NAME = swk_hkm_name;
	}

	/**
	 * �⏕�Ȗڗ��̂��擾����
	 * 
	 * @return �⏕�Ȗڗ���
	 */
	public String getSWK_HKM_NAME_S() {
		return sWK_HKM_NAME_S;
	}

	/**
	 * �⏕�Ȗڗ��̂�ݒ肷��
	 * 
	 * @param swk_hkm_name_s
	 */
	public void setSWK_HKM_NAME_S(String swk_hkm_name_s) {
		sWK_HKM_NAME_S = swk_hkm_name_s;
	}

	/**
	 * ���v����1���擾����
	 * 
	 * @return ���v����1
	 */
	public String getSWK_HM_1() {
		return sWK_HM_1;
	}

	/**
	 * ���v����1��ݒ肷��
	 * 
	 * @param swk_hm_1
	 */
	public void setSWK_HM_1(String swk_hm_1) {
		sWK_HM_1 = swk_hm_1;
	}

	/**
	 * ���v����2���擾����
	 * 
	 * @return ���v����2
	 */
	public String getSWK_HM_2() {
		return sWK_HM_2;
	}

	/**
	 * ���v����2��ݒ肷��
	 * 
	 * @param swk_hm_2
	 */
	public void setSWK_HM_2(String swk_hm_2) {
		sWK_HM_2 = swk_hm_2;
	}

	/**
	 * ���v����3���擾����
	 * 
	 * @return ���v����3
	 */
	public String getSWK_HM_3() {
		return sWK_HM_3;
	}

	/**
	 * ���v����3��ݒ肷��
	 * 
	 * @param swk_hm_3
	 */
	public void setSWK_HM_3(String swk_hm_3) {
		sWK_HM_3 = swk_hm_3;
	}

	/**
	 * ���͋��z���擾����
	 * 
	 * @return ���͋��z
	 */
	public BigDecimal getSWK_IN_KIN() {
		return sWK_IN_KIN;
	}

	/**
	 * ���͋��z��ݒ肷��
	 * 
	 * @param swk_in_kin
	 */
	public void setSWK_IN_KIN(BigDecimal swk_in_kin) {
		sWK_IN_KIN = swk_in_kin;
	}

	/**
	 * ���͏���Ŋz���擾����
	 * 
	 * @return ���͏���Ŋz
	 */
	public BigDecimal getSWK_IN_ZEI_KIN() {
		return sWK_IN_ZEI_KIN;
	}

	/**
	 * ���͏���Ŋz��ݒ肷��
	 * 
	 * @param swk_in_zei_kin
	 */
	public void setSWK_IN_ZEI_KIN(BigDecimal swk_in_zei_kin) {
		sWK_IN_ZEI_KIN = swk_in_zei_kin;
	}

	/**
	 * �v���ЃR�[�h���擾����
	 * 
	 * @return �v���ЃR�[�h
	 */
	public String getSWK_K_KAI_CODE() {
		return sWK_K_KAI_CODE;
	}

	/**
	 * �v���ЃR�[�h��ݒ肷��
	 * 
	 * @param swk_k_kai_code
	 */
	public void setSWK_K_KAI_CODE(String swk_k_kai_code) {
		sWK_K_KAI_CODE = swk_k_kai_code;
	}

	/**
	 * �����`�[���t���擾����
	 * 
	 * @return �����`�[���t
	 */
	public Date getSWK_KESI_DATE() {
		return sWK_KESI_DATE;
	}

	/**
	 * �����`�[���t��ݒ肷��
	 * 
	 * @param swk_kesi_date
	 */
	public void setSWK_KESI_DATE(Date swk_kesi_date) {
		sWK_KESI_DATE = swk_kesi_date;
	}

	/**
	 * �����`�[�ԍ����擾����
	 * 
	 * @return �����`�[�ԍ�
	 */
	public String getSWK_KESI_DEN_NO() {
		return sWK_KESI_DEN_NO;
	}

	/**
	 * �����`�[�ԍ���ݒ肷��
	 * 
	 * @param swk_kesi_den_no
	 */
	public void setSWK_KESI_DEN_NO(String swk_kesi_den_no) {
		sWK_KESI_DEN_NO = swk_kesi_den_no;
	}

	/**
	 * �����敪���擾����
	 * 
	 * @return �����敪
	 */
	public int getSWK_KESI_KBN() {
		return sWK_KESI_KBN;
	}

	/**
	 * �����敪��ݒ肷��
	 * 
	 * @param swk_kesi_kbn
	 */
	public void setSWK_KESI_KBN(int swk_kesi_kbn) {
		sWK_KESI_KBN = swk_kesi_kbn;
	}

	/**
	 * �M�݋��z���擾����
	 * 
	 * @return �M�݋��z
	 */
	public BigDecimal getSWK_KIN() {
		return sWK_KIN;
	}

	/**
	 * �M�݋��z��ݒ肷��
	 * 
	 * @param swk_kin
	 */
	public void setSWK_KIN(BigDecimal swk_kin) {
		sWK_KIN = swk_kin;
	}

	/**
	 * �ȖڃR�[�h���擾����
	 * 
	 * @return �ȖڃR�[�h
	 */
	public String getSWK_KMK_CODE() {
		return sWK_KMK_CODE;
	}

	/**
	 * �ȖڃR�[�h��ݒ肷��
	 * 
	 * @param swk_kmk_code
	 */
	public void setSWK_KMK_CODE(String swk_kmk_code) {
		sWK_KMK_CODE = swk_kmk_code;
	}

	/**
	 * �Ȗږ��̂��擾����
	 * 
	 * @return �Ȗږ���
	 */
	public String getSWK_KMK_NAME() {
		return sWK_KMK_NAME;
	}

	/**
	 * �Ȗږ��̂�ݒ肷��
	 * 
	 * @param swk_kmk_name
	 */
	public void setSWK_KMK_NAME(String swk_kmk_name) {
		sWK_KMK_NAME = swk_kmk_name;
	}

	/**
	 * �Ȗڗ��̂��擾����
	 * 
	 * @return �Ȗڗ���
	 */
	public String getSWK_KMK_NAME_S() {
		return sWK_KMK_NAME_S;
	}

	/**
	 * �Ȗڗ��̂�ݒ肷��
	 * 
	 * @param swk_kmk_name_s
	 */
	public void setSWK_KMK_NAME_S(String swk_kmk_name_s) {
		sWK_KMK_NAME_S = swk_kmk_name_s;
	}

	/**
	 * �Ǘ�1�R�[�h���擾����
	 * 
	 * @return �Ǘ�1�R�[�h
	 */
	public String getSWK_KNR_CODE_1() {
		return sWK_KNR_CODE_1;
	}

	/**
	 * �Ǘ�1�R�[�h��ݒ肷��
	 * 
	 * @param swk_knr_code_1
	 */
	public void setSWK_KNR_CODE_1(String swk_knr_code_1) {
		sWK_KNR_CODE_1 = swk_knr_code_1;
	}

	/**
	 * �Ǘ�2�R�[�h���擾����
	 * 
	 * @return �Ǘ�2�R�[�h
	 */
	public String getSWK_KNR_CODE_2() {
		return sWK_KNR_CODE_2;
	}

	/**
	 * �Ǘ�2�R�[�h��ݒ肷��
	 * 
	 * @param swk_knr_code_2
	 */
	public void setSWK_KNR_CODE_2(String swk_knr_code_2) {
		sWK_KNR_CODE_2 = swk_knr_code_2;
	}

	/**
	 * �Ǘ�3�R�[�h���擾����
	 * 
	 * @return �Ǘ�1�R�[�h3
	 */
	public String getSWK_KNR_CODE_3() {
		return sWK_KNR_CODE_3;
	}

	/**
	 * �Ǘ�3�R�[�h��ݒ肷��
	 * 
	 * @param swk_knr_code_3
	 */
	public void setSWK_KNR_CODE_3(String swk_knr_code_3) {
		sWK_KNR_CODE_3 = swk_knr_code_3;
	}

	/**
	 * �Ǘ�4�R�[�h���擾����
	 * 
	 * @return �Ǘ�4�R�[�h
	 */
	public String getSWK_KNR_CODE_4() {
		return sWK_KNR_CODE_4;
	}

	/**
	 * �Ǘ�4�R�[�h��ݒ肷��
	 * 
	 * @param swk_knr_code_4
	 */
	public void setSWK_KNR_CODE_4(String swk_knr_code_4) {
		sWK_KNR_CODE_4 = swk_knr_code_4;
	}

	/**
	 * �Ǘ�5�R�[�h���擾����
	 * 
	 * @return �Ǘ�5�R�[�h
	 */
	public String getSWK_KNR_CODE_5() {
		return sWK_KNR_CODE_5;
	}

	/**
	 * �Ǘ�5�R�[�h��ݒ肷��
	 * 
	 * @param swk_knr_code_5
	 */
	public void setSWK_KNR_CODE_5(String swk_knr_code_5) {
		sWK_KNR_CODE_5 = swk_knr_code_5;
	}

	/**
	 * �Ǘ�6�R�[�h���擾����
	 * 
	 * @return �Ǘ�6�R�[�h
	 */
	public String getSWK_KNR_CODE_6() {
		return sWK_KNR_CODE_6;
	}

	/**
	 * �Ǘ�6�R�[�h��ݒ肷��
	 * 
	 * @param swk_knr_code_6
	 */
	public void setSWK_KNR_CODE_6(String swk_knr_code_6) {
		sWK_KNR_CODE_6 = swk_knr_code_6;
	}

	/**
	 * �Ǘ�1�R�[�h���̂��擾����
	 * 
	 * @return �Ǘ�1�R�[�h����
	 */
	public String getSWK_KNR_NAME_1() {
		return sWK_KNR_NAME_1;
	}

	/**
	 * �Ǘ�1�R�[�h���̂�ݒ肷��
	 * 
	 * @param swk_knr_name_1
	 */
	public void setSWK_KNR_NAME_1(String swk_knr_name_1) {
		sWK_KNR_NAME_1 = swk_knr_name_1;
	}

	/**
	 * �Ǘ�2�R�[�h���̂��擾����
	 * 
	 * @return �Ǘ�2�R�[�h����
	 */
	public String getSWK_KNR_NAME_2() {
		return sWK_KNR_NAME_2;
	}

	/**
	 * �Ǘ�2�R�[�h���̂�ݒ肷��
	 * 
	 * @param swk_knr_name_2
	 */
	public void setSWK_KNR_NAME_2(String swk_knr_name_2) {
		sWK_KNR_NAME_2 = swk_knr_name_2;
	}

	/**
	 * �Ǘ�3�R�[�h���̂��擾����
	 * 
	 * @return �Ǘ�3�R�[�h����
	 */
	public String getSWK_KNR_NAME_3() {
		return sWK_KNR_NAME_3;
	}

	/**
	 * �Ǘ�3�R�[�h���̂�ݒ肷��
	 * 
	 * @param swk_knr_name_3
	 */
	public void setSWK_KNR_NAME_3(String swk_knr_name_3) {
		sWK_KNR_NAME_3 = swk_knr_name_3;
	}

	/**
	 * �Ǘ�4�R�[�h���̂��擾����
	 * 
	 * @return �Ǘ�4�R�[�h����
	 */
	public String getSWK_KNR_NAME_4() {
		return sWK_KNR_NAME_4;
	}

	/**
	 * �Ǘ�4�R�[�h���̂�ݒ肷��
	 * 
	 * @param swk_knr_name_4
	 */
	public void setSWK_KNR_NAME_4(String swk_knr_name_4) {
		sWK_KNR_NAME_4 = swk_knr_name_4;
	}

	/**
	 * �Ǘ�5�R�[�h���̂��擾����
	 * 
	 * @return �Ǘ�5�R�[�h����
	 */
	public String getSWK_KNR_NAME_5() {
		return sWK_KNR_NAME_5;
	}

	/**
	 * �Ǘ�5�R�[�h���̂�ݒ肷��
	 * 
	 * @param swk_knr_name_5
	 */
	public void setSWK_KNR_NAME_5(String swk_knr_name_5) {
		sWK_KNR_NAME_5 = swk_knr_name_5;
	}

	/**
	 * �Ǘ�6�R�[�h���̂��擾����
	 * 
	 * @return �Ǘ�6�R�[�h����
	 */
	public String getSWK_KNR_NAME_6() {
		return sWK_KNR_NAME_6;
	}

	/**
	 * �Ǘ�6�R�[�h���̂�ݒ肷��
	 * 
	 * @param swk_knr_name_6
	 */
	public void setSWK_KNR_NAME_6(String swk_knr_name_6) {
		sWK_KNR_NAME_6 = swk_knr_name_6;
	}

	/**
	 * �Ǘ�1�R�[�h���̂��擾����
	 * 
	 * @return �Ǘ�1�R�[�h����
	 */
	public String getSWK_KNR_NAME_S_1() {
		return sWK_KNR_NAME_S_1;
	}

	/**
	 * �Ǘ�1�R�[�h���̂�ݒ肷��
	 * 
	 * @param swk_knr_name_s_1
	 */
	public void setSWK_KNR_NAME_S_1(String swk_knr_name_s_1) {
		sWK_KNR_NAME_S_1 = swk_knr_name_s_1;
	}

	/**
	 * �Ǘ�2�R�[�h���̂��擾����
	 * 
	 * @return �Ǘ�2�R�[�h����
	 */
	public String getSWK_KNR_NAME_S_2() {
		return sWK_KNR_NAME_S_2;
	}

	/**
	 * �Ǘ�2�R�[�h���̂�ݒ肷��
	 * 
	 * @param swk_knr_name_s_2
	 */
	public void setSWK_KNR_NAME_S_2(String swk_knr_name_s_2) {
		sWK_KNR_NAME_S_2 = swk_knr_name_s_2;
	}

	/**
	 * �Ǘ�3�R�[�h���̂��擾����
	 * 
	 * @return �Ǘ�3�R�[�h����
	 */
	public String getSWK_KNR_NAME_S_3() {
		return sWK_KNR_NAME_S_3;
	}

	/**
	 * �Ǘ�3�R�[�h���̂�ݒ肷��
	 * 
	 * @param swk_knr_name_s_3
	 */
	public void setSWK_KNR_NAME_S_3(String swk_knr_name_s_3) {
		sWK_KNR_NAME_S_3 = swk_knr_name_s_3;
	}

	/**
	 * �Ǘ�4�R�[�h���̂��擾����
	 * 
	 * @return �Ǘ�4�R�[�h����
	 */
	public String getSWK_KNR_NAME_S_4() {
		return sWK_KNR_NAME_S_4;
	}

	/**
	 * �Ǘ�4�R�[�h���̂�ݒ肷��
	 * 
	 * @param swk_knr_name_s_4
	 */
	public void setSWK_KNR_NAME_S_4(String swk_knr_name_s_4) {
		sWK_KNR_NAME_S_4 = swk_knr_name_s_4;
	}

	/**
	 * �Ǘ�5�R�[�h���̂��擾����
	 * 
	 * @return �Ǘ�5�R�[�h����
	 */
	public String getSWK_KNR_NAME_S_5() {
		return sWK_KNR_NAME_S_5;
	}

	/**
	 * �Ǘ�5�R�[�h���̂�ݒ肷��
	 * 
	 * @param swk_knr_name_s_5
	 */
	public void setSWK_KNR_NAME_S_5(String swk_knr_name_s_5) {
		sWK_KNR_NAME_S_5 = swk_knr_name_s_5;
	}

	/**
	 * �Ǘ�6�R�[�h���̂��擾����
	 * 
	 * @return �Ǘ�6�R�[�h����
	 */
	public String getSWK_KNR_NAME_S_6() {
		return sWK_KNR_NAME_S_6;
	}

	/**
	 * �Ǘ�6�R�[�h���̂�ݒ肷��
	 * 
	 * @param swk_knr_name_s_6
	 */
	public void setSWK_KNR_NAME_S_6(String swk_knr_name_s_6) {
		sWK_KNR_NAME_S_6 = swk_knr_name_s_6;
	}

	/**
	 * �N�x���擾����
	 * 
	 * @return �N�x
	 */
	public int getSWK_NENDO() {
		return sWK_NENDO;
	}

	/**
	 * �N�x��ݒ肷��
	 * 
	 * @param swk_nendo
	 */
	public void setSWK_NENDO(int swk_nendo) {
		sWK_NENDO = swk_nendo;
	}

	/**
	 * ���E�����擾����
	 * 
	 * @return ���E��
	 */
	public Date getSWK_SOUSAI_DATE() {
		return sWK_SOUSAI_DATE;
	}

	/**
	 * ���E����ݒ肷��
	 * 
	 * @param swk_sousai_date
	 */
	public void setSWK_SOUSAI_DATE(Date swk_sousai_date) {
		sWK_SOUSAI_DATE = swk_sousai_date;
	}

	/**
	 * ���E�`�[�ԍ����擾����
	 * 
	 * @return ���E�`�[�ԍ�
	 */
	public String getSWK_SOUSAI_DEN_NO() {
		return sWK_SOUSAI_DEN_NO;
	}

	/**
	 * ���E�`�[�ԍ���ݒ肷��
	 * 
	 * @param swk_sousai_den_no
	 */
	public void setSWK_SOUSAI_DEN_NO(String swk_sousai_den_no) {
		sWK_SOUSAI_DEN_NO = swk_sousai_den_no;
	}

	/**
	 * �����R�[�h���擾����
	 * 
	 * @return �����R�[�h
	 */
	public String getSWK_TRI_CODE() {
		return sWK_TRI_CODE;
	}

	/**
	 * �����R�[�h��ݒ肷��
	 * 
	 * @param swk_tri_code
	 */
	public void setSWK_TRI_CODE(String swk_tri_code) {
		sWK_TRI_CODE = swk_tri_code;
	}

	/**
	 * ����於�̂��擾����
	 * 
	 * @return ����於��
	 */
	public String getSWK_TRI_NAME() {
		return sWK_TRI_NAME;
	}

	/**
	 * ����於�̂�ݒ肷��
	 * 
	 * @param swk_tri_name
	 */
	public void setSWK_TRI_NAME(String swk_tri_name) {
		sWK_TRI_NAME = swk_tri_name;
	}

	/**
	 * ����旪�̂��擾����
	 * 
	 * @return ����旪��
	 */
	public String getSWK_TRI_NAME_S() {
		return sWK_TRI_NAME_S;
	}

	/**
	 * ����旪�̂�ݒ肷��
	 * 
	 * @param swk_tri_name_s
	 */
	public void setSWK_TRI_NAME_S(String swk_tri_name_s) {
		sWK_TRI_NAME_S = swk_tri_name_s;
	}

	/**
	 * ��Њԕt�֋敪���擾����
	 * 
	 * @return ��Њԕt�֋敪
	 */
	public int getSWK_TUKE_KBN() {
		return sWK_TUKE_KBN;
	}

	/**
	 * ��Њԕt�֋敪��ݒ肷��
	 * 
	 * @param swk_tuke_kbn
	 */
	public void setSWK_TUKE_KBN(int swk_tuke_kbn) {
		sWK_TUKE_KBN = swk_tuke_kbn;
	}

	/**
	 * ���x���擾����
	 * 
	 * @return ���x
	 */
	public int getSWK_TUKIDO() {
		return sWK_TUKIDO;
	}

	/**
	 * ���x��ݒ肷��
	 * 
	 * @param swk_tukido
	 */
	public void setSWK_TUKIDO(int swk_tukido) {
		sWK_TUKIDO = swk_tukido;
	}

	/**
	 * ����ȖڃR�[�h���擾����
	 * 
	 * @return ����ȖڃR�[�h
	 */
	public String getSWK_UKM_CODE() {
		return sWK_UKM_CODE;
	}

	/**
	 * ����ȖڃR�[�h��ݒ肷��
	 * 
	 * @param swk_ukm_code
	 */
	public void setSWK_UKM_CODE(String swk_ukm_code) {
		sWK_UKM_CODE = swk_ukm_code;
	}

	/**
	 * ����Ȗږ��̂��擾����
	 * 
	 * @return ����Ȗږ���
	 */
	public String getSWK_UKM_NAME() {
		return sWK_UKM_NAME;
	}

	/**
	 * ����Ȗږ��̂�ݒ肷��
	 * 
	 * @param swk_ukm_name
	 */
	public void setSWK_UKM_NAME(String swk_ukm_name) {
		sWK_UKM_NAME = swk_ukm_name;
	}

	/**
	 * ����Ȗڗ��̂��擾����
	 * 
	 * @return ����Ȗڗ���
	 */
	public String getSWK_UKM_NAME_S() {
		return sWK_UKM_NAME_S;
	}

	/**
	 * ����Ȗڗ��̂�ݒ肷��
	 * 
	 * @param swk_ukm_name_s
	 */
	public void setSWK_UKM_NAME_S(String swk_ukm_name_s) {
		sWK_UKM_NAME_S = swk_ukm_name_s;
	}

	/**
	 * �ŃR�[�h���擾����
	 * 
	 * @return �ŃR�[�h
	 */
	public String getSWK_ZEI_CODE() {
		return sWK_ZEI_CODE;
	}

	/**
	 * �ŃR�[�h��ݒ肷��
	 * 
	 * @param swk_zei_code
	 */
	public void setSWK_ZEI_CODE(String swk_zei_code) {
		sWK_ZEI_CODE = swk_zei_code;
	}

	/**
	 * �ŋ敪���擾����
	 * 
	 * @return �ŋ敪
	 */
	public int getSWK_ZEI_KBN() {
		return sWK_ZEI_KBN;
	}

	/**
	 * �ŋ敪��ݒ肷��
	 * 
	 * @param swk_zei_kbn
	 */
	public void setSWK_ZEI_KBN(int swk_zei_kbn) {
		sWK_ZEI_KBN = swk_zei_kbn;
	}

	/**
	 * ����ŋ��z���擾����
	 * 
	 * @return ����ŋ��z
	 */
	public BigDecimal getSWK_ZEI_KIN() {
		return sWK_ZEI_KIN;
	}

	/**
	 * ����ŋ��z��ݒ肷��
	 * 
	 * @param swk_zei_kin
	 */
	public void setSWK_ZEI_KIN(BigDecimal swk_zei_kin) {
		sWK_ZEI_KIN = swk_zei_kin;
	}

	/**
	 * �ŗ����擾����
	 * 
	 * @return �ŗ�
	 */
	public BigDecimal getSWK_ZEI_RATE() {
		return sWK_ZEI_RATE;
	}

	/**
	 * �ŗ���ݒ肷��
	 * 
	 * @param swk_zei_rate
	 */
	public void setSWK_ZEI_RATE(BigDecimal swk_zei_rate) {
		sWK_ZEI_RATE = swk_zei_rate;
	}

	/**
	 * �X�V�敪���擾����
	 * 
	 * @return �X�V�敪
	 */
	public int getUPD_KBN() {
		return uPD_KBN;
	}

	/**
	 * �X�V�敪��ݒ肷��
	 * 
	 * @param upd_kbn
	 */
	public void setUPD_KBN(int upd_kbn) {
		uPD_KBN = upd_kbn;
	}

	/**
	 * ���[�U�[ID���擾����
	 * 
	 * @return ���[�U�[ID
	 */
	public String getUSR_ID() {
		return uSR_ID;
	}

	/**
	 * ���[�U�[ID��ݒ肷��
	 * 
	 * @param usr_id
	 */
	public void setUSR_ID(String usr_id) {
		uSR_ID = usr_id;
	}

	/**
	 * �`�[��� ��������
	 * 
	 * @return �`�[��� ��������
	 */
	public String getDEN_SYU_NAME_K() {
		return dEN_SYU_NAME_K;
	}

	/**
	 * �`�[��� ��������
	 * 
	 * @param den_syu_name_k
	 */
	public void setDEN_SYU_NAME_K(String den_syu_name_k) {
		dEN_SYU_NAME_K = den_syu_name_k;
	}

	/**
	 * �⏕�Ȗږ��́i���[�o�͗p�j���擾����
	 * 
	 * @return �⏕�Ȗږ��́i���[�o�͗p�j
	 */
	public String getCMP_HKM_NAME() {
		return cMP_HKM_NAME;
	}

	/**
	 * �⏕�Ȗږ��́i���[�o�͗p�j��ݒ肷��
	 * 
	 * @param cmp_hkm_name
	 */
	public void setCMP_HKM_NAME(String cmp_hkm_name) {
		cMP_HKM_NAME = cmp_hkm_name;
	}

	/**
	 * �Ȗږ��́i���[�o�͗p�j���擾����
	 * 
	 * @return �Ȗږ��́i���[�o�͗p�j
	 */
	public String getCMP_KMK_NAME() {
		return cMP_KMK_NAME;
	}

	/**
	 * �Ȗږ��́i���[�o�͗p�j��ݒ肷��
	 * 
	 * @param cmp_kmk_name
	 */
	public void setCMP_KMK_NAME(String cmp_kmk_name) {
		cMP_KMK_NAME = cmp_kmk_name;
	}

	/**
	 * ����Ȗږ��́i���[�o�͗p�j���擾����
	 * 
	 * @return ����Ȗږ��́i���[�o�͗p�j
	 */
	public String getCMP_UKM_NAME() {
		return cMP_UKM_NAME;
	}

	/**
	 * ����Ȗږ��́i���[�o�͗p�j��ݒ肷��
	 * 
	 * @param cmp_ukm_name
	 */
	public void setCMP_UKM_NAME(String cmp_ukm_name) {
		cMP_UKM_NAME = cmp_ukm_name;
	}

	/**
	 * �Ј����̂��擾����
	 * 
	 * @return �Ј�����
	 */
	public String getHDR_EMP_NAME() {
		return hDR_EMP_NAME;
	}

	/**
	 * �Ј����̂�ݒ肷��
	 * 
	 * @param hdr_emp_name
	 */
	public void setHDR_EMP_NAME(String hdr_emp_name) {
		hDR_EMP_NAME = hdr_emp_name;
	}

	/**
	 * �Ј����̂��擾����
	 * 
	 * @return �Ј�����
	 */
	public String getHDR_EMP_NAME_S() {
		return hDR_EMP_NAME_S;
	}

	/**
	 * �Ј����̂�ݒ肷��
	 * 
	 * @param hdr_emp_name_s
	 */
	public void setHDR_EMP_NAME_S(String hdr_emp_name_s) {
		hDR_EMP_NAME_S = hdr_emp_name_s;
	}

	/**
	 * ��s�����R�[�h ��s�R�[�h���擾����
	 * 
	 * @return ��s�����R�[�h ��s�R�[�h
	 */
	public String getHDR_CBK_BNK_CODE() {
		return HDR_CBK_BNK_CODE;
	}

	/**
	 * ��s�����R�[�h ��s�R�[�h��ݒ肷��
	 * 
	 * @param hdr_cbk_bnk_code
	 */
	public void setHDR_CBK_BNK_CODE(String hdr_cbk_bnk_code) {
		HDR_CBK_BNK_CODE = hdr_cbk_bnk_code;
	}

	/**
	 * ��s�����R�[�h �x�X�R�[�h���擾����
	 * 
	 * @return ��s�����R�[�h �x�X�R�[�h
	 */
	public String getHDR_CBK_STN_CODE() {
		return HDR_CBK_STN_CODE;
	}

	/**
	 * ��s�����R�[�h �x�X�R�[�h��ݒ肷��
	 * 
	 * @param hdr_cbk_stn_code
	 */
	public void setHDR_CBK_STN_CODE(String hdr_cbk_stn_code) {
		HDR_CBK_STN_CODE = hdr_cbk_stn_code;
	}

	/**
	 * �v�㕔�喼���擾����
	 * 
	 * @return �v�㕔�喼
	 */
	public String getHDR_DEP_NAME() {
		return hDR_DEP_NAME;
	}

	/**
	 * �v�㕔�喼��ݒ肷��
	 * 
	 * @param hdr_dep_name
	 */
	public void setHDR_DEP_NAME(String hdr_dep_name) {
		hDR_DEP_NAME = hdr_dep_name;
	}

	/**
	 * �v�㕔�嗪�̂��擾����
	 * 
	 * @return �v�㕔�嗪��
	 */
	public String getHDR_DEP_NAME_S() {
		return hDR_DEP_NAME_S;
	}

	/**
	 * �v�㕔�嗪�̂�ݒ肷��
	 * 
	 * @param hdr_dep_name_s
	 */
	public void setHDR_DEP_NAME_S(String hdr_dep_name_s) {
		hDR_DEP_NAME_S = hdr_dep_name_s;
	}

	/**
	 * �ʉ݃R�[�h �����_���擾����(�w�b�_�[)
	 * 
	 * @return �ʉ݃R�[�h �����_(�w�b�_�[)
	 */
	public String getHDR_CUR_DEC_KETA() {
		return hDR_CUR_DEC_KETA;
	}

	/**
	 * �ʉ݃R�[�h �����_��ݒ肷��(�w�b�_�[)
	 * 
	 * @param hdr_cur_dec_keta
	 */
	public void setHDR_CUR_DEC_KETA(String hdr_cur_dec_keta) {
		hDR_CUR_DEC_KETA = hdr_cur_dec_keta;
	}

	/**
	 * ���Z�ω������͋��z���擾����
	 * 
	 * @return ���Z�ω������͋��z
	 */
	public BigDecimal getHDR_SSY_KARI_IN_KIN() {
		return hDR_SSY_KARI_IN_KIN;
	}

	/**
	 * ���Z�ω������͋��z��ݒ肷��
	 * 
	 * @param hdr_ssy_kari_in_kin
	 */
	public void setHDR_SSY_KARI_IN_KIN(BigDecimal hdr_ssy_kari_in_kin) {
		hDR_SSY_KARI_IN_KIN = hdr_ssy_kari_in_kin;
	}

	/**
	 * ���Z�ω����M�݋��z���擾����B
	 * 
	 * @return ���Z�ω����M�݋��z
	 */
	public BigDecimal getHDR_SSY_KARI_KIN() {
		return hDR_SSY_KARI_KIN;
	}

	/**
	 * ���Z�ω����M�݋��z��ݒ肷��
	 * 
	 * @param hdr_ssy_kari_kin
	 */
	public void setHDR_SSY_KARI_KIN(BigDecimal hdr_ssy_kari_kin) {
		hDR_SSY_KARI_KIN = hdr_ssy_kari_kin;
	}

	/**
	 * ���� �v�㕔�嗪��
	 * 
	 * @return ���� �v�㕔�嗪��
	 */
	public String getSWK_DEP_NAME_S() {
		return sWK_DEP_NAME_S;
	}

	/**
	 * ���� �v�㕔�嗪��
	 * 
	 * @param swk_dep_name_s ���� �v�㕔�嗪��
	 */
	public void setSWK_DEP_NAME_S(String swk_dep_name_s) {
		sWK_DEP_NAME_S = swk_dep_name_s;
	}

	/**
	 * ���������擾
	 * 
	 * @return ������
	 */
	public Date getTMP_PAY_DATE() {
		return tMP_PAY_DATE;
	}

	/**
	 * ��������ݒ�
	 * 
	 * @param tmp_pay_date
	 */
	public void setTMP_PAY_DATE(Date tmp_pay_date) {
		tMP_PAY_DATE = tmp_pay_date;
	}

	/**
	 * �v���Ж���
	 * 
	 * @return sWK_K_KAI_NAME �v���Ж���
	 */
	public String getSWK_K_KAI_NAME() {
		return sWK_K_KAI_NAME;
	}

	/**
	 * �v���Ж���
	 * 
	 * @param swk_k_kai_name �v���Ж��̐ݒ肷��
	 */
	public void setSWK_K_KAI_NAME(String swk_k_kai_name) {
		sWK_K_KAI_NAME = swk_k_kai_name;
	}

	/**
	 * ����Ŗ���
	 * 
	 * @return sWK_ZEI_NAME ����Ŗ���
	 */
	public String getSWK_ZEI_NAME() {
		return sWK_ZEI_NAME;
	}

	/**
	 * ����Ŗ���
	 * 
	 * @param swk_zei_name ����Ŗ��̐ݒ肷��
	 */
	public void setSWK_ZEI_NAME(String swk_zei_name) {
		sWK_ZEI_NAME = swk_zei_name;
	}

}
