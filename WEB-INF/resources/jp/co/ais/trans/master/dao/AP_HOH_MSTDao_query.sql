SELECT *
FROM AP_HOH_MST
WHERE KAI_CODE = /*kaiCode*/
/*BEGIN*/AND
/*IF hohHohCodeFrom != "" && hohHohCodeTo != "" */ HOH_HOH_CODE BETWEEN /*hohHohCodeFrom*/ AND /*hohHohCodeTo*/ /*END*/
/*IF hohHohCodeFrom != "" && hohHohCodeTo == "" */ HOH_HOH_CODE >= /*hohHohCodeFrom*/ /*END*/
/*IF hohHohCodeFrom == "" && hohHohCodeTo != "" */ HOH_HOH_CODE <= /*hohHohCodeTo*/ /*END*/
/*END*/
/*BEGIN*/AND
/*IF includeEmployeePayment == "1" && includeExternalPayment == "0" */ HOH_SIH_KBN = 0 /*END*/
/*IF includeEmployeePayment == "0" && includeExternalPayment == "1" */ HOH_SIH_KBN = 1 /*END*/
/*END*/