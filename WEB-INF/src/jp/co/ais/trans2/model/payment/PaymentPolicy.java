package jp.co.ais.trans2.model.payment;

import java.math.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �x�����j���
 */
public class PaymentPolicy extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �莞�x���i�P���j */
	protected int sHH_SIHA_1 = 0;

	/** �莞�x���i�T���j */
	protected int sHH_SIHA_5 = 0;

	/** �莞�x���i10���j */
	protected int sHH_SIHA_10 = 0;

	/** �莞�x���i15���j */
	protected int sHH_SIHA_15 = 0;

	/** �莞�x���i20���j */
	protected int sHH_SIHA_20 = 0;

	/** �莞�x���i25���j */
	protected int sHH_SIHA_25 = 0;

	/** �莞�x���i�����j */
	protected int sHH_SIHA_30 = 0;

	/** ��s�x���敪�i1���j */
	protected int sHH_BNK_KBN_1 = 0;

	/** ��s�x���敪�i5���j */
	protected int sHH_BNK_KBN_5 = 0;

	/** ��s�x���敪�i10���j */
	protected int sHH_BNK_KBN_10 = 0;

	/** ��s�x���敪�i15���j */
	protected int sHH_BNK_KBN_15 = 0;

	/** ��s�x���敪�i20���j */
	protected int sHH_BNK_KBN_20 = 0;

	/** ��s�x���敪�i25���j */
	protected int sHH_BNK_KBN_25 = 0;

	/** ��s�x���敪�i�����j */
	protected int sHH_BNK_KBN_30 = 0;

	/** �x�������z */
	protected BigDecimal sHH_SIHA_MIN = null;

	/** �U���萔�������z */
	protected BigDecimal sHH_FURI_MIN = null;

	/** �萔���ȖڃR�[�h */
	protected String sHH_TESU_KMK_CODE = null;

	/** �萔���⏕�ȖڃR�[�h */
	protected String sHH_TESU_HKM_CODE = null;

	/** �萔������ȖڃR�[�h */
	protected String sHH_TESU_UKM_CODE = null;

	/** �萔���v�㕔��R�[�h */
	protected String sHH_TESU_DEP_CODE = null;

	/** �萔������ŃR�[�h */
	protected String sHH_TESU_ZEI_CODE = null;

	/** �O�������쐬�t���O */
	protected String sHH_GS_PRINT_KBN = null;

	/** �������ԍ��t���O */
	protected String sHH_SEI_NO_KBN = null;

	/** �ЊO�x��FB�f�[�^�o�͐� */
	protected String fbOutputPath;

	/** �O�������˗��f�[�^�o�͐� */
	protected String remmitanceOutputPath;

	/** �Ј��x��FB�f�[�^�o�͐� */
	protected String employeeFbOutputPath;

	/**
	 * �Ј��x��FB�f�[�^�o�͐�
	 * 
	 * @return fb FB�f�[�^�o�͐��߂��܂��B
	 */
	public String getEmployeeFbOutputPath() {
		return this.employeeFbOutputPath;
	}

	/**
	 * �Ј��x��FB�f�[�^�o�͐�
	 * 
	 * @param path FB�f�[�^�o�͐��ݒ肵�܂��B
	 */
	public void setEmployeeFbOutputPath(String path) {
		this.employeeFbOutputPath = path;
	}

	/**
	 * @return companyCode ��ЃR�[�h��߂��܂��B
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * @param companyCode ��ЃR�[�h��ݒ肵�܂��B
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �ЊO�x��FB�f�[�^�o�͐�
	 * 
	 * @return �ЊO�x��FB�f�[�^�o�͐�
	 */
	public String getFbOutputPath() {
		return this.fbOutputPath;
	}

	/**
	 * �ЊO�x��FB�f�[�^�o�͐�
	 * 
	 * @param fbOutputPath �ЊO�x��FB�f�[�^�o�͐�
	 */
	public void setFbOutputPath(String fbOutputPath) {
		this.fbOutputPath = fbOutputPath;
	}

	/**
	 * �O�������˗��f�[�^�o�͐�
	 * 
	 * @return �O�������˗��f�[�^�o�͐�
	 */
	public String getRemmitanceOutputPath() {
		return this.remmitanceOutputPath;
	}

	/**
	 * �O�������˗��f�[�^�o�͐�
	 * 
	 * @param remmitanceOutputPath �O�������˗��f�[�^�o�͐�
	 */
	public void setRemmitanceOutputPath(String remmitanceOutputPath) {
		this.remmitanceOutputPath = remmitanceOutputPath;
	}

	/**
	 * �莞�x���i�P���j�̎擾
	 * 
	 * @return sHH_SIHA_1 �莞�x���i�P���j
	 */
	public int getSHH_SIHA_1() {
		return sHH_SIHA_1;
	}

	/**
	 * �莞�x���i�P���j�̐ݒ�
	 * 
	 * @param sHH_SIHA_1 �莞�x���i�P���j
	 */
	public void setSHH_SIHA_1(int sHH_SIHA_1) {
		this.sHH_SIHA_1 = sHH_SIHA_1;
	}

	/**
	 * �莞�x���i�T���j�̎擾
	 * 
	 * @return sHH_SIHA_5 �莞�x���i�T���j
	 */
	public int getSHH_SIHA_5() {
		return sHH_SIHA_5;
	}

	/**
	 * �莞�x���i�T���j�̐ݒ�
	 * 
	 * @param sHH_SIHA_5 �莞�x���i�T���j
	 */
	public void setSHH_SIHA_5(int sHH_SIHA_5) {
		this.sHH_SIHA_5 = sHH_SIHA_5;
	}

	/**
	 * �莞�x���i10���j�̎擾
	 * 
	 * @return sHH_SIHA_10 �莞�x���i10���j
	 */
	public int getSHH_SIHA_10() {
		return sHH_SIHA_10;
	}

	/**
	 * �莞�x���i10���j�̐ݒ�
	 * 
	 * @param sHH_SIHA_10 �莞�x���i10���j
	 */
	public void setSHH_SIHA_10(int sHH_SIHA_10) {
		this.sHH_SIHA_10 = sHH_SIHA_10;
	}

	/**
	 * �莞�x���i15���j�̎擾
	 * 
	 * @return sHH_SIHA_15 �莞�x���i15���j
	 */
	public int getSHH_SIHA_15() {
		return sHH_SIHA_15;
	}

	/**
	 * �莞�x���i15���j�̐ݒ�
	 * 
	 * @param sHH_SIHA_15 �莞�x���i15���j
	 */
	public void setSHH_SIHA_15(int sHH_SIHA_15) {
		this.sHH_SIHA_15 = sHH_SIHA_15;
	}

	/**
	 * �莞�x���i20���j�̎擾
	 * 
	 * @return sHH_SIHA_20 �莞�x���i20���j
	 */
	public int getSHH_SIHA_20() {
		return sHH_SIHA_20;
	}

	/**
	 * �莞�x���i20���j�̐ݒ�
	 * 
	 * @param sHH_SIHA_20 �莞�x���i20���j
	 */
	public void setSHH_SIHA_20(int sHH_SIHA_20) {
		this.sHH_SIHA_20 = sHH_SIHA_20;
	}

	/**
	 * �莞�x���i25���j�̎擾
	 * 
	 * @return sHH_SIHA_25 �莞�x���i25���j
	 */
	public int getSHH_SIHA_25() {
		return sHH_SIHA_25;
	}

	/**
	 * �莞�x���i25���j�̐ݒ�
	 * 
	 * @param sHH_SIHA_25 �莞�x���i25���j
	 */
	public void setSHH_SIHA_25(int sHH_SIHA_25) {
		this.sHH_SIHA_25 = sHH_SIHA_25;
	}

	/**
	 * �莞�x���i�����j�̎擾
	 * 
	 * @return sHH_SIHA_30 �莞�x���i�����j
	 */
	public int getSHH_SIHA_30() {
		return sHH_SIHA_30;
	}

	/**
	 * �莞�x���i�����j�̐ݒ�
	 * 
	 * @param sHH_SIHA_30 �莞�x���i�����j
	 */
	public void setSHH_SIHA_30(int sHH_SIHA_30) {
		this.sHH_SIHA_30 = sHH_SIHA_30;
	}

	/**
	 * ��s�x���敪�i1���j�̎擾
	 * 
	 * @return sHH_BNK_KBN_1 ��s�x���敪�i1���j
	 */
	public int getSHH_BNK_KBN_1() {
		return sHH_BNK_KBN_1;
	}

	/**
	 * ��s�x���敪�i1���j�̐ݒ�
	 * 
	 * @param sHH_BNK_KBN_1 ��s�x���敪�i1���j
	 */
	public void setSHH_BNK_KBN_1(int sHH_BNK_KBN_1) {
		this.sHH_BNK_KBN_1 = sHH_BNK_KBN_1;
	}

	/**
	 * ��s�x���敪�i5���j�̎擾
	 * 
	 * @return sHH_BNK_KBN_5 ��s�x���敪�i5���j
	 */
	public int getSHH_BNK_KBN_5() {
		return sHH_BNK_KBN_5;
	}

	/**
	 * ��s�x���敪�i5���j�̐ݒ�
	 * 
	 * @param sHH_BNK_KBN_5 ��s�x���敪�i5���j
	 */
	public void setSHH_BNK_KBN_5(int sHH_BNK_KBN_5) {
		this.sHH_BNK_KBN_5 = sHH_BNK_KBN_5;
	}

	/**
	 * ��s�x���敪�i10���j�̎擾
	 * 
	 * @return sHH_BNK_KBN_10 ��s�x���敪�i10���j
	 */
	public int getSHH_BNK_KBN_10() {
		return sHH_BNK_KBN_10;
	}

	/**
	 * ��s�x���敪�i10���j�̐ݒ�
	 * 
	 * @param sHH_BNK_KBN_10 ��s�x���敪�i10���j
	 */
	public void setSHH_BNK_KBN_10(int sHH_BNK_KBN_10) {
		this.sHH_BNK_KBN_10 = sHH_BNK_KBN_10;
	}

	/**
	 * ��s�x���敪�i15���j�̎擾
	 * 
	 * @return sHH_BNK_KBN_15 ��s�x���敪�i15���j
	 */
	public int getSHH_BNK_KBN_15() {
		return sHH_BNK_KBN_15;
	}

	/**
	 * ��s�x���敪�i15���j�̐ݒ�
	 * 
	 * @param sHH_BNK_KBN_15 ��s�x���敪�i15���j
	 */
	public void setSHH_BNK_KBN_15(int sHH_BNK_KBN_15) {
		this.sHH_BNK_KBN_15 = sHH_BNK_KBN_15;
	}

	/**
	 * ��s�x���敪�i20���j�̎擾
	 * 
	 * @return sHH_BNK_KBN_20 ��s�x���敪�i20���j
	 */
	public int getSHH_BNK_KBN_20() {
		return sHH_BNK_KBN_20;
	}

	/**
	 * ��s�x���敪�i20���j�̐ݒ�
	 * 
	 * @param sHH_BNK_KBN_20 ��s�x���敪�i20���j
	 */
	public void setSHH_BNK_KBN_20(int sHH_BNK_KBN_20) {
		this.sHH_BNK_KBN_20 = sHH_BNK_KBN_20;
	}

	/**
	 * ��s�x���敪�i25���j�̎擾
	 * 
	 * @return sHH_BNK_KBN_25 ��s�x���敪�i25���j
	 */
	public int getSHH_BNK_KBN_25() {
		return sHH_BNK_KBN_25;
	}

	/**
	 * ��s�x���敪�i25���j�̐ݒ�
	 * 
	 * @param sHH_BNK_KBN_25 ��s�x���敪�i25���j
	 */
	public void setSHH_BNK_KBN_25(int sHH_BNK_KBN_25) {
		this.sHH_BNK_KBN_25 = sHH_BNK_KBN_25;
	}

	/**
	 * ��s�x���敪�i�����j�̎擾
	 * 
	 * @return sHH_BNK_KBN_30 ��s�x���敪�i�����j
	 */
	public int getSHH_BNK_KBN_30() {
		return sHH_BNK_KBN_30;
	}

	/**
	 * ��s�x���敪�i�����j�̐ݒ�
	 * 
	 * @param sHH_BNK_KBN_30 ��s�x���敪�i�����j
	 */
	public void setSHH_BNK_KBN_30(int sHH_BNK_KBN_30) {
		this.sHH_BNK_KBN_30 = sHH_BNK_KBN_30;
	}

	/**
	 * �x�������z�̎擾
	 * 
	 * @return sHH_SIHA_MIN �x�������z
	 */
	public BigDecimal getSHH_SIHA_MIN() {
		return sHH_SIHA_MIN;
	}

	/**
	 * �x�������z�̐ݒ�
	 * 
	 * @param sHH_SIHA_MIN �x�������z
	 */
	public void setSHH_SIHA_MIN(BigDecimal sHH_SIHA_MIN) {
		this.sHH_SIHA_MIN = sHH_SIHA_MIN;
	}

	/**
	 * �U���萔�������z�̎擾
	 * 
	 * @return sHH_FURI_MIN �U���萔�������z
	 */
	public BigDecimal getSHH_FURI_MIN() {
		return sHH_FURI_MIN;
	}

	/**
	 * �U���萔�������z�̐ݒ�
	 * 
	 * @param sHH_FURI_MIN �U���萔�������z
	 */
	public void setSHH_FURI_MIN(BigDecimal sHH_FURI_MIN) {
		this.sHH_FURI_MIN = sHH_FURI_MIN;
	}

	/**
	 * �萔���ȖڃR�[�h�̎擾
	 * 
	 * @return sHH_TESU_KMK_CODE �萔���ȖڃR�[�h
	 */
	public String getSHH_TESU_KMK_CODE() {
		return sHH_TESU_KMK_CODE;
	}

	/**
	 * �萔���ȖڃR�[�h�̐ݒ�
	 * 
	 * @param sHH_TESU_KMK_CODE �萔���ȖڃR�[�h
	 */
	public void setSHH_TESU_KMK_CODE(String sHH_TESU_KMK_CODE) {
		this.sHH_TESU_KMK_CODE = sHH_TESU_KMK_CODE;
	}

	/**
	 * �萔���⏕�ȖڃR�[�h�̎擾
	 * 
	 * @return sHH_TESU_HKM_CODE �萔���⏕�ȖڃR�[�h
	 */
	public String getSHH_TESU_HKM_CODE() {
		return sHH_TESU_HKM_CODE;
	}

	/**
	 * �萔���⏕�ȖڃR�[�h�̐ݒ�
	 * 
	 * @param sHH_TESU_HKM_CODE �萔���⏕�ȖڃR�[�h
	 */
	public void setSHH_TESU_HKM_CODE(String sHH_TESU_HKM_CODE) {
		this.sHH_TESU_HKM_CODE = sHH_TESU_HKM_CODE;
	}

	/**
	 * �萔������ȖڃR�[�h�̎擾
	 * 
	 * @return sHH_TESU_UKM_CODE �萔������ȖڃR�[�h
	 */
	public String getSHH_TESU_UKM_CODE() {
		return sHH_TESU_UKM_CODE;
	}

	/**
	 * �萔������ȖڃR�[�h�̐ݒ�
	 * 
	 * @param sHH_TESU_UKM_CODE �萔������ȖڃR�[�h
	 */
	public void setSHH_TESU_UKM_CODE(String sHH_TESU_UKM_CODE) {
		this.sHH_TESU_UKM_CODE = sHH_TESU_UKM_CODE;
	}

	/**
	 * �萔���v�㕔��R�[�h�̎擾
	 * 
	 * @return sHH_TESU_DEP_CODE �萔���v�㕔��R�[�h
	 */
	public String getSHH_TESU_DEP_CODE() {
		return sHH_TESU_DEP_CODE;
	}

	/**
	 * �萔���v�㕔��R�[�h�̐ݒ�
	 * 
	 * @param sHH_TESU_DEP_CODE �萔���v�㕔��R�[�h
	 */
	public void setSHH_TESU_DEP_CODE(String sHH_TESU_DEP_CODE) {
		this.sHH_TESU_DEP_CODE = sHH_TESU_DEP_CODE;
	}

	/**
	 * �萔������ŃR�[�h�̎擾
	 * 
	 * @return sHH_TESU_ZEI_CODE �萔������ŃR�[�h
	 */
	public String getSHH_TESU_ZEI_CODE() {
		return sHH_TESU_ZEI_CODE;
	}

	/**
	 * �萔������ŃR�[�h�̐ݒ�
	 * 
	 * @param sHH_TESU_ZEI_CODE �萔������ŃR�[�h
	 */
	public void setSHH_TESU_ZEI_CODE(String sHH_TESU_ZEI_CODE) {
		this.sHH_TESU_ZEI_CODE = sHH_TESU_ZEI_CODE;
	}

	/**
	 * �O�������쐬�t���O�̎擾
	 * 
	 * @return sHH_GS_PRINT_KBN �O�������쐬�t���O
	 */
	public String getSHH_GS_PRINT_KBN() {
		return sHH_GS_PRINT_KBN;
	}

	/**
	 * �O�������쐬�t���O�̐ݒ�
	 * 
	 * @param sHH_GS_PRINT_KBN �O�������쐬�t���O
	 */
	public void setSHH_GS_PRINT_KBN(String sHH_GS_PRINT_KBN) {
		this.sHH_GS_PRINT_KBN = sHH_GS_PRINT_KBN;
	}

	/**
	 * �������ԍ��t���O�̎擾
	 * 
	 * @return sHH_SEI_NO_KBN �������ԍ��t���O
	 */
	public String getSHH_SEI_NO_KBN() {
		return sHH_SEI_NO_KBN;
	}

	/**
	 * �������ԍ��t���O�̐ݒ�
	 * 
	 * @param sHH_SEI_NO_KBN �������ԍ��t���O
	 */
	public void setSHH_SEI_NO_KBN(String sHH_SEI_NO_KBN) {
		this.sHH_SEI_NO_KBN = sHH_SEI_NO_KBN;
	}

}