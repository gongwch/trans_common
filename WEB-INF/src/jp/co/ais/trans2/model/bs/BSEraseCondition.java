package jp.co.ais.trans2.model.bs;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.item.*;

/**
 * BS�����������
 */
public class BSEraseCondition extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �`�[���t */
	protected Date slipDate = null;

	/** �ȖڃR�[�h */
	protected String itemCode = null;

	/** �⏕�ȖڃR�[�h */
	protected String subItemCode = null;

	/** ����ȖڃR�[�h */
	protected String detailItemCode = null;

	/** �����R�[�h */
	protected String customerCode = null;

	/** ����R�[�h */
	protected String departmentCode = null;

	/** �Ј��R�[�h */
	protected String employeeCode = null;

	/** �Ǘ�1�R�[�h */
	protected String manegement1Code = null;

	/** �Ǘ�2�R�[�h */
	protected String manegement2Code = null;

	/** �Ǘ�3�R�[�h */
	protected String manegement3Code = null;

	/** �Ǘ�4�R�[�h */
	protected String manegement4Code = null;

	/** �Ǘ�5�R�[�h */
	protected String manegement5Code = null;

	/** �Ǘ�6�R�[�h */
	protected String manegement6Code = null;

	/** ���v����1 */
	protected String hm1 = null;

	/** ���v����2 */
	protected String hm2 = null;

	/** ���v����3 */
	protected String hm3 = null;

	/** �`�[�ԍ����X�g */
	protected List<String> slipNoList = new ArrayList<String>();

	/** �s�ԍ����X�g(�`�[�ԍ����X�g�ƃy�A) */
	protected List<Integer> rowNoList = new ArrayList<Integer>();

	/** ������̓`�[�ԍ� */
	protected String slipNo = null;

	/** ������̍s�ԍ� */
	protected Integer rowNo = null;

	/** �`�[�C���ŏ������̒��o���ǂ��� */
	protected boolean forSlipModify = false;

	/** ���o�ΏۊO������̓`�[�ԍ� */
	protected String excludeSlipNo = null;

	/** �ʉ݃R�[�h */
	protected String currencyCode = null;

	/** �v����(�r���p) */
	protected Company kCompany = null;

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
	 * �Ȗڏ��̐ݒ�
	 * 
	 * @param item �Ȗڏ��
	 */
	public void setItem(Item item) {

		if (item == null) {
			return;
		}

		itemCode = item.getCode();
		subItemCode = item.getSubItemCode();
		detailItemCode = item.getDetailItemCode();

	}

	/**
	 * �ȖڃR�[�h�̎擾
	 * 
	 * @return itemCode �ȖڃR�[�h
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * �ȖڃR�[�h�̐ݒ�
	 * 
	 * @param itemCode �ȖڃR�[�h
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * �⏕�ȖڃR�[�h�̎擾
	 * 
	 * @return subItemCode �⏕�ȖڃR�[�h
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * �⏕�ȖڃR�[�h�̐ݒ�
	 * 
	 * @param subItemCode �⏕�ȖڃR�[�h
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * ����ȖڃR�[�h�̎擾
	 * 
	 * @return detailItemCode ����ȖڃR�[�h
	 */
	public String getDetailItemCode() {
		return detailItemCode;
	}

	/**
	 * ����ȖڃR�[�h�̐ݒ�
	 * 
	 * @param detailItemCode ����ȖڃR�[�h
	 */
	public void setDetailItemCode(String detailItemCode) {
		this.detailItemCode = detailItemCode;
	}

	/**
	 * �����R�[�h�̎擾
	 * 
	 * @return customerCode �����R�[�h
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * �����R�[�h�̐ݒ�
	 * 
	 * @param customerCode �����R�[�h
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * ����R�[�h�̎擾
	 * 
	 * @return departmentCode ����R�[�h
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * ����R�[�h�̐ݒ�
	 * 
	 * @param departmentCode ����R�[�h
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * �Ј��R�[�h�̎擾
	 * 
	 * @return employeeCode �Ј��R�[�h
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}

	/**
	 * �Ј��R�[�h�̐ݒ�
	 * 
	 * @param employeeCode �Ј��R�[�h
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	/**
	 * �Ǘ�1�R�[�h�̎擾
	 * 
	 * @return manegement1Code �Ǘ�1�R�[�h
	 */
	public String getManegement1Code() {
		return manegement1Code;
	}

	/**
	 * �Ǘ�1�R�[�h�̐ݒ�
	 * 
	 * @param manegement1Code �Ǘ�1�R�[�h
	 */
	public void setManegement1Code(String manegement1Code) {
		this.manegement1Code = manegement1Code;
	}

	/**
	 * �Ǘ�2�R�[�h�̎擾
	 * 
	 * @return manegement2Code �Ǘ�2�R�[�h
	 */
	public String getManegement2Code() {
		return manegement2Code;
	}

	/**
	 * �Ǘ�2�R�[�h�̐ݒ�
	 * 
	 * @param manegement2Code �Ǘ�2�R�[�h
	 */
	public void setManegement2Code(String manegement2Code) {
		this.manegement2Code = manegement2Code;
	}

	/**
	 * �Ǘ�3�R�[�h�̎擾
	 * 
	 * @return manegement3Code �Ǘ�3�R�[�h
	 */
	public String getManegement3Code() {
		return manegement3Code;
	}

	/**
	 * �Ǘ�3�R�[�h�̐ݒ�
	 * 
	 * @param manegement3Code �Ǘ�3�R�[�h
	 */
	public void setManegement3Code(String manegement3Code) {
		this.manegement3Code = manegement3Code;
	}

	/**
	 * �Ǘ�4�R�[�h�̎擾
	 * 
	 * @return manegement4Code �Ǘ�4�R�[�h
	 */
	public String getManegement4Code() {
		return manegement4Code;
	}

	/**
	 * �Ǘ�4�R�[�h�̐ݒ�
	 * 
	 * @param manegement4Code �Ǘ�4�R�[�h
	 */
	public void setManegement4Code(String manegement4Code) {
		this.manegement4Code = manegement4Code;
	}

	/**
	 * �Ǘ�5�R�[�h�̎擾
	 * 
	 * @return manegement5Code �Ǘ�5�R�[�h
	 */
	public String getManegement5Code() {
		return manegement5Code;
	}

	/**
	 * �Ǘ�5�R�[�h�̐ݒ�
	 * 
	 * @param manegement5Code �Ǘ�5�R�[�h
	 */
	public void setManegement5Code(String manegement5Code) {
		this.manegement5Code = manegement5Code;
	}

	/**
	 * �Ǘ�6�R�[�h�̎擾
	 * 
	 * @return manegement6Code �Ǘ�6�R�[�h
	 */
	public String getManegement6Code() {
		return manegement6Code;
	}

	/**
	 * �Ǘ�6�R�[�h�̐ݒ�
	 * 
	 * @param manegement6Code �Ǘ�6�R�[�h
	 */
	public void setManegement6Code(String manegement6Code) {
		this.manegement6Code = manegement6Code;
	}

	/**
	 * ���v����1�̎擾
	 * 
	 * @return hm1 ���v����1
	 */
	public String getHm1() {
		return hm1;
	}

	/**
	 * ���v����1�̐ݒ�
	 * 
	 * @param hm1 ���v����1
	 */
	public void setHm1(String hm1) {
		this.hm1 = hm1;
	}

	/**
	 * ���v����2�̎擾
	 * 
	 * @return hm2 ���v����2
	 */
	public String getHm2() {
		return hm2;
	}

	/**
	 * ���v����2�̐ݒ�
	 * 
	 * @param hm2 ���v����2
	 */
	public void setHm2(String hm2) {
		this.hm2 = hm2;
	}

	/**
	 * ���v����3�̎擾
	 * 
	 * @return hm3 ���v����3
	 */
	public String getHm3() {
		return hm3;
	}

	/**
	 * ���v����3�̐ݒ�
	 * 
	 * @param hm3 ���v����3
	 */
	public void setHm3(String hm3) {
		this.hm3 = hm3;
	}

	/**
	 * �`�[�ԍ��̒ǉ�
	 * 
	 * @param slipNo1 �`�[�ԍ�
	 * @param rowNo1 �s�ԍ�
	 */
	public void add(String slipNo1, Integer rowNo1) {
		this.slipNoList.add(slipNo1);
		this.rowNoList.add(rowNo1);
	}

	/**
	 * �`�[�ԍ��A�s�ԍ��̃N���A
	 */
	public void clear() {
		this.slipNoList.clear();
		this.rowNoList.clear();
	}

	/**
	 * �`�[�ԍ����X�g�̎擾
	 * 
	 * @return slipNo �`�[�ԍ����X�g
	 */
	public List<String> getSlipNoList() {
		return slipNoList;
	}

	/**
	 * �s�ԍ����X�g�̎擾
	 * 
	 * @return rowNo �s�ԍ����X�g
	 */
	public List<Integer> getRowNoList() {
		return rowNoList;
	}

	/**
	 * ������̓`�[�ԍ��̎擾
	 * 
	 * @return slipNo ������̓`�[�ԍ�
	 */
	public String getSlipNo() {
		return slipNo;
	}

	/**
	 * ������̓`�[�ԍ��̐ݒ�
	 * 
	 * @param slipNo ������̓`�[�ԍ�
	 */
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	/**
	 * ������̍s�ԍ��̎擾
	 * 
	 * @return rowNo ������̍s�ԍ�
	 */
	public Integer getRowNo() {
		return rowNo;
	}

	/**
	 * ������̍s�ԍ��̐ݒ�
	 * 
	 * @param rowNo ������̍s�ԍ�
	 */
	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}

	/**
	 * �`�[�C���ŏ������̒��o���ǂ����̎擾
	 * 
	 * @return forSlipModify �`�[�C���ŏ������̒��o���ǂ���
	 */
	public boolean isForSlipModify() {
		return forSlipModify;
	}

	/**
	 * �`�[�C���ŏ������̒��o���ǂ����̐ݒ�
	 * 
	 * @param forSlipModify �`�[�C���ŏ������̒��o���ǂ���
	 */
	public void setForSlipModify(boolean forSlipModify) {
		this.forSlipModify = forSlipModify;
	}

	/**
	 * ���o�ΏۊO������̓`�[�ԍ��̎擾
	 * 
	 * @return excludeSlipNo ���o�ΏۊO������̓`�[�ԍ�
	 */
	public String getExcludeSlipNo() {
		return excludeSlipNo;
	}

	/**
	 * ���o�ΏۊO������̓`�[�ԍ��̐ݒ�
	 * 
	 * @param excludeSlipNo ���o�ΏۊO������̓`�[�ԍ�
	 */
	public void setExcludeSlipNo(String excludeSlipNo) {
		this.excludeSlipNo = excludeSlipNo;
	}

	/**
	 * �ʉ݃R�[�h�̎擾
	 * 
	 * @return currencyCode �ʉ݃R�[�h
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * �ʉ݃R�[�h�̐ݒ�
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * �v����(�r���p)�̎擾
	 * 
	 * @return kCompany �v����(�r���p)
	 */
	public Company getKCompany() {
		return kCompany;
	}

	/**
	 * �v����(�r���p)�̐ݒ�
	 * 
	 * @param kCompany �v����(�r���p)
	 */
	public void setKCompany(Company kCompany) {
		this.kCompany = kCompany;
	}

}
