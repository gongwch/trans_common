package jp.co.ais.trans2.model.passwordupdate;

import jp.co.ais.trans.common.dt.*;

/**
 * MG0025 �p�X���[�h�ύX
 * 
 * @author AIS
 */
public class PasswordUpdate extends TransferBase {

	/** �V�����p�X���[�h */
	protected String newPassword = null;

	/** �V�����p�X���[�h(�m�F) */
	protected String confNewPassword = null;

	/**
	 * �V�����p�X���[�h�̎擾
	 * 
	 * @return newPasswoer �V�����p�X���[�h
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * �V�����p�X���[�h�̐ݒ�
	 * 
	 * @param newPassword �V�����p�X���[�h
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * �V�����p�X���[�h(�m�F)�̎擾
	 * 
	 * @return ��onfNewPassword �V�����p�X���[�h
	 */
	public String getConfNewPassword() {
		return confNewPassword;
	}

	/**
	 * �V�����p�X���[�h(�m�F)�̐ݒ�
	 * 
	 * @param confNewPassword �V�����p�X���[�h
	 */
	public void setConfNewPassword(String confNewPassword) {
		this.confNewPassword = confNewPassword;
	}

}
