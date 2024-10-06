package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans2.common.ledger.*;
import jp.co.ais.trans2.define.*;

/**
 * �`�[���[�w�b�_�[
 * 
 * @author AIS
 */
public class SlipBookHeader extends LedgerSheetHeader {

	/** �^�C�g�� */
	protected String title = null;

	/** �`�[�ԍ� */
	protected String SlipNo = null;

	/** �`�[���t */
	protected Date slipDate = null;

	/** ���͕���R�[�h */
	protected String departmentCode = null;

	/** ���͕��喼�� */
	protected String departmentName = null;

	/** ���͎҃R�[�h */
	protected String employeeCode = null;

	/** ���͎Җ��� */
	protected String employeeName = null;

	/** �؜ߔԍ� */
	protected String voucherNo = null;

	/** �E�v�R�[�h */
	protected String remarkCode = null;

	/** �E�v */
	protected String remark = null;

	/** �C���� */
	protected int updateCount = 0;

	/** ��v���� */
	protected AccountBook accountBook = null;

	/** ���Z�i�K */
	protected int settlementStage = 0;

	/**
	 * �^�C�g���̎擾
	 * 
	 * @return title �^�C�g��
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * �^�C�g���̐ݒ�
	 * 
	 * @param title �^�C�g��
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * �`�[�ԍ��̎擾
	 * 
	 * @return SlipNo �`�[�ԍ�
	 */
	public String getSlipNo() {
		return SlipNo;
	}

	/**
	 * �`�[�ԍ��̐ݒ�
	 * 
	 * @param SlipNo �`�[�ԍ�
	 */
	public void setSlipNo(String SlipNo) {
		this.SlipNo = SlipNo;
	}

	/**
	 * �`�[���t�̎擾
	 * 
	 * @return slipDate �`�[���t
	 */
	public Date getSlipDate() {
		return slipDate;
	}

	/**
	 * �`�[���t�̐ݒ�
	 * 
	 * @param slipDate �`�[���t
	 */
	public void setSlipDate(Date slipDate) {
		this.slipDate = slipDate;
	}

	/**
	 * ���͕���R�[�h�̎擾
	 * 
	 * @return departmentCode ���͕���R�[�h
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * ���͕���R�[�h�̐ݒ�
	 * 
	 * @param departmentCode ���͕���R�[�h
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * ���͕��喼�̂̎擾
	 * 
	 * @return departmentName ���͕��喼��
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * ���͕��喼�̂̐ݒ�
	 * 
	 * @param departmentName ���͕��喼��
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * ���͎҃R�[�h�̎擾
	 * 
	 * @return employeeCode ���͎҃R�[�h
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}

	/**
	 * ���͎҃R�[�h�̐ݒ�
	 * 
	 * @param employeeCode ���͎҃R�[�h
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	/**
	 * ���͎Җ��̂̎擾
	 * 
	 * @return employeeName ���͎Җ���
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * ���͎Җ��̂̐ݒ�
	 * 
	 * @param employeeName ���͎Җ���
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * �؜ߔԍ��̎擾
	 * 
	 * @return voucherNo �؜ߔԍ�
	 */
	public String getVoucherNo() {
		return voucherNo;
	}

	/**
	 * �؜ߔԍ��̐ݒ�
	 * 
	 * @param voucherNo �؜ߔԍ�
	 */
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	/**
	 * �E�v�R�[�h�̎擾
	 * 
	 * @return remarkCode �E�v�R�[�h
	 */
	public String getRemarkCode() {
		return remarkCode;
	}

	/**
	 * �E�v�R�[�h�̐ݒ�
	 * 
	 * @param remarkCode �E�v�R�[�h
	 */
	public void setRemarkCode(String remarkCode) {
		this.remarkCode = remarkCode;
	}

	/**
	 * �E�v�̎擾
	 * 
	 * @return remark �E�v
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * �E�v�̐ݒ�
	 * 
	 * @param remark �E�v
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * �C���񐔂̎擾
	 * 
	 * @return updateCount �C����
	 */
	public int getUpdateCount() {
		return updateCount;
	}

	/**
	 * �C���񐔂̐ݒ�
	 * 
	 * @param updateCount �C����
	 */
	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}

	/**
	 * ��v����̎擾
	 * 
	 * @return accountBook ��v����
	 */
	public AccountBook getAccountBook() {
		return accountBook;
	}

	/**
	 * ��v����̐ݒ�
	 * 
	 * @param accountBook ��v����
	 */
	public void setAccountBook(AccountBook accountBook) {
		this.accountBook = accountBook;
	}

	/**
	 * @return ���Z�i�K��߂��܂��B
	 */
	public int getSettlementStage() {
		return settlementStage;
	}

	/**
	 * @param settlementStage ���Z�i�K��ݒ肵�܂��B
	 */
	public void setSettlementStage(int settlementStage) {
		this.settlementStage = settlementStage;
	}

}