SELECT 
 ZEI_NAME
 
FROM 
 SZEI_MST
  
WHERE 
 KAI_CODE = /*kaiCode*/
 AND ZEI_CODE = /*zeiCode*/
 /*IF slipDate != null && slipDate != "" */
  AND STR_DATE <= /*slipDate*/ 
  AND END_DATE >= /*slipDate*/  
 /*END*/