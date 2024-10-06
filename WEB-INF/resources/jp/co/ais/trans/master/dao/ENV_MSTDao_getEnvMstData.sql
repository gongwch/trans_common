SELECT KAI_NAME
FROM ENV_MST 
WHERE
KAI_CODE = /*kaiCode*/
 /*IF slipDate != null && slipDate != "" */ 
   AND STR_DATE <= /*slipDate*/ 
   AND END_DATE >= /*slipDate*/ 
 /*END*/