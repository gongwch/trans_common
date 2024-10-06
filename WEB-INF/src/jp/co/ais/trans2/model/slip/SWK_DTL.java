package jp.co.ais.trans2.model.slip;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.balance.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.management.*;
import jp.co.ais.trans2.model.remark.*;
import jp.co.ais.trans2.model.tax.*;
import jp.co.ais.trans2.op.model.item.*;

/**
 * �d�󖾍�
 */
public class SWK_DTL extends TransferBase implements Cloneable {

	/** �����d��敪�萔 */
	public static final class AUTO_KBN {

		/** 0:�ʏ�`�[ */
		public static final int NOMAL = 0;

		/** 1:�����d��`�[ */
		public static final int AUTO = 1;

		/** 2:�ԓ` = �t�d��) */
		public static final int REVERSE = 2;
	}

	/** �ŋ敪�萔 */
	public static final class ZEI_KBN {

		/** 0:�O�� */
		public static final int TAX_OUT = 0;

		/** 1:���� */
		public static final int TAX_IN = 1;

		/** 2:��ې� */
		public static final int TAX_NONE = 2;
	}

	/** �����敪�萔 */
	public static final class KESI_KBN {

		/** 0:�ʏ� */
		public static final int NOMAL = 0;

		/** 1:�������d��s */
		public static final int BASE = 1;

		/** 2:������d��s */
		public static final int FORWARD = 2;
	}

	/** AP/AR�����敪�萔 */
	public static final class APAR_KESI_KBN {

		/** 0:�ʏ� */
		public static final int NOMAL = 0;

		/** 1:AP�������d��s */
		public static final int AP_BASE = 1;

		/** 2:AP������d��s */
		public static final int AP_FORWARD = 2;

		/** 3:AR�������d��s */
		public static final int AR_BASE = 3;

		/** 4:AR������d��s */
		public static final int AR_FORWARD = 4;
	}

	/** ���R�敪�萔 */
	public static final class FREE_KBN {

		/** 0:���g�p */
		public static final int NOMAL = 0;

		/** 1:�o�ߑ[�u���� */
		public static final int KEKA_SOTI = 1;

		// �ȊO�g���҂�
	}

	/** ��ЃR�[�h */
	protected String kAI_CODE;

	/** �`�[���t */
	protected Date sWK_DEN_DATE;

	/** �`�[�ԍ� */
	protected String sWK_DEN_NO;

	/** BOOK No.(1:�ʏ�d�� 2:�@�\�ʉݎd��) */
	protected int sWK_BOOK_NO = CurrencyType.KEY.value;

	/** �s�ԍ� */
	protected int sWK_GYO_NO;

	/** IFRS�����敪(0:���� 1:�����̂� 2:IFRS�̂�) */
	protected int sWK_ADJ_KBN = AccountBook.BOTH.value;

	/** �N�x */
	protected int sWK_NENDO;

	/** ���x */
	protected int sWK_TUKIDO;

	/** �؜ߔԍ� */
	protected String sWK_SEI_NO;

	/** �ݎ؋敪 */
	protected int sWK_DC_KBN;

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

	/** �ŋ敪 */
	protected int sWK_ZEI_KBN;

	/** �M�݋��z */
	protected BigDecimal sWK_KIN;

	/** ����Ŋz */
	protected BigDecimal sWK_ZEI_KIN;

	/** ����ŃR�[�h */
	protected String sWK_ZEI_CODE;

	/** ����Ŗ��� */
	protected String sWK_ZEI_NAME;

	/** ����ŗ��� */
	protected String sWK_ZEI_NAME_S;

	/** �ŗ� */
	protected BigDecimal sWK_ZEI_RATE;

	/** �s�E�v�R�[�h */
	protected String sWK_GYO_TEK_CODE;

	/** �s�E�v */
	protected String sWK_GYO_TEK;

	/** �����R�[�h */
	protected String sWK_TRI_CODE;

	/** ����於�� */
	protected String sWK_TRI_NAME;

	/** ����旪�� */
	protected String sWK_TRI_NAME_S;

	/** �W�v�����R�[�h */
	protected String sUM_TRI_CODE;

	/** �W�v����旪�� */
	protected String sUM_TRI_NAME_S;

	/** �Ј��R�[�h */
	protected String sWK_EMP_CODE;

	/** �Ј����� */
	protected String sWK_EMP_NAME;

	/** �Ј����� */
	protected String sWK_EMP_NAME_S;

	/** �Ǘ��P�R�[�h */
	protected String sWK_KNR_CODE_1;

	/** �Ǘ��P���� */
	protected String sWK_KNR_NAME_1;

	/** �Ǘ��P���� */
	protected String sWK_KNR_NAME_S_1;

	/** �Ǘ��Q�R�[�h */
	protected String sWK_KNR_CODE_2;

	/** �Ǘ��Q���� */
	protected String sWK_KNR_NAME_2;

	/** �Ǘ��Q���� */
	protected String sWK_KNR_NAME_S_2;

	/** �Ǘ��R�R�[�h */
	protected String sWK_KNR_CODE_3;

	/** �Ǘ��R���� */
	protected String sWK_KNR_NAME_3;

	/** �Ǘ��R���� */
	protected String sWK_KNR_NAME_S_3;

	/** �Ǘ��S�R�[�h */
	protected String sWK_KNR_CODE_4;

	/** �Ǘ��S���� */
	protected String sWK_KNR_NAME_4;

	/** �Ǘ��S���� */
	protected String sWK_KNR_NAME_S_4;

	/** �Ǘ��T�R�[�h */
	protected String sWK_KNR_CODE_5;

	/** �Ǘ��T���� */
	protected String sWK_KNR_NAME_5;

	/** �Ǘ��T���� */
	protected String sWK_KNR_NAME_S_5;

	/** �Ǘ��U�R�[�h */
	protected String sWK_KNR_CODE_6;

	/** �Ǘ��U���� */
	protected String sWK_KNR_NAME_6;

	/** �Ǘ��U���� */
	protected String sWK_KNR_NAME_S_6;

	/** ���v���ׂP */
	protected String sWK_HM_1;

	/** ���v���ׂQ */
	protected String sWK_HM_2;

	/** ���v���ׂR */
	protected String sWK_HM_3;

	/** �����d��敪 */
	protected int sWK_AUTO_KBN;

	/** �f�[�^�敪 */
	protected String sWK_DATA_KBN;

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

	/** �o�^���t */
	protected Date iNP_DATE;

	/** �X�V���t */
	protected Date uPD_DATE;

	/** �v���O�����h�c */
	protected String pRG_ID;

	/** ���[�U�[�h�c */
	protected String uSR_ID;

	/** �v���к��� */
	protected String sWK_K_KAI_CODE;

	/** �v���Ж� */
	protected String sWK_K_KAI_NAME;

	/** �v���З��� */
	protected String sWK_K_KAI_NAME_S;

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

	/** �����敪 */
	protected int sWK_KESI_KBN;

	/** �����`�[���t */
	protected Date sWK_KESI_DATE;

	/** �����`�[�ԍ� */
	protected String sWK_KESI_DEN_NO;

	/** ���E�`�[���t */
	protected Date sWK_SOUSAI_DATE;

	/** ���E�`�[�ԍ� */
	protected String sWK_SOUSAI_DEN_NO;

	/** ���E�s�ԍ� */
	protected Integer sWK_SOUSAI_GYO_NO = null;

	/** ������ */
	protected Date hAS_DATE;

	/** �`�[��ʺ��� */
	protected String dEN_SYU_CODE;

	// �p�^�[���p --

	/** �`�[�C���� */
	protected int sWK_UPD_CNT;

	// �ǉ� --

	/** �ʉ� �����_���� */
	protected int cUR_DEC_KETA;

	/** �ʉ� ���[�g�W�� */
	protected int cUR_RATE_POW;

	/** ��ʉ݃R�[�h */
	protected String kEY_CUR_CODE;

	/** ��ʉݏ����_���� */
	protected int kEY_CUR_DEC_KETA;

	/** �@�\�ʉ݃R�[�h */
	protected String fUNC_CUR_CODE;

	/** �@�\�ʉݏ����_���� */
	protected int fUNC_CUR_DEC_KETA;

	/** ����Ŏd�󂩂ǂ��� */
	protected boolean taxJornal;

	/** �v���� */
	protected Company appropriateCompany;

	/** �v�㕔�� */
	protected Department department;

	/** �Ȗ� */
	protected Item item;

	/** �s�E�v */
	protected Remark remark;

	/** ����� */
	protected ConsumptionTax tax;

	/** �ʉ� */
	protected Currency currency;

	/** �Ј� */
	protected Employee employee;

	/** ����� */
	protected Customer customer;

	/** �Ǘ�1 */
	protected Management1 management1;

	/** �Ǘ�2 */
	protected Management2 management2;

	/** �Ǘ�3 */
	protected Management3 management3;

	/** �Ǘ�4 */
	protected Management4 management4;

	/** �Ǘ�5 */
	protected Management5 management5;

	/** �Ǘ�6 */
	protected Management6 management6;

	/** ���c��(�����p) */
	protected AP_ZAN apBalance;

	/** ���c��(�����p) */
	protected AR_ZAN arBalance;

	/** ���Z�d�󂩂ǂ��� */
	protected boolean settlementJornal;

	/** BS��������d�� */
	protected SWK_DTL bsDetail = null;

	/** �`��T�Z�`�[�ԍ� */
	protected String sWK_EST_DEN_NO;

	/** �`����Z�`�[�ԍ� */
	protected String sWK_STL_DEN_NO;

	/** �`��O�n���`�[�ԍ� */
	protected String sWK_MAE_DEN_NO;

	/** �`��O�n���s�ԍ� */
	protected Integer sWK_MAE_GYO_NO;

	/** �Ȗڔ������g�p */
	protected boolean useItemOccurDate = false;

	/** AP/AR�����敪 */
	protected int sWK_APAR_KESI_KBN = 0;

	/** AP/AR�����`�[�ԍ� */
	protected String sWK_APAR_DEN_NO = null;

	/** AP/AR�����s�ԍ� */
	protected Integer sWK_APAR_GYO_NO = null;

	/** �d�󖾍חpOP�A�C�e��(���ӁFDB�֌W�Ȃ�OP�����p�̂�) */
	protected OPItem journalOPItem = null;

	/** �`�[�ǉ����(���ӁFDB�֌W�Ȃ�OP�����p�̂�) */
	protected SlipDetailAddonInfo addonInfo = new SlipDetailAddonInfo();

	/** ���R�敪 */
	protected int SWK_FREE_KBN = 0;

	/** �o�ߑ[�u�̌��s�ԍ� */
	protected Integer SWK_ORI_GYO_NO = null;

	/** �o�ߑ[�u�d��ł��邩�ǂ��� true�F�o�ߑ[�u�d�� */
	protected boolean isKekaDtl = false;

	/** �o�ߑ[�u�T���\��(1%�`99%) */
	protected BigDecimal KEKA_SOTI_RATE = null;

	/**
	 * �I�u�W�F�N�g��Clone
	 */
	@Override
	public SWK_DTL clone() {
		try {
			SWK_DTL clone = (SWK_DTL) super.clone();
			clone.apBalance = this.apBalance;
			clone.arBalance = this.arBalance;
			clone.bsDetail = this.bsDetail;

			return clone;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

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
	public Date getHAS_DATE() {
		return hAS_DATE;
	}

	/**
	 * ������
	 * 
	 * @param has_date ������
	 */
	public void setHAS_DATE(Date has_date) {
		hAS_DATE = has_date;
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
	 * BOOK No.
	 * 
	 * @return BOOK No.
	 */
	public int getSWK_BOOK_NO() {
		return sWK_BOOK_NO;
	}

	/**
	 * BOOK No.
	 * 
	 * @param swk_book_no BOOK No.
	 */
	public void setSWK_BOOK_NO(int swk_book_no) {
		sWK_BOOK_NO = swk_book_no;
	}

	/**
	 * Book�^�C�v
	 * 
	 * @param type Book�^�C�v
	 */
	public void setCurrencyType(CurrencyType type) {
		sWK_BOOK_NO = type.value;
	}

	/**
	 * Book�^�C�v
	 * 
	 * @return Book�^�C�v
	 */
	public CurrencyType getCurrencyType() {
		return CurrencyType.get(sWK_BOOK_NO);
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
	 * �f�[�^�敪
	 * 
	 * @param swk_data_kbn �f�[�^�敪
	 */
	public void setSWK_DATA_KBN(String swk_data_kbn) {
		sWK_DATA_KBN = swk_data_kbn;
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
	 * @param swk_ifrs_flg IFRS�����敪
	 */
	public void setSWK_ADJ_KBN(int swk_ifrs_flg) {
		sWK_ADJ_KBN = swk_ifrs_flg;
	}

	/**
	 * ��v����
	 * 
	 * @param type �^�C�v
	 */
	public void setAccountBook(AccountBook type) {
		sWK_ADJ_KBN = type.value;
	}

	/**
	 * ��v����
	 * 
	 * @return ��v����
	 */
	public AccountBook getAccountBook() {
		return AccountBook.get(sWK_ADJ_KBN);
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
	 * �����`�[���t
	 * 
	 * @return �����`�[���t
	 */
	public Date getSWK_KESI_DATE() {
		return sWK_KESI_DATE;
	}

	/**
	 * �����`�[���t
	 * 
	 * @param swk_kesi_date �����`�[���t
	 */
	public void setSWK_KESI_DATE(Date swk_kesi_date) {
		sWK_KESI_DATE = swk_kesi_date;
	}

	/**
	 * �����`�[�ԍ�
	 * 
	 * @return �����`�[�ԍ�
	 */
	public String getSWK_KESI_DEN_NO() {
		return sWK_KESI_DEN_NO;
	}

	/**
	 * �����`�[�ԍ�
	 * 
	 * @param swk_kesi_den_no �����`�[�ԍ�
	 */
	public void setSWK_KESI_DEN_NO(String swk_kesi_den_no) {
		sWK_KESI_DEN_NO = swk_kesi_den_no;
	}

	/**
	 * �����敪
	 * 
	 * @return �����敪
	 */
	public int getSWK_KESI_KBN() {
		return sWK_KESI_KBN;
	}

	/**
	 * �����敪
	 * 
	 * @param swk_kesi_kbn �����敪
	 */
	public void setSWK_KESI_KBN(int swk_kesi_kbn) {
		sWK_KESI_KBN = swk_kesi_kbn;
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
	 * �N�x
	 * 
	 * @return �N�x
	 */
	public int getSWK_NENDO() {
		return sWK_NENDO;
	}

	/**
	 * �N�x
	 * 
	 * @param swk_nendo �N�x
	 */
	public void setSWK_NENDO(int swk_nendo) {
		sWK_NENDO = swk_nendo;
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
	 * ���E�`�[���t
	 * 
	 * @return ���E�`�[���t
	 */
	public Date getSWK_SOUSAI_DATE() {
		return sWK_SOUSAI_DATE;
	}

	/**
	 * ���E�`�[���t
	 * 
	 * @param swk_sousai_date ���E�`�[���t
	 */
	public void setSWK_SOUSAI_DATE(Date swk_sousai_date) {
		sWK_SOUSAI_DATE = swk_sousai_date;
	}

	/**
	 * ���E�`�[�ԍ�
	 * 
	 * @return ���E�`�[�ԍ�
	 */
	public String getSWK_SOUSAI_DEN_NO() {
		return sWK_SOUSAI_DEN_NO;
	}

	/**
	 * ���E�`�[�ԍ�
	 * 
	 * @param swk_sousai_den_no ���E�`�[�ԍ�
	 */
	public void setSWK_SOUSAI_DEN_NO(String swk_sousai_den_no) {
		sWK_SOUSAI_DEN_NO = swk_sousai_den_no;
	}

	/**
	 * ���E�s�ԍ��̎擾
	 * 
	 * @return sWK_SOUSAI_GYO_NO ���E�s�ԍ�
	 */
	public Integer getSWK_SOUSAI_GYO_NO() {
		return sWK_SOUSAI_GYO_NO;
	}

	/**
	 * ���E�s�ԍ��̐ݒ�
	 * 
	 * @param sWK_SOUSAI_GYO_NO ���E�s�ԍ�
	 */
	public void setSWK_SOUSAI_GYO_NO(Integer sWK_SOUSAI_GYO_NO) {
		this.sWK_SOUSAI_GYO_NO = sWK_SOUSAI_GYO_NO;
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
	 * ���x
	 * 
	 * @return ���x
	 */
	public int getSWK_TUKIDO() {
		return sWK_TUKIDO;
	}

	/**
	 * ���x
	 * 
	 * @param swk_tukido ���x
	 */
	public void setSWK_TUKIDO(int swk_tukido) {
		sWK_TUKIDO = swk_tukido;
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

	/**
	 * �ŗ�
	 * 
	 * @return �ŗ�
	 */
	public BigDecimal getSWK_ZEI_RATE() {
		return sWK_ZEI_RATE;
	}

	/**
	 * �ŗ�
	 * 
	 * @param swk_zei_rate �ŗ�
	 */
	public void setSWK_ZEI_RATE(BigDecimal swk_zei_rate) {
		sWK_ZEI_RATE = swk_zei_rate;
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
	 * �Ǘ��P����
	 * 
	 * @return �Ǘ��P����
	 */
	public String getSWK_KNR_NAME_1() {
		return sWK_KNR_NAME_1;
	}

	/**
	 * �Ǘ��P����
	 * 
	 * @param swk_knr_name_1 �Ǘ��P����
	 */
	public void setSWK_KNR_NAME_1(String swk_knr_name_1) {
		sWK_KNR_NAME_1 = swk_knr_name_1;
	}

	/**
	 * �Ǘ��Q����
	 * 
	 * @return �Ǘ��Q����
	 */
	public String getSWK_KNR_NAME_2() {
		return sWK_KNR_NAME_2;
	}

	/**
	 * �Ǘ��Q����
	 * 
	 * @param swk_knr_name_2 �Ǘ��Q����
	 */
	public void setSWK_KNR_NAME_2(String swk_knr_name_2) {
		sWK_KNR_NAME_2 = swk_knr_name_2;
	}

	/**
	 * �Ǘ��R����
	 * 
	 * @return �Ǘ��R����
	 */
	public String getSWK_KNR_NAME_3() {
		return sWK_KNR_NAME_3;
	}

	/**
	 * �Ǘ��R����
	 * 
	 * @param swk_knr_name_3 �Ǘ��R����
	 */
	public void setSWK_KNR_NAME_3(String swk_knr_name_3) {
		sWK_KNR_NAME_3 = swk_knr_name_3;
	}

	/**
	 * �Ǘ��S����
	 * 
	 * @return �Ǘ��S����
	 */
	public String getSWK_KNR_NAME_4() {
		return sWK_KNR_NAME_4;
	}

	/**
	 * �Ǘ��S����
	 * 
	 * @param swk_knr_name_4 �Ǘ��S����
	 */
	public void setSWK_KNR_NAME_4(String swk_knr_name_4) {
		sWK_KNR_NAME_4 = swk_knr_name_4;
	}

	/**
	 * �Ǘ��T����
	 * 
	 * @return �Ǘ��T����
	 */
	public String getSWK_KNR_NAME_5() {
		return sWK_KNR_NAME_5;
	}

	/**
	 * �Ǘ��T����
	 * 
	 * @param swk_knr_name_5 �Ǘ��T����
	 */
	public void setSWK_KNR_NAME_5(String swk_knr_name_5) {
		sWK_KNR_NAME_5 = swk_knr_name_5;
	}

	/**
	 * �Ǘ��U����
	 * 
	 * @return �Ǘ��U����
	 */
	public String getSWK_KNR_NAME_6() {
		return sWK_KNR_NAME_6;
	}

	/**
	 * �Ǘ��U����
	 * 
	 * @param swk_knr_name_6 �Ǘ��U����
	 */
	public void setSWK_KNR_NAME_6(String swk_knr_name_6) {
		sWK_KNR_NAME_6 = swk_knr_name_6;
	}

	/**
	 * �Ǘ��P����
	 * 
	 * @return �Ǘ��P����
	 */
	public String getSWK_KNR_NAME_S_1() {
		return sWK_KNR_NAME_S_1;
	}

	/**
	 * �Ǘ��P����
	 * 
	 * @param swk_knr_name_s_1 �Ǘ��P����
	 */
	public void setSWK_KNR_NAME_S_1(String swk_knr_name_s_1) {
		sWK_KNR_NAME_S_1 = swk_knr_name_s_1;
	}

	/**
	 * �Ǘ��Q����
	 * 
	 * @return �Ǘ��Q����
	 */
	public String getSWK_KNR_NAME_S_2() {
		return sWK_KNR_NAME_S_2;
	}

	/**
	 * �Ǘ��Q����
	 * 
	 * @param swk_knr_name_s_2 �Ǘ��Q����
	 */
	public void setSWK_KNR_NAME_S_2(String swk_knr_name_s_2) {
		sWK_KNR_NAME_S_2 = swk_knr_name_s_2;
	}

	/**
	 * �Ǘ��R����
	 * 
	 * @return �Ǘ��R����
	 */
	public String getSWK_KNR_NAME_S_3() {
		return sWK_KNR_NAME_S_3;
	}

	/**
	 * �Ǘ��R����
	 * 
	 * @param swk_knr_name_s_3 �Ǘ��R����
	 */
	public void setSWK_KNR_NAME_S_3(String swk_knr_name_s_3) {
		sWK_KNR_NAME_S_3 = swk_knr_name_s_3;
	}

	/**
	 * �Ǘ��S����
	 * 
	 * @return �Ǘ��S����
	 */
	public String getSWK_KNR_NAME_S_4() {
		return sWK_KNR_NAME_S_4;
	}

	/**
	 * �Ǘ��S����
	 * 
	 * @param swk_knr_name_s_4 �Ǘ��S����
	 */
	public void setSWK_KNR_NAME_S_4(String swk_knr_name_s_4) {
		sWK_KNR_NAME_S_4 = swk_knr_name_s_4;
	}

	/**
	 * �Ǘ��T����
	 * 
	 * @return �Ǘ��T����
	 */
	public String getSWK_KNR_NAME_S_5() {
		return sWK_KNR_NAME_S_5;
	}

	/**
	 * �Ǘ��T����
	 * 
	 * @param swk_knr_name_s_5 �Ǘ��T����
	 */
	public void setSWK_KNR_NAME_S_5(String swk_knr_name_s_5) {
		sWK_KNR_NAME_S_5 = swk_knr_name_s_5;
	}

	/**
	 * �Ǘ��U����
	 * 
	 * @return �Ǘ��U����
	 */
	public String getSWK_KNR_NAME_S_6() {
		return sWK_KNR_NAME_S_6;
	}

	/**
	 * �Ǘ��U����
	 * 
	 * @param swk_knr_name_s_6 �Ǘ��U����
	 */
	public void setSWK_KNR_NAME_S_6(String swk_knr_name_s_6) {
		sWK_KNR_NAME_S_6 = swk_knr_name_s_6;
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
	 * ����Ŗ���
	 * 
	 * @return ����Ŗ���
	 */
	public String getSWK_ZEI_NAME() {
		return sWK_ZEI_NAME;
	}

	/**
	 * ����Ŗ���
	 * 
	 * @param swk_zei_name ����Ŗ���
	 */
	public void setSWK_ZEI_NAME(String swk_zei_name) {
		sWK_ZEI_NAME = swk_zei_name;
	}

	/**
	 * ����ŗ���
	 * 
	 * @return ����ŗ���
	 */
	public String getSWK_ZEI_NAME_S() {
		return sWK_ZEI_NAME_S;
	}

	/**
	 * ����ŗ���
	 * 
	 * @param swk_zei_name_s ����ŗ���
	 */
	public void setSWK_ZEI_NAME_S(String swk_zei_name_s) {
		sWK_ZEI_NAME_S = swk_zei_name_s;
	}

	/**
	 * �K�{���ڂ�NULL���܂܂�Ă��Ȃ����ǂ������`�F�b�N
	 * 
	 * @return true:null�A�܂��̓u�����N���܂܂��
	 */
	public String isRequiredItemNULL() {

		// ��ЃR�[�h KAI_CODE
		// �`�[���t SWK_DEN_DATE
		// �`�[�ԍ� SWK_DEN_NO
		// BOOK No. SWK_BOOK_NO
		// �s�ԍ� SWK_GYO_NO
		// IFRS�����敪 SWKADJ_KBN
		// �N�x SWK_NENDO
		// ���x SWK_TUKIDO
		// �ݎ؋敪 SWK_DC_KBN
		// �ȖڃR�[�h SWK_KMK_CODE
		// �v�㕔��R�[�h SWK_DEP_CODE
		// �����d��敪 SWK_AUTO_KBN
		// �f�[�^�敪 SWK_DATA_KBN
		// �X�V�敪 SWK_UPD_KBN
		// ���Z�敪 SWK_KSN_KBN
		// �`�[��ʺ��� DEN_SYU_CODE
		Map<String, Object> notNullList = new TreeMap<String, Object>();
		// notNullList.put("KAI_CODE", kAI_CODE);
		// notNullList.put("SWK_DEN_DATE", sWK_DEN_DATE);
		// notNullList.put("SWK_DEN_NO", sWK_DEN_NO);
		notNullList.put("SWK_KMK_CODE", sWK_KMK_CODE);
		notNullList.put("SWK_DEP_CODE", sWK_DEP_CODE);
		notNullList.put("DEN_SYU_CODE", dEN_SYU_CODE);

		for (Map.Entry<String, Object> entry : notNullList.entrySet()) {
			if (Util.isNullOrEmpty(entry.getValue())) {
				return entry.getKey();
			}
		}

		return "";
	}

	/**
	 * ��ېł��ǂ���
	 * 
	 * @return true:��ې�
	 */
	public boolean isNonTax() {
		return sWK_ZEI_KBN == ZEI_KBN.TAX_NONE;
	}

	/**
	 * ���ł��ǂ���
	 * 
	 * @return true:����
	 */
	public boolean isTaxInclusive() {
		return sWK_ZEI_KBN == ZEI_KBN.TAX_IN;
	}

	/**
	 * �O�ł��ǂ���
	 * 
	 * @return true:�O��
	 */
	public boolean isTaxExcluded() {
		return sWK_ZEI_KBN == ZEI_KBN.TAX_OUT;
	}

	/**
	 * �ؕ��̊�ʉ݋��z
	 * 
	 * @return ���v���z
	 */
	public BigDecimal getDebitKeyAmount() {
		return Dc.getDc(sWK_DC_KBN) == Dc.DEBIT ? DecimalUtil.avoidNull(sWK_KIN) : BigDecimal.ZERO;
	}

	/**
	 * �ݕ��̊�ʉ݋��z
	 * 
	 * @return ���v���z
	 */
	public BigDecimal getCreditKeyAmount() {
		return Dc.getDc(sWK_DC_KBN) == Dc.CREDIT ? DecimalUtil.avoidNull(sWK_KIN) : BigDecimal.ZERO;
	}

	/**
	 * �ؕ��̓��͋��z
	 * 
	 * @return ���v���z
	 */
	public BigDecimal getDebitInputAmount() {
		return Dc.getDc(sWK_DC_KBN) == Dc.DEBIT ? DecimalUtil.avoidNull(sWK_IN_KIN) : BigDecimal.ZERO;
	}

	/**
	 * �ݕ��̓��͋��z
	 * 
	 * @return ���v���z
	 */
	public BigDecimal getCreditInputAmount() {
		return Dc.getDc(sWK_DC_KBN) == Dc.CREDIT ? DecimalUtil.avoidNull(sWK_IN_KIN) : BigDecimal.ZERO;
	}

	/**
	 * �ؕ��̏���ŋ��z
	 * 
	 * @return ���v���z
	 */
	public BigDecimal getDebitTaxAmount() {
		return Dc.getDc(sWK_DC_KBN) == Dc.DEBIT ? DecimalUtil.avoidNull(sWK_ZEI_KIN) : BigDecimal.ZERO;
	}

	/**
	 * �ݕ��̏���ŋ��z
	 * 
	 * @return ���v���z
	 */
	public BigDecimal getCreditTaxAmount() {
		return Dc.getDc(sWK_DC_KBN) == Dc.CREDIT ? DecimalUtil.avoidNull(sWK_ZEI_KIN) : BigDecimal.ZERO;
	}

	/**
	 * �ؕ��̏���ŋ��z
	 * 
	 * @return ���v���z
	 */
	public BigDecimal getDebitTaxInputAmount() {
		return Dc.getDc(sWK_DC_KBN) == Dc.DEBIT ? DecimalUtil.avoidNull(sWK_IN_ZEI_KIN) : BigDecimal.ZERO;
	}

	/**
	 * �ݕ��̏���ŋ��z
	 * 
	 * @return ���v���z
	 */
	public BigDecimal getCreditTaxInputAmount() {
		return Dc.getDc(sWK_DC_KBN) == Dc.CREDIT ? DecimalUtil.avoidNull(sWK_IN_ZEI_KIN) : BigDecimal.ZERO;
	}

	/**
	 * �@�\�ʉݎd�󂩂ǂ���
	 * 
	 * @return true:�@�\�ʉݎd��
	 */
	public boolean isFunctionalCurrency() {
		return CurrencyType.get(sWK_BOOK_NO) == CurrencyType.FUNCTIONAL;
	}

	/**
	 * �ؕ����ǂ���
	 * 
	 * @return true:�ؕ�
	 */
	public boolean isDR() {
		return Dc.getDc(sWK_DC_KBN) == Dc.DEBIT;
	}

	/**
	 * ����Ŏd�󂩂ǂ���
	 * 
	 * @param taxJornal true:����Ŏd��
	 */
	public void setTaxJornal(boolean taxJornal) {
		this.taxJornal = taxJornal;
	}

	/**
	 * ����Ŏd�󂩂ǂ���
	 * 
	 * @return true:����Ŏd��
	 */
	public boolean isTaxJornal() {
		return this.taxJornal;
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
	 * �ʉ� ���[�g�W��
	 * 
	 * @return cUR_RATE_POW �ʉ� ���[�g�W��
	 */
	public int getCUR_RATE_POW() {
		return cUR_RATE_POW;
	}

	/**
	 * �ʉ� ���[�g�W��
	 * 
	 * @param cur_rate_pow �ʉ� ���[�g�W��
	 */
	public void setCUR_RATE_POW(int cur_rate_pow) {
		cUR_RATE_POW = cur_rate_pow;
	}

	/**
	 * �v���Ж�
	 * 
	 * @return �v���Ж�
	 */
	public String getSWK_K_KAI_NAME() {
		return sWK_K_KAI_NAME;
	}

	/**
	 * �v���Ж�
	 * 
	 * @param swk_k_kai_name �v���Ж�
	 */
	public void setSWK_K_KAI_NAME(String swk_k_kai_name) {
		sWK_K_KAI_NAME = swk_k_kai_name;
	}

	/**
	 * �v���З���
	 * 
	 * @return �v���З���
	 */
	public String getSWK_K_KAI_NAME_S() {
		return sWK_K_KAI_NAME_S;
	}

	/**
	 * �v���З���
	 * 
	 * @param swk_k_kai_name_s �v���З���
	 */
	public void setSWK_K_KAI_NAME_S(String swk_k_kai_name_s) {
		sWK_K_KAI_NAME_S = swk_k_kai_name_s;
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
	 * @return kEY_CUR_DEC_KETA
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
	 * �⏕�Ȗڂ����邩�ǂ���
	 * 
	 * @return true:����
	 */
	public boolean hasSubItem() {
		return !Util.isNullOrEmpty(sWK_HKM_CODE);
	}

	/**
	 * ����Ȗڂ����邩�ǂ���
	 * 
	 * @return true:����
	 */
	public boolean hasDetailItem() {
		return !Util.isNullOrEmpty(sWK_UKM_CODE);
	}

	/**
	 * ���c��
	 * 
	 * @return ���c��
	 */
	public AP_ZAN getAP_ZAN() {
		return apBalance;
	}

	/**
	 * ���c��
	 * 
	 * @param apBalance ���c��
	 */
	public void setAP_ZAN(AP_ZAN apBalance) {
		this.apBalance = apBalance;
	}

	/**
	 * ���c��
	 * 
	 * @return ���c��
	 */
	public AR_ZAN getAR_ZAN() {
		return arBalance;
	}

	/**
	 * ���c��
	 * 
	 * @param arBalance ���c��
	 */
	public void setAR_ZAN(AR_ZAN arBalance) {
		this.arBalance = arBalance;
	}

	/**
	 * �������ׂł��邩�ǂ���
	 * 
	 * @return true:��������
	 */
	public boolean isErasing() {
		return apBalance != null || arBalance != null || bsDetail != null;
	}

	/**
	 * AP�������ׂł��邩�ǂ���
	 * 
	 * @return true:AP��������
	 */
	public boolean isAPErasing() {
		return apBalance != null;
	}

	/**
	 * AR�������ׂł��邩�ǂ���
	 * 
	 * @return true:AR��������
	 */
	public boolean isARErasing() {
		return arBalance != null;
	}

	/**
	 * BS�������ׂł��邩�ǂ���
	 * 
	 * @return true:BS��������
	 */
	public boolean isBSErasing() {
		return bsDetail != null;
	}

	/**
	 * �W�v�����R�[�h
	 * 
	 * @return �W�v�����R�[�h
	 */
	public String getSUM_TRI_CODE() {
		return sUM_TRI_CODE;
	}

	/**
	 * �W�v�����R�[�h
	 * 
	 * @param sum_tri_code �W�v�����R�[�h
	 */
	public void setSUM_TRI_CODE(String sum_tri_code) {
		sUM_TRI_CODE = sum_tri_code;
	}

	/**
	 * �W�v����旪��
	 * 
	 * @return �W�v����旪��
	 */
	public String getSUM_TRI_NAME_S() {
		return sUM_TRI_NAME_S;
	}

	/**
	 * �W�v����旪��
	 * 
	 * @param sum_tri_name_s �W�v����旪��
	 */
	public void setSUM_TRI_NAME_S(String sum_tri_name_s) {
		sUM_TRI_NAME_S = sum_tri_name_s;
	}

	/**
	 * �`��T�Z�`�[�ԍ���ݒ肷��
	 * 
	 * @param sWK_EST_DEN_NO
	 */
	public void setSWK_EST_DEN_NO(String sWK_EST_DEN_NO) {
		this.sWK_EST_DEN_NO = sWK_EST_DEN_NO;
	}

	/**
	 * �`��T�Z�`�[�ԍ����擾����
	 * 
	 * @return sWK_EST_DEN_NO
	 */
	public String getSWK_EST_DEN_NO() {
		return this.sWK_EST_DEN_NO;
	}

	/**
	 * �`����Z�`�[�ԍ���ݒ肷��
	 * 
	 * @param sWK_STL_DEN_NO
	 */
	public void setSWK_STL_DEN_NO(String sWK_STL_DEN_NO) {
		this.sWK_STL_DEN_NO = sWK_STL_DEN_NO;
	}

	/**
	 * �`����Z�`�[�ԍ����擾����
	 * 
	 * @return sWK_STL_DEN_NO
	 */
	public String getSWK_STL_DEN_NO() {
		return this.sWK_STL_DEN_NO;
	}

	/**
	 * �`��O�n���`�[�ԍ���ݒ肷��
	 * 
	 * @param sWK_MAE_DEN_NO
	 */
	public void setSWK_MAE_DEN_NO(String sWK_MAE_DEN_NO) {
		this.sWK_MAE_DEN_NO = sWK_MAE_DEN_NO;
	}

	/**
	 * �`��O�n���`�[�ԍ����擾����
	 * 
	 * @return sWK_MAE_DEN_NO
	 */
	public String getSWK_MAE_DEN_NO() {
		return this.sWK_MAE_DEN_NO;
	}

	/**
	 * �`��O�n���s�ԍ���ݒ肷��
	 * 
	 * @param sWK_MAE_GYO_NO
	 */
	public void setSWK_MAE_GYO_NO(Integer sWK_MAE_GYO_NO) {
		this.sWK_MAE_GYO_NO = sWK_MAE_GYO_NO;
	}

	/**
	 * �`��O�n���s�ԍ����擾����
	 * 
	 * @return sWK_MAE_GYO_NO
	 */
	public Integer getSWK_MAE_GYO_NO() {
		return this.sWK_MAE_GYO_NO;
	}

	/**
	 * �Ȗڔ������g�p�̎擾
	 * 
	 * @return useItemOccurDate �Ȗڔ������g�p
	 */
	public boolean isUseItemOccurDate() {
		return useItemOccurDate;
	}

	/**
	 * �Ȗڔ������g�p�̐ݒ�
	 * 
	 * @param useItemOccurDate �Ȗڔ������g�p
	 */
	public void setUseItemOccurDate(boolean useItemOccurDate) {
		this.useItemOccurDate = useItemOccurDate;
	}

	/**
	 * �v���Ђ̐ݒ�
	 * 
	 * @param comapny �v����
	 */
	public void setAppropriateCompany(Company comapny) {
		appropriateCompany = comapny;

		if (comapny == null) {
			sWK_K_KAI_CODE = null;
			sWK_K_KAI_NAME = null;
			sWK_K_KAI_NAME_S = null;

		} else {
			sWK_K_KAI_CODE = comapny.getCode();
			sWK_K_KAI_NAME = comapny.getName();
			sWK_K_KAI_NAME_S = comapny.getNames();
		}
	}

	/**
	 * �v���Ђ̎擾
	 * 
	 * @return �v����
	 */
	public Company getAppropriateCompany() {
		return appropriateCompany;
	}

	/**
	 * �v�㕔��̐ݒ�
	 * 
	 * @param dept ����
	 */
	public void setDepartment(Department dept) {
		department = dept;

		if (dept == null) {
			sWK_DEP_CODE = null;
			sWK_DEP_NAME = null;
			sWK_DEP_NAME_S = null;

		} else {
			sWK_DEP_CODE = dept.getCode();
			sWK_DEP_NAME = dept.getName();
			sWK_DEP_NAME_S = dept.getNames();
		}
	}

	/**
	 * �v�㕔��̎擾
	 * 
	 * @return ����
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * �Ȗڂ̐ݒ�
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

			// �������t���O
			useItemOccurDate = false;

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

			// �������t���O
			useItemOccurDate = item.getPreferedItem().isUseOccurDate();
		}
	}

	/**
	 * �Ȗڂ̎擾
	 * 
	 * @return �Ȗ�
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * �s�E�v
	 * 
	 * @param remark �s�E�v
	 */
	public void setRemark(Remark remark) {
		this.remark = remark;

		if (remark == null) {
			sWK_GYO_TEK = null;
			sWK_GYO_TEK_CODE = null;

		} else {
			sWK_GYO_TEK_CODE = remark.getCode();
			sWK_GYO_TEK = remark.getName();
		}
	}

	/**
	 * �s�E�v
	 * 
	 * @return �s�E�v
	 */
	public Remark getRemark() {
		return remark;
	}

	/**
	 * �����
	 * 
	 * @param tax �����
	 */
	public void setTax(ConsumptionTax tax) {
		this.tax = tax;

		if (tax == null) {
			sWK_ZEI_CODE = null;
			sWK_ZEI_NAME = null;
			sWK_ZEI_NAME_S = null;
			sWK_ZEI_RATE = null;

		} else {
			sWK_ZEI_CODE = tax.getCode();
			sWK_ZEI_NAME = tax.getName();
			sWK_ZEI_NAME_S = tax.getNames();
			sWK_ZEI_RATE = tax.getRate();
		}
	}

	/**
	 * �����
	 * 
	 * @return �����
	 */
	public ConsumptionTax getTax() {
		return this.tax;
	}

	/**
	 * �ʉ�
	 * 
	 * @return �ʉ�
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * �ʉ�
	 * 
	 * @param currency �ʉ�
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;

		if (currency == null) {
			sWK_CUR_CODE = null;
			cUR_DEC_KETA = 0;
		} else {
			sWK_CUR_CODE = currency.getCode();
			cUR_DEC_KETA = currency.getDecimalPoint();
		}
	}

	/**
	 * �Ј�
	 * 
	 * @param employee �Ј�
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;

		if (employee == null) {
			sWK_EMP_CODE = null;
			sWK_EMP_NAME = null;
			sWK_EMP_NAME_S = null;

		} else {
			sWK_EMP_CODE = employee.getCode();
			sWK_EMP_NAME = employee.getName();
			sWK_EMP_NAME_S = employee.getNames();
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
	 * �����
	 * 
	 * @param customer �����
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
	 * �����
	 * 
	 * @return �����
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * �Ǘ�1
	 * 
	 * @param mng1 �Ǘ�1
	 */
	public void setManagement1(Management1 mng1) {
		this.management1 = mng1;

		if (management1 == null) {
			sWK_KNR_CODE_1 = null;
			sWK_KNR_NAME_1 = null;
			sWK_KNR_NAME_S_1 = null;

		} else {
			sWK_KNR_CODE_1 = management1.getCode();
			sWK_KNR_NAME_1 = management1.getName();
			sWK_KNR_NAME_S_1 = management1.getNames();
		}
	}

	/**
	 * �Ǘ�1
	 * 
	 * @return �Ǘ�1
	 */
	public Management1 getManagement1() {
		return management1;
	}

	/**
	 * �Ǘ�2
	 * 
	 * @param mng2 �Ǘ�2
	 */
	public void setManagement2(Management2 mng2) {
		this.management2 = mng2;

		if (management2 == null) {
			sWK_KNR_CODE_2 = null;
			sWK_KNR_NAME_2 = null;
			sWK_KNR_NAME_S_2 = null;

		} else {
			sWK_KNR_CODE_2 = management2.getCode();
			sWK_KNR_NAME_2 = management2.getName();
			sWK_KNR_NAME_S_2 = management2.getNames();
		}
	}

	/**
	 * �Ǘ�2
	 * 
	 * @return �Ǘ�2
	 */
	public Management2 getManagement2() {
		return management2;
	}

	/**
	 * �Ǘ�3
	 * 
	 * @param mng3 �Ǘ�3
	 */
	public void setManagement3(Management3 mng3) {
		this.management3 = mng3;

		if (management3 == null) {
			sWK_KNR_CODE_3 = null;
			sWK_KNR_NAME_3 = null;
			sWK_KNR_NAME_S_3 = null;

		} else {
			sWK_KNR_CODE_3 = management3.getCode();
			sWK_KNR_NAME_3 = management3.getName();
			sWK_KNR_NAME_S_3 = management3.getNames();
		}
	}

	/**
	 * �Ǘ�3
	 * 
	 * @return �Ǘ�3
	 */
	public Management3 getManagement3() {
		return management3;
	}

	/**
	 * �Ǘ�4
	 * 
	 * @param mng4 �Ǘ�4
	 */
	public void setManagement4(Management4 mng4) {
		this.management4 = mng4;

		if (management4 == null) {
			sWK_KNR_CODE_4 = null;
			sWK_KNR_NAME_4 = null;
			sWK_KNR_NAME_S_4 = null;

		} else {
			sWK_KNR_CODE_4 = management4.getCode();
			sWK_KNR_NAME_4 = management4.getName();
			sWK_KNR_NAME_S_4 = management4.getNames();
		}
	}

	/**
	 * �Ǘ�4
	 * 
	 * @return �Ǘ�4
	 */
	public Management4 getManagement4() {
		return management4;
	}

	/**
	 * �Ǘ�5
	 * 
	 * @param mng5 �Ǘ�5
	 */
	public void setManagement5(Management5 mng5) {
		this.management5 = mng5;

		if (management5 == null) {
			sWK_KNR_CODE_5 = null;
			sWK_KNR_NAME_5 = null;
			sWK_KNR_NAME_S_5 = null;

		} else {
			sWK_KNR_CODE_5 = management5.getCode();
			sWK_KNR_NAME_5 = management5.getName();
			sWK_KNR_NAME_S_5 = management5.getNames();
		}
	}

	/**
	 * �Ǘ�5
	 * 
	 * @return �Ǘ�5
	 */
	public Management5 getManagement5() {
		return management5;
	}

	/**
	 * �Ǘ�6
	 * 
	 * @param mng6 �Ǘ�6
	 */
	public void setManagement6(Management6 mng6) {
		this.management6 = mng6;

		if (management6 == null) {
			sWK_KNR_CODE_6 = null;
			sWK_KNR_NAME_6 = null;
			sWK_KNR_NAME_S_6 = null;

		} else {
			sWK_KNR_CODE_6 = management6.getCode();
			sWK_KNR_NAME_6 = management6.getName();
			sWK_KNR_NAME_S_6 = management6.getNames();
		}
	}

	/**
	 * �Ǘ�6
	 * 
	 * @return �Ǘ�6
	 */
	public Management6 getManagement6() {
		return management6;
	}

	/**
	 * ���Z�d�󂩂ǂ���
	 * 
	 * @return true:���Z�d��
	 */
	public boolean isSettlementJornal() {
		return settlementJornal;
	}

	/**
	 * ���Z�d�󂩂ǂ���
	 * 
	 * @param settlementJornal true:���Z�d��
	 */
	public void setSettlementJornal(boolean settlementJornal) {
		this.settlementJornal = settlementJornal;
	}

	/**
	 * BS��������d��̎擾
	 * 
	 * @return bsDetail BS��������d��
	 */
	public SWK_DTL getBsDetail() {
		return bsDetail;
	}

	/**
	 * BS��������d��̐ݒ�
	 * 
	 * @param bsDetail BS��������d��
	 */
	public void setBsDetail(SWK_DTL bsDetail) {
		this.bsDetail = bsDetail;
	}

	/**
	 * �ݎ؋t�]
	 */
	public void reverseDC() {
		setDC(isDR() ? Dc.CREDIT : Dc.DEBIT);
	}

	/**
	 * getDC()�𗘗p���邱��
	 * 
	 * @deprecated
	 * @return �ݎ�
	 */
	@Deprecated
	public Dc getDc() {
		return Dc.getDc(sWK_DC_KBN);
	}

	/**
	 * AP/AR�����敪��ݒ肷��
	 * 
	 * @param sWK_APAR_KESI_KBN
	 */
	public void setSWK_APAR_KESI_KBN(int sWK_APAR_KESI_KBN) {
		this.sWK_APAR_KESI_KBN = sWK_APAR_KESI_KBN;
	}

	/**
	 * AP/AR�����敪���擾����
	 * 
	 * @return sWK_APAR_KESI_KBN
	 */
	public int getSWK_APAR_KESI_KBN() {
		return this.sWK_APAR_KESI_KBN;
	}

	/**
	 * AP/AR�����`�[�ԍ���ݒ肷��
	 * 
	 * @param sWK_APAR_DEN_NO
	 */
	public void setSWK_APAR_DEN_NO(String sWK_APAR_DEN_NO) {
		this.sWK_APAR_DEN_NO = sWK_APAR_DEN_NO;
	}

	/**
	 * AP/AR�����`�[�ԍ����擾����
	 * 
	 * @return sWK_APAR_DEN_NO
	 */
	public String getSWK_APAR_DEN_NO() {
		return this.sWK_APAR_DEN_NO;
	}

	/**
	 * AP/AR�����s�ԍ���ݒ肷��
	 * 
	 * @param sWK_APAR_GYO_NO
	 */
	public void setSWK_APAR_GYO_NO(Integer sWK_APAR_GYO_NO) {
		this.sWK_APAR_GYO_NO = sWK_APAR_GYO_NO;
	}

	/**
	 * AP/AR�����s�ԍ����擾����
	 * 
	 * @return sWK_APAR_GYO_NO
	 */
	public Integer getSWK_APAR_GYO_NO() {
		return this.sWK_APAR_GYO_NO;
	}

	/**
	 * �d�󖾍חpOP�A�C�e��(���ӁFDB�֌W�Ȃ�OP�����p�̂�)�̎擾
	 * 
	 * @return journalOPItem �d�󖾍חpOP�A�C�e��(���ӁFDB�֌W�Ȃ�OP�����p�̂�)
	 */
	public OPItem getJournalOPItem() {
		return journalOPItem;
	}

	/**
	 * �d�󖾍חpOP�A�C�e��(���ӁFDB�֌W�Ȃ�OP�����p�̂�)�̐ݒ�
	 * 
	 * @param journalOPItem �d�󖾍חpOP�A�C�e��(���ӁFDB�֌W�Ȃ�OP�����p�̂�)
	 */
	public void setJournalOPItem(OPItem journalOPItem) {
		this.journalOPItem = journalOPItem;
	}

	/**
	 * �`�[�ǉ����(���ӁFDB�֌W�Ȃ�OP�����p�̂�)�̎擾
	 * 
	 * @return addonInfo �`�[�ǉ����(���ӁFDB�֌W�Ȃ�OP�����p�̂�)
	 */
	public SlipDetailAddonInfo getAddonInfo() {
		return addonInfo;
	}

	/**
	 * �`�[�ǉ����(���ӁFDB�֌W�Ȃ�OP�����p�̂�)�̐ݒ�
	 * 
	 * @param addonInfo �`�[�ǉ����(���ӁFDB�֌W�Ȃ�OP�����p�̂�)
	 */
	public void setAddonInfo(SlipDetailAddonInfo addonInfo) {
		this.addonInfo = addonInfo;
	}

	/**
	 * ���R�敪�̎擾
	 * 
	 * @return SWK_FREE_KBN ���R�敪
	 */
	public int getSWK_FREE_KBN() {
		return SWK_FREE_KBN;
	}

	/**
	 * ���R�敪�̐ݒ�
	 * 
	 * @param SWK_FREE_KBN ���R�敪
	 */
	public void setSWK_FREE_KBN(int SWK_FREE_KBN) {
		this.SWK_FREE_KBN = SWK_FREE_KBN;
	}

	/**
	 * �o�ߑ[�u�̌��s�ԍ��̎擾
	 * 
	 * @return SWK_ORI_GYO_NO �o�ߑ[�u�̌��s�ԍ�
	 */
	public Integer getSWK_ORI_GYO_NO() {
		return SWK_ORI_GYO_NO;
	}

	/**
	 * �o�ߑ[�u�̌��s�ԍ��̐ݒ�
	 * 
	 * @param SWK_ORI_GYO_NO �o�ߑ[�u�̌��s�ԍ�
	 */
	public void setSWK_ORI_GYO_NO(Integer SWK_ORI_GYO_NO) {
		this.SWK_ORI_GYO_NO = SWK_ORI_GYO_NO;
	}

	/**
	 * �o�ߑ[�u�d��ł��邩�ǂ����̎擾
	 * 
	 * @return isKekaDtl �o�ߑ[�u�d��ł��邩�ǂ��� true�F�o�ߑ[�u�d��
	 */
	public boolean isKekaDtl() {
		return isKekaDtl;
	}

	/**
	 * �o�ߑ[�u�d��ł��邩�ǂ����̐ݒ�
	 * 
	 * @param isKekaDtl �o�ߑ[�u�d��ł��邩�ǂ��� true�F�o�ߑ[�u�d��
	 */
	public void setKekaDtl(boolean isKekaDtl) {
		this.isKekaDtl = isKekaDtl;
	}

	/**
	 * �o�ߑ[�u�T���\��(1%�`99%)�̎擾
	 * 
	 * @return KEKA_SOTI_RATE �o�ߑ[�u�T���\��(1%�`99%)
	 */
	public BigDecimal getKEKA_SOTI_RATE() {
		return KEKA_SOTI_RATE;
	}

	/**
	 * �o�ߑ[�u�T���\��(1%�`99%)�̐ݒ�
	 * 
	 * @param KEKA_SOTI_RATE �o�ߑ[�u�T���\��(1%�`99%)
	 */
	public void setKEKA_SOTI_RATE(BigDecimal KEKA_SOTI_RATE) {
		this.KEKA_SOTI_RATE = KEKA_SOTI_RATE;
	}

}
