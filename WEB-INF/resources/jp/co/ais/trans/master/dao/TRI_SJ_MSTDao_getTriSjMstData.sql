SELECT
 sj.TJK_CODE
 
FROM
 TRI_SJ_MST sj
 
 WHERE sj.KAI_CODE = /*kaiCode*/
 AND  sj.TRI_CODE = /*triCode*/
 AND sj.TJK_CODE = /*tjkCode*/
 /*IF slipDate != null && slipDate != "" */ 
   AND sj.STR_DATE <= /*slipDate*/ 
   AND sj.END_DATE >= /*slipDate*/ 
 /*END*/
	