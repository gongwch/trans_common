SELECT 
 TRI_NAME   -- ñºèÃ
  
FROM 
 TRI_MST
  
WHERE 
 KAI_CODE = /*kaiCode*/ 

AND TRI_CODE = /*triCode*/
 
 /*IF slipDate != null && slipDate != "" */ 
  AND STR_DATE <= /*slipDate*/ 
  AND END_DATE >= /*slipDate*/ 
 /*END*/