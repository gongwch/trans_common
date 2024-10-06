package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �`�[�r�����������pValueObject
 */
public class SlipUnlockObject extends TransferBase {

	/** ��ЃR�[�h */
	private String companyCode;

	/** �T�u�V�X�e���R�[�h */
	private int subSystemCode;

	/** ���[�U�R�[�h */
	private String userCode;

	/** ���[�U���� */
	private String userName;

	/** �`�[�ԍ� */
	private String silpNo;

	/** �`�[���� */
	private String silpName;

	/** �`�[�E�v */
	private String silpTek;

	/** �`�[���t */
	private Date silpDate;

	/** �r�����t */
	private Date exclusiveDate;

	/**
	 * ��ЃR�[�h�擾
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �r�����t�擾
	 * 
	 * @return �r�����t
	 */
	public Date getExclusiveDate() {
		return exclusiveDate;
	}

	/**
	 * �r�����t�ݒ�
	 * 
	 * @param exclusiveDate �r�����t
	 */
	public void setExclusiveDate(Date exclusiveDate) {
		this.exclusiveDate = exclusiveDate;
	}

	/**
	 * �`�[���t�擾
	 * 
	 * @return �`�[���t
	 */
	public Date getSilpDate() {
		return silpDate;
	}

	/**
	 * �`�[���t�ݒ�
	 * 
	 * @param silpDate �`�[���t
	 */
	public void setSilpDate(Date silpDate) {
		this.silpDate = silpDate;
	}

	/**
	 * �`�[���̎擾
	 * 
	 * @return �`�[����
	 */
	public String getSilpName() {
		return silpName;
	}

	/**
	 * �`�[���̐ݒ�
	 * 
	 * @param silpName �`�[����
	 */
	public void setSilpName(String silpName) {
		this.silpName = silpName;
	}

	/**
	 * �`�[�R�[�h�擾
	 * 
	 * @return �`�[�R�[�h
	 */
	public String getSilpNo() {
		return silpNo;
	}

	/**
	 * �`�[�R�[�h�ݒ�
	 * 
	 * @param silpNo �`�[�R�[�h
	 */
	public void setSilpNo(String silpNo) {
		this.silpNo = silpNo;
	}

	/**
	 * �`�[�E�v�擾
	 * 
	 * @return �`�[�E�v
	 */
	public String getSilpTek() {
		return silpTek;
	}

	/**
	 * �`�[�E�v�ݒ�
	 * 
	 * @param silpTek �`�[�E�v
	 */
	public void setSilpTek(String silpTek) {
		this.silpTek = silpTek;
	}

	/**
	 * �T�u�V�X�e���R�[�h�擾
	 * 
	 * @return �T�u�V�X�e���R�[�h
	 */
	public int getSubSystemCode() {
		return subSystemCode;
	}

	/**
	 * �T�u�V�X�e���R�[�h�ݒ�
	 * 
	 * @param subSystemCode �T�u�V�X�e���R�[�h
	 */
	public void setSubSystemCode(int subSystemCode) {
		this.subSystemCode = subSystemCode;
	}

	/**
	 * ���[�U�R�[�h�擾
	 * 
	 * @return ���[�U�R�[�h
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * ���[�U�R�[�h�ݒ�
	 * 
	 * @param userCode ���[�U�R�[�h
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * ���[�U���̎擾
	 * 
	 * @return ���[�U����
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * ���[�U���̐ݒ�
	 * 
	 * @param userName ���[�U����
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
