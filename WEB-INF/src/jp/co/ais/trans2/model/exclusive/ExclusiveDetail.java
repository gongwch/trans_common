package jp.co.ais.trans2.model.exclusive;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �r�����׈ꗗ�}�X�^
 */
public class ExclusiveDetail extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** �����敪 */
	protected String SHORI_KBN = null;

	/** �r���L�[ */
	protected String HAITA_KEY = null;

	/** �s�ԍ� */
	protected String GYO_NO = null;

	/** �������� */
	protected Date INP_DATE = null;

	/** �v���O����ID */
	protected String PRG_ID = null;

	/** �v���O�������� */
	protected String PRG_NAME = null;

	/** ���[�UID */
	protected String USR_ID = null;

	/** ���[�U���� */
	protected String USR_NAME = null;

	/**
	 * @return ��ЃR�[�h��߂��܂��B
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * @param KAI_CODE ��ЃR�[�h��ݒ肵�܂��B
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * @return �����敪��߂��܂��B
	 */
	public String getSHORI_KBN() {
		return SHORI_KBN;
	}

	/**
	 * @param SHORI_KBN �����敪��ݒ肵�܂��B
	 */
	public void setSHORI_KBN(String SHORI_KBN) {
		this.SHORI_KBN = SHORI_KBN;
	}

	/**
	 * @return �r���L�[��߂��܂��B
	 */
	public String getHAITA_KEY() {
		return HAITA_KEY;
	}

	/**
	 * @param HAITA_KEY �r���L�[��ݒ肵�܂��B
	 */
	public void setHAITA_KEY(String HAITA_KEY) {
		this.HAITA_KEY = HAITA_KEY;
	}

	/**
	 * @return �s�ԍ���߂��܂��B
	 */
	public String getGYO_NO() {
		return GYO_NO;
	}

	/**
	 * @param GYO_NO �s�ԍ���ݒ肵�܂��B
	 */
	public void setGYO_NO(String GYO_NO) {
		this.GYO_NO = GYO_NO;
	}

	/**
	 * @return ����������߂��܂��B
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * @param INP_DATE ����������ݒ肵�܂��B
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * @return �v���O����ID��߂��܂��B
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * @param PRG_ID �v���O����ID��ݒ肵�܂��B
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * @return �v���O�������̂�߂��܂��B
	 */
	public String getPRG_NAME() {
		return PRG_NAME;
	}

	/**
	 * @param PRG_NAME �v���O�������̂�ݒ肵�܂��B
	 */
	public void setPRG_NAME(String PRG_NAME) {
		this.PRG_NAME = PRG_NAME;
	}

	/**
	 * @return ���[�UID��߂��܂��B
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * @param USR_ID ���[�UID��ݒ肵�܂��B
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * @return ���[�U���̂�߂��܂��B
	 */
	public String getUSR_NAME() {
		return USR_NAME;
	}

	/**
	 * @param USR_NAME ���[�U���̂�ݒ肵�܂��B
	 */
	public void setUSR_NAME(String USR_NAME) {
		this.USR_NAME = USR_NAME;
	}

}