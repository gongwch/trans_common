package jp.co.ais.trans2.model.slip;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.aprvrole.*;
import jp.co.ais.trans2.model.bill.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.payment.*;
import jp.co.ais.trans2.model.remark.*;

/**
 * �`�[�w�b�_�[<br>
 * �S�Ă̓`�[�w�b�_�[�͓��Y�N���X���p������
 */
public class SWK_HDR extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String kAI_CODE;

	/** ��Ж� */
	protected String kAI_NAME = null;

	/** ��З��� */
	protected String kAI_NAME_S = null;

	/** �`�[���t */
	protected Date sWK_DEN_DATE;

	/** �`�[�ԍ� */
	protected String sWK_DEN_NO;

	/** IFRS�����敪 */
	protected int sWK_ADJ_KBN = AccountBook.BOTH.value;

	/** �؜ߔԍ� */
	protected String sWK_SEI_NO;

	/** �ȖڃR�[�h */
	protected String sWK_KMK_CODE;

	/** �Ȗږ��� */
	protected String sWK_KMK_NAME;

	/** �Ȗڗ��� */
	protected String sWK_KMK_NAME_S;

	/** �⏕�ȖڃR�[�h */
	protected String sWK_HKM_CODE;

	/** �⏕�Ȗږ��� */
	protected String sWK_HKM_NAME;

	/** �⏕�Ȗڗ��� */
	protected String sWK_HKM_NAME_S;

	/** ����ȖڃR�[�h */
	protected String sWK_UKM_CODE;

	/** ����Ȗږ��� */
	protected String sWK_UKM_NAME;

	/** ����Ȗڗ��� */
	protected String sWK_UKM_NAME_S;

	/** �v�㕔��R�[�h */
	protected String sWK_DEP_CODE;

	/** �v�㕔�喼�� */
	protected String sWK_DEP_NAME;

	/** �v�㕔�嗪�� */
	protected String sWK_DEP_NAME_S;

	/** �����R�[�h */
	protected String sWK_TRI_CODE;

	/** ����於�� */
	protected String sWK_TRI_NAME;

	/** ����旪�� */
	protected String sWK_TRI_NAME_S;

	/** �Ј��R�[�h */
	protected String sWK_EMP_CODE;

	/** �Ј����� */
	protected String sWK_EMP_NAME;

	/** �Ј����� */
	protected String sWK_EMP_NAME_S;

	/** ���o���M�݋��z */
	protected BigDecimal sWK_KIN;

	/** �f�[�^�敪 */
	protected String sWK_DATA_KBN;

	/** ��t����R�[�h */
	protected String sWK_UKE_DEP_CODE;

	/** ��t���喼�� */
	protected String sWK_UKE_DEP_NAME;

	/** ��t���嗪�� */
	protected String sWK_UKE_DEP_NAME_S;

	/** �`�[�E�v�R�[�h */
	protected String sWK_TEK_CODE;

	/** �`�[�E�v */
	protected String sWK_TEK;

	/** �����O�`�[�ԍ� */
	protected String sWK_BEFORE_DEN_NO;

	/** �X�V�敪 */
	protected int sWK_UPD_KBN;

	/** �X�V�O�̍X�V�敪 */
	protected int sWK_BEFORE_UPD_KBN;

	/** ���F�� */
	protected String sWK_SYO_EMP_CODE;

	/** ���F�Җ��� */
	protected String sWK_SYO_EMP_NAME;

	/** ���F�җ��� */
	protected String sWK_SYO_EMP_NAME_S;

	/** ���F�����O���[�v�R�[�h */
	protected String SWK_APRV_GRP_CODE;

	/** ���F�� */
	protected Date sWK_SYO_DATE;

	/** �˗��� */
	protected String sWK_IRAI_EMP_CODE;

	/** �˗��Җ��� */
	protected String sWK_IRAI_EMP_NAME;

	/** �˗��җ��� */
	protected String sWK_IRAI_EMP_NAME_S;

	/** �˗�����R�[�h */
	protected String sWK_IRAI_DEP_CODE;

	/** �˗����喼�� */
	protected String sWK_IRAI_DEP_NAME;

	/** �˗����嗪�� */
	protected String sWK_IRAI_DEP_NAME_S;

	/** �˗��� */
	protected Date sWK_IRAI_DATE;

	/** �r���t���O */
	protected int sWK_SHR_KBN;

	/** ���Z�敪 */
	protected int sWK_KSN_KBN;

	/** �o�^���t */
	protected Date iNP_DATE;

	/** �X�V���t */
	protected Date uPD_DATE;

	/** �v���O�����h�c */
	protected String pRG_ID;

	/** ���[�U�[�h�c */
	protected String uSR_ID;

	/** �ʉݺ��� */
	protected String sWK_CUR_CODE;

	/** �ʉݏ����_���� */
	protected int sWK_CUR_DEC_KETA;

	/** ڰ� */
	protected BigDecimal sWK_CUR_RATE;

	/** ���o�����͋��z */
	protected BigDecimal sWK_IN_KIN;

	/** ���ы敪 */
	protected String sWK_SYS_KBN;

	/** �`�[��� */
	protected String sWK_DEN_SYU;

	/** �`�[��ʖ��� */
	protected String sWK_DEN_SYU_NAME;

	/** �`�[��ʗ��� */
	protected String sWK_DEN_SYU_NAME_S;

	/** �`�[��ʌ������� */
	protected String sWK_DEN_SYU_NAME_K;

	/** ��Њԕt�֓`�[�敪 */
	protected int sWK_TUKE_KBN;

	/** �`�[�C���� */
	protected int sWK_UPD_CNT;

	// AP�s����--

	/** �x���敪 */
	protected String sWK_SIHA_KBN;

	/** �x���� */
	protected Date sWK_SIHA_DATE;

	/** �x�����@ */
	protected String sWK_HOH_CODE;

	/** �x�����@���� */
	protected String sWK_HOH_NAME;

	/** �x�������R�[�h */
	protected String sWK_HOH_NAI_CODE;

	/** �ۗ��敪 */
	protected int sWK_HORYU_KBN;

	/** �������z */
	protected BigDecimal sWK_KARI_KIN;

	/** �o����z */
	protected BigDecimal sWK_KEIHI_KIN;

	/** �x�����z(�M��) */
	protected BigDecimal sWK_SIHA_KIN;

	/** �����\���`�[�ԍ� */
	protected String sWK_KARI_DR_DEN_NO;

	/** �������Z�`�[�ԍ� */
	protected String sWK_KARI_CR_DEN_NO;

	/** �x�����ϋ敪 */
	protected int sWK_KESAI_KBN;

	/** �����v�㕔��R�[�h */
	protected String sWK_KARI_DEP_CODE;

	/** �����v�㕔�喼�� */
	protected String sWK_KARI_DEP_NAME;

	/** �����v�㕔�嗪�� */
	protected String sWK_KARI_DEP_NAME_S;

	/** �o�^���t */
	protected Date sWK_INP_DATE;

	/** ������������ */
	protected String sWK_TJK_CODE;

	/** ���������}�X�^�̔�d�������a����� */
	protected int sWK_TJK_YKN_KBN;

	/** ���������}�X�^�̔�d�������ԍ� */
	protected String sWK_TJK_YKN_NO;

	/** ���������}�X�^�̔�d���������`�J�i */
	protected String sWK_TJK_YKN_KANA;

	/** �p����s�� */
	protected String sWK_TJK_GS_BNK_NAME;

	/** �p���x�X�� */
	protected String sWK_TJK_GS_STN_NAME;

	/** �p����s�Z�� */
	protected String sWK_TJK_GS_BNK_ADR;

	/** ����������s��(�x����s��) */
	protected String sWK_TJK_BNK_NAME_S;

	/** ����������s������(�x����s�x�X��) */
	protected String sWK_TJK_BNK_STN_NAME_S;

	/** ���͉������z */
	protected BigDecimal sWK_IN_KARI_KIN;

	/** ���͌o����z */
	protected BigDecimal sWK_IN_KEIHI_KIN;

	/** ���͎x�����z */
	protected BigDecimal sWK_IN_SIHA_KIN;

	/** �������ʉݺ��� */
	protected String sWK_INV_CUR_CODE;

	/** �������ʉ�ڰ� */
	protected BigDecimal sWK_INV_CUR_RATE;

	/** �������ʉ݋��z */
	protected BigDecimal sWK_INV_KIN;

	/** ��s�������� */
	protected String sWK_CBK_CODE;

	/** ��s�������� */
	protected String sWK_CBK_NAME;

	/** �a����� */
	protected int sWK_CBK_YKN_KBN;

	/** �����ԍ� */
	protected String sWK_CBK_YKN_NO;

	/** �x�X�R�[�h */
	protected String sWK_CBK_STN_CODE;

	/** ��s�� */
	protected String sWK_BNK_NAME_S;

	/** �x�X�� */
	protected String sWK_BNK_STN_NAME_S;

	/** ���Z�\��� */
	protected Date sWK_SSY_DATE;

	/** ��tNo. */
	protected String sWK_UTK_NO;

	/** �����ʉݺ��� */
	protected String sWK_KARI_CUR_CODE;

	/** �����ʉ�ڰ� */
	protected BigDecimal sWK_KARI_CUR_RATE;

	// AR�s����--

	/** �����敪 */
	protected String sWK_SEI_KBN;

	/** �����\��� */
	protected Date sWK_AR_DATE;

	// �ǉ� --

	/** ��ʉ݃R�[�h */
	protected String kEY_CUR_CODE;

	/** ��ʉݏ����_���� */
	protected int kEY_CUR_DEC_KETA;

	/** �@�\�ʉ݃R�[�h */
	protected String fUNC_CUR_CODE;

	/** �@�\�ʉݏ����_���� */
	protected int fUNC_CUR_DEC_KETA;

	// PE�s����--

	/** �`��T�Z����`�[�ԍ� */
	protected String sWK_EST_CANCEL_DEN_NO;

	/** �`��T�Z�`�[�ԍ� */
	protected String sWK_BASE_EST_DEN_NO;

	// �G���e�B�e�B�n --

	/** ����}�X�^ */
	protected Department department = null;

	/** �Ȗڃ}�X�^ */
	protected Item item = null;

	/** �ʉ݃}�X�^ */
	protected Currency currency = null;

	/** �����}�X�^ */
	protected Customer customer = null;

	/** �x�����@�}�X�^ */
	protected PaymentMethod paymentMethod = null;

	/** ���������}�X�^ */
	protected PaymentSetting paymentSetting = null;

	/** ��s�����}�X�^ */
	protected BankAccount bankAccount = null;

	/** �����敪�}�X�^ */
	protected BillType billType = null;

	/** �Ј� */
	protected Employee employee = null;

	/** �E�v */
	protected Remark remark = null;

	/** �Y�t */
	protected List<SWK_ATTACH> attachments = null;

	/** �Y�t��񂪂��邩�ǂ��� true�F���� */
	protected boolean isExistAttachment = false;

	/** ���F�������[���O���[�v */
	protected AprvRoleGroup aprRoleGroup = null;

	/** ���[�N�t���[���F�R���g���[�� */
	protected SWK_SYO_CTL syoCtl = null;

	/**
	 * �R���X�g���N�^
	 */
	public SWK_HDR() {
		super();
	}

	/**
	 * �K�{���ڂ�NULL���܂܂�Ă��Ȃ����ǂ������`�F�b�N
	 * 
	 * @return true:null�A�܂��̓u�����N���܂܂��
	 */
	public String isReuiredItemNULL() {

		// ��ЃR�[�h KAI_CODE
		// �`�[���t SWK_DEN_DATE
		// �`�[�ԍ� SWK_DEN_NO
		// �f�[�^�敪 SWK_DATA_KBN
		// �`�[�E�v SWK_TEK
		// �X�V�敪 SWK_UPD_KBN
		// ���Z�敪 SWK_KSN_KBN
		Map<String, Object> notNullList = new TreeMap<String, Object>();
		notNullList.put("KAI_CODE", kAI_CODE);
		notNullList.put("SWK_DEN_DATE", sWK_DEN_DATE);
		notNullList.put("SWK_DEN_NO", sWK_DEN_NO);
		notNullList.put("SWK_TEK", sWK_TEK);
		notNullList.put("SWK_DATA_KBN", sWK_DATA_KBN);
		notNullList.put("SWK_DEN_SYU", sWK_DEN_SYU);

		for (Map.Entry<String, Object> entry : notNullList.entrySet()) {
			if (Util.isNullOrEmpty(entry.getValue())) {
				return entry.getKey();
			}
		}

		return "";
	}

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public SWK_HDR clone() {
		try {
			SWK_HDR hdr = (SWK_HDR) super.clone();
			hdr.attachments = this.attachments;
			return hdr;
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * �o�^���t
	 * 
	 * @return �o�^���t
	 */
	public Date getINP_DATE() {
		return iNP_DATE;
	}

	/**
	 * �o�^���t
	 * 
	 * @param inp_date �o�^���t
	 */
	public void setINP_DATE(Date inp_date) {
		iNP_DATE = inp_date;
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
	 * ��Ж��̎擾
	 * 
	 * @return kAI_NAME ��Ж�
	 */
	public String getKAI_NAME() {
		return kAI_NAME;
	}

	/**
	 * ��Ж��̐ݒ�
	 * 
	 * @param kAI_NAME ��Ж�
	 */
	public void setKAI_NAME(String kAI_NAME) {
		this.kAI_NAME = kAI_NAME;
	}

	/**
	 * ��З��̂̎擾
	 * 
	 * @return kAI_NAME_S ��З���
	 */
	public String getKAI_NAME_S() {
		return kAI_NAME_S;
	}

	/**
	 * ��З��̂̐ݒ�
	 * 
	 * @param kAI_NAME_S ��З���
	 */
	public void setKAI_NAME_S(String kAI_NAME_S) {
		this.kAI_NAME_S = kAI_NAME_S;
	}

	/**
	 * �v���O�����h�c
	 * 
	 * @return �v���O�����h�c
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * �v���O�����h�c
	 * 
	 * @param prg_id �v���O�����h�c
	 */
	public void setPRG_ID(String prg_id) {
		pRG_ID = prg_id;
	}

	/**
	 * �����O�`�[�ԍ�
	 * 
	 * @return �����O�`�[�ԍ�
	 */
	public String getSWK_BEFORE_DEN_NO() {
		return sWK_BEFORE_DEN_NO;
	}

	/**
	 * �����O�`�[�ԍ�
	 * 
	 * @param swk_before_den_no �����O�`�[�ԍ�
	 */
	public void setSWK_BEFORE_DEN_NO(String swk_before_den_no) {
		sWK_BEFORE_DEN_NO = swk_before_den_no;
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
	 * �f�[�^�敪
	 * 
	 * @return �f�[�^�敪
	 */
	public String getSWK_DATA_KBN() {
		return sWK_DATA_KBN;
	}

	/**
	 * �`�[�̎��
	 * 
	 * @return �`�[�̎��
	 */
	public SlipKind getSlipKind() {
		return SlipKind.get(sWK_DATA_KBN);
	}

	/**
	 * �f�[�^�敪
	 * 
	 * @param swk_data_kbn �f�[�^�敪
	 */
	public void setSWK_DATA_KBN(String swk_data_kbn) {
		sWK_DATA_KBN = swk_data_kbn;
	}

	/**
	 * �`�[���t
	 * 
	 * @return �`�[���t
	 */
	public Date getSWK_DEN_DATE() {
		return sWK_DEN_DATE;
	}

	/**
	 * �`�[���t
	 * 
	 * @param swk_den_date �`�[���t
	 */
	public void setSWK_DEN_DATE(Date swk_den_date) {
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
	 * ���o�����͋��z
	 * 
	 * @return ���o�����͋��z
	 */
	public BigDecimal getSWK_IN_KIN() {
		return sWK_IN_KIN;
	}

	/**
	 * ���o�����͋��z
	 * 
	 * @param swk_in_kin ���o�����͋��z
	 */
	public void setSWK_IN_KIN(BigDecimal swk_in_kin) {
		sWK_IN_KIN = swk_in_kin;
	}

	/**
	 * �˗���
	 * 
	 * @return �˗���
	 */
	public Date getSWK_IRAI_DATE() {
		return sWK_IRAI_DATE;
	}

	/**
	 * �˗���
	 * 
	 * @param swk_irai_date �˗���
	 */
	public void setSWK_IRAI_DATE(Date swk_irai_date) {
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
	 * ���o���M�݋��z
	 * 
	 * @return ���o���M�݋��z
	 */
	public BigDecimal getSWK_KIN() {
		return sWK_KIN;
	}

	/**
	 * ���o���M�݋��z
	 * 
	 * @param swk_kin ���o���M�݋��z
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
	 * �r���t���O
	 * 
	 * @return �r���t���O
	 */
	public int getSWK_SHR_KBN() {
		return sWK_SHR_KBN;
	}

	/**
	 * �r���t���O
	 * 
	 * @param swk_shr_kbn �r���t���O
	 */
	public void setSWK_SHR_KBN(int swk_shr_kbn) {
		sWK_SHR_KBN = swk_shr_kbn;
	}

	/**
	 * ���F��
	 * 
	 * @return ���F��
	 */
	public Date getSWK_SYO_DATE() {
		return sWK_SYO_DATE;
	}

	/**
	 * ���F��
	 * 
	 * @param swk_syo_date ���F��
	 */
	public void setSWK_SYO_DATE(Date swk_syo_date) {
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
	 * �`�[�C����
	 * 
	 * @return �`�[�C����
	 */
	public int getSWK_UPD_CNT() {
		return sWK_UPD_CNT;
	}

	/**
	 * �`�[�C����
	 * 
	 * @param swk_upd_cnt �`�[�C����
	 */
	public void setSWK_UPD_CNT(int swk_upd_cnt) {
		sWK_UPD_CNT = swk_upd_cnt;
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
	 * �X�V�敪���擾����
	 * 
	 * @return �X�V�敪Enum
	 */
	public SlipState getSlipState() {
		return SlipState.getSlipState(sWK_UPD_KBN);
	}

	/**
	 * �X�V�敪��ݒ肷��
	 * 
	 * @param state �X�V�敪Enum
	 */
	public void setSlipState(SlipState state) {
		this.sWK_UPD_KBN = state.value;
	}

	/**
	 * �X�V�ύX�O�̍X�V�敪�擾
	 * 
	 * @return �X�V�敪
	 */
	public int getSWK_BEFORE_UPD_KBN() {
		return sWK_BEFORE_UPD_KBN;
	}

	/**
	 * �X�V�ύX�O�̍X�V�敪�ݒ�
	 * 
	 * @param swk_upd_kbn_before �X�V�敪
	 */
	public void setSWK_BEFORE_UPD_KBN(int swk_upd_kbn_before) {
		sWK_BEFORE_UPD_KBN = swk_upd_kbn_before;
	}

	/**
	 * �X�V���t
	 * 
	 * @return �X�V���t
	 */
	public Date getUPD_DATE() {
		return uPD_DATE;
	}

	/**
	 * �X�V���t
	 * 
	 * @param upd_date �X�V���t
	 */
	public void setUPD_DATE(Date upd_date) {
		uPD_DATE = upd_date;
	}

	/**
	 * ���[�U�[�h�c
	 * 
	 * @return ���[�U�[�h�c
	 */
	public String getUSR_ID() {
		return uSR_ID;
	}

	/**
	 * ���[�U�[�h�c
	 * 
	 * @param usr_id ���[�U�[�h�c
	 */
	public void setUSR_ID(String usr_id) {
		uSR_ID = usr_id;
	}

	/**
	 * �����\���
	 * 
	 * @return �����\���
	 */
	public Date getSWK_AR_DATE() {
		return sWK_AR_DATE;
	}

	/**
	 * �����\���
	 * 
	 * @param swk_ar_date �����\���
	 */
	public void setSWK_AR_DATE(Date swk_ar_date) {
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
	 * ���͉������z
	 * 
	 * @return ���͉������z
	 */
	public BigDecimal getSWK_IN_KARI_KIN() {
		return sWK_IN_KARI_KIN;
	}

	/**
	 * ���͉������z
	 * 
	 * @param swk_in_kari_kin ���͉������z
	 */
	public void setSWK_IN_KARI_KIN(BigDecimal swk_in_kari_kin) {
		sWK_IN_KARI_KIN = swk_in_kari_kin;
	}

	/**
	 * ���͌o����z
	 * 
	 * @return ���͌o����z
	 */
	public BigDecimal getSWK_IN_KEIHI_KIN() {
		return sWK_IN_KEIHI_KIN;
	}

	/**
	 * ���͌o����z
	 * 
	 * @param swk_in_keihi_kin ���͌o����z
	 */
	public void setSWK_IN_KEIHI_KIN(BigDecimal swk_in_keihi_kin) {
		sWK_IN_KEIHI_KIN = swk_in_keihi_kin;
	}

	/**
	 * ���͎x�����z
	 * 
	 * @return ���͎x�����z
	 */
	public BigDecimal getSWK_IN_SIHA_KIN() {
		return sWK_IN_SIHA_KIN;
	}

	/**
	 * ���͎x�����z
	 * 
	 * @param swk_in_siha_kin ���͎x�����z
	 */
	public void setSWK_IN_SIHA_KIN(BigDecimal swk_in_siha_kin) {
		sWK_IN_SIHA_KIN = swk_in_siha_kin;
	}

	/**
	 * �o�^���t
	 * 
	 * @return �o�^���t
	 */
	public Date getSWK_INP_DATE() {
		return sWK_INP_DATE;
	}

	/**
	 * �o�^���t
	 * 
	 * @param swk_inp_date �o�^���t
	 */
	public void setSWK_INP_DATE(Date swk_inp_date) {
		sWK_INP_DATE = swk_inp_date;
	}

	/**
	 * �������ʉݺ���
	 * 
	 * @return �������ʉݺ���
	 */
	public String getSWK_INV_CUR_CODE() {
		return sWK_INV_CUR_CODE;
	}

	/**
	 * �������ʉݺ���
	 * 
	 * @param swk_inv_cur_code �������ʉݺ���
	 */
	public void setSWK_INV_CUR_CODE(String swk_inv_cur_code) {
		sWK_INV_CUR_CODE = swk_inv_cur_code;
	}

	/**
	 * �������ʉ�ڰ�
	 * 
	 * @return �������ʉ�ڰ�
	 */
	public BigDecimal getSWK_INV_CUR_RATE() {
		return sWK_INV_CUR_RATE;
	}

	/**
	 * �������ʉ�ڰ�
	 * 
	 * @param swk_inv_cur_rate �������ʉ�ڰ�
	 */
	public void setSWK_INV_CUR_RATE(BigDecimal swk_inv_cur_rate) {
		sWK_INV_CUR_RATE = swk_inv_cur_rate;
	}

	/**
	 * �������ʉ݋��z
	 * 
	 * @return �������ʉ݋��z
	 */
	public BigDecimal getSWK_INV_KIN() {
		return sWK_INV_KIN;
	}

	/**
	 * �������ʉ݋��z
	 * 
	 * @param swk_inv_kin �������ʉ݋��z
	 */
	public void setSWK_INV_KIN(BigDecimal swk_inv_kin) {
		sWK_INV_KIN = swk_inv_kin;
	}

	/**
	 * �������Z�`�[�ԍ�
	 * 
	 * @return �������Z�`�[�ԍ�
	 */
	public String getSWK_KARI_CR_DEN_NO() {
		return sWK_KARI_CR_DEN_NO;
	}

	/**
	 * �������Z�`�[�ԍ�
	 * 
	 * @param swk_kari_cr_den_no �������Z�`�[�ԍ�
	 */
	public void setSWK_KARI_CR_DEN_NO(String swk_kari_cr_den_no) {
		sWK_KARI_CR_DEN_NO = swk_kari_cr_den_no;
	}

	/**
	 * �����ʉݺ���
	 * 
	 * @return �����ʉݺ���
	 */
	public String getSWK_KARI_CUR_CODE() {
		return sWK_KARI_CUR_CODE;
	}

	/**
	 * �����ʉݺ���
	 * 
	 * @param swk_kari_cur_code �����ʉݺ���
	 */
	public void setSWK_KARI_CUR_CODE(String swk_kari_cur_code) {
		sWK_KARI_CUR_CODE = swk_kari_cur_code;
	}

	/**
	 * �����ʉ�ڰ�
	 * 
	 * @return �����ʉ�ڰ�
	 */
	public BigDecimal getSWK_KARI_CUR_RATE() {
		return sWK_KARI_CUR_RATE;
	}

	/**
	 * �����ʉ�ڰ�
	 * 
	 * @param swk_kari_cur_rate �����ʉ�ڰ�
	 */
	public void setSWK_KARI_CUR_RATE(BigDecimal swk_kari_cur_rate) {
		sWK_KARI_CUR_RATE = swk_kari_cur_rate;
	}

	/**
	 * �����v�㕔��R�[�h
	 * 
	 * @return �����v�㕔��R�[�h
	 */
	public String getSWK_KARI_DEP_CODE() {
		return sWK_KARI_DEP_CODE;
	}

	/**
	 * �����v�㕔��R�[�h
	 * 
	 * @param swk_kari_dep_code �����v�㕔��R�[�h
	 */
	public void setSWK_KARI_DEP_CODE(String swk_kari_dep_code) {
		sWK_KARI_DEP_CODE = swk_kari_dep_code;
	}

	/**
	 * �����\���`�[�ԍ�
	 * 
	 * @return �����\���`�[�ԍ�
	 */
	public String getSWK_KARI_DR_DEN_NO() {
		return sWK_KARI_DR_DEN_NO;
	}

	/**
	 * �����\���`�[�ԍ�
	 * 
	 * @param swk_kari_dr_den_no �����\���`�[�ԍ�
	 */
	public void setSWK_KARI_DR_DEN_NO(String swk_kari_dr_den_no) {
		sWK_KARI_DR_DEN_NO = swk_kari_dr_den_no;
	}

	/**
	 * �������z
	 * 
	 * @return �������z
	 */
	public BigDecimal getSWK_KARI_KIN() {
		return sWK_KARI_KIN;
	}

	/**
	 * �������z
	 * 
	 * @param swk_kari_kin �������z
	 */
	public void setSWK_KARI_KIN(BigDecimal swk_kari_kin) {
		sWK_KARI_KIN = swk_kari_kin;
	}

	/**
	 * �o����z
	 * 
	 * @return �o����z
	 */
	public BigDecimal getSWK_KEIHI_KIN() {
		return sWK_KEIHI_KIN;
	}

	/**
	 * �o����z
	 * 
	 * @param swk_keihi_kin �o����z
	 */
	public void setSWK_KEIHI_KIN(BigDecimal swk_keihi_kin) {
		sWK_KEIHI_KIN = swk_keihi_kin;
	}

	/**
	 * �x�����ϋ敪
	 * 
	 * @return �x�����ϋ敪
	 */
	public int getSWK_KESAI_KBN() {
		return sWK_KESAI_KBN;
	}

	/**
	 * �x�����ϋ敪
	 * 
	 * @param swk_kesai_kbn �x�����ϋ敪
	 */
	public void setSWK_KESAI_KBN(int swk_kesai_kbn) {
		sWK_KESAI_KBN = swk_kesai_kbn;
	}

	/**
	 * �����敪
	 * 
	 * @return �����敪
	 */
	public String getSWK_SEI_KBN() {
		return sWK_SEI_KBN;
	}

	/**
	 * �����敪
	 * 
	 * @param swk_sei_kbn �����敪
	 */
	public void setSWK_SEI_KBN(String swk_sei_kbn) {
		sWK_SEI_KBN = swk_sei_kbn;
	}

	/**
	 * �x����
	 * 
	 * @return �x����
	 */
	public Date getSWK_SIHA_DATE() {
		return sWK_SIHA_DATE;
	}

	/**
	 * �x����
	 * 
	 * @param swk_siha_date �x����
	 */
	public void setSWK_SIHA_DATE(Date swk_siha_date) {
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
	 * �x�����z(�M��)
	 * 
	 * @return �x�����z(�M��)
	 */
	public BigDecimal getSWK_SIHA_KIN() {
		return sWK_SIHA_KIN;
	}

	/**
	 * �x�����z(�M��)
	 * 
	 * @param swk_siha_kin �x�����z(�M��)
	 */
	public void setSWK_SIHA_KIN(BigDecimal swk_siha_kin) {
		sWK_SIHA_KIN = swk_siha_kin;
	}

	/**
	 * ���Z�\���
	 * 
	 * @return ���Z�\���
	 */
	public Date getSWK_SSY_DATE() {
		return sWK_SSY_DATE;
	}

	/**
	 * ���Z�\���
	 * 
	 * @param swk_ssy_date ���Z�\���
	 */
	public void setSWK_SSY_DATE(Date swk_ssy_date) {
		sWK_SSY_DATE = swk_ssy_date;
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
	 * ��tNo.
	 * 
	 * @return ��tNo.
	 */
	public String getSWK_UTK_NO() {
		return sWK_UTK_NO;
	}

	/**
	 * ��tNo.
	 * 
	 * @param swk_utk_no ��tNo.
	 */
	public void setSWK_UTK_NO(String swk_utk_no) {
		sWK_UTK_NO = swk_utk_no;
	}

	/**
	 * �v�㕔�喼��
	 * 
	 * @return �v�㕔�喼��
	 */
	public String getSWK_DEP_NAME() {
		return sWK_DEP_NAME;
	}

	/**
	 * �v�㕔�喼��
	 * 
	 * @param swk_dep_name �v�㕔�喼��
	 */
	public void setSWK_DEP_NAME(String swk_dep_name) {
		sWK_DEP_NAME = swk_dep_name;
	}

	/**
	 * �v�㕔�嗪��
	 * 
	 * @return �v�㕔�嗪��
	 */
	public String getSWK_DEP_NAME_S() {
		return sWK_DEP_NAME_S;
	}

	/**
	 * �v�㕔�嗪��
	 * 
	 * @param swk_dep_name_s �v�㕔�嗪��
	 */
	public void setSWK_DEP_NAME_S(String swk_dep_name_s) {
		sWK_DEP_NAME_S = swk_dep_name_s;
	}

	/**
	 * �Ј�����
	 * 
	 * @return �Ј�����
	 */
	public String getSWK_EMP_NAME() {
		return sWK_EMP_NAME;
	}

	/**
	 * �Ј�����
	 * 
	 * @param swk_emp_name �Ј�����
	 */
	public void setSWK_EMP_NAME(String swk_emp_name) {
		sWK_EMP_NAME = swk_emp_name;
	}

	/**
	 * �Ј�����
	 * 
	 * @return �Ј�����
	 */
	public String getSWK_EMP_NAME_S() {
		return sWK_EMP_NAME_S;
	}

	/**
	 * �Ј�����
	 * 
	 * @param swk_emp_name_s �Ј�����
	 */
	public void setSWK_EMP_NAME_S(String swk_emp_name_s) {
		sWK_EMP_NAME_S = swk_emp_name_s;
	}

	/**
	 * �⏕�Ȗږ���
	 * 
	 * @return �⏕�Ȗږ���
	 */
	public String getSWK_HKM_NAME() {
		return sWK_HKM_NAME;
	}

	/**
	 * �⏕�Ȗږ���
	 * 
	 * @param swk_hkm_name �⏕�Ȗږ���
	 */
	public void setSWK_HKM_NAME(String swk_hkm_name) {
		sWK_HKM_NAME = swk_hkm_name;
	}

	/**
	 * �⏕�Ȗڗ���
	 * 
	 * @return �⏕�Ȗڗ���
	 */
	public String getSWK_HKM_NAME_S() {
		return sWK_HKM_NAME_S;
	}

	/**
	 * �⏕�Ȗڗ���
	 * 
	 * @param swk_hkm_name_s �⏕�Ȗڗ���
	 */
	public void setSWK_HKM_NAME_S(String swk_hkm_name_s) {
		sWK_HKM_NAME_S = swk_hkm_name_s;
	}

	/**
	 * �˗����喼��
	 * 
	 * @return �˗����喼��
	 */
	public String getSWK_IRAI_DEP_NAME() {
		return sWK_IRAI_DEP_NAME;
	}

	/**
	 * �˗����喼��
	 * 
	 * @param swk_irai_dep_name �˗����喼��
	 */
	public void setSWK_IRAI_DEP_NAME(String swk_irai_dep_name) {
		sWK_IRAI_DEP_NAME = swk_irai_dep_name;
	}

	/**
	 * �˗����嗪��
	 * 
	 * @return �˗����嗪��
	 */
	public String getSWK_IRAI_DEP_NAME_S() {
		return sWK_IRAI_DEP_NAME_S;
	}

	/**
	 * �˗����嗪��
	 * 
	 * @param swk_irai_dep_name_s �˗����嗪��
	 */
	public void setSWK_IRAI_DEP_NAME_S(String swk_irai_dep_name_s) {
		sWK_IRAI_DEP_NAME_S = swk_irai_dep_name_s;
	}

	/**
	 * �˗��Җ���
	 * 
	 * @return �˗��Җ���
	 */
	public String getSWK_IRAI_EMP_NAME() {
		return sWK_IRAI_EMP_NAME;
	}

	/**
	 * �˗��Җ���
	 * 
	 * @param swk_irai_emp_name �˗��Җ���
	 */
	public void setSWK_IRAI_EMP_NAME(String swk_irai_emp_name) {
		sWK_IRAI_EMP_NAME = swk_irai_emp_name;
	}

	/**
	 * �˗��җ���
	 * 
	 * @return �˗��җ���
	 */
	public String getSWK_IRAI_EMP_NAME_S() {
		return sWK_IRAI_EMP_NAME_S;
	}

	/**
	 * �˗��җ���
	 * 
	 * @param swk_irai_emp_name_s �˗��җ���
	 */
	public void setSWK_IRAI_EMP_NAME_S(String swk_irai_emp_name_s) {
		sWK_IRAI_EMP_NAME_S = swk_irai_emp_name_s;
	}

	/**
	 * �����v�㕔�喼��
	 * 
	 * @return �����v�㕔�喼��
	 */
	public String getSWK_KARI_DEP_NAME() {
		return sWK_KARI_DEP_NAME;
	}

	/**
	 * �����v�㕔�喼��
	 * 
	 * @param swk_kari_dep_name �����v�㕔�喼��
	 */
	public void setSWK_KARI_DEP_NAME(String swk_kari_dep_name) {
		sWK_KARI_DEP_NAME = swk_kari_dep_name;
	}

	/**
	 * �����v�㕔�嗪��
	 * 
	 * @return �����v�㕔�嗪��
	 */
	public String getSWK_KARI_DEP_NAME_S() {
		return sWK_KARI_DEP_NAME_S;
	}

	/**
	 * �����v�㕔�嗪��
	 * 
	 * @param swk_kari_dep_name_s �����v�㕔�嗪��
	 */
	public void setSWK_KARI_DEP_NAME_S(String swk_kari_dep_name_s) {
		sWK_KARI_DEP_NAME_S = swk_kari_dep_name_s;
	}

	/**
	 * �Ȗږ���
	 * 
	 * @return �Ȗږ���
	 */
	public String getSWK_KMK_NAME() {
		return sWK_KMK_NAME;
	}

	/**
	 * �Ȗږ���
	 * 
	 * @param swk_kmk_name �Ȗږ���
	 */
	public void setSWK_KMK_NAME(String swk_kmk_name) {
		sWK_KMK_NAME = swk_kmk_name;
	}

	/**
	 * �Ȗڗ���
	 * 
	 * @return �Ȗڗ���
	 */
	public String getSWK_KMK_NAME_S() {
		return sWK_KMK_NAME_S;
	}

	/**
	 * �Ȗڗ���
	 * 
	 * @param swk_kmk_name_s �Ȗڗ���
	 */
	public void setSWK_KMK_NAME_S(String swk_kmk_name_s) {
		sWK_KMK_NAME_S = swk_kmk_name_s;
	}

	/**
	 * ���F�Җ���
	 * 
	 * @return ���F�Җ���
	 */
	public String getSWK_SYO_EMP_NAME() {
		return sWK_SYO_EMP_NAME;
	}

	/**
	 * ���F�Җ���
	 * 
	 * @param swk_syo_emp_name ���F�Җ���
	 */
	public void setSWK_SYO_EMP_NAME(String swk_syo_emp_name) {
		sWK_SYO_EMP_NAME = swk_syo_emp_name;
	}

	/**
	 * ���F�җ���
	 * 
	 * @return ���F�җ���
	 */
	public String getSWK_SYO_EMP_NAME_S() {
		return sWK_SYO_EMP_NAME_S;
	}

	/**
	 * ���F�җ���
	 * 
	 * @param swk_syo_emp_name_s ���F�җ���
	 */
	public void setSWK_SYO_EMP_NAME_S(String swk_syo_emp_name_s) {
		sWK_SYO_EMP_NAME_S = swk_syo_emp_name_s;
	}

	/**
	 * ���F�����O���[�v�R�[�h���擾
	 * 
	 * @return sWK_APRV_GRP_CODE
	 */
	public String getSWK_APRV_GRP_CODE() {
		return SWK_APRV_GRP_CODE;
	}

	/**
	 * ���F�����O���[�v�R�[�h���Z�b�g����
	 * 
	 * @param sWK_APRV_GRP_CODE ���F�����O���[�v�R�[�h
	 */
	public void setSWK_APRV_GRP_CODE(String sWK_APRV_GRP_CODE) {
		SWK_APRV_GRP_CODE = sWK_APRV_GRP_CODE;
	}

	/**
	 * ����於��
	 * 
	 * @return ����於��
	 */
	public String getSWK_TRI_NAME() {
		return sWK_TRI_NAME;
	}

	/**
	 * ����於��
	 * 
	 * @param swk_tri_name ����於��
	 */
	public void setSWK_TRI_NAME(String swk_tri_name) {
		sWK_TRI_NAME = swk_tri_name;
	}

	/**
	 * ����旪��
	 * 
	 * @return ����旪��
	 */
	public String getSWK_TRI_NAME_S() {
		return sWK_TRI_NAME_S;
	}

	/**
	 * ����旪��
	 * 
	 * @param swk_tri_name_s ����旪��
	 */
	public void setSWK_TRI_NAME_S(String swk_tri_name_s) {
		sWK_TRI_NAME_S = swk_tri_name_s;
	}

	/**
	 * ����Ȗږ���
	 * 
	 * @return ����Ȗږ���
	 */
	public String getSWK_UKM_NAME() {
		return sWK_UKM_NAME;
	}

	/**
	 * ����Ȗږ���
	 * 
	 * @param swk_ukm_name ����Ȗږ���
	 */
	public void setSWK_UKM_NAME(String swk_ukm_name) {
		sWK_UKM_NAME = swk_ukm_name;
	}

	/**
	 * ����Ȗڗ���
	 * 
	 * @return ����Ȗڗ���
	 */
	public String getSWK_UKM_NAME_S() {
		return sWK_UKM_NAME_S;
	}

	/**
	 * ����Ȗڗ���
	 * 
	 * @param swk_ukm_name_s ����Ȗڗ���
	 */
	public void setSWK_UKM_NAME_S(String swk_ukm_name_s) {
		sWK_UKM_NAME_S = swk_ukm_name_s;
	}

	/**
	 * ��s��������
	 * 
	 * @return ��s��������
	 */
	public String getSWK_CBK_NAME() {
		return sWK_CBK_NAME;
	}

	/**
	 * ��s��������
	 * 
	 * @param swk_cbk_name ��s��������
	 */
	public void setSWK_CBK_NAME(String swk_cbk_name) {
		sWK_CBK_NAME = swk_cbk_name;
	}

	/**
	 * �a�����
	 * 
	 * @return �a�����
	 */
	public int getSWK_CBK_YKN_KBN() {
		return sWK_CBK_YKN_KBN;
	}

	/**
	 * �a�����
	 * 
	 * @param swk_cbk_ykn_kbn �a�����
	 */
	public void setSWK_CBK_YKN_KBN(int swk_cbk_ykn_kbn) {
		sWK_CBK_YKN_KBN = swk_cbk_ykn_kbn;
	}

	/**
	 * �����ԍ�
	 * 
	 * @return �����ԍ�
	 */
	public String getSWK_CBK_YKN_NO() {
		return sWK_CBK_YKN_NO;
	}

	/**
	 * �����ԍ�
	 * 
	 * @param swk_cbk_ykn_no �����ԍ�
	 */
	public void setSWK_CBK_YKN_NO(String swk_cbk_ykn_no) {
		sWK_CBK_YKN_NO = swk_cbk_ykn_no;
	}

	/**
	 * �x�X�R�[�h
	 * 
	 * @return �x�X�R�[�h
	 */
	public String getSWK_CBK_STN_CODE() {
		return sWK_CBK_STN_CODE;
	}

	/**
	 * �x�X�R�[�h
	 * 
	 * @param swk_cbk_stn_code �x�X�R�[�h
	 */
	public void setSWK_CBK_STN_CODE(String swk_cbk_stn_code) {
		sWK_CBK_STN_CODE = swk_cbk_stn_code;
	}

	/**
	 * ��t���喼��
	 * 
	 * @return ��t���喼��
	 */
	public String getSWK_UKE_DEP_NAME() {
		return sWK_UKE_DEP_NAME;
	}

	/**
	 * ��t���喼��
	 * 
	 * @param swk_uke_dep_name ��t���喼��
	 */
	public void setSWK_UKE_DEP_NAME(String swk_uke_dep_name) {
		sWK_UKE_DEP_NAME = swk_uke_dep_name;
	}

	/**
	 * ��t���嗪��
	 * 
	 * @return ��t���嗪��
	 */
	public String getSWK_UKE_DEP_NAME_S() {
		return sWK_UKE_DEP_NAME_S;
	}

	/**
	 * ��t���嗪��
	 * 
	 * @param swk_uke_dep_name_s ��t���嗪��
	 */
	public void setSWK_UKE_DEP_NAME_S(String swk_uke_dep_name_s) {
		sWK_UKE_DEP_NAME_S = swk_uke_dep_name_s;
	}

	/**
	 * �`�[��ʖ���
	 * 
	 * @return �`�[��ʖ���
	 */
	public String getSWK_DEN_SYU_NAME() {
		return sWK_DEN_SYU_NAME;
	}

	/**
	 * �`�[��ʖ���
	 * 
	 * @param swk_den_syu_name �`�[��ʖ���
	 */
	public void setSWK_DEN_SYU_NAME(String swk_den_syu_name) {
		sWK_DEN_SYU_NAME = swk_den_syu_name;
	}

	/**
	 * �`�[��ʗ���
	 * 
	 * @return �`�[��ʗ���
	 */
	public String getSWK_DEN_SYU_NAME_S() {
		return sWK_DEN_SYU_NAME_S;
	}

	/**
	 * �`�[��ʗ���
	 * 
	 * @param swk_den_syu_name_s �`�[��ʗ���
	 */
	public void setSWK_DEN_SYU_NAME_S(String swk_den_syu_name_s) {
		sWK_DEN_SYU_NAME_S = swk_den_syu_name_s;
	}

	/**
	 * �x�����@����
	 * 
	 * @return �x�����@����
	 */
	public String getSWK_HOH_NAME() {
		return sWK_HOH_NAME;
	}

	/**
	 * �x�����@����
	 * 
	 * @param swk_hoh_name �x�����@����
	 */
	public void setSWK_HOH_NAME(String swk_hoh_name) {
		sWK_HOH_NAME = swk_hoh_name;
	}

	/**
	 * �x�������R�[�h�̎擾
	 * 
	 * @return sWK_HOH_NAI_CODE �x�������R�[�h
	 */
	public String getSWK_HOH_NAI_CODE() {
		return sWK_HOH_NAI_CODE;
	}

	/**
	 * �x�������R�[�h�̐ݒ�
	 * 
	 * @param sWK_HOH_NAI_CODE �x�������R�[�h
	 */
	public void setSWK_HOH_NAI_CODE(String sWK_HOH_NAI_CODE) {
		this.sWK_HOH_NAI_CODE = sWK_HOH_NAI_CODE;
	}

	/**
	 * IFRS�����敪
	 * 
	 * @return IFRS�����敪
	 */
	public int getSWK_ADJ_KBN() {
		return sWK_ADJ_KBN;
	}

	/**
	 * IFRS�����敪
	 * 
	 * @param swk_adj_kbn IFRS�����敪
	 */
	public void setSWK_ADJ_KBN(int swk_adj_kbn) {
		sWK_ADJ_KBN = swk_adj_kbn;
	}

	/**
	 * ��s��
	 * 
	 * @return ��s��
	 */
	public String getSWK_BNK_NAME_S() {
		return sWK_BNK_NAME_S;
	}

	/**
	 * ��s��
	 * 
	 * @param swk_bnk_name_s ��s��
	 */
	public void setSWK_BNK_NAME_S(String swk_bnk_name_s) {
		sWK_BNK_NAME_S = swk_bnk_name_s;
	}

	/**
	 * �x�X�����擾����B
	 * 
	 * @return �x�X��
	 */
	public String getSWK_BNK_STN_NAME_S() {
		return sWK_BNK_STN_NAME_S;
	}

	/**
	 * �x�X����ݒ肷��B
	 * 
	 * @param swk_bnk_stn_name_s �x�X��
	 */
	public void setSWK_BNK_STN_NAME_S(String swk_bnk_stn_name_s) {
		sWK_BNK_STN_NAME_S = swk_bnk_stn_name_s;
	}

	/**
	 * �ʉݏ����_�������擾����B
	 * 
	 * @return �ʉݏ����_����
	 */
	public int getSWK_CUR_DEC_KETA() {
		return sWK_CUR_DEC_KETA;
	}

	/**
	 * �ʉݏ����_������ݒ肷��B
	 * 
	 * @param swk_cur_dec_keta �ʉݏ����_����
	 */
	public void setSWK_CUR_DEC_KETA(int swk_cur_dec_keta) {
		sWK_CUR_DEC_KETA = swk_cur_dec_keta;
	}

	/**
	 * �p����s���̎擾
	 * 
	 * @return sWK_TJK_GS_BNK_NAME �p����s��
	 */
	public String getSWK_TJK_GS_BNK_NAME() {
		return sWK_TJK_GS_BNK_NAME;
	}

	/**
	 * �p����s���̐ݒ�
	 * 
	 * @param sWK_TJK_GS_BNK_NAME �p����s��
	 */
	public void setSWK_TJK_GS_BNK_NAME(String sWK_TJK_GS_BNK_NAME) {
		this.sWK_TJK_GS_BNK_NAME = sWK_TJK_GS_BNK_NAME;
	}

	/**
	 * �p���x�X���̎擾
	 * 
	 * @return sWK_TJK_GS_STN_NAME �p���x�X��
	 */
	public String getSWK_TJK_GS_STN_NAME() {
		return sWK_TJK_GS_STN_NAME;
	}

	/**
	 * �p���x�X���̐ݒ�
	 * 
	 * @param sWK_TJK_GS_STN_NAME �p���x�X��
	 */
	public void setSWK_TJK_GS_STN_NAME(String sWK_TJK_GS_STN_NAME) {
		this.sWK_TJK_GS_STN_NAME = sWK_TJK_GS_STN_NAME;
	}

	/**
	 * ����������s��(�x����s��)
	 * 
	 * @return ����������s��(�x����s��)
	 */
	public String getSWK_TJK_BNK_NAME_S() {
		return sWK_TJK_BNK_NAME_S;
	}

	/**
	 * ����������s��(�x����s��)
	 * 
	 * @param swk_tjk_bnk_name_s ����������s��(�x����s��)
	 */
	public void setSWK_TJK_BNK_NAME_S(String swk_tjk_bnk_name_s) {
		sWK_TJK_BNK_NAME_S = swk_tjk_bnk_name_s;
	}

	/**
	 * ����������s������(�x����s�x�X��)
	 * 
	 * @return ����������s������(�x����s�x�X��)
	 */
	public String getSWK_TJK_BNK_STN_NAME_S() {
		return sWK_TJK_BNK_STN_NAME_S;
	}

	/**
	 * ����������s������(�x����s�x�X��)
	 * 
	 * @param swk_tjk_bnk_stn_name_s ����������s������(�x����s�x�X��)
	 */
	public void setSWK_TJK_BNK_STN_NAME_S(String swk_tjk_bnk_stn_name_s) {
		sWK_TJK_BNK_STN_NAME_S = swk_tjk_bnk_stn_name_s;
	}

	/**
	 * �`�[��ʌ�������
	 * 
	 * @return �`�[��ʌ�������
	 */
	public String getSWK_DEN_SYU_NAME_K() {
		return sWK_DEN_SYU_NAME_K;
	}

	/**
	 * �`�[��ʌ�������
	 * 
	 * @param swk_den_syu_name_k �`�[��ʌ�������
	 */
	public void setSWK_DEN_SYU_NAME_K(String swk_den_syu_name_k) {
		sWK_DEN_SYU_NAME_K = swk_den_syu_name_k;
	}

	/**
	 * �@�\�ʉ݃R�[�h
	 * 
	 * @return �@�\�ʉ݃R�[�h
	 */
	public String getFUNC_CUR_CODE() {
		return fUNC_CUR_CODE;
	}

	/**
	 * �@�\�ʉ݃R�[�h
	 * 
	 * @param func_cur_code �@�\�ʉ݃R�[�h
	 */
	public void setFUNC_CUR_CODE(String func_cur_code) {
		fUNC_CUR_CODE = func_cur_code;
	}

	/**
	 * �@�\�ʉݏ����_����
	 * 
	 * @return �@�\�ʉݏ����_����
	 */
	public int getFUNC_CUR_DEC_KETA() {
		return fUNC_CUR_DEC_KETA;
	}

	/**
	 * �@�\�ʉݏ����_����
	 * 
	 * @param func_cur_dec_keta �@�\�ʉݏ����_����
	 */
	public void setFUNC_CUR_DEC_KETA(int func_cur_dec_keta) {
		fUNC_CUR_DEC_KETA = func_cur_dec_keta;
	}

	/**
	 * ��ʉ݃R�[�h
	 * 
	 * @return ��ʉ݃R�[�h
	 */
	public String getKEY_CUR_CODE() {
		return kEY_CUR_CODE;
	}

	/**
	 * ��ʉ݃R�[�h
	 * 
	 * @param key_cur_code ��ʉ݃R�[�h
	 */
	public void setKEY_CUR_CODE(String key_cur_code) {
		kEY_CUR_CODE = key_cur_code;
	}

	/**
	 * ��ʉݏ����_����
	 * 
	 * @return ��ʉݏ����_����
	 */
	public int getKEY_CUR_DEC_KETA() {
		return kEY_CUR_DEC_KETA;
	}

	/**
	 * ��ʉݏ����_����
	 * 
	 * @param key_cur_dec_keta ��ʉݏ����_����
	 */
	public void setKEY_CUR_DEC_KETA(int key_cur_dec_keta) {
		kEY_CUR_DEC_KETA = key_cur_dec_keta;
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
	 * currency���擾����B
	 * 
	 * @return Currency currency
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * currency��ݒ肷��B
	 * 
	 * @param currency
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;

		if (currency == null) {
			sWK_CUR_CODE = null;
			sWK_CUR_DEC_KETA = 0;

		} else {
			sWK_CUR_CODE = currency.getCode();
			sWK_CUR_DEC_KETA = currency.getDecimalPoint();
		}
	}

	/**
	 * customer���擾����B
	 * 
	 * @return Customer customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * customer��ݒ肷��B
	 * 
	 * @param customer
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;

		if (customer == null) {
			sWK_TRI_CODE = null;
			sWK_TRI_NAME = null;
			sWK_TRI_NAME_S = null;

		} else {
			sWK_TRI_CODE = customer.getCode();
			sWK_TRI_NAME = customer.getName();
			sWK_TRI_NAME_S = customer.getNames();
		}
	}

	/**
	 * paymentMethod���擾����B
	 * 
	 * @return PaymentMethod paymentMethod
	 */
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * paymentMethod��ݒ肷��B
	 * 
	 * @param paymentMethod
	 */
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;

		if (paymentMethod == null) {
			sWK_HOH_CODE = null;
			sWK_HOH_NAME = null;
			sWK_HOH_NAI_CODE = null;

		} else {
			sWK_HOH_CODE = paymentMethod.getCode();
			sWK_HOH_NAME = paymentMethod.getName();
			sWK_HOH_NAI_CODE = paymentMethod.getPaymentKind() == null ? null : paymentMethod.getPaymentKind().value;
		}
	}

	/**
	 * paymentSetting���擾����B
	 * 
	 * @return PaymentSetting paymentSetting
	 */
	public PaymentSetting getPaymentSetting() {
		return paymentSetting;
	}

	/**
	 * paymentSetting��ݒ肷��B
	 * 
	 * @param paymentSetting
	 */
	public void setPaymentSetting(PaymentSetting paymentSetting) {
		this.paymentSetting = paymentSetting;

		if (paymentSetting == null) {
			sWK_TJK_CODE = null;
			sWK_TJK_GS_BNK_NAME = null;
			sWK_TJK_GS_STN_NAME = null;
			sWK_TJK_BNK_NAME_S = null;
			sWK_TJK_BNK_STN_NAME_S = null;

		} else {
			sWK_TJK_CODE = paymentSetting.getCode();
			sWK_TJK_GS_BNK_NAME = paymentSetting.getSendBankName(); // ��d�����E�p����s
			sWK_TJK_GS_STN_NAME = paymentSetting.getSendBranchName(); // ��d�����E�p���x�X
			sWK_TJK_BNK_NAME_S = paymentSetting.getBankName();
			sWK_TJK_BNK_STN_NAME_S = paymentSetting.getBranchName();
		}
	}

	/**
	 * bankAccount���擾����B
	 * 
	 * @return BankAccount bankAccount
	 */
	public BankAccount getBankAccount() {
		return bankAccount;
	}

	/**
	 * bankAccount��ݒ肷��B
	 * 
	 * @param bankAccount
	 */
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;

		if (bankAccount == null) {
			sWK_CBK_CODE = null;
			sWK_CBK_NAME = null;
			sWK_CBK_STN_CODE = null;
			sWK_BNK_NAME_S = null;
			sWK_BNK_STN_NAME_S = null;

		} else {
			sWK_CBK_CODE = bankAccount.getCode();
			sWK_CBK_NAME = bankAccount.getName(); // ��s��������
			sWK_CBK_STN_CODE = bankAccount.getBranchCode(); // �x�X�R�[�h
			sWK_BNK_NAME_S = bankAccount.getBankName(); // ��s��
			sWK_BNK_STN_NAME_S = bankAccount.getBranchName(); // �x�X��
		}
	}

	/**
	 * ����
	 * 
	 * @return ����
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * ����
	 * 
	 * @param department ����
	 */
	public void setDepartment(Department department) {
		this.department = department;

		if (department == null) {
			sWK_DEP_CODE = null;
			sWK_DEP_NAME = null;
			sWK_DEP_NAME_S = null;

		} else {
			sWK_DEP_CODE = department.getCode();
			sWK_DEP_NAME = department.getName();
			sWK_DEP_NAME_S = department.getNames();
		}
	}

	/**
	 * �Ȗ�
	 * 
	 * @return �Ȗ�
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * �Ȗ�
	 * 
	 * @param item �Ȗ�
	 */
	public void setItem(Item item) {
		this.item = item;

		if (item == null) {
			sWK_KMK_CODE = null;
			sWK_KMK_NAME = null;
			sWK_KMK_NAME_S = null;
			sWK_HKM_CODE = null;
			sWK_HKM_NAME = null;
			sWK_HKM_NAME_S = null;
			sWK_UKM_CODE = null;
			sWK_UKM_NAME = null;
			sWK_UKM_NAME_S = null;

		} else {
			sWK_KMK_CODE = item.getCode();
			sWK_KMK_NAME = item.getName();
			sWK_KMK_NAME_S = item.getNames();
			sWK_HKM_CODE = item.getSubItemCode();
			sWK_HKM_NAME = item.getSubItemName();
			sWK_HKM_NAME_S = item.getSubItemNames();
			sWK_UKM_CODE = item.getDetailItemCode();
			sWK_UKM_NAME = item.getDetailItemName();
			sWK_UKM_NAME_S = item.getDetailItemNames();
		}
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
	 * billType���擾����B
	 * 
	 * @return BillType billType
	 */
	public BillType getBillType() {
		return billType;
	}

	/**
	 * billType��ݒ肷��B
	 * 
	 * @param billType
	 */
	public void setBillType(BillType billType) {
		this.billType = billType;

		if (billType == null) {
			this.sWK_SEI_KBN = null;

		} else {
			this.sWK_SEI_KBN = billType.getCode();
		}
	}

	/**
	 * ����敪��ݒ肷��.
	 * 
	 * @param accountBook ����敪
	 */
	public void setAccountBook(AccountBook accountBook) {
		sWK_ADJ_KBN = accountBook.value;
	}

	/**
	 * ���������}�X�^�̔�d�������a�����
	 * 
	 * @return ���������}�X�^�̔�d�������a�����
	 */
	public int getSWK_TJK_YKN_KBN() {
		return sWK_TJK_YKN_KBN;
	}

	/**
	 * ���������}�X�^�̔�d�������a�����
	 * 
	 * @param swk_tjk_ykn_kbn ���������}�X�^�̔�d�������a�����
	 */
	public void setSWK_TJK_YKN_KBN(int swk_tjk_ykn_kbn) {
		sWK_TJK_YKN_KBN = swk_tjk_ykn_kbn;
	}

	/**
	 * ���������}�X�^�̔�d�������ԍ�
	 * 
	 * @return ���������}�X�^�̔�d�������ԍ�
	 */
	public String getSWK_TJK_YKN_NO() {
		return sWK_TJK_YKN_NO;
	}

	/**
	 * ���������}�X�^�̔�d�������ԍ�
	 * 
	 * @param swk_tjk_ykn_no ���������}�X�^�̔�d�������ԍ�
	 */
	public void setSWK_TJK_YKN_NO(String swk_tjk_ykn_no) {
		sWK_TJK_YKN_NO = swk_tjk_ykn_no;
	}

	/**
	 * ���������}�X�^�̔�d���������`�J�i
	 * 
	 * @return sWK_TJK_YKN_KANA
	 */
	public String getSWK_TJK_YKN_KANA() {
		return sWK_TJK_YKN_KANA;
	}

	/**
	 * @param swk_tjk_ykn_kana
	 */
	public void setSWK_TJK_YKN_KANA(String swk_tjk_ykn_kana) {
		sWK_TJK_YKN_KANA = swk_tjk_ykn_kana;
	}

	/**
	 * �`��T�Z����`�[�ԍ���ݒ肷��
	 * 
	 * @param sWK_EST_CANCEL_DEN_NO
	 */
	public void setSWK_EST_CANCEL_DEN_NO(String sWK_EST_CANCEL_DEN_NO) {
		this.sWK_EST_CANCEL_DEN_NO = sWK_EST_CANCEL_DEN_NO;
	}

	/**
	 * �`��T�Z����`�[�ԍ����擾����
	 * 
	 * @return sWK_EST_CANCEL_DEN_NO
	 */
	public String getSWK_EST_CANCEL_DEN_NO() {
		return this.sWK_EST_CANCEL_DEN_NO;
	}

	/**
	 * �`��T�Z�`�[�ԍ���ݒ肷��
	 * 
	 * @param sWK_BASE_EST_DEN_NO
	 */
	public void setSWK_BASE_EST_DEN_NO(String sWK_BASE_EST_DEN_NO) {
		this.sWK_BASE_EST_DEN_NO = sWK_BASE_EST_DEN_NO;
	}

	/**
	 * �`��T�Z�`�[�ԍ����擾����
	 * 
	 * @return sWK_BASE_EST_DEN_NO
	 */
	public String getSWK_BASE_EST_DEN_NO() {
		return this.sWK_BASE_EST_DEN_NO;
	}

	/**
	 * �`�[��ʂ̃Z�b�g
	 * 
	 * @param slipType �`�[���
	 */
	public void setSlipType(SlipType slipType) {

		if (slipType == null) {
			this.sWK_SYS_KBN = null;
			this.sWK_DEN_SYU = null;
			this.sWK_DEN_SYU_NAME = null;
			this.sWK_DEN_SYU_NAME_S = null;
			this.sWK_DEN_SYU_NAME_K = null;
			this.sWK_DATA_KBN = null;

		} else {
			this.sWK_SYS_KBN = slipType.getSystemDiv();
			this.sWK_DEN_SYU = slipType.getCode();
			this.sWK_DEN_SYU_NAME = slipType.getName();
			this.sWK_DEN_SYU_NAME_S = slipType.getNames();
			this.sWK_DEN_SYU_NAME_K = slipType.getNamek();
			this.sWK_DATA_KBN = slipType.getDataType();
		}
	}

	/**
	 * �Ј�
	 * 
	 * @return �Ј�
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * �Ј�
	 * 
	 * @param employee �Ј�
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;

		if (employee == null) {
			this.sWK_EMP_CODE = null;
			this.sWK_EMP_NAME = null;
			this.sWK_EMP_NAME_S = null;

		} else {
			this.sWK_EMP_CODE = employee.getCode();
			this.sWK_EMP_NAME = employee.getName();
			this.sWK_EMP_NAME_S = employee.getNames();
		}
	}

	/**
	 * �E�v
	 * 
	 * @return �E�v
	 */
	public Remark getRemark() {
		return remark;
	}

	/**
	 * �E�v
	 * 
	 * @param remark �E�v
	 */
	public void setRemark(Remark remark) {
		this.remark = remark;

		if (remark == null) {
			this.sWK_TEK_CODE = null;

		} else {
			this.sWK_TEK_CODE = remark.getCode();
		}
	}

	/**
	 * �Y�t�̎擾
	 * 
	 * @return attachments �Y�t
	 */
	public List<SWK_ATTACH> getAttachments() {
		return attachments;
	}

	/**
	 * �Y�t�̐ݒ�
	 * 
	 * @param attachments �Y�t
	 */
	public void setAttachments(List<SWK_ATTACH> attachments) {
		this.attachments = attachments;
	}

	/**
	 * �Y�t��񂪂��邩�ǂ����̎擾
	 * 
	 * @return isExistAttachment �Y�t��񂪂��邩�ǂ��� true�F����
	 */
	public boolean isExistAttachment() {
		return isExistAttachment;
	}

	/**
	 * �Y�t��񂪂��邩�ǂ����̐ݒ�
	 * 
	 * @param isExistAttachment �Y�t��񂪂��邩�ǂ��� true�F����
	 */
	public void setExistAttachment(boolean isExistAttachment) {
		this.isExistAttachment = isExistAttachment;
	}

	/**
	 * �p����s�Z���̎擾
	 * 
	 * @return sWK_TJK_GS_BNK_ADR �p����s�Z��
	 */
	public String getSWK_TJK_GS_BNK_ADR() {
		return sWK_TJK_GS_BNK_ADR;
	}

	/**
	 * �p����s�Z���̐ݒ�
	 * 
	 * @param sWK_TJK_GS_BNK_ADR �p����s�Z��
	 */
	public void setSWK_TJK_GS_BNK_ADR(String sWK_TJK_GS_BNK_ADR) {
		this.sWK_TJK_GS_BNK_ADR = sWK_TJK_GS_BNK_ADR;
	}

	/** �t� */
	protected List<SWK_TAG> swkTags = null;

	/**
	 * �t� �̎擾
	 * 
	 * @return tag �t�
	 */
	public List<SWK_TAG> getSwkTags() {
		return swkTags;
	}

	/**
	 * �t� �̐ݒ�
	 * 
	 * @param swkTags �t�
	 */
	public void setSwkTags(List<SWK_TAG> swkTags) {
		this.swkTags = swkTags;
	}

	/**
	 * ���[�N�t���[���F�R���g���[�����擾
	 * 
	 * @return syoCtl
	 */
	public SWK_SYO_CTL getSyoCtl() {
		return syoCtl;
	}

	/**
	 * ���[�N�t���[���F�R���g���[�����Z�b�g����
	 * 
	 * @param syoCtl syoCtl
	 */
	public void setSyoCtl(SWK_SYO_CTL syoCtl) {
		this.syoCtl = syoCtl;
	}

	/**
	 * ���F�������[���O���[�v ���擾
	 * 
	 * @return ���F�������[���O���[�v
	 */
	public AprvRoleGroup getAprRoleGroup() {
		return aprRoleGroup;
	}

	/**
	 * ���F�������[���O���[�v ���Z�b�g����
	 * 
	 * @param aprRoleGroup ���F�������[���O���[�v
	 */
	public void setAprRoleGroup(AprvRoleGroup aprRoleGroup) {
		this.aprRoleGroup = aprRoleGroup;
	}

}
