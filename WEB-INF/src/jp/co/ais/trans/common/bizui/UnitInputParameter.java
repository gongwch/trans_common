package jp.co.ais.trans.common.bizui;

import java.util.*;

/**
 * 各コンポーネントのInputParameterに条件をセット
 * 
 */
public class UnitInputParameter extends AccountItemInputParameter {
	/** AccountItemInputParameterクラス */
	private List<AccountItemInputParameter> paramList;

	/**
	 * @param paramList
	 */
	public UnitInputParameter(List<AccountItemInputParameter> paramList) {
		this.paramList = Collections.synchronizedList(new LinkedList<AccountItemInputParameter>());
		this.paramList = paramList;
	}

	/**
	 * 会社コードを設定する。<BR>
	 * 
	 * @param companyCode 会社コード
	 * 
	 */
	public void setCompanyCode(String companyCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setCompanyCode(companyCode);
		}
	}

	/**
	 * 科目コードを設定する。<BR>
	 * 
	 * @param itemCode 科目コード
	 * 
	 */
	public void setItemCode(String itemCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setItemCode(itemCode);
		}
	}

	/**
	 * 科目略称を設定する。<BR>
	 * 
	 * @param itemAbbrName 科目略称
	 * 
	 */
	public void setItemAbbrName(String itemAbbrName) {
		for (AccountItemInputParameter param : paramList) {
			param.setItemAbbrName(itemAbbrName);
		}
	}

	/**
	 * 科目検索名称を設定する。<BR>
	 * 
	 * @param itemNameForSearch 科目検索名称
	 * 
	 */
	public void setItemNameForSearch(String itemNameForSearch) {
		for (AccountItemInputParameter param : paramList) {
			param.setItemNameForSearch(itemNameForSearch);
		}
	}

	/**
	 * 補助科目コードを設定する。<BR>
	 * 
	 * @param subItemCode 補助科目コード
	 * 
	 */
	public void setSubItemCode(String subItemCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setSubItemCode(subItemCode);
		}
	}

	/**
	 * 補助科目略称を設定する。<BR>
	 * 
	 * @param subItemAbbrName 補助科目略称
	 * 
	 */
	public void setSubItemAbbrName(String subItemAbbrName) {
		for (AccountItemInputParameter param : paramList) {
			param.setSubItemAbbrName(subItemAbbrName);
		}
	}

	/**
	 * 補助科目検索名称を設定する。<BR>
	 * 
	 * @param subItemNameForSearch 補助科目検索名称
	 * 
	 */
	public void setSubItemNameForSearch(String subItemNameForSearch) {
		for (AccountItemInputParameter param : paramList) {
			param.setSubItemNameForSearch(subItemNameForSearch);
		}
	}

	/**
	 * 内訳科目コードを設定する。<BR>
	 * 
	 * @param breakDownItemCode 内訳科目コード
	 * 
	 */
	public void setBreakDownItemCode(String breakDownItemCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setBreakDownItemCode(breakDownItemCode);
		}
	}

	/**
	 * 内訳科目略称を設定する。<BR>
	 * 
	 * @param breakDownItemAbbrName 内訳科目略称
	 * 
	 */
	public void setBreakDownItemAbbrName(String breakDownItemAbbrName) {
		for (AccountItemInputParameter param : paramList) {
			param.setBreakDownItemAbbrName(breakDownItemAbbrName);
		}
	}

	/**
	 * 内訳科目検索名称を設定する。<BR>
	 * 
	 * @param breakDownItemNameForSearch 内訳科目検索名称
	 * 
	 */
	public void setBreakDownItemNameForSearch(String breakDownItemNameForSearch) {
		for (AccountItemInputParameter param : paramList) {
			param.setBreakDownItemNameForSearch(breakDownItemNameForSearch);
		}
	}

	/**
	 * 補助区分を設定する。<BR>
	 * 
	 * @param subItemDivision 補助区分 0:なし 1:あり
	 * 
	 */
	public void setSubItemDivision(String subItemDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setSubItemDivision(subItemDivision);
		}
	}

	/**
	 * 内訳区分を設定する。<BR>
	 * 
	 * @param breakDownItemDivision 内訳区分 0:なし 1:あり
	 * 
	 */
	public void setBreakDownItemDivision(String breakDownItemDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setBreakDownItemDivision(breakDownItemDivision);
		}
	}

	/**
	 * ＧＬ科目制御区分を設定する。<BR>
	 * 
	 * @param glItemCtrlDivision ＧＬ科目制御区分 00:通常 01:前期繰越利益 04:資金科目 05:売上科目 06:為替換算差損益 07:仮勘定 08:為替差損
	 *            09:為替差益
	 * 
	 */
	public void setGlItemCtrlDivision(String glItemCtrlDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setGlItemCtrlDivision(glItemCtrlDivision);
		}
	}

	/**
	 * 伝票日付を設定する。
	 * 
	 * @param slipDate 伝票日付
	 */
	public void setSlipDate(String slipDate) {
		for (AccountItemInputParameter param : paramList) {
			param.setSlipDate(slipDate);
		}
	}

	/**
	 * BS勘定消込区分を設定する。
	 * 
	 * @param bsAccountErasingDivision BS勘定消込区分 0:しない 1:する
	 */
	public void setBsAccountErasingDivision(String bsAccountErasingDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setBsAccountErasingDivision(bsAccountErasingDivision);
		}
	}

	/**
	 * 集計区分を設定する。
	 */
	public void setSummaryDivision(String summaryDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setSummaryDivision(summaryDivision);
		}
	}

	/**
	 * 振替伝票入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param transferSlipInputFlag 振替伝票入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public void setTransferSlipInputFlag(String transferSlipInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setTransferSlipInputFlag(transferSlipInputFlag);
		}
	}

	/**
	 * 経費精算伝票入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param expenseInputFlag 経費精算伝票入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public void setExpenseInputFlag(String expenseInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setExpenseInputFlag(expenseInputFlag);
		}
	}

	/**
	 * 債務計上入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param saimuFlg 債務計上入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public void setSaimuFlg(String saimuFlg) {
		for (AccountItemInputParameter param : paramList) {
			param.setSaimuFlg(saimuFlg);
		}
	}

	/**
	 * 債権計上入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param saikenFlg 債権計上入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public void setSaikenFlg(String saikenFlg) {
		for (AccountItemInputParameter param : paramList) {
			param.setSaikenFlg(saikenFlg);
		}
	}

	/**
	 * 債権消込伝票入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param accountsRecivableErasingSlipInputFlag 債権消込伝票入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public void setAccountsRecivableErasingSlipInputFlag(String accountsRecivableErasingSlipInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setAccountsRecivableErasingSlipInputFlag(accountsRecivableErasingSlipInputFlag);
		}
	}

	/**
	 * 資産計上伝票入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param assetsAppropriatingSlipInputFlag 資産計上伝票入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public void setAssetsAppropriatingSlipInputFlag(String assetsAppropriatingSlipInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setAssetsAppropriatingSlipInputFlag(assetsAppropriatingSlipInputFlag);
		}
	}

	/**
	 * 支払依頼伝票入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param paymentRequestSlipInputFlag 支払依頼伝票入力ﾌﾗｸﾞ 0:入力不可 1:入力可
	 */
	public void setPaymentRequestSlipInputFlag(String paymentRequestSlipInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setPaymentRequestSlipInputFlag(paymentRequestSlipInputFlag);
		}
	}

	/**
	 * 部門コードを設定する。
	 * 
	 * @param departmentCode 部門コード
	 */
	public void setDepartmentCode(String departmentCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setDepartmentCode(departmentCode);
		}
	}

	/**
	 * 評価替対象フラグを設定する。
	 * 
	 * @param revaluationObjectFlag 評価替対象フラグ 0:しない 1:洗替法 2:切離法
	 */
	public void setRevaluationObjectFlag(String revaluationObjectFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setRevaluationObjectFlag(revaluationObjectFlag);
		}
	}

	/**
	 * 入金伝票入力フラグを設定する。
	 * 
	 * @param recivingSlipInputFlag 入金伝票入力フラグ 0:入力不可 1:入力可
	 */
	public void setRecivingSlipInputFlag(String recivingSlipInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setRecivingSlipInputFlag(recivingSlipInputFlag);
		}
	}

	/**
	 * 出金伝票入力フラグを設定する。
	 * 
	 * @param drawingSlipInputFlag 出金伝票入力フラグ 0:入力不可 1:入力可
	 */
	public void setDrawingSlipInputFlag(String drawingSlipInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setDrawingSlipInputFlag(drawingSlipInputFlag);
		}
	}

	/**
	 * AR制御区分を設定する。
	 * 
	 * @param arItemCtrlDivision AR制御区分 00:通常 01:債権管理科目 02:債権回収仮勘定
	 */
	public void setArItemCtrlDivision(String arItemCtrlDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setArItemCtrlDivision(arItemCtrlDivision);
		}
	}
	
	/**
	 * AR制御区分を設定する。(消込用)
	 * 
	 * @param unArItemCtrlDivision AR制御区分 00:通常 01:債権管理科目 02:債権回収仮勘定
	 */
	public void setUnArItemCtrlDivision(String unArItemCtrlDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setUnArItemCtrlDivision(unArItemCtrlDivision);
		}
	}
	
	/**
	 * AP制御区分を設定する。
	 * 
	 * @param apItemCtrlDivision AP制御区分 00:通常 01:債務管理科目 02：従業員仮払科目
	 */
	public void setApItemCtrlDivision(String apItemCtrlDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setApItemCtrlDivision(apItemCtrlDivision);
		}
	}

	/**
	 * 管理1入力ﾌﾗｸﾞを設定する。<BR>
	 * 
	 * @param management1InputFlag 管理1入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public void setManagement1InputFlag(String management1InputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setManagement1InputFlag(management1InputFlag);
		}
	}

	/**
	 * 管理2入力ﾌﾗｸﾞを設定する。<BR>
	 * 
	 * @param management2InputFlag 管理2入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public void setManagement2InputFlag(String management2InputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setManagement2InputFlag(management2InputFlag);
		}
	}

	/**
	 * 管理3入力ﾌﾗｸﾞを設定する。<BR>
	 * 
	 * @param management3InputFlag 管理3入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public void setManagement3InputFlag(String management3InputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setManagement3InputFlag(management3InputFlag);
		}
	}

	/**
	 * 管理4入力ﾌﾗｸﾞを設定する。<BR>
	 * 
	 * @param management4InputFlag 管理4入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public void setManagement4InputFlag(String management4InputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setManagement4InputFlag(management4InputFlag);
		}
	}

	/**
	 * 管理5入力ﾌﾗｸﾞを設定する。<BR>
	 * 
	 * @param management5InputFlag 管理5入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public void setManagement5InputFlag(String management5InputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setManagement5InputFlag(management5InputFlag);
		}
	}

	/**
	 * 管理6入力ﾌﾗｸﾞを設定する。<BR>
	 * 
	 * @param management6InputFlag 管理6入力ﾌﾗｸﾞ 0:入力不可 1:入力必須
	 */
	public void setManagement6InputFlag(String management6InputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setManagement6InputFlag(management6InputFlag);
		}
	}

	/**
	 * 非会計1入力フラグを設定する。<BR>
	 * 
	 * @param  nonAccountingDetail1Flag 非会計1入力フラグ 0:入力不可 1:入力必須
	 */
	public void setNonAccountingDetail1Flag(String nonAccountingDetail1Flag) {
		for (AccountItemInputParameter param : paramList) {
			param.setNonAccountingDetail1Flag(nonAccountingDetail1Flag);
		}
	}

	/**
	 * 非会計2入力フラグを設定する。<BR>
	 * 
	 * @param nonAccountingDetail2Flag 非会計2入力フラグ 0:入力不可 1:入力必須
	 */
	public void setNonAccountingDetail2Flag(String nonAccountingDetail2Flag) {
		for (AccountItemInputParameter param : paramList) {
			param.setNonAccountingDetail2Flag(nonAccountingDetail2Flag);
		}
	}

	/**
	 * 非会計3入力フラグを設定する。<BR>
	 * 
	 * @param nonAccountingDetail3Flag 非会計3入力フラグ 0:入力不可 1:入力必須
	 */
	public void setNonAccountingDetail3Flag(String nonAccountingDetail3Flag) {
		for (AccountItemInputParameter param : paramList) {
			param.setNonAccountingDetail3Flag(nonAccountingDetail3Flag);
		}
	}

	/**
	 * 発生日入力フラグを設定する。<BR>
	 * 
	 * @param accrualDateInputFlag 発生日入力フラグ 0:入力不可 1:入力必須
	 */
	public void setAccrualDateInputFlag(String accrualDateInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setAccrualDateInputFlag(accrualDateInputFlag);
		}
	}

	/**
	 * 多通貨フラグを設定する。<BR>
	 * 
	 * @param multipleCurrencyInputFlag 多通貨フラグ 0:入力不可 1:入力必須
	 */
	public void setMultipleCurrencyInputFlag(String multipleCurrencyInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setMultipleCurrencyInputFlag(multipleCurrencyInputFlag);
		}
	}

	/**
	 * 取引先入力フラグを設定する。<BR>
	 * 
	 * @param customerInputFlag 取引先入力フラグ 0:入力不可 2:得意先 3:仕入先 4:得意先＆仕入先
	 * 
	 */
	public void setCustomerInputFlag(String customerInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setCustomerInputFlag(customerInputFlag);
		}
	}

	/**
	 * 社員入力フラグを設定する。<BR>
	 * 
	 * @param employeeInputFlag 社員入力フラグ 0:入力不可 1:入力必須
	 */
	public void setEmployeeInputFlag(String employeeInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setEmployeeInputFlag(employeeInputFlag);
		}
	}

	/**
	 * 売上課税入力フラグを設定する。<BR>
	 * 
	 * @param salesTaxInputFlag 売上課税入力フラグ 0:入力不可 1:入力可
	 */
	public void setSalesTaxInputFlag(String salesTaxInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setSalesTaxInputFlag(salesTaxInputFlag);
		}
	}

	/**
	 * 仕入課税入力フラグを設定する。<BR>
	 * 
	 * @param purchaseTaxationInputFlag 仕入課税入力フラグ 0:入力不可 1:入力可
	 * 
	 */
	public void setPurchaseTaxationInputFlag(String purchaseTaxationInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setPurchaseTaxationInputFlag(purchaseTaxationInputFlag);
		}
	}

	/**
	 * 消費税ｺｰﾄﾞを設定する。<BR>
	 * 
	 * @param consumptionTaxCode 消費税コード
	 * 
	 */
	public void setConsumptionTaxCode(String consumptionTaxCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setConsumptionTaxCode(consumptionTaxCode);
		}
	}

	/**
	 * 科目種別を設定する。<BR>
	 * 
	 * @param itemType 科目種別 0:貸借科目 1:損益科目 2:利益処分科目 3:製造原価科目 9:非会計科目
	 * 
	 */
	public void setItemType(String itemType) {
		for (AccountItemInputParameter param : paramList) {
			param.setItemType(itemType);
		}
	}

	/**
	 * 貸借区分を設定する。<BR>
	 * 
	 * @param debitAndCreditDivision 貸借区分 0:借方科目 1:貸方科目
	 * 
	 */
	public void setDebitAndCreditDivision(String debitAndCreditDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setDebitAndCreditDivision(debitAndCreditDivision);
		}
	}

	/**
	 * 固定部門ｺｰﾄﾞを設定する。<BR>
	 * 
	 * @param fixedDepartment 固定部門ｺｰﾄﾞ
	 * 
	 */
	public void setFixedDepartment(String fixedDepartment) {
		for (AccountItemInputParameter param : paramList) {
			param.setFixedDepartment(fixedDepartment);
		}
	}

	/**
	 * 請求書伝票入力フラグを設定する。<BR>
	 * 
	 * @param adjustmentSlipInputFlag 請求書伝票入力フラグ 0:入力不可 1:入力可
	 * 
	 */
	public void setAdjustmentSlipInputFlag(String adjustmentSlipInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setAdjustmentSlipInputFlag(adjustmentSlipInputFlag);
		}
	}

	/**
	 * BG科目制御区分を設定する。<BR>
	 * 
	 * @param bgItemCtrlDivision BG科目制御区分 00:通常 11:予算管理対象
	 * 
	 */
	public void setBgItemCtrlDivision(String bgItemCtrlDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setBgItemCtrlDivision(bgItemCtrlDivision);
		}
	}

	/**
	 * 相殺精算制御区分を設定する。<BR>
	 * 
	 * @param counterbalanceAdjustmentCtrlDivision 相殺精算制御区分 0:しない 1:する
	 * 
	 */
	public void setCounterbalanceAdjustmentCtrlDivision(String counterbalanceAdjustmentCtrlDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setCounterbalanceAdjustmentCtrlDivision(counterbalanceAdjustmentCtrlDivision);
		}
	}

	/**
	 * 伝票日付を設定する。(Date型)
	 * 
	 * @param dateSlipDate 伝票日付
	 */
	public void setDateSlipDate(Date dateSlipDate) {
		for (AccountItemInputParameter param : paramList) {
			param.setDateSlipDate(dateSlipDate);
		}
	}

	/**
	 * 社員ｺｰﾄﾞを設定する。<BR>
	 * 
	 * @param employeeCode 社員ｺｰﾄﾞ
	 * 
	 */
	public void setEmployeeCode(String employeeCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setEmployeeCode(employeeCode);
		}
	}

	/**
	 * 終了コード
	 * 
	 * @param endCode 終了コード
	 */
	public void setEndCode(String endCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setEndCode(endCode);
		}
	}

	/**
	 * 開始コード
	 * 
	 * @param beginCode 開始コード
	 */
	public void setBeginCode(String beginCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setBeginCode(beginCode);
		}
	}

}
