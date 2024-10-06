package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * ���s���O�o�͗p�I�u�W�F�N�g
 * 
 * @author roh
 */
public class ExecutedLog extends TransferBase {

	/** �e�[�u�� */
	public static final String TABLE = "EXE_LOG_TBL";

	/** NoInsert���� */
	public static final String insert_NO_PERSISTENT_PROPS = "pROGRAM_NAME";

	/** ���s���t */
	private Date eXCUTED_DATE;

	/** ���[�U�R�[�h */
	private String uSR_CODE;

	/** ���[�U���� */
	private String uSR_NAME;

	/** IP�A�h���X */
	private String iP_ADDRESS;

	/** �v���O�����R�[�h */
	private String pROGRAM_CODE;

	/** �v���O�������� */
	private String pROGRAM_NAME;

	/** ���s��� */
	private String sTATE;

	/** ��ЃR�[�h */
	private String kAI_CODE;

	/**
	 * ���s���t�擾
	 * 
	 * @return eXCUTED_DATE
	 */
	public Date getEXCUTED_DATE() {
		return eXCUTED_DATE;
	}

	/**
	 * ���s���t�ݒ�
	 * 
	 * @param excuted_date
	 */
	public void setEXCUTED_DATE(Date excuted_date) {
		this.eXCUTED_DATE = excuted_date;
	}

	/**
	 * IP�A�h���X�擾
	 * 
	 * @return iP_ADDRESS
	 */
	public String getIP_ADDRESS() {
		return iP_ADDRESS;
	}

	/**
	 * IP�A�h���X�ݒ�
	 * 
	 * @param ip_address
	 */
	public void setIP_ADDRESS(String ip_address) {
		this.iP_ADDRESS = ip_address;
	}

	/**
	 * �v���O�����R�[�h�擾
	 * 
	 * @return pROGRAM_CODE
	 */
	public String getPROGRAM_CODE() {
		return pROGRAM_CODE;
	}

	/**
	 * �v���O�����R�[�h�ݒ�
	 * 
	 * @param program_code
	 */
	public void setPROGRAM_CODE(String program_code) {
		this.pROGRAM_CODE = program_code;
	}

	/**
	 * ���s��Ԏ擾
	 * 
	 * @return sTATE
	 */
	public String getSTATE() {
		return sTATE;
	}

	/**
	 * ���s��Ԑݒ�
	 * 
	 * @param state
	 */
	public void setSTATE(String state) {
		this.sTATE = state;
	}

	/**
	 * ���[�U�R�[�h�擾
	 * 
	 * @return uSR_CODE
	 */
	public String getUSR_CODE() {
		return uSR_CODE;
	}

	/**
	 * ���[�U�R�[�h�ݒ�
	 * 
	 * @param usr_code
	 */
	public void setUSR_CODE(String usr_code) {
		this.uSR_CODE = usr_code;
	}

	/**
	 * ���[�U���̎擾
	 * 
	 * @return uSR_NAME
	 */
	public String getUSR_NAME() {
		return uSR_NAME;
	}

	/**
	 * ���[�U���̐ݒ�
	 * 
	 * @param usr_name
	 */
	public void setUSR_NAME(String usr_name) {
		this.uSR_NAME = usr_name;
	}

	/**
	 * �v���O�������̎擾
	 * 
	 * @return pROGRAM_NAME
	 */
	public String getPROGRAM_NAME() {
		return pROGRAM_NAME;
	}

	/**
	 * �v���O�������̐ݒ�
	 * 
	 * @param program_name
	 */
	public void setPROGRAM_NAME(String program_name) {
		this.pROGRAM_NAME = program_name;
	}

	/**
	 * kAI_CODE�擾
	 * 
	 * @return kAI_CODE
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * kAI_CODE�ݒ�
	 * 
	 * @param kai_code
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}
}
