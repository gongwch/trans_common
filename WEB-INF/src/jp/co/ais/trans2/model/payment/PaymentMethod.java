package jp.co.ais.trans2.model.payment;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * �x�����@���
 * 
 * @author AIS
 */
public class PaymentMethod extends TransferBase implements AutoCompletable, FilterableEntity {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** ���� */
	protected String name = null;

	/** �������� */
	protected String namek = null;

	/** �ȖڃR�[�h */
	protected String itemCode = null;

	/** �Ȗڗ��� */
	protected String itemNameS = null;

	/** �⏕�ȖڃR�[�h */
	protected String subItemCode = null;

	/** �⏕�Ȗڗ��� */
	protected String subItemNameS = null;

	/** ����ȖڃR�[�h */
	protected String detailItemCode = null;

	/** ����Ȗڗ��� */
	protected String detailItemNameS = null;

	/** �v�㕔��R�[�h */
	protected String departmentCode = null;

	/** �v�㕔�嗪�� */
	protected String departmentNameS = null;

	/** �x���Ώۋ敪 */
	protected Integer paymentDivision = null;

	/** �x�������R�[�h */
	protected PaymentKind paymentKind = null;

	/** �L�����ԊJ�n */
	protected Date dateFrom = null;

	/** �L�����ԏI�� */
	protected Date dateTo = null;

	/**
	 * @return names
	 */
	public String getNames() {
		return getName();
	}

	/**
	 * @return �C���N�������g�T�[�`�\���l
	 */
	public String getDisplayText() {
		return getCode() + " " + getName();
	}

	/**
	 * �Ј��x���̎x�����@���ǂ���
	 * 
	 * @return boolean true�F�Ј��x��
	 */
	public boolean isUseEmployeePayment() {

		if (paymentDivision == null) {
			return false;
		}
		return paymentDivision == 0;
	}

	/**
	 * �ЊO�x���̎x�����@���ǂ���
	 * 
	 * @return boolean true�F�ЊO�x��
	 */
	public boolean isUseExPayment() {

		if (paymentDivision == null) {
			return false;
		}
		return paymentDivision == 1;
	}

	/**
	 * code���擾����B
	 * 
	 * @return String code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * code��ݒ肷��B
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * companyCode���擾����B
	 * 
	 * @return String companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * companyCode��ݒ肷��B
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * dateFrom���擾����B
	 * 
	 * @return Date dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * dateFrom��ݒ肷��B
	 * 
	 * @param dateFrom
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * dateTo���擾����B
	 * 
	 * @return Date dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * dateTo��ݒ肷��B
	 * 
	 * @param dateTo
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * departmentCode���擾����B
	 * 
	 * @return String departmentCode
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * departmentCode��ݒ肷��B
	 * 
	 * @param departmentCode
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * departmentNameS���擾����B
	 * 
	 * @return String departmentNameS
	 */
	public String getDepartmentNameS() {
		return departmentNameS;
	}

	/**
	 * departmentNameS��ݒ肷��B
	 * 
	 * @param departmentNameS
	 */
	public void setDepartmentNameS(String departmentNameS) {
		this.departmentNameS = departmentNameS;
	}

	/**
	 * detailItemCode���擾����B
	 * 
	 * @return String detailItemCode
	 */
	public String getDetailItemCode() {
		return detailItemCode;
	}

	/**
	 * detailItemCode��ݒ肷��B
	 * 
	 * @param detailItemCode
	 */
	public void setDetailItemCode(String detailItemCode) {
		this.detailItemCode = detailItemCode;
	}

	/**
	 * itemCode���擾����B
	 * 
	 * @return String itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * itemCode��ݒ肷��B
	 * 
	 * @param itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * itemNameS���擾����B
	 * 
	 * @return String itemNameS
	 */
	public String getItemNameS() {
		return itemNameS;
	}

	/**
	 * itemNameS��ݒ肷��B
	 * 
	 * @param itemNameS
	 */
	public void setItemNameS(String itemNameS) {
		this.itemNameS = itemNameS;
	}

	/**
	 * subItemNameS���擾����B
	 * 
	 * @return String subItemNameS
	 */
	public String getSubItemNameS() {
		return subItemNameS;
	}

	/**
	 * subItemNameS��ݒ肷��B
	 * 
	 * @param subItemNameS
	 */
	public void setSubItemNameS(String subItemNameS) {
		this.subItemNameS = subItemNameS;
	}

	/**
	 * detailItemNameS���擾����B
	 * 
	 * @return String itemCode
	 */
	public String getDetailItemNameS() {
		return detailItemNameS;
	}

	/**
	 * detailItemNameS��ݒ肷��B
	 * 
	 * @param detailItemNameS
	 */
	public void setDetailItemNameS(String detailItemNameS) {
		this.detailItemNameS = detailItemNameS;
	}

	/**
	 * name���擾����B
	 * 
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * name��ݒ肷��B
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * namek���擾����B
	 * 
	 * @return String namek
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * namek��ݒ肷��B
	 * 
	 * @param namek
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * paymentDivision���擾����B
	 * 
	 * @return Integer paymentDivision
	 */
	public Integer getPaymentDivision() {
		return paymentDivision;
	}

	/**
	 * paymentDivision��ݒ肷��B
	 * 
	 * @param paymentDivision
	 */
	public void setPaymentDivision(Integer paymentDivision) {
		this.paymentDivision = paymentDivision;
	}

	/**
	 * paymentKind���擾����B
	 * 
	 * @return PaymentKind paymentKind
	 */
	public PaymentKind getPaymentKind() {
		return paymentKind;
	}

	/**
	 * paymentKind��ݒ肷��B
	 * 
	 * @param paymentKind
	 */
	public void setPaymentKind(PaymentKind paymentKind) {
		this.paymentKind = paymentKind;
	}

	/**
	 * subItemCode���擾����B
	 * 
	 * @return String subItemCode
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * subItemCode��ݒ肷��B
	 * 
	 * @param subItemCode
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

}
