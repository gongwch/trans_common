SELECT 
	CBK.*, CUR.CUR_NAME, BNK.BNK_NAME_S, BNK.BNK_STN_NAME_S
FROM 
	AP_CBK_MST CBK
LEFT OUTER JOIN 
	CUR_MST CUR ON CBK.KAI_CODE = CUR.KAI_CODE
AND 
	CBK.CUR_CODE = CUR.CUR_CODE
LEFT OUTER JOIN BNK_MST BNK ON CBK.CBK_BNK_CODE = BNK.BNK_CODE
AND CBK.CBK_STN_CODE = BNK.BNK_STN_CODE
WHERE CBK.KAI_CODE = /*kaiCode*/
/*BEGIN*/AND
/*IF cbkCbkCodeFrom != "" && cbkCbkCodeTo != "" */ CBK.CBK_CBK_CODE BETWEEN /*cbkCbkCodeFrom*/ AND /*cbkCbkCodeTo*/ /*END*/
/*IF cbkCbkCodeFrom != "" && cbkCbkCodeTo == "" */ CBK.CBK_CBK_CODE >= /*cbkCbkCodeFrom*/ /*END*/
/*IF cbkCbkCodeFrom == "" && cbkCbkCodeTo != "" */ CBK.CBK_CBK_CODE <= /*cbkCbkCodeTo*/ /*END*/
/*END*/
ORDER BY CBK.CBK_CBK_CODE
