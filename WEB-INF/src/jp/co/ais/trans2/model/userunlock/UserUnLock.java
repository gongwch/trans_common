package jp.co.ais.trans2.model.userunlock;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * ���O�C�������}�X�^��������
 */
public class UserUnLock extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	private String KAI_CODE = null;

	/** ���[�U�[�R�[�h �ǉ� */
	private String USR_ID = null;

	/** �v���O����ID */
	private String PRG_ID = null;

	/** �N���C�A���g�[���� */
	private String PCI_CLIENT_NAME = null;

	/** ���O�C������ */
	private Date PCI_CHECK_IN_TIME = null;

	/** ���[�U���� */
	private String USR_NAME = null;

	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * @param kAI_CODE
	 */
	public void setKAI_CODE(String kAI_CODE) {
		this.KAI_CODE = kAI_CODE;
	}

	/**
	 * ���[�U�[�R�[�h�ݒ�
	 * 
	 * @param usr_name ���[�U�R�[�h
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	public void setUSR_ID(String usr_code) {
		this.USR_ID = usr_code;
	}

	/**
	 * @return String
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * @param pRG_ID
	 */
	public void setPRG_ID(String pRG_ID) {
		this.PRG_ID = pRG_ID;
	}

	/**
	 * @return String
	 */
	public String getPCI_CLIENT_NAME() {
		return PCI_CLIENT_NAME;
	}

	/**
	 * @param pCI_CLIENT_NAME
	 */
	public void setPCI_CLIENT_NAME(String pCI_CLIENT_NAME) {
		this.PCI_CLIENT_NAME = pCI_CLIENT_NAME;
	}

	/**
	 * @return Date
	 */
	public Date getPCI_CHECK_IN_TIME() {
		return PCI_CHECK_IN_TIME;
	}

	/**
	 * @param pCI_CHECK_IN_TIME
	 */
	public void setPCI_CHECK_IN_TIME(Date pCI_CHECK_IN_TIME) {
		this.PCI_CHECK_IN_TIME = pCI_CHECK_IN_TIME;
	}

	/**
	 * ���[�U�[���̐ݒ�
	 * 
	 * @param usr_name ���[�U�[����
	 */
	public String getUSR_NAME() {
		return USR_NAME;
	}

	public void setUSR_NAME(String usr_name) {
		this.USR_NAME = usr_name;
	}
}
