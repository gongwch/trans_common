package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.dt.*;

/**
 * �o�b�`�r�������}�X�^
 */
public class BatchUnLock extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** ����ID */
	protected String BAT_ID = null;

	/** �v���O�������� */
	protected String PRG_NAME = null;

	/** �r�����[�U */
	protected String USR_ID = null;

	/** �r�����[�U���� */
	protected String USR_NAME = null;

	/** �r�����s���� */
	protected String BAT_STR_DATE = null;

	/**
	 * @return ��ЃR�[�h��߂��܂��B
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * @param ��ЃR�[�h��ݒ肵�܂��B
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * @return ����ID��߂��܂��B
	 */
	public String getBAT_ID() {
		return BAT_ID;
	}

	/**
	 * @param ����ID��ݒ肵�܂��B
	 */
	public void setBAT_ID(String BAT_ID) {
		this.BAT_ID = BAT_ID;
	}

	/**
	 * @return �v���O�������̂�߂��܂��B
	 */
	public String getPRG_NAME() {
		return PRG_NAME;
	}

	/**
	 * @param �v���O�������̂�ݒ肵�܂��B
	 */
	public void setPRG_NAME(String PRG_NAME) {
		this.PRG_NAME = PRG_NAME;
	}

	/**
	 * @return �r�����[�U��߂��܂��B
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * @param �r�����[�U��ݒ肵�܂��B
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * @return �r�����[�U���̂�߂��܂��B
	 */
	public String getUSR_NAME() {
		return USR_NAME;
	}

	/**
	 * @param �r�����[�U���̂�ݒ肵�܂��B
	 */
	public void setUSR_NAME(String USR_NAME) {
		this.USR_NAME = USR_NAME;
	}

	/**
	 * �r�����s���t
	 * 
	 * @return �r�����s���t
	 */
	public String getBAT_STR_DATE() {
		return BAT_STR_DATE;
	}

	/**
	 * �r�����s���t
	 * 
	 * @param �r�����s���t
	 */
	public void setBAT_STR_DATE(String BAT_STR_DATE) {
		this.BAT_STR_DATE = BAT_STR_DATE;

	}

}
