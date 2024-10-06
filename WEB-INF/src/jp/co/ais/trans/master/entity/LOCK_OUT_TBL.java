package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * ���b�N�A�E�g�}�X�^�G���e�B�e�B
 * 
 * @author roh
 */
public class LOCK_OUT_TBL extends TransferBase {

	/** ���b�N�A�E�g�e�[�u�� */
	public static final String TABLE = "LOCK_OUT_TBL";

	/** ���[�U���̐ݒ�i�Q�ƌn�N�G���p�j */
	public static final String insert_NO_PERSISTENT_PROPS = "uSR_NAME";

	/** ��ЃR�[�h */
	private String kAI_CODE;

	/** ���[�U�R�[�h */
	private String uSR_CODE;

	/** ���s�� */
	private int fAIL_COUNT;

	/** ���s���� */
	private Date fAIL_DATE;

	/** ���͓��� */
	private Date iNP_DATE;

	/** ���[�U�� */
	private String uSR_NAME;

	/**
	 * ���s�񐔎擾
	 * 
	 * @return ���s��
	 */
	public int getFAIL_COUNT() {
		return fAIL_COUNT;
	}

	/**
	 * ���s�񐔐ݒ�
	 * 
	 * @param fail_count ���s��
	 */
	public void setFAIL_COUNT(int fail_count) {
		fAIL_COUNT = fail_count;
	}

	/**
	 * ���s�����擾
	 * 
	 * @return ���s����
	 */
	public Date getFAIL_DATE() {
		return fAIL_DATE;
	}

	/**
	 * ���s�����ݒ�
	 * 
	 * @param fail_date ���s����
	 */
	public void setFAIL_DATE(Date fail_date) {
		fAIL_DATE = fail_date;
	}

	/**
	 * ���͓����擾
	 * 
	 * @return ���͓���
	 */
	public Date getINP_DATE() {
		return iNP_DATE;
	}

	/**
	 * ���͓����ݒ�
	 * 
	 * @param inp_date ���͓���
	 */
	public void setINP_DATE(Date inp_date) {
		iNP_DATE = inp_date;
	}

	/**
	 * ��ЃR�[�h�擾
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * ��ЃR�[�h�ݒ�
	 * 
	 * @param kai_code ��ЃR�[�h
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * ���[�U�R�[�h�擾
	 * 
	 * @return ���[�U�R�[�h
	 */
	public String getUSR_CODE() {
		return uSR_CODE;
	}

	/**
	 * ���[�U�R�[�h�ݒ�
	 * 
	 * @param code ���[�U�R�[�h
	 */
	public void setUSR_CODE(String code) {
		uSR_CODE = code;
	}

	/**
	 * ���[�U���̎擾
	 * 
	 * @return ���[�U����
	 */
	public String getUSR_NAME() {
		return uSR_NAME;
	}

	/**
	 * ���[�U���̐ݒ�
	 * 
	 * @param usr_name ���[�U����
	 */
	public void setUSR_NAME(String usr_name) {
		uSR_NAME = usr_name;
	}
}
