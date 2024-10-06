package jp.co.ais.trans2.model.close;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �����X�V���
 * 
 * @author TRANS(D) YF.CONG
 */
public class MonthData extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode;

	/** ��Џ�� */
	protected Company company = null;

	/** �o�^ */
	protected int entry = 0;

	/** ����۔F */
	protected int fieldDeny = 0;

	/** ���ꏳ�F */
	protected int fieldApprove = 0;

	/** �o���۔F */
	protected int deny = 0;

	/** �o�����F */
	protected int approve = 0;

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
	 * ��Џ��̎擾
	 * 
	 * @return company ��Џ��
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * ��Џ��̐ݒ�
	 * 
	 * @param company ��Џ��
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * �o�^�̎擾
	 * 
	 * @return entry �o�^
	 */
	public int getEntry() {
		return entry;
	}

	/**
	 * �o�^�̐ݒ�
	 * 
	 * @param entry �o�^
	 */
	public void setEntry(int entry) {
		this.entry = entry;
	}

	/**
	 * ����۔F�̎擾
	 * 
	 * @return fieldDeny ����۔F
	 */
	public int getFieldDeny() {
		return fieldDeny;
	}

	/**
	 * ����۔F�̐ݒ�
	 * 
	 * @param fieldDeny ����۔F
	 */
	public void setFieldDeny(int fieldDeny) {
		this.fieldDeny = fieldDeny;
	}

	/**
	 * ���ꏳ�F�̎擾
	 * 
	 * @return fieldApprove ���ꏳ�F
	 */
	public int getFieldApprove() {
		return fieldApprove;
	}

	/**
	 * ���ꏳ�F�̐ݒ�
	 * 
	 * @param fieldApprove ���ꏳ�F
	 */
	public void setFieldApprove(int fieldApprove) {
		this.fieldApprove = fieldApprove;
	}

	/**
	 * �o���۔F�̎擾
	 * 
	 * @return deny �o���۔F
	 */
	public int getDeny() {
		return deny;
	}

	/**
	 * �o���۔F�̐ݒ�
	 * 
	 * @param deny �o���۔F
	 */
	public void setDeny(int deny) {
		this.deny = deny;
	}

	/**
	 * �o�����F�̎擾
	 * 
	 * @return approve �o�����F
	 */
	public int getApprove() {
		return approve;
	}

	/**
	 * �o�����F�̐ݒ�
	 * 
	 * @param approve �o�����F
	 */
	public void setApprove(int approve) {
		this.approve = approve;
	}

}
