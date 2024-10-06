SELECT DISTINCT
	  kmk.KMK_NAME_S,
       kmk.HKM_KBN,
       kmk.URI_ZEI_FLG, 
       kmk.SIR_ZEI_FLG,
       kmk.ZEI_CODE,
       kmk.TRI_CODE_FLG,
       kmk.EMP_CODE_FLG,
       kmk.KNR_FLG_1,
       kmk.KNR_FLG_2,
       kmk.KNR_FLG_3,
       kmk.KNR_FLG_4,
       kmk.KNR_FLG_5,
       kmk.KNR_FLG_6,
       kmk.HM_FLG_1,
       kmk.HM_FLG_2,
       kmk.HM_FLG_3,
       kmk.HAS_FLG,
       kmk.MCR_FLG,
       kmk.KMK_CNT_GL,
       kmk.KMK_CNT_AP,
       kmk.KMK_CNT_AR,
       kmk.KOTEI_DEP_CODE
FROM KMK_MST kmk
/*IF param.itemSystemCode != null && (!"".equals(param.itemSystemCode))*/ 
	INNER JOIN KMK_SUM_MST kmksum
	ON   kmk.KAI_CODE = kmksum.KAI_CODE
	AND kmk.KMK_CODE = kmksum.KMK_CODE
	AND kmksum.KMT_CODE = /*param.itemSystemCode*/ 
	AND kmksum.HYJ_KBN = '1'
	INNER JOIN KMK_TK_MST kmktk
	ON  kmksum.KAI_CODE = kmktk.KAI_CODE
	AND kmksum.KMT_CODE = kmktk.KMT_CODE
	/*IF param.itemSystemFlg != null && (!"".equals(param.itemSystemFlg) && "10".equals(param.itemSystemCode))*/ 
	INNER JOIN (
			SELECT
   				kmksum.KAI_CODE,
    				kmksum.SUM_CODE
			FROM
				KMK_SUM_MST kmksum
    			INNER JOIN KMK_MST kmk
        		ON  kmksum.KAI_CODE = kmk.KAI_CODE
        		AND kmksum.KMK_CODE = kmk.KMK_CODE
			WHERE kmk.SUM_KBN = 0  
    			AND kmksum.KAI_CODE = /*param.companyCode*/ 
    			AND kmksum.KMT_CODE = /*param.itemSystemCode*/ 
			) sumkmk
     ON  kmksum.KAI_CODE = sumkmk.KAI_CODE
     AND kmksum.KMK_CODE = sumkmk.SUM_CODE
	/*END*/ 
/*END*/ 	
WHERE kmk.KAI_CODE = /*param.companyCode*/

/*IF param.itemSystemCode != null && (!"".equals(param.itemSystemCode))*/
	/*IF param.itemSystemFlg != null && (!"".equals(param.itemSystemFlg) && "10".equals(param.itemSystemCode))*/ 
    	AND kmksum.KAI_CODE = /*param.companyCode*/ 
    	AND kmksum.KMT_CODE = /*param.itemSystemCode*/
	/*END*/     	
/*END*/ 
/*IF param.itemCode != null && (!"".equals(param.itemCode))*/ AND kmk.KMK_CODE = /*param.itemCode*/ /*END*/
/*IF param.summaryDivision != null && (!"".equals(param.summaryDivision))*/ AND kmk.SUM_KBN = STR_TO_INT(/*param.summaryDivision*/) /*END*/
/*IF param.bsAccountErasingDivision != null && (!"".equals(param.bsAccountErasingDivision))*/ AND kmk.KESI_KBN = STR_TO_INT(/*param.bsAccountErasingDivision*/) /*END*/
/*IF param.departmentCode != null && (!"".equals(param.departmentCode))*/    AND (kmk.KOTEI_DEP_CODE IS NULL OR kmk.KOTEI_DEP_CODE = /*param.departmentCode*/) /*END*/
/*IF param.dateSlipDate != null && (!"".equals(param.dateSlipDate))*/ AND kmk.STR_DATE <= /*param.dateSlipDate*/ /*END*/
/*IF param.dateSlipDate != null && (!"".equals(param.dateSlipDate))*/ AND kmk.END_DATE >= /*param.dateSlipDate*/ /*END*/
/*IF param.revaluationObjectFlag != null && ("0".equals(param.revaluationObjectFlag))*/ AND kmk.EXC_FLG = 0 /*END*/
/*IF param.revaluationObjectFlag != null && (!"0".equals(param.revaluationObjectFlag)) && (!"".equals(param.revaluationObjectFlag))*/ AND kmk.EXC_FLG <> 0 /*END*/
/*IF param.recivingSlipInputFlag != null && (!"".equals(param.recivingSlipInputFlag))*/ AND kmk.GL_FLG_1 = STR_TO_INT(/*param.recivingSlipInputFlag*/) /*END*/
/*IF param.drawingSlipInputFlag != null && (!"".equals(param.drawingSlipInputFlag))*/ AND kmk.GL_FLG_2 = STR_TO_INT(/*param.drawingSlipInputFlag*/) /*END*/
/*IF param.transferSlipInputFlag != null && (!"".equals(param.transferSlipInputFlag))*/AND kmk.GL_FLG_3 = STR_TO_INT(/*param.transferSlipInputFlag*/)  /*END*/
/*IF param.glItemCtrlDivision != null && (!"".equals(param.glItemCtrlDivision))*/ AND kmk.KMK_CNT_GL = /*param.glItemCtrlDivision*/ /*END*/
/*IF param.apItemCtrlDivision != null && (!"".equals(param.apItemCtrlDivision))*/ AND kmk.KMK_CNT_AP = /*param.apItemCtrlDivision*/ /*END*/
/*IF param.arItemCtrlDivision != null && (!"".equals(param.arItemCtrlDivision))*/ AND kmk.KMK_CNT_AR = /*param.arItemCtrlDivision*/ /*END*/
/*IF param.expenseInputFlag != null && (!"".equals(param.expenseInputFlag))*/AND kmk.AP_FLG_1 = STR_TO_INT(/*param.expenseInputFlag*/)  /*END*/
/*IF param.saimuFlg != null && (!"".equals(param.saimuFlg))*/AND kmk.AP_FLG_2 = STR_TO_INT(/*param.saimuFlg*/)  /*END*/
/*IF param.saikenFlg != null && (!"".equals(param.saikenFlg))*/AND kmk.AR_FLG_1 = STR_TO_INT(/*param.saikenFlg*/)  /*END*/
/*IF param.accountsRecivableErasingSlipInputFlag != null && (!"".equals(param.accountsRecivableErasingSlipInputFlag))*/AND kmk.AR_FLG_2 = STR_TO_INT(/*param.accountsRecivableErasingSlipInputFlag*/)  /*END*/
/*IF param.assetsAppropriatingSlipInputFlag != null && (!"".equals(param.assetsAppropriatingSlipInputFlag))*/AND kmk.FA_FLG_1 = STR_TO_INT(/*param.assetsAppropriatingSlipInputFlag*/)  /*END*/
/*IF param.paymentRequestSlipInputFlag != null && (!"".equals(param.paymentRequestSlipInputFlag))*/AND kmk.FA_FLG_2 = STR_TO_INT(/*param.paymentRequestSlipInputFlag*/)  /*END*/

/*IF param.itemType != null && (!"".equals(param.itemType))*/AND kmk.KMK_SHU = /*param.itemType*/  /*END*/
/*IF param.debitAndCreditDivision != null && (!"".equals(param.debitAndCreditDivision))*/AND kmk.DC_KBN = STR_TO_INT(/*param.debitAndCreditDivision*/)  /*END*/
/*IF param.subItemDivision != null && (!"".equals(param.subItemDivision))*/AND kmk.HKM_KBN = STR_TO_INT(/*param.subItemDivision*/)  /*END*/
/*IF param.customerInputFlag != null && (!"".equals(param.customerInputFlag))*/AND kmk.TRI_CODE_FLG = STR_TO_INT(/*param.customerInputFlag*/)  /*END*/
/*IF param.accrualDateInputFlag != null && (!"".equals(param.accrualDateInputFlag))*/AND kmk.HAS_FLG = STR_TO_INT(/*param.accrualDateInputFlag*/)  /*END*/
/*IF param.employeeInputFlag != null && (!"".equals(param.employeeInputFlag))*/AND kmk.EMP_CODE_FLG = STR_TO_INT(/*param.employeeInputFlag*/)  /*END*/
/*IF param.management1InputFlag != null && (!"".equals(param.management1InputFlag))*/AND kmk.KNR_FLG_1 = STR_TO_INT(/*param.management1InputFlag*/)  /*END*/
/*IF param.management2InputFlag != null && (!"".equals(param.management2InputFlag))*/AND kmk.KNR_FLG_2 = STR_TO_INT(/*param.management2InputFlag*/)  /*END*/
/*IF param.management3InputFlag != null && (!"".equals(param.management3InputFlag))*/AND kmk.KNR_FLG_3 = STR_TO_INT(/*param.management3InputFlag*/)  /*END*/
/*IF param.management4InputFlag != null && (!"".equals(param.management4InputFlag))*/AND kmk.KNR_FLG_4 = STR_TO_INT(/*param.management4InputFlag*/)  /*END*/
/*IF param.management5InputFlag != null && (!"".equals(param.management5InputFlag))*/AND kmk.KNR_FLG_5 = STR_TO_INT(/*param.management5InputFlag*/)  /*END*/
/*IF param.management6InputFlag != null && (!"".equals(param.management6InputFlag))*/AND kmk.KNR_FLG_6 = STR_TO_INT(/*param.management6InputFlag*/)  /*END*/
/*IF param.nonAccountingDetail1Flag != null && (!"".equals(param.nonAccountingDetail1Flag))*/AND kmk.HM_FLG_1 = STR_TO_INT(/*param.nonAccountingDetail1Flag*/)  /*END*/
/*IF param.nonAccountingDetail2Flag != null && (!"".equals(param.nonAccountingDetail2Flag))*/AND kmk.HM_FLG_2 = STR_TO_INT(/*param.nonAccountingDetail2Flag*/)  /*END*/
/*IF param.nonAccountingDetail3Flag != null && (!"".equals(param.nonAccountingDetail3Flag))*/AND kmk.HM_FLG_3 = STR_TO_INT(/*param.nonAccountingDetail3Flag*/)  /*END*/
/*IF param.salesTaxInputFlag != null && (!"".equals(param.salesTaxInputFlag))*/AND kmk.URI_ZEI_FLG = STR_TO_INT(/*param.salesTaxInputFlag*/)  /*END*/
/*IF param.purchaseTaxationInputFlag != null && (!"".equals(param.purchaseTaxationInputFlag))*/AND kmk.SIR_ZEI_FLG = STR_TO_INT(/*param.purchaseTaxationInputFlag*/)  /*END*/
/*IF param.multipleCurrencyInputFlag != null && (!"".equals(param.multipleCurrencyInputFlag))*/AND kmk.MCR_FLG = STR_TO_INT(/*param.multipleCurrencyInputFlag*/)  /*END*/
/*IF param.bgItemCtrlDivision != null && (!"".equals(param.bgItemCtrlDivision))*/ AND kmk.KMK_CNT_BG = /*param.bgItemCtrlDivision*/ /*END*/
/*IF param.counterbalanceAdjustmentCtrlDivision != null && (!"".equals(param.counterbalanceAdjustmentCtrlDivision))*/ AND kmk.KMK_CNT_SOUSAI = /*param.counterbalanceAdjustmentCtrlDivision*/ /*END*/
/*IF param.unArItemCtrlDivision != null && (!"".equals(param.unArItemCtrlDivision))*/ AND kmk.KMK_CNT_AR <> /*param.unArItemCtrlDivision*/ /*END*/
