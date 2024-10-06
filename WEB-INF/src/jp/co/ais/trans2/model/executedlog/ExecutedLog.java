package jp.co.ais.trans2.model.executedlog;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * ���s���O�Q��
 */
public class ExecutedLog extends TransferBase {

	/** ��ЃR�[�h */
	protected String kaiCode = null;

	/** ���s���� */
	protected Date excDate = null;

	/** ���s���[�U�[�R�[�h */
	protected String excCode = null;

	/** ���s���[�U�[���� */
	protected String excName = null;

	/** ���s���[�U�[���� */
	protected String excNames = null;

	/** IP�A�h���X */
	protected String ipAddress = null;

	/** �v���O�����R�[�h */
	protected String proCode = null;

	/** �v���O�������� */
	protected String proName = null;

	/** ���s��� */
	protected String stste = null;

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return kaiCode ��ЃR�[�h
	 */
	public String getKaiCode() {
		return kaiCode;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param kaiCode ��ЃR�[�h
	 */
	public void setKaiCode(String kaiCode) {
		this.kaiCode = kaiCode;
	}

	/**
	 * ���s�����̎擾
	 * 
	 * @return excDate ���s����
	 */
	public Date getExcDate() {
		return excDate;
	}

	/**
	 * ���s�����̐ݒ�
	 * 
	 * @param excDate ���s����
	 */
	public void setExcDate(Date excDate) {
		this.excDate = excDate;
	}

	/**
	 * ���s���[�U�[�R�[�h�̎擾
	 * 
	 * @return excCode ���s���[�U�[�R�[�h
	 */
	public String getExcCode() {
		return excCode;
	}

	/**
	 * ���s���[�U�[�R�[�h�̐ݒ�
	 * 
	 * @param excCode ���s���[�U�[�R�[�h
	 */
	public void setExcCode(String excCode) {
		this.excCode = excCode;
	}

	/**
	 * ���s���[�U�[���̂̎擾
	 * 
	 * @return excName ���s���[�U�[����
	 */
	public String getExcName() {
		return excName;
	}

	/**
	 * ���s���[�U�[���̂̐ݒ�
	 * 
	 * @param excName ���s���[�U�[����
	 */
	public void setExcName(String excName) {
		this.excName = excName;
	}

	/**
	 * ���s���[�U�[���̂̎擾
	 * 
	 * @return excNames ���s���[�U�[����
	 */
	public String getExcNames() {
		return excNames;
	}

	/**
	 * ���s���[�U�[���̂̐ݒ�
	 * 
	 * @param excNames ���s���[�U�[����
	 */
	public void setExcNames(String excNames) {
		this.excNames = excNames;
	}

	/**
	 * IP�A�h���X�̎擾
	 * 
	 * @return ipAddress IP�A�h���X
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * IP�A�h���X�̐ݒ�
	 * 
	 * @param ipAddress IP�A�h���X
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * �v���O�����R�[�h�̎擾
	 * 
	 * @return proCode �v���O�����R�[�h
	 */
	public String getProCode() {
		return proCode;
	}

	/**
	 * �v���O�����R�[�h�̐ݒ�
	 * 
	 * @param proCode �v���O�����R�[�h
	 */
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	/**
	 * �v���O�������̂̎擾
	 * 
	 * @return proName �v���O��������
	 */
	public String getProName() {
		return proName;
	}

	/**
	 * �v���O�������̂̐ݒ�
	 * 
	 * @param proName �v���O��������
	 */
	public void setProName(String proName) {
		this.proName = proName;
	}

	/**
	 * ���s��Ԃ̎擾
	 * 
	 * @return stste ���s���
	 */
	public String getStste() {
		return stste;
	}

	/**
	 * ���s��Ԃ̐ݒ�
	 * 
	 * @param stste ���s���
	 */
	public void setStste(String stste) {
		this.stste = stste;
	}

}
