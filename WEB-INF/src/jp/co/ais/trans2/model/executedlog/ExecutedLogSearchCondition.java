package jp.co.ais.trans2.model.executedlog;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * MG0028ExecutedLog - ���s���O�Q�� - SearchCondition Class
 * 
 * @author AIS
 */
public class ExecutedLogSearchCondition extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �Ώ۔N�����i�J�n�j */
	protected Date dateFrom = null;

	/** �Ώ۔N�����i�I���j */
	protected Date dateTo = null;

	/** ���[�U�[�͈͌����i�J�n�j */
	protected String userFrom = null;

	/** ���[�U�[�͈͌����i�I���j */
	protected String userTo = null;

	/** �v���O�����͈͌����i�J�n�j */
	protected String programFrom = null;

	/** �v���O�����͈͌����i�I���j */
	protected String programTo = null;

	/** ���O�C���E���O�A�E�g��Ώ� */
	protected boolean isLogin = false;

	/** ���я� */
	protected int sort;

	/**
	 * ��ЃR�[�h�擾
	 * 
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�ݒ�
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �Ώ۔N�����i�J�n�j�̎擾
	 * 
	 * @return dateFrom �Ώ۔N�����i�J�n�j
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * �Ώ۔N�����i�J�n�j�̐ݒ�
	 * 
	 * @param dateFrom �Ώ۔N�����i�J�n�j
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * �Ώ۔N�����i�I���j�̎擾
	 * 
	 * @return dateTo �Ώ۔N�����i�I���j
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * �Ώ۔N�����i�I���j�̐ݒ�
	 * 
	 * @param dateTo �Ώ۔N�����i�I���j
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * ���[�U�[�͈͌����i�J�n�j�̎擾
	 * 
	 * @return userFrom ���[�U�[�͈͌����i�J�n�j
	 */
	public String getUserFrom() {
		return userFrom;
	}

	/**
	 * ���[�U�[�͈͌����i�J�n�j�̐ݒ�
	 * 
	 * @param userFrom ���[�U�[�͈͌����i�J�n�j
	 */
	public void setUserFrom(String userFrom) {
		this.userFrom = userFrom;
	}

	/**
	 * ���[�U�[�͈͌����i�I���j�̎擾
	 * 
	 * @return userTo ���[�U�[�͈͌����i�I���j
	 */
	public String getUserTo() {
		return userTo;
	}

	/**
	 * ���[�U�[�͈͌����i�I���j�̐ݒ�
	 * 
	 * @param userTo ���[�U�[�͈͌����i�I���j
	 */
	public void setUserTo(String userTo) {
		this.userTo = userTo;
	}

	/**
	 * �v���O�����͈͌����i�J�n�j�̎擾
	 * 
	 * @return programFrom �v���O�����͈͌����i�J�n�j
	 */
	public String getProgramFrom() {
		return programFrom;
	}

	/**
	 * �v���O�����͈͌����i�J�n�j�̐ݒ�
	 * 
	 * @param programFrom �v���O�����͈͌����i�J�n�j
	 */
	public void setProgramFrom(String programFrom) {
		this.programFrom = programFrom;
	}

	/**
	 * �v���O�����͈͌����i�I���j�̎擾
	 * 
	 * @return programTo �v���O�����͈͌����i�I���j
	 */
	public String getProgramTo() {
		return programTo;
	}

	/**
	 * �v���O�����͈͌����i�I���j�̐ݒ�
	 * 
	 * @param programTo �v���O�����͈͌����i�I���j
	 */
	public void setProgramTo(String programTo) {
		this.programTo = programTo;
	}

	/**
	 * ���O�C���E���O�A�E�g��Ώۂ̎擾
	 * 
	 * @return isLogin ���O�C���E���O�A�E�g��Ώ�
	 */
	public boolean isIsLogin() {
		return isLogin;
	}

	/**
	 * ���O�C���E���O�A�E�g��Ώۂ̐ݒ�
	 * 
	 * @param isLogin ���O�C���E���O�A�E�g��Ώ�
	 */
	public void setIsLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	/**
	 * ���я��̎擾
	 * 
	 * @return sort ���я�
	 */
	public int getSort() {
		return sort;
	}

	/**
	 * ���я��̐ݒ�
	 * 
	 * @param sort ���я�
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

}