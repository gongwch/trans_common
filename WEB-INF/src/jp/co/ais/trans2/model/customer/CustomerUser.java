package jp.co.ais.trans2.model.customer;

import jp.co.ais.trans.common.dt.*;

/**
 * ��Џ��
 * 
 * @author AIS
 */
public class CustomerUser extends TransferBase {

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** �����R�[�h */
	protected String TRI_CODE = null;

	/** ����於�� */
	protected String TRI_NAME = null;

	/** �V�X�e���敪 */
	protected String SYS_KBN = null;

	/** �S���Җ��� */
	protected String USR_NAME = null;

	/** �������� */
	protected String DEP_NAME = null;

	/** ��E */
	protected String POSITION = null;

	/**
	 * @return KAI_CODE��߂��܂��B
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * @param KAI_CODE KAI_CODE��ݒ肵�܂��B
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * @return TRI_CODE��߂��܂��B
	 */
	public String getTRI_CODE() {
		return TRI_CODE;
	}

	/**
	 * @param TRI_CODE TRI_CODE��ݒ肵�܂��B
	 */
	public void setTRI_CODE(String TRI_CODE) {
		this.TRI_CODE = TRI_CODE;
	}

	/**
	 * @return TRI_NAME��߂��܂��B
	 */
	public String getTRI_NAME() {
		return TRI_NAME;
	}

	/**
	 * @param TRI_NAME TRI_NAME��ݒ肵�܂��B
	 */
	public void setTRI_NAME(String TRI_NAME) {
		this.TRI_NAME = TRI_NAME;
	}

	/**
	 * @return SYS_KBN��߂��܂��B
	 */
	public String getSYS_KBN() {
		return SYS_KBN;
	}

	/**
	 * @param SYS_KBN SYS_KBN��ݒ肵�܂��B
	 */
	public void setSYS_KBN(String SYS_KBN) {
		this.SYS_KBN = SYS_KBN;
	}

	/**
	 * @return DEP_NAME��߂��܂��B
	 */
	public String getDEP_NAME() {
		return DEP_NAME;
	}

	/**
	 * @param DEP_NAME DEP_NAME��ݒ肵�܂��B
	 */
	public void setDEP_NAME(String DEP_NAME) {
		this.DEP_NAME = DEP_NAME;
	}

	/**
	 * @return USR_NAME��߂��܂��B
	 */
	public String getUSR_NAME() {
		return USR_NAME;
	}

	/**
	 * @param USR_NAME USR_NAME��ݒ肵�܂��B
	 */
	public void setUSR_NAME(String USR_NAME) {
		this.USR_NAME = USR_NAME;
	}

	/**
	 * @return POSITION��߂��܂��B
	 */
	public String getPOSITION() {
		return POSITION;
	}

	/**
	 * @param POSITION POSITION��ݒ肵�܂��B
	 */
	public void setPOSITION(String POSITION) {
		this.POSITION = POSITION;
	}

}
