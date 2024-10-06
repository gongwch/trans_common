SELECT UKM_MST.UKM_CODE,
       UKM_MST.UKM_NAME_S,
       UKM_MST.UKM_NAME_K
FROM UKM_MST
WHERE KAI_CODE = /*param.companyCode*/
  AND UKM_MST.KMK_CODE = /*param.itemCode*/
  AND UKM_MST.HKM_CODE = /*param.subItemCode*/
/*IF param.breakDownItemCode != "" */AND UKM_CODE  /*$param.breakDownItemCode*/  /*END*/ -- LIKE
/*IF param.breakDownItemAbbrName != "" */AND UKM_NAME_S  /*$param.breakDownItemAbbrName*/  /*END*/ -- LIKE
/*IF param.breakDownItemNameForSearch != "" */AND UKM_NAME_K  /*$param.breakDownItemNameForSearch*/  /*END*/ -- LIKE
/*IF param.dateSlipDate != null && (!"".equals(param.dateSlipDate))*/ AND STR_DATE <= /*param.dateSlipDate*/ /*END*/
/*IF param.dateSlipDate != null && (!"".equals(param.dateSlipDate))*/ AND END_DATE >= /*param.dateSlipDate*/ /*END*/
/*IF param.transferSlipInputFlag != null && (!"".equals(param.transferSlipInputFlag))*/AND GL_FLG_3 = STR_TO_INT(/*param.transferSlipInputFlag*/)  /*END*/
/*IF param.recivingSlipInputFlag != null && (!"".equals(param.recivingSlipInputFlag))*/ AND GL_FLG_1 = STR_TO_INT(/*param.recivingSlipInputFlag*/) /*END*/
/*IF param.drawingSlipInputFlag != null && (!"".equals(param.drawingSlipInputFlag))*/ AND GL_FLG_2 = STR_TO_INT(/*param.drawingSlipInputFlag*/) /*END*/
/*IF param.expenseInputFlag != null && (!"".equals(param.expenseInputFlag))*/AND AP_FLG_1 = STR_TO_INT(/*param.expenseInputFlag*/)  /*END*/
/*IF param.saimuFlg != null && (!"".equals(param.saimuFlg))*/AND AP_FLG_2 = STR_TO_INT(/*param.saimuFlg*/)  /*END*/
/*IF param.saikenFlg != null && (!"".equals(param.saikenFlg))*/AND AR_FLG_1 = STR_TO_INT(/*param.saikenFlg*/)  /*END*/
/*IF param.accountsRecivableErasingSlipInputFlag != null && (!"".equals(param.accountsRecivableErasingSlipInputFlag))*/AND AR_FLG_2 = STR_TO_INT(/*param.accountsRecivableErasingSlipInputFlag*/)  /*END*/
/*IF param.assetsAppropriatingSlipInputFlag != null && (!"".equals(param.assetsAppropriatingSlipInputFlag))*/AND FA_FLG_1 = STR_TO_INT(/*param.assetsAppropriatingSlipInputFlag*/)  /*END*/
/*IF param.paymentRequestSlipInputFlag != null && (!"".equals(param.paymentRequestSlipInputFlag))*/AND FA_FLG_2 = STR_TO_INT(/*param.paymentRequestSlipInputFlag*/)  /*END*/
/*IF param.beginCode != null && !"".equals(param.beginCode)*/ AND UKM_CODE >= /*param.beginCode*/ /*END*/
/*IF param.endCode != null && !"".equals(param.endCode)*/ AND UKM_CODE <= /*param.endCode*/ /*END*/

/*IF param.revaluationObjectFlag != null && ("0".equals(param.revaluationObjectFlag))*/ AND EXC_FLG = 0 /*END*/
/*IF param.revaluationObjectFlag != null && (!"0".equals(param.revaluationObjectFlag)) && (!"".equals(param.revaluationObjectFlag))*/ AND EXC_FLG <> 0 /*END*/
/*IF param.customerInputFlag != null && (!"".equals(param.customerInputFlag))*/AND TRI_CODE_FLG = STR_TO_INT(/*param.customerInputFlag*/)  /*END*/
/*IF param.accrualDateInputFlag != null && (!"".equals(param.accrualDateInputFlag))*/AND HAS_FLG = STR_TO_INT(/*param.accrualDateInputFlag*/)  /*END*/
/*IF param.employeeInputFlag != null && (!"".equals(param.employeeInputFlag))*/AND EMP_CODE_FLG = STR_TO_INT(/*param.employeeInputFlag*/)  /*END*/
/*IF param.management1InputFlag != null && (!"".equals(param.management1InputFlag))*/AND KNR_FLG_1 = STR_TO_INT(/*param.management1InputFlag*/)  /*END*/
/*IF param.management2InputFlag != null && (!"".equals(param.management2InputFlag))*/AND KNR_FLG_2 = STR_TO_INT(/*param.management2InputFlag*/)  /*END*/
/*IF param.management3InputFlag != null && (!"".equals(param.management3InputFlag))*/AND KNR_FLG_3 = STR_TO_INT(/*param.management3InputFlag*/)  /*END*/
/*IF param.management4InputFlag != null && (!"".equals(param.management4InputFlag))*/AND KNR_FLG_4 = STR_TO_INT(/*param.management4InputFlag*/)  /*END*/
/*IF param.management5InputFlag != null && (!"".equals(param.management5InputFlag))*/AND KNR_FLG_5 = STR_TO_INT(/*param.management5InputFlag*/)  /*END*/
/*IF param.management6InputFlag != null && (!"".equals(param.management6InputFlag))*/AND KNR_FLG_6 = STR_TO_INT(/*param.management6InputFlag*/)  /*END*/
/*IF param.nonAccountingDetail1Flag != null && (!"".equals(param.nonAccountingDetail1Flag))*/AND HM_FLG_1 = STR_TO_INT(/*param.nonAccountingDetail1Flag*/)  /*END*/
/*IF param.nonAccountingDetail2Flag != null && (!"".equals(param.nonAccountingDetail2Flag))*/AND HM_FLG_2 = STR_TO_INT(/*param.nonAccountingDetail2Flag*/)  /*END*/
/*IF param.nonAccountingDetail3Flag != null && (!"".equals(param.nonAccountingDetail3Flag))*/AND HM_FLG_3 = STR_TO_INT(/*param.nonAccountingDetail3Flag*/)  /*END*/
/*IF param.salesTaxInputFlag != null && (!"".equals(param.salesTaxInputFlag))*/AND URI_ZEI_FLG = STR_TO_INT(/*param.salesTaxInputFlag*/)  /*END*/
/*IF param.purchaseTaxationInputFlag != null && (!"".equals(param.purchaseTaxationInputFlag))*/AND SIR_ZEI_FLG = STR_TO_INT(/*param.purchaseTaxationInputFlag*/)  /*END*/
/*IF param.multipleCurrencyInputFlag != null && (!"".equals(param.multipleCurrencyInputFlag))*/AND MCR_FLG = STR_TO_INT(/*param.multipleCurrencyInputFlag*/)  /*END*/

ORDER BY UKM_CODE