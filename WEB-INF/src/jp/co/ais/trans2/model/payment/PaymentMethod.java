package jp.co.ais.trans2.model.payment;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * 支払方法情報
 * 
 * @author AIS
 */
public class PaymentMethod extends TransferBase implements AutoCompletable, FilterableEntity {

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/** 名称 */
	protected String name = null;

	/** 検索名称 */
	protected String namek = null;

	/** 科目コード */
	protected String itemCode = null;

	/** 科目略称 */
	protected String itemNameS = null;

	/** 補助科目コード */
	protected String subItemCode = null;

	/** 補助科目略称 */
	protected String subItemNameS = null;

	/** 内訳科目コード */
	protected String detailItemCode = null;

	/** 内訳科目略称 */
	protected String detailItemNameS = null;

	/** 計上部門コード */
	protected String departmentCode = null;

	/** 計上部門略称 */
	protected String departmentNameS = null;

	/** 支払対象区分 */
	protected Integer paymentDivision = null;

	/** 支払内部コード */
	protected PaymentKind paymentKind = null;

	/** 有効期間開始 */
	protected Date dateFrom = null;

	/** 有効期間終了 */
	protected Date dateTo = null;

	/**
	 * @return names
	 */
	public String getNames() {
		return getName();
	}

	/**
	 * @return インクリメントサーチ表示値
	 */
	public String getDisplayText() {
		return getCode() + " " + getName();
	}

	/**
	 * 社員支払の支払方法かどうか
	 * 
	 * @return boolean true：社員支払
	 */
	public boolean isUseEmployeePayment() {

		if (paymentDivision == null) {
			return false;
		}
		return paymentDivision == 0;
	}

	/**
	 * 社外支払の支払方法かどうか
	 * 
	 * @return boolean true：社外支払
	 */
	public boolean isUseExPayment() {

		if (paymentDivision == null) {
			return false;
		}
		return paymentDivision == 1;
	}

	/**
	 * codeを取得する。
	 * 
	 * @return String code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * codeを設定する。
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * companyCodeを取得する。
	 * 
	 * @return String companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * companyCodeを設定する。
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * dateFromを取得する。
	 * 
	 * @return Date dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * dateFromを設定する。
	 * 
	 * @param dateFrom
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * dateToを取得する。
	 * 
	 * @return Date dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * dateToを設定する。
	 * 
	 * @param dateTo
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * departmentCodeを取得する。
	 * 
	 * @return String departmentCode
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * departmentCodeを設定する。
	 * 
	 * @param departmentCode
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * departmentNameSを取得する。
	 * 
	 * @return String departmentNameS
	 */
	public String getDepartmentNameS() {
		return departmentNameS;
	}

	/**
	 * departmentNameSを設定する。
	 * 
	 * @param departmentNameS
	 */
	public void setDepartmentNameS(String departmentNameS) {
		this.departmentNameS = departmentNameS;
	}

	/**
	 * detailItemCodeを取得する。
	 * 
	 * @return String detailItemCode
	 */
	public String getDetailItemCode() {
		return detailItemCode;
	}

	/**
	 * detailItemCodeを設定する。
	 * 
	 * @param detailItemCode
	 */
	public void setDetailItemCode(String detailItemCode) {
		this.detailItemCode = detailItemCode;
	}

	/**
	 * itemCodeを取得する。
	 * 
	 * @return String itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * itemCodeを設定する。
	 * 
	 * @param itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * itemNameSを取得する。
	 * 
	 * @return String itemNameS
	 */
	public String getItemNameS() {
		return itemNameS;
	}

	/**
	 * itemNameSを設定する。
	 * 
	 * @param itemNameS
	 */
	public void setItemNameS(String itemNameS) {
		this.itemNameS = itemNameS;
	}

	/**
	 * subItemNameSを取得する。
	 * 
	 * @return String subItemNameS
	 */
	public String getSubItemNameS() {
		return subItemNameS;
	}

	/**
	 * subItemNameSを設定する。
	 * 
	 * @param subItemNameS
	 */
	public void setSubItemNameS(String subItemNameS) {
		this.subItemNameS = subItemNameS;
	}

	/**
	 * detailItemNameSを取得する。
	 * 
	 * @return String itemCode
	 */
	public String getDetailItemNameS() {
		return detailItemNameS;
	}

	/**
	 * detailItemNameSを設定する。
	 * 
	 * @param detailItemNameS
	 */
	public void setDetailItemNameS(String detailItemNameS) {
		this.detailItemNameS = detailItemNameS;
	}

	/**
	 * nameを取得する。
	 * 
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * nameを設定する。
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * namekを取得する。
	 * 
	 * @return String namek
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * namekを設定する。
	 * 
	 * @param namek
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * paymentDivisionを取得する。
	 * 
	 * @return Integer paymentDivision
	 */
	public Integer getPaymentDivision() {
		return paymentDivision;
	}

	/**
	 * paymentDivisionを設定する。
	 * 
	 * @param paymentDivision
	 */
	public void setPaymentDivision(Integer paymentDivision) {
		this.paymentDivision = paymentDivision;
	}

	/**
	 * paymentKindを取得する。
	 * 
	 * @return PaymentKind paymentKind
	 */
	public PaymentKind getPaymentKind() {
		return paymentKind;
	}

	/**
	 * paymentKindを設定する。
	 * 
	 * @param paymentKind
	 */
	public void setPaymentKind(PaymentKind paymentKind) {
		this.paymentKind = paymentKind;
	}

	/**
	 * subItemCodeを取得する。
	 * 
	 * @return String subItemCode
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * subItemCodeを設定する。
	 * 
	 * @param subItemCode
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

}
