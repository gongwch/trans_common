package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * ���[�U�F�؊Ǘ��}�X�^
 * 
 * @author roh
 */
public class USR_AUTH_MST extends TransferBase {

	/** ���[�U�F�؊Ǘ��e�[�u�� */
	public static final String TABLE = "USR_AUTH_MST";

	/** ��ЃR�[�h */
	private String kAI_CODE;

	/** ���b�N�A�E�g�� */
	private int lOCK_OUT_ARR_CNT;

	/** ���b�N�A�E�g�������� */
	private int lOCK_OUT_RELEASE_TIME;

	/** �p�X���[�h�Œ�� */
	private int pWD_MIN_LENGTH;

	/** �p�X���[�h�L������ */
	private int pWD_TERM;

	/** ���G��׃� */
	private int cOMPLICATE_LVL;

	/** �����ő�� */
	private int hISTORY_MAX_CNT;

	/** ���͓��t */
	private Date iNP_DATE;

	/** �X�V���t */
	private Date uPD_DATE;

	/** �v���O����ID */
	private String pRG_ID;

	/** ���[�UID */
	private String uSR_ID;

	/** �p�X���[�h�����؂�ʒm���� */
	private int pWD_EXP_BEFORE_DAYS;

	/**
	 * ���G���x���擾
	 * 
	 * @return ���G���x��
	 */
	public int getCOMPLICATE_LVL() {
		return cOMPLICATE_LVL;
	}

	/**
	 * ���G���x���ݒ�
	 * 
	 * @param complicate_lvl ���G���x��
	 */
	public void setCOMPLICATE_LVL(int complicate_lvl) {
		cOMPLICATE_LVL = complicate_lvl;
	}

	/**
	 * �����ő���擾
	 * 
	 * @return �����ő��
	 */
	public int getHISTORY_MAX_CNT() {
		return hISTORY_MAX_CNT;
	}

	/**
	 * �����ő���ݒ�
	 * 
	 * @param history_max_cnt �����ő��
	 */
	public void setHISTORY_MAX_CNT(int history_max_cnt) {
		hISTORY_MAX_CNT = history_max_cnt;
	}

	/**
	 * ���͓��t�擾
	 * 
	 * @return ���͓��t
	 */
	public Date getINP_DATE() {
		return iNP_DATE;
	}

	/**
	 * ���͓��t�ݒ�
	 * 
	 * @param inp_date ���͓��t
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
	 * ���b�N�A�E�g�񐔎擾
	 * 
	 * @return ���b�N�A�E�g��
	 */
	public int getLOCK_OUT_ARR_CNT() {
		return lOCK_OUT_ARR_CNT;
	}

	/**
	 * ���b�N�A�E�g�񐔐ݒ�
	 * 
	 * @param lock_out_arr_cnt ���b�N�A�E�g��
	 */
	public void setLOCK_OUT_ARR_CNT(int lock_out_arr_cnt) {
		lOCK_OUT_ARR_CNT = lock_out_arr_cnt;
	}

	/**
	 * ���b�N�A�E�g�J�����Ԏ擾
	 * 
	 * @return ���b�N�A�E�g�J������
	 */
	public int getLOCK_OUT_RELEASE_TIME() {
		return lOCK_OUT_RELEASE_TIME;
	}

	/**
	 * ���b�N�A�E�g�J�����Ԑݒ�
	 * 
	 * @param lock_out_release_time ���b�N�A�E�g�J������
	 */
	public void setLOCK_OUT_RELEASE_TIME(int lock_out_release_time) {
		lOCK_OUT_RELEASE_TIME = lock_out_release_time;
	}

	/**
	 * �v���O����ID�擾
	 * 
	 * @return �v���O����ID
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * �v���O����ID�ݒ�
	 * 
	 * @param prg_id �v���O����ID
	 */
	public void setPRG_ID(String prg_id) {
		pRG_ID = prg_id;
	}

	/**
	 * �p�X���[�h�Œ���擾
	 * 
	 * @return �p�X���[�h�Œ��
	 */
	public int getPWD_MIN_LENGTH() {
		return pWD_MIN_LENGTH;
	}

	/**
	 * �p�X���[�h�Œ���ݒ�
	 * 
	 * @param pwd_min_length �p�X���[�h�Œ��
	 */
	public void setPWD_MIN_LENGTH(int pwd_min_length) {
		pWD_MIN_LENGTH = pwd_min_length;
	}

	/**
	 * �p�X���[�h�L�����Ԏ擾
	 * 
	 * @return �p�X���[�h�L������
	 */
	public int getPWD_TERM() {
		return pWD_TERM;
	}

	/**
	 * �p�X���[�h�L�����Ԑݒ�
	 * 
	 * @param pwd_term �p�X���[�h�L������
	 */
	public void setPWD_TERM(int pwd_term) {
		pWD_TERM = pwd_term;
	}

	/**
	 * �X�V���t�擾
	 * 
	 * @return �X�V���t
	 */
	public Date getUPD_DATE() {
		return uPD_DATE;
	}

	/**
	 * �X�V���t�ݒ�
	 * 
	 * @param upd_date �X�V���t
	 */
	public void setUPD_DATE(Date upd_date) {
		uPD_DATE = upd_date;
	}

	/**
	 * ���[�UID�擾
	 * 
	 * @return ���[�UID
	 */
	public String getUSR_ID() {
		return uSR_ID;
	}

	/**
	 * ���[�UID�ݒ�
	 * 
	 * @param usr_id ���[�UID
	 */
	public void setUSR_ID(String usr_id) {
		uSR_ID = usr_id;
	}

	/**
	 * �p�X���[�h�����؂�ʒm����
	 * 
	 * @return �p�X���[�h�����؂�ʒm����
	 */
	public int getPWD_EXP_BEFORE_DAYS() {
		return pWD_EXP_BEFORE_DAYS;
	}

	/**
	 * �p�X���[�h�����؂�ʒm����
	 * 
	 * @param pwd_exp_before_days �p�X���[�h�����؂�ʒm����
	 */
	public void setPWD_EXP_BEFORE_DAYS(int pwd_exp_before_days) {
		pWD_EXP_BEFORE_DAYS = pwd_exp_before_days;
	}
}
