package jp.co.ais.trans2.model.balance;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.db.*;

/**
 * 残高検索条件
 */
public class BalanceCondition extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String companyCode;

	/** 取引先コード（開始） */
	protected String clientCodeFrom;

	/** 取引先コード（終了） */
	protected String clientCodeTo;

	/** 部門コード（開始） */
	protected String departmentCodeFrom;

	/** 部門コード（終了） */
	protected String departmentCodeTo;

	/** 取引先/支払先コード（ライク） */
	protected String customerNamesLike;

	/** 請求/計上伝票番号（前方一致） */
	protected String slipNoLike;

	/** 請求書番号（前方一致） */
	protected String billNoLike;

	/** 入金/支払予定日 */
	protected Date expectedDay;

	/** 請求/計上伝票番号 */
	protected String slipNo;

	/** 伝票行番号 */
	protected int slipLineNo = -1;

	/** 請求日/計上日 */
	protected Date slipDate;

	/** プログラムID */
	protected String programCode;

	/** ユーザーID */
	protected String userCode;

	/** 更新日付 */
	protected Date updateDate;

	/** 対象として含めない消込伝票番号 */
	protected String notIncledEraseSlipNo;

	/** 消込伝票日付 */
	protected Date eraseSlipDate;

	/** 消込伝票番号 */
	protected String eraseSlipNo;

	/** 消込伝票行番号 */
	protected int eraseSlipLineNo = -1;

	/** 通貨コード */
	protected String currencyCode;

	/** 請求/計上伝票番号(複数指定用) */
	protected List<String> slipNoList = new LinkedList<String>();

	/** 消込金額(邦貨) */
	protected BigDecimal kin;

	/** 行番号リスト(伝票番号リストとペア) */
	protected List<Integer> rowNoList = new ArrayList<Integer>();

	/** 計上部門（ライク） */
	protected String departmentNameLike;

	/** 伝票摘要（ライク） */
	protected String slipTekLike;

	/**
	 * クローン
	 */
	@Override
	public BalanceCondition clone() {
		try {
			return (BalanceCondition) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * LIKE文変換した条件にコンバート
	 * 
	 * @return 条件クラス
	 */
	protected BalanceCondition toSQL() {
		BalanceCondition clone = this.clone();
		clone.setCustomerNamesLike(SQLUtil.getLikeStatement(customerNamesLike, SQLUtil.NCHAR_AMBI));
		clone.setSlipNoLike(SQLUtil.getLikeStatement(slipNoLike, SQLUtil.CHAR_FRONT));
		clone.setBillNoLike(SQLUtil.getLikeStatement(billNoLike, SQLUtil.NCHAR_AMBI));

		clone.setDepartmentNameLike(SQLUtil.getLikeStatement(departmentNameLike, SQLUtil.NCHAR_AMBI));
		clone.setSlipTekLike(SQLUtil.getLikeStatement(slipTekLike, SQLUtil.NCHAR_AMBI));

		return clone;
	}

	/**
	 * 請求書番号（前方一致）
	 * 
	 * @return 請求書番号（前方一致）
	 */
	public String getBillNoLike() {
		return billNoLike;
	}

	/**
	 * 請求書番号（前方一致）
	 * 
	 * @param billCodeLike 請求書番号（前方一致）
	 */
	public void setBillNoLike(String billCodeLike) {
		this.billNoLike = billCodeLike;
	}

	/**
	 * 請求日/計上日
	 * 
	 * @return 請求日/計上日
	 */
	public Date getSlipDate() {
		return slipDate;
	}

	/**
	 * 請求日/計上日
	 * 
	 * @param slipDate 請求日/計上日
	 */
	public void setSlipDate(Date slipDate) {
		this.slipDate = slipDate;
	}

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コード
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 取引先コード（開始）
	 * 
	 * @return 取引先コード（開始）
	 */
	public String getClientCodeFrom() {
		return clientCodeFrom;
	}

	/**
	 * 取引先コード（開始）
	 * 
	 * @param customerCodeFrom 取引先コード（開始）
	 */
	public void setClientCodeFrom(String customerCodeFrom) {
		this.clientCodeFrom = customerCodeFrom;
	}

	/**
	 * 取引先コード（終了）
	 * 
	 * @return 取引先コード（終了）
	 */
	public String getClientCodeTo() {
		return clientCodeTo;
	}

	/**
	 * 取引先コード（終了）
	 * 
	 * @param customerCodeTo 取引先コード（終了）
	 */
	public void setClientCodeTo(String customerCodeTo) {
		this.clientCodeTo = customerCodeTo;
	}

	/**
	 * 取引先/支払先略称（曖昧）
	 * 
	 * @return 取引先/支払先略称（曖昧）
	 */
	public String getCustomerNamesLike() {
		return customerNamesLike;
	}

	/**
	 * 取引先/支払先略称（曖昧）
	 * 
	 * @param customerNamesLike 取引先/支払先略称（曖昧）
	 */
	public void setCustomerNamesLike(String customerNamesLike) {
		this.customerNamesLike = customerNamesLike;
	}

	/**
	 * 部門コード（開始）
	 * 
	 * @return 部門コード（開始）
	 */
	public String getDepartmentCodeFrom() {
		return departmentCodeFrom;
	}

	/**
	 * 部門コード（開始）
	 * 
	 * @param departmentCodeFrom 部門コード（開始）
	 */
	public void setDepartmentCodeFrom(String departmentCodeFrom) {
		this.departmentCodeFrom = departmentCodeFrom;
	}

	/**
	 * 部門コード（終了）
	 * 
	 * @return 部門コード（終了）
	 */
	public String getDepartmentCodeTo() {
		return departmentCodeTo;
	}

	/**
	 * 部門コード（終了）
	 * 
	 * @param departmentCodeTo 部門コード（終了）
	 */
	public void setDepartmentCodeTo(String departmentCodeTo) {
		this.departmentCodeTo = departmentCodeTo;
	}

	/**
	 * 入金予定日
	 * 
	 * @return 入金予定日
	 */
	public Date getExpectedDay() {
		return expectedDay;
	}

	/**
	 * 入金予定日
	 * 
	 * @param paymentExpectedDay 入金予定日
	 */
	public void setExpectedDay(Date paymentExpectedDay) {
		this.expectedDay = paymentExpectedDay;
	}

	/**
	 * 請求伝票番号（前方一致）
	 * 
	 * @return 請求伝票番号（前方一致）
	 */
	public String getSlipNoLike() {
		return slipNoLike;
	}

	/**
	 * 請求伝票番号（前方一致）
	 * 
	 * @param slipNoLike 請求伝票番号（前方一致）
	 */
	public void setSlipNoLike(String slipNoLike) {
		this.slipNoLike = slipNoLike;
	}

	/**
	 * 伝票行番号
	 * 
	 * @return 伝票行番号
	 */
	public int getSlipLineNo() {
		return slipLineNo;
	}

	/**
	 * 伝票行番号
	 * 
	 * @param slipLineNo 伝票行番号
	 */
	public void setSlipLineNo(int slipLineNo) {
		this.slipLineNo = slipLineNo;
	}

	/**
	 * 伝票番号
	 * 
	 * @return 伝票番号
	 */
	public String getSlipNo() {
		return slipNo;
	}

	/**
	 * 伝票番号
	 * 
	 * @param slipNo 伝票番号
	 */
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	/**
	 * プログラムID
	 * 
	 * @return プログラムID
	 */
	public String getProgramCode() {
		return programCode;
	}

	/**
	 * プログラムID
	 * 
	 * @param programCode プログラムID
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	/**
	 * 更新日付
	 * 
	 * @return 更新日付
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * 更新日付
	 * 
	 * @param updateDate 更新日付
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * ユーザーID
	 * 
	 * @return ユーザーID
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * ユーザーID
	 * 
	 * @param userCode ユーザーID
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * 支払実績日
	 * 
	 * @return 支払実績日
	 */
	public Date getEraseSlipDate() {
		return eraseSlipDate;
	}

	/**
	 * 支払実績日
	 * 
	 * @param paymentSlipDate 支払実績日
	 */
	public void setEraseSlipDate(Date paymentSlipDate) {
		this.eraseSlipDate = paymentSlipDate;
	}

	/**
	 * 対象として含める消込伝票番号
	 * 
	 * @return 対象として含める消込伝票番号
	 */
	public String getNotIncledEraseSlipNo() {
		return notIncledEraseSlipNo;
	}

	/**
	 * 対象として含める消込伝票番号
	 * 
	 * @param incledEraseSlipNo 対象として含める消込伝票番号
	 */
	public void setNotIncledEraseSlipNo(String incledEraseSlipNo) {
		this.notIncledEraseSlipNo = incledEraseSlipNo;
	}

	/**
	 * 消込伝票番号
	 * 
	 * @return 消込伝票番号
	 */
	public String getEraseSlipNo() {
		return eraseSlipNo;
	}

	/**
	 * 消込伝票番号
	 * 
	 * @param eraseSlipNo 消込伝票番号
	 */
	public void setEraseSlipNo(String eraseSlipNo) {
		this.eraseSlipNo = eraseSlipNo;
	}

	/**
	 * 消込伝票行番号
	 * 
	 * @return 消込伝票行番号
	 */
	public int getEraseSlipLineNo() {
		return eraseSlipLineNo;
	}

	/**
	 * 消込伝票行番号
	 * 
	 * @param eraseSlipLineNo 消込伝票行番号
	 */
	public void setEraseSlipLineNo(int eraseSlipLineNo) {
		this.eraseSlipLineNo = eraseSlipLineNo;
	}

	/**
	 * 通貨コードを取得する。
	 * 
	 * @return String currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 通貨コードを設定する。
	 * 
	 * @param currencyCode
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 消込金額(邦貨)
	 * 
	 * @param kin 消込金額(邦貨)
	 */
	public void setKin(BigDecimal kin) {
		this.kin = kin;
	}

	/**
	 * 消込金額(邦貨)
	 * 
	 * @return kin 消込金額(邦貨)
	 */
	public BigDecimal getKin() {
		return kin;
	}

	/**
	 * 請求/計上伝票番号(複数指定用)
	 * 
	 * @return 請求/計上伝票番号リスト
	 */
	public List<String> getSlipNoList() {
		return slipNoList;
	}

	/**
	 * 請求/計上伝票番号(複数指定用)
	 * 
	 * @param slipNoList 請求/計上伝票番号リスト
	 */
	public void setSlipNoList(List<String> slipNoList) {
		this.slipNoList = slipNoList;
	}

	/**
	 * 請求/計上伝票番号リストへの追加(複数指定用)
	 * 
	 * @param slipNo_ 請求/計上伝票番号
	 */
	public void addSlipNo(String slipNo_) {
		this.slipNoList.add(slipNo_);
	}

	/**
	 * 行番号リストの取得
	 * 
	 * @return rowNo 行番号リスト
	 */
	public List<Integer> getRowNoList() {
		return rowNoList;
	}

	/**
	 * 伝票番号の追加
	 * 
	 * @param slipNo1 伝票番号
	 * @param rowNo1 行番号
	 */
	public void add(String slipNo1, Integer rowNo1) {
		this.slipNoList.add(slipNo1);
		this.rowNoList.add(rowNo1);
	}

	/**
	 * 伝票番号、行番号のクリア
	 */
	public void clear() {
		this.slipNoList.clear();
		this.rowNoList.clear();
	}
	
	/**
	 * 計上部門（ライク）
	 * 
	 * @return 計上部門（ライク）
	 */
	public String getDepartmentNameLike() {
		return departmentNameLike;
	}

	/**
	 * 計上部門（ライク）
	 * 
	 * @param departmentNameLike 計上部門（ライク）
	 */
	public void setDepartmentNameLike(String departmentNameLike) {
		this.departmentNameLike = departmentNameLike;
	}

	/**
	 * 伝票摘要（ライク）
	 * 
	 * @return 伝票摘要（ライク）
	 */
	public String getSlipTekLike() {
		return slipTekLike;
	}

	/**
	 * 伝票摘要（ライク）
	 * 
	 * @param slipTekLike 伝票摘要（ライク）
	 */
	public void setSlipTekLike(String slipTekLike) {
		this.slipTekLike = slipTekLike;
	}
}
