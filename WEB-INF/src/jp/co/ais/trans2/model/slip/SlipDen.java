package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �����Ώۂ̓`�[
 */
public class SlipDen extends TransferBase {

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** �`�[�ԍ� */
	protected String SWK_DEN_NO = null;

	/** �`�[���t */
	protected Date SWK_DEN_DATE = null;

	/** BEFORE�`�[�ԍ� */
	protected String SWK_BEFORE_DEN_NO = null;

	/** �O��X�V�敪 */
	protected int SWK_BEFORE_UPD_KBN = -1;

	/** �f�[�^�敪 */
	protected String SWK_DATA_KBN = null;

	/** �X�V�敪 */
	protected int SWK_UPD_KBN = -1;

	/** ���F�҃R�[�h */
	protected String SWK_SYO_EMP_CODE = null;

	/** ���F�Җ��� */
	protected String SWK_SYO_EMP_NAME = null;

	/** ���F�җ��� */
	protected String SWK_SYO_EMP_NAME_S = null;

	/** ���F�� */
	protected Date SWK_SYO_DATE = null;

	/** �˗��� */
	protected String SWK_IRAI_EMP_CODE = null;

	/** �`�[�E�v */
	protected String SWK_TEK = null;

	/** �`�[��� */
	protected String SWK_DEN_SYU = null;

	/** �ʉ݃R�[�h */
	protected String SWK_CUR_CODE = null;

	/** �C���� */
	protected int SWK_UPD_CNT = -1;

	/** ���Z�敪 */
	protected int SWK_KSN_KBN = -1;

	/** �o�����F�t���O */
	protected boolean ACCT_APRV_FLG = false;

	/** ���̏��F�҃R�[�h */
	protected String APRV_ROLE_CODE = null;

	/** ���̏��F�҃R�[�h */
	protected String NEXT_APRV_ROLE_CODE = null;

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return KAI_CODE ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param KAI_CODE ��ЃR�[�h
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * �`�[�ԍ��̎擾
	 * 
	 * @return SWK_DEN_NO �`�[�ԍ�
	 */
	public String getSWK_DEN_NO() {
		return SWK_DEN_NO;
	}

	/**
	 * �`�[�ԍ��̐ݒ�
	 * 
	 * @param SWK_DEN_NO �`�[�ԍ�
	 */
	public void setSWK_DEN_NO(String SWK_DEN_NO) {
		this.SWK_DEN_NO = SWK_DEN_NO;
	}

	/**
	 * �`�[���t�̎擾
	 * 
	 * @return SWK_DEN_DATE �`�[���t
	 */
	public Date getSWK_DEN_DATE() {
		return SWK_DEN_DATE;
	}

	/**
	 * �`�[���t�̐ݒ�
	 * 
	 * @param SWK_DEN_DATE �`�[���t
	 */
	public void setSWK_DEN_DATE(Date SWK_DEN_DATE) {
		this.SWK_DEN_DATE = SWK_DEN_DATE;
	}

	/**
	 * BEFORE�`�[�ԍ��̎擾
	 * 
	 * @return SWK_BEFORE_DEN_NO BEFORE�`�[�ԍ�
	 */
	public String getSWK_BEFORE_DEN_NO() {
		return SWK_BEFORE_DEN_NO;
	}

	/**
	 * BEFORE�`�[�ԍ��̐ݒ�
	 * 
	 * @param SWK_BEFORE_DEN_NO BEFORE�`�[�ԍ�
	 */
	public void setSWK_BEFORE_DEN_NO(String SWK_BEFORE_DEN_NO) {
		this.SWK_BEFORE_DEN_NO = SWK_BEFORE_DEN_NO;
	}

	/**
	 * �O��X�V�敪�̎擾
	 * 
	 * @return SWK_BEFORE_UPD_KBN �O��X�V�敪
	 */
	public int getSWK_BEFORE_UPD_KBN() {
		return SWK_BEFORE_UPD_KBN;
	}

	/**
	 * �O��X�V�敪�̐ݒ�
	 * 
	 * @param SWK_BEFORE_UPD_KBN �O��X�V�敪
	 */
	public void setSWK_BEFORE_UPD_KBN(int SWK_BEFORE_UPD_KBN) {
		this.SWK_BEFORE_UPD_KBN = SWK_BEFORE_UPD_KBN;
	}

	/**
	 * �f�[�^�敪�̎擾
	 * 
	 * @return SWK_DATA_KBN �f�[�^�敪
	 */
	public String getSWK_DATA_KBN() {
		return SWK_DATA_KBN;
	}

	/**
	 * �f�[�^�敪�̐ݒ�
	 * 
	 * @param SWK_DATA_KBN �f�[�^�敪
	 */
	public void setSWK_DATA_KBN(String SWK_DATA_KBN) {
		this.SWK_DATA_KBN = SWK_DATA_KBN;
	}

	/**
	 * �X�V�敪�̎擾
	 * 
	 * @return SWK_UPD_KBN �X�V�敪
	 */
	public int getSWK_UPD_KBN() {
		return SWK_UPD_KBN;
	}

	/**
	 * �X�V�敪�̐ݒ�
	 * 
	 * @param SWK_UPD_KBN �X�V�敪
	 */
	public void setSWK_UPD_KBN(int SWK_UPD_KBN) {
		this.SWK_UPD_KBN = SWK_UPD_KBN;
	}

	/**
	 * ���F�҃R�[�h�̎擾
	 * 
	 * @return SWK_SYO_EMP_CODE ���F�҃R�[�h
	 */
	public String getSWK_SYO_EMP_CODE() {
		return SWK_SYO_EMP_CODE;
	}

	/**
	 * ���F�҃R�[�h�̐ݒ�
	 * 
	 * @param SWK_SYO_EMP_CODE ���F�҃R�[�h
	 */
	public void setSWK_SYO_EMP_CODE(String SWK_SYO_EMP_CODE) {
		this.SWK_SYO_EMP_CODE = SWK_SYO_EMP_CODE;
	}

	/**
	 * ���F�Җ��̂̎擾
	 * 
	 * @return SWK_SYO_EMP_NAME ���F�Җ���
	 */
	public String getSWK_SYO_EMP_NAME() {
		return SWK_SYO_EMP_NAME;
	}

	/**
	 * ���F�Җ��̂̐ݒ�
	 * 
	 * @param SWK_SYO_EMP_NAME ���F�Җ���
	 */
	public void setSWK_SYO_EMP_NAME(String SWK_SYO_EMP_NAME) {
		this.SWK_SYO_EMP_NAME = SWK_SYO_EMP_NAME;
	}

	/**
	 * ���F�җ��̂̎擾
	 * 
	 * @return SWK_SYO_EMP_NAME_S ���F�җ���
	 */
	public String getSWK_SYO_EMP_NAME_S() {
		return SWK_SYO_EMP_NAME_S;
	}

	/**
	 * ���F�җ��̂̐ݒ�
	 * 
	 * @param SWK_SYO_EMP_NAME_S ���F�җ���
	 */
	public void setSWK_SYO_EMP_NAME_S(String SWK_SYO_EMP_NAME_S) {
		this.SWK_SYO_EMP_NAME_S = SWK_SYO_EMP_NAME_S;
	}

	/**
	 * ���F���̎擾
	 * 
	 * @return SWK_SYO_DATE ���F��
	 */
	public Date getSWK_SYO_DATE() {
		return SWK_SYO_DATE;
	}

	/**
	 * ���F���̐ݒ�
	 * 
	 * @param SWK_SYO_DATE ���F��
	 */
	public void setSWK_SYO_DATE(Date SWK_SYO_DATE) {
		this.SWK_SYO_DATE = SWK_SYO_DATE;
	}

	/**
	 * �˗��҂̎擾
	 * 
	 * @return SWK_IRAI_EMP_CODE �˗���
	 */
	public String getSWK_IRAI_EMP_CODE() {
		return SWK_IRAI_EMP_CODE;
	}

	/**
	 * �˗��҂̐ݒ�
	 * 
	 * @param SWK_IRAI_EMP_CODE �˗���
	 */
	public void setSWK_IRAI_EMP_CODE(String SWK_IRAI_EMP_CODE) {
		this.SWK_IRAI_EMP_CODE = SWK_IRAI_EMP_CODE;
	}

	/**
	 * �`�[�E�v�̎擾
	 * 
	 * @return SWK_TEK �`�[�E�v
	 */
	public String getSWK_TEK() {
		return SWK_TEK;
	}

	/**
	 * �`�[�E�v�̐ݒ�
	 * 
	 * @param SWK_TEK �`�[�E�v
	 */
	public void setSWK_TEK(String SWK_TEK) {
		this.SWK_TEK = SWK_TEK;
	}

	/**
	 * �`�[��ʂ̎擾
	 * 
	 * @return SWK_DEN_SYU �`�[���
	 */
	public String getSWK_DEN_SYU() {
		return SWK_DEN_SYU;
	}

	/**
	 * �`�[��ʂ̐ݒ�
	 * 
	 * @param SWK_DEN_SYU �`�[���
	 */
	public void setSWK_DEN_SYU(String SWK_DEN_SYU) {
		this.SWK_DEN_SYU = SWK_DEN_SYU;
	}

	/**
	 * �ʉ݃R�[�h�̎擾
	 * 
	 * @return SWK_CUR_CODE �ʉ݃R�[�h
	 */
	public String getSWK_CUR_CODE() {
		return SWK_CUR_CODE;
	}

	/**
	 * �ʉ݃R�[�h�̐ݒ�
	 * 
	 * @param SWK_CUR_CODE �ʉ݃R�[�h
	 */
	public void setSWK_CUR_CODE(String SWK_CUR_CODE) {
		this.SWK_CUR_CODE = SWK_CUR_CODE;
	}

	/**
	 * �C���񐔂̎擾
	 * 
	 * @return SWK_UPD_CNT �C����
	 */
	public int getSWK_UPD_CNT() {
		return SWK_UPD_CNT;
	}

	/**
	 * �C���񐔂̐ݒ�
	 * 
	 * @param SWK_UPD_CNT �C����
	 */
	public void setSWK_UPD_CNT(int SWK_UPD_CNT) {
		this.SWK_UPD_CNT = SWK_UPD_CNT;
	}

	/**
	 * ���Z�敪�̎擾
	 * 
	 * @return SWK_KSN_KBN ���Z�敪
	 */
	public int getSWK_KSN_KBN() {
		return SWK_KSN_KBN;
	}

	/**
	 * ���Z�敪�̐ݒ�
	 * 
	 * @param SWK_KSN_KBN ���Z�敪
	 */
	public void setSWK_KSN_KBN(int SWK_KSN_KBN) {
		this.SWK_KSN_KBN = SWK_KSN_KBN;
	}

	/**
	 * �o�����F�t���O�̎擾
	 * 
	 * @return ACCT_APRV_FLG �o�����F�t���O
	 */
	public boolean isACCT_APRV_FLG() {
		return ACCT_APRV_FLG;
	}

	/**
	 * �o�����F�t���O�̐ݒ�
	 * 
	 * @param ACCT_APRV_FLG �o�����F�t���O
	 */
	public void setACCT_APRV_FLG(boolean ACCT_APRV_FLG) {
		this.ACCT_APRV_FLG = ACCT_APRV_FLG;
	}

	/**
	 * ���̏��F�҃R�[�h�̎擾
	 * 
	 * @return APRV_ROLE_CODE ���̏��F�҃R�[�h
	 */
	public String getAPRV_ROLE_CODE() {
		return APRV_ROLE_CODE;
	}

	/**
	 * ���̏��F�҃R�[�h�̐ݒ�
	 * 
	 * @param APRV_ROLE_CODE ���̏��F�҃R�[�h
	 */
	public void setAPRV_ROLE_CODE(String APRV_ROLE_CODE) {
		this.APRV_ROLE_CODE = APRV_ROLE_CODE;
	}

	/**
	 * ���̏��F�҃R�[�h�̎擾
	 * 
	 * @return NEXT_APRV_ROLE_CODE ���̏��F�҃R�[�h
	 */
	public String getNEXT_APRV_ROLE_CODE() {
		return NEXT_APRV_ROLE_CODE;
	}

	/**
	 * ���̏��F�҃R�[�h�̐ݒ�
	 * 
	 * @param NEXT_APRV_ROLE_CODE ���̏��F�҃R�[�h
	 */
	public void setNEXT_APRV_ROLE_CODE(String NEXT_APRV_ROLE_CODE) {
		this.NEXT_APRV_ROLE_CODE = NEXT_APRV_ROLE_CODE;
	}

}
