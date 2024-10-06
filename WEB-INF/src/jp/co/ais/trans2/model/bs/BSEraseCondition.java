package jp.co.ais.trans2.model.bs;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.item.*;

/**
 * BS勘定消込条件
 */
public class BSEraseCondition extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** 伝票日付 */
	protected Date slipDate = null;

	/** 科目コード */
	protected String itemCode = null;

	/** 補助科目コード */
	protected String subItemCode = null;

	/** 内訳科目コード */
	protected String detailItemCode = null;

	/** 取引先コード */
	protected String customerCode = null;

	/** 部門コード */
	protected String departmentCode = null;

	/** 社員コード */
	protected String employeeCode = null;

	/** 管理1コード */
	protected String manegement1Code = null;

	/** 管理2コード */
	protected String manegement2Code = null;

	/** 管理3コード */
	protected String manegement3Code = null;

	/** 管理4コード */
	protected String manegement4Code = null;

	/** 管理5コード */
	protected String manegement5Code = null;

	/** 管理6コード */
	protected String manegement6Code = null;

	/** 非会計明細1 */
	protected String hm1 = null;

	/** 非会計明細2 */
	protected String hm2 = null;

	/** 非会計明細3 */
	protected String hm3 = null;

	/** 伝票番号リスト */
	protected List<String> slipNoList = new ArrayList<String>();

	/** 行番号リスト(伝票番号リストとペア) */
	protected List<Integer> rowNoList = new ArrayList<Integer>();

	/** 消込先の伝票番号 */
	protected String slipNo = null;

	/** 消込先の行番号 */
	protected Integer rowNo = null;

	/** 伝票修正で消込元の抽出かどうか */
	protected boolean forSlipModify = false;

	/** 抽出対象外消込先の伝票番号 */
	protected String excludeSlipNo = null;

	/** 通貨コード */
	protected String currencyCode = null;

	/** 計上会社(排他用) */
	protected Company kCompany = null;

	/**
	 * 会社コードの取得
	 * 
	 * @return companyCode 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 伝票日付の取得
	 * 
	 * @return slipDate 伝票日付
	 */
	public Date getSlipDate() {
		return slipDate;
	}

	/**
	 * 伝票日付の設定
	 * 
	 * @param slipDate 伝票日付
	 */
	public void setSlipDate(Date slipDate) {
		this.slipDate = slipDate;
	}

	/**
	 * 科目情報の設定
	 * 
	 * @param item 科目情報
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
	 * 科目コードの取得
	 * 
	 * @return itemCode 科目コード
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * 科目コードの設定
	 * 
	 * @param itemCode 科目コード
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * 補助科目コードの取得
	 * 
	 * @return subItemCode 補助科目コード
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * 補助科目コードの設定
	 * 
	 * @param subItemCode 補助科目コード
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * 内訳科目コードの取得
	 * 
	 * @return detailItemCode 内訳科目コード
	 */
	public String getDetailItemCode() {
		return detailItemCode;
	}

	/**
	 * 内訳科目コードの設定
	 * 
	 * @param detailItemCode 内訳科目コード
	 */
	public void setDetailItemCode(String detailItemCode) {
		this.detailItemCode = detailItemCode;
	}

	/**
	 * 取引先コードの取得
	 * 
	 * @return customerCode 取引先コード
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * 取引先コードの設定
	 * 
	 * @param customerCode 取引先コード
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * 部門コードの取得
	 * 
	 * @return departmentCode 部門コード
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * 部門コードの設定
	 * 
	 * @param departmentCode 部門コード
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * 社員コードの取得
	 * 
	 * @return employeeCode 社員コード
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}

	/**
	 * 社員コードの設定
	 * 
	 * @param employeeCode 社員コード
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	/**
	 * 管理1コードの取得
	 * 
	 * @return manegement1Code 管理1コード
	 */
	public String getManegement1Code() {
		return manegement1Code;
	}

	/**
	 * 管理1コードの設定
	 * 
	 * @param manegement1Code 管理1コード
	 */
	public void setManegement1Code(String manegement1Code) {
		this.manegement1Code = manegement1Code;
	}

	/**
	 * 管理2コードの取得
	 * 
	 * @return manegement2Code 管理2コード
	 */
	public String getManegement2Code() {
		return manegement2Code;
	}

	/**
	 * 管理2コードの設定
	 * 
	 * @param manegement2Code 管理2コード
	 */
	public void setManegement2Code(String manegement2Code) {
		this.manegement2Code = manegement2Code;
	}

	/**
	 * 管理3コードの取得
	 * 
	 * @return manegement3Code 管理3コード
	 */
	public String getManegement3Code() {
		return manegement3Code;
	}

	/**
	 * 管理3コードの設定
	 * 
	 * @param manegement3Code 管理3コード
	 */
	public void setManegement3Code(String manegement3Code) {
		this.manegement3Code = manegement3Code;
	}

	/**
	 * 管理4コードの取得
	 * 
	 * @return manegement4Code 管理4コード
	 */
	public String getManegement4Code() {
		return manegement4Code;
	}

	/**
	 * 管理4コードの設定
	 * 
	 * @param manegement4Code 管理4コード
	 */
	public void setManegement4Code(String manegement4Code) {
		this.manegement4Code = manegement4Code;
	}

	/**
	 * 管理5コードの取得
	 * 
	 * @return manegement5Code 管理5コード
	 */
	public String getManegement5Code() {
		return manegement5Code;
	}

	/**
	 * 管理5コードの設定
	 * 
	 * @param manegement5Code 管理5コード
	 */
	public void setManegement5Code(String manegement5Code) {
		this.manegement5Code = manegement5Code;
	}

	/**
	 * 管理6コードの取得
	 * 
	 * @return manegement6Code 管理6コード
	 */
	public String getManegement6Code() {
		return manegement6Code;
	}

	/**
	 * 管理6コードの設定
	 * 
	 * @param manegement6Code 管理6コード
	 */
	public void setManegement6Code(String manegement6Code) {
		this.manegement6Code = manegement6Code;
	}

	/**
	 * 非会計明細1の取得
	 * 
	 * @return hm1 非会計明細1
	 */
	public String getHm1() {
		return hm1;
	}

	/**
	 * 非会計明細1の設定
	 * 
	 * @param hm1 非会計明細1
	 */
	public void setHm1(String hm1) {
		this.hm1 = hm1;
	}

	/**
	 * 非会計明細2の取得
	 * 
	 * @return hm2 非会計明細2
	 */
	public String getHm2() {
		return hm2;
	}

	/**
	 * 非会計明細2の設定
	 * 
	 * @param hm2 非会計明細2
	 */
	public void setHm2(String hm2) {
		this.hm2 = hm2;
	}

	/**
	 * 非会計明細3の取得
	 * 
	 * @return hm3 非会計明細3
	 */
	public String getHm3() {
		return hm3;
	}

	/**
	 * 非会計明細3の設定
	 * 
	 * @param hm3 非会計明細3
	 */
	public void setHm3(String hm3) {
		this.hm3 = hm3;
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
	 * 伝票番号リストの取得
	 * 
	 * @return slipNo 伝票番号リスト
	 */
	public List<String> getSlipNoList() {
		return slipNoList;
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
	 * 消込先の伝票番号の取得
	 * 
	 * @return slipNo 消込先の伝票番号
	 */
	public String getSlipNo() {
		return slipNo;
	}

	/**
	 * 消込先の伝票番号の設定
	 * 
	 * @param slipNo 消込先の伝票番号
	 */
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	/**
	 * 消込先の行番号の取得
	 * 
	 * @return rowNo 消込先の行番号
	 */
	public Integer getRowNo() {
		return rowNo;
	}

	/**
	 * 消込先の行番号の設定
	 * 
	 * @param rowNo 消込先の行番号
	 */
	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}

	/**
	 * 伝票修正で消込元の抽出かどうかの取得
	 * 
	 * @return forSlipModify 伝票修正で消込元の抽出かどうか
	 */
	public boolean isForSlipModify() {
		return forSlipModify;
	}

	/**
	 * 伝票修正で消込元の抽出かどうかの設定
	 * 
	 * @param forSlipModify 伝票修正で消込元の抽出かどうか
	 */
	public void setForSlipModify(boolean forSlipModify) {
		this.forSlipModify = forSlipModify;
	}

	/**
	 * 抽出対象外消込先の伝票番号の取得
	 * 
	 * @return excludeSlipNo 抽出対象外消込先の伝票番号
	 */
	public String getExcludeSlipNo() {
		return excludeSlipNo;
	}

	/**
	 * 抽出対象外消込先の伝票番号の設定
	 * 
	 * @param excludeSlipNo 抽出対象外消込先の伝票番号
	 */
	public void setExcludeSlipNo(String excludeSlipNo) {
		this.excludeSlipNo = excludeSlipNo;
	}

	/**
	 * 通貨コードの取得
	 * 
	 * @return currencyCode 通貨コード
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 通貨コードの設定
	 * 
	 * @param currencyCode 通貨コード
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 計上会社(排他用)の取得
	 * 
	 * @return kCompany 計上会社(排他用)
	 */
	public Company getKCompany() {
		return kCompany;
	}

	/**
	 * 計上会社(排他用)の設定
	 * 
	 * @param kCompany 計上会社(排他用)
	 */
	public void setKCompany(Company kCompany) {
		this.kCompany = kCompany;
	}

}
