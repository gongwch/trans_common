package jp.co.ais.trans2.model.remittance;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �����ړI���
 */
public class Remittance extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �����ړI�R�[�h */
	protected String code = null;

	/** �����ړI���J�i */
	protected String name = null;

	/** ���ێ��x�R�[�h */
	protected String balanceCode = null;

	/** ���ێ��x���� */
	protected String balanceName = null;

	/** �o�^���t */
	protected Date inpDate;

	/** �X�V���t */
	protected Date updDate;

	/**
	 * ��ЃR�[�h��߂�
	 * 
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * ��ЃR�[�h��ݒ肷��
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �����ړI�R�[�h��߂�
	 * 
	 * @return code �����ړI�R�[�h
	 */
	public String getCode() {
		return code;
	}

	/**
	 * �����ړI�R�[�h��ݒ肷��
	 * 
	 * @param code �����ړI�R�[�h
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * �����ړI���J�i��߂�
	 * 
	 * @return kana �����ړI���J�i
	 */
	public String getName() {
		return name;
	}

	/**
	 * �����ړI���J�i��ݒ肷��
	 * 
	 * @param kana �����ړI���J�i
	 */
	public void setName(String kana) {
		this.name = kana;
	}

	/**
	 * ���ێ��x�R�[�h��߂�
	 * 
	 * @return balanceCode
	 */
	public String getBalanceCode() {
		return this.balanceCode;
	}

	/**
	 * ���ێ��x�R�[�h��ݒ肷��
	 * 
	 * @param balanceCode
	 */
	public void setBalanceCode(String balanceCode) {
		this.balanceCode = balanceCode;
	}

	/**
	 * ���ێ��x���̂�߂�
	 * 
	 * @return balanceName
	 */
	public String getBalanceName() {
		return this.balanceName;
	}

	/**
	 * ���ێ��x���̂�ݒ肷��
	 * 
	 * @param balanceName
	 */
	public void setBalanceName(String balanceName) {
		this.balanceName = balanceName;
	}

	/**
	 * �o�^���t�̎擾
	 * 
	 * @return inpDate
	 */
	public Date getInpDate() {
		return inpDate;
	}

	/**
	 * �o�^���t��ݒ肷��
	 * 
	 * @param inpDate
	 */
	public void setInpDate(Date inpDate) {
		this.inpDate = inpDate;
	}

	/**
	 * �X�V���t�̎擾
	 * 
	 * @return updDate
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * �X�V���t��ݒ肷��
	 * 
	 * @param updDate
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

}
