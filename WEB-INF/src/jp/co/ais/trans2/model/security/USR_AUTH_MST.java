package jp.co.ais.trans2.model.security;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.*;

/**
 * ���[�U�F�؊Ǘ��}�X�^
 */
public class USR_AUTH_MST extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** ���b�N�A�E�g���B�� */
	protected int LOCK_OUT_ARR_CNT;

	/** ���b�N�A�E�g�J������ */
	protected String LOCK_OUT_RELEASE_TIME = null;

	/** �Œ�p�X���[�h�� */
	protected int PWD_MIN_LENGTH;

	/** �p�X���[�h�L������ */
	protected String PWD_TERM = null;

	/** ���G���x�� */
	protected int COMPLICATE_LVL;

	/** ����ێ��� */
	protected int HISTORY_MAX_CNT;

	/** �o�^���t */
	protected Date INP_DATE = null;

	/** �X�V���t */
	protected Date UPD_DATE = null;

	/** �v���O����ID */
	protected String PRG_ID = null;

	/** ���[�UID */
	protected String USR_ID = null;

	/** �p�X���[�h�����؂�ʒm���� */
	protected String PWD_EXP_BEFORE_DAYS = null;

	/**
	 * @return ���b�N�A�E�g���B�񐔂�߂��܂��B
	 */
	public int getLOCK_OUT_ARR_CNT() {
		return LOCK_OUT_ARR_CNT;
	}

	/**
	 * @param ���b�N�A�E�g���B�񐔂�ݒ肵�܂��B
	 */
	public void setLOCK_OUT_ARR_CNT(int rockcnt) {
		this.LOCK_OUT_ARR_CNT = rockcnt;
	}

	/**
	 * @return ��ЃR�[�h��߂��܂��B
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * @param ��ЃR�[�h��ݒ肵�܂��B
	 */
	public void setKAI_CODE(String companyCode) {
		this.KAI_CODE = companyCode;
	}

	/**
	 * @return ���b�N�A�E�g�J�����Ԃ�߂��܂��B
	 */
	public String getLOCK_OUT_RELEASE_TIME() {
		return LOCK_OUT_RELEASE_TIME;
	}

	/**
	 * @param ���b�N�A�E�g�J�����Ԃ�ݒ肵�܂��B
	 */
	public void setLOCK_OUT_RELEASE_TIME(String rockcnttime) {
		this.LOCK_OUT_RELEASE_TIME = rockcnttime;
	}

	/**
	 * @return �Œ�p�X���[�h����߂��܂��B
	 */
	public int getPWD_MIN_LENGTH() {
		return PWD_MIN_LENGTH;
	}

	/**
	 * @param �Œ�p�X���[�h����ݒ肵�܂��B
	 */
	public void setPWD_MIN_LENGTH(int minpwd) {
		this.PWD_MIN_LENGTH = minpwd;
	}

	/**
	 * @return �p�X���[�h�L�����Ԃ�߂��܂��B
	 */
	public String getPWD_TERM() {
		return PWD_TERM;
	}

	/**
	 * @param �p�X���[�h�L�����Ԃ�ݒ肵�܂��B
	 */
	public void setPWD_TERM(String pwdterm) {
		this.PWD_TERM = pwdterm;
	}

	/**
	 * @return ���G���x����߂��܂��B
	 */
	public int getCOMPLICATE_LVL() {
		return COMPLICATE_LVL;
	}

	/**
	 * @param ���G���x����ݒ肵�܂��B
	 */
	public void setCOMPLICATE_LVL(int diffilev) {
		this.COMPLICATE_LVL = diffilev;
	}

	/**
	 * @return ����ێ�����߂��܂��B
	 */
	public int getHISTORY_MAX_CNT() {
		return HISTORY_MAX_CNT;
	}

	/**
	 * @param ����ێ�����ݒ肵�܂��B
	 */
	public void setHISTORY_MAX_CNT(int histcnt) {
		this.HISTORY_MAX_CNT = histcnt;
	}

	/**
	 * �o�^���t��߂��܂�
	 * 
	 * @return true:����
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * �o�^���t��ݒ肵�܂�
	 * 
	 * @param hasSubItem true:����
	 */
	public void setINP_DATE(Date insdate) {
		this.INP_DATE = insdate;
	}

	/**
	 * �X�V���t��߂��܂�
	 * 
	 * @return true:����
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * �X�V���t��ݒ肵�܂�
	 * 
	 * @param hasSubItem true:����
	 */
	public void setUPD_DATE(Date upddate) {
		this.UPD_DATE = upddate;
	}

	/**
	 * �v���O����ID��߂��܂�
	 * 
	 * @return true:����
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * �v���O����ID��ݒ肵�܂�
	 * 
	 * @param hasSubItem true:����
	 */
	public void setPRG_ID(String prgId) {
		this.PRG_ID = prgId;
	}

	/**
	 * ���[�UID��߂��܂�
	 * 
	 * @return true:����
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ���[�UID��ݒ肵�܂�
	 * 
	 * @param USR_ID true:����
	 */
	public void setUSR_ID(String userId) {
		this.USR_ID = userId;
	}

	/**
	 * �p�X���[�h�����؂�ʒm������߂��܂�
	 * 
	 * @return true:����
	 */
	public String getPWD_EXP_BEFORE_DAYS() {
		return PWD_EXP_BEFORE_DAYS;
	}

	/**
	 * �p�X���[�h�����؂�ʒm������ݒ肵�܂�
	 * 
	 * @param hasSubItem true:����
	 */
	public void setPWD_EXP_BEFORE_DAYS(String pwdexp) {
		this.PWD_EXP_BEFORE_DAYS = pwdexp;
	}

}
