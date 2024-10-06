package jp.co.ais.trans2.model.close;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �����X�V���
 * 
 * @author AIS
 */
public class DailyData extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode;

	/** ��Ж� */
	protected String companyName = null;

	/** �ŏI���s���� */
	protected Date lastUpdateDate = null;

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return companyCode ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * ��Ж��̎擾
	 * 
	 * @return companyName ��Ж�
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * ��Ж��̐ݒ�
	 * 
	 * @param companyName ��Ж�
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * �ŏI���s�����̎擾
	 * 
	 * @return lastUpdateDate �ŏI���s����
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * �ŏI���s�����̐ݒ�
	 * 
	 * @param lastUpdateDate �ŏI���s����
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

}
