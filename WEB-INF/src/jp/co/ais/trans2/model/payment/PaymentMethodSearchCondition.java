package jp.co.ais.trans2.model.payment;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * 支払方法の検索条件
 * 
 * @author AIS
 */
public class PaymentMethodSearchCondition extends TransferBase implements Cloneable, FilterableCondition,
	OPLoginCondition {

	/** 会社コード */
	protected String companyCode = null;

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

	/** 検索名称 like */
	protected String namekLike = null;

	/** 社員支払 */
	protected boolean useEmployeePayment = false;

	/** 社外支払 */
	protected boolean useExPayment = false;

	/** 有効期間 */
	protected Date validTerm = null;

	/** 支払方法コード */
	protected List<String> codeList = new LinkedList<String>();

	/** 含めない支払方法内部コードリスト */
	protected List<PaymentKind> notPaymentKindList = new LinkedList<PaymentKind>();

	/** 支払方法内部コードリスト */
	protected List<PaymentKind> paymentKindList = new LinkedList<PaymentKind>();

	/** 最終更新日時 */
	protected Date lastUpdateDate = null;

	/** 現在件数 */
	protected int nowCount = 0;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public PaymentMethodSearchCondition clone() {
		try {
			return (PaymentMethodSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * accountNokLikeを取得する。
	 * 
	 * @return String namekLike
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * accountNokLikeを設定する。
	 * 
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
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
	 * useEmployeePaymentを取得する。
	 * 
	 * @return boolean useEmployeePayment
	 */
	public boolean isUseEmployeePayment() {
		return useEmployeePayment;
	}

	/**
	 * useEmployeePaymentを設定する。
	 * 
	 * @param useEmployeePayment
	 */
	public void setUseEmployeePayment(boolean useEmployeePayment) {
		this.useEmployeePayment = useEmployeePayment;
	}

	/**
	 * useExPaymentを取得する。
	 * 
	 * @return boolean useExPayment
	 */
	public boolean isUseExPayment() {
		return useExPayment;
	}

	/**
	 * useExPaymentを設定する。
	 * 
	 * @param useExPayment
	 */
	public void setUseExPayment(boolean useExPayment) {
		this.useExPayment = useExPayment;
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
	 * 支払方法コード(複数)
	 * 
	 * @return 支払方法コード(複数)
	 */
	public List<String> getCodeList() {
		return codeList;
	}

	/**
	 * 支払方法コード(複数)
	 * 
	 * @param lsit 支払方法コード(複数)
	 */
	public void setCodeList(List<String> lsit) {
		this.codeList = lsit;
	}

	/**
	 * 支払方法コード(複数)
	 * 
	 * @param lsit 支払方法コード(複数)
	 */
	public void addCode(String... lsit) {
		for (String no : lsit) {
			this.codeList.add(no);
		}
	}

	/**
	 * 支払方法コード(複数)のクリア
	 */
	public void clearCodeList() {
		codeList.clear();
	}

	/**
	 * 含めない支払方法内部コード
	 * 
	 * @return 支払方法内部コード
	 */
	public List<PaymentKind> getNotPaymentKindList() {
		return notPaymentKindList;
	}

	/**
	 * 含めない支払方法内部コード
	 * 
	 * @param paymentKindList 支払方法内部コード
	 */
	public void setNotPaymentKindList(List<PaymentKind> paymentKindList) {
		this.notPaymentKindList = paymentKindList;
	}

	/**
	 * 含めない支払方法内部コード
	 * 
	 * @param kinds 支払方法内部コード
	 */
	public void addNotPaymentKind(PaymentKind... kinds) {
		for (PaymentKind kind : kinds) {
			this.notPaymentKindList.add(kind);
		}
	}

	/**
	 * 含めない支払方法内部コード
	 * 
	 * @return 支払方法内部コード
	 */
	public String[] getNotPaymentKinds() {
		String[] arry = new String[notPaymentKindList.size()];
		int i = 0;
		for (PaymentKind kind : notPaymentKindList) {
			arry[i] = kind.value;
			i++;
		}

		return arry;
	}

	/**
	 * 支払方法内部コード
	 * 
	 * @return 支払方法内部コード
	 */
	public List<PaymentKind> getPaymentKindList() {
		return paymentKindList;
	}

	/**
	 * 支払方法内部コード
	 * 
	 * @param paymentKindList 支払方法内部コード
	 */
	public void setPaymentKindList(List<PaymentKind> paymentKindList) {
		this.paymentKindList = paymentKindList;
	}

	/**
	 * 支払方法内部コード
	 * 
	 * @param kinds 支払方法内部コード
	 */
	public void addPaymentKind(PaymentKind... kinds) {
		for (PaymentKind kind : kinds) {
			this.paymentKindList.add(kind);
		}
	}

	/**
	 * 支払方法内部コード
	 * 
	 * @return 支払方法内部コード
	 */
	public String[] getPaymentKinds() {
		String[] arry = new String[paymentKindList.size()];
		int i = 0;
		for (PaymentKind kind : paymentKindList) {
			arry[i] = kind.value;
			i++;
		}

		return arry;
	}

	/**
	 * @return あいまい
	 */
	public String getNamesLike() {
		return nameLike;
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

	/**
	 * 現在件数の取得
	 * 
	 * @return nowCount 現在件数
	 */
	public int getNowCount() {
		return nowCount;
	}

	/**
	 * 現在件数の設定
	 * 
	 * @param nowCount 現在件数
	 */
	public void setNowCount(int nowCount) {
		this.nowCount = nowCount;
	}

}
