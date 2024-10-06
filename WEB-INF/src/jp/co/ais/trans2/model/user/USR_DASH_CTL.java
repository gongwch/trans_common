package jp.co.ais.trans2.model.user;

import jp.co.ais.trans.common.dt.*;

/**
 * �_�b�V���{�[�h�������� USR_DASH_CTL
 * 
 * @author AIS
 */
public class USR_DASH_CTL extends TransferBase {

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** ���[�U�[�R�[�h */
	protected String USR_CODE = null;

	/** �V�X�e���敪 */
	protected String SYS_KBN_CODE = null;

	/** �V�X�e���敪���� */
	protected String SYS_KBN_NAME = null;

	/** 0:�g�p�s��false�A1:�g�p��true */
	protected boolean USE_KBN = false;

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
	 * /** ���[�U�[�R�[�h�̎擾
	 * 
	 * @return USER_CODE ���[�U�[�R�[�h
	 */
	public String getUSR_CODE() {
		return USR_CODE;
	}

	/**
	 * ���[�U�[�R�[�h�̐ݒ�
	 * 
	 * @param USR_CODE ���[�U�[�R�[�h
	 */
	public void setUSR_CODE(String USR_CODE) {
		this.USR_CODE = USR_CODE;
	}

	/**
	 * �V�X�e���敪�̎擾
	 * 
	 * @return SYS_KBN_CODE �V�X�e���敪
	 */
	public String getSYS_KBN_CODE() {
		return SYS_KBN_CODE;
	}

	/**
	 * �V�X�e���敪�̐ݒ�
	 * 
	 * @param SYS_KBN_CODE �V�X�e���敪
	 */
	public void setSYS_KBN_CODE(String SYS_KBN_CODE) {
		this.SYS_KBN_CODE = SYS_KBN_CODE;
	}

	/**
	 * �V�X�e���敪���̂̎擾
	 * 
	 * @return SYS_KBN_NAME �V�X�e���敪����
	 */
	public String getSYS_KBN_NAME() {
		return SYS_KBN_NAME;
	}

	/**
	 * �V�X�e���敪���̂̐ݒ�
	 * 
	 * @param SYS_KBN_NAME �V�X�e���敪����
	 */
	public void setSYS_KBN_NAME(String SYS_KBN_NAME) {
		this.SYS_KBN_NAME = SYS_KBN_NAME;
	}

	/**
	 * 0:�g�p�s��false�A1:�g�p��true�̎擾
	 * 
	 * @return USE_KBN 0:�g�p�s��false�A1:�g�p��true
	 */
	public boolean isUSE_KBN() {
		return USE_KBN;
	}

	/**
	 * 0:�g�p�s��false�A1:�g�p��true�̐ݒ�
	 * 
	 * @param USE_KBN 0:�g�p�s��false�A1:�g�p��true
	 */
	public void setUSE_KBN(boolean USE_KBN) {
		this.USE_KBN = USE_KBN;
	}

}
