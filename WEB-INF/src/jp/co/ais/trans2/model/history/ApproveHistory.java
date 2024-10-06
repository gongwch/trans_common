package jp.co.ais.trans2.model.history;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.define.*;

/**
 * ���F����
 */
public class ApproveHistory extends TransferBase {

	/**
	 * ���F�t���O
	 */
	public static class SYO_FLG {

		/** 0:���F */
		public static final int APPROVE = 0;

		/** 1:��� */
		public static final int CANCEL = 1;

		/** 2:�۔F */
		public static final int DENY = 2;

		/** 3:�o�^ */
		public static final int ENTRY = 3;

		/** 4:�C�� */
		public static final int MODIFY = 4;

		/** 5:�X�V */
		public static final int UPDATE = 5;
	}

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** �`�[���t */
	protected Date SWK_DEN_DATE = null;

	/** �`�[�ԍ� */
	protected String SWK_DEN_NO = null;

	/** �X�V�敪 */
	protected int SWK_UPD_KBN = SlipState.ENTRY.value;

	/** ���F�� */
	protected String EMP_CODE = null;

	/** ���F�Җ��� */
	protected String EMP_NAME = null;

	/** ���F�җ��� */
	protected String EMP_NAME_S = null;

	/** ���F�t���O(0:���F�A1:�L�����Z���A2:�۔F�A3:�o�^�A4:�C���A5:�X�V) */
	protected int sYO_FLG = 0;

	/** �o�^����(���F����) */
	protected Date INP_DATE = null;

	/** �O�̍X�V�敪 */
	protected int PREV_SWK_UPD_KBN = SlipState.ENTRY.value;

	/** ���̏��F�Ҍ������[���R�[�h */
	protected String APRV_ROLE_CODE = null;

	/** ���̏��F�Ҍ������[���R�[�h */
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
	 * ���F�҂̎擾
	 * 
	 * @return EMP_CODE ���F��
	 */
	public String getEMP_CODE() {
		return EMP_CODE;
	}

	/**
	 * ���F�҂̐ݒ�
	 * 
	 * @param EMP_CODE ���F��
	 */
	public void setEMP_CODE(String EMP_CODE) {
		this.EMP_CODE = EMP_CODE;
	}

	/**
	 * ���F�Җ��̂̎擾
	 * 
	 * @return EMP_NAME ���F�Җ���
	 */
	public String getEMP_NAME() {
		return EMP_NAME;
	}

	/**
	 * ���F�Җ��̂̐ݒ�
	 * 
	 * @param EMP_NAME ���F�Җ���
	 */
	public void setEMP_NAME(String EMP_NAME) {
		this.EMP_NAME = EMP_NAME;
	}

	/**
	 * ���F�җ��̂̎擾
	 * 
	 * @return EMP_NAME_S ���F�җ���
	 */
	public String getEMP_NAME_S() {
		return EMP_NAME_S;
	}

	/**
	 * ���F�җ��̂̐ݒ�
	 * 
	 * @param EMP_NAME_S ���F�җ���
	 */
	public void setEMP_NAME_S(String EMP_NAME_S) {
		this.EMP_NAME_S = EMP_NAME_S;
	}

	/**
	 * �o�^����(���F����)�̎擾
	 * 
	 * @return INP_DATE �o�^����(���F����)
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * �o�^����(���F����)�̐ݒ�
	 * 
	 * @param INP_DATE �o�^����(���F����)
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * ���F�t���O(0:���F�A1:�L�����Z���A2:�۔F�A3:�o�^�A4:�C���A5:�X�V)�̎擾
	 * 
	 * @return SYO_FLG ���F�t���O(0:���F�A1:�L�����Z���A2:�۔F�A3:�o�^�A4:�C���A5:�X�V)
	 */
	public int getSYO_FLG() {
		return sYO_FLG;
	}

	/**
	 * ���F�t���O(0:���F�A1:�L�����Z���A2:�۔F�A3:�o�^�A4:�C���A5:�X�V)�̐ݒ�
	 * 
	 * @param SYO_FLG ���F�t���O(0:���F�A1:�L�����Z���A2:�۔F�A3:�o�^�A4:�C���A5:�X�V)
	 */
	public void setSYO_FLG(int SYO_FLG) {
		this.sYO_FLG = SYO_FLG;
	}

	/**
	 * �O�̍X�V�敪�̎擾
	 * 
	 * @return PREV_SWK_UPD_KBN �O�̍X�V�敪
	 */
	public int getPREV_SWK_UPD_KBN() {
		return PREV_SWK_UPD_KBN;
	}

	/**
	 * �O�̍X�V�敪�̐ݒ�
	 * 
	 * @param PREV_SWK_UPD_KBN �O�̍X�V�敪
	 */
	public void setPREV_SWK_UPD_KBN(int PREV_SWK_UPD_KBN) {
		this.PREV_SWK_UPD_KBN = PREV_SWK_UPD_KBN;
	}

	/**
	 * ���̏��F�Ҍ������[���R�[�h�̎擾
	 * 
	 * @return ���̏��F�Ҍ������[��
	 */
	public String getAPRV_ROLE_CODE() {
		return APRV_ROLE_CODE;
	}

	/**
	 * ���̏��F�Ҍ������[���R�[�h�̐ݒ�
	 * 
	 * @param APRV_ROLE_CODE
	 */
	public void setAPRV_ROLE_CODE(String APRV_ROLE_CODE) {
		this.APRV_ROLE_CODE = APRV_ROLE_CODE;
	}

	/**
	 * ���̏��F�Ҍ������[���R�[�h�̎擾
	 * 
	 * @return ���̏��F�Ҍ������[��
	 */
	public String getNEXT_APRV_ROLE_CODE() {
		return NEXT_APRV_ROLE_CODE;
	}

	/**
	 * ���̏��F�Ҍ������[���R�[�h�̐ݒ�
	 * 
	 * @param NEXT_APRV_ROLE_CODE
	 */
	public void setNEXT_APRV_ROLE_CODE(String NEXT_APRV_ROLE_CODE) {
		this.NEXT_APRV_ROLE_CODE = NEXT_APRV_ROLE_CODE;
	}
}
