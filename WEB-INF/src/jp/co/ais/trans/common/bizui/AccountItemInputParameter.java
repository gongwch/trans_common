package jp.co.ais.trans.common.bizui;

import java.io.*;
import java.util.*;

/**
 * 科目フィールド入力条件
 */
public class AccountItemInputParameter implements Serializable {

	/** 会社コード */
	private String companyCode = "";

	/** 科目コード */
	private String itemCode = "";

	/** 科目略称 */
	private String itemAbbrName = "";

	/** 科目検索名称 */
	private String itemNameForSearch = "";

	/** 補助科目コード */
	private String subItemCode = "";

	/** 補助科目略称 */
	private String subItemAbbrName = "";

	/** 補助科目検索名称 */
	private String subItemNameForSearch = "";

	/** 内訳科目コード */
	private String breakDownItemCode = "";

	/** 内訳科目略称 */
	private String breakDownItemAbbrName = "";

	/** 内訳科目検索名称 */
	private String breakDownItemNameForSearch = "";

	/** 補助区分 */
	private String subItemDivision = "";

	/** 内訳区分 */
	private String breakDownItemDivision = "";

	/** ＧＬ科目制御区分 */
	private String glItemCtrlDivision = "";

	/** AP制御区分 */
	private String apItemCtrlDivision = "";

	/** AR制御区分 */
	private String arItemCtrlDivision = "";

	/** AR制御区分(消込用) */
	private String unArItemCtrlDivision = "";

	/** 伝票日付 */
	private String slipDate = "";

	/** 伝票日付(Date型) */
	private Date dateSlipDate = null;

	/** BS勘定消込区分 */
	private String bsAccountErasingDivision = "";

	/** 集計区分 */
	private String summaryDivision = "";

	/** 振替伝票入力ﾌﾗｸﾞ */
	private String transferSlipInputFlag = "";

	/** 債務計上入力ﾌﾗｸﾞ */
	private String saimuFlg = "";

	/** 債権計上入力ﾌﾗｸﾞ */
	private String saikenFlg = "";

	/** 経費精算伝票入力ﾌﾗｸﾞ */
	private String expenseInputFlag = "";

	/** 債権消込伝票入力ﾌﾗｸﾞ */
	private String accountsRecivableErasingSlipInputFlag = "";

	/** 資産計上伝票入力ﾌﾗｸﾞ */
	private String assetsAppropriatingSlipInputFlag = "";

	/** 支払依頼伝票入力ﾌﾗｸﾞ */
	private String paymentRequestSlipInputFlag = "";

	/** 部門コード */
	private String departmentCode = "";

	/** 評価替対象フラグ */
	private String revaluationObjectFlag = "";

	/** 入金伝票入力フラグ */
	private String recivingSlipInputFlag = "";

	/** 出金伝票入力フラグ */
	private String drawingSlipInputFlag = "";

	/** 取引先入力ﾌﾗｸﾞ */
	private String customerInputFlag = "";

	/** 社員入力ﾌﾗｸﾞ */
	private String employeeInputFlag = "";

	/** 発生日入力フラグ */
	private String accrualDateInputFlag = "";

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

	/** 売上課税入力フラグ */
	private String salesTaxInputFlag = "";

	/** 仕入課税入力フラグ */
	private String purchaseTaxationInputFlag = "";

	/** 多通貨フラグ */
	private String multipleCurrencyInputFlag = "";

	/** 消費税ｺｰﾄﾞ */
	private String consumptionTaxCode = "";

	/** 科目種別 */
	private String itemType = "";

	/** 貸借区分 */
	private String debitAndCreditDivision = "";

	/** 固定部門ｺｰﾄﾞ */
	private String fixedDepartment = "";

	/** 請求書伝票入力フラグ */
	private String adjustmentSlipInputFlag = "";

	/** BG科目制御区分 */
	private String bgItemCtrlDivision = "";

	/** 相殺精算制御区分 */
	private String counterbalanceAdjustmentCtrlDivision = "";

	/** 社員ｺｰﾄﾞ */
	private String employeeCode = "";

	/** 開始コード */
	private String beginCode = "";

	/** 終了コード */
	private String endCode = "";

	/** 科目体系コード */
	private String itemSystemCode = "";

	/** 科目体系使用フラグ */
	private String itemSystemFlg = "";

	/**
	 * 会社コードを設定する。<BR>
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 会社コードを返す。<BR>
	 * 
	 * @return companyCode 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
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
	 * 科目検索名称を設定する。<BR>
	 * 
	 * @param itemNameForSearch 科目検索名称
	 */
	public void setItemNameForSearch(String itemNameForSearch) {
		this.itemNameForSearch = itemNameForSearch;
	}

	/**
	 * 科目検索名称を返す。<BR>
	 * 
	 * @return itemNameForSearch 科目検索名称
	 */
	public String getItemNameForSearch() {
		return itemNameForSearch;
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
	 * 補助科目検索名称を設定する。<BR>
	 * 
	 * @param subItemNameForSearch 補助科目検索名称
	 */
	public void setSubItemNameForSearch(String subItemNameForSearch) {
		this.subItemNameForSearch = subItemNameForSearch;
	}

	/**
	 * 補助科目検索名称を返す。<BR>
	 * 
	 * @return subItemNameForSearch 補助科目検索名称
	 */
	public String getSubItemNameForSearch() {
		return subItemNameForSearch;
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
	 * 内訳科目検索名称を設定する。<BR>
	 * 
	 * @param breakDownItemNameForSearch 内訳科目検索名称
	 */
	public void setBreakDownItemNameForSearch(String breakDownItemNameForSearch) {
		this.breakDownItemNameForSearch = breakDownItemNameForSearch;
	}

	/**
	 * 内訳科目検索名称を返す。<BR>
	 * 
	 * @return breakDownItemNameForSearch 内訳科目検索名称
	 */
	public String getBreakDownItemNameForSearch() {
		return breakDownItemNameForSearch;
	}

	/**
	 * 補助区分を設定する。<BR>
	 * 
	 * @param subItemDivision 補助区分 0:なし 1:あり
	 */
	public void setSubItemDivision(String subItemDivision) {
		this.subItemDivision = subItemDivision;
	}

	/**
	 * 補助区分を返す。<BR>
	 * 
	 * @return subItemDivision 補助区分 0:なし 1:あり
	 */
	public String getSubItemDivision() {
		return subItemDivision;
	}

	/**
	 * 内訳区分を設定する。<BR>
	 * 
	 * @param breakDownItemDivision 内訳区分 0:なし 1:あり
	 */
	public void setBreakDownItemDivision(String breakDownItemDivision) {
		this.breakDownItemDivision = breakDownItemDivision;
	}

	/**
	 * 内訳区分を返す。<BR>
	 * 
	 * @return breakDownItemDivision 内訳区分 0:なし 1:あり
	 */
	public String getBreakDownItemDivision() {
		return breakDownItemDivision;
	}

	/**
	 * ＧＬ科目制御区分を設定する。<BR>
	 * 
	 * @param glItemCtrlDivision ＧＬ科目制御区分 00:通常 01:前期繰越利益 04:資金科目 05:売上科目 06:為替換算差損益 07:仮勘定 08:為替差損 09:為替差益
	 */
	public void setGlItemCtrlDivision(String glItemCtrlDivision) {
		this.glItemCtrlDivision = glItemCtrlDivision;
	}

	/**
	 * ＧＬ科目制御区分を返す。<BR>
	 * 
	 * @return glItemCtrlDivision ＧＬ科目制御区分 00:通常 01:前期繰越利益 04:資金科目 05:売上科目 06:為替換算差損益 07:仮勘定 08:為替差損 09:為替差益
	 */
	public String getGlItemCtrlDivision() {
		return glItemCtrlDivision;
	}

	/**
	 * 伝票日付を設定する。
	 * 
	 * @param slipDate 伝票日付
	 */
	public void setSlipDate(String slipDate) {
		this.slipDate = slipDate;
	}

	/**
	 * 伝票日付を返す。
	 * 
	 * @return slipDate 伝票日付
	 */
	public String getSlipDate() {
		return slipDate;
	}

	/**
	 * BS勘定消込区分を設定する。
	 * 
	 * @param bsAccountErasingDivision BS勘定消込区分 0:しない 1:する
	 */
	public void setBsAccountErasingDivision(String bsAccountErasingDivision) {
		this.bsAccountErasingDivision = bsAccountErasingDivision;
	}

	/**
	 * BS勘定消込区分を返す。
	 * 
	 * @return bsAccountErasingDivision BS勘定消込区分 0:しない 1:する
	 */
	public String getBsAccountErasingDivision() {
		return bsAccountErasingDivision;
	}

	/**
	 * 集計区分を設定する。
	 * 
	 * @param summaryDivision 集計区分 0:入力科目 1:集計科目 2:見出科目
	 */
	public void setSummaryDivision(String summaryDivision) {
		this.summaryDivision = summaryDivision;
	}

	/**
	 * 集計区分を返す。
	 * 
	 * @return sumKbn 集計区分 0:入力科目 1:集計科目 2:見出科目
	 */
	public String getSummaryDivision() {
		return summaryDivision;
	}

	/**
	 * 振替伝票入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param transferSlipInputFlag 振替伝票入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public void setTransferSlipInputFlag(String transferSlipInputFlag) {
		this.transferSlipInputFlag = transferSlipInputFlag;
	}

	/**
	 * 振替伝票入力ﾌﾗｸﾞを返す。
	 * 
	 * @return furikaeFlg 振替伝票入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public String getTransferSlipInputFlag() {
		return transferSlipInputFlag;
	}

	/**
	 * 経費精算伝票入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param expenseInputFlag 経費精算伝票入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public void setExpenseInputFlag(String expenseInputFlag) {
		this.expenseInputFlag = expenseInputFlag;
	}

	/**
	 * 経費精算伝票入力ﾌﾗｸﾞを返す。
	 * 
	 * @return expenseInputFlag 経費精算伝票入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public String getExpenseInputFlag() {
		return expenseInputFlag;
	}

	/**
	 * 債務計上入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param saimuFlg 債務計上入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public void setSaimuFlg(String saimuFlg) {
		this.saimuFlg = saimuFlg;
	}

	/**
	 * 債務計上入力ﾌﾗｸﾞを返す。
	 * 
	 * @return saimuFlg 債務計上入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public String getSaimuFlg() {
		return saimuFlg;
	}

	/**
	 * 債権計上入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param saikenFlg 債権計上入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public void setSaikenFlg(String saikenFlg) {
		this.saikenFlg = saikenFlg;
	}

	/**
	 * 債権計上入力ﾌﾗｸﾞを返す。
	 * 
	 * @return saikenFlg 債権計上入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public String getSaikenFlg() {
		return saikenFlg;
	}

	/**
	 * 債権消込伝票入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param accountsRecivableErasingSlipInputFlag 債権消込伝票入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public void setAccountsRecivableErasingSlipInputFlag(String accountsRecivableErasingSlipInputFlag) {
		this.accountsRecivableErasingSlipInputFlag = accountsRecivableErasingSlipInputFlag;
	}

	/**
	 * 債権消込伝票入力ﾌﾗｸﾞを返す。
	 * 
	 * @return saikesiFlg 債権消込伝票入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public String getAccountsRecivableErasingSlipInputFlag() {
		return accountsRecivableErasingSlipInputFlag;
	}

	/**
	 * 資産計上伝票入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param assetsAppropriatingSlipInputFlag 資産計上伝票入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public void setAssetsAppropriatingSlipInputFlag(String assetsAppropriatingSlipInputFlag) {
		this.assetsAppropriatingSlipInputFlag = assetsAppropriatingSlipInputFlag;
	}

	/**
	 * 資産計上伝票入力ﾌﾗｸﾞを返す。
	 * 
	 * @return assetsAppropriatingSlipInputFlag 資産計上伝票入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public String getAssetsAppropriatingSlipInputFlag() {
		return assetsAppropriatingSlipInputFlag;
	}

	/**
	 * 支払依頼伝票入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param paymentRequestSlipInputFlag 支払依頼伝票入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public void setPaymentRequestSlipInputFlag(String paymentRequestSlipInputFlag) {
		this.paymentRequestSlipInputFlag = paymentRequestSlipInputFlag;
	}

	/**
	 * 支払依頼伝票入力ﾌﾗｸﾞを返す。
	 * 
	 * @return paymentRequestSlipInputFlag 支払依頼伝票入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public String getPaymentRequestSlipInputFlag() {
		return paymentRequestSlipInputFlag;
	}

	/**
	 * 部門コードを設定する。
	 * 
	 * @param departmentCode 部門コード
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * 部門コードを返す。
	 * 
	 * @return bmnCode 部門コード
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * 評価替対象フラグを設定する。
	 * 
	 * @param revaluationObjectFlag 評価替対象フラグ 0:しない 1:洗替法 2:切離法
	 */
	public void setRevaluationObjectFlag(String revaluationObjectFlag) {
		this.revaluationObjectFlag = revaluationObjectFlag;
	}

	/**
	 * 評価替対象フラグを返す。
	 * 
	 * @return revaluationObjectFlag 評価替対象フラグ 0:しない 1:洗替法 2:切離法
	 */
	public String getRevaluationObjectFlag() {
		return revaluationObjectFlag;
	}

	/**
	 * 入金伝票入力フラグを設定する。
	 * 
	 * @param recivingSlipInputFlag 入金伝票入力フラグ 0:入力不可 1:入力可
	 */
	public void setRecivingSlipInputFlag(String recivingSlipInputFlag) {
		this.recivingSlipInputFlag = recivingSlipInputFlag;
	}

	/**
	 * 入金伝票入力フラグを返す。
	 * 
	 * @return recivingSlipInputFlag 入金伝票入力フラグ 0:入力不可 1:入力可
	 */
	public String getRecivingSlipInputFlag() {
		return recivingSlipInputFlag;
	}

	/**
	 * 出金伝票入力フラグを設定する。
	 * 
	 * @param drawingSlipInputFlag 出金伝票入力フラグ 0:入力不可 1:入力可
	 */
	public void setDrawingSlipInputFlag(String drawingSlipInputFlag) {
		this.drawingSlipInputFlag = drawingSlipInputFlag;
	}

	/**
	 * 出金伝票入力フラグを返す。
	 * 
	 * @return drawingSlipInputFlag 出金伝票入力フラグ 0:入力不可 1:入力可
	 */
	public String getDrawingSlipInputFlag() {
		return drawingSlipInputFlag;
	}

	/**
	 * AR制御区分を設定する。
	 * 
	 * @param arItemCtrlDivision AR制御区分 00:通常 01:債権管理科目 02:債権回収仮勘定
	 */
	public void setArItemCtrlDivision(String arItemCtrlDivision) {
		this.arItemCtrlDivision = arItemCtrlDivision;
	}

	/**
	 * AR制御区分を返す
	 * 
	 * @return arItemCtrlDivision AR制御区分 00:通常 01:債権管理科目 02:債権回収仮勘定
	 */
	public String getArItemCtrlDivision() {
		return arItemCtrlDivision;
	}

	/**
	 * AR制御区分を設定する。(消込用)
	 * 
	 * @param unArItemCtrlDivision AR制御区分 00:通常 01:債権管理科目 02:債権回収仮勘定
	 */
	public void setUnArItemCtrlDivision(String unArItemCtrlDivision) {
		this.unArItemCtrlDivision = unArItemCtrlDivision;
	}

	/**
	 * AR制御区分を返す(消込用)
	 * 
	 * @return unArItemCtrlDivision AR制御区分 00:通常 01:債権管理科目 02:債権回収仮勘定
	 */
	public String getUnArItemCtrlDivision() {
		return unArItemCtrlDivision;
	}

	/**
	 * AP制御区分を設定する。
	 * 
	 * @param apItemCtrlDivision AP制御区分 00:通常 01:債務管理科目 02：従業員仮払科目
	 */
	public void setApItemCtrlDivision(String apItemCtrlDivision) {
		this.apItemCtrlDivision = apItemCtrlDivision;
	}

	/**
	 * AP制御区分を返す
	 * 
	 * @return apItemCtrlDivision AP制御区分 00:通常 01:債務管理科目 02：従業員仮払科目
	 */
	public String getApItemCtrlDivision() {
		return apItemCtrlDivision;
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
	 * @return management1InputFlag 管理1入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public String getManagement1InputFlag() {
		return this.management1InputFlag;
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
	 * @return management2InputFlag 管理2入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public String getManagement2InputFlag() {
		return this.management2InputFlag;
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
	 * @return management3InputFlag 管理3入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public String getManagement3InputFlag() {
		return this.management3InputFlag;
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
	 * @return management4InputFlag 管理4入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public String getManagement4InputFlag() {
		return this.management4InputFlag;
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
	 * @return management5InputFlag 管理5入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public String getManagement5InputFlag() {
		return this.management5InputFlag;
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
	 * @return management6InputFlag 管理6入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public String getManagement6InputFlag() {
		return this.management6InputFlag;
	}

	/**
	 * 非会計1入力フラグを設定する。<BR>
	 * 
	 * @param nonAccountingDetail1Flag 非会計1入力フラグ 0:入力不可 1:入力必須
	 */
	public void setNonAccountingDetail1Flag(String nonAccountingDetail1Flag) {
		this.nonAccountingDetail1Flag = nonAccountingDetail1Flag;
	}

	/**
	 * 非会計1入力フラグを返す。<BR>
	 * 
	 * @return management6InputFlag 非会計1入力フラグﾞ 0:入力不可 1:入力必須
	 */
	public String getNonAccountingDetail1Flag() {
		return this.nonAccountingDetail1Flag;
	}

	/**
	 * 非会計2入力フラグを設定する。<BR>
	 * 
	 * @param nonAccountingDetail2Flag 非会計2入力フラグ 0:入力不可 1:入力必須
	 */
	public void setNonAccountingDetail2Flag(String nonAccountingDetail2Flag) {
		this.nonAccountingDetail2Flag = nonAccountingDetail2Flag;
	}

	/**
	 * 非会計2入力フラグを返す。<BR>
	 * 
	 * @return nonAccountingDetail2Flag 非会計2入力フラグﾞ 0:入力不可 1:入力必須
	 */
	public String getNonAccountingDetail2Flag() {
		return this.nonAccountingDetail2Flag;
	}

	/**
	 * 非会計3入力フラグを設定する。<BR>
	 * 
	 * @param nonAccountingDetail3Flag 非会計3入力フラグ 0:入力不可 1:入力必須
	 */
	public void setNonAccountingDetail3Flag(String nonAccountingDetail3Flag) {
		this.nonAccountingDetail3Flag = nonAccountingDetail3Flag;
	}

	/**
	 * 非会計3入力フラグを返す。<BR>
	 * 
	 * @return nonAccountingDetail3Flag 非会計3入力フラグﾞ 0:入力不可 1:入力必須
	 */
	public String getNonAccountingDetail3Flag() {
		return this.nonAccountingDetail3Flag;
	}

	/**
	 * 発生日入力フラグを設定する。<BR>
	 * 
	 * @param accrualDateInputFlag 発生日入力フラグ 0:入力不可 1:入力必須
	 */
	public void setAccrualDateInputFlag(String accrualDateInputFlag) {
		this.accrualDateInputFlag = accrualDateInputFlag;
	}

	/**
	 * 発生日入力フラグを返す。<BR>
	 * 
	 * @return accrualDateInputFlag 発生日入力フラグﾞ 0:入力不可 1:入力必須
	 */
	public String getAccrualDateInputFlag() {
		return this.accrualDateInputFlag;
	}

	/**
	 * 多通貨フラグを設定する。<BR>
	 * 
	 * @param multipleCurrencyInputFlag 多通貨フラグ 0:入力不可 1:入力必須
	 */
	public void setMultipleCurrencyInputFlag(String multipleCurrencyInputFlag) {
		this.multipleCurrencyInputFlag = multipleCurrencyInputFlag;
	}

	/**
	 * 多通貨フラグを返す。<BR>
	 * 
	 * @return multipleCurrencyInputFlag 多通貨フラグ 0:入力不可 1:入力必須
	 */
	public String getMultipleCurrencyInputFlag() {
		return this.multipleCurrencyInputFlag;
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
	 * @return employeeInputFlag 社員入力フラグ 0:入力不可 1:入力必須
	 */
	public String getEmployeeInputFlag() {
		return this.employeeInputFlag;
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
	 * @return salesTaxInputFlag 売上課税入力フラグ 0:入力不可 1:入力可
	 */
	public String getSalesTaxInputFlag() {
		return salesTaxInputFlag;
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
	 * @return purchaseTaxationInputFlag 仕入課税入力フラグ 0:入力不可 1:入力可
	 */
	public String getPurchaseTaxationInputFlag() {
		return this.purchaseTaxationInputFlag;
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
	 * 科目種別を設定する。<BR>
	 * 
	 * @param itemType 科目種別 0:貸借科目 1:損益科目 2:利益処分科目 3:製造原価科目 9:非会計科目
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**
	 * 科目種別を返す。<BR>
	 * 
	 * @return itemType 科目種別 0:貸借科目 1:損益科目 2:利益処分科目 3:製造原価科目 9:非会計科目
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * 貸借区分を設定する。<BR>
	 * 
	 * @param debitAndCreditDivision 貸借区分 0:借方科目 1:貸方科目
	 */
	public void setDebitAndCreditDivision(String debitAndCreditDivision) {
		this.debitAndCreditDivision = debitAndCreditDivision;
	}

	/**
	 * 貸借区分を返す。<BR>
	 * 
	 * @return debitAndCreditDivision 貸借区分 0:借方科目 1:貸方科目
	 */
	public String getDebitAndCreditDivision() {
		return debitAndCreditDivision;
	}

	/**
	 * 固定部門ｺｰﾄﾞを設定する。<BR>
	 * 
	 * @param fixedDepartment 固定部門ｺｰﾄﾞ
	 */
	public void setFixedDepartment(String fixedDepartment) {
		this.fixedDepartment = fixedDepartment;
	}

	/**
	 * 固定部門ｺｰﾄﾞを返す。<BR>
	 * 
	 * @return fixedDepartment 固定部門ｺｰﾄﾞ
	 */
	public String getFixedDepartment() {
		return fixedDepartment;
	}

	/**
	 * 請求書伝票入力フラグを設定する。<BR>
	 * 
	 * @param adjustmentSlipInputFlag 請求書伝票入力フラグ 0:入力不可 1:入力可
	 */
	public void setAdjustmentSlipInputFlag(String adjustmentSlipInputFlag) {
		this.adjustmentSlipInputFlag = adjustmentSlipInputFlag;
	}

	/**
	 * 請求書伝票入力フラグを返す。<BR>
	 * 
	 * @return adjustmentSlipInputFlag 請求書伝票入力フラグ 0:入力不可 1:入力可
	 */
	public String getAdjustmentSlipInputFlag() {
		return adjustmentSlipInputFlag;
	}

	/**
	 * BG科目制御区分を設定する。<BR>
	 * 
	 * @param bgItemCtrlDivision BG科目制御区分 00:通常 11:予算管理対象
	 */
	public void setBgItemCtrlDivision(String bgItemCtrlDivision) {
		this.bgItemCtrlDivision = bgItemCtrlDivision;
	}

	/**
	 * BG科目制御区分を返す。<BR>
	 * 
	 * @return adjustmentSlipInputFlag BG科目制御区分 00:通常 11:予算管理対象
	 */
	public String getBgItemCtrlDivision() {
		return bgItemCtrlDivision;
	}

	/**
	 * 相殺精算制御区分を設定する。<BR>
	 * 
	 * @param counterbalanceAdjustmentCtrlDivision 相殺精算制御区分 0:しない 1:する
	 */
	public void setCounterbalanceAdjustmentCtrlDivision(String counterbalanceAdjustmentCtrlDivision) {
		this.counterbalanceAdjustmentCtrlDivision = counterbalanceAdjustmentCtrlDivision;
	}

	/**
	 * 相殺精算制御区分を返す。<BR>
	 * 
	 * @return counterbalanceAdjustmentCtrlDivision 相殺精算制御区分 0:しない 1:する
	 */
	public String getCounterbalanceAdjustmentCtrlDivision() {
		return counterbalanceAdjustmentCtrlDivision;
	}

	/**
	 * 伝票日付を設定する。(Date型)
	 * 
	 * @param dateSlipDate 伝票日付
	 */
	public void setDateSlipDate(Date dateSlipDate) {
		this.dateSlipDate = dateSlipDate;
	}

	/**
	 * 伝票日付を返す。(Date型)
	 * 
	 * @return dateSlipDate 伝票日付
	 */
	public Date getDateSlipDate() {
		return dateSlipDate;
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
	 * 終了コード
	 * 
	 * @return 終了コード
	 */
	public String getEndCode() {
		return endCode;
	}

	/**
	 * 終了コード
	 * 
	 * @param endCode 終了コード
	 */
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	/**
	 * 開始コード
	 * 
	 * @return 開始コード
	 */
	public String getBeginCode() {
		return beginCode;
	}

	/**
	 * 開始コード
	 * 
	 * @param beginCode 開始コード
	 */
	public void setBeginCode(String beginCode) {
		this.beginCode = beginCode;
	}

	/**
	 * 科目体系コードを設定する
	 * 
	 * @return 科目体系コード
	 */
	public String getItemSystemCode() {
		return itemSystemCode;
	}

	/**
	 * 科目体系コードを取得する
	 * 
	 * @param itemSystemCode 科目体系コード
	 */
	public void setItemSystemCode(String itemSystemCode) {
		this.itemSystemCode = itemSystemCode;
	}

	/**
	 * 科目体系フラグを設定する
	 * 
	 * @return itemSystemFlg
	 */
	public String getItemSystemFlg() {
		return itemSystemFlg;
	}

	/**
	 * 科目体系フラグを取得する
	 * 
	 * @param itemSystemFlg
	 */
	public void setItemSystemFlg(String itemSystemFlg) {
		this.itemSystemFlg = itemSystemFlg;
	}

}
