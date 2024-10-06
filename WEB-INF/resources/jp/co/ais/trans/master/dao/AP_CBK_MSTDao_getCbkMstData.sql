 
 SELECT  
  T1.CBK_NAME
  
FROM 
 AP_CBK_MST T1
 
WHERE 
  T1.KAI_CODE = /*kaiCode*/  

  AND T1.CBK_CBK_CODE = /*cbkCode*/ 
 
 /*IF slipDate !=null && slipDate != "" */ 
  AND T1.STR_DATE <= /*slipDate*/
  AND T1.END_DATE >= /*slipDate*/
 /*END*/
