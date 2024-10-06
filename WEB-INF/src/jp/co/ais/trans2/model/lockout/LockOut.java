package jp.co.ais.trans2.model.lockout;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * MG0027 ���b�N�A�E�g�Ǘ�
 * 
 * @author AIS
 */
public class LockOut extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode;

	/** ���[�U�[�R�[�h */
	protected String userCode = null;

	/** ���[�U�[���� */
	protected String userNames = null;

	/** ���O�C�����s���� */
	protected Date logFailure = null;

	/**
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * ���[�U�[�R�[�h�̎擾
	 * 
	 * @return userCode ���[�U�[�R�[�h
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * ���[�U�[�R�[�h�̐ݒ�
	 * 
	 * @param userCode ���[�U�[�R�[�h
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * ���[�U�[���̂̎擾
	 * 
	 * @return userNames ���[�U�[����
	 */
	public String getUserNames() {
		return userNames;
	}

	/**
	 * ���[�U�[���̂̐ݒ�
	 * 
	 * @param userNames ���[�U�[����
	 */
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	/**
	 * ���O�C�����s�����̎擾
	 * 
	 * @return logFailure ���O�C�����s����
	 */
	public Date getLogFailure() {
		return logFailure;
	}

	/**
	 * ���O�C�����s�����̐ݒ�
	 * 
	 * @param logFailure ���O�C�����s����
	 */
	public void setLogFailure(Date logFailure) {
		this.logFailure = logFailure;
	}

}
