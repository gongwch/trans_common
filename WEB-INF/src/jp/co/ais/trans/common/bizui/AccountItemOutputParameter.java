package jp.co.ais.trans.common.bizui;

import java.io.*;
import java.util.*;

/**
 * 科目出力パラメーター
 */
public class AccountItemOutputParameter implements Serializable {

	/** 指定されたコードが存在しているかどうか */
	private boolean isExist;

	/** 科目コード */
	private String itemCode = "";

	/** 科目略称 */
	private String itemAbbrName = "";

	/** 補助科目コード */
	private String subItemCode = "";

	/** 補助科目略称 */
	private String subItemAbbrName = "";

	/** 内訳科目コード */
	private String breakDownItemCode = "";

	/** 内訳科目略称 */
	private String breakDownItemAbbrName = "";

	/** 補助区分 */
	private String subItemFlag = "";

	/** 内訳区分 */
	private String breakDownItemFlag = "";

	/** 売上課税入力フラグ */
	private String salesTaxInputFlag = "";

	/** 仕入課税入力フラグ */
	private String purchaseTaxationInputFlag = "";

	/** 消費税ｺｰﾄﾞ */
	private String consumptionTaxCode = "";

	/** 消費税略称 */
	private String consumptionTaxAbbrName = "";

	/** 消費税区分 */
	private String consumptionTaxDivision = "";

	/** 取引先入力ﾌﾗｸﾞ */
	private String customerInputFlag = "0";

	/** 社員入力ﾌﾗｸﾞ */
	private String employeeInputFlag = "";

	/** 社員ｺｰﾄﾞ */
	private String employeeCode = "";

	/** 社員略称 */
	private String employeeAbbrName = "";

	/** 管理1入力ﾌﾗｸﾞ */
	private String management1InputFlag = "";

	/** 管理2入力ﾌﾗｸﾞ */
	private String management2InputFlag = "";

	/** 管理3入力ﾌﾗｸﾞ */
	private String management3InputFlag = "";

	/** 管理4入力ﾌﾗｸﾞ */
	private String management4InputFlag = "";

	/** 管理5入力ﾌﾗｸﾞ */
	private String management5InputFlag = "";

	/** 管理6入力ﾌﾗｸﾞ */
	private String management6InputFlag = "";

	/** 非会計1入力フラグ */
	private String nonAccountingDetail1Flag = "";

	/** 非会計2入力フラグ */
	private String nonAccountingDetail2Flag = "";

	/** 非会計3入力フラグ */
	private String nonAccountingDetail3Flag = "";

	/** 発生日入力フラグ */
	private String accrualDateInputFlag = "";

	/** 多通貨フラグ */
	private String multipleCurrencyInputFlag = "";

	/** GL科目制御区分 */
	private String kmkCntGl = "";

	/** AP科目制御区分 */
	private String kmkCntAp = "";

	/** AR科目制御区分 */
	private String kmkCntAr = "";

	/** 科目体系コード */
	private String itemSystemCode = "";

	/** 開始日 */
	private Date strDate;

	/** 終了日 */
	private Date endDate;

	/**
	 * 終了日を取得する。
	 * 
	 * @return Date endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 終了日を設定する。
	 * 
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 開始日を取得する。
	 * 
	 * @return Date strDate
	 */
	public Date getStrDate() {
		return strDate;
	}

	/**
	 * 開始日を設定する。
	 * 
	 * @param strDate
	 */
	public void setStrDate(Date strDate) {
		this.strDate = strDate;
	}

	/**
	 * 指定されたコードが存在しているかどうか
	 * 
	 * @return true:存在
	 */
	public boolean isExist() {
		return isExist;
	}

	/**
	 * 指定されたコードが存在しているかどうか
	 * 
	 * @param isExist true:存在
	 */
	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}

	/**
	 * 科目コードを設定する。<BR>
	 * 
	 * @param itemCode 科目コード
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * 科目コードを返す。<BR>
	 * 
	 * @return itemCode 科目コード
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * 科目略称を設定する。<BR>
	 * 
	 * @param itemAbbrName 科目略称
	 */
	public void setItemAbbrName(String itemAbbrName) {
		this.itemAbbrName = itemAbbrName;
	}

	/**
	 * 科目略称を返す。<BR>
	 * 
	 * @return itemAbbrName 科目略称
	 */
	public String getItemAbbrName() {
		return itemAbbrName;
	}

	/**
	 * 補助科目コードを設定する。<BR>
	 * 
	 * @param subItemCode 補助科目コード
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * 補助科目コードを返す。<BR>
	 * 
	 * @return subItemCode 補助科目コード
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * 補助科目略称を設定する。<BR>
	 * 
	 * @param subItemAbbrName 補助科目略称
	 */
	public void setSubItemAbbrName(String subItemAbbrName) {
		this.subItemAbbrName = subItemAbbrName;
	}

	/**
	 * 補助科目略称を返す。<BR>
	 * 
	 * @return subItemAbbrName 補助科目略称
	 */
	public String getSubItemAbbrName() {
		return subItemAbbrName;
	}

	/**
	 * 内訳科目コードを設定する。<BR>
	 * 
	 * @param breakDownItemCode 内訳科目コード
	 */
	public void setBreakDownItemCode(String breakDownItemCode) {
		this.breakDownItemCode = breakDownItemCode;
	}

	/**
	 * 内訳科目コードを返す。<BR>
	 * 
	 * @return breakDownItemCode 内訳科目コード
	 */
	public String getBreakDownItemCode() {
		return breakDownItemCode;
	}

	/**
	 * 内訳科目略称を設定する。<BR>
	 * 
	 * @param breakDownItemAbbrName 内訳科目略称
	 */
	public void setBreakDownItemAbbrName(String breakDownItemAbbrName) {
		this.breakDownItemAbbrName = breakDownItemAbbrName;
	}

	/**
	 * 内訳科目略称を返す。<BR>
	 * 
	 * @return breakDownItemAbbrName 内訳科目略称
	 */
	public String getBreakDownItemAbbrName() {
		return breakDownItemAbbrName;
	}

	/**
	 * 補助区分を設定する。<BR>
	 * 
	 * @param subItemFlag 補助区分 0:なし 1:あり
	 */
	public void setSubItemFlag(String subItemFlag) {
		this.subItemFlag = subItemFlag;
	}

	/**
	 * 補助区分を返す。<BR>
	 * 
	 * @return subItemFlag 補助区分 true:あり false:なし
	 */
	public boolean isIncludeSubItem() {
		return "1".equals(this.subItemFlag);
	}

	/**
	 * 内訳区分を設定する。<BR>
	 * 
	 * @param breakDownItemFlag 内訳区分 0:なし 1:あり
	 */
	public void setBreakDownItemFlag(String breakDownItemFlag) {
		this.breakDownItemFlag = breakDownItemFlag;
	}

	/**
	 * 内訳区分を返す。<BR>
	 * 
	 * @return breakDownItemFlag 内訳区分 true:あり false:なし
	 */
	public boolean isIncludeBreakDownItem() {
		return "1".equals(this.breakDownItemFlag);
	}

	/**
	 * 売上課税入力フラグを設定する。<BR>
	 * 
	 * @param salesTaxInputFlag 売上課税入力フラグ 0:入力不可 1:入力可
	 */
	public void setSalesTaxInputFlag(String salesTaxInputFlag) {
		this.salesTaxInputFlag = salesTaxInputFlag;
	}

	/**
	 * 売上課税入力フラグを返す。<BR>
	 * 
	 * @return salesTaxInputFlag 売上課税入力フラグ true:入力可 false:入力不可
	 */
	public boolean isIncludeSalesTaxInputFlag() {
		return "1".equals(this.salesTaxInputFlag);
	}

	/**
	 * 仕入課税入力フラグを設定する。<BR>
	 * 
	 * @param purchaseTaxationInputFlag 仕入課税入力フラグ 0:入力不可 1:入力可
	 */
	public void setPurchaseTaxationInputFlag(String purchaseTaxationInputFlag) {
		this.purchaseTaxationInputFlag = purchaseTaxationInputFlag;
	}

	/**
	 * 仕入課税入力フラグを返す。<BR>
	 * 
	 * @return purchaseTaxationInputFlag 仕入課税入力フラグ true:入力可 false:入力不可
	 */
	public boolean isIncludePurchaseTaxationInputFlag() {
		return "1".equals(this.purchaseTaxationInputFlag);
	}

	/**
	 * 消費税ｺｰﾄﾞを設定する。<BR>
	 * 
	 * @param consumptionTaxCode 消費税コード
	 */
	public void setConsumptionTaxCode(String consumptionTaxCode) {
		this.consumptionTaxCode = consumptionTaxCode;
	}

	/**
	 * 消費税ｺｰﾄﾞを返す。<BR>
	 * 
	 * @return consumptionTaxCode 消費税コード
	 */
	public String getConsumptionTaxCode() {
		return consumptionTaxCode;
	}

	/**
	 * 消費税略称を設定する。<BR>
	 * 
	 * @param consumptionTaxAbbrName 消費税略称
	 */
	public void setConsumptionTaxAbbrName(String consumptionTaxAbbrName) {
		this.consumptionTaxAbbrName = consumptionTaxAbbrName;
	}

	/**
	 * 消費税略称を返す。<BR>
	 * 
	 * @return consumptionTaxAbbrName 消費税略称
	 */
	public String getConsumptionTaxAbbrName() {
		return consumptionTaxAbbrName;
	}

	/**
	 * 消費税区分を設定する。<BR>
	 * 
	 * @param consumptionTaxDivision 消費税区分
	 */
	public void setConsumptionTaxDivision(String consumptionTaxDivision) {
		this.consumptionTaxDivision = consumptionTaxDivision;
	}

	/**
	 * 消費税区分を返す。<BR>
	 * 
	 * @return consumptionTaxDivision 消費税区分
	 */
	public String getconsumptionTaxDivision() {
		return consumptionTaxDivision;
	}

	/**
	 * 取引先入力フラグを設定する。<BR>
	 * 
	 * @param customerInputFlag 取引先入力フラグ 0:入力不可 2:得意先 3:仕入先 4:得意先＆仕入先
	 */
	public void setCustomerInputFlag(String customerInputFlag) {
		this.customerInputFlag = customerInputFlag;
	}

	/**
	 * 取引先入力フラグを返す。<BR>
	 * 
	 * @return customerInputFlag 取引先入力フラグ 0:入力不可 2:得意先 3:仕入先 4:得意先＆仕入先
	 */
	public String getCustomerInputFlag() {
		return customerInputFlag;
	}

	/**
	 * 取引先入力フラグを返す。
	 * 
	 * @return 取引先入力フラグ 0:入力不可 2:得意先 3:仕入先 4:得意先＆仕入先
	 */
	public boolean isIncludeCustomerInputFlag() {
		return !"0".equals(this.customerInputFlag);
	}

	/**
	 * 社員入力フラグを設定する。<BR>
	 * 
	 * @param employeeInputFlag 社員入力フラグ 0:入力不可 1:入力必須
	 */
	public void setEmployeeInputFlag(String employeeInputFlag) {
		this.employeeInputFlag = employeeInputFlag;
	}

	/**
	 * 社員入力フラグを返す。<BR>
	 * 
	 * @return employeeInputFlag 社員入力フラグ true:入力可 false:入力不可
	 */
	public boolean isIncludeEmployeeInputFlag() {
		return "1".equals(this.employeeInputFlag);
	}

	/**
	 * 社員ｺｰﾄﾞを設定する。<BR>
	 * 
	 * @param employeeCode 社員ｺｰﾄﾞ
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	/**
	 * 社員ｺｰﾄﾞを返す。<BR>
	 * 
	 * @return employeeCode 社員ｺｰﾄﾞ
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}

	/**
	 * 社員略称を設定する。<BR>
	 * 
	 * @param employeeAbbrName 社員略称
	 */
	public void setEmployeeAbbrName(String employeeAbbrName) {
		this.employeeAbbrName = employeeAbbrName;
	}

	/**
	 * 社員略称を返す。<BR>
	 * 
	 * @return employeeAbbrName 社員略称
	 */
	public String getEmployeeAbbrName() {
		return employeeAbbrName;
	}

	/**
	 * 管理1入力ﾌﾗｸﾞを設定する。<BR>
	 * 
	 * @param management1InputFlag 管理1入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public void setManagement1InputFlag(String management1InputFlag) {
		this.management1InputFlag = management1InputFlag;
	}

	/**
	 * 管理1入力ﾌﾗｸﾞを返す。<BR>
	 * 
	 * @return management1InputFlag 管理1入力ﾌﾗｸﾞ true:入力可 false:入力不可
	 */
	public boolean isIncludeManagement1InputFlag() {
		return "1".equals(this.management1InputFlag);
	}

	/**
	 * 管理2入力ﾌﾗｸﾞを設定する。<BR>
	 * 
	 * @param management2InputFlag 管理2入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public void setManagement2InputFlag(String management2InputFlag) {
		this.management2InputFlag = management2InputFlag;
	}

	/**
	 * 管理2入力ﾌﾗｸﾞを返す。<BR>
	 * 
	 * @return management2InputFlag 管理1入力ﾌﾗｸﾞ true:入力可 false:入力不可
	 */
	public boolean isIncludeManagement2InputFlag() {
		return "1".equals(this.management2InputFlag);
	}

	/**
	 * 管理3入力ﾌﾗｸﾞを設定する。<BR>
	 * 
	 * @param management3InputFlag 管理3入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public void setManagement3InputFlag(String management3InputFlag) {
		this.management3InputFlag = management3InputFlag;
	}

	/**
	 * 管理3入力ﾌﾗｸﾞを返す。<BR>
	 * 
	 * @return management3InputFlag 管理3入力ﾌﾗｸﾞ true:入力可 false:入力不可
	 */
	public boolean isIncludeManagement3InputFlag() {
		return "1".equals(this.management3InputFlag);
	}

	/**
	 * 管理4入力ﾌﾗｸﾞを設定する。<BR>
	 * 
	 * @param management4InputFlag 管理4入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public void setManagement4InputFlag(String management4InputFlag) {
		this.management4InputFlag = management4InputFlag;
	}

	/**
	 * 管理4入力ﾌﾗｸﾞを返す。<BR>
	 * 
	 * @return management4InputFlag 管理4入力ﾌﾗｸﾞ true:入力可 false:入力不可
	 */
	public boolean isIncludeManagement4InputFlag() {
		return "1".equals(this.management4InputFlag);
	}

	/**
	 * 管理5入力ﾌﾗｸﾞを設定する。<BR>
	 * 
	 * @param management5InputFlag 管理5入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public void setManagement5InputFlag(String management5InputFlag) {
		this.management5InputFlag = management5InputFlag;
	}

	/**
	 * 管理5入力ﾌﾗｸﾞを返す。<BR>
	 * 
	 * @return management5InputFlag 管理5入力ﾌﾗｸﾞ true:入力可 false:入力不可
	 */
	public boolean isIncludeManagement5InputFlag() {
		return "1".equals(this.management5InputFlag);
	}

	/**
	 * 管理6入力ﾌﾗｸﾞを設定する。<BR>
	 * 
	 * @param management6InputFlag 管理6入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public void setManagement6InputFlag(String management6InputFlag) {
		this.management6InputFlag = management6InputFlag;
	}

	/**
	 * 管理6入力ﾌﾗｸﾞを返す。<BR>
	 * 
	 * @return management6InputFlag 管理6入力ﾌﾗｸﾞ true:入力可 false:入力不可
	 */
	public boolean isIncludeManagement6InputFlag() {
		return "1".equals(this.management6InputFlag);
	}

	/**
	 * 非会計1入力フラグを設定する。<BR>
	 * 
	 * @param nonAccountingDetail1Flag 非会計1入力フラグ 0:入力不可 1:入力可
	 */
	public void setNonAccountingDetail1Flag(String nonAccountingDetail1Flag) {
		this.nonAccountingDetail1Flag = nonAccountingDetail1Flag;
	}

	/**
	 * 非会計1入力フラグを返す。<BR>
	 * 
	 * @return management6InputFlag 非会計1入力フラグﾞ true:入力可 false:入力不可
	 */
	public boolean isIncludeNonAccountingDetail1Flag() {
		return "1".equals(this.nonAccountingDetail1Flag);
	}

	/**
	 * 非会計2入力フラグを設定する。<BR>
	 * 
	 * @param nonAccountingDetail2Flag 非会計2入力フラグ 0:入力不可 1:入力可
	 */
	public void setNonAccountingDetail2Flag(String nonAccountingDetail2Flag) {
		this.nonAccountingDetail2Flag = nonAccountingDetail2Flag;
	}

	/**
	 * 非会計2入力フラグを返す。<BR>
	 * 
	 * @return nonAccountingDetail2Flag 非会計2入力フラグﾞ true:入力可 false:入力不可
	 */
	public boolean isIncludeNonAccountingDetail2Flag() {
		return "1".equals(this.nonAccountingDetail2Flag);
	}

	/**
	 * 非会計3入力フラグを設定する。<BR>
	 * 
	 * @param nonAccountingDetail3Flag 非会計3入力フラグ 0:入力不可 1:入力可
	 */
	public void setNonAccountingDetail3Flag(String nonAccountingDetail3Flag) {
		this.nonAccountingDetail3Flag = nonAccountingDetail3Flag;
	}

	/**
	 * 非会計3入力フラグを返す。<BR>
	 * 
	 * @return nonAccountingDetail3Flag 非会計3入力フラグﾞ true:入力可 false:入力不可
	 */
	public boolean isIncludeNonAccountingDetail3Flag() {
		return "1".equals(this.nonAccountingDetail3Flag);
	}

	/**
	 * 発生日入力フラグを設定する。<BR>
	 * 
	 * @param accrualDateInputFlag 発生日入力フラグ 0:入力不可 1:入力可
	 */
	public void setAccrualDateInputFlag(String accrualDateInputFlag) {
		this.accrualDateInputFlag = accrualDateInputFlag;
	}

	/**
	 * 発生日入力フラグを返す。<BR>
	 * 
	 * @return accrualDateInputFlag 発生日入力フラグﾞ true:入力可 false:入力不可
	 */
	public boolean isIncludeAccrualDateInputFlag() {
		return "1".equals(this.accrualDateInputFlag);
	}

	/**
	 * 多通貨フラグを設定する。<BR>
	 * 
	 * @param multipleCurrencyInputFlag 多通貨フラグ 0:入力不可 1:入力可
	 */
	public void setMultipleCurrencyInputFlag(String multipleCurrencyInputFlag) {
		this.multipleCurrencyInputFlag = multipleCurrencyInputFlag;
	}

	/**
	 * 多通貨フラグを返す。<BR>
	 * 
	 * @return multipleCurrencyInputFlag 多通貨フラグ true:入力可 false:入力不可
	 */
	public boolean isIncludeMultipleCurrencyInputFlag() {
		return "1".equals(this.multipleCurrencyInputFlag);
	}

	/**
	 * GL科目制御区分を設定する。<BR>
	 * 
	 * @param kmkCntGl GL科目制御区分
	 */
	public void setKmkCntGl(String kmkCntGl) {
		this.kmkCntGl = kmkCntGl;
	}

	/**
	 * GL科目制御区分を返す。<BR>
	 * 
	 * @return kmkCntGl GL科目制御区分
	 */
	public String getKmkCntGl() {
		return kmkCntGl;
	}

	/**
	 * AP科目制御区分を設定する。<BR>
	 * 
	 * @param kmkCntAp AP科目制御区分
	 */
	public void setKmkCntAp(String kmkCntAp) {
		this.kmkCntAp = kmkCntAp;
	}

	/**
	 * AP科目制御区分を返す。<BR>
	 * 
	 * @return kmkCntAp AP科目制御区分
	 */
	public String getKmkCntAp() {
		return kmkCntAp;
	}

	/**
	 * AR科目制御区分を設定する。<BR>
	 * 
	 * @param kmkCntAr AR科目制御区分
	 */
	public void setKmkCntAr(String kmkCntAr) {
		this.kmkCntAr = kmkCntAr;
	}

	/**
	 * AR科目制御区分を返す。<BR>
	 * 
	 * @return kmkCntAr AR科目制御区分
	 */
	public String getKmkCntAr() {
		return kmkCntAr;
	}

	/**
	 * 科目体系コードを返す。<BR>
	 * 
	 * @param itemSystemCode 科目体系コード
	 */
	public void setItemSystemCode(String itemSystemCode) {
		this.itemSystemCode = itemSystemCode;
	}

	/**
	 * 科目体系コードを設定する。<BR>
	 * 
	 * @return itemSystemCode 科目体系コード
	 */
	public String getItemSystemCode() {
		return itemSystemCode;
	}

	/**
	 * 値を初期値に戻す
	 */
	public void clear() {
		isExist = false;
		itemCode = "";
		itemAbbrName = "";
		subItemCode = "";
		subItemAbbrName = "";
		breakDownItemCode = "";
		breakDownItemAbbrName = "";
		subItemFlag = "";
		breakDownItemFlag = "";
		salesTaxInputFlag = "";
		purchaseTaxationInputFlag = "";
		consumptionTaxCode = "";
		consumptionTaxAbbrName = "";
		consumptionTaxDivision = "";
		customerInputFlag = "0";
		employeeInputFlag = "";
		employeeCode = "";
		employeeAbbrName = "";
		management1InputFlag = "";
		management2InputFlag = "";
		management3InputFlag = "";
		management4InputFlag = "";
		management5InputFlag = "";
		management6InputFlag = "";
		nonAccountingDetail1Flag = "";
		nonAccountingDetail2Flag = "";
		nonAccountingDetail3Flag = "";
		accrualDateInputFlag = "";
		multipleCurrencyInputFlag = "";
		kmkCntGl = "";
		kmkCntAp = "";
		kmkCntAr = "";
		itemSystemCode = "";
	}

}
