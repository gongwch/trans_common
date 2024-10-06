package jp.co.ais.trans.logic.system.impl;

import java.util.*;

/**
 * ���s���O�����p�����[�^
 */
public class ExecutedLogSearchParam {

	/** �����J�n���t */
	private Date startDate;

	/** �����I�����t */
	private Date endDate;

	/** �����J�n���[�U */
	private String startUser;

	/** �����I�����[�U */
	private String endUser;

	/** �����J�n�v���O���� */
	private String startPrg;

	/** �����I���v���O���� */
	private String endPrg;

	/** ��ЃR�[�h */
	private String companyCode;

	/** ���O�C���A�E�g�敪 */
	private int isIncludeLogin = 0;

	/** �\�[�g�� */
	private String orderBy;

	/**
	 * companyCode�擾
	 * 
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * companyCode�ݒ�
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * endDate�擾
	 * 
	 * @return endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * endDate�ݒ�
	 * 
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * endPrg�擾
	 * 
	 * @return endPrg
	 */
	public String getEndPrg() {
		return endPrg;
	}

	/**
	 * endPrg�ݒ�
	 * 
	 * @param endPrg
	 */
	public void setEndPrg(String endPrg) {
		this.endPrg = endPrg;
	}

	/**
	 * endUser�擾
	 * 
	 * @return endUser
	 */
	public String getEndUser() {
		return endUser;
	}

	/**
	 * endUser�ݒ�
	 * 
	 * @param endUser
	 */
	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}

	/**
	 * orderBy�擾
	 * 
	 * @return orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * orderBy�ݒ�
	 * 
	 * @param orderBy
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * startDate�擾
	 * 
	 * @return startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * startDate�ݒ�
	 * 
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * startPrg�擾
	 * 
	 * @return startPrg
	 */
	public String getStartPrg() {
		return startPrg;
	}

	/**
	 * startPrg�ݒ�
	 * 
	 * @param startPrg
	 */
	public void setStartPrg(String startPrg) {
		this.startPrg = startPrg;
	}

	/**
	 * startUser�擾
	 * 
	 * @return startUser
	 */
	public String getStartUser() {
		return startUser;
	}

	/**
	 * startUser�ݒ�
	 * 
	 * @param startUser
	 */
	public void setStartUser(String startUser) {
		this.startUser = startUser;
	}

	/**
	 * isIncludeLogin�擾
	 * 
	 * @return isIncludeLogin
	 */
	public int getIsIncludeLogin() {
		return isIncludeLogin;
	}

	/**
	 * isIncludeLogin�ݒ�
	 * 
	 * @param isIncludeLogin
	 */
	public void setIsIncludeLogin(int isIncludeLogin) {
		this.isIncludeLogin = isIncludeLogin;
	}

}
