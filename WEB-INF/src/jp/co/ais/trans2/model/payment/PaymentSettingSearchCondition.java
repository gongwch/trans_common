package jp.co.ais.trans2.model.payment;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;

/**
 * 支払条件マスタの検索条件
 * 
 * @author AIS
 */
public class PaymentSettingSearchCondition extends TransferBase implements Cloneable, FilterableCondition {

	/** 会社コード */
	protected String companyCode = null;

	/** 取引先コード */
	protected String customerCode = null;

	/** 取引先コード(開始) */
	protected String customerCodeFrom = null;

	/** 取引先コード(終了) */
	protected String customerCodeTo = null;

	/** コード */
	protected String code = null;

	/** 開始コード */
	protected String codeFrom = null;

	/** 終了コード */
	protected String codeTo = null;

	/** コード前方一致 */
	protected String codeLike = null;

	/** 名称like */
	protected String nameLike = null;

	/** 有効期間 */
	protected Date validTerm = null;

	/** 支払条件口座番号 */
	protected String yknNo = null;

	/** 支払条件コード(複数) */
	protected List<String> paymentSettingCodeList = new LinkedList<String>();

	/** 最終更新日時 */
	protected Date lastUpdateDate = null;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public PaymentSettingSearchCondition clone() {
		try {
			return (PaymentSettingSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
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
	 * codeFromを取得する。
	 * 
	 * @return String codeFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * codeFromを設定する。
	 * 
	 * @param codeFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * codeLikeを取得する。
	 * 
	 * @return String codeLike
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * codeLikeを設定する。
	 * 
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * codeToを取得する。
	 * 
	 * @return String codeTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * codeToを設定する。
	 * 
	 * @param codeTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
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
	 * customerCodeを取得する。
	 * 
	 * @return String customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * customerCodeを設定する。
	 * 
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * nameLikeを取得する。
	 * 
	 * @return String nameLike
	 */
	public String getNameLike() {
		return nameLike;
	}

	/**
	 * nameLikeを設定する。
	 * 
	 * @param nameLike
	 */
	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	/**
	 * validTermを取得する。
	 * 
	 * @return Date validTerm
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * validTermを設定する。
	 * 
	 * @param validTerm
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

	/**
	 * 取引先コード(開始)
	 * 
	 * @return 取引先コード(開始)
	 */
	public String getCustomerCodeFrom() {
		return this.customerCodeFrom;
	}

	/**
	 * 取引先コード(開始)
	 * 
	 * @param customerCcodeFrom 取引先コード(開始)
	 */
	public void setCustomerCodeFrom(String customerCcodeFrom) {
		this.customerCodeFrom = customerCcodeFrom;
	}

	/**
	 * 取引先コード(終了)
	 * 
	 * @return 取引先コード(終了)
	 */
	public String getCustomerCodeTo() {
		return this.customerCodeTo;
	}

	/**
	 * 取引先コード(終了)
	 * 
	 * @param customerCodeTo 取引先コード(終了)
	 */
	public void setCustomerCodeTo(String customerCodeTo) {
		this.customerCodeTo = customerCodeTo;
	}

	/**
	 * 支払条件口座番号の取得
	 * 
	 * @return yknNo 支払条件口座番号
	 */
	public String getYknNo() {
		return yknNo;
	}

	/**
	 * 支払条件口座番号の設定
	 * 
	 * @param yknNo 支払条件口座番号
	 */
	public void setYknNo(String yknNo) {
		this.yknNo = yknNo;
	}

	/**
	 * @return あいまい
	 */
	public String getNamekLike() {
		return nameLike;
	}

	/**
	 * @return あいまい
	 */
	public String getNamesLike() {
		return nameLike;
	}

	/**
	 * 支払条件コード(複数)の取得
	 * 
	 * @return 支払条件コード(複数)
	 */
	public List<String> getCodeList() {
		return this.paymentSettingCodeList;
	}

	/**
	 * 支払条件コード(複数)の取得
	 * 
	 * @return 支払条件コード(複数)
	 */
	public String[] getPaymentSettingCodeList() {
		if (paymentSettingCodeList.isEmpty()) {
			return null;
		}

		return paymentSettingCodeList.toArray(new String[paymentSettingCodeList.size()]);
	}

	/**
	 * 支払条件コード(複数)の設定
	 * 
	 * @param paymentSettingCodeList 支払条件コード(複数)
	 */
	public void setPaymentSettingCodeList(String[] paymentSettingCodeList) {
		addPaymentSettingCode(paymentSettingCodeList);
	}

	/**
	 * 支払条件コード(複数)の設定
	 * 
	 * @param list 支払条件コード(複数)
	 */
	public void addPaymentSettingCode(String... list) {
		for (String paymentSettingCode : list) {
			this.paymentSettingCodeList.add(paymentSettingCode);
		}
	}

	/**
	 * 支払条件コード(複数)のクリア
	 */
	public void clearPaymentSettingCodeList() {
		paymentSettingCodeList.clear();
	}

	/**
	 * 最終更新日時の取得
	 * 
	 * @return lastUpdateDate 最終更新日時
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * 最終更新日時の設定
	 * 
	 * @param lastUpdateDate 最終更新日時
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

}
